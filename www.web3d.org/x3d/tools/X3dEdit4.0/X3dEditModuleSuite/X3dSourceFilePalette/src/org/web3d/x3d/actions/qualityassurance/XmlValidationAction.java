/*
 * Copyright (c) 1995-2021 held by the author(s) .  All rights reserved.
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
 *       (http://www.nps.edu and https://MovesInstitute.nps.edu)
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
package org.web3d.x3d.actions.qualityassurance;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Properties;
import org.apache.tools.ant.module.api.support.ActionUtils;
import org.openide.execution.ExecutorTask;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.HelpCtx;
import org.openide.util.actions.CallableSystemAction;
import org.openide.windows.Mode;
import org.openide.windows.OutputWriter;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import org.web3d.x3d.X3DDataObject;
import org.web3d.x3d.X3DEditorSupport;

/**
 *
 * @author brutzman
 */
abstract public class XmlValidationAction extends CallableSystemAction {

    static Properties antProperties = new Properties();
    static File buildF;
    static FileObject buildFO;
    static X3DDataObject dob;
    static FileObject mySrc;
    static File   sourceFile;

    static File   schematronStylesheetFile;
    static File   svrlReportStylesheetFile;
    static File   X3dToClassicVrmlStylesheetFile;

    static String X3dToClassicVrmlResultName;
    static String svrlOutputFileName;
    static String svrlReportFileName;

    private static File   tempDirectory;

    // .xslt stylesheet, must match SchemaValidationAction and invocation task in schematron build.xml !!
    static protected String stylesheetExtension = ".xslt";

    public static File getSchemaTempDirectory()
    {
      initializeFiles();
      return tempDirectory;
    }

    public static void initializeFiles() {
        if (tempDirectory == null) {
            try {
                // we create, populate and refer to a directory so that Ant will have visibility (it doesn't use nbresloc)
                String[] layerDirectoryNames = new String[]{"Schematron","Schemas"};
                tempDirectory = buildTempDirectory(layerDirectoryNames);
                System.out.println("SchematronValidationAction tempDirectory=" + tempDirectory.getAbsolutePath());

                schematronStylesheetFile       = new File(tempDirectory.getAbsolutePath() + File.separator + "X3dSchematronValidityChecks" + stylesheetExtension);
                svrlReportStylesheetFile       = new File(tempDirectory.getAbsolutePath() + File.separator + "SvrlReportText"              + stylesheetExtension);

                X3dToClassicVrmlStylesheetFile = new File(tempDirectory.getAbsolutePath() + File.separator + "X3dToX3dvClassicVrmlEncoding.xslt");

                buildF                         = new File(tempDirectory.getAbsolutePath() + File.separator +  "build.xml");

                buildFO = FileUtil.toFileObject(FileUtil.normalizeFile(buildF));
            }
            catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }

    public void setupAntProperties(TopComponent selectedOne) {
        dob = (X3DDataObject) ((X3DEditorSupport.X3dEditor) selectedOne).getX3dEditorSupport().getDataObject();
        mySrc = dob.getPrimaryFile();
        sourceFile = FileUtil.toFile(mySrc);

        // put results in subdirectory
        svrlOutputFileName          = sourceFile.getParent() + File.separator + "_schematron" + File.separator + sourceFile.getName() + ".svrl";
        svrlReportFileName          = sourceFile.getParent() + File.separator + "_schematron" + File.separator + sourceFile.getName() + ".svrl.txt";
        X3dToClassicVrmlResultName  = sourceFile.getParent() + File.separator + "_schematron" + File.separator + sourceFile.getName() + "v"; // .x3dv

        antProperties = new Properties();
        antProperties.setProperty("sourceFile", sourceFile.getAbsolutePath());
        antProperties.setProperty("schematronStylesheet", schematronStylesheetFile.getAbsolutePath());
        antProperties.setProperty("svrlReportStylesheet", svrlReportStylesheetFile.getAbsolutePath());
        antProperties.setProperty("svrlOutputFile", svrlOutputFileName);
        antProperties.setProperty("svrlReportFile", svrlReportFileName);
        antProperties.setProperty("X3dToClassicVrmlStylesheet", X3dToClassicVrmlStylesheetFile.getAbsolutePath());
        antProperties.setProperty("fileEncoding", "ClassicVRML"); // TODO redundant, trying to ensure VRML97 rules not applied
        antProperties.setProperty("X3dToClassicVrmlOutputFile", X3dToClassicVrmlResultName);
    }

  private static File buildTempDirectory(String[] resourceDirPathsInLayerFile) throws IOException
  {
    // Make the directory in the os temp area
    File tempDir = File.createTempFile("X3D-EditSchema", ""); // no extension, can't make a dir with this call
    String absPath = tempDir.getAbsolutePath();
    tempDir.delete();
    tempDir = new File(absPath);
    tempDir.mkdir();
    tempDir.deleteOnExit();
    for (String resourceLayerDir : resourceDirPathsInLayerFile) {
      // Get the FileObject pointing to our "layer folder"
      FileObject jarredStuff = FileUtil.getConfigRoot().getFileSystem().findResource(resourceLayerDir); //Repository.getDefault().getDefaultFileSystem().findResource(resourceLayerDir);

      if (!jarredStuff.isFolder()) // not a folder
      {
        copyFileToTempDir(tempDir, jarredStuff); // just copy it
      }
      else {
        Enumeration<? extends FileObject> en = jarredStuff.getChildren(true); // true == recurse
        while (en.hasMoreElements()) {
          copyFileToTempDir(tempDir, en.nextElement());
        }
      }
    }
    return tempDir;
  }

    private static void copyFileToTempDir(File targetDir, FileObject fo) throws IOException {
        File f = new File(targetDir, fo.getNameExt());  // to check..assum the path is the one w/in layer
        f.getParentFile().mkdirs();
        if (fo.isFolder()) {
            f.mkdir();
        } else {
            f.createNewFile();
            InputStreamReader rdr = new InputStreamReader(fo.getInputStream());
            copyOneFile(rdr, f);
        }
        f.deleteOnExit();
    }

    private static void copyOneFile(InputStreamReader rdr, File dest) throws IOException {
        BufferedWriter out = new BufferedWriter(new FileWriter(dest));
        try {
            int c;
            while ((c = rdr.read()) != -1) {
                out.write(c);
            }
            rdr.close();
            out.close();
        } catch (IOException ex) {
            System.err.println("Exception moving build file in SchematronValidationAction: " + ex.getLocalizedMessage());
        }
    }
    public boolean performValidationAntTask(String antTask)
    {
        Mode m = WindowManager.getDefault().findMode("editor"); // noi18n
        TopComponent selectedOne = m.getSelectedTopComponent();
        if ((selectedOne == null) || !(selectedOne instanceof X3DEditorSupport.X3dEditor))
        {
            return false; // selected file (if any) is not X3D, and so do not test
        }

        // TODO perform these tasks directly via Java (rather than Ant) for speedier performance
        // look at org.web3d.x3d.actions.conversions.ExportClassicVRMLxsltAction for example

        // Ant tasks
        initializeFiles ();
        setupAntProperties (selectedOne);

        try
        {
            ExecutorTask task = ActionUtils.runTarget(buildFO, new String[]{antTask}, antProperties);
            task.getInputOutput().select();
            OutputWriter ow = task.getInputOutput().getOut();
            ow.println("X3D ant task " + antTask + " begun...");
            return true;
        }
        catch (IOException | IllegalArgumentException ex)
        {
            System.err.println ("Encountered exception while performing ant task " + antTask + ":");
            System.err.println (ex);
            return false;
        }
    }

    @Override
    protected void initialize() {
        super.initialize();
        // see org.openide.util.actions.SystemAction.iconResource() Javadoc for more details
        putValue("noIconInMenu", Boolean.TRUE);
    }

    @Override
    public HelpCtx getHelpCtx() {
        return HelpCtx.DEFAULT_HELP;
    }

    @Override
    protected boolean asynchronous() {
        return false;
    }
}
