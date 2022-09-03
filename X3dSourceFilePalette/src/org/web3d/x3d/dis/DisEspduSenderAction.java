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

package org.web3d.x3d.dis;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;

import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle;
import org.openide.windows.TopComponent;

/**
 * Action which shows DisTester component.
 */
@ActionID(id = "org.web3d.x3d.dis.DisEspduSenderAction", category = "Window")
@ActionRegistration(displayName = "#CTL_DisEspduSenderAction", lazy = false)
@ActionReferences(value = {
  @ActionReference(path = "Menu/X3D-Edit/DIS Networking", name = "org-web3d-x3d-dis-DisTesterAction", position = 200),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/DIS Networking", position = 200)
})
public class DisEspduSenderAction extends AbstractAction
{
  public DisEspduSenderAction()
  {
    super(NbBundle.getMessage(DisEspduSenderAction.class, "CTL_DisTesterAction"));
//        putValue(SMALL_ICON, new ImageIcon(Utilities.loadImage(DisEspduSenderControlPanel.ICON_PATH, true)));
  }

  @Override
  public void actionPerformed(ActionEvent evt)
  {
    TopComponent win = DisEspduSenderControlPanel.findInstance();
    win.open();
    win.requestActive();
  }
}
