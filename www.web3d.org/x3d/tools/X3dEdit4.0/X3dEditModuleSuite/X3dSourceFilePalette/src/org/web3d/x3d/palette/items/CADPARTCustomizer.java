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

import java.awt.Dialog;
import javax.swing.DefaultComboBoxModel;
import javax.swing.text.JTextComponent;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * CADPARTCustomizer.java
 * Created on Jul 7 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class CADPARTCustomizer extends BaseCustomizer
{
  private CADPART cadPart;
  private JTextComponent target;

  private boolean translationScaleApplied, centerScaleApplied = false;
  private String  translationXoriginal, translationYoriginal, translationZoriginal;
  private String       centerXoriginal,      centerYoriginal,      centerZoriginal;
  
  /** Creates new form CADPARTCustomizer */
  public CADPARTCustomizer(CADPART cadPart, JTextComponent target)
  {
    super(cadPart);
    this.cadPart = cadPart;
    this.target = target;
                             
    HelpCtx.setHelpIDString(this, "CADPART_ELEM_HELPID");

    cadPart.setVisualizationSelectionAvailable(true); // must precede initComponents() interface initialization
    cadPart.setVisualizationTooltip("Show center axes, add wireframe Box and axes to show boundingBox center and size (if defined)");
    
    initComponents();
    
    nameTextField.setText(cadPart.getName());
    bboxCenterXTF.setText(cadPart.getBboxCenterX());
    bboxCenterYTF.setText(cadPart.getBboxCenterY());
    bboxCenterZTF.setText(cadPart.getBboxCenterZ());
    bboxSizeXTF.setText(cadPart.getBboxSizeX());
    bboxSizeYTF.setText(cadPart.getBboxSizeY());
    bboxSizeZTF.setText(cadPart.getBboxSizeZ());
    centerXTF.setText(cadPart.getCenterX());
    centerYTF.setText(cadPart.getCenterY());
    centerZTF.setText(cadPart.getCenterZ());
    rotationXaxisTF.setText(cadPart.getRotationX());
    rotationYaxisTF.setText(cadPart.getRotationY());
    rotationZaxisTF.setText(cadPart.getRotationZ());
    rotationAngleTF.setText(cadPart.getRotationAngle());
    scaleOrientationXaxisTF.setText(cadPart.getScaleOrientationX());
    scaleOrientationYaxisTF.setText(cadPart.getScaleOrientationY());
    scaleOrientationZaxisTF.setText(cadPart.getScaleOrientationZ());
    scaleOrientationAngleTF.setText(cadPart.getScaleOrientationAngle());
    scaleXTF.setText(cadPart.getScaleX());
    scaleYTF.setText(cadPart.getScaleY());
    scaleZTF.setText(cadPart.getScaleZ());
    translationXTF.setText(cadPart.getTranslationX());
    translationYTF.setText(cadPart.getTranslationY());
    translationZTF.setText(cadPart.getTranslationZ());
    
    translationXoriginal = cadPart.getTranslationX();
    translationYoriginal = cadPart.getTranslationY();
    translationZoriginal = cadPart.getTranslationZ();
         centerXoriginal = cadPart.getCenterX();
         centerYoriginal = cadPart.getCenterY();
         centerZoriginal = cadPart.getCenterZ();

    checkAngles (false);

    setDefaultDEFname ();
  }
  
  private void setDefaultDEFname ()
  {
    super.getDEFUSEpanel().setDefaultDEFname(NbBundle.getMessage(getClass(),getNameKey()) + nameTextField.getText());
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
        nameLabel = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        xLabel = new javax.swing.JLabel();
        yLabel = new javax.swing.JLabel();
        zLabel = new javax.swing.JLabel();
        angleLabel = new javax.swing.JLabel();
        adjustmentsLabel = new javax.swing.JLabel();
        translationLabel = new javax.swing.JLabel();
        translationXTF = new javax.swing.JTextField();
        translationYTF = new javax.swing.JTextField();
        translationZTF = new javax.swing.JTextField();
        translationModificationComboBox = new javax.swing.JComboBox<String>();
        rotationLabel = new javax.swing.JLabel();
        rotationXaxisTF = new javax.swing.JTextField();
        rotationYaxisTF = new javax.swing.JTextField();
        rotationZaxisTF = new javax.swing.JTextField();
        rotationAngleTF = new javax.swing.JTextField();
        rotationCalculatorlButton = new javax.swing.JButton();
        centerLabel = new javax.swing.JLabel();
        centerXTF = new javax.swing.JTextField();
        centerYTF = new javax.swing.JTextField();
        centerZTF = new javax.swing.JTextField();
        centerModificationComboBox = new javax.swing.JComboBox<String>();
        scaleLabel = new javax.swing.JLabel();
        scaleXTF = new javax.swing.JTextField();
        scaleYTF = new javax.swing.JTextField();
        scaleZTF = new javax.swing.JTextField();
        scaleSelectionComboBox = new javax.swing.JComboBox<String>();
        scaleOrientationLabel = new javax.swing.JLabel();
        scaleOrientationXaxisTF = new javax.swing.JTextField();
        scaleOrientationYaxisTF = new javax.swing.JTextField();
        scaleOrientationZaxisTF = new javax.swing.JTextField();
        scaleOrientationAngleTF = new javax.swing.JTextField();
        scaleOrientationCalculatorlButton = new javax.swing.JButton();
        normalizeRotationValuesButton = new javax.swing.JButton();
        bboxCenterLabel = new javax.swing.JLabel();
        bboxCenterXTF = new javax.swing.JTextField();
        bboxCenterYTF = new javax.swing.JTextField();
        bboxCenterZTF = new javax.swing.JTextField();
        bboxSizeLabel = new javax.swing.JLabel();
        bboxSizeXTF = new javax.swing.JTextField();
        bboxSizeYTF = new javax.swing.JTextField();
        bboxSizeZTF = new javax.swing.JTextField();
        nodeHintPanel = new javax.swing.JPanel();
        hintLabel1 = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel, gridBagConstraints);

        nameLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        nameLabel.setText("name");
        nameLabel.setToolTipText("name of this particular CADPart instance");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nameLabel, gridBagConstraints);

        nameTextField.setToolTipText("name of this particular CADPart instance");
        nameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nameTextField, gridBagConstraints);

        xLabel.setText("x");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(8, 3, 3, 3);
        add(xLabel, gridBagConstraints);

        yLabel.setText("y");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(8, 3, 3, 3);
        add(yLabel, gridBagConstraints);

        zLabel.setText("z");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(8, 3, 3, 3);
        add(zLabel, gridBagConstraints);

        angleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        angleLabel.setText("angle");
        angleLabel.setToolTipText("angle in radians (can convert large degree values if > 6.28)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(8, 3, 3, 3);
        add(angleLabel, gridBagConstraints);

        adjustmentsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        adjustmentsLabel.setText("adjustments");
        adjustmentsLabel.setToolTipText("apply adjustment factor to original value");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(8, 3, 3, 3);
        add(adjustmentsLabel, gridBagConstraints);

        translationLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        translationLabel.setText("translation");
        translationLabel.setToolTipText("Position (x, y, z in meters) of children relative to local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(translationLabel, gridBagConstraints);

        translationXTF.setColumns(4);
        translationXTF.setToolTipText("Position (x, y, z in meters) of children relative to local coordinate system");
        translationXTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                translationXTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(translationXTF, gridBagConstraints);

        translationYTF.setColumns(4);
        translationYTF.setToolTipText("Position (x, y, z in meters) of children relative to local coordinate system");
        translationYTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                translationYTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(translationYTF, gridBagConstraints);

        translationZTF.setColumns(4);
        translationZTF.setToolTipText("Position (x, y, z in meters) of children relative to local coordinate system");
        translationZTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                translationZTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(translationZTF, gridBagConstraints);

        translationModificationComboBox.setModel(new DefaultComboBoxModel<String>(TRANSFORM_ATTR_TRANSLATION_CHOICES));
        translationModificationComboBox.setToolTipText("Scale translation values to meters");
        translationModificationComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                translationModificationComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(translationModificationComboBox, gridBagConstraints);

        rotationLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        rotationLabel.setText("rotation");
        rotationLabel.setToolTipText("Orientation (axis, angle in radians) of children relative to local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(rotationLabel, gridBagConstraints);

        rotationXaxisTF.setColumns(4);
        rotationXaxisTF.setToolTipText("Orientation (axis, angle in radians) of children relative to local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(rotationXaxisTF, gridBagConstraints);

        rotationYaxisTF.setColumns(4);
        rotationYaxisTF.setToolTipText("Orientation (axis, angle in radians) of children relative to local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(rotationYaxisTF, gridBagConstraints);

        rotationZaxisTF.setColumns(4);
        rotationZaxisTF.setToolTipText("Orientation (axis, angle in radians) of children relative to local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(rotationZaxisTF, gridBagConstraints);

        rotationAngleTF.setColumns(4);
        rotationAngleTF.setToolTipText("Orientation (axis, angle in radians) of children relative to local coordinate system");
        rotationAngleTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotationAngleTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.weightx = 0.25;
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
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(rotationCalculatorlButton, gridBagConstraints);

        centerLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        centerLabel.setText("center");
        centerLabel.setToolTipText("Translation offset from origin of local coordinate system, applied prior to rotation or scaling");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(centerLabel, gridBagConstraints);

        centerXTF.setColumns(4);
        centerXTF.setToolTipText("Translation offset from origin of local coordinate system, applied prior to rotation or scaling");
        centerXTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                centerXTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(centerXTF, gridBagConstraints);

        centerYTF.setColumns(4);
        centerYTF.setToolTipText("Translation offset from origin of local coordinate system, applied prior to rotation or scaling");
        centerYTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                centerYTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(centerYTF, gridBagConstraints);

        centerZTF.setColumns(4);
        centerZTF.setToolTipText("Translation offset from origin of local coordinate system, applied prior to rotation or scaling");
        centerZTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                centerZTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(centerZTF, gridBagConstraints);

        centerModificationComboBox.setModel(new DefaultComboBoxModel<String>(TRANSFORM_ATTR_TRANSLATION_CHOICES));
        centerModificationComboBox.setToolTipText("Scale center values to meters");
        centerModificationComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                centerModificationComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(centerModificationComboBox, gridBagConstraints);

        scaleLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        scaleLabel.setText("scale");
        scaleLabel.setToolTipText("Non-uniform x-y-z scale of child coordinate system, adjusted by center and scaleOrientation");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleLabel, gridBagConstraints);

        scaleXTF.setColumns(4);
        scaleXTF.setToolTipText("Non-uniform x-y-z scale of child coordinate system, adjusted by center and scaleOrientation");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleXTF, gridBagConstraints);

        scaleYTF.setColumns(4);
        scaleYTF.setToolTipText("Non-uniform x-y-z scale of child coordinate system, adjusted by center and scaleOrientation");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleYTF, gridBagConstraints);

        scaleZTF.setColumns(4);
        scaleZTF.setToolTipText("Non-uniform x-y-z scale of child coordinate system, adjusted by center and scaleOrientation");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleZTF, gridBagConstraints);

        scaleSelectionComboBox.setModel(new DefaultComboBoxModel<String>(TRANSFORM_ATTR_SCALE_CHOICES));
        scaleSelectionComboBox.setToolTipText("Scale child node dimensions to meters");
        scaleSelectionComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scaleSelectionComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleSelectionComboBox, gridBagConstraints);

        scaleOrientationLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        scaleOrientationLabel.setText("scaleOrientation");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleOrientationLabel, gridBagConstraints);

        scaleOrientationXaxisTF.setColumns(4);
        scaleOrientationXaxisTF.setToolTipText("Preliminary rotation of coordinate system before scaling (to allow scaling around arbitrary orientations)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleOrientationXaxisTF, gridBagConstraints);

        scaleOrientationYaxisTF.setColumns(4);
        scaleOrientationYaxisTF.setToolTipText("Preliminary rotation of coordinate system before scaling (to allow scaling around arbitrary orientations)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleOrientationYaxisTF, gridBagConstraints);

        scaleOrientationZaxisTF.setColumns(4);
        scaleOrientationZaxisTF.setToolTipText("Preliminary rotation of coordinate system before scaling (to allow scaling around arbitrary orientations)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleOrientationZaxisTF, gridBagConstraints);

        scaleOrientationAngleTF.setColumns(4);
        scaleOrientationAngleTF.setToolTipText("Preliminary rotation of coordinate system before scaling (to allow scaling around arbitrary orientations)");
        scaleOrientationAngleTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scaleOrientationAngleTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleOrientationAngleTF, gridBagConstraints);

        scaleOrientationCalculatorlButton.setText("calculator");
        scaleOrientationCalculatorlButton.setToolTipText("launch geoSystem panel");
        scaleOrientationCalculatorlButton.setMaximumSize(new java.awt.Dimension(80, 22));
        scaleOrientationCalculatorlButton.setMinimumSize(new java.awt.Dimension(80, 22));
        scaleOrientationCalculatorlButton.setPreferredSize(new java.awt.Dimension(6, 22));
        scaleOrientationCalculatorlButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scaleOrientationCalculatorlButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleOrientationCalculatorlButton, gridBagConstraints);

        normalizeRotationValuesButton.setText("normalize rotation and scaleOrientation values");
        normalizeRotationValuesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                normalizeRotationValuesButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(normalizeRotationValuesButton, gridBagConstraints);

        bboxCenterLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        bboxCenterLabel.setText("bboxCenter");
        bboxCenterLabel.setToolTipText("position offset from origin of local coordinate system for collision bounding box");
        bboxCenterLabel.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterLabel, gridBagConstraints);

        bboxCenterXTF.setColumns(4);
        bboxCenterXTF.setToolTipText("position offset from origin of local coordinate system for collision bounding box");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterXTF, gridBagConstraints);

        bboxCenterYTF.setColumns(4);
        bboxCenterYTF.setToolTipText("position offset from origin of local coordinate system for collision bounding box");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterYTF, gridBagConstraints);

        bboxCenterZTF.setColumns(4);
        bboxCenterZTF.setToolTipText("position offset from origin of local coordinate system for collision bounding box");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterZTF, gridBagConstraints);

        bboxSizeLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        bboxSizeLabel.setText("bboxSize");
        bboxSizeLabel.setToolTipText("bounding box is automatically calculated, can also be specified as an optimization or constraint");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxSizeLabel, gridBagConstraints);

        bboxSizeXTF.setColumns(4);
        bboxSizeXTF.setToolTipText("bounding box is automatically calculated, can also be specified as an optimization or constraint");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxSizeXTF, gridBagConstraints);

        bboxSizeYTF.setColumns(4);
        bboxSizeYTF.setToolTipText("bounding box is automatically calculated, can also be specified as an optimization or constraint");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxSizeYTF, gridBagConstraints);

        bboxSizeZTF.setColumns(4);
        bboxSizeZTF.setToolTipText("bounding box is automatically calculated, can also be specified as an optimization or constraint");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxSizeZTF, gridBagConstraints);

        nodeHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nodeHintPanel.setLayout(new java.awt.GridBagLayout());

        hintLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel1.setText("<html><p align='center'><b>CADPart</b> can contain multiple CADFace nodes that make up a single part.</p>");
        hintLabel1.setToolTipText("close this panel to add children nodes");
        hintLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        nodeHintPanel.add(hintLabel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nodeHintPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void translationModificationComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_translationModificationComboBoxActionPerformed
    {//GEN-HEADEREND:event_translationModificationComboBoxActionPerformed
        double x, y, z;
        int index = translationModificationComboBox.getSelectedIndex();
        if (index <= 0) return;
        
        if (translationScaleApplied == false)
        {
            x = (new SFDouble(translationXTF.getText())).getValue() * (new SFDouble(TRANSFORM_ATTR_SCALE_FACTORS[index])).getValue();
            y = (new SFDouble(translationYTF.getText())).getValue() * (new SFDouble(TRANSFORM_ATTR_SCALE_FACTORS[index])).getValue();
            z = (new SFDouble(translationZTF.getText())).getValue() * (new SFDouble(TRANSFORM_ATTR_SCALE_FACTORS[index])).getValue();
            translationScaleApplied = true;
        }
        else // applying another scaling-factor change, so use original value
        {
            x = (new SFDouble(translationXoriginal)).getValue() * (new SFDouble(TRANSFORM_ATTR_SCALE_FACTORS[index])).getValue();
            y = (new SFDouble(translationYoriginal)).getValue() * (new SFDouble(TRANSFORM_ATTR_SCALE_FACTORS[index])).getValue();
            z = (new SFDouble(translationZoriginal)).getValue() * (new SFDouble(TRANSFORM_ATTR_SCALE_FACTORS[index])).getValue();
        }
        translationXTF.setText(fiveDigitFormat.format(x));
        translationYTF.setText(fiveDigitFormat.format(y));
        translationZTF.setText(fiveDigitFormat.format(z));
}//GEN-LAST:event_translationModificationComboBoxActionPerformed

    private void centerModificationComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_centerModificationComboBoxActionPerformed
    {//GEN-HEADEREND:event_centerModificationComboBoxActionPerformed
        double x, y, z;
        int index = centerModificationComboBox.getSelectedIndex();
        if (centerScaleApplied == false)
        {
            x = (new SFDouble(centerXTF.getText())).getValue() * (new SFDouble(TRANSFORM_ATTR_SCALE_FACTORS[index])).getValue();
            y = (new SFDouble(centerYTF.getText())).getValue() * (new SFDouble(TRANSFORM_ATTR_SCALE_FACTORS[index])).getValue();
            z = (new SFDouble(centerZTF.getText())).getValue() * (new SFDouble(TRANSFORM_ATTR_SCALE_FACTORS[index])).getValue();
            centerScaleApplied = true;
        }
        else // applying another scaling-factor change, so use original value
        {
            x = (new SFDouble(centerXoriginal)).getValue() * (new SFDouble(TRANSFORM_ATTR_SCALE_FACTORS[index])).getValue();
            y = (new SFDouble(centerYoriginal)).getValue() * (new SFDouble(TRANSFORM_ATTR_SCALE_FACTORS[index])).getValue();
            z = (new SFDouble(centerZoriginal)).getValue() * (new SFDouble(TRANSFORM_ATTR_SCALE_FACTORS[index])).getValue();
        }
        centerXTF.setText(fiveDigitFormat.format(x));
        centerYTF.setText(fiveDigitFormat.format(y));
        centerZTF.setText(fiveDigitFormat.format(z));
}//GEN-LAST:event_centerModificationComboBoxActionPerformed

    private void scaleSelectionComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_scaleSelectionComboBoxActionPerformed
    {//GEN-HEADEREND:event_scaleSelectionComboBoxActionPerformed
        int index = scaleSelectionComboBox.getSelectedIndex();
        scaleXTF.setText(TRANSFORM_ATTR_SCALE_FACTORS[index]);
        scaleYTF.setText(TRANSFORM_ATTR_SCALE_FACTORS[index]);
        scaleZTF.setText(TRANSFORM_ATTR_SCALE_FACTORS[index]);

        // also assign DEF label as descriptive documentation, if no prior DEF provided by author
        boolean priorScalingLabelFound = false;
        for (int i=0; i < TRANSFORM_ATTR_SCALE_LABELS.length; i++)
        {
            if (dEFUSEpanel.getDefTF().getText().equalsIgnoreCase("Scale" + TRANSFORM_ATTR_SCALE_LABELS[i])
                    && (TRANSFORM_ATTR_SCALE_LABELS[i].length() > 0))
                priorScalingLabelFound = true;
        }
        if (((dEFUSEpanel.getDefTF().getText().length()==0) || priorScalingLabelFound) && (TRANSFORM_ATTR_SCALE_LABELS[index].length() > 0))
            // of course if this DEF label is a duplicate occurrence, that is an XML validation error
            dEFUSEpanel.getDefTF().setText("Scale" + TRANSFORM_ATTR_SCALE_LABELS[index]);
        else if (priorScalingLabelFound && (TRANSFORM_ATTR_SCALE_LABELS[index].length() == 0))
            dEFUSEpanel.getDefTF().setText("");
}//GEN-LAST:event_scaleSelectionComboBoxActionPerformed

    private void nameTextFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_nameTextFieldActionPerformed
    {//GEN-HEADEREND:event_nameTextFieldActionPerformed
        setDefaultDEFname ();
    }//GEN-LAST:event_nameTextFieldActionPerformed

    private void normalizeRotationValuesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_normalizeRotationValuesButtonActionPerformed
        checkAngles(true);
        double normalizationFactor, x, y, z, angle;

        x = new SFFloat(rotationXaxisTF.getText()).getValue();
        y = new SFFloat(rotationYaxisTF.getText()).getValue();
        z = new SFFloat(rotationZaxisTF.getText()).getValue();
        angle = new SFFloat(rotationAngleTF.getText()).getValue();
        normalizationFactor = Math.sqrt(x * x + y * y + z * z);
        if (normalizationFactor == 0.0)
        {
            NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                "Found zero-magnitude axis for rotation, reset to 0 1 0", NotifyDescriptor.WARNING_MESSAGE);
            DialogDisplayer.getDefault().notify(descriptor);
            rotationXaxisTF.setText("0");
            rotationYaxisTF.setText("1");
            rotationZaxisTF.setText("0");
        }
        else
        {
            rotationXaxisTF.setText(fiveDigitFormat.format(x / normalizationFactor));
            rotationYaxisTF.setText(fiveDigitFormat.format(y / normalizationFactor));
            rotationZaxisTF.setText(fiveDigitFormat.format(z / normalizationFactor));
        }
        if (angle == -0.0)
        {
            angle = 0.0;
        }
        while (angle < 0.0)
        {
            angle += 2.0 * Math.PI;
        }
        while (angle > 2.0 * Math.PI)
        {
            angle -= 2.0 * Math.PI;
        }
        rotationAngleTF.setText(radiansFormat.format(angle));
        rotationAngleTF.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");

        x = new SFFloat(scaleOrientationXaxisTF.getText()).getValue();
        y = new SFFloat(scaleOrientationYaxisTF.getText()).getValue();
        z = new SFFloat(scaleOrientationZaxisTF.getText()).getValue();
        angle = new SFFloat(scaleOrientationAngleTF.getText()).getValue();
        normalizationFactor = Math.sqrt(x * x + y * y + z * z);
        if (normalizationFactor == 0.0)
        {
            NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                "Found zero-magnitude axis for scaleOrientation, reset to 0 1 0", NotifyDescriptor.WARNING_MESSAGE);
            DialogDisplayer.getDefault().notify(descriptor);
            scaleOrientationXaxisTF.setText("0");
            scaleOrientationYaxisTF.setText("1");
            scaleOrientationZaxisTF.setText("0");
        }
        else
        {
            scaleOrientationXaxisTF.setText(fiveDigitFormat.format(x / normalizationFactor));
            scaleOrientationYaxisTF.setText(fiveDigitFormat.format(y / normalizationFactor));
            scaleOrientationZaxisTF.setText(fiveDigitFormat.format(z / normalizationFactor));
        }
        if (angle == -0.0)
        {
            angle = 0.0;
        }
        while (angle < 0.0)
        {
            angle += 2.0 * Math.PI;
        }
        while (angle > 2.0 * Math.PI)
        {
            angle -= 2.0 * Math.PI;
        }
        scaleOrientationAngleTF.setText(radiansFormat.format(angle));
        scaleOrientationAngleTF.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");
    }//GEN-LAST:event_normalizeRotationValuesButtonActionPerformed

    private void rotationAngleTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rotationAngleTFActionPerformed
        checkAngles (false);
    }//GEN-LAST:event_rotationAngleTFActionPerformed

    private void scaleOrientationAngleTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scaleOrientationAngleTFActionPerformed
        checkAngles (false);
    }//GEN-LAST:event_scaleOrientationAngleTFActionPerformed

    private void translationXTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_translationXTFActionPerformed
        translationXoriginal    = translationXTF.getText();
        translationScaleApplied = false;
    }//GEN-LAST:event_translationXTFActionPerformed

    private void translationYTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_translationYTFActionPerformed
        translationYoriginal    = translationYTF.getText();
        translationScaleApplied = false;
    }//GEN-LAST:event_translationYTFActionPerformed

    private void translationZTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_translationZTFActionPerformed
        translationZoriginal    = translationZTF.getText();
        translationScaleApplied = false;
    }//GEN-LAST:event_translationZTFActionPerformed

    private void centerXTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_centerXTFActionPerformed
        centerXoriginal    = centerXTF.getText();
        centerScaleApplied = false;
    }//GEN-LAST:event_centerXTFActionPerformed

    private void centerYTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_centerYTFActionPerformed
        centerYoriginal    = centerYTF.getText();
        centerScaleApplied = false;
    }//GEN-LAST:event_centerYTFActionPerformed

    private void centerZTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_centerZTFActionPerformed
        centerZoriginal    = centerZTF.getText();
        centerScaleApplied = false;
    }//GEN-LAST:event_centerZTFActionPerformed

    private void rotationCalculatorlButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rotationCalculatorlButtonActionPerformed
        RotationCalculatorPanel rotationCalculatorPanel = new RotationCalculatorPanel(cadPart, "rotation");
        rotationCalculatorPanel.setRotationValue (
            rotationXaxisTF.getText(),
            rotationYaxisTF.getText(),
            rotationZaxisTF.getText(),
            rotationAngleTF.getText());
        DialogDescriptor dd = new DialogDescriptor(rotationCalculatorPanel, "Rotation Calculator for HAnimJoint rotation");
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

    private void scaleOrientationCalculatorlButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scaleOrientationCalculatorlButtonActionPerformed
        RotationCalculatorPanel scaleOrientationCalculatorPanel = new RotationCalculatorPanel(cadPart, "scaleOrientation");
        scaleOrientationCalculatorPanel.setRotationValue (
            scaleOrientationXaxisTF.getText(),
            scaleOrientationYaxisTF.getText(),
            scaleOrientationZaxisTF.getText(),
            scaleOrientationAngleTF.getText());
        DialogDescriptor dd = new DialogDescriptor(scaleOrientationCalculatorPanel, "Rotation Calculator for HAnimHumanoid scaleOrientation");
        Dialog dialog = DialogDisplayer.getDefault().createDialog(dd);
        dialog.setVisible(true);
        if (dd.getValue() != DialogDescriptor.CANCEL_OPTION)
        {
            // save values
            if (!scaleOrientationCalculatorPanel.getRotationResult().isEmpty())
            {
                scaleOrientationXaxisTF.setText(scaleOrientationCalculatorPanel.getRotationResultX());
                scaleOrientationYaxisTF.setText(scaleOrientationCalculatorPanel.getRotationResultY());
                scaleOrientationZaxisTF.setText(scaleOrientationCalculatorPanel.getRotationResultZ());
                scaleOrientationAngleTF.setText(scaleOrientationCalculatorPanel.getRotationResultAngle());
                scaleOrientationAngleTF.setToolTipText(scaleOrientationCalculatorPanel.getRotationResultAngle() + " radians = " +
                    singleDigitFormat.format(Float.parseFloat(scaleOrientationCalculatorPanel.getRotationResultAngle()) * 180.0 / Math.PI) + " degrees");
            }
        }
    }//GEN-LAST:event_scaleOrientationCalculatorlButtonActionPerformed
  
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel adjustmentsLabel;
    private javax.swing.JLabel angleLabel;
    private javax.swing.JLabel bboxCenterLabel;
    private javax.swing.JTextField bboxCenterXTF;
    private javax.swing.JTextField bboxCenterYTF;
    private javax.swing.JTextField bboxCenterZTF;
    private javax.swing.JLabel bboxSizeLabel;
    private javax.swing.JTextField bboxSizeXTF;
    private javax.swing.JTextField bboxSizeYTF;
    private javax.swing.JTextField bboxSizeZTF;
    private javax.swing.JLabel centerLabel;
    private javax.swing.JComboBox<String> centerModificationComboBox;
    private javax.swing.JTextField centerXTF;
    private javax.swing.JTextField centerYTF;
    private javax.swing.JTextField centerZTF;
    private org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel;
    private javax.swing.JLabel hintLabel1;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JPanel nodeHintPanel;
    private javax.swing.JButton normalizeRotationValuesButton;
    private javax.swing.JTextField rotationAngleTF;
    private javax.swing.JButton rotationCalculatorlButton;
    private javax.swing.JLabel rotationLabel;
    private javax.swing.JTextField rotationXaxisTF;
    private javax.swing.JTextField rotationYaxisTF;
    private javax.swing.JTextField rotationZaxisTF;
    private javax.swing.JLabel scaleLabel;
    private javax.swing.JTextField scaleOrientationAngleTF;
    private javax.swing.JButton scaleOrientationCalculatorlButton;
    private javax.swing.JLabel scaleOrientationLabel;
    private javax.swing.JTextField scaleOrientationXaxisTF;
    private javax.swing.JTextField scaleOrientationYaxisTF;
    private javax.swing.JTextField scaleOrientationZaxisTF;
    private javax.swing.JComboBox<String> scaleSelectionComboBox;
    private javax.swing.JTextField scaleXTF;
    private javax.swing.JTextField scaleYTF;
    private javax.swing.JTextField scaleZTF;
    private javax.swing.JLabel translationLabel;
    private javax.swing.JComboBox<String> translationModificationComboBox;
    private javax.swing.JTextField translationXTF;
    private javax.swing.JTextField translationYTF;
    private javax.swing.JTextField translationZTF;
    private javax.swing.JLabel xLabel;
    private javax.swing.JLabel yLabel;
    private javax.swing.JLabel zLabel;
    // End of variables declaration//GEN-END:variables

    private void checkAngles(boolean precedesNormalization)
  {
      // indicate degree values in tooltips
      // usability note:  can enter degree values (-6..+6) as (354..366) to provoke this conversion check
      double angle = new SFFloat(rotationAngleTF.getText()).getValue();
      rotationAngleTF.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");
      if (Math.abs(angle) > 2.0 * Math.PI)
      {
            String message;
            message = "<html><center>Large value provided for <b>rotation</b> angle, which is a radians value.<br/><br/>Convert <b>" + angle + " degrees</b> to <b>" +
                    radiansFormat.format((angle % 360.0) * Math.PI / 180.0) + " radians</b>";
            if (precedesNormalization)
                 message += " before normalization?";
            else message += "?";
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  message, "X3D angles are in radians", NotifyDescriptor.YES_NO_OPTION);
          if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
          {
              angle = (angle % 360.0) * Math.PI / 180.0;
              rotationAngleTF.setText(radiansFormat.format(angle));
              rotationAngleTF.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");
          }
      }
      angle = new SFFloat(scaleOrientationAngleTF.getText()).getValue();
      scaleOrientationAngleTF.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");
      if (Math.abs(angle) > 2.0 * Math.PI)
      {
            String message;
            message = "<html><center>Large value provided for <b>scaleOrientation</b> angle, which is a radians value.<br/><br/>Convert <b>" + angle + " degrees</b> to <b>" +
                    radiansFormat.format((angle % 360.0) * Math.PI / 180.0) + " radians</b>";
            if (precedesNormalization)
                 message += " before normalization?";
            else message += "?";
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  message, "X3D angles are in radians", NotifyDescriptor.YES_NO_OPTION);
          if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
          {
              angle = (angle % 360.0) * Math.PI / 180.0;
              scaleOrientationAngleTF.setText(radiansFormat.format(angle));
              scaleOrientationAngleTF.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");
          }
      }
  }

@Override
  public String getNameKey()
  {
    return "NAME_X3D_CADPART";
  }

  @Override
  public void unloadInput()
  {
    checkAngles (false);
    unLoadDEFUSE();
    
    cadPart.setName(nameTextField.getText().trim());
    cadPart.setBboxCenterX(bboxCenterXTF.getText().trim());
    cadPart.setBboxCenterY(bboxCenterYTF.getText().trim());
    cadPart.setBboxCenterZ(bboxCenterZTF.getText().trim());
    cadPart.setBboxSizeX(bboxSizeXTF.getText().trim());
    cadPart.setBboxSizeY(bboxSizeYTF.getText().trim());
    cadPart.setBboxSizeZ(bboxSizeZTF.getText().trim());
    cadPart.setCenterX(centerXTF.getText().trim());
    cadPart.setCenterY(centerYTF.getText().trim());
    cadPart.setCenterZ(centerZTF.getText().trim());
    cadPart.setRotationX(rotationXaxisTF.getText().trim());
    cadPart.setRotationY(rotationYaxisTF.getText().trim());
    cadPart.setRotationZ(rotationZaxisTF.getText().trim());
    cadPart.setRotationAngle(rotationAngleTF.getText().trim());
    cadPart.setScaleOrientationX(scaleOrientationXaxisTF.getText().trim());
    cadPart.setScaleOrientationY(scaleOrientationYaxisTF.getText().trim());
    cadPart.setScaleOrientationZ(scaleOrientationZaxisTF.getText().trim());
    cadPart.setScaleOrientationAngle(scaleOrientationAngleTF.getText().trim());
    cadPart.setScaleX(scaleXTF.getText().trim());
    cadPart.setScaleY(scaleYTF.getText().trim());
    cadPart.setScaleZ(scaleZTF.getText().trim());
    cadPart.setTranslationX(translationXTF.getText().trim());
    cadPart.setTranslationY(translationYTF.getText().trim());
    cadPart.setTranslationZ(translationZTF.getText().trim());
  }   
}
