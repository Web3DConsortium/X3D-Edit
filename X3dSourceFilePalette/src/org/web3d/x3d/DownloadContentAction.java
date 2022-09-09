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

package org.web3d.x3d;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.Box;
import javax.swing.BoxLayout;
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
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;
// no longer supported import org.netbeans.api.javahelp.Help;

@ActionID(id = "org.web3d.x3d.DownloadContentAction", category = "Tools")
@ActionRegistration(displayName = "#CTL_DownloadContentAction", 
                    lazy=true) // don't do lazy=false since iconBase no longer gets registered
@ActionReferences(value = {
  @ActionReference(path = "Menu/X3D-Edit/Examples", position = 100),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/Examples", position = 100)
})

@SuppressWarnings("serial")
public final class DownloadContentAction extends CallableSystemAction
{
  //private static Dialog dlg;
  //private static DialogDescriptor descriptor;
  private ExampleArchivesDownloadPanel panel;
  private buttBar buttonBar;
  JFrame frame;

  @Override
  public void performAction()
  {
    if (frame == null) {
      frame = new JFrame(NbBundle.getMessage(getClass(), "ExampleArchivesDownloadTitle"));
      frame.getContentPane().setLayout(new BorderLayout());
      panel = new ExampleArchivesDownloadPanel();

      frame.getContentPane().add(panel, BorderLayout.CENTER);
      buttonBar = new buttBar();
      frame.getContentPane().add(buttonBar, BorderLayout.SOUTH);

      frame.pack();
      frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);

      final ActionListener al = (ActionEvent e) -> {
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
      buttonBar.closeButt.addActionListener(al);
      final ActionListener hl = (ActionEvent e) -> {
// TODO convert from JavaHelp to help page
//          Help help = Lookup.getDefault().lookup(Help.class);
//          if (help != null) {
//              help.showHelp(getHelpCtx());
//          }
      };
      buttonBar.helpButt.addActionListener(hl);

      // Window title-bar close button handler
      WindowAdapter wl = new WindowAdapter()
      {

        @Override
        public void windowClosed(WindowEvent e)
        {
          al.actionPerformed(null);
        }
      };
      frame.addWindowListener(wl);
    }

    frame.setLocationRelativeTo(null); // center of screen
    frame.setVisible(true);
  }

  class buttBar extends JPanel
  {

    JButton helpButt, closeButt;

    buttBar()
    {
      setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
      setBorder(new EmptyBorder(12, 12, 12, 12));
      add(Box.createHorizontalGlue());
      add(closeButt = new JButton("Close"));
      add(helpButt = new JButton("Help"));
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
//      JButton closeButt = new JButton("Close");
//      panel = new ExampleArchivesDownloadPanel();
//      descriptor = new DialogDescriptor(panel,
//          NbBundle.getMessage(ExampleArchivesDownloadPanel.class, "ExampleArchivesDownloadTitle"),
//          false,
//          new Object[]{closeButt}, closeButt,
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
    return NbBundle.getMessage(DownloadContentAction.class, "CTL_DownloadContentAction");
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
