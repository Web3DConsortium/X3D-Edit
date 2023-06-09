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
package org.web3d.x3d;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.Action;
import org.openide.actions.OpenAction;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.cookies.OpenCookie;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataFolder;
import org.openide.loaders.DataObject;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;
import org.web3d.x3d.options.X3dEditUserPreferences;

/**
 * Create new X3D scene source file .x3d for editing
 */
@ActionID(id = "org.web3d.x3d.NewHelloWorldExampleAction", category = "X3D-Edit")

@ActionRegistration(   iconBase = "org/web3d/x3d/resources/x3d32x32.png",
                    displayName = "#CTL_X3DNewHelloWorldExampleAction",
                           lazy = true) // don't do lazy=false since iconBase no longer gets registered
@ActionReferences(value = {
  @ActionReference(path = "Toolbars/X3D-Edit New File Templates", name = "org-web3d-x3d-NewHelloWorldExampleAction", position = 300),
  @ActionReference(path = "Menu/&X3D-Edit/&New File Templates", position = 300),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/&New File Templates", position = 300),
})

public final class NewHelloWorldExampleAction extends CallableSystemAction
{
  @Override
  public void performAction()
  {
    try {
      // Look in the "filesystem" to find the registered template (through classpath)
      String path = "Templates/Other/HelloWorldX3D4.x3d";
      FileObject x3dTemplateFileObject = FileUtil.getConfigRoot().getFileSystem().findResource(path); //Repository.getDefault().getDefaultFileSystem().findResource(path);
      if (x3dTemplateFileObject == null)
      {
          System.out.println("*** Error, template file " + path + " not found");
          return;
      }
      x3dTemplateFileObject.setAttribute("template", Boolean.TRUE);
      
      DataObject templ = DataObject.find(x3dTemplateFileObject);      // get a DataObject for the template
       
      // Build the temp file in home directory
      File homeDirectory = new File(X3dEditUserPreferences.getNewX3dModelsDirectory());
      FileObject homeFileObject = FileUtil.createFolder(homeDirectory);
      
      // Find a free name, may append number for uniqueness
      String freename = FileUtil.findFreeFileName(homeFileObject, "newHelloWorldExample", "x3d");
      DataObject newDataObject = templ.createFromTemplate(DataFolder.findFolder(homeFileObject),freename);
      
      // The above method calls into X3DDataObject.handleCreateFromTemplate(), which copies the template
      // into the new file.
      
      // TODO copy image file(s) so that CORS restrictions are met
      
      // Finally, execute the OpenAction
      OpenCookie openCookie = newDataObject.getLookup().lookup(OpenCookie.class);
      if (openCookie != null) {
          openCookie.open();
          return;
      }
      // Old way
      Node nod = newDataObject.getNodeDelegate();
      Action[] acts = nod.getActions(false);
      for(Action a : acts) {
        if(a instanceof OpenAction) {
          a.actionPerformed(new ActionEvent(nod, ActionEvent.ACTION_PERFORMED, ""));
          return;
        }
      }
    }
    catch (IOException ex) {
      Exceptions.printStackTrace(ex);
    }
  }
  
  @Override
  public String getName()
  {
    return NbBundle.getMessage(NewHelloWorldExampleAction.class, "CTL_X3DNewHelloWorldExampleAction");
  }
  
  @Override
  protected String iconResource()
  {
    return "org/web3d/x3d/resources/X3D.png";
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
