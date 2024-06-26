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

import java.awt.Dialog;
import javax.swing.DefaultComboBoxModel;
import javax.swing.text.JTextComponent;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.util.HelpCtx;
import static org.web3d.x3d.types.X3DSchemaData4.DegreesDecimalToDegreesMinutesSeconds;
import static org.web3d.x3d.types.X3DSchemaData.GEOSYSTEM_ATTR_CHOICES;

/**
 * GEOLOCATIONCustomizer.java
 * Created on 23 Apr 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class GEOLOCATIONCustomizer extends BaseCustomizer
{
  private final GEOLOCATION geoLocation;
  private final JTextComponent target;

  public GEOLOCATIONCustomizer(GEOLOCATION geoLocation, JTextComponent target)
  {
    super(geoLocation);
    this.geoLocation = geoLocation;
    this.target = target;
    
    HelpCtx.setHelpIDString(this, "GEOLOCATION_ELEM_HELPID");

    geoLocation.setVisualizationSelectionAvailable(true); // must precede initComponents() interface initialization
    geoLocation.setVisualizationTooltip("Add wireframe Box and axes to show boundingBox center and size (if defined)");
    
    initComponents();
    
    geoSystemCB.setSelectedItem(BaseX3DElement.unQuotify(geoLocation.getGeoSystem()));
    String[] sa = geoLocation.getGeoCoords();
    geoCoordsTF0.setText(sa[0]);
    geoCoordsTF1.setText(sa[1]);
    geoCoordsTF2.setText(sa[2]);
    geoCoordsTF0.setToolTipText(DegreesDecimalToDegreesMinutesSeconds(geoCoordsTF0.getText()));
    geoCoordsTF1.setToolTipText(DegreesDecimalToDegreesMinutesSeconds(geoCoordsTF1.getText()));

    bboxCenterXTF.setText(geoLocation.getBboxCenterX().trim());
    bboxCenterYTF.setText(geoLocation.getBboxCenterY().trim());
    bboxCenterZTF.setText(geoLocation.getBboxCenterZ().trim());
      bboxSizeXTF.setText(geoLocation.getBboxSizeX().trim());
      bboxSizeYTF.setText(geoLocation.getBboxSizeY().trim());
      bboxSizeZTF.setText(geoLocation.getBboxSizeZ().trim());
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

        dEFUSEpanel1 = getDEFUSEpanel();
        geoSystemLabel = new javax.swing.JLabel();
        geoSystemCB = new javax.swing.JComboBox<>();
        geoSystemPanelButton = new javax.swing.JButton();
        geoCoordsLab = new javax.swing.JLabel();
        geoCoordsTF0 = new javax.swing.JTextField();
        geoCoordsTF1 = new javax.swing.JTextField();
        geoCoordsTF2 = new javax.swing.JTextField();
        leftSpacerLab = new javax.swing.JLabel();
        rightSpacerLab = new javax.swing.JLabel();
        bboxSizeXTF = new javax.swing.JTextField();
        bboxSizeLabel = new javax.swing.JLabel();
        bboxCenterZTF = new javax.swing.JTextField();
        bboxSizeYTF = new javax.swing.JTextField();
        bboxCenterXTF = new javax.swing.JTextField();
        bboxSizeZTF = new javax.swing.JTextField();
        bboxCenterYTF = new javax.swing.JTextField();
        bboxCenterLabel = new javax.swing.JLabel();
        nodeHintPanel = new javax.swing.JPanel();
        hintLabel = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel1, gridBagConstraints);

        geoSystemLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        geoSystemLabel.setText("geoSystem");
        geoSystemLabel.setToolTipText("Identifies spatial reference frame: Geodetic, Universal Transverse Mercator, Geocentric");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 10, 3, 3);
        add(geoSystemLabel, gridBagConstraints);

        geoSystemCB.setEditable(true);
        geoSystemCB.setModel(new DefaultComboBoxModel<>(GEOSYSTEM_ATTR_CHOICES));
        geoSystemCB.setToolTipText("identifies spatial reference frame: Geodetic (GD), Geocentric (GC), Universal Transverse Mercator (UTM)");
        geoSystemCB.setMinimumSize(new java.awt.Dimension(6, 22));
        geoSystemCB.setPreferredSize(new java.awt.Dimension(6, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(geoSystemCB, gridBagConstraints);

        geoSystemPanelButton.setText("modify");
        geoSystemPanelButton.setToolTipText("launch geoSystem panel");
        geoSystemPanelButton.setMaximumSize(new java.awt.Dimension(80, 22));
        geoSystemPanelButton.setMinimumSize(new java.awt.Dimension(80, 22));
        geoSystemPanelButton.setPreferredSize(new java.awt.Dimension(6, 22));
        geoSystemPanelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                geoSystemPanelButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 70;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(geoSystemPanelButton, gridBagConstraints);

        geoCoordsLab.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        geoCoordsLab.setText("geoCoords");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 10, 3, 3);
        add(geoCoordsLab, gridBagConstraints);

        geoCoordsTF0.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                geoCoordsTF0ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(geoCoordsTF0, gridBagConstraints);

        geoCoordsTF1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                geoCoordsTF1ActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(geoCoordsTF1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(geoCoordsTF2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        add(leftSpacerLab, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        add(rightSpacerLab, gridBagConstraints);

        bboxSizeXTF.setToolTipText("automatically calculated, can be specified as an optimization or constraint");
        bboxSizeXTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bboxSizeXTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxSizeXTF, gridBagConstraints);

        bboxSizeLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        bboxSizeLabel.setText("bboxSize");
        bboxSizeLabel.setToolTipText("automatically calculated, can be specified as an optimization or constraint");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 10, 3, 3);
        add(bboxSizeLabel, gridBagConstraints);

        bboxCenterZTF.setToolTipText("position offset from origin of local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterZTF, gridBagConstraints);

        bboxSizeYTF.setToolTipText("automatically calculated, can be specified as an optimization or constraint");
        bboxSizeYTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bboxSizeYTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxSizeYTF, gridBagConstraints);

        bboxCenterXTF.setToolTipText("position offset from origin of local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterXTF, gridBagConstraints);

        bboxSizeZTF.setToolTipText("automatically calculated, can be specified as an optimization or constraint");
        bboxSizeZTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bboxSizeZTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxSizeZTF, gridBagConstraints);

        bboxCenterYTF.setToolTipText("position offset from origin of local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterYTF, gridBagConstraints);

        bboxCenterLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        bboxCenterLabel.setText("bboxCenter");
        bboxCenterLabel.setToolTipText("position offset from origin of local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 10, 3, 3);
        add(bboxCenterLabel, gridBagConstraints);

        nodeHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nodeHintPanel.setLayout(new java.awt.GridBagLayout());

        hintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel.setText("<html><p align=\"center\"><b>GeoLocation</b> is a Grouping node that can contain most nodes </p>");
        hintLabel.setToolTipText("close this panel to add children nodes");
        hintLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        nodeHintPanel.add(hintLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
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

  private void geoCoordsTF0ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_geoCoordsTF0ActionPerformed
      geoCoordsTF0.setToolTipText(DegreesDecimalToDegreesMinutesSeconds(geoCoordsTF0.getText()));
  }//GEN-LAST:event_geoCoordsTF0ActionPerformed

  private void geoCoordsTF1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_geoCoordsTF1ActionPerformed
      geoCoordsTF1.setToolTipText(DegreesDecimalToDegreesMinutesSeconds(geoCoordsTF1.getText()));
  }//GEN-LAST:event_geoCoordsTF1ActionPerformed

    private void geoSystemPanelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_geoSystemPanelButtonActionPerformed
        GeoSystemPanel gsp = new GeoSystemPanel(geoSystemCB.getSelectedItem().toString().trim());
        DialogDescriptor dd = new DialogDescriptor(gsp, "geoSystem editor");
        Dialog dial = DialogDisplayer.getDefault().createDialog(dd);
        dial.setVisible(true);
        if (dd.getValue() != DialogDescriptor.CANCEL_OPTION) {
            geoSystemCB.setSelectedItem(gsp.getGeosytemValue());
        }
    }//GEN-LAST:event_geoSystemPanelButtonActionPerformed
    
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
    private javax.swing.JLabel geoCoordsLab;
    private javax.swing.JTextField geoCoordsTF0;
    private javax.swing.JTextField geoCoordsTF1;
    private javax.swing.JTextField geoCoordsTF2;
    private javax.swing.JComboBox<String> geoSystemCB;
    private javax.swing.JLabel geoSystemLabel;
    private javax.swing.JButton geoSystemPanelButton;
    private javax.swing.JLabel hintLabel;
    private javax.swing.JLabel leftSpacerLab;
    private javax.swing.JPanel nodeHintPanel;
    private javax.swing.JLabel rightSpacerLab;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_GEOLOCATION";
  }

  @Override
  public void unloadInput()
  {
    unLoadDEFUSE();
    geoLocation.setGeoSystem(BaseX3DElement.splitStringIntoStringArray(geoSystemCB.getSelectedItem().toString().trim()));
    geoLocation.setGeoCoords(new String[]{geoCoordsTF0.getText().trim(),
                                     geoCoordsTF1.getText().trim(),
                                     geoCoordsTF2.getText().trim()});
    geoLocation.setBboxCenterX(bboxCenterXTF.getText().trim());
    geoLocation.setBboxCenterY(bboxCenterYTF.getText().trim());
    geoLocation.setBboxCenterY(bboxCenterZTF.getText().trim());
    geoLocation.setBboxSizeX(bboxSizeXTF.getText().trim());
    geoLocation.setBboxSizeY(bboxSizeYTF.getText().trim());
    geoLocation.setBboxSizeZ(bboxSizeZTF.getText().trim());
  }
}
