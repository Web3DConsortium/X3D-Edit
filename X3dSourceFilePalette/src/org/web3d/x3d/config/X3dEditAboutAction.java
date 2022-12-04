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
package org.web3d.x3d.config;

import java.util.ResourceBundle;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.modules.ModuleInfo;
import org.openide.modules.Modules;
import org.openide.modules.SpecificationVersion;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;

@ActionID(id = "org.web3d.x3d.x3dedit.config.X3dEditAboutAction", category = "X3D-Edit")
@ActionRegistration(   iconBase = "org/web3d/x3d/resources/X3Dicon16.png",
                    displayName = "#CTL_X3dEditAboutAction",
                            lazy=true)
@ActionReferences(value = {
  @ActionReference(path = "Menu/&X3D-Edit/X3D-Edit &Information", position = 100), // see layer.xml
  @ActionReference(path = "Editors/model/x3d+xml/Popup/X3D-Edit &Information", position = 100),
  // https://bits.netbeans.org/15/javadoc/org-openide-awt/org/openide/awt/ActionReference.html
  @ActionReference(path = "Shortcuts", name = "CS-A") // shortcut control-shift-A
  // see Apache NetBeans > Help > Keyboard Shortcuts Card for other shortcuts
})

public final class X3dEditAboutAction extends CallableSystemAction
{
  String moduleReleaseDate = "3 December 2022"; // manually edit along with module version for each release
  
  String rightMargin = "&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp; ";

  public X3dEditAboutAction()
  {
  }

  @Override
  public void performAction()
  {
      // https://bits.netbeans.org/15/javadoc/org-openide-util/org/openide/util/NbBundle.html
      String branding = NbBundle.getBranding();
      ResourceBundle resourceBundle = NbBundle.getBundle("org.netbeans.core.windows.view.ui.Bundle_" + branding);
      String mainWindowTitle = resourceBundle.getString("CTL_MainWindow_Title");
      
      // https://bits.netbeans.org/15/javadoc/org-openide-modules/org/openide/modules/Modules.html
      // https://bits.netbeans.org/15/javadoc/org-openide-modules/org/openide/modules/ModuleInfo.html
      Modules modules = Modules.getDefault();
      ModuleInfo moduleInfo = modules.findCodeNameBase("org.web3d.x3d.palette");
      String           buildVersion = moduleInfo.getBuildVersion();
      String           displayName  = moduleInfo.getDisplayName();
      String  implementationVersion = moduleInfo.getImplementationVersion();
      SpecificationVersion specificationVersion = moduleInfo.getSpecificationVersion();
      String x3dMajorVersion = specificationVersion.toString().substring(0, specificationVersion.toString().lastIndexOf("."));
      
      String newMainWindowTitle = mainWindowTitle + ", updated " + moduleReleaseDate + " version " + specificationVersion.toString();
      System.out.println ("*** About X3D-Edit: " + newMainWindowTitle);
      // TODO how to set?  Once that is figured out, move to top-level componenet initialization
//    resourceBundle.setString("CTL_MainWindow_Title"); ??
    // Unfortunately, not able to set values via the ResourceBundle convention.
    // Can only read values and obtain the keys to those values, no setting.
    // @see java.util.PropertyResourceBundle
      
      String aboutHtmlMessage = "<html>" +
              "<p>&nbsp;</p>" +
              "<p align='center'><hr />" +
              "<p align='center'>&nbsp;</p>" +
              "<h2 align='center'>Welcome to X3D-Edit " + x3dMajorVersion + "</h2>" +
              "<p align='center'>A free, open-source Extensible 3D (X3D) Graphics authoring tool.</p>" +
              "<p align='center'>&nbsp;</p>" +
              "<p align='center'><b>" + newMainWindowTitle + "</b> " + rightMargin + " </p>" +
              "<p align='center'><hr />" +
              "<p align='center'>&nbsp;</p>" +
              "<p align='center'>Installer compilation date <b>" + buildVersion + "</b> " + rightMargin + " </p>" +
              "<p align='center'>&nbsp;</p>" +
              "<p align='center'>Plugin module update <b>" + moduleReleaseDate + "</b></p>" +
              "<p align='center'>&nbsp;</p>" +
              "<p align='center'>Plugin module version <b>" + specificationVersion.toString() + "</b>" + rightMargin + "</p>" +
              "<p align='center'>&nbsp;</p>" +
              "<p align='center'><hr />" +
//              "<p align='center'>&nbsp;</p>" +
//              " with issue reports welcome via <a href='" + LaunchEmailReportAction.MAILTO_REPORT_URL + "'>e-mail</a>." +
//              "</p>" +
//        TODO: link not working
//              "<p align='center'>&nbsp;</p>" +
//             + "<p align='center'>Use the X3D-Edit &Information menu to launch X3D-Edit home page and issue reports.</p>"
//             + "<p align='center'>&nbsp;</p>" 
//              "<p align='center'><a href='https://savage.nps.edu/X3D-Edit'>https://savage.nps.edu/X3D-Edit</a></p>" +
              "</html>";

//    final JButton reportButton = new JButton("Report");
//    final ActionListener emailReportActionListener = (ActionEvent event) ->
//    {
//       LaunchEmailReportAction.sendBrowserTo(LaunchEmailReportAction.MAILTO_REPORT_URL);
//    };
//    // null pointer can happen during a unit test ?!  perhaps artifact of prior javahelp dependency...
//    if (reportButton != null)
//    {
//        reportButton.setToolTipText("Send email issue report: please describe issue, give example .x3d excerpt or attach a snapshot");
//        reportButton.addActionListener(emailReportActionListener);
//        reportButton.setVisible(true);
//    }
//
//    Object[] optionalButtonsArray;
//    optionalButtonsArray = new Object[]{
//        aboutHtmlMessage,
//        reportButton
//    };
//
//    NotifyDescriptor notifyDescriptor = new DialogDescriptor(this,
//            "About X3D-Edit",
//            true,
//            optionalButtonsArray,
//            DialogDescriptor.OK_OPTION,     // default
//            DialogDescriptor.RIGHT_ALIGN, // align
//            HelpCtx.DEFAULT_HELP,
//            emailReportActionListener);


// TODO panel X3D-Edit icon working on mac, set it for windows...

        NotifyDescriptor notifyDescriptor = new NotifyDescriptor.Confirmation(
                aboutHtmlMessage, "About X3D-Edit", NotifyDescriptor.PLAIN_MESSAGE);

        DialogDisplayer.getDefault().notify(notifyDescriptor);
  }

  @Override
  public String getName()
  {
    return NbBundle.getMessage(X3dEditAboutAction.class, "CTL_X3dEditAboutAction");
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
    return HelpCtx.DEFAULT_HELP;
  }

  @Override
  protected boolean asynchronous()
  {
    return false;
  }

//  class aboutContentPanel extends JPanel
//  {
//    String link = "https://savage.nps.edu/X3D-Edit";
//        
//    aboutContentPanel()
//    {
//        // TODO add splash screen image
//        
//        // https://docs.oracle.com/javase/tutorial/i18n/format/simpleDateFormat.html
//        Date today;
//        String dateOutput;
//        SimpleDateFormat formatter;
//
//        formatter = new SimpleDateFormat("dd MMMM YYYY", Locale.US);
//        today = new Date();
//        dateOutput = formatter.format(today);
//        
//        String data1 = "<html><h2 align='center'>X3D-Edit 4.0</h2>" +
//                       "<p align='center'>Naval Postgraduate School (NPS), Monterey, California USA</p>" +
//                       "<p align='center'>Released " + dateOutput + 
//                       "</p></html>";
//        //<br>with automatic updates not yet available
//        
//      setBorder(new EmptyBorder(0,10,0,10));
//      setLayout(new BorderLayout());
//      JLabel label1 = new JLabel(data1);
//      label1.setHorizontalAlignment(JLabel.CENTER);
//      label1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
//      add(label1, BorderLayout.CENTER);
//      
//      final JButton butt = new JButton(link);
//      butt.setBorder(null);
//      butt.setFocusPainted(false);  // remove blue line
//      butt.setForeground(Color.blue);
//      add(butt,BorderLayout.SOUTH);
//      
//      butt.addMouseListener(new MouseAdapter()
//      {
//        @Override
//        public void mouseEntered(MouseEvent e)
//        {
//          butt.setForeground(Color.green.darker());
//        }
//
//        @Override
//        public void mouseExited(MouseEvent e)
//        {
//          butt.setForeground(Color.blue);
//        }       
//      });
//      butt.addActionListener((ActionEvent ev) -> {
//          try {
//              HtmlBrowser.URLDisplayer.getDefault().showURL(new URL(link));
//              
//              String urlString = link;
//              
//    // https://stackoverflow.com/questions/5226212/how-to-open-the-default-webbrowser-using-java
//    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
//        Desktop.getDesktop().browse(new URI(urlString));
//          } catch(IOException | URISyntaxException ex) {
//                Exceptions.printStackTrace(ex);
//            }
//        });
//    }
//  }
}
