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

import org.openide.util.NbBundle;
import org.web3d.x3d.DownloadX3dExamplesArchivesPanel;
import static org.web3d.x3d.DownloadX3dExamplesArchivesPanel.isLocalArchivePresent;
import static org.web3d.x3d.DownloadX3dExamplesArchivesPanel.updateStatusPropertiesLocalArchivesPresent;
import org.web3d.x3d.actions.conversions.X3dToXhtmlDomConversionFrame;
import org.web3d.x3d.options.X3dEditUserPreferences;
//import org.web3d.x3d.InputOutputReporter;
/**
 * BaseLocalViewAction.java
 * Created on Feb 7, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey jmbailey@nps.edu
 * @version $Id$
 */
@SuppressWarnings("serial")
abstract public class BaseLocalViewAction extends BaseViewAction
{
  public BaseLocalViewAction()
  {
    if ((this.getLocalExamplesPath() == null) || this.getLocalExamplesPath().isBlank())
        setEnabled(false); // TODO needed?
    else
    {
        setEnabled(true);  // TODO needed?
        updateStatusPropertiesLocalArchivesPresent();
        if (DownloadX3dExamplesArchivesPanel.isAnyArchivePresent())
        {
            if (!X3dEditUserPreferences.isExampleArchivesServerAutolaunch())
            {
                DownloadX3dExamplesArchivesPanel.checkAutolaunchRunExampleArchivesServer();
            }
            X3dToXhtmlDomConversionFrame.autolaunchAllServers();
        }
    }
  }
  
  protected void performAction2(String directoryLocation, String errPath)
  {
    DownloadX3dExamplesArchivesPanel.updateStatusPropertiesLocalArchivesPresent();
    
    if (directoryLocation == null) {
        showErrOut(errPath);
        directoryLocation = "";
    }
    String urlString = "file://"+directoryLocation+"/index.html"; // default url
    
    String   archiveName = directoryLocation;
    if      (archiveName.contains("\\"))
             archiveName = directoryLocation.substring(directoryLocation.lastIndexOf("\\") + 1);
    else if (archiveName.contains("/"))
             archiveName = directoryLocation.substring(directoryLocation.lastIndexOf("/") + 1);
    
    // note that this tests whether port is bound, since that might occur externally (regardless of local http server)
    if (isLocalArchivePresent(archiveName) && 
        X3dToXhtmlDomConversionFrame.isPortBound(Integer.parseInt(X3dEditUserPreferences.getExampleArchivesServerPort()))) // TODO and server started...
        urlString = "http://localhost:" + X3dEditUserPreferences.getExampleArchivesServerPort() + "/" + archiveName + "/index.html";
    
    try
    {
//      showInBrowser(urlString); // avoid local, reinforce unified method
        BaseViewAction.showInBrowser(urlString);
    } 
    catch (Exception e)
    {
        showOut("Error showing browser (" + urlString + "):" + e.getLocalizedMessage());
    }
  }
  
  
  protected void showErrOut(String dir)
  {
    showOut(dir + NbBundle.getBundle(getClass()).getString("MSG_was_not_found"));
  }
  
  protected void showOut(String msg)
  {
//    InputOutputReporter console = new InputOutputReporter("View Local Examples Archive");
    java.util.ResourceBundle bun = NbBundle.getBundle(getClass());

//    console.message(msg);
//    console.moveToFront(true);    
  }
}
