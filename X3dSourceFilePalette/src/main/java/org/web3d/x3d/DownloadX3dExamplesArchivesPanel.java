/*
 * DownloadX3dExamplesArchivesPanel.java
 *
 * Created on January 10, 2008, 4:09 PM
 */
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

package org.web3d.x3d;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Properties;
import javax.swing.JFileChooser;
import org.apache.tools.ant.module.api.support.ActionUtils;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.execution.ExecutorTask;
import org.openide.filesystems.FileLock;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.HelpCtx;
import org.openide.util.Task;
import org.openide.util.TaskListener;
import static org.web3d.x3d.actions.BaseViewAction.sendBrowserTo;
import org.web3d.x3d.actions.LaunchX3dExamplesAction;
import org.web3d.x3d.actions.LocalExamplesFinder;
import org.web3d.x3d.actions.conversions.X3dToXhtmlDomConversionAction;
import org.web3d.x3d.actions.conversions.X3dToXhtmlDomConversionFrame;
import static org.web3d.x3d.actions.conversions.X3dToXhtmlDomConversionFrame.startExampleArchivesServer;
import org.web3d.x3d.options.X3dEditUserPreferences;
import static org.web3d.x3d.options.X3dEditUserPreferences.EXAMPLES_ROOT_DIRECTORY_DEFAULT;

/**
 *
 * @author  Mike Bailey <jmbailey@nps.edu>
 */
public class DownloadX3dExamplesArchivesPanel extends javax.swing.JPanel
{
    private static final String BASICEXAMPLESTARGET        = "Basic";
    private static final String CONFORMANCENISTTARGET      = "ConformanceNist"; // note capitalization
    private static final String HUMANOIDANIMATIONTARGET    = "HumanoidAnimation";
    private static final String VRML2SOURCEBOOKTARGET      = "Vrml2Sourcebook";
    private static final String SAVAGETARGET               = "Savage";
    private static final String SAVAGEDEFENSETARGET        = "SavageDefense";   // private NPS
    private static final String X3D4WA_EXAMPLESTARGET      = "X3dForWebAuthors";
    private static final String X3D4AM_EXAMPLESTARGET      = "X3dForAdvancedModeling";
    private static      boolean anyArchivePresent          = false;   
    private             boolean anyArchivePresentInitially = false;   

    private final String DEFAULTROOTDIR = System.getProperty("user.dir"); // "/";
    private final String targetPath     = "www.web3d.org/x3d/content/examples/";
    private final String antScriptPath  = "X3dAntScripts/examplesDownloaderAntScript.xml";

    private       String localArchiveDirectory = ""; // not root but below, if available
  
    Color   black      = new Color(  0,   0,  0);
    Color   darkgreen  = new Color( 21,  71, 52);
    Color   red        = new Color(153,   0,  0);
    Font plainFont;
    Font  boldFont;
    private JFileChooser fileChooser;
    private ExecutorTask executorTask;
    
    // create local X3dToXhtmlDomConversionFrame class, instantiated so that static methods can run
    // TODO consider singleton pattern?
    X3dToXhtmlDomConversionFrame x3dToXhtmlDomConversionFrame = new X3dToXhtmlDomConversionFrame(new X3dToXhtmlDomConversionAction());
  
  /** Constructor that creates new form ExampleArchivesDownloadPanel */
  public DownloadX3dExamplesArchivesPanel()
  {
    initComponents();
    HelpCtx.setHelpIDString(DownloadX3dExamplesArchivesPanel.this, "helpExampleDownloads");
    
    plainFont = x3d4waExamplesCB.getFont().deriveFont(Font.PLAIN);
     boldFont = plainFont.deriveFont(Font.BOLD);

    // figure out root directory
    String originalRootDirectory = X3dEditUserPreferences.getExampleArchivesRootDirectory();
    String workingRootDirectory  = X3dEditUserPreferences.getExampleArchivesRootDirectory();
    if  (workingRootDirectory.contains("\\"))
         workingRootDirectory = workingRootDirectory.replace("\\","/");
    if  (workingRootDirectory.endsWith("/"))
         workingRootDirectory = workingRootDirectory.substring(0, workingRootDirectory.length() -1);
    if  (workingRootDirectory.endsWith("www.web3d.org/x3d/content/examples") ||
         workingRootDirectory.endsWith("www.web3d.org\\x3d\\content\\examples"))
    {
         workingRootDirectory = originalRootDirectory.substring(0, originalRootDirectory.indexOf("www.web3d.org") - 1);
         X3dEditUserPreferences.setExampleArchivesRootDirectory(workingRootDirectory);
         rootDownloadDirectoryTF.setText(workingRootDirectory);
    }
    else rootDownloadDirectoryTF.setText(X3dEditUserPreferences.getExampleArchivesRootDirectory());
    
    if (rootDownloadDirectoryTF.getText().isBlank())
    {
        startDownloadButton.setEnabled(false);
    }
    else
    {
        System.out.println("*** DownloadX3dExamplesArchivesPanel ExampleArchivesRootDirectory=" + X3dEditUserPreferences.getExampleArchivesRootDirectory());
    }
    
    
    downloadDirectoryLabelUpdate (); // initialize
    downloadDirectoryOpenButton.setEnabled(Desktop.isDesktopSupported());
    
    progressHintLabel.setVisible(false);
     
    updateStatusPropertiesLocalArchivesPresent(); // also updates anyArchivePresent
    anyArchivePresentInitially = anyArchivePresent;
    updateStatusPropertiesLocalArchivesPresent();
    updatePanelLocalArchivesPresent();
  }
  
  /* Update status of all local archives */
  private void updatePanelLocalArchivesPresent()
  {
        if  (isLocalArchivePresent(X3D4WA_EXAMPLESTARGET))
        {
            x3d4waExamplesCB.setForeground(darkgreen);
            x3d4waExamplesCB.setFont(boldFont);
        }
        else 
        {
            x3d4waExamplesCB.setForeground(black);
            x3d4waExamplesCB.setFont(plainFont);
        }
        if  (isLocalArchivePresent(X3D4AM_EXAMPLESTARGET))
        {
            x3d4amExamplesCB.setForeground(darkgreen);
            x3d4amExamplesCB.setFont(boldFont);
        }
        else 
        {
            x3d4amExamplesCB.setForeground(black);
            x3d4amExamplesCB.setFont(plainFont);
        }
        if  (isLocalArchivePresent(VRML2SOURCEBOOKTARGET))
        {
            vrmlSourcebookCB.setForeground(darkgreen);
            vrmlSourcebookCB.setFont(boldFont);
        }
        else 
        {
            vrmlSourcebookCB.setForeground(black);
            vrmlSourcebookCB.setFont(plainFont);
        }
        if  (isLocalArchivePresent(BASICEXAMPLESTARGET))
        {
            basicExamplesCB.setForeground(darkgreen);
            basicExamplesCB.setFont(boldFont);
        }
        else 
        {
            basicExamplesCB.setForeground(black);
            basicExamplesCB.setFont(plainFont);
        }
        if  (isLocalArchivePresent(CONFORMANCENISTTARGET))
        {
            conformanceCB.setForeground(darkgreen);
            conformanceCB.setFont(boldFont);
        }
        else 
        {
            conformanceCB.setForeground(black);
            conformanceCB.setFont(plainFont);
        }
        if  (isLocalArchivePresent(HUMANOIDANIMATIONTARGET))
        {
            humanoidAnimationCB.setForeground(darkgreen);
            humanoidAnimationCB.setFont(boldFont);
        }
        else
        {
            humanoidAnimationCB.setForeground(black);
            humanoidAnimationCB.setFont(plainFont);
        }
        if  (isLocalArchivePresent(SAVAGETARGET))
        {
            savageCB.setForeground(darkgreen);
            savageCB.setFont(boldFont);
        }
        else 
        {
            savageCB.setForeground(black);
            savageCB.setFont(plainFont);
        }
        // preceding isLocalArchivePresent() checks set the property value too
                 x3d4waExamplesBrowserViewButton.setEnabled(X3dEditUserPreferences.getX3d4waLocalExamplesPresent());
               x3d4waExamplesDirectoryOpenButton.setEnabled(X3dEditUserPreferences.getX3d4waLocalExamplesPresent());
                 x3d4amExamplesBrowserViewButton.setEnabled(X3dEditUserPreferences.getX3d4amLocalExamplesPresent());
               x3d4amExamplesDirectoryOpenButton.setEnabled(X3dEditUserPreferences.getX3d4amLocalExamplesPresent());
         vrmlSourcebookExamplesBrowserViewButton.setEnabled(X3dEditUserPreferences.getVrmlSourcebookLocalExamplesPresent());
       vrmlSourcebookExamplesDirectoryOpenButton.setEnabled(X3dEditUserPreferences.getVrmlSourcebookLocalExamplesPresent());
                  basicExamplesBrowserViewButton.setEnabled(X3dEditUserPreferences.getBasicLocalExamplesPresent());
                basicExamplesDirectoryOpenButton.setEnabled(X3dEditUserPreferences.getBasicLocalExamplesPresent());
            conformanceExamplesBrowserViewButton.setEnabled(X3dEditUserPreferences.getConformanceNistLocalExamplesPresent());
          conformanceExamplesDirectoryOpenButton.setEnabled(X3dEditUserPreferences.getConformanceNistLocalExamplesPresent());
      humanoidAnimationExamplesBrowserViewButton.setEnabled(X3dEditUserPreferences.getHumanoidAnimationLocalExamplesPresent());
    humanoidAnimationExamplesDirectoryOpenButton.setEnabled(X3dEditUserPreferences.getHumanoidAnimationLocalExamplesPresent());
                 savageExamplesBrowserViewButton.setEnabled(X3dEditUserPreferences.getSavageLocalExamplesPresent());
               savageExamplesDirectoryOpenButton.setEnabled(X3dEditUserPreferences.getSavageLocalExamplesPresent());
  }
  
  /* Update status of all local archives */
  public static void updateStatusPropertiesLocalArchivesPresent()
  {
    anyArchivePresent =
    (
        // each of these checks also performs a persistent property update
        isLocalArchivePresent(BASICEXAMPLESTARGET    ) ||
        isLocalArchivePresent(CONFORMANCENISTTARGET  ) ||
        isLocalArchivePresent(HUMANOIDANIMATIONTARGET) ||
        isLocalArchivePresent(VRML2SOURCEBOOKTARGET  ) ||
        isLocalArchivePresent(SAVAGETARGET           ) ||
        isLocalArchivePresent(X3D4WA_EXAMPLESTARGET  ) ||
        isLocalArchivePresent(X3D4AM_EXAMPLESTARGET  )
    );
  }
  /** Checks presence and sets persistent variable
     * @param archiveName name of archive
     * @return whether local copy of archive is present */
  public static boolean isLocalArchivePresent(String archiveName)
  {
      boolean archivePresent;
      if      (archiveName.equals(BASICEXAMPLESTARGET))
      {
          archivePresent = (new File(X3dEditUserPreferences.getExampleArchivesRootDirectory() + File.separator + 
                            BASICEXAMPLESTARGET + File.separator + "HelloWorld.x3d")).exists();
          X3dEditUserPreferences.setBasicLocalExamplesPresent(archivePresent);
          return archivePresent;
      }
      else if (archiveName.equals(CONFORMANCENISTTARGET))
      {
          
          archivePresent = ((new File(X3dEditUserPreferences.getExampleArchivesRootDirectory() + File.separator + 
                            CONFORMANCENISTTARGET + File.separator + "HelloWorld.x3d")).exists());
          X3dEditUserPreferences.setConformanceNistLocalExamplesPresent(archivePresent);
          return archivePresent;
      }
      else if (archiveName.equals(HUMANOIDANIMATIONTARGET))
      {
          
          archivePresent = ((new File(X3dEditUserPreferences.getExampleArchivesRootDirectory() + File.separator + 
                            HUMANOIDANIMATIONTARGET + File.separator + "HelloWorld.x3d")).exists());
          X3dEditUserPreferences.setHumanoidAnimationLocalExamplesPresent(archivePresent);
          return archivePresent;
      }
      else if (archiveName.equals(VRML2SOURCEBOOKTARGET))
      {
          archivePresent = ((new File(X3dEditUserPreferences.getExampleArchivesRootDirectory() + File.separator + 
                            VRML2SOURCEBOOKTARGET + File.separator + "HelloWorld.x3d")).exists());
          X3dEditUserPreferences.setVrmlSourcebookLocalExamplesPresent(archivePresent);
          return archivePresent;
      }
      else if (archiveName.equals(SAVAGETARGET))
      {
          archivePresent = ((new File(X3dEditUserPreferences.getExampleArchivesRootDirectory() + File.separator + 
                            SAVAGETARGET + File.separator + "HelloWorld.x3d")).exists());
          X3dEditUserPreferences.setSavageLocalExamplesPresent(archivePresent);
          return archivePresent;
      }
      else if (archiveName.equals(X3D4WA_EXAMPLESTARGET))
      {
          archivePresent = ((new File(X3dEditUserPreferences.getExampleArchivesRootDirectory() + File.separator + 
                            X3D4WA_EXAMPLESTARGET + File.separator + "HelloWorld.x3d")).exists());
          X3dEditUserPreferences.setX3d4waLocalExamplesPresent(archivePresent);
          return archivePresent;
      }
      else if (archiveName.equals(X3D4AM_EXAMPLESTARGET))
      {
          archivePresent = ((new File(X3dEditUserPreferences.getExampleArchivesRootDirectory() + File.separator + 
                            X3D4AM_EXAMPLESTARGET + File.separator + "HelloWorld.x3d")).exists());
          X3dEditUserPreferences.setX3d4amLocalExamplesPresent(archivePresent);
          return archivePresent;
      }
      else
      {
          System.err.println("*** " + archiveName + " is not a valid X3D Examples Archive name");
          return false;
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

        x3d4waExamplesCB = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        x3dForWebAuthorsTA = new javax.swing.JTextArea();
        x3d4waExamplesBrowserViewButton = new javax.swing.JButton();
        x3d4waExamplesDirectoryOpenButton = new javax.swing.JButton();
        x3d4amExamplesCB = new javax.swing.JCheckBox();
        jScrollPane6 = new javax.swing.JScrollPane();
        x3dForAdvancedModelingTA = new javax.swing.JTextArea();
        x3d4amExamplesBrowserViewButton = new javax.swing.JButton();
        x3d4amExamplesDirectoryOpenButton = new javax.swing.JButton();
        vrmlSourcebookCB = new javax.swing.JCheckBox();
        jScrollPane4 = new javax.swing.JScrollPane();
        vrmlTA = new javax.swing.JTextArea();
        vrmlSourcebookExamplesBrowserViewButton = new javax.swing.JButton();
        vrmlSourcebookExamplesDirectoryOpenButton = new javax.swing.JButton();
        basicExamplesCB = new javax.swing.JCheckBox();
        basicExamplesBrowserViewButton = new javax.swing.JButton();
        basicExamplesDirectoryOpenButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        basicExamplesTA = new javax.swing.JTextArea();
        conformanceCB = new javax.swing.JCheckBox();
        jScrollPane7 = new javax.swing.JScrollPane();
        conformanceTA1 = new javax.swing.JTextArea();
        conformanceExamplesBrowserViewButton = new javax.swing.JButton();
        conformanceExamplesDirectoryOpenButton = new javax.swing.JButton();
        humanoidAnimationCB = new javax.swing.JCheckBox();
        jScrollPane8 = new javax.swing.JScrollPane();
        hanimTextArea = new javax.swing.JTextArea();
        humanoidAnimationExamplesBrowserViewButton = new javax.swing.JButton();
        humanoidAnimationExamplesDirectoryOpenButton = new javax.swing.JButton();
        savageCB = new javax.swing.JCheckBox();
        jScrollPane5 = new javax.swing.JScrollPane();
        savageTA = new javax.swing.JTextArea();
        savageExamplesBrowserViewButton = new javax.swing.JButton();
        savageExamplesDirectoryOpenButton = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        downLoadLab = new javax.swing.JLabel();
        rootDownloadDirectoryTF = new javax.swing.JTextField();
        rootDownloadDirectoryChooserButton = new javax.swing.JButton();
        rootDownloadDirectoryDefaultButton = new javax.swing.JButton();
        downloadDirectoryNoteLabel = new javax.swing.JLabel();
        downloadDirectoryLabel = new javax.swing.JLabel();
        downloadDirectoryOpenButton = new javax.swing.JButton();
        startDownloadButton = new javax.swing.JButton();
        cancelDownloadButton = new javax.swing.JButton();
        progressHintLabel = new javax.swing.JLabel();
        refreshDownloadPanelButton = new javax.swing.JButton();
        allSelectCheckBox = new javax.swing.JCheckBox();
        allClearCheckBox = new javax.swing.JCheckBox();

        setBorder(javax.swing.BorderFactory.createEtchedBorder());
        setPreferredSize(new java.awt.Dimension(900, 520));
        setLayout(new java.awt.GridBagLayout());

        x3d4waExamplesCB.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.x3d4waExamplesCB.text")); // NOI18N
        x3d4waExamplesCB.setToolTipText("select to download latest archive");
        x3d4waExamplesCB.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                x3d4waExamplesCBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(x3d4waExamplesCB, gridBagConstraints);

        jScrollPane1.setMaximumSize(new java.awt.Dimension(120, 24));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(120, 24));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(120, 24));

        x3dForWebAuthorsTA.setEditable(false);
        x3dForWebAuthorsTA.setColumns(20);
        x3dForWebAuthorsTA.setLineWrap(true);
        x3dForWebAuthorsTA.setRows(2);
        x3dForWebAuthorsTA.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.x3dForWebAuthorsTA.text")); // NOI18N
        x3dForWebAuthorsTA.setWrapStyleWord(true);
        x3dForWebAuthorsTA.setAutoscrolls(false);
        x3dForWebAuthorsTA.setMaximumSize(new java.awt.Dimension(80, 400));
        x3dForWebAuthorsTA.setMinimumSize(new java.awt.Dimension(80, 400));
        x3dForWebAuthorsTA.setPreferredSize(new java.awt.Dimension(80, 400));
        jScrollPane1.setViewportView(x3dForWebAuthorsTA);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 0.4;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 0);
        add(jScrollPane1, gridBagConstraints);

        x3d4waExamplesBrowserViewButton.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.x3d4waExamplesBrowserViewButton.text")); // NOI18N
        x3d4waExamplesBrowserViewButton.setToolTipText("view local archive in Web browser");
        x3d4waExamplesBrowserViewButton.setMargin(null);
        x3d4waExamplesBrowserViewButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                x3d4waExamplesBrowserViewButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 32;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(x3d4waExamplesBrowserViewButton, gridBagConstraints);

        x3d4waExamplesDirectoryOpenButton.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.x3d4waExamplesDirectoryOpenButton.text")); // NOI18N
        x3d4waExamplesDirectoryOpenButton.setToolTipText("open directory");
        x3d4waExamplesDirectoryOpenButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                x3d4waExamplesDirectoryOpenButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 32;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 3, 3);
        add(x3d4waExamplesDirectoryOpenButton, gridBagConstraints);

        x3d4amExamplesCB.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.x3d4amExamplesCB.text")); // NOI18N
        x3d4amExamplesCB.setToolTipText("select to download latest archive");
        x3d4amExamplesCB.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                x3d4amExamplesCBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(x3d4amExamplesCB, gridBagConstraints);

        jScrollPane6.setMaximumSize(new java.awt.Dimension(120, 24));
        jScrollPane6.setMinimumSize(new java.awt.Dimension(120, 24));
        jScrollPane6.setPreferredSize(new java.awt.Dimension(120, 24));

        x3dForAdvancedModelingTA.setEditable(false);
        x3dForAdvancedModelingTA.setColumns(20);
        x3dForAdvancedModelingTA.setLineWrap(true);
        x3dForAdvancedModelingTA.setRows(2);
        x3dForAdvancedModelingTA.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.x3dForAdvancedModelingTA.text")); // NOI18N
        x3dForAdvancedModelingTA.setWrapStyleWord(true);
        x3dForAdvancedModelingTA.setAutoscrolls(false);
        x3dForAdvancedModelingTA.setMaximumSize(new java.awt.Dimension(80, 400));
        x3dForAdvancedModelingTA.setMinimumSize(new java.awt.Dimension(80, 400));
        x3dForAdvancedModelingTA.setPreferredSize(new java.awt.Dimension(80, 400));
        jScrollPane6.setViewportView(x3dForAdvancedModelingTA);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 0.4;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 0);
        add(jScrollPane6, gridBagConstraints);

        x3d4amExamplesBrowserViewButton.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.x3d4amExamplesBrowserViewButton.text")); // NOI18N
        x3d4amExamplesBrowserViewButton.setToolTipText("view local archive in Web browser");
        x3d4amExamplesBrowserViewButton.setMargin(null);
        x3d4amExamplesBrowserViewButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                x3d4amExamplesBrowserViewButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 32;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(x3d4amExamplesBrowserViewButton, gridBagConstraints);

        x3d4amExamplesDirectoryOpenButton.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.x3d4amExamplesDirectoryOpenButton.text")); // NOI18N
        x3d4amExamplesDirectoryOpenButton.setToolTipText("open directory");
        x3d4amExamplesDirectoryOpenButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                x3d4amExamplesDirectoryOpenButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 32;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 3, 3);
        add(x3d4amExamplesDirectoryOpenButton, gridBagConstraints);

        vrmlSourcebookCB.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.vrmlSourcebookCB.text")); // NOI18N
        vrmlSourcebookCB.setToolTipText("select to download latest archive");
        vrmlSourcebookCB.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                vrmlSourcebookCBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(vrmlSourcebookCB, gridBagConstraints);

        jScrollPane4.setMaximumSize(new java.awt.Dimension(120, 24));
        jScrollPane4.setMinimumSize(new java.awt.Dimension(120, 24));
        jScrollPane4.setPreferredSize(new java.awt.Dimension(120, 24));

        vrmlTA.setEditable(false);
        vrmlTA.setColumns(20);
        vrmlTA.setLineWrap(true);
        vrmlTA.setRows(2);
        vrmlTA.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.vrmlTA.text")); // NOI18N
        vrmlTA.setWrapStyleWord(true);
        vrmlTA.setAutoscrolls(false);
        vrmlTA.setMaximumSize(new java.awt.Dimension(80, 400));
        vrmlTA.setMinimumSize(new java.awt.Dimension(80, 400));
        vrmlTA.setPreferredSize(new java.awt.Dimension(80, 400));
        jScrollPane4.setViewportView(vrmlTA);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 0.4;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 0);
        add(jScrollPane4, gridBagConstraints);

        vrmlSourcebookExamplesBrowserViewButton.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.vrmlSourcebookExamplesBrowserViewButton.text")); // NOI18N
        vrmlSourcebookExamplesBrowserViewButton.setToolTipText("view local archive in Web browser");
        vrmlSourcebookExamplesBrowserViewButton.setMargin(null);
        vrmlSourcebookExamplesBrowserViewButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                vrmlSourcebookExamplesBrowserViewButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 32;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(vrmlSourcebookExamplesBrowserViewButton, gridBagConstraints);

        vrmlSourcebookExamplesDirectoryOpenButton.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.vrmlSourcebookExamplesDirectoryOpenButton.text")); // NOI18N
        vrmlSourcebookExamplesDirectoryOpenButton.setToolTipText("open directory");
        vrmlSourcebookExamplesDirectoryOpenButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                vrmlSourcebookExamplesDirectoryOpenButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 32;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 3, 3);
        add(vrmlSourcebookExamplesDirectoryOpenButton, gridBagConstraints);

        basicExamplesCB.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.basicExamplesCB.text")); // NOI18N
        basicExamplesCB.setToolTipText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.basicExamplesCB.toolTipText")); // NOI18N
        basicExamplesCB.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                basicExamplesCBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(basicExamplesCB, gridBagConstraints);

        basicExamplesBrowserViewButton.setText("view");
        basicExamplesBrowserViewButton.setToolTipText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.basicExamplesBrowserViewButton.toolTipText")); // NOI18N
        basicExamplesBrowserViewButton.setMargin(null);
        basicExamplesBrowserViewButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                basicExamplesBrowserViewButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 32;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(basicExamplesBrowserViewButton, gridBagConstraints);

        basicExamplesDirectoryOpenButton.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.basicExamplesDirectoryOpenButton.text")); // NOI18N
        basicExamplesDirectoryOpenButton.setToolTipText("open directory");
        basicExamplesDirectoryOpenButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                basicExamplesDirectoryOpenButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 32;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 3, 3);
        add(basicExamplesDirectoryOpenButton, gridBagConstraints);

        jScrollPane2.setMaximumSize(new java.awt.Dimension(120, 24));
        jScrollPane2.setMinimumSize(new java.awt.Dimension(120, 24));
        jScrollPane2.setPreferredSize(new java.awt.Dimension(120, 24));

        basicExamplesTA.setEditable(false);
        basicExamplesTA.setColumns(20);
        basicExamplesTA.setLineWrap(true);
        basicExamplesTA.setRows(2);
        basicExamplesTA.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.basicExamplesTA.text")); // NOI18N
        basicExamplesTA.setWrapStyleWord(true);
        basicExamplesTA.setAutoscrolls(false);
        basicExamplesTA.setMaximumSize(new java.awt.Dimension(80, 400));
        basicExamplesTA.setMinimumSize(new java.awt.Dimension(80, 400));
        basicExamplesTA.setPreferredSize(new java.awt.Dimension(80, 400));
        jScrollPane2.setViewportView(basicExamplesTA);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 0.4;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 0);
        add(jScrollPane2, gridBagConstraints);

        conformanceCB.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.conformanceCB.text")); // NOI18N
        conformanceCB.setToolTipText("select to download latest archive");
        conformanceCB.setMaximumSize(new java.awt.Dimension(250, 23));
        conformanceCB.setMinimumSize(new java.awt.Dimension(250, 23));
        conformanceCB.setPreferredSize(new java.awt.Dimension(250, 23));
        conformanceCB.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                conformanceCBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(conformanceCB, gridBagConstraints);

        jScrollPane7.setMaximumSize(new java.awt.Dimension(120, 24));
        jScrollPane7.setMinimumSize(new java.awt.Dimension(120, 24));
        jScrollPane7.setPreferredSize(new java.awt.Dimension(120, 24));

        conformanceTA1.setEditable(false);
        conformanceTA1.setColumns(20);
        conformanceTA1.setLineWrap(true);
        conformanceTA1.setRows(4);
        conformanceTA1.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.conformanceTA1.text")); // NOI18N
        conformanceTA1.setWrapStyleWord(true);
        conformanceTA1.setAutoscrolls(false);
        conformanceTA1.setMaximumSize(new java.awt.Dimension(80, 400));
        conformanceTA1.setMinimumSize(new java.awt.Dimension(80, 400));
        conformanceTA1.setPreferredSize(new java.awt.Dimension(80, 400));
        jScrollPane7.setViewportView(conformanceTA1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 0.4;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 0);
        add(jScrollPane7, gridBagConstraints);

        conformanceExamplesBrowserViewButton.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.conformanceExamplesBrowserViewButton.text")); // NOI18N
        conformanceExamplesBrowserViewButton.setToolTipText("view local archive in Web browser");
        conformanceExamplesBrowserViewButton.setMargin(null);
        conformanceExamplesBrowserViewButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                conformanceExamplesBrowserViewButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 32;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(conformanceExamplesBrowserViewButton, gridBagConstraints);

        conformanceExamplesDirectoryOpenButton.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.conformanceExamplesDirectoryOpenButton.text")); // NOI18N
        conformanceExamplesDirectoryOpenButton.setToolTipText("open directory");
        conformanceExamplesDirectoryOpenButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                conformanceExamplesDirectoryOpenButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 32;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 3, 3);
        add(conformanceExamplesDirectoryOpenButton, gridBagConstraints);

        humanoidAnimationCB.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.humanoidAnimationCB.text")); // NOI18N
        humanoidAnimationCB.setToolTipText("select to download latest archive");
        humanoidAnimationCB.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                humanoidAnimationCBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 1.0;
        add(humanoidAnimationCB, gridBagConstraints);

        jScrollPane8.setMaximumSize(new java.awt.Dimension(120, 24));
        jScrollPane8.setMinimumSize(new java.awt.Dimension(120, 24));
        jScrollPane8.setPreferredSize(new java.awt.Dimension(120, 24));

        hanimTextArea.setEditable(false);
        hanimTextArea.setColumns(20);
        hanimTextArea.setLineWrap(true);
        hanimTextArea.setRows(4);
        hanimTextArea.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.hanimTextArea.text")); // NOI18N
        hanimTextArea.setWrapStyleWord(true);
        hanimTextArea.setAutoscrolls(false);
        hanimTextArea.setMaximumSize(new java.awt.Dimension(80, 400));
        hanimTextArea.setMinimumSize(new java.awt.Dimension(80, 400));
        hanimTextArea.setPreferredSize(new java.awt.Dimension(80, 400));
        jScrollPane8.setViewportView(hanimTextArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 0.4;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 0);
        add(jScrollPane8, gridBagConstraints);

        humanoidAnimationExamplesBrowserViewButton.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.humanoidAnimationExamplesBrowserViewButton.text")); // NOI18N
        humanoidAnimationExamplesBrowserViewButton.setToolTipText("view local archive in Web browser");
        humanoidAnimationExamplesBrowserViewButton.setMargin(null);
        humanoidAnimationExamplesBrowserViewButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                humanoidAnimationExamplesBrowserViewButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 32;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(humanoidAnimationExamplesBrowserViewButton, gridBagConstraints);

        humanoidAnimationExamplesDirectoryOpenButton.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.humanoidAnimationExamplesDirectoryOpenButton.text")); // NOI18N
        humanoidAnimationExamplesDirectoryOpenButton.setToolTipText("open directory");
        humanoidAnimationExamplesDirectoryOpenButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                humanoidAnimationExamplesDirectoryOpenButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 32;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 3, 3);
        add(humanoidAnimationExamplesDirectoryOpenButton, gridBagConstraints);

        savageCB.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.savageCB.text")); // NOI18N
        savageCB.setToolTipText("select to download latest archive");
        savageCB.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                savageCBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(savageCB, gridBagConstraints);

        jScrollPane5.setMaximumSize(new java.awt.Dimension(120, 24));
        jScrollPane5.setMinimumSize(new java.awt.Dimension(120, 24));
        jScrollPane5.setPreferredSize(new java.awt.Dimension(120, 24));

        savageTA.setEditable(false);
        savageTA.setColumns(20);
        savageTA.setLineWrap(true);
        savageTA.setRows(3);
        savageTA.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.savageTA.text")); // NOI18N
        savageTA.setWrapStyleWord(true);
        savageTA.setAutoscrolls(false);
        savageTA.setMaximumSize(new java.awt.Dimension(80, 400));
        savageTA.setMinimumSize(new java.awt.Dimension(80, 400));
        savageTA.setPreferredSize(new java.awt.Dimension(80, 400));
        jScrollPane5.setViewportView(savageTA);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.ipady = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 0.4;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 0);
        add(jScrollPane5, gridBagConstraints);

        savageExamplesBrowserViewButton.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.savageExamplesBrowserViewButton.text")); // NOI18N
        savageExamplesBrowserViewButton.setToolTipText("view local archive in Web browser");
        savageExamplesBrowserViewButton.setMargin(null);
        savageExamplesBrowserViewButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                savageExamplesBrowserViewButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 18;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 32;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(savageExamplesBrowserViewButton, gridBagConstraints);

        savageExamplesDirectoryOpenButton.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.savageExamplesDirectoryOpenButton.text")); // NOI18N
        savageExamplesDirectoryOpenButton.setToolTipText("open directory");
        savageExamplesDirectoryOpenButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                savageExamplesDirectoryOpenButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 32;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 3, 3);
        add(savageExamplesDirectoryOpenButton, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 22;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(6, 3, 6, 3);
        add(jSeparator1, gridBagConstraints);

        downLoadLab.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        downLoadLab.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.downLoadLab.text")); // NOI18N
        downLoadLab.setToolTipText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.downLoadLab.toolTipText")); // NOI18N
        downLoadLab.setMaximumSize(new java.awt.Dimension(120, 24));
        downLoadLab.setMinimumSize(new java.awt.Dimension(120, 24));
        downLoadLab.setPreferredSize(new java.awt.Dimension(120, 24));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(downLoadLab, gridBagConstraints);

        rootDownloadDirectoryTF.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.rootDownloadDirectoryTF.text")); // NOI18N
        try
        {
            rootDownloadDirectoryTF.setText(new File(DEFAULTROOTDIR).getCanonicalPath());
        }
        catch(IOException ex) {}
        rootDownloadDirectoryTF.addMouseListener(new java.awt.event.MouseAdapter()
        {
            public void mouseExited(java.awt.event.MouseEvent evt)
            {
                rootDownloadDirectoryTFMouseExited(evt);
            }
        });
        rootDownloadDirectoryTF.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                rootDownloadDirectoryTFActionPerformed(evt);
            }
        });
        rootDownloadDirectoryTF.addPropertyChangeListener(new java.beans.PropertyChangeListener()
        {
            public void propertyChange(java.beans.PropertyChangeEvent evt)
            {
                rootDownloadDirectoryTFPropertyChange(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 440;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.8;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 0);
        add(rootDownloadDirectoryTF, gridBagConstraints);

        rootDownloadDirectoryChooserButton.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.rootDownloadDirectoryChooserButton.text")); // NOI18N
        rootDownloadDirectoryChooserButton.setMargin(null);
        rootDownloadDirectoryChooserButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                rootDownloadDirectoryChooserButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 0);
        add(rootDownloadDirectoryChooserButton, gridBagConstraints);

        rootDownloadDirectoryDefaultButton.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.rootDownloadDirectoryDefaultButton.text")); // NOI18N
        rootDownloadDirectoryDefaultButton.setToolTipText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.rootDownloadDirectoryDefaultButton.toolTipText")); // NOI18N
        rootDownloadDirectoryDefaultButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                rootDownloadDirectoryDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 24;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 32;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(rootDownloadDirectoryDefaultButton, gridBagConstraints);

        downloadDirectoryNoteLabel.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.downloadDirectoryNoteLabel.text")); // NOI18N
        downloadDirectoryNoteLabel.setToolTipText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.downloadDirectoryNoteLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 25;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(downloadDirectoryNoteLabel, gridBagConstraints);

        downloadDirectoryLabel.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        downloadDirectoryLabel.setForeground(new java.awt.Color(0, 102, 51));
        downloadDirectoryLabel.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.downloadDirectoryLabel.text")); // NOI18N
        downloadDirectoryLabel.setToolTipText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.downloadDirectoryLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 25;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 4, 3, 3);
        add(downloadDirectoryLabel, gridBagConstraints);

        downloadDirectoryOpenButton.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.downloadDirectoryOpenButton.text")); // NOI18N
        downloadDirectoryOpenButton.setToolTipText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.downloadDirectoryOpenButton.toolTipText")); // NOI18N
        downloadDirectoryOpenButton.setMargin(null);
        downloadDirectoryOpenButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                downloadDirectoryOpenButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 25;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 32;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(downloadDirectoryOpenButton, gridBagConstraints);

        startDownloadButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        startDownloadButton.setForeground(new java.awt.Color(0, 102, 51));
        startDownloadButton.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.startDownloadButton.text")); // NOI18N
        startDownloadButton.setToolTipText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.startDownloadButton.toolTipText")); // NOI18N
        startDownloadButton.setMaximumSize(new java.awt.Dimension(120, 24));
        startDownloadButton.setMinimumSize(new java.awt.Dimension(120, 24));
        startDownloadButton.setPreferredSize(new java.awt.Dimension(120, 24));
        startDownloadButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                startDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        add(startDownloadButton, gridBagConstraints);

        cancelDownloadButton.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        cancelDownloadButton.setForeground(new java.awt.Color(153, 0, 0));
        cancelDownloadButton.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.cancelDownloadButton.text")); // NOI18N
        cancelDownloadButton.setEnabled(false);
        cancelDownloadButton.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        cancelDownloadButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                cancelDownloadButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        add(cancelDownloadButton, gridBagConstraints);

        progressHintLabel.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        progressHintLabel.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.progressHintLabel.text")); // NOI18N
        progressHintLabel.setToolTipText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.progressHintLabel.toolTipText")); // NOI18N
        progressHintLabel.setEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(progressHintLabel, gridBagConstraints);

        refreshDownloadPanelButton.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.refreshDownloadPanelButton.text")); // NOI18N
        refreshDownloadPanelButton.setToolTipText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.refreshDownloadPanelButton.toolTipText")); // NOI18N
        refreshDownloadPanelButton.setMargin(null);
        refreshDownloadPanelButton.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                refreshDownloadPanelButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 26;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 32;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(refreshDownloadPanelButton, gridBagConstraints);

        allSelectCheckBox.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        allSelectCheckBox.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.allSelectCheckBox.text")); // NOI18N
        allSelectCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.allSelectCheckBox.toolTipText")); // NOI18N
        allSelectCheckBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                allSelectCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LAST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(allSelectCheckBox, gridBagConstraints);

        allClearCheckBox.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        allClearCheckBox.setText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.allClearCheckBox.text")); // NOI18N
        allClearCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "DownloadX3dExamplesArchivesPanel.allClearCheckBox.toolTipText")); // NOI18N
        allClearCheckBox.addActionListener(new java.awt.event.ActionListener()
        {
            public void actionPerformed(java.awt.event.ActionEvent evt)
            {
                allClearCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LAST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(allClearCheckBox, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
  private void rootDownloadDirectoryChooserButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_rootDownloadDirectoryChooserButtonActionPerformed
  {//GEN-HEADEREND:event_rootDownloadDirectoryChooserButtonActionPerformed
    if(fileChooser == null) {
      fileChooser = new JFileChooser();
      fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      fileChooser.setDialogTitle("Select archive root directory");
      fileChooser.setToolTipText("Archives will extract to "+targetPath);
      fileChooser.setCurrentDirectory(new File(rootDownloadDirectoryTF.getText()));
      fileChooser.setMultiSelectionEnabled(false);
    }
    
    int returnValue = fileChooser.showOpenDialog(this);
    if (returnValue != JFileChooser.APPROVE_OPTION)
        return;
    
    rootDownloadDirectoryTF.setText(fileChooser.getSelectedFile().getAbsolutePath());
    downloadDirectoryLabelUpdate (); // path adjustment, prerequisite to saving value in X3dEditUserPreferences
    X3dEditUserPreferences.setExampleArchivesRootDirectory(localArchiveDirectory);
    updateStatusPropertiesLocalArchivesPresent();
    updatePanelLocalArchivesPresent();
}//GEN-LAST:event_rootDownloadDirectoryChooserButtonActionPerformed

  private void startDownloadButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_startDownloadButtonActionPerformed
  {//GEN-HEADEREND:event_startDownloadButtonActionPerformed
    allSelectCheckBox.setSelected(false);
     allClearCheckBox.setSelected(false);
    downloadDirectoryLabelUpdate (); // path adjustment, prerequisite to saving value in X3dEditUserPreferences
    X3dEditUserPreferences.setExampleArchivesRootDirectory(localArchiveDirectory);
    updateStatusPropertiesLocalArchivesPresent();
    updatePanelLocalArchivesPresent();
    
    ArrayList<String> targets = new ArrayList<>();
    String message = "<html><p align='center'><b>ARCHIVE_NAME</b> examples archive</p><p> is already present in local directory.</p>"
            + "<p align='center'>  </p><p align='center' style='color: rgb(127, 70, 0)'><b>Overwrite local files?</b></p></html>"; // darkorange divided by 2

    if (x3d4waExamplesCB.isSelected())
    {
        if  (isLocalArchivePresent(X3D4WA_EXAMPLESTARGET))
        {
            NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                    message.replace("ARCHIVE_NAME", X3D4WA_EXAMPLESTARGET), 
                    "Overwrite local files?", NotifyDescriptor.YES_NO_OPTION);
            if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
            {
                targets.add(X3D4WA_EXAMPLESTARGET);
                x3d4waExamplesCB.setForeground(red);
                x3d4waExamplesCB.setFont(boldFont);
            }
            else x3d4waExamplesCB.setSelected(false);
        }
        else 
        {
            targets.add(X3D4WA_EXAMPLESTARGET);
            x3d4waExamplesCB.setForeground(red);
            x3d4waExamplesCB.setFont(boldFont);
        }
    }
    if (x3d4amExamplesCB.isSelected())
    {
        if  (isLocalArchivePresent(X3D4AM_EXAMPLESTARGET))
        {
            NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                    message.replace("ARCHIVE_NAME", X3D4AM_EXAMPLESTARGET), 
                    "Overwrite local files?", NotifyDescriptor.YES_NO_OPTION);
            if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
            {
                targets.add(X3D4AM_EXAMPLESTARGET);
                x3d4amExamplesCB.setForeground(red);
                x3d4amExamplesCB.setFont(boldFont);
            }
            else x3d4amExamplesCB.setSelected(false);
        }
        else 
        {
            targets.add(X3D4AM_EXAMPLESTARGET);
        }
    }
    if (vrmlSourcebookCB.isSelected())
    {
        if  (isLocalArchivePresent(VRML2SOURCEBOOKTARGET))
        {
            NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                    message.replace("ARCHIVE_NAME", VRML2SOURCEBOOKTARGET), 
                    "Overwrite local files?", NotifyDescriptor.YES_NO_OPTION);
            if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
            {
                targets.add(VRML2SOURCEBOOKTARGET);
                vrmlSourcebookCB.setForeground(red);
                vrmlSourcebookCB.setFont(boldFont);
            }
            else vrmlSourcebookCB.setSelected(false);
        }
        else 
        {
            targets.add(VRML2SOURCEBOOKTARGET);
        }
    }
    if (basicExamplesCB.isSelected())
    {
        if  (isLocalArchivePresent(BASICEXAMPLESTARGET))
        {
            NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                    message.replace("ARCHIVE_NAME", BASICEXAMPLESTARGET), 
                    "Overwrite local files?", NotifyDescriptor.YES_NO_OPTION);
            if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
            {
                targets.add(BASICEXAMPLESTARGET);
                basicExamplesCB.setForeground(red);
                basicExamplesCB.setFont(boldFont);
            }
            else basicExamplesCB.setSelected(false);
        }
        else 
        {
            targets.add(BASICEXAMPLESTARGET);
            basicExamplesCB.setForeground(red);
            basicExamplesCB.setFont(boldFont);
        }
    }
    if (conformanceCB.isSelected())
    {
        if  (isLocalArchivePresent(CONFORMANCENISTTARGET))
        {
            NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                    message.replace("ARCHIVE_NAME", CONFORMANCENISTTARGET), 
                    "Overwrite local files?", NotifyDescriptor.YES_NO_OPTION);
            if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
            {
                targets.add(CONFORMANCENISTTARGET);
                conformanceCB.setForeground(red);
                conformanceCB.setFont(boldFont);
            }
            else conformanceCB.setSelected(false);
        }
        else 
        {
            targets.add(CONFORMANCENISTTARGET);
            conformanceCB.setForeground(red);
            conformanceCB.setFont(boldFont);
        }
    }
    if (humanoidAnimationCB.isSelected())
    {
        if  (isLocalArchivePresent(HUMANOIDANIMATIONTARGET))
        {
            NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                    message.replace("ARCHIVE_NAME", HUMANOIDANIMATIONTARGET), 
                    "Overwrite local files?", NotifyDescriptor.YES_NO_OPTION);
            if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
            {
                targets.add(HUMANOIDANIMATIONTARGET);
                humanoidAnimationCB.setForeground(red);
                humanoidAnimationCB.setFont(boldFont);
            }
            else humanoidAnimationCB.setSelected(false);
        }
        else 
        {
            targets.add(HUMANOIDANIMATIONTARGET);
            humanoidAnimationCB.setForeground(red);
            humanoidAnimationCB.setFont(boldFont);
        }
    }
    if (savageCB.isSelected())
    {
        if  (isLocalArchivePresent(SAVAGETARGET))
        {
            NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                    message.replace("ARCHIVE_NAME", SAVAGETARGET), 
                    "Overwrite local files?", NotifyDescriptor.YES_NO_OPTION);
            if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
            {
                targets.add(SAVAGETARGET);
                savageCB.setForeground(red);
                savageCB.setFont(boldFont);
            }
            else savageCB.setSelected(false);
        }
        else 
        {
            targets.add(SAVAGETARGET);
            savageCB.setForeground(red);
            savageCB.setFont(boldFont);
        }
    }
    startDownloadButtonRequestFocus(); // might have not confirmed selection of any archives for download

    if (targets.size() <= 0)
      return;
    
    String[] targetsArray = targets.toArray(new String[0]);

    Properties targetProperties = null;
    String targetDirectory = rootDownloadDirectoryTF.getText().trim();
    if (targetDirectory.length() > 0) {
        targetProperties = new Properties();
        targetProperties.put("examplesRootDirectory", targetDirectory);
    }

    try {
      FileObject buildFile = FileUtil.getConfigRoot()/*Repository.getDefault().getDefaultFileSystem()*/.getFileSystem().findResource(antScriptPath);
      // Can only execute ant tasks residing on disk, so get this out of the jar into a temp directory
      final File tempFile = File.createTempFile(buildFile.getName(), buildFile.getExt());
     
      tempFile.deleteOnExit(); // TODO avoid "parameter file was not normalized" warning
      FileObject tempFileObject = FileUtil.createData(tempFile);
      FileLock fileLock = tempFileObject.lock();
      OutputStream os = tempFileObject.getOutputStream(fileLock);     
      FileUtil.copy(buildFile.getInputStream(), os);
      os.close();
      fileLock.releaseLock();

      executorTask = ActionUtils.runTarget(tempFileObject, targetsArray, targetProperties);
      executorTask.addTaskListener(new taskListener());
      executorTask.getInputOutput().select();
      startDownloadButton.setText("Download started...");
      startDownloadButton.setEnabled(false);
     cancelDownloadButton.setEnabled(true);
     cancelDownloadButton.requestFocus(true);
     progressHintLabel.setEnabled(true);
     progressHintLabel.setVisible(true);

      // Save the locations for the View menu
      File interimParentDirectory = new File(targetDirectory,targetPath);
      if (x3d4waExamplesCB.isSelected())
        LocalExamplesFinder.instance().setX3d4waExamplesDirectory           (new File(interimParentDirectory,X3D4WA_EXAMPLESTARGET).getAbsolutePath());
      if (x3d4waExamplesCB.isSelected())          
        LocalExamplesFinder.instance().setX3d4amExamplesDirectory           (new File(interimParentDirectory,X3D4AM_EXAMPLESTARGET).getAbsolutePath());
      if (basicExamplesCB.isSelected())          
        LocalExamplesFinder.instance().setBasicExamplesDirectory            (new File(interimParentDirectory,BASICEXAMPLESTARGET).getAbsolutePath());
      if (conformanceCB.isSelected())          
        LocalExamplesFinder.instance().setConformExamplesDirectory          (new File(interimParentDirectory,CONFORMANCENISTTARGET).getAbsolutePath());
      if (humanoidAnimationCB.isSelected())
        LocalExamplesFinder.instance().setHumanoidAnimationExamplesDirectory(new File(interimParentDirectory,HUMANOIDANIMATIONTARGET).getAbsolutePath());
      if (vrmlSourcebookCB.isSelected())
        LocalExamplesFinder.instance().setVrmlExamplesDirectory             (new File(interimParentDirectory,VRML2SOURCEBOOKTARGET).getAbsolutePath());
      if (savageCB.isSelected())          
        LocalExamplesFinder.instance().setSavageExamplesDirectory           (new File(interimParentDirectory,SAVAGETARGET).getAbsolutePath());
    }//GEN-LAST:event_startDownloadButtonActionPerformed
    catch (IOException | IllegalArgumentException ex) {
      executorTask.getInputOutput().select();
      executorTask.getInputOutput().getErr().append(ex.getMessage());
    }
    // TODO confirm, are downloads complete prior to reaching next step?
//    executorTask.waitFinished(); // don't block reporting output progress
    
    updateStatusPropertiesLocalArchivesPresent();
    updatePanelLocalArchivesPresent();
    if (!anyArchivePresentInitially && isAnyArchivePresent())
    {
        // initial installation complete, support user with autolaunch
        X3dEditUserPreferences.setExampleArchivesServerAutolaunch(true);
        
        NotifyDescriptor notifyDescriptor = new NotifyDescriptor.Confirmation(
                "Enabling autolaunch of localhost HTTP server for example archives", 
                "Enabling autolaunch", NotifyDescriptor.PLAIN_MESSAGE);
        DialogDisplayer.getDefault().notify(notifyDescriptor);
    }
  }
  
  public boolean isRunning()
  {
    return executorTask != null;
  }
  
  class taskListener implements TaskListener
  {
    @Override
    public void taskFinished(Task task)
    {
      task = null;
      killTask(); // just for the buttons
    }    
  }
  public void killTask()
  {
    if(executorTask != null) {
      executorTask.stop();
      executorTask = null;
    }
    // downloading all done
     startDownloadButton.setText("Start downloads"); // restore label
     startDownloadButton.setEnabled(true);
    cancelDownloadButton.setEnabled(false);
    clearAllArchiveCheckBoxes();
    updateStatusPropertiesLocalArchivesPresent();
    updatePanelLocalArchivesPresent();
  }
  private void cancelDownloadButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_cancelDownloadButtonActionPerformed
  {//GEN-HEADEREND:event_cancelDownloadButtonActionPerformed
    killTask();
    // refresh status in case anything was downloaded
    updateStatusPropertiesLocalArchivesPresent();
    updatePanelLocalArchivesPresent();
  }//GEN-LAST:event_cancelDownloadButtonActionPerformed

  private void savageCBActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_savageCBActionPerformed
  {//GEN-HEADEREND:event_savageCBActionPerformed
      startDownloadButtonRequestFocus();
        allSelectCheckBox.setSelected(false);
         allClearCheckBox.setSelected(false);
  }//GEN-LAST:event_savageCBActionPerformed

    private void x3d4amExamplesCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_x3d4amExamplesCBActionPerformed
       startDownloadButtonRequestFocus();
        allSelectCheckBox.setSelected(false);
         allClearCheckBox.setSelected(false);
    }//GEN-LAST:event_x3d4amExamplesCBActionPerformed

    private void rootDownloadDirectoryTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rootDownloadDirectoryTFActionPerformed
        downloadDirectoryLabelUpdate ();
        updateStatusPropertiesLocalArchivesPresent();
        updatePanelLocalArchivesPresent();
    }//GEN-LAST:event_rootDownloadDirectoryTFActionPerformed

    private void rootDownloadDirectoryTFPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_rootDownloadDirectoryTFPropertyChange
        downloadDirectoryLabelUpdate ();
        updateStatusPropertiesLocalArchivesPresent();
        updatePanelLocalArchivesPresent();
    }//GEN-LAST:event_rootDownloadDirectoryTFPropertyChange

    private void x3d4waExamplesCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_x3d4waExamplesCBActionPerformed
        startDownloadButtonRequestFocus();
        allSelectCheckBox.setSelected(false);
         allClearCheckBox.setSelected(false);
    }//GEN-LAST:event_x3d4waExamplesCBActionPerformed

    private void vrmlSourcebookCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_vrmlSourcebookCBActionPerformed
        startDownloadButtonRequestFocus();
        allSelectCheckBox.setSelected(false);
         allClearCheckBox.setSelected(false);
    }//GEN-LAST:event_vrmlSourcebookCBActionPerformed

    private void basicExamplesCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_basicExamplesCBActionPerformed
        startDownloadButtonRequestFocus();
        allSelectCheckBox.setSelected(false);
         allClearCheckBox.setSelected(false);
    }//GEN-LAST:event_basicExamplesCBActionPerformed

    private void conformanceCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conformanceCBActionPerformed
        startDownloadButtonRequestFocus();
        allSelectCheckBox.setSelected(false);
         allClearCheckBox.setSelected(false);
    }//GEN-LAST:event_conformanceCBActionPerformed

    private void humanoidAnimationCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_humanoidAnimationCBActionPerformed
        startDownloadButtonRequestFocus();
        allSelectCheckBox.setSelected(false);
         allClearCheckBox.setSelected(false);
    }//GEN-LAST:event_humanoidAnimationCBActionPerformed

    private void rootDownloadDirectoryDefaultButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_rootDownloadDirectoryDefaultButtonActionPerformed
    {//GEN-HEADEREND:event_rootDownloadDirectoryDefaultButtonActionPerformed
        X3dEditUserPreferences.setExampleArchivesRootDirectory(EXAMPLES_ROOT_DIRECTORY_DEFAULT); // user.dir
        rootDownloadDirectoryTF.setText(X3dEditUserPreferences.getExampleArchivesRootDirectory());
        downloadDirectoryLabelUpdate (); // re-initialize
        updateStatusPropertiesLocalArchivesPresent();
        updatePanelLocalArchivesPresent();
    }//GEN-LAST:event_rootDownloadDirectoryDefaultButtonActionPerformed
    /** Open directory using local file system
     * @param archiveName optional: name of local X3D example archive, uses root directory otherwise */ 
    private void localArchiveDirectoryOpen (String archiveName)
    {
        if (Desktop.isDesktopSupported())
        {
            try
            {
                String fullArchiveDirectory = localArchiveDirectory;
                if (!archiveName.isBlank() && !archiveName.equals(localArchiveDirectory))
                    fullArchiveDirectory += File.separatorChar + archiveName;
                File archiveDirectoryFile = new File(fullArchiveDirectory); // which is linked in downloadDirectoryLabel
                if  (archiveDirectoryFile.isDirectory())
                //   Desktop.getDesktop().browseFileDirectory(archiveDirectoryFile); // not supported on windows 8(
                     Desktop.getDesktop().browse(archiveDirectoryFile.toURI());
                else
                {
                    // archive not found, so go to user-designated examples installation root
                    archiveDirectoryFile = new File(rootDownloadDirectoryTF.getText());
                    if  (archiveDirectoryFile.isDirectory())
                    {
                        NotifyDescriptor notifyDescriptor = new NotifyDescriptor.Confirmation(
                                "<html><p align='center'>No local example archives found.</p> <br /> " + 
                                      "<p align='center'>Opening installation root directory '" + 
                                rootDownloadDirectoryTF.getText() + "' instead.</p></html>", 
                           "Open directory for local examples", NotifyDescriptor.PLAIN_MESSAGE);
                        DialogDisplayer.getDefault().notify(notifyDescriptor);
                        Desktop.getDesktop().browse(archiveDirectoryFile.toURI());
                    }
                    else
                    {
                        NotifyDescriptor notifyDescriptor = new NotifyDescriptor.Confirmation(
                                "Installation root directory for examples not found", "Open directory for local examples", NotifyDescriptor.PLAIN_MESSAGE);
                        DialogDisplayer.getDefault().notify(notifyDescriptor);
                    }
                }
            }
            catch (IOException ioe) // if problem occurs, show in console
            {
                System.err.println(Arrays.toString(ioe.getStackTrace()));
            }
        }
    }
    private void downloadDirectoryOpenButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_downloadDirectoryOpenButtonActionPerformed
    {//GEN-HEADEREND:event_downloadDirectoryOpenButtonActionPerformed
        localArchiveDirectoryOpen("");
    }//GEN-LAST:event_downloadDirectoryOpenButtonActionPerformed

    private void basicExamplesDirectoryOpenButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_basicExamplesDirectoryOpenButtonActionPerformed
    {//GEN-HEADEREND:event_basicExamplesDirectoryOpenButtonActionPerformed
        localArchiveDirectoryOpen(BASICEXAMPLESTARGET);
    }//GEN-LAST:event_basicExamplesDirectoryOpenButtonActionPerformed

    private void basicExamplesBrowserViewButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_basicExamplesBrowserViewButtonActionPerformed
    {//GEN-HEADEREND:event_basicExamplesBrowserViewButtonActionPerformed
        checkAutolaunchRunExampleArchivesServer();
        sendBrowserTo(localArchiveDirectory + File.separatorChar + BASICEXAMPLESTARGET + File.separatorChar + "index.html");
    }//GEN-LAST:event_basicExamplesBrowserViewButtonActionPerformed

    private void x3d4waExamplesBrowserViewButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_x3d4waExamplesBrowserViewButtonActionPerformed
    {//GEN-HEADEREND:event_x3d4waExamplesBrowserViewButtonActionPerformed
        checkAutolaunchRunExampleArchivesServer();
        sendBrowserTo(localArchiveDirectory + File.separatorChar + X3D4WA_EXAMPLESTARGET + File.separatorChar + "index.html");
    }//GEN-LAST:event_x3d4waExamplesBrowserViewButtonActionPerformed

    private void x3d4waExamplesDirectoryOpenButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_x3d4waExamplesDirectoryOpenButtonActionPerformed
    {//GEN-HEADEREND:event_x3d4waExamplesDirectoryOpenButtonActionPerformed
        localArchiveDirectoryOpen(X3D4WA_EXAMPLESTARGET);
    }//GEN-LAST:event_x3d4waExamplesDirectoryOpenButtonActionPerformed

    private void x3d4amExamplesBrowserViewButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_x3d4amExamplesBrowserViewButtonActionPerformed
    {//GEN-HEADEREND:event_x3d4amExamplesBrowserViewButtonActionPerformed
        checkAutolaunchRunExampleArchivesServer();
        sendBrowserTo(localArchiveDirectory + File.separatorChar + X3D4AM_EXAMPLESTARGET + File.separatorChar + "index.html");
    }//GEN-LAST:event_x3d4amExamplesBrowserViewButtonActionPerformed

    private void x3d4amExamplesDirectoryOpenButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_x3d4amExamplesDirectoryOpenButtonActionPerformed
    {//GEN-HEADEREND:event_x3d4amExamplesDirectoryOpenButtonActionPerformed
        localArchiveDirectoryOpen(X3D4AM_EXAMPLESTARGET);
    }//GEN-LAST:event_x3d4amExamplesDirectoryOpenButtonActionPerformed

    private void vrmlSourcebookExamplesBrowserViewButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_vrmlSourcebookExamplesBrowserViewButtonActionPerformed
    {//GEN-HEADEREND:event_vrmlSourcebookExamplesBrowserViewButtonActionPerformed
        checkAutolaunchRunExampleArchivesServer();
        sendBrowserTo(localArchiveDirectory + File.separatorChar + VRML2SOURCEBOOKTARGET + File.separatorChar + "index.html");
    }//GEN-LAST:event_vrmlSourcebookExamplesBrowserViewButtonActionPerformed

    private void vrmlSourcebookExamplesDirectoryOpenButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_vrmlSourcebookExamplesDirectoryOpenButtonActionPerformed
    {//GEN-HEADEREND:event_vrmlSourcebookExamplesDirectoryOpenButtonActionPerformed
        localArchiveDirectoryOpen(VRML2SOURCEBOOKTARGET);
    }//GEN-LAST:event_vrmlSourcebookExamplesDirectoryOpenButtonActionPerformed

    private void savageExamplesBrowserViewButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_savageExamplesBrowserViewButtonActionPerformed
    {//GEN-HEADEREND:event_savageExamplesBrowserViewButtonActionPerformed
        checkAutolaunchRunExampleArchivesServer();
        sendBrowserTo(localArchiveDirectory + File.separatorChar + SAVAGETARGET + File.separatorChar + "index.html");
    }//GEN-LAST:event_savageExamplesBrowserViewButtonActionPerformed

    private void savageExamplesDirectoryOpenButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_savageExamplesDirectoryOpenButtonActionPerformed
    {//GEN-HEADEREND:event_savageExamplesDirectoryOpenButtonActionPerformed
        localArchiveDirectoryOpen(SAVAGETARGET);
    }//GEN-LAST:event_savageExamplesDirectoryOpenButtonActionPerformed

    private void conformanceExamplesBrowserViewButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_conformanceExamplesBrowserViewButtonActionPerformed
    {//GEN-HEADEREND:event_conformanceExamplesBrowserViewButtonActionPerformed
        checkAutolaunchRunExampleArchivesServer();
        sendBrowserTo(localArchiveDirectory + File.separatorChar + CONFORMANCENISTTARGET + File.separatorChar + "index.html");
    }//GEN-LAST:event_conformanceExamplesBrowserViewButtonActionPerformed

    private void conformanceExamplesDirectoryOpenButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_conformanceExamplesDirectoryOpenButtonActionPerformed
    {//GEN-HEADEREND:event_conformanceExamplesDirectoryOpenButtonActionPerformed
        localArchiveDirectoryOpen(CONFORMANCENISTTARGET);
    }//GEN-LAST:event_conformanceExamplesDirectoryOpenButtonActionPerformed

    private void humanoidAnimationExamplesBrowserViewButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_humanoidAnimationExamplesBrowserViewButtonActionPerformed
    {//GEN-HEADEREND:event_humanoidAnimationExamplesBrowserViewButtonActionPerformed
        checkAutolaunchRunExampleArchivesServer();
        sendBrowserTo(localArchiveDirectory + File.separatorChar + HUMANOIDANIMATIONTARGET + File.separatorChar + "index.html");
    }//GEN-LAST:event_humanoidAnimationExamplesBrowserViewButtonActionPerformed

    private void humanoidAnimationExamplesDirectoryOpenButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_humanoidAnimationExamplesDirectoryOpenButtonActionPerformed
    {//GEN-HEADEREND:event_humanoidAnimationExamplesDirectoryOpenButtonActionPerformed
        localArchiveDirectoryOpen(HUMANOIDANIMATIONTARGET);
    }//GEN-LAST:event_humanoidAnimationExamplesDirectoryOpenButtonActionPerformed

    private void refreshDownloadPanelButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_refreshDownloadPanelButtonActionPerformed
    {//GEN-HEADEREND:event_refreshDownloadPanelButtonActionPerformed
        updateStatusPropertiesLocalArchivesPresent();
        updatePanelLocalArchivesPresent();
    }//GEN-LAST:event_refreshDownloadPanelButtonActionPerformed

    private void allSelectCheckBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_allSelectCheckBoxActionPerformed
    {//GEN-HEADEREND:event_allSelectCheckBoxActionPerformed
        if (allSelectCheckBox.isSelected())
        {
            x3d4waExamplesCB.setSelected(true);
            x3d4amExamplesCB.setSelected(true);
            vrmlSourcebookCB.setSelected(true);
             basicExamplesCB.setSelected(true);
               conformanceCB.setSelected(true);
         humanoidAnimationCB.setSelected(true);
                    savageCB.setSelected(true);
            
            startDownloadButtonRequestFocus();
        }
        allClearCheckBox.setSelected(false);
    }//GEN-LAST:event_allSelectCheckBoxActionPerformed

    private void allClearCheckBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_allClearCheckBoxActionPerformed
    {//GEN-HEADEREND:event_allClearCheckBoxActionPerformed
        if (allClearCheckBox.isSelected())
        {
            clearAllArchiveCheckBoxes();
            startDownloadButtonRequestFocus();
        }
        allSelectCheckBox.setSelected(false);
        LaunchX3dExamplesAction.sleepSeconds(1);
        allClearCheckBox.setSelected(false); // give user time to note button response, then reset
    }//GEN-LAST:event_allClearCheckBoxActionPerformed

    public void clearAllArchiveCheckBoxes()
    {
            x3d4waExamplesCB.setSelected(false);
            x3d4amExamplesCB.setSelected(false);
            vrmlSourcebookCB.setSelected(false);
             basicExamplesCB.setSelected(false);
               conformanceCB.setSelected(false);
         humanoidAnimationCB.setSelected(false);
                    savageCB.setSelected(false);

            x3d4waExamplesCB.setForeground(black);
            x3d4amExamplesCB.setForeground(black);
            vrmlSourcebookCB.setForeground(black);
             basicExamplesCB.setForeground(black);
               conformanceCB.setForeground(black);
         humanoidAnimationCB.setForeground(black);
                    savageCB.setForeground(black);

            x3d4waExamplesCB.setFont(plainFont);
            x3d4amExamplesCB.setFont(plainFont);
            vrmlSourcebookCB.setFont(plainFont);
             basicExamplesCB.setFont(plainFont);
               conformanceCB.setFont(plainFont);
         humanoidAnimationCB.setFont(plainFont);
                    savageCB.setFont(plainFont);
    }
    private void rootDownloadDirectoryTFMouseExited(java.awt.event.MouseEvent evt)//GEN-FIRST:event_rootDownloadDirectoryTFMouseExited
    {//GEN-HEADEREND:event_rootDownloadDirectoryTFMouseExited
        downloadDirectoryLabelUpdate ();
        updateStatusPropertiesLocalArchivesPresent();
        updatePanelLocalArchivesPresent();
    }//GEN-LAST:event_rootDownloadDirectoryTFMouseExited

    /** Check autolaunch, set if user wants to, then run Example Archives server */
    public final static void checkAutolaunchRunExampleArchivesServer()
    {
        if (!X3dEditUserPreferences.isExampleArchivesServerAutolaunch())
        {
            NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                    "<html><p align='center'>Localhost http server is necessary to view local examples using X_ITE." +
                    "</p><br /><p align='center'>Turn on autolaunch for local Example Archives server?</p></html>", 
                    "Autolaunch http server?", NotifyDescriptor.YES_NO_OPTION);
            if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
            {
                X3dEditUserPreferences.setExampleArchivesServerAutolaunch(true);
            }
            startExampleArchivesServer();
        }
    }
    final void downloadDirectoryLabelUpdate ()
    {
        localArchiveDirectory = rootDownloadDirectoryTF.getText();
        if      (localArchiveDirectory.endsWith("\\"))
                 localArchiveDirectory = localArchiveDirectory +   "www.web3d.org\\x3d\\content\\examples"; // Windows path
        else if (localArchiveDirectory.contains("\\") || localArchiveDirectory.contains(":"))
                 localArchiveDirectory = localArchiveDirectory + "\\www.web3d.org\\x3d\\content\\examples"; // Windows path
        else if (localArchiveDirectory.endsWith("/"))
                 localArchiveDirectory = localArchiveDirectory +   "www.web3d.org/x3d/content/examples";    // Unix path
        else     localArchiveDirectory = localArchiveDirectory +  "/www.web3d.org/x3d/content/examples";    // Unix path
        downloadDirectoryLabel.setText(localArchiveDirectory); 
        X3dEditUserPreferences.setExampleArchivesRootDirectory(localArchiveDirectory);

        updateStatusPropertiesLocalArchivesPresent();
        updatePanelLocalArchivesPresent();
        if  (rootDownloadDirectoryTF.getText().isBlank())
             startDownloadButton.setEnabled(false);
        else startDownloadButtonRequestFocus();
    }
    private void startDownloadButtonRequestFocus()
    {
        if (   x3d4amExamplesCB.isSelected() ||
               x3d4waExamplesCB.isSelected() ||
                basicExamplesCB.isSelected() ||
                  conformanceCB.isSelected() ||
            humanoidAnimationCB.isSelected() ||
               vrmlSourcebookCB.isSelected() ||
                       savageCB.isSelected()
            )
        {
            startDownloadButton.setEnabled(true);
            // https://stackoverflow.com/questions/8615958/java-gui-how-to-set-focus-on-jbutton-in-jpanel-on-jframe
            startDownloadButton.requestFocus();
        }
        else
        {
            startDownloadButton.setEnabled(false);
        }
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox allClearCheckBox;
    private javax.swing.JCheckBox allSelectCheckBox;
    private javax.swing.JButton basicExamplesBrowserViewButton;
    private javax.swing.JCheckBox basicExamplesCB;
    private javax.swing.JButton basicExamplesDirectoryOpenButton;
    private javax.swing.JTextArea basicExamplesTA;
    private javax.swing.JButton cancelDownloadButton;
    private javax.swing.JCheckBox conformanceCB;
    private javax.swing.JButton conformanceExamplesBrowserViewButton;
    private javax.swing.JButton conformanceExamplesDirectoryOpenButton;
    private javax.swing.JTextArea conformanceTA1;
    private javax.swing.JLabel downLoadLab;
    private javax.swing.JLabel downloadDirectoryLabel;
    private javax.swing.JLabel downloadDirectoryNoteLabel;
    private javax.swing.JButton downloadDirectoryOpenButton;
    private javax.swing.JTextArea hanimTextArea;
    private javax.swing.JCheckBox humanoidAnimationCB;
    private javax.swing.JButton humanoidAnimationExamplesBrowserViewButton;
    private javax.swing.JButton humanoidAnimationExamplesDirectoryOpenButton;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel progressHintLabel;
    private javax.swing.JButton refreshDownloadPanelButton;
    private javax.swing.JButton rootDownloadDirectoryChooserButton;
    private javax.swing.JButton rootDownloadDirectoryDefaultButton;
    private javax.swing.JTextField rootDownloadDirectoryTF;
    private javax.swing.JCheckBox savageCB;
    private javax.swing.JButton savageExamplesBrowserViewButton;
    private javax.swing.JButton savageExamplesDirectoryOpenButton;
    private javax.swing.JTextArea savageTA;
    private javax.swing.JButton startDownloadButton;
    private javax.swing.JCheckBox vrmlSourcebookCB;
    private javax.swing.JButton vrmlSourcebookExamplesBrowserViewButton;
    private javax.swing.JButton vrmlSourcebookExamplesDirectoryOpenButton;
    private javax.swing.JTextArea vrmlTA;
    private javax.swing.JButton x3d4amExamplesBrowserViewButton;
    private javax.swing.JCheckBox x3d4amExamplesCB;
    private javax.swing.JButton x3d4amExamplesDirectoryOpenButton;
    private javax.swing.JButton x3d4waExamplesBrowserViewButton;
    private javax.swing.JCheckBox x3d4waExamplesCB;
    private javax.swing.JButton x3d4waExamplesDirectoryOpenButton;
    private javax.swing.JTextArea x3dForAdvancedModelingTA;
    private javax.swing.JTextArea x3dForWebAuthorsTA;
    // End of variables declaration//GEN-END:variables

    /**
     * @return the anyArchivePresent
     */
    public static boolean isAnyArchivePresent()
    {
        return anyArchivePresent;
    }

    /**
     * @param anyArchivePresent the anyArchivePresent to set
     */
    public void setAnyArchivePresent(boolean anyArchivePresent)
    {
        DownloadX3dExamplesArchivesPanel.anyArchivePresent = anyArchivePresent;
    }
}
