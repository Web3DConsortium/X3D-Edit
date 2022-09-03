/*
* Copyright (c) 1995-2021 held by the author(s).  All rights reserved.
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

package org.web3d.x3d.actions.conversions;

import iicm.vrml.vrml2x3d.Vrml97Converter;
import java.io.File;
import java.io.PrintStream;
import java.util.MissingResourceException;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;
import org.openide.util.RequestProcessor.Task;
import org.openide.util.actions.CallableSystemAction;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.openide.windows.OutputWriter;

@ActionID(id = "org.web3d.x3d.actions.conversions.ImportVrml97Action", category = "File")
@ActionRegistration(displayName = "#CTL_ImportVrml97Action")
@ActionReferences(value = {
  @ActionReference(path = "Menu/X3D-Edit/Import Model from File", position = 150),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/Import Model from File", position = 150)
})

public final class ImportVrml97Action extends CallableSystemAction
{
  private static JFileChooser fileChooser;
  private OutputWriter ow;
  private final Class baseClass = getClass();
  private PrintStream origSysOut=null;
  
  @Override
  public void performAction()
  {
    if (fileChooser == null) {
      fileChooser = new JFileChooser();
      fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      fileChooser.setMultiSelectionEnabled(false);
      fileChooser.setDialogTitle(NbBundle.getMessage(baseClass, "CTL_ImportVrml97OpenTitle")); //"Select VRML97 scene file (.wrl)");
      fileChooser.setFileFilter(new vrmlFileTypeFilter());
    }
    int retVal = fileChooser.showOpenDialog(null);
    if (retVal != JFileChooser.APPROVE_OPTION)
      return;

    String taskErrorStage="";
    try {
      taskErrorStage = NbBundle.getMessage(baseClass, "CTL_ImportVrml97Error1"); //"Error creating temp file ";
      
      File inFile = fileChooser.getSelectedFile();
      FileObject inFobj = FileUtil.toFileObject(inFile);
      
      InputOutput io = IOProvider.getDefault().getIO("Output", false);
      ow = io.getOut();
      moveToFront(io,ow,false);
      
    // Use some code in this class:
     // BaseConversionsAction bca = new Xj3DConverterBaseImportAction.BCA();
      ConversionsHelper.saveFilePack sfp = ConversionsHelper.getDestinationFile(inFile,inFobj.getName()+".x3d");
      
      if(sfp == null) {
        ow.println(NbBundle.getMessage(baseClass, "CTL_ImportVrml97Cancelled"));
        return;
      }
      File outFile = sfp.file;

      taskErrorStage = NbBundle.getMessage(baseClass, "CTL_ImportVrml97Error2"); //"Error processing VRML97 file ";
      origSysOut = iicm.SystemOut.getSysOut();
      iicm.SystemOut.setSysOut(new NbSysOutFilter(io));
      
      // The following will convert a Vrml97 file to X3D,
      // then load it into the editor
      
      Task tsk = RequestProcessor.getDefault().post(new ImportVrml97Thread(inFile,outFile));
      RequestProcessor.getDefault().post(new cleanUpTask(tsk,sfp));
    }
    catch (MissingResourceException t) {
      ow.println(taskErrorStage + t.getLocalizedMessage());
    }
  }
  
  // copied from InputOutputReporter
  public final void moveToFront(InputOutput tab, OutputWriter ow, boolean lastMessage)
  {
    boolean wasFocusTaken = tab.isFocusTaken();
    tab.select();
    tab.setFocusTaken(true);
    ow.write("\r");
    tab.setFocusTaken(wasFocusTaken);
    if (lastMessage) {
      ow.close();
    }
  }

  class cleanUpTask implements Runnable
  {
    private RequestProcessor.Task task;
    private ConversionsHelper.saveFilePack sfp;
    
    cleanUpTask(RequestProcessor.Task task, ConversionsHelper.saveFilePack sfp)
    {
      this.task = task;
      this.sfp = sfp;
    }
    
    @Override
    public void run()
    {
      try {
        task.waitFinished(20000);
        
        if(sfp.openInEditor) 
           ConversionsHelper.openInEditor(sfp.file.getAbsolutePath());       
        if(sfp.openInBrowser) 
          ConversionsHelper.openInBrowser(sfp.file.getAbsolutePath());
      }
      catch(InterruptedException e) {
        ow.println(NbBundle.getMessage(baseClass, "CTL_ImportVrml97Error3"));//"ImportVrml97Action.cleanUpTask timed out");
      }
      finally {
        if(origSysOut != null)
          iicm.SystemOut.setSysOut(origSysOut);
      }
    }
    
  }
  @Override
  public String getName()
  {
    return NbBundle.getMessage(baseClass, "CTL_ImportVrml97Action");
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
  
  class ImportVrml97Thread implements Runnable
  {
    File inpF, outF;
    ImportVrml97Thread(File inf, File outf)
    {
      this.inpF = inf;
      this.outF = outf;
    }
    @Override
    public void run()
    {
      try {
        new Vrml97Converter().convert(inpF.getAbsolutePath(),outF.getAbsolutePath(),true);
      }
      catch(Exception e) {
        ow.println(NbBundle.getMessage(baseClass, "CTL_ImportVrml97Error4")+e.getLocalizedMessage()); //);"Error processing VRML97 file: "
      }
    }
    
  }
  public class vrmlFileTypeFilter extends FileFilter
  {
    @Override
    public boolean accept(File f)
    {
      if (f.isDirectory()) {
        return true;
      }
      
      String extension = FileUtil.toFileObject(f).getExt();
      if (extension != null) {
        if (extension.equals("wrl") ||
            extension.equals("WRL")) {
          return true;
        }
      }

      return false;
    }

    //The description of this filter
    @Override
    public String getDescription()
    {
      return "VRML97 scenes (.wrl)";
    }
  }
}
