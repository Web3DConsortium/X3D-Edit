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

import java.util.Iterator;
import java.util.Vector;
import javax.swing.text.JTextComponent;
import org.openide.util.HelpCtx;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
/**
 * FLOATVERTEXATTRIBUTECustomizer.java
 * Created on 24 January 2010
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class FLOATVERTEXATTRIBUTECustomizer extends BaseCustomizer
{
  private FLOATVERTEXATTRIBUTE fva;
  private JTextComponent target;

  private String[] columnTitles1Component = new String[]{"#","0"};
  private String[] columnTitles2Component = new String[]{"#","0","1"};
  private String[] columnTitles3Component = new String[]{"#","0","1","2"};
  private String[] columnTitles4Component = new String[]{"#","0","1","2","3"};
  private String[][] colTitleArr= {null,columnTitles1Component,columnTitles2Component,
                                        columnTitles3Component,columnTitles4Component};

  private boolean preinit=true;

  public FLOATVERTEXATTRIBUTECustomizer(FLOATVERTEXATTRIBUTE fva, JTextComponent target)
  {
    super(fva);
    this.fva = fva;
    this.target = target;
                   
    HelpCtx.setHelpIDString(this, "FLOATVERTEXATTRIBUTE_ELEM_HELPID");
    
    initComponents();
    
    super.getDEFUSEpanel().setContainerField("attrib");

    nameTF.setText          (fva.getName());

    String[][] saa = fva.getValues(); // might be 0-length
 //   expandableList.setData(saa); // precedes setting table columns in order to initialize, may get reset
   
    reInitTable (fva.getNumComponents(),saa);
    numComponentsComboBox.setSelectedIndex (fva.getNumComponents()-1);
    preinit=false;
  }

  private void reInitTable (int num, String[][]saa)
  {
    if(num == 0)
      num = 4;
    if(num > 4) {
      //todo show warning panel
      fva.setNumComponents("4");
      num = 4;
    }

    //String[][] saa = expandableList.getData();
    saa = resizeIfNeeded(saa,num);

    expandableList.doIndexInFirstColumn(true);
    expandableList.setTitle("float matrix arrays");
    expandableList.setColumnTitles(colTitleArr[num]);  // This sets the number of visible columns
    expandableList.setData(saa);
  }

  private Vector<String> serializeIt(String[][]saa)
  {
    Vector<String> v = new Vector<String>();
    for(int i=0;i<saa.length;i++)
      for(int j=0;j<saa[0].length;j++)
        v.add(saa[i][j]);
    return v;
  }

  // Flow the existing data...don't truncate any
  private String[][] resizeIfNeeded(String[][] saa, int ncols)
  {
    if(saa.length == 0)
      return saa;
    if(saa[0].length == ncols)
      return saa;

    Vector<String> v = serializeIt(saa);
    int n = v.size();
    int numRows = n/ncols;
    if(n%ncols != 0)
      numRows++;
    Iterator<String> itr = v.iterator();

    String[][] newsaa = new String[numRows][ncols];
    for(int i=0;i<numRows;i++) {
      String[] sa = new String[ncols];
      for(int j=0;j<ncols;j++) {
        if(itr.hasNext())
          sa[j]=itr.next();
        else
          sa[j]="0";
      }
      newsaa[i]=sa;
    }

    return newsaa;
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
        nameLabel = new javax.swing.JLabel();
        nameTF = new javax.swing.JTextField();
        numComponentsLabel = new javax.swing.JLabel();
        numComponentsComboBox = new javax.swing.JComboBox<String>();
        expandableList = new org.web3d.x3d.palette.items.ExpandableList();
        eventHintPanel = new javax.swing.JPanel();
        contentModelLabel = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        add(dEFUSEpanel1, gridBagConstraints);

        nameLabel.setText("name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
        add(nameLabel, gridBagConstraints);

        nameTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nameTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 227;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        add(nameTF, gridBagConstraints);

        numComponentsLabel.setText("numComponents");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 5);
        add(numComponentsLabel, gridBagConstraints);

        numComponentsComboBox.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "1", "2", "3", "4" }));
        numComponentsComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numComponentsComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        add(numComponentsComboBox, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(expandableList, gridBagConstraints);

        eventHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eventHintPanel.setLayout(new java.awt.GridBagLayout());

        contentModelLabel.setText("<html><p align=\"center\"> <b>FloatVertexAttribute</b> is used for shader field initialization</p>");
        contentModelLabel.setToolTipText("PackagedShader describes a single program having one or more shaders and effects");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        eventHintPanel.add(contentModelLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        add(eventHintPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void nameTFActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_nameTFActionPerformed
    {//GEN-HEADEREND:event_nameTFActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_nameTFActionPerformed

    private void numComponentsComboBoxActionPerformed (java.awt.event.ActionEvent evt)//GEN-FIRST:event_numComponentsComboBoxActionPerformed
    {//GEN-HEADEREND:event_numComponentsComboBoxActionPerformed

      if(!preinit)
        reInitTable ((new SFInt32(numComponentsComboBox.getSelectedItem().toString()).getValue()),
                     expandableList.getData());
    }//GEN-LAST:event_numComponentsComboBoxActionPerformed
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel contentModelLabel;
    private javax.swing.JPanel eventHintPanel;
    private org.web3d.x3d.palette.items.ExpandableList expandableList;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JTextField nameTF;
    private javax.swing.JComboBox<String> numComponentsComboBox;
    private javax.swing.JLabel numComponentsLabel;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_FLOATVERTEXATTRIBUTE";
  }

  @Override
  public void unloadInput() throws IllegalArgumentException
  {
    unLoadDEFUSE();

    fva.setName(nameTF.getText().trim());
    fva.setNumComponents(numComponentsComboBox.getSelectedItem().toString());
    fva.setValues(expandableList.getData());
  }  
}
