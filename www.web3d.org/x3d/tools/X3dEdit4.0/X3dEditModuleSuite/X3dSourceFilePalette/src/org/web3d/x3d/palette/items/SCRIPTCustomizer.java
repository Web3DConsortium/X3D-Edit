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
import java.util.ArrayList;
import javax.swing.DefaultCellEditor;
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
import static org.web3d.x3d.palette.X3DPaletteUtilities.escapeXmlCharacters;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * SCRIPTCustomizer.java
 * Created on JAN 8, 2008 11:40 AM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class SCRIPTCustomizer extends BaseCustomizer implements TableModelListener
{
  private final SCRIPT script;
  private final JTextComponent target;
  private final ArrayList<FIELD> fieldObjs = new ArrayList<>();
  private final X3DDataObject xObj;
  
  private final int NAME_COLUMN = 0;
  private final int TYPE_COLUMN = 1;
  private final int ACCESSTYPE_COLUMN = 2;
  private final int VALUE_COLUMN = 3;
  private final int APPINFO_COLUMN = 4;
  private final int DOCUMENTATION_COLUMN = 5;
  
  /** Creates new form SCRIPTCustomizer */
  public SCRIPTCustomizer(SCRIPT script, JTextComponent target, X3DDataObject xObj)
  {
    super(script);
    this.script = script;
    this.target = target;
    this.xObj = xObj;
    
    HelpCtx.setHelpIDString(this, "SCRIPT_ELEM_HELPID");
    
    initComponents();
    
    scriptHeader1Label.setText(SCRIPT.ecmaScriptHeader1);
    scriptHeader2Label.setText(SCRIPT.ecmaScriptHeader2);
    scriptTrailer1Label.setText(SCRIPT.ecmaScriptTrailer);
    
   // urlList.setTitle(NbBundle.getMessage(getClass(), "LBL_URLS"));
    urlList.setMasterDocumentLocation(xObj.getPrimaryFile());
    urlList.setUrlData(script.getUrls());
    urlList.setTarget(target); // enable urlList to reach back into jdom tree to getHeaderIdentifierPath()
    urlList.setMinimumSize(new Dimension(50,50));
    Dimension d = urlList.getPreferredSize();
    urlList.setPreferredSize(new Dimension(d.width,Math.max(50,d.height-70)));
    urlList.setFileChooserScript ();
    urlList.checkUrlValues();
    
    directOutputCB.setSelected(script.isDirectOutput());
    mustEvaluateCB.setSelected(script.isMustEvaluate());
    
    fieldsList.setHeaderTooltip (FIELD_HEADER_TOOLTIP);
    fieldsList.setColumnEditor(new DefaultCellEditor(new JComboBox<>(FIELD_ATTR_TYPE_CHOICES)),             TYPE_COLUMN);
    fieldsList.setColumnEditor(new DefaultCellEditor(new JComboBox<>(FIELD_ATTR_ACCESSTYPE_CHOICES)), ACCESSTYPE_COLUMN);

    fieldsList.setColumnTitles  (FIELD_ATTR_NAMES);
    fieldsList.setColumnToolTips(FIELD_ATTR_TOOLTIPS);
    fieldsList.setNewRowData(new Object[]{"","","","","",""});
    fieldsList.setCellEditPanelVisible(false); // however might want to modify values, TODO handle only that column
    
        insertCommasCheckBox.setSelected(script.isInsertCommas());
    insertLineBreaksCheckBox.setSelected(script.isInsertLineBreaks());

    fieldObjs.clear();
    fieldObjs.addAll(script.getFields());
    String[][] data = new String[fieldObjs.size()][6];
    if (fieldObjs.isEmpty())
    {
        // don't add an empty field when initializing
    }
    else
    {
        int r = 0;
        for (FIELD f : fieldObjs)
        {
          data[r][NAME_COLUMN]          = f.getName();
          data[r][TYPE_COLUMN]          = f.getType();
          data[r][ACCESSTYPE_COLUMN]    = f.getAccessType();
          data[r][VALUE_COLUMN]         = f.getValue();
          data[r][APPINFO_COLUMN]       = f.getAppinfo();
          data[r][DOCUMENTATION_COLUMN] = f.getDocumentation();
          r++;
        }
        // TODO also handle initializing children nodes?
    }
    fieldsList.setData(data);
        
    fieldsList.getTable().setDefaultRenderer(String.class, new myDefaultStringRenderer());
    fieldsList.getTable().setDefaultEditor(String.class, new myDefaultStringEditor());
    fieldsList.getTable().getModel().addTableModelListener(this);
    
    ECMAScriptSourceTextArea.setText(script.getEcmaContent());
    boolean doScript = script.isShowECMA();
    if (handleUrlContainingEmbeddedSource ())
    {
        doScript = true;
        script.setShowECMA(true);
    }    
    includeECMAScriptSourceCB.setSelected(doScript);
    toggleECMAScriptSourcePanelWidgets(doScript);

    setDefaultDEFname ();
    createScriptFileButton.setToolTipText("Create new " + super.getDEFUSEpanel().getDefaultDEFname() + ".js file holding this source");
  }
  private void setDefaultDEFname ()
  {
	if ((urlList == null) || (urlList.getUrlData() == null) || (urlList.getUrlData().length == 0))
	{
		super.getDEFUSEpanel().setDefaultDEFname("New" + "Script");
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
        fileName += "Script"; // otherwise empty
    
    super.getDEFUSEpanel().setDefaultDEFname(fileName);
  }
  private boolean handleUrlContainingEmbeddedSource ()
  {
    for (int i = 0; i < urlList.getUrlData().length; i++)
    {
        String sourceCode = urlList.getUrlData()[i];

        if (sourceCode.trim().length() == 0) 
            continue;
        if (!sourceCode.trim().startsWith("ecmascript:") && !sourceCode.trim().startsWith("javascript:"))
            continue;
        if (!sourceCode.contains("function") && !sourceCode.contains("//"))
            continue;
        
        // found ecmascript source in url value
        StringBuilder notificationMessage = new StringBuilder();
        notificationMessage.append("<html><p align='center'>");
        notificationMessage.append("url[").append(i).append("] ");
        notificationMessage.append(" contains ecmascript source code</p>");
        notificationMessage.append("<br />");
        notificationMessage.append("<p align='center'>Move source to embedded CDATA block (recommended)?</p>");
        NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
              notificationMessage.toString(),
              "Source code found in url", NotifyDescriptor.YES_NO_OPTION);
        if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION) 
        {
            // move source
            if (sourceCode.trim().startsWith("ecmascript:"))
                sourceCode = sourceCode.substring(sourceCode.indexOf("ecmascript:") + "ecmascript:".length());
            if (sourceCode.trim().startsWith("javascript:"))
                sourceCode = sourceCode.substring(sourceCode.indexOf("javascript:") + "javascript:".length());
            ECMAScriptSourceTextArea.setText(sourceCode);
            String[] newUrlData =  urlList.getUrlData();
            newUrlData[i] = "";
            urlList.setUrlData(newUrlData);
            
            if (!sourceCode.contains("\n") && ((sourceCode.length() > 10) || sourceCode.contains("//")))
            {
                notificationMessage = new StringBuilder();
                notificationMessage.append("<html><p align='center'>");
                notificationMessage.append("Be sure to reformat source with line breaks, in order to</p>");
                notificationMessage.append("<p align='center'>avoid problems with <b><code>// comments hiding <b>{ code blocks }</b></code></p>");
                descriptor = new NotifyDescriptor.Message(
                    notificationMessage.toString(), NotifyDescriptor.INFORMATION_MESSAGE);
                DialogDisplayer.getDefault().notify(descriptor);
            }
            return true; // only look for and handle one set of source, do not continue
        }
    }
    return false;
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
        directOutputCB = new javax.swing.JCheckBox();
        mustEvaluateCB = new javax.swing.JCheckBox();
        tablesScriptSplitter = new javax.swing.JSplitPane();
        urlFieldsSplitter = new javax.swing.JSplitPane();
        urlScrollPane = new javax.swing.JScrollPane();
        urlList = new org.web3d.x3d.palette.items.UrlExpandableList2();
        fieldsScrollPane = new javax.swing.JScrollPane();
        fieldsList = new org.web3d.x3d.palette.items.ExpandableList();
        ecmascriptSourcePanel = new javax.swing.JPanel();
        scriptHeader2Label = new javax.swing.JLabel();
        scriptTrailer1Label = new javax.swing.JLabel();
        scriptHeader1Label = new javax.swing.JLabel();
        scriptScrollPane = new javax.swing.JScrollPane();
        ECMAScriptSourceTextArea = new javax.swing.JTextArea();
        includeECMAScriptSourceCB = new javax.swing.JCheckBox();
        appendFieldSetMethodsButton = new javax.swing.JButton();
        createScriptFileButton = new javax.swing.JButton();
        clearScriptButton = new javax.swing.JButton();
        rightSpacer = new javax.swing.JLabel();
        leftSpacer = new javax.swing.JLabel();
        urlLabel = new javax.swing.JLabel();
        appendLabel = new javax.swing.JLabel();
        insertCommasCheckBox = new javax.swing.JCheckBox();
        insertLineBreaksCheckBox = new javax.swing.JCheckBox();

        directOutputCB.setText("directOutput");
        directOutputCB.setToolTipText("set true if field of type SFNode/MFNode is modified by script");
        directOutputCB.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);

        mustEvaluateCB.setText("mustEvaluate");
        mustEvaluateCB.setToolTipText("if false then player may delay sending input events until outputs are needed, otherwise process events immediately");
        mustEvaluateCB.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);

        tablesScriptSplitter.setBorder(null);
        tablesScriptSplitter.setDividerLocation(320);
        tablesScriptSplitter.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        tablesScriptSplitter.setResizeWeight(0.5);
        tablesScriptSplitter.setLastDividerLocation(320);

        urlFieldsSplitter.setBorder(null);
        urlFieldsSplitter.setDividerLocation(160);
        urlFieldsSplitter.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        urlFieldsSplitter.setResizeWeight(0.5);
        urlFieldsSplitter.setLastDividerLocation(160);
        urlFieldsSplitter.setPreferredSize(new java.awt.Dimension(590, 320));

        urlScrollPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 5, 1));
        urlScrollPane.setPreferredSize(new java.awt.Dimension(590, 100));

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
        fieldsScrollPane.setPreferredSize(new java.awt.Dimension(590, 100));

        fieldsList.setBorder(null);
        fieldsList.setMaximumSize(null);
        fieldsList.setPreferredSize(new java.awt.Dimension(100, 120));
        fieldsList.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                fieldsListPropertyChange(evt);
            }
        });
        fieldsScrollPane.setViewportView(fieldsList);

        urlFieldsSplitter.setBottomComponent(fieldsScrollPane);

        tablesScriptSplitter.setTopComponent(urlFieldsSplitter);

        ecmascriptSourcePanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createEtchedBorder(), "", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BELOW_TOP, new java.awt.Font("Dialog", 0, 12))); // NOI18N
        ecmascriptSourcePanel.setMaximumSize(new java.awt.Dimension(400, 300));
        ecmascriptSourcePanel.setLayout(new java.awt.GridBagLayout());

        scriptHeader2Label.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        scriptHeader2Label.setText("ecmascript:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 3);
        ecmascriptSourcePanel.add(scriptHeader2Label, gridBagConstraints);

        scriptTrailer1Label.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        scriptTrailer1Label.setText("]]>");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 3);
        ecmascriptSourcePanel.add(scriptTrailer1Label, gridBagConstraints);

        scriptHeader1Label.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        scriptHeader1Label.setText("<![CDATA[");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 3);
        ecmascriptSourcePanel.add(scriptHeader1Label, gridBagConstraints);

        ECMAScriptSourceTextArea.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        ECMAScriptSourceTextArea.setTabSize(2);
        ECMAScriptSourceTextArea.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                ECMAScriptSourceTextAreaPropertyChange(evt);
            }
        });
        ECMAScriptSourceTextArea.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                ECMAScriptSourceTextAreaKeyReleased(evt);
            }
        });
        scriptScrollPane.setViewportView(ECMAScriptSourceTextArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 859;
        gridBagConstraints.ipady = 44;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 10, 0, 3);
        ecmascriptSourcePanel.add(scriptScrollPane, gridBagConstraints);

        includeECMAScriptSourceCB.setText(" Embed ECMAscript source code");
        includeECMAScriptSourceCB.setToolTipText("Enable embedded source editor below, rather than using external .js file");
        includeECMAScriptSourceCB.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        includeECMAScriptSourceCB.setMargin(new java.awt.Insets(2, 0, 2, 2));
        includeECMAScriptSourceCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                includeECMAScriptSourceCBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.ipady = -8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 20, 3, 3);
        ecmascriptSourcePanel.add(includeECMAScriptSourceCB, gridBagConstraints);

        appendFieldSetMethodsButton.setText("Add field methods to source for input events");
        appendFieldSetMethodsButton.setToolTipText("Append stub field methods for receiving inputOnly and inputOutput events to source code");
        appendFieldSetMethodsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                appendFieldSetMethodsButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        ecmascriptSourcePanel.add(appendFieldSetMethodsButton, gridBagConstraints);

        createScriptFileButton.setText("Create External Script File");
        createScriptFileButton.setToolTipText("Create new .js file by copying this embedded source");
        createScriptFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createScriptFileButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        ecmascriptSourcePanel.add(createScriptFileButton, gridBagConstraints);

        clearScriptButton.setText("Clear Script Text");
        clearScriptButton.setToolTipText("Clear source code from text area");
        clearScriptButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearScriptButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        ecmascriptSourcePanel.add(clearScriptButton, gridBagConstraints);

        tablesScriptSplitter.setRightComponent(ecmascriptSourcePanel);

        urlLabel.setText("url array");
        urlLabel.setToolTipText("ordered list of alternate url addresses for script, if not embedded");

        appendLabel.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        appendLabel.setText("append:");

        insertCommasCheckBox.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        insertCommasCheckBox.setText("commas,");

        insertLineBreaksCheckBox.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        insertLineBreaksCheckBox.setText("line feeds");
        insertLineBreaksCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertLineBreaksCheckBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(tablesScriptSplitter, javax.swing.GroupLayout.DEFAULT_SIZE, 982, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(urlLabel)
                        .addGap(18, 18, 18)
                        .addComponent(directOutputCB)
                        .addGap(18, 18, 18)
                        .addComponent(mustEvaluateCB)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(leftSpacer, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                        .addGap(263, 263, 263)
                        .addComponent(rightSpacer, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
                        .addGap(12, 12, 12)
                        .addComponent(appendLabel)
                        .addGap(0, 0, 0)
                        .addComponent(insertCommasCheckBox)
                        .addGap(0, 0, 0)
                        .addComponent(insertLineBreaksCheckBox))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(216, 216, 216)
                        .addComponent(dEFUSEpanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dEFUSEpanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(rightSpacer)
                            .addComponent(leftSpacer))
                        .addGap(4, 4, 4)
                        .addComponent(urlLabel))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(appendLabel))
                            .addComponent(insertCommasCheckBox)
                            .addComponent(insertLineBreaksCheckBox))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(directOutputCB)
                            .addComponent(mustEvaluateCB))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tablesScriptSplitter, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

  private void includeECMAScriptSourceCBActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_includeECMAScriptSourceCBActionPerformed
  {//GEN-HEADEREND:event_includeECMAScriptSourceCBActionPerformed
    toggleECMAScriptSourcePanelWidgets(includeECMAScriptSourceCB.isSelected());
}//GEN-LAST:event_includeECMAScriptSourceCBActionPerformed

  private void appendFieldSetMethodsButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_appendFieldSetMethodsButtonActionPerformed
  {//GEN-HEADEREND:event_appendFieldSetMethodsButtonActionPerformed
          String[][] data = fieldsList.getData();
          // create stub methods for inputOnly, inputOutput fields
          boolean found   = false;
          boolean changed = false;
          for (String[] sa : data)
          {
              if (sa[NAME_COLUMN] != null && sa[NAME_COLUMN].length() > 0)
              {
                  // TODO use proper tab setting from Netbeans
                  String parameterName;
              //  parameterName= "new" + sa[NAME_COLUMN].substring(0,1).toUpperCase() + sa[NAME_COLUMN].substring(1); // camel case
                  parameterName = "eventValue"; // also not adding timestamp to parameter list, usage is too infrequent/obscure
                  if (sa[ACCESSTYPE_COLUMN].equalsIgnoreCase("inputOnly"))
                  {
                      found = true;
                      if (!ECMAScriptSourceTextArea.getText().contains("function " + sa[NAME_COLUMN]))
                      {
                          ECMAScriptSourceTextArea.append("\nfunction " + sa[NAME_COLUMN] + " (" + parameterName + ")\n" +
                                  "{\n   // input " + parameterName + " received for inputOnly field\n   // do something with input " + parameterName + ";\n}\n");
                          changed = true;
                      }
                  }
                  else if (sa[ACCESSTYPE_COLUMN].equalsIgnoreCase("inputOutput"))  // note clarified design pattern from Web3D Symposium, June 2009
                  {
                      found = true;
                      if (!ECMAScriptSourceTextArea.getText().contains("function set_" + sa[NAME_COLUMN]))
                      {
                          ECMAScriptSourceTextArea.append("\nfunction set_" + sa[NAME_COLUMN] + " (" + parameterName + ")\n" +
                                  "{\n   // input " + parameterName + " received for inputOutput field\n    " + sa[NAME_COLUMN] + " = " + parameterName + ";\n}\n");
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

  private void insertLineBreaksCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertLineBreaksCheckBoxActionPerformed
      // TODO add your handling code here:
}//GEN-LAST:event_insertLineBreaksCheckBoxActionPerformed

    private void urlListPropertyChange(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_urlListPropertyChange
    {//GEN-HEADEREND:event_urlListPropertyChange
        setDefaultDEFname ();
    }//GEN-LAST:event_urlListPropertyChange

    private void createScriptFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createScriptFileButtonActionPerformed
        String        scriptName   = dEFUSEpanel1.getDEF();
        StringBuilder scriptSource = new StringBuilder ();
        if (scriptName.isEmpty())
            scriptName = super.getDEFUSEpanel().getDefaultDEFname();
        scriptSource.append("// ").append(scriptName).append(".js").append("\n").append("\n");
        // TODO copy over file metadata

        // TODO refactor as shared utility method
        String[][] data = fieldsList.getData();
        if(data.length>0) {
//          ArrayList<FIELD> v = new ArrayList<>(data.length);
          int r=0;
          for(String[] sa : data) {
            if(sa[NAME_COLUMN] != null && sa[NAME_COLUMN].length()>0) {
              FIELD field = fieldObjs.get(r); //new FIELD();
              field.setName(sa[NAME_COLUMN]);
              field.setType(sa[TYPE_COLUMN]);
              field.setAccessType(sa[ACCESSTYPE_COLUMN]);
              field.setValue(sa[VALUE_COLUMN]);
              field.setAppinfo(sa[APPINFO_COLUMN]);
              field.setDocumentation(sa[DOCUMENTATION_COLUMN]);
//              v.add(field);
              r++;
              if (sa[TYPE_COLUMN].equals("MFString") && (sa[VALUE_COLUMN].length() > 0) && !sa[VALUE_COLUMN].contains("\""))
              {
                  field.setValue("\"" + sa[VALUE_COLUMN] + "\"");
                  NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                     "<html><p align='center'>field <b>" + field.getName() +
                     "</b> of type MFString must have quoted value</p>" +
                     "<br /> <p align='center'>Quotes added to create a proper single-string value</p>", NotifyDescriptor.WARNING_MESSAGE);
                  DialogDisplayer.getDefault().notify(descriptor);
              }
            
                scriptSource.append("// ").append(field.getName())
                              .append(" ").append(field.getType())
                              .append(" ").append(field.getAccessType());
                if (field.getValue().length() > 0)
                {
                    scriptSource.append(" ").append(field.getValue());
                }            
                if (field.getAppinfo().length() > 0)
                {
                    scriptSource.append("; ").append(field.getAppinfo());
                }            
                if (field.getDocumentation().length() > 0)
                {
                    scriptSource.append("\n").append("//     ");
                    scriptSource.append(" ").append(field.getDocumentation());
                }            
                scriptSource.append("\n");
            }
            // TODO also handle initializing children nodes
          }
        }
      
        scriptSource.append(ECMAScriptSourceTextArea.getText()).append("\n");
        urlList.openInX3dEdit(scriptName + ".js", scriptSource.toString());
    }//GEN-LAST:event_createScriptFileButtonActionPerformed

    private void clearScriptButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearScriptButtonActionPerformed
            if (!ECMAScriptSourceTextArea.getText().trim().isEmpty())
            {
            StringBuilder notificationMessage = new StringBuilder();
            notificationMessage.append("<html><p align='center'>");
            notificationMessage.append("<b>Are you sure</b> you want to delete source code for this script?</p>");
            notificationMessage.append("<br />");
            notificationMessage.append("<p align='center'>All source code will be cleared from the text area below.</p>");
            NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  notificationMessage.toString(),
                  "Clear source code?", NotifyDescriptor.YES_NO_OPTION);
            if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION) 
            {
                ECMAScriptSourceTextArea.setText("");
                includeECMAScriptSourceCB.setSelected(false);
                toggleECMAScriptSourcePanelWidgets(false);
            }
        }
    }//GEN-LAST:event_clearScriptButtonActionPerformed

    private void ECMAScriptSourceTextAreaPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_ECMAScriptSourceTextAreaPropertyChange
        toggleECMAScriptSourcePanelWidgets(includeECMAScriptSourceCB.isSelected());
    }//GEN-LAST:event_ECMAScriptSourceTextAreaPropertyChange

    private void ECMAScriptSourceTextAreaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_ECMAScriptSourceTextAreaKeyReleased
        toggleECMAScriptSourcePanelWidgets(includeECMAScriptSourceCB.isSelected());
    }//GEN-LAST:event_ECMAScriptSourceTextAreaKeyReleased

    private void fieldsListPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_fieldsListPropertyChange
        toggleECMAScriptSourcePanelWidgets(includeECMAScriptSourceCB.isSelected()); // update appendFields button enabled
    }//GEN-LAST:event_fieldsListPropertyChange
  
  private void toggleECMAScriptSourcePanelWidgets(boolean enable)
  {
    ECMAScriptSourceTextArea.setEnabled(enable);
    scriptHeader1Label.setEnabled(enable);
    scriptHeader2Label.setEnabled(enable);
    scriptTrailer1Label.setEnabled(enable);
    appendFieldSetMethodsButton.setEnabled(enable); // TODO needs fieldsListPropertyChange callback:   && (fieldsList.getTable().getRowCount() >= 1));
    
    createScriptFileButton.setEnabled(enable && !ECMAScriptSourceTextArea.getText().trim().isEmpty());
         clearScriptButton.setEnabled(enable && !ECMAScriptSourceTextArea.getText().isEmpty());
  }
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea ECMAScriptSourceTextArea;
    private javax.swing.JButton appendFieldSetMethodsButton;
    private javax.swing.JLabel appendLabel;
    private javax.swing.JButton clearScriptButton;
    private javax.swing.JButton createScriptFileButton;
    private org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel1;
    private javax.swing.JCheckBox directOutputCB;
    private javax.swing.JPanel ecmascriptSourcePanel;
    private org.web3d.x3d.palette.items.ExpandableList fieldsList;
    private javax.swing.JScrollPane fieldsScrollPane;
    private javax.swing.JCheckBox includeECMAScriptSourceCB;
    private javax.swing.JCheckBox insertCommasCheckBox;
    private javax.swing.JCheckBox insertLineBreaksCheckBox;
    private javax.swing.JLabel leftSpacer;
    private javax.swing.JCheckBox mustEvaluateCB;
    private javax.swing.JLabel rightSpacer;
    private javax.swing.JLabel scriptHeader1Label;
    private javax.swing.JLabel scriptHeader2Label;
    private javax.swing.JScrollPane scriptScrollPane;
    private javax.swing.JLabel scriptTrailer1Label;
    private javax.swing.JSplitPane tablesScriptSplitter;
    private javax.swing.JSplitPane urlFieldsSplitter;
    private javax.swing.JLabel urlLabel;
    private org.web3d.x3d.palette.items.UrlExpandableList2 urlList;
    private javax.swing.JScrollPane urlScrollPane;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_SCRIPT";
  }

  @Override
  public void unloadInput()
  {
    unLoadDEFUSE();
    urlList.checkUrlValues();
    
    script.setDirectOutput(directOutputCB.isSelected());
    script.setMustEvaluate(mustEvaluateCB.isSelected());
    script.setUrls(urlList.getUrlData());
    
    if (includeECMAScriptSourceCB.isSelected())
    {
        script.setEcmaContent(ECMAScriptSourceTextArea.getText());
//        script.setShowECMA(true);
    }
    else
    {
        script.setEcmaContent("");
//        script.setShowECMA(false);
    }
    
    String[][] data = fieldsList.getData();
    if(data.length>0) {
      ArrayList<FIELD> v = new ArrayList<>(data.length);
      int r=0;
      for(String[] sa : data) {
        if(sa[NAME_COLUMN] != null && sa[NAME_COLUMN].length()>0) {
          FIELD field = fieldObjs.get(r); //new FIELD();
          field.setName(sa[NAME_COLUMN]);
          field.setType(sa[TYPE_COLUMN]);
          field.setAccessType(sa[ACCESSTYPE_COLUMN]);
          field.setValue(escapeXmlCharacters(sa[VALUE_COLUMN]));
          field.setAppinfo(escapeXmlCharacters(sa[APPINFO_COLUMN]));
          field.setDocumentation(escapeXmlCharacters(sa[DOCUMENTATION_COLUMN]));
          v.add(field);
          r++;
          if (sa[TYPE_COLUMN].equals("MFString") && (sa[VALUE_COLUMN].length() > 0) && !sa[VALUE_COLUMN].contains("\""))
          {
              field.setValue("\"" + sa[VALUE_COLUMN] + "\"");
              NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                 "<html><p align='center'>field <b>" + field.getName() +
                 "</b> of type MFString must have quoted value</p>" +
                 "<br /> <p align='center'>Quotes added to create a proper single-string value</p>", NotifyDescriptor.WARNING_MESSAGE);
              DialogDisplayer.getDefault().notify(descriptor);
          }
        }
        // TODO also handle initializing children nodes
      }
     script.setFields(v);
    }
    script.setInsertCommas    (   insertCommasCheckBox.isSelected());
    script.setInsertLineBreaks(insertLineBreaksCheckBox.isSelected());
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
        ((DefaultTableModel)e.getSource()).fireTableCellUpdated(row, VALUE_COLUMN); // value
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
        f.setParent(SCRIPT_ELNAME);
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
