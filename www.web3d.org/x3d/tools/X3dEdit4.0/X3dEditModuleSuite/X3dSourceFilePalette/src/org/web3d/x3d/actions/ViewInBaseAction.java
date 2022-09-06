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
/**
 * ViewInBaseAction.java
 * Created on 31 July 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey and Don Brutzman
 * @version $Id$
 */
package org.web3d.x3d.actions;

import java.io.File;
import java.io.IOException;
import org.openide.awt.StatusDisplayer;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.actions.CookieAction;
import org.web3d.x3d.X3DDataObject;
import org.web3d.x3d.options.X3dOptions;

@SuppressWarnings("serial")
public abstract class ViewInBaseAction extends CookieAction
{
  @Override
  protected boolean enable(Node[] activatedNodes)
  {
    boolean localEnabled = false;
    String path = getExePath();
    if(path != null && path.length()>0) {
      File f = new File(path);
      if(f.exists())
        localEnabled = true;
    }
    else if ((path != null) && (X3dOptions.getOtherX3dPlayerNameDefault() != null) && 
              path.equals(X3dOptions.getOtherX3dPlayerNameDefault()))
    {
        return true;
    }
    return super.enable(activatedNodes) && localEnabled;
  }

  @Override
  protected void performAction(Node[] activatedNodes)
  {
    X3DDataObject x3DDataObject = activatedNodes[0].getLookup().lookup(X3DDataObject.class);

    FileObject fo = x3DDataObject.getPrimaryFile();
    try {
      String statusString = getStatusString();
      if(statusString != null && statusString.length()>0)
        StatusDisplayer.getDefault().setStatusText(statusString);

      //InputStream is = supp.getInputStream();
      
      // Method 1: use temp file
      //File tempF = File.createTempFile(fo.getName(),"."+fo.getExt());
      //tempF.deleteOnExit();
      //FileUtil.copy(is,new FileOutputStream(tempF));
      
      // Method 2: use original file
      File tempF = FileUtil.toFile(fo);
      String path = tempF.getAbsolutePath();
      if(getEscapeSpaces())
        path = path.replace(" ", "%20");
      path = getPrependScheme() + path;
      
      String[] execStringArray = CommandExecutionScripts.getCommandExecutionScript();
      execStringArray[CommandExecutionScripts.getExecutablePathIndex()] = getExePath();
      execStringArray[CommandExecutionScripts.getArgumentPathIndex()]   = path;
      
      ProcessBuilder pb = new ProcessBuilder(execStringArray);
      pb.directory(tempF.getParentFile());
      pb.start();
    }
    catch(IOException e) {
      Exceptions.printStackTrace(e);
    }
  }

  abstract protected boolean isAutoLaunch();
  abstract protected String getExePath();
  abstract protected String getStatusString();
  protected boolean getEscapeSpaces() { return false; }
  protected String  getPrependScheme() { return ""; }

  @Override
  protected int mode()
  {
    return CookieAction.MODE_EXACTLY_ONE;
  }

  @Override
  protected Class<?>[] cookieClasses()
  {
    return new Class<?>[]{X3DDataObject.class};
  }

  @Override
  protected void initialize()
  {
    super.initialize();

    // see org.openide.util.actions.SystemAction.iconResource() Javadoc for more details
    putValue("noIconInMenu", Boolean.TRUE);
  }

  @Override
  protected boolean asynchronous()
  {
    return false;
  }


 }

