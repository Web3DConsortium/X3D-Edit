/*
Copyright (c) 1995-2021 held by the author(s) .  All rights reserved.
Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:
 * Redistributions of source code must retain the above copyright
notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
notice, this list of conditions and the following disclaimer
in the documentation and/or other materials provided with the
distribution.
 * Neither the names of the Naval Postgraduate School (NPS)
Modeling Virtual Environments and Simulation (MOVES) Institute
(http://www.nps.edu and https://MovesInstitute.nps.edu)
nor the names of its contributors may be used to endorse or
promote products derived from this software without specific
prior written permission.
THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.
 */
/**
 * ManageKeyStoreAction.java
 * Created July 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
package org.web3d.x3d.actions.security;

import java.awt.Dialog;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;

@ActionID(id = "org.web3d.x3d.actions.security.ManageKeyStoreAction", category = "Tools")
@ActionRegistration(displayName = "#CTL_ManageKeyStoreAction", 
                    lazy=true) // don't do lazy=false since iconBase no longer gets registered
@ActionReferences(value = {
  @ActionReference(path = "Menu/X3D-Edit/Security/Manage XML Security keystore...", position = 150),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/Security/Manage XML Security keystore...", position = 150)})

public final class ManageKeyStoreAction extends CallableSystemAction
{
  public static ManageKeyStoreAction instance;
  public ManageKeyStoreAction()
  {
    instance=this;
  }
 
  public void performAction(char[] pw)
  {
    BouncyCastleHelper.setup();

    DialogDescriptor descriptor;
    try {
      ManageKeyStorePanel mPan = BouncyCastleHelper.buildManageKeyPanel(pw);
      if(mPan == null)
        return;
      // Want only close button and help button
      JButton closeButt = new JButton(NbBundle.getMessage(getClass(), "MSG_Close")); //"Close");
      descriptor = new DialogDescriptor(
          mPan,                             //component
          NbBundle.getMessage(getClass(),"ManageKeysDialogTitle"), //title
          true,                                                    //modal
          new Object[]{closeButt},                                 //buttons to show
          closeButt,                                               //default button
          DialogDescriptor.DEFAULT_ALIGN,                          //button alignment
          HelpCtx.DEFAULT_HELP,                                    //help context
          null);                                                   //action listener
    }
    catch(OperationCancelledException cex) {  // potentially comes from ManageKeyStorePanel constructor
      return;
    }
    catch(Exception ex) {
      String msg = NbBundle.getMessage(getClass(), "MSG_KeystoreError")+ex.getLocalizedMessage(); //"Keystore error: "
      NotifyDescriptor d =new NotifyDescriptor.Message(msg, NotifyDescriptor.ERROR_MESSAGE);
      DialogDisplayer.getDefault().notify(d);
      return;
    }
    
    Dialog dlg = null;
    try {
      dlg = DialogDisplayer.getDefault().createDialog(descriptor);
      dlg.setResizable(true);
      dlg.pack();
      dlg.setVisible(true);
    }
    finally {
      if (dlg != null)
        dlg.dispose();
    }
  }
  
  @Override
  public void performAction()
  {
    performAction(null); // no password
  }
  
  @Override
  public String getName()
  {
    return NbBundle.getMessage(ManageKeyStoreAction.class, "CTL_ManageKeyStoreAction");
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
   * previous tt.
   * @return what goes into the menu
   */
  @Override
  public JMenuItem getMenuPresenter()
  {
    JMenuItem mi = super.getMenuPresenter();
    mi.setToolTipText(NbBundle.getMessage(getClass(), "TT_ManageKeystore"));
    return mi;
  }

  public static class OperationCancelledException extends Exception {}
}
