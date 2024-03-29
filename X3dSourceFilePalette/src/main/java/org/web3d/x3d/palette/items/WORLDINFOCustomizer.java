/*
Copyright (c) 1995-2023 held by the author(s).  All rights reserved.
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

import javax.swing.JLabel;
import javax.swing.text.JTextComponent;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.HelpCtx;

/**
 * WORLDINFOCustomizer.java
 * Created on 29 April 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey and Don Brutzman
 * @version $Id$
 */
public class WORLDINFOCustomizer extends BaseCustomizer
{

  private final WORLDINFO worldInfo;
  private final JTextComponent target;

  public WORLDINFOCustomizer(WORLDINFO worldInfo, JTextComponent target)
  {
    super(worldInfo);
    this.worldInfo = worldInfo;
    this.target = target;
    
    HelpCtx.setHelpIDString(this, "WORLDINFO_ELEM_HELPID");
    
    initComponents();

    initializeInfoTable();
    
    titleTF.setText(worldInfo.getTitle());
    checkTitle();

        insertCommasCheckBox.setSelected(worldInfo.isInsertCommas());
    insertLineBreaksCheckBox.setSelected(worldInfo.isInsertLineBreaks());
  }
    private void initializeInfoTable()
    {
        infoTable.setTitle("info array");
        infoTable.setColumnTitles(new String[]
        {
            "#", "Information values"
        });
        infoTable.setHeaderTooltip("Array of single or multiple string values");
        infoTable.setColumnToolTips(new String[]{"index","Array of single or multiple string values"});
        infoTable.setNewRowData(new Object[]
        {
            "", "" // second value used to add new row
        });
        infoTable.setTextAlignment(JLabel.TRAILING);
        infoTable.getTable().setRowHeight(16);
        infoTable.doIndexInFirstColumn(true);
        infoTable.setDataStringBased(true);
        infoTable.setData(worldInfo.getInfoArray());

        // apparently the following has to come after setting data
        infoTable.getTable().getColumnModel().getColumn(0).setPreferredWidth(500);
    }
    /** Check title for naming conventions */
    private void checkTitle()
    {
        String displayTitle = titleTF.getText().trim();
        titleTF.setText(displayTitle); // omit excess whitespace, if any
        
        // get model title
        String modelTitle = getBaseX3DElement().getMetaTitle();
        
        if (displayTitle.contains(" "))
        {
            NotifyDescriptor d = new NotifyDescriptor.Confirmation(
               "<html><p align='center'>Remove embedded whitespace from WorldInfo title (X3D file name)?</p><p>&nbsp;</p><p align='center'>\"<b>" + displayTitle + "</b>\" to \"<b>" + displayTitle.replace(" ","") + "</b>\"</p>",
               "Confirm", NotifyDescriptor.YES_NO_OPTION);
            if (DialogDisplayer.getDefault().notify(d) == NotifyDescriptor.YES_OPTION)
            {
                displayTitle = displayTitle.replace(" ","");
                titleTF.setText(displayTitle); // omit embedded whitespace
                System.out.println ("*** WorldInfo title=" + displayTitle + "'");
            }
        }
        if (displayTitle.endsWith(".x3d") && !displayTitle.equals(modelTitle))
        {
            NotifyDescriptor d = new NotifyDescriptor.Confirmation(
               "<html><p align='center'>Modify Worldinfo title <b>" + displayTitle + "</b> to match head/meta title <b>" + modelTitle + "</b>?</p>",
               "Confirm", NotifyDescriptor.YES_NO_OPTION);
            if (DialogDisplayer.getDefault().notify(d) == NotifyDescriptor.YES_OPTION)
            {
                displayTitle = modelTitle;
                titleTF.setText(displayTitle); // update
                System.out.println ("*** WorldInfo title=" + displayTitle + "'");
            }
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

        org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel1 = getDEFUSEpanel();
        titleLabel = new javax.swing.JLabel();
        titleTF = new javax.swing.JTextField();
        infoTable = new org.web3d.x3d.palette.items.ExpandableList();
        appendLabel = new javax.swing.JLabel();
        insertLineBreaksCheckBox = new javax.swing.JCheckBox();
        insertCommasCheckBox = new javax.swing.JCheckBox();
        spacerLabel = new javax.swing.JLabel();
        hintLabel = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(650, 510));
        setLayout(new java.awt.GridBagLayout());

        dEFUSEpanel1.setMaximumSize(null);
        dEFUSEpanel1.setMinimumSize(null);
        dEFUSEpanel1.setPreferredSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel1, gridBagConstraints);

        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        titleLabel.setText("title");
        titleLabel.setToolTipText("World title, placed in X3D window title");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(16, 15, 16, 3);
        add(titleLabel, gridBagConstraints);

        titleTF.setToolTipText("World title, placed in X3D window title");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(16, 3, 16, 12);
        add(titleTF, gridBagConstraints);

        infoTable.setToolTipText("Single or multiple string values");
        infoTable.setColumnTitles(new String[] {"string values"});
        infoTable.setMinimumSize(null);
        infoTable.setPreferredSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(infoTable, gridBagConstraints);

        appendLabel.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        appendLabel.setText("append:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(appendLabel, gridBagConstraints);

        insertLineBreaksCheckBox.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        insertLineBreaksCheckBox.setText("line feeds");
        insertLineBreaksCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertLineBreaksCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 1, 3, 3);
        add(insertLineBreaksCheckBox, gridBagConstraints);

        insertCommasCheckBox.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        insertCommasCheckBox.setText("commas,");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(insertCommasCheckBox, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 250;
        gridBagConstraints.weightx = 1.0;
        add(spacerLabel, gridBagConstraints);

        hintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel.setText("<html><p align=\"center\"><b>WorldInfo</b> contains a browsesr-displayable title and persistent string information about an X3D scene.</p>\n<br />\n<p align=\"center\">For X3D4 models, <b>WorldInfo</b> can also contain a <b>MetadataSet</b> node with further metadata information about the scene.</p>");
        hintLabel.setToolTipText("HAnimSite nodes aid humanoid animation");
        hintLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        hintLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(hintLabel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void insertLineBreaksCheckBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_insertLineBreaksCheckBoxActionPerformed
    {//GEN-HEADEREND:event_insertLineBreaksCheckBoxActionPerformed

        // TODO add your handling code here:}//GEN-LAST:event_insertLineBreaksCheckBoxActionPerformed
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel appendLabel;
    private javax.swing.JLabel hintLabel;
    private org.web3d.x3d.palette.items.ExpandableList infoTable;
    private javax.swing.JCheckBox insertCommasCheckBox;
    private javax.swing.JCheckBox insertLineBreaksCheckBox;
    private javax.swing.JLabel spacerLabel;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JTextField titleTF;
    // End of variables declaration//GEN-END:variables

  // End of variables declaration

  String[] buildInfoArray()
  {
      int numberRows = infoTable.getTable().getModel().getRowCount();
      String[] valueArray = new String[numberRows];
      for (int row=0; row < numberRows; row++)
      {
          String newValue = new String();
          if            (infoTable.getTable().getModel().getValueAt(row, 1) != null)
          {
              newValue = infoTable.getTable().getModel().getValueAt(row, 1).toString();
          }
          if (checkStringValue (row, newValue))
          {
               valueArray[row] = newValue.trim(); // XML escaping handled by WORLDINFO.createAttributes()
          }
          else valueArray[row] = ""; // empty strings are legal values, nulls are not
      }
      return valueArray;
  }

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_WORLDINFO";
  }

  @Override
  public void unloadInput()
      throws IllegalArgumentException
  {
    unLoadDEFUSE();

    worldInfo.setInfoArray (buildInfoArray());
    worldInfo.setTitle     (titleTF.getText().trim());
  }
}
