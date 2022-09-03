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
import java.awt.Font;
import java.util.ArrayList;
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
import org.openide.util.HelpCtx;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * NURBSCURVE2DCustomizer.java
 * Created on 18 December 2011
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class NURBSCURVE2DCustomizer extends BaseCustomizer
{ 
    private NURBSCURVE2D nurbsCurve2D;
    private JTextComponent target;
    private ChartPanel chartPanel;
    private JFreeChart chart;

  /** Creates new form NURBSCURVECustomizer
   * @param nurbsCurve2D NURBSCURVE node holding data structures for scene graph
   * @param target Swing component
   */
  public NURBSCURVE2DCustomizer(NURBSCURVE2D nurbsCurve2D, JTextComponent target)
  {
    super(nurbsCurve2D);
    this.nurbsCurve2D=nurbsCurve2D;
    this.target = target;
                           
    HelpCtx.setHelpIDString(NURBSCURVE2DCustomizer.this, "NURBSCURVE_ELEM_HELPID");
    
    initComponents();
    
    super.getDEFUSEpanel().setContainerFieldChoices(CONTOURPOLYLINE2D_CONTAINERFIELD_CHOICES, CONTOURPOLYLINE2D_CONTAINERFIELD_TOOLTIPS);
    // DEFUSEpanel initialization must NOT be repeated or else array of choices will be overwritten

    closedCheckBox.getModel().setSelected(nurbsCurve2D.getClosed());
    
    orderComboBox.setSelectedItem(String.valueOf(nurbsCurve2D.getOrder())); // also invokes updateOrderWarningLabel()

    tessellationTF.setText(nurbsCurve2D.getTessellation().trim());
    
    knotTextArea.setText(nurbsCurve2D.getKnot());

    expandableListWeightsAndControlPoints.setTitle("weight, controlPoint arrays");
    expandableListWeightsAndControlPoints.setColumnTitles  (new String[]{"#","weight","controlPoint.x","controlPoint.y"});
    expandableListWeightsAndControlPoints.setColumnToolTips(new String[]{"index","weight value","controlPoint.x","controlPoint.y"});
    expandableListWeightsAndControlPoints.setHeaderTooltip ("weight, controlPoint values define a parametric curve");
    expandableListWeightsAndControlPoints.setNewRowData(new Object[]{"1","0","0"});  //  weight=0, values 0
    expandableListWeightsAndControlPoints.doIndexInFirstColumn(true);
    expandableListWeightsAndControlPoints.setBoldColumn(1);

    expandableListWeightsAndControlPoints.setShowAppendCommasLineBreaks(true);
    expandableListWeightsAndControlPoints.setKeyColumnIncluded(false);
    expandableListWeightsAndControlPoints.setInsertCommas    (nurbsCurve2D.isInsertCommas());
    expandableListWeightsAndControlPoints.setInsertLineBreaks(nurbsCurve2D.isInsertLineBreaks());
    expandableListWeightsAndControlPoints.setColumnWidthAndResizeStrategy(false, 75);
    
    expandableListWeightsAndControlPoints.setGeneratePointsDescriptions(COORDINATE2D_ATTR_POINT_DESCRIPTIONS);
    expandableListWeightsAndControlPoints.setGeneratePointsChoices(COORDINATE2D_ATTR_POINT_CHOICES); // provide choice labels for appending
    expandableListWeightsAndControlPoints.setGeneratePointsEnumerationValues(COORDINATE2D_ATTR_POINT_VALUES);
    
    String[][] saa = nurbsCurve2D.getWeightsAndControlPoints(); // may be 0-length
    expandableListWeightsAndControlPoints.setData(saa);

    updateJFreeChartPointPlot ();
  }

    private void updateOrderWarningLabel()
    {
        Color green = new Color (0, 128, 0);
        
        int order; // = 3;
        
        if ((orderComboBox.getSelectedIndex() >= 0) && (orderComboBox.getSelectedIndex() < orderComboBox.getMaximumRowCount()))
        {
            order = orderComboBox.getSelectedIndex() + 2;
        }
        else try 
        {
            order = new SFInt32(orderComboBox.getSelectedItem().toString(), 0, null).getValue();
        }
        catch (Exception e)
        {
            order =  -1;
        }
        
        if     (order < 2)
        {
            orderWarningLabel.setText(NURBSCURVECustomizer.ORDER_TOO_SMALL_WARNING);
            orderWarningLabel.setForeground(Color.RED);                 
        }
        else if (order > 6)
        {
            orderWarningLabel.setText(NURBSCURVECustomizer.ORDER_TOO_LARGE_WARNING);
            orderWarningLabel.setForeground(Color.RED);
            // do not modify value since it remains legal
        }
        else switch (order)
        {
            case 2:
                orderWarningLabel.setText("second order is a linear curve");
                orderWarningLabel.setForeground(green);
                break;
            case 3:
                orderWarningLabel.setText("third order is a quadratic curve (most common)");
                orderWarningLabel.setForeground(green);
                break;
            case 4:
                orderWarningLabel.setText("fourth order is a cubic curve");
                orderWarningLabel.setForeground(green);
                break;
            case 5:
                orderWarningLabel.setText("fifth order supports continuous higher-order derivatives");
                orderWarningLabel.setForeground(green);
                break;
            case 6:
                orderWarningLabel.setText("sixth order supports continuous higher-order derivatives");
                orderWarningLabel.setForeground(green);
                break;
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
        orderLabel = new javax.swing.JLabel();
        orderLayoutPanel = new javax.swing.JPanel();
        orderComboBox = new javax.swing.JComboBox<String>();
        orderWarningLabel = new javax.swing.JLabel();
        closedLabel = new javax.swing.JLabel();
        closedCheckBox = new javax.swing.JCheckBox();
        tessellationLabel = new javax.swing.JLabel();
        tessellationTF = new javax.swing.JTextField();
        expandableListWeightsAndControlPoints = new org.web3d.x3d.palette.items.ExpandableList();
        knotLabel = new javax.swing.JLabel();
        knotScrollPane = new javax.swing.JScrollPane();
        knotTextArea = new javax.swing.JTextArea();
        replotButton = new javax.swing.JButton();
        jFreeChartPanel = new javax.swing.JPanel();
        nodeHintPanel = new javax.swing.JPanel();
        hintLabel = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel1, gridBagConstraints);

        orderLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        orderLabel.setText("order");
        orderLabel.setToolTipText("polynomial degree for smoothing calculations");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 23, 3, 3);
        add(orderLabel, gridBagConstraints);

        orderLayoutPanel.setLayout(new java.awt.GridBagLayout());

        orderComboBox.setEditable(true);
        orderComboBox.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "2", "3", "4", "5", "6" }));
        orderComboBox.setToolTipText("polynomial degree for smoothing calculations");
        orderComboBox.setMinimumSize(new java.awt.Dimension(35, 20));
        orderComboBox.setPreferredSize(new java.awt.Dimension(35, 20));
        orderComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orderComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        orderLayoutPanel.add(orderComboBox, gridBagConstraints);

        orderWarningLabel.setText(NURBSCURVECustomizer.ORDER_TOO_SMALL_WARNING);
        orderWarningLabel.setToolTipText("polynomial degree for smoothing calculations");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 12, 3, 8);
        orderLayoutPanel.add(orderWarningLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(orderLayoutPanel, gridBagConstraints);

        closedLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        closedLabel.setText("closed");
        closedLabel.setToolTipText("position offset from origin of local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 23, 3, 3);
        add(closedLabel, gridBagConstraints);

        closedCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closedCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(closedCheckBox, gridBagConstraints);

        tessellationLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        tessellationLabel.setText("tessellation");
        tessellationLabel.setToolTipText("automatically calculated, can be specified as an optimization or constraint");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 23, 3, 3);
        add(tessellationLabel, gridBagConstraints);

        tessellationTF.setColumns(5);
        tessellationTF.setToolTipText("automatically calculated, can be specified as an optimization or constraint");
        tessellationTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tessellationTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.3333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(tessellationTF, gridBagConstraints);

        expandableListWeightsAndControlPoints.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                expandableListWeightsAndControlPointsPropertyChange(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(expandableListWeightsAndControlPoints, gridBagConstraints);

        knotLabel.setText("knot");
        knotLabel.setToolTipText("each span of knot values has length=order to adjust the parametric curve");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 23, 3, 3);
        add(knotLabel, gridBagConstraints);

        knotTextArea.setColumns(20);
        knotTextArea.setRows(5);
        knotTextArea.setToolTipText("each span of knot values has length=order to adjust the parametric curve");
        knotTextArea.setBorder(null);
        knotScrollPane.setViewportView(knotTextArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 23);
        add(knotScrollPane, gridBagConstraints);

        replotButton.setText("Replot");
        replotButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                replotButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 7, 0);
        add(replotButton, gridBagConstraints);

        jFreeChartPanel.setBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.disabledText")));
        jFreeChartPanel.setMinimumSize(new java.awt.Dimension(300, 300));
        jFreeChartPanel.setPreferredSize(new java.awt.Dimension(300, 300));
        jFreeChartPanel.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.34;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        add(jFreeChartPanel, gridBagConstraints);

        nodeHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nodeHintPanel.setLayout(new java.awt.GridBagLayout());

        hintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel.setText("<html><p align='center'><b>NurbsCurve2D</b> is a geometry node defining a parametric curve in 2D space.</p>");
        hintLabel.setToolTipText("All controlPoint values are saved as part of NurbsCurve2D");
        hintLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        nodeHintPanel.add(hintLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nodeHintPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void orderComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_orderComboBoxActionPerformed
    {//GEN-HEADEREND:event_orderComboBoxActionPerformed
       updateOrderWarningLabel();
    }//GEN-LAST:event_orderComboBoxActionPerformed

    private void tessellationTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tessellationTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_tessellationTFActionPerformed

    private void closedCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closedCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_closedCheckBoxActionPerformed

    private void replotButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_replotButtonActionPerformed

        updateJFreeChartPointPlot();     }//GEN-LAST:event_replotButtonActionPerformed

    private void expandableListWeightsAndControlPointsPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_expandableListWeightsAndControlPointsPropertyChange
        updateJFreeChartPointPlot ();
    }//GEN-LAST:event_expandableListWeightsAndControlPointsPropertyChange
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox closedCheckBox;
    private javax.swing.JLabel closedLabel;
    private org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel1;
    private org.web3d.x3d.palette.items.ExpandableList expandableListWeightsAndControlPoints;
    private javax.swing.JLabel hintLabel;
    private javax.swing.JPanel jFreeChartPanel;
    private javax.swing.JLabel knotLabel;
    private javax.swing.JScrollPane knotScrollPane;
    private javax.swing.JTextArea knotTextArea;
    private javax.swing.JPanel nodeHintPanel;
    private javax.swing.JComboBox<String> orderComboBox;
    private javax.swing.JLabel orderLabel;
    private javax.swing.JPanel orderLayoutPanel;
    private javax.swing.JLabel orderWarningLabel;
    private javax.swing.JButton replotButton;
    private javax.swing.JLabel tessellationLabel;
    private javax.swing.JTextField tessellationTF;
    // End of variables declaration//GEN-END:variables
  
  
  @Override
  public String getNameKey()
  {
    return "NAME_X3D_NURBSCURVE2D";
  }

    private void updateJFreeChartPointPlot ()
    {
        XYSeries pointSeries = new XYSeries("controlPoint", false, true); // avoid title, autosort off, multiple x values allowed
        // TODO split out initialization and refresh
        pointSeries.clear();
        String[][] saa = expandableListWeightsAndControlPoints.getData(); // textureCoordinate.getPoint();
        ArrayList<String> tooltipList = new ArrayList<String>();
        for (int i = 0; i < saa.length; i++)
        {
            if ((saa[i][1] != null) && (saa[i][2] != null) && (saa[i][1].length() > 0) && (saa[i][2].length() > 0) )
            {
                double x = new SFDouble(saa[i][1]).getValue();
                double y = new SFDouble(saa[i][2]).getValue();
                pointSeries.add(x, y);
                tooltipList.add("NurbsCurve2D controlPoint [" + String.valueOf(i) + "] = (" + singleDigitFormat.format(x) + ", " + singleDigitFormat.format(y) + ")");
            }
        }
        XYSeriesCollection datasetCollection = new XYSeriesCollection();
        datasetCollection.addSeries(pointSeries);

        // create a chart...
        chart = ChartFactory.createXYLineChart(
            "NurbsCurve2D controlPoint plot",
            "u", // x axis label
            "v", // y axis label
            datasetCollection, // data
            PlotOrientation.VERTICAL,
            false, // include legend
            true, // tooltips
            false // urls
        );
        // chart customization
        chart.setBackgroundPaint(Color.white);
        TextTitle title = new TextTitle("NurbsCurve2D controlPoint array");
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
//        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
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
        chartPanel.setZoomOutFactor(1.5); // TODO confirm OK
        
        double centerX = (pointSeries.getMinX() + pointSeries.getMaxX()) / 2.0;
        double centerY = (pointSeries.getMinY() + pointSeries.getMaxY()) / 2.0;
        chartPanel.zoomOutBoth(centerX, centerY);

        jFreeChartPanel.removeAll();
        jFreeChartPanel.add(chartPanel, BorderLayout.CENTER);
        jFreeChartPanel.validate (); // necessary for display after change
    }

  @Override
  public void unloadInput() throws IllegalArgumentException
  {    
    unLoadDEFUSE();
    
    nurbsCurve2D.setClosed(closedCheckBox.getModel().isSelected());
    
    nurbsCurve2D.setKnot(knotTextArea.getText().replaceAll("\\s+", " ").trim()); // normalize whitespace
    
    if (   (orderComboBox.getSelectedIndex() >= 0)
        || (orderComboBox.getSelectedItem().toString().length() > 0))// avoid reseting to values < 2; TODO confirm
        nurbsCurve2D.setOrder(String.valueOf(orderComboBox.getSelectedItem()));
    
    nurbsCurve2D.setWeightsAndControlPoints (expandableListWeightsAndControlPoints.getData());
    nurbsCurve2D.setInsertCommas            (expandableListWeightsAndControlPoints.isInsertCommasSet());
    nurbsCurve2D.setInsertLineBreaks        (expandableListWeightsAndControlPoints.isInsertLineBreaksSet());
    
    nurbsCurve2D.setTessellation(tessellationTF.getText().trim());
  } 

}
