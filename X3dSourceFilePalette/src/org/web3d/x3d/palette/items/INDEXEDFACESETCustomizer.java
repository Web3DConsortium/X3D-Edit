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
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * INDEXEDFACESETCustomizer.java
 * Created on Sep 12, 2007, 3:05 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class INDEXEDFACESETCustomizer extends BaseCustomizer
{
  private final INDEXEDFACESET indexedFaceSet;
  private final JTextComponent target;
  
  private int numberOfPoints   = 0;
  private int numberOfPolygons = 0;

  private boolean  readyForPanelUpdates = false;
  
  public INDEXEDFACESETCustomizer(INDEXEDFACESET indexedFaceSet, JTextComponent target)
  {
    super(indexedFaceSet);
    this.indexedFaceSet = indexedFaceSet;
    this.target = target;
    
    HelpCtx.setHelpIDString(this, "INDEXEDFACESET_ELEM_HELPID");

    initComponents();
    
    commonInitialization ();
  }
  private void commonInitialization ()
  {
                ccwCB.setSelected(indexedFaceSet.isCcw());
             convexCB.setSelected(indexedFaceSet.isConvex());
              solidCB.setSelected(indexedFaceSet.isSolid());
     colorPerVertexCB.setSelected(indexedFaceSet.isColorPerVertex());
    normalPerVertexCB.setSelected(indexedFaceSet.isNormalPerVertex());
        creaseAngleTF.setText(indexedFaceSet.getCreaseAngle());
    
    // =====================
    if (indexedFaceSet.getChildCoordArraySize() > 0)
         expandableListCoordIndex.setTitle("coordIndex array values have allowed range [0.." + (indexedFaceSet.getChildCoordArraySize() - 1) + "] where each polygon index sequence ends with sentinel value -1");
    else expandableListCoordIndex.setTitle("coordIndex array values have allowed range [0..# Cooordinate points] where each polygon index sequence ends with sentinel value -1");
    expandableListCoordIndex.setColumnTitles(new String[]{"#","coordIndex array for each polygon"});
    expandableListCoordIndex.setColumnToolTips(new String[]{"index","index list"});
    expandableListCoordIndex.setHeaderTooltip ("Coordinate indices for point connectivity");
    expandableListCoordIndex.doIndexInFirstColumn(true);
    expandableListCoordIndex.setRedColumn(-1); // 0 is index, -1 means no color editing
    expandableListCoordIndex.setColumnWidthAndResizeStrategy(false, 75);
    expandableListCoordIndex.setDefaultCellValue("0 0 0 -1"); // TODO not working
    
    expandableListCoordIndex.setToolTipText  ("coordIndex array of coordinate indices");
    expandableListCoordIndex.setHeaderTooltip("coordIndex array of coordinate indices");
    expandableListCoordIndex.setCellEditPanelVisible      (false);
    expandableListCoordIndex.setInsertPanelVisible        (true);
    expandableListCoordIndex.setGeneratePointsPanelVisible(false);
        
    String[] sa = INDEXEDFACESET.parseMFStringIntoStringArray(indexedFaceSet.getCoordIndex(), false, true, "-1"); // may be 0-length
    if (sa.length != 0)
    {
        expandableListCoordIndex.setData(sa);
        expandableListCoordIndex.setShowAppendCommasLineBreaks(true);
        expandableListCoordIndex.setInsertCommas    (indexedFaceSet.isInsertCommasCoordIndex());
        expandableListCoordIndex.setInsertLineBreaks(indexedFaceSet.isInsertLineBreaksCoordIndex());
    }

    numberOfPoints   = indexedFaceSet.getChildCoordArraySize();
    numberOfPolygons = expandableListCoordIndex.getData().length;
    if (numberOfPolygons != 1)
         polygonArrayCountLabel.setText("<html>(<b>" + numberOfPolygons + "</b> polygons defined by coordIndex)");
    else polygonArrayCountLabel.setText("<html>(<b>" + numberOfPolygons + "</b> polygon defined by coordIndex)");
    
    coordArrayCountLabel.setText("<html>(<b>" + indexedFaceSet.getChildCoordArraySize() + "</b> contained <b>Coordinate</b> points)");
    if (indexedFaceSet.getChildCoordArraySize() > 0)
        coordArrayCountLabel.setToolTipText("allowed coordIndex values are -1,0.."       + (indexedFaceSet.getChildCoordArraySize()    - 1));
    
    // =====================
    expandableListColorIndex.setColumnToolTips(new String[]{"index","index list"});
    expandableListColorIndex.doIndexInFirstColumn(true);
    expandableListColorIndex.setRedColumn(-1); // 0 is index, -1 means no color editing
    expandableListColorIndex.setColumnWidthAndResizeStrategy(false, 75);
    expandableListColorIndex.setDefaultCellValue("0");
    
    expandableListColorIndex.setToolTipText  ("colorIndex array of color indices");
    expandableListColorIndex.setHeaderTooltip("colorIndex array of color indices");
    expandableListColorIndex.setCellEditPanelVisible      (false);
    expandableListColorIndex.setInsertPanelVisible        (true);
    expandableListColorIndex.setGeneratePointsPanelVisible(false);

    sa = INDEXEDFACESET.parseMFStringIntoStringArray(indexedFaceSet.getColorIndex(), false, true, "-1"); // may be 0-length
    if (sa.length != 0)
    {
        expandableListColorIndex.setData(sa);
        expandableListColorIndex.setShowAppendCommasLineBreaks(true);
        expandableListColorIndex.setInsertCommas    (indexedFaceSet.isInsertCommasColorIndex());
        expandableListColorIndex.setInsertLineBreaks(indexedFaceSet.isInsertLineBreaksColorIndex());
    }

    colorArrayCountLabel.setText("<html>(<b>" + indexedFaceSet.getChildColorArraySize() + "</b> contained <b>Color</b> values)");
    if (indexedFaceSet.getChildColorArraySize() > 0)
        colorArrayCountLabel.setToolTipText("allowed colorIndex values are -1,0.."       + (indexedFaceSet.getChildColorArraySize()    - 1));

    // =====================
    if (indexedFaceSet.getChildNormalArraySize() > 0)
         expandableListNormalIndex.setTitle("normalIndex array values have allowed range [0.." + (indexedFaceSet.getChildNormalArraySize() - 1) + "]");
    else expandableListNormalIndex.setTitle("normalIndex array values have allowed range [0..# Normal values]");
    
    expandableListNormalIndex.setColumnToolTips(new String[]{"index","index list"});
    expandableListNormalIndex.doIndexInFirstColumn(true);
    expandableListNormalIndex.setRedColumn(-1); // 0 is index, -1 means no normal editing
    expandableListNormalIndex.setColumnWidthAndResizeStrategy(false, 75);
    expandableListNormalIndex.setDefaultCellValue("0");
    
    expandableListNormalIndex.setToolTipText  ("normalIndex array of normal indices");
    expandableListNormalIndex.setHeaderTooltip("normalIndex array of normal indices");
    expandableListNormalIndex.setCellEditPanelVisible      (false);
    expandableListNormalIndex.setInsertPanelVisible        (true);
    expandableListNormalIndex.setGeneratePointsPanelVisible(false);

    sa = INDEXEDFACESET.parseMFStringIntoStringArray(indexedFaceSet.getNormalIndex(), false, true, "-1"); // may be 0-length
    if (sa.length != 0)
    {
        expandableListNormalIndex.setData(sa);
        expandableListNormalIndex.setShowAppendCommasLineBreaks(true);
        expandableListNormalIndex.setInsertCommas    (indexedFaceSet.isInsertCommasNormalIndex());
        expandableListNormalIndex.setInsertLineBreaks(indexedFaceSet.isInsertLineBreaksNormalIndex());
    }

    normalArrayCountLabel.setText("<html>(<b>" + indexedFaceSet.getChildNormalArraySize() + "</b> contained <b>Normal</b> values)");
    if (indexedFaceSet.getChildNormalArraySize() > 0)
        normalArrayCountLabel.setToolTipText("allowed normalIndex values are -1,0.."       + (indexedFaceSet.getChildNormalArraySize()    - 1));

    // =====================
    if (indexedFaceSet.getChildTexCoordArraySize() > 0)
         expandableListTexCoordIndex.setTitle("texCoordIndex array values have allowed range [0.." + (indexedFaceSet.getChildTexCoordArraySize() - 1) + "] or -1");
    else expandableListTexCoordIndex.setTitle("texCoordIndex array values have allowed range [0..# TextureCoordinate values] or -1");
    expandableListTexCoordIndex.setColumnTitles(new String[]{"#","texCoordIndex array matching vertices to TextureCoordinate values"});
    expandableListTexCoordIndex.setHeaderTooltip ("texCoord indices matching vertices to TextureCoordinate point values");

    expandableListTexCoordIndex.setColumnToolTips(new String[]{"index","index list"});
    expandableListTexCoordIndex.doIndexInFirstColumn(true);
    expandableListTexCoordIndex.setRedColumn(-1); // 0 is index, -1 means no texcoord editing
    expandableListTexCoordIndex.setColumnWidthAndResizeStrategy(false, 75);
    expandableListTexCoordIndex.setDefaultCellValue("0");
    
    expandableListTexCoordIndex.setToolTipText  ("texCoordIndex array of texcoord indices");
    expandableListTexCoordIndex.setHeaderTooltip("texCoordIndex array of texcoord indices");
    expandableListTexCoordIndex.setCellEditPanelVisible      (false);
    expandableListTexCoordIndex.setInsertPanelVisible        (true);
    expandableListTexCoordIndex.setGeneratePointsPanelVisible(false);
        
    sa = INDEXEDFACESET.parseMFStringIntoStringArray(indexedFaceSet.getTexCoordIndex(), false, true, "-1"); // may be 0-length
    if (sa.length != 0)
    {
        expandableListTexCoordIndex.setData(sa);
        expandableListTexCoordIndex.setShowAppendCommasLineBreaks(true);
        expandableListTexCoordIndex.setInsertCommas    (indexedFaceSet.isInsertCommasTexCoordIndex());
        expandableListTexCoordIndex.setInsertLineBreaks(indexedFaceSet.isInsertLineBreaksTexCoordIndex());
    }

    texCoordArrayCountLabel.setText("<html>(<b>" + indexedFaceSet.getChildTexCoordArraySize() + "</b> contained <b>TextureCoordinate</b> values)");
    if (indexedFaceSet.getChildTexCoordArraySize() > 0)
        texCoordArrayCountLabel.setToolTipText("allowed texCoordIndex values are -1,0.."       + (indexedFaceSet.getChildTexCoordArraySize()    - 1));
    
    readyForPanelUpdates = true;
    commonPanelUpdate ();
  }
  private void commonPanelUpdate ()
  {
//    if (!readyForPanelUpdates) return;
    
    String expectedColorArrayLength = "expected overall array length is ";
    if (colorPerVertexCB.isSelected())
    {
        expectedColorArrayLength += expandableListCoordIndex.getRowCount() + " (matching point sequences in coordIndex array)";
        expandableListColorIndex.setColumnTitles(new String[]{"#","colorIndex array for each vertex"});
        expandableListColorIndex.setHeaderTooltip ("Color indices for vertex colors");
    }
    else 
    {
        expectedColorArrayLength += expandableListCoordIndex.getRowCount() + " (matching polygon face count)";
        expandableListColorIndex.setColumnTitles(new String[]{"#","colorIndex array for each polygon"});
        expandableListColorIndex.setHeaderTooltip ("Color indices for each polygon color");
    }
    if (indexedFaceSet.getChildColorArraySize() > 0)
         expandableListColorIndex.setTitle("colorIndex array values have allowed range [0.." + (indexedFaceSet.getChildColorArraySize() - 1) + "] where " + expectedColorArrayLength);
    else expandableListColorIndex.setTitle("colorIndex array values have allowed range [0..# Color values] where " + expectedColorArrayLength);
    
    if (normalPerVertexCB.isSelected())
    {
        expandableListNormalIndex.setColumnTitles(new String[]{"#","normalIndex array for each vertex"});
        expandableListNormalIndex.setHeaderTooltip ("Normal indices for vertex normals");
    }
    else 
    {
        expandableListNormalIndex.setColumnTitles(new String[]{"#","normalIndex array for each polygon"});
        expandableListNormalIndex.setHeaderTooltip ("Normal indices for polygon normals");
    }
    
    // =====================
    // buttons to add indices
    
    if ((expandableListColorIndex.getRowCount()    < expandableListCoordIndex.getRowCount()) && (indexedFaceSet.getChildColorArraySize() > 0))
         colorIndexAppendButton.setEnabled(true);
    else colorIndexAppendButton.setEnabled(false);
    
    if ((expandableListNormalIndex.getRowCount()   < expandableListCoordIndex.getRowCount()) && (indexedFaceSet.getChildNormalArraySize() > 0))
         normalIndexAppendButton.setEnabled(true);
    else normalIndexAppendButton.setEnabled(false);
    
    if ((expandableListTexCoordIndex.getRowCount() < expandableListCoordIndex.getRowCount()) && (indexedFaceSet.getChildTexCoordArraySize() > 0))
         texCoordIndexAppendButton.setEnabled(true);
    else texCoordIndexAppendButton.setEnabled(false);
    
    // =====================

    checkAngles (false);

    checkIndices();
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
        checkBoxButtonLabelPanel = new javax.swing.JPanel();
        checkBoxPanel = new javax.swing.JPanel();
        ccwCB = new javax.swing.JCheckBox();
        convexCB = new javax.swing.JCheckBox();
        solidCB = new javax.swing.JCheckBox();
        creaseAngleLabel = new javax.swing.JLabel();
        creaseAngleTF = new javax.swing.JTextField();
        normalizeCreaseAngleButton = new javax.swing.JButton();
        jSeparatorHorizontal1 = new javax.swing.JSeparator();
        polygonArrayCountLabel = new javax.swing.JLabel();
        coordArrayCountLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        colorPerVertexCB = new javax.swing.JCheckBox();
        colorArrayCountLabel = new javax.swing.JLabel();
        colorIndexAppendButton = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        normalPerVertexCB = new javax.swing.JCheckBox();
        normalArrayCountLabel = new javax.swing.JLabel();
        normalIndexAppendButton = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        texCoordArrayCountLabel = new javax.swing.JLabel();
        texCoordIndexAppendButton = new javax.swing.JButton();
        jSeparatorHorizontal2 = new javax.swing.JSeparator();
        expandableListTabbedPane = new javax.swing.JTabbedPane();
        expandableListCoordIndex = new org.web3d.x3d.palette.items.ExpandableList();
        expandableListColorIndex = new org.web3d.x3d.palette.items.ExpandableList();
        expandableListNormalIndex = new org.web3d.x3d.palette.items.ExpandableList();
        expandableListTexCoordIndex = new org.web3d.x3d.palette.items.ExpandableList();
        nodeHintPanel = new javax.swing.JPanel();
        hintLabel = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.ABOVE_BASELINE_LEADING;
        add(dEFUSEpanel1, gridBagConstraints);

        checkBoxButtonLabelPanel.setLayout(new java.awt.GridBagLayout());

        checkBoxPanel.setLayout(new java.awt.GridBagLayout());

        ccwCB.setText("ccw");
        ccwCB.setToolTipText("ccw = counterclockwise: ordering of vertex coordinates orientation");
        ccwCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        ccwCB.setMaximumSize(null);
        ccwCB.setMinimumSize(null);
        ccwCB.setPreferredSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        checkBoxPanel.add(ccwCB, gridBagConstraints);

        convexCB.setText("convex");
        convexCB.setToolTipText("whether all polygons are convex (true), or possibly concave (false)");
        convexCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        convexCB.setMaximumSize(null);
        convexCB.setMinimumSize(null);
        convexCB.setPreferredSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        checkBoxPanel.add(convexCB, gridBagConstraints);

        solidCB.setText("solid");
        solidCB.setToolTipText("Setting solid true means draw only one side of polygons (backface culling on), setting solid false means draw both sides of polygons (backface culling off)");
        solidCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        solidCB.setMaximumSize(null);
        solidCB.setMinimumSize(null);
        solidCB.setPreferredSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        checkBoxPanel.add(solidCB, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 0);
        checkBoxButtonLabelPanel.add(checkBoxPanel, gridBagConstraints);

        creaseAngleLabel.setText("creaseAngle");
        creaseAngleLabel.setToolTipText("defines angle (in radians) for determining whether adjacent polygons are drawn with sharp edges or smooth shading");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        checkBoxButtonLabelPanel.add(creaseAngleLabel, gridBagConstraints);

        creaseAngleTF.setToolTipText("defines angle (in radians) for determining whether adjacent polygons are drawn with sharp edges or smooth shading");
        creaseAngleTF.setMinimumSize(new java.awt.Dimension(30, 20));
        creaseAngleTF.setPreferredSize(new java.awt.Dimension(30, 20));
        creaseAngleTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                creaseAngleTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        checkBoxButtonLabelPanel.add(creaseAngleTF, gridBagConstraints);

        normalizeCreaseAngleButton.setText("normalize creaseAngle");
        normalizeCreaseAngleButton.setToolTipText("reset creaseAngle [0..2pi)");
        normalizeCreaseAngleButton.setMargin(new java.awt.Insets(3, 3, 3, 3));
        normalizeCreaseAngleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                normalizeCreaseAngleButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        checkBoxButtonLabelPanel.add(normalizeCreaseAngleButton, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 0);
        checkBoxButtonLabelPanel.add(jSeparatorHorizontal1, gridBagConstraints);

        polygonArrayCountLabel.setText("(polygon count)");
        polygonArrayCountLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 40, 3, 10);
        checkBoxButtonLabelPanel.add(polygonArrayCountLabel, gridBagConstraints);

        coordArrayCountLabel.setText("(coord array count)");
        coordArrayCountLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.ABOVE_BASELINE_LEADING;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 40, 3, 10);
        checkBoxButtonLabelPanel.add(coordArrayCountLabel, gridBagConstraints);

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 3);
        checkBoxButtonLabelPanel.add(jSeparator1, gridBagConstraints);

        colorPerVertexCB.setText("colorPerVertex");
        colorPerVertexCB.setToolTipText("whether Color node is applied per vertex (true) or per polygon (false)");
        colorPerVertexCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        colorPerVertexCB.setMinimumSize(new java.awt.Dimension(40, 20));
        colorPerVertexCB.setPreferredSize(new java.awt.Dimension(40, 20));
        colorPerVertexCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorPerVertexCBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        checkBoxButtonLabelPanel.add(colorPerVertexCB, gridBagConstraints);

        colorArrayCountLabel.setText("(color array count)");
        colorArrayCountLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.ABOVE_BASELINE_LEADING;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        checkBoxButtonLabelPanel.add(colorArrayCountLabel, gridBagConstraints);

        colorIndexAppendButton.setText("add colorIndex values");
        colorIndexAppendButton.setToolTipText("append colorIndex example values");
        colorIndexAppendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorIndexAppendButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.ABOVE_BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 10, 3);
        checkBoxButtonLabelPanel.add(colorIndexAppendButton, gridBagConstraints);

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 3);
        checkBoxButtonLabelPanel.add(jSeparator2, gridBagConstraints);

        normalPerVertexCB.setText("normalPerVertex");
        normalPerVertexCB.setToolTipText("whether Normal node is applied per vertex (true) or per polygon (false");
        normalPerVertexCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        normalPerVertexCB.setMinimumSize(new java.awt.Dimension(40, 20));
        normalPerVertexCB.setPreferredSize(new java.awt.Dimension(40, 20));
        normalPerVertexCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                normalPerVertexCBActionPerformed(evt);
            }
        });
        normalPerVertexCB.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                normalPerVertexCBPropertyChange(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        checkBoxButtonLabelPanel.add(normalPerVertexCB, gridBagConstraints);

        normalArrayCountLabel.setText("(normal array count)");
        normalArrayCountLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.ABOVE_BASELINE_LEADING;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        checkBoxButtonLabelPanel.add(normalArrayCountLabel, gridBagConstraints);

        normalIndexAppendButton.setText("add normalIndex values");
        normalIndexAppendButton.setToolTipText("append normalIndex example values");
        normalIndexAppendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                normalIndexAppendButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.ABOVE_BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 10, 3);
        checkBoxButtonLabelPanel.add(normalIndexAppendButton, gridBagConstraints);

        jSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 3);
        checkBoxButtonLabelPanel.add(jSeparator3, gridBagConstraints);

        texCoordArrayCountLabel.setText("(texCoord array count)");
        texCoordArrayCountLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.ABOVE_BASELINE_LEADING;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        checkBoxButtonLabelPanel.add(texCoordArrayCountLabel, gridBagConstraints);

        texCoordIndexAppendButton.setText("add texCoordIndex values");
        texCoordIndexAppendButton.setToolTipText("append example texcoordIndex values");
        texCoordIndexAppendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                texCoordIndexAppendButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.ABOVE_BASELINE_LEADING;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 10, 3);
        checkBoxButtonLabelPanel.add(texCoordIndexAppendButton, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 3, 0);
        checkBoxButtonLabelPanel.add(jSeparatorHorizontal2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        add(checkBoxButtonLabelPanel, gridBagConstraints);

        expandableListTabbedPane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mouseClickedListener(evt);
            }
        });

        expandableListCoordIndex.setMinimumSize(new java.awt.Dimension(200, 110));
        expandableListCoordIndex.setPreferredSize(new java.awt.Dimension(300, 250));
        expandableListCoordIndex.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                expandableListCoordIndexPropertyChange(evt);
            }
        });
        expandableListTabbedPane.addTab("coordIndex", expandableListCoordIndex);

        expandableListColorIndex.setPreferredSize(new java.awt.Dimension(632, 250));
        expandableListTabbedPane.addTab("colorIndex", expandableListColorIndex);

        expandableListNormalIndex.setPreferredSize(new java.awt.Dimension(632, 250));
        expandableListTabbedPane.addTab("normalIndex", expandableListNormalIndex);

        expandableListTexCoordIndex.setPreferredSize(new java.awt.Dimension(632, 250));
        expandableListTabbedPane.addTab("texCoordIndex", expandableListTexCoordIndex);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(expandableListTabbedPane, gridBagConstraints);

        nodeHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nodeHintPanel.setLayout(new java.awt.GridBagLayout());

        hintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel.setText("<html><p align='center'><b>IndexedFaceSetSet</b> can contain </p> <p align='center'> a <b>Coordinate|CoordinateDouble</b> node, a <b>Color|ColorRGBA</b> node, a <b>Normal</b> node, and </p> <p align='center'><b>TextureCoordinate|TextureCoordinateGenerator|MultiTextureCoordinate</b> nodes</p>");
        hintLabel.setToolTipText("close this panel to add children nodes");
        hintLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        nodeHintPanel.add(hintLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nodeHintPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

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

    private void expandableListCoordIndexPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_expandableListCoordIndexPropertyChange
        numberOfPolygons = expandableListCoordIndex.getData().length;
        if (numberOfPolygons != 1)
             polygonArrayCountLabel.setText("<html>(<b>" + numberOfPolygons + "</b> polygons defined by coordIndex)");
        else polygonArrayCountLabel.setText("<html>(<b>" + numberOfPolygons + "</b> polygon defined by coordIndex)");
        expandableListCoordIndex.doIndexInFirstColumn(true);
    }//GEN-LAST:event_expandableListCoordIndexPropertyChange

    private void colorPerVertexCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorPerVertexCBActionPerformed
        checkColorIndices();
        if (colorPerVertexCB.isSelected())
        {
            expandableListColorIndex.setColumnTitles(new String[]{"#","colorIndex array for each vertex"});
            expandableListColorIndex.setHeaderTooltip ("Color indices for vertex colors");
        }
        else 
        {
            expandableListColorIndex.setColumnTitles(new String[]{"#","colorIndex array for each polygon"});
            expandableListColorIndex.setHeaderTooltip ("Color indices for polygon colors");
        }
        expandableListColorIndex.doIndexInFirstColumn(true);
        commonPanelUpdate (); // reset counts, tooltips
    }//GEN-LAST:event_colorPerVertexCBActionPerformed

    private void normalPerVertexCBPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_normalPerVertexCBPropertyChange
        if (normalPerVertexCB.isSelected())
        {
            expandableListNormalIndex.setColumnTitles(new String[]{"#","normalIndex array for each vertex"});
            expandableListNormalIndex.setHeaderTooltip ("Normal indices for vertex normals");
        }
        else 
        {
            expandableListNormalIndex.setColumnTitles(new String[]{"#","normalIndex array for each polygon"});
            expandableListNormalIndex.setHeaderTooltip ("Normal indices for polygon normals");
        }
        expandableListNormalIndex.doIndexInFirstColumn(true);
    }//GEN-LAST:event_normalPerVertexCBPropertyChange

    private void colorIndexAppendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_colorIndexAppendButtonActionPerformed
        String [][] saa = expandableListColorIndex.getData();
        int  initialLength = saa.length;
        if ((initialLength == 1) && saa[0][0].isEmpty())
             initialLength = 0;
        int finalLength;
        if (colorPerVertexCB.isSelected())
             finalLength = indexedFaceSet.getChildCoordArraySize(); // one colorIndex row per corresponding polygon
        else finalLength = numberOfPolygons;
        String [][] saa2 = new String [finalLength][1];
        for (int i = 0; i < initialLength; i++)
        {
            saa2 [i][0] = saa [i][0];
        }
        String [][] saaCoordIndex = expandableListCoordIndex.getData();
        for (int i = initialLength; i < finalLength; i++)
        {
            int vertexCount = 3;                                               
            if ((saaCoordIndex != null) && (saaCoordIndex[i] != null))
            {
                vertexCount = saaCoordIndex[i][0].split("\\s+").length;
                if (saaCoordIndex[i][0].trim().endsWith("-1"))
                    vertexCount--;
            }
            saa2 [i][0] = new String();
            for (int j = 0; j < vertexCount; j++) // create example values from 0..(vertexCount-1)
            {
                saa2 [i][0] += Integer.toString(Math.min(j, (indexedFaceSet.getChildColorArraySize() - 1))) + " ";
            }
            saa2 [i][0] += "-1";
        }
        expandableListColorIndex.setData(saa2);
        expandableListTabbedPane.setSelectedIndex(1);
    }//GEN-LAST:event_colorIndexAppendButtonActionPerformed

    private void normalIndexAppendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_normalIndexAppendButtonActionPerformed
        String [][] saa = expandableListNormalIndex.getData();
        int  initialLength = saa.length;
        if ((initialLength == 1) && saa[0][0].isEmpty())
             initialLength = 0;
        int finalLength;
        if (normalPerVertexCB.isSelected())
             finalLength = indexedFaceSet.getChildCoordArraySize(); // one normalIndex row per corresponding polygon
        else finalLength = numberOfPolygons;
        String [][] saa2 = new String [finalLength][1];
        for (int i = 0; i < initialLength; i++)
        {
            saa2 [i][0] = saa [i][0];
        }
        String [][] saaCoordIndex = expandableListCoordIndex.getData();
        for (int i = initialLength; i < finalLength; i++)
        {
            int vertexCount = 3;                                               
            if ((saaCoordIndex != null) && (saaCoordIndex[i] != null))
            {
                vertexCount = saaCoordIndex[i][0].split("\\s+").length;
                if (saaCoordIndex[i][0].trim().endsWith("-1"))
                    vertexCount--;
            }
            saa2 [i][0] = new String();
            for (int j = 0; j < vertexCount; j++) // create example values from 0..(vertexCount-1)
            {
                saa2 [i][0] += Integer.toString(Math.min(j, (indexedFaceSet.getChildNormalArraySize() - 1))) + " ";
            }
            saa2 [i][0] += "-1";
        }
        expandableListNormalIndex.setData(saa2);
        expandableListTabbedPane.setSelectedIndex(2);
    }//GEN-LAST:event_normalIndexAppendButtonActionPerformed

    private void texCoordIndexAppendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_texCoordIndexAppendButtonActionPerformed
        String [][] saa = expandableListTexCoordIndex.getData();
        int  initialLength = saa.length;
        if ((initialLength == 1) && saa[0][0].isEmpty())
             initialLength = 0;
        int finalLength = expandableListCoordIndex.getData().length; // create one texCoordIndex row per corresponding polygon row
        String [][] saa2 = new String [finalLength][1];
        for (int i = 0; i < initialLength; i++)
        {
            saa2 [i][0] = saa [i][0];
        }        
        String [][] saaCoordIndex = expandableListCoordIndex.getData();
        for (int i = initialLength; i < finalLength; i++)
        {
            int vertexCount = 3;                                               
            if ((saaCoordIndex != null) && (saaCoordIndex[i] != null))
            {
                vertexCount = saaCoordIndex[i][0].split("\\s+").length;
                if (saaCoordIndex[i][0].trim().endsWith("-1"))
                    vertexCount--;
            }
            saa2 [i][0] = new String();
            for (int j = 0; j < vertexCount; j++) // create example values from 0..(vertexCount-1)
            {
                saa2 [i][0] += Integer.toString(Math.min(j, (indexedFaceSet.getChildTexCoordArraySize() - 1))) + " ";
            }
            saa2 [i][0] += "-1";
        }
        expandableListTexCoordIndex.setData(saa2);
        expandableListTabbedPane.setSelectedIndex(3);
    }//GEN-LAST:event_texCoordIndexAppendButtonActionPerformed

    private void normalPerVertexCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_normalPerVertexCBActionPerformed
        commonPanelUpdate (); // reset counts, tooltips
    }//GEN-LAST:event_normalPerVertexCBActionPerformed

    private void mouseClickedListener(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mouseClickedListener
        commonPanelUpdate (); // reset counts, tooltips
    }//GEN-LAST:event_mouseClickedListener
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox ccwCB;
    private javax.swing.JPanel checkBoxButtonLabelPanel;
    private javax.swing.JPanel checkBoxPanel;
    private javax.swing.JLabel colorArrayCountLabel;
    private javax.swing.JButton colorIndexAppendButton;
    private javax.swing.JCheckBox colorPerVertexCB;
    private javax.swing.JCheckBox convexCB;
    private javax.swing.JLabel coordArrayCountLabel;
    private javax.swing.JLabel creaseAngleLabel;
    private javax.swing.JTextField creaseAngleTF;
    private org.web3d.x3d.palette.items.ExpandableList expandableListColorIndex;
    private org.web3d.x3d.palette.items.ExpandableList expandableListCoordIndex;
    private org.web3d.x3d.palette.items.ExpandableList expandableListNormalIndex;
    private javax.swing.JTabbedPane expandableListTabbedPane;
    private org.web3d.x3d.palette.items.ExpandableList expandableListTexCoordIndex;
    private javax.swing.JLabel hintLabel;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparatorHorizontal1;
    private javax.swing.JSeparator jSeparatorHorizontal2;
    private javax.swing.JPanel nodeHintPanel;
    private javax.swing.JLabel normalArrayCountLabel;
    private javax.swing.JButton normalIndexAppendButton;
    private javax.swing.JCheckBox normalPerVertexCB;
    private javax.swing.JButton normalizeCreaseAngleButton;
    private javax.swing.JLabel polygonArrayCountLabel;
    private javax.swing.JCheckBox solidCB;
    private javax.swing.JLabel texCoordArrayCountLabel;
    private javax.swing.JButton texCoordIndexAppendButton;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_INDEXEDFACESET";
  }
  
  boolean cancelButtonSelected = false;

  private void checkIndices()
  {
      try
      {
        if (!cancelButtonSelected) 
        {
            checkCoordIndices();
            checkColorIndices();
            checkNormalIndices();
            checkTexCoordIndices();
        }
        cancelButtonSelected = false;
      }
      catch (Exception e)
      {
          System.out.println (e);
          Exceptions.printStackTrace(e);
      }
  }

  private void checkCoordIndices()
  {
      String message;
      int previousValue    = -2;
      boolean valueChanged = false;
      NotifyDescriptor descriptor;

      String[][] saa = expandableListCoordIndex.getData();
      String[]   sa  = new String[saa.length];
      int indexCount = 0;
      for (int i = 0; i < sa.length; i++)
      {
        sa[i] = saa[i][0].trim(); // saa is single-column square array
        if (sa[i].length() > 0)
            indexCount += sa[i].split("\\s+").length;
      }
      StringBuilder title = new StringBuilder("coordIndex");
      if (indexCount > 0)
      { 
        title.append(" (").append(expandableListCoordIndex.getRowCount()).append(" row");
        if (expandableListCoordIndex.getRowCount() != 1)
             title.append("s");
        title.append(", ").append(indexCount);
        if (indexCount != 1)
             title.append(" values)");
        else title.append(" value)");
      }
      expandableListTabbedPane.setTitleAt(0, title.toString());
      
      if ((indexedFaceSet.getChildCoordArraySize() > 0)  // no warning dialogs if child node not present, since editing likely in progress
          && (sa.length > 0)) // ensure values available to check
      {
          String [][] coordIndexArray = saa; // multiple rows, multiple entries, single value per entry
          for (int i=0; i < coordIndexArray.length; i++)
          {
            coordIndexArray[i] = sa[i].split("\\s+"); // split string of multiple values into string array
            for (int j=0; j < coordIndexArray[i].length; j++)
            {
              int value = new SFInt32(coordIndexArray[i][j]).getValue();
              if     (value >= indexedFaceSet.getChildCoordArraySize()) // index value too large
              {
                  message = "<html><center>Large index value found for coordIndex[<b>" + (i) + "</b>][<b>" + (j) + "</b>]=<b>" + String.valueOf(value)
                          + "</b>, which is greater than maximum index value of " + (indexedFaceSet.getChildCoordArraySize() - 1)
                          + ".<br/><br/>Convert value to <b>" + (indexedFaceSet.getChildCoordArraySize() - 1) + "</b>?";
                  descriptor = new NotifyDescriptor.Confirmation(
                          message, "IndexedFaceSet coordIndex value incorrect!", NotifyDescriptor.YES_NO_CANCEL_OPTION);
                  Object userChoice = DialogDisplayer.getDefault().notify(descriptor);
                  if      (userChoice == NotifyDescriptor.YES_OPTION)
                  {
                          value = indexedFaceSet.getChildCoordArraySize() - 1;
                          coordIndexArray[i][j] = Integer.toString(value);
                          valueChanged = true;
                  }
                  else if (userChoice == NotifyDescriptor.CANCEL_OPTION)
                  {
                      valueChanged = false;
                      cancelButtonSelected = true;
                      break; // no more checks
                  }
              }
              else if (value < -1) // index value illegal
              {
                    message = "<html><center>Incorrect value found for coordIndex[<b>" + (i) + "</b>][<b>" + (j) + "</b>]=<b>" + String.valueOf(value)
                              + "</b>, which is less than allowed sentinel value of -1"
                              + ".<br/><br/>Convert index value to <b>-1</b>?";
                  descriptor = new NotifyDescriptor.Confirmation(
                          message, "IndexedFaceSet coordIndex value incorrect!", NotifyDescriptor.YES_NO_CANCEL_OPTION);
                  Object userChoice = DialogDisplayer.getDefault().notify(descriptor);
                  if      (userChoice == NotifyDescriptor.YES_OPTION)
                  {
                          value = -1;
                          coordIndexArray[i][j] = "-1";
                          valueChanged = true;
                  }
                  else if (userChoice == NotifyDescriptor.CANCEL_OPTION)
                  {
                      valueChanged = false;
                      cancelButtonSelected = true;
                      break; // no more checks
                  }
              }
              else if ((j > 0) && (value == -1) && (previousValue == -1)) // doubled sentinel value
              {
                    message = "<html><center>Double <b>-1</b> sentinel values found for coordIndex[<b>" + (i) + "</b>][<b>" + (j-1) + "</b>] and coordIndex[<b>" + (i) + "</b>][<b>" + (j) + "</b>]"
                              + ".<br/><br/>Eliminate extra <b>-1</b> sentinel value?";
                  descriptor = new NotifyDescriptor.Confirmation(
                          message, "IndexedFaceSet coordIndex has empty sentinel value sequence", NotifyDescriptor.YES_NO_CANCEL_OPTION);
                  Object userChoice = DialogDisplayer.getDefault().notify(descriptor);
                  if (userChoice == NotifyDescriptor.YES_OPTION)
                  {
                          value = -2;
                          coordIndexArray[i][j] = "";
                          valueChanged = true;
                  }
                  else if (userChoice == NotifyDescriptor.CANCEL_OPTION)
                  {
                      valueChanged = false;
                      cancelButtonSelected = true;
                      break; // no more checks
                  }
              }
              else if ((j > 0) && (value == previousValue)) // double index
              {
                    message = "<html><center>Double index values found for coordIndex[<b>" + (i) + "</b>][<b>" + (j-1) + "</b>]=<b>" + String.valueOf(previousValue) + "</b> and coordIndex[<b>" + (i) + "</b>][<b>" + (j) + "</b>]=<b>" + String.valueOf(value)
                              + "</b>.<br/><br/>Eliminate extra <b>" + value + "</b> value?";
                  descriptor = new NotifyDescriptor.Confirmation(
                          message, "IndexedFaceSet coordIndex has duplicate adjacent values", NotifyDescriptor.YES_NO_CANCEL_OPTION);
                  Object userChoice = DialogDisplayer.getDefault().notify(descriptor);
                  if      (userChoice == NotifyDescriptor.YES_OPTION)
                  {
                          value = -2;
                          coordIndexArray[i][j] = "";
                          valueChanged = true;
                  }
                  else if (userChoice == NotifyDescriptor.CANCEL_OPTION)
                  {
                      valueChanged = false;
                      cancelButtonSelected = true;
                      break; // no more checks
                  }
              }
              if ((j > 0) && (coordIndexArray[i].length > 0))
                  previousValue = value; // save for next inner loop
            }
          }
          if (valueChanged)
          {
              String[] result = new String[sa.length];
              for (int i=0; i < coordIndexArray.length; i++)
              {
                result[i] = "";
                for (int j=0; j < coordIndexArray[i].length; j++)
                {
                  if (coordIndexArray[i][j].length() > 0)
                      result[i] += coordIndexArray[i][j] + " ";
                }
              }
              expandableListCoordIndex.setData(result);
          }
      }
  }

  private void checkColorIndices()
  {
      String message;
      NotifyDescriptor descriptor;
      boolean valueChanged = false;

      String[][] saa = expandableListColorIndex.getData();
      String[]   sa  = new String[saa.length];
      int indexCount = 0;
      for (int i = 0; i < sa.length; i++)
      {
        sa[i] = saa[i][0].trim(); // saa is single-column square array
        if (sa[i].length() > 0)
            indexCount += sa[i].split("\\s+").length;
      }
      StringBuilder title = new StringBuilder("colorIndex");
      if (indexCount > 0)
      { 
        title.append(" (").append(expandableListColorIndex.getRowCount()).append(" row");
        if (expandableListColorIndex.getRowCount() != 1)
             title.append("s");
        title.append(", ").append(indexCount);
        if (indexCount != 1)
             title.append(" values)");
        else title.append(" value)");
      }
      expandableListTabbedPane.setTitleAt(1, title.toString());
      
    String expectedColorArrayLength = "expected overall array length is ";
    if (colorPerVertexCB.isSelected())
    {
        expectedColorArrayLength += expandableListCoordIndex.getRowCount() + " (matching point sequences in coordIndex array)";
    }
    else 
    {
        expectedColorArrayLength += expandableListCoordIndex.getRowCount() + " (matching polygon count)";
    }
    if (indexedFaceSet.getChildColorArraySize() > 0)
         expandableListColorIndex.setTitle("colorIndex array values have allowed range [0.." + (indexedFaceSet.getChildColorArraySize() - 1) + "] where " + expectedColorArrayLength);
    else expandableListColorIndex.setTitle("colorIndex array values have allowed range [0..# Color values] where "                                        + expectedColorArrayLength);
    
    // check if number of color indices is mismatch to coordIndexArray
    if ( colorPerVertexCB.isSelected() && 
        (expandableListColorIndex.getRowCount() != expandableListCoordIndex.getRowCount()) && 
        (expandableListColorIndex.getRowCount() !=  0))
    {
          message = "<html><center>colorIndex array length (<b>" + (expandableListColorIndex.getRowCount()) + "</b>) needs to match coordIndex array length (<b>" + (expandableListCoordIndex.getRowCount()) + "</b>) "
                    + " since colorPerVertex='" + colorPerVertexCB.isSelected() + "'"
                    + ".<br/><br/>Note that colorIndex sentinel values (−1) also need to correspond to the same places as the coordIndex field.";
        descriptor = new NotifyDescriptor.Confirmation(
                message, "IndexedLineSet colorIndex size mismatch", NotifyDescriptor.PLAIN_MESSAGE);
        DialogDisplayer.getDefault().notify(descriptor);
    }
    
      if ((indexedFaceSet.getChildColorArraySize() > 0)  // no warning dialogs if child node not present, since editing likely in progress
       && (sa.length > 0)) // ensure values available to check
      {
          String [][] colorIndexArray = saa; // multiple rows, multiple entries, single value per entry
          for (int i=0; i < colorIndexArray.length; i++)
          {
            colorIndexArray[i] = sa[i].split("\\s+"); // split string of multiple values into string array
            for (int j=0; j < colorIndexArray[i].length; j++)
            {
              int value = new SFInt32(colorIndexArray[i][j]).getValue();
//          String [] colorIndexArray = sa; // single value per entry
//          for (int i=0; i < colorIndexArray.length; i++)
//          {
//              int value = new SFInt32(colorIndexArray[i]).getValue();
              if     (value >= indexedFaceSet.getChildColorArraySize()) // index value too large
              {
                  message = "<html><center>Large index value found for colorIndex[<b>" + i + "</b>]=<b>" + value
                          + "</b>, which is greater than maximum index value of " + (indexedFaceSet.getChildColorArraySize() - 1)
                          + ".<br/><br/>Convert value to <b>" + (indexedFaceSet.getChildColorArraySize() - 1) + "</b>?";
                  descriptor = new NotifyDescriptor.Confirmation(
                          message, "IndexedFaceSet colorIndex value incorrect!", NotifyDescriptor.YES_NO_CANCEL_OPTION);
                  Object userChoice = DialogDisplayer.getDefault().notify(descriptor);
                  if      (userChoice == NotifyDescriptor.YES_OPTION)
                  {
                          colorIndexArray[i][j] = Integer.toString(indexedFaceSet.getChildColorArraySize() - 1);
                          valueChanged = true;
                  }
                  else if (userChoice == NotifyDescriptor.CANCEL_OPTION)
                  {
                      valueChanged = false;
                      cancelButtonSelected = true;
                      break; // no more checks
                  }
              }
              else if (new SFInt32(colorIndexArray[i][j]).getValue() < -1) // check if index value illegal, -1 sentinel value allowed
              {
                    message = "<html><center>Incorrect value found for colorIndex[<b>" + i + "</b>]=<b>" + value
                              + "</b>, which is less than allowed index value of -1"
                              + ".<br/><br/>Convert value to <b>0</b>?";
                  descriptor = new NotifyDescriptor.Confirmation(
                          message, "IndexedFaceSet colorIndex value incorrect!", NotifyDescriptor.YES_NO_CANCEL_OPTION);
                  Object userChoice = DialogDisplayer.getDefault().notify(descriptor);
                  if      (userChoice == NotifyDescriptor.YES_OPTION)
                  {
                          colorIndexArray[i][j] = "0";
                          valueChanged = true;
                  }
                  else if (userChoice == NotifyDescriptor.CANCEL_OPTION)
                  {
                      valueChanged = false;
                      cancelButtonSelected = true;
                      break; // no more checks
                  }
              }
            }
          }
          if (valueChanged)
          {
              expandableListColorIndex.setData(colorIndexArray);
          }
            // check if number of color indices higher than number of polygons
            if ((colorIndexArray.length > numberOfPolygons) && !colorPerVertexCB.isSelected()) // colorPerVertex='false'
            {
                  message = "<html><center>Found excessive number of colorIndex values (<b>" + (colorIndexArray.length) + "</b>) <br />"
                            + " since colorPerVertex='" + colorPerVertexCB.isSelected() + "' and only <b>" + numberOfPolygons + "</b> polygons are defined"
                            + ".<br/><br/>Suggestions: remove some colorIndex values, or add more polygons in coordIndex array.";
                descriptor = new NotifyDescriptor.Confirmation(
                        message, "IndexedFaceSet colorIndex has too many values", NotifyDescriptor.PLAIN_MESSAGE);
                DialogDisplayer.getDefault().notify(descriptor);
            }
            // check if number of color indices higher than number of polygons
            else if ((colorIndexArray.length < numberOfPolygons) && !colorPerVertexCB.isSelected()) // colorPerVertex='false'
            {
                  message = "<html><center>Found insufficient number of colorIndex values (<b>" + (colorIndexArray.length) + "</b>)"
                            + " since colorPerVertex='" + colorPerVertexCB.isSelected() + "' and <b>" + numberOfPolygons + "</b> polygons are defined"
                            + ".<br/><br/>Suggestions: add more colorIndex values, or remove some polygons in coordIndex array.";
                descriptor = new NotifyDescriptor.Confirmation(
                        message, "IndexedFaceSet colorIndex has too few values", NotifyDescriptor.PLAIN_MESSAGE);
                DialogDisplayer.getDefault().notify(descriptor);
            }
            // check if number of color indices higher than number of vertices
            else if ((colorIndexArray.length > indexedFaceSet.getChildCoordArraySize()) && colorPerVertexCB.isSelected()) // colorPerVertex='true'
            {
                  message = "<html><center>Found excessive number of colorIndex values (<b>" + (colorIndexArray.length) + "</b>) <br />"
                            + " since colorPerVertex='" + colorPerVertexCB.isSelected() + "' and only <b>" + indexedFaceSet.getChildCoordArraySize() + "</b> different <b>Coordinate</b> points are defined"
                            + ".<br/><br/>Suggestions: remove some colorIndex values, or add more points to the <b>Coordinate</b> node.";
                descriptor = new NotifyDescriptor.Confirmation(
                        message, "IndexedFaceSet colorIndex has too many values", NotifyDescriptor.PLAIN_MESSAGE);
                DialogDisplayer.getDefault().notify(descriptor);
            }
      }
  }

  private void checkNormalIndices()
  {
      String message;
      NotifyDescriptor descriptor;
      boolean valueChanged = false;

      String[][] saa = expandableListNormalIndex.getData();
      String[]   sa  = new String[saa.length];
      int indexCount = 0;
      for (int i = 0; i < sa.length; i++)
      {
        sa[i] = saa[i][0].trim(); // saa is single-column square array
        if (sa[i].length() > 0)
            indexCount += sa[i].split("\\s+").length;
      }
      StringBuilder title = new StringBuilder("normalIndex");
      if (indexCount > 0)
      { 
        title.append(" (").append(expandableListNormalIndex.getRowCount()).append(" row");
        if (expandableListNormalIndex.getRowCount() != 1)
             title.append("s");
        title.append(", ").append(indexCount);
        if (indexCount != 1)
             title.append(" values)");
        else title.append(" value)");
      }
      expandableListTabbedPane.setTitleAt(2, title.toString());
    
      if ((indexedFaceSet.getChildNormalArraySize() > 0)  // no warning dialogs if child node not present, since editing likely in progress
       && (sa.length > 0)) // ensure values available to check
      {
          String [][] normalIndexArray = saa; // multiple rows, multiple entries, single value per entry
          for (int i=0; i < normalIndexArray.length; i++)
          {
            normalIndexArray[i] = sa[i].split("\\s+"); // split string of multiple values into string array
            for (int j=0; j < normalIndexArray[i].length; j++)
            {
              int value = new SFInt32(normalIndexArray[i][j]).getValue();
//          String [] normalIndexArray = sa; // single value per entry
//          for (int i=0; i < normalIndexArray.length; i++)
//          {
//              int value = new SFInt32(normalIndexArray[i]).getValue();
              if     (value >= indexedFaceSet.getChildNormalArraySize()) // index value too large
              {
                  message = "<html><center>Large index value found for normalIndex[<b>" + i + "</b>]=<b>" + value
                          + "</b>, which is greater than maximum index value of " + (indexedFaceSet.getChildNormalArraySize() - 1)
                          + ".<br/><br/>Convert value to <b>" + (indexedFaceSet.getChildNormalArraySize() - 1) + "</b>?";
                  descriptor = new NotifyDescriptor.Confirmation(
                          message, "IndexedFaceSet normalIndex value incorrect!", NotifyDescriptor.YES_NO_CANCEL_OPTION);
                  Object userChoice = DialogDisplayer.getDefault().notify(descriptor);
                  if      (userChoice == NotifyDescriptor.YES_OPTION)
                  {
                          normalIndexArray[i][j] = Integer.toString(indexedFaceSet.getChildNormalArraySize() - 1);
                          valueChanged = true;
                  }
                  else if (userChoice == NotifyDescriptor.CANCEL_OPTION)
                  {
                      valueChanged = false;
                      cancelButtonSelected = true;
                      break; // no more checks
                  }
              }
              else if (new SFInt32(normalIndexArray[i][j]).getValue() < 0) // index value illegal
              {
                    message = "<html><center>Incorrect value found for normalIndex[<b>" + i + "</b>]=<b>" + value
                              + "</b>, which is less than allowed index value of 0"
                              + ".<br/><br/>Convert value to <b>0</b>?";
                  descriptor = new NotifyDescriptor.Confirmation(
                          message, "IndexedFaceSet normalIndex value incorrect!", NotifyDescriptor.YES_NO_CANCEL_OPTION);
                  Object userChoice = DialogDisplayer.getDefault().notify(descriptor);
                  if      (userChoice == NotifyDescriptor.YES_OPTION)
                  {
                          normalIndexArray[i][j] = "0";
                          valueChanged = true;
                  }
                  else if (userChoice == NotifyDescriptor.CANCEL_OPTION)
                  {
                      valueChanged = false;
                      cancelButtonSelected = true;
                      break; // no more checks
                  }
              }
            }
          }
          if (valueChanged)
          {
              expandableListNormalIndex.setData(normalIndexArray);
          }
            // check if number of normal indices higher than number of polygons
            if ((normalIndexArray.length > numberOfPolygons) && !normalPerVertexCB.isSelected()) // normalPerVertex='false'
            {
                  message = "<html><center>Found excessive number of normalIndex values (<b>" + (normalIndexArray.length) + "</b>) <br />"
                            + " since normalPerVertex='" + normalPerVertexCB.isSelected() + "' and only <b>" + numberOfPolygons + "</b> polygons are defined"
                            + ".<br/><br/>Suggestions: remove some normalIndex values, or add more polygons in coordIndex array.";
                descriptor = new NotifyDescriptor.Confirmation(
                        message, "IndexedFaceSet normalIndex has too many values", NotifyDescriptor.PLAIN_MESSAGE);
                DialogDisplayer.getDefault().notify(descriptor);
            }
            // check if number of normal indices higher than number of polygons
            else if ((normalIndexArray.length < numberOfPolygons) && !normalPerVertexCB.isSelected()) // normalPerVertex='false'
            {
                  message = "<html><center>Found insufficient number of normalIndex values (<b>" + (normalIndexArray.length) + "</b>)"
                            + " since normalPerVertex='" + normalPerVertexCB.isSelected() + "' and <b>" + numberOfPolygons + "</b> polygons are defined"
                            + ".<br/><br/>Suggestions: add more normalIndex values, or remove some polygons in coordIndex array.";
                descriptor = new NotifyDescriptor.Confirmation(
                        message, "IndexedFaceSet normalIndex has too few values", NotifyDescriptor.PLAIN_MESSAGE);
                DialogDisplayer.getDefault().notify(descriptor);
            }
            // check if number of normal indices higher than number of vertices
            else if ((normalIndexArray.length > indexedFaceSet.getChildCoordArraySize()) && normalPerVertexCB.isSelected()) // normalPerVertex='true'
            {
                  message = "<html><center>Found excessive number of normalIndex values (<b>" + (normalIndexArray.length) + "</b>) <br />"
                            + " since normalPerVertex='" + normalPerVertexCB.isSelected() + "' and only <b>" + indexedFaceSet.getChildCoordArraySize() + "</b> different <b>Coordinate</b> points are defined"
                            + ".<br/><br/>Suggestions: remove some normalIndex values, or add more points to the <b>Coordinate</b> node.";
                descriptor = new NotifyDescriptor.Confirmation(
                        message, "IndexedFaceSet normalIndex has too many values", NotifyDescriptor.PLAIN_MESSAGE);
                DialogDisplayer.getDefault().notify(descriptor);
            }
      }
  }

  private void checkTexCoordIndices()
  {
      String message;
      NotifyDescriptor descriptor;
      boolean valueChanged = false;

      String[][] saa = expandableListTexCoordIndex.getData();
      String[]   sa  = new String[saa.length];
      int indexCount = 0;
      for (int i = 0; i < sa.length; i++)
      {
        sa[i] = saa[i][0].trim(); // saa is single-column square array
        if (sa[i].length() > 0)
            indexCount += sa[i].split("\\s+").length;
      }
      StringBuilder title = new StringBuilder("texCoordIndex");
      if (indexCount > 0)
      { 
        title.append(" (").append(expandableListTexCoordIndex.getRowCount()).append(" row");
        if (expandableListTexCoordIndex.getRowCount() != 1)
             title.append("s");
        title.append(", ").append(indexCount);
        if (indexCount != 1)
             title.append(" values)");
        else title.append(" value)");
      }
      expandableListTabbedPane.setTitleAt(3, title.toString());
//      for (int i = 0; i < sa.length; i++)
//      {
//        sa[i] = saa[i][0]; // saa is single-column square array
//      }
//      expandableListTabbedPane.setTitleAt(3, "texCoordIndex (" + expandableListTexCoordIndex.getRowCount() + ")");
    
      if ((indexedFaceSet.getChildTexCoordArraySize() > 0)  // no warning dialogs if child node not present, since editing likely in progress
       && (sa.length > 0)) // ensure values available to check
      {
          String [][] texcoordIndexArray = saa; // multiple rows, multiple entries, single value per entry
          for (int i=0; i < texcoordIndexArray.length; i++)
          {
            texcoordIndexArray[i] = sa[i].split("\\s+"); // split string of multiple values into string array
            for (int j=0; j < texcoordIndexArray[i].length; j++)
            {
              int value = new SFInt32(texcoordIndexArray[i][j]).getValue();
//          String [] texcoordIndexArray = sa; // single value per entry
//          for (int i=0; i < texcoordIndexArray.length; i++)
//          {
//              int value = new SFInt32(texcoordIndexArray[i]).getValue();
              if     (value >= indexedFaceSet.getChildTexCoordArraySize()) // index value too large
              {
                  message = "<html><center>Large index value found for texcoordIndex[<b>" + i + "</b>]=<b>" + value
                          + "</b>, which is greater than maximum index value of " + (indexedFaceSet.getChildTexCoordArraySize() - 1)
                          + ".<br/><br/>Convert value to <b>" + (indexedFaceSet.getChildTexCoordArraySize() - 1) + "</b>?";
                  descriptor = new NotifyDescriptor.Confirmation(
                          message, "IndexedFaceSet texcoordIndex value incorrect!", NotifyDescriptor.YES_NO_CANCEL_OPTION);
                  Object userChoice = DialogDisplayer.getDefault().notify(descriptor);
                  if      (userChoice == NotifyDescriptor.YES_OPTION)
                  {
                          texcoordIndexArray[i][j] = Integer.toString(indexedFaceSet.getChildTexCoordArraySize() - 1);
                          valueChanged = true;
                  }
                  else if (userChoice == NotifyDescriptor.CANCEL_OPTION)
                  {
                      valueChanged = false;
                      cancelButtonSelected = true;
                      break; // no more checks
                  }
              }
              else if (new SFInt32(texcoordIndexArray[i][j]).getValue() < -1) // index value illegal
              {
                    message = "<html><center>Incorrect value found for texcoordIndex[<b>" + i + "</b>]=<b>" + value
                              + "</b>, which is less than allowed index value of 0 (or -1 sentinel value)"
                              + ".<br/><br/>Convert value to <b>0</b>?";
                  descriptor = new NotifyDescriptor.Confirmation(
                          message, "IndexedFaceSet texcoordIndex value incorrect!", NotifyDescriptor.YES_NO_CANCEL_OPTION);
                  Object userChoice = DialogDisplayer.getDefault().notify(descriptor);
                  if      (userChoice == NotifyDescriptor.YES_OPTION)
                  {
                          texcoordIndexArray[i][j] = "0";
                          valueChanged = true;
                  }
                  else if (userChoice == NotifyDescriptor.CANCEL_OPTION)
                  {
                      valueChanged = false;
                      cancelButtonSelected = true;
                      break; // no more checks
                  }
              }
            }
          }
          if (valueChanged)
          {
              expandableListTexCoordIndex.setData(texcoordIndexArray);
          }
          // no texcoordPerVertex and so there is little to check
      }
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
            message = "<html><center>Large value found for <b>creaseAngle</b> angle, which is a radians value.<br/><br/>Convert <b>" + angle + " degrees</b> to <b>" +
                    radiansFormat.format((angle % 360.0) * Math.PI / 180.0) + " radians</b>";
            if (precedesNormalization)
                 message += " before normalization?";
            else message += "?";
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  message, "X3D angles are in radians", NotifyDescriptor.YES_NO_OPTION);
          Object userChoice = DialogDisplayer.getDefault().notify(descriptor);
          if (userChoice == NotifyDescriptor.YES_OPTION)
          {
              angle = (angle % 360.0) * Math.PI / 180.0;
              creaseAngleTF.setText(radiansFormat.format(angle));
              creaseAngleTF.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");
          }
        }
        if (angle < 0.0)
        {
            String message = "<html><center>Negative value found for <b>creaseAngle</b>.<br/><br/>Convert <b>" + angle + " radians</b> to <b>" +
                    (-angle) + " radians</b>";
            if (precedesNormalization)
                 message += " before normalization?";
            else message += "?";
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  message, "creaseAngle must be nonnegative", NotifyDescriptor.YES_NO_OPTION);
          Object userChoice = DialogDisplayer.getDefault().notify(descriptor);
          if (userChoice == NotifyDescriptor.YES_OPTION)
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
    checkIndices();
    unLoadDEFUSE();
    
    indexedFaceSet.setCcw(ccwCB.isSelected());
    indexedFaceSet.setConvex(convexCB.isSelected());
    indexedFaceSet.setSolid(solidCB.isSelected());
    indexedFaceSet.setCreaseAngle(creaseAngleTF.getText().trim());
    
    indexedFaceSet.setColorPerVertex(colorPerVertexCB.isSelected());
    indexedFaceSet.setNormalPerVertex(normalPerVertexCB.isSelected());
    
    indexedFaceSet.setInsertCommasCoordIndex    (expandableListCoordIndex.isInsertCommasSet());
    indexedFaceSet.setInsertLineBreaksCoordIndex(expandableListCoordIndex.isInsertLineBreaksSet());
    String[][] saa = expandableListCoordIndex.getData();
    String[]   sa  = new String[saa.length];
    for (int i = 0; i < sa.length; i++)
    {
        sa[i] = saa[i][0]; // saa is single-column square array
    }
    indexedFaceSet.setCoordIndex(
            INDEXEDFACESET.concatStringArrayCommasLineBreaks(sa,
              expandableListCoordIndex.isInsertCommasSet(), 
              expandableListCoordIndex.isInsertLineBreaksSet()));
    
    indexedFaceSet.setInsertCommasColorIndex    (expandableListColorIndex.isInsertCommasSet());
    indexedFaceSet.setInsertLineBreaksColorIndex(expandableListColorIndex.isInsertLineBreaksSet());
    saa = expandableListColorIndex.getData();
    sa  = new String[saa.length];
    for (int i = 0; i < sa.length; i++)
    {
        sa[i] = saa[i][0]; // saa is single-column square array
    }
    indexedFaceSet.setColorIndex(
            INDEXEDFACESET.concatStringArrayCommasLineBreaks(sa,
              expandableListColorIndex.isInsertCommasSet(), 
              expandableListCoordIndex.isInsertLineBreaksSet()));
    
    indexedFaceSet.setInsertCommasNormalIndex    (expandableListNormalIndex.isInsertCommasSet());
    indexedFaceSet.setInsertLineBreaksNormalIndex(expandableListNormalIndex.isInsertLineBreaksSet());
    saa = expandableListNormalIndex.getData();
    sa  = new String[saa.length];
    for (int i = 0; i < sa.length; i++)
    {
        sa[i] = saa[i][0]; // saa is single-column square array
    }
    indexedFaceSet.setNormalIndex(
            INDEXEDFACESET.concatStringArrayCommasLineBreaks(sa,
              expandableListNormalIndex.isInsertCommasSet(), 
              expandableListCoordIndex.isInsertLineBreaksSet()));
    
    indexedFaceSet.setInsertCommasTexCoordIndex    (expandableListTexCoordIndex.isInsertCommasSet());
    indexedFaceSet.setInsertLineBreaksTexCoordIndex(expandableListTexCoordIndex.isInsertLineBreaksSet());
    saa = expandableListTexCoordIndex.getData();
    sa  = new String[saa.length];
    for (int i = 0; i < sa.length; i++)
    {
        sa[i] = saa[i][0]; // saa is single-column square array
    }
    indexedFaceSet.setTexCoordIndex(
            INDEXEDFACESET.concatStringArrayCommasLineBreaks(sa,
              expandableListTexCoordIndex.isInsertCommasSet(), 
              expandableListTexCoordIndex.isInsertLineBreaksSet()));
  
   }  
}
