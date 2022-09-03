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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.GZIPOutputStream;
import javax.swing.JMenuItem;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.web3d.x3d.X3DDataObject;
import org.web3d.x3d.X3DEditorSupport;

@ActionID(id = "org.web3d.x3d.actions.conversions.GzipX3dAction", category = "Tools")
@ActionRegistration(displayName = "#CTL_GzipX3dAction")
@ActionReferences(value = {
  @ActionReference(path = "Menu/X3D-Edit/Compression", position = 1000),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/Compression", position = 1000)
})

public class GzipX3dAction extends BaseConversionsAction
{
  @Override
 public String transformSingleFile(X3DEditorSupport.X3dEditor xed)
  {
    //xsltOneFile(ed,"X3dTransforms/X3dToX3dvClassicVrmlEncoding.xslt",".x3dv");
    Node[] node = xed.getActivatedNodes();

    X3DDataObject dob = (X3DDataObject) xed.getX3dEditorSupport().getDataObject();
    FileObject mySrc = dob.getPrimaryFile();

    File mySrcF = FileUtil.toFile(mySrc);
    File myOutF = new File(mySrcF.getParentFile(), mySrc.getName() + ".x3d.gz");
    
    TransformListener co = TransformListener.getInstance();
    co.message(NbBundle.getMessage(getClass(), "Gzip_compression_starting"));
    co.message(NbBundle.getMessage(getClass(), "Saving_as_")+myOutF.getAbsolutePath());
    co.moveToFront();
    co.setNode(node[0]);
    
    try {
      FileInputStream fis = new FileInputStream(mySrcF);
      GZIPOutputStream gzos = new GZIPOutputStream(new FileOutputStream(myOutF));

      byte [] buf = new byte[4096];
      int ret;
      while((ret=fis.read(buf)) >0)
        gzos.write(buf, 0, ret);
      gzos.close();
    }
    catch(Exception ex) {
      co.message(NbBundle.getMessage(getClass(),"Exception:__")+ex.getLocalizedMessage());
      return null;
    }
    co.message(NbBundle.getMessage(getClass(), "Gzip_compression_complete"));
    return myOutF.getAbsolutePath();
  }

  @Override
  public String getName()
  {
    return NbBundle.getMessage(getClass(), "CTL_GzipX3dAction");
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
    mi.setToolTipText(NbBundle.getMessage(getClass(), "CTL_GzipX3dAction_tt"));
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
