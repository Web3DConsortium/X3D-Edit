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
 * EncryptDocumentAction.java
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

import java.awt.Dialog;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.security.KeyStore;
import java.security.KeyStore.Entry;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.utils.XMLUtils;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.web3d.x3d.BaseX3DEditAction;
import org.web3d.x3d.actions.conversions.ConversionsHelper;
import org.web3d.x3d.actions.security.ManageKeyStoreAction.OperationCancelledException;

@ActionID(id = "org.web3d.x3d.actions.security.EncryptDocumentAction", category = "Tools")
@ActionRegistration(displayName = "#CTL_EncryptDocumentAction", 
                    lazy=true) // don't do lazy=false since iconBase no longer gets registered
@ActionReferences(value = {
  @ActionReference(path = "Menu/X3D-Edit/Security", position = 300),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/Security", position = 300)})

public final class EncryptDocumentAction extends BaseX3DEditAction
{
  private JFileChooser saveChooser;
  private JCheckBox openInEditorCB;

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
        keyPan = BouncyCastleHelper.buildSelectKeyPanel(SelectKeyPanel.ENCRYPTING_KEY_TYPE);
        if (keyPan == null)
          return;
        descriptor = new DialogDescriptor(keyPan, NbBundle.getMessage(getClass(), "SelectEncryptKeyDialogTitle"));
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

      Document w3cDoc = getW3cDocument(); //new XercesDOMAdapter().getDocument(this.x3dEditorSupport.getInputStream(), false);
      NodeList nlist = w3cDoc.getElementsByTagName("X3D");
      Element w3cElem = (org.w3c.dom.Element) nlist.item(0);
      Document newdoc;

      Entry ent = keyPan.getSelectedEntry();

      if (ent instanceof KeyStore.SecretKeyEntry) {
        KeyStore.SecretKeyEntry secKeyEnt = (KeyStore.SecretKeyEntry) ent;
        org.apache.xml.security.Init.init();

        XMLCipher cipher = XMLCipher.getProviderInstance(XMLCipher.TRIPLEDES, "BC");
        cipher.init(XMLCipher.ENCRYPT_MODE, secKeyEnt.getSecretKey());
        newdoc = cipher.doFinal(w3cDoc, w3cElem);
      }
      else if (ent instanceof KeyStore.PrivateKeyEntry) {
        // Doesn't work
        KeyStore.PrivateKeyEntry prKeyEnt = (KeyStore.PrivateKeyEntry) ent;
        org.apache.xml.security.Init.init();

        XMLCipher cipher = XMLCipher.getProviderInstance(XMLCipher.TRIPLEDES, "BC");
        cipher.init(XMLCipher.ENCRYPT_MODE, prKeyEnt.getCertificate().getPublicKey());
        newdoc = cipher.doFinal(w3cDoc, w3cElem);
      }
      else {
        throw new Exception(NbBundle.getMessage(getClass(), "MSG_SecretToEncrypt")); //"Use only secret (symmetric) keys to encrypt");
      }

      ByteArrayOutputStream baos = new ByteArrayOutputStream();
      XMLUtils.outputDOMc14nWithComments(newdoc, baos);
      String xmlString = baos.toString("UTF-8");

      if (saveChooser == null) {
        saveChooser = new JFileChooser();
        saveChooser.setDialogTitle(NbBundle.getMessage(getClass(), "MSG_SaveEncryptedFileTitle")); //"Save Encrypted File");
        saveChooser.setDialogType(JFileChooser.SAVE_DIALOG);
        openInEditorCB = new JCheckBox(NbBundle.getMessage(getClass(),"MSG_OpenInEditor"));//"Open in editor");
        openInEditorCB.setSelected(true); // by default
        saveChooser.setAccessory(openInEditorCB);
      }
      FileObject thisFo = x3dEditorSupport.getDataObject().getPrimaryFile();
      FileObject directory = thisFo.getParent();
      saveChooser.setCurrentDirectory(FileUtil.toFile(directory));

      String outputType = "xml";// no i18n

      String outFileNm = FileUtil.findFreeFileName(directory, thisFo.getName(), outputType);

      saveChooser.setSelectedFile(new File(outFileNm + "." + outputType));
      if (saveChooser.showSaveDialog(null) == JFileChooser.CANCEL_OPTION)
        return;

      File outFile = saveChooser.getSelectedFile();
      FileWriter outFw = new FileWriter(outFile);
      outFw.write(xmlString);
      outFw.close();

      if (openInEditorCB.isSelected()) {
        ConversionsHelper.openInEditor(outFile);
      }

      InputOutput io = IOProvider.getDefault().getIO("Output", false);
      io.select();
      io.getOut().println(NbBundle.getMessage(getClass(), "MSG_EncryptOpComplete")); //"Encryption operation complete");
    }
    catch (Exception ex) {  //todo, specific msgs for spec exceptions
      throw new RuntimeException(ex); // let baseclass handle
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
    return NbBundle.getMessage(EncryptDocumentAction.class, "CTL_EncryptDocumentAction");
  }

  @Override
  protected Class[] cookieClasses()
  {
    return new Class[]{DataObject.class};
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

