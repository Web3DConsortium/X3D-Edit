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

import java.awt.Dialog;
import javax.swing.DefaultComboBoxModel;
import javax.swing.text.JTextComponent;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.util.HelpCtx;
import org.web3d.x3d.X3DDataObject;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * GEOLODCustomizer.java
 * Created on 6 December 2009
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class GEOLODCustomizer extends BaseCustomizer
{ 
  private final GEOLOD geoLOD;
  private final JTextComponent target;
  private final X3DDataObject xObj;
  
  /** Creates new form LODCustomizer */
  public GEOLODCustomizer(GEOLOD geoLOD, JTextComponent target, X3DDataObject xObj)
  {
    super(geoLOD);
    this.geoLOD = geoLOD;
    this.target = target;
    this.xObj = xObj;
    
    HelpCtx.setHelpIDString(this, "GEOLOD_ELEM_HELPID");

    geoLOD.setVisualizationSelectionAvailable(true); // must precede initComponents() interface initialization
    geoLOD.setVisualizationTooltip("Add wireframe Box and axes to show boundingBoxcenter and size (if defined)");
    
    initComponents();

    geoSystemCB.setSelectedItem(BaseX3DElement.unQuotify(geoLOD.getGeoSystem()));
    
    bboxCenterXTF.setText(geoLOD.getBboxCenterX().trim());
    bboxCenterYTF.setText(geoLOD.getBboxCenterY().trim());
    bboxCenterZTF.setText(geoLOD.getBboxCenterZ().trim());
    bboxSizeXTF.setText(geoLOD.getBboxSizeX().trim());
    bboxSizeYTF.setText(geoLOD.getBboxSizeY().trim());
    bboxSizeZTF.setText(geoLOD.getBboxSizeZ().trim());
    centerXTF.setText(geoLOD.getCenterX().trim());
    centerYTF.setText(geoLOD.getCenterY().trim());
    centerZTF.setText(geoLOD.getCenterZ().trim());
    rangeTF.setText(geoLOD.getRange());

    centerXTF.setToolTipText(DegreesDecimalToDegreesMinutesSeconds(centerXTF.getText()));
    centerYTF.setToolTipText(DegreesDecimalToDegreesMinutesSeconds(centerYTF.getText()));

      rootUrlList.setMasterDocumentLocation(xObj.getPrimaryFile());
      rootUrlList.setUrlData(geoLOD.getRootUrl());
      rootUrlList.setFileChooserX3d();
    child1UrlList.setMasterDocumentLocation(xObj.getPrimaryFile());
    child1UrlList.setUrlData(geoLOD.getChild1Url());
    child1UrlList.setFileChooserX3d();
    child2UrlList.setMasterDocumentLocation(xObj.getPrimaryFile());
    child2UrlList.setUrlData(geoLOD.getChild2Url());
    child2UrlList.setFileChooserX3d();
    child3UrlList.setMasterDocumentLocation(xObj.getPrimaryFile());
    child3UrlList.setUrlData(geoLOD.getChild3Url());
    child3UrlList.setFileChooserX3d();
    child4UrlList.setMasterDocumentLocation(xObj.getPrimaryFile());
    child4UrlList.setUrlData(geoLOD.getChild4Url());
    child4UrlList.setFileChooserX3d();
    
      rootUrlList.checkUrlValues();
    child1UrlList.checkUrlValues();
    child2UrlList.checkUrlValues();
    child3UrlList.checkUrlValues();
    child4UrlList.checkUrlValues();

      rootUrlList.setTarget(target); // enable urlList to reach back into jdom tree to getHeaderIdentifierPath()
    child1UrlList.setTarget(target); // enable urlList to reach back into jdom tree to getHeaderIdentifierPath()
    child2UrlList.setTarget(target); // enable urlList to reach back into jdom tree to getHeaderIdentifierPath()
    child3UrlList.setTarget(target); // enable urlList to reach back into jdom tree to getHeaderIdentifierPath()
    child4UrlList.setTarget(target); // enable urlList to reach back into jdom tree to getHeaderIdentifierPath()

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

        dEFUSEpanel1 = getDEFUSEpanel();
        geoSystemLabel = new javax.swing.JLabel();
        geoSystemCB = new javax.swing.JComboBox<String>();
        geoSystemPanelButton = new javax.swing.JButton();
        rangeLabel = new javax.swing.JLabel();
        rangeTF = new javax.swing.JTextField();
        centerLabel = new javax.swing.JLabel();
        centerXTF = new javax.swing.JTextField();
        centerYTF = new javax.swing.JTextField();
        centerZTF = new javax.swing.JTextField();
        bboxCenterLabel = new javax.swing.JLabel();
        bboxCenterXTF = new javax.swing.JTextField();
        bboxCenterYTF = new javax.swing.JTextField();
        bboxCenterZTF = new javax.swing.JTextField();
        bboxSizeLabel = new javax.swing.JLabel();
        bboxSizeXTF = new javax.swing.JTextField();
        bboxSizeYTF = new javax.swing.JTextField();
        bboxSizeZTF = new javax.swing.JTextField();
        urlsLabel = new javax.swing.JLabel();
        urlTabbedPane = new javax.swing.JTabbedPane();
        diagramLabel = new javax.swing.JLabel();
        rootUrlList = new org.web3d.x3d.palette.items.UrlExpandableList2();
        child1UrlList = new org.web3d.x3d.palette.items.UrlExpandableList2();
        child2UrlList = new org.web3d.x3d.palette.items.UrlExpandableList2();
        child3UrlList = new org.web3d.x3d.palette.items.UrlExpandableList2();
        child4UrlList = new org.web3d.x3d.palette.items.UrlExpandableList2();
        nodeHintPanel = new javax.swing.JPanel();
        hintLabel = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(60, 18));
        setLayout(new java.awt.GridBagLayout());

        dEFUSEpanel1.setMinimumSize(new java.awt.Dimension(198, 77));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel1, gridBagConstraints);

        geoSystemLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        geoSystemLabel.setText("geoSystem");
        geoSystemLabel.setToolTipText("Identifies spatial reference frame: Geodetic, Universal Transverse Mercator, Geocentric");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
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

        rangeLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        rangeLabel.setText("range");
        rangeLabel.setToolTipText("Viewer range from geographic-coordinates center triggers quadtree loading/unloading");
        rangeLabel.setMinimumSize(new java.awt.Dimension(80, 18));
        rangeLabel.setPreferredSize(new java.awt.Dimension(80, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        add(rangeLabel, gridBagConstraints);

        rangeTF.setToolTipText("Viewer range from geographic-coordinates center triggers quadtree loading/unloading");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(rangeTF, gridBagConstraints);

        centerLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        centerLabel.setText("center");
        centerLabel.setToolTipText("Viewer range from geographic-coordinates center triggers quadtree loading/unloading");
        centerLabel.setMinimumSize(new java.awt.Dimension(80, 18));
        centerLabel.setPreferredSize(new java.awt.Dimension(80, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        add(centerLabel, gridBagConstraints);

        centerXTF.setToolTipText("Viewer range from geographic-coordinates center triggers quadtree loading/unloading");
        centerXTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                centerXTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(centerXTF, gridBagConstraints);

        centerYTF.setToolTipText("Viewer range from geographic-coordinates center triggers quadtree loading/unloading");
        centerYTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                centerYTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(centerYTF, gridBagConstraints);

        centerZTF.setToolTipText("Viewer range from geographic-coordinates center triggers quadtree loading/unloading");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(centerZTF, gridBagConstraints);

        bboxCenterLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        bboxCenterLabel.setText("bboxCenter");
        bboxCenterLabel.setToolTipText("position offset from origin of local coordinate system");
        bboxCenterLabel.setMinimumSize(new java.awt.Dimension(80, 18));
        bboxCenterLabel.setPreferredSize(new java.awt.Dimension(80, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        add(bboxCenterLabel, gridBagConstraints);

        bboxCenterXTF.setToolTipText("position offset from origin of local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterXTF, gridBagConstraints);

        bboxCenterYTF.setToolTipText("position offset from origin of local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterYTF, gridBagConstraints);

        bboxCenterZTF.setToolTipText("position offset from origin of local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterZTF, gridBagConstraints);

        bboxSizeLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        bboxSizeLabel.setText("bboxSize");
        bboxSizeLabel.setToolTipText("automatically calculated, can be specified as an optimization or constraint");
        bboxSizeLabel.setMinimumSize(new java.awt.Dimension(80, 18));
        bboxSizeLabel.setPreferredSize(new java.awt.Dimension(80, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        add(bboxSizeLabel, gridBagConstraints);

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
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
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
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
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
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxSizeZTF, gridBagConstraints);

        urlsLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        urlsLabel.setText("url lists");
        urlsLabel.setToolTipText("Select url list to edit");
        urlsLabel.setMinimumSize(new java.awt.Dimension(80, 18));
        urlsLabel.setPreferredSize(new java.awt.Dimension(80, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(13, 3, 3, 3);
        add(urlsLabel, gridBagConstraints);

        urlTabbedPane.setToolTipText("Select url list to edit");
        urlTabbedPane.setMinimumSize(new java.awt.Dimension(650, 200));
        urlTabbedPane.setPreferredSize(new java.awt.Dimension(650, 200));

        diagramLabel.setBackground(new java.awt.Color(255, 255, 255));
        diagramLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        diagramLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/palette/items/resources/GeoLODlevels.png"))); // NOI18N
        diagramLabel.setText("<html><p align='center'>root and child<br/> &nbsp;url arrangement</p>");
        diagramLabel.setToolTipText("root and child url arrangement");
        urlTabbedPane.addTab("layout diagram", diagramLabel);

        rootUrlList.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        rootUrlList.setToolTipText("Use either children nodes or rootUrl to specify this level, not both");
        rootUrlList.setMinimumSize(new java.awt.Dimension(536, 100));
        rootUrlList.setPreferredSize(new java.awt.Dimension(536, 100));
        urlTabbedPane.addTab("rootUrl list", rootUrlList);

        child1UrlList.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        child1UrlList.setToolTipText("Lower-left (southwest) quadtree geometry loaded when viewer is within range");
        child1UrlList.setMinimumSize(new java.awt.Dimension(536, 100));
        child1UrlList.setPreferredSize(new java.awt.Dimension(536, 100));
        urlTabbedPane.addTab("child1Url list", child1UrlList);

        child2UrlList.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        child2UrlList.setToolTipText("Upper-left (northwest) quadtree geometry loaded when viewer is within range");
        child2UrlList.setMinimumSize(new java.awt.Dimension(536, 100));
        child2UrlList.setPreferredSize(new java.awt.Dimension(536, 100));
        urlTabbedPane.addTab("child2Url list", child2UrlList);

        child3UrlList.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        child3UrlList.setToolTipText("Upper-right (northeast) quadtree geometry loaded when viewer is within range");
        child3UrlList.setMinimumSize(new java.awt.Dimension(536, 100));
        child3UrlList.setPreferredSize(new java.awt.Dimension(536, 100));
        urlTabbedPane.addTab("child3Url list", child3UrlList);

        child4UrlList.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        child4UrlList.setToolTipText("Lower-right (southeast) quadtree geometry loaded when viewer is within range");
        child4UrlList.setMinimumSize(new java.awt.Dimension(536, 100));
        child4UrlList.setPreferredSize(new java.awt.Dimension(536, 100));
        urlTabbedPane.addTab("child4Url list", child4UrlList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 3, 3);
        add(urlTabbedPane, gridBagConstraints);

        nodeHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nodeHintPanel.setLayout(new java.awt.GridBagLayout());

        hintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel.setText("<html><b>GeoLOD</b> provides quadtree level-of-detail loading/unloading for multi-resolution terrains");
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
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 10, 3);
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

    private void centerXTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_centerXTFActionPerformed
        centerXTF.setToolTipText(DegreesDecimalToDegreesMinutesSeconds(centerXTF.getText()));
    }//GEN-LAST:event_centerXTFActionPerformed

    private void centerYTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_centerYTFActionPerformed
        centerYTF.setToolTipText(DegreesDecimalToDegreesMinutesSeconds(centerYTF.getText()));

    }//GEN-LAST:event_centerYTFActionPerformed

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
    private javax.swing.JLabel centerLabel;
    private javax.swing.JTextField centerXTF;
    private javax.swing.JTextField centerYTF;
    private javax.swing.JTextField centerZTF;
    private org.web3d.x3d.palette.items.UrlExpandableList2 child1UrlList;
    private org.web3d.x3d.palette.items.UrlExpandableList2 child2UrlList;
    private org.web3d.x3d.palette.items.UrlExpandableList2 child3UrlList;
    private org.web3d.x3d.palette.items.UrlExpandableList2 child4UrlList;
    private org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel1;
    private javax.swing.JLabel diagramLabel;
    private javax.swing.JComboBox<String> geoSystemCB;
    private javax.swing.JLabel geoSystemLabel;
    private javax.swing.JButton geoSystemPanelButton;
    private javax.swing.JLabel hintLabel;
    private javax.swing.JPanel nodeHintPanel;
    private javax.swing.JLabel rangeLabel;
    private javax.swing.JTextField rangeTF;
    private org.web3d.x3d.palette.items.UrlExpandableList2 rootUrlList;
    private javax.swing.JTabbedPane urlTabbedPane;
    private javax.swing.JLabel urlsLabel;
    // End of variables declaration//GEN-END:variables
  
  
  @Override
  public String getNameKey()
  {
    return "NAME_X3D_GEOLOD";
  }

  @Override
  public void unloadInput() throws IllegalArgumentException
  {
    unLoadDEFUSE();
    
      rootUrlList.checkUrlValues();
    child1UrlList.checkUrlValues();
    child2UrlList.checkUrlValues();
    child3UrlList.checkUrlValues();
    child4UrlList.checkUrlValues();

    geoLOD.setGeoSystem(BaseX3DElement.splitStringIntoStringArray(geoSystemCB.getSelectedItem().toString().trim()));
    
    geoLOD.setBboxCenterX(bboxCenterXTF.getText().trim());
    geoLOD.setBboxCenterY(bboxCenterYTF.getText().trim());
    geoLOD.setBboxCenterY(bboxCenterZTF.getText().trim());
    geoLOD.setBboxSizeX(bboxSizeXTF.getText().trim());
    geoLOD.setBboxSizeY(bboxSizeYTF.getText().trim());
    geoLOD.setBboxSizeZ(bboxSizeZTF.getText().trim());
    geoLOD.setCenterX(centerXTF.getText().trim());
    geoLOD.setCenterY(centerYTF.getText().trim());
    geoLOD.setCenterZ(centerZTF.getText().trim());
    geoLOD.setRange(rangeTF.getText().trim());

    geoLOD.setRootUrl  (  rootUrlList.getUrlData());
    geoLOD.setChild1Url(child1UrlList.getUrlData());
    geoLOD.setChild2Url(child2UrlList.getUrlData());
    geoLOD.setChild3Url(child3UrlList.getUrlData());
    geoLOD.setChild4Url(child4UrlList.getUrlData());
  }
}
