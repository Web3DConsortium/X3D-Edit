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

import java.awt.Color;
import javax.swing.text.JTextComponent;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.HelpCtx;
import org.web3d.x3d.palette.X3DPaletteUtilitiesJdom;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * NURBSORIENTATIONINTERPOLATORCustomizer.java
 * Created on 18 December 2011
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class NURBSORIENTATIONINTERPOLATORCustomizer extends BaseCustomizer
{ 
  private NURBSORIENTATIONINTERPOLATOR nurbsOrientationInterpolator;
  private JTextComponent target;
  
  /** Creates new form NURBSORIENTATIONINTERPOLATORCustomizer
   * @param nurbsOrientationInterpolator NURBSORIENTATIONINTERPOLATOR node holding data structures for scene graph
   * @param target Swing component of interest Swing component
   */
  public NURBSORIENTATIONINTERPOLATORCustomizer(NURBSORIENTATIONINTERPOLATOR nurbsOrientationInterpolator, JTextComponent target)
  {
    super(nurbsOrientationInterpolator);
    this.nurbsOrientationInterpolator=nurbsOrientationInterpolator;
    this.target = target;
    
    org.jdom.Document documentRoot = X3DPaletteUtilitiesJdom.getJdom(target);
    nurbsOrientationInterpolator.setDocumentRootRecheckUSEpointValues (documentRoot); // recalculates controlPoint array if USE node involved
                           
    HelpCtx.setHelpIDString(NURBSORIENTATIONINTERPOLATORCustomizer.this, "NURBSORIENTATIONINTERPOLATOR_ELEM_HELPID");
    
    initComponents();
    
    orderComboBox.setSelectedItem(String.valueOf(nurbsOrientationInterpolator.getOrder())); // also invokes updateOrderWarningLabel()
    
    knotTextArea.setText(nurbsOrientationInterpolator.getKnot());

    expandableListWeightsAndControlPoints.setTitle("weight, controlPoint arrays");
    expandableListWeightsAndControlPoints.setColumnTitles  (new String[]{"#","weight","controlPoint.x","controlPoint.y","controlPoint.z"});
    expandableListWeightsAndControlPoints.setColumnToolTips(new String[]{"index","weight value","controlPoint.x","controlPoint.y","controlPoint.z"});
    expandableListWeightsAndControlPoints.setHeaderTooltip ("weight, controlPoint values define a parametric curve");
    expandableListWeightsAndControlPoints.setNewRowData(new Object[]{"1","0","0","0"});  //  weight=1, values 0
    expandableListWeightsAndControlPoints.doIndexInFirstColumn(true);
    expandableListWeightsAndControlPoints.setBoldColumn(1);

    expandableListWeightsAndControlPoints.setShowAppendCommasLineBreaks(true);
    expandableListWeightsAndControlPoints.setKeyColumnIncluded(false);
    expandableListWeightsAndControlPoints.setInsertCommas    (nurbsOrientationInterpolator.isInsertCommas());
    expandableListWeightsAndControlPoints.setInsertLineBreaks(nurbsOrientationInterpolator.isInsertLineBreaks());
    expandableListWeightsAndControlPoints.setColumnWidthAndResizeStrategy(false, 75);
    
    String[][] saa = nurbsOrientationInterpolator.getWeightsAndControlPoints(); // may be 0-length
    expandableListWeightsAndControlPoints.setData(saa);
    
    if (nurbsOrientationInterpolator.isCoordinateNodeFound()) // CoordinateDouble is default if none provided
    {
        hintLabel.setText       (hintLabel.getText().replace(       "CoordinateDouble", "Coordinate"));
        hintLabel.setToolTipText(hintLabel.getToolTipText().replace("CoordinateDouble", "Coordinate"));
    }
    // if present, show DEF/USE name on interface
    if      (nurbsOrientationInterpolator.isCoordinateUseNodeFound())
    {
            sourceLabel.setText(sourceLabel.getText() + "   &lt;Coordinate USE='" + nurbsOrientationInterpolator.getCoordinateNodeUSE() + "'/&gt;");
    }
    else if (nurbsOrientationInterpolator.isCoordinateDoubleUseNodeFound())
    {
            sourceLabel.setText(sourceLabel.getText() + "   &lt;CoordinateDouble USE='" + nurbsOrientationInterpolator.getCoordinateNodeUSE() + "'/&gt;");
    }
    else if (nurbsOrientationInterpolator.isCoordinateDefNodeFound())
    {
            sourceLabel.setText(sourceLabel.getText() + "   &lt;Coordinate DEF='" + nurbsOrientationInterpolator.getCoordinateNodeDEF() + "'/&gt;");
    }
    else if (nurbsOrientationInterpolator.isCoordinateDoubleDefNodeFound())
    {
            sourceLabel.setText(sourceLabel.getText() + "   &lt;CoordinateDouble DEF='" + nurbsOrientationInterpolator.getCoordinateNodeDEF() + "'/&gt;");
    }
    else    sourceLabel.setVisible(false);
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
        expandableListWeightsAndControlPoints = new org.web3d.x3d.palette.items.ExpandableList();
        knotLabel = new javax.swing.JLabel();
        knotScrollPane = new javax.swing.JScrollPane();
        knotTextArea = new javax.swing.JTextArea();
        nodeHintPanel = new javax.swing.JPanel();
        hintLabel = new javax.swing.JLabel();
        sourceLabel = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
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

        nodeHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nodeHintPanel.setLayout(new java.awt.GridBagLayout());

        hintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel.setText("<html><p align='center'><b>NurbsOrientionInterpolator</b> returns tangents to a parametric curve in 3D space.</p> <br /> \n<p align='center'>Primary input event is <b>set_fraction</b> along the knot spans, <br /> \nwhile primary output event is orientation <b>value_changed</b>.</p> <br /> \n<p align='center'>Changes to the <i>controlPoint</i> array are saved as a child <b>Coordinate</b>|<b>CoordinateDouble</b> node.</p>");
        hintLabel.setToolTipText("CoordinateDouble child node editing is handled automatically by NurbsPositionInterpolator");
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
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nodeHintPanel, gridBagConstraints);

        sourceLabel.setText("<html>controlPoint source:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 0, 20);
        add(sourceLabel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void orderComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_orderComboBoxActionPerformed
    {//GEN-HEADEREND:event_orderComboBoxActionPerformed
       updateOrderWarningLabel();
    }//GEN-LAST:event_orderComboBoxActionPerformed
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel1;
    private org.web3d.x3d.palette.items.ExpandableList expandableListWeightsAndControlPoints;
    private javax.swing.JLabel hintLabel;
    private javax.swing.JLabel knotLabel;
    private javax.swing.JScrollPane knotScrollPane;
    private javax.swing.JTextArea knotTextArea;
    private javax.swing.JPanel nodeHintPanel;
    private javax.swing.JComboBox<String> orderComboBox;
    private javax.swing.JLabel orderLabel;
    private javax.swing.JPanel orderLayoutPanel;
    private javax.swing.JLabel orderWarningLabel;
    private javax.swing.JLabel sourceLabel;
    // End of variables declaration//GEN-END:variables
  
  
  @Override
  public String getNameKey()
  {
    return "NAME_X3D_NURBSORIENTATIONINTERPOLATOR";
  }

  @Override
  public void unloadInput() throws IllegalArgumentException
  {
    StringBuilder notificationMessage = new StringBuilder();
    String nodeName = "Coordinate";
    if (nurbsOrientationInterpolator.isCoordinateDoubleNodeFound())
           nodeName = "CoordinateDouble";
    notificationMessage.append("<html><p align='center'>");
    notificationMessage.append(nodeName);
    notificationMessage.append(" controlPoint values were originally copied from a USE node</p>");
    notificationMessage.append("<p align='center'> reference: <b>&lt;");
    notificationMessage.append(nodeName);
    notificationMessage.append(" USE='");
    notificationMessage.append(nurbsOrientationInterpolator.getCoordinateNodeUSE());
    notificationMessage.append("'/&gt;</b></p><br />");
    notificationMessage.append("<p align='center'>Select Yes to save changes as a local <b>&lt;");
    notificationMessage.append(nodeName);
    notificationMessage.append(" point='</b>..<i>values</i>..<b>'/&gt;</b> node instead?</p>");
    notificationMessage.append("<p align='center'>Select no to discard any changes.</p>");
    
    NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
          notificationMessage.toString(),
          "Create new " + nodeName + "?", NotifyDescriptor.YES_NO_OPTION);
    if ((expandableListWeightsAndControlPoints.hasChangedValues() || nurbsOrientationInterpolator.hasChangedValues()) &&
        ( nurbsOrientationInterpolator.isCoordinateUseNodeFound() || nurbsOrientationInterpolator.isCoordinateDoubleUseNodeFound()))
    {
        if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
        {
            nurbsOrientationInterpolator.setCoordinateValueArrayEditable(true);
        }
    }
    nurbsOrientationInterpolator.setWeightsAndControlPoints (expandableListWeightsAndControlPoints.getData());
    nurbsOrientationInterpolator.setInsertCommas            (expandableListWeightsAndControlPoints.isInsertCommasSet());
    nurbsOrientationInterpolator.setInsertLineBreaks        (expandableListWeightsAndControlPoints.isInsertLineBreaksSet());
    
    unLoadDEFUSE();
    
    nurbsOrientationInterpolator.setKnot(knotTextArea.getText().replaceAll("\\s+", " ").trim()); // normalize whitespace
    
    if (   (orderComboBox.getSelectedIndex() >= 0)
        || (orderComboBox.getSelectedItem().toString().length() > 0))// avoid reseting to values < 2; TODO confirm
        nurbsOrientationInterpolator.setOrder(String.valueOf(orderComboBox.getSelectedItem()));
    
  } 

}
