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
/**
 * ViewX3dInBrowserAction.java
 * Created on Nov 27, 2007, 9:29 AM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
package org.web3d.x3d;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.MissingResourceException;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.awt.HtmlBrowser;
import org.openide.awt.StatusDisplayer;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.nodes.Node;
import org.openide.util.Exceptions;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;

@ActionID(id = "org.web3d.x3d.ViewX3dInBrowserAction", category = "View")
@ActionRegistration(displayName = "#CTL_ViewX3dInBrowser", lazy=true)
@ActionReferences(value = {
  @ActionReference(path = "Menu/X3D-Edit/View Saved Scene", position = 113),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/View Saved Scene", position = 113)
}) 

public final class ViewX3dInBrowserAction extends CookieAction
{
  @Override
  protected void performAction(Node[] activatedNodes)
  {
    X3DDataObject x3DDataObject = activatedNodes[0].getLookup().lookup(X3DDataObject.class);

    FileObject fo = x3DDataObject.getPrimaryFile();
    try {

      // Method 1: use temp file
      //File tempF = File.createTempFile(fo.getName(),"."+fo.getExt());
      //tempF.deleteOnExit();
      //FileUtil.copy(is,new FileOutputStream(tempF));

      // Method 2: use original file
      File tempF = FileUtil.toFile(fo);

      java.util.ResourceBundle bun = NbBundle.getBundle(ViewX3dInBrowserAction.class);
      String text = bun.getString("STATUSLINE_OpeningBrowser");
      String url = "file://"+tempF.getAbsolutePath();
      // String warning = bun.getString("STATUSLINE_OpeningBrowser_warning");
      StatusDisplayer.getDefault().setStatusText(text+url); //+warning);

      HtmlBrowser.URLDisplayer.getDefault().showURLExternal(new URL(url));
    }
    catch(IOException | MissingResourceException e) {
      Exceptions.printStackTrace(e);
    }
  }

  @Override
  protected int mode()
  {
    return CookieAction.MODE_EXACTLY_ONE;
  }

  @Override
  public String getName()
  {
    return NbBundle.getMessage(ViewX3dInBrowserAction.class, "CTL_ViewX3dInBrowser");
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
  public HelpCtx getHelpCtx()
  {
    return HelpCtx.DEFAULT_HELP;
  }

  @Override
  protected boolean asynchronous()
  {
    return false;
  }
}

