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

import javax.swing.JCheckBox;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.TableColumnModel;
import javax.swing.text.JTextComponent;
import org.jdom.Attribute;
import org.jdom.Element;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.HelpCtx;
import org.web3d.x3d.palette.items.ExpandableList.IsCellEditableIf;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * PROTOINSTANCECustomizer.java
 * Created on Mar 14, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class PROTOINSTANCECustomizer extends BaseCustomizer implements IsCellEditableIf
{
  private PROTOINSTANCE protoInstance;
  private JTextComponent target;

  private String[] protoInstanceNames = { "insert prototype name matching prior declaration" };
  
  private final int OVERRIDE_COLUMN   = 0;
  private final int NAME_COLUMN       = 1;
  private final int TYPE_COLUMN       = 2;
  private final int ACCESSTYPE_COLUMN = 3;
  private final int VALUE_COLUMN      = 4;
  
  private final int NUM_COLUMNS       = 5;
  
  private List<FIELDVALUE> legalFieldValues;
  private List<FIELDVALUE> extraFieldValues = new Vector<>();
  
  public PROTOINSTANCECustomizer(PROTOINSTANCE protoInstance, JTextComponent target)
  {
    super(protoInstance);
    this.protoInstance = protoInstance;
    this.target = target;
    
    HelpCtx.setHelpIDString(this, "PROTOINSTANCE_ELEM_HELPID");
    
    initComponents();
    String protoDeclarationNote = " (as defined in corresponding ProtoDeclare or ExternProtoDeclare)";
    String[] columnTooltips = new String[]{"whether to override default value",
                                           FIELD_ATTR_TOOLTIPS[0] + protoDeclarationNote,
                                           FIELD_ATTR_TOOLTIPS[1] + protoDeclarationNote,
                                           FIELD_ATTR_TOOLTIPS[2] + protoDeclarationNote,
                                           FIELD_ATTR_TOOLTIPS[3] + protoDeclarationNote};
    fieldValueList.setColumnTitles(new String[]{"override","name","type","accessType","value"});
    fieldValueList.setColumnToolTips(columnTooltips);
    fieldValueList.setNewRowData(new Object[]{Boolean.TRUE,"","","",""}); // empty values are best, let author enter text or use selections

    fieldValueList.setColumnEditor(new DefaultCellEditor(new JCheckBox()),                                  OVERRIDE_COLUMN);
    fieldValueList.setColumnEditor(new DefaultCellEditor(new JComboBox<>(FIELD_ATTR_TYPE_CHOICES)),             TYPE_COLUMN);
    fieldValueList.setColumnEditor(new DefaultCellEditor(new JComboBox<>(FIELD_ATTR_ACCESSTYPE_CHOICES)), ACCESSTYPE_COLUMN);
    fieldValueList.setReadOnlyColumn(TYPE_COLUMN);
    fieldValueList.setReadOnlyColumn(ACCESSTYPE_COLUMN);
	
	// fillProtoDeclareNodeComboBox handles USE node to find corresponding ProtoInstance DEF
	// and thus name to assign in nameComboBox
    fillProtoDeclareNodeComboBox();

    List<FIELDVALUE> fvlis = getFieldValues();

    legalFieldValues = buildLegalFieldValueList(fvlis); // unused
    setInitialFieldValues(fvlis);
    
    fieldValueList.setCellEditableHandler(this);

    customizeColumnWidths();

    setDefaultDEFname ();
  }
  private void setDefaultDEFname ()
  {
     String protoName = protoDeclareNameComboBox.getSelectedItem().toString();
     
     if (protoName.length() > 0) super.getDEFUSEpanel().setDefaultDEFname(protoName + "Instance");
  }

  // Sets init. column width, doesn't change autoresize mode, normally scrollbar not used
  private void customizeColumnWidths()
  {
    TableColumnModel colMod = fieldValueList.getTable().getColumnModel();

    colMod.getColumn(NAME_COLUMN).setPreferredWidth(130);
    colMod.getColumn(TYPE_COLUMN).setPreferredWidth(70);
    colMod.getColumn(ACCESSTYPE_COLUMN).setPreferredWidth(90);
    colMod.getColumn(VALUE_COLUMN).setPreferredWidth(120);
  }
  
  private HashMap<String,String> originalFieldValueNodeContent;
  
  private List<FIELDVALUE> getFieldValues()
  {
    List<FIELDVALUE> lis = protoInstance.getFieldValues();
    originalFieldValueNodeContent = new HashMap<>(lis.size());
    for(FIELDVALUE fv : lis)
      this.originalFieldValueNodeContent.put(fv.getName(), fv.getContent());
    return lis;
  }
  
  private List<FIELDVALUE> buildLegalFieldValueList(List<FIELDVALUE> ls)
  {
    Vector<FIELDVALUE> vec = new Vector<>(ls.size());
    
    for(FIELDVALUE fv : ls) {
      String accessType = fv.getAccessType();
      String type = fv.getType();
      if(accessType != null && canHaveValue(accessType, type))
        vec.add(fv);
    }
    return vec;
  }
  
  // Called from combobox switch
  private void setTableData()
  {
    Vector<FIELDVALUE> vec = new Vector<>();
    vec.addAll(extraFieldValues);
    vec.addAll(legalFieldValues);
    
    Object[][] oaa = new Object[vec.size()][];
    int i=0;
    for(FIELDVALUE fv : vec) {
      Object[] oa = new Object[NUM_COLUMNS];
      oa[OVERRIDE_COLUMN]   = false;
      oa[NAME_COLUMN]       = fv.getName();
      oa[TYPE_COLUMN]       = fv.getType();
      oa[ACCESSTYPE_COLUMN] = fv.getAccessType();
      oa[VALUE_COLUMN]      = fv.getValue();
      oaa[i++] = oa; 
    }
    fieldValueList.setData(oaa);
  }
        
  private boolean canHaveValue(String accessType, String type)
  {
    if(accessType.contains("inputOnly")||
       accessType.contains("outputOnly")||
       type.equals("SFNode")||
       type.equals("MFNode"))
      return false;
    return true;
  }
  
  // Called on entry when editing existing node
  private void setInitialFieldValues(List<FIELDVALUE> passedInFieldValues)
  {
    // Combobox has already been hit so table already loaded w/ legal values  
    String[][] loadedTableData = fieldValueList.getData();  // legal vals
    
    Vector<FIELDVALUE> additionalFieldValues = new Vector<>();
    
    for(String[] row : loadedTableData)
      row[OVERRIDE_COLUMN] = "false"; // converted to Boolean.FALSE when set below
    
    for(FIELDVALUE fldVal : passedInFieldValues) {
      boolean found = false;
      for(String[] row : loadedTableData) {
        if(fldVal.getName().equals(unmangleName(row[NAME_COLUMN]))) {
          found = true;
          row[OVERRIDE_COLUMN] = "true"; // converted to Boolean.TRUE when set below
          row[VALUE_COLUMN] = fldVal.getValue();
          break;
        }
      }
      if(!found) {
        additionalFieldValues.add(fldVal);
        extraFieldValues.add(fldVal);  // for next combobox swap
      }
    }
    
    Object[][] oa = new Object[Math.max(loadedTableData.length+additionalFieldValues.size(),1)][NUM_COLUMNS]; // no less than 1
    
    int oRow = 0;
    for(FIELDVALUE fieldValue : additionalFieldValues)
    {
      if (oa[oRow] == null)
      {
          System.out.println ("Error in ProtoInstanceCustomizer setInitialFieldValues additionalFieldValues");
          break;
      }
      oa[oRow][OVERRIDE_COLUMN]   = Boolean.TRUE;
      oa[oRow][NAME_COLUMN]       = fieldValue.getName();
      oa[oRow][TYPE_COLUMN]       = fieldValue.getType();
      oa[oRow][ACCESSTYPE_COLUMN] = fieldValue.getAccessType();
      oa[oRow][VALUE_COLUMN]      = fieldValue.getValue();
      oRow++;
    }
    for(String[]row : loadedTableData)
    {
      if (oa[oRow] == null)
      {
          System.out.println ("Error in ProtoInstanceCustomizer setInitialFieldValues loadedTableData");
          break;
      }
      oa[oRow][OVERRIDE_COLUMN]   = Boolean.valueOf(row[OVERRIDE_COLUMN]);
      oa[oRow][NAME_COLUMN]       = row[NAME_COLUMN];
      oa[oRow][TYPE_COLUMN]       = row[TYPE_COLUMN];
      oa[oRow][ACCESSTYPE_COLUMN] = row[ACCESSTYPE_COLUMN];
      oa[oRow][VALUE_COLUMN]      = row[VALUE_COLUMN];
      oRow++;
    }    
    fieldValueList.setData(oa);
  }
  
  /**
   * Get the node list into the combo box, alphabetizing
   */
  @SuppressWarnings("unchecked")  
  private void fillProtoDeclareNodeComboBox()
  {
    nodeElem selected = null;
    Vector<Element> sceneNodeList = protoInstance.getNodeList(target);
    if(sceneNodeList.size() <= 0)
      return;
    Vector<nodeElem> vectorNodeElements = new Vector<>(sceneNodeList.size()); // at most
	String USEvalue = new String(); // TODO
	if (protoInstance.isUSE())
		USEvalue = protoInstance.getDEFUSEvalue();
    for(Element nextElement : sceneNodeList) 
	{
      nodeElem newNodeElement = new nodeElem(nextElement); 
      if(nextElement.getName().equalsIgnoreCase(PROTODECLARE_ELNAME) ||
         nextElement.getName().equalsIgnoreCase(EXTERNPROTODECLARE_ELNAME))
	  {
		// found ProtoDeclare or ExternProtoDeclare, add it to list
        vectorNodeElements.add(newNodeElement);
        if (newNodeElement.toString().equals(protoInstance.getProtoDeclareName()))
            selected = newNodeElement;     
      }
	  else if ( nextElement.getName().equals(PROTOINSTANCE_ELNAME)    &&
			   (nextElement.getAttributeValue("DEF") != null)         && 
			    protoInstance.isUSE() &&  !USEvalue.isEmpty()         &&
			    nextElement.getAttributeValue("DEF").equals(USEvalue) &&
			    protoInstance.getProtoDeclareName().isEmpty())
	  {
		  protoInstance.setProtoDeclareName(nextElement.getAttributeValue(PROTOINSTANCE_ATTR_NAME_NAME));
          selected = newNodeElement;     
	  }
    }
	if (!vectorNodeElements.contains(protoInstance.getProtoDeclareName()))
	{
		vectorNodeElements.add (new nodeElem(protoInstance.getProtoDeclareName()));
	}
		 
    if(vectorNodeElements.size() <= 0)
      return;
    
    Collections.sort(vectorNodeElements);   
    protoDeclareNameComboBox.setModel(new DefaultComboBoxModel(vectorNodeElements));
    if(selected != null)
      protoDeclareNameComboBox.setSelectedItem(selected);
    else
      protoDeclareNameComboBox.setSelectedIndex(0);
  }

  public static class nodeElem implements Comparable
  {
    Element el;
    Attribute nameAttr;
    nodeElem(Element e)
    {
      el = e;
      nameAttr = el.getAttribute("name");
    }
    nodeElem(String s)
    {
      el = new Element (s);
	  el.setAttribute("name",s);
      nameAttr = el.getAttribute("name");
    }
    @Override
    public String toString()
    {
      return (nameAttr == null)?"":nameAttr.getValue();
     }

    @Override
    public int compareTo(Object o)
    {
      return toString().compareTo(o.toString());
    }   

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
        protoDeclareNameComboBox = new javax.swing.JComboBox<>();
        referencedPrototypeLabel = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        fieldValueLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        fieldValueList = new org.web3d.x3d.palette.items.ExpandableList();
        nodeHintPanel = new javax.swing.JPanel();
        childrenLabel = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 3, 3, 3);
        add(dEFUSEpanel1, gridBagConstraints);

        protoDeclareNameComboBox.setEditable(true);
        protoDeclareNameComboBox.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        protoDeclareNameComboBox.setModel(new DefaultComboBoxModel<String>(protoInstanceNames));
        protoDeclareNameComboBox.setToolTipText("select from list of ProtoDeclare and ExternProtoDeclare definitions available in this scene");
        protoDeclareNameComboBox.setMinimumSize(new java.awt.Dimension(200, 18));
        protoDeclareNameComboBox.setPreferredSize(new java.awt.Dimension(200, 20));
        protoDeclareNameComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                protoDeclareNameComboBoxActionPerformed(evt);
            }
        });
        protoDeclareNameComboBox.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                protoDeclareNameComboBoxPropertyChange(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 120;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 3, 3);
        add(protoDeclareNameComboBox, gridBagConstraints);

        referencedPrototypeLabel.setText("<html>Prototype <b>name</b>");
        referencedPrototypeLabel.setToolTipText("name is required.  Select from list of ProtoDeclare and ExternProtoDeclare definitions available in this scene.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 3, 3);
        add(referencedPrototypeLabel, gridBagConstraints);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Note that ProtoInstance USE output does not include name");
        jLabel1.setToolTipText("Note that ProtoInstance USE output does not include name");
        jLabel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 10, 3);
        add(jLabel1, gridBagConstraints);

        fieldValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fieldValueLabel.setText("fieldValue initializations:");
        fieldValueLabel.setToolTipText("fieldValue initializations override default values within a prototype");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 3, 0, 3);
        add(fieldValueLabel, gridBagConstraints);

        jScrollPane1.setBorder(null);

        fieldValueList.setBorder(null);
        fieldValueList.setMaximumSize(null);
        fieldValueList.setPreferredSize(new java.awt.Dimension(100, 120));
        jScrollPane1.setViewportView(fieldValueList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(jScrollPane1, gridBagConstraints);

        nodeHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nodeHintPanel.setLayout(new java.awt.GridBagLayout());

        childrenLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        childrenLabel.setText("<html><b>ProtoInstance</b> creates and initializes a new X3D node matching the prototype declaration");
        childrenLabel.setToolTipText("ProtoInstance nodes can be treated like any other node of the same type");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 6);
        nodeHintPanel.add(childrenLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nodeHintPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
 
    @SuppressWarnings("unchecked")
    private void protoDeclareNameComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_protoDeclareNameComboBoxActionPerformed
    {//GEN-HEADEREND:event_protoDeclareNameComboBoxActionPerformed
      nodeElem nd = (nodeElem)protoDeclareNameComboBox.getSelectedItem();
      Vector<FIELDVALUE> fldVec = new Vector<>();
      
      Element fieldHolderElem = nd.el;
      if(nd.el.getName().equalsIgnoreCase(PROTODECLARE_ELNAME))
        fieldHolderElem = nd.el.getChild(PROTOINTERFACE_ELNAME);
      
      if(fieldHolderElem != null) {
        List<Element> flds = (List<Element>)fieldHolderElem.getChildren(FIELD_ELNAME);
        for(Element e : flds) {
          Attribute attr = e.getAttribute(FIELD_ATTR_NAME_NAME);       
          String nm = (attr == null)?"":attr.getValue();
          attr = e.getAttribute(FIELD_ATTR_VALUE_NAME);
          String val = (attr == null)?"":attr.getValue();
          attr = e.getAttribute(FIELD_ATTR_TYPE_NAME);
          String typ = (attr == null)?"":attr.getValue();
          attr = e.getAttribute(FIELD_ATTR_ACCESSTYPE_NAME);
          String acc = (attr == null)?"":attr.getValue();
          fldVec.add(new FIELDVALUE(nm,val,typ,acc));
        }
      }
      legalFieldValues = buildLegalFieldValueList(fldVec);
      setTableData();
      
      setDefaultDEFname ();
}//GEN-LAST:event_protoDeclareNameComboBoxActionPerformed

    private void protoDeclareNameComboBoxPropertyChange(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_protoDeclareNameComboBoxPropertyChange
    {//GEN-HEADEREND:event_protoDeclareNameComboBoxPropertyChange
        setDefaultDEFname ();
    }//GEN-LAST:event_protoDeclareNameComboBoxPropertyChange
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel childrenLabel;
    private org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel1;
    private javax.swing.JLabel fieldValueLabel;
    private org.web3d.x3d.palette.items.ExpandableList fieldValueList;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel nodeHintPanel;
    private javax.swing.JComboBox<String> protoDeclareNameComboBox;
    private javax.swing.JLabel referencedPrototypeLabel;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_PROTOINSTANCE";
  }

  @Override
  public void unloadInput()
  {
    unLoadDEFUSE();
        
    protoInstance.setProtoDeclareName(protoDeclareNameComboBox.getSelectedItem().toString().trim());
    
    String[][] data = fieldValueList.getData();
    if(data.length>0) {
      Vector<FIELDVALUE> fieldValuesVector = new Vector<>(data.length);
      for(String[] sa : data) {
        boolean yn = Boolean.parseBoolean(sa[OVERRIDE_COLUMN]);
        if(yn) {
          FIELDVALUE fieldValue = new FIELDVALUE();
          fieldValue.setName(unmangleName(sa[NAME_COLUMN]));
          if(canHaveValue(sa[ACCESSTYPE_COLUMN],sa[TYPE_COLUMN]))
          {
              if      (sa[TYPE_COLUMN].equals("SFBool") && sa[VALUE_COLUMN].equals("TRUE"))
              {
                  fieldValue.setValue("true");
                  NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                     "<html><p align='center'>Illegal initialization value found for SFBool fieldValue <b>" + fieldValue.getName() +
                     "</b>, reset TRUE to <b>true</b></p>" +
                     "<br /> <p align='center'>(Note that upper-case &apos;TRUE&apos; is ClassicVRML .x3dv syntax, lower-case &apos;true&apos; is XML .x3d syntax)</p>", NotifyDescriptor.WARNING_MESSAGE);
                  DialogDisplayer.getDefault().notify(descriptor);
              }
              else if (sa[TYPE_COLUMN].equals("SFBool") && sa[VALUE_COLUMN].equals("FALSE"))
              {
                  fieldValue.setValue("false");
                  NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                     "<html><p align='center'>Illegal initialization value found for SFBool fieldValue <b>" + fieldValue.getName() +
                     "</b>, reset FALSE to <b>false</b></p>" +
                     "<br /> <p align='center'>(Note that upper-case &apos;FALSE&apos; is ClassicVRML .x3dv syntax, lower-case &apos;false&apos; is XML .x3d syntax)</p>", NotifyDescriptor.WARNING_MESSAGE);
                  DialogDisplayer.getDefault().notify(descriptor);
              }
              else if (sa[TYPE_COLUMN].equals("MFString") && (sa[VALUE_COLUMN].length() > 0) && !sa[VALUE_COLUMN].contains("\""))
              {
                  fieldValue.setValue("\"" + sa[VALUE_COLUMN] + "\"");
                  NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                     "<html><p align='center'>fieldValue <b>" + fieldValue.getName() +
                     "</b> of type MFString must have quoted value</p>" +
                     "<br /> <p align='center'>Quotes added to create a proper single-string value</p>", NotifyDescriptor.WARNING_MESSAGE);
                  DialogDisplayer.getDefault().notify(descriptor);
              }
              else if (sa[VALUE_COLUMN].length() > 0)        fieldValue.setValue(sa[VALUE_COLUMN]);  // initialization value found, so use it
              // otherwise no initialization value found, so set to default value and then inform author
              else if (sa[TYPE_COLUMN].equals("SFBool"))     fieldValue.setValue("true");
              else if (sa[TYPE_COLUMN].equals("SFInt32"))    fieldValue.setValue("0");
              else if (sa[TYPE_COLUMN].equals("SFFloat"))    fieldValue.setValue("0.0");
              else if (sa[TYPE_COLUMN].equals("SFDouble"))   fieldValue.setValue("0.0");
              else if (sa[TYPE_COLUMN].equals("SFTime"))     fieldValue.setValue("-1");
              else if (sa[TYPE_COLUMN].equals("SFImage"))    fieldValue.setValue("0 0 0");
              else if (sa[TYPE_COLUMN].equals("SFRotation")) fieldValue.setValue("0 1 0 0");
              else if (sa[TYPE_COLUMN].equals("SFVec2f")     || sa[TYPE_COLUMN].equals("SFVec2d"))    fieldValue.setValue("0.0 0.0");
              else if (sa[TYPE_COLUMN].equals("SFColor")     ||
                       sa[TYPE_COLUMN].equals("SFVec3f")     || sa[TYPE_COLUMN].equals("SFVec3d"))    fieldValue.setValue("0.0 0.0 0.0");
              else if (sa[TYPE_COLUMN].equals("SFColorRGBA"))                                               fieldValue.setValue("0 0 0 0");
              else if (sa[TYPE_COLUMN].equals("SFVec4f")     || sa[TYPE_COLUMN].equals("SFVec4d"))    fieldValue.setValue("0 0 0 1");
              else if (sa[TYPE_COLUMN].equals("SFMatrix3f")  || sa[TYPE_COLUMN].equals("SFMatrix3d")) fieldValue.setValue("1 0 0 0 1 0 0 0 1");
              else if (sa[TYPE_COLUMN].equals("SFMatrix4f")  || sa[TYPE_COLUMN].equals("SFMatrix4d")) fieldValue.setValue("1 0 0 0 0 1 0 0 0 0 1 0 0 0 0 1");

              if ((sa[VALUE_COLUMN].length() == 0) && sa[TYPE_COLUMN].startsWith("SF") && !sa[TYPE_COLUMN].equals("SFString"))
              {
                  NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                     "<html><p align='center'>No initialization value found for fieldValue <b>" + fieldValue.getName() + 
                     "</b>, reset to " + sa[TYPE_COLUMN] + " type default value <b>" + fieldValue.getValue() + "</b></p>" +
                     "<br /> <p align='center'>(Note that default values for each fieldValue are defined in original ProtoDeclare field declaration)</p>", NotifyDescriptor.WARNING_MESSAGE);
                  DialogDisplayer.getDefault().notify(descriptor);
              }
          }
          else fieldValue.setValue("");
          fieldValue.setContent(originalFieldValueNodeContent.get(fieldValue.getName()));
          
          fieldValuesVector.add(fieldValue); // add latest fieldValue to vector
        }
      }
     protoInstance.setFieldValues(fieldValuesVector);
    }
  }

  @Override
  public boolean isCellEditable(int row, int column)
  {
    JTable tab = fieldValueList.getTable();
    if(column == VALUE_COLUMN)
      return canHaveValue(tab.getValueAt(row, ACCESSTYPE_COLUMN).toString(), tab.getValueAt(row, TYPE_COLUMN).toString());
    else if(column == ACCESSTYPE_COLUMN)
      return false;
    else if(column == TYPE_COLUMN)
      return false;
    else return true; // for boolean and name
  }

  private String unmangleName(String s)
  {
    s = s.replace("<html>", "");
    s = s.replaceFirst("\\x28.*\\x29", "");  // parens surrounding anything
    return s.trim();
  }
}
