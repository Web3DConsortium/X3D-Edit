/*
Copyright (c) 1995-2024 held by the author(s).  All rights reserved.
 
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
      (https://www.nps.edu and https://MovesInstitute.nps.edu)
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
import java.awt.Dialog;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFDouble;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFFloat;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFInt32;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * HANIMHUMANOIDCustomizer.java
 * Created on 30 May 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class HANIMHUMANOIDCustomizer extends BaseCustomizer
{
  private HANIMHUMANOID hAnimHumanoid;
  private JTextComponent target;
  
  private boolean translationScaleApplied, centerScaleApplied = false;
  private String  translationXoriginal, translationYoriginal, translationZoriginal;
  private String       centerXoriginal,      centerYoriginal,      centerZoriginal;
  
  private String  localPrefix = new String();
  
    /**
     * Creates new form HANIMHUMANOIDCustomizer
     * @param humanoid data of interest
     * @param target Swing component of interest */
    public HANIMHUMANOIDCustomizer(HANIMHUMANOID humanoid, JTextComponent target)
  {
    super(humanoid);
    this.hAnimHumanoid = humanoid;
    this.target = target;
    
    HelpCtx.setHelpIDString(this, "HANIMHUMANOID_ELEM_HELPID");

    hAnimHumanoid.setVisualizationSelectionAvailable(true); // must precede initComponents() interface initialization
    hAnimHumanoid.setVisualizationTooltip("Show center axes, add wireframe Box and axes to show boundingBox center and size (if defined)");
    
    initComponents();
    
    super.getDEFUSEpanel().setContainerFieldChoices(HANIMHUMANOID_CONTAINERFIELD_CHOICES, HANIMHUMANOID_CONTAINERFIELD_TOOLTIPS);
    super.getDEFUSEpanel().setContainerField(hAnimHumanoid.getContainerField()); // reset value to match updated JComboBox data model
    // DEFUSEpanel initialization must NOT be repeated or else array of choices will be overwritten
    
    if (!hAnimHumanoid.getDescription().isBlank())
    {
        checkX3D4FieldSupportDialog("HAnimHumanoid","description"); // X3D4.0 field
        descriptionTF.setText(hAnimHumanoid.getDescription());
    }
    
    initializeInfoTable();
    initializeJointTables();
    
    // common background for factor-adjustment combo boxes in order to distinguish from field entries
    translationModificationComboBox.setBackground(this.getBackground());
         centerModificationComboBox.setBackground(this.getBackground());
             scaleSelectionComboBox.setBackground(this.getBackground());
    
    centerXTF.setText(humanoid.getCenterX());
    centerYTF.setText(humanoid.getCenterY());
    centerZTF.setText(humanoid.getCenterZ());
    
    nameTextField.setText(humanoid.getName());
    setDefaultDEFname ();
    checkNameDefMatchRules();
    
    rotationXaxisTF.setText(humanoid.getRotationX());
    rotationYaxisTF.setText(humanoid.getRotationY());
    rotationZaxisTF.setText(humanoid.getRotationZ());
    rotationAngleTF.setText(humanoid.getRotationAngle());
    scaleOrientationXaxisTF.setText(humanoid.getScaleOrientationX());
    scaleOrientationYaxisTF.setText(humanoid.getScaleOrientationY());
    scaleOrientationZaxisTF.setText(humanoid.getScaleOrientationZ());
    scaleOrientationAngleTF.setText(humanoid.getScaleOrientationAngle());
    scaleXTF.setText(humanoid.getScaleX());
    scaleYTF.setText(humanoid.getScaleY());
    scaleZTF.setText(humanoid.getScaleZ());
    translationXTF.setText(humanoid.getTranslationX());
    translationYTF.setText(humanoid.getTranslationY());
    translationZTF.setText(humanoid.getTranslationZ());
    
    // TODO if not X3D4, value should be -1
        loaComboBox.setSelectedIndex(humanoid.getLoa().getValue() + 1);
        loaComboBox.setToolTipText(HANIMHUMANOID_ATTR_LOA_TOOLTIPS[loaComboBox.getSelectedIndex()]);
    // TODO if X3D4, value should be 2.0
    versionCombo.setSelectedItem(humanoid.getVersion());
    versionCombo.setToolTipText(HANIMHUMANOID_ATTR_VERSION_CHOICES[versionCombo.getSelectedIndex()]);
    
    bboxCenterTFX.setText(humanoid.getBboxCenterX());
    bboxCenterTFY.setText(humanoid.getBboxCenterY());
    bboxCenterTFZ.setText(humanoid.getBboxCenterZ());
    bboxSizeTFX.setText(humanoid.getBboxSizeX());
    bboxSizeTFY.setText(humanoid.getBboxSizeY());
    bboxSizeTFZ.setText(humanoid.getBboxSizeZ());
    
    translationXoriginal = humanoid.getTranslationX();
    translationYoriginal = humanoid.getTranslationY();
    translationZoriginal = humanoid.getTranslationZ();
         centerXoriginal = humanoid.getCenterX();
         centerYoriginal = humanoid.getCenterY();
         centerZoriginal = humanoid.getCenterZ();

    checkAngles (false);

    if (!hAnimHumanoid.getName().isBlank() &&
        super.getDEFUSEpanel().getDEF().endsWith(hAnimHumanoid.getName())) // successful name match
    {
        localPrefix = super.getDEFUSEpanel().getDEF().substring(0,super.getDEFUSEpanel().getDEF().lastIndexOf(hAnimHumanoid.getName()));
    }
    nameTextField.setText(hAnimHumanoid.getName());
//    checkHumanoidRootSpelling();
    setDefaultDEFname();
    nameWarningLabel.setText(""); // setup for initialization
    checkNameDefMatchRules();
  }
  private void setDefaultDEFname()
  {
    String newDefaultName = nameTextField.getText().trim();
    if (!localPrefix.isBlank())
    {
        if (!localPrefix.endsWith("_"))
             localPrefix += "_";
        newDefaultName = localPrefix + newDefaultName;
    }
    else newDefaultName = NbBundle.getMessage(getClass(),getNameKey()) + newDefaultName;
    // TODO if available, use prefix from ancestor HAnimHumanoid
    
    super.getDEFUSEpanel().setDefaultDEFname(newDefaultName);
  }
  private void initializeJointTables()
  {
    // TODO set tab titles green, tooltip X3D4
      
    jointBindingPositionsExpandableList.setColumnTitles(new String[]{"index","x","y","z"});
    jointBindingPositionsExpandableList.setNewRowData(new Object[]{"untitled","0","0","0"});
    jointBindingPositionsExpandableList.getTable().setRowHeight(16);
    jointBindingPositionsExpandableList.setColumnTitles(new String[]{"name","value"});
    jointBindingPositionsExpandableList.setNewRowData(new Object[]{"untitled",""});
//  jointBindingPositionsExpandableList.setData(HANIMHUMANOID_ATTR_INFO_SUGGESTED_NAME_PAIRS);
    jointBindingPositionsExpandableList.setTextAlignment(JLabel.LEADING);
    jointBindingPositionsExpandableList.getTable().setRowHeight(16);
    String inputs = hAnimHumanoid.getJointBindingPositionsString();
    if (inputs != null && inputs.length() > 0) {
      String[] sa = inputs.replace(',', ' ').trim().split("[\\\"']");
      ArrayList<String> v = new ArrayList<>();
      for (String s : sa) {
        s = s.trim();
        if (s.length() > 0 &&
            !(s.length() == 1 && (s.charAt(0) == '\'' || s.charAt(0) == '"'))) {
          v.add(s);
        }
      }
      if (!v.isEmpty()) {
        Iterator<String> itr = v.iterator();
        while (itr.hasNext()) {
          String s = itr.next();
          String[]sta= s.split("=");
          String nm = sta[0];
          String value;
          if(sta.length != 2)
            value = "";
          else
            value = sta[1];
          
         int row = tableContains(nm);
         if (row >= 0)
            setDataAtRow(row, nm, value);
          else
            newRow(nm, value);
        }
      }
    }
    // apparently the following has to come after setting data
//    jointBindingPositionsExpandableList.getTable().getColumnModel().getColumn(0).setPreferredWidth(100);
//    jointBindingPositionsExpandableList.getTable().getColumnModel().getColumn(1).setPreferredWidth(100);
//    jointBindingPositionsExpandableList.getTable().getColumnModel().getColumn(2).setPreferredWidth(100);
//    jointBindingPositionsExpandableList.getTable().getColumnModel().getColumn(3).setPreferredWidth(100);
    // --
    jointBindingRotationsExpandableList.setColumnTitles(new String[]{"index","x","y","z"});
    jointBindingRotationsExpandableList.setNewRowData(new Object[]{"untitled","0","0","0"});
    jointBindingRotationsExpandableList.getTable().setRowHeight(16);
    jointBindingRotationsExpandableList.setColumnTitles(new String[]{"name","value"});
    jointBindingRotationsExpandableList.setNewRowData(new Object[]{"untitled",""});
//  jointBindingRotationsExpandableList.setData(HANIMHUMANOID_ATTR_INFO_SUGGESTED_NAME_PAIRS);
    jointBindingRotationsExpandableList.setTextAlignment(JLabel.LEADING);
    jointBindingRotationsExpandableList.getTable().setRowHeight(16);
    inputs = hAnimHumanoid.getJointBindingRotationsString();
    if (inputs != null && inputs.length() > 0) {
      String[] sa = inputs.replace(',', ' ').trim().split("[\\\"']");
      ArrayList<String> v = new ArrayList<>();
      for (String s : sa) {
        s = s.trim();
        if (s.length() > 0 &&
            !(s.length() == 1 && (s.charAt(0) == '\'' || s.charAt(0) == '"'))) {
          v.add(s);
        }
      }
      if (!v.isEmpty()) {
        Iterator<String> itr = v.iterator();
        while (itr.hasNext()) {
          String s = itr.next();
          String[]sta= s.split("=");
          String nm = sta[0];
          String value;
          if(sta.length != 2)
            value = "";
          else
            value = sta[1];
          
         int row = tableContains(nm);
         if (row >= 0)
            setDataAtRow(row, nm, value);
          else
            newRow(nm, value);
        }
      }
    }
    // apparently the following has to come after setting data    
//    jointBindingRotationsExpandableList.getTable().getColumnModel().getColumn(0).setPreferredWidth(80);
//    jointBindingRotationsExpandableList.getTable().getColumnModel().getColumn(1).setPreferredWidth(80);
//    jointBindingRotationsExpandableList.getTable().getColumnModel().getColumn(2).setPreferredWidth(80);
//    jointBindingRotationsExpandableList.getTable().getColumnModel().getColumn(3).setPreferredWidth(80);
//    jointBindingRotationsExpandableList.getTable().getColumnModel().getColumn(4).setPreferredWidth(80);
    
    // --
    jointBindingScalesExpandableList.setColumnTitles(new String[]{"index","x","y","z"});
    jointBindingScalesExpandableList.setNewRowData(new Object[]{"untitled","0","0","0"});
    jointBindingScalesExpandableList.getTable().setRowHeight(16);
    jointBindingScalesExpandableList.setColumnTitles(new String[]{"name","value"});
    jointBindingScalesExpandableList.setNewRowData(new Object[]{"untitled",""});
//  jointBindingScalesExpandableList.setData(HANIMHUMANOID_ATTR_INFO_SUGGESTED_NAME_PAIRS);
    jointBindingScalesExpandableList.setTextAlignment(JLabel.LEADING);
    jointBindingScalesExpandableList.getTable().setRowHeight(16);
    inputs = hAnimHumanoid.getJointBindingScalesString();
    if (inputs != null && inputs.length() > 0) {
      String[] sa = inputs.replace(',', ' ').trim().split("[\\\"']");
      ArrayList<String> v = new ArrayList<>();
      for (String s : sa) {
        s = s.trim();
        if (s.length() > 0 &&
            !(s.length() == 1 && (s.charAt(0) == '\'' || s.charAt(0) == '"'))) {
          v.add(s);
        }
      }
      if (!v.isEmpty()) {
        Iterator<String> itr = v.iterator();
        while (itr.hasNext()) {
          String s = itr.next();
          String[]sta= s.split("=");
          String nm = sta[0];
          String value;
          if(sta.length != 2)
            value = "";
          else
            value = sta[1];
          
         int row = tableContains(nm);
         if (row >= 0)
            setDataAtRow(row, nm, value);
          else
            newRow(nm, value);
        }
      }
    }
    // apparently the following has to come after setting data
//    jointBindingScalesExpandableList.getTable().getColumnModel().getColumn(0).setPreferredWidth(100);
//    jointBindingScalesExpandableList.getTable().getColumnModel().getColumn(1).setPreferredWidth(100);
//    jointBindingScalesExpandableList.getTable().getColumnModel().getColumn(2).setPreferredWidth(100);
//    jointBindingScalesExpandableList.getTable().getColumnModel().getColumn(3).setPreferredWidth(100);
  }
  private void initializeInfoTable()
  {
    infoExpandableList.setColumnTitles(new String[]{"name","value"});
    infoExpandableList.setNewRowData(new Object[]{"untitled",""});
    infoExpandableList.setData(HANIMHUMANOID_ATTR_INFO_SUGGESTED_NAME_PAIRS);
    infoExpandableList.setTextAlignment(JLabel.LEADING);
    infoExpandableList.getTable().setRowHeight(16);
    String inputs = hAnimHumanoid.getInfo();
    if (inputs != null && inputs.length() > 0) {
      String[] sa = inputs.replace(',', ' ').trim().split("[\\\"']");
      Vector<String> v = new Vector<String>();
      for (String s : sa) {
        s = s.trim();
        if (s.length() > 0 &&
            !(s.length() == 1 && (s.charAt(0) == '\'' || s.charAt(0) == '"'))) {
          v.add(s);
        }
      }
      if (!v.isEmpty()) {
        Iterator<String> itr = v.iterator();
        while (itr.hasNext()) {
          String s = itr.next();
          String[]sta= s.split("=");
          String nm = sta[0];
          String value;
          if(sta.length != 2)
            value = "";
          else
            value = sta[1];
          
         int row = tableContains(nm);
         if (row >= 0)
            setDataAtRow(row, nm, value);
          else
            newRow(nm, value);
        }
      }
    }
    // apparently the following has to come after setting data
    infoExpandableList.getTable().getColumnModel().getColumn(0).setPreferredWidth(110);
    infoExpandableList.getTable().getColumnModel().getColumn(1).setPreferredWidth(250);
  }
  
  private int tableContains(String name)
  {
    JTable tab = infoExpandableList.getTable();
    TableModel mod = tab.getModel();
    int nrows = mod.getRowCount();
    for(int r=0;r<nrows;r++) {
      if(name.equalsIgnoreCase((String)mod.getValueAt(r, 0)))
        return r;
    }
    return -1;
  }
  
  private void setDataAtRow(int row, String nm, String val)
  {
    JTable tab = infoExpandableList.getTable();
    DefaultTableModel mod = (DefaultTableModel)tab.getModel();
    mod.setValueAt(nm, row, 0);
    mod.setValueAt(val, row, 1);
  }
//  private void insertAtRow(int row, String nm, String val)
//  {
//    JTable tab = infoTable.getTable();
//    DefaultTableModel mod = (DefaultTableModel)tab.getModel();
//    mod.insertRow(row, new String[]{nm,val});
//  }
  
  private void newRow(String nm, String val)
  {
    JTable tab = infoExpandableList.getTable();
    DefaultTableModel mod = (DefaultTableModel)tab.getModel();
    mod.addRow(new String[]{nm,val});
  }
  /** Build "name=value" strings */
  String buildInfoString()
  {
    JTable infoTable = infoExpandableList.getTable();
    DefaultTableModel tableModel = (DefaultTableModel)infoTable.getModel();
    StringBuilder sb = new StringBuilder();
    int rowCount = tableModel.getRowCount();
    for(int row=0; row < rowCount; row++) {
      String name  = ((String)tableModel.getValueAt(row, 0)).trim();
      String value = ((String)tableModel.getValueAt(row, 1)).trim();
      if ((name.isBlank() || value.isBlank()) && isFromDefaultList(name))
           continue;
      if(name.isBlank())
         name = "untitled";
      sb.append('"');
      sb.append(name);
      sb.append('=');
      sb.append(value);
      sb.append("\" ");
    }
    return sb.toString().trim();
  }
  /** test whether name is from default list of names suggested by spec */
  private boolean isFromDefaultList(String name)
  {
    for(String[] defaultNamePair : HANIMHUMANOID_ATTR_INFO_SUGGESTED_NAME_PAIRS) 
    {
      if(name.equals(defaultNamePair[0]))
        return true;
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

        dEFUSEpanel = getDEFUSEpanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        fieldsPanel = new javax.swing.JPanel();
        nameWarningLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        nameTextField = new javax.swing.JTextField();
        versionLabel = new javax.swing.JLabel();
        versionCombo = new javax.swing.JComboBox<>();
        loaLabel = new javax.swing.JLabel();
        loaComboBox = new javax.swing.JComboBox<>();
        descriptionLabel = new javax.swing.JLabel();
        descriptionTF = new javax.swing.JTextField();
        skeletalConfigurationLabel = new javax.swing.JLabel();
        skeletalConfigurationTF = new javax.swing.JTextField();
        skeletalConfigurationDefaultButton = new javax.swing.JButton();
        xLabel = new javax.swing.JLabel();
        yLabel = new javax.swing.JLabel();
        zLabel = new javax.swing.JLabel();
        angleLabel = new javax.swing.JLabel();
        adjustmentsLabel = new javax.swing.JLabel();
        translationLabel = new javax.swing.JLabel();
        translationXTF = new javax.swing.JTextField();
        translationYTF = new javax.swing.JTextField();
        translationZTF = new javax.swing.JTextField();
        translationModificationComboBox = new javax.swing.JComboBox<>();
        centerLabel = new javax.swing.JLabel();
        centerXTF = new javax.swing.JTextField();
        centerYTF = new javax.swing.JTextField();
        centerZTF = new javax.swing.JTextField();
        centerModificationComboBox = new javax.swing.JComboBox<>();
        rotationLabel = new javax.swing.JLabel();
        rotationXaxisTF = new javax.swing.JTextField();
        rotationYaxisTF = new javax.swing.JTextField();
        rotationZaxisTF = new javax.swing.JTextField();
        rotationAngleTF = new javax.swing.JTextField();
        rotationCalculatorlButton = new javax.swing.JButton();
        scaleLabel = new javax.swing.JLabel();
        scaleXTF = new javax.swing.JTextField();
        scaleYTF = new javax.swing.JTextField();
        scaleZTF = new javax.swing.JTextField();
        scaleSelectionComboBox = new javax.swing.JComboBox<>();
        scaleOrientationLabel = new javax.swing.JLabel();
        scaleOrientationXaxisTF = new javax.swing.JTextField();
        scaleOrientationYaxisTF = new javax.swing.JTextField();
        scaleOrientationZaxisTF = new javax.swing.JTextField();
        scaleOrientationAngleTF = new javax.swing.JTextField();
        scaleOrientationCalculatorlButton = new javax.swing.JButton();
        normalizeRotationValuesButton = new javax.swing.JButton();
        bboxCenterLabel = new javax.swing.JLabel();
        bboxCenterTFX = new javax.swing.JTextField();
        bboxCenterTFY = new javax.swing.JTextField();
        bboxCenterTFZ = new javax.swing.JTextField();
        bboxSizeLabel = new javax.swing.JLabel();
        bboxSizeTFX = new javax.swing.JTextField();
        bboxSizeTFY = new javax.swing.JTextField();
        bboxSizeTFZ = new javax.swing.JTextField();
        infoPanel = new javax.swing.JPanel();
        infoExpandableList = new org.web3d.x3d.palette.items.ExpandableList();
        jointBindingPositionsPanel = new javax.swing.JPanel();
        jointBindingPositionsExpandableList = new org.web3d.x3d.palette.items.ExpandableList();
        jointBindingRotationsPanel = new javax.swing.JPanel();
        jointBindingRotationsExpandableList = new org.web3d.x3d.palette.items.ExpandableList();
        jointBindingScalesPanel = new javax.swing.JPanel();
        jointBindingScalesExpandableList = new org.web3d.x3d.palette.items.ExpandableList();
        nodeHintPanel = new javax.swing.JPanel();
        hintLabel = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(610, 520));
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });
        setLayout(new java.awt.GridBagLayout());

        dEFUSEpanel.setMinimumSize(new java.awt.Dimension(10, 10));
        dEFUSEpanel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dEFUSEpanelMouseClicked(evt);
            }
        });
        dEFUSEpanel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                dEFUSEpanelKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel, gridBagConstraints);

        jTabbedPane1.setToolTipText("metadata name=value pairs");
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(399, 336));
        jTabbedPane1.setRequestFocusEnabled(false);

        fieldsPanel.setToolTipText("simple attributes");
        fieldsPanel.setMaximumSize(new java.awt.Dimension(2147483647, 311));
        fieldsPanel.setMinimumSize(new java.awt.Dimension(394, 311));
        fieldsPanel.setPreferredSize(new java.awt.Dimension(610, 350));
        fieldsPanel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                fieldsPanelFocusGained(evt);
            }
        });
        fieldsPanel.setLayout(new java.awt.GridBagLayout());

        nameWarningLabel.setFont(new java.awt.Font("Segoe UI", 2, 12)); // NOI18N
        nameWarningLabel.setText("name must have a legal value. no name attribute is allowed for USE nodes.");
        nameWarningLabel.setToolTipText("HAnim has strict rules for name and DEF");
        nameWarningLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                nameWarningLabelMouseEntered(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 3, 3);
        fieldsPanel.add(nameWarningLabel, gridBagConstraints);

        nameLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        nameLabel.setText("name");
        nameLabel.setToolTipText("Must assign unique name for this HAnimHumanoid");
        nameLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                nameLabelMouseEntered(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(nameLabel, gridBagConstraints);

        nameTextField.setToolTipText("Must assign proper name for this HAnimHumanoid");
        nameTextField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                nameTextFieldFocusGained(evt);
            }
        });
        nameTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameTextFieldActionPerformed(evt);
            }
        });
        nameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                nameTextFieldKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(nameTextField, gridBagConstraints);

        versionLabel.setForeground(new java.awt.Color(0, 153, 153));
        versionLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        versionLabel.setText("version");
        versionLabel.setToolTipText("(v4.0) Required: HAnimHumanoid version, where standardized ISO 19774 value is 2.0");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(versionLabel, gridBagConstraints);

        versionCombo.setEditable(true);
        versionCombo.setModel(new DefaultComboBoxModel<String>(HANIMHUMANOID_ATTR_VERSION_CHOICES));
        versionCombo.setToolTipText("(v4.0) Required: HAnimHumanoid version, where standardized ISO 19774 value is 2.0");
        versionCombo.setMinimumSize(new java.awt.Dimension(50, 20));
        versionCombo.setPreferredSize(new java.awt.Dimension(50, 20));
        versionCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                versionComboActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(versionCombo, gridBagConstraints);

        loaLabel.setForeground(new java.awt.Color(0, 153, 153));
        loaLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        loaLabel.setText("loa");
        loaLabel.setToolTipText("(v4.0) Level Of Articulation 0..4 for HAnim complexity and detail, -1 means noncompliant");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(loaLabel, gridBagConstraints);

        loaComboBox.setEditable(true);
        loaComboBox.setModel(new DefaultComboBoxModel<String>(HANIMHUMANOID_ATTR_LOA_CHOICES));
        loaComboBox.setToolTipText("(v4.0) Level Of Articulation 0..4 for HAnim complexity and detail, -1 means noncompliant");
        loaComboBox.setMinimumSize(new java.awt.Dimension(50, 20));
        loaComboBox.setPreferredSize(new java.awt.Dimension(50, 20));
        loaComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loaComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(loaComboBox, gridBagConstraints);

        descriptionLabel.setForeground(new java.awt.Color(0, 153, 153));
        descriptionLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        descriptionLabel.setText("description");
        descriptionLabel.setToolTipText("(v4.0) Text description to be displayed for action of this node");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(descriptionLabel, gridBagConstraints);

        descriptionTF.setForeground(new java.awt.Color(0, 153, 153));
        descriptionTF.setToolTipText("(v4.0) Author-provided prose that describes intended purpose of the node");
        descriptionTF.setMinimumSize(new java.awt.Dimension(50, 20));
        descriptionTF.setPreferredSize(new java.awt.Dimension(50, 20));
        descriptionTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                descriptionTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(descriptionTF, gridBagConstraints);

        skeletalConfigurationLabel.setForeground(new java.awt.Color(0, 153, 153));
        skeletalConfigurationLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        skeletalConfigurationLabel.setText("skeletalConfiguration");
        skeletalConfigurationLabel.setToolTipText("(v4.0) Models sharing common skeletalConfiguration can share animations");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(skeletalConfigurationLabel, gridBagConstraints);

        skeletalConfigurationTF.setForeground(new java.awt.Color(0, 153, 153));
        skeletalConfigurationTF.setToolTipText("(v4.0) Models sharing common skeletalConfiguration can share animations");
        skeletalConfigurationTF.setMinimumSize(new java.awt.Dimension(50, 20));
        skeletalConfigurationTF.setPreferredSize(new java.awt.Dimension(50, 20));
        skeletalConfigurationTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                skeletalConfigurationTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(skeletalConfigurationTF, gridBagConstraints);

        skeletalConfigurationDefaultButton.setForeground(new java.awt.Color(0, 153, 153));
        skeletalConfigurationDefaultButton.setText("default");
        skeletalConfigurationDefaultButton.setToolTipText("set default value BASIC");
        skeletalConfigurationDefaultButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                skeletalConfigurationDefaultButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(skeletalConfigurationDefaultButton, gridBagConstraints);

        xLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        xLabel.setText("x");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(xLabel, gridBagConstraints);

        yLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        yLabel.setText("y");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(yLabel, gridBagConstraints);

        zLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        zLabel.setText("z");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(zLabel, gridBagConstraints);

        angleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        angleLabel.setText("angle");
        angleLabel.setToolTipText("angle in radians (can convert large degree values if > 6.28)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(angleLabel, gridBagConstraints);

        adjustmentsLabel.setText("adjustments");
        adjustmentsLabel.setToolTipText("apply adjustment factor to original value");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 3, 3, 3);
        fieldsPanel.add(adjustmentsLabel, gridBagConstraints);

        translationLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        translationLabel.setText("translation");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(translationLabel, gridBagConstraints);

        translationXTF.setMinimumSize(new java.awt.Dimension(50, 20));
        translationXTF.setPreferredSize(new java.awt.Dimension(50, 20));
        translationXTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                translationXTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(translationXTF, gridBagConstraints);

        translationYTF.setMinimumSize(new java.awt.Dimension(50, 20));
        translationYTF.setPreferredSize(new java.awt.Dimension(50, 20));
        translationYTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                translationYTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(translationYTF, gridBagConstraints);

        translationZTF.setMinimumSize(new java.awt.Dimension(50, 20));
        translationZTF.setPreferredSize(new java.awt.Dimension(50, 20));
        translationZTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                translationZTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(translationZTF, gridBagConstraints);

        translationModificationComboBox.setModel(new DefaultComboBoxModel<String>(TRANSFORM_ATTR_TRANSLATION_CHOICES));
        translationModificationComboBox.setToolTipText("Scale translation values to meters");
        translationModificationComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                translationModificationComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        fieldsPanel.add(translationModificationComboBox, gridBagConstraints);

        centerLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        centerLabel.setText("center");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(centerLabel, gridBagConstraints);

        centerXTF.setMinimumSize(new java.awt.Dimension(50, 20));
        centerXTF.setPreferredSize(new java.awt.Dimension(50, 20));
        centerXTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                centerXTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(centerXTF, gridBagConstraints);

        centerYTF.setMinimumSize(new java.awt.Dimension(50, 20));
        centerYTF.setPreferredSize(new java.awt.Dimension(50, 20));
        centerYTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                centerYTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(centerYTF, gridBagConstraints);

        centerZTF.setMinimumSize(new java.awt.Dimension(50, 20));
        centerZTF.setPreferredSize(new java.awt.Dimension(50, 20));
        centerZTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                centerZTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(centerZTF, gridBagConstraints);

        centerModificationComboBox.setModel(new DefaultComboBoxModel<String>(TRANSFORM_ATTR_TRANSLATION_CHOICES));
        centerModificationComboBox.setToolTipText("Scale center values to meters");
        centerModificationComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                centerModificationComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        fieldsPanel.add(centerModificationComboBox, gridBagConstraints);

        rotationLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        rotationLabel.setText("rotation");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(rotationLabel, gridBagConstraints);

        rotationXaxisTF.setMinimumSize(new java.awt.Dimension(50, 20));
        rotationXaxisTF.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(rotationXaxisTF, gridBagConstraints);

        rotationYaxisTF.setMinimumSize(new java.awt.Dimension(50, 20));
        rotationYaxisTF.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(rotationYaxisTF, gridBagConstraints);

        rotationZaxisTF.setMinimumSize(new java.awt.Dimension(50, 20));
        rotationZaxisTF.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(rotationZaxisTF, gridBagConstraints);

        rotationAngleTF.setMinimumSize(new java.awt.Dimension(50, 20));
        rotationAngleTF.setPreferredSize(new java.awt.Dimension(50, 20));
        rotationAngleTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotationAngleTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(rotationAngleTF, gridBagConstraints);

        rotationCalculatorlButton.setText("calculator");
        rotationCalculatorlButton.setToolTipText("launch geoSystem panel");
        rotationCalculatorlButton.setMaximumSize(new java.awt.Dimension(80, 22));
        rotationCalculatorlButton.setMinimumSize(new java.awt.Dimension(80, 22));
        rotationCalculatorlButton.setPreferredSize(new java.awt.Dimension(6, 22));
        rotationCalculatorlButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotationCalculatorlButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(rotationCalculatorlButton, gridBagConstraints);

        scaleLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        scaleLabel.setText("scale");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(scaleLabel, gridBagConstraints);

        scaleXTF.setMinimumSize(new java.awt.Dimension(50, 20));
        scaleXTF.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(scaleXTF, gridBagConstraints);

        scaleYTF.setMinimumSize(new java.awt.Dimension(50, 20));
        scaleYTF.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(scaleYTF, gridBagConstraints);

        scaleZTF.setMinimumSize(new java.awt.Dimension(50, 20));
        scaleZTF.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(scaleZTF, gridBagConstraints);

        scaleSelectionComboBox.setModel(new DefaultComboBoxModel<String>(TRANSFORM_ATTR_SCALE_CHOICES));
        scaleSelectionComboBox.setToolTipText("Scale child node dimensions to meters");
        scaleSelectionComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scaleSelectionComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        fieldsPanel.add(scaleSelectionComboBox, gridBagConstraints);

        scaleOrientationLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        scaleOrientationLabel.setText("scaleOrientation");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(scaleOrientationLabel, gridBagConstraints);

        scaleOrientationXaxisTF.setMinimumSize(new java.awt.Dimension(50, 20));
        scaleOrientationXaxisTF.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(scaleOrientationXaxisTF, gridBagConstraints);

        scaleOrientationYaxisTF.setMinimumSize(new java.awt.Dimension(50, 20));
        scaleOrientationYaxisTF.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(scaleOrientationYaxisTF, gridBagConstraints);

        scaleOrientationZaxisTF.setMinimumSize(new java.awt.Dimension(50, 20));
        scaleOrientationZaxisTF.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(scaleOrientationZaxisTF, gridBagConstraints);

        scaleOrientationAngleTF.setMinimumSize(new java.awt.Dimension(50, 20));
        scaleOrientationAngleTF.setPreferredSize(new java.awt.Dimension(50, 20));
        scaleOrientationAngleTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scaleOrientationAngleTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(scaleOrientationAngleTF, gridBagConstraints);

        scaleOrientationCalculatorlButton.setText("calculator");
        scaleOrientationCalculatorlButton.setToolTipText("launch geoSystem panel");
        scaleOrientationCalculatorlButton.setMaximumSize(new java.awt.Dimension(80, 22));
        scaleOrientationCalculatorlButton.setMinimumSize(new java.awt.Dimension(80, 22));
        scaleOrientationCalculatorlButton.setPreferredSize(new java.awt.Dimension(6, 22));
        scaleOrientationCalculatorlButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scaleOrientationCalculatorlButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.ipadx = 80;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(scaleOrientationCalculatorlButton, gridBagConstraints);

        normalizeRotationValuesButton.setText("normalize rotation and scaleOrientation values");
        normalizeRotationValuesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                normalizeRotationValuesButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        fieldsPanel.add(normalizeRotationValuesButton, gridBagConstraints);

        bboxCenterLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        bboxCenterLabel.setText("bboxCenter");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(bboxCenterLabel, gridBagConstraints);

        bboxCenterTFX.setMinimumSize(new java.awt.Dimension(50, 20));
        bboxCenterTFX.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(bboxCenterTFX, gridBagConstraints);

        bboxCenterTFY.setMinimumSize(new java.awt.Dimension(50, 20));
        bboxCenterTFY.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(bboxCenterTFY, gridBagConstraints);

        bboxCenterTFZ.setMinimumSize(new java.awt.Dimension(50, 20));
        bboxCenterTFZ.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        fieldsPanel.add(bboxCenterTFZ, gridBagConstraints);

        bboxSizeLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        bboxSizeLabel.setText("bboxSize");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 6, 3);
        fieldsPanel.add(bboxSizeLabel, gridBagConstraints);

        bboxSizeTFX.setMinimumSize(new java.awt.Dimension(50, 20));
        bboxSizeTFX.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 6, 3);
        fieldsPanel.add(bboxSizeTFX, gridBagConstraints);

        bboxSizeTFY.setMinimumSize(new java.awt.Dimension(50, 20));
        bboxSizeTFY.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 6, 3);
        fieldsPanel.add(bboxSizeTFY, gridBagConstraints);

        bboxSizeTFZ.setMinimumSize(new java.awt.Dimension(50, 20));
        bboxSizeTFZ.setPreferredSize(new java.awt.Dimension(50, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.25;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 6, 3);
        fieldsPanel.add(bboxSizeTFZ, gridBagConstraints);

        jTabbedPane1.addTab("fields", fieldsPanel);

        infoPanel.setToolTipText("Metadata information, name=value pairs");
        infoPanel.setMinimumSize(new java.awt.Dimension(300, 110));

        infoExpandableList.setPreferredSize(new java.awt.Dimension(300, 110));

        javax.swing.GroupLayout infoPanelLayout = new javax.swing.GroupLayout(infoPanel);
        infoPanel.setLayout(infoPanelLayout);
        infoPanelLayout.setHorizontalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(infoExpandableList, javax.swing.GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE)
                .addContainerGap())
        );
        infoPanelLayout.setVerticalGroup(
            infoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(infoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(infoExpandableList, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("info values", infoPanel);

        jointBindingPositionsPanel.setForeground(new java.awt.Color(0, 153, 153));

        jointBindingPositionsExpandableList.setPreferredSize(new java.awt.Dimension(300, 110));

        javax.swing.GroupLayout jointBindingPositionsPanelLayout = new javax.swing.GroupLayout(jointBindingPositionsPanel);
        jointBindingPositionsPanel.setLayout(jointBindingPositionsPanelLayout);
        jointBindingPositionsPanelLayout.setHorizontalGroup(
            jointBindingPositionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jointBindingPositionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jointBindingPositionsExpandableList, javax.swing.GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE)
                .addContainerGap())
        );
        jointBindingPositionsPanelLayout.setVerticalGroup(
            jointBindingPositionsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jointBindingPositionsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jointBindingPositionsExpandableList, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("jointBindingPositions", jointBindingPositionsPanel);

        jointBindingRotationsPanel.setForeground(new java.awt.Color(0, 153, 153));

        jointBindingRotationsExpandableList.setPreferredSize(new java.awt.Dimension(300, 110));

        javax.swing.GroupLayout jointBindingRotationsPanelLayout = new javax.swing.GroupLayout(jointBindingRotationsPanel);
        jointBindingRotationsPanel.setLayout(jointBindingRotationsPanelLayout);
        jointBindingRotationsPanelLayout.setHorizontalGroup(
            jointBindingRotationsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jointBindingRotationsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jointBindingRotationsExpandableList, javax.swing.GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE)
                .addContainerGap())
        );
        jointBindingRotationsPanelLayout.setVerticalGroup(
            jointBindingRotationsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jointBindingRotationsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jointBindingRotationsExpandableList, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("jointBindingRotations", jointBindingRotationsPanel);

        jointBindingScalesPanel.setForeground(new java.awt.Color(0, 153, 153));

        jointBindingScalesExpandableList.setPreferredSize(new java.awt.Dimension(300, 110));

        javax.swing.GroupLayout jointBindingScalesPanelLayout = new javax.swing.GroupLayout(jointBindingScalesPanel);
        jointBindingScalesPanel.setLayout(jointBindingScalesPanelLayout);
        jointBindingScalesPanelLayout.setHorizontalGroup(
            jointBindingScalesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 600, Short.MAX_VALUE)
            .addGroup(jointBindingScalesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jointBindingScalesPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jointBindingScalesExpandableList, javax.swing.GroupLayout.DEFAULT_SIZE, 588, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jointBindingScalesPanelLayout.setVerticalGroup(
            jointBindingScalesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 313, Short.MAX_VALUE)
            .addGroup(jointBindingScalesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jointBindingScalesPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jointBindingScalesExpandableList, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jTabbedPane1.addTab("jointBindingScales", jointBindingScalesPanel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(jTabbedPane1, gridBagConstraints);

        nodeHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nodeHintPanel.setLayout(new java.awt.GridBagLayout());

        hintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel.setText("<html> <p align=\"center\"><b>HAnimHumanoid </b> serves as a container for the entire humanoid,<br />\n provides a convenient way of moving the humanoid to different locations, <br />\n and stores references to the joints, segments, sites, skin and viewpoint nodes.</p>");
        hintLabel.setToolTipText("close this panel to add children nodes");
        hintLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 3, 3, 3);
        nodeHintPanel.add(hintLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nodeHintPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void nameTextFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_nameTextFieldActionPerformed
    {//GEN-HEADEREND:event_nameTextFieldActionPerformed
        setDefaultDEFname ();
        checkNameDefMatchRules();
    }//GEN-LAST:event_nameTextFieldActionPerformed

    private void versionComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_versionComboActionPerformed
        versionCombo.setToolTipText(HANIMHUMANOID_ATTR_VERSION_TOOLTIPS[versionCombo.getSelectedIndex()]);
    }//GEN-LAST:event_versionComboActionPerformed

    private void translationModificationComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_translationModificationComboBoxActionPerformed
        double x, y, z;
        int index = translationModificationComboBox.getSelectedIndex();
        if (index <= 0) return;
        
        if (translationScaleApplied == false)
        {
            x = (new SFFloat(translationXTF.getText()).getValue()) * (new SFFloat(TRANSFORM_ATTR_SCALE_FACTORS[index]).getValue());
            y = (new SFFloat(translationYTF.getText()).getValue()) * (new SFFloat(TRANSFORM_ATTR_SCALE_FACTORS[index]).getValue());
            z = (new SFFloat(translationZTF.getText()).getValue()) * (new SFFloat(TRANSFORM_ATTR_SCALE_FACTORS[index]).getValue());
            translationScaleApplied = true;
        }
        else // applying another scaling-factor change, so use original value
        {
            x = (new SFFloat(translationXoriginal).getValue()) * (new SFFloat(TRANSFORM_ATTR_SCALE_FACTORS[index]).getValue());
            y = (new SFFloat(translationYoriginal).getValue()) * (new SFFloat(TRANSFORM_ATTR_SCALE_FACTORS[index]).getValue());
            z = (new SFFloat(translationZoriginal).getValue()) * (new SFFloat(TRANSFORM_ATTR_SCALE_FACTORS[index]).getValue());
        }
        translationXTF.setText(fiveDigitFormat.format(x));
        translationYTF.setText(fiveDigitFormat.format(y));
        translationZTF.setText(fiveDigitFormat.format(z));
    }//GEN-LAST:event_translationModificationComboBoxActionPerformed

    private void centerModificationComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_centerModificationComboBoxActionPerformed
        double x, y, z;
        int index = centerModificationComboBox.getSelectedIndex();
        if (centerScaleApplied == false)
        {
            x = (new SFFloat(centerXTF.getText()).getValue() * new SFFloat(TRANSFORM_ATTR_SCALE_FACTORS[index]).getValue());
            y = (new SFFloat(centerYTF.getText()).getValue() * new SFFloat(TRANSFORM_ATTR_SCALE_FACTORS[index]).getValue());
            z = (new SFFloat(centerZTF.getText()).getValue() * new SFFloat(TRANSFORM_ATTR_SCALE_FACTORS[index]).getValue());
            centerScaleApplied = true;
        }
        else // applying another scaling-factor change, so use original value
        {
            x = (new SFFloat(centerXoriginal).getValue() * new SFFloat(TRANSFORM_ATTR_SCALE_FACTORS[index]).getValue());
            y = (new SFFloat(centerYoriginal).getValue() * new SFFloat(TRANSFORM_ATTR_SCALE_FACTORS[index]).getValue());
            z = (new SFFloat(centerZoriginal).getValue() * new SFFloat(TRANSFORM_ATTR_SCALE_FACTORS[index]).getValue());
        }
        centerXTF.setText(fiveDigitFormat.format(x));
        centerYTF.setText(fiveDigitFormat.format(y));
        centerZTF.setText(fiveDigitFormat.format(z));
    }//GEN-LAST:event_centerModificationComboBoxActionPerformed

    private void scaleSelectionComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scaleSelectionComboBoxActionPerformed
        int index = scaleSelectionComboBox.getSelectedIndex();
        scaleXTF.setText(TRANSFORM_ATTR_SCALE_FACTORS[index]);
        scaleYTF.setText(TRANSFORM_ATTR_SCALE_FACTORS[index]);
        scaleZTF.setText(TRANSFORM_ATTR_SCALE_FACTORS[index]);

        // also assign DEF label as descriptive documentation, if no prior DEF provided by author
        boolean priorScalingLabelFound = false;
        for (int i=0; i < TRANSFORM_ATTR_SCALE_LABELS.length; i++)
        {
            if (dEFUSEpanel.getDefTF().getText().equalsIgnoreCase("Scale" + TRANSFORM_ATTR_SCALE_LABELS[i])
                && (TRANSFORM_ATTR_SCALE_LABELS[i].length() > 0))
            priorScalingLabelFound = true;
        }
        if (((dEFUSEpanel.getDefTF().getText().length()==0) || priorScalingLabelFound) && (TRANSFORM_ATTR_SCALE_LABELS[index].length() > 0))
        // of course if this DEF label is a duplicate occurrence, that is an XML validation error
        dEFUSEpanel.getDefTF().setText("Scale" + TRANSFORM_ATTR_SCALE_LABELS[index]);
        else if (priorScalingLabelFound && (TRANSFORM_ATTR_SCALE_LABELS[index].length() == 0))
        dEFUSEpanel.getDefTF().setText("");
    }//GEN-LAST:event_scaleSelectionComboBoxActionPerformed

    private void normalizeRotationValuesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_normalizeRotationValuesButtonActionPerformed
        checkAngles(true);
        double normalizationFactor, x, y, z, angle;

        x = new SFFloat(rotationXaxisTF.getText()).getValue();
        y = new SFFloat(rotationYaxisTF.getText()).getValue();
        z = new SFFloat(rotationZaxisTF.getText()).getValue();
        angle = new SFFloat(rotationAngleTF.getText()).getValue();
        normalizationFactor = Math.sqrt(x * x + y * y + z * z);
        if (normalizationFactor == 0.0)
        {
            NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                "Found zero-magnitude axis for rotation, reset to 0 1 0", NotifyDescriptor.WARNING_MESSAGE);
            DialogDisplayer.getDefault().notify(descriptor);
            rotationXaxisTF.setText("0");
            rotationYaxisTF.setText("1");
            rotationZaxisTF.setText("0");
        }
        else
        {
            rotationXaxisTF.setText(fiveDigitFormat.format(x / normalizationFactor));
            rotationYaxisTF.setText(fiveDigitFormat.format(y / normalizationFactor));
            rotationZaxisTF.setText(fiveDigitFormat.format(z / normalizationFactor));
        }
        if (angle == -0.0)
        {
            angle = 0.0;
        }
        while (angle < 0.0)
        {
            angle += 2.0 * Math.PI;
        }
        while (angle > 2.0 * Math.PI)
        {
            angle -= 2.0 * Math.PI;
        }
        rotationAngleTF.setText(radiansFormat.format(angle));
        rotationAngleTF.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");

        x = new SFFloat(scaleOrientationXaxisTF.getText()).getValue();
        y = new SFFloat(scaleOrientationYaxisTF.getText()).getValue();
        z = new SFFloat(scaleOrientationZaxisTF.getText()).getValue();
        angle = new SFFloat(scaleOrientationAngleTF.getText()).getValue();
        normalizationFactor = Math.sqrt(x * x + y * y + z * z);
        if (normalizationFactor == 0.0)
        {
            NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                "Found zero-magnitude axis for scaleOrientation, reset to 0 1 0", NotifyDescriptor.WARNING_MESSAGE);
            DialogDisplayer.getDefault().notify(descriptor);
            scaleOrientationXaxisTF.setText("0");
            scaleOrientationYaxisTF.setText("1");
            scaleOrientationZaxisTF.setText("0");
        }
        else
        {
            scaleOrientationXaxisTF.setText(fiveDigitFormat.format(x / normalizationFactor));
            scaleOrientationYaxisTF.setText(fiveDigitFormat.format(y / normalizationFactor));
            scaleOrientationZaxisTF.setText(fiveDigitFormat.format(z / normalizationFactor));
        }
        if (angle == -0.0)
        {
            angle = 0.0;
        }
        while (angle < 0.0)
        {
            angle += 2.0 * Math.PI;
        }
        while (angle > 2.0 * Math.PI)
        {
            angle -= 2.0 * Math.PI;
        }
        scaleOrientationAngleTF.setText(radiansFormat.format(angle));
        scaleOrientationAngleTF.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");
    }//GEN-LAST:event_normalizeRotationValuesButtonActionPerformed

    private void rotationAngleTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rotationAngleTFActionPerformed
        checkAngles (false);
    }//GEN-LAST:event_rotationAngleTFActionPerformed

    private void scaleOrientationAngleTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scaleOrientationAngleTFActionPerformed
        checkAngles (false);
    }//GEN-LAST:event_scaleOrientationAngleTFActionPerformed

    private void translationXTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_translationXTFActionPerformed
        translationXoriginal    = translationXTF.getText();
        translationScaleApplied = false;
    }//GEN-LAST:event_translationXTFActionPerformed

    private void translationYTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_translationYTFActionPerformed
        translationYoriginal    = translationYTF.getText();
        translationScaleApplied = false;
    }//GEN-LAST:event_translationYTFActionPerformed

    private void translationZTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_translationZTFActionPerformed
        translationZoriginal    = translationZTF.getText();
        translationScaleApplied = false;
    }//GEN-LAST:event_translationZTFActionPerformed

    private void centerXTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_centerXTFActionPerformed
        centerXoriginal    = centerXTF.getText();
        centerScaleApplied = false;
    }//GEN-LAST:event_centerXTFActionPerformed

    private void centerYTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_centerYTFActionPerformed
        centerYoriginal    = centerYTF.getText();
        centerScaleApplied = false;
    }//GEN-LAST:event_centerYTFActionPerformed

    private void centerZTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_centerZTFActionPerformed
        centerZoriginal    = centerZTF.getText();
        centerScaleApplied = false;
    }//GEN-LAST:event_centerZTFActionPerformed

    private void rotationCalculatorlButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rotationCalculatorlButtonActionPerformed
        RotationCalculatorPanel rotationCalculatorPanel = new RotationCalculatorPanel(hAnimHumanoid, "rotation");
        rotationCalculatorPanel.setRotationValue (
            rotationXaxisTF.getText(),
            rotationYaxisTF.getText(),
            rotationZaxisTF.getText(),
            rotationAngleTF.getText());
        DialogDescriptor dd = new DialogDescriptor(rotationCalculatorPanel, "Rotation Calculator for HAnimHumanoid rotation");
        Dialog dialog = DialogDisplayer.getDefault().createDialog(dd);
        dialog.setVisible(true);
        if (dd.getValue() != DialogDescriptor.CANCEL_OPTION)
        {
            // save values
            if (!rotationCalculatorPanel.getRotationResult().isEmpty())
            {
                rotationXaxisTF.setText(rotationCalculatorPanel.getRotationResultX());
                rotationYaxisTF.setText(rotationCalculatorPanel.getRotationResultY());
                rotationZaxisTF.setText(rotationCalculatorPanel.getRotationResultZ());
                rotationAngleTF.setText(rotationCalculatorPanel.getRotationResultAngle());
                rotationAngleTF.setToolTipText(rotationCalculatorPanel.getRotationResultAngle() + " radians = " +
                    singleDigitFormat.format(Float.parseFloat(rotationCalculatorPanel.getRotationResultAngle()) * 180.0 / Math.PI) + " degrees");
            }
        }
    }//GEN-LAST:event_rotationCalculatorlButtonActionPerformed

    private void scaleOrientationCalculatorlButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scaleOrientationCalculatorlButtonActionPerformed
        RotationCalculatorPanel scaleOrientationCalculatorPanel = new RotationCalculatorPanel(hAnimHumanoid, "scaleOrientation");
        scaleOrientationCalculatorPanel.setRotationValue (
            scaleOrientationXaxisTF.getText(),
            scaleOrientationYaxisTF.getText(),
            scaleOrientationZaxisTF.getText(),
            scaleOrientationAngleTF.getText());
        DialogDescriptor dd = new DialogDescriptor(scaleOrientationCalculatorPanel, "Rotation Calculator for HAnimHumanoid scaleOrientation");
        Dialog dialog = DialogDisplayer.getDefault().createDialog(dd);
        dialog.setVisible(true);
        if (dd.getValue() != DialogDescriptor.CANCEL_OPTION)
        {
            // save values
            if (!scaleOrientationCalculatorPanel.getRotationResult().isEmpty())
            {
                scaleOrientationXaxisTF.setText(scaleOrientationCalculatorPanel.getRotationResultX());
                scaleOrientationYaxisTF.setText(scaleOrientationCalculatorPanel.getRotationResultY());
                scaleOrientationZaxisTF.setText(scaleOrientationCalculatorPanel.getRotationResultZ());
                scaleOrientationAngleTF.setText(scaleOrientationCalculatorPanel.getRotationResultAngle());
                scaleOrientationAngleTF.setToolTipText(scaleOrientationCalculatorPanel.getRotationResultAngle() + " radians = " +
                    singleDigitFormat.format(Float.parseFloat(scaleOrientationCalculatorPanel.getRotationResultAngle()) * 180.0 / Math.PI) + " degrees");
            }
        }
    }//GEN-LAST:event_scaleOrientationCalculatorlButtonActionPerformed

    private void descriptionTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_descriptionTFActionPerformed
    {//GEN-HEADEREND:event_descriptionTFActionPerformed
        checkX3D4FieldSupportDialog("HAnimHumanoid","description"); // X3D4.0 field
    }//GEN-LAST:event_descriptionTFActionPerformed

    private void dEFUSEpanelKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_dEFUSEpanelKeyReleased
    {//GEN-HEADEREND:event_dEFUSEpanelKeyReleased
        setDefaultDEFname ();
        checkNameDefMatchRules();
    }//GEN-LAST:event_dEFUSEpanelKeyReleased

    private void nameTextFieldKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_nameTextFieldKeyReleased
    {//GEN-HEADEREND:event_nameTextFieldKeyReleased
        setDefaultDEFname ();
        checkNameDefMatchRules();
    }//GEN-LAST:event_nameTextFieldKeyReleased

    private void formFocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event_formFocusGained
    {//GEN-HEADEREND:event_formFocusGained
        setDefaultDEFname ();
        checkNameDefMatchRules();
    }//GEN-LAST:event_formFocusGained

    private void fieldsPanelFocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event_fieldsPanelFocusGained
    {//GEN-HEADEREND:event_fieldsPanelFocusGained
        setDefaultDEFname ();
        checkNameDefMatchRules();
    }//GEN-LAST:event_fieldsPanelFocusGained

    private void nameTextFieldFocusGained(java.awt.event.FocusEvent evt)//GEN-FIRST:event_nameTextFieldFocusGained
    {//GEN-HEADEREND:event_nameTextFieldFocusGained
        setDefaultDEFname ();
        checkNameDefMatchRules();
    }//GEN-LAST:event_nameTextFieldFocusGained

    private void nameWarningLabelMouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event_nameWarningLabelMouseEntered
    {//GEN-HEADEREND:event_nameWarningLabelMouseEntered
        setDefaultDEFname ();
        checkNameDefMatchRules();
    }//GEN-LAST:event_nameWarningLabelMouseEntered

    private void nameLabelMouseEntered(java.awt.event.MouseEvent evt)//GEN-FIRST:event_nameLabelMouseEntered
    {//GEN-HEADEREND:event_nameLabelMouseEntered
        setDefaultDEFname ();
        checkNameDefMatchRules();
    }//GEN-LAST:event_nameLabelMouseEntered

    private void loaComboBoxActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_loaComboBoxActionPerformed
    {//GEN-HEADEREND:event_loaComboBoxActionPerformed
        loaComboBox.setToolTipText(HANIMHUMANOID_ATTR_LOA_TOOLTIPS[loaComboBox.getSelectedIndex()]);
    }//GEN-LAST:event_loaComboBoxActionPerformed

    private void skeletalConfigurationTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_skeletalConfigurationTFActionPerformed
    {//GEN-HEADEREND:event_skeletalConfigurationTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_skeletalConfigurationTFActionPerformed

    private void skeletalConfigurationDefaultButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_skeletalConfigurationDefaultButtonActionPerformed
    {//GEN-HEADEREND:event_skeletalConfigurationDefaultButtonActionPerformed
        skeletalConfigurationTF.setText(HANIMHUMANOID_ATTR_SKELETALCONFIGURATION_DFLT);
    }//GEN-LAST:event_skeletalConfigurationDefaultButtonActionPerformed

    private void dEFUSEpanelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dEFUSEpanelMouseClicked
        setDefaultDEFname ();
        checkNameDefMatchRules();
    }//GEN-LAST:event_dEFUSEpanelMouseClicked

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_HANIMHUMANOID";
  }

  private void checkAngles(boolean precedesNormalization)
  {
      // indicate degree values in tooltips
      // usability note:  can enter degree values (-6..+6) as (354..366) to provoke this conversion check
      double angle = new SFDouble(rotationAngleTF.getText()).getValue();
      rotationAngleTF.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");
      if (Math.abs(angle) > 2.0 * Math.PI)
      {
            String message;
            message = "<html><center>Large value provided for <b>rotation</b> angle, which is a radians value.<br/><br/>Convert <b>" + angle + " degrees</b> to <b>" +
                    radiansFormat.format((angle % 360.0) * Math.PI / 180.0) + " radians</b>";
            if (precedesNormalization)
                 message += " before normalization?";
            else message += "?";
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  message, "X3D angles are in radians", NotifyDescriptor.YES_NO_OPTION);
          if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
          {
              angle = (angle % 360.0) * Math.PI / 180.0;
              rotationAngleTF.setText(radiansFormat.format(angle));
              rotationAngleTF.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");
          }
      }
      angle = new SFDouble(scaleOrientationAngleTF.getText()).getValue();
      scaleOrientationAngleTF.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");
      if (Math.abs(angle) > 2.0 * Math.PI)
      {
            String message;
            message = "<html><center>Large value provided for <b>scaleOrientation</b> angle, which is a radians value.<br/><br/>Convert <b>" + angle + " degrees</b> to <b>" +
                    radiansFormat.format((angle % 360.0) * Math.PI / 180.0) + " radians</b>";
            if (precedesNormalization)
                 message += " before normalization?";
            else message += "?";
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  message, "X3D angles are in radians", NotifyDescriptor.YES_NO_OPTION);
          if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
          {
              angle = (angle % 360.0) * Math.PI / 180.0;
              scaleOrientationAngleTF.setText(radiansFormat.format(angle));
              scaleOrientationAngleTF.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");
          }
      }
  }
  
    private void checkNameDefMatchRules()
    {
        String NAME_REQUIRED      = "name must have a legal value";
        String NAME_RULE_MATCH    = "successful name match: DEF = prefix + name";
        String NAME_RULE_MISMATCH = "name mismatch: DEF must match prefix + name";
  
        String DEF    = super.getDEFUSEpanel().getDEF();
        String name   = nameTextField.getText();
        
        Color  burntorange = new Color(191,  87,  0);
        Color   darkorange = new Color(255, 140,  0);
        Color   darkgreen  = new Color( 21,  71, 52);
        
        nameTextField.setEnabled(true); // default
        if (getDEFUSEpanel().isUSE())
        {
            // no name for USE node
            nameWarningLabel.setText("no name attribute is allowed for USE node");
            nameWarningLabel.setForeground(Color.BLACK);
            nameTextField.setBackground(Color.WHITE);
            nameTextField.setEnabled(false);
        }
        else if (name.isBlank())
        {
            nameWarningLabel.setText(NAME_REQUIRED);
            nameWarningLabel.setForeground(darkorange);
            nameTextField.setBackground(Color.YELLOW);
            super.getDEFUSEpanel().setDefColors(Color.BLACK, Color.WHITE);
            super.getDEFUSEpanel().refreshPanel();
        }
        else if (DEF.isBlank()) // and name value is present
        {
            nameWarningLabel.setText(NAME_REQUIRED);
            nameWarningLabel.setForeground(Color.BLACK);
            nameTextField.setBackground(Color.WHITE);
            super.getDEFUSEpanel().setDefColors(Color.BLACK, Color.WHITE);
            super.getDEFUSEpanel().refreshPanel();
        }
        else if (DEF.endsWith(name)) // successful name match
        {            
            localPrefix = DEF.substring(0,DEF.lastIndexOf(name));
            // TODO compare to ancestor humanoid prefix if needed
            
            nameWarningLabel.setText(NAME_RULE_MATCH + ", where prefix=" + localPrefix);
            nameWarningLabel.setForeground(darkgreen); // too bright: Color.GREEN
            nameTextField.setBackground(Color.WHITE);
            super.getDEFUSEpanel().selectX3dDEFUSEpane();
            super.getDEFUSEpanel().setDefColors(Color.BLACK, Color.WHITE);
            super.getDEFUSEpanel().refreshPanel();
        }
        else
        {
            nameWarningLabel.setText(NAME_RULE_MISMATCH + ", prefix=" + localPrefix);
            nameWarningLabel.setForeground(darkorange);
            nameTextField.setBackground(Color.YELLOW);
            super.getDEFUSEpanel().selectX3dDEFUSEpane();
            super.getDEFUSEpanel().setDefColors(Color.BLACK, Color.YELLOW);
            super.getDEFUSEpanel().refreshPanel();
        }
    }

  @Override
  public void unloadInput() throws IllegalArgumentException
  {
    unLoadDEFUSE();
    
    hAnimHumanoid.setDescription(descriptionTF.getText().trim());
    // TODO if not X3D4, value should be -1
    hAnimHumanoid.setLoa(new SFInt32(loaComboBox.getSelectedItem().toString(),-1,4));
    // TODO if X3D4, value should be 2.0
    hAnimHumanoid.setVersion(versionCombo.getSelectedItem().toString());
    
    hAnimHumanoid.setName(nameTextField.getText().trim());
    hAnimHumanoid.setCenterX(centerXTF.getText().trim());
    hAnimHumanoid.setCenterY(centerYTF.getText().trim());
    hAnimHumanoid.setCenterZ(centerZTF.getText().trim());
    hAnimHumanoid.setInfo(buildInfoString());
    hAnimHumanoid.setName(nameTextField.getText().trim());
    hAnimHumanoid.setRotationX(rotationXaxisTF.getText().trim());
    hAnimHumanoid.setRotationY(rotationYaxisTF.getText().trim());
    hAnimHumanoid.setRotationZ(rotationZaxisTF.getText().trim());
    hAnimHumanoid.setRotationAngle(rotationAngleTF.getText().trim());
    hAnimHumanoid.setScaleOrientationX(scaleOrientationXaxisTF.getText().trim());
    hAnimHumanoid.setScaleOrientationY(scaleOrientationYaxisTF.getText().trim());
    hAnimHumanoid.setScaleOrientationZ(scaleOrientationZaxisTF.getText().trim());
    hAnimHumanoid.setScaleOrientationAngle(scaleOrientationAngleTF.getText().trim());
    hAnimHumanoid.setScaleX(scaleXTF.getText().trim());
    hAnimHumanoid.setScaleY(scaleYTF.getText().trim());
    hAnimHumanoid.setScaleZ(scaleZTF.getText().trim());
    hAnimHumanoid.setTranslationX(translationXTF.getText().trim());
    hAnimHumanoid.setTranslationY(translationYTF.getText().trim());
    hAnimHumanoid.setTranslationZ(translationZTF.getText().trim());
    hAnimHumanoid.setVersion((String)versionCombo.getSelectedItem());
    hAnimHumanoid.setBboxCenterX(bboxCenterTFX.getText().trim());
    hAnimHumanoid.setBboxCenterY(bboxCenterTFY.getText().trim());
    hAnimHumanoid.setBboxCenterZ(bboxCenterTFZ.getText().trim());
    hAnimHumanoid.setBboxSizeX(bboxSizeTFX.getText().trim());
    hAnimHumanoid.setBboxSizeY(bboxSizeTFY.getText().trim());
    hAnimHumanoid.setBboxSizeZ(bboxSizeTFZ.getText().trim());
    
    hAnimHumanoid.setInfo(buildInfoString()); 
    
    hAnimHumanoid.setJointBindingPositions(jointBindingPositionsExpandableList.getDataSFFloatArray());
    hAnimHumanoid.setJointBindingRotations(jointBindingRotationsExpandableList.getDataSFFloatArray());
    hAnimHumanoid.setJointBindingScales   (     jointBindingScalesExpandableList.getDataSFFloatArray());

    hAnimHumanoid.setInsertCommas    (jointBindingPositionsExpandableList.isInsertCommasSet() ||
                                      jointBindingRotationsExpandableList.isInsertCommasSet() ||
                                         jointBindingScalesExpandableList.isInsertCommasSet());
    hAnimHumanoid.setInsertLineBreaks(jointBindingPositionsExpandableList.isInsertLineBreaksSet() ||
                                      jointBindingRotationsExpandableList.isInsertLineBreaksSet() ||
                                         jointBindingScalesExpandableList.isInsertLineBreaksSet());
  }
     
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel adjustmentsLabel;
    private javax.swing.JLabel angleLabel;
    private javax.swing.JLabel bboxCenterLabel;
    private javax.swing.JTextField bboxCenterTFX;
    private javax.swing.JTextField bboxCenterTFY;
    private javax.swing.JTextField bboxCenterTFZ;
    private javax.swing.JLabel bboxSizeLabel;
    private javax.swing.JTextField bboxSizeTFX;
    private javax.swing.JTextField bboxSizeTFY;
    private javax.swing.JTextField bboxSizeTFZ;
    private javax.swing.JLabel centerLabel;
    private javax.swing.JComboBox<String> centerModificationComboBox;
    private javax.swing.JTextField centerXTF;
    private javax.swing.JTextField centerYTF;
    private javax.swing.JTextField centerZTF;
    private org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JTextField descriptionTF;
    private javax.swing.JPanel fieldsPanel;
    private javax.swing.JLabel hintLabel;
    private org.web3d.x3d.palette.items.ExpandableList infoExpandableList;
    private javax.swing.JPanel infoPanel;
    private javax.swing.JTabbedPane jTabbedPane1;
    private org.web3d.x3d.palette.items.ExpandableList jointBindingPositionsExpandableList;
    private javax.swing.JPanel jointBindingPositionsPanel;
    private org.web3d.x3d.palette.items.ExpandableList jointBindingRotationsExpandableList;
    private javax.swing.JPanel jointBindingRotationsPanel;
    private org.web3d.x3d.palette.items.ExpandableList jointBindingScalesExpandableList;
    private javax.swing.JPanel jointBindingScalesPanel;
    private javax.swing.JComboBox<String> loaComboBox;
    private javax.swing.JLabel loaLabel;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTextField;
    private javax.swing.JLabel nameWarningLabel;
    private javax.swing.JPanel nodeHintPanel;
    private javax.swing.JButton normalizeRotationValuesButton;
    private javax.swing.JTextField rotationAngleTF;
    private javax.swing.JButton rotationCalculatorlButton;
    private javax.swing.JLabel rotationLabel;
    private javax.swing.JTextField rotationXaxisTF;
    private javax.swing.JTextField rotationYaxisTF;
    private javax.swing.JTextField rotationZaxisTF;
    private javax.swing.JLabel scaleLabel;
    private javax.swing.JTextField scaleOrientationAngleTF;
    private javax.swing.JButton scaleOrientationCalculatorlButton;
    private javax.swing.JLabel scaleOrientationLabel;
    private javax.swing.JTextField scaleOrientationXaxisTF;
    private javax.swing.JTextField scaleOrientationYaxisTF;
    private javax.swing.JTextField scaleOrientationZaxisTF;
    private javax.swing.JComboBox<String> scaleSelectionComboBox;
    private javax.swing.JTextField scaleXTF;
    private javax.swing.JTextField scaleYTF;
    private javax.swing.JTextField scaleZTF;
    private javax.swing.JButton skeletalConfigurationDefaultButton;
    private javax.swing.JLabel skeletalConfigurationLabel;
    private javax.swing.JTextField skeletalConfigurationTF;
    private javax.swing.JLabel translationLabel;
    private javax.swing.JComboBox<String> translationModificationComboBox;
    private javax.swing.JTextField translationXTF;
    private javax.swing.JTextField translationYTF;
    private javax.swing.JTextField translationZTF;
    private javax.swing.JComboBox<String> versionCombo;
    private javax.swing.JLabel versionLabel;
    private javax.swing.JLabel xLabel;
    private javax.swing.JLabel yLabel;
    private javax.swing.JLabel zLabel;
    // End of variables declaration//GEN-END:variables

}
