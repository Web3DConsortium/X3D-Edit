/*
Copyright (c) 1995-2021 held by the author(s).  All rights reserved.
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

//import java.awt.StackedLayout;
import java.awt.Color;
import java.net.URL;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.text.JTextComponent;
import org.openide.awt.HtmlBrowser.URLDisplayer;
import org.openide.util.HelpCtx;
import static org.web3d.x3d.palette.X3DPaletteUtilities.escapeXmlCharacters;
import org.web3d.x3d.types.X3DSchemaData;
import static org.web3d.x3d.types.X3DSchemaData.FIELD_ATTR_ACCESSTYPE_INITIALIZEONLY;
import static org.web3d.x3d.types.X3DSchemaData.FIELD_ATTR_ACCESSTYPE_INPUTOUTPUT;

/**
 * CYLINDERCustomizer.java
 * Created on July 12, 2007, 3:36 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class FIELDCustomizer extends BaseCustomizer
{
  private final JTextComponent target;
  private final FIELD field;
  private boolean fieldAttributeValueAllowed = true;
//private boolean removeFieldInitializationValue = false;
  private boolean displayInitializationComplete;
  private String previousComputedDefaultValue;

  /** Creates new form FIELDCustomizer
   * @param field
   * @param target Swing component of interest  
   */
  public FIELDCustomizer(FIELD field, JTextComponent target)
  {
    super(field);
    this.target = target;
    this.field = field;

    HelpCtx.setHelpIDString(this, "FIELD_ELEM_HELPID");

    initComponents();
    displayInitializationComplete = false; // avoid duplicate initialization invocations by callback functions
   
    nameTF.setText(field.getName());
    typeCB.setModel(new DefaultComboBoxModel<>(X3DSchemaData.FIELD_ATTR_TYPE_CHOICES));
    String fieldType = field.getType();
    if (fieldType != null && fieldType.length() > 0)
      typeCB.setSelectedItem(fieldType);
    else
      typeCB.setSelectedIndex(0);

    accessTypeCB.setModel(new DefaultComboBoxModel<>(X3DSchemaData.FIELD_ATTR_ACCESSTYPE_CHOICES));
    String accessType = field.getAccessType();
    if (accessType != null && accessType.length() > 0)
      accessTypeCB.setSelectedItem(accessType);
    else
      accessTypeCB.setSelectedIndex(0);
    
    valueTextArea.setText(field.getValue());
    setDefaultValue();
    displayValueEntryOrValueWarning();
    if ((field.getValue().length() > 0) && !fieldAttributeValueAllowed)
    {
        int ret = JOptionPane.showConfirmDialog(this,
                    "<html><p align='center'>Remove illegal initialization <b>value='" + field.getValue() + 
                        "'</b> from definition of <b>" + nameTF.toString() + "</b> field?</p>" +
                    "<p>" + valueTextArea.getToolTipText() + "</p>",
                    "Confirm...", JOptionPane.YES_NO_OPTION);
        if (ret == JOptionPane.YES_OPTION)
        {
              valueTextArea.setText("");
        }
    }
    displayInitializationComplete = true; // avoid duplicate initialization invocations by callback functions
    
    appinfoTF.setText(field.getAppinfo());
    documentationTF.setText(field.getDocumentation());
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

        nameLab = new javax.swing.JLabel();
        nameTF = new javax.swing.JTextField();
        typeLab = new javax.swing.JLabel();
        typeCB = new javax.swing.JComboBox<>();
        accessTypeLabel = new javax.swing.JLabel();
        accessTypeCB = new javax.swing.JComboBox<>();
        appInfoLab = new javax.swing.JLabel();
        appinfoTF = new javax.swing.JTextField();
        appinfoInitializeButton = new javax.swing.JButton();
        documentationLabel = new javax.swing.JLabel();
        documentationTF = new javax.swing.JTextField();
        openDocumentationButton = new javax.swing.JButton();
        valueLabel = new javax.swing.JLabel();
        warningLabel = new javax.swing.JLabel();
        valueTextAreaScrollPane = new javax.swing.JScrollPane();
        valueTextArea = new javax.swing.JTextArea();
        hintLabel = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(540, 480));
        setLayout(new java.awt.GridBagLayout());

        nameLab.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        nameLab.setText("name");
        nameLab.setToolTipText("field name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 45;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nameLab, gridBagConstraints);

        nameTF.setToolTipText("field name must be unique for this node");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 250;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nameTF, gridBagConstraints);

        typeLab.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        typeLab.setText("type");
        typeLab.setToolTipText("select data type");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 49;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(typeLab, gridBagConstraints);

        typeCB.setEditable(true);
        typeCB.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        typeCB.setToolTipText("type is SF Single or MF Multiple Field type");
        typeCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeCBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(typeCB, gridBagConstraints);

        accessTypeLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        accessTypeLabel.setText("accessType");
        accessTypeLabel.setToolTipText("accessType determines event input, event output, or persistent state");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 15;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(accessTypeLabel, gridBagConstraints);

        accessTypeCB.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        accessTypeCB.setToolTipText("select accessType");
        accessTypeCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accessTypeCBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(accessTypeCB, gridBagConstraints);

        appInfoLab.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        appInfoLab.setText("appinfo");
        appInfoLab.setToolTipText("application information:  simple tooltip description");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 35;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(appInfoLab, gridBagConstraints);

        appinfoTF.setToolTipText("application information:  simple tooltip description");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(appinfoTF, gridBagConstraints);

        appinfoInitializeButton.setText("default");
        appinfoInitializeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                appinfoInitializeButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(appinfoInitializeButton, gridBagConstraints);

        documentationLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        documentationLabel.setText("documentation");
        documentationLabel.setToolTipText("url for field documentation");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(documentationLabel, gridBagConstraints);

        documentationTF.setToolTipText("url for field documentation");
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
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(documentationTF, gridBagConstraints);

        openDocumentationButton.setText("open");
        openDocumentationButton.setToolTipText("open documentation url in browser");
        openDocumentationButton.setEnabled(false);
        openDocumentationButton.setMargin(new java.awt.Insets(2, 12, 2, 12));
        openDocumentationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                openDocumentationButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(openDocumentationButton, gridBagConstraints);

        valueLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        valueLabel.setText("value");
        valueLabel.setToolTipText("initial value of field, must match type");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 45;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(valueLabel, gridBagConstraints);

        warningLabel.setBackground(new Color(getBackground().getRed(),getBackground().getGreen(),getBackground().getBlue(),80));
        warningLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        warningLabel.setText("<html><p>No <i>value</i> attribute initialization is allowed for </p>\n<ul>   \n<li>accessType inputOnly or outputOnly</li>   \n<li>contained SFNode or MFNode content</li>   \n<li>ExternProtoDeclare field definitions</li>   \n<li>field with IS-connect interface</li> \n</ul> \n<p align='center'>(see tooltip for specific details)</p> </html>");
        warningLabel.setToolTipText("initialization value");
        warningLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        warningLabel.setOpaque(true);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        add(warningLabel, gridBagConstraints);
        warningLabel.getAccessibleContext().setAccessibleName("<html><center>No initial value for accessType inputOnly or outputOnly, or for child of ExternProtoDeclare node.");

        valueTextAreaScrollPane.setPreferredSize(new java.awt.Dimension(325, 120));

        valueTextArea.setColumns(20);
        valueTextArea.setLineWrap(true);
        valueTextArea.setRows(5);
        valueTextArea.setWrapStyleWord(true);
        valueTextArea.setPreferredSize(new java.awt.Dimension(760, 290));
        valueTextAreaScrollPane.setViewportView(valueTextArea);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.ipadx = 150;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(valueTextAreaScrollPane, gridBagConstraints);

        hintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel.setText("<html><p align=\"center\">A <b>field</b> statement defines an interface attribute or node.</p>\n<br />\n<p align=\"center\">Hint: first add a parent <b>Script</b> node or <b>ProtoDeclare</b><b>ProtoInterface</b> statements\n<br /> before defining a new <b>field</b>. </p>\n");
        hintLabel.setToolTipText(org.openide.util.NbBundle.getMessage(FIELDCustomizer.class, "INTEGERSEQUENCERCustomizer.eventLabel3.toolTipText")); // NOI18N
        hintLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        hintLabel.setPreferredSize(new java.awt.Dimension(950, 620));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(hintLabel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
    private void typeCBActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_typeCBActionPerformed
    {//GEN-HEADEREND:event_typeCBActionPerformed
      if (displayInitializationComplete)
      {
          setDefaultValue();
          displayValueEntryOrValueWarning ();
      }
    }//GEN-LAST:event_typeCBActionPerformed

    private void accessTypeCBActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_accessTypeCBActionPerformed
    {//GEN-HEADEREND:event_accessTypeCBActionPerformed
      if (displayInitializationComplete)
      {
          setDefaultValue();
          displayValueEntryOrValueWarning ();
      }
    }//GEN-LAST:event_accessTypeCBActionPerformed

    private void documentationTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_documentationTFActionPerformed
        openDocumentationButton.setEnabled(!documentationTF.getText().trim().isEmpty());
    }//GEN-LAST:event_documentationTFActionPerformed

    private void openDocumentationButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openDocumentationButtonActionPerformed

        if (documentationTF.getText().trim().isEmpty()) {             return;         }         URL url = null;         try {             url = new URL(documentationTF.getText());             URLDisplayer.getDefault().showURL(url);         } catch (Exception e) {             System.out.println("Failed attempt to launch " + url.toString());         }     }//GEN-LAST:event_openDocumentationButtonActionPerformed

    private void documentationTFPropertyChange(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_documentationTFPropertyChange
    {//GEN-HEADEREND:event_documentationTFPropertyChange
        openDocumentationButton.setEnabled(!documentationTF.getText().trim().isEmpty());
    }//GEN-LAST:event_documentationTFPropertyChange

    private void appinfoInitializeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_appinfoInitializeButtonActionPerformed
        
        if (valueTextArea.getText().trim().isEmpty())
        {
            appinfoTF.setText("");
        }
        else
        {
            appinfoTF.setText("default value: " + valueTextArea.getText().trim());
        }
    }//GEN-LAST:event_appinfoInitializeButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> accessTypeCB;
    private javax.swing.JLabel accessTypeLabel;
    private javax.swing.JLabel appInfoLab;
    private javax.swing.JButton appinfoInitializeButton;
    private javax.swing.JTextField appinfoTF;
    private javax.swing.JLabel documentationLabel;
    private javax.swing.JTextField documentationTF;
    private javax.swing.JLabel hintLabel;
    private javax.swing.JLabel nameLab;
    private javax.swing.JTextField nameTF;
    private javax.swing.JButton openDocumentationButton;
    private javax.swing.JComboBox<String> typeCB;
    private javax.swing.JLabel typeLab;
    private javax.swing.JLabel valueLabel;
    private javax.swing.JTextArea valueTextArea;
    private javax.swing.JScrollPane valueTextAreaScrollPane;
    private javax.swing.JLabel warningLabel;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_FIELD";
  }

  @Override
  public void unloadInput() throws IllegalArgumentException
  {
    field.setName(nameTF.getText().trim());
    field.setType(((String) typeCB.getSelectedItem()).trim());
    field.setAccessType((String) accessTypeCB.getSelectedItem());
    if (valueTextArea.isEnabled())
      field.setValue      (escapeXmlCharacters(valueTextArea.getText().trim()));
    else
      field.setValue("");
    field.setAppinfo      (escapeXmlCharacters(appinfoTF.getText().trim()));
    field.setDocumentation(escapeXmlCharacters(documentationTF.getText().trim()));
  }

    private void displayValueEntryOrValueWarning ()
    {
        if      (field.isExternProtoDeclareChild())
        {
            fieldAttributeValueAllowed = false;
            enableValueTextArea (fieldAttributeValueAllowed, "Initialization value not allowed for field inside ExternProtoDeclare");
        }
        else if (field.isConnectedWithIS())
        {
            fieldAttributeValueAllowed = false;
            enableValueTextArea (fieldAttributeValueAllowed, "Initialization value not allowed for field with IS-connect interface");
        }
        else if (typeCB.getSelectedItem().toString().contentEquals("SFNode") ||
                 typeCB.getSelectedItem().toString().contentEquals("MFNode"))
        {
            fieldAttributeValueAllowed = false;
            enableValueTextArea (fieldAttributeValueAllowed, "Initialization value not allowed for field with contained " + typeCB.getSelectedItem().toString() + " content");
        }
        else if (accessTypeCB.getSelectedItem().toString().contentEquals(FIELD_ATTR_ACCESSTYPE_INITIALIZEONLY) ||
                 accessTypeCB.getSelectedItem().toString().contentEquals(FIELD_ATTR_ACCESSTYPE_INPUTOUTPUT))
        {
            fieldAttributeValueAllowed = true;
            String tooltip;
            if      (typeCB.getSelectedItem().toString().contentEquals("SFString"))
                   tooltip = "Empty string is an allowed value for type SFString";
            else if (typeCB.getSelectedItem().toString().contentEquals("MFString"))
                   tooltip = "Empty string array is an allowed value for type MFString";
            else if (typeCB.getSelectedItem().toString().startsWith("MF"))
                   tooltip = "Empty array is an allowed value for type " + typeCB.getSelectedItem().toString();
            else tooltip = "Initialization value required for accessType=\"" + accessTypeCB.getSelectedItem().toString() + "\"";
            enableValueTextArea (fieldAttributeValueAllowed, tooltip);
        }
        else // accessType inputOnly, outputOnly (which are both transient)
        {
            fieldAttributeValueAllowed = false;
            enableValueTextArea (fieldAttributeValueAllowed, "Initialization value not allowed for accessType=\"" + field.getAccessType() + "\")");
        }
    }

  private void enableValueTextArea(boolean valueAllowed, String tooltip)
  {
    valueTextArea.setToolTipText(tooltip);
    
    valueLabel.setEnabled(valueAllowed);
    valueTextArea.setEnabled(valueAllowed);  // this is checked in unloadInput
    valueTextArea.setVisible(valueAllowed);
    valueTextArea.setEditable(valueAllowed);
    
    int policy = valueAllowed ? JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED : JScrollPane.HORIZONTAL_SCROLLBAR_NEVER;
    valueTextAreaScrollPane.setHorizontalScrollBarPolicy(policy);
    policy     = valueAllowed ? JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED : JScrollPane.VERTICAL_SCROLLBAR_NEVER;
    valueTextAreaScrollPane.setVerticalScrollBarPolicy(policy);

    warningLabel.setVisible(!valueAllowed);
    warningLabel.setToolTipText(tooltip);
   }

    private void setDefaultValue ()
    {
        String typeValue = (String) typeCB.getSelectedItem();

        // tooltip, warning and textarea display tests handled externally

//        if ((typeValue.equalsIgnoreCase("SFBool") == false) && (valueTextArea.getText().equalsIgnoreCase("true"))) {
//            // clear built-in SFBool default if user already made some modifications, so that value get restored as appropriate
//            // otherwise, hands off - don't second-guess author's intent
//            valueTextArea.setText("");
//        }
        // do not insert default value if a value already exists, unless it was previously inserted
        if ((valueTextArea.getText().length() > 0) && !valueTextArea.getText().equals(previousComputedDefaultValue))
        {
            return;
        }

        // insert default value, as appropriate

        if (typeValue.equalsIgnoreCase("SFBool")) {
            valueTextArea.setText("true");
        } else if (typeValue.equalsIgnoreCase("SFTime")) {
            valueTextArea.setText("-1");
        } else if (typeValue.equalsIgnoreCase("SFInt32")) {
            valueTextArea.setText("0");
        } else if (typeValue.equalsIgnoreCase("SFFloat") ||
                   typeValue.equalsIgnoreCase("SFDouble")) {
            valueTextArea.setText("0.0");
        } else if (typeValue.equalsIgnoreCase("SFVec2f") ||
                   typeValue.equalsIgnoreCase("SFVec2d")) {
            valueTextArea.setText("0.0 0.0");
        } else if (typeValue.equalsIgnoreCase("SFVec3f") ||
                   typeValue.equalsIgnoreCase("SFVec3d") ||
                   typeValue.equalsIgnoreCase("SFColor")) {
            valueTextArea.setText("0.0 0.0 0.0");
        } else if (typeValue.equalsIgnoreCase("SFColorRGBA")) {
            valueTextArea.setText("0.0 0.0 0.0 0.0");
        } else if (typeValue.equalsIgnoreCase("SFImage")) {
            valueTextArea.setText("0 0 0");
        } else if (typeValue.equalsIgnoreCase("SFVec4f") ||
                   typeValue.equalsIgnoreCase("SFVec4d")) {
            valueTextArea.setText("0 0 0 1");
        } else if (typeValue.equalsIgnoreCase("SFRotation")) {
            valueTextArea.setText("0 1 0 0.0"); // preferable default value, equivalent to specification default value 0 0 1 0
        } else if (typeValue.equalsIgnoreCase("SFMatrix3f") ||
                   typeValue.equalsIgnoreCase("SFMatrix3d")) {
            valueTextArea.setText("1 0 0\n0 1 0\n0 0 1");
        } else if (typeValue.equalsIgnoreCase("SFMatrix4f") ||
                   typeValue.equalsIgnoreCase("SFMatrix4d")) {
            valueTextArea.setText("1 0 0 0\n0 1 0 0\n0 0 1 0\n0 0 0 1");
        }
        // SFString default is empty string
        // MF array defaults are empty arrays
        else valueTextArea.setText("");
        
        previousComputedDefaultValue = valueTextArea.getText();
        valueTextArea.repaint();
    }
}
