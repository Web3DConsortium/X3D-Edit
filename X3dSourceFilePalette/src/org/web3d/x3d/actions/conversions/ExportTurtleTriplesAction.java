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
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.web3d.x3d.X3DEditorSupport;

@ActionID(id = "org.web3d.x3d.actions.conversions.ExportTurtleTriplesAction", category = "Tools")

@ActionRegistration(
        iconBase = "org/web3d/x3d/resources/SemanticWebCube24.png",
     displayName = "#CTL_ExportTurtleTriplesAction",
             lazy=true) // don't do lazy=false since iconBase no longer gets registered
// https://www.w3.org/2007/10/sw-logos.html
// C:\x3d-code\www.web3d.org\x3d\tools\X3dEdit3.3\X3dEditorSuite\websites\savage.nps.edu\X3D-Edit\images\X3D-EditSplash4.vsdx

@ActionReferences(value = {
  @ActionReference(path = "Menu/X3D-Edit/Author Workflow", position = 100),
  @ActionReference(path = "Menu/X3D-Edit/Export Model to File", position = 100),
  @ActionReference(path = "Toolbars/Author Workflow", position = 100),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/Author Workflow", position = 100),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/Export Model to File", position = 100)
})

public final class ExportTurtleTriplesAction extends BaseConversionsAction
{
  public static String X3dToTurtleXsltStylesheet = "X3dToTurtle.xslt";

  @Override
  public String transformSingleFile(X3DEditorSupport.X3dEditor ed)
  {
    ConversionsHelper.saveFilePack fp;
  //  if (BaseConversionsAction.xsltFilesRoot == null)
      fp = xsltOneFile(ed, "X3dTransforms/" + X3dToTurtleXsltStylesheet, ".ttl", true, false, null);
  //  else {
  //    File target = new File(BaseConversionsAction.xsltFilesRoot, xsltFile);
  //    fp = xsltOneFile(ed, target.getAbsolutePath(), ".html", false, true, null);
  //  }
    if (fp != null) {
      if (fp.openInEditor)
        ConversionsHelper.openInEditor(fp.file.getAbsolutePath());
      if (fp.openInBrowser)
        ConversionsHelper.openInBrowser(fp.file.getAbsolutePath());
      return fp.file.getAbsolutePath();
    }
    return null;
  }

  @Override
  public String getName()
  {
    return NbBundle.getMessage(getClass(), "CTL_ExportTurtleTriplesAction");
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
    mi.setToolTipText(NbBundle.getMessage(getClass(), "CTL_ExportTurtleTriplesAction_tt"));
    return mi;
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
