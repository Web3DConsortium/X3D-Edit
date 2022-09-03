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

/*
 * BACKGROUNDCustomizer.java
 *
 * Created on Apr 29, 2010, 4:07:14 PM
 */
package org.web3d.x3d.palette.items;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.text.JTextComponent;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.web3d.x3d.X3DDataObject;
import static org.web3d.x3d.types.X3DPrimitiveTypes.colorFormat;
import static org.web3d.x3d.types.X3DPrimitiveTypes.radiansFormat;

/**
 * BACKGROUNDCustomizer.java
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey and Don Brutzman
 * @version $Id$
 */
public class BACKGROUNDCustomizer extends BaseCustomizer
{
  private final BACKGROUND background;
  private final JTextComponent target;
  private final X3DDataObject xObj;
  private final BackgroundContentPanel backgroundContentPanel;
  
  private int groundReadOnlyCol = 0;
  private int groundReadOnlyRow = 0;

  public BACKGROUNDCustomizer(BACKGROUND background, JTextComponent target, X3DDataObject xObj)
  {
    super(background);
    this.background = background;
    this.target = target;
    this.xObj = xObj;

    HelpCtx.setHelpIDString(this, "BACKGROUND_ELEM_HELPID");

    initComponents();
    
    backgroundContentPanel = new BackgroundContentPanel(this);
    backgroundContentPanel.setUrlListsVisible(true);
    bcpHoldingPanel.add (backgroundContentPanel);

    setUpAngleColorTables(); // must follow backgroundContentPanel instantiation

    // must precede setting url values
    backgroundContentPanel.setTarget(target); // enable urlList to reach back into jdom tree to getHeaderIdentifierPath()
    backgroundContentPanel.setMasterDocumentLocation(xObj.getPrimaryFile());
    
      backgroundContentPanel.setLeftUrls(background.getLeftUrls());
     backgroundContentPanel.setRightUrls(background.getRightUrls());
     backgroundContentPanel.setFrontUrls(background.getFrontUrls());
      backgroundContentPanel.setBackUrls(background.getBackUrls());
       backgroundContentPanel.setTopUrls(background.getTopUrls());
    backgroundContentPanel.setBottomUrls(background.getBottomUrls());
    
    validate(); // redraw
  }

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_BACKGROUND";
  }

  private void setUpGroundTable()
  {
    String[] gaa = background.getGroundAngleArray();
    checkAngles("groundAngle", gaa, Math.PI / 2.0);
    String[][] caa = background.getGroundColorArray();
    checkColors("groundColor", caa);
    setUpGroundTableCommon(gaa, caa);
  }

  public void setUpGroundTableDefault()
  {
    setUpGroundTableCommon(background.getGroundAngleDefault(), background.getGroundColorDefault());
  }

  private void setUpGroundTableCommon(String[] angle, String[][] colors)
  {
    Object[][] colorData;
    if (colors.length <= 0)
      colorData = new Object[][]{};
    else {
      colorData = new Object[colors.length][4];
      // do not reverse entries in this array, that was done already
      for (int i = 0; i < colors.length; i++) {
        String[] oa = new String[4];
        oa[0] = (i == (colors.length - 1) ? "nadir" : angle[i]); // use regular angles first, nadir is last element
        oa[1] = colors[i][0];
        oa[2] = colors[i][1];
        oa[3] = colors[i][2];
        colorData[i] = oa;
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
    String[] saa = background.getSkyAngleArray();
    checkAngles("skyAngle", saa, Math.PI);
    String[][] caa = background.getSkyColorArray();
    checkColors("skyColor", caa);
    setupSkyTableCommon(saa, caa);
  }

  public void setupSkyTableDefault()
  {
    setupSkyTableCommon(background.getSkyAngleDefault(), background.getSkyColorDefault());
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
//    Object[][] colorData;
//    String[] aa = background.getSkyAngleArray();
//    String[][] caa = background.getSkyColorArray();
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
//    bcp.skyColorList.setColumnTitles(new String[]{"angle","r","g","b","..."});
//    bcp.skyColorList.setRedColumn(1);
//    //bcp.skyColorList.setTitle("sky color");
//    bcp.skyColorList.doTrailingColorChooser();
//
//    bcp.skyColorList.setNewRowData(new Object[]{"0","0","0","0"});
//    bcp.skyColorList.setData(colorData);
//    bcp.skyColorList.setReadOnlyCell(0,0);
//    bcp.skyColorList.setInsertRowFilter(new MySkyRowInserter());
//    bcp.skyColorList.setRemoveRowFilter(new MySkyRowRemover());
//
//    aa = background.getGroundAngleArray();
//    caa = background.getGroundColorArray();
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
//    bcp.groundColorList.setColumnTitles(new String[]{"angle","r","g","b","..."});
//    bcp.groundColorList.setRedColumn(1);
//    //bcp.groundColorList.setTitle("ground color");
//    bcp.groundColorList.doTrailingColorChooser();
//
//    bcp.groundColorList.setNewRowData(new Object[]{"0","0","0","0"});
//    bcp.groundColorList.setData(colorData);
//    int nrows = bcp.groundColorList.getTable().getRowCount();
//    if(nrows >0)
//      bcp.groundColorList.setReadOnlyCell(groundReadOnlyRow=(nrows-1), groundReadOnlyCol=0);
//    else
//      bcp.groundColorList.setReadOnlyCell(groundReadOnlyRow=0,groundReadOnlyCol=0);
//    bcp.groundColorList.setInsertRowFilter(new MyGroundRowInserter());
//    bcp.groundColorList.setRemoveRowFilter(new MyGroundRowRemover());
//  }

  class MySkyRowInserter implements ExpandableList.InsertRowFilterIf
  {
    @Override
    public int insertRowNumber(int selectedRow)
    {
      int rowCount = backgroundContentPanel.skyAngleColorList.getTable().getRowCount();
      if (rowCount <= 0)
        backgroundContentPanel.skyAngleColorList.setNewRowData(new Object[]{"zenith", "0", "0", "0"});
      else
        backgroundContentPanel.skyAngleColorList.setNewRowData(new Object[]{"0", "0", "0", "0"});
      return selectedRow;
    }
  }

  class MySkyRowRemover implements ExpandableList.RemoveRowFilterIf
  {
    @Override
    public int removeRowNumber(int selectedRow)
    {
      int rowCount = backgroundContentPanel.skyAngleColorList.getTable().getRowCount();
      if (selectedRow == -1 || rowCount <= 0)  // empty table or non selected
        return -1;

      // Don't remove top if there are ones below it
      if (rowCount > 1 && selectedRow == 0)
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
      if (backgroundContentPanel.groundAngleColorList.getTable().getRowCount() <= 0)
          backgroundContentPanel.groundAngleColorList.setNewRowData(new Object[]{"nadir", "0", "0", "0"});
      else
          backgroundContentPanel.groundAngleColorList.setNewRowData(new Object[]{"0", "0", "0", "0"});

      backgroundContentPanel.groundAngleColorList.unsetReadOnlyCell(groundReadOnlyRow, groundReadOnlyCol);
      backgroundContentPanel.groundAngleColorList.setReadOnlyCell(groundReadOnlyRow = rowCount, groundReadOnlyCol = 0); // last row (soon to be added)

      if (rowCount > 0 && selectedRow == rowCount - 1) //if last row is selected one
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
      if (rowCount > 1 && selectedRow == rowCount - 1)
        return -1;

      backgroundContentPanel.groundAngleColorList.unsetReadOnlyCell(groundReadOnlyRow, groundReadOnlyCol);
      backgroundContentPanel.groundAngleColorList.setReadOnlyCell(groundReadOnlyRow = (rowCount - 2), groundReadOnlyCol = 0); // last row

      return selectedRow;
    }
  }

  private void checkAngles(String fieldName, String[] angles, double maxAngle)
  {
      for (int i = 0; i < angles.length; i++)
      {
          if ((angles[i] != null) && (angles[i].length() > 0))
          {
              if (fieldName.startsWith("ground") && (angles[i].equalsIgnoreCase("1.571")))
                  angles[i] = "1.57079"; // author convenience, avoid exception
              if (angles[i].equalsIgnoreCase("horizon"))
                  angles[i] = "1.57079"; // author convenience
              double angle = Double.parseDouble(angles[i]);
              if ((angle > maxAngle) && (angle < 3.2)) // less than approximately Pi
              {
                  String message;
                  message = "<html><center>Large value " + angle + " provided for angle " + fieldName + "[" + i + "]" + ", which is a radians value &gt; " + radiansFormat.format(maxAngle) +
                          "<br/><br/>Convert to maximum value, <b>" + radiansFormat.format(maxAngle) + " radians</b>?";
                  NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                          message, "X3D angles are in radians", NotifyDescriptor.YES_NO_OPTION);
                  if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
                  {
                      angle = maxAngle;
                      angles[i] = (radiansFormat.format(angle));
                  }
              }
              else if (angle > maxAngle)
              {
                  String message;
                  message = "<html><center>Large value " + angle + " provided for angle " + fieldName + "[" + i + "]" + ", which is a radians value &gt; " + radiansFormat.format(maxAngle) +
                          "<br/><br/>Convert <b>" + angle + " degrees</b> to <b>" +
                            radiansFormat.format((angle % 360.0) * Math.PI / 180.0) + " radians</b>?";
                  NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                          message, "X3D angles are in radians", NotifyDescriptor.YES_NO_OPTION);
                  if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
                  {
                      angle = (angle % 360.0) * Math.PI / 180.0;
                      angles[i] = (radiansFormat.format(angle));
                  }
              }
          }
      }
  }

  private void checkColors(String fieldName, String[][] colors)
  {
      for (int i = 0; i < colors.length; i++)
        {
        for (int j = 0; j < 3; j++)
        {
            if ((colors[i][j] != null) && (colors[i][j].length() > 0))
            {
                double color = Double.parseDouble(colors[i][j]);
                if ((color > 1.0) && (color <= 255.0))
                {
                    String message;
                    message = "<html><center>Large value " + color + " provided for " + fieldName + "[" + i + "][" + j + "]" + ", which must be an RGB value ranging [0..1]<br/><br/>Divide by 255?";
                    NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                            message, "X3D colors are RGB values", NotifyDescriptor.YES_NO_OPTION);
                    if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
                    {
                        color = color / 255.0;
                        colors[i][j] = (colorFormat.format(color));
                    }
                }
                else if (color > 1.0)
                {
                    String message;
                    message = "<html><center>Large value " + color + " provided for " + fieldName + "[" + i + "][" + j + "]" + ", which must be an RGB value ranging [0..1]<br/><br/>Clamp to 1?";
                    NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                            message, "X3D colors are RGB values", NotifyDescriptor.YES_NO_OPTION);
                    if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
                    {
                        color = 1.0;
                        colors[i][j] = (colorFormat.format(color));
                    }
                }
                else if (color < 0.0)
                {
                    String message;
                    message = "<html><center>Negative value " + color + " provided for " + fieldName + "[" + i + "][" + j + "]" + ", which must be an RGB value ranging [0..1]<br/><br/>Clamp to 0?";
                    NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                            message, "X3D colors are RGB values", NotifyDescriptor.YES_NO_OPTION);
                    if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
                    {
                        color = 0.0;
                        colors[i][j] = (colorFormat.format(color));
                    }
                }
            }
        }
      }
  }

  private void checkSortColorsAndAngles(String fieldName, String[] angles, String[][] colors)
  {
      // note length of angles array is one less than length of colors array
      // TODO
  
  }

  private void unloadColorsAndAngles()
  {
    String[][] saa = backgroundContentPanel.groundAngleColorList.getData();
    if (saa == null || saa.length <= 0) 
    {
      background.setGroundAngleArray(background.getGroundAngleDefault());
      background.setGroundColorArray(background.getGroundColorDefault());
    }
    else 
    {
      String[] gaa = new String[saa.length - 1];
      // do not reverse here, that is done when value is set
      String[][] caa = new String[saa.length][3];
      for (int i = 0; i < saa.length; i++) {
        String[] sa = saa[i];
        if (i != saa.length - 1) // nadir is last displayed
          gaa[i] = sa[0];
        String[] ca = new String[3];
        ca[0] = sa[1];
        ca[1] = sa[2];
        ca[2] = sa[3];
//        caa[r++] = ca;
        caa[i] = ca;
      }
      checkAngles("groundAngle", gaa, Math.PI / 2.0);
      background.setGroundAngleArray(gaa);
      checkColors("groundColor", caa);
      background.setGroundColorArray(caa);
    }

    saa = backgroundContentPanel.skyAngleColorList.getData();
    if (saa == null || saa.length <= 0) {
      background.setSkyAngleArray(new String[]{});
      background.setSkyColorArray(new String[][]{});
    }
    else {
      String[] skyaa = new String[saa.length - 1];
      int r = 0;
      String[][] caa = new String[saa.length][3];
      for (String[] sa : saa) {
        if (r != 0)
          skyaa[r - 1] = sa[0];
        String[] ca = new String[3];
        ca[0] = sa[1];
        ca[1] = sa[2];
        ca[2] = sa[3];
        caa[r++] = ca;
      }
      checkAngles("skyAngle", skyaa, Math.PI);
      background.setSkyAngleArray(skyaa);
      checkColors("skyColor", caa);
      background.setSkyColorArray(caa);
    }
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        GridBagConstraints gridBagConstraints;

        dEFUSEpanel1 = getDEFUSEpanel();
        bcpHoldingPanel = new JPanel();
        nodeHintPanel = new JPanel();
        hintLabel = new JLabel();

        setMinimumSize(null);
        setPreferredSize(null);
        setLayout(new GridBagLayout());

        dEFUSEpanel1.setName("dEFUSEpanel1"); // NOI18N
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.PAGE_START;
        add(dEFUSEpanel1, gridBagConstraints);

        bcpHoldingPanel.setName("bcpHoldingPanel"); // NOI18N
        bcpHoldingPanel.setLayout(new GridBagLayout());
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        add(bcpHoldingPanel, gridBagConstraints);

        nodeHintPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        nodeHintPanel.setName("nodeHintPanel"); // NOI18N
        nodeHintPanel.setLayout(new GridBagLayout());

        hintLabel.setHorizontalAlignment(SwingConstants.CENTER);
        hintLabel.setText(NbBundle.getMessage(BACKGROUNDCustomizer.class, "BackgroundCustomizer.hintLabel.text")); // NOI18N
        hintLabel.setToolTipText(NbBundle.getMessage(BACKGROUNDCustomizer.class, "BackgroundContentPanel.hintLabel1.toolTipText")); // NOI18N
        hintLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        hintLabel.setName("hintLabel"); // NOI18N
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        nodeHintPanel.add(hintLabel, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        add(nodeHintPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JPanel bcpHoldingPanel;
    private DEFUSEpanel dEFUSEpanel1;
    private JLabel hintLabel;
    private JPanel nodeHintPanel;
    // End of variables declaration//GEN-END:variables

  @Override
  public void unloadInput()
  {
    unLoadDEFUSE();

    unloadColorsAndAngles();
    
    backgroundContentPanel.refreshUrlListValues(); // make sure we are getting latest values from panels

	background.setLeftUrls(  backgroundContentPanel.getleftUrls());
	background.setRightUrls( backgroundContentPanel.getrightUrls());
	background.setTopUrls(   backgroundContentPanel.gettopUrls());
	background.setBottomUrls(backgroundContentPanel.getbottomUrls());
	background.setFrontUrls( backgroundContentPanel.getfrontUrls());
	background.setBackUrls(  backgroundContentPanel.getbackUrls());
  }
}
