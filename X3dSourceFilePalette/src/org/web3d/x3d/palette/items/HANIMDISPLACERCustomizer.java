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

import java.awt.Color;
import javax.swing.DefaultComboBoxModel;
import javax.swing.text.JTextComponent;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * HANIMDISPLACERCustomizer.java
 * Created on 2 May 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey and Don Brutzman
 * @version $Id$
 */
public class HANIMDISPLACERCustomizer extends BaseCustomizer
{
  private HANIMDISPLACER hAnimDisplacer;
  private JTextComponent target;
  
  private String  localPrefix = new String();
  
  /** Creates new form HANIMDISPLACERCustomizer
     * @param displacer data of interest
     * @param target Swing component of interest */
  public HANIMDISPLACERCustomizer(HANIMDISPLACER displacer, JTextComponent target)
  {
    super(displacer);
    hAnimDisplacer = displacer;
    this.target = target;
    
    HelpCtx.setHelpIDString(this, "HANIMDISPLACER_ELEM_HELPID");
    
    initComponents();
    
    super.getDEFUSEpanel().setContainerFieldChoices(HANIMDISPLACER_CONTAINERFIELD_CHOICES, HANIMDISPLACER_CONTAINERFIELD_TOOLTIPS);
    // DEFUSEpanel initialization must NOT be repeated or else array of choices will be overwritten
    
    descriptionTF.setText(hAnimDisplacer.getDescription());
    if (!hAnimDisplacer.getDescription().isBlank())
    {
        checkX3D4FieldSupportDialog("HAnimDisplacer","description"); // X3D4 field
        descriptionTF.setText(hAnimDisplacer.getDescription());
    }
    
    nameComboBox.setSelectedItem(hAnimDisplacer.getName());
    coordIndexTF.setText    (hAnimDisplacer.getCoordIndex());
    displacementsTF.setText (hAnimDisplacer.getDisplacements());
    weightTF.setText        (hAnimDisplacer.getWeight());
    
    if (!hAnimDisplacer.getName().isBlank() &&
        super.getDEFUSEpanel().getDEF().endsWith(hAnimDisplacer.getName())) // successful name match
    {
        localPrefix = super.getDEFUSEpanel().getDEF().substring(0,super.getDEFUSEpanel().getDEF().lastIndexOf(hAnimDisplacer.getName()));
    }
    nameComboBox.setSelectedItem(hAnimDisplacer.getName());
//    checkHumanoidRootSpelling();
    setDefaultDEFname();
    checkNameDefMatchRules();
  }
  private void setDefaultDEFname()
  {
    String enumerationName, newDefaultName;
    if  (nameComboBox.getSelectedItem().toString().trim().length() > 5)
    {
         enumerationName = nameComboBox.getSelectedItem().toString().substring(3).trim(); // omit index
         enumerationName = enumerationName.substring(0,enumerationName.indexOf(" ")).trim();     // omit full name
    }
    else enumerationName = hAnimDisplacer.getName();
    newDefaultName = enumerationName;
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
    private void initComponents()
    {
        java.awt.GridBagConstraints gridBagConstraints;

        nameLabel = new javax.swing.JLabel();
        nameComboBox = new javax.swing.JComboBox<>();
        nameWarningLabel = new javax.swing.JLabel();
        descriptionLabel = new javax.swing.JLabel();
        coordIndexTF = new javax.swing.JTextField();
        displacementsTF = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel = getDEFUSEpanel();
        weightTF = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        nodeHintPanel = new javax.swing.JPanel();
        hintLabel = new javax.swing.JLabel();
        descriptionTF = new javax.swing.JTextField();
        spacerLabel = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(620, 258));
        setPreferredSize(new java.awt.Dimension(600, 300));
        setLayout(new java.awt.GridBagLayout());

        nameLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        nameLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        nameLabel.setText("name");
        nameLabel.setToolTipText("Unique name attribute must be defined so that HAnimDisplacer node can be identified at runtime for animation purposes");
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
        gridBagConstraints.ipadx = 70;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nameLabel, gridBagConstraints);

        nameComboBox.setEditable(true);
        nameComboBox.setFont(new java.awt.Font("Courier New", 1, 12)); // NOI18N
        nameComboBox.setModel(new DefaultComboBoxModel<String>(HANIMDISPLACER_NAME_FEATUREPOINT_CHOICES));
        nameComboBox.setToolTipText("select HAminDisplacer name");
        nameComboBox.addItemListener(new java.awt.event.ItemListener()
        {
            public void itemStateChanged(java.awt.event.ItemEvent evt)
            {
                nameComboBoxItemStateChanged(evt);
            }
        });
        nameComboBox.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(java.awt.event.FocusEvent evt)
            {
                nameComboBoxFocusGained(evt);
            }
        });
        nameComboBox.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseEntered(java.awt.event.MouseEvent evt)
            {
                nameComboBoxMouseEntered(evt);
            }
        });
        nameComboBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                nameComboBoxActionPerformed(evt);
            }
        });
        nameComboBox.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyReleased(java.awt.event.KeyEvent evt)
            {
                nameComboBoxKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nameComboBox, gridBagConstraints);

        nameWarningLabel.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        nameWarningLabel.setText("name must have a legal value");
        nameWarningLabel.setToolTipText("HAnim has strict rules for name and DEF");
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
        gridBagConstraints.gridwidth = 3;
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
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 70;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(descriptionLabel, gridBagConstraints);

        coordIndexTF.setToolTipText("Defines index values into the coordinate array for the mesh of vertices affected by this HAnimDisplacer. Values start at index 0.");
        coordIndexTF.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                coordIndexTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(coordIndexTF, gridBagConstraints);

        displacementsTF.setToolTipText("displacements are a set of SFVec3f values added to neutral/resting position each ofcorresponding vertices referenced by coordIndex field.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(displacementsTF, gridBagConstraints);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("coordIndex");
        jLabel2.setToolTipText("Defines index values into the coordinate array for the mesh of vertices affected by this HAnimDisplacer. Values start at index 0.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 70;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(jLabel2, gridBagConstraints);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("displacements");
        jLabel3.setToolTipText("displacements are a set of SFVec3f values added to neutral/resting position each ofcorresponding vertices referenced by coordIndex field.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.ipadx = 70;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(jLabel3, gridBagConstraints);

        dEFUSEpanel.addKeyListener(new java.awt.event.KeyAdapter()
        {
            public void keyReleased(java.awt.event.KeyEvent evt)
            {
                dEFUSEpanelKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 45;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel, gridBagConstraints);

        weightTF.setToolTipText("weight has typical range [0,1] and determines magnitude of displacement");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(weightTF, gridBagConstraints);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("weight");
        jLabel4.setToolTipText("weight has typical range [0,1] and determines magnitude of displacement");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.ipadx = 70;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(jLabel4, gridBagConstraints);

        nodeHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nodeHintPanel.setLayout(new java.awt.GridBagLayout());

        hintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel.setText("<html><b>HAnimDisplacer</b>  provides animation values to alter the shape of individual HAnimSegment nodes. ");
        hintLabel.setToolTipText("HAnimDisplacer can be used for a variety of animation techniques");
        hintLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        nodeHintPanel.add(hintLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nodeHintPanel, gridBagConstraints);

        descriptionTF.setForeground(new java.awt.Color(0, 153, 153));
        descriptionTF.setToolTipText("(X3D4) Author-provided prose that describes intended purpose of the node");
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
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(descriptionTF, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(spacerLabel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void coordIndexTFActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_coordIndexTFActionPerformed
    {//GEN-HEADEREND:event_coordIndexTFActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_coordIndexTFActionPerformed

    private void nameComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameComboBoxActionPerformed
        setDefaultDEFname ();
        checkNameDefMatchRules();
    }//GEN-LAST:event_nameComboBoxActionPerformed

    private void descriptionTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_descriptionTFActionPerformed
    {//GEN-HEADEREND:event_descriptionTFActionPerformed
        checkX3D4FieldSupportDialog("HAnimDisplacer","description"); // X3D4 field
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

    private void dEFUSEpanelKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_dEFUSEpanelKeyReleased
    {//GEN-HEADEREND:event_dEFUSEpanelKeyReleased
        checkNameDefMatchRules(); // TODO fix, apparently not receiving an event
    }//GEN-LAST:event_dEFUSEpanelKeyReleased

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

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_HANIMDISPLACER";
  }
    private void checkNameDefMatchRules()
    {
        String NAME_REQUIRED      = "name must have a legal value";
        String NAME_RULE_MATCH    = "successful name match: DEF = prefix + name";
        String NAME_RULE_MISMATCH = "name mismatch: DEF must match prefix + name";
  
        String DEF    = super.getDEFUSEpanel().getDEF();
        String name, enumerationName;
        if (nameComboBox.getSelectedIndex() >= 0) // chosen from combo box
        {
             enumerationName = nameComboBox.getSelectedItem().toString().substring(3).trim(); // omit index
             enumerationName = enumerationName.substring(0,enumerationName.indexOf(" ")).trim();     // omit full name
        }
        else enumerationName = nameComboBox.getSelectedItem().toString().trim(); // user provided
        name = enumerationName;
        
        Color  burntorange = new Color(191,  87,  0);
        Color   darkorange = new Color(255, 140,  0);
        Color   darkgreen  = new Color( 21,  71, 52);
        
        if (name.isBlank())
        {
            nameWarningLabel.setText(NAME_REQUIRED);
            nameWarningLabel.setForeground(darkorange);
            nameComboBox.setBackground(Color.YELLOW);
            super.getDEFUSEpanel().setDefColors(Color.BLACK, Color.WHITE);
            super.getDEFUSEpanel().refreshPanel();
        }
        else if (DEF.isBlank()) // and name value is present
        {
            nameWarningLabel.setText("");
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
    String enumerationName;
    if (nameComboBox.getSelectedIndex() >= 0) // chosen from combo box
    {
         enumerationName = nameComboBox.getSelectedItem().toString().substring(3).trim(); // omit index
         enumerationName = enumerationName.substring(0,enumerationName.indexOf(" ")).trim();     // omit full name
    }
    else enumerationName = nameComboBox.getSelectedItem().toString().trim(); // user provided
    
    hAnimDisplacer.setDescription(descriptionTF.getText().trim());
    hAnimDisplacer.setName(enumerationName);
    hAnimDisplacer.setCoordIndex(coordIndexTF.getText().trim());
    hAnimDisplacer.setDisplacements(displacementsTF.getText().trim());
    if (HANIMDISPLACER_ATTR_WEIGHT_REQD || 
            !((weightTF.getText().trim().compareTo("0") == 0) || (weightTF.getText().trim().compareTo("0.0") == 0)))
        hAnimDisplacer.setWeight(weightTF.getText().trim());
  }
  
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField coordIndexTF;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JTextField descriptionTF;
    private javax.swing.JTextField displacementsTF;
    private javax.swing.JLabel hintLabel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JComboBox<String> nameComboBox;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel nameWarningLabel;
    private javax.swing.JPanel nodeHintPanel;
    private javax.swing.JLabel spacerLabel;
    private javax.swing.JTextField weightTF;
    // End of variables declaration//GEN-END:variables
  
}
