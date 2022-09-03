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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import javax.swing.text.JTextComponent;
import javax.swing.text.Position;
import org.netbeans.modules.editor.indent.api.Indent;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.nodes.Node;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;
import org.web3d.x3d.options.X3dOptions;
import org.web3d.x3d.palette.X3DPaletteUtilities;
import org.web3d.x3d.palette.X3DPaletteUtilities.ElementLocation;
import org.web3d.x3d.palette.X3DPaletteUtilities.ValidateThread;
import org.web3d.x3d.palette.items.BaseCustomizer;
import org.web3d.x3d.palette.items.BaseX3DElement;
import org.web3d.x3d.palette.items.EXTRUSION;
import org.web3d.x3d.palette.items.PROTOTYPE_ExtrusionCrossSection;

// superceded by XhtmlX3domAction

//@ActionID(id = "org.web3d.x3d.ExportX3domAction", category = "Edit")
//@ActionRegistration(iconBase = "org/web3d/x3d/resources/edit.png",
//                    displayName = "#CTL_ExportX3domAction",
//                    lazy=true) // don't do lazy=false since iconBase no longer gets registered
//@ActionReferences(value = {
//  @ActionReference(path = "Menu/X3D-Edit/View Saved Scene", position = 70),
//  @ActionReference(path = "Editors/model/x3d+xml/Popup", position = 212),
//  @ActionReference(path = "Toolbars/Author Workflow", position = 100),})
//
//@NbBundle.Messages("CTL_ExportX3domAction=Export X3DOM XHTML")

/**
 * @author jmbailey@nps.edu
 */
public class ExportX3domAction extends BaseX3DEditAction //CookieAction
{
  protected ElementLocation selectedElement;

  protected org.jdom.Document getSelectedElementAsJdom(String elementString) throws Exception
  {
    return X3DPaletteUtilities.buildJdomFromString(elementString);
  }

  @Override
  public String getName()
  {
    return NbBundle.getMessage(getClass(), "CTL_ExportX3domAction");
  }

  @Override
  protected void doWork(Node[] activatedNodes)
  {
    int selectedStringLength;
    try {
      ElementLocation selectedLocation = X3DPaletteUtilities.findSelectedElement(documentEditorPane); //findSelectedElement();
      selectedLocation = changeIfSpecialCase(selectedLocation);       // special case edits
      highlightSelectedElement(selectedLocation);

      selectedStringLength = selectedLocation.docOffsetEnd - selectedLocation.docOffsetStart;
      String selectedString = documentEditorPane.getText(selectedLocation.docOffsetStart, selectedStringLength);

      org.jdom.Document jdoc = getSelectedElementAsJdom(selectedString);
      if (jdoc == null)
        return; // cancelled

      x3dElement = null; // reset

      String newString = doDialog(jdoc, documentEditorPane, selectedLocation);
      boolean handlerInsertionMade = false;

      if (newString != null) // node editing dialog returned a result, so insert it and replace prior text
      {
        insertNewString(selectedLocation, newString, selectedStringLength);

            // =============================================================================================================================
        // handlers for special prototype insertions
        int documentLength = abstractDocument.getDefaultRootElement().getDocument().getLength();
        int remainingLength = documentLength - selectedLocation.docOffsetStart;
        StringBuilder prototypeInsertion = new StringBuilder();
        boolean externProtoDeclareFound;
        String documentText = abstractDocument.getDefaultRootElement().getDocument().getText(0, documentLength);

        // find next closing </Shape> for cases where extra source must follow
        int shapeOffset = abstractDocument.getDefaultRootElement().getDocument().getText(selectedLocation.docOffsetStart, remainingLength).indexOf("</Shape>", 0);
        shapeOffset += "</Shape>".length();

        // =============================================================================================================================
        if (x3dElement.isAppendExtrusionCrossSection()) // perform Extrusion postprocessing
        {
          // customize field values by setting extrusion node
          PROTOTYPE_ExtrusionCrossSection extrusionCrossSection = new PROTOTYPE_ExtrusionCrossSection();
          extrusionCrossSection.setExtrusion((EXTRUSION) x3dElement);

          // determine where to put the change
          selectedLocation.docOffsetStart += shapeOffset; // newString.length() +
          selectedLocation.docOffsetEnd = selectedLocation.docOffsetStart; // same start and end, not replacing any text at this point

          prototypeInsertion.append("\n");
          prototypeInsertion.append("  <!-- ==================== -->\n");
          prototypeInsertion.append("  <Switch whichChoice='0' class='visualization.Extrusion'>\n");

                // only insert ExternProtoDeclare if not already found
          // TODO need ExternProtoDeclare at beginning
          externProtoDeclareFound = documentIncludesString("<ExternProtoDeclare name='ExtrusionCrossSection'")
              || documentIncludesString("name='ExtrusionCrossSection'/>"); // intervening appinfo follows <ExternProtoDeclare
          int locationOriginalExtrusionCrossSection = documentLocationString("name='ExtrusionCrossSection'");

          if (!externProtoDeclareFound) {
            prototypeInsertion.append(extrusionCrossSection.getExternProtoDeclare()); // OK for this to be nested inside Switch
          } // ExternProtoDeclare already found, determine if change goes sooner or later
          else if (locationOriginalExtrusionCrossSection > selectedLocation.docOffsetStart) {
            int returnValue = JOptionPane.showConfirmDialog(documentEditorPane,
                "<html><p align='center'>Move ExternProtoDeclare here from later location?</p><p>Note that ExternProtoDeclare must precede ProtoInstance.</p>",
                "Confirm...", JOptionPane.YES_NO_OPTION);
            if (returnValue == JOptionPane.YES_OPTION) {
              // include ExternProtoDeclare in current insertion
              prototypeInsertion.append(extrusionCrossSection.getExternProtoDeclare()); // OK for this to be nested inside Switch

              // find second ExternProtoDeclare
              remainingLength = documentLength - locationOriginalExtrusionCrossSection;
              int documentLocationSecondExternProtoDeclare
                  = abstractDocument.getDefaultRootElement().getDocument().getText(locationOriginalExtrusionCrossSection, remainingLength).lastIndexOf("<ExternProtoDeclare");
              // TODO remove second ExternProtoDeclare

            }
          }
          // elseExtrusionCrossSection ExternProtoDeclare already found and precedes current location, no insertion necessary

          prototypeInsertion.append(extrusionCrossSection.getProtoInstance());
          prototypeInsertion.append("  </Switch>\n");
          prototypeInsertion.append("  <!-- ==================== -->\n");

          abstractDocument.insertString(selectedLocation.docOffsetStart, prototypeInsertion.toString(), null);
          handlerInsertionMade = true;
          x3dElement.setAppendExtrusionCrossSection(false); // insertion complete, clear flag
        }
        // =============================================================================================================================
        // Element is now inserted.  Validate resulting document only if user preference is set (on by default)
        if (((newString.length() > 0) || handlerInsertionMade) && X3dOptions.getAutoValidate())
          // Schedule validation task in separate thread
          RequestProcessor.getDefault().post(new ValidateThread(documentEditorPane, "inserted text", true));
      }
      else { // cancelled
        Position startPos = abstractDocument.createPosition(Math.max(0, selectedLocation.docOffsetStart - 1)); // see jdoc for createPosition()
        int startOffset = startPos.getOffset();
        setCaretLocation(startOffset+2);
      }
      X3DPaletteUtilities.getTopComponent(documentEditorPane).requestActive();  // this shows the cursor
    }
    catch (InvocationTargetException itex) {
      String message = itex.getClass().getSimpleName()+": ";
      if      (itex.getLocalizedMessage() != null)
               message += itex.getLocalizedMessage() + " ";
      else if (itex.getMessage() != null)
               message += itex.getMessage() + " ";
      if      (itex.getTargetException().getLocalizedMessage() != null)
               message += itex.getTargetException().getLocalizedMessage();
      else if (itex.getTargetException().getMessage() != null)
               message += itex.getTargetException().getMessage();
      
      NotifyDescriptor nd = new NotifyDescriptor.Message(message);
      nd.setTitle("X3D-Edit program exception");
      DialogDisplayer.getDefault().notify(nd);
      System.err.println("X3D-Edit program exception: " + message);
    }
    catch (Exception ex) {
      // This can be a common error, should be handled here.
      String message = "<html><h3>X3D-Edit program exception</h3>" + 
                       ex.getClass().getSimpleName()+": ";
      if      (ex.getLocalizedMessage() != null)
               message += ex.getLocalizedMessage() + " ";
      else if (ex.getMessage() != null)
               message += ex.getMessage()+ " ";
      
      NotifyDescriptor nd = new NotifyDescriptor.Message(message);
      nd.setTitle("X3D-Edit program exception");
      DialogDisplayer.getDefault().notify(nd);
      System.err.println("X3D-Edit program exception: " + message);
    }
  }

  protected void insertNewString(ElementLocation selectedLocation, String newString, int oldStringLength) throws Exception
  {
    // Remember start and end around insert
    Position startPos = abstractDocument.createPosition(Math.max(0, selectedLocation.docOffsetStart - 1)); // see jdoc for createPosition()
    int startOffset = startPos.getOffset();
    Position endPos = abstractDocument.createPosition(selectedLocation.docOffsetEnd);

    abstractDocument.replace(selectedLocation.docOffsetStart, oldStringLength, newString, null);

     // Reformat
    if (baseDocument != null) { // TODO fix
      //New 6.5 code
      Indent indent = Indent.get(baseDocument);
      indent.lock();
      indent.reindent(startPos.getOffset(), endPos.getOffset());
      indent.unlock();

      //pre-6.5 code:
//      baseDocument.getFormatter().reformatLock();
//      baseDocument.getFormatter().reformat(baseDocument, startPos.getOffset(), endPos.getOffset());
//      baseDocument.getFormatter().reformatUnlock();

      X3DPaletteUtilities.buildJdom(documentEditorPane);  // rebuild after format

     // don't bother selecting the new element.  We want our cursor to be in it, and that negates selection
     // ElementLocation newSelectedLocation = X3DPaletteUtilities.findSelectedElement(documentEditorPane); //findSelectedElement();
     // documentEditorPane.setSelectionStart(newSelectedLocation.docOffsetStart);
     // documentEditorPane.setSelectionEnd(newSelectedLocation.docOffsetEnd);
      setCaretLocation(startOffset + 2);   // place it in the brackets
    }
    if (x3dElement != null)
        x3dElement.postInsert(documentEditorPane);  // inform element object (e.g., BOX, MATERIAL, etc.) that we're done
  }

  /**
   * Put a public face on performAction so I can execute it at will
   * @param activatedNodes
   */
  public void publicPerformAction(Node[] activatedNodes)
  {
    performAction(activatedNodes);
  }  //todo elemLoc always has a jdom element, don't need the jdoc

  private BaseX3DElement x3dElement;

  private String doDialog(org.jdom.Document jdoc, JTextComponent pane, ElementLocation elemLoc)
      throws ClassNotFoundException, NoSuchMethodException,
      InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException
  {
    return doDialog(jdoc, pane, elemLoc, null);
  }

  private String doDialog(org.jdom.Document jdoc, JTextComponent pane, ElementLocation elemLoc, String elemName)
      throws ClassNotFoundException, NoSuchMethodException,
      InstantiationException, IllegalAccessException,
      IllegalArgumentException, InvocationTargetException
  {
    String rootName;
    if (elemName != null)
      rootName = elemName;
    else
      rootName = elemLoc.element./*jdoc.getRootElement().*/getName();

    String objClassName = "org.web3d.x3d.palette.items." + rootName.toUpperCase();
    @java.lang.SuppressWarnings(value = "unchecked")
    Class<? extends BaseX3DElement> dobjCls = (Class<? extends BaseX3DElement>) Class.forName(objClassName);
    Constructor<? extends BaseX3DElement> dobjConstr = dobjCls.getDeclaredConstructor();

    x3dElement = dobjConstr.newInstance();
    x3dElement.setLocation(elemLoc);
    x3dElement.initializeFromJdom(/*elemLoc.element,*/jdoc.getRootElement(), pane); // the first is an in-dtd-context element, w/ default attrs
    // expanded; not what we want; it fills out all attributes when written out.
    Class<? extends BaseCustomizer> bcustCls = x3dElement.getCustomizer();
    Method custFactMethod = x3dElement.getCustomizerFactoryMethod();

    BaseCustomizer cust = null;
    if (bcustCls != null) {
      Constructor<? extends BaseCustomizer> custConstr;
      try {
        // try the three argument constructor first
        custConstr = bcustCls.getDeclaredConstructor(x3dElement.getClass(), JTextComponent.class, X3DDataObject.class);
        cust = custConstr.newInstance(x3dElement, pane, x3dDataObject);
      }
      catch (NoSuchMethodException ex) {
        // fall back to the 2 argument constructor
        custConstr = bcustCls.getDeclaredConstructor(x3dElement.getClass(), JTextComponent.class);
        cust = custConstr.newInstance(x3dElement, pane); // todo, lose the text component requirement;
      }
    }
    else if (custFactMethod != null) {
      cust = (BaseCustomizer) custFactMethod.invoke(null, x3dElement, pane);
    }

    if (cust != null) {
      // note: set dragDrop=false and showNewLineOption=false when editing an element in place, since result goes in same place as originally selected text
      BaseCustomizer.DialogReturnEnumeration retrn = cust.showDialog(NbBundle.getMessage(getClass(), "LBL_Customizer_EditPrefix"), false, false);

      String pre = "";
      String post = "";
      switch (retrn) {
        case ACCEPT_PREPEND_LINEFEED:
          pre = linesep;
          break;
        case ACCEPT_APPEND_LINEFEED:
          post = linesep;
          break;
        case ACCEPT_BOTH_LINEFEEDS:
          pre = linesep;
          post = linesep;
          break;
      }

      if (retrn != BaseCustomizer.DialogReturnEnumeration.CANCEL)
      {
        return pre + x3dElement.createBody() + post;
      }
    }

    return null;
  }
  /* The array and 2 methods below are to support the idea of selecting an enclosing
   * element to edit when the cursor is on an inner one.  The first of these type supported here
   * is the "field" element, which when enclosed in a Script or ProtoDeclare element,
   * brings up that "parent" node editor instead.  Script is the direct parent of field, but
   * ProtoDeclare is the grandparent of field, with ProtoInterface in between, so Script is
   * is at array entry 1, ProtoDeclare at 2.  Each of the levels in the array may have more than
   * one entry: e.g.: {SCRIPT_ELNAME,OTHERNODE_ELNAME}
   *
   * To add other special nodes besides field, build another array and check for
   * it in foundSpecialElement().
   */
  private static final Map<String, String[][]> specialMap;


  static {
//    String[][] fieldParents = new String[][]{
//      {}, // nothing at 0
//      {SCRIPT_ELNAME, EXTERNPROTODECLARE_ELNAME}, // Script and EXt.. at 1 back
//      {PROTODECLARE_ELNAME} // ProtoDeclare at 2 back
//    };

//    String[][] fieldValueParents = new String[][]{
//      {},
//      {PROTOINSTANCE_ELNAME} // ProtoInstance at 1 back
//    };

//    String[][] protoInterfaceOrBodyParents = new String[][]{
//      {},
//      {PROTODECLARE_ELNAME} // ProtoDeclare at 1 back
//    };

    specialMap = new HashMap<>();
    //No longer skipping these interfaces, panes are now provided
    //specialMap.put(FIELD_ELNAME, fieldParents);
    //specialMap.put(FIELDVALUE_ELNAME, fieldValueParents);
    //specialMap.put(PROTOINTERFACE_ELNAME, protoInterfaceOrBodyParents);
    //specialMap.put(PROTOBODY_ELNAME, protoInterfaceOrBodyParents);
  }

  private String[][] foundSpecialElement(String nm)
  {
    return specialMap.get(nm);
  }

  ElementLocation changeIfSpecialCase(ElementLocation loc)
  {
    String[][] specArr = foundSpecialElement(loc.name);
    if (specArr != null)
      big:
      {
        ElementLocation tmpLoc = loc;
        for (int nstLv = 1; nstLv < specArr.length; nstLv++) {
          tmpLoc = tmpLoc.parent;
          if (tmpLoc == null)
            break big;
          String[] sa = specArr[nstLv];
          if (sa != null) {
            for (String s : sa) {
              if (s.equals(tmpLoc.name)) {
                loc = tmpLoc;
                break big;
              }
            }
          }
        }
      }

    return loc;
  }
}

