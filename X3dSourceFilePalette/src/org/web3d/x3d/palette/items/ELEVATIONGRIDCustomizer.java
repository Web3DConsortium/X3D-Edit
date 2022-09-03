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

import javax.swing.text.JTextComponent;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.HelpCtx;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFDouble;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFInt32;
import static org.web3d.x3d.types.X3DPrimitiveTypes.radiansFormat;
import static org.web3d.x3d.types.X3DPrimitiveTypes.singleDigitFormat;

/**
 * ELEVATIONGRIDCustomizer.java
 * Recreated on 29 March 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class ELEVATIONGRIDCustomizer extends BaseCustomizer
{
  private ELEVATIONGRID elevationGrid;
  private final JTextComponent target;
  private float xWidth,  zDepth;
  private double xSpacing, zSpacing;

  public ELEVATIONGRIDCustomizer(ELEVATIONGRID elevationGrid, JTextComponent target)
  {
    super(elevationGrid);
    this.elevationGrid = elevationGrid;
    this.target = target;

    HelpCtx.setHelpIDString(this, "ELEVATIONGRID_ELEM_HELPID");

    initComponents();
    
    tablePanel.setTitle("height values array");
    tablePanel.setGeoElevationGrid(false);
    
    ccwCB.setSelected(elevationGrid.isCcw());
    colorPerVertexCB.setSelected(elevationGrid.isColorPerVertex());
    creaseAngleTF.setText(elevationGrid.getCreaseAngle());
    normalPerVertexCB.setSelected(elevationGrid.isNormalPerVertex());
    solidCB.setSelected(elevationGrid.isSolid());

    // check initial values make sense, reinitialize if not
    int xDimension, zDimension;
    if (elevationGrid.getXDimension().equals("") || (elevationGrid.getXDimension() == null))
      xDimension = 0;
    else
      xDimension = new SFInt32(elevationGrid.getXDimension()).getValue();
    
    if (elevationGrid.getZDimension().equals("") || (elevationGrid.getZDimension() == null))
      zDimension = 0;
    else
      zDimension = new SFInt32(elevationGrid.getZDimension()).getValue();
    
    elevationGrid.checkHeightArray(); // initialize/modify height array if needed
    
    xSpacingTF.setText(elevationGrid.getXSpacing());
    zSpacingTF.setText(elevationGrid.getZSpacing());
    
    // set spacing values first to ensure proper tooltips are created when panel data is set
    tablePanel.setXSpacing(elevationGrid.getXSpacingValue());
    tablePanel.setZSpacing(elevationGrid.getZSpacingValue());
    tablePanel.setData(xDimension, zDimension, elevationGrid.getHeight());
    tablePanel.setColumnWidthAndResizeStrategy(true, 75);       // set fixed column width here
 
    computeXWidthLabel();
    computeZDepthLabel();

    checkAngles (false);

        insertCommasCheckBox.setSelected(elevationGrid.isInsertCommas());
    insertLineBreaksCheckBox.setSelected(elevationGrid.isInsertLineBreaks());
  }

  private void computeXWidthLabel() throws NumberFormatException
  {
//    if (new Float(geoElevationGrid.getXDimension()).equals(0.0f)) {
//      xWidthLabel.setText("0 m ");
//    }
//    else {
//      xWidth = (new Float(geoElevationGrid.getXDimension()) - 1.0f) * (new Float(geoElevationGrid.getXSpacing()));
//      xWidthLabel.setText(maximumDigitsFormat.format(xWidth) + " m ");
//    }
  }

  private void computeZDepthLabel() throws NumberFormatException
  {
//    if (new Float(geoElevationGrid.getZDimension()).equals(0.0f)) {
//      zDepthLabel.setText("0 m ");
//    }
//    else {
//      zDepth = (new Float(geoElevationGrid.getZDimension()) - 1.0f) * (new Float(geoElevationGrid.getZSpacing()));
//      zDepthLabel.setText(maximumDigitsFormat.format(zDepth) + " m ");
//    }
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel1 = getDEFUSEpanel();
        topCenterPanel = new javax.swing.JPanel();
        jSeparator1 = new javax.swing.JSeparator();
        xSpacingTF = new javax.swing.JTextField();
        xSpacingLabel = new javax.swing.JLabel();
        zSpacingLabel = new javax.swing.JLabel();
        zSpacingTF = new javax.swing.JTextField();
        creaseAngleLabel = new javax.swing.JLabel();
        creaseAngleTF = new javax.swing.JTextField();
        normalizeCreaseAngleButton = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        ccwCB = new javax.swing.JCheckBox();
        colorPerVertexCB = new javax.swing.JCheckBox();
        solidCB = new javax.swing.JCheckBox();
        normalPerVertexCB = new javax.swing.JCheckBox();
        jSeparator3 = new javax.swing.JSeparator();
        insertLineBreaksCheckBox = new javax.swing.JCheckBox();
        insertCommasCheckBox = new javax.swing.JCheckBox();
        tablePanel = new org.web3d.x3d.palette.items.ExpandableElevationGridTable();
        nodeHintPanel = new javax.swing.JPanel();
        hintLabel = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        setMinimumSize(new java.awt.Dimension(400, 300));
        setLayout(new java.awt.GridBagLayout());

        dEFUSEpanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(204, 204, 204)));
        dEFUSEpanel1.setMinimumSize(new java.awt.Dimension(542, 70));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        add(dEFUSEpanel1, gridBagConstraints);

        topCenterPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        topCenterPanel.setLayout(new java.awt.GridBagLayout());

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        topCenterPanel.add(jSeparator1, gridBagConstraints);

        xSpacingTF.setColumns(10);
        xSpacingTF.setText("1.0");
        xSpacingTF.setToolTipText("meters distance between grid-array vertices along X direction");
        xSpacingTF.setPreferredSize(new java.awt.Dimension(80, 20));
        xSpacingTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xSpacingTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        topCenterPanel.add(xSpacingTF, gridBagConstraints);

        xSpacingLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        xSpacingLabel.setText("xSpacing");
        xSpacingLabel.setToolTipText("meters distance between grid-array vertices along X direction");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        topCenterPanel.add(xSpacingLabel, gridBagConstraints);

        zSpacingLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        zSpacingLabel.setText("zSpacing");
        zSpacingLabel.setToolTipText("meters distance between grid-array vertices along Z direction");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        topCenterPanel.add(zSpacingLabel, gridBagConstraints);

        zSpacingTF.setColumns(10);
        zSpacingTF.setText("1.0");
        zSpacingTF.setToolTipText("meters distance between grid-array vertices along Z direction");
        zSpacingTF.setMaximumSize(new java.awt.Dimension(140, 20));
        zSpacingTF.setMinimumSize(new java.awt.Dimension(80, 20));
        zSpacingTF.setPreferredSize(new java.awt.Dimension(80, 20));
        zSpacingTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zSpacingTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        topCenterPanel.add(zSpacingTF, gridBagConstraints);

        creaseAngleLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        creaseAngleLabel.setText("creaseAngle");
        creaseAngleLabel.setToolTipText("[0,infinity) creaseAngle (radians) determines if adjacent polygons have sharp edges or smooth shading");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        topCenterPanel.add(creaseAngleLabel, gridBagConstraints);

        creaseAngleTF.setColumns(10);
        creaseAngleTF.setToolTipText("[0,infinity) creaseAngle (radians) determines if adjacent polygons have sharp edges or smooth shading");
        creaseAngleTF.setMaximumSize(new java.awt.Dimension(140, 20));
        creaseAngleTF.setMinimumSize(new java.awt.Dimension(80, 20));
        creaseAngleTF.setPreferredSize(new java.awt.Dimension(80, 20));
        creaseAngleTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                creaseAngleTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        topCenterPanel.add(creaseAngleTF, gridBagConstraints);

        normalizeCreaseAngleButton.setText("normalize creaseAngle");
        normalizeCreaseAngleButton.setToolTipText("reset creaseAngle [0..pi)");
        normalizeCreaseAngleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                normalizeCreaseAngleButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        topCenterPanel.add(normalizeCreaseAngleButton, gridBagConstraints);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        topCenterPanel.add(jSeparator2, gridBagConstraints);

        ccwCB.setText("ccw");
        ccwCB.setToolTipText("ccw = counterclockwise: ordering of vertex coordinates orientation");
        ccwCB.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        ccwCB.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        topCenterPanel.add(ccwCB, gridBagConstraints);

        colorPerVertexCB.setText("colorPerVertex");
        colorPerVertexCB.setToolTipText("whether Color node is applied per vertex (true) or per quadrilateral (false)");
        colorPerVertexCB.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        colorPerVertexCB.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        topCenterPanel.add(colorPerVertexCB, gridBagConstraints);

        solidCB.setText("solid");
        solidCB.setToolTipText("solid true means draw only one side of polygons (backface culling on), setting solid false means draw both sides of polygons (backface culling off)");
        solidCB.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        solidCB.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        topCenterPanel.add(solidCB, gridBagConstraints);

        normalPerVertexCB.setText("normalPerVertex");
        normalPerVertexCB.setToolTipText("whether Normal vectors are applied per vertex (true) or per quadrilateral (false)");
        normalPerVertexCB.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        normalPerVertexCB.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        topCenterPanel.add(normalPerVertexCB, gridBagConstraints);

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        topCenterPanel.add(jSeparator3, gridBagConstraints);

        insertLineBreaksCheckBox.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        insertLineBreaksCheckBox.setText("append line breaks");
        insertLineBreaksCheckBox.setToolTipText("append line breaks after each row of source");
        insertLineBreaksCheckBox.setActionCommand("insertCommasCheckBox");
        insertLineBreaksCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        insertLineBreaksCheckBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        insertLineBreaksCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertLineBreaksCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        topCenterPanel.add(insertLineBreaksCheckBox, gridBagConstraints);

        insertCommasCheckBox.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        insertCommasCheckBox.setText("append commas");
        insertCommasCheckBox.setToolTipText("append commas after each row of source");
        insertCommasCheckBox.setActionCommand("insertCommasCheckBox");
        insertCommasCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        insertCommasCheckBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        insertCommasCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertCommasCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        topCenterPanel.add(insertCommasCheckBox, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(topCenterPanel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(tablePanel, gridBagConstraints);

        nodeHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nodeHintPanel.setLayout(new java.awt.GridBagLayout());

        hintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel.setText("<html><p align=\"center\"><b>ElevationGrid</b> can contain <b>Color|ColorRGBA</b>, <b>Normal</b> and <b>TextureCoordinate</b> nodes.</p>");
        hintLabel.setToolTipText("close this panel to add children nodes");
        hintLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        nodeHintPanel.add(hintLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 0);
        add(nodeHintPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void xSpacingTFActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_xSpacingTFActionPerformed
    {//GEN-HEADEREND:event_xSpacingTFActionPerformed
      // first set
      elevationGrid.setXSpacing(xSpacingTF.getText().trim());
      // then compute
      computeXWidthLabel();
      tablePanel.setXSpacing(elevationGrid.getXSpacingValue());
    }//GEN-LAST:event_xSpacingTFActionPerformed

    private void zSpacingTFActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_zSpacingTFActionPerformed
    {//GEN-HEADEREND:event_zSpacingTFActionPerformed
      // first set
      elevationGrid.setZSpacing(zSpacingTF.getText().trim());
      // then compute
      computeZDepthLabel();
      tablePanel.setZSpacing(elevationGrid.getZSpacingValue());
    }//GEN-LAST:event_zSpacingTFActionPerformed

    private void normalizeCreaseAngleButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_normalizeCreaseAngleButtonActionPerformed
    {//GEN-HEADEREND:event_normalizeCreaseAngleButtonActionPerformed
        checkAngles(true);
        double angle;

        angle = new SFDouble(creaseAngleTF.getText()).getValue();
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
        creaseAngleTF.setText(radiansFormat.format(angle));
        creaseAngleTF.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");
}//GEN-LAST:event_normalizeCreaseAngleButtonActionPerformed

    private void creaseAngleTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_creaseAngleTFActionPerformed
    {//GEN-HEADEREND:event_creaseAngleTFActionPerformed
        checkAngles (false);
    }//GEN-LAST:event_creaseAngleTFActionPerformed

    private void insertCommasCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertCommasCheckBoxActionPerformed
        // handled by unloadInput ()
    }//GEN-LAST:event_insertCommasCheckBoxActionPerformed

    private void insertLineBreaksCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertLineBreaksCheckBoxActionPerformed
        // handled by unloadInput ()
    }//GEN-LAST:event_insertLineBreaksCheckBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox ccwCB;
    private javax.swing.JCheckBox colorPerVertexCB;
    private javax.swing.JLabel creaseAngleLabel;
    private javax.swing.JTextField creaseAngleTF;
    private javax.swing.JLabel hintLabel;
    private javax.swing.JCheckBox insertCommasCheckBox;
    private javax.swing.JCheckBox insertLineBreaksCheckBox;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JPanel nodeHintPanel;
    private javax.swing.JCheckBox normalPerVertexCB;
    private javax.swing.JButton normalizeCreaseAngleButton;
    private javax.swing.JCheckBox solidCB;
    private org.web3d.x3d.palette.items.ExpandableElevationGridTable tablePanel;
    private javax.swing.JPanel topCenterPanel;
    private javax.swing.JLabel xSpacingLabel;
    private javax.swing.JTextField xSpacingTF;
    private javax.swing.JLabel zSpacingLabel;
    private javax.swing.JTextField zSpacingTF;
    // End of variables declaration//GEN-END:variables
  @Override
  public String getNameKey()
  {
    return "NAME_X3D_ELEVATIONGRID";
  }

  private void checkAngles(boolean precedesNormalization)
  {
        // indicate degree values in tooltips
        // usability note:  can enter degree values (-6..+6) as (354..366) to provoke this conversion check
        double angle = new SFDouble(creaseAngleTF.getText()).getValue();
        creaseAngleTF.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");
        if (Math.abs(angle) > 2.0 * Math.PI)
        {
            String message;
            message = "<html><center>Large value provided for <b>creaseAngle</b> angle, which is a radians value.<br/><br/>Convert <b>" + angle + " degrees</b> to <b>" +
                    radiansFormat.format((angle % 360.0) * Math.PI / 180.0) + " radians</b>";
            if (precedesNormalization)
                 message += " before normalization?";
            else message += "?";
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  message, "X3D angles are in radians", NotifyDescriptor.YES_NO_OPTION);
          if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
          {
              angle = (angle % 360.0) * Math.PI / 180.0;
              creaseAngleTF.setText(radiansFormat.format(angle));
              creaseAngleTF.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");
          }
        }
        if (angle < 0.0)
        {
            String message = "<html><center>Negative value provided for <b>creaseAngle</b>.<br/><br/>Convert <b>" + angle + " radians</b> to <b>" +
                    (-angle) + " radians</b>";
            if (precedesNormalization)
                 message += " before normalization?";
            else message += "?";
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  message, "creaseAngle must be nonnegative", NotifyDescriptor.YES_NO_OPTION);
          if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
          {
              angle = Math.abs(angle);
              creaseAngleTF.setText(radiansFormat.format(angle));
              creaseAngleTF.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");
          }
        }
  }

  @Override
  public void unloadInput() throws IllegalArgumentException
  {
    checkAngles (false);
    unLoadDEFUSE();

    elevationGrid.setCcw(ccwCB.isSelected());
    elevationGrid.setHeight(tablePanel.getData());
    elevationGrid.setColorPerVertex(colorPerVertexCB.isSelected());
    elevationGrid.setCreaseAngle(creaseAngleTF.getText().trim());
    elevationGrid.setNormalPerVertex(normalPerVertexCB.isSelected());
    elevationGrid.setSolid(solidCB.isSelected());
    elevationGrid.setXSpacing(xSpacingTF.getText().trim());
    elevationGrid.setZSpacing(zSpacingTF.getText().trim());
    
    elevationGrid.setXDimension(""+tablePanel.getNumberColumns());
    elevationGrid.setZDimension(""+tablePanel.getNumberRows());

    elevationGrid.setInsertCommas    (    insertCommasCheckBox.isSelected());
    elevationGrid.setInsertLineBreaks(insertLineBreaksCheckBox.isSelected());
  }
}
