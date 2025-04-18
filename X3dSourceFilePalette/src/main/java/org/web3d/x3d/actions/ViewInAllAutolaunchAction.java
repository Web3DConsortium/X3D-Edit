/*
* Copyright (c) 1995-2023 held by the author(s).  All rights reserved.
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
*       (https://www.nps.edu and https://MovesInstitute.nps.edu)
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

/**
 * ViewInAllAction.java
 * Created on 31 July 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id: ViewInAllAction.java 32293 2021-07-29 01:47:10Z tnorbraten $
 */
package org.web3d.x3d.actions;

import java.io.File;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.RequestProcessor;
import org.openide.util.actions.CookieAction;
import org.web3d.x3d.X3DDataObject;
import org.web3d.x3d.actions.conversions.ViewInXj3DApplicationAction;
import org.web3d.x3d.options.X3dEditUserPreferences;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFFloat;

/**
 * Autolaunch all X3D application viewers that are installed and enabled
 * @author brutzman
 */
@ActionID(id = "org.web3d.x3d.actions.ViewInAllAction", category = "X3D-Edit")
// https://en.wikipedia.org/wiki/Three_Arrows
@ActionRegistration(   iconBase = "org/web3d/x3d/resources/ThreeArrowsDrei_Pfeile.svg.32x32.png",
                    displayName = "#CTL_ViewInAllAction", lazy=true)
@ActionReferences( value = {
  @ActionReference(path = "Menu/&X3D-Edit/&Author Workflow", position = 70, separatorAfter = 71),
  @ActionReference(path = "Toolbars/X3D-Edit Author Workflow", position = 70),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/&Author Workflow", position = 70, separatorAfter = 71),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/&View Saved X3D Model", position = 110, separatorBefore = 109), // 
  @ActionReference(path = "Menu/&X3D-Edit/&View Saved X3D Model", position = 110, separatorBefore = 109),
  @ActionReference(path = "Shortcuts", name = "CS-9"), // shortcut control-shift-0 (L and F10 and 0 not working, A already taken by About X3D-Edit)
  // see Apache NetBeans > Help > Keyboard Shortcuts Card for other shortcuts
})

public final class ViewInAllAutolaunchAction extends CookieAction
{
  private static String launchInterval;
  private final ViewInBaseAction[] allActions;

  public ViewInAllAutolaunchAction()
  {
    allActions = new ViewInBaseAction[]
    {
      ViewInContactAction.get(ViewInContactAction.class),
      ViewInContactGeoAction.get(ViewInContactGeoAction.class),
      ViewInFreeWrlAction.get(ViewInFreeWrlAction.class),
      ViewInH3dAction.get(ViewInH3dAction.class),
      //    ViewInHeilanAction.get        (ViewInHeilanAction.class), // inactive support
      ViewInInstantRealityAction.get(ViewInInstantRealityAction.class),
      ViewInOctagaAction.get(ViewInOctagaAction.class),
      ViewInSwirlX3DAction.get(ViewInSwirlX3DAction.class),
      ViewInView3dSceneAction.get(ViewInView3dSceneAction.class),
      ViewInVivatyPlayerAction.get(ViewInVivatyPlayerAction.class),
      ViewInXj3DExternalAction.get(ViewInXj3DExternalAction.class),
      ViewInOtherAction.get(ViewInOtherAction.class)
    };

    if (areAnyLaunchActionsThere())
      return;

    this.setEnabled(false);
  }

  private boolean areAnyLaunchActionsThere()
  {
    for (ViewInBaseAction launchAction : allActions)
    {
      if(!launchAction.isAutoLaunch())
        continue;
      String path = launchAction.getExePath();
      if (path != null && path.length() > 0)
        if (new File(path).exists())
          return true;
    }
    // however note that embedded Xj3D is also included on the autolaunch menu
    return true; // false;
  }

    @Override
    protected boolean enable(Node[] activatedNodes)
    {
        if ((activatedNodes ==  null) || (activatedNodes.length == 0))
        {
            String message = "Cannot launch players if no X3D model is open or active. No action taken.";
            System.err.println ("*** " + this.getClass().getName() + ": " + message);
            NotifyDescriptor.Message msg = new NotifyDescriptor.Message(message);
            DialogDisplayer.getDefault().notify(msg);
            return false;
        }
        boolean localLaunchActionsAvailable = areAnyLaunchActionsThere();
        return super.enable(activatedNodes) && localLaunchActionsAvailable;
    }

  @Override
  protected void performAction(Node[] activatedNodes)
  {
        launchInterval = X3dEditUserPreferences.getLaunchInterval(); // seconds

        RequestProcessor rProc = RequestProcessor.getDefault();
        int delay=0;
        int increment = (int)((new SFFloat(launchInterval)).getValue() * 1000.0f); // convert seconds to msec
        
        // user-installed launch capabilities
        for(ViewInBaseAction launchAction : allActions)
        {
          if(!launchAction.enable(activatedNodes) || !launchAction.isAutoLaunch())
            continue;
          rProc.post(new ViewerRunner(launchAction,activatedNodes), delay);
          delay += increment;   // seconds to ms, but first is 0
        }
        try // built-in launch capabilities
        {
            // ViewInXj3DApplicationAction uses conversions package pattern
            ViewInXj3DApplicationAction viewInXj3DApplicationAction = new ViewInXj3DApplicationAction();
            viewInXj3DApplicationAction.performAction ();
            
//            ExportX3domAction exportX3domAction = new ExportX3domAction ();
//          exportX3domAction.performAction ();
        }
        catch (Exception e)
        {
            System.err.println("Error launching ViewInXj3DApplicationAction");
            e.printStackTrace(System.err);
        }
  }

  class ViewerRunner implements Runnable
  {
    ViewInBaseAction action;
    Node[] activatedNodes;
    ViewerRunner(ViewInBaseAction action, Node[] activatedNodes)
    {
      this.action = action;
      this.activatedNodes = activatedNodes;
    }
      @Override
    public void run()
    {
      action.performAction(activatedNodes);
    }
  }
  @Override
  public String getName()
  {
    return NbBundle.getMessage(getClass(), "CTL_ViewInAllAction");
  }

  @Override
  public HelpCtx getHelpCtx()
  {
    return HelpCtx.DEFAULT_HELP;
  }

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

