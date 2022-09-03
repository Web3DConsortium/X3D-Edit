/*
Copyright (c) 1995-2021 held by the author(s) .  All rights reserved.
 
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
      (http://www.nps.edu and https://MovesInstitute.nps.edu)
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

import java.awt.Color;

/**
 *
 * @author don
 */


public class X3dTidyConversionPanel extends javax.swing.JPanel {

    private final X3dTidyConversionAction x3dTidyConversionAction;
    /**
     * Creates new form X3dTidyConversionPanel
     */
    public X3dTidyConversionPanel(X3dTidyConversionAction x3dTidyConversionAction) {
        
        this.x3dTidyConversionAction = x3dTidyConversionAction;
        
        initComponents();
        
        loadValuesInPanel (); // must follow componenent intialization
    }
    
    protected final void loadValuesInPanel ()
    {
        conversionRequiredCheckBox.setSelected(Boolean.valueOf(x3dTidyConversionAction.getConversionRequired()));
//        title;
        modifyX3dVersionCheckBox.setSelected(Boolean.valueOf(x3dTidyConversionAction.getModifyX3dVersion()));
        revisedX3dVersionComboBox.setSelectedItem((x3dTidyConversionAction.getRevisedX3dVersion()));
        fixDateFormatsCheckBox.setSelected(Boolean.valueOf(x3dTidyConversionAction.getFixDateFormats()));
        fixMFStringQuotesCheckBox.setSelected(Boolean.valueOf(x3dTidyConversionAction.getFixMFStringQuotes()));
        fixGeoSystemMetadataCheckBox.setSelected(Boolean.valueOf(x3dTidyConversionAction.getFixGeoSystemMetadata()));
        fixMetaNamesMatchDublinCoreCheckBox.setSelected(Boolean.valueOf(x3dTidyConversionAction.getFixMetaNamesMatchDublinCore()));
        replaceBlackEmissiveColorCheckBox.setSelected(Boolean.valueOf(x3dTidyConversionAction.getReplaceBlackEmissiveColor()));
  
        fixUrlAdditionHttpAddressesCheckBox.setSelected(Boolean.valueOf(x3dTidyConversionAction.getFixUrlAdditionHttpAddresses()));
  
        appendWrlAfterX3dAddressesCheckBox.setSelected(Boolean.valueOf(x3dTidyConversionAction.getAppendWrlAfterX3dAddresses()));
        prependX3dBeforeWrlAddressesCheckBox.setSelected(Boolean.valueOf(x3dTidyConversionAction.getPrependX3dBeforeWrlAddresses()));
        defaultUrlAddressTextField.setText(x3dTidyConversionAction.getDefaultUrlAddress());
  
        changeJavascriptEcmascriptCheckBox.setSelected(Boolean.valueOf(x3dTidyConversionAction.getChangeJavascriptEcmascript()));
        insertMissingEcmascriptCheckBox.setSelected(Boolean.valueOf(x3dTidyConversionAction.getInsertMissingEcmascript()));
        insertMissingMetaLicenseCheckBox.setSelected(Boolean.valueOf(x3dTidyConversionAction.getInsertMissingEcmascript()));
        licenseLinkTextField.setText(x3dTidyConversionAction.getLicenseLink());
          
        HAnimGeometryRemoveCheckBox.setSelected(Boolean.valueOf(x3dTidyConversionAction.getHAnimGeometryRemove()));
        HAnimSkeletonIllustrateCheckBox.setSelected(Boolean.valueOf(x3dTidyConversionAction.getHAnimSkeletonIllustrate()));
        HAnimSiteIllustrateCheckBox.setSelected(Boolean.valueOf(x3dTidyConversionAction.getHAnimSiteIllustrate()));
        HAnimViewpointIllustrateCheckBox.setSelected(Boolean.valueOf(x3dTidyConversionAction.getHAnimViewpointIllustrate()));
        HAnimAddBoneSegmentsCheckBox.setSelected(Boolean.valueOf(x3dTidyConversionAction.getHAnimAddBoneSegments()));

        jointColorTextField.setText(x3dTidyConversionAction.getJointColor().trim());
        segmentColorTextField.setText(x3dTidyConversionAction.getSegmentColor().trim());
        siteColorTextField.setText(x3dTidyConversionAction.getSiteColor().trim());
        viewpointColorTextField.setText(x3dTidyConversionAction.getSiteViewpointColor().trim());
        
        // set matching values in color widgets
        
        String colorField = jointColorTextField.getText(); // 3-tuple floats [0..1]
        int index1 = colorField.indexOf(" ");
        int index2 = colorField.substring(index1+1).indexOf(" ") + index1+1;
        Color c = new Color (Float.valueOf(colorField.substring(0,index1)),
                             Float.valueOf(colorField.substring(index1+1,index2)),
                             Float.valueOf(colorField.substring(index2+1)));
        jointColorChooser.setColor(c);
        // http://stackoverflow.com/questions/3607858/how-to-convert-a-rgb-color-value-to-an-hexadecimal-value-in-java
        jointColorHexTextField.setText(String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue()));
        
        colorField = segmentColorTextField.getText(); // 3-tuple floats [0..1]
        index1 = colorField.indexOf(" ");
        index2 = colorField.substring(index1+1).indexOf(" ") + index1+1;
        c = new Color (Float.valueOf(colorField.substring(0,index1)),
                       Float.valueOf(colorField.substring(index1+1,index2)),
                       Float.valueOf(colorField.substring(index2+1)));
        segmentColorChooser.setColor(c);
        // http://stackoverflow.com/questions/3607858/how-to-convert-a-rgb-color-value-to-an-hexadecimal-value-in-java
        segmentColorHexTextField.setText(String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue()));
        
        colorField = siteColorTextField.getText(); // 3-tuple floats [0..1]
        index1 = colorField.indexOf(" ");
        index2 = colorField.substring(index1+1).indexOf(" ") + index1+1;
        c = new Color (Float.valueOf(colorField.substring(0,index1)),
                       Float.valueOf(colorField.substring(index1+1,index2)),
                       Float.valueOf(colorField.substring(index2+1)));
        siteColorChooser.setColor(c);
        // http://stackoverflow.com/questions/3607858/how-to-convert-a-rgb-color-value-to-an-hexadecimal-value-in-java
        siteColorHexTextField.setText(String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue()));
        
        colorField = viewpointColorTextField.getText(); // 3-tuple floats [0..1]
        index1 = colorField.indexOf(" ");
        index2 = colorField.substring(index1+1).indexOf(" ") + index1+1;
        c = new Color (Float.valueOf(colorField.substring(0,index1)),
                       Float.valueOf(colorField.substring(index1+1,index2)),
                       Float.valueOf(colorField.substring(index2+1)));
        viewpointColorChooser.setColor(c);
        // http://stackoverflow.com/questions/3607858/how-to-convert-a-rgb-color-value-to-an-hexadecimal-value-in-java
        viewpointColorHexTextField.setText(String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue()));
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

        conversionPanel = new javax.swing.JPanel();
        conversionRequiredCheckBox = new javax.swing.JCheckBox();
        modifyX3dVersionCheckBox = new javax.swing.JCheckBox();
        revisedX3dVersionComboBox = new javax.swing.JComboBox();
        fixDateFormatsCheckBox = new javax.swing.JCheckBox();
        fixMFStringQuotesCheckBox = new javax.swing.JCheckBox();
        fixMetaNamesMatchDublinCoreCheckBox = new javax.swing.JCheckBox();
        replaceBlackEmissiveColorCheckBox = new javax.swing.JCheckBox();
        horizontalSeparator1 = new javax.swing.JSeparator();
        changeJavascriptEcmascriptCheckBox = new javax.swing.JCheckBox();
        insertMissingEcmascriptCheckBox = new javax.swing.JCheckBox();
        addressesPanel = new javax.swing.JPanel();
        fixUrlAdditionHttpAddressesCheckBox = new javax.swing.JCheckBox();
        appendWrlAfterX3dAddressesCheckBox = new javax.swing.JCheckBox();
        prependX3dBeforeWrlAddressesCheckBox = new javax.swing.JCheckBox();
        defaultUrlAddressTextField = new javax.swing.JTextField();
        insertMissingMetaLicenseCheckBox = new javax.swing.JCheckBox();
        licenseLinkTextField = new javax.swing.JTextField();
        geospatialPanel = new javax.swing.JPanel();
        fixGeoSystemMetadataCheckBox = new javax.swing.JCheckBox();
        hanimPanel = new javax.swing.JPanel();
        HAnimGeometryRemoveCheckBox = new javax.swing.JCheckBox();
        HAnimSkeletonIllustrateCheckBox = new javax.swing.JCheckBox();
        HAnimSiteIllustrateCheckBox = new javax.swing.JCheckBox();
        HAnimViewpointIllustrateCheckBox = new javax.swing.JCheckBox();
        HAnimAddBoneSegmentsCheckBox = new javax.swing.JCheckBox();
        horizontalSeparator2 = new javax.swing.JSeparator();
        HAnimIllustrateVisualizationPreferencesLabel = new javax.swing.JLabel();
        jointColorLabel = new javax.swing.JLabel();
        jointColorTextField = new javax.swing.JTextField();
        jointColorChooser = new net.java.dev.colorchooser.ColorChooser();
        jointColorHexTextField = new javax.swing.JTextField();
        segmentColorLabel = new javax.swing.JLabel();
        segmentColorTextField = new javax.swing.JTextField();
        segmentColorChooser = new net.java.dev.colorchooser.ColorChooser();
        segmentColorHexTextField = new javax.swing.JTextField();
        siteColorLabel = new javax.swing.JLabel();
        siteColorTextField = new javax.swing.JTextField();
        siteColorChooser = new net.java.dev.colorchooser.ColorChooser();
        siteColorHexTextField = new javax.swing.JTextField();
        viewpointColorLabel = new javax.swing.JLabel();
        viewpointColorTextField = new javax.swing.JTextField();
        viewpointColorChooser = new net.java.dev.colorchooser.ColorChooser();
        viewpointColorHexTextField = new javax.swing.JTextField();

        setLayout(new java.awt.GridBagLayout());

        conversionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.conversionPanel.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        conversionPanel.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(conversionRequiredCheckBox, org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.conversionRequiredCheckBox.text")); // NOI18N
        conversionRequiredCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        conversionRequiredCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                conversionRequiredCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        conversionPanel.add(conversionRequiredCheckBox, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(modifyX3dVersionCheckBox, org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.modifyX3dVersionCheckBox.text")); // NOI18N
        modifyX3dVersionCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        modifyX3dVersionCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modifyX3dVersionCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        conversionPanel.add(modifyX3dVersionCheckBox, gridBagConstraints);

        revisedX3dVersionComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "3.0", "3.1", "3.2", "3.3", "4.0" }));
        revisedX3dVersionComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                revisedX3dVersionComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        conversionPanel.add(revisedX3dVersionComboBox, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(fixDateFormatsCheckBox, org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.fixDateFormatsCheckBox.text")); // NOI18N
        fixDateFormatsCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fixDateFormatsCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        conversionPanel.add(fixDateFormatsCheckBox, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(fixMFStringQuotesCheckBox, org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.fixMFStringQuotesCheckBox.text")); // NOI18N
        fixMFStringQuotesCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fixMFStringQuotesCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        conversionPanel.add(fixMFStringQuotesCheckBox, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(fixMetaNamesMatchDublinCoreCheckBox, org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.fixMetaNamesMatchDublinCoreCheckBox.text")); // NOI18N
        fixMetaNamesMatchDublinCoreCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fixMetaNamesMatchDublinCoreCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        conversionPanel.add(fixMetaNamesMatchDublinCoreCheckBox, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(replaceBlackEmissiveColorCheckBox, org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.replaceBlackEmissiveColorCheckBox.text")); // NOI18N
        replaceBlackEmissiveColorCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                replaceBlackEmissiveColorCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        conversionPanel.add(replaceBlackEmissiveColorCheckBox, gridBagConstraints);

        horizontalSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        conversionPanel.add(horizontalSeparator1, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(changeJavascriptEcmascriptCheckBox, org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.changeJavascriptEcmascriptCheckBox.text")); // NOI18N
        changeJavascriptEcmascriptCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeJavascriptEcmascriptCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        conversionPanel.add(changeJavascriptEcmascriptCheckBox, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(insertMissingEcmascriptCheckBox, org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.insertMissingEcmascriptCheckBox.text")); // NOI18N
        insertMissingEcmascriptCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertMissingEcmascriptCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        conversionPanel.add(insertMissingEcmascriptCheckBox, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(conversionPanel, gridBagConstraints);

        addressesPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.addressesPanel.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        addressesPanel.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(fixUrlAdditionHttpAddressesCheckBox, org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.fixUrlAdditionHttpAddressesCheckBox.text")); // NOI18N
        fixUrlAdditionHttpAddressesCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fixUrlAdditionHttpAddressesCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        addressesPanel.add(fixUrlAdditionHttpAddressesCheckBox, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(appendWrlAfterX3dAddressesCheckBox, org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.appendWrlAfterX3dAddressesCheckBox.text")); // NOI18N
        appendWrlAfterX3dAddressesCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                appendWrlAfterX3dAddressesCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        addressesPanel.add(appendWrlAfterX3dAddressesCheckBox, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(prependX3dBeforeWrlAddressesCheckBox, org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.prependX3dBeforeWrlAddressesCheckBox.text")); // NOI18N
        prependX3dBeforeWrlAddressesCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prependX3dBeforeWrlAddressesCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        addressesPanel.add(prependX3dBeforeWrlAddressesCheckBox, gridBagConstraints);

        defaultUrlAddressTextField.setToolTipText(org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.defaultUrlAddressTextField.toolTipText")); // NOI18N
        defaultUrlAddressTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                defaultUrlAddressTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 20, 3, 3);
        addressesPanel.add(defaultUrlAddressTextField, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(insertMissingMetaLicenseCheckBox, org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.insertMissingMetaLicenseCheckBox.text")); // NOI18N
        insertMissingMetaLicenseCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertMissingMetaLicenseCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        addressesPanel.add(insertMissingMetaLicenseCheckBox, gridBagConstraints);

        licenseLinkTextField.setText(org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.licenseLinkTextField.text")); // NOI18N
        licenseLinkTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                licenseLinkTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 20, 3, 3);
        addressesPanel.add(licenseLinkTextField, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(addressesPanel, gridBagConstraints);

        geospatialPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.geospatialPanel.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        geospatialPanel.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(fixGeoSystemMetadataCheckBox, org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.fixGeoSystemMetadataCheckBox.text")); // NOI18N
        fixGeoSystemMetadataCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fixGeoSystemMetadataCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        geospatialPanel.add(fixGeoSystemMetadataCheckBox, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(geospatialPanel, gridBagConstraints);

        hanimPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.hanimPanel.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        hanimPanel.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(HAnimGeometryRemoveCheckBox, org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.HAnimGeometryRemoveCheckBox.text")); // NOI18N
        HAnimGeometryRemoveCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HAnimGeometryRemoveCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hanimPanel.add(HAnimGeometryRemoveCheckBox, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(HAnimSkeletonIllustrateCheckBox, org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.HAnimSkeletonIllustrateCheckBox.text")); // NOI18N
        HAnimSkeletonIllustrateCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HAnimSkeletonIllustrateCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hanimPanel.add(HAnimSkeletonIllustrateCheckBox, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(HAnimSiteIllustrateCheckBox, org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.HAnimSiteIllustrateCheckBox.text")); // NOI18N
        HAnimSiteIllustrateCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HAnimSiteIllustrateCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hanimPanel.add(HAnimSiteIllustrateCheckBox, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(HAnimViewpointIllustrateCheckBox, org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.HAnimViewpointIllustrateCheckBox.text")); // NOI18N
        HAnimViewpointIllustrateCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HAnimViewpointIllustrateCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hanimPanel.add(HAnimViewpointIllustrateCheckBox, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(HAnimAddBoneSegmentsCheckBox, org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.HAnimAddBoneSegmentsCheckBox.text")); // NOI18N
        HAnimAddBoneSegmentsCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.HAnimAddBoneSegmentsCheckBox.toolTipText")); // NOI18N
        HAnimAddBoneSegmentsCheckBox.setEnabled(false);
        HAnimAddBoneSegmentsCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HAnimAddBoneSegmentsCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hanimPanel.add(HAnimAddBoneSegmentsCheckBox, gridBagConstraints);

        horizontalSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hanimPanel.add(horizontalSeparator2, gridBagConstraints);

        HAnimIllustrateVisualizationPreferencesLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(HAnimIllustrateVisualizationPreferencesLabel, org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.HAnimIllustrateVisualizationPreferencesLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        hanimPanel.add(HAnimIllustrateVisualizationPreferencesLabel, gridBagConstraints);

        jointColorLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(jointColorLabel, org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.jointColorLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hanimPanel.add(jointColorLabel, gridBagConstraints);

        jointColorTextField.setText(org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.siteColorTextField.text")); // NOI18N
        jointColorTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jointColorTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hanimPanel.add(jointColorTextField, gridBagConstraints);

        jointColorChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jointColorChooserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jointColorChooserLayout = new javax.swing.GroupLayout(jointColorChooser);
        jointColorChooser.setLayout(jointColorChooserLayout);
        jointColorChooserLayout.setHorizontalGroup(
            jointColorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );
        jointColorChooserLayout.setVerticalGroup(
            jointColorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hanimPanel.add(jointColorChooser, gridBagConstraints);

        jointColorHexTextField.setText(org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.jointColorHexTextField.text")); // NOI18N
        jointColorHexTextField.setToolTipText(org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.jointColorHexTextField.toolTipText")); // NOI18N
        jointColorHexTextField.setMinimumSize(new java.awt.Dimension(60, 22));
        jointColorHexTextField.setPreferredSize(new java.awt.Dimension(60, 22));
        jointColorHexTextField.setRequestFocusEnabled(false);
        jointColorHexTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jointColorHexTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hanimPanel.add(jointColorHexTextField, gridBagConstraints);

        segmentColorLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(segmentColorLabel, org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.segmentColorLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hanimPanel.add(segmentColorLabel, gridBagConstraints);

        segmentColorTextField.setText(org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.segmentColorTextField.text")); // NOI18N
        segmentColorTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                segmentColorTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hanimPanel.add(segmentColorTextField, gridBagConstraints);

        segmentColorChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                segmentColorChooserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout segmentColorChooserLayout = new javax.swing.GroupLayout(segmentColorChooser);
        segmentColorChooser.setLayout(segmentColorChooserLayout);
        segmentColorChooserLayout.setHorizontalGroup(
            segmentColorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );
        segmentColorChooserLayout.setVerticalGroup(
            segmentColorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hanimPanel.add(segmentColorChooser, gridBagConstraints);

        segmentColorHexTextField.setText(org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.segmentColorHexTextField.text")); // NOI18N
        segmentColorHexTextField.setToolTipText(org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.segmentColorHexTextField.toolTipText")); // NOI18N
        segmentColorHexTextField.setMinimumSize(new java.awt.Dimension(60, 22));
        segmentColorHexTextField.setPreferredSize(new java.awt.Dimension(60, 22));
        segmentColorHexTextField.setRequestFocusEnabled(false);
        segmentColorHexTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                segmentColorHexTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hanimPanel.add(segmentColorHexTextField, gridBagConstraints);

        siteColorLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(siteColorLabel, org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.siteColorLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hanimPanel.add(siteColorLabel, gridBagConstraints);

        siteColorTextField.setText(org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.siteColorTextField.text")); // NOI18N
        siteColorTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                siteColorTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hanimPanel.add(siteColorTextField, gridBagConstraints);

        siteColorChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                siteColorChooserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout siteColorChooserLayout = new javax.swing.GroupLayout(siteColorChooser);
        siteColorChooser.setLayout(siteColorChooserLayout);
        siteColorChooserLayout.setHorizontalGroup(
            siteColorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );
        siteColorChooserLayout.setVerticalGroup(
            siteColorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hanimPanel.add(siteColorChooser, gridBagConstraints);

        siteColorHexTextField.setText(org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.siteColorHexTextField.text")); // NOI18N
        siteColorHexTextField.setToolTipText(org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.siteColorHexTextField.toolTipText")); // NOI18N
        siteColorHexTextField.setMinimumSize(new java.awt.Dimension(60, 22));
        siteColorHexTextField.setPreferredSize(new java.awt.Dimension(60, 22));
        siteColorHexTextField.setRequestFocusEnabled(false);
        siteColorHexTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                siteColorHexTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hanimPanel.add(siteColorHexTextField, gridBagConstraints);

        viewpointColorLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(viewpointColorLabel, org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.viewpointColorLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hanimPanel.add(viewpointColorLabel, gridBagConstraints);

        viewpointColorTextField.setText(org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.siteColorTextField.text")); // NOI18N
        viewpointColorTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewpointColorTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hanimPanel.add(viewpointColorTextField, gridBagConstraints);

        viewpointColorChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewpointColorChooserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout viewpointColorChooserLayout = new javax.swing.GroupLayout(viewpointColorChooser);
        viewpointColorChooser.setLayout(viewpointColorChooserLayout);
        viewpointColorChooserLayout.setHorizontalGroup(
            viewpointColorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );
        viewpointColorChooserLayout.setVerticalGroup(
            viewpointColorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hanimPanel.add(viewpointColorChooser, gridBagConstraints);

        viewpointColorHexTextField.setText(org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.viewpointColorHexTextField.text")); // NOI18N
        viewpointColorHexTextField.setToolTipText(org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.viewpointColorHexTextField.toolTipText")); // NOI18N
        viewpointColorHexTextField.setMinimumSize(new java.awt.Dimension(60, 22));
        viewpointColorHexTextField.setPreferredSize(new java.awt.Dimension(60, 22));
        viewpointColorHexTextField.setRequestFocusEnabled(false);
        viewpointColorHexTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewpointColorHexTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hanimPanel.add(viewpointColorHexTextField, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(hanimPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void fixDateFormatsCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fixDateFormatsCheckBoxActionPerformed
        x3dTidyConversionAction.setFixDateFormats(Boolean.toString(fixDateFormatsCheckBox.isSelected()));
    }//GEN-LAST:event_fixDateFormatsCheckBoxActionPerformed

    private void replaceBlackEmissiveColorCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_replaceBlackEmissiveColorCheckBoxActionPerformed
        x3dTidyConversionAction.setReplaceBlackEmissiveColor(Boolean.toString(replaceBlackEmissiveColorCheckBox.isSelected()));
    }//GEN-LAST:event_replaceBlackEmissiveColorCheckBoxActionPerformed

    private void fixUrlAdditionHttpAddressesCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fixUrlAdditionHttpAddressesCheckBoxActionPerformed
        x3dTidyConversionAction.setFixUrlAdditionHttpAddresses(Boolean.toString(fixUrlAdditionHttpAddressesCheckBox.isSelected()));
    }//GEN-LAST:event_fixUrlAdditionHttpAddressesCheckBoxActionPerformed

    private void appendWrlAfterX3dAddressesCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_appendWrlAfterX3dAddressesCheckBoxActionPerformed
        x3dTidyConversionAction.setAppendWrlAfterX3dAddresses(Boolean.toString(appendWrlAfterX3dAddressesCheckBox.isSelected()));
    }//GEN-LAST:event_appendWrlAfterX3dAddressesCheckBoxActionPerformed

    private void defaultUrlAddressTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_defaultUrlAddressTextFieldActionPerformed
        if(!defaultUrlAddressTextField.getText().trim().equals(org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.defaultUrlAddressTextField.text")))
           x3dTidyConversionAction.setDefaultUrlAddress(defaultUrlAddressTextField.getText().trim());
    }//GEN-LAST:event_defaultUrlAddressTextFieldActionPerformed

    private void prependX3dBeforeWrlAddressesCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prependX3dBeforeWrlAddressesCheckBoxActionPerformed
        x3dTidyConversionAction.setPrependX3dBeforeWrlAddresses(Boolean.toString(prependX3dBeforeWrlAddressesCheckBox.isSelected()));
    }//GEN-LAST:event_prependX3dBeforeWrlAddressesCheckBoxActionPerformed

    private void HAnimGeometryRemoveCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HAnimGeometryRemoveCheckBoxActionPerformed
        x3dTidyConversionAction.setHAnimGeometryRemove(Boolean.toString(HAnimGeometryRemoveCheckBox.isSelected()));
    }//GEN-LAST:event_HAnimGeometryRemoveCheckBoxActionPerformed

    private void HAnimSkeletonIllustrateCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HAnimSkeletonIllustrateCheckBoxActionPerformed
        x3dTidyConversionAction.setHAnimSkeletonIllustrate(Boolean.toString(HAnimSkeletonIllustrateCheckBox.isSelected()));
             HAnimSiteIllustrateCheckBox.setSelected(HAnimSkeletonIllustrateCheckBox.isSelected());
        HAnimViewpointIllustrateCheckBox.setSelected(HAnimSkeletonIllustrateCheckBox.isSelected());
    }//GEN-LAST:event_HAnimSkeletonIllustrateCheckBoxActionPerformed

    private void HAnimSiteIllustrateCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HAnimSiteIllustrateCheckBoxActionPerformed
        x3dTidyConversionAction.setHAnimSiteIllustrate(Boolean.toString(HAnimSiteIllustrateCheckBox.isSelected()));
        if (!HAnimSiteIllustrateCheckBox.isSelected())
             HAnimSkeletonIllustrateCheckBox.setSelected(false);
    }//GEN-LAST:event_HAnimSiteIllustrateCheckBoxActionPerformed

    private void HAnimViewpointIllustrateCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HAnimViewpointIllustrateCheckBoxActionPerformed
        x3dTidyConversionAction.setHAnimViewpointIllustrate(Boolean.toString(HAnimViewpointIllustrateCheckBox.isSelected()));
        if (!HAnimViewpointIllustrateCheckBox.isSelected())
             HAnimSkeletonIllustrateCheckBox.setSelected(false);
    }//GEN-LAST:event_HAnimViewpointIllustrateCheckBoxActionPerformed

    private void HAnimAddBoneSegmentsCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HAnimAddBoneSegmentsCheckBoxActionPerformed
        x3dTidyConversionAction.setHAnimAddBoneSegments(Boolean.toString(HAnimAddBoneSegmentsCheckBox.isSelected()));
    }//GEN-LAST:event_HAnimAddBoneSegmentsCheckBoxActionPerformed

    
    private void jointColorTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jointColorTextFieldActionPerformed
        String colorField = jointColorTextField.getText().trim(); // 3-tuple floats [0..1]
        x3dTidyConversionAction.setJointColor(colorField);
        int index1 = colorField.indexOf(" ");
        int index2 = colorField.substring(index1+1).indexOf(" ") + index1+1;
        Color c = new Color (Float.valueOf(colorField.substring(0,index1)),
                             Float.valueOf(colorField.substring(index1+1,index2)),
                             Float.valueOf(colorField.substring(index2+1)));
        jointColorChooser.setColor(c);
        // http://stackoverflow.com/questions/3607858/how-to-convert-a-rgb-color-value-to-an-hexadecimal-value-in-java
        jointColorHexTextField.setText(String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue()));
    }//GEN-LAST:event_jointColorTextFieldActionPerformed

    private void siteColorTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_siteColorTextFieldActionPerformed
        x3dTidyConversionAction.setSiteColor(siteColorTextField.getText().trim());
    }//GEN-LAST:event_siteColorTextFieldActionPerformed

    private void viewpointColorTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewpointColorTextFieldActionPerformed
        x3dTidyConversionAction.setSiteViewpointColor(viewpointColorTextField.getText().trim());
    }//GEN-LAST:event_viewpointColorTextFieldActionPerformed

    private void segmentColorTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_segmentColorTextFieldActionPerformed
        x3dTidyConversionAction.setSegmentColor(segmentColorTextField.getText().trim());
    }//GEN-LAST:event_segmentColorTextFieldActionPerformed

    private void changeJavascriptEcmascriptCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeJavascriptEcmascriptCheckBoxActionPerformed
        x3dTidyConversionAction.setChangeJavascriptEcmascript(Boolean.toString(changeJavascriptEcmascriptCheckBox.isSelected()));
    }//GEN-LAST:event_changeJavascriptEcmascriptCheckBoxActionPerformed

    private void insertMissingEcmascriptCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertMissingEcmascriptCheckBoxActionPerformed
        x3dTidyConversionAction.setInsertMissingEcmascript(Boolean.toString(insertMissingEcmascriptCheckBox.isSelected()));
    }//GEN-LAST:event_insertMissingEcmascriptCheckBoxActionPerformed

    private void conversionRequiredCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conversionRequiredCheckBoxActionPerformed
        x3dTidyConversionAction.setConversionRequired(Boolean.toString(conversionRequiredCheckBox.isSelected()));
    }//GEN-LAST:event_conversionRequiredCheckBoxActionPerformed

    private void modifyX3dVersionCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modifyX3dVersionCheckBoxActionPerformed
        x3dTidyConversionAction.setModifyX3dVersion(Boolean.toString(modifyX3dVersionCheckBox.isSelected()));
    }//GEN-LAST:event_modifyX3dVersionCheckBoxActionPerformed

    private void revisedX3dVersionComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_revisedX3dVersionComboBoxActionPerformed
        x3dTidyConversionAction.setRevisedX3dVersion(revisedX3dVersionComboBox.getSelectedItem().toString());
    }//GEN-LAST:event_revisedX3dVersionComboBoxActionPerformed

    private void fixMFStringQuotesCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fixMFStringQuotesCheckBoxActionPerformed
        x3dTidyConversionAction.setFixMFStringQuotes(Boolean.toString(fixMFStringQuotesCheckBox.isSelected()));
    }//GEN-LAST:event_fixMFStringQuotesCheckBoxActionPerformed

    private void fixGeoSystemMetadataCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fixGeoSystemMetadataCheckBoxActionPerformed
        x3dTidyConversionAction.setFixGeoSystemMetadata(Boolean.toString(fixGeoSystemMetadataCheckBox.isSelected()));
    }//GEN-LAST:event_fixGeoSystemMetadataCheckBoxActionPerformed

    private void fixMetaNamesMatchDublinCoreCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fixMetaNamesMatchDublinCoreCheckBoxActionPerformed
        x3dTidyConversionAction.setFixMetaNamesMatchDublinCore(Boolean.toString(fixMetaNamesMatchDublinCoreCheckBox.isSelected()));
    }//GEN-LAST:event_fixMetaNamesMatchDublinCoreCheckBoxActionPerformed

    private void insertMissingMetaLicenseCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertMissingMetaLicenseCheckBoxActionPerformed
        x3dTidyConversionAction.setInsertMissingMetaLicense(Boolean.toString(insertMissingMetaLicenseCheckBox.isSelected()));
    }//GEN-LAST:event_insertMissingMetaLicenseCheckBoxActionPerformed

    private void licenseLinkTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_licenseLinkTextFieldActionPerformed
        x3dTidyConversionAction.setLicenseLink(licenseLinkTextField.getText().trim());
    }//GEN-LAST:event_licenseLinkTextFieldActionPerformed

    private void jointColorChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jointColorChooserActionPerformed
        Color c = jointColorChooser.getColor();
        float red   = (c.getRed()   / 255.0f);
        float green = (c.getGreen() / 255.0f);
        float blue  = (c.getBlue()  / 255.0f);
        jointColorTextField.setText(red + " " + green + " " + blue);
        // http://stackoverflow.com/questions/3607858/how-to-convert-a-rgb-color-value-to-an-hexadecimal-value-in-java
        jointColorHexTextField.setText(String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue()));
    }//GEN-LAST:event_jointColorChooserActionPerformed

    private void jointColorHexTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jointColorHexTextFieldActionPerformed
        String hexColorValue = jointColorHexTextField.getText().trim();
        if  (hexColorValue.startsWith("#"))
             hexColorValue = hexColorValue.substring(hexColorValue.lastIndexOf("#")+1); // strip # character(s)
        else jointColorHexTextField.setText("#" + hexColorValue);
        Color c = new Color (Integer.getInteger(hexColorValue));  
        jointColorTextField.setText((c.getRed()   / 255.0f) + " " +
                                    (c.getGreen() / 255.0f) + " " +
                                    (c.getBlue()  / 255.0f));
        jointColorChooser.setColor(c);
    }//GEN-LAST:event_jointColorHexTextFieldActionPerformed

    private void segmentColorChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_segmentColorChooserActionPerformed
        Color c = segmentColorChooser.getColor();
        float red   = (c.getRed()   / 255.0f);
        float green = (c.getGreen() / 255.0f);
        float blue  = (c.getBlue()  / 255.0f);
        segmentColorTextField.setText(red + " " + green + " " + blue);
        // http://stackoverflow.com/questions/3607858/how-to-convert-a-rgb-color-value-to-an-hexadecimal-value-in-java
        segmentColorHexTextField.setText(String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue()));
    }//GEN-LAST:event_segmentColorChooserActionPerformed

    private void segmentColorHexTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_segmentColorHexTextFieldActionPerformed
        String hexColorValue = segmentColorHexTextField.getText().trim();
        if  (hexColorValue.startsWith("#"))
             hexColorValue = hexColorValue.substring(hexColorValue.lastIndexOf("#")+1); // strip # character(s)
        else segmentColorHexTextField.setText("#" + hexColorValue);
        Color c = new Color (Integer.getInteger(hexColorValue));  
        segmentColorTextField.setText((c.getRed()   / 255.0f) + " " +
                                    (c.getGreen() / 255.0f) + " " +
                                    (c.getBlue()  / 255.0f));
        segmentColorChooser.setColor(c);
    }//GEN-LAST:event_segmentColorHexTextFieldActionPerformed

    private void siteColorChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_siteColorChooserActionPerformed
        Color c = siteColorChooser.getColor();
        float red   = (c.getRed()   / 255.0f);
        float green = (c.getGreen() / 255.0f);
        float blue  = (c.getBlue()  / 255.0f);
        siteColorTextField.setText(red + " " + green + " " + blue);
        // http://stackoverflow.com/questions/3607858/how-to-convert-a-rgb-color-value-to-an-hexadecimal-value-in-java
        siteColorHexTextField.setText(String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue()));
    }//GEN-LAST:event_siteColorChooserActionPerformed

    private void siteColorHexTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_siteColorHexTextFieldActionPerformed
        String hexColorValue = siteColorHexTextField.getText().trim();
        if  (hexColorValue.startsWith("#"))
             hexColorValue = hexColorValue.substring(hexColorValue.lastIndexOf("#")+1); // strip # character(s)
        else siteColorHexTextField.setText("#" + hexColorValue);
        Color c = new Color (Integer.getInteger(hexColorValue));  
        siteColorTextField.setText((c.getRed()   / 255.0f) + " " +
                                    (c.getGreen() / 255.0f) + " " +
                                    (c.getBlue()  / 255.0f));
        siteColorChooser.setColor(c);
    }//GEN-LAST:event_siteColorHexTextFieldActionPerformed

    private void viewpointColorChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewpointColorChooserActionPerformed
        Color c = viewpointColorChooser.getColor();
        float red   = (c.getRed()   / 255.0f);
        float green = (c.getGreen() / 255.0f);
        float blue  = (c.getBlue()  / 255.0f);
        viewpointColorTextField.setText(red + " " + green + " " + blue);
        // http://stackoverflow.com/questions/3607858/how-to-convert-a-rgb-color-value-to-an-hexadecimal-value-in-java
        viewpointColorHexTextField.setText(String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue()));
    }//GEN-LAST:event_viewpointColorChooserActionPerformed

    private void viewpointColorHexTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewpointColorHexTextFieldActionPerformed
        String hexColorValue = viewpointColorHexTextField.getText().trim();
        if  (hexColorValue.startsWith("#"))
             hexColorValue = hexColorValue.substring(hexColorValue.lastIndexOf("#")+1); // strip # character(s)
        else viewpointColorHexTextField.setText("#" + hexColorValue);
        Color c = new Color (Integer.getInteger(hexColorValue));  
        viewpointColorTextField.setText((c.getRed()   / 255.0f) + " " +
                                    (c.getGreen() / 255.0f) + " " +
                                    (c.getBlue()  / 255.0f));
        viewpointColorChooser.setColor(c);
    }//GEN-LAST:event_viewpointColorHexTextFieldActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox HAnimAddBoneSegmentsCheckBox;
    private javax.swing.JCheckBox HAnimGeometryRemoveCheckBox;
    private javax.swing.JLabel HAnimIllustrateVisualizationPreferencesLabel;
    private javax.swing.JCheckBox HAnimSiteIllustrateCheckBox;
    private javax.swing.JCheckBox HAnimSkeletonIllustrateCheckBox;
    private javax.swing.JCheckBox HAnimViewpointIllustrateCheckBox;
    private javax.swing.JPanel addressesPanel;
    private javax.swing.JCheckBox appendWrlAfterX3dAddressesCheckBox;
    private javax.swing.JCheckBox changeJavascriptEcmascriptCheckBox;
    private javax.swing.JPanel conversionPanel;
    private javax.swing.JCheckBox conversionRequiredCheckBox;
    private javax.swing.JTextField defaultUrlAddressTextField;
    private javax.swing.JCheckBox fixDateFormatsCheckBox;
    private javax.swing.JCheckBox fixGeoSystemMetadataCheckBox;
    private javax.swing.JCheckBox fixMFStringQuotesCheckBox;
    private javax.swing.JCheckBox fixMetaNamesMatchDublinCoreCheckBox;
    private javax.swing.JCheckBox fixUrlAdditionHttpAddressesCheckBox;
    private javax.swing.JPanel geospatialPanel;
    private javax.swing.JPanel hanimPanel;
    private javax.swing.JSeparator horizontalSeparator1;
    private javax.swing.JSeparator horizontalSeparator2;
    private javax.swing.JCheckBox insertMissingEcmascriptCheckBox;
    private javax.swing.JCheckBox insertMissingMetaLicenseCheckBox;
    private net.java.dev.colorchooser.ColorChooser jointColorChooser;
    private javax.swing.JTextField jointColorHexTextField;
    private javax.swing.JLabel jointColorLabel;
    private javax.swing.JTextField jointColorTextField;
    private javax.swing.JTextField licenseLinkTextField;
    private javax.swing.JCheckBox modifyX3dVersionCheckBox;
    private javax.swing.JCheckBox prependX3dBeforeWrlAddressesCheckBox;
    private javax.swing.JCheckBox replaceBlackEmissiveColorCheckBox;
    private javax.swing.JComboBox revisedX3dVersionComboBox;
    private net.java.dev.colorchooser.ColorChooser segmentColorChooser;
    private javax.swing.JTextField segmentColorHexTextField;
    private javax.swing.JLabel segmentColorLabel;
    private javax.swing.JTextField segmentColorTextField;
    private net.java.dev.colorchooser.ColorChooser siteColorChooser;
    private javax.swing.JTextField siteColorHexTextField;
    private javax.swing.JLabel siteColorLabel;
    private javax.swing.JTextField siteColorTextField;
    private net.java.dev.colorchooser.ColorChooser viewpointColorChooser;
    private javax.swing.JTextField viewpointColorHexTextField;
    private javax.swing.JLabel viewpointColorLabel;
    private javax.swing.JTextField viewpointColorTextField;
    // End of variables declaration//GEN-END:variables

//    private void unloadValues ()
//    {
//        x3dTidyConversionAction.setConversionRequired(Boolean.toString(conversionRequiredCheckBox.isSelected()));
////        x3dTidyConversionAction.set(title);
//        x3dTidyConversionAction.setModifyX3dVersion(Boolean.toString(modifyX3dVersionCheckBox.isSelected()));
//        x3dTidyConversionAction.setRevisedX3dVersion(revisedX3dVersionComboBox.getSelectedItem().toString());
//        x3dTidyConversionAction.setFixDateFormats(Boolean.toString(fixDateFormatsCheckBox.isSelected()));
//        x3dTidyConversionAction.setFixMFStringQuotes(Boolean.toString(fixMFStringQuotesCheckBox.isSelected()));
//        x3dTidyConversionAction.setFixGeoSystemMetadata(Boolean.toString(fixGeoSystemMetadataCheckBox.isSelected()));
//        x3dTidyConversionAction.setFixMetaNamesMatchDublinCore(Boolean.toString(fixMetaNamesMatchDublinCoreCheckBox.isSelected()));
//        x3dTidyConversionAction.setReplaceBlackEmissiveColor(Boolean.toString(replaceBlackEmissiveColorCheckBox.isSelected()));
//
//        x3dTidyConversionAction.setFixUrlAdditionHttpAddresses(Boolean.toString(fixUrlAdditionHttpAddressesCheckBox.isSelected()));
//
//        x3dTidyConversionAction.setAppendWrlAfterX3dAddresses(Boolean.toString(appendWrlAfterX3dAddressesCheckBox.isSelected()));
//        x3dTidyConversionAction.setPrependX3dBeforeWrlAddresses(Boolean.toString(prependX3dBeforeWrlAddressesCheckBox.isSelected()));
//        if(!defaultUrlAddressTextField.getText().trim().equals(org.openide.util.NbBundle.getMessage(X3dTidyConversionPanel.class, "X3dTidyConversionPanel.defaultUrlAddressTextField.text")))
//           x3dTidyConversionAction.setDefaultUrlAddress(defaultUrlAddressTextField.getText().trim());
//
//        x3dTidyConversionAction.setChangeJavascriptEcmascript(Boolean.toString(changeJavascriptEcmascriptCheckBox.isSelected()));
//        x3dTidyConversionAction.setInsertMissingEcmascript(Boolean.toString(insertMissingEcmascriptCheckBox.isSelected()));
//        x3dTidyConversionAction.setInsertMissingMetaLicense(Boolean.toString(insertMissingMetaLicenseCheckBox.isSelected()));
//        x3dTidyConversionAction.setLicenseLink(licenseLinkTextField.getText().trim());
//
//        x3dTidyConversionAction.setHAnimGeometryRemove(Boolean.toString(HAnimGeometryRemoveCheckBox.isSelected()));
//        x3dTidyConversionAction.setHAnimSkeletonIllustrate(Boolean.toString(HAnimSkeletonIllustrateCheckBox.isSelected()));
//        x3dTidyConversionAction.setHAnimSiteIllustrate(Boolean.toString(HAnimSiteIllustrateCheckBox.isSelected()));
//        x3dTidyConversionAction.setHAnimViewpointIllustrate(Boolean.toString(HAnimViewpointIllustrateCheckBox.isSelected()));
//        x3dTidyConversionAction.setHAnimAddBoneSegments(Boolean.toString(HAnimAddBoneSegmentsCheckBox.isSelected()));
//
//        x3dTidyConversionAction.setJointColor(jointColorTextField.getText().trim());
//        // no setSegmentColor method
//        x3dTidyConversionAction.setSegmentColor(segmentColorTextField.getText().trim());
//        x3dTidyConversionAction.setSiteColor(siteColorTextField.getText().trim());
//        x3dTidyConversionAction.setSiteViewpointColor(viewpointColorTextField.getText().trim());
//    }
    
    protected void setGeospatialPanelEnabled (boolean value)
    {
        geospatialPanel.setEnabled(value);
        fixGeoSystemMetadataCheckBox.setEnabled(value);
    }
    
    protected void setHAnimPanelEnabled (boolean value)
    {
        hanimPanel.setEnabled(value);
        HAnimGeometryRemoveCheckBox.setEnabled(value);
        HAnimSkeletonIllustrateCheckBox.setEnabled(value);
        HAnimSiteIllustrateCheckBox.setEnabled(value);
        HAnimViewpointIllustrateCheckBox.setEnabled(value);
        HAnimAddBoneSegmentsCheckBox.setEnabled(value);
        jointColorLabel.setEnabled(value);
        jointColorTextField.setEnabled(value);
        jointColorChooser.setEnabled(value);
        jointColorHexTextField.setEnabled(value);
        segmentColorLabel.setEnabled(value);
        segmentColorTextField.setEnabled(value);
        segmentColorChooser.setEnabled(value);
        segmentColorHexTextField.setEnabled(value);
        siteColorLabel.setEnabled(value);
        siteColorTextField.setEnabled(value);
        siteColorChooser.setEnabled(value);
        siteColorHexTextField.setEnabled(value);
        siteColorHexTextField.setEnabled(value);
        viewpointColorLabel.setEnabled(value);
        viewpointColorTextField.setEnabled(value);
        viewpointColorChooser.setEnabled(value);
        viewpointColorHexTextField.setEnabled(value);
    }
}
