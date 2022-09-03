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

package org.web3d.x3d.actions.conversions;

import java.awt.Dialog;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;
import org.web3d.x3d.X3DDataObject;
import org.web3d.x3d.options.Xj3dCadFilterOptionsPanel;

@ActionID(id = "org.web3d.x3d.actions.conversions.CadFilterAction", category = "Tools")
@ActionRegistration(
        displayName = "#CTL_CadFilterAction",
        lazy=true)
@ActionReferences(value = {
  @ActionReference(path = "Menu/X3D-Edit/Compression", position = 50),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/Compression", position = 50)
})

public final class CadFilterAction extends CookieAction
{
  private static JFileChooser chooser;
  @Override
  protected void performAction(Node[] activatedNodes)
  {
    X3DDataObject x3DDataObject = activatedNodes[0].getLookup().lookup(X3DDataObject.class);
    
    Xj3dCadFilterOptionsPanel optionsPanel = new Xj3dCadFilterOptionsPanel();
    JButton continueButton = new JButton(NbBundle.getMessage(getClass(),"MSG_Save_As"));
    JButton   cancelButton = new JButton(NbBundle.getMessage(getClass(),"MSG_Cancel"));
    continueButton.setToolTipText(NbBundle.getMessage(getClass(),"TIP_Save_As"));
      cancelButton.setToolTipText(NbBundle.getMessage(getClass(),"TIP_Cancel"));
    DialogDescriptor descriptor = new DialogDescriptor(
        optionsPanel, // inner pane
        NbBundle.getMessage(getClass(),"CadFilterDialogTitle"),
        true, // modal
        new Object[]{continueButton, cancelButton},  // buttons
        continueButton,                            // default
        DialogDescriptor.DEFAULT_ALIGN,
        HelpCtx.DEFAULT_HELP,
        null); // action listener
   
    Dialog dlg = DialogDisplayer.getDefault().createDialog(descriptor);
    dlg.setResizable(true);
    dlg.pack();
    dlg.setVisible(true);
    //blocks
    
    if(descriptor.getValue() != continueButton)
      return;
    optionsPanel.saveToPreferences();
    
    FileObject thisFileObj = x3DDataObject.getPrimaryFile();
    
    if(chooser == null) {
      File dir = new File(System.getProperty("user.home"));
      FileObject parentFO = thisFileObj.getParent();
      if(parentFO != null)
        dir = FileUtil.toFile(parentFO);
      chooser = new JFileChooser(dir);
      chooser.setMultiSelectionEnabled(false);
    }
    String newFilteredFileName = thisFileObj.getName()+"Filtered."+thisFileObj.getExt();
    
    chooser.setSelectedFile(new File(newFilteredFileName));
    if(chooser.showSaveDialog(null)== JFileChooser.CANCEL_OPTION)
      return;
    
    File target = chooser.getSelectedFile();
    
    CadFilterHelper.doFilter(x3DDataObject, target);
  }
  
  @Override
  protected int mode()
  {
    return CookieAction.MODE_EXACTLY_ONE;
  }

  @Override
  public String getName()
  {
    return NbBundle.getMessage(CadFilterAction.class, "CTL_CadFilterAction");
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
  }

  @Override
  protected String iconResource() // TODO not working...
  {
    return "org/web3d/x3d/resources/xsl_transformation.png";
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

