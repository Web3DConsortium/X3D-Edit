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
package org.web3d.x3d;

import org.openide.loaders.DataObject;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;
import org.web3d.x3d.xj3d.viewer.Xj3dTopComponent;
/*
@ActionID(id = "org.web3d.x3d.ReloadXj3dComponentAction", category = "X3D-Edit")
@ActionRegistration(displayName = "#CTL_ReloadXj3dComponent",lazy=false)
@ActionReference(path = "Editors/model/x3d+xml/Popup", position = 1020)
*/
/* Superceded by Xj3dViewerAction

@ActionID(id = "org.web3d.x3d.ReloadXj3dComponentAction", category = "X3D")
@ActionRegistration(iconBase = "org/web3d/x3d/resources/Xj3Dlogo32x32.png",
                    displayName = "#CTL_ReloadXj3dComponent",
                    lazy=true) // don't do lazy=false since iconBase no longer gets registered
@ActionReferences(value = {
  @ActionReference(path = "Menu/&X3D-Edit", position = 60),
  @ActionReference(path = "Editors/model/x3d+xml/Popup", position = 200),
  @ActionReference(path = "Toolbars/X3D", position = 90),})
*/

/** @deprecated use org.web3d.x3d.xj3d.viewer.Xj3dViewerAction */
@Deprecated(since = "3.2", forRemoval = true)
public final class ReloadXj3dComponentAction extends CookieAction
{
  @Override
  protected void performAction(Node[] activatedNodes)
  {
    Xj3dTopComponent xj3dTopComponent = Xj3dTopComponent.findInstance();

    if(!xj3dTopComponent.isOpened()) {
        xj3dTopComponent.open();
        xj3dTopComponent.requestActive();
    }
//    topC.getXj3DViewerPanel().reloadXj3dComponent();
  }

  @Override
  protected int mode()
  {
    return CookieAction.MODE_EXACTLY_ONE;
  }

  @Override
  public String getName()
  {
    return NbBundle.getMessage(ReloadXj3dComponentAction.class, "CTL_ReloadXj3dComponent");
  }

  @Override
  protected Class<?>[] cookieClasses()
  {
    return new Class<?>[]{DataObject.class};
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
}

