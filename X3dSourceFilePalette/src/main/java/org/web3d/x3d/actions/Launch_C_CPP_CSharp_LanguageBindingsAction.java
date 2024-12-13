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

package org.web3d.x3d.actions;

import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;
import static org.web3d.x3d.actions.BaseViewAction.X3D_CPP_LANGUAGE_BINDING_SAI;
import static org.web3d.x3d.actions.BaseViewAction.X3D_CSHARP_LANGUAGE_BINDING_SAI;
import static org.web3d.x3d.actions.BaseViewAction.X3D_C_LANGUAGE_BINDING_SAI;

/**
 * Launch HAnim2 standard
 * @author brutzman
 */
    @ActionID(id = "org.web3d.x3d.actions.Launch_C_CPP_CSharp_LanguageBindingsAction", category = "X3D-Edit")
@ActionRegistration(   iconBase = "org/web3d/x3d/resources/C_Sharp_wordmark.svg.32x32.png",
                    displayName = "#CTL_Launch_C_CPP_CSharp_LanguageBindingsAction",
                            lazy=true)
@ActionReferences( value = {
  @ActionReference(path = "Menu/&X3D-Edit/X3D &Work in Progress", position = 300),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/X3D &Work in Progress", position = 300)
})

public final class Launch_C_CPP_CSharp_LanguageBindingsAction extends CallableSystemAction
{
  @Override
  public void performAction()
  {
    // looking for javahelp
    // Help hlp = Lookup.getDefault().lookup(org.netbeans.api.javahelp.Help.class);
    // hlp.showHelp(HelpCtx.findHelp(this));

// X3D SAI for C    https://www.web3d.org/specifications/ISO-IEC19777/ISO-IEC19777-3v3.3-DIS 

// X3D SAI for C++  https://www.web3d.org/specifications/ISO-IEC19777/ISO-IEC19777-4v3.3-DIS 

// X3D SAI for C#   https://www.web3d.org/specifications/ISO-IEC19777/ISO-IEC19777-5v3.3-DIS 

    BaseViewAction.sendBrowserTo(X3D_C_LANGUAGE_BINDING_SAI   );
    
    LaunchX3dExamplesAction.sleepSeconds(1); // give user time to notice first page, then second
    
    BaseViewAction.sendBrowserTo(X3D_CPP_LANGUAGE_BINDING_SAI);
    
    LaunchX3dExamplesAction.sleepSeconds(1); // give user time to notice first page, then second
    
    BaseViewAction.sendBrowserTo(X3D_CSHARP_LANGUAGE_BINDING_SAI);
  }

  @Override
  public String getName()
  {
    return NbBundle.getMessage(getClass(), "CTL_Launch_C_CPP_CSharp_LanguageBindingsAction");
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
    return new HelpCtx("C_CPP_CSharp_LanguageBindings");
  }

  @Override
  protected boolean asynchronous()
  {
    return false;
  }
}
