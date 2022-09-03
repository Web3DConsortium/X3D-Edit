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
import javax.swing.text.JTextComponent;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.util.HelpCtx;
import static org.web3d.x3d.types.X3DPrimitiveTypes.singleDigitFormat;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * RIGIDBODYCustomizer.java
 * Created on 2 January 2009
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class RIGIDBODYCustomizer extends BaseCustomizer
{
  private final RIGIDBODY rigidBody;
  private final JTextComponent target;
  
  /** Creates new form RIGIDBODYCustomizer */
  public RIGIDBODYCustomizer(RIGIDBODY rigidBody, JTextComponent target)
  {
    super(rigidBody);
    this.rigidBody = rigidBody;
    this.target = target;
                             
    HelpCtx.setHelpIDString(this, "RIGIDBODY_ELEM_HELPID");   
    
    initComponents();
    
    super.getDEFUSEpanel().setContainerFieldChoices(
            RIGIDBODY_ATTR_CONTAINERFIELD_CHOICES,
            RIGIDBODY_ATTR_CONTAINERFIELD_TOOLTIPS);
    // DEFUSEpanel initialization must NOT be repeated or else array of choices will be overwritten

    autoDampCB.setSelected(rigidBody.isAutoDamp());
    autoDisableCB.setSelected(rigidBody.isAutoDisable());
    enabledCB.setSelected(rigidBody.isEnabled());
    fixedCB.setSelected(rigidBody.isFixed());
    useFiniteRotationCB.setSelected(rigidBody.isUseFiniteRotation());
    useGlobalGravityCB.setSelected(rigidBody.isUseGlobalGravity());
    
    angularDampingFactorTF.setText(rigidBody.getAngularDampingFactor());
    disableAngularSpeedTF.setText(rigidBody.getDisableAngularSpeed());
    disableLinearSpeedTF.setText(rigidBody.getDisableLinearSpeed());
    disableTimeTF.setText(rigidBody.getDisableTime());
    
    angularVelocity0TF.setText(rigidBody.getAngularVelocity0());
    angularVelocity1TF.setText(rigidBody.getAngularVelocity1());
    angularVelocity2TF.setText(rigidBody.getAngularVelocity2());
    
    centerOfMass0TF.setText(rigidBody.getCenterOfMass0());
    centerOfMass1TF.setText(rigidBody.getCenterOfMass1());
    centerOfMass2TF.setText(rigidBody.getCenterOfMass2());
    
    finiteRotationAxis0TF.setText(rigidBody.getFiniteRotationAxis0());
    finiteRotationAxis1TF.setText(rigidBody.getFiniteRotationAxis1());
    finiteRotationAxis2TF.setText(rigidBody.getFiniteRotationAxis2());
    
    forcesTF.setText(rigidBody.getForces());
    
    inertia00TF.setText(rigidBody.getInertia00());
    inertia01TF.setText(rigidBody.getInertia01());
    inertia02TF.setText(rigidBody.getInertia02());
    inertia10TF.setText(rigidBody.getInertia10());
    inertia11TF.setText(rigidBody.getInertia11());
    inertia12TF.setText(rigidBody.getInertia12());
    inertia20TF.setText(rigidBody.getInertia20());
    inertia21TF.setText(rigidBody.getInertia21());
    inertia22TF.setText(rigidBody.getInertia22());
    
    linearDampingFactorTF.setText(rigidBody.getLinearDampingFactor());    
    linearVelocity0TF.setText(rigidBody.getLinearVelocity0());
    linearVelocity1TF.setText(rigidBody.getLinearVelocity1());
    linearVelocity2TF.setText(rigidBody.getLinearVelocity2());
    
    massTF.setText(rigidBody.getMass());
    orientationXaxisTF.setText(rigidBody.getOrientation0());
    orientationYaxisTF.setText(rigidBody.getOrientation1());
    orientationZaxisTF.setText(rigidBody.getOrientation2());
    orientationAngleTF.setText(rigidBody.getOrientation3());
    position0TF.setText(rigidBody.getPosition0());
    position1TF.setText(rigidBody.getPosition1());
    position2TF.setText(rigidBody.getPosition2());
    torquesTF.setText(rigidBody.getTorques());
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
        xLabel = new javax.swing.JLabel();
        yLabel = new javax.swing.JLabel();
        zLabel = new javax.swing.JLabel();
        angleLabel = new javax.swing.JLabel();
        adjustmentsLabel = new javax.swing.JLabel();
        angularVelocityLabel = new javax.swing.JLabel();
        angularDampingFactorLabel = new javax.swing.JLabel();
        angularDampingFactorTF = new javax.swing.JTextField();
        angularVelocity0TF = new javax.swing.JTextField();
        angularVelocity1TF = new javax.swing.JTextField();
        angularVelocity2TF = new javax.swing.JTextField();
        centerOfMassLabel = new javax.swing.JLabel();
        centerOfMass0TF = new javax.swing.JTextField();
        centerOfMass1TF = new javax.swing.JTextField();
        centerOfMass2TF = new javax.swing.JTextField();
        forcesLabel = new javax.swing.JLabel();
        forcesTF = new javax.swing.JTextField();
        torquesTF = new javax.swing.JTextField();
        disableLinearSpeedLabel = new javax.swing.JLabel();
        disableLinearSpeedTF = new javax.swing.JTextField();
        inertia22TF = new javax.swing.JTextField();
        positionLabel = new javax.swing.JLabel();
        position0TF = new javax.swing.JTextField();
        position1TF = new javax.swing.JTextField();
        enabledLabel = new javax.swing.JLabel();
        enabledCB = new javax.swing.JCheckBox();
        linearDampingFactorLabel = new javax.swing.JLabel();
        linearDampingFactorTF = new javax.swing.JTextField();
        disableTimeLabel = new javax.swing.JLabel();
        mass = new javax.swing.JLabel();
        disableTimeTF = new javax.swing.JTextField();
        massTF = new javax.swing.JTextField();
        disableAngularSpeedLabel = new javax.swing.JLabel();
        disableAngularSpeedTF = new javax.swing.JTextField();
        inertiaLabel = new javax.swing.JLabel();
        finiteRotationAxisLabel = new javax.swing.JLabel();
        finiteRotationAxis0TF = new javax.swing.JTextField();
        finiteRotationAxis1TF = new javax.swing.JTextField();
        finiteRotationAxis2TF = new javax.swing.JTextField();
        inertia00TF = new javax.swing.JTextField();
        inertia20TF = new javax.swing.JTextField();
        inertia21TF = new javax.swing.JTextField();
        autoDampCB = new javax.swing.JCheckBox();
        autoDampLabel = new javax.swing.JLabel();
        autoDisableLabel = new javax.swing.JLabel();
        autoDisableCB = new javax.swing.JCheckBox();
        useGlobalGravityLabel = new javax.swing.JLabel();
        useGlobalGravityCB = new javax.swing.JCheckBox();
        useFiniteRotationCB = new javax.swing.JCheckBox();
        useFiniteRotationLabel = new javax.swing.JLabel();
        fixedCB = new javax.swing.JCheckBox();
        fixedLabel = new javax.swing.JLabel();
        inertia12TF = new javax.swing.JTextField();
        inertia11TF = new javax.swing.JTextField();
        inertia10TF = new javax.swing.JTextField();
        inertia02TF = new javax.swing.JTextField();
        inertia01TF = new javax.swing.JTextField();
        torquesLabel = new javax.swing.JLabel();
        position2TF = new javax.swing.JTextField();
        linearVelocityLabel = new javax.swing.JLabel();
        linearVelocity0TF = new javax.swing.JTextField();
        linearVelocity1TF = new javax.swing.JTextField();
        linearVelocity2TF = new javax.swing.JTextField();
        orientationLabel = new javax.swing.JLabel();
        orientationXaxisTF = new javax.swing.JTextField();
        orientationYaxisTF = new javax.swing.JTextField();
        orientationZaxisTF = new javax.swing.JTextField();
        orientationAngleTF = new javax.swing.JTextField();
        orientationCalculatorlButton = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 8, 3, 8);
        add(dEFUSEpanel1, gridBagConstraints);

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

        adjustmentsLabel.setText("adjustments");
        adjustmentsLabel.setToolTipText("apply adjustment factor to original value");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(8, 12, 3, 3);
        add(adjustmentsLabel, gridBagConstraints);

        angularVelocityLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        angularVelocityLabel.setText("angularVelocity");
        angularVelocityLabel.setToolTipText("Global paramaters for collision system");
        angularVelocityLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 8, 3, 3);
        add(angularVelocityLabel, gridBagConstraints);

        angularDampingFactorLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        angularDampingFactorLabel.setText("angularDampingFactor");
        angularDampingFactorLabel.setToolTipText("bounce indicates bounciness (0 = no bounce at all, 1 = maximum bounce)");
        angularDampingFactorLabel.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 8, 3, 3);
        add(angularDampingFactorLabel, gridBagConstraints);

        angularDampingFactorTF.setColumns(4);
        angularDampingFactorTF.setToolTipText("bounce indicates bounciness (0 = no bounce at all, 1 = maximum bounce)");
        angularDampingFactorTF.setMinimumSize(new java.awt.Dimension(40, 20));
        angularDampingFactorTF.setPreferredSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(angularDampingFactorTF, gridBagConstraints);

        angularVelocity0TF.setColumns(4);
        angularVelocity0TF.setToolTipText("unit vector describing normal between two colliding bodies");
        angularVelocity0TF.setMinimumSize(new java.awt.Dimension(40, 20));
        angularVelocity0TF.setPreferredSize(new java.awt.Dimension(40, 20));
        angularVelocity0TF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                angularVelocity0TFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(angularVelocity0TF, gridBagConstraints);

        angularVelocity1TF.setColumns(4);
        angularVelocity1TF.setToolTipText("unit vector describing normal between two colliding bodies");
        angularVelocity1TF.setMinimumSize(new java.awt.Dimension(40, 20));
        angularVelocity1TF.setPreferredSize(new java.awt.Dimension(40, 20));
        angularVelocity1TF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                angularVelocity1TFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(angularVelocity1TF, gridBagConstraints);

        angularVelocity2TF.setColumns(4);
        angularVelocity2TF.setToolTipText("unit vector describing normal between two colliding bodies");
        angularVelocity2TF.setMinimumSize(new java.awt.Dimension(40, 20));
        angularVelocity2TF.setPreferredSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(angularVelocity2TF, gridBagConstraints);

        centerOfMassLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        centerOfMassLabel.setText("centerOfMass");
        centerOfMassLabel.setToolTipText("defines local center of mass for physics calculations");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 8, 3, 3);
        add(centerOfMassLabel, gridBagConstraints);

        centerOfMass0TF.setColumns(4);
        centerOfMass0TF.setToolTipText("defines local center of mass for physics calculations");
        centerOfMass0TF.setMinimumSize(new java.awt.Dimension(40, 20));
        centerOfMass0TF.setPreferredSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(centerOfMass0TF, gridBagConstraints);

        centerOfMass1TF.setColumns(4);
        centerOfMass1TF.setToolTipText("defines local center of mass for physics calculations");
        centerOfMass1TF.setMinimumSize(new java.awt.Dimension(40, 20));
        centerOfMass1TF.setPreferredSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(centerOfMass1TF, gridBagConstraints);

        centerOfMass2TF.setColumns(4);
        centerOfMass2TF.setToolTipText("defines local center of mass for physics calculations");
        centerOfMass2TF.setMinimumSize(new java.awt.Dimension(40, 20));
        centerOfMass2TF.setPreferredSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(centerOfMass2TF, gridBagConstraints);

        forcesLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        forcesLabel.setText("forces");
        forcesLabel.setToolTipText("defines linear force values applied to the object every frame");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 8, 3, 3);
        add(forcesLabel, gridBagConstraints);

        forcesTF.setColumns(4);
        forcesTF.setToolTipText("defines linear force values applied to the object every frame");
        forcesTF.setMinimumSize(new java.awt.Dimension(40, 20));
        forcesTF.setPreferredSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(forcesTF, gridBagConstraints);

        torquesTF.setColumns(4);
        torquesTF.setToolTipText("defines rotational force values applied to the object every frame");
        torquesTF.setMinimumSize(new java.awt.Dimension(40, 20));
        torquesTF.setPreferredSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 25;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(torquesTF, gridBagConstraints);

        disableLinearSpeedLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        disableLinearSpeedLabel.setText("disableLinearSpeed");
        disableLinearSpeedLabel.setToolTipText("defines lower-limit tolerance value when body is considered at rest and not part of rigid body calculations, reducing numeric instabilities");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 8, 3, 3);
        add(disableLinearSpeedLabel, gridBagConstraints);

        disableLinearSpeedTF.setColumns(4);
        disableLinearSpeedTF.setToolTipText("defines lower-limit tolerance value when body is considered at rest and not part of rigid body calculations, reducing numeric instabilities");
        disableLinearSpeedTF.setMinimumSize(new java.awt.Dimension(40, 20));
        disableLinearSpeedTF.setPreferredSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(disableLinearSpeedTF, gridBagConstraints);

        inertia22TF.setColumns(4);
        inertia22TF.setToolTipText("inertia matrix defines a 3x2 inertia tensor matrix");
        inertia22TF.setMinimumSize(new java.awt.Dimension(40, 20));
        inertia22TF.setPreferredSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(inertia22TF, gridBagConstraints);

        positionLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        positionLabel.setText("position");
        positionLabel.setToolTipText("sets body location in world space, then reports physics updates");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 8, 3, 3);
        add(positionLabel, gridBagConstraints);

        position0TF.setColumns(4);
        position0TF.setToolTipText("sets body location in world space, then reports physics updates");
        position0TF.setMinimumSize(new java.awt.Dimension(40, 20));
        position0TF.setPreferredSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(position0TF, gridBagConstraints);

        position1TF.setColumns(4);
        position1TF.setToolTipText("sets body location in world space, then reports physics updates");
        position1TF.setMinimumSize(new java.awt.Dimension(40, 20));
        position1TF.setPreferredSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(position1TF, gridBagConstraints);

        enabledLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        enabledLabel.setText("enabled");
        enabledLabel.setToolTipText("enable/disable node operation");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 8, 3, 3);
        add(enabledLabel, gridBagConstraints);

        enabledCB.setToolTipText("enable/disable node operation");
        enabledCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 0);
        add(enabledCB, gridBagConstraints);

        linearDampingFactorLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        linearDampingFactorLabel.setText("linearDampingFactor");
        linearDampingFactorLabel.setToolTipText("automatically damps a portion of body motion over time");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 8, 3, 3);
        add(linearDampingFactorLabel, gridBagConstraints);

        linearDampingFactorTF.setToolTipText("automatically damps a portion of body motion over time");
        linearDampingFactorTF.setMinimumSize(new java.awt.Dimension(40, 20));
        linearDampingFactorTF.setPreferredSize(new java.awt.Dimension(40, 20));
        linearDampingFactorTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                linearDampingFactorTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(linearDampingFactorTF, gridBagConstraints);

        disableTimeLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        disableTimeLabel.setText("disableTime");
        disableTimeLabel.setToolTipText("defines interval when body becomes at rest and not part of rigid body calculations, reducing numeric instabilities");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 8, 3, 3);
        add(disableTimeLabel, gridBagConstraints);

        mass.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        mass.setText("mass");
        mass.setToolTipText("mass of the body in kilograms (warning: mass must be greater than 0)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 21;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 8, 3, 3);
        add(mass, gridBagConstraints);

        disableTimeTF.setToolTipText("defines interval when body becomes at rest and not part of rigid body calculations, reducing numeric instabilities");
        disableTimeTF.setMinimumSize(new java.awt.Dimension(40, 20));
        disableTimeTF.setPreferredSize(new java.awt.Dimension(40, 20));
        disableTimeTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disableTimeTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(disableTimeTF, gridBagConstraints);

        massTF.setToolTipText("mass of the body in kilograms (warning: mass must be greater than 0)");
        massTF.setMinimumSize(new java.awt.Dimension(40, 20));
        massTF.setPreferredSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 21;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(massTF, gridBagConstraints);

        disableAngularSpeedLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        disableAngularSpeedLabel.setText("disableAngularSpeed");
        disableAngularSpeedLabel.setToolTipText("defines lower-limit tolerance value when body is considered at rest and not part of rigid body calculations, reducing numeric instabilities");
        disableAngularSpeedLabel.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 8, 3, 3);
        add(disableAngularSpeedLabel, gridBagConstraints);

        disableAngularSpeedTF.setColumns(4);
        disableAngularSpeedTF.setToolTipText("defines lower-limit tolerance value when body is considered at rest and not part of rigid body calculations, reducing numeric instabilities");
        disableAngularSpeedTF.setMinimumSize(new java.awt.Dimension(40, 20));
        disableAngularSpeedTF.setPreferredSize(new java.awt.Dimension(40, 20));
        disableAngularSpeedTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disableAngularSpeedTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(disableAngularSpeedTF, gridBagConstraints);

        inertiaLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        inertiaLabel.setText("inertia");
        inertiaLabel.setToolTipText("inertia matrix defines a 3x2 inertia tensor matrix");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 8, 3, 3);
        add(inertiaLabel, gridBagConstraints);

        finiteRotationAxisLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        finiteRotationAxisLabel.setText("finiteRotationAxis");
        finiteRotationAxisLabel.setToolTipText("specifies vector around which the object rotates");
        finiteRotationAxisLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 8, 3, 3);
        add(finiteRotationAxisLabel, gridBagConstraints);

        finiteRotationAxis0TF.setColumns(4);
        finiteRotationAxis0TF.setToolTipText("specifies vector around which the object rotates");
        finiteRotationAxis0TF.setMinimumSize(new java.awt.Dimension(40, 20));
        finiteRotationAxis0TF.setPreferredSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(finiteRotationAxis0TF, gridBagConstraints);

        finiteRotationAxis1TF.setColumns(4);
        finiteRotationAxis1TF.setToolTipText("specifies vector around which the object rotates");
        finiteRotationAxis1TF.setMinimumSize(new java.awt.Dimension(40, 20));
        finiteRotationAxis1TF.setPreferredSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(finiteRotationAxis1TF, gridBagConstraints);

        finiteRotationAxis2TF.setColumns(4);
        finiteRotationAxis2TF.setToolTipText("specifies vector around which the object rotates");
        finiteRotationAxis2TF.setMinimumSize(new java.awt.Dimension(40, 20));
        finiteRotationAxis2TF.setPreferredSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(finiteRotationAxis2TF, gridBagConstraints);

        inertia00TF.setColumns(4);
        inertia00TF.setToolTipText("inertia matrix defines a 3x2 inertia tensor matrix");
        inertia00TF.setMinimumSize(new java.awt.Dimension(40, 20));
        inertia00TF.setPreferredSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(inertia00TF, gridBagConstraints);

        inertia20TF.setColumns(4);
        inertia20TF.setToolTipText("inertia matrix defines a 3x2 inertia tensor matrix");
        inertia20TF.setMinimumSize(new java.awt.Dimension(40, 20));
        inertia20TF.setPreferredSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(inertia20TF, gridBagConstraints);

        inertia21TF.setColumns(4);
        inertia21TF.setToolTipText("inertia matrix defines a 3x2 inertia tensor matrix");
        inertia21TF.setMinimumSize(new java.awt.Dimension(40, 20));
        inertia21TF.setPreferredSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(inertia21TF, gridBagConstraints);

        autoDampCB.setToolTipText("enables/disables angularDampingFactor and linearDampingFactor");
        autoDampCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(autoDampCB, gridBagConstraints);

        autoDampLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        autoDampLabel.setText("autoDamp");
        autoDampLabel.setToolTipText("enables/disables angularDampingFactor and linearDampingFactor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 8, 3, 3);
        add(autoDampLabel, gridBagConstraints);

        autoDisableLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        autoDisableLabel.setText("autoDisable");
        autoDisableLabel.setToolTipText("toggles operation of disableAngularSpeed, disableLinearSpeed, disableTime");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 8, 3, 3);
        add(autoDisableLabel, gridBagConstraints);

        autoDisableCB.setToolTipText("toggles operation of disableAngularSpeed, disableLinearSpeed, disableTime");
        autoDisableCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(autoDisableCB, gridBagConstraints);

        useGlobalGravityLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        useGlobalGravityLabel.setText("useGlobalGravity");
        useGlobalGravityLabel.setToolTipText("indicates whether this particular body is influenced by parent RigidBodyCollection's gravity setting");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 27;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 8, 3, 3);
        add(useGlobalGravityLabel, gridBagConstraints);

        useGlobalGravityCB.setToolTipText("indicates whether this particular body is influenced by parent RigidBodyCollection's gravity setting");
        useGlobalGravityCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 27;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 0);
        add(useGlobalGravityCB, gridBagConstraints);

        useFiniteRotationCB.setToolTipText("enables/disables higher-resolution, higher-cost computational method for calculating rotations");
        useFiniteRotationCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 0);
        add(useFiniteRotationCB, gridBagConstraints);

        useFiniteRotationLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        useFiniteRotationLabel.setText("useFiniteRotation");
        useFiniteRotationLabel.setToolTipText("enables/disables higher-resolution, higher-cost computational method for calculating rotations");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 8, 3, 3);
        add(useFiniteRotationLabel, gridBagConstraints);

        fixedCB.setToolTipText("indicates whether body is able to move");
        fixedCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 0);
        add(fixedCB, gridBagConstraints);

        fixedLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        fixedLabel.setText("fixed");
        fixedLabel.setToolTipText("indicates whether body is able to move");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 8, 3, 3);
        add(fixedLabel, gridBagConstraints);

        inertia12TF.setColumns(4);
        inertia12TF.setToolTipText("inertia matrix defines a 3x2 inertia tensor matrix");
        inertia12TF.setMinimumSize(new java.awt.Dimension(40, 20));
        inertia12TF.setPreferredSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(inertia12TF, gridBagConstraints);

        inertia11TF.setColumns(4);
        inertia11TF.setToolTipText("inertia matrix defines a 3x2 inertia tensor matrix");
        inertia11TF.setMinimumSize(new java.awt.Dimension(40, 20));
        inertia11TF.setPreferredSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(inertia11TF, gridBagConstraints);

        inertia10TF.setColumns(4);
        inertia10TF.setToolTipText("inertia matrix defines a 3x2 inertia tensor matrix");
        inertia10TF.setMinimumSize(new java.awt.Dimension(40, 20));
        inertia10TF.setPreferredSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(inertia10TF, gridBagConstraints);

        inertia02TF.setColumns(4);
        inertia02TF.setToolTipText("inertia matrix defines a 3x2 inertia tensor matrix");
        inertia02TF.setMinimumSize(new java.awt.Dimension(40, 20));
        inertia02TF.setPreferredSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(inertia02TF, gridBagConstraints);

        inertia01TF.setColumns(4);
        inertia01TF.setToolTipText("inertia matrix defines a 3x2 inertia tensor matrix");
        inertia01TF.setMinimumSize(new java.awt.Dimension(40, 20));
        inertia01TF.setPreferredSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(inertia01TF, gridBagConstraints);

        torquesLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        torquesLabel.setText("torques");
        torquesLabel.setToolTipText("defines rotational force values applied to the object every frame");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 25;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 8, 3, 3);
        add(torquesLabel, gridBagConstraints);

        position2TF.setColumns(4);
        position2TF.setToolTipText("sets body location in world space, then reports physics updates");
        position2TF.setMinimumSize(new java.awt.Dimension(40, 20));
        position2TF.setPreferredSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(position2TF, gridBagConstraints);

        linearVelocityLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        linearVelocityLabel.setText("linearVelocity");
        linearVelocityLabel.setToolTipText("sets constant velocity value to object every frame, and reports updates by physics model");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 8, 3, 3);
        add(linearVelocityLabel, gridBagConstraints);

        linearVelocity0TF.setToolTipText("sets constant velocity value to object every frame, and reports updates by physics model");
        linearVelocity0TF.setMinimumSize(new java.awt.Dimension(40, 20));
        linearVelocity0TF.setPreferredSize(new java.awt.Dimension(40, 20));
        linearVelocity0TF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                linearVelocity0TFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(linearVelocity0TF, gridBagConstraints);

        linearVelocity1TF.setToolTipText("sets constant velocity value to object every frame, and reports updates by physics model");
        linearVelocity1TF.setMinimumSize(new java.awt.Dimension(40, 20));
        linearVelocity1TF.setPreferredSize(new java.awt.Dimension(40, 20));
        linearVelocity1TF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                linearVelocity1TFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(linearVelocity1TF, gridBagConstraints);

        linearVelocity2TF.setToolTipText("sets constant velocity value to object every frame, and reports updates by physics model");
        linearVelocity2TF.setMinimumSize(new java.awt.Dimension(40, 20));
        linearVelocity2TF.setPreferredSize(new java.awt.Dimension(40, 20));
        linearVelocity2TF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                linearVelocity2TFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(linearVelocity2TF, gridBagConstraints);

        orientationLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        orientationLabel.setText("orientation");
        orientationLabel.setToolTipText("sets body direction in world space, then reports physics updates");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 23;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 8, 3, 3);
        add(orientationLabel, gridBagConstraints);

        orientationXaxisTF.setColumns(4);
        orientationXaxisTF.setToolTipText("sets body direction in world space, then reports physics updates");
        orientationXaxisTF.setMinimumSize(new java.awt.Dimension(40, 20));
        orientationXaxisTF.setPreferredSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 23;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(orientationXaxisTF, gridBagConstraints);

        orientationYaxisTF.setColumns(4);
        orientationYaxisTF.setToolTipText("sets body direction in world space, then reports physics updates");
        orientationYaxisTF.setMinimumSize(new java.awt.Dimension(40, 20));
        orientationYaxisTF.setPreferredSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 23;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(orientationYaxisTF, gridBagConstraints);

        orientationZaxisTF.setColumns(4);
        orientationZaxisTF.setToolTipText("sets body direction in world space, then reports physics updates");
        orientationZaxisTF.setMinimumSize(new java.awt.Dimension(40, 20));
        orientationZaxisTF.setPreferredSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 23;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(orientationZaxisTF, gridBagConstraints);

        orientationAngleTF.setColumns(4);
        orientationAngleTF.setToolTipText("sets body direction in world space, then reports physics updates");
        orientationAngleTF.setMinimumSize(new java.awt.Dimension(40, 20));
        orientationAngleTF.setPreferredSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 23;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(orientationAngleTF, gridBagConstraints);

        orientationCalculatorlButton.setText("calculator");
        orientationCalculatorlButton.setToolTipText("launch geoSystem panel");
        orientationCalculatorlButton.setMaximumSize(new java.awt.Dimension(80, 22));
        orientationCalculatorlButton.setMinimumSize(new java.awt.Dimension(80, 22));
        orientationCalculatorlButton.setPreferredSize(new java.awt.Dimension(6, 22));
        orientationCalculatorlButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orientationCalculatorlButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 23;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(orientationCalculatorlButton, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

private void linearDampingFactorTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_linearDampingFactorTFActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_linearDampingFactorTFActionPerformed

private void disableTimeTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disableTimeTFActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_disableTimeTFActionPerformed

private void disableAngularSpeedTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disableAngularSpeedTFActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_disableAngularSpeedTFActionPerformed

private void angularVelocity1TFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_angularVelocity1TFActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_angularVelocity1TFActionPerformed

private void angularVelocity0TFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_angularVelocity0TFActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_angularVelocity0TFActionPerformed

private void linearVelocity0TFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_linearVelocity0TFActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_linearVelocity0TFActionPerformed

private void linearVelocity1TFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_linearVelocity1TFActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_linearVelocity1TFActionPerformed

private void linearVelocity2TFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_linearVelocity2TFActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_linearVelocity2TFActionPerformed

    private void orientationCalculatorlButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orientationCalculatorlButtonActionPerformed
        RotationCalculatorPanel orientationCalculatorPanel = new RotationCalculatorPanel(rigidBody, "orientation");
        orientationCalculatorPanel.setRotationValue (
            orientationXaxisTF.getText(),
            orientationYaxisTF.getText(),
            orientationZaxisTF.getText(),
            orientationAngleTF.getText());
        DialogDescriptor dd = new DialogDescriptor(orientationCalculatorPanel, "Rotation Calculator for RigidBody orientation");
        Dialog dialog = DialogDisplayer.getDefault().createDialog(dd);
        dialog.setVisible(true);
        if (dd.getValue() != DialogDescriptor.CANCEL_OPTION)
        {
            // save values
            if (!orientationCalculatorPanel.getRotationResult().isEmpty())
            {
                orientationXaxisTF.setText(orientationCalculatorPanel.getRotationResultX());
                orientationYaxisTF.setText(orientationCalculatorPanel.getRotationResultY());
                orientationZaxisTF.setText(orientationCalculatorPanel.getRotationResultZ());
                orientationAngleTF.setText(orientationCalculatorPanel.getRotationResultAngle());
                orientationAngleTF.setToolTipText(orientationCalculatorPanel.getRotationResultAngle() + " radians = " +
                    singleDigitFormat.format(Float.parseFloat(orientationCalculatorPanel.getRotationResultAngle()) * 180.0 / Math.PI) + " degrees");
            }
        }
    }//GEN-LAST:event_orientationCalculatorlButtonActionPerformed
  
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel adjustmentsLabel;
    private javax.swing.JLabel angleLabel;
    private javax.swing.JLabel angularDampingFactorLabel;
    private javax.swing.JTextField angularDampingFactorTF;
    private javax.swing.JTextField angularVelocity0TF;
    private javax.swing.JTextField angularVelocity1TF;
    private javax.swing.JTextField angularVelocity2TF;
    private javax.swing.JLabel angularVelocityLabel;
    private javax.swing.JCheckBox autoDampCB;
    private javax.swing.JLabel autoDampLabel;
    private javax.swing.JCheckBox autoDisableCB;
    private javax.swing.JLabel autoDisableLabel;
    private javax.swing.JTextField centerOfMass0TF;
    private javax.swing.JTextField centerOfMass1TF;
    private javax.swing.JTextField centerOfMass2TF;
    private javax.swing.JLabel centerOfMassLabel;
    private org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel1;
    private javax.swing.JLabel disableAngularSpeedLabel;
    private javax.swing.JTextField disableAngularSpeedTF;
    private javax.swing.JLabel disableLinearSpeedLabel;
    private javax.swing.JTextField disableLinearSpeedTF;
    private javax.swing.JLabel disableTimeLabel;
    private javax.swing.JTextField disableTimeTF;
    private javax.swing.JCheckBox enabledCB;
    private javax.swing.JLabel enabledLabel;
    private javax.swing.JTextField finiteRotationAxis0TF;
    private javax.swing.JTextField finiteRotationAxis1TF;
    private javax.swing.JTextField finiteRotationAxis2TF;
    private javax.swing.JLabel finiteRotationAxisLabel;
    private javax.swing.JCheckBox fixedCB;
    private javax.swing.JLabel fixedLabel;
    private javax.swing.JLabel forcesLabel;
    private javax.swing.JTextField forcesTF;
    private javax.swing.JTextField inertia00TF;
    private javax.swing.JTextField inertia01TF;
    private javax.swing.JTextField inertia02TF;
    private javax.swing.JTextField inertia10TF;
    private javax.swing.JTextField inertia11TF;
    private javax.swing.JTextField inertia12TF;
    private javax.swing.JTextField inertia20TF;
    private javax.swing.JTextField inertia21TF;
    private javax.swing.JTextField inertia22TF;
    private javax.swing.JLabel inertiaLabel;
    private javax.swing.JLabel linearDampingFactorLabel;
    private javax.swing.JTextField linearDampingFactorTF;
    private javax.swing.JTextField linearVelocity0TF;
    private javax.swing.JTextField linearVelocity1TF;
    private javax.swing.JTextField linearVelocity2TF;
    private javax.swing.JLabel linearVelocityLabel;
    private javax.swing.JLabel mass;
    private javax.swing.JTextField massTF;
    private javax.swing.JTextField orientationAngleTF;
    private javax.swing.JButton orientationCalculatorlButton;
    private javax.swing.JLabel orientationLabel;
    private javax.swing.JTextField orientationXaxisTF;
    private javax.swing.JTextField orientationYaxisTF;
    private javax.swing.JTextField orientationZaxisTF;
    private javax.swing.JTextField position0TF;
    private javax.swing.JTextField position1TF;
    private javax.swing.JTextField position2TF;
    private javax.swing.JLabel positionLabel;
    private javax.swing.JLabel torquesLabel;
    private javax.swing.JTextField torquesTF;
    private javax.swing.JCheckBox useFiniteRotationCB;
    private javax.swing.JLabel useFiniteRotationLabel;
    private javax.swing.JCheckBox useGlobalGravityCB;
    private javax.swing.JLabel useGlobalGravityLabel;
    private javax.swing.JLabel xLabel;
    private javax.swing.JLabel yLabel;
    private javax.swing.JLabel zLabel;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_RIGIDBODY";
  }

  @Override
  public void unloadInput()
  {
    unLoadDEFUSE();
    
    rigidBody.setAutoDamp(autoDampCB.isSelected());
    rigidBody.setAutoDisable(autoDisableCB.isSelected());
    rigidBody.setEnabled(enabledCB.isSelected());
    rigidBody.setFixed(fixedCB.isSelected());
    rigidBody.setUseFiniteRotation(useFiniteRotationCB.isSelected());
    rigidBody.setUseGlobalGravity(useGlobalGravityCB.isSelected());
    
    rigidBody.setAngularDampingFactor(angularDampingFactorTF.getText().trim());
    rigidBody.setDisableAngularSpeed(disableAngularSpeedTF.getText().trim());
    rigidBody.setDisableLinearSpeed(disableLinearSpeedTF.getText().trim());
    rigidBody.setDisableTime(disableTimeTF.getText().trim());
    
    rigidBody.setAngularVelocity0(angularVelocity0TF.getText().trim());
    rigidBody.setAngularVelocity1(angularVelocity1TF.getText().trim());
    rigidBody.setAngularVelocity2(angularVelocity2TF.getText().trim());
    
    rigidBody.setCenterOfMass0(centerOfMass0TF.getText().trim());
    rigidBody.setCenterOfMass1(centerOfMass1TF.getText().trim());
    rigidBody.setCenterOfMass2(centerOfMass2TF.getText().trim());
    
    rigidBody.setFiniteRotationAxis0(finiteRotationAxis0TF.getText().trim());
    rigidBody.setFiniteRotationAxis1(finiteRotationAxis1TF.getText().trim());
    rigidBody.setFiniteRotationAxis2(finiteRotationAxis2TF.getText().trim());
    
    rigidBody.setForces(forcesTF.getText().trim());
    
    rigidBody.setInertia00(inertia00TF.getText().trim());
    rigidBody.setInertia01(inertia01TF.getText().trim());
    rigidBody.setInertia02(inertia02TF.getText().trim());
    rigidBody.setInertia10(inertia10TF.getText().trim());
    rigidBody.setInertia11(inertia11TF.getText().trim());
    rigidBody.setInertia12(inertia12TF.getText().trim());
    rigidBody.setInertia20(inertia20TF.getText().trim());
    rigidBody.setInertia21(inertia21TF.getText().trim());
    rigidBody.setInertia22(inertia22TF.getText().trim());
    
    rigidBody.setLinearDampingFactor(linearDampingFactorTF.getText().trim());
    rigidBody.setLinearVelocity0(linearVelocity0TF.getText().trim());
    rigidBody.setLinearVelocity1(linearVelocity1TF.getText().trim());
    rigidBody.setLinearVelocity2(linearVelocity2TF.getText().trim());
    
    rigidBody.setMass(massTF.getText().trim());
    rigidBody.setOrientation0(orientationXaxisTF.getText().trim());
    rigidBody.setOrientation1(orientationYaxisTF.getText().trim());
    rigidBody.setOrientation2(orientationZaxisTF.getText().trim());
    rigidBody.setOrientation3(orientationAngleTF.getText().trim());
    rigidBody.setPosition0(position0TF.getText().trim());
    rigidBody.setPosition1(position1TF.getText().trim());
    rigidBody.setPosition2(position2TF.getText().trim());
    rigidBody.setTorques(torquesTF.getText().trim());
  }   
}
