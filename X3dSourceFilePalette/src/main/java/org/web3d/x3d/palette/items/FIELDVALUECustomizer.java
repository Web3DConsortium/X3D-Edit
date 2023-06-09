/*
Copyright (c) 1995-2022 held by the author(s).  All rights reserved.
 
Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:
 
 * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer
      in the documentation and/or other materials provided with the
      distribution.
 * Neither the names of the Naval Postgraduate School (NPS)
      Modeling Virtual Environments and Simulation (MOVES) Institute
      (https://www.nps.edu and https://MovesInstitute.nps.edu)
      nor the names of its contributors may be used to endorse or
      promote products derived from this software without specific
      prior written permission.
 
THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.
 */

package org.web3d.x3d.palette.items;

import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.text.JTextComponent;
import org.jdom.Element;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.HelpCtx;
import static org.web3d.x3d.palette.X3DPaletteUtilities.escapeXmlCharacters;
import static org.web3d.x3d.types.X3DSchemaData.*;


/**
 * CYLINDERCustomizer.java
 * Created on July 12, 2007, 3:36 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey and Don Brutzman
 * @version $Id$
 */
public class FIELDVALUECustomizer extends BaseCustomizer
{
  private final JTextComponent target;
  private final FIELDVALUE fieldValue;
  private Vector<FieldPan> fieldPanels;
  private int selection = 0;

  private final String[] fieldNames = {  }; // "TBD list of field names from ProtoDeclare or ExternProtoDeclare"
  
  /** Creates new form CYLINDERCustomizer */
  public FIELDVALUECustomizer(FIELDVALUE fieldValue, JTextComponent target)
  {
    super(fieldValue);
    this.target = target;
    this.fieldValue = fieldValue;
    
    HelpCtx.setHelpIDString(FIELDVALUECustomizer.this, "FIELDVALUE_ELEM_HELPID");
    
    initComponents();

    initializeFieldNameComboBox();

    valueTextArea.setText(fieldValue.getValue());
    
    this.addAncestorListener(new AncestorListener()
    {
      @Override
      public void ancestorAdded(AncestorEvent event)
      {
        FIELDVALUECustomizer.this.getRootPane().setDefaultButton(null);
        valueTextArea.requestFocusInWindow();
      }
      @Override
      public void ancestorMoved(AncestorEvent event){}
      @Override
      public void ancestorRemoved(AncestorEvent event){}
    });
  }
  
  @SuppressWarnings("unchecked")
  private void initializeFieldNameComboBox()
  { 
    String[] protoNameType = new String[]{"someFieldName",""};
    Vector<Element> fields = fieldValue.getFieldsList(target,protoNameType);
    fieldPanels = new Vector<>(fields.size());
    int i=0;
    for(Element elm : fields) {
      String s = elm.getAttributeValue(FIELD_ATTR_NAME_NAME);
      String fieldName = (s==null?"":s);
      s = elm.getAttributeValue(FIELD_ATTR_TYPE_NAME);
      String fieldType = (s==null?"":s);
      s = elm.getAttributeValue(FIELD_ATTR_ACCESSTYPE_NAME);
      String fieldAccessType = (s==null?"":s);
      if (elm.getAttributeValue(FIELD_ATTR_ACCESSTYPE_NAME).equalsIgnoreCase("initializeOnly") ||
          elm.getAttributeValue(FIELD_ATTR_ACCESSTYPE_NAME).equalsIgnoreCase("inputOutput"))
      {
          fieldPanels.add(new FieldPan(fieldName, fieldType, fieldAccessType));
          if (fieldName.equals(fieldValue.getName()))
          {
              selection = i;
          }
          i++;
      }
    }

    fieldNameComboBox.setModel(new DefaultComboBoxModel(fieldPanels)); // this is an "unchecked conversion"
    if(fieldPanels.size()>0)
      fieldNameComboBox.setSelectedIndex(selection);
    
    protoInstanceTypeLabel.setText(protoNameType[1]);
    protoInstanceNameLabel.setText("<html><b>"+protoNameType[0]);

    fieldNameComboBox.setRenderer(new FieldRenderer(null));
    fieldNameComboBox.setEditor(new FieldRenderer.FieldEditor());
  }
  
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        nameLabel = new javax.swing.JLabel();
        valueLabel = new javax.swing.JLabel();
        fieldNameComboBox = new javax.swing.JComboBox<>();
        valueScrollPane = new javax.swing.JScrollPane();
        valueTextArea = new javax.swing.JTextArea();
        protoInstanceTypeLabel = new javax.swing.JLabel();
        protoInstanceHeaderNameLabel = new javax.swing.JLabel();
        protoInstanceNameLabel = new javax.swing.JLabel();
        protoInstanceFieldValueLabel = new javax.swing.JLabel();
        hintLabel = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(660, 300));
        setLayout(new java.awt.GridBagLayout());

        nameLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        nameLabel.setText("name");
        nameLabel.setToolTipText("name of mapped field in parent ProtoInterface");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 6, 0, 0);
        add(nameLabel, gridBagConstraints);

        valueLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        valueLabel.setText("value");
        valueLabel.setToolTipText("initialization value, must match type");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(valueLabel, gridBagConstraints);

        fieldNameComboBox.setEditable(true);
        fieldNameComboBox.setModel(new DefaultComboBoxModel<String>(fieldNames));
        fieldNameComboBox.setToolTipText("can only override fields with accessType initializeOnly or inputOutput");
        fieldNameComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldNameComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.ipadx = 644;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(13, 6, 0, 0);
        add(fieldNameComboBox, gridBagConstraints);

        valueTextArea.setColumns(20);
        valueTextArea.setRows(5);
        valueTextArea.setToolTipText("initialization value, must match type");
        valueScrollPane.setViewportView(valueTextArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 700;
        gridBagConstraints.ipady = 92;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 0);
        add(valueScrollPane, gridBagConstraints);

        protoInstanceTypeLabel.setText("ProtoDeclare or ExternalProtoDeclare");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(protoInstanceTypeLabel, gridBagConstraints);

        protoInstanceHeaderNameLabel.setText("name: ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 0, 0);
        add(protoInstanceHeaderNameLabel, gridBagConstraints);

        protoInstanceNameLabel.setText("null");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 488;
        gridBagConstraints.ipady = -2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(7, 6, 0, 0);
        add(protoInstanceNameLabel, gridBagConstraints);

        protoInstanceFieldValueLabel.setText("ProtoInstance fieldValue:");
        protoInstanceFieldValueLabel.setToolTipText("fieldValue initialization overrides default values");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(18, 6, 0, 0);
        add(protoInstanceFieldValueLabel, gridBagConstraints);

        hintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel.setText("<html><p align=\"center\">A <b>fieldValue</b> statement re-initializes the default value of a field in a parent <b>ProtoInstance</b> node.</p>\n<br />\n<p align=\"center\">Note that a <b>field</b> statement with a matching name must be already defined by the \n<br /> corresponding <b>ProtoDeclare</b> or <b>ExternProtoDeclare</b> statement. </p>\n");
        hintLabel.setToolTipText(org.openide.util.NbBundle.getMessage(FIELDVALUECustomizer.class, "INTEGERSEQUENCERCustomizer.eventLabel3.toolTipText")); // NOI18N
        hintLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        hintLabel.setPreferredSize(new java.awt.Dimension(770, 620));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(hintLabel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

  private String  lastValue="";
  private boolean lastWasNode = false;
  private final String sfNodeWarning = "No value attribute, add child node as appropriate";
  private final String mfNodeWarning = "No value attribute, add child node(s) as appropriate";
  
  private void fieldNameComboBoxActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_fieldNameComboBoxActionPerformed
  {//GEN-HEADEREND:event_fieldNameComboBoxActionPerformed
    FieldPan fp = (FieldPan)fieldNameComboBox.getSelectedItem();
    if (selection != fieldNameComboBox.getSelectedIndex())
    {
        selection = fieldNameComboBox.getSelectedIndex();
        valueTextArea.setText("");
    }
    String fieldType = fp.getFieldType();
    boolean isNode = fieldType.equals("SFNode") || fieldType.equals("MFNode");
    if(isNode) {
      if(!lastWasNode) {        
        String warning = (fieldType.equals("SFNode")?sfNodeWarning:mfNodeWarning);
        lastValue = valueTextArea.getText();
        valueTextArea.setText(warning);
        lastWasNode = true;
      }
    }
    else { // not node
      if(lastWasNode) { // was last a node?
        valueTextArea.setText(lastValue);
        lastWasNode = false;
      }
    }
    valueTextArea.setEnabled(!isNode);
    valueLabel.setEnabled(!isNode);
    ensureRequiredValueSet();
}//GEN-LAST:event_fieldNameComboBoxActionPerformed
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> fieldNameComboBox;
    private javax.swing.JLabel hintLabel;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel protoInstanceFieldValueLabel;
    private javax.swing.JLabel protoInstanceHeaderNameLabel;
    private javax.swing.JLabel protoInstanceNameLabel;
    private javax.swing.JLabel protoInstanceTypeLabel;
    private javax.swing.JLabel valueLabel;
    private javax.swing.JScrollPane valueScrollPane;
    private javax.swing.JTextArea valueTextArea;
    // End of variables declaration//GEN-END:variables
  @Override
  public String getNameKey()
  {
    return "NAME_X3D_FIELDVALUE";
  }
  
  @Override
  public void unloadInput() throws IllegalArgumentException
  {
    FieldPan fp = (FieldPan)fieldNameComboBox.getSelectedItem();

    ensureRequiredValueSet ();
    
    fieldValue.setName(fp.toString().trim());
    if(valueTextArea.isEnabled())
      fieldValue.setValue(escapeXmlCharacters(valueTextArea.getText().trim()));
    else {
      fieldValue.setValue("");
      // Here's where an SFNode or MFNode is chosen
      // Want to write out <fieldValue....></fieldValue> instead of <fieldValue.../>
      // If there is non-zero content in the node, we get the opening and closing-- what we want.
      // Make sure it happens:
      if(fieldValue.getContent()== null || fieldValue.getContent().length()<=0)
        fieldValue.setContent(newLine);
    }
  } 
  private void ensureRequiredValueSet ()
  {
            if (fieldPanels == null || fieldPanels.size()<=0)
            {
                System.out.println ("fieldPanels null :("); // TODO fix for problematic drag/drop crash, not sure of cause
                return;
            }
            if (fieldPanels.elementAt(selection) == null)
            {
                System.out.println ("fieldPanels.elementAt(selection) null :("); // TODO fix drag/drop crash
                return;
            }
            if      (fieldPanels.elementAt(selection).getFieldType().equals("SFBool") && valueTextArea.getText().trim().equals("TRUE"))
            {
                  valueTextArea.setText("true");
                  NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                     "<html><p align='center'>Illegal initialization value found for SFBool fieldValue <b>" + fieldValue.getName() +
                     "</b>, reset TRUE to <b>true</b></p>" +
                     "<br /> <p align='center'>(Note that upper-case &apos;TRUE&apos; is ClassicVRML .x3dv syntax, lower-case &apos;true&apos; is XML .x3d syntax)</p>", NotifyDescriptor.WARNING_MESSAGE);
                  DialogDisplayer.getDefault().notify(descriptor);
                  return;
            }
            else if (fieldPanels.elementAt(selection).getFieldType().equals("SFBool") && valueTextArea.getText().trim().equals("FALSE"))
            {
                  valueTextArea.setText("false");
                  NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                     "<html><p align='center'>Illegal initialization value found for SFBool fieldValue <b>" + fieldValue.getName() +
                     "</b>, reset FALSE to <b>false</b></p>" +
                     "<br /> <p align='center'>(Note that upper-case &apos;FALSE&apos; is ClassicVRML .x3dv syntax, lower-case &apos;false&apos; is XML .x3d syntax)</p>", NotifyDescriptor.WARNING_MESSAGE);
                  DialogDisplayer.getDefault().notify(descriptor);
                  return;
            }
            else if (fieldPanels.elementAt(selection).getFieldType().equals("SFBool") &&
                     (valueTextArea.getText().trim().equals("true") || valueTextArea.getText().trim().equals("false")))
            {
                  return;
            }
            else if (fieldPanels.elementAt(selection).getFieldType().equals("MFString") && (valueTextArea.getText().trim().length() > 0) && !valueTextArea.getText().trim().contains("\"")) 
            {
                valueTextArea.setText("\"" + valueTextArea.getText() + "\"");
                NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                      "<html><p align='center'>fieldValue <b>" + fieldPanels.elementAt(selection).getFieldName()
                      + "</b> of type MFString must have quoted value</p>"
                      + "<br /> <p align='center'>Quotes added to create a proper single-string value</p>", NotifyDescriptor.WARNING_MESSAGE);
                DialogDisplayer.getDefault().notify(descriptor);
            }
            if (valueTextArea.getText().trim().length() > 0) return;

            if      (fieldPanels.elementAt(selection).getFieldType().equalsIgnoreCase("SFString") ||
                     fieldPanels.elementAt(selection).getFieldType().equalsIgnoreCase("SFNode") ||
                     fieldPanels.elementAt(selection).getFieldType().startsWith("MF") ||
                     valueTextArea.getText().trim().startsWith("MF"))
                return; // no initial value required

            if      (fieldPanels.elementAt(selection).getFieldType().equalsIgnoreCase("SFBool"))
                valueTextArea.setText("true");
            else if (fieldPanels.elementAt(selection).getFieldType().equalsIgnoreCase("SFTime"))
                valueTextArea.setText("-1");
            else if (fieldPanels.elementAt(selection).getFieldType().equalsIgnoreCase("SFInt32"))
                valueTextArea.setText("0");
            else if (fieldPanels.elementAt(selection).getFieldType().equalsIgnoreCase("SFFloat") || 
                     fieldPanels.elementAt(selection).getFieldType().equalsIgnoreCase("SFDouble"))
                valueTextArea.setText("0.0");
            else if (fieldPanels.elementAt(selection).getFieldType().equalsIgnoreCase("SFVec2f") || 
                     fieldPanels.elementAt(selection).getFieldType().equalsIgnoreCase("SFVec2d"))
                valueTextArea.setText("0 0");
            else if (fieldPanels.elementAt(selection).getFieldType().equalsIgnoreCase("SFVec3f") || 
                     fieldPanels.elementAt(selection).getFieldType().equalsIgnoreCase("SFVec3d") || 
                     fieldPanels.elementAt(selection).getFieldType().equalsIgnoreCase("SFColor") || 
                     fieldPanels.elementAt(selection).getFieldType().equalsIgnoreCase("SFColorRGBA") || 
                     fieldPanels.elementAt(selection).getFieldType().equalsIgnoreCase("SFImage"))
                valueTextArea.setText("0 0 0");
            else if (fieldPanels.elementAt(selection).getFieldType().equalsIgnoreCase("SFVec4f") || 
                     fieldPanels.elementAt(selection).getFieldType().equalsIgnoreCase("SFVec4d"))
                valueTextArea.setText("0 0 0 1");
            else if (fieldPanels.elementAt(selection).getFieldType().equalsIgnoreCase("SFRotation"))
                valueTextArea.setText("0 1 0 0"); // preferable value, equivalent to spec default 0 0 1 0
            else if (fieldPanels.elementAt(selection).getFieldType().equalsIgnoreCase("SFMatrix3f") || 
                     fieldPanels.elementAt(selection).getFieldType().equalsIgnoreCase("SFMatrix3d"))
                valueTextArea.setText("1 0 0\n0 1 0\n0 0 1");
            else if (fieldPanels.elementAt(selection).getFieldType().equalsIgnoreCase("SFMatrix4f") || 
                     fieldPanels.elementAt(selection).getFieldType().equalsIgnoreCase("SFMatrix4d"))
                valueTextArea.setText("1 0 0 0\n0 1 0 0\n0 0 1 0\n0 0 0 1");
            else return; // no problem

              NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                 "<html><p align='center'>No initialization value found for fieldValue <b>" + fieldNameComboBox.getSelectedItem().toString() +
                 "</b>, reset to " + fieldPanels.elementAt(selection).getFieldType() + " type default value <b>" + valueTextArea.getText() + "</b></p>" +
                 "<br /> <p align='center'>(Note that default values for each fieldValue are defined in original ProtoDeclare field declaration)</p>", NotifyDescriptor.WARNING_MESSAGE);
              DialogDisplayer.getDefault().notify(descriptor);
  }
}
