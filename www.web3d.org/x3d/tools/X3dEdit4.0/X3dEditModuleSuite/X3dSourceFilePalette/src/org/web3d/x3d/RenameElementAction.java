/*
* Copyright (c) 1995-2022 held by the author(s).  All rights reserved.
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
*       (https://www.nps.edu and https://MovesInstitute.nps.edu)
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
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import org.jdom.JDOMException;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.nodes.Node;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;
import org.web3d.x3d.options.X3dOptions;
import org.web3d.x3d.palette.X3DPaletteUtilitiesJdom;
import org.web3d.x3d.palette.X3DPaletteUtilitiesJdom.ElementLocation;
import org.web3d.x3d.palette.X3DPaletteUtilitiesJdom.ValidateThread;
import org.web3d.x3d.palette.items.NodeListPanel;
import org.xml.sax.SAXException;

@ActionID(id = "org.web3d.x3d.RenameElementAction", category = "X3D-Edit")
@ActionRegistration(iconBase = "org/web3d/x3d/palette/items/resources/XML32.png",
                    displayName = "#CTL_RenameElementAction", 
                    lazy=true) // don't do lazy=false since iconBase no longer gets registered)
@ActionReferences( value = {
  @ActionReference(path = "Menu/&X3D-Edit/&Edit Element XML", position = 750),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/&Edit Element XML", position = 750)
})

public final class RenameElementAction extends BaseX3DEditAction //CookieAction
{
  NodeListPanel nodeListPanel = new NodeListPanel();
  protected ElementLocation selectedElement;
  
  @Override
  public String getName()
  {
    return NbBundle.getMessage(getClass(), "CTL_RenameElementAction");
  }

  @Override
  protected void doWork(Node[] activatedNodes)
  {
    try {
      ElementLocation selectedLocation = X3DPaletteUtilitiesJdom.findSelectedElement(documentEditorPane); //findSelectedElement();
      highlightSelectedElement(selectedLocation);

      int textBlockLength = selectedLocation.docOffsetEnd - selectedLocation.docOffsetStart;
      String selectedText = documentEditorPane.getText(selectedLocation.docOffsetStart,textBlockLength);
      org.jdom.Document jdoc = X3DPaletteUtilitiesJdom.buildJdomFromString(selectedText);

      String returnValue = doDialog(jdoc,documentEditorPane);
      if(returnValue != null)
        abstractDocument.replace(selectedLocation.docOffsetStart,textBlockLength,returnValue,null);

      Node n = activatedNodes[0];
      //doValidate(n);
      // Do this validate instead to get a "Undo change" dialog on error
      if (X3dOptions.getAutoValidate())
        // Schedule validation task in separate thread
        RequestProcessor.getDefault().post(new ValidateThread(documentEditorPane, "renamed element", true));
    }
    catch(BadLocationException | IOException | SAXException | JDOMException ex) {  //todo, specific msgs for spec exceptions
      throw new RuntimeException(ex); // let base class handle
    }
  }

  private String doDialog(org.jdom.Document jdoc, JTextComponent pane)
  {
    org.jdom.Element root = jdoc.getRootElement();
//    JPanel pan = new JPanel();
//    pan.setLayout(new BoxLayout(pan,BoxLayout.X_AXIS));
//    pan.setBorder(new EmptyBorder(10,10,0,10));
//    JLabel lab = new JLabel(NbBundle.getBundle(RenameElementAction.class).getString("LBL_Rename_Element_Label"));
//    JTextField tf = new JTextField(15);
//    tf.setText(root.getName());
//    tf.selectAll();
//    pan.add(lab);
//    pan.add(Box.createHorizontalStrut(5));
//    pan.add(tf);
    
    String title = NbBundle.getMessage(RenameElementAction.class, "DIA_Rename_Element_Title");

    nodeListPanel.setSelectedName(root.getName()); // set to initial name
    DialogDescriptor dd = new DialogDescriptor(nodeListPanel,title);
    DialogDisplayer.getDefault().createDialog(dd).setVisible(true);
    if(!dd.getValue().equals(DialogDescriptor.OK_OPTION))
      return null;
    
    // Put in new name, then produce xml
    String nodeName = nodeListPanel.getSelectedName();
    if (nodeName.length() > 0)
    {
        root.setName(nodeName);
        org.jdom.output.XMLOutputter renamedElementBlock = new org.jdom.output.XMLOutputter();
        return renamedElementBlock.outputString(root);    
    }
    else return null;
  }
}

