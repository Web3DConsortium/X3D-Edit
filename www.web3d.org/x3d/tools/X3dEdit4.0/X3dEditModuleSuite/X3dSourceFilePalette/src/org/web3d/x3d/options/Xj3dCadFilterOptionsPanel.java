/*
Copyright (c) 2008-2021 held by the author(s) .  All rights reserved.

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

/*
 * Xj3dCadFilterOptionsPanel.java
 *
 * Created on July 1, 2008, 1:54 PM
 */
package org.web3d.x3d.options;

import javax.swing.JOptionPane;
import org.openide.util.NbBundle;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 *
 * @author  Mike Bailey
 */
public class Xj3dCadFilterOptionsPanel extends javax.swing.JPanel
{
  /** Creates new form Xj3dCadFilterOptionsPanel */
  public Xj3dCadFilterOptionsPanel()
  {
    initComponents();
    setValues();
  }
  private void setValues()
  {
    identityFilterRB.setSelected             (X3dOptions.getCadFilterIdentityFilter());
    cadFiltersRB.setSelected                 (X3dOptions.getCadFiltersEnabledRadioButton());

    absoluteScaleFactorTF.setText            (X3dOptions.getCadFilterAbsScaleFactor());
    addBoundingBoxesCheckBox.setSelected     (X3dOptions.getCadFilterAddBoundingBoxes());
    appearanceFilterCheckBox.setSelected     (X3dOptions.getCadFilterAppearanceFilter());
    binaryCompressionMethodCB.setSelectedItem(X3dOptions.getCadFilterBinaryCompressionMethod());
    centerFilterCheckBox.setSelected         (X3dOptions.getCadFilterCenterFilter());
    combineAppearancesCheckBox.setSelected   (X3dOptions.getCadFilterCombineAppearances());
    combineShapesCheckBox.setSelected        (X3dOptions.getCadFilterCombineShapes());
    debugCheckBox.setSelected                (X3dOptions.getCadFilterDebug());
    defNameShortenedCheckBox.setSelected     (X3dOptions.getCadFilterDefNameShortened());
    defUseImageTextureCheckBox.setSelected   (X3dOptions.getCadFilterDefuseImageTexture());
    embedPrototypesCheckBox.setSelected      (X3dOptions.getCadFilterEmbedProto());

    flattenTransformsCheckBox.setSelected    (X3dOptions.getCadFilterFlattenTransforms());
    flattenTextureTransformsCheckBox.setSelected (X3dOptions.getCadFilterFlattenTextureTransforms());
    flattenSelectableCheckBox.setSelected    (X3dOptions.getCadFilterFlattenSelectable());
    floatingPointQuantizationTF.setText      (X3dOptions.getCadFilterFloatingPointQuantization());
    generateNormalsCheckBox.setSelected      (X3dOptions.getCadFilterGenerateNormals());
    indexFilterCheckBox.setSelected          (X3dOptions.getCadFilterIndexFilter());
    IFStoITSCheckBox.setSelected             (X3dOptions.getCadFilterIFStoITS());
    IFStoTSCheckBox.setSelected              (X3dOptions.getCadFilterIFStoTS());
    logLevelCB.setSelectedItem               (X3dOptions.getCadFilterLogLevel());
    materialFilterCheckBox.setSelected       (X3dOptions.getCadFilterMaterialFilter());
    minimumProfileCheckBox.setSelected       (X3dOptions.getCadFilterMinimumProfile());
    modifyViewpointCheckBox.setSelected      (X3dOptions.getCadFilterModifyViewpoint());
    reIndexCheckBox.setSelected              (X3dOptions.getCadFilterReIndex());
    triangleCountCheckBox.setSelected        (X3dOptions.getCadFilterTriangleCount());
    triangulationFilterCheckBox.setSelected  (X3dOptions.getCadFilterTriangulationFilter());
    x3dVersionCB.setSelectedItem             (X3dOptions.getCadFilterX3dVersion());

    radioButtsHandler(null);
  }
  
  public void saveToPreferences()
  {
    String badSets = "";
    try {
      (new SFFloat(absoluteScaleFactorTF.getText())).getValue(); // check valid value
      X3dOptions.setCadFilterAbsScaleFactor(absoluteScaleFactorTF.getText().trim());
    }
    catch(NumberFormatException ex) {
      badSets = badSets + this.absoluteScaleFactorLabel.getText() + " ";
    }
    try {
      (new SFFloat(floatingPointQuantizationTF.getText())).getValue(); // check valid value
      X3dOptions.setCadFilterFloatingPointQuantization(floatingPointQuantizationTF.getText().trim());
    }
    catch(NumberFormatException ex) {
      badSets = badSets + floatingPointQuantizationLabel.getText();
    }
    X3dOptions.setCadFilterIdentityFilter     (identityFilterRB.isSelected());
    X3dOptions.setCadFilterCadFiltersRB       (cadFiltersRB.isSelected());

    X3dOptions.setCadFilterAddBoundingBoxes   (addBoundingBoxesCheckBox.isSelected());
    X3dOptions.setCadFilterAppearanceFilter   (appearanceFilterCheckBox.isSelected());
    X3dOptions.setCadFilterBinaryCompression  ((String)binaryCompressionMethodCB.getSelectedItem());
    X3dOptions.setCadFilterCombineAppearances (combineAppearancesCheckBox.isSelected());
    X3dOptions.setCadFilterCombineShapes      (combineShapesCheckBox.isSelected());
    X3dOptions.setCadFilterCenterFilter       (centerFilterCheckBox.isSelected());
    X3dOptions.setCadFilterDebug              (debugCheckBox.isSelected());
    X3dOptions.setCadFilterDefNameShortened   (defNameShortenedCheckBox.isSelected());
    X3dOptions.setCadFilterDefuseImageTexture (defUseImageTextureCheckBox.isSelected());
    X3dOptions.setCadFilterEmbedProto         (embedPrototypesCheckBox.isSelected());

    X3dOptions.setCadFilterFlattenTransforms  (flattenTransformsCheckBox.isSelected());
    X3dOptions.setCadFilterFlattenTextureTransforms  (flattenTextureTransformsCheckBox.isSelected());
    X3dOptions.setCadFilterFlattenSelectable  (flattenSelectableCheckBox.isSelected());
    X3dOptions.setCadFilterGenerateNormals    (generateNormalsCheckBox.isSelected());
    X3dOptions.setCadFilterIndexFilter        (indexFilterCheckBox.isSelected());
    X3dOptions.setCadFilterIFStoITS           (IFStoITSCheckBox.isSelected());
    X3dOptions.setCadFilterIFStoTS            (IFStoTSCheckBox.isSelected());
    X3dOptions.setCadFilterLogLevel           ((String)logLevelCB.getSelectedItem());
    X3dOptions.setCadFilterMaterialFilter     (materialFilterCheckBox.isSelected());
    X3dOptions.setCadFilterMinimumProfile     (minimumProfileCheckBox.isSelected());
    X3dOptions.setCadFilterModifyViewpoint    (modifyViewpointCheckBox.isSelected());
    X3dOptions.setCadFilterReindex            (reIndexCheckBox.isSelected());
    X3dOptions.setCadFilterTriangleCount      (triangleCountCheckBox.isSelected());
    X3dOptions.setCadFilterTriangulationFilter(triangulationFilterCheckBox.isSelected());
    X3dOptions.setCadFilterX3dVersion         ((String)x3dVersionCB.getSelectedItem());
   
    badSets = badSets.trim();
    if(badSets.length() > 0) {
      String s0 = NbBundle.getMessage(getClass(),"NumberFormatErrorString0_");
      String s1 = NbBundle.getMessage(getClass(),"NumberFormatErrorString1");
      JOptionPane.showMessageDialog(this,s0+badSets+s1,NbBundle.getMessage(getClass(), "NumberFormatErrorDialogTitle"),
                                    JOptionPane.ERROR_MESSAGE);
    }
  }

  public void resetAll()
  {
    X3dOptions.resetAllCadFilterPreferences();
    setValues();
  }
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        sceneResultsPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        x3dVersionCB = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        logLevelCB = new javax.swing.JComboBox();
        triangleCountCheckBox = new javax.swing.JCheckBox();
        minimumProfileCheckBox = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        binaryCompressionMethodCB = new javax.swing.JComboBox();
        embedPrototypesCheckBox = new javax.swing.JCheckBox();
        identityFilterRB = new javax.swing.JRadioButton();
        cadFiltersRB = new javax.swing.JRadioButton();
        resetButton = new javax.swing.JButton();
        filterMethodsPanel = new javax.swing.JPanel();
        absoluteScaleFactorTF = new javax.swing.JTextField();
        absoluteScaleFactorLabel = new javax.swing.JLabel();
        floatingPointQuantizationLabel = new javax.swing.JLabel();
        floatingPointQuantizationTF = new javax.swing.JTextField();
        addBoundingBoxesCheckBox = new javax.swing.JCheckBox();
        appearanceFilterCheckBox = new javax.swing.JCheckBox();
        centerFilterCheckBox = new javax.swing.JCheckBox();
        combineAppearancesCheckBox = new javax.swing.JCheckBox();
        combineShapesCheckBox = new javax.swing.JCheckBox();
        defNameShortenedCheckBox = new javax.swing.JCheckBox();
        defUseImageTextureCheckBox = new javax.swing.JCheckBox();
        flattenTransformsCheckBox = new javax.swing.JCheckBox();
        flattenSelectableCheckBox = new javax.swing.JCheckBox();
        flattenTextureTransformsCheckBox = new javax.swing.JCheckBox();
        generateNormalsCheckBox = new javax.swing.JCheckBox();
        indexFilterCheckBox = new javax.swing.JCheckBox();
        IFStoITSCheckBox = new javax.swing.JCheckBox();
        IFStoTSCheckBox = new javax.swing.JCheckBox();
        materialFilterCheckBox = new javax.swing.JCheckBox();
        modifyViewpointCheckBox = new javax.swing.JCheckBox();
        triangulationFilterCheckBox = new javax.swing.JCheckBox();
        timeConsumingPanel = new javax.swing.JPanel();
        reIndexCheckBox = new javax.swing.JCheckBox();
        debugCheckBox = new javax.swing.JCheckBox();
        spacerLabel = new javax.swing.JLabel();
        verticalSpacerLabel = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        sceneResultsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(getClass(), "Xj3dCadFilterOptionsPanel.sceneResultsPanel.border.title"))); // NOI18N
        sceneResultsPanel.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText(org.openide.util.NbBundle.getMessage(getClass(), "Xj3dCadFilterOptionsPanel.jLabel1.text")); // NOI18N
        jLabel1.setToolTipText(org.openide.util.NbBundle.getMessage(Xj3dCadFilterOptionsPanel.class, "Xj3dCadFilterOptionsPanel.jLabel1.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 5);
        sceneResultsPanel.add(jLabel1, gridBagConstraints);

        x3dVersionCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "3.0", "3.1", "3.2", "3.3", "4.0" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 2);
        sceneResultsPanel.add(x3dVersionCB, gridBagConstraints);

        jLabel2.setText(org.openide.util.NbBundle.getMessage(getClass(), "Xj3dCadFilterOptionsPanel.jLabel2.text")); // NOI18N
        jLabel2.setToolTipText(org.openide.util.NbBundle.getMessage(Xj3dCadFilterOptionsPanel.class, "Xj3dCadFilterOptionsPanel.jLabel2.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 5);
        sceneResultsPanel.add(jLabel2, gridBagConstraints);

        logLevelCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "ALL", "WARNINGS", "ERRORS", "FATAL", "NONE" }));
        logLevelCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                logLevelCBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 2);
        sceneResultsPanel.add(logLevelCB, gridBagConstraints);

        triangleCountCheckBox.setText(org.openide.util.NbBundle.getMessage(getClass(), "Xj3dCadFilterOptionsPanel.triangleCountCheckBox.text")); // NOI18N
        triangleCountCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(Xj3dCadFilterOptionsPanel.class, "Xj3dCadFilterOptionsPanel.triangleCountCheckBox.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 5);
        sceneResultsPanel.add(triangleCountCheckBox, gridBagConstraints);

        minimumProfileCheckBox.setText(org.openide.util.NbBundle.getMessage(getClass(), "Xj3dCadFilterOptionsPanel.minimumProfileCheckBox.text")); // NOI18N
        minimumProfileCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(Xj3dCadFilterOptionsPanel.class, "Xj3dCadFilterOptionsPanel.minimumProfileCheckBox.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 4);
        sceneResultsPanel.add(minimumProfileCheckBox, gridBagConstraints);

        jLabel3.setText(org.openide.util.NbBundle.getMessage(getClass(), "Xj3dCadFilterOptionsPanel.jLabel3.text")); // NOI18N
        jLabel3.setToolTipText(org.openide.util.NbBundle.getMessage(Xj3dCadFilterOptionsPanel.class, "Xj3dCadFilterOptionsPanel.jLabel3.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 5);
        sceneResultsPanel.add(jLabel3, gridBagConstraints);

        binaryCompressionMethodCB.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "FASTEST", "SMALLEST", "LOSSY", "STRINGS" }));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 2);
        sceneResultsPanel.add(binaryCompressionMethodCB, gridBagConstraints);

        embedPrototypesCheckBox.setText(org.openide.util.NbBundle.getMessage(getClass(), "Xj3dCadFilterOptionsPanel.embedPrototypesCheckBox.text")); // NOI18N
        embedPrototypesCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(Xj3dCadFilterOptionsPanel.class, "Xj3dCadFilterOptionsPanel.embedPrototypesCheckBox.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 5);
        sceneResultsPanel.add(embedPrototypesCheckBox, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 5);
        add(sceneResultsPanel, gridBagConstraints);

        buttonGroup1.add(identityFilterRB);
        identityFilterRB.setSelected(true);
        identityFilterRB.setText(org.openide.util.NbBundle.getMessage(getClass(), "Xj3dCadFilterOptionsPanel.identityFilterRB.text")); // NOI18N
        identityFilterRB.setToolTipText(org.openide.util.NbBundle.getMessage(Xj3dCadFilterOptionsPanel.class, "Xj3dCadFilterOptionsPanel.identityFilterRB.toolTipText")); // NOI18N
        identityFilterRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioButtsHandler(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 6, 0, 5);
        add(identityFilterRB, gridBagConstraints);

        buttonGroup1.add(cadFiltersRB);
        cadFiltersRB.setText(org.openide.util.NbBundle.getMessage(getClass(), "Xj3dCadFilterOptionsPanel.cadFiltersRB.text")); // NOI18N
        cadFiltersRB.setToolTipText(org.openide.util.NbBundle.getMessage(Xj3dCadFilterOptionsPanel.class, "Xj3dCadFilterOptionsPanel.cadFiltersRB.toolTipText")); // NOI18N
        cadFiltersRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioButtsHandler(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 6, 5, 5);
        add(cadFiltersRB, gridBagConstraints);

        resetButton.setText(NbBundle.getMessage(getClass(), "Xj3dCadFilterOptionsPanel.resetButton.text")); // NOI18N
        resetButton.setToolTipText(org.openide.util.NbBundle.getMessage(Xj3dCadFilterOptionsPanel.class, "Xj3dCadFilterOptionsPanel.resetButton.toolTipText")); // NOI18N
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        add(resetButton, gridBagConstraints);

        filterMethodsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(getClass(), "Xj3dCadFilterOptionsPanel.filterMethodsPanel.border.title"))); // NOI18N
        filterMethodsPanel.setLayout(new java.awt.GridBagLayout());

        absoluteScaleFactorTF.setColumns(5);
        absoluteScaleFactorTF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        absoluteScaleFactorTF.setText(org.openide.util.NbBundle.getMessage(getClass(), "Xj3dCadFilterOptionsPanel.absoluteScaleFactorTF.text")); // NOI18N
        absoluteScaleFactorTF.setMinimumSize(new java.awt.Dimension(61, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 2);
        filterMethodsPanel.add(absoluteScaleFactorTF, gridBagConstraints);

        absoluteScaleFactorLabel.setText(org.openide.util.NbBundle.getMessage(getClass(), "Xj3dCadFilterOptionsPanel.absoluteScaleFactorLabel.text")); // NOI18N
        absoluteScaleFactorLabel.setToolTipText(org.openide.util.NbBundle.getMessage(Xj3dCadFilterOptionsPanel.class, "Xj3dCadFilterOptionsPanel.absoluteScaleFactorLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 5);
        filterMethodsPanel.add(absoluteScaleFactorLabel, gridBagConstraints);

        floatingPointQuantizationLabel.setText(org.openide.util.NbBundle.getMessage(getClass(), "Xj3dCadFilterOptionsPanel.floatingPointQuantizationLabel.text")); // NOI18N
        floatingPointQuantizationLabel.setToolTipText(org.openide.util.NbBundle.getMessage(Xj3dCadFilterOptionsPanel.class, "Xj3dCadFilterOptionsPanel.floatingPointQuantizationLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 2, 5, 5);
        filterMethodsPanel.add(floatingPointQuantizationLabel, gridBagConstraints);

        floatingPointQuantizationTF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        floatingPointQuantizationTF.setText(org.openide.util.NbBundle.getMessage(getClass(), "Xj3dCadFilterOptionsPanel.floatingPointQuantizationTF.text")); // NOI18N
        floatingPointQuantizationTF.setToolTipText(org.openide.util.NbBundle.getMessage(Xj3dCadFilterOptionsPanel.class, "Xj3dCadFilterOptionsPanel.floatingPointQuantizationTF.toolTipText")); // NOI18N
        floatingPointQuantizationTF.setMinimumSize(new java.awt.Dimension(6, 10));
        floatingPointQuantizationTF.setPreferredSize(new java.awt.Dimension(34, 10));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 2);
        filterMethodsPanel.add(floatingPointQuantizationTF, gridBagConstraints);

        addBoundingBoxesCheckBox.setText(org.openide.util.NbBundle.getMessage(getClass(), "Xj3dCadFilterOptionsPanel.addBoundingBoxesCheckBox.text")); // NOI18N
        addBoundingBoxesCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(Xj3dCadFilterOptionsPanel.class, "Xj3dCadFilterOptionsPanel.addBoundingBoxesCheckBox.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        filterMethodsPanel.add(addBoundingBoxesCheckBox, gridBagConstraints);

        appearanceFilterCheckBox.setText(org.openide.util.NbBundle.getMessage(Xj3dCadFilterOptionsPanel.class, "Xj3dCadFilterOptionsPanel.appearanceFilterCheckBox.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        filterMethodsPanel.add(appearanceFilterCheckBox, gridBagConstraints);

        centerFilterCheckBox.setText(org.openide.util.NbBundle.getMessage(getClass(), "Xj3dCadFilterOptionsPanel.centerFilterCheckBox.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        filterMethodsPanel.add(centerFilterCheckBox, gridBagConstraints);

        combineAppearancesCheckBox.setText(org.openide.util.NbBundle.getMessage(getClass(), "Xj3dCadFilterOptionsPanel.combineAppearancesCheckBox.text")); // NOI18N
        combineAppearancesCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(Xj3dCadFilterOptionsPanel.class, "Xj3dCadFilterOptionsPanel.combineAppearancesCheckBox.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        filterMethodsPanel.add(combineAppearancesCheckBox, gridBagConstraints);

        combineShapesCheckBox.setText(org.openide.util.NbBundle.getMessage(getClass(), "Xj3dCadFilterOptionsPanel.combineShapesCheckBox.text")); // NOI18N
        combineShapesCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(Xj3dCadFilterOptionsPanel.class, "Xj3dCadFilterOptionsPanel.combineShapesCheckBox.toolTipText")); // NOI18N
        combineShapesCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combineShapesCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        filterMethodsPanel.add(combineShapesCheckBox, gridBagConstraints);

        defNameShortenedCheckBox.setText(org.openide.util.NbBundle.getMessage(getClass(), "Xj3dCadFilterOptionsPanel.defNameShortenedCheckBox.text")); // NOI18N
        defNameShortenedCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(Xj3dCadFilterOptionsPanel.class, "Xj3dCadFilterOptionsPanel.defNameShortenedCheckBox.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        filterMethodsPanel.add(defNameShortenedCheckBox, gridBagConstraints);

        defUseImageTextureCheckBox.setText(org.openide.util.NbBundle.getMessage(getClass(), "Xj3dCadFilterOptionsPanel.defUseImageTextureCheckBox.text")); // NOI18N
        defUseImageTextureCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(Xj3dCadFilterOptionsPanel.class, "Xj3dCadFilterOptionsPanel.defUseImageTextureCheckBox.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        filterMethodsPanel.add(defUseImageTextureCheckBox, gridBagConstraints);

        flattenTransformsCheckBox.setText(org.openide.util.NbBundle.getMessage(getClass(), "Xj3dCadFilterOptionsPanel.flattenTransformsCheckBox.text")); // NOI18N
        flattenTransformsCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(Xj3dCadFilterOptionsPanel.class, "Xj3dCadFilterOptionsPanel.flattenTransformsCheckBox.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        filterMethodsPanel.add(flattenTransformsCheckBox, gridBagConstraints);

        flattenSelectableCheckBox.setText(org.openide.util.NbBundle.getMessage(getClass(), "Xj3dCadFilterOptionsPanel.flattenSelectableCheckBox.text")); // NOI18N
        flattenSelectableCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(Xj3dCadFilterOptionsPanel.class, "Xj3dCadFilterOptionsPanel.flattenSelectableCheckBox.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        filterMethodsPanel.add(flattenSelectableCheckBox, gridBagConstraints);

        flattenTextureTransformsCheckBox.setText(org.openide.util.NbBundle.getMessage(getClass(), "Xj3dCadFilterOptionsPanel.flattenTextureTransformsCheckBox.text")); // NOI18N
        flattenTextureTransformsCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(Xj3dCadFilterOptionsPanel.class, "Xj3dCadFilterOptionsPanel.flattenTextureTransformsCheckBox.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        filterMethodsPanel.add(flattenTextureTransformsCheckBox, gridBagConstraints);

        generateNormalsCheckBox.setText(org.openide.util.NbBundle.getMessage(getClass(), "Xj3dCadFilterOptionsPanel.generateNormalsCheckBox.text")); // NOI18N
        generateNormalsCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(Xj3dCadFilterOptionsPanel.class, "Xj3dCadFilterOptionsPanel.generateNormalsCheckBox.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        filterMethodsPanel.add(generateNormalsCheckBox, gridBagConstraints);

        indexFilterCheckBox.setText(org.openide.util.NbBundle.getMessage(getClass(), "Xj3dCadFilterOptionsPanel.indexFilterCheckBox.text")); // NOI18N
        indexFilterCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(Xj3dCadFilterOptionsPanel.class, "Xj3dCadFilterOptionsPanel.indexFilterCheckBox.toolTipText")); // NOI18N
        indexFilterCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                indexFilterCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        filterMethodsPanel.add(indexFilterCheckBox, gridBagConstraints);

        IFStoITSCheckBox.setText(org.openide.util.NbBundle.getMessage(getClass(), "Xj3dCadFilterOptionsPanel.IFStoITSCheckBox.text")); // NOI18N
        IFStoITSCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(Xj3dCadFilterOptionsPanel.class, "Xj3dCadFilterOptionsPanel.IFStoITSCheckBox.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        filterMethodsPanel.add(IFStoITSCheckBox, gridBagConstraints);

        IFStoTSCheckBox.setText(org.openide.util.NbBundle.getMessage(getClass(), "Xj3dCadFilterOptionsPanel.IFStoTSCheckBox.text")); // NOI18N
        IFStoTSCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(Xj3dCadFilterOptionsPanel.class, "Xj3dCadFilterOptionsPanel.IFStoTSCheckBox.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        filterMethodsPanel.add(IFStoTSCheckBox, gridBagConstraints);

        materialFilterCheckBox.setText(org.openide.util.NbBundle.getMessage(Xj3dCadFilterOptionsPanel.class, "Xj3dCadFilterOptionsPanel.materialFilterCheckBox.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        filterMethodsPanel.add(materialFilterCheckBox, gridBagConstraints);

        modifyViewpointCheckBox.setText(org.openide.util.NbBundle.getMessage(getClass(), "Xj3dCadFilterOptionsPanel.modifyViewpointCheckBox.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        filterMethodsPanel.add(modifyViewpointCheckBox, gridBagConstraints);

        triangulationFilterCheckBox.setText(org.openide.util.NbBundle.getMessage(getClass(), "Xj3dCadFilterOptionsPanel.triangulationFilterCheckBox.text")); // NOI18N
        triangulationFilterCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(Xj3dCadFilterOptionsPanel.class, "Xj3dCadFilterOptionsPanel.triangulationFilterCheckBox.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        filterMethodsPanel.add(triangulationFilterCheckBox, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 3.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 5, 5, 5);
        add(filterMethodsPanel, gridBagConstraints);

        timeConsumingPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(getClass(), "Xj3dCadFilterOptionsPanel.timeConsumingPanel.border.title"))); // NOI18N
        timeConsumingPanel.setToolTipText(org.openide.util.NbBundle.getMessage(Xj3dCadFilterOptionsPanel.class, "Xj3dCadFilterOptionsPanel.timeConsumingPanel.toolTipText")); // NOI18N
        timeConsumingPanel.setLayout(new java.awt.GridBagLayout());

        reIndexCheckBox.setText(org.openide.util.NbBundle.getMessage(getClass(), "Xj3dCadFilterOptionsPanel.reIndexCheckBox.text")); // NOI18N
        reIndexCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(Xj3dCadFilterOptionsPanel.class, "Xj3dCadFilterOptionsPanel.reIndexCheckBox.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        timeConsumingPanel.add(reIndexCheckBox, gridBagConstraints);

        debugCheckBox.setText(org.openide.util.NbBundle.getMessage(getClass(), "Xj3dCadFilterOptionsPanel.debugCheckBox.text")); // NOI18N
        debugCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(Xj3dCadFilterOptionsPanel.class, "Xj3dCadFilterOptionsPanel.debugCheckBox.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 5);
        timeConsumingPanel.add(debugCheckBox, gridBagConstraints);

        spacerLabel.setText(org.openide.util.NbBundle.getMessage(getClass(), "Xj3dCadFilterOptionsPanel.spacerLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        timeConsumingPanel.add(spacerLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 5, 0, 5);
        add(timeConsumingPanel, gridBagConstraints);

        verticalSpacerLabel.setText(org.openide.util.NbBundle.getMessage(Xj3dCadFilterOptionsPanel.class, "Xj3dCadFilterOptionsPanel.verticalSpacerLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(verticalSpacerLabel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

  private void resetButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_resetButtonActionPerformed
  {//GEN-HEADEREND:event_resetButtonActionPerformed
    resetAll();
}//GEN-LAST:event_resetButtonActionPerformed

private void radioButtsHandler(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioButtsHandler
  boolean wh = cadFiltersRB.isSelected();
  
  absoluteScaleFactorLabel.setEnabled(wh);
  absoluteScaleFactorTF.setEnabled(wh);
  addBoundingBoxesCheckBox.setEnabled(wh);
  appearanceFilterCheckBox.setEnabled(wh);
  centerFilterCheckBox.setEnabled(wh);
  combineAppearancesCheckBox.setEnabled(wh);
  combineShapesCheckBox.setEnabled(wh);
  defNameShortenedCheckBox.setEnabled(wh);
  defUseImageTextureCheckBox.setEnabled(wh);
  flattenTransformsCheckBox.setEnabled(wh);
  flattenTextureTransformsCheckBox.setEnabled(wh);
  flattenSelectableCheckBox.setEnabled(wh);
  floatingPointQuantizationLabel.setEnabled(wh);
  floatingPointQuantizationTF.setEnabled(wh);
  generateNormalsCheckBox.setEnabled(wh);
  indexFilterCheckBox.setEnabled(wh);
  IFStoITSCheckBox.setEnabled(wh);
  IFStoTSCheckBox.setEnabled(wh);
  materialFilterCheckBox.setEnabled(wh);
  modifyViewpointCheckBox.setEnabled(wh);
  triangulationFilterCheckBox.setEnabled(wh);
}//GEN-LAST:event_radioButtsHandler

private void combineShapesCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combineShapesCheckBoxActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_combineShapesCheckBoxActionPerformed

private void indexFilterCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_indexFilterCheckBoxActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_indexFilterCheckBoxActionPerformed

    private void logLevelCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_logLevelCBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_logLevelCBActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox IFStoITSCheckBox;
    private javax.swing.JCheckBox IFStoTSCheckBox;
    private javax.swing.JLabel absoluteScaleFactorLabel;
    private javax.swing.JTextField absoluteScaleFactorTF;
    private javax.swing.JCheckBox addBoundingBoxesCheckBox;
    private javax.swing.JCheckBox appearanceFilterCheckBox;
    private javax.swing.JComboBox binaryCompressionMethodCB;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JRadioButton cadFiltersRB;
    private javax.swing.JCheckBox centerFilterCheckBox;
    private javax.swing.JCheckBox combineAppearancesCheckBox;
    private javax.swing.JCheckBox combineShapesCheckBox;
    private javax.swing.JCheckBox debugCheckBox;
    private javax.swing.JCheckBox defNameShortenedCheckBox;
    private javax.swing.JCheckBox defUseImageTextureCheckBox;
    private javax.swing.JCheckBox embedPrototypesCheckBox;
    private javax.swing.JPanel filterMethodsPanel;
    private javax.swing.JCheckBox flattenSelectableCheckBox;
    private javax.swing.JCheckBox flattenTextureTransformsCheckBox;
    private javax.swing.JCheckBox flattenTransformsCheckBox;
    private javax.swing.JLabel floatingPointQuantizationLabel;
    private javax.swing.JTextField floatingPointQuantizationTF;
    private javax.swing.JCheckBox generateNormalsCheckBox;
    private javax.swing.JRadioButton identityFilterRB;
    private javax.swing.JCheckBox indexFilterCheckBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JComboBox logLevelCB;
    private javax.swing.JCheckBox materialFilterCheckBox;
    private javax.swing.JCheckBox minimumProfileCheckBox;
    private javax.swing.JCheckBox modifyViewpointCheckBox;
    private javax.swing.JCheckBox reIndexCheckBox;
    private javax.swing.JButton resetButton;
    private javax.swing.JPanel sceneResultsPanel;
    private javax.swing.JLabel spacerLabel;
    private javax.swing.JPanel timeConsumingPanel;
    private javax.swing.JCheckBox triangleCountCheckBox;
    private javax.swing.JCheckBox triangulationFilterCheckBox;
    private javax.swing.JLabel verticalSpacerLabel;
    private javax.swing.JComboBox x3dVersionCB;
    // End of variables declaration//GEN-END:variables
}
