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

import javax.swing.text.JTextComponent;
import org.openide.util.HelpCtx;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFFloat;
/**
 * POSITIONCHASER2DCustomizer.java
 * Created on 6 February 2010
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class POSITIONCHASER2DCustomizer extends BaseCustomizer
{
  private POSITIONCHASER2D positionChaser2D;
  private JTextComponent target;

  public POSITIONCHASER2DCustomizer(POSITIONCHASER2D positionChaser2D, JTextComponent target)
  {
    super(positionChaser2D);
    this.positionChaser2D = positionChaser2D;
    this.target = target;
                   
    HelpCtx.setHelpIDString(this, "POSITIONCHASER2D_ELEM_HELPID");
    
    initComponents();

                durationTF.setText (positionChaser2D.getDuration());

    initialValue0TextField.setText (positionChaser2D.getInitialValue0().toString ());
    initialValue1TextField.setText (positionChaser2D.getInitialValue1().toString ());

    initialDestination0TextField.setText (positionChaser2D.getInitialDestination0().toString ());
    initialDestination1TextField.setText (positionChaser2D.getInitialDestination1().toString ());
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
        durationLabel = new javax.swing.JLabel();
        durationTF = new javax.swing.JTextField();
        durationUnitsLabel = new javax.swing.JLabel();
        initialValueLabel = new javax.swing.JLabel();
        initialDestinationLabel = new javax.swing.JLabel();
        initialValue0TextField = new javax.swing.JTextField();
        initialValue1TextField = new javax.swing.JTextField();
        initialDestination0TextField = new javax.swing.JTextField();
        initialDestination1TextField = new javax.swing.JTextField();
        followerFigureLabel = new javax.swing.JLabel();
        hintLabel = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(630, 525));
        setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel1, gridBagConstraints);

        durationLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        durationLabel.setText("duration");
        durationLabel.setToolTipText("time interval for filter response in seconds");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 20, 3, 3);
        add(durationLabel, gridBagConstraints);

        durationTF.setColumns(5);
        durationTF.setToolTipText("time interval for filter response in seconds");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(durationTF, gridBagConstraints);

        durationUnitsLabel.setText("seconds");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(durationUnitsLabel, gridBagConstraints);

        initialValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        initialValueLabel.setText("initialValue");
        initialValueLabel.setToolTipText("initial starting value");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 20, 3, 3);
        add(initialValueLabel, gridBagConstraints);

        initialDestinationLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        initialDestinationLabel.setText("initialDestination");
        initialDestinationLabel.setToolTipText("initial goal value");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 20, 3, 3);
        add(initialDestinationLabel, gridBagConstraints);

        initialValue0TextField.setColumns(5);
        initialValue0TextField.setToolTipText("initial starting value");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(initialValue0TextField, gridBagConstraints);

        initialValue1TextField.setColumns(5);
        initialValue1TextField.setToolTipText("initial starting value");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 20);
        add(initialValue1TextField, gridBagConstraints);

        initialDestination0TextField.setColumns(5);
        initialDestination0TextField.setToolTipText("initial goal value");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(initialDestination0TextField, gridBagConstraints);

        initialDestination1TextField.setColumns(5);
        initialDestination1TextField.setToolTipText("initial goal value");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 20);
        add(initialDestination1TextField, gridBagConstraints);

        followerFigureLabel.setBackground(new java.awt.Color(255, 255, 255));
        followerFigureLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        followerFigureLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/palette/items/resources/follower_1.png"))); // NOI18N
        followerFigureLabel.setBorder(javax.swing.BorderFactory.createEmptyBorder(3, 3, 3, 3));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(followerFigureLabel, gridBagConstraints);

        hintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel.setText("<html> <p align=\"center\"><b>PositionChaser2D</b> generates a series of  2-tuple x-y SFVec2f values using a Finite-Impulse Response (FIR) algorithm\n<br> \nthat progressively changes from initial value to destination value. </p>\n<br />\n<p align=\"center\"> To start changing output SFVec2f values, <b>ROUTE</b> a new value to <b>set_destination</b>.  Also create a <b>ROUTE</b>\n<br /> for changing SFVec2f values from the <b>value_changed</b> event to a <b>Transform</b> node's <b>translation</b> field, for example.</p>\n<br />\n<p align=\"center\"> To completely reinitialize the initial SFRotation of a <b>PositionChaser2D</b>, simply <b>ROUTE</b> a new <b>set_value</b> event.</p>\n\n\n");
        hintLabel.setToolTipText("ROUTE passes events by connecting fields between source and destination nodes");
        hintLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(hintLabel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel durationLabel;
    private javax.swing.JTextField durationTF;
    private javax.swing.JLabel durationUnitsLabel;
    private javax.swing.JLabel followerFigureLabel;
    private javax.swing.JLabel hintLabel;
    private javax.swing.JTextField initialDestination0TextField;
    private javax.swing.JTextField initialDestination1TextField;
    private javax.swing.JLabel initialDestinationLabel;
    private javax.swing.JTextField initialValue0TextField;
    private javax.swing.JTextField initialValue1TextField;
    private javax.swing.JLabel initialValueLabel;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_POSITIONCHASER2D";
  }

  @Override
  public void unloadInput() throws IllegalArgumentException
  {
    unLoadDEFUSE();

    positionChaser2D.setDuration            (new SFFloat(            durationTF.getText().trim(), 0.0f, null)); // no upper limit
    positionChaser2D.setInitialValue0       (new SFFloat(initialValue0TextField.getText().trim(), null, null));
    positionChaser2D.setInitialValue1       (new SFFloat(initialValue1TextField.getText().trim(), null, null));
    positionChaser2D.setInitialDestination0 (new SFFloat(initialDestination0TextField.getText().trim(), null, null));
    positionChaser2D.setInitialDestination1 (new SFFloat(initialDestination1TextField.getText().trim(), null, null));
  }  
}