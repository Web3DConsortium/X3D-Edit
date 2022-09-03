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

package org.web3d.x3d.actions.conversions;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JFileChooser;
import org.openide.util.HelpCtx;
import org.web3d.x3d.actions.LaunchX3dExamplesAction;
import org.web3d.x3d.actions.LaunchX3dSceneAuthoringHintsCorsAction;

/**
 *
 * @author brutzman
 */

public class X3dToXhtmlDomConversionPanel extends javax.swing.JPanel {

    private final XhtmlX3domAction xhtmlX3domAction;
    
    private final String X3DOM_name = "X3DOM";
    private final String X_ITE_name = "X_ITE";
    
    private JFileChooser fileChooser;
    
    /**
     * Creates new form X3dToXhtmlDomInitializationPanel
     */
    public X3dToXhtmlDomConversionPanel(XhtmlX3domAction xhtmlX3domAction) {
        
        this.xhtmlX3domAction = xhtmlX3domAction;
        
        initComponents();
                   
        HelpCtx.setHelpIDString(this, "X3D-Edit.Features");
        
//         html5Image94x120Label.setIcon(new ImageIcon(ImageUtilities.loadImage("org/web3d/x3d/resources/HTML5_logo_and_wordmark.svg94x120.png")));
//           x3dImage100Label.setIcon(new ImageIcon(ImageUtilities.loadImage("org/web3d/x3d/resources/x3d100x100.png")));
//         x3domLabel.setIcon(new ImageIcon(ImageUtilities.loadImage("org/web3d/x3d/resources/x3dom-whiteOnblue160.png")));
//         x_iteLabel.setIcon(new ImageIcon(ImageUtilities.loadImage("org/web3d/x3d/resources/cobweb-logo160.png")));
        
        loadValuesInPanel (); // must follow componenent intialization
        
        // used in parent XhtmlX3domAction and X3dToXhtmlDomConversionPanel
        // TODO lookup x3dEditor to get correct directory
//        X3DDataObject dob = (X3DDataObject)x3dEditor.getX3dEditorSupport().getDataObject();
//        String fileName = dob.getPrimaryFile().getNameExt();
//        String filePath = dob.getPrimaryFile().getPath();
// maybe  String filePath = xhtmlX3domAction...

//        rootDirectoryTextField.setText(filePath);
        
//        if (cobwebRadioButton.isSelected()) 
//        {                                                  
//             xhtmlX3domAction.setPlayer("Cobweb");
//        }
//        else xhtmlX3domAction.setPlayer(X3DOM_name);
        
        if (xhtmlX3domAction.getPlayer().equalsIgnoreCase("Cobweb") || xhtmlX3domAction.getPlayer().equalsIgnoreCase(X_ITE_name))
        {
             x_iteRadioButton.setSelected(true);
        }
        else x3domRadioButton.setSelected(true);
		
		urlList.setFileChooserX3d();
    }
    
    protected final void setPlayerSelection (String playerName)
    {
        if (playerName.equalsIgnoreCase("Cobweb") || playerName.equalsIgnoreCase(X_ITE_name))
        {
             x_iteRadioButton.setSelected(true);
        }
        else  x3domRadioButton.setSelected(true);
    }
    
    protected final void saveUrlValues ()
    {
        String urlComplete = "";

        for (int i = 0; i < urlList.getUrlData().length; i++)
        {
            if (!urlList.getUrlData()[i].contains("\""))
                 urlComplete += "\"" + urlList.getUrlData()[i] + "\" "; // note trailing space in case of array length > 1
            else urlComplete +=        urlList.getUrlData()[i];
        }
        urlComplete = urlComplete.trim();
        xhtmlX3domAction.setUrlScene(urlComplete);
    }
    
    protected final void loadValuesInPanel ()
    {
                  widthTextField.setText(xhtmlX3domAction.getX3dWidth());
                 heightTextField.setText(xhtmlX3domAction.getX3dHeight());
                 showLogCheckBox.setSelected(xhtmlX3domAction.isShowLog());
            showProgressCheckBox.setSelected(xhtmlX3domAction.isShowProgress());
          showStatisticsCheckBox.setSelected(xhtmlX3domAction.isShowStatistics());
        primitiveQualityComboBox.setSelectedItem(xhtmlX3domAction.getPrimitiveQuality());
                   cacheCheckBox.setSelected(xhtmlX3domAction.isCache());
                         urlList.setUrlData(xhtmlX3domAction.getUrlScene());
        // TODO url
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        playerButtonGroup = new javax.swing.ButtonGroup();
        html5Image94x120Label = new javax.swing.JLabel();
        x3dImage100Label = new javax.swing.JLabel();
        widthLabel = new javax.swing.JLabel();
        widthDescriptionLabel = new javax.swing.JLabel();
        widthTextField = new javax.swing.JTextField();
        heightLabel = new javax.swing.JLabel();
        heightDescriptionLabel = new javax.swing.JLabel();
        heightTextField = new javax.swing.JTextField();
        corsDescriptionLabel = new javax.swing.JLabel();
        corsHelpButton = new javax.swing.JButton();
        portLabel = new javax.swing.JLabel();
        portTextField = new javax.swing.JTextField();
        localHttpServerLabel = new javax.swing.JLabel();
        rootDirectoryLabel = new javax.swing.JLabel();
        rootDirectoryTextField = new javax.swing.JTextField();
        rootDirectoryChooserButton = new javax.swing.JButton();
        javaHttpServerButton = new javax.swing.JButton();
        jettyButton = new javax.swing.JButton();
        pythonConsoleButton = new javax.swing.JButton();
        javaStopButton = new javax.swing.JButton();
        jettyStopButton = new javax.swing.JButton();
        pythonStopButton = new javax.swing.JButton();
        warningLabel = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        x3domLabel = new javax.swing.JLabel();
        x3domRadioButton = new javax.swing.JRadioButton();
        showLogCheckBox = new javax.swing.JCheckBox();
        showLogLabel = new javax.swing.JLabel();
        showStatisticsCheckBox = new javax.swing.JCheckBox();
        showStatLabel = new javax.swing.JLabel();
        showProgressCheckBox = new javax.swing.JCheckBox();
        showProgressLabel = new javax.swing.JLabel();
        PrimitiveQualityLabel = new javax.swing.JLabel();
        primitiveQualityComboBox = new javax.swing.JComboBox();
        PrimitiveQualityDescriptionLabel = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        x_iteLabel = new javax.swing.JLabel();
        x_iteRadioButton = new javax.swing.JRadioButton();
        cacheCheckBox = new javax.swing.JCheckBox();
        cacheDescriptionLabel = new javax.swing.JLabel();
        urlLabel = new javax.swing.JLabel();
        urlList = new org.web3d.x3d.palette.items.UrlExpandableList2();

        setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.border.title"))); // NOI18N
        setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(html5Image94x120Label, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.html5Image94x120Label.text")); // NOI18N
        html5Image94x120Label.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.html5Image94x120Label.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 3, 0);
        add(html5Image94x120Label, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(x3dImage100Label, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.x3dImage100Label.text")); // NOI18N
        x3dImage100Label.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.x3dImage100Label.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 1, 3, 3);
        add(x3dImage100Label, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(widthLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.widthLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(widthLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(widthDescriptionLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.widthDescriptionLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 1, 3, 3);
        add(widthDescriptionLabel, gridBagConstraints);

        widthTextField.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        widthTextField.setText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.widthTextField.text")); // NOI18N
        widthTextField.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.widthTextField.toolTipText")); // NOI18N
        widthTextField.setMaximumSize(new java.awt.Dimension(60, 22));
        widthTextField.setMinimumSize(new java.awt.Dimension(20, 22));
        widthTextField.setPreferredSize(new java.awt.Dimension(20, 22));
        widthTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                widthTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 4, 3, 3);
        add(widthTextField, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(heightLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.heightLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(heightLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(heightDescriptionLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.heightDescriptionLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 1, 3, 3);
        add(heightDescriptionLabel, gridBagConstraints);

        heightTextField.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        heightTextField.setText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.heightTextField.text")); // NOI18N
        heightTextField.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.heightTextField.toolTipText")); // NOI18N
        heightTextField.setMaximumSize(new java.awt.Dimension(60, 22));
        heightTextField.setMinimumSize(new java.awt.Dimension(20, 22));
        heightTextField.setPreferredSize(new java.awt.Dimension(20, 22));
        heightTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                heightTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 4, 3, 3);
        add(heightTextField, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(corsDescriptionLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.corsDescriptionLabel.text")); // NOI18N
        corsDescriptionLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.corsDescriptionLabel.toolTipText")); // NOI18N
        corsDescriptionLabel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        corsDescriptionLabel.setMaximumSize(new java.awt.Dimension(250, 48));
        corsDescriptionLabel.setMinimumSize(new java.awt.Dimension(116, 32));
        corsDescriptionLabel.setPreferredSize(new java.awt.Dimension(116, 32));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(corsDescriptionLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(corsHelpButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.corsHelpButton.text")); // NOI18N
        corsHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                corsHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(corsHelpButton, gridBagConstraints);

        portLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(portLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.portLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(portLabel, gridBagConstraints);

        portTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        portTextField.setText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.portTextField.text")); // NOI18N
        portTextField.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.portTextField.toolTipText")); // NOI18N
        portTextField.setMaximumSize(new java.awt.Dimension(60, 22));
        portTextField.setMinimumSize(new java.awt.Dimension(20, 22));
        portTextField.setPreferredSize(new java.awt.Dimension(20, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 24;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(portTextField, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(localHttpServerLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.localHttpServerLabel.text")); // NOI18N
        localHttpServerLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.localHttpServerLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 3, 3);
        add(localHttpServerLabel, gridBagConstraints);

        rootDirectoryLabel.setForeground(new java.awt.Color(255, 102, 0));
        org.openide.awt.Mnemonics.setLocalizedText(rootDirectoryLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.rootDirectoryLabel.text")); // NOI18N
        rootDirectoryLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.rootDirectoryLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(rootDirectoryLabel, gridBagConstraints);

        rootDirectoryTextField.setText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.rootDirectoryTextField.text")); // NOI18N
        rootDirectoryTextField.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.rootDirectoryTextField.toolTipText")); // NOI18N
        rootDirectoryTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rootDirectoryTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(rootDirectoryTextField, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(rootDirectoryChooserButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.rootDirectoryChooserButton.text")); // NOI18N
        rootDirectoryChooserButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.rootDirectoryChooserButton.toolTipText")); // NOI18N
        rootDirectoryChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rootDirectoryChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(rootDirectoryChooserButton, gridBagConstraints);

        javaHttpServerButton.setForeground(new java.awt.Color(255, 102, 0));
        org.openide.awt.Mnemonics.setLocalizedText(javaHttpServerButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.javaHttpServerButton.text")); // NOI18N
        javaHttpServerButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.javaHttpServerButton.toolTipText")); // NOI18N
        javaHttpServerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                javaHttpServerButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 3, 3);
        add(javaHttpServerButton, gridBagConstraints);

        jettyButton.setForeground(new java.awt.Color(255, 102, 0));
        org.openide.awt.Mnemonics.setLocalizedText(jettyButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.jettyButton.text")); // NOI18N
        jettyButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.jettyButton.toolTipText")); // NOI18N
        jettyButton.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 3, 3);
        add(jettyButton, gridBagConstraints);

        pythonConsoleButton.setForeground(new java.awt.Color(255, 102, 0));
        org.openide.awt.Mnemonics.setLocalizedText(pythonConsoleButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.pythonConsoleButton.text")); // NOI18N
        pythonConsoleButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.pythonConsoleButton.toolTipText")); // NOI18N
        pythonConsoleButton.setActionCommand(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.pythonConsoleButton.actionCommand")); // NOI18N
        pythonConsoleButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pythonConsoleButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 3, 3);
        add(pythonConsoleButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(javaStopButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.javaStopButton.text")); // NOI18N
        javaStopButton.setEnabled(false);
        javaStopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                javaStopButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(javaStopButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(jettyStopButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.jettyStopButton.text")); // NOI18N
        jettyStopButton.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(jettyStopButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(pythonStopButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.pythonStopButton.text")); // NOI18N
        pythonStopButton.setEnabled(false);
        pythonStopButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pythonStopButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(pythonStopButton, gridBagConstraints);

        warningLabel.setForeground(new java.awt.Color(255, 102, 0));
        org.openide.awt.Mnemonics.setLocalizedText(warningLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.warningLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 3, 3);
        add(warningLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
        add(jSeparator1, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(x3domLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.x3domLabel.text")); // NOI18N
        x3domLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.x3domLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(x3domLabel, gridBagConstraints);

        playerButtonGroup.add(x3domRadioButton);
        x3domRadioButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        x3domRadioButton.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(x3domRadioButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.x3domRadioButton.text")); // NOI18N
        x3domRadioButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.x3domRadioButton.toolTipText")); // NOI18N
        x3domRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                x3domRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(x3domRadioButton, gridBagConstraints);

        showLogCheckBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(showLogCheckBox, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.showLogCheckBox.text")); // NOI18N
        showLogCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        showLogCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showLogCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        add(showLogCheckBox, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(showLogLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.showLogLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(showLogLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(showStatisticsCheckBox, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.showStatisticsCheckBox.text")); // NOI18N
        showStatisticsCheckBox.setActionCommand(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.showStatisticsCheckBox.actionCommand")); // NOI18N
        showStatisticsCheckBox.setDoubleBuffered(true);
        showStatisticsCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        showStatisticsCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showStatisticsCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        add(showStatisticsCheckBox, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(showStatLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.showStatLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(showStatLabel, gridBagConstraints);

        showProgressCheckBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(showProgressCheckBox, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.showProgressCheckBox.text")); // NOI18N
        showProgressCheckBox.setActionCommand(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.showProgressCheckBox.actionCommand")); // NOI18N
        showProgressCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        showProgressCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showProgressCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        add(showProgressCheckBox, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(showProgressLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.showProgressLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(showProgressLabel, gridBagConstraints);

        PrimitiveQualityLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(PrimitiveQualityLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.PrimitiveQualityLabel.text")); // NOI18N
        PrimitiveQualityLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.PrimitiveQualityLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(PrimitiveQualityLabel, gridBagConstraints);

        primitiveQualityComboBox.setEditable(true);
        primitiveQualityComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "High", "Medium", "Low", "float" }));
        primitiveQualityComboBox.setMaximumSize(new java.awt.Dimension(60, 22));
        primitiveQualityComboBox.setMinimumSize(new java.awt.Dimension(20, 22));
        primitiveQualityComboBox.setPreferredSize(new java.awt.Dimension(20, 22));
        primitiveQualityComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                primitiveQualityComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 4, 3, 3);
        add(primitiveQualityComboBox, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(PrimitiveQualityDescriptionLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.PrimitiveQualityDescriptionLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(PrimitiveQualityDescriptionLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
        add(jSeparator2, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(x_iteLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.x_iteLabel.text")); // NOI18N
        x_iteLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.x_iteLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(x_iteLabel, gridBagConstraints);

        playerButtonGroup.add(x_iteRadioButton);
        x_iteRadioButton.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(x_iteRadioButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.x_iteRadioButton.text")); // NOI18N
        x_iteRadioButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.x_iteRadioButton.toolTipText")); // NOI18N
        x_iteRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                x_iteRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(x_iteRadioButton, gridBagConstraints);

        cacheCheckBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(cacheCheckBox, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.cacheCheckBox.text")); // NOI18N
        cacheCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.cacheCheckBox.toolTipText")); // NOI18N
        cacheCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cacheCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        add(cacheCheckBox, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(cacheDescriptionLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.cacheDescriptionLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(cacheDescriptionLabel, gridBagConstraints);

        urlLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(urlLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.urlLabel.text")); // NOI18N
        urlLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.urlLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 0, 3);
        add(urlLabel, gridBagConstraints);

        urlList.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        urlList.setMinimumSize(new java.awt.Dimension(600, 120));
        urlList.setPreferredSize(new java.awt.Dimension(620, 180));
        urlList.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                urlListPropertyChange(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(urlList, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    
    private void showStatisticsCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showStatisticsCheckBoxActionPerformed
        xhtmlX3domAction.setShowStatistics(showStatisticsCheckBox.isSelected());
    }//GEN-LAST:event_showStatisticsCheckBoxActionPerformed

    private void primitiveQualityComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_primitiveQualityComboBoxActionPerformed
        xhtmlX3domAction.setPrimitiveQuality( primitiveQualityComboBox.getSelectedItem().toString());
    }//GEN-LAST:event_primitiveQualityComboBoxActionPerformed

    private void showProgressCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showProgressCheckBoxActionPerformed
        xhtmlX3domAction.setShowProgress(showProgressCheckBox.isSelected());
    }//GEN-LAST:event_showProgressCheckBoxActionPerformed

    private void showLogCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showLogCheckBoxActionPerformed
        xhtmlX3domAction.setShowLog(showLogCheckBox.isSelected());
    }//GEN-LAST:event_showLogCheckBoxActionPerformed

    private void widthTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_widthTextFieldActionPerformed
        xhtmlX3domAction.setX3dWidth(widthTextField.getText());
    }//GEN-LAST:event_widthTextFieldActionPerformed

    private void heightTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_heightTextFieldActionPerformed
        xhtmlX3domAction.setX3dHeight(heightTextField.getText());
    }//GEN-LAST:event_heightTextFieldActionPerformed

    private void urlListPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_urlListPropertyChange

    }//GEN-LAST:event_urlListPropertyChange

    private void x_iteRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_x_iteRadioButtonActionPerformed
        xhtmlX3domAction.setPlayer(X_ITE_name);
    }//GEN-LAST:event_x_iteRadioButtonActionPerformed

    private void cacheCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cacheCheckBoxActionPerformed
        xhtmlX3domAction.setCache(cacheCheckBox.isSelected());
    }//GEN-LAST:event_cacheCheckBoxActionPerformed

    private void x3domRadioButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_x3domRadioButtonActionPerformed
        xhtmlX3domAction.setPlayer(X3DOM_name);
    }//GEN-LAST:event_x3domRadioButtonActionPerformed

    private void corsHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_corsHelpButtonActionPerformed
        LaunchX3dSceneAuthoringHintsCorsAction launchCorsAction = new LaunchX3dSceneAuthoringHintsCorsAction();
        launchCorsAction.performAction();
    }//GEN-LAST:event_corsHelpButtonActionPerformed

    Process pythonHttpProcess;
    
    private void pythonConsoleButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pythonConsoleButtonActionPerformed
       // https://docs.oracle.com/en/java/javase/16/docs/api/java.base/java/io/Console.html
//        java.io.Console pythonSystemConsole;
//        if ((pythonSystemConsole = System.console()) != null)
//             pythonSystemConsole.printf("%s", "Hello python system console");
//        else System.err.println ("*** failure to launch system console");

    // common code
        int             portValue = Integer.parseInt(portTextField.getText());
        String modelRootDirectory = rootDirectoryTextField.getText().replaceAll("\\\\","/"); // double escaping for Java character and regex literal;
    // common code
        
        try
        {
            URI uri = new URI(modelRootDirectory);
            String modelPath = uri.getPath();
            
            String localUrl = "http://localhost:" + portValue + "/" + modelPath;
            localUrl = localUrl.replaceAll("\\\\","/"); // double escaping for Java character and regex literal
            
            if (pythonHttpProcess != null)
                stopPythonHttpProcess();
            
            // initial code Rick Lentz
            // TODO how to capture/share process console output
            File modelDirectory = new File(modelPath); // modelRootDirectory);
            Runtime runtime = Runtime.getRuntime();
            // note ability to define launching directory
            pythonHttpProcess = runtime.exec("python -m http.server " + portValue , new String[0], modelDirectory);
            pythonStopButton.setEnabled(true);
            
			System.out.println("*** CORS console launched: python -m http.server " + portValue + " in " + modelPath);
            LaunchX3dExamplesAction.sendBrowserTo("/"); // which goes to modelPath);
			System.out.println("*** launch default browser to " + modelPath);
			System.out.flush();
        }
        catch (URISyntaxException urise)
        {
            System.err.println ("*** pythonConsoleButtonActionPerformed() URISyntaxException " + urise);
			urise.printStackTrace();
        }
        catch (IOException ioe)
        {
            System.err.println ("*** pythonConsoleButtonActionPerformed() IOException " + ioe);
			ioe.printStackTrace();
        }
    }//GEN-LAST:event_pythonConsoleButtonActionPerformed

    private void rootDirectoryChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rootDirectoryChooserButtonActionPerformed
    if (fileChooser == null) {
        fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        fileChooser.setDialogTitle("Select model directory tree");
        fileChooser.setToolTipText("CORS provides local http visibility to these files");
        String modelRootDirectory = rootDirectoryTextField.getText().replaceAll("\\\\","/"); // double escaping for Java character and regex literal;
        fileChooser.setCurrentDirectory(new File(modelRootDirectory));
        fileChooser.setMultiSelectionEnabled(false);
    }
    
    int returnValue = fileChooser.showOpenDialog(this);
    if  (returnValue != JFileChooser.APPROVE_OPTION)
         return; // no action
    else rootDirectoryTextField.setText(fileChooser.getSelectedFile().getAbsolutePath()); // remember
    // TODO global setting in X3DOptions?
    }//GEN-LAST:event_rootDirectoryChooserButtonActionPerformed

    /**
     * @link https://dzone.com/articles/simple-http-server-in-java
     */
class LocalFileHandler implements HttpHandler {

	@Override    
 	public void handle(HttpExchange httpExchange) throws IOException {

    	String requestParamValue=null; 
    	if("GET".equals(httpExchange.getRequestMethod())) 
    	{ 
    		requestParamValue = handleGetRequest(httpExchange);
    	}
    	handleResponse(httpExchange,requestParamValue); 
	}

	private String handleGetRequest(HttpExchange httpExchange) 
	{
		String decoded = null;
		try 
        {
			decoded = URLDecoder.decode(httpExchange.getRequestURI().toString().substring(1), "UTF-8");
		} 
        catch (UnsupportedEncodingException e) 
        {
			e.printStackTrace();
		}
	    return decoded;
	}

	private void handleResponse(HttpExchange httpExchange, String requestParamValue)  throws  IOException {
		OutputStream outputStream = httpExchange.getResponseBody();

		byte[] content = Files.readAllBytes(Paths.get(requestParamValue));

		
		httpExchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
      	httpExchange.sendResponseHeaders(200, content.length);
		outputStream.write(content);
		outputStream.flush();
		outputStream.close();
    }
}
    /* avoid potential Netbeans/X3D-Edit error when using null (default) executor in same thread
    * https://docs.oracle.com/en/java/javase/16/docs/api/java.base/java/util/concurrent/Executor.html
    */
    class ThreadPerTaskExecutor implements java.util.concurrent.Executor {
      @Override
      public void execute(Runnable r) {
        new Thread(r).start();
      }
    }

    HttpServer httpServer;
    final String DEFAULT_MODEL_ROOT_DIRECTORY = "local directory tree for model visibility";
    
    private void javaHttpServerClose()
    {
        if (httpServer != null)
        {            
            httpServer.stop(4); // waits, closes, kills thread, exits
            System.out.println("*** CORS httpServer stopped");
            System.out.flush();
            javaStopButton.setEnabled(false);
            httpServer = null; // forcibly destroy
        }
    }
    
    /**
     * Launch http server on local host
     * @link https://docs.oracle.com/en/java/javase/16/docs/api/jdk.httpserver/com/sun/net/httpserver/package-summary.html
     * @link https://dzone.com/articles/simple-http-server-in-java
     * @param evt triggering input event from callback
     */
    private void javaHttpServerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_javaHttpServerButtonActionPerformed
        // Would spinning off a JAVA process that references the local dir using the below work?
        // ref: https://dzone.com/articles/simple-http-server-in-java

        int             portValue = Integer.parseInt(portTextField.getText());
        String modelRootDirectory = rootDirectoryTextField.getText().replaceAll("\\\\","/"); // double escaping for Java character and regex literal;
        
        // put safety/security checks here, e.g start and end with "/"
        // https://docs.oracle.com/en/java/javase/16/docs/api/jdk.httpserver/com/sun/net/httpserver/HttpServer.html#createContext(java.lang.String,com.sun.net.httpserver.HttpHandler)
        
        if (modelRootDirectory.equals("\\"))
        {
            // TODO error handling, starting/ending slashes, etc. probably confirm by checking it is a valid URI instance
        }
            
        String localUrl = "http://localhost:" + portValue + modelRootDirectory;
        localUrl = localUrl.replaceAll("\\\\","/"); // double escaping for Java character and regex literal
        
        try
        {
            if (httpServer != null) // created previously
            {
                javaHttpServerClose();
            }
            httpServer = HttpServer.create((new InetSocketAddress("localhost", portValue)), 0);
            
			// INFO [org.netbeans.api.java.source.ElementHandle]: Cannot resolve: ElementHandle[kind=METHOD; sigs=com.sun.net.httpserver.HttpServer createContext (Ljava/lang/String;)Lcom/sun/net/httpserver/HttpContext; ]
			httpServer.createContext(modelRootDirectory, new LocalFileHandler() );
            // https://docs.oracle.com/en/java/javase/16/docs/api/jdk.httpserver/com/sun/net/httpserver/HttpServer.html#setExecutor(java.util.concurrent.Executor)
			
            ThreadPerTaskExecutor httpServerExecutor = new ThreadPerTaskExecutor();
            httpServer.setExecutor(httpServerExecutor); // null means default implementation; TODO eliminate potential problem
            
            httpServer.start();
			System.out.println("*** CORS httpServer started");
            javaStopButton.setEnabled(true);
            
            LaunchX3dExamplesAction.sendBrowserTo(localUrl);
			System.out.println("*** launch default browser to " + localUrl);
			System.out.flush();
        }
        catch (IOException ioe)
        {
            System.err.println ("*** javaHttpServerButtonActionPerformed() exception " + ioe);
			ioe.printStackTrace();
        }
        // http://localhost:8080
        // TODO what about restricting http server to specific directory tree?
        // TODO what about launching browser in initial directory
        // TODO what about ability to kill process?
        // TODO what about overall session timeout?
    }//GEN-LAST:event_javaHttpServerButtonActionPerformed

    private void rootDirectoryTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rootDirectoryTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rootDirectoryTextFieldActionPerformed

    private void stopPythonHttpProcess ()
    {
        // TODO also invoke this method on application exit
        pythonHttpProcess.destroy();
        pythonStopButton.setEnabled(false);
        System.out.println("*** stop (destroy) python process");
        pythonHttpProcess = null;
    }
    private void pythonStopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pythonStopButtonActionPerformed
        stopPythonHttpProcess ();
    }//GEN-LAST:event_pythonStopButtonActionPerformed

    private void javaStopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_javaStopButtonActionPerformed
        javaHttpServerClose();
    }//GEN-LAST:event_javaStopButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel PrimitiveQualityDescriptionLabel;
    private javax.swing.JLabel PrimitiveQualityLabel;
    private javax.swing.JCheckBox cacheCheckBox;
    private javax.swing.JLabel cacheDescriptionLabel;
    private javax.swing.JLabel corsDescriptionLabel;
    private javax.swing.JButton corsHelpButton;
    private javax.swing.JLabel heightDescriptionLabel;
    private javax.swing.JLabel heightLabel;
    private javax.swing.JTextField heightTextField;
    private javax.swing.JLabel html5Image94x120Label;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JButton javaHttpServerButton;
    private javax.swing.JButton javaStopButton;
    private javax.swing.JButton jettyButton;
    private javax.swing.JButton jettyStopButton;
    private javax.swing.JLabel localHttpServerLabel;
    private javax.swing.ButtonGroup playerButtonGroup;
    private javax.swing.JLabel portLabel;
    private javax.swing.JTextField portTextField;
    private javax.swing.JComboBox primitiveQualityComboBox;
    private javax.swing.JButton pythonConsoleButton;
    private javax.swing.JButton pythonStopButton;
    private javax.swing.JButton rootDirectoryChooserButton;
    private javax.swing.JLabel rootDirectoryLabel;
    private javax.swing.JTextField rootDirectoryTextField;
    private javax.swing.JCheckBox showLogCheckBox;
    private javax.swing.JLabel showLogLabel;
    private javax.swing.JCheckBox showProgressCheckBox;
    private javax.swing.JLabel showProgressLabel;
    private javax.swing.JLabel showStatLabel;
    private javax.swing.JCheckBox showStatisticsCheckBox;
    private javax.swing.JLabel urlLabel;
    private org.web3d.x3d.palette.items.UrlExpandableList2 urlList;
    private javax.swing.JLabel warningLabel;
    private javax.swing.JLabel widthDescriptionLabel;
    private javax.swing.JLabel widthLabel;
    private javax.swing.JTextField widthTextField;
    private javax.swing.JLabel x3dImage100Label;
    private javax.swing.JLabel x3domLabel;
    private javax.swing.JRadioButton x3domRadioButton;
    private javax.swing.JLabel x_iteLabel;
    private javax.swing.JRadioButton x_iteRadioButton;
    // End of variables declaration//GEN-END:variables

}
