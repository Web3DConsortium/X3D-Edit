/*
Copyright (c) 1995-2024 held by the author(s).  All rights reserved.
 
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

import java.awt.Color;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.text.JTextComponent;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * HANIMSEGMENTCustomizer.java
 * Created on 29 May 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class HANIMSEGMENTCustomizer extends BaseCustomizer
{
  private HANIMSEGMENT hAnimSegment;
  private JTextComponent target;
  private JTextField[] moiArray;
  
  private String  localPrefix = new String();
     
    /**
     * Creates new form HANIMSEGMENTCustomizer
     * @param segment data of interest
     * @param target Swing component of interest
     */
    public HANIMSEGMENTCustomizer(HANIMSEGMENT segment, JTextComponent target)
  {
    super(segment);
    hAnimSegment = segment;
    this.target = target;
    
    HelpCtx.setHelpIDString(this, "HANIMSEGMENT_ELEM_HELPID");

    hAnimSegment.setVisualizationSelectionAvailable(true); // must precede initComponents() interface initialization
    hAnimSegment.setVisualizationTooltip("Show center axes, add wireframe Box and axes to show boundingBox center and size (if defined)");
    
    initComponents();
    
    super.getDEFUSEpanel().setContainerFieldChoices(HANIMSEGMENT_CONTAINERFIELD_CHOICES, HANIMSEGMENT_CONTAINERFIELD_TOOLTIPS);
    super.getDEFUSEpanel().setContainerField(hAnimSegment.getContainerField()); // reset value to match updated JComboBox data model
    // DEFUSEpanel initialization must NOT be repeated or else array of choices will be overwritten
    
    descriptionTF.setText(hAnimSegment.getDescription());
    if (!hAnimSegment.getDescription().isBlank())
    {
        checkX3D4FieldSupportDialog("HAnimSegment","description"); // X3D4.0 field
        descriptionTF.setText(hAnimSegment.getDescription());
    }
    
    nameComboBox.setSelectedItem(hAnimSegment.getName());

    moiArray = new JTextField[] {
      moiTF0,moiTF1,moiTF2,
      moiTF3,moiTF4,moiTF5,
      moiTF6,moiTF7,moiTF8,
    };
 
    centerOfMassTFX.setText(hAnimSegment.getCenterOfMassX());
    centerOfMassTFY.setText(hAnimSegment.getCenterOfMassY());
    centerOfMassTFZ.setText(hAnimSegment.getCenterOfMassZ());
    
    String[] mois = hAnimSegment.getMomentsOfInertia();
    int i=0;
    for(String s : mois)
      moiArray[i++].setText(s);
    
    massTF.setText(hAnimSegment.getMass());
    bboxCenterTFX.setText(hAnimSegment.getBboxCenterX());
    bboxCenterTFY.setText(hAnimSegment.getBboxCenterY());
    bboxCenterTFZ.setText(hAnimSegment.getBboxCenterZ());
    bboxSizeTFX.setText(hAnimSegment.getBboxSizeX());
    bboxSizeTFY.setText(hAnimSegment.getBboxSizeY());
    bboxSizeTFZ.setText(hAnimSegment.getBboxSizeZ());

    if (!hAnimSegment.getName().isBlank() &&
        super.getDEFUSEpanel().getDEF().endsWith(hAnimSegment.getName())) // successful name match
    {
        localPrefix = super.getDEFUSEpanel().getDEF().substring(0,super.getDEFUSEpanel().getDEF().lastIndexOf(hAnimSegment.getName()));
    }
    nameComboBox.setSelectedItem(hAnimSegment.getName());
//    checkHumanoidRootSpelling();
    setDefaultDEFname();
    nameWarningLabel.setText(""); // setup for initialization
    checkNameDefMatchRules();
  }
  private void setDefaultDEFname()
  {
    String newDefaultName = nameComboBox.getSelectedItem().toString().trim();
    if (!localPrefix.isBlank())
    {
        if (!localPrefix.endsWith("_"))
             localPrefix += "_";
        newDefaultName = localPrefix + newDefaultName;
    }
    else newDefaultName = NbBundle.getMessage(getClass(),getNameKey()) + newDefaultName;
    // TODO if available, use prefix from ancestor HAnimHumanoid
    
    super.getDEFUSEpanel().setDefaultDEFname(newDefaultName);
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
        nameLabel = new javax.swing.JLabel();
        nameComboBox = new javax.swing.JComboBox<>();
        nameWarningLabel = new javax.swing.JLabel();
        descriptionLabel = new javax.swing.JLabel();
        descriptionTF = new javax.swing.JTextField();
        centerOfMassLabel = new javax.swing.JLabel();
        centerOfMassTFX = new javax.swing.JTextField();
        centerOfMassTFY = new javax.swing.JTextField();
        centerOfMassTFZ = new javax.swing.JTextField();
        massLabel = new javax.swing.JLabel();
        massTF = new javax.swing.JTextField();
        momentsOfInertiaLabel = new javax.swing.JLabel();
        moiTF0 = new javax.swing.JTextField();
        moiTF1 = new javax.swing.JTextField();
        moiTF2 = new javax.swing.JTextField();
        moiTF3 = new javax.swing.JTextField();
        moiTF4 = new javax.swing.JTextField();
        moiTF5 = new javax.swing.JTextField();
        moiTF6 = new javax.swing.JTextField();
        moiTF7 = new javax.swing.JTextField();
        moiTF8 = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        bboxCenterTFX = new javax.swing.JTextField();
        bboxCenterTFY = new javax.swing.JTextField();
        bboxCenterTFZ = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        bboxSizeTFX = new javax.swing.JTextField();
        bboxSizeTFY = new javax.swing.JTextField();
        bboxSizeTFZ = new javax.swing.JTextField();
        nodeHintPanel = new javax.swing.JPanel();
        hintLabel = new javax.swing.JLabel();
        spacerLabel = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(640, 500));
        setLayout(new java.awt.GridBagLayout());

        dEFUSEpanel1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dEFUSEpanel1MouseClicked(evt);
            }
        });
        dEFUSEpanel1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                dEFUSEpanel1KeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel1, gridBagConstraints);

        nameLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        nameLabel.setText("name");
        nameLabel.setToolTipText("Unique name attribute must be defined so that HAnimSegment node can be identified at runtime for animation purposes");
        nameLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                nameLabelMouseEntered(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nameLabel, gridBagConstraints);

        nameComboBox.setEditable(true);
        nameComboBox.setFont(new java.awt.Font("Courier New", 1, 12)); // NOI18N
        nameComboBox.setModel(new DefaultComboBoxModel<String>(HANIMSEGMENT_NAME_CHOICES));
        nameComboBox.setToolTipText("select HAminSegment name");
        nameComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                nameComboBoxItemStateChanged(evt);
            }
        });
        nameComboBox.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                nameComboBoxFocusGained(evt);
            }
        });
        nameComboBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                nameComboBoxMouseEntered(evt);
            }
        });
        nameComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameComboBoxActionPerformed(evt);
            }
        });
        nameComboBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nameComboBoxKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nameComboBox, gridBagConstraints);

        nameWarningLabel.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        nameWarningLabel.setText("name must have a legal value. no name attribute is allowed for USE nodes.");
        nameWarningLabel.setToolTipText("HAnim has strict rules for name and DEF");
        nameWarningLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                nameWarningLabelMouseEntered(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nameWarningLabel, gridBagConstraints);

        descriptionLabel.setForeground(new java.awt.Color(0, 153, 153));
        descriptionLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        descriptionLabel.setText("description");
        descriptionLabel.setToolTipText("Text description to be displayed for action of this node");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
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
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(descriptionTF, gridBagConstraints);

        centerOfMassLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        centerOfMassLabel.setText("centerOfMass");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(centerOfMassLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(centerOfMassTFX, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(centerOfMassTFY, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(centerOfMassTFZ, gridBagConstraints);

        massLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        massLabel.setText("mass");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(massLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(massTF, gridBagConstraints);

        momentsOfInertiaLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        momentsOfInertiaLabel.setText("momentsOfInertia");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(momentsOfInertiaLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(moiTF0, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(moiTF1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(moiTF2, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(moiTF3, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(moiTF4, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(moiTF5, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(moiTF6, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(moiTF7, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(moiTF8, gridBagConstraints);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel7.setText("bboxCenter");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(jLabel7, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterTFX, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterTFY, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterTFZ, gridBagConstraints);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel8.setText("bboxSize");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(jLabel8, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxSizeTFX, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxSizeTFY, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxSizeTFZ, gridBagConstraints);

        nodeHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nodeHintPanel.setPreferredSize(new java.awt.Dimension(625, 110));
        nodeHintPanel.setLayout(new java.awt.GridBagLayout());

        hintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel.setText("<html> <p align=\"center\"><b>HAnimSegment</b> holds Shape geometry for each body segment, showing a visual representation of a skeleton.  \n Parent/child translation and rotation relationships are defined in ancestor/descendant <b>HAnimJoint</b> nodes. </p>   \n <br />   \n<p align=\"center\"><b>HAnimSegment</b> contains <b>Coordinate</b> (or <b>CoordinateDouble</b>) with containerField='coord', also can hold\n <br />\ngrouping nodes or <b>Shape</b> with containerField='children'  and <b>HAnimDisplacer</b> with containerField='displacers'. </p>");
        hintLabel.setToolTipText("close this panel to add children nodes");
        hintLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        hintLabel.setMaximumSize(new java.awt.Dimension(2147483647, 1666666));
        hintLabel.setMinimumSize(new java.awt.Dimension(28, 16));
        hintLabel.setPreferredSize(new java.awt.Dimension(600, 100));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        nodeHintPanel.add(hintLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nodeHintPanel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridheight = 7;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(spacerLabel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void nameComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameComboBoxActionPerformed
        setDefaultDEFname ();
        checkNameDefMatchRules();
    }//GEN-LAST:event_nameComboBoxActionPerformed

    private void descriptionTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_descriptionTFActionPerformed
    {//GEN-HEADEREND:event_descriptionTFActionPerformed
        checkX3D4FieldSupportDialog("HAnimSegment","description"); // X3D4.0 field
    }//GEN-LAST:event_descriptionTFActionPerformed

    private void nameComboBoxFocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event_nameComboBoxFocusGained
    {//GEN-HEADEREND:event_nameComboBoxFocusGained
        setDefaultDEFname ();
        checkNameDefMatchRules();
    }//GEN-LAST:event_nameComboBoxFocusGained

    private void nameComboBoxKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_nameComboBoxKeyReleased
    {//GEN-HEADEREND:event_nameComboBoxKeyReleased
        setDefaultDEFname ();
        checkNameDefMatchRules();
    }//GEN-LAST:event_nameComboBoxKeyReleased

    private void nameComboBoxItemStateChanged(java.awt.event.ItemEvent evt)//GEN-FIRST:event_nameComboBoxItemStateChanged
    {//GEN-HEADEREND:event_nameComboBoxItemStateChanged
        setDefaultDEFname ();
        checkNameDefMatchRules();
    }//GEN-LAST:event_nameComboBoxItemStateChanged

    private void nameComboBoxMouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event_nameComboBoxMouseEntered
    {//GEN-HEADEREND:event_nameComboBoxMouseEntered
        setDefaultDEFname ();
        checkNameDefMatchRules();
    }//GEN-LAST:event_nameComboBoxMouseEntered

    private void dEFUSEpanel1KeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_dEFUSEpanel1KeyReleased
    {//GEN-HEADEREND:event_dEFUSEpanel1KeyReleased
        setDefaultDEFname ();
        checkNameDefMatchRules();
    }//GEN-LAST:event_dEFUSEpanel1KeyReleased

    private void nameWarningLabelMouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event_nameWarningLabelMouseEntered
    {//GEN-HEADEREND:event_nameWarningLabelMouseEntered
        setDefaultDEFname ();
        checkNameDefMatchRules();
    }//GEN-LAST:event_nameWarningLabelMouseEntered

    private void nameLabelMouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event_nameLabelMouseEntered
    {//GEN-HEADEREND:event_nameLabelMouseEntered
        setDefaultDEFname ();
        checkNameDefMatchRules();
    }//GEN-LAST:event_nameLabelMouseEntered

    private void dEFUSEpanel1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dEFUSEpanel1MouseClicked
        setDefaultDEFname ();
        checkNameDefMatchRules();
    }//GEN-LAST:event_dEFUSEpanel1MouseClicked

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_HANIMSEGMENT";
  }
    private void checkNameDefMatchRules()
    {
        String NAME_REQUIRED      = "name must have a legal value";
        String NAME_RULE_MATCH    = "successful name match: DEF = prefix + name";
        String NAME_RULE_MISMATCH = "name mismatch: DEF must match prefix + name";
  
        String DEF    = super.getDEFUSEpanel().getDEF();
        String name   = nameComboBox.getSelectedItem().toString();
        
        Color  burntorange = new Color(191,  87,  0);
        Color   darkorange = new Color(255, 140,  0);
        Color   darkgreen  = new Color( 21,  71, 52);
        
        nameComboBox.setEnabled(true); // default
        if (getDEFUSEpanel().isUSE())
        {
            // no name for USE node
            nameWarningLabel.setText("no name attribute is allowed for USE node");
            nameWarningLabel.setForeground(Color.BLACK);
            nameComboBox.setBackground(Color.WHITE);
            nameComboBox.setEnabled(false);
        }
        else if (name.isBlank())
        {
            nameWarningLabel.setText(NAME_REQUIRED);
            nameWarningLabel.setForeground(darkorange);
            nameComboBox.setBackground(Color.YELLOW);
            super.getDEFUSEpanel().setDefColors(Color.BLACK, Color.WHITE);
            super.getDEFUSEpanel().refreshPanel();
        }
        else if (DEF.isBlank()) // and name value is present
        {
            nameWarningLabel.setText(NAME_REQUIRED);
            nameWarningLabel.setForeground(Color.BLACK);
            nameComboBox.setBackground(Color.WHITE);
            super.getDEFUSEpanel().setDefColors(Color.BLACK, Color.WHITE);
            super.getDEFUSEpanel().refreshPanel();
        }
        else if (DEF.endsWith(name)) // successful name match
        {
            localPrefix = DEF.substring(0,DEF.lastIndexOf(name));
            // TODO compare to ancestor humanoid prefix if needed
            
            nameWarningLabel.setText(NAME_RULE_MATCH + ", prefix=" + localPrefix);
            nameWarningLabel.setForeground(darkgreen); // too bright: Color.GREEN
            nameComboBox.setBackground(Color.WHITE);
            super.getDEFUSEpanel().selectX3dDEFUSEpane();
            super.getDEFUSEpanel().setDefColors(Color.BLACK, Color.WHITE);
            super.getDEFUSEpanel().refreshPanel();
        }
        else
        {
            nameWarningLabel.setText(NAME_RULE_MISMATCH + ", prefix=" + localPrefix);
            nameWarningLabel.setForeground(darkorange);
            nameComboBox.setBackground(Color.YELLOW);
            super.getDEFUSEpanel().selectX3dDEFUSEpane();
            super.getDEFUSEpanel().setDefColors(Color.BLACK, Color.YELLOW);
            super.getDEFUSEpanel().refreshPanel();
        }
    }

  @Override
  public void unloadInput() throws IllegalArgumentException
  {
    unLoadDEFUSE();
     
    hAnimSegment.setCenterOfMassX(centerOfMassTFX.getText().trim());
    hAnimSegment.setCenterOfMassY(centerOfMassTFY.getText().trim());
    hAnimSegment.setCenterOfMassZ(centerOfMassTFZ.getText().trim());

    hAnimSegment.setDescription(descriptionTF.getText().trim());
    hAnimSegment.setName(nameComboBox.getSelectedItem().toString().trim());
    
    int i=0;
    String[] sa = new String[moiArray.length];
    for(JTextField tf : moiArray) {
      String s = tf.getText().trim();
      sa[i++] = (s==null||s.length()<=0)?"0":s;
    }
    
    hAnimSegment.setMass(massTF.getText().trim());
    
    hAnimSegment.setBboxCenterX(bboxCenterTFX.getText().trim());
    hAnimSegment.setBboxCenterY(bboxCenterTFY.getText().trim());
    hAnimSegment.setBboxCenterZ(bboxCenterTFZ.getText().trim());
    hAnimSegment.setBboxSizeX(bboxSizeTFX.getText().trim());
    hAnimSegment.setBboxSizeY(bboxSizeTFY.getText().trim());
    hAnimSegment.setBboxSizeZ(bboxSizeTFZ.getText().trim());
  }
  
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField bboxCenterTFX;
    private javax.swing.JTextField bboxCenterTFY;
    private javax.swing.JTextField bboxCenterTFZ;
    private javax.swing.JTextField bboxSizeTFX;
    private javax.swing.JTextField bboxSizeTFY;
    private javax.swing.JTextField bboxSizeTFZ;
    private javax.swing.JLabel centerOfMassLabel;
    private javax.swing.JTextField centerOfMassTFX;
    private javax.swing.JTextField centerOfMassTFY;
    private javax.swing.JTextField centerOfMassTFZ;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JTextField descriptionTF;
    private javax.swing.JLabel hintLabel;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel massLabel;
    private javax.swing.JTextField massTF;
    private javax.swing.JTextField moiTF0;
    private javax.swing.JTextField moiTF1;
    private javax.swing.JTextField moiTF2;
    private javax.swing.JTextField moiTF3;
    private javax.swing.JTextField moiTF4;
    private javax.swing.JTextField moiTF5;
    private javax.swing.JTextField moiTF6;
    private javax.swing.JTextField moiTF7;
    private javax.swing.JTextField moiTF8;
    private javax.swing.JLabel momentsOfInertiaLabel;
    private javax.swing.JComboBox<String> nameComboBox;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel nameWarningLabel;
    private javax.swing.JPanel nodeHintPanel;
    private javax.swing.JLabel spacerLabel;
    // End of variables declaration//GEN-END:variables

}
