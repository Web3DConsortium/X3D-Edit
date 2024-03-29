/*
Copyright (c) 1995-2023 held by the author(s).  All rights reserved.
 
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

package org.web3d.x3d.actions.qualityassurance;

import javax.swing.JMenuItem;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle;
import org.web3d.x3d.actions.*;

@ActionID(id = "org.web3d.x3d.actions.qualityassurance.X3dValidatorOnlineAction", category = "X3D-Edit")

@ActionRegistration(   iconBase = "org/web3d/x3d/resources/CheckMark.png",
                    displayName = "#CTL_X3dValidatorOnlineAction",
                    lazy=true) // don't do lazy=false since iconBase no longer gets registered

// TODO do not advertise online X3D Validator until restored
//@ActionReferences(value = {
//  @ActionReference(path = "Menu/&X3D-Edit/&Quality Assurance (QA)",  position = 1000),
//  @ActionReference(path = "Editors/model/x3d+xml/Popup/&Quality Assurance (QA)", position = 1000),
//})

public final class X3dValidatorOnlineAction extends BaseViewAction
{
  @Override
  public void performAction()
  {
    sendBrowserTo(X3DVALIDATORURL);
  }

  @Override
  public String getName()
  {
    return NbBundle.getMessage(getClass(), "CTL_X3dValidatorOnlineAction");
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
    mi.setToolTipText(NbBundle.getMessage(ComprehensiveValidationAction.class, "CTL_X3dValidatorOnlineAction_tt"));
    return mi;
  }

  @Override
  protected String iconResource()
  {
    return "org/web3d/x3d/resources/CheckMark.png";
  }
}
