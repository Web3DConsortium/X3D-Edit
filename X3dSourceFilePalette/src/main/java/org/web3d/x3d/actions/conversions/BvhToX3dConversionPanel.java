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

package org.web3d.x3d.actions.conversions;

import java.awt.Dialog;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.filesystems.FileObject;
import org.web3d.x3d.palette.items.UrlCustomizerPanel;

/**
 *
 * @author brutzman
 */

public class BvhToX3dConversionPanel extends javax.swing.JPanel {
    
    private final UrlCustomizerPanel urlCustomizerPanel = new UrlCustomizerPanel();

    private static FileObject masterFo;

    private final BvhToX3dConversionAction bvhToX3dConversionAction;
    /**
     * Creates new form BvhToX3dConversionPanel
     * @param bvhToX3dConversionAction action class of interest
     */
    public BvhToX3dConversionPanel(BvhToX3dConversionAction bvhToX3dConversionAction) { // , X3DDataObject x3dDataObject
        
        this.bvhToX3dConversionAction = bvhToX3dConversionAction;
        
//        masterFo = x3dDataObject.getPrimaryFile();
        
        initComponents();
        
        loadValuesInPanel (); // must follow componenent intialization
		
		bvhToX3dConversionAction.setIncludeVisualizationShapes(includeVisualizationShapesCheckBox.isSelected());
    }
    
    protected final void loadValuesInPanel ()
    {
//                 indentEnabledCheckBox.setSelected(Boolean.valueOf(bvhToX3dConversionAction.getIndentEnabled()));
//        stripDefaultAttributesCheckBox.setSelected(Boolean.valueOf(bvhToX3dConversionAction.getStripDefaultAttributes()));
//                 stripCommentsCheckBox.setSelected(Boolean.valueOf(bvhToX3dConversionAction.getStripComments()));
//                    sourceTextComboBox.setSelectedItem((bvhToX3dConversionAction.getSourceText()));
//                traceEnabledCheckBox.setSelected(Boolean.valueOf(bvhToX3dConversionAction.getTraceEnabled()));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        bvhDescriptionLabel = new javax.swing.JLabel();
        bvhFileAddressTextField = new javax.swing.JTextField();
        chooserButton = new javax.swing.JButton();
        loadButton = new javax.swing.JButton();
        saveBvhButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        bvhTextArea = new javax.swing.JTextArea();
        stripCommentsCheckBox = new javax.swing.JCheckBox();
        stripDefaultAttributesCheckBox = new javax.swing.JCheckBox();
        indentEnabledCheckBox = new javax.swing.JCheckBox();
        jSeparator1 = new javax.swing.JSeparator();
        sourceTextLabel = new javax.swing.JLabel();
        sourceTextComboBox = new javax.swing.JComboBox();
        stripCommentsLabel = new javax.swing.JLabel();
        stripDefaultAttributesLabel = new javax.swing.JLabel();
        indentEnabledLabel = new javax.swing.JLabel();
        includeVisualizationShapesCheckBox = new javax.swing.JCheckBox();

        setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(BvhToX3dConversionPanel.class, "BvhToX3dConversionPanel.border.title"))); // NOI18N
        setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(bvhDescriptionLabel, org.openide.util.NbBundle.getMessage(BvhToX3dConversionPanel.class, "BvhToX3dConversionPanel.bvhDescriptionLabel.text")); // NOI18N
        bvhDescriptionLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        bvhDescriptionLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bvhDescriptionLabel, gridBagConstraints);

        bvhFileAddressTextField.setText(org.openide.util.NbBundle.getMessage(BvhToX3dConversionPanel.class, "BvhToX3dConversionPanel.bvhFileAddressTextField.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 300;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 4.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bvhFileAddressTextField, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(chooserButton, org.openide.util.NbBundle.getMessage(BvhToX3dConversionPanel.class, "BvhToX3dConversionPanel.chooserButton.text")); // NOI18N
        chooserButton.setToolTipText(org.openide.util.NbBundle.getMessage(BvhToX3dConversionPanel.class, "BvhToX3dConversionPanel.chooserButton.toolTipText")); // NOI18N
        chooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(chooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(loadButton, org.openide.util.NbBundle.getMessage(BvhToX3dConversionPanel.class, "BvhToX3dConversionPanel.loadButton.text")); // NOI18N
        loadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(loadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(saveBvhButton, org.openide.util.NbBundle.getMessage(BvhToX3dConversionPanel.class, "BvhToX3dConversionPanel.saveBvhButton.text")); // NOI18N
        saveBvhButton.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(saveBvhButton, gridBagConstraints);

        bvhTextArea.setColumns(20);
        bvhTextArea.setRows(5);
        jScrollPane1.setViewportView(bvhTextArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 300;
        gridBagConstraints.ipady = 600;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jScrollPane1, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(stripCommentsCheckBox, org.openide.util.NbBundle.getMessage(BvhToX3dConversionPanel.class, "BvhToX3dConversionPanel.stripCommentsCheckBox.text")); // NOI18N
        stripCommentsCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        stripCommentsCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stripCommentsCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(stripCommentsCheckBox, gridBagConstraints);

        stripDefaultAttributesCheckBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(stripDefaultAttributesCheckBox, org.openide.util.NbBundle.getMessage(BvhToX3dConversionPanel.class, "BvhToX3dConversionPanel.stripDefaultAttributesCheckBox.text")); // NOI18N
        stripDefaultAttributesCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        stripDefaultAttributesCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stripDefaultAttributesCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(stripDefaultAttributesCheckBox, gridBagConstraints);

        indentEnabledCheckBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(indentEnabledCheckBox, org.openide.util.NbBundle.getMessage(BvhToX3dConversionPanel.class, "BvhToX3dConversionPanel.indentEnabledCheckBox.text")); // NOI18N
        indentEnabledCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        indentEnabledCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                indentEnabledCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(indentEnabledCheckBox, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        add(jSeparator1, gridBagConstraints);

        sourceTextLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(sourceTextLabel, org.openide.util.NbBundle.getMessage(BvhToX3dConversionPanel.class, "BvhToX3dConversionPanel.sourceTextLabel.text")); // NOI18N
        sourceTextLabel.setToolTipText(org.openide.util.NbBundle.getMessage(BvhToX3dConversionPanel.class, "BvhToX3dConversionPanel.sourceTextLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(sourceTextLabel, gridBagConstraints);

        sourceTextComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "escaped", "strings", "plaintext" }));
        sourceTextComboBox.setSelectedIndex(1);
        sourceTextComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sourceTextComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(sourceTextComboBox, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(stripCommentsLabel, org.openide.util.NbBundle.getMessage(BvhToX3dConversionPanel.class, "BvhToX3dConversionPanel.stripCommentsLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(stripCommentsLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(stripDefaultAttributesLabel, org.openide.util.NbBundle.getMessage(BvhToX3dConversionPanel.class, "BvhToX3dConversionPanel.stripDefaultAttributesLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(stripDefaultAttributesLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(indentEnabledLabel, org.openide.util.NbBundle.getMessage(BvhToX3dConversionPanel.class, "BvhToX3dConversionPanel.indentEnabledLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(indentEnabledLabel, gridBagConstraints);

        includeVisualizationShapesCheckBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(includeVisualizationShapesCheckBox, org.openide.util.NbBundle.getMessage(BvhToX3dConversionPanel.class, "BvhToX3dConversionPanel.includeVisualizationShapesCheckBox.text")); // NOI18N
        includeVisualizationShapesCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(BvhToX3dConversionPanel.class, "BvhToX3dConversionPanel.includeVisualizationShapesCheckBox.toolTipText")); // NOI18N
        includeVisualizationShapesCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                includeVisualizationShapesCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(includeVisualizationShapesCheckBox, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    
    private void stripCommentsCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stripCommentsCheckBoxActionPerformed
//        bvhToX3dConversionAction.setStripComments(Boolean.toString(stripCommentsCheckBox.isSelected()));
    }//GEN-LAST:event_stripCommentsCheckBoxActionPerformed

    private void sourceTextComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sourceTextComboBoxActionPerformed
//        bvhToX3dConversionAction.setSourceText(sourceTextComboBox.getSelectedItem().toString());
    }//GEN-LAST:event_sourceTextComboBoxActionPerformed

    private void stripDefaultAttributesCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stripDefaultAttributesCheckBoxActionPerformed
//        bvhToX3dConversionAction.setStripDefaultAttributes(Boolean.toString(stripDefaultAttributesCheckBox.isSelected()));
    }//GEN-LAST:event_stripDefaultAttributesCheckBoxActionPerformed

    private void indentEnabledCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_indentEnabledCheckBoxActionPerformed
//        bvhToX3dConversionAction.setIndentEnabled(Boolean.toString(indentEnabledCheckBox.isSelected()));
    }//GEN-LAST:event_indentEnabledCheckBoxActionPerformed

    private void chooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooserButtonActionPerformed
        String urlString = bvhFileAddressTextField.getText().trim(); // getUrlString ()

        showEditDialog(urlString);
    }//GEN-LAST:event_chooserButtonActionPerformed

    private void includeVisualizationShapesCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_includeVisualizationShapesCheckBoxActionPerformed
        bvhToX3dConversionAction.setIncludeVisualizationShapes(includeVisualizationShapesCheckBox.isSelected());
    }//GEN-LAST:event_includeVisualizationShapesCheckBoxActionPerformed

    private void loadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadButtonActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_loadButtonActionPerformed

  protected String showEditDialog(String urlString)
  {
    if  (!urlString.isEmpty())
         urlCustomizerPanel.setData(urlString);
//    else urlCustomizerPanel.setMasterDocumentLocation(masterFo);

    String DIALOG_SELECTION_TITLE = "Select BVH file for conversion into .x3d";
    
    urlCustomizerPanel.setFormatChoice("bvh");

    DialogDescriptor dialogDescriptor = new DialogDescriptor(urlCustomizerPanel, DIALOG_SELECTION_TITLE);
    dialogDescriptor.setTitle(DIALOG_SELECTION_TITLE);

    Dialog dialog = null;
    try 
    {
      dialog = DialogDisplayer.getDefault().createDialog(dialogDescriptor);
      dialog.setTitle(DIALOG_SELECTION_TITLE);
      dialog.setResizable(true);
//      dlg.setMinimumSize(new Dimension(700,250));
//      //dlg.setMaximumSize(new Dimension(300,200));
      dialog.pack();
      dialog.setVisible(true);
    }
    finally
	{
      if (dialog != null)
          dialog.dispose();
    }
    while (!dialogDescriptor.getValue().equals(DialogDescriptor.CANCEL_OPTION))
    {
      // adjust file chooser if appropriate
      String resourcePath = urlCustomizerPanel.getData();
      
      if (resourcePath.startsWith("file://") || !resourcePath.contains("://"))
      {
          bvhToX3dConversionAction.setBvhLocalFile(true);
          bvhToX3dConversionAction.setBvhLocalPath(resourcePath);
	  return urlCustomizerPanel.getData(); // successful choice (or data entry) found
      }
      else if (resourcePath.length() > 0)
      {
          bvhToX3dConversionAction.setBvhLocalFile(false);
          bvhToX3dConversionAction.setBvhUrlAddress(resourcePath);
	  return urlCustomizerPanel.getData(); // successful choice (or data entry) found
      }
	  // continue until user cancels or chooses
    }
    return ""; // no choice, not found
  }
  public void setBvhSource (String bvhSource)
  {
      bvhTextArea.setText(bvhSource);
  }
  public String getBvhSource ()
  {
      return bvhTextArea.getText();
  }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bvhDescriptionLabel;
    private javax.swing.JTextField bvhFileAddressTextField;
    private javax.swing.JTextArea bvhTextArea;
    private javax.swing.JButton chooserButton;
    private javax.swing.JCheckBox includeVisualizationShapesCheckBox;
    private javax.swing.JCheckBox indentEnabledCheckBox;
    private javax.swing.JLabel indentEnabledLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton loadButton;
    private javax.swing.JButton saveBvhButton;
    private javax.swing.JComboBox sourceTextComboBox;
    private javax.swing.JLabel sourceTextLabel;
    private javax.swing.JCheckBox stripCommentsCheckBox;
    private javax.swing.JLabel stripCommentsLabel;
    private javax.swing.JCheckBox stripDefaultAttributesCheckBox;
    private javax.swing.JLabel stripDefaultAttributesLabel;
    // End of variables declaration//GEN-END:variables

}
