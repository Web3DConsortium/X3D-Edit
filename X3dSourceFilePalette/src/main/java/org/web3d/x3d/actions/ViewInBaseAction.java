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

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.StatusDisplayer;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.actions.CookieAction;
import org.web3d.x3d.X3DDataObject;
import org.web3d.x3d.options.X3dEditUserPreferences;

@SuppressWarnings("serial")
public abstract class ViewInBaseAction extends CookieAction
{
  @Override
  protected boolean enable(Node[] activatedNodes)
  {
//    TODO launch Wireshark even if no file is selected
//    if (this.getClass().toString().endsWith("LaunchWiresharkAction"))
//        return true; // no editor file needs to be activated for launching Wireshark
    
    if ((activatedNodes ==  null) || (activatedNodes.length == 0))
    {
        return false;
    }
    boolean localEnabled = false;
    String path = getExePath();
    if((path != null) && (path.length() > 0))
    {
        if (path.contains("sunrize")) // actually an invocation, not a path
            localEnabled = true; // assumes node.js installed
        else
        {
            File f = new File(path);
            if(f.exists())
               localEnabled = true;
        }
    }
    else if ((path != null) &&  (X3dEditUserPreferences.getOtherX3dPlayerNameDefault() != null) && 
              path.equals(X3dEditUserPreferences.getOtherX3dPlayerNameDefault()))
    {
        return true;
    }
    return super.enable(activatedNodes) && localEnabled;
  }

  /** View using subclass application
    * @param activatedNodes what file tree is selected in editor*/
  @Override
  protected void performAction(Node[] activatedNodes)
  {
    X3DDataObject x3DDataObject = null;
    FileObject    fo            = null;
    File          tempFile      = null;
    String        tempFilePath  = "";
    
    // no editor file needs to be activated for launching Wireshark
    boolean isFileSelectionNeeded = !this.getClass().toString().endsWith("LaunchWiresharkAction");
    
    if ((activatedNodes != null) && (activatedNodes.length >= 1))
        x3DDataObject = activatedNodes[0].getLookup().lookup(X3DDataObject.class);

    if   (x3DDataObject != null)
          fo = x3DDataObject.getPrimaryFile();
    if (isFileSelectionNeeded && ((fo == null) || !fo.canRead()))
    {
        String message = "File not available, please open and select a file first";
        System.out.println("*** " + message);
        NotifyDescriptor.Message msg = new NotifyDescriptor.Message(message);
        DialogDisplayer.getDefault().notify(msg);
        return;
    }
    try {
      String statusString = getStatusString();
      if(statusString != null && statusString.length()>0)
        StatusDisplayer.getDefault().setStatusText(statusString);

      //InputStream is = supp.getInputStream();
      
      // Method 1: use temp file
      //File tempF = File.createTempFile(fo.getName(),"."+fo.getExt());
      //tempF.deleteOnExit();
      //FileUtil.copy(is,new FileOutputStream(tempF));
      
      if (isFileSelectionNeeded || (fo != null))
      {
        // Method 2: use original file
        tempFile     = FileUtil.toFile(fo);
        tempFilePath = tempFile.getAbsolutePath();
        if(getEscapeSpaces())
          tempFilePath = tempFilePath.replace(" ", "%20");
        tempFilePath = getPrependScheme() + tempFilePath;
      }
      
      String[] execStringArray = CommandExecutionScripts.getCommandExecutionScript();
      execStringArray[CommandExecutionScripts.getExecutablePathIndex()] = getExePath(); // .replace(" ", "%20")
      execStringArray[CommandExecutionScripts.getArgumentPathIndex()]   = tempFilePath;
      
      if (getExePath().toLowerCase().contains("blender"))
      {
          // https://docs.blender.org/manual/en/latest/advanced/command_line/arguments.html
          System.out.println("TODO blender launch invocation to import X3D file... " + tempFilePath);
          execStringArray[CommandExecutionScripts.getArgumentPathIndex()]  = "--window-border"; // simple substitution rather than empty string
      }
      String prefix = "[" + this.getClass().getName() + "] ";
      System.out.println(prefix + Arrays.toString(execStringArray).replace(",",""));
      ProcessBuilder processBuilder = new ProcessBuilder(execStringArray);
      if  (tempFile != null)
           processBuilder.directory(tempFile.getParentFile());
      else processBuilder.directory(null); // user.dir
      Process process = processBuilder.start();
// TODO blocks, fix that
//      BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//      String line;
//      while ((line = reader.readLine()) != null)
//      {
//          if (!line.isEmpty())
//              System.out.println(prefix + line);
//      }
    }
    catch(IOException ioe) {
      Exceptions.printStackTrace(ioe);
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

