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

package org.web3d.x3d.actions.conversions;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JFileChooser;
import org.openide.util.HelpCtx;
import static org.web3d.x3d.actions.BaseViewAction.X3D4_HTML_AUTHORING_GUIDELINES;
import org.web3d.x3d.actions.LaunchX3dExamplesAction;
import org.web3d.x3d.actions.LaunchX3dSceneAuthoringHintsCorsAction;
import static org.web3d.x3d.actions.conversions.ConversionsHelper.openInBrowser;
import org.web3d.x3d.options.X3dOptions;
import static org.web3d.x3d.options.X3dOptions.AUTHOR_DESIGNATED_CORS_DIRECTORY_DEFAULT;

/**
 *
 * @author brutzman
 */

public class X3dToXhtmlDomConversionPanel extends javax.swing.JPanel {

    private final XhtmlX3domAction xhtmlX3domAction;
    
    private final String X3DOM_name = "X3DOM";
    public  final String X3DOM_site = "https://www.x3dom.org";
    public  final String X3DOM_help = "https://www.x3dom.org/examples";
    
    private final String X_ITE_name = "X_ITE";
    public  final String X_ITE_site = "https://create3000.github.io/x_ite";
    public  final String X_ITE_help = "https://create3000.github.io/x_ite/tutorials";
    
    public static final int NO_CHANGE_IN_TAB = -1;
    public static final int  HTML_LAYOUT_TAB =  0;
    public static final int        X3DOM_TAB =  1;
    public static final int        X_ITE_TAB =  2;
    public static final int         CORS_TAB =  3;
    
    private             String          authorCorsDirectory;
    // LOCAL_EXAMPLES_ROOT is default in X3dOptions.AUTHOR_CORS_DIRECTORY_CHOICE_DEFAULT
    public static final String          LOCAL_EXAMPLES_ROOT = "LOCAL_EXAMPLES_ROOT";
    public static final String         DESIGNATED_DIRECTORY = "DESIGNATED_DIRECTORY";
    public static final String  CURRENT_X3D_MODEL_DIRECTORY = "CURRENT_X3D_MODEL_DIRECTORY";
        
    private JFileChooser corsDirectoryChooser;
    
    /**
     * Creates new form X3dToXhtmlDomConversionPanel
     * @param xhtmlX3domAction action class of interest
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
            pageIntegrationTabbedPane.setSelectedIndex(X_ITE_TAB);
            
//             x_iteRadioButton.setSelected(true);
//             
//            // hide X3DOM widgets
//            setDisplayWidgetsX3DOM (false);
//
//            // show X_ITE widgets
//            setDisplayWidgetsX_ITE (true);
        }
        else if (xhtmlX3domAction.getPlayer().equalsIgnoreCase(X3DOM_name))
        {
            pageIntegrationTabbedPane.setSelectedIndex(X3DOM_TAB);
            
//            x3domRadioButton.setSelected(true);
//             
//            // show X3DOM widgets
//            setDisplayWidgetsX3DOM (true);
//
//            // hide X_ITE widgets
//            setDisplayWidgetsX_ITE (false);
        }
        // might set X_ITE but then want CORS HTTP
        if (xhtmlX3domAction.getPreferredTab() == CORS_TAB)
        {
            pageIntegrationTabbedPane.setSelectedIndex(CORS_TAB);
        }
		
	urlList.setFileChooserX3d();
        
        // TODO, maybe if someday needed
//        pythonStartButton.setVisible(false);
//         pythonStopButton.setVisible(false);
    }
    
    protected final void setPlayerSelection (String playerName)
    {
        if      (xhtmlX3domAction.getPreferredTab() == CORS_TAB)
        {
            pageIntegrationTabbedPane.setSelectedIndex(CORS_TAB);
        }
        else if (playerName.equalsIgnoreCase("Cobweb") || playerName.equalsIgnoreCase(X_ITE_name))
        {
            pageIntegrationTabbedPane.setSelectedIndex(X_ITE_TAB);
        }
        else  if (playerName.equalsIgnoreCase(X3DOM_name))
        {
            pageIntegrationTabbedPane.setSelectedIndex(X3DOM_TAB);
        }
        // else ignore
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
        
        // TODO cache local url(s) if different
                    alwaysAutostartCheckBox.setSelected(X3dOptions.getAuthorAutolaunchCorsDirectory());
        localExamplesRootDirectoryTextField.setText(    X3dOptions.getExamplesRootDirectory());
        designatedLocalhostDirectoryTextField.setText( X3dOptions.getAuthorDesignatedCorsDirectory());
        
        if      (X3dOptions.getAuthorCorsDirectoryChoice().equals(LOCAL_EXAMPLES_ROOT))
        {
            useExamplesRootDirectoryRadioButton.setSelected(true);
            authorCorsDirectory =  localExamplesRootDirectoryTextField.getText();
        }
        else if (X3dOptions.getAuthorCorsDirectoryChoice().equals(DESIGNATED_DIRECTORY))
        {
            useDesignatedDirectoryRadioButton.setSelected(true);
            authorCorsDirectory =  designatedLocalhostDirectoryTextField.getText();
        }
        else if (X3dOptions.getAuthorCorsDirectoryChoice().equals(CURRENT_X3D_MODEL_DIRECTORY))
        {
            useModelDirectoryRadioButton.setSelected(true);
            authorCorsDirectory =  "TODO";
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {
        java.awt.GridBagConstraints gridBagConstraints;

        playerButtonGroup = new javax.swing.ButtonGroup();
        corsDirectoryButtonGroup = new javax.swing.ButtonGroup();
        pageIntegrationTabbedPane = new javax.swing.JTabbedPane();
        htmlPanel = new javax.swing.JPanel();
        verticalSpacerLabel1 = new javax.swing.JLabel();
        html5ImageLabel = new javax.swing.JLabel();
        x3dImage100Label = new javax.swing.JLabel();
        widthLabel = new javax.swing.JLabel();
        widthDescriptionLabel = new javax.swing.JLabel();
        widthTextField = new javax.swing.JTextField();
        heightLabel = new javax.swing.JLabel();
        heightDescriptionLabel = new javax.swing.JLabel();
        heightTextField = new javax.swing.JTextField();
        verticalSpacerLabel2 = new javax.swing.JLabel();
        viewX3d4Html5AnnexButton = new javax.swing.JButton();
        viewSecurityExamplesReadmeLabel = new javax.swing.JLabel();
        verticalSpacerLabelBottom = new javax.swing.JLabel();
        horizontalSpacerLabel = new javax.swing.JLabel();
        horizontalSpacerLabel1 = new javax.swing.JLabel();
        x3domPanel = new javax.swing.JPanel();
        x3domHeaderLabel = new javax.swing.JLabel();
        x3domLabel = new javax.swing.JLabel();
        x3domHomeButton = new javax.swing.JButton();
        x3domHelpButton = new javax.swing.JButton();
        x3domImageLabel = new javax.swing.JLabel();
        showLogCheckBox = new javax.swing.JCheckBox();
        showLogLabel = new javax.swing.JLabel();
        showStatisticsCheckBox = new javax.swing.JCheckBox();
        showStatisticsLabel = new javax.swing.JLabel();
        showProgressCheckBox = new javax.swing.JCheckBox();
        showProgressLabel = new javax.swing.JLabel();
        primitiveQualityLabel = new javax.swing.JLabel();
        primitiveQualityComboBox = new javax.swing.JComboBox();
        primitiveQualityDescriptionLabel = new javax.swing.JLabel();
        x3domDescriptionLabel = new javax.swing.JLabel();
        x_itePanel = new javax.swing.JPanel();
        x_iteHeaderLabel = new javax.swing.JLabel();
        x_iteLabel = new javax.swing.JLabel();
        x_iteHomeButton = new javax.swing.JButton();
        x_iteHelpButton = new javax.swing.JButton();
        cacheCheckBox = new javax.swing.JCheckBox();
        cacheDescriptionLabel = new javax.swing.JLabel();
        urlLabel = new javax.swing.JLabel();
        urlList = new org.web3d.x3d.palette.items.UrlExpandableList2();
        x_iteDescriptionLabel = new javax.swing.JLabel();
        corsPanel = new javax.swing.JPanel();
        corsLabel = new javax.swing.JLabel();
        corsDescriptionLabel = new javax.swing.JLabel();
        corsHelpButton = new javax.swing.JButton();
        localhostLabel = new javax.swing.JLabel();
        localhostComboBox = new javax.swing.JComboBox<>();
        portLabel = new javax.swing.JLabel();
        portTextField = new javax.swing.JTextField();
        useExamplesRootDirectoryRadioButton = new javax.swing.JRadioButton();
        useDesignatedDirectoryRadioButton = new javax.swing.JRadioButton();
        useModelDirectoryRadioButton = new javax.swing.JRadioButton();
        localExamplesRootDirectoryTextField = new javax.swing.JTextField();
        designatedLocalhostDirectoryTextField = new javax.swing.JTextField();
        designatedDirectoryClearButton = new javax.swing.JButton();
        designatedDirectoryButton = new javax.swing.JButton();
        designatedDirectoryDefaultButton = new javax.swing.JButton();
        currentDirectoryLabel = new javax.swing.JLabel();
        launchLocalDirectoryInBrowserButton = new javax.swing.JButton();
        javaHeaderLabel = new javax.swing.JLabel();
        javaAutoStartButton = new javax.swing.JButton();
        javaStartButton = new javax.swing.JButton();
        javaStopButton = new javax.swing.JButton();
        localhostHttpIconHeader = new javax.swing.JLabel();
        alwaysAutostartCheckBox = new javax.swing.JCheckBox();
        localHttpServerLabel = new javax.swing.JLabel();
        html5ImageLabel3 = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createTitledBorder(null, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14))); // NOI18N
        setPreferredSize(new java.awt.Dimension(720, 380));
        setLayout(new java.awt.GridBagLayout());

        pageIntegrationTabbedPane.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.pageIntegrationTabbedPane.toolTipText")); // NOI18N

        htmlPanel.setMinimumSize(new java.awt.Dimension(300, 70));
        htmlPanel.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(verticalSpacerLabel1, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.verticalSpacerLabel1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        htmlPanel.add(verticalSpacerLabel1, gridBagConstraints);

        html5ImageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/resources/HTML5_Logo_64.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(html5ImageLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.html5ImageLabel.text")); // NOI18N
        html5ImageLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.html5ImageLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 3, 0);
        htmlPanel.add(html5ImageLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(x3dImage100Label, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.x3dImage100Label.text")); // NOI18N
        x3dImage100Label.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.x3dImage100Label.toolTipText")); // NOI18N
        x3dImage100Label.setPreferredSize(new java.awt.Dimension(32, 32));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 30;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 1, 3, 3);
        htmlPanel.add(x3dImage100Label, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(widthLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.widthLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        htmlPanel.add(widthLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(widthDescriptionLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.widthDescriptionLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        htmlPanel.add(widthDescriptionLabel, gridBagConstraints);

        widthTextField.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        widthTextField.setText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.widthTextField.text")); // NOI18N
        widthTextField.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.widthTextField.toolTipText")); // NOI18N
        widthTextField.setMaximumSize(new java.awt.Dimension(60, 22));
        widthTextField.setMinimumSize(new java.awt.Dimension(20, 22));
        widthTextField.setPreferredSize(new java.awt.Dimension(60, 22));
        widthTextField.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                widthTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 4, 3, 3);
        htmlPanel.add(widthTextField, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(heightLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.heightLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        htmlPanel.add(heightLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(heightDescriptionLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.heightDescriptionLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        htmlPanel.add(heightDescriptionLabel, gridBagConstraints);

        heightTextField.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        heightTextField.setText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.heightTextField.text")); // NOI18N
        heightTextField.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.heightTextField.toolTipText")); // NOI18N
        heightTextField.setMaximumSize(new java.awt.Dimension(60, 22));
        heightTextField.setMinimumSize(new java.awt.Dimension(20, 22));
        heightTextField.setPreferredSize(new java.awt.Dimension(60, 22));
        heightTextField.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                heightTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 4, 3, 3);
        htmlPanel.add(heightTextField, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(verticalSpacerLabel2, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.verticalSpacerLabel2.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        htmlPanel.add(verticalSpacerLabel2, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(viewX3d4Html5AnnexButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.viewX3d4Html5AnnexButton.text")); // NOI18N
        viewX3d4Html5AnnexButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.viewX3d4Html5AnnexButton.toolTipText")); // NOI18N
        viewX3d4Html5AnnexButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                viewX3d4Html5AnnexButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        htmlPanel.add(viewX3d4Html5AnnexButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(viewSecurityExamplesReadmeLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.viewSecurityExamplesReadmeLabel.text")); // NOI18N
        viewSecurityExamplesReadmeLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.viewSecurityExamplesReadmeLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        htmlPanel.add(viewSecurityExamplesReadmeLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(verticalSpacerLabelBottom, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.verticalSpacerLabelBottom.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 10.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        htmlPanel.add(verticalSpacerLabelBottom, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(horizontalSpacerLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.horizontalSpacerLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 4.0;
        gridBagConstraints.weighty = 1.0;
        htmlPanel.add(horizontalSpacerLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(horizontalSpacerLabel1, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.horizontalSpacerLabel1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 4.0;
        gridBagConstraints.weighty = 1.0;
        htmlPanel.add(horizontalSpacerLabel1, gridBagConstraints);

        pageIntegrationTabbedPane.addTab(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.htmlPanel.TabConstraints.tabTitle"), htmlPanel); // NOI18N

        x3domPanel.setLayout(new java.awt.GridBagLayout());

        x3domHeaderLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(x3domHeaderLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.x3domHeaderLabel.text")); // NOI18N
        x3domHeaderLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.x3domHeaderLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3domPanel.add(x3domHeaderLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(x3domLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.x3domLabel.text")); // NOI18N
        x3domLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.x3domLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3domPanel.add(x3domLabel, gridBagConstraints);

        x3domHomeButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        x3domHomeButton.setForeground(new java.awt.Color(21, 71, 52));
        org.openide.awt.Mnemonics.setLocalizedText(x3domHomeButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.x3domHomeButton.text")); // NOI18N
        x3domHomeButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                x3domHomeButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 3, 3, 3);
        x3domPanel.add(x3domHomeButton, gridBagConstraints);

        x3domHelpButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        x3domHelpButton.setForeground(new java.awt.Color(21, 71, 52));
        org.openide.awt.Mnemonics.setLocalizedText(x3domHelpButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.x3domHelpButton.text")); // NOI18N
        x3domHelpButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                x3domHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 3, 3, 3);
        x3domPanel.add(x3domHelpButton, gridBagConstraints);

        x3domImageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/resources/x3dom_logo150x44.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(x3domImageLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.x3domImageLabel.text")); // NOI18N
        x3domImageLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.x3domImageLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 3, 0);
        x3domPanel.add(x3domImageLabel, gridBagConstraints);

        showLogCheckBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(showLogCheckBox, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.showLogCheckBox.text")); // NOI18N
        showLogCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        showLogCheckBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                showLogCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        x3domPanel.add(showLogCheckBox, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(showLogLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.showLogLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3domPanel.add(showLogLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(showStatisticsCheckBox, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.showStatisticsCheckBox.text")); // NOI18N
        showStatisticsCheckBox.setActionCommand(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.showStatisticsCheckBox.actionCommand")); // NOI18N
        showStatisticsCheckBox.setDoubleBuffered(true);
        showStatisticsCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        showStatisticsCheckBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                showStatisticsCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        x3domPanel.add(showStatisticsCheckBox, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(showStatisticsLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.showStatisticsLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3domPanel.add(showStatisticsLabel, gridBagConstraints);

        showProgressCheckBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(showProgressCheckBox, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.showProgressCheckBox.text")); // NOI18N
        showProgressCheckBox.setActionCommand(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.showProgressCheckBox.actionCommand")); // NOI18N
        showProgressCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        showProgressCheckBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                showProgressCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        x3domPanel.add(showProgressCheckBox, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(showProgressLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.showProgressLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3domPanel.add(showProgressLabel, gridBagConstraints);

        primitiveQualityLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(primitiveQualityLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.primitiveQualityLabel.text")); // NOI18N
        primitiveQualityLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.primitiveQualityLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3domPanel.add(primitiveQualityLabel, gridBagConstraints);

        primitiveQualityComboBox.setEditable(true);
        primitiveQualityComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "High", "Medium", "Low", "float" }));
        primitiveQualityComboBox.setMaximumSize(new java.awt.Dimension(60, 22));
        primitiveQualityComboBox.setMinimumSize(new java.awt.Dimension(20, 22));
        primitiveQualityComboBox.setPreferredSize(new java.awt.Dimension(55, 22));
        primitiveQualityComboBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                primitiveQualityComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 60;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        x3domPanel.add(primitiveQualityComboBox, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(primitiveQualityDescriptionLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.primitiveQualityDescriptionLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3domPanel.add(primitiveQualityDescriptionLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(x3domDescriptionLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.x3domDescriptionLabel.text")); // NOI18N
        x3domDescriptionLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.x3domDescriptionLabel.toolTipText")); // NOI18N
        x3domDescriptionLabel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        x3domDescriptionLabel.setMaximumSize(new java.awt.Dimension(250, 48));
        x3domDescriptionLabel.setMinimumSize(new java.awt.Dimension(116, 32));
        x3domDescriptionLabel.setPreferredSize(new java.awt.Dimension(116, 32));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3domPanel.add(x3domDescriptionLabel, gridBagConstraints);

        pageIntegrationTabbedPane.addTab(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.x3domPanel.TabConstraints.tabTitle"), x3domPanel); // NOI18N

        x_itePanel.setLayout(new java.awt.GridBagLayout());

        x_iteHeaderLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(x_iteHeaderLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.x_iteHeaderLabel.text")); // NOI18N
        x_iteHeaderLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.x_iteHeaderLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 3, 3, 3);
        x_itePanel.add(x_iteHeaderLabel, gridBagConstraints);

        x_iteLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/resources/x_ite_logo64x64.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(x_iteLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.x_iteLabel.text")); // NOI18N
        x_iteLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.x_iteLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x_itePanel.add(x_iteLabel, gridBagConstraints);

        x_iteHomeButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        x_iteHomeButton.setForeground(new java.awt.Color(21, 71, 52));
        org.openide.awt.Mnemonics.setLocalizedText(x_iteHomeButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.x_iteHomeButton.text")); // NOI18N
        x_iteHomeButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.x_iteHomeButton.toolTipText")); // NOI18N
        x_iteHomeButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                x_iteHomeButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 3, 3, 3);
        x_itePanel.add(x_iteHomeButton, gridBagConstraints);

        x_iteHelpButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        x_iteHelpButton.setForeground(new java.awt.Color(21, 71, 52));
        org.openide.awt.Mnemonics.setLocalizedText(x_iteHelpButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.x_iteHelpButton.text")); // NOI18N
        x_iteHelpButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.x_iteHelpButton.toolTipText")); // NOI18N
        x_iteHelpButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                x_iteHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 3, 3, 3);
        x_itePanel.add(x_iteHelpButton, gridBagConstraints);

        cacheCheckBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(cacheCheckBox, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.cacheCheckBox.text")); // NOI18N
        cacheCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.cacheCheckBox.toolTipText")); // NOI18N
        cacheCheckBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                cacheCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        x_itePanel.add(cacheCheckBox, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(cacheDescriptionLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.cacheDescriptionLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x_itePanel.add(cacheDescriptionLabel, gridBagConstraints);

        urlLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(urlLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.urlLabel.text")); // NOI18N
        urlLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.urlLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 0, 3);
        x_itePanel.add(urlLabel, gridBagConstraints);

        urlList.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        urlList.setMinimumSize(new java.awt.Dimension(600, 120));
        urlList.setPreferredSize(new java.awt.Dimension(620, 180));
        urlList.addPropertyChangeListener(new java.beans.PropertyChangeListener()
        {
            public void propertyChange(java.beans.PropertyChangeEvent evt)
            {
                urlListPropertyChange(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x_itePanel.add(urlList, gridBagConstraints);

        x_iteDescriptionLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(x_iteDescriptionLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.x_iteDescriptionLabel.text")); // NOI18N
        x_iteDescriptionLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.x_iteDescriptionLabel.toolTipText")); // NOI18N
        x_iteDescriptionLabel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        x_iteDescriptionLabel.setMaximumSize(new java.awt.Dimension(250, 48));
        x_iteDescriptionLabel.setMinimumSize(new java.awt.Dimension(116, 32));
        x_iteDescriptionLabel.setPreferredSize(new java.awt.Dimension(116, 32));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x_itePanel.add(x_iteDescriptionLabel, gridBagConstraints);

        pageIntegrationTabbedPane.addTab(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.x_itePanel.TabConstraints.tabTitle"), x_itePanel); // NOI18N

        corsPanel.setLayout(new java.awt.GridBagLayout());

        corsLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(corsLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.corsLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(corsLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(corsDescriptionLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.corsDescriptionLabel.text")); // NOI18N
        corsDescriptionLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.corsDescriptionLabel.toolTipText")); // NOI18N
        corsDescriptionLabel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        corsDescriptionLabel.setMaximumSize(new java.awt.Dimension(250, 48));
        corsDescriptionLabel.setMinimumSize(new java.awt.Dimension(116, 32));
        corsDescriptionLabel.setPreferredSize(new java.awt.Dimension(116, 32));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridwidth = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 3, 3);
        corsPanel.add(corsDescriptionLabel, gridBagConstraints);

        corsHelpButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        corsHelpButton.setForeground(new java.awt.Color(21, 71, 52));
        org.openide.awt.Mnemonics.setLocalizedText(corsHelpButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.corsHelpButton.text")); // NOI18N
        corsHelpButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                corsHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 3, 3, 3);
        corsPanel.add(corsHelpButton, gridBagConstraints);

        localhostLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(localhostLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.localhostLabel.text")); // NOI18N
        localhostLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.localhostLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(localhostLabel, gridBagConstraints);

        localhostComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "localhost", "0.0.0.0", "127.0.0.1" }));
        localhostComboBox.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.localhostComboBox.toolTipText")); // NOI18N
        localhostComboBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                localhostComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(localhostComboBox, gridBagConstraints);

        portLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(portLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.portLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        corsPanel.add(portLabel, gridBagConstraints);

        portTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        portTextField.setText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.portTextField.text")); // NOI18N
        portTextField.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.portTextField.toolTipText")); // NOI18N
        portTextField.setMaximumSize(new java.awt.Dimension(60, 22));
        portTextField.setMinimumSize(new java.awt.Dimension(20, 22));
        portTextField.setPreferredSize(new java.awt.Dimension(20, 22));
        portTextField.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                portTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 32;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(portTextField, gridBagConstraints);

        corsDirectoryButtonGroup.add(useExamplesRootDirectoryRadioButton);
        useExamplesRootDirectoryRadioButton.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(useExamplesRootDirectoryRadioButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.useExamplesRootDirectoryRadioButton.text")); // NOI18N
        useExamplesRootDirectoryRadioButton.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        useExamplesRootDirectoryRadioButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                useExamplesRootDirectoryRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        corsPanel.add(useExamplesRootDirectoryRadioButton, gridBagConstraints);

        corsDirectoryButtonGroup.add(useDesignatedDirectoryRadioButton);
        org.openide.awt.Mnemonics.setLocalizedText(useDesignatedDirectoryRadioButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.useDesignatedDirectoryRadioButton.text")); // NOI18N
        useDesignatedDirectoryRadioButton.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        useDesignatedDirectoryRadioButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                useDesignatedDirectoryRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        corsPanel.add(useDesignatedDirectoryRadioButton, gridBagConstraints);

        corsDirectoryButtonGroup.add(useModelDirectoryRadioButton);
        org.openide.awt.Mnemonics.setLocalizedText(useModelDirectoryRadioButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.useModelDirectoryRadioButton.text")); // NOI18N
        useModelDirectoryRadioButton.setEnabled(false);
        useModelDirectoryRadioButton.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        useModelDirectoryRadioButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                useModelDirectoryRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        corsPanel.add(useModelDirectoryRadioButton, gridBagConstraints);

        localExamplesRootDirectoryTextField.setText(X3dOptions.getExamplesRootDirectory());
        localExamplesRootDirectoryTextField.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.localExamplesRootDirectoryTextField.toolTipText")); // NOI18N
        localExamplesRootDirectoryTextField.setEnabled(false);
        localExamplesRootDirectoryTextField.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                localExamplesRootDirectoryTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(localExamplesRootDirectoryTextField, gridBagConstraints);

        designatedLocalhostDirectoryTextField.setText(X3dOptions.getExamplesRootDirectory());
        designatedLocalhostDirectoryTextField.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.designatedLocalhostDirectoryTextField.toolTipText")); // NOI18N
        designatedLocalhostDirectoryTextField.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                designatedLocalhostDirectoryTextFieldMouseExited(evt);
            }
        });
        designatedLocalhostDirectoryTextField.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                designatedLocalhostDirectoryTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(designatedLocalhostDirectoryTextField, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(designatedDirectoryClearButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.designatedDirectoryClearButton.text")); // NOI18N
        designatedDirectoryClearButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.designatedDirectoryClearButton.toolTipText")); // NOI18N
        designatedDirectoryClearButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        designatedDirectoryClearButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                designatedDirectoryClearButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        corsPanel.add(designatedDirectoryClearButton, gridBagConstraints);

        designatedDirectoryButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(designatedDirectoryButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.designatedDirectoryButton.text")); // NOI18N
        designatedDirectoryButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.designatedDirectoryButton.toolTipText")); // NOI18N
        designatedDirectoryButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        designatedDirectoryButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                designatedDirectoryButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 11;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
        corsPanel.add(designatedDirectoryButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(designatedDirectoryDefaultButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.designatedDirectoryDefaultButton.text")); // NOI18N
        designatedDirectoryDefaultButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.designatedDirectoryDefaultButton.toolTipText")); // NOI18N
        designatedDirectoryDefaultButton.setMargin(new java.awt.Insets(3, 3, 3, 3));
        designatedDirectoryDefaultButton.setMinimumSize(new java.awt.Dimension(24, 24));
        designatedDirectoryDefaultButton.setPreferredSize(new java.awt.Dimension(48, 24));
        designatedDirectoryDefaultButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                designatedDirectoryDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.ipadx = 32;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(designatedDirectoryDefaultButton, gridBagConstraints);

        currentDirectoryLabel.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(currentDirectoryLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.currentDirectoryLabel.text")); // NOI18N
        currentDirectoryLabel.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(currentDirectoryLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(launchLocalDirectoryInBrowserButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.launchLocalDirectoryInBrowserButton.text")); // NOI18N
        launchLocalDirectoryInBrowserButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.launchLocalDirectoryInBrowserButton.toolTipText")); // NOI18N
        launchLocalDirectoryInBrowserButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                launchLocalDirectoryInBrowserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(launchLocalDirectoryInBrowserButton, gridBagConstraints);

        javaHeaderLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(javaHeaderLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.javaHeaderLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(javaHeaderLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(javaAutoStartButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.javaAutoStartButton.text")); // NOI18N
        javaAutoStartButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.javaAutoStartButton.toolTipText")); // NOI18N
        javaAutoStartButton.setEnabled(false);
        javaAutoStartButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                javaAutoStartButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(javaAutoStartButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(javaStartButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.javaStartButton.text")); // NOI18N
        javaStartButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.javaStartButton.toolTipText")); // NOI18N
        javaStartButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                javaStartButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(javaStartButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(javaStopButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.javaStopButton.text")); // NOI18N
        javaStopButton.setEnabled(false);
        javaStopButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                javaStopButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(javaStopButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(localhostHttpIconHeader, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.localhostHttpIconHeader.text")); // NOI18N
        localhostHttpIconHeader.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.localhostHttpIconHeader.toolTipText")); // NOI18N
        localhostHttpIconHeader.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        corsPanel.add(localhostHttpIconHeader, gridBagConstraints);

        alwaysAutostartCheckBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(alwaysAutostartCheckBox, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.alwaysAutostartCheckBox.text")); // NOI18N
        alwaysAutostartCheckBox.setEnabled(false);
        alwaysAutostartCheckBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        alwaysAutostartCheckBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                alwaysAutostartCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(alwaysAutostartCheckBox, gridBagConstraints);

        localHttpServerLabel.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        localHttpServerLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(localHttpServerLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.localHttpServerLabel.text")); // NOI18N
        localHttpServerLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.localHttpServerLabel.toolTipText")); // NOI18N
        localHttpServerLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        corsPanel.add(localHttpServerLabel, gridBagConstraints);

        html5ImageLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/resources/Http_icon.svg-120x62.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(html5ImageLabel3, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.html5ImageLabel3.text")); // NOI18N
        html5ImageLabel3.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.html5ImageLabel3.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 3, 0);
        corsPanel.add(html5ImageLabel3, gridBagConstraints);

        pageIntegrationTabbedPane.addTab(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionPanel.class, "X3dToXhtmlDomConversionPanel.corsPanel.TabConstraints.tabTitle"), corsPanel); // NOI18N

        pageIntegrationTabbedPane.setSelectedIndex(HTML_LAYOUT_TAB);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 10, 3, 10);
        add(pageIntegrationTabbedPane, gridBagConstraints);
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

//    private void setDisplayWidgetsX_ITE (boolean shown)
//    {
//                      cacheCheckBox.setVisible(shown);
//              cacheDescriptionLabel.setVisible(shown);
//                           urlLabel.setVisible(shown);
//                            urlList.setVisible(shown);
//              x_iteDescriptionLabel.setVisible(shown); 
//        
//    }
//    private void setDisplayWidgetsX3DOM (boolean shown)
//    {
//                     showLogCheckBox.setVisible(shown);
//                        showLogLabel.setVisible(shown);
//              showStatisticsCheckBox.setVisible(shown);
//                 showStatisticsLabel.setVisible(shown);
//                showProgressCheckBox.setVisible(shown);
//                   showProgressLabel.setVisible(shown);
//            primitiveQualityComboBox.setVisible(shown);
//               primitiveQualityLabel.setVisible(shown);
//    primitiveQualityDescriptionLabel.setVisible(shown);
//               x3domDescriptionLabel.setVisible(shown);
//        
//    }
    
    private void cacheCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cacheCheckBoxActionPerformed
        xhtmlX3domAction.setCache(cacheCheckBox.isSelected());
    }//GEN-LAST:event_cacheCheckBoxActionPerformed

    private void corsHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_corsHelpButtonActionPerformed
        LaunchX3dSceneAuthoringHintsCorsAction launchCorsAction = new LaunchX3dSceneAuthoringHintsCorsAction();
        launchCorsAction.performAction();
    }//GEN-LAST:event_corsHelpButtonActionPerformed

    /**
     * @link https://dzone.com/articles/simple-http-server-in-java
     */
class LocalFileHandler implements HttpHandler {

    @Override    
    public void handle(HttpExchange httpExchange) throws IOException 
    {
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

    private void handleResponse(HttpExchange httpExchange, String requestParamValue)  throws  IOException
    {
        OutputStream outputStream = httpExchange.getResponseBody();

        byte[] content = Files.readAllBytes(Paths.get(requestParamValue));


        httpExchange.getResponseHeaders().set("Access-Control-Allow-Origin", "*");
      	httpExchange.sendResponseHeaders(200, content.length);
        outputStream.write(content);
        outputStream.flush();
        outputStream.close();
    }
}
    /* Avoid potential Netbeans/X3D-Edit error when using null (default) executor in same thread
    * @see https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/util/concurrent/Executor.html
    */
    class ThreadPerTaskExecutor implements java.util.concurrent.Executor
    {
        @Override
        public void execute(Runnable r)
        {
            new Thread(r).start();
        }
    }

    HttpServer httpServer;
//    final String DEFAULT_MODEL_ROOT_DIRECTORY = "local directory tree for model visibility";
    
    private void javaHttpServerClose()
    {
        if (httpServer != null)
        {            
            httpServer.stop(4); // waits, closes, kills thread, exits
            System.out.println("*** CORS httpServer stopped");
            System.out.flush();
            javaStopButton.setEnabled(false);
            httpServer = null; // forcibly destroy
            // TODO unbind address
        }
    }
    
    /**
     * Launch http server on local host
     * @link https://docs.oracle.com/en/java/javase/16/docs/api/jdk.httpserver/com/sun/net/httpserver/package-summary.html
     * @link https://dzone.com/articles/simple-http-server-in-java
     * @param evt triggering input event from callback
     */
    private void javaStartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_javaStartButtonActionPerformed
        // Would spinning off a JAVA process that references the local dir using the below work?
        // ref: https://dzone.com/articles/simple-http-server-in-java

        // TODO port value checks
        int             portValue = Integer.parseInt(portTextField.getText());
        String       addressValue = localhostComboBox.getSelectedItem().toString();
        if (addressValue.isBlank())
            addressValue = "localhost"; // probably wrong, expect this to get overwritten by interface
        String modelRootDirectory = "/";
        
        if      (useExamplesRootDirectoryRadioButton.isSelected())
                 modelRootDirectory = localExamplesRootDirectoryTextField.getText();
        else if (  useDesignatedDirectoryRadioButton.isSelected())
                 modelRootDirectory = designatedLocalhostDirectoryTextField.getText();
//        else if (       useModelDirectoryRadioButton.isSelected())
//                 modelRootDirectory = directory of current html page and model
        
        // TODO URI conversion likely better; getting path exception for server context
//        modelRootDirectory = modelRootDirectory.replaceAll("\\\\","/"); // double escaping for Java character and regex literal;
        
        // put safety/XML &Security checks here, e.g start and end with "/"
        // https://docs.oracle.com/en/java/javase/19/docs/api/jdk.httpserver/com/sun/net/httpserver/HttpServer.html#createContext(java.lang.String,com.sun.net.httpserver.HttpHandler)
        
        if (modelRootDirectory.equals("\\"))
        {
            // TODO error handling, starting/ending slashes, etc. probably confirm by checking it is a valid URI instance
        }
        // note http for local use, not https
        String localUrl = "http://" + addressValue + ":" + portValue + File.separatorChar + modelRootDirectory;
        localUrl = localUrl.replaceAll("\\\\","/"); // double escaping for Java character and regex literal
        
        try
        {
            File holdFile = new File(modelRootDirectory); // which is linked in downloadDirectoryLabel, actually a direcgory
            if  (!holdFile.isDirectory())
                System.out.println("*** URI problem...");
            URI modelRootDirectoryURI = holdFile.toURI();
                
            if (httpServer != null) // created previously
            {
                javaHttpServerClose();
            }
            httpServer = HttpServer.create((new InetSocketAddress(addressValue, portValue)), 0);
            
            // TODO administrator permission needed?
            
            // INFO [org.netbeans.api.java.source.ElementHandle]: Cannot resolve: ElementHandle[kind=METHOD; sigs=com.sun.net.httpserver.HttpServer createContext (Ljava/lang/String;)Lcom/sun/net/httpserver/HttpContext; ]
            httpServer.createContext(modelRootDirectoryURI.getPath(), new LocalFileHandler() );
            
            // https://docs.oracle.com/en/java/javase/19/docs/api/jdk.httpserver/com/sun/net/httpserver/HttpServer.html#setExecutor(java.util.concurrent.Executor)
            ThreadPerTaskExecutor httpServerExecutor = new ThreadPerTaskExecutor();
            httpServer.setExecutor(httpServerExecutor); // null means default implementation; TODO eliminate potential problem

            httpServer.start();
            System.out.println("*** Java httpServer started for CORS");
            
            LaunchX3dExamplesAction.sendBrowserTo("/"); // localUrl);
            System.out.println("*** launch default browser to / looking for " + localUrl);
            System.out.flush();
        }
        catch (IOException ioe)
        {
            System.err.println ("*** javaHttpServerButtonActionPerformed() exception " + ioe);
            ioe.printStackTrace();
            
            // TODO report via dialog box if address already bound, http server might already be running
        }
        // https://localhost:8000
        // TODO what about restricting http server to specific directory tree?
        // TODO what about launching browser in initial directory
        // TODO what about ability to kill process?
        // TODO what about overall session timeout?
        
        javaAutoStartButton.setEnabled(false);
            javaStartButton.setEnabled(false); 
             javaStopButton.setEnabled(true);
    }//GEN-LAST:event_javaStartButtonActionPerformed

    private void localExamplesRootDirectoryTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_localExamplesRootDirectoryTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_localExamplesRootDirectoryTextFieldActionPerformed

    public void indicateCorsServerRunning (boolean state)
    {
        // TODO, perhaps an image icon in tab?
    }
    public void indicateCorsServerNeeded (boolean state)
    {
        // TODO, invoke when spinning off X_ITE pages
    }
    private boolean startJavaHttpProcess ()
    {
        // TODO, return whether running
        javaStartButton.setEnabled(false);
         javaStopButton.setEnabled(true);
        return false;
    }
    private boolean stopJavaHttpProcess ()
    {
        // TODO, return whether running
        javaStartButton.setEnabled(true);
         javaStopButton.setEnabled(false);
        return false;
    }
//    private boolean startPythonHttpProcess ()
//    {
//        // TODO 
//        pythonStartButton.setEnabled(false);
//         pythonStopButton.setEnabled(true);
//        return false;
//    }
//    private boolean stopPythonHttpProcess ()
//    {
//        // TODO also invoke this method on application exit
//        pythonHttpProcess.destroy();
//        pythonStartButton.setEnabled(true);
//         pythonStopButton.setEnabled(false);
//        System.out.println("*** stop (destroy) python process");
//        pythonHttpProcess = null;
//        return true;
//    }
    /** set chooser tab
     * @param newIndex tab to select */
    public void setPaneIndex(int newIndex)
    {
        if ((newIndex >= 0) && (newIndex <= 3))
            pageIntegrationTabbedPane.setSelectedIndex(newIndex);
    }
    private void javaStopButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_javaStopButtonActionPerformed
//        javaAutoStartButton.setEnabled(true); // TODO activate
            javaStartButton.setEnabled(true);
             javaStopButton.setEnabled(false); 
        
        javaHttpServerClose();
    }//GEN-LAST:event_javaStopButtonActionPerformed

    private void x3domHomeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_x3domHomeButtonActionPerformed
        LaunchX3dExamplesAction.sendBrowserTo(X3DOM_site);
    }//GEN-LAST:event_x3domHomeButtonActionPerformed

    private void x3domHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_x3domHelpButtonActionPerformed
        LaunchX3dExamplesAction.sendBrowserTo(X3DOM_help);
    }//GEN-LAST:event_x3domHelpButtonActionPerformed

    private void x_iteHomeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_x_iteHomeButtonActionPerformed
        LaunchX3dExamplesAction.sendBrowserTo(X_ITE_site);
    }//GEN-LAST:event_x_iteHomeButtonActionPerformed

    private void x_iteHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_x_iteHelpButtonActionPerformed
        LaunchX3dExamplesAction.sendBrowserTo(X_ITE_help);
    }//GEN-LAST:event_x_iteHelpButtonActionPerformed

    private void viewX3d4Html5AnnexButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_viewX3d4Html5AnnexButtonActionPerformed
    {//GEN-HEADEREND:event_viewX3d4Html5AnnexButtonActionPerformed
        openInBrowser(X3D4_HTML_AUTHORING_GUIDELINES);
    }//GEN-LAST:event_viewX3d4Html5AnnexButtonActionPerformed

    private void portTextFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_portTextFieldActionPerformed
    {//GEN-HEADEREND:event_portTextFieldActionPerformed
        // TODO add your handling code here: check legal values
    }//GEN-LAST:event_portTextFieldActionPerformed

    private void javaAutoStartButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_javaAutoStartButtonActionPerformed
    {//GEN-HEADEREND:event_javaAutoStartButtonActionPerformed
        // TODO add your handling code here: 
    javaAutoStartButton.setEnabled(false);
        javaStartButton.setEnabled(false);
         javaStopButton.setEnabled(true);
    }//GEN-LAST:event_javaAutoStartButtonActionPerformed

    private void useModelDirectoryRadioButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_useModelDirectoryRadioButtonActionPerformed
    {//GEN-HEADEREND:event_useModelDirectoryRadioButtonActionPerformed
        // TODO figure out current directory
        setAuthorCorsDirectory(CURRENT_X3D_MODEL_DIRECTORY);
        X3dOptions.setAuthorCorsDirectoryChoice(CURRENT_X3D_MODEL_DIRECTORY);
    }//GEN-LAST:event_useModelDirectoryRadioButtonActionPerformed

    private void designatedLocalhostDirectoryTextFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_designatedLocalhostDirectoryTextFieldActionPerformed
    {//GEN-HEADEREND:event_designatedLocalhostDirectoryTextFieldActionPerformed
        X3dOptions.setAuthorDesignatedCorsDirectory(designatedLocalhostDirectoryTextField.getText().trim());
    }//GEN-LAST:event_designatedLocalhostDirectoryTextFieldActionPerformed

    private void designatedDirectoryDefaultButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_designatedDirectoryDefaultButtonActionPerformed
    {//GEN-HEADEREND:event_designatedDirectoryDefaultButtonActionPerformed
        designatedLocalhostDirectoryTextField.setText(AUTHOR_DESIGNATED_CORS_DIRECTORY_DEFAULT); // user.home
        X3dOptions.setAuthorDesignatedCorsDirectory(designatedLocalhostDirectoryTextField.getText());
    }//GEN-LAST:event_designatedDirectoryDefaultButtonActionPerformed

    private void designatedDirectoryButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_designatedDirectoryButtonActionPerformed
    {//GEN-HEADEREND:event_designatedDirectoryButtonActionPerformed
        // file chooser looks in given directory first
        if (corsDirectoryChooser == null) // first time through
        {
          if  ((designatedLocalhostDirectoryTextField.getText() != null) && !designatedLocalhostDirectoryTextField.getText().isBlank())
               corsDirectoryChooser = new JFileChooser(designatedLocalhostDirectoryTextField.getText().trim());
          else corsDirectoryChooser = new JFileChooser(System.getProperty("user.home"));
          corsDirectoryChooser.setMultiSelectionEnabled(false);
          corsDirectoryChooser.putClientProperty("JFileChooser.appBundleIsTraversable", "never");  // for macs
        }
        String title = "Choose designated CORS server directory";
        corsDirectoryChooser.setDialogTitle(title);
        // https://stackoverflow.com/questions/25666642/jfilechooser-to-pick-a-directory-or-a-single-file
        corsDirectoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnValue = corsDirectoryChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION)
        {
            designatedLocalhostDirectoryTextField.setText(corsDirectoryChooser.getSelectedFile().getAbsolutePath());
            // TF callback to save changed options (doesn't happen automatically)
            designatedLocalhostDirectoryTextField.postActionEvent();
        }
        
        designatedLocalhostDirectoryTextField.setText(      designatedLocalhostDirectoryTextField.getText().trim());
        X3dOptions.setAuthorDesignatedCorsDirectory(designatedLocalhostDirectoryTextField.getText());
    }//GEN-LAST:event_designatedDirectoryButtonActionPerformed

    private void designatedDirectoryClearButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_designatedDirectoryClearButtonActionPerformed
    {//GEN-HEADEREND:event_designatedDirectoryClearButtonActionPerformed
        designatedLocalhostDirectoryTextField.setText("");
        X3dOptions.setAuthorDesignatedCorsDirectory(designatedLocalhostDirectoryTextField.getText());
    }//GEN-LAST:event_designatedDirectoryClearButtonActionPerformed

    private void localhostComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_localhostComboBoxActionPerformed
    {//GEN-HEADEREND:event_localhostComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_localhostComboBoxActionPerformed

    private void useExamplesRootDirectoryRadioButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_useExamplesRootDirectoryRadioButtonActionPerformed
    {//GEN-HEADEREND:event_useExamplesRootDirectoryRadioButtonActionPerformed
        setAuthorCorsDirectory(localExamplesRootDirectoryTextField.getText());
        X3dOptions.setAuthorCorsDirectoryChoice(LOCAL_EXAMPLES_ROOT);
    }//GEN-LAST:event_useExamplesRootDirectoryRadioButtonActionPerformed

    private void useDesignatedDirectoryRadioButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_useDesignatedDirectoryRadioButtonActionPerformed
    {//GEN-HEADEREND:event_useDesignatedDirectoryRadioButtonActionPerformed
        setAuthorCorsDirectory(designatedLocalhostDirectoryTextField.getText());
        X3dOptions.setAuthorCorsDirectoryChoice(DESIGNATED_DIRECTORY);
    }//GEN-LAST:event_useDesignatedDirectoryRadioButtonActionPerformed

    private void alwaysAutostartCheckBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_alwaysAutostartCheckBoxActionPerformed
    {//GEN-HEADEREND:event_alwaysAutostartCheckBoxActionPerformed
        X3dOptions.setAuthorAutolaunchCorsDirectory(alwaysAutostartCheckBox.isSelected());
    }//GEN-LAST:event_alwaysAutostartCheckBoxActionPerformed

    private void designatedLocalhostDirectoryTextFieldMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_designatedLocalhostDirectoryTextFieldMouseExited
    {//GEN-HEADEREND:event_designatedLocalhostDirectoryTextFieldMouseExited
        X3dOptions.setAuthorDesignatedCorsDirectory(designatedLocalhostDirectoryTextField.getText().trim());
    }//GEN-LAST:event_designatedLocalhostDirectoryTextFieldMouseExited

    private void launchLocalDirectoryInBrowserButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_launchLocalDirectoryInBrowserButtonActionPerformed
    {//GEN-HEADEREND:event_launchLocalDirectoryInBrowserButtonActionPerformed
        // TODO consolidate duplicative code
        int             portValue = Integer.parseInt(portTextField.getText());
        String       addressValue = localhostComboBox.getSelectedItem().toString();
        if (addressValue.isBlank())
            addressValue = "localhost";
        // getAuthorCorsDirectory() should be root of query to https://localhost:8000
        String localRootAddress = "http://" + addressValue + ":" + portValue;
        openInBrowser(localRootAddress);
    }//GEN-LAST:event_launchLocalDirectoryInBrowserButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox alwaysAutostartCheckBox;
    private javax.swing.JCheckBox cacheCheckBox;
    private javax.swing.JLabel cacheDescriptionLabel;
    private javax.swing.JLabel corsDescriptionLabel;
    private javax.swing.ButtonGroup corsDirectoryButtonGroup;
    private javax.swing.JButton corsHelpButton;
    private javax.swing.JLabel corsLabel;
    private javax.swing.JPanel corsPanel;
    private javax.swing.JLabel currentDirectoryLabel;
    private javax.swing.JButton designatedDirectoryButton;
    private javax.swing.JButton designatedDirectoryClearButton;
    private javax.swing.JButton designatedDirectoryDefaultButton;
    private javax.swing.JTextField designatedLocalhostDirectoryTextField;
    private javax.swing.JLabel heightDescriptionLabel;
    private javax.swing.JLabel heightLabel;
    private javax.swing.JTextField heightTextField;
    private javax.swing.JLabel horizontalSpacerLabel;
    private javax.swing.JLabel horizontalSpacerLabel1;
    private javax.swing.JLabel html5ImageLabel;
    private javax.swing.JLabel html5ImageLabel3;
    private javax.swing.JPanel htmlPanel;
    private javax.swing.JButton javaAutoStartButton;
    private javax.swing.JLabel javaHeaderLabel;
    private javax.swing.JButton javaStartButton;
    private javax.swing.JButton javaStopButton;
    private javax.swing.JButton launchLocalDirectoryInBrowserButton;
    private javax.swing.JTextField localExamplesRootDirectoryTextField;
    private javax.swing.JLabel localHttpServerLabel;
    private javax.swing.JComboBox<String> localhostComboBox;
    private javax.swing.JLabel localhostHttpIconHeader;
    private javax.swing.JLabel localhostLabel;
    private javax.swing.JTabbedPane pageIntegrationTabbedPane;
    private javax.swing.ButtonGroup playerButtonGroup;
    private javax.swing.JLabel portLabel;
    private javax.swing.JTextField portTextField;
    private javax.swing.JComboBox primitiveQualityComboBox;
    private javax.swing.JLabel primitiveQualityDescriptionLabel;
    private javax.swing.JLabel primitiveQualityLabel;
    private javax.swing.JCheckBox showLogCheckBox;
    private javax.swing.JLabel showLogLabel;
    private javax.swing.JCheckBox showProgressCheckBox;
    private javax.swing.JLabel showProgressLabel;
    private javax.swing.JCheckBox showStatisticsCheckBox;
    private javax.swing.JLabel showStatisticsLabel;
    private javax.swing.JLabel urlLabel;
    private org.web3d.x3d.palette.items.UrlExpandableList2 urlList;
    private javax.swing.JRadioButton useDesignatedDirectoryRadioButton;
    private javax.swing.JRadioButton useExamplesRootDirectoryRadioButton;
    private javax.swing.JRadioButton useModelDirectoryRadioButton;
    private javax.swing.JLabel verticalSpacerLabel1;
    private javax.swing.JLabel verticalSpacerLabel2;
    private javax.swing.JLabel verticalSpacerLabelBottom;
    private javax.swing.JLabel viewSecurityExamplesReadmeLabel;
    private javax.swing.JButton viewX3d4Html5AnnexButton;
    private javax.swing.JLabel widthDescriptionLabel;
    private javax.swing.JLabel widthLabel;
    private javax.swing.JTextField widthTextField;
    private javax.swing.JLabel x3dImage100Label;
    private javax.swing.JLabel x3domDescriptionLabel;
    private javax.swing.JLabel x3domHeaderLabel;
    private javax.swing.JButton x3domHelpButton;
    private javax.swing.JButton x3domHomeButton;
    private javax.swing.JLabel x3domImageLabel;
    private javax.swing.JLabel x3domLabel;
    private javax.swing.JPanel x3domPanel;
    private javax.swing.JLabel x_iteDescriptionLabel;
    private javax.swing.JLabel x_iteHeaderLabel;
    private javax.swing.JButton x_iteHelpButton;
    private javax.swing.JButton x_iteHomeButton;
    private javax.swing.JLabel x_iteLabel;
    private javax.swing.JPanel x_itePanel;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the authorCorsDirectory
     */
    public String getAuthorCorsDirectory()
    {
        return authorCorsDirectory;
    }
    /**
     * @param newAuthorCorsDirectoryChoice the preferredCorsDirectoryChoice to set
     */
    public void setAuthorCorsDirectory(String newAuthorCorsDirectoryChoice)
    {
        this.authorCorsDirectory = newAuthorCorsDirectoryChoice;
    }
}
