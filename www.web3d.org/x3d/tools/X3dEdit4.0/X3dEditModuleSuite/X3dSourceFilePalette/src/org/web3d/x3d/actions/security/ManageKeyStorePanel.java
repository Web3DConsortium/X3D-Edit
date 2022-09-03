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
 * ManageKeyStorePanel.java
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

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.KeyStore;
import java.security.KeyStore.Entry;
import java.security.KeyStore.PasswordProtection;
import java.security.KeyStore.SecretKeyEntry;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;
import javax.crypto.SecretKey;
import javax.security.auth.x500.X500Principal;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.apache.xml.security.utils.XMLUtils;
import org.bouncycastle.x509.X509V1CertificateGenerator;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.web3d.x3d.actions.conversions.ProcessRunner;
import org.web3d.x3d.actions.security.BouncyCastleHelper.KeystorePasswordException;
import org.web3d.x3d.actions.security.ManageKeyStoreAction.OperationCancelledException;
import org.web3d.x3d.options.X3dOptions;

/**
 *
 * @author  mike
 */
public class ManageKeyStorePanel extends javax.swing.JPanel
{
  private final int ALIAS_TABLE_COLUMN = 0;
  private final int TYPE_TABLE_COLUMN = 1;
  private final int DATE_TABLE_COLUMN = 2;
  private final String ALIAS_TITLE = NbBundle.getMessage(getClass(), "MSG_KeyTableAliasColumnTitle"); //"Alias";
  private final String TYPE_TITLE = NbBundle.getMessage(getClass(), "MSG_KeyTableTypeColumnTitle"); //"Type";
  private final String DATE_TITLE = NbBundle.getMessage(getClass(), "MSG_KeyTableDateColumnTitle"); //"Date added";

  private Vector<String> titles;
  private KeyStore ks;
  private File ksFile;
  private FileInputStream ksInpStr;
  private char[] pw;

  private static JFileChooser saveChooser,openChooser;
  private static File lastChooserDir = new File(System.getProperty("user.home")+"/X3D-Edit/security");
  
  /** Creates new form ManageKeyStorePanel */
  public ManageKeyStorePanel(char[] password) throws Exception
  {
    initComponents();
    HelpCtx.setHelpIDString(ManageKeyStorePanel.this, "MANAGE_KEYSTORE_HELPID");

    pw = password;
    
    titles = new Vector<>(3);
    titles.add(ALIAS_TITLE);
    titles.add(TYPE_TITLE);
    titles.add(DATE_TITLE);

    initKeyStore();  // also gets called from reloadTable()
    initKeyTable();
    reloadTable();
    
    customizeColumnWidths();
  }

  private char[] getAPassword(String msg)
  {
    return BouncyCastleHelper.getAPassword(msg);
//    if (msg == null)
//      msg = org.openide.util.NbBundle.getMessage(getClass(), "MSG_EnterPassword"); //"Enter password:"
//    JPasswordField pwF = new JPasswordField(10);
//    int ret = JOptionPane.showConfirmDialog(this, pwF, msg, JOptionPane.OK_CANCEL_OPTION);
//    if (ret == JOptionPane.CANCEL_OPTION)
//      return null;
//    return pwF.getPassword();
  }

  private String getNewAlias(String typ) throws Exception
  {
    boolean repeat;
    String returnStr = null;
    do {
      String msg = NbBundle.getMessage(getClass(), "MSG_EnterNewSpace")+typ+NbBundle.getMessage(getClass(), "MSG_SpaceAliasSpace"); //"Enter new " " alias "; 
      JTextField tF = new JTextField(17);
      int ret = JOptionPane.showConfirmDialog(this, tF, msg, JOptionPane.OK_CANCEL_OPTION);
      if (ret == JOptionPane.CANCEL_OPTION)
        return null;
      if (ks.containsAlias(tF.getText())) {
        int i = JOptionPane.showConfirmDialog(this, NbBundle.getMessage(getClass(), "MSG_AliasInUseRetry")); //"Alias in use.  Retry?");
        if (i != JOptionPane.OK_OPTION)
          return null;
        repeat = true;
      }
      else {
        repeat = false;
        returnStr = tF.getText();
      }
    }
    while (repeat);
    
    return returnStr;
  }

  private void initKeyStore() throws Exception
  {
    ks = BouncyCastleHelper.getKeyStore();
    String path = X3dOptions.getKeystorePath();
    ksFile = new File(path);
    if (ksFile.exists())
      ksInpStr = new FileInputStream(ksFile);
  }
  
  private void initKeyTable() throws Exception
  {
    String msg = NbBundle.getMessage(getClass(), "MSG_EnterKeystorePassword");
    if(ksInpStr == null)
      msg = NbBundle.getMessage(getClass(), "MSG_EnterPasswordForNewKeystore");
    
    pw = getAPassword(msg); //"Enter " "keystore password"
    if (pw == null)
      throw new OperationCancelledException();
  }

  private void reloadTable() throws Exception
  {
    initKeyStore();
      
    try {
      ks.load(ksInpStr, pw); // null inpstr means new, empty keystore
    }
    catch(IOException ioex) {
      throw new KeystorePasswordException(NbBundle.getMessage(getClass(), "MSG_BadPassword"));//"Password incorrect or file unreadable");
    }
    
    Vector<Vector<String>> rows = new Vector<>();
    if (ks != null) {
      Enumeration<String> aliases = ks.aliases();
      if (aliases.hasMoreElements()) {
        while (aliases.hasMoreElements()) {
          String s = aliases.nextElement();
          Entry ent;
          if(ks.isCertificateEntry(s))
            ent = ks.getEntry(s, null);
          else
            ent = ks.getEntry(s, new KeyStore.PasswordProtection(pw));
          Vector<String> vs = new Vector<>(3);
          vs.add(s);
          if (ent instanceof KeyStore.SecretKeyEntry) {
            vs.add(NbBundle.getMessage(getClass(), "MSG_Secret")); //"Secret key");
          }
          else if (ent instanceof KeyStore.PrivateKeyEntry) {
            vs.add(NbBundle.getMessage(getClass(), "MSG_PubPrivPair"));//"Public/private key pair");
          }
          else if (ent instanceof KeyStore.TrustedCertificateEntry) {
            vs.add(NbBundle.getMessage(getClass(), "MSG_TrustedPublic")); //"Trusted public key");
          }
          vs.add(ks.getCreationDate(s).toString());
          rows.add(vs);
        }
      }
    }
    Collections.sort(rows, dateSorter);
    ((DefaultTableModel) keyTable.getModel()).setDataVector(rows, titles);
     
    if(rows.size()>0)
      keyTable.setRowSelectionInterval(0,0);
  }
  
  private ByDateSorter dateSorter = new ByDateSorter();

  private class ByDateSorter implements Comparator<Vector<String>>
  {
    @Override
    public int compare(Vector<String> v1, Vector<String> v2)
    {
      return v1.elementAt(DATE_TABLE_COLUMN).compareTo(
          v2.elementAt(DATE_TABLE_COLUMN));
    }
  }

  private int defaultAliasColWidth = 135; //90;
  private int defaultTypeColWidth = 120; //130;
  private int defaultDateColWidth = 245; //235;

  private void saveColumnWidths()
  {
    TableColumnModel colMod = keyTable.getColumnModel();
    defaultAliasColWidth = colMod.getColumn(ALIAS_TABLE_COLUMN).getWidth();
    defaultTypeColWidth = colMod.getColumn(TYPE_TABLE_COLUMN).getWidth();
    defaultDateColWidth = colMod.getColumn(DATE_TABLE_COLUMN).getWidth();
  }

  private void customizeColumnWidths()
  {
    TableColumnModel colMod = keyTable.getColumnModel();

    colMod.getColumn(ALIAS_TABLE_COLUMN).setPreferredWidth(defaultAliasColWidth);
    colMod.getColumn(TYPE_TABLE_COLUMN).setPreferredWidth(defaultTypeColWidth);
    colMod.getColumn(DATE_TABLE_COLUMN).setPreferredWidth(defaultDateColWidth);
  }

  private void addSecretKey(char[] pw) throws Exception
  {
    SecretKey secKey = BouncyCastleHelper.createSecretKey();

    String alias = getNewAlias(NbBundle.getMessage(getClass(), "MSG_Secret_lc")); //"secret key");
    if(alias == null)
      return;
    
    ks.setKeyEntry(alias,secKey,pw,null);
    
    commonCleanup(NbBundle.getMessage(getClass(), "MSG_SecretKeyGenOpComplete")); //"Secret (symmetric) key generation and store operation complete");
  }
  
  private void addPrivateKeyPair(char[] pw) throws Exception
  {
    String alias = getNewAlias(NbBundle.getMessage(getClass(), "MSG_KeyPair")); //"key pair");
    if (alias == null)
      return;

    KeyPair pair = KeyPairGenerator.getInstance(BouncyCastleHelper.getKeyPairGenerationAlgorithm(), "BC").genKeyPair();
    X509V1CertificateGenerator certGen = new X509V1CertificateGenerator();
    X500Principal dnName = new X500Principal(BouncyCastleHelper.getCerficateDistinguishedName());

    certGen.setSerialNumber(BigInteger.valueOf(BouncyCastleHelper.getNextCertificateSerialNumber()));
    certGen.setIssuerDN(dnName);
    Date startDate = new Date();
    certGen.setNotBefore(startDate);            // time from which certificate is valid
    certGen.setNotAfter(new Date(startDate.getTime() + BouncyCastleHelper.getCertificateValidityInDays() * 24 * 60 * 60 * 1000));   // time after which certificate is not valid
    certGen.setSubjectDN(dnName);               // note: same as issuer
    certGen.setPublicKey(pair.getPublic());
    certGen.setSignatureAlgorithm(BouncyCastleHelper.getKeyPairSigningAlgorithm());

    X509Certificate cert = certGen.generate(pair.getPrivate(), "BC");
    
    ks.setKeyEntry(alias, pair.getPrivate(), pw, new X509Certificate[]{cert});
 
    commonCleanup(NbBundle.getMessage(getClass(), "MSG_KeyPairGenOpComplete")); //"Key pair generation and store operation complete");
  }
  
  private void commonCleanup(String msg) throws Exception
  {
    ksFile.getParentFile().mkdirs();
    
    FileOutputStream fow = new FileOutputStream(ksFile);
    ks.store(fow, pw);

    InputOutput io = IOProvider.getDefault().getIO("Output", false);
    io.select();
    io.getOut().println(msg);

    saveColumnWidths();
    reloadTable();
    customizeColumnWidths();
  }
  
  private void deleteKey(String alias, char[] pw) throws Exception
  {
    ks.deleteEntry(alias);
    
    commonCleanup(NbBundle.getMessage(getClass(), "MSG_KeyDeleteOpComplete")); //"Key deletion operation complete");
  }
  
  private void olddeleteKey(String alias, char[] pw) throws Exception
  {
    Vector<String> v = new Vector<>();

    v.add(System.getProperty("java.home") + File.separator + "bin" + File.separator + "keytool");
    v.add("-delete");

    v.add("-alias");
    v.add(alias);
    v.add("-storetype");
    v.add(KeyStore.getDefaultType());
    v.add("-keystore");
    v.add(ksFile.getAbsolutePath());
    v.add("-storepass");
    v.add(new String(pw));
    //v.add("-providerClass");  v.add("org.bouncycastle.jce.provider.BouncyCastleProvider");
    //dumpit(v);

    InputOutput io = IOProvider.getDefault().getIO("Output", false);
    io.select();
    ProcessRunner.exec(io.getOut(), v);

    io.getOut().println(NbBundle.getMessage(getClass(), "MSG_KeyDeleteOpComplete"));

    saveColumnWidths();
    reloadTable();
    customizeColumnWidths();
  }
      
  private void oldaddPrivateKeyPair(char[] pw) throws Exception
  {
    addKeyCommon("-genkey", pw);
  }

  private void addKeyCommon(String cmd, char[] passw) throws Exception
  {
    Vector<String> v = new Vector<>();

    v.add(System.getProperty("java.home") + File.separator + "bin" + File.separator + "keytool");
    v.add(cmd);

    String alias = getNewAlias("key");
    if (alias == null)
      return;

    v.add("-alias");
    v.add(alias);
    v.add("-keyalg");
    v.add(BouncyCastleHelper.getKeyPairGenerationAlgorithm()); //"RSA");
    v.add("-keysize");
    v.add(Integer.toString(BouncyCastleHelper.getKeyPairKeySize())); //"1024");
    v.add("-sigalg");
    v.add(BouncyCastleHelper.getKeyPairSigningAlgorithm()); //"SHA1WithRSA");
    v.add("-dname");
    v.add(BouncyCastleHelper.getCerficateDistinguishedName()); //ManageKeyStorePanel.DISTINGUISHEDNAME);
    v.add("-keypass");
    v.add(new String(passw));
    v.add("-validity");
    v.add(Long.toString(BouncyCastleHelper.getCertificateValidityInDays()));//ManageKeyStorePanel.VALIDITY_PERIOD_IN_DAYS));
    v.add("-storetype");
    v.add(KeyStore.getDefaultType());
    v.add("-keystore");
    v.add(ksFile.getAbsolutePath());
    v.add("-storepass");
    v.add(new String(passw));
    //v.add("-providerClass");  v.add("org.bouncycastle.jce.provider.BouncyCastleProvider");
    //dumpit(v);
    InputOutput io = IOProvider.getDefault().getIO("Output", false);
    io.select();
    ProcessRunner.exec(io.getOut(), v);

    io.getOut().println(NbBundle.getMessage(getClass(), "MSG_KeyGenOpComplete")); //"Key generation and store operation complete");

    saveColumnWidths();
    reloadTable();
    customizeColumnWidths();
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {



    createButt = new JButton();
    importButt = new JButton();
    exportButt = new JButton();
    jLabel1 = new JLabel();
    jLabel2 = new JLabel();
    jScrollPane1 = new JScrollPane();
    keyTable = new JTable();
    deleteButt = new JButton();

    setBorder(BorderFactory.createEtchedBorder());

    createButt.setText(NbBundle.getMessage(ManageKeyStorePanel.class, "ManageKeyStorePanel.createButt.text")); // NOI18N
    createButt.setToolTipText(NbBundle.getMessage(getClass(), "ManageKeyStorePanel.createButt.tooltip")); // NOI18N
    createButt.setMinimumSize(new Dimension(40, 22));
    createButt.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        createButtActionPerformed(evt);
      }
    });

    importButt.setText(NbBundle.getMessage(ManageKeyStorePanel.class, "ManageKeyStorePanel.importButt.text")); // NOI18N
    importButt.setToolTipText(NbBundle.getMessage(getClass(), "ManageKeyStorePanel.importButt.tooltip")); // NOI18N
    importButt.setMinimumSize(new Dimension(40, 22));
    importButt.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        importButtActionPerformed(evt);
      }
    });

    exportButt.setText(NbBundle.getMessage(ManageKeyStorePanel.class, "ManageKeyStorePanel.exportButt.text")); // NOI18N
    exportButt.setToolTipText(NbBundle.getMessage(getClass(), "ManageKeyStorePanel.exportButt.tooltip")); // NOI18N
    exportButt.setMinimumSize(new Dimension(40, 22));
    exportButt.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        exportButtActionPerformed(evt);
      }
    });

    jLabel1.setText(NbBundle.getMessage(ManageKeyStorePanel.class, "ManageKeyStorePanel.jLabel1.text")); // NOI18N
    jLabel2.setText(NbBundle.getMessage(ManageKeyStorePanel.class, "ManageKeyStorePanel.jLabel2.text")); // NOI18N
    keyTable.setModel(new ROTableModel());
    keyTable.setToolTipText(NbBundle.getMessage(getClass(), "TT_KeyTable")); // NOI18N
    jScrollPane1.setViewportView(keyTable);

    deleteButt.setText(NbBundle.getMessage(ManageKeyStorePanel.class, "ManageKeyStorePanel.deleteButt.text")); // NOI18N
    deleteButt.setToolTipText(NbBundle.getMessage(getClass(), "ManageKeyStorePanel.deleteButt.tooltip")); // NOI18N
    deleteButt.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        deleteButtActionPerformed(evt);
      }
    });

    GroupLayout layout = new GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(Alignment.LEADING)
          .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 884, Short.MAX_VALUE)
          .addGroup(Alignment.TRAILING, layout.createSequentialGroup()
            .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, 196, Short.MAX_VALUE)
            .addPreferredGap(ComponentPlacement.RELATED)
            .addComponent(createButt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(ComponentPlacement.RELATED)
            .addComponent(deleteButt)
            .addGap(18, 18, 18)
            .addComponent(importButt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(ComponentPlacement.RELATED)
            .addComponent(exportButt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(ComponentPlacement.RELATED)
            .addComponent(jLabel2, GroupLayout.DEFAULT_SIZE, 197, Short.MAX_VALUE)))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jScrollPane1, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
        .addPreferredGap(ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(Alignment.BASELINE)
          .addComponent(createButt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
          .addComponent(deleteButt)
          .addComponent(importButt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
          .addComponent(exportButt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
          .addComponent(jLabel2)
          .addComponent(jLabel1))
        .addContainerGap())
    );
  }// </editor-fold>//GEN-END:initComponents

private void exportButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportButtActionPerformed
  int row = keyTable.getSelectedRow();
  if (row == -1)
    return;
  String alias = (String) keyTable.getModel().getValueAt(row, ALIAS_TABLE_COLUMN);
  try {
    Entry ent = ks.getEntry(alias, new PasswordProtection(pw));
    String msg = "";
    if(ks.entryInstanceOf(alias, KeyStore.SecretKeyEntry.class)) {
        try (PrintStream ps = doSaveChooser(alias,"_key.b64")) {
            if(ps == null)
                return;
            
            SecretKey sKey = ((SecretKeyEntry)ent).getSecretKey();
            byte[] ba = sKey.getEncoded();
            String encStr = XMLUtils.encodeToString(ba);
            ps.print(encStr);
        }
      msg = "Key ";
    }
    else if(ks.entryInstanceOf(alias, KeyStore.PrivateKeyEntry.class)) {
        try (PrintStream ps = doSaveChooser(alias,"_certificateChain.cer")) {
            if(ps == null)
                return;
            
            X509Certificate cert = (X509Certificate) ks.getCertificate(alias);
            ps.println(BouncyCastleHelper.BEGIN_CERT);
            String encStr = XMLUtils.encodeToString(cert.getEncoded());
            ps.print(encStr);
            ps.println(BouncyCastleHelper.END_CERT);
        }
      msg = NbBundle.getMessage(getClass(), "MSG_CertificateSpace"); //"Certificate ";
    }
    else {
      throw new Exception(NbBundle.getMessage(getClass(), "MSG_UnknownKeystoreEntry")); //"Unknown keystore entry");
    }
    
    InputOutput io = IOProvider.getDefault().getIO("Output", false);
    io.select();
    io.getOut().println(msg+NbBundle.getMessage(getClass(), "MSG_ExportOpComplete")); //"export operation complete");
  }
  catch (Exception ex) {
    String msg = NbBundle.getMessage(getClass(), "MSG_KeyExportError") + ex.getLocalizedMessage(); //"Key export error: "
    NotifyDescriptor d = new NotifyDescriptor.Message(msg, NotifyDescriptor.ERROR_MESSAGE);
    DialogDisplayer.getDefault().notify(d);
  }
}//GEN-LAST:event_exportButtActionPerformed

private PrintStream doSaveChooser(String alias, String filesuffix) throws FileNotFoundException
{
  if (saveChooser == null)
    saveChooser = new JFileChooser();
  File f = new File(lastChooserDir, alias + filesuffix);
  saveChooser.setSelectedFile(f);
  if (saveChooser.showSaveDialog(this) == JFileChooser.CANCEL_OPTION)
    return null;
  lastChooserDir = saveChooser.getSelectedFile().getParentFile();
  return new PrintStream(new FileOutputStream(saveChooser.getSelectedFile()));
} 

private void importButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_importButtActionPerformed
  try {
    if (openChooser == null)
      openChooser = new JFileChooser();
    openChooser.setCurrentDirectory(lastChooserDir);
    if (openChooser.showOpenDialog(this) == JFileChooser.CANCEL_OPTION)
      return;
    lastChooserDir = openChooser.getSelectedFile().getParentFile();

    File selFile = openChooser.getSelectedFile();
    InputStream is = new FileInputStream(selFile);

    if (selFile.getName().endsWith(".cer") || selFile.getName().endsWith(".CER")) {
      String alias = getNewAlias(NbBundle.getMessage(getClass(), "MSG_CertificateLC"));//"certificate");
      if (alias == null)
        return;

      CertificateFactory cFact = CertificateFactory.getInstance("X509", "BC");
      X509Certificate cert = (X509Certificate) cFact.generateCertificate(is);

      if (isSelfSigned(cert))      // if certificate is self-signed, make sure it verifies
        cert.verify(cert.getPublicKey());

      ks.setCertificateEntry(alias, cert);
    }
    else {
      String alias = getNewAlias(NbBundle.getMessage(getClass(), "MSG_Secret_lc")); //"secret key");
      if (alias == null)
        return;

      SecretKey sKey = BouncyCastleHelper.readSecretKey(is);
      ks.setEntry(alias, new KeyStore.SecretKeyEntry(sKey), new KeyStore.PasswordProtection(pw));
    }
    commonCleanup(NbBundle.getMessage(getClass(), "MSG_ImportOpComplete")); //"Certificate importation complete");
  }
  catch (Exception ex) {
    String msg = NbBundle.getMessage(getClass(), "MSG_ImportError") + ex.getLocalizedMessage(); //"Import error: "
    NotifyDescriptor d = new NotifyDescriptor.Message(msg, NotifyDescriptor.ERROR_MESSAGE);
    DialogDisplayer.getDefault().notify(d);
  }
}//GEN-LAST:event_importButtActionPerformed

private boolean isSelfSigned(X509Certificate cert)
{
  return cert.getSubjectX500Principal().equals(cert.getIssuerX500Principal());
}

private void createButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createButtActionPerformed
  JPanel opts = new JPanel(new BorderLayout());
  JRadioButton secretRB, privateRB;
  opts.add(secretRB = new JRadioButton(NbBundle.getMessage(getClass(), "MSG_KeyChoiceSecretButton")), BorderLayout.NORTH); //"Secret key (for document encryption)"
  opts.add(privateRB = new JRadioButton(NbBundle.getMessage(getClass(), "MSG_KeyChoicePairButton")), BorderLayout.SOUTH); //"Public/private key pair (for document signing)"
  ButtonGroup bg = new ButtonGroup();
  bg.add(secretRB);
  bg.add(privateRB);
  secretRB.setSelected(false);
  privateRB.setSelected(true);

  int ret = JOptionPane.showConfirmDialog(this,opts,NbBundle.getMessage(getClass(), "MSG_ChooseKeyType"),JOptionPane.OK_CANCEL_OPTION); //"Choose key type"
  if(ret == JOptionPane.CANCEL_OPTION)
    return;

  try {
    if (secretRB.isSelected())
      addSecretKey(pw);
    else
      addPrivateKeyPair(pw);
  }
  catch (Exception ex) {
    String msg = NbBundle.getMessage(getClass(), "MSG_KeyGenError") + ex.getLocalizedMessage(); //"Key generation error: "
    NotifyDescriptor d = new NotifyDescriptor.Message(msg, NotifyDescriptor.ERROR_MESSAGE);
    DialogDisplayer.getDefault().notify(d);
  }
}//GEN-LAST:event_createButtActionPerformed

class ROTableModel extends DefaultTableModel
{
  @Override
  public boolean isCellEditable(int row, int column)
  {
    return false;
  }
}

private void deleteButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtActionPerformed

  int row = keyTable.getSelectedRow();
  if (row == -1)
    return;

  String alias = (String) keyTable.getModel().getValueAt(row, ALIAS_TABLE_COLUMN);
  int ret = JOptionPane.showConfirmDialog(this, 
      NbBundle.getMessage(getClass(), "MSG_ReallyDeleteQuote")+
      alias+"\"?",
      NbBundle.getMessage(getClass(), "MSG_DeleteKey"),
      JOptionPane.OK_CANCEL_OPTION);

  if (ret != JOptionPane.OK_OPTION)
    return;
  
  try {
    this.deleteKey(alias, pw);
  }
  catch(Exception ex) {
    String msg = NbBundle.getMessage(getClass(), "MSG_KeyDeleteError") + ex.getLocalizedMessage(); //"Key deletion error: "
    NotifyDescriptor d = new NotifyDescriptor.Message(msg, NotifyDescriptor.ERROR_MESSAGE);
    DialogDisplayer.getDefault().notify(d);   
  }
}//GEN-LAST:event_deleteButtActionPerformed
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private JButton createButt;
  private JButton deleteButt;
  private JButton exportButt;
  private JButton importButt;
  private JLabel jLabel1;
  private JLabel jLabel2;
  private JScrollPane jScrollPane1;
  private JTable keyTable;
  // End of variables declaration//GEN-END:variables

}
