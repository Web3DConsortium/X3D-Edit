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

import javax.swing.text.JTextComponent;
import org.openide.util.HelpCtx;

/**
 * ISOSURFACEVOLUMEDATACustomizer.java
 * Created on 15 November 2011
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey and Don Brutzman
 * @version $Id$
 */
public class ISOSURFACEVOLUMEDATACustomizer extends BaseCustomizer
{
  private ISOSURFACEVOLUMEDATA isoSurfaceVolumeData;
  private JTextComponent target;
  
  /** Creates new form VOLUMEDATACustomizer
   * @param isoSurfaceVolumeData
   * @param target  
   */
  public ISOSURFACEVOLUMEDATACustomizer(ISOSURFACEVOLUMEDATA isoSurfaceVolumeData, JTextComponent target)
  {
    super(isoSurfaceVolumeData);
    this.isoSurfaceVolumeData = isoSurfaceVolumeData;
    this.target = target;
    
    HelpCtx.setHelpIDString(this, "VOLUMEDATA_ELEM_HELPID");

    isoSurfaceVolumeData.setVisualizationSelectionAvailable(true); // must precede initComponents() interface initialization
    isoSurfaceVolumeData.setVisualizationTooltip("Add wireframe Box and axes to show boundingBox center and size (if defined)");
    
    initComponents();
    
    dimensionsXTF.setText(isoSurfaceVolumeData.getDimensionsX().toString());
    dimensionsYTF.setText(isoSurfaceVolumeData.getDimensionsY().toString());
    dimensionsZTF.setText(isoSurfaceVolumeData.getDimensionsZ().toString());
    bboxCenterXTF.setText(isoSurfaceVolumeData.getBboxCenterX());
    bboxCenterYTF.setText(isoSurfaceVolumeData.getBboxCenterY());
    bboxCenterZTF.setText(isoSurfaceVolumeData.getBboxCenterZ());
    bboxSizeXTF.setText(isoSurfaceVolumeData.getBboxSizeX());
    bboxSizeYTF.setText(isoSurfaceVolumeData.getBboxSizeY());
    bboxSizeZTF.setText(isoSurfaceVolumeData.getBboxSizeZ());
    
     contourStepSizeTF.setText(isoSurfaceVolumeData.getContourStepSize());
    surfaceToleranceTF.setText(isoSurfaceVolumeData.getSurfaceTolerance());
       surfaceValuesTF.setText(isoSurfaceVolumeData.getSurfaceValues());

    expandableListSegmentEnabled.setTitle("segmentEnabled array");
    expandableListSegmentEnabled.setToolTipText("segmentEnabled specifies whether each segment is rendered or not");
    expandableListSegmentEnabled.setColumnTitles  (new String[]{"#","segmentEnabled"});
    expandableListSegmentEnabled.setColumnToolTips(new String[]{"index","whether each segment is enabled"});
    expandableListSegmentEnabled.setHeaderTooltip ("segmentEnabled defines specifies whether a segment is rendered or not");
    expandableListSegmentEnabled.setNewRowData(new Object[]{Boolean.TRUE}); // produce checkbox widget rather than string 'true'
    expandableListSegmentEnabled.doIndexInFirstColumn(true);
    expandableListSegmentEnabled.setBoldColumn(1);

    expandableListSegmentEnabled.setShowAppendCommasLineBreaks(true);
    expandableListSegmentEnabled.setInsertCommas    (isoSurfaceVolumeData.isInsertCommas());
    expandableListSegmentEnabled.setInsertLineBreaks(isoSurfaceVolumeData.isInsertLineBreaks());
    expandableListSegmentEnabled.setCellEditPanelVisible(false);
    expandableListSegmentEnabled.setKeyColumnIncluded(false);
    expandableListSegmentEnabled.setColumnWidthAndResizeStrategy(false, 75);

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
        contourStepSizeLabel = new javax.swing.JLabel();
        contourStepSizeTF = new javax.swing.JTextField();
        surfaceToleranceLabel = new javax.swing.JLabel();
        surfaceToleranceTF = new javax.swing.JTextField();
        surfaceValuesLabel = new javax.swing.JLabel();
        surfaceValuesTF = new javax.swing.JTextField();
        dimensionsLabel = new javax.swing.JLabel();
        dimensionsXTF = new javax.swing.JTextField();
        dimensionsYTF = new javax.swing.JTextField();
        dimensionsZTF = new javax.swing.JTextField();
        bboxCenterLabel = new javax.swing.JLabel();
        bboxCenterXTF = new javax.swing.JTextField();
        bboxCenterYTF = new javax.swing.JTextField();
        bboxCenterZTF = new javax.swing.JTextField();
        bboxSizeLabel = new javax.swing.JLabel();
        bboxSizeXTF = new javax.swing.JTextField();
        bboxSizeYTF = new javax.swing.JTextField();
        bboxSizeZTF = new javax.swing.JTextField();
        expandableListSegmentEnabled = new org.web3d.x3d.palette.items.ExpandableList();
        nodeHintPanel = new javax.swing.JPanel();
        hintLabel1 = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        dEFUSEpanel1.setMinimumSize(new java.awt.Dimension(198, 77));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel1, gridBagConstraints);

        contourStepSizeLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        contourStepSizeLabel.setText("contourStepSize");
        contourStepSizeLabel.setToolTipText("If contourStepSize is non-zero, also render all isosurfaces that are multiples of that step size from initial surface value. Hint: contourStepSize can be negative so that steppping can proceed in a negative direction.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 50, 3, 3);
        add(contourStepSizeLabel, gridBagConstraints);

        contourStepSizeTF.setToolTipText("If contourStepSize is non-zero, also render all isosurfaces that are multiples of that step size from initial surface value. Hint: contourStepSize can be negative so that steppping can proceed in a negative direction.");
        contourStepSizeTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contourStepSizeTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(contourStepSizeTF, gridBagConstraints);

        surfaceToleranceLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        surfaceToleranceLabel.setText("surfaceTolerance");
        surfaceToleranceLabel.setToolTipText("Threshold for gradient magnitude for voxel inolusion in isosurface. Hint: contained X3DTexture3DNode gradients field can provide explicit per-voxel gradient direction information for determining surface boundaries.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 50, 3, 3);
        add(surfaceToleranceLabel, gridBagConstraints);

        surfaceToleranceTF.setToolTipText("Threshold for gradient magnitude for voxel inolusion in isosurface. Hint: contained X3DTexture3DNode gradients field can provide explicit per-voxel gradient direction information for determining surface boundaries.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(surfaceToleranceTF, gridBagConstraints);

        surfaceValuesLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        surfaceValuesLabel.setText("surfaceValues");
        surfaceValuesLabel.setToolTipText("If surfaceValues has one value defined, render corresponding isosurface plus any isosurfaces based on contourStepSize.  If surfaceValues has more than one value defined, ignore contourStepSize and render surfaces corresponding to listed surfaceValues.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 50, 3, 3);
        add(surfaceValuesLabel, gridBagConstraints);

        surfaceValuesTF.setToolTipText("actual-size dimension X value");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(surfaceValuesTF, gridBagConstraints);

        dimensionsLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        dimensionsLabel.setText("dimensions");
        dimensionsLabel.setToolTipText("actual-size X-Y-Z dimensions of volume data in local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 50, 3, 3);
        add(dimensionsLabel, gridBagConstraints);

        dimensionsXTF.setToolTipText("If surfaceValues has one value defined, render corresponding isosurface plus any isosurfaces based on contourStepSize.  If surfaceValues has more than one value defined, ignore contourStepSize and render surfaces corresponding to listed surfaceValues.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 60);
        add(dimensionsXTF, gridBagConstraints);

        dimensionsYTF.setToolTipText("actual-size dimension Y value");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dimensionsYTF, gridBagConstraints);

        dimensionsZTF.setToolTipText("actual-size dimension Z value");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 60);
        add(dimensionsZTF, gridBagConstraints);

        bboxCenterLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        bboxCenterLabel.setText("bboxCenter");
        bboxCenterLabel.setToolTipText("position offset from origin of local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 50, 3, 3);
        add(bboxCenterLabel, gridBagConstraints);

        bboxCenterXTF.setToolTipText("position offset from origin of local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterXTF, gridBagConstraints);

        bboxCenterYTF.setToolTipText("position offset from origin of local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterYTF, gridBagConstraints);

        bboxCenterZTF.setToolTipText("position offset from origin of local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 60);
        add(bboxCenterZTF, gridBagConstraints);

        bboxSizeLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        bboxSizeLabel.setText("bboxSize");
        bboxSizeLabel.setToolTipText("automatically calculated, can be specified as an optimization or constraint");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 50, 3, 3);
        add(bboxSizeLabel, gridBagConstraints);

        bboxSizeXTF.setToolTipText("automatically calculated, can be specified as an optimization or constraint");
        bboxSizeXTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bboxSizeXTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridheight = 2;
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
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridheight = 2;
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
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 60);
        add(bboxSizeZTF, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(expandableListSegmentEnabled, gridBagConstraints);

        nodeHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nodeHintPanel.setLayout(new java.awt.GridBagLayout());

        hintLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel1.setText("<html><p align='center'><b>SegmentedVolumeData</b> contains one or more<i>X3DVolumeRenderStyleNode</i> nodes,<br /> an <i>X3DTexture3DNode</i> nodes to contain voxels, <br /> and an <i>X3DTexture3DNode</i> nodes to contain segmentIdentifiers.</p>");
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
        gridBagConstraints.gridy = 10;
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

    private void contourStepSizeTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contourStepSizeTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_contourStepSizeTFActionPerformed
  
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bboxCenterLabel;
    private javax.swing.JTextField bboxCenterXTF;
    private javax.swing.JTextField bboxCenterYTF;
    private javax.swing.JTextField bboxCenterZTF;
    private javax.swing.JLabel bboxSizeLabel;
    private javax.swing.JTextField bboxSizeXTF;
    private javax.swing.JTextField bboxSizeYTF;
    private javax.swing.JTextField bboxSizeZTF;
    private javax.swing.JLabel contourStepSizeLabel;
    private javax.swing.JTextField contourStepSizeTF;
    private org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel1;
    private javax.swing.JLabel dimensionsLabel;
    private javax.swing.JTextField dimensionsXTF;
    private javax.swing.JTextField dimensionsYTF;
    private javax.swing.JTextField dimensionsZTF;
    private org.web3d.x3d.palette.items.ExpandableList expandableListSegmentEnabled;
    private javax.swing.JLabel hintLabel1;
    private javax.swing.JPanel nodeHintPanel;
    private javax.swing.JLabel surfaceToleranceLabel;
    private javax.swing.JTextField surfaceToleranceTF;
    private javax.swing.JLabel surfaceValuesLabel;
    private javax.swing.JTextField surfaceValuesTF;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_SEGMENTEDVOLUMEDATA";
  }

  @Override
  public void unloadInput()
  {
    unLoadDEFUSE();
    
    isoSurfaceVolumeData.setContourStepSize  (contourStepSizeTF.getText().trim());
    isoSurfaceVolumeData.setSurfaceTolerance(surfaceToleranceTF.getText().trim());
    isoSurfaceVolumeData.setSurfaceValues      (surfaceValuesTF.getText().trim().replace(',', ' ').trim().split("\\s"));
    
    isoSurfaceVolumeData.setDimensionsY(dimensionsYTF.getText().trim());
    isoSurfaceVolumeData.setDimensionsZ(dimensionsZTF.getText().trim());
    isoSurfaceVolumeData.setBboxCenterX(bboxCenterXTF.getText().trim());
    isoSurfaceVolumeData.setBboxCenterY(bboxCenterYTF.getText().trim());
    isoSurfaceVolumeData.setBboxCenterZ(bboxCenterZTF.getText().trim());
    isoSurfaceVolumeData.setBboxSizeX(bboxSizeXTF.getText().trim());
    isoSurfaceVolumeData.setBboxSizeY(bboxSizeYTF.getText().trim());
    isoSurfaceVolumeData.setBboxSizeZ(bboxSizeZTF.getText().trim());

    isoSurfaceVolumeData.setInsertCommas    (expandableListSegmentEnabled.isInsertCommasSet());
    isoSurfaceVolumeData.setInsertLineBreaks(expandableListSegmentEnabled.isInsertLineBreaksSet());
  }   
}
