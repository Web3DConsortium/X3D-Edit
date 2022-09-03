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

import java.io.*;
import javax.swing.JMenuItem;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.openide.windows.OutputWriter;
import org.web3d.x3d.X3DEditorSupport;
import org.web3d.x3d.actions.conversions.BaseConversionsAction;

@ActionID(id = "org.web3d.x3d.actions.qualityassurance.SchematronValidationAction", category = "Tools")
@ActionRegistration(displayName = "#CTL_SchematronValidationAction")
@ActionReferences(value = {
  @ActionReference(path = "Menu/X3D-Edit/Quality Assurance", position = 360),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/Quality Assurance", position = 360),
})

public class SchematronValidationAction extends BaseConversionsAction
{
  @Override
  public String transformSingleFile(X3DEditorSupport.X3dEditor ed)
  {
    RequestProcessor rp = getReqProc();
    if(rp != null)
      rp.post(new SchematronValidationRunner(ed));
    return null;
  }

  class SchematronValidationRunner implements Runnable
  {
    X3DEditorSupport.X3dEditor ed;

    SchematronValidationRunner(X3DEditorSupport.X3dEditor ed)
    {
      this.ed = ed;
    }

    @Override
    public void run()
    {
      try {
        File firstResultF = File.createTempFile(NbBundle.getMessage(SchematronValidationAction.class, "SchematronIntermediate"), ".svrl");//SchematronIntermediate", ".svrl");
        RequestProcessor.Task task = xsltOneFile(ed, firstResultF, "Schematron/X3dSchematronValidityChecks.xslt", false, null, null);

        if (task != null) {
          task.waitFinished();

          // 2nd pass
          File f2out = File.createTempFile(NbBundle.getMessage(SchematronValidationAction.class, "SchematronReport"),".txt"); //SchematronReport", ".txt");

          task = xsltOneFile(ed, firstResultF.getName(), new FileInputStream(firstResultF), f2out, "Schematron/SvrlReportText.xslt", false, null, NbBundle.getMessage(SchematronValidationAction.class, "SeeSchematronResults"));
          if (task != null) {
            task.waitFinished();
            String title = NbBundle.getMessage(SchematronValidationAction.class, "Schematron_Result")+" ("+ed.getX3dEditorSupport().getDataObject().getPrimaryFile().getNameExt()+")";
            InputOutput iop = IOProvider.getDefault().getIO(title, true);
            OutputWriter ow = iop.getOut();
            ow.println(NbBundle.getMessage(SchematronValidationAction.class, "BeginErrors"));
              try (BufferedReader br = new BufferedReader(new FileReader(f2out))) {
                  while (br.ready()) {
                      ow.println(br.readLine());
                  } 
              }
            ow.println(NbBundle.getMessage(SchematronValidationAction.class, "EndErrors"));
          }
        }
      }
      catch (IOException iEx) {
        System.err.println(iEx.getLocalizedMessage());
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
    return NbBundle.getMessage(SchematronValidationAction.class, "CTL_SchematronValidationAction");
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

  /**
   * Do this because this call in the super creates a new one every time, losing any
   * previous tooltip.
   * @return what goes into the menu
   */
  @Override
  public JMenuItem getMenuPresenter()
  {
    JMenuItem mi = super.getMenuPresenter();
    mi.setToolTipText(NbBundle.getMessage(SchematronValidationAction.class, "CTL_SchematronValidationAction_tt"));
    return mi;
  }
}
