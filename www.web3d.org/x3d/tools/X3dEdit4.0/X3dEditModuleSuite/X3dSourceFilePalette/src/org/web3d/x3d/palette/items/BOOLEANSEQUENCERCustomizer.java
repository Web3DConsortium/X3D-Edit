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

/**
 * BOOLEANSEQUENCERCustomizer.java
 * Created on Sep 12, 2007, 2:46 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */

package org.web3d.x3d.palette.items;

import javax.swing.text.JTextComponent;
import org.openide.util.HelpCtx;

/**
 *
 * @author  Mike Bailey <jmbailey@nps.edu>
 */
public class BOOLEANSEQUENCERCustomizer extends BaseCustomizer
{  
  private final BOOLEANSEQUENCER booleanSequencer;
  private final JTextComponent target;

  public BOOLEANSEQUENCERCustomizer(BOOLEANSEQUENCER node, JTextComponent target)
  {
    super(node);
    this.booleanSequencer = node;
    this.target = target;

    HelpCtx.setHelpIDString(this, "BOOLEANSEQUENCER_ELEM_HELPID");

    initComponents();

    expandableListSegmentEnabled.setTitle("key, keyValue arrays");
    expandableListSegmentEnabled.setColumnTitles  (new String[]{"#","key", "keyValue"});
    expandableListSegmentEnabled.setColumnToolTips(new String[]{"index","key fraction input", "keyValue boolean output"});
    expandableListSegmentEnabled.setHeaderTooltip ("Sequencer key/keyValue pairs define the output function");
    expandableListSegmentEnabled.setNewRowData(new Object[]{"0.0",Boolean.TRUE}); // produce checkbox widget rather than string 'true'
    expandableListSegmentEnabled.doIndexInFirstColumn(true);
    expandableListSegmentEnabled.setBoldColumn(1);

    expandableListSegmentEnabled.setShowAppendCommasLineBreaks(true);
    expandableListSegmentEnabled.setInsertCommas    (booleanSequencer.isInsertCommas());
    expandableListSegmentEnabled.setInsertLineBreaks(booleanSequencer.isInsertLineBreaks());
    expandableListSegmentEnabled.setCellEditPanelVisible(false);
    expandableListSegmentEnabled.setKeyColumnIncluded(true);
    expandableListSegmentEnabled.setColumnWidthAndResizeStrategy(false, 75);

    Object[][] oaa = booleanSequencer.getKeysAndValues(); // may be 0-length
    expandableListSegmentEnabled.setData(oaa);
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
        expandableListSegmentEnabled = new org.web3d.x3d.palette.items.ExpandableList();
        fieldEventDiagramLabel = new javax.swing.JLabel();
        eventHintPanel = new javax.swing.JPanel();
        eventLabel = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(expandableListSegmentEnabled, gridBagConstraints);

        fieldEventDiagramLabel.setBackground(new java.awt.Color(255, 255, 255));
        fieldEventDiagramLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fieldEventDiagramLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/palette/items/resources/BooleanSequencerFieldEvents.png"))); // NOI18N
        fieldEventDiagramLabel.setToolTipText(org.openide.util.NbBundle.getMessage(BOOLEANSEQUENCERCustomizer.class, "BOOLEANSEQUENCERCustomizer.fieldEventDiagramLabel.toolTipText")); // NOI18N
        fieldEventDiagramLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(fieldEventDiagramLabel, gridBagConstraints);

        eventHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eventHintPanel.setToolTipText(org.openide.util.NbBundle.getMessage(BOOLEANSEQUENCERCustomizer.class, "BOOLEANSEQUENCERCustomizer.eventHintPanel.toolTipText")); // NOI18N
        eventHintPanel.setLayout(new java.awt.GridBagLayout());

        eventLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eventLabel.setText("<html><b>BooleanSequencer</b> sends boolean events based on <b>TimeSensor</b> <i>fraction_changed</i> events or <i>next/previous</i> events");
        eventLabel.setToolTipText(org.openide.util.NbBundle.getMessage(BOOLEANSEQUENCERCustomizer.class, "BOOLEANSEQUENCERCustomizer.eventLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        eventHintPanel.add(eventLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(eventHintPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
  
  @Override
  public String getNameKey()
  {
    return "NAME_X3D_BOOLEANSEQUENCER";
  }
  
  @Override
  public void unloadInput() throws IllegalArgumentException
  {
    unLoadDEFUSE();

    booleanSequencer.setKeysAndValues   (expandableListSegmentEnabled.getData());
    booleanSequencer.setInsertCommas    (expandableListSegmentEnabled.isInsertCommasSet());
    booleanSequencer.setInsertLineBreaks(expandableListSegmentEnabled.isInsertLineBreaksSet());
  }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel eventHintPanel;
    private javax.swing.JLabel eventLabel;
    private org.web3d.x3d.palette.items.ExpandableList expandableListSegmentEnabled;
    private javax.swing.JLabel fieldEventDiagramLabel;
    // End of variables declaration//GEN-END:variables

}