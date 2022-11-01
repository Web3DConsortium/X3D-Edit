/*
Copyright (c) 1995-2021 held by the author(s).  All rights reserved.
 
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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;

import org.openide.awt.HtmlBrowser;
import org.openide.util.Exceptions;

import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;

//// TODO
////@ActionID(id = "org.web3d.x3d.x3dedit.config.X3dEditAboutAction", category = "Tools")
////@ActionRegistration(   iconBase = "org/web3d/x3d/resources/X3D.png",
////                    displayName = "About X3D-Edit",
////                            lazy=true)
////@ActionReferences(value = {
////  @ActionReference(path = "Menu/X3D", position = 625), // see layer.xml
////})

@ActionID(id = "org.web3d.x3d.x3dedit.config.X3dEditAboutAction", category = "Tools")
@ActionRegistration(// iconBase = "org/web3d/x3d/resources/X3Dicon16.png", // TODO file path/name correct but not found
                    displayName = "#CTL_X3dEditAboutAction",
                            lazy=true)
@ActionReferences(value = {
  @ActionReference(path = "Menu/X3D-Edit/X3D-Edit configuration", position = 100), // see layer.xml
  @ActionReference(path = "Editors/model/x3d+xml/Popup/X3D-Edit configuration", position = 100),
})

public final class X3dEditAboutAction extends CallableSystemAction
{

  public X3dEditAboutAction()
  {
  }

  @Override
  public void performAction()
  {
        // https://docs.oracle.com/javase/tutorial/i18n/format/simpleDateFormat.html
        Date today;
        String dateOutput;
        SimpleDateFormat formatter;

        formatter = new SimpleDateFormat("dd MMMM YYYY", Locale.US);
        today = new Date();
        dateOutput = formatter.format(today); // TODO don't want today, want build date!
        
        // TODO add splash screen image
        
        String message = "<html>"
            + "<p>&nbsp;</p>" +
              "<h2 align='center'>X3D-Edit 4.0 &nbsp; &nbsp; </h2>" +
              "<p align='center'>X3D-Edit is a free, open-source Extensible 3D (X3D) Graphics authoring tool.</p>" +
              "<p align='center'>&nbsp;</p>" +
              "<p align='center'>Revised &nbsp;<b>" + 
                    "1 November 2022" + // dateOutput + // TODO change to regex changeable BUILD_DATE_REVISION
                    "</b> with automatic updates not yet available.</p>" +
              "<p align='center'>&nbsp;</p>" +
              "<p align='center'>Use the X3D menu to launch the X3D-Edit Home.</p>";

//        TODO: link not working
//              "<p align='center'>&nbsp;</p>" +
//              "<p align='center'><a href='https://savage.nps.edu/X3D-Edit'>https://savage.nps.edu/X3D-Edit</a></p>";
        
        NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                message, "About X3D-Edit", NotifyDescriptor.PLAIN_MESSAGE);
        DialogDisplayer.getDefault().notify(descriptor);
                  
//    DialogDescriptor descriptor = new DialogDescriptor(new aboutContent(),
//                                                       NbBundle.getMessage(X3dEditAboutAction.class, "X3D_About_Dialog_Title"));
//    
//    descriptor.setOptionType(DEFAULT_OPTION);
    
////    DialogDescriptor descriptor = new DialogDescriptor(
////            new aboutContent(),
////            NbBundle.getMessage(X3dEditAboutAction.class, "X3D_About_Dialog_Title"),
////            true, //model
////            new Object[0], //options
////            null, //initialvalue
////            DialogDescriptor.DEFAULT_ALIGN,
////            null, // helpctx
////            null);// actionlistener
//
//    Dialog dlg = null;
//    try {
//        dlg = DialogDisplayer.getDefault().createDialog(descriptor);
//        dlg.setResizable(false);
//        //dlg.setMinimumSize(new Dimension(300,200));
//        //dlg.setMaximumSize(new Dimension(300,200));
//        dlg.pack();
//        dlg.setVisible(true);
//    }
//    finally {
//      if(dlg != null)
//        dlg.dispose();
//    }
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

  class aboutContent extends JPanel
  {
    String link = "https://savage.nps.edu/X3D-Edit";
        
    aboutContent()
    {
        // TODO add splash screen image
        
        // https://docs.oracle.com/javase/tutorial/i18n/format/simpleDateFormat.html
        Date today;
        String dateOutput;
        SimpleDateFormat formatter;

        formatter = new SimpleDateFormat("dd MMMM YYYY", Locale.US);
        today = new Date();
        dateOutput = formatter.format(today);
        
        String data1 = "<html><h2 align='center'>X3D-Edit 4.0</h2>" +
                       "<p align='center'>Naval Postgraduate School (NPS), Monterey, California USA</p>" +
                       "<p align='center'>Released " + dateOutput + 
                       "</p></html>";
        //<br>with automatic updates not yet available
        
      setBorder(new EmptyBorder(0,10,0,10));
      setLayout(new BorderLayout());
      JLabel label1 = new JLabel(data1);
      label1.setHorizontalAlignment(JLabel.CENTER);
      label1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
      add(label1, BorderLayout.CENTER);
      
      final JButton butt = new JButton(link);
      butt.setBorder(null);
      butt.setFocusPainted(false);  // remove blue line
      butt.setForeground(Color.blue);
      add(butt,BorderLayout.SOUTH);
      
      butt.addMouseListener(new MouseAdapter()
      {
        @Override
        public void mouseEntered(MouseEvent e)
        {
          butt.setForeground(Color.green.darker());
        }

        @Override
        public void mouseExited(MouseEvent e)
        {
          butt.setForeground(Color.blue);
        }       
      });
      butt.addActionListener((ActionEvent ev) -> {
          try {
              HtmlBrowser.URLDisplayer.getDefault().showURL(new URL(link));
              
              String urlString = link;
              
    // https://stackoverflow.com/questions/5226212/how-to-open-the-default-webbrowser-using-java
    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
        Desktop.getDesktop().browse(new URI(urlString));
          } catch(IOException | URISyntaxException ex) {
                Exceptions.printStackTrace(ex);
            }
        });
    }
  }
}
