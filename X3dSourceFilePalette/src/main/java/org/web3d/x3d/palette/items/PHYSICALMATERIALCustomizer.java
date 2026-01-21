/*
Copyright (c) 1995-2026  held by the author(s).  All rights reserved.

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

import java.awt.Color;
import java.awt.Component;

import javax.swing.*;
import javax.swing.text.JTextComponent;

import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.HelpCtx;

import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * PHYSICALMATERIALCustomizer.java
 * 
 * @author Don Brutzman
 * @version $Id$
 */
public class PHYSICALMATERIALCustomizer extends BaseCustomizer
{
  private final PHYSICALMATERIAL unlitMaterial;

  private final String DEFname="";
  private final String HEX_COLOR_TOOLTIP = "HTML hexadecimal #rrggbb (RGB float 0..1 = integer 0..255 = hex 00..FF)";
  private       String r, g, b;
  private      SFColor sfColor;
  private String message;
  private JFormattedTextField[] emissiveTFArray;
  
  /** Constructor
   * @param physicalMaterial the material node to customize
   * @param target Swing component of interest required arg, but can be null
   */
  public PHYSICALMATERIALCustomizer(PHYSICALMATERIAL physicalMaterial, JTextComponent target)
  {
    super(physicalMaterial);
    this.unlitMaterial = physicalMaterial;

    HelpCtx.setHelpIDString(PHYSICALMATERIALCustomizer.this, "PHYSICALMATERIAL_ELEM_HELPID");

    initComponents();
    
      emissiveColorRedTF.setText(physicalMaterial.getEmissiveColor0());
    emissiveColorGreenTF.setText(physicalMaterial.getEmissiveColor1());
     emissiveColorBlueTF.setText(physicalMaterial.getEmissiveColor2());
     updateEmissiveColorChooser();
     updateEmissiveColorHexTextField();

      normalScaleTF.setText(physicalMaterial.getNormalScale().toString());
     transparencyTF.setText(physicalMaterial.getTransparency());
 transparencySlider.setValue((int)(Float.parseFloat(physicalMaterial.getTransparency()) * 100.0f));
     
     emissiveTextureMappingTF.setText(physicalMaterial.getEmissiveTextureMapping());
       normalTextureMappingTF.setText(physicalMaterial.getNormalTextureMapping());
  }

  //----------------------------------------------------------
  // Method defined by JComponent
  //----------------------------------------------------------

  @Override
  public void removeNotify()
  {
    super.removeNotify();
  }

  /** Check Material for legal color values, fix if needed */
  private void checkFixMaterialColorValues ()
  {
      Float red, green, blue;

      red   = Float.valueOf(unlitMaterial.getEmissiveColor0());
      green = Float.valueOf(unlitMaterial.getEmissiveColor1());
      blue  = Float.valueOf(unlitMaterial.getEmissiveColor2());

      if ((red > 1.0f) || (green > 1.0f) || (blue > 1.0f)) // values > 255 already filtered during MATERIAL SFFloat loading
      {
          message = "<html><p>Large values found for emissiveColor='" + red + " " + green + " " + blue + "'</p><p>Convert from HTML to X3D?";
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  message, "X3D RGB values are [0..1]", NotifyDescriptor.YES_NO_OPTION);
          if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
          {
              if (red > 1.0f)
                  red   /= 255.0f;
              if (green > 1.0f)
                  green /= 255.0f;
              if (blue  > 1.0f)
                  blue  /= 255.0f;
              unlitMaterial.setEmissiveColor0(  red.toString());
              unlitMaterial.setEmissiveColor1(green.toString());
              unlitMaterial.setEmissiveColor2( blue.toString());
          }
      }

      if ((red > 1.0f) || (green > 1.0f) || (blue > 1.0f)) // values > 255 already filtered during MATERIAL SFFloat loading
      {
          message = "<html><p>Large values found for emissiveColor='" + red + " " + green + " " + blue + "'</p><p>Convert from HTML to X3D?";
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  message, "X3D RGB values are [0..1]", NotifyDescriptor.YES_NO_OPTION);
          if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
          {
              if (red > 1.0f)
                  red   /= 255.0f;
              if (green > 1.0f)
                  green /= 255.0f;
              if (blue  > 1.0f)
                  blue  /= 255.0f;
              unlitMaterial.setEmissiveColor0(  red.toString());
              unlitMaterial.setEmissiveColor1(green.toString());
              unlitMaterial.setEmissiveColor2( blue.toString());
          }
      }
  }

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_PHYSICALMATERIAL";
  }

  public PHYSICALMATERIAL getPHYSICALMATERIAL()
  {
    return unlitMaterial;
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        dEFUSEpanel = getDEFUSEpanel();
        unlitMaterialFieldsPanel = new javax.swing.JPanel();
        emissiveColorLabel = new javax.swing.JLabel();
        emissiveColorRedTF = new javax.swing.JFormattedTextField();
        emissiveColorGreenTF = new javax.swing.JFormattedTextField();
        emissiveColorBlueTF = new javax.swing.JFormattedTextField();
        emissiveColorChooser = new net.java.dev.colorchooser.ColorChooser();
        emissiveColorHexTextField = new javax.swing.JTextField();
        emissiveTextureMappingLabel = new javax.swing.JLabel();
        emissiveTextureMappingTF = new javax.swing.JFormattedTextField();
        normalTextureMappingLabel = new javax.swing.JLabel();
        normalTextureMappingTF = new javax.swing.JFormattedTextField();
        normalScaleLabel = new javax.swing.JLabel();
        normalScaleTF = new javax.swing.JFormattedTextField();
        transparencyLabel = new javax.swing.JLabel();
        transparencyTF = new javax.swing.JFormattedTextField();
        transparencySlider = new javax.swing.JSlider();
        unlitMaterialHintLabel = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(720, 450));
        setPreferredSize(new java.awt.Dimension(720, 450));
        setLayout(new java.awt.GridBagLayout());

        dEFUSEpanel.setMaximumSize(null);
        dEFUSEpanel.setMinimumSize(null);
        dEFUSEpanel.setPreferredSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel, gridBagConstraints);

        unlitMaterialFieldsPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        unlitMaterialFieldsPanel.setPreferredSize(new java.awt.Dimension(250, 206));
        unlitMaterialFieldsPanel.setLayout(new java.awt.GridBagLayout());

        emissiveColorLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        emissiveColorLabel.setText(org.openide.util.NbBundle.getMessage(PHYSICALMATERIALCustomizer.class, "NewJPanel.emisLab.text")); // NOI18N
        emissiveColorLabel.setToolTipText(org.openide.util.NbBundle.getMessage(PHYSICALMATERIALCustomizer.class, "PHYSICALMATERIALCustomizer.emissiveColorLabel.toolTipText_1")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        unlitMaterialFieldsPanel.add(emissiveColorLabel, gridBagConstraints);

        emissiveColorRedTF.setColumns(3);
        emissiveColorRedTF.setText(org.openide.util.NbBundle.getMessage(PHYSICALMATERIALCustomizer.class, "NewJPanel.emissiveColorRedTF.text")); // NOI18N
        emissiveColorRedTF.setToolTipText(org.openide.util.NbBundle.getMessage(PHYSICALMATERIALCustomizer.class, "MATERIALCustomizer.emissiveColorRed.toolTipText")); // NOI18N
        emissiveColorRedTF.setMinimumSize(new java.awt.Dimension(6, 15));
        emissiveColorRedTF.setName("emissiveColorRed"); // NOI18N
        emissiveColorRedTF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                globalPropertyChangeListener(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        unlitMaterialFieldsPanel.add(emissiveColorRedTF, gridBagConstraints);

        emissiveColorGreenTF.setColumns(3);
        emissiveColorGreenTF.setText(org.openide.util.NbBundle.getMessage(PHYSICALMATERIALCustomizer.class, "NewJPanel.emissiveColorGreenTF.text")); // NOI18N
        emissiveColorGreenTF.setToolTipText(org.openide.util.NbBundle.getMessage(PHYSICALMATERIALCustomizer.class, "MATERIALCustomizer.emissiveColorGreen.toolTipText")); // NOI18N
        emissiveColorGreenTF.setMinimumSize(new java.awt.Dimension(6, 15));
        emissiveColorGreenTF.setName("emissiveColorGreen"); // NOI18N
        emissiveColorGreenTF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                globalPropertyChangeListener(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        unlitMaterialFieldsPanel.add(emissiveColorGreenTF, gridBagConstraints);

        emissiveColorBlueTF.setColumns(3);
        emissiveColorBlueTF.setText(org.openide.util.NbBundle.getMessage(PHYSICALMATERIALCustomizer.class, "NewJPanel.emissiveColorBlueTF.text")); // NOI18N
        emissiveColorBlueTF.setToolTipText(org.openide.util.NbBundle.getMessage(PHYSICALMATERIALCustomizer.class, "MATERIALCustomizer.emissiveColorBlue.toolTipText")); // NOI18N
        emissiveColorBlueTF.setMinimumSize(new java.awt.Dimension(6, 15));
        emissiveColorBlueTF.setName("emissiveColorBlue"); // NOI18N
        emissiveColorBlueTF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                globalPropertyChangeListener(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        unlitMaterialFieldsPanel.add(emissiveColorBlueTF, gridBagConstraints);

        emissiveColorChooser.setMinimumSize(new java.awt.Dimension(15, 15));
        emissiveColorChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emissiveColorChooserActionPerformed(evt);
            }
        });
        emissiveColorChooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                emissiveColorChooserPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout emissiveColorChooserLayout = new javax.swing.GroupLayout(emissiveColorChooser);
        emissiveColorChooser.setLayout(emissiveColorChooserLayout);
        emissiveColorChooserLayout.setHorizontalGroup(
            emissiveColorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );
        emissiveColorChooserLayout.setVerticalGroup(
            emissiveColorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        unlitMaterialFieldsPanel.add(emissiveColorChooser, gridBagConstraints);

        emissiveColorHexTextField.setEditable(false);
        emissiveColorHexTextField.setText(org.openide.util.NbBundle.getMessage(PHYSICALMATERIALCustomizer.class, "PHYSICALMATERIALCustomizer.emissiveColorHexTextField.text_1")); // NOI18N
        emissiveColorHexTextField.setToolTipText(org.openide.util.NbBundle.getMessage(PHYSICALMATERIALCustomizer.class, "MATERIALCustomizer.directionalLightColorHexTextField.toolTipText")); // NOI18N
        emissiveColorHexTextField.setMinimumSize(new java.awt.Dimension(60, 22));
        emissiveColorHexTextField.setPreferredSize(new java.awt.Dimension(60, 22));
        emissiveColorHexTextField.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        unlitMaterialFieldsPanel.add(emissiveColorHexTextField, gridBagConstraints);

        emissiveTextureMappingLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        emissiveTextureMappingLabel.setText(org.openide.util.NbBundle.getMessage(PHYSICALMATERIALCustomizer.class, "NewJPanel.shinLab.text")); // NOI18N
        emissiveTextureMappingLabel.setToolTipText(org.openide.util.NbBundle.getMessage(PHYSICALMATERIALCustomizer.class, "UNLITMATERIALCustomizer.emissiveTextureMappingTF.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        unlitMaterialFieldsPanel.add(emissiveTextureMappingLabel, gridBagConstraints);

        emissiveTextureMappingTF.setColumns(3);
        emissiveTextureMappingTF.setText(org.openide.util.NbBundle.getMessage(PHYSICALMATERIALCustomizer.class, "NewJPanel.shininessTF.text")); // NOI18N
        emissiveTextureMappingTF.setToolTipText(org.openide.util.NbBundle.getMessage(PHYSICALMATERIALCustomizer.class, "PHYSICALMATERIALCustomizer.emissiveTextureMappingTF.toolTipText")); // NOI18N
        emissiveTextureMappingTF.setMinimumSize(new java.awt.Dimension(6, 15));
        emissiveTextureMappingTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emissiveTextureMappingTFActionPerformed(evt);
            }
        });
        emissiveTextureMappingTF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                globalPropertyChangeListener(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        unlitMaterialFieldsPanel.add(emissiveTextureMappingTF, gridBagConstraints);

        normalTextureMappingLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        normalTextureMappingLabel.setText(org.openide.util.NbBundle.getMessage(PHYSICALMATERIALCustomizer.class, "PHYSICALMATERIALCustomizer.normalTextureMappingLabel.text")); // NOI18N
        normalTextureMappingLabel.setToolTipText(org.openide.util.NbBundle.getMessage(PHYSICALMATERIALCustomizer.class, "UNLITMATERIALCustomizer.normalTextureMappingTF.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        unlitMaterialFieldsPanel.add(normalTextureMappingLabel, gridBagConstraints);

        normalTextureMappingTF.setColumns(3);
        normalTextureMappingTF.setText(org.openide.util.NbBundle.getMessage(PHYSICALMATERIALCustomizer.class, "PHYSICALMATERIALCustomizer.normalTextureMappingTF.text")); // NOI18N
        normalTextureMappingTF.setToolTipText(org.openide.util.NbBundle.getMessage(PHYSICALMATERIALCustomizer.class, "PHYSICALMATERIALCustomizer.normalTextureMappingTF.toolTipText")); // NOI18N
        normalTextureMappingTF.setMinimumSize(new java.awt.Dimension(6, 15));
        normalTextureMappingTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                normalTextureMappingTFActionPerformed(evt);
            }
        });
        normalTextureMappingTF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                normalTextureMappingTFglobalPropertyChangeListener(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        unlitMaterialFieldsPanel.add(normalTextureMappingTF, gridBagConstraints);

        normalScaleLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        normalScaleLabel.setText(org.openide.util.NbBundle.getMessage(PHYSICALMATERIALCustomizer.class, "NewJPanel.ambLabMat.text")); // NOI18N
        normalScaleLabel.setToolTipText(org.openide.util.NbBundle.getMessage(PHYSICALMATERIALCustomizer.class, "PHYSICALMATERIALCustomizer.normalScaleLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        unlitMaterialFieldsPanel.add(normalScaleLabel, gridBagConstraints);

        normalScaleTF.setColumns(3);
        normalScaleTF.setText(org.openide.util.NbBundle.getMessage(PHYSICALMATERIALCustomizer.class, "NewJPanel.ambientIntensityTF.text")); // NOI18N
        normalScaleTF.setToolTipText(org.openide.util.NbBundle.getMessage(PHYSICALMATERIALCustomizer.class, "UNLITMATERIALCustomizer.normalScaleLabel.toolTipText")); // NOI18N
        normalScaleTF.setMinimumSize(new java.awt.Dimension(6, 15));
        normalScaleTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                normalScaleTFActionPerformed(evt);
            }
        });
        normalScaleTF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                globalPropertyChangeListener(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        unlitMaterialFieldsPanel.add(normalScaleTF, gridBagConstraints);

        transparencyLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        transparencyLabel.setText(org.openide.util.NbBundle.getMessage(PHYSICALMATERIALCustomizer.class, "NewJPanel.transLab.text")); // NOI18N
        transparencyLabel.setToolTipText(org.openide.util.NbBundle.getMessage(PHYSICALMATERIALCustomizer.class, "PHYSICALMATERIALCustomizer.transparencyLabel.toolTipText_1")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        unlitMaterialFieldsPanel.add(transparencyLabel, gridBagConstraints);

        transparencyTF.setColumns(3);
        transparencyTF.setText(org.openide.util.NbBundle.getMessage(PHYSICALMATERIALCustomizer.class, "NewJPanel.transparencyTF.text")); // NOI18N
        transparencyTF.setToolTipText(org.openide.util.NbBundle.getMessage(PHYSICALMATERIALCustomizer.class, "PHYSICALMATERIALCustomizer.transparencyTF.toolTipText_1")); // NOI18N
        transparencyTF.setMinimumSize(new java.awt.Dimension(6, 15));
        transparencyTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transparencyTFActionPerformed(evt);
            }
        });
        transparencyTF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                globalPropertyChangeListener(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        unlitMaterialFieldsPanel.add(transparencyTF, gridBagConstraints);

        transparencySlider.setToolTipText(org.openide.util.NbBundle.getMessage(PHYSICALMATERIALCustomizer.class, "PHYSICALMATERIALCustomizer.transparencySlider.toolTipText_1")); // NOI18N
        transparencySlider.setMaximumSize(new java.awt.Dimension(100, 25));
        transparencySlider.setPreferredSize(new java.awt.Dimension(100, 25));
        transparencySlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                transparencySliderHandler(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.67;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        unlitMaterialFieldsPanel.add(transparencySlider, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(unlitMaterialFieldsPanel, gridBagConstraints);

        unlitMaterialHintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        unlitMaterialHintLabel.setText(org.openide.util.NbBundle.getMessage(PHYSICALMATERIALCustomizer.class, "PHYSICALMATERIALCustomizer.unlitMaterialHintLabel.text")); // NOI18N
        unlitMaterialHintLabel.setToolTipText(org.openide.util.NbBundle.getMessage(PHYSICALMATERIALCustomizer.class, "PHYSICALMATERIALCustomizer.unlitMaterialHintLabel.toolTipText")); // NOI18N
        unlitMaterialHintLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(unlitMaterialHintLabel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

  // Sliders
  @SuppressWarnings("unused") // evt
  private void transparencySliderHandler(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_transparencySliderHandler
  {//GEN-HEADEREND:event_transparencySliderHandler
    int value = transparencySlider.getValue();
    transparencyTF.setValue((float)value/100.0f);
  }//GEN-LAST:event_transparencySliderHandler

  @SuppressWarnings("unused") // evt
  private void emissiveColorChooserActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_emissiveColorChooserActionPerformed
  {//GEN-HEADEREND:event_emissiveColorChooserActionPerformed
    Color c = emissiveColorChooser.getColor();
    setEmissiveColor(c);
  }//GEN-LAST:event_emissiveColorChooserActionPerformed

  private void emissiveColorChooserPropertyChange(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_emissiveColorChooserPropertyChange
  {//GEN-HEADEREND:event_emissiveColorChooserPropertyChange
    colorChooserPropertyChange(evt,emissiveTFArray);
  }//GEN-LAST:event_emissiveColorChooserPropertyChange

  private void colorChooserPropertyChange(java.beans.PropertyChangeEvent evt, JFormattedTextField[] rgbTF)
  {
   if(evt.getPropertyName().equals(net.java.dev.colorchooser.ColorChooser.PROP_TRANSIENT_COLOR) ||
      evt.getPropertyName().equals(net.java.dev.colorchooser.ColorChooser.PROP_COLOR)     ) {
      setEmissiveColor((Color)evt.getNewValue());
    }
  }

  private void checkColorTextField (JFormattedTextField jftf)
  {
      String text      = nullTo0(jftf);
      String textLower = text.toLowerCase();
//      String message   = "";
      String name      = jftf.getName();

      if (text.contains(".") || text.trim().startsWith("-"))
      {
        try
        {
            float floatValue = Float.parseFloat(text);
            if (floatValue > 1.0f)
            {
                jftf.setText("1.0"); // clamp max
                message = "<html><p>Large float value '<b>" + text + "</b>' found for <b>" + name + "</b>, clamped to 1.0";
            }
            else if (floatValue < 0.0f)
            {
                jftf.setText("0.0"); // clamp min
                message = "<html><p>Negative float value '<b>" + text + "</b>' found for <b>" + name + "</b>, clamped to 0.0";
            }
            // no change
        }
        catch (NumberFormatException e)
        {
            // bad value
            jftf.setText("0");
            message = "<html><p>Malformed value '<b>" + text + "</b>' found for <b>" + name + "</b>, reset to 0";
        }
      }
      else if (textLower.contains("a") || textLower.contains("b") || textLower.contains("c") || textLower.contains("d") || textLower.contains("e") || textLower.contains("f") ||
               textLower.startsWith("0x") || textLower.startsWith("#"))
      {
          if (text.startsWith("#"))
          {
              text = text.substring(1);
          }
          else if (textLower.startsWith("0x"))
          {
              text = text.substring(2);
          }
          try
          {
              int hexValue = Integer.parseInt(text, 16);
              if (hexValue > 255)
              {
                   jftf.setText("1"); // clamp max
                    message = "<html><p>Large hexadecimal value '<b>" + text + "</b>' found for <b>" + name + "</b>, clamped to ff = 1";
              }
              else if (hexValue < 0)
              {
                   jftf.setText("0"); // clamp min
                    message = "<html><p>Negative hexadecimal value '<b>" + text + "</b>' found for <b>" + name + "</b>, clamped to 0";
              }
              else // legal, explicit hex value '<b>" + text + "</b>' found 0..255
              {
                  jftf.setText(String.valueOf(hexValue / 255.0f));
              }
          }
          catch (NumberFormatException e)
          {
            // bad value
            jftf.setText("0");
            message = "<html><p>Malformed hexadecimal value '<b>" + text + "</b>' found for <b>" + name + "</b>, reset to 0";
          }
      }
      else // integer
      {
          try
          {
              int intValue = Integer.parseInt(text);
              if (intValue > 255)
              {
                   jftf.setText("1"); // clamp max
                    message = "<html><p>Large integer value '<b>" + text + "</b>' found for <b>" + name + "</b>, clamped to max = 1";
              }
              else if (intValue < 0)
              {
                   jftf.setText("0"); // clamp min
                    message = "<html><p>Negative integer value '<b>" + text + "</b>' found for <b>" + name + "</b>, clamped to 0";
              }
              else
              {
                  jftf.setText(String.valueOf(intValue / 255.0f));
                  message = "<html><p>Large HTML integer value '<b>" + text + "</b>' found for <b>" + name + "</b>, divided by 255, new value=" +
                          jftf.getText();
              }
          }
          catch (NumberFormatException e)
          {
            // bad value
            jftf.setText("0");
            message = "<html><p>Malformed integer value '<b>" + text + "</b>' found for <b>" + name + "</b>, reset to 0";
          }
      }
      if (message != null && !message.isBlank())
      {
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  message, "Color value problem", NotifyDescriptor.PLAIN_MESSAGE);
          DialogDisplayer.getDefault().notify(descriptor);
      }
  }
  /**
   * double-check event handling to ensure that all sliders are consistent with all text fields, updating Xj3D and source-text panes also
   * @param evt
   */
  private void globalPropertyChangeListener(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_globalPropertyChangeListener
  {//GEN-HEADEREND:event_globalPropertyChangeListener
    if (!"value".equals(evt.getPropertyName())) {
      return;
    }
    Object src = evt.getSource();

    if ((src instanceof JFormattedTextField) &&
        (((JFormattedTextField)src).getName() !=  null) &&
         ((Component)src).getName().contains("Color"))
    {
        checkColorTextField ((JFormattedTextField)src);
    }

    unlitMaterial.setContent(""); // clear for any change

    if (  src == emissiveColorRedTF ||
          src == emissiveColorGreenTF ||
          src == emissiveColorBlueTF)
    {
      updateEmissiveColorChooser();
      updateEmissiveColorHexTextField();
    }
    else if (src == transparencyTF)
    {
      // this block may be needed if user didn't hit enter on field
      double value = Double.parseDouble(nullTo0(transparencyTF));
      transparencySlider.setValue((int) (value * 100.0));
    }
  }//GEN-LAST:event_globalPropertyChangeListener

    private void emissiveTextureMappingTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emissiveTextureMappingTFActionPerformed

    }//GEN-LAST:event_emissiveTextureMappingTFActionPerformed

    @SuppressWarnings("unused") // evt
    private void normalScaleTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_normalScaleTFActionPerformed
        float value = Float.parseFloat(nullTo0(normalScaleTF));
        transparencySlider.setValue((int)(value * 100.0));
    }//GEN-LAST:event_normalScaleTFActionPerformed

    @SuppressWarnings("unused") // evt
    private void transparencyTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transparencyTFActionPerformed
        double value = Double.parseDouble(nullTo0(transparencyTF));
        transparencySlider.setValue((int)(value * 100.0));
    }//GEN-LAST:event_transparencyTFActionPerformed

    private void normalTextureMappingTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_normalTextureMappingTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_normalTextureMappingTFActionPerformed

    private void normalTextureMappingTFglobalPropertyChangeListener(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_normalTextureMappingTFglobalPropertyChangeListener
        // TODO add your handling code here:
    }//GEN-LAST:event_normalTextureMappingTFglobalPropertyChangeListener

  private void updateEmissiveColorHexTextField()
  {
      r =   emissiveColorRedTF.getText();
      g = emissiveColorGreenTF.getText();
      b =  emissiveColorBlueTF.getText();
      sfColor = new SFColor(r, g, b);
      emissiveColorHexTextField.setText(             sfColor.getHex());
      emissiveColorHexTextField.setToolTipText("[" + sfColor.getHtmlColor() + "] " + HEX_COLOR_TOOLTIP);
  }

  private void updateEmissiveColorChooser()
  {
    try
    {
        emissiveColorChooser.setColor(new SFColor(emissiveColorRedTF.getText(),
                                                  emissiveColorGreenTF.getText(),
                                                  emissiveColorBlueTF.getText()).getColor());
    }
    catch (IllegalArgumentException e)
    {
          message = "<html><p>Illegal value emissiveColor='" +
                     emissiveColorRedTF.getText()   + " " +
                     emissiveColorGreenTF.getText() + " " +
                     emissiveColorBlueTF.getText()  + "'" +
                     "</p><p>All values must be in range [0..1]</p>";
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  message, "emissiveColor value problem", NotifyDescriptor.PLAIN_MESSAGE);
          DialogDisplayer.getDefault().notify(descriptor);
    }
  }

  String nullTo0(JTextField tf)
  {
    String s = tf.getText().trim();
    return s.length()<=0?"0":s;
  }

  private void setEmissiveColor(Color c)
  {
    float[] fa = c.getRGBColorComponents(null);

      emissiveColorRedTF.setValue(""+fa[0]);  // use instead of setText to force "value" propertyChange event in TF
    emissiveColorGreenTF.setValue(""+fa[1]);
     emissiveColorBlueTF.setValue(""+fa[2]);
  }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel;
    private javax.swing.JFormattedTextField emissiveColorBlueTF;
    private net.java.dev.colorchooser.ColorChooser emissiveColorChooser;
    private javax.swing.JFormattedTextField emissiveColorGreenTF;
    private javax.swing.JTextField emissiveColorHexTextField;
    private javax.swing.JLabel emissiveColorLabel;
    private javax.swing.JFormattedTextField emissiveColorRedTF;
    private javax.swing.JLabel emissiveTextureMappingLabel;
    private javax.swing.JFormattedTextField emissiveTextureMappingTF;
    private javax.swing.JLabel normalScaleLabel;
    private javax.swing.JFormattedTextField normalScaleTF;
    private javax.swing.JLabel normalTextureMappingLabel;
    private javax.swing.JFormattedTextField normalTextureMappingTF;
    private javax.swing.JLabel transparencyLabel;
    private javax.swing.JSlider transparencySlider;
    private javax.swing.JFormattedTextField transparencyTF;
    private javax.swing.JPanel unlitMaterialFieldsPanel;
    private javax.swing.JLabel unlitMaterialHintLabel;
    // End of variables declaration//GEN-END:variables

  @Override
  public void unloadInput() throws IllegalArgumentException
  {
    unLoadDEFUSE();

    unlitMaterial.setEmissiveColor0  (emissiveColorRedTF.getText());
    unlitMaterial.setEmissiveColor1  (emissiveColorGreenTF.getText());
    unlitMaterial.setEmissiveColor2  (emissiveColorBlueTF.getText());
    unlitMaterial.setNormalScale     (new SFFloat( normalScaleTF.getText(), 0.0f, null));
    unlitMaterial.setTransparency    (transparencyTF.getText());
    
    unlitMaterial.setEmissiveTextureMapping(emissiveTextureMappingTF.getText());
    unlitMaterial.setNormalTextureMapping  (normalTextureMappingTF.getText());
  }
}
