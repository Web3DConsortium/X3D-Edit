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
 * ManageKeyStorePortecleAction.java
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
import net.sf.portecle.FPortecle;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;

@ActionID(id = "org.web3d.x3d.actions.security.ManageKeyStorePortecleAction", category = "Tools")
@ActionRegistration(displayName = "#CTL_ManageKeyStorePortecleAction", 
                    lazy=true) // don't do lazy=false since iconBase no longer gets registered
@ActionReferences(value = {
  @ActionReference(path = "Menu/X3D-Edit/Security/Manage XML Security keystore...", position = 160),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/Security/Manage XML Security keystore...", position = 160)})

public final class ManageKeyStorePortecleAction extends CallableSystemAction
{
  public static ManageKeyStorePortecleAction instance;
  public ManageKeyStorePortecleAction()
  {
    instance=this;
  }
 
  public void performAction(char[] pw)
  {

//    net.sf.portecle.FPortecle portecleFrameGUI = new FPortecle();
//
//    String [] initializationParameters = { }; // TODO insert initialization string(s) pointing to default keystore, if any
//
//    portecleFrameGUI.main (initializationParameters);  // TODO is there a better way to invoke this frame?

    // TODO exit exception, maybe there is a System.exit() in this class?  Probably due to use of main() method.
    // org.netbeans.ExitSecurityException: Illegal attempt to exit early

      // Mike's take on this:

   FPortecle portecleFrame = new FPortecle();
//   portecleFrame.setLocation(500,400);
//   portecleFrame.setVisible(true);

    // The above will launch the portecle frame.  It's not useable as is, however, because:
    // 1. we need to pass it the X3D-Edit keystore path
    // 2. it is a frame with its own menu bar, file menu and exit menuitem...which does a System.exit()...doesn't work well
    //    within X3D edit.  need to subclass or something.
    // 3. the frame needs to be disposed of when it closes
    // 4. it tries to twiddle with the look and feel...causes exceptions
    // 5. probably others

  }
  
  @Override
  public void performAction()
  {
    performAction(null); // no password
  }
  
  @Override
  public String getName()
  {
    return NbBundle.getMessage(ManageKeyStorePortecleAction.class, "CTL_ManageKeyStorePortecleAction");
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
    mi.setToolTipText(NbBundle.getMessage(getClass(), "TT_ManageKeystorePortecle"));
    return mi;
  }

  public static class OperationCancelledException extends Exception {}
}
