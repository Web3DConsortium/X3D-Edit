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
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.HelpCtx;
import org.web3d.x3d.NsidedCapsule;
import org.web3d.x3d.NsidedPolygon;
import org.web3d.x3d.RoundedRectangle;
import org.web3d.x3d.types.X3DPrimitiveTypes;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * SHAPECustomizer.java
 * Created on August 15, 2007, 2:25 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class SHAPECustomizer extends BaseCustomizer
{
  private final SHAPE shape;

  private float       height = 1.0f;
  private float       radius = 1.0f;
  private float  innerRadius = 0.5f;
  private float  outerRadius = 1.0f;
  
  private int numberOfPoints = 64;
  private int saveLevelIndex =  0;

  private String geometryNodeName = "IndexedFaceSet";

  java.awt.event.ActionEvent event = new java.awt.event.ActionEvent(SHAPECustomizer.this,0,"initialize");

  /** Creates new form SHAPECustomizer
   * @param shape the shape object to customize
   * @param target a text component required by other customizers, but can be
   * null here
   */
  public SHAPECustomizer(SHAPE shape, JTextComponent target)
  {
    super(shape);
    this.shape = shape;

    HelpCtx.setHelpIDString(SHAPECustomizer.this, "SHAPE_ELEM_HELPID");

    shape.setVisualizationSelectionAvailable(true); // must precede initComponents() interface initialization
    shape.setVisualizationTooltip("Add wireframe Box and axes to show boundingBox center and size (if defined)");

    initComponents();

    // can be the shape field of CADPart
    super.getDEFUSEpanel().setContainerFieldChoices(SHAPE_ATTR_CONTAINERFIELD_CHOICES, SHAPE_ATTR_CONTAINERFIELD_TOOLTIPS);
    // DEFUSEpanel initialization must NOT be repeated or else array of choices will be overwritten

    if (shape.getContent().trim().isEmpty())
    {
           noContentRadioButton.setSelected(true);
          newContentRadioButton.setSelected(false);
        priorContentRadioButton.setEnabled(false);
    }
    else
    {
        priorContentRadioButton.setEnabled(true);
        priorContentRadioButton.setSelected(true);
        priorContentRadioButton.setToolTipText(shape.getContent());

        priorContentRadioButtonActionPerformed (event);
    }
    // re-initialize selections when re-opening panel
    setGeometryTypeButtonGroupEnabled(false);
    polygonsRadioButton.setSelected(true);
    
    geometryPanel.setEnabled(false);
    geometryButtonGroup.clearSelection();
    numberOfPointsTextField.setEnabled(false);
            heightTextField.setEnabled(false);
            radiusTextField.setEnabled(false);
             depthTextField.setEnabled(false);
              levelComboBox.setEnabled(false);

    bboxCenterXTF.setText(shape.getBboxCenterX());
    bboxCenterYTF.setText(shape.getBboxCenterY());
    bboxCenterZTF.setText(shape.getBboxCenterZ());

    bboxSizeXTF.setText(shape.getBboxSizeX());
    bboxSizeYTF.setText(shape.getBboxSizeY());
    bboxSizeZTF.setText(shape.getBboxSizeZ());

            heightTextField.setText(String.valueOf(height));
            radiusTextField.setText(String.valueOf(radius));
    numberOfPointsTextField.setText(String.valueOf(numberOfPoints));
    
    levelComboBox.setSelectedIndex(saveLevelIndex);
    
    additionalGeometriesComboBox.setVisible(false); // TODO implement

    // TODO check parent, if CADFace then warn regarding containerField='shape'
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
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        childContentButtonGroup = new javax.swing.ButtonGroup();
        geometryButtonGroup = new javax.swing.ButtonGroup();
        geometryTypeButtonGroup = new javax.swing.ButtonGroup();
        dEFUSEpanel = getDEFUSEpanel();
        contentSelectionPanel = new javax.swing.JPanel();
        contentSelectionLabel = new javax.swing.JLabel();
        newContentRadioButton = new javax.swing.JRadioButton();
        priorContentRadioButton = new javax.swing.JRadioButton();
        noContentRadioButton = new javax.swing.JRadioButton();
        geometryPanel = new javax.swing.JPanel();
        geometry3dNodeLabel = new javax.swing.JLabel();
        boxRadioButton = new javax.swing.JRadioButton();
        coneRadioButton = new javax.swing.JRadioButton();
        cylinderRadioButton = new javax.swing.JRadioButton();
        sphereRadioButton = new javax.swing.JRadioButton();
        textRadioButton = new javax.swing.JRadioButton();
        textPlusTransparentBoxRadioButton = new javax.swing.JRadioButton();
        axisLinesRgbRadioButton = new javax.swing.JRadioButton();
        verticalSeparator1 = new javax.swing.JSeparator();
        elevationGridRadioButton = new javax.swing.JRadioButton();
        extrusionRadioButton = new javax.swing.JRadioButton();
        indexedFaceSetRadioButton = new javax.swing.JRadioButton();
        indexedLineSetRadioButton = new javax.swing.JRadioButton();
        lineSetRadioButton = new javax.swing.JRadioButton();
        pointSetRadioButton = new javax.swing.JRadioButton();
        verticalSeparator2 = new javax.swing.JSeparator();
        capsuleRadioButton = new javax.swing.JRadioButton();
        conicalPolygonRadioButton = new javax.swing.JRadioButton();
        cylindricalPolygonRadioButton = new javax.swing.JRadioButton();
        dodecahedronRadioButton = new javax.swing.JRadioButton();
        hemisphereRadioButton = new javax.swing.JRadioButton();
        icosahedronRadioButton = new javax.swing.JRadioButton();
        additionalGeometriesComboBox = new javax.swing.JComboBox();
        jSeparator1 = new javax.swing.JSeparator();
        propertiesLabel = new javax.swing.JLabel();
        verticalSeparator3 = new javax.swing.JSeparator();
        numberOfPointsLabel = new javax.swing.JLabel();
        numberOfPointsTextField = new javax.swing.JTextField();
        heightLabel = new javax.swing.JLabel();
        heightTextField = new javax.swing.JTextField();
        radiusLabel = new javax.swing.JLabel();
        radiusTextField = new javax.swing.JTextField();
        depthLabel = new javax.swing.JLabel();
        depthTextField = new javax.swing.JTextField();
        levelLabel = new javax.swing.JLabel();
        levelComboBox = new javax.swing.JComboBox();
        geometryTypeLabel = new javax.swing.JLabel();
        primitivesRadioButton = new javax.swing.JRadioButton();
        polygonsRadioButton = new javax.swing.JRadioButton();
        linesRadioButton = new javax.swing.JRadioButton();
        pointsRadioButton = new javax.swing.JRadioButton();
        roundedRectangleRadioButton = new javax.swing.JRadioButton();
        tetrahedronRadioButton = new javax.swing.JRadioButton();
        verticalSeparator4 = new javax.swing.JSeparator();
        geometry2dNodeLabel = new javax.swing.JLabel();
        arc2dRadioButton = new javax.swing.JRadioButton();
        arcClose2dRadioButton = new javax.swing.JRadioButton();
        circle2dRadioButton = new javax.swing.JRadioButton();
        disk2dRadioButton = new javax.swing.JRadioButton();
        polyline2dRadioButton = new javax.swing.JRadioButton();
        polypoint2dRadioButton = new javax.swing.JRadioButton();
        rectangle2dRadioButton = new javax.swing.JRadioButton();
        roundedRectangle2dRadioButton = new javax.swing.JRadioButton();
        triangleSet2dRadioButton = new javax.swing.JRadioButton();
        eventHintPanel = new javax.swing.JPanel();
        hintLabel = new javax.swing.JLabel();
        bboxPanel = new javax.swing.JPanel();
        bboxCenterLabel = new javax.swing.JLabel();
        bboxCenterXTF = new javax.swing.JTextField();
        bboxCenterYTF = new javax.swing.JTextField();
        bboxCenterZTF = new javax.swing.JTextField();
        bboxSizeLabel = new javax.swing.JLabel();
        bboxSizeXTF = new javax.swing.JTextField();
        bboxSizeYTF = new javax.swing.JTextField();
        bboxSizeZTF = new javax.swing.JTextField();

        setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel, gridBagConstraints);

        contentSelectionPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        contentSelectionPanel.setLayout(new java.awt.GridBagLayout());

        contentSelectionLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        contentSelectionLabel.setText("child content");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        contentSelectionPanel.add(contentSelectionLabel, gridBagConstraints);

        childContentButtonGroup.add(newContentRadioButton);
        newContentRadioButton.setText("new nodes");
        newContentRadioButton.setToolTipText("Select new geometry from choices at right");
        newContentRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newContentRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        contentSelectionPanel.add(newContentRadioButton, gridBagConstraints);

        childContentButtonGroup.add(priorContentRadioButton);
        priorContentRadioButton.setSelected(true);
        priorContentRadioButton.setText("prior nodes");
        priorContentRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                priorContentRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        contentSelectionPanel.add(priorContentRadioButton, gridBagConstraints);

        childContentButtonGroup.add(noContentRadioButton);
        noContentRadioButton.setText("no Content");
        noContentRadioButton.setToolTipText("empty Shape");
        noContentRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noContentRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        contentSelectionPanel.add(noContentRadioButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 13, 3, 3);
        add(contentSelectionPanel, gridBagConstraints);

        geometryPanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        geometryPanel.setToolTipText("");
        geometryPanel.setLayout(new java.awt.GridBagLayout());

        geometry3dNodeLabel.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        geometry3dNodeLabel.setText("3D geometry");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(geometry3dNodeLabel, gridBagConstraints);

        geometryButtonGroup.add(boxRadioButton);
        boxRadioButton.setText("Box");
        boxRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boxRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(boxRadioButton, gridBagConstraints);

        geometryButtonGroup.add(coneRadioButton);
        coneRadioButton.setText("Cone");
        coneRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coneRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(coneRadioButton, gridBagConstraints);

        geometryButtonGroup.add(cylinderRadioButton);
        cylinderRadioButton.setText("Cylinder");
        cylinderRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cylinderRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(cylinderRadioButton, gridBagConstraints);

        geometryButtonGroup.add(sphereRadioButton);
        sphereRadioButton.setText("Sphere");
        sphereRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sphereRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(sphereRadioButton, gridBagConstraints);

        geometryButtonGroup.add(textRadioButton);
        textRadioButton.setText("Text");
        textRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(textRadioButton, gridBagConstraints);

        geometryButtonGroup.add(textPlusTransparentBoxRadioButton);
        textPlusTransparentBoxRadioButton.setText("<html><p>Selectable <br /> Text </p>");
        textPlusTransparentBoxRadioButton.setVerticalTextPosition(javax.swing.SwingConstants.TOP);
        textPlusTransparentBoxRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textPlusTransparentBoxRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        geometryPanel.add(textPlusTransparentBoxRadioButton, gridBagConstraints);

        geometryButtonGroup.add(axisLinesRgbRadioButton);
        axisLinesRgbRadioButton.setText("Axis Lines");
        axisLinesRgbRadioButton.setToolTipText("RGB lines showing XYZ axes");
        axisLinesRgbRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                axisLinesRgbRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        geometryPanel.add(axisLinesRgbRadioButton, gridBagConstraints);

        verticalSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 3);
        geometryPanel.add(verticalSeparator1, gridBagConstraints);

        geometryButtonGroup.add(elevationGridRadioButton);
        elevationGridRadioButton.setText("ElevationGrid");
        elevationGridRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                elevationGridRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(elevationGridRadioButton, gridBagConstraints);

        geometryButtonGroup.add(extrusionRadioButton);
        extrusionRadioButton.setText("Extrusion");
        extrusionRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                extrusionRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(extrusionRadioButton, gridBagConstraints);

        geometryButtonGroup.add(indexedFaceSetRadioButton);
        indexedFaceSetRadioButton.setText("IndexedFaceSet");
        indexedFaceSetRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                indexedFaceSetRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(indexedFaceSetRadioButton, gridBagConstraints);

        geometryButtonGroup.add(indexedLineSetRadioButton);
        indexedLineSetRadioButton.setText("IndexedLineSet");
        indexedLineSetRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                indexedLineSetRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(indexedLineSetRadioButton, gridBagConstraints);

        geometryButtonGroup.add(lineSetRadioButton);
        lineSetRadioButton.setText("LineSet");
        lineSetRadioButton.setToolTipText("Line drawn from 3D points");
        lineSetRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lineSetRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(lineSetRadioButton, gridBagConstraints);

        geometryButtonGroup.add(pointSetRadioButton);
        pointSetRadioButton.setText("PointSet");
        pointSetRadioButton.setToolTipText("Set of 3D points");
        pointSetRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pointSetRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(pointSetRadioButton, gridBagConstraints);

        verticalSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 3);
        geometryPanel.add(verticalSeparator2, gridBagConstraints);

        geometryButtonGroup.add(capsuleRadioButton);
        capsuleRadioButton.setText("Capsule");
        capsuleRadioButton.setToolTipText("Two hemispheres connected by a cylinder");
        capsuleRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                capsuleRadioButtonActionPerformed(evt);
            }
        });
        capsuleRadioButton.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                capsuleRadioButtonPropertyChange(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(capsuleRadioButton, gridBagConstraints);

        geometryButtonGroup.add(conicalPolygonRadioButton);
        conicalPolygonRadioButton.setText("Conical n-point Polygon");
        conicalPolygonRadioButton.setToolTipText("Cone, customizable");
        conicalPolygonRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                conicalPolygonRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(conicalPolygonRadioButton, gridBagConstraints);

        geometryButtonGroup.add(cylindricalPolygonRadioButton);
        cylindricalPolygonRadioButton.setText("Cylindrical n-point Polygon");
        cylindricalPolygonRadioButton.setToolTipText("Cylinder, customizable");
        cylindricalPolygonRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cylindricalPolygonRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(cylindricalPolygonRadioButton, gridBagConstraints);

        geometryButtonGroup.add(dodecahedronRadioButton);
        dodecahedronRadioButton.setText("Dodecahedron");
        dodecahedronRadioButton.setToolTipText("Platonic solid");
        dodecahedronRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dodecahedronRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(dodecahedronRadioButton, gridBagConstraints);

        geometryButtonGroup.add(hemisphereRadioButton);
        hemisphereRadioButton.setText("Hemisphere");
        hemisphereRadioButton.setToolTipText("Top half of sphere");
        hemisphereRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hemisphereRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(hemisphereRadioButton, gridBagConstraints);

        geometryButtonGroup.add(icosahedronRadioButton);
        icosahedronRadioButton.setText("Icosahedron");
        icosahedronRadioButton.setToolTipText("Spherical tessellation using triangles");
        icosahedronRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                icosahedronRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(icosahedronRadioButton, gridBagConstraints);

        additionalGeometriesComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Additional choices", "Dodecahedron", "Torus" }));
        additionalGeometriesComboBox.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 24, 3, 3);
        geometryPanel.add(additionalGeometriesComboBox, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 0, 0);
        geometryPanel.add(jSeparator1, gridBagConstraints);

        propertiesLabel.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        propertiesLabel.setText("properties");
        propertiesLabel.setMaximumSize(new java.awt.Dimension(70, 16));
        propertiesLabel.setMinimumSize(new java.awt.Dimension(70, 16));
        propertiesLabel.setPreferredSize(new java.awt.Dimension(70, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(propertiesLabel, gridBagConstraints);

        verticalSeparator3.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 3);
        geometryPanel.add(verticalSeparator3, gridBagConstraints);

        numberOfPointsLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        numberOfPointsLabel.setText("# points");
        numberOfPointsLabel.setToolTipText("# points around perimeter");
        numberOfPointsLabel.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(numberOfPointsLabel, gridBagConstraints);

        numberOfPointsTextField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        numberOfPointsTextField.setText("64");
        numberOfPointsTextField.setToolTipText(org.openide.util.NbBundle.getMessage(SHAPECustomizer.class, "ExpandableList.numberOfPointsTextField.toolTipText")); // NOI18N
        numberOfPointsTextField.setEnabled(false);
        numberOfPointsTextField.setMinimumSize(new java.awt.Dimension(60, 20));
        numberOfPointsTextField.setPreferredSize(new java.awt.Dimension(60, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(numberOfPointsTextField, gridBagConstraints);

        heightLabel.setText("height");
        heightLabel.setToolTipText("meters");
        heightLabel.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(heightLabel, gridBagConstraints);

        heightTextField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        heightTextField.setText("1");
        heightTextField.setToolTipText("meters");
        heightTextField.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(heightTextField, gridBagConstraints);

        radiusLabel.setText("radius");
        radiusLabel.setToolTipText("meters");
        radiusLabel.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(radiusLabel, gridBagConstraints);

        radiusTextField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        radiusTextField.setText("1");
        radiusTextField.setToolTipText("meters");
        radiusTextField.setEnabled(false);
        radiusTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radiusTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(radiusTextField, gridBagConstraints);

        depthLabel.setText("depth");
        depthLabel.setToolTipText("meters");
        depthLabel.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(depthLabel, gridBagConstraints);

        depthTextField.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        depthTextField.setText("1");
        depthTextField.setToolTipText("meters");
        depthTextField.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(depthTextField, gridBagConstraints);

        levelLabel.setText("level");
        levelLabel.setToolTipText("level of refinement");
        levelLabel.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(levelLabel, gridBagConstraints);

        levelComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "0", "1", "2", "3", "4", "5" }));
        levelComboBox.setToolTipText("level of refinement");
        levelComboBox.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(levelComboBox, gridBagConstraints);

        geometryTypeLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        geometryTypeLabel.setText("type");
        geometryTypeLabel.setToolTipText("geometry type");
        geometryTypeLabel.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(geometryTypeLabel, gridBagConstraints);

        geometryTypeButtonGroup.add(primitivesRadioButton);
        primitivesRadioButton.setSelected(true);
        primitivesRadioButton.setText("primitive");
        primitivesRadioButton.setToolTipText("X3D primitive geometry");
        primitivesRadioButton.setEnabled(false);
        primitivesRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                primitivesRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(primitivesRadioButton, gridBagConstraints);

        geometryTypeButtonGroup.add(polygonsRadioButton);
        polygonsRadioButton.setText("polygons");
        polygonsRadioButton.setToolTipText("triangulation");
        polygonsRadioButton.setEnabled(false);
        polygonsRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                polygonsRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(polygonsRadioButton, gridBagConstraints);

        geometryTypeButtonGroup.add(linesRadioButton);
        linesRadioButton.setText("lines");
        linesRadioButton.setToolTipText("wireframe");
        linesRadioButton.setEnabled(false);
        linesRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                linesRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(linesRadioButton, gridBagConstraints);

        geometryTypeButtonGroup.add(pointsRadioButton);
        pointsRadioButton.setText("points");
        pointsRadioButton.setToolTipText("individual points");
        pointsRadioButton.setEnabled(false);
        pointsRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pointsRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(pointsRadioButton, gridBagConstraints);

        geometryButtonGroup.add(roundedRectangleRadioButton);
        roundedRectangleRadioButton.setText("Rounded Rectangle");
        roundedRectangleRadioButton.setToolTipText("round off corners of rectangular box");
        roundedRectangleRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedRectangleRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(roundedRectangleRadioButton, gridBagConstraints);

        geometryButtonGroup.add(tetrahedronRadioButton);
        tetrahedronRadioButton.setText("Tetrahedron");
        tetrahedronRadioButton.setToolTipText("Tetrahedron is a triangular pyramidal polyhedron");
        tetrahedronRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tetrahedronRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(tetrahedronRadioButton, gridBagConstraints);

        verticalSeparator4.setForeground(new java.awt.Color(0, 0, 0));
        verticalSeparator4.setOrientation(javax.swing.SwingConstants.VERTICAL);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 0, 3);
        geometryPanel.add(verticalSeparator4, gridBagConstraints);

        geometry2dNodeLabel.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        geometry2dNodeLabel.setText("2D geometry");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(geometry2dNodeLabel, gridBagConstraints);

        geometryButtonGroup.add(arc2dRadioButton);
        arc2dRadioButton.setText("Arc2D");
        arc2dRadioButton.setToolTipText("Arc2D defines a linear circular arc");
        arc2dRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                arc2dRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(arc2dRadioButton, gridBagConstraints);

        geometryButtonGroup.add(arcClose2dRadioButton);
        arcClose2dRadioButton.setText("ArcClose2D");
        arcClose2dRadioButton.setToolTipText("Arc2D defines a linear circular arc, closed by PIE or CHORD line segments");
        arcClose2dRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                arcClose2dRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(arcClose2dRadioButton, gridBagConstraints);

        geometryButtonGroup.add(circle2dRadioButton);
        circle2dRadioButton.setText("Circle2D");
        circle2dRadioButton.setToolTipText("Circle2D defines a linear circle with center (0,0) in X-Y plane");
        circle2dRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                circle2dRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(circle2dRadioButton, gridBagConstraints);

        geometryButtonGroup.add(disk2dRadioButton);
        disk2dRadioButton.setText("Disk2D");
        disk2dRadioButton.setToolTipText("Disk2D is a filled (or partially filled) planar circle in X-Y plane");
        disk2dRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disk2dRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(disk2dRadioButton, gridBagConstraints);

        geometryButtonGroup.add(polyline2dRadioButton);
        polyline2dRadioButton.setText("Polyline2D");
        polyline2dRadioButton.setToolTipText("Polyline2D defines a connected set of  line segments in X-Y plane");
        polyline2dRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                polyline2dRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(polyline2dRadioButton, gridBagConstraints);

        geometryButtonGroup.add(polypoint2dRadioButton);
        polypoint2dRadioButton.setText("Polypoint2D");
        polypoint2dRadioButton.setToolTipText("Polypoint2D defines a set of 2D points in X-Y plane");
        polypoint2dRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                polypoint2dRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(polypoint2dRadioButton, gridBagConstraints);

        geometryButtonGroup.add(rectangle2dRadioButton);
        rectangle2dRadioButton.setText("Rectangle2D");
        rectangle2dRadioButton.setToolTipText("Rectangle2D defines a filled 2D rectangle in X-Y plane");
        rectangle2dRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rectangle2dRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(rectangle2dRadioButton, gridBagConstraints);

        geometryButtonGroup.add(roundedRectangle2dRadioButton);
        roundedRectangle2dRadioButton.setText("RoundedRectangle2D");
        roundedRectangle2dRadioButton.setToolTipText("Rectangle2D defines a filled 2D rectangle in X-Y plane");
        roundedRectangle2dRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                roundedRectangle2dRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(roundedRectangle2dRadioButton, gridBagConstraints);

        geometryButtonGroup.add(triangleSet2dRadioButton);
        triangleSet2dRadioButton.setText("TriangleSet2D");
        triangleSet2dRadioButton.setToolTipText("TriangleSet2D defines a set of filled 2D triangles in X-Y plane");
        triangleSet2dRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                triangleSet2dRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geometryPanel.add(triangleSet2dRadioButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(geometryPanel, gridBagConstraints);

        eventHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eventHintPanel.setMinimumSize(new java.awt.Dimension(50, 25));
        eventHintPanel.setLayout(new java.awt.GridBagLayout());

        hintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel.setText("<html><p align=\"center\"><b>Shape</b> contains a single geometry node and a single <b>Appearance</b> node.<br/>Animation hint: replacement child nodes can be <b>ROUTE</b>d to <i>new_geometry</i> and <i>new_appearance</i>.</p>");
        hintLabel.setToolTipText("close this panel to add children nodes");
        hintLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 3, 3, 3);
        eventHintPanel.add(hintLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 0, 3);
        add(eventHintPanel, gridBagConstraints);

        bboxPanel.setLayout(new java.awt.GridBagLayout());

        bboxCenterLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        bboxCenterLabel.setText("bboxCenter");
        bboxCenterLabel.setMaximumSize(null);
        bboxCenterLabel.setMinimumSize(new java.awt.Dimension(75, 16));
        bboxCenterLabel.setPreferredSize(new java.awt.Dimension(75, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        bboxPanel.add(bboxCenterLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.weightx = 0.3333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        bboxPanel.add(bboxCenterXTF, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.weightx = 0.3333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        bboxPanel.add(bboxCenterYTF, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.weightx = 0.3333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        bboxPanel.add(bboxCenterZTF, gridBagConstraints);

        bboxSizeLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        bboxSizeLabel.setText("bboxSize");
        bboxSizeLabel.setMaximumSize(new java.awt.Dimension(75, 16));
        bboxSizeLabel.setMinimumSize(new java.awt.Dimension(75, 16));
        bboxSizeLabel.setPreferredSize(new java.awt.Dimension(75, 16));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 30;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        bboxPanel.add(bboxSizeLabel, gridBagConstraints);

        bboxSizeXTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bboxSizeXTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.weightx = 0.3333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        bboxPanel.add(bboxSizeXTF, gridBagConstraints);

        bboxSizeYTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bboxSizeYTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.weightx = 0.3333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        bboxPanel.add(bboxSizeYTF, gridBagConstraints);

        bboxSizeZTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bboxSizeZTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.weightx = 0.3333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        bboxPanel.add(bboxSizeZTF, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxPanel, gridBagConstraints);
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

    private void noContentRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noContentRadioButtonActionPerformed
            geometryButtonGroup.clearSelection();
    }//GEN-LAST:event_noContentRadioButtonActionPerformed

    private void conicalPolygonRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conicalPolygonRadioButtonActionPerformed
        newContentRadioButton.setSelected(true);
        setGeometryTypeButtonGroupEnabled(true);
         primitivesRadioButton.setEnabled(true);
       numberOfPointsTextField.setEnabled(true);
               heightTextField.setEnabled(true);
               radiusTextField.setEnabled(true);
                depthTextField.setEnabled(false);
                 levelComboBox.setEnabled(false);
                 levelComboBox.setEditable(false); // grey background
           numberOfPointsLabel.setEnabled(true);
                   heightLabel.setEnabled(true);
                   radiusLabel.setEnabled(true);
                    depthLabel.setEnabled(false);
                    levelLabel.setEnabled(false);
                   radiusLabelRenameWidth(false);
     if (primitivesRadioButton.isSelected())
           polygonsRadioButton.setSelected(true);
        dEFUSEpanel.setDefaultDEFname("ConicalPolygonShape");
        computeBoundingBox ();
    }//GEN-LAST:event_conicalPolygonRadioButtonActionPerformed
    private void setGeometryTypeButtonGroupEnabled (boolean value)
    {
        polygonsRadioButton.setEnabled(value);
           linesRadioButton.setEnabled(value);
          pointsRadioButton.setEnabled(value);
    }
    private void cylindricalPolygonRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cylindricalPolygonRadioButtonActionPerformed
        newContentRadioButton.setSelected(true);
        setGeometryTypeButtonGroupEnabled(true);
         primitivesRadioButton.setEnabled(true);
       numberOfPointsTextField.setEnabled(true);
               heightTextField.setEnabled(true);
               radiusTextField.setEnabled(true);
                depthTextField.setEnabled(false);
                 levelComboBox.setEnabled(false);
                 levelComboBox.setEditable(false); // grey background
           numberOfPointsLabel.setEnabled(true);
                   heightLabel.setEnabled(true);
                   radiusLabel.setEnabled(true);
                    depthLabel.setEnabled(false);
                    levelLabel.setEnabled(false);
                   radiusLabelRenameWidth(false);
     if (primitivesRadioButton.isSelected())
           polygonsRadioButton.setSelected(true);
        dEFUSEpanel.setDefaultDEFname("CylindricalPolygonShape");
        computeBoundingBox ();
    }//GEN-LAST:event_cylindricalPolygonRadioButtonActionPerformed

    private void newContentRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newContentRadioButtonActionPerformed
        geometryPanel.setEnabled(newContentRadioButton.isSelected());
    }//GEN-LAST:event_newContentRadioButtonActionPerformed

    private void boxRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boxRadioButtonActionPerformed
         newContentRadioButton.setSelected(true);
        setGeometryTypeButtonGroupEnabled(false);
         primitivesRadioButton.setSelected(true);
         primitivesRadioButton.setEnabled(true);
       numberOfPointsTextField.setEnabled(false);
               heightTextField.setEnabled(true);
               radiusTextField.setEnabled(true);
                depthTextField.setEnabled(true);
                 levelComboBox.setEnabled(false);
                 levelComboBox.setEditable(false); // grey background
           numberOfPointsLabel.setEnabled(false);
                   heightLabel.setEnabled(true);
                   radiusLabel.setEnabled(true);
                    depthLabel.setEnabled(true);
                    levelLabel.setEnabled(false);
                   radiusLabelRenameWidth(true);
        dEFUSEpanel.setDefaultDEFname("BoxShape");
        computeBoundingBox ();
    }//GEN-LAST:event_boxRadioButtonActionPerformed

    private void coneRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coneRadioButtonActionPerformed
         newContentRadioButton.setSelected(true);
         setGeometryTypeButtonGroupEnabled(true);
         primitivesRadioButton.setSelected(true);
         primitivesRadioButton.setEnabled (true);
       numberOfPointsTextField.setEnabled(false);
               heightTextField.setEnabled(true);
               radiusTextField.setEnabled(true);
                depthTextField.setEnabled(false);
                 levelComboBox.setEnabled(false);
                 levelComboBox.setEditable(false); // grey background
           numberOfPointsLabel.setEnabled(false);
                   heightLabel.setEnabled(true);
                   radiusLabel.setEnabled(true);
                    depthLabel.setEnabled(false);
                    levelLabel.setEnabled(false);
                   radiusLabelRenameWidth(false);
        dEFUSEpanel.setDefaultDEFname("ConeShape");
        computeBoundingBox ();
    }//GEN-LAST:event_coneRadioButtonActionPerformed

    private void cylinderRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cylinderRadioButtonActionPerformed
         newContentRadioButton.setSelected(true);
         setGeometryTypeButtonGroupEnabled(true);
         primitivesRadioButton.setSelected(true);
         primitivesRadioButton.setEnabled(true);
       numberOfPointsTextField.setEnabled(false);
               heightTextField.setEnabled(true);
               radiusTextField.setEnabled(true);
                depthTextField.setEnabled(false);
                 levelComboBox.setEnabled(false);
                 levelComboBox.setEditable(false); // grey background
           numberOfPointsLabel.setEnabled(false);
                   heightLabel.setEnabled(true);
                   radiusLabel.setEnabled(true);
                    depthLabel.setEnabled(false);
                    levelLabel.setEnabled(false);
                   radiusLabelRenameWidth(false);
        dEFUSEpanel.setDefaultDEFname("CylinderShape");
        computeBoundingBox ();
    }//GEN-LAST:event_cylinderRadioButtonActionPerformed

    private void sphereRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sphereRadioButtonActionPerformed
         newContentRadioButton.setSelected(true);
         setGeometryTypeButtonGroupEnabled(true);
         primitivesRadioButton.setSelected(true);
         primitivesRadioButton.setEnabled(true);
       numberOfPointsTextField.setEnabled(false);
               heightTextField.setEnabled(false);
               radiusTextField.setEnabled(true);
                depthTextField.setEnabled(false);
                 levelComboBox.setEnabled(false);
                 levelComboBox.setEditable(false); // grey background
           numberOfPointsLabel.setEnabled(false);
                   heightLabel.setEnabled(false);
                   radiusLabel.setEnabled(true);
                    depthLabel.setEnabled(false);
                    levelLabel.setEnabled(false);
                   radiusLabelRenameWidth(false);
        dEFUSEpanel.setDefaultDEFname("SphereShape");
        computeBoundingBox ();
    }//GEN-LAST:event_sphereRadioButtonActionPerformed

    private void textRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textRadioButtonActionPerformed
         newContentRadioButton.setSelected(true);
        setGeometryTypeButtonGroupEnabled(false);
         primitivesRadioButton.setSelected(true);
         primitivesRadioButton.setEnabled(true);
       numberOfPointsTextField.setEnabled(false);
               heightTextField.setEnabled(true);
               heightTextField.setText("1");
               radiusTextField.setEnabled(false);
                depthTextField.setEnabled(false);
                 levelComboBox.setEnabled(false);
                 levelComboBox.setEditable(false); // grey background
           numberOfPointsLabel.setEnabled(false);
                   heightLabel.setEnabled(true);
                   radiusLabel.setEnabled(false);
                    depthLabel.setEnabled(false);
                    levelLabel.setEnabled(false);
                   radiusLabelRenameWidth(false);
        dEFUSEpanel.setDefaultDEFname("TextShape");
        computeBoundingBox ();
    }//GEN-LAST:event_textRadioButtonActionPerformed

    private void elevationGridRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_elevationGridRadioButtonActionPerformed
         newContentRadioButton.setSelected(true);
         setGeometryTypeButtonGroupEnabled(true);
         primitivesRadioButton.setSelected(true);
         primitivesRadioButton.setEnabled(true);
       numberOfPointsTextField.setEnabled(false);
               heightTextField.setEnabled(false);
               radiusTextField.setEnabled(false);
                depthTextField.setEnabled(false);
                 levelComboBox.setEnabled(false);
                 levelComboBox.setEditable(false); // grey background
           numberOfPointsLabel.setEnabled(false);
                   heightLabel.setEnabled(false);
                   radiusLabel.setEnabled(false);
                    depthLabel.setEnabled(false);
                    levelLabel.setEnabled(false);
                   radiusLabelRenameWidth(false);
        dEFUSEpanel.setDefaultDEFname("ElevationGridShape");
        computeBoundingBox ();
    }//GEN-LAST:event_elevationGridRadioButtonActionPerformed

    private void extrusionRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_extrusionRadioButtonActionPerformed
         newContentRadioButton.setSelected(true);
         setGeometryTypeButtonGroupEnabled(true);
         primitivesRadioButton.setSelected(true);
       numberOfPointsTextField.setEnabled(false);
               heightTextField.setEnabled(false);
               radiusTextField.setEnabled(false);
                depthTextField.setEnabled(false);
                 levelComboBox.setEnabled(false);
                 levelComboBox.setEditable(false); // grey background
           numberOfPointsLabel.setEnabled(false);
                   heightLabel.setEnabled(false);
                   radiusLabel.setEnabled(false);
                    depthLabel.setEnabled(false);
                    levelLabel.setEnabled(false);
                   radiusLabelRenameWidth(false);
        dEFUSEpanel.setDefaultDEFname("ExtrusionShape");
        computeBoundingBox ();
    }//GEN-LAST:event_extrusionRadioButtonActionPerformed

    private void indexedFaceSetRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_indexedFaceSetRadioButtonActionPerformed
         newContentRadioButton.setSelected(true);
         setGeometryTypeButtonGroupEnabled(true);
         primitivesRadioButton.setSelected(true);
         primitivesRadioButton.setEnabled(true);
       numberOfPointsTextField.setEnabled(false);
               heightTextField.setEnabled(false);
               radiusTextField.setEnabled(false);
                depthTextField.setEnabled(false);
                 levelComboBox.setEnabled(false);
                 levelComboBox.setEditable(false); // grey background
           numberOfPointsLabel.setEnabled(false);
                   heightLabel.setEnabled(false);
                   radiusLabel.setEnabled(false);
                    depthLabel.setEnabled(false);
                    levelLabel.setEnabled(false);
                   radiusLabelRenameWidth(false);
        dEFUSEpanel.setDefaultDEFname("IndexedFaceSetShape");
        computeBoundingBox ();
    }//GEN-LAST:event_indexedFaceSetRadioButtonActionPerformed

    private void indexedLineSetRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_indexedLineSetRadioButtonActionPerformed
         newContentRadioButton.setSelected(true);
         setGeometryTypeButtonGroupEnabled(true);
         primitivesRadioButton.setSelected(true);
         primitivesRadioButton.setEnabled(true);
       numberOfPointsTextField.setEnabled(false);
               heightTextField.setEnabled(false);
               radiusTextField.setEnabled(false);
                depthTextField.setEnabled(false);
                 levelComboBox.setEnabled(false);
                 levelComboBox.setEditable(false); // grey background
           numberOfPointsLabel.setEnabled(false);
                   heightLabel.setEnabled(false);
                   radiusLabel.setEnabled(false);
                    depthLabel.setEnabled(false);
                    levelLabel.setEnabled(false);
                   radiusLabelRenameWidth(false);
        dEFUSEpanel.setDefaultDEFname("IndexedLineSetShape");
        computeBoundingBox ();
    }//GEN-LAST:event_indexedLineSetRadioButtonActionPerformed

    private void lineSetRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lineSetRadioButtonActionPerformed
         newContentRadioButton.setSelected(true);
         primitivesRadioButton.setSelected(true);
         setGeometryTypeButtonGroupEnabled(true);
         primitivesRadioButton.setEnabled(true);
       numberOfPointsTextField.setEnabled(false);
               heightTextField.setEnabled(false);
               radiusTextField.setEnabled(false);
                depthTextField.setEnabled(false);
                 levelComboBox.setEnabled(false);
                 levelComboBox.setEditable(false); // grey background
           numberOfPointsLabel.setEnabled(false);
                   heightLabel.setEnabled(false);
                   radiusLabel.setEnabled(false);
                    depthLabel.setEnabled(false);
                    levelLabel.setEnabled(false);
                   radiusLabelRenameWidth(false);
        dEFUSEpanel.setDefaultDEFname("LineSetShape");
        computeBoundingBox ();
    }//GEN-LAST:event_lineSetRadioButtonActionPerformed

    private void pointSetRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pointSetRadioButtonActionPerformed
         newContentRadioButton.setSelected(true);
         setGeometryTypeButtonGroupEnabled(true);
         primitivesRadioButton.setSelected(true);
         primitivesRadioButton.setEnabled(true);
       numberOfPointsTextField.setEnabled(false);
               heightTextField.setEnabled(false);
               radiusTextField.setEnabled(false);
                depthTextField.setEnabled(false);
                 levelComboBox.setEnabled(false);
                 levelComboBox.setEditable(false); // grey background
           numberOfPointsLabel.setEnabled(false);
                   heightLabel.setEnabled(false);
                   radiusLabel.setEnabled(false);
                    depthLabel.setEnabled(false);
                    levelLabel.setEnabled(false);
                   radiusLabelRenameWidth(false);
        dEFUSEpanel.setDefaultDEFname("PointSetShape");
        computeBoundingBox ();
    }//GEN-LAST:event_pointSetRadioButtonActionPerformed

    private void icosahedronRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_icosahedronRadioButtonActionPerformed
         newContentRadioButton.setSelected(true);
        setGeometryTypeButtonGroupEnabled(true);
         primitivesRadioButton.setEnabled(true);
     if (primitivesRadioButton.isSelected())
           polygonsRadioButton.setSelected(true);
       numberOfPointsTextField.setEnabled(false);
               heightTextField.setEnabled(false);
               radiusTextField.setEnabled(false);
                depthTextField.setEnabled(false);
                 levelComboBox.setEnabled(true);
                 levelComboBox.setEditable(true);
           numberOfPointsLabel.setEnabled(false);
                   heightLabel.setEnabled(false);
                   radiusLabel.setEnabled(false);
                    depthLabel.setEnabled(false);
                    levelLabel.setEnabled(true);
                   radiusLabelRenameWidth(false);
        dEFUSEpanel.setDefaultDEFname("IcosahedronShape");
        computeBoundingBox ();
    }//GEN-LAST:event_icosahedronRadioButtonActionPerformed

    private void primitivesRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_primitivesRadioButtonActionPerformed
        if (icosahedronRadioButton.isSelected())
        {
            sphereRadioButton.setSelected (true);
            sphereRadioButtonActionPerformed(event);
            dEFUSEpanel.setDefaultDEFname("SphereShape");
        }
        if (conicalPolygonRadioButton.isSelected())
        {
            coneRadioButton.setSelected (true);
            coneRadioButtonActionPerformed(event);
            dEFUSEpanel.setDefaultDEFname("ConeShape");
        }
        if (cylindricalPolygonRadioButton.isSelected())
        {
            cylinderRadioButton.setSelected (true);
            cylinderRadioButtonActionPerformed(event);
            dEFUSEpanel.setDefaultDEFname("CylinderShape");
        }
        computeBoundingBox ();
    }//GEN-LAST:event_primitivesRadioButtonActionPerformed

    private void polygonsRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_polygonsRadioButtonActionPerformed
        if (sphereRadioButton.isSelected())
        {
            icosahedronRadioButton.setSelected (true);
            icosahedronRadioButtonActionPerformed(event);
        }
        if (coneRadioButton.isSelected())
        {
            conicalPolygonRadioButton.setSelected (true);
            conicalPolygonRadioButtonActionPerformed(event);
        }
        if (cylinderRadioButton.isSelected())
        {
            cylindricalPolygonRadioButton.setSelected (true);
            cylindricalPolygonRadioButtonActionPerformed(event);
        }
        dEFUSEpanel.setDefaultDEFname("PolygonsShape");
        computeBoundingBox ();
    }//GEN-LAST:event_polygonsRadioButtonActionPerformed

    private void linesRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_linesRadioButtonActionPerformed
        if (sphereRadioButton.isSelected())
        {
            icosahedronRadioButton.setSelected (true);
            icosahedronRadioButtonActionPerformed(event);
        }
        if (coneRadioButton.isSelected())
        {
            conicalPolygonRadioButton.setSelected (true);
            conicalPolygonRadioButtonActionPerformed(event);
        }
        if (cylinderRadioButton.isSelected())
        {
            cylindricalPolygonRadioButton.setSelected (true);
            cylindricalPolygonRadioButtonActionPerformed(event);
        }
        dEFUSEpanel.setDefaultDEFname("LinesShape");
        computeBoundingBox ();
    }//GEN-LAST:event_linesRadioButtonActionPerformed

    private void pointsRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pointsRadioButtonActionPerformed
        if (sphereRadioButton.isSelected())
        {
            icosahedronRadioButton.setSelected (true);
            icosahedronRadioButtonActionPerformed(event);
        }
        if (coneRadioButton.isSelected())
        {
            conicalPolygonRadioButton.setSelected (true);
            conicalPolygonRadioButtonActionPerformed(event);
        }
        if (cylinderRadioButton.isSelected())
        {
            cylindricalPolygonRadioButton.setSelected (true);
            cylindricalPolygonRadioButtonActionPerformed(event);
        }
        dEFUSEpanel.setDefaultDEFname("PointsShape");
        computeBoundingBox ();
    }//GEN-LAST:event_pointsRadioButtonActionPerformed

    private void dodecahedronRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dodecahedronRadioButtonActionPerformed
         newContentRadioButton.setSelected(true);
         setGeometryTypeButtonGroupEnabled(true);
     if (primitivesRadioButton.isSelected())
           polygonsRadioButton.setSelected (true);
         primitivesRadioButton.setEnabled(false);
       numberOfPointsTextField.setEnabled(false);
               heightTextField.setEnabled(false);
               radiusTextField.setEnabled(false);
                depthTextField.setEnabled(false);
                 levelComboBox.setEnabled(false);
                 levelComboBox.setEditable(false); // grey background
           numberOfPointsLabel.setEnabled(false);
                   heightLabel.setEnabled(false);
                   radiusLabel.setEnabled(false);
                    depthLabel.setEnabled(false);
                    levelLabel.setEnabled(false);
                   radiusLabelRenameWidth(false);
        dEFUSEpanel.setDefaultDEFname("DodecahedronShape");
        computeBoundingBox ();
    }//GEN-LAST:event_dodecahedronRadioButtonActionPerformed

    private void capsuleRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_capsuleRadioButtonActionPerformed
         newContentRadioButton.setSelected(true);
        setGeometryTypeButtonGroupEnabled(true);
     if (primitivesRadioButton.isSelected())
           polygonsRadioButton.setSelected (true);
         primitivesRadioButton.setEnabled(false);
       numberOfPointsTextField.setEnabled(true);
               heightTextField.setEnabled(true);
               radiusTextField.setEnabled(true);
                depthTextField.setEnabled(false);
                 levelComboBox.setEnabled(true);
                 levelComboBox.setEditable(true);
           numberOfPointsLabel.setEnabled(true);
                   heightLabel.setEnabled(true);
                   radiusLabel.setEnabled(true);
                    depthLabel.setEnabled(false);
                    levelLabel.setEnabled(false);
                    
     saveLevelIndex = levelComboBox.getSelectedIndex();
                 levelComboBox.setSelectedItem(10);
                   radiusLabelRenameWidth(false);
        dEFUSEpanel.setDefaultDEFname("CapsuleShape");
        computeBoundingBox ();
    }//GEN-LAST:event_capsuleRadioButtonActionPerformed

    private void hemisphereRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hemisphereRadioButtonActionPerformed
         newContentRadioButton.setSelected(true);
        setGeometryTypeButtonGroupEnabled(true);
        if (primitivesRadioButton.isSelected())
              polygonsRadioButton.setSelected (true);
         primitivesRadioButton.setEnabled(false);
       numberOfPointsTextField.setEnabled(false);
               heightTextField.setEnabled(false);
               radiusTextField.setEnabled(false);
                depthTextField.setEnabled(false);
                 levelComboBox.setEnabled(false);
                 levelComboBox.setEditable(false); // grey background
           numberOfPointsLabel.setEnabled(false);
                   heightLabel.setEnabled(false);
                   radiusLabel.setEnabled(false);
                    depthLabel.setEnabled(false);
                    levelLabel.setEnabled(false);
                   radiusLabelRenameWidth(false);
        dEFUSEpanel.setDefaultDEFname("HemisphereShape");
        computeBoundingBox ();
    }//GEN-LAST:event_hemisphereRadioButtonActionPerformed

    private void priorContentRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_priorContentRadioButtonActionPerformed
        geometryButtonGroup.clearSelection();

        if       (shape.getContent().contains("<Box"))
        {
            boxRadioButton.setSelected(true);
            boxRadioButtonActionPerformed(evt);
        }
        else if (shape.getContent().contains("<Cone"))
        {
            coneRadioButton.setSelected(true);
            coneRadioButtonActionPerformed(evt);
        }
        else if (shape.getContent().contains("<Cylinder"))
        {
            cylinderRadioButton.setSelected(true);
            cylinderRadioButtonActionPerformed(evt);
        }
        else if (shape.getContent().contains("<Sphere"))
        {
            sphereRadioButton.setSelected(true);
            sphereRadioButtonActionPerformed(evt);
        }
        else if (shape.getContent().contains("<Text"))
        {
            textRadioButton.setSelected(true);
            textRadioButtonActionPerformed(evt);
        }
        else if (shape.getContent().contains("<ElevationGrid"))
        {
            elevationGridRadioButton.setSelected(true);
            elevationGridRadioButtonActionPerformed(evt);
        }
        else if (shape.getContent().contains("<Extrusion"))
        {
            extrusionRadioButton.setSelected(true);
            extrusionRadioButtonActionPerformed(evt);
        }
        else if (shape.getContent().contains("<IndexedFaceSet"))
        {
            indexedFaceSetRadioButton.setSelected(true);
            indexedFaceSetRadioButtonActionPerformed(evt);
        }
        else if (shape.getContent().contains("<IndexedLineSet"))
        {
            indexedLineSetRadioButton.setSelected(true);
            indexedLineSetRadioButtonActionPerformed(evt);
        }
        else if (shape.getContent().contains("<LineSet"))
        {
            lineSetRadioButton.setSelected(true);
            lineSetRadioButtonActionPerformed(evt);
        }
        else if (shape.getContent().contains("<PointSet"))
        {
            pointSetRadioButton.setSelected(true);
            pointSetRadioButtonActionPerformed(evt);
        }
        else if (shape.getContent().contains("<IndexedFaceSet"))
        {
            indexedFaceSetRadioButton.setSelected(true);
            indexedFaceSetRadioButtonActionPerformed(evt);
        }
        else if (shape.getContent().contains("Capsule"))
        {
            capsuleRadioButton.setSelected(true);
            capsuleRadioButtonActionPerformed(evt);
        }
        else if (shape.getContent().contains("Conical"))
        {
            conicalPolygonRadioButton.setSelected(true);
            conicalPolygonRadioButtonActionPerformed(evt);
        }
        else if (shape.getContent().contains("Cylindrical"))
        {
            cylindricalPolygonRadioButton.setSelected(true);
            cylindricalPolygonRadioButtonActionPerformed(evt);
        }
        else if (shape.getContent().contains("Dodecahedron"))
        {
            dodecahedronRadioButton.setSelected(true);
            dodecahedronRadioButtonActionPerformed(evt);
        }
        else if (shape.getContent().contains("Hemisphere"))
        {
            hemisphereRadioButton.setSelected(true);
            hemisphereRadioButtonActionPerformed(evt);
        }
        else if (shape.getContent().contains("Icosahedron"))
        {
            icosahedronRadioButton.setSelected(true);
            icosahedronRadioButtonActionPerformed(evt);
        }
        else if (shape.getContent().contains("Tetrahedron"))
        {
            tetrahedronRadioButton.setSelected(true);
            tetrahedronRadioButtonActionPerformed(evt);
        }
        else if (shape.getContent().contains("Capsule"))
        {
            capsuleRadioButton.setSelected(true);
            capsuleRadioButtonActionPerformed(evt);
        }

        if      (shape.getContent().contains("level 0"))
                     levelComboBox.setSelectedIndex(0);
        else if (shape.getContent().contains("level 1"))
                     levelComboBox.setSelectedIndex(1);
        else if (shape.getContent().contains("level 2"))
                     levelComboBox.setSelectedIndex(2);
        else if (shape.getContent().contains("level 3"))
                     levelComboBox.setSelectedIndex(3);
        else if (shape.getContent().contains("level 4"))
                     levelComboBox.setSelectedIndex(4);
        else if (shape.getContent().contains("level 5"))
                     levelComboBox.setSelectedIndex(5);

        // TODO find, parse: height, radius etc.

        priorContentRadioButton.setSelected(true); // restore status since other callbacks may reset it
    }//GEN-LAST:event_priorContentRadioButtonActionPerformed

    private void tetrahedronRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tetrahedronRadioButtonActionPerformed
         newContentRadioButton.setSelected(true);
        setGeometryTypeButtonGroupEnabled(true);
     if (primitivesRadioButton.isSelected())
           polygonsRadioButton.setSelected (true);
         primitivesRadioButton.setEnabled(false);
       numberOfPointsTextField.setEnabled(false);
               heightTextField.setEnabled(false);
               radiusTextField.setEnabled(false);
                 levelComboBox.setEnabled(false);
                 levelComboBox.setEditable(false); // grey background
           numberOfPointsLabel.setEnabled(false);
                   heightLabel.setEnabled(false);
                   radiusLabel.setEnabled(false);
                    depthLabel.setEnabled(false);
                    levelLabel.setEnabled(false);
                   radiusLabelRenameWidth(false);
        dEFUSEpanel.setDefaultDEFname("TetrahedronShape");
        computeBoundingBox ();
    }//GEN-LAST:event_tetrahedronRadioButtonActionPerformed

    private void arc2dRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_arc2dRadioButtonActionPerformed
         newContentRadioButton.setSelected(true);
        setGeometryTypeButtonGroupEnabled(true);
         primitivesRadioButton.setSelected(true);
         primitivesRadioButton.setEnabled(true);
           polygonsRadioButton.setEnabled(false);
              linesRadioButton.setEnabled(false);
             pointsRadioButton.setEnabled(false);
       numberOfPointsTextField.setEnabled(false);
               heightTextField.setEnabled(false);
               radiusTextField.setEnabled(true);
                depthTextField.setEnabled(false);
                 levelComboBox.setEnabled(false);
                 levelComboBox.setEditable(false); // grey background
           numberOfPointsLabel.setEnabled(false);
                   heightLabel.setEnabled(false);
                   radiusLabel.setEnabled(true);
                    depthLabel.setEnabled(false);
                    levelLabel.setEnabled(false);
                   radiusLabelRenameWidth(false);
        dEFUSEpanel.setDefaultDEFname("Arc2dShape");
        computeBoundingBox ();
    }//GEN-LAST:event_arc2dRadioButtonActionPerformed

    private void arcClose2dRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_arcClose2dRadioButtonActionPerformed
         newContentRadioButton.setSelected(true);
         setGeometryTypeButtonGroupEnabled(true);
         primitivesRadioButton.setSelected(true);
         primitivesRadioButton.setEnabled(true);
           polygonsRadioButton.setEnabled(false);
              linesRadioButton.setEnabled(false);
             pointsRadioButton.setEnabled(false);
       numberOfPointsTextField.setEnabled(false);
               heightTextField.setEnabled(false);
               radiusTextField.setEnabled(true);
                depthTextField.setEnabled(false);
                 levelComboBox.setEnabled(false);
                 levelComboBox.setEditable(false); // grey background
           numberOfPointsLabel.setEnabled(false);
                   heightLabel.setEnabled(false);
                   radiusLabel.setEnabled(true);
                    depthLabel.setEnabled(false);
                    levelLabel.setEnabled(false);
                   radiusLabelRenameWidth(false);
        dEFUSEpanel.setDefaultDEFname("ArcClose2dShape");
        computeBoundingBox ();
    }//GEN-LAST:event_arcClose2dRadioButtonActionPerformed

    private void circle2dRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_circle2dRadioButtonActionPerformed
         newContentRadioButton.setSelected(true);
         setGeometryTypeButtonGroupEnabled(true);
         primitivesRadioButton.setSelected(true);
         primitivesRadioButton.setEnabled(true);
           polygonsRadioButton.setEnabled(false);
              linesRadioButton.setEnabled(false);
             pointsRadioButton.setEnabled(false);
       numberOfPointsTextField.setEnabled(false);
               heightTextField.setEnabled(false);
               radiusTextField.setEnabled(true);
                depthTextField.setEnabled(false);
                 levelComboBox.setEnabled(false);
                 levelComboBox.setEditable(false); // grey background
           numberOfPointsLabel.setEnabled(false);
                   heightLabel.setEnabled(false);
                   radiusLabel.setEnabled(true);
                    depthLabel.setEnabled(false);
                    levelLabel.setEnabled(false);
                   radiusLabelRenameWidth(false);
        dEFUSEpanel.setDefaultDEFname("Circle2dShape");
        computeBoundingBox ();
    }//GEN-LAST:event_circle2dRadioButtonActionPerformed

    private void disk2dRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disk2dRadioButtonActionPerformed
         newContentRadioButton.setSelected(true);
         setGeometryTypeButtonGroupEnabled(true);
         primitivesRadioButton.setSelected(true);
         primitivesRadioButton.setEnabled(true);
           polygonsRadioButton.setEnabled(false);
              linesRadioButton.setEnabled(false);
             pointsRadioButton.setEnabled(false);
       numberOfPointsTextField.setEnabled(false);
               heightTextField.setEnabled(false);
               radiusTextField.setEnabled(true);
                depthTextField.setEnabled(false);
                 levelComboBox.setEnabled(false);
                 levelComboBox.setEditable(false); // grey background
           numberOfPointsLabel.setEnabled(false);
                   heightLabel.setEnabled(false);
                   radiusLabel.setEnabled(true);
                    depthLabel.setEnabled(false);
                    levelLabel.setEnabled(false);
                   radiusLabelRenameWidth(false);
        dEFUSEpanel.setDefaultDEFname("Disk2dShape");
        computeBoundingBox ();
        
         if (  radiusTextField.isEnabled() && !(radiusTextField.getText().isEmpty()) &&
             !(radiusTextField.getText().trim().contains(" ")))
         {
            NotifyDescriptor.Message msg = new NotifyDescriptor.Message("<html><p>Hint: use radius text field to enter<br/> both innerRadius and outerRadius</p>");
            DialogDisplayer.getDefault().notifyLater(msg);             
         }
    }//GEN-LAST:event_disk2dRadioButtonActionPerformed

    private void polyline2dRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_polyline2dRadioButtonActionPerformed
         newContentRadioButton.setSelected(true);
         setGeometryTypeButtonGroupEnabled(true);
         primitivesRadioButton.setSelected(true);
         primitivesRadioButton.setEnabled(true);
           polygonsRadioButton.setEnabled(false);
              linesRadioButton.setEnabled(false);
             pointsRadioButton.setEnabled(false);
       numberOfPointsTextField.setEnabled(false);
               heightTextField.setEnabled(false);
               radiusTextField.setEnabled(false);
                depthTextField.setEnabled(false);
                 levelComboBox.setEnabled(false);
           numberOfPointsLabel.setEnabled(false);
                   heightLabel.setEnabled(false);
                   radiusLabel.setEnabled(false);
                    depthLabel.setEnabled(false);
                    levelLabel.setEnabled(false);
                   radiusLabelRenameWidth(false);
        dEFUSEpanel.setDefaultDEFname("Polyline2dShape");
        computeBoundingBox ();
    }//GEN-LAST:event_polyline2dRadioButtonActionPerformed

    private void polypoint2dRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_polypoint2dRadioButtonActionPerformed
         newContentRadioButton.setSelected(true);
         setGeometryTypeButtonGroupEnabled(true);
         primitivesRadioButton.setSelected(true);
         primitivesRadioButton.setEnabled(true);
           polygonsRadioButton.setEnabled(false);
              linesRadioButton.setEnabled(false);
             pointsRadioButton.setEnabled(false);
       numberOfPointsTextField.setEnabled(false);
               heightTextField.setEnabled(false);
               radiusTextField.setEnabled(false);
                depthTextField.setEnabled(false);
                 levelComboBox.setEnabled(false);
           numberOfPointsLabel.setEnabled(false);
                   heightLabel.setEnabled(false);
                   radiusLabel.setEnabled(false);
                    depthLabel.setEnabled(false);
                    levelLabel.setEnabled(false);
                   radiusLabelRenameWidth(false);
        dEFUSEpanel.setDefaultDEFname("Polypoint2dShape");
        computeBoundingBox ();
    }//GEN-LAST:event_polypoint2dRadioButtonActionPerformed

    private void rectangle2dRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rectangle2dRadioButtonActionPerformed
         newContentRadioButton.setSelected(true);
         setGeometryTypeButtonGroupEnabled(true);
         primitivesRadioButton.setSelected(true);
         primitivesRadioButton.setEnabled(true);
           polygonsRadioButton.setEnabled(false);
              linesRadioButton.setEnabled(false);
             pointsRadioButton.setEnabled(false);
       numberOfPointsTextField.setEnabled(false);
               heightTextField.setEnabled(true);
               radiusTextField.setEnabled(true);
                depthTextField.setEnabled(false);
                 levelComboBox.setEnabled(false);
           numberOfPointsLabel.setEnabled(false);
                   heightLabel.setEnabled(true);
                   radiusLabel.setEnabled(true);
                    depthLabel.setEnabled(false);
                    levelLabel.setEnabled(false);
                   radiusLabelRenameWidth(false);
        dEFUSEpanel.setDefaultDEFname("Rectangle2dShape");
        computeBoundingBox ();
    }//GEN-LAST:event_rectangle2dRadioButtonActionPerformed

    private void triangleSet2dRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_triangleSet2dRadioButtonActionPerformed
         newContentRadioButton.setSelected(true);
         setGeometryTypeButtonGroupEnabled(true);
         primitivesRadioButton.setSelected(true);
         primitivesRadioButton.setEnabled(true);
           polygonsRadioButton.setEnabled(false);
              linesRadioButton.setEnabled(false);
             pointsRadioButton.setEnabled(false);
       numberOfPointsTextField.setEnabled(false);
               heightTextField.setEnabled(false);
               radiusTextField.setEnabled(false);
                depthTextField.setEnabled(false);
                 levelComboBox.setEnabled(false);
           numberOfPointsLabel.setEnabled(false);
                   heightLabel.setEnabled(false);
                   radiusLabel.setEnabled(false);
                    depthLabel.setEnabled(false);
                    levelLabel.setEnabled(false);
                   radiusLabelRenameWidth(false);
        dEFUSEpanel.setDefaultDEFname("TriangleSet2dShape");
        computeBoundingBox ();
    }//GEN-LAST:event_triangleSet2dRadioButtonActionPerformed

    private void radiusTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radiusTextFieldActionPerformed
      readInnerOuterRadiusValues ();
    }//GEN-LAST:event_radiusTextFieldActionPerformed

    private void capsuleRadioButtonPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_capsuleRadioButtonPropertyChange
        if (!capsuleRadioButton.isSelected())
             levelComboBox.setSelectedIndex(saveLevelIndex);
        dEFUSEpanel.setDefaultDEFname("CapsuleShape");
        computeBoundingBox ();
    }//GEN-LAST:event_capsuleRadioButtonPropertyChange

    private void roundedRectangleRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedRectangleRadioButtonActionPerformed
         newContentRadioButton.setSelected(true);
         setGeometryTypeButtonGroupEnabled(true);
           polygonsRadioButton.setSelected(true);
         primitivesRadioButton.setEnabled(false);
           polygonsRadioButton.setEnabled(true);
              linesRadioButton.setEnabled(true);
             pointsRadioButton.setEnabled(true);
       numberOfPointsTextField.setEnabled(false);
               heightTextField.setEnabled(true);
               radiusTextField.setEnabled(true);
                depthTextField.setEnabled(true);
                 levelComboBox.setEnabled(false);
           numberOfPointsLabel.setEnabled(false);
                   heightLabel.setEnabled(true);
                   radiusLabel.setEnabled(true);
                    depthLabel.setEnabled(true);
                    levelLabel.setEnabled(false);
                   radiusLabelRenameWidth(false);
        if (depthTextField.getText().equals("1"))
            depthTextField.setText("0.2"); // better default
        dEFUSEpanel.setDefaultDEFname("RoundedRectangleShape");
        computeBoundingBox ();
    }//GEN-LAST:event_roundedRectangleRadioButtonActionPerformed

    private void roundedRectangle2dRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_roundedRectangle2dRadioButtonActionPerformed
        newContentRadioButton.setSelected(true);
        setGeometryTypeButtonGroupEnabled(true);
           polygonsRadioButton.setSelected(true);
         primitivesRadioButton.setEnabled(false);
           polygonsRadioButton.setEnabled(true);
              linesRadioButton.setEnabled(true);
             pointsRadioButton.setEnabled(true);
       numberOfPointsTextField.setEnabled(false); // TODO sectors
               heightTextField.setEnabled(true);
               radiusTextField.setEnabled(true);
                depthTextField.setEnabled(false);
                 levelComboBox.setEnabled(false);
           numberOfPointsLabel.setEnabled(false);
                   heightLabel.setEnabled(true);
                   radiusLabel.setEnabled(true);
                    depthLabel.setEnabled(false);
                    levelLabel.setEnabled(false);
                   radiusLabelRenameWidth(true);
        if (depthTextField.getText().equals("1"))
            depthTextField.setText("0.2"); // better default
        dEFUSEpanel.setDefaultDEFname("RoundedRectangle2dShape");
        computeBoundingBox ();
    }//GEN-LAST:event_roundedRectangle2dRadioButtonActionPerformed

    private void textPlusTransparentBoxRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textPlusTransparentBoxRadioButtonActionPerformed
         newContentRadioButton.setSelected(true);
        setGeometryTypeButtonGroupEnabled(false);
         primitivesRadioButton.setSelected(true);
         primitivesRadioButton.setEnabled(true);
       numberOfPointsTextField.setEnabled(false);
               heightTextField.setEnabled(true);
               heightTextField.setText("1");
               radiusTextField.setEnabled(false);
                depthTextField.setEnabled(false);
                 levelComboBox.setEnabled(false);
                 levelComboBox.setEditable(false); // grey background
           numberOfPointsLabel.setEnabled(false);
                   heightLabel.setEnabled(true);
                   radiusLabel.setEnabled(false);
                    depthLabel.setEnabled(false);
                    levelLabel.setEnabled(false);
                   radiusLabelRenameWidth(false);
        dEFUSEpanel.setDefaultDEFname("SelectedTextShape");
        computeBoundingBox ();
    }//GEN-LAST:event_textPlusTransparentBoxRadioButtonActionPerformed

    private void axisLinesRgbRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_axisLinesRgbRadioButtonActionPerformed
         newContentRadioButton.setSelected(true);
        setGeometryTypeButtonGroupEnabled(false);
         primitivesRadioButton.setSelected(true);
         primitivesRadioButton.setEnabled(true);
       numberOfPointsTextField.setEnabled(false);
               heightTextField.setEnabled(true);
               heightTextField.setText("1");
               radiusTextField.setEnabled(false);
                depthTextField.setEnabled(false);
                 levelComboBox.setEnabled(false);
                 levelComboBox.setEditable(false); // grey background
           numberOfPointsLabel.setEnabled(false);
                   heightLabel.setEnabled(true);
                   radiusLabel.setEnabled(false);
                    depthLabel.setEnabled(false);
                    levelLabel.setEnabled(false);
                   radiusLabelRenameWidth(false);
        dEFUSEpanel.setDefaultDEFname("AxisLinesShape");
        computeBoundingBox ();
    }//GEN-LAST:event_axisLinesRgbRadioButtonActionPerformed
    /** radiusLabel also serves as width label */
    private void radiusLabelRenameWidth (boolean reset)
    {        
        if (reset)
             radiusLabel.setText("width");
        else radiusLabel.setText("radius");
        radiusLabel.repaint();
    }
    private void readInnerOuterRadiusValues ()
    {
      if (radiusTextField.isEnabled() && !(radiusTextField.getText().isEmpty()))
      {
          if (!(radiusTextField.getText().trim().contains(" ")))
          {
              radius = Float.valueOf (radiusTextField.getText());
              outerRadius = radius;
              if (innerRadius > outerRadius)
              {
                  innerRadius = outerRadius / 2.0f; // provide a better default
              }
          }
          else
          {
              radius      = Float.valueOf (radiusTextField.getText().trim().substring(0,radiusTextField.getText().trim().indexOf(" ")));
              innerRadius = radius;
              outerRadius = Float.valueOf (radiusTextField.getText().trim().substring(radiusTextField.getText().trim().indexOf(" ")));
              if (Math.abs(innerRadius) > Math.abs(outerRadius))
              {
                  float  temp = innerRadius;
                  innerRadius = outerRadius;
                  outerRadius = temp;
              }
          }
          if (     radius < 0.0f)      radius = -radius;
          if (innerRadius < 0.0f) innerRadius = -innerRadius;
          if (outerRadius < 0.0f) outerRadius = -outerRadius;
      }
    }
    private void computeBoundingBox ()
    {
        // TODO.  also add clear bbox size button.
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox additionalGeometriesComboBox;
    private javax.swing.JRadioButton arc2dRadioButton;
    private javax.swing.JRadioButton arcClose2dRadioButton;
    private javax.swing.JRadioButton axisLinesRgbRadioButton;
    private javax.swing.JLabel bboxCenterLabel;
    private javax.swing.JTextField bboxCenterXTF;
    private javax.swing.JTextField bboxCenterYTF;
    private javax.swing.JTextField bboxCenterZTF;
    private javax.swing.JPanel bboxPanel;
    private javax.swing.JLabel bboxSizeLabel;
    private javax.swing.JTextField bboxSizeXTF;
    private javax.swing.JTextField bboxSizeYTF;
    private javax.swing.JTextField bboxSizeZTF;
    private javax.swing.JRadioButton boxRadioButton;
    private javax.swing.JRadioButton capsuleRadioButton;
    private javax.swing.ButtonGroup childContentButtonGroup;
    private javax.swing.JRadioButton circle2dRadioButton;
    private javax.swing.JRadioButton coneRadioButton;
    private javax.swing.JRadioButton conicalPolygonRadioButton;
    private javax.swing.JLabel contentSelectionLabel;
    private javax.swing.JPanel contentSelectionPanel;
    private javax.swing.JRadioButton cylinderRadioButton;
    private javax.swing.JRadioButton cylindricalPolygonRadioButton;
    private org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel;
    private javax.swing.JLabel depthLabel;
    private javax.swing.JTextField depthTextField;
    private javax.swing.JRadioButton disk2dRadioButton;
    private javax.swing.JRadioButton dodecahedronRadioButton;
    private javax.swing.JRadioButton elevationGridRadioButton;
    private javax.swing.JPanel eventHintPanel;
    private javax.swing.JRadioButton extrusionRadioButton;
    private javax.swing.JLabel geometry2dNodeLabel;
    private javax.swing.JLabel geometry3dNodeLabel;
    private javax.swing.ButtonGroup geometryButtonGroup;
    private javax.swing.JPanel geometryPanel;
    private javax.swing.ButtonGroup geometryTypeButtonGroup;
    private javax.swing.JLabel geometryTypeLabel;
    private javax.swing.JLabel heightLabel;
    private javax.swing.JTextField heightTextField;
    private javax.swing.JRadioButton hemisphereRadioButton;
    private javax.swing.JLabel hintLabel;
    private javax.swing.JRadioButton icosahedronRadioButton;
    private javax.swing.JRadioButton indexedFaceSetRadioButton;
    private javax.swing.JRadioButton indexedLineSetRadioButton;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JComboBox levelComboBox;
    private javax.swing.JLabel levelLabel;
    private javax.swing.JRadioButton lineSetRadioButton;
    private javax.swing.JRadioButton linesRadioButton;
    private javax.swing.JRadioButton newContentRadioButton;
    private javax.swing.JRadioButton noContentRadioButton;
    private javax.swing.JLabel numberOfPointsLabel;
    private javax.swing.JTextField numberOfPointsTextField;
    private javax.swing.JRadioButton pointSetRadioButton;
    private javax.swing.JRadioButton pointsRadioButton;
    private javax.swing.JRadioButton polygonsRadioButton;
    private javax.swing.JRadioButton polyline2dRadioButton;
    private javax.swing.JRadioButton polypoint2dRadioButton;
    private javax.swing.JRadioButton primitivesRadioButton;
    private javax.swing.JRadioButton priorContentRadioButton;
    private javax.swing.JLabel propertiesLabel;
    private javax.swing.JLabel radiusLabel;
    private javax.swing.JTextField radiusTextField;
    private javax.swing.JRadioButton rectangle2dRadioButton;
    private javax.swing.JRadioButton roundedRectangle2dRadioButton;
    private javax.swing.JRadioButton roundedRectangleRadioButton;
    private javax.swing.JRadioButton sphereRadioButton;
    private javax.swing.JRadioButton tetrahedronRadioButton;
    private javax.swing.JRadioButton textPlusTransparentBoxRadioButton;
    private javax.swing.JRadioButton textRadioButton;
    private javax.swing.JRadioButton triangleSet2dRadioButton;
    private javax.swing.JSeparator verticalSeparator1;
    private javax.swing.JSeparator verticalSeparator2;
    private javax.swing.JSeparator verticalSeparator3;
    private javax.swing.JSeparator verticalSeparator4;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_SHAPE";
  }

  /**
   * Check radio buttons and set content accordingly.
   * Use default (or preferred) values of interest for embedded nodes to provide a quickstart to new authors.
   */
  private void setContent ()
  {  
      // TODO consider Appearance/Material/Color interface also
      String APPEARANCE_CONTENT_EMISSIVE_LINES  = "\n    <Appearance>\n      <!-- Line color is defined by emissiveColor -->\n      <Material emissiveColor='1 1 1'/>\n      <!-- TODO authors can add optional LineProperties node here -->\n    </Appearance>\n  ";
      String APPEARANCE_CONTENT_EMISSIVE_POINTS = "\n    <Appearance>\n      <!-- Point color is defined by emissiveColor -->\n      <Material emissiveColor='1 1 1'/>\n    </Appearance>\n  ";
      String APPEARANCE_CONTENT_MATERIAL        = "\n    <Appearance>\n      <Material diffuseColor='0.9 0.9 0.9'/>\n      <!-- TODO authors can add optional ImageTexture, MovieTexture, PixelTexture, TextureTransform, FillProperties, and/or LineProperties nodes here -->\n    </Appearance>\n  ";
      String APPEARANCE_CONTENT_MATERIAL_COLOR  = "\n    <Appearance>\n      <Material/>\n      <!-- TODO authors can add optional ImageTexture, MovieTexture, PixelTexture, TextureTransform, FillProperties, and/or LineProperties nodes here -->\n    </Appearance>\n  ";
      String APPEARANCE_CONTENT_TEXT            =   "    <Appearance>\n      <Material diffuseColor='0.9 0.9 0.9'/>\n    </Appearance>\n  ";
      String APPEARANCE_CONTENT_TEXT2           =   "      <Appearance>\n        <Material diffuseColor='0.9 0.9 0.9'/>\n      </Appearance>\n  ";
      String APPEARANCE_CONTENT_OPTIONAL        = "\n    <!-- Optional alternative: Appearance/Material etc. instead of Color node -->\n    <Appearance>\n      <Material/>\n      <!-- TODO authors can add optional ImageTexture, MovieTexture, PixelTexture, TextureTransform, FillProperties, and/or LineProperties nodes here -->\n    </Appearance>\n  ";
      
      if (noContentRadioButton.isSelected())
      {
          shape.setContent("");
          return;
      }
      else if (priorContentRadioButton.isSelected())
          return; // no change

      // adjust content to match selection
      StringBuilder newContent = new StringBuilder();

      if     (pointsRadioButton.isSelected())
           geometryNodeName = "PointSet";
      else if (linesRadioButton.isSelected())
           geometryNodeName = "IndexedLineSet";
      else geometryNodeName = "IndexedFaceSet"; // polygonsRadioButton.isSelected()

      readInnerOuterRadiusValues ();

      if (heightTextField.isEnabled() && !(heightTextField.getText().isEmpty()))
         height = Float.valueOf (heightTextField.getText());

      if (numberOfPointsTextField.isEnabled() && !(numberOfPointsTextField.getText().isEmpty()))
         numberOfPoints = Integer.valueOf (numberOfPointsTextField.getText());

      if      (boxRadioButton.isSelected())
      {
          // radiusTextField holds width
          newContent.append   ("\n      <Box size='").append(radiusTextField.getText().trim()).append(" ")   // x
                                                .append(heightTextField.getText().trim()).append(" ")   // y
                                                .append(depthTextField.getText().trim()).append("'/>"); // z
          newContent.append(APPEARANCE_CONTENT_MATERIAL);
      }
      else if (coneRadioButton.isSelected())
      {
          newContent.append   ("\n      <Cone height='").append(height).append("' bottomRadius='").append(radius).append("' bottom='true' side='true'/>");
          newContent.append(APPEARANCE_CONTENT_MATERIAL);
      }
      else if (cylinderRadioButton.isSelected())
      {
          newContent.append   ("\n      <Cylinder height='").append(height).append("' radius='").append(radius).append("' bottom='true' side='true' top='true'/>");
          newContent.append(APPEARANCE_CONTENT_MATERIAL);
      }
      else if (sphereRadioButton.isSelected())
      {
          newContent.append   ("\n      <Sphere radius='").append(radius).append("'/>");
          newContent.append(APPEARANCE_CONTENT_MATERIAL);
      }
      else if (textRadioButton.isSelected())
      {
          newContent.append   ("\n      <Text string='\"Here are some\" \"lines of text...\"' solid='false'>")
                    .append   ("\n        <FontStyle family='\"SANS\"' justify='\"MIDDLE\" \"MIDDLE\"' size='").append(heightTextField.getText().trim()).append("' style='BOLD'/>")
                    .append   ("\n      </Text>");
          newContent.append   ("\n").append(APPEARANCE_CONTENT_TEXT);
      }
      else if (textPlusTransparentBoxRadioButton.isSelected()) // Selectable Text
      {
          newContent.append   ("\n      <!-- TODO adjust Text string and Box size, then set Material transparency='1' -->")
                    .append   ("\n      <Text string='\"Here are some\" \"lines of text...\"' solid='false'>")
                    .append   ("\n        <FontStyle family='\"SANS\"' justify='\"MIDDLE\" \"MIDDLE\"' size='").append(heightTextField.getText().trim()).append("' style='BOLD'/>")
                    .append   ("\n      </Text>");
          newContent.append   ("\n"  ).append(APPEARANCE_CONTENT_TEXT2).append("  ");
          
          shape.setPrependText(  "    <!-- Selectable Text design pattern has transparent Box and TouchSensor description as a tooltip -->" + 
                               "\n    <Group>" + 
                               "\n      ");
          // Shape goes here
          shape.setAppendText("\n      <Shape>" + 
                              "\n        <!-- Author TODO: to adjust transparent Box as text-selection assist, set width and height to match size, then set transparency='1' to make invisible.  -->" + 
                              "\n        <Box size='6.2 2 .001'/>" + 
                              "\n        <Appearance>" + 
                              "\n          <Material transparency='0.8'/>" + 
                              "\n        </Appearance>" + 
                              "\n      </Shape>" + 
                              "\n      <!-- Author TODO: for interactive scenes, the TouchSensor output is easily applied as an animation trigger by adding a DEF name and a connecting ROUTE. -->" + 
                              "\n      <TouchSensor description='enter user-tooltip description of selectable text here'/>" + 
                              "\n    </Group>");
      }
      else if (axisLinesRgbRadioButton.isSelected())
      {
          newContent.append  ("\n    <!-- RGB lines showing XYZ axes -->")
                    .append  ("\n    <IndexedLineSet colorIndex='0 1 2' colorPerVertex='false' coordIndex='0 1 -1 0 2 -1 0 3 -1'>")
                    .append  ("\n      <Coordinate point='0 0 0 ").append(height).append(" 0 0 0 ").append(height).append(" 0 0 0 ").append(height).append("'/>")
                    .append  ("\n      <Color color='1 0 0 0 0.6 0 0 0 1'/>") // darker green
                    .append  ("\n    </IndexedLineSet>");
          newContent.append  ("\n").append(APPEARANCE_CONTENT_TEXT);
      }
      else if (elevationGridRadioButton.isSelected())
      {
          newContent.append  ("\n    <ElevationGrid height='0 0 0 0 0 0.5 0.4 0 0 0.3 0.2 0 0 0 0 0' xDimension='4' xSpacing='1' zDimension='4' zSpacing='1' solid='false'>\n      <Color color='1 1 0.1 1 1 0.1 1 1 0.1 1 1 0.1 1 1 0.1 1 0 0 1 0.6 0.2 1 1 0.1 1 1 0.1 0.6 0.4 0 0.8 0.7 0 1 1 0.1 1 1 0.1 1 1 0.1 1 1 0.1 1 1 0.1'/>\n    </ElevationGrid>");
          newContent.append(APPEARANCE_CONTENT_OPTIONAL);
      }
      else if (extrusionRadioButton.isSelected())
      {
          newContent.append  ("\n    <Extrusion crossSection='1 1 1 -1 -1 -1 -1 1 1 1' scale='2 2 1.2 1.2 1 1' spine='0 0 0 0 1.4 0 0 2 0' solid='false'/>");
          newContent.append(APPEARANCE_CONTENT_MATERIAL);
      }
      else if (indexedFaceSetRadioButton.isSelected())
      {
          newContent.append  ("\n    <IndexedFaceSet coordIndex='0 1 2 3 0 -1' solid='false'>\n      <Coordinate point='1 1 0 1 -1 0 -1 -1 0 -1 1 0'/>\n    </IndexedFaceSet>");
          newContent.append(APPEARANCE_CONTENT_MATERIAL_COLOR);
      }
      else if (indexedLineSetRadioButton.isSelected())
      {
          newContent.append  ("\n    <IndexedLineSet coordIndex='0 1 2 3 0 -1'>\n      <Coordinate point='1 1 0 1 -1 0 -1 -1 0 -1 1 0'/>\n    </IndexedLineSet>");
          newContent.append(APPEARANCE_CONTENT_EMISSIVE_LINES);
      }
      else if (lineSetRadioButton.isSelected())
      {
          newContent.append  ("\n    <LineSet vertexCount='5'>\n      <Coordinate point='1 1 0 1 -1 0 -1 -1 0 -1 1 0 1 1 0'/>\n    </LineSet>");
          newContent.append(APPEARANCE_CONTENT_EMISSIVE_LINES);
      }
      else if (pointSetRadioButton.isSelected())
      {
          newContent.append  ("\n    <PointSet>\n      <Coordinate point='1 1 0 1 -1 0 -1 -1 0 -1 1 0 '/>\n      <Color color='1 0 0 0 1 0 0 0 1 1 1 1'/>\n    </PointSet>");
          newContent.append(APPEARANCE_CONTENT_EMISSIVE_POINTS);
      }
      else if (conicalPolygonRadioButton.isSelected())
      {
          NsidedPolygon nsidedPolygon = new NsidedPolygon();
          nsidedPolygon.setClosed(true);
          readNumberOfPointsTextField ();

          newContent.append  ("\n    <!-- Conical polygon with ").append(numberOfPoints).append("-sided base -->");
          newContent.append  ("\n    <").append(geometryNodeName);
          if (!pointsRadioButton.isSelected())
          {
            newContent.append(" coordIndex='");
            // bottom
            for (int i=numberOfPoints-1; i >= 0; i--)
            {
                newContent.append(i+2); // skip indices for first 2 points
                if (i > 0)
                    newContent.append(" ");
                else
                    newContent.append(" -1, ");
            }
            // sides
            for (int i=0; i < numberOfPoints; i++)
            {
                newContent.append("1 ");
                newContent.append(i+2); // skip first 2 coordinate points
                newContent.append(" ");
                newContent.append(i+3);
                newContent.append(" -1");
                if (i < numberOfPoints - 1)
                    newContent.append(", ");
            }
            newContent.append("'");
          }
          if (polygonsRadioButton.isSelected())
          {
              newContent.append(" creaseAngle='3.14159' solid='false'");
          }
          newContent.append(">");
          newContent.append("\n      <Coordinate point='");
          newContent.append("0 0 0, 0 1 0, ").append(nsidedPolygon.circlePointsArrayXZ(numberOfPoints, radius, true));
          newContent.append("'/>");
          newContent.append("\n    </").append(geometryNodeName).append(">");
          if (polygonsRadioButton.isSelected())
               newContent.append(APPEARANCE_CONTENT_MATERIAL_COLOR);
          else if (linesRadioButton.isSelected())
               newContent.append(APPEARANCE_CONTENT_EMISSIVE_LINES);
          else newContent.append(APPEARANCE_CONTENT_EMISSIVE_POINTS);
      }
      else if (cylindricalPolygonRadioButton.isSelected())
      {
          NsidedPolygon nsidedPolygon = new NsidedPolygon();
          nsidedPolygon.setClosed(true);
          readNumberOfPointsTextField ();

          newContent.append("\n    <!-- Cylindrical ").append(numberOfPoints).append("-sided polygon -->");
          newContent.append("\n    <").append(geometryNodeName);
          if (!pointsRadioButton.isSelected())
          {
                newContent.append(" coordIndex='");
              // top
              for (int i=0; i < numberOfPoints; i++)
              {
                  newContent.append(i);
                  if (i < numberOfPoints - 1)
                      newContent.append(" ");
                  else
                      newContent.append(" 0 -1, "); // close loop
              }
              // bottom
              for (int i=numberOfPoints-1; i >= 0; i--)
              {
                  newContent.append(i+numberOfPoints); // skip indices for first circle of coordinate points
                  if (i > 0)
                      newContent.append(" ");
                  else
                      newContent.append(" ").append(numberOfPoints + numberOfPoints-1).append(" -1, "); // close loop
              }
              // sides
              for (int i=0; i < numberOfPoints; i++)
              {
                  // right hand rule:  normal facing outward
                  if (i < numberOfPoints - 1)
                       newContent.append(i+1).append(" ").append(i+numberOfPoints+1).append(" ").append(i+numberOfPoints).append(" ").append(i).append(" ").append(i+1);
                  else // index wrap around to beginning of each sequence
                       newContent.append(  0).append(" ").append(  numberOfPoints  ).append(" ").append(i+numberOfPoints).append(" ").append(i).append(" ").append(0);
                  newContent.append(" -1"); // close loop
                  if (i < numberOfPoints - 1)
                      newContent.append(", ");
              }
              newContent.append("'");
          }
          if (polygonsRadioButton.isSelected())
          {
              newContent.append(" creaseAngle='3.14159' solid='false'");
          }
          newContent.append(">");
          newContent.append("\n      <Coordinate point='");
          // no zero coordinates needed
          nsidedPolygon.setHeight(1.0f);
          newContent.append(nsidedPolygon.circlePointsArrayXZ(numberOfPoints, radius, false)); // open
          newContent.append(", ");
          nsidedPolygon.setHeight(0.0f);
          newContent.append(nsidedPolygon.circlePointsArrayXZ(numberOfPoints, radius, false)); // open
          newContent.append("'/>");
          newContent.append("\n    </").append(geometryNodeName).append(">");
          if (polygonsRadioButton.isSelected())
               newContent.append(APPEARANCE_CONTENT_MATERIAL_COLOR);
          else if (linesRadioButton.isSelected())
               newContent.append(APPEARANCE_CONTENT_EMISSIVE_LINES);
          else newContent.append(APPEARANCE_CONTENT_EMISSIVE_POINTS);
      }
      else if (dodecahedronRadioButton.isSelected())
      {
          newContent.append("\n    <!-- Dodecahedron example: http://x3dGraphics.com/examples/X3dForAdvancedModeling/GeometricShapes/IcosahedronSubdivisionLevel5.x3d -->")
                    .append("\n    <").append(geometryNodeName);
          if (!pointsRadioButton.isSelected())
          {
               newContent.append(" coordIndex='0 1 2 -1 0 2 3 -1 0 3 4 -1 0 4 5 -1 0 5 1 -1 6 7 8 -1 6 8 9 -1 6 9 4 -1 6 4 3 -1 6 3 7 -1 10 7 3 -1 10 3 2 -1 10 2 11 -1 10 11 12 -1 10 12 7 -1 13 9 14 -1 13 14 15 -1 13 15 5 -1 13 5 4 -1 13 4 9 -1 16 2 1 -1 16 1 17 -1 16 17 18 -1 16 18 11 -1 16 11 2 -1 19 7 12 -1 19 12 20 -1 19 20 21 -1 19 21 8 -1 19 8 7 -1 22 9 8 -1 22 8 21 -1 22 21 23 -1 22 23 14 -1 22 14 9 -1 24 1 5 -1 24 5 15 -1 24 15 25 -1 24 25 17 -1 24 17 1 -1 26 12 11 -1 26 11 18 -1 26 18 27 -1 26 27 20 -1 26 20 12 -1 28 23 29 -1 28 29 25 -1 28 25 15 -1 28 15 14 -1 28 14 23 -1 30 18 17 -1 30 17 25 -1 30 25 29 -1 30 29 27 -1 30 27 18 -1 31 23 21 -1 31 21 20 -1 31 20 27 -1 31 27 29 -1 31 29 23 -1'");
          }
          if (polygonsRadioButton.isSelected())
          {
               newContent.append(" solid='false'");
          }
          newContent.append(">");
          newContent.append("\n      <Coordinate point='0.378886 0 0.61305 0.847214 0 0.323606 0.523606 0.523606 0.523606 0 0.323606 0.847214 0 -0.323606 0.847214 0.523606 -0.523606 0.523606 -0.378886 0 0.61305 -0.523606 0.523606 0.523606 -0.847214 0 0.323606 -0.523606 -0.523606 0.523606 0 0.61305 0.378886 0.323606 0.847214 0 -0.323606 0.847214 0 0 -0.61305 0.378886 -0.323606 -0.847214 0 0.323606 -0.847214 0 0.61305 0.378886 0 0.847214 0 -0.323606 0.523606 0.523606 -0.523606 -0.61305 0.378886 0 -0.523606 0.523606 -0.523606 -0.847214 0 -0.323606 -0.61305 -0.378886 0 -0.523606 -0.523606 -0.523606 0.61305 -0.378886 0 0.523606 -0.523606 -0.523606 0 0.61305 -0.378886 0 0.323606 -0.847214 0 -0.61305 -0.378886 0 -0.323606 -0.847214 0.378886 0 -0.61305 -0.378886 0 -0.61305'/>")
                    .append("\n    </").append(geometryNodeName).append(">");
          if (polygonsRadioButton.isSelected())
               newContent.append(APPEARANCE_CONTENT_MATERIAL_COLOR);
          else if (linesRadioButton.isSelected())
               newContent.append(APPEARANCE_CONTENT_EMISSIVE_LINES);
          else newContent.append(APPEARANCE_CONTENT_EMISSIVE_POINTS);
      }
      else if (hemisphereRadioButton.isSelected())
      {
          newContent.append("\n    <!-- Hemisphere example: http://x3dGraphics.com/examples/X3dForAdvancedModeling/GeometricShapes/Hemisphere.x3d -->")
                    .append("\n    <").append(geometryNodeName);
          if (!pointsRadioButton.isSelected())
          {
               newContent.append(" coordIndex='0 1 2 -1 1 3 4 -1 2 4 5 -1 3 6 7 -1 4 7 8 -1 5 8 9 -1 6 10 11 -1 7 11 12 -1 8 12 13 -1 9 13 14 -1 10 15 16 -1 11 16 17 -1 12 17 18 -1 13 18 19 -1 14 19 20 -1 1 4 2 -1 3 7 4 -1 4 8 5 -1 6 11 7 -1 7 12 8 -1 8 13 9 -1 10 16 11 -1 11 17 12 -1 12 18 13 -1 13 19 14 -1 21 22 23 -1 22 24 25 -1 23 25 26 -1 24 27 28 -1 25 28 29 -1 26 29 30 -1 27 31 32 -1 28 32 33 -1 29 33 34 -1 30 34 35 -1 31 0 2 -1 32 2 5 -1 33 5 9 -1 34 9 14 -1 35 14 20 -1 22 25 23 -1 24 28 25 -1 25 29 26 -1 27 32 28 -1 28 33 29 -1 29 34 30 -1 31 2 32 -1 32 5 33 -1 33 9 34 -1 34 14 35 -1 21 36 22 -1 36 37 38 -1 22 38 24 -1 37 39 40 -1 38 40 41 -1 24 41 27 -1 41 42 43 -1 27 43 31 -1 31 44 0 -1 36 38 22 -1 37 40 38 -1 38 41 24 -1 40 42 41 -1 41 43 27 -1 43 44 31 -1 15 45 16 -1 45 46 47 -1 16 47 17 -1 46 48 49 -1 47 49 50 -1 17 50 18 -1 48 51 52 -1 49 52 53 -1 50 53 54 -1 18 54 19 -1 51 55 56 -1 52 56 57 -1 53 57 58 -1 54 58 59 -1 19 59 20 -1 45 47 16 -1 46 49 47 -1 47 50 17 -1 48 52 49 -1 49 53 50 -1 50 54 18 -1 51 56 52 -1 52 57 53 -1 53 58 54 -1 54 59 19 -1 15 60 45 -1 45 61 46 -1 61 62 63 -1 46 63 48 -1 63 64 65 -1 48 65 51 -1 64 66 67 -1 65 67 68 -1 51 68 55 -1 60 61 45 -1 61 63 46 -1 62 64 63 -1 63 65 48 -1 64 67 65 -1 65 68 51 -1 55 69 56 -1 69 70 71 -1 56 71 57 -1 70 72 73 -1 71 73 74 -1 57 74 58 -1 72 75 76 -1 73 76 77 -1 74 77 78 -1 58 78 59 -1 75 79 80 -1 76 80 81 -1 77 81 82 -1 78 82 83 -1 59 83 20 -1 69 71 56 -1 70 73 71 -1 71 74 57 -1 72 76 73 -1 73 77 74 -1 74 78 58 -1 75 80 76 -1 76 81 77 -1 77 82 78 -1 78 83 59 -1 55 84 69 -1 84 85 86 -1 69 86 70 -1 85 87 88 -1 86 88 89 -1 70 89 72 -1 87 90 91 -1 88 91 92 -1 89 92 93 -1 72 93 75 -1 90 94 95 -1 91 95 96 -1 92 96 97 -1 93 97 98 -1 75 98 79 -1 84 86 69 -1 85 88 86 -1 86 89 70 -1 87 91 88 -1 88 92 89 -1 89 93 72 -1 90 95 91 -1 91 96 92 -1 92 97 93 -1 93 98 75 -1 79 99 80 -1 99 100 101 -1 80 101 81 -1 100 102 103 -1 101 103 104 -1 81 104 82 -1 102 105 106 -1 103 106 107 -1 104 107 108 -1 82 108 83 -1 105 21 23 -1 106 23 26 -1 107 26 30 -1 108 30 35 -1 83 35 20 -1 99 101 80 -1 100 103 101 -1 101 104 81 -1 102 106 103 -1 103 107 104 -1 104 108 82 -1 105 23 106 -1 106 26 107 -1 107 30 108 -1 108 35 83 -1 79 109 99 -1 109 110 111 -1 99 111 100 -1 110 112 113 -1 111 113 114 -1 100 114 102 -1 112 115 116 -1 113 116 117 -1 114 117 118 -1 102 118 105 -1 115 119 120 -1 116 120 121 -1 117 121 122 -1 118 122 123 -1 105 123 21 -1 109 111 99 -1 110 113 111 -1 111 114 100 -1 112 116 113 -1 113 117 114 -1 114 118 102 -1 115 120 116 -1 116 121 117 -1 117 122 118 -1 118 123 105 -1 119 115 124 -1 115 112 128 -1 124 128 125 -1 112 110 129 -1 128 129 130 -1 125 130 126 -1 110 109 131 -1 129 131 132 -1 130 132 133 -1 126 133 127 -1 109 79 98 -1 131 98 97 -1 132 97 96 -1 133 96 95 -1 127 95 94 -1 115 128 124 -1 112 129 128 -1 128 130 125 -1 110 131 129 -1 129 132 130 -1 130 133 126 -1 109 98 131 -1 131 97 132 -1 132 96 133 -1 133 95 127 -1 39 37 135 -1 37 36 137 -1 135 137 138 -1 136 138 139 -1 36 21 123 -1 137 123 122 -1 138 122 121 -1 139 121 120 -1 134 120 119 -1 37 137 135 -1 135 138 136 -1 36 123 137 -1 137 122 138 -1 138 121 139 -1 139 120 134 -1 94 90 140 -1 90 87 141 -1 87 85 142 -1 141 142 143 -1 85 84 144 -1 142 144 145 -1 84 55 68 -1 144 68 67 -1 145 67 66 -1 90 141 140 -1 87 142 141 -1 85 144 142 -1 142 145 143 -1 84 68 144 -1 144 67 145 -1'");
          }
          if (polygonsRadioButton.isSelected())
          {
               newContent.append(" creaseAngle='0.785398' solid='false'");
          }
          newContent.append(">");
          newContent.append("\n      <Coordinate point='0.5257 0 0.8507 0.3477 0 0.9376 0.4636 0.1875 0.866 0.1227 0 0.9924 0.2531 0.2047 0.9455 0.368 0.397 0.8408 -0.1227 0 0.9924 0 0.2116 0.9773 0.1308 0.4233 0.8965 0.2453 0.5955 0.765 -0.3477 0 0.9376 -0.2531 0.2047 0.9455 -0.1308 0.4233 0.8965 0 0.6142 0.7891 0.1159 0.7501 0.6511 -0.5257 0 0.8507 -0.4636 0.1875 0.866 -0.368 0.397 0.8408 -0.2453 0.5955 0.765 -0.1159 0.7501 0.6511 0 0.8507 0.5257 0.8507 0.5257 0 0.866 0.4636 0.1875 0.7501 0.6511 0.1159 0.8408 0.368 0.397 0.7408 0.5844 0.3313 0.5955 0.765 0.2453 0.765 0.2453 0.5955 0.6849 0.4732 0.5541 0.5541 0.6849 0.4732 0.397 0.8408 0.368 0.6511 0.1159 0.7501 0.5844 0.3313 0.7408 0.4732 0.5541 0.6849 0.3313 0.7408 0.5844 0.1875 0.866 0.4636 0.9376 0.3477 0 0.9924 0.1227 0 0.9455 0.2531 0.2047 0.9924 0 0 0.9773 0 0.2116 0.8965 0.1308 0.4233 0.8965 0 0.4233 0.7891 0 0.6142 0.6511 0 0.7501 -0.6511 0.1159 0.7501 -0.765 0.2453 0.5955 -0.5844 0.3313 0.7408 -0.8408 0.368 0.397 -0.6849 0.4732 0.5541 -0.4732 0.5541 0.6849 -0.866 0.4636 0.1875 -0.7408 0.5844 0.3313 -0.5541 0.6849 0.4732 -0.3313 0.7408 0.5844 -0.8507 0.5257 0 -0.7501 0.6511 0.1159 -0.5955 0.765 0.2453 -0.397 0.8408 0.368 -0.1875 0.866 0.4636 -0.6511 0 0.7501 -0.7891 0 0.6142 -0.8965 0 0.4233 -0.8965 0.1308 0.4233 -0.9773 0 0.2116 -0.9455 0.2531 0.2047 -0.9924 0 0 -0.9924 0.1227 0 -0.9376 0.3477 0 -0.7501 0.6511 -0.1159 -0.5955 0.765 -0.2453 -0.6142 0.7891 0 -0.397 0.8408 -0.368 -0.4233 0.8965 -0.1308 -0.4233 0.8965 0.1308 -0.1875 0.866 -0.4636 -0.2047 0.9455 -0.2531 -0.2116 0.9773 0 -0.2047 0.9455 0.2531 0 0.8507 -0.5257 0 0.9376 -0.3477 0 0.9924 -0.1227 0 0.9924 0.1227 0 0.9376 0.3477 -0.866 0.4636 -0.1875 -0.8408 0.368 -0.397 -0.7408 0.5844 -0.3313 -0.765 0.2453 -0.5955 -0.6849 0.4732 -0.5541 -0.5541 0.6849 -0.4732 -0.6511 0.1159 -0.7501 -0.5844 0.3313 -0.7408 -0.4732 0.5541 -0.6849 -0.3313 0.7408 -0.5844 -0.5257 0 -0.8507 -0.4636 0.1875 -0.866 -0.368 0.397 -0.8408 -0.2453 0.5955 -0.765 -0.1159 0.7501 -0.6511 0.1875 0.866 -0.4636 0.397 0.8408 -0.368 0.2047 0.9455 -0.2531 0.5955 0.765 -0.2453 0.4233 0.8965 -0.1308 0.2116 0.9773 0 0.7501 0.6511 -0.1159 0.6142 0.7891 0 0.4233 0.8965 0.1308 0.2047 0.9455 0.2531 0.1159 0.7501 -0.6511 0.2453 0.5955 -0.765 0.3313 0.7408 -0.5844 0.368 0.397 -0.8408 0.4732 0.5541 -0.6849 0.5541 0.6849 -0.4732 0.4636 0.1875 -0.866 0.5844 0.3313 -0.7408 0.6849 0.4732 -0.5541 0.7408 0.5844 -0.3313 0.5257 0 -0.8507 0.6511 0.1159 -0.7501 0.765 0.2453 -0.5955 0.8408 0.368 -0.397 0.866 0.4636 -0.1875 0.3477 0 -0.9376 0.1227 0 -0.9924 -0.1227 0 -0.9924 -0.3477 0 -0.9376 0.2531 0.2047 -0.9455 0.1308 0.4233 -0.8965 0 0.2116 -0.9773 0 0.6142 -0.7891 -0.1308 0.4233 -0.8965 -0.2531 0.2047 -0.9455 0.6511 0 -0.7501 0.9773 0 -0.2116 0.8965 0 -0.4233 0.9455 0.2531 -0.2047 0.8965 0.1308 -0.4233 0.7891 0 -0.6142 -0.6511 0 -0.7501 -0.7891 0 -0.6142 -0.8965 0.1308 -0.4233 -0.8965 0 -0.4233 -0.9455 0.2531 -0.2047 -0.9773 0 -0.2116'/>")
                    .append("\n    </").append(geometryNodeName).append(">");
          if (polygonsRadioButton.isSelected())
               newContent.append(APPEARANCE_CONTENT_MATERIAL_COLOR);
          else if (linesRadioButton.isSelected())
               newContent.append(APPEARANCE_CONTENT_EMISSIVE_LINES);
          else newContent.append(APPEARANCE_CONTENT_EMISSIVE_POINTS);
      }
      else if (icosahedronRadioButton.isSelected() && (levelComboBox.getSelectedIndex() == 5))
      {
          newContent.append("\n    <!-- Icosahedron level 5 example: http://x3dGraphics.com/examples/X3dForAdvancedModeling/GeometricShapes/IcosahedronSubdivisionLevel5.x3d -->");
      }
      else if (icosahedronRadioButton.isSelected() && (levelComboBox.getSelectedIndex() == 4))
      {
          newContent.append("\n    <!-- Icosahedron level 4 example: http://x3dGraphics.com/examples/X3dForAdvancedModeling/GeometricShapes/IcosahedronSubdivisionLevel4.x3d -->");
      }
      else if (icosahedronRadioButton.isSelected() && (levelComboBox.getSelectedIndex() == 3))
      {
          newContent.append("\n    <!-- Icosahedron level 3 example: http://x3dGraphics.com/examples/X3dForAdvancedModeling/GeometricShapes/IcosahedronSubdivisionLevel3.x3d -->")
                    .append("\n    <").append(geometryNodeName);
          if (!pointsRadioButton.isSelected())
          {
               newContent.append(" coordIndex='0 1 2 -1 3 4 5 -1 6 7 8 -1 9 10 11 -1 12 13 14 -1 15 16 17 -1 18 19 20 -1 21 22 23 -1 24 25 26 -1 27 28 29 -1 30 31 32 -1 33 34 35 -1 36 37 38 -1 39 40 41 -1 42 43 44 -1 45 46 47 -1 48 49 50 -1 51 52 53 -1 54 55 56 -1 57 58 59 -1 60 61 62 -1 63 64 65 -1 66 67 68 -1 69 70 71 -1 72 73 74 -1 75 76 77 -1 78 79 80 -1 81 82 83 -1 84 85 86 -1 87 88 89 -1 90 91 92 -1 93 94 95 -1 96 97 98 -1 99 100 101 -1 102 103 104 -1 105 106 107 -1 108 109 110 -1 111 112 113 -1 114 115 116 -1 117 118 119 -1 120 121 122 -1 123 124 125 -1 126 127 128 -1 129 130 131 -1 132 133 134 -1 135 136 137 -1 138 139 140 -1 141 142 143 -1 144 145 146 -1 147 148 149 -1 150 151 152 -1 153 154 155 -1 156 157 158 -1 159 160 161 -1 162 163 164 -1 165 166 167 -1 168 169 170 -1 171 172 173 -1 174 175 176 -1 177 178 179 -1 180 181 182 -1 183 184 185 -1 186 187 188 -1 189 190 191 -1 192 193 194 -1 195 196 197 -1 198 199 200 -1 201 202 203 -1 204 205 206 -1 207 208 209 -1 210 211 212 -1 213 214 215 -1 216 217 218 -1 219 220 221 -1 222 223 224 -1 225 226 227 -1 228 229 230 -1 231 232 233 -1 234 235 236 -1 237 238 239 -1 240 2 241 -1 242 243 0 -1 1 244 245 -1 246 5 247 -1 248 249 3 -1 4 250 251 -1 252 8 253 -1 254 255 6 -1 7 256 257 -1 258 11 259 -1 260 261 9 -1 10 262 263 -1 264 14 265 -1 266 267 12 -1 13 268 269 -1 270 17 271 -1 272 273 15 -1 16 274 275 -1 276 20 277 -1 278 279 18 -1 19 280 281 -1 282 23 283 -1 284 285 21 -1 22 286 287 -1 288 26 289 -1 290 291 24 -1 25 292 293 -1 294 29 295 -1 296 297 27 -1 28 298 299 -1 300 32 301 -1 302 303 30 -1 31 304 305 -1 306 35 307 -1 308 309 33 -1 34 310 311 -1 312 38 313 -1 314 315 36 -1 37 316 317 -1 318 41 319 -1 320 321 39 -1 40 322 323 -1 324 44 325 -1 326 327 42 -1 43 328 329 -1 330 47 331 -1 332 333 45 -1 46 334 335 -1 336 50 337 -1 338 339 48 -1 49 340 341 -1 342 53 343 -1 344 345 51 -1 52 346 347 -1 348 56 349 -1 350 351 54 -1 55 352 353 -1 354 59 355 -1 356 357 57 -1 58 358 359 -1 360 62 361 -1 362 241 60 -1 61 245 363 -1 364 65 240 -1 365 366 63 -1 64 367 242 -1 244 68 368 -1 243 369 66 -1 67 370 371 -1 365 71 372 -1 364 247 69 -1 70 251 373 -1 362 74 246 -1 360 374 72 -1 73 375 248 -1 250 77 376 -1 249 377 75 -1 76 378 379 -1 380 80 381 -1 382 253 78 -1 79 257 383 -1 384 83 252 -1 385 386 81 -1 82 387 254 -1 256 86 388 -1 255 389 84 -1 85 390 391 -1 385 89 392 -1 384 259 87 -1 88 263 393 -1 382 92 258 -1 380 394 90 -1 91 395 260 -1 262 95 396 -1 261 397 93 -1 94 398 399 -1 400 98 401 -1 402 265 96 -1 97 269 403 -1 404 101 264 -1 405 406 99 -1 100 407 266 -1 268 104 408 -1 267 409 102 -1 103 410 411 -1 405 107 412 -1 404 271 105 -1 106 275 413 -1 402 110 270 -1 400 414 108 -1 109 415 272 -1 274 113 416 -1 273 417 111 -1 112 418 419 -1 420 116 421 -1 422 277 114 -1 115 281 423 -1 424 119 276 -1 425 426 117 -1 118 427 278 -1 280 122 428 -1 279 429 120 -1 121 430 431 -1 425 125 432 -1 424 283 123 -1 124 287 433 -1 422 128 282 -1 420 434 126 -1 127 435 284 -1 286 131 436 -1 285 437 129 -1 130 438 439 -1 440 134 441 -1 442 289 132 -1 133 293 443 -1 444 137 288 -1 445 446 135 -1 136 447 290 -1 292 140 448 -1 291 449 138 -1 139 450 451 -1 445 143 452 -1 444 295 141 -1 142 299 453 -1 442 146 294 -1 440 454 144 -1 145 455 296 -1 298 149 456 -1 297 457 147 -1 148 458 459 -1 460 152 461 -1 462 301 150 -1 151 305 463 -1 464 155 300 -1 465 466 153 -1 154 467 302 -1 304 158 468 -1 303 469 156 -1 157 470 471 -1 465 161 472 -1 464 307 159 -1 160 311 473 -1 462 164 306 -1 460 474 162 -1 163 475 308 -1 310 167 476 -1 309 477 165 -1 166 478 479 -1 458 170 366 -1 457 313 168 -1 169 317 367 -1 455 173 312 -1 454 418 171 -1 172 417 314 -1 316 176 369 -1 315 415 174 -1 175 414 370 -1 372 179 459 -1 373 319 177 -1 178 323 456 -1 376 182 318 -1 379 421 180 -1 181 423 320 -1 322 185 453 -1 321 428 183 -1 184 431 452 -1 361 188 471 -1 363 325 186 -1 187 329 468 -1 368 191 324 -1 371 401 189 -1 190 403 326 -1 328 194 463 -1 327 408 192 -1 193 411 461 -1 470 197 374 -1 469 331 195 -1 196 335 375 -1 467 200 330 -1 466 438 198 -1 199 437 332 -1 334 203 377 -1 333 435 201 -1 202 434 378 -1 381 206 451 -1 383 337 204 -1 205 341 448 -1 388 209 336 -1 391 412 207 -1 208 413 338 -1 340 212 443 -1 339 416 210 -1 211 419 441 -1 450 215 394 -1 449 343 213 -1 214 347 395 -1 447 218 342 -1 446 430 216 -1 217 429 344 -1 346 221 397 -1 345 427 219 -1 220 426 398 -1 478 224 386 -1 477 349 222 -1 223 353 387 -1 475 227 348 -1 474 410 225 -1 226 409 350 -1 352 230 389 -1 351 407 228 -1 229 406 390 -1 392 233 479 -1 393 355 231 -1 232 359 476 -1 396 236 354 -1 399 432 234 -1 235 433 356 -1 358 239 473 -1 357 436 237 -1 238 439 472 -1 480 0 2 -1 0 481 1 -1 2 1 482 -1 483 3 5 -1 3 484 4 -1 5 4 485 -1 486 6 8 -1 6 487 7 -1 8 7 488 -1 489 9 11 -1 9 490 10 -1 11 10 491 -1 492 12 14 -1 12 493 13 -1 14 13 494 -1 495 15 17 -1 15 496 16 -1 17 16 497 -1 498 18 20 -1 18 499 19 -1 20 19 500 -1 501 21 23 -1 21 502 22 -1 23 22 503 -1 504 24 26 -1 24 505 25 -1 26 25 506 -1 507 27 29 -1 27 508 28 -1 29 28 509 -1 510 30 32 -1 30 511 31 -1 32 31 512 -1 513 33 35 -1 33 514 34 -1 35 34 515 -1 516 36 38 -1 36 517 37 -1 38 37 518 -1 519 39 41 -1 39 520 40 -1 41 40 521 -1 522 42 44 -1 42 523 43 -1 44 43 524 -1 525 45 47 -1 45 526 46 -1 47 46 527 -1 528 48 50 -1 48 529 49 -1 50 49 530 -1 531 51 53 -1 51 532 52 -1 53 52 533 -1 534 54 56 -1 54 535 55 -1 56 55 536 -1 537 57 59 -1 57 538 58 -1 59 58 539 -1 540 60 62 -1 60 482 61 -1 62 61 541 -1 542 63 65 -1 63 543 64 -1 65 64 480 -1 481 66 68 -1 66 544 67 -1 68 67 545 -1 542 69 71 -1 69 485 70 -1 71 70 546 -1 540 72 74 -1 72 547 73 -1 74 73 483 -1 484 75 77 -1 75 548 76 -1 77 76 549 -1 550 78 80 -1 78 488 79 -1 80 79 551 -1 552 81 83 -1 81 553 82 -1 83 82 486 -1 487 84 86 -1 84 554 85 -1 86 85 555 -1 552 87 89 -1 87 491 88 -1 89 88 556 -1 550 90 92 -1 90 557 91 -1 92 91 489 -1 490 93 95 -1 93 558 94 -1 95 94 559 -1 560 96 98 -1 96 494 97 -1 98 97 561 -1 562 99 101 -1 99 563 100 -1 101 100 492 -1 493 102 104 -1 102 564 103 -1 104 103 565 -1 562 105 107 -1 105 497 106 -1 107 106 566 -1 560 108 110 -1 108 567 109 -1 110 109 495 -1 496 111 113 -1 111 568 112 -1 113 112 569 -1 570 114 116 -1 114 500 115 -1 116 115 571 -1 572 117 119 -1 117 573 118 -1 119 118 498 -1 499 120 122 -1 120 574 121 -1 122 121 575 -1 572 123 125 -1 123 503 124 -1 125 124 576 -1 570 126 128 -1 126 577 127 -1 128 127 501 -1 502 129 131 -1 129 578 130 -1 131 130 579 -1 580 132 134 -1 132 506 133 -1 134 133 581 -1 582 135 137 -1 135 583 136 -1 137 136 504 -1 505 138 140 -1 138 584 139 -1 140 139 585 -1 582 141 143 -1 141 509 142 -1 143 142 586 -1 580 144 146 -1 144 587 145 -1 146 145 507 -1 508 147 149 -1 147 588 148 -1 149 148 589 -1 590 150 152 -1 150 512 151 -1 152 151 591 -1 592 153 155 -1 153 593 154 -1 155 154 510 -1 511 156 158 -1 156 594 157 -1 158 157 595 -1 592 159 161 -1 159 515 160 -1 161 160 596 -1 590 162 164 -1 162 597 163 -1 164 163 513 -1 514 165 167 -1 165 598 166 -1 167 166 599 -1 588 168 170 -1 168 518 169 -1 170 169 543 -1 587 171 173 -1 171 568 172 -1 173 172 516 -1 517 174 176 -1 174 567 175 -1 176 175 544 -1 546 177 179 -1 177 521 178 -1 179 178 589 -1 549 180 182 -1 180 571 181 -1 182 181 519 -1 520 183 185 -1 183 575 184 -1 185 184 586 -1 541 186 188 -1 186 524 187 -1 188 187 595 -1 545 189 191 -1 189 561 190 -1 191 190 522 -1 523 192 194 -1 192 565 193 -1 194 193 591 -1 594 195 197 -1 195 527 196 -1 197 196 547 -1 593 198 200 -1 198 578 199 -1 200 199 525 -1 526 201 203 -1 201 577 202 -1 203 202 548 -1 551 204 206 -1 204 530 205 -1 206 205 585 -1 555 207 209 -1 207 566 208 -1 209 208 528 -1 529 210 212 -1 210 569 211 -1 212 211 581 -1 584 213 215 -1 213 533 214 -1 215 214 557 -1 583 216 218 -1 216 574 217 -1 218 217 531 -1 532 219 221 -1 219 573 220 -1 221 220 558 -1 598 222 224 -1 222 536 223 -1 224 223 553 -1 597 225 227 -1 225 564 226 -1 227 226 534 -1 535 228 230 -1 228 563 229 -1 230 229 554 -1 556 231 233 -1 231 539 232 -1 233 232 599 -1 559 234 236 -1 234 576 235 -1 236 235 537 -1 538 237 239 -1 237 579 238 -1 239 238 596 -1 600 240 241 -1 240 480 2 -1 241 2 482 -1 480 242 0 -1 242 601 243 -1 0 243 481 -1 482 1 245 -1 1 481 244 -1 245 244 602 -1 600 246 247 -1 246 483 5 -1 247 5 485 -1 483 248 3 -1 248 603 249 -1 3 249 484 -1 485 4 251 -1 4 484 250 -1 251 250 604 -1 605 252 253 -1 252 486 8 -1 253 8 488 -1 486 254 6 -1 254 606 255 -1 6 255 487 -1 488 7 257 -1 7 487 256 -1 257 256 607 -1 605 258 259 -1 258 489 11 -1 259 11 491 -1 489 260 9 -1 260 608 261 -1 9 261 490 -1 491 10 263 -1 10 490 262 -1 263 262 609 -1 610 264 265 -1 264 492 14 -1 265 14 494 -1 492 266 12 -1 266 611 267 -1 12 267 493 -1 494 13 269 -1 13 493 268 -1 269 268 612 -1 610 270 271 -1 270 495 17 -1 271 17 497 -1 495 272 15 -1 272 613 273 -1 15 273 496 -1 497 16 275 -1 16 496 274 -1 275 274 614 -1 615 276 277 -1 276 498 20 -1 277 20 500 -1 498 278 18 -1 278 616 279 -1 18 279 499 -1 500 19 281 -1 19 499 280 -1 281 280 617 -1 615 282 283 -1 282 501 23 -1 283 23 503 -1 501 284 21 -1 284 618 285 -1 21 285 502 -1 503 22 287 -1 22 502 286 -1 287 286 619 -1 620 288 289 -1 288 504 26 -1 289 26 506 -1 504 290 24 -1 290 621 291 -1 24 291 505 -1 506 25 293 -1 25 505 292 -1 293 292 622 -1 620 294 295 -1 294 507 29 -1 295 29 509 -1 507 296 27 -1 296 623 297 -1 27 297 508 -1 509 28 299 -1 28 508 298 -1 299 298 624 -1 625 300 301 -1 300 510 32 -1 301 32 512 -1 510 302 30 -1 302 626 303 -1 30 303 511 -1 512 31 305 -1 31 511 304 -1 305 304 627 -1 625 306 307 -1 306 513 35 -1 307 35 515 -1 513 308 33 -1 308 628 309 -1 33 309 514 -1 515 34 311 -1 34 514 310 -1 311 310 629 -1 623 312 313 -1 312 516 38 -1 313 38 518 -1 516 314 36 -1 314 613 315 -1 36 315 517 -1 518 37 317 -1 37 517 316 -1 317 316 601 -1 604 318 319 -1 318 519 41 -1 319 41 521 -1 519 320 39 -1 320 617 321 -1 39 321 520 -1 521 40 323 -1 40 520 322 -1 323 322 624 -1 602 324 325 -1 324 522 44 -1 325 44 524 -1 522 326 42 -1 326 612 327 -1 42 327 523 -1 524 43 329 -1 43 523 328 -1 329 328 627 -1 626 330 331 -1 330 525 47 -1 331 47 527 -1 525 332 45 -1 332 618 333 -1 45 333 526 -1 527 46 335 -1 46 526 334 -1 335 334 603 -1 607 336 337 -1 336 528 50 -1 337 50 530 -1 528 338 48 -1 338 614 339 -1 48 339 529 -1 530 49 341 -1 49 529 340 -1 341 340 622 -1 621 342 343 -1 342 531 53 -1 343 53 533 -1 531 344 51 -1 344 616 345 -1 51 345 532 -1 533 52 347 -1 52 532 346 -1 347 346 608 -1 628 348 349 -1 348 534 56 -1 349 56 536 -1 534 350 54 -1 350 611 351 -1 54 351 535 -1 536 55 353 -1 55 535 352 -1 353 352 606 -1 609 354 355 -1 354 537 59 -1 355 59 539 -1 537 356 57 -1 356 619 357 -1 57 357 538 -1 539 58 359 -1 58 538 358 -1 359 358 629 -1 630 360 361 -1 360 540 62 -1 361 62 541 -1 540 362 60 -1 362 600 241 -1 60 241 482 -1 541 61 363 -1 61 482 245 -1 363 245 602 -1 600 364 240 -1 364 542 65 -1 240 65 480 -1 542 365 63 -1 365 631 366 -1 63 366 543 -1 480 64 242 -1 64 543 367 -1 242 367 601 -1 602 244 368 -1 244 481 68 -1 368 68 545 -1 481 243 66 -1 243 601 369 -1 66 369 544 -1 545 67 371 -1 67 544 370 -1 371 370 632 -1 631 365 372 -1 365 542 71 -1 372 71 546 -1 542 364 69 -1 364 600 247 -1 69 247 485 -1 546 70 373 -1 70 485 251 -1 373 251 604 -1 600 362 246 -1 362 540 74 -1 246 74 483 -1 540 360 72 -1 360 630 374 -1 72 374 547 -1 483 73 248 -1 73 547 375 -1 248 375 603 -1 604 250 376 -1 250 484 77 -1 376 77 549 -1 484 249 75 -1 249 603 377 -1 75 377 548 -1 549 76 379 -1 76 548 378 -1 379 378 633 -1 634 380 381 -1 380 550 80 -1 381 80 551 -1 550 382 78 -1 382 605 253 -1 78 253 488 -1 551 79 383 -1 79 488 257 -1 383 257 607 -1 605 384 252 -1 384 552 83 -1 252 83 486 -1 552 385 81 -1 385 635 386 -1 81 386 553 -1 486 82 254 -1 82 553 387 -1 254 387 606 -1 607 256 388 -1 256 487 86 -1 388 86 555 -1 487 255 84 -1 255 606 389 -1 84 389 554 -1 555 85 391 -1 85 554 390 -1 391 390 636 -1 635 385 392 -1 385 552 89 -1 392 89 556 -1 552 384 87 -1 384 605 259 -1 87 259 491 -1 556 88 393 -1 88 491 263 -1 393 263 609 -1 605 382 258 -1 382 550 92 -1 258 92 489 -1 550 380 90 -1 380 634 394 -1 90 394 557 -1 489 91 260 -1 91 557 395 -1 260 395 608 -1 609 262 396 -1 262 490 95 -1 396 95 559 -1 490 261 93 -1 261 608 397 -1 93 397 558 -1 559 94 399 -1 94 558 398 -1 399 398 637 -1 632 400 401 -1 400 560 98 -1 401 98 561 -1 560 402 96 -1 402 610 265 -1 96 265 494 -1 561 97 403 -1 97 494 269 -1 403 269 612 -1 610 404 264 -1 404 562 101 -1 264 101 492 -1 562 405 99 -1 405 636 406 -1 99 406 563 -1 492 100 266 -1 100 563 407 -1 266 407 611 -1 612 268 408 -1 268 493 104 -1 408 104 565 -1 493 267 102 -1 267 611 409 -1 102 409 564 -1 565 103 411 -1 103 564 410 -1 411 410 638 -1 636 405 412 -1 405 562 107 -1 412 107 566 -1 562 404 105 -1 404 610 271 -1 105 271 497 -1 566 106 413 -1 106 497 275 -1 413 275 614 -1 610 402 270 -1 402 560 110 -1 270 110 495 -1 560 400 108 -1 400 632 414 -1 108 414 567 -1 495 109 272 -1 109 567 415 -1 272 415 613 -1 614 274 416 -1 274 496 113 -1 416 113 569 -1 496 273 111 -1 273 613 417 -1 111 417 568 -1 569 112 419 -1 112 568 418 -1 419 418 639 -1 633 420 421 -1 420 570 116 -1 421 116 571 -1 570 422 114 -1 422 615 277 -1 114 277 500 -1 571 115 423 -1 115 500 281 -1 423 281 617 -1 615 424 276 -1 424 572 119 -1 276 119 498 -1 572 425 117 -1 425 637 426 -1 117 426 573 -1 498 118 278 -1 118 573 427 -1 278 427 616 -1 617 280 428 -1 280 499 122 -1 428 122 575 -1 499 279 120 -1 279 616 429 -1 120 429 574 -1 575 121 431 -1 121 574 430 -1 431 430 640 -1 637 425 432 -1 425 572 125 -1 432 125 576 -1 572 424 123 -1 424 615 283 -1 123 283 503 -1 576 124 433 -1 124 503 287 -1 433 287 619 -1 615 422 282 -1 422 570 128 -1 282 128 501 -1 570 420 126 -1 420 633 434 -1 126 434 577 -1 501 127 284 -1 127 577 435 -1 284 435 618 -1 619 286 436 -1 286 502 131 -1 436 131 579 -1 502 285 129 -1 285 618 437 -1 129 437 578 -1 579 130 439 -1 130 578 438 -1 439 438 641 -1 639 440 441 -1 440 580 134 -1 441 134 581 -1 580 442 132 -1 442 620 289 -1 132 289 506 -1 581 133 443 -1 133 506 293 -1 443 293 622 -1 620 444 288 -1 444 582 137 -1 288 137 504 -1 582 445 135 -1 445 640 446 -1 135 446 583 -1 504 136 290 -1 136 583 447 -1 290 447 621 -1 622 292 448 -1 292 505 140 -1 448 140 585 -1 505 291 138 -1 291 621 449 -1 138 449 584 -1 585 139 451 -1 139 584 450 -1 451 450 634 -1 640 445 452 -1 445 582 143 -1 452 143 586 -1 582 444 141 -1 444 620 295 -1 141 295 509 -1 586 142 453 -1 142 509 299 -1 453 299 624 -1 620 442 294 -1 442 580 146 -1 294 146 507 -1 580 440 144 -1 440 639 454 -1 144 454 587 -1 507 145 296 -1 145 587 455 -1 296 455 623 -1 624 298 456 -1 298 508 149 -1 456 149 589 -1 508 297 147 -1 297 623 457 -1 147 457 588 -1 589 148 459 -1 148 588 458 -1 459 458 631 -1 638 460 461 -1 460 590 152 -1 461 152 591 -1 590 462 150 -1 462 625 301 -1 150 301 512 -1 591 151 463 -1 151 512 305 -1 463 305 627 -1 625 464 300 -1 464 592 155 -1 300 155 510 -1 592 465 153 -1 465 641 466 -1 153 466 593 -1 510 154 302 -1 154 593 467 -1 302 467 626 -1 627 304 468 -1 304 511 158 -1 468 158 595 -1 511 303 156 -1 303 626 469 -1 156 469 594 -1 595 157 471 -1 157 594 470 -1 471 470 630 -1 641 465 472 -1 465 592 161 -1 472 161 596 -1 592 464 159 -1 464 625 307 -1 159 307 515 -1 596 160 473 -1 160 515 311 -1 473 311 629 -1 625 462 306 -1 462 590 164 -1 306 164 513 -1 590 460 162 -1 460 638 474 -1 162 474 597 -1 513 163 308 -1 163 597 475 -1 308 475 628 -1 629 310 476 -1 310 514 167 -1 476 167 599 -1 514 309 165 -1 309 628 477 -1 165 477 598 -1 599 166 479 -1 166 598 478 -1 479 478 635 -1 631 458 366 -1 458 588 170 -1 366 170 543 -1 588 457 168 -1 457 623 313 -1 168 313 518 -1 543 169 367 -1 169 518 317 -1 367 317 601 -1 623 455 312 -1 455 587 173 -1 312 173 516 -1 587 454 171 -1 454 639 418 -1 171 418 568 -1 516 172 314 -1 172 568 417 -1 314 417 613 -1 601 316 369 -1 316 517 176 -1 369 176 544 -1 517 315 174 -1 315 613 415 -1 174 415 567 -1 544 175 370 -1 175 567 414 -1 370 414 632 -1 631 372 459 -1 372 546 179 -1 459 179 589 -1 546 373 177 -1 373 604 319 -1 177 319 521 -1 589 178 456 -1 178 521 323 -1 456 323 624 -1 604 376 318 -1 376 549 182 -1 318 182 519 -1 549 379 180 -1 379 633 421 -1 180 421 571 -1 519 181 320 -1 181 571 423 -1 320 423 617 -1 624 322 453 -1 322 520 185 -1 453 185 586 -1 520 321 183 -1 321 617 428 -1 183 428 575 -1 586 184 452 -1 184 575 431 -1 452 431 640 -1 630 361 471 -1 361 541 188 -1 471 188 595 -1 541 363 186 -1 363 602 325 -1 186 325 524 -1 595 187 468 -1 187 524 329 -1 468 329 627 -1 602 368 324 -1 368 545 191 -1 324 191 522 -1 545 371 189 -1 371 632 401 -1 189 401 561 -1 522 190 326 -1 190 561 403 -1 326 403 612 -1 627 328 463 -1 328 523 194 -1 463 194 591 -1 523 327 192 -1 327 612 408 -1 192 408 565 -1 591 193 461 -1 193 565 411 -1 461 411 638 -1 630 470 374 -1 470 594 197 -1 374 197 547 -1 594 469 195 -1 469 626 331 -1 195 331 527 -1 547 196 375 -1 196 527 335 -1 375 335 603 -1 626 467 330 -1 467 593 200 -1 330 200 525 -1 593 466 198 -1 466 641 438 -1 198 438 578 -1 525 199 332 -1 199 578 437 -1 332 437 618 -1 603 334 377 -1 334 526 203 -1 377 203 548 -1 526 333 201 -1 333 618 435 -1 201 435 577 -1 548 202 378 -1 202 577 434 -1 378 434 633 -1 634 381 451 -1 381 551 206 -1 451 206 585 -1 551 383 204 -1 383 607 337 -1 204 337 530 -1 585 205 448 -1 205 530 341 -1 448 341 622 -1 607 388 336 -1 388 555 209 -1 336 209 528 -1 555 391 207 -1 391 636 412 -1 207 412 566 -1 528 208 338 -1 208 566 413 -1 338 413 614 -1 622 340 443 -1 340 529 212 -1 443 212 581 -1 529 339 210 -1 339 614 416 -1 210 416 569 -1 581 211 441 -1 211 569 419 -1 441 419 639 -1 634 450 394 -1 450 584 215 -1 394 215 557 -1 584 449 213 -1 449 621 343 -1 213 343 533 -1 557 214 395 -1 214 533 347 -1 395 347 608 -1 621 447 342 -1 447 583 218 -1 342 218 531 -1 583 446 216 -1 446 640 430 -1 216 430 574 -1 531 217 344 -1 217 574 429 -1 344 429 616 -1 608 346 397 -1 346 532 221 -1 397 221 558 -1 532 345 219 -1 345 616 427 -1 219 427 573 -1 558 220 398 -1 220 573 426 -1 398 426 637 -1 635 478 386 -1 478 598 224 -1 386 224 553 -1 598 477 222 -1 477 628 349 -1 222 349 536 -1 553 223 387 -1 223 536 353 -1 387 353 606 -1 628 475 348 -1 475 597 227 -1 348 227 534 -1 597 474 225 -1 474 638 410 -1 225 410 564 -1 534 226 350 -1 226 564 409 -1 350 409 611 -1 606 352 389 -1 352 535 230 -1 389 230 554 -1 535 351 228 -1 351 611 407 -1 228 407 563 -1 554 229 390 -1 229 563 406 -1 390 406 636 -1 635 392 479 -1 392 556 233 -1 479 233 599 -1 556 393 231 -1 393 609 355 -1 231 355 539 -1 599 232 476 -1 232 539 359 -1 476 359 629 -1 609 396 354 -1 396 559 236 -1 354 236 537 -1 559 399 234 -1 399 637 432 -1 234 432 576 -1 537 235 356 -1 235 576 433 -1 356 433 619 -1 629 358 473 -1 358 538 239 -1 473 239 596 -1 538 357 237 -1 357 619 436 -1 237 436 579 -1 596 238 472 -1 238 579 439 -1 472 439 641 -1'");
          }
          if (polygonsRadioButton.isSelected())
          {
               newContent.append(" solid='false'");
          }
          newContent.append(">");
          newContent.append("\n      <Coordinate point='0.399607 0.912982 0.0823236 0.399607 0.912982 -0.0823236 0.266405 0.963861 0 -0.399607 0.912982 -0.0823236 -0.399607 0.912982 0.0823236 -0.266405 0.963861 0 0.399607 -0.912982 -0.0823236 0.399607 -0.912982 0.0823236 0.266405 -0.963861 0 -0.399607 -0.912982 0.0823236 -0.399607 -0.912982 -0.0823236 -0.266405 -0.963861 0 0.912982 -0.0823236 -0.399607 0.912982 0.0823236 -0.399607 0.963861 0 -0.266405 0.912982 0.0823236 0.399607 0.912982 -0.0823236 0.399607 0.963861 0 0.266405 -0.912982 -0.0823236 0.399607 -0.912982 0.0823236 0.399607 -0.963861 0 0.266405 -0.912982 0.0823236 -0.399607 -0.912982 -0.0823236 -0.399607 -0.963861 0 -0.266405 -0.0823236 -0.399607 0.912982 0.0823236 -0.399607 0.912982 0 -0.266405 0.963861 0.0823236 0.399607 0.912982 -0.0823236 0.399607 0.912982 0 0.266405 0.963861 -0.0823236 0.399607 -0.912982 0.0823236 0.399607 -0.912982 0 0.266405 -0.963861 0.0823236 -0.399607 -0.912982 -0.0823236 -0.399607 -0.912982 0 -0.266405 -0.963861 0.646578 0.513375 0.564254 0.564254 0.646578 0.513375 0.513375 0.564254 0.646578 -0.646578 0.513375 0.564254 -0.513375 0.564254 0.646578 -0.564254 0.646578 0.513375 0.646578 0.513375 -0.564254 0.513375 0.564254 -0.646578 0.564254 0.646578 -0.513375 -0.646578 0.513375 -0.564254 -0.564254 0.646578 -0.513375 -0.513375 0.564254 -0.646578 0.646578 -0.513375 0.564254 0.513375 -0.564254 0.646578 0.564254 -0.646578 0.513375 -0.646578 -0.513375 0.564254 -0.564254 -0.646578 0.513375 -0.513375 -0.564254 0.646578 0.646578 -0.513375 -0.564254 0.564254 -0.646578 -0.513375 0.513375 -0.564254 -0.646578 -0.646578 -0.513375 -0.564254 -0.513375 -0.564254 -0.646578 -0.564254 -0.646578 -0.513375 0.132792 0.966393 -0.220117 0.264083 0.916244 -0.301259 0.131655 0.924305 -0.358229 0.131655 0.924305 0.358229 0.264083 0.916244 0.301259 0.132792 0.966393 0.220117 0.62024 0.780204 0.0811418 0.711282 0.702907 0 0.62024 0.780204 -0.0811418 -0.132792 0.966393 0.220117 -0.264083 0.916244 0.301259 -0.131655 0.924305 0.358229 -0.131655 0.924305 -0.358229 -0.264083 0.916244 -0.301259 -0.132792 0.966393 -0.220117 -0.62024 0.780204 -0.0811418 -0.711282 0.702907 0 -0.62024 0.780204 0.0811418 0.132792 -0.966393 0.220117 0.264083 -0.916244 0.301259 0.131655 -0.924305 0.358229 0.131655 -0.924305 -0.358229 0.264083 -0.916244 -0.301259 0.132792 -0.966393 -0.220117 0.62024 -0.780204 -0.0811418 0.711282 -0.702907 0 0.62024 -0.780204 0.0811418 -0.132792 -0.966393 -0.220117 -0.264083 -0.916244 -0.301259 -0.131655 -0.924305 -0.358229 -0.131655 -0.924305 0.358229 -0.264083 -0.916244 0.301259 -0.132792 -0.966393 0.220117 -0.62024 -0.780204 0.0811418 -0.711282 -0.702907 0 -0.62024 -0.780204 -0.0811418 0.966393 0.220117 -0.132792 0.916244 0.301259 -0.264083 0.924305 0.358229 -0.131655 0.924305 -0.358229 -0.131655 0.916244 -0.301259 -0.264083 0.966393 -0.220117 -0.132792 0.780204 -0.0811418 -0.62024 0.702907 0 -0.711282 0.780204 0.0811418 -0.62024 0.966393 -0.220117 0.132792 0.916244 -0.301259 0.264083 0.924305 -0.358229 0.131655 0.924305 0.358229 0.131655 0.916244 0.301259 0.264083 0.966393 0.220117 0.132792 0.780204 0.0811418 0.62024 0.702907 0 0.711282 0.780204 -0.0811418 0.62024 -0.966393 0.220117 0.132792 -0.916244 0.301259 0.264083 -0.924305 0.358229 0.131655 -0.924305 -0.358229 0.131655 -0.916244 -0.301259 0.264083 -0.966393 -0.220117 0.132792 -0.780204 -0.0811418 0.62024 -0.702907 0 0.711282 -0.780204 0.0811418 0.62024 -0.966393 -0.220117 -0.132792 -0.916244 -0.301259 -0.264083 -0.924305 -0.358229 -0.131655 -0.924305 0.358229 -0.131655 -0.916244 0.301259 -0.264083 -0.966393 0.220117 -0.132792 -0.780204 0.0811418 -0.62024 -0.702907 0 -0.711282 -0.780204 -0.0811418 -0.62024 0.220117 -0.132792 0.966393 0.301259 -0.264083 0.916244 0.358229 -0.131655 0.924305 -0.358229 -0.131655 0.924305 -0.301259 -0.264083 0.916244 -0.220117 -0.132792 0.966393 -0.0811418 -0.62024 0.780204 0 -0.711282 0.702907 0.0811418 -0.62024 0.780204 -0.220117 0.132792 0.966393 -0.301259 0.264083 0.916244 -0.358229 0.131655 0.924305 0.358229 0.131655 0.924305 0.301259 0.264083 0.916244 0.220117 0.132792 0.966393 0.0811418 0.62024 0.780204 0 0.711282 0.702907 -0.0811418 0.62024 0.780204 0.220117 0.132792 -0.966393 0.301259 0.264083 -0.916244 0.358229 0.131655 -0.924305 -0.358229 0.131655 -0.924305 -0.301259 0.264083 -0.916244 -0.220117 0.132792 -0.966393 -0.0811418 0.62024 -0.780204 0 0.711282 -0.702907 0.0811418 0.62024 -0.780204 -0.220117 -0.132792 -0.966393 -0.301259 -0.264083 -0.916244 -0.358229 -0.131655 -0.924305 0.358229 -0.131655 -0.924305 0.301259 -0.264083 -0.916244 0.220117 -0.132792 -0.966393 0.0811418 -0.62024 -0.780204 0 -0.711282 -0.702907 -0.0811418 -0.62024 -0.780204 0.296005 0.70231 0.647412 0.346153 0.783452 0.516122 0.213023 0.792649 0.571252 0.571252 0.213023 0.792649 0.647412 0.296005 0.70231 0.516122 0.346153 0.783452 0.783452 0.516122 0.346153 0.792649 0.571252 0.213023 0.70231 0.647412 0.296005 -0.346153 0.783452 0.516122 -0.296005 0.70231 0.647412 -0.213023 0.792649 0.571252 -0.792649 0.571252 0.213023 -0.783452 0.516122 0.346153 -0.70231 0.647412 0.296005 -0.647412 0.296005 0.70231 -0.571252 0.213023 0.792649 -0.516122 0.346153 0.783452 0.346153 0.783452 -0.516122 0.296005 0.70231 -0.647412 0.213023 0.792649 -0.571252 0.792649 0.571252 -0.213023 0.783452 0.516122 -0.346153 0.70231 0.647412 -0.296005 0.647412 0.296005 -0.70231 0.571252 0.213023 -0.792649 0.516122 0.346153 -0.783452 -0.296005 0.70231 -0.647412 -0.346153 0.783452 -0.516122 -0.213023 0.792649 -0.571252 -0.571252 0.213023 -0.792649 -0.647412 0.296005 -0.70231 -0.516122 0.346153 -0.783452 -0.783452 0.516122 -0.346153 -0.792649 0.571252 -0.213023 -0.70231 0.647412 -0.296005 0.346153 -0.783452 0.516122 0.296005 -0.70231 0.647412 0.213023 -0.792649 0.571252 0.792649 -0.571252 0.213023 0.783452 -0.516122 0.346153 0.70231 -0.647412 0.296005 0.647412 -0.296005 0.70231 0.571252 -0.213023 0.792649 0.516122 -0.346153 0.783452 -0.296005 -0.70231 0.647412 -0.346153 -0.783452 0.516122 -0.213023 -0.792649 0.571252 -0.571252 -0.213023 0.792649 -0.647412 -0.296005 0.70231 -0.516122 -0.346153 0.783452 -0.783452 -0.516122 0.346153 -0.792649 -0.571252 0.213023 -0.70231 -0.647412 0.296005 0.296005 -0.70231 -0.647412 0.346153 -0.783452 -0.516122 0.213023 -0.792649 -0.571252 0.571252 -0.213023 -0.792649 0.647412 -0.296005 -0.70231 0.516122 -0.346153 -0.783452 0.783452 -0.516122 -0.346153 0.792649 -0.571252 -0.213023 0.70231 -0.647412 -0.296005 -0.346153 -0.783452 -0.516122 -0.296005 -0.70231 -0.647412 -0.213023 -0.792649 -0.571252 -0.792649 -0.571252 -0.213023 -0.783452 -0.516122 -0.346153 -0.70231 -0.647412 -0.296005 -0.647412 -0.296005 -0.70231 -0.571252 -0.213023 -0.792649 -0.516122 -0.346153 -0.783452 0.133071 0.987688 0.0822425 0.133071 0.987688 -0.0822425 0.386187 0.891007 0.238677 0.519258 0.840178 0.156434 0.519258 0.840178 -0.156434 0.386187 0.891007 -0.238677 -0.133071 0.987688 -0.0822425 -0.133071 0.987688 0.0822425 -0.386187 0.891007 -0.238677 -0.519258 0.840178 -0.156434 -0.519258 0.840178 0.156434 -0.386187 0.891007 0.238677 0.133071 -0.987688 -0.0822425 0.133071 -0.987688 0.0822425 0.386187 -0.891007 -0.238677 0.519258 -0.840178 -0.156434 0.519258 -0.840178 0.156434 0.386187 -0.891007 0.238677 -0.133071 -0.987688 0.0822425 -0.133071 -0.987688 -0.0822425 -0.386187 -0.891007 0.238677 -0.519258 -0.840178 0.156434 -0.519258 -0.840178 -0.156434 -0.386187 -0.891007 -0.238677 0.987688 -0.0822425 -0.133071 0.987688 0.0822425 -0.133071 0.891007 -0.238677 -0.386187 0.840178 -0.156434 -0.519258 0.840178 0.156434 -0.519258 0.891007 0.238677 -0.386187 0.987688 0.0822425 0.133071 0.987688 -0.0822425 0.133071 0.891007 0.238677 0.386187 0.840178 0.156434 0.519258 0.840178 -0.156434 0.519258 0.891007 -0.238677 0.386187 -0.987688 -0.0822425 0.133071 -0.987688 0.0822425 0.133071 -0.891007 -0.238677 0.386187 -0.840178 -0.156434 0.519258 -0.840178 0.156434 0.519258 -0.891007 0.238677 0.386187 -0.987688 0.0822425 -0.133071 -0.987688 -0.0822425 -0.133071 -0.891007 0.238677 -0.386187 -0.840178 0.156434 -0.519258 -0.840178 -0.156434 -0.519258 -0.891007 -0.238677 -0.386187 -0.0822425 -0.133071 0.987688 0.0822425 -0.133071 0.987688 -0.238677 -0.386187 0.891007 -0.156434 -0.519258 0.840178 0.156434 -0.519258 0.840178 0.238677 -0.386187 0.891007 0.0822425 0.133071 0.987688 -0.0822425 0.133071 0.987688 0.238677 0.386187 0.891007 0.156434 0.519258 0.840178 -0.156434 0.519258 0.840178 -0.238677 0.386187 0.891007 -0.0822425 0.133071 -0.987688 0.0822425 0.133071 -0.987688 -0.238677 0.386187 -0.891007 -0.156434 0.519258 -0.840178 0.156434 0.519258 -0.840178 0.238677 0.386187 -0.891007 0.0822425 -0.133071 -0.987688 -0.0822425 -0.133071 -0.987688 0.238677 -0.386187 -0.891007 0.156434 -0.519258 -0.840178 -0.156434 -0.519258 -0.840178 -0.238677 -0.386187 -0.891007 0.453991 0.46843 0.757935 0.371748 0.601501 0.707107 0.707107 0.371748 0.601501 0.757935 0.453991 0.46843 0.601501 0.707107 0.371748 0.46843 0.757935 0.453991 -0.601501 0.707107 0.371748 -0.46843 0.757935 0.453991 -0.757935 0.453991 0.46843 -0.707107 0.371748 0.601501 -0.453991 0.46843 0.757935 -0.371748 0.601501 0.707107 0.601501 0.707107 -0.371748 0.46843 0.757935 -0.453991 0.757935 0.453991 -0.46843 0.707107 0.371748 -0.601501 0.453991 0.46843 -0.757935 0.371748 0.601501 -0.707107 -0.453991 0.46843 -0.757935 -0.371748 0.601501 -0.707107 -0.707107 0.371748 -0.601501 -0.757935 0.453991 -0.46843 -0.601501 0.707107 -0.371748 -0.46843 0.757935 -0.453991 0.601501 -0.707107 0.371748 0.46843 -0.757935 0.453991 0.757935 -0.453991 0.46843 0.707107 -0.371748 0.601501 0.453991 -0.46843 0.757935 0.371748 -0.601501 0.707107 -0.453991 -0.46843 0.757935 -0.371748 -0.601501 0.707107 -0.707107 -0.371748 0.601501 -0.757935 -0.453991 0.46843 -0.601501 -0.707107 0.371748 -0.46843 -0.757935 0.453991 0.453991 -0.46843 -0.757935 0.371748 -0.601501 -0.707107 0.707107 -0.371748 -0.601501 0.757935 -0.453991 -0.46843 0.601501 -0.707107 -0.371748 0.46843 -0.757935 -0.453991 -0.601501 -0.707107 -0.371748 -0.46843 -0.757935 -0.453991 -0.757935 -0.453991 -0.46843 -0.707107 -0.371748 -0.601501 -0.453991 -0.46843 -0.757935 -0.371748 -0.601501 -0.707107 0 0.915043 -0.403355 0.1312 0.864929 -0.484442 0 0.990439 -0.137952 0.383614 0.843911 -0.375039 0 0.990439 0.137952 0 0.915043 0.403355 0.1312 0.864929 0.484442 0.383614 0.843911 0.375039 0.606825 0.758652 -0.237086 0.606825 0.758652 0.237086 0.783843 0.615642 0.0810863 0.783843 0.615642 -0.0810863 -0.1312 0.864929 0.484442 -0.383614 0.843911 0.375039 -0.1312 0.864929 -0.484442 -0.383614 0.843911 -0.375039 -0.606825 0.758652 0.237086 -0.606825 0.758652 -0.237086 -0.783843 0.615642 -0.0810863 -0.783843 0.615642 0.0810863 0 -0.915043 0.403355 0.1312 -0.864929 0.484442 0 -0.990439 0.137952 0.383614 -0.843911 0.375039 0 -0.990439 -0.137952 0 -0.915043 -0.403355 0.1312 -0.864929 -0.484442 0.383614 -0.843911 -0.375039 0.606825 -0.758652 0.237086 0.606825 -0.758652 -0.237086 0.783843 -0.615642 -0.0810863 0.783843 -0.615642 0.0810863 -0.1312 -0.864929 -0.484442 -0.383614 -0.843911 -0.375039 -0.1312 -0.864929 0.484442 -0.383614 -0.843911 0.375039 -0.606825 -0.758652 -0.237086 -0.606825 -0.758652 0.237086 -0.783843 -0.615642 0.0810863 -0.783843 -0.615642 -0.0810863 0.915043 0.403355 0 0.864929 0.484442 -0.1312 0.990439 0.137952 0 0.843911 0.375039 -0.383614 0.990439 -0.137952 0 0.915043 -0.403355 0 0.864929 -0.484442 -0.1312 0.843911 -0.375039 -0.383614 0.758652 0.237086 -0.606825 0.758652 -0.237086 -0.606825 0.615642 -0.0810863 -0.783843 0.615642 0.0810863 -0.783843 0.864929 -0.484442 0.1312 0.843911 -0.375039 0.383614 0.864929 0.484442 0.1312 0.843911 0.375039 0.383614 0.758652 -0.237086 0.606825 0.758652 0.237086 0.606825 0.615642 0.0810863 0.783843 0.615642 -0.0810863 0.783843 -0.915043 0.403355 0 -0.864929 0.484442 0.1312 -0.990439 0.137952 0 -0.843911 0.375039 0.383614 -0.990439 -0.137952 0 -0.915043 -0.403355 0 -0.864929 -0.484442 0.1312 -0.843911 -0.375039 0.383614 -0.758652 0.237086 0.606825 -0.758652 -0.237086 0.606825 -0.615642 -0.0810863 0.783843 -0.615642 0.0810863 0.783843 -0.864929 -0.484442 -0.1312 -0.843911 -0.375039 -0.383614 -0.864929 0.484442 -0.1312 -0.843911 0.375039 -0.383614 -0.758652 -0.237086 -0.606825 -0.758652 0.237086 -0.606825 -0.615642 0.0810863 -0.783843 -0.615642 -0.0810863 -0.783843 0.403355 0 0.915043 0.484442 -0.1312 0.864929 0.137952 0 0.990439 0.375039 -0.383614 0.843911 -0.137952 0 0.990439 -0.403355 0 0.915043 -0.484442 -0.1312 0.864929 -0.375039 -0.383614 0.843911 0.237086 -0.606825 0.758652 -0.237086 -0.606825 0.758652 -0.0810863 -0.783843 0.615642 0.0810863 -0.783843 0.615642 -0.484442 0.1312 0.864929 -0.375039 0.383614 0.843911 0.484442 0.1312 0.864929 0.375039 0.383614 0.843911 -0.237086 0.606825 0.758652 0.237086 0.606825 0.758652 0.0810863 0.783843 0.615642 -0.0810863 0.783843 0.615642 0.403355 0 -0.915043 0.484442 0.1312 -0.864929 0.137952 0 -0.990439 0.375039 0.383614 -0.843911 -0.137952 0 -0.990439 -0.403355 0 -0.915043 -0.484442 0.1312 -0.864929 -0.375039 0.383614 -0.843911 0.237086 0.606825 -0.758652 -0.237086 0.606825 -0.758652 -0.0810863 0.783843 -0.615642 0.0810863 0.783843 -0.615642 -0.484442 -0.1312 -0.864929 -0.375039 -0.383614 -0.843911 0.484442 -0.1312 -0.864929 0.375039 -0.383614 -0.843911 -0.237086 -0.606825 -0.758652 0.237086 -0.606825 -0.758652 0.0810863 -0.783843 -0.615642 -0.0810863 -0.783843 -0.615642 0.262866 0.951057 0.16246 0.525731 0.850651 0 0.262866 0.951057 -0.16246 -0.262866 0.951057 -0.16246 -0.525731 0.850651 0 -0.262866 0.951057 0.16246 0.262866 -0.951057 -0.16246 0.525731 -0.850651 0 0.262866 -0.951057 0.16246 -0.262866 -0.951057 0.16246 -0.525731 -0.850651 0 -0.262866 -0.951057 -0.16246 0.951057 -0.16246 -0.262866 0.850651 0 -0.525731 0.951057 0.16246 -0.262866 0.951057 0.16246 0.262866 0.850651 0 0.525731 0.951057 -0.16246 0.262866 -0.951057 -0.16246 0.262866 -0.850651 0 0.525731 -0.951057 0.16246 0.262866 -0.951057 0.16246 -0.262866 -0.850651 0 -0.525731 -0.951057 -0.16246 -0.262866 -0.16246 -0.262866 0.951057 0 -0.525731 0.850651 0.16246 -0.262866 0.951057 0.16246 0.262866 0.951057 0 0.525731 0.850651 -0.16246 0.262866 0.951057 -0.16246 0.262866 -0.951057 0 0.525731 -0.850651 0.16246 0.262866 -0.951057 0.16246 -0.262866 -0.951057 0 -0.525731 -0.850651 -0.16246 -0.262866 -0.951057 0.587785 0.425325 0.688191 0.688191 0.587785 0.425325 0.425325 0.688191 0.587785 -0.688191 0.587785 0.425325 -0.587785 0.425325 0.688191 -0.425325 0.688191 0.587785 0.688191 0.587785 -0.425325 0.587785 0.425325 -0.688191 0.425325 0.688191 -0.587785 -0.587785 0.425325 -0.688191 -0.688191 0.587785 -0.425325 -0.425325 0.688191 -0.587785 0.688191 -0.587785 0.425325 0.587785 -0.425325 0.688191 0.425325 -0.688191 0.587785 -0.587785 -0.425325 0.688191 -0.688191 -0.587785 0.425325 -0.425325 -0.688191 0.587785 0.587785 -0.425325 -0.688191 0.688191 -0.587785 -0.425325 0.425325 -0.688191 -0.587785 -0.688191 -0.587785 -0.425325 -0.587785 -0.425325 -0.688191 -0.425325 -0.688191 -0.587785 0 0.961938 -0.273267 0.259892 0.862668 -0.433889 0 0.961938 0.273267 0.259892 0.862668 0.433889 0.702046 0.69378 0.160622 0.702046 0.69378 -0.160622 -0.259892 0.862668 0.433889 -0.259892 0.862668 -0.433889 -0.702046 0.69378 -0.160622 -0.702046 0.69378 0.160622 0 -0.961938 0.273267 0.259892 -0.862668 0.433889 0 -0.961938 -0.273267 0.259892 -0.862668 -0.433889 0.702046 -0.69378 -0.160622 0.702046 -0.69378 0.160622 -0.259892 -0.862668 -0.433889 -0.259892 -0.862668 0.433889 -0.702046 -0.69378 0.160622 -0.702046 -0.69378 -0.160622 0.961938 0.273267 0 0.862668 0.433889 -0.259892 0.961938 -0.273267 0 0.862668 -0.433889 -0.259892 0.69378 -0.160622 -0.702046 0.69378 0.160622 -0.702046 0.862668 -0.433889 0.259892 0.862668 0.433889 0.259892 0.69378 0.160622 0.702046 0.69378 -0.160622 0.702046 -0.961938 0.273267 0 -0.862668 0.433889 0.259892 -0.961938 -0.273267 0 -0.862668 -0.433889 0.259892 -0.69378 -0.160622 0.702046 -0.69378 0.160622 0.702046 -0.862668 -0.433889 -0.259892 -0.862668 0.433889 -0.259892 -0.69378 0.160622 -0.702046 -0.69378 -0.160622 -0.702046 0.273267 0 0.961938 0.433889 -0.259892 0.862668 -0.273267 0 0.961938 -0.433889 -0.259892 0.862668 -0.160622 -0.702046 0.69378 0.160622 -0.702046 0.69378 -0.433889 0.259892 0.862668 0.433889 0.259892 0.862668 0.160622 0.702046 0.69378 -0.160622 0.702046 0.69378 0.273267 0 -0.961938 0.433889 0.259892 -0.862668 -0.273267 0 -0.961938 -0.433889 0.259892 -0.862668 -0.160622 0.702046 -0.69378 0.160622 0.702046 -0.69378 -0.433889 -0.259892 -0.862668 0.433889 -0.259892 -0.862668 0.160622 -0.702046 -0.69378 -0.160622 -0.702046 -0.69378 0 1 0 0.5 0.809017 0.309017 0.5 0.809017 -0.309017 -0.5 0.809017 -0.309017 -0.5 0.809017 0.309017 0 -1 0 0.5 -0.809017 -0.309017 0.5 -0.809017 0.309017 -0.5 -0.809017 0.309017 -0.5 -0.809017 -0.309017 1 0 0 0.809017 -0.309017 -0.5 0.809017 0.309017 -0.5 0.809017 0.309017 0.5 0.809017 -0.309017 0.5 -1 0 0 -0.809017 -0.309017 0.5 -0.809017 0.309017 0.5 -0.809017 0.309017 -0.5 -0.809017 -0.309017 -0.5 0 0 1 -0.309017 -0.5 0.809017 0.309017 -0.5 0.809017 0.309017 0.5 0.809017 -0.309017 0.5 0.809017 0 0 -1 -0.309017 0.5 -0.809017 0.309017 0.5 -0.809017 0.309017 -0.5 -0.809017 -0.309017 -0.5 -0.809017 0 0.850651 -0.525731 0 0.850651 0.525731 0.850651 0.525731 0 -0.850651 0.525731 0 0 -0.850651 0.525731 0 -0.850651 -0.525731 0.850651 -0.525731 0 -0.850651 -0.525731 0 0.525731 0 -0.850651 0.525731 0 0.850651 -0.525731 0 0.850651 -0.525731 0 -0.850651'/>")
                    .append("\n    </").append(geometryNodeName).append(">");
          if (polygonsRadioButton.isSelected())
               newContent.append(APPEARANCE_CONTENT_MATERIAL_COLOR);
          else if (linesRadioButton.isSelected())
               newContent.append(APPEARANCE_CONTENT_EMISSIVE_LINES);
          else newContent.append(APPEARANCE_CONTENT_EMISSIVE_POINTS);
      }
      else if (icosahedronRadioButton.isSelected() && (levelComboBox.getSelectedIndex() == 2))
      {
          newContent.append("\n    <!-- Icosahedron level 2 example: http://x3dGraphics.com/examples/X3dForAdvancedModeling/GeometricShapes/IcosahedronSubdivisionLevel2.x3d -->")
                    .append("\n    <").append(geometryNodeName);
          if (!pointsRadioButton.isSelected())
          {
               newContent.append(" coordIndex='0 1 2 -1 3 4 5 -1 6 7 8 -1 9 10 11 -1 12 13 14 -1 15 16 17 -1 18 19 20 -1 21 22 23 -1 24 25 26 -1 27 28 29 -1 30 31 32 -1 33 34 35 -1 36 37 38 -1 39 40 41 -1 42 43 44 -1 45 46 47 -1 48 49 50 -1 51 52 53 -1 54 55 56 -1 57 58 59 -1 60 2 61 -1 62 63 0 -1 1 64 65 -1 62 5 66 -1 60 67 3 -1 4 68 69 -1 70 8 71 -1 72 73 6 -1 7 74 75 -1 72 11 76 -1 70 77 9 -1 10 78 79 -1 80 14 81 -1 82 83 12 -1 13 84 85 -1 82 17 86 -1 80 87 15 -1 16 88 89 -1 90 20 91 -1 92 93 18 -1 19 94 95 -1 92 23 96 -1 90 97 21 -1 22 98 99 -1 100 26 101 -1 102 103 24 -1 25 104 105 -1 102 29 106 -1 100 107 27 -1 28 108 109 -1 110 32 111 -1 112 113 30 -1 31 114 115 -1 112 35 116 -1 110 117 33 -1 34 118 119 -1 108 38 63 -1 107 88 36 -1 37 87 64 -1 66 41 109 -1 69 91 39 -1 40 95 106 -1 61 44 115 -1 65 81 42 -1 43 85 111 -1 114 47 67 -1 113 98 45 -1 46 97 68 -1 71 50 105 -1 75 86 48 -1 49 89 101 -1 104 53 77 -1 103 94 51 -1 52 93 78 -1 118 56 73 -1 117 84 54 -1 55 83 74 -1 76 59 119 -1 79 96 57 -1 58 99 116 -1 120 0 2 -1 0 121 1 -1 2 1 122 -1 120 3 5 -1 3 123 4 -1 5 4 124 -1 125 6 8 -1 6 126 7 -1 8 7 127 -1 125 9 11 -1 9 128 10 -1 11 10 129 -1 130 12 14 -1 12 131 13 -1 14 13 132 -1 130 15 17 -1 15 133 16 -1 17 16 134 -1 135 18 20 -1 18 136 19 -1 20 19 137 -1 135 21 23 -1 21 138 22 -1 23 22 139 -1 140 24 26 -1 24 141 25 -1 26 25 142 -1 140 27 29 -1 27 143 28 -1 29 28 144 -1 145 30 32 -1 30 146 31 -1 32 31 147 -1 145 33 35 -1 33 148 34 -1 35 34 149 -1 143 36 38 -1 36 133 37 -1 38 37 121 -1 124 39 41 -1 39 137 40 -1 41 40 144 -1 122 42 44 -1 42 132 43 -1 44 43 147 -1 146 45 47 -1 45 138 46 -1 47 46 123 -1 127 48 50 -1 48 134 49 -1 50 49 142 -1 141 51 53 -1 51 136 52 -1 53 52 128 -1 148 54 56 -1 54 131 55 -1 56 55 126 -1 129 57 59 -1 57 139 58 -1 59 58 149 -1 150 60 61 -1 60 120 2 -1 61 2 122 -1 120 62 0 -1 62 151 63 -1 0 63 121 -1 122 1 65 -1 1 121 64 -1 65 64 152 -1 151 62 66 -1 62 120 5 -1 66 5 124 -1 120 60 3 -1 60 150 67 -1 3 67 123 -1 124 4 69 -1 4 123 68 -1 69 68 153 -1 154 70 71 -1 70 125 8 -1 71 8 127 -1 125 72 6 -1 72 155 73 -1 6 73 126 -1 127 7 75 -1 7 126 74 -1 75 74 156 -1 155 72 76 -1 72 125 11 -1 76 11 129 -1 125 70 9 -1 70 154 77 -1 9 77 128 -1 129 10 79 -1 10 128 78 -1 79 78 157 -1 152 80 81 -1 80 130 14 -1 81 14 132 -1 130 82 12 -1 82 156 83 -1 12 83 131 -1 132 13 85 -1 13 131 84 -1 85 84 158 -1 156 82 86 -1 82 130 17 -1 86 17 134 -1 130 80 15 -1 80 152 87 -1 15 87 133 -1 134 16 89 -1 16 133 88 -1 89 88 159 -1 153 90 91 -1 90 135 20 -1 91 20 137 -1 135 92 18 -1 92 157 93 -1 18 93 136 -1 137 19 95 -1 19 136 94 -1 95 94 160 -1 157 92 96 -1 92 135 23 -1 96 23 139 -1 135 90 21 -1 90 153 97 -1 21 97 138 -1 139 22 99 -1 22 138 98 -1 99 98 161 -1 159 100 101 -1 100 140 26 -1 101 26 142 -1 140 102 24 -1 102 160 103 -1 24 103 141 -1 142 25 105 -1 25 141 104 -1 105 104 154 -1 160 102 106 -1 102 140 29 -1 106 29 144 -1 140 100 27 -1 100 159 107 -1 27 107 143 -1 144 28 109 -1 28 143 108 -1 109 108 151 -1 158 110 111 -1 110 145 32 -1 111 32 147 -1 145 112 30 -1 112 161 113 -1 30 113 146 -1 147 31 115 -1 31 146 114 -1 115 114 150 -1 161 112 116 -1 112 145 35 -1 116 35 149 -1 145 110 33 -1 110 158 117 -1 33 117 148 -1 149 34 119 -1 34 148 118 -1 119 118 155 -1 151 108 63 -1 108 143 38 -1 63 38 121 -1 143 107 36 -1 107 159 88 -1 36 88 133 -1 121 37 64 -1 37 133 87 -1 64 87 152 -1 151 66 109 -1 66 124 41 -1 109 41 144 -1 124 69 39 -1 69 153 91 -1 39 91 137 -1 144 40 106 -1 40 137 95 -1 106 95 160 -1 150 61 115 -1 61 122 44 -1 115 44 147 -1 122 65 42 -1 65 152 81 -1 42 81 132 -1 147 43 111 -1 43 132 85 -1 111 85 158 -1 150 114 67 -1 114 146 47 -1 67 47 123 -1 146 113 45 -1 113 161 98 -1 45 98 138 -1 123 46 68 -1 46 138 97 -1 68 97 153 -1 154 71 105 -1 71 127 50 -1 105 50 142 -1 127 75 48 -1 75 156 86 -1 48 86 134 -1 142 49 101 -1 49 134 89 -1 101 89 159 -1 154 104 77 -1 104 141 53 -1 77 53 128 -1 141 103 51 -1 103 160 94 -1 51 94 136 -1 128 52 78 -1 52 136 93 -1 78 93 157 -1 155 118 73 -1 118 148 56 -1 73 56 126 -1 148 117 54 -1 117 158 84 -1 54 84 131 -1 126 55 74 -1 55 131 83 -1 74 83 156 -1 155 76 119 -1 76 129 59 -1 119 59 149 -1 129 79 57 -1 79 157 96 -1 57 96 139 -1 149 58 116 -1 58 139 99 -1 116 99 161 -1'");
          }
          if (polygonsRadioButton.isSelected())
          {
               newContent.append(" solid='false'");
          }
          newContent.append(">");
          newContent.append("\n      <Coordinate point='0.262866 0.951057 0.16246 0.525731 0.850651 0 0.262866 0.951057 -0.16246 -0.262866 0.951057 -0.16246 -0.525731 0.850651 0 -0.262866 0.951057 0.16246 0.262866 -0.951057 -0.16246 0.525731 -0.850651 0 0.262866 -0.951057 0.16246 -0.262866 -0.951057 0.16246 -0.525731 -0.850651 0 -0.262866 -0.951057 -0.16246 0.951057 -0.16246 -0.262866 0.850651 0 -0.525731 0.951057 0.16246 -0.262866 0.951057 0.16246 0.262866 0.850651 0 0.525731 0.951057 -0.16246 0.262866 -0.951057 -0.16246 0.262866 -0.850651 0 0.525731 -0.951057 0.16246 0.262866 -0.951057 0.16246 -0.262866 -0.850651 0 -0.525731 -0.951057 -0.16246 -0.262866 -0.16246 -0.262866 0.951057 0 -0.525731 0.850651 0.16246 -0.262866 0.951057 0.16246 0.262866 0.951057 0 0.525731 0.850651 -0.16246 0.262866 0.951057 -0.16246 0.262866 -0.951057 0 0.525731 -0.850651 0.16246 0.262866 -0.951057 0.16246 -0.262866 -0.951057 0 -0.525731 -0.850651 -0.16246 -0.262866 -0.951057 0.587785 0.425325 0.688191 0.688191 0.587785 0.425325 0.425325 0.688191 0.587785 -0.688191 0.587785 0.425325 -0.587785 0.425325 0.688191 -0.425325 0.688191 0.587785 0.688191 0.587785 -0.425325 0.587785 0.425325 -0.688191 0.425325 0.688191 -0.587785 -0.587785 0.425325 -0.688191 -0.688191 0.587785 -0.425325 -0.425325 0.688191 -0.587785 0.688191 -0.587785 0.425325 0.587785 -0.425325 0.688191 0.425325 -0.688191 0.587785 -0.587785 -0.425325 0.688191 -0.688191 -0.587785 0.425325 -0.425325 -0.688191 0.587785 0.587785 -0.425325 -0.688191 0.688191 -0.587785 -0.425325 0.425325 -0.688191 -0.587785 -0.688191 -0.587785 -0.425325 -0.587785 -0.425325 -0.688191 -0.425325 -0.688191 -0.587785 0 0.961938 -0.273267 0.259892 0.862668 -0.433889 0 0.961938 0.273267 0.259892 0.862668 0.433889 0.702046 0.69378 0.160622 0.702046 0.69378 -0.160622 -0.259892 0.862668 0.433889 -0.259892 0.862668 -0.433889 -0.702046 0.69378 -0.160622 -0.702046 0.69378 0.160622 0 -0.961938 0.273267 0.259892 -0.862668 0.433889 0 -0.961938 -0.273267 0.259892 -0.862668 -0.433889 0.702046 -0.69378 -0.160622 0.702046 -0.69378 0.160622 -0.259892 -0.862668 -0.433889 -0.259892 -0.862668 0.433889 -0.702046 -0.69378 0.160622 -0.702046 -0.69378 -0.160622 0.961938 0.273267 0 0.862668 0.433889 -0.259892 0.961938 -0.273267 0 0.862668 -0.433889 -0.259892 0.69378 -0.160622 -0.702046 0.69378 0.160622 -0.702046 0.862668 -0.433889 0.259892 0.862668 0.433889 0.259892 0.69378 0.160622 0.702046 0.69378 -0.160622 0.702046 -0.961938 0.273267 0 -0.862668 0.433889 0.259892 -0.961938 -0.273267 0 -0.862668 -0.433889 0.259892 -0.69378 -0.160622 0.702046 -0.69378 0.160622 0.702046 -0.862668 -0.433889 -0.259892 -0.862668 0.433889 -0.259892 -0.69378 0.160622 -0.702046 -0.69378 -0.160622 -0.702046 0.273267 0 0.961938 0.433889 -0.259892 0.862668 -0.273267 0 0.961938 -0.433889 -0.259892 0.862668 -0.160622 -0.702046 0.69378 0.160622 -0.702046 0.69378 -0.433889 0.259892 0.862668 0.433889 0.259892 0.862668 0.160622 0.702046 0.69378 -0.160622 0.702046 0.69378 0.273267 0 -0.961938 0.433889 0.259892 -0.862668 -0.273267 0 -0.961938 -0.433889 0.259892 -0.862668 -0.160622 0.702046 -0.69378 0.160622 0.702046 -0.69378 -0.433889 -0.259892 -0.862668 0.433889 -0.259892 -0.862668 0.160622 -0.702046 -0.69378 -0.160622 -0.702046 -0.69378 0 1 0 0.5 0.809017 0.309017 0.5 0.809017 -0.309017 -0.5 0.809017 -0.309017 -0.5 0.809017 0.309017 0 -1 0 0.5 -0.809017 -0.309017 0.5 -0.809017 0.309017 -0.5 -0.809017 0.309017 -0.5 -0.809017 -0.309017 1 0 0 0.809017 -0.309017 -0.5 0.809017 0.309017 -0.5 0.809017 0.309017 0.5 0.809017 -0.309017 0.5 -1 0 0 -0.809017 -0.309017 0.5 -0.809017 0.309017 0.5 -0.809017 0.309017 -0.5 -0.809017 -0.309017 -0.5 0 0 1 -0.309017 -0.5 0.809017 0.309017 -0.5 0.809017 0.309017 0.5 0.809017 -0.309017 0.5 0.809017 0 0 -1 -0.309017 0.5 -0.809017 0.309017 0.5 -0.809017 0.309017 -0.5 -0.809017 -0.309017 -0.5 -0.809017 0 0.850651 -0.525731 0 0.850651 0.525731 0.850651 0.525731 0 -0.850651 0.525731 0 0 -0.850651 0.525731 0 -0.850651 -0.525731 0.850651 -0.525731 0 -0.850651 -0.525731 0 0.525731 0 -0.850651 0.525731 0 0.850651 -0.525731 0 0.850651 -0.525731 0 -0.850651'/>")
                    .append("\n    </").append(geometryNodeName).append(">");
          if (polygonsRadioButton.isSelected())
               newContent.append(APPEARANCE_CONTENT_MATERIAL_COLOR);
          else if (linesRadioButton.isSelected())
               newContent.append(APPEARANCE_CONTENT_EMISSIVE_LINES);
          else newContent.append(APPEARANCE_CONTENT_EMISSIVE_POINTS);
      }
      else if (icosahedronRadioButton.isSelected() && (levelComboBox.getSelectedIndex() == 1))
      {
          newContent.append("\n    <!-- Icosahedron level 1 example: http://x3dGraphics.com/examples/X3dForAdvancedModeling/GeometricShapes/IcosahedronSubdivisionLevel1.x3d -->")
                    .append("\n    <").append(geometryNodeName);
          if (!pointsRadioButton.isSelected())
          {
               newContent.append(" coordIndex='0 1 2 -1 0 3 4 -1 5 6 7 -1 5 8 9 -1 10 11 12 -1 10 13 14 -1 15 16 17 -1 15 18 19 -1 20 21 22 -1 20 23 24 -1 25 26 27 -1 25 28 29 -1 23 13 1 -1 4 17 24 -1 2 12 27 -1 26 18 3 -1 7 14 22 -1 21 16 8 -1 28 11 6 -1 9 19 29 -1 30 0 2 -1 0 31 1 -1 2 1 32 -1 31 0 4 -1 0 30 3 -1 4 3 33 -1 34 5 7 -1 5 35 6 -1 7 6 36 -1 35 5 9 -1 5 34 8 -1 9 8 37 -1 32 10 12 -1 10 36 11 -1 12 11 38 -1 36 10 14 -1 10 32 13 -1 14 13 39 -1 33 15 17 -1 15 37 16 -1 17 16 40 -1 37 15 19 -1 15 33 18 -1 19 18 41 -1 39 20 22 -1 20 40 21 -1 22 21 34 -1 40 20 24 -1 20 39 23 -1 24 23 31 -1 38 25 27 -1 25 41 26 -1 27 26 30 -1 41 25 29 -1 25 38 28 -1 29 28 35 -1 31 23 1 -1 23 39 13 -1 1 13 32 -1 31 4 24 -1 4 33 17 -1 24 17 40 -1 30 2 27 -1 2 32 12 -1 27 12 38 -1 30 26 3 -1 26 41 18 -1 3 18 33 -1 34 7 22 -1 7 36 14 -1 22 14 39 -1 34 21 8 -1 21 40 16 -1 8 16 37 -1 35 28 6 -1 28 38 11 -1 6 11 36 -1 35 9 29 -1 9 37 19 -1 29 19 41 -1'");
          }
          if (polygonsRadioButton.isSelected())
          {
               newContent.append(" solid='false'");
          }
          newContent.append(">");
          newContent.append("\n      <Coordinate point='0 1 0 0.5 0.809017 0.309017 0.5 0.809017 -0.309017 -0.5 0.809017 -0.309017 -0.5 0.809017 0.309017 0 -1 0 0.5 -0.809017 -0.309017 0.5 -0.809017 0.309017 -0.5 -0.809017 0.309017 -0.5 -0.809017 -0.309017 1 0 0 0.809017 -0.309017 -0.5 0.809017 0.309017 -0.5 0.809017 0.309017 0.5 0.809017 -0.309017 0.5 -1 0 0 -0.809017 -0.309017 0.5 -0.809017 0.309017 0.5 -0.809017 0.309017 -0.5 -0.809017 -0.309017 -0.5 0 0 1 -0.309017 -0.5 0.809017 0.309017 -0.5 0.809017 0.309017 0.5 0.809017 -0.309017 0.5 0.809017 0 0 -1 -0.309017 0.5 -0.809017 0.309017 0.5 -0.809017 0.309017 -0.5 -0.809017 -0.309017 -0.5 -0.809017 0 0.850651 -0.525731 0 0.850651 0.525731 0.850651 0.525731 0 -0.850651 0.525731 0 0 -0.850651 0.525731 0 -0.850651 -0.525731 0.850651 -0.525731 0 -0.850651 -0.525731 0 0.525731 0 -0.850651 0.525731 0 0.850651 -0.525731 0 0.850651 -0.525731 0 -0.850651'/>")
                    .append("\n    </").append(geometryNodeName).append(">");
          if (polygonsRadioButton.isSelected())
               newContent.append(APPEARANCE_CONTENT_MATERIAL_COLOR);
          else if (linesRadioButton.isSelected())
               newContent.append(APPEARANCE_CONTENT_EMISSIVE_LINES);
          else newContent.append(APPEARANCE_CONTENT_EMISSIVE_POINTS);
      }
      else if (icosahedronRadioButton.isSelected()) // (levelComboBox.getSelectedIndex() == 0)
      {
          newContent.append("\n    <!-- Icosahedron example: http://x3dGraphics.com/examples/X3dForAdvancedModeling/GeometricShapes/IcosahedronSubdivisionLevel0.x3d -->")
                    .append("\n    <").append(geometryNodeName);
          if (!pointsRadioButton.isSelected())
          {
               newContent.append(" coordIndex='0 1 2 -1 1 0 3 -1 4 5 6 -1 5 4 7 -1 2 6 8 -1 6 2 9 -1 3 7 10 -1 7 3 11 -1 9 10 4 -1 10 9 1 -1 8 11 0 -1 11 8 5 -1 1 9 2 -1 1 3 10 -1 0 2 8 -1 0 11 3 -1 4 6 9 -1 4 10 7 -1 5 8 6 -1 5 7 11 -1'");
          }
          if (polygonsRadioButton.isSelected())
          {
               newContent.append(" solid='false'");
          }
          newContent.append(">");
          newContent.append("\n      <Coordinate point='0 1.61803 -1 0 1.61803 1 1.61803 1 0 -1.61803 1 0 0 -1.61803 1 0 -1.61803 -1 1.61803 -1 0 -1.61803 -1 0 1 0 -1.61803 1 0 1.61803 -1 0 1.61803 -1 0 -1.61803'/>")
                    .append("\n    </").append(geometryNodeName).append(">");
          if (polygonsRadioButton.isSelected())
               newContent.append(APPEARANCE_CONTENT_MATERIAL_COLOR);
          else if (linesRadioButton.isSelected())
               newContent.append(APPEARANCE_CONTENT_EMISSIVE_LINES);
          else newContent.append(APPEARANCE_CONTENT_EMISSIVE_POINTS);
      }
      else if (tetrahedronRadioButton.isSelected())
      {
          newContent.append("\n    <!-- Tetrahedron example: http://x3dGraphics.com/examples/X3dForAdvancedModeling/GeometricShapes/Tetrahedron.x3d -->")
                    .append("\n    <").append(geometryNodeName);
          if (!pointsRadioButton.isSelected())
          {
               newContent.append(" coordIndex='0 1 2 -1 0 2 3 -1 0 3 1 -1 3 2 1 -1'");
          }
          if (polygonsRadioButton.isSelected())
          {
              newContent.append(" creaseAngle='0.785398' solid='false'");
          }
          newContent.append(">");
          newContent.append("\n      <Coordinate point='1 1 1 -1 1 -1 -1 -1 1 1 -1 -1'/>")
                    .append("\n    </").append(geometryNodeName).append(">");
          if (polygonsRadioButton.isSelected())
               newContent.append(APPEARANCE_CONTENT_MATERIAL_COLOR);
          else if (linesRadioButton.isSelected())
               newContent.append(APPEARANCE_CONTENT_EMISSIVE_LINES);
          else newContent.append(APPEARANCE_CONTENT_EMISSIVE_POINTS);
      }
      else if (capsuleRadioButton.isSelected())
      {
          NsidedCapsule nsidedCapsule = new NsidedCapsule();
          String geometryType;
          readNumberOfPointsTextField ();
          if (pointsRadioButton.isSelected())
          {
               geometryType = "points";
          }
          else if (linesRadioButton.isSelected())
          {
               geometryType = "lines";
          }
          else // (polygonsRadioButton.isSelected())
          {
               geometryType = "polygons";
          }
          int numberOfLevels = 5;
          if (levelComboBox.getSelectedItem().toString().length() > 0)
          {
              try 
              {
                  numberOfLevels = Integer.parseInt(levelComboBox.getSelectedItem().toString());
              }
              catch (NumberFormatException e)
              {
                  numberOfLevels = 5;
              }
              if (numberOfLevels <  5) numberOfLevels =  5;
              if (numberOfLevels > 10) numberOfLevels = 10;
          }
//          newContent.append("\n    <!-- Capsule with ").append(numberOfPoints).append("-sided circumference -->");
          newContent.append("\n")
                    .append(nsidedCapsule.capsuleGeometry (numberOfPoints, radius, height, 
                                numberOfLevels, 
                                geometryType, // points lines polygons
                                true,   // top,
                                true,   // side 
                                true)); // bottom
            if (geometryType.equals("points") || geometryType.equals("lines"))
                 newContent.append(APPEARANCE_CONTENT_EMISSIVE_LINES);
            else newContent.append(APPEARANCE_CONTENT_MATERIAL);
      }
      else if (roundedRectangleRadioButton.isSelected())
      {
          RoundedRectangle roundedRectangle = new RoundedRectangle();
          float depth = Float.parseFloat(depthTextField.getText());
          
          String pointsList = roundedRectangle.roundedRectanglePointsArray(radius, height, depth, SHAPE.ARRAY_3D_XY_PLANE); // precompute number of points
          newContent.append("\n    <!-- RoundedRectangle with ").append(roundedRectangle.getNumberOfPoints()).append(" perimeter points + 4 internal points on each level -->");
          newContent.append("\n    <").append(geometryNodeName);
          if (!pointsRadioButton.isSelected())
          {
              newContent.append(" coordIndex='").append(roundedRectangle.roundedRectangleIndexArray(radius, height, depth, SHAPE.ARRAY_3D_XY_PLANE));
              newContent.append("'");
          }
          if (polygonsRadioButton.isSelected())
          {
              newContent.append(" creaseAngle='6.28' solid='false'");
          }
          newContent.append(">");
          newContent.append("\n      <Coordinate point='");
          newContent.append(pointsList);
          newContent.append("'/>");
          newContent.append("\n    </").append(geometryNodeName).append(">");
          if (polygonsRadioButton.isSelected())
               newContent.append(APPEARANCE_CONTENT_MATERIAL_COLOR);
          else if (linesRadioButton.isSelected())
               newContent.append(APPEARANCE_CONTENT_EMISSIVE_LINES);
          else newContent.append(APPEARANCE_CONTENT_EMISSIVE_POINTS);
      }
      else if (roundedRectangle2dRadioButton.isSelected())
      {
          RoundedRectangle roundedRectangle = new RoundedRectangle();
//          float depth = Float.parseFloat(depthTextField.getText());

          String pointsList = roundedRectangle.roundedRectanglePointsArray2D(radius, height); // precompute number of points
          newContent.append("\n    <!-- RoundedRectangle2D with ").append(roundedRectangle.getNumberOfPoints()).append(" perimeter points + 4 internal points -->");
          newContent.append("\n    <").append(geometryNodeName);
          if (!pointsRadioButton.isSelected())
          {
              newContent.append(" coordIndex='").append(roundedRectangle.roundedRectangleIndexArray2D(radius, height));
              newContent.append("'");
          }
          if (polygonsRadioButton.isSelected())
          {
              newContent.append(" creaseAngle='6.28' solid='false'");
          }
          newContent.append(">");
          newContent.append("\n      <Coordinate point='");
          newContent.append(pointsList);
          newContent.append("'/>");
          newContent.append("\n    </").append(geometryNodeName).append(">");
          if (polygonsRadioButton.isSelected())
               newContent.append(APPEARANCE_CONTENT_MATERIAL_COLOR);
          else if (linesRadioButton.isSelected())
               newContent.append(APPEARANCE_CONTENT_EMISSIVE_LINES);
          else newContent.append(APPEARANCE_CONTENT_EMISSIVE_POINTS);
      }
      else if (arc2dRadioButton.isSelected())
      {
          newContent.append("\n    <Arc2D radius='").append(radius).append("' startAngle='0' endAngle='3.14159'/>");
          newContent.append(APPEARANCE_CONTENT_EMISSIVE_LINES);
      }
      else if (arcClose2dRadioButton.isSelected())
      {
          newContent.append("\n    <ArcClose2D closureType='PIE' radius='").append(radius).append("' startAngle='0' endAngle='3.14159' solid='false'/>");
          newContent.append(APPEARANCE_CONTENT_EMISSIVE_LINES);
      }
      else if (circle2dRadioButton.isSelected())
      {
          newContent.append("\n    <Circle2D radius='").append(radius).append("'/>");
          newContent.append(APPEARANCE_CONTENT_EMISSIVE_LINES);
      }
      else if (disk2dRadioButton.isSelected())
      {
          newContent.append("\n    <Disk2D");
          newContent.append(" innerRadius='").append(innerRadius);
          newContent.append("' outerRadius='").append(outerRadius);
          newContent.append("' solid='false'/>");
          newContent.append(APPEARANCE_CONTENT_MATERIAL);
      }
      else if (polyline2dRadioButton.isSelected())
      {
          newContent.append("\n    <Polyline2D lineSegments='-4 1 -3 2 -2 1 0 3 4 0'/>");
          newContent.append(APPEARANCE_CONTENT_EMISSIVE_LINES);
      }
      else if (polypoint2dRadioButton.isSelected())
      {
          newContent.append("\n    <Polypoint2D point='-4 1 -3 2 -2 1 0 3 4 0'/>");
          newContent.append(APPEARANCE_CONTENT_EMISSIVE_LINES);
      }
      else if (rectangle2dRadioButton.isSelected())
      {
          newContent.append("\n    <Rectangle2D size='4 2' solid='false'/>");
          newContent.append(APPEARANCE_CONTENT_MATERIAL);
      }
      else if (triangleSet2dRadioButton.isSelected())
      {
          newContent.append("\n    <TriangleSet2D vertices='0 0 -0.5 1 -1.2 0.1 0 0 2 0.5 0.5 2 0 0 -0.3 -3.5 3 -2' solid='false'/>");
          newContent.append(APPEARANCE_CONTENT_MATERIAL);
      }
      else // geometry radio button selection logic problem?
      {
          newContent.append("\n    <!-- TODO authors can add a single geometry node here -->");
          newContent.append(APPEARANCE_CONTENT_MATERIAL_COLOR);
      }
      // now update shape before serializing
      shape.setContent(newContent.toString());
  }

  private void readNumberOfPointsTextField ()
  {
       String cellContents;

       cellContents = numberOfPointsTextField.getText().trim();
       if (cellContents.isEmpty() || cellContents.contains("point"))
       {
           // no numberOfPoints value found
           numberOfPoints = -1; // force default number of segments
           return;
       }
       try
       {
           numberOfPoints = (new X3DPrimitiveTypes.SFInt32(cellContents)).getValue();
       }
       catch (Exception e)
       {
           numberOfPoints = -1; // force default number of segments
       }
  }

  @Override
  public void unloadInput() throws IllegalArgumentException
  {
    setContent(); // check buttons and set content accordingly

    unLoadDEFUSE();

    shape.setBboxCenterX(bboxCenterXTF.getText().trim());
    shape.setBboxCenterY(bboxCenterYTF.getText().trim());
    shape.setBboxCenterZ(bboxCenterZTF.getText().trim());
    shape.setBboxSizeX(bboxSizeXTF.getText().trim());
    shape.setBboxSizeY(bboxSizeYTF.getText().trim());
    shape.setBboxSizeZ(bboxSizeZTF.getText().trim());
  }

}
