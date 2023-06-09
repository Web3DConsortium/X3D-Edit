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

import java.awt.Dimension;
import javax.swing.text.JTextComponent;
import org.openide.util.HelpCtx;
/**
 * MATRIX4VERTEXATTRIBUTECustomizer.java
 * Created on 16 January 2010
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class MATRIX4VERTEXATTRIBUTECustomizer extends BaseCustomizer
{
  private MATRIX4VERTEXATTRIBUTE m4va;
  private JTextComponent target;
  
  public MATRIX4VERTEXATTRIBUTECustomizer(MATRIX4VERTEXATTRIBUTE m4va, JTextComponent target)
  {
    super(m4va);
    this.m4va = m4va;
    this.target = target;
                   
    HelpCtx.setHelpIDString(this, "MATRIX4VERTEXATTRIBUTE_ELEM_HELPID");
    
    initComponents();
    
    super.getDEFUSEpanel().setContainerField("attrib");

    nameTF.setMinimumSize(new Dimension(nameTF.getPreferredSize()));  // This prevents the "snap-to-min size when resize, but the tf has to be initted with a col value for this to work
    nameTF.setText          (m4va.getName());

    expandableList.setTitle("4x4 matrix arrays");
    expandableList.setColumnTitles(new String[]{"#","0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15"});
    expandableList.doIndexInFirstColumn(true);
    
    String[][] saa = m4va.getValues(); // may be 0-length
    expandableList.setData(saa);
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
    contentModelLabel = new javax.swing.JLabel();
    nameTF = new javax.swing.JTextField();
    nameLabel = new javax.swing.JLabel();
    expandableList = new org.web3d.x3d.palette.items.ExpandableList();

    setLayout(new java.awt.GridBagLayout());
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 0;
    gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
    add(dEFUSEpanel1, gridBagConstraints);

    contentModelLabel.setText("Matrix4VertexAttribute is used for shader field initialization");
    contentModelLabel.setToolTipText("PackagedShader describes a single program having one or more shaders and effects");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 1;
    gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
    add(contentModelLabel, gridBagConstraints);

    nameTF.setColumns(20);
    nameTF.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        nameTFActionPerformed(evt);
      }
    });
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 1;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
    gridBagConstraints.weightx = 0.5;
    gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
    add(nameTF, gridBagConstraints);

    nameLabel.setText("name");
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 2;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
    gridBagConstraints.weightx = 0.5;
    gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
    add(nameLabel, gridBagConstraints);
    gridBagConstraints = new java.awt.GridBagConstraints();
    gridBagConstraints.gridx = 0;
    gridBagConstraints.gridy = 3;
    gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
    gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
    gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
    gridBagConstraints.weightx = 1.0;
    gridBagConstraints.weighty = 1.0;
    gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
    add(expandableList, gridBagConstraints);
  }// </editor-fold>//GEN-END:initComponents

    private void nameTFActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_nameTFActionPerformed
    {//GEN-HEADEREND:event_nameTFActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_nameTFActionPerformed
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JLabel contentModelLabel;
  private org.web3d.x3d.palette.items.ExpandableList expandableList;
  private javax.swing.JLabel nameLabel;
  private javax.swing.JTextField nameTF;
  // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_MATRIX4VERTEXATTRIBUTE";
  }

  @Override
  public void unloadInput() throws IllegalArgumentException
  {
    unLoadDEFUSE();

    m4va.setName(nameTF.getText().trim());
    m4va.setValues(expandableList.getData());
  }  
}
