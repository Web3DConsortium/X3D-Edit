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

import com.sauria.apachexml.ch10.DocumentVerifier;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.loaders.DataObject;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;
import org.w3c.dom.Document;
import org.web3d.x3d.BaseX3DEditAction;

@ActionID(id = "org.web3d.x3d.actions.security.CheckedSignedDocumentIntegrity", category = "X3D-Edit")
// https://letsencrypt.org/images/letsencrypt-logo-horizontal.svg
@ActionRegistration(   iconBase = "org/web3d/x3d/resources/LetsEncryptSeal30x32.png",
                    displayName = "#CTL_CheckedSignedDocumentIntegrity", 
                    lazy=true) // don't do lazy=false since iconBase no longer gets registered
@ActionReferences(value = {
  @ActionReference(path = "Menu/&X3D-Edit/XML &Security", position = 900),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/XML &Security", position = 900)})

/**
 * CheckedSignedDocumentIntegrity.java
 * Created July 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public final class CheckedSignedDocumentIntegrity extends BaseX3DEditAction
{
  @Override
  protected void performAction(Node[] activatedNodes)
  {
    BouncyCastleHelper.setup();
    super.performAction(activatedNodes);
  }

  @Override
  protected void doWork(Node[] activatedNodes)
  {
    try {
      Document w3cDoc = getW3cDocument();
      
      if (w3cDoc != null && !DocumentVerifier.hasSignature(w3cDoc, "")) {
        String msg = NbBundle.getMessage(getClass(), "MSG_SignatureNotFound"); //"No signature "
        NotifyDescriptor nd = new NotifyDescriptor.Message(msg, NotifyDescriptor.INFORMATION_MESSAGE);
        DialogDisplayer.getDefault().notify(nd);
        return;       
      }
      if (w3cDoc == null) {
          return; // sometimes happens when only and XML doc as active in the editor
      }

      List<XMLSignatureInput> failedRefs = DocumentVerifier.verifySignedDocument(w3cDoc, null, "");

      if (failedRefs.isEmpty())
        showMsg("Document integrity check successful");
      else {
        StringBuilder refsb = new StringBuilder();

        for (XMLSignatureInput xsi : failedRefs) {
          refsb.append(xsi.getSourceURI());
          refsb.append(" ");
        }
        if(refsb.length()>0) {
          refsb.insert(0, "Reference URI(s) ");
          refsb.append(" did not verify.");
          showMsg(refsb.toString(),JOptionPane.ERROR_MESSAGE);
        }
      }
    }
    catch (Exception ex) {  //todo, specific msgs for spec exceptions
      throw new RuntimeException(ex); // let baseclass handle
    }
  }

  private void showMsg(String msg)
  {
    showMsg(msg,JOptionPane.INFORMATION_MESSAGE);
  }
  private void showMsg(String msg, int typ)
  {
    JOptionPane.showMessageDialog(null, msg, "Verification Results", typ);
  }

  @Override
  protected int mode()
  {
    return CookieAction.MODE_EXACTLY_ONE;
  }

  @Override
  public String getName()
  {
    return NbBundle.getMessage(getClass(), "CTL_CheckedSignedDocumentIntegrity");
  }

  @Override
  protected Class<?>[] cookieClasses()
  {
    return new Class<?>[]{DataObject.class};
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

