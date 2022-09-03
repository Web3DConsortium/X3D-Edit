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
import org.web3d.x3d.types.X3DPrimitiveTypes.SFDouble;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFInt32;
import static org.web3d.x3d.types.X3DPrimitiveTypes.radiansFormat;
import static org.web3d.x3d.types.X3DPrimitiveTypes.singleDigitFormat;
import static org.web3d.x3d.types.X3DSchemaData.GEOSYSTEM_ATTR_CHOICES;

/**
 * GEOELEVATIONGRIDCustomizer.java
 * Created on 29 March 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class GEOELEVATIONGRIDCustomizer extends BaseCustomizer
{
  private final GEOELEVATIONGRID geoElevationGrid;
  private final JTextComponent target;

  public GEOELEVATIONGRIDCustomizer(GEOELEVATIONGRID geoElevationGrid, JTextComponent target)
  {
    super(geoElevationGrid);
    this.geoElevationGrid = geoElevationGrid;
    this.target = target;

    HelpCtx.setHelpIDString(this, "GEOELEVATIONGRID_ELEM_HELPID");

    initComponents();
    
    tablePanel.setTitle("height array");
    tablePanel.setGeoElevationGrid(true);
    
    ccwCB.setSelected(geoElevationGrid.isCcw());
    //heightTA.setText(geoElevationGrid.getHeight());
    colorPerVertexCB.setSelected(geoElevationGrid.isColorPerVertex());
    creaseAngleTF.setText(geoElevationGrid.getCreaseAngle());
    normalPerVertexCB.setSelected(geoElevationGrid.isNormalPerVertex());
    solidCB.setSelected(geoElevationGrid.isSolid());

    int xDimension, zDimension;
    if (geoElevationGrid.getXDimension().equals("") || (geoElevationGrid.getXDimension() == null))
      xDimension = 0;
    else
      xDimension = new SFInt32(geoElevationGrid.getXDimension()).getValue();
    
    if (geoElevationGrid.getZDimension().equals("") || (geoElevationGrid.getZDimension() == null))
      zDimension = 0;
    else
      zDimension = new SFInt32(geoElevationGrid.getZDimension()).getValue();
    geoElevationGrid.checkHeightArray(); // initialize/modify height array if needed
    
    xSpacingTF.setText(geoElevationGrid.getXSpacing());
    zSpacingTF.setText(geoElevationGrid.getZSpacing());
    yScaleTF.setText(geoElevationGrid.getYScale());

    // set spacing values first to ensure proper tooltips created when panel data is set
    tablePanel.setXSpacing(geoElevationGrid.getXSpacingValue());
    tablePanel.setZSpacing(geoElevationGrid.getZSpacingValue());
    tablePanel.setData(xDimension, zDimension, geoElevationGrid.getHeight());
    tablePanel.setColumnWidthAndResizeStrategy(true, 75);    // set fixed column width here
    
    geoSystemCB.setSelectedItem(BaseX3DElement.unQuotify(geoElevationGrid.getGeoSystem()));
    
    String[] sa = geoElevationGrid.getGeoGridOrigin();
    geoGridOriginTF0.setText(sa[0]);
    geoGridOriginTF1.setText(sa[1]);
    geoGridOriginTF2.setText(sa[2]);

    computeXWidthLabel();
    computeZDepthLabel();

    checkAngles (false);

        insertCommasCheckBox.setSelected(geoElevationGrid.isInsertCommas());
    insertLineBreaksCheckBox.setSelected(geoElevationGrid.isInsertLineBreaks());
  }
  
  private void computeXWidthLabel() throws NumberFormatException
  {
//    if (new Float(geoElevationGrid.getXDimension()).equals(0.0f)) {
//      xWidthLabel.setText("0 m ");
//    }
//    else {
//      xWidth = (new Float(geoElevationGrid.getXDimension()) - 1.0f) * (new Float(geoElevationGrid.getXSpacing()));
//      xWidthLabel.setText(df.format(xWidth) + " m ");
//    }
  }

  private void computeZDepthLabel() throws NumberFormatException
  {
//    if (new Float(geoElevationGrid.getZDimension()).equals(0.0f)) {
//      zDepthLabel.setText("0 m ");
//    }
//    else {
//      zDepth = (new Float(geoElevationGrid.getZDimension()) - 1.0f) * (new Float(geoElevationGrid.getZSpacing()));
//      zDepthLabel.setText(df.format(zDepth) + " m ");
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
        xSpacingLabel = new javax.swing.JLabel();
        xSpacingTF = new javax.swing.JTextField();
        zSpacingLabel = new javax.swing.JLabel();
        zSpacingTF = new javax.swing.JTextField();
        yScaleLabel = new javax.swing.JLabel();
        yScaleTF = new javax.swing.JTextField();
        creaseAngleLabel = new javax.swing.JLabel();
        creaseAngleTF = new javax.swing.JTextField();
        normalizeRotationValuesButton = new javax.swing.JButton();
        ccwCB = new javax.swing.JCheckBox();
        solidCB = new javax.swing.JCheckBox();
        colorPerVertexCB = new javax.swing.JCheckBox();
        normalPerVertexCB = new javax.swing.JCheckBox();
        geoSystemButt = new javax.swing.JButton();
        geoSystemLabel = new javax.swing.JLabel();
        geoGridOriginLabel = new javax.swing.JLabel();
        originPanel = new javax.swing.JPanel();
        geoGridOriginTF0 = new javax.swing.JTextField();
        geoGridOriginTF1 = new javax.swing.JTextField();
        geoGridOriginTF2 = new javax.swing.JTextField();
        geoSystemCB = new javax.swing.JComboBox<String>();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        insertLineBreaksCheckBox = new javax.swing.JCheckBox();
        insertCommasCheckBox = new javax.swing.JCheckBox();
        tablePanel = new org.web3d.x3d.palette.items.ExpandableElevationGridTable();
        nodeHintPanel = new javax.swing.JPanel();
        hintLabel = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(700, 500));
        setLayout(new java.awt.GridBagLayout());

        dEFUSEpanel1.setMinimumSize(new java.awt.Dimension(542, 70));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel1, gridBagConstraints);

        topCenterPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        topCenterPanel.setLayout(new java.awt.GridBagLayout());

        xSpacingLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        xSpacingLabel.setText("xSpacing");
        xSpacingLabel.setToolTipText("meters distance between grid-array vertices along X direction");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        topCenterPanel.add(xSpacingLabel, gridBagConstraints);

        xSpacingTF.setColumns(7);
        xSpacingTF.setText("1.0");
        xSpacingTF.setToolTipText("meters distance between grid-array vertices along X direction");
        xSpacingTF.setMinimumSize(new java.awt.Dimension(98, 28));
        xSpacingTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xSpacingTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        topCenterPanel.add(xSpacingTF, gridBagConstraints);

        zSpacingLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        zSpacingLabel.setText("zSpacing");
        zSpacingLabel.setToolTipText("meters distance between grid-array vertices along Z direction");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        topCenterPanel.add(zSpacingLabel, gridBagConstraints);

        zSpacingTF.setColumns(7);
        zSpacingTF.setText("1.0");
        zSpacingTF.setToolTipText("meters distance between grid-array vertices along Z direction");
        zSpacingTF.setMinimumSize(new java.awt.Dimension(98, 28));
        zSpacingTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zSpacingTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        topCenterPanel.add(zSpacingTF, gridBagConstraints);

        yScaleLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        yScaleLabel.setText("yScale");
        yScaleLabel.setToolTipText("vertical exaggeration of displayed data");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        topCenterPanel.add(yScaleLabel, gridBagConstraints);

        yScaleTF.setColumns(7);
        yScaleTF.setToolTipText("vertical exaggeration of displayed data");
        yScaleTF.setMinimumSize(new java.awt.Dimension(98, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        topCenterPanel.add(yScaleTF, gridBagConstraints);

        creaseAngleLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        creaseAngleLabel.setText("creaseAngle");
        creaseAngleLabel.setToolTipText("[0,infinity) creaseAngle (radians) determines if adjacent polygons have sharp edges or smooth shading");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        topCenterPanel.add(creaseAngleLabel, gridBagConstraints);

        creaseAngleTF.setColumns(5);
        creaseAngleTF.setToolTipText("[0,infinity) creaseAngle (radians) determines if adjacent polygons have sharp edges or smooth shading");
        creaseAngleTF.setMinimumSize(new java.awt.Dimension(74, 28));
        creaseAngleTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                creaseAngleTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        topCenterPanel.add(creaseAngleTF, gridBagConstraints);

        normalizeRotationValuesButton.setText("normalize");
        normalizeRotationValuesButton.setToolTipText("reset creaseAngle [0..pi)");
        normalizeRotationValuesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                normalizeRotationValuesButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        topCenterPanel.add(normalizeRotationValuesButton, gridBagConstraints);

        ccwCB.setText("ccw");
        ccwCB.setToolTipText("ccw = counterclockwise: ordering of vertex coordinates orientation");
        ccwCB.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        ccwCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ccwCBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        topCenterPanel.add(ccwCB, gridBagConstraints);

        solidCB.setText("solid");
        solidCB.setToolTipText("solid true means draw only one side of polygons (backface culling on), solid false means draw both sides of polygons (backface culling off)");
        solidCB.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        topCenterPanel.add(solidCB, gridBagConstraints);

        colorPerVertexCB.setText("colorPerVertex");
        colorPerVertexCB.setToolTipText("whether Color node is applied per vertex (true) or per quadrilateral (false)");
        colorPerVertexCB.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        colorPerVertexCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorPerVertexCBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        topCenterPanel.add(colorPerVertexCB, gridBagConstraints);

        normalPerVertexCB.setText("normalPerVertex");
        normalPerVertexCB.setToolTipText("whether Normal vectors are applied per vertex (true) or per quadrilateral (false)");
        normalPerVertexCB.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        topCenterPanel.add(normalPerVertexCB, gridBagConstraints);

        geoSystemButt.setText("modify");
        geoSystemButt.setToolTipText("launch geoSystem panel");
        geoSystemButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                geoSystemButtActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        topCenterPanel.add(geoSystemButt, gridBagConstraints);

        geoSystemLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        geoSystemLabel.setText("geoSystem");
        geoSystemLabel.setToolTipText("Identifies spatial reference frame used (Geodetic, Universal Transverse Mercator, Geocentric) plus optional parameters");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        topCenterPanel.add(geoSystemLabel, gridBagConstraints);

        geoGridOriginLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        geoGridOriginLabel.setText("geoGridOrigin");
        geoGridOriginLabel.setToolTipText("geographic coordinate for southwest (lower-left) corner of height dataset");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        topCenterPanel.add(geoGridOriginLabel, gridBagConstraints);

        originPanel.setLayout(new java.awt.GridBagLayout());

        geoGridOriginTF0.setColumns(5);
        geoGridOriginTF0.setToolTipText("geographic coordinate for southwest (lower-left) corner of height dataset");
        geoGridOriginTF0.setMinimumSize(new java.awt.Dimension(74, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        originPanel.add(geoGridOriginTF0, gridBagConstraints);

        geoGridOriginTF1.setColumns(5);
        geoGridOriginTF1.setToolTipText("geographic coordinate for southwest (lower-left) corner of height dataset");
        geoGridOriginTF1.setMinimumSize(new java.awt.Dimension(74, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        originPanel.add(geoGridOriginTF1, gridBagConstraints);

        geoGridOriginTF2.setColumns(5);
        geoGridOriginTF2.setToolTipText("geographic coordinate for southwest (lower-left) corner of height dataset");
        geoGridOriginTF2.setMinimumSize(new java.awt.Dimension(74, 28));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        originPanel.add(geoGridOriginTF2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        topCenterPanel.add(originPanel, gridBagConstraints);

        geoSystemCB.setEditable(true);
        geoSystemCB.setModel(new DefaultComboBoxModel<>(GEOSYSTEM_ATTR_CHOICES));
        geoSystemCB.setToolTipText("Identifies spatial reference frame used (Geodetic, Universal Transverse Mercator, Geocentric) plus optional parameters");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        topCenterPanel.add(geoSystemCB, gridBagConstraints);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        topCenterPanel.add(jSeparator1, gridBagConstraints);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        topCenterPanel.add(jSeparator2, gridBagConstraints);

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        topCenterPanel.add(jSeparator3, gridBagConstraints);

        insertLineBreaksCheckBox.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        insertLineBreaksCheckBox.setText("append line breaks");
        insertLineBreaksCheckBox.setToolTipText("append line breaks after each row of source");
        insertLineBreaksCheckBox.setActionCommand("insertCommasCheckBox");
        insertLineBreaksCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        insertLineBreaksCheckBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        insertLineBreaksCheckBox.setMaximumSize(new java.awt.Dimension(124, 55));
        insertLineBreaksCheckBox.setMinimumSize(new java.awt.Dimension(124, 55));
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
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(topCenterPanel, gridBagConstraints);

        tablePanel.setMinimumSize(new java.awt.Dimension(360, 180));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(tablePanel, gridBagConstraints);

        nodeHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nodeHintPanel.setLayout(new java.awt.GridBagLayout());

        hintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel.setText("<html><p align=\"center\"><b>GeoElevationGrid</b> can contain Color/ColorRGBA, Normal and TextureCoordinate nodes.</p>");
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
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nodeHintPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void ccwCBActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_ccwCBActionPerformed
    {//GEN-HEADEREND:event_ccwCBActionPerformed
    // TODO add your handling code here:
    }//GEN-LAST:event_ccwCBActionPerformed

    private void colorPerVertexCBActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_colorPerVertexCBActionPerformed
    {//GEN-HEADEREND:event_colorPerVertexCBActionPerformed
    // TODO add your handling code here:
    }//GEN-LAST:event_colorPerVertexCBActionPerformed

    private void xSpacingTFActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_xSpacingTFActionPerformed
    {//GEN-HEADEREND:event_xSpacingTFActionPerformed
      // first set
      geoElevationGrid.setXSpacing(xSpacingTF.getText().trim());
      // then compute
      computeXWidthLabel();
      tablePanel.setXSpacing(geoElevationGrid.getXSpacingValue());
    }//GEN-LAST:event_xSpacingTFActionPerformed

    private void zSpacingTFActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_zSpacingTFActionPerformed
    {//GEN-HEADEREND:event_zSpacingTFActionPerformed
      // first set
      geoElevationGrid.setZSpacing(zSpacingTF.getText().trim());
      // then compute
      computeZDepthLabel();
      tablePanel.setZSpacing(geoElevationGrid.getZSpacingValue());
    }//GEN-LAST:event_zSpacingTFActionPerformed

    private void geoSystemButtActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_geoSystemButtActionPerformed
    {//GEN-HEADEREND:event_geoSystemButtActionPerformed
      GeoSystemPanel gsp = new GeoSystemPanel(geoSystemCB.getSelectedItem().toString().trim());
      DialogDescriptor dd = new DialogDescriptor(gsp, "GeoSystem");
      Dialog dial = DialogDisplayer.getDefault().createDialog(dd);
      dial.setVisible(true);
      if (dd.getValue() != DialogDescriptor.CANCEL_OPTION) {
        geoSystemCB.setSelectedItem(gsp.getGeosytemValue());
      }
    }//GEN-LAST:event_geoSystemButtActionPerformed

    private void normalizeRotationValuesButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_normalizeRotationValuesButtonActionPerformed
    {//GEN-HEADEREND:event_normalizeRotationValuesButtonActionPerformed
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
}//GEN-LAST:event_normalizeRotationValuesButtonActionPerformed

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
    private javax.swing.JLabel geoGridOriginLabel;
    private javax.swing.JTextField geoGridOriginTF0;
    private javax.swing.JTextField geoGridOriginTF1;
    private javax.swing.JTextField geoGridOriginTF2;
    private javax.swing.JButton geoSystemButt;
    private javax.swing.JComboBox<String> geoSystemCB;
    private javax.swing.JLabel geoSystemLabel;
    private javax.swing.JLabel hintLabel;
    private javax.swing.JCheckBox insertCommasCheckBox;
    private javax.swing.JCheckBox insertLineBreaksCheckBox;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JPanel nodeHintPanel;
    private javax.swing.JCheckBox normalPerVertexCB;
    private javax.swing.JButton normalizeRotationValuesButton;
    private javax.swing.JPanel originPanel;
    private javax.swing.JCheckBox solidCB;
    private org.web3d.x3d.palette.items.ExpandableElevationGridTable tablePanel;
    private javax.swing.JPanel topCenterPanel;
    private javax.swing.JLabel xSpacingLabel;
    private javax.swing.JTextField xSpacingTF;
    private javax.swing.JLabel yScaleLabel;
    private javax.swing.JTextField yScaleTF;
    private javax.swing.JLabel zSpacingLabel;
    private javax.swing.JTextField zSpacingTF;
    // End of variables declaration//GEN-END:variables
  @Override
  public String getNameKey()
  {
    return "NAME_X3D_GEOELEVATIONGRID";
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

    geoElevationGrid.setCcw(ccwCB.isSelected());
    geoElevationGrid.setHeight(tablePanel.getData());
    geoElevationGrid.setColorPerVertex(colorPerVertexCB.isSelected());
    geoElevationGrid.setCreaseAngle(creaseAngleTF.getText().trim());
    geoElevationGrid.setNormalPerVertex(normalPerVertexCB.isSelected());
    geoElevationGrid.setSolid(solidCB.isSelected());
    geoElevationGrid.setXSpacing(xSpacingTF.getText().trim());
    geoElevationGrid.setZSpacing(zSpacingTF.getText().trim());
    geoElevationGrid.setYScale(yScaleTF.getText().trim());
    
    geoElevationGrid.setXDimension(""+tablePanel.getNumberColumns());
    geoElevationGrid.setZDimension(""+tablePanel.getNumberRows());

    geoElevationGrid.setGeoSystem(BaseX3DElement.splitStringIntoStringArray(geoSystemCB.getSelectedItem().toString().trim()));
    geoElevationGrid.setGeoGridOrigin(
                        new String[]{geoGridOriginTF0.getText().trim(),
                                     geoGridOriginTF1.getText().trim(),
                                     geoGridOriginTF2.getText().trim()});

    geoElevationGrid.setInsertCommas    (    insertCommasCheckBox.isSelected());
    geoElevationGrid.setInsertLineBreaks(insertLineBreaksCheckBox.isSelected());
  }
}
