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
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.text.JTextComponent;
import org.openide.util.HelpCtx;
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
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * CONTOURPOLYLINE2DCustomizer.java
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class CONTOURPOLYLINE2DCustomizer extends BaseCustomizer
{
    private CONTOURPOLYLINE2D contourPolyline2D;
    private JTextComponent target;
    private ChartPanel chartPanel;
    private JFreeChart chart;

  public CONTOURPOLYLINE2DCustomizer(CONTOURPOLYLINE2D contourPolyline2D, JTextComponent target)
  {
    super(contourPolyline2D);
    this.contourPolyline2D = contourPolyline2D;
    this.target = target;
                           
    HelpCtx.setHelpIDString(this, "CONTOURPOLYLINE2D_ELEM_HELPID");   
    
    initComponents();
    
    super.getDEFUSEpanel().setContainerFieldChoices(CONTOURPOLYLINE2D_CONTAINERFIELD_CHOICES, CONTOURPOLYLINE2D_CONTAINERFIELD_TOOLTIPS);
    // DEFUSEpanel initialization must NOT be repeated or else array of choices will be overwritten
    
    //pointTA.setText(textureCoordinate.getPoint());
    expandableListControlPoint.setTitle("controlPoint array");
    expandableListControlPoint.setColumnTitles  (new String[]{"<html>&nbsp;","u","v"});
    expandableListControlPoint.setColumnToolTips(new String[]{"index","u horizontal coordinate","v vertical coordinate"});
    expandableListControlPoint.setHeaderTooltip("ContourPolyline2D controlPoint (u,v) values in range [0..1]");
    expandableListControlPoint.setNewRowData(new Object[]{"0","0"});
    expandableListControlPoint.doIndexInFirstColumn(true);
    expandableListControlPoint.setRedColumn(-1); // 0 is index, -1 means no color editing
    
    String[][] saa = contourPolyline2D.getPoint();
    expandableListControlPoint.setData(saa);
    
    expandableListControlPoint.setGeneratePointsDescriptions(COORDINATE2D_ATTR_POINT_DESCRIPTIONS);
    expandableListControlPoint.setGeneratePointsChoices(COORDINATE2D_ATTR_POINT_CHOICES); // provide choice labels for appending
    expandableListControlPoint.setGeneratePointsEnumerationValues(COORDINATE2D_ATTR_POINT_VALUES);

    updateJFreeChartPointPlot ();
  }
  
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        GridBagConstraints gridBagConstraints;
        DEFUSEpanel dEFUSEpanel1 = getDEFUSEpanel();
        expandableListControlPoint = new ExpandableList();
        jFreeChartPanel = new JPanel();
        replotButton = new JButton();
        nodeHintPanel = new JPanel();
        descriptionLabel = new JLabel();

        setLayout(new GridBagLayout());
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        add(dEFUSEpanel1, gridBagConstraints);

        expandableListControlPoint.setMinimumSize(new Dimension(463, 200));
        expandableListControlPoint.setPreferredSize(new Dimension(473, 200));
        expandableListControlPoint.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                expandableListPropertyChanged(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(3, 3, 3, 0);
        add(expandableListControlPoint, gridBagConstraints);

        jFreeChartPanel.setBorder(BorderFactory.createLineBorder(UIManager.getDefaults().getColor("Button.disabledText")));
        jFreeChartPanel.setMinimumSize(new Dimension(300, 300));
        jFreeChartPanel.setPreferredSize(new Dimension(300, 300));
        jFreeChartPanel.setLayout(new BorderLayout());
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.34;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new Insets(6, 6, 6, 6);
        add(jFreeChartPanel, gridBagConstraints);

        replotButton.setText("Replot");
        replotButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                replotButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.SOUTHEAST;
        gridBagConstraints.insets = new Insets(0, 3, 7, 0);
        add(replotButton, gridBagConstraints);

        nodeHintPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        nodeHintPanel.setLayout(new GridBagLayout());

        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        descriptionLabel.setText("<html><p align='center'><b>ContourPolyline2D</b> defines a piecewise linear curve segment as part of a trimming contour in (u,v) domain of a surface</p>");
        descriptionLabel.setToolTipText("ContourPolyline2D nodes are contained by Contour2D");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(10, 3, 10, 3);
        nodeHintPanel.add(descriptionLabel, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        add(nodeHintPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void expandableListPropertyChanged(PropertyChangeEvent evt)//GEN-FIRST:event_expandableListPropertyChanged
    {//GEN-HEADEREND:event_expandableListPropertyChanged
        updateJFreeChartPointPlot ();
    }//GEN-LAST:event_expandableListPropertyChanged

    private void replotButtonActionPerformed(ActionEvent evt)//GEN-FIRST:event_replotButtonActionPerformed
    {//GEN-HEADEREND:event_replotButtonActionPerformed
        updateJFreeChartPointPlot ();
    }//GEN-LAST:event_replotButtonActionPerformed
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JLabel descriptionLabel;
    private ExpandableList expandableListControlPoint;
    private JPanel jFreeChartPanel;
    private JPanel nodeHintPanel;
    private JButton replotButton;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_CONTOURPOLYLINE2D";
  }

    private void updateJFreeChartPointPlot ()
    {
        XYSeries pointSeries = new XYSeries("controlPoint", false, true); // avoid title, autosort off, multiple x values allowed
        // TODO split out initialization and refresh
        pointSeries.clear();
        String[][] saa = expandableListControlPoint.getData(); // textureCoordinate.getPoint();
        ArrayList<String> tooltipList = new ArrayList<String>();
        for (int i = 0; i < saa.length; i++)
        {
            if ((saa[i][0] != null) && (saa[i][1] != null) && (saa[i][0].length() > 0) && (saa[i][1].length() > 0) )
            {
                double x = new SFDouble(saa[i][0]).getValue();
                double y = new SFDouble(saa[i][1]).getValue();
                pointSeries.add(x, y);
                tooltipList.add("ContourPolyline2D controlPoint [" + String.valueOf(i) + "] = (" + singleDigitFormat.format(x) + ", " + singleDigitFormat.format(y) + ")");
            }
        }
        XYSeriesCollection datasetCollection = new XYSeriesCollection();
        datasetCollection.addSeries(pointSeries);

        // create a chart...
        chart = ChartFactory.createXYLineChart(
            "ContourPolyline2D controlPoint plot",
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
        TextTitle title = new TextTitle("ContourPolyline2D controlPoint array");
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
    
    contourPolyline2D.setPoint(expandableListControlPoint.getData());
  }  
}
