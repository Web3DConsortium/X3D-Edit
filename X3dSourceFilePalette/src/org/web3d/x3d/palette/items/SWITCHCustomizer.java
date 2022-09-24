/*
Copyright (c) 1995-2022 held by the author(s) .  All rights reserved.
 
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
import javax.swing.text.JTextComponent;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.HelpCtx;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * SWITCHCustomizer.java
 * Created on August 16, 2007, 1:40 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class SWITCHCustomizer extends BaseCustomizer
{ 
  private final SWITCH s_witch;
  private final JTextComponent target;

  protected static String WHICHCHOICE_NEGATIVE_ONE_WARNING = "Warning, whichChoice index -1 means no Switch children are rendered";
  
  /** Creates new form SWITCHCustomizer
   * @param s_witch SWITCH node holding data structures for scene graph
   * @param target Swing component
   */
  public SWITCHCustomizer(SWITCH s_witch, JTextComponent target)
  {
    super(s_witch);
    this.s_witch=s_witch;
    this.target = target;
                           
    HelpCtx.setHelpIDString(SWITCHCustomizer.this, "SWITCH_ELEM_HELPID");

    s_witch.setVisualizationSelectionAvailable(true); // must precede initComponents() interface initialization
    s_witch.setVisualizationTooltip("Add wireframe Box and axes to show boundingBox center and size (if defined)");
    
    initComponents();
    
    // can be the proxy field of a Collision node
    super.getDEFUSEpanel().setContainerFieldChoices(GROUP_CONTAINERFIELD_CHOICES, GROUP_CONTAINERFIELD_TOOLTIPS);
    // DEFUSEpanel initialization must NOT be repeated or else array of choices will be overwritten

    int whichChoice = Integer.parseInt(s_witch.getWhichChoice().trim());
    if (whichChoice <= 6)
    {
         whichChoiceComboBox.setSelectedIndex(whichChoice + 1);
    }
    else whichChoiceComboBox.setSelectedItem(whichChoice);
    // TODO
//    ListCellRenderer comboBoxRenderer = whichChoiceComboBox.getRenderer();
//    comboBoxRenderer.setHorizontalAlignment(JLabel.RIGHT);

    bboxCenterXTF.setText(s_witch.getBboxCenterX().trim());
    bboxCenterYTF.setText(s_witch.getBboxCenterY().trim());
    bboxCenterZTF.setText(s_witch.getBboxCenterZ().trim());
    bboxSizeXTF.setText(s_witch.getBboxSizeX().trim());
    bboxSizeYTF.setText(s_witch.getBboxSizeY().trim());
    bboxSizeZTF.setText(s_witch.getBboxSizeZ().trim());

    checkVisualizable ();
    updateWhichChoiceWarningLabel(whichChoice);

    // TODO display immediate children by node name and DEF name
  }

    private void checkVisualizable ()
    {
      enableAppendVisualizationCB(!(bboxSizeXTF.getText().equals("-1") && bboxSizeYTF.getText().equals("-1") && bboxSizeZTF.getText().equals("-1")));
    }

    private void updateWhichChoiceWarningLabel(int whichChoice)
    {
        Color green = new Color (0, 128, 0);
        switch (whichChoice)
        {
            case -1:
                whichChoiceWarningLabel.setText(WHICHCHOICE_NEGATIVE_ONE_WARNING);
                whichChoiceWarningLabel.setForeground(Color.RED);
                break;
            case 0:
                whichChoiceWarningLabel.setText("whichChoice index 0 means initial Switch child");
                whichChoiceWarningLabel.setForeground(green);
                break;
            case 1:
                whichChoiceWarningLabel.setText("whichChoice index 1 means second Switch child");
                whichChoiceWarningLabel.setForeground(green);
                break;
            case 2:
                whichChoiceWarningLabel.setText("whichChoice index 2 means third Switch child");
                whichChoiceWarningLabel.setForeground(green);
                break;
            case 3:
                whichChoiceWarningLabel.setText("whichChoice index 3 means fourth Switch child");
                whichChoiceWarningLabel.setForeground(green);
                break;
            case 4:
                whichChoiceWarningLabel.setText("whichChoice index 4 means fifth Switch child");
                whichChoiceWarningLabel.setForeground(green);
                break;
            case 5:
                whichChoiceWarningLabel.setText("whichChoice index 5 means sixth Switch child");
                whichChoiceWarningLabel.setForeground(green);
                break;
            case 6:
                whichChoiceWarningLabel.setText("whichChoice index 6 means seventh Switch child");
                whichChoiceWarningLabel.setForeground(green);
                break;
            case 7:
                whichChoiceWarningLabel.setText("whichChoice index 7 means eighth Switch child");
                whichChoiceWarningLabel.setForeground(green);
                break;
            case 8:
                whichChoiceWarningLabel.setText("whichChoice index 8 means ninth Switch child");
                whichChoiceWarningLabel.setForeground(green);
                break;
            case 9:
                whichChoiceWarningLabel.setText("whichChoice index 9 means tenth Switch child");
                whichChoiceWarningLabel.setForeground(green);
                break;
            case 10:
                whichChoiceWarningLabel.setText("whichChoice index 10 means eleventh Switch child");
                whichChoiceWarningLabel.setForeground(green);
                break;
            case 11:
                whichChoiceWarningLabel.setText("whichChoice index 11 means twelfth Switch child");
                whichChoiceWarningLabel.setForeground(green);
                break;
            default:
                whichChoiceWarningLabel.setText("whichChoice index " + whichChoice + " means Switch child #" + (whichChoice + 1));
                whichChoiceWarningLabel.setForeground(green);
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

        whichChoiceLabel = new javax.swing.JLabel();
        bboxCenterLabel = new javax.swing.JLabel();
        bboxCenterXTF = new javax.swing.JTextField();
        bboxCenterYTF = new javax.swing.JTextField();
        bboxCenterZTF = new javax.swing.JTextField();
        bboxSizeLabel = new javax.swing.JLabel();
        bboxSizeXTF = new javax.swing.JTextField();
        bboxSizeYTF = new javax.swing.JTextField();
        bboxSizeZTF = new javax.swing.JTextField();
        dEFUSEpanel1 = getDEFUSEpanel();
        whichChoiceLayoutPanel = new javax.swing.JPanel();
        whichChoiceComboBox = new javax.swing.JComboBox<>();
        whichChoiceWarningLabel = new javax.swing.JLabel();
        nodeHintPanel = new javax.swing.JPanel();
        hintLabel = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        whichChoiceLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        whichChoiceLabel.setText("whichChoice");
        whichChoiceLabel.setToolTipText("Index of active child choice, counting from 0.  -1 means show no children.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 23, 3, 3);
        add(whichChoiceLabel, gridBagConstraints);

        bboxCenterLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        bboxCenterLabel.setText("bboxCenter");
        bboxCenterLabel.setToolTipText("position offset from origin of local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 23, 3, 3);
        add(bboxCenterLabel, gridBagConstraints);

        bboxCenterXTF.setColumns(5);
        bboxCenterXTF.setToolTipText("position offset from origin of local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.3333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterXTF, gridBagConstraints);

        bboxCenterYTF.setColumns(5);
        bboxCenterYTF.setToolTipText("position offset from origin of local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.3333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterYTF, gridBagConstraints);

        bboxCenterZTF.setColumns(5);
        bboxCenterZTF.setToolTipText("position offset from origin of local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.3333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 23);
        add(bboxCenterZTF, gridBagConstraints);

        bboxSizeLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        bboxSizeLabel.setText("bboxSize");
        bboxSizeLabel.setToolTipText("automatically calculated, can be specified as an optimization or constraint");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 23, 3, 3);
        add(bboxSizeLabel, gridBagConstraints);

        bboxSizeXTF.setColumns(5);
        bboxSizeXTF.setToolTipText("automatically calculated, can be specified as an optimization or constraint");
        bboxSizeXTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bboxSizeXTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.3333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxSizeXTF, gridBagConstraints);

        bboxSizeYTF.setColumns(5);
        bboxSizeYTF.setToolTipText("automatically calculated, can be specified as an optimization or constraint");
        bboxSizeYTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bboxSizeYTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.3333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxSizeYTF, gridBagConstraints);

        bboxSizeZTF.setColumns(5);
        bboxSizeZTF.setToolTipText("automatically calculated, can be specified as an optimization or constraint");
        bboxSizeZTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bboxSizeZTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.3333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 23);
        add(bboxSizeZTF, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel1, gridBagConstraints);

        whichChoiceLayoutPanel.setLayout(new java.awt.GridBagLayout());

        whichChoiceComboBox.setEditable(true);
        whichChoiceComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "-1", "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50" }));
        whichChoiceComboBox.setToolTipText("Index of active child choice, counting from 0.  -1 means show no children.");
        whichChoiceComboBox.setMinimumSize(new java.awt.Dimension(35, 20));
        whichChoiceComboBox.setPreferredSize(new java.awt.Dimension(35, 20));
        whichChoiceComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                whichChoiceComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 15;
        whichChoiceLayoutPanel.add(whichChoiceComboBox, gridBagConstraints);

        whichChoiceWarningLabel.setText(WHICHCHOICE_NEGATIVE_ONE_WARNING);
        whichChoiceWarningLabel.setToolTipText("Index of active child choice, counting from 0.  -1 means show no children.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 12, 3, 8);
        whichChoiceLayoutPanel.add(whichChoiceWarningLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(whichChoiceLayoutPanel, gridBagConstraints);

        nodeHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nodeHintPanel.setLayout(new java.awt.GridBagLayout());

        hintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel.setText("<html><p align='center'><b>Switch</b> is a Grouping node that can contain most nodes.</p> <p align='center'>Either one or no children are displayed at a given time.</p>");
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
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nodeHintPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void whichChoiceComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_whichChoiceComboBoxActionPerformed
    {//GEN-HEADEREND:event_whichChoiceComboBoxActionPerformed
       updateWhichChoiceWarningLabel(whichChoiceComboBox.getSelectedIndex() - 1); // TODO  new Integer(whichChoiceComboBox.get().trim()).intValue() );
    }//GEN-LAST:event_whichChoiceComboBoxActionPerformed

    private void bboxSizeXTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bboxSizeXTFActionPerformed
        checkVisualizable ();
    }//GEN-LAST:event_bboxSizeXTFActionPerformed

    private void bboxSizeYTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bboxSizeYTFActionPerformed
        checkVisualizable ();
    }//GEN-LAST:event_bboxSizeYTFActionPerformed

    private void bboxSizeZTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bboxSizeZTFActionPerformed
        checkVisualizable ();
    }//GEN-LAST:event_bboxSizeZTFActionPerformed
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bboxCenterLabel;
    private javax.swing.JTextField bboxCenterXTF;
    private javax.swing.JTextField bboxCenterYTF;
    private javax.swing.JTextField bboxCenterZTF;
    private javax.swing.JLabel bboxSizeLabel;
    private javax.swing.JTextField bboxSizeXTF;
    private javax.swing.JTextField bboxSizeYTF;
    private javax.swing.JTextField bboxSizeZTF;
    private org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel1;
    private javax.swing.JLabel hintLabel;
    private javax.swing.JPanel nodeHintPanel;
    private javax.swing.JComboBox<String> whichChoiceComboBox;
    private javax.swing.JLabel whichChoiceLabel;
    private javax.swing.JPanel whichChoiceLayoutPanel;
    private javax.swing.JLabel whichChoiceWarningLabel;
    // End of variables declaration//GEN-END:variables
  
  
  @Override
  public String getNameKey()
  {
    return "NAME_X3D_SWITCH";
  }

  @Override
  public void unloadInput() throws IllegalArgumentException
  {
    NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
          "<html><p align='center'>Switch whichChoice='-1' means that no child will be displayed.</p>" +
          "<p>&nbsp;</p>" +
          "<p align='center'>Set whichChoice='0' to display initial child instead?</p>",
          "Default whichChoice is -1", NotifyDescriptor.YES_NO_OPTION);
    if (whichChoiceComboBox.getSelectedItem().toString().equals("-1") ||
        whichChoiceComboBox.getSelectedItem().toString().isEmpty())
    {
        if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
        {
          whichChoiceComboBox.setSelectedItem("0");
        }
    }

    unLoadDEFUSE();
    
    s_witch.setWhichChoice(String.valueOf(whichChoiceComboBox.getSelectedItem()));
    s_witch.setBboxCenterX(bboxCenterXTF.getText().trim());
    s_witch.setBboxCenterY(bboxCenterYTF.getText().trim());
    s_witch.setBboxCenterY(bboxCenterZTF.getText().trim());
    s_witch.setBboxSizeX(bboxSizeXTF.getText().trim());
    s_witch.setBboxSizeY(bboxSizeYTF.getText().trim());
    s_witch.setBboxSizeZ(bboxSizeZTF.getText().trim());
  } 

}
