/*
Copyright (c) 1995-2022 held by the author(s).  All rights reserved.
 
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

import java.util.List;
import java.util.Vector;
import javax.swing.text.JTextComponent;
import org.openide.util.HelpCtx;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * NAVIGATIONINFOCustomizer.java
 * Created on August 16, 2007, 3:31 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class NAVIGATIONINFOCustomizer extends BaseCustomizer
{
  private NAVIGATIONINFO navinfo;
  private JTextComponent target;
  
  /** Creates new form NAVIGATIONINFOCustomizer */
  public NAVIGATIONINFOCustomizer(NAVIGATIONINFO navinfo, JTextComponent target)
  {
    super(navinfo);
    this.navinfo = navinfo;
    this.target = target;
      
    HelpCtx.setHelpIDString(NAVIGATIONINFOCustomizer.this, "NAVIGATIONINFO_ELEM_HELPID");
    
    initComponents();
    avatarXTF.setText(navinfo.getAvatarSizeX());
    avatarYTF.setText(navinfo.getAvatarSizeY());
    avatarZTF.setText(navinfo.getAvatarSizeZ());

    headlightChkB.setSelected(navinfo.isHeadlight());
    speedTF.setText(navinfo.getSpeed());
    transitionTimeTF.setText(navinfo.getTransitionTime());
    visibilityLimitTF.setText(navinfo.getVisibilityLimit());
    
    // TODO handle special values provided by author or scene, but not listed in specification

    // set type array
    String[] ta = navinfo.getType();  // get selected types
    Vector<Integer> v = new Vector<>();
    int wh = 0;
    for(String s : NAVINFO_ATTR_TYPE_CHOICES) {  // values in list order
      for(String ss : ta) {
        if(ss.equals(s)) {
          v.add(wh);
          break;
        }
      }
      wh++;
    }
    int[] inta = new int[v.size()];
    int count=0;
    for(Integer I : v)
    {
      inta[count++] = I;
    }
    typeList.setSelectedIndices(inta);
    typeList.setVisibleRowCount(6);

//    transTypeCB.setSelectedItem(navinfo.getTransitionType());
    
    // set transitionType array
    ta = navinfo.getTransitionType();  // get selected types
    v = new Vector<>();
    wh = 0;
    for(String s : NAVINFO_ATTR_TRANSTYPE_CHOICES) {  // values in list order
      for(String ss : ta) {
        if(ss.equals(s)) {
          v.add(wh);
          break;
        }
      }
      wh++;
    }
    inta = new int[v.size()];
    count=0;
    for(Integer I : v)
    {
      inta[count++] = I;
    }
    transitionTypeList.setSelectedIndices(inta);
    transitionTypeList.setVisibleRowCount(3);
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
        leftPan = new javax.swing.JPanel();
        typeLabel = new javax.swing.JLabel();
        transitionTypeLabel = new javax.swing.JLabel();
        typeScrollPane = new javax.swing.JScrollPane();
        typeList = new javax.swing.JList<>();
        transitionTypeScrollPane = new javax.swing.JScrollPane();
        transitionTypeList = new javax.swing.JList<>();
        speedLabel = new javax.swing.JLabel();
        speedTF = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        headlightChkB = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        transitionTimeTF = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        visibilityLimitTF = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        avatarSizeLabel = new javax.swing.JLabel();
        avatarXTF = new javax.swing.JTextField();
        avatarYTF = new javax.swing.JTextField();
        avatarZTF = new javax.swing.JTextField();
        collisionDistanceLabel = new javax.swing.JLabel();
        viewerHeightLabel = new javax.swing.JLabel();
        WALKoverHeightLabel = new javax.swing.JLabel();
        hintLabel = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(620, 590));
        setLayout(new java.awt.GridBagLayout());

        dEFUSEpanel1.setMinimumSize(new java.awt.Dimension(198, 77));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        add(dEFUSEpanel1, gridBagConstraints);

        leftPan.setLayout(new java.awt.GridBagLayout());

        typeLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        typeLabel.setText("type");
        typeLabel.setToolTipText("Enter one or more quoted SFString values: \"EXAMINE\" \"WALK\" \"FLY\" \"LOOKAT\" \"ANY\" \"NONE\"");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        leftPan.add(typeLabel, gridBagConstraints);

        transitionTypeLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        transitionTypeLabel.setText("transitionType");
        transitionTypeLabel.setToolTipText("Type of camera transition between viewpoints, enter one or more quoted SFString values");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 0, 0);
        leftPan.add(transitionTypeLabel, gridBagConstraints);

        typeScrollPane.setMinimumSize(new java.awt.Dimension(73, 120));
        typeScrollPane.setPreferredSize(new java.awt.Dimension(73, 120));

        typeList.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "ANY", "WALK", "EXAMINE", "FLY", "LOOKAT", "NONE" }));
        typeList.setListData(NAVINFO_ATTR_TYPE_CHOICES);
        typeList.setToolTipText("Enter one or more quoted SFString values: \"EXAMINE\" \"WALK\" \"FLY\" \"LOOKAT\" \"ANY\" \"NONE\"");
        typeList.setPreferredSize(new java.awt.Dimension(54, 120));
        typeList.setVisibleRowCount(6);
        typeScrollPane.setViewportView(typeList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 5, 0, 0);
        leftPan.add(typeScrollPane, gridBagConstraints);

        transitionTypeList.setModel(new javax.swing.DefaultComboBoxModel<String>(new String[] { "TELEPORT", "LINEAR", "ANIMATE" }));
        transitionTypeList.setToolTipText("Enter one or more quoted SFString values: TELEPORT\" \"LINEAR\" \"ANIMATE\"");
        transitionTypeList.setMinimumSize(new java.awt.Dimension(73, 60));
        transitionTypeList.setPreferredSize(new java.awt.Dimension(73, 60));
        transitionTypeList.setVisibleRowCount(3);
        transitionTypeScrollPane.setViewportView(transitionTypeList);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 0);
        leftPan.add(transitionTypeScrollPane, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 0, 5);
        add(leftPan, gridBagConstraints);

        speedLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        speedLabel.setText("speed");
        speedLabel.setToolTipText("Default rate of view travel during navigation (meters/second)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(speedLabel, gridBagConstraints);

        speedTF.setColumns(10);
        speedTF.setToolTipText("Default rate of view travel during navigation (meters/second)");
        speedTF.setMinimumSize(new java.awt.Dimension(60, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(speedTF, gridBagConstraints);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel3.setText("headlight");
        jLabel3.setToolTipText("Enable directional light that follows user's point of view");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(jLabel3, gridBagConstraints);

        headlightChkB.setToolTipText("Enable directional light that follows user's point of view");
        headlightChkB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(headlightChkB, gridBagConstraints);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel5.setText("transitionTime");
        jLabel5.setToolTipText("Duration of viewpoint transition in seconds");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(jLabel5, gridBagConstraints);

        transitionTimeTF.setColumns(10);
        transitionTimeTF.setToolTipText("Duration of viewpoint transition in seconds");
        transitionTimeTF.setMinimumSize(new java.awt.Dimension(60, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(transitionTimeTF, gridBagConstraints);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel6.setText("visibilityLimit");
        jLabel6.setToolTipText("Geometry beyond visibilityLimit (in meters) may not be rendered, 0 means no view-frustrum culling (see Help)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(jLabel6, gridBagConstraints);

        visibilityLimitTF.setColumns(10);
        visibilityLimitTF.setToolTipText("Geometry beyond visibilityLimit (in meters) may not be rendered, 0 means no view-frustrum culling (see Help)");
        visibilityLimitTF.setMinimumSize(new java.awt.Dimension(60, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(visibilityLimitTF, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 3, 8, 3);
        add(jSeparator1, gridBagConstraints);

        avatarSizeLabel.setText("avatarSize ");
        avatarSizeLabel.setToolTipText("parameter values for camera collision distance, avatar height, and step-over height");
        avatarSizeLabel.setHorizontalTextPosition(javax.swing.SwingConstants.RIGHT);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(avatarSizeLabel, gridBagConstraints);

        avatarXTF.setColumns(10);
        avatarXTF.setToolTipText("collision distance (in meters) between user and geometry");
        avatarXTF.setMinimumSize(new java.awt.Dimension(60, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(avatarXTF, gridBagConstraints);

        avatarYTF.setColumns(10);
        avatarYTF.setToolTipText("viewer height (in meters) above terrain");
        avatarYTF.setMinimumSize(new java.awt.Dimension(60, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(avatarYTF, gridBagConstraints);

        avatarZTF.setColumns(10);
        avatarZTF.setToolTipText("tallest height (in meters) that viewer can WALK over");
        avatarZTF.setMinimumSize(new java.awt.Dimension(60, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(avatarZTF, gridBagConstraints);

        collisionDistanceLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        collisionDistanceLabel.setText("collision distance ");
        collisionDistanceLabel.setToolTipText("collision distance (in meters) between user and geometry");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(collisionDistanceLabel, gridBagConstraints);

        viewerHeightLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        viewerHeightLabel.setText("viewer height ");
        viewerHeightLabel.setToolTipText("viewer height (in meters) above terrain");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(viewerHeightLabel, gridBagConstraints);

        WALKoverHeightLabel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        WALKoverHeightLabel.setText("WALK over height ");
        WALKoverHeightLabel.setToolTipText("tallest height (in meters) that viewer can WALK over");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(WALKoverHeightLabel, gridBagConstraints);

        hintLabel.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        hintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel.setText("<html>  <p align=\"center\"><b>NavigationInfo</b> describes the user's viewing model, user navigation-interaction modalities, <br /> and also dimensional characteristics of the user's (typically invisible) avatar.</p>  \n<ul>\n<li> Hint: or inspection of simple objects, usability often improves with type=\"EXAMINE\" \"ANY\".</li>\n<li> Hint: Background, Fog, GeoViewpoint, NavigationInfo, OrthoViewpoint, TextureBackground and Viewpoint <br />are bindable nodes, meaning that no more than one of each node type can be active at a given time. </li>\n<li> Hint: NavigationInfo types '\"WALK\" \"FLY\"' support camera-to-object collision detection. </li>\n</ul>");
        hintLabel.setToolTipText("NavigationInfo defines modalities for how a user can navigate within a scene");
        hintLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        hintLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(hintLabel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel WALKoverHeightLabel;
    private javax.swing.JLabel avatarSizeLabel;
    private javax.swing.JTextField avatarXTF;
    private javax.swing.JTextField avatarYTF;
    private javax.swing.JTextField avatarZTF;
    private javax.swing.JLabel collisionDistanceLabel;
    private org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel1;
    private javax.swing.JCheckBox headlightChkB;
    private javax.swing.JLabel hintLabel;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JPanel leftPan;
    private javax.swing.JLabel speedLabel;
    private javax.swing.JTextField speedTF;
    private javax.swing.JTextField transitionTimeTF;
    private javax.swing.JLabel transitionTypeLabel;
    private javax.swing.JList<String> transitionTypeList;
    private javax.swing.JScrollPane transitionTypeScrollPane;
    private javax.swing.JLabel typeLabel;
    private javax.swing.JList<String> typeList;
    private javax.swing.JScrollPane typeScrollPane;
    private javax.swing.JLabel viewerHeightLabel;
    private javax.swing.JTextField visibilityLimitTF;
    // End of variables declaration//GEN-END:variables
  
  @Override
  public String getNameKey()
  {
    return "NAME_X3D_NAVIGATIONINFO";
  }

  @Override
  public void unloadInput() throws IllegalArgumentException
  {
    unLoadDEFUSE();
    
    navinfo.setAvatarSizeX(avatarXTF.getText().trim());
    navinfo.setAvatarSizeY(avatarYTF.getText().trim());
    navinfo.setAvatarSizeZ(avatarZTF.getText().trim());
    
    navinfo.setHeadlight(headlightChkB.isSelected());
    navinfo.setSpeed(speedTF.getText().trim());
    navinfo.setTransitionTime(transitionTimeTF.getText().trim());

    List<String> oa = typeList.getSelectedValuesList();
    String[] sa = new String[oa.size()];
    for(int i=0;i<oa.size();i++)
      sa[i] = oa.get(i);
    navinfo.setType(sa);
    
//    navinfo.setTransitionType((String)transTypeCB.getSelectedItem());
    oa = transitionTypeList.getSelectedValuesList();
    sa = new String[oa.size()];
    for(int i=0;i<oa.size();i++)
      sa[i] = oa.get(i);
    navinfo.setTransitionType(sa);

    navinfo.setVisibilityLimit(visibilityLimitTF.getText().trim());
  }
}