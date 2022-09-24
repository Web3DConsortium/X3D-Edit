/*
Copyright (c) 1995-2022 held by the author(s) .  All rights reserved.

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

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import java.beans.PropertyChangeEvent;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.*;
import javax.swing.text.JTextComponent;

import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.HelpCtx;

import org.web3d.x3d.xj3d.viewer.Xj3dViewerPanel;

import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * MATERIALCustomizer.java
 * Created on March 14, 2007, 10:09 AM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey and Don Brutzman
 * @version $Id$
 */
public class MATERIALCustomizer extends BaseCustomizer
{
  private final MATERIAL material;

  private String libraryChoice;
  private int    libraryIndex;
  private String message;

  private static final boolean lightOnDefault = true;
  private static final Color   lightColorDefault = new Color(1.0f,1.0f,1.0f);
  private static final float[] lightDirectionDefault = {-0.7071f, 0.0f, -0.7071f};
  private static final float   lightIntensityDefault = 1.0f;
  private static final float   lightAmbientIntensityDefault = 1.0f;
  private static final Color   skyColorDefault = Color.black;

  private final UniversalMediaMaterialFinder universalMediaMaterialFinder;
  private final String DEFname="";
  private final String UNIVERSAL_MEDIA_COMMENT_HEADER1 = "<!-- ";
  private final String UNIVERSAL_MEDIA_COMMENT_HEADER2 = "Universal Media Library: ";

  private final String UNIVERSAL_MEDIA_COMMENT_HEADER_REGEX       = ".*<!--\\s*" + UNIVERSAL_MEDIA_COMMENT_HEADER2 + "\\s*";
  private final String UNIVERSAL_MEDIA_COMMENT_HEADER_FRONT_REGEX = ".*<!--\\s*frontMaterial " + UNIVERSAL_MEDIA_COMMENT_HEADER2 + "\\s*";
  private final String UNIVERSAL_MEDIA_COMMENT_HEADER_BACK_REGEX  = ".*<!--\\s*backMaterial " + UNIVERSAL_MEDIA_COMMENT_HEADER2 + "\\s*";
  private final String UNIVERSAL_MEDIA_COMMENT_TAIL_REGEX = "\\s+(\\d+)\\s+-->.*";

  private final String HEX_COLOR_TOOLTIP = "HTML hexadecimal #rrggbb (RGB float 0..1 = integer 0..255 = hex 00..FF)";
  private       String r, g, b;
  private      SFColor sfColor;

  private final String MEDIA_NONE_ID  = "--none--";
  private final int    MEDIA_NONE_IDX = 0;

  private boolean mediaSliderDisablementLocked = false;

  private JFormattedTextField[] dLightColorTFArray;
  private JFormattedTextField[] diffuseColorTFArray;
  private JFormattedTextField[] specularTFArray;
  private JFormattedTextField[] emissiveTFArray;
  private JFormattedTextField[] skyColorTFArray;

  private final MaterialCustomizerXj3dSupport materialCustomizerXj3dSupport;

  // Make this object a singleton so the xj3d widget doesn't have to be
  // recreated each time.
//  private static MATERIALCustomizer me;
//
//  public static synchronized MATERIALCustomizer getInstance(MATERIAL material, JTextComponent target)
//  {
//    if(me == null)
//      me = new MATERIALCustomizer(material,target);
//    else {
//      me.material = material;
//      me.target = target;
//      me.setDomainObject(material);
//      me.initDEFUSEpanel();
//    }
//    me.initializePanelContent();
//    return me;
//  }
//
  
  /** Constructor
   * @param material the material node to customize
   * @param target required arg, but can be null
   */
  public MATERIALCustomizer(MATERIAL material, JTextComponent target)
  {
    super(material);
    this.material = material;

    HelpCtx.setHelpIDString(MATERIALCustomizer.this, "MATERIAL_ELEM_HELPID");

    initComponents();

      leftSplitPane.setMinimumSize(new Dimension(10,10));
     rightSplitPane.setMinimumSize(new Dimension(10,10));
       leftTopPanel.setMinimumSize(new Dimension(10,10));
    leftBottomPanel.setMinimumSize(new Dimension(10,10));
    rightTopPanel.setMinimumSize(new Dimension(10,10));

    universalMediaMaterialFinder = new UniversalMediaMaterialFinder();

    xj3dViewerPanel.initialize("X3dExamples/MaterialExample.x3d"); // created during component initialization
    materialCustomizerXj3dSupport = new MaterialCustomizerXj3dSupport(xj3dViewerPanel);
    geometryTypeCombo.setSelectedIndex(3); // Sphere is default choice
    checkFixMaterialColorValues();
    initializePanelContent();
  }

  //----------------------------------------------------------
  // Method defined by JComponent
  //----------------------------------------------------------

  @Override
  public void removeNotify()
  {
    // Load the blank scene until something else is required
    xj3dViewerPanel.initialize("Templates/Other/newScene.x3d");
    super.removeNotify();
  }

  /** Check Material for legal color values, fix if needed */
  private void checkFixMaterialColorValues ()
  {
      Float red, green, blue;

      red   = Float.valueOf(material.getDiffuseColor0());
      green = Float.valueOf(material.getDiffuseColor1());
      blue  = Float.valueOf(material.getDiffuseColor2());

      if ((red > 1.0f) || (green > 1.0f) || (blue > 1.0f)) // values > 255 already filtered during MATERIAL SFFloat loading
      {
          message = "<html><p>Large HTML values found for diffuseColor='" + red + " " + green + " " + blue +
                    "'</p><p>Convert from HTML to X3D?";
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  message, "X3D RGB values are [0..1]", NotifyDescriptor.YES_NO_OPTION);
          if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
          {
              red   /= 255.0f;
              green /= 255.0f;
              blue  /= 255.0f;
              material.setDiffuseColor0(  red.toString());
              material.setDiffuseColor1(green.toString());
              material.setDiffuseColor2( blue.toString());
          }
      }

      red   = Float.valueOf(material.getEmissiveColor0());
      green = Float.valueOf(material.getEmissiveColor1());
      blue  = Float.valueOf(material.getEmissiveColor2());

      if ((red > 1.0f) || (green > 1.0f) || (blue > 1.0f)) // values > 255 already filtered during MATERIAL SFFloat loading
      {
          message = "<html><p>Large values found for emissiveColor='" + red + " " + green + " " + blue + "'</p><p>Convert from HTML to X3D?";
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  message, "X3D RGB values are [0..1]", NotifyDescriptor.YES_NO_OPTION);
          if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
          {
              red   /= 255.0f;
              green /= 255.0f;
              blue  /= 255.0f;
              material.setEmissiveColor0(  red.toString());
              material.setEmissiveColor1(green.toString());
              material.setEmissiveColor2( blue.toString());
          }
      }

      red   = Float.valueOf(material.getSpecularColor0());
      green = Float.valueOf(material.getSpecularColor1());
      blue  = Float.valueOf(material.getSpecularColor2());

      if ((red > 1.0f) || (green > 1.0f) || (blue > 1.0f)) // values > 255 already filtered during MATERIAL SFFloat loading
      {
          message = "<html><p>Large values found for specularColor='" + red + " " + green + " " + blue + "'</p><p>Convert from HTML to X3D?";
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  message, "X3D RGB values are [0..1]", NotifyDescriptor.YES_NO_OPTION);
          if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
          {
              red   /= 255.0f;
              green /= 255.0f;
              blue  /= 255.0f;
              material.setSpecularColor0(  red.toString());
              material.setSpecularColor1(green.toString());
              material.setSpecularColor2( blue.toString());
          }
      }
  }

  /** Initialize panel display to match current values */
  private void initializePanelContent()
  {
    String content = material.getContent();  // save around the following
    dLightColorTFArray  = new JFormattedTextField[]{directionalLightColorRedTF,  directionalLightColorGreenTF,  directionalLightColorBlueTF};
    diffuseColorTFArray = new JFormattedTextField[]{diffuseColorRedTF, diffuseColorGreenTF, diffuseColorBlueTF};
    specularTFArray     = new JFormattedTextField[]{specularColorRedTF,specularColorGreenTF,specularColorBlueTF};
    emissiveTFArray     = new JFormattedTextField[]{emissiveColorRedTF,emissiveColorGreenTF,emissiveColorBlueTF};
    skyColorTFArray     = new JFormattedTextField[]{skyColorRedTF,     skyColorGreenTF,     skyColorBlueTF};
    adjustWidgetSizes();
    resetLightingValues();
    resetMaterialValues();
    updateAllXj3dLightingFields();
    updateAllXj3dMaterialFields();

    if (material.isBack())
    {
            diffuseColorLabel.setText("backDiffuseColor");
           emissiveColorLabel.setText("backEmissiveColor");
           specularColorLabel.setText("backSpecularColor");
        ambientIntensityLabel.setText("backAmbientIntensity");
               shininessLabel.setText("backShininess");
            transparencyLabel.setText("backTransparency");
    }

    material.setContent(content);  // restore (gets hit by property change sys) for the following
    initializeUniversalMediaSelection();

          geometrySelectionPanel.setToolTipText("This view panel shows example Material effects");
         directionalLightBackgroundPanel.setToolTipText("This light only affects the Material view panel above, not the X3D scene");
    backgroundLabel.setToolTipText("This background color only affects the Material view panel above, not the X3D scene");
  }

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_MATERIAL";
  }

  @Override
  public void unloadInput() throws IllegalArgumentException
  {
    unLoadDEFUSE();

    material.setAmbientIntensity(nullTo0(ambientIntensityTF));
    material.setDiffuseColor0   (nullTo0(diffuseColorRedTF));
    material.setDiffuseColor1   (nullTo0(diffuseColorGreenTF));
    material.setDiffuseColor2   (nullTo0(diffuseColorBlueTF));
    material.setEmissiveColor0  (nullTo0(emissiveColorRedTF));
    material.setEmissiveColor1  (nullTo0(emissiveColorGreenTF));
    material.setEmissiveColor2  (nullTo0(emissiveColorBlueTF));
    material.setShininess       (nullTo0(shininessTF));
    material.setSpecularColor0  (nullTo0(specularColorRedTF));
    material.setSpecularColor1  (nullTo0(specularColorGreenTF));
    material.setSpecularColor2  (nullTo0(specularColorBlueTF));
    material.setTransparency    (nullTo0(transparencyTF));
  }

  private Xj3dViewerPanel makeXj3dViewerPanel()
  {
        if    (xj3dViewerPanel == null)
               xj3dViewerPanel =  new Xj3dViewerPanel();
        else
        {
            System.err.println ("*** MaterialCustomizer unexpectedly invoked makeXj3dViewerPanel() when xj3dViewerPanel already exists...");
        }
        return xj3dViewerPanel;
  }

  public Xj3dViewerPanel getXj3dViewerPanel()
  {
        if    (xj3dViewerPanel == null)
        {
            makeXj3dViewerPanel();
            System.err.println ("*** MaterialCustomizer invoked getXj3dViewerPanel() when xj3dViewerPanel is null...");
        }
        return xj3dViewerPanel;
  }

  public JComponent extractContent()
  {
    getLayout().removeLayoutComponent(masterSplitPane);
    return masterSplitPane;
  }

  public MATERIAL getMATERIAL()
  {
    return material;
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        dEFUSEpan = getDEFUSEpanel();
        masterSplitPane = new javax.swing.JSplitPane();
        leftSplitPane = new javax.swing.JSplitPane();
        leftTopPanel = new javax.swing.JPanel();
        xj3dViewerPanel = getXj3dViewerPanel();
        leftBottomPanel = new javax.swing.JPanel();
        panelsContainer = new javax.swing.JPanel();
        geometrySelectionPanel = new javax.swing.JPanel();
        geometryTypeCombo = new javax.swing.JComboBox<>();
        axesCB = new javax.swing.JCheckBox();
        lightVectorCB = new javax.swing.JCheckBox();
        directionalLightBackgroundPanel = new javax.swing.JPanel();
        directionalLightOnLabel = new javax.swing.JLabel();
        directionalLightOnCB = new javax.swing.JCheckBox();
        directionalLightColorLabel = new javax.swing.JLabel();
        directionalLightColorRedTF = new javax.swing.JFormattedTextField();
        directionalLightColorGreenTF = new javax.swing.JFormattedTextField();
        directionalLightColorBlueTF = new javax.swing.JFormattedTextField();
        directionalLightColorChooser = new net.java.dev.colorchooser.ColorChooser();
        directionalLightColorHexTextField = new javax.swing.JTextField();
        directionalLightDirectionLabel = new javax.swing.JLabel();
        directionalLightDirectionXTF = new javax.swing.JFormattedTextField();
        directionalLightDirectionYTF = new javax.swing.JFormattedTextField();
        directionalLightDirectionZTF = new javax.swing.JFormattedTextField();
        directionalLightDirectionNormalizeButton = new javax.swing.JButton();
        directionalLightIntensityLabel = new javax.swing.JLabel();
        directionalLightIntensityTF = new javax.swing.JFormattedTextField();
        directionalLightIntensitySlider = new javax.swing.JSlider();
        directionalLightAmbientIntensityLabel = new javax.swing.JLabel();
        directionalLightAmbientIntensityTF = new javax.swing.JFormattedTextField();
        directionalLightAmbientIntensitySlider = new javax.swing.JSlider();
        BackgroundColorSeparator = new javax.swing.JSeparator();
        backgroundLabel = new javax.swing.JLabel();
        skyColorLabel = new javax.swing.JLabel();
        skyColorRedTF = new javax.swing.JFormattedTextField();
        skyColorGreenTF = new javax.swing.JFormattedTextField();
        skyColorBlueTF = new javax.swing.JFormattedTextField();
        skyColorChooser = new net.java.dev.colorchooser.ColorChooser();
        skyColorHexTextField = new javax.swing.JTextField();
        rightSplitPane = new javax.swing.JSplitPane();
        rightTopPanel = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        materialFieldsPanel = new javax.swing.JPanel();
        diffuseColorLabel = new javax.swing.JLabel();
        diffuseColorRedTF = new javax.swing.JFormattedTextField();
        diffuseColorGreenTF = new javax.swing.JFormattedTextField();
        diffuseColorBlueTF = new javax.swing.JFormattedTextField();
        diffuseColorChooser = new net.java.dev.colorchooser.ColorChooser();
        diffuseColorHexTextField = new javax.swing.JTextField();
        emissiveColorLabel = new javax.swing.JLabel();
        emissiveColorRedTF = new javax.swing.JFormattedTextField();
        emissiveColorGreenTF = new javax.swing.JFormattedTextField();
        emissiveColorBlueTF = new javax.swing.JFormattedTextField();
        emissiveColorChooser = new net.java.dev.colorchooser.ColorChooser();
        emissiveColorHexTextField = new javax.swing.JTextField();
        specularColorLabel = new javax.swing.JLabel();
        specularColorRedTF = new javax.swing.JFormattedTextField();
        specularColorGreenTF = new javax.swing.JFormattedTextField();
        specularColorBlueTF = new javax.swing.JFormattedTextField();
        specularColorChooser = new net.java.dev.colorchooser.ColorChooser();
        specularColorHexTextField = new javax.swing.JTextField();
        transparencyLabel = new javax.swing.JLabel();
        transparencyTF = new javax.swing.JFormattedTextField();
        transparencySlider = new javax.swing.JSlider();
        shininessLabel = new javax.swing.JLabel();
        shininessTF = new javax.swing.JFormattedTextField();
        shininessSlider = new javax.swing.JSlider();
        ambientIntensityLabel = new javax.swing.JLabel();
        ambientIntensityTF = new javax.swing.JFormattedTextField();
        ambientIntensitySlider = new javax.swing.JSlider();
        universalMediaSelectorPanel = new javax.swing.JPanel();
        universalMediaThemeLabel = new javax.swing.JLabel();
        universalMediaGroupCombo = new javax.swing.JComboBox<>();
        universalMediaMaterialSlider = new javax.swing.JSlider();
        universalMediaMaterialTF = new javax.swing.JFormattedTextField();
        srcTabbedPane = new javax.swing.JTabbedPane();
        x3dSourcePane = new javax.swing.JPanel();
        x3dSourceScrollPane = new javax.swing.JScrollPane();
        x3dTextArea = new javax.swing.JTextArea();
        x3dvSourcePane = new javax.swing.JPanel();
        x3dvSourceScrollPane = new javax.swing.JScrollPane();
        x3dvTextArea = new javax.swing.JTextArea();
        ecmaSourcePane = new javax.swing.JPanel();
        ecmaSourceScrollPane = new javax.swing.JScrollPane();
        ecmascriptTextArea = new javax.swing.JTextArea();
        javaSourcePane = new javax.swing.JPanel();
        javaSourceScrollPane = new javax.swing.JScrollPane();
        javaTextArea = new javax.swing.JTextArea();
        materialHintLabel = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        dEFUSEpan.setMaximumSize(null);
        dEFUSEpan.setMinimumSize(null);
        dEFUSEpan.setPreferredSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.PAGE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        add(dEFUSEpan, gridBagConstraints);

        masterSplitPane.setDividerLocation(400);
        masterSplitPane.setResizeWeight(1.0);
        masterSplitPane.setPreferredSize(new java.awt.Dimension(800, 600));

        leftSplitPane.setDividerLocation(280);
        leftSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        leftSplitPane.setResizeWeight(1.0);
        leftSplitPane.setMinimumSize(new java.awt.Dimension(391, 281));

        leftTopPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.leftTopPanel.border.title"))); // NOI18N
        leftTopPanel.setLayout(new java.awt.BorderLayout());

        xj3dViewerPanel.setMinimumSize(new java.awt.Dimension(381, 220));
        xj3dViewerPanel.setPreferredSize(new java.awt.Dimension(381, 220));
        leftTopPanel.add(xj3dViewerPanel, java.awt.BorderLayout.CENTER);

        leftSplitPane.setTopComponent(leftTopPanel);

        panelsContainer.setLayout(new java.awt.GridBagLayout());

        geometrySelectionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.viewPan.border.title"))); // NOI18N

        geometryTypeCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Box", "Cone", "Cylinder", "Sphere", "Teapot" }));
        geometryTypeCombo.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.geometryTypeCombo.toolTipText")); // NOI18N
        geometryTypeCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                geometryTypeComboActionPerformed(evt);
            }
        });

        axesCB.setSelected(true);
        axesCB.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.jCheckBox2.text")); // NOI18N
        axesCB.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.axesCB.toolTipText")); // NOI18N
        axesCB.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        axesCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                axesCBActionPerformed(evt);
            }
        });

        lightVectorCB.setSelected(true);
        lightVectorCB.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.jCheckBox3.text")); // NOI18N
        lightVectorCB.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.lightVectorCB.toolTipText")); // NOI18N
        lightVectorCB.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        lightVectorCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                lightVectorCBActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout geometrySelectionPanelLayout = new javax.swing.GroupLayout(geometrySelectionPanel);
        geometrySelectionPanel.setLayout(geometrySelectionPanelLayout);
        geometrySelectionPanelLayout.setHorizontalGroup(
            geometrySelectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, geometrySelectionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(geometryTypeCombo, 0, 216, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(axesCB)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lightVectorCB)
                .addContainerGap())
        );
        geometrySelectionPanelLayout.setVerticalGroup(
            geometrySelectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(geometrySelectionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(geometrySelectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(geometryTypeCombo)
                    .addComponent(axesCB)
                    .addComponent(lightVectorCB)))
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        panelsContainer.add(geometrySelectionPanel, gridBagConstraints);

        directionalLightBackgroundPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.lightPan.border.border.title")))); // NOI18N
        directionalLightBackgroundPanel.setMinimumSize(new java.awt.Dimension(381, 240));
        directionalLightBackgroundPanel.setPreferredSize(new java.awt.Dimension(381, 240));
        directionalLightBackgroundPanel.setLayout(new java.awt.GridBagLayout());

        directionalLightOnLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        directionalLightOnLabel.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.onLab.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        directionalLightBackgroundPanel.add(directionalLightOnLabel, gridBagConstraints);

        directionalLightOnCB.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.jCheckBox1.text")); // NOI18N
        directionalLightOnCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        directionalLightOnCB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                directionalLightOnCBActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        directionalLightBackgroundPanel.add(directionalLightOnCB, gridBagConstraints);

        directionalLightColorLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        directionalLightColorLabel.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.colorLab.text")); // NOI18N
        directionalLightColorLabel.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.directionalLightColorLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        directionalLightBackgroundPanel.add(directionalLightColorLabel, gridBagConstraints);

        directionalLightColorRedTF.setColumns(3);
        directionalLightColorRedTF.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.colorRedTF.text")); // NOI18N
        directionalLightColorRedTF.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.directionalLightColorRed.toolTipText")); // NOI18N
        directionalLightColorRedTF.setMinimumSize(new java.awt.Dimension(50, 28));
        directionalLightColorRedTF.setName("directionalLightColorRed"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        directionalLightBackgroundPanel.add(directionalLightColorRedTF, gridBagConstraints);

        directionalLightColorGreenTF.setColumns(1);
        directionalLightColorGreenTF.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.colorGrnTF.text")); // NOI18N
        directionalLightColorGreenTF.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.directionalLightColorGreen.toolTipText")); // NOI18N
        directionalLightColorGreenTF.setMinimumSize(new java.awt.Dimension(50, 28));
        directionalLightColorGreenTF.setName("directionalLightColorGreen"); // NOI18N
        directionalLightColorGreenTF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                globalPropertyChangeListener(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        directionalLightBackgroundPanel.add(directionalLightColorGreenTF, gridBagConstraints);

        directionalLightColorBlueTF.setColumns(1);
        directionalLightColorBlueTF.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.colorBluTF.text")); // NOI18N
        directionalLightColorBlueTF.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.directionalLightColorBlue.toolTipText")); // NOI18N
        directionalLightColorBlueTF.setMinimumSize(new java.awt.Dimension(50, 28));
        directionalLightColorBlueTF.setName("directionalLightColorBlue"); // NOI18N
        directionalLightColorBlueTF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                globalPropertyChangeListener(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        directionalLightBackgroundPanel.add(directionalLightColorBlueTF, gridBagConstraints);

        directionalLightColorChooser.setMinimumSize(new java.awt.Dimension(15, 15));
        directionalLightColorChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                directionalLightColorChooserActionPerformed(evt);
            }
        });
        directionalLightColorChooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                directionalLightColorChooserPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout directionalLightColorChooserLayout = new javax.swing.GroupLayout(directionalLightColorChooser);
        directionalLightColorChooser.setLayout(directionalLightColorChooserLayout);
        directionalLightColorChooserLayout.setHorizontalGroup(
            directionalLightColorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 23, Short.MAX_VALUE)
        );
        directionalLightColorChooserLayout.setVerticalGroup(
            directionalLightColorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 23, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        directionalLightBackgroundPanel.add(directionalLightColorChooser, gridBagConstraints);

        directionalLightColorHexTextField.setEditable(false);
        directionalLightColorHexTextField.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.directionalLightColorHexTextField.text")); // NOI18N
        directionalLightColorHexTextField.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.directionalLightColorHexTextField.toolTipText")); // NOI18N
        directionalLightColorHexTextField.setMinimumSize(new java.awt.Dimension(60, 22));
        directionalLightColorHexTextField.setPreferredSize(new java.awt.Dimension(60, 22));
        directionalLightColorHexTextField.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        directionalLightBackgroundPanel.add(directionalLightColorHexTextField, gridBagConstraints);

        directionalLightDirectionLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        directionalLightDirectionLabel.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.dirLab.text")); // NOI18N
        directionalLightDirectionLabel.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.directionalLightDirectionXTF.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        directionalLightBackgroundPanel.add(directionalLightDirectionLabel, gridBagConstraints);

        directionalLightDirectionXTF.setColumns(1);
        directionalLightDirectionXTF.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.dir0TF.text")); // NOI18N
        directionalLightDirectionXTF.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.directionalLightDirectionXTF.toolTipText")); // NOI18N
        directionalLightDirectionXTF.setMinimumSize(new java.awt.Dimension(50, 28));
        directionalLightDirectionXTF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                globalPropertyChangeListener(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        directionalLightBackgroundPanel.add(directionalLightDirectionXTF, gridBagConstraints);

        directionalLightDirectionYTF.setColumns(1);
        directionalLightDirectionYTF.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.dir1TF.text")); // NOI18N
        directionalLightDirectionYTF.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.directionalLightDirectionXTF.toolTipText")); // NOI18N
        directionalLightDirectionYTF.setMinimumSize(new java.awt.Dimension(50, 28));
        directionalLightDirectionYTF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                globalPropertyChangeListener(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        directionalLightBackgroundPanel.add(directionalLightDirectionYTF, gridBagConstraints);

        directionalLightDirectionZTF.setColumns(1);
        directionalLightDirectionZTF.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.dir2TF.text")); // NOI18N
        directionalLightDirectionZTF.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.directionalLightDirectionXTF.toolTipText")); // NOI18N
        directionalLightDirectionZTF.setMinimumSize(new java.awt.Dimension(50, 28));
        directionalLightDirectionZTF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                globalPropertyChangeListener(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        directionalLightBackgroundPanel.add(directionalLightDirectionZTF, gridBagConstraints);

        directionalLightDirectionNormalizeButton.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.directionalLightDirectionNormalizeButton.text")); // NOI18N
        directionalLightDirectionNormalizeButton.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.directionalLightDirectionNormalizeButton.toolTipText")); // NOI18N
        directionalLightDirectionNormalizeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                directionalLightDirectionNormalizeButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        directionalLightBackgroundPanel.add(directionalLightDirectionNormalizeButton, gridBagConstraints);

        directionalLightIntensityLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        directionalLightIntensityLabel.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.intensLab.text")); // NOI18N
        directionalLightIntensityLabel.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.directionalLightIntensityLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        directionalLightBackgroundPanel.add(directionalLightIntensityLabel, gridBagConstraints);

        directionalLightIntensityTF.setColumns(1);
        directionalLightIntensityTF.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.intensTF.text")); // NOI18N
        directionalLightIntensityTF.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.directionalLightIntensityTF.toolTipText")); // NOI18N
        directionalLightIntensityTF.setMinimumSize(new java.awt.Dimension(50, 28));
        directionalLightIntensityTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                directionalLightIntensityTFActionPerformed(evt);
            }
        });
        directionalLightIntensityTF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                globalPropertyChangeListener(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        directionalLightBackgroundPanel.add(directionalLightIntensityTF, gridBagConstraints);

        directionalLightIntensitySlider.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.directionalLightIntensitySlider.toolTipText")); // NOI18N
        directionalLightIntensitySlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                directionalLightIntensitySliderStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        directionalLightBackgroundPanel.add(directionalLightIntensitySlider, gridBagConstraints);

        directionalLightAmbientIntensityLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        directionalLightAmbientIntensityLabel.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.ambLab.text")); // NOI18N
        directionalLightAmbientIntensityLabel.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.directionalLightAmbientIntensityLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        directionalLightBackgroundPanel.add(directionalLightAmbientIntensityLabel, gridBagConstraints);

        directionalLightAmbientIntensityTF.setColumns(1);
        directionalLightAmbientIntensityTF.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.ambTF.text")); // NOI18N
        directionalLightAmbientIntensityTF.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.directionalLightAmbientIntensityTF.toolTipText")); // NOI18N
        directionalLightAmbientIntensityTF.setMinimumSize(new java.awt.Dimension(50, 28));
        directionalLightAmbientIntensityTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                directionalLightAmbientIntensityTFActionPerformed(evt);
            }
        });
        directionalLightAmbientIntensityTF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                globalPropertyChangeListener(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 8, 3);
        directionalLightBackgroundPanel.add(directionalLightAmbientIntensityTF, gridBagConstraints);

        directionalLightAmbientIntensitySlider.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.directionalLightAmbientIntensitySlider.toolTipText")); // NOI18N
        directionalLightAmbientIntensitySlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                directionalLightAmbientIntensitySliderStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 8, 3);
        directionalLightBackgroundPanel.add(directionalLightAmbientIntensitySlider, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 0);
        directionalLightBackgroundPanel.add(BackgroundColorSeparator, gridBagConstraints);

        backgroundLabel.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.backgroundLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        directionalLightBackgroundPanel.add(backgroundLabel, gridBagConstraints);

        skyColorLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        skyColorLabel.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.skyLab.text")); // NOI18N
        skyColorLabel.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.skyColorLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 8, 3);
        directionalLightBackgroundPanel.add(skyColorLabel, gridBagConstraints);

        skyColorRedTF.setColumns(4);
        skyColorRedTF.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.skyTF0.text")); // NOI18N
        skyColorRedTF.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.skyColorRed.toolTipText")); // NOI18N
        skyColorRedTF.setName("skyColorRed"); // NOI18N
        skyColorRedTF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                globalPropertyChangeListener(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 8, 3);
        directionalLightBackgroundPanel.add(skyColorRedTF, gridBagConstraints);

        skyColorGreenTF.setColumns(4);
        skyColorGreenTF.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.skyTF1.text")); // NOI18N
        skyColorGreenTF.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.skyColorGreen.toolTipText")); // NOI18N
        skyColorGreenTF.setName("skyColorGreen"); // NOI18N
        skyColorGreenTF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                globalPropertyChangeListener(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 8, 3);
        directionalLightBackgroundPanel.add(skyColorGreenTF, gridBagConstraints);

        skyColorBlueTF.setColumns(4);
        skyColorBlueTF.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.skyTF2.text")); // NOI18N
        skyColorBlueTF.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.skyColorBlue.toolTipText")); // NOI18N
        skyColorBlueTF.setName("skyColorBlue"); // NOI18N
        skyColorBlueTF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                globalPropertyChangeListener(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 8, 3);
        directionalLightBackgroundPanel.add(skyColorBlueTF, gridBagConstraints);

        skyColorChooser.setMaximumSize(new java.awt.Dimension(48, 24));
        skyColorChooser.setMinimumSize(new java.awt.Dimension(48, 24));
        skyColorChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                skyColorChooserActionPerformed(evt);
            }
        });
        skyColorChooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                skyColorChooserPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout skyColorChooserLayout = new javax.swing.GroupLayout(skyColorChooser);
        skyColorChooser.setLayout(skyColorChooserLayout);
        skyColorChooserLayout.setHorizontalGroup(
            skyColorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 46, Short.MAX_VALUE)
        );
        skyColorChooserLayout.setVerticalGroup(
            skyColorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 27, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        directionalLightBackgroundPanel.add(skyColorChooser, gridBagConstraints);

        skyColorHexTextField.setEditable(false);
        skyColorHexTextField.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.skyColorHexTextField.text")); // NOI18N
        skyColorHexTextField.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.directionalLightColorHexTextField.toolTipText")); // NOI18N
        skyColorHexTextField.setMinimumSize(new java.awt.Dimension(60, 22));
        skyColorHexTextField.setPreferredSize(new java.awt.Dimension(60, 22));
        skyColorHexTextField.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        directionalLightBackgroundPanel.add(skyColorHexTextField, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        panelsContainer.add(directionalLightBackgroundPanel, gridBagConstraints);

        javax.swing.GroupLayout leftBottomPanelLayout = new javax.swing.GroupLayout(leftBottomPanel);
        leftBottomPanel.setLayout(leftBottomPanelLayout);
        leftBottomPanelLayout.setHorizontalGroup(
            leftBottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 391, Short.MAX_VALUE)
            .addGroup(leftBottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(leftBottomPanelLayout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(panelsContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, 0)))
        );
        leftBottomPanelLayout.setVerticalGroup(
            leftBottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 291, Short.MAX_VALUE)
            .addGroup(leftBottomPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(leftBottomPanelLayout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addComponent(panelsContainer, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, 0)))
        );

        leftSplitPane.setRightComponent(leftBottomPanel);

        masterSplitPane.setLeftComponent(leftSplitPane);

        rightSplitPane.setDividerLocation(300);
        rightSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);

        rightTopPanel.setLayout(new java.awt.BorderLayout());

        jPanel2.setLayout(new java.awt.GridBagLayout());

        materialFieldsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.materialFieldsPan.border.title"))); // NOI18N
        materialFieldsPanel.setLayout(new java.awt.GridBagLayout());

        diffuseColorLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        diffuseColorLabel.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.diffLab.text")); // NOI18N
        diffuseColorLabel.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.diffuseColorLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        materialFieldsPanel.add(diffuseColorLabel, gridBagConstraints);

        diffuseColorRedTF.setColumns(3);
        diffuseColorRedTF.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.diffuseColorRedTF.text")); // NOI18N
        diffuseColorRedTF.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.diffuseColorRed.toolTipText")); // NOI18N
        diffuseColorRedTF.setMinimumSize(new java.awt.Dimension(6, 15));
        diffuseColorRedTF.setName("diffuseColorRed"); // NOI18N
        diffuseColorRedTF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                globalPropertyChangeListener(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        materialFieldsPanel.add(diffuseColorRedTF, gridBagConstraints);

        diffuseColorGreenTF.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.diffuseColorGreenTF.text")); // NOI18N
        diffuseColorGreenTF.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.diffuseColorGreen.toolTipText")); // NOI18N
        diffuseColorGreenTF.setMinimumSize(new java.awt.Dimension(6, 15));
        diffuseColorGreenTF.setName("diffuseColorGreen"); // NOI18N
        diffuseColorGreenTF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                globalPropertyChangeListener(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        materialFieldsPanel.add(diffuseColorGreenTF, gridBagConstraints);

        diffuseColorBlueTF.setColumns(3);
        diffuseColorBlueTF.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.diffuseColorBlueTF.text")); // NOI18N
        diffuseColorBlueTF.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.diffuseColorBlue.toolTipText")); // NOI18N
        diffuseColorBlueTF.setMinimumSize(new java.awt.Dimension(6, 15));
        diffuseColorBlueTF.setName("diffuseColorBlue"); // NOI18N
        diffuseColorBlueTF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                globalPropertyChangeListener(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        materialFieldsPanel.add(diffuseColorBlueTF, gridBagConstraints);

        diffuseColorChooser.setMinimumSize(new java.awt.Dimension(15, 15));
        diffuseColorChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                diffuseColorChooserActionPerformed(evt);
            }
        });
        diffuseColorChooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                diffuseColorChooserPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout diffuseColorChooserLayout = new javax.swing.GroupLayout(diffuseColorChooser);
        diffuseColorChooser.setLayout(diffuseColorChooserLayout);
        diffuseColorChooserLayout.setHorizontalGroup(
            diffuseColorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );
        diffuseColorChooserLayout.setVerticalGroup(
            diffuseColorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        materialFieldsPanel.add(diffuseColorChooser, gridBagConstraints);

        diffuseColorHexTextField.setEditable(false);
        diffuseColorHexTextField.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.diffuseColorHexTextField.text")); // NOI18N
        diffuseColorHexTextField.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.directionalLightColorHexTextField.toolTipText")); // NOI18N
        diffuseColorHexTextField.setMinimumSize(new java.awt.Dimension(60, 22));
        diffuseColorHexTextField.setPreferredSize(new java.awt.Dimension(60, 22));
        diffuseColorHexTextField.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        materialFieldsPanel.add(diffuseColorHexTextField, gridBagConstraints);

        emissiveColorLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        emissiveColorLabel.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.emisLab.text")); // NOI18N
        emissiveColorLabel.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.emissiveColorLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        materialFieldsPanel.add(emissiveColorLabel, gridBagConstraints);

        emissiveColorRedTF.setColumns(3);
        emissiveColorRedTF.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.emissiveColorRedTF.text")); // NOI18N
        emissiveColorRedTF.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.emissiveColorRed.toolTipText")); // NOI18N
        emissiveColorRedTF.setMinimumSize(new java.awt.Dimension(6, 15));
        emissiveColorRedTF.setName("emissiveColorRed"); // NOI18N
        emissiveColorRedTF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                globalPropertyChangeListener(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        materialFieldsPanel.add(emissiveColorRedTF, gridBagConstraints);

        emissiveColorGreenTF.setColumns(3);
        emissiveColorGreenTF.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.emissiveColorGreenTF.text")); // NOI18N
        emissiveColorGreenTF.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.emissiveColorGreen.toolTipText")); // NOI18N
        emissiveColorGreenTF.setMinimumSize(new java.awt.Dimension(6, 15));
        emissiveColorGreenTF.setName("emissiveColorGreen"); // NOI18N
        emissiveColorGreenTF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                globalPropertyChangeListener(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        materialFieldsPanel.add(emissiveColorGreenTF, gridBagConstraints);

        emissiveColorBlueTF.setColumns(3);
        emissiveColorBlueTF.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.emissiveColorBlueTF.text")); // NOI18N
        emissiveColorBlueTF.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.emissiveColorBlue.toolTipText")); // NOI18N
        emissiveColorBlueTF.setMinimumSize(new java.awt.Dimension(6, 15));
        emissiveColorBlueTF.setName("emissiveColorBlue"); // NOI18N
        emissiveColorBlueTF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                globalPropertyChangeListener(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        materialFieldsPanel.add(emissiveColorBlueTF, gridBagConstraints);

        emissiveColorChooser.setMinimumSize(new java.awt.Dimension(15, 15));
        emissiveColorChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emissiveColorChooserActionPerformed(evt);
            }
        });
        emissiveColorChooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                emissiveColorChooserPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout emissiveColorChooserLayout = new javax.swing.GroupLayout(emissiveColorChooser);
        emissiveColorChooser.setLayout(emissiveColorChooserLayout);
        emissiveColorChooserLayout.setHorizontalGroup(
            emissiveColorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );
        emissiveColorChooserLayout.setVerticalGroup(
            emissiveColorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        materialFieldsPanel.add(emissiveColorChooser, gridBagConstraints);

        emissiveColorHexTextField.setEditable(false);
        emissiveColorHexTextField.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.emissiveColorHexTextField.text")); // NOI18N
        emissiveColorHexTextField.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.directionalLightColorHexTextField.toolTipText")); // NOI18N
        emissiveColorHexTextField.setMinimumSize(new java.awt.Dimension(60, 22));
        emissiveColorHexTextField.setPreferredSize(new java.awt.Dimension(60, 22));
        emissiveColorHexTextField.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        materialFieldsPanel.add(emissiveColorHexTextField, gridBagConstraints);

        specularColorLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        specularColorLabel.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.specLab.text")); // NOI18N
        specularColorLabel.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.specularColorLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        materialFieldsPanel.add(specularColorLabel, gridBagConstraints);

        specularColorRedTF.setColumns(3);
        specularColorRedTF.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.specularColorRedTF.text")); // NOI18N
        specularColorRedTF.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.specularColorRed.toolTipText")); // NOI18N
        specularColorRedTF.setMinimumSize(new java.awt.Dimension(6, 15));
        specularColorRedTF.setName("specularColorRed"); // NOI18N
        specularColorRedTF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                globalPropertyChangeListener(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        materialFieldsPanel.add(specularColorRedTF, gridBagConstraints);

        specularColorGreenTF.setColumns(3);
        specularColorGreenTF.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.specularColorGreenTF.text")); // NOI18N
        specularColorGreenTF.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.specularColorGreen.toolTipText")); // NOI18N
        specularColorGreenTF.setMinimumSize(new java.awt.Dimension(6, 15));
        specularColorGreenTF.setName("specularColorGreen"); // NOI18N
        specularColorGreenTF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                globalPropertyChangeListener(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        materialFieldsPanel.add(specularColorGreenTF, gridBagConstraints);

        specularColorBlueTF.setColumns(3);
        specularColorBlueTF.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.specularColorBlueTF.text")); // NOI18N
        specularColorBlueTF.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.specularColorBlue.toolTipText")); // NOI18N
        specularColorBlueTF.setMinimumSize(new java.awt.Dimension(6, 15));
        specularColorBlueTF.setName("specularColorBlue"); // NOI18N
        specularColorBlueTF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                globalPropertyChangeListener(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        materialFieldsPanel.add(specularColorBlueTF, gridBagConstraints);

        specularColorChooser.setMinimumSize(new java.awt.Dimension(15, 15));
        specularColorChooser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                specularColorChooserActionPerformed(evt);
            }
        });
        specularColorChooser.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                specularColorChooserPropertyChange(evt);
            }
        });

        javax.swing.GroupLayout specularColorChooserLayout = new javax.swing.GroupLayout(specularColorChooser);
        specularColorChooser.setLayout(specularColorChooserLayout);
        specularColorChooserLayout.setHorizontalGroup(
            specularColorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );
        specularColorChooserLayout.setVerticalGroup(
            specularColorChooserLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 22, Short.MAX_VALUE)
        );

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        materialFieldsPanel.add(specularColorChooser, gridBagConstraints);

        specularColorHexTextField.setEditable(false);
        specularColorHexTextField.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.specularColorHexTextField.text")); // NOI18N
        specularColorHexTextField.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.directionalLightColorHexTextField.toolTipText")); // NOI18N
        specularColorHexTextField.setMinimumSize(new java.awt.Dimension(60, 22));
        specularColorHexTextField.setPreferredSize(new java.awt.Dimension(60, 22));
        specularColorHexTextField.setRequestFocusEnabled(false);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        materialFieldsPanel.add(specularColorHexTextField, gridBagConstraints);

        transparencyLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        transparencyLabel.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.transLab.text")); // NOI18N
        transparencyLabel.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.transparencyLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        materialFieldsPanel.add(transparencyLabel, gridBagConstraints);

        transparencyTF.setColumns(3);
        transparencyTF.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.transparencyTF.text")); // NOI18N
        transparencyTF.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.transparencyTF.toolTipText")); // NOI18N
        transparencyTF.setMinimumSize(new java.awt.Dimension(6, 15));
        transparencyTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transparencyTFActionPerformed(evt);
            }
        });
        transparencyTF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                globalPropertyChangeListener(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        materialFieldsPanel.add(transparencyTF, gridBagConstraints);

        transparencySlider.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.transparencySlider.toolTipText")); // NOI18N
        transparencySlider.setMaximumSize(new java.awt.Dimension(100, 25));
        transparencySlider.setPreferredSize(new java.awt.Dimension(100, 25));
        transparencySlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                transparencySliderHandler(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.67;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        materialFieldsPanel.add(transparencySlider, gridBagConstraints);

        shininessLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        shininessLabel.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.shinLab.text")); // NOI18N
        shininessLabel.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.shininessLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        materialFieldsPanel.add(shininessLabel, gridBagConstraints);

        shininessTF.setColumns(3);
        shininessTF.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.shininessTF.text")); // NOI18N
        shininessTF.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.shininessTF.toolTipText")); // NOI18N
        shininessTF.setMinimumSize(new java.awt.Dimension(6, 15));
        shininessTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                shininessTFActionPerformed(evt);
            }
        });
        shininessTF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                globalPropertyChangeListener(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        materialFieldsPanel.add(shininessTF, gridBagConstraints);

        shininessSlider.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.shininessSlider.toolTipText")); // NOI18N
        shininessSlider.setMaximumSize(new java.awt.Dimension(100, 25));
        shininessSlider.setPreferredSize(new java.awt.Dimension(100, 25));
        shininessSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                shininessSliderHandler(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.67;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        materialFieldsPanel.add(shininessSlider, gridBagConstraints);

        ambientIntensityLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        ambientIntensityLabel.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.ambLabMat.text")); // NOI18N
        ambientIntensityLabel.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.ambientIntensityLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        materialFieldsPanel.add(ambientIntensityLabel, gridBagConstraints);

        ambientIntensityTF.setColumns(3);
        ambientIntensityTF.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.ambientIntensityTF.text")); // NOI18N
        ambientIntensityTF.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.ambientIntensityTF.toolTipText")); // NOI18N
        ambientIntensityTF.setMinimumSize(new java.awt.Dimension(6, 15));
        ambientIntensityTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ambientIntensityTFActionPerformed(evt);
            }
        });
        ambientIntensityTF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                globalPropertyChangeListener(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        materialFieldsPanel.add(ambientIntensityTF, gridBagConstraints);

        ambientIntensitySlider.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.ambientIntensitySlider.toolTipText")); // NOI18N
        ambientIntensitySlider.setMaximumSize(new java.awt.Dimension(100, 25));
        ambientIntensitySlider.setPreferredSize(new java.awt.Dimension(100, 25));
        ambientIntensitySlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ambientIntensitySliderHandler(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.67;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        materialFieldsPanel.add(ambientIntensitySlider, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel2.add(materialFieldsPanel, gridBagConstraints);

        universalMediaSelectorPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.universalMediaSelectorPanel.border.title"))); // NOI18N
        universalMediaSelectorPanel.setMinimumSize(new java.awt.Dimension(0, 0));
        universalMediaSelectorPanel.setLayout(new java.awt.GridBagLayout());

        universalMediaThemeLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        universalMediaThemeLabel.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.selectLab.text")); // NOI18N
        universalMediaThemeLabel.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.universalMediaThemeLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        universalMediaSelectorPanel.add(universalMediaThemeLabel, gridBagConstraints);

        universalMediaGroupCombo.setModel(new DefaultComboBoxModel<>(getMediaGroupList()));
        universalMediaGroupCombo.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.universalMediaGroupCombo.toolTipText")); // NOI18N
        universalMediaGroupCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                universalMediaGroupComboActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        universalMediaSelectorPanel.add(universalMediaGroupCombo, gridBagConstraints);

        universalMediaMaterialSlider.setMaximum(34);
        universalMediaMaterialSlider.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.universalMediaMaterialSlider.toolTipText")); // NOI18N
        universalMediaMaterialSlider.setValue(0);
        universalMediaMaterialSlider.setEnabled(false);
        universalMediaMaterialSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                universalMediaMaterialSelectSliderStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.67;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        universalMediaSelectorPanel.add(universalMediaMaterialSlider, gridBagConstraints);

        universalMediaMaterialTF.setEditable(false);
        universalMediaMaterialTF.setColumns(3);
        universalMediaMaterialTF.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        universalMediaMaterialTF.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.universalMediaMaterialTF.text")); // NOI18N
        universalMediaMaterialTF.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.universalMediaMaterialTF.toolTipText")); // NOI18N
        universalMediaMaterialTF.setEnabled(false);
        universalMediaMaterialTF.setMinimumSize(new java.awt.Dimension(39, 22));
        universalMediaMaterialTF.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                globalPropertyChangeListener(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        universalMediaSelectorPanel.add(universalMediaMaterialTF, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        jPanel2.add(universalMediaSelectorPanel, gridBagConstraints);
        universalMediaSelectorPanel.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.universalMediaSelectorPanel.AccessibleContext.accessibleName")); // NOI18N

        rightTopPanel.add(jPanel2, java.awt.BorderLayout.CENTER);

        rightSplitPane.setTopComponent(rightTopPanel);

        srcTabbedPane.setMaximumSize(new java.awt.Dimension(90, 60));

        x3dSourceScrollPane.setBorder(null);

        x3dTextArea.setEditable(false);
        x3dTextArea.setColumns(20);
        x3dTextArea.setFont(new Font("MonoSpaced",getFont().getStyle(),getFont().getSize()));
        x3dTextArea.setRows(5);
        x3dSourceScrollPane.setViewportView(x3dTextArea);

        javax.swing.GroupLayout x3dSourcePaneLayout = new javax.swing.GroupLayout(x3dSourcePane);
        x3dSourcePane.setLayout(x3dSourcePaneLayout);
        x3dSourcePaneLayout.setHorizontalGroup(
            x3dSourcePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(x3dSourceScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
        );
        x3dSourcePaneLayout.setVerticalGroup(
            x3dSourcePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(x3dSourceScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
        );

        srcTabbedPane.addTab(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.x3dSourcePane.TabConstraints.tabTitle"), x3dSourcePane); // NOI18N

        x3dvSourceScrollPane.setBorder(null);

        x3dvTextArea.setColumns(20);
        x3dvTextArea.setEditable(false);
        x3dvTextArea.setFont(new Font("MonoSpaced",getFont().getStyle(),getFont().getSize()));
        x3dvTextArea.setRows(5);
        x3dvSourceScrollPane.setViewportView(x3dvTextArea);

        javax.swing.GroupLayout x3dvSourcePaneLayout = new javax.swing.GroupLayout(x3dvSourcePane);
        x3dvSourcePane.setLayout(x3dvSourcePaneLayout);
        x3dvSourcePaneLayout.setHorizontalGroup(
            x3dvSourcePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(x3dvSourceScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
        );
        x3dvSourcePaneLayout.setVerticalGroup(
            x3dvSourcePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(x3dvSourceScrollPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
        );

        srcTabbedPane.addTab(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.x3dvSrcPan.TabConstraints.tabTitle"), x3dvSourcePane); // NOI18N

        ecmaSourcePane.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.ecmaSourcePane.toolTipText")); // NOI18N
        ecmaSourcePane.setEnabled(false);

        ecmaSourceScrollPane.setBorder(null);

        ecmascriptTextArea.setColumns(20);
        ecmascriptTextArea.setEditable(false);
        ecmascriptTextArea.setFont(new Font("MonoSpaced",getFont().getStyle(),getFont().getSize()));
        ecmascriptTextArea.setRows(5);
        ecmaSourceScrollPane.setViewportView(ecmascriptTextArea);

        javax.swing.GroupLayout ecmaSourcePaneLayout = new javax.swing.GroupLayout(ecmaSourcePane);
        ecmaSourcePane.setLayout(ecmaSourcePaneLayout);
        ecmaSourcePaneLayout.setHorizontalGroup(
            ecmaSourcePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ecmaSourceScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
        );
        ecmaSourcePaneLayout.setVerticalGroup(
            ecmaSourcePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(ecmaSourceScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
        );

        srcTabbedPane.addTab(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.ecmaSrcPan.TabConstraints.tabTitle"), ecmaSourcePane); // NOI18N

        javaSourcePane.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.javaSourcePane.toolTipText")); // NOI18N
        javaSourcePane.setEnabled(false);

        javaSourceScrollPane.setBorder(null);

        javaTextArea.setColumns(20);
        javaTextArea.setEditable(false);
        javaTextArea.setFont(new Font("MonoSpaced",getFont().getStyle(),getFont().getSize()));
        javaTextArea.setRows(5);
        javaSourceScrollPane.setViewportView(javaTextArea);

        javax.swing.GroupLayout javaSourcePaneLayout = new javax.swing.GroupLayout(javaSourcePane);
        javaSourcePane.setLayout(javaSourcePaneLayout);
        javaSourcePaneLayout.setHorizontalGroup(
            javaSourcePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(javaSourceScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
        );
        javaSourcePaneLayout.setVerticalGroup(
            javaSourcePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(javaSourceScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
        );

        srcTabbedPane.addTab(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "NewJPanel.javaSrcPan.TabConstraints.tabTitle"), javaSourcePane); // NOI18N

        rightSplitPane.setRightComponent(srcTabbedPane);

        masterSplitPane.setRightComponent(rightSplitPane);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(masterSplitPane, gridBagConstraints);

        materialHintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        materialHintLabel.setText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.materialHintLabel.text")); // NOI18N
        materialHintLabel.setToolTipText(org.openide.util.NbBundle.getMessage(MATERIALCustomizer.class, "MATERIALCustomizer.materialHintLabel.toolTipText")); // NOI18N
        materialHintLabel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(materialHintLabel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

  // https://docs.oracle.com/en/java/javase/16/docs/api/java.base/java/lang/SuppressWarnings.html
  // https://docs.oracle.com/javase/specs/jls/se16/html/jls-9.html#jls-9.6.4.5
  // https://stackoverflow.com/questions/48401194/how-to-document-suppresswarningsunused
  @SuppressWarnings("unused") // evt
  // Combo action handlers
  private void geometryTypeComboActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_geometryTypeComboActionPerformed
  {//GEN-HEADEREND:event_geometryTypeComboActionPerformed
    materialCustomizerXj3dSupport.setGeometryField((String)geometryTypeCombo.getSelectedItem());
  }//GEN-LAST:event_geometryTypeComboActionPerformed

  @SuppressWarnings("unused") // evt
  private void universalMediaGroupComboActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_universalMediaGroupComboActionPerformed
  {//GEN-HEADEREND:event_universalMediaGroupComboActionPerformed
    mediaSliderDisablementLocked = true;
    globalPropertyChangeListener(new PropertyChangeEvent(universalMediaGroupCombo,"value",null,null));  // fake it
    mediaSliderDisablementLocked = false;
  }//GEN-LAST:event_universalMediaGroupComboActionPerformed

  // Check box action handlers
  @SuppressWarnings("unused") // evt
  private void axesCBActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_axesCBActionPerformed
  {//GEN-HEADEREND:event_axesCBActionPerformed
    materialCustomizerXj3dSupport.setShowAxes(axesCB.isSelected());
  }//GEN-LAST:event_axesCBActionPerformed

  @SuppressWarnings("unused") // evt
  private void lightVectorCBActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_lightVectorCBActionPerformed
  {//GEN-HEADEREND:event_lightVectorCBActionPerformed
    materialCustomizerXj3dSupport.setShowLightVector(lightVectorCB.isSelected());
  }//GEN-LAST:event_lightVectorCBActionPerformed

  @SuppressWarnings("unused") // evt
  private void directionalLightOnCBActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_directionalLightOnCBActionPerformed
  {//GEN-HEADEREND:event_directionalLightOnCBActionPerformed
    materialCustomizerXj3dSupport.setLightOn(directionalLightOnCB.isSelected());
  }//GEN-LAST:event_directionalLightOnCBActionPerformed

  // Sliders
  @SuppressWarnings("unused") // evt
  private void transparencySliderHandler(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_transparencySliderHandler
  {//GEN-HEADEREND:event_transparencySliderHandler
    int value = transparencySlider.getValue();
    transparencyTF.setValue((float)value/100.0f);
    disableUniversalMediaSliders();
  }//GEN-LAST:event_transparencySliderHandler

  @SuppressWarnings("unused") // evt
  private void shininessSliderHandler(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_shininessSliderHandler
  {//GEN-HEADEREND:event_shininessSliderHandler
    int val = shininessSlider.getValue();
    shininessTF.setValue((float)val/100.0f);
    disableUniversalMediaSliders();
  }//GEN-LAST:event_shininessSliderHandler

  @SuppressWarnings("unused") // evt
  private void ambientIntensitySliderHandler(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_ambientIntensitySliderHandler
  {//GEN-HEADEREND:event_ambientIntensitySliderHandler
    int value = ambientIntensitySlider.getValue();
    ambientIntensityTF.setValue((float)value/100.0f);
    disableUniversalMediaSliders();
  }//GEN-LAST:event_ambientIntensitySliderHandler

  @SuppressWarnings("unused") // evt
  private void directionalLightIntensitySliderStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_directionalLightIntensitySliderStateChanged
  {//GEN-HEADEREND:event_directionalLightIntensitySliderStateChanged
    int value = directionalLightIntensitySlider.getValue();
    directionalLightIntensityTF.setValue((float)value/100.0f);
  }//GEN-LAST:event_directionalLightIntensitySliderStateChanged

  @SuppressWarnings("unused") // evt
  private void directionalLightAmbientIntensitySliderStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_directionalLightAmbientIntensitySliderStateChanged
  {//GEN-HEADEREND:event_directionalLightAmbientIntensitySliderStateChanged
    int val = directionalLightAmbientIntensitySlider.getValue();
    directionalLightAmbientIntensityTF.setValue((float)val/100.0f);
  }//GEN-LAST:event_directionalLightAmbientIntensitySliderStateChanged

  @SuppressWarnings("unused") // evt
  private void universalMediaMaterialSelectSliderStateChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_universalMediaMaterialSelectSliderStateChanged
  {//GEN-HEADEREND:event_universalMediaMaterialSelectSliderStateChanged
    mediaSliderDisablementLocked = true;
    int val = universalMediaMaterialSlider.getValue();
    universalMediaMaterialTF.setValue(val);
    mediaSliderDisablementLocked = false;
  }//GEN-LAST:event_universalMediaMaterialSelectSliderStateChanged

  // Color Choosers Action handlers (called on final choice)
  @SuppressWarnings("unused") // evt
  private void diffuseColorChooserActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_diffuseColorChooserActionPerformed
  {//GEN-HEADEREND:event_diffuseColorChooserActionPerformed
    Color c = diffuseColorChooser.getColor();
    setAColor(c, diffuseColorTFArray);
    disableUniversalMediaSliders();
  }//GEN-LAST:event_diffuseColorChooserActionPerformed

  @SuppressWarnings("unused") // evt
  private void emissiveColorChooserActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_emissiveColorChooserActionPerformed
  {//GEN-HEADEREND:event_emissiveColorChooserActionPerformed
    Color c = emissiveColorChooser.getColor();
    setAColor(c,emissiveTFArray);
    disableUniversalMediaSliders();
  }//GEN-LAST:event_emissiveColorChooserActionPerformed

  @SuppressWarnings("unused") // evt
  private void specularColorChooserActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_specularColorChooserActionPerformed
  {//GEN-HEADEREND:event_specularColorChooserActionPerformed
    Color c = specularColorChooser.getColor();
    setAColor(c,specularTFArray);
    disableUniversalMediaSliders();
  }//GEN-LAST:event_specularColorChooserActionPerformed

  @SuppressWarnings("unused") // evt
  private void directionalLightColorChooserActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_directionalLightColorChooserActionPerformed
  {//GEN-HEADEREND:event_directionalLightColorChooserActionPerformed
    Color c = directionalLightColorChooser.getColor();
    setAColor(c,dLightColorTFArray);
  }//GEN-LAST:event_directionalLightColorChooserActionPerformed

  @SuppressWarnings("unused") // evt
  private void skyColorChooserActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_skyColorChooserActionPerformed
  {//GEN-HEADEREND:event_skyColorChooserActionPerformed
    Color c = skyColorChooser.getColor();
    setAColor(c,skyColorTFArray);
  }//GEN-LAST:event_skyColorChooserActionPerformed

  // Color chooser property changes (on every mouse move)
  private void diffuseColorChooserPropertyChange(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_diffuseColorChooserPropertyChange
  {//GEN-HEADEREND:event_diffuseColorChooserPropertyChange
    colorChooserPropertyChange(evt,diffuseColorTFArray);
  }//GEN-LAST:event_diffuseColorChooserPropertyChange

  private void skyColorChooserPropertyChange(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_skyColorChooserPropertyChange
  {//GEN-HEADEREND:event_skyColorChooserPropertyChange
    colorChooserPropertyChange(evt,skyColorTFArray);
  }//GEN-LAST:event_skyColorChooserPropertyChange

  private void directionalLightColorChooserPropertyChange(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_directionalLightColorChooserPropertyChange
  {//GEN-HEADEREND:event_directionalLightColorChooserPropertyChange
    colorChooserPropertyChange(evt,dLightColorTFArray);
  }//GEN-LAST:event_directionalLightColorChooserPropertyChange

  private void emissiveColorChooserPropertyChange(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_emissiveColorChooserPropertyChange
  {//GEN-HEADEREND:event_emissiveColorChooserPropertyChange
    colorChooserPropertyChange(evt,emissiveTFArray);
  }//GEN-LAST:event_emissiveColorChooserPropertyChange

  private void specularColorChooserPropertyChange(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_specularColorChooserPropertyChange
  {//GEN-HEADEREND:event_specularColorChooserPropertyChange
    colorChooserPropertyChange(evt,specularTFArray);
  }//GEN-LAST:event_specularColorChooserPropertyChange

  private void colorChooserPropertyChange(java.beans.PropertyChangeEvent evt, JFormattedTextField[] rgbTF)
  {
   if(evt.getPropertyName().equals(net.java.dev.colorchooser.ColorChooser.PROP_TRANSIENT_COLOR) ||
      evt.getPropertyName().equals(net.java.dev.colorchooser.ColorChooser.PROP_COLOR)     ) {
      setAColor((Color)evt.getNewValue(),rgbTF);
    }
  }

  private void disableUniversalMediaSliders()
  {
    if(!mediaSliderDisablementLocked) {
      if(universalMediaGroupCombo.getSelectedIndex() != MEDIA_NONE_IDX)
         universalMediaGroupCombo.setSelectedIndex(MEDIA_NONE_IDX);
      if(universalMediaMaterialSlider.isEnabled()) {
         universalMediaMaterialSlider.setValue(0);
         universalMediaMaterialSlider.setEnabled(false);
      }
      if(universalMediaMaterialTF.isEnabled())
        universalMediaMaterialTF.setEnabled(false);
    }
  }

  private void enableUniversalMediaSliders()
  {
   //if(!mediaSliderLocked) {
     if(!universalMediaMaterialSlider.isEnabled())
         universalMediaMaterialSlider.setEnabled(true);
     if(!universalMediaMaterialTF.isEnabled())
         universalMediaMaterialTF.setEnabled(true);
  // }
  }

  private void updateAllXj3dMaterialFields()
  {
    materialCustomizerXj3dSupport.setDiffuseColor    (nullTo0(diffuseColorRedTF),
                                  nullTo0(diffuseColorGreenTF),
                                  nullTo0(diffuseColorBlueTF));
    materialCustomizerXj3dSupport.setEmissiveColor   (nullTo0(emissiveColorRedTF),
                                  nullTo0(emissiveColorGreenTF),
                                  nullTo0(emissiveColorBlueTF));
    materialCustomizerXj3dSupport.setSpecularColor   (nullTo0(specularColorRedTF),
                                  nullTo0(specularColorGreenTF),
                                  nullTo0(specularColorBlueTF));
    materialCustomizerXj3dSupport.setTransparency    (nullTo0(transparencyTF));
    materialCustomizerXj3dSupport.setAmbientIntensity(nullTo0(ambientIntensityTF));
    materialCustomizerXj3dSupport.setShininess       (nullTo0(shininessTF));
  }

  private void updateAllXj3dLightingFields()
  {
    materialCustomizerXj3dSupport.setDirectionalLightDirection(nullTo0(directionalLightDirectionXTF),
                                           nullTo0(directionalLightDirectionYTF),
                                           nullTo0(directionalLightDirectionZTF));
    materialCustomizerXj3dSupport.setDirectionalLightColor(    nullTo0(directionalLightColorRedTF),
                                           nullTo0(directionalLightColorGreenTF),
                                           nullTo0(directionalLightColorBlueTF));
    materialCustomizerXj3dSupport.setSkyColor(                 nullTo0(skyColorRedTF),
                                           nullTo0(skyColorGreenTF),
                                           nullTo0(skyColorBlueTF));
    materialCustomizerXj3dSupport.setDirectionalLightIntensity(nullTo0(directionalLightIntensityTF));
    materialCustomizerXj3dSupport.setDirectionalLightAmbientIntensity(nullTo0(directionalLightAmbientIntensityTF));
  }


  private void checkColorTextField (JFormattedTextField jftf)
  {
      String text      = nullTo0(jftf);
      String textLower = text.toLowerCase();
//      String message   = "";
      String name      = jftf.getName();

      if (text.contains(".") || text.trim().startsWith("-"))
      {
        try
        {
            float floatValue = Float.parseFloat(text);
            if (floatValue > 1.0f)
            {
                jftf.setText("1.0"); // clamp max
                message = "<html><p>Large float value '<b>" + text + "</b>' found for <b>" + name + "</b>, clamped to 1.0";
            }
            else if (floatValue < 0.0f)
            {
                jftf.setText("0.0"); // clamp min
                message = "<html><p>Negative float value '<b>" + text + "</b>' found for <b>" + name + "</b>, clamped to 0.0";
            }
            // no change
        }
        catch (NumberFormatException e)
        {
            // bad value
            jftf.setText("0");
            message = "<html><p>Malformed value '<b>" + text + "</b>' found for <b>" + name + "</b>, reset to 0";
        }
      }
      else if (textLower.contains("a") || textLower.contains("b") || textLower.contains("c") || textLower.contains("d") || textLower.contains("e") || textLower.contains("f") ||
               textLower.startsWith("0x") || textLower.startsWith("#"))
      {
          if (text.startsWith("#"))
          {
              text = text.substring(1);
          }
          else if (textLower.startsWith("0x"))
          {
              text = text.substring(2);
          }
          try
          {
              int hexValue = Integer.parseInt(text, 16);
              if (hexValue > 255)
              {
                   jftf.setText("1"); // clamp max
                    message = "<html><p>Large hexadecimal value '<b>" + text + "</b>' found for <b>" + name + "</b>, clamped to ff = 1";
              }
              else if (hexValue < 0)
              {
                   jftf.setText("0"); // clamp min
                    message = "<html><p>Negative hexadecimal value '<b>" + text + "</b>' found for <b>" + name + "</b>, clamped to 0";
              }
              else // legal, explicit hex value '<b>" + text + "</b>' found 0..255
              {
                  jftf.setText(String.valueOf(hexValue / 255.0f));
              }
          }
          catch (NumberFormatException e)
          {
            // bad value
            jftf.setText("0");
            message = "<html><p>Malformed hexadecimal value '<b>" + text + "</b>' found for <b>" + name + "</b>, reset to 0";
          }
      }
      else // integer
      {
          try
          {
              int intValue = Integer.parseInt(text);
              if (intValue > 255)
              {
                   jftf.setText("1"); // clamp max
                    message = "<html><p>Large integer value '<b>" + text + "</b>' found for <b>" + name + "</b>, clamped to max = 1";
              }
              else if (intValue < 0)
              {
                   jftf.setText("0"); // clamp min
                    message = "<html><p>Negative integer value '<b>" + text + "</b>' found for <b>" + name + "</b>, clamped to 0";
              }
              else
              {
                  jftf.setText(String.valueOf(intValue / 255.0f));
                  message = "<html><p>Large HTML integer value '<b>" + text + "</b>' found for <b>" + name + "</b>, divided by 255, new value=" +
                          jftf.getText();
              }
          }
          catch (NumberFormatException e)
          {
            // bad value
            jftf.setText("0");
            message = "<html><p>Malformed integer value '<b>" + text + "</b>' found for <b>" + name + "</b>, reset to 0";
          }
      }
      if (message != null && !message.isBlank())
      {
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  message, "Color value problem", NotifyDescriptor.PLAIN_MESSAGE);
          DialogDisplayer.getDefault().notify(descriptor);
      }
  }
  /**
   * double-check event handling to ensure that all sliders are consistent with all text fields, updating Xj3D and source-text panes also
   * @param evt
   */
  private void globalPropertyChangeListener(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_globalPropertyChangeListener
  {//GEN-HEADEREND:event_globalPropertyChangeListener
    if (!"value".equals(evt.getPropertyName())) {
      return;
    }
    Object src = evt.getSource();

    if ((src instanceof JFormattedTextField) &&
        (((JFormattedTextField)src).getName() !=  null) &&
         ((Component)src).getName().contains("Color"))
    {
        checkColorTextField ((JFormattedTextField)src);
    }

    if (src == directionalLightDirectionXTF ||
        src == directionalLightDirectionYTF ||
        src == directionalLightDirectionZTF)
    {
      materialCustomizerXj3dSupport.setDirectionalLightDirection(nullTo0(directionalLightDirectionXTF),
                                             nullTo0(directionalLightDirectionYTF),
                                             nullTo0(directionalLightDirectionZTF));
    }
    else if (src == directionalLightColorRedTF ||
             src == directionalLightColorGreenTF ||
             src == directionalLightColorBlueTF)
    {
      materialCustomizerXj3dSupport.setDirectionalLightColor(nullTo0(directionalLightColorRedTF),
                                         nullTo0(directionalLightColorGreenTF),
                                         nullTo0(directionalLightColorBlueTF));
      updateDirectionalLightColorChooser();
      updateDirectionalLightColorHexTextField();
    }
    else if (src == directionalLightIntensityTF)
    {
      materialCustomizerXj3dSupport.setDirectionalLightIntensity(nullTo0(directionalLightIntensityTF));
        // this block may be needed if user didn't hit enter on field
        double value = Double.parseDouble(nullTo0(directionalLightIntensityTF));
        directionalLightIntensitySlider.setValue((int)(value * 100.0));
    }
    else if (src == directionalLightAmbientIntensityTF)
    {
      materialCustomizerXj3dSupport.setDirectionalLightAmbientIntensity(nullTo0(directionalLightAmbientIntensityTF));
        // this block may be needed if user didn't hit enter on field
        double value = Double.parseDouble(nullTo0(directionalLightAmbientIntensityTF));
        directionalLightAmbientIntensitySlider.setValue((int) (value * 100.0));
    }
    else if (src == skyColorRedTF ||
             src == skyColorGreenTF ||
             src == skyColorBlueTF)
    {
      materialCustomizerXj3dSupport.setSkyColor(nullTo0(skyColorRedTF),
                            nullTo0(skyColorGreenTF),
                            nullTo0(skyColorBlueTF));
      updateSkyColorChooser();
      updateSkyColorHexTextField();
    }
    else if (src == universalMediaGroupCombo) {
      if (MEDIA_NONE_ID.equals(universalMediaGroupCombo.getSelectedItem()))
      {
        disableUniversalMediaSliders();
      }
      else
      {
        enableUniversalMediaSliders();
      }
      handleUniversalMediaChange();
    }
    else if (src == universalMediaMaterialTF) {
      handleUniversalMediaChange();
    }
    else // Material field value changed
    {
      material.setContent(""); // clear for any change

      disableUniversalMediaSliders();
      if (src == diffuseColorRedTF ||
          src == diffuseColorGreenTF ||
          src == diffuseColorBlueTF)
      {
        materialCustomizerXj3dSupport.setDiffuseColor(
            nullTo0(diffuseColorRedTF),
            nullTo0(diffuseColorGreenTF),
            nullTo0(diffuseColorBlueTF));
        updateDiffuseColorChooser();
        updateDiffuseColorHexTextField();
      }
      else if (src == emissiveColorRedTF ||
               src == emissiveColorGreenTF ||
               src == emissiveColorBlueTF)
      {
        materialCustomizerXj3dSupport.setEmissiveColor(
            nullTo0(emissiveColorRedTF),
            nullTo0(emissiveColorGreenTF),
            nullTo0(emissiveColorBlueTF));
        updateEmissiveColorChooser();
        updateEmissiveColorHexTextField();
      }
      else if (src == specularColorRedTF ||
               src == specularColorGreenTF ||
               src == specularColorBlueTF)
      {
        materialCustomizerXj3dSupport.setSpecularColor(
            nullTo0(specularColorRedTF),
            nullTo0(specularColorGreenTF),
            nullTo0(specularColorBlueTF));
        updateSpecularColorChooser();
        updateSpecularColorHexTextField();
      }
      else if (src == transparencyTF)
      {
        materialCustomizerXj3dSupport.setTransparency(nullTo0(transparencyTF));
        // this block may be needed if user didn't hit enter on field
        double value = Double.parseDouble(nullTo0(transparencyTF));
        transparencySlider.setValue((int) (value * 100.0));
      }
      else if (src == ambientIntensityTF)
      {
        materialCustomizerXj3dSupport.setAmbientIntensity(nullTo0(ambientIntensityTF));
        // this block may be needed if user didn't hit enter on field
        double value = Double.parseDouble(nullTo0(ambientIntensityTF));
        ambientIntensitySlider.setValue((int) (value * 100.0));
      }
      else if (src == shininessTF)
      {
        materialCustomizerXj3dSupport.setShininess(nullTo0(shininessTF));
        // this block may be needed if user didn't hit enter on field
        double value = Double.parseDouble(nullTo0(shininessTF));
        shininessSlider.setValue((int) (value * 100.0));
      }
    }
    updateAllXj3dLightingFields();
    updateAllXj3dMaterialFields();

    x3dTextArea.setText(x3dEncoding());
    x3dvTextArea.setText(x3dvEncoding());
  }//GEN-LAST:event_globalPropertyChangeListener

  private void directionalLightDirectionNormalizeButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_directionalLightDirectionNormalizeButtonActionPerformed
  {//GEN-HEADEREND:event_directionalLightDirectionNormalizeButtonActionPerformed
    // normalize to unit vector
    double x = Double.parseDouble(nullTo0(directionalLightDirectionXTF));
    double y = Double.parseDouble(nullTo0(directionalLightDirectionYTF));
    double z = Double.parseDouble(nullTo0(directionalLightDirectionZTF));
    double u_hat = Math.sqrt(x * x + y * y + z * z);

    if (u_hat == 0)
    {
        directionalLightDirectionXTF.setText (""+lightDirectionDefault[0]);
        directionalLightDirectionYTF.setText (""+lightDirectionDefault[1]);
        directionalLightDirectionZTF.setText (""+lightDirectionDefault[2]);
    }
    else
    {
        directionalLightDirectionXTF.setValue("" + Math.floor((x / u_hat) * 10000.) / 10000.);
        directionalLightDirectionYTF.setValue("" + Math.floor((y / u_hat) * 10000.) / 10000.);
        directionalLightDirectionZTF.setValue("" + Math.floor((z / u_hat) * 10000.) / 10000.);
    }
  }//GEN-LAST:event_directionalLightDirectionNormalizeButtonActionPerformed

    @SuppressWarnings("unused") // evt
private void directionalLightIntensityTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_directionalLightIntensityTFActionPerformed
        double value = Double.parseDouble(nullTo0(directionalLightIntensityTF));
        directionalLightIntensitySlider.setValue((int)(value * 100.0));
        disableUniversalMediaSliders();
}//GEN-LAST:event_directionalLightIntensityTFActionPerformed

    @SuppressWarnings("unused") // evt
    private void shininessTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_shininessTFActionPerformed
        double value = Double.parseDouble(nullTo0(shininessTF));
        shininessSlider.setValue((int)(value * 100.0));
        disableUniversalMediaSliders();
    }//GEN-LAST:event_shininessTFActionPerformed

    @SuppressWarnings("unused") // evt
    private void ambientIntensityTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ambientIntensityTFActionPerformed
        double value = Double.parseDouble(nullTo0(ambientIntensityTF));
        ambientIntensitySlider.setValue((int)(value * 100.0));
        disableUniversalMediaSliders();
    }//GEN-LAST:event_ambientIntensityTFActionPerformed

    @SuppressWarnings("unused") // evt
    private void transparencyTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transparencyTFActionPerformed
        double value = Double.parseDouble(nullTo0(transparencyTF));
        transparencySlider.setValue((int)(value * 100.0));
        disableUniversalMediaSliders();
    }//GEN-LAST:event_transparencyTFActionPerformed

    @SuppressWarnings("unused") // evt
    private void directionalLightAmbientIntensityTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_directionalLightAmbientIntensityTFActionPerformed
        double value = Double.parseDouble(nullTo0(directionalLightAmbientIntensityTF));
        directionalLightAmbientIntensitySlider.setValue((int) (value * 100.0));
        disableUniversalMediaSliders();
    }//GEN-LAST:event_directionalLightAmbientIntensityTFActionPerformed

  private void handleUniversalMediaChange()
  {
    libraryChoice = (String) universalMediaGroupCombo.getSelectedItem ();
    libraryIndex  = universalMediaGroupCombo.getSelectedIndex();
    if (!libraryChoice.equals(MEDIA_NONE_ID)) {
      int materialVal = universalMediaMaterialSlider.getValue();
      UniversalMediaMaterials newMaterial = universalMediaMaterialFinder.getMaterial(libraryChoice, materialVal);
      setMaterialFields(newMaterial);
      updateMediaChoosers();
      updateAllXj3dMaterialFields();
      String frontBackNoneLabel = new String();
      if      (material.isFront()) frontBackNoneLabel = "frontMaterial ";
      else if (material.isBack())  frontBackNoneLabel = " backMaterial ";

      material.setContent("\n\t\t\t" + UNIVERSAL_MEDIA_COMMENT_HEADER1 + frontBackNoneLabel + UNIVERSAL_MEDIA_COMMENT_HEADER2 +
              UniversalMediaMaterialFinder.MATERIAL_UNIVERSAL_MEDIA_CHOICES[libraryIndex - 1] + " " +
              materialVal + " -->\n\t\t");
    }
    else
      material.setContent("");
  }

  private void updateMediaChoosers()
  {
    updateDiffuseColorChooser();
    updateEmissiveColorChooser();
    updateSpecularColorChooser();
  }

  private void updateDiffuseColorHexTextField()
  {
      r =   diffuseColorRedTF.getText();
      g = diffuseColorGreenTF.getText();
      b =  diffuseColorBlueTF.getText();
      sfColor = new SFColor(r, g, b);
      diffuseColorHexTextField.setText(             sfColor.getHex());
      diffuseColorHexTextField.setToolTipText("[" + sfColor.getHtmlColor() + "] " + HEX_COLOR_TOOLTIP);
  }

  private void updateEmissiveColorHexTextField()
  {
      r =   emissiveColorRedTF.getText();
      g = emissiveColorGreenTF.getText();
      b =  emissiveColorBlueTF.getText();
      sfColor = new SFColor(r, g, b);
      emissiveColorHexTextField.setText(             sfColor.getHex());
      emissiveColorHexTextField.setToolTipText("[" + sfColor.getHtmlColor() + "] " + HEX_COLOR_TOOLTIP);
  }

  private void updateSpecularColorHexTextField()
  {
      r =   specularColorRedTF.getText();
      g = specularColorGreenTF.getText();
      b =  specularColorBlueTF.getText();
      sfColor = new SFColor(r, g, b);
      specularColorHexTextField.setText(             sfColor.getHex());
      specularColorHexTextField.setToolTipText("[" + sfColor.getHtmlColor() + "] " + HEX_COLOR_TOOLTIP);
  }

  private void updateDirectionalLightColorHexTextField()
  {
      r =   directionalLightColorRedTF.getText();
      g = directionalLightColorGreenTF.getText();
      b =  directionalLightColorBlueTF.getText();
      sfColor = new SFColor(r, g, b);
      directionalLightColorHexTextField.setText(             sfColor.getHex());
      directionalLightColorHexTextField.setToolTipText("[" + sfColor.getHtmlColor() + "] " + HEX_COLOR_TOOLTIP);
  }

  private void updateSkyColorHexTextField()
  {
      r =   skyColorRedTF.getText();
      g = skyColorGreenTF.getText();
      b =  skyColorBlueTF.getText();
      sfColor = new SFColor(r, g, b);
      skyColorHexTextField.setText(             sfColor.getHex());
      skyColorHexTextField.setToolTipText("[" + sfColor.getHtmlColor() + "] " + HEX_COLOR_TOOLTIP);
  }

  private void updateDiffuseColorChooser()
  {
    try
    {
        diffuseColorChooser.setColor(new SFColor(diffuseColorRedTF.getText(),
                                                 diffuseColorGreenTF.getText(),
                                                 diffuseColorBlueTF.getText()).getColor());
    }
    catch (IllegalArgumentException e)
    {
          message = "<html><p>Illegal value diffuseColor='" +
                     diffuseColorRedTF.getText()   + " " +
                     diffuseColorGreenTF.getText() + " " +
                     diffuseColorBlueTF.getText()  + "'" +
                     "</p><p>All values must be in range [0..1]</p>";
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  message, "diffuseColor value problem,", NotifyDescriptor.PLAIN_MESSAGE);
          DialogDisplayer.getDefault().notify(descriptor);
    }
  }

  private void updateEmissiveColorChooser()
  {
    try
    {
        emissiveColorChooser.setColor(new SFColor(emissiveColorRedTF.getText(),
                                                  emissiveColorGreenTF.getText(),
                                                  emissiveColorBlueTF.getText()).getColor());
    }
    catch (IllegalArgumentException e)
    {
          message = "<html><p>Illegal value emissiveColor='" +
                     emissiveColorRedTF.getText()   + " " +
                     emissiveColorGreenTF.getText() + " " +
                     emissiveColorBlueTF.getText()  + "'" +
                     "</p><p>All values must be in range [0..1]</p>";
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  message, "emissiveColor value problem", NotifyDescriptor.PLAIN_MESSAGE);
          DialogDisplayer.getDefault().notify(descriptor);
    }
  }

  private void updateSpecularColorChooser()
  {
    try
    {
        specularColorChooser.setColor(new SFColor(specularColorRedTF.getText(),
                                                  specularColorGreenTF.getText(),
                                                  specularColorBlueTF.getText()).getColor());
    }
    catch (IllegalArgumentException e)
    {
          message = "<html><p>Illegal value specularColor='" +
                     specularColorRedTF.getText()   + " " +
                     specularColorGreenTF.getText() + " " +
                     specularColorBlueTF.getText()  + "'" +
                     "</p><p>All values must be in range [0..1]</p>";
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  message, "specularColor value problem", NotifyDescriptor.PLAIN_MESSAGE);
          DialogDisplayer.getDefault().notify(descriptor);
    }
  }

  private void updateSkyColorChooser()
  {
    skyColorChooser.setColor(new SFColor(skyColorRedTF.getText(),
                                         skyColorGreenTF.getText(),
                                         skyColorBlueTF.getText()).getColor());
  }

  private void updateDirectionalLightColorChooser()
  {
    directionalLightColorChooser.setColor(new SFColor(directionalLightColorRedTF.getText(),
                                                      directionalLightColorGreenTF.getText(),
                                                      directionalLightColorBlueTF.getText()).getColor());
  }
  String nullTo0(JTextField tf)
  {
    String s = tf.getText().trim();
    return s.length()<=0?"0":s;
  }

  private void setAColor(Color c, JFormattedTextField[] rgbTF)
  {
    float[] fa = c.getRGBColorComponents(null);

    rgbTF[0].setValue(""+fa[0]);  // use instead of setText to force "value" propertyChange event in TF
    rgbTF[1].setValue(""+fa[1]);
    rgbTF[2].setValue(""+fa[2]);
  }

  private String[] getMediaGroupList()
  {
    UniversalMediaGroup[] grps = UniversalMediaGroup.values();
    String[] sa = new String[grps.length+1];
    sa[0] = MEDIA_NONE_ID;
    int i=1;
    for(UniversalMediaGroup umg : grps)
    {
      sa[i] = umg.name();
      i++;
    }
    return sa;
  }

  private String getUniversalMediaGroupRegEx()
  {
    StringBuilder sb = new StringBuilder();
    if      (material.isFront()) sb.append(UNIVERSAL_MEDIA_COMMENT_HEADER_FRONT_REGEX);
    else if (material.isBack())  sb.append(UNIVERSAL_MEDIA_COMMENT_HEADER_BACK_REGEX);
    else                         sb.append(UNIVERSAL_MEDIA_COMMENT_HEADER_REGEX);
    sb.append('('); // start of group
    UniversalMediaGroup[] grps = UniversalMediaGroup.values();
    for(UniversalMediaGroup umg : grps) {
      sb.append(umg.name());
      sb.append('|');
    }
    sb.deleteCharAt(sb.length()-1); // last vert bar
    sb.append(')'); // end of group
    sb.append(UNIVERSAL_MEDIA_COMMENT_TAIL_REGEX);
    return sb.toString();
  }

  /** Create .x3d output string */
  private String x3dEncoding ()
  {
    String DEFconstruct;
    if (DEFname.length () > 0) DEFconstruct = " DEF='" + DEFname + "'";
    else                       DEFconstruct = "";

    String value = "<Material" + DEFconstruct
            +  "\n     diffuseColor='" + nullTo0(diffuseColorRedTF) + " " +  nullTo0(diffuseColorGreenTF) + " " +  nullTo0(diffuseColorBlueTF)
            + "'\n    emissiveColor='" + nullTo0(emissiveColorRedTF) + " " + nullTo0(emissiveColorGreenTF) + " " + nullTo0(emissiveColorBlueTF)
            + "'\n    specularColor='" + nullTo0(specularColorRedTF) + " " + nullTo0(specularColorGreenTF) + " " + nullTo0(specularColorBlueTF)
            + "'\n     transparency='" + nullTo0(transparencyTF)
            + "'\n ambientIntensity='" + nullTo0(ambientIntensityTF)
            + "'\n        shininess='" + nullTo0(shininessTF)
            + "'\n   containerField='material'\n/>";
    return value;
  }

  /** Create .x3dv output string */
  private String x3dvEncoding ()
  {
    String DEFconstruct;
    if (DEFname.length () > 0) DEFconstruct = " DEF " + DEFname;
    else                       DEFconstruct = "";

    String value = "material"  + DEFconstruct + " Material {"
            + "\n     diffuseColor " + nullTo0(diffuseColorRedTF) + " " +  nullTo0(diffuseColorGreenTF) + " " +  nullTo0(diffuseColorBlueTF)
            + "\n    emissiveColor " + nullTo0(emissiveColorRedTF) + " " + nullTo0(emissiveColorGreenTF) + " " + nullTo0(emissiveColorBlueTF)
            + "\n    specularColor " + nullTo0(specularColorRedTF) + " " + nullTo0(specularColorGreenTF) + " " + nullTo0(specularColorBlueTF)
            + "\n     transparency " + nullTo0(transparencyTF)
            + "\n ambientIntensity " + nullTo0(ambientIntensityTF)
            + "\n        shininess " + nullTo0(shininessTF)
            + "\n}";
    return value;
  }

  private void setMaterialFields(UniversalMediaMaterials mat)
  {
    diffuseColorRedTF.setText("" + mat.diffuseColorRed());
    diffuseColorGreenTF.setText("" + mat.diffuseColorGreen());
    diffuseColorBlueTF.setText("" + mat.diffuseColorBlue());
    emissiveColorRedTF.setText("" + mat.emissiveColorRed());
    emissiveColorGreenTF.setText("" + mat.emissiveColorGreen());
    emissiveColorBlueTF.setText("" + mat.emissiveColorBlue());
    specularColorRedTF.setText("" + mat.specularColorRed());
    specularColorGreenTF.setText("" + mat.specularColorGreen());
    specularColorBlueTF.setText("" + mat.specularColorBlue());
    transparencyTF.setText("" + mat.transparency());
    ambientIntensityTF.setText("" + mat.ambientIntensity());
    shininessTF.setText("" + mat.shininess());

    float f = new SFFloat(mat.transparency()).getValue();
    transparencySlider.setValue((int)(f*100.));
    f = new SFFloat(mat.ambientIntensity()).getValue();
    ambientIntensitySlider.setValue((int)(f*100.));
    f = new SFFloat(mat.shininess()).getValue();
    shininessSlider.setValue((int)(f*100.));
  }

  private void resetMaterialValues()
  {
    emissiveColorRedTF.setText(material.getEmissiveColor0());
    emissiveColorGreenTF.setText(material.getEmissiveColor1());
    emissiveColorBlueTF.setText(material.getEmissiveColor2());
    try
    {
        emissiveColorChooser.setColor(new SFColor(material.getEmissiveColor0(),
                                         material.getEmissiveColor1(),
                                         material.getEmissiveColor2()).getColor());
    }
    catch (IllegalArgumentException e)
    {
          message = "<html><p>Illegal value emissiveColor='" +
                     material.getEmissiveColor0() + " " +
                     material.getEmissiveColor1() + " " +
                     material.getEmissiveColor2() + "'" +
                     "</p><p>All values must be in range [0..1]</p>";
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  message, "emissiveColor value problem", NotifyDescriptor.PLAIN_MESSAGE);
          DialogDisplayer.getDefault().notify(descriptor);
    }

    diffuseColorRedTF.setText(material.getDiffuseColor0());
    diffuseColorGreenTF.setText(material.getDiffuseColor1());
    diffuseColorBlueTF.setText(material.getDiffuseColor2());
    try
    {
        diffuseColorChooser.setColor(new SFColor(material.getDiffuseColor0(),
                                         material.getDiffuseColor1(),
                                         material.getDiffuseColor2()).getColor());
    }
    catch (IllegalArgumentException e)
    {
          message = "<html><p>Illegal value diffuseColor='" +
                     material.getDiffuseColor0() + " " +
                     material.getDiffuseColor1() + " " +
                     material.getDiffuseColor2() + "'" +
                     "</p><p>All values must be in range [0..1]</p>";
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  message, "diffuseColor value problem", NotifyDescriptor.PLAIN_MESSAGE);
          DialogDisplayer.getDefault().notify(descriptor);
    }

    specularColorRedTF.setText(material.getSpecularColor0());
    specularColorGreenTF.setText(material.getSpecularColor1());
    specularColorBlueTF.setText(material.getSpecularColor2());
    try
    {
        specularColorChooser.setColor(new SFColor(material.getSpecularColor0(),
                                             material.getSpecularColor1(),
                                             material.getSpecularColor2()).getColor());
    }
    catch (IllegalArgumentException e)
    {
          message = "<html><p>Illegal value specularColor='" +
                     material.getSpecularColor0() + " " +
                     material.getSpecularColor1() + " " +
                     material.getSpecularColor2() + "'" +
                     "</p><p>All values must be in range [0..1]</p>";
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  message, "specularColor value problem", NotifyDescriptor.PLAIN_MESSAGE);
          DialogDisplayer.getDefault().notify(descriptor);
    }

        transparencySlider.setValue((int)((new SFFloat(material.getTransparency(),    0.0f,1.0f,true)).getValue()*100.0f));
    ambientIntensitySlider.setValue((int)((new SFFloat(material.getAmbientIntensity(),0.0f,1.0f,true)).getValue()*100.0f));
           shininessSlider.setValue((int)((new SFFloat(material.getShininess(),       0.0f,1.0f,true)).getValue()*100.0f));
        transparencyTF.setText(material.getTransparency());
    ambientIntensityTF.setText(material.getAmbientIntensity());
           shininessTF.setText(material.getShininess());
  }

  private void initializeUniversalMediaSelection()
  {
    String comboVal;
    int sliderVal;

    try { // catch number parsing error
      String content = material.getContent();
      if (content != null && !content.isBlank()) {
        Pattern pattern = Pattern.compile(getUniversalMediaGroupRegEx(), Pattern.CASE_INSENSITIVE|Pattern.DOTALL); // dotall means include \r\n in "."
        Matcher matcher = pattern.matcher(content);
        if (matcher.matches()) {
          int gc = matcher.groupCount();
          if (gc >= 2) {
            comboVal = matcher.group(1);                     // name -- group 0 is whole string
            sliderVal = Integer.parseInt(matcher.group(2));  // number
            int count = universalMediaGroupCombo.getItemCount();
            for (int i = 0; i < count; i++) {
              if (comboVal.equalsIgnoreCase(universalMediaGroupCombo.getItemAt(i))) {
                universalMediaGroupCombo.setSelectedIndex(i);
                universalMediaMaterialSlider.setValue(sliderVal);
                return; // successful match
              }
            }
          }
        }
      }
    }
    catch (NumberFormatException t) {}

    // Didn't match, set to default
    universalMediaGroupCombo.setSelectedItem(MEDIA_NONE_ID);
    universalMediaMaterialSlider.setValue(0);
  }

  @Deprecated
  @SuppressWarnings("unused") // evt
  private void initializeUniversalMediaSelectionUnused () // unused/OBE, regex employed instead
  {
    // check for embedded comment indicating that this Material is actually a Universal Media material
    System.out.println ("material.getContent().length()=" + material.getContent().length());
    if (material.getContent().length()==0) return;

    System.out.println (material.getContent());
    if (!material.getContent().contains(UNIVERSAL_MEDIA_COMMENT_HEADER1))
    {
        System.out.println ("warning, unrecognized content, ignoring");
        return;
    }
    System.out.println ("found header: " + UNIVERSAL_MEDIA_COMMENT_HEADER1);
    String remainder1 = material.getContent().trim(). substring(UNIVERSAL_MEDIA_COMMENT_HEADER1.length());
    System.out.println ("remainder1: " + remainder1);
    int nextSpaceIndex = remainder1.indexOf(" ");
    System.out.println ("nextSpaceIndex for library name: " + nextSpaceIndex);
    if (nextSpaceIndex <= 0) return;
    String libraryName = remainder1.substring(0, nextSpaceIndex);
    System.out.println ("libraryName: " + libraryName);

    String remainder2 = remainder1.substring(nextSpaceIndex);
    System.out.println ("remainder2: " + remainder2);
    nextSpaceIndex = remainder2.indexOf(" ");
    System.out.println ("nextSpaceIndex for library number: " + nextSpaceIndex);
    String libraryNumber  = remainder2.substring(0, nextSpaceIndex);
    System.out.println ("libraryNumber: " + libraryNumber);

    // TODO content matches assigned values for this library.  If so accept, if not remove embedded comment
    universalMediaGroupCombo.setSelectedItem (libraryName);
    universalMediaGroupCombo.setSelectedItem(libraryNumber);
  }

  private void resetLightingValues()
  {
    directionalLightOnCB.setSelected (lightOnDefault);

    float[] lightDefaultFloats = lightColorDefault.getRGBColorComponents(null);
    directionalLightColorRedTF.setText(""+lightDefaultFloats[0]);
    directionalLightColorGreenTF.setText (""+lightDefaultFloats[1]);
    directionalLightColorBlueTF.setText (""+lightDefaultFloats[2]);
    directionalLightColorChooser.setColor(lightColorDefault);

    directionalLightDirectionXTF.setText (""+lightDirectionDefault[0]);
    directionalLightDirectionYTF.setText (""+lightDirectionDefault[1]);
    directionalLightDirectionZTF.setText (""+lightDirectionDefault[2]);

    directionalLightIntensityTF.setText (""+lightIntensityDefault);
    directionalLightIntensitySlider.setValue((int)(lightIntensityDefault*100.0));
    directionalLightAmbientIntensityTF.setText (""+lightAmbientIntensityDefault);
    directionalLightAmbientIntensitySlider.setValue((int)(lightAmbientIntensityDefault*100.0));

    float[] skyColorDefaultFloats = skyColorDefault.getRGBColorComponents(null);
    skyColorRedTF.setText (""+skyColorDefaultFloats[0]);
    skyColorGreenTF.setText (""+skyColorDefaultFloats[1]);
    skyColorBlueTF.setText (""+skyColorDefaultFloats[2]);
    skyColorChooser.setColor(skyColorDefault);

    universalMediaGroupCombo.setSelectedIndex(MEDIA_NONE_IDX);
    universalMediaMaterialSlider.setValue(0);

    lightVectorCB.setSelected (true);

    axesCB.setSelected(true);
    // TODO:  reset scene viewpoint
  }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JSeparator BackgroundColorSeparator;
    private javax.swing.JLabel ambientIntensityLabel;
    private javax.swing.JSlider ambientIntensitySlider;
    private javax.swing.JFormattedTextField ambientIntensityTF;
    private javax.swing.JCheckBox axesCB;
    private javax.swing.JLabel backgroundLabel;
    private org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpan;
    private javax.swing.JFormattedTextField diffuseColorBlueTF;
    private net.java.dev.colorchooser.ColorChooser diffuseColorChooser;
    private javax.swing.JFormattedTextField diffuseColorGreenTF;
    private javax.swing.JTextField diffuseColorHexTextField;
    private javax.swing.JLabel diffuseColorLabel;
    private javax.swing.JFormattedTextField diffuseColorRedTF;
    private javax.swing.JLabel directionalLightAmbientIntensityLabel;
    private javax.swing.JSlider directionalLightAmbientIntensitySlider;
    private javax.swing.JFormattedTextField directionalLightAmbientIntensityTF;
    private javax.swing.JPanel directionalLightBackgroundPanel;
    private javax.swing.JFormattedTextField directionalLightColorBlueTF;
    private net.java.dev.colorchooser.ColorChooser directionalLightColorChooser;
    private javax.swing.JFormattedTextField directionalLightColorGreenTF;
    private javax.swing.JTextField directionalLightColorHexTextField;
    private javax.swing.JLabel directionalLightColorLabel;
    private javax.swing.JFormattedTextField directionalLightColorRedTF;
    private javax.swing.JLabel directionalLightDirectionLabel;
    private javax.swing.JButton directionalLightDirectionNormalizeButton;
    private javax.swing.JFormattedTextField directionalLightDirectionXTF;
    private javax.swing.JFormattedTextField directionalLightDirectionYTF;
    private javax.swing.JFormattedTextField directionalLightDirectionZTF;
    private javax.swing.JLabel directionalLightIntensityLabel;
    private javax.swing.JSlider directionalLightIntensitySlider;
    private javax.swing.JFormattedTextField directionalLightIntensityTF;
    private javax.swing.JCheckBox directionalLightOnCB;
    private javax.swing.JLabel directionalLightOnLabel;
    private javax.swing.JPanel ecmaSourcePane;
    private javax.swing.JScrollPane ecmaSourceScrollPane;
    private javax.swing.JTextArea ecmascriptTextArea;
    private javax.swing.JFormattedTextField emissiveColorBlueTF;
    private net.java.dev.colorchooser.ColorChooser emissiveColorChooser;
    private javax.swing.JFormattedTextField emissiveColorGreenTF;
    private javax.swing.JTextField emissiveColorHexTextField;
    private javax.swing.JLabel emissiveColorLabel;
    private javax.swing.JFormattedTextField emissiveColorRedTF;
    private javax.swing.JPanel geometrySelectionPanel;
    private javax.swing.JComboBox<String> geometryTypeCombo;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel javaSourcePane;
    private javax.swing.JScrollPane javaSourceScrollPane;
    private javax.swing.JTextArea javaTextArea;
    private javax.swing.JPanel leftBottomPanel;
    private javax.swing.JSplitPane leftSplitPane;
    private javax.swing.JPanel leftTopPanel;
    private javax.swing.JCheckBox lightVectorCB;
    private javax.swing.JSplitPane masterSplitPane;
    private javax.swing.JPanel materialFieldsPanel;
    private javax.swing.JLabel materialHintLabel;
    private javax.swing.JPanel panelsContainer;
    private javax.swing.JSplitPane rightSplitPane;
    private javax.swing.JPanel rightTopPanel;
    private javax.swing.JLabel shininessLabel;
    private javax.swing.JSlider shininessSlider;
    private javax.swing.JFormattedTextField shininessTF;
    private javax.swing.JFormattedTextField skyColorBlueTF;
    private net.java.dev.colorchooser.ColorChooser skyColorChooser;
    private javax.swing.JFormattedTextField skyColorGreenTF;
    private javax.swing.JTextField skyColorHexTextField;
    private javax.swing.JLabel skyColorLabel;
    private javax.swing.JFormattedTextField skyColorRedTF;
    private javax.swing.JFormattedTextField specularColorBlueTF;
    private net.java.dev.colorchooser.ColorChooser specularColorChooser;
    private javax.swing.JFormattedTextField specularColorGreenTF;
    private javax.swing.JTextField specularColorHexTextField;
    private javax.swing.JLabel specularColorLabel;
    private javax.swing.JFormattedTextField specularColorRedTF;
    private javax.swing.JTabbedPane srcTabbedPane;
    private javax.swing.JLabel transparencyLabel;
    private javax.swing.JSlider transparencySlider;
    private javax.swing.JFormattedTextField transparencyTF;
    private javax.swing.JComboBox<String> universalMediaGroupCombo;
    private javax.swing.JSlider universalMediaMaterialSlider;
    private javax.swing.JFormattedTextField universalMediaMaterialTF;
    private javax.swing.JPanel universalMediaSelectorPanel;
    private javax.swing.JLabel universalMediaThemeLabel;
    private javax.swing.JPanel x3dSourcePane;
    private javax.swing.JScrollPane x3dSourceScrollPane;
    private javax.swing.JTextArea x3dTextArea;
    private javax.swing.JPanel x3dvSourcePane;
    private javax.swing.JScrollPane x3dvSourceScrollPane;
    private javax.swing.JTextArea x3dvTextArea;
    private org.web3d.x3d.xj3d.viewer.Xj3dViewerPanel xj3dViewerPanel;
    // End of variables declaration//GEN-END:variables

  private void adjustWidgetSizes()
  {
    Dimension d = new Dimension(directionalLightColorRedTF.getPreferredSize());
    ambientIntensityTF.setMinimumSize(d);
    diffuseColorBlueTF.setMinimumSize(d);
    diffuseColorGreenTF.setMinimumSize(d);
    diffuseColorRedTF.setMinimumSize(d);
    directionalLightAmbientIntensityTF.setMinimumSize(d);
    directionalLightColorBlueTF.setMinimumSize(d);
    directionalLightColorGreenTF.setMinimumSize(d);
    directionalLightColorRedTF.setMinimumSize(d);
    directionalLightDirectionXTF.setMinimumSize(d);
    directionalLightDirectionYTF.setMinimumSize(d);
    directionalLightDirectionZTF.setMinimumSize(d);
    directionalLightIntensityTF.setMinimumSize(d);
    emissiveColorBlueTF.setMinimumSize(d);
    emissiveColorGreenTF.setMinimumSize(d);
    emissiveColorRedTF.setMinimumSize(d);
    universalMediaMaterialTF.setMinimumSize(d);
    shininessTF.setMinimumSize(d);
    skyColorBlueTF.setMinimumSize(d);
    skyColorGreenTF.setMinimumSize(d);
    skyColorRedTF.setMinimumSize(d);
    specularColorBlueTF.setMinimumSize(d);
    specularColorGreenTF.setMinimumSize(d);
    specularColorRedTF.setMinimumSize(d);
    transparencyTF.setMinimumSize(d);
  }
}
