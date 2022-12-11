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
 * CTL_ManageKeyStoreExplorerAction.java
 * Created June 2009
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @authors Don Brutzman and Mike Bailey
 * @version $Id$
 */
package org.web3d.x3d.actions.security;

import javax.swing.JMenuItem;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;
import org.web3d.x3d.options.OptionsMiscellaneousX3dPanel;
import org.web3d.x3d.options.X3dOptions;

@ActionID(id = "org.web3d.x3d.actions.security.ManageKeyStoreExplorerAction", category = "X3D-Edit")
@ActionRegistration(   iconBase = "org/web3d/x3d/resources/KeyWikimedia-60_Ŝlosilo_1.svg.32x32.png",
                    displayName = "#CTL_ManageKeyStoreExplorerAction", 
                    lazy=true) // don't do lazy=false since iconBase no longer gets registered
@ActionReferences(value = {
  @ActionReference(path = "Menu/&X3D-Edit/XML &Security", position = 160),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/XML &Security", position = 160)})

/**
 * Use KeyStoreExplorer tool to manage public/private key pairs during runtime.
 */
public final class ManageKeyStoreExplorerAction extends CallableSystemAction
{
  public static ManageKeyStoreExplorerAction instance;
  public ManageKeyStoreExplorerAction()
  {
    instance=this;
  }
 
  public void performAction(char[] pw)
  {
      OptionsMiscellaneousX3dPanel.externalProcessLaunch(X3dOptions.getKeystoreExplorerPlayerPath());
  }
  
  @Override
  public void performAction()
  {
    performAction(null); // no password
  }
  
  @Override
  public String getName()
  {
    return NbBundle.getMessage(ManageKeyStoreExplorerAction.class, "CTL_ManageKeyStoreExplorerAction");
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
    mi.setToolTipText(NbBundle.getMessage(getClass(), "TT_ManageKeystoreExplorer"));
    return mi;
  }

  public static class OperationCancelledException extends Exception {}
}
