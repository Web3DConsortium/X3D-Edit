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

import static org.web3d.x3d.types.X3DSchemaData.*;
/**
 * COORDINATECustomizer.java
 * Created on Sep 11, 2007, 3:05 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class COORDINATECustomizer extends BaseCustomizer
{
  private final COORDINATE coordinate;
  private final JTextComponent target;

  public COORDINATECustomizer(COORDINATEDOUBLE coordinateDouble, JTextComponent target)
  {
    //COORDINATECustomizer((COORDINATE)coordinateDouble, target);
    super(coordinateDouble);
    this.coordinate = coordinateDouble;
    this.target = target;
    this.coordinate.setDouble(true);

    commonInitialization ();
  }

  public COORDINATECustomizer(COORDINATE coordinate, JTextComponent target)
  {
    super(coordinate);
    this.coordinate = coordinate;
    this.target = target;

    commonInitialization ();
  }
  private void commonInitialization ()
  {
    initComponents();

    expandableListPoints.setTitle("point array");
    expandableListPoints.setColumnTitles(new String[]{"#","x","y","z"});
    expandableListPoints.setColumnToolTips(new String[]{"index","point x value","point y value","point z value"});
    expandableListPoints.setHeaderTooltip ("Coordinate (x y z) points");
    expandableListPoints.doIndexInFirstColumn(true);
    expandableListPoints.setRedColumn(-1); // 0 is index, -1 means no color editing
    expandableListPoints.setColumnWidthAndResizeStrategy(false, 75);
    if (coordinate.isDouble())
    {
        expandableListPoints.setToolTipText("point array of double-precision 3D coordinates");
        expandableListPoints.setHeaderTooltip("CoordinateDouble point (x y z) values");
        HelpCtx.setHelpIDString(this, "COORDINATEDOUBLE_ELEM_HELPID");
        hintLabel.setText("<html><p align='center'><b>CoordinateDouble</b> contains point values for building geometry</p>");
    }
    else
    {
        expandableListPoints.setToolTipText("point array of single-precision 3D coordinates");
        expandableListPoints.setHeaderTooltip("Coordinate point (x y z) values");
        HelpCtx.setHelpIDString(this, "COORDINATE_ELEM_HELPID");
        hintLabel.setText("<html><p align='center'><b>Coordinate</b> contains point values for building geometry</p>");
    }
    
    String[][] saa = coordinate.getPoint(); // may be 0-length
    expandableListPoints.setData(saa);
    expandableListPoints.setShowAppendCommasLineBreaks(true);
    expandableListPoints.setInsertCommas    (coordinate.isInsertCommas());
    expandableListPoints.setInsertLineBreaks(coordinate.isInsertLineBreaks());
    
    expandableListPoints.setGeneratePointsDescriptions(COORDINATE_ATTR_POINT_DESCRIPTIONS);
    expandableListPoints.setGeneratePointsChoices(COORDINATE_ATTR_POINT_CHOICES); // provide choice labels for appending
    expandableListPoints.setGeneratePointsEnumerationValues(COORDINATE_ATTR_POINT_VALUES);
    
	expandableListPoints.setIncludeMakeOpenClosedButton(true);
	expandableListPoints.setTitle(listTitle());
  }
  public String listTitle ()
  {
	int currentLength = 0;
	if (expandableListPoints.getData() != null)
		currentLength = expandableListPoints.getData().length;
	String title = "point array (" + currentLength + " total)";
	if (expandableListPoints.isClosed())
		   title += " is closed (coincident endpoints)";
	else   title += " is open (distinct endpoints)";
	return title;
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
        expandableListPoints = new org.web3d.x3d.palette.items.ExpandableList();
        hintLabel = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(400, 250));
        setPreferredSize(new java.awt.Dimension(700, 640));
        setLayout(new java.awt.GridBagLayout());

        dEFUSEpanel1.setMinimumSize(new java.awt.Dimension(198, 77));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel1, gridBagConstraints);

        expandableListPoints.setPreferredSize(new java.awt.Dimension(632, 250));
        expandableListPoints.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                expandableListPointsPropertyChange(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(expandableListPoints, gridBagConstraints);

        hintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel.setText("<html><p align='center'><b>Coordinate|CoordinateDouble</b> contains point values for building geometry</p>");
        hintLabel.setToolTipText("Coordinate point values can be applied in many ways");
        hintLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        hintLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 4;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(hintLabel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void expandableListPointsPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_expandableListPointsPropertyChange
        expandableListPoints.setTitle(listTitle());
		repaint();
		revalidate();
    }//GEN-LAST:event_expandableListPointsPropertyChange
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private org.web3d.x3d.palette.items.ExpandableList expandableListPoints;
    private javax.swing.JLabel hintLabel;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    if (coordinate.isDouble())
    {
        return "NAME_X3D_COORDINATEDOUBLE";
    }
    else
    {
        return "NAME_X3D_COORDINATE";
    }
  }

  @Override
  public void unloadInput() throws IllegalArgumentException
  {
    unLoadDEFUSE();

    coordinate.setPoint(expandableListPoints.getData());
    coordinate.setInsertCommas    (expandableListPoints.isInsertCommasSet());
    coordinate.setInsertLineBreaks(expandableListPoints.isInsertLineBreaksSet());
  }  
}
