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

package org.web3d.x3d.actions.conversions;

import javax.swing.JEditorPane;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.appender.WriterAppender;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.cookies.EditCookie;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.web3d.x3d.X3DDataObject;
import org.web3d.x3d.X3DEditorSupport;
import org.web3d.x3d.tools.x3db.X3dCanonicalizer;

@ActionID(id = "org.web3d.x3d.actions.conversions.CanonicalizeC14nAction", category = "X3D-Edit")

@ActionRegistration(   iconBase = "org/web3d/x3d/resources/c14n.png",
                    displayName = "#CTL_CanonicalizeC14nAction", 
                    lazy=true) // don't do lazy=false since iconBase no longer gets registered
@ActionReferences(value = {
  @ActionReference(path = "Menu/&X3D-Edit/&Author Workflow", position = 20),
  @ActionReference(path = "Menu/&X3D-Edit/&Compression", position = 100),
  @ActionReference(path = "Menu/&X3D-Edit/XML &Security", position = 250),
  @ActionReference(path = "Toolbars/X3D-Edit Author Workflow", position = 20),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/&Author Workflow", position = 20),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/&Compression", position = 100),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/XML &Security", position = 250),
  @ActionReference(path = "Shortcuts", name = "CS-4"), // shortcut control-shift-4
  // see Apache NetBeans > Help > Keyboard Shortcuts Card for other shortcuts
})

public final class CanonicalizeC14nAction extends CookieAction
{
  @Override
  protected void performAction(Node[] activatedNodes)
  {
    X3DDataObject x3dDataObject = activatedNodes[0].getLookup().lookup(org.web3d.x3d.X3DDataObject.class);
    X3DEditorSupport x3DEditorSupport = x3dDataObject.getLookup().lookup(org.web3d.x3d.X3DEditorSupport.class);
    
    if (x3DEditorSupport.getOpenedPanes().length == 0)
    {
        String message = "Must first open an .x3d model prior to performing conversion.  No action taken.";
        System.err.println ("*** " + this.getClass().getName() + ": " + message);
        NotifyDescriptor.Message msg = new NotifyDescriptor.Message(message);
        DialogDisplayer.getDefault().notify(msg);
        return; // no action to perform
    }
    JEditorPane pane = x3DEditorSupport.getOpenedPanes()[0];

    InputOutput io = IOProvider.getDefault().getIO("Output", false);
    io.select();

    X3dCanonicalizer canner = new X3dCanonicalizer(pane.getText());
    
    // https://stackoverflow.com/questions/45771959/how-to-configure-writerappender-in-log4j2-xml
    PatternLayout layout = PatternLayout.newBuilder()
            .withPattern("%d{yyyy-MM-dd HH:mm:ss.SSS} %level [%t] [%c] [%M] [%l] - %msg%n")
            .build();

    WriterAppender app = WriterAppender.newBuilder()
            .setName("writeLogger")
            .setTarget(io.getOut())
            .setLayout(layout)
            .build();
    canner.addLog4jAppender(app);
    Level oldLev = canner.setLog4jLevel(Level.INFO);

    if (!canner.isDigitallySigned()) {
        if (!canner.isCanonical()) {
            pane.setText(canner.getFinalC14nScene());
        }
       pane.setCaretPosition(0); // top of document
       io.getOut().println(NbBundle.getMessage(getClass(), "MSG_CanonOpComplete")); //"Canonicalization operation complete");    
    } else {
       io.getOut().println("*** X3D Document digitally signed and assumed to be C14N'd");
    }
    
    canner.setLog4jLevel(oldLev);
    canner.removeLog4jAppender(app);
  }

  @Override
  protected int mode()
  {
    return CookieAction.MODE_EXACTLY_ONE;
  }

  @Override
  public String getName()
  {
    return NbBundle.getMessage(CanonicalizeC14nAction.class, "CTL_CanonicalizeC14nAction");
  }

  @Override
  protected Class<?>[] cookieClasses()
  {
    return new Class<?>[]{EditCookie.class};
  }

  @Override
  protected void initialize()
  {
    super.initialize();
    // see org.openide.util.actions.SystemAction.iconResource() Javadoc for more details
    putValue("noIconInMenu", Boolean.FALSE); //Boolean.TRUE);
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

