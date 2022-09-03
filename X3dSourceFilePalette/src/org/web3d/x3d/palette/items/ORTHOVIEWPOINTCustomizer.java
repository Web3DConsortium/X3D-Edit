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
 * ORTHOVIEWPOINTCustomizer.java
 * Created on 9 July 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */

public class ORTHOVIEWPOINTCustomizer extends BaseCustomizer
{
  private ORTHOVIEWPOINT orthoViewpoint;
  private JTextComponent target;
  // persist panel settings between invocations
  private static double  targetX, targetY, targetZ, twistAngle = 0.0;
  private static boolean headsUpRotationMode = true;
  private double positionX, positionY, positionZ, deltaX, deltaY, deltaZ, horizontalDistance, distance;
  private double hAngle, pAngle = 0.0; // horizontal angle from -Z axis, pitch angle above/below Y=0 plane
  
  /** Creates new form ORTHOVIEWPOINTCustomizer
   * @param orthoViewpoint
   * @param target
   */
  public ORTHOVIEWPOINTCustomizer(ORTHOVIEWPOINT orthoViewpoint, JTextComponent target)
  {
    super(orthoViewpoint);
    this.orthoViewpoint = orthoViewpoint;
    this.target = target;
                                 
    HelpCtx.setHelpIDString(this, "ORTHOVIEWPOINT_ELEM_HELPID");

    orthoViewpoint.setVisualizationSelectionAvailable(true); // must precede initComponents() interface initialization
    orthoViewpoint.setVisualizationTooltip("Add wireframe and transparent ViewFrustum to show OrthoViewpoint coverage");
    
    initComponents();
    
    centerOfRotationXTF.setText(orthoViewpoint.getCenterOfRotationX());
    centerOfRotationYTF.setText(orthoViewpoint.getCenterOfRotationY());
    centerOfRotationZTF.setText(orthoViewpoint.getCenterOfRotationZ());
    
    descriptionTA.setText(orthoViewpoint.getDescription());
    // fieldOfView rectangle
    minXTF.setText(orthoViewpoint.getMinX());
    minYTF.setText(orthoViewpoint.getMinY());
    maxXTF.setText(orthoViewpoint.getMaxX());
    maxYTF.setText(orthoViewpoint.getMaxY());
                 jumpCB.setSelected(orthoViewpoint.isJump());
    retainUserOffsetsCB.setSelected(orthoViewpoint.isRetainUserOffsets());
    
    orientationAngleTF.setText(orthoViewpoint.getOrientationAngle());
    orientationXaxisTF.setText(orthoViewpoint.getOrientationX());
    orientationYaxisTF.setText(orthoViewpoint.getOrientationY());
    orientationZaxisTF.setText(orthoViewpoint.getOrientationZ());
    if (orthoViewpoint.getOrientationX().equals("0") && orthoViewpoint.getOrientationY().equals("0") && orthoViewpoint.getOrientationZ().equals("1") &&
        orthoViewpoint.getOrientationAngle().equals("0"))
    {
        // use preferred default vice X3D default
        orientationYaxisTF.setText("1");
        orientationZaxisTF.setText("0");
    }
    
    positionXTF.setText(orthoViewpoint.getPositionX());
    positionYTF.setText(orthoViewpoint.getPositionY());
    positionZTF.setText(orthoViewpoint.getPositionZ());

            targetXTF.setText(String.valueOf(targetX));
            targetYTF.setText(String.valueOf(targetY));
            targetZTF.setText(String.valueOf(targetZ));
         twistAngleTF.setText(String.valueOf(twistAngle));
    horizontalAngleTF.setText(String.valueOf(hAngle));
      verticalAngleTF.setText(String.valueOf(pAngle));

    if (headsUpRotationMode)
         headsUpRotationRadioButton.setSelected(true);
    else greatCircleRotationRadioButton.setSelected(true);
    headsUpRotationRadioButton.updateUI();

    computeDistanceAngleValues ();
    checkAngles (false);
    
    // test if centerOfRotation not level (using TF instead of original value due to axis swap above)
    if ((orientationXaxisTF.getText().equals("0") && orientationYaxisTF.getText().equals("1") && orientationZaxisTF.getText().equals("0")) &&
			(new SFFloat(orthoViewpoint.getCenterOfRotationY()).getValue() != (new SFFloat(orthoViewpoint.getPositionY()).getValue())))
    {
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  "<html><p>OrthoViewpoint centerOfRotation height differs from position height</p>" +
						"<p>Match height so that examination rotation doesn't jump?</p></html>", "OrthoViewpoint centerOfRotation check", NotifyDescriptor.YES_NO_OPTION);
          if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
          {
              centerOfRotationYTF.setText(orthoViewpoint.getPositionY());
          } 
    }
    targetXTF.setText(centerOfRotationXTF.getText());
    targetYTF.setText(centerOfRotationYTF.getText());
    targetZTF.setText(centerOfRotationZTF.getText());
	
	includeLookatPointAsCommentCheckBox.setSelected(VIEWPOINTCustomizer.isIncludeLookatPointAsComment() || orthoViewpoint.getContent().contains(VIEWPOINTCustomizer.LOOKAT_COMMENT_HEADER));
	if (orthoViewpoint.getContent().contains(VIEWPOINTCustomizer.LOOKAT_COMMENT_HEADER))
	{
		String lookatValuesString = orthoViewpoint.getContent().substring(
				orthoViewpoint.getContent().indexOf(VIEWPOINTCustomizer.LOOKAT_COMMENT_HEADER) + VIEWPOINTCustomizer.LOOKAT_COMMENT_HEADER.length(),
				orthoViewpoint.getContent().indexOf("-->")).trim();
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

        dEFUSEpanel1 = getDEFUSEpanel();
        descriptionLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
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
        jumpLabel = new javax.swing.JLabel();
        jumpCB = new javax.swing.JCheckBox();
        orientationLabel = new javax.swing.JLabel();
        orientationXaxisTF = new javax.swing.JTextField();
        orientationYaxisTF = new javax.swing.JTextField();
        orientationZaxisTF = new javax.swing.JTextField();
        orientationAngleTF = new javax.swing.JTextField();
        normalizeRotationValuesButton = new javax.swing.JButton();
        centerOfRotationLabel = new javax.swing.JLabel();
        centerOfRotationXTF = new javax.swing.JTextField();
        centerOfRotationYTF = new javax.swing.JTextField();
        centerOfRotationZTF = new javax.swing.JTextField();
        retainUserOffsetsLabel = new javax.swing.JLabel();
        retainUserOffsetsCB = new javax.swing.JCheckBox();
        fieldOfViewLabel = new javax.swing.JLabel();
        minXTF = new javax.swing.JTextField();
        minXLabel = new javax.swing.JLabel();
        minYLabel = new javax.swing.JLabel();
        minYTF = new javax.swing.JTextField();
        maxXLabel = new javax.swing.JLabel();
        maxXTF = new javax.swing.JTextField();
        maxYLabel = new javax.swing.JLabel();
        maxYTF = new javax.swing.JTextField();
        authorAssistSeparator = new javax.swing.JSeparator();
        authorAssistLabel = new javax.swing.JLabel();
        targetXTF = new javax.swing.JTextField();
        targetYTF = new javax.swing.JTextField();
        targetZTF = new javax.swing.JTextField();
        recomputeLookatRotationButton = new javax.swing.JButton();
        goalAimPointLabel = new javax.swing.JLabel();
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
        levelHeightButton = new javax.swing.JButton();
        clearAimPointButton = new javax.swing.JButton();
        rotationCalculatorlButton = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel1, gridBagConstraints);

        descriptionLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        descriptionLabel.setText("description");
        descriptionLabel.setToolTipText("Text description or navigation hint identifying this Viewpoint. Hint: use spaces, make descriptions clear and readable. ");
        descriptionLabel.setMaximumSize(null);
        descriptionLabel.setMinimumSize(new java.awt.Dimension(80, 18));
        descriptionLabel.setOpaque(true);
        descriptionLabel.setPreferredSize(new java.awt.Dimension(80, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(descriptionLabel, gridBagConstraints);

        jScrollPane1.setMaximumSize(null);
        jScrollPane1.setMinimumSize(new java.awt.Dimension(30, 20));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(30, 20));

        descriptionTA.setColumns(1);
        descriptionTA.setRows(1);
        descriptionTA.setToolTipText("Text description or navigation hint identifying this Viewpoint. Hint: use spaces, make descriptions clear and readable. ");
        descriptionTA.setMinimumSize(new java.awt.Dimension(60, 60));
        descriptionTA.setPreferredSize(new java.awt.Dimension(60, 60));
        jScrollPane1.setViewportView(descriptionTA);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 0.8;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(jScrollPane1, gridBagConstraints);

        xLabel.setText("x");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(8, 3, 3, 3);
        add(xLabel, gridBagConstraints);

        yLabel.setText("y");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(8, 3, 3, 3);
        add(yLabel, gridBagConstraints);

        zLabel.setText("z");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(8, 3, 3, 3);
        add(zLabel, gridBagConstraints);

        angleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        angleLabel.setText("angle");
        angleLabel.setToolTipText("angle in radians (can convert large degree values if > 6.28)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
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
        positionLabel.setToolTipText("location (x, y, z in meters) relative to local coordinate system");
        positionLabel.setMaximumSize(null);
        positionLabel.setMinimumSize(new java.awt.Dimension(80, 18));
        positionLabel.setPreferredSize(new java.awt.Dimension(80, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(positionLabel, gridBagConstraints);

        positionXTF.setToolTipText("location (x, y, z in meters) relative to local coordinate system");
        positionXTF.setMaximumSize(null);
        positionXTF.setMinimumSize(new java.awt.Dimension(60, 22));
        positionXTF.setPreferredSize(new java.awt.Dimension(60, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(positionXTF, gridBagConstraints);

        positionYTF.setToolTipText("location (x, y, z in meters) relative to local coordinate system");
        positionYTF.setMaximumSize(null);
        positionYTF.setMinimumSize(new java.awt.Dimension(60, 22));
        positionYTF.setPreferredSize(new java.awt.Dimension(60, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(positionYTF, gridBagConstraints);

        positionZTF.setToolTipText("location (x, y, z in meters) relative to local coordinate system");
        positionZTF.setMaximumSize(null);
        positionZTF.setMinimumSize(new java.awt.Dimension(60, 22));
        positionZTF.setPreferredSize(new java.awt.Dimension(60, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(positionZTF, gridBagConstraints);

        jumpLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jumpLabel.setText("jump");
        jumpLabel.setToolTipText("Transition instantly by jumping, or smoothly adjust offsets in place when changing to this Viewpoint");
        jumpLabel.setMaximumSize(null);
        jumpLabel.setMinimumSize(new java.awt.Dimension(80, 18));
        jumpLabel.setPreferredSize(new java.awt.Dimension(80, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(jumpLabel, gridBagConstraints);

        jumpCB.setToolTipText("Transition instantly by jumping, or smoothly adjust offsets in place when changing to this Viewpoint");
        jumpCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        jumpCB.setMaximumSize(null);
        jumpCB.setMinimumSize(new java.awt.Dimension(70, 18));
        jumpCB.setPreferredSize(new java.awt.Dimension(70, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(jumpCB, gridBagConstraints);

        orientationLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        orientationLabel.setText("orientation");
        orientationLabel.setToolTipText("rotation (axis, angle in radians) of Viewpoint, relative to default -Z axis direction in local coordinate system");
        orientationLabel.setMaximumSize(null);
        orientationLabel.setMinimumSize(new java.awt.Dimension(80, 18));
        orientationLabel.setPreferredSize(new java.awt.Dimension(80, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(orientationLabel, gridBagConstraints);

        orientationXaxisTF.setToolTipText("rotation (axis, angle in radians) of Viewpoint, relative to default -Z axis direction in local coordinate system");
        orientationXaxisTF.setMaximumSize(null);
        orientationXaxisTF.setMinimumSize(new java.awt.Dimension(60, 22));
        orientationXaxisTF.setPreferredSize(new java.awt.Dimension(60, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(orientationXaxisTF, gridBagConstraints);

        orientationYaxisTF.setToolTipText("rotation (axis, angle in radians) of Viewpoint, relative to default -Z axis direction in local coordinate system");
        orientationYaxisTF.setMaximumSize(null);
        orientationYaxisTF.setMinimumSize(new java.awt.Dimension(60, 22));
        orientationYaxisTF.setPreferredSize(new java.awt.Dimension(60, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(orientationYaxisTF, gridBagConstraints);

        orientationZaxisTF.setToolTipText("rotation (axis, angle in radians) of Viewpoint, relative to default -Z axis direction in local coordinate system");
        orientationZaxisTF.setMaximumSize(null);
        orientationZaxisTF.setMinimumSize(new java.awt.Dimension(60, 22));
        orientationZaxisTF.setPreferredSize(new java.awt.Dimension(60, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(orientationZaxisTF, gridBagConstraints);

        orientationAngleTF.setToolTipText("rotation (axis, angle in radians) of Viewpoint, relative to default -Z axis direction in local coordinate system");
        orientationAngleTF.setMaximumSize(null);
        orientationAngleTF.setMinimumSize(new java.awt.Dimension(60, 22));
        orientationAngleTF.setPreferredSize(new java.awt.Dimension(60, 22));
        orientationAngleTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orientationAngleTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(orientationAngleTF, gridBagConstraints);

        normalizeRotationValuesButton.setText("normalize orientation values");
        normalizeRotationValuesButton.setToolTipText("for orientation values, rescale axis values as normalized vector (unit length, ranges 0..1) and reset angles [0..2pi)");
        normalizeRotationValuesButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        normalizeRotationValuesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                normalizeRotationValuesButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(normalizeRotationValuesButton, gridBagConstraints);

        centerOfRotationLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        centerOfRotationLabel.setText("centerOfRotation");
        centerOfRotationLabel.setToolTipText("centerOfRotation point relates to NavigationInfo EXAMINE mode");
        centerOfRotationLabel.setMaximumSize(null);
        centerOfRotationLabel.setMinimumSize(new java.awt.Dimension(80, 18));
        centerOfRotationLabel.setPreferredSize(new java.awt.Dimension(80, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(centerOfRotationLabel, gridBagConstraints);

        centerOfRotationXTF.setToolTipText("centerOfRotation point relates to NavigationInfo EXAMINE mode");
        centerOfRotationXTF.setMaximumSize(null);
        centerOfRotationXTF.setMinimumSize(new java.awt.Dimension(60, 22));
        centerOfRotationXTF.setPreferredSize(new java.awt.Dimension(60, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(centerOfRotationXTF, gridBagConstraints);

        centerOfRotationYTF.setToolTipText("centerOfRotation point relates to NavigationInfo EXAMINE mode");
        centerOfRotationYTF.setMaximumSize(null);
        centerOfRotationYTF.setMinimumSize(new java.awt.Dimension(60, 22));
        centerOfRotationYTF.setPreferredSize(new java.awt.Dimension(60, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(centerOfRotationYTF, gridBagConstraints);

        centerOfRotationZTF.setToolTipText("centerOfRotation point relates to NavigationInfo EXAMINE mode");
        centerOfRotationZTF.setMaximumSize(null);
        centerOfRotationZTF.setMinimumSize(new java.awt.Dimension(60, 22));
        centerOfRotationZTF.setPreferredSize(new java.awt.Dimension(60, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(centerOfRotationZTF, gridBagConstraints);

        retainUserOffsetsLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        retainUserOffsetsLabel.setText("retainUserOffsets");
        retainUserOffsetsLabel.setToolTipText("retain (true) or reset to zero (false) any prior user navigation offsets from defined viewpoint position, orientation");
        retainUserOffsetsLabel.setMaximumSize(null);
        retainUserOffsetsLabel.setMinimumSize(new java.awt.Dimension(80, 18));
        retainUserOffsetsLabel.setPreferredSize(new java.awt.Dimension(80, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(retainUserOffsetsLabel, gridBagConstraints);

        retainUserOffsetsCB.setToolTipText("retain (true) or reset to zero (false) any prior user navigation offsets from defined viewpoint position, orientation");
        retainUserOffsetsCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        retainUserOffsetsCB.setMaximumSize(null);
        retainUserOffsetsCB.setMinimumSize(new java.awt.Dimension(70, 18));
        retainUserOffsetsCB.setPreferredSize(new java.awt.Dimension(70, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(retainUserOffsetsCB, gridBagConstraints);

        fieldOfViewLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        fieldOfViewLabel.setText("fieldOfView");
        fieldOfViewLabel.setToolTipText("Preferred minimum and maximum view extents  in local coordinate system units. Small field of view roughly corresponds to a telephoto lens, large field of view roughly corresponds to a wide-angle lens. ");
        fieldOfViewLabel.setMaximumSize(null);
        fieldOfViewLabel.setMinimumSize(new java.awt.Dimension(80, 18));
        fieldOfViewLabel.setPreferredSize(new java.awt.Dimension(80, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(fieldOfViewLabel, gridBagConstraints);

        minXTF.setToolTipText("Preferred minimum viewing angle from this viewpoint in radians. Small field of view roughly corresponds to a telephoto lens, large field of view roughly corresponds to a wide-angle lens. ");
        minXTF.setMaximumSize(null);
        minXTF.setMinimumSize(new java.awt.Dimension(60, 22));
        minXTF.setPreferredSize(new java.awt.Dimension(60, 22));
        minXTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minXTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(minXTF, gridBagConstraints);

        minXLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        minXLabel.setText("minX");
        minXLabel.setMinimumSize(new java.awt.Dimension(60, 22));
        minXLabel.setPreferredSize(new java.awt.Dimension(60, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(minXLabel, gridBagConstraints);

        minYLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        minYLabel.setText("minY");
        minYLabel.setMinimumSize(new java.awt.Dimension(60, 22));
        minYLabel.setPreferredSize(new java.awt.Dimension(60, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(minYLabel, gridBagConstraints);

        minYTF.setToolTipText("Preferred minimum viewing angle from this viewpoint in radians. Small field of view roughly corresponds to a telephoto lens, large field of view roughly corresponds to a wide-angle lens. ");
        minYTF.setMaximumSize(null);
        minYTF.setMinimumSize(new java.awt.Dimension(60, 22));
        minYTF.setPreferredSize(new java.awt.Dimension(60, 22));
        minYTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minYTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(minYTF, gridBagConstraints);

        maxXLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        maxXLabel.setText("maxX");
        maxXLabel.setMinimumSize(new java.awt.Dimension(60, 22));
        maxXLabel.setPreferredSize(new java.awt.Dimension(60, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(maxXLabel, gridBagConstraints);

        maxXTF.setToolTipText("Preferred minimum viewing angle from this viewpoint in radians. Small field of view roughly corresponds to a telephoto lens, large field of view roughly corresponds to a wide-angle lens. ");
        maxXTF.setMaximumSize(null);
        maxXTF.setMinimumSize(new java.awt.Dimension(60, 22));
        maxXTF.setPreferredSize(new java.awt.Dimension(60, 22));
        maxXTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maxXTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(maxXTF, gridBagConstraints);

        maxYLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        maxYLabel.setText("maxY");
        maxYLabel.setMinimumSize(new java.awt.Dimension(60, 22));
        maxYLabel.setPreferredSize(new java.awt.Dimension(60, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(maxYLabel, gridBagConstraints);

        maxYTF.setToolTipText("Preferred minimum viewing angle from this viewpoint in radians. Small field of view roughly corresponds to a telephoto lens, large field of view roughly corresponds to a wide-angle lens. ");
        maxYTF.setMaximumSize(null);
        maxYTF.setMinimumSize(new java.awt.Dimension(60, 22));
        maxYTF.setPreferredSize(new java.awt.Dimension(60, 22));
        maxYTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                maxYTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(maxYTF, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(authorAssistSeparator, gridBagConstraints);

        authorAssistLabel.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        authorAssistLabel.setForeground(new java.awt.Color(0, 102, 102));
        authorAssistLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        authorAssistLabel.setText("Viewpoint Rotation Calculator");
        authorAssistLabel.setToolTipText("Assist author to recompute orientation based on look-at point");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(authorAssistLabel, gridBagConstraints);

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
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
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
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
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
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(targetZTF, gridBagConstraints);

        recomputeLookatRotationButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        recomputeLookatRotationButton.setForeground(new java.awt.Color(0, 102, 102));
        recomputeLookatRotationButton.setText("recompute orientation for goal aim point");
        recomputeLookatRotationButton.setToolTipText("use goal aim point to recompute Viewpoint orientation");
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
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(recomputeLookatRotationButton, gridBagConstraints);

        goalAimPointLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        goalAimPointLabel.setForeground(new java.awt.Color(0, 102, 102));
        goalAimPointLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        goalAimPointLabel.setText("goal aim point");
        goalAimPointLabel.setToolTipText("set desired point to aim Viewpoint orientation");
        goalAimPointLabel.setMaximumSize(new java.awt.Dimension(200, 22));
        goalAimPointLabel.setMinimumSize(new java.awt.Dimension(130, 22));
        goalAimPointLabel.setPreferredSize(new java.awt.Dimension(130, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(goalAimPointLabel, gridBagConstraints);

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
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.2;
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
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(twistAngleTF, gridBagConstraints);

        headsUpRotationRadioButton.setSelected(true);
        headsUpRotationRadioButton.setText("heads-up rotation");
        headsUpRotationRadioButton.setToolTipText("first rotate in horizontal plane, then pitch up/down (preferred mode)");
        headsUpRotationRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                headsUpRotationRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(headsUpRotationRadioButton, gridBagConstraints);

        greatCircleRotationRadioButton.setText("direct-path rotation");
        greatCircleRotationRadioButton.setToolTipText("rotate via direct path from initial to final view direction, although result may be tilted obliquely from horizontal plane");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
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
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(includeLookatPointAsCommentCheckBox, gridBagConstraints);

        slantRangeLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        slantRangeLabel.setText("slant range");
        slantRangeLabel.setToolTipText("computed straight-line distance in meters between Viewpoint position and goal aim point");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
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
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(slantRangeTF, gridBagConstraints);

        horizontalRangeLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        horizontalRangeLabel.setText("horizontal range");
        horizontalRangeLabel.setToolTipText("computed horizontal distance in meters between Viewpoint position and goal aim point");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
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
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(horizontalRangeTF, gridBagConstraints);

        verticalRangeLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        verticalRangeLabel.setText("vertical range");
        verticalRangeLabel.setToolTipText("computed vertical distance in meters between Viewpoint position and goal aim point");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
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
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
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
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
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
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
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
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.2;
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
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 3, 3);
        add(verticalAngleTF, gridBagConstraints);

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
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(levelHeightButton, gridBagConstraints);

        clearAimPointButton.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        clearAimPointButton.setForeground(new java.awt.Color(0, 102, 102));
        clearAimPointButton.setText("reset");
        clearAimPointButton.setToolTipText("set look-at point to origin without changing Viewpoint values");
        clearAimPointButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearAimPointButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(clearAimPointButton, gridBagConstraints);

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
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(rotationCalculatorlButton, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void normalizeRotationValuesButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_normalizeRotationValuesButtonActionPerformed
    {//GEN-HEADEREND:event_normalizeRotationValuesButtonActionPerformed
        checkAngles(true);
        double normalizationFactor, x, y, z, angle;

        x     = new SFDouble(orientationXaxisTF.getText()).getValue();
        y     = new SFDouble(orientationYaxisTF.getText()).getValue();
        z     = new SFDouble(orientationZaxisTF.getText()).getValue();
        angle = new SFDouble(orientationAngleTF.getText()).getValue();

        if ((x <= 0.0) && (y <= 0.0) && (z <= 0.0))
        {
            // negate all components to produce simpler form since all axis values are non-positive
                x = -x;
                y = -y;
                z = -z;
            angle = -angle;
        }
        normalizationFactor = Math.sqrt(x * x + y * y + z * z);
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

        if (angle == -0.0)
        {
            angle = 0.0;
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

        // idiosyncracy/roundoff cleanup
        if (    orientationXaxisTF.getText().equals("-0") ||     orientationXaxisTF.getText().equals("-0.0"))     orientationXaxisTF.setText("0");
        if (    orientationYaxisTF.getText().equals("-0") ||     orientationYaxisTF.getText().equals("-0.0"))     orientationYaxisTF.setText("0");
        if (    orientationZaxisTF.getText().equals("-0") ||     orientationZaxisTF.getText().equals("-0.0"))     orientationZaxisTF.setText("0");
        if (orientationAngleTF.getText().equals("-0") || orientationAngleTF.getText().equals("-0.0")) orientationAngleTF.setText("0");

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

    private void minXTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_minXTFActionPerformed
    {//GEN-HEADEREND:event_minXTFActionPerformed
        checkFieldOfViewCoordinatePair ();
}//GEN-LAST:event_minXTFActionPerformed

    private void minYTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_minYTFActionPerformed
    {//GEN-HEADEREND:event_minYTFActionPerformed
        checkFieldOfViewCoordinatePair ();
}//GEN-LAST:event_minYTFActionPerformed

    private void maxXTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_maxXTFActionPerformed
    {//GEN-HEADEREND:event_maxXTFActionPerformed
        checkFieldOfViewCoordinatePair ();
}//GEN-LAST:event_maxXTFActionPerformed

    private void maxYTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_maxYTFActionPerformed
    {//GEN-HEADEREND:event_maxYTFActionPerformed
        checkFieldOfViewCoordinatePair ();
}//GEN-LAST:event_maxYTFActionPerformed

    private void targetXTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_targetXTFActionPerformed
        computeDistanceAngleValues();
}//GEN-LAST:event_targetXTFActionPerformed

    private void targetYTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_targetYTFActionPerformed
        computeDistanceAngleValues();
}//GEN-LAST:event_targetYTFActionPerformed

    private void targetZTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_targetZTFActionPerformed
        computeDistanceAngleValues();
}//GEN-LAST:event_targetZTFActionPerformed

    private void recomputeLookatRotationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recomputeLookatRotationButtonActionPerformed
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
        double a, b, a1, a2, a3, b1, b2, b3, a_dot_b, axis1, axis2, axis3, angle;
        a1 =  0.0;
        a2 =  0.0;
        a3 = -1.0;
        b1 = targetX - positionX;
        b2 = targetY - positionY;
        b3 = targetZ - positionZ;
        a  = Math.sqrt(a1*a1 + a2*a2 + a3*a3); // magnitude
        b  = Math.sqrt(b1*b1 + b2*b2 + b3*b3); // magnitude
        a_dot_b = a1*b1 + a2*b2 + a3*b3;

        // compute axis and angle values
        axis1 = a2 * b3 - a3 * b2;
        axis2 = a3 * b1 - a1 * b3;
        axis3 = a1 * b2 - a2 * b1;

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
	String lookatComment = "<!-- " + VIEWPOINTCustomizer.LOOKAT_COMMENT_HEADER + " " +
				targetXTF.getText().trim() + " " +
				targetYTF.getText().trim() + " " +
				targetZTF.getText().trim() + " -->";
	
	if      (VIEWPOINTCustomizer.isIncludeLookatPointAsComment() && nonzero && orthoViewpoint.hasContent("<Metadata"))
		     orthoViewpoint.setContent(lookatComment + // prepend
					 orthoViewpoint.getContent().substring(orthoViewpoint.getContent().indexOf("<Metadata")));
	else if (VIEWPOINTCustomizer.isIncludeLookatPointAsComment() && nonzero)
		     orthoViewpoint.setContent(lookatComment);
	else if (!VIEWPOINTCustomizer.isIncludeLookatPointAsComment() && orthoViewpoint.hasContent("<Metadata"))
		     orthoViewpoint.setContent(
					 orthoViewpoint.getContent().substring(orthoViewpoint.getContent().indexOf("<Metadata")));
	else if (!VIEWPOINTCustomizer.isIncludeLookatPointAsComment() && orthoViewpoint.hasContent(VIEWPOINTCustomizer.LOOKAT_COMMENT_HEADER))
		     orthoViewpoint.setContent(""); // clear
}
private void highlightDisplayViewpointCalculatorFields ()
{
        // reset other display fields
        Color highlightForegroundColor = authorAssistLabel.getForeground();
           positionLabel.setFont(positionLabel.getFont().deriveFont(Font.BOLD));
        orientationLabel.setFont(positionLabel.getFont().deriveFont(Font.BOLD));
       goalAimPointLabel.setFont(positionLabel.getFont().deriveFont(Font.BOLD));
         twistAngleLabel.setFont(positionLabel.getFont().deriveFont(Font.BOLD));
           positionLabel.setForeground(highlightForegroundColor);
        orientationLabel.setForeground(highlightForegroundColor);
          orientationXaxisTF.setForeground(highlightForegroundColor);
          orientationYaxisTF.setForeground(highlightForegroundColor);
          orientationZaxisTF.setForeground(highlightForegroundColor);
      orientationAngleTF.setForeground(highlightForegroundColor);
        // similiar color for backgroung but brighter
        Color fieldBackgroundColor = new Color (255 - (255 - highlightForegroundColor.getRed())   / 4,
                                                255 - (255 - highlightForegroundColor.getGreen()) / 4,
                                                255 - (255 - highlightForegroundColor.getBlue())  / 4);
        orientationXaxisTF.setBackground(fieldBackgroundColor);
        orientationYaxisTF.setBackground(fieldBackgroundColor);
        orientationZaxisTF.setBackground(fieldBackgroundColor);
    orientationAngleTF.setBackground(fieldBackgroundColor);
             centerOfRotationXTF.setBackground(fieldBackgroundColor);
             centerOfRotationYTF.setBackground(fieldBackgroundColor);
             centerOfRotationZTF.setBackground(fieldBackgroundColor);
}
private void resetDisplayViewpointCalculatorFields ()
{
        // reset other display fields
           positionLabel.setFont(positionLabel.getFont().deriveFont(Font.PLAIN));
        orientationLabel.setFont(positionLabel.getFont().deriveFont(Font.PLAIN));
       goalAimPointLabel.setFont(positionLabel.getFont().deriveFont(Font.PLAIN));
         twistAngleLabel.setFont(positionLabel.getFont().deriveFont(Font.PLAIN));
        Color fieldForegroundColor = descriptionTA.getForeground();
        Color fieldBackgroundColor = descriptionTA.getBackground();
           positionLabel.setForeground(fieldForegroundColor);
        orientationLabel.setForeground(fieldForegroundColor);
          orientationXaxisTF.setForeground(fieldForegroundColor);
          orientationYaxisTF.setForeground(fieldForegroundColor);
          orientationZaxisTF.setForeground(fieldForegroundColor);
      orientationAngleTF.setForeground(fieldForegroundColor);
          orientationXaxisTF.setBackground(fieldBackgroundColor);
          orientationYaxisTF.setBackground(fieldBackgroundColor);
          orientationZaxisTF.setBackground(fieldBackgroundColor);
      orientationAngleTF.setBackground(fieldBackgroundColor);
               centerOfRotationXTF.setBackground(fieldBackgroundColor);
               centerOfRotationYTF.setBackground(fieldBackgroundColor);
               centerOfRotationZTF.setBackground(fieldBackgroundColor);
}//GEN-LAST:event_recomputeLookatRotationButtonActionPerformed

    private void twistAngleTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_twistAngleTFActionPerformed
        checkAngles(false);
        computeDistanceAngleValues();
        twistAngle = new SFDouble(twistAngleTF.getText()).getValue();
        twistAngleTF.setToolTipText(radiansFormat.format(twistAngle) + " radians = " + radiansFormat.format(twistAngle * 180.0 / Math.PI) + " degrees");
        resetDisplayViewpointCalculatorFields ();
}//GEN-LAST:event_twistAngleTFActionPerformed

    private void headsUpRotationRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_headsUpRotationRadioButtonActionPerformed
        headsUpRotationMode = headsUpRotationRadioButton.isSelected();
}//GEN-LAST:event_headsUpRotationRadioButtonActionPerformed

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

    private void includeLookatPointAsCommentCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_includeLookatPointAsCommentCheckBoxActionPerformed
        VIEWPOINTCustomizer.setIncludeLookatPointAsComment(includeLookatPointAsCommentCheckBox.isSelected()); // remember static variable for this session
		updateLookatPointAsComment();
    }//GEN-LAST:event_includeLookatPointAsCommentCheckBoxActionPerformed

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
    }//GEN-LAST:event_levelHeightButtonActionPerformed

    private void clearAimPointButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearAimPointButtonActionPerformed
        targetXTF.setText("0");
        targetYTF.setText("0");
        targetZTF.setText("0");
        twistAngleTF.setText("0");
        headsUpRotationRadioButton.setSelected(true);
        computeDistanceAngleValues ();
        resetDisplayViewpointCalculatorFields ();
    }//GEN-LAST:event_clearAimPointButtonActionPerformed

    private void rotationCalculatorlButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rotationCalculatorlButtonActionPerformed
        RotationCalculatorPanel orientationCalculatorPanel = new RotationCalculatorPanel(orthoViewpoint, "orientation");
        orientationCalculatorPanel.setRotationValue (
            orientationXaxisTF.getText(),
            orientationYaxisTF.getText(),
            orientationZaxisTF.getText(),
            orientationAngleTF.getText());
        DialogDescriptor dd = new DialogDescriptor(orientationCalculatorPanel, "Rotation Calculator for OrthoViewpoint orientation");
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
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel adjustmentsLabel;
    private javax.swing.JLabel angleLabel;
    private javax.swing.JLabel authorAssistLabel;
    private javax.swing.JSeparator authorAssistSeparator;
    private javax.swing.JLabel centerOfRotationLabel;
    private javax.swing.JTextField centerOfRotationXTF;
    private javax.swing.JTextField centerOfRotationYTF;
    private javax.swing.JTextField centerOfRotationZTF;
    private javax.swing.JButton clearAimPointButton;
    private org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel1;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JTextArea descriptionTA;
    private javax.swing.JLabel fieldOfViewLabel;
    private javax.swing.JLabel goalAimPointLabel;
    private javax.swing.JRadioButton greatCircleRotationRadioButton;
    private javax.swing.JRadioButton headsUpRotationRadioButton;
    private javax.swing.JLabel horizontalAngleLabel;
    private javax.swing.JTextField horizontalAngleTF;
    private javax.swing.JLabel horizontalRangeLabel;
    private javax.swing.JTextField horizontalRangeTF;
    private javax.swing.JCheckBox includeLookatPointAsCommentCheckBox;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JCheckBox jumpCB;
    private javax.swing.JLabel jumpLabel;
    private javax.swing.JButton levelHeightButton;
    private javax.swing.JLabel maxXLabel;
    private javax.swing.JTextField maxXTF;
    private javax.swing.JLabel maxYLabel;
    private javax.swing.JTextField maxYTF;
    private javax.swing.JLabel minXLabel;
    private javax.swing.JTextField minXTF;
    private javax.swing.JLabel minYLabel;
    private javax.swing.JTextField minYTF;
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
    private javax.swing.JCheckBox retainUserOffsetsCB;
    private javax.swing.JLabel retainUserOffsetsLabel;
    private javax.swing.JButton rotationCalculatorlButton;
    private javax.swing.JLabel slantRangeLabel;
    private javax.swing.JTextField slantRangeTF;
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
    return "NAME_X3D_ORTHOVIEWPOINT";
  }

  public void checkFieldOfViewCoordinatePair ()
  {
      // check min coordinate < max coordinate
      double minX = new SFDouble(minXTF.getText()).getValue();
      double minY = new SFDouble(minYTF.getText()).getValue();
      double maxX = new SFDouble(maxXTF.getText()).getValue();
      double maxY = new SFDouble(maxYTF.getText()).getValue();

      if ((minY == maxY) || (minY == maxY))
      {
          String message = "minimum/maximum values are equal, reset fieldOfView to default?";
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  message, "fieldOfView Warning", NotifyDescriptor.YES_NO_OPTION);
          if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
          {
              minXTF.setText("-1");
              minYTF.setText("-1");
              maxXTF.setText("1");
              maxYTF.setText("1");
          }
      }
      if (minX > maxX)
      {
          String message = "fieldOfView minimum X is greater than maximum X, swap values?";
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  message, "fieldOfView Warning", NotifyDescriptor.YES_NO_OPTION);
          if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
          {
              minXTF.setText(String.valueOf(maxX));
              maxXTF.setText(String.valueOf(minX));
          }
      }
      if (minY > maxY)
      {
          String message = "fieldOfView minimum Y is greater than maximum Y, swap values?";
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  message, "fieldOfView Warning", NotifyDescriptor.YES_NO_OPTION);
          if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
          {
              minYTF.setText(String.valueOf(maxY));
              maxYTF.setText(String.valueOf(minY));
          }
      }
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

        positionX = new SFDouble(positionXTF.getText()).getValue();
        positionY = new SFDouble(positionYTF.getText()).getValue();
        positionZ = new SFDouble(positionZTF.getText()).getValue();
        targetX   = new SFDouble(targetXTF.getText()).getValue(); // this also saves local panel parameters
        targetY   = new SFDouble(targetYTF.getText()).getValue();
        targetZ   = new SFDouble(targetZTF.getText()).getValue();
        twistAngle= new SFDouble(twistAngleTF.getText()).getValue();
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

        return;
  }

  public final void checkAngles(boolean precedesNormalization)
  {
      // indicate degree values in tooltips
      // usability note:  can enter degree values (-6..+6) as (354..366) to provoke this conversion check
      double angle = new SFDouble(orientationAngleTF.getText()).getValue();
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
      angle = new SFDouble(twistAngleTF.getText()).getValue();
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
    checkFieldOfViewCoordinatePair ();

    orthoViewpoint.setCenterOfRotationX(centerOfRotationXTF.getText().trim());
    orthoViewpoint.setCenterOfRotationY(centerOfRotationYTF.getText().trim());
    orthoViewpoint.setCenterOfRotationZ(centerOfRotationZTF.getText().trim());
    orthoViewpoint.setDescription(descriptionTA.getText().trim());

	// field of view rectangle
    orthoViewpoint.setMinX(minXTF.getText().trim());
    orthoViewpoint.setMinY(minYTF.getText().trim());
    orthoViewpoint.setMaxX(maxXTF.getText().trim());
    orthoViewpoint.setMaxY(maxYTF.getText().trim());

    orthoViewpoint.setJump(jumpCB.isSelected());
    orthoViewpoint.setRetainUserOffsets(retainUserOffsetsCB.isSelected());
    orthoViewpoint.setOrienationtX    (orientationXaxisTF.getText().trim());
    orthoViewpoint.setOrientationY    (orientationYaxisTF.getText().trim());
    orthoViewpoint.setOrientationZ    (orientationZaxisTF.getText().trim());
    orthoViewpoint.setOrientationAngle(orientationAngleTF.getText().trim());
    if (orthoViewpoint.getOrientationX().equals("0") && orthoViewpoint.getOrientationY().equals("1") && orthoViewpoint.getOrientationZ().equals("0") &&
        orthoViewpoint.getOrientationAngle().equals("0"))
    {
        // restore X3D default vice preferred default
        orthoViewpoint.setOrientationY("0");
        orthoViewpoint.setOrientationZ("1");
    }
    orthoViewpoint.setPositionX(positionXTF.getText().trim());
    orthoViewpoint.setPositionY(positionYTF.getText().trim());
    orthoViewpoint.setPositionZ(positionZTF.getText().trim());

    // viewpoint computation assist parameters saved separately as local variables, not Viewpoint attributes
       targetX = new SFDouble(targetXTF.getText()).getValue();
       targetY = new SFDouble(targetYTF.getText()).getValue();
       targetZ = new SFDouble(targetZTF.getText()).getValue();
    twistAngle = new SFDouble(twistAngleTF.getText()).getValue();
    headsUpRotationMode = headsUpRotationRadioButton.isSelected();
  }
}
