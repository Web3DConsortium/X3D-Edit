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
import javax.swing.DefaultComboBoxModel;
import javax.swing.text.JTextComponent;
import org.openide.util.HelpCtx;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
/**
 * FILLPROPERTIESCustomizer.java
 * Created on July 12, 2007, 3:05 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class FILLPROPERTIESCustomizer extends BaseCustomizer
{
  private FILLPROPERTIES fillProperties;
  private JTextComponent target;
  
  public FILLPROPERTIESCustomizer(FILLPROPERTIES fillProperties, JTextComponent target)
  {
    super(fillProperties);
    this.fillProperties = fillProperties;
    this.target = target;
    
    HelpCtx.setHelpIDString(this, "FILLPROPERTIES_ELEM_HELPID");
    
    initComponents();
    
    String c0 = fillProperties.getColor0();
    String c1 = fillProperties.getColor1();
    String c2 = fillProperties.getColor2();
    color0TF.setText(c0);
    color1TF.setText(c1);
    color2TF.setText(c2);
    
    filledCB.setSelected(fillProperties.isFilled());
    hatchedCB.setSelected(fillProperties.isHatched());
    
    hatchStyleCBox.setSelectedIndex(fillProperties.getHatchStyle()-1); // 1-based to 0-based
    
    colorChooser1.setColor((new SFColor(c0, c1, c2)).getColor());
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
        filledLabel = new javax.swing.JLabel();
        filledCB = new javax.swing.JCheckBox();
        hatchedLabel = new javax.swing.JLabel();
        hatchedCB = new javax.swing.JCheckBox();
        hatchColorLabel = new javax.swing.JLabel();
        color0TF = new javax.swing.JTextField();
        color1TF = new javax.swing.JTextField();
        color2TF = new javax.swing.JTextField();
        colorChooser1 = new net.java.dev.colorchooser.ColorChooser();
        hatchedStyleLabel = new javax.swing.JLabel();
        hatchStyleCBox = new javax.swing.JComboBox<String>();
        nodeHintPanel = new javax.swing.JPanel();
        descriptionLabel = new javax.swing.JLabel();

        setToolTipText("FillProperties indicates whether appearance is filled or hatched");
        setLayout(new java.awt.GridBagLayout());

        dEFUSEpanel1.setMinimumSize(new java.awt.Dimension(198, 77));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel1, gridBagConstraints);

        filledLabel.setText("filled");
        filledLabel.setToolTipText("Whether or not associated geometry is filled");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 43, 3, 3);
        add(filledLabel, gridBagConstraints);

        filledCB.setToolTipText("Whether or not associated geometry is filled");
        filledCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(filledCB, gridBagConstraints);

        hatchedLabel.setText("hatched");
        hatchedLabel.setToolTipText("Whether or not associated geometry is hatched");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 43, 3, 3);
        add(hatchedLabel, gridBagConstraints);

        hatchedCB.setToolTipText("Whether or not associated geometry is hatched");
        hatchedCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(hatchedCB, gridBagConstraints);

        hatchColorLabel.setText("hatchColor");
        hatchColorLabel.setToolTipText("Color of the hatch pattern");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 43, 3, 3);
        add(hatchColorLabel, gridBagConstraints);

        color0TF.setToolTipText("Color of the hatch pattern");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(color0TF, gridBagConstraints);

        color1TF.setToolTipText("Color of the hatch pattern");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(color1TF, gridBagConstraints);

        color2TF.setToolTipText("Color of the hatch pattern");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(color2TF, gridBagConstraints);

        colorChooser1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorChooser1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout colorChooser1Layout = new javax.swing.GroupLayout(colorChooser1);
        colorChooser1.setLayout(colorChooser1Layout);
        colorChooser1Layout.setHorizontalGroup(
            colorChooser1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );
        colorChooser1Layout.setVerticalGroup(
            colorChooser1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 60);
        add(colorChooser1, gridBagConstraints);

        hatchedStyleLabel.setText("hatchStyle");
        hatchedStyleLabel.setToolTipText("hatchStyle selects a hatch pattern, see list of integer codes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 43, 3, 3);
        add(hatchedStyleLabel, gridBagConstraints);

        hatchStyleCBox.setModel(new DefaultComboBoxModel<String>(FILLPROPERTIES_ATTR_HATCHSTYLE_CHOICES));
        hatchStyleCBox.setToolTipText("hatchStyle selects a hatch pattern, see list of integer codes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.3333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(hatchStyleCBox, gridBagConstraints);

        nodeHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nodeHintPanel.setLayout(new java.awt.GridBagLayout());

        descriptionLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        descriptionLabel.setText("<html><b>FillProperties</b> allows fine control over polygonal rendering of peer geometry");
        descriptionLabel.setToolTipText("FillProperties affects adjacent geometry nodes");
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
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nodeHintPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

  private void colorChooser1ActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_colorChooser1ActionPerformed
  {//GEN-HEADEREND:event_colorChooser1ActionPerformed
    Color c = colorChooser1.getColor();
    color0TF.setText(formatDecimal((float)c.getRed()/255));
    color1TF.setText(formatDecimal((float)c.getGreen()/255));
    color2TF.setText(formatDecimal((float)c.getBlue()/255));    
  }//GEN-LAST:event_colorChooser1ActionPerformed
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField color0TF;
    private javax.swing.JTextField color1TF;
    private javax.swing.JTextField color2TF;
    private net.java.dev.colorchooser.ColorChooser colorChooser1;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JCheckBox filledCB;
    private javax.swing.JLabel filledLabel;
    private javax.swing.JLabel hatchColorLabel;
    private javax.swing.JComboBox<String> hatchStyleCBox;
    private javax.swing.JCheckBox hatchedCB;
    private javax.swing.JLabel hatchedLabel;
    private javax.swing.JLabel hatchedStyleLabel;
    private javax.swing.JPanel nodeHintPanel;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_FILLPROPERTIES";
  }

  @Override
  public void unloadInput() throws IllegalArgumentException
  {
    unLoadDEFUSE();
    
    fillProperties.setFilled(filledCB.isSelected());
    fillProperties.setHatched(hatchedCB.isSelected());
    fillProperties.setHatchStyle(hatchStyleCBox.getSelectedIndex()+1); // 0-based to 1-based
    
    fillProperties.setColor0(color0TF.getText().trim());
    fillProperties.setColor1(color1TF.getText().trim());
    fillProperties.setColor2(color2TF.getText().trim());
  }  
}