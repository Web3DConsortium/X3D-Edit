/*
* Copyright (c) 1995-2022 held by the author(s).  All rights reserved.
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
*       (https://www.nps.edu and https://MovesInstitute.nps.edu)
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
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
import org.openide.windows.OutputWriter;
import org.web3d.x3d.X3DDataObject;
import org.web3d.x3d.X3DEditorSupport;
import org.web3d.x3d.X3DEditorSupport.X3dEditor;
import org.web3d.x3d.actions.conversions.BaseConversionsAction;

@ActionID(id = "org.web3d.x3d.actions.qualityassurance.X3dToX3dvClassicVrmlEncodingXsltErrorsAction", category = "X3D-Edit")
@ActionRegistration(   iconBase = "org/web3d/x3d/resources/xsl_transformation.png",
                    displayName = "#CTL_X3dToX3dvClassicVrmlEncodingXsltErrorsAction", 
                    lazy=true) // don't do lazy=false since iconBase no longer gets registered
@ActionReferences(value = {
  @ActionReference(path = "Menu/&X3D-Edit/&Quality Assurance (QA)", position = 500),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/&Quality Assurance (QA)", position = 500),
})

public final class X3dToX3dvClassicVrmlEncodingXsltErrorsAction extends BaseConversionsAction
{
  X3DEditorSupport.X3dEditor x3dEditor;

  public X3dToX3dvClassicVrmlEncodingXsltErrorsAction()
  {
    this.setEnabled(true);
  }

  @Override
  protected String transformSingleFile(X3dEditor x3dEditor)
  {
   RequestProcessor requestProcessor = getReqProc();
    if(requestProcessor != null)
      requestProcessor.post(new VrmlConversionRunner(x3dEditor));
    return null;
  }

  class VrmlConversionRunner implements Runnable
  {

    X3DEditorSupport.X3dEditor x3dEditor;

    VrmlConversionRunner(X3DEditorSupport.X3dEditor x3dEditor)
    {
      this.x3dEditor = x3dEditor;
    }

    @Override
    public void run()
    {
      try {
        X3DDataObject dob = (X3DDataObject) x3dEditor.getX3dEditorSupport().getDataObject();
        FileObject mySrc = dob.getPrimaryFile();
        File mySrcFile = FileUtil.toFile(mySrc);

        File resultF = File.createTempFile(mySrcFile.getName(), ".x3dv");
        String finishMsg = "Save contents of the "+mySrc.getName()+".x3dv tab to the location of your choice (right click->Save As...).";
        RequestProcessor.Task task = xsltOneFile(x3dEditor, mySrc.getNameExt(), new FileInputStream(mySrcFile),
                resultF, "Schematron/X3dToX3dvClassicVrmlEncoding.xslt", false, null, finishMsg);

        if (task != null) {
          task.waitFinished();

          String title = mySrc.getName()+".x3dv";
          InputOutput iop = IOProvider.getDefault().getIO(title, true);
          OutputWriter ow = iop.getOut();
            try (BufferedReader br = new BufferedReader(new FileReader(resultF))) {
                while (br.ready()) {
                    ow.println(br.readLine());
                } }
        }
      }
      catch (IOException iEx) {
        System.out.println(iEx.getLocalizedMessage());
      }
      reqProc = null;
    }
  }

  private RequestProcessor reqProc = null;
  /**
   * Will return null if the last one hasn't finished yet
   * @return
   */
  private synchronized RequestProcessor getReqProc()
  {
    if(reqProc == null)
      return reqProc = RequestProcessor.getDefault();
    return null;
  }

  @Override
  public String getName()
  {
    return NbBundle.getMessage(X3dToX3dvClassicVrmlEncodingXsltErrorsAction.class, "CTL_X3dToX3dvClassicVrmlEncodingXsltErrorsAction");
  }

  @Override
  public HelpCtx getHelpCtx()
  {
    return HelpCtx.DEFAULT_HELP;
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
    mi.setToolTipText(NbBundle.getMessage(X3dToX3dvClassicVrmlEncodingXsltErrorsAction.class, "CTL_X3dToX3dvClassicVrmlEncodingXsltErrorsAction_tt"));
    return mi;
  }
}
