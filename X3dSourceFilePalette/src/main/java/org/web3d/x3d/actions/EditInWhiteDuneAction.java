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
/**
 * ViewInWhiteDuneAction.java
 * Created on 4 May 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey and Don Brutzman
 * @version $Id$
 */
package org.web3d.x3d.actions;

import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.web3d.x3d.options.X3dEditUserPreferences;

@ActionID(id = "org.web3d.x3d.actions.EditInWhiteDuneAction", category = "X3D-Edit")
@ActionRegistration(   iconBase = "org/web3d/x3d/resources/whitedune_32x32.png", // ico
                    displayName = "#CTL_EditInWhiteDuneAction", lazy=true)
@ActionReferences( value = {
    @ActionReference(path = "Menu/&X3D-Edit/&Tool Editing of Saved X3D Model", position = 180),
    @ActionReference(path = "Editors/model/x3d+xml/Popup/&Tool Editing of Saved X3D Model", position = 180),
})

public final class EditInWhiteDuneAction extends ViewInBaseAction
{
  @Override
  protected boolean getEscapeSpaces()
  {
    return true;
  }

  @Override
  protected String getExePath()
  {
    return X3dEditUserPreferences.getWhiteDuneX3dEditorPath();
  }

  @Override
  protected String getStatusString()
  {
    return NbBundle.getMessage(getClass(), "STATUSLINE_EditingWhiteDune");
  }

  @Override
  protected boolean isAutoLaunch()
  {
      return Boolean.parseBoolean(X3dEditUserPreferences.isWhiteDuneAutoLaunch());
  }
    
  @Override
  public String getName()
  {
    return NbBundle.getMessage(getClass(), "CTL_EditInWhiteDuneAction");
  }

  @Override
  public HelpCtx getHelpCtx()
  {
    return HelpCtx.DEFAULT_HELP;
  }
}

