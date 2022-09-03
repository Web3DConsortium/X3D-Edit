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
package org.web3d.x3d;

import org.netbeans.modules.editor.indent.api.Indent;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionRegistration;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;
import org.openide.util.actions.CookieAction;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.openide.windows.OutputWriter;
import org.web3d.x3d.options.X3dOptions;
import org.web3d.x3d.palette.X3DPaletteUtilities;
import org.web3d.x3d.palette.X3DPaletteUtilities.ElementLocation;
import org.web3d.x3d.palette.X3DPaletteUtilities.ValidateThread;

@ActionID(id = "org.web3d.x3d.ExpandSelfClosedElementAction", category = "Edit")
@ActionRegistration(displayName = "#CTL_ExpandSelfClosedElementAction", 
                    lazy=true) // don't do lazy=false since iconBase no longer gets registered)
@ActionReference(path = "Editors/model/x3d+xml/Popup/Edit Element XML", position = 775)

public final class ExpandSelfClosedElementAction extends BaseX3DEditAction //CookieAction
{
  @Override
  protected void doWork(Node[] activatedNodes)
  {
    try {
      ElementLocation selectedLocation = X3DPaletteUtilities.findSelectedElement(documentEditorPane); //findSelectedElement();

      if (!expandSelfClosedSelectedElement(selectedLocation)) {
        InputOutput io = IOProvider.getDefault().getIO("Messages", false); // Name matches existing tab
        OutputWriter ow = io.getOut();
        ow.println(NbBundle.getMessage(getClass(), "LBL_NotSelfClosedError"));
      }
      else {
        // Reformat
        if (baseDocument != null) {
          //New 6.5 code
          Indent indent = Indent.get(baseDocument);
          indent.lock();
          indent.reindent(selectedLocation.docOffsetStart, selectedLocation.docOffsetEnd + 25);
          indent.unlock();

          //pre-6.5 code:
//    baseDocument.getFormatter().reformatLock();
//    baseDocument.getFormatter().reformat(baseDocument,selectedLocation.docOffsetStart,selectedLocation.docOffsetEnd+25);  // guess doesn't seem to hurt
//    baseDocument.getFormatter().reformatUnlock();
        }
        if (X3dOptions.getAutoValidate())
            // Schedule validation task in separate thread
            RequestProcessor.getDefault().post(new ValidateThread(documentEditorPane, "inserted text", true));
      }
    }
    catch (Exception ex) {  //todo, specific msgs for spec exceptions
      throw new RuntimeException(ex);
    }
  }

  @Override
  protected int mode()
  {
    return CookieAction.MODE_EXACTLY_ONE;
  }

  @Override
  public String getName()
  {
    return NbBundle.getMessage(ExpandSelfClosedElementAction.class, "CTL_ExpandSelfClosedElementAction");
  }

  @Override
  protected Class[] cookieClasses()
  {
    return new Class[]{X3DDataObject.class};
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

