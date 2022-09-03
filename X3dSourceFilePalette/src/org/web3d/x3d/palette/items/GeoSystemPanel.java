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
 *
 * GeoSystemPanel.java
 *
 * Created on April 23, 2008, 3:35 PM
 */
package org.web3d.x3d.palette.items;

import java.util.Arrays;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComponent;
import javax.swing.SpinnerNumberModel;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFInt32;
import static org.web3d.x3d.types.X3DSchemaData.GEO_EARTH_ELLIPSOIDS;
import static org.web3d.x3d.types.X3DSchemaData.GEO_EARTH_GEOIDS;

/**
 *
 * @author  Mike Bailey <jmbailey@nps.edu>
 */
public class GeoSystemPanel extends javax.swing.JPanel
{
  private final JComponent[] gdComponents;
  private final JComponent[] utmComponents;
  private final JComponent[] gcComponents;
  private final Vector<String> ellipsoids;
  private final Vector<String> geoids;
  private final String defaultEllipsoid = "default \"WE\" (WGS84)";
  private final String defaultGeoid     = "default WGS84";
  private final String utmWikipediaLink = "https://en.wikipedia.org/wiki/Universal_Transverse_Mercator_coordinate_system";

  public GeoSystemPanel(String data)
  {
    initComponents();

    ellipsoids = new Vector<>(Arrays.asList(GEO_EARTH_ELLIPSOIDS));
        geoids = new Vector<>(Arrays.asList(GEO_EARTH_GEOIDS));
    ellipsoids.add(0, defaultEllipsoid);
        geoids.add(0, defaultGeoid);

     gdEllipsoidCombobox.setModel(new DefaultComboBoxModel<>(ellipsoids));
    utmEllipsoidCombobox.setModel(new DefaultComboBoxModel<>(ellipsoids));
        utmGeoidCombobox.setModel(new DefaultComboBoxModel<>(geoids));
    
     gdEllipsoidCombobox.setSelectedIndex(0);
    utmEllipsoidCombobox.setSelectedIndex(0);
        utmGeoidCombobox.setSelectedIndex(0);

    gdComponents  = new JComponent[]{gdEllipsoidLabel, gdEllipsoidCombobox};
    gcComponents  = new JComponent[]{};
    utmComponents = new JComponent[]{utmGeoidCombobox, utmGeoidLabel, utmSouthCheckbox, southLabel, utmEllipsoidCombobox, utmEllipsoidLabel, zoneLabel, utmZoneSpinner};

    parseData(data);
    geoSystemTextField.setText(getGeosytemValue());
  }

  public final String getGeosytemValue()
  {
    StringBuilder sb = new StringBuilder();
    if (gdRadioButton.isSelected()) 
    {
      sb.append("\"GD\"");
      String s = (String) gdEllipsoidCombobox.getSelectedItem();
      if (!s.equals(defaultEllipsoid)) {
        sb.append(" ");
        sb.append(s.substring(0,2)); // only first two characters of annotated entry
      }
      else sb.append(" \"WE\"");
      
      return sb.toString();
    }
    else if (utmRadioButton.isSelected())
    {
      sb.append("\"UTM\" \"Z");
      sb.append(utmZoneSpinner.getValue()); // toString() will work?
      sb.append("\"");

      if (utmSouthCheckbox.isSelected())
        sb.append(" \"S\"");

      String s = (String) utmEllipsoidCombobox.getSelectedItem();
      if (!s.equals(defaultEllipsoid)) {
        sb.append(" ");
        sb.append(s.substring(0,2)); // only first two characters of annotated entry
      }

      s = (String) this.utmGeoidCombobox.getSelectedItem();
      if (!s.equals(defaultGeoid)) {
        sb.append(" ");
        sb.append(s.substring(0,5)); // only first five characters of annotated entry:  WGS85
      }
      return sb.toString();
    }
    else if (gcRadioButton.isSelected())
    {
      return "\"GC\"";
    }
    return "";
  }

  private void parseData(String s)
  {
    resetWidgets();
    s = s.replace('\'', ' ');
    s = s.replace('"',  ' ');
    String[] sa = s.replace(',', ' ').trim().split("\\s+");

    if (sa.length >= 1) {
      if (sa[0].equalsIgnoreCase("GD"))
        parseGD(sa);
      else if (sa[0].equalsIgnoreCase("UTM"))
        parseUTM(sa);
      else if (sa[0].equalsIgnoreCase("GC"))
        parseGC(sa);
    }
  }

  private void resetWidgets()
  {
    gdEllipsoidCombobox.setSelectedIndex(0);

    utmSouthCheckbox.setSelected(false);
//test    utmZoneSpinner.setValue(0);
    utmEllipsoidCombobox.setSelectedIndex(0);
    utmGeoidCombobox.setSelectedIndex(0);
  }

  private void parseGD(String[] sa)
  {
    if (sa.length >= 2)
      if (ellipsoids.contains(sa[1].toUpperCase()))
        gdEllipsoidCombobox.setSelectedItem(sa[1].toUpperCase());

    gdRadioButton.doClick();
  }

  private void parseUTM(String[] sa)
  {
    if (sa.length <= 1)
      return;

    for (int i = 1; i < sa.length; i++) {
      if (sa[i].equalsIgnoreCase("S")) {
        this.utmSouthCheckbox.setSelected(true);
      }
      else if (sa[i].charAt(0) == 'Z' || sa[i].charAt(0) == 'z') {
        try {
          Integer zInt = new SFInt32(sa[1].substring(1)).getValue();
          utmZoneSpinner.setValue(zInt);
        }
        catch (Throwable t) {

        }
      }
      else if (ellipsoids.contains(sa[i].toUpperCase())) {
        utmEllipsoidCombobox.setSelectedItem(sa[i].toUpperCase());
      }
      else if (geoids.contains(sa[i].toUpperCase())) {
        utmGeoidCombobox.setSelectedItem(sa[i].toUpperCase());
      }
    }
    utmRadioButton.doClick();
  }

  private void parseGC(String[] sa)
  {
    gcRadioButton.doClick();
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        buttonGroup1 = new javax.swing.ButtonGroup();
        geoSystemPanel = new javax.swing.JPanel();
        gdRadioButton = new javax.swing.JRadioButton();
        gdEllipsoidLabel = new javax.swing.JLabel();
        gdEllipsoidCombobox = new javax.swing.JComboBox<String>();
        jSeparator1 = new javax.swing.JSeparator();
        gcRadioButton = new javax.swing.JRadioButton();
        jSeparator2 = new javax.swing.JSeparator();
        utmRadioButton = new javax.swing.JRadioButton();
        utmDiagramLabel = new javax.swing.JLabel();
        zoneLabel = new javax.swing.JLabel();
        utmZoneSpinner = new javax.swing.JSpinner(new SpinnerNumberModel(1,1,60,1));
        southLabel = new javax.swing.JLabel();
        utmSouthCheckbox = new javax.swing.JCheckBox();
        utmEllipsoidLabel = new javax.swing.JLabel();
        utmEllipsoidCombobox = new javax.swing.JComboBox<String>();
        utmGeoidLabel = new javax.swing.JLabel();
        utmGeoidCombobox = new javax.swing.JComboBox<String>();
        jSeparator3 = new javax.swing.JSeparator();
        geoSystemTextField = new javax.swing.JTextField();
        geoSystemLabel = new javax.swing.JLabel();
        jSeparator4 = new javax.swing.JSeparator();
        nodeHintPanel = new javax.swing.JPanel();
        hintLabel = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        geoSystemPanel.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        geoSystemPanel.setToolTipText(org.openide.util.NbBundle.getMessage(GeoSystemPanel.class, "GeoSystemPanel.utmRadioButton.toolTipText")); // NOI18N
        geoSystemPanel.setLayout(new java.awt.GridBagLayout());

        buttonGroup1.add(gdRadioButton);
        gdRadioButton.setText(org.openide.util.NbBundle.getMessage(GeoSystemPanel.class, "GeoSystemPanel.gdRadioButton.text")); // NOI18N
        gdRadioButton.setToolTipText(org.openide.util.NbBundle.getMessage(GeoSystemPanel.class, "GeoSystemPanel.gdRadioButton.toolTipText")); // NOI18N
        gdRadioButton.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        gdRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gdRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 15, 0);
        geoSystemPanel.add(gdRadioButton, gridBagConstraints);

        gdEllipsoidLabel.setText(org.openide.util.NbBundle.getMessage(GeoSystemPanel.class, "GeoSystemPanel.gdEllipsoidLabel.text")); // NOI18N
        gdEllipsoidLabel.setToolTipText(org.openide.util.NbBundle.getMessage(GeoSystemPanel.class, "GeoSystemPanel.gdEllipsoidLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 3, 15, 0);
        geoSystemPanel.add(gdEllipsoidLabel, gridBagConstraints);

        gdEllipsoidCombobox.setEditable(true);
        gdEllipsoidCombobox.setToolTipText(org.openide.util.NbBundle.getMessage(GeoSystemPanel.class, "GeoSystemPanel.gdEllipsoidLabel.toolTipText")); // NOI18N
        gdEllipsoidCombobox.setMinimumSize(null);
        gdEllipsoidCombobox.setPreferredSize(null);
        gdEllipsoidCombobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gdEllipsoidComboboxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 3, 15, 0);
        geoSystemPanel.add(gdEllipsoidCombobox, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        geoSystemPanel.add(jSeparator1, gridBagConstraints);

        buttonGroup1.add(gcRadioButton);
        gcRadioButton.setSelected(true);
        gcRadioButton.setText(org.openide.util.NbBundle.getMessage(GeoSystemPanel.class, "GeoSystemPanel.gcRadioButton.text")); // NOI18N
        gcRadioButton.setToolTipText(org.openide.util.NbBundle.getMessage(GeoSystemPanel.class, "GeoSystemPanel.gcRadioButton.toolTipText_1")); // NOI18N
        gcRadioButton.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        gcRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gcRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 15, 15, 0);
        geoSystemPanel.add(gcRadioButton, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        geoSystemPanel.add(jSeparator2, gridBagConstraints);

        buttonGroup1.add(utmRadioButton);
        utmRadioButton.setText(org.openide.util.NbBundle.getMessage(GeoSystemPanel.class, "GeoSystemPanel.utmRadioButton.text")); // NOI18N
        utmRadioButton.setToolTipText(org.openide.util.NbBundle.getMessage(GeoSystemPanel.class, "GeoSystemPanel.utmRadioButton.toolTipText")); // NOI18N
        utmRadioButton.setHorizontalTextPosition(javax.swing.SwingConstants.LEADING);
        utmRadioButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                utmRadioButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(15, 12, 0, 0);
        geoSystemPanel.add(utmRadioButton, gridBagConstraints);

        utmDiagramLabel.setBackground(new java.awt.Color(255, 255, 255));
        utmDiagramLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        utmDiagramLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/org/web3d/x3d/palette/items/resources/Utm-zones384x192.jpg"))); // NOI18N
        utmDiagramLabel.setText(org.openide.util.NbBundle.getMessage(GeoSystemPanel.class, "GeoSystemPanel.utmDiagramLabel.text")); // NOI18N
        utmDiagramLabel.setToolTipText(org.openide.util.NbBundle.getMessage(GeoSystemPanel.class, "GeoSystemPanel.utmDiagramLabel.toolTipText")); // NOI18N
        utmDiagramLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        utmDiagramLabel.setMaximumSize(null);
        utmDiagramLabel.setMinimumSize(null);
        utmDiagramLabel.setPreferredSize(null);
        utmDiagramLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                utmDiagramLabelMouseClicked(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(13, 3, 3, 3);
        geoSystemPanel.add(utmDiagramLabel, gridBagConstraints);

        zoneLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        zoneLabel.setText(org.openide.util.NbBundle.getMessage(GeoSystemPanel.class, "GeoSystemPanel.zoneLabel.text")); // NOI18N
        zoneLabel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                zoneLabelFocusGained(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.ipadx = 14;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        geoSystemPanel.add(zoneLabel, gridBagConstraints);

        utmZoneSpinner.setToolTipText(org.openide.util.NbBundle.getMessage(GeoSystemPanel.class, "GeoSystemPanel.utmZoneSpinner.toolTipText")); // NOI18N
        utmZoneSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                utmZoneSpinnerStateChanged(evt);
            }
        });
        utmZoneSpinner.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                utmZoneSpinnerFocusGained(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 23;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(13, 4, 0, 0);
        geoSystemPanel.add(utmZoneSpinner, gridBagConstraints);

        southLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        southLabel.setText(org.openide.util.NbBundle.getMessage(GeoSystemPanel.class, "GeoSystemPanel.southLabel.text")); // NOI18N
        southLabel.setToolTipText(org.openide.util.NbBundle.getMessage(GeoSystemPanel.class, "GeoSystemPanel.southLabel.toolTipText")); // NOI18N
        southLabel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                southLabelFocusGained(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(11, 0, 0, 0);
        geoSystemPanel.add(southLabel, gridBagConstraints);

        utmSouthCheckbox.setText(org.openide.util.NbBundle.getMessage(GeoSystemPanel.class, "GeoSystemPanel.utmSouthCheckbox.text")); // NOI18N
        utmSouthCheckbox.setToolTipText(org.openide.util.NbBundle.getMessage(GeoSystemPanel.class, "GeoSystemPanel.utmSouthCheckbox.toolTipText")); // NOI18N
        utmSouthCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                utmSouthCheckboxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(8, 0, 0, 0);
        geoSystemPanel.add(utmSouthCheckbox, gridBagConstraints);

        utmEllipsoidLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        utmEllipsoidLabel.setText(org.openide.util.NbBundle.getMessage(GeoSystemPanel.class, "GeoSystemPanel.utmEllipsoidLabel.text")); // NOI18N
        utmEllipsoidLabel.setToolTipText(org.openide.util.NbBundle.getMessage(GeoSystemPanel.class, "GeoSystemPanel.utmEllipsoidLabel.toolTipText")); // NOI18N
        utmEllipsoidLabel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                utmEllipsoidLabelFocusGained(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 0);
        geoSystemPanel.add(utmEllipsoidLabel, gridBagConstraints);

        utmEllipsoidCombobox.setEditable(true);
        utmEllipsoidCombobox.setToolTipText(org.openide.util.NbBundle.getMessage(GeoSystemPanel.class, "GeoSystemPanel.utmEllipsoidCombobox.toolTipText")); // NOI18N
        utmEllipsoidCombobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                utmEllipsoidComboboxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 4, 0, 0);
        geoSystemPanel.add(utmEllipsoidCombobox, gridBagConstraints);

        utmGeoidLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        utmGeoidLabel.setText(org.openide.util.NbBundle.getMessage(GeoSystemPanel.class, "GeoSystemPanel.utmGeoidLabel.text")); // NOI18N
        utmGeoidLabel.setToolTipText(org.openide.util.NbBundle.getMessage(GeoSystemPanel.class, "GeoSystemPanel.utmGeoidCombobox.toolTipText")); // NOI18N
        utmGeoidLabel.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                utmGeoidLabelFocusGained(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.ipadx = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 0);
        geoSystemPanel.add(utmGeoidLabel, gridBagConstraints);

        utmGeoidCombobox.setEditable(true);
        utmGeoidCombobox.setToolTipText(org.openide.util.NbBundle.getMessage(GeoSystemPanel.class, "GeoSystemPanel.utmGeoidCombobox.toolTipText")); // NOI18N
        utmGeoidCombobox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                utmGeoidComboboxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 4, 0, 0);
        geoSystemPanel.add(utmGeoidCombobox, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 2, 0);
        geoSystemPanel.add(jSeparator3, gridBagConstraints);

        geoSystemTextField.setEditable(false);
        geoSystemTextField.setText(org.openide.util.NbBundle.getMessage(GeoSystemPanel.class, "GeoSystemPanel.geoSystemTextField.text")); // NOI18N
        geoSystemTextField.setToolTipText(org.openide.util.NbBundle.getMessage(GeoSystemPanel.class, "GeoSystemPanel.geoSystemTextField.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geoSystemPanel.add(geoSystemTextField, gridBagConstraints);

        geoSystemLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        geoSystemLabel.setText(org.openide.util.NbBundle.getMessage(GeoSystemPanel.class, "GeoSystemPanel.geoSystemLabel.text")); // NOI18N
        geoSystemLabel.setToolTipText(org.openide.util.NbBundle.getMessage(GeoSystemPanel.class, "GeoSystemPanel.geoSystemLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.ipadx = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geoSystemPanel.add(geoSystemLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(2, 0, 3, 0);
        geoSystemPanel.add(jSeparator4, gridBagConstraints);

        nodeHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nodeHintPanel.setLayout(new java.awt.GridBagLayout());

        hintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel.setText(org.openide.util.NbBundle.getMessage(GeoSystemPanel.class, "GeoSystemPanel.hintLabel.text")); // NOI18N
        hintLabel.setToolTipText(org.openide.util.NbBundle.getMessage(GeoSystemPanel.class, "GeoSystemPanel.hintLabel.toolTipText")); // NOI18N
        hintLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        nodeHintPanel.add(hintLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        geoSystemPanel.add(nodeHintPanel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(geoSystemPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents
  private void gdRadioButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_gdRadioButtonActionPerformed
  {//GEN-HEADEREND:event_gdRadioButtonActionPerformed
    enableForGD(gdRadioButton.isSelected());
    geoSystemTextField.setText(getGeosytemValue());
}//GEN-LAST:event_gdRadioButtonActionPerformed

  private void utmRadioButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_utmRadioButtonActionPerformed
  {//GEN-HEADEREND:event_utmRadioButtonActionPerformed
    enableForUTM(utmRadioButton.isSelected());
    geoSystemTextField.setText(getGeosytemValue());
  }//GEN-LAST:event_utmRadioButtonActionPerformed

  private void gcRadioButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_gcRadioButtonActionPerformed
  {//GEN-HEADEREND:event_gcRadioButtonActionPerformed
    enableForGC(gcRadioButton.isSelected());
    geoSystemTextField.setText(getGeosytemValue());
  }//GEN-LAST:event_gcRadioButtonActionPerformed

    private void gdEllipsoidComboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gdEllipsoidComboboxActionPerformed
        gdRadioButton.setSelected(true);
    geoSystemTextField.setText(getGeosytemValue());
    }//GEN-LAST:event_gdEllipsoidComboboxActionPerformed

    private void utmEllipsoidComboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_utmEllipsoidComboboxActionPerformed
        utmRadioButton.setSelected(true);
    geoSystemTextField.setText(getGeosytemValue());
    }//GEN-LAST:event_utmEllipsoidComboboxActionPerformed

    private void utmGeoidComboboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_utmGeoidComboboxActionPerformed
        utmRadioButton.setSelected(true);
    geoSystemTextField.setText(getGeosytemValue());
    }//GEN-LAST:event_utmGeoidComboboxActionPerformed

    private void utmSouthCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_utmSouthCheckboxActionPerformed
        utmRadioButton.setSelected(true);
    geoSystemTextField.setText(getGeosytemValue());
    }//GEN-LAST:event_utmSouthCheckboxActionPerformed

    private void utmZoneSpinnerFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_utmZoneSpinnerFocusGained
       utmRadioButton.setSelected(true);
    geoSystemTextField.setText(getGeosytemValue());
    }//GEN-LAST:event_utmZoneSpinnerFocusGained

    private void zoneLabelFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_zoneLabelFocusGained
        utmRadioButton.setSelected(true);
    geoSystemTextField.setText(getGeosytemValue());
    }//GEN-LAST:event_zoneLabelFocusGained

    private void southLabelFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_southLabelFocusGained
        utmRadioButton.setSelected(true);
    geoSystemTextField.setText(getGeosytemValue());
    }//GEN-LAST:event_southLabelFocusGained

    private void utmEllipsoidLabelFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_utmEllipsoidLabelFocusGained
        utmRadioButton.setSelected(true);
    geoSystemTextField.setText(getGeosytemValue());
    }//GEN-LAST:event_utmEllipsoidLabelFocusGained

    private void utmGeoidLabelFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_utmGeoidLabelFocusGained
        utmRadioButton.setSelected(true);
    geoSystemTextField.setText(getGeosytemValue());
    }//GEN-LAST:event_utmGeoidLabelFocusGained

    private void utmDiagramLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_utmDiagramLabelMouseClicked
        UrlExpandableList2.launchInBrowser(utmWikipediaLink);
    }//GEN-LAST:event_utmDiagramLabelMouseClicked

    private void utmZoneSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_utmZoneSpinnerStateChanged
    geoSystemTextField.setText(getGeosytemValue());
    }//GEN-LAST:event_utmZoneSpinnerStateChanged

  private void enableForGD(boolean selected)
  {
    if (selected) {
      enableForUTM(false);
      enableForGC(false);
    }
    enableComponents(gdComponents, selected);
  }

  private void enableForUTM(boolean selected)
  {
    if (selected) {
      enableForGD(false);
      enableForGC(false);
    }
    enableComponents(utmComponents, selected);
  }

  private void enableForGC(boolean selected)
  {
    if (selected) {
      enableForGD(false);
      enableForUTM(false);
    }
    enableComponents(gcComponents, selected);
  }

  private void enableComponents(JComponent[] ca, boolean wh)
  {
    for (JComponent c : ca)
      c.setEnabled(wh);
  }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JRadioButton gcRadioButton;
    private javax.swing.JComboBox<String> gdEllipsoidCombobox;
    private javax.swing.JLabel gdEllipsoidLabel;
    private javax.swing.JRadioButton gdRadioButton;
    private javax.swing.JLabel geoSystemLabel;
    private javax.swing.JPanel geoSystemPanel;
    private javax.swing.JTextField geoSystemTextField;
    private javax.swing.JLabel hintLabel;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JPanel nodeHintPanel;
    private javax.swing.JLabel southLabel;
    private javax.swing.JLabel utmDiagramLabel;
    private javax.swing.JComboBox<String> utmEllipsoidCombobox;
    private javax.swing.JLabel utmEllipsoidLabel;
    private javax.swing.JComboBox<String> utmGeoidCombobox;
    private javax.swing.JLabel utmGeoidLabel;
    private javax.swing.JRadioButton utmRadioButton;
    private javax.swing.JCheckBox utmSouthCheckbox;
    private javax.swing.JSpinner utmZoneSpinner;
    private javax.swing.JLabel zoneLabel;
    // End of variables declaration//GEN-END:variables
}
