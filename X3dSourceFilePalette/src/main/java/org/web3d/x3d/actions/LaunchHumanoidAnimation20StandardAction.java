/*
Copyright (c) 1995-2025 held by the author(s).  All rights reserved.
 
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

package org.web3d.x3d.actions;

import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;
import static org.web3d.x3d.actions.BaseViewAction.HANIM20_STANDARD;

/**
 * Launch HAnim2 standard
 * @author brutzman
 */
@ActionID(id = "org.web3d.x3d.actions.LaunchHumanoidAnimationStandardAction", category = "X3D-Edit")
@ActionRegistration(   iconBase = "org/web3d/x3d/resources/ISO_favicon32x32.png",
                    displayName = "#CTL_LaunchHumanoidAnimation20StandardAction",
                            lazy=true)
@ActionReferences( value = {
  @ActionReference(path = "Menu/&X3D-Edit/Web3D &Standards", position = 180),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/Web3D &Standards", position = 180)
})

public final class LaunchHumanoidAnimation20StandardAction extends CallableSystemAction
{
  @Override
  public void performAction()
  {
    // looking for javahelp
    // Help hlp = Lookup.getDefault().lookup(org.netbeans.api.javahelp.Help.class);
    // hlp.showHelp(HelpCtx.findHelp(this));
      
    BaseViewAction.sendBrowserTo(HANIM20_STANDARD);
  }

  @Override
  public String getName()
  {
    return NbBundle.getMessage(getClass(), "CTL_LaunchHumanoidAnimation20StandardAction");
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
    return new HelpCtx("HumanoidAnimation20StandardAction");
  }

  @Override
  protected boolean asynchronous()
  {
    return false;
  }
}
