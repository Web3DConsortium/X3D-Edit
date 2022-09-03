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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import java.util.Vector;
import javax.swing.text.JTextComponent;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.labels.CustomXYToolTipGenerator;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.HorizontalAlignment;
import org.jfree.ui.RectangleEdge;
import org.jfree.ui.RectangleInsets;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFDouble;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFFloat;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * extrusionCustomizer.java
 * Created on Sep 12, 2007, 3:05 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
// Use EXTRUSIONTabbedPaneCustomizer instead
@Deprecated
public class EXTRUSIONCustomizer extends BaseCustomizer
{
    private EXTRUSION extrusion;
    private JTextComponent target;
    private ChartPanel chartPanel;
    private JFreeChart chart;

    private boolean showEditEnhancements, insertCommas, insertLineBreaks = false;

    public EXTRUSIONCustomizer(EXTRUSION extrusion, JTextComponent target)
    {
        super(extrusion);
        this.extrusion = extrusion;
        this.target = target;

        HelpCtx.setHelpIDString(this, "EXTRUSION_ELEM_HELPID");
        
        extrusion.setVisualizationSelectionAvailable(true); // must precede initComponents() interface initialization
        extrusion.setVisualizationTooltip("hide original Extrusion and append ExtrusionCrossSection instead");

        initComponents();

        ccwCB.setSelected(extrusion.isCcw());
        convexCB.setSelected(extrusion.isConvex());
        creaseAngleTF.setText(extrusion.getCreaseAngle());

        endCapCB.setSelected(extrusion.isEndCap());
        beginCapCB.setSelected(extrusion.isBeginCap());
        solidCB.setSelected(extrusion.isSolid());

        expandableListCrossSection.setTitle("crossSection array");
        expandableListCrossSection.setColumnTitles(new String[]{"#","x","y"});
        expandableListCrossSection.doIndexInFirstColumn(true);
        expandableListCrossSection.setHeaderTooltip("Extrusion crossSection (x y) values");
        expandableListCrossSection.setCellEditPanelVisible(false);
        String[][] sa = extrusion.getCrossSection(); // may be 0-length
        expandableListCrossSection.setData(sa);
        checkCrossSectionArrayCounts();

        expandableListSpine.setTitle("spine array");
        expandableListSpine.setColumnTitles(new String[]{"#","x","y","z"});
        expandableListSpine.doIndexInFirstColumn(true);
        expandableListSpine.setHeaderTooltip("Extrusion spine (x y z) values");
        expandableListSpine.setCellEditPanelVisible(false);
        sa = extrusion.getSpine(); // may be 0-length
        expandableListSpine.setData(sa);
        checkSpineArrayCounts();

        expandableListScale.setTitle("scale array");
        expandableListScale.setColumnTitles(new String[]{"#","x","y"});
        expandableListScale.setNewRowData(new String[]{"1","1"});
        expandableListScale.doIndexInFirstColumn(true);
        expandableListScale.setHeaderTooltip("Extrusion scale (x y) values");
        expandableListScale.setCellEditPanelVisible(false);
        sa = extrusion.getScale(); // may be 0-length
        expandableListScale.setData(sa);
        checkScaleArrayCounts();

        expandableListOrientation.setTitle("orientation array");
        expandableListOrientation.setColumnTitles(new String[]{"#","x","y","z","angle"});
        expandableListOrientation.setNewRowData(new String[]{"0","1","0","0"});
        expandableListOrientation.doIndexInFirstColumn(true);
        expandableListOrientation.setHeaderTooltip("Extrusion orientation (x y z angle) values");
        expandableListOrientation.setCellEditPanelVisible(false);
        sa = extrusion.getOrientation(); // may be 0-length
        expandableListOrientation.setData(sa);
        expandableListOrientation.setAngleColumn(4);  // no key entry, 4-tuple orientation entry means column 4
        checkOrientationArrayCounts();

        checkAngles(false);

        updateJFreeChartCrossSectionPlot();

        insertCommasCheckBox.setSelected(extrusion.isInsertCommas());
    insertLineBreaksCheckBox.setSelected(extrusion.isInsertLineBreaks());

    }

    /**
     * Make an (empty) chart
     * @return
     */
    private JFreeChart chartInstance() // TODO unused?
    {
      JFreeChart mychart = ChartFactory.createXYLineChart(
            "crossSection plot",
            "", // x axis label
            "", // y axis label
            new XYSeriesCollection(), // data
            PlotOrientation.VERTICAL,
            false, // include legend
            true, // tooltips
            false // urls
        );
        // chart customization
        mychart.setBackgroundPaint(Color.white);
        TextTitle title = new TextTitle("crossSection array");
        title.setFont(new Font("SansSerif", Font.PLAIN, 14));
        title.setPosition(RectangleEdge.TOP);
        title.setHorizontalAlignment(HorizontalAlignment.LEFT);
        mychart.setTitle(title);
        return mychart;
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        expandableListSpine = new org.web3d.x3d.palette.items.ExpandableList();
        expandableListCrossSection = new org.web3d.x3d.palette.items.ExpandableList();
        expandableListScale = new org.web3d.x3d.palette.items.ExpandableList();
        expandableListOrientation = new org.web3d.x3d.palette.items.ExpandableList();
        attributesPanel = new javax.swing.JPanel();
        org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel1 = getDEFUSEpanel();
        attributeSubPanel = new javax.swing.JPanel();
        ccwLabel = new javax.swing.JLabel();
        ccwCB = new javax.swing.JCheckBox();
        endCapLabel = new javax.swing.JLabel();
        endCapCB = new javax.swing.JCheckBox();
        convexLabel = new javax.swing.JLabel();
        convexCB = new javax.swing.JCheckBox();
        beginCapLabel = new javax.swing.JLabel();
        beginCapCB = new javax.swing.JCheckBox();
        solidLabel = new javax.swing.JLabel();
        solidCB = new javax.swing.JCheckBox();
        creaseAngleLabel = new javax.swing.JLabel();
        creaseAngleTF = new javax.swing.JTextField();
        normalizeOrientationValuesButton = new javax.swing.JButton();
        replotButton = new javax.swing.JButton();
        appendLabel = new javax.swing.JLabel();
        insertCommasCheckBox = new javax.swing.JCheckBox();
        insertLineBreaksCheckBox = new javax.swing.JCheckBox();
        jFreeChartPanel = new javax.swing.JPanel();
        verticalSpacerLabel = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        expandableListSpine.setToolTipText("spine is list of 3D points for path along which crossSection is extruded");
        expandableListSpine.setMinimumSize(new java.awt.Dimension(300, 240));
        expandableListSpine.setPreferredSize(new java.awt.Dimension(300, 240));
        expandableListSpine.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                expandableListSpinePropertyChange(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(expandableListSpine, gridBagConstraints);

        expandableListCrossSection.setToolTipText("2D point curve outlining silhouette of Extrusion outer surface");
        expandableListCrossSection.setAutoscrolls(true);
        expandableListCrossSection.setMinimumSize(new java.awt.Dimension(300, 300));
        expandableListCrossSection.setPreferredSize(new java.awt.Dimension(300, 300));
        expandableListCrossSection.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                expandableListCrossSectionPropertyChange(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(expandableListCrossSection, gridBagConstraints);

        expandableListScale.setToolTipText("2D scaling applied to crossSection at each spine point");
        expandableListScale.setMinimumSize(new java.awt.Dimension(300, 240));
        expandableListScale.setPreferredSize(new java.awt.Dimension(300, 240));
        expandableListScale.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                expandableListScalePropertyChange(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(expandableListScale, gridBagConstraints);

        expandableListOrientation.setToolTipText("axis-angle orientation applied to crossSection at each spine point");
        expandableListOrientation.setMinimumSize(new java.awt.Dimension(300, 240));
        expandableListOrientation.setPreferredSize(new java.awt.Dimension(300, 240));
        expandableListOrientation.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                expandableListOrientationPropertyChange(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.34;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(expandableListOrientation, gridBagConstraints);

        attributesPanel.setMinimumSize(new java.awt.Dimension(300, 300));
        attributesPanel.setPreferredSize(new java.awt.Dimension(300, 300));
        attributesPanel.setLayout(new java.awt.GridBagLayout());

        dEFUSEpanel1.setMinimumSize(new java.awt.Dimension(200, 160));
        dEFUSEpanel1.setPreferredSize(new java.awt.Dimension(280, 160));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        attributesPanel.add(dEFUSEpanel1, gridBagConstraints);

        attributeSubPanel.setBorder(javax.swing.BorderFactory.createLineBorder(java.awt.Color.lightGray));
        attributeSubPanel.setMinimumSize(new java.awt.Dimension(200, 150));
        attributeSubPanel.setPreferredSize(new java.awt.Dimension(200, 150));
        attributeSubPanel.setLayout(new java.awt.GridBagLayout());

        ccwLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        ccwLabel.setText("ccw");
        ccwLabel.setToolTipText("counterclockwise ordering of vertex coordinates");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 45, 5, 5);
        attributeSubPanel.add(ccwLabel, gridBagConstraints);

        ccwCB.setToolTipText("counterclockwise ordering of vertex coordinates");
        ccwCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        attributeSubPanel.add(ccwCB, gridBagConstraints);

        endCapLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        endCapLabel.setText("endCap");
        endCapLabel.setToolTipText("whether ending cap is drawn");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        attributeSubPanel.add(endCapLabel, gridBagConstraints);

        endCapCB.setToolTipText("whether ending cap is drawn");
        endCapCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        attributeSubPanel.add(endCapCB, gridBagConstraints);

        convexLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        convexLabel.setText("convex");
        convexLabel.setToolTipText("whether all polygons are convex (true), or possibly concave (false)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 35, 5, 5);
        attributeSubPanel.add(convexLabel, gridBagConstraints);

        convexCB.setToolTipText("whether all polygons are convex (true), or possibly concave (false)");
        convexCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        attributeSubPanel.add(convexCB, gridBagConstraints);

        beginCapLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        beginCapLabel.setText("beginCap");
        beginCapLabel.setToolTipText("whether beginning cap is drawn");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        attributeSubPanel.add(beginCapLabel, gridBagConstraints);

        beginCapCB.setToolTipText("whether beginning cap is drawn");
        beginCapCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        attributeSubPanel.add(beginCapCB, gridBagConstraints);

        solidLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        solidLabel.setText("solid");
        solidLabel.setToolTipText("solid true means draw only one side of polygons");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 35, 5, 5);
        attributeSubPanel.add(solidLabel, gridBagConstraints);

        solidCB.setToolTipText("solid true means draw only one side of polygons");
        solidCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        attributeSubPanel.add(solidCB, gridBagConstraints);

        creaseAngleLabel.setText("creaseAngle");
        creaseAngleLabel.setToolTipText("creaseAngle (in radians) where adjacent polygons are drawn with sharp edges or smooth shading");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 21, 5, 0);
        attributeSubPanel.add(creaseAngleLabel, gridBagConstraints);

        creaseAngleTF.setToolTipText("creaseAngle (in radians) where adjacent polygons are drawn with sharp edges or smooth shading");
        creaseAngleTF.setMaximumSize(new java.awt.Dimension(70, 22));
        creaseAngleTF.setMinimumSize(new java.awt.Dimension(100, 14));
        creaseAngleTF.setPreferredSize(new java.awt.Dimension(100, 14));
        creaseAngleTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                creaseAngleTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        attributeSubPanel.add(creaseAngleTF, gridBagConstraints);

        normalizeOrientationValuesButton.setText("normalize orientations");
        normalizeOrientationValuesButton.setToolTipText("<html><center>normalize all orientation array axis-angle values<br /> axes unit length [0..1], normalized angles [0..2pi)");
        normalizeOrientationValuesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                normalizeOrientationValuesButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        attributeSubPanel.add(normalizeOrientationValuesButton, gridBagConstraints);

        replotButton.setText("replot crossSection");
        replotButton.setToolTipText("redraw plotted graph using saved crossSection data");
        replotButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                replotButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 15, 5, 5);
        attributeSubPanel.add(replotButton, gridBagConstraints);

        appendLabel.setFont(new java.awt.Font("Tahoma", 2, 11));
        appendLabel.setLabelFor(insertCommasCheckBox);
        appendLabel.setText(org.openide.util.NbBundle.getMessage(EXTRUSIONCustomizer.class, "ExpandableList.appendLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        attributeSubPanel.add(appendLabel, gridBagConstraints);

        insertCommasCheckBox.setFont(new java.awt.Font("Tahoma", 2, 11));
        insertCommasCheckBox.setText(org.openide.util.NbBundle.getMessage(EXTRUSIONCustomizer.class, "ExpandableList.insertCommasCheckBox.text")); // NOI18N
        insertCommasCheckBox.setToolTipText(NbBundle.getMessage(getClass(),"ExpandableList.TTIP_COMMAS"));
        insertCommasCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertCommasCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        attributeSubPanel.add(insertCommasCheckBox, gridBagConstraints);

        insertLineBreaksCheckBox.setFont(new java.awt.Font("Tahoma", 2, 11));
        insertLineBreaksCheckBox.setText(org.openide.util.NbBundle.getMessage(EXTRUSIONCustomizer.class, "ExpandableList.insertLineBreaksCheckBox.text")); // NOI18N
        insertLineBreaksCheckBox.setToolTipText(NbBundle.getMessage(getClass(),"ExpandableList.TTIP_LINE_BREAKS"));
        insertLineBreaksCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertLineBreaksCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        attributeSubPanel.add(insertLineBreaksCheckBox, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 5, 5);
        attributesPanel.add(attributeSubPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(attributesPanel, gridBagConstraints);

        jFreeChartPanel.setBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.disabledText")));
        jFreeChartPanel.setMinimumSize(new java.awt.Dimension(300, 300));
        jFreeChartPanel.setPreferredSize(new java.awt.Dimension(300, 300));
        jFreeChartPanel.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.34;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(jFreeChartPanel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 6, 0);
        add(verticalSpacerLabel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void normalizeOrientationValuesButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_normalizeOrientationValuesButtonActionPerformed
    {//GEN-HEADEREND:event_normalizeOrientationValuesButtonActionPerformed
        checkAngles(true);
        double normalizationFactor, x, y, z, angle;

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

        String[][] saa = expandableListOrientation.getData();
        if (saa.length == 0)
        {
            return;
        }
        else
        {
            for (int i = 0; i < saa.length; i++)
            {
                x     = new SFDouble(saa[i][0]).getValue();
                y     = new SFDouble(saa[i][1]).getValue();
                z     = new SFDouble(saa[i][2]).getValue();
                angle = new SFDouble(saa[i][3]).getValue();
                normalizationFactor = Math.sqrt(x * x + y * y + z * z);
                if (normalizationFactor == 0.0)
                {
                    NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                            "<html><p align='center'>Found orientation[" + i + "] has zero-magnitude axis, reset to 0 1 0<br /><br />" +
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
                saa[i][3] = radiansFormat.format(angle);
            }
        }
        expandableListOrientation.setData(saa);
}//GEN-LAST:event_normalizeOrientationValuesButtonActionPerformed

    private void expandableListSpinePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_expandableListSpinePropertyChange
        checkSpineArrayCounts();
    }//GEN-LAST:event_expandableListSpinePropertyChange

    private void expandableListCrossSectionPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_expandableListCrossSectionPropertyChange
        checkCrossSectionArrayCounts();
        updateJFreeChartCrossSectionPlot();
    }//GEN-LAST:event_expandableListCrossSectionPropertyChange

    private void expandableListScalePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_expandableListScalePropertyChange
        checkScaleArrayCounts();
    }//GEN-LAST:event_expandableListScalePropertyChange

    private void expandableListOrientationPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_expandableListOrientationPropertyChange
        checkOrientationArrayCounts();
    }//GEN-LAST:event_expandableListOrientationPropertyChange

    private void creaseAngleTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_creaseAngleTFActionPerformed
        checkCreaseAngle (false);
    }//GEN-LAST:event_creaseAngleTFActionPerformed

    private void replotButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_replotButtonActionPerformed
        updateJFreeChartCrossSectionPlot();
    }//GEN-LAST:event_replotButtonActionPerformed

    private void insertCommasCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertCommasCheckBoxActionPerformed
        setInsertCommas(insertCommasCheckBox.isSelected());
}//GEN-LAST:event_insertCommasCheckBoxActionPerformed

    private void insertLineBreaksCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertLineBreaksCheckBoxActionPerformed
        setInsertLineBreaks(insertLineBreaksCheckBox.isSelected());
}//GEN-LAST:event_insertLineBreaksCheckBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel appendLabel;
    private javax.swing.JPanel attributeSubPanel;
    private javax.swing.JPanel attributesPanel;
    private javax.swing.JCheckBox beginCapCB;
    private javax.swing.JLabel beginCapLabel;
    private javax.swing.JCheckBox ccwCB;
    private javax.swing.JLabel ccwLabel;
    private javax.swing.JCheckBox convexCB;
    private javax.swing.JLabel convexLabel;
    private javax.swing.JLabel creaseAngleLabel;
    private javax.swing.JTextField creaseAngleTF;
    private javax.swing.JCheckBox endCapCB;
    private javax.swing.JLabel endCapLabel;
    private org.web3d.x3d.palette.items.ExpandableList expandableListCrossSection;
    private org.web3d.x3d.palette.items.ExpandableList expandableListOrientation;
    private org.web3d.x3d.palette.items.ExpandableList expandableListScale;
    private org.web3d.x3d.palette.items.ExpandableList expandableListSpine;
    private javax.swing.JCheckBox insertCommasCheckBox;
    private javax.swing.JCheckBox insertLineBreaksCheckBox;
    private javax.swing.JPanel jFreeChartPanel;
    private javax.swing.JButton normalizeOrientationValuesButton;
    private javax.swing.JButton replotButton;
    private javax.swing.JCheckBox solidCB;
    private javax.swing.JLabel solidLabel;
    private javax.swing.JLabel verticalSpacerLabel;
    // End of variables declaration//GEN-END:variables

    @Override
    public String getNameKey()
    {
        return "NAME_X3D_EXTRUSION";
    }

    private void updateJFreeChartCrossSectionPlot ()
    {        
        XYSeries crossSectionSeries = new XYSeries("crossSection", false, true); // avoid title, autosort off, multiple x values allowed
        // TODO split out initialization and refresh
        crossSectionSeries.clear();
        String[][] saa = expandableListCrossSection.getData();
        List<String> tooltipList = new Vector<String>();
        for (int i = 0; i < saa.length; i++)
        {
            if ((saa[i][0] != null) && (saa[i][1] != null)&& (saa[i][0].length() > 0) &&(saa[i][1].length() > 0) )
            {
                double x = new SFDouble(saa[i][0]).getValue();
                double y = new SFDouble(saa[i][1]).getValue();
                crossSectionSeries.add(x, y);
                tooltipList.add("crossSection [" + String.valueOf(i) + "] = (" + singleDigitFormat.format(x) + ", " + singleDigitFormat.format(y) + ")");
            }
        }
        XYSeriesCollection datasetCollection = new XYSeriesCollection();
        datasetCollection.addSeries(crossSectionSeries);

        // create a chart...
        chart = ChartFactory.createXYLineChart(
            "crossSection plot",
            "", // x axis label
            "", // y axis label
            datasetCollection, // data
            PlotOrientation.VERTICAL,
            false, // include legend
            true, // tooltips
            false // urls
        );
        // chart customization
        chart.setBackgroundPaint(Color.white);
        TextTitle title = new TextTitle("crossSection array");
        title.setFont(new Font("SansSerif", Font.PLAIN, 14));
        title.setPosition(RectangleEdge.TOP);
        title.setHorizontalAlignment(HorizontalAlignment.LEFT);
        chart.setTitle(title);
        chart.fireChartChanged(); // in case of redraw

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);
        // slightly offset axes from the edges of the plot area
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.getDomainAxis().setAutoRange(true);
        plot.getRangeAxis().setAutoRange(true);

        // change the auto tick unit selection to integer units only...
        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        domainAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        domainAxis.setAutoRange(true);
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setAutoRange(true);

        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setBaseShapesVisible(true);
        renderer.setBaseShapesFilled(true);
        renderer.setDrawSeriesLineAsPath(true);
        
        CustomXYToolTipGenerator generator = new CustomXYToolTipGenerator ();
        generator.addToolTipSeries(tooltipList);
        renderer.setBaseToolTipGenerator(generator);
        
        if  (chartPanel == null)
             chartPanel = new ChartPanel(chart);
        else chartPanel.setChart (chart);
        chartPanel.setZoomOutFactor(1.1);
        
        double centerX = (crossSectionSeries.getMinX() + crossSectionSeries.getMaxX()) / 2.0;
        double centerY = (crossSectionSeries.getMinY() + crossSectionSeries.getMaxY()) / 2.0;
        chartPanel.zoomOutBoth(centerX, centerY);
        
        // match width and height of adjacent tables
        chartPanel.setPreferredSize(new Dimension(expandableListOrientation.getWidth(), expandableListCrossSection.getHeight()));

        jFreeChartPanel.removeAll();
        jFreeChartPanel.add(chartPanel, BorderLayout.CENTER);
        jFreeChartPanel.validate (); // necessary for display after change
    }

    private void checkAngles(boolean precedesNormalization)
    {
        checkCreaseAngle      (precedesNormalization);
        checkOrientationAngles(precedesNormalization);
    }

    public void checkOrientationAngles(boolean precedesNormalization)
    {
        // usability note:  can enter degree values (-6..+6) as (354..366) to provoke this conversion check
        String[][] saa = expandableListOrientation.getData();

        for (int i = 0; i < saa.length; i++)
        {
            double angle = Double.valueOf(saa[i][3]);
            if (Math.abs(angle) > 2.0 * Math.PI)
            {
                String message;
                message = "<html><p align='center'>Large value provided for <b>orientation[" + i + "] angle, which is a radians value.</b><br/><br/>Convert <b>" + angle + " degrees</b> to <b>" +
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
        expandableListOrientation.setData(saa);
    }

    public void checkCreaseAngle (boolean precedesNormalization)
    {
        // indicate degree values in tooltips
        // usability note:  can enter degree values (-6..+6) as (354..366) to provoke this conversion check
        double angle = Double.valueOf(creaseAngleTF.getText());
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

    private void checkSpineArrayCounts()
    {
        String[][] saaSpine        = expandableListSpine.getData();

        if (saaSpine == null || saaSpine.length == 0)  // restore default if table reduced to zero
        {
            expandableListSpine.setData(extrusion.getSpineDefault());
        }
        else if ((saaSpine.length == 1))
        {
            String message = "<html><center>Error: illegal <b>spine</b> length of 1<br/>" +
                    "<br/><b>spine</b> is a 3-tuple array whose length must be at least 2" +
                    "<br/><br/>Suggested fix:  add more <b>spine</b> points";
            // TODO figure out how to return to editor based on labeled Confirmation choice
//            NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
//                    message, "Extrusion spine array error", NotifyDescriptor.ERROR_MESSAGE);
            NotifyDescriptor descriptor = new NotifyDescriptor.Message(message);
            DialogDisplayer.getDefault().notify(descriptor);
        }
        else if ((saaSpine.length == 2) && saaSpine[0][0].equals(saaSpine[1][0]) && saaSpine[0][1].equals(saaSpine[1][1]) && saaSpine[0][2].equals(saaSpine[1][2]))
        {
            String message = "<html><center>Warning: coincident <b>spine</b> points" +
                    "<br/><br/><b>spine</b> array has length 2 but both points are identical" +
                    "<br/><br/>Suggested fix:  edit one <b>spine</b> point or add others";
            NotifyDescriptor descriptor = new NotifyDescriptor.Message(message);
            DialogDisplayer.getDefault().notify(descriptor);
        }
    }

    private void checkCrossSectionArrayCounts()
    {
        String[][] saaCrossSection = expandableListCrossSection.getData();
        
        if ((saaCrossSection.length == 0))  // restore default if table reduced to zero
        {
            expandableListCrossSection.setData(extrusion.getCrossSectionDefault());
        }
        else if (saaCrossSection.length == 1) // zero length (default value) is OK
        {
            String message = "<html><center>Error: illegal <b>crossSection</b> length of " + saaCrossSection.length + 
                    "<br/><br/><b>crossSection</b> is a 2-tuple array whose length must be at least 2" +
                    "<br/><br/>Suggested fix:  add more <b>crossSection</b> points";
            NotifyDescriptor descriptor = new NotifyDescriptor.Message(message);
            DialogDisplayer.getDefault().notify(descriptor);
        }
    }

    private void checkScaleArrayCounts()
    {
        String[][] saaSpine        = expandableListSpine.getData();
        String[][] saaScale        = expandableListScale.getData();
        
        if (saaScale == null || saaScale.length == 0)  // restore default if table reduced to zero
        {
            expandableListScale.setData(extrusion.getScaleDefault());
            saaScale = expandableListScale.getData();
        }
        
        if(saaSpine == null || saaSpine.length == 0)  // restore default if table reduced to zero
        {
            expandableListSpine.setData(extrusion.getSpineDefault());
            saaSpine = expandableListSpine.getData();
        }

        else if ((saaScale.length != 0) && (saaScale.length != 1) && (saaScale.length != saaSpine.length))
        {
            String message = "<html><center>Error: illegal <b>scale</b> length of " + saaScale.length + 
                    "<br/><br/><b>scale</b> is a 2-tuple array whose length can be 1" +
                    "<br/><br/>or else must match 3-tuple <b>spine</b> array length " +  saaSpine.length;
            NotifyDescriptor descriptor = new NotifyDescriptor.Message(message);
            DialogDisplayer.getDefault().notify(descriptor);
        }
        // check scale values positive
        boolean nonPositiveScaleFound = false;
        for (int i=0; i < saaScale.length; i++) {
          String s0 = saaScale[i][0];
          String s1 = saaScale[i][1];
          if (s0 == null || s0.length() <= 0 || s1 == null || s1.length() <= 0)
            continue;
          float scale0 = (new SFFloat(s0)).getValue();
          float scale1 = (new SFFloat(s1)).getValue();
          if ((scale0 <= 0.0f) || (scale1 <= 0.0f))
          {
            nonPositiveScaleFound = true;
            break;
          }
        }
        if (nonPositiveScaleFound)
        {
                String message = "<html><center>Error: illegal <b>scale</b> value" +
                    "<br/><br/><b>scale</b> values must all be greater than 0.0" +
                    "<br/><br/>Recommended fix:  make all <b>scale</b> values positive";
                NotifyDescriptor descriptor = new NotifyDescriptor.Message(message);
                DialogDisplayer.getDefault().notify(descriptor);
        }
    }

    private void checkOrientationArrayCounts()
    {
        String[][] saaSpine        = expandableListSpine.getData();
        String[][] saaOrientation  = expandableListOrientation.getData();
        
        if ((saaOrientation.length == 0))  // restore default if table reduced to zero
        {
            expandableListOrientation.setData(extrusion.getOrientationDefault());
        }
        else if ((saaOrientation.length != 0) && (saaOrientation.length != 1) && (saaOrientation.length != saaSpine.length))
        {
            String message = "<html><center>Error: illegal <b>orientation</b> length of " + saaOrientation.length + 
                    "<br/><br/><b>orientation</b> is a 4-tuple array whose length can be 1" +
                    "<br/><br/>or else must match 3-tuple <b>spine</b> array length " +  saaSpine.length;
            NotifyDescriptor descriptor = new NotifyDescriptor.Message(message);
            DialogDisplayer.getDefault().notify(descriptor);
        }
    }

    @Override
    public void unloadInput() throws IllegalArgumentException
    {
        checkAngles(true);

        unLoadDEFUSE();

        extrusion.setCcw(ccwCB.isSelected());
        extrusion.setConvex(convexCB.isSelected());
        extrusion.setCreaseAngle(creaseAngleTF.getText().trim());

        extrusion.setEndCap(endCapCB.isSelected());
        extrusion.setBeginCap(beginCapCB.isSelected());
        extrusion.setSolid(solidCB.isSelected());

        extrusion.setCrossSection(expandableListCrossSection.getData());
        extrusion.setScale(expandableListScale.getData());
        extrusion.setSpine(expandableListSpine.getData());
        extrusion.setOrientation(expandableListOrientation.getData());

        extrusion.setInsertCommas    (    insertCommasCheckBox.isSelected());
        extrusion.setInsertLineBreaks(insertLineBreaksCheckBox.isSelected());
    }

    /**
     * @return the insertCommas value
     */
    public boolean isInsertCommas() {
        return insertCommas;
    }

    /**
     * @return the insertLineBreaks value
     */
    public boolean isInsertLineBreaks() {
        return insertLineBreaks;
    }

    /**
     * @param insertCommas the insertCommas value to set
     */
    public void setInsertCommas(boolean insertCommas) {
        this.insertCommas = insertCommas;
        insertCommasCheckBox.setSelected(insertCommas);
    }

    /**
     * @param insertLineBreaks the insertLineBreaks value to set
     */
    public void setInsertLineBreaks(boolean insertLineBreaks) {
        this.insertLineBreaks = insertLineBreaks;
        insertLineBreaksCheckBox.setSelected(insertLineBreaks);
    }

    /**
     * @return the showEditEnhancements value
     */
    public boolean isShowEditEnhancements() {
        return showEditEnhancements;
    }

    /**
     * @param showEditEnhancements the showEditEnhancements value to set
     */
    public void setShowEditEnhancements(boolean showEditEnhancements) {
        this.showEditEnhancements = showEditEnhancements;
        insertCommasCheckBox.setVisible(showEditEnhancements);
    insertLineBreaksCheckBox.setVisible(showEditEnhancements);
                 appendLabel.setVisible(showEditEnhancements);
    }
}
