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
import javax.swing.DefaultComboBoxModel;
import javax.swing.text.JTextComponent;
import org.openide.util.HelpCtx;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * UNITCustomizer.java
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class UNITCustomizer extends BaseCustomizer
{
  private final UNIT unit;
  private final JTextComponent target;

  public UNITCustomizer(UNIT unit, JTextComponent target)
  {
    super(unit);
    this.unit = unit;
    this.target = target;
   
    HelpCtx.setHelpIDString(this, "UNIT_ELEM_HELPID");
    
    initComponents();
    
    if (unit.getCategory().equals("angle"))
    {
      nameComboBox.setModel (new DefaultComboBoxModel<> (UNIT_ATTR_NAME_ANGLE_CHOICES));
    }
    else if (unit.getCategory().equals("length"))
    {
      nameComboBox.setModel (new DefaultComboBoxModel<> (UNIT_ATTR_NAME_LENGTH_CHOICES));
    }
    else if (unit.getCategory().equals("force"))
    {
      nameComboBox.setModel (new DefaultComboBoxModel<> (UNIT_ATTR_NAME_FORCE_CHOICES));
    }
    else if (unit.getCategory().equals("mass"))
    {
      nameComboBox.setModel (new DefaultComboBoxModel<> (UNIT_ATTR_NAME_MASS_CHOICES));
    }
    else  // hopefully unreachable
    {
      nameComboBox.setModel (new DefaultComboBoxModel<String> ());
    }
    
    // must complete comboBox initializations before setting any values via callbacks to avoid NPE
    // name choices correspond to category value
    reconfigureNameComboBox();
    
    categoryComboBox.setSelectedItem(unit.getCategory());
    
    nameComboBox.setSelectedItem(unit.getName());
    
    conversionFactorTextField.setText(unit.getConversionFactor());
  }
  /**
   * Optional user choices for nameComboBox correspond to built-in conversions for current category.
   */
  private void reconfigureNameComboBox ()
  {
      // do not save previous name if changing choices for a new category, in order to prevent mismatched conversions
      
      // however must be careful to leave user-provided name alone since that is used for a custom conversion
      
      if (categoryComboBox.getSelectedItem().equals("angle"))
      {
          nameComboBox.setModel (new DefaultComboBoxModel<> (UNIT_ATTR_NAME_ANGLE_CHOICES));
      }
      else if (categoryComboBox.getSelectedItem().equals("length"))
      {
          nameComboBox.setModel (new DefaultComboBoxModel<> (UNIT_ATTR_NAME_LENGTH_CHOICES));
      }
      else if (categoryComboBox.getSelectedItem().equals("force"))
      {
          nameComboBox.setModel (new DefaultComboBoxModel<> (UNIT_ATTR_NAME_FORCE_CHOICES));
      }
      else if (categoryComboBox.getSelectedItem().equals("mass"))
      {
          nameComboBox.setModel (new DefaultComboBoxModel<> (UNIT_ATTR_NAME_MASS_CHOICES));
      }
      else  // hopefully unreachable
      {
          nameComboBox.setModel (new DefaultComboBoxModel<String> ());
      }
  }
  /**
   * Synchronize tooltip explaining current selection of nameComboBox, which correspond to built-in conversions for current category.
   */
  private void reconfigureNameComboBoxToolTipText ()
  {
      if (nameComboBox.getSelectedIndex() == -1) 
      {
          nameComboBox.setToolTipText(""); // no tooltip for user-defined or other-category name
      }
      else if (categoryComboBox.getSelectedItem().equals("angle"))
      {
           nameComboBox.setToolTipText(UNIT_ATTR_NAME_ANGLE_TOOLTIPS[nameComboBox.getSelectedIndex()]);
      }
      else if (categoryComboBox.getSelectedItem().equals("length"))
      {
           nameComboBox.setToolTipText(UNIT_ATTR_NAME_LENGTH_TOOLTIPS[nameComboBox.getSelectedIndex()]);
      }
      else if (categoryComboBox.getSelectedItem().equals("force"))
      {
           nameComboBox.setToolTipText(UNIT_ATTR_NAME_FORCE_TOOLTIPS[nameComboBox.getSelectedIndex()]);
      }
      else if (categoryComboBox.getSelectedItem().equals("mass"))
      {
           nameComboBox.setToolTipText(UNIT_ATTR_NAME_MASS_TOOLTIPS[nameComboBox.getSelectedIndex()]);
      }
      else // hopefully unreachable
      {
          nameComboBox.setToolTipText("");
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        nodeParentHintPanel = new javax.swing.JPanel();
        hintLabel2 = new javax.swing.JLabel();
        hintLabel1 = new javax.swing.JLabel();
        categoryLabel = new javax.swing.JLabel();
        categoryComboBox = new javax.swing.JComboBox<String>();
        nameLabel = new javax.swing.JLabel();
        nameComboBox = new javax.swing.JComboBox<String>();
        conversionFactorLabel = new javax.swing.JLabel();
        conversionFactorTextField = new javax.swing.JTextField();
        nodeDescriptionHintPanel = new javax.swing.JPanel();
        hintLabel3 = new javax.swing.JLabel();
        hintLabel4 = new javax.swing.JLabel();

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        setLayout(new java.awt.GridBagLayout());

        nodeParentHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nodeParentHintPanel.setToolTipText("unit is only valid in X3D version 3.3+");
        nodeParentHintPanel.setLayout(new java.awt.GridBagLayout());

        hintLabel2.setText("<html>The <b>unit</b> statement defines data conversion factors for a scene.");
        hintLabel2.setToolTipText("unit is only valid in X3D version 3.3+");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 3, 6);
        nodeParentHintPanel.add(hintLabel2, gridBagConstraints);

        hintLabel1.setText("<html><b>unit</b> statements follow <b>head</b>, <b>component</b> (and precede <b>meta</b>) statements");
        hintLabel1.setToolTipText("unit is only valid in X3D version 3.3+");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 6, 6);
        nodeParentHintPanel.add(hintLabel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 10, 3);
        add(nodeParentHintPanel, gridBagConstraints);

        categoryLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        categoryLabel.setText("category");
        categoryLabel.setToolTipText("category of base units");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 60, 3, 3);
        add(categoryLabel, gridBagConstraints);

        categoryComboBox.setModel(new DefaultComboBoxModel<>(UNIT_ATTR_CATEGORY_CHOICES));
        categoryComboBox.setToolTipText("category of units");
        categoryComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                categoryComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 100);
        add(categoryComboBox, gridBagConstraints);

        nameLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        nameLabel.setText("name");
        nameLabel.setToolTipText("user-defined name for this conversion factor (for example, FeetToMeters)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 60, 3, 3);
        add(nameLabel, gridBagConstraints);

        nameComboBox.setEditable(true);
        nameComboBox.setToolTipText("user-defined name for this conversion factor (for example, FeetToMeters)");
        nameComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 100);
        add(nameComboBox, gridBagConstraints);

        conversionFactorLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        conversionFactorLabel.setText("conversionFactor");
        conversionFactorLabel.setToolTipText("positive double-precision factor that converts new base unit to default base unit");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 60, 3, 3);
        add(conversionFactorLabel, gridBagConstraints);

        conversionFactorTextField.setToolTipText("positive double-precision factor that converts new base unit to default base unit");
        conversionFactorTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                conversionFactorTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 100);
        add(conversionFactorTextField, gridBagConstraints);

        nodeDescriptionHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nodeDescriptionHintPanel.setToolTipText("Derived units are handled automatically throughout the X3D scene");
        nodeDescriptionHintPanel.setLayout(new java.awt.GridBagLayout());

        hintLabel3.setText("Corresponding derived units are also consistently redefined for");
        hintLabel3.setToolTipText("Derived units are handled automatically throughout the X3D scene");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 1, 6);
        nodeDescriptionHintPanel.add(hintLabel3, gridBagConstraints);

        hintLabel4.setText("matching acceleration, angular rate, area, speed and volume units.");
        hintLabel4.setToolTipText("Derived units are handled automatically throughout the X3D scene");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(1, 6, 6, 6);
        nodeDescriptionHintPanel.add(hintLabel4, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 0, 3);
        add(nodeDescriptionHintPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void nameComboBoxActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_nameComboBoxActionPerformed
    {//GEN-HEADEREND:event_nameComboBoxActionPerformed
      reconfigureNameComboBoxToolTipText (); // ensure synchronized, clear if different name added
        
      // reset conversionFactor textField value as appropriate to match new selection
      if (nameComboBox.getSelectedIndex() == -1) 
      {
          nameComboBox.setToolTipText(""); // no tooltip for user-defined or other-category name
      }
      else if (categoryComboBox.getSelectedItem().equals("angle"))
      {
           conversionFactorTextField.setText(UNIT_ATTR_NAME_ANGLE_FACTORS[nameComboBox.getSelectedIndex()]);
      }
      else if (categoryComboBox.getSelectedItem().equals("length"))
      {
           conversionFactorTextField.setText(UNIT_ATTR_NAME_LENGTH_FACTORS[nameComboBox.getSelectedIndex()]);
      }
      else if (categoryComboBox.getSelectedItem().equals("force"))
      {
           conversionFactorTextField.setText(UNIT_ATTR_NAME_FORCE_FACTORS[nameComboBox.getSelectedIndex()]);
      }
      else if (categoryComboBox.getSelectedItem().equals("mass"))
      {
           conversionFactorTextField.setText(UNIT_ATTR_NAME_MASS_FACTORS[nameComboBox.getSelectedIndex()]);
      }
      else // hopefully unreachable
      {
          conversionFactorTextField.setText(UNIT_ATTR_CONVERSIONFACTOR_DFLT);
      }
    }//GEN-LAST:event_nameComboBoxActionPerformed

    private void categoryComboBoxActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_categoryComboBoxActionPerformed
    {//GEN-HEADEREND:event_categoryComboBoxActionPerformed
        // set tooltip matching the predefined category names (no user choices allowed)
        for (int index = 0; index < UNIT_ATTR_CATEGORY_CHOICES.length; index++)
        {
            if (categoryComboBox.getSelectedItem().equals(UNIT_ATTR_CATEGORY_CHOICES[index])) 
            {
                categoryComboBox.setToolTipText(UNIT_ATTR_CATEGORY_CHOICES_DEFAULT_TOOLTIPS[index]);
                break;
            }
        }
        if (nameComboBox.getSelectedIndex() == -1) 
        {
              nameComboBox.setToolTipText(""); // no tooltip for user-defined or other-category name
        }
        else // reset for new category
        {
            reconfigureNameComboBox ();             // name choices correspond to category
            nameComboBox.setSelectedIndex(0);       // reset to avoid retaining prior conversion TODO correct?
            reconfigureNameComboBoxToolTipText ();
            conversionFactorTextField.setText (""); // reset to avoid retaining prior conversion
        }
}//GEN-LAST:event_categoryComboBoxActionPerformed

private void conversionFactorTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conversionFactorTextFieldActionPerformed
// TODO if zero or negative, throw warning dialog
}//GEN-LAST:event_conversionFactorTextFieldActionPerformed
  
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> categoryComboBox;
    private javax.swing.JLabel categoryLabel;
    private javax.swing.JLabel conversionFactorLabel;
    private javax.swing.JTextField conversionFactorTextField;
    private javax.swing.JLabel hintLabel1;
    private javax.swing.JLabel hintLabel2;
    private javax.swing.JLabel hintLabel3;
    private javax.swing.JLabel hintLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JComboBox<String> nameComboBox;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JPanel nodeDescriptionHintPanel;
    private javax.swing.JPanel nodeParentHintPanel;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_UNIT";
  }

  @Override
  public void unloadInput()
  {
     String category         = ((String)categoryComboBox.getSelectedItem()).trim();
     String conversionFactor = conversionFactorTextField.getText().trim();
     
     if (conversionFactor.equals("0") || conversionFactor.equals("0.") || conversionFactor.equals("0.0"))
     {
         NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                "<html>Found illegal zero value for <b>conversionFactor</b>, reset to 1</html>", NotifyDescriptor.WARNING_MESSAGE);
         DialogDisplayer.getDefault().notify(descriptor);
         conversionFactor = "1";
     }
     else if (conversionFactor.startsWith("-"))
     {
         NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                "<html>Found illegal negative value for <b>conversionFactor</b>, reset to  positive value</html>", NotifyDescriptor.WARNING_MESSAGE);
         DialogDisplayer.getDefault().notify(descriptor);
         conversionFactor = conversionFactor.substring(1);
     }
     unit.setConversionFactor(conversionFactor);
     
     unit.setCategory(category);
     
     if     ((nameComboBox.getSelectedIndex() >= 0) || (nameComboBox.getSelectedItem() != null)) // editable
     {
		 String authorLabel = ((String)nameComboBox.getSelectedItem()).trim();
		 if (!authorLabel.endsWith("ToMeters"))
			  authorLabel += "ToMeters";
         unit.setName(authorLabel);
     }
     else if (conversionFactor.equals("1") || conversionFactor.equals("1.") || conversionFactor.equals("1.0"))
             unit.setName(category + "Identity"); // create an appropriate name if conversionFactor value is 1.0
     else    unit.setName(""); // validation error
  }  
}
