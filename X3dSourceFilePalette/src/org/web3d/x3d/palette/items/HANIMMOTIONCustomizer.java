/*
Copyright (c) 1995-2023 held by the author(s).  All rights reserved.
 
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
import java.util.Arrays;
import javax.swing.DefaultComboBoxModel;
import javax.swing.text.JTextComponent;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFDouble;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFInt32;
import static org.web3d.x3d.types.X3DSchemaData.HANIMHUMANOID_ATTR_LOA_CHOICES;  // reuse
import static org.web3d.x3d.types.X3DSchemaData.HANIMHUMANOID_ATTR_LOA_TOOLTIPS;
import static org.web3d.x3d.types.X3DSchemaData.HANIMMOTION_ATTR_SKELETALCONFIGURATION_DFLT;
import static org.web3d.x3d.types.X3DSchemaData.HANIMMOTION_CONTAINERFIELD_CHOICES;
import static org.web3d.x3d.types.X3DSchemaData.HANIMMOTION_CONTAINERFIELD_TOOLTIPS;

/**
 * HANIMMOTIONCustomizer.java

 MOVES Institute
 Naval Postgraduate School, Monterey, CA, USA
 www.nps.edu
 *
 * @author Don Brutzman
 * @version $Id$
 */
public class HANIMMOTIONCustomizer extends BaseCustomizer
{
  private final HANIMMOTION hanimMotion;
  private final JTextComponent target;

  private boolean insertCommas, insertLineBreaks = false;
  
  private String  localPrefix = new String();
  
  private final int TUPLE_SIZE = 3;
    
  public HANIMMOTIONCustomizer(HANIMMOTION hanimMotion, JTextComponent target)
  {
    super(hanimMotion);
    this.hanimMotion = hanimMotion;
    this.target = target;
    
    HelpCtx.setHelpIDString(this, "HANIMMOTION_ELEM_HELPID");
    
    initComponents();
    
    super.getDEFUSEpanel().setContainerFieldChoices(HANIMMOTION_CONTAINERFIELD_CHOICES, HANIMMOTION_CONTAINERFIELD_TOOLTIPS);
    super.getDEFUSEpanel().setContainerField(hanimMotion.getContainerField()); // reset value to match updated JComboBox data model
    // DEFUSEpanel initialization must NOT be repeated or else array of choices will be overwritten
    
    channelsTF.setText(hanimMotion.getChannels());
    channelsEnabledTF.setText(Arrays.toString(hanimMotion.getChannelsEnabled()).replace("[","").replace("]",""));
    descriptionTF.setText(hanimMotion.getDescription());
    enabledCB.setSelected(hanimMotion.isEnabled());
    loopCB.setSelected(hanimMotion.isLoop());
      endFrameTF.setText(hanimMotion.getEndFrame().toString());
    startFrameTF.setText(hanimMotion.getStartFrame().toString());
    frameCountTF.setText(hanimMotion.getFrameCount().toString());
    frameDurationTF.setText(hanimMotion.getFrameDuration().toString());
    frameIncrementTF.setText(hanimMotion.getFrameIncrement().toString());
    frameIndexTF.setText(hanimMotion.getFrameIndex().toString());
    jointsTF.setText(hanimMotion.getJoints());
    
    loaComboBox.setSelectedIndex(hanimMotion.getLoa().getValue() + 1);
    loaComboBox.setToolTipText(HANIMHUMANOID_ATTR_LOA_TOOLTIPS[loaComboBox.getSelectedIndex()]);
    
    valuesTable.setTitle("HAnimMotion frame values table");
    valuesTable.setAddColumnButtonTooltip   ("Add column of rotation triplets");
    valuesTable.setRemoveColumnButtonTooltip("Remove column of rotation triplets");
    valuesTable.setAddRowButtonTooltip      ("Add row of rotation triplets");
    valuesTable.setRemoveRowButtonTooltip   ("Remove row of rotation triplets");
    valuesTable.setColumnsLabelText("columns of rotation-vector triplets");
    valuesTable.setRowsLabelText   ("rows of rotation-vector triplets");
    valuesTable.setDefaultTupleValues(new String[]{"0","0","0"}); // 3-tuple
    valuesTable.setColumnWidthAndResizeStrategy(true, 50);

    String[][] saa = hanimMotion.getValuesString(); // may be 0-length
    valuesTable.setData(TUPLE_SIZE, saa);
    if (saa.length == 0) // TODO fix
    {
        // initialize headers for empty table
        String[][] defaultRow = new String[1][4]; // TODO adjust
        defaultRow [0] = new String[]{"0","0","0","0"};
        valuesTable.setData(TUPLE_SIZE, defaultRow);
        valuesTable.setData(TUPLE_SIZE, saa);
    }
    valuesTable.setInsertCommas(hanimMotion.isInsertCommas());
    valuesTable.setInsertLineBreaks(hanimMotion.isInsertLineBreaks());
  }
  private void setDefaultDEFname()
  {
    String newDefaultName = nameTextField.getText().trim();
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
  private void adjustForwardsBackwardsLabel()
  {
      if (frameDurationTF.getText().trim().startsWith("-"))
      {
          forwardBackwardsLabel.setText("backwards");
          forwardBackwardsLabel.setForeground(darkorange);
      }
      else
      {
          forwardBackwardsLabel.setText("forwards");
          forwardBackwardsLabel.setForeground(darkgreen);
      }
  }
  
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        java.awt.GridBagConstraints gridBagConstraints;

        org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel1 = getDEFUSEpanel();
        tabbedPane = new javax.swing.JTabbedPane();
        fieldsPanel = new javax.swing.JPanel();
        paddingLabel = new javax.swing.JLabel();
        nameWarningLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        descriptionLabel = new javax.swing.JLabel();
        descriptionTF = new javax.swing.JTextField();
        skeletalConfigurationLabel = new javax.swing.JLabel();
        skeletalConfigurationTF = new javax.swing.JTextField();
        skeletalConfigurationDefaultButton = new javax.swing.JButton();
        loaComboBox = new javax.swing.JComboBox<>();
        loaLabel = new javax.swing.JLabel();
        enabledLabel = new javax.swing.JLabel();
        enabledCB = new javax.swing.JCheckBox();
        loopLabel = new javax.swing.JLabel();
        loopCB = new javax.swing.JCheckBox();
        frameCountLabel = new javax.swing.JLabel();
        frameCountTF = new javax.swing.JTextField();
        frameIndexLabel = new javax.swing.JLabel();
        frameIndexTF = new javax.swing.JTextField();
        startFrameLabel = new javax.swing.JLabel();
        startFrameTF = new javax.swing.JTextField();
        endFrameLabel = new javax.swing.JLabel();
        endFrameTF = new javax.swing.JTextField();
        durationLabel = new javax.swing.JLabel();
        frameDurationLabel = new javax.swing.JLabel();
        frameDurationTF = new javax.swing.JTextField();
        frameIncrementLabel = new javax.swing.JLabel();
        frameIncrementTF = new javax.swing.JTextField();
        forwardBackwardsLabel = new javax.swing.JLabel();
        channelsLabel = new javax.swing.JLabel();
        channelsTF = new javax.swing.JTextField();
        channelsEnabled = new javax.swing.JLabel();
        channelsEnabledTF = new javax.swing.JTextField();
        jointsLabel = new javax.swing.JLabel();
        jointsTF = new javax.swing.JTextField();
        eventHintPanel = new javax.swing.JPanel();
        eventLabel1 = new javax.swing.JLabel();
        eventLabel2 = new javax.swing.JLabel();
        valuesTablePanel = new javax.swing.JPanel();
        valuesTable = new org.web3d.x3d.palette.items.ExpandableKeyTupleTable();

        setPreferredSize(new java.awt.Dimension(800, 520));
        setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel1, gridBagConstraints);

        fieldsPanel.setPreferredSize(new java.awt.Dimension(800, 530));
        fieldsPanel.setLayout(new java.awt.GridBagLayout());

        paddingLabel.setText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.paddingLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        fieldsPanel.add(paddingLabel, gridBagConstraints);

        nameWarningLabel.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        nameWarningLabel.setText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.nameWarningLabel.text")); // NOI18N
        nameWarningLabel.setToolTipText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.nameWarningLabel.toolTipText")); // NOI18N
        nameWarningLabel.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseEntered(java.awt.event.MouseEvent evt)
            {
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
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 3, 3);
        fieldsPanel.add(nameWarningLabel, gridBagConstraints);

        nameLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        nameLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        nameLabel.setText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.nameLabel.text")); // NOI18N
        nameLabel.setToolTipText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.nameLabel.toolTipText")); // NOI18N
        nameLabel.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseEntered(java.awt.event.MouseEvent evt)
            {
                nameLabelMouseEntered(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(nameLabel, gridBagConstraints);

        nameTextField.setToolTipText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.nameTextField.toolTipText")); // NOI18N
        nameTextField.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(java.awt.event.FocusEvent evt)
            {
                nameTextFieldFocusGained(evt);
            }
        });
        nameTextField.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                nameTextFieldActionPerformed(evt);
            }
        });
        nameTextField.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyReleased(java.awt.event.KeyEvent evt)
            {
                nameTextFieldKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(nameTextField, gridBagConstraints);

        descriptionLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        descriptionLabel.setText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.descriptionLabel.text")); // NOI18N
        descriptionLabel.setToolTipText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.descriptionLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(descriptionLabel, gridBagConstraints);

        descriptionTF.setForeground(new java.awt.Color(0, 153, 153));
        descriptionTF.setToolTipText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.descriptionTF.toolTipText")); // NOI18N
        descriptionTF.setMinimumSize(new java.awt.Dimension(50, 20));
        descriptionTF.setPreferredSize(new java.awt.Dimension(50, 20));
        descriptionTF.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                descriptionTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(descriptionTF, gridBagConstraints);

        skeletalConfigurationLabel.setForeground(new java.awt.Color(0, 153, 153));
        skeletalConfigurationLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        skeletalConfigurationLabel.setText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.skeletalConfigurationLabel.text")); // NOI18N
        skeletalConfigurationLabel.setToolTipText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.skeletalConfigurationLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(skeletalConfigurationLabel, gridBagConstraints);

        skeletalConfigurationTF.setToolTipText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.skeletalConfigurationTF.toolTipText")); // NOI18N
        skeletalConfigurationTF.setMinimumSize(new java.awt.Dimension(50, 20));
        skeletalConfigurationTF.setPreferredSize(new java.awt.Dimension(50, 20));
        skeletalConfigurationTF.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                skeletalConfigurationTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(skeletalConfigurationTF, gridBagConstraints);

        skeletalConfigurationDefaultButton.setForeground(new java.awt.Color(0, 153, 153));
        skeletalConfigurationDefaultButton.setText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.skeletalConfigurationDefaultButton.text")); // NOI18N
        skeletalConfigurationDefaultButton.setToolTipText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.skeletalConfigurationDefaultButton.toolTipText")); // NOI18N
        skeletalConfigurationDefaultButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                skeletalConfigurationDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(skeletalConfigurationDefaultButton, gridBagConstraints);

        loaComboBox.setEditable(true);
        loaComboBox.setModel(new DefaultComboBoxModel<String>(HANIMHUMANOID_ATTR_LOA_CHOICES));
        loaComboBox.setToolTipText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.loaComboBox.toolTipText")); // NOI18N
        loaComboBox.setMaximumSize(new java.awt.Dimension(50, 20));
        loaComboBox.setMinimumSize(new java.awt.Dimension(50, 20));
        loaComboBox.setPreferredSize(new java.awt.Dimension(50, 20));
        loaComboBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                loaComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(loaComboBox, gridBagConstraints);

        loaLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        loaLabel.setText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.loaLabel.text")); // NOI18N
        loaLabel.setToolTipText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.loaLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(loaLabel, gridBagConstraints);

        enabledLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        enabledLabel.setText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.enabledLabel.text")); // NOI18N
        enabledLabel.setToolTipText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.enabledLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(enabledLabel, gridBagConstraints);

        enabledCB.setToolTipText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.enabledCB.toolTipText")); // NOI18N
        enabledCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(enabledCB, gridBagConstraints);

        loopLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        loopLabel.setText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.loopLabel.text")); // NOI18N
        loopLabel.setToolTipText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.loopLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(loopLabel, gridBagConstraints);

        loopCB.setToolTipText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.loopCB.toolTipText")); // NOI18N
        loopCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(loopCB, gridBagConstraints);

        frameCountLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        frameCountLabel.setText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.frameCountLabel.text")); // NOI18N
        frameCountLabel.setToolTipText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.frameCountLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(frameCountLabel, gridBagConstraints);

        frameCountTF.setToolTipText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.frameCountTF.toolTipText")); // NOI18N
        frameCountTF.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                frameCountTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(frameCountTF, gridBagConstraints);

        frameIndexLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        frameIndexLabel.setText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.frameIndexLabel.text")); // NOI18N
        frameIndexLabel.setToolTipText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.frameIndexLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(frameIndexLabel, gridBagConstraints);

        frameIndexTF.setToolTipText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.frameIndexTF.toolTipText")); // NOI18N
        frameIndexTF.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                frameIndexTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(frameIndexTF, gridBagConstraints);

        startFrameLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        startFrameLabel.setText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.startFrameLabel.text")); // NOI18N
        startFrameLabel.setToolTipText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.startFrameLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(startFrameLabel, gridBagConstraints);

        startFrameTF.setText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.startFrameTF.text")); // NOI18N
        startFrameTF.setToolTipText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.startFrameTF.toolTipText")); // NOI18N
        startFrameTF.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                startFrameTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(startFrameTF, gridBagConstraints);

        endFrameLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        endFrameLabel.setText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.endFrameLabel.text")); // NOI18N
        endFrameLabel.setToolTipText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.endFrameLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(endFrameLabel, gridBagConstraints);

        endFrameTF.setText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.endFrameTF.text")); // NOI18N
        endFrameTF.setToolTipText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.endFrameTF.toolTipText")); // NOI18N
        endFrameTF.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                endFrameTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(endFrameTF, gridBagConstraints);

        durationLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        durationLabel.setText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.durationLabel.text")); // NOI18N
        durationLabel.setToolTipText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.durationLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        fieldsPanel.add(durationLabel, gridBagConstraints);

        frameDurationLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        frameDurationLabel.setText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.frameDurationLabel.text")); // NOI18N
        frameDurationLabel.setToolTipText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.frameDurationLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(frameDurationLabel, gridBagConstraints);

        frameDurationTF.setToolTipText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.frameDurationTF.toolTipText")); // NOI18N
        frameDurationTF.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                frameDurationTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(frameDurationTF, gridBagConstraints);

        frameIncrementLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        frameIncrementLabel.setText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.frameIncrementLabel.text")); // NOI18N
        frameIncrementLabel.setToolTipText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.frameIncrementLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(frameIncrementLabel, gridBagConstraints);

        frameIncrementTF.setToolTipText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.frameIncrementTF.toolTipText")); // NOI18N
        frameIncrementTF.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                frameIncrementTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(frameIncrementTF, gridBagConstraints);

        forwardBackwardsLabel.setForeground(new java.awt.Color(21, 71, 52));
        forwardBackwardsLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        forwardBackwardsLabel.setText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.forwardBackwardsLabel.text")); // NOI18N
        forwardBackwardsLabel.setToolTipText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.forwardBackwardsLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        fieldsPanel.add(forwardBackwardsLabel, gridBagConstraints);

        channelsLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        channelsLabel.setText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.channelsLabel.text")); // NOI18N
        channelsLabel.setToolTipText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.channelsLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(channelsLabel, gridBagConstraints);

        channelsTF.setToolTipText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.channelsTF.toolTipText")); // NOI18N
        channelsTF.setMinimumSize(new java.awt.Dimension(50, 20));
        channelsTF.setPreferredSize(new java.awt.Dimension(50, 20));
        channelsTF.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                channelsTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(channelsTF, gridBagConstraints);

        channelsEnabled.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        channelsEnabled.setText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.channelsEnabled.text")); // NOI18N
        channelsEnabled.setToolTipText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.channelsEnabled.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(channelsEnabled, gridBagConstraints);

        channelsEnabledTF.setToolTipText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.channelsEnabledTF.toolTipText")); // NOI18N
        channelsEnabledTF.setMinimumSize(new java.awt.Dimension(50, 20));
        channelsEnabledTF.setPreferredSize(new java.awt.Dimension(50, 20));
        channelsEnabledTF.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                channelsEnabledTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(channelsEnabledTF, gridBagConstraints);

        jointsLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jointsLabel.setText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.jointsLabel.text")); // NOI18N
        jointsLabel.setToolTipText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.jointsLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(jointsLabel, gridBagConstraints);

        jointsTF.setToolTipText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.jointsTF.toolTipText")); // NOI18N
        jointsTF.setMinimumSize(new java.awt.Dimension(50, 20));
        jointsTF.setPreferredSize(new java.awt.Dimension(50, 20));
        jointsTF.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                jointsTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(jointsTF, gridBagConstraints);

        eventHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eventHintPanel.setLayout(new java.awt.GridBagLayout());

        eventLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eventLabel1.setText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.eventLabel1.text")); // NOI18N
        eventLabel1.setToolTipText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.eventLabel1.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        eventHintPanel.add(eventLabel1, gridBagConstraints);

        eventLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eventLabel2.setText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.eventLabel2.text")); // NOI18N
        eventLabel2.setToolTipText(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.eventLabel2.toolTipText")); // NOI18N
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
        gridBagConstraints.gridy = 17;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 0, 3);
        fieldsPanel.add(eventHintPanel, gridBagConstraints);

        tabbedPane.addTab(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.fieldsPanel.TabConstraints.tabTitle"), fieldsPanel); // NOI18N

        valuesTablePanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        valuesTablePanel.add(valuesTable, gridBagConstraints);

        tabbedPane.addTab(org.openide.util.NbBundle.getMessage(HANIMMOTIONCustomizer.class, "HANIMMOTIONCustomizer.valuesTablePanel.TabConstraints.tabTitle"), valuesTablePanel); // NOI18N

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        add(tabbedPane, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

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

    private void nameTextFieldFocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event_nameTextFieldFocusGained
    {//GEN-HEADEREND:event_nameTextFieldFocusGained
        setDefaultDEFname ();
        checkNameDefMatchRules();
    }//GEN-LAST:event_nameTextFieldFocusGained

    private void nameTextFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_nameTextFieldActionPerformed
    {//GEN-HEADEREND:event_nameTextFieldActionPerformed
        setDefaultDEFname ();
        checkNameDefMatchRules();
    }//GEN-LAST:event_nameTextFieldActionPerformed

    private void nameTextFieldKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_nameTextFieldKeyReleased
    {//GEN-HEADEREND:event_nameTextFieldKeyReleased
        setDefaultDEFname ();
        checkNameDefMatchRules();
    }//GEN-LAST:event_nameTextFieldKeyReleased

    private void descriptionTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_descriptionTFActionPerformed
    {//GEN-HEADEREND:event_descriptionTFActionPerformed
        // no action needed
    }//GEN-LAST:event_descriptionTFActionPerformed

    private void loaComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_loaComboBoxActionPerformed
    {//GEN-HEADEREND:event_loaComboBoxActionPerformed
        loaComboBox.setToolTipText(HANIMHUMANOID_ATTR_LOA_TOOLTIPS[loaComboBox.getSelectedIndex()]);
    }//GEN-LAST:event_loaComboBoxActionPerformed

    private void channelsTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_channelsTFActionPerformed
    {//GEN-HEADEREND:event_channelsTFActionPerformed
        // TODO check channels syntax
    }//GEN-LAST:event_channelsTFActionPerformed

    private void channelsEnabledTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_channelsEnabledTFActionPerformed
    {//GEN-HEADEREND:event_channelsEnabledTFActionPerformed
        // TODO check channelsEnabled syntax
    }//GEN-LAST:event_channelsEnabledTFActionPerformed

    private void jointsTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_jointsTFActionPerformed
    {//GEN-HEADEREND:event_jointsTFActionPerformed
        // TODO check joints values
    }//GEN-LAST:event_jointsTFActionPerformed

    private void frameIndexTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_frameIndexTFActionPerformed
    {//GEN-HEADEREND:event_frameIndexTFActionPerformed
        // TODO check if less than start frame or greater than total number of frames
    }//GEN-LAST:event_frameIndexTFActionPerformed

    private void startFrameTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_startFrameTFActionPerformed
    {//GEN-HEADEREND:event_startFrameTFActionPerformed
        // TODO check if less than 0 or greater than total number of frames
    }//GEN-LAST:event_startFrameTFActionPerformed

    private void endFrameTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_endFrameTFActionPerformed
    {//GEN-HEADEREND:event_endFrameTFActionPerformed
        // TODO check if < 1 or outlandishly high
    }//GEN-LAST:event_endFrameTFActionPerformed

    private void frameDurationTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_frameDurationTFActionPerformed
    {//GEN-HEADEREND:event_frameDurationTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_frameDurationTFActionPerformed

    private void frameIncrementTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_frameIncrementTFActionPerformed
    {//GEN-HEADEREND:event_frameIncrementTFActionPerformed
        adjustForwardsBackwardsLabel();
    }//GEN-LAST:event_frameIncrementTFActionPerformed

    private void frameCountTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_frameCountTFActionPerformed
    {//GEN-HEADEREND:event_frameCountTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_frameCountTFActionPerformed

    private void skeletalConfigurationTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_skeletalConfigurationTFActionPerformed
    {//GEN-HEADEREND:event_skeletalConfigurationTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_skeletalConfigurationTFActionPerformed

    private void skeletalConfigurationDefaultButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_skeletalConfigurationDefaultButtonActionPerformed
    {//GEN-HEADEREND:event_skeletalConfigurationDefaultButtonActionPerformed
        skeletalConfigurationTF.setText(HANIMMOTION_ATTR_SKELETALCONFIGURATION_DFLT);
    }//GEN-LAST:event_skeletalConfigurationDefaultButtonActionPerformed
  
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel channelsEnabled;
    private javax.swing.JTextField channelsEnabledTF;
    private javax.swing.JLabel channelsLabel;
    private javax.swing.JTextField channelsTF;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JTextField descriptionTF;
    private javax.swing.JLabel durationLabel;
    private javax.swing.JCheckBox enabledCB;
    private javax.swing.JLabel enabledLabel;
    private javax.swing.JLabel endFrameLabel;
    private javax.swing.JTextField endFrameTF;
    private javax.swing.JPanel eventHintPanel;
    private javax.swing.JLabel eventLabel1;
    private javax.swing.JLabel eventLabel2;
    private javax.swing.JPanel fieldsPanel;
    private javax.swing.JLabel forwardBackwardsLabel;
    private javax.swing.JLabel frameCountLabel;
    private javax.swing.JTextField frameCountTF;
    private javax.swing.JLabel frameDurationLabel;
    private javax.swing.JTextField frameDurationTF;
    private javax.swing.JLabel frameIncrementLabel;
    private javax.swing.JTextField frameIncrementTF;
    private javax.swing.JLabel frameIndexLabel;
    private javax.swing.JTextField frameIndexTF;
    private javax.swing.JLabel jointsLabel;
    private javax.swing.JTextField jointsTF;
    private javax.swing.JComboBox<String> loaComboBox;
    private javax.swing.JLabel loaLabel;
    private javax.swing.JCheckBox loopCB;
    private javax.swing.JLabel loopLabel;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JLabel nameWarningLabel;
    private javax.swing.JLabel paddingLabel;
    private javax.swing.JButton skeletalConfigurationDefaultButton;
    private javax.swing.JLabel skeletalConfigurationLabel;
    private javax.swing.JTextField skeletalConfigurationTF;
    private javax.swing.JLabel startFrameLabel;
    private javax.swing.JTextField startFrameTF;
    private javax.swing.JTabbedPane tabbedPane;
    private org.web3d.x3d.palette.items.ExpandableKeyTupleTable valuesTable;
    private javax.swing.JPanel valuesTablePanel;
    // End of variables declaration//GEN-END:variables
  @Override
    public String getNameKey()
    {
        return "NAME_X3D_HANIMMOTION";
    }

    private Color  burntorange = new Color(191,  87,  0);
    private Color   darkorange = new Color(255, 140,  0);
    private Color   darkgreen  = new Color( 21,  71, 52);
  
    private void checkNameDefMatchRules()
    {
        String NAME_REQUIRED      = "name must have a legal value";
        String NAME_RULE_MATCH    = "successful name match: DEF = prefix + name";
        String NAME_RULE_MISMATCH = "name mismatch: DEF must match prefix + name";
  
        String DEF    = super.getDEFUSEpanel().getDEF();
        String name   = nameTextField.getText();
        
        if (name.isBlank())
        {
            nameWarningLabel.setText(NAME_REQUIRED);
            nameWarningLabel.setForeground(darkorange);
            nameTextField.setBackground(Color.YELLOW);
            super.getDEFUSEpanel().setDefColors(Color.BLACK, Color.WHITE);
            super.getDEFUSEpanel().refreshPanel();
        }
        else if (DEF.isBlank()) // and name value is present
        {
            nameWarningLabel.setText("");
            nameWarningLabel.setForeground(Color.BLACK);
            nameTextField.setBackground(Color.WHITE);
            super.getDEFUSEpanel().setDefColors(Color.BLACK, Color.WHITE);
            super.getDEFUSEpanel().refreshPanel();
        }
        else if (DEF.endsWith(name)) // successful name match
        {            
            localPrefix = DEF.substring(0,DEF.lastIndexOf(name));
            // TODO compare to ancestor humanoid prefix if needed
            
            nameWarningLabel.setText(NAME_RULE_MATCH + ", prefix=" + localPrefix);
            nameWarningLabel.setForeground(darkgreen); // too bright: Color.GREEN
            nameTextField.setBackground(Color.WHITE);
            super.getDEFUSEpanel().selectX3dDEFUSEpane();
            super.getDEFUSEpanel().setDefColors(Color.BLACK, Color.WHITE);
            super.getDEFUSEpanel().refreshPanel();
        }
        else
        {
            nameWarningLabel.setText(NAME_RULE_MISMATCH + ", prefix=" + localPrefix);
            nameWarningLabel.setForeground(darkorange);
            nameTextField.setBackground(Color.YELLOW);
            super.getDEFUSEpanel().selectX3dDEFUSEpane();
            super.getDEFUSEpanel().setDefColors(Color.BLACK, Color.YELLOW);
            super.getDEFUSEpanel().refreshPanel();
        }
    }

  @Override
  public void unloadInput() throws IllegalArgumentException
  {
    unLoadDEFUSE();
    
    hanimMotion.setChannels(channelsTF.getText());
    if  (channelsEnabledTF.getText().isBlank())
         hanimMotion.setChannelsEnabled(new boolean[0]);
    else hanimMotion.setChannelsEnabled(hanimMotion.parseToBooleanArray(channelsEnabledTF.getText().split("\\s")));
    hanimMotion.setDescription(descriptionTF.getText());
    hanimMotion.setEnabled(enabledCB.isSelected());
    hanimMotion.setLoop(loopCB.isSelected());
    hanimMotion.setEndFrame(new SFInt32(endFrameTF.getText(), 0, 65535));
    hanimMotion.setStartFrame(new SFInt32(startFrameTF.getText(),0, 65535));
    hanimMotion.setFrameCount(new SFInt32(frameCountTF.getText(),0, 65535));
    hanimMotion.setFrameDuration(new SFDouble(frameDurationTF.getText(),0.0,null)); // SFTime
    hanimMotion.setFrameIncrement(new SFInt32(frameIncrementTF.getText(),0, 65535));
    hanimMotion.setFrameIndex(new SFInt32(frameIndexTF.getText(),0, 65535));
    hanimMotion.setJoints(jointsTF.getText());
    hanimMotion.setLoa(new SFInt32(loaComboBox.getSelectedItem().toString(),-1,4));
    
    hanimMotion.setValuesString(valuesTable.getData());

    hanimMotion.setInsertCommas    (valuesTable.isInsertCommasSet());
    hanimMotion.setInsertLineBreaks(valuesTable.isInsertLineBreaksSet());
  }  
  
}
