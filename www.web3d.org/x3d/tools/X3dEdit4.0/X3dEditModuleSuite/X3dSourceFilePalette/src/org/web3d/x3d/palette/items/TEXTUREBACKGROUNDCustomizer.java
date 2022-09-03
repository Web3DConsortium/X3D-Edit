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

import javax.swing.text.JTextComponent;
import org.openide.util.HelpCtx;
import org.web3d.x3d.X3DDataObject;

/**
 * TEXTUREBACKGROUNDCustomizer.java
 * Created on May 28, 2008, 5:13 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey and Don Brutzman
 * @version $Id$
 */
public class TEXTUREBACKGROUNDCustomizer extends BaseCustomizer
{
  private TEXTUREBACKGROUND textureBackground;
  private JTextComponent target;
  private X3DDataObject xObj;
  private BackgroundContentPanel backgroundContentPanel;
  
  private int groundReadOnlyCol = 0;
  private int groundReadOnlyRow = 0;
  
  public TEXTUREBACKGROUNDCustomizer(TEXTUREBACKGROUND textureBackground, JTextComponent target, X3DDataObject xObj)
  {
    super(textureBackground);
    this.textureBackground = textureBackground;
    this.target = target;
    this.xObj = xObj;
    
    HelpCtx.setHelpIDString(this, "TEXTUREBACKGROUND_ELEM_HELPID");
    
    initComponents();
    
    transparencyTF.setText(textureBackground.getTransparency());
    
    backgroundContentPanel = new BackgroundContentPanel(this);
    backgroundContentPanel.setUrlListsVisible(false);
    bcpHoldingPanel.add (backgroundContentPanel);

    setUpAngleColorTables(); // must follow backgroundContentPanel instantiation
    
    validate(); // redraw
  }

  private void setUpGroundTable()
  {
    setUpGroundTableCommon(textureBackground.getGroundAngleArray(), textureBackground.getGroundColorArray());
  }

  public void setUpGroundTableDefault()
  {
    setUpGroundTableCommon(textureBackground.getGroundAngleDefault(), textureBackground.getGroundColorDefault());
  }

  private void setUpGroundTableCommon(String[] angle, String[][] colors)
  {
    Object[][] colorData;
    if (colors.length <= 0)
      colorData = new Object[][]{};
    else {
      colorData = new Object[colors.length][4];
      int j = colors.length - 1;
      for (int i = 0; i < colors.length; i++) {
        String[] oa = new String[4];
        oa[0] = (i == 0 ? "nadir" : angle[i - 1]);
        oa[1] = colors[i][0];
        oa[2] = colors[i][1];
        oa[3] = colors[i][2];
        colorData[j--] = oa;
      }
    }
    backgroundContentPanel.groundAngleColorList.setColumnTitles  (new String[]{"angle", "r", "g", "b", "..."});
    backgroundContentPanel.groundAngleColorList.setColumnToolTips(new String[]{"angle radians","red","green","blue","chooser"});
    backgroundContentPanel.groundAngleColorList.setHeaderTooltip ("Spherical color map, averaged by angle and color");
    backgroundContentPanel.groundAngleColorList.setRedColumn(1);
    //bcp.groundColorList.setTitle("ground color");
    backgroundContentPanel.groundAngleColorList.doTrailingColorChooser();

    backgroundContentPanel.groundAngleColorList.setNewRowData(new Object[]{"0", "0", "0", "0"});
    backgroundContentPanel.groundAngleColorList.setData(colorData);
    int nrows = backgroundContentPanel.groundAngleColorList.getTable().getRowCount();
    if (nrows > 0)
        backgroundContentPanel.groundAngleColorList.setReadOnlyCell(groundReadOnlyRow = (nrows - 1), groundReadOnlyCol = 0);
    else
        backgroundContentPanel.groundAngleColorList.setReadOnlyCell(groundReadOnlyRow = 0, groundReadOnlyCol = 0);
    backgroundContentPanel.groundAngleColorList.setInsertRowFilter(new MyGroundRowInserter());
    backgroundContentPanel.groundAngleColorList.setRemoveRowFilter(new MyGroundRowRemover());
    backgroundContentPanel.groundAngleColorList.validate();
  }

  private void setupSkyTable()
  {
    setupSkyTableCommon(textureBackground.getSkyAngleArray(), textureBackground.getSkyColorArray());
  }

  public void setupSkyTableDefault()
  {
    setupSkyTableCommon(textureBackground.getSkyAngleDefault(), textureBackground.getSkyColorDefault());
  }

  private void setupSkyTableCommon(String[] angles, String[][] colors)
  {
    Object[][] colorData;

    if (colors.length <= 0)
      colorData = new Object[][]{};
    else {
      colorData = new Object[colors.length][4];
      for (int i = 0; i < colors.length; i++) {
        String[] oa = new String[4];
        oa[0] = (i == 0 ? "zenith" : angles[i - 1]);
        oa[1] = colors[i][0];
        oa[2] = colors[i][1];
        oa[3] = colors[i][2];
        colorData[i] = oa;
      }
    }
    backgroundContentPanel.skyAngleColorList.setColumnTitles  (new String[]{"angle", "r", "g", "b", "..."});
    backgroundContentPanel.skyAngleColorList.setColumnToolTips(new String[]{"angle radians","red","green","blue","chooser"});
    backgroundContentPanel.skyAngleColorList.setHeaderTooltip ("Spherical color map, averaged by angle and color");
    backgroundContentPanel.skyAngleColorList.setRedColumn(1);
    //bcp.skyColorList.setTitle("sky color");
    backgroundContentPanel.skyAngleColorList.doTrailingColorChooser();

    backgroundContentPanel.skyAngleColorList.setNewRowData(new Object[]{"0", "0", "0", "0"});
    backgroundContentPanel.skyAngleColorList.setData(colorData);
    backgroundContentPanel.skyAngleColorList.setReadOnlyCell(0, 0);
    backgroundContentPanel.skyAngleColorList.setInsertRowFilter(new MySkyRowInserter());
    backgroundContentPanel.skyAngleColorList.setRemoveRowFilter(new MySkyRowRemover());
    backgroundContentPanel.skyAngleColorList.validate();
  }

  private void setUpAngleColorTables()
  {
    setupSkyTable();
    setUpGroundTable();
  }
 
//  private void setUpAngleColorTables()
//  {
//    Object[][] colorData;
//    String[] aa = textureBackground.getSkyAngleArray();
//    String[][] caa = textureBackground.getSkyColorArray();
//
//    if(caa.length <= 0) 
//      colorData = new Object[][]{};
//    else {
//      colorData = new Object[caa.length][4];
//      for(int i=0;i<caa.length;i++) {
//        String[] oa = new String[4];
//        oa[0] = (i==0?"zenith":aa[i-1]);
//        oa[1] = caa[i][0];
//        oa[2] = caa[i][1];
//        oa[3] = caa[i][2];
//        colorData[i] = oa;
//      }      
//    }
//    tcp.skyColorList.setColumnTitles  (new String[]{"angle","r","g","b","..."});
//    tcp.skyColorList.setColumnToolTips(new String[]{"angle radians","red","green","blue","chooser"});
//    tcp.skyColorList.setHeaderTooltip ("Spherical color map, averaged by angle and color");
//    tcp.skyColorList.setRedColumn(1);
//    //tcp.skyColorList.setTitle("sky color");
//    tcp.skyColorList.doTrailingColorChooser();
//  
//    tcp.skyColorList.setNewRowData(new Object[]{"0","0","0","0"});
//    tcp.skyColorList.setData(colorData);
//    tcp.skyColorList.setReadOnlyCell(0,0);
//    tcp.groundColorList.setInsertRowFilter(new MySkyRowInserter());
//    tcp.groundColorList.setRemoveRowFilter(new MySkyRowRemover());
//   
//    aa = textureBackground.getGroundAngleArray();
//    caa = textureBackground.getGroundColorArray();
//        
//    if(caa.length <= 0) 
//      colorData = new Object[][]{};
//    else {
//      colorData = new Object[caa.length][4];
//      int j=caa.length-1;
//      for(int i=0;i<caa.length;i++) {
//        String[] oa = new String[4];
//        oa[0] = (i==0?"nadir":aa[i-1]);
//        oa[1] = caa[i][0];
//        oa[2] = caa[i][1];
//        oa[3] = caa[i][2];
//        colorData[j--] = oa;
//      }      
//    }
//    tcp.groundColorList.setColumnTitles  (new String[]{"angle","r","g","b","..."});
//    tcp.groundColorList.setColumnToolTips(new String[]{"angle radians","red","green","blue","chooser"});
//    tcp.groundColorList.setHeaderTooltip ("Spherical color map, averaged by angle and color");
//    tcp.groundColorList.setRedColumn(1);
//    //tcp.groundColorList.setTitle("ground color");
//    tcp.groundColorList.doTrailingColorChooser();
//    tcp.groundColorList.setNewRowData(new Object[]{"0","0","0","0"});
//    tcp.groundColorList.setData(colorData);
//    int nrows = tcp.groundColorList.getTable().getRowCount();
//    if(nrows >0)
//      tcp.groundColorList.setReadOnlyCell(groundReadOnlyRow=(nrows-1), groundReadOnlyCol=0);
//    else
//      tcp.groundColorList.setReadOnlyCell(groundReadOnlyRow=0,groundReadOnlyCol=0);
//    tcp.groundColorList.setInsertRowFilter(new MyGroundRowInserter());
//    tcp.groundColorList.setRemoveRowFilter(new MyGroundRowRemover());
//  }
  
  class MySkyRowInserter implements ExpandableList.InsertRowFilterIf
  {
    @Override
    public int insertRowNumber(int selectedRow)
    {
      int rowCount = backgroundContentPanel.skyAngleColorList.getTable().getRowCount();
      if(rowCount <= 0)
        backgroundContentPanel.skyAngleColorList.setNewRowData(new Object[]{"zenith","0","0","0"});
      else
        backgroundContentPanel.skyAngleColorList.setNewRowData(new Object[]{"0","0","0","0"});
      return selectedRow;
    }   
  }
   class MySkyRowRemover implements ExpandableList.RemoveRowFilterIf
  {
    @Override
    public int removeRowNumber(int selectedRow)
    {
      int rowCount = backgroundContentPanel.groundAngleColorList.getTable().getRowCount();
      if (selectedRow == -1 || rowCount <= 0)  // empty table or non selected
        return -1;
      
      // Don't remove top if there are ones below it
      if(rowCount > 1 && selectedRow == 0)
        return -1;
       
      return selectedRow;
    }   
  }
 
  class MyGroundRowInserter implements ExpandableList.InsertRowFilterIf
  {
    @Override
    public int insertRowNumber(int selectedRow)
    {
      // Default
     // selectedRow = (selectedRow == -1 ? 0 : selectedRow);  // this looks fishy, test inserting at row #1 in an empty table
      //return selectedRow + 1; // append after selected row
      int rowCount = backgroundContentPanel.groundAngleColorList.getTable().getRowCount();
      if(backgroundContentPanel.groundAngleColorList.getTable().getRowCount() <= 0)
        backgroundContentPanel.groundAngleColorList.setNewRowData(new Object[]{"nadir","0","0","0"});
      else
        backgroundContentPanel.groundAngleColorList.setNewRowData(new Object[]{"0","0","0","0"});
      
      backgroundContentPanel.groundAngleColorList.unsetReadOnlyCell(groundReadOnlyRow,groundReadOnlyCol);
      backgroundContentPanel.groundAngleColorList.setReadOnlyCell(groundReadOnlyRow=rowCount, groundReadOnlyCol=0); // last row (soon to be added)
      
      if(rowCount > 0 && selectedRow == rowCount-1) //if last row is selected one
        return selectedRow;   // insert before selected row
      //else
      return selectedRow + 1; // append after selected row
    }   
  }
  
  class MyGroundRowRemover implements ExpandableList.RemoveRowFilterIf
  {
    @Override
    public int removeRowNumber(int selectedRow)
    {
      int rowCount = backgroundContentPanel.groundAngleColorList.getTable().getRowCount();
      if (selectedRow == -1 || rowCount <= 0)  // empty table or non selected
        return -1;
      
      // Don't remove bottom if there are ones above it
      if(rowCount > 1 && selectedRow == rowCount-1)
        return -1;
      
      backgroundContentPanel.groundAngleColorList.unsetReadOnlyCell(groundReadOnlyRow,groundReadOnlyCol);
      backgroundContentPanel.groundAngleColorList.setReadOnlyCell(groundReadOnlyRow=(rowCount-2), groundReadOnlyCol=0); // last row
      
      return selectedRow;
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
        transparencyTF = new javax.swing.JTextField();
        transparencyLabel = new javax.swing.JLabel();
        bcpHoldingPanel = new javax.swing.JPanel();
        nodeHintPanel = new javax.swing.JPanel();
        hintLabel = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        dEFUSEpanel1.setMinimumSize(new java.awt.Dimension(198, 77));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel1, gridBagConstraints);

        transparencyTF.setText(org.openide.util.NbBundle.getMessage(TEXTUREBACKGROUNDCustomizer.class, "TextureBackgroundContentPanel.transparencyTF.text")); // NOI18N
        transparencyTF.setToolTipText("transparency applied to texture images, enabling X3D scene to overlay an HTML page or desktop");
        transparencyTF.setMinimumSize(new java.awt.Dimension(80, 20));
        transparencyTF.setPreferredSize(new java.awt.Dimension(80, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        add(transparencyTF, gridBagConstraints);

        transparencyLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        transparencyLabel.setText(org.openide.util.NbBundle.getMessage(TEXTUREBACKGROUNDCustomizer.class, "TextureBackgroundContentPanel.transparencyLabel.text")); // NOI18N
        transparencyLabel.setToolTipText("transparency applied to texture images, enabling X3D scene to overlay an HTML page or desktop");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(6, 26, 6, 6);
        add(transparencyLabel, gridBagConstraints);

        bcpHoldingPanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bcpHoldingPanel, gridBagConstraints);

        nodeHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nodeHintPanel.setLayout(new java.awt.GridBagLayout());

        hintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel.setText("<html><p align='center'><b>TextureBackground</b> contains sky+ground colors+angles, plus<br> <b>ImageTexture</b>, <b>PixelTexture</b> or <b>MovieTexture</b> nodes with corresponding <i>containerField</i> values<br>for each side of the world background box</p>");
        hintLabel.setToolTipText("close this panel to add children nodes");
        hintLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        nodeHintPanel.add(hintLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nodeHintPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
  
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bcpHoldingPanel;
    private org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel1;
    private javax.swing.JLabel hintLabel;
    private javax.swing.JPanel nodeHintPanel;
    private javax.swing.JLabel transparencyLabel;
    javax.swing.JTextField transparencyTF;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_TEXTUREBACKGROUND";
  }

  @Override
  public void unloadInput()
  {
    unLoadDEFUSE();
    
    unloadColorsAndAngles(); 
    textureBackground.setTransparency(transparencyTF.getText().trim());  // TODO floating point range test?
  } 
  
  private void unloadColorsAndAngles()
  {
    String[][] saa = backgroundContentPanel.groundAngleColorList.getData();
    if(saa == null || saa.length<=0) 
    {
      textureBackground.setGroundAngleArray(textureBackground.getGroundAngleDefault());
      textureBackground.setGroundColorArray(textureBackground.getGroundColorDefault());
    } 
    else 
    {
      String[] aa = new String[saa.length-1];
      //int r=0;
      int r=saa.length-1;
      String[][] caa = new String[saa.length][3];
      for(String[] sa : saa ) {
        if(r != 0)
          aa[r-1] = sa[0];
        String[] ca = new String[3];
        ca[0] = sa[1];
        ca[1] = sa[2];
        ca[2] = sa[3];
//        caa[r++] = ca;
        caa[r--] = ca;
      }
      textureBackground.setGroundAngleArray(aa);
      textureBackground.setGroundColorArray(caa);
    }
    
    saa = backgroundContentPanel.skyAngleColorList.getData();
    if(saa == null || saa.length<=0) {
      textureBackground.setSkyAngleArray(new String[]{});
      textureBackground.setSkyColorArray(new String[][]{{}});
    } 
    else {
      String[] aa = new String[saa.length-1];
      int r=0;
      String[][] caa = new String[saa.length][3];
      for(String[] sa : saa ) {
        if(r != 0)
          aa[r-1] = sa[0];
        String[] ca = new String[3];
        ca[0] = sa[1];
        ca[1] = sa[2];
        ca[2] = sa[3];
        caa[r++] = ca;
      }
      textureBackground.setSkyAngleArray(aa);
      textureBackground.setSkyColorArray(caa);      
    }
  }
}
