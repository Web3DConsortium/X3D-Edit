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
import org.web3d.x3d.palette.X3DPaletteUtilities;
import org.web3d.x3d.palette.X3DPaletteUtilities.ElementLocation;
import org.web3d.x3d.palette.X3DPaletteUtilities.ValidateThread;
import org.web3d.x3d.palette.items.NodeListPanel;
import org.xml.sax.SAXException;

@ActionID(id = "org.web3d.x3d.RenameElementAction", category = "Edit")
@ActionRegistration(displayName = "#CTL_RenameElementAction", 
                    lazy=true) // don't do lazy=false since iconBase no longer gets registered)
@ActionReferences( value = {
  @ActionReference(path = "Menu/X3D-Edit/Edit Element XML", position = 750),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/Edit Element XML", position = 750)
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
      ElementLocation selectedLocation = X3DPaletteUtilities.findSelectedElement(documentEditorPane); //findSelectedElement();
      highlightSelectedElement(selectedLocation);

      int textBlockLength = selectedLocation.docOffsetEnd - selectedLocation.docOffsetStart;
      String selectedText = documentEditorPane.getText(selectedLocation.docOffsetStart,textBlockLength);
      org.jdom.Document jdoc = X3DPaletteUtilities.buildJdomFromString(selectedText);

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

