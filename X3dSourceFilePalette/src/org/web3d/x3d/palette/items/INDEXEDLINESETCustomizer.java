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

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.text.JTextComponent;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * INDEXEDLINESETCustomizer.java
 * Created on July 12, 2007, 3:05 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class INDEXEDLINESETCustomizer extends BaseCustomizer
{
  private final INDEXEDLINESET indexedLineSet;
  private final JTextComponent target;
  
  private int numberOfPolylines = 0;

//  private final String[]  DEFAULT_INDEX_ARRAY = { "" };

  public INDEXEDLINESETCustomizer(INDEXEDLINESET indexedLineSet, JTextComponent target)
  {
    super(indexedLineSet);
    this.indexedLineSet = indexedLineSet;
    this.target = target;
    
    HelpCtx.setHelpIDString(this, "INDEXEDLINESET_ELEM_HELPID");

    initComponents();
    
    commonInitialization ();
  }
  private void commonInitialization ()
  {
    colorPerVertexCB.setSelected(indexedLineSet.isColorPerVertex());

    // =====================
    if (indexedLineSet.getChildCoordArraySize() > 0)
         expandableListCoordIndex.setTitle("coordIndex array, allowed index range is [0.." + (indexedLineSet.getChildCoordArraySize() - 1) + "], polylines end with sentinel value-1");
    else expandableListCoordIndex.setTitle("coordIndex array, allowed index range is [0..# Cooordinate points], polylines end with sentinel value-1");
    expandableListCoordIndex.setColumnTitles(new String[]{"#","coordIndex array for each polyline"});
    expandableListCoordIndex.setColumnToolTips(new String[]{"index","index list"});
    expandableListCoordIndex.setHeaderTooltip ("Coordinate indices for point connectivity");
    expandableListCoordIndex.doIndexInFirstColumn(true);
    expandableListCoordIndex.setRedColumn(-1); // 0 is index, -1 means no color editing
    expandableListCoordIndex.setColumnWidthAndResizeStrategy(false, 75);
    expandableListCoordIndex.setDefaultCellValue("0 0 0 -1");
    
    expandableListCoordIndex.setToolTipText  ("coordIndex array of coordinate indices");
    expandableListCoordIndex.setHeaderTooltip("coordIndex array of coordinate indices");
    expandableListCoordIndex.setCellEditPanelVisible      (false);
    expandableListCoordIndex.setInsertPanelVisible        (true);
    expandableListCoordIndex.setGeneratePointsPanelVisible(false);

    String[] sa = INDEXEDLINESET.parseMFStringIntoStringArray(indexedLineSet.getCoordIndex(), false, true, "-1"); // may be 0-length
    if (sa.length != 0)
    {
        expandableListCoordIndex.setData(sa);
        expandableListCoordIndex.setShowAppendCommasLineBreaks(true);
        expandableListCoordIndex.setInsertCommas    (indexedLineSet.isInsertCommasCoordIndex());
        expandableListCoordIndex.setInsertLineBreaks(indexedLineSet.isInsertLineBreaksCoordIndex());
    }

    numberOfPolylines = expandableListCoordIndex.getData().length;
    if (numberOfPolylines != 1)
         polylineArrayCountLabel.setText("<html>(<b>" + numberOfPolylines + "</b> polylines defined by coordIndex array)");
    else polylineArrayCountLabel.setText("<html>(<b>" + numberOfPolylines + "</b> polyline defined by coordIndex array)");
    
    coordArrayCountLabel.setText("<html>(<b>" + indexedLineSet.getChildCoordArraySize() + "</b> contained <b>Coordinate</b> points)");
    if (indexedLineSet.getChildCoordArraySize() > 0)
        coordArrayCountLabel.setToolTipText("allowed coordIndex values are -1,0.."       + (indexedLineSet.getChildCoordArraySize()    - 1));
    
    // =====================
    expandableListColorIndex.setColumnToolTips(new String[]{"index","index list"});
    expandableListColorIndex.doIndexInFirstColumn(true);
    expandableListColorIndex.setRedColumn(-1); // 0 is index, -1 means no color editing
    expandableListColorIndex.setColumnWidthAndResizeStrategy(false, 75);
    expandableListCoordIndex.setDefaultCellValue("0");
    
    expandableListColorIndex.setToolTipText  ("colorIndex array of color indices");
    expandableListColorIndex.setHeaderTooltip("colorIndex array of color indices");
    expandableListColorIndex.setCellEditPanelVisible      (false);
    expandableListColorIndex.setInsertPanelVisible        (true);
    expandableListColorIndex.setGeneratePointsPanelVisible(false);
        
    sa = INDEXEDLINESET.parseMFStringIntoStringArray(indexedLineSet.getColorIndex(), false, true, "-1"); // may be 0-length
    if (sa.length != 0)
    {
        expandableListColorIndex.setData(sa);
        expandableListColorIndex.setShowAppendCommasLineBreaks(true);
        expandableListColorIndex.setInsertCommas    (indexedLineSet.isInsertCommasColorIndex());
        expandableListColorIndex.setInsertLineBreaks(indexedLineSet.isInsertLineBreaksColorIndex());
    }

    colorArrayCountLabel.setText("<html>(<b>" + indexedLineSet.getChildColorArraySize() + "</b> contained color values)");
    if (indexedLineSet.getChildColorArraySize() > 0)
        colorArrayCountLabel.setToolTipText("allowed colorIndex values are -1,0.." + (indexedLineSet.getChildColorArraySize() - 1));
    
    commonPanelUpdate ();
  }
  
  private void commonPanelUpdate ()
  {
    String expectedColorArrayLength = "expected array length is ";
    
    if (colorPerVertexCB.isSelected())
    {
        expectedColorArrayLength += expandableListCoordIndex.getRowCount() + " (matching point sequences in coordIndex array)";
        expandableListColorIndex.setColumnTitles(new String[]{"#","colorIndex array for each vertex"});
        expandableListColorIndex.setHeaderTooltip ("Color indices for vertex colors");
    }
    else
    {
        expectedColorArrayLength += expandableListCoordIndex.getRowCount() + "(matching polyline count)";
        expandableListColorIndex.setColumnTitles(new String[]{"#","colorIndex array for each polyline"});
        expandableListColorIndex.setHeaderTooltip ("Color indices for each polyline color");
    }
    if (indexedLineSet.getChildColorArraySize() > 0)
         expandableListColorIndex.setTitle("colorIndex array, allowed index range is [0.." + (indexedLineSet.getChildColorArraySize() - 1) + "], " + expectedColorArrayLength);
    else expandableListColorIndex.setTitle("colorIndex array, allowed index range is [0..# Color values], "                                        + expectedColorArrayLength);
    
    // =====================
    // button to add indices
    
    if ((expandableListColorIndex.getRowCount()    < expandableListCoordIndex.getRowCount()) && (indexedLineSet.getChildColorArraySize() > 0))
         colorIndexAppendButton.setEnabled(true);
    else colorIndexAppendButton.setEnabled(false);
    
    // =====================

    checkIndices();       // this statement must follow initial setting of TA TextArea index values
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
        polylineArrayCountLabel = new JLabel();
        coordArrayCountLabel = new JLabel();
        colorPerVertexCB = new JCheckBox();
        colorArrayCountLabel = new JLabel();
        jSeparator1 = new JSeparator();
        expandableListTabbedPane = new JTabbedPane();
        expandableListCoordIndex = new ExpandableList();
        expandableListColorIndex = new ExpandableList();
        nodeHintPanel = new JPanel();
        hintLabel = new JLabel();
        colorIndexAppendButton = new JButton();

        setLayout(new GridBagLayout());
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        add(dEFUSEpanel1, gridBagConstraints);

        polylineArrayCountLabel.setText("(polyline count)");
        polylineArrayCountLabel.setVerticalAlignment(SwingConstants.TOP);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new Insets(3, 30, 3, 3);
        add(polylineArrayCountLabel, gridBagConstraints);

        coordArrayCountLabel.setText("(coordinate count)");
        coordArrayCountLabel.setVerticalAlignment(SwingConstants.TOP);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new Insets(3, 30, 3, 3);
        add(coordArrayCountLabel, gridBagConstraints);

        colorPerVertexCB.setText("colorPerVertex");
        colorPerVertexCB.setMargin(new Insets(0, 0, 0, 0));
        colorPerVertexCB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                colorPerVertexCBActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        add(colorPerVertexCB, gridBagConstraints);

        colorArrayCountLabel.setText("(color count)");
        colorArrayCountLabel.setVerticalAlignment(SwingConstants.TOP);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        add(colorArrayCountLabel, gridBagConstraints);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(3, 0, 3, 0);
        add(jSeparator1, gridBagConstraints);

        expandableListTabbedPane.setToolTipText("select editor");
        expandableListTabbedPane.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                mouseClickedListener(evt);
            }
        });

        expandableListCoordIndex.setPreferredSize(new Dimension(632, 250));
        expandableListCoordIndex.addPropertyChangeListener(new PropertyChangeListener() {
            public void propertyChange(PropertyChangeEvent evt) {
                expandableListCoordIndexPropertyChange(evt);
            }
        });
        expandableListTabbedPane.addTab("coordIndex", expandableListCoordIndex);

        expandableListColorIndex.setPreferredSize(new Dimension(632, 250));
        expandableListTabbedPane.addTab("colorIndex", expandableListColorIndex);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 1.0;
        add(expandableListTabbedPane, gridBagConstraints);

        nodeHintPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        nodeHintPanel.setLayout(new GridBagLayout());

        hintLabel.setHorizontalAlignment(SwingConstants.CENTER);
        hintLabel.setText("<html><p align='center'><b>IndexedLineSet</b> contains a <b>Coordinate|CoordinateDouble</b> node<br/>plus an optional <b>Color|ColorRGBA</b> node.<br/> Hint: line color can also be controlled by accompanying <b>Appearance</b>/<b>Material</b> emissiveColor.</p>");
        hintLabel.setToolTipText("close this panel to add children nodes");
        hintLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        nodeHintPanel.add(hintLabel, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        add(nodeHintPanel, gridBagConstraints);

        colorIndexAppendButton.setText("add indices");
        colorIndexAppendButton.setToolTipText("append colorIndex indices");
        colorIndexAppendButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                colorIndexAppendButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new Insets(3, 3, 10, 3);
        add(colorIndexAppendButton, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void colorPerVertexCBActionPerformed(ActionEvent evt) {//GEN-FIRST:event_colorPerVertexCBActionPerformed
        checkColorIndices();
        if (colorPerVertexCB.isSelected())
        {
            expandableListColorIndex.setColumnTitles(new String[]{"#","colorIndex array for each vertex"});
            expandableListColorIndex.setHeaderTooltip ("Color indices for vertex colors");
        }
        else 
        {
            expandableListColorIndex.setColumnTitles(new String[]{"#","colorIndex array for each polyline"});
            expandableListColorIndex.setHeaderTooltip ("Color indices for polyline colors");
        }
        expandableListColorIndex.doIndexInFirstColumn(true);
        
        commonPanelUpdate ();
    }//GEN-LAST:event_colorPerVertexCBActionPerformed

    private void expandableListCoordIndexPropertyChange(PropertyChangeEvent evt) {//GEN-FIRST:event_expandableListCoordIndexPropertyChange
        numberOfPolylines = expandableListCoordIndex.getData().length;
        polylineArrayCountLabel.setText("<html>(<b>" + numberOfPolylines + "</b> polylines defined by coordIndex)");
        expandableListCoordIndex.doIndexInFirstColumn(true);
        expandableListCoordIndex.doIndexInFirstColumn(true);
    }//GEN-LAST:event_expandableListCoordIndexPropertyChange

    private void colorIndexAppendButtonActionPerformed(ActionEvent evt) {//GEN-FIRST:event_colorIndexAppendButtonActionPerformed
        String [][] saa = expandableListColorIndex.getData();
        int  initialLength = saa.length;
        if ((initialLength == 1) && saa[0][0].isEmpty())
             initialLength = 0;
        int finalLength;
        if (colorPerVertexCB.isSelected())
             finalLength = indexedLineSet.getChildCoordArraySize(); // one colorIndex row per corresponding polygon
        else finalLength = numberOfPolylines;
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
                saa2 [i][0] += Integer.toString(Math.min(j, (indexedLineSet.getChildColorArraySize() - 1))) + " ";
            }
            saa2 [i][0] += "-1";
        }
        expandableListColorIndex.setData(saa2);
        expandableListTabbedPane.setSelectedIndex(1);
    }//GEN-LAST:event_colorIndexAppendButtonActionPerformed

    private void mouseClickedListener(MouseEvent evt) {//GEN-FIRST:event_mouseClickedListener
        commonPanelUpdate (); // reset counts, tooltips
    }//GEN-LAST:event_mouseClickedListener
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JLabel colorArrayCountLabel;
    private JButton colorIndexAppendButton;
    private JCheckBox colorPerVertexCB;
    private JLabel coordArrayCountLabel;
    private ExpandableList expandableListColorIndex;
    private ExpandableList expandableListCoordIndex;
    private JTabbedPane expandableListTabbedPane;
    private JLabel hintLabel;
    private JSeparator jSeparator1;
    private JPanel nodeHintPanel;
    private JLabel polylineArrayCountLabel;
    // End of variables declaration//GEN-END:variables

  boolean cancelButtonSelected = false;

  private void checkIndices()
  {
      try {
        {
            checkCoordIndices();
            checkColorIndices();
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
      for (int i = 0; i < sa.length; i++)
      {
        sa[i] = saa[i][0]; // saa is single-column square array
      }
      expandableListTabbedPane.setTitleAt(0, "coordIndex (" + expandableListCoordIndex.getRowCount() + ")");
      
      if ((indexedLineSet.getChildCoordArraySize() > 0)  // no warning dialogs if child node not present, since editing likely in progress
          && (sa.length > 0)) // ensure values available to check
      {
          String [][] coordIndexArray = saa; // multiple rows, multiple entries, single value per entry
          for (int i=0; i < coordIndexArray.length; i++)
          {
            coordIndexArray[i] = sa[i].split("\\s+"); // split string of multiple values into string array
            for (int j=0; j < coordIndexArray[i].length; j++)
            {
              int value = new SFInt32(coordIndexArray[i][j]).getValue();
              if     (value >= indexedLineSet.getChildCoordArraySize()) // index value too large
              {
                  message = "<html><center>Large index value found for coordIndex[<b>" + (i) + "</b>][<b>" + (j) + "</b>]=<b>" + String.valueOf(value)
                          + "</b>, which is greater than maximum index value of " + (indexedLineSet.getChildCoordArraySize() - 1)
                          + ".<br/><br/>Convert value to <b>" + (indexedLineSet.getChildCoordArraySize() - 1) + "</b>?";
                  descriptor = new NotifyDescriptor.Confirmation(
                          message, "IndexedLineSet coordIndex value incorrect!", NotifyDescriptor.YES_NO_CANCEL_OPTION);
                  Object userChoice = DialogDisplayer.getDefault().notify(descriptor);
                  if      (userChoice == NotifyDescriptor.YES_OPTION)
                  {
                          value = indexedLineSet.getChildCoordArraySize() - 1;
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
                          message, "IndexedLineSet coordIndex value incorrect!", NotifyDescriptor.YES_NO_CANCEL_OPTION);
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
              else if ((j > 0) && (value == -1) && (previousValue == -1)) // double sentinel
              {
                    message = "<html><center>Double <b>-1</b> sentinel values found for coordIndex[<b>" + (i) + "</b>][<b>" + (j-1) + "</b>] and coordIndex[<b>" + (i) + "</b>][<b>" + (j) + "</b>]"
                              + ".<br/><br/>Eliminate extra <b>-1</b> sentinel?";
                  descriptor = new NotifyDescriptor.Confirmation(
                          message, "IndexedLineSet coordIndex has empty sentinel valuesequence", NotifyDescriptor.YES_NO_CANCEL_OPTION);
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
                          message, "IndexedLineSet coordIndex has duplicate adjacent values", NotifyDescriptor.YES_NO_CANCEL_OPTION);
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
//              coordIndexTA.setText(indexedLineSet.formatIndexArray(result, true)); TODO fix
              expandableListCoordIndex.setData(result);
          }
      }
  }

  private void checkColorIndices()
  {
      String message;
      boolean valueChanged = false;
      NotifyDescriptor descriptor;
      
    String expectedColorArrayLength = "expected array length is ";
    if (colorPerVertexCB.isSelected())
    {
        expectedColorArrayLength += expandableListCoordIndex.getRowCount() + " (matching point sequences in coordIndex array)";
    }
    else 
    {
        expectedColorArrayLength += expandableListCoordIndex.getRowCount() + " (matching polyline count)";
    }
    if (indexedLineSet.getChildColorArraySize() > 0)
         expandableListColorIndex.setTitle("colorIndex array, allowed index range is [0.." + (indexedLineSet.getChildColorArraySize() - 1) + "], " + expectedColorArrayLength);
    else expandableListColorIndex.setTitle("colorIndex array, allowed index range is [0..# Color values], "                                        + expectedColorArrayLength);
    
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

      String[][] saa = expandableListColorIndex.getData();
      String[]   sa  = new String[saa.length];
      for (int i = 0; i < sa.length; i++)
      {
        sa[i] = saa[i][0]; // saa is single-column square array
      }
      expandableListTabbedPane.setTitleAt(1, "colorIndex (" + expandableListColorIndex.getRowCount() + ")");

      if ((indexedLineSet.getChildColorArraySize() > 0)  // no warning dialogs if child node not present, since editing likely in progress
       && (sa.length > 0)) // ensure values available to check
      {
          String [][] colorIndexArray = saa; // multiple rows, multiple entries, single value per entry
          for (int i=0; i < colorIndexArray.length; i++)
          {
            colorIndexArray[i] = sa[i].split("\\s+"); // split string of multiple values into string array
            for (int j=0; j < colorIndexArray[i].length; j++)
            {
              int value = new SFInt32(colorIndexArray[i][j]).getValue();
              if     (value >= indexedLineSet.getChildColorArraySize()) // index value too large
              {
                  message = "<html><center>Large index value found for colorIndex[<b>" + i + "</b>]=<b>" + value
                          + "</b>, which is greater than maximum index value of " + (indexedLineSet.getChildColorArraySize() - 1)
                          + ".<br/><br/>Convert value to <b>" + (indexedLineSet.getChildColorArraySize() - 1) + "</b>?";
                  descriptor = new NotifyDescriptor.Confirmation(
                          message, "IndexedLineSet colorIndex value incorrect!", NotifyDescriptor.YES_NO_CANCEL_OPTION);
                  Object userChoice = DialogDisplayer.getDefault().notify(descriptor);
                  if      (userChoice == NotifyDescriptor.YES_OPTION)
                  {
                          colorIndexArray[i][j] = Integer.toString(indexedLineSet.getChildColorArraySize() - 1);
                          valueChanged = true;
                  }
                  else if (userChoice == NotifyDescriptor.CANCEL_OPTION)
                  {
                      valueChanged = false;
                      cancelButtonSelected = true;
                      break; // no more checks
                  }
              }
              else if (new SFInt32(colorIndexArray[i][j]).getValue() < -1) // check if index value illegal, -1 sentinel valueis allowed
              {
                    message = "<html><center>Incorrect value found for colorIndex[<b>" + i + "</b>]=<b>" + value
                              + "</b>, which is less than allowed index value of -1"
                              + ".<br/><br/>Convert value to <b>0</b>?";
                  descriptor = new NotifyDescriptor.Confirmation(
                          message, "IndexedLineSet colorIndex value incorrect!", NotifyDescriptor.YES_NO_CANCEL_OPTION);
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
          if (valueChanged)
          {
//              for (int i=0; i < colorIndexArray.length; i++)
//              {
//                  result += colorIndexArray[i] + " ";
//              }
//              colorIndexTA.setText(indexedLineSet.formatIndexArray(result, true));
              expandableListColorIndex.setData(colorIndexArray);
          }
            // check if number of color indices higher than number of polylines
            if ((colorIndexArray.length > numberOfPolylines) && !colorPerVertexCB.isSelected()) // colorPerVertex='false'
            {
                  message = "<html><center>Found excessive number of colorIndex values (<b>" + (colorIndexArray.length) + "</b>) <br /> "
                            + " since colorPerVertex='" + colorPerVertexCB.isSelected() + "' and only <b>" + numberOfPolylines + "</b> polylines are defined"
                            + ".<br/><br/>Suggestions: remove some colorIndex values, or add more polylines in coordIndex array.";
                descriptor = new NotifyDescriptor.Confirmation(
                        message, "IndexedLineSet colorIndex has too many values", NotifyDescriptor.PLAIN_MESSAGE);
                DialogDisplayer.getDefault().notify(descriptor);
            }
            // check if number of color indices higher than number of polylines
            else if ((colorIndexArray.length < numberOfPolylines) && !colorPerVertexCB.isSelected()) // colorPerVertex='false'
            {
                  message = "<html><center>Found insufficient number of colorIndex values (<b>" + (colorIndexArray.length) + "</b>)"
                            + " since colorPerVertex='" + colorPerVertexCB.isSelected() + "' and <b>" + numberOfPolylines + "</b> polylines are defined"
                            + ".<br/><br/>Suggestions: add more colorIndex values, or remove some polylines in coordIndex array.";
                descriptor = new NotifyDescriptor.Confirmation(
                        message, "IndexedLineSet colorIndex has too few values", NotifyDescriptor.PLAIN_MESSAGE);
                DialogDisplayer.getDefault().notify(descriptor);
            }
            // check if number of color indices higher than number of vertices
            else if ((colorIndexArray.length > indexedLineSet.getChildCoordArraySize()) && colorPerVertexCB.isSelected()) // colorPerVertex='true'
            {
                  message = "<html><center>Found excessive number of colorIndex values (<b>" + (colorIndexArray.length) + "</b>) <br />"
                            + " since colorPerVertex='" + colorPerVertexCB.isSelected() + "' and only <b>" + indexedLineSet.getChildCoordArraySize() + "</b> different <b>Coordinate</b> points are defined"
                            + ".<br/><br/>Suggestions: remove some colorIndex values, or add more points to the <b>Coordinate</b> node.";
                descriptor = new NotifyDescriptor.Confirmation(
                        message, "IndexedLineSet colorIndex has too many values", NotifyDescriptor.PLAIN_MESSAGE);
                DialogDisplayer.getDefault().notify(descriptor);
            }
          }
      }
  }

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_INDEXEDLINESET";
  }

  @Override
  public void unloadInput() throws IllegalArgumentException
  {
    checkIndices();
    unLoadDEFUSE();
    
    indexedLineSet.setColorPerVertex(colorPerVertexCB.isSelected());
    
    indexedLineSet.setInsertCommasCoordIndex    (expandableListCoordIndex.isInsertCommasSet());
    indexedLineSet.setInsertLineBreaksCoordIndex(expandableListCoordIndex.isInsertLineBreaksSet());
    String[][] saa = expandableListCoordIndex.getData();
    String[]   sa  = new String[saa.length];
    for (int i = 0; i < sa.length; i++)
    {
        sa[i] = saa[i][0]; // saa is single-column square array
    }
    indexedLineSet.setCoordIndex(
            INDEXEDLINESET.concatStringArrayCommasLineBreaks(sa,
              expandableListCoordIndex.isInsertCommasSet(), 
              expandableListCoordIndex.isInsertLineBreaksSet()));
    
    indexedLineSet.setInsertCommasColorIndex    (expandableListColorIndex.isInsertCommasSet());
    indexedLineSet.setInsertLineBreaksColorIndex(expandableListColorIndex.isInsertLineBreaksSet());
    saa = expandableListColorIndex.getData();
    sa  = new String[saa.length];
    for (int i = 0; i < sa.length; i++)
    {
        sa[i] = saa[i][0]; // saa is single-column square array
    }
    indexedLineSet.setColorIndex(
            INDEXEDLINESET.concatStringArrayCommasLineBreaks(sa,
              expandableListColorIndex.isInsertCommasSet(), 
              expandableListCoordIndex.isInsertLineBreaksSet()));
   }
}
