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

import javax.swing.text.JTextComponent;
import org.openide.util.HelpCtx;
import org.web3d.x3d.X3DDataObject;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * RENDEREDTEXTURECustomizer.java
 * 
 * @author Don Brutzman
 * @version $Id$
 */
public class RENDEREDTEXTURECustomizer extends BaseCustomizer
{
  private RENDEREDTEXTURE renderedTexture;
  private JTextComponent target;
  private X3DDataObject xObj;
  
  public RENDEREDTEXTURECustomizer(RENDEREDTEXTURE renderedTexture, JTextComponent target, X3DDataObject xObj)
  {
    super(renderedTexture);
    this.renderedTexture = renderedTexture;
    this.target = target;
    this.xObj = xObj;
    
    HelpCtx.setHelpIDString(this, "RENDEREDTEXTURE_ELEM_HELPID");
    
    initComponents();
    
    super.getDEFUSEpanel().setContainerFieldChoices(
            RENDEREDTEXTURE_ATTR_CONTAINERFIELD_CHOICES,
            RENDEREDTEXTURE_ATTR_CONTAINERFIELD_TOOLTIPS);
    super.getDEFUSEpanel().setContainerField(renderedTexture.getContainerField()); // reset value to match updated JComboBox data model
    // DEFUSEpanel initialization must NOT be repeated or else array of choices will be overwritten
    
         enabledCB.setSelected(renderedTexture.isEnabled());
        depthMapCB.setSelected(renderedTexture.isDepthMap());
    replaceImageCB.setSelected(renderedTexture.isReplaceImage());
    
    // dimensions array
          widthTF.setText(renderedTexture.getWidth());
         heightTF.setText(renderedTexture.getHeight());
    depthComboBox.setSelectedItem(renderedTexture.getNumberOfComponents());
        
maximumNumberFramesTF.setText(renderedTexture.getMaximumNumberFrames());
     updateIntervalTF.setText(renderedTexture.getUpdateInterval());
       updateComboBox.setSelectedItem(renderedTexture.getUpdate());

    repeatSCB.setSelected(renderedTexture.isRepeatS());
    repeatTCB.setSelected(renderedTexture.isRepeatT());
    
    urlList.setMasterDocumentLocation(xObj.getPrimaryFile());
    urlList.setUrlData(renderedTexture.getUrls());
    urlList.setTarget(target); // enable urlList to reach back into jdom tree to getHeaderIdentifierPath()
    urlList.setFileChooserImage();
    urlList.checkUrlValues();

        insertCommasCheckBox.setSelected(renderedTexture.isInsertCommas());
    insertLineBreaksCheckBox.setSelected(renderedTexture.isInsertLineBreaks());

    setDefaultDEFname ();
  }

  private void setDefaultDEFname ()
  {
	if ((urlList == null) || (urlList.getUrlData() == null) || (urlList.getUrlData().length == 0))
	{
		super.getDEFUSEpanel().setDefaultDEFname("New" + "RenderedTexture");
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
        fileName += "RenderedTexture"; // otherwise empty
    
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

        org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel = getDEFUSEpanel();
        enabledLabel = new javax.swing.JLabel();
        enabledCB = new javax.swing.JCheckBox();
        depthMapLabel = new javax.swing.JLabel();
        depthMapCB = new javax.swing.JCheckBox();
        replaceImageLabel = new javax.swing.JLabel();
        replaceImageCB = new javax.swing.JCheckBox();
        repeatSLabel = new javax.swing.JLabel();
        repeatSCB = new javax.swing.JCheckBox();
        repeatTLabel = new javax.swing.JLabel();
        repeatTCB = new javax.swing.JCheckBox();
        appendPanel = new javax.swing.JPanel();
        appendLabel = new javax.swing.JLabel();
        insertCommasCheckBox = new javax.swing.JCheckBox();
        insertLineBreaksCheckBox = new javax.swing.JCheckBox();
        urlLabel = new javax.swing.JLabel();
        urlList = new org.web3d.x3d.palette.items.UrlExpandableList2();
        nodeHintPanel = new javax.swing.JPanel();
        descriptionLabel = new javax.swing.JLabel();
        descriptionLabel1 = new javax.swing.JLabel();
        descriptionTF = new javax.swing.JTextField();
        dimensionsLabel = new javax.swing.JLabel();
        widthLabel = new javax.swing.JLabel();
        widthTF = new javax.swing.JTextField();
        heightLabel = new javax.swing.JLabel();
        heightTF = new javax.swing.JTextField();
        depthLabel = new javax.swing.JLabel();
        depthComboBox = new javax.swing.JComboBox<>();
        updateIntervalLabel = new javax.swing.JLabel();
        updateIntervalTF = new javax.swing.JTextField();
        updateLabel = new javax.swing.JLabel();
        updateComboBox = new javax.swing.JComboBox<>();
        maximumNumberFramesLabel = new javax.swing.JLabel();
        maximumNumberFramesTF = new javax.swing.JTextField();

        setMinimumSize(new java.awt.Dimension(800, 640));
        setPreferredSize(new java.awt.Dimension(800, 640));
        setLayout(new java.awt.GridBagLayout());

        dEFUSEpanel.setMinimumSize(new java.awt.Dimension(198, 77));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel, gridBagConstraints);

        enabledLabel.setText("enabled");
        enabledLabel.setToolTipText("enables/disables node operation");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(enabledLabel, gridBagConstraints);

        enabledCB.setSelected(true);
        enabledCB.setToolTipText("enables/disables node operation");
        enabledCB.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        enabledCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(enabledCB, gridBagConstraints);

        depthMapLabel.setText("depthMap");
        depthMapLabel.setToolTipText("indicates that the generated texture contains a depth buffer for the image, instead of a color buffer");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(depthMapLabel, gridBagConstraints);

        depthMapCB.setToolTipText("indicates that the generated texture contains a depth buffer for the image, instead of a color buffer");
        depthMapCB.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        depthMapCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        depthMapCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                depthMapCBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(depthMapCB, gridBagConstraints);

        replaceImageLabel.setText("replaceImage");
        replaceImageLabel.setToolTipText("whether only a single updated image file or multiple image files can be saved");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(replaceImageLabel, gridBagConstraints);

        replaceImageCB.setSelected(true);
        replaceImageCB.setToolTipText("whether only a single updated image file or multiple image files can be saved");
        replaceImageCB.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        replaceImageCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(replaceImageCB, gridBagConstraints);

        repeatSLabel.setText("repeatS");
        repeatSLabel.setToolTipText("Horizontally repeat texture along S axis using child TextureTransform");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(repeatSLabel, gridBagConstraints);

        repeatSCB.setToolTipText("Horizontally repeat texture along S axis using child TextureTransform");
        repeatSCB.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        repeatSCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        repeatSCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                repeatSCBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(repeatSCB, gridBagConstraints);

        repeatTLabel.setText("repeatT");
        repeatTLabel.setToolTipText("Horizontally repeat texture along T axis using child TextureTransform");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(repeatTLabel, gridBagConstraints);

        repeatTCB.setToolTipText("Horizontally repeat texture along T axis using child TextureTransform");
        repeatTCB.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        repeatTCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        repeatTCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                repeatTCBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(repeatTCB, gridBagConstraints);

        appendPanel.setLayout(new java.awt.GridBagLayout());

        appendLabel.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        appendLabel.setText("append:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        appendPanel.add(appendLabel, gridBagConstraints);

        insertCommasCheckBox.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        insertCommasCheckBox.setText("commas,");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        appendPanel.add(insertCommasCheckBox, gridBagConstraints);

        insertLineBreaksCheckBox.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        insertLineBreaksCheckBox.setText("line feeds");
        insertLineBreaksCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                insertLineBreaksCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        appendPanel.add(insertLineBreaksCheckBox, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(appendPanel, gridBagConstraints);

        urlLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        urlLabel.setText("url");
        urlLabel.setToolTipText("List of image resources, retrieved in order until one is found");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(urlLabel, gridBagConstraints);

        urlList.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        urlList.setPreferredSize(new java.awt.Dimension(640, 120));
        urlList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                urlListMouseReleased(evt);
            }
        });
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
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipady = 60;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(urlList, gridBagConstraints);

        nodeHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nodeHintPanel.setMinimumSize(new java.awt.Dimension(788, 170));
        nodeHintPanel.setPreferredSize(new java.awt.Dimension(788, 170));
        nodeHintPanel.setLayout(new java.awt.GridBagLayout());

        descriptionLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        descriptionLabel.setText("<html>\n<p align='center'><b>RenderedTexture</b> is a texture node that renders the view from a local viewpoint or separate scene into an offscreen buffer,\nproducing an image or depth map that can be rendered from model geometry in real time. \nThe output renderings can optionally be saved via the url field as a local file or a stream source. RenderedTexture</b> enables \neffects such as dynamic reflections, live video screens, or portal views by continuously updating the texture based on the rendered content.</p>\n<br />\n<p align='center'>Like other texture nodes ,<b>RenderedTexture</b> is contained by <b>Appearance</b> to map an image onto peer geometry,\nand  can contain a <b>TextureProperties</b> node.</p>\n<br />\n<p align='center'>\nThe <i>isActive</i> output field sends a TRUE event when data output becomes active, and a FALSE event when data output is stopped.</p>");
        descriptionLabel.setToolTipText("TextureProperties, TextureTransform and TextureCoordinate can further adjust texture application");
        descriptionLabel.setMinimumSize(new java.awt.Dimension(780, 160));
        descriptionLabel.setPreferredSize(new java.awt.Dimension(780, 160));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 10, 3);
        nodeHintPanel.add(descriptionLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nodeHintPanel, gridBagConstraints);

        descriptionLabel1.setText("description");
        descriptionLabel1.setToolTipText("(X3D4) Author-provided prose that describes intended purpose of the node");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
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
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(descriptionTF, gridBagConstraints);

        dimensionsLabel.setText("dimensions");
        dimensionsLabel.setToolTipText("attribute for width, height, and number of color components");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 36);
        add(dimensionsLabel, gridBagConstraints);

        widthLabel.setText("width");
        widthLabel.setToolTipText("width in pixels for the rendered texture");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(widthLabel, gridBagConstraints);

        widthTF.setToolTipText("width in pixels for the rendered texture");
        widthTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                widthTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(widthTF, gridBagConstraints);

        heightLabel.setText("height");
        heightLabel.setToolTipText("height in pixels for the rendered texture");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(heightLabel, gridBagConstraints);

        heightTF.setToolTipText("height in pixels for the rendered texture");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(heightTF, gridBagConstraints);

        depthLabel.setText("depth");
        depthLabel.setToolTipText("number of color components in each pixel value are 1 (intensity), 2 (intensity alpha), 3 (RGB red green blue), 4 (RGBA red green blue alpha-opacity)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(depthLabel, gridBagConstraints);

        depthComboBox.setMaximumRowCount(4);
        depthComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1", "2", "3", "4" }));
        depthComboBox.setToolTipText("number of color components in each pixel value are 1 (intensity), 2 (intensity alpha), 3 (RGB red green blue), 4 (RGBA red green blue alpha-opacity)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(depthComboBox, gridBagConstraints);

        updateIntervalLabel.setText("updateInterval");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(updateIntervalLabel, gridBagConstraints);

        updateIntervalTF.setToolTipText("height in pixels for the rendered texture");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(updateIntervalTF, gridBagConstraints);

        updateLabel.setText("update");
        updateLabel.setToolTipText("number of color components in each pixel value are 1 (intensity), 2 (intensity alpha), 3 (RGB red green blue), 4 (RGBA red green blue alpha-opacity)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_END;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(updateLabel, gridBagConstraints);

        updateComboBox.setMaximumRowCount(3);
        updateComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "NONE", "NEXT_FRAME_ONLY", "ALWAYS" }));
        updateComboBox.setToolTipText("number of color components in each pixel value are 1 (intensity), 2 (intensity alpha), 3 (RGB red green blue), 4 (RGBA red green blue alpha-opacity)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(updateComboBox, gridBagConstraints);

        maximumNumberFramesLabel.setText("maximumNumberFrames");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(3, 10, 3, 3);
        add(maximumNumberFramesLabel, gridBagConstraints);

        maximumNumberFramesTF.setToolTipText("height in pixels for the rendered texture");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(maximumNumberFramesTF, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void insertLineBreaksCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_insertLineBreaksCheckBoxActionPerformed
        // TODO add your handling code here:
}//GEN-LAST:event_insertLineBreaksCheckBoxActionPerformed

    private void repeatSCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_repeatSCBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_repeatSCBActionPerformed

    private void urlListKeyReleased(java.awt.event.KeyEvent evt)//GEN-FIRST:event_urlListKeyReleased
    {//GEN-HEADEREND:event_urlListKeyReleased
        setDefaultDEFname ();
    }//GEN-LAST:event_urlListKeyReleased

    private void urlListMouseReleased(java.awt.event.MouseEvent evt)//GEN-FIRST:event_urlListMouseReleased
    {//GEN-HEADEREND:event_urlListMouseReleased
        setDefaultDEFname ();
    }//GEN-LAST:event_urlListMouseReleased

    private void urlListPropertyChange(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_urlListPropertyChange
    {//GEN-HEADEREND:event_urlListPropertyChange
        setDefaultDEFname ();
    }//GEN-LAST:event_urlListPropertyChange

    private void descriptionTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_descriptionTFActionPerformed
    {//GEN-HEADEREND:event_descriptionTFActionPerformed
        checkX3D4FieldSupportDialog("RenderedTexture","description"); // X3D4.0 field
    }//GEN-LAST:event_descriptionTFActionPerformed

    private void depthMapCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_depthMapCBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_depthMapCBActionPerformed

    private void widthTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_widthTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_widthTFActionPerformed

    private void repeatTCBActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_repeatTCBActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_repeatTCBActionPerformed
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel appendLabel;
    private javax.swing.JPanel appendPanel;
    private javax.swing.JComboBox<String> depthComboBox;
    private javax.swing.JLabel depthLabel;
    private javax.swing.JCheckBox depthMapCB;
    private javax.swing.JLabel depthMapLabel;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JLabel descriptionLabel1;
    private javax.swing.JTextField descriptionTF;
    private javax.swing.JLabel dimensionsLabel;
    private javax.swing.JCheckBox enabledCB;
    private javax.swing.JLabel enabledLabel;
    private javax.swing.JLabel heightLabel;
    private javax.swing.JTextField heightTF;
    private javax.swing.JCheckBox insertCommasCheckBox;
    private javax.swing.JCheckBox insertLineBreaksCheckBox;
    private javax.swing.JLabel maximumNumberFramesLabel;
    private javax.swing.JTextField maximumNumberFramesTF;
    private javax.swing.JPanel nodeHintPanel;
    private javax.swing.JCheckBox repeatSCB;
    private javax.swing.JLabel repeatSLabel;
    private javax.swing.JCheckBox repeatTCB;
    private javax.swing.JLabel repeatTLabel;
    private javax.swing.JCheckBox replaceImageCB;
    private javax.swing.JLabel replaceImageLabel;
    private javax.swing.JComboBox<String> updateComboBox;
    private javax.swing.JLabel updateIntervalLabel;
    private javax.swing.JTextField updateIntervalTF;
    private javax.swing.JLabel updateLabel;
    private javax.swing.JLabel urlLabel;
    private org.web3d.x3d.palette.items.UrlExpandableList2 urlList;
    private javax.swing.JLabel widthLabel;
    private javax.swing.JTextField widthTF;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_RENDEREDTEXTURE";
  }

  @Override
  public void unloadInput() throws IllegalArgumentException
  {
    unLoadDEFUSE();
    urlList.checkUrlValues();
    
    renderedTexture.setEnabled     (enabledCB.isSelected());
    renderedTexture.setDepthMap    (depthMapCB.isSelected());
    renderedTexture.setReplaceImage(enabledCB.isSelected());
    
    renderedTexture.setDescription(descriptionTF.getText().trim());
    
    // dimensions array
    renderedTexture.setWidth ( widthTF.getText().trim());
    renderedTexture.setHeight(heightTF.getText().trim());
    renderedTexture.setNumberOfComponents ( depthComboBox.getSelectedItem().toString());
    
    renderedTexture.setUpdate        (updateComboBox.getSelectedItem().toString());
    renderedTexture.setUpdateInterval(updateIntervalTF.getText().trim());
    
    renderedTexture.setRepeatS(repeatSCB.isSelected());
    renderedTexture.setRepeatT(repeatTCB.isSelected());

    renderedTexture.setUrls   (urlList.getUrlData());
    renderedTexture.setInsertCommas    (   insertCommasCheckBox.isSelected());
    renderedTexture.setInsertLineBreaks(insertLineBreaksCheckBox.isSelected());
  }  
}
