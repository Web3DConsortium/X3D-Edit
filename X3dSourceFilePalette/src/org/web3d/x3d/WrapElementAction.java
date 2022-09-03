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

import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.loaders.DataObject;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;
import org.openide.util.lookup.Lookups;
import org.web3d.x3d.palette.X3DPaletteUtilities;
import org.web3d.x3d.palette.X3DPaletteUtilities.ElementLocation;
import org.web3d.x3d.palette.items.NodeListPanel;

@ActionID(id = "org.web3d.x3d.WrapElementAction", category = "Edit")
@ActionRegistration(displayName = "#CTL_WrapElementAction", 
                    lazy=true) // don't do lazy=false since iconBase no longer gets registered)
@ActionReferences( value = {
  @ActionReference(path = "Menu/X3D-Edit/Edit Element XML", position = 780),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/Edit Element XML", position = 780)
})

public final class WrapElementAction extends EditElementAction
{
  NodeListPanel nodeListPanel = new NodeListPanel();
  DialogDescriptor dialogDescriptor;

  private boolean workComplete = false;

  @Override
  protected void initialize()
  {
      super.initialize();
      // see org.openide.util.actions.SystemAction.iconResource() Javadoc for more details
      putValue("noIconInMenu", Boolean.TRUE);
      nodeListPanel.setSelectedName("Transform"); // second choice: Group
      String title = NbBundle.getMessage(getClass(), "MSG_ChooseEnclosingElement");
      dialogDescriptor = new DialogDescriptor(nodeListPanel, title); // node list doesn't change
      workComplete = false;
  }
    
  @Override
  protected void doWork(Node[] activatedNodes)
  {
    try {
      if (workComplete)
      {
          workComplete = false; // setup for next use
          return;               // exit panel
      }
      boolean cancelled = false;
      DialogDisplayer displayer = DialogDisplayer.getDefault();

      if (displayer.notify(dialogDescriptor) == DialogDescriptor.CANCEL_OPTION)
        cancelled = true;
      else {
        ElementLocation selectedLocation = X3DPaletteUtilities.findSelectedElement(documentEditorPane);
        // Not for wrapping i think...selectedLocation = changeIfSpecialCase(selectedLocation);       // special case edits

        int selectedStringLength = selectedLocation.docOffsetEnd - selectedLocation.docOffsetStart;
        String selectedString = documentEditorPane.getText(selectedLocation.docOffsetStart, selectedStringLength);

        String nodeName = nodeListPanel.getSelectedName();

        StringBuilder sb = new StringBuilder();
        sb.append("<");
        sb.append(nodeName);
        sb.append(">");
//        sb.append("\n"); // TODO needs indenting afterwards
        sb.append(selectedString);
//        sb.append("\n"); // TODO needs indenting afterwards
        sb.append("</");
        sb.append(nodeName);
        sb.append(">");

        int caretPosition = getCaretLocation();
        insertNewString(selectedLocation, sb.toString(), selectedStringLength);
        
        // shift editor caret post-insertion in order to select correct element (i.e. the element that was just wrapped)
        setCaretLocation(selectedLocation.docOffsetStart +  nodeName.length() + 2);  // to allow edit action to properly locate it
        workComplete = true;
      }

      //TODO not working
      if (!cancelled) {
        // Call our standard edit action for the new node
        Lookup lu = Lookups.forPath("Actions/Edit");
        EditElementAction editAct = lu.lookup(EditElementAction.class);
        // TODO this selection seems incorrect, we want to edit the node that was inserted at caret location rather than invocation nodeset
        editAct.publicPerformAction(activatedNodes);
      }
    }
    catch (Exception ex) {
      throw new RuntimeException(ex);  // let base class handle
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
    return NbBundle.getMessage(getClass(), "CTL_WrapElementAction");
  }

  @Override
  protected Class[] cookieClasses()
  {
    return new Class[]{DataObject.class};
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

