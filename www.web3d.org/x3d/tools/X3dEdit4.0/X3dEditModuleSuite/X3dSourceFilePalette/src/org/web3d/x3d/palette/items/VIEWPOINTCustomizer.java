/*
Copyright (c) 1995-2022 held by the author(s) .  All rights reserved.
 
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
import java.awt.Dialog;
import java.awt.Font;
import javax.swing.text.JTextComponent;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.HelpCtx;
import javax.vecmath.*;
import org.openide.DialogDescriptor;

import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * VIEWPOINTCustomizer.java
 * Created on August 16, 2007, 3:31 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class VIEWPOINTCustomizer extends BaseCustomizer
{
  private final VIEWPOINT viewpoint;
  private final JTextComponent target; 
  // persist panel settings between invocations
  private static double  targetX, targetY, targetZ, twistAngle = 0.0;
  private static boolean headsUpRotationMode = true;
  private static boolean includeLookatPointAsComment = true; // shared via accessors with ORTHOVIEWPOINTCustomizer, GEOVIEWPOINTCustomizer
	final static String  LOOKAT_COMMENT_HEADER = "local lookat point:";
  private double positionX, positionY, positionZ, deltaX, deltaY, deltaZ, horizontalDistance, distance;
  private double hAngle, pAngle = 0.0; // horizontal angle from -Z axis, pitch angle above/below Y=0 plane
  
  /** Creates new form VIEWPOINTCustomizer
   * @param viewpoint
   * @param target
   */
  public VIEWPOINTCustomizer(VIEWPOINT viewpoint, JTextComponent target)
  {
    super(viewpoint);
    this.viewpoint = viewpoint;
    this.target = target;
                                 
    HelpCtx.setHelpIDString(this, "VIEWPOINT_ELEM_HELPID");

    viewpoint.setVisualizationSelectionAvailable(true); // must precede initComponents() interface initialization
    viewpoint.setVisualizationTooltip("Add wireframe and transparent ViewFrustum to show Viewpoint coverage");
    
    initComponents();
    
    centerOfRotationXTF.setText(viewpoint.getCenterOfRotationX());
    centerOfRotationYTF.setText(viewpoint.getCenterOfRotationY());
    centerOfRotationZTF.setText(viewpoint.getCenterOfRotationZ());
    
    descriptionTA.setText(viewpoint.getDescription());
    fieldOfViewTF.setText(viewpoint.getFieldOfView());
                 jumpCB.setSelected(viewpoint.isJump());
    retainUserOffsetsCB.setSelected(viewpoint.isRetainUserOffsets());
    
    orientationXaxisTF.setText(viewpoint.getOrientationX());
    orientationYaxisTF.setText(viewpoint.getOrientationY());
    orientationZaxisTF.setText(viewpoint.getOrientationZ());
    orientationAngleTF.setText(viewpoint.getOrientationAngle());
    if (viewpoint.getOrientationX().equals("0") && viewpoint.getOrientationY().equals("0") && viewpoint.getOrientationZ().equals("1") &&
        viewpoint.getOrientationAngle().equals("0"))
    {
        // use preferred default vice X3D default
        orientationYaxisTF.setText("1");
        orientationZaxisTF.setText("0");
    }
    positionXTF.setText(viewpoint.getPositionX());
    positionYTF.setText(viewpoint.getPositionY());
    positionZTF.setText(viewpoint.getPositionZ());

            targetXTF.setText(String.valueOf(targetX));
            targetYTF.setText(String.valueOf(targetY));
            targetZTF.setText(String.valueOf(targetZ));
         twistAngleTF.setText(String.valueOf(twistAngle));
    horizontalAngleTF.setText(String.valueOf(hAngle));
      verticalAngleTF.setText(String.valueOf(pAngle));

    if  (headsUpRotationMode)
         headsUpRotationRadioButton.setSelected(true);
    else greatCircleRotationRadioButton.setSelected(true);
    headsUpRotationRadioButton.updateUI();
	
    computeDistanceAngleValues ();
    checkAngles (false);
    
    // test if centerOfRotation not level (using TF instead of original value due to axis swap above)
    if ((orientationXaxisTF.getText().equals("0") && orientationYaxisTF.getText().equals("1") && orientationZaxisTF.getText().equals("0")) &&
        (new SFFloat(viewpoint.getCenterOfRotationY()).getValue() != (new SFFloat(viewpoint.getPositionY()).getValue())))
    {
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  "<html><p>Viewpoint centerOfRotation height differs from position height</p>" +
						"<p>Match height so that examination rotation doesn't jump?</p></html>", "Viewpoint centerOfRotation check", NotifyDescriptor.YES_NO_OPTION);
          if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
          {
              centerOfRotationYTF.setText(viewpoint.getPositionY());
          } 
    }
    targetXTF.setText(centerOfRotationXTF.getText());
    targetYTF.setText(centerOfRotationYTF.getText());
    targetZTF.setText(centerOfRotationZTF.getText());
	
	includeLookatPointAsCommentCheckBox.setSelected(includeLookatPointAsComment || ((viewpoint.getContent() != null) && viewpoint.getContent().contains(LOOKAT_COMMENT_HEADER)));
	if ((viewpoint.getContent() != null) && viewpoint.getContent().contains(LOOKAT_COMMENT_HEADER))
	{
		String lookatValuesString = viewpoint.getContent().substring(
				viewpoint.getContent().indexOf(LOOKAT_COMMENT_HEADER) + LOOKAT_COMMENT_HEADER.length(),
				viewpoint.getContent().indexOf("-->")).trim();
		String[] lookatValues = lookatValuesString.split(" ");
		if ((lookatValues != null) && (lookatValues.length == 3))
		{
			targetXTF.setText(lookatValues[0]);
			targetYTF.setText(lookatValues[1]);
			targetZTF.setText(lookatValues[2]);
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

        rotationModeButtonGroup = new javax.swing.ButtonGroup();
        dEFUSEpanel1 = getDEFUSEpanel();
        descriptionLabel = new javax.swing.JLabel();
        descriptionScrollPane = new javax.swing.JScrollPane();
        descriptionTA = new javax.swing.JTextArea();
        xLabel = new javax.swing.JLabel();
        yLabel = new javax.swing.JLabel();
        zLabel = new javax.swing.JLabel();
        angleLabel = new javax.swing.JLabel();
        adjustmentsLabel = new javax.swing.JLabel();
        positionLabel = new javax.swing.JLabel();
        positionXTF = new javax.swing.JTextField();
        positionYTF = new javax.swing.JTextField();
        positionZTF = new javax.swing.JTextField();
        centerOfRotationLabel = new javax.swing.JLabel();
        centerOfRotationXTF = new javax.swing.JTextField();
        centerOfRotationYTF = new javax.swing.JTextField();
        centerOfRotationZTF = new javax.swing.JTextField();
        orientationLabel = new javax.swing.JLabel();
        orientationXaxisTF = new javax.swing.JTextField();
        orientationYaxisTF = new javax.swing.JTextField();
        orientationZaxisTF = new javax.swing.JTextField();
        orientationAngleTF = new javax.swing.JTextField();
        fieldOfViewLabel = new javax.swing.JLabel();
        fieldOfViewTF = new javax.swing.JTextField();
        normalizeRotationValuesButton = new javax.swing.JButton();
        jumpLabel = new javax.swing.JLabel();
        jumpCB = new javax.swing.JCheckBox();
        retainUserOffsetsCB = new javax.swing.JCheckBox();
        authorAssistSeparator = new javax.swing.JSeparator();
        authorAssistLabel = new javax.swing.JLabel();
        goalAimPointLabel = new javax.swing.JLabel();
        targetXTF = new javax.swing.JTextField();
        targetYTF = new javax.swing.JTextField();
        targetZTF = new javax.swing.JTextField();
        recomputeLookatRotationButton = new javax.swing.JButton();
        twistAngleLabel = new javax.swing.JLabel();
        twistAngleTF = new javax.swing.JTextField();
        headsUpRotationRadioButton = new javax.swing.JRadioButton();
        greatCircleRotationRadioButton = new javax.swing.JRadioButton();
        includeLookatPointAsCommentCheckBox = new javax.swing.JCheckBox();
        slantRangeLabel = new javax.swing.JLabel();
        slantRangeTF = new javax.swing.JTextField();
        horizontalRangeLabel = new javax.swing.JLabel();
        horizontalRangeTF = new javax.swing.JTextField();
        verticalRangeLabel = new javax.swing.JLabel();
        verticalRangeTF = new javax.swing.JTextField();
        horizontalAngleLabel = new javax.swing.JLabel();
        horizontalAngleTF = new javax.swing.JTextField();
        verticalAngleLabel = new javax.swing.JLabel();
        verticalAngleTF = new javax.swing.JTextField();
        resetAimPointButton = new javax.swing.JButton();
        levelHeightButton = new javax.swing.JButton();
        spacerLabel = new javax.swing.JLabel();
        rotationCalculatorlButton = new javax.swing.JButton();
        hintLabel = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(680, 700));
        setRequestFocusEnabled(false);
        setLayout(new java.awt.GridBagLayout());

        dEFUSEpanel1.setMaximumSize(null);
        dEFUSEpanel1.setMinimumSize(null);
        dEFUSEpanel1.setPreferredSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel1, gridBagConstraints);

        descriptionLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        descriptionLabel.setText("description");
        descriptionLabel.setToolTipText("Text description or navigation hint identifying this Viewpoint. Hint: use spaces, make descriptions clear and readable. ");
        descriptionLabel.setMaximumSize(new java.awt.Dimension(200, 22));
        descriptionLabel.setMinimumSize(new java.awt.Dimension(130, 22));
        descriptionLabel.setOpaque(true);
        descriptionLabel.setPreferredSize(new java.awt.Dimension(130, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(descriptionLabel, gridBagConstraints);

        descriptionTA.setColumns(1);
        descriptionTA.setRows(2);
        descriptionTA.setToolTipText("Text description or navigation hint identifying this Viewpoint. Hint: use spaces, make descriptions clear and readable. ");
        descriptionTA.setMinimumSize(new java.awt.Dimension(12, 58));
        descriptionTA.setPreferredSize(new java.awt.Dimension(12, 58));
        descriptionScrollPane.setViewportView(descriptionTA);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.8;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(descriptionScrollPane, gridBagConstraints);

        xLabel.setText("x");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(8, 3, 3, 3);
        add(xLabel, gridBagConstraints);

        yLabel.setText("y");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weighty = 0.2;
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
        gridBagConstraints.insets = new java.awt.Insets(8, 3, 3, 3);
        add(adjustmentsLabel, gridBagConstraints);

        positionLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        positionLabel.setText("position");
        positionLabel.setToolTipText("Viewpoint location (x, y, z in meters) relative to local coordinate system");
        positionLabel.setMaximumSize(new java.awt.Dimension(200, 22));
        positionLabel.setMinimumSize(new java.awt.Dimension(130, 22));
        positionLabel.setPreferredSize(new java.awt.Dimension(130, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(positionLabel, gridBagConstraints);

        positionXTF.setToolTipText("Viewpoint location (x, y, z in meters) relative to local coordinate system");
        positionXTF.setMinimumSize(new java.awt.Dimension(60, 22));
        positionXTF.setPreferredSize(new java.awt.Dimension(60, 22));
        positionXTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                positionXTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(positionXTF, gridBagConstraints);

        positionYTF.setToolTipText("Viewpoint location (x, y, z in meters) relative to local coordinate system");
        positionYTF.setMinimumSize(new java.awt.Dimension(60, 22));
        positionYTF.setPreferredSize(new java.awt.Dimension(60, 22));
        positionYTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                positionYTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(positionYTF, gridBagConstraints);

        positionZTF.setToolTipText("Viewpoint location (x, y, z in meters) relative to local coordinate system");
        positionZTF.setMinimumSize(new java.awt.Dimension(60, 22));
        positionZTF.setPreferredSize(new java.awt.Dimension(60, 22));
        positionZTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                positionZTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(positionZTF, gridBagConstraints);

        centerOfRotationLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        centerOfRotationLabel.setText("centerOfRotation");
        centerOfRotationLabel.setToolTipText("centerOfRotation point is used by currently bound NavigationInfo in EXAMINE mode");
        centerOfRotationLabel.setMaximumSize(new java.awt.Dimension(200, 22));
        centerOfRotationLabel.setMinimumSize(new java.awt.Dimension(130, 22));
        centerOfRotationLabel.setPreferredSize(new java.awt.Dimension(130, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(centerOfRotationLabel, gridBagConstraints);

        centerOfRotationXTF.setToolTipText("centerOfRotation point is used by currently bound NavigationInfo in EXAMINE mode");
        centerOfRotationXTF.setMinimumSize(new java.awt.Dimension(60, 22));
        centerOfRotationXTF.setPreferredSize(new java.awt.Dimension(60, 22));
        centerOfRotationXTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                centerOfRotationXTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(centerOfRotationXTF, gridBagConstraints);

        centerOfRotationYTF.setToolTipText("centerOfRotation point is used by currently bound NavigationInfo in EXAMINE mode");
        centerOfRotationYTF.setMinimumSize(new java.awt.Dimension(60, 22));
        centerOfRotationYTF.setPreferredSize(new java.awt.Dimension(60, 22));
        centerOfRotationYTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                centerOfRotationYTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(centerOfRotationYTF, gridBagConstraints);

        centerOfRotationZTF.setToolTipText("centerOfRotation point is used by currently bound NavigationInfo in EXAMINE mode");
        centerOfRotationZTF.setMinimumSize(new java.awt.Dimension(60, 22));
        centerOfRotationZTF.setPreferredSize(new java.awt.Dimension(60, 22));
        centerOfRotationZTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                centerOfRotationZTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(centerOfRotationZTF, gridBagConstraints);

        orientationLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        orientationLabel.setText("orientation");
        orientationLabel.setToolTipText("rotation (axis, angle in radians) of Viewpoint, relative to default -Z axis direction in local coordinate system");
        orientationLabel.setMaximumSize(new java.awt.Dimension(200, 22));
        orientationLabel.setMinimumSize(new java.awt.Dimension(130, 22));
        orientationLabel.setPreferredSize(new java.awt.Dimension(130, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(orientationLabel, gridBagConstraints);

        orientationXaxisTF.setToolTipText("rotation (axis, angle in radians) of Viewpoint, relative to default -Z axis direction in local coordinate system");
        orientationXaxisTF.setMinimumSize(new java.awt.Dimension(60, 22));
        orientationXaxisTF.setPreferredSize(new java.awt.Dimension(60, 22));
        orientationXaxisTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orientationXaxisTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(orientationXaxisTF, gridBagConstraints);

        orientationYaxisTF.setToolTipText("rotation (axis, angle in radians) of Viewpoint, relative to default -Z axis direction in local coordinate system");
        orientationYaxisTF.setMinimumSize(new java.awt.Dimension(60, 22));
        orientationYaxisTF.setPreferredSize(new java.awt.Dimension(60, 22));
        orientationYaxisTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orientationYaxisTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(orientationYaxisTF, gridBagConstraints);

        orientationZaxisTF.setToolTipText("rotation (axis, angle in radians) of Viewpoint, relative to default -Z axis direction in local coordinate system");
        orientationZaxisTF.setMinimumSize(new java.awt.Dimension(60, 22));
        orientationZaxisTF.setPreferredSize(new java.awt.Dimension(60, 22));
        orientationZaxisTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orientationZaxisTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(orientationZaxisTF, gridBagConstraints);

        orientationAngleTF.setToolTipText("rotation (axis, angle in radians) of Viewpoint, relative to default -Z axis direction in local coordinate system");
        orientationAngleTF.setMaximumSize(new java.awt.Dimension(60, 22));
        orientationAngleTF.setMinimumSize(new java.awt.Dimension(60, 22));
        orientationAngleTF.setPreferredSize(new java.awt.Dimension(60, 22));
        orientationAngleTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orientationAngleTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(orientationAngleTF, gridBagConstraints);

        fieldOfViewLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        fieldOfViewLabel.setText("fieldOfView");
        fieldOfViewLabel.setToolTipText("Preferred minimum viewing angle from this viewpoint in radians. Small field of view roughly corresponds to a telephoto lens, large field of view roughly corresponds to a wide-angle lens. ");
        fieldOfViewLabel.setMaximumSize(new java.awt.Dimension(200, 22));
        fieldOfViewLabel.setMinimumSize(new java.awt.Dimension(130, 22));
        fieldOfViewLabel.setPreferredSize(new java.awt.Dimension(130, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(fieldOfViewLabel, gridBagConstraints);

        fieldOfViewTF.setToolTipText("Preferred minimum viewing angle from this viewpoint in radians. Small field of view roughly corresponds to a telephoto lens, large field of view roughly corresponds to a wide-angle lens. ");
        fieldOfViewTF.setMinimumSize(new java.awt.Dimension(60, 22));
        fieldOfViewTF.setPreferredSize(new java.awt.Dimension(60, 22));
        fieldOfViewTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fieldOfViewTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(fieldOfViewTF, gridBagConstraints);

        normalizeRotationValuesButton.setText("normalize orientation values and fieldOfView angle");
        normalizeRotationValuesButton.setToolTipText("for orientation and fieldOfView values, rescale axis values as normalized vector (unit length, ranges 0..1) and reset angles [0..2pi)");
        normalizeRotationValuesButton.setMaximumSize(new java.awt.Dimension(180, 25));
        normalizeRotationValuesButton.setMinimumSize(new java.awt.Dimension(180, 25));
        normalizeRotationValuesButton.setPreferredSize(new java.awt.Dimension(180, 25));
        normalizeRotationValuesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                normalizeRotationValuesButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.6;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(normalizeRotationValuesButton, gridBagConstraints);

        jumpLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jumpLabel.setText("jump");
        jumpLabel.setToolTipText("Transition instantly by jumping, or smoothly adjust offsets in place when changing to this Viewpoint");
        jumpLabel.setMaximumSize(new java.awt.Dimension(200, 22));
        jumpLabel.setMinimumSize(new java.awt.Dimension(130, 22));
        jumpLabel.setPreferredSize(new java.awt.Dimension(130, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(jumpLabel, gridBagConstraints);

        jumpCB.setToolTipText("Transition instantly by jumping, or smoothly adjust offsets in place when changing to this Viewpoint");
        jumpCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jumpCB.setMinimumSize(new java.awt.Dimension(60, 18));
        jumpCB.setPreferredSize(new java.awt.Dimension(60, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(jumpCB, gridBagConstraints);

        retainUserOffsetsCB.setText("retainUserOffsets");
        retainUserOffsetsCB.setToolTipText("retain (true) or reset to zero (false) any prior user navigation offsets from defined viewpoint position, orientation");
        retainUserOffsetsCB.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        retainUserOffsetsCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        retainUserOffsetsCB.setMinimumSize(new java.awt.Dimension(60, 18));
        retainUserOffsetsCB.setPreferredSize(new java.awt.Dimension(60, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(retainUserOffsetsCB, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 3, 3);
        add(authorAssistSeparator, gridBagConstraints);

        authorAssistLabel.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        authorAssistLabel.setForeground(new java.awt.Color(0, 102, 102));
        authorAssistLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        authorAssistLabel.setText("Viewpoint Rotation Calculator");
        authorAssistLabel.setToolTipText("Recompute Viewpoint orientation based on position and goal look-at point");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.6;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(authorAssistLabel, gridBagConstraints);

        goalAimPointLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        goalAimPointLabel.setForeground(new java.awt.Color(0, 102, 102));
        goalAimPointLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        goalAimPointLabel.setText("goal look-at point");
        goalAimPointLabel.setToolTipText("set desired point to aim Viewpoint orientation, also set centerOfRotation");
        goalAimPointLabel.setMaximumSize(new java.awt.Dimension(200, 22));
        goalAimPointLabel.setMinimumSize(new java.awt.Dimension(130, 22));
        goalAimPointLabel.setPreferredSize(new java.awt.Dimension(130, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(goalAimPointLabel, gridBagConstraints);

        targetXTF.setForeground(new java.awt.Color(0, 102, 102));
        targetXTF.setText("0");
        targetXTF.setToolTipText("x value for desired point to aim Viewpoint orientation");
        targetXTF.setMinimumSize(new java.awt.Dimension(60, 22));
        targetXTF.setPreferredSize(new java.awt.Dimension(60, 22));
        targetXTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                targetXTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(targetXTF, gridBagConstraints);

        targetYTF.setForeground(new java.awt.Color(0, 102, 102));
        targetYTF.setText("0");
        targetYTF.setToolTipText("y value for desired point to aim Viewpoint orientation");
        targetYTF.setMinimumSize(new java.awt.Dimension(60, 22));
        targetYTF.setPreferredSize(new java.awt.Dimension(60, 22));
        targetYTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                targetYTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(targetYTF, gridBagConstraints);

        targetZTF.setForeground(new java.awt.Color(0, 102, 102));
        targetZTF.setText("0");
        targetZTF.setToolTipText("z value for desired point to aim Viewpoint orientation");
        targetZTF.setMinimumSize(new java.awt.Dimension(60, 22));
        targetZTF.setPreferredSize(new java.awt.Dimension(60, 22));
        targetZTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                targetZTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(targetZTF, gridBagConstraints);

        recomputeLookatRotationButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        recomputeLookatRotationButton.setForeground(new java.awt.Color(0, 102, 102));
        recomputeLookatRotationButton.setText("recompute orientation for look-at point");
        recomputeLookatRotationButton.setToolTipText("use goal aim point to recompute Viewpoint orientation, also set look-at point as centerOfRotation");
        recomputeLookatRotationButton.setMaximumSize(new java.awt.Dimension(180, 25));
        recomputeLookatRotationButton.setMinimumSize(new java.awt.Dimension(180, 25));
        recomputeLookatRotationButton.setPreferredSize(new java.awt.Dimension(180, 25));
        recomputeLookatRotationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recomputeLookatRotationButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(recomputeLookatRotationButton, gridBagConstraints);

        twistAngleLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        twistAngleLabel.setForeground(new java.awt.Color(0, 102, 102));
        twistAngleLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        twistAngleLabel.setText("twist angle");
        twistAngleLabel.setToolTipText("twist angle applied about final view axis");
        twistAngleLabel.setMaximumSize(new java.awt.Dimension(60, 22));
        twistAngleLabel.setMinimumSize(new java.awt.Dimension(60, 22));
        twistAngleLabel.setPreferredSize(new java.awt.Dimension(60, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(twistAngleLabel, gridBagConstraints);

        twistAngleTF.setForeground(new java.awt.Color(0, 102, 102));
        twistAngleTF.setText("0");
        twistAngleTF.setToolTipText(" twist angle applied about final view axis");
        twistAngleTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                twistAngleTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(twistAngleTF, gridBagConstraints);

        rotationModeButtonGroup.add(headsUpRotationRadioButton);
        headsUpRotationRadioButton.setSelected(true);
        headsUpRotationRadioButton.setText("heads-up rotation");
        headsUpRotationRadioButton.setToolTipText("first rotate in horizontal plane, then pitch up/down to compute new orientation (preferred mode)");
        headsUpRotationRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                headsUpRotationRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.4;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(headsUpRotationRadioButton, gridBagConstraints);

        rotationModeButtonGroup.add(greatCircleRotationRadioButton);
        greatCircleRotationRadioButton.setText("direct-path rotation");
        greatCircleRotationRadioButton.setToolTipText("rotate via direct path from initial to final view orientation, although result may be tilted obliquely from horizontal plane");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.4;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(greatCircleRotationRadioButton, gridBagConstraints);

        includeLookatPointAsCommentCheckBox.setSelected(true);
        includeLookatPointAsCommentCheckBox.setText("include lookat point as comment");
        includeLookatPointAsCommentCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                includeLookatPointAsCommentCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(includeLookatPointAsCommentCheckBox, gridBagConstraints);

        slantRangeLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        slantRangeLabel.setText("slant range");
        slantRangeLabel.setToolTipText("computed straight-line distance in meters between Viewpoint position and goal aim point");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(slantRangeLabel, gridBagConstraints);

        slantRangeTF.setEditable(false);
        slantRangeTF.setText("0 m");
        slantRangeTF.setToolTipText("computed straight-line distance in meters between Viewpoint position and goal aim point");
        slantRangeTF.setBorder(null);
        slantRangeTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                slantRangeTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(slantRangeTF, gridBagConstraints);

        horizontalRangeLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        horizontalRangeLabel.setText("horizontal range");
        horizontalRangeLabel.setToolTipText("computed horizontal distance in meters between Viewpoint position and goal aim point");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(horizontalRangeLabel, gridBagConstraints);

        horizontalRangeTF.setEditable(false);
        horizontalRangeTF.setText("0 m");
        horizontalRangeTF.setToolTipText("computed horizontal distance in meters between Viewpoint position and goal aim point");
        horizontalRangeTF.setBorder(null);
        horizontalRangeTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                horizontalRangeTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(horizontalRangeTF, gridBagConstraints);

        verticalRangeLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        verticalRangeLabel.setText("vertical range");
        verticalRangeLabel.setToolTipText("computed vertical distance in meters between Viewpoint position and goal aim point");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(verticalRangeLabel, gridBagConstraints);

        verticalRangeTF.setEditable(false);
        verticalRangeTF.setText("0 m");
        verticalRangeTF.setToolTipText("computed vertical distance in meters between Viewpoint position and goal aim point");
        verticalRangeTF.setBorder(null);
        verticalRangeTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verticalRangeTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(verticalRangeTF, gridBagConstraints);

        horizontalAngleLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        horizontalAngleLabel.setText("horizontal angle");
        horizontalAngleLabel.setToolTipText("initial angle of heads-up rotation about +Y axis in horizontal plane");
        horizontalAngleLabel.setMaximumSize(new java.awt.Dimension(60, 22));
        horizontalAngleLabel.setMinimumSize(new java.awt.Dimension(60, 22));
        horizontalAngleLabel.setPreferredSize(new java.awt.Dimension(60, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(horizontalAngleLabel, gridBagConstraints);

        horizontalAngleTF.setEditable(false);
        horizontalAngleTF.setText("0");
        horizontalAngleTF.setToolTipText(" horizontal angle computed about +Y axis");
        horizontalAngleTF.setBorder(null);
        horizontalAngleTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                horizontalAngleTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 3, 3);
        add(horizontalAngleTF, gridBagConstraints);

        verticalAngleLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        verticalAngleLabel.setText("vertical angle");
        verticalAngleLabel.setToolTipText("secondary vertical pitch angle above/below horizontal plane, also computed for heads-up rotation");
        verticalAngleLabel.setMaximumSize(new java.awt.Dimension(60, 22));
        verticalAngleLabel.setMinimumSize(new java.awt.Dimension(60, 22));
        verticalAngleLabel.setPreferredSize(new java.awt.Dimension(60, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(verticalAngleLabel, gridBagConstraints);

        verticalAngleTF.setEditable(false);
        verticalAngleTF.setText("0");
        verticalAngleTF.setToolTipText(" vertical pitch angle computed above/below horizontal plane");
        verticalAngleTF.setBorder(null);
        verticalAngleTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verticalAngleTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 3, 3);
        add(verticalAngleTF, gridBagConstraints);

        resetAimPointButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        resetAimPointButton.setForeground(new java.awt.Color(0, 102, 102));
        resetAimPointButton.setText("reset");
        resetAimPointButton.setToolTipText("set look-at point to origin without changing Viewpoint values");
        resetAimPointButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetAimPointButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(resetAimPointButton, gridBagConstraints);

        levelHeightButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        levelHeightButton.setForeground(new java.awt.Color(0, 102, 102));
        levelHeightButton.setText("level height");
        levelHeightButton.setToolTipText("match height of look-at point to position");
        levelHeightButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                levelHeightButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(levelHeightButton, gridBagConstraints);

        spacerLabel.setText("                 ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(spacerLabel, gridBagConstraints);

        rotationCalculatorlButton.setText("calculator");
        rotationCalculatorlButton.setToolTipText("launch geoSystem panel");
        rotationCalculatorlButton.setMaximumSize(new java.awt.Dimension(80, 22));
        rotationCalculatorlButton.setMinimumSize(new java.awt.Dimension(40, 22));
        rotationCalculatorlButton.setPreferredSize(new java.awt.Dimension(40, 22));
        rotationCalculatorlButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotationCalculatorlButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(rotationCalculatorlButton, gridBagConstraints);

        hintLabel.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        hintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel.setText("<html>  <p align=\"center\"><b>Viewpoint</b> provides a specific location and direction where the user may view the scene. Viewpoints are <br />\n the primary way for a user to navigate within a scene, and for an author to show critical aspects of a model. \n<ul>\n  <li> Hint: a list of good Viewpoints with understandable descriptions can provide a guided tour of the model. </li>\n  <li> Hint: currently bound NavigationInfo node determines how users navigate after reaching this Viewpoint. </li>\n</ul></p>");
        hintLabel.setToolTipText("Viewpoint provides a specific location and direction where the user may view the scene.");
        hintLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        hintLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(hintLabel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void normalizeRotationValuesButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_normalizeRotationValuesButtonActionPerformed
    {//GEN-HEADEREND:event_normalizeRotationValuesButtonActionPerformed
        checkAngles(true);
        float normalizationFactor, x, y, z, angle;

        x     = new SFFloat(orientationXaxisTF.getText()).getValue();
        y     = new SFFloat(orientationYaxisTF.getText()).getValue();
        z     = new SFFloat(orientationZaxisTF.getText()).getValue();
        angle = new SFFloat(orientationAngleTF.getText()).getValue();

        if ((x <= 0.0) && (y <= 0.0) && (z <= 0.0))
        {
            // negate all components to produce simpler form since all axis values are non-positive
                x = -x;
                y = -y;
                z = -z;
            angle = -angle;
        }
        normalizationFactor = (float)Math.sqrt(x * x + y * y + z * z);
        if (normalizationFactor == 0.0)
        {
            NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                    "Found zero-magnitude axis for orientation, reset to 0 1 0", NotifyDescriptor.WARNING_MESSAGE);
            DialogDisplayer.getDefault().notify(descriptor);
            orientationXaxisTF.setText("0");
            orientationYaxisTF.setText("1");
            orientationZaxisTF.setText("0");
        }
        else
        {
            orientationXaxisTF.setText(fiveDigitFormat.format(x / normalizationFactor));
            orientationYaxisTF.setText(fiveDigitFormat.format(y / normalizationFactor));
            orientationZaxisTF.setText(fiveDigitFormat.format(z / normalizationFactor));
        }

        if (angle == -0.0f)
        {
            angle = 0.0f;
        }
        while (angle <= -Math.PI)
        {
            angle += 2.0 * Math.PI;
        }
        while (angle > Math.PI)
        {
            angle -= 2.0 * Math.PI;
        }
        orientationAngleTF.setText(radiansFormat.format(angle));
        orientationAngleTF.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");

        angle = new SFFloat(fieldOfViewTF.getText()).getValue();
        if (angle == -0.0)
        {
            angle = 0.0f;
        }
        while (angle <= -Math.PI)
        {
            angle += 2.0 * Math.PI;
        }
        while (angle > Math.PI)
        {
            angle -= 2.0 * Math.PI;
        }
        fieldOfViewTF.setText(radiansFormat.format(angle));
        fieldOfViewTF.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");

        // idiosyncracy/roundoff cleanup
        if (    orientationXaxisTF.getText().equals("-0") ||     orientationXaxisTF.getText().equals("-0.0"))     orientationXaxisTF.setText("0");
        if (    orientationYaxisTF.getText().equals("-0") ||     orientationYaxisTF.getText().equals("-0.0"))     orientationYaxisTF.setText("0");
        if (    orientationZaxisTF.getText().equals("-0") ||     orientationZaxisTF.getText().equals("-0.0"))     orientationZaxisTF.setText("0");
        if (orientationAngleTF.getText().equals("-0") || orientationAngleTF.getText().equals("-0.0")) orientationAngleTF.setText("0");
        if (     fieldOfViewTF.getText().equals("-0") ||      fieldOfViewTF.getText().equals("-0.0"))      fieldOfViewTF.setText("0.0");

        // prefer +Y axis for zero rotation
        if ((    orientationXaxisTF.getText().equals("1") &&
                 orientationYaxisTF.getText().equals("0") &&
                 orientationZaxisTF.getText().equals("0") &&
                 orientationAngleTF.getText().equals("0"))
            ||
            (    orientationXaxisTF.getText().equals("0") &&
                 orientationYaxisTF.getText().equals("0") &&
                 orientationZaxisTF.getText().equals("1") &&
                 orientationAngleTF.getText().equals("0")))
        {
            orientationXaxisTF.setText("0");
            orientationYaxisTF.setText("1");
            orientationZaxisTF.setText("0");
        }
}//GEN-LAST:event_normalizeRotationValuesButtonActionPerformed

    private void orientationAngleTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_orientationAngleTFActionPerformed
    {//GEN-HEADEREND:event_orientationAngleTFActionPerformed
        checkAngles (false);
        resetDisplayViewpointCalculatorFields ();
    }//GEN-LAST:event_orientationAngleTFActionPerformed

    private void fieldOfViewTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_fieldOfViewTFActionPerformed
    {//GEN-HEADEREND:event_fieldOfViewTFActionPerformed
        checkAngles (false);
    }//GEN-LAST:event_fieldOfViewTFActionPerformed

    private void recomputeLookatRotationButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_recomputeLookatRotationButtonActionPerformed
    {//GEN-HEADEREND:event_recomputeLookatRotationButtonActionPerformed
        // axis
        // reference:  http://en.wikipedia.org/wiki/Cross_product
        // a × b = (a2b3 − a3b2) i + (a3b1 − a1b3) j + (a1b2 − a2b1) k = (a2b3 − a3b2, a3b1 − a1b3, a1b2 − a2b1).

        // angle
        // reference:  http://en.wikipedia.org/wiki/Dot_product
        // a • b = a1b1 + a2b2 + a3b3
        // a • b = |a| |b| cos(theta)
        // theta = arccos (a • b  / (|a| |b|))

        // goal:  compare default viewpoint orientation along -Z axis to difference vector (Target.position - Viewpoint.position)

        checkAngles (false);
        computeDistanceAngleValues ();

        // heads-up rotation computation:  first rotate in horizontal plane, then pitch up/down towards lookat point

        // javax.vecmath conversions (boy is this a convoluted API or what!)
        AxisAngle4d axisAngleHorizontal = new AxisAngle4d(0.0, 1.0, 0.0, hAngle);
        AxisAngle4d axisAnglePitch      = new AxisAngle4d(1.0, 0.0, 0.0, pAngle);
        AxisAngle4d axisAngleTwist      = new AxisAngle4d(1.0, 0.0, 0.0, twistAngle);

        Quat4d      quatHorizontal      = new Quat4d ();      quatHorizontal.set(axisAngleHorizontal);
        Quat4d      quatPitch           = new Quat4d ();           quatPitch.set(axisAnglePitch);
        Quat4d      quatTwist           = new Quat4d ();           quatTwist.set(axisAngleTwist);

        Matrix4f    matrixHorizontal    = new Matrix4f();   matrixHorizontal.set (quatHorizontal);
        Matrix4f    matrixPitch         = new Matrix4f();        matrixPitch.set (quatPitch);
        Matrix4f    matrixTwist         = new Matrix4f();        matrixTwist.set (quatTwist);
        Matrix4f    matrixRotated       = new Matrix4f();
        Matrix4f    matrixTwisted       = new Matrix4f();

        matrixRotated.mul (matrixHorizontal, matrixPitch);
        Quat4d      quatRotated         = new Quat4d ();          quatRotated.set(matrixRotated);
        AxisAngle4d axisAngleRotated    = new AxisAngle4d(); axisAngleRotated.set(quatRotated);

        matrixTwisted.mul (matrixRotated, matrixTwist);
        Quat4d      quatTwisted         = new Quat4d ();          quatTwisted.set(matrixTwisted);
        AxisAngle4d axisAngleTwisted    = new AxisAngle4d(); axisAngleTwisted.set(quatTwisted);

        // great-circle route computation:  go directly from initial to final view direction
        // (code block adapted in BaseX3DElement)
        double a, b, a1, a2, a3, b1, b2, b3, a_dot_b, axis1, axis2, axis3, angle;
        a1 =  0.0;
        a2 =  0.0;
        a3 = -1.0;
        b1 =  - positionX;
        b2 = targetY - positionY;
        b3 = targetZ - positionZ;
        a  = Math.sqrt(a1*a1 + a2*a2 + a3*a3); // magnitude
        b  = Math.sqrt(b1*b1 + b2*b2 + b3*b3); // magnitude
        a_dot_b = a1*b1 + a2*b2 + a3*b3;

        // compute axis and angle values
        axis1 = a2 * b3 - a3 * b2;
        axis2 = a3 * b1 - a1 * b3;
        axis3 = a1 * b2 - a2 * b1;

        double axisLength = Math.sqrt(axis1*axis1 + axis2*axis2 + axis3*axis3);
        if (axisLength > 0.0) // normalize
        {
            axis1 /= axisLength;
            axis2 /= axisLength;
            axis3 /= axisLength;
        }

        if ((a != 0.0) && (b != 0.0)) // avoid divide by zero
        {
             angle = Math.acos(a_dot_b / (a * b));
        }
        else angle = 0.0;

        // test for zero vector, reset to unit vector (to avoid later normalization warning)
        if ((axis1 == 0.0) && (axis2 == 0.0) && (axis3 == 0.0))
        {
            axis2 = 1.0;
            angle = 0.0;
        }

        // eliminate negative zero -0.0 values; equality test (value == -0.0) didn't work
        if (axis1 == -0.0) axis1 = +0.0;
        if (axis2 == -0.0) axis2 = +0.0;
        if (axis3 == -0.0) axis3 = +0.0;

        // various other user-assist cleanups, no change in rotation intended
        if      ((axis1 < 0.0) && (axis2 == 0.0) && (axis3 == 0.0))
        {
            axis1 = -axis1;
            angle = -angle;
        }
        else if ((axis1 == 0.0) && (axis2 < 0.0) && (axis3 == 0.0))
        {
            axis2 = -axis2;
            angle = -angle;
        }
        else if ((axis1 == 0.0) && (axis2 == 0.0) && (axis3 < 0.0))
        {
            axis3 = -axis3;
            angle = -angle;
        }
        // TODO: restore twist, probably by multiplying rotation <0 0 -1 angle>

// Xj3D: org.web3d.vrml.scripting.ecmascript.builtin;
        
//    public SFRotation jsFunction_multiply(Scriptable sc) {
//        if (!(sc instanceof SFRotation)) {
//                Context.reportRuntimeError(INVALID_TYPE_MSG);
//        }
//        SFRotation rot = (SFRotation) sc;
//
//        SFRotation ret_val = new SFRotation();
//
//        ret_val.x = angle * rot.x + x * rot.angle + y * rot.z - z * rot.y;
//        ret_val.y = angle * rot.y + y * rot.angle + z * rot.x - x * rot.z;
//        ret_val.z = angle * rot.z + z * rot.angle + x * rot.y - y * rot.x;
//        ret_val.angle = angle * rot.angle - x * rot.x - y * rot.y - z * rot.z;

        // display, save results on interface
        headsUpRotationMode = headsUpRotationRadioButton.isSelected();
        if (headsUpRotationMode)
        {
			orientationXaxisTF.setText(String.valueOf(axisAngleTwisted.x));
			orientationYaxisTF.setText(String.valueOf(axisAngleTwisted.y));
			orientationZaxisTF.setText(String.valueOf(axisAngleTwisted.z));
            orientationAngleTF.setText(String.valueOf(axisAngleTwisted.angle));
        }
        else // great-circle rotation
        {
            orientationXaxisTF.setText(String.valueOf(axis1));
            orientationYaxisTF.setText(String.valueOf(axis2));
            orientationZaxisTF.setText(String.valueOf(axis3));
            orientationAngleTF.setText(String.valueOf(angle));
        }
        // normalize results
        normalizeRotationValuesButtonActionPerformed (evt);
        highlightDisplayViewpointCalculatorFields ();
        
        // assign centerOfRotation to match
        centerOfRotationXTF.setText(targetXTF.getText().trim());
        centerOfRotationYTF.setText(targetYTF.getText().trim());
        centerOfRotationZTF.setText(targetZTF.getText().trim());
		
		updateLookatPointAsComment();
}
private void updateLookatPointAsComment()
{
	targetX = Double.parseDouble(targetXTF.getText());
	targetY = Double.parseDouble(targetYTF.getText());
	targetZ = Double.parseDouble(targetZTF.getText());
	boolean nonzero = (targetX != 0.0) || (targetY != 0.0) || (targetZ != 0.0);
	String lookatComment = "<!-- " + LOOKAT_COMMENT_HEADER + " " +
				targetXTF.getText().trim() + " " +
				targetYTF.getText().trim() + " " +
				targetZTF.getText().trim() + " -->";
	
	if      (isIncludeLookatPointAsComment() && nonzero && viewpoint.hasContent("<Metadata"))
		     viewpoint.setContent(lookatComment + // prepend
					 viewpoint.getContent().substring(viewpoint.getContent().indexOf("<Metadata")));
	else if (isIncludeLookatPointAsComment() && nonzero)
		     viewpoint.setContent(lookatComment);
	else if (!isIncludeLookatPointAsComment() && viewpoint.hasContent("<Metadata"))
		     viewpoint.setContent(
					 viewpoint.getContent().substring(viewpoint.getContent().indexOf("<Metadata")));
	else if (!isIncludeLookatPointAsComment() && viewpoint.hasContent(LOOKAT_COMMENT_HEADER))
		     viewpoint.setContent(""); // clear
}
private void highlightDisplayViewpointCalculatorFields ()
{
        // reset other display fields
        Color highlightForegroundColor = authorAssistLabel.getForeground();
        // similiar color for background but brighter
        Color fieldBackgroundColor = new Color (255 - (255 - highlightForegroundColor.getRed())   / 4,
                                                255 - (255 - highlightForegroundColor.getGreen()) / 4,
                                                255 - (255 - highlightForegroundColor.getBlue())  / 4);
        
       centerOfRotationLabel.setFont(positionLabel.getFont().deriveFont(Font.BOLD));
            orientationLabel.setFont(positionLabel.getFont().deriveFont(Font.BOLD));
           goalAimPointLabel.setFont(positionLabel.getFont().deriveFont(Font.BOLD));
             twistAngleLabel.setFont(positionLabel.getFont().deriveFont(Font.BOLD));
             
       centerOfRotationLabel.setForeground(highlightForegroundColor);
            orientationLabel.setForeground(highlightForegroundColor);
          orientationXaxisTF.setForeground(highlightForegroundColor);
          orientationYaxisTF.setForeground(highlightForegroundColor);
          orientationZaxisTF.setForeground(highlightForegroundColor);
          orientationAngleTF.setForeground(highlightForegroundColor);
         centerOfRotationXTF.setForeground(highlightForegroundColor);
         centerOfRotationYTF.setForeground(highlightForegroundColor);
         centerOfRotationZTF.setForeground(highlightForegroundColor);
         
          orientationXaxisTF.setBackground(fieldBackgroundColor);
          orientationYaxisTF.setBackground(fieldBackgroundColor);
          orientationZaxisTF.setBackground(fieldBackgroundColor);
          orientationAngleTF.setBackground(fieldBackgroundColor);
         centerOfRotationXTF.setBackground(fieldBackgroundColor);
         centerOfRotationYTF.setBackground(fieldBackgroundColor);
         centerOfRotationZTF.setBackground(fieldBackgroundColor);
         
       if (!isIncludeLookatPointAsComment()) // only highlight background if saved
          fieldBackgroundColor = descriptionTA.getBackground();
       
                   targetXTF.setBackground(fieldBackgroundColor);
                   targetYTF.setBackground(fieldBackgroundColor);
                   targetZTF.setBackground(fieldBackgroundColor);
             twistAngleLabel.setBackground(fieldBackgroundColor);
}
private void resetDisplayViewpointCalculatorFields ()
{
        // reset other display fields
        Color fieldForegroundColor = descriptionTA.getForeground();
        Color fieldBackgroundColor = descriptionTA.getBackground();
        
       centerOfRotationLabel.setFont(positionLabel.getFont().deriveFont(Font.PLAIN));
            orientationLabel.setFont(positionLabel.getFont().deriveFont(Font.PLAIN));
           goalAimPointLabel.setFont(positionLabel.getFont().deriveFont(Font.PLAIN));
             twistAngleLabel.setFont(positionLabel.getFont().deriveFont(Font.PLAIN));
             
       centerOfRotationLabel.setForeground(fieldForegroundColor);
            orientationLabel.setForeground(fieldForegroundColor);
          orientationXaxisTF.setForeground(fieldForegroundColor);
          orientationYaxisTF.setForeground(fieldForegroundColor);
          orientationZaxisTF.setForeground(fieldForegroundColor);
          orientationAngleTF.setForeground(fieldForegroundColor);
         centerOfRotationXTF.setForeground(fieldForegroundColor);
         centerOfRotationYTF.setForeground(fieldForegroundColor);
         centerOfRotationZTF.setForeground(fieldForegroundColor);
          
          orientationXaxisTF.setBackground(fieldBackgroundColor);
          orientationYaxisTF.setBackground(fieldBackgroundColor);
          orientationZaxisTF.setBackground(fieldBackgroundColor);
          orientationAngleTF.setBackground(fieldBackgroundColor);
         centerOfRotationXTF.setBackground(fieldBackgroundColor);
         centerOfRotationYTF.setBackground(fieldBackgroundColor);
         centerOfRotationZTF.setBackground(fieldBackgroundColor);
                   targetXTF.setBackground(fieldBackgroundColor);
                   targetYTF.setBackground(fieldBackgroundColor);
                   targetZTF.setBackground(fieldBackgroundColor);
             twistAngleLabel.setBackground(fieldBackgroundColor);
}//GEN-LAST:event_recomputeLookatRotationButtonActionPerformed

    private void headsUpRotationRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_headsUpRotationRadioButtonActionPerformed
        headsUpRotationMode = headsUpRotationRadioButton.isSelected();
    }//GEN-LAST:event_headsUpRotationRadioButtonActionPerformed

    private void twistAngleTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_twistAngleTFActionPerformed
        checkAngles (false);
        computeDistanceAngleValues ();
        twistAngle = new SFFloat(twistAngleTF.getText()).getValue();
        twistAngleTF.setToolTipText(radiansFormat.format(twistAngle) + " radians = " + radiansFormat.format(twistAngle * 180.0 / Math.PI) + " degrees");
        resetDisplayViewpointCalculatorFields ();
    }//GEN-LAST:event_twistAngleTFActionPerformed

    private void targetXTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_targetXTFActionPerformed
        computeDistanceAngleValues ();
        resetDisplayViewpointCalculatorFields ();
    }//GEN-LAST:event_targetXTFActionPerformed

    private void targetYTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_targetYTFActionPerformed
        computeDistanceAngleValues ();
        resetDisplayViewpointCalculatorFields ();
    }//GEN-LAST:event_targetYTFActionPerformed

    private void targetZTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_targetZTFActionPerformed
        computeDistanceAngleValues ();
        resetDisplayViewpointCalculatorFields ();
    }//GEN-LAST:event_targetZTFActionPerformed

    private void slantRangeTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_slantRangeTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_slantRangeTFActionPerformed

    private void horizontalRangeTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_horizontalRangeTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_horizontalRangeTFActionPerformed

    private void verticalRangeTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verticalRangeTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_verticalRangeTFActionPerformed

    private void horizontalAngleTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_horizontalAngleTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_horizontalAngleTFActionPerformed

    private void verticalAngleTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_verticalAngleTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_verticalAngleTFActionPerformed

    private void resetAimPointButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetAimPointButtonActionPerformed
            targetXTF.setText("0");
            targetYTF.setText("0");
            targetZTF.setText("0");
         twistAngleTF.setText("0");
        
        // assign centerOfRotation to match
        centerOfRotationXTF.setText("0");
        centerOfRotationYTF.setText("0");
        centerOfRotationZTF.setText("0");
        
    headsUpRotationRadioButton.setSelected(true);
    computeDistanceAngleValues ();
    resetDisplayViewpointCalculatorFields ();
    }//GEN-LAST:event_resetAimPointButtonActionPerformed

    private void positionXTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_positionXTFActionPerformed
        resetDisplayViewpointCalculatorFields ();
    }//GEN-LAST:event_positionXTFActionPerformed

    private void positionYTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_positionYTFActionPerformed
        resetDisplayViewpointCalculatorFields ();
    }//GEN-LAST:event_positionYTFActionPerformed

    private void positionZTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_positionZTFActionPerformed
        resetDisplayViewpointCalculatorFields ();
    }//GEN-LAST:event_positionZTFActionPerformed

    private void orientationXaxisTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orientationXaxisTFActionPerformed
        resetDisplayViewpointCalculatorFields ();
    }//GEN-LAST:event_orientationXaxisTFActionPerformed

    private void orientationYaxisTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orientationYaxisTFActionPerformed
        resetDisplayViewpointCalculatorFields ();
    }//GEN-LAST:event_orientationYaxisTFActionPerformed

    private void orientationZaxisTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orientationZaxisTFActionPerformed
        resetDisplayViewpointCalculatorFields ();
    }//GEN-LAST:event_orientationZaxisTFActionPerformed

    private void levelHeightButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_levelHeightButtonActionPerformed
        try {
            if (positionYTF.getText().length() > 0)
            {
                double validValueCheck = Double.parseDouble(positionYTF.getText()); // check for exception
                targetYTF.setText(positionYTF.getText());
            }
        }
        catch (NumberFormatException e)
        {
            // bad value ignored
        }
        // assign centerOfRotation to match
        centerOfRotationYTF.setText(targetYTF.getText().trim());
    }//GEN-LAST:event_levelHeightButtonActionPerformed

    private void centerOfRotationXTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_centerOfRotationXTFActionPerformed
        resetDisplayViewpointCalculatorFields ();
    }//GEN-LAST:event_centerOfRotationXTFActionPerformed

    private void centerOfRotationYTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_centerOfRotationYTFActionPerformed
        resetDisplayViewpointCalculatorFields ();
    }//GEN-LAST:event_centerOfRotationYTFActionPerformed

    private void centerOfRotationZTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_centerOfRotationZTFActionPerformed
        resetDisplayViewpointCalculatorFields ();
    }//GEN-LAST:event_centerOfRotationZTFActionPerformed

    private void rotationCalculatorlButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rotationCalculatorlButtonActionPerformed
        RotationCalculatorPanel orientationCalculatorPanel = new RotationCalculatorPanel(viewpoint, "orientation");
        orientationCalculatorPanel.setRotationValue (
           orientationXaxisTF.getText(),
           orientationYaxisTF.getText(),
           orientationZaxisTF.getText(),
           orientationAngleTF.getText());
        DialogDescriptor dd = new DialogDescriptor(orientationCalculatorPanel, "Rotation Calculator for Viewpoint orientation");
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
    }//GEN-LAST:event_rotationCalculatorlButtonActionPerformed

    private void includeLookatPointAsCommentCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_includeLookatPointAsCommentCheckBoxActionPerformed
        setIncludeLookatPointAsComment(includeLookatPointAsCommentCheckBox.isSelected()); // remember static variable for this session
		updateLookatPointAsComment();
        highlightDisplayViewpointCalculatorFields ();
    }//GEN-LAST:event_includeLookatPointAsCommentCheckBoxActionPerformed
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel adjustmentsLabel;
    private javax.swing.JLabel angleLabel;
    private javax.swing.JLabel authorAssistLabel;
    private javax.swing.JSeparator authorAssistSeparator;
    private javax.swing.JLabel centerOfRotationLabel;
    private javax.swing.JTextField centerOfRotationXTF;
    private javax.swing.JTextField centerOfRotationYTF;
    private javax.swing.JTextField centerOfRotationZTF;
    private org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel1;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JScrollPane descriptionScrollPane;
    private javax.swing.JTextArea descriptionTA;
    private javax.swing.JLabel fieldOfViewLabel;
    private javax.swing.JTextField fieldOfViewTF;
    private javax.swing.JLabel goalAimPointLabel;
    private javax.swing.JRadioButton greatCircleRotationRadioButton;
    private javax.swing.JRadioButton headsUpRotationRadioButton;
    private javax.swing.JLabel hintLabel;
    private javax.swing.JLabel horizontalAngleLabel;
    private javax.swing.JTextField horizontalAngleTF;
    private javax.swing.JLabel horizontalRangeLabel;
    private javax.swing.JTextField horizontalRangeTF;
    private javax.swing.JCheckBox includeLookatPointAsCommentCheckBox;
    private javax.swing.JCheckBox jumpCB;
    private javax.swing.JLabel jumpLabel;
    private javax.swing.JButton levelHeightButton;
    private javax.swing.JButton normalizeRotationValuesButton;
    private javax.swing.JTextField orientationAngleTF;
    private javax.swing.JLabel orientationLabel;
    private javax.swing.JTextField orientationXaxisTF;
    private javax.swing.JTextField orientationYaxisTF;
    private javax.swing.JTextField orientationZaxisTF;
    private javax.swing.JLabel positionLabel;
    private javax.swing.JTextField positionXTF;
    private javax.swing.JTextField positionYTF;
    private javax.swing.JTextField positionZTF;
    private javax.swing.JButton recomputeLookatRotationButton;
    private javax.swing.JButton resetAimPointButton;
    private javax.swing.JCheckBox retainUserOffsetsCB;
    private javax.swing.JButton rotationCalculatorlButton;
    private javax.swing.ButtonGroup rotationModeButtonGroup;
    private javax.swing.JLabel slantRangeLabel;
    private javax.swing.JTextField slantRangeTF;
    private javax.swing.JLabel spacerLabel;
    private javax.swing.JTextField targetXTF;
    private javax.swing.JTextField targetYTF;
    private javax.swing.JTextField targetZTF;
    private javax.swing.JLabel twistAngleLabel;
    private javax.swing.JTextField twistAngleTF;
    private javax.swing.JLabel verticalAngleLabel;
    private javax.swing.JTextField verticalAngleTF;
    private javax.swing.JLabel verticalRangeLabel;
    private javax.swing.JTextField verticalRangeTF;
    private javax.swing.JLabel xLabel;
    private javax.swing.JLabel yLabel;
    private javax.swing.JLabel zLabel;
    // End of variables declaration//GEN-END:variables
  
  @Override
  public String getNameKey()
  {
    return "NAME_X3D_VIEWPOINT";
  }

  private void computeDistanceAngleValues ()
  {
        if ( positionXTF.getText().trim().isEmpty()) positionXTF.setText("0");
        if ( positionYTF.getText().trim().isEmpty()) positionYTF.setText("0");
        if ( positionZTF.getText().trim().isEmpty()) positionZTF.setText("0");
        if (   targetXTF.getText().trim().isEmpty())   targetXTF.setText("0");
        if (   targetYTF.getText().trim().isEmpty())   targetYTF.setText("0");
        if (   targetZTF.getText().trim().isEmpty())   targetZTF.setText("0");
        if (twistAngleTF.getText().trim().isEmpty())twistAngleTF.setText("0");

        positionX = new SFFloat(positionXTF.getText()).getValue();
        positionY = new SFFloat(positionYTF.getText()).getValue();
        positionZ = new SFFloat(positionZTF.getText()).getValue();
        targetX   = new SFFloat(targetXTF.getText()).getValue(); // this also saves local panel parameters
        targetY   = new SFFloat(targetYTF.getText()).getValue();
        targetZ   = new SFFloat(targetZTF.getText()).getValue();
        twistAngle= new SFFloat(twistAngleTF.getText()).getValue();
        deltaX    = targetX - positionX;
        deltaY    = targetY - positionY;
        deltaZ    = targetZ - positionZ;

        horizontalDistance = Math.sqrt (deltaX*deltaX                 + deltaZ*deltaZ);
                  distance = Math.sqrt (deltaX*deltaX + deltaY*deltaY + deltaZ*deltaZ);

        if (horizontalDistance==0.0)
             hAngle = 0.0;
        else hAngle = Math.atan2 (-deltaZ, deltaX) - (Math.PI / 2.0); // atan2 measures from X axis, apply offset for view -Z axis
        while  (hAngle <  0.0)           hAngle += 2.0 * Math.PI; // normalize
        while  (hAngle >= 2.0 * Math.PI) hAngle -= 2.0 * Math.PI;
        if (deltaY==0.0)
             pAngle = 0.0;
        else pAngle = Math.atan2 (deltaY, horizontalDistance);
        while (pAngle <  Math.PI / 2.0)  pAngle += 2.0 * Math.PI; // normalize
        while (pAngle >= Math.PI / 2.0)  pAngle -= 2.0 * Math.PI;

             slantRangeTF.setText(singleDigitFormat.format(distance) + " m");
        horizontalRangeTF.setText(singleDigitFormat.format(horizontalDistance) + " m");
          verticalRangeTF.setText(singleDigitFormat.format(deltaY) + " m");

             twistAngleTF.setText       (radiansFormat.format(twistAngle));
             twistAngleTF.setToolTipText(radiansFormat.format(twistAngle) + " radians = " +
                                         singleDigitFormat.format(twistAngle * 180.0 / Math.PI) + " degrees");
        horizontalAngleTF.setText       (radiansFormat.format(hAngle));
        horizontalAngleTF.setToolTipText(radiansFormat.format(hAngle) + " radians = " +
                                         singleDigitFormat.format(hAngle * 180.0 / Math.PI) + " degrees");
          verticalAngleTF.setText       (radiansFormat.format(pAngle));
          verticalAngleTF.setToolTipText(radiansFormat.format(pAngle) + " radians = " +
                                         singleDigitFormat.format(pAngle * 180.0 / Math.PI) + " degrees");
  }

  private void checkAngles(boolean precedesNormalization)
  {
      // indicate degree values in tooltips
      // usability note:  can enter degree values (-6..+6) as (354..366) to provoke this conversion check
      double angle = new SFFloat(orientationAngleTF.getText()).getValue();
      orientationAngleTF.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");
      if (Math.abs(angle) > 2.0 * Math.PI)
      {
            String message;
            message = "<html><center>Large value provided for <b>orientation</b> angle, which is a radians value.<br/><br/>Convert <b>" + angle + " degrees</b> to <b>" +
                    radiansFormat.format((angle % 360.0) * Math.PI / 180.0) + " radians</b>";
            if (precedesNormalization)
                 message += " before normalization?";
            else message += "?";
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  message, "X3D angles are in radians", NotifyDescriptor.YES_NO_OPTION);
          if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
          {
              angle = (angle % 360.0) * Math.PI / 180.0;
              orientationAngleTF.setText(radiansFormat.format(angle));
              orientationAngleTF.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");
          }
      }
      angle = new SFFloat(fieldOfViewTF.getText()).getValue();
      fieldOfViewTF.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");
      if (Math.abs(angle) > 2.0 * Math.PI)
      {
            String message;
            message = "<html><center>Large value provided for <b>fieldOfView</b> angle, which is a radians value.<br/><br/>Convert <b>" + angle + " degrees</b> to <b>" +
                    radiansFormat.format((angle % 360.0) * Math.PI / 180.0) + " radians</b>";
            if (precedesNormalization)
                 message += " before normalization?";
            else message += "?";
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  message, "X3D angles are in radians", NotifyDescriptor.YES_NO_OPTION);
          if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
          {
              angle = (angle % 360.0) * Math.PI / 180.0;
              fieldOfViewTF.setText(radiansFormat.format(angle));
              fieldOfViewTF.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");
          }
      }
      angle = new SFFloat(twistAngleTF.getText()).getValue();
      twistAngleTF.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");
      if (Math.abs(angle) > 2.0 * Math.PI)
      {
            String message;
            message = "<html><center>Large value provided for <b>twist</b> angle, which is a radians value.<br/><br/>Convert <b>" + angle + " degrees</b> to <b>" +
                    radiansFormat.format((angle % 360.0) * Math.PI / 180.0) + " radians</b>?";
//            if (precedesNormalization)
//                 message += " before normalization?";
//            else message += "?";
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  message, "X3D angles are in radians", NotifyDescriptor.YES_NO_OPTION);
          if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
          {
              angle = (angle % 360.0) * Math.PI / 180.0;
              twistAngleTF.setText(radiansFormat.format(angle));
              twistAngleTF.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");
          }
      }
  }

  @Override
  public void unloadInput() throws IllegalArgumentException
  {
    checkAngles (false);
    unLoadDEFUSE();
    
    viewpoint.setCenterOfRotationX(centerOfRotationXTF.getText().trim());
    viewpoint.setCenterOfRotationY(centerOfRotationYTF.getText().trim());
    viewpoint.setCenterOfRotationZ(centerOfRotationZTF.getText().trim());
    viewpoint.setDescription(descriptionTA.getText().trim());
	
    viewpoint.setFieldOfView(fieldOfViewTF.getText().trim());
    viewpoint.setJump(jumpCB.isSelected());
    viewpoint.setRetainUserOffsets(retainUserOffsetsCB.isSelected());
    viewpoint.setOrientationX    (orientationXaxisTF.getText().trim());
    viewpoint.setOrientationY    (orientationYaxisTF.getText().trim());
    viewpoint.setOrientationZ    (orientationZaxisTF.getText().trim());
    viewpoint.setOrientationAngle(orientationAngleTF.getText().trim());
    if (viewpoint.getOrientationX().equals("0") && viewpoint.getOrientationY().equals("1") && viewpoint.getOrientationZ().equals("0") &&
        viewpoint.getOrientationAngle().equals("0"))
    {
        // restore X3D default vice preferred default
        viewpoint.setOrientationY("0");
        viewpoint.setOrientationZ("1");
    }
    viewpoint.setPositionX(positionXTF.getText().trim());
    viewpoint.setPositionY(positionYTF.getText().trim());
    viewpoint.setPositionZ(positionZTF.getText().trim());

    // viewpoint computation assist parameters saved separately as local variables, not Viewpoint attributes
       targetX = new SFFloat(targetXTF.getText()).getValue();
       targetY = new SFFloat(targetYTF.getText()).getValue();
       targetZ = new SFFloat(targetZTF.getText()).getValue();
    twistAngle = new SFFloat(twistAngleTF.getText()).getValue();
    headsUpRotationMode = headsUpRotationRadioButton.isSelected();
  }

	/**
	 * @return the includeLookatPointAsComment
	 */
	public static boolean isIncludeLookatPointAsComment() {
		return includeLookatPointAsComment;
	}

	/**
	 * @param aIncludeLookatPointAsComment the includeLookatPointAsComment to set
	 */
	public static void setIncludeLookatPointAsComment(boolean aIncludeLookatPointAsComment) {
		includeLookatPointAsComment = aIncludeLookatPointAsComment;
	}
}
