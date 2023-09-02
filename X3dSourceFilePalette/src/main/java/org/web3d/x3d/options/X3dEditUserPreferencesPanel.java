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
package org.web3d.x3d.options;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.awt.Window;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.web3d.x3d.DownloadX3dExamplesArchivesAction;
import static org.web3d.x3d.actions.BaseViewAction.X3D_CANONICALIZATION_C14N;
import static org.web3d.x3d.actions.BaseViewAction.X3D_CANONICALIZATION_C14N_SPECIFICATION;
import static org.web3d.x3d.actions.BaseViewAction.X3D_RESOURCES_SECURITY;
import static org.web3d.x3d.actions.BaseViewAction.X3D_RESOURCES_SECURITY_VULNERABILITIES;
import org.web3d.x3d.actions.CommandExecutionScripts;
import org.web3d.x3d.actions.LaunchIssueReportEmailAction;
import org.web3d.x3d.actions.ViewX3dSecurityExamplesOnlineAction;
import org.web3d.x3d.actions.security.ManageKeyStoreAction;
import static org.web3d.x3d.options.X3dEditUserPreferences.EXAMPLES_ROOT_DIRECTORY_DEFAULT;
import static org.web3d.x3d.options.X3dEditUserPreferences.getKeystoreFileNameDefault;
import static org.web3d.x3d.options.X3dEditUserPreferences.getKeystorePassword;
import static org.web3d.x3d.options.X3dEditUserPreferences.getKeystorePasswordDefault;
import static org.web3d.x3d.options.X3dEditUserPreferences.getKeystorePath;
import static org.web3d.x3d.options.X3dEditUserPreferences.setAuthorEmail;
import static org.web3d.x3d.options.X3dEditUserPreferences.setAuthorName;
import static org.web3d.x3d.options.X3dEditUserPreferences.setKeystoreFileName;
import static org.web3d.x3d.options.X3dEditUserPreferences.setKeystorePassword;
import org.web3d.x3d.palette.items.BaseCustomizer;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFColor;

final public class X3dEditUserPreferencesPanel extends javax.swing.JPanel
{
  private static boolean escapeSpaces = false;

  static {
    String os = System.getProperty("os.name");
    if (os.startsWith("Windows"))
      escapeSpaces = true;
  }
  private static String  launcherExampleScenePath = "X3dExamples/LauncherTestScene.x3d";    // This path is setup in X3D layer.xml
  private static File    launcherExampleSceneFile = null;
  private        File    checkExistingFile;
  private        boolean isExecutableFile;  // might be directory, depending on the check
  private        boolean isReachableWebsite;
  private final X3dOptionsPanelController controller;
  
  public static final int AUTHOR_INFO_PANE               = 0;
  public static final int X3D_PLAYERS_PANE               = 1;
  public static final int X3D_MODELING_TOOLS_PANE        = 2;
  public static final int IMAGE_VOLUME_TOOLS_PANE        = 3;
  public static final int WEB_MULTIMEDIA_TOOLS_PANE      = 4;
  public static final int VISUALIZATION_PREFERENCES_PANE = 5;
  public static final int CAD_FILTER_OPTIONS_PANE        = 6;
  public static final int XML_SECURITY_PANE              = 7;
    
  // https://docs.oracle.com/en/java/javase/19/docs/api/java.desktop/javax/swing/JTabbedPane.html#setSelectedIndex(int)
  private int preferredPaneIndex = -1; // initial pane at at launch, does existing class remember prior setting?
  
  private final String EXAMPLES_DIRECTORY_TF_DEFAULT_MESSAGE = "(root directory of X3D Examples Archives, first initialized by example model archives download or user)";

  X3dEditUserPreferencesPanel(int initialPanelIndex)
  {
      this(null);
      if (initialPanelIndex >= 0)
          preferredPaneIndex = initialPanelIndex;
      if (initialPanelIndex != -1)
          x3dOptionsTabbedPane.setSelectedIndex(initialPanelIndex);
  }

  X3dEditUserPreferencesPanel()
  {
      this(null);
  }

  X3dEditUserPreferencesPanel(X3dOptionsPanelController controller)
  {
    this.controller = controller;
    initComponents();
    
    // MuseHub - What does it do and can it really cause harm? In search of facts. jimfoster - Jan 6, 2023 
    // https://musescore.org/en/node/341517
    // MuseHub removed 31 JUL 2023 due to untrustworthy unadvertised dependency on BitTorrent P2P
    
    // adjust to match, if needed
    panelFontName = newX3dModelsDirectoryDescriptionLabel2.getFont().getFontName();
    panelFontSize = newX3dModelsDirectoryDescriptionLabel2.getFont().getSize();
    panelFontPlain          = new Font (panelFontName, Font.PLAIN, panelFontSize); 
    panelFontBold      = new Font (panelFontName, Font.BOLD,  panelFontSize);
    
    hideBSContactGeoComponents (); // superfluous, duplicative

    load ();  // restore saved defaults to panel
    autoLaunchChecks ();
  }
  
  public static String panelFontName = "Segoe UI";
  public static int    panelFontSize = 12;
  public static Font   panelFontPlain     = new Font ("Segoe UI", Font.PLAIN, 12);
  public static Font   panelFontBold = new Font ("Segoe UI", Font.BOLD,  12);
  // https://www.colorhexa.com/2e8b57
  public static final Color colorSeaGreen  = new Color (18,55,34);
  public static final Color colorPaleGreen = new Color (235,255,245);
  
  private void showFound (boolean found, JLabel label, JTextField textField)
  {
      if (found)
      {
        label.setForeground(colorSeaGreen);
        textField.setBackground(colorPaleGreen);
        label.setFont(panelFontBold);
      }
      else
      {
            label.setForeground(Color.BLACK);
        textField.setBackground(Color.WHITE);
             label.setFont(panelFontPlain);
      }
  }
  private void showFound (boolean found, JTextField textField)
  {
      if (found)
      {
        textField.setForeground(colorSeaGreen);
        textField.setBackground(colorPaleGreen);
        textField.setFont(panelFontBold);
      }
      else
      {
        textField.setForeground(Color.BLACK);
        textField.setBackground(Color.WHITE);
        textField.setFont(panelFontPlain);
      }
  }

  private void hideHeilanComponents ()
  {
            heilanCheckBox.setVisible(false);
       heilanChooserButton.setVisible(false);
       heilanDefaultButton.setVisible(false);
      heilanDownloadButton.setVisible(false);
        heilanLaunchButton.setVisible(false);
               heilanLabel.setVisible(false);
                  heilanTF.setVisible(false);
  }

  private void hideBSContactGeoComponents ()
  {
             contactGeoLabel.setVisible(false);
            contactGeoCheckBox.setVisible(false);
       contactGeoChooserButton.setVisible(false);
       contactGeoDefaultButton.setVisible(false);
      contactGeoDownloadButton.setVisible(false);
        contactGeoLaunchButton.setVisible(false);
                  contactGeoTF.setVisible(false);
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        x3dOptionsTabbedPane = new javax.swing.JTabbedPane();
        authorSettingsPanel = new javax.swing.JPanel();
        verticalSpacerLabel20 = new javax.swing.JLabel();
        authorNameLabel = new javax.swing.JLabel();
        authorNameTextField = new javax.swing.JTextField();
        authorNameClearButton = new javax.swing.JButton();
        authorEmailLabel = new javax.swing.JLabel();
        authorEmailTextField = new javax.swing.JTextField();
        authorEmailClearButton = new javax.swing.JButton();
        verticalSpacerLabel17 = new javax.swing.JLabel();
        newX3dModelsDirectoryDescriptionLabel1 = new javax.swing.JLabel();
        newX3dModelsDirectoryDescriptionLabel2 = new javax.swing.JLabel();
        newX3dModelsDirectoryLocationLabel = new javax.swing.JLabel();
        newX3dModelsDirectoryTF = new javax.swing.JTextField();
        newX3dModelsDirectoryClearButton = new javax.swing.JButton();
        newX3dModelsDirectoryButton = new javax.swing.JButton();
        newX3dModelsDirectoryDefaultButton = new javax.swing.JButton();
        verticalSpacerLabel21 = new javax.swing.JLabel();
        authorExamplesLocationLabel = new javax.swing.JLabel();
        authorExamplesDirectoryDescriptionLabel1 = new javax.swing.JLabel();
        authorExamplesDirectoryDescriptionLabel2 = new javax.swing.JLabel();
        authorExamplesDirectoryTF = new javax.swing.JTextField();
        authorExamplesDirectoryClearButton = new javax.swing.JButton();
        authorExamplesDirectoryButton = new javax.swing.JButton();
        authorExamplesDirectoryDefaultButton = new javax.swing.JButton();
        downloadLocalExamplesArchivesButton = new javax.swing.JButton();
        verticalSpacerLabel19 = new javax.swing.JLabel();
        reportAuthorButton = new javax.swing.JButton();
        x3dPlayerPathsPanel = new javax.swing.JPanel();
        contactLabel = new javax.swing.JLabel();
        contactGeoLabel = new javax.swing.JLabel();
        freeWrlLabel = new javax.swing.JLabel();
        heilanLabel = new javax.swing.JLabel();
        instantRealityLabel = new javax.swing.JLabel();
        octagaLabel = new javax.swing.JLabel();
        swirlx3dLabel = new javax.swing.JLabel();
        view3dsceneLabel = new javax.swing.JLabel();
        vivatyLabel = new javax.swing.JLabel();
        xj3dLabel = new javax.swing.JLabel();
        otherPlayerLabel = new javax.swing.JLabel();
        otherPlayerNameLabel = new javax.swing.JLabel();
        contactCheckBox = new javax.swing.JCheckBox();
        contactGeoCheckBox = new javax.swing.JCheckBox();
        freeWrlCheckBox = new javax.swing.JCheckBox();
        heilanCheckBox = new javax.swing.JCheckBox();
        instantRealityCheckBox = new javax.swing.JCheckBox();
        octagaCheckBox = new javax.swing.JCheckBox();
        view3dsceneCheckBox = new javax.swing.JCheckBox();
        vivatyCheckBox = new javax.swing.JCheckBox();
        swirlx3dCheckBox = new javax.swing.JCheckBox();
        xj3dCheckBox = new javax.swing.JCheckBox();
        otherX3dPlayerCheckBox = new javax.swing.JCheckBox();
        contactTF = new javax.swing.JTextField();
        contactGeoTF = new javax.swing.JTextField();
        freeWrlTF = new javax.swing.JTextField();
        heilanTF = new javax.swing.JTextField();
        instantRealityTF = new javax.swing.JTextField();
        octagaTF = new javax.swing.JTextField();
        swirlx3dTF = new javax.swing.JTextField();
        view3dsceneTF = new javax.swing.JTextField();
        vivatyTF = new javax.swing.JTextField();
        xj3dTF = new javax.swing.JTextField();
        otherX3dPlayerPathTF = new javax.swing.JTextField();
        otherX3dPlayerNameTF = new javax.swing.JTextField();
        contactChooserButton = new javax.swing.JButton();
        contactGeoChooserButton = new javax.swing.JButton();
        freeWrlChooserButton = new javax.swing.JButton();
        heilanChooserButton = new javax.swing.JButton();
        instantRealityChooserButton = new javax.swing.JButton();
        octagaChooserButton = new javax.swing.JButton();
        swirlx3dChooserButton = new javax.swing.JButton();
        view3dsceneChooserButton = new javax.swing.JButton();
        vivatyChooserButton = new javax.swing.JButton();
        xj3dChooserButton = new javax.swing.JButton();
        otherChooserButton = new javax.swing.JButton();
        contactDefaultButton = new javax.swing.JButton();
        contactGeoDefaultButton = new javax.swing.JButton();
        freeWrlDefaultButton = new javax.swing.JButton();
        heilanDefaultButton = new javax.swing.JButton();
        instantRealityDefaultButton = new javax.swing.JButton();
        octagaDefaultButton = new javax.swing.JButton();
        swirlx3dDefaultButton = new javax.swing.JButton();
        view3dsceneDefaultButton = new javax.swing.JButton();
        vivatyDefaultButton = new javax.swing.JButton();
        xj3dDefaultButton = new javax.swing.JButton();
        otherX3dPlayerClearButton = new javax.swing.JButton();
        contactDownloadButton = new javax.swing.JButton();
        contactGeoDownloadButton = new javax.swing.JButton();
        freeWrlDownloadButton = new javax.swing.JButton();
        heilanDownloadButton = new javax.swing.JButton();
        instantRealityDownloadButton = new javax.swing.JButton();
        octagaDownloadButton = new javax.swing.JButton();
        swirlx3dDownloadButton = new javax.swing.JButton();
        view3dsceneDownloadButton = new javax.swing.JButton();
        vivatyDownloadButton = new javax.swing.JButton();
        xj3dDownloadButton = new javax.swing.JButton();
        otherX3dPlayerDownloadButton = new javax.swing.JButton();
        contactLaunchButton = new javax.swing.JButton();
        contactGeoLaunchButton = new javax.swing.JButton();
        freeWrlLaunchButton = new javax.swing.JButton();
        heilanLaunchButton = new javax.swing.JButton();
        instantRealityLaunchButton = new javax.swing.JButton();
        octagaLaunchButton = new javax.swing.JButton();
        swirlx3dLaunchButton = new javax.swing.JButton();
        view3dsceneLaunchButton = new javax.swing.JButton();
        vivatyLaunchButton = new javax.swing.JButton();
        xj3dLaunchButton = new javax.swing.JButton();
        otherX3dPlayerLaunchButton = new javax.swing.JButton();
        h3dLabel = new javax.swing.JLabel();
        h3dCheckBox = new javax.swing.JCheckBox();
        h3dTF = new javax.swing.JTextField();
        h3dChooserButton = new javax.swing.JButton();
        h3dDefaultButton = new javax.swing.JButton();
        h3dLaunchButton = new javax.swing.JButton();
        h3dDownloadButton = new javax.swing.JButton();
        verticalSpacerLabel10 = new javax.swing.JLabel();
        launchIntervalLabel = new javax.swing.JLabel();
        launchIntervalTF = new javax.swing.JTextField();
        secondsLabel = new javax.swing.JLabel();
        verticalSpacerLabel1 = new javax.swing.JLabel();
        externalX3dEditorLabel1 = new javax.swing.JLabel();
        verticalSpacerLabel8 = new javax.swing.JLabel();
        defunctX3dEditorLabel = new javax.swing.JLabel();
        reportPlayerButton = new javax.swing.JButton();
        x3dModelingToolsPanel = new javax.swing.JPanel();
        externalX3dEditorLabel = new javax.swing.JLabel();
        verticalSpacerLabel9 = new javax.swing.JLabel();
        altovaXMLSpyLabel = new javax.swing.JLabel();
        altovaXMLSpyCheckBox = new javax.swing.JCheckBox();
        altovaXMLSpyTF = new javax.swing.JTextField();
        altovaXMLSpyChooserButton = new javax.swing.JButton();
        altovaXMLSpyDefaultButton = new javax.swing.JButton();
        altovaXMLSpyLaunchButton = new javax.swing.JButton();
        altovaXMLSpyDownloadButton = new javax.swing.JButton();
        altovaXMLSpyHelpButton = new javax.swing.JButton();
        blenderX3dEditorLabel = new javax.swing.JLabel();
        blenderX3dEditorCheckBox = new javax.swing.JCheckBox();
        blenderX3dEditorPathTF = new javax.swing.JTextField();
        blenderX3dEditorChooserButton = new javax.swing.JButton();
        blenderX3dEditorDefaultButton = new javax.swing.JButton();
        blenderX3dEditorLaunchButton = new javax.swing.JButton();
        blenderX3dEditorDownloadButton = new javax.swing.JButton();
        blenderX3dEditorHelpButton = new javax.swing.JButton();
        bsContentStudioX3dEditorLabel = new javax.swing.JLabel();
        bsContentStudioX3dEditorCheckBox = new javax.swing.JCheckBox();
        bsContentStudioX3dEditorPathTF = new javax.swing.JTextField();
        bsContentStudioX3dEditorChooserButton = new javax.swing.JButton();
        bsContentStudioX3dEditorDefaultButton = new javax.swing.JButton();
        bsContentStudioX3dEditorLaunchButton = new javax.swing.JButton();
        bsContentStudioX3dEditorDownloadButton = new javax.swing.JButton();
        bsContentStudioX3dEditorHelpButton = new javax.swing.JButton();
        bvhackerEditorLabel = new javax.swing.JLabel();
        bvhackerEditorCheckBox = new javax.swing.JCheckBox();
        bvhackerEditorPathTF = new javax.swing.JTextField();
        bvhackerEditorChooserButton = new javax.swing.JButton();
        bvhackerEditorDefaultButton = new javax.swing.JButton();
        bvhackerEditorLaunchButton = new javax.swing.JButton();
        bvhackerEditorDownloadButton = new javax.swing.JButton();
        bvhackerEditorHelpButton = new javax.swing.JButton();
        curaX3dEditorLabel = new javax.swing.JLabel();
        curaX3dEditorCheckBox = new javax.swing.JCheckBox();
        curaX3dEditorPathTF = new javax.swing.JTextField();
        curaX3dEditorChooserButton = new javax.swing.JButton();
        curaX3dEditorDefaultButton = new javax.swing.JButton();
        curaX3dEditorLaunchButton = new javax.swing.JButton();
        curaX3dEditorDownloadButton = new javax.swing.JButton();
        curaX3dEditorHelpButton = new javax.swing.JButton();
        meshLabX3dEditorLabel = new javax.swing.JLabel();
        meshLabX3dEditorCheckBox = new javax.swing.JCheckBox();
        meshLabX3dEditorPathTF = new javax.swing.JTextField();
        meshLabX3dEditorChooserButton = new javax.swing.JButton();
        meshLabX3dEditorDefaultButton = new javax.swing.JButton();
        meshLabX3dEditorLaunchButton = new javax.swing.JButton();
        meshLabX3dEditorDownloadButton = new javax.swing.JButton();
        meshLabX3dEditorHelpButton = new javax.swing.JButton();
        paraviewX3dEditorLabel = new javax.swing.JLabel();
        paraviewX3dEditorCheckBox = new javax.swing.JCheckBox();
        paraviewX3dEditorPathTF = new javax.swing.JTextField();
        paraviewX3dEditorChooserButton = new javax.swing.JButton();
        paraviewX3dEditorDefaultButton = new javax.swing.JButton();
        paraviewX3dEditorLaunchButton = new javax.swing.JButton();
        paraviewX3dEditorDownloadButton = new javax.swing.JButton();
        paraviewX3dEditorHelpButton = new javax.swing.JButton();
        polyTransNuGrafEditorLabel = new javax.swing.JLabel();
        polyTransNuGrafEditorCheckBox = new javax.swing.JCheckBox();
        polyTransNuGrafEditorPathTF = new javax.swing.JTextField();
        polyTransNuGrafEditorChooserButton = new javax.swing.JButton();
        polyTransNuGrafEditorDefaultButton = new javax.swing.JButton();
        polyTransNuGrafEditorLaunchButton = new javax.swing.JButton();
        polyTransNuGrafEditorDownloadButton = new javax.swing.JButton();
        polyTransNuGrafEditorHelpButton = new javax.swing.JButton();
        titaniaX3dEditorLabel = new javax.swing.JLabel();
        titaniaX3dEditorCheckBox = new javax.swing.JCheckBox();
        titaniaX3dEditorPathTF = new javax.swing.JTextField();
        titaniaX3dEditorChooserButton = new javax.swing.JButton();
        titaniaX3dEditorDefaultButton = new javax.swing.JButton();
        titaniaX3dEditorLaunchButton = new javax.swing.JButton();
        titaniaX3dEditorDownloadButton = new javax.swing.JButton();
        titaniaX3dEditorHelpButton = new javax.swing.JButton();
        seamless3dX3dEditorLabel = new javax.swing.JLabel();
        seamless3dX3dEditorCheckBox = new javax.swing.JCheckBox();
        seamless3dX3dEditorPathTF = new javax.swing.JTextField();
        seamless3dX3dEditorChooserButton = new javax.swing.JButton();
        seamless3dX3dEditorDefaultButton = new javax.swing.JButton();
        seamless3dX3dEditorLaunchButton = new javax.swing.JButton();
        seamless3dX3dEditorDownloadButton = new javax.swing.JButton();
        seamless3dX3dEditorHelpButton = new javax.swing.JButton();
        ultraEditX3dEditorLabel = new javax.swing.JLabel();
        ultraEditX3dEditorCheckBox = new javax.swing.JCheckBox();
        ultraEditX3dEditorPathTF = new javax.swing.JTextField();
        ultraEditX3dEditorChooserButton = new javax.swing.JButton();
        ultraEditX3dEditorDefaultButton = new javax.swing.JButton();
        ultraEditX3dEditorLaunchButton = new javax.swing.JButton();
        ultraEditX3dEditorDownloadButton = new javax.swing.JButton();
        ultraEditHelpButton = new javax.swing.JButton();
        whiteDuneX3dEditorLabel = new javax.swing.JLabel();
        whiteDuneX3dEditorCheckBox = new javax.swing.JCheckBox();
        whiteDuneX3dEditorPathTF = new javax.swing.JTextField();
        whiteDuneX3dEditorChooserButton = new javax.swing.JButton();
        whiteDuneX3dEditorDefaultButton = new javax.swing.JButton();
        whiteDuneX3dEditorLaunchButton = new javax.swing.JButton();
        whiteDuneX3dEditorDownloadButton = new javax.swing.JButton();
        whiteDuneX3dEditorHelpButton = new javax.swing.JButton();
        wings3dX3dEditorLabel = new javax.swing.JLabel();
        wings3dX3dEditorCheckBox = new javax.swing.JCheckBox();
        wings3dX3dEditorPathTF = new javax.swing.JTextField();
        wings3dX3dEditorChooserButton = new javax.swing.JButton();
        wings3dX3dEditorDefaultButton = new javax.swing.JButton();
        wings3dX3dEditorLaunchButton = new javax.swing.JButton();
        wings3dX3dEditorDownloadButton = new javax.swing.JButton();
        wings3dX3dEditorHelpButton = new javax.swing.JButton();
        verticalSpacerLabel16 = new javax.swing.JLabel();
        otherEditorNameLabel = new javax.swing.JLabel();
        otherEditorPathLabel = new javax.swing.JLabel();
        otherX3dEditorNameTF = new javax.swing.JTextField();
        otherX3dEditorCheckBox = new javax.swing.JCheckBox();
        otherX3dEditorPathTF = new javax.swing.JTextField();
        otherX3dEditorChooserButton = new javax.swing.JButton();
        otherX3dEditorClearButton = new javax.swing.JButton();
        otherX3dEditorLaunchButton = new javax.swing.JButton();
        otherX3dEditorDownloadButton = new javax.swing.JButton();
        leftMarginSpacerLabel1 = new javax.swing.JLabel();
        verticalSpacerLabel12 = new javax.swing.JLabel();
        reportModelingToolsButton = new javax.swing.JButton();
        imageVolumeToolsPanel = new javax.swing.JPanel();
        imageToolsLabel = new javax.swing.JLabel();
        gimpEditorLabel = new javax.swing.JLabel();
        gimpCheckBox = new javax.swing.JCheckBox();
        gimpEditorTF = new javax.swing.JTextField();
        gimpEditorChooserButton = new javax.swing.JButton();
        gimpEditorDefaultButton = new javax.swing.JButton();
        gimpEditorLaunchButton = new javax.swing.JButton();
        gimpEditorDownloadButton = new javax.swing.JButton();
        gimpHelpButton = new javax.swing.JButton();
        fijiEditorLabel = new javax.swing.JLabel();
        fijiCheckBox = new javax.swing.JCheckBox();
        fijiEditorTF = new javax.swing.JTextField();
        fijiEditorChooserButton = new javax.swing.JButton();
        fijiEditorDefaultButton = new javax.swing.JButton();
        fijiEditorLaunchButton = new javax.swing.JButton();
        fijiEditorDownloadButton = new javax.swing.JButton();
        fijiHelpButton = new javax.swing.JButton();
        imageJEditorLabel = new javax.swing.JLabel();
        imageJEditorTF = new javax.swing.JTextField();
        imageJCheckBox = new javax.swing.JCheckBox();
        imageJEditorChooserButton = new javax.swing.JButton();
        imageJEditorDefaultButton = new javax.swing.JButton();
        imageJEditorLaunchButton = new javax.swing.JButton();
        imageJEditorDownloadButton = new javax.swing.JButton();
        imageJHelpButton = new javax.swing.JButton();
        imageMagickEditorLabel = new javax.swing.JLabel();
        imageMagickCheckBox = new javax.swing.JCheckBox();
        imageMagickEditorTF = new javax.swing.JTextField();
        imageMagickEditorChooserButton = new javax.swing.JButton();
        imageMagickEditorDefaultButton = new javax.swing.JButton();
        imageMagickEditorLaunchButton = new javax.swing.JButton();
        imageMagickEditorDownloadButton = new javax.swing.JButton();
        imageMagickHelpButton = new javax.swing.JButton();
        verticalSpacerLabel22 = new javax.swing.JLabel();
        otherImageEditorNameTF = new javax.swing.JTextField();
        otherImageEditorCheckBox = new javax.swing.JCheckBox();
        otherImageEditorPathTF = new javax.swing.JTextField();
        otherImageEditorChooserButton = new javax.swing.JButton();
        otherImageEditorClearButton = new javax.swing.JButton();
        otherImageEditorLaunchButton = new javax.swing.JButton();
        otherImageEditorDownloadButton = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        externalVolumeEditorLabel = new javax.swing.JLabel();
        fijiImageJvolumeHintLabel = new javax.swing.JLabel();
        itksnapVolumeEditorLabel = new javax.swing.JLabel();
        itksnapVolumeEditorCheckBox = new javax.swing.JCheckBox();
        itksnapVolumeEditorPathTF = new javax.swing.JTextField();
        itksnapVolumeEditorChooserButton = new javax.swing.JButton();
        itksnapVolumeEditorDefaultButton = new javax.swing.JButton();
        itksnapVolumeEditorLaunchButton = new javax.swing.JButton();
        itksnapVolumeEditorDownloadButton = new javax.swing.JButton();
        itksnapVolumeEditorHelpButton = new javax.swing.JButton();
        seg3dVolumeEditorLabel = new javax.swing.JLabel();
        seg3dVolumeEditorCheckBox = new javax.swing.JCheckBox();
        seg3dVolumeEditorPathTF = new javax.swing.JTextField();
        seg3dVolumeEditorChooserButton = new javax.swing.JButton();
        seg3dVolumeEditorDefaultButton = new javax.swing.JButton();
        seg3dVolumeEditorLaunchButton = new javax.swing.JButton();
        seg3dVolumeEditorDownloadButton = new javax.swing.JButton();
        seg3dVolumeEditorHelpButton = new javax.swing.JButton();
        slicer3dVolumeEditorLabel = new javax.swing.JLabel();
        slicer3dVolumeEditorCheckBox = new javax.swing.JCheckBox();
        slicer3dVolumeEditorPathTF = new javax.swing.JTextField();
        slicer3dVolumeEditorChooserButton = new javax.swing.JButton();
        slicer3dVolumeEditorDefaultButton = new javax.swing.JButton();
        slicer3dVolumeEditorLaunchButton = new javax.swing.JButton();
        slicer3dVolumeEditorDownloadButton = new javax.swing.JButton();
        slicer3dVolumeEditorHelpButtonslicer3dVolume = new javax.swing.JButton();
        verticalSpacerLabel18 = new javax.swing.JLabel();
        otherVolumeEditorNameTF = new javax.swing.JTextField();
        otherVolumeEditorCheckBox = new javax.swing.JCheckBox();
        otherVolumeEditorPathTF = new javax.swing.JTextField();
        otherVolumeChooserButton = new javax.swing.JButton();
        otherVolumeEditorClearButton = new javax.swing.JButton();
        otherVolumeEditorLaunchButton = new javax.swing.JButton();
        otherVolumeEditorDownloadButton = new javax.swing.JButton();
        verticalSpacerLabel14 = new javax.swing.JLabel();
        reportImageVolumeToolsButton = new javax.swing.JButton();
        svgToolHintLabel = new javax.swing.JLabel();
        webMultimediaToolsPanel = new javax.swing.JPanel();
        verticalSpacerLabel7 = new javax.swing.JLabel();
        audioToolsLabel = new javax.swing.JLabel();
        audacityEditorLabel = new javax.swing.JLabel();
        audacityEditorCheckBox = new javax.swing.JCheckBox();
        audacityEditorPathTF = new javax.swing.JTextField();
        audacityEditorChooserButton = new javax.swing.JButton();
        audacityEditorDefaultButton = new javax.swing.JButton();
        audacityEditorLaunchButton = new javax.swing.JButton();
        audacityEditorDownloadButton = new javax.swing.JButton();
        audacityEditorHelpButton = new javax.swing.JButton();
        otherAudioEditorNameTF = new javax.swing.JTextField();
        otherAudioEditorCheckBox = new javax.swing.JCheckBox();
        otherAudioEditorPathTF = new javax.swing.JTextField();
        otherAudioEditorChooserButton = new javax.swing.JButton();
        otherAudioEditorClearButton = new javax.swing.JButton();
        otherAudioEditorLaunchButton = new javax.swing.JButton();
        otherAudioEditorDownloadButton = new javax.swing.JButton();
        jSeparator4 = new javax.swing.JSeparator();
        verticalSpacerLabel13 = new javax.swing.JLabel();
        htmlToolsLabel = new javax.swing.JLabel();
        amayaEditorLabel = new javax.swing.JLabel();
        amayaEditorPathTF = new javax.swing.JTextField();
        amayaEditorCheckBox = new javax.swing.JCheckBox();
        amayaEditorChooserButton = new javax.swing.JButton();
        amayaEditorDefaultButton = new javax.swing.JButton();
        amayaEditorLaunchButton = new javax.swing.JButton();
        amayaEditorDownloadButton = new javax.swing.JButton();
        amayaEditorHelpButton = new javax.swing.JButton();
        batikEditorLabel = new javax.swing.JLabel();
        batikEditorPathTF = new javax.swing.JTextField();
        batikEditorCheckBox = new javax.swing.JCheckBox();
        batikEditorChooserButton = new javax.swing.JButton();
        batikEditorDefaultButton = new javax.swing.JButton();
        batikEditorLaunchButton = new javax.swing.JButton();
        batikEditorHelpButton = new javax.swing.JButton();
        batikEditorDownloadButton = new javax.swing.JButton();
        inkscapeEditorLabel = new javax.swing.JLabel();
        inkscapeEditorPathTF = new javax.swing.JTextField();
        inkscapeEditorCheckBox = new javax.swing.JCheckBox();
        inkscapeEditorChooserButton = new javax.swing.JButton();
        inkscapeEditorDefaultButton = new javax.swing.JButton();
        inkscapeEditorLaunchButton = new javax.swing.JButton();
        inkscapeEditorHelpButton = new javax.swing.JButton();
        inkscapeEditorDownloadButton = new javax.swing.JButton();
        svgeditEditorLabel = new javax.swing.JLabel();
        svgeditEditorPathTF = new javax.swing.JTextField();
        svgeditEditorCheckBox = new javax.swing.JCheckBox();
        svgeditEditorChooserButton = new javax.swing.JButton();
        svgeditEditorDefaultButton = new javax.swing.JButton();
        svgeditEditorLaunchButton = new javax.swing.JButton();
        svgeditEditorDownloadButton = new javax.swing.JButton();
        svgeditEditorHelpButton = new javax.swing.JButton();
        otherHtml5EditorNameTF = new javax.swing.JTextField();
        otherHtml5EditorCheckBox = new javax.swing.JCheckBox();
        otherHtml5EditorPathTF = new javax.swing.JTextField();
        otherHtml5EditorChooserButton = new javax.swing.JButton();
        otherHtml5EditorClearButton = new javax.swing.JButton();
        otherHtml5EditorLaunchButton = new javax.swing.JButton();
        otherHtml5EditorDownloadButton = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        externalVideoEditorLabel = new javax.swing.JLabel();
        verticalSpacerLabel11 = new javax.swing.JLabel();
        vlcPlayerLabel = new javax.swing.JLabel();
        vlcPlayerCheckBox = new javax.swing.JCheckBox();
        vlcPlayerPathTF = new javax.swing.JTextField();
        vlcPlayerChooserButton = new javax.swing.JButton();
        vlcPlayerDefaultButton = new javax.swing.JButton();
        vlcPlayerLaunchButton = new javax.swing.JButton();
        vlcPlayerDownloadButton = new javax.swing.JButton();
        vlcPlayerHelpButton = new javax.swing.JButton();
        otherVideoEditorNameTF = new javax.swing.JTextField();
        otherVideoEditorCheckBox = new javax.swing.JCheckBox();
        otherVideoEditorPathTF = new javax.swing.JTextField();
        otherVideoChooserButton = new javax.swing.JButton();
        otherVideoEditorClearButton = new javax.swing.JButton();
        otherVideoEditorLaunchButton = new javax.swing.JButton();
        otherVideoEditorDownloadButton = new javax.swing.JButton();
        externalOntologyEditorLabel = new javax.swing.JLabel();
        protegePlayerLabel = new javax.swing.JLabel();
        protegePlayerCheckBox = new javax.swing.JCheckBox();
        protegePlayerPathTF = new javax.swing.JTextField();
        protegePlayerChooserButton = new javax.swing.JButton();
        protegePlayerDefaultButton = new javax.swing.JButton();
        protegePlayerLaunchButton = new javax.swing.JButton();
        protegePlayerDownloadButton = new javax.swing.JButton();
        protegePlayerHelpButton = new javax.swing.JButton();
        otherSemanticWebEditorNameTF = new javax.swing.JTextField();
        otherSemanticWebEditorCheckBox = new javax.swing.JCheckBox();
        otherSemanticWebEditorPathTF = new javax.swing.JTextField();
        otherSemanticWebEditorChooserButton = new javax.swing.JButton();
        otherSemanticWebEditorClearButton = new javax.swing.JButton();
        otherSemanticWebEditorLaunchButton = new javax.swing.JButton();
        otherSemanticWebEditorDownloadButton = new javax.swing.JButton();
        leftMarginSpacerLabel = new javax.swing.JLabel();
        verticalSpacerLabel6 = new javax.swing.JLabel();
        reportWebMultimediaToolsButton = new javax.swing.JButton();
        x3dEditVisualizationPreferencesPanel = new javax.swing.JPanel();
        verticalSpacerLabel2 = new javax.swing.JLabel();
        nodeEditingOptionsPanel = new javax.swing.JPanel();
        showNewLineOptionCheckBox = new javax.swing.JCheckBox();
        prependNewLineCheckBox = new javax.swing.JCheckBox();
        appendNewLineCheckBox = new javax.swing.JCheckBox();
        jSeparator1 = new javax.swing.JSeparator();
        autoValidationCheckBox = new javax.swing.JCheckBox();
        horizontalSpacerLabel1 = new javax.swing.JLabel();
        defaultEditorOptionsButton = new javax.swing.JButton();
        verticalSpacerLabel3 = new javax.swing.JLabel();
        visualizationOptionsPanel = new javax.swing.JPanel();
        lineColorLabel = new javax.swing.JLabel();
        lineColorRedTF = new javax.swing.JTextField();
        lineColorGreenTF = new javax.swing.JTextField();
        lineColorBlueTF = new javax.swing.JTextField();
        lineColorChooser = new net.java.dev.colorchooser.ColorChooser();
        shapeColorLabel = new javax.swing.JLabel();
        shapeColorRedTF = new javax.swing.JTextField();
        shapeColorGreenTF = new javax.swing.JTextField();
        shapeColorBlueTF = new javax.swing.JTextField();
        shapeColorChooser = new net.java.dev.colorchooser.ColorChooser();
        transparencyLabel = new javax.swing.JLabel();
        transparencyTF = new javax.swing.JTextField();
        defaultVisualizationSettingsButton = new javax.swing.JButton();
        axesOriginLabel = new javax.swing.JLabel();
        coordinateAxesCheckBox = new javax.swing.JCheckBox();
        coneLabel = new javax.swing.JLabel();
        coneCenterLineCheckBox = new javax.swing.JCheckBox();
        coneLinesLabel = new javax.swing.JLabel();
        coneLinesComboBox = new javax.swing.JComboBox<>();
        verticalSpacerLabel4 = new javax.swing.JLabel();
        hAnimVisualizationOptionsPanel = new javax.swing.JPanel();
        hAnimJointColorLabel = new javax.swing.JLabel();
        hAnimJointColorRedTF = new javax.swing.JTextField();
        hAnimJointColorGreenTF = new javax.swing.JTextField();
        hAnimJointColorBlueTF = new javax.swing.JTextField();
        hAnimJointColorChooser = new net.java.dev.colorchooser.ColorChooser();
        hAnimSegmentColorLabel = new javax.swing.JLabel();
        hAnimSegmentColorRedTF = new javax.swing.JTextField();
        hAnimSegmentColorGreenTF = new javax.swing.JTextField();
        hAnimSegmentColorBlueTF = new javax.swing.JTextField();
        hAnimSegmentColorChooser = new net.java.dev.colorchooser.ColorChooser();
        hAnimDefaultVisualizationSettingsButton = new javax.swing.JButton();
        hAnimVisualizeCoordinateAxesCheckBox = new javax.swing.JCheckBox();
        hAnimSiteColorLabel = new javax.swing.JLabel();
        hAnimSiteColorRedTF = new javax.swing.JTextField();
        hAnimSiteColorGreenTF = new javax.swing.JTextField();
        hAnimSiteColorBlueTF = new javax.swing.JTextField();
        hAnimSiteColorChooser = new net.java.dev.colorchooser.ColorChooser();
        hAnimAxesOriginLabel = new javax.swing.JLabel();
        verticalSpacerLabel5 = new javax.swing.JLabel();
        reportVisualizationPreferencesButton = new javax.swing.JButton();
        xj3dCadFilterOptionsPanel = new org.web3d.x3d.options.Xj3dCadFilterOptionsPanel();
        x3dSecurityPanel = new javax.swing.JPanel();
        keystoreSectionHeaderLabel = new javax.swing.JLabel();
        keystoreManagerDescription1Label = new javax.swing.JLabel();
        keystorePasswordLabel = new javax.swing.JLabel();
        keystorePasswordTF = new javax.swing.JTextField();
        keystorePasswordTFClearButton = new javax.swing.JButton();
        keystorePasswordDefaultButton = new javax.swing.JButton();
        keystoreFileNameLabel = new javax.swing.JLabel();
        keystoreFileNameTF = new javax.swing.JTextField();
        keystoreFileNameTFClearButton = new javax.swing.JButton();
        keystoreFileNameDefaultButton = new javax.swing.JButton();
        keystorePathLabel1 = new javax.swing.JLabel();
        keystorePathLabel2 = new javax.swing.JLabel();
        keystoreDirectoryLabel = new javax.swing.JLabel();
        keystoreDirectoryTF = new javax.swing.JTextField();
        keystoreDirectoryTFClearButton = new javax.swing.JButton();
        keystoreDirectoryButton = new javax.swing.JButton();
        keystoreManageButton = new javax.swing.JButton();
        keystoreExplorerPlayerLaunchButton = new javax.swing.JButton();
        keystoreDefaultButton = new javax.swing.JButton();
        securityExamplesLabel = new javax.swing.JLabel();
        securityResourcesLabel = new javax.swing.JLabel();
        reportSecurityPanelButton = new javax.swing.JButton();
        additionalKeystoreManagersLabel = new javax.swing.JLabel();
        viewSecurityExamplesReadmeButton = new javax.swing.JButton();
        viewX3dResourcesSecurityButton = new javax.swing.JButton();
        viewX3dResourcesSecurityVulnerabilitiesButton = new javax.swing.JButton();
        viewSecurityExamplesReadmeLabel = new javax.swing.JLabel();
        viewX3dResourcesSecurityLabel = new javax.swing.JLabel();
        viewX3dResourcesSecurityVulnerabilitiesLabel = new javax.swing.JLabel();
        verticalSpacerLabel = new javax.swing.JLabel();
        porteclePlayerLabel = new javax.swing.JLabel();
        porteclePlayerPathTF = new javax.swing.JTextField();
        porteclePlayerChooserButton = new javax.swing.JButton();
        porteclePlayerDefaultButton = new javax.swing.JButton();
        porteclePlayerLaunchButton = new javax.swing.JButton();
        porteclePlayerDownloadButton = new javax.swing.JButton();
        porteclePlayerHelpButton = new javax.swing.JButton();
        keystoreExplorerPlayerLabel = new javax.swing.JLabel();
        keystoreExplorerPlayerPathTF = new javax.swing.JTextField();
        keystoreExplorerPlayerChooserButton = new javax.swing.JButton();
        keystoreExplorerPlayerDefaultButton = new javax.swing.JButton();
        keystoreExplorerPlayerDownloadButton = new javax.swing.JButton();
        keystoreExplorerPlayerHelpButton = new javax.swing.JButton();
        keystoreManagerDescription2Label2 = new javax.swing.JLabel();
        viewX3dCanonicalizationC14nReadmeButton = new javax.swing.JButton();
        viewX3dCanonicalizationC14nReadmeLabel = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(850, 600));
        setPreferredSize(new java.awt.Dimension(850, 600));
        setLayout(new java.awt.GridBagLayout());

        x3dOptionsTabbedPane.setToolTipText("Set X3D-Edit preference values");
        x3dOptionsTabbedPane.setMinimumSize(new java.awt.Dimension(825, 600));
        x3dOptionsTabbedPane.setPreferredSize(new java.awt.Dimension(850, 600));

        authorSettingsPanel.setToolTipText("");
        authorSettingsPanel.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(verticalSpacerLabel20, "   ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        authorSettingsPanel.add(verticalSpacerLabel20, gridBagConstraints);

        authorNameLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(authorNameLabel, "Author name");
        authorNameLabel.setToolTipText("optional, used for model meta tags (just like HTML)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        authorSettingsPanel.add(authorNameLabel, gridBagConstraints);

        authorNameTextField.setToolTipText("optional, used for model meta tags (just like HTML)");
        authorNameTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                authorNameTextFieldFocusLost(evt);
            }
        });
        authorNameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                authorNameTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 250;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        authorSettingsPanel.add(authorNameTextField, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(authorNameClearButton, "x");
        authorNameClearButton.setToolTipText("clear content text");
        authorNameClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                authorNameClearButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        authorSettingsPanel.add(authorNameClearButton, gridBagConstraints);

        authorEmailLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(authorEmailLabel, "Author email");
        authorEmailLabel.setToolTipText("optional, used for model meta tags (just like HTML)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        authorSettingsPanel.add(authorEmailLabel, gridBagConstraints);

        authorEmailTextField.setToolTipText("optional, used for model meta tags (just like HTML)");
        authorEmailTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                authorEmailTextFieldFocusLost(evt);
            }
        });
        authorEmailTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                authorEmailTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 250;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        authorSettingsPanel.add(authorEmailTextField, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(authorEmailClearButton, "x");
        authorEmailClearButton.setToolTipText("clear content text");
        authorEmailClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                authorEmailClearButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        authorSettingsPanel.add(authorEmailClearButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(verticalSpacerLabel17, "   ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        authorSettingsPanel.add(verticalSpacerLabel17, gridBagConstraints);

        newX3dModelsDirectoryDescriptionLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(newX3dModelsDirectoryDescriptionLabel1, "New X3D Models");
        newX3dModelsDirectoryDescriptionLabel1.setToolTipText("local path for new models, supports CORS HTTP autolaunch");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        authorSettingsPanel.add(newX3dModelsDirectoryDescriptionLabel1, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(newX3dModelsDirectoryDescriptionLabel2, "default local directory path for authoring");
        newX3dModelsDirectoryDescriptionLabel2.setToolTipText("local path for new models, supports CORS HTTP autolaunch");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        authorSettingsPanel.add(newX3dModelsDirectoryDescriptionLabel2, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(newX3dModelsDirectoryLocationLabel, "location");
        newX3dModelsDirectoryLocationLabel.setToolTipText("Drectory path for new X3D models");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        authorSettingsPanel.add(newX3dModelsDirectoryLocationLabel, gridBagConstraints);

        newX3dModelsDirectoryTF.setText(org.web3d.x3d.options.X3dEditUserPreferences.NEW_X3D_MODELS_DIRECTORY_DEFAULT);
        newX3dModelsDirectoryTF.setToolTipText("Drectory path for new X3D models");
        newX3dModelsDirectoryTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                newX3dModelsDirectoryTFFocusLost(evt);
            }
        });
        newX3dModelsDirectoryTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newX3dModelsDirectoryTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 300;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        authorSettingsPanel.add(newX3dModelsDirectoryTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(newX3dModelsDirectoryClearButton, "x");
        newX3dModelsDirectoryClearButton.setToolTipText("clear content text");
        newX3dModelsDirectoryClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newX3dModelsDirectoryClearButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        authorSettingsPanel.add(newX3dModelsDirectoryClearButton, gridBagConstraints);

        newX3dModelsDirectoryButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(newX3dModelsDirectoryButton, "...");
        newX3dModelsDirectoryButton.setToolTipText("Browse to select file location for new X3D models");
        newX3dModelsDirectoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newX3dModelsDirectoryButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
        authorSettingsPanel.add(newX3dModelsDirectoryButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(newX3dModelsDirectoryDefaultButton, "default");
        newX3dModelsDirectoryDefaultButton.setToolTipText("Reset default file location for new X3D models");
        newX3dModelsDirectoryDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newX3dModelsDirectoryDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        authorSettingsPanel.add(newX3dModelsDirectoryDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(verticalSpacerLabel21, "   ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 10;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        authorSettingsPanel.add(verticalSpacerLabel21, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(authorExamplesLocationLabel, "location");
        authorExamplesLocationLabel.setToolTipText("Drectory path to local copies of X3D Examples Model Archives");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        authorSettingsPanel.add(authorExamplesLocationLabel, gridBagConstraints);

        authorExamplesDirectoryDescriptionLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(authorExamplesDirectoryDescriptionLabel1, "X3D Examples Model Archives");
        authorExamplesDirectoryDescriptionLabel1.setToolTipText("X3D Examples Model Archives, locally downloaded directory path");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        authorSettingsPanel.add(authorExamplesDirectoryDescriptionLabel1, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(authorExamplesDirectoryDescriptionLabel2, "default local directory path for downloading");
        authorExamplesDirectoryDescriptionLabel2.setToolTipText("X3D Examples Model Archives, locally downloaded directory path");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        authorSettingsPanel.add(authorExamplesDirectoryDescriptionLabel2, gridBagConstraints);

        authorExamplesDirectoryTF.setText(EXAMPLES_DIRECTORY_TF_DEFAULT_MESSAGE);
        authorExamplesDirectoryTF.setToolTipText("Drectory path to local copies of X3D Examples Model Archives");
        authorExamplesDirectoryTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                authorExamplesDirectoryTFFocusLost(evt);
            }
        });
        authorExamplesDirectoryTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                authorExamplesDirectoryTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 300;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        authorSettingsPanel.add(authorExamplesDirectoryTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(authorExamplesDirectoryClearButton, "x");
        authorExamplesDirectoryClearButton.setToolTipText("clear content text");
        authorExamplesDirectoryClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                authorExamplesDirectoryClearButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        authorSettingsPanel.add(authorExamplesDirectoryClearButton, gridBagConstraints);

        authorExamplesDirectoryButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(authorExamplesDirectoryButton, "...");
        authorExamplesDirectoryButton.setToolTipText("Browse to select file location for local examples");
        authorExamplesDirectoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                authorExamplesDirectoryButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
        authorSettingsPanel.add(authorExamplesDirectoryButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(authorExamplesDirectoryDefaultButton, "default");
        authorExamplesDirectoryDefaultButton.setToolTipText("Reset default file location for local examples");
        authorExamplesDirectoryDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                authorExamplesDirectoryDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        authorSettingsPanel.add(authorExamplesDirectoryDefaultButton, gridBagConstraints);

        downloadLocalExamplesArchivesButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/resources/Icon_download_32x32.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(downloadLocalExamplesArchivesButton, "Download X3D Examples");
        downloadLocalExamplesArchivesButton.setToolTipText("Download X3D Examples");
        downloadLocalExamplesArchivesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downloadLocalExamplesArchivesButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 3, 3);
        authorSettingsPanel.add(downloadLocalExamplesArchivesButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(verticalSpacerLabel19, "   ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 50.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        authorSettingsPanel.add(verticalSpacerLabel19, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(reportAuthorButton, "Report");
        reportAuthorButton.setToolTipText(BaseCustomizer.MAILTO_TOOLTIP);
        reportAuthorButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportAuthorButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        authorSettingsPanel.add(reportAuthorButton, gridBagConstraints);

        x3dOptionsTabbedPane.addTab("Author", authorSettingsPanel);

        x3dPlayerPathsPanel.setToolTipText("Edit paths to locally installed X3D viewers");
        x3dPlayerPathsPanel.setMinimumSize(new java.awt.Dimension(825, 600));
        x3dPlayerPathsPanel.setPreferredSize(new java.awt.Dimension(825, 600));
        x3dPlayerPathsPanel.setLayout(new java.awt.GridBagLayout());

        contactLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(contactLabel, "BS Contact");
        contactLabel.setToolTipText("Bitmanagement BS Contact Player");
        contactLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        contactLabel.setMinimumSize(new java.awt.Dimension(120, 20));
        contactLabel.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(contactLabel, gridBagConstraints);

        contactGeoLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(contactGeoLabel, "BS Contact Geo");
        contactGeoLabel.setToolTipText("Bitmanagement BS Contact Player");
        contactGeoLabel.setEnabled(false);
        contactGeoLabel.setMinimumSize(new java.awt.Dimension(120, 20));
        contactGeoLabel.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(contactGeoLabel, gridBagConstraints);

        freeWrlLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(freeWrlLabel, "FreeWrl");
        freeWrlLabel.setToolTipText("FreeWrl (Mac, Linux Windows)");
        freeWrlLabel.setMinimumSize(new java.awt.Dimension(120, 20));
        freeWrlLabel.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(freeWrlLabel, gridBagConstraints);

        heilanLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(heilanLabel, "Heilan");
        heilanLabel.setToolTipText("Heilan X3D audiosual player");
        heilanLabel.setMinimumSize(new java.awt.Dimension(120, 20));
        heilanLabel.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(heilanLabel, gridBagConstraints);

        instantRealityLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(instantRealityLabel, " InstantReality");
        instantRealityLabel.setToolTipText("Fraunhofer Instant Reality player");
        instantRealityLabel.setMinimumSize(new java.awt.Dimension(120, 20));
        instantRealityLabel.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(instantRealityLabel, gridBagConstraints);

        octagaLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(octagaLabel, "Octaga");
        octagaLabel.setToolTipText("Octaga player");
        octagaLabel.setMinimumSize(new java.awt.Dimension(120, 20));
        octagaLabel.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(octagaLabel, gridBagConstraints);

        swirlx3dLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(swirlx3dLabel, "SwirlX3D");
        swirlx3dLabel.setToolTipText("SwirlX3D player");
        swirlx3dLabel.setMinimumSize(new java.awt.Dimension(120, 20));
        swirlx3dLabel.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(swirlx3dLabel, gridBagConstraints);

        view3dsceneLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(view3dsceneLabel, "view3dscene");
        view3dsceneLabel.setToolTipText("view3dscene (Castle Game Engine) player");
        view3dsceneLabel.setMinimumSize(new java.awt.Dimension(120, 20));
        view3dsceneLabel.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(view3dsceneLabel, gridBagConstraints);

        vivatyLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(vivatyLabel, "Vivaty");
        vivatyLabel.setToolTipText("Vivaty Player (formerly MediaMachines Flux)");
        vivatyLabel.setMinimumSize(new java.awt.Dimension(120, 20));
        vivatyLabel.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(vivatyLabel, gridBagConstraints);

        xj3dLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(xj3dLabel, "Xj3D");
        xj3dLabel.setToolTipText("Xj3D player (external application)");
        xj3dLabel.setMinimumSize(new java.awt.Dimension(120, 20));
        xj3dLabel.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(xj3dLabel, gridBagConstraints);

        otherPlayerLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(otherPlayerLabel, "Other player path");
        otherPlayerLabel.setToolTipText("Set up an additional X3D player");
        otherPlayerLabel.setMinimumSize(new java.awt.Dimension(120, 20));
        otherPlayerLabel.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(otherPlayerLabel, gridBagConstraints);

        otherPlayerNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(otherPlayerNameLabel, "Other player name");
        otherPlayerNameLabel.setToolTipText("Name of additional X3D player");
        otherPlayerNameLabel.setMinimumSize(new java.awt.Dimension(120, 20));
        otherPlayerNameLabel.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(otherPlayerNameLabel, gridBagConstraints);

        contactCheckBox.setSelected(true);
        contactCheckBox.setToolTipText("Include when autolaunching all browsers");
        contactCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        contactCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contactCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(contactCheckBox, gridBagConstraints);

        contactGeoCheckBox.setToolTipText("Include when autolaunching all browsers");
        contactGeoCheckBox.setEnabled(false);
        contactGeoCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        contactGeoCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contactGeoCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(contactGeoCheckBox, gridBagConstraints);

        freeWrlCheckBox.setSelected(true);
        freeWrlCheckBox.setToolTipText("Include when autolaunching all browsers");
        freeWrlCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        freeWrlCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                freeWrlCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 0);
        x3dPlayerPathsPanel.add(freeWrlCheckBox, gridBagConstraints);

        heilanCheckBox.setToolTipText("Include when autolaunching all browsers");
        heilanCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        heilanCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                heilanCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 0);
        x3dPlayerPathsPanel.add(heilanCheckBox, gridBagConstraints);

        instantRealityCheckBox.setSelected(true);
        instantRealityCheckBox.setToolTipText("Include when autolaunching all browsers");
        instantRealityCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        instantRealityCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                instantRealityCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 0);
        x3dPlayerPathsPanel.add(instantRealityCheckBox, gridBagConstraints);

        octagaCheckBox.setSelected(true);
        octagaCheckBox.setToolTipText("Include when autolaunching all browsers");
        octagaCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        octagaCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                octagaCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 0);
        x3dPlayerPathsPanel.add(octagaCheckBox, gridBagConstraints);

        view3dsceneCheckBox.setSelected(true);
        view3dsceneCheckBox.setToolTipText("Include when autolaunching all browsers");
        view3dsceneCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        view3dsceneCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view3dsceneCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 0);
        x3dPlayerPathsPanel.add(view3dsceneCheckBox, gridBagConstraints);

        vivatyCheckBox.setToolTipText("Include when autolaunching all browsers");
        vivatyCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        vivatyCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vivatyCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 0);
        x3dPlayerPathsPanel.add(vivatyCheckBox, gridBagConstraints);

        swirlx3dCheckBox.setToolTipText("Include when autolaunching all browsers");
        swirlx3dCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        swirlx3dCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                swirlx3dCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 0);
        x3dPlayerPathsPanel.add(swirlx3dCheckBox, gridBagConstraints);

        xj3dCheckBox.setSelected(true);
        xj3dCheckBox.setToolTipText("Include when autolaunching all browsers");
        xj3dCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        xj3dCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xj3dCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 0);
        x3dPlayerPathsPanel.add(xj3dCheckBox, gridBagConstraints);

        otherX3dPlayerCheckBox.setToolTipText("Include when autolaunching all browsers");
        otherX3dPlayerCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        otherX3dPlayerCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherX3dPlayerCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 0);
        x3dPlayerPathsPanel.add(otherX3dPlayerCheckBox, gridBagConstraints);

        contactTF.setToolTipText("Local file location");
        contactTF.setMinimumSize(new java.awt.Dimension(100, 20));
        contactTF.setPreferredSize(new java.awt.Dimension(100, 20));
        contactTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                contactTFFocusLost(evt);
            }
        });
        contactTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contactTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(contactTF, gridBagConstraints);

        contactGeoTF.setToolTipText("Local file location");
        contactGeoTF.setEnabled(false);
        contactGeoTF.setMinimumSize(new java.awt.Dimension(100, 20));
        contactGeoTF.setPreferredSize(new java.awt.Dimension(100, 20));
        contactGeoTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contactGeoTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(contactGeoTF, gridBagConstraints);

        freeWrlTF.setToolTipText("Local file location");
        freeWrlTF.setMinimumSize(new java.awt.Dimension(100, 20));
        freeWrlTF.setPreferredSize(new java.awt.Dimension(100, 20));
        freeWrlTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                freeWrlTFFocusLost(evt);
            }
        });
        freeWrlTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                freeWrlTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(freeWrlTF, gridBagConstraints);

        heilanTF.setToolTipText("Local file location");
        heilanTF.setMinimumSize(new java.awt.Dimension(100, 20));
        heilanTF.setPreferredSize(new java.awt.Dimension(100, 20));
        heilanTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                heilanTFFocusLost(evt);
            }
        });
        heilanTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                heilanTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(heilanTF, gridBagConstraints);

        instantRealityTF.setToolTipText("Local file location");
        instantRealityTF.setMinimumSize(new java.awt.Dimension(100, 20));
        instantRealityTF.setPreferredSize(new java.awt.Dimension(100, 20));
        instantRealityTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                instantRealityTFFocusLost(evt);
            }
        });
        instantRealityTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                instantRealityTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(instantRealityTF, gridBagConstraints);

        octagaTF.setToolTipText("Local file location");
        octagaTF.setMinimumSize(new java.awt.Dimension(100, 20));
        octagaTF.setPreferredSize(new java.awt.Dimension(100, 20));
        octagaTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                octagaTFFocusLost(evt);
            }
        });
        octagaTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                octagaTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(octagaTF, gridBagConstraints);

        swirlx3dTF.setToolTipText("Local file location");
        swirlx3dTF.setMinimumSize(new java.awt.Dimension(100, 20));
        swirlx3dTF.setPreferredSize(new java.awt.Dimension(100, 20));
        swirlx3dTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                swirlx3dTFFocusLost(evt);
            }
        });
        swirlx3dTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                swirlx3dTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(swirlx3dTF, gridBagConstraints);

        view3dsceneTF.setToolTipText("Local file location");
        view3dsceneTF.setMinimumSize(new java.awt.Dimension(100, 20));
        view3dsceneTF.setPreferredSize(new java.awt.Dimension(100, 20));
        view3dsceneTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                view3dsceneTFFocusLost(evt);
            }
        });
        view3dsceneTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view3dsceneTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(view3dsceneTF, gridBagConstraints);

        vivatyTF.setToolTipText("Local file location");
        vivatyTF.setMinimumSize(new java.awt.Dimension(100, 20));
        vivatyTF.setPreferredSize(new java.awt.Dimension(100, 20));
        vivatyTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                vivatyTFFocusLost(evt);
            }
        });
        vivatyTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vivatyTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(vivatyTF, gridBagConstraints);

        xj3dTF.setToolTipText("Local file location");
        xj3dTF.setMinimumSize(new java.awt.Dimension(100, 20));
        xj3dTF.setPreferredSize(new java.awt.Dimension(100, 20));
        xj3dTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                xj3dTFFocusLost(evt);
            }
        });
        xj3dTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xj3dTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(xj3dTF, gridBagConstraints);

        otherX3dPlayerPathTF.setToolTipText("Local file location of additional X3D player");
        otherX3dPlayerPathTF.setMinimumSize(new java.awt.Dimension(100, 20));
        otherX3dPlayerPathTF.setPreferredSize(new java.awt.Dimension(100, 20));
        otherX3dPlayerPathTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                otherX3dPlayerPathTFFocusLost(evt);
            }
        });
        otherX3dPlayerPathTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherX3dPlayerPathTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(otherX3dPlayerPathTF, gridBagConstraints);

        otherX3dPlayerNameTF.setToolTipText("Name of additional X3D player");
        otherX3dPlayerNameTF.setMinimumSize(new java.awt.Dimension(100, 20));
        otherX3dPlayerNameTF.setPreferredSize(new java.awt.Dimension(100, 20));
        otherX3dPlayerNameTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                otherX3dPlayerNameTFFocusLost(evt);
            }
        });
        otherX3dPlayerNameTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherX3dPlayerNameTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(otherX3dPlayerNameTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(contactChooserButton, "...");
        contactChooserButton.setToolTipText("Find local file location");
        contactChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contactChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(contactChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(contactGeoChooserButton, "...");
        contactGeoChooserButton.setToolTipText("Find local file location");
        contactGeoChooserButton.setEnabled(false);
        contactGeoChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contactGeoChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(contactGeoChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(freeWrlChooserButton, "...");
        freeWrlChooserButton.setToolTipText("Find local file location");
        freeWrlChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                freeWrlChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(freeWrlChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(heilanChooserButton, "...");
        heilanChooserButton.setToolTipText("Find local file location");
        heilanChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                heilanChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(heilanChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(instantRealityChooserButton, "...");
        instantRealityChooserButton.setToolTipText("Find local file location");
        instantRealityChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                instantRealityChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(instantRealityChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(octagaChooserButton, "...");
        octagaChooserButton.setToolTipText("Find local file location");
        octagaChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                octagaChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(octagaChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(swirlx3dChooserButton, "...");
        swirlx3dChooserButton.setToolTipText("Find local file location");
        swirlx3dChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                swirlx3dChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(swirlx3dChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(view3dsceneChooserButton, "...");
        view3dsceneChooserButton.setToolTipText("Find local file location");
        view3dsceneChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view3dsceneChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(view3dsceneChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(vivatyChooserButton, "...");
        vivatyChooserButton.setToolTipText("Find local file location");
        vivatyChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vivatyChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(vivatyChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(xj3dChooserButton, "...");
        xj3dChooserButton.setToolTipText("Find local file location");
        xj3dChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xj3dChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(xj3dChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(otherChooserButton, "...");
        otherChooserButton.setToolTipText("Find local file location");
        otherChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(otherChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(contactDefaultButton, "default");
        contactDefaultButton.setToolTipText("Reset default file location");
        contactDefaultButton.setActionCommand(org.web3d.x3d.options.X3dEditUserPreferences.getContactPathDefault());
        contactDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contactDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(contactDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(contactGeoDefaultButton, "default");
        contactGeoDefaultButton.setToolTipText("Reset default file location");
        contactGeoDefaultButton.setActionCommand(org.web3d.x3d.options.X3dEditUserPreferences.getContactGeoPathDefault());
        contactGeoDefaultButton.setEnabled(false);
        contactGeoDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contactGeoDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(contactGeoDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(freeWrlDefaultButton, "default");
        freeWrlDefaultButton.setToolTipText("Reset default file location");
        freeWrlDefaultButton.setActionCommand(org.web3d.x3d.options.X3dEditUserPreferences.getFreeWrlPathDefault());
        freeWrlDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                freeWrlDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(freeWrlDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(heilanDefaultButton, "default");
        heilanDefaultButton.setToolTipText("Reset default file location");
        heilanDefaultButton.setActionCommand(org.web3d.x3d.options.X3dEditUserPreferences.getInstantRealityPathDefault());
        heilanDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                heilanDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(heilanDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(instantRealityDefaultButton, "default");
        instantRealityDefaultButton.setToolTipText("Reset default file location");
        instantRealityDefaultButton.setActionCommand(org.web3d.x3d.options.X3dEditUserPreferences.getInstantRealityPathDefault());
        instantRealityDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                instantRealityDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(instantRealityDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(octagaDefaultButton, "default");
        octagaDefaultButton.setToolTipText("Reset default file location");
        octagaDefaultButton.setActionCommand(org.web3d.x3d.options.X3dEditUserPreferences.getOctagaPathDefault());
        octagaDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                octagaDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(octagaDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(swirlx3dDefaultButton, "default");
        swirlx3dDefaultButton.setToolTipText("Reset default file location");
        swirlx3dDefaultButton.setActionCommand(org.web3d.x3d.options.X3dEditUserPreferences.getSwirlX3DPathDefault());
        swirlx3dDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                swirlx3dDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(swirlx3dDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(view3dsceneDefaultButton, "default");
        view3dsceneDefaultButton.setToolTipText("Reset default file location");
        view3dsceneDefaultButton.setActionCommand(org.web3d.x3d.options.X3dEditUserPreferences.getVivatyPlayerPathDefault());
        view3dsceneDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view3dsceneDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(view3dsceneDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(vivatyDefaultButton, "default");
        vivatyDefaultButton.setToolTipText("Reset default file location");
        vivatyDefaultButton.setActionCommand(org.web3d.x3d.options.X3dEditUserPreferences.getVivatyPlayerPathDefault());
        vivatyDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vivatyDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(vivatyDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(xj3dDefaultButton, "default");
        xj3dDefaultButton.setToolTipText("Reset default file location");
        xj3dDefaultButton.setActionCommand(org.web3d.x3d.options.X3dEditUserPreferences.getXj3DPathDefault());
        xj3dDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xj3dDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(xj3dDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(otherX3dPlayerClearButton, "clear");
        otherX3dPlayerClearButton.setToolTipText("reset default file location");
        otherX3dPlayerClearButton.setActionCommand(org.web3d.x3d.options.X3dEditUserPreferences.getOtherX3dPlayerPathDefault());
        otherX3dPlayerClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherX3dPlayerClearButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(otherX3dPlayerClearButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(contactDownloadButton, "get");
        contactDownloadButton.setToolTipText("Download player from website");
        contactDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contactDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(contactDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(contactGeoDownloadButton, "get");
        contactGeoDownloadButton.setToolTipText("Download player from website");
        contactGeoDownloadButton.setEnabled(false);
        contactGeoDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                contactGeoDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(contactGeoDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(freeWrlDownloadButton, "get");
        freeWrlDownloadButton.setToolTipText("Download player from website");
        freeWrlDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                freeWrlDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(freeWrlDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(heilanDownloadButton, "get");
        heilanDownloadButton.setToolTipText("Download player from website");
        heilanDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                heilanDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(heilanDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(instantRealityDownloadButton, "get");
        instantRealityDownloadButton.setToolTipText("Download player from website");
        instantRealityDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                instantRealityDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(instantRealityDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(octagaDownloadButton, "get");
        octagaDownloadButton.setToolTipText("Download player from website");
        octagaDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                octagaDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(octagaDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(swirlx3dDownloadButton, "get");
        swirlx3dDownloadButton.setToolTipText("Download player from website");
        swirlx3dDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                swirlx3dDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(swirlx3dDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(view3dsceneDownloadButton, "get");
        view3dsceneDownloadButton.setToolTipText("Download player from website");
        view3dsceneDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                view3dsceneDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(view3dsceneDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(vivatyDownloadButton, "get");
        vivatyDownloadButton.setToolTipText("Download player from website");
        vivatyDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vivatyDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(vivatyDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(xj3dDownloadButton, "get");
        xj3dDownloadButton.setToolTipText("Download player from website");
        xj3dDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xj3dDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(xj3dDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(otherX3dPlayerDownloadButton, "find");
        otherX3dPlayerDownloadButton.setToolTipText("Download player from website");
        otherX3dPlayerDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherX3dPlayerDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(otherX3dPlayerDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(contactLaunchButton, "launch");
        contactLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        contactLaunchButton.setActionCommand("contactTF");
        contactLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commonLauncher(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(contactLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(contactGeoLaunchButton, "launch");
        contactGeoLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        contactGeoLaunchButton.setActionCommand("contactGeoTF");
        contactGeoLaunchButton.setEnabled(false);
        contactGeoLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commonLauncher(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(contactGeoLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(freeWrlLaunchButton, "launch");
        freeWrlLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        freeWrlLaunchButton.setActionCommand("freeWrlTF");
        freeWrlLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commonLauncher(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(freeWrlLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(heilanLaunchButton, "launch");
        heilanLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        heilanLaunchButton.setActionCommand("heilanTF");
        heilanLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commonLauncher(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(heilanLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(instantRealityLaunchButton, "launch");
        instantRealityLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        instantRealityLaunchButton.setActionCommand("instantRealityTF");
        instantRealityLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commonLauncher(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(instantRealityLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(octagaLaunchButton, "launch");
        octagaLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        octagaLaunchButton.setActionCommand("octagaTF");
        octagaLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commonLauncher(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(octagaLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(swirlx3dLaunchButton, "launch");
        swirlx3dLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        swirlx3dLaunchButton.setActionCommand("SwirlX3DTF");
        swirlx3dLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commonLauncher(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(swirlx3dLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(view3dsceneLaunchButton, "launch");
        view3dsceneLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        view3dsceneLaunchButton.setActionCommand("vivatyTF");
        view3dsceneLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commonLauncher(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(view3dsceneLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(vivatyLaunchButton, "launch");
        vivatyLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        vivatyLaunchButton.setActionCommand("vivatyTF");
        vivatyLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commonLauncher(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(vivatyLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(xj3dLaunchButton, "launch");
        xj3dLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        xj3dLaunchButton.setActionCommand("xj3DTF");
        xj3dLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commonLauncher(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(xj3dLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(otherX3dPlayerLaunchButton, "launch");
        otherX3dPlayerLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        otherX3dPlayerLaunchButton.setActionCommand("otherTF");
        otherX3dPlayerLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commonLauncher(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(otherX3dPlayerLaunchButton, gridBagConstraints);

        h3dLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(h3dLabel, "H3D");
        h3dLabel.setToolTipText("H3D haptic X3D viewer");
        h3dLabel.setMinimumSize(new java.awt.Dimension(120, 20));
        h3dLabel.setPreferredSize(new java.awt.Dimension(120, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(h3dLabel, gridBagConstraints);

        h3dCheckBox.setSelected(true);
        h3dCheckBox.setToolTipText("Include when autolaunching all browsers");
        h3dCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        h3dCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h3dCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 0);
        x3dPlayerPathsPanel.add(h3dCheckBox, gridBagConstraints);

        h3dTF.setToolTipText("Local file location");
        h3dTF.setMinimumSize(new java.awt.Dimension(100, 20));
        h3dTF.setPreferredSize(new java.awt.Dimension(100, 20));
        h3dTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                h3dTFFocusLost(evt);
            }
        });
        h3dTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h3dTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(h3dTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(h3dChooserButton, "...");
        h3dChooserButton.setToolTipText("Find local file location");
        h3dChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h3dChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(h3dChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(h3dDefaultButton, "default");
        h3dDefaultButton.setToolTipText("Reset default file location");
        h3dDefaultButton.setActionCommand(org.web3d.x3d.options.X3dEditUserPreferences.getInstantRealityPathDefault());
        h3dDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h3dDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(h3dDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(h3dLaunchButton, "launch");
        h3dLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        h3dLaunchButton.setActionCommand("heilanTF");
        h3dLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commonLauncher(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(h3dLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(h3dDownloadButton, "get");
        h3dDownloadButton.setToolTipText("Download player from website");
        h3dDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                h3dDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(h3dDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(verticalSpacerLabel10, "   ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(verticalSpacerLabel10, gridBagConstraints);

        launchIntervalLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(launchIntervalLabel, "Player autolaunch interval: ");
        launchIntervalLabel.setToolTipText("Wait interval when automatically launching multiple players");
        launchIntervalLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        launchIntervalLabel.setMinimumSize(new java.awt.Dimension(35, 20));
        launchIntervalLabel.setPreferredSize(new java.awt.Dimension(35, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 100;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 10, 3);
        x3dPlayerPathsPanel.add(launchIntervalLabel, gridBagConstraints);

        launchIntervalTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        launchIntervalTF.setText("1.0");
        launchIntervalTF.setToolTipText("number of seconds to pause when launching all browsersa");
        launchIntervalTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                launchIntervalTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 10, 3);
        x3dPlayerPathsPanel.add(launchIntervalTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(secondsLabel, "seconds");
        secondsLabel.setToolTipText("Wait interval when launching multiple players");
        secondsLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 10, 3);
        x3dPlayerPathsPanel.add(secondsLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(verticalSpacerLabel1, "   ");
        x3dPlayerPathsPanel.add(verticalSpacerLabel1, new java.awt.GridBagConstraints());

        externalX3dEditorLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        externalX3dEditorLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        org.openide.awt.Mnemonics.setLocalizedText(externalX3dEditorLabel1, "X3D Players and Viewers");
        externalX3dEditorLabel1.setToolTipText("External applications for viewing X3D scenes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 6, 10, 3);
        x3dPlayerPathsPanel.add(externalX3dEditorLabel1, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(verticalSpacerLabel8, "   ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 21;
        gridBagConstraints.gridwidth = 7;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(verticalSpacerLabel8, gridBagConstraints);

        defunctX3dEditorLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        defunctX3dEditorLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        org.openide.awt.Mnemonics.setLocalizedText(defunctX3dEditorLabel, "Historic Players");
        defunctX3dEditorLabel.setToolTipText("External tools for modifying X3D scenes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 6, 10, 3);
        x3dPlayerPathsPanel.add(defunctX3dEditorLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(reportPlayerButton, "Report");
        reportPlayerButton.setToolTipText(BaseCustomizer.MAILTO_TOOLTIP);
        reportPlayerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportPlayerButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 23;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dPlayerPathsPanel.add(reportPlayerButton, gridBagConstraints);

        x3dOptionsTabbedPane.addTab(org.openide.util.NbBundle.getMessage(X3dEditUserPreferencesPanel.class, "Paths_Tab_Title"), null, x3dPlayerPathsPanel, "Set directories for launching external browsers"); // NOI18N

        x3dModelingToolsPanel.setMinimumSize(new java.awt.Dimension(825, 600));
        x3dModelingToolsPanel.setPreferredSize(new java.awt.Dimension(825, 600));
        x3dModelingToolsPanel.setLayout(new java.awt.GridBagLayout());

        externalX3dEditorLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        externalX3dEditorLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        org.openide.awt.Mnemonics.setLocalizedText(externalX3dEditorLabel, "X3D-Capable Modeling Tools");
        externalX3dEditorLabel.setToolTipText("External tools for modifying X3D scenes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 6, 3, 3);
        x3dModelingToolsPanel.add(externalX3dEditorLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(verticalSpacerLabel9, "   ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 3, 3);
        x3dModelingToolsPanel.add(verticalSpacerLabel9, gridBagConstraints);

        altovaXMLSpyLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(altovaXMLSpyLabel, "Altova XMLSpy");
        altovaXMLSpyLabel.setToolTipText("Altova XMLSpy editor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        x3dModelingToolsPanel.add(altovaXMLSpyLabel, gridBagConstraints);

        altovaXMLSpyCheckBox.setSelected(true);
        altovaXMLSpyCheckBox.setToolTipText("Include when autolaunching tools");
        altovaXMLSpyCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        altovaXMLSpyCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                altovaXMLSpyCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(altovaXMLSpyCheckBox, gridBagConstraints);

        altovaXMLSpyTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                altovaXMLSpyTFFocusLost(evt);
            }
        });
        altovaXMLSpyTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                altovaXMLSpyTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(altovaXMLSpyTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(altovaXMLSpyChooserButton, "...");
        altovaXMLSpyChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                altovaXMLSpyChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(altovaXMLSpyChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(altovaXMLSpyDefaultButton, "default");
        altovaXMLSpyDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                altovaXMLSpyDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(altovaXMLSpyDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(altovaXMLSpyLaunchButton, "launch");
        altovaXMLSpyLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                altovaXMLSpyLaunchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(altovaXMLSpyLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(altovaXMLSpyDownloadButton, "get");
        altovaXMLSpyDownloadButton.setToolTipText("Download tool from website");
        altovaXMLSpyDownloadButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        altovaXMLSpyDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                altovaXMLSpyDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(altovaXMLSpyDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(altovaXMLSpyHelpButton, "help");
        altovaXMLSpyHelpButton.setToolTipText("Display tool help page");
        altovaXMLSpyHelpButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        altovaXMLSpyHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                altovaXMLSpyHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(altovaXMLSpyHelpButton, gridBagConstraints);

        blenderX3dEditorLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(blenderX3dEditorLabel, "Blender");
        blenderX3dEditorLabel.setToolTipText("Blender 3D Graphics Tool");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(blenderX3dEditorLabel, gridBagConstraints);

        blenderX3dEditorCheckBox.setSelected(true);
        blenderX3dEditorCheckBox.setToolTipText("Include when autolaunching tools");
        blenderX3dEditorCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        blenderX3dEditorCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blenderX3dEditorCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(blenderX3dEditorCheckBox, gridBagConstraints);

        blenderX3dEditorPathTF.setToolTipText("File location for local application");
        blenderX3dEditorPathTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                blenderX3dEditorPathTFFocusLost(evt);
            }
        });
        blenderX3dEditorPathTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blenderX3dEditorPathTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(blenderX3dEditorPathTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(blenderX3dEditorChooserButton, "...");
        blenderX3dEditorChooserButton.setToolTipText("Browse to select file location for local keystore");
        blenderX3dEditorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blenderX3dEditorChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(blenderX3dEditorChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(blenderX3dEditorDefaultButton, "default");
        blenderX3dEditorDefaultButton.setToolTipText("Reset default file location for local application");
        blenderX3dEditorDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blenderX3dEditorDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(blenderX3dEditorDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(blenderX3dEditorLaunchButton, "launch");
        blenderX3dEditorLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        blenderX3dEditorLaunchButton.setActionCommand("contactTF");
        blenderX3dEditorLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blenderX3dEditorLaunchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(blenderX3dEditorLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(blenderX3dEditorDownloadButton, "get");
        blenderX3dEditorDownloadButton.setToolTipText("Download tool from website");
        blenderX3dEditorDownloadButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        blenderX3dEditorDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blenderX3dEditorDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(blenderX3dEditorDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(blenderX3dEditorHelpButton, "help");
        blenderX3dEditorHelpButton.setToolTipText("Display tool help page");
        blenderX3dEditorHelpButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        blenderX3dEditorHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                blenderX3dEditorHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(blenderX3dEditorHelpButton, gridBagConstraints);

        bsContentStudioX3dEditorLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(bsContentStudioX3dEditorLabel, "BS Content Studio");
        bsContentStudioX3dEditorLabel.setToolTipText("Blender 3D Graphics Tool");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(bsContentStudioX3dEditorLabel, gridBagConstraints);

        bsContentStudioX3dEditorCheckBox.setSelected(true);
        bsContentStudioX3dEditorCheckBox.setToolTipText("Include when autolaunching tools");
        bsContentStudioX3dEditorCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bsContentStudioX3dEditorCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsContentStudioX3dEditorCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(bsContentStudioX3dEditorCheckBox, gridBagConstraints);

        bsContentStudioX3dEditorPathTF.setToolTipText("File location for local application");
        bsContentStudioX3dEditorPathTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                bsContentStudioX3dEditorPathTFFocusLost(evt);
            }
        });
        bsContentStudioX3dEditorPathTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsContentStudioX3dEditorPathTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(bsContentStudioX3dEditorPathTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(bsContentStudioX3dEditorChooserButton, "...");
        bsContentStudioX3dEditorChooserButton.setToolTipText("Browse to select file location for local keystore");
        bsContentStudioX3dEditorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsContentStudioX3dEditorChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(bsContentStudioX3dEditorChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(bsContentStudioX3dEditorDefaultButton, "default");
        bsContentStudioX3dEditorDefaultButton.setToolTipText("Reset default file location for local application");
        bsContentStudioX3dEditorDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsContentStudioX3dEditorDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(bsContentStudioX3dEditorDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(bsContentStudioX3dEditorLaunchButton, "launch");
        bsContentStudioX3dEditorLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        bsContentStudioX3dEditorLaunchButton.setActionCommand("contactTF");
        bsContentStudioX3dEditorLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsContentStudioX3dEditorLaunchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(bsContentStudioX3dEditorLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(bsContentStudioX3dEditorDownloadButton, "get");
        bsContentStudioX3dEditorDownloadButton.setToolTipText("Download tool from website");
        bsContentStudioX3dEditorDownloadButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        bsContentStudioX3dEditorDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsContentStudioX3dEditorDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(bsContentStudioX3dEditorDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(bsContentStudioX3dEditorHelpButton, "help");
        bsContentStudioX3dEditorHelpButton.setToolTipText("Display tool help page");
        bsContentStudioX3dEditorHelpButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        bsContentStudioX3dEditorHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bsContentStudioX3dEditorHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(bsContentStudioX3dEditorHelpButton, gridBagConstraints);

        bvhackerEditorLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(bvhackerEditorLabel, "bvhacker");
        bvhackerEditorLabel.setToolTipText("free bvh file editing tool");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(bvhackerEditorLabel, gridBagConstraints);

        bvhackerEditorCheckBox.setSelected(true);
        bvhackerEditorCheckBox.setToolTipText("Include when autolaunching tools");
        bvhackerEditorCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        bvhackerEditorCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bvhackerEditorCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(bvhackerEditorCheckBox, gridBagConstraints);

        bvhackerEditorPathTF.setToolTipText("File location for local application");
        bvhackerEditorPathTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                bvhackerEditorPathTFFocusLost(evt);
            }
        });
        bvhackerEditorPathTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bvhackerEditorPathTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(bvhackerEditorPathTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(bvhackerEditorChooserButton, "...");
        bvhackerEditorChooserButton.setToolTipText("Browse to select file location for local keystore");
        bvhackerEditorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bvhackerEditorChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(bvhackerEditorChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(bvhackerEditorDefaultButton, "default");
        bvhackerEditorDefaultButton.setToolTipText("Reset default file location for local application");
        bvhackerEditorDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bvhackerEditorDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(bvhackerEditorDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(bvhackerEditorLaunchButton, "launch");
        bvhackerEditorLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        bvhackerEditorLaunchButton.setActionCommand("contactTF");
        bvhackerEditorLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bvhackerEditorLaunchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(bvhackerEditorLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(bvhackerEditorDownloadButton, "get");
        bvhackerEditorDownloadButton.setToolTipText("Download tool from website");
        bvhackerEditorDownloadButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        bvhackerEditorDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bvhackerEditorDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(bvhackerEditorDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(bvhackerEditorHelpButton, "help");
        bvhackerEditorHelpButton.setToolTipText("Display tool help page");
        bvhackerEditorHelpButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        bvhackerEditorHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bvhackerEditorHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(bvhackerEditorHelpButton, gridBagConstraints);

        curaX3dEditorLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(curaX3dEditorLabel, "Ultimaker Cura");
        curaX3dEditorLabel.setToolTipText("Ultimaker Cura printing tool");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(curaX3dEditorLabel, gridBagConstraints);

        curaX3dEditorCheckBox.setSelected(true);
        curaX3dEditorCheckBox.setToolTipText("Include when autolaunching tools");
        curaX3dEditorCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        curaX3dEditorCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                curaX3dEditorCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(curaX3dEditorCheckBox, gridBagConstraints);

        curaX3dEditorPathTF.setToolTipText("File location for local application");
        curaX3dEditorPathTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                curaX3dEditorPathTFFocusLost(evt);
            }
        });
        curaX3dEditorPathTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                curaX3dEditorPathTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(curaX3dEditorPathTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(curaX3dEditorChooserButton, "...");
        curaX3dEditorChooserButton.setToolTipText("Browse to select file location for local keystore");
        curaX3dEditorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                curaX3dEditorChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(curaX3dEditorChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(curaX3dEditorDefaultButton, "default");
        curaX3dEditorDefaultButton.setToolTipText("Reset default file location for local application");
        curaX3dEditorDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                curaX3dEditorDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(curaX3dEditorDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(curaX3dEditorLaunchButton, "launch");
        curaX3dEditorLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        curaX3dEditorLaunchButton.setActionCommand("contactTF");
        curaX3dEditorLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                curaX3dEditorLaunchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(curaX3dEditorLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(curaX3dEditorDownloadButton, "get");
        curaX3dEditorDownloadButton.setToolTipText("Download tool from website");
        curaX3dEditorDownloadButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        curaX3dEditorDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                curaX3dEditorDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(curaX3dEditorDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(curaX3dEditorHelpButton, "help");
        curaX3dEditorHelpButton.setToolTipText("Display tool help page");
        curaX3dEditorHelpButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        curaX3dEditorHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                curaX3dEditorHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(curaX3dEditorHelpButton, gridBagConstraints);

        meshLabX3dEditorLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(meshLabX3dEditorLabel, "MeshLab");
        meshLabX3dEditorLabel.setToolTipText("MeshLab 3D graphics authoring tool");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(meshLabX3dEditorLabel, gridBagConstraints);

        meshLabX3dEditorCheckBox.setSelected(true);
        meshLabX3dEditorCheckBox.setToolTipText("Include when autolaunching tools");
        meshLabX3dEditorCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        meshLabX3dEditorCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                meshLabX3dEditorCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(meshLabX3dEditorCheckBox, gridBagConstraints);

        meshLabX3dEditorPathTF.setToolTipText("File location for local application");
        meshLabX3dEditorPathTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                meshLabX3dEditorPathTFFocusLost(evt);
            }
        });
        meshLabX3dEditorPathTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                meshLabX3dEditorPathTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(meshLabX3dEditorPathTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(meshLabX3dEditorChooserButton, "...");
        meshLabX3dEditorChooserButton.setToolTipText("Browse to select file location for local keystore");
        meshLabX3dEditorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                meshLabX3dEditorChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(meshLabX3dEditorChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(meshLabX3dEditorDefaultButton, "default");
        meshLabX3dEditorDefaultButton.setToolTipText("Reset default file location for local application");
        meshLabX3dEditorDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                meshLabX3dEditorDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(meshLabX3dEditorDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(meshLabX3dEditorLaunchButton, "launch");
        meshLabX3dEditorLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        meshLabX3dEditorLaunchButton.setActionCommand("contactTF");
        meshLabX3dEditorLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                meshLabX3dEditorLaunchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(meshLabX3dEditorLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(meshLabX3dEditorDownloadButton, "get");
        meshLabX3dEditorDownloadButton.setToolTipText("Download tool from website");
        meshLabX3dEditorDownloadButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        meshLabX3dEditorDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                meshLabX3dEditorDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(meshLabX3dEditorDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(meshLabX3dEditorHelpButton, "help");
        meshLabX3dEditorHelpButton.setToolTipText("Display tool help page");
        meshLabX3dEditorHelpButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        meshLabX3dEditorHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                meshLabX3dEditorHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(meshLabX3dEditorHelpButton, gridBagConstraints);

        paraviewX3dEditorLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(paraviewX3dEditorLabel, "Paraview");
        paraviewX3dEditorLabel.setToolTipText("Paraview 3D graphics authoring tool");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(paraviewX3dEditorLabel, gridBagConstraints);

        paraviewX3dEditorCheckBox.setSelected(true);
        paraviewX3dEditorCheckBox.setToolTipText("Include when autolaunching tools");
        paraviewX3dEditorCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        paraviewX3dEditorCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paraviewX3dEditorCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(paraviewX3dEditorCheckBox, gridBagConstraints);

        paraviewX3dEditorPathTF.setToolTipText("File location for local application");
        paraviewX3dEditorPathTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                paraviewX3dEditorPathTFFocusLost(evt);
            }
        });
        paraviewX3dEditorPathTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paraviewX3dEditorPathTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(paraviewX3dEditorPathTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(paraviewX3dEditorChooserButton, "...");
        paraviewX3dEditorChooserButton.setToolTipText("Browse to select file location for local keystore");
        paraviewX3dEditorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paraviewX3dEditorChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(paraviewX3dEditorChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(paraviewX3dEditorDefaultButton, "default");
        paraviewX3dEditorDefaultButton.setToolTipText("Reset default file location for local application");
        paraviewX3dEditorDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paraviewX3dEditorDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(paraviewX3dEditorDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(paraviewX3dEditorLaunchButton, "launch");
        paraviewX3dEditorLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        paraviewX3dEditorLaunchButton.setActionCommand("contactTF");
        paraviewX3dEditorLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paraviewX3dEditorLaunchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(paraviewX3dEditorLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(paraviewX3dEditorDownloadButton, "get");
        paraviewX3dEditorDownloadButton.setToolTipText("Download tool from website");
        paraviewX3dEditorDownloadButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        paraviewX3dEditorDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paraviewX3dEditorDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(paraviewX3dEditorDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(paraviewX3dEditorHelpButton, "help");
        paraviewX3dEditorHelpButton.setToolTipText("Display tool help page");
        paraviewX3dEditorHelpButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        paraviewX3dEditorHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                paraviewX3dEditorHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(paraviewX3dEditorHelpButton, gridBagConstraints);

        polyTransNuGrafEditorLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(polyTransNuGrafEditorLabel, "Okino NuGraf");
        polyTransNuGrafEditorLabel.setToolTipText("Okino PolyTrans NuGraf 3D authoring tool");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(polyTransNuGrafEditorLabel, gridBagConstraints);

        polyTransNuGrafEditorCheckBox.setSelected(true);
        polyTransNuGrafEditorCheckBox.setToolTipText("Include when autolaunching tools");
        polyTransNuGrafEditorCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        polyTransNuGrafEditorCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                polyTransNuGrafEditorCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(polyTransNuGrafEditorCheckBox, gridBagConstraints);

        polyTransNuGrafEditorPathTF.setToolTipText("File location for local application");
        polyTransNuGrafEditorPathTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                polyTransNuGrafEditorPathTFFocusLost(evt);
            }
        });
        polyTransNuGrafEditorPathTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                polyTransNuGrafEditorPathTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(polyTransNuGrafEditorPathTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(polyTransNuGrafEditorChooserButton, "...");
        polyTransNuGrafEditorChooserButton.setToolTipText("Browse to select file location for local keystore");
        polyTransNuGrafEditorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                polyTransNuGrafEditorChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(polyTransNuGrafEditorChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(polyTransNuGrafEditorDefaultButton, "default");
        polyTransNuGrafEditorDefaultButton.setToolTipText("Reset default file location for local application");
        polyTransNuGrafEditorDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                polyTransNuGrafEditorDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(polyTransNuGrafEditorDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(polyTransNuGrafEditorLaunchButton, "launch");
        polyTransNuGrafEditorLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        polyTransNuGrafEditorLaunchButton.setActionCommand("contactTF");
        polyTransNuGrafEditorLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                polyTransNuGrafEditorLaunchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(polyTransNuGrafEditorLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(polyTransNuGrafEditorDownloadButton, "get");
        polyTransNuGrafEditorDownloadButton.setToolTipText("Download tool from website");
        polyTransNuGrafEditorDownloadButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        polyTransNuGrafEditorDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                polyTransNuGrafEditorDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(polyTransNuGrafEditorDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(polyTransNuGrafEditorHelpButton, "help");
        polyTransNuGrafEditorHelpButton.setToolTipText("Display tool help page");
        polyTransNuGrafEditorHelpButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        polyTransNuGrafEditorHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                polyTransNuGrafEditorHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(polyTransNuGrafEditorHelpButton, gridBagConstraints);

        titaniaX3dEditorLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(titaniaX3dEditorLabel, "Titania");
        titaniaX3dEditorLabel.setToolTipText("Titania X3D Editor (Linux only)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(titaniaX3dEditorLabel, gridBagConstraints);

        titaniaX3dEditorCheckBox.setSelected(true);
        titaniaX3dEditorCheckBox.setToolTipText("Include when autolaunching tools");
        titaniaX3dEditorCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titaniaX3dEditorCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                titaniaX3dEditorCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(titaniaX3dEditorCheckBox, gridBagConstraints);

        titaniaX3dEditorPathTF.setToolTipText("File location for local application");
        titaniaX3dEditorPathTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                titaniaX3dEditorPathTFFocusLost(evt);
            }
        });
        titaniaX3dEditorPathTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                titaniaX3dEditorPathTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(titaniaX3dEditorPathTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(titaniaX3dEditorChooserButton, "...");
        titaniaX3dEditorChooserButton.setToolTipText("Browse to select file location for local keystore");
        titaniaX3dEditorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                titaniaX3dEditorChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(titaniaX3dEditorChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(titaniaX3dEditorDefaultButton, "default");
        titaniaX3dEditorDefaultButton.setToolTipText("Reset default file location for local application");
        titaniaX3dEditorDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                titaniaX3dEditorDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(titaniaX3dEditorDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(titaniaX3dEditorLaunchButton, "launch");
        titaniaX3dEditorLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        titaniaX3dEditorLaunchButton.setActionCommand("contactTF");
        titaniaX3dEditorLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                titaniaX3dEditorLaunchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(titaniaX3dEditorLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(titaniaX3dEditorDownloadButton, "get");
        titaniaX3dEditorDownloadButton.setToolTipText("Download tool from website");
        titaniaX3dEditorDownloadButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        titaniaX3dEditorDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                titaniaX3dEditorDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(titaniaX3dEditorDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(titaniaX3dEditorHelpButton, "help");
        titaniaX3dEditorHelpButton.setToolTipText("Display tool help page");
        titaniaX3dEditorHelpButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        titaniaX3dEditorHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                titaniaX3dEditorHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(titaniaX3dEditorHelpButton, gridBagConstraints);

        seamless3dX3dEditorLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(seamless3dX3dEditorLabel, "Seamless3d");
        seamless3dX3dEditorLabel.setToolTipText("Seamless3d graphics authoring tool");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(seamless3dX3dEditorLabel, gridBagConstraints);

        seamless3dX3dEditorCheckBox.setSelected(true);
        seamless3dX3dEditorCheckBox.setToolTipText("Include when autolaunching tools");
        seamless3dX3dEditorCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        seamless3dX3dEditorCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seamless3dX3dEditorCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(seamless3dX3dEditorCheckBox, gridBagConstraints);

        seamless3dX3dEditorPathTF.setToolTipText("File location for local application");
        seamless3dX3dEditorPathTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                seamless3dX3dEditorPathTFFocusLost(evt);
            }
        });
        seamless3dX3dEditorPathTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seamless3dX3dEditorPathTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(seamless3dX3dEditorPathTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(seamless3dX3dEditorChooserButton, "...");
        seamless3dX3dEditorChooserButton.setToolTipText("Browse to select file location for local keystore");
        seamless3dX3dEditorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seamless3dX3dEditorChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(seamless3dX3dEditorChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(seamless3dX3dEditorDefaultButton, "default");
        seamless3dX3dEditorDefaultButton.setToolTipText("Reset default file location for local application");
        seamless3dX3dEditorDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seamless3dX3dEditorDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(seamless3dX3dEditorDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(seamless3dX3dEditorLaunchButton, "launch");
        seamless3dX3dEditorLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        seamless3dX3dEditorLaunchButton.setActionCommand("contactTF");
        seamless3dX3dEditorLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seamless3dX3dEditorLaunchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(seamless3dX3dEditorLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(seamless3dX3dEditorDownloadButton, "get");
        seamless3dX3dEditorDownloadButton.setToolTipText("Download tool from website");
        seamless3dX3dEditorDownloadButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        seamless3dX3dEditorDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seamless3dX3dEditorDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(seamless3dX3dEditorDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(seamless3dX3dEditorHelpButton, "help");
        seamless3dX3dEditorHelpButton.setToolTipText("Display tool help page");
        seamless3dX3dEditorHelpButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        seamless3dX3dEditorHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seamless3dX3dEditorHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(seamless3dX3dEditorHelpButton, gridBagConstraints);

        ultraEditX3dEditorLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(ultraEditX3dEditorLabel, "UltraEdit");
        ultraEditX3dEditorLabel.setToolTipText("UltraEdit Text Editor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(ultraEditX3dEditorLabel, gridBagConstraints);

        ultraEditX3dEditorCheckBox.setSelected(true);
        ultraEditX3dEditorCheckBox.setToolTipText("Include when autolaunching tools");
        ultraEditX3dEditorCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        ultraEditX3dEditorCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ultraEditX3dEditorCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(ultraEditX3dEditorCheckBox, gridBagConstraints);

        ultraEditX3dEditorPathTF.setToolTipText("File location for local application");
        ultraEditX3dEditorPathTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                ultraEditX3dEditorPathTFFocusLost(evt);
            }
        });
        ultraEditX3dEditorPathTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ultraEditX3dEditorPathTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(ultraEditX3dEditorPathTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(ultraEditX3dEditorChooserButton, "...");
        ultraEditX3dEditorChooserButton.setToolTipText("Browse to select file location for local keystore");
        ultraEditX3dEditorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ultraEditX3dEditorChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(ultraEditX3dEditorChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(ultraEditX3dEditorDefaultButton, "default");
        ultraEditX3dEditorDefaultButton.setToolTipText("Reset default file location for local application");
        ultraEditX3dEditorDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ultraEditX3dEditorDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(ultraEditX3dEditorDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(ultraEditX3dEditorLaunchButton, "launch");
        ultraEditX3dEditorLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        ultraEditX3dEditorLaunchButton.setActionCommand("contactTF");
        ultraEditX3dEditorLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ultraEditX3dEditorLaunchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(ultraEditX3dEditorLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(ultraEditX3dEditorDownloadButton, "get");
        ultraEditX3dEditorDownloadButton.setToolTipText("Download tool from website");
        ultraEditX3dEditorDownloadButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        ultraEditX3dEditorDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ultraEditX3dEditorDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(ultraEditX3dEditorDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(ultraEditHelpButton, "help");
        ultraEditHelpButton.setToolTipText("Display tool help page");
        ultraEditHelpButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        ultraEditHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ultraEditHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(ultraEditHelpButton, gridBagConstraints);

        whiteDuneX3dEditorLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(whiteDuneX3dEditorLabel, "White Dune");
        whiteDuneX3dEditorLabel.setToolTipText("White Dune graphics authoring tool");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(whiteDuneX3dEditorLabel, gridBagConstraints);

        whiteDuneX3dEditorCheckBox.setSelected(true);
        whiteDuneX3dEditorCheckBox.setToolTipText("Include when autolaunching tools");
        whiteDuneX3dEditorCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        whiteDuneX3dEditorCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                whiteDuneX3dEditorCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(whiteDuneX3dEditorCheckBox, gridBagConstraints);

        whiteDuneX3dEditorPathTF.setToolTipText("File location for local application");
        whiteDuneX3dEditorPathTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                whiteDuneX3dEditorPathTFFocusLost(evt);
            }
        });
        whiteDuneX3dEditorPathTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                whiteDuneX3dEditorPathTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(whiteDuneX3dEditorPathTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(whiteDuneX3dEditorChooserButton, "...");
        whiteDuneX3dEditorChooserButton.setToolTipText("Browse to select file location for local keystore");
        whiteDuneX3dEditorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                whiteDuneX3dEditorChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(whiteDuneX3dEditorChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(whiteDuneX3dEditorDefaultButton, "default");
        whiteDuneX3dEditorDefaultButton.setToolTipText("Reset default file location for local application");
        whiteDuneX3dEditorDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                whiteDuneX3dEditorDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(whiteDuneX3dEditorDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(whiteDuneX3dEditorLaunchButton, "launch");
        whiteDuneX3dEditorLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        whiteDuneX3dEditorLaunchButton.setActionCommand("contactTF");
        whiteDuneX3dEditorLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                whiteDuneX3dEditorLaunchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(whiteDuneX3dEditorLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(whiteDuneX3dEditorDownloadButton, "get");
        whiteDuneX3dEditorDownloadButton.setToolTipText("Download tool from website");
        whiteDuneX3dEditorDownloadButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        whiteDuneX3dEditorDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                whiteDuneX3dEditorDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(whiteDuneX3dEditorDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(whiteDuneX3dEditorHelpButton, "help");
        whiteDuneX3dEditorHelpButton.setToolTipText("Display tool help page");
        whiteDuneX3dEditorHelpButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        whiteDuneX3dEditorHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                whiteDuneX3dEditorHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(whiteDuneX3dEditorHelpButton, gridBagConstraints);

        wings3dX3dEditorLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(wings3dX3dEditorLabel, "Wings 3D");
        wings3dX3dEditorLabel.setToolTipText("Wings 3D graphics authoring tool");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(wings3dX3dEditorLabel, gridBagConstraints);

        wings3dX3dEditorCheckBox.setSelected(true);
        wings3dX3dEditorCheckBox.setToolTipText("Include when autolaunching tools");
        wings3dX3dEditorCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        wings3dX3dEditorCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wings3dX3dEditorCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(wings3dX3dEditorCheckBox, gridBagConstraints);

        wings3dX3dEditorPathTF.setToolTipText("File location for local application");
        wings3dX3dEditorPathTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                wings3dX3dEditorPathTFFocusLost(evt);
            }
        });
        wings3dX3dEditorPathTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wings3dX3dEditorPathTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(wings3dX3dEditorPathTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(wings3dX3dEditorChooserButton, "...");
        wings3dX3dEditorChooserButton.setToolTipText("Browse to select file location for local keystore");
        wings3dX3dEditorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wings3dX3dEditorChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(wings3dX3dEditorChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(wings3dX3dEditorDefaultButton, "default");
        wings3dX3dEditorDefaultButton.setToolTipText("Reset default file location for local application");
        wings3dX3dEditorDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wings3dX3dEditorDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(wings3dX3dEditorDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(wings3dX3dEditorLaunchButton, "launch");
        wings3dX3dEditorLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        wings3dX3dEditorLaunchButton.setActionCommand("contactTF");
        wings3dX3dEditorLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wings3dX3dEditorLaunchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(wings3dX3dEditorLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(wings3dX3dEditorDownloadButton, "get");
        wings3dX3dEditorDownloadButton.setToolTipText("Download tool from website");
        wings3dX3dEditorDownloadButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        wings3dX3dEditorDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wings3dX3dEditorDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(wings3dX3dEditorDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(wings3dX3dEditorHelpButton, "help");
        wings3dX3dEditorHelpButton.setToolTipText("Display tool help page");
        wings3dX3dEditorHelpButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        wings3dX3dEditorHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                wings3dX3dEditorHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(wings3dX3dEditorHelpButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(verticalSpacerLabel16, "   ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 27;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(verticalSpacerLabel16, gridBagConstraints);

        otherEditorNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(otherEditorNameLabel, "Other tool name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 28;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(otherEditorNameLabel, gridBagConstraints);

        otherEditorPathLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(otherEditorPathLabel, "Other tool path:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 29;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(otherEditorPathLabel, gridBagConstraints);

        otherX3dEditorNameTF.setHorizontalAlignment(javax.swing.JTextField.LEFT);
        otherX3dEditorNameTF.setToolTipText("Enter name of alternate tool");
        otherX3dEditorNameTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                otherX3dEditorNameTFFocusLost(evt);
            }
        });
        otherX3dEditorNameTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherX3dEditorNameTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 28;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(otherX3dEditorNameTF, gridBagConstraints);

        otherX3dEditorCheckBox.setToolTipText("Include when autolaunching tools");
        otherX3dEditorCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        otherX3dEditorCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherX3dEditorCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 29;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(otherX3dEditorCheckBox, gridBagConstraints);

        otherX3dEditorPathTF.setToolTipText("Enter file location for local application");
        otherX3dEditorPathTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                otherX3dEditorPathTFFocusLost(evt);
            }
        });
        otherX3dEditorPathTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherX3dEditorPathTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 29;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(otherX3dEditorPathTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(otherX3dEditorChooserButton, "...");
        otherX3dEditorChooserButton.setToolTipText("Browse to select file location for local keystore");
        otherX3dEditorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherX3dEditorChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 29;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(otherX3dEditorChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(otherX3dEditorClearButton, "clear");
        otherX3dEditorClearButton.setToolTipText("Reset default file location for local application");
        otherX3dEditorClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherX3dEditorClearButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 29;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(otherX3dEditorClearButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(otherX3dEditorLaunchButton, "launch");
        otherX3dEditorLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        otherX3dEditorLaunchButton.setActionCommand("contactTF");
        otherX3dEditorLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherX3dEditorLaunchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 29;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(otherX3dEditorLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(otherX3dEditorDownloadButton, "find");
        otherX3dEditorDownloadButton.setToolTipText("Find other X3D Resources for scene editors");
        otherX3dEditorDownloadButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        otherX3dEditorDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherX3dEditorDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 29;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(otherX3dEditorDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(leftMarginSpacerLabel1, "   ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.gridheight = 23;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 0.1;
        x3dModelingToolsPanel.add(leftMarginSpacerLabel1, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(verticalSpacerLabel12, "   ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 31;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(verticalSpacerLabel12, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(reportModelingToolsButton, "Report");
        reportModelingToolsButton.setToolTipText(BaseCustomizer.MAILTO_TOOLTIP);
        reportModelingToolsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportModelingToolsButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 33;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dModelingToolsPanel.add(reportModelingToolsButton, gridBagConstraints);

        x3dOptionsTabbedPane.addTab("X3D Modeling Tools", x3dModelingToolsPanel);

        imageVolumeToolsPanel.setMinimumSize(new java.awt.Dimension(825, 600));
        imageVolumeToolsPanel.setPreferredSize(new java.awt.Dimension(825, 600));
        imageVolumeToolsPanel.setLayout(new java.awt.GridBagLayout());

        imageToolsLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        imageToolsLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        org.openide.awt.Mnemonics.setLocalizedText(imageToolsLabel, "Image Tools");
        imageToolsLabel.setToolTipText("External tools for modifying image files");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        imageVolumeToolsPanel.add(imageToolsLabel, gridBagConstraints);

        gimpEditorLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(gimpEditorLabel, "GIMP");
        gimpEditorLabel.setToolTipText("GNU Image Manipulation Program (GIMP)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(gimpEditorLabel, gridBagConstraints);

        gimpCheckBox.setSelected(true);
        gimpCheckBox.setToolTipText("Include when autolaunching tools");
        gimpCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        gimpCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gimpCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(gimpCheckBox, gridBagConstraints);

        gimpEditorTF.setToolTipText("File location for local application");
        gimpEditorTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                gimpEditorTFFocusLost(evt);
            }
        });
        gimpEditorTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gimpEditorTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(gimpEditorTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(gimpEditorChooserButton, "...");
        gimpEditorChooserButton.setToolTipText("Browse to select file location for local keystore");
        gimpEditorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gimpEditorChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(gimpEditorChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(gimpEditorDefaultButton, "default");
        gimpEditorDefaultButton.setToolTipText("Reset default file location for local application");
        gimpEditorDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gimpEditorDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(gimpEditorDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(gimpEditorLaunchButton, "launch");
        gimpEditorLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        gimpEditorLaunchButton.setActionCommand("contactTF");
        gimpEditorLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gimpEditorLaunchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(gimpEditorLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(gimpEditorDownloadButton, "get");
        gimpEditorDownloadButton.setToolTipText("Download tool from website");
        gimpEditorDownloadButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        gimpEditorDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gimpEditorDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(gimpEditorDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(gimpHelpButton, "help");
        gimpHelpButton.setToolTipText("Display tool help page");
        gimpHelpButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        gimpHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gimpHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(gimpHelpButton, gridBagConstraints);

        fijiEditorLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(fijiEditorLabel, "Fiji");
        fijiEditorLabel.setToolTipText("(Fiji Is Just) ImageJ with a better graphical user interface");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(fijiEditorLabel, gridBagConstraints);

        fijiCheckBox.setSelected(true);
        fijiCheckBox.setToolTipText("Include when autolaunching tools");
        fijiCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fijiCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fijiCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(fijiCheckBox, gridBagConstraints);

        fijiEditorTF.setToolTipText("File location for local application");
        fijiEditorTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                fijiEditorTFFocusLost(evt);
            }
        });
        fijiEditorTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fijiEditorTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(fijiEditorTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(fijiEditorChooserButton, "...");
        fijiEditorChooserButton.setToolTipText("Browse to select file location for local keystore");
        fijiEditorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fijiEditorChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(fijiEditorChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(fijiEditorDefaultButton, "default");
        fijiEditorDefaultButton.setToolTipText("Reset default file location for local application");
        fijiEditorDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fijiEditorDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(fijiEditorDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(fijiEditorLaunchButton, "launch");
        fijiEditorLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        fijiEditorLaunchButton.setActionCommand("contactTF");
        fijiEditorLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fijiEditorLaunchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(fijiEditorLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(fijiEditorDownloadButton, "get");
        fijiEditorDownloadButton.setToolTipText("Download tool from website");
        fijiEditorDownloadButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        fijiEditorDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fijiEditorDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(fijiEditorDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(fijiHelpButton, "help");
        fijiHelpButton.setToolTipText("Display tool help page");
        fijiHelpButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        fijiHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fijiHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(fijiHelpButton, gridBagConstraints);

        imageJEditorLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(imageJEditorLabel, "ImageJ");
        imageJEditorLabel.setToolTipText("Image Processing and Analysis in Java");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(imageJEditorLabel, gridBagConstraints);

        imageJEditorTF.setToolTipText("File location for local application");
        imageJEditorTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                imageJEditorTFFocusLost(evt);
            }
        });
        imageJEditorTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imageJEditorTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(imageJEditorTF, gridBagConstraints);

        imageJCheckBox.setSelected(true);
        imageJCheckBox.setToolTipText("Include when autolaunching tools");
        imageJCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imageJCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imageJCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(imageJCheckBox, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(imageJEditorChooserButton, "...");
        imageJEditorChooserButton.setToolTipText("Browse to select file location for local keystore");
        imageJEditorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imageJEditorChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(imageJEditorChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(imageJEditorDefaultButton, "default");
        imageJEditorDefaultButton.setToolTipText("Reset default file location for local application");
        imageJEditorDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imageJEditorDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(imageJEditorDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(imageJEditorLaunchButton, "launch");
        imageJEditorLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        imageJEditorLaunchButton.setActionCommand("contactTF");
        imageJEditorLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imageJEditorLaunchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(imageJEditorLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(imageJEditorDownloadButton, "get");
        imageJEditorDownloadButton.setToolTipText("Download tool from website");
        imageJEditorDownloadButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        imageJEditorDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imageJEditorDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(imageJEditorDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(imageJHelpButton, "help");
        imageJHelpButton.setToolTipText("Display tool help page");
        imageJHelpButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        imageJHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imageJHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(imageJHelpButton, gridBagConstraints);

        imageMagickEditorLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(imageMagickEditorLabel, "ImageMagick");
        imageMagickEditorLabel.setToolTipText("simple ImageMagick tool");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(imageMagickEditorLabel, gridBagConstraints);

        imageMagickCheckBox.setSelected(true);
        imageMagickCheckBox.setToolTipText("Include when autolaunching tools");
        imageMagickCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        imageMagickCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imageMagickCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(imageMagickCheckBox, gridBagConstraints);

        imageMagickEditorTF.setToolTipText("File location for local application");
        imageMagickEditorTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                imageMagickEditorTFFocusLost(evt);
            }
        });
        imageMagickEditorTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imageMagickEditorTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(imageMagickEditorTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(imageMagickEditorChooserButton, "...");
        imageMagickEditorChooserButton.setToolTipText("Browse to select file location for local keystore");
        imageMagickEditorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imageMagickEditorChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(imageMagickEditorChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(imageMagickEditorDefaultButton, "default");
        imageMagickEditorDefaultButton.setToolTipText("Reset default file location for local application");
        imageMagickEditorDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imageMagickEditorDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(imageMagickEditorDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(imageMagickEditorLaunchButton, "launch");
        imageMagickEditorLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        imageMagickEditorLaunchButton.setActionCommand("contactTF");
        imageMagickEditorLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imageMagickEditorLaunchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(imageMagickEditorLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(imageMagickEditorDownloadButton, "get");
        imageMagickEditorDownloadButton.setToolTipText("Download tool from website");
        imageMagickEditorDownloadButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        imageMagickEditorDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imageMagickEditorDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(imageMagickEditorDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(imageMagickHelpButton, "help");
        imageMagickHelpButton.setToolTipText("Display tool help page");
        imageMagickHelpButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        imageMagickHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imageMagickHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(imageMagickHelpButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(verticalSpacerLabel22, "   ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(verticalSpacerLabel22, gridBagConstraints);

        otherImageEditorNameTF.setBackground(new java.awt.Color(242, 242, 242));
        otherImageEditorNameTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        otherImageEditorNameTF.setText("Other image tool");
        otherImageEditorNameTF.setToolTipText("Enter name of alternate tool");
        otherImageEditorNameTF.setBorder(null);
        otherImageEditorNameTF.setMinimumSize(new java.awt.Dimension(6, 24));
        otherImageEditorNameTF.setPreferredSize(new java.awt.Dimension(6, 24));
        otherImageEditorNameTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherImageEditorNameTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(otherImageEditorNameTF, gridBagConstraints);

        otherImageEditorCheckBox.setToolTipText("Include when autolaunching tools");
        otherImageEditorCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        otherImageEditorCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherImageEditorCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(otherImageEditorCheckBox, gridBagConstraints);

        otherImageEditorPathTF.setToolTipText("Enter file location for local application");
        otherImageEditorPathTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                otherImageEditorPathTFFocusLost(evt);
            }
        });
        otherImageEditorPathTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherImageEditorPathTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(otherImageEditorPathTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(otherImageEditorChooserButton, "...");
        otherImageEditorChooserButton.setToolTipText("Browse to select file location for local keystore");
        otherImageEditorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherImageEditorChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(otherImageEditorChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(otherImageEditorClearButton, "clear");
        otherImageEditorClearButton.setToolTipText("Reset default file location for local application");
        otherImageEditorClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherImageEditorClearButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(otherImageEditorClearButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(otherImageEditorLaunchButton, "launch");
        otherImageEditorLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        otherImageEditorLaunchButton.setActionCommand("contactTF");
        otherImageEditorLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherImageEditorLaunchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(otherImageEditorLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(otherImageEditorDownloadButton, "find");
        otherImageEditorDownloadButton.setToolTipText("Find other image tools using X3D Scene Authoring Hints");
        otherImageEditorDownloadButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        otherImageEditorDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherImageEditorDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(otherImageEditorDownloadButton, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 10, 3);
        imageVolumeToolsPanel.add(jSeparator3, gridBagConstraints);

        externalVolumeEditorLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        externalVolumeEditorLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        org.openide.awt.Mnemonics.setLocalizedText(externalVolumeEditorLabel, "Volume Authoring Tools");
        externalVolumeEditorLabel.setToolTipText("External tools for modifying X3D scenes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        imageVolumeToolsPanel.add(externalVolumeEditorLabel, gridBagConstraints);

        fijiImageJvolumeHintLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        org.openide.awt.Mnemonics.setLocalizedText(fijiImageJvolumeHintLabel, "<html><b>Fiji</b> and <b>ImageJ</b> (above) also support image slicing from volumes.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(fijiImageJvolumeHintLabel, gridBagConstraints);

        itksnapVolumeEditorLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(itksnapVolumeEditorLabel, "ITK-SNAP");
        itksnapVolumeEditorLabel.setToolTipText("Segment structures in 3D, 4D biomedical images");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(itksnapVolumeEditorLabel, gridBagConstraints);

        itksnapVolumeEditorCheckBox.setSelected(true);
        itksnapVolumeEditorCheckBox.setToolTipText("Include when autolaunching tools");
        itksnapVolumeEditorCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        itksnapVolumeEditorCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itksnapVolumeEditorCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(itksnapVolumeEditorCheckBox, gridBagConstraints);

        itksnapVolumeEditorPathTF.setToolTipText("File location for local application");
        itksnapVolumeEditorPathTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                itksnapVolumeEditorPathTFFocusLost(evt);
            }
        });
        itksnapVolumeEditorPathTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itksnapVolumeEditorPathTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(itksnapVolumeEditorPathTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(itksnapVolumeEditorChooserButton, "...");
        itksnapVolumeEditorChooserButton.setToolTipText("Browse to select file location for local keystore");
        itksnapVolumeEditorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itksnapVolumeEditorChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(itksnapVolumeEditorChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(itksnapVolumeEditorDefaultButton, "default");
        itksnapVolumeEditorDefaultButton.setToolTipText("Reset default file location for local application");
        itksnapVolumeEditorDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itksnapVolumeEditorDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(itksnapVolumeEditorDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(itksnapVolumeEditorLaunchButton, "launch");
        itksnapVolumeEditorLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        itksnapVolumeEditorLaunchButton.setActionCommand("contactTF");
        itksnapVolumeEditorLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itksnapVolumeEditorLaunchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(itksnapVolumeEditorLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(itksnapVolumeEditorDownloadButton, "get");
        itksnapVolumeEditorDownloadButton.setToolTipText("Download tool from website");
        itksnapVolumeEditorDownloadButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        itksnapVolumeEditorDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itksnapVolumeEditorDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(itksnapVolumeEditorDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(itksnapVolumeEditorHelpButton, "help");
        itksnapVolumeEditorHelpButton.setToolTipText("Display tool help page");
        itksnapVolumeEditorHelpButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        itksnapVolumeEditorHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itksnapVolumeEditorHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(itksnapVolumeEditorHelpButton, gridBagConstraints);

        seg3dVolumeEditorLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(seg3dVolumeEditorLabel, "Seg3d");
        seg3dVolumeEditorLabel.setToolTipText("NIH volume segmentation and processing tool");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 21;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(seg3dVolumeEditorLabel, gridBagConstraints);

        seg3dVolumeEditorCheckBox.setSelected(true);
        seg3dVolumeEditorCheckBox.setToolTipText("Include when autolaunching tools");
        seg3dVolumeEditorCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        seg3dVolumeEditorCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seg3dVolumeEditorCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 21;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(seg3dVolumeEditorCheckBox, gridBagConstraints);

        seg3dVolumeEditorPathTF.setToolTipText("File location for local application");
        seg3dVolumeEditorPathTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                seg3dVolumeEditorPathTFFocusLost(evt);
            }
        });
        seg3dVolumeEditorPathTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seg3dVolumeEditorPathTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 21;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(seg3dVolumeEditorPathTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(seg3dVolumeEditorChooserButton, "...");
        seg3dVolumeEditorChooserButton.setToolTipText("Browse to select file location for local keystore");
        seg3dVolumeEditorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seg3dVolumeEditorChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 21;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(seg3dVolumeEditorChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(seg3dVolumeEditorDefaultButton, "default");
        seg3dVolumeEditorDefaultButton.setToolTipText("Reset default file location for local application");
        seg3dVolumeEditorDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seg3dVolumeEditorDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 21;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(seg3dVolumeEditorDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(seg3dVolumeEditorLaunchButton, "launch");
        seg3dVolumeEditorLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        seg3dVolumeEditorLaunchButton.setActionCommand("contactTF");
        seg3dVolumeEditorLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seg3dVolumeEditorLaunchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 21;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(seg3dVolumeEditorLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(seg3dVolumeEditorDownloadButton, "get");
        seg3dVolumeEditorDownloadButton.setToolTipText("Download tool from website");
        seg3dVolumeEditorDownloadButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        seg3dVolumeEditorDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seg3dVolumeEditorDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 21;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(seg3dVolumeEditorDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(seg3dVolumeEditorHelpButton, "help");
        seg3dVolumeEditorHelpButton.setToolTipText("Display tool help page");
        seg3dVolumeEditorHelpButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        seg3dVolumeEditorHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                seg3dVolumeEditorHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 21;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(seg3dVolumeEditorHelpButton, gridBagConstraints);

        slicer3dVolumeEditorLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(slicer3dVolumeEditorLabel, "3D Slicer");
        slicer3dVolumeEditorLabel.setToolTipText("Visualization, processing, segmentation, registration, and analysis of 3D images and meshes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 23;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(slicer3dVolumeEditorLabel, gridBagConstraints);

        slicer3dVolumeEditorCheckBox.setSelected(true);
        slicer3dVolumeEditorCheckBox.setToolTipText("Include when autolaunching tools");
        slicer3dVolumeEditorCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slicer3dVolumeEditorCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                slicer3dVolumeEditorCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 23;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(slicer3dVolumeEditorCheckBox, gridBagConstraints);

        slicer3dVolumeEditorPathTF.setToolTipText("File location for local application");
        slicer3dVolumeEditorPathTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                slicer3dVolumeEditorPathTFFocusLost(evt);
            }
        });
        slicer3dVolumeEditorPathTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                slicer3dVolumeEditorPathTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 23;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(slicer3dVolumeEditorPathTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(slicer3dVolumeEditorChooserButton, "...");
        slicer3dVolumeEditorChooserButton.setToolTipText("Browse to select file location for local keystore");
        slicer3dVolumeEditorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                slicer3dVolumeEditorChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 23;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(slicer3dVolumeEditorChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(slicer3dVolumeEditorDefaultButton, "default");
        slicer3dVolumeEditorDefaultButton.setToolTipText("Reset default file location for local application");
        slicer3dVolumeEditorDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                slicer3dVolumeEditorDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 23;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(slicer3dVolumeEditorDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(slicer3dVolumeEditorLaunchButton, "launch");
        slicer3dVolumeEditorLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        slicer3dVolumeEditorLaunchButton.setActionCommand("contactTF");
        slicer3dVolumeEditorLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                slicer3dVolumeEditorLaunchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 23;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(slicer3dVolumeEditorLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(slicer3dVolumeEditorDownloadButton, "get");
        slicer3dVolumeEditorDownloadButton.setToolTipText("Download tool from website");
        slicer3dVolumeEditorDownloadButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        slicer3dVolumeEditorDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                slicer3dVolumeEditorDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 23;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(slicer3dVolumeEditorDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(slicer3dVolumeEditorHelpButtonslicer3dVolume, "help");
        slicer3dVolumeEditorHelpButtonslicer3dVolume.setToolTipText("Display tool help page");
        slicer3dVolumeEditorHelpButtonslicer3dVolume.setMargin(new java.awt.Insets(2, 3, 2, 3));
        slicer3dVolumeEditorHelpButtonslicer3dVolume.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                slicer3dVolumeEditorHelpButtonslicer3dVolumeActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 23;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(slicer3dVolumeEditorHelpButtonslicer3dVolume, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(verticalSpacerLabel18, "   ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 25;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(verticalSpacerLabel18, gridBagConstraints);

        otherVolumeEditorNameTF.setBackground(new java.awt.Color(242, 242, 242));
        otherVolumeEditorNameTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        otherVolumeEditorNameTF.setText("Other volume tool");
        otherVolumeEditorNameTF.setToolTipText("Enter name of alternate tool");
        otherVolumeEditorNameTF.setBorder(null);
        otherVolumeEditorNameTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherVolumeEditorNameTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 27;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 120;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(otherVolumeEditorNameTF, gridBagConstraints);

        otherVolumeEditorCheckBox.setToolTipText("Include when autolaunching tools");
        otherVolumeEditorCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        otherVolumeEditorCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherVolumeEditorCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 27;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(otherVolumeEditorCheckBox, gridBagConstraints);

        otherVolumeEditorPathTF.setToolTipText("Enter file location for local application");
        otherVolumeEditorPathTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                otherVolumeEditorPathTFFocusLost(evt);
            }
        });
        otherVolumeEditorPathTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherVolumeEditorPathTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 27;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(otherVolumeEditorPathTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(otherVolumeChooserButton, "...");
        otherVolumeChooserButton.setToolTipText("Browse to select file location for local keystore");
        otherVolumeChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherVolumeChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 27;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(otherVolumeChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(otherVolumeEditorClearButton, "clear");
        otherVolumeEditorClearButton.setToolTipText("Reset default file location for local application");
        otherVolumeEditorClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherVolumeEditorClearButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 27;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(otherVolumeEditorClearButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(otherVolumeEditorLaunchButton, "launch");
        otherVolumeEditorLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        otherVolumeEditorLaunchButton.setActionCommand("contactTF");
        otherVolumeEditorLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherVolumeEditorLaunchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 27;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(otherVolumeEditorLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(otherVolumeEditorDownloadButton, "find");
        otherVolumeEditorDownloadButton.setToolTipText("Find other video tools using X3D Scene Authoring Hints");
        otherVolumeEditorDownloadButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        otherVolumeEditorDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherVolumeEditorDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 27;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(otherVolumeEditorDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(verticalSpacerLabel14, "   ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 29;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(verticalSpacerLabel14, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(reportImageVolumeToolsButton, "Report");
        reportImageVolumeToolsButton.setToolTipText(BaseCustomizer.MAILTO_TOOLTIP);
        reportImageVolumeToolsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportImageVolumeToolsButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 31;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(reportImageVolumeToolsButton, gridBagConstraints);

        svgToolHintLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        org.openide.awt.Mnemonics.setLocalizedText(svgToolHintLabel, "<html> See <b>Web and Multipmedia Tools</b> tab (above) for SVG authoring tools.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        imageVolumeToolsPanel.add(svgToolHintLabel, gridBagConstraints);

        x3dOptionsTabbedPane.addTab("Image and Volume Tools", imageVolumeToolsPanel);

        webMultimediaToolsPanel.setMinimumSize(new java.awt.Dimension(825, 600));
        webMultimediaToolsPanel.setPreferredSize(new java.awt.Dimension(825, 600));
        webMultimediaToolsPanel.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(verticalSpacerLabel7, "   ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 3, 3);
        webMultimediaToolsPanel.add(verticalSpacerLabel7, gridBagConstraints);

        audioToolsLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(audioToolsLabel, "Audio Tools");
        audioToolsLabel.setToolTipText("External tools for modifying audio recordings and files");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 6, 3, 3);
        webMultimediaToolsPanel.add(audioToolsLabel, gridBagConstraints);

        audacityEditorLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(audacityEditorLabel, "Audacity");
        audacityEditorLabel.setToolTipText("Audacity Audio Recording Editor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(audacityEditorLabel, gridBagConstraints);

        audacityEditorCheckBox.setSelected(true);
        audacityEditorCheckBox.setToolTipText("Include when autolaunching tools");
        audacityEditorCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        audacityEditorCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                audacityEditorCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(audacityEditorCheckBox, gridBagConstraints);

        audacityEditorPathTF.setToolTipText("File location for local application");
        audacityEditorPathTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                audacityEditorPathTFFocusLost(evt);
            }
        });
        audacityEditorPathTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                audacityEditorPathTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(audacityEditorPathTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(audacityEditorChooserButton, "...");
        audacityEditorChooserButton.setToolTipText("Browse to select file location for local keystore");
        audacityEditorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                audacityEditorChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(audacityEditorChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(audacityEditorDefaultButton, "default");
        audacityEditorDefaultButton.setToolTipText("Reset default file location for local application");
        audacityEditorDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                audacityEditorDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(audacityEditorDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(audacityEditorLaunchButton, "launch");
        audacityEditorLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        audacityEditorLaunchButton.setActionCommand("contactTF");
        audacityEditorLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                audacityEditorLaunchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(audacityEditorLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(audacityEditorDownloadButton, "get");
        audacityEditorDownloadButton.setToolTipText("Download tool from website");
        audacityEditorDownloadButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        audacityEditorDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                audacityEditorDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 6);
        webMultimediaToolsPanel.add(audacityEditorDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(audacityEditorHelpButton, "help");
        audacityEditorHelpButton.setToolTipText("Display tool help page");
        audacityEditorHelpButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        audacityEditorHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                audacityEditorHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(audacityEditorHelpButton, gridBagConstraints);

        otherAudioEditorNameTF.setBackground(new java.awt.Color(242, 242, 242));
        otherAudioEditorNameTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        otherAudioEditorNameTF.setText("Other audio tool");
        otherAudioEditorNameTF.setToolTipText("Enter name of alternate tool");
        otherAudioEditorNameTF.setMinimumSize(new java.awt.Dimension(6, 24));
        otherAudioEditorNameTF.setPreferredSize(new java.awt.Dimension(6, 24));
        otherAudioEditorNameTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherAudioEditorNameTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 160;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(otherAudioEditorNameTF, gridBagConstraints);

        otherAudioEditorCheckBox.setToolTipText("Include when autolaunching tools");
        otherAudioEditorCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        otherAudioEditorCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherAudioEditorCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(otherAudioEditorCheckBox, gridBagConstraints);

        otherAudioEditorPathTF.setToolTipText("Enter file location for local application");
        otherAudioEditorPathTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                otherAudioEditorPathTFFocusLost(evt);
            }
        });
        otherAudioEditorPathTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherAudioEditorPathTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(otherAudioEditorPathTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(otherAudioEditorChooserButton, "...");
        otherAudioEditorChooserButton.setToolTipText("Browse to select file location for local keystore");
        otherAudioEditorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherAudioEditorChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(otherAudioEditorChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(otherAudioEditorClearButton, "clear");
        otherAudioEditorClearButton.setToolTipText("Reset default file location for local application");
        otherAudioEditorClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherAudioEditorClearButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(otherAudioEditorClearButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(otherAudioEditorLaunchButton, "launch");
        otherAudioEditorLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        otherAudioEditorLaunchButton.setActionCommand("contactTF");
        otherAudioEditorLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherAudioEditorLaunchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(otherAudioEditorLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(otherAudioEditorDownloadButton, "find");
        otherAudioEditorDownloadButton.setToolTipText("Find other audio tools using X3D Scene Authoring Hints");
        otherAudioEditorDownloadButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        otherAudioEditorDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherAudioEditorDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(otherAudioEditorDownloadButton, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 10, 3);
        webMultimediaToolsPanel.add(jSeparator4, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(verticalSpacerLabel13, "   ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(verticalSpacerLabel13, gridBagConstraints);

        htmlToolsLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        htmlToolsLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        org.openide.awt.Mnemonics.setLocalizedText(htmlToolsLabel, "HTML, CSS, and SVG Tools");
        htmlToolsLabel.setToolTipText("External tools for modifying image files");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        webMultimediaToolsPanel.add(htmlToolsLabel, gridBagConstraints);

        amayaEditorLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(amayaEditorLabel, "Amaya");
        amayaEditorLabel.setToolTipText("Web document editor by W3C");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(amayaEditorLabel, gridBagConstraints);

        amayaEditorPathTF.setToolTipText("File location for local application");
        amayaEditorPathTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                amayaEditorPathTFFocusLost(evt);
            }
        });
        amayaEditorPathTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amayaEditorPathTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(amayaEditorPathTF, gridBagConstraints);

        amayaEditorCheckBox.setSelected(true);
        amayaEditorCheckBox.setToolTipText("Include when autolaunching tools");
        amayaEditorCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        amayaEditorCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amayaEditorCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(amayaEditorCheckBox, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(amayaEditorChooserButton, "...");
        amayaEditorChooserButton.setToolTipText("Browse to select file location for local keystore");
        amayaEditorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amayaEditorChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(amayaEditorChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(amayaEditorDefaultButton, "default");
        amayaEditorDefaultButton.setToolTipText("Reset default file location for local application");
        amayaEditorDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amayaEditorDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(amayaEditorDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(amayaEditorLaunchButton, "launch");
        amayaEditorLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        amayaEditorLaunchButton.setActionCommand("contactTF");
        amayaEditorLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amayaEditorLaunchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(amayaEditorLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(amayaEditorDownloadButton, "get");
        amayaEditorDownloadButton.setToolTipText("Download tool from website");
        amayaEditorDownloadButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        amayaEditorDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amayaEditorDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(amayaEditorDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(amayaEditorHelpButton, "help");
        amayaEditorHelpButton.setToolTipText("Display tool help page");
        amayaEditorHelpButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        amayaEditorHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                amayaEditorHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(amayaEditorHelpButton, gridBagConstraints);

        batikEditorLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(batikEditorLabel, "Batik");
        batikEditorLabel.setToolTipText("Apache Batik SVG Toolkit");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(batikEditorLabel, gridBagConstraints);

        batikEditorPathTF.setToolTipText("File location for local application");
        batikEditorPathTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                batikEditorPathTFFocusLost(evt);
            }
        });
        batikEditorPathTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batikEditorPathTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(batikEditorPathTF, gridBagConstraints);

        batikEditorCheckBox.setSelected(true);
        batikEditorCheckBox.setToolTipText("Include when autolaunching tools");
        batikEditorCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        batikEditorCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batikEditorCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(batikEditorCheckBox, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(batikEditorChooserButton, "...");
        batikEditorChooserButton.setToolTipText("Browse to select file location for local keystore");
        batikEditorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batikEditorChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(batikEditorChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(batikEditorDefaultButton, "default");
        batikEditorDefaultButton.setToolTipText("Reset default file location for local application");
        batikEditorDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batikEditorDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(batikEditorDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(batikEditorLaunchButton, "launch");
        batikEditorLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        batikEditorLaunchButton.setActionCommand("contactTF");
        batikEditorLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batikEditorLaunchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(batikEditorLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(batikEditorHelpButton, "help");
        batikEditorHelpButton.setToolTipText("Display tool help page");
        batikEditorHelpButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        batikEditorHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batikEditorHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(batikEditorHelpButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(batikEditorDownloadButton, "get");
        batikEditorDownloadButton.setToolTipText("Download tool from website");
        batikEditorDownloadButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        batikEditorDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                batikEditorDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(batikEditorDownloadButton, gridBagConstraints);

        inkscapeEditorLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(inkscapeEditorLabel, "Inkscape");
        inkscapeEditorLabel.setToolTipText("Draw freely with Inkscpae design tool");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(inkscapeEditorLabel, gridBagConstraints);

        inkscapeEditorPathTF.setToolTipText("File location for local application");
        inkscapeEditorPathTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                inkscapeEditorPathTFFocusLost(evt);
            }
        });
        inkscapeEditorPathTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inkscapeEditorPathTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(inkscapeEditorPathTF, gridBagConstraints);

        inkscapeEditorCheckBox.setSelected(true);
        inkscapeEditorCheckBox.setToolTipText("Include when autolaunching tools");
        inkscapeEditorCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inkscapeEditorCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inkscapeEditorCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(inkscapeEditorCheckBox, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(inkscapeEditorChooserButton, "...");
        inkscapeEditorChooserButton.setToolTipText("Browse to select file location for local keystore");
        inkscapeEditorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inkscapeEditorChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(inkscapeEditorChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(inkscapeEditorDefaultButton, "default");
        inkscapeEditorDefaultButton.setToolTipText("Reset default file location for local application");
        inkscapeEditorDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inkscapeEditorDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(inkscapeEditorDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(inkscapeEditorLaunchButton, "launch");
        inkscapeEditorLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        inkscapeEditorLaunchButton.setActionCommand("contactTF");
        inkscapeEditorLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inkscapeEditorLaunchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(inkscapeEditorLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(inkscapeEditorHelpButton, "help");
        inkscapeEditorHelpButton.setToolTipText("Display tool help page");
        inkscapeEditorHelpButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        inkscapeEditorHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inkscapeEditorHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(inkscapeEditorHelpButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(inkscapeEditorDownloadButton, "get");
        inkscapeEditorDownloadButton.setToolTipText("Download tool from website");
        inkscapeEditorDownloadButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        inkscapeEditorDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                inkscapeEditorDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(inkscapeEditorDownloadButton, gridBagConstraints);

        svgeditEditorLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(svgeditEditorLabel, "svg-edit");
        svgeditEditorLabel.setToolTipText("Web-based, JavaScript-driven SVG drawing editor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(svgeditEditorLabel, gridBagConstraints);

        svgeditEditorPathTF.setToolTipText("File location for local application");
        svgeditEditorPathTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                svgeditEditorPathTFFocusLost(evt);
            }
        });
        svgeditEditorPathTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                svgeditEditorPathTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(svgeditEditorPathTF, gridBagConstraints);

        svgeditEditorCheckBox.setSelected(true);
        svgeditEditorCheckBox.setToolTipText("Include when autolaunching tools");
        svgeditEditorCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        svgeditEditorCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                svgeditEditorCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(svgeditEditorCheckBox, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(svgeditEditorChooserButton, "...");
        svgeditEditorChooserButton.setToolTipText("Browse to select file location for local keystore");
        svgeditEditorChooserButton.setEnabled(false);
        svgeditEditorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                svgeditEditorChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(svgeditEditorChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(svgeditEditorDefaultButton, "default");
        svgeditEditorDefaultButton.setToolTipText("Reset default file location for local application");
        svgeditEditorDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                svgeditEditorDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(svgeditEditorDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(svgeditEditorLaunchButton, "launch");
        svgeditEditorLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        svgeditEditorLaunchButton.setActionCommand("contactTF");
        svgeditEditorLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                svgeditEditorLaunchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(svgeditEditorLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(svgeditEditorDownloadButton, "get");
        svgeditEditorDownloadButton.setToolTipText("Download tool from website");
        svgeditEditorDownloadButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        svgeditEditorDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                svgeditEditorDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(svgeditEditorDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(svgeditEditorHelpButton, "help");
        svgeditEditorHelpButton.setToolTipText("Display tool help page");
        svgeditEditorHelpButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        svgeditEditorHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                svgeditEditorHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(svgeditEditorHelpButton, gridBagConstraints);

        otherHtml5EditorNameTF.setBackground(new java.awt.Color(242, 242, 242));
        otherHtml5EditorNameTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        otherHtml5EditorNameTF.setText("Other tool");
        otherHtml5EditorNameTF.setToolTipText("Enter name of alternate tool");
        otherHtml5EditorNameTF.setMinimumSize(new java.awt.Dimension(6, 24));
        otherHtml5EditorNameTF.setPreferredSize(new java.awt.Dimension(6, 24));
        otherHtml5EditorNameTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherHtml5EditorNameTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 160;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(otherHtml5EditorNameTF, gridBagConstraints);

        otherHtml5EditorCheckBox.setToolTipText("Include when autolaunching tools");
        otherHtml5EditorCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        otherHtml5EditorCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherHtml5EditorCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(otherHtml5EditorCheckBox, gridBagConstraints);

        otherHtml5EditorPathTF.setToolTipText("Enter file location for local application");
        otherHtml5EditorPathTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                otherHtml5EditorPathTFFocusLost(evt);
            }
        });
        otherHtml5EditorPathTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherHtml5EditorPathTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(otherHtml5EditorPathTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(otherHtml5EditorChooserButton, "...");
        otherHtml5EditorChooserButton.setToolTipText("Browse to select file location for local keystore");
        otherHtml5EditorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherHtml5EditorChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(otherHtml5EditorChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(otherHtml5EditorClearButton, "clear");
        otherHtml5EditorClearButton.setToolTipText("Reset default file location for local application");
        otherHtml5EditorClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherHtml5EditorClearButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(otherHtml5EditorClearButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(otherHtml5EditorLaunchButton, "launch");
        otherHtml5EditorLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        otherHtml5EditorLaunchButton.setActionCommand("contactTF");
        otherHtml5EditorLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherHtml5EditorLaunchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(otherHtml5EditorLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(otherHtml5EditorDownloadButton, "find");
        otherHtml5EditorDownloadButton.setToolTipText("Find other image tools using X3D Scene Authoring Hints");
        otherHtml5EditorDownloadButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        otherHtml5EditorDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherHtml5EditorDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(otherHtml5EditorDownloadButton, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 10, 3);
        webMultimediaToolsPanel.add(jSeparator2, gridBagConstraints);

        externalVideoEditorLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        externalVideoEditorLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        org.openide.awt.Mnemonics.setLocalizedText(externalVideoEditorLabel, "Video Tools");
        externalVideoEditorLabel.setToolTipText("External tools for modifying X3D scenes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        webMultimediaToolsPanel.add(externalVideoEditorLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(verticalSpacerLabel11, "   ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(verticalSpacerLabel11, gridBagConstraints);

        vlcPlayerLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(vlcPlayerLabel, "vlc");
        vlcPlayerLabel.setToolTipText("vlc video player");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(vlcPlayerLabel, gridBagConstraints);

        vlcPlayerCheckBox.setSelected(true);
        vlcPlayerCheckBox.setToolTipText("Include when autolaunching tools");
        vlcPlayerCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        vlcPlayerCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vlcPlayerCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(vlcPlayerCheckBox, gridBagConstraints);

        vlcPlayerPathTF.setToolTipText("File location for local application");
        vlcPlayerPathTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                vlcPlayerPathTFFocusLost(evt);
            }
        });
        vlcPlayerPathTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vlcPlayerPathTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(vlcPlayerPathTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(vlcPlayerChooserButton, "...");
        vlcPlayerChooserButton.setToolTipText("Browse to select file location for local keystore");
        vlcPlayerChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vlcPlayerChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(vlcPlayerChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(vlcPlayerDefaultButton, "default");
        vlcPlayerDefaultButton.setToolTipText("Reset default file location for local application");
        vlcPlayerDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vlcPlayerDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(vlcPlayerDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(vlcPlayerLaunchButton, "launch");
        vlcPlayerLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        vlcPlayerLaunchButton.setActionCommand("contactTF");
        vlcPlayerLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vlcPlayerLaunchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(vlcPlayerLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(vlcPlayerDownloadButton, "get");
        vlcPlayerDownloadButton.setToolTipText("Download tool from website");
        vlcPlayerDownloadButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        vlcPlayerDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vlcPlayerDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(vlcPlayerDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(vlcPlayerHelpButton, "help");
        vlcPlayerHelpButton.setToolTipText("Display tool help page");
        vlcPlayerHelpButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        vlcPlayerHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                vlcPlayerHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(vlcPlayerHelpButton, gridBagConstraints);

        otherVideoEditorNameTF.setBackground(new java.awt.Color(242, 242, 242));
        otherVideoEditorNameTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        otherVideoEditorNameTF.setText("Other video tool");
        otherVideoEditorNameTF.setToolTipText("Enter name of alternate tool");
        otherVideoEditorNameTF.setMinimumSize(new java.awt.Dimension(6, 24));
        otherVideoEditorNameTF.setPreferredSize(new java.awt.Dimension(6, 24));
        otherVideoEditorNameTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherVideoEditorNameTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 160;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(otherVideoEditorNameTF, gridBagConstraints);

        otherVideoEditorCheckBox.setSelected(true);
        otherVideoEditorCheckBox.setToolTipText("Include when autolaunching tools");
        otherVideoEditorCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        otherVideoEditorCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherVideoEditorCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(otherVideoEditorCheckBox, gridBagConstraints);

        otherVideoEditorPathTF.setToolTipText("Enter file location for local application");
        otherVideoEditorPathTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                otherVideoEditorPathTFFocusLost(evt);
            }
        });
        otherVideoEditorPathTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherVideoEditorPathTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(otherVideoEditorPathTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(otherVideoChooserButton, "...");
        otherVideoChooserButton.setToolTipText("Browse to select file location for local keystore");
        otherVideoChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherVideoChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(otherVideoChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(otherVideoEditorClearButton, "clear");
        otherVideoEditorClearButton.setToolTipText("Reset default file location for local application");
        otherVideoEditorClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherVideoEditorClearButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(otherVideoEditorClearButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(otherVideoEditorLaunchButton, "launch");
        otherVideoEditorLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        otherVideoEditorLaunchButton.setActionCommand("contactTF");
        otherVideoEditorLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherVideoEditorLaunchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(otherVideoEditorLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(otherVideoEditorDownloadButton, "find");
        otherVideoEditorDownloadButton.setToolTipText("Find other video tools using X3D Scene Authoring Hints");
        otherVideoEditorDownloadButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        otherVideoEditorDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherVideoEditorDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(otherVideoEditorDownloadButton, gridBagConstraints);

        externalOntologyEditorLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        externalOntologyEditorLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        org.openide.awt.Mnemonics.setLocalizedText(externalOntologyEditorLabel, "Semantic Web Ontology Tools");
        externalOntologyEditorLabel.setToolTipText("External tools for modifying X3D scenes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        webMultimediaToolsPanel.add(externalOntologyEditorLabel, gridBagConstraints);

        protegePlayerLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(protegePlayerLabel, "Protege");
        protegePlayerLabel.setToolTipText("Protege Ontology Editor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 23;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(protegePlayerLabel, gridBagConstraints);

        protegePlayerCheckBox.setSelected(true);
        protegePlayerCheckBox.setToolTipText("Include when autolaunching tools");
        protegePlayerCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        protegePlayerCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                protegePlayerCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 23;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(protegePlayerCheckBox, gridBagConstraints);

        protegePlayerPathTF.setToolTipText("File location for local application");
        protegePlayerPathTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                protegePlayerPathTFFocusLost(evt);
            }
        });
        protegePlayerPathTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                protegePlayerPathTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 23;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(protegePlayerPathTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(protegePlayerChooserButton, "...");
        protegePlayerChooserButton.setToolTipText("Browse to select file location for local keystore");
        protegePlayerChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                protegePlayerChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 23;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(protegePlayerChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(protegePlayerDefaultButton, "default");
        protegePlayerDefaultButton.setToolTipText("Reset default file location for local application");
        protegePlayerDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                protegePlayerDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 23;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(protegePlayerDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(protegePlayerLaunchButton, "launch");
        protegePlayerLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        protegePlayerLaunchButton.setActionCommand("contactTF");
        protegePlayerLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                protegePlayerLaunchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 23;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(protegePlayerLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(protegePlayerDownloadButton, "get");
        protegePlayerDownloadButton.setToolTipText("Download tool from website");
        protegePlayerDownloadButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        protegePlayerDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                protegePlayerDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 23;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(protegePlayerDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(protegePlayerHelpButton, "help");
        protegePlayerHelpButton.setToolTipText("Display tool help page");
        protegePlayerHelpButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        protegePlayerHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                protegePlayerHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 23;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(protegePlayerHelpButton, gridBagConstraints);

        otherSemanticWebEditorNameTF.setBackground(new java.awt.Color(242, 242, 242));
        otherSemanticWebEditorNameTF.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        otherSemanticWebEditorNameTF.setText("Other semantic tool");
        otherSemanticWebEditorNameTF.setToolTipText("Enter name of alternate tool");
        otherSemanticWebEditorNameTF.setMinimumSize(new java.awt.Dimension(6, 24));
        otherSemanticWebEditorNameTF.setPreferredSize(new java.awt.Dimension(6, 24));
        otherSemanticWebEditorNameTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherSemanticWebEditorNameTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 160;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(otherSemanticWebEditorNameTF, gridBagConstraints);

        otherSemanticWebEditorCheckBox.setToolTipText("Include when autolaunching tools");
        otherSemanticWebEditorCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        otherSemanticWebEditorCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherSemanticWebEditorCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(otherSemanticWebEditorCheckBox, gridBagConstraints);

        otherSemanticWebEditorPathTF.setToolTipText("Enter file location for local application");
        otherSemanticWebEditorPathTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                otherSemanticWebEditorPathTFFocusLost(evt);
            }
        });
        otherSemanticWebEditorPathTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherSemanticWebEditorPathTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 400;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(otherSemanticWebEditorPathTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(otherSemanticWebEditorChooserButton, "...");
        otherSemanticWebEditorChooserButton.setToolTipText("Browse to select file location for local keystore");
        otherSemanticWebEditorChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherSemanticWebEditorChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(otherSemanticWebEditorChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(otherSemanticWebEditorClearButton, "clear");
        otherSemanticWebEditorClearButton.setToolTipText("Reset default file location for local application");
        otherSemanticWebEditorClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherSemanticWebEditorClearButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(otherSemanticWebEditorClearButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(otherSemanticWebEditorLaunchButton, "launch");
        otherSemanticWebEditorLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        otherSemanticWebEditorLaunchButton.setActionCommand("contactTF");
        otherSemanticWebEditorLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherSemanticWebEditorLaunchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(otherSemanticWebEditorLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(otherSemanticWebEditorDownloadButton, "find");
        otherSemanticWebEditorDownloadButton.setToolTipText("Find other video tools using X3D Scene Authoring Hints");
        otherSemanticWebEditorDownloadButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        otherSemanticWebEditorDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                otherSemanticWebEditorDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(otherSemanticWebEditorDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(leftMarginSpacerLabel, "   ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridheight = 29;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 0.1;
        webMultimediaToolsPanel.add(leftMarginSpacerLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(verticalSpacerLabel6, "   ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 27;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(verticalSpacerLabel6, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(reportWebMultimediaToolsButton, "Report");
        reportWebMultimediaToolsButton.setToolTipText(BaseCustomizer.MAILTO_TOOLTIP);
        reportWebMultimediaToolsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportWebMultimediaToolsButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 29;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        webMultimediaToolsPanel.add(reportWebMultimediaToolsButton, gridBagConstraints);

        x3dOptionsTabbedPane.addTab("Web and Multimedia Tools", webMultimediaToolsPanel);

        x3dEditVisualizationPreferencesPanel.setToolTipText("Editing and visualization options");
        x3dEditVisualizationPreferencesPanel.setMinimumSize(new java.awt.Dimension(825, 600));
        x3dEditVisualizationPreferencesPanel.setPreferredSize(new java.awt.Dimension(825, 600));
        x3dEditVisualizationPreferencesPanel.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(verticalSpacerLabel2, "   ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        x3dEditVisualizationPreferencesPanel.add(verticalSpacerLabel2, gridBagConstraints);

        nodeEditingOptionsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Node editing options", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        nodeEditingOptionsPanel.setLayout(new java.awt.GridBagLayout());

        org.openide.awt.Mnemonics.setLocalizedText(showNewLineOptionCheckBox, "show newLine options");
        showNewLineOptionCheckBox.setToolTipText("show newLine options at bottom of each editing pane");
        showNewLineOptionCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showNewLineOptionCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        nodeEditingOptionsPanel.add(showNewLineOptionCheckBox, gridBagConstraints);

        prependNewLineCheckBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(prependNewLineCheckBox, "prepend newLine");
        prependNewLineCheckBox.setToolTipText("prepend newLine before adding element to scene");
        prependNewLineCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                prependNewLineCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        nodeEditingOptionsPanel.add(prependNewLineCheckBox, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(appendNewLineCheckBox, "append newLine");
        appendNewLineCheckBox.setToolTipText("append newLine after adding element to scene");
        appendNewLineCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                appendNewLineCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        nodeEditingOptionsPanel.add(appendNewLineCheckBox, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(6, 3, 6, 3);
        nodeEditingOptionsPanel.add(jSeparator1, gridBagConstraints);

        autoValidationCheckBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(autoValidationCheckBox, "Validate scene after adding new node");
        autoValidationCheckBox.setToolTipText("automatically perform XML validation after adding new node, allows error undo");
        autoValidationCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                autoValidationCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        nodeEditingOptionsPanel.add(autoValidationCheckBox, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(horizontalSpacerLabel1, "   ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 30);
        nodeEditingOptionsPanel.add(horizontalSpacerLabel1, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(defaultEditorOptionsButton, "default");
        defaultEditorOptionsButton.setToolTipText("reset editor options to default settings");
        defaultEditorOptionsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                defaultEditorOptionsButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        nodeEditingOptionsPanel.add(defaultEditorOptionsButton, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dEditVisualizationPreferencesPanel.add(nodeEditingOptionsPanel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(verticalSpacerLabel3, "   ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        x3dEditVisualizationPreferencesPanel.add(verticalSpacerLabel3, gridBagConstraints);

        visualizationOptionsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Node visualization default values", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        visualizationOptionsPanel.setLayout(new java.awt.GridBagLayout());

        lineColorLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(lineColorLabel, "line color");
        lineColorLabel.setToolTipText("visualization line color, usually emissive");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        visualizationOptionsPanel.add(lineColorLabel, gridBagConstraints);

        lineColorRedTF.setToolTipText("lineColor red component");
        lineColorRedTF.setMinimumSize(new java.awt.Dimension(90, 25));
        lineColorRedTF.setPreferredSize(new java.awt.Dimension(90, 25));
        lineColorRedTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lineColorRedTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        visualizationOptionsPanel.add(lineColorRedTF, gridBagConstraints);

        lineColorGreenTF.setToolTipText("lineColor green component");
        lineColorGreenTF.setMinimumSize(new java.awt.Dimension(90, 25));
        lineColorGreenTF.setPreferredSize(new java.awt.Dimension(90, 25));
        lineColorGreenTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lineColorGreenTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        visualizationOptionsPanel.add(lineColorGreenTF, gridBagConstraints);

        lineColorBlueTF.setToolTipText("lineColor blue component");
        lineColorBlueTF.setMinimumSize(new java.awt.Dimension(90, 25));
        lineColorBlueTF.setPreferredSize(new java.awt.Dimension(90, 25));
        lineColorBlueTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lineColorBlueTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        visualizationOptionsPanel.add(lineColorBlueTF, gridBagConstraints);

        lineColorChooser.setMaximumSize(new java.awt.Dimension(22, 22));
        lineColorChooser.setMinimumSize(new java.awt.Dimension(22, 22));
        lineColorChooser.setPreferredSize(new java.awt.Dimension(22, 22));
        lineColorChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lineColorChooserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout lineColorChooserLayout = new javax.swing.GroupLayout(lineColorChooser);
        lineColorChooser.setLayout(lineColorChooserLayout);
        lineColorChooserLayout.setHorizontalGroup(
            lineColorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        lineColorChooserLayout.setVerticalGroup(
            lineColorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        visualizationOptionsPanel.add(lineColorChooser, gridBagConstraints);

        shapeColorLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(shapeColorLabel, "shape color");
        shapeColorLabel.setToolTipText("visualization shape color, usually diffuse");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        visualizationOptionsPanel.add(shapeColorLabel, gridBagConstraints);

        shapeColorRedTF.setToolTipText("shapeColor red component");
        shapeColorRedTF.setMinimumSize(new java.awt.Dimension(90, 25));
        shapeColorRedTF.setPreferredSize(new java.awt.Dimension(90, 25));
        shapeColorRedTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shapeColorRedTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        visualizationOptionsPanel.add(shapeColorRedTF, gridBagConstraints);

        shapeColorGreenTF.setToolTipText("shapeColor green component");
        shapeColorGreenTF.setMinimumSize(new java.awt.Dimension(90, 25));
        shapeColorGreenTF.setPreferredSize(new java.awt.Dimension(90, 25));
        shapeColorGreenTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shapeColorGreenTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        visualizationOptionsPanel.add(shapeColorGreenTF, gridBagConstraints);

        shapeColorBlueTF.setToolTipText("shapeColor blue component");
        shapeColorBlueTF.setMinimumSize(new java.awt.Dimension(90, 25));
        shapeColorBlueTF.setPreferredSize(new java.awt.Dimension(90, 25));
        shapeColorBlueTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shapeColorBlueTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        visualizationOptionsPanel.add(shapeColorBlueTF, gridBagConstraints);

        shapeColorChooser.setMaximumSize(new java.awt.Dimension(22, 22));
        shapeColorChooser.setMinimumSize(new java.awt.Dimension(22, 22));
        shapeColorChooser.setPreferredSize(new java.awt.Dimension(22, 22));
        shapeColorChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shapeColorChooserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout shapeColorChooserLayout = new javax.swing.GroupLayout(shapeColorChooser);
        shapeColorChooser.setLayout(shapeColorChooserLayout);
        shapeColorChooserLayout.setHorizontalGroup(
            shapeColorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        shapeColorChooserLayout.setVerticalGroup(
            shapeColorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        visualizationOptionsPanel.add(shapeColorChooser, gridBagConstraints);

        transparencyLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(transparencyLabel, "transparency");
        transparencyLabel.setToolTipText("[0,1] 0 is fully opaque, 1 is fully transparent");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 10, 3, 3);
        visualizationOptionsPanel.add(transparencyLabel, gridBagConstraints);

        transparencyTF.setToolTipText("[0,1] 0 is fully opaque, 1 is fully transparent");
        transparencyTF.setMaximumSize(null);
        transparencyTF.setMinimumSize(new java.awt.Dimension(90, 25));
        transparencyTF.setPreferredSize(new java.awt.Dimension(90, 25));
        transparencyTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transparencyTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        visualizationOptionsPanel.add(transparencyTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(defaultVisualizationSettingsButton, "default");
        defaultVisualizationSettingsButton.setToolTipText("reset editor options to default settings");
        defaultVisualizationSettingsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                defaultVisualizationSettingsButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        visualizationOptionsPanel.add(defaultVisualizationSettingsButton, gridBagConstraints);

        axesOriginLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(axesOriginLabel, "axes");
        axesOriginLabel.setToolTipText("include CoordinateAxes Inline to show local origin, direction and scale");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        visualizationOptionsPanel.add(axesOriginLabel, gridBagConstraints);

        coordinateAxesCheckBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(coordinateAxesCheckBox, "CoordinateAxes included to show local origin or Transform center");
        coordinateAxesCheckBox.setToolTipText("include unit CoordinateAxes Inline to show local origin, direction and scale");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        visualizationOptionsPanel.add(coordinateAxesCheckBox, gridBagConstraints);

        coneLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(coneLabel, "cone");
        coneLabel.setToolTipText("cone visualization parameters for SpotLight");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        visualizationOptionsPanel.add(coneLabel, gridBagConstraints);

        coneCenterLineCheckBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(coneCenterLineCheckBox, "center line");
        coneCenterLineCheckBox.setToolTipText("whether to include cone center line");
        coneCenterLineCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coneCenterLineCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        visualizationOptionsPanel.add(coneCenterLineCheckBox, gridBagConstraints);

        coneLinesLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(coneLinesLabel, "wireframe lines");
        coneLinesLabel.setToolTipText("number of lines along circumference sides");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        visualizationOptionsPanel.add(coneLinesLabel, gridBagConstraints);

        coneLinesComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "2", "3", "4", "6", "8", "12", "24" }));
        coneLinesComboBox.setToolTipText("number of lines along circumference sides");
        coneLinesComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                coneLinesComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        visualizationOptionsPanel.add(coneLinesComboBox, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dEditVisualizationPreferencesPanel.add(visualizationOptionsPanel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(verticalSpacerLabel4, "   ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        x3dEditVisualizationPreferencesPanel.add(verticalSpacerLabel4, gridBagConstraints);

        hAnimVisualizationOptionsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Humanoid Animation (H-Anim) visualization default values", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11))); // NOI18N
        hAnimVisualizationOptionsPanel.setLayout(new java.awt.GridBagLayout());

        hAnimJointColorLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(hAnimJointColorLabel, "Joint color");
        hAnimJointColorLabel.setToolTipText("visualization line color, usually emissive");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hAnimVisualizationOptionsPanel.add(hAnimJointColorLabel, gridBagConstraints);

        hAnimJointColorRedTF.setToolTipText("HAnimJoint Color red component");
        hAnimJointColorRedTF.setMinimumSize(new java.awt.Dimension(90, 25));
        hAnimJointColorRedTF.setPreferredSize(new java.awt.Dimension(90, 25));
        hAnimJointColorRedTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hAnimJointColorRedTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hAnimVisualizationOptionsPanel.add(hAnimJointColorRedTF, gridBagConstraints);

        hAnimJointColorGreenTF.setToolTipText("HAnimJoint Color green component");
        hAnimJointColorGreenTF.setMinimumSize(new java.awt.Dimension(90, 25));
        hAnimJointColorGreenTF.setPreferredSize(new java.awt.Dimension(90, 25));
        hAnimJointColorGreenTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hAnimJointColorGreenTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hAnimVisualizationOptionsPanel.add(hAnimJointColorGreenTF, gridBagConstraints);

        hAnimJointColorBlueTF.setToolTipText("HAnimJoint Color blue component");
        hAnimJointColorBlueTF.setMinimumSize(new java.awt.Dimension(90, 25));
        hAnimJointColorBlueTF.setPreferredSize(new java.awt.Dimension(90, 25));
        hAnimJointColorBlueTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hAnimJointColorBlueTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hAnimVisualizationOptionsPanel.add(hAnimJointColorBlueTF, gridBagConstraints);

        hAnimJointColorChooser.setMaximumSize(new java.awt.Dimension(22, 22));
        hAnimJointColorChooser.setMinimumSize(new java.awt.Dimension(22, 22));
        hAnimJointColorChooser.setPreferredSize(new java.awt.Dimension(22, 22));
        hAnimJointColorChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hAnimJointColorChooserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout hAnimJointColorChooserLayout = new javax.swing.GroupLayout(hAnimJointColorChooser);
        hAnimJointColorChooser.setLayout(hAnimJointColorChooserLayout);
        hAnimJointColorChooserLayout.setHorizontalGroup(
            hAnimJointColorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        hAnimJointColorChooserLayout.setVerticalGroup(
            hAnimJointColorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hAnimVisualizationOptionsPanel.add(hAnimJointColorChooser, gridBagConstraints);

        hAnimSegmentColorLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(hAnimSegmentColorLabel, "Segment color");
        hAnimSegmentColorLabel.setToolTipText("visualization shape color, usually diffuse");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hAnimVisualizationOptionsPanel.add(hAnimSegmentColorLabel, gridBagConstraints);

        hAnimSegmentColorRedTF.setToolTipText("HAnimSegment color red component");
        hAnimSegmentColorRedTF.setMinimumSize(new java.awt.Dimension(90, 25));
        hAnimSegmentColorRedTF.setPreferredSize(new java.awt.Dimension(90, 25));
        hAnimSegmentColorRedTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hAnimSegmentColorRedTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hAnimVisualizationOptionsPanel.add(hAnimSegmentColorRedTF, gridBagConstraints);

        hAnimSegmentColorGreenTF.setToolTipText("HAnimSegment color green component");
        hAnimSegmentColorGreenTF.setMinimumSize(new java.awt.Dimension(90, 25));
        hAnimSegmentColorGreenTF.setPreferredSize(new java.awt.Dimension(90, 25));
        hAnimSegmentColorGreenTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hAnimSegmentColorGreenTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hAnimVisualizationOptionsPanel.add(hAnimSegmentColorGreenTF, gridBagConstraints);

        hAnimSegmentColorBlueTF.setToolTipText("HAnimSegment color blue component");
        hAnimSegmentColorBlueTF.setMinimumSize(new java.awt.Dimension(90, 25));
        hAnimSegmentColorBlueTF.setPreferredSize(new java.awt.Dimension(90, 25));
        hAnimSegmentColorBlueTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hAnimSegmentColorBlueTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hAnimVisualizationOptionsPanel.add(hAnimSegmentColorBlueTF, gridBagConstraints);

        hAnimSegmentColorChooser.setMaximumSize(new java.awt.Dimension(22, 22));
        hAnimSegmentColorChooser.setMinimumSize(new java.awt.Dimension(22, 22));
        hAnimSegmentColorChooser.setPreferredSize(new java.awt.Dimension(22, 22));
        hAnimSegmentColorChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hAnimSegmentColorChooserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout hAnimSegmentColorChooserLayout = new javax.swing.GroupLayout(hAnimSegmentColorChooser);
        hAnimSegmentColorChooser.setLayout(hAnimSegmentColorChooserLayout);
        hAnimSegmentColorChooserLayout.setHorizontalGroup(
            hAnimSegmentColorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        hAnimSegmentColorChooserLayout.setVerticalGroup(
            hAnimSegmentColorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hAnimVisualizationOptionsPanel.add(hAnimSegmentColorChooser, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(hAnimDefaultVisualizationSettingsButton, "default");
        hAnimDefaultVisualizationSettingsButton.setToolTipText("reset editor options to default settings");
        hAnimDefaultVisualizationSettingsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hAnimDefaultVisualizationSettingsButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hAnimVisualizationOptionsPanel.add(hAnimDefaultVisualizationSettingsButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(hAnimVisualizeCoordinateAxesCheckBox, "CoordinateAxes included to show local origin");
        hAnimVisualizeCoordinateAxesCheckBox.setToolTipText("include unit CoordinateAxes Inline to show local origin, direction and scale");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hAnimVisualizationOptionsPanel.add(hAnimVisualizeCoordinateAxesCheckBox, gridBagConstraints);

        hAnimSiteColorLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(hAnimSiteColorLabel, "Site color");
        hAnimSiteColorLabel.setToolTipText("visualization shape color, usually diffuse");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hAnimVisualizationOptionsPanel.add(hAnimSiteColorLabel, gridBagConstraints);

        hAnimSiteColorRedTF.setToolTipText("HAnimSegment color red component");
        hAnimSiteColorRedTF.setMinimumSize(new java.awt.Dimension(90, 25));
        hAnimSiteColorRedTF.setPreferredSize(new java.awt.Dimension(90, 25));
        hAnimSiteColorRedTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hAnimSiteColorRedTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hAnimVisualizationOptionsPanel.add(hAnimSiteColorRedTF, gridBagConstraints);

        hAnimSiteColorGreenTF.setToolTipText("HAnimSegment color green component");
        hAnimSiteColorGreenTF.setMinimumSize(new java.awt.Dimension(90, 25));
        hAnimSiteColorGreenTF.setPreferredSize(new java.awt.Dimension(90, 25));
        hAnimSiteColorGreenTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hAnimSiteColorGreenTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hAnimVisualizationOptionsPanel.add(hAnimSiteColorGreenTF, gridBagConstraints);

        hAnimSiteColorBlueTF.setToolTipText("HAnimSegment color blue component");
        hAnimSiteColorBlueTF.setMinimumSize(new java.awt.Dimension(90, 25));
        hAnimSiteColorBlueTF.setPreferredSize(new java.awt.Dimension(90, 25));
        hAnimSiteColorBlueTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hAnimSiteColorBlueTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hAnimVisualizationOptionsPanel.add(hAnimSiteColorBlueTF, gridBagConstraints);

        hAnimSiteColorChooser.setMaximumSize(new java.awt.Dimension(22, 22));
        hAnimSiteColorChooser.setMinimumSize(new java.awt.Dimension(22, 22));
        hAnimSiteColorChooser.setPreferredSize(new java.awt.Dimension(22, 22));
        hAnimSiteColorChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hAnimSiteColorChooserActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout hAnimSiteColorChooserLayout = new javax.swing.GroupLayout(hAnimSiteColorChooser);
        hAnimSiteColorChooser.setLayout(hAnimSiteColorChooserLayout);
        hAnimSiteColorChooserLayout.setHorizontalGroup(
            hAnimSiteColorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );
        hAnimSiteColorChooserLayout.setVerticalGroup(
            hAnimSiteColorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 20, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hAnimVisualizationOptionsPanel.add(hAnimSiteColorChooser, gridBagConstraints);

        hAnimAxesOriginLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(hAnimAxesOriginLabel, "axes");
        hAnimAxesOriginLabel.setToolTipText("include CoordinateAxes Inline to show local origin, direction and scale");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        hAnimVisualizationOptionsPanel.add(hAnimAxesOriginLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dEditVisualizationPreferencesPanel.add(hAnimVisualizationOptionsPanel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(verticalSpacerLabel5, "   ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        x3dEditVisualizationPreferencesPanel.add(verticalSpacerLabel5, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(reportVisualizationPreferencesButton, "Report");
        reportVisualizationPreferencesButton.setToolTipText(BaseCustomizer.MAILTO_TOOLTIP);
        reportVisualizationPreferencesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportVisualizationPreferencesButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dEditVisualizationPreferencesPanel.add(reportVisualizationPreferencesButton, gridBagConstraints);

        x3dOptionsTabbedPane.addTab("X3D-Edit Visualization Settings", null, x3dEditVisualizationPreferencesPanel, "Additional editor preferences");

        xj3dCadFilterOptionsPanel.setToolTipText("CAD Filter configuration settings for geometry reduction");
        xj3dCadFilterOptionsPanel.setMinimumSize(new java.awt.Dimension(825, 600));
        xj3dCadFilterOptionsPanel.setPreferredSize(new java.awt.Dimension(825, 600));
        x3dOptionsTabbedPane.addTab(NbBundle.getMessage(getClass(), "Cad_Filter_Tab_Title"), xj3dCadFilterOptionsPanel); // NOI18N

        x3dSecurityPanel.setToolTipText("X3D-Edit settings for Security ");
        x3dSecurityPanel.setMinimumSize(new java.awt.Dimension(200, 300));
        x3dSecurityPanel.setPreferredSize(new java.awt.Dimension(0, 0));
        x3dSecurityPanel.setLayout(new java.awt.GridBagLayout());

        keystoreSectionHeaderLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        keystoreSectionHeaderLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        org.openide.awt.Mnemonics.setLocalizedText(keystoreSectionHeaderLabel, "X3D-Edit Keystore Manager Settings");
        keystoreSectionHeaderLabel.setToolTipText("A keystore holds encryption passwords and authentication certificates");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(20, 6, 1, 3);
        x3dSecurityPanel.add(keystoreSectionHeaderLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(keystoreManagerDescription1Label, "X3D-Edit includes a native keystore manager.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 3, 3);
        x3dSecurityPanel.add(keystoreManagerDescription1Label, gridBagConstraints);

        keystorePasswordLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(keystorePasswordLabel, "keystore password");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        x3dSecurityPanel.add(keystorePasswordLabel, gridBagConstraints);

        keystorePasswordTF.setFont(new java.awt.Font("Segoe UI", 3, 12)); // NOI18N
        keystorePasswordTF.setForeground(new java.awt.Color(255, 102, 0));
        keystorePasswordTF.setText(org.openide.util.NbBundle.getMessage(X3dEditUserPreferencesPanel.class, "KEYSTORE_DEFAULT_PASSWORD")); // NOI18N
        keystorePasswordTF.setToolTipText("default keystore password: test");
        keystorePasswordTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                keystorePasswordTFFocusLost(evt);
            }
        });
        keystorePasswordTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keystorePasswordTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dSecurityPanel.add(keystorePasswordTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(keystorePasswordTFClearButton, "x");
        keystorePasswordTFClearButton.setToolTipText("clear content text");
        keystorePasswordTFClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keystorePasswordTFClearButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        x3dSecurityPanel.add(keystorePasswordTFClearButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(keystorePasswordDefaultButton, "default");
        keystorePasswordDefaultButton.setToolTipText("Reset default file location for local keystore");
        keystorePasswordDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keystorePasswordDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dSecurityPanel.add(keystorePasswordDefaultButton, gridBagConstraints);

        keystoreFileNameLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(keystoreFileNameLabel, "keystore filename");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        x3dSecurityPanel.add(keystoreFileNameLabel, gridBagConstraints);

        keystoreFileNameTF.setText(org.web3d.x3d.options.X3dEditUserPreferences.KEYSTORE_FILENAME_DEFAULT);
        keystoreFileNameTF.setToolTipText("default keystore password: test");
        keystoreFileNameTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                keystoreFileNameTFFocusLost(evt);
            }
        });
        keystoreFileNameTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keystoreFileNameTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dSecurityPanel.add(keystoreFileNameTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(keystoreFileNameTFClearButton, "x");
        keystoreFileNameTFClearButton.setToolTipText("clear content text");
        keystoreFileNameTFClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keystoreFileNameTFClearButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        x3dSecurityPanel.add(keystoreFileNameTFClearButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(keystoreFileNameDefaultButton, "default");
        keystoreFileNameDefaultButton.setToolTipText("Reset default file location for local keystore");
        keystoreFileNameDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keystoreFileNameDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dSecurityPanel.add(keystoreFileNameDefaultButton, gridBagConstraints);

        keystorePathLabel1.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        keystorePathLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(keystorePathLabel1, "combined path:");
        keystorePathLabel1.setToolTipText("keystore directory + filename");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        x3dSecurityPanel.add(keystorePathLabel1, gridBagConstraints);

        keystorePathLabel2.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(keystorePathLabel2, "  ");
        keystorePathLabel2.setToolTipText("keystore directory + filename");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dSecurityPanel.add(keystorePathLabel2, gridBagConstraints);

        keystoreDirectoryLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(keystoreDirectoryLabel, "keystore directory");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        x3dSecurityPanel.add(keystoreDirectoryLabel, gridBagConstraints);

        keystoreDirectoryTF.setText(org.openide.util.NbBundle.getMessage(X3dEditUserPreferencesPanel.class, "KEYSTORE_DEFAULT_WARNING")); // NOI18N
        keystoreDirectoryTF.setToolTipText("File location for local keystore");
        keystoreDirectoryTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                keystoreDirectoryTFFocusLost(evt);
            }
        });
        keystoreDirectoryTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keystoreDirectoryTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 300;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dSecurityPanel.add(keystoreDirectoryTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(keystoreDirectoryTFClearButton, "x");
        keystoreDirectoryTFClearButton.setToolTipText("clear content text");
        keystoreDirectoryTFClearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keystoreDirectoryTFClearButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        x3dSecurityPanel.add(keystoreDirectoryTFClearButton, gridBagConstraints);

        keystoreDirectoryButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(keystoreDirectoryButton, "...");
        keystoreDirectoryButton.setToolTipText("Browse to select file location for local keystore");
        keystoreDirectoryButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keystoreDirectoryButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
        x3dSecurityPanel.add(keystoreDirectoryButton, gridBagConstraints);

        keystoreManageButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/resources/KeyWikimedia-60_Ŝlosilo_1.svg.32x32.png"))); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(keystoreManageButton, "<html> <p align='center'>Manage XML Security Keystore <br /> using X3D-Edit </p>");
        keystoreManageButton.setToolTipText("launch keystore manager");
        keystoreManageButton.setIconTextGap(10);
        keystoreManageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keystoreManageButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipadx = 120;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dSecurityPanel.add(keystoreManageButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(keystoreExplorerPlayerLaunchButton, "launch");
        keystoreExplorerPlayerLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        keystoreExplorerPlayerLaunchButton.setActionCommand("contactTF");
        keystoreExplorerPlayerLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keystoreExplorerPlayerLaunchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dSecurityPanel.add(keystoreExplorerPlayerLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(keystoreDefaultButton, "default");
        keystoreDefaultButton.setToolTipText("Reset default file location for local keystore");
        keystoreDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keystoreDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dSecurityPanel.add(keystoreDefaultButton, gridBagConstraints);

        securityExamplesLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        securityExamplesLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        org.openide.awt.Mnemonics.setLocalizedText(securityExamplesLabel, "X3D Canonicalization (C14N) and X3D Security Examples");
        securityExamplesLabel.setToolTipText("X3D Security Examples show how to apply XML Encryption and Authentication to X3D scenes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(20, 6, 6, 3);
        x3dSecurityPanel.add(securityExamplesLabel, gridBagConstraints);

        securityResourcesLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        securityResourcesLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        org.openide.awt.Mnemonics.setLocalizedText(securityResourcesLabel, "X3D Security Resources");
        securityResourcesLabel.setToolTipText("X3D Security Examples show how to apply XML Encryption and Authentication to X3D scenes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(20, 6, 6, 3);
        x3dSecurityPanel.add(securityResourcesLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(reportSecurityPanelButton, "Feedback");
        reportSecurityPanelButton.setToolTipText(BaseCustomizer.MAILTO_TOOLTIP);
        reportSecurityPanelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reportSecurityPanelButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dSecurityPanel.add(reportSecurityPanelButton, gridBagConstraints);

        additionalKeystoreManagersLabel.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        org.openide.awt.Mnemonics.setLocalizedText(additionalKeystoreManagersLabel, "Additional Keystore Managers");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(20, 6, 1, 3);
        x3dSecurityPanel.add(additionalKeystoreManagersLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(viewSecurityExamplesReadmeButton, "View");
        viewSecurityExamplesReadmeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewSecurityExamplesReadmeButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dSecurityPanel.add(viewSecurityExamplesReadmeButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(viewX3dResourcesSecurityButton, "View");
        viewX3dResourcesSecurityButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewX3dResourcesSecurityButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dSecurityPanel.add(viewX3dResourcesSecurityButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(viewX3dResourcesSecurityVulnerabilitiesButton, "View");
        viewX3dResourcesSecurityVulnerabilitiesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewX3dResourcesSecurityVulnerabilitiesButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dSecurityPanel.add(viewX3dResourcesSecurityVulnerabilitiesButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(viewSecurityExamplesReadmeLabel, "X3D Security Examples and README");
        viewSecurityExamplesReadmeLabel.setToolTipText("view X3D Security Examples and README");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dSecurityPanel.add(viewSecurityExamplesReadmeLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(viewX3dResourcesSecurityLabel, "X3D Resources: Security");
        viewX3dResourcesSecurityLabel.setToolTipText("view X3D Resources: Security");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dSecurityPanel.add(viewX3dResourcesSecurityLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(viewX3dResourcesSecurityVulnerabilitiesLabel, "X3D Resources: Security Vulnerabilities");
        viewX3dResourcesSecurityVulnerabilitiesLabel.setToolTipText("view X3D Resources: Security Vulnerabilities");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dSecurityPanel.add(viewX3dResourcesSecurityVulnerabilitiesLabel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(verticalSpacerLabel, "   ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weighty = 100.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dSecurityPanel.add(verticalSpacerLabel, gridBagConstraints);

        porteclePlayerLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(porteclePlayerLabel, "Portecle");
        porteclePlayerLabel.setToolTipText("Protege Ontology Editor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dSecurityPanel.add(porteclePlayerLabel, gridBagConstraints);

        porteclePlayerPathTF.setToolTipText("File location for local application");
        porteclePlayerPathTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                porteclePlayerPathTFFocusLost(evt);
            }
        });
        porteclePlayerPathTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                porteclePlayerPathTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 300;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dSecurityPanel.add(porteclePlayerPathTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(porteclePlayerChooserButton, "...");
        porteclePlayerChooserButton.setToolTipText("Browse to select file location for local keystore");
        porteclePlayerChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                porteclePlayerChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dSecurityPanel.add(porteclePlayerChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(porteclePlayerDefaultButton, "default");
        porteclePlayerDefaultButton.setToolTipText("Reset default file location for local application");
        porteclePlayerDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                porteclePlayerDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dSecurityPanel.add(porteclePlayerDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(porteclePlayerLaunchButton, "launch");
        porteclePlayerLaunchButton.setToolTipText(NbBundle.getMessage(getClass(), "Launch_Buttons_Tooltip")); // NOI18N
        porteclePlayerLaunchButton.setActionCommand("contactTF");
        porteclePlayerLaunchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                porteclePlayerLaunchButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dSecurityPanel.add(porteclePlayerLaunchButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(porteclePlayerDownloadButton, "get");
        porteclePlayerDownloadButton.setToolTipText("Download tool from website");
        porteclePlayerDownloadButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        porteclePlayerDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                porteclePlayerDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dSecurityPanel.add(porteclePlayerDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(porteclePlayerHelpButton, "help");
        porteclePlayerHelpButton.setToolTipText("Display tool help page");
        porteclePlayerHelpButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        porteclePlayerHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                porteclePlayerHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dSecurityPanel.add(porteclePlayerHelpButton, gridBagConstraints);

        keystoreExplorerPlayerLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        org.openide.awt.Mnemonics.setLocalizedText(keystoreExplorerPlayerLabel, "Keystore Explorer");
        keystoreExplorerPlayerLabel.setToolTipText("Protege Ontology Editor");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dSecurityPanel.add(keystoreExplorerPlayerLabel, gridBagConstraints);

        keystoreExplorerPlayerPathTF.setToolTipText("File location for local application");
        keystoreExplorerPlayerPathTF.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                keystoreExplorerPlayerPathTFFocusLost(evt);
            }
        });
        keystoreExplorerPlayerPathTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keystoreExplorerPlayerPathTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 300;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dSecurityPanel.add(keystoreExplorerPlayerPathTF, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(keystoreExplorerPlayerChooserButton, "...");
        keystoreExplorerPlayerChooserButton.setToolTipText("Browse to select file location for local keystore");
        keystoreExplorerPlayerChooserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keystoreExplorerPlayerChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dSecurityPanel.add(keystoreExplorerPlayerChooserButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(keystoreExplorerPlayerDefaultButton, "default");
        keystoreExplorerPlayerDefaultButton.setToolTipText("Reset default file location for local application");
        keystoreExplorerPlayerDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keystoreExplorerPlayerDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dSecurityPanel.add(keystoreExplorerPlayerDefaultButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(keystoreExplorerPlayerDownloadButton, "get");
        keystoreExplorerPlayerDownloadButton.setToolTipText("Download tool from website");
        keystoreExplorerPlayerDownloadButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        keystoreExplorerPlayerDownloadButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keystoreExplorerPlayerDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dSecurityPanel.add(keystoreExplorerPlayerDownloadButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(keystoreExplorerPlayerHelpButton, "help");
        keystoreExplorerPlayerHelpButton.setToolTipText("Display tool help page");
        keystoreExplorerPlayerHelpButton.setMargin(new java.awt.Insets(2, 3, 2, 3));
        keystoreExplorerPlayerHelpButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                keystoreExplorerPlayerHelpButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dSecurityPanel.add(keystoreExplorerPlayerHelpButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(keystoreManagerDescription2Label2, "Alternative keystore managers are available and optional.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 3, 3);
        x3dSecurityPanel.add(keystoreManagerDescription2Label2, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(viewX3dCanonicalizationC14nReadmeButton, "View");
        viewX3dCanonicalizationC14nReadmeButton.setToolTipText("");
        viewX3dCanonicalizationC14nReadmeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewX3dCanonicalizationC14nReadmeButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dSecurityPanel.add(viewX3dCanonicalizationC14nReadmeButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(viewX3dCanonicalizationC14nReadmeLabel, "X3D Canonicalization (C14N) to normalize file whitespace");
        viewX3dCanonicalizationC14nReadmeLabel.setToolTipText("view X3D Canonicalization (C14N)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        x3dSecurityPanel.add(viewX3dCanonicalizationC14nReadmeLabel, gridBagConstraints);

        x3dOptionsTabbedPane.addTab(NbBundle.getMessage(getClass(), "Security_Tab_Title"), x3dSecurityPanel); // NOI18N

        add(x3dOptionsTabbedPane, new java.awt.GridBagConstraints());
    }// </editor-fold>//GEN-END:initComponents
    private void freeWrlChooserButtonActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_freeWrlChooserButtonActionPerformed
    {//GEN-HEADEREND:event_freeWrlChooserButtonActionPerformed
      commonChooser(freeWrlTF, "Find FreeWrl Player Executable", evt);
}//GEN-LAST:event_freeWrlChooserButtonActionPerformed

  private void instantRealityChooserButtonActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_instantRealityChooserButtonActionPerformed
  {//GEN-HEADEREND:event_instantRealityChooserButtonActionPerformed
    commonChooser(instantRealityTF, "Find Intant Reality Executable", evt);
}//GEN-LAST:event_instantRealityChooserButtonActionPerformed

  private void freeWrlDefaultButtonActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_freeWrlDefaultButtonActionPerformed
  {//GEN-HEADEREND:event_freeWrlDefaultButtonActionPerformed
    freeWrlTF.setText(X3dEditUserPreferences.getFreeWrlPathDefault());
    X3dEditUserPreferences.setFreeWrlPath(freeWrlTF.getText().trim());
}//GEN-LAST:event_freeWrlDefaultButtonActionPerformed

  private void instantRealityDefaultButtonActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_instantRealityDefaultButtonActionPerformed
  {//GEN-HEADEREND:event_instantRealityDefaultButtonActionPerformed
    instantRealityTF.setText(X3dEditUserPreferences.getInstantRealityPathDefault());
    X3dEditUserPreferences.setInstantRealityPath(instantRealityTF.getText().trim());
}//GEN-LAST:event_instantRealityDefaultButtonActionPerformed

  private void octagaChooserButtonActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_octagaChooserButtonActionPerformed
  {//GEN-HEADEREND:event_octagaChooserButtonActionPerformed
    commonChooser(octagaTF, "Find Octaga Player Executable", evt);
}//GEN-LAST:event_octagaChooserButtonActionPerformed

  private void octagaDefaultButtonActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_octagaDefaultButtonActionPerformed
  {//GEN-HEADEREND:event_octagaDefaultButtonActionPerformed
    octagaTF.setText(X3dEditUserPreferences.getOctagaPathDefault());
    X3dEditUserPreferences.setOctagaPath(octagaTF.getText().trim());
}//GEN-LAST:event_octagaDefaultButtonActionPerformed

  private void xj3dChooserButtonActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_xj3dChooserButtonActionPerformed
  {//GEN-HEADEREND:event_xj3dChooserButtonActionPerformed
    commonChooser(xj3dTF, "Find Xj3D Player Executable", evt);
}//GEN-LAST:event_xj3dChooserButtonActionPerformed

  private void xj3dDefaultButtonActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_xj3dDefaultButtonActionPerformed
  {//GEN-HEADEREND:event_xj3dDefaultButtonActionPerformed
    xj3dTF.setText(X3dEditUserPreferences.getXj3DPathDefault());
    X3dEditUserPreferences.setXj3DPath(xj3dTF.getText().trim());
}//GEN-LAST:event_xj3dDefaultButtonActionPerformed

  private void contactDefaultButtonActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_contactDefaultButtonActionPerformed
  {//GEN-HEADEREND:event_contactDefaultButtonActionPerformed
    contactTF.setText(X3dEditUserPreferences.getContactPathDefault());
    X3dEditUserPreferences.setContactPath(contactTF.getText().trim());
}//GEN-LAST:event_contactDefaultButtonActionPerformed

  private void vivatyDefaultButtonActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_vivatyDefaultButtonActionPerformed
  {//GEN-HEADEREND:event_vivatyDefaultButtonActionPerformed
    vivatyTF.setText(X3dEditUserPreferences.getVivatyPlayerPathDefault());
    X3dEditUserPreferences.setVivatyPlayerPath(vivatyTF.getText().trim());
}//GEN-LAST:event_vivatyDefaultButtonActionPerformed

  private void vivatyChooserButtonActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_vivatyChooserButtonActionPerformed
  {//GEN-HEADEREND:event_vivatyChooserButtonActionPerformed
    commonChooser(vivatyTF, "Find VivatyPlayer Executable", evt);
}//GEN-LAST:event_vivatyChooserButtonActionPerformed

  private void contactDownloadButtonActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_contactDownloadButtonActionPerformed
  {//GEN-HEADEREND:event_contactDownloadButtonActionPerformed
    openInBrowser(X3dEditUserPreferences.getDownloadSiteContact());
}//GEN-LAST:event_contactDownloadButtonActionPerformed

  private void vivatyDownloadButtonActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_vivatyDownloadButtonActionPerformed
  {//GEN-HEADEREND:event_vivatyDownloadButtonActionPerformed
    openInBrowser(X3dEditUserPreferences.getDownloadSiteVivaty());
}//GEN-LAST:event_vivatyDownloadButtonActionPerformed

  private void freeWrlDownloadButtonActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_freeWrlDownloadButtonActionPerformed
  {//GEN-HEADEREND:event_freeWrlDownloadButtonActionPerformed
    openInBrowser(X3dEditUserPreferences.getDownloadSiteFreeWrl());
}//GEN-LAST:event_freeWrlDownloadButtonActionPerformed

  private void instantRealityDownloadButtonActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_instantRealityDownloadButtonActionPerformed
  {//GEN-HEADEREND:event_instantRealityDownloadButtonActionPerformed
    openInBrowser(X3dEditUserPreferences.getDownloadSiteInstantReality());
}//GEN-LAST:event_instantRealityDownloadButtonActionPerformed

  private void octagaDownloadButtonActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_octagaDownloadButtonActionPerformed
  {//GEN-HEADEREND:event_octagaDownloadButtonActionPerformed
    openInBrowser(X3dEditUserPreferences.getDownloadSiteOctaga());
}//GEN-LAST:event_octagaDownloadButtonActionPerformed

  private void xj3dDownloadButtonActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_xj3dDownloadButtonActionPerformed
  {//GEN-HEADEREND:event_xj3dDownloadButtonActionPerformed
    openInBrowser(X3dEditUserPreferences.getDownloadSiteXj3D());
}//GEN-LAST:event_xj3dDownloadButtonActionPerformed

  private void otherChooserButtonActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_otherChooserButtonActionPerformed
  {//GEN-HEADEREND:event_otherChooserButtonActionPerformed
    commonChooser(otherX3dPlayerPathTF, "Find other X3D executable", evt);
}//GEN-LAST:event_otherChooserButtonActionPerformed

  private void otherX3dPlayerClearButtonActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_otherX3dPlayerClearButtonActionPerformed
  {//GEN-HEADEREND:event_otherX3dPlayerClearButtonActionPerformed
    otherX3dPlayerNameTF.setText(X3dEditUserPreferences.getOtherX3dPlayerNameDefault());
    otherX3dPlayerPathTF.setText(X3dEditUserPreferences.getOtherX3dPlayerPathDefault());
}//GEN-LAST:event_otherX3dPlayerClearButtonActionPerformed

  private void otherX3dPlayerDownloadButtonActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_otherX3dPlayerDownloadButtonActionPerformed
  {//GEN-HEADEREND:event_otherX3dPlayerDownloadButtonActionPerformed
        openInBrowser(X3dEditUserPreferences.getDownloadSiteOtherX3dPlayer());
}//GEN-LAST:event_otherX3dPlayerDownloadButtonActionPerformed

private void keystoreDirectoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keystoreDirectoryButtonActionPerformed
    commonChooser(keystoreDirectoryTF, "Choose XML Keystore directory and file", evt);
    X3dEditUserPreferences.setKeystoreDirectory(keystoreDirectoryTF.getText().trim());
    setKeystoreFileName(keystoreFileNameTF.getText());
    keystorePathLabel2.setText(getKeystorePath());
    keystoreDirectoryCheck ();
    keystoreFilePathCheck();
}//GEN-LAST:event_keystoreDirectoryButtonActionPerformed

private void keystoreDefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keystoreDefaultButtonActionPerformed
    keystoreDirectoryTF.setText(X3dEditUserPreferences.getKeystorePathDefault().replace("\\", "/"));
    X3dEditUserPreferences.setKeystoreDirectory(keystoreDirectoryTF.getText().trim());
    keystorePathLabel2.setText(getKeystorePath());
    keystoreDirectoryCheck ();
    keystoreFilePathCheck();
}//GEN-LAST:event_keystoreDefaultButtonActionPerformed

private void swirlx3dChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_swirlx3dChooserButtonActionPerformed
  commonChooser(swirlx3dTF, "Find SwirlX3D Executable", evt);
}//GEN-LAST:event_swirlx3dChooserButtonActionPerformed

private void swirlx3dDefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_swirlx3dDefaultButtonActionPerformed
  swirlx3dTF.setText(X3dEditUserPreferences.getSwirlX3DPathDefault());
    X3dEditUserPreferences.setSwirlX3DPath(swirlx3dTF.getText().trim());
}//GEN-LAST:event_swirlx3dDefaultButtonActionPerformed

private void swirlx3dDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_swirlx3dDownloadButtonActionPerformed
    openInBrowser(X3dEditUserPreferences.getDownloadSiteSwirlX3D());
}//GEN-LAST:event_swirlx3dDownloadButtonActionPerformed

private void commonLauncher(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commonLauncher
  try {
    File tempDir = new File(System.getProperty("java.io.tmpdir"));
    File tempDirNorm = FileUtil.normalizeFile(tempDir);
    if (launcherExampleSceneFile == null) {
      FileObject jarredFileFO = FileUtil.getConfigRoot().getFileSystem().findResource(launcherExampleScenePath); //Repository.getDefault().getDefaultFileSystem().findResource(launcherExampleScenePath);

      FileObject tempDirFO = null;
      try {
        tempDirFO = FileUtil.createData(tempDirNorm);
      }
      catch (IOException e) {
        DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message("commonLauncher Exception:\n" + e.getMessage(),NotifyDescriptor.ERROR_MESSAGE));
      }
      String targetName = FileUtil.findFreeFileName(tempDirFO, "Launched-from-X3D-Edit-4.0", "x3d");
      FileUtil.copyFile(jarredFileFO, tempDirFO, targetName);

      launcherExampleSceneFile = new File(tempDir, new StringBuilder().append(targetName).append(".x3d").toString());
      launcherExampleSceneFile.deleteOnExit();
    }

    String[] execStringArray = CommandExecutionScripts.getCommandExecutionScript();

    String tfName = evt.getActionCommand();  // jtextfield field name
    Field tfField = getClass().getDeclaredField(tfName);  // Field object
    JTextField tf = (JTextField)tfField.get(this);        // field instance
    String path = tf.getText().trim();
    if(path.length() <= 0) {
      DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message("Empty path",NotifyDescriptor.ERROR_MESSAGE));
      return;
    }
    execStringArray[CommandExecutionScripts.getExecutablePathIndex()] = tf.getText().trim(); // path to launcher

    String targPath = launcherExampleSceneFile.getAbsolutePath();
    //if(escapeSpaces)
    //  targPath = targPath.replace(" ", "%20");  // Screws up some apps, just don't normalize and no spaces show up
    execStringArray[CommandExecutionScripts.getArgumentPathIndex()] = targPath;

    ProcessBuilder pb = new ProcessBuilder(execStringArray);
    pb.directory(tempDir);
    pb.start();
  }
  catch (IOException | IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException ex) {
    InputOutput io = IOProvider.getDefault().getIO("Output", false);
    io.select();
    io.getOut().println(new StringBuilder().append("Error launching external viewer: ").append(ex.getLocalizedMessage()).toString());
    //NbBundle.getMessage(getClass(), "MSG_DecryptOpComplete"));//"Decryption operation complete");
  }
}//GEN-LAST:event_commonLauncher

private void launchIntervalTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_launchIntervalTFActionPerformed
{//GEN-HEADEREND:event_launchIntervalTFActionPerformed
    // ensure legal value
    float newLaunchInterval = Float.parseFloat(evt.getActionCommand());
    if (newLaunchInterval < 0.0f)
    {
        X3dEditUserPreferences.resetLaunchInterval();
        System.err.println (new StringBuilder().append("newLaunchInterval=").append(newLaunchInterval).append("is illegal, resetting to default value ").append(X3dEditUserPreferences.getLaunchIntervalDefault()).toString());
    }
    // TODO set value?
}//GEN-LAST:event_launchIntervalTFActionPerformed

private void contactCheckBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_contactCheckBoxActionPerformed
{//GEN-HEADEREND:event_contactCheckBoxActionPerformed
    if (contactCheckBox.isSelected())
         X3dEditUserPreferences.setContactAutoLaunch("true");
    else X3dEditUserPreferences.setContactAutoLaunch("false");
}//GEN-LAST:event_contactCheckBoxActionPerformed

private void freeWrlCheckBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_freeWrlCheckBoxActionPerformed
{//GEN-HEADEREND:event_freeWrlCheckBoxActionPerformed
    if (freeWrlCheckBox.isSelected())
         X3dEditUserPreferences.setFreeWrlAutoLaunch("true");
    else X3dEditUserPreferences.setFreeWrlAutoLaunch("false");
}//GEN-LAST:event_freeWrlCheckBoxActionPerformed

private void instantRealityCheckBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_instantRealityCheckBoxActionPerformed
{//GEN-HEADEREND:event_instantRealityCheckBoxActionPerformed
    if (instantRealityCheckBox.isSelected())
         X3dEditUserPreferences.setInstantRealityAutoLaunch("true");
    else X3dEditUserPreferences.setInstantRealityAutoLaunch("false");
}//GEN-LAST:event_instantRealityCheckBoxActionPerformed

private void octagaCheckBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_octagaCheckBoxActionPerformed
{//GEN-HEADEREND:event_octagaCheckBoxActionPerformed
    if (octagaCheckBox.isSelected())
         X3dEditUserPreferences.setOctagaAutoLaunch("true");
    else X3dEditUserPreferences.setOctagaAutoLaunch("false");
}//GEN-LAST:event_octagaCheckBoxActionPerformed

private void vivatyCheckBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_vivatyCheckBoxActionPerformed
{//GEN-HEADEREND:event_vivatyCheckBoxActionPerformed
    if (vivatyCheckBox.isSelected())
         X3dEditUserPreferences.setVivatyAutoLaunch("true");
    else X3dEditUserPreferences.setVivatyAutoLaunch("false");
}//GEN-LAST:event_vivatyCheckBoxActionPerformed

private void swirlx3dCheckBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_swirlx3dCheckBoxActionPerformed
{//GEN-HEADEREND:event_swirlx3dCheckBoxActionPerformed
    if (swirlx3dCheckBox.isSelected())
         X3dEditUserPreferences.setSwirlx3dAutoLaunch("true");
    else X3dEditUserPreferences.setSwirlx3dAutoLaunch("false");
}//GEN-LAST:event_swirlx3dCheckBoxActionPerformed

private void xj3dCheckBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_xj3dCheckBoxActionPerformed
{//GEN-HEADEREND:event_xj3dCheckBoxActionPerformed
    if (xj3dCheckBox.isSelected())
         X3dEditUserPreferences.setXj3dAutoLaunch("true");
    else X3dEditUserPreferences.setXj3dAutoLaunch("false");
}//GEN-LAST:event_xj3dCheckBoxActionPerformed

private void otherX3dPlayerCheckBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_otherX3dPlayerCheckBoxActionPerformed
{//GEN-HEADEREND:event_otherX3dPlayerCheckBoxActionPerformed
    if (otherX3dPlayerCheckBox.isSelected())
         X3dEditUserPreferences.setOtherX3dPlayerAutoLaunch("true");
    else X3dEditUserPreferences.setOtherX3dPlayerAutoLaunch("false");
}//GEN-LAST:event_otherX3dPlayerCheckBoxActionPerformed

private void xj3dTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_xj3dTFActionPerformed
{//GEN-HEADEREND:event_xj3dTFActionPerformed
    X3dEditUserPreferences.setXj3DPath(xj3dTF.getText().trim());
    xj3dAutoLaunchCheck();
}//GEN-LAST:event_xj3dTFActionPerformed

private void vivatyTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_vivatyTFActionPerformed
{//GEN-HEADEREND:event_vivatyTFActionPerformed
    X3dEditUserPreferences.setVivatyPlayerPath(vivatyTF.getText().trim());
    vivatyAutoLaunchCheck();
}//GEN-LAST:event_vivatyTFActionPerformed

private void otherX3dPlayerNameTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_otherX3dPlayerNameTFActionPerformed
{//GEN-HEADEREND:event_otherX3dPlayerNameTFActionPerformed
    X3dEditUserPreferences.setOtherX3dPlayerName(otherX3dPlayerNameTF.getText().trim());
    otherX3dPlayerAutoLaunchCheck();
}//GEN-LAST:event_otherX3dPlayerNameTFActionPerformed

private void heilanChooserButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_heilanChooserButtonActionPerformed
{//GEN-HEADEREND:event_heilanChooserButtonActionPerformed
      commonChooser(heilanTF, "Find Heilan Player Executable", evt);
}//GEN-LAST:event_heilanChooserButtonActionPerformed

private void heilanDefaultButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_heilanDefaultButtonActionPerformed
{//GEN-HEADEREND:event_heilanDefaultButtonActionPerformed
    heilanTF.setText(X3dEditUserPreferences.getHeilanPathDefault());
    X3dEditUserPreferences.setHeilanPath(heilanTF.getText().trim());
}//GEN-LAST:event_heilanDefaultButtonActionPerformed

private void heilanDownloadButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_heilanDownloadButtonActionPerformed
{//GEN-HEADEREND:event_heilanDownloadButtonActionPerformed
    openInBrowser(X3dEditUserPreferences.getDownloadSiteHeilan());
}//GEN-LAST:event_heilanDownloadButtonActionPerformed

private void heilanCheckBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_heilanCheckBoxActionPerformed
{//GEN-HEADEREND:event_heilanCheckBoxActionPerformed
    if (heilanCheckBox.isSelected())
         X3dEditUserPreferences.setHeilanAutoLaunch("true");
    else X3dEditUserPreferences.setHeilanAutoLaunch("false");
}//GEN-LAST:event_heilanCheckBoxActionPerformed

private void showNewLineOptionCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showNewLineOptionCheckBoxActionPerformed

}//GEN-LAST:event_showNewLineOptionCheckBoxActionPerformed

private void prependNewLineCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_prependNewLineCheckBoxActionPerformed

}//GEN-LAST:event_prependNewLineCheckBoxActionPerformed

private void appendNewLineCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_appendNewLineCheckBoxActionPerformed

}//GEN-LAST:event_appendNewLineCheckBoxActionPerformed

private void defaultEditorOptionsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_defaultEditorOptionsButtonActionPerformed
    showNewLineOptionCheckBox.setSelected(X3dEditUserPreferences.SHOW_NEWLINE_OPTION_DEFAULT);
       prependNewLineCheckBox.setSelected(X3dEditUserPreferences.PREPEND_NEWLINE_DEFAULT);
        appendNewLineCheckBox.setSelected(X3dEditUserPreferences.APPEND_NEWLINE_DEFAULT);
       autoValidationCheckBox.setSelected(X3dEditUserPreferences.AUTOVALIDATE_DEFAULT);
}//GEN-LAST:event_defaultEditorOptionsButtonActionPerformed

private void autoValidationCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_autoValidationCheckBoxActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_autoValidationCheckBoxActionPerformed

private void lineColorChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lineColorChooserActionPerformed
    Color c = lineColorChooser.getColor();
      lineColorRedTF.setText(colorFormat.format((float)c.getRed()/255));
    lineColorGreenTF.setText(colorFormat.format((float)c.getGreen()/255));
     lineColorBlueTF.setText(colorFormat.format((float)c.getBlue()/255));
}//GEN-LAST:event_lineColorChooserActionPerformed

private void transparencyTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transparencyTFActionPerformed
    float newTransparency = Float.parseFloat(transparencyTF.getText().trim());
    if ((newTransparency >= 0.0f) && (newTransparency <= 1.0f))
    {
        X3dEditUserPreferences.setVisualizeTransparency(transparencyTF.getText().trim());
    }
    else
    {
        NotifyDescriptor notifyDescriptor = new NotifyDescriptor.Confirmation(
                "Illegal transparency value=" + transparencyTF.getText() + ", ignored", 
                "Illegal transparency value", NotifyDescriptor.PLAIN_MESSAGE);
        DialogDisplayer.getDefault().notify(notifyDescriptor);
    }
}//GEN-LAST:event_transparencyTFActionPerformed

private void lineColorGreenTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lineColorGreenTFActionPerformed
    lineColorChooser.setColor(
            (new SFColor(lineColorRedTF.getText(),
                         lineColorGreenTF.getText(),
                         lineColorBlueTF.getText())).getColor());
}//GEN-LAST:event_lineColorGreenTFActionPerformed

private void defaultVisualizationSettingsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_defaultVisualizationSettingsButtonActionPerformed
 coordinateAxesCheckBox.setSelected(X3dEditUserPreferences.VISUALIZE_COORDINATE_AXES_DEFAULT);
 coneCenterLineCheckBox.setSelected(X3dEditUserPreferences.VISUALIZE_CENTER_LINE_DEFAULT);
      coneLinesComboBox.setSelectedItem(X3dEditUserPreferences.VISUALIZE_CONE_LINES_DEFAULT);
           lineColorRedTF.setText(X3dEditUserPreferences.VISUALIZE_LINECOLOR_RED_DEFAULT);
           lineColorGreenTF.setText(X3dEditUserPreferences.VISUALIZE_LINECOLOR_GREEN_DEFAULT);
           lineColorBlueTF.setText(X3dEditUserPreferences.VISUALIZE_LINECOLOR_BLUE_DEFAULT);
       lineColorChooser.setColor(
            (new SFColor(lineColorRedTF.getText(),
                         lineColorGreenTF.getText(),
                         lineColorBlueTF.getText())).getColor());
          shapeColorRedTF.setText(X3dEditUserPreferences.VISUALIZE_SHAPECOLOR_RED_DEFAULT);
          shapeColorGreenTF.setText(X3dEditUserPreferences.VISUALIZE_SHAPECOLOR_GREEN_DEFAULT);
          shapeColorBlueTF.setText(X3dEditUserPreferences.VISUALIZE_SHAPECOLOR_BLUE_DEFAULT);
      shapeColorChooser.setColor(
            (new SFColor(shapeColorRedTF.getText(),
                         shapeColorGreenTF.getText(),
                         shapeColorBlueTF.getText())).getColor());
         transparencyTF.setText(X3dEditUserPreferences.VISUALIZE_TRANSPARENCY_DEFAULT);
}//GEN-LAST:event_defaultVisualizationSettingsButtonActionPerformed

private void lineColorRedTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lineColorRedTFActionPerformed
    lineColorChooser.setColor(
            (new SFColor(lineColorRedTF.getText(),
                         lineColorGreenTF.getText(),
                         lineColorBlueTF.getText())).getColor());
}//GEN-LAST:event_lineColorRedTFActionPerformed

private void lineColorBlueTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_lineColorBlueTFActionPerformed
    lineColorChooser.setColor(
            (new SFColor(lineColorRedTF.getText(),
                         lineColorGreenTF.getText(),
                         lineColorBlueTF.getText())).getColor());
}//GEN-LAST:event_lineColorBlueTFActionPerformed

private void shapeColorRedTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shapeColorRedTFActionPerformed
    shapeColorChooser.setColor(
            (new SFColor(shapeColorRedTF.getText(),
                         shapeColorGreenTF.getText(),
                         shapeColorBlueTF.getText())).getColor());
}//GEN-LAST:event_shapeColorRedTFActionPerformed

private void shapeColorGreenTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shapeColorGreenTFActionPerformed
    shapeColorChooser.setColor(
            (new SFColor(shapeColorRedTF.getText(),
                         shapeColorGreenTF.getText(),
                         shapeColorBlueTF.getText())).getColor());
}//GEN-LAST:event_shapeColorGreenTFActionPerformed

private void shapeColorBlueTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shapeColorBlueTFActionPerformed
    shapeColorChooser.setColor(
            (new SFColor(shapeColorRedTF.getText(),
                         shapeColorGreenTF.getText(),
                         shapeColorBlueTF.getText())).getColor());
}//GEN-LAST:event_shapeColorBlueTFActionPerformed

private void shapeColorChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shapeColorChooserActionPerformed
    Color c = shapeColorChooser.getColor();
      shapeColorRedTF.setText(colorFormat.format((float)c.getRed()/255));
    shapeColorGreenTF.setText(colorFormat.format((float)c.getGreen()/255));
     shapeColorBlueTF.setText(colorFormat.format((float)c.getBlue()/255));
}//GEN-LAST:event_shapeColorChooserActionPerformed

private void coneCenterLineCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coneCenterLineCheckBoxActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_coneCenterLineCheckBoxActionPerformed

private void contactGeoCheckBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_contactGeoCheckBoxActionPerformed
{//GEN-HEADEREND:event_contactGeoCheckBoxActionPerformed
    if (contactGeoCheckBox.isSelected())
         X3dEditUserPreferences.setContactGeoAutoLaunch("true");
    else X3dEditUserPreferences.setContactGeoAutoLaunch("false");
    contactGeoAutoLaunchCheck();
}//GEN-LAST:event_contactGeoCheckBoxActionPerformed

private void contactChooserButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_contactChooserButtonActionPerformed
{//GEN-HEADEREND:event_contactChooserButtonActionPerformed
    commonChooser(contactTF, "Find Contact Player Executable", evt);
    contactAutoLaunchCheck();
}//GEN-LAST:event_contactChooserButtonActionPerformed

private void contactGeoDefaultButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_contactGeoDefaultButtonActionPerformed
{//GEN-HEADEREND:event_contactGeoDefaultButtonActionPerformed
    contactGeoTF.setText(X3dEditUserPreferences.getContactGeoPathDefault());
    X3dEditUserPreferences.setContactGeoPath(contactGeoTF.getText().trim());
    contactGeoAutoLaunchCheck();
}//GEN-LAST:event_contactGeoDefaultButtonActionPerformed

private void contactGeoDownloadButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_contactGeoDownloadButtonActionPerformed
{//GEN-HEADEREND:event_contactGeoDownloadButtonActionPerformed
    openInBrowser(X3dEditUserPreferences.getDownloadSiteContactGeo());
}//GEN-LAST:event_contactGeoDownloadButtonActionPerformed

private void contactGeoChooserButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_contactGeoChooserButtonActionPerformed
{//GEN-HEADEREND:event_contactGeoChooserButtonActionPerformed
    commonChooser(contactGeoTF, "Find Contact Geo Player Executable", evt);
    contactGeoAutoLaunchCheck();
}//GEN-LAST:event_contactGeoChooserButtonActionPerformed

private void contactTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_contactTFActionPerformed
{//GEN-HEADEREND:event_contactTFActionPerformed
    X3dEditUserPreferences.setContactPath(contactTF.getText().trim());
    contactAutoLaunchCheck();
}//GEN-LAST:event_contactTFActionPerformed

    private void view3dsceneCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_view3dsceneCheckBoxActionPerformed
       if (view3dsceneCheckBox.isSelected())
            X3dEditUserPreferences.setView3dSceneAutoLaunch("true");
       else X3dEditUserPreferences.setView3dSceneAutoLaunch("false");
       view3dsceneAutoLaunchCheck();
    }//GEN-LAST:event_view3dsceneCheckBoxActionPerformed

    private void view3dsceneTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_view3dsceneTFActionPerformed
        X3dEditUserPreferences.setView3dScenePath(view3dsceneTF.getText().trim());
        view3dsceneAutoLaunchCheck();
    }//GEN-LAST:event_view3dsceneTFActionPerformed

    private void view3dsceneChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_view3dsceneChooserButtonActionPerformed
        commonChooser(view3dsceneTF, "Find view3dScene Executable", evt);
       view3dsceneAutoLaunchCheck();
    }//GEN-LAST:event_view3dsceneChooserButtonActionPerformed

    private void view3dsceneDefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_view3dsceneDefaultButtonActionPerformed
        view3dsceneTF.setText(X3dEditUserPreferences.getView3dScenePathDefault());
        X3dEditUserPreferences.setView3dScenePath(view3dsceneTF.getText().trim());
        view3dsceneAutoLaunchCheck();
    }//GEN-LAST:event_view3dsceneDefaultButtonActionPerformed

    private void view3dsceneDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_view3dsceneDownloadButtonActionPerformed
        openInBrowser(X3dEditUserPreferences.getDownloadSiteView3dScene());
    }//GEN-LAST:event_view3dsceneDownloadButtonActionPerformed

    private void h3dCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h3dCheckBoxActionPerformed
        if (h3dCheckBox.isSelected())
             X3dEditUserPreferences.setH3dAutoLaunch("true");
        else X3dEditUserPreferences.setH3dAutoLaunch("false");
        h3dAutoLaunchCheck();
    }//GEN-LAST:event_h3dCheckBoxActionPerformed

    private void h3dChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h3dChooserButtonActionPerformed
        commonChooser(h3dTF, "Find H3D Executable", evt);
        h3dAutoLaunchCheck();
    }//GEN-LAST:event_h3dChooserButtonActionPerformed

    private void h3dDefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h3dDefaultButtonActionPerformed
        h3dTF.setText(X3dEditUserPreferences.getH3dPathDefault());
        X3dEditUserPreferences.setH3dPath(h3dTF.getText().trim());
        h3dAutoLaunchCheck();
    }//GEN-LAST:event_h3dDefaultButtonActionPerformed

    private void h3dDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h3dDownloadButtonActionPerformed
        openInBrowser(X3dEditUserPreferences.getDownloadSiteH3d());
    }//GEN-LAST:event_h3dDownloadButtonActionPerformed

    private void h3dTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_h3dTFActionPerformed
        X3dEditUserPreferences.setH3dPath(h3dTF.getText().trim());
        h3dAutoLaunchCheck();
    }//GEN-LAST:event_h3dTFActionPerformed

    private void contactGeoTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_contactGeoTFActionPerformed
        X3dEditUserPreferences.setContactGeoPath(contactGeoTF.getText().trim());
        contactGeoAutoLaunchCheck();
    }//GEN-LAST:event_contactGeoTFActionPerformed

    private void freeWrlTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_freeWrlTFActionPerformed
        X3dEditUserPreferences.setFreeWrlPath(freeWrlTF.getText().trim());
        freeWrlAutoLaunchCheck();
    }//GEN-LAST:event_freeWrlTFActionPerformed

    private void heilanTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_heilanTFActionPerformed
        X3dEditUserPreferences.setHeilanPath(heilanTF.getText().trim());
        heilanAutoLaunchCheck();
    }//GEN-LAST:event_heilanTFActionPerformed

    private void instantRealityTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_instantRealityTFActionPerformed
        X3dEditUserPreferences.setInstantRealityPath(instantRealityTF.getText().trim());
        instantRealityAutoLaunchCheck();
    }//GEN-LAST:event_instantRealityTFActionPerformed

    private void octagaTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_octagaTFActionPerformed
        X3dEditUserPreferences.setOctagaPath(octagaTF.getText().trim());
        octagaAutoLaunchCheck();
    }//GEN-LAST:event_octagaTFActionPerformed

    private void swirlx3dTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_swirlx3dTFActionPerformed
        X3dEditUserPreferences.setSwirlX3DPath(swirlx3dTF.getText().trim());
        swirlx3dAutoLaunchCheck();
    }//GEN-LAST:event_swirlx3dTFActionPerformed

    private void otherX3dPlayerPathTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherX3dPlayerPathTFActionPerformed
        X3dEditUserPreferences.setOtherX3dPlayerPath(otherX3dPlayerPathTF.getText().trim());
        otherX3dPlayerAutoLaunchCheck();
    }//GEN-LAST:event_otherX3dPlayerPathTFActionPerformed

    private void hAnimJointColorRedTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hAnimJointColorRedTFActionPerformed
        hAnimJointColorChooser.setColor(
            (new SFColor(hAnimJointColorRedTF.getText(),
                         hAnimJointColorGreenTF.getText(),
                         hAnimJointColorBlueTF.getText())).getColor());
    }//GEN-LAST:event_hAnimJointColorRedTFActionPerformed

    private void hAnimJointColorGreenTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hAnimJointColorGreenTFActionPerformed
        hAnimJointColorChooser.setColor(
            (new SFColor(hAnimJointColorRedTF.getText(),
                         hAnimJointColorGreenTF.getText(),
                         hAnimJointColorBlueTF.getText())).getColor());
    }//GEN-LAST:event_hAnimJointColorGreenTFActionPerformed

    private void hAnimJointColorBlueTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hAnimJointColorBlueTFActionPerformed
        hAnimJointColorChooser.setColor(
            (new SFColor(hAnimJointColorRedTF.getText(),
                         hAnimJointColorGreenTF.getText(),
                         hAnimJointColorBlueTF.getText())).getColor());
    }//GEN-LAST:event_hAnimJointColorBlueTFActionPerformed

    private void hAnimJointColorChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hAnimJointColorChooserActionPerformed
    Color c = hAnimJointColorChooser.getColor();
      hAnimJointColorRedTF.setText(colorFormat.format((float)c.getRed()/255));
    hAnimJointColorGreenTF.setText(colorFormat.format((float)c.getGreen()/255));
     hAnimJointColorBlueTF.setText(colorFormat.format((float)c.getBlue()/255));
    }//GEN-LAST:event_hAnimJointColorChooserActionPerformed

    private void hAnimSegmentColorRedTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hAnimSegmentColorRedTFActionPerformed
        hAnimSegmentColorChooser.setColor(
            (new SFColor(hAnimSegmentColorRedTF.getText(),
                         hAnimSegmentColorGreenTF.getText(),
                         hAnimSegmentColorBlueTF.getText())).getColor());
    }//GEN-LAST:event_hAnimSegmentColorRedTFActionPerformed

    private void hAnimSegmentColorGreenTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hAnimSegmentColorGreenTFActionPerformed
        hAnimSegmentColorChooser.setColor(
            (new SFColor(hAnimSegmentColorRedTF.getText(),
                         hAnimSegmentColorGreenTF.getText(),
                         hAnimSegmentColorBlueTF.getText())).getColor());
    }//GEN-LAST:event_hAnimSegmentColorGreenTFActionPerformed

    private void hAnimSegmentColorBlueTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hAnimSegmentColorBlueTFActionPerformed
        hAnimSegmentColorChooser.setColor(
            (new SFColor(hAnimSegmentColorRedTF.getText(),
                         hAnimSegmentColorGreenTF.getText(),
                         hAnimSegmentColorBlueTF.getText())).getColor());
    }//GEN-LAST:event_hAnimSegmentColorBlueTFActionPerformed

    private void hAnimSegmentColorChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hAnimSegmentColorChooserActionPerformed
    Color c = hAnimSegmentColorChooser.getColor();
      hAnimSegmentColorRedTF.setText(colorFormat.format((float)c.getRed()/255));
    hAnimSegmentColorGreenTF.setText(colorFormat.format((float)c.getGreen()/255));
     hAnimSegmentColorBlueTF.setText(colorFormat.format((float)c.getBlue()/255));
    }//GEN-LAST:event_hAnimSegmentColorChooserActionPerformed

    private void hAnimDefaultVisualizationSettingsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hAnimDefaultVisualizationSettingsButtonActionPerformed
        initializeHAnimVisualizationDefaultValues ();
    }//GEN-LAST:event_hAnimDefaultVisualizationSettingsButtonActionPerformed

    private void hAnimSiteColorRedTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hAnimSiteColorRedTFActionPerformed
        hAnimSiteColorChooser.setColor(
            (new SFColor(hAnimSiteColorRedTF.getText(),
                         hAnimSiteColorGreenTF.getText(),
                         hAnimSiteColorBlueTF.getText())).getColor());
    }//GEN-LAST:event_hAnimSiteColorRedTFActionPerformed

    private void hAnimSiteColorGreenTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hAnimSiteColorGreenTFActionPerformed
        hAnimSiteColorChooser.setColor(
            (new SFColor(hAnimSiteColorRedTF.getText(),
                         hAnimSiteColorGreenTF.getText(),
                         hAnimSiteColorBlueTF.getText())).getColor());
    }//GEN-LAST:event_hAnimSiteColorGreenTFActionPerformed

    private void hAnimSiteColorBlueTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hAnimSiteColorBlueTFActionPerformed
        hAnimSiteColorChooser.setColor(
            (new SFColor(hAnimSiteColorRedTF.getText(),
                         hAnimSiteColorGreenTF.getText(),
                         hAnimSiteColorBlueTF.getText())).getColor());
    }//GEN-LAST:event_hAnimSiteColorBlueTFActionPerformed

    private void hAnimSiteColorChooserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hAnimSiteColorChooserActionPerformed
    Color c = hAnimSiteColorChooser.getColor();
      hAnimSiteColorRedTF.setText(colorFormat.format((float)c.getRed()/255));
    hAnimSiteColorGreenTF.setText(colorFormat.format((float)c.getGreen()/255));
     hAnimSiteColorBlueTF.setText(colorFormat.format((float)c.getBlue()/255));
    }//GEN-LAST:event_hAnimSiteColorChooserActionPerformed

  private void audacityEditorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_audacityEditorChooserButtonActionPerformed
    commonChooser(audacityEditorPathTF, "Find Audacity Audio Recording Editor", evt);
    audacityAutoLaunchCheck ();
  }//GEN-LAST:event_audacityEditorChooserButtonActionPerformed

  private void audacityEditorDefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_audacityEditorDefaultButtonActionPerformed
    audacityEditorPathTF.setText(X3dEditUserPreferences.getAudacityEditorPathDefault());
    X3dEditUserPreferences.setAudacityEditorPath(audacityEditorPathTF.getText().trim());
    audacityAutoLaunchCheck ();
  }//GEN-LAST:event_audacityEditorDefaultButtonActionPerformed

  private void audacityEditorDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_audacityEditorDownloadButtonActionPerformed
    openInBrowser(X3dEditUserPreferences.getDownloadSiteAudacity());
  }//GEN-LAST:event_audacityEditorDownloadButtonActionPerformed

  private void imageJEditorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imageJEditorChooserButtonActionPerformed
    commonChooser(imageJEditorTF, "Find ImageJ Editor", evt);
    imageJAutoLaunchCheck ();
  }//GEN-LAST:event_imageJEditorChooserButtonActionPerformed

  private void imageJEditorDefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imageJEditorDefaultButtonActionPerformed
    imageJEditorTF.setText(X3dEditUserPreferences.getImageJEditorPathDefault());
    X3dEditUserPreferences.setImageJEditorPath(imageJEditorTF.getText().trim());
    imageJAutoLaunchCheck ();
  }//GEN-LAST:event_imageJEditorDefaultButtonActionPerformed

  private void imageJEditorDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imageJEditorDownloadButtonActionPerformed
    openInBrowser(X3dEditUserPreferences.getDownloadSiteImageJ());
  }//GEN-LAST:event_imageJEditorDownloadButtonActionPerformed

  private void ultraEditX3dEditorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ultraEditX3dEditorChooserButtonActionPerformed
    commonChooser(ultraEditX3dEditorPathTF, "Find UltraEdit Text Editor", evt);
    ultraEditAutoLaunchCheck ();
  }//GEN-LAST:event_ultraEditX3dEditorChooserButtonActionPerformed

  private void ultraEditX3dEditorDefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ultraEditX3dEditorDefaultButtonActionPerformed
    ultraEditX3dEditorPathTF.setText(X3dEditUserPreferences.getUltraEditX3dEditorPathDefault());
    X3dEditUserPreferences.setUltraEditX3dEditorPath(ultraEditX3dEditorPathTF.getText().trim());
    ultraEditAutoLaunchCheck ();
  }//GEN-LAST:event_ultraEditX3dEditorDefaultButtonActionPerformed

  private void ultraEditX3dEditorDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ultraEditX3dEditorDownloadButtonActionPerformed
        openInBrowser(X3dEditUserPreferences.getDownloadSiteUltraEdit());
        ultraEditAutoLaunchCheck();
  }//GEN-LAST:event_ultraEditX3dEditorDownloadButtonActionPerformed

  private void audacityEditorLaunchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_audacityEditorLaunchButtonActionPerformed
        externalProcessLaunch(X3dEditUserPreferences.getAudacityEditorPath());
        audacityAutoLaunchCheck();
  }//GEN-LAST:event_audacityEditorLaunchButtonActionPerformed

  private void imageJEditorLaunchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imageJEditorLaunchButtonActionPerformed
        externalProcessLaunch(X3dEditUserPreferences.getImageJEditorPath());
        imageJAutoLaunchCheck();
  }//GEN-LAST:event_imageJEditorLaunchButtonActionPerformed

  private void ultraEditX3dEditorLaunchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ultraEditX3dEditorLaunchButtonActionPerformed
        externalProcessLaunch(X3dEditUserPreferences.getUltraEditX3dEditorPath());
        ultraEditAutoLaunchCheck();
  }//GEN-LAST:event_ultraEditX3dEditorLaunchButtonActionPerformed

  private void audacityEditorCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_audacityEditorCheckBoxActionPerformed
        if (audacityEditorCheckBox.isSelected())
        {
          X3dEditUserPreferences.setAudacityAutoLaunch("true");
        }
        else
        {
          X3dEditUserPreferences.setAudacityAutoLaunch("false");
        }
        audacityAutoLaunchCheck();
  }//GEN-LAST:event_audacityEditorCheckBoxActionPerformed

  private void gimpEditorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gimpEditorChooserButtonActionPerformed
    commonChooser(gimpEditorTF, "Find GNU Image Manipulation Program (GIMP)", evt);
    gimpAutoLaunchCheck ();
  }//GEN-LAST:event_gimpEditorChooserButtonActionPerformed

  private void gimpEditorDefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gimpEditorDefaultButtonActionPerformed
    gimpEditorTF.setText(X3dEditUserPreferences.getGimpImageEditorPathDefault());
    X3dEditUserPreferences.setGimpImageEditorPath(gimpEditorTF.getText().trim());
    gimpAutoLaunchCheck ();
  }//GEN-LAST:event_gimpEditorDefaultButtonActionPerformed

  private void gimpEditorLaunchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gimpEditorLaunchButtonActionPerformed
    externalProcessLaunch(X3dEditUserPreferences.getGimpImageEditorPath());
    gimpAutoLaunchCheck ();
  }//GEN-LAST:event_gimpEditorLaunchButtonActionPerformed

  private void gimpEditorDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gimpEditorDownloadButtonActionPerformed
    openInBrowser(X3dEditUserPreferences.getDownloadSiteGimp());
  }//GEN-LAST:event_gimpEditorDownloadButtonActionPerformed

  private void fijiEditorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fijiEditorChooserButtonActionPerformed
    commonChooser(fijiEditorTF, "Find Fiji ImageJ Editor", evt);
    fijiAutoLaunchCheck ();
  }//GEN-LAST:event_fijiEditorChooserButtonActionPerformed

  private void fijiEditorDefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fijiEditorDefaultButtonActionPerformed
    fijiEditorTF.setText(X3dEditUserPreferences.getFijiImageEditorPathDefault());
    X3dEditUserPreferences.setFijiEditorPath(fijiEditorTF.getText().trim());
    fijiAutoLaunchCheck ();
  }//GEN-LAST:event_fijiEditorDefaultButtonActionPerformed

  private void fijiEditorLaunchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fijiEditorLaunchButtonActionPerformed
    externalProcessLaunch(X3dEditUserPreferences.getFijiImageEditorPath());
    fijiAutoLaunchCheck ();
  }//GEN-LAST:event_fijiEditorLaunchButtonActionPerformed

  private void fijiEditorDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fijiEditorDownloadButtonActionPerformed
    openInBrowser(X3dEditUserPreferences.getDownloadSiteFiji());
  }//GEN-LAST:event_fijiEditorDownloadButtonActionPerformed

  private void ultraEditX3dEditorPathTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ultraEditX3dEditorPathTFActionPerformed
    X3dEditUserPreferences.setUltraEditX3dEditorPath(ultraEditX3dEditorPathTF.getText().trim());
    ultraEditAutoLaunchCheck ();
  }//GEN-LAST:event_ultraEditX3dEditorPathTFActionPerformed

  private void gimpEditorTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gimpEditorTFActionPerformed
        X3dEditUserPreferences.setGimpImageEditorPath(gimpEditorTF.getText().trim());
        gimpAutoLaunchCheck ();
  }//GEN-LAST:event_gimpEditorTFActionPerformed

  private void audacityEditorPathTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_audacityEditorPathTFActionPerformed
        X3dEditUserPreferences.setAudacityEditorPath(audacityEditorPathTF.getText().trim());
        audacityAutoLaunchCheck ();
  }//GEN-LAST:event_audacityEditorPathTFActionPerformed

  private void fijiEditorTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fijiEditorTFActionPerformed
        X3dEditUserPreferences.setFijiEditorPath(fijiEditorTF.getText().trim());
        fijiAutoLaunchCheck ();
  }//GEN-LAST:event_fijiEditorTFActionPerformed

  private void imageJEditorTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imageJEditorTFActionPerformed
        X3dEditUserPreferences.setImageJEditorPath(imageJEditorTF.getText().trim());
        imageJAutoLaunchCheck ();
  }//GEN-LAST:event_imageJEditorTFActionPerformed

  private void gimpCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gimpCheckBoxActionPerformed
    if (gimpCheckBox.isSelected())
    {
      X3dEditUserPreferences.setGimpAutoLaunch("true");
    }
    else
    {
      X3dEditUserPreferences.setGimpAutoLaunch("false");
    }
    gimpAutoLaunchCheck ();
  }//GEN-LAST:event_gimpCheckBoxActionPerformed

  private void fijiCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fijiCheckBoxActionPerformed
    if (fijiCheckBox.isSelected())
    {
      X3dEditUserPreferences.setFijiAutoLaunch("true");
    }
    else
    {
      X3dEditUserPreferences.setFijiAutoLaunch("false");
    }
    fijiAutoLaunchCheck ();
  }//GEN-LAST:event_fijiCheckBoxActionPerformed

  private void imageJCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imageJCheckBoxActionPerformed
    if (imageJCheckBox.isSelected())
    {
      X3dEditUserPreferences.setImageJAutoLaunch("true");
    }
    else
    {
      X3dEditUserPreferences.setImageJAutoLaunch("false");
    }
    imageJAutoLaunchCheck ();
  }//GEN-LAST:event_imageJCheckBoxActionPerformed

  private void ultraEditX3dEditorCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ultraEditX3dEditorCheckBoxActionPerformed
    if (ultraEditX3dEditorCheckBox.isSelected())
    {
      X3dEditUserPreferences.setUltraEditAutoLaunch("true");
    }
    else
    {
      X3dEditUserPreferences.setUltraEditAutoLaunch("false");
    }
    ultraEditAutoLaunchCheck ();
  }//GEN-LAST:event_ultraEditX3dEditorCheckBoxActionPerformed

  private void gimpHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gimpHelpButtonActionPerformed
    browserLaunch(X3dEditUserPreferences.helpSiteGimp);
  }//GEN-LAST:event_gimpHelpButtonActionPerformed

  private void audacityEditorHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_audacityEditorHelpButtonActionPerformed
    browserLaunch(X3dEditUserPreferences.helpSiteAudacity);
  }//GEN-LAST:event_audacityEditorHelpButtonActionPerformed

  private void fijiHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fijiHelpButtonActionPerformed
    browserLaunch(X3dEditUserPreferences.helpSiteFiji);
  }//GEN-LAST:event_fijiHelpButtonActionPerformed

  private void imageJHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imageJHelpButtonActionPerformed
    browserLaunch(X3dEditUserPreferences.helpSiteImageJ);
  }//GEN-LAST:event_imageJHelpButtonActionPerformed

  private void ultraEditHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ultraEditHelpButtonActionPerformed
    browserLaunch(X3dEditUserPreferences.helpSiteUltraEdit);
  }//GEN-LAST:event_ultraEditHelpButtonActionPerformed

  private void imageMagickEditorTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imageMagickEditorTFActionPerformed
        X3dEditUserPreferences.setImageMagickEditorPath(imageMagickEditorTF.getText().trim());
        imageMagickAutoLaunchCheck ();
  }//GEN-LAST:event_imageMagickEditorTFActionPerformed

  private void imageMagickCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imageMagickCheckBoxActionPerformed
    if (imageMagickCheckBox.isSelected())
    {
      X3dEditUserPreferences.setImageMagickAutoLaunch("true");
    }
    else
    {
      X3dEditUserPreferences.setImageMagickAutoLaunch("false");
    }
    imageMagickAutoLaunchCheck ();
  }//GEN-LAST:event_imageMagickCheckBoxActionPerformed

  private void imageMagickEditorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imageMagickEditorChooserButtonActionPerformed
    commonChooser(imageMagickEditorTF, "Find ImageMagick imageconverter", evt);
    X3dEditUserPreferences.setImageMagickEditorPath(imageMagickEditorTF.getText().trim());
    imageMagickAutoLaunchCheck ();
  }//GEN-LAST:event_imageMagickEditorChooserButtonActionPerformed

  private void imageMagickEditorDefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imageMagickEditorDefaultButtonActionPerformed
    imageMagickEditorTF.setText(X3dEditUserPreferences.getImageMagickEditorPathDefault());
    X3dEditUserPreferences.setImageMagickEditorPath(imageMagickEditorTF.getText().trim());
    imageMagickAutoLaunchCheck ();
  }//GEN-LAST:event_imageMagickEditorDefaultButtonActionPerformed

  private void imageMagickEditorLaunchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imageMagickEditorLaunchButtonActionPerformed
    externalProcessLaunch(X3dEditUserPreferences.getImageMagickEditorPath());
    imageMagickAutoLaunchCheck ();
  }//GEN-LAST:event_imageMagickEditorLaunchButtonActionPerformed

  private void imageMagickEditorDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imageMagickEditorDownloadButtonActionPerformed
    openInBrowser(X3dEditUserPreferences.getDownloadSiteImageMagick());
  }//GEN-LAST:event_imageMagickEditorDownloadButtonActionPerformed

  private void imageMagickHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imageMagickHelpButtonActionPerformed
    browserLaunch(X3dEditUserPreferences.helpSiteImageMagick);
  }//GEN-LAST:event_imageMagickHelpButtonActionPerformed

  private void otherX3dEditorCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherX3dEditorCheckBoxActionPerformed
    if (otherX3dEditorCheckBox.isSelected())
    {
      X3dEditUserPreferences.setOtherX3dEditorAutoLaunch("true");
    }
    else
    {
      X3dEditUserPreferences.setOtherX3dEditorAutoLaunch("false");
    }
    otherX3dEditorAutoLaunchCheck();
  }//GEN-LAST:event_otherX3dEditorCheckBoxActionPerformed

  private void otherX3dEditorPathTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherX3dEditorPathTFActionPerformed
        X3dEditUserPreferences.setOtherX3dEditorPath(otherX3dEditorPathTF.getText().trim());
        otherX3dEditorAutoLaunchCheck();
  }//GEN-LAST:event_otherX3dEditorPathTFActionPerformed

  private void otherX3dEditorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherX3dEditorChooserButtonActionPerformed
    commonChooser(otherX3dEditorPathTF, "Find other X3D Graphics authoring tool", evt);
    X3dEditUserPreferences.setOtherX3dEditorPath(otherX3dEditorPathTF.getText().trim());
    otherX3dEditorAutoLaunchCheck();
  }//GEN-LAST:event_otherX3dEditorChooserButtonActionPerformed

  private void otherX3dEditorClearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherX3dEditorClearButtonActionPerformed
    X3dEditUserPreferences.resetOtherX3dEditorName();
    X3dEditUserPreferences.resetOtherX3dEditorPath();
    otherX3dEditorNameTF.setText( X3dEditUserPreferences.getOtherX3dEditorNameDefault());
    otherX3dEditorPathTF.setText("");
    otherX3dEditorAutoLaunchCheck();
  }//GEN-LAST:event_otherX3dEditorClearButtonActionPerformed

  private void otherX3dEditorLaunchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherX3dEditorLaunchButtonActionPerformed
    externalProcessLaunch(X3dEditUserPreferences.getOtherX3dEditorPath());
  }//GEN-LAST:event_otherX3dEditorLaunchButtonActionPerformed

  private void otherX3dEditorNameTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherX3dEditorNameTFActionPerformed
        X3dEditUserPreferences.setOtherX3dEditorName(otherX3dEditorNameTF.getText().trim());
        otherX3dEditorAutoLaunchCheck();
  }//GEN-LAST:event_otherX3dEditorNameTFActionPerformed

  private void blenderX3dEditorCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_blenderX3dEditorCheckBoxActionPerformed
    if (blenderX3dEditorCheckBox.isSelected())
    {
      X3dEditUserPreferences.setBlenderAutoLaunch("true");
    }
    else
    {
      X3dEditUserPreferences.setBlenderAutoLaunch("false");
    }
    blenderAutoLaunchCheck();
  }//GEN-LAST:event_blenderX3dEditorCheckBoxActionPerformed

  private void blenderX3dEditorPathTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_blenderX3dEditorPathTFActionPerformed
    X3dEditUserPreferences.setBlenderX3dEditorPath(blenderX3dEditorPathTF.getText().trim());
    blenderAutoLaunchCheck ();
  }//GEN-LAST:event_blenderX3dEditorPathTFActionPerformed

  private void blenderX3dEditorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_blenderX3dEditorChooserButtonActionPerformed
    commonChooser(blenderX3dEditorPathTF, "Find Blender 3D graphics authoring tool", evt);
    blenderAutoLaunchCheck ();
  }//GEN-LAST:event_blenderX3dEditorChooserButtonActionPerformed

  private void blenderX3dEditorDefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_blenderX3dEditorDefaultButtonActionPerformed
    blenderX3dEditorPathTF.setText(X3dEditUserPreferences.getBlenderX3dEditorPathDefault());
    X3dEditUserPreferences.setBlenderX3dEditorPath(blenderX3dEditorPathTF.getText().trim());
    blenderAutoLaunchCheck ();
  }//GEN-LAST:event_blenderX3dEditorDefaultButtonActionPerformed

  private void blenderX3dEditorLaunchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_blenderX3dEditorLaunchButtonActionPerformed
    externalProcessLaunch(X3dEditUserPreferences.getBlenderX3dEditorPath());
    blenderAutoLaunchCheck();
  }//GEN-LAST:event_blenderX3dEditorLaunchButtonActionPerformed

  private void blenderX3dEditorDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_blenderX3dEditorDownloadButtonActionPerformed
    openInBrowser(X3dEditUserPreferences.getDownloadSiteBlender());
  }//GEN-LAST:event_blenderX3dEditorDownloadButtonActionPerformed

  private void blenderX3dEditorHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_blenderX3dEditorHelpButtonActionPerformed
    browserLaunch(X3dEditUserPreferences.helpSiteBlender);
  }//GEN-LAST:event_blenderX3dEditorHelpButtonActionPerformed

  private void otherX3dEditorDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherX3dEditorDownloadButtonActionPerformed
    browserLaunch(X3dEditUserPreferences.getDownloadSiteOtherX3dEditor());
  }//GEN-LAST:event_otherX3dEditorDownloadButtonActionPerformed

  private void otherImageEditorNameTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherImageEditorNameTFActionPerformed
    X3dEditUserPreferences.setOtherImageEditorName(otherImageEditorNameTF.getText().trim());
    otherImageEditorAutoLaunchCheck();
  }//GEN-LAST:event_otherImageEditorNameTFActionPerformed

  private void otherImageEditorCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherImageEditorCheckBoxActionPerformed
    if (otherImageEditorCheckBox.isSelected())
    {
      X3dEditUserPreferences.setOtherImageEditorAutoLaunch("true");
    }
    else
    {
      X3dEditUserPreferences.setOtherImageEditorAutoLaunch("false");
    }
    otherImageEditorAutoLaunchCheck();
  }//GEN-LAST:event_otherImageEditorCheckBoxActionPerformed

  private void otherImageEditorPathTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherImageEditorPathTFActionPerformed
        X3dEditUserPreferences.setOtherImageEditorPath(otherImageEditorPathTF.getText().trim());
        otherImageEditorAutoLaunchCheck ();
  }//GEN-LAST:event_otherImageEditorPathTFActionPerformed

  private void otherImageEditorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherImageEditorChooserButtonActionPerformed
    commonChooser(otherImageEditorPathTF, "Find other image editor", evt);
    X3dEditUserPreferences.setOtherImageEditorPath(otherImageEditorPathTF.getText().trim());
    otherImageEditorAutoLaunchCheck ();
  }//GEN-LAST:event_otherImageEditorChooserButtonActionPerformed

  private void otherImageEditorClearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherImageEditorClearButtonActionPerformed
    X3dEditUserPreferences.resetOtherImageEditorPath();
    X3dEditUserPreferences.resetOtherImageEditorName();
    otherImageEditorPathTF.setText("");
    otherImageEditorNameTF.setText( X3dEditUserPreferences.getOtherImageEditorNameDefault());
    otherImageEditorAutoLaunchCheck ();
  }//GEN-LAST:event_otherImageEditorClearButtonActionPerformed

  private void otherImageEditorLaunchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherImageEditorLaunchButtonActionPerformed
    externalProcessLaunch(X3dEditUserPreferences.getOtherImageEditorPath());
    otherImageEditorAutoLaunchCheck();
  }//GEN-LAST:event_otherImageEditorLaunchButtonActionPerformed

  private void otherImageEditorDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherImageEditorDownloadButtonActionPerformed
    browserLaunch(X3dEditUserPreferences.getDownloadSiteOtherImageEditor());
  }//GEN-LAST:event_otherImageEditorDownloadButtonActionPerformed

  private void otherAudioEditorNameTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherAudioEditorNameTFActionPerformed
    X3dEditUserPreferences.setOtherAudioEditorName(otherAudioEditorNameTF.getText().trim());
    otherAudioEditorAutoLaunchCheck();
  }//GEN-LAST:event_otherAudioEditorNameTFActionPerformed

  private void otherAudioEditorCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherAudioEditorCheckBoxActionPerformed
    if (otherAudioEditorCheckBox.isSelected())
    {
      X3dEditUserPreferences.setOtherAudioEditorAutoLaunch("true");
    }
    else
    {
      X3dEditUserPreferences.setOtherAudioEditorAutoLaunch("false");
    }
    otherAudioEditorAutoLaunchCheck();
  }//GEN-LAST:event_otherAudioEditorCheckBoxActionPerformed

  private void otherAudioEditorPathTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherAudioEditorPathTFActionPerformed
    X3dEditUserPreferences.setOtherAudioEditorPath(otherAudioEditorPathTF.getText().trim());
    otherAudioEditorAutoLaunchCheck();
  }//GEN-LAST:event_otherAudioEditorPathTFActionPerformed

  private void otherAudioEditorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherAudioEditorChooserButtonActionPerformed
    commonChooser(otherAudioEditorPathTF, "Find other audio editor", evt);
    X3dEditUserPreferences.setOtherAudioEditorPath(otherAudioEditorPathTF.getText().trim());
    otherAudioEditorAutoLaunchCheck();
  }//GEN-LAST:event_otherAudioEditorChooserButtonActionPerformed

  private void otherAudioEditorClearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherAudioEditorClearButtonActionPerformed
    X3dEditUserPreferences.resetOtherAudioEditorPath();
    X3dEditUserPreferences.resetOtherAudioEditorName();
    otherAudioEditorPathTF.setText("");
    otherAudioEditorNameTF.setText( X3dEditUserPreferences.getOtherAudioEditorNameDefault());
    otherAudioEditorAutoLaunchCheck();
  }//GEN-LAST:event_otherAudioEditorClearButtonActionPerformed

  private void otherAudioEditorLaunchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherAudioEditorLaunchButtonActionPerformed
    externalProcessLaunch(X3dEditUserPreferences.getOtherAudioEditorPath());
    otherAudioEditorAutoLaunchCheck();
  }//GEN-LAST:event_otherAudioEditorLaunchButtonActionPerformed

  private void otherAudioEditorDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherAudioEditorDownloadButtonActionPerformed
    browserLaunch(X3dEditUserPreferences.getDownloadSiteOtherAudioEditor());
  }//GEN-LAST:event_otherAudioEditorDownloadButtonActionPerformed

  private void otherVideoEditorNameTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherVideoEditorNameTFActionPerformed
    X3dEditUserPreferences.setOtherVideoEditorName(otherVideoEditorNameTF.getText().trim());
    otherVideoEditorAutoLaunchCheck();
  }//GEN-LAST:event_otherVideoEditorNameTFActionPerformed

  private void otherVideoEditorCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherVideoEditorCheckBoxActionPerformed
    if (otherVideoEditorCheckBox.isSelected())
    {
      X3dEditUserPreferences.setOtherVideoEditorAutoLaunch("true");
    }
    else
    {
      X3dEditUserPreferences.setOtherVideoEditorAutoLaunch("false");
    }
    otherVideoEditorAutoLaunchCheck();
  }//GEN-LAST:event_otherVideoEditorCheckBoxActionPerformed

  private void otherVideoEditorPathTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherVideoEditorPathTFActionPerformed
        X3dEditUserPreferences.setOtherVideoEditorPath(otherVideoEditorPathTF.getText().trim());
        otherVideoEditorAutoLaunchCheck();
  }//GEN-LAST:event_otherVideoEditorPathTFActionPerformed

  private void otherVideoChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherVideoChooserButtonActionPerformed
    commonChooser(otherVideoEditorPathTF, "Find other video editor", evt);
    X3dEditUserPreferences.setOtherVideoEditorPath(otherVideoEditorPathTF.getText().trim());
    otherVideoEditorAutoLaunchCheck ();
  }//GEN-LAST:event_otherVideoChooserButtonActionPerformed

  private void otherVideoEditorClearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherVideoEditorClearButtonActionPerformed
    X3dEditUserPreferences.resetOtherVideoEditorPath();
    X3dEditUserPreferences.resetOtherVideoEditorName();
    otherVideoEditorPathTF.setText("");
    otherVideoEditorNameTF.setText( X3dEditUserPreferences.getOtherVideoEditorNameDefault());
    otherVideoEditorAutoLaunchCheck ();
  }//GEN-LAST:event_otherVideoEditorClearButtonActionPerformed

  private void otherVideoEditorLaunchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherVideoEditorLaunchButtonActionPerformed
    externalProcessLaunch(X3dEditUserPreferences.getOtherVideoEditorPath());
    otherVideoEditorAutoLaunchCheck();
  }//GEN-LAST:event_otherVideoEditorLaunchButtonActionPerformed

  private void otherVideoEditorDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherVideoEditorDownloadButtonActionPerformed
    browserLaunch(X3dEditUserPreferences.getDownloadSiteOtherVideoEditor());
  }//GEN-LAST:event_otherVideoEditorDownloadButtonActionPerformed

  private void vlcPlayerCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vlcPlayerCheckBoxActionPerformed
    if (vlcPlayerCheckBox.isSelected())
    {
      X3dEditUserPreferences.setVlcAutoLaunch("true");
    }
    else
    {
      X3dEditUserPreferences.setVlcAutoLaunch("false");
    }
    vlcAutoLaunchCheck ();
  }//GEN-LAST:event_vlcPlayerCheckBoxActionPerformed

  private void vlcPlayerPathTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vlcPlayerPathTFActionPerformed
        X3dEditUserPreferences.setVlcPlayerPath(vlcPlayerPathTF.getText().trim());
        vlcAutoLaunchCheck ();
  }//GEN-LAST:event_vlcPlayerPathTFActionPerformed

  private void vlcPlayerChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vlcPlayerChooserButtonActionPerformed
    commonChooser(vlcPlayerPathTF, "Find vlc video player", evt);
    X3dEditUserPreferences.setVlcPlayerPath(vlcPlayerPathTF.getText().trim());
    vlcAutoLaunchCheck ();
  }//GEN-LAST:event_vlcPlayerChooserButtonActionPerformed

  private void vlcPlayerDefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vlcPlayerDefaultButtonActionPerformed
    vlcPlayerPathTF.setText(X3dEditUserPreferences.getVlcPlayerPathDefault());
    X3dEditUserPreferences.setVlcPlayerPath(vlcPlayerPathTF.getText().trim());
    vlcAutoLaunchCheck ();
  }//GEN-LAST:event_vlcPlayerDefaultButtonActionPerformed

  private void vlcPlayerLaunchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vlcPlayerLaunchButtonActionPerformed
    externalProcessLaunch(X3dEditUserPreferences.getVlcPlayerPath());
    vlcAutoLaunchCheck ();
  }//GEN-LAST:event_vlcPlayerLaunchButtonActionPerformed

  private void vlcPlayerDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vlcPlayerDownloadButtonActionPerformed
    browserLaunch(X3dEditUserPreferences.getDownloadSiteVlc());
  }//GEN-LAST:event_vlcPlayerDownloadButtonActionPerformed

  private void vlcPlayerHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vlcPlayerHelpButtonActionPerformed
    browserLaunch(X3dEditUserPreferences.helpSiteVlc);
  }//GEN-LAST:event_vlcPlayerHelpButtonActionPerformed

  private void meshLabX3dEditorCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_meshLabX3dEditorCheckBoxActionPerformed
    if (meshLabX3dEditorCheckBox.isSelected())
    {
      X3dEditUserPreferences.setMeshLabAutoLaunch("true");
    }
    else
    {
      X3dEditUserPreferences.setMeshLabAutoLaunch("false");
    }
    meshLabAutoLaunchCheck ();
  }//GEN-LAST:event_meshLabX3dEditorCheckBoxActionPerformed

  private void meshLabX3dEditorPathTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_meshLabX3dEditorPathTFActionPerformed
        X3dEditUserPreferences.setMeshLabX3dEditorPath(meshLabX3dEditorPathTF.getText().trim());
        meshLabAutoLaunchCheck ();
  }//GEN-LAST:event_meshLabX3dEditorPathTFActionPerformed

  private void meshLabX3dEditorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_meshLabX3dEditorChooserButtonActionPerformed
    commonChooser(meshLabX3dEditorPathTF, "Find MeshLab 3D graphics authoring tool", evt);
    X3dEditUserPreferences.setMeshLabX3dEditorPath(meshLabX3dEditorPathTF.getText().trim());
    meshLabAutoLaunchCheck ();
  }//GEN-LAST:event_meshLabX3dEditorChooserButtonActionPerformed

  private void meshLabX3dEditorDefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_meshLabX3dEditorDefaultButtonActionPerformed
        meshLabX3dEditorPathTF.setText(X3dEditUserPreferences.getMeshLabX3dEditorPathDefault());
        X3dEditUserPreferences.setMeshLabX3dEditorPath(meshLabX3dEditorPathTF.getText().trim());
        meshLabAutoLaunchCheck ();
  }//GEN-LAST:event_meshLabX3dEditorDefaultButtonActionPerformed

  private void meshLabX3dEditorLaunchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_meshLabX3dEditorLaunchButtonActionPerformed
    externalProcessLaunch(X3dEditUserPreferences.getMeshLabX3dEditorPath());
    meshLabAutoLaunchCheck ();
  }//GEN-LAST:event_meshLabX3dEditorLaunchButtonActionPerformed

  private void meshLabX3dEditorDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_meshLabX3dEditorDownloadButtonActionPerformed
        browserLaunch(X3dEditUserPreferences.getDownloadSiteMeshLab());
  }//GEN-LAST:event_meshLabX3dEditorDownloadButtonActionPerformed

  private void meshLabX3dEditorHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_meshLabX3dEditorHelpButtonActionPerformed
    browserLaunch(X3dEditUserPreferences.helpSiteMeshLab);
  }//GEN-LAST:event_meshLabX3dEditorHelpButtonActionPerformed

  private void seamless3dX3dEditorCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seamless3dX3dEditorCheckBoxActionPerformed
    if (seamless3dX3dEditorCheckBox.isSelected())
    {
      X3dEditUserPreferences.setSeamless3dAutoLaunch("true");
    }
    else
    {
      X3dEditUserPreferences.setSeamless3dAutoLaunch("false");
    }
    seamless3dAutoLaunchCheck ();
  }//GEN-LAST:event_seamless3dX3dEditorCheckBoxActionPerformed

  private void seamless3dX3dEditorPathTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seamless3dX3dEditorPathTFActionPerformed
        X3dEditUserPreferences.setSeamlessX3dEditorPath(seamless3dX3dEditorPathTF.getText().trim());
        seamless3dAutoLaunchCheck ();
  }//GEN-LAST:event_seamless3dX3dEditorPathTFActionPerformed

  private void seamless3dX3dEditorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seamless3dX3dEditorChooserButtonActionPerformed
    commonChooser(seamless3dX3dEditorPathTF, "Find Seamless3d graphics authoring tool", evt);
    X3dEditUserPreferences.setSeamlessX3dEditorPath(seamless3dX3dEditorPathTF.getText().trim());
    seamless3dAutoLaunchCheck ();
  }//GEN-LAST:event_seamless3dX3dEditorChooserButtonActionPerformed

  private void seamless3dX3dEditorDefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seamless3dX3dEditorDefaultButtonActionPerformed
    seamless3dX3dEditorPathTF.setText(X3dEditUserPreferences.getSeamlessX3dEditorPathDefault());
    X3dEditUserPreferences.setSeamlessX3dEditorPath(seamless3dX3dEditorPathTF.getText().trim());
    seamless3dAutoLaunchCheck ();
  }//GEN-LAST:event_seamless3dX3dEditorDefaultButtonActionPerformed

  private void seamless3dX3dEditorLaunchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seamless3dX3dEditorLaunchButtonActionPerformed
    externalProcessLaunch(X3dEditUserPreferences.getSeamlessX3dEditorPath());
    seamless3dAutoLaunchCheck ();
  }//GEN-LAST:event_seamless3dX3dEditorLaunchButtonActionPerformed

  private void seamless3dX3dEditorDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seamless3dX3dEditorDownloadButtonActionPerformed
    browserLaunch(X3dEditUserPreferences.getDownloadSiteSeamless3d());
  }//GEN-LAST:event_seamless3dX3dEditorDownloadButtonActionPerformed

  private void seamless3dX3dEditorHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seamless3dX3dEditorHelpButtonActionPerformed
    browserLaunch(X3dEditUserPreferences.helpSiteSeamless3d);
  }//GEN-LAST:event_seamless3dX3dEditorHelpButtonActionPerformed

  private void wings3dX3dEditorCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wings3dX3dEditorCheckBoxActionPerformed
    if (wings3dX3dEditorCheckBox.isSelected())
    {
      X3dEditUserPreferences.setWings3dAutoLaunch("true");
    }
    else
    {
      X3dEditUserPreferences.setWings3dAutoLaunch("false");
    }
    wings3dAutoLaunchCheck ();
  }//GEN-LAST:event_wings3dX3dEditorCheckBoxActionPerformed

  private void wings3dX3dEditorPathTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wings3dX3dEditorPathTFActionPerformed
        X3dEditUserPreferences.setWingsX3dEditorPath(wings3dX3dEditorPathTF.getText().trim());
        wings3dAutoLaunchCheck ();
  }//GEN-LAST:event_wings3dX3dEditorPathTFActionPerformed

  private void wings3dX3dEditorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wings3dX3dEditorChooserButtonActionPerformed
    commonChooser(wings3dX3dEditorPathTF, "Find Wings 3D graphics authoring tool", evt);
    X3dEditUserPreferences.setWingsX3dEditorPath(wings3dX3dEditorPathTF.getText().trim());
    wings3dAutoLaunchCheck ();
  }//GEN-LAST:event_wings3dX3dEditorChooserButtonActionPerformed

  private void wings3dX3dEditorDefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wings3dX3dEditorDefaultButtonActionPerformed
    wings3dX3dEditorPathTF.setText(X3dEditUserPreferences.getWingsX3dEditorPathDefault());
    X3dEditUserPreferences.setWingsX3dEditorPath(wings3dX3dEditorPathTF.getText().trim());
    wings3dAutoLaunchCheck ();
  }//GEN-LAST:event_wings3dX3dEditorDefaultButtonActionPerformed

  private void wings3dX3dEditorLaunchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wings3dX3dEditorLaunchButtonActionPerformed
    externalProcessLaunch(X3dEditUserPreferences.getWingsX3dEditorPath());
    wings3dAutoLaunchCheck ();
  }//GEN-LAST:event_wings3dX3dEditorLaunchButtonActionPerformed

  private void wings3dX3dEditorDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wings3dX3dEditorDownloadButtonActionPerformed
    browserLaunch(X3dEditUserPreferences.getDownloadSiteWings3d());
  }//GEN-LAST:event_wings3dX3dEditorDownloadButtonActionPerformed

  private void wings3dX3dEditorHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_wings3dX3dEditorHelpButtonActionPerformed
    browserLaunch(X3dEditUserPreferences.helpSiteWings3d);
  }//GEN-LAST:event_wings3dX3dEditorHelpButtonActionPerformed

  private void paraviewX3dEditorCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paraviewX3dEditorCheckBoxActionPerformed
    if (paraviewX3dEditorCheckBox.isSelected())
    {
      X3dEditUserPreferences.setParaviewAutoLaunch("true");
    }
    else
    {
      X3dEditUserPreferences.setParaviewAutoLaunch("false");
    }
    paraviewAutoLaunchCheck ();
  }//GEN-LAST:event_paraviewX3dEditorCheckBoxActionPerformed

  private void paraviewX3dEditorPathTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paraviewX3dEditorPathTFActionPerformed
        X3dEditUserPreferences.setParaviewX3dEditorPath(paraviewX3dEditorPathTF.getText().trim());
        paraviewAutoLaunchCheck ();
  }//GEN-LAST:event_paraviewX3dEditorPathTFActionPerformed

  private void paraviewX3dEditorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paraviewX3dEditorChooserButtonActionPerformed
    commonChooser(paraviewX3dEditorPathTF, "Find Paraview graphics authoring tool", evt);
    X3dEditUserPreferences.setParaviewX3dEditorPath(paraviewX3dEditorPathTF.getText().trim());
    paraviewAutoLaunchCheck ();
  }//GEN-LAST:event_paraviewX3dEditorChooserButtonActionPerformed

  private void paraviewX3dEditorDefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paraviewX3dEditorDefaultButtonActionPerformed
    paraviewX3dEditorPathTF.setText(X3dEditUserPreferences.getParaviewX3dEditorPathDefault());
    X3dEditUserPreferences.setParaviewX3dEditorPath(paraviewX3dEditorPathTF.getText().trim());
    paraviewAutoLaunchCheck ();
  }//GEN-LAST:event_paraviewX3dEditorDefaultButtonActionPerformed

  private void paraviewX3dEditorLaunchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paraviewX3dEditorLaunchButtonActionPerformed
    externalProcessLaunch(X3dEditUserPreferences.getParaviewX3dEditorPath());
    paraviewAutoLaunchCheck ();
  }//GEN-LAST:event_paraviewX3dEditorLaunchButtonActionPerformed

  private void paraviewX3dEditorDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paraviewX3dEditorDownloadButtonActionPerformed
    browserLaunch(X3dEditUserPreferences.getDownloadSiteParaview());
  }//GEN-LAST:event_paraviewX3dEditorDownloadButtonActionPerformed

  private void paraviewX3dEditorHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_paraviewX3dEditorHelpButtonActionPerformed
    browserLaunch(X3dEditUserPreferences.helpSiteParaview);
  }//GEN-LAST:event_paraviewX3dEditorHelpButtonActionPerformed

  private void amayaEditorPathTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amayaEditorPathTFActionPerformed
        X3dEditUserPreferences.setAmayaEditorPath(amayaEditorPathTF.getText().trim());
        amayaAutoLaunchCheck ();
  }//GEN-LAST:event_amayaEditorPathTFActionPerformed

  private void amayaEditorCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amayaEditorCheckBoxActionPerformed
    if (amayaEditorCheckBox.isSelected())
    {
      X3dEditUserPreferences.setAmayaAutoLaunch("true");
    }
    else
    {
      X3dEditUserPreferences.setAmayaAutoLaunch("false");
    }
    amayaAutoLaunchCheck ();
  }//GEN-LAST:event_amayaEditorCheckBoxActionPerformed

  private void amayaEditorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amayaEditorChooserButtonActionPerformed
    commonChooser(amayaEditorPathTF, "Find Amaya HTML5 authoring tool", evt);
    X3dEditUserPreferences.setAmayaEditorPath(amayaEditorPathTF.getText().trim());
    amayaAutoLaunchCheck ();
  }//GEN-LAST:event_amayaEditorChooserButtonActionPerformed

  private void amayaEditorDefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amayaEditorDefaultButtonActionPerformed
    amayaEditorPathTF.setText(X3dEditUserPreferences.getAmayaEditorPathDefault());
    X3dEditUserPreferences.setAmayaEditorPath(amayaEditorPathTF.getText().trim());
    amayaAutoLaunchCheck ();
  }//GEN-LAST:event_amayaEditorDefaultButtonActionPerformed

  private void amayaEditorLaunchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amayaEditorLaunchButtonActionPerformed
    externalProcessLaunch(X3dEditUserPreferences.getAmayaEditorPath());
    amayaAutoLaunchCheck ();
  }//GEN-LAST:event_amayaEditorLaunchButtonActionPerformed

  private void amayaEditorDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amayaEditorDownloadButtonActionPerformed
    browserLaunch(X3dEditUserPreferences.getDownloadSiteAmaya());
  }//GEN-LAST:event_amayaEditorDownloadButtonActionPerformed

  private void amayaEditorHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_amayaEditorHelpButtonActionPerformed
    browserLaunch(X3dEditUserPreferences.helpSiteAmaya);
  }//GEN-LAST:event_amayaEditorHelpButtonActionPerformed

  private void otherHtml5EditorNameTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherHtml5EditorNameTFActionPerformed
    X3dEditUserPreferences.setOtherHtml5EditorName(otherHtml5EditorNameTF.getText().trim());
    otherHtml5EditorAutoLaunchCheck();
  }//GEN-LAST:event_otherHtml5EditorNameTFActionPerformed

  private void otherHtml5EditorCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherHtml5EditorCheckBoxActionPerformed
    if (otherHtml5EditorCheckBox.isSelected())
    {
      X3dEditUserPreferences.setOtherHtml5EditorAutoLaunch("true");
    }
    else
    {
      X3dEditUserPreferences.setOtherHtml5EditorAutoLaunch("false");
    }
    otherHtml5EditorAutoLaunchCheck();
  }//GEN-LAST:event_otherHtml5EditorCheckBoxActionPerformed

  private void otherHtml5EditorPathTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherHtml5EditorPathTFActionPerformed
        X3dEditUserPreferences.setOtherHtml5EditorPath(otherHtml5EditorPathTF.getText().trim());
        otherHtml5EditorAutoLaunchCheck();
  }//GEN-LAST:event_otherHtml5EditorPathTFActionPerformed

  private void otherHtml5EditorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherHtml5EditorChooserButtonActionPerformed
    commonChooser(otherHtml5EditorPathTF, "Find other HTML5 editor", evt);
    X3dEditUserPreferences.setOtherHtml5EditorPath(otherHtml5EditorPathTF.getText().trim());
    otherHtml5EditorAutoLaunchCheck ();
  }//GEN-LAST:event_otherHtml5EditorChooserButtonActionPerformed

  private void otherHtml5EditorClearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherHtml5EditorClearButtonActionPerformed
    X3dEditUserPreferences.resetOtherHtml5EditorPath();
    X3dEditUserPreferences.resetOtherHtml5EditorName();
    otherHtml5EditorPathTF.setText("");
    otherHtml5EditorNameTF.setText( X3dEditUserPreferences.getOtherHtml5EditorNameDefault());
    otherHtml5EditorAutoLaunchCheck ();
  }//GEN-LAST:event_otherHtml5EditorClearButtonActionPerformed

  private void otherHtml5EditorLaunchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherHtml5EditorLaunchButtonActionPerformed
    externalProcessLaunch(X3dEditUserPreferences.getOtherHtml5EditorPath());
    otherHtml5EditorAutoLaunchCheck();
  }//GEN-LAST:event_otherHtml5EditorLaunchButtonActionPerformed

  private void otherHtml5EditorDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherHtml5EditorDownloadButtonActionPerformed
    browserLaunch(X3dEditUserPreferences.getDownloadSiteOtherHtml5Editor());
  }//GEN-LAST:event_otherHtml5EditorDownloadButtonActionPerformed

  private void otherVolumeEditorNameTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherVolumeEditorNameTFActionPerformed
        X3dEditUserPreferences.setOtherVolumeEditorName(otherVolumeEditorNameTF.getText().trim());
        otherVolumeEditorAutoLaunchCheck();
  }//GEN-LAST:event_otherVolumeEditorNameTFActionPerformed

  private void otherVolumeEditorCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherVolumeEditorCheckBoxActionPerformed
        if (otherVolumeEditorCheckBox.isSelected())
    {
      X3dEditUserPreferences.setOtherVolumeEditorAutoLaunch("true");
    }
    else
    {
      X3dEditUserPreferences.setOtherVolumeEditorAutoLaunch("false");
    }
    otherVolumeEditorAutoLaunchCheck();
  }//GEN-LAST:event_otherVolumeEditorCheckBoxActionPerformed

  private void otherVolumeEditorPathTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherVolumeEditorPathTFActionPerformed
        X3dEditUserPreferences.setOtherVolumeEditorPath(otherVolumeEditorPathTF.getText().trim());
        otherVolumeEditorAutoLaunchCheck();
  }//GEN-LAST:event_otherVolumeEditorPathTFActionPerformed

  private void otherVolumeChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherVolumeChooserButtonActionPerformed
    commonChooser(otherVolumeEditorPathTF, "Find other volume editor", evt);
    X3dEditUserPreferences.setOtherVolumeEditorPath(otherVolumeEditorPathTF.getText().trim());
    otherVolumeEditorAutoLaunchCheck ();
  }//GEN-LAST:event_otherVolumeChooserButtonActionPerformed

  private void otherVolumeEditorClearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherVolumeEditorClearButtonActionPerformed
    X3dEditUserPreferences.resetOtherVolumeEditorPath();
    X3dEditUserPreferences.resetOtherVolumeEditorName();
    otherVolumeEditorPathTF.setText("");
    otherVolumeEditorNameTF.setText( X3dEditUserPreferences.getOtherVolumeEditorNameDefault());
    otherVolumeEditorAutoLaunchCheck ();
  }//GEN-LAST:event_otherVolumeEditorClearButtonActionPerformed

  private void otherVolumeEditorLaunchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherVolumeEditorLaunchButtonActionPerformed
    externalProcessLaunch(X3dEditUserPreferences.getOtherVolumeEditorPath());
    otherVolumeEditorAutoLaunchCheck();
  }//GEN-LAST:event_otherVolumeEditorLaunchButtonActionPerformed

  private void otherVolumeEditorDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherVolumeEditorDownloadButtonActionPerformed
    browserLaunch(X3dEditUserPreferences.getDownloadSiteOtherVolumeEditor());
  }//GEN-LAST:event_otherVolumeEditorDownloadButtonActionPerformed

  private void seg3dVolumeEditorCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seg3dVolumeEditorCheckBoxActionPerformed
    if (seg3dVolumeEditorCheckBox.isSelected())
    {
      X3dEditUserPreferences.setSeg3dAutoLaunch("true");
    }
    else
    {
      X3dEditUserPreferences.setSeg3dAutoLaunch("false");
    }
    seg3dVolumeAutoLaunchCheck ();
  }//GEN-LAST:event_seg3dVolumeEditorCheckBoxActionPerformed

  private void seg3dVolumeEditorPathTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seg3dVolumeEditorPathTFActionPerformed
        X3dEditUserPreferences.setSeg3dEditorPath(seg3dVolumeEditorPathTF.getText().trim());
        seg3dVolumeAutoLaunchCheck ();
  }//GEN-LAST:event_seg3dVolumeEditorPathTFActionPerformed

  private void seg3dVolumeEditorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seg3dVolumeEditorChooserButtonActionPerformed
    commonChooser(seg3dVolumeEditorPathTF, "Find Seg3d graphics authoring tool", evt);
    X3dEditUserPreferences.setSeg3dEditorPath(seg3dVolumeEditorPathTF.getText().trim());
    seg3dVolumeAutoLaunchCheck ();
  }//GEN-LAST:event_seg3dVolumeEditorChooserButtonActionPerformed

  private void seg3dVolumeEditorDefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seg3dVolumeEditorDefaultButtonActionPerformed
    seg3dVolumeEditorPathTF.setText(X3dEditUserPreferences.getSeg3dEditorPathDefault());
    X3dEditUserPreferences.setSeg3dEditorPath(seg3dVolumeEditorPathTF.getText().trim());
    seg3dVolumeAutoLaunchCheck ();
  }//GEN-LAST:event_seg3dVolumeEditorDefaultButtonActionPerformed

  private void seg3dVolumeEditorLaunchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seg3dVolumeEditorLaunchButtonActionPerformed
    externalProcessLaunch(X3dEditUserPreferences.getSeg3dEditorPath());
    seg3dVolumeAutoLaunchCheck ();
  }//GEN-LAST:event_seg3dVolumeEditorLaunchButtonActionPerformed

  private void seg3dVolumeEditorDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seg3dVolumeEditorDownloadButtonActionPerformed
    browserLaunch(X3dEditUserPreferences.getDownloadSiteSeg3d());
  }//GEN-LAST:event_seg3dVolumeEditorDownloadButtonActionPerformed

  private void seg3dVolumeEditorHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_seg3dVolumeEditorHelpButtonActionPerformed
    browserLaunch(X3dEditUserPreferences.helpSiteSeg3d);
  }//GEN-LAST:event_seg3dVolumeEditorHelpButtonActionPerformed

  private void slicer3dVolumeEditorCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_slicer3dVolumeEditorCheckBoxActionPerformed
    if (slicer3dVolumeEditorCheckBox.isSelected())
    {
      X3dEditUserPreferences.setSlicer3dAutoLaunch("true");
    }
    else
    {
      X3dEditUserPreferences.setSlicer3dAutoLaunch("false");
    }
    slicer3dVolumeAutoLaunchCheck ();
  }//GEN-LAST:event_slicer3dVolumeEditorCheckBoxActionPerformed

  private void slicer3dVolumeEditorPathTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_slicer3dVolumeEditorPathTFActionPerformed
        X3dEditUserPreferences.setSlicer3dEditorPath(slicer3dVolumeEditorPathTF.getText().trim());
        slicer3dVolumeAutoLaunchCheck ();
  }//GEN-LAST:event_slicer3dVolumeEditorPathTFActionPerformed

  private void slicer3dVolumeEditorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_slicer3dVolumeEditorChooserButtonActionPerformed
    commonChooser(slicer3dVolumeEditorPathTF, "Find Slicer3d graphics authoring tool", evt);
    X3dEditUserPreferences.setSlicer3dEditorPath(slicer3dVolumeEditorPathTF.getText().trim());
    slicer3dVolumeAutoLaunchCheck ();
  }//GEN-LAST:event_slicer3dVolumeEditorChooserButtonActionPerformed

  private void slicer3dVolumeEditorDefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_slicer3dVolumeEditorDefaultButtonActionPerformed
    slicer3dVolumeEditorPathTF.setText(X3dEditUserPreferences.getSlicer3dEditorPathDefault());
    X3dEditUserPreferences.setSlicer3dEditorPath(slicer3dVolumeEditorPathTF.getText().trim());
    slicer3dVolumeAutoLaunchCheck ();
  }//GEN-LAST:event_slicer3dVolumeEditorDefaultButtonActionPerformed

  private void slicer3dVolumeEditorLaunchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_slicer3dVolumeEditorLaunchButtonActionPerformed
    externalProcessLaunch(X3dEditUserPreferences.getSlicer3dEditorPath());
    slicer3dVolumeAutoLaunchCheck ();
  }//GEN-LAST:event_slicer3dVolumeEditorLaunchButtonActionPerformed

  private void slicer3dVolumeEditorDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_slicer3dVolumeEditorDownloadButtonActionPerformed
    browserLaunch(X3dEditUserPreferences.getDownloadSiteSlicer3d());
  }//GEN-LAST:event_slicer3dVolumeEditorDownloadButtonActionPerformed

  private void slicer3dVolumeEditorHelpButtonslicer3dVolumeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_slicer3dVolumeEditorHelpButtonslicer3dVolumeActionPerformed
    browserLaunch(X3dEditUserPreferences.helpSiteSlicer3d);
  }//GEN-LAST:event_slicer3dVolumeEditorHelpButtonslicer3dVolumeActionPerformed

  private void itksnapVolumeEditorCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itksnapVolumeEditorCheckBoxActionPerformed
    if (itksnapVolumeEditorCheckBox.isSelected())
    {
      X3dEditUserPreferences.setItksnapAutoLaunch("true");
    }
    else
    {
      X3dEditUserPreferences.setItksnapAutoLaunch("false");
    }
    itksnapVolumeAutoLaunchCheck ();
  }//GEN-LAST:event_itksnapVolumeEditorCheckBoxActionPerformed

  private void itksnapVolumeEditorPathTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itksnapVolumeEditorPathTFActionPerformed
        X3dEditUserPreferences.setItksnapEditorPath(itksnapVolumeEditorPathTF.getText().trim());
        itksnapVolumeAutoLaunchCheck ();
  }//GEN-LAST:event_itksnapVolumeEditorPathTFActionPerformed

  private void itksnapVolumeEditorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itksnapVolumeEditorChooserButtonActionPerformed
    commonChooser(itksnapVolumeEditorPathTF, "Find ITK-SNAP graphics authoring tool", evt);
    X3dEditUserPreferences.setItksnapEditorPath(itksnapVolumeEditorPathTF.getText().trim());
    itksnapVolumeAutoLaunchCheck ();
  }//GEN-LAST:event_itksnapVolumeEditorChooserButtonActionPerformed

  private void itksnapVolumeEditorDefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itksnapVolumeEditorDefaultButtonActionPerformed
    itksnapVolumeEditorPathTF.setText(X3dEditUserPreferences.getItksnapEditorPathDefault());
    X3dEditUserPreferences.setItksnapEditorPath(itksnapVolumeEditorPathTF.getText().trim());
    itksnapVolumeAutoLaunchCheck ();
  }//GEN-LAST:event_itksnapVolumeEditorDefaultButtonActionPerformed

  private void itksnapVolumeEditorLaunchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itksnapVolumeEditorLaunchButtonActionPerformed
    externalProcessLaunch(X3dEditUserPreferences.getItksnapEditorPath());
    itksnapVolumeAutoLaunchCheck ();
  }//GEN-LAST:event_itksnapVolumeEditorLaunchButtonActionPerformed

  private void itksnapVolumeEditorDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itksnapVolumeEditorDownloadButtonActionPerformed
    browserLaunch(X3dEditUserPreferences.getDownloadSiteItksnap());
  }//GEN-LAST:event_itksnapVolumeEditorDownloadButtonActionPerformed

  private void itksnapVolumeEditorHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itksnapVolumeEditorHelpButtonActionPerformed
    browserLaunch(X3dEditUserPreferences.helpSiteItksnap);
  }//GEN-LAST:event_itksnapVolumeEditorHelpButtonActionPerformed

    private void bsContentStudioX3dEditorCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsContentStudioX3dEditorCheckBoxActionPerformed
        if (bsContentStudioX3dEditorCheckBox.isSelected())
        {
          X3dEditUserPreferences.setBsContentStudioAutoLaunch("true");
        }
        else
        {
          X3dEditUserPreferences.setBsContentStudioAutoLaunch("false");
        }
        bsContentStudioAutoLaunchCheck ();
    }//GEN-LAST:event_bsContentStudioX3dEditorCheckBoxActionPerformed

    private void bsContentStudioX3dEditorPathTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsContentStudioX3dEditorPathTFActionPerformed
        X3dEditUserPreferences.setBsContentStudioX3dEditorPath(bsContentStudioX3dEditorPathTF.getText().trim());
        bsContentStudioAutoLaunchCheck ();
    }//GEN-LAST:event_bsContentStudioX3dEditorPathTFActionPerformed

    private void bsContentStudioX3dEditorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsContentStudioX3dEditorChooserButtonActionPerformed
        commonChooser(bsContentStudioX3dEditorPathTF, "Find BS Content Studio 3D graphics authoring tool", evt);
        bsContentStudioAutoLaunchCheck ();
    }//GEN-LAST:event_bsContentStudioX3dEditorChooserButtonActionPerformed

    private void bsContentStudioX3dEditorDefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsContentStudioX3dEditorDefaultButtonActionPerformed
        bsContentStudioX3dEditorPathTF.setText(X3dEditUserPreferences.getBsContentStudioX3dEditorPathDefault());
        X3dEditUserPreferences.setBsContentStudioX3dEditorPath(bsContentStudioX3dEditorPathTF.getText().trim());
        bsContentStudioAutoLaunchCheck ();
    }//GEN-LAST:event_bsContentStudioX3dEditorDefaultButtonActionPerformed

    private void bsContentStudioX3dEditorLaunchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsContentStudioX3dEditorLaunchButtonActionPerformed
        externalProcessLaunch(X3dEditUserPreferences.getBsContentStudioX3dEditorPath());
        bsContentStudioAutoLaunchCheck ();
    }//GEN-LAST:event_bsContentStudioX3dEditorLaunchButtonActionPerformed

    private void bsContentStudioX3dEditorDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsContentStudioX3dEditorDownloadButtonActionPerformed
        openInBrowser(X3dEditUserPreferences.getDownloadSiteBsContentStudio()); 
    }//GEN-LAST:event_bsContentStudioX3dEditorDownloadButtonActionPerformed

    private void bsContentStudioX3dEditorHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bsContentStudioX3dEditorHelpButtonActionPerformed
        browserLaunch(X3dEditUserPreferences.helpSiteBsContentStudio);
    }//GEN-LAST:event_bsContentStudioX3dEditorHelpButtonActionPerformed

    private void svgeditEditorPathTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_svgeditEditorPathTFActionPerformed
        X3dEditUserPreferences.setSvgeditEditorPath(svgeditEditorPathTF.getText().trim());
        svgeditEditorAutoLaunchCheck ();
    }//GEN-LAST:event_svgeditEditorPathTFActionPerformed

    private void svgeditEditorCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_svgeditEditorCheckBoxActionPerformed
        if (svgeditEditorCheckBox.isSelected())
        {
          X3dEditUserPreferences.setSvgeditAutoLaunch("true");
        }
        else
        {
          X3dEditUserPreferences.setSvgeditAutoLaunch("false");
        }
        svgeditEditorAutoLaunchCheck ();
    }//GEN-LAST:event_svgeditEditorCheckBoxActionPerformed

    private void svgeditEditorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_svgeditEditorChooserButtonActionPerformed
        commonChooser(svgeditEditorPathTF, "Find SVG-Edit authoring tool", evt);
        svgeditEditorAutoLaunchCheck();
    }//GEN-LAST:event_svgeditEditorChooserButtonActionPerformed

    private void svgeditEditorDefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_svgeditEditorDefaultButtonActionPerformed
        svgeditEditorPathTF.setText(X3dEditUserPreferences.getSvgeditEditorPathDefault());
        X3dEditUserPreferences.setSvgeditEditorPath(svgeditEditorPathTF.getText().trim());
        svgeditEditorAutoLaunchCheck();
    }//GEN-LAST:event_svgeditEditorDefaultButtonActionPerformed

    private void svgeditEditorLaunchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_svgeditEditorLaunchButtonActionPerformed
        String svgeditUrl = X3dEditUserPreferences.getSvgeditEditorPath();
        if (!svgeditUrl.contains("://"))
        {
             svgeditUrl = "file://" + svgeditUrl; // prepend
        }
        openInBrowser(svgeditUrl); // browser-based execution using javascript
        svgeditEditorAutoLaunchCheck ();
    }//GEN-LAST:event_svgeditEditorLaunchButtonActionPerformed

    private void svgeditEditorDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_svgeditEditorDownloadButtonActionPerformed
        openInBrowser(X3dEditUserPreferences.getDownloadSiteSvgedit());
    }//GEN-LAST:event_svgeditEditorDownloadButtonActionPerformed

    private void svgeditEditorHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_svgeditEditorHelpButtonActionPerformed
        browserLaunch(X3dEditUserPreferences.helpSiteSvgedit);
    }//GEN-LAST:event_svgeditEditorHelpButtonActionPerformed

    private void coneLinesComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_coneLinesComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_coneLinesComboBoxActionPerformed

    private void polyTransNuGrafEditorCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_polyTransNuGrafEditorCheckBoxActionPerformed
    if (polyTransNuGrafEditorCheckBox.isSelected())
    {
      X3dEditUserPreferences.setPolyTransNuGrafAutoLaunch("true");
    }
    else
    {
      X3dEditUserPreferences.setPolyTransNuGrafAutoLaunch("false");
    }
        polyTransNuGrafAutoLaunchCheck ();
    }//GEN-LAST:event_polyTransNuGrafEditorCheckBoxActionPerformed

    private void polyTransNuGrafEditorPathTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_polyTransNuGrafEditorPathTFActionPerformed
        X3dEditUserPreferences.setPolyTransNuGrafEditorPath(polyTransNuGrafEditorPathTF.getText().trim());
        polyTransNuGrafAutoLaunchCheck ();
    }//GEN-LAST:event_polyTransNuGrafEditorPathTFActionPerformed

    private void polyTransNuGrafEditorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_polyTransNuGrafEditorChooserButtonActionPerformed
        commonChooser(polyTransNuGrafEditorPathTF, "Find PolyTransNuGraf graphics authoring tool", evt);
        X3dEditUserPreferences.setPolyTransNuGrafEditorPath(polyTransNuGrafEditorPathTF.getText().trim());
        polyTransNuGrafAutoLaunchCheck ();
    }//GEN-LAST:event_polyTransNuGrafEditorChooserButtonActionPerformed

    private void polyTransNuGrafEditorDefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_polyTransNuGrafEditorDefaultButtonActionPerformed
        polyTransNuGrafEditorPathTF.setText(X3dEditUserPreferences.getPolyTransNuGrafEditorPathDefault());
        X3dEditUserPreferences.setPolyTransNuGrafEditorPath(polyTransNuGrafEditorPathTF.getText().trim());
        polyTransNuGrafAutoLaunchCheck ();
    }//GEN-LAST:event_polyTransNuGrafEditorDefaultButtonActionPerformed

    private void polyTransNuGrafEditorLaunchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_polyTransNuGrafEditorLaunchButtonActionPerformed
        externalProcessLaunch(X3dEditUserPreferences.getPolyTransNuGrafEditorPath());
        polyTransNuGrafAutoLaunchCheck ();
    }//GEN-LAST:event_polyTransNuGrafEditorLaunchButtonActionPerformed

    private void polyTransNuGrafEditorDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_polyTransNuGrafEditorDownloadButtonActionPerformed
        browserLaunch(X3dEditUserPreferences.getDownloadSitePolyTransNuGraf());
    }//GEN-LAST:event_polyTransNuGrafEditorDownloadButtonActionPerformed

    private void polyTransNuGrafEditorHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_polyTransNuGrafEditorHelpButtonActionPerformed
        browserLaunch(X3dEditUserPreferences.helpSitePolyTransNuGraf);
    }//GEN-LAST:event_polyTransNuGrafEditorHelpButtonActionPerformed

    private void whiteDuneX3dEditorCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_whiteDuneX3dEditorCheckBoxActionPerformed
        if (whiteDuneX3dEditorCheckBox.isSelected())
        {
          X3dEditUserPreferences.setWhiteDuneAutoLaunch("true");
        }
        else
        {
          X3dEditUserPreferences.setWhiteDuneAutoLaunch("false");
        }
        whiteDuneAutoLaunchCheck ();
    }//GEN-LAST:event_whiteDuneX3dEditorCheckBoxActionPerformed

    private void whiteDuneX3dEditorPathTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_whiteDuneX3dEditorPathTFActionPerformed
        X3dEditUserPreferences.setWhiteDuneX3dEditorPath(whiteDuneX3dEditorPathTF.getText().trim());
        whiteDuneAutoLaunchCheck ();
    }//GEN-LAST:event_whiteDuneX3dEditorPathTFActionPerformed

    private void whiteDuneX3dEditorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_whiteDuneX3dEditorChooserButtonActionPerformed
        commonChooser(whiteDuneX3dEditorPathTF, "Find White Dune 3D graphics authoring tool", evt);
        X3dEditUserPreferences.setWhiteDuneX3dEditorPath(whiteDuneX3dEditorPathTF.getText().trim());
        whiteDuneAutoLaunchCheck ();
    }//GEN-LAST:event_whiteDuneX3dEditorChooserButtonActionPerformed

    private void whiteDuneX3dEditorDefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_whiteDuneX3dEditorDefaultButtonActionPerformed
        whiteDuneX3dEditorPathTF.setText(X3dEditUserPreferences.getWhiteDuneX3dEditorPathDefault());
        X3dEditUserPreferences.setWhiteDuneX3dEditorPath(whiteDuneX3dEditorPathTF.getText().trim());
        whiteDuneAutoLaunchCheck ();
    }//GEN-LAST:event_whiteDuneX3dEditorDefaultButtonActionPerformed

    private void whiteDuneX3dEditorLaunchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_whiteDuneX3dEditorLaunchButtonActionPerformed
        externalProcessLaunch(X3dEditUserPreferences.getWhiteDuneX3dEditorPath());
        whiteDuneAutoLaunchCheck ();
    }//GEN-LAST:event_whiteDuneX3dEditorLaunchButtonActionPerformed

    private void whiteDuneX3dEditorDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_whiteDuneX3dEditorDownloadButtonActionPerformed
        browserLaunch(X3dEditUserPreferences.getDownloadSiteWhiteDune());
    }//GEN-LAST:event_whiteDuneX3dEditorDownloadButtonActionPerformed

    private void whiteDuneX3dEditorHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_whiteDuneX3dEditorHelpButtonActionPerformed
        browserLaunch(X3dEditUserPreferences.helpSiteWhiteDune);
    }//GEN-LAST:event_whiteDuneX3dEditorHelpButtonActionPerformed

    private void curaX3dEditorCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_curaX3dEditorCheckBoxActionPerformed
        if (curaX3dEditorCheckBox.isSelected())
        {
          X3dEditUserPreferences.setCuraAutoLaunch("true");
        }
        else
        {
          X3dEditUserPreferences.setCuraAutoLaunch("false");
        }
        curaAutoLaunchCheck ();
    }//GEN-LAST:event_curaX3dEditorCheckBoxActionPerformed

    private void curaX3dEditorPathTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_curaX3dEditorPathTFActionPerformed
        X3dEditUserPreferences.setCuraX3dEditorPath(curaX3dEditorPathTF.getText().trim());
        curaAutoLaunchCheck ();
    }//GEN-LAST:event_curaX3dEditorPathTFActionPerformed

    private void curaX3dEditorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_curaX3dEditorChooserButtonActionPerformed
        commonChooser(curaX3dEditorPathTF, "Find Ultimaker Cura3D printing tool", evt);
        X3dEditUserPreferences.setCuraX3dEditorPath(curaX3dEditorPathTF.getText().trim());
        curaAutoLaunchCheck ();
    }//GEN-LAST:event_curaX3dEditorChooserButtonActionPerformed

    private void curaX3dEditorDefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_curaX3dEditorDefaultButtonActionPerformed
        curaX3dEditorPathTF.setText(X3dEditUserPreferences.getCuraX3dEditorPathDefault());
        X3dEditUserPreferences.setCuraX3dEditorPath(curaX3dEditorPathTF.getText().trim());
        curaAutoLaunchCheck ();
    }//GEN-LAST:event_curaX3dEditorDefaultButtonActionPerformed

    private void curaX3dEditorLaunchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_curaX3dEditorLaunchButtonActionPerformed
        externalProcessLaunch(X3dEditUserPreferences.getCuraX3dEditorPath());
        curaAutoLaunchCheck ();
    }//GEN-LAST:event_curaX3dEditorLaunchButtonActionPerformed

    private void curaX3dEditorDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_curaX3dEditorDownloadButtonActionPerformed
        browserLaunch(X3dEditUserPreferences.getDownloadSiteCura());
    }//GEN-LAST:event_curaX3dEditorDownloadButtonActionPerformed

    private void curaX3dEditorHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_curaX3dEditorHelpButtonActionPerformed
        browserLaunch(X3dEditUserPreferences.helpSiteCura);
    }//GEN-LAST:event_curaX3dEditorHelpButtonActionPerformed

    private void altovaXMLSpyCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_altovaXMLSpyCheckBoxActionPerformed
        if (altovaXMLSpyCheckBox.isSelected())
        {
          X3dEditUserPreferences.setAltovaXMLSpyAutoLaunch("true");
        }
        else
        {
          X3dEditUserPreferences.setAltovaXMLSpyAutoLaunch("false");
        }
        altovaXMLSpyAutoLaunchCheck ();
    }//GEN-LAST:event_altovaXMLSpyCheckBoxActionPerformed

    private void altovaXMLSpyTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_altovaXMLSpyTFActionPerformed
        X3dEditUserPreferences.setAltovaXMLSpyX3dEditorPath(altovaXMLSpyTF.getText().trim());
        altovaXMLSpyAutoLaunchCheck ();
    }//GEN-LAST:event_altovaXMLSpyTFActionPerformed

    private void altovaXMLSpyChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_altovaXMLSpyChooserButtonActionPerformed
        commonChooser(altovaXMLSpyTF, "Find Altova XMLSpy authoring tool", evt);
        X3dEditUserPreferences.setAltovaXMLSpyX3dEditorPath(altovaXMLSpyTF.getText().trim());
        altovaXMLSpyAutoLaunchCheck ();
    }//GEN-LAST:event_altovaXMLSpyChooserButtonActionPerformed

    private void altovaXMLSpyDefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_altovaXMLSpyDefaultButtonActionPerformed
        altovaXMLSpyTF.setText(X3dEditUserPreferences.getAltovaXMLSpyX3dEditorPathDefault());
        X3dEditUserPreferences.setAltovaXMLSpyX3dEditorPath(altovaXMLSpyTF.getText().trim());
        altovaXMLSpyAutoLaunchCheck ();
    }//GEN-LAST:event_altovaXMLSpyDefaultButtonActionPerformed

    private void altovaXMLSpyLaunchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_altovaXMLSpyLaunchButtonActionPerformed
        externalProcessLaunch(X3dEditUserPreferences.getAltovaXMLSpyX3dEditorPath());
        altovaXMLSpyAutoLaunchCheck ();
    }//GEN-LAST:event_altovaXMLSpyLaunchButtonActionPerformed

    private void altovaXMLSpyDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_altovaXMLSpyDownloadButtonActionPerformed
        browserLaunch(X3dEditUserPreferences.getDownloadSiteAltovaXMLSpy());
    }//GEN-LAST:event_altovaXMLSpyDownloadButtonActionPerformed

    private void altovaXMLSpyHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_altovaXMLSpyHelpButtonActionPerformed
        browserLaunch(X3dEditUserPreferences.helpSiteAltovaXMLSpy);
    }//GEN-LAST:event_altovaXMLSpyHelpButtonActionPerformed

    private void protegePlayerCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_protegePlayerCheckBoxActionPerformed
        if (protegePlayerCheckBox.isSelected())
        {
          X3dEditUserPreferences.setProtegeAutoLaunch("true");
        }
        else
        {
          X3dEditUserPreferences.setProtegeAutoLaunch("false");
        }
        protegeAutoLaunchCheck ();
    }//GEN-LAST:event_protegePlayerCheckBoxActionPerformed

    private void protegePlayerPathTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_protegePlayerPathTFActionPerformed
        X3dEditUserPreferences.setProtegePlayerPath(protegePlayerPathTF.getText().trim());
        protegeAutoLaunchCheck ();
    }//GEN-LAST:event_protegePlayerPathTFActionPerformed

    private void protegePlayerChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_protegePlayerChooserButtonActionPerformed
        commonChooser(protegePlayerPathTF, "Find Protege ontology editor", evt);
        X3dEditUserPreferences.setProtegePlayerPath(protegePlayerPathTF.getText().trim());
        protegeAutoLaunchCheck ();
    }//GEN-LAST:event_protegePlayerChooserButtonActionPerformed

    private void protegePlayerDefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_protegePlayerDefaultButtonActionPerformed
        protegePlayerPathTF.setText(X3dEditUserPreferences.getProtegePlayerPathDefault());
        X3dEditUserPreferences.setProtegePlayerPath(protegePlayerPathTF.getText().trim());
        protegeAutoLaunchCheck ();
    }//GEN-LAST:event_protegePlayerDefaultButtonActionPerformed

    private void protegePlayerLaunchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_protegePlayerLaunchButtonActionPerformed
    externalProcessLaunch(X3dEditUserPreferences.getProtegePlayerPath());
        protegeAutoLaunchCheck ();
    }//GEN-LAST:event_protegePlayerLaunchButtonActionPerformed

    private void protegePlayerDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_protegePlayerDownloadButtonActionPerformed
        browserLaunch(X3dEditUserPreferences.getDownloadSiteProtege());
    }//GEN-LAST:event_protegePlayerDownloadButtonActionPerformed

    private void protegePlayerHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_protegePlayerHelpButtonActionPerformed
        browserLaunch(X3dEditUserPreferences.helpSiteProtege);
    }//GEN-LAST:event_protegePlayerHelpButtonActionPerformed

    private void otherSemanticWebEditorNameTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherSemanticWebEditorNameTFActionPerformed
        X3dEditUserPreferences.setOtherSemanticWebEditorName(otherSemanticWebEditorNameTF.getText().trim());
        otherSemanticWebEditorAutoLaunchCheck();
    }//GEN-LAST:event_otherSemanticWebEditorNameTFActionPerformed

    private void otherSemanticWebEditorCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherSemanticWebEditorCheckBoxActionPerformed
        if (otherSemanticWebEditorCheckBox.isSelected())
        {
          X3dEditUserPreferences.setOtherSemanticWebEditorAutoLaunch("true");
        }
        else
        {
          X3dEditUserPreferences.setOtherSemanticWebEditorAutoLaunch("false");
        }
        otherSemanticWebEditorAutoLaunchCheck();
    }//GEN-LAST:event_otherSemanticWebEditorCheckBoxActionPerformed

    private void otherSemanticWebEditorPathTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherSemanticWebEditorPathTFActionPerformed
        X3dEditUserPreferences.setOtherSemanticWebEditorPath(otherSemanticWebEditorPathTF.getText().trim());
        otherSemanticWebEditorAutoLaunchCheck();
    }//GEN-LAST:event_otherSemanticWebEditorPathTFActionPerformed

    private void otherSemanticWebEditorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherSemanticWebEditorChooserButtonActionPerformed
        commonChooser(otherSemanticWebEditorPathTF, "Find other Semantic Web editor", evt);
        X3dEditUserPreferences.setOtherSemanticWebEditorPath(otherSemanticWebEditorPathTF.getText().trim());
        otherSemanticWebEditorAutoLaunchCheck();
    }//GEN-LAST:event_otherSemanticWebEditorChooserButtonActionPerformed

    private void otherSemanticWebEditorClearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherSemanticWebEditorClearButtonActionPerformed
    X3dEditUserPreferences.resetOtherSemanticWebEditorPath();
    X3dEditUserPreferences.resetOtherSemanticWebEditorName();
    otherSemanticWebEditorPathTF.setText("");
    otherSemanticWebEditorNameTF.setText( X3dEditUserPreferences.getOtherSemanticWebEditorNameDefault());
    otherSemanticWebEditorAutoLaunchCheck();
    }//GEN-LAST:event_otherSemanticWebEditorClearButtonActionPerformed

    private void otherSemanticWebEditorLaunchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherSemanticWebEditorLaunchButtonActionPerformed
    externalProcessLaunch(X3dEditUserPreferences.getOtherSemanticWebEditorPath());
        otherSemanticWebEditorAutoLaunchCheck();
    }//GEN-LAST:event_otherSemanticWebEditorLaunchButtonActionPerformed

    private void otherSemanticWebEditorDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_otherSemanticWebEditorDownloadButtonActionPerformed
    browserLaunch(X3dEditUserPreferences.getDownloadSiteOtherSemanticWebEditor());
    }//GEN-LAST:event_otherSemanticWebEditorDownloadButtonActionPerformed

    private void otherSemanticWebEditorPathTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_otherSemanticWebEditorPathTFFocusLost
        X3dEditUserPreferences.setOtherSemanticWebEditorPath(otherSemanticWebEditorPathTF.getText().trim());
        otherSemanticWebEditorAutoLaunchCheck();
    }//GEN-LAST:event_otherSemanticWebEditorPathTFFocusLost

    private void otherVideoEditorPathTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_otherVideoEditorPathTFFocusLost
        X3dEditUserPreferences.setOtherVideoEditorPath(otherVideoEditorPathTF.getText().trim());
        otherVideoEditorAutoLaunchCheck();
    }//GEN-LAST:event_otherVideoEditorPathTFFocusLost

    private void otherHtml5EditorPathTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_otherHtml5EditorPathTFFocusLost
        X3dEditUserPreferences.setOtherHtml5EditorPath(otherHtml5EditorPathTF.getText().trim());
        otherHtml5EditorAutoLaunchCheck();
    }//GEN-LAST:event_otherHtml5EditorPathTFFocusLost

    private void otherAudioEditorPathTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_otherAudioEditorPathTFFocusLost
        X3dEditUserPreferences.setOtherAudioEditorPath(otherAudioEditorPathTF.getText().trim());
        otherAudioEditorAutoLaunchCheck();
    }//GEN-LAST:event_otherAudioEditorPathTFFocusLost

    private void otherX3dEditorPathTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_otherX3dEditorPathTFFocusLost
        X3dEditUserPreferences.setOtherX3dEditorPath(otherX3dEditorPathTF.getText().trim());
        otherX3dEditorAutoLaunchCheck();
    }//GEN-LAST:event_otherX3dEditorPathTFFocusLost

    private void otherX3dPlayerPathTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_otherX3dPlayerPathTFFocusLost
        X3dEditUserPreferences.setOtherX3dPlayerPath(otherX3dPlayerPathTF.getText().trim());
        otherX3dPlayerAutoLaunchCheck();
    }//GEN-LAST:event_otherX3dPlayerPathTFFocusLost

    private void otherImageEditorPathTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_otherImageEditorPathTFFocusLost
        X3dEditUserPreferences.setOtherImageEditorPath(otherImageEditorPathTF.getText().trim());
        otherImageEditorAutoLaunchCheck ();
    }//GEN-LAST:event_otherImageEditorPathTFFocusLost

    private void otherVolumeEditorPathTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_otherVolumeEditorPathTFFocusLost
        X3dEditUserPreferences.setOtherVolumeEditorPath(otherVolumeEditorPathTF.getText().trim());
        otherVolumeEditorAutoLaunchCheck();
    }//GEN-LAST:event_otherVolumeEditorPathTFFocusLost

    private void reportPlayerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportPlayerButtonActionPerformed
        feedbackButtonSend ("Panel Preferences: X3D Players tab");
    }//GEN-LAST:event_reportPlayerButtonActionPerformed

    private void reportModelingToolsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportModelingToolsButtonActionPerformed
        feedbackButtonSend ("Panel Preferences: X3D Modeling Tools tab");
    }//GEN-LAST:event_reportModelingToolsButtonActionPerformed

    private void reportImageVolumeToolsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportImageVolumeToolsButtonActionPerformed
        feedbackButtonSend ("Panel Preferences: Image and Volume Tools tab");
    }//GEN-LAST:event_reportImageVolumeToolsButtonActionPerformed

    private void reportWebMultimediaToolsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportWebMultimediaToolsButtonActionPerformed
        feedbackButtonSend ("Panel Preferences: Web and Multimedia Tools tab");
    }//GEN-LAST:event_reportWebMultimediaToolsButtonActionPerformed

    private void reportVisualizationPreferencesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportVisualizationPreferencesButtonActionPerformed
        feedbackButtonSend ("Panel Preferences: Visualization Settings tab");
    }//GEN-LAST:event_reportVisualizationPreferencesButtonActionPerformed

    private void reportSecurityPanelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reportSecurityPanelButtonActionPerformed
        feedbackButtonSend ("Panel Preferences: X3D Security tab");
    }//GEN-LAST:event_reportSecurityPanelButtonActionPerformed

    private void viewX3dResourcesSecurityButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewX3dResourcesSecurityButtonActionPerformed
       openInBrowser(X3D_RESOURCES_SECURITY);
    }//GEN-LAST:event_viewX3dResourcesSecurityButtonActionPerformed

    private void viewSecurityExamplesReadmeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewSecurityExamplesReadmeButtonActionPerformed
     ViewX3dSecurityExamplesOnlineAction viewX3dSecurityExamplesOnlineAction = new ViewX3dSecurityExamplesOnlineAction();
     viewX3dSecurityExamplesOnlineAction.performAction();
    }//GEN-LAST:event_viewSecurityExamplesReadmeButtonActionPerformed

    private void viewX3dResourcesSecurityVulnerabilitiesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewX3dResourcesSecurityVulnerabilitiesButtonActionPerformed
        openInBrowser(X3D_RESOURCES_SECURITY_VULNERABILITIES);
    }//GEN-LAST:event_viewX3dResourcesSecurityVulnerabilitiesButtonActionPerformed

    private void porteclePlayerPathTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_porteclePlayerPathTFActionPerformed
        X3dEditUserPreferences.setPorteclePlayerPath(porteclePlayerPathTF.getText().trim());
        portecleAutoLaunchCheck ();
    }//GEN-LAST:event_porteclePlayerPathTFActionPerformed

    private void porteclePlayerChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_porteclePlayerChooserButtonActionPerformed
        commonChooser(porteclePlayerPathTF, "Find Portecle keystore manager", evt);
        X3dEditUserPreferences.setPorteclePlayerPath(porteclePlayerPathTF.getText().trim());
        portecleAutoLaunchCheck ();
    }//GEN-LAST:event_porteclePlayerChooserButtonActionPerformed

    private void porteclePlayerDefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_porteclePlayerDefaultButtonActionPerformed
        porteclePlayerPathTF.setText(X3dEditUserPreferences.getPorteclePlayerPathDefault());
        X3dEditUserPreferences.setPorteclePlayerPath(porteclePlayerPathTF.getText().trim());
        portecleAutoLaunchCheck ();
    }//GEN-LAST:event_porteclePlayerDefaultButtonActionPerformed

    private void porteclePlayerLaunchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_porteclePlayerLaunchButtonActionPerformed
        externalProcessLaunch(X3dEditUserPreferences.getPorteclePlayerPath());
        portecleAutoLaunchCheck ();
    }//GEN-LAST:event_porteclePlayerLaunchButtonActionPerformed

    private void porteclePlayerDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_porteclePlayerDownloadButtonActionPerformed
        browserLaunch(X3dEditUserPreferences.getDownloadSitePortecle());
    }//GEN-LAST:event_porteclePlayerDownloadButtonActionPerformed

    private void porteclePlayerHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_porteclePlayerHelpButtonActionPerformed
        browserLaunch(X3dEditUserPreferences.helpSitePortecle);
    }//GEN-LAST:event_porteclePlayerHelpButtonActionPerformed

    private void keystoreExplorerPlayerPathTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keystoreExplorerPlayerPathTFActionPerformed
        X3dEditUserPreferences.setKeystoreExplorerPlayerPath(keystoreExplorerPlayerPathTF.getText().trim());
        keystoreExplorerAutoLaunchCheck ();
    }//GEN-LAST:event_keystoreExplorerPlayerPathTFActionPerformed

    private void keystoreExplorerPlayerChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keystoreExplorerPlayerChooserButtonActionPerformed
        commonChooser(keystoreExplorerPlayerPathTF, "Find KeyStore Explorer keystore manager", evt);
        X3dEditUserPreferences.setKeystoreExplorerPlayerPath(keystoreExplorerPlayerPathTF.getText().trim());
        keystoreExplorerAutoLaunchCheck ();
    }//GEN-LAST:event_keystoreExplorerPlayerChooserButtonActionPerformed

    private void keystoreExplorerPlayerDefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keystoreExplorerPlayerDefaultButtonActionPerformed
        keystoreExplorerPlayerPathTF.setText(X3dEditUserPreferences.getKeystoreExplorerPlayerPathDefault());
        X3dEditUserPreferences.setKeystoreExplorerPlayerPath(keystoreExplorerPlayerPathTF.getText().trim());
        keystoreExplorerAutoLaunchCheck ();
    }//GEN-LAST:event_keystoreExplorerPlayerDefaultButtonActionPerformed

    private void keystoreExplorerPlayerLaunchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keystoreExplorerPlayerLaunchButtonActionPerformed
        externalProcessLaunch(X3dEditUserPreferences.getKeystoreExplorerPlayerPath());
        keystoreExplorerAutoLaunchCheck ();
    }//GEN-LAST:event_keystoreExplorerPlayerLaunchButtonActionPerformed

    private void keystoreExplorerPlayerDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keystoreExplorerPlayerDownloadButtonActionPerformed
        browserLaunch(X3dEditUserPreferences.getDownloadSiteKeystoreExplorer());
    }//GEN-LAST:event_keystoreExplorerPlayerDownloadButtonActionPerformed

    private void keystoreExplorerPlayerHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keystoreExplorerPlayerHelpButtonActionPerformed
        browserLaunch(X3dEditUserPreferences.helpSiteKeystoreExplorer);
    }//GEN-LAST:event_keystoreExplorerPlayerHelpButtonActionPerformed

    private void checkSplitKeystoreDirectoryFilename ()
    {
        String newValue = keystoreDirectoryTF.getText().trim();
        if (newValue.endsWith(".ks"))
        {
            int newFileNameIndex = newValue.length();
            String newFileName;
            if (newValue.contains("/"))
            {
                 newFileNameIndex = newValue.lastIndexOf("/") + 1;
                 newFileName = newValue.substring(newFileNameIndex);
            }
            else if (newValue.contains("\\"))
            {
                 newFileNameIndex = newValue.lastIndexOf("\\") + 1;
                 newFileName = newValue.substring(newFileNameIndex);
            }
            else newFileName = newValue;
            keystoreFileNameTF.setText(newFileName);
            keystoreDirectoryTF.setText(newValue.substring(0, newFileNameIndex));
        }
        keystoreExplorerAutoLaunchCheck ();
    }
    private void keystoreDirectoryTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_keystoreDirectoryTFActionPerformed
        checkSplitKeystoreDirectoryFilename ();
        X3dEditUserPreferences.setKeystoreDirectory(keystoreDirectoryTF.getText().trim());
        keystorePathLabel2.setText(getKeystorePath());
        keystoreDirectoryCheck ();
        keystoreFilePathCheck();
    }//GEN-LAST:event_keystoreDirectoryTFActionPerformed

    private void keystoreDirectoryTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_keystoreDirectoryTFFocusLost
        checkSplitKeystoreDirectoryFilename ();
        X3dEditUserPreferences.setKeystoreDirectory(keystoreDirectoryTF.getText().trim());
        keystoreDirectoryCheck ();
        keystoreFilePathCheck();
    }//GEN-LAST:event_keystoreDirectoryTFFocusLost

    private void keystoreExplorerPlayerPathTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_keystoreExplorerPlayerPathTFFocusLost
        X3dEditUserPreferences.setKeystoreExplorerPlayerPath(keystoreExplorerPlayerPathTF.getText().trim());
        keystoreExplorerAutoLaunchCheck ();
    }//GEN-LAST:event_keystoreExplorerPlayerPathTFFocusLost

    private void porteclePlayerPathTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_porteclePlayerPathTFFocusLost
        X3dEditUserPreferences.setPorteclePlayerPath(porteclePlayerPathTF.getText().trim());
        portecleAutoLaunchCheck ();
    }//GEN-LAST:event_porteclePlayerPathTFFocusLost

    private void keystoreDirectoryTFClearButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_keystoreDirectoryTFClearButtonActionPerformed
    {//GEN-HEADEREND:event_keystoreDirectoryTFClearButtonActionPerformed
        keystoreDirectoryTF.setText("");
        setKeystoreFileName(keystoreFileNameTF.getText());
        keystorePathLabel2.setText(getKeystorePath());
        keystoreDirectoryCheck ();
        keystoreFilePathCheck();
    }//GEN-LAST:event_keystoreDirectoryTFClearButtonActionPerformed

    private void authorExamplesDirectoryTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_authorExamplesDirectoryTFActionPerformed
    {//GEN-HEADEREND:event_authorExamplesDirectoryTFActionPerformed
        // check valid directory rather than file
        authorExamplesDirectoryTF.setText(authorExamplesDirectoryTF.getText().trim()); // normalize
        try {
          File file = new File(authorExamplesDirectoryTF.getText());
          if  (file.exists() && !file.isDirectory())
          {
              file = file.getParentFile(); // ensure directory
              authorExamplesDirectoryTF.setText(file.getPath());
          }
        }
        catch(Throwable t) {}// Forget about it, if any errors
        
        X3dEditUserPreferences.setExampleArchivesRootDirectory (authorExamplesDirectoryTF.getText());
        authorExamplesDirectoryCheck ();
    }//GEN-LAST:event_authorExamplesDirectoryTFActionPerformed

    private void authorExamplesDirectoryClearButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_authorExamplesDirectoryClearButtonActionPerformed
    {//GEN-HEADEREND:event_authorExamplesDirectoryClearButtonActionPerformed
        authorExamplesDirectoryTF.setText("");
        authorExamplesDirectoryTF.setText(      authorExamplesDirectoryTF.getText().trim());
        X3dEditUserPreferences.setExampleArchivesRootDirectory (authorExamplesDirectoryTF.getText());
        authorExamplesDirectoryCheck ();
    }//GEN-LAST:event_authorExamplesDirectoryClearButtonActionPerformed

    private void authorExamplesDirectoryButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_authorExamplesDirectoryButtonActionPerformed
    {//GEN-HEADEREND:event_authorExamplesDirectoryButtonActionPerformed
        // file chooser looks in given directory first
        commonChooser(authorExamplesDirectoryTF, "Choose local examples root directory", evt);
        authorExamplesDirectoryTF.setText(      authorExamplesDirectoryTF.getText().trim());
        X3dEditUserPreferences.setExampleArchivesRootDirectory (authorExamplesDirectoryTF.getText());
        authorExamplesDirectoryCheck ();
    }//GEN-LAST:event_authorExamplesDirectoryButtonActionPerformed

    private void authorExamplesDirectoryDefaultButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_authorExamplesDirectoryDefaultButtonActionPerformed
    {//GEN-HEADEREND:event_authorExamplesDirectoryDefaultButtonActionPerformed
        authorExamplesDirectoryTF.setText(EXAMPLES_ROOT_DIRECTORY_DEFAULT); // user.home
        X3dEditUserPreferences.setExampleArchivesRootDirectory(authorExamplesDirectoryTF.getText());
        authorExamplesDirectoryCheck ();
    }//GEN-LAST:event_authorExamplesDirectoryDefaultButtonActionPerformed

    private void keystorePasswordTFFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_keystorePasswordTFFocusLost
    {//GEN-HEADEREND:event_keystorePasswordTFFocusLost
        setKeystorePassword(keystorePasswordTF.getText());
    }//GEN-LAST:event_keystorePasswordTFFocusLost

    private void keystorePasswordTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_keystorePasswordTFActionPerformed
    {//GEN-HEADEREND:event_keystorePasswordTFActionPerformed
        setKeystorePassword(keystorePasswordTF.getText());
    }//GEN-LAST:event_keystorePasswordTFActionPerformed

    private void keystoreManageButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_keystoreManageButtonActionPerformed
    {//GEN-HEADEREND:event_keystoreManageButtonActionPerformed
        ManageKeyStoreAction newManageKeyStoreAction = new ManageKeyStoreAction();
        // https://stackoverflow.com/questions/19594587/how-to-convert-a-string-array-to-char-array-in-java
        newManageKeyStoreAction.performAction(getKeystorePassword().toCharArray());
    }//GEN-LAST:event_keystoreManageButtonActionPerformed

    private void authorNameClearButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_authorNameClearButtonActionPerformed
    {//GEN-HEADEREND:event_authorNameClearButtonActionPerformed
        authorNameTextField.setText("");
    }//GEN-LAST:event_authorNameClearButtonActionPerformed

    private void authorEmailClearButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_authorEmailClearButtonActionPerformed
    {//GEN-HEADEREND:event_authorEmailClearButtonActionPerformed
        authorEmailTextField.setText("");
    }//GEN-LAST:event_authorEmailClearButtonActionPerformed

    private void downloadLocalExamplesArchivesButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_downloadLocalExamplesArchivesButtonActionPerformed
    {//GEN-HEADEREND:event_downloadLocalExamplesArchivesButtonActionPerformed
        DownloadX3dExamplesArchivesAction downloadX3dExamplesArchivesAction = new DownloadX3dExamplesArchivesAction();
        SwingUtilities.invokeLater(() -> {
          downloadX3dExamplesArchivesAction.performAction();
        });
        // close this Preferences Panel via action class
        // https://stackoverflow.com/questions/29357055/close-window-jpanel-in-java
        JComponent parentComponent = (JComponent) evt.getSource();
        Window     parentWindow    = SwingUtilities.getWindowAncestor(parentComponent);
        parentWindow.dispose();
    }//GEN-LAST:event_downloadLocalExamplesArchivesButtonActionPerformed

    private void keystorePasswordTFClearButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_keystorePasswordTFClearButtonActionPerformed
    {//GEN-HEADEREND:event_keystorePasswordTFClearButtonActionPerformed
        keystorePasswordTF.setText("");
    }//GEN-LAST:event_keystorePasswordTFClearButtonActionPerformed

    private void authorEmailTextFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_authorEmailTextFieldActionPerformed
    {//GEN-HEADEREND:event_authorEmailTextFieldActionPerformed
        setAuthorEmail(authorEmailTextField.getText());
    }//GEN-LAST:event_authorEmailTextFieldActionPerformed

    private void authorNameTextFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_authorNameTextFieldActionPerformed
    {//GEN-HEADEREND:event_authorNameTextFieldActionPerformed
        setAuthorName(authorNameTextField.getText());
    }//GEN-LAST:event_authorNameTextFieldActionPerformed

    private void authorNameTextFieldFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_authorNameTextFieldFocusLost
    {//GEN-HEADEREND:event_authorNameTextFieldFocusLost
        authorNameTextFieldActionPerformed(null);
    }//GEN-LAST:event_authorNameTextFieldFocusLost

    private void authorEmailTextFieldFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_authorEmailTextFieldFocusLost
    {//GEN-HEADEREND:event_authorEmailTextFieldFocusLost
        authorEmailTextFieldActionPerformed(null);
    }//GEN-LAST:event_authorEmailTextFieldFocusLost

    private void authorExamplesDirectoryTFFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_authorExamplesDirectoryTFFocusLost
    {//GEN-HEADEREND:event_authorExamplesDirectoryTFFocusLost
        authorExamplesDirectoryTFActionPerformed(null);
    }//GEN-LAST:event_authorExamplesDirectoryTFFocusLost

    private void keystoreFileNameTFFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_keystoreFileNameTFFocusLost
    {//GEN-HEADEREND:event_keystoreFileNameTFFocusLost
        setKeystoreFileName(keystoreFileNameTF.getText());
        keystorePathLabel2.setText(getKeystorePath());
        keystoreFilePathCheck ();
    }//GEN-LAST:event_keystoreFileNameTFFocusLost

    private void keystoreFileNameTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_keystoreFileNameTFActionPerformed
    {//GEN-HEADEREND:event_keystoreFileNameTFActionPerformed
        setKeystoreFileName(keystoreFileNameTF.getText());
        keystorePathLabel2.setText(getKeystorePath());
        keystoreFilePathCheck ();
    }//GEN-LAST:event_keystoreFileNameTFActionPerformed

    private void keystoreFileNameTFClearButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_keystoreFileNameTFClearButtonActionPerformed
    {//GEN-HEADEREND:event_keystoreFileNameTFClearButtonActionPerformed
        keystoreFileNameTF.setText("");
        setKeystoreFileName(keystoreFileNameTF.getText());
        keystorePathLabel2.setText(getKeystorePath());
        keystoreFilePathCheck ();
    }//GEN-LAST:event_keystoreFileNameTFClearButtonActionPerformed

    private void keystorePasswordDefaultButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_keystorePasswordDefaultButtonActionPerformed
    {//GEN-HEADEREND:event_keystorePasswordDefaultButtonActionPerformed
        keystorePasswordTF.setText(getKeystorePasswordDefault());
    }//GEN-LAST:event_keystorePasswordDefaultButtonActionPerformed

    private void keystoreFileNameDefaultButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_keystoreFileNameDefaultButtonActionPerformed
    {//GEN-HEADEREND:event_keystoreFileNameDefaultButtonActionPerformed
        keystoreFileNameTF.setText(getKeystoreFileNameDefault());
        X3dEditUserPreferences.setKeystoreFileName(keystoreFileNameTF.getText());
        keystorePathLabel2.setText(getKeystorePath());
        keystoreFilePathCheck ();
    }//GEN-LAST:event_keystoreFileNameDefaultButtonActionPerformed

    private void reportAuthorButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_reportAuthorButtonActionPerformed
    {//GEN-HEADEREND:event_reportAuthorButtonActionPerformed
        feedbackButtonSend ("Panel Preferences: Author settings tab");
    }//GEN-LAST:event_reportAuthorButtonActionPerformed

    private void newX3dModelsDirectoryTFFocusLost(java.awt.event.FocusEvent evt)//GEN-FIRST:event_newX3dModelsDirectoryTFFocusLost
    {//GEN-HEADEREND:event_newX3dModelsDirectoryTFFocusLost
        X3dEditUserPreferences.setNewX3dModelsDirectory(newX3dModelsDirectoryTF.getText());
        newX3dModelsDirectoryCheck ();
    }//GEN-LAST:event_newX3dModelsDirectoryTFFocusLost

    private void newX3dModelsDirectoryTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_newX3dModelsDirectoryTFActionPerformed
    {//GEN-HEADEREND:event_newX3dModelsDirectoryTFActionPerformed
        // check valid directory rather than file
        newX3dModelsDirectoryTF.setText(newX3dModelsDirectoryTF.getText().trim()); // normalize
        try {
          File file = new File(newX3dModelsDirectoryTF.getText());
          if  (file.exists() && !file.isDirectory())
          {
              file = file.getParentFile(); // ensure directory
              newX3dModelsDirectoryTF.setText(file.getPath());
          }
        }
        catch(Throwable t) {}// Forget about it, if any errors
        
        initializeNewX3dModelsDirectory();
        newX3dModelsDirectoryCheck ();
    }//GEN-LAST:event_newX3dModelsDirectoryTFActionPerformed

    private void newX3dModelsDirectoryClearButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_newX3dModelsDirectoryClearButtonActionPerformed
    {//GEN-HEADEREND:event_newX3dModelsDirectoryClearButtonActionPerformed
        newX3dModelsDirectoryTF.setText("");
        newX3dModelsDirectoryCheck ();
    }//GEN-LAST:event_newX3dModelsDirectoryClearButtonActionPerformed

    private void newX3dModelsDirectoryButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_newX3dModelsDirectoryButtonActionPerformed
    {//GEN-HEADEREND:event_newX3dModelsDirectoryButtonActionPerformed
        // file chooser looks in given directory first
        commonChooser(newX3dModelsDirectoryTF, "Choose directory for creating new X3D example files", evt);
        newX3dModelsDirectoryTF.setText(       newX3dModelsDirectoryTF.getText().trim());
        X3dEditUserPreferences.setNewX3dModelsDirectory(newX3dModelsDirectoryTF.getText());
        initializeNewX3dModelsDirectory();
        newX3dModelsDirectoryCheck ();
    }//GEN-LAST:event_newX3dModelsDirectoryButtonActionPerformed

    private void newX3dModelsDirectoryDefaultButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_newX3dModelsDirectoryDefaultButtonActionPerformed
    {//GEN-HEADEREND:event_newX3dModelsDirectoryDefaultButtonActionPerformed
        newX3dModelsDirectoryTF.setText(X3dEditUserPreferences.NEW_X3D_MODELS_DIRECTORY_DEFAULT);
        initializeNewX3dModelsDirectory();
        newX3dModelsDirectoryCheck ();
    }//GEN-LAST:event_newX3dModelsDirectoryDefaultButtonActionPerformed

    private void viewX3dCanonicalizationC14nReadmeButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_viewX3dCanonicalizationC14nReadmeButtonActionPerformed
    {//GEN-HEADEREND:event_viewX3dCanonicalizationC14nReadmeButtonActionPerformed
        openInBrowser(X3D_CANONICALIZATION_C14N); // TODO update, also update links on X3D Resources
        sleep (500l);
        openInBrowser(X3D_CANONICALIZATION_C14N_SPECIFICATION);
    }//GEN-LAST:event_viewX3dCanonicalizationC14nReadmeButtonActionPerformed

    private void inkscapeEditorPathTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inkscapeEditorPathTFActionPerformed
        X3dEditUserPreferences.setInkscapeEditorPath(inkscapeEditorPathTF.getText().trim());
        inkscapeEditorAutoLaunchCheck ();
    }//GEN-LAST:event_inkscapeEditorPathTFActionPerformed

    private void inkscapeEditorCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inkscapeEditorCheckBoxActionPerformed
        if (inkscapeEditorCheckBox.isSelected())
        {
          X3dEditUserPreferences.setInkscapeAutoLaunch("true");
        }
        else
        {
          X3dEditUserPreferences.setInkscapeAutoLaunch("false");
        }
        inkscapeEditorAutoLaunchCheck ();
    }//GEN-LAST:event_inkscapeEditorCheckBoxActionPerformed

    private void inkscapeEditorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inkscapeEditorChooserButtonActionPerformed
        commonChooser(inkscapeEditorPathTF, "Find Inkscape authoring tool", evt);
        inkscapeEditorAutoLaunchCheck();
            commonChooser(inkscapeEditorPathTF, "Inkscape SVG authoring tool", evt);
    X3dEditUserPreferences.setInkscapeEditorPath(inkscapeEditorPathTF.getText().trim());
    inkscapeEditorAutoLaunchCheck ();
    }//GEN-LAST:event_inkscapeEditorChooserButtonActionPerformed

    private void inkscapeEditorDefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inkscapeEditorDefaultButtonActionPerformed
        inkscapeEditorPathTF.setText(X3dEditUserPreferences.getInkscapeEditorPathDefault());
        X3dEditUserPreferences.setInkscapeEditorPath(inkscapeEditorPathTF.getText().trim());
        inkscapeEditorAutoLaunchCheck();
    }//GEN-LAST:event_inkscapeEditorDefaultButtonActionPerformed

    private void inkscapeEditorLaunchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inkscapeEditorLaunchButtonActionPerformed
        externalProcessLaunch(X3dEditUserPreferences.getInkscapeEditorPath());
        inkscapeEditorAutoLaunchCheck ();
    }//GEN-LAST:event_inkscapeEditorLaunchButtonActionPerformed

    private void inkscapeEditorDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inkscapeEditorDownloadButtonActionPerformed
        openInBrowser(X3dEditUserPreferences.getDownloadSiteInkscape());
    }//GEN-LAST:event_inkscapeEditorDownloadButtonActionPerformed

    private void inkscapeEditorHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_inkscapeEditorHelpButtonActionPerformed
        browserLaunch(X3dEditUserPreferences.helpSiteInkscape);
    }//GEN-LAST:event_inkscapeEditorHelpButtonActionPerformed

    private void titaniaX3dEditorCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_titaniaX3dEditorCheckBoxActionPerformed
        String os = System.getProperty("os.name");
        if (os.contains("Linux") && titaniaX3dEditorCheckBox.isSelected())
        {
          X3dEditUserPreferences.setTitaniaAutoLaunch("true");
        }
        else
        {
          X3dEditUserPreferences.setTitaniaAutoLaunch("false");
        }
        titaniaAutoLaunchCheck ();
    }//GEN-LAST:event_titaniaX3dEditorCheckBoxActionPerformed

    private void titaniaX3dEditorPathTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_titaniaX3dEditorPathTFActionPerformed
        X3dEditUserPreferences.setTitaniaX3dEditorPath(titaniaX3dEditorPathTF.getText().trim());
        titaniaAutoLaunchCheck ();
    }//GEN-LAST:event_titaniaX3dEditorPathTFActionPerformed

    private void titaniaX3dEditorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_titaniaX3dEditorChooserButtonActionPerformed
        commonChooser(titaniaX3dEditorPathTF, "Find Titania graphics authoring tool", evt);
        X3dEditUserPreferences.setTitaniaX3dEditorPath(titaniaX3dEditorPathTF.getText().trim());
        titaniaAutoLaunchCheck ();
    }//GEN-LAST:event_titaniaX3dEditorChooserButtonActionPerformed

    private void titaniaX3dEditorDefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_titaniaX3dEditorDefaultButtonActionPerformed
        titaniaX3dEditorPathTF.setText(X3dEditUserPreferences.getTitaniaX3dEditorPathDefault());
        X3dEditUserPreferences.setTitaniaX3dEditorPath(titaniaX3dEditorPathTF.getText().trim());
        titaniaAutoLaunchCheck ();
    }//GEN-LAST:event_titaniaX3dEditorDefaultButtonActionPerformed

    private void titaniaX3dEditorLaunchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_titaniaX3dEditorLaunchButtonActionPerformed
        externalProcessLaunch(X3dEditUserPreferences.getTitaniaX3dEditorPath());
        titaniaAutoLaunchCheck ();
    }//GEN-LAST:event_titaniaX3dEditorLaunchButtonActionPerformed

    private void titaniaX3dEditorDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_titaniaX3dEditorDownloadButtonActionPerformed
        browserLaunch(X3dEditUserPreferences.getDownloadSiteTitania());
    }//GEN-LAST:event_titaniaX3dEditorDownloadButtonActionPerformed

    private void titaniaX3dEditorHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_titaniaX3dEditorHelpButtonActionPerformed
        browserLaunch(X3dEditUserPreferences.helpSiteTitania);
    }//GEN-LAST:event_titaniaX3dEditorHelpButtonActionPerformed

    private void batikEditorPathTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batikEditorPathTFActionPerformed
        X3dEditUserPreferences.setBatikEditorPath(batikEditorPathTF.getText().trim());
        batikAutoLaunchCheck ();
    }//GEN-LAST:event_batikEditorPathTFActionPerformed

    private void batikEditorCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batikEditorCheckBoxActionPerformed
        if (batikEditorCheckBox.isSelected())
        {
          X3dEditUserPreferences.setBatikAutoLaunch("true");
        }
        else
        {
          X3dEditUserPreferences.setBatikAutoLaunch("false");
        }
        batikAutoLaunchCheck ();
    }//GEN-LAST:event_batikEditorCheckBoxActionPerformed

    private void batikEditorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batikEditorChooserButtonActionPerformed
        commonChooser(batikEditorPathTF, "Find Batik graphics authoring tool", evt);
        X3dEditUserPreferences.setBatikEditorPath(batikEditorPathTF.getText().trim());
        batikAutoLaunchCheck ();
    }//GEN-LAST:event_batikEditorChooserButtonActionPerformed

    private void batikEditorDefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batikEditorDefaultButtonActionPerformed
        batikEditorPathTF.setText(X3dEditUserPreferences.getBatikEditorPathDefault());
        X3dEditUserPreferences.setBatikEditorPath(batikEditorPathTF.getText().trim());
        batikAutoLaunchCheck ();
    }//GEN-LAST:event_batikEditorDefaultButtonActionPerformed

    private void batikEditorLaunchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batikEditorLaunchButtonActionPerformed
        externalProcessLaunch(X3dEditUserPreferences.getBatikEditorPath());
        batikAutoLaunchCheck ();
    }//GEN-LAST:event_batikEditorLaunchButtonActionPerformed

    private void batikEditorHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batikEditorHelpButtonActionPerformed
        browserLaunch(X3dEditUserPreferences.helpSiteBatik);
    }//GEN-LAST:event_batikEditorHelpButtonActionPerformed

    private void batikEditorDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_batikEditorDownloadButtonActionPerformed
        browserLaunch(X3dEditUserPreferences.getDownloadSiteBatik());
    }//GEN-LAST:event_batikEditorDownloadButtonActionPerformed

    private void view3dsceneTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_view3dsceneTFFocusLost
        X3dEditUserPreferences.setView3dScenePath(view3dsceneTF.getText().trim());
        view3dsceneAutoLaunchCheck();
    }//GEN-LAST:event_view3dsceneTFFocusLost

    private void freeWrlTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_freeWrlTFFocusLost
        X3dEditUserPreferences.setFreeWrlPath(freeWrlTF.getText().trim());
        freeWrlAutoLaunchCheck();
    }//GEN-LAST:event_freeWrlTFFocusLost

    private void h3dTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_h3dTFFocusLost
        X3dEditUserPreferences.setFreeWrlPath(h3dTF.getText().trim());
        h3dAutoLaunchCheck();
    }//GEN-LAST:event_h3dTFFocusLost

    private void instantRealityTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_instantRealityTFFocusLost
        X3dEditUserPreferences.setInstantRealityPath(instantRealityTF.getText().trim());
        instantRealityAutoLaunchCheck();
    }//GEN-LAST:event_instantRealityTFFocusLost

    private void octagaTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_octagaTFFocusLost
        X3dEditUserPreferences.setOctagaPath(octagaTF.getText().trim());
        octagaAutoLaunchCheck();
    }//GEN-LAST:event_octagaTFFocusLost

    private void xj3dTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_xj3dTFFocusLost
        X3dEditUserPreferences.setXj3DPath(xj3dTF.getText().trim());
        xj3dAutoLaunchCheck();
    }//GEN-LAST:event_xj3dTFFocusLost

    private void heilanTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_heilanTFFocusLost
        X3dEditUserPreferences.setHeilanPath(heilanTF.getText().trim());
        heilanAutoLaunchCheck();
    }//GEN-LAST:event_heilanTFFocusLost

    private void swirlx3dTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_swirlx3dTFFocusLost
        X3dEditUserPreferences.setSwirlX3DPath(swirlx3dTF.getText().trim());
        swirlx3dAutoLaunchCheck();
    }//GEN-LAST:event_swirlx3dTFFocusLost

    private void vivatyTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_vivatyTFFocusLost
        X3dEditUserPreferences.setVivatyPlayerPath(vivatyTF.getText().trim());
        vivatyAutoLaunchCheck();
    }//GEN-LAST:event_vivatyTFFocusLost

    private void contactTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_contactTFFocusLost
        X3dEditUserPreferences.setContactPath(contactTF.getText().trim());
        contactAutoLaunchCheck();
    }//GEN-LAST:event_contactTFFocusLost

    private void otherX3dPlayerNameTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_otherX3dPlayerNameTFFocusLost
        X3dEditUserPreferences.setOtherX3dPlayerName(otherX3dPlayerNameTF.getText().trim());
        otherX3dPlayerAutoLaunchCheck();
    }//GEN-LAST:event_otherX3dPlayerNameTFFocusLost

    private void altovaXMLSpyTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_altovaXMLSpyTFFocusLost
        X3dEditUserPreferences.setAltovaXMLSpyX3dEditorPath(altovaXMLSpyTF.getText().trim());
        altovaXMLSpyAutoLaunchCheck ();
    }//GEN-LAST:event_altovaXMLSpyTFFocusLost

    private void blenderX3dEditorPathTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_blenderX3dEditorPathTFFocusLost
        X3dEditUserPreferences.setBlenderX3dEditorPath(blenderX3dEditorPathTF.getText().trim());
        blenderAutoLaunchCheck ();
    }//GEN-LAST:event_blenderX3dEditorPathTFFocusLost

    private void bsContentStudioX3dEditorPathTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_bsContentStudioX3dEditorPathTFFocusLost
        X3dEditUserPreferences.setBsContentStudioX3dEditorPath(bsContentStudioX3dEditorPathTF.getText().trim());
        bsContentStudioAutoLaunchCheck ();
    }//GEN-LAST:event_bsContentStudioX3dEditorPathTFFocusLost

    private void meshLabX3dEditorPathTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_meshLabX3dEditorPathTFFocusLost
        X3dEditUserPreferences.setMeshLabX3dEditorPath(meshLabX3dEditorPathTF.getText().trim());
        meshLabAutoLaunchCheck ();
    }//GEN-LAST:event_meshLabX3dEditorPathTFFocusLost

    private void polyTransNuGrafEditorPathTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_polyTransNuGrafEditorPathTFFocusLost
        X3dEditUserPreferences.setPolyTransNuGrafEditorPath(polyTransNuGrafEditorPathTF.getText().trim());
        polyTransNuGrafAutoLaunchCheck ();
    }//GEN-LAST:event_polyTransNuGrafEditorPathTFFocusLost

    private void paraviewX3dEditorPathTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_paraviewX3dEditorPathTFFocusLost
        X3dEditUserPreferences.setParaviewX3dEditorPath(paraviewX3dEditorPathTF.getText().trim());
        paraviewAutoLaunchCheck ();
    }//GEN-LAST:event_paraviewX3dEditorPathTFFocusLost

    private void seamless3dX3dEditorPathTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_seamless3dX3dEditorPathTFFocusLost
        X3dEditUserPreferences.setSeamlessX3dEditorPath(seamless3dX3dEditorPathTF.getText().trim());
        seamless3dAutoLaunchCheck ();
    }//GEN-LAST:event_seamless3dX3dEditorPathTFFocusLost

    private void titaniaX3dEditorPathTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_titaniaX3dEditorPathTFFocusLost
        X3dEditUserPreferences.setTitaniaX3dEditorPath(titaniaX3dEditorPathTF.getText().trim());
        titaniaAutoLaunchCheck ();
    }//GEN-LAST:event_titaniaX3dEditorPathTFFocusLost

    private void curaX3dEditorPathTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_curaX3dEditorPathTFFocusLost
        X3dEditUserPreferences.setCuraX3dEditorPath(curaX3dEditorPathTF.getText().trim());
        curaAutoLaunchCheck ();
    }//GEN-LAST:event_curaX3dEditorPathTFFocusLost

    private void ultraEditX3dEditorPathTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_ultraEditX3dEditorPathTFFocusLost
    X3dEditUserPreferences.setUltraEditX3dEditorPath(ultraEditX3dEditorPathTF.getText().trim());
    ultraEditAutoLaunchCheck ();
    }//GEN-LAST:event_ultraEditX3dEditorPathTFFocusLost

    private void whiteDuneX3dEditorPathTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_whiteDuneX3dEditorPathTFFocusLost
        X3dEditUserPreferences.setWhiteDuneX3dEditorPath(whiteDuneX3dEditorPathTF.getText().trim());
        whiteDuneAutoLaunchCheck ();
    }//GEN-LAST:event_whiteDuneX3dEditorPathTFFocusLost

    private void wings3dX3dEditorPathTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_wings3dX3dEditorPathTFFocusLost
        X3dEditUserPreferences.setWingsX3dEditorPath(wings3dX3dEditorPathTF.getText().trim());
        wings3dAutoLaunchCheck ();
    }//GEN-LAST:event_wings3dX3dEditorPathTFFocusLost

    private void otherX3dEditorNameTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_otherX3dEditorNameTFFocusLost
        X3dEditUserPreferences.setOtherX3dEditorName(otherX3dEditorNameTF.getText().trim());
        otherX3dEditorAutoLaunchCheck();
    }//GEN-LAST:event_otherX3dEditorNameTFFocusLost

    private void audacityEditorPathTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_audacityEditorPathTFFocusLost
        X3dEditUserPreferences.setAudacityEditorPath(audacityEditorPathTF.getText().trim());
        audacityAutoLaunchCheck ();
    }//GEN-LAST:event_audacityEditorPathTFFocusLost

    private void amayaEditorPathTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_amayaEditorPathTFFocusLost
        X3dEditUserPreferences.setAmayaEditorPath(amayaEditorPathTF.getText().trim());
        amayaAutoLaunchCheck ();
    }//GEN-LAST:event_amayaEditorPathTFFocusLost

    private void batikEditorPathTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_batikEditorPathTFFocusLost
        X3dEditUserPreferences.setBatikEditorPath(batikEditorPathTF.getText().trim());
        batikAutoLaunchCheck ();
    }//GEN-LAST:event_batikEditorPathTFFocusLost

    private void inkscapeEditorPathTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_inkscapeEditorPathTFFocusLost
        X3dEditUserPreferences.setInkscapeEditorPath(inkscapeEditorPathTF.getText().trim());
        inkscapeEditorAutoLaunchCheck ();
    }//GEN-LAST:event_inkscapeEditorPathTFFocusLost

    private void svgeditEditorPathTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_svgeditEditorPathTFFocusLost
        X3dEditUserPreferences.setSvgeditEditorPath(svgeditEditorPathTF.getText().trim());
        svgeditEditorAutoLaunchCheck ();
    }//GEN-LAST:event_svgeditEditorPathTFFocusLost

    private void vlcPlayerPathTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_vlcPlayerPathTFFocusLost
        X3dEditUserPreferences.setVlcPlayerPath(vlcPlayerPathTF.getText().trim());
        vlcAutoLaunchCheck ();
    }//GEN-LAST:event_vlcPlayerPathTFFocusLost

    private void protegePlayerPathTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_protegePlayerPathTFFocusLost
        X3dEditUserPreferences.setProtegePlayerPath(protegePlayerPathTF.getText().trim());
        protegeAutoLaunchCheck ();
    }//GEN-LAST:event_protegePlayerPathTFFocusLost

    private void gimpEditorTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_gimpEditorTFFocusLost
        X3dEditUserPreferences.setGimpImageEditorPath(gimpEditorTF.getText().trim());
        gimpAutoLaunchCheck ();
    }//GEN-LAST:event_gimpEditorTFFocusLost

    private void fijiEditorTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_fijiEditorTFFocusLost
        X3dEditUserPreferences.setFijiEditorPath(fijiEditorTF.getText().trim());
        fijiAutoLaunchCheck ();
    }//GEN-LAST:event_fijiEditorTFFocusLost

    private void imageJEditorTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_imageJEditorTFFocusLost
        X3dEditUserPreferences.setImageJEditorPath(imageJEditorTF.getText().trim());
        imageJAutoLaunchCheck ();
    }//GEN-LAST:event_imageJEditorTFFocusLost

    private void imageMagickEditorTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_imageMagickEditorTFFocusLost
        X3dEditUserPreferences.setImageMagickEditorPath(imageMagickEditorTF.getText().trim());
        imageMagickAutoLaunchCheck ();
    }//GEN-LAST:event_imageMagickEditorTFFocusLost

    private void itksnapVolumeEditorPathTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_itksnapVolumeEditorPathTFFocusLost
        X3dEditUserPreferences.setItksnapEditorPath(itksnapVolumeEditorPathTF.getText().trim());
        itksnapVolumeAutoLaunchCheck ();
    }//GEN-LAST:event_itksnapVolumeEditorPathTFFocusLost

    private void seg3dVolumeEditorPathTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_seg3dVolumeEditorPathTFFocusLost
        X3dEditUserPreferences.setSeg3dEditorPath(seg3dVolumeEditorPathTF.getText().trim());
        seg3dVolumeAutoLaunchCheck ();
    }//GEN-LAST:event_seg3dVolumeEditorPathTFFocusLost

    private void slicer3dVolumeEditorPathTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_slicer3dVolumeEditorPathTFFocusLost
        X3dEditUserPreferences.setSlicer3dEditorPath(slicer3dVolumeEditorPathTF.getText().trim());
        slicer3dVolumeAutoLaunchCheck ();
    }//GEN-LAST:event_slicer3dVolumeEditorPathTFFocusLost

    private void bvhackerEditorCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bvhackerEditorCheckBoxActionPerformed
        if (bvhackerEditorCheckBox.isSelected())
        {
          X3dEditUserPreferences.setBvhackerAutoLaunch("true");
        }
        else
        {
          X3dEditUserPreferences.setBvhackerAutoLaunch("false");
        }
        bvhackerAutoLaunchCheck ();
    }//GEN-LAST:event_bvhackerEditorCheckBoxActionPerformed

    private void bvhackerEditorPathTFFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_bvhackerEditorPathTFFocusLost
        X3dEditUserPreferences.setBvhackerEditorPath(bvhackerEditorPathTF.getText().trim());
        bvhackerAutoLaunchCheck ();  
    }//GEN-LAST:event_bvhackerEditorPathTFFocusLost

    private void bvhackerEditorPathTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bvhackerEditorPathTFActionPerformed
        X3dEditUserPreferences.setBvhackerEditorPath(bvhackerEditorPathTF.getText().trim());
        bvhackerAutoLaunchCheck ();  
    }//GEN-LAST:event_bvhackerEditorPathTFActionPerformed

    private void bvhackerEditorChooserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bvhackerEditorChooserButtonActionPerformed
        commonChooser(bvhackerEditorPathTF, "Find bvhacker BVH motion animation authoring tool", evt);
        X3dEditUserPreferences.setBvhackerEditorPath(bvhackerEditorPathTF.getText().trim());
        bvhackerAutoLaunchCheck ();
    }//GEN-LAST:event_bvhackerEditorChooserButtonActionPerformed

    private void bvhackerEditorDefaultButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bvhackerEditorDefaultButtonActionPerformed
        bvhackerEditorPathTF.setText(X3dEditUserPreferences.getBvhackerEditorPathDefault());
        X3dEditUserPreferences.setBvhackerEditorPath(bvhackerEditorPathTF.getText().trim());
        bvhackerAutoLaunchCheck ();
    }//GEN-LAST:event_bvhackerEditorDefaultButtonActionPerformed

    private void bvhackerEditorLaunchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bvhackerEditorLaunchButtonActionPerformed
        externalProcessLaunch(X3dEditUserPreferences.getBvhackerEditorPath());
        bvhackerAutoLaunchCheck ();
    }//GEN-LAST:event_bvhackerEditorLaunchButtonActionPerformed

    private void bvhackerEditorDownloadButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bvhackerEditorDownloadButtonActionPerformed
        browserLaunch(X3dEditUserPreferences.getDownloadSiteBvhacker());
    }//GEN-LAST:event_bvhackerEditorDownloadButtonActionPerformed

    private void bvhackerEditorHelpButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bvhackerEditorHelpButtonActionPerformed
        browserLaunch(X3dEditUserPreferences.helpSiteBvhacker);
    }//GEN-LAST:event_bvhackerEditorHelpButtonActionPerformed

    public void sleep (long msecDuration)
    {
        try
        {
            Thread.sleep(msecDuration);
        } 
        catch (InterruptedException ex)
        {
            Exceptions.printStackTrace(ex);
        }
    }
    /** Include information so that directory purpose is evident. */
    private void initializeNewX3dModelsDirectory()
    {
        // set autolaunch if appropriate
        if (newX3dModelsDirectoryTF.getText().trim().equals(X3dEditUserPreferences.getAuthorModelsDirectory()) &&
            !X3dEditUserPreferences.isExampleArchivesServerAutolaunch())
        {
            X3dEditUserPreferences.setExampleArchivesServerAutolaunch(true);
            
            NotifyDescriptor notifyDescriptor = new NotifyDescriptor.Confirmation(
                    "Enabling autolaunch of localhost HTTP server for author X3D New Models directory", 
                    "Enabling autolaunch of localhost HTTP server", NotifyDescriptor.PLAIN_MESSAGE);
            DialogDisplayer.getDefault().notify(notifyDescriptor);
        }
        // TODO refactor/combine with checkAuthorModelsDirectoryAutolaunch ();
        
        String filename = "README.X3D-Edit.txt";
        String filepath = newX3dModelsDirectoryTF.getText() + File.separatorChar + filename;
        File ReadmeFile = new File(filepath);
        if (ReadmeFile.exists())
        {
            // close file?
            return;
        }
        // https://stackoverflow.com/questions/2885173/how-do-i-create-a-file-and-write-to-it
        PrintWriter readmeWriter;
        try
        {
            System.out.println("*** writing " + filepath);
            readmeWriter = new PrintWriter(filepath, "UTF-8");
        }
        catch (FileNotFoundException | UnsupportedEncodingException ex)
        {
            
            Exceptions.printStackTrace(ex);
            return;
        }
        readmeWriter.println(filename);
        readmeWriter.println();
        readmeWriter.println("""
This directory contains new models created using X3D-Edit authoring tool
for Extensible 3D (X3D) Graphics International Standard.

* https://savage.nps.edu/X3D-Edit
* https://web3d.org/x3d
""");
        readmeWriter.close();
        newX3dModelsDirectoryCheck();
    }

    public static void feedbackButtonSend (String panelName)
    {
        // https://stackoverflow.com/questions/5226212/how-to-open-the-default-webbrowser-using-java
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
        try {
            String mailtoReportUrl = LaunchIssueReportEmailAction.MAILTO_REPORT_URL;
            mailtoReportUrl  = mailtoReportUrl.replace("%20"," ").trim();
            mailtoReportUrl += ", " + panelName;
            mailtoReportUrl  = mailtoReportUrl.replace(" ","%20");
            Desktop.getDesktop().browse(new URI(mailtoReportUrl));
        } 
        catch (URISyntaxException | IOException ex)
        {
            Exceptions.printStackTrace(ex);
        }
    }
  private void contactAutoLaunchCheck ()
  {
    checkExistingFile = new File(contactTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setContactAutoLaunch(Boolean.toString(isExecutableFile));
    contactCheckBox.setSelected(isExecutableFile);
    contactLaunchButton.setEnabled(isExecutableFile);
    showFound (isExecutableFile, contactLabel, contactTF);
  }
  private void contactGeoAutoLaunchCheck ()
  {
    checkExistingFile = new File(contactGeoTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setContactGeoAutoLaunch(Boolean.toString(isExecutableFile));
    contactGeoCheckBox.setSelected(isExecutableFile);
    contactGeoLaunchButton.setEnabled(isExecutableFile);
    showFound (isExecutableFile, contactGeoLabel, contactGeoTF);
  }
  private void freeWrlAutoLaunchCheck ()
  {
    checkExistingFile = new File(freeWrlTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setFreeWrlAutoLaunch(Boolean.toString(isExecutableFile));
    freeWrlCheckBox.setSelected(isExecutableFile);
    freeWrlLaunchButton.setEnabled(isExecutableFile);
    showFound (isExecutableFile, freeWrlLabel, freeWrlTF);
  }
  private void h3dAutoLaunchCheck ()
  {
    checkExistingFile = new File(h3dTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setH3dAutoLaunch(Boolean.toString(isExecutableFile));
    h3dCheckBox.setSelected(isExecutableFile);
    h3dLaunchButton.setEnabled(isExecutableFile);
    showFound (isExecutableFile, h3dLabel, h3dTF);
  }
  private void instantRealityAutoLaunchCheck ()
  {
    checkExistingFile = new File(instantRealityTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setInstantRealityAutoLaunch(Boolean.toString(isExecutableFile));
    instantRealityCheckBox.setSelected(isExecutableFile);
    instantRealityLaunchButton.setEnabled(isExecutableFile);
    showFound (isExecutableFile, instantRealityLabel, instantRealityTF);
  }
  private void octagaAutoLaunchCheck ()
  {
    checkExistingFile = new File(octagaTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setOctagaAutoLaunch(Boolean.toString(isExecutableFile));
    octagaCheckBox.setSelected(isExecutableFile);
    octagaLaunchButton.setEnabled(isExecutableFile);
    showFound (isExecutableFile, octagaLabel, octagaTF);
  }
  private void view3dsceneAutoLaunchCheck ()
  {
    checkExistingFile = new File(view3dsceneTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setView3dSceneAutoLaunch(Boolean.toString(isExecutableFile));
    view3dsceneCheckBox.setSelected(isExecutableFile);
    view3dsceneLaunchButton.setEnabled(isExecutableFile);
    showFound (isExecutableFile, view3dsceneLabel, view3dsceneTF);
  }
  private void xj3dAutoLaunchCheck ()
  {
    checkExistingFile = new File(xj3dTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setXj3dAutoLaunch(Boolean.toString(isExecutableFile));
    xj3dCheckBox.setSelected(isExecutableFile);
    xj3dLaunchButton.setEnabled(isExecutableFile);
    showFound (isExecutableFile, xj3dLabel, xj3dTF);
  }
  private void heilanAutoLaunchCheck ()
  {
    checkExistingFile = new File(heilanTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setHeilanAutoLaunch(Boolean.toString(isExecutableFile));
    heilanCheckBox.setSelected(isExecutableFile);
    heilanLaunchButton.setEnabled(isExecutableFile);
    showFound (isExecutableFile, heilanLabel, heilanTF);
  }
  private void swirlx3dAutoLaunchCheck ()
  {
    checkExistingFile = new File(swirlx3dTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setSwirlx3dAutoLaunch(Boolean.toString(isExecutableFile));
    swirlx3dCheckBox.setSelected(isExecutableFile);
    swirlx3dLaunchButton.setEnabled(isExecutableFile);
    showFound (isExecutableFile, swirlx3dLabel, swirlx3dTF);
  }
  private void vivatyAutoLaunchCheck ()
  {
    checkExistingFile = new File(vivatyTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setVivatyAutoLaunch(Boolean.toString(isExecutableFile));
    vivatyCheckBox.setSelected(isExecutableFile);
    vivatyLaunchButton.setEnabled(isExecutableFile);
    showFound (isExecutableFile, vivatyLabel, vivatyTF);
  }
  private void amayaAutoLaunchCheck ()
  {
    checkExistingFile = new File(amayaEditorPathTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setAmayaAutoLaunch(Boolean.toString(isExecutableFile));
    amayaEditorCheckBox.setSelected(isExecutableFile);
    amayaEditorLaunchButton.setEnabled(isExecutableFile);
    showFound (isExecutableFile, amayaEditorLabel, amayaEditorPathTF);
  }
  private void batikAutoLaunchCheck ()
  {
    checkExistingFile = new File(batikEditorPathTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setBatikAutoLaunch(Boolean.toString(isExecutableFile));
    batikEditorCheckBox.setSelected(isExecutableFile);
    batikEditorLaunchButton.setEnabled(isExecutableFile);
    showFound (isExecutableFile, batikEditorLabel, batikEditorPathTF);
  }
  private void titaniaAutoLaunchCheck ()
  {
    checkExistingFile = new File(titaniaX3dEditorPathTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setTitaniaAutoLaunch(Boolean.toString(isExecutableFile));
    titaniaX3dEditorCheckBox.setSelected(isExecutableFile);
    titaniaX3dEditorLaunchButton.setEnabled(isExecutableFile);
    showFound (isExecutableFile, titaniaX3dEditorLabel, titaniaX3dEditorPathTF);
  }
  private void audacityAutoLaunchCheck ()
  {
    checkExistingFile = new File(audacityEditorPathTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setAudacityAutoLaunch(Boolean.toString(isExecutableFile));
    audacityEditorCheckBox.setSelected(isExecutableFile);
    audacityEditorLaunchButton.setEnabled(isExecutableFile);
    showFound (isExecutableFile, audacityEditorLabel, audacityEditorPathTF);
  }
  private void gimpAutoLaunchCheck ()
  {
    checkExistingFile = new File(gimpEditorTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setGimpAutoLaunch(Boolean.toString(isExecutableFile));
    gimpCheckBox.setSelected(isExecutableFile);
    gimpEditorLaunchButton.setEnabled(isExecutableFile);
    showFound (isExecutableFile, gimpEditorLabel, gimpEditorTF);
  }
  private void fijiAutoLaunchCheck ()
  {
    checkExistingFile = new File(fijiEditorTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setFijiAutoLaunch(Boolean.toString(isExecutableFile));
    fijiCheckBox.setSelected(isExecutableFile);
    fijiEditorLaunchButton.setEnabled(isExecutableFile);
    showFound (isExecutableFile, fijiEditorLabel, fijiEditorTF);
  }
  private void imageJAutoLaunchCheck ()
  {
    checkExistingFile = new File(imageJEditorTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setImageJAutoLaunch(Boolean.toString(isExecutableFile));
    imageJCheckBox.setSelected(isExecutableFile);
    imageJEditorLaunchButton.setEnabled(isExecutableFile);
    showFound (isExecutableFile, imageJEditorLabel, imageJEditorTF);
  }
  private void imageMagickAutoLaunchCheck ()
  {
    checkExistingFile = new File(imageMagickEditorTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setImageMagickAutoLaunch(Boolean.toString(isExecutableFile));
    imageMagickCheckBox.setSelected(isExecutableFile);
    imageMagickEditorLaunchButton.setEnabled(isExecutableFile);
         showFound (isExecutableFile, imageMagickEditorLabel, imageMagickEditorTF);
  }
  private void vlcAutoLaunchCheck ()
  {
    checkExistingFile = new File(vlcPlayerPathTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setVlcAutoLaunch(Boolean.toString(isExecutableFile));
    vlcPlayerCheckBox.setSelected(isExecutableFile);
    vlcPlayerLaunchButton.setEnabled(isExecutableFile);
         showFound (isExecutableFile, vlcPlayerLabel, vlcPlayerPathTF);
  }
  private void protegeAutoLaunchCheck ()
  {
    checkExistingFile = new File(protegePlayerPathTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setProtegeAutoLaunch(Boolean.toString(isExecutableFile));
    protegePlayerCheckBox.setSelected(isExecutableFile);
    protegePlayerLaunchButton.setEnabled(isExecutableFile);
         showFound (isExecutableFile, protegePlayerLabel, protegePlayerPathTF);
  }
  private void portecleAutoLaunchCheck ()
  {
    checkExistingFile = new File(porteclePlayerPathTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setPortecleAutoLaunch(Boolean.toString(isExecutableFile));
    porteclePlayerLaunchButton.setEnabled(isExecutableFile);
    showFound (isExecutableFile, porteclePlayerLabel, porteclePlayerPathTF);
  }
  private void keystoreExplorerAutoLaunchCheck ()
  {
    checkExistingFile = new File(keystoreExplorerPlayerPathTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setKeystoreExplorerAutoLaunch(Boolean.toString(isExecutableFile));
    keystoreExplorerPlayerLaunchButton.setEnabled(isExecutableFile);
    showFound (isExecutableFile, keystoreExplorerPlayerLabel, keystoreExplorerPlayerPathTF);
  }
  private void altovaXMLSpyAutoLaunchCheck ()
  {
    checkExistingFile = new File(altovaXMLSpyTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setAltovaXMLSpyAutoLaunch(Boolean.toString(isExecutableFile));
    altovaXMLSpyCheckBox.setSelected(isExecutableFile);
    altovaXMLSpyLaunchButton.setEnabled(isExecutableFile);
    showFound (isExecutableFile, altovaXMLSpyLabel, altovaXMLSpyTF);
  }
  private void blenderAutoLaunchCheck ()
  {
    checkExistingFile = new File(blenderX3dEditorPathTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setBlenderAutoLaunch(Boolean.toString(isExecutableFile));
    blenderX3dEditorCheckBox.setSelected(isExecutableFile);
    blenderX3dEditorLaunchButton.setEnabled(isExecutableFile);
    showFound (isExecutableFile, blenderX3dEditorLabel, blenderX3dEditorPathTF);
  }
  private void bsContentStudioAutoLaunchCheck ()
  {
    checkExistingFile = new File(bsContentStudioX3dEditorPathTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setBsContentStudioAutoLaunch(Boolean.toString(isExecutableFile));
    bsContentStudioX3dEditorCheckBox.setSelected(isExecutableFile);
    bsContentStudioX3dEditorLaunchButton.setEnabled(isExecutableFile);
    showFound (isExecutableFile, bsContentStudioX3dEditorLabel, bsContentStudioX3dEditorPathTF);
  }
  private void bvhackerAutoLaunchCheck ()
  {
    checkExistingFile = new File(bvhackerEditorPathTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setBvhackerAutoLaunch(Boolean.toString(isExecutableFile));
    bvhackerEditorCheckBox.setSelected(isExecutableFile);
    bvhackerEditorLaunchButton.setEnabled(isExecutableFile);
    showFound (isExecutableFile, bvhackerEditorLabel, bvhackerEditorPathTF);
  }
  private void curaAutoLaunchCheck ()
  {
    checkExistingFile = new File(curaX3dEditorPathTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setCuraAutoLaunch(Boolean.toString(isExecutableFile));
    curaX3dEditorCheckBox.setSelected(isExecutableFile);
    curaX3dEditorLaunchButton.setEnabled(isExecutableFile);
    showFound (isExecutableFile, curaX3dEditorLabel, curaX3dEditorPathTF);
  }
  private void meshLabAutoLaunchCheck ()
  {
    checkExistingFile = new File(meshLabX3dEditorPathTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setMeshLabAutoLaunch(Boolean.toString(isExecutableFile));
    meshLabX3dEditorCheckBox.setSelected(isExecutableFile);
    meshLabX3dEditorLaunchButton.setEnabled(isExecutableFile);
    showFound (isExecutableFile, meshLabX3dEditorLabel, meshLabX3dEditorPathTF);
  }
  private void paraviewAutoLaunchCheck ()
  {
    checkExistingFile = new File(paraviewX3dEditorPathTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setParaviewAutoLaunch(Boolean.toString(isExecutableFile));
    paraviewX3dEditorCheckBox.setSelected(isExecutableFile);
    paraviewX3dEditorLaunchButton.setEnabled(isExecutableFile);
    showFound (isExecutableFile, paraviewX3dEditorLabel, paraviewX3dEditorPathTF);
  }
  private void polyTransNuGrafAutoLaunchCheck ()
  {
    checkExistingFile = new File(polyTransNuGrafEditorPathTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setPolyTransNuGrafAutoLaunch(Boolean.toString(isExecutableFile));
    polyTransNuGrafEditorCheckBox.setSelected(isExecutableFile);
    polyTransNuGrafEditorLaunchButton.setEnabled(isExecutableFile);
    showFound (isExecutableFile, polyTransNuGrafEditorLabel, polyTransNuGrafEditorPathTF);
  }
  private void seamless3dAutoLaunchCheck ()
  {
    checkExistingFile = new File(seamless3dX3dEditorPathTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setSeamless3dAutoLaunch(Boolean.toString(isExecutableFile));
    seamless3dX3dEditorCheckBox.setSelected(isExecutableFile);
    seamless3dX3dEditorLaunchButton.setEnabled(isExecutableFile);
    showFound (isExecutableFile, seamless3dX3dEditorLabel, seamless3dX3dEditorPathTF);
  }
  private void itksnapVolumeAutoLaunchCheck ()
  {
    checkExistingFile = new File(itksnapVolumeEditorPathTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setItksnapAutoLaunch(Boolean.toString(isExecutableFile));
    itksnapVolumeEditorCheckBox.setSelected(isExecutableFile);
    itksnapVolumeEditorLaunchButton.setEnabled(isExecutableFile);
    showFound (isExecutableFile, itksnapVolumeEditorLabel, itksnapVolumeEditorPathTF);
  }
  private void seg3dVolumeAutoLaunchCheck ()
  {
    checkExistingFile = new File(seg3dVolumeEditorPathTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setSeg3dAutoLaunch(Boolean.toString(isExecutableFile));
    seg3dVolumeEditorCheckBox.setSelected(isExecutableFile);
    seg3dVolumeEditorLaunchButton.setEnabled(isExecutableFile);
    showFound (isExecutableFile, seg3dVolumeEditorLabel, seg3dVolumeEditorPathTF);
  }
  private void slicer3dVolumeAutoLaunchCheck ()
  {
    checkExistingFile = new File(slicer3dVolumeEditorPathTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setSlicer3dAutoLaunch(Boolean.toString(isExecutableFile));
    slicer3dVolumeEditorCheckBox.setSelected(isExecutableFile);
    slicer3dVolumeEditorLaunchButton.setEnabled(isExecutableFile);
    showFound (isExecutableFile, slicer3dVolumeEditorLabel, slicer3dVolumeEditorPathTF);
  }
  private void ultraEditAutoLaunchCheck ()
  {
    checkExistingFile = new File(ultraEditX3dEditorPathTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setUltraEditAutoLaunch(Boolean.toString(isExecutableFile));
    ultraEditX3dEditorCheckBox.setSelected(isExecutableFile);
    ultraEditX3dEditorLaunchButton.setEnabled(isExecutableFile);
    showFound (isExecutableFile, ultraEditX3dEditorLabel, ultraEditX3dEditorPathTF);
  }
  private void wings3dAutoLaunchCheck ()
  {
    checkExistingFile = new File(wings3dX3dEditorPathTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setWings3dAutoLaunch(Boolean.toString(isExecutableFile));
    wings3dX3dEditorCheckBox.setSelected(isExecutableFile);
    wings3dX3dEditorLaunchButton.setEnabled(isExecutableFile);
    showFound (isExecutableFile, wings3dX3dEditorLabel, wings3dX3dEditorPathTF);
  }
  private void whiteDuneAutoLaunchCheck ()
  {
    checkExistingFile = new File(whiteDuneX3dEditorPathTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setWhiteDuneAutoLaunch(Boolean.toString(isExecutableFile));
    whiteDuneX3dEditorCheckBox.setSelected(isExecutableFile);
    whiteDuneX3dEditorLaunchButton.setEnabled(isExecutableFile);
    showFound (isExecutableFile, whiteDuneX3dEditorLabel, whiteDuneX3dEditorPathTF);
  }
  private void otherAudioEditorAutoLaunchCheck ()
  {
    checkExistingFile = new File(otherAudioEditorPathTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setOtherAudioEditorAutoLaunch(Boolean.toString(isExecutableFile));
    otherAudioEditorCheckBox.setSelected(isExecutableFile);
    otherAudioEditorLaunchButton.setEnabled(isExecutableFile);
    otherAudioEditorClearButton.setEnabled(otherAudioEditorPathTF.getText().trim().length() > 0);
    showFound (isExecutableFile, otherAudioEditorNameTF);
  }
  private void otherHtml5EditorAutoLaunchCheck ()
  {
    checkExistingFile = new File(otherHtml5EditorPathTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setOtherHtml5EditorAutoLaunch(Boolean.toString(isExecutableFile));
    otherHtml5EditorCheckBox.setSelected(isExecutableFile);
    otherHtml5EditorLaunchButton.setEnabled(isExecutableFile);
    otherHtml5EditorClearButton.setEnabled(otherHtml5EditorPathTF.getText().trim().length() > 0);
    showFound (isExecutableFile, otherHtml5EditorNameTF);
  }
  private void otherImageEditorAutoLaunchCheck ()
  {
    checkExistingFile = new File(otherImageEditorPathTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setOtherImageEditorAutoLaunch(Boolean.toString(isExecutableFile));
    otherImageEditorCheckBox.setSelected(isExecutableFile);
    otherImageEditorLaunchButton.setEnabled(isExecutableFile);
    otherImageEditorClearButton.setEnabled(otherImageEditorPathTF.getText().trim().length() > 0);
    showFound (isExecutableFile, otherImageEditorNameTF);
  }
  private void otherVideoEditorAutoLaunchCheck ()
  {
    checkExistingFile = new File(otherVideoEditorPathTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setOtherVideoEditorAutoLaunch(Boolean.toString(isExecutableFile));
    otherVideoEditorCheckBox.setSelected(isExecutableFile);
    otherVideoEditorLaunchButton.setEnabled(isExecutableFile);
    otherVideoEditorClearButton.setEnabled(otherVideoEditorPathTF.getText().trim().length() > 0);
    showFound (isExecutableFile, otherVideoEditorNameTF);
  }
  private void otherVolumeEditorAutoLaunchCheck ()
  {
    checkExistingFile = new File(otherVolumeEditorPathTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setOtherVolumeEditorAutoLaunch(Boolean.toString(isExecutableFile));
    otherVolumeEditorCheckBox.setSelected(isExecutableFile);
    otherVolumeEditorLaunchButton.setEnabled(isExecutableFile);
    otherVolumeEditorClearButton.setEnabled(otherVolumeEditorPathTF.getText().trim().length() > 0);
    showFound (isExecutableFile, otherVolumeEditorNameTF);
  }
  private void otherSemanticWebEditorAutoLaunchCheck ()
  {
    checkExistingFile = new File(otherSemanticWebEditorPathTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setOtherSemanticWebEditorAutoLaunch(Boolean.toString(isExecutableFile));
    otherSemanticWebEditorCheckBox.setSelected(isExecutableFile);
    otherSemanticWebEditorLaunchButton.setEnabled(isExecutableFile);
    otherSemanticWebEditorClearButton.setEnabled(otherSemanticWebEditorPathTF.getText().trim().length() > 0);
    showFound (isExecutableFile, otherSemanticWebEditorNameTF);
  }
  private void otherX3dEditorAutoLaunchCheck ()
  {
    checkExistingFile = new File(otherX3dEditorPathTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setOtherX3dEditorAutoLaunch(Boolean.toString(isExecutableFile));
    otherX3dEditorCheckBox.setSelected(isExecutableFile);
    otherX3dEditorLaunchButton.setEnabled(isExecutableFile);
    otherX3dEditorClearButton.setEnabled(otherX3dEditorPathTF.getText().trim().length() > 0); // keep the clear button enabled
    showFound (isExecutableFile, otherX3dEditorNameTF);
  }
  private void otherX3dPlayerAutoLaunchCheck ()
  {
    checkExistingFile = new File(otherX3dPlayerPathTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setOtherX3dPlayerAutoLaunch(Boolean.toString(isExecutableFile));
    otherX3dPlayerCheckBox.setSelected(isExecutableFile);
    otherX3dPlayerLaunchButton.setEnabled(isExecutableFile);
    otherX3dPlayerClearButton.setEnabled(otherX3dPlayerPathTF.getText().trim().length() > 0); // keep the clear button enabled
    showFound (isExecutableFile, otherX3dPlayerNameTF);
  }
  private void inkscapeEditorAutoLaunchCheck ()
  {
    checkExistingFile = new File(inkscapeEditorPathTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && checkExistingFile.canExecute();
    X3dEditUserPreferences.setInkscapeAutoLaunch(Boolean.toString(isExecutableFile));
    inkscapeEditorCheckBox.setSelected(isExecutableFile);
    inkscapeEditorLaunchButton.setEnabled(isExecutableFile);
    showFound (isExecutableFile, inkscapeEditorLabel, inkscapeEditorPathTF);
  }
  private void newX3dModelsDirectoryCheck ()
  {
    checkExistingFile = new File(newX3dModelsDirectoryTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isDirectory() && checkExistingFile.canExecute();
    showFound (isExecutableFile, newX3dModelsDirectoryTF);
  }
  private void authorExamplesDirectoryCheck ()
  {
    checkExistingFile = new File(authorExamplesDirectoryTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isDirectory() && checkExistingFile.canExecute();
    showFound (isExecutableFile, authorExamplesDirectoryTF);
  }
  private void keystoreDirectoryCheck ()
  {
    checkExistingFile = new File(keystoreDirectoryTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isDirectory() && checkExistingFile.canExecute();
    showFound (isExecutableFile, keystoreDirectoryTF);
  }
  private void keystoreFilePathCheck ()
  {
    checkExistingFile = new File(keystoreDirectoryTF.getText().trim() + File.separatorChar + keystoreFileNameTF.getText().trim());
    isExecutableFile = checkExistingFile.exists() && checkExistingFile.isFile() && 
                       !keystoreFileNameTF.getText().contains("\\") && !keystoreFileNameTF.getText().contains("//");
    showFound (isExecutableFile, keystoreFileNameTF);
  }
  private void svgeditEditorAutoLaunchCheck ()
  {
    // SVG-Edit is an online tool
    isReachableWebsite = isSiteUp (svgeditEditorPathTF.getText().trim());
    X3dEditUserPreferences.setSvgeditAutoLaunch(Boolean.toString(isReachableWebsite)); // "true"
      
    svgeditEditorChooserButton.setEnabled(false); // local launch not supported
    svgeditEditorCheckBox.setSelected   (true); // executableFile);
    svgeditEditorLaunchButton.setEnabled(true); // executableFile);
    showFound (isReachableWebsite, svgeditEditorLabel, svgeditEditorPathTF);
  }
  
  // https://stackoverflow.com/questions/13778635/checking-status-of-website-in-java
  public static boolean isSiteUp(String address)
  {
        try {
            URL site = new URI (address).toURL(); 
            HttpURLConnection httpURLConnection = (HttpURLConnection) site.openConnection();
            httpURLConnection.getContent();
            if (httpURLConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return true;
            }
            return false;
        } catch (SocketTimeoutException tout) {
            return false;
        } catch (IOException | URISyntaxException ioex) {
            // You may decide on more specific behaviour...
            return false;
        }
      }

  public static void browserLaunch(String pageUrl)
  {
    try
    {
      // HtmlBrowser.URLDisplayer.getDefault().showURL(new URL(pageUrl));
      
      String urlString = pageUrl;
    // https://stackoverflow.com/questions/5226212/how-to-open-the-default-webbrowser-using-java
    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
        Desktop.getDesktop().browse(new URI(urlString.replaceAll("\\\\","/")));
    }
    catch (java.net.MalformedURLException ex) {
      InputOutput io = IOProvider.getDefault().getIO("Output", false);
      io.select();
      io.getOut().println(new StringBuilder().append("Error launching external editor for ").append(pageUrl).append(": ").append(ex.getLocalizedMessage()).toString());
    } catch (URISyntaxException | IOException ex) {
          Exceptions.printStackTrace(ex);
      }
  }

    /**
     * Launch application in local operating system, no passed parameters.
     * @param applicationPath local executable of interest
     * @see https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/lang/ProcessBuilder.html
     */
    public static void externalProcessLaunch(String applicationPath) {
        ProcessBuilder processBuilder;
        try
        {
            processBuilder = new ProcessBuilder(applicationPath);
            // check if application apparently needs to start in its own directory
            if (applicationPath.toLowerCase().contains("portecle"))
            {
                File applicationDirectory = (new File(applicationPath)).getParentFile();
                if (applicationDirectory.isDirectory())
                    processBuilder.directory(applicationDirectory); // set initial directory
            }
            // now ready to start
            processBuilder.start();
        } 
        catch (IOException ex)
        {
            InputOutput io = IOProvider.getDefault().getIO("Output", false);
            io.select();
            io.getOut().println(new StringBuilder().append("Error launching external editor: ").append(ex.getLocalizedMessage()).toString());
        }
    }
    /**
     * Launch application in local operating system, with passed parameters.
     * @param applicationInvocation local executable of interest
     * @param parameters additional parameters, if any
     * @see https://docs.oracle.com/en/java/javase/19/docs/api/java.base/java/lang/ProcessBuilder.html
     */
    public static void externalProcessLaunch(String applicationInvocation, String parameters)
    {
        String[] parameterArray = parameters.split("\\s");
        String[] launchArray    = new String[1 + parameterArray.length];
        launchArray[0] = applicationInvocation;
        System.arraycopy(launchArray, 1, parameterArray, 0, parameterArray.length);
        
        ProcessBuilder processBuilder;
        try
        {
            String[] commandLine = applicationInvocation.split("\\s");
            processBuilder = new ProcessBuilder(commandLine);
            // check if application apparently needs to start in its own directory
            if (applicationInvocation.toLowerCase().contains("portecle"))
            {
                File applicationDirectory = (new File(applicationInvocation)).getParentFile();
                if (applicationDirectory.isDirectory())
                    processBuilder.directory(applicationDirectory); // set initial directory
            }
            // now ready to start
            processBuilder.start();
        }
        catch (IOException ex) {
          InputOutput io = IOProvider.getDefault().getIO("Output", false);
          io.select();
          io.getOut().println(new StringBuilder().append("Error launching external editor: ").append(ex.getLocalizedMessage()).toString());
        }
    }


  JFileChooser fileChooser;
  public void commonChooser(JTextField textField, String title, java.awt.event.ActionEvent evt)
  {
    if(fileChooser == null) // first time through
    {
      String newX3dModelDirectoryPath;
      if  ((textField.getText() != null) && !textField.getText().isBlank())
           newX3dModelDirectoryPath = textField.getText().trim();
      else newX3dModelDirectoryPath = X3dEditUserPreferences.getNewX3dModelsDirectory();
      
      File newX3dModelDirectory = new File(newX3dModelDirectoryPath);
      if (!newX3dModelDirectory.exists())
      {
          boolean successfulCreation = newX3dModelDirectory.mkdir();
          if (!successfulCreation)
          {
              System.err.println("*** commonChooser(" + title + ") unable to create new directory " + newX3dModelDirectoryPath);
          }
      }
      if  (!newX3dModelDirectory.exists() ||
           !newX3dModelDirectory.isDirectory()) // ensure directory works
      {
          newX3dModelDirectory.getParent();
          newX3dModelDirectoryPath = newX3dModelDirectory.getAbsolutePath();
          X3dEditUserPreferences.setNewX3dModelsDirectory(newX3dModelDirectoryPath);
      }
      fileChooser = new JFileChooser(newX3dModelDirectoryPath);
      fileChooser.setMultiSelectionEnabled(false);
      fileChooser.putClientProperty("JFileChooser.appBundleIsTraversable", "never");  // for macs
    }
    fileChooser.setDialogTitle(title);
    // https://stackoverflow.com/questions/25666642/jfilechooser-to-pick-a-directory-or-a-single-file
    fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES) ; // prevents user from seeing what is present: DIRECTORIES_ONLY);
    
    // https://stackoverflow.com/questions/4850315/how-to-change-text-in-jfilechooser
    fileChooser.setApproveButtonText("Select");
    
    if (title.toLowerCase().contains("keystore") && !title.toLowerCase().contains("manager"))
    {
        fileChooser.setFileFilter(new keyStoreFileTypeFilter());
    }
    // TODO special handling for any other file types?
    
    // initally point chooser at prior directory, if any
    if ((textField != null) && !textField.getText().trim().isEmpty())
    {
        File priorFile = new File(textField.getText().trim());
        if (priorFile.exists())
        {
           // unnecesary
//            if (newDirectory.isFile())
//                newDirectory = newDirectory.getParentFile(); // directory actually
            fileChooser.setSelectedFile(priorFile);
        }
    }    

    int returnValue = fileChooser.showOpenDialog(this); // blocks to display and choose
    if (returnValue == JFileChooser.APPROVE_OPTION)
    {
        // look out below!  fileChooser.getCurrentDirectory() provides parent directory, not selected directory
//        if  ( title.toLowerCase().contains("directory") && 
//             !title.toLowerCase().contains("keystore"))
//        {
//             textField.setText(fileChooser.getCurrentDirectory().getAbsolutePath());
//        }
//        else 
        textField.setText(fileChooser.getSelectedFile().getAbsolutePath());
        
        // TF callback to save changed options (doesn't happen automatically)
        textField.postActionEvent();
    }
  }
  /**
   * Retrieve default or saved values and initialize panels
   */
  void load ()
  {
       authorNameTextField.setText(X3dEditUserPreferences.getAuthorName());
      authorEmailTextField.setText(X3dEditUserPreferences.getAuthorEmail());
   newX3dModelsDirectoryTF.setText(X3dEditUserPreferences.getNewX3dModelsDirectory());
 authorExamplesDirectoryTF.setText(X3dEditUserPreferences.getExampleArchivesRootDirectory());
        keystorePasswordTF.setText(X3dEditUserPreferences.getKeystorePassword());
        keystoreFileNameTF.setText(X3dEditUserPreferences.getKeystoreFileName());
       keystoreDirectoryTF.setText(X3dEditUserPreferences.getKeystoreDirectory());
        keystorePathLabel2.setText(X3dEditUserPreferences.getKeystorePath());
 
                 contactTF.setText(X3dEditUserPreferences.getContactPath());
              contactGeoTF.setText(X3dEditUserPreferences.getContactGeoPath());
       curaX3dEditorPathTF.setText(X3dEditUserPreferences.getCuraX3dEditorPath());
                 freeWrlTF.setText(X3dEditUserPreferences.getFreeWrlPath());
                     h3dTF.setText(X3dEditUserPreferences.getH3dPath());
                  heilanTF.setText(X3dEditUserPreferences.getHeilanPath());
          instantRealityTF.setText(X3dEditUserPreferences.getInstantRealityPath());
                  octagaTF.setText(X3dEditUserPreferences.getOctagaPath());
                swirlx3dTF.setText(X3dEditUserPreferences.getSwirlX3DPath());
             view3dsceneTF.setText(X3dEditUserPreferences.getView3dScenePath());
                  vivatyTF.setText(X3dEditUserPreferences.getVivatyPath());
                    xj3dTF.setText(X3dEditUserPreferences.getXj3DPath());
      otherX3dPlayerNameTF.setText(X3dEditUserPreferences.getOtherX3dPlayerName());
      otherX3dPlayerPathTF.setText(X3dEditUserPreferences.getOtherX3dPlayerPath());
      otherX3dEditorNameTF.setText(X3dEditUserPreferences.getOtherX3dEditorName());
      otherX3dEditorPathTF.setText(X3dEditUserPreferences.getOtherX3dEditorPath());

          amayaEditorPathTF.setText(X3dEditUserPreferences.getAmayaEditorPath());
       audacityEditorPathTF.setText(X3dEditUserPreferences.getAudacityEditorPath());
          batikEditorPathTF.setText(X3dEditUserPreferences.getBatikEditorPath());
       inkscapeEditorPathTF.setText(X3dEditUserPreferences.getInkscapeEditorPath());
               gimpEditorTF.setText(X3dEditUserPreferences.getGimpImageEditorPath());
               fijiEditorTF.setText(X3dEditUserPreferences.getFijiImageEditorPath());
             imageJEditorTF.setText(X3dEditUserPreferences.getImageJEditorPath());
        imageMagickEditorTF.setText(X3dEditUserPreferences.getImageMagickEditorPath());
            vlcPlayerPathTF.setText(X3dEditUserPreferences.getVlcPlayerPath());
        protegePlayerPathTF.setText(X3dEditUserPreferences.getProtegePlayerPath());
       porteclePlayerPathTF.setText(X3dEditUserPreferences.getPorteclePlayerPath());
        keystoreDirectoryTF.setText(X3dEditUserPreferences.getKeystoreDirectory());
keystoreExplorerPlayerPathTF.setText(X3dEditUserPreferences.getKeystoreExplorerPlayerPath());
      altovaXMLSpyTF.setText(X3dEditUserPreferences.getAltovaXMLSpyX3dEditorPath());
     blenderX3dEditorPathTF.setText(X3dEditUserPreferences.getBlenderX3dEditorPath());
bsContentStudioX3dEditorPathTF.setText(X3dEditUserPreferences.getBsContentStudioX3dEditorPath());
       bvhackerEditorPathTF.setText(X3dEditUserPreferences.getBvhackerEditorPath());
        curaX3dEditorPathTF.setText(X3dEditUserPreferences.getCuraX3dEditorPath());
     meshLabX3dEditorPathTF.setText(X3dEditUserPreferences.getMeshLabX3dEditorPath());
    paraviewX3dEditorPathTF.setText(X3dEditUserPreferences.getParaviewX3dEditorPath());
polyTransNuGrafEditorPathTF.setText(X3dEditUserPreferences.getPolyTransNuGrafEditorPath());
  seamless3dX3dEditorPathTF.setText(X3dEditUserPreferences.getSeamlessX3dEditorPath());
     titaniaX3dEditorPathTF.setText(X3dEditUserPreferences.getTitaniaX3dEditorPath());
  itksnapVolumeEditorPathTF.setText(X3dEditUserPreferences.getItksnapEditorPath());
    seg3dVolumeEditorPathTF.setText(X3dEditUserPreferences.getSeg3dEditorPath());
 slicer3dVolumeEditorPathTF.setText(X3dEditUserPreferences.getSlicer3dEditorPath());
       inkscapeEditorPathTF.setText(X3dEditUserPreferences.getInkscapeEditorPath());
        svgeditEditorPathTF.setText(X3dEditUserPreferences.getSvgeditEditorPath());
   whiteDuneX3dEditorPathTF.setText(X3dEditUserPreferences.getWhiteDuneX3dEditorPath());
     wings3dX3dEditorPathTF.setText(X3dEditUserPreferences.getWingsX3dEditorPath());
   ultraEditX3dEditorPathTF.setText(X3dEditUserPreferences.getUltraEditX3dEditorPath());

   otherAudioEditorNameTF.setText(X3dEditUserPreferences.getOtherAudioEditorName());
   otherHtml5EditorNameTF.setText(X3dEditUserPreferences.getOtherHtml5EditorName());
   otherImageEditorNameTF.setText(X3dEditUserPreferences.getOtherImageEditorName());
   otherVideoEditorNameTF.setText(X3dEditUserPreferences.getOtherVideoEditorName());
  otherVolumeEditorNameTF.setText(X3dEditUserPreferences.getOtherVolumeEditorName());
   otherAudioEditorPathTF.setText(X3dEditUserPreferences.getOtherAudioEditorPath());
   otherHtml5EditorPathTF.setText(X3dEditUserPreferences.getOtherHtml5EditorPath());
   otherImageEditorPathTF.setText(X3dEditUserPreferences.getOtherImageEditorPath());
   otherVideoEditorPathTF.setText(X3dEditUserPreferences.getOtherVideoEditorPath());
  otherSemanticWebEditorPathTF.setText(X3dEditUserPreferences.getOtherSemanticWebEditorPath());

              contactCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isContactAutoLaunch()));
           contactGeoCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isContactGeoAutoLaunch()));
              freeWrlCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isFreeWrlAutoLaunch()));
                  h3dCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isH3dAutoLaunch()));
               heilanCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isHeilanAutoLaunch()));
       instantRealityCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isInstantRealityAutoLaunch()));
               octagaCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isOctagaAutoLaunch()));
          view3dsceneCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isView3dSceneAutoLaunch()));
                 xj3dCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isXj3dAutoLaunch()));
             swirlx3dCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isSwirlX3DAutoLaunch()));
               vivatyCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isVivatyAutoLaunch()));
       otherX3dPlayerCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isOtherX3dPlayerAutoLaunch()));
       otherX3dEditorCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isOtherX3dEditorAutoLaunch()));

         amayaEditorCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isAmayaAutoLaunch()));
      audacityEditorCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isAudacityAutoLaunch()));
         batikEditorCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isBatikAutoLaunch()));
      inkscapeEditorCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isInkscapeAutoLaunch()));
                gimpCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isGimpAutoLaunch()));
                fijiCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isFijiAutoLaunch()));
              imageJCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isImageJAutoLaunch()));
         imageMagickCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isImageMagickAutoLaunch()));
           vlcPlayerCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isVlcAutoLaunch()));
       protegePlayerCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isProtegeAutoLaunch()));
        altovaXMLSpyCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isAltovaXMLSpyAutoLaunch()));
    blenderX3dEditorCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isBlenderAutoLaunch()));
bsContentStudioX3dEditorCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isBsContentStudioAutoLaunch()));
      bvhackerEditorCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isBvhackerAutoLaunch()));
       curaX3dEditorCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isCuraAutoLaunch())); // ultimaker
    meshLabX3dEditorCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isMeshLabAutoLaunch()));
   paraviewX3dEditorCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isParaviewAutoLaunch()));
polyTransNuGrafEditorCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isPolyTransNuGrafAutoLaunch()));
 seamless3dX3dEditorCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isSeamless3dAutoLaunch()));
 itksnapVolumeEditorCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isItksnapAutoLaunch()));
   seg3dVolumeEditorCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isSeg3dAutoLaunch()));
slicer3dVolumeEditorCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isSlicer3dAutoLaunch()));
      inkscapeEditorCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isInkscapeAutoLaunch()));
       svgeditEditorCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isSvgeditAutoLaunch()));
  whiteDuneX3dEditorCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isWhiteDuneAutoLaunch()));
    wings3dX3dEditorCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isWings3dAutoLaunch()));
  ultraEditX3dEditorCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isUltraEditAutoLaunch()));
    otherAudioEditorCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isOtherAudioEditorAutoLaunch()));
    otherHtml5EditorCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isOtherHtml5EditorAutoLaunch()));
    otherImageEditorCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isOtherImageEditorAutoLaunch()));
    otherVideoEditorCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isOtherVideoEditorAutoLaunch()));
   otherVolumeEditorCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isOtherVolumeEditorAutoLaunch()));
otherSemanticWebEditorCheckBox.setSelected(Boolean.parseBoolean(X3dEditUserPreferences.isOtherSemanticWebEditorAutoLaunch()));
        
       launchIntervalTF.setText(X3dEditUserPreferences.getLaunchInterval());
       keystoreDirectoryTF.setText(X3dEditUserPreferences.getKeystoreDirectory());

          showNewLineOptionCheckBox.setSelected(X3dEditUserPreferences.getShowNewlineOption());
       prependNewLineCheckBox.setSelected(X3dEditUserPreferences.getPrependNewline());
        appendNewLineCheckBox.setSelected(X3dEditUserPreferences.getAppendNewline());

        autoValidationCheckBox.setSelected(X3dEditUserPreferences.getAutoValidate());
        coordinateAxesCheckBox.setSelected(X3dEditUserPreferences.getVisualizeCoordinateAxes());
        coneCenterLineCheckBox.setSelected(X3dEditUserPreferences.getVisualizeCenterLine());
        coneLinesComboBox.setSelectedItem(X3dEditUserPreferences.getVisualizeConeLines());
          lineColorRedTF.setText(X3dEditUserPreferences.getVisualizeLineColorRed());
        lineColorGreenTF.setText(X3dEditUserPreferences.getVisualizeLineColorGreen());
         lineColorBlueTF.setText(X3dEditUserPreferences.getVisualizeLineColorBlue());
        lineColorChooser.setColor(
            (new SFColor(lineColorRedTF.getText(),
                         lineColorGreenTF.getText(),
                         lineColorBlueTF.getText())).getColor());
          shapeColorRedTF.setText(X3dEditUserPreferences.getVisualizeShapeColorRed());
        shapeColorGreenTF.setText(X3dEditUserPreferences.getVisualizeShapeColorGreen());
         shapeColorBlueTF.setText(X3dEditUserPreferences.getVisualizeShapeColorBlue());
        shapeColorChooser.setColor(
            (new SFColor(shapeColorRedTF.getText(),
                         shapeColorGreenTF.getText(),
                         shapeColorBlueTF.getText())).getColor());
        transparencyTF.setText(X3dEditUserPreferences.getVisualizeTransparency());
        
        initializeHAnimVisualizationDefaultValues ();
        
    // override previous settings if tool is unavailable on current operating system
    String os = System.getProperty("os.name");
    if (!os.contains("Linux"))
    {
             titaniaX3dEditorCheckBox.setEnabled(false);
        titaniaX3dEditorChooserButton.setEnabled(false);
        titaniaX3dEditorDefaultButton.setEnabled(false);
         titaniaX3dEditorLaunchButton.setEnabled(false);
    }
  }
  private void initializeHAnimVisualizationDefaultValues ()
  {
          hAnimVisualizeCoordinateAxesCheckBox.setSelected(X3dEditUserPreferences.getVisualizeHanimCoordinateAxes());
          hAnimJointColorRedTF.setText(X3dEditUserPreferences.getVisualizeHanimJointColorRed());
        hAnimJointColorGreenTF.setText(X3dEditUserPreferences.getVisualizeHanimJointColorGreen());
         hAnimJointColorBlueTF.setText(X3dEditUserPreferences.getVisualizeHanimJointColorBlue());
        hAnimJointColorChooser.setColor(
            (new SFColor(hAnimJointColorRedTF.getText(),
                         hAnimJointColorGreenTF.getText(),
                         hAnimJointColorBlueTF.getText())).getColor());
          hAnimSegmentColorRedTF.setText(X3dEditUserPreferences.getVisualizeHanimSegmentColorRed());
        hAnimSegmentColorGreenTF.setText(X3dEditUserPreferences.getVisualizeHanimSegmentColorGreen());
         hAnimSegmentColorBlueTF.setText(X3dEditUserPreferences.getVisualizeHanimSegmentColorBlue());
        hAnimSegmentColorChooser.setColor(
            (new SFColor(hAnimSegmentColorRedTF.getText(),
                         hAnimSegmentColorGreenTF.getText(),
                         hAnimSegmentColorBlueTF.getText())).getColor());
          hAnimSiteColorRedTF.setText(X3dEditUserPreferences.getVisualizeHanimSiteColorRed());
        hAnimSiteColorGreenTF.setText(X3dEditUserPreferences.getVisualizeHanimSiteColorGreen());
         hAnimSiteColorBlueTF.setText(X3dEditUserPreferences.getVisualizeHanimSiteColorBlue());
        hAnimSiteColorChooser.setColor(
            (new SFColor(hAnimSiteColorRedTF.getText(),
                         hAnimSiteColorGreenTF.getText(),
                         hAnimSiteColorBlueTF.getText())).getColor());
  }
  private void autoLaunchChecks ()
  {
    newX3dModelsDirectoryCheck ();
  authorExamplesDirectoryCheck ();
        keystoreDirectoryCheck ();
         keystoreFilePathCheck ();
        contactAutoLaunchCheck ();
     contactGeoAutoLaunchCheck ();
        freeWrlAutoLaunchCheck ();
            h3dAutoLaunchCheck ();
 instantRealityAutoLaunchCheck ();
         octagaAutoLaunchCheck ();
    view3dsceneAutoLaunchCheck ();
           xj3dAutoLaunchCheck ();
         heilanAutoLaunchCheck ();
       swirlx3dAutoLaunchCheck ();
         vivatyAutoLaunchCheck ();
      
       audacityAutoLaunchCheck ();
          amayaAutoLaunchCheck ();
          batikAutoLaunchCheck ();
 inkscapeEditorAutoLaunchCheck ();
  svgeditEditorAutoLaunchCheck ();
            vlcAutoLaunchCheck ();
        protegeAutoLaunchCheck ();
        
           gimpAutoLaunchCheck ();
           fijiAutoLaunchCheck ();
         imageJAutoLaunchCheck ();
    imageMagickAutoLaunchCheck ();
  itksnapVolumeAutoLaunchCheck ();
    seg3dVolumeAutoLaunchCheck ();
 slicer3dVolumeAutoLaunchCheck ();

// not exposed
keystoreExplorerAutoLaunchCheck();
       portecleAutoLaunchCheck ();
        
   altovaXMLSpyAutoLaunchCheck ();
        blenderAutoLaunchCheck ();
bsContentStudioAutoLaunchCheck ();
       bvhackerAutoLaunchCheck ();
        meshLabAutoLaunchCheck ();
polyTransNuGrafAutoLaunchCheck (); // okino
       paraviewAutoLaunchCheck ();
     seamless3dAutoLaunchCheck ();
        titaniaAutoLaunchCheck ();
      ultraEditAutoLaunchCheck ();
           curaAutoLaunchCheck (); // ultimaker
      whiteDuneAutoLaunchCheck ();
        wings3dAutoLaunchCheck ();

      otherAudioEditorAutoLaunchCheck();
      otherHtml5EditorAutoLaunchCheck ();
      otherImageEditorAutoLaunchCheck ();
      otherVideoEditorAutoLaunchCheck ();
     otherVolumeEditorAutoLaunchCheck ();
otherSemanticWebEditorAutoLaunchCheck();
      //otherX3dPlayerAutoLaunchCheck (); // TODO?
        otherX3dEditorAutoLaunchCheck();
  }

  void store() // TODO needed? seems superfluous since interface performs saving...
  {
    // tab 1
    String path;

    path = contactTF.getText().trim();
    if(path.equals(X3dEditUserPreferences.getContactPathDefault()))
      X3dEditUserPreferences.resetContactPath();
    else
      X3dEditUserPreferences.setContactPath(path);

    path = contactGeoTF.getText().trim();
    if(path.equals(X3dEditUserPreferences.getContactGeoPathDefault()))
      X3dEditUserPreferences.resetContactGeoPath();
    else
      X3dEditUserPreferences.setContactGeoPath(path);

    path = freeWrlTF.getText().trim();
    if(path.equals(X3dEditUserPreferences.getFreeWrlPathDefault()))
      X3dEditUserPreferences.resetFreeWrlPath();
    else
      X3dEditUserPreferences.setFreeWrlPath(path);

    path = instantRealityTF.getText().trim();
    if(path.equals(X3dEditUserPreferences.getInstantRealityPathDefault()))
      X3dEditUserPreferences.resetInstantRealityPath();
    else
      X3dEditUserPreferences.setInstantRealityPath(path);

    path = octagaTF.getText().trim();
    if(path.equals(X3dEditUserPreferences.getOctagaPathDefault()))
      X3dEditUserPreferences.resetOctagaPath();
    else
      X3dEditUserPreferences.setOctagaPath(path);

    path = swirlx3dTF.getText().trim();
    if(path.equals(X3dEditUserPreferences.getSwirlX3DPathDefault()))
      X3dEditUserPreferences.resetSwirlX3DPath();
    else
      X3dEditUserPreferences.setSwirlX3DPath(path);

    path = view3dsceneTF.getText().trim();
    if(path.equals(X3dEditUserPreferences.getView3dScenePathDefault()))
      X3dEditUserPreferences.resetView3dScenePath();
    else
      X3dEditUserPreferences.setVivatyPlayerPath(path);

    path = vivatyTF.getText().trim();
    if(path.equals(X3dEditUserPreferences.getVivatyPlayerPathDefault()))
      X3dEditUserPreferences.resetVivatyPath();
    else
      X3dEditUserPreferences.setVivatyPlayerPath(path);

    path = xj3dTF.getText().trim();
    if(path.equals(X3dEditUserPreferences.getXj3DPathDefault()))
      X3dEditUserPreferences.resetXj3DPath();
    else
      X3dEditUserPreferences.setXj3DPath(path);

    path = otherX3dPlayerPathTF.getText().trim();
    if(path.equals(X3dEditUserPreferences.getOtherX3dPlayerPathDefault()))
      X3dEditUserPreferences.resetOtherX3dPlayerPath();
    else
      X3dEditUserPreferences.setOtherX3dPlayerPath(path);

    path = otherX3dPlayerNameTF.getText().trim();
    if(path.equals(X3dEditUserPreferences.getOtherX3dPlayerNameDefault()))
      X3dEditUserPreferences.resetOtherX3dPlayerName();
    else
      X3dEditUserPreferences.setOtherX3dPlayerName(path);

    path = otherX3dEditorPathTF.getText().trim();
    if(path.equals(X3dEditUserPreferences.getOtherX3dEditorPathDefault()))
      X3dEditUserPreferences.resetOtherX3dEditorPath();
    else
      X3dEditUserPreferences.setOtherX3dEditorPath(path);

    path = otherX3dEditorNameTF.getText().trim();
    if(path.equals(X3dEditUserPreferences.getOtherX3dEditorNameDefault()))
      X3dEditUserPreferences.resetOtherX3dEditorName();
    else
      X3dEditUserPreferences.setOtherX3dEditorName(path);

    path = otherSemanticWebEditorPathTF.getText().trim();
    if(path.equals(X3dEditUserPreferences.getOtherSemanticWebEditorPathDefault()))
      X3dEditUserPreferences.resetOtherSemanticWebEditorPath();
    else
      X3dEditUserPreferences.setOtherSemanticWebEditorPath(path);

    path = otherSemanticWebEditorNameTF.getText().trim();
    if(path.equals(X3dEditUserPreferences.getOtherSemanticWebEditorNameDefault()))
      X3dEditUserPreferences.resetOtherSemanticWebEditorName();
    else
      X3dEditUserPreferences.setOtherSemanticWebEditorName(path);

    // autoLaunch
    X3dEditUserPreferences.setContactAutoLaunch       (String.valueOf(contactCheckBox.isSelected()));
    X3dEditUserPreferences.setFreeWrlAutoLaunch       (String.valueOf(freeWrlCheckBox.isSelected()));
    X3dEditUserPreferences.setInstantRealityAutoLaunch(String.valueOf(instantRealityCheckBox.isSelected()));
    X3dEditUserPreferences.setOctagaAutoLaunch        (String.valueOf(octagaCheckBox.isSelected()));
    X3dEditUserPreferences.setSwirlx3dAutoLaunch      (String.valueOf(swirlx3dCheckBox.isSelected()));
    X3dEditUserPreferences.setView3dSceneAutoLaunch   (String.valueOf(view3dsceneCheckBox.isSelected()));
    X3dEditUserPreferences.setVivatyAutoLaunch  (String.valueOf(vivatyCheckBox.isSelected()));
    X3dEditUserPreferences.setOtherX3dPlayerAutoLaunch(String.valueOf(otherX3dPlayerCheckBox.isSelected()));
    X3dEditUserPreferences.setOtherX3dEditorAutoLaunch(String.valueOf(otherX3dEditorCheckBox.isSelected()));

    String value = launchIntervalTF.getText().trim();
    if(path.equals(X3dEditUserPreferences.getLaunchIntervalDefault()))
      X3dEditUserPreferences.resetLaunchInterval();
    else
      X3dEditUserPreferences.setLaunchInterval(value);

    // security tab
    path = keystoreDirectoryTF.getText().trim();
    if(path.equals(X3dEditUserPreferences.getKeystorePathDefault()))
      X3dEditUserPreferences.resetKeystoreDirectory();
    else
      X3dEditUserPreferences.setKeystoreDirectory(path);

    X3dEditUserPreferences.setShowNewlineOption(showNewLineOptionCheckBox.isSelected());
    X3dEditUserPreferences.setPrependNewline(prependNewLineCheckBox.isSelected());
    X3dEditUserPreferences.setAppendNewline  (appendNewLineCheckBox.isSelected());

    X3dEditUserPreferences.setAutoValidate(autoValidationCheckBox.isSelected());
    X3dEditUserPreferences.setVisualizeCoordinateAxes(coordinateAxesCheckBox.isSelected());
    X3dEditUserPreferences.setVisualizeCenterLine(coneCenterLineCheckBox.isSelected());
    X3dEditUserPreferences.setVisualizeConeLines(coneLinesComboBox.getSelectedItem().toString());
    // TODO error checks
    X3dEditUserPreferences.setVisualizeLineColorRed   (lineColorRedTF.getText());
    X3dEditUserPreferences.setVisualizeLineColorGreen (lineColorGreenTF.getText());
    X3dEditUserPreferences.setVisualizeLineColorBlue  (lineColorBlueTF.getText());
    X3dEditUserPreferences.setVisualizeShapeColorRed  (shapeColorRedTF.getText());
    X3dEditUserPreferences.setVisualizeShapeColorGreen(shapeColorGreenTF.getText());
    X3dEditUserPreferences.setVisualizeShapeColorBlue (shapeColorBlueTF.getText());
    X3dEditUserPreferences.setVisualizeTransparency   (transparencyTF.getText());

    X3dEditUserPreferences.setVisualizeHanimCoordinateAxes   (hAnimVisualizeCoordinateAxesCheckBox.isSelected());
    X3dEditUserPreferences.setVisualizeHanimJointColorRed    (hAnimJointColorRedTF.getText());
    X3dEditUserPreferences.setVisualizeHanimJointColorGreen  (hAnimJointColorGreenTF.getText());
    X3dEditUserPreferences.setVisualizeHanimJointColorBlue   (hAnimJointColorBlueTF.getText());
    X3dEditUserPreferences.setVisualizeHanimSegmentColorRed  (hAnimSegmentColorRedTF.getText());
    X3dEditUserPreferences.setVisualizeHanimSegmentColorGreen(hAnimSegmentColorGreenTF.getText());
    X3dEditUserPreferences.setVisualizeHanimSegmentColorBlue (hAnimSegmentColorBlueTF.getText());
    X3dEditUserPreferences.setVisualizeHanimSiteColorRed     (hAnimSiteColorRedTF.getText());
    X3dEditUserPreferences.setVisualizeHanimSiteColorGreen   (hAnimSiteColorGreenTF.getText());
    X3dEditUserPreferences.setVisualizeHanimSiteColorBlue    (hAnimSiteColorBlueTF.getText());
  }

  boolean valid()
  {
    // TODO check whether form is consistent and complete
    return true;
  }

  protected void openInBrowser(String urlString)
  {
    try {
      // HtmlBrowser.URLDisplayer.getDefault().showURL(new URL(urlString));
      
    // https://stackoverflow.com/questions/5226212/how-to-open-the-default-webbrowser-using-java
    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
        Desktop.getDesktop().browse(new URI(urlString.replaceAll("\\\\","/")));
    }
    catch (MalformedURLException e) {
      System.err.println(NbBundle.getMessage(getClass(),"Trying_to_display_") + urlString +
                         NbBundle.getMessage(getClass(),"_in_HtmlBrowser:_") + e.getLocalizedMessage());
    } catch (IOException | URISyntaxException ex) {
          Exceptions.printStackTrace(ex);
      }
  }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel additionalKeystoreManagersLabel;
    private javax.swing.JCheckBox altovaXMLSpyCheckBox;
    private javax.swing.JButton altovaXMLSpyChooserButton;
    private javax.swing.JButton altovaXMLSpyDefaultButton;
    private javax.swing.JButton altovaXMLSpyDownloadButton;
    private javax.swing.JButton altovaXMLSpyHelpButton;
    private javax.swing.JLabel altovaXMLSpyLabel;
    private javax.swing.JButton altovaXMLSpyLaunchButton;
    private javax.swing.JTextField altovaXMLSpyTF;
    private javax.swing.JCheckBox amayaEditorCheckBox;
    private javax.swing.JButton amayaEditorChooserButton;
    private javax.swing.JButton amayaEditorDefaultButton;
    private javax.swing.JButton amayaEditorDownloadButton;
    private javax.swing.JButton amayaEditorHelpButton;
    private javax.swing.JLabel amayaEditorLabel;
    private javax.swing.JButton amayaEditorLaunchButton;
    private javax.swing.JTextField amayaEditorPathTF;
    private javax.swing.JCheckBox appendNewLineCheckBox;
    private javax.swing.JCheckBox audacityEditorCheckBox;
    private javax.swing.JButton audacityEditorChooserButton;
    private javax.swing.JButton audacityEditorDefaultButton;
    private javax.swing.JButton audacityEditorDownloadButton;
    private javax.swing.JButton audacityEditorHelpButton;
    private javax.swing.JLabel audacityEditorLabel;
    private javax.swing.JButton audacityEditorLaunchButton;
    private javax.swing.JTextField audacityEditorPathTF;
    private javax.swing.JLabel audioToolsLabel;
    private javax.swing.JButton authorEmailClearButton;
    private javax.swing.JLabel authorEmailLabel;
    private javax.swing.JTextField authorEmailTextField;
    private javax.swing.JButton authorExamplesDirectoryButton;
    private javax.swing.JButton authorExamplesDirectoryClearButton;
    private javax.swing.JButton authorExamplesDirectoryDefaultButton;
    private javax.swing.JLabel authorExamplesDirectoryDescriptionLabel1;
    private javax.swing.JLabel authorExamplesDirectoryDescriptionLabel2;
    private javax.swing.JTextField authorExamplesDirectoryTF;
    private javax.swing.JLabel authorExamplesLocationLabel;
    private javax.swing.JButton authorNameClearButton;
    private javax.swing.JLabel authorNameLabel;
    private javax.swing.JTextField authorNameTextField;
    private javax.swing.JPanel authorSettingsPanel;
    private javax.swing.JCheckBox autoValidationCheckBox;
    private javax.swing.JLabel axesOriginLabel;
    private javax.swing.JCheckBox batikEditorCheckBox;
    private javax.swing.JButton batikEditorChooserButton;
    private javax.swing.JButton batikEditorDefaultButton;
    private javax.swing.JButton batikEditorDownloadButton;
    private javax.swing.JButton batikEditorHelpButton;
    private javax.swing.JLabel batikEditorLabel;
    private javax.swing.JButton batikEditorLaunchButton;
    private javax.swing.JTextField batikEditorPathTF;
    private javax.swing.JCheckBox blenderX3dEditorCheckBox;
    private javax.swing.JButton blenderX3dEditorChooserButton;
    private javax.swing.JButton blenderX3dEditorDefaultButton;
    private javax.swing.JButton blenderX3dEditorDownloadButton;
    private javax.swing.JButton blenderX3dEditorHelpButton;
    private javax.swing.JLabel blenderX3dEditorLabel;
    private javax.swing.JButton blenderX3dEditorLaunchButton;
    private javax.swing.JTextField blenderX3dEditorPathTF;
    private javax.swing.JCheckBox bsContentStudioX3dEditorCheckBox;
    private javax.swing.JButton bsContentStudioX3dEditorChooserButton;
    private javax.swing.JButton bsContentStudioX3dEditorDefaultButton;
    private javax.swing.JButton bsContentStudioX3dEditorDownloadButton;
    private javax.swing.JButton bsContentStudioX3dEditorHelpButton;
    private javax.swing.JLabel bsContentStudioX3dEditorLabel;
    private javax.swing.JButton bsContentStudioX3dEditorLaunchButton;
    private javax.swing.JTextField bsContentStudioX3dEditorPathTF;
    private javax.swing.JCheckBox bvhackerEditorCheckBox;
    private javax.swing.JButton bvhackerEditorChooserButton;
    private javax.swing.JButton bvhackerEditorDefaultButton;
    private javax.swing.JButton bvhackerEditorDownloadButton;
    private javax.swing.JButton bvhackerEditorHelpButton;
    private javax.swing.JLabel bvhackerEditorLabel;
    private javax.swing.JButton bvhackerEditorLaunchButton;
    private javax.swing.JTextField bvhackerEditorPathTF;
    private javax.swing.JCheckBox coneCenterLineCheckBox;
    private javax.swing.JLabel coneLabel;
    private javax.swing.JComboBox<String> coneLinesComboBox;
    private javax.swing.JLabel coneLinesLabel;
    private javax.swing.JCheckBox contactCheckBox;
    private javax.swing.JButton contactChooserButton;
    private javax.swing.JButton contactDefaultButton;
    private javax.swing.JButton contactDownloadButton;
    private javax.swing.JCheckBox contactGeoCheckBox;
    private javax.swing.JButton contactGeoChooserButton;
    private javax.swing.JButton contactGeoDefaultButton;
    private javax.swing.JButton contactGeoDownloadButton;
    private javax.swing.JLabel contactGeoLabel;
    private javax.swing.JButton contactGeoLaunchButton;
    private javax.swing.JTextField contactGeoTF;
    private javax.swing.JLabel contactLabel;
    private javax.swing.JButton contactLaunchButton;
    private javax.swing.JTextField contactTF;
    private javax.swing.JCheckBox coordinateAxesCheckBox;
    private javax.swing.JCheckBox curaX3dEditorCheckBox;
    private javax.swing.JButton curaX3dEditorChooserButton;
    private javax.swing.JButton curaX3dEditorDefaultButton;
    private javax.swing.JButton curaX3dEditorDownloadButton;
    private javax.swing.JButton curaX3dEditorHelpButton;
    private javax.swing.JLabel curaX3dEditorLabel;
    private javax.swing.JButton curaX3dEditorLaunchButton;
    private javax.swing.JTextField curaX3dEditorPathTF;
    private javax.swing.JButton defaultEditorOptionsButton;
    private javax.swing.JButton defaultVisualizationSettingsButton;
    private javax.swing.JLabel defunctX3dEditorLabel;
    private javax.swing.JButton downloadLocalExamplesArchivesButton;
    private javax.swing.JLabel externalOntologyEditorLabel;
    private javax.swing.JLabel externalVideoEditorLabel;
    private javax.swing.JLabel externalVolumeEditorLabel;
    private javax.swing.JLabel externalX3dEditorLabel;
    private javax.swing.JLabel externalX3dEditorLabel1;
    private javax.swing.JCheckBox fijiCheckBox;
    private javax.swing.JButton fijiEditorChooserButton;
    private javax.swing.JButton fijiEditorDefaultButton;
    private javax.swing.JButton fijiEditorDownloadButton;
    private javax.swing.JLabel fijiEditorLabel;
    private javax.swing.JButton fijiEditorLaunchButton;
    private javax.swing.JTextField fijiEditorTF;
    private javax.swing.JButton fijiHelpButton;
    private javax.swing.JLabel fijiImageJvolumeHintLabel;
    private javax.swing.JCheckBox freeWrlCheckBox;
    private javax.swing.JButton freeWrlChooserButton;
    private javax.swing.JButton freeWrlDefaultButton;
    private javax.swing.JButton freeWrlDownloadButton;
    private javax.swing.JLabel freeWrlLabel;
    private javax.swing.JButton freeWrlLaunchButton;
    private javax.swing.JTextField freeWrlTF;
    private javax.swing.JCheckBox gimpCheckBox;
    private javax.swing.JButton gimpEditorChooserButton;
    private javax.swing.JButton gimpEditorDefaultButton;
    private javax.swing.JButton gimpEditorDownloadButton;
    private javax.swing.JLabel gimpEditorLabel;
    private javax.swing.JButton gimpEditorLaunchButton;
    private javax.swing.JTextField gimpEditorTF;
    private javax.swing.JButton gimpHelpButton;
    private javax.swing.JCheckBox h3dCheckBox;
    private javax.swing.JButton h3dChooserButton;
    private javax.swing.JButton h3dDefaultButton;
    private javax.swing.JButton h3dDownloadButton;
    private javax.swing.JLabel h3dLabel;
    private javax.swing.JButton h3dLaunchButton;
    private javax.swing.JTextField h3dTF;
    private javax.swing.JLabel hAnimAxesOriginLabel;
    private javax.swing.JButton hAnimDefaultVisualizationSettingsButton;
    private javax.swing.JTextField hAnimJointColorBlueTF;
    private net.java.dev.colorchooser.ColorChooser hAnimJointColorChooser;
    private javax.swing.JTextField hAnimJointColorGreenTF;
    private javax.swing.JLabel hAnimJointColorLabel;
    private javax.swing.JTextField hAnimJointColorRedTF;
    private javax.swing.JTextField hAnimSegmentColorBlueTF;
    private net.java.dev.colorchooser.ColorChooser hAnimSegmentColorChooser;
    private javax.swing.JTextField hAnimSegmentColorGreenTF;
    private javax.swing.JLabel hAnimSegmentColorLabel;
    private javax.swing.JTextField hAnimSegmentColorRedTF;
    private javax.swing.JTextField hAnimSiteColorBlueTF;
    private net.java.dev.colorchooser.ColorChooser hAnimSiteColorChooser;
    private javax.swing.JTextField hAnimSiteColorGreenTF;
    private javax.swing.JLabel hAnimSiteColorLabel;
    private javax.swing.JTextField hAnimSiteColorRedTF;
    private javax.swing.JPanel hAnimVisualizationOptionsPanel;
    private javax.swing.JCheckBox hAnimVisualizeCoordinateAxesCheckBox;
    private javax.swing.JCheckBox heilanCheckBox;
    private javax.swing.JButton heilanChooserButton;
    private javax.swing.JButton heilanDefaultButton;
    private javax.swing.JButton heilanDownloadButton;
    private javax.swing.JLabel heilanLabel;
    private javax.swing.JButton heilanLaunchButton;
    private javax.swing.JTextField heilanTF;
    private javax.swing.JLabel horizontalSpacerLabel1;
    private javax.swing.JLabel htmlToolsLabel;
    private javax.swing.JCheckBox imageJCheckBox;
    private javax.swing.JButton imageJEditorChooserButton;
    private javax.swing.JButton imageJEditorDefaultButton;
    private javax.swing.JButton imageJEditorDownloadButton;
    private javax.swing.JLabel imageJEditorLabel;
    private javax.swing.JButton imageJEditorLaunchButton;
    private javax.swing.JTextField imageJEditorTF;
    private javax.swing.JButton imageJHelpButton;
    private javax.swing.JCheckBox imageMagickCheckBox;
    private javax.swing.JButton imageMagickEditorChooserButton;
    private javax.swing.JButton imageMagickEditorDefaultButton;
    private javax.swing.JButton imageMagickEditorDownloadButton;
    private javax.swing.JLabel imageMagickEditorLabel;
    private javax.swing.JButton imageMagickEditorLaunchButton;
    private javax.swing.JTextField imageMagickEditorTF;
    private javax.swing.JButton imageMagickHelpButton;
    private javax.swing.JLabel imageToolsLabel;
    private javax.swing.JPanel imageVolumeToolsPanel;
    private javax.swing.JCheckBox inkscapeEditorCheckBox;
    private javax.swing.JButton inkscapeEditorChooserButton;
    private javax.swing.JButton inkscapeEditorDefaultButton;
    private javax.swing.JButton inkscapeEditorDownloadButton;
    private javax.swing.JButton inkscapeEditorHelpButton;
    private javax.swing.JLabel inkscapeEditorLabel;
    private javax.swing.JButton inkscapeEditorLaunchButton;
    private javax.swing.JTextField inkscapeEditorPathTF;
    private javax.swing.JCheckBox instantRealityCheckBox;
    private javax.swing.JButton instantRealityChooserButton;
    private javax.swing.JButton instantRealityDefaultButton;
    private javax.swing.JButton instantRealityDownloadButton;
    private javax.swing.JLabel instantRealityLabel;
    private javax.swing.JButton instantRealityLaunchButton;
    private javax.swing.JTextField instantRealityTF;
    private javax.swing.JCheckBox itksnapVolumeEditorCheckBox;
    private javax.swing.JButton itksnapVolumeEditorChooserButton;
    private javax.swing.JButton itksnapVolumeEditorDefaultButton;
    private javax.swing.JButton itksnapVolumeEditorDownloadButton;
    private javax.swing.JButton itksnapVolumeEditorHelpButton;
    private javax.swing.JLabel itksnapVolumeEditorLabel;
    private javax.swing.JButton itksnapVolumeEditorLaunchButton;
    private javax.swing.JTextField itksnapVolumeEditorPathTF;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JButton keystoreDefaultButton;
    private javax.swing.JButton keystoreDirectoryButton;
    private javax.swing.JLabel keystoreDirectoryLabel;
    private javax.swing.JTextField keystoreDirectoryTF;
    private javax.swing.JButton keystoreDirectoryTFClearButton;
    private javax.swing.JButton keystoreExplorerPlayerChooserButton;
    private javax.swing.JButton keystoreExplorerPlayerDefaultButton;
    private javax.swing.JButton keystoreExplorerPlayerDownloadButton;
    private javax.swing.JButton keystoreExplorerPlayerHelpButton;
    private javax.swing.JLabel keystoreExplorerPlayerLabel;
    private javax.swing.JButton keystoreExplorerPlayerLaunchButton;
    private javax.swing.JTextField keystoreExplorerPlayerPathTF;
    private javax.swing.JButton keystoreFileNameDefaultButton;
    private javax.swing.JLabel keystoreFileNameLabel;
    private javax.swing.JTextField keystoreFileNameTF;
    private javax.swing.JButton keystoreFileNameTFClearButton;
    private javax.swing.JButton keystoreManageButton;
    private javax.swing.JLabel keystoreManagerDescription1Label;
    private javax.swing.JLabel keystoreManagerDescription2Label2;
    private javax.swing.JButton keystorePasswordDefaultButton;
    private javax.swing.JLabel keystorePasswordLabel;
    private javax.swing.JTextField keystorePasswordTF;
    private javax.swing.JButton keystorePasswordTFClearButton;
    private javax.swing.JLabel keystorePathLabel1;
    private javax.swing.JLabel keystorePathLabel2;
    private javax.swing.JLabel keystoreSectionHeaderLabel;
    private javax.swing.JLabel launchIntervalLabel;
    private javax.swing.JTextField launchIntervalTF;
    private javax.swing.JLabel leftMarginSpacerLabel;
    private javax.swing.JLabel leftMarginSpacerLabel1;
    private javax.swing.JTextField lineColorBlueTF;
    private net.java.dev.colorchooser.ColorChooser lineColorChooser;
    private javax.swing.JTextField lineColorGreenTF;
    private javax.swing.JLabel lineColorLabel;
    private javax.swing.JTextField lineColorRedTF;
    private javax.swing.JCheckBox meshLabX3dEditorCheckBox;
    private javax.swing.JButton meshLabX3dEditorChooserButton;
    private javax.swing.JButton meshLabX3dEditorDefaultButton;
    private javax.swing.JButton meshLabX3dEditorDownloadButton;
    private javax.swing.JButton meshLabX3dEditorHelpButton;
    private javax.swing.JLabel meshLabX3dEditorLabel;
    private javax.swing.JButton meshLabX3dEditorLaunchButton;
    private javax.swing.JTextField meshLabX3dEditorPathTF;
    private javax.swing.JButton newX3dModelsDirectoryButton;
    private javax.swing.JButton newX3dModelsDirectoryClearButton;
    private javax.swing.JButton newX3dModelsDirectoryDefaultButton;
    private javax.swing.JLabel newX3dModelsDirectoryDescriptionLabel1;
    private javax.swing.JLabel newX3dModelsDirectoryDescriptionLabel2;
    private javax.swing.JLabel newX3dModelsDirectoryLocationLabel;
    private javax.swing.JTextField newX3dModelsDirectoryTF;
    private javax.swing.JPanel nodeEditingOptionsPanel;
    private javax.swing.JCheckBox octagaCheckBox;
    private javax.swing.JButton octagaChooserButton;
    private javax.swing.JButton octagaDefaultButton;
    private javax.swing.JButton octagaDownloadButton;
    private javax.swing.JLabel octagaLabel;
    private javax.swing.JButton octagaLaunchButton;
    private javax.swing.JTextField octagaTF;
    private javax.swing.JCheckBox otherAudioEditorCheckBox;
    private javax.swing.JButton otherAudioEditorChooserButton;
    private javax.swing.JButton otherAudioEditorClearButton;
    private javax.swing.JButton otherAudioEditorDownloadButton;
    private javax.swing.JButton otherAudioEditorLaunchButton;
    private javax.swing.JTextField otherAudioEditorNameTF;
    private javax.swing.JTextField otherAudioEditorPathTF;
    private javax.swing.JButton otherChooserButton;
    private javax.swing.JLabel otherEditorNameLabel;
    private javax.swing.JLabel otherEditorPathLabel;
    private javax.swing.JCheckBox otherHtml5EditorCheckBox;
    private javax.swing.JButton otherHtml5EditorChooserButton;
    private javax.swing.JButton otherHtml5EditorClearButton;
    private javax.swing.JButton otherHtml5EditorDownloadButton;
    private javax.swing.JButton otherHtml5EditorLaunchButton;
    private javax.swing.JTextField otherHtml5EditorNameTF;
    private javax.swing.JTextField otherHtml5EditorPathTF;
    private javax.swing.JCheckBox otherImageEditorCheckBox;
    private javax.swing.JButton otherImageEditorChooserButton;
    private javax.swing.JButton otherImageEditorClearButton;
    private javax.swing.JButton otherImageEditorDownloadButton;
    private javax.swing.JButton otherImageEditorLaunchButton;
    private javax.swing.JTextField otherImageEditorNameTF;
    private javax.swing.JTextField otherImageEditorPathTF;
    private javax.swing.JLabel otherPlayerLabel;
    private javax.swing.JLabel otherPlayerNameLabel;
    private javax.swing.JCheckBox otherSemanticWebEditorCheckBox;
    private javax.swing.JButton otherSemanticWebEditorChooserButton;
    private javax.swing.JButton otherSemanticWebEditorClearButton;
    private javax.swing.JButton otherSemanticWebEditorDownloadButton;
    private javax.swing.JButton otherSemanticWebEditorLaunchButton;
    private javax.swing.JTextField otherSemanticWebEditorNameTF;
    private javax.swing.JTextField otherSemanticWebEditorPathTF;
    private javax.swing.JButton otherVideoChooserButton;
    private javax.swing.JCheckBox otherVideoEditorCheckBox;
    private javax.swing.JButton otherVideoEditorClearButton;
    private javax.swing.JButton otherVideoEditorDownloadButton;
    private javax.swing.JButton otherVideoEditorLaunchButton;
    private javax.swing.JTextField otherVideoEditorNameTF;
    private javax.swing.JTextField otherVideoEditorPathTF;
    private javax.swing.JButton otherVolumeChooserButton;
    private javax.swing.JCheckBox otherVolumeEditorCheckBox;
    private javax.swing.JButton otherVolumeEditorClearButton;
    private javax.swing.JButton otherVolumeEditorDownloadButton;
    private javax.swing.JButton otherVolumeEditorLaunchButton;
    private javax.swing.JTextField otherVolumeEditorNameTF;
    private javax.swing.JTextField otherVolumeEditorPathTF;
    private javax.swing.JCheckBox otherX3dEditorCheckBox;
    private javax.swing.JButton otherX3dEditorChooserButton;
    private javax.swing.JButton otherX3dEditorClearButton;
    private javax.swing.JButton otherX3dEditorDownloadButton;
    private javax.swing.JButton otherX3dEditorLaunchButton;
    private javax.swing.JTextField otherX3dEditorNameTF;
    private javax.swing.JTextField otherX3dEditorPathTF;
    private javax.swing.JCheckBox otherX3dPlayerCheckBox;
    private javax.swing.JButton otherX3dPlayerClearButton;
    private javax.swing.JButton otherX3dPlayerDownloadButton;
    private javax.swing.JButton otherX3dPlayerLaunchButton;
    private javax.swing.JTextField otherX3dPlayerNameTF;
    private javax.swing.JTextField otherX3dPlayerPathTF;
    private javax.swing.JCheckBox paraviewX3dEditorCheckBox;
    private javax.swing.JButton paraviewX3dEditorChooserButton;
    private javax.swing.JButton paraviewX3dEditorDefaultButton;
    private javax.swing.JButton paraviewX3dEditorDownloadButton;
    private javax.swing.JButton paraviewX3dEditorHelpButton;
    private javax.swing.JLabel paraviewX3dEditorLabel;
    private javax.swing.JButton paraviewX3dEditorLaunchButton;
    private javax.swing.JTextField paraviewX3dEditorPathTF;
    private javax.swing.JCheckBox polyTransNuGrafEditorCheckBox;
    private javax.swing.JButton polyTransNuGrafEditorChooserButton;
    private javax.swing.JButton polyTransNuGrafEditorDefaultButton;
    private javax.swing.JButton polyTransNuGrafEditorDownloadButton;
    private javax.swing.JButton polyTransNuGrafEditorHelpButton;
    private javax.swing.JLabel polyTransNuGrafEditorLabel;
    private javax.swing.JButton polyTransNuGrafEditorLaunchButton;
    private javax.swing.JTextField polyTransNuGrafEditorPathTF;
    private javax.swing.JButton porteclePlayerChooserButton;
    private javax.swing.JButton porteclePlayerDefaultButton;
    private javax.swing.JButton porteclePlayerDownloadButton;
    private javax.swing.JButton porteclePlayerHelpButton;
    private javax.swing.JLabel porteclePlayerLabel;
    private javax.swing.JButton porteclePlayerLaunchButton;
    private javax.swing.JTextField porteclePlayerPathTF;
    private javax.swing.JCheckBox prependNewLineCheckBox;
    private javax.swing.JCheckBox protegePlayerCheckBox;
    private javax.swing.JButton protegePlayerChooserButton;
    private javax.swing.JButton protegePlayerDefaultButton;
    private javax.swing.JButton protegePlayerDownloadButton;
    private javax.swing.JButton protegePlayerHelpButton;
    private javax.swing.JLabel protegePlayerLabel;
    private javax.swing.JButton protegePlayerLaunchButton;
    private javax.swing.JTextField protegePlayerPathTF;
    private javax.swing.JButton reportAuthorButton;
    private javax.swing.JButton reportImageVolumeToolsButton;
    private javax.swing.JButton reportModelingToolsButton;
    private javax.swing.JButton reportPlayerButton;
    private javax.swing.JButton reportSecurityPanelButton;
    private javax.swing.JButton reportVisualizationPreferencesButton;
    private javax.swing.JButton reportWebMultimediaToolsButton;
    private javax.swing.JCheckBox seamless3dX3dEditorCheckBox;
    private javax.swing.JButton seamless3dX3dEditorChooserButton;
    private javax.swing.JButton seamless3dX3dEditorDefaultButton;
    private javax.swing.JButton seamless3dX3dEditorDownloadButton;
    private javax.swing.JButton seamless3dX3dEditorHelpButton;
    private javax.swing.JLabel seamless3dX3dEditorLabel;
    private javax.swing.JButton seamless3dX3dEditorLaunchButton;
    private javax.swing.JTextField seamless3dX3dEditorPathTF;
    private javax.swing.JLabel secondsLabel;
    private javax.swing.JLabel securityExamplesLabel;
    private javax.swing.JLabel securityResourcesLabel;
    private javax.swing.JCheckBox seg3dVolumeEditorCheckBox;
    private javax.swing.JButton seg3dVolumeEditorChooserButton;
    private javax.swing.JButton seg3dVolumeEditorDefaultButton;
    private javax.swing.JButton seg3dVolumeEditorDownloadButton;
    private javax.swing.JButton seg3dVolumeEditorHelpButton;
    private javax.swing.JLabel seg3dVolumeEditorLabel;
    private javax.swing.JButton seg3dVolumeEditorLaunchButton;
    private javax.swing.JTextField seg3dVolumeEditorPathTF;
    private javax.swing.JTextField shapeColorBlueTF;
    private net.java.dev.colorchooser.ColorChooser shapeColorChooser;
    private javax.swing.JTextField shapeColorGreenTF;
    private javax.swing.JLabel shapeColorLabel;
    private javax.swing.JTextField shapeColorRedTF;
    private javax.swing.JCheckBox showNewLineOptionCheckBox;
    private javax.swing.JCheckBox slicer3dVolumeEditorCheckBox;
    private javax.swing.JButton slicer3dVolumeEditorChooserButton;
    private javax.swing.JButton slicer3dVolumeEditorDefaultButton;
    private javax.swing.JButton slicer3dVolumeEditorDownloadButton;
    private javax.swing.JButton slicer3dVolumeEditorHelpButtonslicer3dVolume;
    private javax.swing.JLabel slicer3dVolumeEditorLabel;
    private javax.swing.JButton slicer3dVolumeEditorLaunchButton;
    private javax.swing.JTextField slicer3dVolumeEditorPathTF;
    private javax.swing.JLabel svgToolHintLabel;
    private javax.swing.JCheckBox svgeditEditorCheckBox;
    private javax.swing.JButton svgeditEditorChooserButton;
    private javax.swing.JButton svgeditEditorDefaultButton;
    private javax.swing.JButton svgeditEditorDownloadButton;
    private javax.swing.JButton svgeditEditorHelpButton;
    private javax.swing.JLabel svgeditEditorLabel;
    private javax.swing.JButton svgeditEditorLaunchButton;
    private javax.swing.JTextField svgeditEditorPathTF;
    private javax.swing.JCheckBox swirlx3dCheckBox;
    private javax.swing.JButton swirlx3dChooserButton;
    private javax.swing.JButton swirlx3dDefaultButton;
    private javax.swing.JButton swirlx3dDownloadButton;
    private javax.swing.JLabel swirlx3dLabel;
    private javax.swing.JButton swirlx3dLaunchButton;
    private javax.swing.JTextField swirlx3dTF;
    private javax.swing.JCheckBox titaniaX3dEditorCheckBox;
    private javax.swing.JButton titaniaX3dEditorChooserButton;
    private javax.swing.JButton titaniaX3dEditorDefaultButton;
    private javax.swing.JButton titaniaX3dEditorDownloadButton;
    private javax.swing.JButton titaniaX3dEditorHelpButton;
    private javax.swing.JLabel titaniaX3dEditorLabel;
    private javax.swing.JButton titaniaX3dEditorLaunchButton;
    private javax.swing.JTextField titaniaX3dEditorPathTF;
    private javax.swing.JLabel transparencyLabel;
    private javax.swing.JTextField transparencyTF;
    private javax.swing.JButton ultraEditHelpButton;
    private javax.swing.JCheckBox ultraEditX3dEditorCheckBox;
    private javax.swing.JButton ultraEditX3dEditorChooserButton;
    private javax.swing.JButton ultraEditX3dEditorDefaultButton;
    private javax.swing.JButton ultraEditX3dEditorDownloadButton;
    private javax.swing.JLabel ultraEditX3dEditorLabel;
    private javax.swing.JButton ultraEditX3dEditorLaunchButton;
    private javax.swing.JTextField ultraEditX3dEditorPathTF;
    private javax.swing.JLabel verticalSpacerLabel;
    private javax.swing.JLabel verticalSpacerLabel1;
    private javax.swing.JLabel verticalSpacerLabel10;
    private javax.swing.JLabel verticalSpacerLabel11;
    private javax.swing.JLabel verticalSpacerLabel12;
    private javax.swing.JLabel verticalSpacerLabel13;
    private javax.swing.JLabel verticalSpacerLabel14;
    private javax.swing.JLabel verticalSpacerLabel16;
    private javax.swing.JLabel verticalSpacerLabel17;
    private javax.swing.JLabel verticalSpacerLabel18;
    private javax.swing.JLabel verticalSpacerLabel19;
    private javax.swing.JLabel verticalSpacerLabel2;
    private javax.swing.JLabel verticalSpacerLabel20;
    private javax.swing.JLabel verticalSpacerLabel21;
    private javax.swing.JLabel verticalSpacerLabel22;
    private javax.swing.JLabel verticalSpacerLabel3;
    private javax.swing.JLabel verticalSpacerLabel4;
    private javax.swing.JLabel verticalSpacerLabel5;
    private javax.swing.JLabel verticalSpacerLabel6;
    private javax.swing.JLabel verticalSpacerLabel7;
    private javax.swing.JLabel verticalSpacerLabel8;
    private javax.swing.JLabel verticalSpacerLabel9;
    private javax.swing.JCheckBox view3dsceneCheckBox;
    private javax.swing.JButton view3dsceneChooserButton;
    private javax.swing.JButton view3dsceneDefaultButton;
    private javax.swing.JButton view3dsceneDownloadButton;
    private javax.swing.JLabel view3dsceneLabel;
    private javax.swing.JButton view3dsceneLaunchButton;
    private javax.swing.JTextField view3dsceneTF;
    private javax.swing.JButton viewSecurityExamplesReadmeButton;
    private javax.swing.JLabel viewSecurityExamplesReadmeLabel;
    private javax.swing.JButton viewX3dCanonicalizationC14nReadmeButton;
    private javax.swing.JLabel viewX3dCanonicalizationC14nReadmeLabel;
    private javax.swing.JButton viewX3dResourcesSecurityButton;
    private javax.swing.JLabel viewX3dResourcesSecurityLabel;
    private javax.swing.JButton viewX3dResourcesSecurityVulnerabilitiesButton;
    private javax.swing.JLabel viewX3dResourcesSecurityVulnerabilitiesLabel;
    private javax.swing.JPanel visualizationOptionsPanel;
    private javax.swing.JCheckBox vivatyCheckBox;
    private javax.swing.JButton vivatyChooserButton;
    private javax.swing.JButton vivatyDefaultButton;
    private javax.swing.JButton vivatyDownloadButton;
    private javax.swing.JLabel vivatyLabel;
    private javax.swing.JButton vivatyLaunchButton;
    private javax.swing.JTextField vivatyTF;
    private javax.swing.JCheckBox vlcPlayerCheckBox;
    private javax.swing.JButton vlcPlayerChooserButton;
    private javax.swing.JButton vlcPlayerDefaultButton;
    private javax.swing.JButton vlcPlayerDownloadButton;
    private javax.swing.JButton vlcPlayerHelpButton;
    private javax.swing.JLabel vlcPlayerLabel;
    private javax.swing.JButton vlcPlayerLaunchButton;
    private javax.swing.JTextField vlcPlayerPathTF;
    private javax.swing.JPanel webMultimediaToolsPanel;
    private javax.swing.JCheckBox whiteDuneX3dEditorCheckBox;
    private javax.swing.JButton whiteDuneX3dEditorChooserButton;
    private javax.swing.JButton whiteDuneX3dEditorDefaultButton;
    private javax.swing.JButton whiteDuneX3dEditorDownloadButton;
    private javax.swing.JButton whiteDuneX3dEditorHelpButton;
    private javax.swing.JLabel whiteDuneX3dEditorLabel;
    private javax.swing.JButton whiteDuneX3dEditorLaunchButton;
    private javax.swing.JTextField whiteDuneX3dEditorPathTF;
    private javax.swing.JCheckBox wings3dX3dEditorCheckBox;
    private javax.swing.JButton wings3dX3dEditorChooserButton;
    private javax.swing.JButton wings3dX3dEditorDefaultButton;
    private javax.swing.JButton wings3dX3dEditorDownloadButton;
    private javax.swing.JButton wings3dX3dEditorHelpButton;
    private javax.swing.JLabel wings3dX3dEditorLabel;
    private javax.swing.JButton wings3dX3dEditorLaunchButton;
    private javax.swing.JTextField wings3dX3dEditorPathTF;
    private javax.swing.JPanel x3dEditVisualizationPreferencesPanel;
    private javax.swing.JPanel x3dModelingToolsPanel;
    private javax.swing.JTabbedPane x3dOptionsTabbedPane;
    private javax.swing.JPanel x3dPlayerPathsPanel;
    private javax.swing.JPanel x3dSecurityPanel;
    private org.web3d.x3d.options.Xj3dCadFilterOptionsPanel xj3dCadFilterOptionsPanel;
    private javax.swing.JCheckBox xj3dCheckBox;
    private javax.swing.JButton xj3dChooserButton;
    private javax.swing.JButton xj3dDefaultButton;
    private javax.swing.JButton xj3dDownloadButton;
    private javax.swing.JLabel xj3dLabel;
    private javax.swing.JButton xj3dLaunchButton;
    private javax.swing.JTextField xj3dTF;
    // End of variables declaration//GEN-END:variables

    /**
     * Set preferred pane
     * @param index the preferredPaneIndex to set
     */
    public void setPreferredPane(int index)
    {
        if (index >= -1)
        {
            preferredPaneIndex = index;
            x3dOptionsTabbedPane.setSelectedIndex(preferredPaneIndex);
            preferredPaneIndex = -1;
        }
    }
    public class keyStoreFileTypeFilter extends FileFilter
    {
        @Override
        public boolean accept(File f)
        {
            if (f.isDirectory())
            {
                return true;
            }
            String extension = FileUtil.toFileObject(f).getExt();
            if (extension != null)
            {
                if (extension.equals("ks")
                        || extension.equals("KS"))
                {
                    return true;
                }
            }
            return false;
        }

        @Override
        public String getDescription()
        {
            return "X3D-Edit User Preferences";
        }
    }
}
