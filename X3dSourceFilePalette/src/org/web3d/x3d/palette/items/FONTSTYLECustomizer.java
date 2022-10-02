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
import javax.swing.DefaultComboBoxModel;
import javax.swing.text.JTextComponent;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.HelpCtx;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * FONTSTYLECustomizer.java
 * Created on August 2, 2007, 2:53 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class FONTSTYLECustomizer extends BaseCustomizer
{
  private final FONTSTYLE fontstyle;
  private final JTextComponent target;
  private final UrlExpandableList2 urlExpandableList = new UrlExpandableList2();
  
  /** Creates new form FONTSTYLECustomizer */
  public FONTSTYLECustomizer(FONTSTYLE fontstyle, JTextComponent target)
  {
    super(fontstyle);
    this.fontstyle = fontstyle;
    this.target = target;
    
    HelpCtx.setHelpIDString(FONTSTYLECustomizer.this, "FONTSTYLE_ELEM_HELPID");
    
    initComponents();

    // TODO family is actually MFString array,
    // need to allow selection/insertion of multiple values
   familyCombo.setSelectedItem(fontstyle.getFamily());
   
   checkJustifyValues ();
   
   majorJustifyCombo.setSelectedItem(fontstyle.getMajorJustify());
   minorJustifyCombo.setSelectedItem(fontstyle.getMinorJustify());
        styleCB.setSelectedItem(fontstyle.getStyle());
             languageTF.setText(fontstyle.getLanguage());
     horizontalChkB.setSelected(fontstyle.isHorizontal());
    leftToRightChkB.setSelected(fontstyle.isLeftToRight());
    topToBottomChkB.setSelected(fontstyle.isTopToBottom());
                 sizeTF.setText(fontstyle.getSize());
              spacingTF.setText(fontstyle.getSpacing());
              
        topToBottomHandler(null);
  }
  
    private void checkJustifyValues()
    {
        boolean justifyWarningFound = false;
        StringBuilder sb = new StringBuilder("<html><p>Illegal justify attribute value found in <br /> &lt;Fontstyle ");
        if (fontstyle.getDEFUSEvalue().length() > 0)
        {
            sb.append("DEF='").append(fontstyle.getDEFUSEvalue()).append("' ");
        }
        sb.append("justify='\"").append(
                fontstyle.getMajorJustify()).append("\" \"").append(
                fontstyle.getMinorJustify()).append("\"'/&gt;</p> <p>");

        if (fontstyle.getMajorJustify().equalsIgnoreCase("LEFT"))
        {
            justifyWarningFound = true;
            sb.append("<p>replaced major-axis <b>LEFT</b> with <b>BEGIN</b></p>");
            fontstyle.setMajorJustify("BEGIN");
        }
        if (fontstyle.getMinorJustify().equalsIgnoreCase("LEFT"))
        {
            justifyWarningFound = true;
            sb.append("<p>replaced minor-axis <b>LEFT</b> with <b>BEGIN</b></p>");
            fontstyle.setMinorJustify("BEGIN");
        }

        if (fontstyle.getMajorJustify().equalsIgnoreCase("RIGHT"))
        {
            justifyWarningFound = true;
            sb.append("<p>replaced major-axis <b>").append(fontstyle.getMajorJustify()).append("</b> with <b>END</b></p>");
            fontstyle.setMajorJustify("END");
        }
        if (fontstyle.getMinorJustify().equalsIgnoreCase("RIGHT"))
        {
            justifyWarningFound = true;
            sb.append("<p>replaced minor-axis <b>").append(fontstyle.getMinorJustify()).append("</b> with <b>END</b></p>");
            fontstyle.setMinorJustify("END");
        }

        if (fontstyle.getMajorJustify().equalsIgnoreCase("TOP"))
        {
            justifyWarningFound = true;
            sb.append("<p>replaced major-axis <b>").append(fontstyle.getMajorJustify()).append("</b> with <b>BEGIN</b></p>");
            fontstyle.setMajorJustify("BEGIN");
        }
        if (fontstyle.getMinorJustify().equalsIgnoreCase("TOP"))
        {
            justifyWarningFound = true;
            sb.append("<p>replaced minor-axis <b>").append(fontstyle.getMinorJustify()).append("</b> with <b>BEGIN</b></p>");
            fontstyle.setMinorJustify("BEGIN");
        }

        if (fontstyle.getMajorJustify().equalsIgnoreCase("BOTTOM"))
        {
            justifyWarningFound = true;
            sb.append("<p>replaced major-axis <b>").append(fontstyle.getMajorJustify()).append("</b> with <b>END</b></p>");
            fontstyle.setMajorJustify("END");
        }
        if (fontstyle.getMinorJustify().equalsIgnoreCase("BOTTOM"))
        {
            justifyWarningFound = true;
            sb.append("<p>replaced minor-axis <b>").append(fontstyle.getMinorJustify()).append("</b> with <b>END</b></p>");
            fontstyle.setMinorJustify("END");
        }

        if (fontstyle.getMajorJustify().equalsIgnoreCase("CENTER"))
        {
            justifyWarningFound = true;
            sb.append("<p>replaced major-axis <b>").append(fontstyle.getMajorJustify()).append("</b> with <b>MIDDLE</b></p>");
            fontstyle.setMajorJustify("MIDDLE");
        }
        if (fontstyle.getMinorJustify().equalsIgnoreCase("CENTER"))
        {
            justifyWarningFound = true;
            sb.append("<p>replaced minor-axis <b>").append(fontstyle.getMinorJustify()).append("</b> with <b>MIDDLE</b></p>");
            fontstyle.setMinorJustify("MIDDLE");
        }

        if (justifyWarningFound)
        {
            NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                    sb.toString(), NotifyDescriptor.WARNING_MESSAGE);
            DialogDisplayer.getDefault().notify(descriptor);
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
        exampleLabel = new javax.swing.JLabel();
        majorJustifyLabel = new javax.swing.JLabel();
        majorJustifyCombo = new javax.swing.JComboBox<>();
        minorJustifyLabel = new javax.swing.JLabel();
        minorJustifyCombo = new javax.swing.JComboBox<>();
        familyLabel = new javax.swing.JLabel();
        familyCombo = new javax.swing.JComboBox<>();
        familyExampleLabel = new javax.swing.JLabel();
        styleLabel = new javax.swing.JLabel();
        styleCB = new javax.swing.JComboBox<>();
        styleExampleLabel = new javax.swing.JLabel();
        sizelabel = new javax.swing.JLabel();
        sizeTF = new javax.swing.JTextField();
        spacingLabel = new javax.swing.JLabel();
        spacingTF = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        internationalizationLabel = new javax.swing.JLabel();
        i18nLabel = new javax.swing.JLabel();
        countryCodesButton = new javax.swing.JButton();
        languageLabel = new javax.swing.JLabel();
        languageTF = new javax.swing.JTextField();
        languageExampleLabel = new javax.swing.JLabel();
        horizontalLabel = new javax.swing.JLabel();
        horizontalChkB = new javax.swing.JCheckBox();
        horizontalDefaultLabel = new javax.swing.JLabel();
        leftToRightLabel = new javax.swing.JLabel();
        leftToRightChkB = new javax.swing.JCheckBox();
        leftToRightDefaultLabel = new javax.swing.JLabel();
        topToBottomLabel = new javax.swing.JLabel();
        topToBottomChkB = new javax.swing.JCheckBox();
        topToBottomDefaultLabel = new javax.swing.JLabel();
        preferredLabel = new javax.swing.JLabel();
        defaultLabel = new javax.swing.JLabel();
        middleLabel2 = new javax.swing.JLabel();
        middleLabel = new javax.swing.JLabel();
        beginLabel = new javax.swing.JLabel();
        firstLabel = new javax.swing.JLabel();
        serifLabel = new javax.swing.JLabel();
        plainLabel = new javax.swing.JLabel();
        nodeHintPanel = new javax.swing.JPanel();
        hintLabel = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(650, 495));
        setLayout(new java.awt.GridBagLayout());

        dEFUSEpanel1.setMinimumSize(new java.awt.Dimension(198, 77));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel1, gridBagConstraints);

        exampleLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        exampleLabel.setText("example");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(9, 3, 3, 3);
        add(exampleLabel, gridBagConstraints);

        majorJustifyLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        majorJustifyLabel.setText("horizontal justify");
        majorJustifyLabel.setToolTipText("major axis alignment");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(majorJustifyLabel, gridBagConstraints);

        majorJustifyCombo.setModel(new DefaultComboBoxModel<String>(FONTSTYLE_ATTR_JUSTIFY_CHOICES));
        majorJustifyCombo.setToolTipText("major axis alignment");
        majorJustifyCombo.setMinimumSize(new java.awt.Dimension(100, 20));
        majorJustifyCombo.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(majorJustifyCombo, gridBagConstraints);

        minorJustifyLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        minorJustifyLabel.setText("vertical justify");
        minorJustifyLabel.setToolTipText("minor axis alignment");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(minorJustifyLabel, gridBagConstraints);

        minorJustifyCombo.setModel(new DefaultComboBoxModel<String>(FONTSTYLE_ATTR_JUSTIFY_CHOICES));
        minorJustifyCombo.setToolTipText("minor axis alignment");
        minorJustifyCombo.setMinimumSize(new java.awt.Dimension(100, 20));
        minorJustifyCombo.setPreferredSize(new java.awt.Dimension(100, 20));
        minorJustifyCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minorJustifyComboActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(minorJustifyCombo, gridBagConstraints);

        familyLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        familyLabel.setText("family");
        familyLabel.setToolTipText("select family or enter quoted font names, browsers use first supported family (e.g. \"Arial\" \"SANS\")");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(familyLabel, gridBagConstraints);

        familyCombo.setEditable(true);
        familyCombo.setModel(new DefaultComboBoxModel<String>(FONTSTYLE_ATTR_FAMILY_CHOICES));
        familyCombo.setToolTipText("select family or enter quoted font names, browsers use first supported family (e.g. \"Arial\" \"SANS\")");
        familyCombo.setMinimumSize(new java.awt.Dimension(100, 20));
        familyCombo.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(familyCombo, gridBagConstraints);

        familyExampleLabel.setText("\"Times\" \"SERIF\"");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(familyExampleLabel, gridBagConstraints);

        styleLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        styleLabel.setText("style");
        styleLabel.setToolTipText("four different style values are supported");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(styleLabel, gridBagConstraints);

        styleCB.setModel(new DefaultComboBoxModel<String>(FONTSTYLE_ATTR_STYLE_CHOICES));
        styleCB.setToolTipText("four different style values are supported");
        styleCB.setMinimumSize(new java.awt.Dimension(100, 20));
        styleCB.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(styleCB, gridBagConstraints);

        styleExampleLabel.setText("BOLDITALIC");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(styleExampleLabel, gridBagConstraints);

        sizelabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        sizelabel.setText("size");
        sizelabel.setToolTipText("Height (meters) of text glyphs plus line spacing");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(sizelabel, gridBagConstraints);

        sizeTF.setToolTipText("Height (meters) of text glyphs plus line spacing");
        sizeTF.setMinimumSize(new java.awt.Dimension(100, 20));
        sizeTF.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(sizeTF, gridBagConstraints);

        spacingLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        spacingLabel.setText("spacing");
        spacingLabel.setToolTipText("Adjustment factor for line spacing");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(spacingLabel, gridBagConstraints);

        spacingTF.setToolTipText("Adjustment factor for line spacing");
        spacingTF.setMinimumSize(new java.awt.Dimension(100, 20));
        spacingTF.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(spacingTF, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(jSeparator1, gridBagConstraints);

        internationalizationLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        internationalizationLabel.setText("Internationalization");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        add(internationalizationLabel, gridBagConstraints);

        i18nLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        i18nLabel.setText("(i18n)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        add(i18nLabel, gridBagConstraints);

        countryCodesButton.setText("country codes");
        countryCodesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                countryCodesButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(countryCodesButton, gridBagConstraints);

        languageLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        languageLabel.setText("language");
        languageLabel.setToolTipText("Primary code - optional subcode, e.g. fr-ca French (Canada) ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(languageLabel, gridBagConstraints);

        languageTF.setToolTipText("Primary code - optional subcode, e.g. fr-ca French (Canada) ");
        languageTF.setMinimumSize(new java.awt.Dimension(100, 20));
        languageTF.setPreferredSize(new java.awt.Dimension(100, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(languageTF, gridBagConstraints);

        languageExampleLabel.setText("fr-ca");
        languageExampleLabel.setToolTipText("language French, locale Canada");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(languageExampleLabel, gridBagConstraints);

        horizontalLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        horizontalLabel.setText("horizontal");
        horizontalLabel.setToolTipText("Whether text direction is horizontal (true) or vertical (false)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(horizontalLabel, gridBagConstraints);

        horizontalChkB.setToolTipText("Whether text direction is horizontal (true) or vertical (false)");
        horizontalChkB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 4, 3, 10);
        add(horizontalChkB, gridBagConstraints);

        horizontalDefaultLabel.setText("true");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(horizontalDefaultLabel, gridBagConstraints);

        leftToRightLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        leftToRightLabel.setText("leftToRight");
        leftToRightLabel.setToolTipText("Whether text direction is left-to-right (true) or right-to-left (false).");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(leftToRightLabel, gridBagConstraints);

        leftToRightChkB.setToolTipText("Whether text direction is left-to-right (true) or right-to-left (false).");
        leftToRightChkB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 4, 3, 10);
        add(leftToRightChkB, gridBagConstraints);

        leftToRightDefaultLabel.setText("true");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(leftToRightDefaultLabel, gridBagConstraints);

        topToBottomLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        topToBottomLabel.setText("topToBottom");
        topToBottomLabel.setToolTipText("Whether text direction is top-to-bottom (true) or bottom-to-top (false)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(topToBottomLabel, gridBagConstraints);

        topToBottomChkB.setToolTipText("Whether text direction is top-to-bottom (true) or bottom-to-top (false)");
        topToBottomChkB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        topToBottomChkB.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                topToBottomHandler(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 4, 3, 10);
        add(topToBottomChkB, gridBagConstraints);

        topToBottomDefaultLabel.setText("true");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(topToBottomDefaultLabel, gridBagConstraints);

        preferredLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        preferredLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        preferredLabel.setText("preferred");
        preferredLabel.setToolTipText("preferred values");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(9, 20, 3, 7);
        add(preferredLabel, gridBagConstraints);

        defaultLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        defaultLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        defaultLabel.setText("default");
        defaultLabel.setToolTipText("default X3D Specification values");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(9, 2, 3, 3);
        add(defaultLabel, gridBagConstraints);

        middleLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        middleLabel2.setText("MIDDLE");
        middleLabel2.setToolTipText("MIDDLE is usually the preferred horizontal justify value");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 20, 3, 14);
        add(middleLabel2, gridBagConstraints);

        middleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        middleLabel.setText("MIDDLE");
        middleLabel.setToolTipText("MIDDLE is usually the preferred vertical justify value");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 20, 3, 14);
        add(middleLabel, gridBagConstraints);

        beginLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        beginLabel.setText("BEGIN");
        beginLabel.setToolTipText("BEGIN is default horizontal justify value");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        add(beginLabel, gridBagConstraints);

        firstLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        firstLabel.setText("FIRST");
        firstLabel.setToolTipText("FIRST is default vertical justify value");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        add(firstLabel, gridBagConstraints);

        serifLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        serifLabel.setText("SERIF");
        serifLabel.setToolTipText("SERIF SANS and TYPEWRITER support is guaranteed");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        add(serifLabel, gridBagConstraints);

        plainLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        plainLabel.setText("PLAIN");
        plainLabel.setToolTipText("PLAIN is default style value");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        add(plainLabel, gridBagConstraints);

        nodeHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nodeHintPanel.setLayout(new java.awt.GridBagLayout());

        hintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel.setText("<html><p align='center'><b>FontStyle</b> affects the layout, language and style of its parent <b>Text</b> node.</p><p align='center'>Full internationalization (i18n) features are available for any written language.</p><p align='center'>Hint: <b>FontStyle</b> DEF/USE can help achieve consistent text styles in a scene.</p>");
        hintLabel.setToolTipText("a Text node can only contain one FontStyle node");
        hintLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        nodeHintPanel.add(hintLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(9, 3, 3, 3);
        add(nodeHintPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

  private void topToBottomHandler(java.awt.event.ActionEvent evt)//GEN-FIRST:event_topToBottomHandler
  {//GEN-HEADEREND:event_topToBottomHandler
    if(this.topToBottomChkB.isSelected()) {
      majorJustifyLabel.setText("horizontal justify");
      minorJustifyLabel.setText("vertical justify");
    }
    else {
      minorJustifyLabel.setText("horizontal justify");
      majorJustifyLabel.setText("vertical justify");
    }
  }//GEN-LAST:event_topToBottomHandler

    private void minorJustifyComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minorJustifyComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_minorJustifyComboActionPerformed

    private void countryCodesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_countryCodesButtonActionPerformed
        urlExpandableList.launchInBrowser("http://www.loc.gov/standards/iso639-2/php/code_list.php");
    }//GEN-LAST:event_countryCodesButtonActionPerformed
  
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel beginLabel;
    private javax.swing.JButton countryCodesButton;
    private org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel1;
    private javax.swing.JLabel defaultLabel;
    private javax.swing.JLabel exampleLabel;
    private javax.swing.JComboBox<String> familyCombo;
    private javax.swing.JLabel familyExampleLabel;
    private javax.swing.JLabel familyLabel;
    private javax.swing.JLabel firstLabel;
    private javax.swing.JLabel hintLabel;
    private javax.swing.JCheckBox horizontalChkB;
    private javax.swing.JLabel horizontalDefaultLabel;
    private javax.swing.JLabel horizontalLabel;
    private javax.swing.JLabel i18nLabel;
    private javax.swing.JLabel internationalizationLabel;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel languageExampleLabel;
    private javax.swing.JLabel languageLabel;
    private javax.swing.JTextField languageTF;
    private javax.swing.JCheckBox leftToRightChkB;
    private javax.swing.JLabel leftToRightDefaultLabel;
    private javax.swing.JLabel leftToRightLabel;
    private javax.swing.JComboBox<String> majorJustifyCombo;
    private javax.swing.JLabel majorJustifyLabel;
    private javax.swing.JLabel middleLabel;
    private javax.swing.JLabel middleLabel2;
    private javax.swing.JComboBox<String> minorJustifyCombo;
    private javax.swing.JLabel minorJustifyLabel;
    private javax.swing.JPanel nodeHintPanel;
    private javax.swing.JLabel plainLabel;
    private javax.swing.JLabel preferredLabel;
    private javax.swing.JLabel serifLabel;
    private javax.swing.JTextField sizeTF;
    private javax.swing.JLabel sizelabel;
    private javax.swing.JLabel spacingLabel;
    private javax.swing.JTextField spacingTF;
    private javax.swing.JComboBox<String> styleCB;
    private javax.swing.JLabel styleExampleLabel;
    private javax.swing.JLabel styleLabel;
    private javax.swing.JCheckBox topToBottomChkB;
    private javax.swing.JLabel topToBottomDefaultLabel;
    private javax.swing.JLabel topToBottomLabel;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_FONTSTYLE";
  }

  @Override
  public void unloadInput() throws IllegalArgumentException
  {
    unLoadDEFUSE();
    
    // if family has multiple string values, ensure they are quoted properly as MFString array regardless of whether user got the quotes correct
    StringBuilder familyMFString = new StringBuilder();
    // normalize space, prepare for " insertion
    String       familyText     = ((String)familyCombo.getSelectedItem()).replace("\" \"", " ").replace("\"", "").trim();
    if (familyText.length() > 0)
    {
        familyMFString.append("\""); // initial "
        for (int i=0; i < familyText.length(); i++)
        {
            if (familyText.charAt(i) == ' ')
            {
                // check for no follow-on space character
                if (i+1 < familyText.length()) // avoid overreaching end of array
                {
                    // insert quotes to split individual string values
                    if (familyText.charAt(i+1) != ' ') familyMFString.append("\" \"");
                    // otherwise skip this extra space
                }
                // otherwise skip this final space
            }
            else familyMFString.append(familyText.charAt(i));
        }
        familyMFString.append("\""); // final "
    }
    // trace System.out.println ("familyText=" + familyText + ", familyMFString=" + familyMFString);
    fontstyle.setFamily(familyMFString.toString());
    
    fontstyle.setMajorJustify((String)majorJustifyCombo.getSelectedItem());
    fontstyle.setMinorJustify((String)minorJustifyCombo.getSelectedItem());
    fontstyle.setStyle((String)styleCB.getSelectedItem());
    fontstyle.setLanguage(languageTF.getText().trim());
    fontstyle.setHorizontal(horizontalChkB.isSelected());
    fontstyle.setLeftToRight(leftToRightChkB.isSelected());
    fontstyle.setTopToBottom(topToBottomChkB.isSelected());
    fontstyle.setSize(sizeTF.getText().trim());
    fontstyle.setSpacing(spacingTF.getText().trim());
  }
  
}
