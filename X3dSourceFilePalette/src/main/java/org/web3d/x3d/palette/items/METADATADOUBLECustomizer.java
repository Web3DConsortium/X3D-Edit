/*
Copyright (c) 1995-2021 held by the author(s).  All rights reserved.
 
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

import javax.swing.JLabel;
import javax.swing.text.JTextComponent;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import static org.web3d.x3d.types.X3DSchemaData.METADATA_CONTAINERFIELD_CHOICES;
import static org.web3d.x3d.types.X3DSchemaData.METADATA_CONTAINERFIELD_TOOLTIPS;

/**
 * METADATADOUBLECustomizer.java
 * Created on March 8, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class METADATADOUBLECustomizer extends BaseCustomizer
{
  private METADATADOUBLE metadataDouble;
  private JTextComponent target;

  public METADATADOUBLECustomizer(METADATADOUBLE metadataDouble, JTextComponent target)
  {
    super(metadataDouble);
    this.metadataDouble = metadataDouble;
    this.target = target;
   
    HelpCtx.setHelpIDString(this, "METADATADOUBLE_ELEM_HELPID");
    
    initComponents();
    
    // TODO the following safety check is likely unnecessary, but likely other checks are (first child, containerField, etc.)
    if ((super.getDEFUSEpanel() != null) && (super.getDEFUSEpanel().getContainerField()) != null) // TODO should this check be in other Metadata* nodes?
    {
        super.getDEFUSEpanel().setContainerFieldChoices (METADATA_CONTAINERFIELD_CHOICES, METADATA_CONTAINERFIELD_TOOLTIPS);
        super.getDEFUSEpanel().setContainerField(metadataDouble.getContainerField()); // reset value to match updated JComboBox data model
        // DEFUSEpanel initialization must NOT be repeated or else array of choices will be overwritten

        if (super.getDEFUSEpanel().getContainerField().equals("metadata"))
            super.getDEFUSEpanel().setUseContainerField(false);
    }
    else
    {
            System.out.println("*** METADATADOUBLECustomizer problem");
    }
    
         nameTextField.setText(metadataDouble.getName());
    referenceTextField.setText(metadataDouble.getReference());

    initializeValueTable();

    setDefaultDEFname ();
  }
  private void setDefaultDEFname ()
  {
    super.getDEFUSEpanel().setDefaultDEFname(NbBundle.getMessage(getClass(),getNameKey()) + nameTextField.getText());
  }

    private void initializeValueTable()
    {
        valueTable.setTitle("value array");
        valueTable.setColumnTitles(new String[]
        {
            "#","Individual double-precision floating-point values"
        });
        valueTable.setNewRowData(new Object[]
        {
            "0.0","0.0" // second value used to add new row
        });
        valueTable.setHeaderTooltip("Array of single or multiple floating-point values");
        valueTable.setColumnToolTips(new String[]{"index","SFDouble floating-point value"});
        valueTable.setTextAlignment(JLabel.TRAILING);
        valueTable.getTable().setRowHeight(16);
        valueTable.doIndexInFirstColumn(true);
        valueTable.setBoldColumn(1);
        valueTable.setData(metadataDouble.getValueArray());

        valueTable.setShowAppendCommasLineBreaks(true);
        valueTable.setInsertCommas    (metadataDouble.isInsertCommas());
        valueTable.setInsertLineBreaks(metadataDouble.isInsertLineBreaks());

        // apparently the following has to come after setting data
        valueTable.getTable().getColumnModel().getColumn(0).setPreferredWidth(500);
    }
  
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel1 = getDEFUSEpanel();
        nameLabel = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        valueTable = new org.web3d.x3d.palette.items.ExpandableList();
        referenceLabel = new javax.swing.JLabel();
        referenceTextField = new javax.swing.JTextField();
        nodeHintPanel = new javax.swing.JPanel();
        hintLabel = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(616, 411));
        setLayout(new java.awt.GridBagLayout());

        dEFUSEpanel1.setMinimumSize(new java.awt.Dimension(198, 77));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel1, gridBagConstraints);

        nameLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        nameLabel.setText("name");
        nameLabel.setToolTipText("metadata attribute name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 8, 3, 3);
        add(nameLabel, gridBagConstraints);

        nameTextField.setToolTipText("metadata attribute name");
        nameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nameTextField, gridBagConstraints);

        valueTable.setToolTipText("Single or multiple double-precision floating-point values");
        valueTable.setColumnTitles(new String[] {"string values"});
        valueTable.setMinimumSize(new java.awt.Dimension(170, 200));
        valueTable.setPreferredSize(new java.awt.Dimension(170, 200));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(valueTable, gridBagConstraints);

        referenceLabel.setText("reference");
        referenceLabel.setToolTipText("reference to metadata standard or definition for this particular metadata value");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 8, 3, 3);
        add(referenceLabel, gridBagConstraints);

        referenceTextField.setToolTipText("reference to metadata standard or definition for this particular metadata value");
        referenceTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                referenceTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(referenceTextField, gridBagConstraints);

        nodeHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nodeHintPanel.setLayout(new java.awt.GridBagLayout());

        hintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel.setText("<html><p align=\"center\"><b>MetadataDouble</b> contains a named, typed list of values providing metadata information about its parent node. </p>");
        hintLabel.setToolTipText("Metadata nodes provide typed information about model characteristics");
        hintLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        nodeHintPanel.add(hintLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nodeHintPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void referenceTextFieldActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_referenceTextFieldActionPerformed
    {//GEN-HEADEREND:event_referenceTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_referenceTextFieldActionPerformed

    private void nameTextFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_nameTextFieldActionPerformed
    {//GEN-HEADEREND:event_nameTextFieldActionPerformed
        setDefaultDEFname ();
    }//GEN-LAST:event_nameTextFieldActionPerformed
  
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel hintLabel;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JPanel nodeHintPanel;
    private javax.swing.JLabel referenceLabel;
    private javax.swing.JTextField referenceTextField;
    private org.web3d.x3d.palette.items.ExpandableList valueTable;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_METADATADOUBLE";
  }

  String[] buildValueArray()
  {
      int numberRows = valueTable.getTable().getModel().getRowCount();
      String[] valueArray = new String[numberRows];
      int actualRowCount = 0;
      for (int row=0; row < numberRows; row++)
      {
          String newValue = new String();
          if            (valueTable.getTable().getModel().getValueAt(row, 1) != null)
              newValue = valueTable.getTable().getModel().getValueAt(row, 1).toString();
          if (checkDoubleValue (row, newValue))
          {
              valueArray[actualRowCount] = newValue;
              actualRowCount++;
          }
          // else ignore null value
      }
      String[] actualArray = new String[actualRowCount];
      System.arraycopy(valueArray, 0, actualArray, 0, actualRowCount);
      if ((actualArray==null) || (actualArray.length == 0) || ((actualArray.length == 0) && (actualArray[0] == null)))
      {
          actualArray = new String[1];
          actualArray[0] = "";
      }
      return actualArray;
  }

  @Override
  public void unloadInput()
  {
     unLoadDEFUSE();
     
     metadataDouble.setName(nameTextField.getText().trim());
     metadataDouble.setReference(referenceTextField.getText().trim());
     metadataDouble.setValueArray(buildValueArray());

     metadataDouble.setInsertCommas    (valueTable.isInsertCommasSet());
     metadataDouble.setInsertLineBreaks(valueTable.isInsertLineBreaksSet());
  }  
}
