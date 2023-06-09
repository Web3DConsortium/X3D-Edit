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

import javax.swing.text.JTextComponent;
import org.openide.util.HelpCtx;

/**
 * SLIDERJOINTCustomizer.java
 * Created on December 23, 2008, 10:49 AM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class SLIDERJOINTCustomizer extends BaseCustomizer
{
  private SLIDERJOINT sliderJoint;
  private JTextComponent target;
  
  /** Creates new form SLIDERJOINTCustomizer */
  public SLIDERJOINTCustomizer(SLIDERJOINT sliderJoint, JTextComponent target)
  {
    super(sliderJoint);
    this.sliderJoint = sliderJoint;
    this.target = target;
                              
    HelpCtx.setHelpIDString(this, "SLIDERJOINT_ELEM_HELPID");   
    
    initComponents();
    
    String textValue = sliderJoint.getUnformattedForceOutput();
    if (textValue.startsWith("'") && textValue.endsWith("'") && (textValue.length() > 1))
         forceOutputTA.setText(textValue.substring(1, textValue.length()-2));
    else forceOutputTA.setText(textValue);
    
    axisXTF.setText(sliderJoint.getAxisX());
    axisYTF.setText(sliderJoint.getAxisY());
    axisZTF.setText(sliderJoint.getAxisZ());
    
          minSeparationTF.setText(sliderJoint.getMinSeparation());
          maxSeparationTF.setText(sliderJoint.getMaxSeparation());
            sliderForceTF.setText(sliderJoint.getSliderForce());
             stopBounceTF.setText(sliderJoint.getStopBounce());
    stopErrorCorrectionTF.setText(sliderJoint.getStopErrorCorrection());
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
    jLabel1 = new javax.swing.JLabel();
    jScrollPane1 = new javax.swing.JScrollPane();
    forceOutputTA = new javax.swing.JTextArea();
    jLabel2 = new javax.swing.JLabel();
    axisXTF = new javax.swing.JTextField();
    axisYTF = new javax.swing.JTextField();
    axisZTF = new javax.swing.JTextField();
    jLabel3 = new javax.swing.JLabel();
    minSeparationTF = new javax.swing.JTextField();
    jLabel4 = new javax.swing.JLabel();
    maxSeparationTF = new javax.swing.JTextField();
    jLabel5 = new javax.swing.JLabel();
    stopBounceTF = new javax.swing.JTextField();
    stopErrorCorrectionTF = new javax.swing.JTextField();
    jLabel6 = new javax.swing.JLabel();
    jLabel7 = new javax.swing.JLabel();
    sliderForceTF = new javax.swing.JTextField();

    setToolTipText("normalized vector specifying direction of motion [0..1]");
    setLayout(new java.awt.GridBagLayout());
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    add(dEFUSEpanel1, gridBagConstraints);

    jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
    jLabel1.setText("forceOutput");
    jLabel1.setToolTipText("output fields generated for next frame: ALL, NONE, or exact names of output fields");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    add(jLabel1, gridBagConstraints);

    forceOutputTA.setColumns(20);
    forceOutputTA.setRows(2);
    forceOutputTA.setToolTipText("output fields generated for next frame: ALL, NONE, or exact names of output fields");
    jScrollPane1.setViewportView(forceOutputTA);

    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    add(jScrollPane1, gridBagConstraints);

    jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
    jLabel2.setText("axis");
    jLabel2.setToolTipText("normalized vector specifying direction of motion [0..1]");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    add(jLabel2, gridBagConstraints);

    axisXTF.setColumns(5);
    axisXTF.setToolTipText("normalized vector specifying direction of motion [0..1]");
    axisXTF.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        axisXTFActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 0.3333;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    add(axisXTF, gridBagConstraints);

    axisYTF.setColumns(5);
    axisYTF.setToolTipText("normalized vector specifying direction of motion [0..1]");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 2;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 0.3333;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    add(axisYTF, gridBagConstraints);

    axisZTF.setColumns(5);
    axisZTF.setToolTipText("normalized vector specifying direction of motion [0..1]");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 3;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 0.3333;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    add(axisZTF, gridBagConstraints);

    jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
    jLabel3.setText("minSeparation");
    jLabel3.setToolTipText("minimum separation distance between two bodies");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    add(jLabel3, gridBagConstraints);

    minSeparationTF.setColumns(5);
    minSeparationTF.setToolTipText("minimum separation distance between two bodies");
    minSeparationTF.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        minSeparationTFActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 0.3333;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    add(minSeparationTF, gridBagConstraints);

    jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
    jLabel4.setText("maxSeparation");
    jLabel4.setToolTipText("maximum separation distance between two bodies");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    add(jLabel4, gridBagConstraints);

    maxSeparationTF.setColumns(5);
    maxSeparationTF.setToolTipText("maximum separation distance between two bodies");
    maxSeparationTF.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        maxSeparationTFActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 4;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 0.3333;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    add(maxSeparationTF, gridBagConstraints);

    jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
    jLabel5.setText("stopBounce");
    jLabel5.setToolTipText("velocity factor for bounce back once stop point is reached");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 6;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    add(jLabel5, gridBagConstraints);

    stopBounceTF.setColumns(5);
    stopBounceTF.setToolTipText("velocity factor for bounce back once stop point is reached");
    stopBounceTF.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        stopBounceTFActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 6;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 0.3333;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    add(stopBounceTF, gridBagConstraints);

    stopErrorCorrectionTF.setColumns(5);
    stopErrorCorrectionTF.setToolTipText("fraction of error correction performed during time step once stop point is reached (0 = no error correction, 1 = all error corrected in single step)");
    stopErrorCorrectionTF.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        stopErrorCorrectionTFActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 7;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 0.3333;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    add(stopErrorCorrectionTF, gridBagConstraints);

    jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
    jLabel6.setText("stopErrorCorrection");
    jLabel6.setToolTipText("fraction of error correction performed during time step once stop point is reached (0 = no error correction, 1 = all error corrected in single step)");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 7;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    add(jLabel6, gridBagConstraints);

    jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
    jLabel7.setText("sliderForce");
    jLabel7.setToolTipText("velocity factor for bounce back once stop point is reached");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 5;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    add(jLabel7, gridBagConstraints);

    sliderForceTF.setColumns(5);
    sliderForceTF.setToolTipText("velocity factor for bounce back once stop point is reached");
    sliderForceTF.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        sliderForceTFActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 5;
    gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
    gridBagConstraints.weightx = 0.3333;
    gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
    add(sliderForceTF, gridBagConstraints);
  }// </editor-fold>//GEN-END:initComponents

private void axisXTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_axisXTFActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_axisXTFActionPerformed

private void minSeparationTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minSeparationTFActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_minSeparationTFActionPerformed

private void maxSeparationTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_maxSeparationTFActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_maxSeparationTFActionPerformed

private void stopBounceTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopBounceTFActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_stopBounceTFActionPerformed

private void stopErrorCorrectionTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopErrorCorrectionTFActionPerformed
// TODO add your handling code here:
}//GEN-LAST:event_stopErrorCorrectionTFActionPerformed

private void sliderForceTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sliderForceTFActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_sliderForceTFActionPerformed
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JTextField axisXTF;
  private javax.swing.JTextField axisYTF;
  private javax.swing.JTextField axisZTF;
  private org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel1;
  private javax.swing.JTextArea forceOutputTA;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JLabel jLabel7;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JTextField maxSeparationTF;
  private javax.swing.JTextField minSeparationTF;
  private javax.swing.JTextField sliderForceTF;
  private javax.swing.JTextField stopBounceTF;
  private javax.swing.JTextField stopErrorCorrectionTF;
  // End of variables declaration//GEN-END:variables
 
  public String getNameKey()
  {
    return "NAME_X3D_SLIDERJOINT";
  }

  public void unloadInput() throws IllegalArgumentException
  {
    sliderJoint.setForceOutput(forceOutputTA.getText().trim());
    
    sliderJoint.setAxisX(axisXTF.getText().trim());
    sliderJoint.setAxisY(axisYTF.getText().trim());
    sliderJoint.setAxisZ(axisZTF.getText().trim());
    
    sliderJoint.setMinSeparation(minSeparationTF.getText().trim());
    sliderJoint.setMaxSeparation(maxSeparationTF.getText().trim());
    sliderJoint.setSliderForce(sliderForceTF.getText().trim());
    sliderJoint.setStopBounce(stopBounceTF.getText().trim());
    sliderJoint.setStopErrorCorrection(stopErrorCorrectionTF.getText().trim());    
  }
}
