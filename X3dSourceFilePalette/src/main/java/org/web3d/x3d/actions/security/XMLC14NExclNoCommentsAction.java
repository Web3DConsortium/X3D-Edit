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
    * Neither the name of the Web3D Consortium (https://www.web3d.org)
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
package org.web3d.x3d.actions.security;

import org.apache.xml.security.c14n.Canonicalizer;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;

@ActionID(id = "org.web3d.x3d.actions.security.XMLC14NExclNoCommentsAction", category = "X3D-Edit")

@ActionRegistration(   iconBase = "org/web3d/x3d/resources/c14n_blue.png",
                    displayName = "#CTL_XMLC14NExclNoCommentsAction", 
                    lazy=true) // don't do lazy=false since iconBase no longer gets registered
@ActionReferences(value = {
  @ActionReference(path = "Menu/&X3D-Edit/XML &Security/C14N format using XML Canonicalization...", position = 220),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/XML &Security/C14N format using XML Canonicalization...", position = 220)
})

/**
 * XMLC14NExclNoCommentsAction.java
 * Created July 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public final class XMLC14NExclNoCommentsAction extends XMLC14NBaseAction
{
  @Override
  protected void performAction(Node[] activatedNodes)
  {
    basePerformAction(activatedNodes); //, Canonicalizer.ALGO_ID_C14N_EXCL_OMIT_COMMENTS);
  }

  @Override
  protected String getAlgorithm()
  {
    return Canonicalizer.ALGO_ID_C14N_EXCL_OMIT_COMMENTS;
  }

  @Override
  protected void doWork(Node[] activatedNodes) {
      super.doWork(activatedNodes);
  }

  @Override
  public String getName()
  {
    return NbBundle.getMessage(XMLC14NExclNoCommentsAction.class, "CTL_XMLC14NExclNoCommentsAction");
  }

  @Override
  public HelpCtx getHelpCtx()
  {
    return HelpCtx.DEFAULT_HELP;
  }
}

