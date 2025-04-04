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

import java.awt.Desktop;
import java.net.URI;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;

@ActionID(id = "org.web3d.x3d.actions.LaunchIssueReportEmailAction", category = "X3D-Edit")
// adapted email icon from https://creativecommons.org/website-icons
@ActionRegistration(   iconBase = "org/web3d/x3d/resources/envelope32X24.png",
                    displayName = "#CTL_LaunchIssueReportEmailAction", 
                            lazy=true)
@ActionReferences(value = {
  @ActionReference(path = "Menu/&X3D-Edit/X3D-Edit &Information", position = 1000),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/X3D-Edit &Information", position = 1000)
})

public final class LaunchIssueReportEmailAction extends CallableSystemAction
{
    // https://en.wikipedia.org/wiki/Mailto
    // https://en.wikipedia.org/wiki/Percent-encoding
    // https://stackoverflow.com/questions/3871729/transmitting-newline-character-n
    
  public static String MAILTO_REPORT_URL = "mailto:brutzman@nps.edu%20(Don%20Brutzman)?subject=X3D-Edit%20issue%20report";

  // don't want to add body to email, since that clobbers user signature, but Outlook now blocks "To:" field
  // %0A is newline character, parenthesis %28 { and %29 )
  
  public static String MAILTO_REPORT_URL_SUFFIX = 
          "&body=Please%20send%20issue-report%20email%20to%20Don%20Brutzman%20%28brutzman@nps.edu%29%20-%20thanks!" +
          "%0A%0A" + // newlines
          "Issue%20description:%20";
  
  private String elementName = new String();
  
  public static void sendMailtoFeedback (String itemName)
  {
      String mailtoReportUrl = MAILTO_REPORT_URL;
      
      if  (itemName == null)
           itemName = new String();
      if (!itemName.isBlank())
      {
          // also append url-escaped blank character to facilitate user editing
          mailtoReportUrl+= ":%20" + itemName + "%20";
      }
      mailtoReportUrl+= MAILTO_REPORT_URL_SUFFIX;
      
      System.out.println ("LaunchIssueReportEmailAction sendMailtoFeedback() mailtoReportUrl=\n   " + mailtoReportUrl);
      System.out.println ("   " + mailtoReportUrl.replace("%20", " ").replace("%28", "(").replace("%29", ")").replace("%0A", " "));
      
      sendBrowserTo(mailtoReportUrl); // redirects to user's mail application, if present
  }
  
  @Override
  public void performAction()
  {
      sendMailtoFeedback (getElementName());
      
//      String mailtoReportUrl = MAILTO_REPORT_URL;
//      // TODO add node/statement name
//      if (!getElementName().isBlank())
//      {
//          // also append url-escaped blank character to facilitate user editing
//          mailtoReportUrl+= ":%20" + getElementName() + "%20";
//      }
//      mailtoReportUrl+= MAILTO_REPORT_URL_SUFFIX;
//      
//      System.out.println ("LaunchIssueReportEmailAction performAction() mailtoReportUrl=\n   " + mailtoReportUrl);
//      
//      sendBrowserTo(mailtoReportUrl); // redirects to user's mail application, if present
  }
  
  public static void sendBrowserTo(String urlString)
  {
     urlString = urlString.replace(" ", "%20"); // normalize email url
     System.out.println ("LaunchIssueReportEmailAction sendBrowserTo() urlString=" + urlString);
      
     try {
       showInBrowser(urlString);
     }
     catch(Exception e) {
       System.err.println("Trying to display "+urlString+" in HtmlBrowser: "+e.getLocalizedMessage());
     }    
  }
  protected static void showInBrowser(String urlString) throws Exception
  {
    // HtmlBrowser.URLDisplayer.getDefault().showURL(new URL(urlString));
      
    // https://stackoverflow.com/questions/5226212/how-to-open-the-default-webbrowser-using-java
    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
        Desktop.getDesktop().browse(new URI(urlString.replaceAll("\\\\","/")));
  }

  @Override
  public String getName()
  {
    return NbBundle.getMessage(getClass(), "CTL_LaunchIssueReportEmailAction");
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
    return new HelpCtx("X3dResourcesExamples");
  }

  @Override
  protected boolean asynchronous()
  {
    return false;
  }

    /**
     * Callback to notify which panel is invoking
     * @param parentElementName
     */
    public void setElementName(String parentElementName)
  {
      elementName = parentElementName;
  }
    /**
     * Which panel invoked the launch action
     * @return parent element Name
     */
    public String getElementName()
  {
      return elementName;
  }
}
