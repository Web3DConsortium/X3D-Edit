/*
Copyright (c) 1995-2022 held by the author(s) .  All rights reserved.
 
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
//import org.web3d.x3d.InputOutputReporter;

/**
 * BaseLocalViewAction.java
 * Created on Feb 7, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey <jmbailey@nps.edu>
 * @version $Id$
 */
@SuppressWarnings("serial")
abstract public class BaseLocalViewAction extends BaseViewAction
{
  public BaseLocalViewAction()
  {
    if(this.getLocalExamplesPath() == null)
      setEnabled(false);
  }
  
  protected void performAction2(String dirLoc, String errPath)
  {
    if(dirLoc == null) {
      showErrOut(errPath);
    }
    String urlString = "file://"+dirLoc+"/index.html";
    if(dirLoc != null) {
      try {
        showInBrowser(urlString);
      }
      catch(Exception e) {
        showOut("Error showing browser ("+urlString+"):"+e.getLocalizedMessage());
      }
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