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

import java.awt.Color;

/**
 *
 * @author  Mike Bailey jmbailey@nps.edu
 */
public class FieldPan extends javax.swing.JPanel
{  
  /** Creates new form FieldPan */
  public FieldPan(String name, String type, String access)
  {
    initComponents();
    nameLab.setText(name);
    typeLab.setText(type);
    accessLab.setText(access);
  }
  
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    nameLab = new javax.swing.JLabel();
    typeLab = new javax.swing.JLabel();
    accessLab = new javax.swing.JLabel();

    nameLab.setFont(new java.awt.Font("Dialog", 1, 12));
    nameLab.setText(org.openide.util.NbBundle.getMessage(FieldPan.class, "FieldPan.nameLab.text")); // NOI18N

    typeLab.setFont(new java.awt.Font("Dialog", 2, 12));
    typeLab.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
    typeLab.setText(org.openide.util.NbBundle.getMessage(FieldPan.class, "FieldPan.typeLab.text")); // NOI18N

    accessLab.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
    accessLab.setText(org.openide.util.NbBundle.getMessage(FieldPan.class, "FieldPan.accessLab.text")); // NOI18N

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
    this.setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addComponent(nameLab, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addComponent(typeLab, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addComponent(accessLab, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
        .addComponent(nameLab)
        .addComponent(typeLab, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addComponent(accessLab))
    );
  }// </editor-fold>//GEN-END:initComponents
  
  
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JLabel accessLab;
  private javax.swing.JLabel nameLab;
  private javax.swing.JLabel typeLab;
  // End of variables declaration//GEN-END:variables
  
  @Override
  public void setForeground(Color c)
  {
   // nameLab.setForeground(c);
   // accessLab.setForeground(c);
    if(typeLab != null)
      typeLab.setForeground(c);
  }
  
  public String getFieldName()
  {
    return nameLab.getText();
  }
  
  public String getFieldType()
  {
    return typeLab.getText().trim();
  }
  
  public String getFieldAccess()
  {
    return accessLab.getText().trim();
  }
  
  public void setFieldName(String s)
  {
    nameLab.setText(s);
  }
  
  public void setFieldType(String s)
  {
    typeLab.setText(s);
  }
  
  public void setFieldAccess(String s)
  {
    accessLab.setText(s);
  }
  
  @Override
  public String toString()
  {
    return getFieldName();
  }
  
  public void valueOf(String s)
  {
    setFieldName(s);
  }
}
