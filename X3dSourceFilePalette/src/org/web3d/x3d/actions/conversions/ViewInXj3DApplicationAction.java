/*
* Copyright (c) 1995-2021 held by the author(s) .  All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions
* are met:
*
*  * Redistributions of source code must retain the above copyright
*       notice, this list of conditions and the following disclaimer.
*  * Redistributions in binary form must reproduce the above copyright
*       notice, this list of conditions and the following disclaimer
*       in the documentation and/or other materials provided with the
*       distribution.
*  * Neither the names of the Naval Postgraduate School (NPS)
*       Modeling Virtual Environments and Simulation (MOVES) Institute
*       (http://www.nps.edu and https://MovesInstitute.nps.edu)
*       nor the names of its contributors may be used to endorse or
*       promote products derived from this software without specific
*       prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
* "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
* LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
* FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
* COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
* INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
* BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
* CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
* ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
* POSSIBILITY OF SUCH DAMAGE.
*/
package org.web3d.x3d.actions.conversions;

import java.awt.BorderLayout;
import java.awt.Dialog;
import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;
import org.openide.windows.Mode;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;
import org.web3d.x3d.X3DDataObject;
import org.web3d.x3d.X3DEditorSupport;
import xj3d.browser.*;
/*
  This one views the scene in the Application version of the XJ3d installed player.
*/
@ActionID(id = "org.web3d.x3d.actions.conversions.ViewInXj3DApplicationAction", category = "View")
@ActionRegistration(
           iconBase = "org/web3d/x3d/resources/xj3d.png",
        displayName = "#CTL_ViewInXj3DApplicationAction",
               lazy = true) // don't do lazy=false since iconBase no longer gets registered

@ActionReferences(value={
  @ActionReference(path = "Menu/X3D-Edit/View Saved Scene", position = 50),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/View Saved Scene", position = 50),
})
@NbBundle.Messages("CTL_Xj3dViewerAction=Xj3D application")

/**
 * ViewInXj3DAction.java
 * Created on May 4, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey and Don Brutzman
 * @version $Id$
 */
public final class ViewInXj3DApplicationAction extends CallableSystemAction
{
    public void launchSceneInXj3DApplication (X3DEditorSupport.X3dEditor editorObject)
    {
        //  get file and launch Xj3D application
        String scenePath;
        try
        {
            scenePath = editorObject.getX3dEditorSupport().getDataObject().getPrimaryFile().getPath();
        }
        catch (Exception e)
        {
            scenePath = ""; // simply open
        }
        System.out.println("launchSceneInXj3DApplication(" + scenePath + ")");

        String[] args = {
            "-anisotropicDegree", "16",
            "-antialias", "8",
            "-enableOriginManager",
            "-numLoaderThreads", "4",
            "-swing",
            "-title", "Xj3D Application Browser for X3D-Edit",  // Xj3DBrowser.DEFAULT_TITLE
            scenePath,                  // last
        };
        Xj3DBrowser.main(args); // static
    }

    // code cribbed from BaseConversionsAction

  /**
   * Method called from app menu
   */
  @Override
  public void performAction()
  {
    // the Mode/TopComponent access must always be done in EventThread
    if(EventQueue.isDispatchThread())
      worker.run();
    else {
      try {
        EventQueue.invokeAndWait(worker);
      }
      catch(InterruptedException | InvocationTargetException ex) {
        System.err.println(BaseConversionsAction.Nb_Exception + ex.getClass().getSimpleName() + BaseConversionsAction.Nb_in_BaseConversionsAction);
      }
    }
  }

  private final Runnable worker = () -> {
      Mode m = WindowManager.getDefault().findMode("editor"); // noi18n
      TopComponent selectedOne = m.getSelectedTopComponent();

      TopComponent[] tcs = m.getTopComponents();
      if (tcs == null || tcs.length <= 0)
          return;
      
      Vector<X3DEditorSupport.X3dEditor> x3dTcs = new Vector<>();
      for (TopComponent tc : tcs)
          if (tc instanceof X3DEditorSupport.X3dEditor) {
              x3dTcs.add((X3DEditorSupport.X3dEditor) tc);
          }
      
      if (x3dTcs.size() == 1) {
          launchSceneInXj3DApplication(x3dTcs.get(0));
          return;
      }

      Integer selected = null;
      Vector<String> filenames = new Vector<>();
      for (X3DEditorSupport.X3dEditor ed : x3dTcs) {
          X3DDataObject dob = (X3DDataObject) ed.getX3dEditorSupport().getDataObject();
          filenames.add(dob.getPrimaryFile().getNameExt());
          if (ed.equals(selectedOne)) {
              //selected = filenames.size() - 1;
              // changed to:
              // if there are several X3d files and one of them is the top, do that one only
              launchSceneInXj3DApplication(ed);
              return;
          }
      }

      JList<String> list = new JList<>(filenames);
      list.setVisibleRowCount(3);
      list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
      if (selected != null)
          list.setSelectedIndex(selected);
      JPanel p = new JPanel(new BorderLayout());
      p.setBorder(new EmptyBorder(10, 10, 0, 10));
      p.add(new JLabel(BaseConversionsAction.Nb_Select_files_to_process), BorderLayout.NORTH);
      p.add(new JScrollPane(list), BorderLayout.CENTER);
      DialogDescriptor dd = new DialogDescriptor(p, BaseConversionsAction.Nb_Select_X3D_Files);
      Dialog dial = DialogDisplayer.getDefault().createDialog(dd);
      dial.setVisible(true);
      if (dd.getValue().equals(DialogDescriptor.CANCEL_OPTION))
          return;
      
      List<String>  selectedFns = list.getSelectedValuesList();

      for (String fn : selectedFns) {
          for (X3DEditorSupport.X3dEditor xed : x3dTcs)
              if (xed.getX3dEditorSupport().getDataObject().getPrimaryFile().getNameExt().equals(fn)) {
                  launchSceneInXj3DApplication(xed);
                  break;
              }
      }
    };
  
  @Override
  public String getName()
  {
    return NbBundle.getMessage(getClass(), "CTL_ViewInXj3DApplicationAction");
  }

  @Override
  public HelpCtx getHelpCtx()
  {
    return HelpCtx.DEFAULT_HELP;
  }
}

