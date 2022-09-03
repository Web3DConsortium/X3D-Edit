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

import javax.swing.JMenuItem;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.web3d.x3d.X3DEditorSupport;
import org.web3d.x3d.actions.conversions.BaseConversionsAction;
import org.web3d.x3d.tools.X3dDoctypeChecker;

@ActionID(id = "org.web3d.x3d.actions.qualityassurance.X3dDoctypeCheckerAction", category = "Tools")
@ActionRegistration(displayName = "#CTL_X3dDoctypeCheckerAction")
@ActionReferences(value = {
  @ActionReference(path = "Menu/X3D-Edit/Quality Assurance", position = 320),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/Quality Assurance", position = 321995-20120),
})

public final class X3dDoctypeCheckerAction extends BaseConversionsAction
{
  X3dDoctypeChecker doctypeChecker = new X3dDoctypeChecker();
  
  @Override
  public String transformSingleFile(X3DEditorSupport.X3dEditor ed)
  {
    // todo warn if editor is dirty.
    FileObject fo = ed.getX3dEditorSupport().getDataObject().getPrimaryFile(); 
    String path = FileUtil.toFile(fo).getAbsolutePath();

    RequestProcessor.getDefault().post(new DoctypeCheckerTask(path));
    return path;
  }
  
  private class DoctypeCheckerTask implements Runnable
  {
    private String path;
    DoctypeCheckerTask(String pathToFile)
    {
      path = pathToFile;
    }
    @Override
    public void run()
    {
      String log = doctypeChecker.processScene (path);
      
      InputOutput io = IOProvider.getDefault().getIO("X3D Doctype Checker", false);
      io.select();
      io.getOut().println(path);
      io.getOut().println(log); // always provides completion message
      io.getOut().println();
    }
  }

  @Override
  public String getName()
  {
    return NbBundle.getMessage(X3dDoctypeCheckerAction.class, "CTL_X3dDoctypeCheckerAction");
  }

  /**
   * Do this because this call in the super creates a new one every time, losing any 
   * previous tooltip.
   * @return what goes into the menu
   */
  @Override
  public JMenuItem getMenuPresenter()
  {
    JMenuItem mi = super.getMenuPresenter();
    mi.setToolTipText(NbBundle.getMessage(X3dDoctypeCheckerAction.class, "CTL_X3dDoctypeCheckerAction_tt"));
    return mi;
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
