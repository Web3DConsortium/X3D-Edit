/*
* Copyright (c) 1995-2021 held by the author(s) .  All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions
* are met:
*
*  * Redistributions of source code must retain the above copyright
*       notice, this list of conditions and the following disclaimer.
*  * Redistributions in binary form must reproduce the above copyright
*       notice, this list of conditions and the following disclaimer
*       in the documentation and/or other materials provided with the
*       distribution.
*  * Neither the names of the Naval Postgraduate School (NPS)
*       Modeling Virtual Environments and Simulation (MOVES) Institute
*       (http://www.nps.edu and https://MovesInstitute.nps.edu)
*       nor the names of its contributors may be used to endorse or
*       promote products derived from this software without specific
*       prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
* "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
* LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
* FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
* COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
* INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
* BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
* CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
* ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
* POSSIBILITY OF SUCH DAMAGE.
*/
package org.web3d.x3d;

import java.io.IOException;
import java.util.Vector;

import javax.swing.JEditorPane;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;

import org.jdom.JDOMException;
import org.netbeans.api.editor.document.LineDocumentUtils;

import org.netbeans.editor.BaseDocument;
import org.netbeans.spi.xml.cookies.ValidateXMLSupport;

import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSParser;

import org.web3d.x3d.X3DEditorSupport.X3dEditor;
import org.web3d.x3d.palette.X3DPaletteUtilities;
import org.web3d.x3d.palette.X3DPaletteUtilities.ElementLocation;

import org.xml.sax.SAXException;

/**
 * BaseX3DEditAction.java
 * Created on Sep 21, 2007, 10:08:00 AM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */

abstract public class BaseX3DEditAction extends CookieAction
{
  protected static String linesep = System.getProperty("line.separator");

  X3DDataObject x3dDataObject;                       // init in actionPreamble
  protected X3DEditorSupport x3dEditorSupport;       // init in actionPreamble
  protected AbstractDocument abstractDocument;       // init in actionPreamble
  protected BaseDocument baseDocument;

  Vector<ElementLocation> saxLocations;              // init in actionPreamble
  protected JEditorPane documentEditorPane;          // init in actionPreamble

  @Override
  protected void performAction(Node[] activatedNodes)
  {
    try {
      actionPreamble(activatedNodes);
      doWorkUnderLock(activatedNodes);
    }
    catch (IOException | SAXException | JDOMException ex) {
      //NotifyDescriptor.Exception ed = new NotifyDescriptor.Exception(ex);
      NotifyDescriptor.Message msg = new NotifyDescriptor.Message(ex.getLocalizedMessage());
      DialogDisplayer.getDefault().notifyLater(msg); //ed);
    }
  }
  protected abstract void doWork(Node[] activatedNodes);

  private void actionPreamble(Node[] activatedNodes) throws IOException, SAXException, JDOMException
  {
    x3dDataObject = activatedNodes[0].getLookup().lookup(org.web3d.x3d.X3DDataObject.class);
    x3dEditorSupport  = x3dDataObject.getLookup().lookup(org.web3d.x3d.X3DEditorSupport.class);
    documentEditorPane = x3dEditorSupport.getOpenedPanes()[0];
    X3DPaletteUtilities.buildJdom(documentEditorPane);  // rebuild jdom tree

//    locSupp = new SAXLocatorSupport(x3dEditorSupport.getInputStream());
    //saxLocations = locSupp.getLocations();
    X3dEditor x3dEditor = (X3dEditor)X3DPaletteUtilities.getTopComponent(documentEditorPane);
    saxLocations = x3dEditor.getJdomSaxLocations();

    abstractDocument = (AbstractDocument) x3dEditorSupport.getDocument();
  }

  private void doWorkUnderLock(final Node[] activatedNodes)
  {
    if (abstractDocument instanceof BaseDocument) {
      baseDocument = LineDocumentUtils.asRequired(abstractDocument, BaseDocument.class);
      baseDocument.runAtomic(() -> {
          doWork(activatedNodes);
      });
    }
    else
      doWork(activatedNodes);
  }

  protected int getCaretLocation()
  {
    return documentEditorPane.getCaretPosition();
  }

  protected void setCaretLocation(int loc)
  {
    documentEditorPane.setCaretPosition(loc);
  }

//  protected ElementLocation findSelectedElement() throws BadLocationException
//  {
//    int pos = documentEditorPane.getCaretPosition();
//
//    Element root = abstractDocument.getDefaultRootElement();  // This is not an XML/jdom element
//    int count = root.getElementCount();
//
//    int[] linestarts = new int[count];
//
//    for (int i = 0; i < count; i++)
//      linestarts[i] = root.getElement(i).getStartOffset();
//
//    for (ElementLocation eloc : saxLocations) {
//      eloc.docOffsetEnd = linestarts[eloc.endLine - 1] + eloc.endCol - 1;
//      // Point the docOffsetStart field to the opening left bracket
//      // using eloc.startCol w/ no subtract pointd here V
//      //                              blah blach blah"><
//      // subtract one for 1- to zero based counting
//      // subtract another so as start looking within the previouse bracketted element
//      eloc.docOffsetStart = fixStart(abstractDocument, linestarts[eloc.startLine - 1] + eloc.startCol - 2);
//    }
//
//    boolean foundit = false;
//    int vi;
//    // loop from bottom up
//    for (vi = saxLocations.size() - 1; vi >= 0; vi--) {
//      ElementLocation vel = saxLocations.get(vi);
//      if ((pos > vel.docOffsetStart) && (pos < vel.docOffsetEnd)) {
//        foundit = true;
//        break;
//      }
//    }
//    if (foundit)
//      return saxLocations.get(vi);
//
//    throw new BadLocationException("Can't parse selected element", pos);
//  }

  protected void highlightSelectedElement(ElementLocation selectedLocation)
  {
    documentEditorPane.setSelectionStart(selectedLocation.docOffsetStart);
    documentEditorPane.setSelectionEnd(selectedLocation.docOffsetEnd);
  }

  protected void deleteLocatedElement(ElementLocation selectedLocation)
  {
    replaceSelectedElement(selectedLocation,null);
  }

  protected void replaceSelectedElement(ElementLocation selectedLocation, String s)
  {
    documentEditorPane.setSelectionStart(selectedLocation.docOffsetStart);
    documentEditorPane.setSelectionEnd(selectedLocation.docOffsetEnd);
    documentEditorPane.replaceSelection(s);
  }

  protected boolean expandSelfClosedSelectedElement(ElementLocation selectedLocation)
  {
    highlightSelectedElement(selectedLocation);
    String s = documentEditorPane.getSelectedText();
    int i = s.length()-1;
    while(i >0) {
      if(s.charAt(i) == '<')
        break;
      if(s.charAt(i) == '>') {
        if(s.charAt(i-1) == '/') {
          doExpandedSubstitution(selectedLocation,s,i-1);
          return true;
        }
      }
      i--;
    }

    // Here if no go
    return false;
  }

  private void doExpandedSubstitution(ElementLocation eloc, String s, int idx)
  {
    StringBuilder sb = new StringBuilder();
    sb.append(s.substring(0, idx));
    sb.append(">");
    sb.append(linesep);
    sb.append("</");
    sb.append(eloc.element.getName());
    sb.append(">");
    //sb.append(linesep);

    replaceSelectedElement(eloc,sb.toString());
  }

//  private int fixStart(AbstractDocument doc, int pos) throws BadLocationException
//  {
//    int newpos = pos;
//    while (doc.getText(newpos, 1).charAt(0) != '<') {
//      newpos--;
//      if (newpos == 0)
//        break;
//    }
//    return newpos;
//  }

  public org.w3c.dom.Document getW3cDocument() throws ClassNotFoundException, InstantiationException, IllegalAccessException, IOException
  {
    DOMImplementationRegistry dreg = DOMImplementationRegistry.newInstance();

    DOMImplementationLS dls = (DOMImplementationLS) dreg.getDOMImplementation("LS");
    LSParser parser = dls.createLSParser(DOMImplementationLS.MODE_SYNCHRONOUS, null);
    LSInput lsInp = dls.createLSInput();
    lsInp.setByteStream(this.x3dEditorSupport.getInputStream());
    return parser.parse(lsInp);
  }

  public static void doValidate(Node n)
  {
    X3DDataObject x3DDataObject = n.getLookup().lookup(X3DDataObject.class);
    ValidateXMLSupport validator = x3DDataObject.getValidateHelper();

    // Lifted from ValidateAction
    InputOutputReporter console = new InputOutputReporter();
    java.util.ResourceBundle bun = NbBundle.getBundle(BaseX3DEditAction.class);

    console.message(bun.getString("MSG_validation_started"));
    console.moveToFront();
    console.setNode(n);
    validator.validateXML(console);
    console.message(bun.getString("MSG_validation_finished"));
    console.moveToFront(true);
  }

  @Override
  protected int mode()
  {
    return CookieAction.MODE_EXACTLY_ONE;
  }

  @Override
  abstract public String getName();

  @Override
  protected Class[] cookieClasses()
  {
    return new Class[] {
      X3DDataObject.class
    };
  }

  @Override
  protected void initialize()
  {
    super.initialize();
    // see org.openide.util.actions.SystemAction.iconResource() javadoc for more details
    putValue("noIconInMenu", Boolean.TRUE);
  }

  @Override
  public HelpCtx getHelpCtx()
  {
    return HelpCtx.DEFAULT_HELP;
  }

  @Override
  protected boolean asynchronous()
  {
    return false;
  }

  /**
   * Find string in current document being edited
   * @param stringMatch
   * @return true if string found, false otherwise
   */
  protected boolean documentIncludesString (String stringMatch)
  {
        int documentLength  = abstractDocument.getDefaultRootElement().getDocument().getLength();
        try
        {
            return abstractDocument.getDefaultRootElement().getDocument().getText(0, documentLength).contains(stringMatch);
        }
        catch (BadLocationException ex)
        {
            System.out.println (new StringBuilder().append(ex).append("\n").append(ex.getStackTrace()).toString());
            return false;
        }
  }

  /**
   * Find location of string in current document being edited
   * @param stringMatch
   * @return index location of string if found, -1 otherwise
   */
  protected int documentLocationString (String stringMatch)
  {
        int documentLength  = abstractDocument.getDefaultRootElement().getDocument().getLength();
        try
        {
            if (documentIncludesString(stringMatch))  // more work than necessary, simply invoking indexOf would do the same thing
            {
                return abstractDocument.getDefaultRootElement().getDocument().getText(0, documentLength).indexOf(stringMatch);
            }
            else return -1;
        }
        catch (BadLocationException ex)
        {
            System.out.println (new StringBuilder().append(ex).append("\n").append(ex.getStackTrace()).toString());
            return -1;
        }
  }
}

