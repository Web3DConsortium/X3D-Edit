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

package org.web3d.x3d.palette.items;

import java.awt.Color;
import javax.swing.DefaultComboBoxModel;
import javax.swing.text.JTextComponent;
import org.openide.util.HelpCtx;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
/**
 * TEXTUREPROPERTIESCustomizer.java
 * Created on July 12, 2007, 3:05 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class TEXTUREPROPERTIESCustomizer extends BaseCustomizer
{
  private TEXTUREPROPERTIES textureProperties;
  private JTextComponent target;
  
  public TEXTUREPROPERTIESCustomizer(TEXTUREPROPERTIES textureProperties, JTextComponent target)
  {
    super(textureProperties);
    this.textureProperties = textureProperties;
    this.target = target;
    
    HelpCtx.setHelpIDString(this, "TEXTUREPROPERTIES_ELEM_HELPID");
    
    initComponents();

    anisotropicDegreeTF.setText(textureProperties.getAnisotropicDegree());
    
    String c0,c1,c2,c3;
    borderColor0TF.setText(c0 = textureProperties.getColor0());
    borderColor1TF.setText(c1 = textureProperties.getColor1());
    borderColor2TF.setText(c2 = textureProperties.getColor2());
    borderColor3TF.setText(c3 = textureProperties.getColor3());

    borderColorChooser.setColor((new SFColor(c0, c1, c2)).getColor());

    borderWidthCBox.setSelectedIndex(textureProperties.getBorderWidth());

    boundaryModeSCBox.setSelectedIndex(getBoundaryModeIndex(textureProperties.getBoundaryModeS()));
    boundaryModeTCBox.setSelectedIndex(getBoundaryModeIndex(textureProperties.getBoundaryModeT()));
    boundaryModeRCBox.setSelectedIndex(getBoundaryModeIndex(textureProperties.getBoundaryModeR()));

    generateMipMapsCB.setSelected(textureProperties.isGenerateMipMaps());

    magnificationFilterCBox.setSelectedIndex(getMagnificationModeIndex(textureProperties.getMagnificationFilter()));
     minificationFilterCBox.setSelectedIndex(getMinificationModeIndex (textureProperties.getMinificationFilter()));
     textureCompressionCBox.setSelectedIndex(getCompressionModeIndex  (textureProperties.getTextureCompression()));

    texturePriorityTF.setText(textureProperties.getTexturePriority());
  }

  private int getBoundaryModeIndex (String value)
  {
      for (int i=0; i < TEXTUREPROPERTIES_ATTR_BOUNDARYMODE_CHOICES.length; i++)
      {
          if (value.equals(TEXTUREPROPERTIES_ATTR_BOUNDARYMODE_CHOICES[i])) return i;
      }
      return -1; // no legal value found
  }

  private int getMagnificationModeIndex (String value)
  {
      for (int i=0; i < TEXTUREPROPERTIES_ATTR_MAGNIFICATIONMODE_CHOICES.length; i++)
      {
          if (value.equals(TEXTUREPROPERTIES_ATTR_MAGNIFICATIONMODE_CHOICES[i])) return i;
      }
      return -1; // no legal value found
  }

  private int getMinificationModeIndex (String value)
  {
      for (int i=0; i < TEXTUREPROPERTIES_ATTR_MINIFICATIONMODE_CHOICES.length; i++)
      {
          if (value.equals(TEXTUREPROPERTIES_ATTR_MINIFICATIONMODE_CHOICES[i])) return i;
      }
      return -1; // no legal value found
  }

  private int getCompressionModeIndex (String value)
  {
      for (int i=0; i < TEXTUREPROPERTIES_ATTR_COMPRESSIONMODE_CHOICES.length; i++)
      {
          if (value.equals(TEXTUREPROPERTIES_ATTR_COMPRESSIONMODE_CHOICES[i])) return i;
      }
      return -1; // no legal value found
  }
  
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        generateMipMapsLabel = new javax.swing.JLabel();
        generateMipMapsCB = new javax.swing.JCheckBox();
        borderColorLabel = new javax.swing.JLabel();
        borderColor0TF = new javax.swing.JTextField();
        borderWidthLabel = new javax.swing.JLabel();
        anisotropicDegreeLabel = new javax.swing.JLabel();
        org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel1 = getDEFUSEpanel();
        borderColor1TF = new javax.swing.JTextField();
        borderColor2TF = new javax.swing.JTextField();
        borderColorChooser = new net.java.dev.colorchooser.ColorChooser();
        borderWidthCBox = new javax.swing.JComboBox<String>();
        borderColor3TF = new javax.swing.JTextField();
        anisotropicDegreeTF = new javax.swing.JTextField();
        boundaryModeSLabel = new javax.swing.JLabel();
        boundaryModeSCBox = new javax.swing.JComboBox<String>();
        boundaryModeTLabel = new javax.swing.JLabel();
        boundaryModeTCBox = new javax.swing.JComboBox<String>();
        boundaryModeRLabel = new javax.swing.JLabel();
        boundaryModeRCBox = new javax.swing.JComboBox<String>();
        texturePriorityLabel = new javax.swing.JLabel();
        texturePriorityTF = new javax.swing.JTextField();
        magnificationFilterLabel = new javax.swing.JLabel();
        magnificationFilterCBox = new javax.swing.JComboBox<String>();
        minificationFilterLabel1 = new javax.swing.JLabel();
        minificationFilterCBox = new javax.swing.JComboBox<String>();
        textureCompressionLabel = new javax.swing.JLabel();
        textureCompressionCBox = new javax.swing.JComboBox<String>();
        nodeHintPanel = new javax.swing.JPanel();
        descriptionLabel = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        generateMipMapsLabel.setText("generateMipMaps");
        generateMipMapsLabel.setToolTipText("whether MIPMAPs are generated for texture (required for MIPMAP filtering modes)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        add(generateMipMapsLabel, gridBagConstraints);

        generateMipMapsCB.setToolTipText("whether MIPMAPs are generated for texture (required for MIPMAP filtering modes)");
        generateMipMapsCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(generateMipMapsCB, gridBagConstraints);

        borderColorLabel.setText("borderColor");
        borderColorLabel.setToolTipText("border pixel color");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        add(borderColorLabel, gridBagConstraints);

        borderColor0TF.setColumns(5);
        borderColor0TF.setToolTipText("border pixel red component");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(borderColor0TF, gridBagConstraints);

        borderWidthLabel.setText("borderWidth");
        borderWidthLabel.setToolTipText("number of pixels for texture border (0 or 1)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        add(borderWidthLabel, gridBagConstraints);

        anisotropicDegreeLabel.setText("anisotropicDegree");
        anisotropicDegreeLabel.setToolTipText("minimum degree of anisotropy to account for in texture filtering (1=none or higher value)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        add(anisotropicDegreeLabel, gridBagConstraints);

        dEFUSEpanel1.setMinimumSize(new java.awt.Dimension(198, 77));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel1, gridBagConstraints);

        borderColor1TF.setColumns(5);
        borderColor1TF.setToolTipText("border pixel green component");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(borderColor1TF, gridBagConstraints);

        borderColor2TF.setColumns(5);
        borderColor2TF.setToolTipText("border pixel blue component");
        borderColor2TF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borderColor2TFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(borderColor2TF, gridBagConstraints);

        borderColorChooser.setPreferredSize(new java.awt.Dimension(20, 20));
        borderColorChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borderColorChooserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout borderColorChooserLayout = new javax.swing.GroupLayout(borderColorChooser);
        borderColorChooser.setLayout(borderColorChooserLayout);
        borderColorChooserLayout.setHorizontalGroup(
            borderColorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );
        borderColorChooserLayout.setVerticalGroup(
            borderColorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 6);
        add(borderColorChooser, gridBagConstraints);

        borderWidthCBox.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "0", "1" }));
        borderWidthCBox.setToolTipText("number of pixels for texture border (0 or 1)");
        borderWidthCBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borderWidthCBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(borderWidthCBox, gridBagConstraints);

        borderColor3TF.setColumns(5);
        borderColor3TF.setToolTipText("border pixel alpha channel (0=transparent, 1=opaque)");
        borderColor3TF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                borderColor3TFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(borderColor3TF, gridBagConstraints);

        anisotropicDegreeTF.setColumns(5);
        anisotropicDegreeTF.setToolTipText("minimum degree of anisotropy to account for in texture filtering (1=none or higher value)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(anisotropicDegreeTF, gridBagConstraints);

        boundaryModeSLabel.setText("boundaryModeS");
        boundaryModeSLabel.setToolTipText("handling texture-coordinate boundaries");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        add(boundaryModeSLabel, gridBagConstraints);

        boundaryModeSCBox.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        boundaryModeSCBox.setModel(new DefaultComboBoxModel<String>(TEXTUREPROPERTIES_ATTR_BOUNDARYMODE_LABELS));
        boundaryModeSCBox.setToolTipText("handling texture-coordinate boundaries");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 6);
        add(boundaryModeSCBox, gridBagConstraints);

        boundaryModeTLabel.setText("boundaryModeT");
        boundaryModeTLabel.setToolTipText("handling texture-coordinate boundaries");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        add(boundaryModeTLabel, gridBagConstraints);

        boundaryModeTCBox.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        boundaryModeTCBox.setModel(new DefaultComboBoxModel<String>(TEXTUREPROPERTIES_ATTR_BOUNDARYMODE_LABELS));
        boundaryModeTCBox.setToolTipText("handling texture-coordinate boundaries");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 6);
        add(boundaryModeTCBox, gridBagConstraints);

        boundaryModeRLabel.setText("boundaryModeR");
        boundaryModeRLabel.setToolTipText("handling texture-coordinate boundaries");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        add(boundaryModeRLabel, gridBagConstraints);

        boundaryModeRCBox.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        boundaryModeRCBox.setModel(new DefaultComboBoxModel<String>(TEXTUREPROPERTIES_ATTR_BOUNDARYMODE_LABELS));
        boundaryModeRCBox.setToolTipText("handling texture-coordinate boundaries");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 6);
        add(boundaryModeRCBox, gridBagConstraints);

        texturePriorityLabel.setText("texturePriority");
        texturePriorityLabel.setToolTipText("priority for allocating texture memory [0,1]");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        add(texturePriorityLabel, gridBagConstraints);

        texturePriorityTF.setColumns(5);
        texturePriorityTF.setToolTipText("priority for allocating texture memory [0,1]");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(texturePriorityTF, gridBagConstraints);

        magnificationFilterLabel.setText("magnificationFilter");
        magnificationFilterLabel.setToolTipText("texture filter when image is smaller than screen space representation");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        add(magnificationFilterLabel, gridBagConstraints);

        magnificationFilterCBox.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        magnificationFilterCBox.setModel(new DefaultComboBoxModel<String>(TEXTUREPROPERTIES_ATTR_MAGNIFICATIONMODE_LABELS));
        magnificationFilterCBox.setToolTipText("texture filter when image is smaller than screen space representation");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 6);
        add(magnificationFilterCBox, gridBagConstraints);

        minificationFilterLabel1.setText("minificationFilter");
        minificationFilterLabel1.setToolTipText("texture filter when image is larger than screen space representation");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        add(minificationFilterLabel1, gridBagConstraints);

        minificationFilterCBox.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        minificationFilterCBox.setModel(new DefaultComboBoxModel<String>(TEXTUREPROPERTIES_ATTR_MINIFICATIONMODE_LABELS));
        minificationFilterCBox.setToolTipText("texture filter when image is larger than screen space representation");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 6);
        add(minificationFilterCBox, gridBagConstraints);

        textureCompressionLabel.setText("textureCompression");
        textureCompressionLabel.setToolTipText("compression algorithm selection mode");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        add(textureCompressionLabel, gridBagConstraints);

        textureCompressionCBox.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        textureCompressionCBox.setModel(new DefaultComboBoxModel<String>(TEXTUREPROPERTIES_ATTR_COMPRESSIONMODE_LABELS));
        textureCompressionCBox.setToolTipText("compression algorithm selection mode");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 6);
        add(textureCompressionCBox, gridBagConstraints);

        nodeHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nodeHintPanel.setLayout(new java.awt.GridBagLayout());

        descriptionLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        descriptionLabel.setText("<html><b>TextureProperties</b> allows fine control over texture application");
        descriptionLabel.setToolTipText("TextureProperties affects adjacent Texture nodes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 10, 3);
        nodeHintPanel.add(descriptionLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nodeHintPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void borderColorChooserActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_borderColorChooserActionPerformed
    {//GEN-HEADEREND:event_borderColorChooserActionPerformed
    Color c = borderColorChooser.getColor();
    borderColor0TF.setText(formatDecimal((float)c.getRed()/255));
    borderColor1TF.setText(formatDecimal((float)c.getGreen()/255));
    borderColor2TF.setText(formatDecimal((float)c.getBlue()/255));
}//GEN-LAST:event_borderColorChooserActionPerformed

    private void borderColor2TFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_borderColor2TFActionPerformed
    {//GEN-HEADEREND:event_borderColor2TFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_borderColor2TFActionPerformed

    private void borderColor3TFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_borderColor3TFActionPerformed
    {//GEN-HEADEREND:event_borderColor3TFActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_borderColor3TFActionPerformed

    private void borderWidthCBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_borderWidthCBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_borderWidthCBoxActionPerformed
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel anisotropicDegreeLabel;
    private javax.swing.JTextField anisotropicDegreeTF;
    private javax.swing.JTextField borderColor0TF;
    private javax.swing.JTextField borderColor1TF;
    private javax.swing.JTextField borderColor2TF;
    private javax.swing.JTextField borderColor3TF;
    private net.java.dev.colorchooser.ColorChooser borderColorChooser;
    private javax.swing.JLabel borderColorLabel;
    private javax.swing.JComboBox<String> borderWidthCBox;
    private javax.swing.JLabel borderWidthLabel;
    private javax.swing.JComboBox<String> boundaryModeRCBox;
    private javax.swing.JLabel boundaryModeRLabel;
    private javax.swing.JComboBox<String> boundaryModeSCBox;
    private javax.swing.JLabel boundaryModeSLabel;
    private javax.swing.JComboBox<String> boundaryModeTCBox;
    private javax.swing.JLabel boundaryModeTLabel;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JCheckBox generateMipMapsCB;
    private javax.swing.JLabel generateMipMapsLabel;
    private javax.swing.JComboBox<String> magnificationFilterCBox;
    private javax.swing.JLabel magnificationFilterLabel;
    private javax.swing.JComboBox<String> minificationFilterCBox;
    private javax.swing.JLabel minificationFilterLabel1;
    private javax.swing.JPanel nodeHintPanel;
    private javax.swing.JComboBox<String> textureCompressionCBox;
    private javax.swing.JLabel textureCompressionLabel;
    private javax.swing.JLabel texturePriorityLabel;
    private javax.swing.JTextField texturePriorityTF;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_TEXTUREPROPERTIES";
  }

  @Override
  public void unloadInput() throws IllegalArgumentException
  {
    unLoadDEFUSE();

    textureProperties.setAnisotropicDegree(anisotropicDegreeTF.getText().trim());
    
    textureProperties.setColor0(borderColor0TF.getText().trim());
    textureProperties.setColor1(borderColor1TF.getText().trim());
    textureProperties.setColor2(borderColor2TF.getText().trim());
    textureProperties.setColor3(borderColor3TF.getText().trim());

    textureProperties.setBorderWidth(borderWidthCBox.getSelectedIndex());

    textureProperties.setBoundaryModeS(TEXTUREPROPERTIES_ATTR_BOUNDARYMODE_CHOICES[boundaryModeSCBox.getSelectedIndex()]);
    textureProperties.setBoundaryModeT(TEXTUREPROPERTIES_ATTR_BOUNDARYMODE_CHOICES[boundaryModeTCBox.getSelectedIndex()]);
    textureProperties.setBoundaryModeR(TEXTUREPROPERTIES_ATTR_BOUNDARYMODE_CHOICES[boundaryModeRCBox.getSelectedIndex()]);

    textureProperties.setGenerateMipMaps(generateMipMapsCB.isSelected());

    textureProperties.setMagnificationFilter(TEXTUREPROPERTIES_ATTR_MAGNIFICATIONMODE_CHOICES[magnificationFilterCBox.getSelectedIndex()]);
    textureProperties.setMinificationFilter (TEXTUREPROPERTIES_ATTR_MINIFICATIONMODE_CHOICES [ minificationFilterCBox.getSelectedIndex()]);
    textureProperties.setTextureCompression (TEXTUREPROPERTIES_ATTR_COMPRESSIONMODE_CHOICES  [ textureCompressionCBox.getSelectedIndex()]);

    textureProperties.setTexturePriority  (  texturePriorityTF.getText().trim());
  }  
}
