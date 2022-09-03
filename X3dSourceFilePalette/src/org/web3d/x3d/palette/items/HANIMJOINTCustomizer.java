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
 * HANIMJOINTCustomizer.java
 * Created on 2 May 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey and Don Brutzman
 * @version $Id$
 */
public class HANIMJOINTCustomizer extends BaseCustomizer
{
  private HANIMJOINT hAnimJoint;
  private JTextComponent target;
  
  private boolean translationScaleApplied, centerScaleApplied = false;
  private String  translationXoriginal, translationYoriginal, translationZoriginal;
  private String       centerXoriginal,      centerYoriginal,      centerZoriginal;
  
  /** Creates new form ARC2DCustomizer */
  public HANIMJOINTCustomizer(HANIMJOINT hAnimJoint, JTextComponent target)
  {
    super(hAnimJoint);
    this.hAnimJoint = hAnimJoint;
    this.target = target;
    
    HelpCtx.setHelpIDString(HANIMJOINTCustomizer.this, "HANIMJOINT_ELEM_HELPID");

    // Note that visualization is not practical for HAnimJoint itself because it can only contain HAnimJoint, HAnimSegment or HAnimSite as children
    // Authors can add visualization using HAnimSegment and HAnimSite instead
//    hAnimJoint.setVisualizationSelectionAvailable(true); // must precede initComponents() interface initialization
//    hAnimJoint.setVisualizationTooltip("Show center axes, add wireframe Box and axes to show boundingBox center and size (if defined)");
    
    initComponents();
    
    super.getDEFUSEpanel().setContainerFieldChoices(HANIMJOINT_CONTAINERFIELD_CHOICES, HANIMJOINT_CONTAINERFIELD_TOOLTIPS);
    // DEFUSEpanel initialization must NOT be repeated or else array of choices will be overwritten
    
    // common background for factor-adjustment combo boxes in order to distinguish from field entries
    translationModificationComboBox.setBackground(this.getBackground());
         centerModificationComboBox.setBackground(this.getBackground());
             scaleSelectionComboBox.setBackground(this.getBackground());
    
    nameComboBox.setSelectedItem(hAnimJoint.getName());
    skinCoordIndexTF.setText    (hAnimJoint.getSkinCoordIndex());
    skinCoordWeightTF.setText   (hAnimJoint.getSkinCoordWeight());
    llimitTF.setText            (hAnimJoint.getLLimit());
    stiffnessTF.setText         (hAnimJoint.getStiffness());
    limitOrientationXaxisTF.setText (    hAnimJoint.getLimitOrientationX());
    limitOrientationYaxisTF.setText     (hAnimJoint.getLimitOrientationY());
    limitOrientationZaxisTF.setText     (hAnimJoint.getLimitOrientationZ());
    limitOrientationAngleTF.setText (hAnimJoint.getLimitOrientationAngle());
    
    bboxCenterXTF.setText(hAnimJoint.getBboxCenterX());
    bboxCenterYTF.setText(hAnimJoint.getBboxCenterY());
    bboxCenterZTF.setText(hAnimJoint.getBboxCenterZ());
    bboxSizeXTF.setText(hAnimJoint.getBboxSizeX());
    bboxSizeYTF.setText(hAnimJoint.getBboxSizeY());
    bboxSizeZTF.setText(hAnimJoint.getBboxSizeZ());
    centerXTF.setText(hAnimJoint.getCenterX());
    centerYTF.setText(hAnimJoint.getCenterY());
    centerZTF.setText(hAnimJoint.getCenterZ());
    rotationXaxisTF.setText(hAnimJoint.getRotationX());
    rotationYaxisTF.setText(hAnimJoint.getRotationY());
    rotationZaxisTF.setText(hAnimJoint.getRotationZ());
    rotationAngleTF.setText(hAnimJoint.getRotationAngle());
    scaleOrientationXaxisTF.setText(hAnimJoint.getScaleOrientationX());
    scaleOrientationYaxisTF.setText(hAnimJoint.getScaleOrientationY());
    scaleOrientationZaxisTF.setText(hAnimJoint.getScaleOrientationZ());
    scaleOrientationAngleTF.setText(hAnimJoint.getScaleOrientationAngle());
    scaleXTF.setText(hAnimJoint.getScaleX());
    scaleYTF.setText(hAnimJoint.getScaleY());
    scaleZTF.setText(hAnimJoint.getScaleZ());
    translationXTF.setText(hAnimJoint.getTranslationX());
    translationYTF.setText(hAnimJoint.getTranslationY());
    translationZTF.setText(hAnimJoint.getTranslationZ());
    
    translationXoriginal = hAnimJoint.getTranslationX();
    translationYoriginal = hAnimJoint.getTranslationY();
    translationZoriginal = hAnimJoint.getTranslationZ();
         centerXoriginal = hAnimJoint.getCenterX();
         centerYoriginal = hAnimJoint.getCenterY();
         centerZoriginal = hAnimJoint.getCenterZ();

    checkAngles (false);

    setDefaultDEFname ();
  }
  private void setDefaultDEFname ()
  {
    super.getDEFUSEpanel().setDefaultDEFname(NbBundle.getMessage(getClass(),getNameKey()) + nameComboBox.getSelectedItem().toString());

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
        nameComboBox = new javax.swing.JComboBox<>();
        llimitLabel = new javax.swing.JLabel();
        llimitTF = new javax.swing.JTextField();
        limitOrientationLabel = new javax.swing.JLabel();
        limitOrientationXaxisTF = new javax.swing.JTextField();
        limitOrientationYaxisTF = new javax.swing.JTextField();
        limitOrientationZaxisTF = new javax.swing.JTextField();
        limitOrientationAngleTF = new javax.swing.JTextField();
        lilmitOrientationCalculatorlButton = new javax.swing.JButton();
        skinCoordIndexLabel = new javax.swing.JLabel();
        skinCoordIndexTF = new javax.swing.JTextField();
        skinCoordWeightLabel = new javax.swing.JLabel();
        skinCoordWeightTF = new javax.swing.JTextField();
        stiffnessLabel = new javax.swing.JLabel();
        stiffnessTF = new javax.swing.JTextField();
        xLabel = new javax.swing.JLabel();
        yLabel = new javax.swing.JLabel();
        zLabel = new javax.swing.JLabel();
        angleLabel = new javax.swing.JLabel();
        adjustmentsLabel = new javax.swing.JLabel();
        translationLabel = new javax.swing.JLabel();
        translationXTF = new javax.swing.JTextField();
        translationYTF = new javax.swing.JTextField();
        translationZTF = new javax.swing.JTextField();
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
        scaleLabel = new javax.swing.JLabel();
        scaleXTF = new javax.swing.JTextField();
        scaleYTF = new javax.swing.JTextField();
        scaleZTF = new javax.swing.JTextField();
        scaleOrientationLabel = new javax.swing.JLabel();
        scaleOrientationXaxisTF = new javax.swing.JTextField();
        scaleOrientationYaxisTF = new javax.swing.JTextField();
        scaleOrientationZaxisTF = new javax.swing.JTextField();
        scaleOrientationAngleTF = new javax.swing.JTextField();
        scaleOrientationCalculatorlButton = new javax.swing.JButton();
        boxbCenterLabel = new javax.swing.JLabel();
        bboxCenterXTF = new javax.swing.JTextField();
        bboxCenterYTF = new javax.swing.JTextField();
        bboxCenterZTF = new javax.swing.JTextField();
        bboxSizeLabel = new javax.swing.JLabel();
        bboxSizeXTF = new javax.swing.JTextField();
        bboxSizeYTF = new javax.swing.JTextField();
        bboxSizeZTF = new javax.swing.JTextField();
        normalizeRotationValuesButton = new javax.swing.JButton();
        nodeHintPanel = new javax.swing.JPanel();
        hintLabel = new javax.swing.JLabel();
        translationModificationComboBox = new javax.swing.JComboBox<>();
        centerModificationComboBox = new javax.swing.JComboBox<>();
        scaleSelectionComboBox = new javax.swing.JComboBox<>();

        setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel, gridBagConstraints);

        nameLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        nameLabel.setText("name");
        nameLabel.setToolTipText("Must assign proper name for this HAnimJoint");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 96;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nameLabel, gridBagConstraints);

        nameComboBox.setEditable(true);
        nameComboBox.setModel(new DefaultComboBoxModel<String>(HANIMJOINT_NAME_CHOICES));
        nameComboBox.setToolTipText("select HAminJoint name");
        nameComboBox.setAutoscrolls(true);
        nameComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nameComboBox, gridBagConstraints);

        llimitLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        llimitLabel.setText("llimit");
        llimitLabel.setToolTipText("Lower limit for minimum joint rotation in radians");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 101;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(llimitLabel, gridBagConstraints);

        llimitTF.setToolTipText("Lower limit for minimum joint rotation in radians");
        llimitTF.setMinimumSize(new java.awt.Dimension(50, 18));
        llimitTF.setPreferredSize(new java.awt.Dimension(50, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(llimitTF, gridBagConstraints);

        limitOrientationLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        limitOrientationLabel.setText("limitOrientation");
        limitOrientationLabel.setToolTipText("Orientation of upper/lower rotation limits, relative to HAnimJoint center");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.ipadx = 41;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(limitOrientationLabel, gridBagConstraints);

        limitOrientationXaxisTF.setToolTipText("Orientation of upper/lower rotation limits, relative to HAnimJoint center");
        limitOrientationXaxisTF.setMinimumSize(new java.awt.Dimension(50, 18));
        limitOrientationXaxisTF.setPreferredSize(new java.awt.Dimension(50, 18));
        limitOrientationXaxisTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limitOrientationXaxisTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(limitOrientationXaxisTF, gridBagConstraints);

        limitOrientationYaxisTF.setToolTipText("Orientation of upper/lower rotation limits, relative to HAnimJoint center");
        limitOrientationYaxisTF.setMinimumSize(new java.awt.Dimension(50, 18));
        limitOrientationYaxisTF.setPreferredSize(new java.awt.Dimension(50, 18));
        limitOrientationYaxisTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limitOrientationYaxisTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(limitOrientationYaxisTF, gridBagConstraints);

        limitOrientationZaxisTF.setToolTipText("Orientation of upper/lower rotation limits, relative to HAnimJoint center");
        limitOrientationZaxisTF.setMinimumSize(new java.awt.Dimension(50, 18));
        limitOrientationZaxisTF.setPreferredSize(new java.awt.Dimension(50, 18));
        limitOrientationZaxisTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limitOrientationZaxisTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(limitOrientationZaxisTF, gridBagConstraints);

        limitOrientationAngleTF.setToolTipText("Orientation of upper/lower rotation limits, relative to HAnimJoint center");
        limitOrientationAngleTF.setMinimumSize(new java.awt.Dimension(50, 18));
        limitOrientationAngleTF.setPreferredSize(new java.awt.Dimension(50, 18));
        limitOrientationAngleTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                limitOrientationAngleTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(limitOrientationAngleTF, gridBagConstraints);

        lilmitOrientationCalculatorlButton.setText("calculator");
        lilmitOrientationCalculatorlButton.setToolTipText("launch geoSystem panel");
        lilmitOrientationCalculatorlButton.setMaximumSize(new java.awt.Dimension(80, 22));
        lilmitOrientationCalculatorlButton.setMinimumSize(new java.awt.Dimension(80, 22));
        lilmitOrientationCalculatorlButton.setPreferredSize(new java.awt.Dimension(6, 22));
        lilmitOrientationCalculatorlButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lilmitOrientationCalculatorlButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(lilmitOrientationCalculatorlButton, gridBagConstraints);

        skinCoordIndexLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        skinCoordIndexLabel.setText("skinCoordIndex");
        skinCoordIndexLabel.setToolTipText("which Coordinate vertices are influenced by the joint");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 41;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(skinCoordIndexLabel, gridBagConstraints);

        skinCoordIndexTF.setToolTipText("which Coordinate vertices are influenced by the joint");
        skinCoordIndexTF.setMinimumSize(new java.awt.Dimension(50, 18));
        skinCoordIndexTF.setPreferredSize(new java.awt.Dimension(50, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(skinCoordIndexTF, gridBagConstraints);

        skinCoordWeightLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        skinCoordWeightLabel.setText("skinCoordWeight");
        skinCoordWeightLabel.setToolTipText("Weight deformations for corresponding skinCoordIndex values ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 32;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(skinCoordWeightLabel, gridBagConstraints);

        skinCoordWeightTF.setToolTipText("Weight deformations for corresponding skinCoordIndex values ");
        skinCoordWeightTF.setMinimumSize(new java.awt.Dimension(50, 18));
        skinCoordWeightTF.setPreferredSize(new java.awt.Dimension(50, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(skinCoordWeightTF, gridBagConstraints);

        stiffnessLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        stiffnessLabel.setText("stiffness");
        stiffnessLabel.setToolTipText("value (0..1) indicating willingness of joint to move, larger stiffness values means greater resistance (about local X, Y, Z axes)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.ipadx = 81;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(stiffnessLabel, gridBagConstraints);

        stiffnessTF.setToolTipText("value (0..1) indicating willingness of joint to move, larger stiffness values means greater resistance (about local X, Y, Z axes)");
        stiffnessTF.setMinimumSize(new java.awt.Dimension(50, 18));
        stiffnessTF.setPreferredSize(new java.awt.Dimension(50, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(stiffnessTF, gridBagConstraints);

        xLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        xLabel.setText("x");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(xLabel, gridBagConstraints);

        yLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        yLabel.setText("y");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(yLabel, gridBagConstraints);

        zLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        zLabel.setText("z");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(zLabel, gridBagConstraints);

        angleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        angleLabel.setText("angle");
        angleLabel.setToolTipText("angle in radians (can convert large degree values if > 6.28)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(angleLabel, gridBagConstraints);

        adjustmentsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        adjustmentsLabel.setText("adjustments");
        adjustmentsLabel.setToolTipText("apply adjustment factor to original value");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(8, 3, 3, 3);
        add(adjustmentsLabel, gridBagConstraints);

        translationLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        translationLabel.setText("translation");
        translationLabel.setToolTipText("Position (x, y, z in meters) of children relative to local coordinate system");
        translationLabel.setMaximumSize(null);
        translationLabel.setMinimumSize(new java.awt.Dimension(80, 18));
        translationLabel.setPreferredSize(new java.awt.Dimension(80, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.ipadx = 48;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(translationLabel, gridBagConstraints);

        translationXTF.setColumns(4);
        translationXTF.setToolTipText("Position (x, y, z in meters) of children relative to local coordinate system");
        translationXTF.setMaximumSize(null);
        translationXTF.setMinimumSize(new java.awt.Dimension(50, 18));
        translationXTF.setPreferredSize(new java.awt.Dimension(50, 18));
        translationXTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                translationXTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(translationXTF, gridBagConstraints);

        translationYTF.setColumns(4);
        translationYTF.setToolTipText("Position (x, y, z in meters) of children relative to local coordinate system");
        translationYTF.setMaximumSize(null);
        translationYTF.setMinimumSize(new java.awt.Dimension(50, 18));
        translationYTF.setPreferredSize(new java.awt.Dimension(50, 18));
        translationYTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                translationYTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(translationYTF, gridBagConstraints);

        translationZTF.setColumns(4);
        translationZTF.setToolTipText("Position (x, y, z in meters) of children relative to local coordinate system");
        translationZTF.setMaximumSize(null);
        translationZTF.setMinimumSize(new java.awt.Dimension(50, 18));
        translationZTF.setPreferredSize(new java.awt.Dimension(50, 18));
        translationZTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                translationZTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(translationZTF, gridBagConstraints);

        rotationLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        rotationLabel.setText("rotation");
        rotationLabel.setToolTipText("Orientation (axis, angle in radians) of children relative to local coordinate system");
        rotationLabel.setMaximumSize(null);
        rotationLabel.setMinimumSize(new java.awt.Dimension(80, 18));
        rotationLabel.setPreferredSize(new java.awt.Dimension(80, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.ipadx = 48;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(rotationLabel, gridBagConstraints);

        rotationXaxisTF.setColumns(4);
        rotationXaxisTF.setToolTipText("Orientation (axis, angle in radians) of children relative to local coordinate system");
        rotationXaxisTF.setMaximumSize(null);
        rotationXaxisTF.setMinimumSize(new java.awt.Dimension(50, 18));
        rotationXaxisTF.setPreferredSize(new java.awt.Dimension(50, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(rotationXaxisTF, gridBagConstraints);

        rotationYaxisTF.setColumns(4);
        rotationYaxisTF.setToolTipText("Orientation (axis, angle in radians) of children relative to local coordinate system");
        rotationYaxisTF.setMaximumSize(null);
        rotationYaxisTF.setMinimumSize(new java.awt.Dimension(50, 18));
        rotationYaxisTF.setPreferredSize(new java.awt.Dimension(50, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(rotationYaxisTF, gridBagConstraints);

        rotationZaxisTF.setColumns(4);
        rotationZaxisTF.setToolTipText("Orientation (axis, angle in radians) of children relative to local coordinate system");
        rotationZaxisTF.setMaximumSize(null);
        rotationZaxisTF.setMinimumSize(new java.awt.Dimension(50, 18));
        rotationZaxisTF.setPreferredSize(new java.awt.Dimension(50, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(rotationZaxisTF, gridBagConstraints);

        rotationAngleTF.setColumns(4);
        rotationAngleTF.setToolTipText("Orientation (axis, angle in radians) of children relative to local coordinate system");
        rotationAngleTF.setMaximumSize(null);
        rotationAngleTF.setMinimumSize(new java.awt.Dimension(50, 18));
        rotationAngleTF.setPreferredSize(new java.awt.Dimension(50, 18));
        rotationAngleTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotationAngleTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
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
        gridBagConstraints.gridy = 16;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(rotationCalculatorlButton, gridBagConstraints);

        centerLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        centerLabel.setText("center");
        centerLabel.setToolTipText("Translation offset from origin of local coordinate system, applied prior to rotation or scaling");
        centerLabel.setMaximumSize(null);
        centerLabel.setMinimumSize(new java.awt.Dimension(80, 18));
        centerLabel.setPreferredSize(new java.awt.Dimension(80, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.ipadx = 48;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(centerLabel, gridBagConstraints);

        centerXTF.setColumns(4);
        centerXTF.setToolTipText("Translation offset from origin of local coordinate system, applied prior to rotation or scaling");
        centerXTF.setMaximumSize(null);
        centerXTF.setMinimumSize(new java.awt.Dimension(50, 18));
        centerXTF.setPreferredSize(new java.awt.Dimension(50, 18));
        centerXTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                centerXTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(centerXTF, gridBagConstraints);

        centerYTF.setColumns(4);
        centerYTF.setToolTipText("Translation offset from origin of local coordinate system, applied prior to rotation or scaling");
        centerYTF.setMaximumSize(null);
        centerYTF.setMinimumSize(new java.awt.Dimension(50, 18));
        centerYTF.setPreferredSize(new java.awt.Dimension(50, 18));
        centerYTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                centerYTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(centerYTF, gridBagConstraints);

        centerZTF.setColumns(4);
        centerZTF.setToolTipText("Translation offset from origin of local coordinate system, applied prior to rotation or scaling");
        centerZTF.setMaximumSize(null);
        centerZTF.setMinimumSize(new java.awt.Dimension(50, 18));
        centerZTF.setPreferredSize(new java.awt.Dimension(50, 18));
        centerZTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                centerZTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(centerZTF, gridBagConstraints);

        scaleLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        scaleLabel.setText("scale");
        scaleLabel.setToolTipText("Non-uniform x-y-z scale of child coordinate system, adjusted by center and scaleOrientation");
        scaleLabel.setMaximumSize(null);
        scaleLabel.setMinimumSize(new java.awt.Dimension(80, 18));
        scaleLabel.setPreferredSize(new java.awt.Dimension(80, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.ipadx = 48;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleLabel, gridBagConstraints);

        scaleXTF.setColumns(4);
        scaleXTF.setToolTipText("Non-uniform x-y-z scale of child coordinate system, adjusted by center and scaleOrientation");
        scaleXTF.setMaximumSize(null);
        scaleXTF.setMinimumSize(new java.awt.Dimension(50, 18));
        scaleXTF.setPreferredSize(new java.awt.Dimension(50, 18));
        scaleXTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scaleXTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleXTF, gridBagConstraints);

        scaleYTF.setColumns(4);
        scaleYTF.setToolTipText("Non-uniform x-y-z scale of child coordinate system, adjusted by center and scaleOrientation");
        scaleYTF.setMaximumSize(null);
        scaleYTF.setMinimumSize(new java.awt.Dimension(50, 18));
        scaleYTF.setPreferredSize(new java.awt.Dimension(50, 18));
        scaleYTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scaleYTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleYTF, gridBagConstraints);

        scaleZTF.setColumns(4);
        scaleZTF.setToolTipText("Non-uniform x-y-z scale of child coordinate system, adjusted by center and scaleOrientation");
        scaleZTF.setMaximumSize(null);
        scaleZTF.setMinimumSize(new java.awt.Dimension(50, 18));
        scaleZTF.setPreferredSize(new java.awt.Dimension(50, 18));
        scaleZTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scaleZTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleZTF, gridBagConstraints);

        scaleOrientationLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        scaleOrientationLabel.setText("scaleOrientation");
        scaleOrientationLabel.setToolTipText("Preliminary rotation of coordinate system before scaling (to allow scaling around arbitrary orientations)");
        scaleOrientationLabel.setMaximumSize(null);
        scaleOrientationLabel.setMinimumSize(new java.awt.Dimension(80, 18));
        scaleOrientationLabel.setPreferredSize(new java.awt.Dimension(80, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.ipadx = 48;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleOrientationLabel, gridBagConstraints);

        scaleOrientationXaxisTF.setColumns(4);
        scaleOrientationXaxisTF.setToolTipText("Preliminary rotation of coordinate system before scaling (to allow scaling around arbitrary orientations)");
        scaleOrientationXaxisTF.setMaximumSize(null);
        scaleOrientationXaxisTF.setMinimumSize(new java.awt.Dimension(50, 18));
        scaleOrientationXaxisTF.setPreferredSize(new java.awt.Dimension(50, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleOrientationXaxisTF, gridBagConstraints);

        scaleOrientationYaxisTF.setColumns(4);
        scaleOrientationYaxisTF.setToolTipText("Preliminary rotation of coordinate system before scaling (to allow scaling around arbitrary orientations)");
        scaleOrientationYaxisTF.setMaximumSize(null);
        scaleOrientationYaxisTF.setMinimumSize(new java.awt.Dimension(50, 18));
        scaleOrientationYaxisTF.setPreferredSize(new java.awt.Dimension(50, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleOrientationYaxisTF, gridBagConstraints);

        scaleOrientationZaxisTF.setColumns(4);
        scaleOrientationZaxisTF.setToolTipText("Preliminary rotation of coordinate system before scaling (to allow scaling around arbitrary orientations)");
        scaleOrientationZaxisTF.setMaximumSize(null);
        scaleOrientationZaxisTF.setMinimumSize(new java.awt.Dimension(50, 18));
        scaleOrientationZaxisTF.setPreferredSize(new java.awt.Dimension(50, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleOrientationZaxisTF, gridBagConstraints);

        scaleOrientationAngleTF.setColumns(4);
        scaleOrientationAngleTF.setToolTipText("Preliminary rotation of coordinate system before scaling (to allow scaling around arbitrary orientations)");
        scaleOrientationAngleTF.setMaximumSize(null);
        scaleOrientationAngleTF.setMinimumSize(new java.awt.Dimension(50, 18));
        scaleOrientationAngleTF.setPreferredSize(new java.awt.Dimension(50, 18));
        scaleOrientationAngleTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scaleOrientationAngleTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
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
        gridBagConstraints.gridy = 17;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleOrientationCalculatorlButton, gridBagConstraints);

        boxbCenterLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        boxbCenterLabel.setText("bboxCenter");
        boxbCenterLabel.setToolTipText("position offset from origin of local coordinate system for collision bounding box");
        boxbCenterLabel.setMaximumSize(null);
        boxbCenterLabel.setMinimumSize(new java.awt.Dimension(80, 18));
        boxbCenterLabel.setOpaque(true);
        boxbCenterLabel.setPreferredSize(new java.awt.Dimension(80, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.ipadx = 48;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(boxbCenterLabel, gridBagConstraints);

        bboxCenterXTF.setColumns(4);
        bboxCenterXTF.setToolTipText("position offset from origin of local coordinate system for collision bounding box");
        bboxCenterXTF.setMaximumSize(null);
        bboxCenterXTF.setMinimumSize(new java.awt.Dimension(50, 18));
        bboxCenterXTF.setPreferredSize(new java.awt.Dimension(50, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterXTF, gridBagConstraints);

        bboxCenterYTF.setColumns(4);
        bboxCenterYTF.setToolTipText("position offset from origin of local coordinate system for collision bounding box");
        bboxCenterYTF.setMaximumSize(null);
        bboxCenterYTF.setMinimumSize(new java.awt.Dimension(50, 18));
        bboxCenterYTF.setPreferredSize(new java.awt.Dimension(50, 18));
        bboxCenterYTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bboxCenterYTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterYTF, gridBagConstraints);

        bboxCenterZTF.setColumns(4);
        bboxCenterZTF.setToolTipText("position offset from origin of local coordinate system for collision bounding box");
        bboxCenterZTF.setMaximumSize(null);
        bboxCenterZTF.setMinimumSize(new java.awt.Dimension(50, 18));
        bboxCenterZTF.setPreferredSize(new java.awt.Dimension(50, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterZTF, gridBagConstraints);

        bboxSizeLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        bboxSizeLabel.setText("bboxSize");
        bboxSizeLabel.setToolTipText("bounding box is automatically calculated, can also be specified as an optimization or constraint");
        bboxSizeLabel.setMaximumSize(null);
        bboxSizeLabel.setMinimumSize(new java.awt.Dimension(80, 18));
        bboxSizeLabel.setPreferredSize(new java.awt.Dimension(80, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.ipadx = 48;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxSizeLabel, gridBagConstraints);

        bboxSizeXTF.setColumns(4);
        bboxSizeXTF.setToolTipText("bounding box is automatically calculated, can also be specified as an optimization or constraint");
        bboxSizeXTF.setMaximumSize(null);
        bboxSizeXTF.setMinimumSize(new java.awt.Dimension(50, 18));
        bboxSizeXTF.setPreferredSize(new java.awt.Dimension(50, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxSizeXTF, gridBagConstraints);

        bboxSizeYTF.setColumns(4);
        bboxSizeYTF.setToolTipText("bounding box is automatically calculated, can also be specified as an optimization or constraint");
        bboxSizeYTF.setMaximumSize(null);
        bboxSizeYTF.setMinimumSize(new java.awt.Dimension(50, 18));
        bboxSizeYTF.setPreferredSize(new java.awt.Dimension(50, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxSizeYTF, gridBagConstraints);

        bboxSizeZTF.setColumns(4);
        bboxSizeZTF.setToolTipText("bounding box is automatically calculated, can also be specified as an optimization or constraint");
        bboxSizeZTF.setMaximumSize(null);
        bboxSizeZTF.setMinimumSize(new java.awt.Dimension(50, 18));
        bboxSizeZTF.setPreferredSize(new java.awt.Dimension(50, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxSizeZTF, gridBagConstraints);

        normalizeRotationValuesButton.setText("normalize rotation and scaleOrientation values");
        normalizeRotationValuesButton.setToolTipText("for rotation and scaleOrientation fields, rescale axis values as normalized vector (unit length, ranges 0..1), reset angles [0..2pi)");
        normalizeRotationValuesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                normalizeRotationValuesButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(normalizeRotationValuesButton, gridBagConstraints);

        nodeHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nodeHintPanel.setLayout(new java.awt.GridBagLayout());

        hintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel.setText("<html> <p align=\"center\"><b>HAnimJoint</b> is used to represent each joint in the body.</p>\n<p align=\"center\">Parent must be another <b>HAnimJoint</b> or else the HAnimHumanoid node (with containerfield='skeleton' or 'joints').</p>  \n<p align=\"center\"><b>HAnimJoint</b> can contain HAnimSegment, HAnimSite or HAnimJoint as children nodes, plus HanimDisplacer displacers.</p>");
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
        gridBagConstraints.gridy = 21;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nodeHintPanel, gridBagConstraints);

        translationModificationComboBox.setModel(new DefaultComboBoxModel<String>(TRANSFORM_ATTR_TRANSLATION_CHOICES));
        translationModificationComboBox.setToolTipText("Scale translation values to meters");
        translationModificationComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                translationModificationComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(translationModificationComboBox, gridBagConstraints);

        centerModificationComboBox.setModel(new DefaultComboBoxModel<String>(TRANSFORM_ATTR_TRANSLATION_CHOICES));
        centerModificationComboBox.setToolTipText("Scale center values to meters");
        centerModificationComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                centerModificationComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(centerModificationComboBox, gridBagConstraints);

        scaleSelectionComboBox.setModel(new DefaultComboBoxModel<String>(TRANSFORM_ATTR_SCALE_CHOICES));
        scaleSelectionComboBox.setToolTipText("Scale child node dimensions to meters");
        scaleSelectionComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scaleSelectionComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(scaleSelectionComboBox, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void rotationAngleTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rotationAngleTFActionPerformed
        checkAngles(false);
}//GEN-LAST:event_rotationAngleTFActionPerformed

    private void scaleOrientationAngleTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scaleOrientationAngleTFActionPerformed
        checkAngles(false);
}//GEN-LAST:event_scaleOrientationAngleTFActionPerformed

    private void bboxCenterYTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bboxCenterYTFActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_bboxCenterYTFActionPerformed

    private void normalizeRotationValuesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_normalizeRotationValuesButtonActionPerformed
        checkAngles(true);
        double normalizationFactor, x, y, z, angle;

        x = new SFDouble(rotationXaxisTF.getText()).getValue();
        y = new SFDouble(rotationYaxisTF.getText()).getValue();
        z = new SFDouble(rotationZaxisTF.getText()).getValue();
        angle = new SFDouble(rotationAngleTF.getText()).getValue();
        normalizationFactor = Math.sqrt(x * x + y * y + z * z);
        if (normalizationFactor == 0.0) {
            NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                    "Found zero-magnitude axis for rotation, reset to 0 1 0", NotifyDescriptor.WARNING_MESSAGE);
            DialogDisplayer.getDefault().notify(descriptor);
            rotationXaxisTF.setText("0");
            rotationYaxisTF.setText("1");
            rotationZaxisTF.setText("0");
        } else {
            rotationXaxisTF.setText(fiveDigitFormat.format(x / normalizationFactor));
            rotationYaxisTF.setText(fiveDigitFormat.format(y / normalizationFactor));
            rotationZaxisTF.setText(fiveDigitFormat.format(z / normalizationFactor));
        }
        if (angle == -0.0) {
            angle = 0.0;
        }
        while (angle < 0.0) {
            angle += 2.0 * Math.PI;
        }
        while (angle > 2.0 * Math.PI) {
            angle -= 2.0 * Math.PI;
        }
        rotationAngleTF.setText(radiansFormat.format(angle));
        rotationAngleTF.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");

        x = new SFDouble(scaleOrientationXaxisTF.getText()).getValue();
        y = new SFDouble(scaleOrientationYaxisTF.getText()).getValue();
        z = new SFDouble(scaleOrientationZaxisTF.getText()).getValue();
        angle = new SFDouble(scaleOrientationAngleTF.getText()).getValue();
        normalizationFactor = Math.sqrt(x * x + y * y + z * z);
        if (normalizationFactor == 0.0) {
            NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                    "Found zero-magnitude axis for scaleOrientation, reset to 0 1 0", NotifyDescriptor.WARNING_MESSAGE);
            DialogDisplayer.getDefault().notify(descriptor);
            scaleOrientationXaxisTF.setText("0");
            scaleOrientationYaxisTF.setText("1");
            scaleOrientationZaxisTF.setText("0");
        } else {
            scaleOrientationXaxisTF.setText(fiveDigitFormat.format(x / normalizationFactor));
            scaleOrientationYaxisTF.setText(fiveDigitFormat.format(y / normalizationFactor));
            scaleOrientationZaxisTF.setText(fiveDigitFormat.format(z / normalizationFactor));
        }
        if (angle == -0.0) {
            angle = 0.0;
        }
        while (angle < 0.0) {
            angle += 2.0 * Math.PI;
        }
        while (angle > 2.0 * Math.PI) {
            angle -= 2.0 * Math.PI;
        }
        scaleOrientationAngleTF.setText(radiansFormat.format(angle));
        scaleOrientationAngleTF.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");
}//GEN-LAST:event_normalizeRotationValuesButtonActionPerformed

    private void nameComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_nameComboBoxActionPerformed
    {//GEN-HEADEREND:event_nameComboBoxActionPerformed
        setDefaultDEFname ();
    }//GEN-LAST:event_nameComboBoxActionPerformed

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

    private void scaleXTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scaleXTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_scaleXTFActionPerformed

    private void scaleYTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scaleYTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_scaleYTFActionPerformed

    private void scaleZTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scaleZTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_scaleZTFActionPerformed

    private void rotationCalculatorlButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rotationCalculatorlButtonActionPerformed
        RotationCalculatorPanel rotationCalculatorPanel = new RotationCalculatorPanel(hAnimJoint, "rotation");
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
        RotationCalculatorPanel scaleOrientationCalculatorPanel = new RotationCalculatorPanel(hAnimJoint, "scaleOrientation");
        scaleOrientationCalculatorPanel.setRotationValue (
            scaleOrientationXaxisTF.getText(),
            scaleOrientationYaxisTF.getText(),
            scaleOrientationZaxisTF.getText(),
            scaleOrientationAngleTF.getText());
        DialogDescriptor dd = new DialogDescriptor(scaleOrientationCalculatorPanel, "Rotation Calculator for HAnimJoint scaleOrientation");
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

    private void limitOrientationXaxisTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limitOrientationXaxisTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_limitOrientationXaxisTFActionPerformed

    private void limitOrientationYaxisTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limitOrientationYaxisTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_limitOrientationYaxisTFActionPerformed

    private void limitOrientationZaxisTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limitOrientationZaxisTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_limitOrientationZaxisTFActionPerformed

    private void limitOrientationAngleTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_limitOrientationAngleTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_limitOrientationAngleTFActionPerformed

    private void lilmitOrientationCalculatorlButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lilmitOrientationCalculatorlButtonActionPerformed
       RotationCalculatorPanel limitOrientationCalculatorPanel = new RotationCalculatorPanel(hAnimJoint, "limitOrientation");
        limitOrientationCalculatorPanel.setRotationValue (
            limitOrientationXaxisTF.getText(),
            limitOrientationYaxisTF.getText(),
            limitOrientationZaxisTF.getText(),
            limitOrientationAngleTF.getText());
        DialogDescriptor dd = new DialogDescriptor(limitOrientationCalculatorPanel, "Rotation Calculator for HAnimJoint limitOrientation");
        Dialog dialog = DialogDisplayer.getDefault().createDialog(dd);
        dialog.setVisible(true);
        if (dd.getValue() != DialogDescriptor.CANCEL_OPTION)
        {
            // save values
            if (!limitOrientationCalculatorPanel.getRotationResult().isEmpty())
            {
                limitOrientationXaxisTF.setText(limitOrientationCalculatorPanel.getRotationResultX());
                limitOrientationYaxisTF.setText(limitOrientationCalculatorPanel.getRotationResultY());
                limitOrientationZaxisTF.setText(limitOrientationCalculatorPanel.getRotationResultZ());
                limitOrientationAngleTF.setText(limitOrientationCalculatorPanel.getRotationResultAngle());
                limitOrientationAngleTF.setToolTipText(limitOrientationCalculatorPanel.getRotationResultAngle() + " radians = " +
                    singleDigitFormat.format(Float.parseFloat(limitOrientationCalculatorPanel.getRotationResultAngle()) * 180.0 / Math.PI) + " degrees");
            }
        }
    }//GEN-LAST:event_lilmitOrientationCalculatorlButtonActionPerformed

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_HANIMJOINT";
  }

  private void checkAngles(boolean precedesNormalization)
  {
      // indicate degree values in tooltips
      // usability note:  can enter degree values (-6..+6) as (354..366) to provoke this conversion check
      double angle = new SFDouble(rotationAngleTF.getText()).getValue();
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
      angle = new SFDouble(scaleOrientationAngleTF.getText()).getValue();
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
  public void unloadInput() throws IllegalArgumentException
  {
    checkAngles (false);
    unLoadDEFUSE();

    hAnimJoint.setName            (nameComboBox.getSelectedItem().toString());
    hAnimJoint.setSkinCoordIndex  (skinCoordIndexTF.getText().trim());
    hAnimJoint.setLLimit          (llimitTF.getText().trim());
    hAnimJoint.setSkinCoordWeight (skinCoordWeightTF.getText().trim());
    hAnimJoint.setStiffnesss      (stiffnessTF.getText().trim());
    hAnimJoint.setLimitOrientationX    (limitOrientationXaxisTF.getText().trim());
    hAnimJoint.setLimitOrientationY    (limitOrientationYaxisTF.getText().trim());
    hAnimJoint.setLimitOrientationZ    (limitOrientationZaxisTF.getText().trim());
    hAnimJoint.setLimitOrientationAngle(limitOrientationAngleTF.getText().trim());

    hAnimJoint.setBboxCenterX(bboxCenterXTF.getText().trim());
    hAnimJoint.setBboxCenterY(bboxCenterYTF.getText().trim());
    hAnimJoint.setBboxCenterZ(bboxCenterZTF.getText().trim());
    hAnimJoint.setBboxSizeX(bboxSizeXTF.getText().trim());
    hAnimJoint.setBboxSizeY(bboxSizeYTF.getText().trim());
    hAnimJoint.setBboxSizeZ(bboxSizeZTF.getText().trim());
    hAnimJoint.setCenterX(centerXTF.getText().trim());
    hAnimJoint.setCenterY(centerYTF.getText().trim());
    hAnimJoint.setCenterZ(centerZTF.getText().trim());
    hAnimJoint.setRotationX(rotationXaxisTF.getText().trim());
    hAnimJoint.setRotationY(rotationYaxisTF.getText().trim());
    hAnimJoint.setRotationZ(rotationZaxisTF.getText().trim());
    hAnimJoint.setRotationAngle(rotationAngleTF.getText().trim());
    hAnimJoint.setScaleOrientationX(scaleOrientationXaxisTF.getText().trim());
    hAnimJoint.setScaleOrientationY(scaleOrientationYaxisTF.getText().trim());
    hAnimJoint.setScaleOrientationZ(scaleOrientationZaxisTF.getText().trim());
    hAnimJoint.setScaleOrientationAngle(scaleOrientationAngleTF.getText().trim());
    hAnimJoint.setScaleX(scaleXTF.getText().trim());
    hAnimJoint.setScaleY(scaleYTF.getText().trim());
    hAnimJoint.setScaleZ(scaleZTF.getText().trim());
    hAnimJoint.setTranslationX(translationXTF.getText().trim());
    hAnimJoint.setTranslationY(translationYTF.getText().trim());
    hAnimJoint.setTranslationZ(translationZTF.getText().trim());
  }  
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel adjustmentsLabel;
    private javax.swing.JLabel angleLabel;
    private javax.swing.JTextField bboxCenterXTF;
    private javax.swing.JTextField bboxCenterYTF;
    private javax.swing.JTextField bboxCenterZTF;
    private javax.swing.JLabel bboxSizeLabel;
    private javax.swing.JTextField bboxSizeXTF;
    private javax.swing.JTextField bboxSizeYTF;
    private javax.swing.JTextField bboxSizeZTF;
    private javax.swing.JLabel boxbCenterLabel;
    private javax.swing.JLabel centerLabel;
    private javax.swing.JComboBox<String> centerModificationComboBox;
    private javax.swing.JTextField centerXTF;
    private javax.swing.JTextField centerYTF;
    private javax.swing.JTextField centerZTF;
    private org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel;
    private javax.swing.JLabel hintLabel;
    private javax.swing.JButton lilmitOrientationCalculatorlButton;
    private javax.swing.JTextField limitOrientationAngleTF;
    private javax.swing.JLabel limitOrientationLabel;
    private javax.swing.JTextField limitOrientationXaxisTF;
    private javax.swing.JTextField limitOrientationYaxisTF;
    private javax.swing.JTextField limitOrientationZaxisTF;
    private javax.swing.JLabel llimitLabel;
    private javax.swing.JTextField llimitTF;
    private javax.swing.JComboBox<String> nameComboBox;
    private javax.swing.JLabel nameLabel;
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
    private javax.swing.JLabel skinCoordIndexLabel;
    private javax.swing.JTextField skinCoordIndexTF;
    private javax.swing.JLabel skinCoordWeightLabel;
    private javax.swing.JTextField skinCoordWeightTF;
    private javax.swing.JLabel stiffnessLabel;
    private javax.swing.JTextField stiffnessTF;
    private javax.swing.JLabel translationLabel;
    private javax.swing.JComboBox<String> translationModificationComboBox;
    private javax.swing.JTextField translationXTF;
    private javax.swing.JTextField translationYTF;
    private javax.swing.JTextField translationZTF;
    private javax.swing.JLabel xLabel;
    private javax.swing.JLabel yLabel;
    private javax.swing.JLabel zLabel;
    // End of variables declaration//GEN-END:variables
  
}
