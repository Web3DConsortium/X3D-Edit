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

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.HelpCtx;
import org.openide.util.ImageUtilities;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;
import org.web3d.x3d.actions.BaseViewAction;
import static org.web3d.x3d.actions.BaseViewAction.X3D_RESOURCES_EXAMPLES_ARCHIVES;
import org.web3d.x3d.actions.LaunchIssueReportEmailAction;
import static org.web3d.x3d.palette.items.BaseCustomizer.MAILTO_TOOLTIP;
// no longer supported import org.netbeans.api.javahelp.Help;

@ActionID(id = "org.web3d.x3d.DownloadX3dExamplesArchivesAction", category = "X3D-Edit")
// https://commons.wikimedia.org/wiki/File:Icon_download_96x96.png
@ActionRegistration(   iconBase = "org/web3d/x3d/resources/Icon_download_32x32.png",
                    displayName = "#CTL_DownloadX3dExamplesArchivesAction", 
                            lazy=true) // don't do lazy=false since iconBase no longer gets registered
@ActionReferences(value = {
  @ActionReference(path = "Menu/&X3D-Edit/&Example X3D Model Archives", position = 300),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/&Example X3D Model Archives", position = 300)
})

@SuppressWarnings("serial")
public final class DownloadX3dExamplesArchivesAction extends CallableSystemAction
{
  //private static Dialog dlg;
  //private static DialogDescriptor descriptor;
  private DownloadX3dExamplesArchivesPanel panel;
  private buttonBar buttonBar;
  JFrame frame;

  @Override
  public void performAction()
  {
    if (frame == null)
    {
        frame = new JFrame(NbBundle.getMessage(getClass(), "ExampleArchivesDownloadTitle"));
        frame.getContentPane().setLayout(new BorderLayout());
        BufferedImage bufferedImage;
        frame.setIconImage(ImageUtilities.loadImage("org/web3d/x3d/resources/Icon_download_32x32.png"));
        panel = new DownloadX3dExamplesArchivesPanel();

        frame.getContentPane().add(panel, BorderLayout.CENTER);
        buttonBar = new buttonBar();
        frame.getContentPane().add(buttonBar, BorderLayout.SOUTH);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

        final ActionListener closeActionListener = (ActionEvent e) -> {
          if (frame != null) {
              if (panel != null) {
                  if (panel.isRunning()) {
                      NotifyDescriptor d = new NotifyDescriptor.Confirmation(NbBundle.getMessage(getClass(), "MSG_StopDownloadQuestion"), //"Stop download task and close window?"
                              NbBundle.getMessage(getClass(), "MSG_DownloadInProgress"), NotifyDescriptor.YES_NO_OPTION);
                      if (DialogDisplayer.getDefault().notify(d) != NotifyDescriptor.YES_OPTION)
                          return;
                      panel.killTask();
                  }
              }
              frame.setVisible(false);
          }
        } // Close button handler
        ;
        buttonBar.closeButton.addActionListener(closeActionListener);        
        
        final ActionListener helpActionListener = (ActionEvent e) ->
        {            
            BaseViewAction.sendBrowserTo(X3D_RESOURCES_EXAMPLES_ARCHIVES);
            
// TODO convert from JavaHelp to help page
//          Help help = Lookup.getDefault().lookup(Help.class);
//          if (help != null) {
//              help.showHelp(getHelpCtx());
//          }
        };
        buttonBar.helpButton.addActionListener(helpActionListener);
        buttonBar.helpButton.setToolTipText("Show X3D Resources: Example Archives");
        
        final ActionListener reportActionListener = (ActionEvent e) ->
        {            
           LaunchIssueReportEmailAction.sendBrowserTo(LaunchIssueReportEmailAction.MAILTO_REPORT_URL + 
                   ", X3D-Edit " + NbBundle.getMessage(getClass(), "ExampleArchivesDownloadTitle"));
        };
        buttonBar.reportButton.addActionListener(reportActionListener);
        buttonBar.reportButton.setToolTipText(MAILTO_TOOLTIP);

        // Window title-bar close button handler
        WindowAdapter windowListener = new WindowAdapter()
        {
            @Override
            public void windowClosed(WindowEvent e)
            {
              closeActionListener.actionPerformed(null);
            }
        };
        frame.addWindowListener(windowListener);
    }
    frame.setLocationRelativeTo(null); // center of screen
    frame.setVisible(true);
  }

  class buttonBar extends JPanel
  {
    JButton helpButton, closeButton, reportButton;

    buttonBar()
    {
        java.awt.GridBagLayout gridBagLayout= new java.awt.GridBagLayout();
        
        java.awt.GridBagConstraints gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor  = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.fill    = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets  = new java.awt.Insets(3, 3, 3, 3);
        gridBagConstraints.weightx = 1.0;
        
        setLayout(gridBagLayout);
        setBorder(new EmptyBorder(3, 3, 3, 3));
//        add(Box.createHorizontalGlue());
        javax.swing.JLabel horizontalSpacerLabel = new javax.swing.JLabel();
        add(horizontalSpacerLabel,                gridBagConstraints);
        
         closeButton = new JButton("Close") ;
        reportButton = new JButton("Report");
          helpButton = new JButton("Help")  ;
      
         closeButton.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        reportButton.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
          helpButton.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        
        gridBagConstraints.weightx = 0.0;
        add( closeButton, gridBagConstraints);
        add(reportButton, gridBagConstraints);
        add(  helpButton, gridBagConstraints);
    }
  }

//  public void performActionOld()
//  {
//    // Only one dialog; if menu item is chosen when already open, never mind
//    if (dlg == null) {
//      ActionListener al = new ActionListener()
//      {
//        // Close button handler
//        public void actionPerformed(ActionEvent e)
//        {
//          if (dlg != null) {
//            if (panel != null) {
//              if (panel.isRunning()) {
//                NotifyDescriptor d = new NotifyDescriptor.Confirmation("Stop download task and close window?", "Download in progress", NotifyDescriptor.YES_NO_OPTION);
//                if (DialogDisplayer.getDefault().notify(d) != NotifyDescriptor.YES_OPTION)
//                  return;
//                panel.killTask();
//              }
//            }
//            dlg.setVisible(false);
//            dlg.dispose();
//            dlg = null;
//          }
//        }
//      };
//
//      // Window title-bar close button handler
//      WindowAdapter wl = new WindowAdapter()
//      {
//
//        @Override
//        public void windowClosed(WindowEvent e)
//        {
//          // Prevent title bar button from closing dialog
//          if (descriptor.getValue() == DialogDescriptor.CLOSED_OPTION)
//            dlg.setVisible(true);
//        }
//      };
//
//      JButton closeButton = new JButton("Close");
//      panel = new DownloadX3dExamplesArchivesPanel();
//      descriptor = new DialogDescriptor(panel,
//          NbBundle.getMessage(DownloadX3dExamplesArchivesPanel.class, "ExampleArchivesDownloadTitle"),
//          false,
//          new Object[]{closeButton}, closeButton,
//          DialogDescriptor.DEFAULT_ALIGN, HelpCtx.findHelp(panel), al);
//
//      dlg = DialogDisplayer.getDefault().createDialog(descriptor);
//      dlg.addWindowListener(wl);
//      dlg.pack();
//    }
//
//    dlg.setVisible(true);
//  }
  @Override
  public String getName()
  {
    return NbBundle.getMessage(DownloadX3dExamplesArchivesAction.class, "CTL_DownloadX3dExamplesArchivesAction");
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
    return new HelpCtx("helpExamples");
  }

  @Override
  protected boolean asynchronous()
  {
    return false;
  }
}
