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

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;
import org.jdom.Attribute;
import org.jdom.Element;
import org.openide.awt.HtmlBrowser.URLDisplayer;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.web3d.x3d.X3DDataObject;
import static org.web3d.x3d.palette.X3DPaletteUtilities.escapeXmlCharacters;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * EXTERNPROTODECLARECustomizer.java
 * Created on JAN 8, 2008 11:40 AM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class EXTERNPROTODECLARECustomizer extends BaseCustomizer implements TableModelListener, ListSelectionListener, ExternProtoDeclareSyncHelper2.SyncStatusListener
{
  private final EXTERNPROTODECLARE externProtoDeclare;
  private final JTextComponent target;
  private final X3DDataObject xObj;

  public static final int NAME_COLUMN = 0;
  public static final int TYPE_COLUMN = 1;
  public static final int ACCESSTYPE_COLUMN = 2;
  public static final int VALUE_COLUMN = 3;
  public static final int APPINFO_COLUMN = 4;
  public static final int DOCUMENTATION_COLUMN = 5;

  private final Color    redColor = UrlExpandableList2.redForeground;
  private final Color yellowColor = new Color(120, 120,   0);
  private final Color   greyColor = new Color(200, 200, 200);
  private final Color  greenColor = new Color(  0, 120,   0);
  private       Color defaultForeground;
  private       Color defaultBackground;

  private int     fieldValueInitializationCount = 0;
  private boolean removeFieldInitializationValues = true;
  private int     redRowsStartIndex;

  private final String[] attributeNames    = FIELD_ATTR_NAMES;
  private final String[] attributeToolTips = {"","","","","",""};
  
  private final DefaultTableCellRenderer fieldsDefaultTableCellRenderer; 
  private final DefaultCellEditor        fieldsDefaultCellEditor;

  /** Creates new form EXTERNPROTODECLARECustomizer */
  @SuppressWarnings("RedundantStringConstructorCall")
  public EXTERNPROTODECLARECustomizer(EXTERNPROTODECLARE epd, JTextComponent target, X3DDataObject xObj)
  {
    super(epd);
    this.externProtoDeclare = epd;
    this.target = target;
    this.xObj = xObj;
    HelpCtx.setHelpIDString(this, "EXTERNPROTODECLARE_ELEM_HELPID");
    
    initComponents();

    // forced copy by value (rather than copy by reference) to avoid modifying original data structure
    for (int i=0; i < FIELD_ATTR_TOOLTIPS.length; i++)
            attributeToolTips[i] = new String (FIELD_ATTR_TOOLTIPS[i]);
    attributeToolTips[3] = "no value allowed for ExternProtoDeclare field definitions";
    
    urlTableList.setTitle(NbBundle.getMessage(getClass(), "LBL_URL_LIST_2_TITLE"));

    urlTableList.setMasterDocumentLocation(xObj.getPrimaryFile());
    urlTableList.getJList().addListSelectionListener(this);
    urlTableList.setFileChooserX3d();

    String protoName = externProtoDeclare.getName();
    nameTF.setText(protoName);
    
//    urlList.setColumnTitles(new String[]{NbBundle.getMessage(EXTERNPROTODECLARECustomizer.class, "LBL_URL"),"..."});
//    urlList.setToolTipText(NbBundle.getMessage(EXTERNPROTODECLARECustomizer.class, "LBL_URL_TOOLTIP"));
//    urlList.doTrailingTextEditButt(true);
//    urlList.setTextAlignment(JLabel.RIGHT);

    String [] urlArray = externProtoDeclare.getUrls();
    for (int i=0; i < urlArray.length; i++)
    {
        if      (!urlArray[i].trim().startsWith("urn:") &&
                 !urlArray[i].contains("#")) // if #protoName missing, needs #protoName appended
        {
            int ret = JOptionPane.showConfirmDialog(this, 
                        "<html><p align='center'>Append missing prototype name <i>" + protoName + "</i>  to url?</p><br />" +
                        "<p align='center'>" + urlArray[i] + "<b>#<i>" + protoName + "</i></b>&nbsp;</p>",
                        "Confirm...", JOptionPane.YES_NO_OPTION);
            if (ret == JOptionPane.YES_OPTION)
            {
                urlArray[i] += "#" + protoName; // append
            }
        }
    }
    urlTableList.setUrlData(urlArray);
    urlTableList.setTarget(target); // enable urlList to reach back into jdom tree to getHeaderIdentifierPath()
    urlTableList.setPreferredSize(adjustUrlListSize());
    urlTableList.checkUrlValues();
	if (urlTableList.getLength() > 0)
	{
		urlTableList.setSelectedIndex (0);
		enableVerifyUpdateButtons (true);
	}

    fieldsTableList.setColumnEditor(new DefaultCellEditor(new JComboBox<>(FIELD_ATTR_TYPE_CHOICES)),1);
    fieldsTableList.setColumnEditor(new DefaultCellEditor(new JComboBox<>(FIELD_ATTR_ACCESSTYPE_CHOICES)), 2);
    
    // no initialization values are allowed in ExternProtoDeclare field declarations
    fieldsTableList.setColumnTitles  (attributeNames);
    fieldsTableList.setColumnToolTips(attributeToolTips);
    fieldsTableList.setHeaderTooltip (FIELDVALUE_HEADER_TOOLTIP);
    fieldsTableList.setNewRowData(new Object[] { "", "", "", "", "", "" });
    fieldsTableList.setCellEditPanelVisible(false);
    fieldsTableList.setShowAppendCommasLineBreaks(true);
    fieldsTableList.setInsertCommas    (externProtoDeclare.isInsertCommas());
    fieldsTableList.setInsertLineBreaks(externProtoDeclare.isInsertLineBreaks());

    // precede setting data
    fieldsDefaultTableCellRenderer = new NewDefaultTableCellRenderer();
    fieldsDefaultCellEditor        = new NewDefaultCellEditor();
    fieldsTableList.getTable().setDefaultRenderer(Object.class, fieldsDefaultTableCellRenderer); // String.class
    fieldsTableList.getTable().setDefaultEditor  (Object.class, fieldsDefaultCellEditor);        // String.class

    List<FIELD> fields = externProtoDeclare.getFields();
    if(fields == null)
      fields = new Vector<>(1);
    
    if( fields.isEmpty()) {
      FIELD f = new FIELD("","","","","",""); // something to start with
      f.setParent(EXTERNPROTODECLARE_ELNAME);
      fields.add(f);
    }
    redRowsStartIndex = fields.size();
    StringBuilder fieldValueInitializationNames = new StringBuilder();
    if (!fields.isEmpty())
    {
      String[][] data = new String[fields.size()][6];
      int r = 0;
      for (FIELD f : fields) {
        data[r][NAME_COLUMN]          = f.getName();
        data[r][TYPE_COLUMN]          = f.getType();
        data[r][ACCESSTYPE_COLUMN]    = f.getAccessType();
        data[r][VALUE_COLUMN]         = f.getValue();
        data[r][APPINFO_COLUMN]       = f.getAppinfo();
        data[r][DOCUMENTATION_COLUMN] = f.getDocumentation();
        r++;
        if (f.getValue().length()!=0)
        {
            fieldValueInitializationCount++;
            fieldValueInitializationNames.append(f.getName()).append(" ");
        }
      }
      if (fieldValueInitializationCount > 0) // field values are not legal within ExternProtoDeclare
      {
            String plural = "";
            if (fieldValueInitializationCount > 1) plural = "s";
            int ret = JOptionPane.showConfirmDialog(this,
                        "<html><p align='center'>Remove illegal initialization value" + plural + 
                        "<br />from field definition" + plural + " <b>" + fieldValueInitializationNames + "</b> " +
                        "<br />inside ExternProtoDeclare <b>" + protoName + "</b>?</p>",
                        "Confirm...", JOptionPane.YES_NO_OPTION);
            if (ret == JOptionPane.YES_OPTION)
            {
                  removeFieldInitializationValues = true;
                  r = 0;
                  for(FIELD f : fields) {
                    data[r][VALUE_COLUMN] = "";
                    r++;
                  }
            }
      }
      fieldsTableList.setData(data);
    }
      // ExternProtoDeclare fields are not allowed to have value attribues or children
      // TODO disable column 4 for value
      // fieldsList.getTable().getColumnClass(4).
    fieldsTableList.getTable().getModel().addTableModelListener(this);
    
    fieldsTableList.getTable().setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
    fieldsTableList.getTable().getColumnModel().getColumn(NAME_COLUMN).setPreferredWidth(130);
    fieldsTableList.getTable().getColumnModel().getColumn(TYPE_COLUMN).setPreferredWidth(70);
    fieldsTableList.getTable().getColumnModel().getColumn(ACCESSTYPE_COLUMN).setPreferredWidth(70);
    fieldsTableList.getTable().getColumnModel().getColumn(VALUE_COLUMN).setPreferredWidth(40);
    fieldsTableList.getTable().getColumnModel().getColumn(APPINFO_COLUMN).setPreferredWidth(200);
    fieldsTableList.getTable().getColumnModel().getColumn(DOCUMENTATION_COLUMN).setPreferredWidth(200);

    appInfoTextArea.setText(externProtoDeclare.getAppinfo());
    appInfoTextArea.setCaretPosition(0);
    documentationTF.setText(externProtoDeclare.getDocumentation());
    documentationTF.setCaretPosition(0);
    openDocumentationButton.setEnabled(!documentationTF.getText().trim().isEmpty());

    urlTableList.getJList().addListSelectionListener(this);
  }

  /**
   * Notification that a urlList selection has been made
   * @param e
   */
  @Override
  public void valueChanged(ListSelectionEvent e)
  {
      if(e.getValueIsAdjusting())
          return;
      String url = getSelectedGreenUrl();
      if ((url == null) || (!url.contains(".x3d")))
      {
          enableVerifyUpdateButtons(false);
      }
      else
      {
          enableVerifyUpdateButtons(true);
      }
  }
 
  private Dimension adjustUrlListSize()
  {
    Dimension d = urlTableList.getPreferredSize();
    String[] sa = urlTableList.getUrlData();
    FontMetrics fm = urlTableList.getFontMetrics(urlTableList.getFont());
    int w = 400; // min
    for(String s : sa){
      int wi = fm.stringWidth(s);
      w = Math.max(w, wi+35);  // overhead
    }
    w = Math.min(w, 900);
    return new Dimension(w,d.height);
  }

  private String getSelectedUrl()
  {
    int idx = urlTableList.getJList().getSelectedIndex();
    if(idx == -1)
      return null;
    return urlTableList.getJList().getSelectedValue();
  }

  private String getSelectedGreenUrl()
  {
    int idx = urlTableList.getJList().getSelectedIndex();
    if(idx == -1)
      return null;
    if(!urlTableList.isGreenLink(idx))
      return null;
    return urlTableList.getJList().getSelectedValue();
  }

  ExternProtoDeclareSyncHelper2 externProtoDeclareSyncHelper;
  boolean doVerifyOnly = true;
  // Callbacks from Syncher

  @Override
  public void checkDone(HashMap<String, Object> remoteHashMap, final String statusResults)
  {
    // The remoteProtoDeclareHashMap holds the original ProtoDeclare/ProtoInterface set of fields, either null or not
    remoteProtoDeclareHashMap = remoteHashMap;
    
    if (doVerifyOnly) // Verify field signatures
    {
      SwingUtilities.invokeLater(() -> {
          statusLabel.setText(statusResults);
      
          // TODO iterate over remoteProtoDeclareHashMap to add jTable rows matching remote extra fields
          if ((remoteProtoDeclareHashMap != null) && !remoteProtoDeclareHashMap.isEmpty())
          {
              DefaultTableModel tableModel = (DefaultTableModel) fieldsTableList.getTable().getModel();
              Iterator<HashMap.Entry<String, Object>> entriesIterator = remoteProtoDeclareHashMap.entrySet().iterator();
              while (entriesIterator.hasNext())
              {
                  Map.Entry<String, Object> entry = entriesIterator.next();
                  String remoteFieldName = entry.getKey();
                  boolean found    = false;
                  for (int i=0; i < fieldsTableList.getTable().getRowCount(); i++)
                  {
                      String fieldName = (String) fieldsTableList.getTable().getValueAt(i, NAME_COLUMN);
                      if (remoteFieldName.equalsIgnoreCase(fieldName))
                      {
                          found    = true;// mismatched case is flagged in cell renderer
                          break;
                      }
                  }
                  if (!found) // add new field to jTable
                  {
                      // first check for special entries
                      if      (remoteFieldName.equals(ExternProtoDeclareSyncHelper2.PROTODECL_APPINFO_KEY))
                      {
                          handleSpecialText(ExternProtoDeclareSyncHelper2.PROTODECL_APPINFO_KEY);
                      }
                      else if (remoteFieldName.equals(ExternProtoDeclareSyncHelper2.PROTODECL_DOCUMENTATION_KEY))
                      {
                          handleSpecialText(ExternProtoDeclareSyncHelper2.PROTODECL_DOCUMENTATION_KEY);
                      }
                      else // add row
                      {
                          Element fieldElement = (Element) entry.getValue();
                          tableModel.addRow(new Object[]{
                              remoteFieldName, "", "", "", "", ""
                          });
                          int row = tableModel.getRowCount() - 1;
                          
                          org.jdom.Attribute attr;
                          attr = fieldElement.getAttribute(FIELD_ATTR_TYPE_NAME);
                          if(attr != null)
                              tableModel.setValueAt(attr.getValue(),row,TYPE_COLUMN);
                          attr = fieldElement.getAttribute(FIELD_ATTR_ACCESSTYPE_NAME);
                          if(attr != null)
                              tableModel.setValueAt(attr.getValue(),row,ACCESSTYPE_COLUMN);
                          attr = fieldElement.getAttribute(FIELD_ATTR_VALUE_NAME);
                          if(attr != null)
                              tableModel.setValueAt(attr.getValue(),row,VALUE_COLUMN);
                          attr = fieldElement.getAttribute(FIELD_ATTR_APPINFO_NAME);
                          if(attr != null)
                              tableModel.setValueAt(attr.getValue(),row,APPINFO_COLUMN);
                          attr = fieldElement.getAttribute(FIELD_ATTR_DOC_NAME);
                          if(attr != null)
                              tableModel.setValueAt(attr.getValue(),row,DOCUMENTATION_COLUMN);
                          // show last row after adding
                          fieldsTableList.getTable().changeSelection(fieldsTableList.getTable().getRowCount() - 1, 0, false, false);  
                      }
                  }
              }
          }
          // Rerender the table to show the errors, cellRenderer checks
          ((DefaultTableModel) fieldsTableList.getTable().getModel()).fireTableStructureChanged();
          enableVerifyUpdateButtons(getSelectedUrl() != null);
      });
    }
    else // !doVerifyOnly: Update field signatures
    {
      SwingUtilities.invokeLater(() -> {
          statusLabel.setText(statusResults);
          if (remoteProtoDeclareHashMap == null)
              return; // nothing to compare

          // Fill in the table with data from remoteProtoDeclareHashMap
          DefaultTableModel tableModel = (DefaultTableModel) fieldsTableList.getTable().getModel();

          int numRows = tableModel.getRowCount();
          ArrayList<String> missingRows = new ArrayList<>();  // in case

          Set<String> names = remoteProtoDeclareHashMap.keySet();  // get all names defined
          for (String s : names) {
              if(s.equals(ExternProtoDeclareSyncHelper2.PROTODECL_APPINFO_KEY) ||
                      s.equals(ExternProtoDeclareSyncHelper2.PROTODECL_DOCUMENTATION_KEY)) {
                  handleSpecialText(s,true);
                  continue;
              }
              int rowNum = findRow(tableModel, s);
              if (rowNum == -1)
                  missingRows.add(s);
              else {
                  Element el = (Element)remoteProtoDeclareHashMap.get(s);
                  for (int c = TYPE_COLUMN; c <= DOCUMENTATION_COLUMN; c++) {
                      if(c == VALUE_COLUMN)  // Don't to anything to value column
                          continue;
                      Attribute att = el.getAttribute(attributeNames[c]);
                      String attVal = att == null ? "" : att.getValue();
                      tableModel.setValueAt(escapeXmlCharacters(attVal), rowNum, c);
                  }
              }
          }

          for (String s : missingRows) {
              Element el = (Element)remoteProtoDeclareHashMap.get(s);
              tableModel.addRow((Object[]) null);
              tableModel.setValueAt(s, numRows, NAME_COLUMN);
              
              for (int c = TYPE_COLUMN; c <= DOCUMENTATION_COLUMN; c++) {
                  if(c == VALUE_COLUMN)  // Don't to anything to value column
                      continue;
                  Attribute att = el.getAttribute(attributeNames[c]);
                  String attVal = att == null ? "" : att.getValue();
                  tableModel.setValueAt(escapeXmlCharacters(attVal), numRows, c);
              }
              numRows++;    // numRows holds the idx of next new row
          }
          statusLabel.setText("Update complete");
          enableVerifyUpdateButtons(getSelectedUrl() != null);
          ((DefaultTableModel) fieldsTableList.getTable().getModel()).fireTableStructureChanged();
      });
    }
  }
  private void handleSpecialText(String key)
  {
    handleSpecialText(key,false);
  }

  private Color docDefaultForeground;
  private Color docDefaultBackground;
  private void handleSpecialText(String key, boolean replace)
  {
    if(docDefaultForeground == null)
      docDefaultForeground = appInfoTextArea.getForeground();
    if(docDefaultBackground == null)
      docDefaultBackground = appInfoTextArea.getBackground();

    String attrVal = (String)remoteProtoDeclareHashMap.get(key);
    if (key.equals(ExternProtoDeclareSyncHelper2.PROTODECL_APPINFO_KEY)) {
      if(!appInfoTextArea.getText().equals(attrVal)) {
        if(replace) {
          appInfoTextArea.setText(attrVal);
          appInfoTextArea.setForeground(docDefaultForeground);
          appInfoTextArea.setBackground(docDefaultBackground);
        }
        else {
          appInfoTextArea.setForeground(redColor);
          appInfoTextArea.setToolTipText(attrVal);
          if(appInfoTextArea.getText().length()<=0)
            appInfoTextArea.setBackground(redColor);
          else
            appInfoTextArea.setBackground(docDefaultBackground);
        }
      }
      else {
        appInfoTextArea.setForeground(docDefaultForeground);
        appInfoTextArea.setBackground(docDefaultBackground);
      }
    }
    else if (key.equals(ExternProtoDeclareSyncHelper2.PROTODECL_DOCUMENTATION_KEY)) {
       if(!documentationTF.getText().equals(attrVal)) {
         if(replace) {
           documentationTF.setText(attrVal);
           documentationTF.setForeground(docDefaultForeground);
           documentationTF.setBackground(docDefaultBackground);
         }
         else {
           documentationTF.setForeground(redColor);
           documentationTF.setToolTipText(attrVal);
           if(documentationTF.getText().length()<=0)
             documentationTF.setBackground(redColor);
           else
             documentationTF.setBackground(docDefaultBackground);
         }
       }
       else {
         documentationTF.setForeground(docDefaultForeground);
         documentationTF.setBackground(docDefaultBackground);
       }
   }
  }
  
  private int findRow(DefaultTableModel mod, String s)
  {
    int numRows = mod.getRowCount();
    for(int r=0;r<numRows;r++) {
      String nm = (String)mod.getValueAt(r, NAME_COLUMN);
      if(nm.equals(s))
        return r;
    }
    return -1;
  }
  private void enableVerifyUpdateButtons(boolean wh)
  {
    verifyFieldsButton.setEnabled(wh);
    updateFieldsButton.setEnabled(wh);
  }

  @Override
  public void statusIn(final String s)
  {
    SwingUtilities.invokeLater(() -> {
        statusLabel.setText(s);
    });
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        nameLab = new javax.swing.JLabel();
        nameTF = new javax.swing.JTextField();
        appInfoLab = new javax.swing.JLabel();
        appInfoScrollPane = new javax.swing.JScrollPane();
        appInfoTextArea = new javax.swing.JTextArea();
        documentationLabel = new javax.swing.JLabel();
        documentationTF = new javax.swing.JTextField();
        openDocumentationButton = new javax.swing.JButton();
        urlFieldsSplitter = new javax.swing.JSplitPane();
        urlTableList = new org.web3d.x3d.palette.items.UrlExpandableList2();
        fieldsTableList = new org.web3d.x3d.palette.items.ExpandableList();
        buttonPane = new javax.swing.JPanel();
        verifyFieldsButton = new javax.swing.JButton();
        updateFieldsButton = new javax.swing.JButton();
        statusLabel = new javax.swing.JLabel();
        appendProtoInstanceCheckBox = new javax.swing.JCheckBox();
        authorAssistLabel = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(1200, 800));
        setPreferredSize(new java.awt.Dimension(800, 600));
        setLayout(new java.awt.GridBagLayout());

        nameLab.setText("External prototype name");
        nameLab.setToolTipText("ExternProtoDeclare prototype name usually matches ProtoDeclare name, but can be different");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 10, 3, 3);
        add(nameLab, gridBagConstraints);

        nameTF.setToolTipText("ExternProtoDeclare prototype name usually matches ProtoDeclare name, but can be different");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 23);
        add(nameTF, gridBagConstraints);

        appInfoLab.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        appInfoLab.setText("appinfo");
        appInfoLab.setToolTipText("application information provides simple tooltip description");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(appInfoLab, gridBagConstraints);

        appInfoScrollPane.setAutoscrolls(true);
        appInfoScrollPane.setMinimumSize(new java.awt.Dimension(23, 30));
        appInfoScrollPane.setPreferredSize(new java.awt.Dimension(166, 30));

        appInfoTextArea.setColumns(20);
        appInfoTextArea.setLineWrap(true);
        appInfoTextArea.setRows(5);
        appInfoTextArea.setToolTipText("application information provides simple tooltip description");
        appInfoTextArea.setWrapStyleWord(true);
        appInfoTextArea.setMinimumSize(new java.awt.Dimension(4, 30));
        appInfoTextArea.setPreferredSize(new java.awt.Dimension(164, 30));
        appInfoScrollPane.setViewportView(appInfoTextArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 0.25;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 23);
        add(appInfoScrollPane, gridBagConstraints);

        documentationLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        documentationLabel.setText("documentation");
        documentationLabel.setToolTipText("url for prototype documentation");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(documentationLabel, gridBagConstraints);

        documentationTF.setToolTipText("url for prototype documentation");
        documentationTF.setPreferredSize(new java.awt.Dimension(350, 22));
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
        documentationTF.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                toolTipUpdater(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
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
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 23);
        add(openDocumentationButton, gridBagConstraints);

        urlFieldsSplitter.setBorder(null);
        urlFieldsSplitter.setDividerLocation(175);
        urlFieldsSplitter.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        urlFieldsSplitter.setMinimumSize(new java.awt.Dimension(31, 350));
        urlFieldsSplitter.setPreferredSize(new java.awt.Dimension(110, 350));

        urlTableList.setMaximumSize(new java.awt.Dimension(32769, 32769));
        urlTableList.setMinimumSize(new java.awt.Dimension(0, 140));
        urlTableList.setPreferredSize(new java.awt.Dimension(25, 140));
        urlTableList.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                urlTableListPropertyChange(evt);
            }
        });
        urlFieldsSplitter.setLeftComponent(urlTableList);

        fieldsTableList.setBorder(null);
        fieldsTableList.setMinimumSize(new java.awt.Dimension(162, 140));
        fieldsTableList.setPreferredSize(new java.awt.Dimension(100, 140));
        urlFieldsSplitter.setRightComponent(fieldsTableList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        add(urlFieldsSplitter, gridBagConstraints);

        verifyFieldsButton.setText("Verify field signatures using external .x3d declaration");
        verifyFieldsButton.setToolTipText("first select url address for .x3d scene with defining ProtoDeclare, then press button to check field info");
        verifyFieldsButton.setEnabled(false);
        verifyFieldsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                verifyFieldsButtonActionPerformed(evt);
            }
        });
        buttonPane.add(verifyFieldsButton);

        updateFieldsButton.setText("Update field signatures using external .x3d declaration");
        updateFieldsButton.setToolTipText("update field values using selected url address for external master .x3d ProtoDeclare that defines field signatures");
        updateFieldsButton.setEnabled(false);
        updateFieldsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateFieldsButtonActionPerformed(evt);
            }
        });
        buttonPane.add(updateFieldsButton);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        add(buttonPane, gridBagConstraints);

        statusLabel.setFont(new java.awt.Font("Dialog", 2, 12)); // NOI18N
        statusLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        statusLabel.setText("<html>&nbsp;");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(statusLabel, gridBagConstraints);

        appendProtoInstanceCheckBox.setText("append new example ProtoInstance");
        appendProtoInstanceCheckBox.setToolTipText("append new example ProtoInstance node matching the referenced ExternProtoDeclare definition");
        appendProtoInstanceCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                appendProtoInstanceCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(appendProtoInstanceCheckBox, gridBagConstraints);

        authorAssistLabel.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        authorAssistLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        authorAssistLabel.setText("Author-assist editing feature");
        authorAssistLabel.setToolTipText("Check boxes to append helpful additional content with this ProtoDeclare");
        authorAssistLabel.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 11, 0, 5);
        add(authorAssistLabel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void verifyFieldsButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_verifyFieldsButtonActionPerformed
    {//GEN-HEADEREND:event_verifyFieldsButtonActionPerformed
      String url = getSelectedGreenUrl();
      if ((url == null) || (!url.contains(".x3d")))
        return;
      doVerifyOnly = true; // do not overwrite
      statusLabel.setText("Verification in progress...");
      
      // reset row length to number of fields in original externProtoDeclare so that repeated invocation won't clear added rows
      redRowsStartIndex = externProtoDeclare.getFields().size();
      
      remoteProtoDeclareHashMap = null;
      
      // get remote ProtoDeclare fields list prior to firing comparisons during re-render
      externProtoDeclareSyncHelper = new ExternProtoDeclareSyncHelper2(xObj.getPrimaryFile(), url, this);
      
      ((DefaultTableModel) fieldsTableList.getTable().getModel()).fireTableStructureChanged();  // cause a redraw to update existing colors
      enableVerifyUpdateButtons(false);
      // note that checkDone gets invoked later by externProtoDeclareSyncHelper and occurs before rendering final results
}//GEN-LAST:event_verifyFieldsButtonActionPerformed

  private void toolTipUpdater(java.awt.event.KeyEvent evt)//GEN-FIRST:event_toolTipUpdater
  {//GEN-HEADEREND:event_toolTipUpdater
  }//GEN-LAST:event_toolTipUpdater

  private void updateFieldsButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_updateFieldsButtonActionPerformed
  {//GEN-HEADEREND:event_updateFieldsButtonActionPerformed
     String url = getSelectedGreenUrl();
     if ((url == null) || (!url.contains(".x3d")))
        return;
     doVerifyOnly = false; // overwrite
     statusLabel.setText("Update in progress...");
     remoteProtoDeclareHashMap = null;  // all black
     ((DefaultTableModel) fieldsTableList.getTable().getModel()).fireTableStructureChanged();  // cause a redraw to neutralize existing colors
     externProtoDeclareSyncHelper = new ExternProtoDeclareSyncHelper2(xObj.getPrimaryFile(), url, this);
     updateFieldsButton.setForeground(defaultForeground);
     enableVerifyUpdateButtons(false);
}//GEN-LAST:event_updateFieldsButtonActionPerformed

  private void urlTableListPropertyChange(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_urlTableListPropertyChange
  {//GEN-HEADEREND:event_urlTableListPropertyChange

  }//GEN-LAST:event_urlTableListPropertyChange

    private void openDocumentationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openDocumentationButtonActionPerformed
        if (documentationTF.getText().trim().isEmpty()) return;
        URL url;
        try 
        {
            url = new URL (documentationTF.getText().trim());
            URLDisplayer.getDefault().showURL(url);
        }
        catch (MalformedURLException e)
        {
            System.out.println ("Failed attempt to launch documentation" + documentationTF.getText().trim());
        }
    }//GEN-LAST:event_openDocumentationButtonActionPerformed

    private void documentationTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_documentationTFActionPerformed
        openDocumentationButton.setEnabled(!documentationTF.getText().trim().isEmpty()); // didn't test for url since address might be relative
    }//GEN-LAST:event_documentationTFActionPerformed

    private void appendProtoInstanceCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_appendProtoInstanceCheckBoxActionPerformed
        // handled when serializing
    }//GEN-LAST:event_appendProtoInstanceCheckBoxActionPerformed

    private void documentationTFPropertyChange(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_documentationTFPropertyChange
    {//GEN-HEADEREND:event_documentationTFPropertyChange
        openDocumentationButton.setEnabled(!documentationTF.getText().trim().isEmpty()); // didn't test for url since address might be relative
    }//GEN-LAST:event_documentationTFPropertyChange
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel appInfoLab;
    private javax.swing.JScrollPane appInfoScrollPane;
    private javax.swing.JTextArea appInfoTextArea;
    private javax.swing.JCheckBox appendProtoInstanceCheckBox;
    private javax.swing.JLabel authorAssistLabel;
    private javax.swing.JPanel buttonPane;
    private javax.swing.JLabel documentationLabel;
    private javax.swing.JTextField documentationTF;
    private org.web3d.x3d.palette.items.ExpandableList fieldsTableList;
    private javax.swing.JLabel nameLab;
    private javax.swing.JTextField nameTF;
    private javax.swing.JButton openDocumentationButton;
    private javax.swing.JLabel statusLabel;
    private javax.swing.JButton updateFieldsButton;
    private javax.swing.JSplitPane urlFieldsSplitter;
    private org.web3d.x3d.palette.items.UrlExpandableList2 urlTableList;
    private javax.swing.JButton verifyFieldsButton;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_EXTERNPROTODECLARE";
  }

  @Override
  public void unloadInput()
  {
    externProtoDeclare.setName(nameTF.getText().trim());
    urlTableList.checkUrlValues();
    externProtoDeclare.setUrls(urlTableList.getUrlData());
        
    String[][] data = fieldsTableList.getData();
    if(data.length>0) {
      ArrayList <FIELD> v = new ArrayList <>(data.length);
      for(String[] sa : data) {
        if((sa[NAME_COLUMN] != null) && (sa[NAME_COLUMN].length() > 0)) {
          FIELD f = new FIELD();
          f.setName      (sa[NAME_COLUMN]);
          f.setType      (sa[TYPE_COLUMN]);
          f.setAccessType(sa[ACCESSTYPE_COLUMN]);
          if (removeFieldInitializationValues)
          {
              f.setValue(""); // force removal of value data
          }
          else
          {
              f.setValue(escapeXmlCharacters(sa[VALUE_COLUMN].trim()));// retain illegal value data per author's direction
          }
          f.setAppinfo      (escapeXmlCharacters(sa[APPINFO_COLUMN].trim()));
          f.setDocumentation(escapeXmlCharacters(sa[DOCUMENTATION_COLUMN].trim()));
          f.setParent       (EXTERNPROTODECLARE_ELNAME);
          v.add(f);
          // ExternProtoDeclare fields are not allowed to have value attribues or children
          // TODO issue warning
        }
      }
      externProtoDeclare.setFields(v);
    }
    externProtoDeclare.setAppinfo      (escapeXmlCharacters(appInfoTextArea.getText().trim()));
    externProtoDeclare.setDocumentation(escapeXmlCharacters(documentationTF.getText().trim()));
    externProtoDeclare.setInsertCommas    (fieldsTableList.isInsertCommasSet());
    externProtoDeclare.setInsertLineBreaks(fieldsTableList.isInsertLineBreaksSet());

      // =============================================
      // special feature, triggered from GUI checkbox:
      // append custom ProtoInstance with matching fieldValue declarations
      if (appendProtoInstanceCheckBox.isSelected())
      {
          StringBuilder protoInstance = new StringBuilder();
          protoInstance.append("\n");
          protoInstance.append("    "); // TODO use proper tab setting from Netbeans
          protoInstance.append("<ProtoInstance name='").append(externProtoDeclare.getName()).append("'");
          protoInstance.append(">\n");

          for (String[] sa : data)
          {
              if (sa[NAME_COLUMN] != null && sa[NAME_COLUMN].length() > 0 &&
                (!sa[TYPE_COLUMN].equals("SFNode") && !sa[TYPE_COLUMN].equals("MFNode")) &&
                 (sa[ACCESSTYPE_COLUMN].equals("initializeOnly") || sa[ACCESSTYPE_COLUMN].equals("inputOutput")) &&
                  sa[TYPE_COLUMN].startsWith("SF"))
              {
                  protoInstance.append("      "); // TODO use proper tab setting from Netbeans
                  protoInstance.append("<fieldValue ").append("name='").append(sa[NAME_COLUMN]).append("' ").append("value='");
                  // default values not listed in ExternProtoDeclare, use type defaults instead
                  // TODO read Proto if available and use those defaults instead; that would be good feature for interface too
                  switch (sa[TYPE_COLUMN]) {
                      case "SFBool":
                          protoInstance.append("0");
                          break;
                      case "SFInt32":
                          protoInstance.append("0");
                          break;
                      case "SFFloat":
                          protoInstance.append("0.0");
                          break;
                      case "SFDouble":
                          protoInstance.append("0.0");
                          break;
                      case "SFVec2f":
                          protoInstance.append("0 0");
                          break;
                      case "SFVec2d":
                          protoInstance.append("0 0");
                          break;
                      case "SFVec3f":
                          protoInstance.append("0 0 0");
                          break;
                      case "SFVec3d":
                          protoInstance.append("0 0 0");
                          break;
                      case "SFVec4f":
                          protoInstance.append("0 0 0 0");
                          break;
                      case "SFVec4d":
                          protoInstance.append("0 0 0 0");
                          break;
                      case "SFColor":
                          protoInstance.append("0 0 0");
                          break;
                      case "SFColorRGBA":
                          protoInstance.append("0 0 0 0");
                          break;
                      case "SFRotation":
                          protoInstance.append("0 1 0 0");
                          break;
                      case "SFImage":
                          protoInstance.append("0 0 0");
                          break;
                      case "SFTime":
                          protoInstance.append("-1");
                          break;
                      default:
                          break;
                  }
                  // otherwise (e.g. string and MF values) empty
                  protoInstance.append("'");

                  // do not include appinfo or type:  they are illegal in ProtoInstance fieldValue declarations
                  protoInstance.append("/>\n");
              }
          }
          protoInstance.append("    "); // TODO use proper tab setting from Netbeans
          protoInstance.append("</ProtoInstance>\n");
          protoInstance.append("    "); // TODO use proper tab setting from Netbeans
          protoInstance.append("<!-- Add any ROUTEs here that connect ProtoInstance to/from prior nodes in Scene (and outside of ProtoDeclare) -->\n");
          externProtoDeclare.setAppendedContent(protoInstance.toString()); // follows ProtoDeclare
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
/*
  original version; see below
  class myDefaultStringRenderer extends DefaultTableCellRenderer
  {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
      Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      boolean enabled = true;
      if(column == VALUE_COLUMN) {
        enabled = FIELD.canHaveValue((String)table.getValueAt(row, ACCESSTYPE_COLUMN));
      }
      c.setEnabled(enabled);
      return c;
    }   
  }
*/
  private HashMap<String,Object> remoteProtoDeclareHashMap;

  class NewDefaultTableCellRenderer extends DefaultTableCellRenderer
  {
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
    {
      Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
      if(defaultForeground == null)
        defaultForeground = c.getForeground();
      if(defaultBackground == null)
        defaultBackground = c.getBackground();

      // first set background
      if (column == VALUE_COLUMN)
      {
        c.setEnabled(false);
        if (value.toString().length() > 0)
        {
            c.setBackground(redColor);
            setStatus("field initialization values are illegal inside ExternProtoDeclare");
            if (doVerifyOnly)
			{
				updateFieldsButton.setForeground(greenColor);
			}
        }
        else
        {
            c.setBackground(greyColor);
        }
        return c;
      }
      else
      {
        c.setEnabled(true);
        c.setBackground(defaultBackground);
      }
      
      // background of added rows gets marked red, even if author added some rows before checking, in order to avoid reset upon repeated checks
      if ((doVerifyOnly == true) && (row >= redRowsStartIndex)) // only when verifying, not when replacing
      {
            c.setBackground(redColor);
            if (doVerifyOnly)
			{
				updateFieldsButton.setForeground(greenColor);
			}
      }
      // now set cell text colors
      if (remoteProtoDeclareHashMap != null)  // Have we gotten some result from checking?
      {
        String fieldName = (String) table.getValueAt(row, NAME_COLUMN);
        Element remoteFieldElement = (Element)remoteProtoDeclareHashMap.get(fieldName);
        if (column == NAME_COLUMN) // &&  ! && fieldName.equalsIgnoreCase(remoteFieldElement.toString()))
        {
            if ((remoteFieldElement != null) && remoteProtoDeclareHashMap.containsKey(fieldName))
            {
                // Here if we found a field with the name specified
                if(c instanceof JLabel)
                  c.setForeground(defaultForeground);
                return c;
            }
            else if (remoteFieldElement == null) // check for case mismatch, if found re-color and return
            {
                  Iterator<HashMap.Entry<String, Object>> entriesIterator = remoteProtoDeclareHashMap.entrySet().iterator();
                  while (entriesIterator.hasNext())
                  {
                      Map.Entry<String, Object> entry = entriesIterator.next();
                      String remoteFieldName = entry.getKey();
                      if (remoteFieldName.equalsIgnoreCase(fieldName))
                      {
                          if (c instanceof JLabel)
                          {
                            ((JLabel) c).setForeground(yellowColor);
                            setStatus("Mismatched capitalization of field name");
                            if (doVerifyOnly)
							{
								updateFieldsButton.setForeground(greenColor);
							}
                            return c;
                          }
                      }
                  }
             }
            // name not found in any form
            if (c instanceof JLabel)
            {
              ((JLabel) c).setForeground(redColor);
              setStatus("No such field name");
              if (doVerifyOnly)
			  {
				  updateFieldsButton.setForeground(greenColor);
			  }
              return c;
            }
        }
        // now other field attributes besides NAME
        if (remoteFieldElement == null) 
        {
            // nothing found to check against
            if (c instanceof JLabel)
            {
              ((JLabel) c).setForeground(redColor);
              if (doVerifyOnly)
			  {
				  updateFieldsButton.setForeground(greenColor);
			  }
              return c;
            }
        }
        return checkField(c, table, row, column, remoteFieldElement);
      }
      // Here if we haven't gotten back results in remoteProtoDeclareHashMap
      return c;
    }

    private Component checkField(Component c, JTable table, int row, int col, Element jdomEl)
    {
      Attribute typeAttr = jdomEl.getAttribute(attributeNames[col]);
      String typeAttrVal = typeAttr==null?"":typeAttr.getValue();
      String localType = (String) table.getValueAt(row, col);
      typeAttrVal = typeAttrVal.trim();
      localType = localType.trim();
      Color foregroundColor = defaultForeground;
      if (!escapeXmlCharacters(localType).equals(escapeXmlCharacters(typeAttrVal)))
      {
        setStatus("Error found");
        foregroundColor = redColor;
        if(localType.length()<=0)
          c.setBackground(redColor);
        if (c instanceof JLabel)
          ((JLabel) c).setToolTipText(typeAttrVal);
      }
      if (c instanceof JLabel)
      {
        ((JLabel) c).setForeground(foregroundColor);
      }
      return c;
    }
  }

  private void setStatus(String s)
  {
    // if there is somewhere on the panel to report this, do it here
  }

  class NewDefaultCellEditor extends DefaultCellEditor
  {
    public NewDefaultCellEditor()
    {
      super(new JTextField());
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column)
    {
      Component c = super.getTableCellEditorComponent(table, value, isSelected, row, column);
      c.setEnabled(true);
      if(column == VALUE_COLUMN) {
        if(!FIELD.canHaveValue((String)table.getValueAt(row, TYPE_COLUMN),(String)table.getValueAt(row, ACCESSTYPE_COLUMN)))
         c.setEnabled(false);
      }
      return c;
    }   
  }
}
