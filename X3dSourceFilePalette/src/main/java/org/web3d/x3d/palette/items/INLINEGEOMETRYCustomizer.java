/*
Copyright (c) 1995-2026 held by the author(s).  All rights reserved.
 
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

import javax.swing.DefaultComboBoxModel;
import javax.swing.text.JTextComponent;
import org.openide.util.HelpCtx;
import org.web3d.x3d.X3DDataObject;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * INLINEGEOMETRCustomizer.java
 *
 * @author Don Brutzman
 * @version $Id$
 */
public class INLINEGEOMETRYCustomizer extends BaseCustomizer
{
  private final INLINEGEOMETRY inlineGeometry;
  private final JTextComponent target;
  private final X3DDataObject x3DDataObject;
  private String   originalContent = "";
  private final String INLINEGEOMETRY_PROFILE_METADATA_PREFIX = "<MetadataString name='profile' value='\"";
  
  /** Creates new form INLINEGEOMETRYCustomizer
     * @param inlineGeometry INLINEGEOMETRY node
     * @param target component
     * @param x3DDataObject scene data object */
  public INLINEGEOMETRYCustomizer(INLINEGEOMETRY inlineGeometry, JTextComponent target, X3DDataObject x3DDataObject)
  {
    super(inlineGeometry);
    this.inlineGeometry = inlineGeometry;
    this.target = target;
    this.x3DDataObject = x3DDataObject;
    
    HelpCtx.setHelpIDString(INLINEGEOMETRYCustomizer.this, "INLINEGEOMETRY_ELEM_HELPID");

    inlineGeometry.setVisualizationSelectionAvailable(false); // must precede initComponents() interface initialization
    inlineGeometry.setVisualizationTooltip("Add wireframe Box and axes to show boundingBox center and size (if defined)");
    
    initComponents();
    
    // can be the proxy field of a Collision node
    super.getDEFUSEpanel().setContainerFieldChoices(INLINEGEOMETRY_CONTAINERFIELD_CHOICES, INLINEGEOMETRY_CONTAINERFIELD_TOOLTIPS);
    super.getDEFUSEpanel().setContainerField(inlineGeometry.getContainerField()); // reset value to match updated JComboBox data model
    // DEFUSEpanel initialization must NOT be repeated or else array of choices will be overwritten
    
    if (!inlineGeometry.getDescription().isBlank())
    {
        descriptionTF.setText(inlineGeometry.getDescription());
    }
    urlList.setMasterDocumentLocation(x3DDataObject.getPrimaryFile());
    urlList.setUrlData(inlineGeometry.getUrls());
    urlList.setTarget(target); // enable urlList to reach back into jdom tree to getHeaderIdentifierPath()
    urlList.setFileChooserStlPlyX3d();
    
    urlList.checkUrlValues();
    // TODO check X3D version 4.0+ for gltf/glb file extensions urls, Netowrking component level 4 as well
    
    loadCB.setSelected(inlineGeometry.isLoad());
        insertCommasCheckBox.setSelected(inlineGeometry.isInsertCommas());
    insertLineBreaksCheckBox.setSelected(inlineGeometry.isInsertLineBreaks());

    setDefaultDEFname ();
    
    originalContent = inlineGeometry.getContent();
    if (originalContent.trim().isEmpty())
        originalContent = "";
    
    String expectedProfile = inlineGeometry.getExpectedProfile(); // utilize previous hint comment
    if      (expectedProfile.equals("Immersive"))
         expectedProfileComboBox.setSelectedItem("Immersive");
    else if (expectedProfile.equals("Interchange"))
         expectedProfileComboBox.setSelectedItem("Interchange");
    else if (expectedProfile.equals("Interactive"))
         expectedProfileComboBox.setSelectedItem("Interactive");
    else if (expectedProfile.equals("CADInterchange"))
         expectedProfileComboBox.setSelectedItem("CADInterchange");
    else if (expectedProfile.equals("Full"))
         expectedProfileComboBox.setSelectedItem("Full");
    else if (expectedProfile.equals("Core"))
         expectedProfileComboBox.setSelectedItem("Core");
    else expectedProfileComboBox.setSelectedIndex(0); // empty choice
  }
  private void setDefaultDEFname ()
  {
	if ((urlList == null) || (urlList.getUrlData() == null) || (urlList.getUrlData().length == 0))
	{
		super.getDEFUSEpanel().setDefaultDEFname("New" + "InlineGeometry");
		return;
	}
    // extract file name (minus extension) as candidate DEF name
    String fileName = urlList.getUrlData()[0];
    if (fileName.contains("/"))
        fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
    if (fileName.contains("\\"))
        fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
    if (fileName.contains("."))
        fileName = fileName.substring(0,fileName.lastIndexOf("."));
    if (fileName.length() > 0)
        fileName += "InlineGeometry"; // otherwise empty
    
    super.getDEFUSEpanel().setDefaultDEFname(fileName);
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
        descriptionLabel1 = new javax.swing.JLabel();
        descriptionTF = new javax.swing.JTextField();
        loadLabel = new javax.swing.JLabel();
        loadCB = new javax.swing.JCheckBox();
        urlLabel = new javax.swing.JLabel();
        urlList = new org.web3d.x3d.palette.items.UrlExpandableList2();
        appendLabel = new javax.swing.JLabel();
        insertCommasCheckBox = new javax.swing.JCheckBox();
        insertLineBreaksCheckBox = new javax.swing.JCheckBox();
        expectedProfileLabel = new javax.swing.JLabel();
        expectedProfileComboBox = new javax.swing.JComboBox<>();
        expectedProfileNoteLabel = new javax.swing.JLabel();
        nodeHintPanel = new javax.swing.JPanel();
        descriptionLabel = new javax.swing.JLabel();
        spacerLabel = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(700, 540));
        setPreferredSize(new java.awt.Dimension(700, 540));
        setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel1, gridBagConstraints);

        descriptionLabel1.setText("description");
        descriptionLabel1.setToolTipText("(X3D4) Author-provided prose that describes intended purpose of the node");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(descriptionLabel1, gridBagConstraints);

        descriptionTF.setForeground(new java.awt.Color(0, 153, 153));
        descriptionTF.setToolTipText("(X3D4) Author-provided prose that describes intended purpose of the node");
        descriptionTF.setMinimumSize(new java.awt.Dimension(50, 20));
        descriptionTF.setPreferredSize(new java.awt.Dimension(50, 20));
        descriptionTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                descriptionTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(descriptionTF, gridBagConstraints);

        loadLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        loadLabel.setText("load");
        loadLabel.setToolTipText("load=true means load immediately, load=false means defer loading or unload contained asset");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(loadLabel, gridBagConstraints);

        loadCB.setSelected(true);
        loadCB.setToolTipText("load=true means load immediately, load=false means defer loading or unload contained asset");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(loadCB, gridBagConstraints);

        urlLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        urlLabel.setText("url");
        urlLabel.setToolTipText("Address of X3D world to load into current scene");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(urlLabel, gridBagConstraints);

        urlList.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        urlList.setMinimumSize(new java.awt.Dimension(50, 50));
        urlList.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                urlListPropertyChange(evt);
            }
        });
        urlList.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                urlListKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 5.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(urlList, gridBagConstraints);

        appendLabel.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        appendLabel.setText("append:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        add(appendLabel, gridBagConstraints);

        insertCommasCheckBox.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        insertCommasCheckBox.setText("commas,");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        add(insertCommasCheckBox, gridBagConstraints);

        insertLineBreaksCheckBox.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        insertLineBreaksCheckBox.setText("line feeds");
        insertLineBreaksCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertLineBreaksCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 6);
        add(insertLineBreaksCheckBox, gridBagConstraints);

        expectedProfileLabel.setText("expected profile");
        expectedProfileLabel.setToolTipText("Expected Inline model profile ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(expectedProfileLabel, gridBagConstraints);

        expectedProfileComboBox.setEditable(true);
        expectedProfileComboBox.setModel(new DefaultComboBoxModel<>(X3D_ATTR_PROFILE_OPTIONS));
        expectedProfileComboBox.setToolTipText("Expected Inline scene profile ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(expectedProfileComboBox, gridBagConstraints);

        expectedProfileNoteLabel.setText("(inserts MetadataString child for expected profile of InlineGeometry model)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(expectedProfileNoteLabel, gridBagConstraints);

        nodeHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nodeHintPanel.setLayout(new java.awt.GridBagLayout());

        descriptionLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        descriptionLabel.setText("<html>  <p align='center'><b>InlineGeometry</b> has a parent <b>Shape</b> node and can load content from an STL file, a PLY file, <br />       \nor another X3D scene via the url list of local and online file addresses, sorted by availability. </p>\n<p align='center'> Append #DEFname when loading geometry from an X3D or VRML file (otherwise use first Shape found). </p>\n <p align='center'>Hint: multiple links usually provide alternate addresses or encodings for the same content.</p>  \n<p align='center'>Hint: Schematron warnings regarding X3D profile are silenced by inserting a MetadataString node.</p> ");
        descriptionLabel.setToolTipText("InlineGeometry has a parent Shape node and can load geometry from STL files, PLY files, or other X3D scenes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 10, 3);
        nodeHintPanel.add(descriptionLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nodeHintPanel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.ipadx = 4;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(spacerLabel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void insertLineBreaksCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertLineBreaksCheckBoxActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_insertLineBreaksCheckBoxActionPerformed

    private void urlListKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_urlListKeyReleased
    {//GEN-HEADEREND:event_urlListKeyReleased
        setDefaultDEFname ();
    }//GEN-LAST:event_urlListKeyReleased

    private void urlListPropertyChange(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_urlListPropertyChange
    {//GEN-HEADEREND:event_urlListPropertyChange
        setDefaultDEFname ();
    }//GEN-LAST:event_urlListPropertyChange

    private void descriptionTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_descriptionTFActionPerformed
        // X3D4.1 node
    }//GEN-LAST:event_descriptionTFActionPerformed
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel appendLabel;
    private org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel1;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JLabel descriptionLabel1;
    private javax.swing.JTextField descriptionTF;
    private javax.swing.JComboBox<String> expectedProfileComboBox;
    private javax.swing.JLabel expectedProfileLabel;
    private javax.swing.JLabel expectedProfileNoteLabel;
    private javax.swing.JCheckBox insertCommasCheckBox;
    private javax.swing.JCheckBox insertLineBreaksCheckBox;
    private javax.swing.JCheckBox loadCB;
    private javax.swing.JLabel loadLabel;
    private javax.swing.JPanel nodeHintPanel;
    private javax.swing.JLabel spacerLabel;
    private javax.swing.JLabel urlLabel;
    private org.web3d.x3d.palette.items.UrlExpandableList2 urlList;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_INLINEGEOMETRY";
  }

  @Override
  public void unloadInput()
  {
    unLoadDEFUSE();
    urlList.checkUrlValues();
    // TODO check X3D version 4.0+ for X3D/VRML file extensions urls, Networking component level 4 as well
    
    if (!descriptionTF.getText().isBlank())
    {
        inlineGeometry.setDescription(descriptionTF.getText().trim());
    }
    
    inlineGeometry.setLoad(loadCB.isSelected());
    inlineGeometry.setUrls(urlList.getUrlData());

    inlineGeometry.setInsertCommas    (   insertCommasCheckBox.isSelected());
    inlineGeometry.setInsertLineBreaks(insertLineBreaksCheckBox.isSelected());
    if (expectedProfileComboBox.getSelectedIndex() > 0)
    {
        inlineGeometry.setExpectedProfile((String)expectedProfileComboBox.getSelectedItem());
        String hintComment =   "\n        " + INLINEGEOMETRY_PROFILE_METADATA_PREFIX + inlineGeometry.getExpectedProfile() + "\"'/>";

        if (originalContent.trim().startsWith(INLINEGEOMETRY_PROFILE_METADATA_PREFIX)) // omit previous hint comment
        {
            originalContent = originalContent.substring(  originalContent.indexOf("/>") + 2); // following content
        }
        if (originalContent.trim().length() > 0)
             hintComment += originalContent;
        else hintComment += "\n";
        inlineGeometry.setContent(hintComment);
    }
  }   
}
