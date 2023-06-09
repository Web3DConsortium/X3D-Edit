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

import javax.swing.text.JTextComponent;
import org.openide.util.HelpCtx;

/**
 * COMPOSEDVOLUMESTYLECustomizer.java
 * Created on SEP 26, 2007 11:40 AM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey and Don Brutzman
 * @version $Id$
 */
public class COMPOSEDVOLUMESTYLECustomizer extends BaseCustomizer
{
  private COMPOSEDVOLUMESTYLE composedVolumeStyle;
  private JTextComponent target;
  
  /** Creates new form COMPOSEDVOLUMESTYLECustomizer
   * @param composedVolumeStyle
   * @param target Swing component of interest  
   */
  public COMPOSEDVOLUMESTYLECustomizer(COMPOSEDVOLUMESTYLE composedVolumeStyle, JTextComponent target)
  {
    super(composedVolumeStyle);
    this.composedVolumeStyle = composedVolumeStyle;
    this.target = target;
    
    HelpCtx.setHelpIDString(this, "COMPOSEDVOLUMESTYLE_ELEM_HELPID");
    
    initComponents();
    
        enabledCB.setSelected(composedVolumeStyle.isEnabled());
        orderedCB.setSelected(composedVolumeStyle.isOrdered());
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
        enabledLabel = new javax.swing.JLabel();
        enabledCB = new javax.swing.JCheckBox();
        nodeHintPanel = new javax.swing.JPanel();
        hintLabel1 = new javax.swing.JLabel();
        orderedLabel = new javax.swing.JLabel();
        orderedCB = new javax.swing.JCheckBox();

        setLayout(new java.awt.GridBagLayout());

        dEFUSEpanel1.setMinimumSize(new java.awt.Dimension(198, 77));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel1, gridBagConstraints);

        enabledLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        enabledLabel.setText("enabled");
        enabledLabel.setToolTipText("Enables/disables node operation");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 200, 3, 3);
        add(enabledLabel, gridBagConstraints);

        enabledCB.setToolTipText("Enables/disables node operation");
        enabledCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(enabledCB, gridBagConstraints);

        nodeHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nodeHintPanel.setLayout(new java.awt.GridBagLayout());

        hintLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel1.setText("<html><p align='center'><b>BoundaryEnhancementVolumeStyle</b> contains a<br/>X3DComposableVolumeRenderStyleNode node to define renderStyle.</p>");
        hintLabel1.setToolTipText("multiple style nodes can be applied to volume data");
        hintLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        nodeHintPanel.add(hintLabel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nodeHintPanel, gridBagConstraints);

        orderedLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        orderedLabel.setText("ordered");
        orderedLabel.setToolTipText("If ordered  is true, strictly apply each contained renderStyle node in order declared");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 200, 3, 3);
        add(orderedLabel, gridBagConstraints);

        orderedCB.setToolTipText("If ordered  is true, strictly apply each contained renderStyle node in order declared");
        orderedCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(orderedCB, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
  
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel1;
    private javax.swing.JCheckBox enabledCB;
    private javax.swing.JLabel enabledLabel;
    private javax.swing.JLabel hintLabel1;
    private javax.swing.JPanel nodeHintPanel;
    private javax.swing.JCheckBox orderedCB;
    private javax.swing.JLabel orderedLabel;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_COMPOSEDVOLUMESTYLE";
  }

  @Override
  public void unloadInput()
  {
    unLoadDEFUSE();
    
    composedVolumeStyle.setEnabled(enabledCB.isSelected());
    composedVolumeStyle.setOrdered(orderedCB.isSelected());
  }   
}
