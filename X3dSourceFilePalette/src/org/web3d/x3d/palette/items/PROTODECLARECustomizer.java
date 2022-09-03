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

import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import java.net.URL;
import org.openide.awt.HtmlBrowser.URLDisplayer;
import java.awt.Component;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;
import org.openide.util.HelpCtx;
import static org.web3d.x3d.palette.X3DPaletteUtilities.escapeXmlCharacters;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * PROTODECLARECustomizer.java
 * Created on Mar 7, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman, Loren Peitso
 * @version $Id$
 */
public class PROTODECLARECustomizer extends BaseCustomizer implements TableModelListener
{
  private final PROTODECLARE protoDeclare;
  private final JTextComponent target;

  private boolean  insertTraceParameter = false;
  private boolean  hasInitialFields = false;
  private String   tabSetting = "      "; // TODO use proper tab setting from Netbeans
  
  private final int          NAME_COLUMN = 0;
  private final int          TYPE_COLUMN = 1;
  private final int    ACCESSTYPE_COLUMN = 2;
  private final int         VALUE_COLUMN = 3;
  private final int       APPINFO_COLUMN = 4;
  private final int DOCUMENTATION_COLUMN = 5;

  /** Creates new form PROTODECLARECustomizer */
  public PROTODECLARECustomizer(PROTODECLARE protoDeclare, JTextComponent target)
  {
    super(protoDeclare);
    this.protoDeclare = protoDeclare;
    this.target = target;
    
    HelpCtx.setHelpIDString(this, "PROTODECLARE_ELEM_HELPID");
    
    initComponents();
    
    nameTF.setText(protoDeclare.getName());
        
    fieldsList.setHeaderTooltip (FIELD_HEADER_TOOLTIP);
    fieldsList.setColumnEditor(new DefaultCellEditor(new JComboBox<>(FIELD_ATTR_TYPE_CHOICES)),             TYPE_COLUMN);
    fieldsList.setColumnEditor(new DefaultCellEditor(new JComboBox<>(FIELD_ATTR_ACCESSTYPE_CHOICES)), ACCESSTYPE_COLUMN);

    fieldsList.setColumnTitles  (FIELD_ATTR_NAMES);
    fieldsList.setColumnToolTips(FIELD_ATTR_TOOLTIPS);
    fieldsList.setNewRowData(new Object[]{"","","","","",""}); // empty values are best, let author enter text or use selections
    fieldsList.setCellEditPanelVisible(false); // might want to modify values, TODO handle only that column

    List<FIELD>fields = protoDeclare.getProtoInterface().getFields();
    if(fields == null)
       fields = new Vector<>(1);
    
    if(fields.isEmpty())
    {
      hasInitialFields = false; // stay that way
      FIELD f = new FIELD("","","","","",""); // something to start with
      f.setParent(PROTODECLARE_ELNAME);
      fields.add(f);
    }
    else if (fields.size() > 0)
    {
      hasInitialFields = true;
      String[][] data = new String[fields.size()][6];
      int r = 0;
      for(FIELD f : fields) {
        data[r][NAME_COLUMN]          = f.getName();
        data[r][TYPE_COLUMN]          = f.getType();
        data[r][ACCESSTYPE_COLUMN]    = f.getAccessType();
        data[r][VALUE_COLUMN]         = f.getValue();
        data[r][APPINFO_COLUMN]       = f.getAppinfo();
        data[r][DOCUMENTATION_COLUMN] = f.getDocumentation();
        r++;
      }
      fieldsList.setData(data);
    }
    
    fieldsList.getTable().setDefaultRenderer(String.class, new myDefaultStringRenderer());
    fieldsList.getTable().setDefaultEditor(String.class, new myDefaultStringEditor());
    fieldsList.getTable().getModel().addTableModelListener(this);

    // now that fieldsList column titles and interface are initialized, restore to empty if appropriate
    if (hasInitialFields == false)
    {
        fields.clear();
        String[][] data = new String[0][0];
        fieldsList.setData(data);
    }
    appInfoTextArea.setText(protoDeclare.getAppinfo());
    documentationTF.setText(protoDeclare.getDocumentation());
    openDocumentationButton.setEnabled(!documentationTF.getText().trim().isEmpty());
   }
 
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        nameLabel = new javax.swing.JLabel();
        nameTF = new javax.swing.JTextField();
        appinfoLabel = new javax.swing.JLabel();
        appInfoScrollPane = new javax.swing.JScrollPane();
        appInfoTextArea = new javax.swing.JTextArea();
        documentationLabel = new javax.swing.JLabel();
        documentationTF = new javax.swing.JTextField();
        openDocumentationButton = new javax.swing.JButton();
        fieldDefinitionsLabel = new javax.swing.JLabel();
        fieldListScrollPane = new javax.swing.JScrollPane();
        fieldsList = new org.web3d.x3d.palette.items.ExpandableList();
        authorAssistLabel = new javax.swing.JLabel();
        appendProtoInstanceCheckBox = new javax.swing.JCheckBox();
        appendExternProtoDeclareCheckBox = new javax.swing.JCheckBox();
        copyDefaultsAsAppinfoCheckBox = new javax.swing.JCheckBox();
        insertScriptCheckBox = new javax.swing.JCheckBox();
        insertTraceParameterCodeCheckBox = new javax.swing.JCheckBox();
        spacerLabel = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(800, 400));
        setLayout(new java.awt.GridBagLayout());

        nameLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        nameLabel.setText("Prototype name");
        nameLabel.setToolTipText("Prototype names must be unique for ProtoDeclare and ExternProtoDeclare");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 28, 5, 5);
        add(nameLabel, gridBagConstraints);

        nameTF.setToolTipText("Prototype names must be unique for ProtoDeclare and ExternProtoDeclare");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 23);
        add(nameTF, gridBagConstraints);

        appinfoLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        appinfoLabel.setText("appinfo");
        appinfoLabel.setToolTipText("application information provides simple tooltip description");
        appinfoLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 28, 5, 5);
        add(appinfoLabel, gridBagConstraints);

        appInfoScrollPane.setAutoscrolls(true);
        appInfoScrollPane.setMinimumSize(new java.awt.Dimension(23, 30));
        appInfoScrollPane.setPreferredSize(new java.awt.Dimension(166, 30));

        appInfoTextArea.setColumns(20);
        appInfoTextArea.setLineWrap(true);
        appInfoTextArea.setRows(2);
        appInfoTextArea.setToolTipText("application information provides simple tooltip description");
        appInfoTextArea.setWrapStyleWord(true);
        appInfoTextArea.setMinimumSize(new java.awt.Dimension(4, 30));
        appInfoScrollPane.setViewportView(appInfoTextArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 23);
        add(appInfoScrollPane, gridBagConstraints);

        documentationLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        documentationLabel.setText("documentation");
        documentationLabel.setToolTipText("url for prototype documentation");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 28, 5, 5);
        add(documentationLabel, gridBagConstraints);

        documentationTF.setToolTipText("url for prototype documentation");
        documentationTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                documentationTFActionPerformed(evt);
            }
        });
        documentationTF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                documentationTFPropertyChange(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 2);
        add(documentationTF, gridBagConstraints);

        openDocumentationButton.setText("open");
        openDocumentationButton.setToolTipText("open documentation url in browser");
        openDocumentationButton.setEnabled(false);
        openDocumentationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openDocumentationButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 22);
        add(openDocumentationButton, gridBagConstraints);

        fieldDefinitionsLabel.setFont(new java.awt.Font("Tahoma", 1, 13));
        fieldDefinitionsLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        fieldDefinitionsLabel.setText("ProtoInterface field definitions");
        fieldDefinitionsLabel.setToolTipText("expandable list holding ProtoInterface field definitions");
        fieldDefinitionsLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(5, 11, 0, 5);
        add(fieldDefinitionsLabel, gridBagConstraints);

        fieldsList.setBorder(null);
        fieldsList.setPreferredSize(new java.awt.Dimension(100, 100));
        fieldListScrollPane.setViewportView(fieldsList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 80;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(fieldListScrollPane, gridBagConstraints);

        authorAssistLabel.setFont(new java.awt.Font("Tahoma", 1, 13));
        authorAssistLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        authorAssistLabel.setText("Author-assist editing features");
        authorAssistLabel.setToolTipText("Check boxes to append helpful additional content with this ProtoDeclare");
        authorAssistLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(6, 11, 0, 5);
        add(authorAssistLabel, gridBagConstraints);

        appendProtoInstanceCheckBox.setText("Append new example ProtoInstance");
        appendProtoInstanceCheckBox.setToolTipText("Append new example ProtoInstance matching this ProtoDeclare");
        appendProtoInstanceCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                appendProtoInstanceCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 3, 5);
        add(appendProtoInstanceCheckBox, gridBagConstraints);

        appendExternProtoDeclareCheckBox.setText("Append matching ExternProtoDeclare");
        appendExternProtoDeclareCheckBox.setToolTipText("Append matching new ExternProtoDeclare to cut/move into another .x3d scene");
        appendExternProtoDeclareCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                appendExternProtoDeclareCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 3, 5);
        add(appendExternProtoDeclareCheckBox, gridBagConstraints);

        copyDefaultsAsAppinfoCheckBox.setSelected(true);
        copyDefaultsAsAppinfoCheckBox.setText("Copy default field values to provide initial appinfo descriptions");
        copyDefaultsAsAppinfoCheckBox.setToolTipText("When appinfo is not already defined, copy default values as descriptions, making field declarations clearer");
        copyDefaultsAsAppinfoCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                copyDefaultsAsAppinfoCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 3, 5);
        add(copyDefaultsAsAppinfoCheckBox, gridBagConstraints);

        insertScriptCheckBox.setText("Insert new Script node with IS/connect links matching ProtoInterface fields");
        insertScriptCheckBox.setToolTipText("Script node with matching IS/connect links is inserted within ProtoBody (a helpful design pattern)");
        insertScriptCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        insertScriptCheckBox.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        insertScriptCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertScriptCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 5, 3, 5);
        add(insertScriptCheckBox, gridBagConstraints);

        insertTraceParameterCodeCheckBox.setSelected(true);
        insertTraceParameterCodeCheckBox.setText("also include traceOutput parameter in Script code for debug tracing");
        insertTraceParameterCodeCheckBox.setToolTipText("Changes in field variables are selectably written to system output console (a helpful design pattern for debugging)");
        insertTraceParameterCodeCheckBox.setEnabled(false);
        insertTraceParameterCodeCheckBox.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        insertTraceParameterCodeCheckBox.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        insertTraceParameterCodeCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertTraceParameterCodeCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 30, 0, 5);
        add(insertTraceParameterCodeCheckBox, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        add(spacerLabel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void insertScriptCheckBoxActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_insertScriptCheckBoxActionPerformed
    {//GEN-HEADEREND:event_insertScriptCheckBoxActionPerformed
        insertTraceParameterCodeCheckBox.setEnabled(insertScriptCheckBox.isSelected());
        if  (insertScriptCheckBox.isSelected() == false)
             insertTraceParameter = false;
        else insertTraceParameter = insertTraceParameterCodeCheckBox.isSelected();
}//GEN-LAST:event_insertScriptCheckBoxActionPerformed

    private void copyDefaultsAsAppinfoCheckBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_copyDefaultsAsAppinfoCheckBoxActionPerformed
    {//GEN-HEADEREND:event_copyDefaultsAsAppinfoCheckBoxActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_copyDefaultsAsAppinfoCheckBoxActionPerformed

    private void appendExternProtoDeclareCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_appendExternProtoDeclareCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_appendExternProtoDeclareCheckBoxActionPerformed

    private void insertTraceParameterCodeCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertTraceParameterCodeCheckBoxActionPerformed
        if  (insertScriptCheckBox.isSelected() == false)
             insertTraceParameter = false;
        else insertTraceParameter = insertTraceParameterCodeCheckBox.isSelected();
    }//GEN-LAST:event_insertTraceParameterCodeCheckBoxActionPerformed

    private void documentationTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_documentationTFActionPerformed
        openDocumentationButton.setEnabled(!documentationTF.getText().trim().isEmpty());
    }//GEN-LAST:event_documentationTFActionPerformed

    private void openDocumentationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openDocumentationButtonActionPerformed

        if (documentationTF.getText().trim().isEmpty()) {             return;         }         URL url = null;         try {             url = new URL(documentationTF.getText());             URLDisplayer.getDefault().showURL(url);         } catch (Exception e) {             System.out.println("Failed attempt to launch " + url.toString());         }     }//GEN-LAST:event_openDocumentationButtonActionPerformed

    private void documentationTFPropertyChange(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_documentationTFPropertyChange
    {//GEN-HEADEREND:event_documentationTFPropertyChange
        openDocumentationButton.setEnabled(!documentationTF.getText().trim().isEmpty());
    }//GEN-LAST:event_documentationTFPropertyChange

    private void appendProtoInstanceCheckBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_appendProtoInstanceCheckBoxActionPerformed
    {//GEN-HEADEREND:event_appendProtoInstanceCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_appendProtoInstanceCheckBoxActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane appInfoScrollPane;
    private javax.swing.JTextArea appInfoTextArea;
    private javax.swing.JCheckBox appendExternProtoDeclareCheckBox;
    private javax.swing.JCheckBox appendProtoInstanceCheckBox;
    private javax.swing.JLabel appinfoLabel;
    private javax.swing.JLabel authorAssistLabel;
    private javax.swing.JCheckBox copyDefaultsAsAppinfoCheckBox;
    private javax.swing.JLabel documentationLabel;
    private javax.swing.JTextField documentationTF;
    private javax.swing.JLabel fieldDefinitionsLabel;
    private javax.swing.JScrollPane fieldListScrollPane;
    private org.web3d.x3d.palette.items.ExpandableList fieldsList;
    private javax.swing.JCheckBox insertScriptCheckBox;
    private javax.swing.JCheckBox insertTraceParameterCodeCheckBox;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTF;
    private javax.swing.JButton openDocumentationButton;
    private javax.swing.JLabel spacerLabel;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_PROTODECLARE";
  }

  @Override
  public void unloadInput()
  {
    boolean traceEnabledFieldAlreadyDefined = false;

    protoDeclare.setName(nameTF.getText().trim());
    
    String[][] data = fieldsList.getData();
    if(data.length>0) {
      Vector<FIELD> v = new Vector<>(data.length);
      for(String[] sa : data) {
        if(sa[NAME_COLUMN] != null && sa[NAME_COLUMN].length()>0) {
          FIELD field = new FIELD();
          field.setName(sa[NAME_COLUMN]);
          field.setType(sa[TYPE_COLUMN]);
          field.setAccessType(sa[ACCESSTYPE_COLUMN]);
          
          if (sa[TYPE_COLUMN].equals("MFString") && (sa[VALUE_COLUMN].trim().length() > 0) && !sa[VALUE_COLUMN].contains("\""))
          {
               field.setValue("\"" + escapeXmlCharacters(sa[VALUE_COLUMN]) + "\"");
               NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                 "<html><p align='center'>field <b>" + field.getName() +
                 "</b> of type MFString must have quoted value</p>" +
                 "<br /> <p align='center'>Quotes added to create a proper single-string value</p>", NotifyDescriptor.WARNING_MESSAGE);
               DialogDisplayer.getDefault().notify(descriptor);
          }
          else field.setValue(escapeXmlCharacters(sa[VALUE_COLUMN]));

          if (copyDefaultsAsAppinfoCheckBox.isSelected() && sa[APPINFO_COLUMN].trim().length() == 0) // do not override appinfo if already defined
          {
              if ((sa[VALUE_COLUMN].trim().length() > 0) && (!sa[TYPE_COLUMN].equals("SFNode") && !sa[TYPE_COLUMN].equals("MFNode")) && (sa[ACCESSTYPE_COLUMN].equals("initializeOnly") || sa[ACCESSTYPE_COLUMN].equals("inputOutput")))
              {
                  field.setAppinfo ("default value " + escapeXmlCharacters(sa[VALUE_COLUMN].trim()));  // override empty value of appinfo
              }
          }
          else field.setAppinfo(escapeXmlCharacters(sa[APPINFO_COLUMN].trim()));  // keep prior existing value, do not override
          
          field.setDocumentation(escapeXmlCharacters(sa[DOCUMENTATION_COLUMN].trim())); // keep prior existing value, do not override
          
          field.setParent(PROTODECLARE_ELNAME);
          v.add(field);
          
          if (sa[NAME_COLUMN].equals("traceEnabled")) traceEnabledFieldAlreadyDefined = true;
        }
      }
      if (insertTraceParameter  && !traceEnabledFieldAlreadyDefined)
      {
          FIELD f = new FIELD();
          f.setName("traceEnabled");
          f.setType("SFBool");
          f.setAccessType("inputOutput");
          f.setValue("true");
          f.setAppinfo("debug trace to Browser output console");
          f.setDocumentation(""); // TODO
          f.setParent(PROTODECLARE_ELNAME);
          v.add(f);
      }
      // TODO also handle initializing children nodes

      protoDeclare.getProtoInterface().setFields(v);
    }
    protoDeclare.setAppinfo(escapeXmlCharacters(appInfoTextArea.getText().trim()));
    protoDeclare.setDocumentation(escapeXmlCharacters(documentationTF.getText().trim()));
    // all data now saved to protoDeclare

      // =============================================
      // special feature, triggered from GUI checkbox:
      // append custom ExternProtoDeclare with matching field definitions, for use in other files
      if (appendExternProtoDeclareCheckBox.isSelected())
      {
          StringBuilder externProtoDeclare = new StringBuilder();
          externProtoDeclare.append("\n");
          externProtoDeclare.append(tabSetting);
          externProtoDeclare.append("<!-- Remove the following new matching ExternProtoDeclare from this scene, move to separate .x3d scene that needs to use the prototype -->\n");
          externProtoDeclare.append(tabSetting);
          externProtoDeclare.append("<ExternProtoDeclare name='").append(protoDeclare.getName()).append("'");
          if (protoDeclare.getAppinfo().length() > 0)
              externProtoDeclare.append(" appinfo='").append(escapeXmlCharacters(protoDeclare.getAppinfo())).append("'");
          // TODO get name of this file, also get http address if it appears in meta tags
          externProtoDeclare.append(" url='\"Filename.x3d#").append(protoDeclare.getName()).append("\" \"http://some.address.org/Filename.x3d#").append(protoDeclare.getName()).append("\"'");
          externProtoDeclare.append(">\n");

          for (String[] sa : data)
          {
              if (sa[NAME_COLUMN] != null && sa[NAME_COLUMN].length() > 0)
              {
                  externProtoDeclare.append(tabSetting).append(tabSetting);
                  externProtoDeclare.append("<field ").append(
                          "accessType='").append(sa[ACCESSTYPE_COLUMN]).append("' ").append(
                                "name='").append(sa[NAME_COLUMN]).append("' ").append(
                                "type='").append(sa[TYPE_COLUMN]).append("'");

                  if (copyDefaultsAsAppinfoCheckBox.isSelected() && sa[APPINFO_COLUMN].length() == 0) // do not override appinfo if already defined
                  {
                      if ((!sa[TYPE_COLUMN].equals("SFNode") && !sa[TYPE_COLUMN].equals("MFNode")) && (sa[ACCESSTYPE_COLUMN].equals("initializeOnly") || sa[ACCESSTYPE_COLUMN].equals("inputOutput")))
                      {
                          externProtoDeclare.append (" appinfo='default value ").append(escapeXmlCharacters(sa[VALUE_COLUMN])).append("'");  // override empty value of appinfo
                      }
                  }
                  else if ( sa[APPINFO_COLUMN].trim().length() > 0) // do not provide appinfo if not defined
                      externProtoDeclare.append(" appinfo='").append(escapeXmlCharacters(sa[APPINFO_COLUMN].trim())).append("'");       // keep prior existing value, do not override
          
                  if (sa[DOCUMENTATION_COLUMN].trim().length() > 0) // do not provide documentation if not defined
                       externProtoDeclare.append(" documentation='").append(escapeXmlCharacters(sa[DOCUMENTATION_COLUMN].trim())).append("'"); // keep prior existing value, do not override

                  // do not include value or contained elements:  they are illegal in ExternProtoDeclare field declarations
                  externProtoDeclare.append("/>\n");
              }
          }
          if (insertTraceParameter  && !traceEnabledFieldAlreadyDefined)
          {
                  externProtoDeclare.append(tabSetting).append(tabSetting);
                  externProtoDeclare.append("<field accessType='inputOutput' name='traceEnabled' type='SFBool' appinfo='debug trace to Browser output console'");
                  externProtoDeclare.append("/>\n");
          }
          externProtoDeclare.append(tabSetting);
          externProtoDeclare.append("</ExternProtoDeclare>\n");
          if  (protoDeclare.getAppendedContent().trim().length() > 0)
               protoDeclare.setAppendedContent(protoDeclare.getAppendedContent() + "\n" + externProtoDeclare.toString()); // follows ProtoDeclare
          else protoDeclare.setAppendedContent(                                    "\n" + externProtoDeclare.toString()); // follows ProtoDeclare
      }

      // =============================================
      // special feature, triggered from GUI checkbox:
      // append custom ProtoInstance with matching fieldValue declarations
      if (appendProtoInstanceCheckBox.isSelected())
      {
          StringBuilder protoInstance = new StringBuilder();
          protoInstance.append("\n");
          protoInstance.append(tabSetting);
          protoInstance.append("<ProtoInstance name='").append(protoDeclare.getName()).append("'");
          protoInstance.append(">\n");

          for (String[] sa : data)
          {
              if (sa[NAME_COLUMN] != null && sa[NAME_COLUMN].trim().length() > 0 && (!sa[TYPE_COLUMN].equals("SFNode") && !sa[TYPE_COLUMN].equals("MFNode")) && (sa[ACCESSTYPE_COLUMN].equals("initializeOnly") || sa[ACCESSTYPE_COLUMN].equals("inputOutput")))
              {
                  protoInstance.append(tabSetting).append(tabSetting);
                  protoInstance.append("<fieldValue ").append(
                                "name='").append(sa[NAME_COLUMN]).append("' "   ).append(
                               "value='").append(escapeXmlCharacters(sa[VALUE_COLUMN])).append("'");

                  // do not include appinfo or type:  they are illegal in ProtoInstance fieldValue declarations
                  protoInstance.append("/>\n");
              }
          }
          if (insertTraceParameter  && !traceEnabledFieldAlreadyDefined)
          {
                  protoInstance.append(tabSetting).append(tabSetting);
                  protoInstance.append("<fieldValue name='traceEnabled' value='true'");
                  protoInstance.append("/>\n");
          }
          protoInstance.append(tabSetting);
          protoInstance.append("</ProtoInstance>\n");
          protoInstance.append(tabSetting);
          protoInstance.append("<!-- Add any ROUTEs here that connect ProtoInstance to/from prior nodes in Scene (and outside of ProtoDeclare) -->\n");
          if  (protoDeclare.getAppendedContent().trim().length() > 0)
               protoDeclare.setAppendedContent(protoDeclare.getAppendedContent() + protoInstance.toString()); // follows ProtoDeclare
          else protoDeclare.setAppendedContent(                                    protoInstance.toString()); // follows ProtoDeclare
      }

      // =============================================
      // special feature, triggered from GUI checkbox:
      // append custom Script with IS/connect links inside ProtoBody that matches ProtoInterface fields
      if (insertScriptCheckBox.isSelected() && (data.length > 0))
      {
          StringBuilder ScriptWithLinks = new StringBuilder();
          ScriptWithLinks.append(tabSetting);
          ScriptWithLinks.append("<!-- This embedded Script provides the X3D author with additional visibility and control");
          ScriptWithLinks.append(" over prototype inputs and outputs -->\n");
          ScriptWithLinks.append(tabSetting);
          ScriptWithLinks.append("<Script DEF='").append(protoDeclare.getName()).append("Script").append("'");
          
          boolean needsDirectOutput = false;
          boolean hasNameField      = false;
          for (String[] sa : data)
          {
              if ((sa[TYPE_COLUMN].equals("SFNode") || sa[TYPE_COLUMN].equals("MFNode")) && (sa[ACCESSTYPE_COLUMN].equals("initializeOnly") || sa[ACCESSTYPE_COLUMN].equals("inputOutput")))
              {
                  needsDirectOutput = true;
              }
              if (sa[NAME_COLUMN].equals("name"))
              {
                  hasNameField = true;
              }
          }
          if (needsDirectOutput)
          {
              ScriptWithLinks.append(" directOutput='").append(needsDirectOutput).append("'");
          }
          ScriptWithLinks.append(">\n");

          for (String[] sa : data)
          {
              if (sa[NAME_COLUMN] != null && sa[NAME_COLUMN].trim().length() > 0)
              {
                  ScriptWithLinks.append(tabSetting).append(tabSetting);
                  ScriptWithLinks.append("<field ").append(
                          "accessType='").append(sa[ACCESSTYPE_COLUMN]).append("' ").append(
                                "name='").append(sa[NAME_COLUMN]).append("' ").append(
                                "type='").append(sa[TYPE_COLUMN]).append("'");

                  if (copyDefaultsAsAppinfoCheckBox.isSelected() && sa[APPINFO_COLUMN].trim().length() == 0) // do not override appinfo if already defined
                  {
                      if ((!sa[TYPE_COLUMN].equals("SFNode") && !sa[TYPE_COLUMN].equals("MFNode")) && (sa[ACCESSTYPE_COLUMN].equals("initializeOnly") || sa[ACCESSTYPE_COLUMN].equals("inputOutput")))
                      {
                          ScriptWithLinks.append (" appinfo='default value ").append(escapeXmlCharacters(sa[VALUE_COLUMN])).append("'");  // override empty value of appinfo
                      }
                  }
                  else if (sa[APPINFO_COLUMN].trim().length() > 0) // do not provide appinfo if not defined
                          ScriptWithLinks.append(" appinfo='").append(escapeXmlCharacters(sa[APPINFO_COLUMN].trim())).append("'");  // keep prior existing value, do not override

// excessively verbose
//                  if (sa[DOCUMENTATION_COLUMN].trim().length() > 0) // do not provide documentation if not defined
//                          ScriptWithLinks.append(" documentation='").append(escapeCharacters(sa[DOCUMENTATION_COLUMN].trim())).append("'"); // keep prior existing value, do not override

                  // do not include value or contained elements:  they are illegal since IS/connect links supercede them
                  ScriptWithLinks.append("/>\n");
              }
          }
          if (insertTraceParameter  && !traceEnabledFieldAlreadyDefined)
          {
                  ScriptWithLinks.append(tabSetting).append(tabSetting);
                  ScriptWithLinks.append("<field accessType='inputOutput' appinfo='debug trace to Browser output console' name='traceEnabled' type='SFBool'");
                  ScriptWithLinks.append("/>\n");
          }
          ScriptWithLinks.append(tabSetting).append(tabSetting);
          ScriptWithLinks.append("<IS>\n");
          for (String[] sa : data)
          {
              if (sa[NAME_COLUMN] != null && sa[NAME_COLUMN].trim().length() > 0)
              {
                  ScriptWithLinks.append(tabSetting).append(tabSetting).append(tabSetting);
                  ScriptWithLinks.append("<connect ").append(
                           "nodeField='").append(sa[NAME_COLUMN]).append("' ").append(
                          "protoField='").append(sa[NAME_COLUMN]).append("'");
                  ScriptWithLinks.append("/>\n");
              }
          }
          if (insertTraceParameter  && traceEnabledFieldAlreadyDefined)
          {
                  ScriptWithLinks.append(tabSetting).append(tabSetting);
                  ScriptWithLinks.append("<connect nodeField='traceEnabled' protoField='traceEnabled'");
                  ScriptWithLinks.append("/>\n");
          }
          ScriptWithLinks.append(tabSetting).append(tabSetting);
          ScriptWithLinks.append("</IS>\n");
          ScriptWithLinks.append(
                  "<![CDATA[\n").append(
                  "ecmascript:\n").append(
                  "// TODO edit the autogenerated javascript code here, or else").append(
                  "\n").append(
                  "// TODO (optionally) move the following source into external file ").append(protoDeclare.getName()).append("Script.js\n").append(
                  "//       and adjust the above Script to:  <Script DEF='").append(protoDeclare.getName()).append("Script").append("' url=' \"").append(protoDeclare.getName()).append("Script.js\" '/>").append(
                  "\n\n");
          if (insertTraceParameter)
              ScriptWithLinks.append("var scriptName='").append(protoDeclare.getName()).append("Script").append("';\n\n");
          ScriptWithLinks.append(
                  "function initialize ()\n").append(
                  "{\n    // TODO author initialization code (if any) goes here\n");
          if (insertTraceParameter)
              ScriptWithLinks.append("\n    tracePrint ('initialization() successful');\n");
          ScriptWithLinks.append("}\n");
          // create stub methods for inputOnly, inputOutput fields
          for (String[] sa : data)
          {
              if (sa[NAME_COLUMN] != null && sa[NAME_COLUMN].trim().length() > 0)
              {
                  String parameterName;
              //  parameterName= "new").append(sa[NAME_COLUMN].substring(0,1).toUpperCase()).append(sa[NAME_COLUMN].substring(1); // camel case
                  parameterName = "eventValue"; // also not adding timestamp to parameter list, usage is too infrequent/obscure
                  if (sa[ACCESSTYPE_COLUMN].equalsIgnoreCase("inputOnly"))
                  {
                      ScriptWithLinks.append(
                              "function ").append(sa[NAME_COLUMN]).append(" (").append(parameterName).append(")\n").append(
                              "{\n").append(
                              "    // input ").append(parameterName).append(" received for inputOnly field ").append(sa[NAME_COLUMN]).append("\n").append(
                              "    ").append(sa[NAME_COLUMN]).append(" = ").append(parameterName).append(";\n");
                      if (insertTraceParameter)
                          ScriptWithLinks.append("\n    tracePrint ('").append(sa[NAME_COLUMN]).append(" = ' + ").append(sa[NAME_COLUMN]).append(");\n");
                      ScriptWithLinks.append("\n").append(
                              "    // TODO author code (if any) goes here\n").append(
                              "}\n");
                  }
                  else if (sa[ACCESSTYPE_COLUMN].equalsIgnoreCase("inputOutput"))
                  {
                      ScriptWithLinks.append(
                              "function set_").append(sa[NAME_COLUMN]).append(" (").append(parameterName).append(")\n").append(
                              "{\n").append(
                              "    // input ").append(parameterName).append(" received for inputOutput field ").append(sa[NAME_COLUMN]).append("\n").append(
                              "    ").append(sa[NAME_COLUMN]).append(" = ").append(parameterName).append(";\n");
                      if (insertTraceParameter) ScriptWithLinks.append(
                              "    tracePrint ('").append(sa[NAME_COLUMN]).append(" = ' + ").append(sa[NAME_COLUMN]).append(");\n");
                      ScriptWithLinks.append("\n").append(
                              "    // TODO author code (if any) goes here\n").append(
                              "}\n");
                  }
              }
          }
          if (insertTraceParameter) // closing function definitions
          {
              ScriptWithLinks.append("// ================== Trace output functions ==================\n\n").append(
                                     "function tracePrint (outputString)\n").append(
                                     "{\n").append(
                                     "   // if traceEnabled is true, print outputString on X3D browser console\n").append(
                                     "   if (traceEnabled)\n").append(
                                     "      Browser.println ('[' + scriptName.toString()");
              if (hasNameField)
              {
              ScriptWithLinks.append("' ' + name.toString()");
              }
              ScriptWithLinks.append("': ' + outputString.toString() + ']');\n").append(
                                     "}\n");
              ScriptWithLinks.append("function alwaysPrint (outputString)\n").append(
                                     "{\n").append(
                                     "      // always print outputString on X3D browser console\n").append(
                                     "      Browser.println ('[' + scriptName.toString()");
              if (hasNameField)
              {
              ScriptWithLinks.append("' ' + name.toString()");
              }
              ScriptWithLinks.append("': ' + outputString.toString() + ']');\n").append(
                                     "}\n");
              ScriptWithLinks.append("function set_traceEnabled (eventValue)\n").append(
                                     "{\n").append(
                                     "      // input eventValue received for inputOutput field\n").append(
                                     "      traceEnabled = eventValue;\n").append(
                                     "}\n");
              ScriptWithLinks.append("// ===========================================================\n");
          }
          ScriptWithLinks.append("]]>");
          ScriptWithLinks.append(tabSetting);
          ScriptWithLinks.append("</Script>\n");
          ScriptWithLinks.append(tabSetting);
          ScriptWithLinks.append("<!-- Add any ROUTEs here that connect Script to/from prior nodes within ProtoBody -->\n");
          protoDeclare.getProtoBody().appendContent(ScriptWithLinks.toString()); // insertion stays within ProtoBody
      }
  }
  
  @Override
  public void tableChanged(TableModelEvent e)
  {
    if(e.getType() == TableModelEvent.UPDATE){
      int col = e.getColumn();
      int row = e.getFirstRow();
      
      if(col == ACCESSTYPE_COLUMN) { // access type 
        //String access = (String)getTable().getValueAt(row, col);
        //if(FIELD.canHaveValue(access))  fire it always
        ((DefaultTableModel)e.getSource()).fireTableCellUpdated(e.getFirstRow(), VALUE_COLUMN); // value
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
      if(column == VALUE_COLUMN) {
        enabled = FIELD.canHaveValue((String)table.getValueAt(row, TYPE_COLUMN), (String)table.getValueAt(row, ACCESSTYPE_COLUMN));
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
      if(column == VALUE_COLUMN) {
        if(!FIELD.canHaveValue((String)table.getValueAt(row, TYPE_COLUMN), (String)table.getValueAt(row, ACCESSTYPE_COLUMN))) {
         c.setEnabled(false);
        }
      }
      return c;
    }   
  }
}
