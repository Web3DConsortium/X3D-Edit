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
 * VerifySignatureAction.java
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

import com.sauria.apachexml.ch10.DocumentVerifier;
import java.awt.Dialog;
import java.security.KeyStore;
import java.security.KeyStore.Entry;
import java.security.PublicKey;
import java.util.List;
import javax.swing.JOptionPane;
import org.apache.xml.security.signature.XMLSignatureInput;
import org.openide.DialogDescriptor;
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
import org.web3d.x3d.actions.security.ManageKeyStoreAction.OperationCancelledException;

@ActionID(id = "org.web3d.x3d.actions.security.VerifySignatureAction", category = "Tools")
@ActionRegistration(displayName = "#CTL_VerifySignatureAction", 
                    lazy=true) // don't do lazy=false since iconBase no longer gets registered
@ActionReferences(value = {
  @ActionReference(path = "Menu/X3D-Edit/Security", position = 1000),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/Security", position = 1000)})

public final class VerifySignatureAction extends BaseX3DEditAction
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
      
      if(!DocumentVerifier.hasSignature(w3cDoc, "")) {
        String msg = NbBundle.getMessage(getClass(), "MSG_SignatureNotFound"); //"No signature "
        NotifyDescriptor nd = new NotifyDescriptor.Message(msg, NotifyDescriptor.ERROR_MESSAGE);
        DialogDisplayer.getDefault().notify(nd);
        return;       
      }
       
      DialogDescriptor descriptor;
      SelectKeyPanel keyPan;
      try {
        keyPan = BouncyCastleHelper.buildSelectKeyPanel(SelectKeyPanel.SIGNING_KEY_TYPE);
        if(keyPan == null)
          return;
        descriptor = new DialogDescriptor(keyPan, NbBundle.getMessage(getClass(), "SelectVerifySignKeyDialogTitle"));
      }
      catch (OperationCancelledException cex) {
        return;
      }
      catch (Exception ex) {
        String msg = NbBundle.getMessage(getClass(), "MSG_KeystoreError") + ex.getLocalizedMessage(); //"Keystore error: "
        NotifyDescriptor nd = new NotifyDescriptor.Message(msg, NotifyDescriptor.ERROR_MESSAGE);
        DialogDisplayer.getDefault().notify(nd);
        return;
      }

      Dialog dlg = null;
      try {
        dlg = DialogDisplayer.getDefault().createDialog(descriptor);
        dlg.setResizable(true);
        dlg.pack();
        dlg.setVisible(true);
      }
      finally {
        if (dlg != null)
          dlg.dispose();
      }

      if(descriptor.getValue() == DialogDescriptor.CANCEL_OPTION)
        return;

      Entry ent = keyPan.getSelectedEntry();
      if(ent == null)
        return;
      PublicKey pKey;
      if(ent instanceof KeyStore.PrivateKeyEntry)
        pKey = ((KeyStore.PrivateKeyEntry)ent).getCertificateChain()[0].getPublicKey();
      else if(ent instanceof KeyStore.TrustedCertificateEntry)
        pKey = ((KeyStore.TrustedCertificateEntry)ent).getTrustedCertificate().getPublicKey();
      else
        throw new Exception(NbBundle.getMessage(getClass(), "MSG_PubPrivateTrustedToVerify"));//"Use only public/private key pairs or trusted keys to verify");

      List failedRefs = DocumentVerifier.verifySignedDocument(w3cDoc, pKey, "");
      
      if(failedRefs.size()<=0)       
        showMsg(NbBundle.getMessage(getClass(), "MSG_VerifyOpComplete")); //"Signature in document verified successfully");
      else {
        StringBuilder refsb = new StringBuilder();
        for (Object obj : failedRefs) {
          if(obj instanceof XMLSignatureInput) {
            XMLSignatureInput xsi = (XMLSignatureInput) obj;
            refsb.append(xsi.getSourceURI());
            refsb.append(" ");
          }
          else
            showMsg(NbBundle.getMessage(getClass(), "MSG_VerifyOpFailure")+obj.toString(),JOptionPane.ERROR_MESSAGE); //"Signature verification check failed: "
        }
        if(refsb.length()>0) {
          refsb.insert(0, NbBundle.getMessage(getClass(), "MSG_ReferenceURIs")); //"Reference URI(s) "
          refsb.append(NbBundle.getMessage(getClass(), "MSG_DidNotVerify")); //" did not verify."
          showMsg(refsb.toString(),JOptionPane.ERROR_MESSAGE);
        }
     }
    }
    catch (Exception ex) {  //todo, specific msgs for spec exceptions
      throw new RuntimeException(ex); //let baseclass handle
    }

  }

  private void showMsg(String msg, int typ)
  {
    JOptionPane.showMessageDialog(null, msg, NbBundle.getMessage(getClass(), "MSG_VerifyResults"), typ); //"Verification Results"
  }

  private void showMsg(String msg)
  {
    JOptionPane.showMessageDialog(null, msg, NbBundle.getMessage(getClass(), "MSG_VerifyResults"), JOptionPane.INFORMATION_MESSAGE);    
  }
  
  @Override
  protected int mode()
  {
    return CookieAction.MODE_EXACTLY_ONE;
  }

  @Override
  public String getName()
  {
    return NbBundle.getMessage(VerifySignatureAction.class, "CTL_VerifySignatureAction");
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

