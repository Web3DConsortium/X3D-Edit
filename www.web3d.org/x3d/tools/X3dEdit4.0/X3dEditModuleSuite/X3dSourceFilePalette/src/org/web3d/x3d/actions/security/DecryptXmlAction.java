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
 * DecryptXmlAction.java
 * Created on Jul 29, 2008
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
import java.io.FileInputStream;
import java.io.FileWriter;
import java.security.KeyStore;
import java.security.KeyStore.Entry;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;
import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.utils.EncryptionConstants;
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
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.w3c.dom.Document;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSParser;
import org.web3d.x3d.actions.conversions.ConversionsHelper;
import org.web3d.x3d.actions.security.ManageKeyStoreAction.OperationCancelledException;
import org.web3d.x3d.types.X3DSchemaData;

@ActionID(id = "org.web3d.x3d.actions.security.DecryptXmlAction", category = "Tools")
@ActionRegistration(displayName = "#CTL_DecryptXmlAction", 
                    lazy=true) // don't do lazy=false since iconBase no longer gets registered
@ActionReferences(value = {
  @ActionReference(path = "Menu/X3D-Edit/Security", position = 1650),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/Security", position = 450)
})

public final class DecryptXmlAction extends CallableSystemAction
{
  private JFileChooser fileChooser;
  private JCheckBox openInEditorCB;
  private JFileChooser saveChooser;
  
  @Override
  public void performAction()
  {
    BouncyCastleHelper.setup();
    
    try {
      DOMImplementationRegistry dreg = DOMImplementationRegistry.newInstance();

      if (fileChooser == null) {
        fileChooser = new JFileChooser();
        fileChooser.setMultiSelectionEnabled(false);
        fileChooser.setDialogTitle(NbBundle.getMessage(getClass(),"MSG_SelectDecryptedFileTitle")); //"Select Encrypted XML File");
        fileChooser.setDialogType(JFileChooser.OPEN_DIALOG);
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileChooser.setAcceptAllFileFilterUsed(true);
        fileChooser.setFileFilter(new XMLFilter());
      }

      if (fileChooser.showOpenDialog(null) != JFileChooser.APPROVE_OPTION)
        return;
      
      DialogDescriptor descriptor;
      SelectKeyPanel keyPan;
      try {
        keyPan = BouncyCastleHelper.buildSelectKeyPanel(SelectKeyPanel.ENCRYPTING_KEY_TYPE);
        if(keyPan == null)
          return;
        descriptor = new DialogDescriptor(keyPan, NbBundle.getMessage(getClass(), "SelectDecryptKeyDialogTitle"));
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

      DOMImplementationLS dls = (DOMImplementationLS) dreg.getDOMImplementation("LS");
      LSParser parser = dls.createLSParser(DOMImplementationLS.MODE_SYNCHRONOUS, null);
      LSInput lsInp = dls.createLSInput();
      lsInp.setEncoding("UTF-8");
      lsInp.getByteStream();
      lsInp.setByteStream(new FileInputStream(fileChooser.getSelectedFile()));
      Document w3cDoc = parser.parse(lsInp);
      
      Entry ent = keyPan.getSelectedEntry();
      if (ent instanceof KeyStore.SecretKeyEntry) {
        KeyStore.SecretKeyEntry secKeyEnt = (KeyStore.SecretKeyEntry) ent;
        org.apache.xml.security.Init.init();

        XMLCipher cipher = XMLCipher.getProviderInstance(XMLCipher.TRIPLEDES,"BC");
        String namespaceUrl = EncryptionConstants.EncryptionSpecNS;
        org.w3c.dom.Element encyElem = (org.w3c.dom.Element)w3cDoc.getElementsByTagNameNS(namespaceUrl,"EncryptedData").item(0);
        if(encyElem == null)
          throw new Exception(NbBundle.getMessage(getClass(), "MSG_EncryptedDataNotFound")); //"Encrypted data not found");
        
        cipher.init(XMLCipher.DECRYPT_MODE, secKeyEnt.getSecretKey());
        Document newdoc = cipher.doFinal(w3cDoc, encyElem);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        XMLUtils.outputDOMc14nWithComments(newdoc, baos);
        String xmlString = baos.toString("UTF-8");
        
        xmlString = insertDocTypeAndXmlHeader(xmlString);
        
        if(saveChooser == null) {
          saveChooser = new JFileChooser();
          saveChooser.setDialogTitle(NbBundle.getMessage(getClass(),"MSG_SaveDecryptedFileTitle")); //"Save Decrypted File");
          saveChooser.setDialogType(JFileChooser.SAVE_DIALOG);
          openInEditorCB = new JCheckBox(NbBundle.getMessage(getClass(),"MSG_OpenInEditor"));//"Open in editor");
          openInEditorCB.setSelected(true); // by default
          saveChooser.setAccessory(openInEditorCB);
        }
        saveChooser.setCurrentDirectory(fileChooser.getCurrentDirectory());
        
        FileObject inpFo = FileUtil.toFileObject(fileChooser.getSelectedFile());
        String outputType = inpFo.getExt();
        if(xmlString.contains("<X3D"))  // no i18n
          outputType = "x3d";           // no i18n

        String outFileNm = FileUtil.findFreeFileName(inpFo.getParent(), inpFo.getName()+"_dec", outputType);
        
        saveChooser.setSelectedFile(new File(outFileNm+"."+outputType));
        if(saveChooser.showSaveDialog(null) == JFileChooser.CANCEL_OPTION)
          return;
        
        File outFile = saveChooser.getSelectedFile();
        FileWriter outFw = new FileWriter(outFile);
        outFw.write(xmlString);
        outFw.close();
        
        if(openInEditorCB.isSelected()) {
          ConversionsHelper.openInEditor(outFile);
        }        
      }
      else {
        throw new Exception(NbBundle.getMessage(getClass(), "MSG_SecretToDecrypt")); //"Use only secret (symmetric) keys to decrypt");
        // caught below
      }
      
      InputOutput io = IOProvider.getDefault().getIO("Output", false);
      io.select();
      io.getOut().println(NbBundle.getMessage(getClass(), "MSG_DecryptOpComplete"));//"Decryption operation complete");
    }
    catch (Exception ex) {
      //NotifyDescriptor.Exception ed = new NotifyDescriptor.Exception(ex);
      NotifyDescriptor.Message msg = new NotifyDescriptor.Message(ex.getLocalizedMessage());
      DialogDisplayer.getDefault().notifyLater(msg); //ed);
    }
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

  class XMLFilter extends FileFilter
  {
    String fileType = "xml";
    String fileTypeUC = fileType.toUpperCase();

    @Override
    public boolean accept(File f)
    {
      if (f.isDirectory())
        return true;

      String extension = FileUtil.toFileObject(f).getExt();
      if (extension != null) {
        if (extension.equals(fileType) || extension.equals(fileTypeUC))
          return true;
      }
      return false;
    }

    //The description of this filter
    @Override
    public String getDescription()
    {
      return NbBundle.getMessage(getClass(), "MSG_EncryptedXmlFiles"); //"Encrypted XML Files";
    }
  }

  @Override
  public String getName()
  {
    return NbBundle.getMessage(getClass(), "CTL_DecryptXmlAction");
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
