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

import java.util.Vector;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;
import org.openide.util.HelpCtx;
import org.web3d.x3d.X3DDataObject;
import static org.web3d.x3d.types.X3DSchemaData.GROUP_CONTAINERFIELD_CHOICES;
import static org.web3d.x3d.types.X3DSchemaData.GROUP_CONTAINERFIELD_TOOLTIPS;

/**
 * ANCHORCustomizer.java
 * Created on March 14, 2007, 9:57 AM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class ANCHORCustomizer extends BaseCustomizer
{
  private ANCHOR anchor;
  private JTextComponent target;
  private X3DDataObject xObj;
  
  /** Creates new form ANCHORCustomizer */
  public ANCHORCustomizer(ANCHOR anchor, JTextComponent target, X3DDataObject xObj)
  {
    super(anchor);
    this.anchor = anchor;
    this.target = target;
    this.xObj = xObj;
    
    HelpCtx.setHelpIDString(this, "ANCHOR_ELEM_HELPID");

    anchor.setVisualizationSelectionAvailable(true); // must precede initComponents() interface initialization
    anchor.setVisualizationTooltip("Add wireframe Box and axes to show boundingBox center and size (if defined)");
    
    initComponents();
    
    // can be the proxy field of a Collision node
    super.getDEFUSEpanel().setContainerFieldChoices(GROUP_CONTAINERFIELD_CHOICES, GROUP_CONTAINERFIELD_TOOLTIPS);
    // DEFUSEpanel initialization must NOT be repeated or else array of choices will be overwritten
    
    descriptionTA.setText(anchor.getDescription());

    urlList.setMasterDocumentLocation(xObj.getPrimaryFile());
    String[] urlArray = anchor.getUrls();
    urlList.setUrlData(urlArray);
    urlList.setTarget(target); // enable urlList to reach back into jdom tree to getHeaderIdentifierPath()
    urlList.setAlternateFormatsExpected(true);
    urlList.checkUrlValues();
    
    if   ((urlArray == null) || (urlArray.length == 0))
        urlList.setFileChooserAll();
    else
    {
        urlList.setFileChooserAll(); // initialize
        // set file chooser based on first recognized format
        for (int i = 0; i < urlArray.length; i++)
        {
            String fileExtension = urlArray[i].substring(urlArray[i].lastIndexOf("."));
            
            if (urlList.selectFileChooserFilter(fileExtension))
                break;
        }
    }
    
    inititializeParameterTable();
    
    bboxCenterXTF.setText(anchor.getBboxCenterX());
    bboxCenterYTF.setText(anchor.getBboxCenterY());
    bboxCenterZTF.setText(anchor.getBboxCenterZ());
      bboxSizeXTF.setText(anchor.getBboxSizeX());
      bboxSizeYTF.setText(anchor.getBboxSizeY());    
      bboxSizeZTF.setText(anchor.getBboxSizeZ());

    checkVisualizable ();

    setDefaultDEFname ();
  }
  private void setDefaultDEFname ()
  {
    // extract file name (minus extension) as candidate DEF name
    String fileName = new String();
	if ((urlList != null) && (urlList.getUrlData() != null) && (urlList.getUrlData().length > 0))
	{
			fileName = urlList.getUrlData()[0];
		if (fileName.contains("/"))
			fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
		if (fileName.contains("\\"))
			fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
		if (fileName.contains("."))
			fileName = fileName.substring(0,fileName.lastIndexOf("."));
		if (fileName.length() > 0)
			fileName += "Anchor"; // otherwise empty
	}
	else fileName = "NewAnchor";
	
    super.getDEFUSEpanel().setDefaultDEFname(fileName);
  }

    private void checkVisualizable ()
    {
      enableAppendVisualizationCB(!(bboxSizeXTF.getText().equals("-1") && bboxSizeYTF.getText().equals("-1") && bboxSizeZTF.getText().equals("-1")));
    }
    private void inititializeParameterTable()
    {
//      parameterTable.setTitle("parameter array"); // title provided in left margin
//      parameterTable.setHeaderTooltip (                     "Target parameters for web browser on launch (e.g. target=blank redirects url loading to new page)");
        
        parameterTable.setColumnTitles  (new String[]{"#",    "Target parameters for web browser"});
        parameterTable.setColumnToolTips(new String[]{"index","Target parameters for web browser on launch (e.g. target=blank redirects url loading to new page)"});
        parameterTable.setNewRowData(new String[]{"target=blank"});
        parameterTable.getTable().setRowHeight(16);
        parameterTable.doIndexInFirstColumn(true);
        parameterTable.setFlippableRowData(true);
        parameterTable.setDataStringBased(true);
        parameterTable.setShowAppendCommasLineBreaks(false);
        parameterTable.setRedColumn(-1); // 0 is index, -1 means no color editing
        
//      parameterTable.setEditCellPanelsVisible(false);
//      parameterTable.setKeyColumnIncluded(false);
//      parameterTable.doTrailingTextEditButton(true); // TODO test
        
        // properly escape characters in each parameter string before setting values into table
        String inps = anchor.getParameterString();
        String[] sa = new String[100];
        int index = 0;
        StringBuilder newString = new StringBuilder();

        if (inps != null && inps.length() > 0)
        {
            boolean betweenStrings = true;
            if (!inps.contains("\""))
            {
                sa[index] = inps; // single un"quoted" string only
            }
            else
            {
                for (int i = 0; i < inps.length(); i++)
                {
                    while (betweenStrings && (inps.substring(i, i+1).trim().length() == 0) && (i < inps.length() - 1))
                    {
                        i++;  // skip whitespace until ready to start next SFString
                    }
                    betweenStrings = false;
                    if (inps.charAt(i) == '\"')// found opening quote
                    {
                        if ((i < inps.length() - 1)) i++; // skip this delimiting character, not part of SFString
                    }
                    else
                    {
                        System.out.println("new string started illegally, character index " + i);
                    }

                    while (i < inps.length())
                    {
                        // inside SFString, look for termination
                        if ((inps.length() - i >= 2) && inps.substring(i, i + 2).equalsIgnoreCase("\\\"")) // skip escaped \"
                        {
                            newString.append(inps.substring(i, i + 2)); // found escaped quote within SFString, save both characters and keep building this string
                            i = i + 2; // increment index, account for second character of two
                        }
                        else if (inps.charAt(i) == '\"') // found terminating ", done with this SFString
                        {
                            betweenStrings = true;
                            sa[index] = newString.toString(); // save this SFString, ignore delimiter " character
                            index++;
                            newString = new StringBuilder(); // reset for next loop
                            break;
                        }
                        else
                        {
                            newString.append(inps.charAt(i)); // keep building this string
                             i++; // increment index
                        }
                    }
                    if ((i == inps.length() - 1) && (newString.length() > 0))
                    {
                        System.out.println("encountered unterminated SFString, mismatched quotes: " + inps);
                        sa[index] = newString.toString(); // save this SFString fragment anyway
                    }
                }
            }
        }

        Vector<String> v = new Vector<>();
        for (String s : sa)
        {
            if (s==null) break; // done

            StringBuilder escapedString = new StringBuilder();
            for (int c = 0; c < s.length(); c++)
            {
                // &amp; and &apos; and &quot; are already converted back to & and ' by XML parser

                if (((s.length() - c) >= 2) && s.substring(c).startsWith("\\\\"))
                {
                    escapedString.append('\\');         // replace double backslash with single backslash
                    c = c + 1;
                }
                else
                {
                    if (((s.length() - c) >= 2) && s.substring(c).startsWith("\\\""))
                    {
                        escapedString.append('"');         // replace \" with "
                        c = c + 1;
                    }
                    else
                    {
                        escapedString.append(s.charAt(c)); // OK as is
                    }
                }
            }
            v.add(escapedString.toString());
        }
        if (v.size() > 0)
        {
          Object[][] oa = new Object[v.size()][1];
          int i=0;
          for(String s : v) {
            oa[i] = new String[]{s};
            i++;
          }
          parameterTable.setData(oa);
        }
    }

  String buildParameterAttribute()
  {
    JTable table = parameterTable.getTable();
    DefaultTableModel mod = (DefaultTableModel)table.getModel();
    StringBuilder sb = new StringBuilder();
    int nrows = mod.getRowCount();
    for(int r=0;r<nrows;r++) {
      String value = ((String)mod.getValueAt(r, 1)).trim();
      if((value.length()<=0))
        continue;
      StringBuilder escapedValue = new StringBuilder();
      for (int c=0;c<value.length();c++)
      {
          if      (value.charAt(c) == '"')
          {
               escapedValue.append('\\');            // replace " with \" for X3D escaping
               escapedValue.append('\"');            // TODO use \" instead of &quot; when reload works
          }
          else if (value.charAt(c) == '&')
               escapedValue.append("&amp;");         // replace & with &amp;
          else if (value.charAt(c) == '\'')
               escapedValue.append("&apos;");         // replace ' with &apos;
          else if (value.charAt(c) == '\\')
               escapedValue.append("\\\\");           // replace \ with \\
          else if (Character.getNumericValue(value.charAt(c)) > 127) // TODO fix
                escapedValue.append("&#").append(Character.getNumericValue(value.charAt(c))).append(";");  // replace special characters with XML character entity
          else escapedValue.append(value.charAt(c)); // otherwise no change
      }
      sb.append('"');
      sb.append(escapedValue);
      sb.append("\" ");
    }
    return sb.toString().trim();
  }
  
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jPanel2 = new javax.swing.JPanel();
        hintLabel = new javax.swing.JLabel();
        org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel1 = getDEFUSEpanel();
        descriptionLabel = new javax.swing.JLabel();
        descriptionScrollPane = new javax.swing.JScrollPane();
        descriptionTA = new javax.swing.JTextArea();
        urlLabel = new javax.swing.JLabel();
        urlList = new org.web3d.x3d.palette.items.UrlExpandableList2();
        parameterLabel = new javax.swing.JLabel();
        parameterTable = new org.web3d.x3d.palette.items.ExpandableList();
        bboxCenterLabel = new javax.swing.JLabel();
        bboxCenterXTF = new javax.swing.JTextField();
        bboxCenterYTF = new javax.swing.JTextField();
        bboxCenterZTF = new javax.swing.JTextField();
        bboxSizeLabel = new javax.swing.JLabel();
        bboxSizeXTF = new javax.swing.JTextField();
        bboxSizeYTF = new javax.swing.JTextField();
        bboxSizeZTF = new javax.swing.JTextField();
        childNodeHintPanel = new javax.swing.JPanel();
        nodeHintPanel = new javax.swing.JPanel();
        hintLabel1 = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        hintLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        hintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel.setText("Anchor is a Grouping node that can contain most nodes");
        hintLabel.setToolTipText("close this panel to add children nodes");
        hintLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        setMinimumSize(new java.awt.Dimension(750, 400));
        setPreferredSize(new java.awt.Dimension(750, 550));
        setLayout(new java.awt.GridBagLayout());

        dEFUSEpanel1.setMinimumSize(new java.awt.Dimension(198, 77));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel1, gridBagConstraints);

        descriptionLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        descriptionLabel.setText("description");
        descriptionLabel.setToolTipText("Text description to be displayed for action of this node");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(descriptionLabel, gridBagConstraints);

        descriptionTA.setLineWrap(true);
        descriptionTA.setRows(1);
        descriptionTA.setToolTipText("Text description to be displayed for action of this node");
        descriptionScrollPane.setViewportView(descriptionTA);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(descriptionScrollPane, gridBagConstraints);

        urlLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        urlLabel.setText("url");
        urlLabel.setToolTipText("Address of replacement world or #ViewpointDEFName, activated by clicking Anchor geometry");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(urlLabel, gridBagConstraints);

        urlList.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        urlList.setMinimumSize(new java.awt.Dimension(536, 120));
        urlList.setPreferredSize(new java.awt.Dimension(536, 180));
        urlList.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                urlListPropertyChange(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(urlList, gridBagConstraints);

        parameterLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        parameterLabel.setText("parameter");
        parameterLabel.setToolTipText("parameters to web browser, e.g. redirect url loading target=blank");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(parameterLabel, gridBagConstraints);

        parameterTable.setToolTipText("parameter strings passed to web browser (e.g. target=_blank)");
        parameterTable.setColumnTitles(new String[] {});
        parameterTable.setColumnToolTips(new String[] {});
        parameterTable.setMinimumSize(new java.awt.Dimension(536, 100));
        parameterTable.setPreferredSize(new java.awt.Dimension(536, 100));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(parameterTable, gridBagConstraints);

        bboxCenterLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        bboxCenterLabel.setText("bboxCenter");
        bboxCenterLabel.setToolTipText("position offset from origin of local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterLabel, gridBagConstraints);

        bboxCenterXTF.setToolTipText("position offset from origin of local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterXTF, gridBagConstraints);

        bboxCenterYTF.setToolTipText("position offset from origin of local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterYTF, gridBagConstraints);

        bboxCenterZTF.setToolTipText("position offset from origin of local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxCenterZTF, gridBagConstraints);

        bboxSizeLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        bboxSizeLabel.setText("bboxSize");
        bboxSizeLabel.setToolTipText("automatically calculated, can be specified as an optimization or constraint");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxSizeLabel, gridBagConstraints);

        bboxSizeXTF.setToolTipText("automatically calculated, can be specified as an optimization or constraint");
        bboxSizeXTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bboxSizeXTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxSizeXTF, gridBagConstraints);

        bboxSizeYTF.setToolTipText("automatically calculated, can be specified as an optimization or constraint");
        bboxSizeYTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bboxSizeYTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxSizeYTF, gridBagConstraints);

        bboxSizeZTF.setToolTipText("automatically calculated, can be specified as an optimization or constraint");
        bboxSizeZTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bboxSizeZTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bboxSizeZTF, gridBagConstraints);

        childNodeHintPanel.setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        add(childNodeHintPanel, gridBagConstraints);

        nodeHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nodeHintPanel.setLayout(new java.awt.GridBagLayout());

        hintLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel1.setText("<html><p align=\"center\"><b>Anchor</b> is a Grouping node that can contain most nodes.</p> <p align=\"center\">User selection of shapes contained by Anchor loads content specified in the url field.</p> <p align=\"center\">Loaded content completely replaces current content, if parameter is same window.</p> <p align=\"center\">Hint: set parameter as target=_blank to load the target url into a new browser frame.</p>");
        hintLabel1.setToolTipText("close this panel to add children nodes");
        hintLabel1.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        nodeHintPanel.add(hintLabel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nodeHintPanel, gridBagConstraints);
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

    private void urlListPropertyChange(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_urlListPropertyChange
    {//GEN-HEADEREND:event_urlListPropertyChange
        setDefaultDEFname ();
    }//GEN-LAST:event_urlListPropertyChange
  
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel bboxCenterLabel;
    private javax.swing.JTextField bboxCenterXTF;
    private javax.swing.JTextField bboxCenterYTF;
    private javax.swing.JTextField bboxCenterZTF;
    private javax.swing.JLabel bboxSizeLabel;
    private javax.swing.JTextField bboxSizeXTF;
    private javax.swing.JTextField bboxSizeYTF;
    private javax.swing.JTextField bboxSizeZTF;
    private javax.swing.JPanel childNodeHintPanel;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JScrollPane descriptionScrollPane;
    private javax.swing.JTextArea descriptionTA;
    private javax.swing.JLabel hintLabel;
    private javax.swing.JLabel hintLabel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel nodeHintPanel;
    private javax.swing.JLabel parameterLabel;
    private org.web3d.x3d.palette.items.ExpandableList parameterTable;
    private javax.swing.JLabel urlLabel;
    private org.web3d.x3d.palette.items.UrlExpandableList2 urlList;
    // End of variables declaration//GEN-END:variables
  @Override
  public String getNameKey()
  {
    return "NAME_X3D_ANCHOR";
  }

  @Override
  public void unloadInput()
  {
     unLoadDEFUSE(); 
     urlList.checkUrlValues();
     
     anchor.setUrls       (urlList.getUrlData());
     anchor.setDescription(descriptionTA.getText().trim());
     anchor.setParameters  (buildParameterAttribute());
     anchor.setBboxCenterX(bboxCenterXTF.getText().trim());
     anchor.setBboxCenterY(bboxCenterYTF.getText().trim());
     anchor.setBboxCenterZ(bboxCenterZTF.getText().trim());
     anchor.setBboxSizeX  (bboxSizeXTF.getText().trim());
     anchor.setBboxSizeY  (bboxSizeYTF.getText().trim());
     anchor.setBboxSizeZ  (bboxSizeZTF.getText().trim());    
  }
  
}
