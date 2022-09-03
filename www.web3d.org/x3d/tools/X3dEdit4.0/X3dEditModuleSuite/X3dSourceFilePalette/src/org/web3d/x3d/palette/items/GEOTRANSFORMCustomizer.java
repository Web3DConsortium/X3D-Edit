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
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * GEOTRANSFORMCustomizer.java
 * Created on 28 March 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class GEOTRANSFORMCustomizer extends BaseCustomizer
{
  private final GEOTRANSFORM geoTransform;
  private final JTextComponent target;
  
  /** Creates new form GEOTRANSFORMCustomizer */
  public GEOTRANSFORMCustomizer(GEOTRANSFORM geoTransform, JTextComponent target)
  {
    super(geoTransform);
    this.geoTransform = geoTransform;
    this.target = target;
                             
    HelpCtx.setHelpIDString(this, "GEOTRANSFORM_ELEM_HELPID");

    geoTransform.setVisualizationSelectionAvailable(true); // must precede initComponents() interface initialization
    geoTransform.setVisualizationTooltip("Show center axes, add wireframe Box and axes to show boundingBox center and size (if defined)");
    
    initComponents();
    
    bboxCenterXTF.setText(geoTransform.getBboxCenterX());
    bboxCenterYTF.setText(geoTransform.getBboxCenterY());
    bboxCenterZTF.setText(geoTransform.getBboxCenterZ());
    bboxSizeXTF.setText(geoTransform.getBboxSizeX());
    bboxSizeYTF.setText(geoTransform.getBboxSizeY());
    bboxSizeZTF.setText(geoTransform.getBboxSizeZ());
    geoCenterXTF.setText(geoTransform.getGeoCenterX());
    geoCenterYTF.setText(geoTransform.getGeoCenterY());
    centerZTf.setText(geoTransform.getGeoCenterZ());
    rotationXaxisTF.setText(geoTransform.getRotationX());
    rotationYaxisTF.setText(geoTransform.getRotationY());
    rotationZaxisTF.setText(geoTransform.getRotationZ());
    rotationAngleTF.setText(geoTransform.getRotationAngle());
    scaleOrientationXaxisTF.setText(geoTransform.getScaleOrientationX());
    scaleOrientationYaxisTF.setText(geoTransform.getScaleOrientationY());
    scaleOrientationZaxisTF.setText(geoTransform.getScaleOrientationZ());
    scaleOrientationAngleTF.setText(geoTransform.getScaleOrientationAngle());
    scaleXTF.setText(geoTransform.getScaleX());
    scaleYTF.setText(geoTransform.getScaleY());
    scaleZTF.setText(geoTransform.getScaleZ());
    translationXTF.setText(geoTransform.getTranslationX());
    translationYTF.setText(geoTransform.getTranslationY());
    translationZTF.setText(geoTransform.getTranslationZ());
    
    geoSystemCB.setSelectedItem(BaseX3DElement.unQuotify(geoTransform.getGeoSystem()));

    geoCenterXTF.setToolTipText(DegreesDecimalToDegreesMinutesSeconds(geoCenterXTF.getText()));
    geoCenterYTF.setToolTipText(DegreesDecimalToDegreesMinutesSeconds(geoCenterYTF.getText()));

    // indicate degree values in tooltips
    double angle;
    angle = new SFDouble(rotationAngleTF.getText()).getValue();
    rotationAngleTF.setToolTipText(angle + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");
    angle = new SFDouble(scaleOrientationAngleTF.getText()).getValue();
    scaleOrientationAngleTF.setToolTipText(angle + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");

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

        jPanel1 = new javax.swing.JPanel();
        dEFUSEpanel = getDEFUSEpanel();
        geoSystemLabel = new javax.swing.JLabel();
        geoSystemCB = new javax.swing.JComboBox<String>();
        geoSystemPanelButton = new javax.swing.JButton();
        xLabel = new javax.swing.JLabel();
        zLabel = new javax.swing.JLabel();
        angleLabel = new javax.swing.JLabel();
        yLabel = new javax.swing.JLabel();
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
        geoCenterXTF = new javax.swing.JTextField();
        geoCenterYTF = new javax.swing.JTextField();
        centerZTf = new javax.swing.JTextField();
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
        normalizeRotationValuesButton = new javax.swing.JButton();
        bboxCenterLabel = new javax.swing.JLabel();
        bboxCenterXTF = new javax.swing.JTextField();
        bboxCenterYTF = new javax.swing.JTextField();
        bboxCenterZTF = new javax.swing.JTextField();
        bboxSizeLabel = new javax.swing.JLabel();
        bboxSizeXTF = new javax.swing.JTextField();
        bboxSizeYTF = new javax.swing.JTextField();
        bboxSizeZTF = new javax.swing.JTextField();
        scaleSelectionComboBox = new javax.swing.JComboBox<String>();
        nodeHintPanel = new javax.swing.JPanel();
        hintLabel = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(61, Short.MAX_VALUE)
                .addComponent(dEFUSEpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(61, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(dEFUSEpanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(jPanel1, gridBagConstraints);

        geoSystemLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        geoSystemLabel.setText("geoSystem");
        geoSystemLabel.setToolTipText("identifies spatial reference frame: Geodetic (GD), Geocentric (GC), Universal Transverse Mercator (UTM)");
        geoSystemLabel.setMinimumSize(new java.awt.Dimension(80, 18));
        geoSystemLabel.setPreferredSize(new java.awt.Dimension(80, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(geoSystemLabel, gridBagConstraints);

        geoSystemCB.setEditable(true);
        geoSystemCB.setModel(new DefaultComboBoxModel<>(GEOSYSTEM_ATTR_CHOICES));
        geoSystemCB.setToolTipText("identifies spatial reference frame: Geodetic (GD), Geocentric (GC), Universal Transverse Mercator (UTM)");
        geoSystemCB.setMinimumSize(new java.awt.Dimension(6, 22));
        geoSystemCB.setPreferredSize(new java.awt.Dimension(6, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(geoSystemCB, gridBagConstraints);

        geoSystemPanelButton.setText("modify");
        geoSystemPanelButton.setToolTipText("launch geoSystem panel");
        geoSystemPanelButton.setMaximumSize(new java.awt.Dimension(80, 22));
        geoSystemPanelButton.setMinimumSize(new java.awt.Dimension(80, 22));
        geoSystemPanelButton.setPreferredSize(new java.awt.Dimension(6, 22));
        geoSystemPanelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                geoSystemPanelButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(geoSystemPanelButton, gridBagConstraints);

        xLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        xLabel.setText("x");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(xLabel, gridBagConstraints);

        zLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        zLabel.setText("z");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(zLabel, gridBagConstraints);

        angleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        angleLabel.setText("angle");
        angleLabel.setToolTipText("angle in radians (can convert large degree values if > 6.28)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(angleLabel, gridBagConstraints);

        yLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        yLabel.setText("y");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(yLabel, gridBagConstraints);

        translationLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        translationLabel.setText("translation");
        translationLabel.setMinimumSize(new java.awt.Dimension(80, 18));
        translationLabel.setPreferredSize(new java.awt.Dimension(80, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(translationLabel, gridBagConstraints);

        translationXTF.setColumns(4);
        translationXTF.setPreferredSize(new java.awt.Dimension(6, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(translationXTF, gridBagConstraints);

        translationYTF.setColumns(4);
        translationYTF.setPreferredSize(new java.awt.Dimension(6, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(translationYTF, gridBagConstraints);

        translationZTF.setColumns(4);
        translationZTF.setPreferredSize(new java.awt.Dimension(6, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(translationZTF, gridBagConstraints);

        rotationLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        rotationLabel.setText("rotation");
        rotationLabel.setToolTipText("Orientation (axis, angle in radians) of children relative to local coordinate system");
        rotationLabel.setMinimumSize(new java.awt.Dimension(80, 18));
        rotationLabel.setPreferredSize(new java.awt.Dimension(80, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(rotationLabel, gridBagConstraints);

        rotationXaxisTF.setColumns(4);
        rotationXaxisTF.setPreferredSize(new java.awt.Dimension(6, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(rotationXaxisTF, gridBagConstraints);

        rotationYaxisTF.setColumns(4);
        rotationYaxisTF.setToolTipText("Orientation (axis, angle in radians) of children relative to local coordinate system");
        rotationYaxisTF.setPreferredSize(new java.awt.Dimension(6, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(rotationYaxisTF, gridBagConstraints);

        rotationZaxisTF.setColumns(4);
        rotationZaxisTF.setToolTipText("Orientation (axis, angle in radians) of children relative to local coordinate system");
        rotationZaxisTF.setPreferredSize(new java.awt.Dimension(6, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(rotationZaxisTF, gridBagConstraints);

        rotationAngleTF.setColumns(4);
        rotationAngleTF.setPreferredSize(new java.awt.Dimension(6, 22));
        rotationAngleTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotationAngleTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
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
        gridBagConstraints.gridy = 8;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(rotationCalculatorlButton, gridBagConstraints);

        centerLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        centerLabel.setText("geoCenter");
        centerLabel.setMinimumSize(new java.awt.Dimension(80, 18));
        centerLabel.setPreferredSize(new java.awt.Dimension(80, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(centerLabel, gridBagConstraints);

        geoCenterXTF.setColumns(4);
        geoCenterXTF.setPreferredSize(new java.awt.Dimension(6, 22));
        geoCenterXTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                geoCenterXTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(geoCenterXTF, gridBagConstraints);

        geoCenterYTF.setColumns(4);
        geoCenterYTF.setPreferredSize(new java.awt.Dimension(6, 22));
        geoCenterYTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                geoCenterYTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(geoCenterYTF, gridBagConstraints);

        centerZTf.setColumns(4);
        centerZTf.setPreferredSize(new java.awt.Dimension(6, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(centerZTf, gridBagConstraints);

        scaleLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        scaleLabel.setText("scale");
        scaleLabel.setToolTipText("Non-uniform x-y-z scale of child coordinate system, adjusted by center and scaleOrientation");
        scaleLabel.setMinimumSize(new java.awt.Dimension(80, 18));
        scaleLabel.setPreferredSize(new java.awt.Dimension(80, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleLabel, gridBagConstraints);

        scaleXTF.setColumns(4);
        scaleXTF.setToolTipText("Non-uniform x-y-z scale of child coordinate system, adjusted by center and scaleOrientation");
        scaleXTF.setPreferredSize(new java.awt.Dimension(6, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleXTF, gridBagConstraints);

        scaleYTF.setColumns(4);
        scaleYTF.setToolTipText("Non-uniform x-y-z scale of child coordinate system, adjusted by center and scaleOrientation");
        scaleYTF.setPreferredSize(new java.awt.Dimension(6, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleYTF, gridBagConstraints);

        scaleZTF.setColumns(4);
        scaleZTF.setToolTipText("Non-uniform x-y-z scale of child coordinate system, adjusted by center and scaleOrientation");
        scaleZTF.setPreferredSize(new java.awt.Dimension(6, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleZTF, gridBagConstraints);

        scaleOrientationLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        scaleOrientationLabel.setText("scaleOrientation");
        scaleOrientationLabel.setToolTipText("Preliminary rotation of coordinate system before scaling (to allow scaling around arbitrary orientations)");
        scaleOrientationLabel.setMinimumSize(new java.awt.Dimension(80, 18));
        scaleOrientationLabel.setPreferredSize(new java.awt.Dimension(80, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleOrientationLabel, gridBagConstraints);

        scaleOrientationXaxisTF.setColumns(4);
        scaleOrientationXaxisTF.setToolTipText("Preliminary rotation of coordinate system before scaling (to allow scaling around arbitrary orientations)");
        scaleOrientationXaxisTF.setPreferredSize(new java.awt.Dimension(6, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleOrientationXaxisTF, gridBagConstraints);

        scaleOrientationYaxisTF.setColumns(4);
        scaleOrientationYaxisTF.setToolTipText("Preliminary rotation of coordinate system before scaling (to allow scaling around arbitrary orientations)");
        scaleOrientationYaxisTF.setPreferredSize(new java.awt.Dimension(6, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleOrientationYaxisTF, gridBagConstraints);

        scaleOrientationZaxisTF.setColumns(4);
        scaleOrientationZaxisTF.setToolTipText("Preliminary rotation of coordinate system before scaling (to allow scaling around arbitrary orientations)");
        scaleOrientationZaxisTF.setPreferredSize(new java.awt.Dimension(6, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleOrientationZaxisTF, gridBagConstraints);

        scaleOrientationAngleTF.setColumns(4);
        scaleOrientationAngleTF.setPreferredSize(new java.awt.Dimension(6, 22));
        scaleOrientationAngleTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scaleOrientationAngleTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
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
        gridBagConstraints.gridy = 9;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(scaleOrientationCalculatorlButton, gridBagConstraints);

        normalizeRotationValuesButton.setText("normalize rotation and scaleOrientation values");
        normalizeRotationValuesButton.setToolTipText("for rotation and scaleOrientation fields, rescale axis values as normalized vector (unit length, ranges 0..1), reset angles [0..2pi)");
        normalizeRotationValuesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                normalizeRotationValuesButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(normalizeRotationValuesButton, gridBagConstraints);

        bboxCenterLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        bboxCenterLabel.setText("bboxCenter");
        bboxCenterLabel.setToolTipText("position offset from origin of local coordinate system");
        bboxCenterLabel.setMinimumSize(new java.awt.Dimension(80, 18));
        bboxCenterLabel.setOpaque(true);
        bboxCenterLabel.setPreferredSize(new java.awt.Dimension(80, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterLabel, gridBagConstraints);

        bboxCenterXTF.setColumns(4);
        bboxCenterXTF.setToolTipText("position offset from origin of local coordinate system");
        bboxCenterXTF.setPreferredSize(new java.awt.Dimension(6, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterXTF, gridBagConstraints);

        bboxCenterYTF.setColumns(4);
        bboxCenterYTF.setToolTipText("position offset from origin of local coordinate system");
        bboxCenterYTF.setPreferredSize(new java.awt.Dimension(6, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterYTF, gridBagConstraints);

        bboxCenterZTF.setColumns(4);
        bboxCenterZTF.setToolTipText("position offset from origin of local coordinate system");
        bboxCenterZTF.setPreferredSize(new java.awt.Dimension(6, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterZTF, gridBagConstraints);

        bboxSizeLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        bboxSizeLabel.setText("bboxSize");
        bboxSizeLabel.setToolTipText("automatically calculated, can be specified as an optimization or constraint");
        bboxSizeLabel.setMinimumSize(new java.awt.Dimension(80, 18));
        bboxSizeLabel.setPreferredSize(new java.awt.Dimension(80, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxSizeLabel, gridBagConstraints);

        bboxSizeXTF.setColumns(4);
        bboxSizeXTF.setToolTipText("automatically calculated, can be specified as an optimization or constraint");
        bboxSizeXTF.setPreferredSize(new java.awt.Dimension(6, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxSizeXTF, gridBagConstraints);

        bboxSizeYTF.setColumns(4);
        bboxSizeYTF.setToolTipText("automatically calculated, can be specified as an optimization or constraint");
        bboxSizeYTF.setPreferredSize(new java.awt.Dimension(6, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxSizeYTF, gridBagConstraints);

        bboxSizeZTF.setColumns(4);
        bboxSizeZTF.setToolTipText("automatically calculated, can be specified as an optimization or constraint");
        bboxSizeZTF.setPreferredSize(new java.awt.Dimension(6, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxSizeZTF, gridBagConstraints);

        scaleSelectionComboBox.setModel(new DefaultComboBoxModel<>(TRANSFORM_ATTR_SCALE_CHOICES));
        scaleSelectionComboBox.setToolTipText("Scale child hierarchy to meters");
        scaleSelectionComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scaleSelectionComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(scaleSelectionComboBox, gridBagConstraints);

        nodeHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nodeHintPanel.setLayout(new java.awt.GridBagLayout());

        hintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel.setText("<html> <p align=\"center\"><b>GeoTransform</b> is a Grouping node that can contain most nodes</p>");
        hintLabel.setToolTipText("close this panel to add children nodes");
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
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nodeHintPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void geoSystemPanelButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_geoSystemPanelButtonActionPerformed
    {//GEN-HEADEREND:event_geoSystemPanelButtonActionPerformed
    GeoSystemPanel gsp = new GeoSystemPanel(geoSystemCB.getSelectedItem().toString().trim());
    DialogDescriptor dd = new DialogDescriptor(gsp, "geoSystem editor");
    Dialog dial = DialogDisplayer.getDefault().createDialog(dd);
    dial.setVisible(true);
    if (dd.getValue() != DialogDescriptor.CANCEL_OPTION) {
      geoSystemCB.setSelectedItem(gsp.getGeosytemValue());
    }
    }//GEN-LAST:event_geoSystemPanelButtonActionPerformed

    private void normalizeRotationValuesButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_normalizeRotationValuesButtonActionPerformed
    {//GEN-HEADEREND:event_normalizeRotationValuesButtonActionPerformed
        checkAngles(true);
        double normalizationFactor, x, y, z, angle;

        x = new SFDouble(rotationXaxisTF.getText()).getValue();
        y = new SFDouble(rotationYaxisTF.getText()).getValue();
        z = new SFDouble(rotationZaxisTF.getText()).getValue();
        angle = new SFDouble(rotationAngleTF.getText()).getValue();
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

        x = new SFDouble(scaleOrientationXaxisTF.getText()).getValue();
        y = new SFDouble(scaleOrientationYaxisTF.getText()).getValue();
        z = new SFDouble(scaleOrientationZaxisTF.getText()).getValue();
        angle = new SFDouble(scaleOrientationAngleTF.getText()).getValue();
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

    private void scaleOrientationAngleTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_scaleOrientationAngleTFActionPerformed
    {//GEN-HEADEREND:event_scaleOrientationAngleTFActionPerformed
        checkAngles (false);
}//GEN-LAST:event_scaleOrientationAngleTFActionPerformed

    private void rotationAngleTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_rotationAngleTFActionPerformed
    {//GEN-HEADEREND:event_rotationAngleTFActionPerformed
        checkAngles (false);
    }//GEN-LAST:event_rotationAngleTFActionPerformed

    private void scaleSelectionComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scaleSelectionComboBoxActionPerformed
        int index = scaleSelectionComboBox.getSelectedIndex();
        scaleXTF.setText(TRANSFORM_ATTR_SCALE_FACTORS[index]);
        scaleYTF.setText(TRANSFORM_ATTR_SCALE_FACTORS[index]);
        scaleZTF.setText(TRANSFORM_ATTR_SCALE_FACTORS[index]);

        // also assign DEF label as descriptive documentation
        boolean priorLabelFound = false;
        for (int i=0; i < TRANSFORM_ATTR_SCALE_LABELS.length; i++) {
            if (dEFUSEpanel.getDefTF().getText().equalsIgnoreCase("Scale" + TRANSFORM_ATTR_SCALE_LABELS[i]))
                priorLabelFound = true;
        }
        if (((dEFUSEpanel.getDefTF().getText().length()==0) || priorLabelFound) && (TRANSFORM_ATTR_SCALE_LABELS[index].length() > 0))
            // of course if this DEF label is a duplicate occurrence, that is an XML validation error
            dEFUSEpanel.getDefTF().setText("Scale" + TRANSFORM_ATTR_SCALE_LABELS[index]);
        else if (priorLabelFound && (TRANSFORM_ATTR_SCALE_LABELS[index].length() == 0))
            dEFUSEpanel.getDefTF().setText("");
}//GEN-LAST:event_scaleSelectionComboBoxActionPerformed

    private void geoCenterXTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_geoCenterXTFActionPerformed
        geoCenterXTF.setToolTipText(DegreesDecimalToDegreesMinutesSeconds(geoCenterXTF.getText()));
    }//GEN-LAST:event_geoCenterXTFActionPerformed

    private void geoCenterYTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_geoCenterYTFActionPerformed
        geoCenterYTF.setToolTipText(DegreesDecimalToDegreesMinutesSeconds(geoCenterYTF.getText()));
    }//GEN-LAST:event_geoCenterYTFActionPerformed

    private void rotationCalculatorlButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rotationCalculatorlButtonActionPerformed
        RotationCalculatorPanel rotationCalculatorPanel = new RotationCalculatorPanel(geoTransform, "rotation");
        rotationCalculatorPanel.setRotationValue (
            rotationXaxisTF.getText(),
            rotationZaxisTF.getText(),
            rotationZaxisTF.getText(),
            rotationAngleTF.getText());
        DialogDescriptor dd = new DialogDescriptor(rotationCalculatorPanel, "Rotation Calculator for GeoTransform rotation");
        Dialog dialog = DialogDisplayer.getDefault().createDialog(dd);
        dialog.setVisible(true);
        if (dd.getValue() != DialogDescriptor.CANCEL_OPTION)
        {
            // save values
            if (!rotationCalculatorPanel.getRotationResult().isEmpty())
            {
                rotationXaxisTF.setText(rotationCalculatorPanel.getRotationResultX());
                rotationZaxisTF.setText(rotationCalculatorPanel.getRotationResultY());
                rotationZaxisTF.setText(rotationCalculatorPanel.getRotationResultZ());
                rotationAngleTF.setText(rotationCalculatorPanel.getRotationResultAngle());
                rotationAngleTF.setToolTipText(rotationCalculatorPanel.getRotationResultAngle() + " radians = " +
                    singleDigitFormat.format(Float.parseFloat(rotationCalculatorPanel.getRotationResultAngle()) * 180.0 / Math.PI) + " degrees");
            }
        }
    }//GEN-LAST:event_rotationCalculatorlButtonActionPerformed

    private void scaleOrientationCalculatorlButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scaleOrientationCalculatorlButtonActionPerformed
        RotationCalculatorPanel scaleOrientationCalculatorPanel = new RotationCalculatorPanel(geoTransform, "scaleOrientation");
        scaleOrientationCalculatorPanel.setRotationValue (
            scaleOrientationXaxisTF.getText(),
            scaleOrientationYaxisTF.getText(),
            scaleOrientationZaxisTF.getText(),
            scaleOrientationAngleTF.getText());
        DialogDescriptor dd = new DialogDescriptor(scaleOrientationCalculatorPanel, "Rotation Calculator for GeoTransform scaleOrientation");
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
    private javax.swing.JTextField centerZTf;
    private org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel;
    private javax.swing.JTextField geoCenterXTF;
    private javax.swing.JTextField geoCenterYTF;
    private javax.swing.JComboBox<String> geoSystemCB;
    private javax.swing.JLabel geoSystemLabel;
    private javax.swing.JButton geoSystemPanelButton;
    private javax.swing.JLabel hintLabel;
    private javax.swing.JPanel jPanel1;
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
    return "NAME_X3D_GEOTRANSFORM";
  }

  private void checkAngles(boolean precedesNormalization)
  {
      // usability note:  can enter degree values (-6..+6) as (354..366) to provoke this conversion check
      double angle = new SFDouble(rotationAngleTF.getText()).getValue();
      rotationAngleTF.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");
      if (Math.abs(angle) > 2.0 * Math.PI)
      {
            String message;
            message = "<html><center>Large value provided for <b>rotation</b> angle, which is a radians value.<br/><br/>Convert <b>" + angle + " degrees</b> to <b>" +
                    radiansFormat.format(angle * Math.PI / 180.0) + " radians</b>";
            if (precedesNormalization)
                 message += " before normalization?";
            else message += "?";
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  message, "X3D angles are in radians", NotifyDescriptor.YES_NO_OPTION);
          if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
          {
              angle = angle * Math.PI / 180.0;
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
  public void unloadInput()
  {
    checkAngles (false);
    
    geoTransform.setBboxCenterX(bboxCenterXTF.getText().trim());
    geoTransform.setBboxCenterY(bboxCenterYTF.getText().trim());
    geoTransform.setBboxCenterZ(bboxCenterZTF.getText().trim());
    geoTransform.setBboxSizeX(bboxSizeXTF.getText().trim());
    geoTransform.setBboxSizeY(bboxSizeYTF.getText().trim());
    geoTransform.setBboxSizeZ(bboxSizeZTF.getText().trim());
    geoTransform.setGeoCenterX(geoCenterXTF.getText().trim());
    geoTransform.setGeoCenterY(geoCenterYTF.getText().trim());
    geoTransform.setGeoCenterZ(centerZTf.getText().trim());
    geoTransform.setRotationX(rotationXaxisTF.getText().trim());
    geoTransform.setRotationY(rotationYaxisTF.getText().trim());
    geoTransform.setRotationZ(rotationZaxisTF.getText().trim());
    geoTransform.setRotationAngle(rotationAngleTF.getText().trim());
    geoTransform.setScaleOrientationX(scaleOrientationXaxisTF.getText().trim());
    geoTransform.setScaleOrientationY(scaleOrientationYaxisTF.getText().trim());
    geoTransform.setScaleOrientationZ(scaleOrientationZaxisTF.getText().trim());
    geoTransform.setScaleOrientationAngle(scaleOrientationAngleTF.getText().trim());
    geoTransform.setScaleX(scaleXTF.getText().trim());
    geoTransform.setScaleY(scaleYTF.getText().trim());
    geoTransform.setScaleZ(scaleZTF.getText().trim());
    geoTransform.setTranslationX(translationXTF.getText().trim());
    geoTransform.setTranslationY(translationYTF.getText().trim());
    geoTransform.setTranslationZ(translationZTF.getText().trim());
    geoTransform.setGeoSystem(BaseX3DElement.splitStringIntoStringArray(geoSystemCB.getSelectedItem().toString().trim()));
  }   
}
