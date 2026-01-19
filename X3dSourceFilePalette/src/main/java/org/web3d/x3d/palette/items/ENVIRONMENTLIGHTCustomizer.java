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

import java.awt.Color;
import java.awt.Dialog;
import javax.swing.text.JTextComponent;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.HelpCtx;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
/**
 * ENVIRONMENTLIGHTCustomizer.java
 * 
 * @author Don Brutzman
 * @version $Id$
 */
public class ENVIRONMENTLIGHTCustomizer extends BaseCustomizer
{
  private ENVIRONMENTLIGHT environmentLight;
  private JTextComponent target;
  
  /** Creates new form ENVIRONMENTLIGHTCustomizer */
  public ENVIRONMENTLIGHTCustomizer(ENVIRONMENTLIGHT environmentLight, JTextComponent target)
  {
    super(environmentLight);
    this.environmentLight = environmentLight;
    this.target = target;
             
    HelpCtx.setHelpIDString(this, "ENVIRONMENTLIGHT_ELEM_HELPID");
    
    initComponents();
    
    ambientIntensityTF.setText(environmentLight.getAmbientIntensity());
    color0TF.setText(environmentLight.getColor0());
    color1TF.setText(environmentLight.getColor1());
    color2TF.setText(environmentLight.getColor2());

    bindColorChooserToBetterJTextFields(color0TF,color1TF,color2TF,colorChooser1);
    
    diffuseCoefficientsTextArea.setText(environmentLight.getDiffuseCoefficients().replaceAll("\\s+", " ").trim());
    checkDiffuseCoefficients();
   
    globalCB.setSelected(environmentLight.isGlobal());
    intensityTF.setText(environmentLight.getIntensity());
    origin0TF.setText(environmentLight.getOriginX());
    origin1TF.setText(environmentLight.getOriginY());
    origin2TF.setText(environmentLight.getOriginZ());
    
    onCB.setSelected(environmentLight.isOn());
    rotationXaxisTF.setText(environmentLight.getRotationX());
    rotationYaxisTF.setText(environmentLight.getRotationY());
    rotationZaxisTF.setText(environmentLight.getRotationZ());
    rotationAngleTF.setText(environmentLight.getRotationAngle());
    checkRotation ();
    shadowsCB.setSelected(environmentLight.isShadows());
    shadowIntensityTF.setText(environmentLight.getShadowIntensity());
  }

    private void checkDiffuseCoefficients ()
    {
        String diffuseCoefficients = diffuseCoefficientsTextArea.getText().replaceAll("\\s+", " ").trim(); // normalize space
        int spaceCount = diffuseCoefficients.length() - diffuseCoefficients.replaceAll(" ", "").length();
        if (!diffuseCoefficients.isBlank() && (spaceCount != 26)) // 3 x 9 array
        {
            String message;
            message = "<html><center>Incorrect number of diffuseCoefficients values: <b>" + (spaceCount + 1) + "</b> rather than 27 (or empty string)";
            NotifyDescriptor descriptor = new NotifyDescriptor.Message(message);
            descriptor.setTitle("Error: invalid diffuseCoefficients array");
            DialogDisplayer.getDefault().notify(descriptor);
        }
    }

  private void checkRotation ()
  {
      // indicate degree values in tooltips
      // usability note:  can enter degree values (-6..+6) as (354..366) to provoke this conversion check
      double angle = new SFFloat(rotationAngleTF.getText()).getValue();
      rotationAngleTF.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");
      if (Math.abs(angle) > 2.0 * Math.PI)
      {
            String message;
            message = "<html><center>Large value provided for <b>rotation</b> angle, which is a radians value.<br/><br/>Convert <b>" + angle + " degrees</b> to <b>" +
                      radiansFormat.format((angle % 360.0) * Math.PI / 180.0) + " radians</b>?";
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  message, "X3D angles are in radians", NotifyDescriptor.YES_NO_OPTION);
          if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
          {
              angle = (angle % 360.0) * Math.PI / 180.0;
              rotationAngleTF.setText(radiansFormat.format(angle));
              rotationAngleTF.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");
          }
      }
  }
  
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        dEFUSEpanel1 = getDEFUSEpanel();
        ambientIntensityLabel = new javax.swing.JLabel();
        ambientIntensityTF = new javax.swing.JTextField();
        colorLabel = new javax.swing.JLabel();
        color0TF = new org.web3d.x3d.palette.BetterJTextField();
        color1TF = new org.web3d.x3d.palette.BetterJTextField();
        color2TF = new org.web3d.x3d.palette.BetterJTextField();
        intensityLabel = new javax.swing.JLabel();
        intensityTF = new javax.swing.JTextField();
        originLabel = new javax.swing.JLabel();
        origin0TF = new javax.swing.JTextField();
        origin1TF = new javax.swing.JTextField();
        origin2TF = new javax.swing.JTextField();
        globalLabel = new javax.swing.JLabel();
        globalCB = new javax.swing.JCheckBox();
        onLabel = new javax.swing.JLabel();
        onCB = new javax.swing.JCheckBox();
        colorChooser1 = new net.java.dev.colorchooser.ColorChooser();
        rotationLabel = new javax.swing.JLabel();
        rotationXaxisTF = new javax.swing.JTextField();
        rotationYaxisTF = new javax.swing.JTextField();
        rotationZaxisTF = new javax.swing.JTextField();
        rotationAngleTF = new javax.swing.JTextField();
        rotationCalculatorlButton = new javax.swing.JButton();
        hintLabel = new javax.swing.JLabel();
        shadowsLabel = new javax.swing.JLabel();
        shadowsCB = new javax.swing.JCheckBox();
        shadowIntensityLabel = new javax.swing.JLabel();
        shadowIntensityTF = new javax.swing.JTextField();
        diffuseCoefficientsLabel = new javax.swing.JLabel();
        diffuseCoefficientsScrollPane = new javax.swing.JScrollPane();
        diffuseCoefficientsTextArea = new javax.swing.JTextArea();

        setMinimumSize(new java.awt.Dimension(700, 550));
        setPreferredSize(new java.awt.Dimension(700, 550));
        setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(dEFUSEpanel1, gridBagConstraints);

        ambientIntensityLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        ambientIntensityLabel.setText("ambientIntensity");
        ambientIntensityLabel.setToolTipText("[0,1] brightness of ambient (nondirectional background) light");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(ambientIntensityLabel, gridBagConstraints);

        ambientIntensityTF.setToolTipText("[0,1] brightness of ambient (nondirectional background) light");
        ambientIntensityTF.setMinimumSize(new java.awt.Dimension(60, 22));
        ambientIntensityTF.setPreferredSize(new java.awt.Dimension(60, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(ambientIntensityTF, gridBagConstraints);

        colorLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        colorLabel.setText("color");
        colorLabel.setToolTipText("color of light, applied to colors of objects");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(colorLabel, gridBagConstraints);

        color0TF.setToolTipText("color of light, applied to colors of objects");
        color0TF.setMinimumSize(new java.awt.Dimension(60, 22));
        color0TF.setPreferredSize(new java.awt.Dimension(60, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(color0TF, gridBagConstraints);

        color1TF.setToolTipText("color of light, applied to colors of objects");
        color1TF.setMinimumSize(new java.awt.Dimension(60, 22));
        color1TF.setPreferredSize(new java.awt.Dimension(60, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(color1TF, gridBagConstraints);

        color2TF.setToolTipText("color of light, applied to colors of objects");
        color2TF.setMinimumSize(new java.awt.Dimension(60, 22));
        color2TF.setPreferredSize(new java.awt.Dimension(60, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(color2TF, gridBagConstraints);

        intensityLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        intensityLabel.setText("intensity");
        intensityLabel.setToolTipText("[0,1] brightness of direct light");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(intensityLabel, gridBagConstraints);

        intensityTF.setToolTipText("[0,1] brightness of direct light");
        intensityTF.setMinimumSize(new java.awt.Dimension(60, 22));
        intensityTF.setPreferredSize(new java.awt.Dimension(60, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(intensityTF, gridBagConstraints);

        originLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        originLabel.setText("origin");
        originLabel.setToolTipText("position of light relative to local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(originLabel, gridBagConstraints);

        origin0TF.setToolTipText("position of light relative to local coordinate system");
        origin0TF.setMinimumSize(new java.awt.Dimension(60, 22));
        origin0TF.setPreferredSize(new java.awt.Dimension(60, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(origin0TF, gridBagConstraints);

        origin1TF.setToolTipText("position of light relative to local coordinate system");
        origin1TF.setMinimumSize(new java.awt.Dimension(60, 22));
        origin1TF.setPreferredSize(new java.awt.Dimension(60, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(origin1TF, gridBagConstraints);

        origin2TF.setToolTipText("position of light relative to local coordinate system");
        origin2TF.setMinimumSize(new java.awt.Dimension(60, 22));
        origin2TF.setPreferredSize(new java.awt.Dimension(60, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(origin2TF, gridBagConstraints);

        globalLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        globalLabel.setText("global");
        globalLabel.setToolTipText("global lights illuminate all objects, scoped lights only affect local transformation hierarchy");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(globalLabel, gridBagConstraints);

        globalCB.setToolTipText("global lights illuminate all objects, scoped lights only affect local transformation hierarchy");
        globalCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        globalCB.setMinimumSize(new java.awt.Dimension(60, 22));
        globalCB.setPreferredSize(new java.awt.Dimension(60, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(globalCB, gridBagConstraints);

        onLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        onLabel.setText("on");
        onLabel.setToolTipText("enables/disables this light");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(onLabel, gridBagConstraints);

        onCB.setToolTipText("enables/disables this light");
        onCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        onCB.setMinimumSize(new java.awt.Dimension(60, 22));
        onCB.setPreferredSize(new java.awt.Dimension(60, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(onCB, gridBagConstraints);

        colorChooser1.setMaximumSize(new java.awt.Dimension(22, 22));
        colorChooser1.setMinimumSize(new java.awt.Dimension(22, 22));
        colorChooser1.setPreferredSize(new java.awt.Dimension(22, 22));
        colorChooser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorChooser1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout colorChooser1Layout = new javax.swing.GroupLayout(colorChooser1);
        colorChooser1.setLayout(colorChooser1Layout);
        colorChooser1Layout.setHorizontalGroup(
            colorChooser1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        colorChooser1Layout.setVerticalGroup(
            colorChooser1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(colorChooser1, gridBagConstraints);

        rotationLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        rotationLabel.setText("rotation");
        rotationLabel.setToolTipText("maximum effective distance of light relative to local light position");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(rotationLabel, gridBagConstraints);

        rotationXaxisTF.setColumns(4);
        rotationXaxisTF.setToolTipText("Orientation (axis, angle in radians) of children relative to local coordinate system");
        rotationXaxisTF.setMaximumSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(rotationXaxisTF, gridBagConstraints);

        rotationYaxisTF.setColumns(4);
        rotationYaxisTF.setToolTipText("Orientation (axis, angle in radians) of children relative to local coordinate system");
        rotationYaxisTF.setMaximumSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(rotationYaxisTF, gridBagConstraints);

        rotationZaxisTF.setColumns(4);
        rotationZaxisTF.setToolTipText("Orientation (axis, angle in radians) of children relative to local coordinate system");
        rotationZaxisTF.setMaximumSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(rotationZaxisTF, gridBagConstraints);

        rotationAngleTF.setColumns(4);
        rotationAngleTF.setToolTipText("Orientation (axis, angle in radians) of children relative to local coordinate system");
        rotationAngleTF.setMaximumSize(null);
        rotationAngleTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotationAngleTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(rotationAngleTF, gridBagConstraints);

        rotationCalculatorlButton.setText("calculator");
        rotationCalculatorlButton.setToolTipText("launch geoSystem panel");
        rotationCalculatorlButton.setMaximumSize(new java.awt.Dimension(80, 22));
        rotationCalculatorlButton.setMinimumSize(new java.awt.Dimension(80, 22));
        rotationCalculatorlButton.setPreferredSize(new java.awt.Dimension(6, 22));
        rotationCalculatorlButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotationCalculatorlButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(rotationCalculatorlButton, gridBagConstraints);

        hintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel.setText("<html><p align=\"center\"><b>EnvironmentLight</b> supports Image Based Lighting (IBL) techniques by specifying <br />\nlight-source intensity around a given location (i.e., the environment) as a cube map. </p>\n<br />\n<p align=\"center\">\nContained <i>ambientIntensity<i> and <i>specularTexture<i> nodes <br />\n(ComposedCubeMapTexture, GeneratedCubeMapTexture, ImageCubeMapTexture) define radiance maps. </p>\n<br />\n<p align=\"center\"> Lighting illuminates all geometry except lines and points.   <br />\nLights have no visible shape themselves and lighting effects continue through any intermediate geometry.</p>");
        hintLabel.setToolTipText(org.openide.util.NbBundle.getMessage(ENVIRONMENTLIGHTCustomizer.class, "INTEGERSEQUENCERCustomizer.eventLabel3.toolTipText")); // NOI18N
        hintLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LAST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(hintLabel, gridBagConstraints);

        shadowsLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        shadowsLabel.setText("shadows");
        shadowsLabel.setToolTipText("whether shadows result from this light");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(shadowsLabel, gridBagConstraints);

        shadowsCB.setToolTipText("whether shadows result from this light");
        shadowsCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        shadowsCB.setMinimumSize(new java.awt.Dimension(60, 22));
        shadowsCB.setPreferredSize(new java.awt.Dimension(60, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(shadowsCB, gridBagConstraints);

        shadowIntensityLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        shadowIntensityLabel.setText("shadowIntensity");
        shadowIntensityLabel.setToolTipText("[0,1] darkness of shadows from this light (0 is no shadow, 1 is full-intensity shadow)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(shadowIntensityLabel, gridBagConstraints);

        shadowIntensityTF.setToolTipText("[0,1] darkness of shadows from this light (0 is no shadow, 1 is full-intensity shadow)");
        shadowIntensityTF.setMinimumSize(new java.awt.Dimension(60, 22));
        shadowIntensityTF.setPreferredSize(new java.awt.Dimension(60, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(shadowIntensityTF, gridBagConstraints);

        diffuseCoefficientsLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        diffuseCoefficientsLabel.setText("diffuseCoefficients");
        diffuseCoefficientsLabel.setToolTipText("[0,1] brightness of direct light");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(diffuseCoefficientsLabel, gridBagConstraints);

        diffuseCoefficientsTextArea.setColumns(20);
        diffuseCoefficientsTextArea.setRows(5);
        diffuseCoefficientsScrollPane.setViewportView(diffuseCoefficientsTextArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 60;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(diffuseCoefficientsScrollPane, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

  private void colorChooser1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorChooser1ActionPerformed
    Color c = colorChooser1.getColor();
    color0TF.setText(colorFormat.format(c.getRed()  /255.0f));
    color1TF.setText(colorFormat.format(c.getGreen()/255.0f));
    color2TF.setText(colorFormat.format(c.getBlue() /255.0f));
  }//GEN-LAST:event_colorChooser1ActionPerformed

    private void rotationAngleTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rotationAngleTFActionPerformed
        checkRotation();
    }//GEN-LAST:event_rotationAngleTFActionPerformed

    private void rotationCalculatorlButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rotationCalculatorlButtonActionPerformed
        RotationCalculatorPanel rotationCalculatorPanel = new RotationCalculatorPanel(environmentLight, "rotation");
        rotationCalculatorPanel.setRotationValue (
            rotationXaxisTF.getText(),
            rotationYaxisTF.getText(),
            rotationZaxisTF.getText(),
            rotationAngleTF.getText());
        DialogDescriptor dd = new DialogDescriptor(rotationCalculatorPanel, "Rotation Calculator for EnvironmentLight rotation");
        Dialog dialog = DialogDisplayer.getDefault().createDialog(dd);
        dialog.setVisible(true);
        if (dd.getValue() != DialogDescriptor.CANCEL_OPTION)
        {
            // save values
            if (!rotationCalculatorPanel.getRotationResult().isEmpty())
            {
                rotationXaxisTF.setText(rotationCalculatorPanel.getRotationResultX());
                rotationYaxisTF.setText(rotationCalculatorPanel.getRotationResultY());
                rotationZaxisTF.setText(rotationCalculatorPanel.getRotationResultZ());
                rotationAngleTF.setText(rotationCalculatorPanel.getRotationResultAngle());
                rotationAngleTF.setToolTipText(rotationCalculatorPanel.getRotationResultAngle() + " radians = " +
                    singleDigitFormat.format(Float.parseFloat(rotationCalculatorPanel.getRotationResultAngle()) * 180.0 / Math.PI) + " degrees");
            }
        }
    }//GEN-LAST:event_rotationCalculatorlButtonActionPerformed
  
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ambientIntensityLabel;
    private javax.swing.JTextField ambientIntensityTF;
    private org.web3d.x3d.palette.BetterJTextField color0TF;
    private org.web3d.x3d.palette.BetterJTextField color1TF;
    private org.web3d.x3d.palette.BetterJTextField color2TF;
    private net.java.dev.colorchooser.ColorChooser colorChooser1;
    private javax.swing.JLabel colorLabel;
    private org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel1;
    private javax.swing.JLabel diffuseCoefficientsLabel;
    private javax.swing.JScrollPane diffuseCoefficientsScrollPane;
    private javax.swing.JTextArea diffuseCoefficientsTextArea;
    private javax.swing.JCheckBox globalCB;
    private javax.swing.JLabel globalLabel;
    private javax.swing.JLabel hintLabel;
    private javax.swing.JLabel intensityLabel;
    private javax.swing.JTextField intensityTF;
    private javax.swing.JCheckBox onCB;
    private javax.swing.JLabel onLabel;
    private javax.swing.JTextField origin0TF;
    private javax.swing.JTextField origin1TF;
    private javax.swing.JTextField origin2TF;
    private javax.swing.JLabel originLabel;
    private javax.swing.JTextField rotationAngleTF;
    private javax.swing.JButton rotationCalculatorlButton;
    private javax.swing.JLabel rotationLabel;
    private javax.swing.JTextField rotationXaxisTF;
    private javax.swing.JTextField rotationYaxisTF;
    private javax.swing.JTextField rotationZaxisTF;
    private javax.swing.JLabel shadowIntensityLabel;
    private javax.swing.JTextField shadowIntensityTF;
    private javax.swing.JCheckBox shadowsCB;
    private javax.swing.JLabel shadowsLabel;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_ENVIRONMENTLIGHT";
  }

  @Override
  public void unloadInput()
  {
    unLoadDEFUSE();
     
    environmentLight.setAmbientIntensity(ambientIntensityTF.getText().trim());
    
    environmentLight.setColor0(color0TF.getText().trim());
    environmentLight.setColor1(color1TF.getText().trim());
    environmentLight.setColor2(color2TF.getText().trim());
    
    checkDiffuseCoefficients();
    environmentLight.setDiffuseCoefficients(diffuseCoefficientsTextArea.getText().replaceAll("\\s+", " ").trim()); // normalize space
    environmentLight.setGlobal(globalCB.isSelected());
    
    environmentLight.setIntensity(intensityTF.getText().trim());
    environmentLight.setOn(onCB.isSelected());
    environmentLight.setOriginX(origin0TF.getText().trim());
    environmentLight.setOriginY(origin1TF.getText().trim());
    environmentLight.setOriginZ(origin2TF.getText().trim());
    
    checkRotation ();
    environmentLight.setRotationX(rotationXaxisTF.getText().trim());
    environmentLight.setRotationY(rotationYaxisTF.getText().trim());
    environmentLight.setRotationZ(rotationZaxisTF.getText().trim());
    environmentLight.setRotationAngle(rotationAngleTF.getText().trim());
    
    environmentLight.setShadows(shadowsCB.isSelected());
    environmentLight.setShadowIntensity(shadowIntensityTF.getText().trim());
  }
  
}
