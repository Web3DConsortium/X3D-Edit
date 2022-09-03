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
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * TRANSFORMCustomizer.java
 * Created on SEP 26, 2007 11:40 AM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class TRANSFORMCustomizer extends BaseCustomizer
{
  private final TRANSFORM transform;
  private final JTextComponent target;

  private boolean translationScaleApplied, centerScaleApplied = false;
  private String  translationXoriginal, translationYoriginal, translationZoriginal;
  private String       centerXoriginal,      centerYoriginal,      centerZoriginal;
  
  /** Creates new form TRANSFORMCustomizer */
  public TRANSFORMCustomizer(TRANSFORM transform, JTextComponent target)
  {
    super(transform);
    this.transform = transform;
    this.target = target;
                             
    HelpCtx.setHelpIDString(TRANSFORMCustomizer.this, "TRANSFORM_ELEM_HELPID");

    transform.setVisualizationSelectionAvailable(true); // must precede initComponents() interface initialization
    transform.setVisualizationTooltip("Show center axes, add wireframe Box and axes to show boundingBox center and size (if defined)");
    
    initComponents();
    
    // common background for factor-adjustment combo boxes in order to distinguish from field entries
    translationModificationComboBox.setBackground(this.getBackground());
         centerModificationComboBox.setBackground(this.getBackground());
             scaleSelectionComboBox.setBackground(this.getBackground());
    
    // can be the proxy field of a Collision node shape field of CADPart
    super.getDEFUSEpanel().setContainerFieldChoices(TRANSFORM_CONTAINERFIELD_CHOICES, TRANSFORM_CONTAINERFIELD_TOOLTIPS);
    // DEFUSEpanel initialization must NOT be repeated or else array of choices will be overwritten
    
    bboxCenterXTF.setText(transform.getBboxCenterX());
    bboxCenterYTF.setText(transform.getBboxCenterY());
    bboxCenterZTF.setText(transform.getBboxCenterZ());
    bboxSizeXTF.setText(transform.getBboxSizeX());
    bboxSizeYTF.setText(transform.getBboxSizeY());
    bboxSizeZTF.setText(transform.getBboxSizeZ());
    centerXTF.setText(transform.getCenterX());
    centerYTF.setText(transform.getCenterY());
    centerZTF.setText(transform.getCenterZ());
    rotationXaxisTF.setText(transform.getRotationX());
    rotationYaxisTF.setText(transform.getRotationY());
    rotationZaxisTF.setText(transform.getRotationZ());
    rotationAngleTF.setText(transform.getRotationAngle());
    rotationAngleTF.setToolTipText(transform.getRotationAngle() + " radians = " + 
                               singleDigitFormat.format(Float.parseFloat(transform.getRotationAngle()) * 180.0 / Math.PI) + " degrees");
    scaleOrientationXaxisTF.setText(transform.getScaleOrientationX());
    scaleOrientationYaxisTF.setText(transform.getScaleOrientationY());
    scaleOrientationZaxisTF.setText(transform.getScaleOrientationZ());
    scaleOrientationAngleTF.setText(transform.getScaleOrientationAngle());
    scaleXTF.setText(transform.getScaleX());
    scaleYTF.setText(transform.getScaleY());
    scaleZTF.setText(transform.getScaleZ());
    translationXTF.setText(transform.getTranslationX());
    translationYTF.setText(transform.getTranslationY());
    translationZTF.setText(transform.getTranslationZ());
    
    translationXoriginal = transform.getTranslationX();
    translationYoriginal = transform.getTranslationY();
    translationZoriginal = transform.getTranslationZ();
         centerXoriginal = transform.getCenterX();
         centerYoriginal = transform.getCenterY();
         centerZoriginal = transform.getCenterZ();

    checkAngles (false);
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
        xLabel = new javax.swing.JLabel();
        yLabel = new javax.swing.JLabel();
        zLabel = new javax.swing.JLabel();
        angleLabel = new javax.swing.JLabel();
        adjustmentsLabel = new javax.swing.JLabel();
        spacerLabel = new javax.swing.JLabel();
        translationXTF = new javax.swing.JTextField();
        translationYTF = new javax.swing.JTextField();
        translationZTF = new javax.swing.JTextField();
        rotationXaxisTF = new javax.swing.JTextField();
        rotationYaxisTF = new javax.swing.JTextField();
        rotationZaxisTF = new javax.swing.JTextField();
        rotationAngleTF = new javax.swing.JTextField();
        centerXTF = new javax.swing.JTextField();
        centerYTF = new javax.swing.JTextField();
        centerZTF = new javax.swing.JTextField();
        scaleXTF = new javax.swing.JTextField();
        scaleYTF = new javax.swing.JTextField();
        scaleZTF = new javax.swing.JTextField();
        scaleOrientationXaxisTF = new javax.swing.JTextField();
        scaleOrientationYaxisTF = new javax.swing.JTextField();
        scaleOrientationZaxisTF = new javax.swing.JTextField();
        scaleOrientationAngleTF = new javax.swing.JTextField();
        bboxCenterXTF = new javax.swing.JTextField();
        bboxCenterYTF = new javax.swing.JTextField();
        bboxCenterZTF = new javax.swing.JTextField();
        bboxSizeXTF = new javax.swing.JTextField();
        bboxSizeYTF = new javax.swing.JTextField();
        bboxSizeZTF = new javax.swing.JTextField();
        normalizeRotationValuesButton = new javax.swing.JButton();
        scaleOrientationLab = new javax.swing.JLabel();
        bboxSizeLab = new javax.swing.JLabel();
        bboxCenterLab = new javax.swing.JLabel();
        scaleLab = new javax.swing.JLabel();
        centerLab = new javax.swing.JLabel();
        rotationLab = new javax.swing.JLabel();
        translationLab = new javax.swing.JLabel();
        translationModificationComboBox = new javax.swing.JComboBox<>();
        centerModificationComboBox = new javax.swing.JComboBox<>();
        scaleSelectionComboBox = new javax.swing.JComboBox<>();
        rotationCalculatorlButton = new javax.swing.JButton();
        nodeHintPanel = new javax.swing.JPanel();
        hintLabel = new javax.swing.JLabel();
        scaleOrientationCalculatorlButton = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel, gridBagConstraints);

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
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 3, 3, 3);
        add(adjustmentsLabel, gridBagConstraints);

        spacerLabel.setToolTipText("");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 20;
        add(spacerLabel, gridBagConstraints);

        translationXTF.setColumns(4);
        translationXTF.setToolTipText("Position (x, y, z in meters) of children relative to local coordinate system");
        translationXTF.setMaximumSize(null);
        translationXTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                translationXTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(translationXTF, gridBagConstraints);

        translationYTF.setColumns(4);
        translationYTF.setToolTipText("Position (x, y, z in meters) of children relative to local coordinate system");
        translationYTF.setMaximumSize(null);
        translationYTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                translationYTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(translationYTF, gridBagConstraints);

        translationZTF.setColumns(4);
        translationZTF.setToolTipText("Position (x, y, z in meters) of children relative to local coordinate system");
        translationZTF.setMaximumSize(null);
        translationZTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                translationZTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(translationZTF, gridBagConstraints);

        rotationXaxisTF.setColumns(4);
        rotationXaxisTF.setToolTipText("Orientation (axis, angle in radians) of children relative to local coordinate system");
        rotationXaxisTF.setMaximumSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(rotationXaxisTF, gridBagConstraints);

        rotationYaxisTF.setColumns(4);
        rotationYaxisTF.setToolTipText("Orientation (axis, angle in radians) of children relative to local coordinate system");
        rotationYaxisTF.setMaximumSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(rotationYaxisTF, gridBagConstraints);

        rotationZaxisTF.setColumns(4);
        rotationZaxisTF.setToolTipText("Orientation (axis, angle in radians) of children relative to local coordinate system");
        rotationZaxisTF.setMaximumSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
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
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(rotationAngleTF, gridBagConstraints);

        centerXTF.setColumns(4);
        centerXTF.setToolTipText("Translation offset from origin of local coordinate system, applied prior to rotation or scaling");
        centerXTF.setMaximumSize(null);
        centerXTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                centerXTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(centerXTF, gridBagConstraints);

        centerYTF.setColumns(4);
        centerYTF.setToolTipText("Translation offset from origin of local coordinate system, applied prior to rotation or scaling");
        centerYTF.setMaximumSize(null);
        centerYTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                centerYTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(centerYTF, gridBagConstraints);

        centerZTF.setColumns(4);
        centerZTF.setToolTipText("Translation offset from origin of local coordinate system, applied prior to rotation or scaling");
        centerZTF.setMaximumSize(null);
        centerZTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                centerZTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(centerZTF, gridBagConstraints);

        scaleXTF.setColumns(4);
        scaleXTF.setToolTipText("Non-uniform x-y-z scale of child coordinate system, adjusted by center and scaleOrientation");
        scaleXTF.setMaximumSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleXTF, gridBagConstraints);

        scaleYTF.setColumns(4);
        scaleYTF.setToolTipText("Non-uniform x-y-z scale of child coordinate system, adjusted by center and scaleOrientation");
        scaleYTF.setMaximumSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleYTF, gridBagConstraints);

        scaleZTF.setColumns(4);
        scaleZTF.setToolTipText("Non-uniform x-y-z scale of child coordinate system, adjusted by center and scaleOrientation");
        scaleZTF.setMaximumSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleZTF, gridBagConstraints);

        scaleOrientationXaxisTF.setColumns(4);
        scaleOrientationXaxisTF.setToolTipText("Preliminary rotation of coordinate system before scaling (to allow scaling around arbitrary orientations)");
        scaleOrientationXaxisTF.setMaximumSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleOrientationXaxisTF, gridBagConstraints);

        scaleOrientationYaxisTF.setColumns(4);
        scaleOrientationYaxisTF.setToolTipText("Preliminary rotation of coordinate system before scaling (to allow scaling around arbitrary orientations)");
        scaleOrientationYaxisTF.setMaximumSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleOrientationYaxisTF, gridBagConstraints);

        scaleOrientationZaxisTF.setColumns(4);
        scaleOrientationZaxisTF.setToolTipText("Preliminary rotation of coordinate system before scaling (to allow scaling around arbitrary orientations)");
        scaleOrientationZaxisTF.setMaximumSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleOrientationZaxisTF, gridBagConstraints);

        scaleOrientationAngleTF.setColumns(4);
        scaleOrientationAngleTF.setToolTipText("Preliminary rotation of coordinate system before scaling (to allow scaling around arbitrary orientations)");
        scaleOrientationAngleTF.setMaximumSize(null);
        scaleOrientationAngleTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scaleOrientationAngleTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleOrientationAngleTF, gridBagConstraints);

        bboxCenterXTF.setColumns(4);
        bboxCenterXTF.setToolTipText("hint to browser: position offset from origin of local coordinate system for collision bounding box");
        bboxCenterXTF.setMaximumSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterXTF, gridBagConstraints);

        bboxCenterYTF.setColumns(4);
        bboxCenterYTF.setToolTipText("hint to browser: position offset from origin of local coordinate system for collision bounding box");
        bboxCenterYTF.setMaximumSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterYTF, gridBagConstraints);

        bboxCenterZTF.setColumns(4);
        bboxCenterZTF.setToolTipText("hint to browser: position offset from origin of local coordinate system for collision bounding box");
        bboxCenterZTF.setMaximumSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterZTF, gridBagConstraints);

        bboxSizeXTF.setColumns(4);
        bboxSizeXTF.setToolTipText("hint to browser: bounding box is automatically calculated, can also be specified as an optimization or constraint");
        bboxSizeXTF.setMaximumSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxSizeXTF, gridBagConstraints);

        bboxSizeYTF.setColumns(4);
        bboxSizeYTF.setToolTipText("hint to browser: bounding box is automatically calculated, can also be specified as an optimization or constraint");
        bboxSizeYTF.setMaximumSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxSizeYTF, gridBagConstraints);

        bboxSizeZTF.setColumns(4);
        bboxSizeZTF.setToolTipText("hint to browser: bounding box is automatically calculated, can also be specified as an optimization or constraint");
        bboxSizeZTF.setMaximumSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxSizeZTF, gridBagConstraints);

        normalizeRotationValuesButton.setText("normalize rotation and scaleOrientation values");
        normalizeRotationValuesButton.setToolTipText("unit x-y-z vector, normalized radians value ");
        normalizeRotationValuesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                normalizeRotationValuesButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 8, 3);
        add(normalizeRotationValuesButton, gridBagConstraints);

        scaleOrientationLab.setText("scaleOrientation");
        scaleOrientationLab.setToolTipText("Preliminary rotation of coordinate system before scaling (to allow scaling around arbitrary orientations)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        add(scaleOrientationLab, gridBagConstraints);

        bboxSizeLab.setText("bboxSize");
        bboxSizeLab.setToolTipText("hint to browser: bounding box is automatically calculated, can also be specified as an optimization or constraint");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxSizeLab, gridBagConstraints);

        bboxCenterLab.setText("bboxCenter");
        bboxCenterLab.setToolTipText("hint to browser: position offset from origin of local coordinate system for collision bounding box");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterLab, gridBagConstraints);

        scaleLab.setText("scale");
        scaleLab.setToolTipText("Non-uniform x-y-z scale of child coordinate system, adjusted by center and scaleOrientation");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleLab, gridBagConstraints);

        centerLab.setText("center");
        centerLab.setToolTipText("Translation offset from origin of local coordinate system, applied prior to rotation or scaling");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(centerLab, gridBagConstraints);

        rotationLab.setText("rotation");
        rotationLab.setToolTipText("Orientation (axis, angle in radians) of children relative to local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(rotationLab, gridBagConstraints);

        translationLab.setText("translation");
        translationLab.setToolTipText("Position (x, y, z in meters) of children relative to local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(translationLab, gridBagConstraints);

        translationModificationComboBox.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        translationModificationComboBox.setModel(new DefaultComboBoxModel<String>(TRANSFORM_ATTR_TRANSLATION_CHOICES));
        translationModificationComboBox.setToolTipText("Scale translation values to meters");
        translationModificationComboBox.setMaximumSize(new java.awt.Dimension(40, 22));
        translationModificationComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                translationModificationComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(translationModificationComboBox, gridBagConstraints);

        centerModificationComboBox.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        centerModificationComboBox.setModel(new DefaultComboBoxModel<String>(TRANSFORM_ATTR_TRANSLATION_CHOICES));
        centerModificationComboBox.setToolTipText("Scale center values to meters");
        centerModificationComboBox.setMaximumSize(new java.awt.Dimension(40, 22));
        centerModificationComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                centerModificationComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(centerModificationComboBox, gridBagConstraints);

        scaleSelectionComboBox.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        scaleSelectionComboBox.setModel(new DefaultComboBoxModel<String>(TRANSFORM_ATTR_SCALE_CHOICES));
        scaleSelectionComboBox.setToolTipText("Scale child node dimensions to meters");
        scaleSelectionComboBox.setMaximumSize(new java.awt.Dimension(40, 22));
        scaleSelectionComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scaleSelectionComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleSelectionComboBox, gridBagConstraints);

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
        gridBagConstraints.gridy = 7;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(rotationCalculatorlButton, gridBagConstraints);

        nodeHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nodeHintPanel.setLayout(new java.awt.GridBagLayout());

        hintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel.setText("<html><p align='center'><b>Transform</b> is a Grouping node that can contain most nodes. </p>  <br /><p align='center'><b>Transform</b>  translates, orients and scales child geometry within the local world coordinate system.  <br />Each transformation creates a new coordinate system relative to the parent coordinate system.</p>");
        hintLabel.setToolTipText("close this panel to add children nodes");
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
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 3, 3, 3);
        add(nodeHintPanel, gridBagConstraints);

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
        gridBagConstraints.gridy = 8;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleOrientationCalculatorlButton, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void normalizeRotationValuesButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_normalizeRotationValuesButtonActionPerformed
    {//GEN-HEADEREND:event_normalizeRotationValuesButtonActionPerformed
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
        rotationAngleTF.setToolTipText(radiansFormat.format(angle) + " radians = " +
                                   singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");

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

    private void rotationAngleTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_rotationAngleTFActionPerformed
    {//GEN-HEADEREND:event_rotationAngleTFActionPerformed
        checkAngles (false);
    }//GEN-LAST:event_rotationAngleTFActionPerformed

    private void scaleOrientationAngleTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_scaleOrientationAngleTFActionPerformed
    {//GEN-HEADEREND:event_scaleOrientationAngleTFActionPerformed
        checkAngles (false);
    }//GEN-LAST:event_scaleOrientationAngleTFActionPerformed

    private void scaleSelectionComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scaleSelectionComboBoxActionPerformed
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

    private void translationModificationComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_translationModificationComboBoxActionPerformed
        double x, y, z;
        int index = translationModificationComboBox.getSelectedIndex();
        if (index <= 0) return;
        
        if (translationScaleApplied == false)
        {
            x = (new SFFloat(translationXTF.getText()).getValue()) * (new SFFloat(TRANSFORM_ATTR_SCALE_FACTORS[index]).getValue());
            y = (new SFFloat(translationYTF.getText()).getValue()) * (new SFFloat(TRANSFORM_ATTR_SCALE_FACTORS[index]).getValue());
            z = (new SFFloat(translationZTF.getText()).getValue()) * (new SFFloat(TRANSFORM_ATTR_SCALE_FACTORS[index]).getValue());
            translationScaleApplied = true;
        }
        else // applying another scaling-factor change, so use original value
        {
            x = (new SFFloat(translationXoriginal).getValue()) * (new SFFloat(TRANSFORM_ATTR_SCALE_FACTORS[index]).getValue());
            y = (new SFFloat(translationYoriginal).getValue()) * (new SFFloat(TRANSFORM_ATTR_SCALE_FACTORS[index]).getValue());
            z = (new SFFloat(translationZoriginal).getValue()) * (new SFFloat(TRANSFORM_ATTR_SCALE_FACTORS[index]).getValue());
        }
        translationXTF.setText(fiveDigitFormat.format(x));
        translationYTF.setText(fiveDigitFormat.format(y));
        translationZTF.setText(fiveDigitFormat.format(z));
    }//GEN-LAST:event_translationModificationComboBoxActionPerformed

    private void centerModificationComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_centerModificationComboBoxActionPerformed
        double x, y, z;
        int index = centerModificationComboBox.getSelectedIndex();
        if (centerScaleApplied == false)
        {
            x = (new SFFloat(centerXTF.getText()).getValue() * new SFFloat(TRANSFORM_ATTR_SCALE_FACTORS[index]).getValue());
            y = (new SFFloat(centerYTF.getText()).getValue() * new SFFloat(TRANSFORM_ATTR_SCALE_FACTORS[index]).getValue());
            z = (new SFFloat(centerZTF.getText()).getValue() * new SFFloat(TRANSFORM_ATTR_SCALE_FACTORS[index]).getValue());
            centerScaleApplied = true;
        }
        else // applying another scaling-factor change, so use original value
        {
            x = (new SFFloat(centerXoriginal).getValue() * new SFFloat(TRANSFORM_ATTR_SCALE_FACTORS[index]).getValue());
            y = (new SFFloat(centerYoriginal).getValue() * new SFFloat(TRANSFORM_ATTR_SCALE_FACTORS[index]).getValue());
            z = (new SFFloat(centerZoriginal).getValue() * new SFFloat(TRANSFORM_ATTR_SCALE_FACTORS[index]).getValue());
        }
        centerXTF.setText(fiveDigitFormat.format(x));
        centerYTF.setText(fiveDigitFormat.format(y));
        centerZTF.setText(fiveDigitFormat.format(z));
    }//GEN-LAST:event_centerModificationComboBoxActionPerformed

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
        RotationCalculatorPanel rotationCalculatorPanel = new RotationCalculatorPanel(transform, "rotation");
        rotationCalculatorPanel.setRotationValue (
                rotationXaxisTF.getText(),
                rotationYaxisTF.getText(),
                rotationZaxisTF.getText(),
                rotationAngleTF.getText());
        DialogDescriptor dd = new DialogDescriptor(rotationCalculatorPanel, "Rotation Calculator for Transform rotation");
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
        RotationCalculatorPanel scaleOrientationCalculatorPanel = new RotationCalculatorPanel(transform, "scaleOrientation");
        scaleOrientationCalculatorPanel.setRotationValue (
                scaleOrientationXaxisTF.getText(),
                scaleOrientationYaxisTF.getText(),
                scaleOrientationZaxisTF.getText(),
                scaleOrientationAngleTF.getText());
        DialogDescriptor dd = new DialogDescriptor(scaleOrientationCalculatorPanel, "Rotation Calculator for Transform scaleOrientation");
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
    private javax.swing.JLabel bboxCenterLab;
    private javax.swing.JTextField bboxCenterXTF;
    private javax.swing.JTextField bboxCenterYTF;
    private javax.swing.JTextField bboxCenterZTF;
    private javax.swing.JLabel bboxSizeLab;
    private javax.swing.JTextField bboxSizeXTF;
    private javax.swing.JTextField bboxSizeYTF;
    private javax.swing.JTextField bboxSizeZTF;
    private javax.swing.JLabel centerLab;
    private javax.swing.JComboBox<String> centerModificationComboBox;
    private javax.swing.JTextField centerXTF;
    private javax.swing.JTextField centerYTF;
    private javax.swing.JTextField centerZTF;
    private org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel;
    private javax.swing.JLabel hintLabel;
    private javax.swing.JPanel nodeHintPanel;
    private javax.swing.JButton normalizeRotationValuesButton;
    private javax.swing.JTextField rotationAngleTF;
    private javax.swing.JButton rotationCalculatorlButton;
    private javax.swing.JLabel rotationLab;
    private javax.swing.JTextField rotationXaxisTF;
    private javax.swing.JTextField rotationYaxisTF;
    private javax.swing.JTextField rotationZaxisTF;
    private javax.swing.JLabel scaleLab;
    private javax.swing.JTextField scaleOrientationAngleTF;
    private javax.swing.JButton scaleOrientationCalculatorlButton;
    private javax.swing.JLabel scaleOrientationLab;
    private javax.swing.JTextField scaleOrientationXaxisTF;
    private javax.swing.JTextField scaleOrientationYaxisTF;
    private javax.swing.JTextField scaleOrientationZaxisTF;
    private javax.swing.JComboBox<String> scaleSelectionComboBox;
    private javax.swing.JTextField scaleXTF;
    private javax.swing.JTextField scaleYTF;
    private javax.swing.JTextField scaleZTF;
    private javax.swing.JLabel spacerLabel;
    private javax.swing.JLabel translationLab;
    private javax.swing.JComboBox<String> translationModificationComboBox;
    private javax.swing.JTextField translationXTF;
    private javax.swing.JTextField translationYTF;
    private javax.swing.JTextField translationZTF;
    private javax.swing.JLabel xLabel;
    private javax.swing.JLabel yLabel;
    private javax.swing.JLabel zLabel;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_TRANSFORM";
  }

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
  public void unloadInput()
  {
    checkAngles (false);
    unLoadDEFUSE();
    
    transform.setBboxCenterX(bboxCenterXTF.getText().trim());
    transform.setBboxCenterY(bboxCenterYTF.getText().trim());
    transform.setBboxCenterZ(bboxCenterZTF.getText().trim());
    transform.setBboxSizeX(bboxSizeXTF.getText().trim());
    transform.setBboxSizeY(bboxSizeYTF.getText().trim());
    transform.setBboxSizeZ(bboxSizeZTF.getText().trim());
    transform.setCenterX(centerXTF.getText().trim());
    transform.setCenterY(centerYTF.getText().trim());
    transform.setCenterZ(centerZTF.getText().trim());
    transform.setRotationX(rotationXaxisTF.getText().trim());
    transform.setRotationY(rotationYaxisTF.getText().trim());
    transform.setRotationZ(rotationZaxisTF.getText().trim());
    transform.setRotationAngle(rotationAngleTF.getText().trim());
    transform.setScaleOrientationX(scaleOrientationXaxisTF.getText().trim());
    transform.setScaleOrientationY(scaleOrientationYaxisTF.getText().trim());
    transform.setScaleOrientationZ(scaleOrientationZaxisTF.getText().trim());
    transform.setScaleOrientationAngle(scaleOrientationAngleTF.getText().trim());
    transform.setScaleX(scaleXTF.getText().trim());
    transform.setScaleY(scaleYTF.getText().trim());
    transform.setScaleZ(scaleZTF.getText().trim());
    transform.setTranslationX(translationXTF.getText().trim());
    transform.setTranslationY(translationYTF.getText().trim());
    transform.setTranslationZ(translationZTF.getText().trim());
  }   
}
