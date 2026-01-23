/*
Copyright (c) 1995-2026 held by the author(s).  All rights reserved.
 
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.text.JTextComponent;
import org.openide.util.HelpCtx;
import org.web3d.x3d.types.X3DPrimitiveTypes;
import static org.web3d.x3d.types.X3DSchemaData.APPEARANCE_ATTR_ALPHAMODE_CHOICES;
/**
 * APPEARANCECustomizer.java
 * Created on August 2, 2007, 2:46 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class APPEARANCECustomizer extends BaseCustomizer
{
  private final APPEARANCE appearance;
  private final JTextComponent target;
  
  private boolean hasMaterial              = false;
  private boolean hasTwoSidedMaterial      = false;
  private boolean hasImageTexture          = false;
  private boolean hasPixelTexture          = false;
  private boolean hasMovieTexture          = false;
  private boolean hasMultiTexture          = false;
  private boolean hasTextureTransform      = false;
  private boolean hasMultiTextureTransform = false;
  
  private boolean hasAcousticProperties    = false;
  private boolean hasFillProperties        = false;
  private boolean hasLineProperties        = false;
  private boolean hasPointProperties       = false;

  java.awt.event.ActionEvent event = new java.awt.event.ActionEvent(APPEARANCECustomizer.this,0,"initialize");
  
  public APPEARANCECustomizer(APPEARANCE appearance, JTextComponent target)
  {
    super(appearance);
    this.appearance = appearance;
    this.target = target;
    
    HelpCtx.setHelpIDString(this, "APPEARANCE_ELEM_HELPID");
    
    initComponents();
    
    alphaCutoffTF.setText(appearance.getAlphaCutoff().toString());
    alphaModeCombo.setSelectedItem(appearance.getAlphaMode());
    
    if (appearance.getContent().trim().isEmpty())
    {
          newContentRadioButton.setSelected(true);
        priorContentRadioButton.setEnabled(false);
    }
    else
    {
        priorContentRadioButton.setSelected(true);
        priorContentRadioButton.setToolTipText(appearance.getContent());

        priorContentRadioButtonActionPerformed (event);
    }
    initializeContentSelections();
  }
  public final void initializeContentSelections()
  {
    hasMaterial              = false;
    hasTwoSidedMaterial      = false;
    hasImageTexture          = false;
    hasPixelTexture          = false;
    hasMovieTexture          = false;
    hasMultiTexture          = false;
    hasTextureTransform      = false;
    hasMultiTextureTransform = false;
   
    hasAcousticProperties    = false;
    hasFillProperties        = false;
    hasLineProperties        = false;
    hasPointProperties       = false;
    
    String content = appearance.getContent().trim();
    
    if (content.contains("<Material "))
        hasMaterial              = true;
    materialRadioButton.setSelected(hasMaterial);
    if (content.contains("<TwoSidedMaterial "))
        hasTwoSidedMaterial              = true;
    twoSidedMaterialRadioButton.setSelected(hasTwoSidedMaterial);
    
    // special logic for material button group, lock buttons if any are selected
    if (hasMaterial || hasTwoSidedMaterial)
    {
            materialRadioButton.setEnabled(false);
    twoSidedMaterialRadioButton.setEnabled(false);
    }
    else // no selections during initialization, remain open for additions
    {
            materialRadioButton.setEnabled(true);
    twoSidedMaterialRadioButton.setEnabled(true);
    }
    
    if (content.contains("<ImageTexture "))
        hasImageTexture              = true;
    imageTextureRadioButton.setSelected(hasImageTexture);

    if (content.contains("<MovieTexture "))
        hasMovieTexture              = true;
    movieTextureRadioButton.setSelected(hasMovieTexture);
    
    if (content.contains("<PixelTexture "))
        hasPixelTexture              = true;
    pixelTextureRadioButton.setSelected(hasPixelTexture);

    // special logic for texture button group, lock buttons if any are selected
    if (hasImageTexture || hasMovieTexture || hasPixelTexture)
    {
        imageTextureRadioButton.setEnabled(false);
        movieTextureRadioButton.setEnabled(false);
        pixelTextureRadioButton.setEnabled(false);
    }
    else
    {
        imageTextureRadioButton.setEnabled(true);
        movieTextureRadioButton.setEnabled(true);
        pixelTextureRadioButton.setEnabled(true);
    }
    // remaining selections are checkboxes and not radio buttons, no further gymnastics needed when locking selections   

    if (content.contains("<MultiTexture "))
        hasMultiTexture              = true;
    multiTextureCheckBox.setSelected(hasMultiTexture);
    multiTextureCheckBox.setEnabled(!hasMultiTexture);

    if (content.contains("<TextureTransform "))
        hasTextureTransform              = true;
    textureTransformCheckBox.setSelected(hasTextureTransform);
    textureTransformCheckBox.setEnabled(!hasTextureTransform);

    if (content.contains("<MultiTextureTransform "))
        hasMultiTextureTransform              = true;
    multiTextureTransformCheckBox.setSelected(hasMultiTextureTransform);
    multiTextureTransformCheckBox.setEnabled(!hasMultiTextureTransform);

    if (content.contains("<AcousticProperties "))
        hasAcousticProperties    = true;
    acousticPropertiesCheckBox.setSelected(hasAcousticProperties);
    acousticPropertiesCheckBox.setEnabled(!hasAcousticProperties);
        
    if (content.contains("<FillProperties "))
        hasFillProperties        = true;
    fillPropertiesCheckBox.setSelected(hasFillProperties);
    fillPropertiesCheckBox.setEnabled(!hasFillProperties);

    if (content.contains("<LineProperties "))
        hasLineProperties        = true;
    linePropertiesCheckBox.setSelected(hasLineProperties);
    linePropertiesCheckBox.setEnabled(!hasLineProperties);
    
    if (content.contains("<PointProperties "))
        hasLineProperties        = true;
    pointPropertiesCheckBox.setSelected(hasLineProperties);
    pointPropertiesCheckBox.setEnabled(!hasLineProperties);
  }
  
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        childContentButtonGroup = new javax.swing.ButtonGroup();
        materialButtonGroup = new javax.swing.ButtonGroup();
        textureButtonGroup = new javax.swing.ButtonGroup();
        dEFUSEpanel1 = getDEFUSEpanel();
        alphaCutoffLabel = new javax.swing.JLabel();
        alphaCutoffTF = new javax.swing.JTextField();
        alphaModeLabel = new javax.swing.JLabel();
        alphaModeCombo = new javax.swing.JComboBox<>();
        contentSelectionPanel = new javax.swing.JPanel();
        contentSelectionLabel = new javax.swing.JLabel();
        newContentRadioButton = new javax.swing.JRadioButton();
        priorContentRadioButton = new javax.swing.JRadioButton();
        noContentRadioButton = new javax.swing.JRadioButton();
        clearAllButton = new javax.swing.JButton();
        selectionPanel = new javax.swing.JPanel();
        materialRadioButton = new javax.swing.JRadioButton();
        twoSidedMaterialRadioButton = new javax.swing.JRadioButton();
        linePropertiesCheckBox = new javax.swing.JCheckBox();
        fillPropertiesCheckBox = new javax.swing.JCheckBox();
        jSeparator2 = new javax.swing.JSeparator();
        imageTextureRadioButton = new javax.swing.JRadioButton();
        movieTextureRadioButton = new javax.swing.JRadioButton();
        pixelTextureRadioButton = new javax.swing.JRadioButton();
        multiTextureCheckBox = new javax.swing.JCheckBox();
        textureTransformCheckBox = new javax.swing.JCheckBox();
        multiTextureTransformCheckBox = new javax.swing.JCheckBox();
        acousticPropertiesCheckBox = new javax.swing.JCheckBox();
        pointPropertiesCheckBox = new javax.swing.JCheckBox();
        appearanceHintLabel = new javax.swing.JLabel();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setMinimumSize(new java.awt.Dimension(620, 540));
        setName(""); // NOI18N
        setPreferredSize(new java.awt.Dimension(620, 540));
        setLayout(new java.awt.GridBagLayout());

        dEFUSEpanel1.setMaximumSize(null);
        dEFUSEpanel1.setMinimumSize(new java.awt.Dimension(600, 120));
        dEFUSEpanel1.setPreferredSize(new java.awt.Dimension(600, 120));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel1, gridBagConstraints);

        alphaCutoffLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        alphaCutoffLabel.setText("alphaCutoff");
        alphaCutoffLabel.setToolTipText("interval in seconds before automatic reload of current url asset is performed");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 10, 3, 3);
        add(alphaCutoffLabel, gridBagConstraints);

        alphaCutoffTF.setToolTipText("interval in seconds before automatic reload of current url asset is performed");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(alphaCutoffTF, gridBagConstraints);

        alphaModeLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        alphaModeLabel.setText("alphaMode");
        alphaModeLabel.setToolTipText("select family or enter quoted font names, browsers use first supported family (e.g. \"Arial\" \"SANS\")");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(alphaModeLabel, gridBagConstraints);

        alphaModeCombo.setEditable(true);
        alphaModeCombo.setModel(new DefaultComboBoxModel<String>(APPEARANCE_ATTR_ALPHAMODE_CHOICES));
        alphaModeCombo.setToolTipText("select family or enter quoted font names, browsers use first supported family (e.g. \"Arial\" \"SANS\")");
        alphaModeCombo.setMinimumSize(new java.awt.Dimension(100, 20));
        alphaModeCombo.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(alphaModeCombo, gridBagConstraints);

        contentSelectionPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        contentSelectionPanel.setLayout(new java.awt.GridBagLayout());

        contentSelectionLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        contentSelectionLabel.setText("child content");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        contentSelectionPanel.add(contentSelectionLabel, gridBagConstraints);

        childContentButtonGroup.add(newContentRadioButton);
        newContentRadioButton.setSelected(true);
        newContentRadioButton.setText("new nodes");
        newContentRadioButton.setToolTipText("Select new geometry from choices at right");
        newContentRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newContentRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        contentSelectionPanel.add(newContentRadioButton, gridBagConstraints);

        childContentButtonGroup.add(priorContentRadioButton);
        priorContentRadioButton.setText("prior nodes");
        priorContentRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                priorContentRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        contentSelectionPanel.add(priorContentRadioButton, gridBagConstraints);

        childContentButtonGroup.add(noContentRadioButton);
        noContentRadioButton.setText("no nodes");
        noContentRadioButton.setToolTipText("empty Shape");
        noContentRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noContentRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        contentSelectionPanel.add(noContentRadioButton, gridBagConstraints);

        clearAllButton.setText("clear all");
        clearAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearAllButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        contentSelectionPanel.add(clearAllButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(contentSelectionPanel, gridBagConstraints);

        selectionPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        selectionPanel.setEnabled(false);
        selectionPanel.setLayout(new java.awt.GridBagLayout());

        materialButtonGroup.add(materialRadioButton);
        materialRadioButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        materialRadioButton.setSelected(true);
        materialRadioButton.setText("Material");
        materialRadioButton.setToolTipText("material or backMaterial");
        materialRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                materialRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(6, 36, 2, 2);
        selectionPanel.add(materialRadioButton, gridBagConstraints);

        materialButtonGroup.add(twoSidedMaterialRadioButton);
        twoSidedMaterialRadioButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        twoSidedMaterialRadioButton.setText("TwoSidedMaterial");
        twoSidedMaterialRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                twoSidedMaterialRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(2, 36, 2, 2);
        selectionPanel.add(twoSidedMaterialRadioButton, gridBagConstraints);

        linePropertiesCheckBox.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        linePropertiesCheckBox.setText("LineProperties");
        linePropertiesCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                linePropertiesCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        selectionPanel.add(linePropertiesCheckBox, gridBagConstraints);

        fillPropertiesCheckBox.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        fillPropertiesCheckBox.setText("FillProperties");
        fillPropertiesCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fillPropertiesCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(6, 2, 2, 2);
        selectionPanel.add(fillPropertiesCheckBox, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 0);
        selectionPanel.add(jSeparator2, gridBagConstraints);

        textureButtonGroup.add(imageTextureRadioButton);
        imageTextureRadioButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        imageTextureRadioButton.setText("ImageTexture");
        imageTextureRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imageTextureRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 36, 2, 2);
        selectionPanel.add(imageTextureRadioButton, gridBagConstraints);

        textureButtonGroup.add(movieTextureRadioButton);
        movieTextureRadioButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        movieTextureRadioButton.setText("MovieTexture");
        movieTextureRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                movieTextureRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 36, 2, 2);
        selectionPanel.add(movieTextureRadioButton, gridBagConstraints);

        textureButtonGroup.add(pixelTextureRadioButton);
        pixelTextureRadioButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        pixelTextureRadioButton.setText("PixelTexture");
        pixelTextureRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pixelTextureRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 36, 2, 2);
        selectionPanel.add(pixelTextureRadioButton, gridBagConstraints);

        multiTextureCheckBox.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        multiTextureCheckBox.setText("MultiTexture");
        multiTextureCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                multiTextureCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        selectionPanel.add(multiTextureCheckBox, gridBagConstraints);

        textureTransformCheckBox.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        textureTransformCheckBox.setText("TextureTransform");
        textureTransformCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textureTransformCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        selectionPanel.add(textureTransformCheckBox, gridBagConstraints);

        multiTextureTransformCheckBox.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        multiTextureTransformCheckBox.setText("MultiTextureTransform");
        multiTextureTransformCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                multiTextureTransformCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        selectionPanel.add(multiTextureTransformCheckBox, gridBagConstraints);

        acousticPropertiesCheckBox.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        acousticPropertiesCheckBox.setText("AcousticProperties");
        acousticPropertiesCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acousticPropertiesCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(6, 2, 2, 2);
        selectionPanel.add(acousticPropertiesCheckBox, gridBagConstraints);

        pointPropertiesCheckBox.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        pointPropertiesCheckBox.setText("PointProperties");
        pointPropertiesCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pointPropertiesCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(6, 2, 2, 2);
        selectionPanel.add(pointPropertiesCheckBox, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(selectionPanel, gridBagConstraints);

        appearanceHintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        appearanceHintLabel.setText("<html><p align=\"center\"><b>Appearance</b> can contain multiple nodes whose rendering  properties are applied \n<br />\nto the adjacent geometry node found inside the shared parent <b>Shape</b> node.</p> \n<ul>\n  <li> <b>AcousticProperties</b>, <b>FillProperties</b>,<b>LineProperties</b>, <b>PointProperties</b>  </li>\n  <li> <b>ComposedShader</b>, <b>PackagedShader</b>, <b>ProgramShader</b> </li>\n  <li> <b>ComposedTexture3D</b>, <b>ImageTexture3D</b>, <b>PixelTexture3D</b></li>\n  <li> <b>ComposedCubeMapTexture</b>, <b>GeneratedCubeMapTexture</b>, <b>ImageCubeMapTexture</b> </li>\n  <li> <b>TextureMatrixTransform</b>, <b>TextureTransform3D</b> </li>\n</ul>\n<p align=\"center\">Hint:  reuse of <b>Appearance</b> via DEF/USE  provides a similar look + feel for related shapes in a scene. </p>");
        appearanceHintLabel.setToolTipText("Appearance specifies the visual properties of geometry");
        appearanceHintLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        appearanceHintLabel.setMinimumSize(new java.awt.Dimension(600, 150));
        appearanceHintLabel.setPreferredSize(new java.awt.Dimension(600, 150));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(appearanceHintLabel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void noContentRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noContentRadioButtonActionPerformed
        selectionPanel.setEnabled(false);
        
                materialRadioButton.setEnabled(false);
        twoSidedMaterialRadioButton.setEnabled(false);
             fillPropertiesCheckBox.setEnabled(false);
             linePropertiesCheckBox.setEnabled(false);
            imageTextureRadioButton.setEnabled(false);
            movieTextureRadioButton.setEnabled(false);
            pixelTextureRadioButton.setEnabled(false);
           textureTransformCheckBox.setEnabled(false);
               multiTextureCheckBox.setEnabled(false);
      multiTextureTransformCheckBox.setEnabled(false);
           
        // remember prior selections
//        materialButtonGroup.clearSelection();
//        textureButtonGroup.clearSelection();
    }//GEN-LAST:event_noContentRadioButtonActionPerformed

    private void priorContentRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_priorContentRadioButtonActionPerformed
        selectionPanel.setEnabled(false);
        
                materialRadioButton.setEnabled(false);
        twoSidedMaterialRadioButton.setEnabled(false);
             fillPropertiesCheckBox.setEnabled(false);
             linePropertiesCheckBox.setEnabled(false);
            imageTextureRadioButton.setEnabled(false);
            movieTextureRadioButton.setEnabled(false);
            pixelTextureRadioButton.setEnabled(false);
           textureTransformCheckBox.setEnabled(false);
               multiTextureCheckBox.setEnabled(false);
      multiTextureTransformCheckBox.setEnabled(false);
            
        materialButtonGroup.clearSelection();
         textureButtonGroup.clearSelection();
         
         // TODO warn if duplicative nodes encountered

        if       (appearance.getContent().contains("<Material"))
        {
            materialRadioButton.setSelected(true);
        }
        if (appearance.getContent().contains("<TwoSidedMaterial"))
        {
            twoSidedMaterialRadioButton.setSelected(true);
        }
        if (appearance.getContent().contains("<FillProperties"))
        {
            fillPropertiesCheckBox.setSelected(true);
        }
        if (appearance.getContent().contains("<LineProperties"))
        {
            linePropertiesCheckBox.setSelected(true);
        }
        if      (appearance.getContent().contains("<ImageTexture"))
        {
            imageTextureRadioButton.setSelected(true);
        }
        if (appearance.getContent().contains("<MovieTexture"))
        {
            movieTextureRadioButton.setSelected(true);
        }
        if (appearance.getContent().contains("<PixelTexture"))
        {
            pixelTextureRadioButton.setSelected(true);
        }
        if (appearance.getContent().contains("<TextureTransform"))
        {
            textureTransformCheckBox.setSelected(true);
        }
        if (appearance.getContent().contains("<MultiTexture"))
        {
            multiTextureCheckBox.setSelected(true);
        }
        if (appearance.getContent().contains("<MultiTextureTransform"))
        {
            multiTextureTransformCheckBox.setSelected(true);
        }

        priorContentRadioButton.setSelected(true); // restore status since other callbacks may reset it
    }//GEN-LAST:event_priorContentRadioButtonActionPerformed

    private void newContentRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newContentRadioButtonActionPerformed
        selectionPanel.setEnabled(newContentRadioButton.isSelected());
        
                materialRadioButton.setEnabled(true);
        twoSidedMaterialRadioButton.setEnabled(true);
         acousticPropertiesCheckBox.setEnabled(true);
             fillPropertiesCheckBox.setEnabled(true);
             linePropertiesCheckBox.setEnabled(true);
            pointPropertiesCheckBox.setEnabled(true);
            imageTextureRadioButton.setEnabled(true);
            movieTextureRadioButton.setEnabled(true);
            pixelTextureRadioButton.setEnabled(true);
           textureTransformCheckBox.setEnabled(true);
               multiTextureCheckBox.setEnabled(true);
      multiTextureTransformCheckBox.setEnabled(true);
    }//GEN-LAST:event_newContentRadioButtonActionPerformed

    private void clearAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearAllButtonActionPerformed
              newContentRadioButton.setSelected(false);
               noContentRadioButton.setSelected(true);
                     selectionPanel.setEnabled(true); 
                     
              appearance.setContent(""); // TODO warn
                materialRadioButton.setSelected(false);
        twoSidedMaterialRadioButton.setSelected(false);
         acousticPropertiesCheckBox.setSelected(false);
             fillPropertiesCheckBox.setSelected(false);
             linePropertiesCheckBox.setSelected(false);
            pointPropertiesCheckBox.setSelected(false);
            imageTextureRadioButton.setSelected(false);
            movieTextureRadioButton.setSelected(false);
            pixelTextureRadioButton.setSelected(false);
           textureTransformCheckBox.setSelected(false);
               multiTextureCheckBox.setSelected(false);
      multiTextureTransformCheckBox.setSelected(false);
                materialRadioButton.setEnabled(true);
        twoSidedMaterialRadioButton.setEnabled(true);
         acousticPropertiesCheckBox.setEnabled(true);
             fillPropertiesCheckBox.setEnabled(true);
             linePropertiesCheckBox.setEnabled(true);
            pointPropertiesCheckBox.setEnabled(true);
            imageTextureRadioButton.setEnabled(true);
            movieTextureRadioButton.setEnabled(true);
            pixelTextureRadioButton.setEnabled(true);
           textureTransformCheckBox.setEnabled(true);
               multiTextureCheckBox.setEnabled(true);
      multiTextureTransformCheckBox.setEnabled(true);
           
             materialButtonGroup.clearSelection();
              textureButtonGroup.clearSelection();
    }//GEN-LAST:event_clearAllButtonActionPerformed

    private void textureTransformCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textureTransformCheckBoxActionPerformed
        newContentRadioButton.setSelected(true);
    }//GEN-LAST:event_textureTransformCheckBoxActionPerformed

    private void multiTextureCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_multiTextureCheckBoxActionPerformed
        newContentRadioButton.setSelected(true);
    }//GEN-LAST:event_multiTextureCheckBoxActionPerformed

    private void multiTextureTransformCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_multiTextureTransformCheckBoxActionPerformed
        newContentRadioButton.setSelected(true);
    }//GEN-LAST:event_multiTextureTransformCheckBoxActionPerformed

    private void twoSidedMaterialRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_twoSidedMaterialRadioButtonActionPerformed
        newContentRadioButton.setSelected(true);
    }//GEN-LAST:event_twoSidedMaterialRadioButtonActionPerformed

    private void materialRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_materialRadioButtonActionPerformed
        newContentRadioButton.setSelected(true);
    }//GEN-LAST:event_materialRadioButtonActionPerformed

    private void imageTextureRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imageTextureRadioButtonActionPerformed
        newContentRadioButton.setSelected(true);
    }//GEN-LAST:event_imageTextureRadioButtonActionPerformed

    private void movieTextureRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_movieTextureRadioButtonActionPerformed
        newContentRadioButton.setSelected(true);
    }//GEN-LAST:event_movieTextureRadioButtonActionPerformed

    private void pixelTextureRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pixelTextureRadioButtonActionPerformed
        newContentRadioButton.setSelected(true);
    }//GEN-LAST:event_pixelTextureRadioButtonActionPerformed

    private void fillPropertiesCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fillPropertiesCheckBoxActionPerformed
        newContentRadioButton.setSelected(true);
    }//GEN-LAST:event_fillPropertiesCheckBoxActionPerformed

    private void linePropertiesCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_linePropertiesCheckBoxActionPerformed
        newContentRadioButton.setSelected(true);
    }//GEN-LAST:event_linePropertiesCheckBoxActionPerformed

    private void acousticPropertiesCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acousticPropertiesCheckBoxActionPerformed
        newContentRadioButton.setSelected(true);
    }//GEN-LAST:event_acousticPropertiesCheckBoxActionPerformed

    private void pointPropertiesCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pointPropertiesCheckBoxActionPerformed
        newContentRadioButton.setSelected(true);
    }//GEN-LAST:event_pointPropertiesCheckBoxActionPerformed
  
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox acousticPropertiesCheckBox;
    private javax.swing.JLabel alphaCutoffLabel;
    private javax.swing.JTextField alphaCutoffTF;
    private javax.swing.JComboBox<String> alphaModeCombo;
    private javax.swing.JLabel alphaModeLabel;
    private javax.swing.JLabel appearanceHintLabel;
    private javax.swing.ButtonGroup childContentButtonGroup;
    private javax.swing.JButton clearAllButton;
    private javax.swing.JLabel contentSelectionLabel;
    private javax.swing.JPanel contentSelectionPanel;
    private org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel1;
    private javax.swing.JCheckBox fillPropertiesCheckBox;
    private javax.swing.JRadioButton imageTextureRadioButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JCheckBox linePropertiesCheckBox;
    private javax.swing.ButtonGroup materialButtonGroup;
    private javax.swing.JRadioButton materialRadioButton;
    private javax.swing.JRadioButton movieTextureRadioButton;
    private javax.swing.JCheckBox multiTextureCheckBox;
    private javax.swing.JCheckBox multiTextureTransformCheckBox;
    private javax.swing.JRadioButton newContentRadioButton;
    private javax.swing.JRadioButton noContentRadioButton;
    private javax.swing.JRadioButton pixelTextureRadioButton;
    private javax.swing.JCheckBox pointPropertiesCheckBox;
    private javax.swing.JRadioButton priorContentRadioButton;
    private javax.swing.JPanel selectionPanel;
    private javax.swing.ButtonGroup textureButtonGroup;
    private javax.swing.JCheckBox textureTransformCheckBox;
    private javax.swing.JRadioButton twoSidedMaterialRadioButton;
    // End of variables declaration//GEN-END:variables

    
  /**
   * Check radio buttons and set content accordingly.
   * Use default (or preferred) values of interest for embedded nodes to provide a quickstart to new authors.
   */
  private void setContent ()
  {
    if (noContentRadioButton.isSelected())
    {
        appearance.setContent("");
        return;
    }
    else if (priorContentRadioButton.isSelected())
        return; // no change

    // adjust content to match selection
    StringBuilder newContent = new StringBuilder();
      
    String           MATERIAL_CONTENT    = "\t\t\t<Material/>\n";
    String   TWOSIDEDMATERIAL_CONTENT    = "\t\t\t<TwoSidedMaterial/>\n";
    String ACOUSTICPROPERTIES_CONTENT    = "\t\t\t<AcousticProperties/>\n";
    String     FILLPROPERTIES_CONTENT    = "\t\t\t<FillProperties/>\n";
    String     LINEPROPERTIES_CONTENT    = "\t\t\t<LineProperties/>\n";
    String    POINTPROPERTIES_CONTENT    = "\t\t\t<AcousticProperties/>\n";
    String       IMAGETEXTURE_CONTENT    = "\t\t\t<ImageTexture/>\n";
    String       MOVIETEXTURE_CONTENT    = "\t\t\t<MovieTexture/>\n";
    String       PIXELTEXTURE_CONTENT    = "\t\t\t<PixelTexture/>\n";
    String   TEXTURETRANSFORM_CONTENT    = "\t\t\t<TextureTransform/>\n";
    String MULTITEXTURE_CONTENT          = "\t\t\t<MultiTexture/>\n";
    String MULTITEXTURETRANSFORM_CONTENT = "\t\t\t<MultiTextureTransform/>\n";
    
    if      (!hasMaterial              &&         materialRadioButton.isSelected()) newContent.append(MATERIAL_CONTENT);
    else if (!hasTwoSidedMaterial      && twoSidedMaterialRadioButton.isSelected()) newContent.append(TWOSIDEDMATERIAL_CONTENT);
    
    if      (!hasAcousticProperties    && acousticPropertiesCheckBox.isSelected()) newContent.append(ACOUSTICPROPERTIES_CONTENT);
    if      (!hasFillProperties        &&     fillPropertiesCheckBox.isSelected()) newContent.append(FILLPROPERTIES_CONTENT);
    if      (!hasLineProperties        &&     linePropertiesCheckBox.isSelected()) newContent.append(LINEPROPERTIES_CONTENT);
    if      (!hasPointProperties       &&    pointPropertiesCheckBox.isSelected()) newContent.append(POINTPROPERTIES_CONTENT);
    
    if      (!hasImageTexture          &&    imageTextureRadioButton.isSelected()) newContent.append(IMAGETEXTURE_CONTENT);
    else if (!hasMovieTexture          &&    movieTextureRadioButton.isSelected()) newContent.append(MOVIETEXTURE_CONTENT);
    else if (!hasPixelTexture          &&    pixelTextureRadioButton.isSelected()) newContent.append(PIXELTEXTURE_CONTENT);
    
    if      (!hasTextureTransform      &&      textureTransformCheckBox.isSelected()) newContent.append(TEXTURETRANSFORM_CONTENT);
    if      (!hasMultiTexture          &&          multiTextureCheckBox.isSelected()) newContent.append(MULTITEXTURE_CONTENT);
    if      (!hasMultiTextureTransform && multiTextureTransformCheckBox.isSelected()) newContent.append(MULTITEXTURETRANSFORM_CONTENT);
    
    if (    !materialRadioButton.isSelected() && !twoSidedMaterialRadioButton.isSelected() && 
     !acousticPropertiesCheckBox.isSelected() && !linePropertiesCheckBox.isSelected()   && !fillPropertiesCheckBox.isSelected()  && !pointPropertiesCheckBox.isSelected() &&
        !imageTextureRadioButton.isSelected() && !movieTextureRadioButton.isSelected()  && !pixelTextureRadioButton.isSelected() && 
           !multiTextureCheckBox.isSelected() && !textureTransformCheckBox.isSelected() && !multiTextureTransformCheckBox.isSelected())
        newContent.append("\t\t\t<!--TODO add optional Material, TwoSidedMaterial, ImageTexture, MovieTexture, PixelTexture, TextureTransform, MultiTexture, MultiTextureTransform, AcousticProperties, FillProperties, LineProperties, and/or PointProperties nodes here-->\n\t\t");
    
    appearance.setContent(newContent.toString());
  }
  
  @Override
  public String getNameKey()
  {
    return "NAME_X3D_APPEARANCE";
  }

  @Override
  public void unloadInput() throws IllegalArgumentException
  {
    setContent ();
    appearance.setAlphaCutoff(new X3DPrimitiveTypes.SFFloat(alphaCutoffTF.getText(), 0.0f, 1.0f));
    appearance.setAlphaMode(alphaModeCombo.getSelectedItem().toString());
    
    unLoadDEFUSE();
  }  
}
