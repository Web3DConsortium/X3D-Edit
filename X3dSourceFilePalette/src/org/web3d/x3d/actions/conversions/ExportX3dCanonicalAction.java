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
import org.web3d.x3d.X3DEditorSupport;
import org.web3d.x3d.tools.x3db.X3dCanonicalizer;

@ActionID(id = "org.web3d.x3d.actions.conversions.ExportX3dCanonicalAction", category = "File")
@ActionRegistration(displayName = "#CTL_CanonicalizeAction")
@ActionReferences(value = {
  @ActionReference(path = "Menu/X3D-Edit/Compression", name = "org-web3d-x3d-actions-conversions-ExportX3dCanonicalAction", position = 150),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/Compression", position = 150)
})

public final class ExportX3dCanonicalAction extends BaseConversionsAction
{
  public ExportX3dCanonicalAction()
  {
    super.initialize();
  }
  
  @Override
  public String transformSingleFile(X3DEditorSupport.X3dEditor ed)
  {
    // todo warn if editor is dirty.
    FileObject fo = ed.getX3dEditorSupport().getDataObject().getPrimaryFile();    
    RequestProcessor.getDefault().post(new CanonTask(FileUtil.toFile(fo).getAbsolutePath()));
    
//    if(resultFile != null)
//     openInEditor(resultFile);
    return null;
  }
  
  private class CanonTask implements Runnable
  {
    private final String path;
    CanonTask(String pathToFile)
    {
      path = pathToFile;
    }
    @Override
    public void run()
    {
      X3dCanonicalizer.main(new String[]{path});
    }
  }
  @Override
  public String getName()
  {
    return NbBundle.getMessage(getClass(), "CTL_CanonicalizeAction");
  }

  @Override
  protected void initialize()
  {
    super.initialize();
    // see org.openide.util.actions.SystemAction.iconResource() Javadoc for more details
    putValue("noIconInMenu", Boolean.TRUE);
  }

  /**
   * Do this because this call in the super creates a new one every time, losing any
   * previous tt.
   * @return what goes into the menu
   */
  @Override
  public JMenuItem getMenuPresenter()
  {
    JMenuItem mi = super.getMenuPresenter();
    mi.setToolTipText(NbBundle.getMessage(getClass(), "CTL_CanonicalizeAction_tt"));
    return mi;
  }

  @Override
  protected String iconResource()
  {
    return "org/web3d/x3d/resources/CheckMark.png";
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
