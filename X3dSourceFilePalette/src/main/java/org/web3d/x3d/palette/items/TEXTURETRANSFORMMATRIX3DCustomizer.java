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
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.text.JTextComponent;
import org.openide.util.HelpCtx;

/**
 * TEXTURETRANSFORMMATRIX3DCustomizer.java
 * Created on 20 November 2011
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class TEXTURETRANSFORMMATRIX3DCustomizer extends BaseCustomizer
{
    private TEXTURETRANSFORMMATRIX3D textureTransformMatrix3D;
    private JTextComponent target;
//    TODO matrix manipulation buttons
//    private Float[][] m = new Float[4][4]; 
//    private float determinant;

  public TEXTURETRANSFORMMATRIX3DCustomizer(TEXTURETRANSFORMMATRIX3D textureTransformMatrix3D, JTextComponent target)
  {
    super(textureTransformMatrix3D);
    this.textureTransformMatrix3D = textureTransformMatrix3D;
    this.target = target;
                           
    HelpCtx.setHelpIDString(this, "TEXTURETRANSFORMMATRIX3D_ELEM_HELPID");   
    
    initComponents();
    
    expandableListMatrix.setTitle("matrix: 4x4 transformation array");
    expandableListMatrix.setHeaderTooltip("TextureTransformMatrix3D matrix values");
    expandableListMatrix.setColumnTitles  (new String[]{"#","0","1","2","3"});
    expandableListMatrix.setColumnToolTips(new String[]{"row, column","column 0","column 1","column 2","column 3"});
    expandableListMatrix.setNewRowData(new Object[]{"0","0","0","0"});
    expandableListMatrix.getTable().setRowHeight(16);
    expandableListMatrix.doIndexInFirstColumn(true);
    expandableListMatrix.setFlippableRowData(true);
    expandableListMatrix.setDataStringBased(true);
    expandableListMatrix.setShowAppendCommasLineBreaks(true);
    expandableListMatrix.setInsertCommas    (textureTransformMatrix3D.isInsertCommas());
    expandableListMatrix.setInsertLineBreaks(textureTransformMatrix3D.isInsertLineBreaks());
    expandableListMatrix.setRedColumn(-1); // 0 is index, -1 means no color editing
    
    String[][] saa = textureTransformMatrix3D.getMatrix();
    expandableListMatrix.setData(saa);
  }
  
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        GridBagConstraints gridBagConstraints;
        DEFUSEpanel dEFUSEpanel1 = getDEFUSEpanel();
        expandableListMatrix = new ExpandableList();
        nodeHintPanel = new JPanel();
        descriptionLabel = new JLabel();

        setLayout(new GridBagLayout());
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        add(dEFUSEpanel1, gridBagConstraints);

        expandableListMatrix.setMinimumSize(new Dimension(463, 200));
        expandableListMatrix.setPreferredSize(new Dimension(473, 200));
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 160;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new Insets(3, 3, 3, 0);
        add(expandableListMatrix, gridBagConstraints);

        nodeHintPanel.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        nodeHintPanel.setLayout(new GridBagLayout());

        descriptionLabel.setHorizontalAlignment(SwingConstants.CENTER);
        descriptionLabel.setText("<html><b>TextureTransformMatrix3D</b> is a general 4×4 transformation matrix");
        descriptionLabel.setToolTipText("TextureTransformMatrix3D maps 3D texture voxels prior to vertex application or volume rendering");
        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 250;
        gridBagConstraints.insets = new Insets(10, 3, 10, 3);
        nodeHintPanel.add(descriptionLabel, gridBagConstraints);

        gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new Insets(3, 3, 3, 3);
        add(nodeHintPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JLabel descriptionLabel;
    private ExpandableList expandableListMatrix;
    private JPanel nodeHintPanel;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_TEXTURETRANSFORMMATRIX3D";
  }

  @Override
  public void unloadInput() throws IllegalArgumentException
  {
    unLoadDEFUSE();
    
    String[][] data = expandableListMatrix.getData();
    textureTransformMatrix3D.setMatrix(expandableListMatrix.getData());
    textureTransformMatrix3D.setInsertCommas    (expandableListMatrix.isInsertCommasSet());
    textureTransformMatrix3D.setInsertLineBreaks(expandableListMatrix.isInsertLineBreaksSet());
    
    // must first set (possibly partial or excessive) values
    if ((data == null) || (data.length != 4) || (data[0] == null) || (data[0].length != 4))
    {
        String    errorMessage = "Illegal matrix size: must be 4x4, ";
        if      ((data == null) || (data.length == 0) || (data[0] == null))
                  errorMessage += "can reset to default identity matrix...";
        else if ((data.length < 4) || (data[0].length < 4))
                  errorMessage += "can replace missing values with 0...";
        else      errorMessage += "can truncate extra values...";
        throw new IllegalArgumentException(errorMessage); // point of no return for this method
    }    
  }  
}