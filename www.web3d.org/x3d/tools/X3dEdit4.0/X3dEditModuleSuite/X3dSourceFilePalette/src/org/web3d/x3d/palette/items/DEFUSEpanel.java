/*
Copyright (c) 1995-2022 held by the author(s) .  All rights reserved.
 
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

import java.awt.Font;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import static org.web3d.x3d.actions.BaseViewAction.X3D4_ARCHITECTURE_STANDARD_DIS;
import static org.web3d.x3d.actions.BaseViewAction.X3D_SCENE_AUTHORING_HINTS;
import org.web3d.x3d.actions.LaunchX3dExamplesAction;
import org.web3d.x3d.types.X3DSchemaData;

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
public class DEFUSEpanel extends javax.swing.JPanel
{
  private String           defaultDEFname = "";
  private String[] containerFieldTooltips =  {}; // initially empty
  private BaseCustomizer parentCustomizerPanel;  

  /** Creates new form DEFUSEpanel */
  public DEFUSEpanel()
  {
    initComponents();
    
    checkDEFnameModificationButtonLabel ();
    
    highlightX3dDEFUSEpane();
    highlightHtmlCssPane();
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
    return cssStyleTextField.getText().trim();
  }
  protected void setCssStyle(String newCssStyle)
  {
    if ((parentCustomizerPanel != null) && 
        (parentCustomizerPanel.getBaseX3DElement().getElementName() != X3DSchemaData.FONTSTYLE_ELNAME))
    {
        cssStyleTextField.setText(newCssStyle.trim());
    }
    // TODO handle FontStyle cssStyle attribute
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
        x3dHtmlTabbedPane = new javax.swing.JTabbedPane();
        x3dDefUsejPanel = new javax.swing.JPanel();
        highlightComponentFont();
        defTF = new javax.swing.JTextField();
        DEFnameModificationButton = new javax.swing.JButton();
        defRB = new javax.swing.JRadioButton();
        useRB = new javax.swing.JRadioButton();
        useCB = new javax.swing.JComboBox<>();
        defUseHelpButton = new javax.swing.JButton();
        containerFieldLabel = new javax.swing.JLabel();
        containerFieldCombo = new javax.swing.JComboBox<>();
        containerFieldCheckBox = new javax.swing.JCheckBox();
        containerFieldHelpButton = new javax.swing.JButton();
        htmlCssPanel = new javax.swing.JPanel();
        highlightComponentFont();
        htmlIdLabel = new javax.swing.JLabel();
        htmlIdTextField = new javax.swing.JTextField();
        cssClassLabel = new javax.swing.JLabel();
        cssClassTextField = new javax.swing.JTextField();
        cssStyleLabel = new javax.swing.JLabel();
        cssStyleTextField = new javax.swing.JTextField();
        htmlHelpButton = new javax.swing.JButton();

        setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(153, 153, 153)));
        setMinimumSize(new java.awt.Dimension(604, 80));
        setPreferredSize(new java.awt.Dimension(600, 100));
        setLayout(new java.awt.GridBagLayout());

        x3dHtmlTabbedPane.setToolTipText("DEF, USE, id and style are common to all elements");
        x3dHtmlTabbedPane.setMinimumSize(new java.awt.Dimension(20, 20));
        x3dHtmlTabbedPane.setPreferredSize(new java.awt.Dimension(580, 90));

        x3dDefUsejPanel.setMinimumSize(new java.awt.Dimension(513, 56));
        x3dDefUsejPanel.setPreferredSize(new java.awt.Dimension(400, 54));
        x3dDefUsejPanel.setLayout(new java.awt.GridBagLayout());

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
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 1);
        x3dDefUsejPanel.add(defTF, gridBagConstraints);

        DEFnameModificationButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        DEFnameModificationButton.setText("+");
        DEFnameModificationButton.setToolTipText("add new DEF name (must review/edit to ensure unique)");
        DEFnameModificationButton.setActionCommand("");
        DEFnameModificationButton.setMargin(new java.awt.Insets(1, 2, 1, 2));
        DEFnameModificationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                DEFnameModificationButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 1, 3, 3);
        x3dDefUsejPanel.add(DEFnameModificationButton, gridBagConstraints);

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
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        x3dDefUsejPanel.add(defRB, gridBagConstraints);

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
        x3dDefUsejPanel.add(useRB, gridBagConstraints);

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
        x3dDefUsejPanel.add(useCB, gridBagConstraints);

        defUseHelpButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        defUseHelpButton.setText("?");
        defUseHelpButton.setToolTipText("Naming Conventions, X3D Scene Authoring Hints");
        defUseHelpButton.setActionCommand("X3D4 HTML5 Guidelines");
        defUseHelpButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        defUseHelpButton.setMinimumSize(new java.awt.Dimension(20, 20));
        defUseHelpButton.setPreferredSize(new java.awt.Dimension(20, 20));
        defUseHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                defUseHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 1, 3, 3);
        x3dDefUsejPanel.add(defUseHelpButton, gridBagConstraints);

        containerFieldLabel.setText("containerField");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        x3dDefUsejPanel.add(containerFieldLabel, gridBagConstraints);

        containerFieldCombo.setEditable(true);
        containerFieldCombo.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "schema default" }));
        containerFieldCombo.setToolTipText(org.openide.util.NbBundle.getMessage(DEFUSEpanel.class, "ContainerFieldPanel.containerFieldCombo.toolTipText")); // NOI18N
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
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 1, 3, 3);
        x3dDefUsejPanel.add(containerFieldCombo, gridBagConstraints);

        containerFieldCheckBox.setText(org.openide.util.NbBundle.getMessage(DEFUSEpanel.class, "ContainerFieldPanel.checkBox.text")); // NOI18N
        containerFieldCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(DEFUSEpanel.class, "ContainerFieldPanel.checkBox.toolTipText")); // NOI18N
        containerFieldCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                containerFieldCheckBoxAction(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.insets = new java.awt.Insets(3, 10, 3, 0);
        x3dDefUsejPanel.add(containerFieldCheckBox, gridBagConstraints);

        containerFieldHelpButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        containerFieldHelpButton.setText("?");
        containerFieldHelpButton.setToolTipText("containerField guidance, X3D Scene Authoring Hints");
        containerFieldHelpButton.setActionCommand("X3D4 HTML5 Guidelines");
        containerFieldHelpButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        containerFieldHelpButton.setMinimumSize(new java.awt.Dimension(20, 20));
        containerFieldHelpButton.setPreferredSize(new java.awt.Dimension(20, 20));
        containerFieldHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                containerFieldHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 1, 3, 3);
        x3dDefUsejPanel.add(containerFieldHelpButton, gridBagConstraints);

        x3dHtmlTabbedPane.addTab("   X3D DEF USE containerField    ", null, x3dDefUsejPanel, "X3D common attributes");

        htmlCssPanel.setToolTipText("");
        htmlCssPanel.setPreferredSize(new java.awt.Dimension(400, 56));
        htmlCssPanel.setLayout(new java.awt.GridBagLayout());

        htmlIdLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        htmlIdLabel.setText("id");
        htmlIdLabel.setToolTipText("id attribute is only used for HTML events");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        htmlCssPanel.add(htmlIdLabel, gridBagConstraints);

        htmlIdTextField.setToolTipText("id attribute is only used for HTML events");
        htmlIdTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                htmlIdTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 32;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        htmlCssPanel.add(htmlIdTextField, gridBagConstraints);

        cssClassLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        cssClassLabel.setText("class");
        cssClassLabel.setToolTipText("class field is only used for CSS class");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        htmlCssPanel.add(cssClassLabel, gridBagConstraints);

        cssClassTextField.setToolTipText("class field is only used for CSS class");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 32;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        htmlCssPanel.add(cssClassTextField, gridBagConstraints);

        cssStyleLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        cssStyleLabel.setText("style");
        cssStyleLabel.setToolTipText("style field is only used for CSS styles");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 16, 3, 0);
        htmlCssPanel.add(cssStyleLabel, gridBagConstraints);

        cssStyleTextField.setToolTipText("CSS style information for HTML5 page");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        htmlCssPanel.add(cssStyleTextField, gridBagConstraints);

        htmlHelpButton.setFont(new java.awt.Font("Tahoma", 0, 10)); // NOI18N
        htmlHelpButton.setText("?");
        htmlHelpButton.setToolTipText("X3D4 HTML5 CSS Guidelines for X3D4 Architecture");
        htmlHelpButton.setActionCommand("X3D4 HTML5 Guidelines");
        htmlHelpButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        htmlHelpButton.setMinimumSize(new java.awt.Dimension(20, 20));
        htmlHelpButton.setPreferredSize(new java.awt.Dimension(20, 20));
        htmlHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                htmlHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        htmlCssPanel.add(htmlHelpButton, gridBagConstraints);

        x3dHtmlTabbedPane.addTab("   HTML5 id, CSS class style    ", null, htmlCssPanel, "HTML5 CSS common attributes");

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        add(x3dHtmlTabbedPane, gridBagConstraints);
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
//        setHtmlPaneVisibility (hasHtmlCssFields());
    }
    
    public void setHtmlPaneVisibility (boolean setVisibleValue)
    {
        if (true) return; // disabled, not needed with tabbed panel
        
        htmlPaneVisible = setVisibleValue; // remembersetHtmlPaneVisibility
        
              htmlIdLabel.setVisible(htmlPaneVisible);
          htmlIdTextField.setVisible(htmlPaneVisible);
            cssClassLabel.setVisible(htmlPaneVisible);
        cssClassTextField.setVisible(htmlPaneVisible);
            cssStyleLabel.setVisible(htmlPaneVisible);
        cssStyleTextField.setVisible(htmlPaneVisible);
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
    private void htmlHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_htmlHelpButtonActionPerformed

        // launch appropriate X3D help
        LaunchX3dExamplesAction.sendBrowserTo(X3D4_ARCHITECTURE_STANDARD_DIS.replace("Architecture", "htmlGuidelines"));
    }//GEN-LAST:event_htmlHelpButtonActionPerformed

    private void htmlIdTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_htmlIdTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_htmlIdTextFieldActionPerformed

    private void defUseHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_defUseHelpButtonActionPerformed
        // launch appropriate X3D help
        LaunchX3dExamplesAction.sendBrowserTo(X3D_SCENE_AUTHORING_HINTS + "#NamingConventions");
    }//GEN-LAST:event_defUseHelpButtonActionPerformed

    private void containerFieldHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_containerFieldHelpButtonActionPerformed
        // launch appropriate X3D help
        LaunchX3dExamplesAction.sendBrowserTo(X3D_SCENE_AUTHORING_HINTS + "#containerField");
    }//GEN-LAST:event_containerFieldHelpButtonActionPerformed
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
    private javax.swing.JButton containerFieldHelpButton;
    private javax.swing.JLabel containerFieldLabel;
    private javax.swing.JLabel cssClassLabel;
    private javax.swing.JTextField cssClassTextField;
    private javax.swing.JLabel cssStyleLabel;
    private javax.swing.JTextField cssStyleTextField;
    private javax.swing.JRadioButton defRB;
    private javax.swing.JTextField defTF;
    private javax.swing.JButton defUseHelpButton;
    private javax.swing.JPanel htmlCssPanel;
    private javax.swing.JButton htmlHelpButton;
    private javax.swing.JLabel htmlIdLabel;
    private javax.swing.JTextField htmlIdTextField;
    private javax.swing.JComboBox<String> useCB;
    private javax.swing.JRadioButton useRB;
    private javax.swing.JPanel x3dDefUsejPanel;
    private javax.swing.JTabbedPane x3dHtmlTabbedPane;
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
    public boolean hasX3dDEFUSEfields ()
    {
        if ((parentCustomizerPanel != null) && (parentCustomizerPanel.getBaseX3DElement() != null))
        {
            BaseX3DElement parentPanel = parentCustomizerPanel.getBaseX3DElement();
            boolean result =  !parentPanel.getDEFUSEvalue().isEmpty();
//                         || !parentPanel.getContainerField().isEmpty() // TODO if non-default
            return result;
        }
        else return false;
    }

    public final int INDEX_X3D_DEFUSE = 0;
    public final int INDEX_HTML_CSS   = 1;
    /**
     * Select appropriate tab
     */
    public void selectX3dDEFUSEpane() 
    {
        x3dHtmlTabbedPane.setSelectedIndex(INDEX_X3D_DEFUSE);
    }
    /**
     * Select appropriate tab
     */
    public void selectHtmlCssPane() 
    {
        x3dHtmlTabbedPane.setSelectedIndex(INDEX_HTML_CSS);
    }

    /**
     * @return the parentPanel
     */
    public JPanel getParentPanel() {
        return parentCustomizerPanel;
    }
    /**
     * Highlight appropriate tab label, if data is found in pane.
     */
    public final void highlightComponentFont() 
    {
        Font newFont = getFont();
        if (hasX3dDEFUSEfields())
             newFont = newFont.deriveFont(Font.BOLD);
        else newFont = newFont.deriveFont(Font.PLAIN);
        setFont(newFont);
    }
    /**
     * Highlight appropriate tab label, if data is found in pane.
     */
    public final void highlightX3dDEFUSEpane() 
    {
        Font newFont = x3dDefUsejPanel.getFont();
        if (hasX3dDEFUSEfields())
             newFont = newFont.deriveFont(Font.BOLD);
        else newFont = newFont.deriveFont(Font.PLAIN);
        x3dDefUsejPanel.setFont(newFont);
        
        SwingUtilities.invokeLater(() -> {
            x3dDefUsejPanel.revalidate();
            x3dDefUsejPanel.repaint();
            
            if (parentCustomizerPanel != null) {
                parentCustomizerPanel.revalidate();
                parentCustomizerPanel.repaint();
            }
        });
    }
    /**
     * Highlight appropriate tab label, if data is found in pane.
     */
    public final void highlightHtmlCssPane() 
    {
        Font newFont = htmlCssPanel.getFont();
        if (hasX3dDEFUSEfields())
             newFont = newFont.deriveFont(Font.BOLD);
        else newFont = newFont.deriveFont(Font.PLAIN);
        htmlCssPanel.setFont(newFont);
    }
    
    public void refreshPanel()
    {
        SwingUtilities.invokeLater(() -> {
            x3dDefUsejPanel.revalidate();
            x3dDefUsejPanel.repaint();
            
            if (parentCustomizerPanel != null) {
                parentCustomizerPanel.revalidate();
                parentCustomizerPanel.repaint();
            }
        });
    }

    /**
     * Receive link to parent panel so that callbacks and responsiveness is possible
     * @param passedParentCustomizerPanel the parentPanel to set
     */
    public void setParentPanel(BaseCustomizer passedParentCustomizerPanel)
    {
        this.parentCustomizerPanel = passedParentCustomizerPanel;
    }
}
