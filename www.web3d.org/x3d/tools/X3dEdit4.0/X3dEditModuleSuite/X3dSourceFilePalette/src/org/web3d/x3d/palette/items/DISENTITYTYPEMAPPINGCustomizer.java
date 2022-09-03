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

import edu.nps.moves.dis7.enumerations.*;

import javax.swing.text.JTextComponent;

import org.openide.util.HelpCtx;
import org.web3d.x3d.X3DDataObject;
import org.web3d.x3d.dis.DisUtils;

/**
 * DISENTITYTYPEMAPPINGCustomizer.java
 * Created on 23 Apr 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class DISENTITYTYPEMAPPINGCustomizer extends BaseCustomizer
{
  private final DISENTITYTYPEMAPPING disEntityTypeMapping;
  private final JTextComponent target;
  private final X3DDataObject xObj;

  public DISENTITYTYPEMAPPINGCustomizer(DISENTITYTYPEMAPPING disEntityTypeMapping, JTextComponent target, X3DDataObject xObj)
  {
    super(disEntityTypeMapping);
    this.disEntityTypeMapping = disEntityTypeMapping;
    this.target = target;
    this.xObj = xObj;
    
    HelpCtx.setHelpIDString(this, "DISENTITYTYPEMAPPING_ELEM_HELPID");
    
    initComponents();

    adjustComboSize(countryCombo,"OTHER");

    urlList.setMasterDocumentLocation(xObj.getPrimaryFile());
    urlList.setUrlData   (disEntityTypeMapping.getUrls());
    urlList.setFileChooserText();
    urlList.setTarget(target); // enable urlList to reach back into jdom tree to getHeaderIdentifierPath()
    urlList.checkUrlValues();

    // TODO customized category?  ExpendableAirCategory ExpendableSubsurfaceCategory ExpendableSurfaceCategory GroupedEntityCategory MunitionCategory RadioCategory SensorEmitterCategory
    categoryTF.setText   (disEntityTypeMapping.getCategory());

    // alternatively AFGHANISTAN which is first in sorted list:
    countryCombo.setSelectedItem(DisUtils.getDisEnum(Country.UNITED_STATES_OF_AMERICA_USA,disEntityTypeMapping.getCountry()));

    domainCombo.setSelectedItem(DisUtils.getDisEnum(PlatformDomain.AIR,disEntityTypeMapping.getDomain()));
    extraTF.setText      (disEntityTypeMapping.getExtra());
    kindCombo.setSelectedItem(DisUtils.getDisEnum(EntityKind.PLATFORM,disEntityTypeMapping.getKind()));
    specificCombo.setSelectedItem(DisUtils.getDisEnum(AggregateStateSpecific.NO_HEADQUARTERS,disEntityTypeMapping.getSpecific()));
    subcategoryCombo.setSelectedItem(DisUtils.getDisEnum(AggregateStateSubcategory.OTHER,disEntityTypeMapping.getSubcategory()));

    setDefaultDEFname ();
  }
  private void setDefaultDEFname ()
  {
	if ((urlList == null) || (urlList.getUrlData() == null) || (urlList.getUrlData().length == 0))
	{
		super.getDEFUSEpanel().setDefaultDEFname("New" + "DISEntityTypeMapping");
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
        fileName += "DisEntityTypeMapping"; // otherwise empty
    
    super.getDEFUSEpanel().setDefaultDEFname(fileName);
   }
   
  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        dEFUSEpanel1 = getDEFUSEpanel();
        urlLabel = new javax.swing.JLabel();
        urlDescription = new javax.swing.JLabel();
        categoryLabel = new javax.swing.JLabel();
        categoryTF = new javax.swing.JTextField(3);
        countryLabel = new javax.swing.JLabel();
        countryCombo = new javax.swing.JComboBox<String>();
        domainLabel = new javax.swing.JLabel();
        domainCombo = new javax.swing.JComboBox();
        extraLabel = new javax.swing.JLabel();
        extraTF = new javax.swing.JTextField();
        kindLabel = new javax.swing.JLabel();
        kindCombo = new javax.swing.JComboBox();
        specificLabel = new javax.swing.JLabel();
        specificCombo = new javax.swing.JComboBox();
        subcategoryLabel = new javax.swing.JLabel();
        subcategoryCombo = new javax.swing.JComboBox<Object>();
        spacerLabel1 = new javax.swing.JLabel();
        spacerLabel2 = new javax.swing.JLabel();
        urlList = new org.web3d.x3d.palette.items.UrlExpandableList2();
        nodeHintPanel = new javax.swing.JPanel();
        hintLabel = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 44;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel1, gridBagConstraints);

        urlLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        urlLabel.setText("url");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(8, 6, 3, 3);
        add(urlLabel, gridBagConstraints);

        urlDescription.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        urlDescription.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        urlDescription.setText("url array provides ordered list for X3D model to use if this DIS EntityType is matched");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(8, 3, 3, 3);
        add(urlDescription, gridBagConstraints);

        categoryLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        categoryLabel.setText("category");
        categoryLabel.setToolTipText("Enumeration value for main category that describes the entity, semantics of each code varies according to domain");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(categoryLabel, gridBagConstraints);

        categoryTF.setToolTipText("Enumeration value for main category that describes the entity, semantics of each code varies according to domain");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(categoryTF, gridBagConstraints);

        countryLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        countryLabel.setText("country");
        countryLabel.setToolTipText("Enumeration value for country to which the design of the entity or its design specification is attributed");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(countryLabel, gridBagConstraints);

        countryCombo.setModel(new javax.swing.DefaultComboBoxModel(Country.values()));
        countryCombo.setToolTipText("Enumeration value for country to which the design of the entity or its design specification is attributed");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(countryCombo, gridBagConstraints);

        domainLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        domainLabel.setText("domain");
        domainLabel.setToolTipText("Enumeration value for domain in which the entity operates: LAND, AIR, SURFACE, SUBSURFACE, SPACE or OTHER ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(domainLabel, gridBagConstraints);

        domainCombo.setModel(new javax.swing.DefaultComboBoxModel(PlatformDomain.values()));
        domainCombo.setToolTipText("Enumeration value for domain in which the entity operates: LAND, AIR, SURFACE, SUBSURFACE, SPACE or OTHER ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(domainCombo, gridBagConstraints);

        extraLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        extraLabel.setText("extra");
        extraLabel.setToolTipText("Any extra information required to describe a particular entity. The contents of this field shall depend on the type of entity represented.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(extraLabel, gridBagConstraints);

        extraTF.setToolTipText("Any extra information required to describe a particular entity. The contents of this field shall depend on the type of entity represented.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(extraTF, gridBagConstraints);

        kindLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        kindLabel.setText("kind");
        kindLabel.setToolTipText("Enumeration value for whether entity is a PLATFORM, MUNITION, LIFE_FORM, ENVIRONMENTAL, CULTURAL_FEATURE, SUPPLY, RADIO, EXPENDABLE, SENSOR_EMITTER or OTHER");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(kindLabel, gridBagConstraints);

        kindCombo.setModel(new javax.swing.DefaultComboBoxModel(EntityKind.values()));
        kindCombo.setToolTipText("Enumeration value for whether entity is a PLATFORM, MUNITION, LIFE_FORM, ENVIRONMENTAL, CULTURAL_FEATURE, SUPPLY, RADIO, EXPENDABLE, SENSOR_EMITTER or OTHER");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(kindCombo, gridBagConstraints);

        specificLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        specificLabel.setText("specific");
        specificLabel.setToolTipText("Specific information about an entity based on the Subcategory field. See DIS Enumerations values.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(specificLabel, gridBagConstraints);

        specificCombo.setModel(new javax.swing.DefaultComboBoxModel(AggregateStateSpecific.values()));
        specificCombo.setToolTipText("Specific information about an entity based on the Subcategory field. See DIS Enumerations values.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(specificCombo, gridBagConstraints);

        subcategoryLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        subcategoryLabel.setText("subcategory");
        subcategoryLabel.setToolTipText("Enumeration value for particular subcategory to which an entity belongs based on the Category field. See DIS Enumerations values.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 0.1;
        gridBagConstraints.insets = new java.awt.Insets(3, 33, 3, 3);
        add(subcategoryLabel, gridBagConstraints);

        subcategoryCombo.setModel(new javax.swing.DefaultComboBoxModel(AggregateStateSubcategory.values()));
        subcategoryCombo.setToolTipText("Enumeration value for particular subcategory to which an entity belongs based on the Category field. See DIS Enumerations values.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(subcategoryCombo, gridBagConstraints);

        spacerLabel1.setText("                                                                                      ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.7;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(spacerLabel1, gridBagConstraints);

        spacerLabel2.setText("                                                                                      ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.7;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(spacerLabel2, gridBagConstraints);

        urlList.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        urlList.setPreferredSize(new java.awt.Dimension(249, 85));
        urlList.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                urlListPropertyChange(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 240;
        gridBagConstraints.ipady = 60;
        gridBagConstraints.weightx = 0.8;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(urlList, gridBagConstraints);

        nodeHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nodeHintPanel.setLayout(new java.awt.GridBagLayout());

        hintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel.setText("<html><p align='center'><b>DISEntityTypeMapping</b> provides a best-match mapping from DIS ESPDU entity type information to a</p>\n<p align='center'> specific X3D model, thus providing a visual and behavioral representation that best matches the entity type.</p> \n<p align='center'>Parent node <b>DisEntityManager</b> ESPDU packets use the IEEE Distributed Interactive Simulation (DIS) protocol.</p>\n");
        hintLabel.setToolTipText("This X3D node supports the IEEE Distributed Interactive Simulation (DIS) protocol.");
        hintLabel.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        nodeHintPanel.add(hintLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nodeHintPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void urlListPropertyChange(java.beans.PropertyChangeEvent evt)//GEN-FIRST:event_urlListPropertyChange
    {//GEN-HEADEREND:event_urlListPropertyChange
        setDefaultDEFname ();
    }//GEN-LAST:event_urlListPropertyChange
  
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel categoryLabel;
    private javax.swing.JTextField categoryTF;
    private javax.swing.JComboBox<String> countryCombo;
    private javax.swing.JLabel countryLabel;
    private DEFUSEpanel dEFUSEpanel1;
    private javax.swing.JComboBox domainCombo;
    private javax.swing.JLabel domainLabel;
    private javax.swing.JLabel extraLabel;
    private javax.swing.JTextField extraTF;
    private javax.swing.JLabel hintLabel;
    private javax.swing.JComboBox kindCombo;
    private javax.swing.JLabel kindLabel;
    private javax.swing.JPanel nodeHintPanel;
    private javax.swing.JLabel spacerLabel1;
    private javax.swing.JLabel spacerLabel2;
    private javax.swing.JComboBox specificCombo;
    private javax.swing.JLabel specificLabel;
    private javax.swing.JComboBox<Object> subcategoryCombo;
    private javax.swing.JLabel subcategoryLabel;
    private javax.swing.JLabel urlDescription;
    private javax.swing.JLabel urlLabel;
    private UrlExpandableList2 urlList;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_DISENTITYTYPEMAPPING";
  }

  @Override
  public void unloadInput()
  {
    unLoadDEFUSE();
    urlList.checkUrlValues();
    
    disEntityTypeMapping.setUrls(urlList.getUrlData());
    disEntityTypeMapping.setCategory(categoryTF.getText().trim());
    disEntityTypeMapping.setCountry(DisUtils.intStringFromDisEnum(countryCombo.getSelectedItem()));
    disEntityTypeMapping.setDomain(DisUtils.intStringFromDisEnum(domainCombo.getSelectedItem()));
    disEntityTypeMapping.setExtra(extraTF.getText().trim());
    disEntityTypeMapping.setKind(DisUtils.intStringFromDisEnum(kindCombo.getSelectedItem()));
    disEntityTypeMapping.setSpecific(DisUtils.intStringFromDisEnum(specificCombo.getSelectedItem()));
    disEntityTypeMapping.setSubcategory(DisUtils.intStringFromDisEnum(subcategoryCombo.getSelectedItem()));
  }
}
