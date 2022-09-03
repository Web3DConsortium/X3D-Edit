/*
Copyright* (c) 1995-2021 held by the author(s) .  All rights reserved.
 
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
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * NURBSPATCHSURFACECustomizer.java
 * Created on 18 December 2011
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class NURBSPATCHSURFACECustomizer extends BaseCustomizer
{ 
    private NURBSPATCHSURFACE nurbsPatchSurface;
    private JTextComponent target;
    private ChartPanel chartPanel;
    private JFreeChart chart;

  /** Creates new form NURBSPATCHSURFACECustomizer
   * @param nurbsPatchSurface NURBSPATCHSURFACE node holding data structures for scene graph
   * @param target Swing component
   */
  public NURBSPATCHSURFACECustomizer(NURBSPATCHSURFACE nurbsPatchSurface, JTextComponent target)
  {
    super(nurbsPatchSurface);
    this.nurbsPatchSurface=nurbsPatchSurface;
    this.target = target;
                           
    HelpCtx.setHelpIDString(NURBSPATCHSURFACECustomizer.this, "NURBSPATCHSURFACE_ELEM_HELPID");
    
    initComponents();
    
// TODO   super.getDEFUSEpanel().setContainerFieldChoices(CONTOURPOLYLINE2D_CONTAINERFIELD_CHOICES, CONTOURPOLYLINE2D_CONTAINERFIELD_TOOLTIPS);
    
    solidCB.setSelected(nurbsPatchSurface.isSolid());
    
    uClosedCheckBox.getModel().setSelected(nurbsPatchSurface.get_uClosed());
    vClosedCheckBox.getModel().setSelected(nurbsPatchSurface.get_vClosed());
    
    uOrderComboBox.setSelectedItem(String.valueOf(nurbsPatchSurface.get_uOrder())); // also invokes updateOrderWarningLabel()
    vOrderComboBox.setSelectedItem(String.valueOf(nurbsPatchSurface.get_vOrder())); // also invokes updateOrderWarningLabel()

    uDimensionTF.setText(nurbsPatchSurface.get_uDimension().trim());
    vDimensionTF.setText(nurbsPatchSurface.get_vDimension().trim());
    
    uKnotTextArea.setText(nurbsPatchSurface.get_uKnot());
    vKnotTextArea.setText(nurbsPatchSurface.get_vKnot());
    
    uTessellationTF.setText(nurbsPatchSurface.get_uTessellation());
    vTessellationTF.setText(nurbsPatchSurface.get_vTessellation());

    expandableListWeightsAndControlPoints.setTitle("weight, controlPoint arrays");
    expandableListWeightsAndControlPoints.setColumnTitles  (new String[]{"#","weight","controlPoint.x","controlPoint.y"});
    expandableListWeightsAndControlPoints.setColumnToolTips(new String[]{"index","weight value","controlPoint.x","controlPoint.y"});
    expandableListWeightsAndControlPoints.setHeaderTooltip ("weight, controlPoint values define a parametric curve");
    expandableListWeightsAndControlPoints.setNewRowData(new Object[]{"1","0","0"});  //  weight=0, values 0
    expandableListWeightsAndControlPoints.doIndexInFirstColumn(true);
    expandableListWeightsAndControlPoints.setBoldColumn(1);

    expandableListWeightsAndControlPoints.setShowAppendCommasLineBreaks(true);
    expandableListWeightsAndControlPoints.setKeyColumnIncluded(false);
    expandableListWeightsAndControlPoints.setInsertCommas    (nurbsPatchSurface.isInsertCommas());
    expandableListWeightsAndControlPoints.setInsertLineBreaks(nurbsPatchSurface.isInsertLineBreaks());
    expandableListWeightsAndControlPoints.setColumnWidthAndResizeStrategy(false, 75);
    
    String[][] saa = nurbsPatchSurface.getWeightsAndControlPoints(); // may be 0-length
    expandableListWeightsAndControlPoints.setData(saa);

    updateJFreeChartPointPlot ();
  }

    private void update_uOrderWarningLabel()
    {
        Color green = new Color (0, 128, 0);
        
        int order; // default = 3;
        
        if ((uOrderComboBox.getSelectedIndex() >= 0) && (uOrderComboBox.getSelectedIndex() < uOrderComboBox.getMaximumRowCount()))
        {
            order = uOrderComboBox.getSelectedIndex() + 2;
        }
        else try 
        {
            order = new SFInt32(uOrderComboBox.getSelectedItem().toString(), 0, null).getValue();
        }
        catch (Exception e)
        {
            order =  -1;
        }
        
        if     (order < 2)
        {
            uOrderWarningLabel.setText(NURBSCURVECustomizer.ORDER_TOO_SMALL_WARNING);
            uOrderWarningLabel.setForeground(Color.RED);                 
        }
        else if (order > 6)
        {
            uOrderWarningLabel.setText(NURBSCURVECustomizer.ORDER_TOO_LARGE_WARNING);
            uOrderWarningLabel.setForeground(Color.RED);
            // do not modify value since it remains legal
        }
        else switch (order)
        {
            case 2:
                uOrderWarningLabel.setText("second order is a linear curve");
                uOrderWarningLabel.setForeground(green);
                break;
            case 3:
                uOrderWarningLabel.setText("third order is a quadratic curve (most common)");
                uOrderWarningLabel.setForeground(green);
                break;
            case 4:
                uOrderWarningLabel.setText("fourth order is a cubic curve");
                uOrderWarningLabel.setForeground(green);
                break;
            case 5:
                uOrderWarningLabel.setText("fifth order supports continuous higher-order derivatives");
                uOrderWarningLabel.setForeground(green);
                break;
            case 6:
                uOrderWarningLabel.setText("sixth order supports continuous higher-order derivatives");
                uOrderWarningLabel.setForeground(green);
                break;
        }
    }
    private void update_vOrderWarningLabel()
    {
        Color green = new Color (0, 128, 0);
        
        int order; // default = 3;
        
        if ((vOrderComboBox.getSelectedIndex() >= 0) && (vOrderComboBox.getSelectedIndex() < vOrderComboBox.getMaximumRowCount()))
        {
            order = vOrderComboBox.getSelectedIndex() + 2;
        }
        else try 
        {
            order = new SFInt32(vOrderComboBox.getSelectedItem().toString(), 0, null).getValue();
        }
        catch (Exception e)
        {
            order =  -1;
        }
        
        if     (order < 2)
        {
            vOrderWarningLabel.setText(NURBSCURVECustomizer.ORDER_TOO_SMALL_WARNING);
            vOrderWarningLabel.setForeground(Color.RED);                 
        }
        else if (order > 6)
        {
            vOrderWarningLabel.setText(NURBSCURVECustomizer.ORDER_TOO_LARGE_WARNING);
            vOrderWarningLabel.setForeground(Color.RED);
            // do not modify value since it remains legal
        }
        else switch (order)
        {
            case 2:
                vOrderWarningLabel.setText("second order is a linear curve");
                vOrderWarningLabel.setForeground(green);
                break;
            case 3:
                vOrderWarningLabel.setText("third order is a quadratic curve (most common)");
                vOrderWarningLabel.setForeground(green);
                break;
            case 4:
                vOrderWarningLabel.setText("fourth order is a cubic curve");
                vOrderWarningLabel.setForeground(green);
                break;
            case 5:
                vOrderWarningLabel.setText("fifth order supports continuous higher-order derivatives");
                vOrderWarningLabel.setForeground(green);
                break;
            case 6:
                vOrderWarningLabel.setText("sixth order supports continuous higher-order derivatives");
                vOrderWarningLabel.setForeground(green);
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
        solidCB = new javax.swing.JCheckBox();
        uClosedLabel = new javax.swing.JLabel();
        uClosedCheckBox = new javax.swing.JCheckBox();
        vClosedlabel = new javax.swing.JLabel();
        vClosedCheckBox = new javax.swing.JCheckBox();
        uTessellationLabel = new javax.swing.JLabel();
        uTessellationTF = new javax.swing.JTextField();
        vTessellationLabel = new javax.swing.JLabel();
        vTessellationTF = new javax.swing.JTextField();
        uOrderLabel = new javax.swing.JLabel();
        uOrderLayoutPanel = new javax.swing.JPanel();
        uOrderComboBox = new javax.swing.JComboBox<String>();
        uOrderWarningLabel = new javax.swing.JLabel();
        uDimensionLabel = new javax.swing.JLabel();
        uDimensionTF = new javax.swing.JTextField();
        expandableListWeightsAndControlPoints = new org.web3d.x3d.palette.items.ExpandableList();
        uKnotLabel = new javax.swing.JLabel();
        uKnotScrollPane = new javax.swing.JScrollPane();
        uKnotTextArea = new javax.swing.JTextArea();
        replotButton = new javax.swing.JButton();
        jFreeChartPanel = new javax.swing.JPanel();
        nodeHintPanel = new javax.swing.JPanel();
        hintLabel = new javax.swing.JLabel();
        vOrderLabel = new javax.swing.JLabel();
        vOrderLayoutPanel = new javax.swing.JPanel();
        vOrderComboBox = new javax.swing.JComboBox<String>();
        vOrderWarningLabel = new javax.swing.JLabel();
        vDimensionLabel = new javax.swing.JLabel();
        vDimensionTF = new javax.swing.JTextField();
        vKnotLabel = new javax.swing.JLabel();
        vKnotScrollPane = new javax.swing.JScrollPane();
        vKnotTextArea = new javax.swing.JTextArea();

        setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel1, gridBagConstraints);

        solidCB.setText("solid");
        solidCB.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(solidCB, gridBagConstraints);

        uClosedLabel.setText("uClosed");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(uClosedLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(uClosedCheckBox, gridBagConstraints);

        vClosedlabel.setText("vClosed");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        add(vClosedlabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(vClosedCheckBox, gridBagConstraints);

        uTessellationLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        uTessellationLabel.setText("uTessellation");
        uTessellationLabel.setToolTipText("automatically calculated, can be specified as an optimization or constraint");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 23, 3, 3);
        add(uTessellationLabel, gridBagConstraints);

        uTessellationTF.setColumns(5);
        uTessellationTF.setToolTipText("automatically calculated, can be specified as an optimization or constraint");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.3333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(uTessellationTF, gridBagConstraints);

        vTessellationLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        vTessellationLabel.setText("vTessellation");
        vTessellationLabel.setToolTipText("automatically calculated, can be specified as an optimization or constraint");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        add(vTessellationLabel, gridBagConstraints);

        vTessellationTF.setColumns(5);
        vTessellationTF.setToolTipText("automatically calculated, can be specified as an optimization or constraint");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.3333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(vTessellationTF, gridBagConstraints);

        uOrderLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        uOrderLabel.setText("uOrder");
        uOrderLabel.setToolTipText("polynomial degree for smoothing calculations");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 23, 3, 3);
        add(uOrderLabel, gridBagConstraints);

        uOrderLayoutPanel.setLayout(new java.awt.GridBagLayout());

        uOrderComboBox.setEditable(true);
        uOrderComboBox.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "2", "3", "4", "5", "6" }));
        uOrderComboBox.setToolTipText("polynomial degree for smoothing calculations");
        uOrderComboBox.setMinimumSize(new java.awt.Dimension(35, 20));
        uOrderComboBox.setPreferredSize(new java.awt.Dimension(35, 20));
        uOrderComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                uOrderComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        uOrderLayoutPanel.add(uOrderComboBox, gridBagConstraints);

        uOrderWarningLabel.setText(NURBSCURVECustomizer.ORDER_TOO_SMALL_WARNING);
        uOrderWarningLabel.setToolTipText("polynomial degree for smoothing calculations");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 12, 3, 8);
        uOrderLayoutPanel.add(uOrderWarningLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(uOrderLayoutPanel, gridBagConstraints);

        uDimensionLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        uDimensionLabel.setText("uDimension");
        uDimensionLabel.setToolTipText("automatically calculated, can be specified as an optimization or constraint");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 23, 3, 3);
        add(uDimensionLabel, gridBagConstraints);

        uDimensionTF.setColumns(5);
        uDimensionTF.setToolTipText("automatically calculated, can be specified as an optimization or constraint");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.3333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(uDimensionTF, gridBagConstraints);

        expandableListWeightsAndControlPoints.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                expandableListWeightsAndControlPointsPropertyChange(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(expandableListWeightsAndControlPoints, gridBagConstraints);

        uKnotLabel.setText("uKnot");
        uKnotLabel.setToolTipText("each span of knot values has length=order to adjust the parametric curve");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 23, 3, 3);
        add(uKnotLabel, gridBagConstraints);

        uKnotTextArea.setColumns(20);
        uKnotTextArea.setRows(5);
        uKnotTextArea.setToolTipText("each span of knot values has length=order to adjust the parametric curve");
        uKnotTextArea.setBorder(null);
        uKnotScrollPane.setViewportView(uKnotTextArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(uKnotScrollPane, gridBagConstraints);

        replotButton.setText("Replot");
        replotButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                replotButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 0);
        add(replotButton, gridBagConstraints);

        jFreeChartPanel.setBorder(javax.swing.BorderFactory.createLineBorder(javax.swing.UIManager.getDefaults().getColor("Button.disabledText")));
        jFreeChartPanel.setMinimumSize(new java.awt.Dimension(300, 300));
        jFreeChartPanel.setPreferredSize(new java.awt.Dimension(300, 300));
        jFreeChartPanel.setLayout(new java.awt.BorderLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(jFreeChartPanel, gridBagConstraints);

        nodeHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nodeHintPanel.setLayout(new java.awt.GridBagLayout());

        hintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel.setText("<html><p align='center'><b>NurbsPatchSurface</b> defines a contiguous 3D Non-Uniform Rational B-Spline (NURBS) Surface.</p>");
        hintLabel.setToolTipText("All controlPoint values are saved as part of NurbsTextureCoordinate");
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
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nodeHintPanel, gridBagConstraints);

        vOrderLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        vOrderLabel.setText("vOrder");
        vOrderLabel.setToolTipText("polynomial degree for smoothing calculations");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        add(vOrderLabel, gridBagConstraints);

        vOrderLayoutPanel.setLayout(new java.awt.GridBagLayout());

        vOrderComboBox.setEditable(true);
        vOrderComboBox.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "2", "3", "4", "5", "6" }));
        vOrderComboBox.setToolTipText("polynomial degree for smoothing calculations");
        vOrderComboBox.setMinimumSize(new java.awt.Dimension(35, 20));
        vOrderComboBox.setPreferredSize(new java.awt.Dimension(35, 20));
        vOrderComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vOrderComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        vOrderLayoutPanel.add(vOrderComboBox, gridBagConstraints);

        vOrderWarningLabel.setText(NURBSCURVECustomizer.ORDER_TOO_SMALL_WARNING);
        vOrderWarningLabel.setToolTipText("polynomial degree for smoothing calculations");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 12, 3, 8);
        vOrderLayoutPanel.add(vOrderWarningLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(vOrderLayoutPanel, gridBagConstraints);

        vDimensionLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        vDimensionLabel.setText("vDimension");
        vDimensionLabel.setToolTipText("automatically calculated, can be specified as an optimization or constraint");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        add(vDimensionLabel, gridBagConstraints);

        vDimensionTF.setColumns(5);
        vDimensionTF.setToolTipText("automatically calculated, can be specified as an optimization or constraint");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.3333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(vDimensionTF, gridBagConstraints);

        vKnotLabel.setText("vKnot");
        vKnotLabel.setToolTipText("each span of knot values has length=order to adjust the parametric curve");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        add(vKnotLabel, gridBagConstraints);

        vKnotTextArea.setColumns(20);
        vKnotTextArea.setRows(5);
        vKnotTextArea.setToolTipText("each span of knot values has length=order to adjust the parametric curve");
        vKnotTextArea.setBorder(null);
        vKnotScrollPane.setViewportView(vKnotTextArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(vKnotScrollPane, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void uOrderComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_uOrderComboBoxActionPerformed
    {//GEN-HEADEREND:event_uOrderComboBoxActionPerformed
       update_uOrderWarningLabel();
    }//GEN-LAST:event_uOrderComboBoxActionPerformed

    private void replotButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_replotButtonActionPerformed

        updateJFreeChartPointPlot();     }//GEN-LAST:event_replotButtonActionPerformed

    private void vOrderComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vOrderComboBoxActionPerformed
        update_vOrderWarningLabel();
    }//GEN-LAST:event_vOrderComboBoxActionPerformed

    private void expandableListWeightsAndControlPointsPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_expandableListWeightsAndControlPointsPropertyChange
        updateJFreeChartPointPlot ();
    }//GEN-LAST:event_expandableListWeightsAndControlPointsPropertyChange
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel1;
    private org.web3d.x3d.palette.items.ExpandableList expandableListWeightsAndControlPoints;
    private javax.swing.JLabel hintLabel;
    private javax.swing.JPanel jFreeChartPanel;
    private javax.swing.JPanel nodeHintPanel;
    private javax.swing.JButton replotButton;
    private javax.swing.JCheckBox solidCB;
    private javax.swing.JCheckBox uClosedCheckBox;
    private javax.swing.JLabel uClosedLabel;
    private javax.swing.JLabel uDimensionLabel;
    private javax.swing.JTextField uDimensionTF;
    private javax.swing.JLabel uKnotLabel;
    private javax.swing.JScrollPane uKnotScrollPane;
    private javax.swing.JTextArea uKnotTextArea;
    private javax.swing.JComboBox<String> uOrderComboBox;
    private javax.swing.JLabel uOrderLabel;
    private javax.swing.JPanel uOrderLayoutPanel;
    private javax.swing.JLabel uOrderWarningLabel;
    private javax.swing.JLabel uTessellationLabel;
    private javax.swing.JTextField uTessellationTF;
    private javax.swing.JCheckBox vClosedCheckBox;
    private javax.swing.JLabel vClosedlabel;
    private javax.swing.JLabel vDimensionLabel;
    private javax.swing.JTextField vDimensionTF;
    private javax.swing.JLabel vKnotLabel;
    private javax.swing.JScrollPane vKnotScrollPane;
    private javax.swing.JTextArea vKnotTextArea;
    private javax.swing.JComboBox<String> vOrderComboBox;
    private javax.swing.JLabel vOrderLabel;
    private javax.swing.JPanel vOrderLayoutPanel;
    private javax.swing.JLabel vOrderWarningLabel;
    private javax.swing.JLabel vTessellationLabel;
    private javax.swing.JTextField vTessellationTF;
    // End of variables declaration//GEN-END:variables
  
  
  @Override
  public String getNameKey()
  {
    return "NAME_X3D_NURBSPATCHSURFACE";
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
                tooltipList.add("NurbsTextureCoordinate controlPoint [" + String.valueOf(i) + "] = (" + singleDigitFormat.format(x) + ", " + singleDigitFormat.format(y) + ")");
            }
        }
        XYSeriesCollection datasetCollection = new XYSeriesCollection();
        datasetCollection.addSeries(pointSeries);

        // create a chart...
        chart = ChartFactory.createXYLineChart(
            "NurbsTextureCoordinate controlPoint plot",
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
        TextTitle title = new TextTitle("NurbsTextureCoordinate controlPoint array");
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
    
    nurbsPatchSurface.setSolid(solidCB.isSelected());
    
    nurbsPatchSurface.set_uDimension(uDimensionTF.getText().trim());
    nurbsPatchSurface.set_vDimension(vDimensionTF.getText().trim());
    
    nurbsPatchSurface.set_uKnot(uKnotTextArea.getText().replaceAll("\\s+", " ").trim()); // normalize whitespace
    nurbsPatchSurface.set_vKnot(vKnotTextArea.getText().replaceAll("\\s+", " ").trim()); // normalize whitespace
    
    if (   (uOrderComboBox.getSelectedIndex() >= 0)
        || (uOrderComboBox.getSelectedItem().toString().length() > 0))// avoid reseting to values < 2; TODO confirm
        nurbsPatchSurface.set_uOrder(String.valueOf(uOrderComboBox.getSelectedItem()));
    if (   (vOrderComboBox.getSelectedIndex() >= 0)
        || (vOrderComboBox.getSelectedItem().toString().length() > 0))// avoid reseting to values < 2; TODO confirm
        nurbsPatchSurface.set_vOrder(String.valueOf(vOrderComboBox.getSelectedItem()));
    
    nurbsPatchSurface.set_uTessellation(uTessellationTF.getText().trim());
    nurbsPatchSurface.set_vTessellation(vTessellationTF.getText().trim());
    
    nurbsPatchSurface.setWeightsAndControlPoints (expandableListWeightsAndControlPoints.getData());
    nurbsPatchSurface.setInsertCommas            (expandableListWeightsAndControlPoints.isInsertCommasSet());
    nurbsPatchSurface.setInsertLineBreaks        (expandableListWeightsAndControlPoints.isInsertLineBreaksSet());
  } 

}
