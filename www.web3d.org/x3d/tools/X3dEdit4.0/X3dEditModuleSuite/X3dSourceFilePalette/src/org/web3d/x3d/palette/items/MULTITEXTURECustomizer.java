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
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;
import org.openide.util.HelpCtx;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFColor;
import static org.web3d.x3d.types.X3DSchemaData.*;
/**
 * MULTITEXTURECustomizer.java
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class MULTITEXTURECustomizer extends BaseCustomizer
{
  private final MULTITEXTURE multiTexture;
  private final JTextComponent target;
  
  public MULTITEXTURECustomizer(MULTITEXTURE multiTexture, JTextComponent target)
  {
    super(multiTexture);
    this.multiTexture = multiTexture;
    this.target = target;
    
    HelpCtx.setHelpIDString(this, "MULTITEXTURE_ELEM_HELPID");
    
    initComponents();

    alphaTF.setText(multiTexture.getAlpha());
    
    String c0 = multiTexture.getColor0();
    String c1 = multiTexture.getColor1();
    String c2 = multiTexture.getColor2();
    color0TF.setText(c0);
    color1TF.setText(c1);
    color2TF.setText(c2);
    colorChooser.setColor((new SFColor(c0, c1, c2)).getColor());

    modeList.setTitle("mode list");
    modeList.setColumnTitles(new String[]
            {
                "mode values"
            });
    modeList.setNewRowData(new Object[]
            {
                // ""
            });
    modeList.setHeaderTooltip("only enter allowed mode values");
    modeList.setTextAlignment(JLabel.CENTER);
    modeList.getTable().setRowHeight(16);
    modeList.setCellEditPanelVisible (false);
    modeList.setGeneratePointsDescriptions(MULTITEXTURE_ATTR_MODE_DESCRIPTIONS);
    modeList.setGeneratePointsChoices(MULTITEXTURE_ATTR_MODE_CHOICES); // provide choice values for appending

    functionList.setTitle("function list");
    functionList.setColumnTitles(new String[]
            {
                "function values"
            });
    functionList.setNewRowData(new Object[]
            {
                // ""
            });
    functionList.setHeaderTooltip("only enter allowed function values");
    functionList.setTextAlignment(JLabel.CENTER);
    functionList.getTable().setRowHeight(16);
    functionList.setCellEditPanelVisible (false);
    functionList.setGeneratePointsDescriptions(MULTITEXTURE_ATTR_FUNCTION_DESCRIPTIONS);
    functionList.setGeneratePointsChoices(MULTITEXTURE_ATTR_FUNCTION_CHOICES); // provide choice values for appending

    sourceList.setTitle("source list");
    sourceList.setColumnTitles(new String[]
            {
                "source values"
            });
    sourceList.setNewRowData(new Object[]
            {
                // ""
            });
    sourceList.setHeaderTooltip("only enter allowed source values");
    sourceList.setTextAlignment(JLabel.CENTER);
    sourceList.getTable().setRowHeight(16);
    sourceList.setCellEditPanelVisible (false);
    sourceList.setGeneratePointsDescriptions(MULTITEXTURE_ATTR_SOURCE_DESCRIPTIONS);
    sourceList.setGeneratePointsChoices(MULTITEXTURE_ATTR_SOURCE_CHOICES); // provide choice values for appending

    // initialize ExpandableLists

    String [][] emptyStringArray = { {} };
    String []   valueStringList;
    String [][] valueStringArray; // fill zeroth columns with value for each row

    valueStringList  = multiTexture.getMode().replace("\"", "").replace(',', ' ').trim().split("\\s");
    valueStringArray = new String [valueStringList.length][1];
    for (int i = 0; i < valueStringList.length; i++)
    {
      valueStringArray[i][0] = valueStringList[i];
    }
    if (multiTexture.getMode().length() == 0)
      modeList.setData(emptyStringArray);
    else
      modeList.setData(valueStringArray);

    valueStringList  = multiTexture.getFunction().replace("\"", "").replace(',', ' ').trim().split("\\s");
    valueStringArray = new String [valueStringList.length][1];
    for (int i = 0; i < valueStringList.length; i++)
    {
      valueStringArray[i][0] = valueStringList[i];
    }
    if (multiTexture.getFunction().length() == 0)
      functionList.setData(emptyStringArray);
    else
      functionList.setData(valueStringArray);

    valueStringList  = multiTexture.getSource().replace("\"", "").replace(',', ' ').trim().split("\\s");
    valueStringArray = new String [valueStringList.length][1];
    for (int i = 0; i < valueStringList.length; i++)
    {
      valueStringArray[i][0] = valueStringList[i];
    }
    if (multiTexture.getSource().length() == 0)
      sourceList.setData(emptyStringArray);
    else
      sourceList.setData(valueStringArray);
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
        colorPanel = new javax.swing.JPanel();
        alphaLabel = new javax.swing.JLabel();
        alphaTF = new javax.swing.JTextField();
        colorLabel = new javax.swing.JLabel();
        color0TF = new javax.swing.JTextField();
        color1TF = new javax.swing.JTextField();
        color2TF = new javax.swing.JTextField();
        colorChooser = new net.java.dev.colorchooser.ColorChooser();
        tabbedPane = new javax.swing.JTabbedPane();
        modeList = new org.web3d.x3d.palette.items.ExpandableList();
        functionList = new org.web3d.x3d.palette.items.ExpandableList();
        sourceList = new org.web3d.x3d.palette.items.ExpandableList();
        nodeHintPanel = new javax.swing.JPanel();
        hintLabel = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(618, 480));
        setLayout(new java.awt.GridBagLayout());

        dEFUSEpanel1.setMinimumSize(new java.awt.Dimension(198, 77));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel1, gridBagConstraints);

        colorPanel.setLayout(new java.awt.GridBagLayout());

        alphaLabel.setText("alpha");
        alphaLabel.setToolTipText("alpha (1-transparency) base value for mode operations");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        colorPanel.add(alphaLabel, gridBagConstraints);

        alphaTF.setColumns(5);
        alphaTF.setToolTipText("alpha (1-transparency) base value for mode operations");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        colorPanel.add(alphaTF, gridBagConstraints);

        colorLabel.setText("color");
        colorLabel.setToolTipText("RGB base values for mode operations");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        colorPanel.add(colorLabel, gridBagConstraints);

        color0TF.setColumns(5);
        color0TF.setToolTipText("RGB base values for mode operations");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        colorPanel.add(color0TF, gridBagConstraints);

        color1TF.setColumns(5);
        color1TF.setToolTipText("RGB base values for mode operations");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        colorPanel.add(color1TF, gridBagConstraints);

        color2TF.setColumns(5);
        color2TF.setToolTipText("RGB base values for mode operations");
        color2TF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                color2TFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        colorPanel.add(color2TF, gridBagConstraints);

        colorChooser.setPreferredSize(new java.awt.Dimension(20, 20));
        colorChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                colorChooserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout colorChooserLayout = new javax.swing.GroupLayout(colorChooser);
        colorChooser.setLayout(colorChooserLayout);
        colorChooserLayout.setHorizontalGroup(
            colorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );
        colorChooserLayout.setVerticalGroup(
            colorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 18, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        colorPanel.add(colorChooser, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 36, 3, 36);
        add(colorPanel, gridBagConstraints);

        tabbedPane.setPreferredSize(new java.awt.Dimension(350, 200));

        modeList.setToolTipText("Single or multiple string values");
        modeList.setColumnTitles(new String[] {"string values"});
        modeList.setMinimumSize(new java.awt.Dimension(250, 120));
        modeList.setPreferredSize(new java.awt.Dimension(350, 160));
        tabbedPane.addTab("mode list", modeList);

        functionList.setToolTipText("Single or multiple string values");
        functionList.setColumnTitles(new String[] {"string values"});
        functionList.setMinimumSize(new java.awt.Dimension(250, 120));
        functionList.setPreferredSize(new java.awt.Dimension(350, 160));
        tabbedPane.addTab("function  list", functionList);

        sourceList.setToolTipText("Single or multiple string values to render");
        sourceList.setColumnTitles(new String[] {"string values"});
        sourceList.setMinimumSize(new java.awt.Dimension(250, 120));
        sourceList.setPreferredSize(new java.awt.Dimension(350, 160));
        tabbedPane.addTab("source list", sourceList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 60;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(tabbedPane, gridBagConstraints);

        nodeHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nodeHintPanel.setLayout(new java.awt.GridBagLayout());

        hintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel.setText("<html><b>MultiTexture</b> can contain multiple texture nodes");
        hintLabel.setToolTipText("close this panel to add child texture nodes");
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
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nodeHintPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void colorChooserActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_colorChooserActionPerformed
    {//GEN-HEADEREND:event_colorChooserActionPerformed
    Color c = colorChooser.getColor();
    color0TF.setText(formatDecimal((float)c.getRed()/255));
    color1TF.setText(formatDecimal((float)c.getGreen()/255));
    color2TF.setText(formatDecimal((float)c.getBlue()/255));
}//GEN-LAST:event_colorChooserActionPerformed

    private void color2TFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_color2TFActionPerformed
    {//GEN-HEADEREND:event_color2TFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_color2TFActionPerformed
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel alphaLabel;
    private javax.swing.JTextField alphaTF;
    private javax.swing.JTextField color0TF;
    private javax.swing.JTextField color1TF;
    private javax.swing.JTextField color2TF;
    private net.java.dev.colorchooser.ColorChooser colorChooser;
    private javax.swing.JLabel colorLabel;
    private javax.swing.JPanel colorPanel;
    private org.web3d.x3d.palette.items.ExpandableList functionList;
    private javax.swing.JLabel hintLabel;
    private org.web3d.x3d.palette.items.ExpandableList modeList;
    private javax.swing.JPanel nodeHintPanel;
    private org.web3d.x3d.palette.items.ExpandableList sourceList;
    private javax.swing.JTabbedPane tabbedPane;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_MULTITEXTURE";
  }

  @Override
  public void unloadInput() throws IllegalArgumentException
  {
    unLoadDEFUSE();

    multiTexture.setAlpha(alphaTF.getText().trim());
    
    multiTexture.setColor0(color0TF.getText().trim());
    multiTexture.setColor1(color1TF.getText().trim());
    multiTexture.setColor2(color2TF.getText().trim());

    JTable table = modeList.getTable();
    DefaultTableModel tableModel = (DefaultTableModel)table.getModel();
    StringBuilder sb = new StringBuilder();
    for (int row=0; row < tableModel.getRowCount(); row++)
    {
      String value = ((String)tableModel.getValueAt(row, 0));
      if (value != null)
          sb.append('"').append(value.trim()).append("\" ");
    }
    multiTexture.setMode(sb.toString().trim());
    // multiTexture.setFunction();

    table = functionList.getTable();
    tableModel = (DefaultTableModel)table.getModel();
    sb = new StringBuilder();
    for (int row=0; row < tableModel.getRowCount(); row++)
    {
      String value = ((String)tableModel.getValueAt(row, 0));
      if (value != null)
          sb.append('"').append(value.trim()).append("\" ");
    }
    multiTexture.setFunction(sb.toString().trim());

    table = sourceList.getTable();
    tableModel = (DefaultTableModel)table.getModel();
    sb = new StringBuilder();
    for (int row=0; row < tableModel.getRowCount(); row++)
    {
      String value = ((String)tableModel.getValueAt(row, 0));
      if (value != null)
          sb.append('"').append(value.trim()).append("\" ");
    }
    multiTexture.setSource(sb.toString().trim());
  }  
}
