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
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * GEOPOSITIONINTERPOLATORCustomizer.java
 * Created on 28 March 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class GEOPOSITIONINTERPOLATORCustomizer extends BaseCustomizer
{
  private final GEOPOSITIONINTERPOLATOR geoPositionInterpolator;
  private final JTextComponent target;
  
  public GEOPOSITIONINTERPOLATORCustomizer(GEOPOSITIONINTERPOLATOR geoPositionInterpolator, JTextComponent target)
  {
    super(geoPositionInterpolator);
    this.geoPositionInterpolator = geoPositionInterpolator;
    this.target = target;
                   
    HelpCtx.setHelpIDString(this, "GEOPOSITIONINTERPOLATOR_ELEM_HELPID");
    
    initComponents();

    expandableList1.setTitle("key, keyValue arrays");
    //expandableList1.setNumColumns(4);
    expandableList1.setColumnTitles  (new String[]{"#","key","keyValue x","keyValue y","keyValue z"});
    expandableList1.setColumnToolTips(new String[]{"index","key fraction input","geoposition x","geoposition y","geoposition z"});
    expandableList1.setHeaderTooltip ("Interpolator key/keyValue pairs define the output function");
    expandableList1.setNewRowData(new Object[]{"0","0","0","0"});  //  key=0, values 0
    expandableList1.doIndexInFirstColumn(true);
    expandableList1.setBoldColumn(1);

    expandableList1.setShowAppendCommasLineBreaks(true);
    expandableList1.setKeyColumnIncluded(true);
    expandableList1.setInsertCommas    (geoPositionInterpolator.isInsertCommas());
    expandableList1.setInsertLineBreaks(geoPositionInterpolator.isInsertLineBreaks());
    
    String[][] saa = geoPositionInterpolator.getKeysAndValues(); // may be 0-length
    expandableList1.setData(saa);
    
    geoSystemCB.setSelectedItem(BaseX3DElement.unQuotify(geoPositionInterpolator.getGeoSystem()));
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
        expandableList1 = new org.web3d.x3d.palette.items.ExpandableList();
        geoSystemLabel = new javax.swing.JLabel();
        geoSystemPanelButton = new javax.swing.JButton();
        geoSystemCB = new javax.swing.JComboBox<String>();
        eventHintPanel = new javax.swing.JPanel();
        eventsLabel = new javax.swing.JLabel();
        eventLabel1 = new javax.swing.JLabel();
        eventLabel2 = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 83;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 100;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(expandableList1, gridBagConstraints);

        geoSystemLabel.setText("geoSystem");
        geoSystemLabel.setToolTipText("Identifies spatial reference frame: Geodetic, Universal Transverse Mercator, Geocentric");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(geoSystemLabel, gridBagConstraints);

        geoSystemPanelButton.setText("modify");
        geoSystemPanelButton.setToolTipText("launch geoSystem panel");
        geoSystemPanelButton.setMaximumSize(new java.awt.Dimension(80, 22));
        geoSystemPanelButton.setMinimumSize(new java.awt.Dimension(80, 22));
        geoSystemPanelButton.setPreferredSize(new java.awt.Dimension(80, 22));
        geoSystemPanelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                geoSystemPanelButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(geoSystemPanelButton, gridBagConstraints);

        geoSystemCB.setEditable(true);
        geoSystemCB.setModel(new DefaultComboBoxModel<>(GEOSYSTEM_ATTR_CHOICES));
        geoSystemCB.setToolTipText("identifies spatial reference frame: Geodetic (GD), Geocentric (GC), Universal Transverse Mercator (UTM)");
        geoSystemCB.setMinimumSize(new java.awt.Dimension(6, 22));
        geoSystemCB.setPreferredSize(new java.awt.Dimension(6, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 11.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(geoSystemCB, gridBagConstraints);

        eventHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eventHintPanel.setLayout(new java.awt.GridBagLayout());

        eventsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eventsLabel.setText("<html> <p align=\"center\"><b>GeoPositionInterpolator</b> animates objects within a geographic coordinate system and can contain a GeoOrigin node. </p>");
        eventsLabel.setToolTipText("Create a ROUTE to connect output events");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        eventHintPanel.add(eventsLabel, gridBagConstraints);

        eventLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eventLabel1.setText("<html>Primary input event is <b>set_fraction</b>");
        eventLabel1.setToolTipText("Create a ROUTE to connect input and output events");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        eventHintPanel.add(eventLabel1, gridBagConstraints);

        eventLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eventLabel2.setText("<html>Primary output event is <b>value_changed</b>");
        eventLabel2.setToolTipText("Create a ROUTE to connect input and output events");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        eventHintPanel.add(eventLabel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 448;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(eventHintPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

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
    private javax.swing.JPanel eventHintPanel;
    private javax.swing.JLabel eventLabel1;
    private javax.swing.JLabel eventLabel2;
    private javax.swing.JLabel eventsLabel;
    private org.web3d.x3d.palette.items.ExpandableList expandableList1;
    private javax.swing.JComboBox<String> geoSystemCB;
    private javax.swing.JLabel geoSystemLabel;
    private javax.swing.JButton geoSystemPanelButton;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_GEOPOSITIONINTERPOLATOR";
  }

  @Override
  public void unloadInput() throws IllegalArgumentException
  {
    unLoadDEFUSE();
    
    geoPositionInterpolator.setKeysAndValues   (expandableList1.getData());
    geoPositionInterpolator.setInsertCommas    (expandableList1.isInsertCommasSet());
    geoPositionInterpolator.setInsertLineBreaks(expandableList1.isInsertLineBreaksSet());

    geoPositionInterpolator.setGeoSystem(BaseX3DElement.splitStringIntoStringArray(geoSystemCB.getSelectedItem().toString().trim()));
  }  
}
