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

import java.awt.Color;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.DefaultComboBoxModel;
import javax.swing.text.JTextComponent;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.HtmlBrowser.URLDisplayer;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.web3d.x3d.X3DDataObject;
import org.web3d.x3d.tools.usage.DateTimeGroupStamp;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * METACustomizer.java
 * Created on March 14, 2007, 9:57 AM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey and Don Brutzman
 * @version $Id$
 */
public class METACustomizer extends BaseCustomizer
{
  private final META meta;
  private final JTextComponent target;
  private Color  normalBackgroundColor, warningBackgroundColor;
  private final UrlExpandableList2 urlExpandableList = new UrlExpandableList2();
  private final String localDirectory;
  private final String generatorValueX3dEdit = "X3D-Edit 4.0, https://savage.nps.edu/X3D-Edit";

  public METACustomizer(META meta, JTextComponent target, X3DDataObject xObj)
  {
    super(meta);
    this.meta = meta;
    this.target = target;
    localDirectory = xObj.getX3dDataObjectDirectory();
   
    HelpCtx.setHelpIDString(METACustomizer.this, "META_ELEM_HELPID");
    
    initComponents();
    
    // use UrlExpandableList2 utility method to launch links in browser
    dummyUrlList.setVisible(false);
    dummyUrlList.setMasterDocumentLocation(xObj.getPrimaryFile());

    nameComboBox.setSelectedItem(meta.getName());
    nameComboBoxTooltipReset ();
    contentTA.setText(meta.getContentAttribute());
    contentTA.selectAll(); // open with all text selected
    
    // handle case sensitivity or empty/null values
    if ((meta.getDir() == null) || meta.getDir().isEmpty())
         dirComboBox.setSelectedItem(0);
    else if (meta.getDir().equalsIgnoreCase("LTR"))
         dirComboBox.setSelectedItem(1);
    else if (meta.getDir().equalsIgnoreCase("RTL"))
         dirComboBox.setSelectedItem(2);
    else dirComboBox.setSelectedItem(0);
    
    httpEquivalentTextField.setText(meta.getHttpEquivalent()); // TODO convert to combo box with common selections
              langTextField.setText(meta.getLang());
            schemeTextField.setText(meta.getScheme());
    
    nameHttpEquivalentTextWarningCheck ();
    enableUrlButtons ();
    
    checkMetaNameContentCombinations();
  }
  
  /** Check and offer to correct mismatched meta name=content combinations */
  private void checkMetaNameContentCombinations()
  {
    // initialization assists, authoring shortcuts
    String metaName = nameComboBox.getSelectedItem().toString().trim();
    String  content = contentTA.getText().trim();
    
    if (metaName.equals("created")  || 
        metaName.equals("imported") || 
        metaName.equals("modified") || 
        metaName.equals("translated")) 
    {
       // insert date if missing occurs later
       if (metaName.equals("modified") &&
                !content.equals(getTodaysDate ()))
       {
            NotifyDescriptor d = new NotifyDescriptor.Confirmation(
               "<html><p align='center'>Set today's date?</p><p>&nbsp;</p><p align='center'>\"<b>" + content + "</b>\" to \"<b>" + getTodaysDate () + "</b>\"</p>",
               "Confirm", NotifyDescriptor.YES_NO_OPTION);
            if (DialogDisplayer.getDefault().notify(d) == NotifyDescriptor.YES_OPTION)
            {
                contentTA.setText(getTodaysDate ());
            }
       }
       contentTA.selectAll(); // ensure open with all text selected
//         todaysDateButton.setEnabled(true); // unneeded, stays active throughout
    }
    else  if (metaName.equals("generator") &&
              content.trim().isEmpty())
    {
        contentTA.setText("X3D-Edit 4.0, https://savage.nps.edu/X3D-Edit");
    }
    else  if (metaName.equals("warning") &&
              content.trim().isEmpty())
    {
        contentTA.setText("under development");
    }

    if  (nameHelpReference ().equals(META_ATTR_NAME_REFERENCE))
         nameComboBox.setForeground(Color.orange); // not found
    else nameComboBox.setForeground(Color.green);
    
    if (!metaName.trim().equals(metaName))
    {
        NotifyDescriptor d = new NotifyDescriptor.Confirmation(
           "<html><p align='center'>Trim whitespace from meta name?</p><p>&nbsp;</p><p align='center'>\"<b>" + metaName + "</b>\" to \"<b>" + metaName.trim() + "</b>\"</p>",
           "Confirm", NotifyDescriptor.YES_NO_OPTION);
        if (DialogDisplayer.getDefault().notify(d) == NotifyDescriptor.YES_OPTION)
        {
            nameComboBox.setSelectedItem(metaName.trim());
            metaName = metaName.trim();
        }
    }
    
    // check tooltip name capitalization
    for (int index = 0; index < META_ATTR_NAME_CHOICES.length; index++)
    {
        if (metaName.equalsIgnoreCase(META_ATTR_NAME_CHOICES[index]) && !metaName.equals(META_ATTR_NAME_CHOICES[index])) 
        {
            NotifyDescriptor d = new NotifyDescriptor.Confirmation(
               "<html><p align='center'>Fix capitalization of meta name?</p><p>&nbsp;</p><p align='center'><b>" + metaName + "</b> to <b>" + META_ATTR_NAME_CHOICES[index] + "</b></p>",
               "Confirm Capitalization Correction", NotifyDescriptor.YES_NO_OPTION);
            if (DialogDisplayer.getDefault().notify(d) == NotifyDescriptor.YES_OPTION)
            {
                nameComboBox.setSelectedItem(META_ATTR_NAME_CHOICES[index]);
                nameComboBox.setToolTipText (META_ATTR_NAME_CHOICES_TOOLTIPS[index]);
            }
            break;
        }
    }
    if (!content.trim().equals(content))
    {
        NotifyDescriptor d = new NotifyDescriptor.Confirmation(
           "<html><p align='center'>Trim whitespace from content?</p><p>&nbsp;</p><p align='center'>\"<b>" + content + "</b>\"<br /> to <br />\"<b>" + content.trim() + "</b>\"</p>",
           "Confirm", NotifyDescriptor.YES_NO_OPTION);
        if (DialogDisplayer.getDefault().notify(d) == NotifyDescriptor.YES_OPTION)
        {
            contentTA.setText(content.trim());
            content = content.trim();
        }
    }
    String contentLowerCase = content.toLowerCase();
    if (metaName.equalsIgnoreCase("generator") &&
        (  contentLowerCase.isEmpty() ||
        ( (contentLowerCase.contains("X3D-Edit".toLowerCase()) || contentLowerCase.contains("X3DEdit".toLowerCase())) &&
             !content.contains(generatorValueX3dEdit))))
    {
        NotifyDescriptor d = new NotifyDescriptor.Confirmation(
           "<html><p align='center'>Update X3D-Edit generator value?</p><p>&nbsp;</p><p align='center'><b>" + generatorValueX3dEdit + "</b></p>",
           "Confirm", NotifyDescriptor.YES_NO_OPTION);
        if (DialogDisplayer.getDefault().notify(d) == NotifyDescriptor.YES_OPTION)
        {
            contentTA.setText(generatorValueX3dEdit);
        }
    }
    if (metaName.equals("modified") && contentLowerCase.isEmpty())
    {
        NotifyDescriptor d = new NotifyDescriptor.Confirmation(
           "<html><p align='center'>Update <b>modified</b> meta content to match today's date?</p>",
           "Confirm", NotifyDescriptor.YES_NO_OPTION);
        if (DialogDisplayer.getDefault().notify(d) == NotifyDescriptor.YES_OPTION)
        {
           contentTA.setText(getTodaysDate ().trim());
        } 
   }
   if (content.startsWith("http://"))
   {              
        NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
              "<html><p align='center'>url address is insecure, http:// instead of https:// - fix it?</p><br/><p align='center'>" + content + "</p>", 
              "Insecure http address found",
              NotifyDescriptor.YES_NO_OPTION);
        if (DialogDisplayer.getDefault().notify(descriptor)== NotifyDescriptor.YES_OPTION)
        {
            contentTA.setText(content.replace("http://", "https://"));
        }
   }
   enableUrlButtons (); // initialize 
  }
  
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        nameLabel = new javax.swing.JLabel();
        nameComboBox = new javax.swing.JComboBox<>();
        nameHelpButton = new javax.swing.JButton();
        nameTooltipLabel = new javax.swing.JLabel();
        contentLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        contentTA = new javax.swing.JTextArea();
        nodeHintPanel = new javax.swing.JPanel();
        hintLabel = new javax.swing.JLabel();
        generatorSelectComboBox = new javax.swing.JComboBox<>();
        todoQuickSelectComboBox = new javax.swing.JComboBox<>();
        chooserButton = new javax.swing.JButton();
        loadContentButton = new javax.swing.JButton();
        openContentButton = new javax.swing.JButton();
        externalEditorButton = new javax.swing.JButton();
        qaButton = new javax.swing.JButton();
        todaysDateButton = new javax.swing.JButton();
        prependHttpButton = new javax.swing.JButton();
        dummyUrlList = new org.web3d.x3d.palette.items.UrlExpandableList2();
        clearButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        dirLabel = new javax.swing.JLabel();
        dirComboBox = new javax.swing.JComboBox<>();
        dirHelpButton = new javax.swing.JButton();
        httpEquivalentLabel = new javax.swing.JLabel();
        httpEquivalentTextField = new javax.swing.JTextField();
        httpEquivalentLookupButton = new javax.swing.JButton();
        httpEquivalentHelpButton = new javax.swing.JButton();
        langLabel = new javax.swing.JLabel();
        langTextField = new javax.swing.JTextField();
        langLookupButton = new javax.swing.JButton();
        langHelpButton = new javax.swing.JButton();
        schemeLabel = new javax.swing.JLabel();
        schemeTextField = new javax.swing.JTextField();
        schemeHelpButton = new javax.swing.JButton();
        domainButton = new javax.swing.JButton();
        pingButton = new javax.swing.JButton();
        directionLabel = new javax.swing.JLabel();
        prependHttpsButton = new javax.swing.JButton();

        setLayout(new java.awt.GridBagLayout());

        nameLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        nameLabel.setText("name");
        nameLabel.setToolTipText("name of metadata term");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 6, 3);
        add(nameLabel, gridBagConstraints);

        nameComboBox.setEditable(true);
        nameComboBox.setModel(new DefaultComboBoxModel<>(META_ATTR_NAME_CHOICES));
        nameComboBox.setToolTipText("enter or choose standard metadata keyword");
        nameComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameComboBoxActionPerformed(evt);
            }
        });
        nameComboBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nameComboBoxKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                nameComboBoxKeyTyped(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nameComboBox, gridBagConstraints);

        nameHelpButton.setText("name help");
        nameHelpButton.setToolTipText("further help on name choices");
        nameHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nameHelpButton, gridBagConstraints);

        nameTooltipLabel.setText("   ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 0, 3);
        add(nameTooltipLabel, gridBagConstraints);

        contentLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        contentLabel.setText("content");
        contentLabel.setToolTipText("value of metadata term");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(7, 3, 3, 3);
        add(contentLabel, gridBagConstraints);

        contentTA.setColumns(20);
        contentTA.setLineWrap(true);
        contentTA.setRows(5);
        contentTA.setWrapStyleWord(true);
        contentTA.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                contentTAPropertyChange(evt);
            }
        });
        contentTA.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                contentTAKeyReleased(evt);
            }
        });
        jScrollPane2.setViewportView(contentTA);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 300;
        gridBagConstraints.ipady = 100;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(jScrollPane2, gridBagConstraints);

        nodeHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nodeHintPanel.setLayout(new java.awt.GridBagLayout());

        hintLabel.setText("<html><p align='center'><b>meta</b> contains a name-value pair of metadata  attributes: <i>name</i> and <i>content</i>.<br > Additional attributes provide direct compatibility matching HTML meta tags.</p>");
        hintLabel.setToolTipText("meta element is similarly used by HTML");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        nodeHintPanel.add(hintLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 8, 3);
        add(nodeHintPanel, gridBagConstraints);

        generatorSelectComboBox.setModel(new DefaultComboBoxModel<>(META_ATTR_GENERATOR_CHOICES));
        generatorSelectComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generatorSelectComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(generatorSelectComboBox, gridBagConstraints);

        todoQuickSelectComboBox.setModel(new DefaultComboBoxModel<>(META_ATTR_TODO_CHOICES));
        todoQuickSelectComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                todoQuickSelectComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(todoQuickSelectComboBox, gridBagConstraints);

        chooserButton.setText("choose file");
        chooserButton.setToolTipText("select a file on local system");
        chooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(chooserButton, gridBagConstraints);

        loadContentButton.setText("load in X3D-Edit");
        loadContentButton.setToolTipText("load content reference in X3D-Edit");
        loadContentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loadContentButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(loadContentButton, gridBagConstraints);

        openContentButton.setText("open in browser");
        openContentButton.setToolTipText("open content reference in Web browser");
        openContentButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openContentButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(openContentButton, gridBagConstraints);

        externalEditorButton.setText("external editor");
        externalEditorButton.setToolTipText("open content reference in external editor");
        externalEditorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                externalEditorButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(externalEditorButton, gridBagConstraints);

        qaButton.setText(NbBundle.getMessage(getClass(), "UrlExpandableList2.qaButton.text")); // NOI18N
        qaButton.setToolTipText(NbBundle.getMessage(getClass(), "UrlExpandableList2.qaButton.toolTipText")); // NOI18N
        qaButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qaButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        add(qaButton, gridBagConstraints);

        todaysDateButton.setText("today's date");
        todaysDateButton.setToolTipText("append today's date");
        todaysDateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                todaysDateButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(todaysDateButton, gridBagConstraints);

        prependHttpButton.setText("prepend http://");
        prependHttpButton.setToolTipText("help complete a url address");
        prependHttpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prependHttpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 20, 3);
        add(prependHttpButton, gridBagConstraints);

        dummyUrlList.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        dummyUrlList.setMinimumSize(new java.awt.Dimension(0, 0));
        dummyUrlList.setPreferredSize(new java.awt.Dimension(0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        add(dummyUrlList, gridBagConstraints);

        clearButton.setText("--");
        clearButton.setToolTipText("Clear content text");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 1);
        add(clearButton, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(6, 3, 6, 3);
        add(jSeparator1, gridBagConstraints);

        dirLabel.setText("dir");
        dirLabel.setToolTipText("Direction for weak/neutral text (ltr=left-to-right, rtl=right-to-left)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dirLabel, gridBagConstraints);

        dirComboBox.setModel(new DefaultComboBoxModel<>(META_ATTR_DIR_CHOICES));
        dirComboBox.setToolTipText("(none), ltr (left-to-right) or rtl (right-to-left)");
        dirComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dirComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dirComboBox, gridBagConstraints);

        dirHelpButton.setText("dir help");
        dirHelpButton.setToolTipText("further help on dir direction values");
        dirHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dirHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dirHelpButton, gridBagConstraints);

        httpEquivalentLabel.setText("http-equiv");
        httpEquivalentLabel.setToolTipText("The http-equiv and content attributes define a response header to an HTTP server.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(httpEquivalentLabel, gridBagConstraints);

        httpEquivalentTextField.setToolTipText("The http-equiv and content attributes define a response header to an HTTP server.");
        httpEquivalentTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                httpEquivalentTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(httpEquivalentTextField, gridBagConstraints);

        httpEquivalentLookupButton.setText("http-equiv lookup");
        httpEquivalentLookupButton.setToolTipText("http-equivalent code lookup online");
        httpEquivalentLookupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                httpEquivalentLookupButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(httpEquivalentLookupButton, gridBagConstraints);

        httpEquivalentHelpButton.setText("http-equiv help");
        httpEquivalentHelpButton.setToolTipText("further help on http-equivalent values");
        httpEquivalentHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                httpEquivalentHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(httpEquivalentHelpButton, gridBagConstraints);

        langLabel.setText("lang");
        langLabel.setToolTipText("Language code, as per [IETF BCP47/RFC5646]");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(langLabel, gridBagConstraints);

        langTextField.setToolTipText("Language code, as per [IETF BCP47/RFC5646]");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(langTextField, gridBagConstraints);

        langLookupButton.setText("language lookup");
        langLookupButton.setToolTipText("Language subtag lookup online");
        langLookupButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                langLookupButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(langLookupButton, gridBagConstraints);

        langHelpButton.setText("lang help");
        langHelpButton.setToolTipText("further help on lang language");
        langHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                langHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(langHelpButton, gridBagConstraints);

        schemeLabel.setText("scheme");
        schemeLabel.setToolTipText("The scheme attribute allows authors to provide user agents more context for the correct interpretation of meta data. For example, <meta scheme=\"ISBN\" name=\"identifier\" content=\"0-8230-2355-9\"> ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(schemeLabel, gridBagConstraints);

        schemeTextField.setToolTipText("The scheme attribute allows authors to provide user agents more context for the correct interpretation of meta data. For example, <meta scheme=\"ISBN\" name=\"identifier\" content=\"0-8230-2355-9\"> ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(schemeTextField, gridBagConstraints);

        schemeHelpButton.setText("scheme help");
        schemeHelpButton.setToolTipText("further help on scheme for this meta name");
        schemeHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                schemeHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(schemeHelpButton, gridBagConstraints);

        domainButton.setText(NbBundle.getMessage(getClass(), "UrlExpandableList2.domainButton.text")); // NOI18N
        domainButton.setToolTipText(NbBundle.getMessage(getClass(), "UrlExpandableList2.domainButton.toolTipText")); // NOI18N
        domainButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                domainButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        add(domainButton, gridBagConstraints);

        pingButton.setText(NbBundle.getMessage(getClass(), "UrlExpandableList2.pingButton.text")); // NOI18N
        pingButton.setToolTipText(NbBundle.getMessage(getClass(), "UrlExpandableList2.pingButton.toolTipText")); // NOI18N
        pingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pingButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(1, 1, 1, 1);
        add(pingButton, gridBagConstraints);

        directionLabel.setText("direction");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(directionLabel, gridBagConstraints);

        prependHttpsButton.setText("prepend https://");
        prependHttpsButton.setToolTipText("help complete a url address");
        prependHttpsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prependHttpsButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(prependHttpsButton, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void enableUrlButtons ()
    {
        boolean isContentUrl        = isContentUrl();
        boolean isContentCompressed = isContentCompressed();
        String  content             = contentTA.getText().trim();
        
//      openContentButton.setEnabled(isContentUrl); // keep enabled to allow web search
        openContentButton.setEnabled(contentTA.getText().length() > 0);
        loadContentButton.setEnabled(isContentUrl && !isContentCompressed);
     externalEditorButton.setEnabled(isContentUrl && !isContentCompressed);        
                 qaButton.setEnabled(isContentUrl && !isContentCompressed && 
                                     urlExpandableList.hasQaTest(content));
             domainButton.setEnabled(isContentUrl && 
                                     urlExpandableList.isOnlineUrl(content));    
               pingButton.setEnabled(isContentUrl && 
                                     urlExpandableList.isOnlineUrl(content));   
               
        if (content.startsWith("http://"))
        {
            prependHttpButton.setEnabled(false);
            prependHttpsButton.setEnabled(true);
        }
        else if (content.startsWith("https://"))
        {
            prependHttpButton.setEnabled(true);
            prependHttpsButton.setEnabled(false);
        }
        else
        {
            prependHttpButton.setEnabled(true);
            prependHttpsButton.setEnabled(true);
        }
    }
    private void nameComboBoxActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_nameComboBoxActionPerformed
    {//GEN-HEADEREND:event_nameComboBoxActionPerformed
        nameHttpEquivalentTextWarningCheck ();
        nameComboBoxTooltipReset ();
        nameHelpButton.setEnabled(!nameHelpReference().isEmpty());
        enableUrlButtons ();
    }//GEN-LAST:event_nameComboBoxActionPerformed
    private void nameComboBoxTooltipReset ()
    {
        // set tooltip matching the predefined category names (no user choices allowed)
        for (int index = 0; index < META_ATTR_NAME_CHOICES.length; index++)
        {
            if (nameComboBox.getSelectedItem().toString().equalsIgnoreCase(META_ATTR_NAME_CHOICES[index])) 
            {
                nameComboBox.setToolTipText(META_ATTR_NAME_CHOICES_TOOLTIPS[index]);
                nameTooltipLabel.setText(   META_ATTR_NAME_CHOICES_TOOLTIPS[index]);
                break;
            }
        }
    }    
    private void todoQuickSelectComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_todoQuickSelectComboBoxActionPerformed
        int    selection      = todoQuickSelectComboBox.getSelectedIndex();
        String selectionProse = todoQuickSelectComboBox.getSelectedItem().toString();
        
        if      (selection == 0) // no selection
        {
            return;
        }
        else if ((selection > 0) && (selection <= META_ATTR_TODO_RESET))
        {
            nameComboBox.setSelectedItem(selectionProse);
            nameComboBox.setToolTipText (META_ATTR_TODO_CHOICES_TOOLTIPS[selection]);
               contentTA.setText        (META_ATTR_TODO_URLS[selection]);
        }
        nameComboBoxTooltipReset ();
        enableUrlButtons ();
    }//GEN-LAST:event_todoQuickSelectComboBoxActionPerformed

    /**
     * strip any text preceding the first url, if needed
     * @param urlString url string to be checked, trimmed
     */
    private String getUrlString (String urlString)
    {
        if      (urlString.contains("http://"))
                 return urlString.substring(urlString.indexOf("http://")); 
        else if (urlString.contains("https://"))
                 return urlString.substring(urlString.indexOf("https://")); 
        else if (urlString.contains("ftp://"))
                 return urlString.substring(urlString.indexOf("ftp://")); 
        else if (urlString.contains("sftp://"))
                 return urlString.substring(urlString.indexOf("sftp://")); 
        else if (urlString.contains("mailto:"))
                 return urlString.substring(urlString.indexOf("mailto:"));
        else if (urlString.contains("../"))
                 return urlString.substring(urlString.indexOf("../")); // relative path, parent directory
        else if (urlString.contains("./"))
                 return urlString.substring(urlString.indexOf("./"));  // relative path, same directory
        else return urlString;
    }
    private void openContentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openContentButtonActionPerformed
        if (contentTA.getText().trim().isEmpty()) return;
        
        String urlString = contentTA.getText().trim();
        
        urlString = getUrlString(urlString); 
        
        // if apparently not a link, conduct search instead
        if (!urlString.contains("://") && !urlString.contains("mailto:") && !urlString.contains("./") && urlString.contains(" ")) // apparently not an address, search instead
             urlString = "https://www.google.com/search?q=" + urlString.replace(" ", "+");
        try 
        {            
            dummyUrlList.launchInBrowser(getUrlString(urlString));
        }
        catch (Exception e)
        {
            System.out.println ("MetaCustomizer: failed attempt to use UrlExpandableList launchInBrowser " + urlString);
            System.out.println (e);
        }
    }//GEN-LAST:event_openContentButtonActionPerformed

    private void contentTAPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_contentTAPropertyChange
        enableUrlButtons ();
    }//GEN-LAST:event_contentTAPropertyChange

    private String nameHelpReference ()
    {
        String nameHelp = nameComboBox.getSelectedItem().toString();
        if       (nameHelp.equals("reference"))
        {
            nameHelp = "#terms-" + "references"; // different DCMI term (but plural doesn't make sense...)
            return META_ATTR_NAME_REFERENCE + nameHelp;
        } 
        else  if (nameHelp.equals("sound")) // capitalization problem
        {
            nameHelp = "#terms-" + "Sound"; // different DCMI term (but plural doesn't make sense...)
            return META_ATTR_NAME_REFERENCE + nameHelp;
        } 
        else if  (nameHelp.equals("drawing") || nameHelp.equals("image") || nameHelp.equals("MovingImage") || nameHelp.equals("photo"))
        {
            // Image is term used in dublin core, but it doesn't seem specific enough
            nameHelp = "#dcmitype-Image"; // different DCMI term
            return META_ATTR_NAME_REFERENCE + nameHelp;
        }
        else if  (nameHelp.equals("error") || nameHelp.equals("generator") || nameHelp.equals("hint") || 
                  nameHelp.equals("info")  || nameHelp.equals("")     || 
                  nameHelp.startsWith("TODO"))
        {
            // not a special entry in dublin core, TODO find or create custom links for help
            return "";
        }
        else if (!nameHelp.isEmpty())
        {
             for (int index = 0; index < META_ATTR_NAME_CHOICES.length; index++)
             {
                if (nameComboBox.getSelectedItem().equals(META_ATTR_NAME_CHOICES[index])) 
                {
                    nameHelp = "#terms-" + nameHelp; // default term
                    return META_ATTR_NAME_REFERENCE + nameHelp;
                }
             }
             // even if no name or no known name found, return some help
             return META_ATTR_NAME_REFERENCE;
        }
        else return META_ATTR_NAME_REFERENCE;
    }
    private void launchUrl (String urlAddress)
    {
        URL url = null;
        try 
        {
            url = new URL (urlAddress);
            URLDisplayer.getDefault().showURL(url);
        }
        catch (MalformedURLException e)
        {
            if (url != null)
                 System.out.println ("Failed attempt to launch url: " + url.toString());
            else System.out.println ("Failed attempt to launch url: <null>");
        }
    }
    private void nameHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nameHelpButtonActionPerformed
        launchUrl (nameHelpReference ());
    }//GEN-LAST:event_nameHelpButtonActionPerformed

    private void contentTAKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_contentTAKeyReleased
        enableUrlButtons ();
    }//GEN-LAST:event_contentTAKeyReleased

    private void prependHttpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prependHttpButtonActionPerformed
        if       (contentTA.getText().trim().startsWith("https://"))
                  contentTA.setText(contentTA.getText().trim().replace("https://", "http://"));
        else if (!contentTA.getText().trim().startsWith("http://"))
                  contentTA.setText("http://" + contentTA.getText().trim());
        contentTA.selectAll(); // open with all text selected
        enableUrlButtons ();
    }//GEN-LAST:event_prependHttpButtonActionPerformed

    private void loadContentButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loadContentButtonActionPerformed
        if (contentTA.getText().trim().isEmpty()) return;
        
        String urlString = contentTA.getText().trim(); // getUrlString ()
        
        try 
        {            
            dummyUrlList.openInX3dEdit(urlString);
        }
        catch (Exception e)
        {
            System.out.println ("MetaCustomizer: failed attempt to use UrlExpandableList openInX3dEdit " + urlString);
            System.out.println (e);
        }
    }//GEN-LAST:event_loadContentButtonActionPerformed

    private void chooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooserButtonActionPerformed
        String urlString = contentTA.getText().trim(); // getUrlString ()
        
        try 
        {            
            dummyUrlList.setAlternateFormatsExpected(true);
            if      (nameComboBox.getSelectedItem().toString().equalsIgnoreCase("drawing") || 
                     nameComboBox.getSelectedItem().toString().equalsIgnoreCase("Image") || 
                     nameComboBox.getSelectedItem().toString().equalsIgnoreCase("photo"))
                     dummyUrlList.setFileChooserImage();
            else if (nameComboBox.getSelectedItem().toString().equalsIgnoreCase("MovingImage"))
                     dummyUrlList.setFileChooserMovie();
            else if (nameComboBox.getSelectedItem().toString().equalsIgnoreCase("Sound"))
                     dummyUrlList.setFileChooserSound();
            String choice = dummyUrlList.showEditDialog(urlString);
            contentTA.setText(choice);
        }
        catch (Exception e)
        {
            System.out.println ("MetaCustomizer: failed attempt to use UrlExpandableList chooser " + urlString);
            System.out.println (e);
        }
        enableUrlButtons ();
    }//GEN-LAST:event_chooserButtonActionPerformed

    private String getTodaysDate ()
    {
        DateTimeGroupStamp dateTimeGroupStamp = new DateTimeGroupStamp(true); // useLocalTimeZone
		String todaysDate = dateTimeGroupStamp.getTodaysDate();
		if (todaysDate.startsWith("0"))
			todaysDate = todaysDate.substring(1); // avoid regex error	looking for leading zeroes	
        return todaysDate;
    }
    private void todaysDateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_todaysDateButtonActionPerformed
        contentTA.setText((contentTA.getText() + " " + getTodaysDate ()).trim());
    }//GEN-LAST:event_todaysDateButtonActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        contentTA.setText("");
    }//GEN-LAST:event_clearButtonActionPerformed

    private void nameComboBoxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nameComboBoxKeyReleased
        nameComboBoxTooltipReset (); // TODO not responding
    }//GEN-LAST:event_nameComboBoxKeyReleased

    private void nameComboBoxKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_nameComboBoxKeyTyped
        nameComboBoxTooltipReset (); // TODO not responding
    }//GEN-LAST:event_nameComboBoxKeyTyped

    private void dirHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dirHelpButtonActionPerformed
        launchUrl (META_ATTR_DIR_REFERENCE);
    }//GEN-LAST:event_dirHelpButtonActionPerformed

    private void httpEquivalentHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_httpEquivalentHelpButtonActionPerformed
        launchUrl (META_ATTR_HTTPEQUIV_REFERENCE);
    }//GEN-LAST:event_httpEquivalentHelpButtonActionPerformed

    private void langHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_langHelpButtonActionPerformed
        launchUrl (META_ATTR_LANG_REFERENCE);
    }//GEN-LAST:event_langHelpButtonActionPerformed

    private void schemeHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_schemeHelpButtonActionPerformed
        launchUrl (META_ATTR_SCHEME_REFERENCE);
    }//GEN-LAST:event_schemeHelpButtonActionPerformed

    private void langLookupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_langLookupButtonActionPerformed
        launchUrl (META_ATTR_LANG_LOOKUP);
    }//GEN-LAST:event_langLookupButtonActionPerformed

    private void dirComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dirComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dirComboBoxActionPerformed
    /**
     * Provide warning dialog, modify background colors, and modify tooltips to alert author not to overload both name and httpequiv attributes at one time.
     */
    private void nameHttpEquivalentTextWarningCheck ()
    {
        String HTTPEQUIVWARNINGMESSAGE = "Illegal to have values for both name and http-equiv attributes at one time";
        if ((normalBackgroundColor == null) || (warningBackgroundColor == null)) // initialize
        {
            normalBackgroundColor = nameComboBox.getBackground();
           warningBackgroundColor = new Color (((float) normalBackgroundColor.getRed()   * 0.85f) / 255.0f, // slight grey - not illegal, just discouraged
                                               ((float) normalBackgroundColor.getGreen() * 0.85f) / 255.0f,
                                               ((float) normalBackgroundColor.getBlue()  * 0.85f) / 255.0f);
        }
        if ((nameComboBox.getSelectedItem().toString().length() > 0) && (httpEquivalentTextField.getText().length() > 0))
        {
            NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                    HTTPEQUIVWARNINGMESSAGE ,NotifyDescriptor.WARNING_MESSAGE);
            DialogDisplayer.getDefault().notify(descriptor);
            
                       nameComboBox.getEditor().getEditorComponent().setBackground(warningBackgroundColor);
            httpEquivalentTextField.setBackground(warningBackgroundColor);
                       nameComboBox.setToolTipText(HTTPEQUIVWARNINGMESSAGE);
            httpEquivalentTextField.setToolTipText(HTTPEQUIVWARNINGMESSAGE);
        }
        else if ((nameComboBox.getSelectedItem().toString().length() > 0))
        {
                       nameComboBox.getEditor().getEditorComponent().setBackground( normalBackgroundColor);
            httpEquivalentTextField.setBackground(warningBackgroundColor);
                       nameComboBox.setToolTipText(nameLabel.getToolTipText());
            httpEquivalentTextField.setToolTipText(HTTPEQUIVWARNINGMESSAGE);
        }
        else if ((httpEquivalentTextField.getText().length() > 0))
        {
                       nameComboBox.getEditor().getEditorComponent().setBackground(warningBackgroundColor);
            httpEquivalentTextField.setBackground( normalBackgroundColor);
                       nameComboBox.setToolTipText(HTTPEQUIVWARNINGMESSAGE);
            httpEquivalentTextField.setToolTipText(httpEquivalentLabel.getToolTipText());
        }
        else // no entries
        {
                       nameComboBox.getEditor().getEditorComponent().setBackground(normalBackgroundColor);
            httpEquivalentTextField.setBackground(normalBackgroundColor);
                       nameComboBox.setToolTipText(nameLabel.getToolTipText());
            httpEquivalentTextField.setToolTipText(httpEquivalentLabel.getToolTipText());
        }
        nameComboBox.repaint(); // TODO background color is not updating..
    }
    private void httpEquivalentTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_httpEquivalentTextFieldActionPerformed
        nameHttpEquivalentTextWarningCheck ();
    }//GEN-LAST:event_httpEquivalentTextFieldActionPerformed

    private void httpEquivalentLookupButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_httpEquivalentLookupButtonActionPerformed
        launchUrl (META_ATTR_HTTPEQUIV_LOOKUP);
    }//GEN-LAST:event_httpEquivalentLookupButtonActionPerformed

    private void qaButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qaButtonActionPerformed
        // similarly implemented in UrlExpandableList2 and METACustomizer, keep these code blocks consistent
        String url = contentTA.getText().trim();
        
        // also skip preceding meta text as appropriate (for example, descriptions preceding urls for name=generator meta tags)
        if      (url.contains("http://"))
                 url = url.substring(url.indexOf("http://"));
        else if (url.contains("https://"))
                 url = url.substring(url.indexOf("https://"));
        else if (url.contains("ftp://"))
                 url = url.substring(url.indexOf("ftp://"));
        else if (url.contains("sftp://"))
                 url = url.substring(url.indexOf("sftp://"));
        
        urlExpandableList.validateUrlContentViaOnlineServer (url);
    }//GEN-LAST:event_qaButtonActionPerformed

    private void domainButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_domainButtonActionPerformed
        // similarly implemented in UrlExpandableList2 and METACustomizer, keep these code blocks consistent
        String url  = contentTA.getText().trim();
        String host = urlExpandableList.getHost(url);
        if (host.length() > 4)
        {
            if (url.contains("https://") || url.contains("sftp://"))  // check https certificate
            {
                urlExpandableList.launchInBrowser("http://www.digicert.com/help?host=" + host);
            }
            urlExpandableList.launchInBrowser("http://centralops.net/co/DomainDossier.aspx?addr=" + host +
                    "&dom_whois=true&dom_dns=true&traceroute=true&net_whois=true&svc_scan=true");
        }
    }//GEN-LAST:event_domainButtonActionPerformed

    private void pingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pingButtonActionPerformed
        // similarly implemented in UrlExpandableList2 and METACustomizer, keep these code blocks consistent
        String host = urlExpandableList.getHost(contentTA.getText().trim());
        if (host.length() > 4)
        urlExpandableList.launchInBrowser("http://centralops.net/co/Ping.aspx?addr=" + host);
    }//GEN-LAST:event_pingButtonActionPerformed

  private void externalEditorButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_externalEditorButtonActionPerformed
        // similarly implemented in UrlExpandableList2 and METACustomizer, keep these code blocks consistent
        String url = contentTA.getText().trim();
        
        // also skip preceding meta text as appropriate (for example, descriptions preceding urls for name=generator meta tags)
        if      (url.contains("http://"))
                 url = url.substring(url.indexOf("http://"));
        else if (url.contains("https://"))
                 url = url.substring(url.indexOf("https://"));
        else if (url.contains("ftp://"))
                 url = url.substring(url.indexOf("ftp://"));
        else if (url.contains("sftp://"))
                 url = url.substring(url.indexOf("sftp://"));
        else if (url.contains("file://"))
                 url = localDirectory + File.separator + url.substring(url.indexOf("file://")+8);
        else if (url.contains("file://"))
                 url = localDirectory + File.separator + url.substring(url.indexOf("file://")+7);
        else     url = localDirectory + File.separator + url;
        
        urlExpandableList.launchExternalEditor (url);
  }//GEN-LAST:event_externalEditorButtonActionPerformed

    private void generatorSelectComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generatorSelectComboBoxActionPerformed
        int    selection      = generatorSelectComboBox.getSelectedIndex();
        String selectionProse = generatorSelectComboBox.getSelectedItem().toString();
        
        if      (selection == 0) // no selection
        {
            return;
        }
        else if ((selection > 0) && (selection <= META_ATTR_GENERATOR_RESET))
        {
            nameComboBox.setSelectedItem("generator");
               contentTA.setText        (META_ATTR_GENERATOR_URLS[selection]);
               contentTA.setToolTipText (META_ATTR_GENERATOR_CHOICES_TOOLTIPS[selection]);
        }
        nameComboBoxTooltipReset ();
        enableUrlButtons ();

    }//GEN-LAST:event_generatorSelectComboBoxActionPerformed

    private void prependHttpsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prependHttpsButtonActionPerformed
        if       (contentTA.getText().trim().startsWith("http://"))
                  contentTA.setText(contentTA.getText().trim().replace("http://", "https://"));
        else if (!contentTA.getText().trim().startsWith("https://"))
                  contentTA.setText("https://" + contentTA.getText().trim());
        contentTA.selectAll(); // open with all text selected
        enableUrlButtons ();
    }//GEN-LAST:event_prependHttpsButtonActionPerformed
    private boolean isContentCompressed ()
    {
        String content = contentTA.getText().trim();
        
        boolean recognizedCompressedFileExtension = 
           (content.endsWith(".x3db")   || content.endsWith(".wrlz") || content.endsWith(".wrz") || 
            content.endsWith(".tar.gz") || content.endsWith(".gz")   || content.endsWith(".tgz") || 
            content.endsWith(".zip")    || content.endsWith(".jar")  || content.endsWith(".7z"));
        return recognizedCompressedFileExtension;
    }
    
    private boolean isContentUrl ()
    {
        String name    = nameComboBox.getSelectedItem().toString();
        String content = contentTA.getText().trim();
        
        boolean recognizedFileExtension = 
           (content.endsWith(".x3d")  || content.endsWith(".x3dv")  || content.endsWith(".wrl")   || 
            content.endsWith(".htm")  || content.endsWith(".html")  || content.endsWith(".svg")   || content.endsWith(".tiff")  ||
            content.endsWith(".png")  || content.endsWith(".jpg")   || content.endsWith(".jpeg")  || content.endsWith(".gif")   ||
            content.endsWith(".pdf")  || content.endsWith(".mp4")   || content.endsWith(".mpg")   || content.endsWith(".mpeg")  || content.endsWith(".txt")   ||
            content.endsWith(".au")   || content.endsWith(".midi")  || content.endsWith(".mid")   || content.endsWith(".mp3")   || content.endsWith(".wav")   ||
            content.endsWith(".js")   || content.endsWith(".xml")   || content.endsWith(".xsl")   || content.endsWith(".xslt")  || 
            content.contains(".asp")  || content.contains(".jsp")   || content.contains(".php")   || content.contains(".cgi")   || content.contains("/cgi-bin") || 
            content.contains(".htm#") || content.contains(".html#") || content.contains(".svg#")  || content.endsWith(".x3d#"));
        
        boolean recognizedDomainExtension = 
           (content.endsWith(".com")  || content.endsWith(".edu")   || content.endsWith(".mil")  || content.endsWith(".net")   || content.endsWith(".org")  || content.endsWith(".gov") ||
            content.contains(".com/") || content.contains(".edu/")  || content.contains(".mil/") || content.contains(".net/")  || content.contains(".org/") || content.contains(".gov/"));
        
        if (!content.contains("://")     && 
            !content.contains("mailto:") && 
            !content.startsWith("../")   && 
            !content.startsWith( "./")   && 
            (content.length() > 6)   && // don't offer to modify fragments until long enough to determine whether http:// is entered
            (recognizedFileExtension || recognizedDomainExtension   || isContentCompressed()   || content.endsWith("/")) &&
//          (content.contains("."))  && // . character is needed for hostname but bad for distinguishing file extensions             
            (name.equals("image")     || name.equals("identifier")  || name.equals("reference") || name.equals("drawing") || 
             name.equals("generator") || name.equals("MovingImage") || name.equals("license")   || name.equals("sound")))
        {
            if (content.startsWith("http://"))
            {
                prependHttpButton.setEnabled(false);
                prependHttpsButton.setEnabled(true);
            }
            else if (content.startsWith("https://"))
            {
                prependHttpButton.setEnabled(true);
                prependHttpsButton.setEnabled(false);
            }
            else
            {
                prependHttpButton.setEnabled(true);
                prependHttpsButton.setEnabled(true);
            }
        }
        if ((content.length() > 4)   && // don't offer to launch fragments until long enough to determine whether http:// (or a.txt etc.) is entered 
            (content.contains("mailto:") || 
             content.contains("://") ||
             content.contains("../") ||
             content.contains("./")  ||
             content.endsWith("/")   ||
             recognizedFileExtension || recognizedDomainExtension   || isContentCompressed ()))
             return true;
        else return false;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton chooserButton;
    private javax.swing.JButton clearButton;
    private javax.swing.JLabel contentLabel;
    private javax.swing.JTextArea contentTA;
    private javax.swing.JComboBox<String> dirComboBox;
    private javax.swing.JButton dirHelpButton;
    private javax.swing.JLabel dirLabel;
    private javax.swing.JLabel directionLabel;
    private javax.swing.JButton domainButton;
    private org.web3d.x3d.palette.items.UrlExpandableList2 dummyUrlList;
    private javax.swing.JButton externalEditorButton;
    private javax.swing.JComboBox<String> generatorSelectComboBox;
    private javax.swing.JLabel hintLabel;
    private javax.swing.JButton httpEquivalentHelpButton;
    private javax.swing.JLabel httpEquivalentLabel;
    private javax.swing.JButton httpEquivalentLookupButton;
    private javax.swing.JTextField httpEquivalentTextField;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JButton langHelpButton;
    private javax.swing.JLabel langLabel;
    private javax.swing.JButton langLookupButton;
    private javax.swing.JTextField langTextField;
    private javax.swing.JButton loadContentButton;
    private javax.swing.JComboBox<String> nameComboBox;
    private javax.swing.JButton nameHelpButton;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JLabel nameTooltipLabel;
    private javax.swing.JPanel nodeHintPanel;
    private javax.swing.JButton openContentButton;
    private javax.swing.JButton pingButton;
    private javax.swing.JButton prependHttpButton;
    private javax.swing.JButton prependHttpsButton;
    private javax.swing.JButton qaButton;
    private javax.swing.JButton schemeHelpButton;
    private javax.swing.JLabel schemeLabel;
    private javax.swing.JTextField schemeTextField;
    private javax.swing.JButton todaysDateButton;
    private javax.swing.JComboBox<String> todoQuickSelectComboBox;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_META";
  }

  @Override
  public void unloadInput()
  {
      if (isContentUrl()) // escape url results or else XML validation might fail due to & characters
      {
          contentTA.setText(UrlExpandableList2.escapeUrl(contentTA.getText()));
      }
              
     nameHttpEquivalentTextWarningCheck ();
     meta.setName(((String)nameComboBox.getSelectedItem()).trim());
     meta.setContentAttribute(contentTA.getText().trim());
     if (((String)dirComboBox.getSelectedItem()).equals("LTR") ||
         ((String)dirComboBox.getSelectedItem()).equals("RTL"))
         meta.setDir(((String)dirComboBox.getSelectedItem()).trim()); // only pass legal values
     meta.setHttpEquivalent(httpEquivalentTextField.getText().trim());
     meta.setLang(langTextField.getText().trim());
     meta.setScheme(schemeTextField.getText().trim());
  }  

    /**
     * @return the generatorValueX3dEdit
     */
    public String getGeneratorValueX3dEdit() {
        return generatorValueX3dEdit;
    }
}
