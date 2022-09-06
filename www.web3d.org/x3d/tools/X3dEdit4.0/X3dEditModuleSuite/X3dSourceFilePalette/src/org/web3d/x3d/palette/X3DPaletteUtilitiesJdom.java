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
package org.web3d.x3d.palette;

import java.awt.Container;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;
import java.util.Vector;
import javax.swing.SwingUtilities;
import javax.swing.text.AbstractDocument;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import org.jdom.Attribute;
import org.jdom.JDOMException;
import org.jdom.input.SAXHandler;
import org.netbeans.api.xml.cookies.CookieMessage;
import org.netbeans.api.xml.cookies.CookieObserver;
import org.netbeans.editor.BaseDocument;
import org.netbeans.spi.xml.cookies.ValidateXMLSupport;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.UndoRedo;
import org.openide.util.Lookup;
import org.openide.util.RequestProcessor;
import org.openide.util.lookup.Lookups;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.openide.windows.OutputWriter;
import org.openide.windows.TopComponent;
import org.openide.xml.EntityCatalog;
import org.web3d.x3d.EditElementAction;
import org.web3d.x3d.X3DDataObject;
import org.web3d.x3d.X3DEditorSupport;
import org.web3d.x3d.options.X3dOptions;
import org.web3d.x3d.palette.items.BaseX3DElement;
import org.xml.sax.Attributes;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

/**
 * X3DPaletteUtilities.java
 * Created on March 9, 2007, 10:13 AM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public final class X3DPaletteUtilitiesJdom
{
    static X3DEditorSupport.X3dEditor x3dEditor;
    static X3DEditorSupport           x3dEditorSupport;
    static X3DDataObject              x3dDataObject;
    
  public static int insert(String lineFeedText, JTextComponent target, BaseX3DElement baseX3DElement) throws BadLocationException
  {
    return insert(lineFeedText, 
            target, 
            true, // boolean reformat
            baseX3DElement);
  }

  //todo use logic to indent appropriately if at EOL, BOL, after close tag, etc.
  public static int insert(String insertionText, final JTextComponent target, boolean reformat, BaseX3DElement baseX3DElement) throws BadLocationException
  {
    int cursorOffsetWhenDone = 2;
      
    if (insertionText == null)
      insertionText = "";
    Document doc = target.getDocument();
    if (doc == null)
      return 0;
    if (doc instanceof BaseDocument) {
      BaseDocument baseDoc = (BaseDocument) doc;
     // baseDoc.atomicLock();
    }

    int start = insert(insertionText, target, doc);
    if (start >= 0) {
      // select the inserted text
    //  Caret caret = target.getCaret();
    //  int current = caret.getDot();
     // caret.setDot(start);
     // caret.moveDot(current);
     // caret.setSelectionVisible(true);
      target.setSelectionStart(start + cursorOffsetWhenDone);
      target.setSelectionEnd  (start + cursorOffsetWhenDone + 5); //current);
    }

    //if (doc instanceof BaseDocument)
    //  ((BaseDocument) doc).atomicUnlock();
/*
    if (reformat && doc instanceof BaseDocument) {
      BaseDocument bdoc = (BaseDocument) doc;
      //New 6.5 code
      final Indent indent = Indent.get(doc);
      indent.lock();
      try {
        bdoc.runAtomic(new Runnable()
        {
          public void run()
          {
            try {
              indent.reindent(target.getSelectionStart(), target.getSelectionEnd());
            }
            catch (BadLocationException ex) {
              ex.printStackTrace(System.err);
            }
          }
        });
      }
      finally {
        indent.unlock();
      }

      //pre-6.5 code:
//      bdoc.getFormatter().reformatLock();
//      bdoc.getFormatter().reformat(bdoc,target.getSelectionStart(),target.getSelectionEnd());
//      bdoc.getFormatter().reformatUnlock();
    }
*/
    if(start >= cursorOffsetWhenDone) // good insert
      baseX3DElement.postInsert(target);

    if (X3dOptions.getAutoValidate())
      // Schedule validation task in separate thread
      RequestProcessor.getDefault().post(new ValidateThread(target, "inserted text", true));
    return start;
  }

  private static int insert(String insertionText, JTextComponent target, Document doc) throws BadLocationException
  {
    int start = -1;
    try {
      //at first, find selected text range
      Caret caret = target.getCaret();
      int p0 = Math.min(caret.getDot(), caret.getMark());
      int p1 = Math.max(caret.getDot(), caret.getMark());
      doc.remove(p0, p1 - p0);

      //replace selected text by the inserted one
      start = caret.getDot();
      doc.insertString(start, insertionText, null);
    }
    catch (BadLocationException ble) {
        ble.printStackTrace(System.err);
    }
    return start;
  }
    
  public static String getCurrentDocumentX3dVersion ()
  {
      String            x3dVersion = "not found by X3DPaletteUtilities";
      if (x3dEditor != null)
      {
        org.jdom.Document currentDocument = x3dEditor.getJdomDoc();
        org.jdom.Element  x3dRootElement  = currentDocument.getRootElement();
                          x3dVersion      = x3dRootElement.getAttributeValue("version");
      }
      else
      {
          System.err.println ("*** Application error, X3DPaletteUtilities.getCurrentDocumentX3dVersion() invoked before x3dEditor instantiated");
      }
      return x3dVersion;
  }
    
  public static boolean isCurrentDocumentX3dVersion4 ()
  {
      return getCurrentDocumentX3dVersion().startsWith("4");
  }
    
  public static boolean isCurrentDocumentX3dVersion3 ()
  {
      return getCurrentDocumentX3dVersion().startsWith("3");
  }

  public static class ValidateThread implements Runnable
  {
    private final JTextComponent thisTarget;
    private final String thisFilename;
    boolean thisDoDialog;

    public ValidateThread(JTextComponent target, String filename, boolean doDialog)
    {
      thisTarget = target;
      thisFilename = filename;
      thisDoDialog = doDialog;
    }

    @Override
    public void run()
    {
      InputSource is = new InputSource(new StringReader(thisTarget.getText()));

      is.setSystemId(thisFilename);

      x3dEditor        = ((X3DEditorSupport.X3dEditor) getTopComponent(thisTarget));
      x3dEditorSupport =  x3dEditor.getX3dEditorSupport();
      x3dDataObject    = (X3DDataObject)x3dEditorSupport.getDataObject();

      ValidateXMLSupport xsup = x3dDataObject.getLookup().lookup(ValidateXMLSupport.class); //new ValidateXMLSupport(is); //MyXMLValidateSupport(is); //ValidateXMLSupport(is);

      ValidateListener lis = new ValidateListener(thisTarget,thisDoDialog,x3dEditor);

      xsup.validateXML(lis);
      lis.receive(null); // signal done
    }

    class MyXMLValidateSupport extends ValidateXMLSupport
    {
      MyXMLValidateSupport(InputSource is)
      {
        super(is);
      }

      @Override
      protected EntityResolver createEntityResolver()
      {
        return EntityCatalog.getDefault(); // This will find dtds and schemas in the local catalog, the default goes to UserCatalog
      }
    }
  }

  /**
   * Does not rebuild jdom document
   * @param target
   */
  public static void checkRouteForwardRefs(JTextComponent target)
  {
    org.jdom.Document doc = getJdom(target); //getJdomDoc(target);

    if (doc == null) return;
    
    org.jdom.Element root = doc.getRootElement();
    Map<String,Integer>defMap = new HashMap<>();
    Map<org.jdom.Element,Integer>routeMap = new HashMap<>();
    doElement(root,defMap,routeMap,-1);

    for(org.jdom.Element route :routeMap.keySet()) {
      Attribute attr = route.getAttribute("toNode");
      String toNode = (attr != null)?attr.getValue():null;

      attr = route.getAttribute("fromNode");
      String fromNode = (attr != null)?attr.getValue():null;

      InputOutput io = IOProvider.getDefault().getIO("XML check", false); // Name matches existing tab
      OutputWriter ow = io.getOut();
      int routeIdx = routeMap.get(route);

      if(toNode != null) {
        Integer defIdx = defMap.get(toNode);
        if(defIdx != null) {
          if(routeIdx <= defIdx) // forward ref
            ow.println("Error: ROUTE element defined before referenced '"+toNode+"' element.");
        }
      }

      if(fromNode != null) {
        Integer defIdx = defMap.get(fromNode);
        if(defIdx != null) {
          if(routeIdx <= defIdx) // forward ref
            ow.println("Error: ROUTE element defined before referenced '"+fromNode+"' element.");
        }
      }
    }
  }

  /**
   *
   * @param elm
   * @param hm
   * @param routes
   * @param idx last existing index
   * @return index for the one just added
   */
  @SuppressWarnings("unchecked")
  private static int doElement(org.jdom.Element elm, Map<String,Integer>hm, Map<org.jdom.Element,Integer>routes, int lastIndex)
  {
    int thisIndex = lastIndex+1;
    if(elm.getName().equalsIgnoreCase("ROUTE")) {
      routes.put(elm, thisIndex);
      return thisIndex;
    }

    Attribute defAttr = elm.getAttribute("DEF");
    if(defAttr != null)
      hm.put(defAttr.getValue(), thisIndex);   // key = name, value = order

    for(org.jdom.Element child : (List<org.jdom.Element>)elm.getChildren())
    {
      thisIndex = doElement(child,hm,routes,thisIndex); // recurse
    }

    return thisIndex;
  }


  static class ValidateListener implements CookieObserver
  {
    boolean errorSeen = false;
    InputOutput io = IOProvider.getDefault().getIO("XML check", false); // Name matches existing tab
    OutputWriter ow = io.getOut();
    JTextComponent target;
    boolean doDialog = false;
    X3DEditorSupport.X3dEditor edi;

    ValidateListener(JTextComponent target, boolean doDialog, X3DEditorSupport.X3dEditor edi)
    {
      this.target = target;
      this.doDialog = doDialog;
      this.edi = edi;
    }

    @Override
    public void receive(CookieMessage msg)
    {
      if (msg != null) {
        io.select(); // make visible // should be able to be cleared the first time using the clear action assoc. with the panel
        int lev = msg.getLevel();
        if (lev == CookieMessage.ERROR_LEVEL || lev == CookieMessage.FATAL_ERROR_LEVEL )//|| lev == CookieMessage.INFORMATIONAL_LEVEL)
          errorSeen = true;
        ow.println(msg.getMessage());
      }
      else {
        ow.println("Validation complete");
        if (errorSeen && doDialog) {
          ValidationErrorResponse resp = showDialog();
            switch (resp) {
                case UNDO:
                    undoLastInsert(target);
                    break;
                case ACCEPT:
                    ;
                    break;
                case REEDIT:
                    int selStart = target.getSelectionStart();
                    String selStr = target.getSelectedText();
                    int i=0;
                    while(selStr.charAt(i) != '<' && i < selStr.length())
                    {
                        i++;
                    }       
                    if(i < selStr.length()) {
                        target.getCaret().setVisible(true);
                        target.setCaretPosition(selStart+i+1);
                        
                        // do the edit action again
                        SwingUtilities.invokeLater(() -> {
                            Lookup lu = Lookups.forPath("Actions/Edit");
                            EditElementAction editAct = lu.lookup(EditElementAction.class);
                            editAct.publicPerformAction(edi.getActivatedNodes());
                        });
                    }       break;
                default:
                    break;
            }
        }
        checkRouteForwardRefs(target);
        ow.println("X3D check complete");
        ow.println();
        // done in above buildJdomModel(target);
      }
      ow.flush();
    }
  }

  public static class myCheckXMLSupport extends org.netbeans.spi.xml.cookies.CheckXMLSupport
  {
     public myCheckXMLSupport(InputSource is)
     {
       super(is);
     }
     public XMLReader getXMLReader()
     {
       return this.createParser(false); // no validate
     }
  }

  public static XMLReader getProperReader(InputStream inStr)
  {
    return new myCheckXMLSupport(new InputSource(inStr)).getXMLReader();
  }

  /**
   * Method called from actions which may rely on the last-build jdom docuement;
   * these are actions like getUSE saxLocations, check route forward refs, etc.
   * @param s
   * @return
   * @throws java.io.IOException
   * @throws org.xml.sax.SAXException
   * @throws org.jdom.JDOMException
   */
  public static org.jdom.Document buildJdomFromString(String s) throws IOException, SAXException, JDOMException
  {
    return _buildJdom(s).doc;
  }

  /**
   * Method called from editing action, selecting action, etc; rebuilds
   * jdom document and sets in editor
   * @param target
   * @return
   * @throws java.io.IOException
   * @throws org.jdom.JDOMException
   */
  public static org.jdom.Document buildJdom(JTextComponent target) throws IOException, JDOMException
  {
    jDOMresults res = _buildJdom(target.getText());
    X3DEditorSupport.X3dEditor myEd = (X3DEditorSupport.X3dEditor) getTopComponent(target);
    myEd.setJdomDoc(res.doc);
    myEd.setJdomSaxLocations(res.saxLocations);
    return res.doc;
  }

  public static X3DDataObject getX3dDataObject(JTextComponent target)
  {
    X3DEditorSupport.X3dEditor myEd = (X3DEditorSupport.X3dEditor) getTopComponent(target);
    return (X3DDataObject)myEd.getX3dEditorSupport().getDataObject();
  }

  /**
   * implementation method to pass back the locator too
   * play this crazy game to use jdom, but use the parser w/in netbeans which knows how to use the user catalog we installed
   * @param s
   * @return
   * @throws java.io.IOException
   * @throws org.jdom.JDOMException
   */
  private static jDOMresults _buildJdom(String s) throws IOException, JDOMException
  {
    StringReader txtRdr = new StringReader(s);
    myCheckXMLSupport xsup = new myCheckXMLSupport(new InputSource(txtRdr));  // just using to grab the reader from it
    XMLReader rdr = xsup.getXMLReader();
    mySAXBuilder builder = new mySAXBuilder(false,rdr);
    org.jdom.Document doc = builder.build(txtRdr);
    mySaxHandler saxH = builder.getMySaxHandler();
    jDOMresults res = new jDOMresults(doc,saxH.saxLocations);
    return res;
  }

  /**
   * Return current jdom object; doesn't build it
   * @param target
   * @return
   */
  public static org.jdom.Document getJdom(JTextComponent target)
  {
    X3DEditorSupport.X3dEditor myEd = (X3DEditorSupport.X3dEditor) getTopComponent(target);
    if (myEd == null)
      return null;
    return myEd.getJdomDoc();
  }

  public static Vector<String> getUSEvector(JTextComponent target, String type)
  {
    Vector<String> v = new Vector<>();
    org.jdom.Document doc = getJdom(target);
    if(doc == null)
      return v;

    return collectDefs(doc.getRootElement(), type, v);
  }

  @SuppressWarnings(value = "unchecked")
  static Vector<String> collectDefs(org.jdom.Element el, String nm, Vector<String> v)
  {
    if (el.getName().equalsIgnoreCase(nm)) {
      org.jdom.Attribute attr = el.getAttribute("DEF");
      if (attr != null) {
        String val = attr.getValue();
        if (val != null && val.length() > 0)
          v.add(val);
      }
    }
    List<org.jdom.Element> lis = el.getChildren();
    for (org.jdom.Element e : lis)
    {
      collectDefs(e, nm, v);
    }
    return v;
  }

  enum ValidationErrorResponse {
    ACCEPT("Accept"),
    UNDO("Discard"),
    REEDIT("Edit again");

    private String prettyName;
    ValidationErrorResponse(String prettyName) {
      this.prettyName=prettyName;
    }

    static ValidationErrorResponse findEnum(String s)
    {
      for(ValidationErrorResponse resp : ValidationErrorResponse.values()) {
        if(resp.prettyName.equalsIgnoreCase(s))
            return resp;
      }
      return null;
    }
  }

//  private static boolean showDialog()
//  {
//     NotifyDescriptor.Confirmation nd = new NotifyDescriptor.Confirmation("<html><center>Invalid name, location or other problem<br> with the new element, or another element (see Output window).<br><br>Cancel insert?", "XML Validation Error", DialogDescriptor.YES_NO_OPTION, DialogDescriptor.ERROR_MESSAGE);
//     return DialogDisplayer.getDefault().notify(nd) == NotifyDescriptor.YES_OPTION;
//  }

  private static ValidationErrorResponse showDialog()
  {
    NotifyDescriptor notd = new NotifyDescriptor(
        "<html><center>Invalid name, location or other problem<br> with either the new element or another element<br>(see Output window for details)<br><br>Accept insertion, discard insertion, or edit again?",
        "XML Validation Error",
        NotifyDescriptor.YES_NO_CANCEL_OPTION,
        NotifyDescriptor.WARNING_MESSAGE,
        new Object[] { ValidationErrorResponse.ACCEPT.prettyName, ValidationErrorResponse.UNDO.prettyName, ValidationErrorResponse.REEDIT.prettyName},
        ValidationErrorResponse.UNDO.prettyName);

    Object obj = DialogDisplayer.getDefault().notify(notd);
    return ValidationErrorResponse.findEnum((String)obj);
  }

  private static void undoLastInsert(JTextComponent target)
  {
    TopComponent tc = getTopComponent(target);
    if (tc != null) {
      UndoRedo ur = tc.getUndoRedo();
      if (ur.canUndo()) {
        ur.undo();
        if(ur.canUndo())     // we insert, then format, so 2 undos
          ur.undo();
        return;
      }
    }
    System.out.println("Can't undo");
  }

  @SuppressWarnings(value = "unchecked")
  public static TopComponent getTopComponent(JTextComponent target)
  {
    TopComponent.Registry reg = TopComponent.getRegistry();
    Set<TopComponent> set = reg.getOpened();
    for (TopComponent tc : set) {
      if (tc instanceof X3DEditorSupport.X3dEditor)
        if (isProperParent(tc, target))
          return tc;
    }
    System.out.println("Can't find top component");
    return null;
  }

  private static boolean isProperParent(TopComponent tc, JTextComponent target)
  {
    Container c = target.getParent();
    while (c != null) {
      if (c.equals(tc))
        return true;
      c = c.getParent();
    }
    return false;
  }
  public static Vector<ElementLocation> buildLocations(JTextComponent pane) throws BadLocationException
  {
    X3DEditorSupport.X3dEditor x3dEditor = ((X3DEditorSupport.X3dEditor) getTopComponent(pane));
    X3DEditorSupport x3dEditorSupport = x3dEditor.getX3dEditorSupport();
    Vector<ElementLocation> saxLocations = x3dEditor.getJdomSaxLocations();
    AbstractDocument abstractDocument = (AbstractDocument)x3dEditorSupport.getDocument();

    int pos = pane.getCaretPosition();

    javax.swing.text.Element root = abstractDocument.getDefaultRootElement();  // This is not an XML/jdom element
    int count = root.getElementCount();

    int[] linestarts = new int[count];

    for (int i = 0; i < count; i++)
    {
      linestarts[i] = root.getElement(i).getStartOffset();
    }

    for (ElementLocation eloc : saxLocations) {
      eloc.docOffsetEnd = linestarts[eloc.endLine - 1] + eloc.endColumn - 1;
      // Point the docOffsetStart field to the opening left bracket
      // using eloc.startCol w/ no subtract pointd here V
      //                              blah blach blah"><
      // subtract one for 1- to zero based counting
      // subtract another so as start looking within the previouse bracketted element
      eloc.docOffsetStart = fixStart(abstractDocument, linestarts[eloc.startLine - 1] + eloc.startColumn - 2);
    }
    return saxLocations;
  }

  public static ElementLocation findNamedElement(JTextComponent pane, String targetName) throws BadLocationException
  {
    return findNamedElement(pane,targetName,null);
  }

  public static ElementLocation findNamedElement(JTextComponent pane, String targetName, String namePrefix) throws BadLocationException
  {
    Vector<ElementLocation> saxLocations = buildLocations(pane);
    for(ElementLocation location : saxLocations) {
      if(location.name.equals(targetName)) {
        if(namePrefix == null || !namePrefix.equals(location.element.getNamespacePrefix()))
          continue;
        return location;
      }
    }
    return null;
  }

// TODO finish testing if really needed
//  public static ElementLocation findNamedExternProtoDeclare(JTextComponent pane, String targetName, String namePrefix) throws BadLocationException
//  {
//    X3DEditorSupport.X3dEditor x3dEditor = ((X3DEditorSupport.X3dEditor) getTopComponent(pane));
//    X3DEditorSupport x3dEditorSupport = x3dEditor.getX3dEditorSupport();
//    Vector<ElementLocation> saxLocations = x3dEditor.getJdomSaxLocations();
//    AbstractDocument abstractDocument = (AbstractDocument)x3dEditorSupport.getDocument();
//
//    for(ElementLocation location : saxLocations) {
//      if(location.name.equals("ExternProtoDeclare"))
//      {
//          if(namePrefix == null || !namePrefix.equals(location.element.getNamespacePrefix()))
//          continue;
//
//          int documentLength  = abstractDocument.getDefaultRootElement().getDocument().getLength();
//          int remainingLength = documentLength - location.docOffsetStart;
//          int nameOffset  = abstractDocument.getDefaultRootElement().getDocument().getText(location.docOffsetStart,remainingLength).indexOf("name='", 0);
//          nameOffset += "name='".length();
//          if (abstractDocument.getDefaultRootElement().getDocument().getText(location.docOffsetStart+nameOffset,remainingLength-nameOffset).startsWith(targetName))
//          return location;
//      }
//    }
//    return null;
//  }

  public static ElementLocation findSelectedElement(JTextComponent pane) throws BadLocationException
  {
    Vector<ElementLocation> saxLocations = buildLocations(pane);
//    X3DEditorSupport.X3dEditor edi = ((X3DEditorSupport.X3dEditor) getTopComponent(pane));
//    X3DEditorSupport supp = edi.getX3dEditorSupport();
//    Vector<ElementLocation> saxLocations = edi.getJdomSaxLocations();
//    AbstractDocument abDoc = (AbstractDocument)supp.getDocument();
//
     int pos = pane.getCaretPosition();
	 while (pane.getText().charAt(pos) == ' ')
		 pos++; // lean forward into element if adjacent
	 if (pane.getText().charAt(pos) == '<')
		 pos++; // lean forward into element if adjacent
	 String selectionString = pane.getText().substring(pos); // debug diagnostic

//    javax.swing.text.Element root = abDoc.getDefaultRootElement();  // This is not an XML/jdom element
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
//      eloc.docOffsetStart = fixStart(abDoc, linestarts[eloc.startLine - 1] + eloc.startCol - 2);
//    }

    boolean foundit = false;
    int vi;
    // loop from bottom up
    for (vi = saxLocations.size() - 1; vi >= 0; vi--) {
      ElementLocation vel = saxLocations.get(vi);  // vel = vector element location, vi = vector index
      if ((pos > vel.docOffsetStart) && (pos < vel.docOffsetEnd)) {
        foundit = true;
        break;
      }
    }
    if (foundit)
      return saxLocations.get(vi);
	else
	{
		// cursor out of bounds somehow - probably XML/DOCTYPE preamble - simply edit root X3D element
		return saxLocations.firstElement();
		//    throw new BadLocationException("Can't find selected element.", pos);
	}
  }
  private static int fixStart(AbstractDocument doc, int pos) throws BadLocationException
  {
    int newpos = pos;
    while (doc.getText(newpos, 1).charAt(0) != '<') {
      newpos--;
      if (newpos == 0)
        break;
    }
    return newpos;
  }

  public static class mySAXBuilder extends org.jdom.input.SAXBuilder
  {
    private final XMLReader xmlReader;
    private final mySaxHandler saxContentHandler = new mySaxHandler();
    
    public mySAXBuilder(boolean val, XMLReader reader)
    {
      super(val);
      this.xmlReader = reader;
    }
    @Override
    protected XMLReader createParser() throws JDOMException
    {
      return xmlReader;
    }

    @Override
    protected SAXHandler createContentHandler()
    {
      return saxContentHandler;
    }

    public mySaxHandler getMySaxHandler()
    {
      return saxContentHandler;
    }
  }

  public static class mySaxHandler extends org.jdom.input.SAXHandler
  {
    public Vector<ElementLocation> saxLocations = new Vector<>();

    private Locator loc;
    private final Stack<ElementLocation> stack = new Stack<>();

    public mySaxHandler()
    {
    }

    /**
     * Totally screwy.  Got to use the one we GET, not set.  Must use what is returned here.
     * @param locator
     */
    @Override
    public void setDocumentLocator(Locator locator)
    {
      loc = locator;
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
    {
      //System.out.println("startElement uri:"+uri+" localName:"+localName+" qName:"+qName);
      super.startElement(uri, localName, qName, attributes);

      ElementLocation myE = new ElementLocation(localName,loc.getLineNumber(),loc.getColumnNumber(), getCurrentElement());
      if (!stack.empty())
        myE.parent = stack.peek();
      stack.push(myE);

      saxLocations.add(myE);
      //System.out.println(getCurrentElement() + "at " + loc.getLineNumber() + " " + loc.getColumnNumber());
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException
    {
      super.endElement(uri,localName,qName);
      //System.out.println("endElement uri:"+uri+" localName:"+localName+" qName:"+qName);
      ElementLocation myE = stack.pop();
      myE.endColumn = loc.getColumnNumber();
      myE.endLine = loc.getLineNumber();
    }
  }

  public static class jDOMresults
  {
    public org.jdom.Document doc;
    public Vector<ElementLocation> saxLocations;

    public jDOMresults(org.jdom.Document doc, Vector<ElementLocation>saxLocations)
    {
      this.doc = doc;
      this.saxLocations = saxLocations;
    }
  }

  public static class ElementLocation
  {
    /**
     * Element name
     */
    public String name;
    public int startColumn = -1,  startLine = -1;
    public int endColumn = -1,  endLine = -1;
    /**
     * Starting index in document
     */
    public int docOffsetStart = -1;

    /**
     * Ending index in document
     */
    public int docOffsetEnd = -1;
    public org.jdom.Element element;

    public ElementLocation(String name, int startLine, int startCol, org.jdom.Element element)
    {
      this.name = name;
      this.startColumn = startCol;
      this.startLine = startLine;
      this.element = element;
    }
    public ElementLocation parent;
  }

  /**
   * Substitute XML escape characters for &apos; apostrophe characters
   * @param text input string to escape
   * @return normalized XML text
   */
  public static String escapeXmlApostrophes(String text)
  {
    return text.replace("'", "&apos;");
  }

  /**
   * Substitute XML escape characters for &lt; less-than characters
   * @param text input string to escape
   * @return normalized XML text
   */
  public static String escapeXmlLessThanCharacters(String text)
  {
    return text.replace("<", "&lt;");
  }

  /**
   * Substitute XML escape characters for &apos; apostrophe character, &lt; less-than sign and &gt; greater-than sign
   * @param text input string to escape
   * @return normalized XML text
   */
  public static String escapeXmlCharacters(String text)
  {
    String result = text;
//  result = s.replace("&", "&amp;");  // do not change ampersand since it will break other character entities
    result = result.replace("'", "&apos;"); // single quote
    result = result.replace("<", "&lt;");   // less-than sign
//  result = result.replace(">", "&gt;");   // greater-than sign, not required/essential
    // appears to be unneeded:  .replace("\"", "&quot;"); // double quote
    return   result; 
  }
}