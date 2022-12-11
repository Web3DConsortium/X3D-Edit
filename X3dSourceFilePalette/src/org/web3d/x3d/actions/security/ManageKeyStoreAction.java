/*
Copyright (c) 1995-2022 held by the author(s).  All rights reserved.
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
(https://www.nps.edu and https://MovesInstitute.nps.edu)
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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;
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
import org.web3d.x3d.actions.LaunchIssueReportEmailAction;
import static org.web3d.x3d.palette.items.BaseCustomizer.MAILTO_TOOLTIP;

@ActionID(id = "org.web3d.x3d.actions.security.ManageKeyStoreAction", category = "X3D-Edit")
@ActionRegistration(   iconBase = "org/web3d/x3d/resources/KeyWikimedia-60_Ŝlosilo_1.svg.32x32.png",
                    displayName = "#CTL_ManageKeyStoreAction", 
                    lazy=true) // don't do lazy=false since iconBase no longer gets registered
@ActionReferences(value = {
  @ActionReference(path = "Menu/&X3D-Edit/XML &Security", position = 150),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/XML &Security", position = 150)})

public final class ManageKeyStoreAction extends CallableSystemAction
{
    public static ManageKeyStoreAction instance;
    final JButton reportButton = new JButton("Report");
    final ActionListener emailReportActionListener = (ActionEvent event) ->
    {
       LaunchIssueReportEmailAction.sendBrowserTo(LaunchIssueReportEmailAction.MAILTO_REPORT_URL + "X3D-Edit Manage KeyStore");
    };
  
    public ManageKeyStoreAction()
    {
      instance=this;
    }
 
    /**
     * Manage keystore
     * @param keystorePassword password for the keystore
     */
    public void performAction(char[] keystorePassword)
  {
    BouncyCastleHelper.setup();

    DialogDescriptor dialogDescriptor;
    try {
      ManageKeyStorePanel manageKeyStorePanel = BouncyCastleHelper.buildManageKeyPanel(keystorePassword);
      if(manageKeyStorePanel == null || manageKeyStorePanel.getKeystore() == null) // check for proper initialization
        return;
      // Want only close button and help button
      JButton closeButton = new JButton(NbBundle.getMessage(getClass(), "MSG_Close")); //"Close");
      dialogDescriptor = new DialogDescriptor(
          manageKeyStorePanel,                                   //component
          NbBundle.getMessage(getClass(),"ManageKeysDialogTitle"), //title
          true,                                                    //modal
          new Object[]{closeButton, reportButton},                                    //buttons to show
          closeButton,                                        //default button
          DialogDescriptor.DEFAULT_ALIGN,                     //button alignment
          HelpCtx.DEFAULT_HELP,                                  //help context
          null);                                                     //action listener
    }
    catch(OperationCancelledException cex) {  // potentially comes from ManageKeyStorePanel constructor
      return;
    }
    catch(Exception ex) {
      String msg = NbBundle.getMessage(getClass(), "MSG_KeystoreError") + ex.getLocalizedMessage(); //"Keystore error: "
      NotifyDescriptor notifyDescriptor = new NotifyDescriptor.Message(msg, NotifyDescriptor.ERROR_MESSAGE);
      DialogDisplayer.getDefault().notify(notifyDescriptor);
      return;
    }
    
    Dialog dialog = null;
    try {
      dialog = DialogDisplayer.getDefault().createDialog(dialogDescriptor);
      dialog.setResizable(true);
      dialog.pack();
      dialog.setVisible(true);
    }
    finally {
      if (dialog != null)
        dialog.dispose();
    }
  }
  
    /**
     * Default entry point from NetBeans
     */
    @Override
  public void performAction()
  {
    performAction(null); // no password, default action
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

    // null pointer can happen during a unit test ?!  perhaps artifact of prior javahelp dependency...
    if (reportButton != null)
    {
        reportButton.setToolTipText(MAILTO_TOOLTIP);
        reportButton.addActionListener(emailReportActionListener);
        reportButton.setVisible(true);
        reportButton.setHorizontalAlignment(SwingConstants.LEFT);
    }
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
