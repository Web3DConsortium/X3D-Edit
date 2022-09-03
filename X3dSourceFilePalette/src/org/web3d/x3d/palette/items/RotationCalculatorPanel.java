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
 *
 * RotationCalculatorPanel.java
 */
package org.web3d.x3d.palette.items;

import javax.swing.DefaultComboBoxModel;
import javax.vecmath.AxisAngle4d;
import javax.vecmath.Matrix4f;
import javax.vecmath.Quat4d;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.web3d.x3d.types.X3DPrimitiveTypes;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFDouble;
import static org.web3d.x3d.types.X3DPrimitiveTypes.fiveDigitFormat;
import static org.web3d.x3d.types.X3DPrimitiveTypes.radiansFormat;
import static org.web3d.x3d.types.X3DPrimitiveTypes.singleDigitFormat;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 *
 * @author  Don Brutzman <brutzman@nps.edu>
 */
public class RotationCalculatorPanel extends javax.swing.JPanel
{
  private final String   rotationResultDefault = "Enter SFRotation values to compute a result";
  private String   rotationResult  = rotationResultDefault;
  private String   rotationResultX = "0";
  private String   rotationResultY = "1";
  private String   rotationResultZ = "0";
  private String   rotationResultAngle = "0";
  final   String[] defaultRotation = new String[]{"0","1","0","0"};
  private String   originalContent = "";
  private final BaseX3DElement parentNode;
  private final String         fieldName;
  private final String ROTATION_CALCULATOR_COMMENT_PREFIX = "<!-- RotationCalculator composed ";
  
  private final String[] rotationSelections = new String[] { "Select", "Euler X-axis", "Euler Y-axis", "Euler Z-axis", "Quaternion" };

  public RotationCalculatorPanel(BaseX3DElement parentNode, String fieldName)
  {
    this.parentNode = parentNode;
    this.fieldName  = fieldName;
    
    initComponents();
    
    initialize();
  }
  
  private void initialize()
  {
    expandableListOrientations.setTitle("X3D SFRotation array");
    expandableListOrientations.setColumnTitles  (new String[]{"#","axis-x","axis-y","axis-z","angle"});
    expandableListOrientations.setColumnToolTips(new String[]{"index","rotation axis-x value","rotation axis-y value","rotation axis-z value","rotation angle"});
    expandableListOrientations.setHeaderTooltip ("Rotations are composed via multiplication to form a single resulting rotation");
    expandableListOrientations.setNewRowData(defaultRotation); // values for zero-degree rotation about Y axis
    expandableListOrientations.doIndexInFirstColumn(true);
//    expandableListOrientations.setBoldColumn(1);
    expandableListOrientations.setAngleColumn(4);  // 4-tuple keyValue entry means column 4

    expandableListOrientations.setShowAppendCommasLineBreaks(true);
    expandableListOrientations.setKeyColumnIncluded(false);
    expandableListOrientations.setDoOrientations   (true);
    expandableListOrientations.setInsertCommas     (true);
    expandableListOrientations.setInsertLineBreaks (false);
    
    rotationResultTextField.setText(rotationResultDefault);
        
    if (parentNode != null)
    {   
        originalContent = parentNode.getContent();
        if (originalContent.trim().isEmpty())
            originalContent = "";
        if (originalContent.trim().startsWith(ROTATION_CALCULATOR_COMMENT_PREFIX + fieldName + ":")) // utilize previous hint comment
        {
            String rotationsTableExcerpt = originalContent.substring(originalContent.indexOf(":") + 1,
                                                                     originalContent.indexOf("-->")).trim(); // contained content
            String[] sa = parseX(rotationsTableExcerpt);
            expandableListOrientations.setData(sa);
            
//            int historyTableLength = sa.length / 4;
//            // TODO truncation warning if not an even multiple
//            String[][] saa = new String[historyTableLength][4];
//            for (int i=0; i < historyTableLength; i++)
//            {
//                for (int j = 0; j < 4; j++)
//                {
//                    saa[i][j] = sa[i*4 + j];
//                }
//            }
//            expandableListOrientations.setData(saa);
//            expandableListOrientations.repaint();
            updateRotationResult ();
        }
    }
  }

  private void resetRotations()
  {
    expandableListOrientations.setData(defaultRotation); // values for zero-degree rotation about Y axis
    rotationResult      = defaultRotation[0] + " " + defaultRotation[1] + " " + defaultRotation[2] + " " + defaultRotation[3];
    rotationResultX     = defaultRotation[0];
    rotationResultY     = defaultRotation[1];
    rotationResultZ     = defaultRotation[2];
    rotationResultAngle = defaultRotation[3];
    updateRotationResult();
  }

  protected void setRotationValue (String axisX, String axisY, String axisZ, String angle)
  {
    String[] initialRotation = {axisX, axisY, axisZ, angle};
    if (expandableListOrientations.getRowCount() == 0) // do not overwrite if preset by contained comment!
    {
        expandableListOrientations.setData(initialRotation); // values for zero-degree rotation about Y axis
    } 
    rotationResult      = axisX + " " + axisY + " " + axisZ + " " + angle;
    rotationResultX     = axisX;
    rotationResultY     = axisY;
    rotationResultZ     = axisZ;
    rotationResultAngle = angle;
    updateRotationResult();
  }

  protected String[][] getData ()
  {
      return expandableListOrientations.getData();
  }

  private void checkAngles(boolean precedesNormalization)
  {
        // usability note:  can enter degree values (-6..+6) as (354..366) to provoke this conversion check
        String[][] saa = expandableListOrientations.getData();

        for (int i = 0; i < saa.length; i++)
        {
            double angle = new X3DPrimitiveTypes.SFDouble(saa[i][3]).getValue();
            if (Math.abs(angle) > 2.0 * Math.PI)
            {
                String message;
                message = "<html><center>Large value provided for angle of <b>rotation[" + i + "]</b>, which is a radians value.<br/><br/>Convert <b>" + angle + " degrees</b> to <b>" +
                        radiansFormat.format((angle % 360.0) * Math.PI / 180.0) + " radians</b>";
                if (precedesNormalization)
                     message += " before normalization?";
                else message += "?";
                NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                        message, "X3D angles are in radians", NotifyDescriptor.YES_NO_OPTION);
                if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
                {
                    angle = (angle % 360.0) * Math.PI / 180.0;
                    saa[i][3] = radiansFormat.format(angle);
                }
            }
        }
        expandableListOrientations.setData(saa);
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        expandableListOrientations = new org.web3d.x3d.palette.items.ExpandableList();
        appendAngleRotationLabel = new javax.swing.JLabel();
        appendAngleRotationComboBox = new javax.swing.JComboBox<String>();
        appendAngleRotationTextField = new javax.swing.JTextField();
        appendAngleRotationButton = new javax.swing.JButton();
        normalizeRotationValuesButton = new javax.swing.JButton();
        resetButton = new javax.swing.JButton();
        recomputeButton = new javax.swing.JButton();
        rotationResultTextField = new javax.swing.JTextField();
        rotationResultLabel = new javax.swing.JLabel();
        nodeHintPanel = new javax.swing.JPanel();
        hintLabel = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        expandableListOrientations.setPreferredSize(new java.awt.Dimension(400, 200));
        expandableListOrientations.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                expandableListOrientationsPropertyChange(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 338;
        gridBagConstraints.ipady = 54;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 6.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(expandableListOrientations, gridBagConstraints);

        appendAngleRotationLabel.setText(org.openide.util.NbBundle.getMessage(RotationCalculatorPanel.class, "RotationCalculatorPanel.appendAngleRotationLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(appendAngleRotationLabel, gridBagConstraints);

        appendAngleRotationComboBox.setModel(new DefaultComboBoxModel<>(rotationSelections));
        appendAngleRotationComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                appendAngleRotationComboBoxItemStateChanged(evt);
            }
        });
        appendAngleRotationComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                appendAngleRotationComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(appendAngleRotationComboBox, gridBagConstraints);

        appendAngleRotationTextField.setText(org.openide.util.NbBundle.getMessage(RotationCalculatorPanel.class, "RotationCalculatorPanel.appendAngleRotationTextField.text")); // NOI18N
        appendAngleRotationTextField.setToolTipText(org.openide.util.NbBundle.getMessage(RotationCalculatorPanel.class, "RotationCalculatorPanel.appendAngleRotationTextField.toolTipText")); // NOI18N
        appendAngleRotationTextField.setEnabled(false);
        appendAngleRotationTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                appendAngleRotationTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(appendAngleRotationTextField, gridBagConstraints);

        appendAngleRotationButton.setText(org.openide.util.NbBundle.getMessage(RotationCalculatorPanel.class, "RotationCalculatorPanel.appendAngleRotationButton.text")); // NOI18N
        appendAngleRotationButton.setEnabled(false);
        appendAngleRotationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                appendAngleRotationButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(appendAngleRotationButton, gridBagConstraints);

        normalizeRotationValuesButton.setText(org.openide.util.NbBundle.getMessage(RotationCalculatorPanel.class, "RotationCalculatorPanel.normalizeRotationValuesButton.text")); // NOI18N
        normalizeRotationValuesButton.setToolTipText(org.openide.util.NbBundle.getMessage(RotationCalculatorPanel.class, "RotationCalculatorPanel.normalizeRotationValuesButton.toolTipText")); // NOI18N
        normalizeRotationValuesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                normalizeRotationValuesButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(normalizeRotationValuesButton, gridBagConstraints);

        resetButton.setText(org.openide.util.NbBundle.getMessage(RotationCalculatorPanel.class, "RotationCalculatorPanel.resetButton.text")); // NOI18N
        resetButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(resetButton, gridBagConstraints);

        recomputeButton.setText(org.openide.util.NbBundle.getMessage(RotationCalculatorPanel.class, "RotationCalculatorPanel.recomputeButton.text")); // NOI18N
        recomputeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recomputeButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(recomputeButton, gridBagConstraints);

        rotationResultTextField.setEditable(false);
        rotationResultTextField.setText(org.openide.util.NbBundle.getMessage(RotationCalculatorPanel.class, "RotationCalculatorPanel.rotationResultTextField.text")); // NOI18N
        rotationResultTextField.setToolTipText(org.openide.util.NbBundle.getMessage(RotationCalculatorPanel.class, "RotationCalculatorPanel.rotationResultTextField.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(rotationResultTextField, gridBagConstraints);

        rotationResultLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        rotationResultLabel.setText(org.openide.util.NbBundle.getMessage(RotationCalculatorPanel.class, "RotationCalculatorPanel.rotationResultLabel.text")); // NOI18N
        rotationResultLabel.setToolTipText(org.openide.util.NbBundle.getMessage(RotationCalculatorPanel.class, "RotationCalculatorPanel.rotationResultLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(rotationResultLabel, gridBagConstraints);

        nodeHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nodeHintPanel.setLayout(new java.awt.GridBagLayout());

        hintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel.setText(org.openide.util.NbBundle.getMessage(RotationCalculatorPanel.class, "RotationCalculatorPanel.hintLabel.text")); // NOI18N
        hintLabel.setToolTipText(org.openide.util.NbBundle.getMessage(RotationCalculatorPanel.class, "RotationCalculatorPanel.hintLabel.toolTipText")); // NOI18N
        hintLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        nodeHintPanel.add(hintLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nodeHintPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void normalizeRotationValuesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_normalizeRotationValuesButtonActionPerformed
        checkAngles(true);
        double normalizationFactor, x, y, z, angle;
        
        String[][] saa = expandableListOrientations.getData();
        if      (saa.length == 0) return;
        else
        {
            for (int i=0; i < saa.length; i++)
            {
                x     = new SFDouble(saa[i][0]).getValue();
                y     = new SFDouble(saa[i][1]).getValue();
                z     = new SFDouble(saa[i][2]).getValue();
                angle = new SFDouble(saa[i][3]).getValue();
                normalizationFactor = Math.sqrt(x * x + y * y + z * z);
                if (normalizationFactor == 0.0)
                {
                    NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                        "<html><p align='center'>Found rotation[" + i + "] has zero-magnitude axis, reset to 0 1 0<br /><br />" +
                        "(<b>0 0 0 " + angle + "</b>) becomes (<b>0 1 0 " + angle + "</b>)", NotifyDescriptor.WARNING_MESSAGE);
                    DialogDisplayer.getDefault().notify(descriptor);
                    saa[i][0] = "0";
                    saa[i][1] = "1";
                    saa[i][2] = "0";
                }
                else
                {
                    saa[i][0] = fiveDigitFormat.format(x / normalizationFactor);
                    saa[i][1] = fiveDigitFormat.format(y / normalizationFactor);
                    saa[i][2] = fiveDigitFormat.format(z / normalizationFactor);
                }
                if (angle == -0.0) angle = 0.0;
                while (angle < 0.0)
                {
                    angle += 2.0 * Math.PI;
                }
                while (angle > 2.0 * Math.PI)
                {
                    angle -= 2.0 * Math.PI;
                }
                saa[i][3] = radiansFormat.format(angle);
            }
        }
        expandableListOrientations.setData(saa);
        updateRotationResult ();
    }//GEN-LAST:event_normalizeRotationValuesButtonActionPerformed

    private void resetButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetButtonActionPerformed
        resetRotations();
    }//GEN-LAST:event_resetButtonActionPerformed
    protected void updateRotationResult ()
    {                                                          
        checkAngles(true);
        double x, y, z, angle;
        StringBuilder rotationList;
        rotationList = new StringBuilder(); // ensure cleared each time an update occurs
        
        String[][] data = expandableListOrientations.getData();
      switch (data.length) {
          case 0:
              rotationResultX     = "";
              rotationResultY     = "";
              rotationResultZ     = "";
              rotationResultAngle = "";
              rotationResult      = "";
              break;
          case 1:
              rotationResultX     = data[0][0];
              rotationResultY     = data[0][1];
              rotationResultZ     = data[0][2];
              rotationResultAngle = data[0][3];
              rotationResult      = rotationResultX + " " + rotationResultY + " " + rotationResultZ + " " + rotationResultAngle;
              break;

          // compute rotation result using transformation matrices
          default:
              // javax.vecmath conversions (boy is this a convoluted API or what!)
              AxisAngle4d  axisAngleIdentity = new AxisAngle4d(0.0, 1.0, 0.0, 0.0);
              Quat4d       quatIdentity      = new Quat4d ();
              quatIdentity.set(axisAngleIdentity);
              Matrix4f     matrixRotated     = new Matrix4f();
              matrixRotated.set (quatIdentity);
              // transformation matrix is now initialized using identity rotation
            
              for (int i=0; i < data.length; i++)
              {
                  x     = new SFDouble(data[i][0]).getValue();
                  y     = new SFDouble(data[i][1]).getValue();
                  z     = new SFDouble(data[i][2]).getValue();
                  angle = new SFDouble(data[i][3]).getValue();
                  
                  AxisAngle4d axisAngleCurrent      = new AxisAngle4d(x, y, z, angle);
                  Quat4d      quatCurrent           = new Quat4d ();           quatCurrent.set(axisAngleCurrent);
                  Matrix4f    matrixCurrent         = new Matrix4f();        matrixCurrent.set (quatCurrent);
                  
                  matrixRotated.mul (matrixCurrent); // multiply prior Matrix4f result against next rotation Matrix4f
                  
                  rotationList.append(" ").append(x).append(" ").append(y).append(" ").append(z).append(" ").append(angle);
                  if (i < data.length - 1)
                      rotationList.append(",");
              } 
              // all done multiplying, convert result Matrix4f to AxisAngle display as SFRotation
              Quat4d      quatRotated         = new Quat4d ();
              quatRotated.set(matrixRotated);
              AxisAngle4d axisAngleRotated    = new AxisAngle4d();
              axisAngleRotated.set(quatRotated);
              rotationResultX     = String.valueOf(fiveDigitFormat.format(axisAngleRotated.x));
              rotationResultY     = String.valueOf(fiveDigitFormat.format(axisAngleRotated.y));
              rotationResultZ     = String.valueOf(fiveDigitFormat.format(axisAngleRotated.z));
              rotationResultAngle = String.valueOf(fiveDigitFormat.format(axisAngleRotated.angle));
              rotationResult      = rotationResultX + " " + rotationResultY + " " + rotationResultZ + " " + rotationResultAngle;
              if (parentNode != null) // save list of composed rotations as a contained comment
              {
                  String hintComment =   "\n        " + ROTATION_CALCULATOR_COMMENT_PREFIX + fieldName + ":" + rotationList.toString() + " -->";
                  
                  if (originalContent.trim().startsWith(ROTATION_CALCULATOR_COMMENT_PREFIX + fieldName + ":")) // omit previous hint comment
                  {
                      originalContent = originalContent.substring(  originalContent.indexOf("-->") + 4); // following content
                  }
                  if (originalContent.trim().length() > 0)
                      hintComment += originalContent;
                  else hintComment += "\n";
                  parentNode.setContent(hintComment);
              } 
              break;
      }
        displayRotationResult();
    }
    
    private void displayRotationResult ()
    {
        rotationResultTextField.setText(rotationResult);
        if (!rotationResult.isEmpty())
             rotationResultTextField.setToolTipText(rotationResultAngle + " radians = " + 
                                                    singleDigitFormat.format(Float.parseFloat(rotationResultAngle) * 180.0 / Math.PI) + " degrees");
    }

    private void recomputeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recomputeButtonActionPerformed
        updateRotationResult (); // user-controlled updates
    }//GEN-LAST:event_recomputeButtonActionPerformed

    private void expandableListOrientationsPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_expandableListOrientationsPropertyChange
//        updateRotationResult (); // automatic updates
    }//GEN-LAST:event_expandableListOrientationsPropertyChange

    private void appendAngleRotationTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_appendAngleRotationTextFieldActionPerformed
        // normalization can occur once added
    }//GEN-LAST:event_appendAngleRotationTextFieldActionPerformed

    private void appendAngleRotationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_appendAngleRotationButtonActionPerformed
        String[] newRow = new String[] { "0", "1", "0", "0" };
      switch (appendAngleRotationComboBox.getSelectedIndex()) {
      // Select
          case 0:
              return; // no action
      // Euler angle X-axis
          case 1:
              newRow = new String[] { "1", "0", "0", appendAngleRotationTextField.getText().trim() };
              break;
      // Euler angle Y-axis
          case 2:
              newRow = new String[] { "0", "1", "0", appendAngleRotationTextField.getText().trim() };
              break;
      // Euler angle Z-axis
          case 3:
              newRow = new String[] { "0", "0", "1", appendAngleRotationTextField.getText().trim() };
              break;
      // Quaternion
          case 4:
              String[] values = appendAngleRotationTextField.getText().trim().split("[,\\s]+");
              if (values.length != 4)
              {
                  String message = "<html>Quaternion values are numeric: <b>q1 q2 q3 q4</b>";
                  NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                          message, "Illegal quaternion value", NotifyDescriptor.ERROR_MESSAGE);
                  DialogDisplayer.getDefault().notify(descriptor);
                  return;
              } Quat4d      quaternion = new Quat4d(Float.parseFloat(values[0]), Float.parseFloat(values[1]), 
                      Float.parseFloat(values[2]), Float.parseFloat(values[3]));
              AxisAngle4d axisAngle  = new AxisAngle4d();
              axisAngle.set(quaternion);
              newRow = new String[] { String.valueOf(fiveDigitFormat.format(axisAngle.x)),
                  String.valueOf(fiveDigitFormat.format(axisAngle.y)),
                  String.valueOf(fiveDigitFormat.format(axisAngle.z)),
                  String.valueOf(fiveDigitFormat.format(axisAngle.angle))};
              break;
          default:
              break;
      }
        expandableListOrientations.appendRow (newRow);
    }//GEN-LAST:event_appendAngleRotationButtonActionPerformed

    private void activateEulerAngleRotationInterface ()
    {
        boolean isEulerActive = (appendAngleRotationComboBox.getSelectedIndex() > 0);
         appendAngleRotationTextField.setEnabled(isEulerActive);
        appendAngleRotationButton.setEnabled   (isEulerActive);
    }
    private void appendAngleRotationComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_appendAngleRotationComboBoxActionPerformed
        activateEulerAngleRotationInterface ();
    }//GEN-LAST:event_appendAngleRotationComboBoxActionPerformed

    private void appendAngleRotationComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_appendAngleRotationComboBoxItemStateChanged
        activateEulerAngleRotationInterface ();
    }//GEN-LAST:event_appendAngleRotationComboBoxItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton appendAngleRotationButton;
    private javax.swing.JComboBox<String> appendAngleRotationComboBox;
    private javax.swing.JLabel appendAngleRotationLabel;
    private javax.swing.JTextField appendAngleRotationTextField;
    private javax.swing.ButtonGroup buttonGroup1;
    private org.web3d.x3d.palette.items.ExpandableList expandableListOrientations;
    private javax.swing.JLabel hintLabel;
    private javax.swing.JPanel nodeHintPanel;
    private javax.swing.JButton normalizeRotationValuesButton;
    private javax.swing.JButton recomputeButton;
    private javax.swing.JButton resetButton;
    private javax.swing.JLabel rotationResultLabel;
    private javax.swing.JTextField rotationResultTextField;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the rotationsResult
     */
    public String getRotationResult() {
        if (!rotationResult.equals(rotationResultDefault))
             return rotationResult;
        else return "";
    }

    /**
     * @return the rotationResultX
     */
    public String getRotationResultX() {
        return rotationResultX;
    }

    /**
     * @return the rotationResultY
     */
    public String getRotationResultY() {
        return rotationResultY;
    }

    /**
     * @return the rotationResultZ
     */
    public String getRotationResultZ() {
        return rotationResultZ;
    }

    /**
     * @return the rotationResultAngle
     */
    public String getRotationResultAngle() {
        return rotationResultAngle;
    }
}
