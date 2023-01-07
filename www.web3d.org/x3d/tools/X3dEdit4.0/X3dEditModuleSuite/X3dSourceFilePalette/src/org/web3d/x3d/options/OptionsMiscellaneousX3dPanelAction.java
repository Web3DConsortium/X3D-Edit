/*
Copyright (c) 1995-2022 held by the author(s).  All rights reserved.
 
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
package org.web3d.x3d.options;

import java.awt.Dialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;

@ActionID(id = "org.web3d.x3d.options.OptionsMiscellaneousX3dPanelAction", category = "X3D-Edit")
@ActionRegistration(   iconBase = "org/web3d/x3d/resources/X3Dicon32.png",
                    displayName = "#CTL_OptionsMiscellaneousX3dPanel",
                            lazy=true) // don't do lazy=false since iconBase no longer gets registered
@ActionReferences(value = {
  @ActionReference(path = "Menu/&X3D-Edit", position = 1150),              // see layer.xml, now follows /X3D-Edit &Information
  @ActionReference(path = "Editors/model/x3d+xml/Popup", position = 1150),// see layer.xml, now follows /X3D-Edit &Information
  @ActionReference(path = "Shortcuts", name = "CS-P") // shortcut control-shift-P
  // see Apache NetBeans > Help > Keyboard Shortcuts Card for other shortcuts
        
//  @ActionReference(path = "Menu/&X3D-Edit/&View Saved X3D Model", position = 990),
//  @ActionReference(path = "Menu/&X3D-Edit/Modify Saved Scene in Tool", position = 990),
})

/** Instantiates the X3D-Edit Options panel that can be invoked from an 
 * abstract caller 
 *
 * @since 3.2
 */
public final class OptionsMiscellaneousX3dPanelAction implements ActionListener
{
  int preferredPaneIndex = -1; // no preference
    
  @Override
  public void actionPerformed(ActionEvent e)
  {
    OptionsMiscellaneousX3dPanel optionsPanel = new OptionsMiscellaneousX3dPanel(preferredPaneIndex);
    if (preferredPaneIndex != -1)
    {
        optionsPanel.setPreferredPane(preferredPaneIndex);
        preferredPaneIndex = -1; // reset
    }

    JButton completeButton = new JButton(NbBundle.getMessage(getClass(),"MSG_Complete"));
    completeButton.setToolTipText(NbBundle.getMessage(getClass(),"TIP_Complete"));
    JLabel  descriptionLabel = new JLabel ("Each panel change is saved upon entry.");
//  JButton acceptButton = new JButton(NbBundle.getMessage(getClass(),"MSG_Accept"));
//  JButton cancelButton = new JButton(NbBundle.getMessage(getClass(),"MSG_Discard"));
//  acceptButton.setToolTipText(NbBundle.getMessage(getClass(),"TIP_Accept"));
//  cancelButton.setToolTipText(NbBundle.getMessage(getClass(),"TIP_Discard"));
    DialogDescriptor descriptor = new DialogDescriptor(
        optionsPanel, // inner pane
        NbBundle.getMessage(getClass(),"OptionsMiscellaneousX3dPanelDialogTitle"),
        true, // modal
        new Object[]{
            descriptionLabel, completeButton}, // acceptButton, cancelButton},  // buttons
        completeButton,                             // default
        DialogDescriptor.DEFAULT_ALIGN,
        HelpCtx.DEFAULT_HELP,
        null); // action listener
   
    Dialog dialog = DialogDisplayer.getDefault().createDialog(descriptor);
    dialog.setResizable(true);
    dialog.pack();
    dialog.setVisible(true);
    //blocks until complete
    
    if (descriptor.getValue() == completeButton) 
        optionsPanel.store(); // TODO superfluous ?
  }
    /**
     * Set preferred pane
     * @param index the preferredPaneIndex to set
     */
    public void setPreferredPane(int index)
    {
        preferredPaneIndex = index;
    }
}
