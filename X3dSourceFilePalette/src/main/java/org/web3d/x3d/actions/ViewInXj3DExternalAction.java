/*
* Copyright (c) 1995-2021 held by the author(s).  All rights reserved.
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
package org.web3d.x3d.actions;

import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.web3d.x3d.options.X3dEditUserPreferences;
/*
  This one views the scene in the EXTERNAL user-installed version of the XJ3d installed player.
*/
@ActionID(id = "org.web3d.x3d.actions.ViewInXj3DExternalAction", category = "X3D-Edit")
@ActionRegistration(
           iconBase = "org/web3d/x3d/resources/Xj3Dlogo32x32.png",
        displayName = "#CTL_ViewInXj3DExternalAction",
                lazy=true) // don't do lazy=false since iconBase no longer gets registered
@ActionReferences(value={
  @ActionReference(path = "Menu/&X3D-Edit/&View Saved X3D Model", position = 60, separatorAfter = 61),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/&View Saved X3D Model", position = 61),
})

/**
 * ViewInXj3DExternalAction.java
 * Created on May 4, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey and Don Brutzman
 * @version $Id$
 */
public final class ViewInXj3DExternalAction extends ViewInBaseAction
{
  @Override
  protected boolean getEscapeSpaces()
  {
    return true;
  }
  @Override
  protected String getPrependScheme()
  {
    return "file:/"; // this works around Xj3D bug for mishandling of paths with embedded spaces (e.g. c:\My%20Documents\
  }

  @Override
  protected String getExePath()
  {
    return X3dEditUserPreferences.getXj3DPath();
  }

  @Override
  protected String getStatusString()
  {
    return NbBundle.getMessage(getClass(), "STATUSLINE_OpeningXj3DExternal");
  }

  @Override
  protected boolean isAutoLaunch()
  {
      return Boolean.parseBoolean(X3dEditUserPreferences.isXj3dAutoLaunch());
  }

  @Override
  public String getName()
  {
    return NbBundle.getMessage(getClass(), "CTL_ViewInXj3DExternalAction");
  }

  @Override
  public HelpCtx getHelpCtx()
  {
    return HelpCtx.DEFAULT_HELP;
  }
}

