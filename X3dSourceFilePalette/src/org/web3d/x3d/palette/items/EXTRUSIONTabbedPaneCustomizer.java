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
import java.util.ArrayList;
import javax.swing.text.JTextComponent;
import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.entity.XYItemEntity;
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
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFDouble;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFFloat;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * ExtrusionTabbedPaneCustomizer.java
 * Created on Sep 12, 2007, 3:05 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class EXTRUSIONTabbedPaneCustomizer extends BaseCustomizer implements ChartMouseListener
{
    private EXTRUSION extrusion;
    private JTextComponent target;
    private ChartPanel chartPanel;
    private JFreeChart chart;

    private boolean showEditEnhancements, insertCommas, insertLineBreaks = false;
	// session preference
    private static boolean insertOpenClosedComment = true;
    
    private final String ADD_VALUES_LABEL = "add values to match spine length";

    public EXTRUSIONTabbedPaneCustomizer(EXTRUSION extrusion, JTextComponent target)
    {
        super(extrusion);
        this.extrusion = extrusion;
        this.target = target;

        HelpCtx.setHelpIDString(this, "EXTRUSION_ELEM_HELPID");
        
        extrusion.setVisualizationSelectionAvailable(true); // must precede initComponents() interface initialization
        extrusion.setVisualizationTooltip("hide original Extrusion, append ExtrusionCrossSection instead");

        initComponents();

        ccwCB.setSelected(extrusion.isCcw());
        convexCB.setSelected(extrusion.isConvex());
        creaseAngleTF.setText(extrusion.getCreaseAngle());

        endCapCB.setSelected(extrusion.isEndCap());
        beginCapCB.setSelected(extrusion.isBeginCap());
        solidCB.setSelected(extrusion.isSolid());

        expandableListCrossSection.setTitle("crossSection array");
        expandableListCrossSection.setColumnTitles  (new String[]{"#","x","y"});
        expandableListCrossSection.setColumnToolTips(new String[]{"index","crossSection point x","crossSection point y"});
        expandableListCrossSection.setHeaderTooltip ("Extrusion crossSection (x y) values");
        expandableListCrossSection.doIndexInFirstColumn(true);
        expandableListCrossSection.setFlippableRowData(true);
        String[][] sa = extrusion.getCrossSection(); // may be 0-length
        expandableListCrossSection.setData(sa);
        checkCrossSectionArrayCounts(true);
    
        expandableListCrossSection.setGeneratePointsDescriptions(COORDINATE2D_ATTR_POINT_DESCRIPTIONS);
        expandableListCrossSection.setGeneratePointsChoices(COORDINATE2D_ATTR_POINT_CHOICES); // provide choice labels for appending
        expandableListCrossSection.setGeneratePointsEnumerationValues(COORDINATE2D_ATTR_POINT_VALUES);
        expandableListCrossSection.setEnumerationValueLoopClosed(true);
		openClosedCommentCheckBox.setSelected(insertOpenClosedComment); // remember session state

        expandableListSpine.setTitle("spine array");
        expandableListSpine.setColumnTitles  (new String[]{"#","x","y","z"});
        expandableListSpine.setColumnToolTips(new String[]{"index","spine point x","spine point y","spine point z"});
        expandableListSpine.setHeaderTooltip ("Extrusion spine (x y z) values");
        expandableListSpine.doIndexInFirstColumn(true);
        sa = extrusion.getSpine(); // may be 0-length
        expandableListSpine.setData(sa);
        checkSpineArrayCounts(true);
        expandableListCrossSection.setIncludeMakeOpenClosedButton(true);
    
        expandableListSpine.setGeneratePointsDescriptions(COORDINATE_ATTR_POINT_DESCRIPTIONS);
        expandableListSpine.setGeneratePointsChoices(COORDINATE_ATTR_POINT_CHOICES); // provide choice labels for appending
        expandableListSpine.setGeneratePointsEnumerationValues(COORDINATE_ATTR_POINT_VALUES);
        expandableListSpine.setEnumerationValueLoopClosed(true);
        expandableListSpine.setIncludeMakeOpenClosedButton(true);

        expandableListScale.setTitle("scale array");
        expandableListScale.setColumnTitles  (new String[]{"#","x","y"});
        expandableListScale.setColumnToolTips(new String[]{"index","scale point x","scale point y"});
        expandableListScale.setHeaderTooltip ("Extrusion scale (x y) values");
        expandableListScale.setNewRowData(new String[]{"1","1"});
        expandableListScale.doIndexInFirstColumn(true);
        expandableListScale.setFlippableRowData(true);
        expandableListScale.preserveGeneratedPointsPanelVerticalSpacing(); // align vertically with expandableListSpine panel
        sa = extrusion.getScale(); // may be 0-length
        expandableListScale.setData(sa);
        checkScaleArrayCounts(true);

        expandableListOrientations.setTitle("orientation array");
        expandableListOrientations.setColumnTitles  (new String[]{"#","x","y","z","angle"});
        expandableListOrientations.setColumnToolTips(new String[]{"index","rotation axis x","rotation axis y","rotation axis z","rotation angle"});
        expandableListOrientations.setHeaderTooltip ("Extrusion orientation (x y z angle) values");
        expandableListOrientations.setNewRowData(new String[]{"0","1","0","0"});
        expandableListOrientations.doIndexInFirstColumn(true);
        expandableListOrientations.setFlippableRowData(true);
        sa = extrusion.getOrientation(); // may be 0-length
        expandableListOrientations.setData(sa);
        expandableListOrientations.setAngleColumn(4);  // no key entry, 4-tuple orientation entry means column 4
        checkOrientationArrayCounts(true);

        checkCreaseAngle      (false);
        checkOrientationArrayAngles(false);

        updateJFreeChartCrossSectionPlot();
//		System.out.println("isCrossSectionClosed()=" + isCrossSectionClosed()); // debug
        
        tabbedPaneNamesRefresh();

        insertCommasCheckBox.setSelected(extrusion.isInsertCommas());
    insertLineBreaksCheckBox.setSelected(extrusion.isInsertLineBreaks());
 
        // reinitialization of button as disabled must follow all property changes,
        // the original is actually controlled by widget itself
//        replotCrossSectionButton.setEnabled(false); // TODO
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

        tabbedPane = new javax.swing.JTabbedPane();
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
        insertCommasLineBreaksPanel = new javax.swing.JPanel();
        insertCommasLineBreaksLabel = new javax.swing.JLabel();
        insertCommasCheckBox = new javax.swing.JCheckBox();
        insertLineBreaksCheckBox = new javax.swing.JCheckBox();
        checkCreaseAngleButton = new javax.swing.JButton();
        insertOpenClosedCommentPanel = new javax.swing.JPanel();
        openClosedCommentLabel = new javax.swing.JLabel();
        openClosedCommentCheckBox = new javax.swing.JCheckBox();
        extrusionScpFigureLabel = new javax.swing.JLabel();
        verticalSpacer = new javax.swing.JLabel();
        crossSectionTab = new javax.swing.JPanel();
        crossSectionSplitPane = new javax.swing.JSplitPane();
        expandableListCrossSection = new org.web3d.x3d.palette.items.ExpandableList();
        replotCrossSectionButton = new javax.swing.JButton();
        jFreeChartPanel = new javax.swing.JPanel();
        spineScaleTab = new javax.swing.JPanel();
        spineScaleSplitPane = new javax.swing.JSplitPane();
        expandableListSpine = new org.web3d.x3d.palette.items.ExpandableList();
        expandableListScale = new org.web3d.x3d.palette.items.ExpandableList();
        orientationTab = new javax.swing.JPanel();
        expandableListOrientations = new org.web3d.x3d.palette.items.ExpandableList();
        normalizeOrientationArrayButton = new javax.swing.JButton();
        spacerLabel = new javax.swing.JLabel();
        matchOrientationArrayLengthButton = new javax.swing.JButton();
        spineArrayLengthLabel = new javax.swing.JLabel();
        computeOrientationAnglesButton = new javax.swing.JButton();
        orientationAnglePanel = new javax.swing.JPanel();
        initialOrientationLabel = new javax.swing.JLabel();
        initialOrientationTextField = new javax.swing.JTextField();
        finalOrientationLabel = new javax.swing.JLabel();
        finalOrientationTextField = new javax.swing.JTextField();
        extrusionScpFigureLabel1 = new javax.swing.JLabel();
        hintLabel = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(600, 500));
        setPreferredSize(new java.awt.Dimension(1000, 640));
        setRequestFocusEnabled(false);
        setLayout(new java.awt.GridBagLayout());

        tabbedPane.setToolTipText("");
        tabbedPane.setMinimumSize(new java.awt.Dimension(800, 378));
        tabbedPane.setPreferredSize(new java.awt.Dimension(1000, 500));
        tabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                tabbedPaneStateChange(evt);
            }
        });

        attributesPanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 5, 15, 5);
        attributesPanel.add(dEFUSEpanel1, gridBagConstraints);

        attributeSubPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));
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
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 35);
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
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 35);
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
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 35);
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
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 35);
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
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 35);
        attributeSubPanel.add(solidCB, gridBagConstraints);

        creaseAngleLabel.setText("creaseAngle");
        creaseAngleLabel.setToolTipText("creaseAngle (in radians) where adjacent polygons are drawn with sharp edges or smooth shading");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 3, 5, 0);
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
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        attributeSubPanel.add(creaseAngleTF, gridBagConstraints);

        insertCommasLineBreaksPanel.setLayout(new java.awt.GridBagLayout());

        insertCommasLineBreaksLabel.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        insertCommasLineBreaksLabel.setLabelFor(insertCommasCheckBox);
        insertCommasLineBreaksLabel.setText("Insert:");
        insertCommasLineBreaksLabel.setToolTipText("Insert commas or line breaks after each row");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        insertCommasLineBreaksPanel.add(insertCommasLineBreaksLabel, gridBagConstraints);

        insertCommasCheckBox.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        insertCommasCheckBox.setText(org.openide.util.NbBundle.getMessage(EXTRUSIONTabbedPaneCustomizer.class, "ExpandableList.insertCommasCheckBox.text")); // NOI18N
        insertCommasCheckBox.setToolTipText(NbBundle.getMessage(getClass(),"ExpandableList.TTIP_COMMAS"));
        insertCommasCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertCommasCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        insertCommasLineBreaksPanel.add(insertCommasCheckBox, gridBagConstraints);

        insertLineBreaksCheckBox.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        insertLineBreaksCheckBox.setText(org.openide.util.NbBundle.getMessage(EXTRUSIONTabbedPaneCustomizer.class, "ExpandableList.insertLineBreaksCheckBox.text")); // NOI18N
        insertLineBreaksCheckBox.setToolTipText(NbBundle.getMessage(getClass(),"ExpandableList.TTIP_LINE_BREAKS"));
        insertLineBreaksCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertLineBreaksCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        insertCommasLineBreaksPanel.add(insertLineBreaksCheckBox, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        attributeSubPanel.add(insertCommasLineBreaksPanel, gridBagConstraints);

        checkCreaseAngleButton.setText("normalize creaseAngle");
        checkCreaseAngleButton.setToolTipText("reset creaseAngle [0..2pi)");
        checkCreaseAngleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkCreaseAngleButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        attributeSubPanel.add(checkCreaseAngleButton, gridBagConstraints);

        insertOpenClosedCommentPanel.setLayout(new java.awt.GridBagLayout());

        openClosedCommentLabel.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        openClosedCommentLabel.setText("Insert: ");
        insertOpenClosedCommentPanel.add(openClosedCommentLabel, new java.awt.GridBagConstraints());

        openClosedCommentCheckBox.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        openClosedCommentCheckBox.setSelected(true);
        openClosedCommentCheckBox.setText("open/closed comment");
        openClosedCommentCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openClosedCommentCheckBoxActionPerformed(evt);
            }
        });
        insertOpenClosedCommentPanel.add(openClosedCommentCheckBox, new java.awt.GridBagConstraints());

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        attributeSubPanel.add(insertOpenClosedCommentPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        attributesPanel.add(attributeSubPanel, gridBagConstraints);

        extrusionScpFigureLabel.setBackground(new java.awt.Color(255, 255, 255));
        extrusionScpFigureLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        extrusionScpFigureLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/palette/items/resources/06_10.jpg"))); // NOI18N
        extrusionScpFigureLabel.setToolTipText("Spine Cross-section Plane (SCP) diagram from X3D Specification");
        extrusionScpFigureLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        attributesPanel.add(extrusionScpFigureLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        attributesPanel.add(verticalSpacer, gridBagConstraints);

        tabbedPane.addTab("attributes", attributesPanel);

        crossSectionTab.setMinimumSize(new java.awt.Dimension(750, 350));
        crossSectionTab.setPreferredSize(new java.awt.Dimension(750, 350));
        crossSectionTab.setLayout(new java.awt.GridBagLayout());

        crossSectionSplitPane.setDividerLocation(500);
        crossSectionSplitPane.setToolTipText("slide adjustment");
        crossSectionSplitPane.setMinimumSize(new java.awt.Dimension(800, 352));
        crossSectionSplitPane.setPreferredSize(new java.awt.Dimension(1150, 352));

        expandableListCrossSection.setToolTipText("crossSection array defines 2D point curve outlining silhouette of Extrusion outer surface");
        expandableListCrossSection.setAutoscrolls(true);
        expandableListCrossSection.setColumnToolTips(new String[] {});
        expandableListCrossSection.setMinimumSize(new java.awt.Dimension(350, 300));
        expandableListCrossSection.setPreferredSize(new java.awt.Dimension(900, 350));
        expandableListCrossSection.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                expandableListCrossSectionPropertyChange(evt);
            }
        });
        crossSectionSplitPane.setLeftComponent(expandableListCrossSection);

        replotCrossSectionButton.setText("replot crossSection");
        replotCrossSectionButton.setToolTipText("redraw plotted graph using saved crossSection data");
        replotCrossSectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                replotCrossSectionButtonActionPerformed(evt);
            }
        });
        crossSectionSplitPane.setRightComponent(replotCrossSectionButton);

        jFreeChartPanel.setBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.disabledText")));
        jFreeChartPanel.setMinimumSize(new java.awt.Dimension(300, 350));
        jFreeChartPanel.setPreferredSize(new java.awt.Dimension(1100, 350));
        jFreeChartPanel.setLayout(new java.awt.BorderLayout());
        crossSectionSplitPane.setRightComponent(jFreeChartPanel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.5;
        gridBagConstraints.weighty = 1.0;
        crossSectionTab.add(crossSectionSplitPane, gridBagConstraints);

        tabbedPane.addTab("crossSection array, plot", crossSectionTab);

        spineScaleTab.setMinimumSize(new java.awt.Dimension(750, 350));
        spineScaleTab.setPreferredSize(new java.awt.Dimension(750, 350));
        spineScaleTab.setRequestFocusEnabled(false);
        spineScaleTab.setLayout(new java.awt.GridBagLayout());

        spineScaleSplitPane.setDividerLocation(650);
        spineScaleSplitPane.setResizeWeight(1.0);
        spineScaleSplitPane.setLastDividerLocation(650);

        expandableListSpine.setToolTipText("spine array is list of 3D points describing central path along which crossSection is extruded");
        expandableListSpine.setMinimumSize(new java.awt.Dimension(475, 300));
        expandableListSpine.setPreferredSize(new java.awt.Dimension(700, 300));
        expandableListSpine.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                expandableListSpinePropertyChange(evt);
            }
        });
        spineScaleSplitPane.setLeftComponent(expandableListSpine);

        expandableListScale.setToolTipText("scale array defines 2D scaling applied to crossSection at each spine point");
        expandableListScale.setMinimumSize(new java.awt.Dimension(475, 300));
        expandableListScale.setPreferredSize(new java.awt.Dimension(475, 300));
        expandableListScale.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                expandableListScalePropertyChange(evt);
            }
        });
        spineScaleSplitPane.setRightComponent(expandableListScale);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        spineScaleTab.add(spineScaleSplitPane, gridBagConstraints);

        tabbedPane.addTab("spine array, scale array", spineScaleTab);

        orientationTab.setMinimumSize(new java.awt.Dimension(750, 350));
        orientationTab.setPreferredSize(new java.awt.Dimension(750, 350));
        orientationTab.setLayout(new java.awt.GridBagLayout());

        expandableListOrientations.setToolTipText("orientation array defines axis-angle rotation applied to crossSection at each spine point");
        expandableListOrientations.setMinimumSize(new java.awt.Dimension(500, 300));
        expandableListOrientations.setPreferredSize(new java.awt.Dimension(500, 300));
        expandableListOrientations.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                expandableListOrientationsPropertyChange(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        orientationTab.add(expandableListOrientations, gridBagConstraints);

        normalizeOrientationArrayButton.setText("normalize orientation array");
        normalizeOrientationArrayButton.setToolTipText("<html><center>normalize all orientation array axis-angle values<br /> axes unit length [0..1], normalized angles [0..2pi)");
        normalizeOrientationArrayButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                normalizeOrientationArrayButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        orientationTab.add(normalizeOrientationArrayButton, gridBagConstraints);

        spacerLabel.setText("       ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        orientationTab.add(spacerLabel, gridBagConstraints);

        matchOrientationArrayLengthButton.setText("add values to match spine length");
        matchOrientationArrayLengthButton.setToolTipText("add values to match spine array length: one orientation for each spine point");
        matchOrientationArrayLengthButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                matchOrientationArrayLengthButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        orientationTab.add(matchOrientationArrayLengthButton, gridBagConstraints);

        spineArrayLengthLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        spineArrayLengthLabel.setText("##");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 3, 3, 6);
        orientationTab.add(spineArrayLengthLabel, gridBagConstraints);

        computeOrientationAnglesButton.setText("compute orientation angles");
        computeOrientationAnglesButton.setToolTipText("compute new angle values for selected array entries");
        computeOrientationAnglesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                computeOrientationAnglesButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(23, 3, 3, 3);
        orientationTab.add(computeOrientationAnglesButton, gridBagConstraints);

        orientationAnglePanel.setLayout(new java.awt.GridBagLayout());

        initialOrientationLabel.setText("initial orientation angle");
        initialOrientationLabel.setToolTipText("value of first orientation angle in array");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 20, 3, 3);
        orientationAnglePanel.add(initialOrientationLabel, gridBagConstraints);

        initialOrientationTextField.setText("0.0");
        initialOrientationTextField.setToolTipText("initial value to modify orientation array");
        initialOrientationTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                initialOrientationTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 23);
        orientationAnglePanel.add(initialOrientationTextField, gridBagConstraints);

        finalOrientationLabel.setText("final orientation angle");
        finalOrientationLabel.setToolTipText("value of final orientation angle in array");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        orientationAnglePanel.add(finalOrientationLabel, gridBagConstraints);

        finalOrientationTextField.setText("0.0");
        finalOrientationTextField.setToolTipText("final value to modify orientation array");
        finalOrientationTextField.setName(""); // NOI18N
        finalOrientationTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                finalOrientationTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 20);
        orientationAnglePanel.add(finalOrientationTextField, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        orientationTab.add(orientationAnglePanel, gridBagConstraints);

        extrusionScpFigureLabel1.setBackground(new java.awt.Color(255, 255, 255));
        extrusionScpFigureLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        extrusionScpFigureLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/palette/items/resources/06_10.jpg"))); // NOI18N
        extrusionScpFigureLabel1.setToolTipText("Spine Cross-section Plane (SCP) diagram from X3D Specification");
        extrusionScpFigureLabel1.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        extrusionScpFigureLabel1.setMaximumSize(new java.awt.Dimension(400, 338));
        extrusionScpFigureLabel1.setMinimumSize(new java.awt.Dimension(400, 338));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        orientationTab.add(extrusionScpFigureLabel1, gridBagConstraints);

        tabbedPane.addTab("orientation array", orientationTab);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(tabbedPane, gridBagConstraints);

        hintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel.setText("<html><p align='center'><b>Extrusion</b> stretches, scales and rotates a 2D cross section along the path of a 3D spine. </p>");
        hintLabel.setToolTipText("A wide variety of Extrusion geometry can be constructed");
        hintLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        hintLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(hintLabel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void normalizeOrientationArrayButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_normalizeOrientationArrayButtonActionPerformed
        checkOrientationArrayAngles(true);
        double normalizationFactor, x, y, z, angle;

        String[][] saa = expandableListOrientations.getData();
        if (saa.length == 0)
        {
            return;
        }
        else
        {
            for (int i = 0; i < saa.length; i++)
            {
                x     = Double.valueOf(saa[i][0]);
                y     = Double.valueOf(saa[i][1]);
                z     = Double.valueOf(saa[i][2]);
                angle = Double.valueOf(saa[i][3]);
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
        expandableListOrientations.setData(saa);
    }//GEN-LAST:event_normalizeOrientationArrayButtonActionPerformed

    private void checkCreaseAngleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkCreaseAngleButtonActionPerformed
        checkCreaseAngle (true);
        double angle;

        angle = Double.valueOf(creaseAngleTF.getText());
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
    }//GEN-LAST:event_checkCreaseAngleButtonActionPerformed

    private void replotCrossSectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_replotCrossSectionButtonActionPerformed
        updateJFreeChartCrossSectionPlot();
//        replotCrossSectionButton.setEnabled(false); // TODO
    }//GEN-LAST:event_replotCrossSectionButtonActionPerformed

    private void insertCommasCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertCommasCheckBoxActionPerformed
        setInsertCommas(insertCommasCheckBox.isSelected());
    }//GEN-LAST:event_insertCommasCheckBoxActionPerformed

    private void insertLineBreaksCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertLineBreaksCheckBoxActionPerformed
        setInsertLineBreaks(insertLineBreaksCheckBox.isSelected());
    }//GEN-LAST:event_insertLineBreaksCheckBoxActionPerformed

    private void expandableListSpinePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_expandableListSpinePropertyChange
        if ((evt.getNewValue() != null) && evt.getNewValue().toString().equals("AppendRow1"))
        {
            String[][] saa = extrusion.getDefaultSpine();
            expandableListSpine.setData(saa);  // override single row, set default values for initial 2 rows
            checkSpineArrayCounts(false);
        }
        else checkSpineArrayCounts(false);
        if ((expandableListSpine.getData().length > 0) &&
            (expandableListCrossSection.getData().length < expandableListSpine.getData().length))
        {
            matchOrientationArrayLengthButton.setEnabled(true);
            matchOrientationArrayLengthButton.setText(ADD_VALUES_LABEL + " (" + expandableListSpine.getData().length + ")");
        }
        tabbedPaneNamesRefresh();
    }//GEN-LAST:event_expandableListSpinePropertyChange

    private void expandableListScalePropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_expandableListScalePropertyChange
        checkScaleArrayCounts(false);
        tabbedPaneNamesRefresh();
    }//GEN-LAST:event_expandableListScalePropertyChange

    private void expandableListCrossSectionPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_expandableListCrossSectionPropertyChange
        updateJFreeChartCrossSectionPlot();
        tabbedPaneNamesRefresh();
        
        String[][] saaCrossSection = expandableListCrossSection.getData();
        if ((saaCrossSection.length >= 2) && isCrossSectionClosed())
        {
            expandableListCrossSection.setTitle("crossSection array is closed (coincident endpoints)");
        }
        else if (saaCrossSection.length >= 2)
        {
            expandableListCrossSection.setTitle("crossSection array is open (distinct endpoints)");
        }
        else
        {
            expandableListCrossSection.setTitle("crossSection array");
        }
    }//GEN-LAST:event_expandableListCrossSectionPropertyChange

    boolean enableExpandableListOrientationsPropertyChange = true;
    private void expandableListOrientationsPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_expandableListOrientationsPropertyChange
        if (enableExpandableListOrientationsPropertyChange)
        {
            checkOrientationArrayCounts(false);
        }
        tabbedPaneNamesRefresh();
        if ((expandableListSpine.getData().length > 0) &&
            (expandableListCrossSection.getData().length < expandableListSpine.getData().length))
        {
            matchOrientationArrayLengthButton.setEnabled(true);
            matchOrientationArrayLengthButton.setText(ADD_VALUES_LABEL + " (" + expandableListSpine.getData().length + ")");
        }
    }//GEN-LAST:event_expandableListOrientationsPropertyChange
 
    private void matchOrientationArrayLengthButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_matchOrientationArrayLengthButtonActionPerformed
        int    goalRowCount =       expandableListSpine.getData().length;
        int initialRowCount = expandableListOrientations.getData().length;
        enableExpandableListOrientationsPropertyChange = false;
        for (int i = initialRowCount; i < goalRowCount; i++)
        {
            expandableListOrientations.appendRow(evt);
        }
        enableExpandableListOrientationsPropertyChange = true;
        checkOrientationArrayCounts(false);
        tabbedPaneNamesRefresh();
    }//GEN-LAST:event_matchOrientationArrayLengthButtonActionPerformed

    private void tabbedPaneStateChange(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_tabbedPaneStateChange
        tabbedPaneNamesRefresh();
    }//GEN-LAST:event_tabbedPaneStateChange

    private void creaseAngleTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_creaseAngleTFActionPerformed
        checkCreaseAngle (false);
    }//GEN-LAST:event_creaseAngleTFActionPerformed

    private void initialOrientationTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_initialOrientationTextFieldActionPerformed
        checkOrientationLimitAngles (false);
    }//GEN-LAST:event_initialOrientationTextFieldActionPerformed

    private void finalOrientationTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_finalOrientationTextFieldActionPerformed
        checkOrientationLimitAngles (false);
    }//GEN-LAST:event_finalOrientationTextFieldActionPerformed

    private void computeOrientationAnglesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_computeOrientationAnglesButtonActionPerformed
        checkOrientationLimitAngles (false);
        SFFloat initialAngle = new SFFloat(0.0f);
        SFFloat   finalAngle = new SFFloat(0.0f);
        if (initialOrientationTextField.getText().trim().length() > 0)
           initialAngle = new SFFloat(initialOrientationTextField.getText().trim());
        if (  finalOrientationTextField.getText().trim().length() > 0)
             finalAngle = new SFFloat(  finalOrientationTextField.getText().trim());
        expandableListOrientations.computeRotationAngles(initialAngle.getValue(), finalAngle.getValue());
    }//GEN-LAST:event_computeOrientationAnglesButtonActionPerformed

    private void openClosedCommentCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openClosedCommentCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_openClosedCommentCheckBoxActionPerformed
    private void tabbedPaneNamesRefresh() 
    {
		String tabTitle, tabTooltip;
		String crossSectionStatus = "open";
		if (isCrossSectionClosed())
			   crossSectionStatus = "closed";
		String spineStatus = "open";
		if (isSpineClosed())
			   spineStatus = "closed";
        if (tabbedPane.getTabCount() >= 4)
        {
            // pane 0 contains common attributes, name doesn't change
            if (expandableListCrossSection.getRowCount() < 2)
            {
                tabbedPane.setTitleAt      (1, "<html>crossSection array [<font color='red'>" + expandableListCrossSection.getRowCount() + "</font>] " + crossSectionStatus + ", plot display");
                tabbedPane.setToolTipTextAt(1, "need two or more points for proper crossSection");
            }
            else 
            {
				tabbedPane.setTitleAt      (1, "<html>crossSection array ["                   + expandableListCrossSection.getRowCount() + "] " + crossSectionStatus + ", plot display");
            	tabTooltip = "crossSection is extruded along spine axis";
				if (isCrossSectionClosed())
					 tabTooltip += ", crossSection array is closed (coincident endpoints)";
				else tabTooltip += ", crossSection array is open (distinct endpoints)";
                tabbedPane.setToolTipTextAt(1, tabTooltip);
			}
            
            if  (expandableListSpine.getRowCount() < 2)
            {
                tabTitle   = "<html>spine array [<font color='red'>" + expandableListSpine.getRowCount() + "</font>] " + spineStatus;
                tabTooltip = "need two or more points for proper spine";
            }
            else
            {
                tabTitle   = "<html>spine array [" +                   expandableListSpine.getRowCount() + "] " + spineStatus;
                tabTooltip = "spine points are central axis of Extrusion";
				if (isSpineClosed())
					 tabTooltip += ", spine array is closed (coincident endpoints)";
				else tabTooltip += ", spine array is open (distinct endpoints)";
            }
            if  ((expandableListScale.getRowCount() != 0) && (expandableListScale.getRowCount() != 1) && (expandableListScale.getRowCount() != expandableListSpine.getRowCount()))
            {
                tabTitle += ", scale array [<font color='red'>" + expandableListScale.getRowCount() + "</font>], ";
            }
            else
            {
                tabTitle += ", scale array [" +                   expandableListScale.getRowCount() + "]";
            }
            tabTooltip += "; scale array length must equal 0, 1, or spine length";
            tabbedPane.setTitleAt      (2,tabTitle);
            tabbedPane.setToolTipTextAt(2, tabTooltip);
                
            if  ((expandableListOrientations.getRowCount() != 0) && (expandableListOrientations.getRowCount() != 1) && (expandableListOrientations.getRowCount() != expandableListSpine.getRowCount()))
            {
                tabbedPane.setTitleAt      (3, "<html>orientation array [<font color='red'>" + expandableListOrientations.getRowCount() + "</font>]");
                tabbedPane.setToolTipTextAt(3, "orientation array length must equal 0 1 or spine length");
            }
            else 
            {
                tabbedPane.setTitleAt      (3, "<html>orientation array ["                   + expandableListOrientations.getRowCount() + "]");
                tabbedPane.setToolTipTextAt(3, "orientations are applied at each spine-aligned crossSection plane");
            }
            spineArrayLengthLabel.setText(Integer.toString(expandableListSpine.getRowCount())); // orientations tab
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel attributeSubPanel;
    private javax.swing.JPanel attributesPanel;
    private javax.swing.JCheckBox beginCapCB;
    private javax.swing.JLabel beginCapLabel;
    private javax.swing.JCheckBox ccwCB;
    private javax.swing.JLabel ccwLabel;
    private javax.swing.JButton checkCreaseAngleButton;
    private javax.swing.JButton computeOrientationAnglesButton;
    private javax.swing.JCheckBox convexCB;
    private javax.swing.JLabel convexLabel;
    private javax.swing.JLabel creaseAngleLabel;
    private javax.swing.JTextField creaseAngleTF;
    private javax.swing.JSplitPane crossSectionSplitPane;
    private javax.swing.JPanel crossSectionTab;
    private javax.swing.JCheckBox endCapCB;
    private javax.swing.JLabel endCapLabel;
    private org.web3d.x3d.palette.items.ExpandableList expandableListCrossSection;
    private org.web3d.x3d.palette.items.ExpandableList expandableListOrientations;
    private org.web3d.x3d.palette.items.ExpandableList expandableListScale;
    private org.web3d.x3d.palette.items.ExpandableList expandableListSpine;
    private javax.swing.JLabel extrusionScpFigureLabel;
    private javax.swing.JLabel extrusionScpFigureLabel1;
    private javax.swing.JLabel finalOrientationLabel;
    private javax.swing.JTextField finalOrientationTextField;
    private javax.swing.JLabel hintLabel;
    private javax.swing.JLabel initialOrientationLabel;
    private javax.swing.JTextField initialOrientationTextField;
    private javax.swing.JCheckBox insertCommasCheckBox;
    private javax.swing.JLabel insertCommasLineBreaksLabel;
    private javax.swing.JPanel insertCommasLineBreaksPanel;
    private javax.swing.JCheckBox insertLineBreaksCheckBox;
    private javax.swing.JPanel insertOpenClosedCommentPanel;
    private javax.swing.JPanel jFreeChartPanel;
    private javax.swing.JButton matchOrientationArrayLengthButton;
    private javax.swing.JButton normalizeOrientationArrayButton;
    private javax.swing.JCheckBox openClosedCommentCheckBox;
    private javax.swing.JLabel openClosedCommentLabel;
    private javax.swing.JPanel orientationAnglePanel;
    private javax.swing.JPanel orientationTab;
    private javax.swing.JButton replotCrossSectionButton;
    private javax.swing.JCheckBox solidCB;
    private javax.swing.JLabel solidLabel;
    private javax.swing.JLabel spacerLabel;
    private javax.swing.JLabel spineArrayLengthLabel;
    private javax.swing.JSplitPane spineScaleSplitPane;
    private javax.swing.JPanel spineScaleTab;
    private javax.swing.JTabbedPane tabbedPane;
    private javax.swing.JLabel verticalSpacer;
    // End of variables declaration//GEN-END:variables

    @Override
    public String getNameKey()
    {
        return "NAME_X3D_EXTRUSION";
    }

    /**
     * @return whether spine is closed, i.e. first and last points identical
     */
    public final boolean isSpineClosed() {
        boolean isClosed;
        int spineLength = expandableListSpine.getData().length;
        isClosed = (spineLength > 1) && 
                           (Float.parseFloat(expandableListSpine.getData()[0][0]) == Float.parseFloat(expandableListSpine.getData()[spineLength-1][0])) && 
                           (Float.parseFloat(expandableListSpine.getData()[0][1]) == Float.parseFloat(expandableListSpine.getData()[spineLength-1][1]));
        return isClosed;
    }

    /**
     * @return whether crossSection is closed, i.e. first and last points identical
     */
    public final boolean isCrossSectionClosed() {
        boolean isClosed;
        int crossSectionLength = expandableListCrossSection.getData().length;
        isClosed = (crossSectionLength > 1) && 
                           (Float.parseFloat(expandableListCrossSection.getData()[0][0]) == Float.parseFloat(expandableListCrossSection.getData()[crossSectionLength-1][0])) && 
                           (Float.parseFloat(expandableListCrossSection.getData()[0][1]) == Float.parseFloat(expandableListCrossSection.getData()[crossSectionLength-1][1]));
        return isClosed;
    }

    private void updateJFreeChartCrossSectionPlot ()
    {        
        XYSeries crossSectionSeries = new XYSeries("crossSection", false, true); // avoid title, autosort off, multiple x values allowed
        // TODO split out initialization and refresh
        crossSectionSeries.clear();
        String[][] saa = expandableListCrossSection.getData();
        ArrayList<String> tooltipList = new ArrayList<>();
        for (int i = 0; i < saa.length; i++)
        {
            if ((saa[i][0] != null) && (saa[i][1] != null)&& (saa[i][0].length() > 0) &&(saa[i][1].length() > 0) )
            {
                double x = new SFDouble(saa[i][0]).getValue();
                double y = new SFDouble(saa[i][1]).getValue();
                crossSectionSeries.add(x, y);
				String tooltip = "crossSection [";
				if (isCrossSectionClosed() && ((i == 0) || (i == (saa.length-1))))
				{
					tooltip = "closed " + tooltip;
					tooltip += "0] and [" + String.valueOf(saa.length-1);
				} // list both coincident points if closed
				else tooltip += String.valueOf(i);
				tooltip += "] = (" + singleDigitFormat.format(x) + ", " + singleDigitFormat.format(y) + ")";
                tooltipList.add(tooltip);
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

        NumberAxis domainAxis = (NumberAxis) plot.getDomainAxis();
        // change the auto tick unit selection to integer units only...
//        domainAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        domainAxis.setAutoRange(true);
        NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
//        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
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
        
        // match width and height of adjacent tables
        chartPanel.setPreferredSize(new Dimension(expandableListOrientations.getWidth(), expandableListCrossSection.getHeight()));
        
        // compute center and zoom out
        double minX = 0.0;
        double maxX = 0.0;
        double minY = 0.0;
        double maxY = 0.0;
        double plotCenterX = 0.0;
        double plotCenterY = 0.0;
        for (int i=0; i < chart.getXYPlot().getDataset().getItemCount(0); i++) // series 0
        {
            double valueX = chart.getXYPlot().getDataset().getXValue(0, i);
            double valueY = chart.getXYPlot().getDataset().getYValue(0, i);
            if ((i == 0) || (minX > valueX)) minX = valueX;
            if ((i == 0) || (maxX < valueX)) maxX = valueX;
            if ((i == 0) || (minY > valueY)) minY = valueY;
            if ((i == 0) || (maxY < valueY)) maxX = valueX;
        }
        if (chart.getXYPlot().getDataset().getItemCount(0) > 0)
        {
            plotCenterX = (maxX - minX) / chart.getXYPlot().getDataset().getSeriesCount();
            plotCenterY = (maxY - minY) / chart.getXYPlot().getDataset().getSeriesCount();
        }
        chartPanel.zoomOutBoth(plotCenterX, plotCenterY); // point to computed center

        chartPanel.addChartMouseListener(this);

        jFreeChartPanel.removeAll();
        jFreeChartPanel.add(chartPanel, BorderLayout.CENTER);
        jFreeChartPanel.validate (); // necessary for display after change
    }

    public void checkAngles(boolean precedesNormalization)
    {
        checkCreaseAngle           (precedesNormalization);
//      checkOrientationLimitAngles(precedesNormalization); // only of temporary interest, not saved
        checkOrientationArrayAngles(precedesNormalization);
    }

    private boolean largeRadianAnglesConfirmed = false;
    private boolean largeRadianAnglesFound     = false;
    
    private void checkOrientationLimitAngles(boolean precedesNormalization)
    {
        if (initialOrientationTextField.getText().trim().isEmpty())
            initialOrientationTextField.setText("0.0");
        if (  finalOrientationTextField.getText().trim().isEmpty())
              finalOrientationTextField.setText("0.0");
        // indicate degree values in tooltips
        // usability note:  can enter degree values (-6..+6) as (354..366) to provoke this conversion check
        double angle = Double.valueOf(initialOrientationTextField.getText());
        initialOrientationTextField.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");
        if ((Math.abs(angle) > 2.0 * Math.PI) && !largeRadianAnglesConfirmed)
        {
            String message;
            message = "<html><center>Large value provided for <b>initialOrientation</b> angle, which is a radians value.<br/><br/>Convert <b>" + angle + " degrees</b> to <b>" +
                    radiansFormat.format((angle) * Math.PI / 180.0) + " radians</b>"; // do not normalize % 360.0
            if (precedesNormalization)
                 message += " before normalization?";
            else message += "?";
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  message, "X3D angles are in radians", NotifyDescriptor.YES_NO_OPTION);
          if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
          {
              angle = (angle) * Math.PI / 180.0; // do not normalize % 360.0
              initialOrientationTextField.setText(radiansFormat.format(angle));
              initialOrientationTextField.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");
          }
          if ((angle < 0.0) || (angle > 2.0 * Math.PI)) // check occurs after potential conversion to radians
          {
              largeRadianAnglesFound = true;
          }
        }
        // do not comment on negative values
        
        angle = Double.valueOf(finalOrientationTextField.getText());
        finalOrientationTextField.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");
        if ((Math.abs(angle) > 2.0 * Math.PI) && !largeRadianAnglesConfirmed)
        {
            String message = "<html><center>Large value provided for <b>finalOrientation</b> angle, which is a radians value.<br/><br/>Convert <b>" + angle + " degrees</b> to <b>" +
                    radiansFormat.format((angle) * Math.PI / 180.0) + " radians</b>"; // do not normalize % 360.0
            if (precedesNormalization)
                 message += " before normalization?";
            else message += "?";
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  message, "X3D angles are in radians", NotifyDescriptor.YES_NO_OPTION);
          if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
          {
              angle = (angle) * Math.PI / 180.0; // do not normalize % 360.0
              finalOrientationTextField.setText(radiansFormat.format(angle));
              finalOrientationTextField.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");
          }
          if ((angle < 0.0) || (angle > 2.0 * Math.PI)) // check occurs after potential conversion to radians
          {
              largeRadianAnglesFound = true;
          }
        }
        // do not comment on negative values
        largeRadianAnglesConfirmed = largeRadianAnglesConfirmed || largeRadianAnglesFound;
        expandableListOrientations.setLargeRadianAnglesConfirmed (largeRadianAnglesConfirmed); // ensure correct tooltips on array values
    }

    private void checkOrientationArrayAngles(boolean precedesNormalization)
    {
        // usability note:  can enter degree values (-6..+6) as (354..366) to provoke this conversion check
        String[][] saa = expandableListOrientations.getData();

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
        expandableListOrientations.setData(saa);
    }

    private void checkCreaseAngle (boolean precedesNormalization)
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

    private void checkSpineArrayCounts(boolean initializeDefaultValues)
    {
        String[][] saaSpine        = expandableListSpine.getData();

        if (initializeDefaultValues && (saaSpine == null || saaSpine.length == 0))  // restore default if table reduced to zero
        {
            expandableListSpine.setData(extrusion.getSpineDefault());
			saaSpine        = expandableListSpine.getData();
        }
        else if ((saaSpine.length == 1))
        {
            String message = "<html><center>Error: illegal <b>spine</b> array length of 1<br/>" +
                    "<br/><b>spine</b> is a 3-tuple array whose length must be at least 2" +
                    "<br/><br/>Suggested fix:  add more <b>spine</b> points";
            // TODO figure out how to return to editor based on labeled Confirmation choice
//            NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
//                    message, "Extrusion spine array error", NotifyDescriptor.ERROR_MESSAGE);
            NotifyDescriptor descriptor = new NotifyDescriptor.Message(message);
            DialogDisplayer.getDefault().notify(descriptor);
        }
        else if ((saaSpine.length == 2) && 
				 (Float.parseFloat(saaSpine[0][0]) == Float.parseFloat(saaSpine[1][0])) && 
				 (Float.parseFloat(saaSpine[0][1]) == Float.parseFloat(saaSpine[1][1])) && 
				 (Float.parseFloat(saaSpine[0][2]) == Float.parseFloat(saaSpine[1][2])))
        {
            String message = "<html><center>Warning: coincident pair of <b>spine</b> points" +
                    "<br/><br/><b>spine</b> array has length 2 but both points are identical" +
                    "<br/><br/>Suggested fix:  edit one <b>spine</b> point or add others";
            NotifyDescriptor descriptor = new NotifyDescriptor.Message(message);
            DialogDisplayer.getDefault().notify(descriptor);
        }
        
        if ((saaSpine.length >= 2) && isSpineClosed())
        {
            expandableListSpine.setTitle("spine array is closed (coincident endpoints)");
        }
        else if (saaSpine.length >= 2)
        {
            expandableListSpine.setTitle("spine array is open (distinct endpoints)");
        }
        else
        {
            expandableListSpine.setTitle("spine array");
        }
    }

    private void checkCrossSectionArrayCounts (boolean initializeDefaultValues)
    {
        String[][] saaCrossSection = expandableListCrossSection.getData();
        
        if (initializeDefaultValues && (saaCrossSection.length == 0))  // restore default if table reduced to zero
        {
            expandableListCrossSection.setData(extrusion.getCrossSectionDefault());
        }
        else if (saaCrossSection.length == 1) // zero length (default value) is OK
        {
            String message = "<html><center>Error: illegal <b>crossSection</b> array length of " + saaCrossSection.length + 
                    "<br/><br/><b>crossSection</b> is a 2-tuple array whose length must be at least 2" +
                    "<br/><br/>Suggested fix:  add another <b>crossSection</b> point";
            NotifyDescriptor descriptor = new NotifyDescriptor.Message(message);
            DialogDisplayer.getDefault().notify(descriptor);
        }
    }

    private void checkScaleArrayCounts (boolean initializeDefaultValues)
    {
        String[][] saaSpine        = expandableListSpine.getData();
        String[][] saaScale        = expandableListScale.getData();
        boolean alreadyWarned = false;
        
        if (initializeDefaultValues && (saaScale == null || saaScale.length == 0))  // restore default if table reduced to zero
        {
            expandableListScale.setData(extrusion.getScaleDefault());
            saaScale = expandableListScale.getData();
        }
        
        if (initializeDefaultValues && (saaSpine == null || saaSpine.length == 0))  // restore default if table reduced to zero
        {
            expandableListSpine.setData(extrusion.getSpineDefault());
            saaSpine = expandableListSpine.getData();
        }
        else if (!alreadyWarned && (saaScale.length != 0) && (saaScale.length != 1) && (saaScale.length != saaSpine.length))
        {
            String message = "<html><center>Error: illegal <b>scale</b> array length of " + saaScale.length + 
                    "<br/><br/><b>scale</b> is a 2-tuple array whose length can be, 1," +
                    "<br/><br/>or else must match 3-tuple <b>spine</b> array length " +  saaSpine.length;
            NotifyDescriptor descriptor = new NotifyDescriptor.Message(message);
            DialogDisplayer.getDefault().notify(descriptor);
            alreadyWarned = true;
        }
        // check scale values positive
        boolean nonPositiveScaleFound = false;
        for (int i=0; i < saaScale.length; i++) 
        {
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

    private void checkOrientationArrayCounts(boolean initializeDefaultValues)
    {
        String[][] saaSpine        = expandableListSpine.getData();
        String[][] saaOrientation  = expandableListOrientations.getData();
        
        boolean alreadyWarned = false;
        
        if (initializeDefaultValues && (saaOrientation.length == 0))  // restore default if table reduced to zero
        {
            SFFloat [][] orientationDefault = extrusion.getOrientationDefault();
            Object [][]oaa = new Object[1][4];
            // cast to integers to avoid decimal points and simplify display consistently
            oaa[0][0] = Integer.toString((int)orientationDefault[0][0].getValue());
            oaa[0][1] = Integer.toString((int)orientationDefault[0][1].getValue());
            oaa[0][2] = Integer.toString((int)orientationDefault[0][2].getValue());
            oaa[0][3] = Integer.toString((int)orientationDefault[0][3].getValue());
            expandableListOrientations.setData(oaa);
            System.out.println ("Extrusion orientation array reset from empty to default: " + 
                    oaa[0][0] + " " + oaa[0][1] + " " + oaa[0][2] + " " + oaa[0][3]);
        }
        else if (!alreadyWarned && (saaOrientation.length != 0) && (saaOrientation.length != 1) && (saaOrientation.length != saaSpine.length))
        {
            String message = "<html><center>Error: illegal <b>orientation</b> array length of " + saaOrientation.length + 
                    "<br/><br/><b>orientation</b> is a 4-tuple array whose length can be 0, 1," +
                    "<br/><br/>or else must match 3-tuple <b>spine</b> array length " +  saaSpine.length;
            NotifyDescriptor descriptor = new NotifyDescriptor.Message(message);
            DialogDisplayer.getDefault().notify(descriptor);
            alreadyWarned = true;
        }
    }

    @Override
    public void unloadInput() throws IllegalArgumentException
    {
        checkCreaseAngle           (true);
        checkOrientationArrayAngles(true);

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
        extrusion.setOrientation(expandableListOrientations.getData());

        extrusion.setInsertCommas    (    insertCommasCheckBox.isSelected());
        extrusion.setInsertLineBreaks(insertLineBreaksCheckBox.isSelected());
		
		insertOpenClosedComment = openClosedCommentCheckBox.isSelected(); // remember preference for this session
		if (insertOpenClosedComment)
		{
			String openClosedComment = new String();
			if (isSpineClosed())
			     openClosedComment += "\n      <!-- spine is closed,";
			else openClosedComment += "\n      <!-- spine is open,";
			if (isCrossSectionClosed())
			     openClosedComment += " crossSection is closed -->";
			else openClosedComment += " crossSection is open -->";

		    if  (extrusion.getContent().contains("<Metadata"))  // only allowed child
				 extrusion.setContent(openClosedComment + // prepend
						 extrusion.getContent().substring(extrusion.getContent().indexOf("<Metadata")));
			else extrusion.setContent(openClosedComment);
		}
		else if (extrusion.getContent().trim().startsWith("<!-- spine is "))
			     extrusion.clearContent();
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
                 insertCommasLineBreaksLabel.setVisible(showEditEnhancements);
    }

    // see JFreeChart documentation 22.16.8 Chart Change Listeners
    @Override
    public void chartMouseClicked(ChartMouseEvent event) {
        XYItemEntity entity;
        try
        {
            entity= (XYItemEntity) event.getEntity();
            int row = entity.getItem();
            expandableListCrossSection.setHighlightedRow(row);
        }
        catch (Exception e)
        {
            // ignore
        }
//        System.out.println ("mouse entity: " + event.getEntity() + ", source: " + event.getSource() + ", trigger point: " + event.getTrigger());
//        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void chartMouseMoved(ChartMouseEvent event) {
//        ChartEntity entity = event.getEntity();
//        System.out.println ("mouse entity: " + event.getEntity() + ", source: " + event.getSource() + ", trigger point: " + event.getTrigger());
//        throw new UnsupportedOperationException("Not supported yet.");
    }
}
