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

import javax.swing.DefaultComboBoxModel;
import javax.swing.text.JTextComponent;
import org.openide.util.HelpCtx;
import org.web3d.x3d.X3DDataObject;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * INLINECustomizer.java
 * Created on SEP 26, 2007 11:40 AM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class INLINECustomizer extends BaseCustomizer
{
  private final INLINE inline;
  private final JTextComponent target;
  private final X3DDataObject x3DDataObject;
  private String   originalContent = "";
  private final String INLINE_PROFILE_METADATA_PREFIX = "<MetadataString name='profile' value='\"";
  
  /** Creates new form INLINECustomizer */
  public INLINECustomizer(INLINE inline, JTextComponent target, X3DDataObject x3DDataObject)
  {
    super(inline);
    this.inline = inline;
    this.target = target;
    this.x3DDataObject = x3DDataObject;
    
    HelpCtx.setHelpIDString(INLINECustomizer.this, "INLINE_ELEM_HELPID");

    inline.setVisualizationSelectionAvailable(true); // must precede initComponents() interface initialization
    inline.setVisualizationTooltip("Add wireframe Box and axes to show boundingBox center and size (if defined)");
    
    initComponents();
    
    // can be the proxy field of a Collision node
    super.getDEFUSEpanel().setContainerFieldChoices(GROUP_CONTAINERFIELD_CHOICES, GROUP_CONTAINERFIELD_TOOLTIPS);
    // DEFUSEpanel initialization must NOT be repeated or else array of choices will be overwritten
    
    urlList.setMasterDocumentLocation(x3DDataObject.getPrimaryFile());
    urlList.setUrlData(inline.getUrls());
    urlList.setTarget(target); // enable urlList to reach back into jdom tree to getHeaderIdentifierPath()
    urlList.setFileChooserX3d();
    urlList.checkUrlValues();
    
    loadCB.setSelected(inline.isLoad());
    bboxCenterXTF.setText(inline.getBboxCenterX());
    bboxCenterYTF.setText(inline.getBboxCenterY());
    bboxCenterZTF.setText(inline.getBboxCenterZ());
    bboxSizeXTF.setText(inline.getBboxSizeX());
    bboxSizeYTF.setText(inline.getBboxSizeY());
    bboxSizeZTF.setText(inline.getBboxSizeZ());

        insertCommasCheckBox.setSelected(inline.isInsertCommas());
    insertLineBreaksCheckBox.setSelected(inline.isInsertLineBreaks());

    checkVisualizable ();

    setDefaultDEFname ();
    
    originalContent = inline.getContent();
    if (originalContent.trim().isEmpty())
        originalContent = "";
    
    String expectedProfile = inline.getExpectedProfile(); // utilize previous hint comment
    if      (expectedProfile.equals("Immersive"))
         expectedProfileComboBox.setSelectedItem("Immersive");
    else if (expectedProfile.equals("Interchange"))
         expectedProfileComboBox.setSelectedItem("Interchange");
    else if (expectedProfile.equals("Interactive"))
         expectedProfileComboBox.setSelectedItem("Interactive");
    else if (expectedProfile.equals("CADInteractive"))
         expectedProfileComboBox.setSelectedItem("Interactive");
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
		super.getDEFUSEpanel().setDefaultDEFname("New" + "Inline");
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
        fileName += "Inline"; // otherwise empty
    
    super.getDEFUSEpanel().setDefaultDEFname(fileName);
  }

  private void checkVisualizable ()
  {
    enableAppendVisualizationCB(!(bboxSizeXTF.getText().equals("-1") && bboxSizeYTF.getText().equals("-1") && bboxSizeZTF.getText().equals("-1")));
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
        bboxCenterLabel = new javax.swing.JLabel();
        bboxCenterXTF = new javax.swing.JTextField();
        bboxCenterYTF = new javax.swing.JTextField();
        bboxCenterZTF = new javax.swing.JTextField();
        bboxSizeLabel = new javax.swing.JLabel();
        bboxSizeXTF = new javax.swing.JTextField();
        bboxSizeYTF = new javax.swing.JTextField();
        bboxSizeZTF = new javax.swing.JTextField();
        loadLab = new javax.swing.JLabel();
        loadCB = new javax.swing.JCheckBox();
        urlLab = new javax.swing.JLabel();
        urlList = new org.web3d.x3d.palette.items.UrlExpandableList2();
        appendLabel = new javax.swing.JLabel();
        insertCommasCheckBox = new javax.swing.JCheckBox();
        insertLineBreaksCheckBox = new javax.swing.JCheckBox();
        nodeHintPanel = new javax.swing.JPanel();
        descriptionLabel = new javax.swing.JLabel();
        expectedProfileLabel = new javax.swing.JLabel();
        expectedProfileComboBox = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();

        setMinimumSize(new java.awt.Dimension(800, 400));
        setPreferredSize(new java.awt.Dimension(800, 400));
        setLayout(new java.awt.GridBagLayout());

        dEFUSEpanel1.setMinimumSize(new java.awt.Dimension(67, 67));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel1, gridBagConstraints);

        bboxCenterLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        bboxCenterLabel.setText("bboxCenter");
        bboxCenterLabel.setToolTipText("position offset from origin of local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterLabel, gridBagConstraints);

        bboxCenterXTF.setToolTipText("position offset from origin of local coordinate system");
        bboxCenterXTF.setMaximumSize(new java.awt.Dimension(6, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterXTF, gridBagConstraints);

        bboxCenterYTF.setToolTipText("position offset from origin of local coordinate system");
        bboxCenterYTF.setMaximumSize(new java.awt.Dimension(6, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterYTF, gridBagConstraints);

        bboxCenterZTF.setToolTipText("position offset from origin of local coordinate system");
        bboxCenterZTF.setMaximumSize(new java.awt.Dimension(6, 30));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterZTF, gridBagConstraints);

        bboxSizeLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        bboxSizeLabel.setText("bboxSize");
        bboxSizeLabel.setToolTipText("automatically calculated, can be specified as an optimization or constraint");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxSizeLabel, gridBagConstraints);

        bboxSizeXTF.setToolTipText("automatically calculated, can be specified as an optimization or constraint");
        bboxSizeXTF.setMaximumSize(new java.awt.Dimension(6, 30));
        bboxSizeXTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bboxSizeXTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxSizeXTF, gridBagConstraints);

        bboxSizeYTF.setToolTipText("automatically calculated, can be specified as an optimization or constraint");
        bboxSizeYTF.setMaximumSize(new java.awt.Dimension(6, 30));
        bboxSizeYTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bboxSizeYTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxSizeYTF, gridBagConstraints);

        bboxSizeZTF.setToolTipText("automatically calculated, can be specified as an optimization or constraint");
        bboxSizeZTF.setMaximumSize(new java.awt.Dimension(6, 30));
        bboxSizeZTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bboxSizeZTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxSizeZTF, gridBagConstraints);

        loadLab.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        loadLab.setText("load");
        loadLab.setToolTipText("load=true means load immediately, load=false means defer loading or unload contained scene");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(loadLab, gridBagConstraints);

        loadCB.setSelected(true);
        loadCB.setToolTipText("load=true means load immediately, load=false means defer loading or unload contained scene");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(loadCB, gridBagConstraints);

        urlLab.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        urlLab.setText("url");
        urlLab.setToolTipText("Address of X3D world to load into current scene");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(urlLab, gridBagConstraints);

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
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(urlList, gridBagConstraints);

        appendLabel.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        appendLabel.setText("append:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        add(appendLabel, gridBagConstraints);

        insertCommasCheckBox.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        insertCommasCheckBox.setText("commas,");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        add(insertCommasCheckBox, gridBagConstraints);

        insertLineBreaksCheckBox.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        insertLineBreaksCheckBox.setText("line feeds");
        insertLineBreaksCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertLineBreaksCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        add(insertLineBreaksCheckBox, gridBagConstraints);

        nodeHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nodeHintPanel.setLayout(new java.awt.GridBagLayout());

        descriptionLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        descriptionLabel.setText("<html><p align='center'><b>Inline</b> can load content from another X3D scene via the url list of local and online file addresses, sorted by availability. </p> \n<p align='center'>Hint: multiple links usually provide alternate addresses or encodings for the same content.</p>\n<p align='center'>Hint: Schematron warnings for X3D profile are silenced by inserting a MetadataString node.</p>");
        descriptionLabel.setToolTipText("Inline can load other X3D scenes");
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
        gridBagConstraints.gridwidth = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nodeHintPanel, gridBagConstraints);

        expectedProfileLabel.setText("expected X3D profile");
        expectedProfileLabel.setToolTipText("Expected Inline scene profile ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(expectedProfileLabel, gridBagConstraints);

        expectedProfileComboBox.setEditable(true);
        expectedProfileComboBox.setModel(new DefaultComboBoxModel<>(X3D_ATTR_PROFILE_OPTIONS));
        expectedProfileComboBox.setToolTipText("Expected Inline scene profile ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 2.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(expectedProfileComboBox, gridBagConstraints);

        jLabel1.setText("(inserts MetadataString for expected profile)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(jLabel1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void bboxSizeXTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bboxSizeXTFActionPerformed
        checkVisualizable ();
    }//GEN-LAST:event_bboxSizeXTFActionPerformed

    private void bboxSizeYTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bboxSizeYTFActionPerformed
        checkVisualizable ();
    }//GEN-LAST:event_bboxSizeYTFActionPerformed

    private void bboxSizeZTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bboxSizeZTFActionPerformed
        checkVisualizable ();
    }//GEN-LAST:event_bboxSizeZTFActionPerformed

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
  
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel appendLabel;
    private javax.swing.JLabel bboxCenterLabel;
    private javax.swing.JTextField bboxCenterXTF;
    private javax.swing.JTextField bboxCenterYTF;
    private javax.swing.JTextField bboxCenterZTF;
    private javax.swing.JLabel bboxSizeLabel;
    private javax.swing.JTextField bboxSizeXTF;
    private javax.swing.JTextField bboxSizeYTF;
    private javax.swing.JTextField bboxSizeZTF;
    private org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel1;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JComboBox<String> expectedProfileComboBox;
    private javax.swing.JLabel expectedProfileLabel;
    private javax.swing.JCheckBox insertCommasCheckBox;
    private javax.swing.JCheckBox insertLineBreaksCheckBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JCheckBox loadCB;
    private javax.swing.JLabel loadLab;
    private javax.swing.JPanel nodeHintPanel;
    private javax.swing.JLabel urlLab;
    private org.web3d.x3d.palette.items.UrlExpandableList2 urlList;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_INLINE";
  }

  @Override
  public void unloadInput()
  {
    unLoadDEFUSE();
    urlList.checkUrlValues();
    
    inline.setLoad(loadCB.isSelected());
    inline.setUrls(urlList.getUrlData());
    inline.setBboxCenterX(bboxCenterXTF.getText().trim());
    inline.setBboxCenterY(bboxCenterYTF.getText().trim());
    inline.setBboxCenterZ(bboxCenterZTF.getText().trim());
    inline.setBboxSizeX(bboxSizeXTF.getText().trim());
    inline.setBboxSizeY(bboxSizeYTF.getText().trim());
    inline.setBboxSizeZ(bboxSizeZTF.getText().trim());

    inline.setInsertCommas    (   insertCommasCheckBox.isSelected());
    inline.setInsertLineBreaks(insertLineBreaksCheckBox.isSelected());
    if (expectedProfileComboBox.getSelectedIndex() > 0)
    {
        inline.setExpectedProfile((String)expectedProfileComboBox.getSelectedItem());
        String hintComment =   "\n        " + INLINE_PROFILE_METADATA_PREFIX + inline.getExpectedProfile() + "\"'/>";

        if (originalContent.trim().startsWith(INLINE_PROFILE_METADATA_PREFIX)) // omit previous hint comment
        {
            originalContent = originalContent.substring(  originalContent.indexOf("/>") + 2); // following content
        }
        if (originalContent.trim().length() > 0)
             hintComment += originalContent;
        else hintComment += "\n";
        inline.setContent(hintComment);
    }
  }   
}
