/*
Copyright (c) 1995-2021 held by the author(s).  All rights reserved.
 
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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.JTextComponent;
import org.openide.util.HelpCtx;

/**
 * SINGLEAXISHINGEJOINTCustomizer.java
 * Created on 4 January 2009
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class SINGLEAXISHINGEJOINTCustomizer extends BaseCustomizer
{
  private SINGLEAXISHINGEJOINT singleAxisHingeJoint;
  private JTextComponent target;
  
  /** Creates new form SINGLEAXISHINGEJOINTCustomizer */
  public SINGLEAXISHINGEJOINTCustomizer(SINGLEAXISHINGEJOINT singleAxisHingeJoint, JTextComponent target)
  {
    super(singleAxisHingeJoint);
    this.singleAxisHingeJoint = singleAxisHingeJoint;
    this.target = target;
                              
    HelpCtx.setHelpIDString(this, "SINGLEAXISHINGEJOINT_ELEM_HELPID");   
    
    initComponents();
    
    String textValue = singleAxisHingeJoint.getUnformattedForceOutput();
    if (textValue.startsWith("'") && textValue.endsWith("'") && (textValue.length() > 1))
         forceOutputTA.setText(textValue.substring(1, textValue.length()-2));
    else forceOutputTA.setText(textValue);
    
    anchorPointXTF.setText(singleAxisHingeJoint.getAnchorPointX());
    anchorPointYTF.setText(singleAxisHingeJoint.getAnchorPointY());
    anchorPointZTF.setText(singleAxisHingeJoint.getAnchorPointZ());
    
    axisXTF.setText(singleAxisHingeJoint.getAxisX());
    axisYTF.setText(singleAxisHingeJoint.getAxisY());
    axisZTF.setText(singleAxisHingeJoint.getAxisZ());
    
    maxAngleTF.setText(singleAxisHingeJoint.getMaxAngle());
    minAngleTF.setText(singleAxisHingeJoint.getMinAngle());
    
    stopBounceTF.setText(singleAxisHingeJoint.getStopBounce());
    stopErrorCorrectionTF.setText(singleAxisHingeJoint.getStopErrorCorrection());
  }
  
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {
    GridBagConstraints gridBagConstraints;

    dEFUSEpanel1 = getDEFUSEpanel();
    jLabel1 = new JLabel();
    jScrollPane1 = new JScrollPane();
    forceOutputTA = new JTextArea();
    jLabel2 = new JLabel();
    axisXTF = new JTextField();
    axisYTF = new JTextField();
    axisZTF = new JTextField();
    jLabel3 = new JLabel();
    anchorPointXTF = new JTextField();
    anchorPointYTF = new JTextField();
    jLabel5 = new JLabel();
    stopBounceTF = new JTextField();
    stopErrorCorrectionTF = new JTextField();
    jLabel6 = new JLabel();
    anchorPointZTF = new JTextField();
    jLabel4 = new JLabel();
    jLabel7 = new JLabel();
    maxAngleTF = new JTextField();
    minAngleTF = new JTextField();

    setToolTipText("motor axis vector (0..1)");
    setLayout(new GridBagLayout());
    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
    gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.insets = new Insets(3, 3, 3, 3);
    add(dEFUSEpanel1, gridBagConstraints);

    jLabel1.setHorizontalAlignment(SwingConstants.TRAILING);
    jLabel1.setText("forceOutput");
    jLabel1.setToolTipText("output fields generated for next frame: ALL, NONE, or exact names of output fields");
    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.anchor = GridBagConstraints.EAST;
    gridBagConstraints.insets = new Insets(3, 3, 3, 3);
    add(jLabel1, gridBagConstraints);

    jScrollPane1.setToolTipText("output fields generated for next frame: ALL, NONE, or exact names of output fields");

    forceOutputTA.setColumns(20);
    forceOutputTA.setRows(2);
    forceOutputTA.setToolTipText("output fields generated for next frame: ALL, NONE, or exact names of output fields");
    jScrollPane1.setViewportView(forceOutputTA);

    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
    gridBagConstraints.fill = GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new Insets(3, 3, 3, 3);
    add(jScrollPane1, gridBagConstraints);

    jLabel2.setHorizontalAlignment(SwingConstants.TRAILING);
    jLabel2.setText("axis");
    jLabel2.setToolTipText("defines vector of joint connection between body1 and body2");
    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.anchor = GridBagConstraints.EAST;
    gridBagConstraints.insets = new Insets(3, 3, 3, 3);
    add(jLabel2, gridBagConstraints);

    axisXTF.setColumns(10);
    axisXTF.setToolTipText("defines vector of joint connection between body1 and body2");
    axisXTF.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        axisXTFActionPerformed(evt);
      }
    });
    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 0.3333;
    gridBagConstraints.insets = new Insets(3, 3, 3, 3);
    add(axisXTF, gridBagConstraints);

    axisYTF.setColumns(10);
    axisYTF.setToolTipText("defines vector of joint connection between body1 and body2");
    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 0.3333;
    gridBagConstraints.insets = new Insets(3, 3, 3, 3);
    add(axisYTF, gridBagConstraints);

    axisZTF.setColumns(10);
    axisZTF.setToolTipText("defines vector of joint connection between body1 and body2");
    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 3;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 0.3333;
    gridBagConstraints.insets = new Insets(3, 3, 3, 3);
    add(axisZTF, gridBagConstraints);

    jLabel3.setHorizontalAlignment(SwingConstants.TRAILING);
    jLabel3.setText("anchorPoint");
    jLabel3.setToolTipText("anchorPoint is joint center, specified in world coordinates");
    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.anchor = GridBagConstraints.EAST;
    gridBagConstraints.insets = new Insets(3, 3, 3, 3);
    add(jLabel3, gridBagConstraints);

    anchorPointXTF.setColumns(10);
    anchorPointXTF.setToolTipText("anchorPoint is joint center, specified in world coordinates");
    anchorPointXTF.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        anchorPointXTFActionPerformed(evt);
      }
    });
    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 0.3333;
    gridBagConstraints.insets = new Insets(3, 3, 3, 3);
    add(anchorPointXTF, gridBagConstraints);

    anchorPointYTF.setColumns(10);
    anchorPointYTF.setToolTipText("anchorPoint is joint center, specified in world coordinates");
    anchorPointYTF.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        anchorPointYTFActionPerformed(evt);
      }
    });
    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 0.3333;
    gridBagConstraints.insets = new Insets(3, 3, 3, 3);
    add(anchorPointYTF, gridBagConstraints);

    jLabel5.setHorizontalAlignment(SwingConstants.TRAILING);
    jLabel5.setText("stopBounce");
    jLabel5.setToolTipText("velocity factor for bounce back once stop point is reached");
    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.anchor = GridBagConstraints.EAST;
    gridBagConstraints.insets = new Insets(3, 3, 3, 3);
    add(jLabel5, gridBagConstraints);

    stopBounceTF.setColumns(10);
    stopBounceTF.setToolTipText("velocity factor for bounce back once stop point is reached");
    stopBounceTF.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        stopBounceTFActionPerformed(evt);
      }
    });
    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 3;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 0.3333;
    gridBagConstraints.insets = new Insets(3, 3, 3, 3);
    add(stopBounceTF, gridBagConstraints);

    stopErrorCorrectionTF.setColumns(10);
    stopErrorCorrectionTF.setToolTipText("fraction of error correction performed during time step once stop point is reached (0 = no error correction, 1 = all error corrected in single step)");
    stopErrorCorrectionTF.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        stopErrorCorrectionTFActionPerformed(evt);
      }
    });
    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 3;
    gridBagConstraints.gridy = 5;
    gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 0.3333;
    gridBagConstraints.insets = new Insets(3, 3, 3, 3);
    add(stopErrorCorrectionTF, gridBagConstraints);

    jLabel6.setHorizontalAlignment(SwingConstants.TRAILING);
    jLabel6.setText("stopErrorCorrection");
    jLabel6.setToolTipText("fraction of error correction performed during time step once stop point is reached (0 = no error correction, 1 = all error corrected in single step)");
    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 5;
    gridBagConstraints.anchor = GridBagConstraints.EAST;
    gridBagConstraints.insets = new Insets(3, 3, 3, 3);
    add(jLabel6, gridBagConstraints);

    anchorPointZTF.setColumns(10);
    anchorPointZTF.setToolTipText("anchorPoint is joint center, specified in world coordinates");
    anchorPointZTF.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        anchorPointZTFActionPerformed(evt);
      }
    });
    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 3;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 0.3333;
    gridBagConstraints.insets = new Insets(3, 3, 3, 3);
    add(anchorPointZTF, gridBagConstraints);

    jLabel4.setText("minAngle");
    jLabel4.setToolTipText("minimum rotation angle for hinge");
    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.anchor = GridBagConstraints.EAST;
    gridBagConstraints.insets = new Insets(3, 3, 3, 3);
    add(jLabel4, gridBagConstraints);

    jLabel7.setText("maxAngle");
    jLabel7.setToolTipText("maximum rotation angle for hinge");
    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 5;
    gridBagConstraints.anchor = GridBagConstraints.EAST;
    gridBagConstraints.insets = new Insets(3, 3, 3, 3);
    add(jLabel7, gridBagConstraints);

    maxAngleTF.setColumns(10);
    maxAngleTF.setToolTipText("maximum rotation angle for hinge");
    maxAngleTF.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        maxAngleTFActionPerformed(evt);
      }
    });
    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 5;
    gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 0.3333;
    gridBagConstraints.insets = new Insets(3, 3, 3, 3);
    add(maxAngleTF, gridBagConstraints);

    minAngleTF.setColumns(10);
    minAngleTF.setToolTipText("minimum rotation angle for hinge");
    minAngleTF.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        minAngleTFActionPerformed(evt);
      }
    });
    gridBagConstraints = new GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 0.3333;
    gridBagConstraints.insets = new Insets(3, 3, 3, 3);
    add(minAngleTF, gridBagConstraints);
  }// </editor-fold>//GEN-END:initComponents

private void axisXTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_axisXTFActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_axisXTFActionPerformed

private void anchorPointXTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_anchorPointXTFActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_anchorPointXTFActionPerformed

private void anchorPointYTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_anchorPointYTFActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_anchorPointYTFActionPerformed

private void stopBounceTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopBounceTFActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_stopBounceTFActionPerformed

private void stopErrorCorrectionTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopErrorCorrectionTFActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_stopErrorCorrectionTFActionPerformed

private void anchorPointZTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_anchorPointZTFActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_anchorPointZTFActionPerformed

private void maxAngleTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maxAngleTFActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_maxAngleTFActionPerformed

private void minAngleTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minAngleTFActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_minAngleTFActionPerformed
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private JTextField anchorPointXTF;
  private JTextField anchorPointYTF;
  private JTextField anchorPointZTF;
  private JTextField axisXTF;
  private JTextField axisYTF;
  private JTextField axisZTF;
  private DEFUSEpanel dEFUSEpanel1;
  private JTextArea forceOutputTA;
  private JLabel jLabel1;
  private JLabel jLabel2;
  private JLabel jLabel3;
  private JLabel jLabel4;
  private JLabel jLabel5;
  private JLabel jLabel6;
  private JLabel jLabel7;
  private JScrollPane jScrollPane1;
  private JTextField maxAngleTF;
  private JTextField minAngleTF;
  private JTextField stopBounceTF;
  private JTextField stopErrorCorrectionTF;
  // End of variables declaration//GEN-END:variables
 
  public String getNameKey()
  {
    return "NAME_X3D_SINGLEAXISHINGEJOINT";
  }

  public void unloadInput() throws IllegalArgumentException
  {
    singleAxisHingeJoint.setForceOutput(forceOutputTA.getText().trim());
    
    singleAxisHingeJoint.setAnchorPointX(anchorPointXTF.getText().trim());
    singleAxisHingeJoint.setAnchorPointY(anchorPointYTF.getText().trim());
    singleAxisHingeJoint.setAnchorPointZ(anchorPointZTF.getText().trim());
    
    singleAxisHingeJoint.setAxisX(axisXTF.getText().trim());
    singleAxisHingeJoint.setAxisY(axisYTF.getText().trim());
    singleAxisHingeJoint.setAxisZ(axisZTF.getText().trim());
    
    singleAxisHingeJoint.setMaxAngle(maxAngleTF.getText().trim());
    singleAxisHingeJoint.setMinAngle(minAngleTF.getText().trim());
    
    singleAxisHingeJoint.setStopBounce(stopBounceTF.getText().trim());
    singleAxisHingeJoint.setStopErrorCorrection(stopErrorCorrectionTF.getText().trim());
  }
}
