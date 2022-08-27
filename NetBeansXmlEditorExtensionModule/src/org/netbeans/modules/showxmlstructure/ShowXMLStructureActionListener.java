/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/NetBeansModuleDevelopment-files/contextAction.java to edit this template
 */
package org.netbeans.modules.showxmlstructure;

import org.openide.awt.ActionReferences;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.cookies.EditorCookie;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.openide.xml.XMLUtil;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

@ActionID(
        category = "Edit",
        id = "org.netbeans.modules.showxmlstructure.ShowXMLStructureActionListener"
)
@ActionRegistration(
        displayName = "#CTL_ShowXMLStructureActionListener"
)
@ActionReferences({
    @ActionReference(path = "Menu/File", position = 0),
    @ActionReference(path = "Editors/text/xml/Popup", position = 1100)
})
@Messages("CTL_ShowXMLStructureActionListener=Show XML Structure")
public final class ShowXMLStructureActionListener implements ActionListener {

    private final EditorCookie context;

    public ShowXMLStructureActionListener(EditorCookie context) {
        this.context = context;
    }

    @Override
    public void actionPerformed(ActionEvent ev) {
        // https://netbeans.apache.org/tutorials/nbm-xmleditor.html

        // "XML Structure" tab is created in Output Window for writing the list of tags:
        InputOutput io = IOProvider.getDefault().getIO(Bundle.CTL_ShowXMLStructureActionListener(), false);
        io.select(); //"XML Structure" tab is selected
        try {
            //Use the NetBeans org.openide.xml.XMLUtil class to create a org.w3c.dom.Document:
            try ( //Get the InputStream from the EditorCookie:
                    
                InputStream is = ((org.openide.text.CloneableEditorSupport) context).getInputStream()) {
                //Use the NetBeans org.openide.xml.XMLUtil class to create a org.w3c.dom.Document:
                Document doc =  XMLUtil.parse(new InputSource(is), true, true, null, null);
                //Create a list of nodes, for all the elements:
                NodeList list = doc.getElementsByTagName("*");
                //Iterate through the list:
                for (int i = 0; i < list.getLength(); i++) {
                    //For each node in the list, create a org.w3c.dom.Node:
                    org.w3c.dom.Node mainNode = list.item(i);
                    //Create a map for all the attributes of the org.w3c.dom.Node:
                    NamedNodeMap map = mainNode.getAttributes();
                    //Get the name of the node:
                    String nodeName = mainNode.getNodeName();
                    //Create a StringBuilder for the Attributes of the Node:
                    StringBuilder attrBuilder = new StringBuilder();
                    //Iterate through the map of attributes:
                    for (int j = 0; j < map.getLength(); j++) {
                        //Each iteration, create a new Node:
                        org.w3c.dom.Node attrNode = map.item(j);
                        //Get the name of the current Attribute:
                        String attrName = attrNode.getNodeName();
                        //Add the current Attribute to the StringBuilder:
                        attrBuilder.append("*").append(attrName).append(" ");
                    }
                    //Print the element and its attributes to the Output window:
                    io.getOut().println("ELEMENT: " + nodeName
                            + " --> ATTRIBUTES: " + attrBuilder.toString());
                }
                //Close the InputStream:
            }
        } catch (SAXException | IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
}
