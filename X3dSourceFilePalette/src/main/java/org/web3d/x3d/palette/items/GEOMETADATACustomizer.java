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

import java.util.HashMap;
import java.util.Vector;
import javax.swing.JLabel;
import javax.swing.text.JTextComponent;
import org.openide.util.HelpCtx;
import static org.web3d.x3d.types.X3DSchemaData.GEOMETADATA_KEYS;
import static org.web3d.x3d.types.X3DSchemaData.GEOMETADATA_KEY_TOOLTIPS;

/**
 * GEOMETADATACustomizer.java
 * Created on Apr 29 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class GEOMETADATACustomizer extends BaseCustomizer
{
  private GEOMETADATA geometadata;
  private JTextComponent target;
  
  /** Creates new form SHAPECustomizer */
  public GEOMETADATACustomizer(GEOMETADATA geometadata, JTextComponent target)
  {
    super(geometadata);
    this.geometadata = geometadata;
    this.target = target;
          
    HelpCtx.setHelpIDString(this, "GEOMETADATA_ELEM_HELPID");
    
    initComponents();
    
    expandableList1.setTitle("url");
    expandableList1.setTextAlignment(JLabel.LEADING);
    expandableList1.doIndexInFirstColumn(true);
    expandableList1.setNewRowData(new Object[]{""});
    expandableList1.setColumnTitles(new String[]{"",""}); // first column gets index, 2nd can be titled "url" or something
    String[] sa = geometadata.getUrl();
    String[][]saa = new String[sa.length][1];
    for(int i=0;i<sa.length;i++)
      saa[i][0] = sa[i];
    expandableList1.setData(saa);
    
    setupSummaryPanel();
    
    String[][] summaryData = geometadata.getSummary();
    for(String[] starr : summaryData) {
      GEOMETADATACustomizerKeyValue keyValue = keyValueMap.get(starr[0]);
      if (keyValue != null) {
        keyValue.setText(starr[1]);
        keyValue.setEnabled(true);
      }
    }
  }
  
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        dEFUSEpanel1 = getDEFUSEpanel();
        jSplitPane1 = new javax.swing.JSplitPane();
        expandableList1 = new org.web3d.x3d.palette.items.ExpandableList();
        jScrollPane1 = new javax.swing.JScrollPane();
        summaryPanel = new javax.swing.JPanel();
        keyValue13 = new org.web3d.x3d.palette.items.GEOMETADATACustomizerKeyValue();
        keyValue9 = new org.web3d.x3d.palette.items.GEOMETADATACustomizerKeyValue();
        keyValue2 = new org.web3d.x3d.palette.items.GEOMETADATACustomizerKeyValue();
        keyValue5 = new org.web3d.x3d.palette.items.GEOMETADATACustomizerKeyValue();
        keyValue6 = new org.web3d.x3d.palette.items.GEOMETADATACustomizerKeyValue();
        keyValue11 = new org.web3d.x3d.palette.items.GEOMETADATACustomizerKeyValue();
        keyValue4 = new org.web3d.x3d.palette.items.GEOMETADATACustomizerKeyValue();
        keyValue12 = new org.web3d.x3d.palette.items.GEOMETADATACustomizerKeyValue();
        keyValue7 = new org.web3d.x3d.palette.items.GEOMETADATACustomizerKeyValue();
        keyValue8 = new org.web3d.x3d.palette.items.GEOMETADATACustomizerKeyValue();
        keyValue1 = new org.web3d.x3d.palette.items.GEOMETADATACustomizerKeyValue();
        keyValue14 = new org.web3d.x3d.palette.items.GEOMETADATACustomizerKeyValue();
        keyValue10 = new org.web3d.x3d.palette.items.GEOMETADATACustomizerKeyValue();
        keyValue3 = new org.web3d.x3d.palette.items.GEOMETADATACustomizerKeyValue();

        jSplitPane1.setBorder(null);
        jSplitPane1.setDividerLocation(150);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        expandableList1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "", javax.swing.border.TitledBorder.LEADING, javax.swing.border.TitledBorder.TOP));
        expandableList1.setMinimumSize(new java.awt.Dimension(300, 140));
        jSplitPane1.setTopComponent(expandableList1);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "summary", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, getFont(), getForeground()));

        summaryPanel.setPreferredSize(new java.awt.Dimension(364, 1122));

        javax.swing.GroupLayout summaryPanelLayout = new javax.swing.GroupLayout(summaryPanel);
        summaryPanel.setLayout(summaryPanelLayout);
        summaryPanelLayout.setHorizontalGroup(
            summaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(keyValue9, javax.swing.GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE)
            .addComponent(keyValue10, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
            .addComponent(keyValue11, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
            .addComponent(keyValue12, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
            .addComponent(keyValue13, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
            .addComponent(keyValue14, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
            .addComponent(keyValue7, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
            .addComponent(keyValue6, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
            .addComponent(keyValue5, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
            .addComponent(keyValue4, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
            .addComponent(keyValue3, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
            .addComponent(keyValue2, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
            .addComponent(keyValue8, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
            .addComponent(keyValue1, javax.swing.GroupLayout.DEFAULT_SIZE, 471, Short.MAX_VALUE)
        );
        summaryPanelLayout.setVerticalGroup(
            summaryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(summaryPanelLayout.createSequentialGroup()
                .addComponent(keyValue1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(keyValue2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(keyValue3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(keyValue4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(keyValue5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(keyValue6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(keyValue7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(keyValue14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(keyValue13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(keyValue12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(keyValue11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(keyValue10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(keyValue9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(keyValue8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jScrollPane1.setViewportView(summaryPanel);

        jSplitPane1.setBottomComponent(jScrollPane1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dEFUSEpanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE)
                    .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 498, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(dEFUSEpanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jSplitPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
   
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel1;
    private org.web3d.x3d.palette.items.ExpandableList expandableList1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSplitPane jSplitPane1;
    private org.web3d.x3d.palette.items.GEOMETADATACustomizerKeyValue keyValue1;
    private org.web3d.x3d.palette.items.GEOMETADATACustomizerKeyValue keyValue10;
    private org.web3d.x3d.palette.items.GEOMETADATACustomizerKeyValue keyValue11;
    private org.web3d.x3d.palette.items.GEOMETADATACustomizerKeyValue keyValue12;
    private org.web3d.x3d.palette.items.GEOMETADATACustomizerKeyValue keyValue13;
    private org.web3d.x3d.palette.items.GEOMETADATACustomizerKeyValue keyValue14;
    private org.web3d.x3d.palette.items.GEOMETADATACustomizerKeyValue keyValue2;
    private org.web3d.x3d.palette.items.GEOMETADATACustomizerKeyValue keyValue3;
    private org.web3d.x3d.palette.items.GEOMETADATACustomizerKeyValue keyValue4;
    private org.web3d.x3d.palette.items.GEOMETADATACustomizerKeyValue keyValue5;
    private org.web3d.x3d.palette.items.GEOMETADATACustomizerKeyValue keyValue6;
    private org.web3d.x3d.palette.items.GEOMETADATACustomizerKeyValue keyValue7;
    private org.web3d.x3d.palette.items.GEOMETADATACustomizerKeyValue keyValue8;
    private org.web3d.x3d.palette.items.GEOMETADATACustomizerKeyValue keyValue9;
    private javax.swing.JPanel summaryPanel;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_GEOMETADATA";
  }

  private GEOMETADATACustomizerKeyValue[] keyValueArray;
  
  HashMap<String,GEOMETADATACustomizerKeyValue> keyValueMap;
  
  private void setupSummaryPanel()
  {
    keyValueArray = new GEOMETADATACustomizerKeyValue[]{
      keyValue1,keyValue2,keyValue3,keyValue4,keyValue5,keyValue6,keyValue7,
      keyValue8,keyValue9,keyValue10,keyValue11,keyValue12,keyValue13,keyValue14
    };
    keyValueMap = new HashMap<String,GEOMETADATACustomizerKeyValue>(keyValueArray.length);
    
    for(int i=0;i<keyValueArray.length;i++) {
      keyValueArray[i].setTitle(GEOMETADATA_KEYS[i]);
      keyValueArray[i].setToolTipText(GEOMETADATA_KEY_TOOLTIPS[i]);
      keyValueArray[i].setEnabled(false);
      keyValueMap.put(GEOMETADATA_KEYS[i], keyValueArray[i]);
    }
  }
  
  @Override
  public void unloadInput() throws IllegalArgumentException
  {
    unLoadDEFUSE();
    String[][] saa = expandableList1.getData();
    String[] sa = new String[saa.length];
    for(int i=0;i<sa.length;i++)
      sa[i] = saa[i][0];
    geometadata.setUrl(sa);
        
    Vector<String[]> v = new Vector<String[]>();
    for(GEOMETADATACustomizerKeyValue kv : keyValueArray)
      if(kv.isEnabled())
        if(kv.getText().trim().length()>0)
          v.add(new String[]{kv.getTitle(),kv.getText().trim()});
    
    String[][] straa = new String[0][0];
    straa = v.toArray(straa);
    geometadata.setSummary(straa);
    
  } 
}