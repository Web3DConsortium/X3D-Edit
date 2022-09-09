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
package org.web3d.x3d;

import org.openide.awt.ActionID;
import org.openide.awt.ActionRegistration;
import org.openide.nodes.Node;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.openide.windows.OutputWriter;
import org.web3d.x3d.palette.X3DPaletteUtilitiesJdom.ValidateThread;

@ActionID(id = "org.web3d.x3d.CheckX3DAction", category = "Edit")
@ActionRegistration(displayName = "#CTL_CheckX3DAction", 
                    lazy=true) // don't do lazy=false since iconBase no longer gets registered

@SuppressWarnings("serial")
public final class CheckX3DAction extends BaseX3DEditAction //CookieAction
{
  @Override
  public String getName()
  {
    return NbBundle.getMessage(getClass(), "CTL_CheckX3DAction");
  }

  @Override
  protected void doWork(Node[] activatedNodes)
  {      
    try {
      // The following will validate, then do our (ROUTE) check
      RequestProcessor.getDefault().post(new ValidateThread(documentEditorPane, activatedNodes[0].getHtmlDisplayName(), false));
    }
   catch(Exception ex) {
     InputOutput io = IOProvider.getDefault().getIO("XML check", false); // Name matches existing tab
     OutputWriter ow = io.getOut();
     ow.println(NbBundle.getMessage(getClass(), "LBL_X3dCheckError_")+ex.getLocalizedMessage());
   }         
  }
}
