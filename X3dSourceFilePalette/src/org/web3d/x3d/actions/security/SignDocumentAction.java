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
    * Neither the name of the Web3D Consortium (http://www.web3D.org)
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

import com.sauria.apachexml.ch10.DocumentSigner;
import java.awt.Dialog;
import java.io.ByteArrayOutputStream;
import java.security.KeyStore.Entry;
import java.security.KeyStore.PrivateKeyEntry;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import javax.swing.JMenuItem;
import org.apache.xml.security.signature.XMLSignature;
import org.apache.xml.security.utils.XMLUtils;
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
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.w3c.dom.Document;
import org.web3d.x3d.BaseX3DEditAction;
import org.web3d.x3d.actions.security.ManageKeyStoreAction.OperationCancelledException;
import org.web3d.x3d.types.X3DSchemaData;

@ActionID(id = "org.web3d.x3d.actions.security.SignDocumentAction", category = "X3D-Edit")
// https://commons.wikimedia.org/wiki/File:Cross-fountain-pen-nib.gif
@ActionRegistration(   iconBase = "org/web3d/x3d/resources/Cross-fountain-pen-nib30x32.png",
                    displayName = "#CTL_SignDocumentAction", 
                           lazy=true) // don't do lazy=false since iconBase no longer gets registered
@ActionReferences(value = {
  @ActionReference(path = "Menu/&X3D-Edit/XML &Security", position = 800),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/XML &Security", position = 800)})

/**
 * Digitally signs a XML (X3D) document using a specified public/private key pair.
 * 
 * Created July 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 */
public final class SignDocumentAction extends BaseX3DEditAction
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
      DialogDescriptor descriptor;

      SelectKeyPanel keyPan;
      try {
        keyPan = BouncyCastleHelper.buildSelectKeyPanel(SelectKeyPanel.SIGNING_KEY_TYPE); // emphasize signing keys
        if(keyPan == null)
          return;
        descriptor = new DialogDescriptor(keyPan, NbBundle.getMessage(getClass(), "SelectSigningKeyDialogTitle"));
      }
      catch (OperationCancelledException cex) {
        return;
      }
      catch (Exception ex) {
        String msg = "Keystore error: " + ex.getLocalizedMessage();
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

      if (descriptor.getValue() == DialogDescriptor.CANCEL_OPTION)
        return;
      
      Document w3cDoc = getW3cDocument();
      if (w3cDoc == null) {
          String msg = "Signing error: You must first sign the document, then encrypt if desired";
          NotifyDescriptor nd = new NotifyDescriptor.Message(msg, NotifyDescriptor.ERROR_MESSAGE);
          DialogDisplayer.getDefault().notify(nd);
          return;
      } // can happen when attempting to sign an encrypted document
      
      Entry ent = keyPan.getSelectedEntry();

      if (!(ent instanceof PrivateKeyEntry)) {
        throw new Exception(NbBundle.getMessage(getClass(), "MSG_PubPrivateToSign")); //"Use only public/private key pairs to sign");
      }

      PrivateKeyEntry prKeyEnt = (PrivateKeyEntry) ent;

      PrivateKey privKey = prKeyEnt.getPrivateKey();
      X509Certificate cert = (X509Certificate) prKeyEnt.getCertificate();
      PublicKey pubKey = cert.getPublicKey();
      String signatureMethod = XMLSignature.ALGO_ID_SIGNATURE_RSA;
      String digestMethod = org.apache.xml.security.utils.Constants.ALGO_ID_DIGEST_SHA1;

      DocumentSigner signer = new DocumentSigner(w3cDoc, privKey, cert, pubKey, "", signatureMethod, digestMethod);
      w3cDoc = signer.sign();
      
      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      signer.writeSignature(baos);
      baos.reset();
      XMLUtils.outputDOMc14nWithComments(w3cDoc, baos);
      String signedXml = baos.toString("UTF-8");
      
      // During document signing by the Apache XML Security suite, the XML
      // header and the X3D doctype element are stripped since the signing is
      // performed at the X3D (root) element.
      signedXml = insertDocTypeAndXmlHeader(signedXml);
      
      int len = abstractDocument.getLength();
      abstractDocument.replace(0, len, signedXml, null);
      
      InputOutput io = IOProvider.getDefault().getIO("Output", false);
      io.select();
      io.getOut().println(NbBundle.getMessage(getClass(), "MSG_SignOpComplete")); //"Signing operation complete");
    }
    catch (Exception ex) {  //todo, specific msgs for spec exceptions
      throw new RuntimeException(ex); // let baseclass handle
    }
  }

  @Override
  public JMenuItem getMenuPresenter()
  {
    JMenuItem mi = super.getMenuPresenter();
    mi.setToolTipText(NbBundle.getMessage(getClass(), "CTL_SignDocumentAction.toolTipText"));
    return mi;
  }

  @Override
  public JMenuItem getPopupPresenter()
  {
    JMenuItem mi = super.getMenuPresenter();
    mi.setToolTipText(NbBundle.getMessage(getClass(), "CTL_SignDocumentAction.toolTipText"));
    return mi;
  }

  @Override
  protected int mode()
  {
    return CookieAction.MODE_EXACTLY_ONE;
  }

  @Override
  public String getName()
  {
    return NbBundle.getMessage(SignDocumentAction.class, "CTL_SignDocumentAction");
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
  
  private String insertDocTypeAndXmlHeader(String xml)
  {
    String lineSep = System.getProperty("line.separator");
    StringBuilder sb = new StringBuilder();
    
    if (!xml.contains("<?xml")) {
      sb.append(X3DSchemaData.XML_HEADER);
      sb.append(lineSep);
    }
    if (xml.contains("<X3D")) {
      if (xml.contains("version=\"4.0\"")) {
        sb.append(X3DSchemaData.DOCTYPE_4_0);
        sb.append(lineSep);
      }
      else if (xml.contains("version=\"3.3\"")) {
        sb.append(X3DSchemaData.DOCTYPE_3_3);
        sb.append(lineSep);
      }
      else if (xml.contains("version=\"3.2\"")) {
        sb.append(X3DSchemaData.DOCTYPE_3_2);
        sb.append(lineSep);
      }
      else if (xml.contains("version=\"3.1\"")) {
        sb.append(X3DSchemaData.DOCTYPE_3_1);
        sb.append(lineSep);
      }
      else if (xml.contains("version=\"3.0\"")) {
        sb.append(X3DSchemaData.DOCTYPE_3_0);
        sb.append(lineSep);
      }
    }
    sb.append(xml);
    return sb.toString();
  }
}

