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

import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import static org.web3d.x3d.actions.BaseViewAction.X3D4_ARCHITECTURE_STANDARD_DIS;
import org.web3d.x3d.actions.LaunchX3dExamplesAction;

/**
 * DEFUSEpanel.java
 * Created on August 9, 2007, 2:01 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
@Deprecated
public class DEFUSEtogglePanel extends javax.swing.JPanel
{
  private String           defaultDEFname = "";
  private String[] containerFieldTooltips =  {}; // initially empty
  private BaseCustomizer parentCustomizerPanel;  

  /** Creates new form DEFUSEpanel */
  public DEFUSEtogglePanel()
  {
    initComponents();
    
    // try to fix Netbeans warnings by proper boxing during combobox initialization
//    containerFieldCombo.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "schema default" }));
//    containerFieldCombo.setSelectedItem("schema default");
    
    checkDEFnameModificationButtonLabel ();
  }

  protected String getDEF()
  {
    return defTF.getText().trim();
  }

  protected void setDEF(String newDEF)
  {
    defTF.setText(newDEF.trim());
  }

  protected String getUSE()
  {
    return useCB.getSelectedItem().toString().trim();
  }

  protected void setUSE(String newUSE)
  {
    useCB.setSelectedItem(newUSE.trim()); // TODO test
  }

  protected javax.swing.JRadioButton getDefRB()
  {
    return defRB;
  }

  protected javax.swing.JRadioButton getUseRB()
  {
    return useRB;
  }

  protected javax.swing.JTextField getDefTF()
  {
    return defTF;
  }

  protected javax.swing.JComboBox<String> getUseCB()
  {
    return useCB;
  }

  protected String getContainerField()
  {
    if(containerFieldCheckBox.isSelected())
      return (String)containerFieldCombo.getSelectedItem();
    return null;
  }
  
  protected void setContainerField(String s)
  {
    containerFieldCombo.setEnabled(true);  // ensure that combo box is turned on
    containerFieldCombo.setEditable(true); // and editable
    containerFieldCombo.setSelectedItem(s);
	
    if (!containerFieldCombo.getSelectedItem().toString().equalsIgnoreCase(s))
    {
		containerFieldCombo.addItem(s);
		containerFieldCombo.setSelectedItem(s);
    }
    if (!containerFieldCombo.getSelectedItem().toString().equalsIgnoreCase(s)) // still missing after adding
    {
		// TODO notifier?
        System.err.println ("Error, DEFUSEpanel.setContainerField(" + s + ") is unexpected value");
    }
	// http://stackoverflow.com/questions/10904639/how-to-refresh-the-jcombobox-data
	this.revalidate();
	this.repaint();
  }
  
  protected void setContainerFieldChoices(String[] choices)
  {
    containerFieldCombo.setModel(new DefaultComboBoxModel<>(choices));
    containerFieldCombo.setEnabled(true);  // ensure that combo box is turned on
    containerFieldCombo.setEditable(true); // and editable
  }

  // overriding single tooltip, consider overriding individual tooltips for each combobox list entry
  public void setContainerFieldTooltips(String[] newContainerFieldTooltips)
  {
    containerFieldTooltips = newContainerFieldTooltips;
    assignCorrectTooltip ();
    containerFieldCombo.setEnabled(true);  // ensure that combo box is turned on
    containerFieldCombo.setEditable(true); // and editable
  }
  protected void setContainerFieldChoices(String[] containerFieldChoices, String containerFieldTooltip)
  {
    containerFieldCombo.setModel(new DefaultComboBoxModel<>(containerFieldChoices));
    containerFieldCombo.setToolTipText(containerFieldTooltip);
    containerFieldCombo.setEnabled(true);  // ensure that combo box is turned on
    containerFieldCombo.setEditable(true); // and editable
  }
  protected void setContainerFieldChoices(String[] containerFieldChoices, String[] containerFieldTooltips)
  {
    containerFieldCombo.setModel(new DefaultComboBoxModel<>(containerFieldChoices));
    setContainerFieldTooltips(containerFieldTooltips);
    containerFieldCombo.setEnabled(true);  // ensure that combo box is turned on
    containerFieldCombo.setEditable(true); // and editable
  }
  
  protected void setUseContainerField(boolean b)
  {
    containerFieldCheckBox.setSelected(b);
//    containerFieldCheckBoxAction(null);
  }
  
  protected boolean getUseContainerField()
  {
    return containerFieldCheckBox.isSelected();
  }
  private void checkDEFnameModificationButtonLabel ()
  {
      if (getDEF().length() > 0)
      {
          DEFnameModificationButton.setText("--");
          DEFnameModificationButton.setToolTipText("Clear DEF name");
      }
      else
      {
          DEFnameModificationButton.setText("+");
          DEFnameModificationButton.setToolTipText("Add example DEF name, edit to ensure unique");
      }
  }
  protected String getHtmlID()
  {
    return htmlIdTextField.getText().trim();
  }
  protected void setHtmlID(String newDEF)
  {
    htmlIdTextField.setText(newDEF.trim());
  }
  protected String getCssClass()
  {
    return cssClassTextField.getText().trim();
  }
  protected void setCssClass(String newDEF)
  {
    cssClassTextField.setText(newDEF.trim());
  }
  protected String getCssStyle()
  {
    return cssStyleTextArea.getText().trim();
  }
  protected void setCssStyle(String newDEF)
  {
    cssStyleTextArea.setText(newDEF.trim());
  }
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        defTF = new javax.swing.JTextField();
        DEFnameModificationButton = new javax.swing.JButton();
        defRB = new javax.swing.JRadioButton();
        useRB = new javax.swing.JRadioButton();
        useCB = new javax.swing.JComboBox<>();
        containerFieldLabel = new javax.swing.JLabel();
        containerFieldCombo = new javax.swing.JComboBox<>();
        containerFieldCheckBox = new javax.swing.JCheckBox();
        htmlIdLabel = new javax.swing.JLabel();
        htmlIdTextField = new javax.swing.JTextField();
        cssClassLabel = new javax.swing.JLabel();
        cssClassTextField = new javax.swing.JTextField();
        cssStyleLabel = new javax.swing.JLabel();
        cssStyleScrollPane = new javax.swing.JScrollPane();
        cssStyleTextArea = new javax.swing.JTextArea();
        htmlSubpanelLabel = new javax.swing.JLabel();
        htmlSeparator = new javax.swing.JSeparator();
        htmlPaneToggleButton = new javax.swing.JToggleButton();
        htmlHelpButton = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        setMaximumSize(new java.awt.Dimension(800, 200));
        setMinimumSize(new java.awt.Dimension(500, 80));
        setPreferredSize(new java.awt.Dimension(500, 180));
        setLayout(new java.awt.GridBagLayout());

        defTF.setToolTipText("DEF defines a unique ID name for this node, referencable by other nodes.  No embedded spaces, start with letter");
        defTF.setMaximumSize(new java.awt.Dimension(6, 2147483647));
        defTF.setPreferredSize(new java.awt.Dimension(40, 20));
        defTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                defTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 1);
        add(defTF, gridBagConstraints);

        DEFnameModificationButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        DEFnameModificationButton.setText("+");
        DEFnameModificationButton.setToolTipText("add new DEF name (edit to ensure unique)");
        DEFnameModificationButton.setActionCommand("");
        DEFnameModificationButton.setMargin(new java.awt.Insets(1, 2, 1, 2));
        DEFnameModificationButton.setMaximumSize(new java.awt.Dimension(17, 17));
        DEFnameModificationButton.setMinimumSize(new java.awt.Dimension(17, 17));
        DEFnameModificationButton.setPreferredSize(new java.awt.Dimension(17, 17));
        DEFnameModificationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DEFnameModificationButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.insets = new java.awt.Insets(3, 1, 3, 3);
        add(DEFnameModificationButton, gridBagConstraints);

        buttonGroup1.add(defRB);
        defRB.setSelected(true);
        defRB.setText("DEF");
        defRB.setToolTipText("DEF defines a unique ID name for this node, referencable by other nodes.  No embedded spaces, start with letter");
        defRB.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        defRB.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        defRB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        defRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enableDisable(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        add(defRB, gridBagConstraints);

        buttonGroup1.add(useRB);
        useRB.setText("USE");
        useRB.setToolTipText("USE references an already DEF-ed node ID, ignoring all other attributes and children");
        useRB.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 0, 0, 0));
        useRB.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        useRB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        useRB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                enableDisable(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        add(useRB, gridBagConstraints);

        useCB.setEditable(true);
        useCB.setToolTipText("USE references an already DEF-ed node ID, ignoring all other attributes and children");
        useCB.setEnabled(false);
        useCB.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                useCBFocusGained(evt);
            }
        });
        useCB.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                useCBMouseClicked(evt);
            }
        });
        useCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                useCBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 1);
        add(useCB, gridBagConstraints);

        containerFieldLabel.setText("containerField");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        add(containerFieldLabel, gridBagConstraints);

        containerFieldCombo.setEditable(true);
        containerFieldCombo.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "schema default" }));
        containerFieldCombo.setToolTipText(org.openide.util.NbBundle.getMessage(DEFUSEtogglePanel.class, "ContainerFieldPanel.containerFieldCombo.toolTipText")); // NOI18N
        containerFieldCombo.setAutoscrolls(true);
        containerFieldCombo.setMinimumSize(new java.awt.Dimension(150, 18));
        containerFieldCombo.setPreferredSize(new java.awt.Dimension(160, 20));
        containerFieldCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                containerFieldComboActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 1, 3, 3);
        add(containerFieldCombo, gridBagConstraints);

        containerFieldCheckBox.setText(org.openide.util.NbBundle.getMessage(DEFUSEtogglePanel.class, "ContainerFieldPanel.checkBox.text")); // NOI18N
        containerFieldCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(DEFUSEtogglePanel.class, "ContainerFieldPanel.checkBox.toolTipText")); // NOI18N
        containerFieldCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                containerFieldCheckBoxAction(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(3, 10, 3, 0);
        add(containerFieldCheckBox, gridBagConstraints);

        htmlIdLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        htmlIdLabel.setText("id");
        htmlIdLabel.setToolTipText("id attribute is only used for HTML events");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        add(htmlIdLabel, gridBagConstraints);

        htmlIdTextField.setToolTipText("id attribute is only used for HTML events");
        htmlIdTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                htmlIdTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 1);
        add(htmlIdTextField, gridBagConstraints);

        cssClassLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        cssClassLabel.setText("class");
        cssClassLabel.setToolTipText("class field is only used for CSS class");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        add(cssClassLabel, gridBagConstraints);

        cssClassTextField.setToolTipText("class field is only used for CSS class");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 1, 3, 3);
        add(cssClassTextField, gridBagConstraints);

        cssStyleLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        cssStyleLabel.setText("style");
        cssStyleLabel.setToolTipText("style field is only used for CSS styles");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        add(cssStyleLabel, gridBagConstraints);

        cssStyleTextArea.setColumns(20);
        cssStyleTextArea.setRows(5);
        cssStyleTextArea.setToolTipText("style field is only used for CSS styles");
        cssStyleScrollPane.setViewportView(cssStyleTextArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 32;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(cssStyleScrollPane, gridBagConstraints);

        htmlSubpanelLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        htmlSubpanelLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        htmlSubpanelLabel.setText("HTML and CSS attributes for X3D4 in Web page");
        htmlSubpanelLabel.setToolTipText("HTML and CSS attributes have no effect on standalone X3D functionality");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(htmlSubpanelLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
        add(htmlSeparator, gridBagConstraints);

        htmlPaneToggleButton.setFont(new java.awt.Font("Tahoma", 0, 8)); // NOI18N
        htmlPaneToggleButton.setText("HTML and CSS");
        htmlPaneToggleButton.setToolTipText("show/hide HTML and CSS fields");
        htmlPaneToggleButton.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        htmlPaneToggleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                htmlPaneToggleButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 6;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 3, 3);
        add(htmlPaneToggleButton, gridBagConstraints);

        htmlHelpButton.setText("X3D4 HTML CSS Guidelines");
        htmlHelpButton.setToolTipText("HTML CSS Guidelines for X3D4 Architecture");
        htmlHelpButton.setMaximumSize(new java.awt.Dimension(170, 23));
        htmlHelpButton.setMinimumSize(new java.awt.Dimension(170, 23));
        htmlHelpButton.setPreferredSize(new java.awt.Dimension(170, 23));
        htmlHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                htmlHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(1, 3, 3, 3);
        add(htmlHelpButton, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

  private void enableDisable(java.awt.event.ActionEvent evt)//GEN-FIRST:event_enableDisable
  {//GEN-HEADEREND:event_enableDisable
                   getDefTF().setEnabled(getDefRB().isSelected());
                   getUseCB().setEnabled(getUseRB().isSelected());
    DEFnameModificationButton.setEnabled(getDefRB().isSelected());
    // TODO BaseCustomizer.enableAppendVisualizationCB.setEnabled(getDefRB().isSelected());
    checkDEFnameModificationButtonLabel ();
  }//GEN-LAST:event_enableDisable

    private void containerFieldCheckBoxAction(java.awt.event.ActionEvent evt)//GEN-FIRST:event_containerFieldCheckBoxAction
    {//GEN-HEADEREND:event_containerFieldCheckBoxAction
        containerFieldCombo.setEnabled(containerFieldCheckBox.isSelected());     
    }//GEN-LAST:event_containerFieldCheckBoxAction

    private void DEFnameModificationButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_DEFnameModificationButtonActionPerformed
    {//GEN-HEADEREND:event_DEFnameModificationButtonActionPerformed
        if (DEFnameModificationButton.getText().equals("+"))
        {
             setDEF(defaultDEFname); // TODO increment by number if needed
        }
        else setDEF(""); // -- minus button

        checkDEFnameModificationButtonLabel ();
    }//GEN-LAST:event_DEFnameModificationButtonActionPerformed

    private void defTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_defTFActionPerformed
    {//GEN-HEADEREND:event_defTFActionPerformed
        checkDEFnameModificationButtonLabel ();
    }//GEN-LAST:event_defTFActionPerformed

    private void containerFieldComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_containerFieldComboActionPerformed
        assignCorrectTooltip ();
		containerFieldCheckBox.setSelected(true);
    }//GEN-LAST:event_containerFieldComboActionPerformed

    private void useCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_useCBActionPerformed
        if (useCB.hasFocus())
            useRB.setSelected(true); // these callbacks don't work unless combobox is enabled, so auto-turnon doesn't appear to be possible
    }//GEN-LAST:event_useCBActionPerformed

    private void useCBFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_useCBFocusGained
        if (useCB.hasFocus())
            useRB.setSelected(true);
    }//GEN-LAST:event_useCBFocusGained

    private void useCBMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_useCBMouseClicked
        if (useCB.hasFocus())
            useRB.setSelected(true);
    }//GEN-LAST:event_useCBMouseClicked
    // avoid static for persistence since this pane applies to multiple node editors
    private boolean htmlPaneVisible = true; 
    
    public void resetHtmlPaneVisibility ()
    {
        setHtmlPaneVisibility (hasHtmlCssFields());
    }
    
    public void setHtmlPaneVisibility (boolean setVisibleValue)
    {
        htmlPaneVisible = setVisibleValue; // remembersetHtmlPaneVisibility
        
        htmlSubpanelLabel.setVisible(htmlPaneVisible);
              htmlIdLabel.setVisible(htmlPaneVisible);
          htmlIdTextField.setVisible(htmlPaneVisible);
            cssClassLabel.setVisible(htmlPaneVisible);
        cssClassTextField.setVisible(htmlPaneVisible);
            cssStyleLabel.setVisible(htmlPaneVisible);
       cssStyleScrollPane.setVisible(htmlPaneVisible);
           htmlHelpButton.setVisible(htmlPaneVisible);
           
//                         this.revalidate(); 
//                         this.repaint();
//        parentCustomizerPanel.revalidate();
//        parentCustomizerPanel.repaint();
    }
    private void htmlPaneToggle ()
    {
        htmlPaneVisible = !htmlPaneVisible; // toggle offset
        setHtmlPaneVisibility(htmlPaneVisible);
    }
    private void htmlPaneToggleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_htmlPaneToggleButtonActionPerformed
        htmlPaneToggle ();
    }//GEN-LAST:event_htmlPaneToggleButtonActionPerformed

    private void htmlHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_htmlHelpButtonActionPerformed

        // launch appropriate X3D help
        LaunchX3dExamplesAction.sendBrowserTo(X3D4_ARCHITECTURE_STANDARD_DIS.replace("Architecture", "htmlGuidelines"));
    }//GEN-LAST:event_htmlHelpButtonActionPerformed

    private void htmlIdTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_htmlIdTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_htmlIdTextFieldActionPerformed
    private void assignCorrectTooltip ()
    {
        int  index = containerFieldCombo.getSelectedIndex();
        if ((index >= 0) && (index < containerFieldTooltips.length))
             containerFieldCombo.setToolTipText(containerFieldTooltips[index]);
    }  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton DEFnameModificationButton;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JCheckBox containerFieldCheckBox;
    private javax.swing.JComboBox<String> containerFieldCombo;
    private javax.swing.JLabel containerFieldLabel;
    private javax.swing.JLabel cssClassLabel;
    private javax.swing.JTextField cssClassTextField;
    private javax.swing.JLabel cssStyleLabel;
    private javax.swing.JScrollPane cssStyleScrollPane;
    private javax.swing.JTextArea cssStyleTextArea;
    private javax.swing.JRadioButton defRB;
    private javax.swing.JTextField defTF;
    private javax.swing.JButton htmlHelpButton;
    private javax.swing.JLabel htmlIdLabel;
    private javax.swing.JTextField htmlIdTextField;
    public javax.swing.JToggleButton htmlPaneToggleButton;
    private javax.swing.JSeparator htmlSeparator;
    private javax.swing.JLabel htmlSubpanelLabel;
    private javax.swing.JComboBox<String> useCB;
    private javax.swing.JRadioButton useRB;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the defaultDEFname
     */
    public String getDefaultDEFname()
    {
        return defaultDEFname;
    }

    /**
     * @param newDefaultDEFname the defaultDEFname to set
     */
    public void setDefaultDEFname(String newDefaultDEFname)
    {
        // ensure no illegal characters are included in default DEF name
        while (newDefaultDEFname.contains("."))
               newDefaultDEFname = newDefaultDEFname.substring(0, newDefaultDEFname.lastIndexOf('.'));
        while (newDefaultDEFname.contains("/"))
               newDefaultDEFname = newDefaultDEFname.substring(newDefaultDEFname.lastIndexOf('/')+1);
        while (newDefaultDEFname.contains("\\"))
               newDefaultDEFname = newDefaultDEFname.substring(newDefaultDEFname.lastIndexOf('\\')+1);
        while (newDefaultDEFname.contains(":"))
               newDefaultDEFname = newDefaultDEFname.substring(newDefaultDEFname.lastIndexOf(':')+1);
        this.defaultDEFname = newDefaultDEFname;

        checkDEFnameModificationButtonLabel ();
    }

    /**
     * @return the parentPanel
     */
    public JPanel getParentPanel() {
        return parentCustomizerPanel;
    }
    
    public boolean hasHtmlCssFields ()
    {
        if ((parentCustomizerPanel != null) && (parentCustomizerPanel.getBaseX3DElement() != null))
        {
            BaseX3DElement parentPanel = parentCustomizerPanel.getBaseX3DElement();
            boolean result =  !parentPanel.getHtmlID().isEmpty()   || 
                              !parentPanel.getCssClass().isEmpty() || 
                              !parentPanel.getCssStyle().isEmpty();
            return result;
        }
        else return false;
    }

    /**
     * @param parentCustomizerPanel the parentPanel to set
     */
    public void setParentPanel(BaseCustomizer passedParentCustomizerPanel)
    {
        this.parentCustomizerPanel = passedParentCustomizerPanel;
    }
}
