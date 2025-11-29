/*
 * Copyright (c) 1995-2023 held by the author(s).  All rights reserved.
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
package org.web3d.x3d.actions.conversions;

import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.NbBundle;
import static org.web3d.x3d.actions.conversions.X3dToXhtmlDomConversionFrame.X_ITE_TAB;

@ActionID(id = "org.web3d.x3d.actions.conversions.XhtmlX_iteConversionAction", category = "X3D-Edit")

@ActionRegistration(
        iconBase = "org/web3d/x3d/resources/cobweb-logo32.png",
     displayName = "#CTL_XhtmlX_iteConversionAction",
             lazy=true) // don't do lazy=false since iconBase no longer gets registered

@ActionReferences(value = {
  @ActionReference(path = "Menu/&X3D-Edit/&Author Workflow", position = 80),
  @ActionReference(path = "Menu/&X3D-Edit/&View Saved X3D Model", position = 151),
  @ActionReference(path = "Toolbars/X3D-Edit Author Workflow", position = 80),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/&Author Workflow", position = 80),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/&View Saved X3D Model", position = 151),
  // see Apache NetBeans > Help > Keyboard Shortcuts Card for other shortcuts
})

public final class XhtmlX_iteConversionAction extends X3dToXhtmlDomConversionAction {

    @Override
    public String getName()
    {
        // invoked when toolbar button is pressed
        setPlayer(X3dToXhtmlDomConversionAction.X_ITE);
        setPreferredTab(X_ITE_TAB);
        return NbBundle.getMessage(getClass(), "CTL_XhtmlX_iteConversionAction");
    }

    @Override
    protected void initialize()
    {
        // invoked first time only
        setPlayer(X3dToXhtmlDomConversionAction.X_ITE);
        setPreferredTab(X_ITE_TAB);
        super.initialize(); // last, following setup
    }

    @Override
    protected String iconResource()
    {
        return "org/web3d/x3d/resources/cobweb-logo32.png";
    }
    // see org.openide.util.actions.SystemAction.iconResource() Javadoc for more details
}
