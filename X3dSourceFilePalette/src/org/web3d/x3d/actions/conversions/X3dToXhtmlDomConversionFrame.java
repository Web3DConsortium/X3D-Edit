/*
 * Copyright (c) 1995-2023 held by the author(s).  All rights reserved.
 *  
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *  
 *  * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer
 *       in the documentation and/or other materials provided with the
 *       distribution.
 *  * Neither the names of the Naval Postgraduate School (NPS)
 *       Modeling Virtual Environments and Simulation (MOVES) Institute
 *       (https://www.nps.edu and https://MovesInstitute.nps.edu)
 *       nor the names of its contributors may be used to endorse or
 *       promote products derived from this software without specific
 *       prior written permission.
 *  
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.web3d.x3d.actions.conversions;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.URI;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JFileChooser;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.Exceptions;
import org.openide.util.ImageUtilities;
import org.web3d.x3d.DownloadX3dExamplesArchivesAction;
import org.web3d.x3d.actions.BaseViewAction;
import static org.web3d.x3d.actions.BaseViewAction.X3D4_HTML_AUTHORING_GUIDELINES;
import static org.web3d.x3d.actions.BaseViewAction.X3D_SCENE_AUTHORING_HINTS;
import org.web3d.x3d.actions.LaunchX3dSceneAuthoringHintsCorsAction;
import static org.web3d.x3d.actions.conversions.ConversionsHelper.openInBrowser;
import org.web3d.x3d.options.X3dEditUserPreferences;
import static org.web3d.x3d.options.X3dEditUserPreferences.AUTHOR_MODELS_DIRECTORY_DEFAULT;
import org.web3d.x3d.options.X3dEditUserPreferencesPanel;
import org.web3d.x3d.palette.items.BaseCustomizer;

/**
 * Popup frame to handle HTML5 export using X3DOM/X_ITE, also handle CORS localhost http server.
 * @author brutzman
 */
public class X3dToXhtmlDomConversionFrame extends javax.swing.JFrame {

    protected int getTabbedPaneIndex()
    {
        return pageIntegrationTabbedPane.getSelectedIndex();
    }
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
    // LOCAL_EXAMPLES_ROOT is default in X3dEditUserPreferences.AUTHOR_MODELS_DIRECTORY_PORT_DEFAULT
    public static final String          LOCAL_EXAMPLES_ROOT = "LOCAL_EXAMPLES_ROOT";
    public static final String         DESIGNATED_DIRECTORY = "DESIGNATED_DIRECTORY";
    public static final String  CURRENT_X3D_MODEL_DIRECTORY = "CURRENT_X3D_MODEL_DIRECTORY";
        
    private JFileChooser authorModelsDirectoryChooser;
    private JFileChooser         corsDirectoryChooser;
    
    final static String HTTP_START   = "http start";
    final static String HTTP_STOP    = "http stop";
    final static String HTTP_RUNNING = "running...";
    final static String HTTP_BLOCKED = "(blocked)";
    
    final static String AUTHOR_MODELS    = "AUTHOR_MODELS";
    final static String EXAMPLE_ARCHIVES = "EXAMPLE_ARCHIVES";
    final static String ACTIVE_X3D_MODEL = "ACTIVE_X3D_MODEL";
        
//    static  String            currentServerType = AUTHOR_MODELS;
    static String                  addressValue = "localhost";
    static boolean    isAliveAuthorModelsServer = false;
    static boolean isAliveExampleArchivesServer = false;
    static boolean  isAliveActiveX3dModelServer = false;
    
    private static final int INITIAL_ACTIVE_X3D_MODEL_SERVER_PORT = 8011;
            static       int nextActiveX3dModelServerPort = INITIAL_ACTIVE_X3D_MODEL_SERVER_PORT;
    private static ArrayList<String>  activeX3dModelNameList;
    private static ArrayList<String>  activeX3dModelDirectoryList;
    private static ArrayList<String>  activeX3dModelPortList;
    private static ArrayList<Process> activeX3dModelProcessList;
    private static int    activeX3dModelServerSelection = -1; // from activeX3dModelDirectoryServerListComboBox selected index
    private static String activeX3dModelServerPort      = "";
    
    
    // ProcessBuilder and Process do not provide a mechanism for inserting a shutdown hook,
    // so use Runtime and Process instead
    // https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/lang/ProcessBuilder.html
//    ProcessBuilder processBuilder1;
//    ProcessBuilder processBuilder2;
//    ProcessBuilder processBuilder3;
    
    // https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/lang/Runtime.html
    public static Runtime runtime;
    
    // https://docs.oracle.com/en/java/javase/18/docs/api/java.base/java/lang/Process.html
    static Process        authorModelsHttpServerProcess;
    static Process        exampleArchivesHttpServerProcess;
    static Process        httpServerProcessNew;
    
    Color   black      = new Color(  0,   0,   0);
    Color   darkgreen  = new Color( 42, 142, 104);
    Color   darkorange = new Color(255, 140,   0);

    Font plainFont;
    Font  boldFont;
    static String message = new String();
    
    private static X3dToXhtmlDomConversionAction x3dToXhtmlDomConversionAction;
    
    /**
     * Creates new form X3dToXhtmlDomConversionFrame
     * @param xhtmlX3domAction action class of interest
     */
    public X3dToXhtmlDomConversionFrame(X3dToXhtmlDomConversionAction xhtmlX3domAction)
    {
        X3dToXhtmlDomConversionFrame.x3dToXhtmlDomConversionAction = xhtmlX3domAction; // same as this. for static variable
        activeX3dModelNameList      = new ArrayList<>();
        activeX3dModelDirectoryList = new ArrayList<>();
        activeX3dModelPortList      = new ArrayList<>();
        activeX3dModelProcessList   = new ArrayList<>();
        
        initComponents();
        setTitle (" X3D4 Model Integration in HTML5 Web Page"); // note leading space for readability
        setIconImage(ImageUtilities.loadImage("org/web3d/x3d/resources/HTML5_Logo_64.png"));
        
        // unset autolaunch if not present
        if (X3dEditUserPreferences.getAuthorModelsDirectory().isBlank() || X3dEditUserPreferences.getAuthorModelsServerPort().isBlank())
        {
            X3dEditUserPreferences.setAuthorModelsServerAutolaunch(false);
            autolaunchAuthorModelsServerCheckBox.setSelected(false);
        }
        if (X3dEditUserPreferences.getExampleArchivesRootDirectory().isBlank() || X3dEditUserPreferences.getExampleArchivesServerPort().isBlank())
        {
            X3dEditUserPreferences.setExampleArchivesServerAutolaunch(false);
            autolaunchExampleArchivesServerCheckBox.setSelected(false);
        }
        // leave ActiveX3dModel autolaunch unmodified on startup (likely empty)

        plainFont = addressLabel.getFont().deriveFont(Font.PLAIN);
         boldFont = plainFont.deriveFont(Font.BOLD);
        
        updateValuesInPanel(); // must follow componnent intialization
        // several fields are not intended for user editing
                portActiveX3dModelServerTextField.setEditable(false);
                exampleArchivesDirectoryTextField.setEditable(false);
        activeX3dModelDirectoryServerListComboBox.setEditable(false);
        
	urlList.setFileChooserX3d(); // configuration
        // TODO load scene url
        updateActiveX3dModelDirectoryButtons();

        // prepare for shutdown of localhost http servers on exit
        // https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/lang/Runtime.html#getRuntime()
        // https://stackoverflow.com/questions/191215/how-to-stop-java-process-gracefully
        // https://stackoverflow.com/questions/19639319/java-shutdown-hook
        if (runtime == null)
            runtime = Runtime.getRuntime(); // singleton since invocation may come via static class method, prior to instance class creation
        runtime.addShutdownHook(new Thread() {
            @Override
            public void run()
            {
                // TODO is it possible to discern which of three servers is running by this thread, in order to improve output message?
                message = "*** X3D-Edit shutdownHook now terminating http server processes...";
                System.out.println(message);
                stopAuthorModelsServer();
                stopExampleArchivesServer();
                stopActiveX3dModelServers(); // ArrayList
                message = "*** X3D-Edit shutdownHook complete.";
                System.out.println(message);
                System.out.flush(); // output might briefly appear in console while shutting down, also appears in NetBeans debug console
            }
        });
        autolaunchAllServers (); // must follow runtime and shutdownhook initialization
        updateIndicationsPortsBoundOnServers();
        checkActiveX3dModelListSizes("X3dToXhtmlDomConversionFrame constructor");
        
        // ------ legacy code follows ------
        
        // refactorying in progress
//          oldStartJavaServerButton.setVisible(false);
//        oldStartPythonServerButton.setVisible(false);
        
        // used in parent X3dToXhtmlDomConversionAction and X3dToXhtmlDomConversionPanel
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
        
//        if (xhtmlX3domAction.getPlayer().equalsIgnoreCase("Cobweb") || xhtmlX3domAction.getPlayer().equalsIgnoreCase(X_ITE_name))
//        {
//            pageIntegrationTabbedPane.setSelectedIndex(X_ITE_TAB);
            
//             x_iteRadioButton.setSelected(true);
//             
//            // hide X3DOM widgets
//            setDisplayWidgetsX3DOM (false);
//
//            // show X_ITE widgets
//            setDisplayWidgetsX_ITE (true);
//        }
//        else if (xhtmlX3domAction.getPlayer().equalsIgnoreCase(X3DOM_name))
//        {
//            pageIntegrationTabbedPane.setSelectedIndex(X3DOM_TAB);
            
//            x3domRadioButton.setSelected(true);
//             
//            // show X3DOM widgets
//            setDisplayWidgetsX3DOM (true);
//
//            // hide X_ITE widgets
//            setDisplayWidgetsX_ITE (false);
//        }
//        // might set X_ITE but then want CORS HTTP
//        if (xhtmlX3domAction.getPreferredTab() == CORS_TAB)
//        {
//            pageIntegrationTabbedPane.setSelectedIndex(CORS_TAB);
//        }
        
        // TODO, maybe if someday needed
//        pythonStartButton.setVisible(false);
//         pythonStopButton.setVisible(false);
// 
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

        oldStartJavaServerButton = new javax.swing.JButton();
        oldStartPythonServerButton = new javax.swing.JButton();
        pageIntegrationTabbedPane = new javax.swing.JTabbedPane();
        htmlPanel = new javax.swing.JPanel();
        verticalSpacerLabel1 = new javax.swing.JLabel();
        html5ImageLabel = new javax.swing.JLabel();
        widthLabel = new javax.swing.JLabel();
        widthDescriptionLabel = new javax.swing.JLabel();
        widthTextField = new javax.swing.JTextField();
        heightLabel = new javax.swing.JLabel();
        heightDescriptionLabel = new javax.swing.JLabel();
        heightTextField = new javax.swing.JTextField();
        verticalSpacerLabel2 = new javax.swing.JLabel();
        viewX3d4Html5AnnexButton = new javax.swing.JButton();
        viewX3d4Html5AnnexLabel = new javax.swing.JLabel();
        helpSceneAuthoringHintsHtmlButton = new javax.swing.JButton();
        helpSceneAuthoringHintsHtmlLabel = new javax.swing.JLabel();
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
        horizontalSpacerLabelButtons3 = new javax.swing.JLabel();
        corsPanel = new javax.swing.JPanel();
        localhostHttpServerControlsLabel = new javax.swing.JLabel();
        addressLabel = new javax.swing.JLabel();
        addressComboBox = new javax.swing.JComboBox<>();
        corsHelpButton = new javax.swing.JButton();
        httpLabel = new javax.swing.JLabel();
        authorModelsDirectoryLabel = new javax.swing.JLabel();
        autoLabel1 = new javax.swing.JLabel();
        autolaunchAuthorModelsServerCheckBox = new javax.swing.JCheckBox();
        portLabel1 = new javax.swing.JLabel();
        portAuthorModelsServerTextField = new javax.swing.JTextField();
        startAuthorModelsServerButton = new javax.swing.JButton();
        stopAuthorModelsServerButton = new javax.swing.JButton();
        browseLocalhostAuthorModelsButton = new javax.swing.JButton();
        authorModelsServerStatusLabel = new javax.swing.JLabel();
        authorModelsDirectoryTextField = new javax.swing.JTextField();
        authorModelsDirectoryClearButton = new javax.swing.JButton();
        authorModelsDirectoryChooserButton = new javax.swing.JButton();
        authorModelsDirectoryDefaultButton = new javax.swing.JButton();
        browseHttpSeparator1 = new javax.swing.JSeparator();
        examplesArchiveDescriptionLabel = new javax.swing.JLabel();
        autoLabel2 = new javax.swing.JLabel();
        autolaunchExampleArchivesServerCheckBox = new javax.swing.JCheckBox();
        portLabel2 = new javax.swing.JLabel();
        portExampleArchivesServerTextField = new javax.swing.JTextField();
        startExampleArchivesServerButton = new javax.swing.JButton();
        stopExampleArchivesServerButton = new javax.swing.JButton();
        browseLocalhostExampleArchivesButton = new javax.swing.JButton();
        exampleArchivesServerStatusLabel = new javax.swing.JLabel();
        exampleArchivesDirectoryTextField = new javax.swing.JTextField();
        exampleArchivesDownloadPanelButton = new javax.swing.JButton();
        exampleArchivesDirectoryChooserButton = new javax.swing.JButton();
        browseHttpSeparator3 = new javax.swing.JSeparator();
        activeX3dModelLocationLabel = new javax.swing.JLabel();
        autoLabel3 = new javax.swing.JLabel();
        autolaunchActiveX3dModelServerCheckBox = new javax.swing.JCheckBox();
        portLabel3 = new javax.swing.JLabel();
        portActiveX3dModelServerTextField = new javax.swing.JTextField();
        startActiveX3dModelServerButton = new javax.swing.JButton();
        stopActiveX3dModelServerButton = new javax.swing.JButton();
        browseLocalhostActiveX3dModelsButton = new javax.swing.JButton();
        activeX3dModelServerStatusLabel = new javax.swing.JLabel();
        activeX3dModelDirectoryServerListComboBox = new javax.swing.JComboBox<>();
        activeX3dModelDirectoryClearButton = new javax.swing.JButton();
        activeX3dModelDirectoryChooserButton = new javax.swing.JButton();
        corsDescriptionLabel = new javax.swing.JLabel();
        horizontalSpacerLabelButtons4 = new javax.swing.JLabel();
        downloadPanelLaunchButton = new javax.swing.JButton();
        refreshExampleArchivesButton = new javax.swing.JButton();
        refreshAuthorModelsButton = new javax.swing.JButton();
        refreshActiveX3dModelButton = new javax.swing.JButton();
        horizontalSpacerBottomLabel = new javax.swing.JLabel();
        feedbackButton = new javax.swing.JButton();
        continueButton = new javax.swing.JButton();
        transformModelButton = new javax.swing.JButton();

        org.openide.awt.Mnemonics.setLocalizedText(oldStartJavaServerButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.oldStartJavaServerButton.text")); // NOI18N
        oldStartJavaServerButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.oldStartJavaServerButton.toolTipText")); // NOI18N
        oldStartJavaServerButton.setEnabled(false);
        oldStartJavaServerButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                oldStartJavaServerButtonActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(oldStartPythonServerButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.oldStartPythonServerButton.text")); // NOI18N
        oldStartPythonServerButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.oldStartPythonServerButton.toolTipText")); // NOI18N
        oldStartPythonServerButton.setEnabled(false);
        oldStartPythonServerButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                oldStartPythonServerButtonActionPerformed(evt);
            }
        });

        getContentPane().setLayout(new java.awt.GridBagLayout());

        pageIntegrationTabbedPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pageIntegrationTabbedPane.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.pageIntegrationTabbedPane.toolTipText")); // NOI18N
        pageIntegrationTabbedPane.addChangeListener(new javax.swing.event.ChangeListener()
        {
            public void stateChanged(javax.swing.event.ChangeEvent evt)
            {
                pageIntegrationTabbedPaneStateChanged(evt);
            }
        });

        htmlPanel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.htmlPanel.toolTipText")); // NOI18N
        htmlPanel.setMinimumSize(new java.awt.Dimension(300, 70));
        htmlPanel.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(verticalSpacerLabel1, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.verticalSpacerLabel1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        htmlPanel.add(verticalSpacerLabel1, gridBagConstraints);

        html5ImageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/resources/HTML5_Logo_64.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(html5ImageLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.html5ImageLabel.text")); // NOI18N
        html5ImageLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.html5ImageLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        htmlPanel.add(html5ImageLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(widthLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.widthLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        htmlPanel.add(widthLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(widthDescriptionLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.widthDescriptionLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        htmlPanel.add(widthDescriptionLabel, gridBagConstraints);

        widthTextField.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        widthTextField.setText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.widthTextField.text")); // NOI18N
        widthTextField.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.widthTextField.toolTipText")); // NOI18N
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

        org.openide.awt.Mnemonics.setLocalizedText(heightLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.heightLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        htmlPanel.add(heightLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(heightDescriptionLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.heightDescriptionLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        htmlPanel.add(heightDescriptionLabel, gridBagConstraints);

        heightTextField.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        heightTextField.setText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.heightTextField.text")); // NOI18N
        heightTextField.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.heightTextField.toolTipText")); // NOI18N
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

        org.openide.awt.Mnemonics.setLocalizedText(verticalSpacerLabel2, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.verticalSpacerLabel2.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        htmlPanel.add(verticalSpacerLabel2, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(viewX3d4Html5AnnexButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.viewX3d4Html5AnnexButton.text")); // NOI18N
        viewX3d4Html5AnnexButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.viewX3d4Html5AnnexButton.toolTipText")); // NOI18N
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
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        htmlPanel.add(viewX3d4Html5AnnexButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(viewX3d4Html5AnnexLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.viewX3d4Html5AnnexLabel.text")); // NOI18N
        viewX3d4Html5AnnexLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.viewX3d4Html5AnnexLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        htmlPanel.add(viewX3d4Html5AnnexLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(helpSceneAuthoringHintsHtmlButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.helpSceneAuthoringHintsHtmlButton.text")); // NOI18N
        helpSceneAuthoringHintsHtmlButton.setToolTipText(BaseCustomizer.MAILTO_TOOLTIP);
        helpSceneAuthoringHintsHtmlButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        helpSceneAuthoringHintsHtmlButton.setMaximumSize(new java.awt.Dimension(44, 23));
        helpSceneAuthoringHintsHtmlButton.setMinimumSize(new java.awt.Dimension(44, 23));
        helpSceneAuthoringHintsHtmlButton.setPreferredSize(new java.awt.Dimension(44, 23));
        helpSceneAuthoringHintsHtmlButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                helpSceneAuthoringHintsHtmlButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        htmlPanel.add(helpSceneAuthoringHintsHtmlButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(helpSceneAuthoringHintsHtmlLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.helpSceneAuthoringHintsHtmlLabel.text")); // NOI18N
        helpSceneAuthoringHintsHtmlLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.helpSceneAuthoringHintsHtmlLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        htmlPanel.add(helpSceneAuthoringHintsHtmlLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(verticalSpacerLabelBottom, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.verticalSpacerLabelBottom.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 10.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        htmlPanel.add(verticalSpacerLabelBottom, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(horizontalSpacerLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.horizontalSpacerLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 4.0;
        gridBagConstraints.weighty = 1.0;
        htmlPanel.add(horizontalSpacerLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(horizontalSpacerLabel1, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.horizontalSpacerLabel1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 4.0;
        gridBagConstraints.weighty = 1.0;
        htmlPanel.add(horizontalSpacerLabel1, gridBagConstraints);

        pageIntegrationTabbedPane.addTab(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.htmlPanel.TabConstraints.tabTitle"), new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/resources/HTML5_Logo_32.png")), htmlPanel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.htmlPanel.TabConstraints.tabToolTip")); // NOI18N

        x3domPanel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.x3domPanel.toolTipText")); // NOI18N
        x3domPanel.setLayout(new java.awt.GridBagLayout());

        x3domHeaderLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(x3domHeaderLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.x3domHeaderLabel.text")); // NOI18N
        x3domHeaderLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.x3domHeaderLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3domPanel.add(x3domHeaderLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(x3domLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.x3domLabel.text")); // NOI18N
        x3domLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.x3domLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 10.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3domPanel.add(x3domLabel, gridBagConstraints);

        x3domHomeButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        x3domHomeButton.setForeground(new java.awt.Color(21, 71, 52));
        org.openide.awt.Mnemonics.setLocalizedText(x3domHomeButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.x3domHomeButton.text")); // NOI18N
        x3domHomeButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                x3domHomeButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 3, 3, 3);
        x3domPanel.add(x3domHomeButton, gridBagConstraints);

        x3domHelpButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        x3domHelpButton.setForeground(new java.awt.Color(21, 71, 52));
        org.openide.awt.Mnemonics.setLocalizedText(x3domHelpButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.x3domHelpButton.text")); // NOI18N
        x3domHelpButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                x3domHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 3, 3, 3);
        x3domPanel.add(x3domHelpButton, gridBagConstraints);

        x3domImageLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/resources/x3dom_logo150x44.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(x3domImageLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.x3domImageLabel.text")); // NOI18N
        x3domImageLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.x3domImageLabel.toolTipText")); // NOI18N
        x3domImageLabel.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseReleased(java.awt.event.MouseEvent evt)
            {
                x3domImageLabelMouseReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_END;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 10);
        x3domPanel.add(x3domImageLabel, gridBagConstraints);

        showLogCheckBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(showLogCheckBox, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.showLogCheckBox.text")); // NOI18N
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

        org.openide.awt.Mnemonics.setLocalizedText(showLogLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.showLogLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3domPanel.add(showLogLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(showStatisticsCheckBox, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.showStatisticsCheckBox.text")); // NOI18N
        showStatisticsCheckBox.setActionCommand(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.showStatisticsCheckBox.actionCommand")); // NOI18N
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

        org.openide.awt.Mnemonics.setLocalizedText(showStatisticsLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.showStatisticsLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3domPanel.add(showStatisticsLabel, gridBagConstraints);

        showProgressCheckBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(showProgressCheckBox, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.showProgressCheckBox.text")); // NOI18N
        showProgressCheckBox.setActionCommand(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.showProgressCheckBox.actionCommand")); // NOI18N
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

        org.openide.awt.Mnemonics.setLocalizedText(showProgressLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.showProgressLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3domPanel.add(showProgressLabel, gridBagConstraints);

        primitiveQualityLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(primitiveQualityLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.primitiveQualityLabel.text")); // NOI18N
        primitiveQualityLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.primitiveQualityLabel.toolTipText")); // NOI18N
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

        org.openide.awt.Mnemonics.setLocalizedText(primitiveQualityDescriptionLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.primitiveQualityDescriptionLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3domPanel.add(primitiveQualityDescriptionLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(x3domDescriptionLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.x3domDescriptionLabel.text")); // NOI18N
        x3domDescriptionLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.x3domDescriptionLabel.toolTipText")); // NOI18N
        x3domDescriptionLabel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        x3domDescriptionLabel.setMaximumSize(new java.awt.Dimension(250, 48));
        x3domDescriptionLabel.setMinimumSize(new java.awt.Dimension(116, 32));
        x3domDescriptionLabel.setPreferredSize(new java.awt.Dimension(116, 32));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3domPanel.add(x3domDescriptionLabel, gridBagConstraints);

        pageIntegrationTabbedPane.addTab(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.x3domPanel.TabConstraints.tabTitle"), new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/resources/x3dom-whiteOnblue32.png")), x3domPanel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.x3domPanel.TabConstraints.tabToolTip")); // NOI18N

        x_itePanel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.x_itePanel.toolTipText")); // NOI18N
        x_itePanel.setLayout(new java.awt.GridBagLayout());

        x_iteHeaderLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(x_iteHeaderLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.x_iteHeaderLabel.text")); // NOI18N
        x_iteHeaderLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.x_iteHeaderLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 10.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 3, 3, 3);
        x_itePanel.add(x_iteHeaderLabel, gridBagConstraints);

        x_iteLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/resources/x_ite_logo64x64.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(x_iteLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.x_iteLabel.text")); // NOI18N
        x_iteLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.x_iteLabel.toolTipText")); // NOI18N
        x_iteLabel.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseReleased(java.awt.event.MouseEvent evt)
            {
                x_iteLabelMouseReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x_itePanel.add(x_iteLabel, gridBagConstraints);

        x_iteHomeButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        x_iteHomeButton.setForeground(new java.awt.Color(21, 71, 52));
        org.openide.awt.Mnemonics.setLocalizedText(x_iteHomeButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.x_iteHomeButton.text")); // NOI18N
        x_iteHomeButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.x_iteHomeButton.toolTipText")); // NOI18N
        x_iteHomeButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                x_iteHomeButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 3, 3, 3);
        x_itePanel.add(x_iteHomeButton, gridBagConstraints);

        x_iteHelpButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        x_iteHelpButton.setForeground(new java.awt.Color(21, 71, 52));
        org.openide.awt.Mnemonics.setLocalizedText(x_iteHelpButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.x_iteHelpButton.text")); // NOI18N
        x_iteHelpButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.x_iteHelpButton.toolTipText")); // NOI18N
        x_iteHelpButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                x_iteHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 3, 3, 3);
        x_itePanel.add(x_iteHelpButton, gridBagConstraints);

        cacheCheckBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(cacheCheckBox, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.cacheCheckBox.text")); // NOI18N
        cacheCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.cacheCheckBox.toolTipText")); // NOI18N
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
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        x_itePanel.add(cacheCheckBox, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(cacheDescriptionLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.cacheDescriptionLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x_itePanel.add(cacheDescriptionLabel, gridBagConstraints);

        urlLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(urlLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.urlLabel.text")); // NOI18N
        urlLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.urlLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 0, 3);
        x_itePanel.add(urlLabel, gridBagConstraints);

        urlList.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        urlList.setMinimumSize(new java.awt.Dimension(600, 120));
        urlList.setPreferredSize(new java.awt.Dimension(620, 250));
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
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x_itePanel.add(urlList, gridBagConstraints);

        x_iteDescriptionLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(x_iteDescriptionLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.x_iteDescriptionLabel.text")); // NOI18N
        x_iteDescriptionLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.x_iteDescriptionLabel.toolTipText")); // NOI18N
        x_iteDescriptionLabel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        x_iteDescriptionLabel.setMaximumSize(new java.awt.Dimension(250, 48));
        x_iteDescriptionLabel.setMinimumSize(new java.awt.Dimension(116, 32));
        x_iteDescriptionLabel.setPreferredSize(new java.awt.Dimension(116, 32));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x_itePanel.add(x_iteDescriptionLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(horizontalSpacerLabelButtons3, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.horizontalSpacerLabelButtons3.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.weightx = 10.0;
        x_itePanel.add(horizontalSpacerLabelButtons3, gridBagConstraints);

        pageIntegrationTabbedPane.addTab(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.x_itePanel.TabConstraints.tabTitle"), new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/resources/x_ite_logo32x32.png")), x_itePanel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.x_itePanel.TabConstraints.tabToolTip")); // NOI18N

        corsPanel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.corsPanel.toolTipText")); // NOI18N
        corsPanel.addFocusListener(new java.awt.event.FocusAdapter()
        {
            public void focusGained(java.awt.event.FocusEvent evt)
            {
                corsPanelFocusGained(evt);
            }
        });
        corsPanel.addComponentListener(new java.awt.event.ComponentAdapter()
        {
            public void componentShown(java.awt.event.ComponentEvent evt)
            {
                corsPanelComponentShown(evt);
            }
        });
        corsPanel.setLayout(new java.awt.GridBagLayout());

        localhostHttpServerControlsLabel.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        localhostHttpServerControlsLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        org.openide.awt.Mnemonics.setLocalizedText(localhostHttpServerControlsLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.localhostHttpServerControlsLabel.text")); // NOI18N
        localhostHttpServerControlsLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.localhostHttpServerControlsLabel.toolTipText")); // NOI18N
        localhostHttpServerControlsLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        corsPanel.add(localhostHttpServerControlsLabel, gridBagConstraints);

        addressLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(addressLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.addressLabel.text")); // NOI18N
        addressLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.addressLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(addressLabel, gridBagConstraints);

        addressComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "localhost", "0.0.0.0", "127.0.0.1" }));
        addressComboBox.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.addressComboBox.toolTipText")); // NOI18N
        addressComboBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                addressComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(addressComboBox, gridBagConstraints);

        corsHelpButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        corsHelpButton.setForeground(new java.awt.Color(21, 71, 52));
        org.openide.awt.Mnemonics.setLocalizedText(corsHelpButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.corsHelpButton.text")); // NOI18N
        corsHelpButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                corsHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(corsHelpButton, gridBagConstraints);

        httpLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/resources/HTTP_logo.svg.64x34.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(httpLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.httpLabel.text")); // NOI18N
        httpLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.httpLabel.toolTipText")); // NOI18N
        httpLabel.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseReleased(java.awt.event.MouseEvent evt)
            {
                httpLabelMouseReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(httpLabel, gridBagConstraints);

        authorModelsDirectoryLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(authorModelsDirectoryLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.authorModelsDirectoryLabel.text")); // NOI18N
        authorModelsDirectoryLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.authorModelsDirectoryLabel.toolTipText")); // NOI18N
        authorModelsDirectoryLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        authorModelsDirectoryLabel.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseReleased(java.awt.event.MouseEvent evt)
            {
                authorModelsDirectoryLabelMouseReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 24;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(authorModelsDirectoryLabel, gridBagConstraints);

        autoLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(autoLabel1, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.autoLabel1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(autoLabel1, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(autolaunchAuthorModelsServerCheckBox, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.autolaunchAuthorModelsServerCheckBox.text")); // NOI18N
        autolaunchAuthorModelsServerCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.autolaunchAuthorModelsServerCheckBox.toolTipText")); // NOI18N
        autolaunchAuthorModelsServerCheckBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        autolaunchAuthorModelsServerCheckBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                autolaunchAuthorModelsServerCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(autolaunchAuthorModelsServerCheckBox, gridBagConstraints);

        portLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(portLabel1, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.portLabel1.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        corsPanel.add(portLabel1, gridBagConstraints);

        portAuthorModelsServerTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        portAuthorModelsServerTextField.setText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.portAuthorModelsServerTextField.text")); // NOI18N
        portAuthorModelsServerTextField.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.portAuthorModelsServerTextField.toolTipText")); // NOI18N
        portAuthorModelsServerTextField.setMaximumSize(new java.awt.Dimension(60, 22));
        portAuthorModelsServerTextField.setMinimumSize(new java.awt.Dimension(20, 22));
        portAuthorModelsServerTextField.setPreferredSize(new java.awt.Dimension(20, 22));
        portAuthorModelsServerTextField.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                portAuthorModelsServerTextFieldMouseExited(evt);
            }
        });
        portAuthorModelsServerTextField.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                portAuthorModelsServerTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 32;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(portAuthorModelsServerTextField, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(startAuthorModelsServerButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.startAuthorModelsServerButton.text")); // NOI18N
        startAuthorModelsServerButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.startAuthorModelsServerButton.toolTipText")); // NOI18N
        startAuthorModelsServerButton.setMargin(new java.awt.Insets(3, 3, 3, 3));
        startAuthorModelsServerButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                startAuthorModelsServerButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 24;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(startAuthorModelsServerButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(stopAuthorModelsServerButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.stopAuthorModelsServerButton.text")); // NOI18N
        stopAuthorModelsServerButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.stopAuthorModelsServerButton.toolTipText")); // NOI18N
        stopAuthorModelsServerButton.setEnabled(false);
        stopAuthorModelsServerButton.setMargin(new java.awt.Insets(3, 3, 3, 3));
        stopAuthorModelsServerButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                stopAuthorModelsServerButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 24;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(stopAuthorModelsServerButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(browseLocalhostAuthorModelsButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.browseLocalhostAuthorModelsButton.text")); // NOI18N
        browseLocalhostAuthorModelsButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.browseLocalhostAuthorModelsButton.toolTipText")); // NOI18N
        browseLocalhostAuthorModelsButton.setMargin(new java.awt.Insets(3, 3, 3, 3));
        browseLocalhostAuthorModelsButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                browseLocalhostAuthorModelsButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(browseLocalhostAuthorModelsButton, gridBagConstraints);

        authorModelsServerStatusLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/resources/circleGrey24x24.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(authorModelsServerStatusLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.authorModelsServerStatusLabel.text")); // NOI18N
        authorModelsServerStatusLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.authorModelsServerStatusLabel.toolTipText")); // NOI18N
        authorModelsServerStatusLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        authorModelsServerStatusLabel.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseReleased(java.awt.event.MouseEvent evt)
            {
                authorModelsServerStatusLabelMouseReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(authorModelsServerStatusLabel, gridBagConstraints);

        authorModelsDirectoryTextField.setText(org.web3d.x3d.options.X3dEditUserPreferences.getExampleArchivesRootDirectory());
        authorModelsDirectoryTextField.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.authorModelsDirectoryTextField.toolTipText")); // NOI18N
        authorModelsDirectoryTextField.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                authorModelsDirectoryTextFieldMouseExited(evt);
            }
        });
        authorModelsDirectoryTextField.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                authorModelsDirectoryTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(authorModelsDirectoryTextField, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(authorModelsDirectoryClearButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.authorModelsDirectoryClearButton.text")); // NOI18N
        authorModelsDirectoryClearButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.authorModelsDirectoryClearButton.toolTipText")); // NOI18N
        authorModelsDirectoryClearButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        authorModelsDirectoryClearButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                authorModelsDirectoryClearButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        corsPanel.add(authorModelsDirectoryClearButton, gridBagConstraints);

        authorModelsDirectoryChooserButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(authorModelsDirectoryChooserButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.authorModelsDirectoryChooserButton.text")); // NOI18N
        authorModelsDirectoryChooserButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.authorModelsDirectoryChooserButton.toolTipText")); // NOI18N
        authorModelsDirectoryChooserButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        authorModelsDirectoryChooserButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                authorModelsDirectoryChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 11;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        corsPanel.add(authorModelsDirectoryChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(authorModelsDirectoryDefaultButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.authorModelsDirectoryDefaultButton.text")); // NOI18N
        authorModelsDirectoryDefaultButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.authorModelsDirectoryDefaultButton.toolTipText")); // NOI18N
        authorModelsDirectoryDefaultButton.setMargin(new java.awt.Insets(3, 3, 3, 3));
        authorModelsDirectoryDefaultButton.setMinimumSize(new java.awt.Dimension(24, 24));
        authorModelsDirectoryDefaultButton.setPreferredSize(new java.awt.Dimension(48, 24));
        authorModelsDirectoryDefaultButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                authorModelsDirectoryDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 12;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        corsPanel.add(authorModelsDirectoryDefaultButton, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 1, 2, 1);
        corsPanel.add(browseHttpSeparator1, gridBagConstraints);

        examplesArchiveDescriptionLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(examplesArchiveDescriptionLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.examplesArchiveDescriptionLabel.text")); // NOI18N
        examplesArchiveDescriptionLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.examplesArchiveDescriptionLabel.toolTipText")); // NOI18N
        examplesArchiveDescriptionLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        examplesArchiveDescriptionLabel.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseReleased(java.awt.event.MouseEvent evt)
            {
                examplesArchiveDescriptionLabelMouseReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 24;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(examplesArchiveDescriptionLabel, gridBagConstraints);

        autoLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(autoLabel2, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.autoLabel2.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(autoLabel2, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(autolaunchExampleArchivesServerCheckBox, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.autolaunchExampleArchivesServerCheckBox.text")); // NOI18N
        autolaunchExampleArchivesServerCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.autolaunchExampleArchivesServerCheckBox.toolTipText")); // NOI18N
        autolaunchExampleArchivesServerCheckBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        autolaunchExampleArchivesServerCheckBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                autolaunchExampleArchivesServerCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(autolaunchExampleArchivesServerCheckBox, gridBagConstraints);

        portLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(portLabel2, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.portLabel2.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        corsPanel.add(portLabel2, gridBagConstraints);

        portExampleArchivesServerTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        portExampleArchivesServerTextField.setText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.portExampleArchivesServerTextField.text")); // NOI18N
        portExampleArchivesServerTextField.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.portExampleArchivesServerTextField.toolTipText")); // NOI18N
        portExampleArchivesServerTextField.setMaximumSize(new java.awt.Dimension(60, 22));
        portExampleArchivesServerTextField.setMinimumSize(new java.awt.Dimension(20, 22));
        portExampleArchivesServerTextField.setPreferredSize(new java.awt.Dimension(20, 22));
        portExampleArchivesServerTextField.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                portExampleArchivesServerTextFieldMouseExited(evt);
            }
        });
        portExampleArchivesServerTextField.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                portExampleArchivesServerTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 32;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(portExampleArchivesServerTextField, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(startExampleArchivesServerButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.startExampleArchivesServerButton.text")); // NOI18N
        startExampleArchivesServerButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.startExampleArchivesServerButton.toolTipText")); // NOI18N
        startExampleArchivesServerButton.setMargin(new java.awt.Insets(3, 3, 3, 3));
        startExampleArchivesServerButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                startExampleArchivesServerButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 24;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(startExampleArchivesServerButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(stopExampleArchivesServerButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.stopExampleArchivesServerButton.text")); // NOI18N
        stopExampleArchivesServerButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.stopExampleArchivesServerButton.toolTipText")); // NOI18N
        stopExampleArchivesServerButton.setEnabled(false);
        stopExampleArchivesServerButton.setMargin(new java.awt.Insets(3, 3, 3, 3));
        stopExampleArchivesServerButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                stopExampleArchivesServerButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 24;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(stopExampleArchivesServerButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(browseLocalhostExampleArchivesButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.browseLocalhostExampleArchivesButton.text")); // NOI18N
        browseLocalhostExampleArchivesButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.browseLocalhostExampleArchivesButton.toolTipText")); // NOI18N
        browseLocalhostExampleArchivesButton.setMargin(new java.awt.Insets(3, 3, 3, 3));
        browseLocalhostExampleArchivesButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                browseLocalhostExampleArchivesButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(browseLocalhostExampleArchivesButton, gridBagConstraints);

        exampleArchivesServerStatusLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/resources/circleGreen24x24.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(exampleArchivesServerStatusLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.exampleArchivesServerStatusLabel.text")); // NOI18N
        exampleArchivesServerStatusLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.exampleArchivesServerStatusLabel.toolTipText")); // NOI18N
        exampleArchivesServerStatusLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        exampleArchivesServerStatusLabel.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseReleased(java.awt.event.MouseEvent evt)
            {
                exampleArchivesServerStatusLabelMouseReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(exampleArchivesServerStatusLabel, gridBagConstraints);

        exampleArchivesDirectoryTextField.setText(org.web3d.x3d.options.X3dEditUserPreferences.getExampleArchivesRootDirectory());
        exampleArchivesDirectoryTextField.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.exampleArchivesDirectoryTextField.toolTipText")); // NOI18N
        exampleArchivesDirectoryTextField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        exampleArchivesDirectoryTextField.setEnabled(false);
        exampleArchivesDirectoryTextField.setMinimumSize(new java.awt.Dimension(64, 22));
        exampleArchivesDirectoryTextField.setPreferredSize(new java.awt.Dimension(85, 22));
        exampleArchivesDirectoryTextField.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                exampleArchivesDirectoryTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(exampleArchivesDirectoryTextField, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(exampleArchivesDownloadPanelButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.exampleArchivesDownloadPanelButton.text")); // NOI18N
        exampleArchivesDownloadPanelButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.exampleArchivesDownloadPanelButton.toolTipText")); // NOI18N
        exampleArchivesDownloadPanelButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        exampleArchivesDownloadPanelButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                exampleArchivesDownloadPanelButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        corsPanel.add(exampleArchivesDownloadPanelButton, gridBagConstraints);

        exampleArchivesDirectoryChooserButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(exampleArchivesDirectoryChooserButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.exampleArchivesDirectoryChooserButton.text")); // NOI18N
        exampleArchivesDirectoryChooserButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.exampleArchivesDirectoryChooserButton.toolTipText")); // NOI18N
        exampleArchivesDirectoryChooserButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        exampleArchivesDirectoryChooserButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                exampleArchivesDirectoryChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 11;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        corsPanel.add(exampleArchivesDirectoryChooserButton, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 1, 2, 1);
        corsPanel.add(browseHttpSeparator3, gridBagConstraints);

        activeX3dModelLocationLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(activeX3dModelLocationLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.activeX3dModelLocationLabel.text")); // NOI18N
        activeX3dModelLocationLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.activeX3dModelLocationLabel.toolTipText")); // NOI18N
        activeX3dModelLocationLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        activeX3dModelLocationLabel.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                activeX3dModelLocationLabelMouseExited(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 24;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(activeX3dModelLocationLabel, gridBagConstraints);

        autoLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(autoLabel3, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.autoLabel3.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(autoLabel3, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(autolaunchActiveX3dModelServerCheckBox, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.autolaunchActiveX3dModelServerCheckBox.text")); // NOI18N
        autolaunchActiveX3dModelServerCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.autolaunchActiveX3dModelServerCheckBox.toolTipText")); // NOI18N
        autolaunchActiveX3dModelServerCheckBox.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        autolaunchActiveX3dModelServerCheckBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                autolaunchActiveX3dModelServerCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(autolaunchActiveX3dModelServerCheckBox, gridBagConstraints);

        portLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(portLabel3, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.portLabel3.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        corsPanel.add(portLabel3, gridBagConstraints);

        portActiveX3dModelServerTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        portActiveX3dModelServerTextField.setText(String.valueOf(INITIAL_ACTIVE_X3D_MODEL_SERVER_PORT));
        portActiveX3dModelServerTextField.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.portActiveX3dModelServerTextField.toolTipText")); // NOI18N
        portActiveX3dModelServerTextField.setMaximumSize(new java.awt.Dimension(60, 22));
        portActiveX3dModelServerTextField.setMinimumSize(new java.awt.Dimension(20, 22));
        portActiveX3dModelServerTextField.setPreferredSize(new java.awt.Dimension(20, 22));
        portActiveX3dModelServerTextField.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                portActiveX3dModelServerTextFieldMouseExited(evt);
            }
        });
        portActiveX3dModelServerTextField.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                portActiveX3dModelServerTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 32;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(portActiveX3dModelServerTextField, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(startActiveX3dModelServerButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.startActiveX3dModelServerButton.text")); // NOI18N
        startActiveX3dModelServerButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.startActiveX3dModelServerButton.toolTipText")); // NOI18N
        startActiveX3dModelServerButton.setMargin(new java.awt.Insets(3, 3, 3, 3));
        startActiveX3dModelServerButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                startActiveX3dModelServerButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 24;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(startActiveX3dModelServerButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(stopActiveX3dModelServerButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.stopActiveX3dModelServerButton.text")); // NOI18N
        stopActiveX3dModelServerButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.stopActiveX3dModelServerButton.toolTipText")); // NOI18N
        stopActiveX3dModelServerButton.setMargin(new java.awt.Insets(3, 3, 3, 3));
        stopActiveX3dModelServerButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                stopActiveX3dModelServerButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 24;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(stopActiveX3dModelServerButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(browseLocalhostActiveX3dModelsButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.browseLocalhostActiveX3dModelsButton.text")); // NOI18N
        browseLocalhostActiveX3dModelsButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.browseLocalhostActiveX3dModelsButton.toolTipText")); // NOI18N
        browseLocalhostActiveX3dModelsButton.setMargin(new java.awt.Insets(3, 3, 3, 3));
        browseLocalhostActiveX3dModelsButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                browseLocalhostActiveX3dModelsButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(browseLocalhostActiveX3dModelsButton, gridBagConstraints);

        activeX3dModelServerStatusLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/resources/circleRed24x24.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(activeX3dModelServerStatusLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.activeX3dModelServerStatusLabel.text")); // NOI18N
        activeX3dModelServerStatusLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.exampleArchivesServerStatusLabel.toolTipText")); // NOI18N
        activeX3dModelServerStatusLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        activeX3dModelServerStatusLabel.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseReleased(java.awt.event.MouseEvent evt)
            {
                activeX3dModelServerStatusLabelMouseReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(activeX3dModelServerStatusLabel, gridBagConstraints);

        activeX3dModelDirectoryServerListComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "This combo box lists model directories that have localhost http servers running" }));
        activeX3dModelDirectoryServerListComboBox.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.activeX3dModelDirectoryServerListComboBox.toolTipText")); // NOI18N
        activeX3dModelDirectoryServerListComboBox.addItemListener(new java.awt.event.ItemListener()
        {
            public void itemStateChanged(java.awt.event.ItemEvent evt)
            {
                activeX3dModelDirectoryServerListComboBoxItemStateChanged(evt);
            }
        });
        activeX3dModelDirectoryServerListComboBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                activeX3dModelDirectoryServerListComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(activeX3dModelDirectoryServerListComboBox, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(activeX3dModelDirectoryClearButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.activeX3dModelDirectoryClearButton.text")); // NOI18N
        activeX3dModelDirectoryClearButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.activeX3dModelDirectoryClearButton.toolTipText")); // NOI18N
        activeX3dModelDirectoryClearButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        activeX3dModelDirectoryClearButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                activeX3dModelDirectoryClearButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        corsPanel.add(activeX3dModelDirectoryClearButton, gridBagConstraints);

        activeX3dModelDirectoryChooserButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(activeX3dModelDirectoryChooserButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.activeX3dModelDirectoryChooserButton.text")); // NOI18N
        activeX3dModelDirectoryChooserButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.activeX3dModelDirectoryChooserButton.toolTipText")); // NOI18N
        activeX3dModelDirectoryChooserButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        activeX3dModelDirectoryChooserButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                activeX3dModelDirectoryChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 11;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        corsPanel.add(activeX3dModelDirectoryChooserButton, gridBagConstraints);

        corsDescriptionLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(corsDescriptionLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.corsDescriptionLabel.text")); // NOI18N
        corsDescriptionLabel.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.corsDescriptionLabel.toolTipText")); // NOI18N
        corsDescriptionLabel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        corsDescriptionLabel.setMaximumSize(new java.awt.Dimension(250, 48));
        corsDescriptionLabel.setMinimumSize(new java.awt.Dimension(116, 32));
        corsDescriptionLabel.setPreferredSize(new java.awt.Dimension(116, 32));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.gridwidth = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.ipady = 24;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 3, 3);
        corsPanel.add(corsDescriptionLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(horizontalSpacerLabelButtons4, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.horizontalSpacerLabelButtons4.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.gridwidth = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.weightx = 10.0;
        corsPanel.add(horizontalSpacerLabelButtons4, gridBagConstraints);

        downloadPanelLaunchButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/resources/Icon_download_32x32.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(downloadPanelLaunchButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.downloadPanelLaunchButton.text")); // NOI18N
        downloadPanelLaunchButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.downloadPanelLaunchButton.toolTipText")); // NOI18N
        downloadPanelLaunchButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        downloadPanelLaunchButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                downloadPanelLaunchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 13;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(downloadPanelLaunchButton, gridBagConstraints);

        refreshExampleArchivesButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/resources/Icon_refresh_24x24.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(refreshExampleArchivesButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.refreshActiveX3dModelButton.text")); // NOI18N
        refreshExampleArchivesButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.refreshActiveX3dModelButton.toolTipText")); // NOI18N
        refreshExampleArchivesButton.setMinimumSize(new java.awt.Dimension(26, 26));
        refreshExampleArchivesButton.setPreferredSize(new java.awt.Dimension(26, 26));
        refreshExampleArchivesButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        refreshExampleArchivesButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                refreshExampleArchivesButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LAST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(refreshExampleArchivesButton, gridBagConstraints);

        refreshAuthorModelsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/resources/Icon_refresh_24x24.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(refreshAuthorModelsButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.refreshActiveX3dModelButton.text")); // NOI18N
        refreshAuthorModelsButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.refreshActiveX3dModelButton.toolTipText")); // NOI18N
        refreshAuthorModelsButton.setMinimumSize(new java.awt.Dimension(26, 26));
        refreshAuthorModelsButton.setPreferredSize(new java.awt.Dimension(26, 26));
        refreshAuthorModelsButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        refreshAuthorModelsButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                refreshAuthorModelsButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LAST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(refreshAuthorModelsButton, gridBagConstraints);

        refreshActiveX3dModelButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/resources/Icon_refresh_24x24.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(refreshActiveX3dModelButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.refreshActiveX3dModelButton.text")); // NOI18N
        refreshActiveX3dModelButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.refreshActiveX3dModelButton.toolTipText")); // NOI18N
        refreshActiveX3dModelButton.setMinimumSize(new java.awt.Dimension(26, 26));
        refreshActiveX3dModelButton.setPreferredSize(new java.awt.Dimension(26, 26));
        refreshActiveX3dModelButton.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
        refreshActiveX3dModelButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                refreshActiveX3dModelButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LAST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        corsPanel.add(refreshActiveX3dModelButton, gridBagConstraints);

        pageIntegrationTabbedPane.addTab(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.corsPanel.TabConstraints.tabTitle"), new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/resources/HTTP_logo.svg.32x16.png")), corsPanel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.corsPanel.TabConstraints.tabToolTip")); // NOI18N

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
        getContentPane().add(pageIntegrationTabbedPane, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(horizontalSpacerBottomLabel, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.horizontalSpacerBottomLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.weightx = 20.0;
        getContentPane().add(horizontalSpacerBottomLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(feedbackButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.feedbackButton.text")); // NOI18N
        feedbackButton.setToolTipText(BaseCustomizer.MAILTO_TOOLTIP);
        feedbackButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        feedbackButton.setMaximumSize(new java.awt.Dimension(55, 23));
        feedbackButton.setMinimumSize(new java.awt.Dimension(55, 23));
        feedbackButton.setPreferredSize(new java.awt.Dimension(55, 23));
        feedbackButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                feedbackButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        getContentPane().add(feedbackButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(continueButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.continueButton.text")); // NOI18N
        continueButton.setToolTipText(BaseCustomizer.MAILTO_TOOLTIP);
        continueButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        continueButton.setMaximumSize(new java.awt.Dimension(55, 23));
        continueButton.setMinimumSize(new java.awt.Dimension(55, 23));
        continueButton.setPreferredSize(new java.awt.Dimension(55, 23));
        continueButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                continueButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        getContentPane().add(continueButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(transformModelButton, org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.transformModelButton.text")); // NOI18N
        transformModelButton.setToolTipText(org.openide.util.NbBundle.getMessage(X3dToXhtmlDomConversionFrame.class, "X3dToXhtmlDomConversionFrame.transformModelButton.toolTipText")); // NOI18N
        transformModelButton.setMargin(new java.awt.Insets(0, 0, 0, 0));
        transformModelButton.setMaximumSize(new java.awt.Dimension(55, 23));
        transformModelButton.setMinimumSize(new java.awt.Dimension(55, 23));
        transformModelButton.setPreferredSize(new java.awt.Dimension(55, 23));
        transformModelButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                transformModelButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 48;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        getContentPane().add(transformModelButton, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void widthTextFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_widthTextFieldActionPerformed
    {//GEN-HEADEREND:event_widthTextFieldActionPerformed
        // TODO change persistence to X3dEditUserPreferences
        // xhtmlX3domAction.setX3dWidth(widthTextField.getText());
    }//GEN-LAST:event_widthTextFieldActionPerformed

    private void heightTextFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_heightTextFieldActionPerformed
    {//GEN-HEADEREND:event_heightTextFieldActionPerformed
        // TODO change persistence to X3dEditUserPreferences
        // xhtmlX3domAction.setX3dHeight(heightTextField.getText());
    }//GEN-LAST:event_heightTextFieldActionPerformed

    private void viewX3d4Html5AnnexButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_viewX3d4Html5AnnexButtonActionPerformed
    {//GEN-HEADEREND:event_viewX3d4Html5AnnexButtonActionPerformed
        openInBrowser(X3D4_HTML_AUTHORING_GUIDELINES);
    }//GEN-LAST:event_viewX3d4Html5AnnexButtonActionPerformed

    private void helpSceneAuthoringHintsHtmlButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_helpSceneAuthoringHintsHtmlButtonActionPerformed
    {//GEN-HEADEREND:event_helpSceneAuthoringHintsHtmlButtonActionPerformed
        X3dEditUserPreferencesPanel.browserLaunch(X3D_SCENE_AUTHORING_HINTS + "#HTML");
    }//GEN-LAST:event_helpSceneAuthoringHintsHtmlButtonActionPerformed

    private void feedbackButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_feedbackButtonActionPerformed
    {//GEN-HEADEREND:event_feedbackButtonActionPerformed
        switch (pageIntegrationTabbedPane.getSelectedIndex())
        {
            case HTML_LAYOUT_TAB: 
                X3dEditUserPreferencesPanel.feedbackButtonSend ("X3D4 Model Integration in HTML5 Web Page: HTML page integration pane");
                break;
            case X3DOM_TAB: 
                X3dEditUserPreferencesPanel.feedbackButtonSend ("X3D4 Model Integration in HTML5 Web Page: X3DOM pane");
                break;
            case X_ITE_TAB: 
                X3dEditUserPreferencesPanel.feedbackButtonSend ("X3D4 Model Integration in HTML5 Web Page: X_ITE pane");
                break;
            case CORS_TAB: 
                X3dEditUserPreferencesPanel.feedbackButtonSend ("X3D4 Model Integration in HTML5 Web Page: CORS localhost http server pane");
                break;
        }
    }//GEN-LAST:event_feedbackButtonActionPerformed

    private void x3domHomeButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_x3domHomeButtonActionPerformed
    {//GEN-HEADEREND:event_x3domHomeButtonActionPerformed
        BaseViewAction.sendBrowserTo(X3DOM_site);
    }//GEN-LAST:event_x3domHomeButtonActionPerformed

    private void x3domHelpButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_x3domHelpButtonActionPerformed
    {//GEN-HEADEREND:event_x3domHelpButtonActionPerformed
        BaseViewAction.sendBrowserTo(X3DOM_help);
    }//GEN-LAST:event_x3domHelpButtonActionPerformed

    private void showLogCheckBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_showLogCheckBoxActionPerformed
    {//GEN-HEADEREND:event_showLogCheckBoxActionPerformed
        // TODO change persistence to X3dEditUserPreferences
        // xhtmlX3domAction.setShowLog(showLogCheckBox.isSelected());
    }//GEN-LAST:event_showLogCheckBoxActionPerformed

    private void showStatisticsCheckBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_showStatisticsCheckBoxActionPerformed
    {//GEN-HEADEREND:event_showStatisticsCheckBoxActionPerformed
        // TODO change persistence to X3dEditUserPreferences
        // xhtmlX3domAction.setShowStatistics(showStatisticsCheckBox.isSelected());
    }//GEN-LAST:event_showStatisticsCheckBoxActionPerformed

    private void showProgressCheckBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_showProgressCheckBoxActionPerformed
    {//GEN-HEADEREND:event_showProgressCheckBoxActionPerformed
        // TODO change persistence to X3dEditUserPreferences
        // xhtmlX3domAction.setShowProgress(showProgressCheckBox.isSelected());
    }//GEN-LAST:event_showProgressCheckBoxActionPerformed

    private void primitiveQualityComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_primitiveQualityComboBoxActionPerformed
    {//GEN-HEADEREND:event_primitiveQualityComboBoxActionPerformed
        // TODO change persistence to X3dEditUserPreferences
        // xhtmlX3domAction.setPrimitiveQuality( primitiveQualityComboBox.getSelectedItem().toString());
    }//GEN-LAST:event_primitiveQualityComboBoxActionPerformed

    private void x_iteHomeButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_x_iteHomeButtonActionPerformed
    {//GEN-HEADEREND:event_x_iteHomeButtonActionPerformed
        BaseViewAction.sendBrowserTo(X_ITE_site);
    }//GEN-LAST:event_x_iteHomeButtonActionPerformed

    private void x_iteHelpButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_x_iteHelpButtonActionPerformed
    {//GEN-HEADEREND:event_x_iteHelpButtonActionPerformed
        BaseViewAction.sendBrowserTo(X_ITE_help);
    }//GEN-LAST:event_x_iteHelpButtonActionPerformed

    private void cacheCheckBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cacheCheckBoxActionPerformed
    {//GEN-HEADEREND:event_cacheCheckBoxActionPerformed
        // TODO change persistence to X3dEditUserPreferences
        // xhtmlX3domAction.setCache(cacheCheckBox.isSelected());
    }//GEN-LAST:event_cacheCheckBoxActionPerformed

    private void urlListPropertyChange(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_urlListPropertyChange
    {//GEN-HEADEREND:event_urlListPropertyChange

    }//GEN-LAST:event_urlListPropertyChange

    private void addressComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_addressComboBoxActionPerformed
    {//GEN-HEADEREND:event_addressComboBoxActionPerformed
        addressValue = addressComboBox.getSelectedItem().toString();
    }//GEN-LAST:event_addressComboBoxActionPerformed

    private void corsHelpButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_corsHelpButtonActionPerformed
    {//GEN-HEADEREND:event_corsHelpButtonActionPerformed
        LaunchX3dSceneAuthoringHintsCorsAction launchCorsAction = new LaunchX3dSceneAuthoringHintsCorsAction();
        launchCorsAction.performAction();
    }//GEN-LAST:event_corsHelpButtonActionPerformed

    private void autolaunchAuthorModelsServerCheckBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_autolaunchAuthorModelsServerCheckBoxActionPerformed
    {//GEN-HEADEREND:event_autolaunchAuthorModelsServerCheckBoxActionPerformed
        X3dEditUserPreferences.setAuthorModelsServerAutolaunch(autolaunchAuthorModelsServerCheckBox.isSelected());
    }//GEN-LAST:event_autolaunchAuthorModelsServerCheckBoxActionPerformed

    private void portAuthorModelsServerTextFieldMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_portAuthorModelsServerTextFieldMouseExited
    {//GEN-HEADEREND:event_portAuthorModelsServerTextFieldMouseExited
        portAuthorModelsServerCheck();
    }//GEN-LAST:event_portAuthorModelsServerTextFieldMouseExited

    private void portAuthorModelsServerCheck()
    {
        // TODO check values 8000..? and not duplicated
        if (portAuthorModelsServerTextField.getText().isBlank())
        {
            System.out.println("*** Resetting blank port for AuthorModelsServer to default " + X3dEditUserPreferences.AUTHOR_MODELS_SERVER_PORT_DEFAULT);
            portAuthorModelsServerTextField.setText(X3dEditUserPreferences.AUTHOR_MODELS_SERVER_PORT_DEFAULT);
        }
        if (!X3dEditUserPreferences.getAuthorModelsServerPort().equals(portAuthorModelsServerTextField.getText().trim()))
        {
            X3dEditUserPreferences.setAuthorModelsServerPort(portAuthorModelsServerTextField.getText().trim());
            if (isAliveAuthorModelsServer)
            {
                // notify user that file location is unworkable
                message = "AuthorModelsServer is already running, be sure to restart localhost http server to expose new port=" + portAuthorModelsServerTextField.getText().trim();
                System.out.println("*** " + message);
                portAuthorModelsServerTextField.setBackground(Color.yellow);
                NotifyDescriptor notifyDescriptor = new NotifyDescriptor.Message(message, NotifyDescriptor.INFORMATION_MESSAGE);
                DialogDisplayer.getDefault().notify(notifyDescriptor);
            }
        }
        updateIndicationsPortsBoundOnServers();
    }
    private void portAuthorModelsServerTextFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_portAuthorModelsServerTextFieldActionPerformed
    {//GEN-HEADEREND:event_portAuthorModelsServerTextFieldActionPerformed
        portAuthorModelsServerCheck();
    }//GEN-LAST:event_portAuthorModelsServerTextFieldActionPerformed

    private void startAuthorModelsServerButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_startAuthorModelsServerButtonActionPerformed
    {//GEN-HEADEREND:event_startAuthorModelsServerButtonActionPerformed
        startAuthorModelsServer ();
        if (isAliveAuthorModelsServer || isPortBound(Integer.parseInt(X3dEditUserPreferences.getAuthorModelsServerPort())))
        {
            autolaunchAuthorModelsServerCheckBox.setSelected(X3dEditUserPreferences.isAuthorModelsServerAutolaunch());
            if (authorModelsHttpServerProcess != null)
            {
                startAuthorModelsServerButton.setText(HTTP_RUNNING);
                startAuthorModelsServerButton.setForeground(darkgreen);
                startAuthorModelsServerButton.setFont(boldFont);
                startAuthorModelsServerButton.setEnabled(true);

                  stopAuthorModelsServerButton.setEnabled(true);
                  stopAuthorModelsServerButton.setText(HTTP_STOP);
                  stopAuthorModelsServerButton.setForeground(Color.RED); // also BOLD
            }
            else
            {
                startAuthorModelsServerButton.setText(HTTP_BLOCKED);
                startAuthorModelsServerButton.setForeground(darkorange);
                startAuthorModelsServerButton.setEnabled(false);
                 stopAuthorModelsServerButton.setEnabled(false);
                 stopAuthorModelsServerButton.setForeground(Color.BLACK);
            }
        }
        updateIndicationsPortsBoundOnServers();
        portAuthorModelsServerTextField.setBackground(Color.white);
         authorModelsDirectoryTextField.setBackground(Color.white);
    }//GEN-LAST:event_startAuthorModelsServerButtonActionPerformed

    private void stopAuthorModelsServerButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_stopAuthorModelsServerButtonActionPerformed
    {//GEN-HEADEREND:event_stopAuthorModelsServerButtonActionPerformed
        stopAuthorModelsServer();
        startAuthorModelsServerButton.setText(HTTP_START);
        startAuthorModelsServerButton.setForeground(black);
        startAuthorModelsServerButton.setFont(plainFont);
        startAuthorModelsServerButton.setEnabled(true);
        
        stopAuthorModelsServerButton.setEnabled(false);
        stopAuthorModelsServerButton.setForeground(black);
        portAuthorModelsServerTextField.setBackground(Color.white);
         authorModelsDirectoryTextField.setBackground(Color.white);
        updateIndicationsPortsBoundOnServers();
    }//GEN-LAST:event_stopAuthorModelsServerButtonActionPerformed

    private void browseLocalhostAuthorModelsButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_browseLocalhostAuthorModelsButtonActionPerformed
    {//GEN-HEADEREND:event_browseLocalhostAuthorModelsButtonActionPerformed
        checkAuthorModelsDirectoryAutolaunch ();
        int portValue = Integer.parseInt(portAuthorModelsServerTextField.getText());
        if (addressValue.isBlank())
            addressValue = "localhost";
        // getAuthorCorsDirectory() should be root of query to https://localhost:8001
        String localRootAddress = "http://" + addressValue + ":" + portValue;
        openInBrowser(localRootAddress);
    }//GEN-LAST:event_browseLocalhostAuthorModelsButtonActionPerformed

    private void authorModelsServerStatusLabelMouseReleased(java.awt.event.MouseEvent evt)//GEN-FIRST:event_authorModelsServerStatusLabelMouseReleased
    {//GEN-HEADEREND:event_authorModelsServerStatusLabelMouseReleased
        updateIndicationsPortsBoundOnServers(); // refresh
        updateValuesInPanel();
    }//GEN-LAST:event_authorModelsServerStatusLabelMouseReleased

    private void authorModelsDirectoryTextFieldMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_authorModelsDirectoryTextFieldMouseExited
    {//GEN-HEADEREND:event_authorModelsDirectoryTextFieldMouseExited
        X3dEditUserPreferences.setAuthorModelsDirectory(authorModelsDirectoryTextField.getText().trim());
    }//GEN-LAST:event_authorModelsDirectoryTextFieldMouseExited

    private void authorModelsDirectoryTextFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_authorModelsDirectoryTextFieldActionPerformed
    {//GEN-HEADEREND:event_authorModelsDirectoryTextFieldActionPerformed
        checkAuthorModelsDirectoryAutolaunch ();
    }//GEN-LAST:event_authorModelsDirectoryTextFieldActionPerformed

    private void checkAuthorModelsDirectoryAutolaunch ()
    {
        if (!X3dEditUserPreferences.isAuthorModelsServerAutolaunch())
        {
            X3dEditUserPreferences.setAuthorModelsServerAutolaunch(true);
            message = "AuthorModelsServer autoLaunch now set true, starting in directoryLocation='" + authorModelsDirectoryTextField.getText().trim() + "'";
            System.out.println("*** " + message);
            if (!isAliveAuthorModelsServer)
                 startAuthorModelsServer();
        }
        if (!X3dEditUserPreferences.getAuthorModelsDirectory().equals(authorModelsDirectoryTextField.getText().trim()))
        {
            X3dEditUserPreferences.setAuthorModelsDirectory(authorModelsDirectoryTextField.getText().trim());
            if (isAliveAuthorModelsServer)
            {
                // notify user that file location is unworkable
                message = "AuthorModelsServer is already running, be sure to restart localhost http server to expose new directoryLocation='" + authorModelsDirectoryTextField.getText().trim() + "'";
                System.out.println("*** " + message);
                authorModelsDirectoryTextField.setBackground(Color.yellow);
                NotifyDescriptor notifyDescriptor = new NotifyDescriptor.Message(message, NotifyDescriptor.INFORMATION_MESSAGE);
                DialogDisplayer.getDefault().notify(notifyDescriptor);
            }
        }
    }
    private void authorModelsDirectoryClearButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_authorModelsDirectoryClearButtonActionPerformed
    {//GEN-HEADEREND:event_authorModelsDirectoryClearButtonActionPerformed
        authorModelsDirectoryTextField.setText("");
        X3dEditUserPreferences.setAuthorModelsDirectory(authorModelsDirectoryTextField.getText());
    }//GEN-LAST:event_authorModelsDirectoryClearButtonActionPerformed

    private void authorModelsDirectoryChooserButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_authorModelsDirectoryChooserButtonActionPerformed
    {//GEN-HEADEREND:event_authorModelsDirectoryChooserButtonActionPerformed
        authorModelsDirectoryChooser();
    }//GEN-LAST:event_authorModelsDirectoryChooserButtonActionPerformed

    private void authorModelsDirectoryDefaultButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_authorModelsDirectoryDefaultButtonActionPerformed
    {//GEN-HEADEREND:event_authorModelsDirectoryDefaultButtonActionPerformed
        authorModelsDirectoryTextField.setText(AUTHOR_MODELS_DIRECTORY_DEFAULT); // user.home
        X3dEditUserPreferences.setAuthorModelsDirectory(authorModelsDirectoryTextField.getText());
    }//GEN-LAST:event_authorModelsDirectoryDefaultButtonActionPerformed

    private void autolaunchExampleArchivesServerCheckBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_autolaunchExampleArchivesServerCheckBoxActionPerformed
    {//GEN-HEADEREND:event_autolaunchExampleArchivesServerCheckBoxActionPerformed
        X3dEditUserPreferences.setExampleArchivesServerAutolaunch(autolaunchExampleArchivesServerCheckBox.isSelected());
    }//GEN-LAST:event_autolaunchExampleArchivesServerCheckBoxActionPerformed

    private void portExampleArchivesServerTextFieldMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_portExampleArchivesServerTextFieldMouseExited
    {//GEN-HEADEREND:event_portExampleArchivesServerTextFieldMouseExited
        portExampleArchivesServerCheck();
    }//GEN-LAST:event_portExampleArchivesServerTextFieldMouseExited

    private void portExampleArchivesServerCheck()
    {
        // TODO check values 8000..? and not duplicated
        if (portExampleArchivesServerTextField.getText().isBlank())
        {
            System.out.println("*** Resetting blank port for ExampleArchivesServer to default " + X3dEditUserPreferences.EXAMPLE_ARCHIVES_SERVER_PORT_DEFAULT);
            portExampleArchivesServerTextField.setText(X3dEditUserPreferences.EXAMPLE_ARCHIVES_SERVER_PORT_DEFAULT);
        }
        if (!X3dEditUserPreferences.getExampleArchivesServerPort().equals(portExampleArchivesServerTextField.getText().trim()))
        {
            X3dEditUserPreferences.setExampleArchivesServerPort(portExampleArchivesServerTextField.getText().trim());
            if (isAliveExampleArchivesServer)
            {
                // notify user that file location is unworkable
                message = "ExampleArchivesServer is already running, be sure to restart localhost http server to expose new port=" + X3dEditUserPreferences.getExampleArchivesServerPort().trim();
                System.out.println("*** " + message);
                portExampleArchivesServerTextField.setBackground(Color.yellow);
                NotifyDescriptor notifyDescriptor = new NotifyDescriptor.Message(message, NotifyDescriptor.INFORMATION_MESSAGE);
                DialogDisplayer.getDefault().notify(notifyDescriptor);
            }
        }
        updateIndicationsPortsBoundOnServers();
    }
    private void portExampleArchivesServerTextFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_portExampleArchivesServerTextFieldActionPerformed
    {//GEN-HEADEREND:event_portExampleArchivesServerTextFieldActionPerformed
        portExampleArchivesServerCheck();
    }//GEN-LAST:event_portExampleArchivesServerTextFieldActionPerformed

    private void startExampleArchivesServerButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_startExampleArchivesServerButtonActionPerformed
    {//GEN-HEADEREND:event_startExampleArchivesServerButtonActionPerformed
        startExampleArchivesServer ();
        if (isAliveExampleArchivesServer || isPortBound(Integer.parseInt(X3dEditUserPreferences.getExampleArchivesServerPort())))
        {
     autolaunchExampleArchivesServerCheckBox.setSelected(X3dEditUserPreferences.isExampleArchivesServerAutolaunch());
            if (exampleArchivesHttpServerProcess != null)
            {
                startExampleArchivesServerButton.setText(HTTP_RUNNING);
                startExampleArchivesServerButton.setForeground(darkgreen);
                startExampleArchivesServerButton.setFont(boldFont);
                startExampleArchivesServerButton.setEnabled(true);
                
                 stopExampleArchivesServerButton.setEnabled(true);
                 stopExampleArchivesServerButton.setText(HTTP_STOP);
                 stopExampleArchivesServerButton.setForeground(Color.RED); // also BOLD
            }
            else
            {
                startExampleArchivesServerButton.setText(HTTP_BLOCKED);
                startExampleArchivesServerButton.setForeground(darkorange);
                startExampleArchivesServerButton.setEnabled(false);
                 stopExampleArchivesServerButton.setEnabled(false);
                 stopExampleArchivesServerButton.setForeground(Color.BLACK);
            }
        }
        updateIndicationsPortsBoundOnServers();
        portExampleArchivesServerTextField.setBackground(Color.white);
        exampleArchivesDirectoryTextField.setBackground(Color.white);
    }//GEN-LAST:event_startExampleArchivesServerButtonActionPerformed

    private void stopExampleArchivesServerButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_stopExampleArchivesServerButtonActionPerformed
    {//GEN-HEADEREND:event_stopExampleArchivesServerButtonActionPerformed
        stopExampleArchivesServer();
        startExampleArchivesServerButton.setEnabled(true);
        startExampleArchivesServerButton.setText(HTTP_START);
        startExampleArchivesServerButton.setForeground(black);
        startExampleArchivesServerButton.setFont(plainFont);
        
        stopExampleArchivesServerButton.setEnabled(false);
        stopExampleArchivesServerButton.setForeground(black);
        updateIndicationsPortsBoundOnServers();
        portExampleArchivesServerTextField.setBackground(Color.white);
         exampleArchivesDirectoryTextField.setBackground(Color.white);
    }//GEN-LAST:event_stopExampleArchivesServerButtonActionPerformed

    private void browseLocalhostExampleArchivesButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_browseLocalhostExampleArchivesButtonActionPerformed
    {//GEN-HEADEREND:event_browseLocalhostExampleArchivesButtonActionPerformed
        int portValue = Integer.parseInt(portExampleArchivesServerTextField.getText());
        if (addressValue.isBlank())
            addressValue = "localhost";
        // getAuthorCorsDirectory() should be root of query to https://localhost:8001
        String localRootAddress = "http://" + addressValue + ":" + portValue;
        openInBrowser(localRootAddress);
    }//GEN-LAST:event_browseLocalhostExampleArchivesButtonActionPerformed

    private void exampleArchivesServerStatusLabelMouseReleased(java.awt.event.MouseEvent evt)//GEN-FIRST:event_exampleArchivesServerStatusLabelMouseReleased
    {//GEN-HEADEREND:event_exampleArchivesServerStatusLabelMouseReleased
        updateIndicationsPortsBoundOnServers(); // refresh
        updateValuesInPanel();
    }//GEN-LAST:event_exampleArchivesServerStatusLabelMouseReleased

    private void exampleArchivesDirectoryTextFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_exampleArchivesDirectoryTextFieldActionPerformed
    {//GEN-HEADEREND:event_exampleArchivesDirectoryTextFieldActionPerformed
        // this field is filled in from properties, not by user
    }//GEN-LAST:event_exampleArchivesDirectoryTextFieldActionPerformed

    private void autolaunchActiveX3dModelServerCheckBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_autolaunchActiveX3dModelServerCheckBoxActionPerformed
    {//GEN-HEADEREND:event_autolaunchActiveX3dModelServerCheckBoxActionPerformed
        // true means autolaunch a new server for each active X3D model converted
        X3dEditUserPreferences.setAuthorModelsServerAutolaunch(autolaunchAuthorModelsServerCheckBox.isSelected());
    }//GEN-LAST:event_autolaunchActiveX3dModelServerCheckBoxActionPerformed

    private void portActiveX3dModelServerTextFieldMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_portActiveX3dModelServerTextFieldMouseExited
    {//GEN-HEADEREND:event_portActiveX3dModelServerTextFieldMouseExited
        // TODO check values 8000..? and not duplicated
//        X3dEditUserPreferences.setActiveX3dModelServerPort(portActiveX3dModelServerTextField.getText().trim());
        updateIndicationsPortsBoundOnServers();
    }//GEN-LAST:event_portActiveX3dModelServerTextFieldMouseExited

    private void portActiveX3dModelServerTextFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_portActiveX3dModelServerTextFieldActionPerformed
    {//GEN-HEADEREND:event_portActiveX3dModelServerTextFieldActionPerformed
        // TODO check values 8000..? and not duplicated
//        X3dEditUserPreferences.setActiveX3dModelServerPort(portActiveX3dModelServerTextField.getText().trim());
        updateIndicationsPortsBoundOnServers();
    }//GEN-LAST:event_portActiveX3dModelServerTextFieldActionPerformed

    private void startActiveX3dModelServerButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_startActiveX3dModelServerButtonActionPerformed
    {//GEN-HEADEREND:event_startActiveX3dModelServerButtonActionPerformed
        activeX3dModelServerSelection = activeX3dModelDirectoryServerListComboBox.getSelectedIndex();
        startActiveX3dModelServer();
        // TODO panel updates
    }//GEN-LAST:event_startActiveX3dModelServerButtonActionPerformed

    private void stopActiveX3dModelServerButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_stopActiveX3dModelServerButtonActionPerformed
    {//GEN-HEADEREND:event_stopActiveX3dModelServerButtonActionPerformed
        stopActiveX3dModelServers();
        startActiveX3dModelServerButton.setText(HTTP_START);
        startActiveX3dModelServerButton.setForeground(black);
        startActiveX3dModelServerButton.setFont(plainFont);
        startActiveX3dModelServerButton.setEnabled(true);
        
         stopActiveX3dModelServerButton.setEnabled(false);
         stopActiveX3dModelServerButton.setForeground(black);
        updateIndicationsPortsBoundOnServers();
    }//GEN-LAST:event_stopActiveX3dModelServerButtonActionPerformed

    private void browseLocalhostActiveX3dModelsButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_browseLocalhostActiveX3dModelsButtonActionPerformed
    {//GEN-HEADEREND:event_browseLocalhostActiveX3dModelsButtonActionPerformed
        activeX3dModelServerSelection = activeX3dModelDirectoryServerListComboBox.getSelectedIndex();
        if (getActiveServerSelection() <= 1)
            return; // no entries
        int portValue = Integer.parseInt(activeX3dModelPortList.get(getActiveServerSelection()));
        if (addressValue.isBlank())
            addressValue = "localhost";
        // activeX3dModelDirectoryList.get(activeX3dModelServerSelection) should be root of query to https://localhost:8001
        String localRootAddress = "http://" + addressValue + ":" + portValue;
        openInBrowser(localRootAddress);
    }//GEN-LAST:event_browseLocalhostActiveX3dModelsButtonActionPerformed

    private void activeX3dModelServerStatusLabelMouseReleased(java.awt.event.MouseEvent evt)//GEN-FIRST:event_activeX3dModelServerStatusLabelMouseReleased
    {//GEN-HEADEREND:event_activeX3dModelServerStatusLabelMouseReleased
        updateIndicationsPortsBoundOnServers(); // refresh
        updateValuesInPanel();
    }//GEN-LAST:event_activeX3dModelServerStatusLabelMouseReleased

    private void oldStartJavaServerButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_oldStartJavaServerButtonActionPerformed
    {//GEN-HEADEREND:event_oldStartJavaServerButtonActionPerformed
        // Would spinning off a JAVA process that references the local dir using the below work?
        // ref: https://dzone.com/articles/simple-http-server-in-java

        // TODO port value checks
        int             portValue = Integer.parseInt(portAuthorModelsServerTextField.getText());
      /*String*/       addressValue = addressComboBox.getSelectedItem().toString();
        if (addressValue.isBlank())
            addressValue = "localhost"; // probably wrong, expect this to get overwritten by interface
        String modelRootDirectory = "/";

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

                        if (originalJavaHttpServer != null) // created previously
                        {
                            originalJavaHttpServerClose();
                        }
                        originalJavaHttpServer = HttpServer.create((new InetSocketAddress(addressValue, portValue)), 0);

                        // TODO administrator permission needed?

                        // INFO [org.netbeans.api.java.source.ElementHandle]: Cannot resolve: ElementHandle[kind=METHOD; sigs=com.sun.net.httpserver.HttpServer createContext (Ljava/lang/String;)Lcom/sun/net/httpserver/HttpContext; ]
//////                        originalJavaHttpServer.createContext(modelRootDirectoryURI.getPath(), new LocalFileHandlerOld() );

                        // https://docs.oracle.com/en/java/javase/19/docs/api/jdk.httpserver/com/sun/net/httpserver/HttpServer.html#setExecutor(java.util.concurrent.Executor)
                        ThreadPerTaskExecutor httpServerExecutor = new ThreadPerTaskExecutor();
                        originalJavaHttpServer.setExecutor(httpServerExecutor); // null means default implementation; TODO eliminate potential problem

                        originalJavaHttpServer.start();
                        System.out.println("*** Java httpServer started for CORS");

                        BaseViewAction.sendBrowserTo("/"); // localUrl);
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

                //        javaAutoStartButton.setEnabled(false);
                //            oldJavaStartButton.setEnabled(false);
                //             stopAuthorModelsServerButton.setEnabled(true);
    }//GEN-LAST:event_oldStartJavaServerButtonActionPerformed

    private void oldStartPythonServerButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_oldStartPythonServerButtonActionPerformed
    {//GEN-HEADEREND:event_oldStartPythonServerButtonActionPerformed
        // https://stackoverflow.com/questions/10954194/start-cmd-by-using-processbuilder
        ArrayList<String> commands = new ArrayList<>();
        commands.clear();
        commands.add("python");
        commands.add("-m");
        commands.add("http.server");
        commands.add(portAuthorModelsServerTextField.getText());
    }//GEN-LAST:event_oldStartPythonServerButtonActionPerformed

    private void pageIntegrationTabbedPaneStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_pageIntegrationTabbedPaneStateChanged
    {//GEN-HEADEREND:event_pageIntegrationTabbedPaneStateChanged
        updatePageIntegrationTabbedPaneState();
    }//GEN-LAST:event_pageIntegrationTabbedPaneStateChanged

    private void continueButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_continueButtonActionPerformed
    {//GEN-HEADEREND:event_continueButtonActionPerformed
        setVisible(false);
    }//GEN-LAST:event_continueButtonActionPerformed

    private void transformModelButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_transformModelButtonActionPerformed
    {//GEN-HEADEREND:event_transformModelButtonActionPerformed
        // invoke file conversion
        x3dToXhtmlDomConversionAction.setReadyForConversion(true);
        x3dToXhtmlDomConversionAction.performAction(); // transform, load/launch result
        x3dToXhtmlDomConversionAction.setReadyForConversion(false); // all done, prevent unintended reinitiation
    }//GEN-LAST:event_transformModelButtonActionPerformed

    private void x3domImageLabelMouseReleased(java.awt.event.MouseEvent evt)//GEN-FIRST:event_x3domImageLabelMouseReleased
    {//GEN-HEADEREND:event_x3domImageLabelMouseReleased
        BaseViewAction.sendBrowserTo(X3DOM_site);
    }//GEN-LAST:event_x3domImageLabelMouseReleased

    private void x_iteLabelMouseReleased(java.awt.event.MouseEvent evt)//GEN-FIRST:event_x_iteLabelMouseReleased
    {//GEN-HEADEREND:event_x_iteLabelMouseReleased
        BaseViewAction.sendBrowserTo(X_ITE_site);
    }//GEN-LAST:event_x_iteLabelMouseReleased

    private void httpLabelMouseReleased(java.awt.event.MouseEvent evt)//GEN-FIRST:event_httpLabelMouseReleased
    {//GEN-HEADEREND:event_httpLabelMouseReleased
        LaunchX3dSceneAuthoringHintsCorsAction launchCorsAction = new LaunchX3dSceneAuthoringHintsCorsAction();
        launchCorsAction.performAction();
    }//GEN-LAST:event_httpLabelMouseReleased

    private void exampleArchivesDirectoryChooserButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_exampleArchivesDirectoryChooserButtonActionPerformed
    {//GEN-HEADEREND:event_exampleArchivesDirectoryChooserButtonActionPerformed
        exampleArchivesDirectoryChooser();
    }//GEN-LAST:event_exampleArchivesDirectoryChooserButtonActionPerformed

    private void activeX3dModelDirectoryClearButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_activeX3dModelDirectoryClearButtonActionPerformed
    {//GEN-HEADEREND:event_activeX3dModelDirectoryClearButtonActionPerformed
        if (activeX3dModelNameList.size() >= activeX3dModelDirectoryServerListComboBox.getModel().getSize() + 1)
        {
            // don't delete first explanatory entry in combobox
            if (activeX3dModelDirectoryServerListComboBox.getSelectedIndex() > 1)
            {
                int index = activeX3dModelDirectoryServerListComboBox.getSelectedIndex();
                activeX3dModelNameList.remove(index);
                activeX3dModelDirectoryList.remove(index);
                activeX3dModelPortList.remove(index);
                activeX3dModelProcessList.remove(index);
                activeX3dModelDirectoryServerListComboBox.removeItem(index + 1);
                if (activeX3dModelDirectoryServerListComboBox.getSelectedIndex() > 1) // update other displays to match
                {
                    portActiveX3dModelServerTextField.setText(activeX3dModelPortList.get(getActiveServerSelection()));
                }
                else // default combobox selection
                {
                    portActiveX3dModelServerTextField.setText("");
                }
                updateIndicationsPortsBoundOnServers();
            }
        }
    }//GEN-LAST:event_activeX3dModelDirectoryClearButtonActionPerformed

    private void activeX3dModelDirectoryChooserButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_activeX3dModelDirectoryChooserButtonActionPerformed
    {//GEN-HEADEREND:event_activeX3dModelDirectoryChooserButtonActionPerformed
        activeX3dModelDirectoryChooser();
    }//GEN-LAST:event_activeX3dModelDirectoryChooserButtonActionPerformed

    private void exampleArchivesDownloadPanelButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_exampleArchivesDownloadPanelButtonActionPerformed
    {//GEN-HEADEREND:event_exampleArchivesDownloadPanelButtonActionPerformed
        DownloadX3dExamplesArchivesAction downloadX3dExamplesArchivesAction = new DownloadX3dExamplesArchivesAction();
        downloadX3dExamplesArchivesAction.performAction();
    }//GEN-LAST:event_exampleArchivesDownloadPanelButtonActionPerformed

    private void activeX3dModelLocationLabelMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_activeX3dModelLocationLabelMouseExited
    {//GEN-HEADEREND:event_activeX3dModelLocationLabelMouseExited
        boolean haveLaunchedModels = (activeX3dModelNameList.size() > 1);
        if (haveLaunchedModels)
            activeX3dModelDirectoryChooser();
    }//GEN-LAST:event_activeX3dModelLocationLabelMouseExited

    private void examplesArchiveDescriptionLabelMouseReleased(java.awt.event.MouseEvent evt)//GEN-FIRST:event_examplesArchiveDescriptionLabelMouseReleased
    {//GEN-HEADEREND:event_examplesArchiveDescriptionLabelMouseReleased
        exampleArchivesDirectoryChooser();
    }//GEN-LAST:event_examplesArchiveDescriptionLabelMouseReleased

    private void authorModelsDirectoryLabelMouseReleased(java.awt.event.MouseEvent evt)//GEN-FIRST:event_authorModelsDirectoryLabelMouseReleased
    {//GEN-HEADEREND:event_authorModelsDirectoryLabelMouseReleased
        authorModelsDirectoryChooser();
    }//GEN-LAST:event_authorModelsDirectoryLabelMouseReleased

    private void activeX3dModelDirectoryServerListComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_activeX3dModelDirectoryServerListComboBoxActionPerformed
    {//GEN-HEADEREND:event_activeX3dModelDirectoryServerListComboBoxActionPerformed
        checkActiveX3dModelListSizes("activeX3dModelDirectoryServerListComboBoxActionPerformed");
        activeX3dModelServerSelection = activeX3dModelDirectoryServerListComboBox.getSelectedIndex(); // first non-info value is index 1
        if (getActiveServerSelection() <= 0) // TODO reserved index 0?
            return; // no entries
        portActiveX3dModelServerTextField.setText(activeX3dModelPortList.get(activeX3dModelServerSelection - 1));
        activeX3dModelServerPort =                  activeX3dModelPortList.get(activeX3dModelServerSelection - 1);
        // TODO check server status, update display
    }//GEN-LAST:event_activeX3dModelDirectoryServerListComboBoxActionPerformed

    private void activeX3dModelDirectoryServerListComboBoxItemStateChanged(java.awt.event.ItemEvent evt)//GEN-FIRST:event_activeX3dModelDirectoryServerListComboBoxItemStateChanged
    {//GEN-HEADEREND:event_activeX3dModelDirectoryServerListComboBoxItemStateChanged
        updateActiveX3dModelDirectoryButtons();
    }//GEN-LAST:event_activeX3dModelDirectoryServerListComboBoxItemStateChanged

    private void downloadPanelLaunchButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_downloadPanelLaunchButtonActionPerformed
    {//GEN-HEADEREND:event_downloadPanelLaunchButtonActionPerformed
        DownloadX3dExamplesArchivesAction downloadX3dExamplesArchivesAction = new DownloadX3dExamplesArchivesAction ();
        downloadX3dExamplesArchivesAction.performAction();
    }//GEN-LAST:event_downloadPanelLaunchButtonActionPerformed

    private void corsPanelFocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event_corsPanelFocusGained
    {//GEN-HEADEREND:event_corsPanelFocusGained
        updateValuesInPanel();
        updateIndicationsPortsBoundOnServers();
    }//GEN-LAST:event_corsPanelFocusGained

    private void corsPanelComponentShown(java.awt.event.ComponentEvent evt)//GEN-FIRST:event_corsPanelComponentShown
    {//GEN-HEADEREND:event_corsPanelComponentShown
        updateValuesInPanel();
        updateIndicationsPortsBoundOnServers();
    }//GEN-LAST:event_corsPanelComponentShown

    private void refreshExampleArchivesButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_refreshExampleArchivesButtonActionPerformed
    {//GEN-HEADEREND:event_refreshExampleArchivesButtonActionPerformed
        if ((exampleArchivesHttpServerProcess != null) || X3dEditUserPreferences.isExampleArchivesServerAutolaunch())
        {
            stopExampleArchivesServer();
            sleep(200l);
            startExampleArchivesServer();
            sleep(200l);
        }
        updateValuesInPanel();
        updateIndicationsPortsBoundOnServers();
        
        message  = "exampleArchivesHttpServerProcess ";
        if  (exampleArchivesHttpServerProcess == null)
             message += "null, isBound=" + isPortBoundExampleArchivesServer();
        else message += "isAlive=" + exampleArchivesHttpServerProcess.isAlive() + " isBound=" + isPortBoundExampleArchivesServer() +
                        "\n    "   + exampleArchivesHttpServerProcess.info();
        System.out.println("*** panel refresh: " + message);
    }//GEN-LAST:event_refreshExampleArchivesButtonActionPerformed

    private void refreshAuthorModelsButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_refreshAuthorModelsButtonActionPerformed
    {//GEN-HEADEREND:event_refreshAuthorModelsButtonActionPerformed
        if ((authorModelsHttpServerProcess != null) || X3dEditUserPreferences.isAuthorModelsServerAutolaunch())
        {
            stopAuthorModelsServer();
            sleep(200l);
            startAuthorModelsServer();
            sleep(200l);
        }
        updateValuesInPanel();
        updateIndicationsPortsBoundOnServers();
        
        message  = "authorModelsHttpServerProcess ";
        if  (authorModelsHttpServerProcess == null)
             message += "null, isBound=" + isPortBoundAuthorModelsServer();
        else message += "isAlive=" + authorModelsHttpServerProcess.isAlive() + " isBound=" + isPortBoundAuthorModelsServer() +
                        "\n    "   + authorModelsHttpServerProcess.info();
        System.out.println("*** panel refresh: " + message);
    }//GEN-LAST:event_refreshAuthorModelsButtonActionPerformed

    private void refreshActiveX3dModelButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_refreshActiveX3dModelButtonActionPerformed
    {//GEN-HEADEREND:event_refreshActiveX3dModelButtonActionPerformed
        if ((exampleArchivesHttpServerProcess != null) || X3dEditUserPreferences.isExampleArchivesServerAutolaunch())
        {
            stopActiveX3dModelServers();
            sleep(200l);
            startActiveX3dModelServer();
            sleep(200l);
        }
        updateValuesInPanel();
        updateIndicationsPortsBoundOnServers();
        
        // TODO active server
        message  = "httpServerProcess3 ";
        if  (httpServerProcessNew == null)
             message += "null";
        else message += httpServerProcessNew.info();
        System.out.println("*** panel refresh: " + message);
    }//GEN-LAST:event_refreshActiveX3dModelButtonActionPerformed

    private void updateActiveX3dModelDirectoryButtons()
    {
        boolean haveLaunchedModels = (activeX3dModelNameList.size() > 1);
        startActiveX3dModelServerButton.setEnabled(haveLaunchedModels);
         stopActiveX3dModelServerButton.setEnabled(haveLaunchedModels);
   browseLocalhostActiveX3dModelsButton.setEnabled(haveLaunchedModels);
     activeX3dModelDirectoryClearButton.setEnabled(haveLaunchedModels);
   activeX3dModelDirectoryChooserButton.setEnabled(haveLaunchedModels);
    }
    private void exampleArchivesDirectoryChooser()
    {
        // file chooser looks in given directory first
        if (corsDirectoryChooser == null) // first time through
        {
            if  ((exampleArchivesDirectoryTextField.getText() != null) && !exampleArchivesDirectoryTextField.getText().isBlank())
                 corsDirectoryChooser = new JFileChooser(exampleArchivesDirectoryTextField.getText().trim());
            else corsDirectoryChooser = new JFileChooser(System.getProperty("user.home"));
                 corsDirectoryChooser.setMultiSelectionEnabled(false);
                 corsDirectoryChooser.putClientProperty("JFileChooser.appBundleIsTraversable", "never");  // for macs
        }
        String title = "Choose designated CORS server directory for author models";
        corsDirectoryChooser.setDialogTitle(title);
        // https://stackoverflow.com/questions/25666642/jfilechooser-to-pick-a-directory-or-a-single-file
        corsDirectoryChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES) ; // prevents user from seeing what is present: DIRECTORIES_ONLY);

        int returnValue = corsDirectoryChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION)
        {
            if  (corsDirectoryChooser.getSelectedFile().isDirectory())
                 exampleArchivesDirectoryTextField.setText(corsDirectoryChooser.getSelectedFile().getAbsolutePath());
            else exampleArchivesDirectoryTextField.setText(corsDirectoryChooser.getCurrentDirectory().getAbsolutePath());
            // TODO TF callback to save changed options (doesn't happen automatically)
            exampleArchivesDirectoryTextField.postActionEvent();
        }
        // do not change X3dEditUserPreferences value
    }
    private void authorModelsDirectoryChooser()
    {
        // file chooser looks in given directory first
        if (corsDirectoryChooser == null) // first time through
        {
            if  ((authorModelsDirectoryTextField.getText() != null) && !authorModelsDirectoryTextField.getText().isBlank())
                 corsDirectoryChooser = new JFileChooser(authorModelsDirectoryTextField.getText().trim());
            else corsDirectoryChooser = new JFileChooser(System.getProperty("user.home"));
            corsDirectoryChooser.setMultiSelectionEnabled(false);
            corsDirectoryChooser.putClientProperty("JFileChooser.appBundleIsTraversable", "never");  // for macs
        }
        String title = "Choose designated CORS server directory for author models";
        corsDirectoryChooser.setDialogTitle(title);
        // https://stackoverflow.com/questions/25666642/jfilechooser-to-pick-a-directory-or-a-single-file
        corsDirectoryChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES) ; // prevents user from seeing what is present: DIRECTORIES_ONLY);

        int returnValue = corsDirectoryChooser.showOpenDialog(this);
        if (returnValue == JFileChooser.APPROVE_OPTION)
        {
            if  (corsDirectoryChooser.getSelectedFile().isDirectory())
                 authorModelsDirectoryTextField.setText(corsDirectoryChooser.getSelectedFile().getAbsolutePath());
            else authorModelsDirectoryTextField.setText(corsDirectoryChooser.getCurrentDirectory().getAbsolutePath());
            // TODO TF callback to save changed options (doesn't happen automatically)
            authorModelsDirectoryTextField.postActionEvent();
        }
        authorModelsDirectoryTextField.setText(authorModelsDirectoryTextField.getText().trim());
        X3dEditUserPreferences.setAuthorModelsDirectory(authorModelsDirectoryTextField.getText());
    }
    private void activeX3dModelDirectoryChooser()
    {
        System.out.println ("*** TODO activeX3dModelDirectoryChooser() selection");
        if (activeX3dModelNameList.isEmpty())
        {
            System.out.println ("*** activeX3dModelDirectoryChooser() no action taken since no model directories have been launchedused");
            return; // TODO disable button if list is empty
        }
        activeX3dModelServerSelection = activeX3dModelDirectoryServerListComboBox.getSelectedIndex();
        String modelDirectory = activeX3dModelDirectoryList.get(getActiveServerSelection());
        corsDirectoryChooser = new JFileChooser(modelDirectory);
        corsDirectoryChooser.setMultiSelectionEnabled(false);
        corsDirectoryChooser.putClientProperty("JFileChooser.appBundleIsTraversable", "never");  // for macs
        corsDirectoryChooser.setDialogTitle("Active X3d model directory");
        int returnValue = corsDirectoryChooser.showOpenDialog(this); // ignore return
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[])
    {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try
        {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
            {
                if ("Nimbus".equals(info.getName()))
                {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex)
        {
            java.util.logging.Logger.getLogger(X3dToXhtmlDomConversionFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run()
            {
                if (X3dToXhtmlDomConversionAction.x3dToXhtmlDomConversionFrame == null)
                {
                    X3dToXhtmlDomConversionAction.x3dToXhtmlDomConversionFrame = new X3dToXhtmlDomConversionFrame(x3dToXhtmlDomConversionAction);
                }
                X3dToXhtmlDomConversionAction.x3dToXhtmlDomConversionFrame.toFront();
                X3dToXhtmlDomConversionAction.x3dToXhtmlDomConversionFrame.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton activeX3dModelDirectoryChooserButton;
    private javax.swing.JButton activeX3dModelDirectoryClearButton;
    private javax.swing.JComboBox<String> activeX3dModelDirectoryServerListComboBox;
    private javax.swing.JLabel activeX3dModelLocationLabel;
    private javax.swing.JLabel activeX3dModelServerStatusLabel;
    private javax.swing.JComboBox<String> addressComboBox;
    private javax.swing.JLabel addressLabel;
    private javax.swing.JButton authorModelsDirectoryChooserButton;
    private javax.swing.JButton authorModelsDirectoryClearButton;
    private javax.swing.JButton authorModelsDirectoryDefaultButton;
    private javax.swing.JLabel authorModelsDirectoryLabel;
    private javax.swing.JTextField authorModelsDirectoryTextField;
    private javax.swing.JLabel authorModelsServerStatusLabel;
    private javax.swing.JLabel autoLabel1;
    private javax.swing.JLabel autoLabel2;
    private javax.swing.JLabel autoLabel3;
    private javax.swing.JCheckBox autolaunchActiveX3dModelServerCheckBox;
    private javax.swing.JCheckBox autolaunchAuthorModelsServerCheckBox;
    private javax.swing.JCheckBox autolaunchExampleArchivesServerCheckBox;
    private javax.swing.JSeparator browseHttpSeparator1;
    private javax.swing.JSeparator browseHttpSeparator3;
    private javax.swing.JButton browseLocalhostActiveX3dModelsButton;
    private javax.swing.JButton browseLocalhostAuthorModelsButton;
    private javax.swing.JButton browseLocalhostExampleArchivesButton;
    private javax.swing.JCheckBox cacheCheckBox;
    private javax.swing.JLabel cacheDescriptionLabel;
    private javax.swing.JButton continueButton;
    private javax.swing.JLabel corsDescriptionLabel;
    private javax.swing.JButton corsHelpButton;
    private javax.swing.JPanel corsPanel;
    private javax.swing.JButton downloadPanelLaunchButton;
    private javax.swing.JButton exampleArchivesDirectoryChooserButton;
    private javax.swing.JTextField exampleArchivesDirectoryTextField;
    private javax.swing.JButton exampleArchivesDownloadPanelButton;
    private javax.swing.JLabel exampleArchivesServerStatusLabel;
    private javax.swing.JLabel examplesArchiveDescriptionLabel;
    private javax.swing.JButton feedbackButton;
    private javax.swing.JLabel heightDescriptionLabel;
    private javax.swing.JLabel heightLabel;
    private javax.swing.JTextField heightTextField;
    private javax.swing.JButton helpSceneAuthoringHintsHtmlButton;
    private javax.swing.JLabel helpSceneAuthoringHintsHtmlLabel;
    private javax.swing.JLabel horizontalSpacerBottomLabel;
    private javax.swing.JLabel horizontalSpacerLabel;
    private javax.swing.JLabel horizontalSpacerLabel1;
    private javax.swing.JLabel horizontalSpacerLabelButtons3;
    private javax.swing.JLabel horizontalSpacerLabelButtons4;
    private javax.swing.JLabel html5ImageLabel;
    private javax.swing.JPanel htmlPanel;
    private javax.swing.JLabel httpLabel;
    private javax.swing.JLabel localhostHttpServerControlsLabel;
    private javax.swing.JButton oldStartJavaServerButton;
    private javax.swing.JButton oldStartPythonServerButton;
    private javax.swing.JTabbedPane pageIntegrationTabbedPane;
    private javax.swing.JTextField portActiveX3dModelServerTextField;
    private javax.swing.JTextField portAuthorModelsServerTextField;
    private javax.swing.JTextField portExampleArchivesServerTextField;
    private javax.swing.JLabel portLabel1;
    private javax.swing.JLabel portLabel2;
    private javax.swing.JLabel portLabel3;
    private javax.swing.JComboBox primitiveQualityComboBox;
    private javax.swing.JLabel primitiveQualityDescriptionLabel;
    private javax.swing.JLabel primitiveQualityLabel;
    private javax.swing.JButton refreshActiveX3dModelButton;
    private javax.swing.JButton refreshAuthorModelsButton;
    private javax.swing.JButton refreshExampleArchivesButton;
    private javax.swing.JCheckBox showLogCheckBox;
    private javax.swing.JLabel showLogLabel;
    private javax.swing.JCheckBox showProgressCheckBox;
    private javax.swing.JLabel showProgressLabel;
    private javax.swing.JCheckBox showStatisticsCheckBox;
    private javax.swing.JLabel showStatisticsLabel;
    private javax.swing.JButton startActiveX3dModelServerButton;
    private javax.swing.JButton startAuthorModelsServerButton;
    private javax.swing.JButton startExampleArchivesServerButton;
    private javax.swing.JButton stopActiveX3dModelServerButton;
    private javax.swing.JButton stopAuthorModelsServerButton;
    private javax.swing.JButton stopExampleArchivesServerButton;
    private javax.swing.JButton transformModelButton;
    private javax.swing.JLabel urlLabel;
    private org.web3d.x3d.palette.items.UrlExpandableList2 urlList;
    private javax.swing.JLabel verticalSpacerLabel1;
    private javax.swing.JLabel verticalSpacerLabel2;
    private javax.swing.JLabel verticalSpacerLabelBottom;
    private javax.swing.JButton viewX3d4Html5AnnexButton;
    private javax.swing.JLabel viewX3d4Html5AnnexLabel;
    private javax.swing.JLabel widthDescriptionLabel;
    private javax.swing.JLabel widthLabel;
    private javax.swing.JTextField widthTextField;
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


    protected final void setPlayerSelection (String playerName)
    {
        if      (x3dToXhtmlDomConversionAction.getPreferredTab() == CORS_TAB)
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
        x3dToXhtmlDomConversionAction.setUrlScene(urlComplete);
    }
    
    public final static void autolaunchAllServers ()
    {
        autolaunchAuthorModelServer ();
        autolaunchExampleArchivesServer ();
        autolaunchActiveX3dModelServers ();
    }
    
    public final static void autolaunchAuthorModelServer ()
    {
        if (X3dEditUserPreferences.isAuthorModelsServerAutolaunch())
        {
            if (!isPortBound(Integer.parseInt(X3dEditUserPreferences.getAuthorModelsServerPort())))
            {
                 startAuthorModelsServer ();
                 System.out.println("*** autolaunch started, AuthorModelsServer port=" + X3dEditUserPreferences.getAuthorModelsServerPort() +
                                    " isAlive=" + isAliveAuthorModelsServer);
            }
            else System.out.println("*** autolaunch ignored, AuthorModelsServer port=" + X3dEditUserPreferences.getAuthorModelsServerPort() +
                                    " isAlive=" + isAliveAuthorModelsServer);
        }
    }
    
    public final static void autolaunchExampleArchivesServer ()
    {
        if (X3dEditUserPreferences.isExampleArchivesServerAutolaunch())
        {
            if (!isPortBound(Integer.parseInt(X3dEditUserPreferences.getExampleArchivesServerPort())))
            {
                 startExampleArchivesServer ();
                 System.out.println("*** autolaunch started, ExampleArchivesServer port=" + X3dEditUserPreferences.getExampleArchivesServerPort() +
                                    " isAlive=" + isAliveExampleArchivesServer);
            }
            else System.out.println("*** autolaunch ignored, ExampleArchivesServer port=" + X3dEditUserPreferences.getExampleArchivesServerPort() +
                                    " isAlive=" + isAliveExampleArchivesServer);
        }
    }
    
    public final static void autolaunchActiveX3dModelServers ()
    {
        // TODO
//        if (X3dEditUserPreferences.isActiveX3dModelServerAutolaunch())
//        {
//            String currentActiveX3dModelPortValue = portActiveX3dModelServerTextField.getText();
//            if (!activeX3dModelNameList.isEmpty())
//            {
//                 System.out.println("*** autolaunch started, ActiveX3dModelServers");
//                 startActiveX3dModelServer ();
//            }
//            else System.out.println("*** autolaunch ignored, activeX3dModelNameList isEmpty()");
//        }
    }
    
    /** build CLI invocation to start server
     * @see https://docs.oracle.com/en/java/javase/19/docs/specs/man/jwebserver.html
     */
    public final static void startAuthorModelsServer ()
    {
        // https://stackoverflow.com/questions/10954194/start-cmd-by-using-processbuilder
        ArrayList<String> commands = new ArrayList<>();
        commands.clear();
        commands.add("jwebserver");
        commands.add("--bind-address");
        commands.add(addressValue);
        commands.add("--port");
        commands.add(X3dEditUserPreferences.getAuthorModelsServerPort());
        commands.add("--output");
        commands.add("verbose");

        isAliveAuthorModelsServer = startServer(AUTHOR_MODELS, commands, X3dEditUserPreferences.getAuthorModelsDirectory()); // authorModelsDirectoryTextField.getText());
    }
    
    /** build CLI invocation to start server
     * @see https://docs.oracle.com/en/java/javase/19/docs/specs/man/jwebserver.html
     */
    public final static void startExampleArchivesServer ()
    {
        // https://stackoverflow.com/questions/10954194/start-cmd-by-using-processbuilder
               
        ArrayList<String> commands = new ArrayList<>();
        commands.clear();
        commands.add("jwebserver");
        commands.add("--bind-address");
        commands.add(addressValue);
        commands.add("--port");
        commands.add(X3dEditUserPreferences.getExampleArchivesServerPort());
        commands.add("--output");
        commands.add("verbose");
        isAliveExampleArchivesServer = startServer(EXAMPLE_ARCHIVES, commands, X3dEditUserPreferences.getExampleArchivesRootDirectory()); // exampleArchivesDirectoryTextField.getText());
    }
    /** build CLI invocation to start server
     * @see https://docs.oracle.com/en/java/javase/19/docs/specs/man/jwebserver.html
     */
    public final void startActiveX3dModelServer ()
    {
        if (activeX3dModelNameList.isEmpty())
        {
             System.out.println("*** startActiveX3dModelServers() ignored since no model directories have been launched");
             startActiveX3dModelServer ();        
        }
        int index = 0;
        for (String activeX3dModelPort : activeX3dModelPortList)
        {
            index++;
            // https://stackoverflow.com/questions/10954194/start-cmd-by-using-processbuilder
            ArrayList<String> commands = new ArrayList<>();
            commands.clear();
            commands.add("jwebserver");
            commands.add("--bind-address");
            commands.add(addressValue);
            commands.add("--port");
            commands.add(portActiveX3dModelServerTextField.getText());
            commands.add("--output");
            commands.add("verbose");
            isAliveActiveX3dModelServer = startServer(ACTIVE_X3D_MODEL, commands, 
                    exampleArchivesDirectoryTextField.getText()); // TODO adjust
            
            sleep(200l);
            if (isAliveActiveX3dModelServer || isPortBound(Integer.parseInt(activeX3dModelPortList.get(index))))
            { 
                // TODO autolaunch refresh
                startActiveX3dModelServerButton.setText(HTTP_RUNNING);
                startActiveX3dModelServerButton.setForeground(darkgreen);
                startActiveX3dModelServerButton.setFont(boldFont);
                startActiveX3dModelServerButton.setEnabled(true);
            
                 stopActiveX3dModelServerButton.setEnabled(true);
                 stopActiveX3dModelServerButton.setText(HTTP_STOP);
                 stopActiveX3dModelServerButton.setForeground(Color.RED); // also BOLD
            }
        }
        updateIndicationsPortsBoundOnServers();
    }
    
    protected final void updateValuesInPanel()
    {
      if (x3dToXhtmlDomConversionAction != null)
      {
//                  widthTextField.setText        (xhtmlX3domAction.getX3dWidth());
//                 heightTextField.setText        (xhtmlX3domAction.getX3dHeight());
                 showLogCheckBox.setSelected    (x3dToXhtmlDomConversionAction.isShowLog());
            showProgressCheckBox.setSelected    (x3dToXhtmlDomConversionAction.isShowProgress());
          showStatisticsCheckBox.setSelected    (x3dToXhtmlDomConversionAction.isShowStatistics());
        primitiveQualityComboBox.setSelectedItem(x3dToXhtmlDomConversionAction.getPrimitiveQuality());
                   cacheCheckBox.setSelected    (x3dToXhtmlDomConversionAction.isCache());
                         urlList.setUrlData     (x3dToXhtmlDomConversionAction.getUrlScene());
      }
        // TODO mistaken design        (      X3dEditUserPreferences.getAuthorPreferenceCorsDirectory () );
                  widthTextField.setText        (X3dEditUserPreferences.getAuthorPreferenceHtmlWidth () ); 
                 heightTextField.setText        (X3dEditUserPreferences.getAuthorPreferenceHtmlHeight () );
        
      autolaunchAuthorModelsServerCheckBox.setSelected(X3dEditUserPreferences.isAuthorModelsServerAutolaunch());
   autolaunchExampleArchivesServerCheckBox.setSelected(X3dEditUserPreferences.isExampleArchivesServerAutolaunch());
    autolaunchActiveX3dModelServerCheckBox.setSelected(X3dEditUserPreferences.isActiveX3dModelServerAutolaunch());
           portAuthorModelsServerTextField.setText    (X3dEditUserPreferences.getAuthorModelsServerPort());
        portExampleArchivesServerTextField.setText    (X3dEditUserPreferences.getExampleArchivesServerPort());
         portActiveX3dModelServerTextField.setText    (String.valueOf(INITIAL_ACTIVE_X3D_MODEL_SERVER_PORT));
         exampleArchivesDirectoryTextField.setText    (X3dEditUserPreferences.getExampleArchivesRootDirectory());
            authorModelsDirectoryTextField.setText    (X3dEditUserPreferences.getAuthorModelsDirectory());
            
        if (isAliveAuthorModelsServer || isPortBound(Integer.parseInt(X3dEditUserPreferences.getAuthorModelsServerPort())))
        {
     autolaunchAuthorModelsServerCheckBox.setSelected(X3dEditUserPreferences.isAuthorModelsServerAutolaunch());
            if (authorModelsHttpServerProcess != null)
            {
                startAuthorModelsServerButton.setText(HTTP_RUNNING);
                startAuthorModelsServerButton.setForeground(darkgreen);
                startAuthorModelsServerButton.setFont(boldFont);
                startAuthorModelsServerButton.setEnabled(true);

                  stopAuthorModelsServerButton.setEnabled(true);
                  stopAuthorModelsServerButton.setText(HTTP_STOP);
                  stopAuthorModelsServerButton.setForeground(Color.RED); // also BOLD
            }
            else
            {
                startAuthorModelsServerButton.setText(HTTP_BLOCKED);
                startAuthorModelsServerButton.setForeground(darkorange);
                startAuthorModelsServerButton.setEnabled(false);
                 stopAuthorModelsServerButton.setEnabled(false);
                 stopAuthorModelsServerButton.setForeground(Color.BLACK);
            }
        }
        if (!isPortBound(Integer.parseInt(X3dEditUserPreferences.getAuthorModelsServerPort())))
        {
                startAuthorModelsServerButton.setText(HTTP_START);
                startAuthorModelsServerButton.setForeground(Color.BLACK);
                startAuthorModelsServerButton.setEnabled(true);
                startAuthorModelsServerButton.setFont(plainFont);
        }
        
        if (isAliveExampleArchivesServer || isPortBound(Integer.parseInt(X3dEditUserPreferences.getExampleArchivesServerPort())))
        {
     autolaunchExampleArchivesServerCheckBox.setSelected(X3dEditUserPreferences.isExampleArchivesServerAutolaunch());
            if (exampleArchivesHttpServerProcess != null)
            {
                startExampleArchivesServerButton.setText(HTTP_RUNNING);
                startExampleArchivesServerButton.setForeground(darkgreen);
                startExampleArchivesServerButton.setFont(boldFont);
                startExampleArchivesServerButton.setEnabled(true);
                
                 stopExampleArchivesServerButton.setEnabled(true);
                 stopExampleArchivesServerButton.setText(HTTP_STOP);
                 stopExampleArchivesServerButton.setForeground(Color.RED); // also BOLD
            }
            else
            {
                startExampleArchivesServerButton.setText(HTTP_BLOCKED);
                startExampleArchivesServerButton.setForeground(darkorange);
                startExampleArchivesServerButton.setEnabled(false);
                 stopExampleArchivesServerButton.setEnabled(false);
                 stopExampleArchivesServerButton.setForeground(Color.BLACK);
            }
        }
        if (!isPortBound(Integer.parseInt(X3dEditUserPreferences.getExampleArchivesServerPort())))
        {
                startExampleArchivesServerButton.setText(HTTP_START);
                startExampleArchivesServerButton.setForeground(Color.BLACK);
                startExampleArchivesServerButton.setEnabled(true);
                startExampleArchivesServerButton.setFont(plainFont);
        }
        
        // TODO give indication if any examples are in archives
        
//        if      (X3dEditUserPreferences.getAuthorModelsServerPort().equals(LOCAL_EXAMPLES_ROOT))
//        {
//            authorCorsDirectory =  examplesArchiveRootDirectoryTextField.getText();
//        }
//        else if (X3dEditUserPreferences.getAuthorModelsServerPort().equals(DESIGNATED_DIRECTORY))
//        {
//            authorCorsDirectory =  authorModelsDirectoryTextField.getText();
//        }
//        else if (X3dEditUserPreferences.getAuthorModelsServerPort().equals(CURRENT_X3D_MODEL_DIRECTORY))
//        {
//            authorCorsDirectory =  "TODO";
//        }
    }
    
    /**
     * Check whether port on local host is bound (meaning another process has it, probably an http server)
     * @param port of interest
     * @return whether port is bound
     * @see https://stackoverflow.com/questions/434718/sockets-discover-port-availability-using-java
     */
    public static boolean isPortBound (String port)
    {
        return isPortBound (Integer.parseInt(port));
    }
    
    /**
     * Check whether port on local host is bound (meaning another process has it, probably an http server)
     * @return whether port is bound
     */
    public static boolean isPortBoundAuthorModelsServer()
    {
        return isPortBound(X3dEditUserPreferences.getAuthorModelsServerPort());
    }
    
    /**
     * Check whether port on local host is bound (meaning another process has it, probably an http server)
     * @return whether port is bound
     */
    public static boolean isPortBoundExampleArchivesServer()
    {
        return isPortBound(X3dEditUserPreferences.getExampleArchivesServerPort());
    }

    /**
     * Check whether port on local host is bound (meaning another process has it, probably an http server)
     * @param port of interest
     * @return whether port is bound
     * @see https://stackoverflow.com/questions/434718/sockets-discover-port-availability-using-java
     */
    public static boolean isPortBound (int port)
    {
      try (Socket ignoredSocket = new Socket("localhost", port)) 
      {
          return true; // able to connect, hence bound
      }
      catch (ConnectException e)
      {
          return false; // unable to connect, hence not bound
      }
      catch (IOException e)
      {
          System.err.println ("*** isPortBound(" + port + ") error while trying to check open port");
          System.err.println (e);
          return false;
      }
    }
    /** show green if bound (by any http server), grey otherwise */
    private void updateIndicationsPortsBoundOnServers()
    {
        // first update icon display indicators
//        indicateAuthorModelsServerPortBound ();
//        indicateExampleArchivesServerPortBound ();
//        indicateActiveX3dModelServerPortBound ();
        
        String PORT_BOUND = "http running on port=";
        String PORT_OPEN  = "http not running on port=";
        StringBuilder messageBuilder = new StringBuilder();
        messageBuilder.append("*** http status refresh: ");
        
        if (X3dEditUserPreferences.getAuthorModelsServerPort().isBlank())
        {
            System.out.println("*** Resetting blank port for AuthorModelsServer to default " + X3dEditUserPreferences.AUTHOR_MODELS_SERVER_PORT_DEFAULT);
            portAuthorModelsServerTextField.setText(X3dEditUserPreferences.AUTHOR_MODELS_SERVER_PORT_DEFAULT);
            X3dEditUserPreferences.setExampleArchivesServerPort(X3dEditUserPreferences.AUTHOR_MODELS_SERVER_PORT_DEFAULT);
        }
        messageBuilder.append("authorModelsServer port=").append(X3dEditUserPreferences.getAuthorModelsServerPort());
        
        if  (isPortBound(Integer.parseInt(X3dEditUserPreferences.getAuthorModelsServerPort())))
        {
             authorModelsServerStatusLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/resources/circleGreen24x24.png")));
             authorModelsServerStatusLabel.setToolTipText(PORT_BOUND + X3dEditUserPreferences.getAuthorModelsServerPort());
             messageBuilder.append(" is bound, ");
        }
        else 
        {
            authorModelsServerStatusLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/resources/circleGrey24x24.png")));
             authorModelsServerStatusLabel.setToolTipText(PORT_OPEN + X3dEditUserPreferences.getAuthorModelsServerPort());
             messageBuilder.append(" not bound, ");
        }
        
        if (X3dEditUserPreferences.getExampleArchivesServerPort().isBlank())
        {
            System.out.println("*** Resetting blank port for ExampleArchivesServer to default " + X3dEditUserPreferences.EXAMPLE_ARCHIVES_SERVER_PORT_DEFAULT);
            portExampleArchivesServerTextField.setText(X3dEditUserPreferences.EXAMPLE_ARCHIVES_SERVER_PORT_DEFAULT);
            X3dEditUserPreferences.setExampleArchivesServerPort(X3dEditUserPreferences.EXAMPLE_ARCHIVES_SERVER_PORT_DEFAULT);
        }
        messageBuilder.append("exampleArchivesServer port=").append(X3dEditUserPreferences.getExampleArchivesServerPort());
        if  (isPortBound(Integer.parseInt(X3dEditUserPreferences.getExampleArchivesServerPort())))
        {
             exampleArchivesServerStatusLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/resources/circleGreen24x24.png")));
             exampleArchivesServerStatusLabel.setToolTipText(PORT_BOUND + X3dEditUserPreferences.getExampleArchivesServerPort());
             messageBuilder.append(" is bound, ");
        }
        else 
        {
             exampleArchivesServerStatusLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/resources/circleGrey24x24.png")));
             exampleArchivesServerStatusLabel.setToolTipText(PORT_OPEN + X3dEditUserPreferences.getExampleArchivesServerPort());
             messageBuilder.append(" not bound, ");
        } 
        // ActiveX3dModel 
        int index = 0;
        if (activeX3dModelServerSelection > 0)
        {
            portActiveX3dModelServerTextField.setText(activeX3dModelPortList.get(activeX3dModelServerSelection - 1));
        //  activeX3dModelNameList.get     (activeX3dModelServerSelection - 1);
        //  activeX3dModelDirectoryList.get(activeX3dModelServerSelection - 1); // should already be displayed by combobox
        //  activeX3dModelProcessList.get  (activeX3dModelServerSelection - 1);
        }
        else // default combobox selection
        {
            portActiveX3dModelServerTextField.setText("");
        }
        String currentActiveX3dModelPortValue = portActiveX3dModelServerTextField.getText();
        messageBuilder.append("activeX3dModel port=").append(currentActiveX3dModelPortValue);
        if  (!currentActiveX3dModelPortValue.isEmpty() &&
             isPortBound(Integer.parseInt(currentActiveX3dModelPortValue)))
        {
             activeX3dModelServerStatusLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/resources/circleGreen24x24.png")));
             activeX3dModelServerStatusLabel.setToolTipText(PORT_BOUND + currentActiveX3dModelPortValue);
             messageBuilder.append(" is bound, ");
        }
        else 
        {
             activeX3dModelServerStatusLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/resources/circleGrey24x24.png")));
             activeX3dModelServerStatusLabel.setToolTipText(PORT_OPEN + currentActiveX3dModelPortValue);
             messageBuilder.append(" not bound, ");
        }
        if ((activeX3dModelServerSelection > 0) && (activeX3dModelProcessList.get(activeX3dModelServerSelection) != null)) // running
        {
            // TODO autolaunch
            startActiveX3dModelServerButton.setText(HTTP_RUNNING);
            startActiveX3dModelServerButton.setForeground(darkgreen);
            startActiveX3dModelServerButton.setFont(boldFont);
            startActiveX3dModelServerButton.setEnabled(true);
            
             stopActiveX3dModelServerButton.setEnabled(true);
             stopActiveX3dModelServerButton.setText(HTTP_STOP);
             stopActiveX3dModelServerButton.setForeground(Color.RED); // also BOLD
             messageBuilder.append("with httpProcess running.");
        }
        else // not running
        {
            startActiveX3dModelServerButton.setText(HTTP_START);
            startActiveX3dModelServerButton.setForeground(black);
            startActiveX3dModelServerButton.setFont(plainFont);
            startActiveX3dModelServerButton.setEnabled(true);
            
             stopActiveX3dModelServerButton.setEnabled(false);
             stopActiveX3dModelServerButton.setForeground(black);
             messageBuilder.append(" with httpProcess not running.");
        }
        updateActiveX3dModelDirectoryButtons();
        System.out.println(messageBuilder.toString());
    }
    
    private void checkActiveX3dModelListSizes(String origination)
    {
        int      comboBoxAdditions = activeX3dModelDirectoryServerListComboBox.getItemCount() - 1;
        int      nameListSize = activeX3dModelNameList.size();
        int directoryListSize = activeX3dModelDirectoryList.size();
        int      portListSize = activeX3dModelPortList.size();
        int   processListSize = activeX3dModelProcessList.size();
        if ((comboBoxAdditions != nameListSize) ||
            (comboBoxAdditions != directoryListSize) ||
            (comboBoxAdditions != portListSize) ||
            (comboBoxAdditions != processListSize))
        {
            System.err.println("*** checkListSizes() mismatch error detected by " + origination);
            System.err.println("    comboBoxSize=" + comboBoxAdditions + ", nameListSize="    + nameListSize + ", directoryListSize=" + directoryListSize + 
                                 ", portListSize=" + portListSize + ", processListSize=" + processListSize);
        }
    }
    
//    /** show green if bound (by any http server), grey otherwise */
//    private void indicateAuthorModelsServerPortBound ()
//    {
//        if  (isPortBound(Integer.parseInt(X3dEditUserPreferences.getAuthorModelsServerPort())))
//             authorModelsServerStatusLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/resources/circleGreen24x24.png")));
//        else authorModelsServerStatusLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/resources/circleGrey24x24.png"))); 
//    }
//    /** show green if bound (by any http server), grey otherwise */
//    private void indicateExampleArchivesServerPortBound ()
//    {
//        if  (isPortBound(Integer.parseInt(X3dEditUserPreferences.getExampleArchivesServerPort())))
//             exampleArchivesServerStatusLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/resources/circleGreen24x24.png")));
//        else exampleArchivesServerStatusLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/resources/circleGrey24x24.png"))); 
//    }
//    /** show green if bound (by any http server), grey otherwise */
//    private void indicateActiveX3dModelServerPortBound ()
//    {
//        if  (isPortBound(Integer.parseInt(X3dEditUserPreferences.getExampleArchivesServerPort())))
//             exampleArchivesServerStatusLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/resources/circleGreen24x24.png")));
//        else exampleArchivesServerStatusLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/resources/circleGrey24x24.png"))); 
//    }
    
//    private boolean startJavaHttpProcessOld ()
//    {
//             oldStartJavaServerButton.setEnabled(false);
//         stopAuthorModelsServerButton.setEnabled(true);
////       stopAuthorModelsServerButton.setForeground(Color.black); // also PLAIN
//        return false;
//    }
//    private boolean stopJavaHttpProcessOld ()
//    {
//             oldStartJavaServerButton.setEnabled(true);
//         stopAuthorModelsServerButton.setEnabled(false);
////            stopAuthorModelsServerButton.setForeground(Color.red); // also BOLD
//        return false;
//    }
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
    
    /** local convenience method to sleep in main frame
     * @param duration milliseconds to sleep
     */
    private static void sleep(long duration)
    {
        try {
            Thread.sleep(duration); // msec
        }
        catch (InterruptedException ie)
        {
            Thread.currentThread().interrupt();
        }
    }
    
    // TODO invoke thie method on JVM shutdown
    private static int stopAuthorModelsServer()
    {
        isAliveAuthorModelsServer = false;
        if ((authorModelsHttpServerProcess != null)) //  && authorModelsHttpServerProcess.isAlive()
        {
            // typically these http servers respond supportsNormalTermination=false
            // System.out.println("*** stopAuthorModelsServer() authorModelsHttpServerProcess.supportsNormalTermination=" + authorModelsHttpServerProcess.supportsNormalTermination()); 
            // TODO can we reach in and tell it to stop?  might require implementing http server directly
            // http servers seem to persist, so destroyForcibly()
            int exitValue = authorModelsHttpServerProcess.destroyForcibly().exitValue();
//            // wait a little for for server to stop prior to checking status
//            sleep (500); // msec
            switch (exitValue)
            {
                case 1:
                    message = "localhost http process normal shutdown (exitValue=" + exitValue + ")";
                    break;
                case 2:
                    message = "localhost http process still running   (exitValue=" + exitValue + ")";
                    break;
                default:
                    message = "exitValue=" + exitValue;
                    break;
            }
            System.out.println("*** stopAuthorModelsServer()    " + message);
            return exitValue;
        }
        System.out.println("*** stopAuthorModelsServer() found authorModelsHttpServerProcess null, no response"); 
        return -1;
    }
    // TODO invoke thie method on JVM shutdown
    private static int stopExampleArchivesServer()
    {
        isAliveExampleArchivesServer = false;
        if ((exampleArchivesHttpServerProcess != null)) //  && exampleArchivesHttpServerProcess.isAlive()
        {
            // typically these http servers respond supportsNormalTermination=false
            // System.out.println("*** stopAuthorModelsServer() exampleArchivesHttpServerProcess.supportsNormalTermination=" + exampleArchivesHttpServerProcess.supportsNormalTermination()); 
            // TODO can we reach in and tell it to stop?  might require implementing http server directly
            // http servers seem to persist, so destroyForcibly()
            int exitValue = exampleArchivesHttpServerProcess.destroyForcibly().exitValue();
//            // wait a little for for server to stop prior to checking status
//            sleep (500); // msec
            switch (exitValue)
            {
                case 1:
                    message = "localhost http process normal shutdown (exitValue=" + exitValue + ")";
                    break;
                case 2:
                    message = "localhost http process still running   (exitValue=" + exitValue + ")";
                    break;
                default:
                    message = "exitValue=" + exitValue;
                    break;
            }
            System.out.println("*** stopExampleArchivesServer() " + message);
            return exitValue;
        }
        System.out.println("*** stopExampleArchivesServer() found exampleArchivesHttpServerProcess null, no response"); 
        return -1;
    }
    private void stopActiveX3dModelServers()
    {
        int    exitValue;
        if (activeX3dModelProcessList.isEmpty())
        {
            System.out.println("*** stopActiveX3dModelServers() has empty activeX3dModelProcessList, no localhost server shutdowns needed.");
            return;
        }
        int index = 0;
        for (Process httpServerProcess : activeX3dModelProcessList)
        {
            index++;
            message = activeX3dModelDirectoryList.get(index) + " port=" + 
                      activeX3dModelPortList.get(index);
            // typically these http servers respond supportsNormalTermination=false
            // System.out.println("*** stopAuthorModelsServer() httpServerProcessNew.supportsNormalTermination=" + httpServerProcessNew.supportsNormalTermination()); 
            // TODO can we reach in and tell it to stop?  might require implementing http server directly
            // http servers seem to persist, so destroyForcibly()
            if (httpServerProcess == null)
            {
                exitValue = -1;
                message += " exitValue=" + exitValue + " (httpServerProcess null, not previously running)";
            }
            else
            {
                exitValue = httpServerProcess.destroyForcibly().exitValue();
//              // wait a little for for server to stop prior to checking status
//              sleep (200); // msec
                switch (exitValue)
                {
                    case 1:
                        message += "localhost http process normal shutdown (exitValue=" + exitValue + ")";
                        break;
                    case 2:
                        message += "localhost http process still running   (exitValue=" + exitValue + ")";
                        break;
                    default:
                        message += " exitValue=" + exitValue;
                        break;
                }
            }
            System.out.println("*** stopActiveX3dModelServer() " + message);
        }
    }

    /**
     * Start jwebserver Java Simple Web Server according to command-line invocation
     * @param whichServer AUTHOR_MODELS 1, EXAMPLE_ARCHIVES 2 or ACTIVE_X3D_MODEL 3
     * @param cli command-line invocation
     * @see https://docs.oracle.com/en/java/javase/19/docs/specs/man/jwebserver.html
     * @see https://blogs.oracle.com/javamagazine/post/java-18-simple-web-server
     * @see https://inside.java/2021/12/06/working-with-the-simple-web-server/
     * @see https://openjdk.org/jeps/408
     * @return whether http process is alive
     */
    private static boolean startServer (String whichServer, ArrayList<String> cliCommands, String directoryLocation)
    {
        boolean isAlive = false;
        boolean isBound = false;
        String portValue = "";
        
        if (runtime == null)
            runtime = Runtime.getRuntime(); // singleton since invocation may come via static class method, prior to instance class creation

        // https://stackoverflow.com/questions/4042434/converting-arrayliststring-to-string-in-java
        String[] cliCommandStringArray = cliCommands.toArray(new String[0]);

//        ShutDownHook shutdownHook = new ShutDownHook(); // handled upstream
        // TODO select directory from directoryLocation which includes filename...
        File directoryLocationFile = new File(directoryLocation);
        if  (!directoryLocationFile.isDirectory())
        {
            directoryLocationFile = directoryLocationFile.getParentFile();
            if (!directoryLocationFile.isDirectory())
            {
                // notify user that file location is unworkable
                System.err.println("*** startServer() Unable to start server " + whichServer + " at directoryLocation='" + directoryLocation + "'");
                message = "<html><p align='center'>Unable to start " + whichServer + " at directoryLocation='" + directoryLocation + "'</p> </html>";
                NotifyDescriptor notifyDescriptor = new NotifyDescriptor.Message(message, NotifyDescriptor.INFORMATION_MESSAGE);
                DialogDisplayer.getDefault().notify(notifyDescriptor);
                return false;
            }
            // should be usable now as directory parent of file
        }
        if  (directoryLocationFile.isDirectory())
        {
            System.out.println("*** startServer() " + whichServer + " directoryLocation=" + directoryLocation);
            System.out.println("    startServer() " + 
                    (Arrays.toString(cliCommands.toArray())).replace("[","").replace("]","").replace(",","").replace("[","")
                    + portValue);
            try {
                switch (whichServer)
                {
                    case AUTHOR_MODELS:
//                        processBuilder1 = new ProcessBuilder(cliCommands);
//                        processBuilder1.directory(directoryLocationFile);
//                        authorModelsHttpServerProcess = processBuilder1.start();
                        
                        // TODO how to redirect process output?
                        if (authorModelsHttpServerProcess != null)
                            stopAuthorModelsServer(); // prepare to restart
                        // https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/lang/Runtime.html#exec(java.lang.String%5B%5D,java.lang.String%5B%5D,java.io.File)
                        // null indicates "If envp is null, the subprocess inherits the environment settings of the current process."
                        authorModelsHttpServerProcess = runtime.exec(cliCommandStringArray, null, directoryLocationFile);
                          isAlive = isAliveAuthorModelsServer = authorModelsHttpServerProcess.isAlive();
                          isBound = isAliveAuthorModelsServer; // no need to check port if running
                        portValue = X3dEditUserPreferences.getAuthorModelsServerPort();
                        System.out.println("    startServer() " + whichServer + " authorModelsHttpServerProcess.isAlive()=" + isAlive + " on port=" + portValue);
                        break;

                    case EXAMPLE_ARCHIVES:
                        // TODO how to redirect process output?
                        if (exampleArchivesHttpServerProcess != null)
                            stopExampleArchivesServer(); // prepare to restart
                        exampleArchivesHttpServerProcess = runtime.exec(cliCommandStringArray, null, directoryLocationFile);
                          isAlive = isAliveExampleArchivesServer = exampleArchivesHttpServerProcess.isAlive();
                          isBound = isAliveExampleArchivesServer; // no need to check port if running
                        portValue = X3dEditUserPreferences.getExampleArchivesServerPort();
                        System.out.println("    startServer() " + whichServer + " exampleArchivesHttpServerProcess.isAlive()=" + isAlive + " on port=" + portValue);
                        break;

                    case ACTIVE_X3D_MODEL:
                         // TODO how to redirect process output?
//                        if (httpServerProcessNew != null)
//                            stopActiveX3dModelServers(); // prepare to restart
                        httpServerProcessNew = runtime.exec(cliCommandStringArray, null, directoryLocationFile);
                        if (httpServerProcessNew != null)
                        {
                            isAlive = httpServerProcessNew.isAlive();
                            activeX3dModelProcessList.add(httpServerProcessNew);
                        }
                        else
                        {
                            isAlive = false;
                            activeX3dModelProcessList.add(null);
                            // TODO fix other lists in calling method
                        }
                        // TODO for static method, this probably will not work unless menu selection gets consistently saved with correct default.
                        // alternative design: pass port value as parameter 
                        portValue = getActiveServerPort();
                          isBound = isPortBound(portValue);
                        System.out.println("    startServer() " + whichServer + " httpServerProcessNew.isAlive()=" + isAlive + " on port=" + portValue);
                        break;
                    
                    default:
                        message = "X3dToXhtmlDomConversionFrame internal error, whichServer='" + whichServer + "' is not a legal value, please report this error</p> <br /> <p align='center'>Continuing...</p></html>";
                        NotifyDescriptor notifyDescriptor = new NotifyDescriptor.Message("<html><p align='center'>" + message + "</p></html>",
                                NotifyDescriptor.INFORMATION_MESSAGE);
                        DialogDisplayer.getDefault().notify(notifyDescriptor);
                        System.err.println("*** " + message);
                        return false;
                }
            }
            catch (IOException ex)
            {
                System.err.println("    Problem with startServer() whichServer=" + whichServer + ", port=" + portValue);
                Exceptions.printStackTrace(ex);
            }
            if (!isAlive && !isBound)
            {
                // notify user port is not bound
                message = "New CORS http server has not started, port=" + portValue + " is not bound";
                System.err.println("    Problem with startServer() whichServer=" + whichServer + ", " + message);
                NotifyDescriptor notifyDescriptor = new NotifyDescriptor.Message("<html><p align='center'>" + message + "</p> <br /> <p align='center'>Continuing...</p></html>",
                        NotifyDescriptor.INFORMATION_MESSAGE);
                DialogDisplayer.getDefault().notify(notifyDescriptor);
            }
            else 
            {
                // wait a little for for server to start prior to checking status
                sleep(500); // msec
            }
            return isAlive || isBound; // legacy server might be running and binding port, still ready for action
        }
        else  
        {
            System.err.println("    startServer() failed, incorrect directoryLocation=" + directoryLocation);
            return false;
        }
    }
    /**
     * @link https://dzone.com/articles/simple-http-server-in-java
     */
class LocalFileHandlerOld implements HttpHandler {

    @Override    
    public void handle(HttpExchange httpExchange) throws IOException 
    {
    	String requestParamValue=null; 
    	if("GET".equals(httpExchange.getRequestMethod())) 
    	{ 
    		requestParamValue = handleGetRequest(httpExchange);
    	}
    	handleResponseOld(httpExchange,requestParamValue); 
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

    private void handleResponseOld(HttpExchange httpExchange, String requestParamValue)  throws  IOException
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

    HttpServer originalJavaHttpServer;
//    final String DEFAULT_MODEL_ROOT_DIRECTORY = "local directory tree for model visibility";
    
    private void originalJavaHttpServerClose()
    {
        if (originalJavaHttpServer != null)
        {            
            originalJavaHttpServer.stop(4); // waits, closes, kills thread, exits
            System.out.println("*** CORS httpServer stopped");
            System.out.flush();
            stopAuthorModelsServerButton.setEnabled(false);
//            stopAuthorModelsServerButton.setForeground(Color.black); // also PLAIN
            originalJavaHttpServer = null; // forcibly destroy
            // TODO unbind address
        }
    }
    /** set chooser tab
     * @param newIndex tab to select
     * @see HTML_LAYOUT_TAB
     * @see X3DOM_TAB
     * @see X_ITE_TAB
     * @see CORS_TAB
     */
    public void setPaneIndex(int newIndex)
    {
        if ((newIndex >= 0) && (newIndex <= 3))
        {
            pageIntegrationTabbedPane.setSelectedIndex(newIndex);
//            updatePageIntegrationTabbedPaneState();
        }
    }
        
    /** return ActiveX3dModel index by directory, -1 if not found */
    protected int selectActiveX3dModelIndexByDirectory (String directoryPath)
    {
        if         (activeX3dModelDirectoryList.contains(directoryPath))
             return activeX3dModelDirectoryList.indexOf (directoryPath);
        else return -1;
    }
        
    /** return ActiveX3dModel index by name, -1 if not found */
    protected int selectActiveX3dModelIndexByName     (String modelName)
    {
        if         (activeX3dModelNameList.contains(modelName))
             return activeX3dModelNameList.indexOf (modelName);
        else return -1;
    }
        
    /** return ActiveX3dModel index by name, -1 if not found */
    protected int selectActiveX3dModelIndexByPort     (String portValue)
    {
        if         (activeX3dModelPortList.contains(portValue))
             return activeX3dModelPortList.indexOf (portValue);
        else return -1;
    }
    
    /** perform necessary display updates when switching tabs, this is an event callback */
    final void updatePageIntegrationTabbedPaneState()
    {
        updateValuesInPanel(); // values might have changed from other user actions prior to switching tabs
        switch (pageIntegrationTabbedPane.getSelectedIndex())
        {
            case HTML_LAYOUT_TAB:
                transformModelButton.setEnabled(false);
                x3dToXhtmlDomConversionAction.setReadyForConversion(false);
                break;
            case X3DOM_TAB:
                transformModelButton.setEnabled(true);
                x3dToXhtmlDomConversionAction.setReadyForConversion(true);
                break;
            case X_ITE_TAB:
                transformModelButton.setEnabled(true);
                x3dToXhtmlDomConversionAction.setReadyForConversion(true);
                break;
            case CORS_TAB:
                transformModelButton.setEnabled(false);
                x3dToXhtmlDomConversionAction.setReadyForConversion(false);
                updateValuesInPanel();
                updateIndicationsPortsBoundOnServers();
                break;
        }
    }
    /** Use existing server or launch new local http server when needed, avoiding duplications
     * @param modelName of interest
     * @param modelDirectory of interest
     * @return port number used, otherwise -1 if failed to launch
     */
    protected int launchNewActiveX3dModelServer(String modelName, String modelDirectory)    
    {
        int foundPort;
        // check if needed, prior model might have used directory
        if      (authorModelsDirectoryTextField.getText().equals(modelDirectory) && 
                 isAliveAuthorModelsServer)
        {
            foundPort = Integer.parseInt(portAuthorModelsServerTextField.getText());
            System.out.println("*** launchNewActiveX3dModelServer() directory" + modelDirectory + 
                               " matching authorModelsDirectory, no need to launch new server on port=" + foundPort);
            return foundPort;
        }
        else if (exampleArchivesDirectoryTextField.getText().equals(modelDirectory) && 
                 isAliveExampleArchivesServer)
        {
            foundPort = Integer.parseInt(portExampleArchivesServerTextField.getText());
            System.out.println("*** launchNewActiveX3dModelServer() directory" + modelDirectory + 
                               " matching exampleArchivesDirectory, no need to launch new server on port=" + foundPort);
            return foundPort;
        }
        else if (activeX3dModelDirectoryList.contains(modelDirectory))
        {
            activeX3dModelServerSelection = activeX3dModelDirectoryList.indexOf(modelDirectory);
            activeX3dModelServerPort      = activeX3dModelPortList.get(activeX3dModelServerSelection - 1);
            if (isPortBound(activeX3dModelServerPort))
            {
                System.out.println("*** launchNewActiveX3dModelServer() directory " + modelDirectory + 
                                   " matching prior activeX3dModelDirectory, no need to launch new server on port=" + activeX3dModelServerPort);
                return Integer.parseInt(activeX3dModelServerPort);
            }
            else
            {
                System.out.println("*** launchNewActiveX3dModelServer() found directory " + modelDirectory + 
                                   " with localhost http port=" + activeX3dModelServerPort + " not bound as expected, trying again");
                // TODO ? otherwise continue with restart
            }
        }
        // modelDirectory not found supported by any server, add to activeX3dModel list
        // attempt to find an open port, usually they are not bound unless zombie processes are persisting
        int incrementCounter = 1;
        while (isPortBound(nextActiveX3dModelServerPort))
        {
            if (incrementCounter > 10)
            {
                System.err.println("*** launchNewActiveX3dModelServer() unable to find unbound port, transformation cancelled");
                return -1; // failure
            }
            System.err.println("*** launchNewActiveX3dModelServer() nextActiveX3dModelServerPort=" + nextActiveX3dModelServerPort + 
                              " is unexpectedly bound, incrementing and trying again");
            nextActiveX3dModelServerPort++;
            incrementCounter++;
        }
        // open port found, add entry to lists and continue
        portActiveX3dModelServerTextField.setText(String.valueOf(nextActiveX3dModelServerPort));
        activeX3dModelPortList.add(               String.valueOf(nextActiveX3dModelServerPort));
        activeX3dModelServerPort =                  String.valueOf(nextActiveX3dModelServerPort);
        activeX3dModelNameList.add     (modelName);
        activeX3dModelDirectoryList.add(modelDirectory);
        activeX3dModelDirectoryServerListComboBox.setSelectedItem(modelDirectory); // re-selects or adds item as appropriate
        activeX3dModelServerSelection = activeX3dModelDirectoryServerListComboBox.getSelectedIndex();
        
        // https://stackoverflow.com/questions/10954194/start-cmd-by-using-processbuilder
        ArrayList<String> commands = new ArrayList<>();
        commands.clear();
        commands.add("jwebserver");
        commands.add("--bind-address");
        commands.add(addressValue);
        commands.add("--port");
        commands.add(String.valueOf(nextActiveX3dModelServerPort));
        commands.add("--output");
        commands.add("verbose");
        
        boolean isAliveNewModelServer = startServer(ACTIVE_X3D_MODEL, commands, modelDirectory);
        if (isAliveNewModelServer || isPortBound(nextActiveX3dModelServerPort))
        {
            // TODO autolaunch
            startActiveX3dModelServerButton.setText(HTTP_RUNNING);
            startActiveX3dModelServerButton.setForeground(darkgreen);
            startActiveX3dModelServerButton.setFont(boldFont);
            startActiveX3dModelServerButton.setEnabled(true);
            
             stopActiveX3dModelServerButton.setEnabled(true);
             stopActiveX3dModelServerButton.setText(HTTP_STOP);
             stopActiveX3dModelServerButton.setForeground(Color.RED); // also BOLD

            portActiveX3dModelServerTextField.setText(String.valueOf(nextActiveX3dModelServerPort));
            activeX3dModelNameList.add      (modelName);
            activeX3dModelDirectoryList.add (modelDirectory);
            activeX3dModelPortList.add      (String.valueOf(nextActiveX3dModelServerPort));
            activeX3dModelDirectoryServerListComboBox.addItem(modelName);
            updateIndicationsPortsBoundOnServers();
            return nextActiveX3dModelServerPort;
        }
        else
        {
            System.err.println ("*** X3dToXhtmlDomConversionFrame launchNewActiveX3dModelServer(" + modelName + ", " + modelDirectory + ") failed to start");
            return -1; // failure
        }
    }
    public void setUrlData (String urlData)
    {
        urlList.setUrlData (urlData);
    }

    /**
     * @return the activeX3dModelServerSelection
     */
    public static int getActiveServerSelection()
    {
        return activeX3dModelServerSelection;
    }
    /**
     * @return the activeX3dModelServerSelection port
     */
    public static String getActiveServerPort()
    {
        return activeX3dModelServerPort;
    }
    /**
     * @return the activeX3dModelServerSelection port
     */
    public static int getActiveServerPortValue()
    {
        return Integer.parseInt(activeX3dModelServerPort);
    }
}
