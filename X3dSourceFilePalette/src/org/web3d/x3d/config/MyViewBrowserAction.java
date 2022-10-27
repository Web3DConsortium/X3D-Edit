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

package org.web3d.x3d.config;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.openide.awt.StatusDisplayer;
import org.openide.awt.HtmlBrowser;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import java.net.URL;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;

@ActionID(id = "org.web3d.x3d.x3dedit.config.MyViewBrowserAction", category = "Tools")
@ActionRegistration(// TODO iconBase = "org/web3d/x3d/resources/X3Dicon16.png",
                    displayName = "#CTL_MyViewBrowserAction", // "X3D-Edit home page"
                            lazy=true)
@ActionReferences(value = {
  @ActionReference(path = "Menu/X3D-Edit/X3D-Edit Home, Preferences", position = 1004), // see layer.xml
  @ActionReference(path = "Editors/model/x3d+xml/Popup/X3D-Edit Home, Preferences", position = 1004)
})

public final class MyViewBrowserAction implements ActionListener
{
  @Override
  public void actionPerformed(ActionEvent e)
  {
    StatusDisplayer.getDefault().setStatusText(NbBundle.getMessage(getClass(), "CTL_OpeningBrowser"));
    try {
      String myHomepage = NbBundle.getMessage(getClass(), "Browser_home_page");
      HtmlBrowser.URLDisplayer.getDefault().showURL(new URL(myHomepage)); //HtmlBrowser.getHomePage()));
    }
    catch (java.net.MalformedURLException mfe) {
      String home = HtmlBrowser.getHomePage();
      if (!home.startsWith("https://")) { // NOI18N
        home = "https://" + home; // NOI18N
      }
      try {
        HtmlBrowser.URLDisplayer.getDefault().showURL(new URL(home));
      }
      catch (java.net.MalformedURLException e1) {
        Exceptions.printStackTrace(e1);
      }
    }
  }
}
