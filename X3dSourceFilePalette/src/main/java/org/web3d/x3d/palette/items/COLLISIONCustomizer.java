/*
Copyright (c) 1995-2022 held by the author(s).  All rights reserved.
 
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
import static org.web3d.x3d.types.X3DSchemaData.GROUP_CONTAINERFIELD_CHOICES;
import static org.web3d.x3d.types.X3DSchemaData.GROUP_CONTAINERFIELD_TOOLTIPS;

/**
 * COLLISIONCustomizer.java
 * Created on March 14, 2007, 9:57 AM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class COLLISIONCustomizer extends BaseCustomizer
{
  private COLLISION collision;
  private JTextComponent target;
  
  /** Creates new form COLLISIONCustomizer
     * @param collision node data structure
     * @param target Swing component of interest */
  public COLLISIONCustomizer(COLLISION collision, JTextComponent target)
  {
    super(collision);
    this.collision = collision;
    this.target = target;
    
    HelpCtx.setHelpIDString(COLLISIONCustomizer.this, "COLLISION_ELEM_HELPID");

    collision.setVisualizationSelectionAvailable(true); // must precede initComponents() interface initialization
    collision.setVisualizationTooltip("Add wireframe Box and axes to show boundingBox center and size (if defined)");
    
    initComponents();
    
    // can be the proxy field of a Collision node
    super.getDEFUSEpanel().setContainerFieldChoices(GROUP_CONTAINERFIELD_CHOICES, GROUP_CONTAINERFIELD_TOOLTIPS);
    super.getDEFUSEpanel().setContainerField(collision.getContainerField()); // reset value to match updated JComboBox data model
    // DEFUSEpanel initialization must NOT be repeated or else array of choices will be overwritten
    
    if (!collision.getDescription().isBlank())
    {
        checkX3D4FieldSupportDialog("Collision","description"); // X3D4.0 field
        descriptionTF.setText(collision.getDescription());
    }
    enabledCB.setSelected(collision.isEnabled());
    bboxCenterXTF.setText(collision.getBboxCenterX());
    bboxCenterYTF.setText(collision.getBboxCenterY());
    bboxCenterZTF.setText(collision.getBboxCenterZ());
    bboxSizeXTF.setText(collision.getBboxSizeX());
    bboxSizeYTF.setText(collision.getBboxSizeY());    
    bboxSizeZTF.setText(collision.getBboxSizeZ());

    checkVisualizable ();
  }

    private void checkVisualizable ()
    {
      enableAppendVisualizationCB(!(bboxSizeXTF.getText().equals("-1") && bboxSizeYTF.getText().equals("-1") && bboxSizeZTF.getText().equals("-1")));
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
        descriptionLabel = new javax.swing.JLabel();
        descriptionTF = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        bboxCenterXTF = new javax.swing.JTextField();
        bboxCenterYTF = new javax.swing.JTextField();
        bboxCenterZTF = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        bboxSizeXTF = new javax.swing.JTextField();
        bboxSizeYTF = new javax.swing.JTextField();
        bboxSizeZTF = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        enabledCB = new javax.swing.JCheckBox();
        nodeHintPanel = new javax.swing.JPanel();
        hintLabel1 = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(610, 360));
        setLayout(new java.awt.GridBagLayout());

        dEFUSEpanel1.setMinimumSize(new java.awt.Dimension(198, 77));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel1, gridBagConstraints);

        descriptionLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        descriptionLabel.setForeground(new java.awt.Color(0, 153, 153));
        descriptionLabel.setText("description");
        descriptionLabel.setToolTipText("(X3D4) Author-provided prose that describes intended purpose of the node");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 43, 3, 3);
        add(descriptionLabel, gridBagConstraints);

        descriptionTF.setForeground(new java.awt.Color(0, 153, 153));
        descriptionTF.setToolTipText("(X3D4) Author-provided prose that describes intended purpose of the node");
        descriptionTF.setMinimumSize(new java.awt.Dimension(50, 20));
        descriptionTF.setPreferredSize(new java.awt.Dimension(50, 20));
        descriptionTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                descriptionTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(descriptionTF, gridBagConstraints);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel4.setText("bboxCenter");
        jLabel4.setToolTipText("position offset from origin of local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 10, 3, 3);
        add(jLabel4, gridBagConstraints);

        bboxCenterXTF.setToolTipText("position offset from origin of local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterXTF, gridBagConstraints);

        bboxCenterYTF.setToolTipText("position offset from origin of local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterYTF, gridBagConstraints);

        bboxCenterZTF.setToolTipText("position offset from origin of local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterZTF, gridBagConstraints);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel5.setText("bboxSize");
        jLabel5.setToolTipText("automatically calculated, can be specified as an optimization or constraint");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 10, 3, 3);
        add(jLabel5, gridBagConstraints);

        bboxSizeXTF.setToolTipText("automatically calculated, can be specified as an optimization or constraint");
        bboxSizeXTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bboxSizeXTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxSizeXTF, gridBagConstraints);

        bboxSizeYTF.setToolTipText("automatically calculated, can be specified as an optimization or constraint");
        bboxSizeYTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bboxSizeYTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxSizeYTF, gridBagConstraints);

        bboxSizeZTF.setToolTipText("automatically calculated, can be specified as an optimization or constraint");
        bboxSizeZTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bboxSizeZTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxSizeZTF, gridBagConstraints);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel2.setText("enabled");
        jLabel2.setToolTipText("enables/disables collision detection for children and all descendants");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 10, 3, 3);
        add(jLabel2, gridBagConstraints);

        enabledCB.setToolTipText("enables/disables collision detection for children and all descendants");
        enabledCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(enabledCB, gridBagConstraints);

        nodeHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nodeHintPanel.setLayout(new java.awt.GridBagLayout());

        hintLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel1.setText("<html> <p align=\"center\"><b>Collision</b> idetects camera-to-object contact using current view and NavigationInfo avatarSize. <br /> \nCollision is a Grouping node that reports collision detection for its children. </p>\n<ul>\n  <li> Hint: Collision can contain a single proxy child node for substitute collision-detection geometry. <br/> Improve run-time performance by using proxy with simpler contact-calculation geometry. </li>\n  <li> Hint: proxy shapes are not rendered and remain invisible. </li>\n  <li> Hint: apply containerField='proxy' to uniquely identify the proxy child Shape or grouping node.  </li>\n  <li> <i>description</i> field requires X3D version='4.0' </li>\n</ul>");
        hintLabel1.setToolTipText("close this panel to add children nodes");
        hintLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        nodeHintPanel.add(hintLabel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nodeHintPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void bboxSizeXTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bboxSizeXTFActionPerformed
        checkVisualizable ();
    }//GEN-LAST:event_bboxSizeXTFActionPerformed

    private void bboxSizeYTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bboxSizeYTFActionPerformed
        checkVisualizable ();
    }//GEN-LAST:event_bboxSizeYTFActionPerformed

    private void bboxSizeZTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bboxSizeZTFActionPerformed
        checkVisualizable ();
    }//GEN-LAST:event_bboxSizeZTFActionPerformed

    private void descriptionTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_descriptionTFActionPerformed
        // if not X3D4
    }//GEN-LAST:event_descriptionTFActionPerformed
  
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField bboxCenterXTF;
    private javax.swing.JTextField bboxCenterYTF;
    private javax.swing.JTextField bboxCenterZTF;
    private javax.swing.JTextField bboxSizeXTF;
    private javax.swing.JTextField bboxSizeYTF;
    private javax.swing.JTextField bboxSizeZTF;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JTextField descriptionTF;
    private javax.swing.JCheckBox enabledCB;
    private javax.swing.JLabel hintLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel nodeHintPanel;
    // End of variables declaration//GEN-END:variables
 
  @Override
  public String getNameKey()
  {
    return "NAME_X3D_COLLISION";
  }

  @Override
  public void unloadInput()
  {
    unLoadDEFUSE();
    
    if (!descriptionTF.getText().isBlank())
    {
        checkX3D4FieldSupportDialog("Collision","description"); // X3D4.0 field
        collision.setDescription(descriptionTF.getText().trim());
    }
    collision.setEnabled(enabledCB.isSelected());
    collision.setBboxCenterX(bboxCenterXTF.getText().trim());
    collision.setBboxCenterY(bboxCenterYTF.getText().trim());
    collision.setBboxCenterZ(bboxCenterZTF.getText().trim());
    collision.setBboxSizeX  (bboxSizeXTF.getText().trim());
    collision.setBboxSizeY  (bboxSizeYTF.getText().trim());
    collision.setBboxSizeZ  (bboxSizeZTF.getText().trim());
  }
  
}
