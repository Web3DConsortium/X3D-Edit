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
package org.web3d.x3d.actions.conversions;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.HelpCtx;
import org.openide.util.actions.CallableSystemAction;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.openide.windows.OutputWriter;
import org.web3d.x3d.X3DEditorSupport.X3dEditor;

/**
 * Xj3DConverterBaseImportAction.java
 * Created on Jun 11, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author mike
 * @version $Id$
 */
public abstract class Xj3DConverterBaseImportAction extends CallableSystemAction
{
  private static JFileChooser fileChooser;
  
  abstract public String getChooserTitle();  
  abstract public String getInputFileType();
  abstract public String getInputFileTypeDescription();
  abstract public String getOutputFileType();  
  abstract public String getPreConversionMessage();
  abstract public String getPostConversionMessage();
  abstract public String getConversionExceptionPrefixMessage();
  abstract public String getConversionCancelledMessage();
  
  @Override
  public void performAction()
  {
    if (fileChooser == null) {
      fileChooser = new JFileChooser();
      fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
      fileChooser.setMultiSelectionEnabled(false);
    }
    fileChooser.setDialogTitle(getChooserTitle()); //NbBundle.getMessage(getClass(), "CTL_ImportX3dbOpenTitle"));
    fileChooser.resetChoosableFileFilters();
    fileChooser.setFileFilter(new myFileTypeFilter());

    int retVal = fileChooser.showOpenDialog(null);
    if (retVal != JFileChooser.APPROVE_OPTION)
      return;
    
    InputOutput io = IOProvider.getDefault().getIO("Output", false);
    io.setFocusTaken(true);
    OutputWriter ow = io.getOut();

    try {
      FileObject mySrc = FileUtil.createData(fileChooser.getSelectedFile());

      File mySrcF = FileUtil.toFile(mySrc);
       // Use some code in this class:
      //BaseConversionsAction bca = new BCA();
      ConversionsHelper.saveFilePack sfp = ConversionsHelper.getDestinationFile(mySrcF,mySrc.getName()+"."+getOutputFileType());
      if(sfp == null) {
        ow.println(this.getConversionCancelledMessage());
        return;
      }
 
      String myOutFPath = sfp.file.getAbsolutePath();
      
      ow.println(getPreConversionMessage());
      ConversionsHelper.getXj3dConverter().convert(mySrcF, myOutFPath);
      ow.println(getPostConversionMessage());
      
      if(sfp.openInEditor)
        ConversionsHelper.openInEditor(myOutFPath);
      if(sfp.openInBrowser)
        ConversionsHelper.openInBrowser(myOutFPath);
    }
    catch(Throwable t) {
      ow.println(getConversionExceptionPrefixMessage());
    }   
  }
  
  class myFileTypeFilter extends FileFilter
  { 
    String fileType = getInputFileType();
    String fileTypeUC = fileType.toUpperCase();
    
    @Override
    public boolean accept(File f)
    {
      if (f.isDirectory())
        return true;
      
      String extension = FileUtil.toFileObject(f).getExt();
      if (extension != null) {
        if (extension.equals(fileType) || extension.equals(fileTypeUC))
          return true;       
      }
      return false;
    }

    //The description of this filter
    @Override
    public String getDescription()
    {
      return getInputFileTypeDescription();
    }    
  }
  // Reuse some source
  public static class BCA extends BaseConversionsAction
  {
    @Override protected String transformSingleFile(X3dEditor xed){return "";}
    @Override public HelpCtx getHelpCtx(){return null;}
    @Override public String getName(){return "";}      
  }

}
