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

package org.web3d.x3d.actions.conversions;

import java.awt.Dialog;
import javax.swing.JMenuItem;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.awt.ActionID;
import org.openide.awt.ActionRegistration;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;
import org.web3d.xmsf.xsbc.apps.comparison.ui.ComparisonPanel;

@ActionID(id = "org.web3d.x3d.actions.conversions.ExportXSBCCompressionAction", category = "X3D-Edit")
@ActionRegistration(displayName = "#CTL_XSBCCompressionAction", 
                    lazy=true) // don't do lazy=false since iconBase no longer gets registered

public final class ExportXSBCCompressionAction extends CallableSystemAction
{
  //todo remove constructor when working
  public ExportXSBCCompressionAction()
  {
    setEnabled(false);
  }
  
  @Override
  public void performAction()
  {
    // Bogus
    DialogDescriptor descriptor = new DialogDescriptor(new ComparisonPanel(),
            "XSBC Compression",//titlePrefix+" "+name,
            true,
            DialogDescriptor.OK_CANCEL_OPTION,
            DialogDescriptor.OK_OPTION,
            //todo....helpcontext here see materialcustomizer.java
            null);
    
    //descriptor.setAdditionalOptions(new Object[]{new JButton(new HelpAction("?"))});
    // comment above because a help button is automatically displayed by the dialog
    
    Dialog dialog = DialogDisplayer.getDefault().createDialog(descriptor);
    dialog.setVisible(true);
    dialog.dispose();

  }

  @Override
  public String getName()
  {
    return NbBundle.getMessage(getClass(), "CTL_XSBCCompressionAction");
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
    mi.setToolTipText("Under development -- "+NbBundle.getMessage(getClass(), "CTL_XSBCCompressionAction_tt"));
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
