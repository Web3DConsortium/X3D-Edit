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

/**
 * DecryptDocumentAction.java
 * Created July 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id: DecryptDocumentAction.java 32346 2021-08-03 21:53:25Z tnorbraten $
 */
package org.web3d.x3d.actions.security;

import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.loaders.DataObject;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;
import org.web3d.x3d.BaseX3DEditAction;

//import java.awt.Dialog;
//import java.io.ByteArrayOutputStream;
//import java.security.KeyStore;
//import java.security.KeyStore.Entry;
//import org.apache.xml.security.encryption.XMLCipher;
//import org.apache.xml.security.utils.EncryptionConstants;
//import org.apache.xml.security.utils.XMLUtils;
//import org.openide.DialogDescriptor;
//import org.openide.DialogDisplayer;
//import org.openide.NotifyDescriptor;
//import org.openide.windows.IOProvider;
//import org.openide.windows.InputOutput;
//import org.w3c.dom.Document;
//import org.web3d.x3d.actions.security.ManageKeyStoreAction.OperationCancelledException;

@ActionID(id = "org.web3d.x3d.actions.security.DecryptDocumentAction", category = "Tools")
@ActionRegistration(displayName = "#CTL_DecryptDocumentAction", 
                    lazy=true) // don't do lazy=false since iconBase no longer gets registered
@ActionReferences(value = {
  @ActionReference(path = "Menu/X3D-Edit/Security", position = 400),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/Security", position = 400)})

public final class DecryptDocumentAction extends BaseX3DEditAction
{
  @Override
  protected void performAction(Node[] activatedNodes)
  {
    DecryptXmlAction.get(DecryptXmlAction.class).performAction();
  }

  @Override
  protected void doWork(Node[] activatedNodes)
  {
    // Not used, work done in performAction
  }

  /*
   * When an x3d file gets encrypted, it becomes an xml file and now receives an xml file type.  As such, when it is opened in the editor,
   * it doesn't have all the x3d-specific actions available to it in the right-click context menu.
   * Furthermore, this context action (DecryptDocumentAction) will never find anything to decrypt when is
   * is executed against an x3d file, which is the _only_ type which it works with.
   * Until I find a way to put this action into the default XML editor as a context menu, I'll simply defer to the
   * global action which finds an xml file through a chooser and decrypts that.  That's what the code above
   * does.  THe code below is the original
   */
//  protected void performActionOriginal(Node[] activatedNodes)
//  {
//    BouncyCastleHelper.setup();
//
//    try {
//      actionPreamble(activatedNodes);
//
//      DialogDescriptor descriptor;
//      SelectKeyPanel keyPan;
//      try {
//        keyPan = BouncyCastleHelper.buildSelectKeyPanel(SelectKeyPanel.ENCRYPTING_KEY_TYPE);
//        if(keyPan == null)
//          return;
//        descriptor = new DialogDescriptor(keyPan, NbBundle.getMessage(getClass(), "SelectDecryptKeyDialogTitle"));
//      }
//      catch (OperationCancelledException cex) {
//        return;
//      }
//      catch (Exception ex) {
//        String msg = NbBundle.getMessage(getClass(), "MSG_KeystoreError") + ex.getLocalizedMessage(); //"Keystore error: "
//        NotifyDescriptor nd = new NotifyDescriptor.Message(msg, NotifyDescriptor.ERROR_MESSAGE);
//        DialogDisplayer.getDefault().notify(nd);
//        return;
//      }
//
//      Dialog dlg = null;
//      try {
//        dlg = DialogDisplayer.getDefault().createDialog(descriptor);
//        dlg.setResizable(true);
//        dlg.pack();
//        dlg.setVisible(true);
//      }
//      finally {
//        if (dlg != null)
//          dlg.dispose();
//      }
//
//      if(descriptor.getValue() == DialogDescriptor.CANCEL_OPTION)
//        return;
//
//      org.w3c.dom.Document w3cDoc = getW3cDocument();
////      org.w3c.dom.NodeList nlist = w3cDoc.getElementsByTagName("X3D");
////      org.w3c.dom.Element w3cElem = (org.w3c.dom.Element)nlist.item(0);
//
//      Entry ent = keyPan.getSelectedEntry();
//      if (ent instanceof KeyStore.SecretKeyEntry) {
//        KeyStore.SecretKeyEntry secKeyEnt = (KeyStore.SecretKeyEntry) ent;
//        org.apache.xml.security.Init.init();
//
//        XMLCipher cipher = XMLCipher.getProviderInstance(XMLCipher.TRIPLEDES,"BC");
//        String namespaceUrl = EncryptionConstants.EncryptionSpecNS;
//        org.w3c.dom.Element encyElem = (org.w3c.dom.Element)w3cDoc.getElementsByTagNameNS(
//            namespaceUrl,"EncryptedData").item(0);
//        if(encyElem == null)
//          throw new Exception(NbBundle.getMessage(getClass(), "MSG_EncryptedDataNotFound")); //"Encrypted data not found");
//
//        cipher.init(XMLCipher.DECRYPT_MODE, secKeyEnt.getSecretKey());
//        Document newdoc = cipher.doFinal(w3cDoc, encyElem);
//
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        XMLUtils.outputDOMc14nWithComments(newdoc, baos);
//        String xmlString = baos.toString("UTF-8");
//        int len = abDoc.getLength();
//        abDoc.replace(0, len, xmlString, null);
//      }
////      if (ent instanceof KeyStore.PrivateKeyEntry) {
////        // Doesn't work
////
////
////      }
//      else {
//        throw new Exception(NbBundle.getMessage(getClass(), "MSG_SecretToDecrypt")); //"Use only secret (symmetric) keys to decrypt");
//      }
//
//      InputOutput io = IOProvider.getDefault().getIO("Output", false);
//      io.select();
//      io.getOut().println(NbBundle.getMessage(getClass(), "MSG_DecryptOpComplete"));//"Decryption operation complete");
//    }
//    catch (Exception ex) {  //todo, specific msgs for spec exceptions
//      NotifyDescriptor.Exception ed = new NotifyDescriptor.Exception(ex);
//      DialogDisplayer.getDefault().notifyLater(ed);
//    }
//
//    actionPostamble();
//  }

  @Override
  protected int mode()
  {
    return CookieAction.MODE_EXACTLY_ONE;
  }

  @Override
  public String getName()
  {
    return NbBundle.getMessage(DecryptDocumentAction.class, "CTL_DecryptDocumentAction");
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

