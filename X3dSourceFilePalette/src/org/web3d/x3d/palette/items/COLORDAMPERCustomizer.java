/*
Copyright (c) 1995-2022 held by the author(s) .  All rights reserved.
 
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
import javax.swing.text.JTextComponent;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.HelpCtx;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFColor;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFFloat;
/**
 * COLORDAMPERCustomizer.java
 * Created on 2 February 2010
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class COLORDAMPERCustomizer extends BaseCustomizer
{
  private COLORDAMPER colorDamper;
  private JTextComponent target;

  public COLORDAMPERCustomizer(COLORDAMPER colorDamper, JTextComponent target)
  {
    super(colorDamper);
    this.colorDamper = colorDamper;
    this.target = target;
                   
    HelpCtx.setHelpIDString(this, "COLORDAMPER_ELEM_HELPID");
    
    initComponents();

          tauTF.setText            (colorDamper.getTau());
    toleranceTF.setText            (colorDamper.getTolerance());
    orderComboBox.setSelectedIndex (colorDamper.getOrder());

    initialValue0TextField.setText (colorDamper.getInitialValue0().toString ());
    initialValue1TextField.setText (colorDamper.getInitialValue1().toString ());
    initialValue2TextField.setText (colorDamper.getInitialValue2().toString ());
    updateInitialValueColorChooser();

    initialDestination0TextField.setText (colorDamper.getInitialDestination0().toString ());
    initialDestination1TextField.setText (colorDamper.getInitialDestination1().toString ());
    initialDestination2TextField.setText (colorDamper.getInitialDestination2().toString ());
    updateInitialDestinationColorChooser();
  }

  private void updateInitialValueColorChooser()
  {
    initialValueColorChooser.setColor(
            (new SFColor(initialValue0TextField.getText(),
                         initialValue1TextField.getText(),
                         initialValue2TextField.getText())).getColor());
  }

  private void updateInitialDestinationColorChooser()
  {
    initialDestinationColorChooser.setColor(
            (new SFColor(initialDestination0TextField.getText(),
                         initialDestination1TextField.getText(),
                         initialDestination2TextField.getText())).getColor());
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel1 = getDEFUSEpanel();
        orderLabel = new javax.swing.JLabel();
        orderComboBox = new javax.swing.JComboBox<>();
        orderHintLabel = new javax.swing.JLabel();
        tauLabel = new javax.swing.JLabel();
        tauTF = new javax.swing.JTextField();
        tauHintLabel = new javax.swing.JLabel();
        toleranceLabel = new javax.swing.JLabel();
        toleranceTF = new javax.swing.JTextField();
        toleranceHintLabel = new javax.swing.JLabel();
        initialValueLabel = new javax.swing.JLabel();
        initialDestinationLabel = new javax.swing.JLabel();
        initialValue0TextField = new javax.swing.JTextField();
        initialValue1TextField = new javax.swing.JTextField();
        initialValue2TextField = new javax.swing.JTextField();
        initialValueColorChooser = new net.java.dev.colorchooser.ColorChooser();
        initialDestination0TextField = new javax.swing.JTextField();
        initialDestination1TextField = new javax.swing.JTextField();
        initialDestination2TextField = new javax.swing.JTextField();
        initialDestinationColorChooser = new net.java.dev.colorchooser.ColorChooser();
        followerFigureLabel = new javax.swing.JLabel();
        hintLabel = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(610, 592));
        setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel1, gridBagConstraints);

        orderLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        orderLabel.setText("order");
        orderLabel.setToolTipText("number of internal filters (larger means smoother response, longer delay)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 20, 3, 3);
        add(orderLabel, gridBagConstraints);

        orderComboBox.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "0", "1", "2", "3", "4", "5" }));
        orderComboBox.setToolTipText("number of internal filters (larger means smoother response, longer delay)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(orderComboBox, gridBagConstraints);

        orderHintLabel.setText("higher order is smoother");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(orderHintLabel, gridBagConstraints);

        tauLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        tauLabel.setText("tau");
        tauLabel.setToolTipText("time constant for filter response in seconds");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 20, 3, 3);
        add(tauLabel, gridBagConstraints);

        tauTF.setToolTipText("time constant for filter response in seconds");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(tauTF, gridBagConstraints);

        tauHintLabel.setText("time constant of filter for speed of response");
        tauHintLabel.setToolTipText("tau=0 is immediate");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(tauHintLabel, gridBagConstraints);

        toleranceLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        toleranceLabel.setText("tolerance");
        toleranceLabel.setToolTipText("absolute value for satisfactory completion proximity (-1 lets browser choose)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 20, 3, 3);
        add(toleranceLabel, gridBagConstraints);

        toleranceTF.setToolTipText("absolute value for satisfactory completion proximity (-1 lets browser choose)");
        toleranceTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                toleranceTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(toleranceTF, gridBagConstraints);

        toleranceHintLabel.setText("determining when complete");
        toleranceHintLabel.setToolTipText("tolerance=-1 lets browser decide");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(toleranceHintLabel, gridBagConstraints);

        initialValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        initialValueLabel.setText("initialValue");
        initialValueLabel.setToolTipText("initial starting value");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 20, 3, 3);
        add(initialValueLabel, gridBagConstraints);

        initialDestinationLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        initialDestinationLabel.setText("initialDestination");
        initialDestinationLabel.setToolTipText("initial goal value");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 20, 3, 3);
        add(initialDestinationLabel, gridBagConstraints);

        initialValue0TextField.setColumns(3);
        initialValue0TextField.setToolTipText("initial starting value");
        initialValue0TextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                initialValue0TextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(initialValue0TextField, gridBagConstraints);

        initialValue1TextField.setColumns(3);
        initialValue1TextField.setToolTipText("initial starting value");
        initialValue1TextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                initialValue1TextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(initialValue1TextField, gridBagConstraints);

        initialValue2TextField.setColumns(3);
        initialValue2TextField.setToolTipText("initial starting value");
        initialValue2TextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                initialValue2TextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(initialValue2TextField, gridBagConstraints);

        initialValueColorChooser.setMinimumSize(new java.awt.Dimension(15, 15));
        initialValueColorChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                initialValueColorChooserActionPerformed(evt);
            }
        });
        initialValueColorChooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                initialValueColorChooserPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout initialValueColorChooserLayout = new javax.swing.GroupLayout(initialValueColorChooser);
        initialValueColorChooser.setLayout(initialValueColorChooserLayout);
        initialValueColorChooserLayout.setHorizontalGroup(
            initialValueColorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );
        initialValueColorChooserLayout.setVerticalGroup(
            initialValueColorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 20);
        add(initialValueColorChooser, gridBagConstraints);

        initialDestination0TextField.setColumns(3);
        initialDestination0TextField.setToolTipText("initial goal value");
        initialDestination0TextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                initialDestination0TextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(initialDestination0TextField, gridBagConstraints);

        initialDestination1TextField.setColumns(3);
        initialDestination1TextField.setToolTipText("initial goal value");
        initialDestination1TextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                initialDestination1TextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(initialDestination1TextField, gridBagConstraints);

        initialDestination2TextField.setColumns(3);
        initialDestination2TextField.setToolTipText("initial goal value");
        initialDestination2TextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                initialDestination2TextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(initialDestination2TextField, gridBagConstraints);

        initialDestinationColorChooser.setMinimumSize(new java.awt.Dimension(15, 15));
        initialDestinationColorChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                initialDestinationColorChooserActionPerformed(evt);
            }
        });
        initialDestinationColorChooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                initialDestinationColorChooserPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout initialDestinationColorChooserLayout = new javax.swing.GroupLayout(initialDestinationColorChooser);
        initialDestinationColorChooser.setLayout(initialDestinationColorChooserLayout);
        initialDestinationColorChooserLayout.setHorizontalGroup(
            initialDestinationColorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );
        initialDestinationColorChooserLayout.setVerticalGroup(
            initialDestinationColorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 20);
        add(initialDestinationColorChooser, gridBagConstraints);

        followerFigureLabel.setBackground(new java.awt.Color(255, 255, 255));
        followerFigureLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        followerFigureLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/palette/items/resources/follower_1.png"))); // NOI18N
        followerFigureLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(followerFigureLabel, gridBagConstraints);

        hintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel.setText("<html> <p align=\"center\"><b>ColorDamper</b> generates a series of RGB SFColor values using an Infinite-Impulse Response (IIR) algorithm\n<br> \nthat progressively changes from initial value to destination value. </p>\n<br />\n<p align=\"center\"> To start changing output SFColor values, <b>ROUTE</b> a new value to <b>set_destination</b>.  Also create a <b>ROUTE</b>\n<br /> for changing colors from the <b>value_changed</b> event to one of a <b>Material</b> node's color field, for example.</p>\n<br />\n<p align=\"center\"> To completely reinitialize the initial SFColor of a <b>ColorDamper</b>, simply <b>ROUTE</b> a new <b>set_value</b> event.</p>\n\n\n");
        hintLabel.setToolTipText("ROUTE passes events by connecting fields between source and destination nodes");
        hintLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(hintLabel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void toleranceTFActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_toleranceTFActionPerformed
    {//GEN-HEADEREND:event_toleranceTFActionPerformed
        checkTolerance ();
    }//GEN-LAST:event_toleranceTFActionPerformed
    private void checkTolerance ()
    {
        SFFloat tolerance = new SFFloat(toleranceTF.getText());
        if ((tolerance.getValue() < 0.0f) && (tolerance.getValue() != -1.0f))
        {
            String message;
            message = "<html><p align='center'>Illegal negative value for <b>tolerance</b>, which must be</p>" +
                       "<p align='center'>0.0 (exact match), positive (absolute value) or -1 (browser choice)</p>" +
                       "<br/><p align='center'>Use positive value for tolerance?</p>";
            NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                    message, "Use positive value for tolerance?", NotifyDescriptor.YES_NO_OPTION);
            if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
            {
               toleranceTF.setText(Float.toString(Math.abs(tolerance.getValue())));
            }
        }    
    }
    private void initialValue0TextFieldActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_initialValue0TextFieldActionPerformed
    {//GEN-HEADEREND:event_initialValue0TextFieldActionPerformed
        updateInitialValueColorChooser();
    }//GEN-LAST:event_initialValue0TextFieldActionPerformed

    private void initialValueColorChooserActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_initialValueColorChooserActionPerformed
    {//GEN-HEADEREND:event_initialValueColorChooserActionPerformed
        Color c = initialValueColorChooser.getColor ();

        initialValue0TextField.setText(String.valueOf ((float)c.getRed()  /255.0f));
        initialValue1TextField.setText(String.valueOf ((float)c.getGreen()/255.0f));
        initialValue2TextField.setText(String.valueOf ((float)c.getBlue() /255.0f));
}//GEN-LAST:event_initialValueColorChooserActionPerformed

    private void initialValueColorChooserPropertyChange (java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_initialValueColorChooserPropertyChange
    {//GEN-HEADEREND:event_initialValueColorChooserPropertyChange

}//GEN-LAST:event_initialValueColorChooserPropertyChange

    private void initialDestinationColorChooserActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_initialDestinationColorChooserActionPerformed
    {//GEN-HEADEREND:event_initialDestinationColorChooserActionPerformed
        Color c = initialDestinationColorChooser.getColor ();

        initialDestination0TextField.setText(String.valueOf ((float)c.getRed()  /255.0f));
        initialDestination1TextField.setText(String.valueOf ((float)c.getGreen()/255.0f));
        initialDestination2TextField.setText(String.valueOf ((float)c.getBlue() /255.0f));
    }//GEN-LAST:event_initialDestinationColorChooserActionPerformed

    private void initialDestinationColorChooserPropertyChange (java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_initialDestinationColorChooserPropertyChange
    {//GEN-HEADEREND:event_initialDestinationColorChooserPropertyChange

    }//GEN-LAST:event_initialDestinationColorChooserPropertyChange

    private void initialValue1TextFieldActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_initialValue1TextFieldActionPerformed
    {//GEN-HEADEREND:event_initialValue1TextFieldActionPerformed
        updateInitialValueColorChooser();
    }//GEN-LAST:event_initialValue1TextFieldActionPerformed

    private void initialValue2TextFieldActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_initialValue2TextFieldActionPerformed
    {//GEN-HEADEREND:event_initialValue2TextFieldActionPerformed
        updateInitialValueColorChooser();
    }//GEN-LAST:event_initialValue2TextFieldActionPerformed

    private void initialDestination0TextFieldActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_initialDestination0TextFieldActionPerformed
    {//GEN-HEADEREND:event_initialDestination0TextFieldActionPerformed
        updateInitialDestinationColorChooser();
    }//GEN-LAST:event_initialDestination0TextFieldActionPerformed

    private void initialDestination1TextFieldActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_initialDestination1TextFieldActionPerformed
    {//GEN-HEADEREND:event_initialDestination1TextFieldActionPerformed
        updateInitialDestinationColorChooser();
    }//GEN-LAST:event_initialDestination1TextFieldActionPerformed

    private void initialDestination2TextFieldActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_initialDestination2TextFieldActionPerformed
    {//GEN-HEADEREND:event_initialDestination2TextFieldActionPerformed
        updateInitialDestinationColorChooser();
    }//GEN-LAST:event_initialDestination2TextFieldActionPerformed
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel followerFigureLabel;
    private javax.swing.JLabel hintLabel;
    private javax.swing.JTextField initialDestination0TextField;
    private javax.swing.JTextField initialDestination1TextField;
    private javax.swing.JTextField initialDestination2TextField;
    private net.java.dev.colorchooser.ColorChooser initialDestinationColorChooser;
    private javax.swing.JLabel initialDestinationLabel;
    private javax.swing.JTextField initialValue0TextField;
    private javax.swing.JTextField initialValue1TextField;
    private javax.swing.JTextField initialValue2TextField;
    private net.java.dev.colorchooser.ColorChooser initialValueColorChooser;
    private javax.swing.JLabel initialValueLabel;
    private javax.swing.JComboBox<String> orderComboBox;
    private javax.swing.JLabel orderHintLabel;
    private javax.swing.JLabel orderLabel;
    private javax.swing.JLabel tauHintLabel;
    private javax.swing.JLabel tauLabel;
    private javax.swing.JTextField tauTF;
    private javax.swing.JLabel toleranceHintLabel;
    private javax.swing.JLabel toleranceLabel;
    private javax.swing.JTextField toleranceTF;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_COLORDAMPER";
  }

  @Override
  public void unloadInput() throws IllegalArgumentException
  {
    unLoadDEFUSE();

    checkTolerance ();
    colorDamper.setTau                 (      tauTF.getText().trim());
    colorDamper.setTolerance           (toleranceTF.getText().trim());
    colorDamper.setOrder               (orderComboBox.getSelectedItem().toString());
    colorDamper.setInitialValue0       (new SFFloat(initialValue0TextField.getText().trim(), 0.0f, 1.0f));
    colorDamper.setInitialValue1       (new SFFloat(initialValue1TextField.getText().trim(), 0.0f, 1.0f));
    colorDamper.setInitialValue2       (new SFFloat(initialValue2TextField.getText().trim(), 0.0f, 1.0f));
    colorDamper.setInitialDestination0 (new SFFloat(initialDestination0TextField.getText().trim(), 0.0f, 1.0f));
    colorDamper.setInitialDestination1 (new SFFloat(initialDestination1TextField.getText().trim(), 0.0f, 1.0f));
    colorDamper.setInitialDestination2 (new SFFloat(initialDestination2TextField.getText().trim(), 0.0f, 1.0f));
  }  
}