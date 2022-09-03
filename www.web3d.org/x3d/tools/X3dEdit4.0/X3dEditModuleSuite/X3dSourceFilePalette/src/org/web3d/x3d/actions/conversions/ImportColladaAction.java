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

import java.awt.HeadlessException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.swing.JFileChooser;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.cookies.OpenCookie;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;
import org.web3d.x3d.InputOutputReporter;
import xj3d.filter.CDFFilter;

@ActionID(id = "org.web3d.x3d.actions.conversions.ImportColladaAction", category = "File")
@ActionRegistration(displayName = "#CTL_ColladaImportAction")
@ActionReferences(value = {
  @ActionReference(path = "Menu/X3D-Edit/Import Model from File", position = 100),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/Import Model from File", position = 100)
})

public final class ImportColladaAction extends CallableSystemAction
{
  private static JFileChooser fChooser;

  @Override
  public void performAction()
  {
    String taskErrorStage;
      
    if(fChooser == null) {
      fChooser = new JFileChooser();
      fChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      fChooser.setMultiSelectionEnabled(false);
    }
    fChooser.setDialogTitle("Select Collada scene graph");
    
    int retVal = fChooser.showOpenDialog(null);
    if(retVal != JFileChooser.APPROVE_OPTION)
      return;
    
    File tmpOutFile;
    taskErrorStage = "Error creating temporary .x3d file";
    try {
      tmpOutFile = File.createTempFile("x3dEditTemporaryFile", ".x3d");
      tmpOutFile.deleteOnExit();
      
      taskErrorStage = "Error processing collada file ";
      try {
        CDFFilter.main(new String[]{"Identity",
                       fChooser.getSelectedFile().getAbsolutePath(),
                       tmpOutFile.getAbsolutePath()});
      }
      catch(Throwable t) {
        if(!t.getClass().getName().endsWith("ExitSecurityException")) {
          showOut("Exception from translator: "+t.getLocalizedMessage());
          return;
        }
      }
      
      String newName = fChooser.getSelectedFile().getName();
      newName = newName.substring(0,newName.lastIndexOf('.'));
      newName = newName + "ImportedFromCollada.x3d";
      File newFile = new File(fChooser.getSelectedFile().getParentFile(),newName);
      fChooser.setSelectedFile(newFile);
      fChooser.setDialogTitle("Save translated Collada file as...");
    
      retVal = fChooser.showSaveDialog(null);
      if(retVal == JFileChooser.CANCEL_OPTION)
        return;
      
      newFile = fChooser.getSelectedFile();
      taskErrorStage = "Error copying file ";
    
      BufferedInputStream bis = new BufferedInputStream (new FileInputStream (tmpOutFile));
      BufferedOutputStream bos= new BufferedOutputStream(new FileOutputStream(newFile));
      int b;
      while((b = bis.read()) != -1) {
        bos.write(b);
      }
      bis.close();
      bos.close();
      
      FileObject fo = FileUtil.toFileObject(newFile);
      DataObject dobj = DataObject.find(fo);
      OpenCookie op = dobj.getLookup().lookup(OpenCookie.class);
      op.open();
    }
    catch(IOException | HeadlessException ex) {
      NotifyDescriptor d = new NotifyDescriptor.Message(taskErrorStage +"("+ex.getLocalizedMessage()+")",
                                     NotifyDescriptor.ERROR_MESSAGE);
      DialogDisplayer.getDefault().notify(d);
    }
    
    //String fsep = System.getProperty("file.separator");
    //String pathToJava = System.getProperty("java.home") + fsep + "bin" + fsep + "java";
    //String classToRun = "xj3d.filter.CDFFilter";
    //String cp = System.getProperty("java.class.path");
//    ProcessBuilder pb = new ProcessBuilder(pathToJava, "-classpath",cp,
//        classToRun,
//        filter,
//        fChooser.getSelectedFile().getAbsolutePath(),
//        tmpOutFile.getAbsolutePath());
  }
  protected void showOut(String msg)
  {
    InputOutputReporter console = new InputOutputReporter("Collada translation");
    java.util.ResourceBundle bun = NbBundle.getBundle(getClass());

    console.message(msg);
    console.moveToFront(true);    
  }

  @Override
  public String getName()
  {
    return NbBundle.getMessage(ImportColladaAction.class, "CTL_ColladaImportAction");
  }

  @Override
  protected void initialize()
  {
    super.initialize();
    // see org.openide.util.actions.SystemAction.iconResource() Javadoc for more details
    putValue("noIconInMenu", Boolean.TRUE);
  }

  @Override
  public HelpCtx getHelpCtx()
  {
    return HelpCtx.DEFAULT_HELP;
  }

  @Override
  protected boolean asynchronous()
  {
    return false;
  }
  
}
