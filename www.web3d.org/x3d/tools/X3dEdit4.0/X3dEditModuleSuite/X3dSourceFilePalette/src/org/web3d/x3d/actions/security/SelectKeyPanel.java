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
 * SelectKeyPanel.java
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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStore.Entry;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import org.openide.util.NbBundle;
import org.web3d.x3d.actions.security.BouncyCastleHelper.KeystorePasswordException;
import org.web3d.x3d.actions.security.ManageKeyStoreAction.OperationCancelledException;
import org.web3d.x3d.options.X3dOptions;

/**
 *
 * @author  mike
 */
public class SelectKeyPanel extends javax.swing.JPanel
{
  private final int ALIAS_TABLE_COLUMN = 0;
  private final int TYPE_TABLE_COLUMN = 1;
  private final int DATE_TABLE_COLUMN = 2;
  private final String ALIAS_TITLE = NbBundle.getMessage(getClass(), "MSG_KeyTableAliasColumnTitle"); //"Alias";
  private final String TYPE_TITLE  = NbBundle.getMessage(getClass(), "MSG_KeyTableTypeColumnTitle"); //"Type";
  private final String DATE_TITLE  = NbBundle.getMessage(getClass(), "MSG_KeyTableDateColumnTitle"); //"Date added";
  private Vector<String> titles;
  private KeyStore ks;
  private File ksFile;
  private FileInputStream ksInpStr;
  private char[] pw;
  
  public static int ENCRYPTING_KEY_TYPE = 0;
  public static int SIGNING_KEY_TYPE = 1;
  private Integer beSelective = null;

  /** Creates new form ManageKeyStorePanel */
  public SelectKeyPanel() throws Exception
  {
    this(null);
  }
  
  public SelectKeyPanel(Integer keyType) throws Exception
  {
    beSelective = keyType;
    initComponents();

    titles = new Vector<String>(3);
    titles.add(ALIAS_TITLE);
    titles.add(TYPE_TITLE);
    titles.add(DATE_TITLE);

    initKeyStore();  // also gets called from reloadTable()
    initKeyTable();
    reloadTable();

    customizeColumnWidths();
  }

  public Entry getSelectedEntry() throws Exception
  {
    int row = keyTable.getSelectedRow();
    if (row == -1)
      return null;
    String alias = (String) keyTable.getModel().getValueAt(row, ALIAS_TABLE_COLUMN);
    if (ks.isCertificateEntry(alias))
      return ks.getEntry(alias, null);
    //else
    return ks.getEntry(alias, new KeyStore.PasswordProtection(pw));
  }

  private char[] getAPassword(String msg)
  {
    return BouncyCastleHelper.getAPassword(msg);
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
      throw new KeystorePasswordException(NbBundle.getMessage(getClass(), "MSG_BadPassword")); //"Password incorrect or file unreadable");
    }
    
    Vector<Vector<String>> rows = new Vector<Vector<String>>();
    String initialSelection = null;
    if (ks != null) {
      Enumeration<String> aliases = ks.aliases();
      if (aliases.hasMoreElements()) {
        while (aliases.hasMoreElements()) {
          String s = aliases.nextElement();
          Entry ent;
          if (ks.isCertificateEntry(s))
            ent = ks.getEntry(s, null);
          else
            ent = ks.getEntry(s, new KeyStore.PasswordProtection(pw));
          Vector<String> vs = new Vector<String>(3);
          vs.add(s);  // first
          if (ent instanceof KeyStore.SecretKeyEntry) {
            vs.add(NbBundle.getMessage(getClass(), "MSG_Secret")); //"Secret key");
            if(initialSelection == null && beSelective.equals(SelectKeyPanel.ENCRYPTING_KEY_TYPE))
              initialSelection = s;
          }
          else if (ent instanceof KeyStore.PrivateKeyEntry) {
            vs.add(NbBundle.getMessage(getClass(), "MSG_PubPrivPair")); //"Public/private key pair");
            if(initialSelection == null && beSelective.equals(SelectKeyPanel.SIGNING_KEY_TYPE))
              initialSelection = s;
            
          }
          else if (ent instanceof KeyStore.TrustedCertificateEntry) {
            vs.add(NbBundle.getMessage(getClass(), "MSG_TrustedPublic")); //"Trusted public key");
            if(initialSelection == null && beSelective.equals(SelectKeyPanel.SIGNING_KEY_TYPE))
              initialSelection = s;
          }
          vs.add(ks.getCreationDate(s).toString()); // third
          rows.add(vs);
        }
      }
    }
    Collections.sort(rows, dateSorter);
    ((DefaultTableModel) keyTable.getModel()).setDataVector(rows, titles);
    
    if(rows.size()>0) {
      if(initialSelection == null)
        keyTable.setRowSelectionInterval(0,0);
      else {
        for(int r = 0; r < keyTable.getRowCount();r++) {
          if(keyTable.getValueAt(r, 0).equals(initialSelection)) {
            keyTable.setRowSelectionInterval(r, r);
            break;
          }
        }
      }
    }
 }
  
  private ByDateSorter dateSorter = new ByDateSorter();

  private class ByDateSorter implements Comparator<Vector<String>>
  {
    public int compare(Vector<String> v1, Vector<String> v2)
    {
      return v1.elementAt(DATE_TABLE_COLUMN).compareTo(
             v2.elementAt(DATE_TABLE_COLUMN));
    }
  }

  private void initKeyStore() throws Exception
  {
    ks = BouncyCastleHelper.getKeyStore();//KeyStore.getInstance("JKS");
    String path = X3dOptions.getKeystorePath();
    ksFile = new File(path);
    if (ksFile.exists())
      ksInpStr = new FileInputStream(ksFile);
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

  class ROTableModel extends DefaultTableModel
  {
    @Override
    public boolean isCellEditable(int row, int column)
    {
      return false;
    }
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jScrollPane1 = new javax.swing.JScrollPane();
    keyTable = new javax.swing.JTable();
    manageKeystoreButt = new javax.swing.JButton();

    setBorder(javax.swing.BorderFactory.createEtchedBorder());

    keyTable.setModel(new ROTableModel());
    keyTable.setToolTipText(org.openide.util.NbBundle.getMessage(getClass(), "TT_KeyTable")); // NOI18N
    jScrollPane1.setViewportView(keyTable);

    manageKeystoreButt.setText(org.openide.util.NbBundle.getMessage(SelectKeyPanel.class, "SelectKeyPanel.manageKeystoreButt.text")); // NOI18N
    manageKeystoreButt.setToolTipText(NbBundle.getMessage(getClass(), "TT_ManageKeystore")); // NOI18N
    manageKeystoreButt.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        manageKeystoreButtActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
          .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 741, Short.MAX_VALUE)
          .addComponent(manageKeystoreButt))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 114, Short.MAX_VALUE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(manageKeystoreButt)
        .addContainerGap())
    );
  }// </editor-fold>//GEN-END:initComponents

private void manageKeystoreButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_manageKeystoreButtActionPerformed
  ManageKeyStoreAction.instance.performAction(pw);
  try {
    saveColumnWidths();
    reloadTable();
    customizeColumnWidths();
  }
  catch (Exception ex) {
    System.err.println(NbBundle.getMessage(getClass(), "MSG_CantReloadKeystore")); //"Can't reload keystore table");
  }
}//GEN-LAST:event_manageKeystoreButtActionPerformed
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JTable keyTable;
  private javax.swing.JButton manageKeystoreButt;
  // End of variables declaration//GEN-END:variables
}
