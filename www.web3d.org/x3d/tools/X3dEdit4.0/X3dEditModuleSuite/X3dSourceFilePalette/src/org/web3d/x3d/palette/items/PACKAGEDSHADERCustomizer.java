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

package org.web3d.x3d.palette.items;

import java.awt.Component;
import java.awt.Dimension;
import java.util.Vector;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.HelpCtx;
import org.web3d.x3d.X3DDataObject;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * PACKAGEDSHADERCustomizer.java
 * Created on 16 January 2010
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class PACKAGEDSHADERCustomizer extends BaseCustomizer implements TableModelListener
{
  private final PACKAGEDSHADER packagedShader;
  private final JTextComponent target;
  private final Vector<FIELD> fieldObjs;
  private final X3DDataObject xObj;

  /** Creates new form PACKAGEDSHADERCustomizer */
  public PACKAGEDSHADERCustomizer(PACKAGEDSHADER packagedShader, JTextComponent target, X3DDataObject xObj)
  {
    super(packagedShader);
    this.fieldObjs = new Vector<>();
    this.packagedShader = packagedShader;
    this.target = target;
    this.xObj = xObj;
    
    HelpCtx.setHelpIDString(this, "PACKAGEDSHADER_ELEM_HELPID");
    
    initComponents();
    
    super.getDEFUSEpanel().setContainerField("shaders");

    languageComboBox.setSelectedItem(packagedShader.getLanguage());
    
    scriptHeader1Lab.setText(PACKAGEDSHADER.ecmaScriptHeader1);
    scriptHeader2Lab.setText(PACKAGEDSHADER.ecmaScriptHeader2);
    scriptTrailer1Lab.setText(PACKAGEDSHADER.ecmaScriptTrailer);
    
   // urlList.setTitle(NbBundle.getMessage(getClass(), "LBL_URLS"));
    urlList.setMasterDocumentLocation(xObj.getPrimaryFile());
    urlList.setUrlData(packagedShader.getUrls());
    urlList.setTarget(target); // enable urlList to reach back into jdom tree to getHeaderIdentifierPath()
    urlList.setMinimumSize(new Dimension(50,50));
    Dimension d = urlList.getPreferredSize();
    urlList.setPreferredSize(new Dimension(d.width,Math.max(50,d.height-70)));
    urlList.setFileChooserScript ();
    urlList.checkUrlValues();
    
    fieldsList.setHeaderTooltip (FIELD_HEADER_TOOLTIP);
    fieldsList.setColumnEditor(new DefaultCellEditor(new JComboBox<>(FIELD_ATTR_TYPE_CHOICES)),1);
    fieldsList.setColumnEditor(new DefaultCellEditor(new JComboBox<>(FIELD_ATTR_ACCESSTYPE_CHOICES)), 2);

    fieldsList.setColumnTitles(new String[]{"name","type","accessType","value","appinfo","documentation"});
    fieldsList.setNewRowData(new Object[]{"","","","","","",});
    
    fieldObjs.clear();
    fieldObjs.addAll(packagedShader.getFields());
    if (fieldObjs.isEmpty()){
      FIELD f = new FIELD("","","","","",""); // something to start with
      f.setParent(PACKAGEDSHADER_ELNAME);
      fieldObjs.add(f);
    }
    String[][] data = new String[fieldObjs.size()][6];
    int r = 0;
    for(FIELD f : fieldObjs) {
      data[r][0] = f.getName();
      data[r][1] = f.getType();
      data[r][2] = f.getAccessType();
      data[r][3] = f.getValue();
      data[r][4] = f.getAppinfo();
      data[r][5] = f.getDocumentation();
      r++;
    }
    // TODO also handle initializing children nodes

    fieldsList.setData(data);
    
    fieldsList.getTable().setDefaultRenderer(String.class, new myDefaultStringRenderer());
    fieldsList.getTable().setDefaultEditor(String.class, new myDefaultStringEditor());
    fieldsList.getTable().getModel().addTableModelListener(this);
    
    ECMA_Ta.setText(packagedShader.getEcmaContent());
    boolean doScript = packagedShader.isShowECMA();
    
    includeECMACB.setSelected(doScript);
    toggleECMAWidgets(doScript);

    setDefaultDEFname ();
  }
  private void setDefaultDEFname ()
  {
	if ((urlList == null) || (urlList.getUrlData() == null) || (urlList.getUrlData().length == 0))
	{
		super.getDEFUSEpanel().setDefaultDEFname("New" + "PackagedShader");
		return;
	}
    // extract file name (minus extension) as candidate DEF name
    String fileName = urlList.getUrlData()[0];
    if (fileName.contains("/"))
        fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
    if (fileName.contains("\\"))
        fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
    if (fileName.contains("."))
        fileName = fileName.substring(0,fileName.lastIndexOf("."));
    if (fileName.length() > 0)
        fileName += "PackagedShader"; // otherwise empty
    
    super.getDEFUSEpanel().setDefaultDEFname(fileName);
   }
   
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        dEFUSEpanel1 = getDEFUSEpanel();
        languageLabel = new javax.swing.JLabel();
        languageComboBox = new javax.swing.JComboBox<String>();
        contentModelLabel = new javax.swing.JLabel();
        urlLabel = new javax.swing.JLabel();
        tablesScriptSplitter = new javax.swing.JSplitPane();
        urlFieldsSplitter = new javax.swing.JSplitPane();
        urlScrollPane = new javax.swing.JScrollPane();
        urlList = new org.web3d.x3d.palette.items.UrlExpandableList2();
        fieldsScrollPane = new javax.swing.JScrollPane();
        fieldsList = new org.web3d.x3d.palette.items.ExpandableList();
        ecmascriptSourcePanel = new javax.swing.JPanel();
        scriptHeader2Lab = new javax.swing.JLabel();
        scriptTrailer1Lab = new javax.swing.JLabel();
        scriptHeader1Lab = new javax.swing.JLabel();
        scriptScrollPane = new javax.swing.JScrollPane();
        ECMA_Ta = new javax.swing.JTextArea();
        includeECMACB = new javax.swing.JCheckBox();
        appendFieldSetMethodsButton = new javax.swing.JButton();

        setPreferredSize(new java.awt.Dimension(783, 621));
        setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.ipadx = 404;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        add(dEFUSEpanel1, gridBagConstraints);

        languageLabel.setText("language");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
        add(languageLabel, gridBagConstraints);

        languageComboBox.setEditable(true);
        languageComboBox.setModel(new DefaultComboBoxModel<String>(SHADER_ATTR_LANGUAGE_CHOICES));
        languageComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                languageComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 55;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(languageComboBox, gridBagConstraints);

        contentModelLabel.setText("PackagedShader describes a single shader program");
        contentModelLabel.setToolTipText("PackagedShader describes a single program having one or more shaders and effects");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        add(contentModelLabel, gridBagConstraints);

        urlLabel.setText("<html><b>url array");
        urlLabel.setToolTipText("ordered list of alternate url addresses for script, if not embedded");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        add(urlLabel, gridBagConstraints);

        tablesScriptSplitter.setBorder(null);
        tablesScriptSplitter.setDividerLocation(320);
        tablesScriptSplitter.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        tablesScriptSplitter.setResizeWeight(0.5);
        tablesScriptSplitter.setLastDividerLocation(320);
        tablesScriptSplitter.setPreferredSize(new java.awt.Dimension(763, 500));

        urlFieldsSplitter.setBorder(null);
        urlFieldsSplitter.setDividerLocation(160);
        urlFieldsSplitter.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        urlFieldsSplitter.setResizeWeight(0.5);
        urlFieldsSplitter.setPreferredSize(new java.awt.Dimension(590, 300));

        urlScrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 5, 1));
        urlScrollPane.setPreferredSize(new java.awt.Dimension(590, 120));

        urlList.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        urlList.setPreferredSize(new java.awt.Dimension(590, 120));
        urlList.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                urlListPropertyChange(evt);
            }
        });
        urlScrollPane.setViewportView(urlList);

        urlFieldsSplitter.setTopComponent(urlScrollPane);

        fieldsScrollPane.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1), "field definitions", javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Dialog", 0, 12))); // NOI18N
        fieldsScrollPane.setToolTipText("author-defined field interfaces for this Script");

        fieldsList.setBorder(null);
        fieldsList.setPreferredSize(new java.awt.Dimension(100, 100));
        fieldsScrollPane.setViewportView(fieldsList);

        urlFieldsSplitter.setBottomComponent(fieldsScrollPane);

        tablesScriptSplitter.setTopComponent(urlFieldsSplitter);

        ecmascriptSourcePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Dialog", 0, 12))); // NOI18N
        ecmascriptSourcePanel.setMaximumSize(new java.awt.Dimension(400, 300));
        ecmascriptSourcePanel.setPreferredSize(new java.awt.Dimension(763, 120));

        scriptHeader2Lab.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        scriptHeader2Lab.setText("ecmascript:");

        scriptTrailer1Lab.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        scriptTrailer1Lab.setText("]]>");

        scriptHeader1Lab.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        scriptHeader1Lab.setText("<![CDATA[");

        scriptScrollPane.setMinimumSize(new java.awt.Dimension(400, 23));
        scriptScrollPane.setPreferredSize(new java.awt.Dimension(600, 100));

        ECMA_Ta.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        ECMA_Ta.setTabSize(2);
        ECMA_Ta.setMinimumSize(new java.awt.Dimension(400, 21));
        ECMA_Ta.setPreferredSize(new java.awt.Dimension(600, 100));
        scriptScrollPane.setViewportView(ECMA_Ta);

        includeECMACB.setText(" embed ECMAscript source code");
        includeECMACB.setToolTipText("enable embedded source editor, rather than using external .js file");
        includeECMACB.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        includeECMACB.setMargin(new java.awt.Insets(2, 0, 2, 2));
        includeECMACB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                includeECMACBActionPerformed(evt);
            }
        });

        appendFieldSetMethodsButton.setText("add field methods to source for input events");
        appendFieldSetMethodsButton.setToolTipText("append undefined field methods for receiving inputOnly and inputOutput events to source code");
        appendFieldSetMethodsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                appendFieldSetMethodsButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ecmascriptSourcePanelLayout = new javax.swing.GroupLayout(ecmascriptSourcePanel);
        ecmascriptSourcePanel.setLayout(ecmascriptSourcePanelLayout);
        ecmascriptSourcePanelLayout.setHorizontalGroup(
            ecmascriptSourcePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ecmascriptSourcePanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ecmascriptSourcePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scriptScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 903, Short.MAX_VALUE)
                    .addGroup(ecmascriptSourcePanelLayout.createSequentialGroup()
                        .addGroup(ecmascriptSourcePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(scriptHeader1Lab)
                            .addComponent(scriptHeader2Lab))
                        .addGap(94, 94, 94)
                        .addComponent(includeECMACB)
                        .addGap(18, 18, 18)
                        .addComponent(appendFieldSetMethodsButton))
                    .addComponent(scriptTrailer1Lab))
                .addContainerGap())
        );
        ecmascriptSourcePanelLayout.setVerticalGroup(
            ecmascriptSourcePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ecmascriptSourcePanelLayout.createSequentialGroup()
                .addGroup(ecmascriptSourcePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ecmascriptSourcePanelLayout.createSequentialGroup()
                        .addGap(7, 7, 7)
                        .addComponent(scriptHeader1Lab)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(scriptHeader2Lab))
                    .addGroup(ecmascriptSourcePanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(ecmascriptSourcePanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(includeECMACB, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(appendFieldSetMethodsButton))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(scriptScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 223, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(scriptTrailer1Lab))
        );

        tablesScriptSplitter.setRightComponent(ecmascriptSourcePanel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 102;
        gridBagConstraints.ipady = 415;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(tablesScriptSplitter, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

  private void includeECMACBActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_includeECMACBActionPerformed
  {//GEN-HEADEREND:event_includeECMACBActionPerformed
    toggleECMAWidgets(includeECMACB.isSelected());
}//GEN-LAST:event_includeECMACBActionPerformed

  private void appendFieldSetMethodsButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_appendFieldSetMethodsButtonActionPerformed
  {//GEN-HEADEREND:event_appendFieldSetMethodsButtonActionPerformed
          String[][] data = fieldsList.getData();
          // create stub methods for inputOnly, inputOutput fields
          boolean found   = false;
          boolean changed = false;
          for (String[] sa : data)
          {
              if (sa[0] != null && sa[0].length() > 0)
              {
                  // TODO use proper tab setting from Netbeans
                  String parameterName;
              //  parameterName= "new" + sa[0].substring(0,1).toUpperCase() + sa[0].substring(1); // camel case
                  parameterName = "eventValue"; // also not adding timestamp to parameter list, usage is too infrequent/obscure
                  if (sa[2].equalsIgnoreCase("inputOnly"))
                  {
                      found = true;
                      if (!ECMA_Ta.getText().contains("function " + sa[0]))
                      {
                          ECMA_Ta.append("\nfunction " + sa[0] + " (" + parameterName + ") // input event received for inputOnly field\n" +
                                  "{\n   // do something with input " + parameterName + ";\n}\n");
                          changed = true;
                      }
                  }
                  else if (sa[2].equalsIgnoreCase("inputOutput"))  // note clarified design pattern from Web3D Symposium, June 2009
                  {
                      found = true;
                      if (!ECMA_Ta.getText().contains("function set_" + sa[0]))
                      {
                          ECMA_Ta.append("\nfunction set_" + sa[0] + " (" + parameterName + ") // input event received for inputOutput field\n" +
                                  "{\n    " + sa[0] + " = " + parameterName + ";\n}\n");
                          changed = true;
                      }
                  }
              }
          }
          if (!found)
          {
              NotifyDescriptor descriptor = new NotifyDescriptor.Message(
              "No inputOnly or inputOutput fields found, so no set_fieldName methods needed", NotifyDescriptor.INFORMATION_MESSAGE);
              DialogDisplayer.getDefault().notify(descriptor);
          }
          else if (found && !changed)
          {
              NotifyDescriptor descriptor = new NotifyDescriptor.Message(
              "<html><p align='center'>All input fields have set_fieldName methods already,</p><p align='center'>so no new set_fieldName methods have been appended</p>", NotifyDescriptor.INFORMATION_MESSAGE);
              DialogDisplayer.getDefault().notify(descriptor);
          }
  }//GEN-LAST:event_appendFieldSetMethodsButtonActionPerformed

  private void languageComboBoxActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_languageComboBoxActionPerformed
  {//GEN-HEADEREND:event_languageComboBoxActionPerformed
      // TODO add your handling code here:
}//GEN-LAST:event_languageComboBoxActionPerformed

    private void urlListPropertyChange(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_urlListPropertyChange
    {//GEN-HEADEREND:event_urlListPropertyChange
        setDefaultDEFname ();
    }//GEN-LAST:event_urlListPropertyChange
  
  private void toggleECMAWidgets(boolean enable)
  {
    ECMA_Ta.setEnabled(enable);
    scriptHeader1Lab.setEnabled(enable);
    scriptHeader2Lab.setEnabled(enable);
    scriptTrailer1Lab.setEnabled(enable);
    appendFieldSetMethodsButton.setEnabled(enable);
  }
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea ECMA_Ta;
    private javax.swing.JButton appendFieldSetMethodsButton;
    private javax.swing.JLabel contentModelLabel;
    private org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel1;
    private javax.swing.JPanel ecmascriptSourcePanel;
    private org.web3d.x3d.palette.items.ExpandableList fieldsList;
    private javax.swing.JScrollPane fieldsScrollPane;
    private javax.swing.JCheckBox includeECMACB;
    private javax.swing.JComboBox<String> languageComboBox;
    private javax.swing.JLabel languageLabel;
    private javax.swing.JLabel scriptHeader1Lab;
    private javax.swing.JLabel scriptHeader2Lab;
    private javax.swing.JScrollPane scriptScrollPane;
    private javax.swing.JLabel scriptTrailer1Lab;
    private javax.swing.JSplitPane tablesScriptSplitter;
    private javax.swing.JSplitPane urlFieldsSplitter;
    private javax.swing.JLabel urlLabel;
    private org.web3d.x3d.palette.items.UrlExpandableList2 urlList;
    private javax.swing.JScrollPane urlScrollPane;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_PACKAGEDSHADER";
  }

  @Override
  public void unloadInput()
  {
    unLoadDEFUSE();
    urlList.checkUrlValues();

    packagedShader.setLanguage((String)languageComboBox.getSelectedItem());
    
    packagedShader.setUrls(urlList.getUrlData());
    
    if(includeECMACB.isSelected())
      packagedShader.setEcmaContent(ECMA_Ta.getText());
    else
      packagedShader.setEcmaContent(null);
    
    String[][] data = fieldsList.getData();
    if(data.length>0) {
      Vector<FIELD> v = new Vector<>(data.length);
      int r=0;
      for(String[] sa : data) {
        if(sa[0] != null && sa[0].length()>0) {
          FIELD f = fieldObjs.elementAt(r); //new FIELD();
          f.setName(sa[0]);
          f.setType(sa[1]);
          f.setAccessType(sa[2]);
          f.setValue(sa[3]);
          f.setAppinfo(sa[4]);
          f.setDocumentation(sa[5]);
          v.add(f);
          r++;
        }
        // TODO also handle initializing children nodes
      }
     packagedShader.setFields(v);
    }
  }
  
  @Override
  public void tableChanged(TableModelEvent e)
  {
    if(e.getType() == TableModelEvent.UPDATE){
      int col = e.getColumn();
      int row = e.getFirstRow();
      
      if(col == 2) { // access type 
        //String access = (String)getTable().getValueAt(row, col);
        //if(FIELD.canHaveValue(access))  fire it always
        ((DefaultTableModel)e.getSource()).fireTableCellUpdated(row, 3); // value
      }
    }
    else if(e.getType() == TableModelEvent.DELETE) {
      int row = e.getFirstRow();
      int lrow = e.getLastRow();
      for(int r = row; r <= lrow; r++)
      {
          fieldObjs.remove(r);
      }
    }
    else if(e.getType() == TableModelEvent.INSERT) {
      int row = e.getFirstRow();
      int lrow = e.getLastRow();
      for(int r = row; r <= lrow; r++) {
        FIELD f = new FIELD();
        f.setParent(PACKAGEDSHADER_ELNAME);
        fieldObjs.add(r, f);
      }
    }
  }

  class myDefaultStringRenderer extends DefaultTableCellRenderer
  {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
      Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      boolean enabled = true;
      if(column == 3) {
        enabled = FIELD.canHaveValue((String)table.getValueAt(row, 1), (String)table.getValueAt(row, 2));
      }
      c.setEnabled(enabled);
      return c;
    }   
  }
  
  class myDefaultStringEditor extends DefaultCellEditor
  {
    public myDefaultStringEditor()
    {
      super(new JTextField());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
    {
      Component c = super.getTableCellEditorComponent(table, value, isSelected, row, column);
      c.setEnabled(true);
      if(column == 3) {
        if(!FIELD.canHaveValue((String)table.getValueAt(row, 1), (String)table.getValueAt(row, 2))) {
         c.setEnabled(false);
        }
      }
      return c;
    }   
  }
}
