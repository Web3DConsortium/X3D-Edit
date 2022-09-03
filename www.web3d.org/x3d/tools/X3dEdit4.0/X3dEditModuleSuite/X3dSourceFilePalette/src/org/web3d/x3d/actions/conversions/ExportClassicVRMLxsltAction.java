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
import javax.swing.JMenuItem;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.web3d.x3d.X3DEditorSupport;

//Export as ClassicVRML using xslt

// Note, this is not enabled...i.e, put into the Export menu (see layer file)
// The reason is that the .xslt used "imports" another from the same "directory".  But in this app,
// the xslt files are in the module jar.  The translater can't find the included file.
// There should be a way to use the URIResolver idea, like DTD catalog, to handle.

// Update: now working.  Didn't work to copy to temp directory (xsltFilesRoot) below,
// Attempted to extract the Transform code from the cookie class (TransformableSupport) so I could
// pass params and work around the import problem.  That works (params below), but a better solution
// is to jam our user catalog into the TransformerFactory every time.  Then we know how to resolve the imports.

@ActionID(id = "org.web3d.x3d.actions.conversions.ExportClassicVRMLxsltAction", category = "File")
@ActionRegistration(displayName = "#CTL_ClassicVRMLxsltAction")
@ActionReferences(value = {
  @ActionReference(path = "Menu/X3D-Edit/Export Model to File", position = 200),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/Export Model to File", position = 200)
})

public final class ExportClassicVRMLxsltAction extends BaseConversionsAction
{
  public static String xsltFile = "X3dToX3dvClassicVrmlEncoding.xslt";

  @Override
  public String transformSingleFile(X3DEditorSupport.X3dEditor ed)
  {
    ConversionsHelper.saveFilePack fp;
    //HashMap<String,Object>params = new HashMap<String,Object>();  // get around xslt catalog problem
    //params.put("fileEncoding","ClassicVRML");
    //params.put("outputDiagnostics","false");
  //  if(BaseConversionsAction.xsltFilesRoot == null)
      fp = xsltOneFile(ed,"X3dTransforms/"+xsltFile,".x3dv",true,false,null); //params);
  //  else {
  //    File target = new File(BaseConversionsAction.xsltFilesRoot,xsltFile);
  //    fp = xsltOneFile(ed,target.getAbsolutePath(),".x3dv",false,true,params);
  //  }
    if(fp != null) {
      if(fp.openInEditor)
        ConversionsHelper.openInEditor(fp.file.getAbsolutePath());
      if(fp.openInBrowser)
        ConversionsHelper.openInBrowser(fp.file.getAbsolutePath());
      return fp.file.getAbsolutePath();
    }
    return null;
  }
  public String origtransformSingleFile(X3DEditorSupport.X3dEditor ed)
  {
    BaseConversionsAction.xsltFilesRoot = null; //todo temp test !!!!
    ConversionsHelper.saveFilePack fp;
    if(BaseConversionsAction.xsltFilesRoot == null)
      fp = xsltOneFile(ed,"X3dTransforms/"+xsltFile,".x3dv",false,false,null);
    else {
      File target = new File(BaseConversionsAction.xsltFilesRoot,xsltFile);
      fp = xsltOneFile(ed,target.getAbsolutePath(),".x3dv",false,true,null);
    }
    if(fp != null) {
//      if(fp.openInEditor)
//        ConversionsHelper.openInEditor(fp.file.getAbsolutePath());
//      if(fp.openInBrowser)
//        ConversionsHelper.openInBrowser(fp.file.getAbsolutePath());
      return fp.file.getAbsolutePath();
    }
    return null;
  }

  @Override
  public String getName()
  {
    return NbBundle.getMessage(getClass(), "CTL_ClassicVRMLxsltAction");
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
    mi.setToolTipText(NbBundle.getMessage(getClass(), "CTL_ClassicVRMLxsltAction_tt"));
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
