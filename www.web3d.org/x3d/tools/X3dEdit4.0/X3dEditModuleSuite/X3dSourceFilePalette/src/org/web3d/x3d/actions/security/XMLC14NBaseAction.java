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
 * XMLC14NBaseAction.java
 * Created July 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
package org.web3d.x3d.actions.security;

import java.io.IOException;
import java.util.MissingResourceException;
import javax.swing.text.BadLocationException;
import org.apache.xml.security.c14n.CanonicalizationException;
import org.apache.xml.security.c14n.Canonicalizer;
import org.apache.xml.security.c14n.InvalidCanonicalizerException;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.nodes.Node;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.w3c.dom.Document;
import org.web3d.x3d.BaseX3DEditAction;
import org.web3d.x3d.X3DDataObject;

public abstract class XMLC14NBaseAction extends BaseX3DEditAction
{
  static
  {
    org.apache.xml.security.Init.init();
  }
  
  protected void basePerformAction(Node[] activatedNodes)//, String canonicalizerAlgorithm)
  {
    super.performAction(activatedNodes);
  }

  protected abstract String getAlgorithm();

  @Override
  protected void doWork(Node[] activatedNodes)
  {
    try {
      Document w3cDoc = getW3cDocument();

      Canonicalizer canner = Canonicalizer.getInstance(this.getAlgorithm());//canonicalizerAlgorithm);
      byte[] outputba = canner.canonicalizeSubtree(w3cDoc);
      String xmlString = new String(outputba);

      int len = abstractDocument.getLength();
      abstractDocument.replace(0, len, xmlString, null);

      InputOutput io = IOProvider.getDefault().getIO("Output", false);
      io.select();
      io.getOut().println(NbBundle.getMessage(getClass(), "MSG_CanonOpComplete")); //"Canonicalization operation complete");    
    }
    catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException | MissingResourceException | BadLocationException | CanonicalizationException | InvalidCanonicalizerException ex) {  //todo, specific msgs for spec exceptions
      //NotifyDescriptor.Exception ed = new NotifyDescriptor.Exception(ex);
      NotifyDescriptor.Message msg = new NotifyDescriptor.Message(ex.getLocalizedMessage());
      DialogDisplayer.getDefault().notifyLater(msg); //ed);
    }
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

