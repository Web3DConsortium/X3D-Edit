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

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import java.lang.reflect.Field;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;

import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;

import static org.web3d.x3d.palette.X3DPaletteUtilities.escapeXmlCharacters;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * ESPDUTRANSFORMCustomizer.java
 * Created on 16 May 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class ESPDUTRANSFORMCustomizer extends BaseCustomizer
{
  private final ESPDUTRANSFORM espduTrans;
  private final JTextComponent target;

  private final int ARTICULATEDPARAMETER_DESIGNATOR_COL     = 1;
  private final int ARTICULATEDPARAMETER_CHANGE_COL         = 2;
  private final int ARTICULATEDPARAMETER_ID_COL             = 3;
  private final int ARTICULATEDPARAMETER_TYPE_COL           = 4;
  private final int ARTICULATEDPARAMETER_PARAMETERVALUE_COL = 5;
  
  public ESPDUTRANSFORMCustomizer(ESPDUTRANSFORM espduTransform, JTextComponent target)
  {
    super(espduTransform);
    this.espduTrans = espduTransform;
    this.target = target;
    
    HelpCtx.setHelpIDString(ESPDUTRANSFORMCustomizer.this, "ESPDUTRANSFORM_ELEM_HELPID");

    espduTransform.setVisualizationSelectionAvailable(true); // must precede initComponents() interface initialization
    espduTransform.setVisualizationTooltip("Show center axes, add wireframe Box and axes to show boundingBox center and size (if defined)");
    
    initComponents();
    
    @SuppressWarnings("unchecked")
    ListCellRenderer<String> cellRenderer = new MyComboBoxRenderer();
    this.entCountryCombo.setRenderer(cellRenderer);
    this.entDomainCombo.setRenderer(cellRenderer);
    this.entKindCombo.setRenderer(cellRenderer);
    this.forceIDCombo.setRenderer(cellRenderer);
    
//    EspduTransformHelper.initComponents(this,espduTrans);
    initializeComponents ();

    if(enableVal != null) {
      enableWidgets(enableVal);
      enableVal = null;
    }

    // indicate degree values in tooltips
    double angle = new SFDouble(rotation3TF.getText()).getValue();
    rotation3TF.setToolTipText(angle + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");
    angle = new SFDouble(scaleOrientation3TF.getText()).getValue();
    scaleOrientation3TF.setToolTipText(angle + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");

    checkAngles (false);

    setDefaultDEFname ();
  }
  private void setDefaultDEFname ()
  {
    super.getDEFUSEpanel().setDefaultDEFname(NbBundle.getMessage(getClass(),getNameKey()) + markingTF.getText());
  }

  private void initializeComponents ()
  {
    addressTF.setText(espduTrans.getAddress()); //String
    appIdTF.setText(espduTrans.getAppID());     //SFInt

//    articParmCountTF.setText           (espduTrans.getArticulationParameterCount());//SFInt
//	articParmDesArrayTF.setText        (espduTrans.formatStringArray(espduTrans.getArticulationParameterDesignatorArray()));//SFInt[]
//	articParmChgIndArrayTF.setText     (espduTrans.formatStringArray(espduTrans.getArticulationParameterChangeIndicatorArray()));//SFInt[]
//	articParmIdPartAttToArrayTF.setText(espduTrans.formatStringArray(espduTrans.getArticulationParameterIdPartAttachedToArray()));//SFInt[]
//	articParmTypeField.setText         (espduTrans.formatStringArray(espduTrans.getArticulationParameterTypeArray()));//SFInt[]
//	articParmArrayTF.setText           (espduTrans.formatStringArray(espduTrans.getArticulationParameterArray()));//SFFloat[]

    int numberArticulatedParameters = new SFInt32(espduTrans.getArticulationParameterCount()).getValue();
    DefaultTableModel mydtm = (DefaultTableModel)articParamTable.getModel();
    mydtm.setRowCount(0); // clear
    String[] desig = espduTrans.getArticulationParameterDesignatorArray();
    String[] chg   = espduTrans.getArticulationParameterChangeIndicatorArray();
    String[] id    = espduTrans.getArticulationParameterIdPartAttachedToArray();
    String[] typ   = espduTrans.getArticulationParameterTypeArray();
    String[] par   = espduTrans.getArticulationParameterArray();

    for(int i = 1; i<=numberArticulatedParameters; i++) {
      // start w/ 1
      Object[] oa = new Object[6];
      oa[0] = i-1;
      oa[1] = desig.length>=i?desig[i-1]:ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERDESIGNATORARRAY_DFLT;
      oa[2] = chg.length>=i  ?chg[i-1]  :ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERCHANGEINDICATORARRAY_DFLT;
      oa[3] = id.length>=i   ?id[i-1]   :ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERIDPARTATTACHEDTORARRAY_DFLT;
      oa[4] = typ.length>=i  ?typ[i-1]  :ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERTYPEARRAY_DFLT;
      oa[5] = par.length>=i  ?par[i-1]  :ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERARRAY_DFLT;

      mydtm.addRow(oa);
    }

    portTF.setText(espduTrans.getPort());
    siteIdTF.setText(espduTrans.getSiteID());
    centerXTF.setText(espduTrans.getCenterX());  //SFFloat
    centerYTF.setText(espduTrans.getCenterY());  //SFFloat
    centerZTF.setText(espduTrans.getCenterZ());  //SFFloat
    collisionTypeCombo.setSelectedItem(Integer.parseInt(espduTrans.getCollisionType()));  //SFInt
    deadReckoningTF.setText(espduTrans.getDeadReckoning());  //SFInt
    detLoc0TF.setText(espduTrans.getDetonationLocationX());  //SFFloat
    detLoc1TF.setText(espduTrans.getDetonationLocationY());  //SFFloat
    detLoc2TF.setText(espduTrans.getDetonationLocationZ());  //SFFloat
    detRelLoc0TF.setText(espduTrans.getDetonationRelativeLocationX());  //SFFloat
    detRelLoc1TF.setText(espduTrans.getDetonationRelativeLocationY());  //SFFloat
    detRelLoc2TF.setText(espduTrans.getDetonationRelativeLocationZ());  //SFFloat
    detonationResultCombo.setSelectedItem(Integer.parseInt(espduTrans.getDetonationResult()));  //SFInt
    enabledCB.setSelected(Boolean.parseBoolean(espduTrans.isEnabled()));   //remember selected not enabled !!!

    entCategoryTF.setText(espduTrans.getEntityCategory());  //SFInt

    String countryNum = espduTrans.getEntityCountry();
    Country ct;
    int countryInt=0;
    try {
      countryInt = (new SFInt32(countryNum)).getValue();
      ct = Country.values()[countryInt];
      entCountryCombo.setSelectedItem(ct);    // good data
    }
    catch (NumberFormatException fex) {
      entCountryCombo.setSelectedItem(Country.OTHER);  // not an int
    }
    catch (ArrayIndexOutOfBoundsException aex) {
      entCountryCombo.setSelectedItem("" + countryInt);  // no enumeration match but good int
    }

    String entityDomainNum = espduTrans.getEntityDomain();
    PlatformDomain ed;
    int entDomInt=0;
    try {
      entDomInt = (new SFInt32(entityDomainNum)).getValue();
      ed = PlatformDomain.values()[entDomInt];
      entDomainCombo.setSelectedItem(ed);    // good data
    }
    catch (NumberFormatException nex) {
      entDomainCombo.setSelectedItem(PlatformDomain.OTHER);  // not an int
    }
    catch (ArrayIndexOutOfBoundsException aex) {
      entDomainCombo.setSelectedItem("" + entDomInt);  // no enumeration match but good int
    }

    entExtraTF.setText(espduTrans.getEntityExtra());  //SFInt
    entityIDTF.setText(espduTrans.getEntityID());  //SFInt

    String entKindNum = espduTrans.getEntityKind();
    EntityKind ek;
    int entKindInt = 0;
    try {
      entKindInt = (new SFInt32(entKindNum)).getValue();
      ek = EntityKind.values()[entKindInt];
      entKindCombo.setSelectedItem(ek);    // good data
    }
    catch (NumberFormatException nex) {
      entKindCombo.setSelectedItem(EntityKind.OTHER);  // not an int
    }
    catch (ArrayIndexOutOfBoundsException aex) {
      entKindCombo.setSelectedItem("" + entKindInt);  // no enumeration match but good int
    }

    entSpecificTF.setText(espduTrans.getEntitySpecific());
    entSubCategoryTF.setText(espduTrans.getEntitySubCategory());

    evAppIDTF.setText(espduTrans.getEventApplicationID());//SFInt
    evEntIDTF.setText(espduTrans.getEventEntityID());//SFInt
    evNumTF.setText(espduTrans.getEventNumber());//SFInt
    evSiteIDTF.setText(espduTrans.getEventSiteID()); //SFInt
    fired1TF.setText(espduTrans.isFired1()); // boolean
    fired2TF.setText(espduTrans.isFired2()); // boolean
    fireMissionIdxTF.setText(espduTrans.getFireMissionIndex());
    firingRangeTF.setText(espduTrans.getFiringRange());
    firingRateTF.setText(espduTrans.getFiringRate());

    String forceIDNum = espduTrans.getForceID();
    ForceID fid;
    int fidInt = 0;
    try {
      fidInt = (new SFInt32(forceIDNum)).getValue();
      fid = ForceID.values()[fidInt];
      forceIDCombo.setSelectedItem(fid);
    }
    catch (NumberFormatException nex) {
      forceIDCombo.setSelectedItem(ForceID.OTHER); // not an int
    }
    catch (ArrayIndexOutOfBoundsException aex) {
      forceIDCombo.setSelectedItem("" + fidInt); // no enumeration match but good int
    }

    fuseTF.setText(espduTrans.getFuse()); // SFInt

    linVel0TF.setText(espduTrans.getLinearVelocityX()); //SFFloat
    linVel1TF.setText(espduTrans.getLinearVelocityY()); //SFFloat
    linVel2TF.setText(espduTrans.getLinearVelocityZ()); //SFFloat
    linAccel0TF.setText(espduTrans.getLinearAccelerationX()); //SFFloat
    linAccel1TF.setText(espduTrans.getLinearAccelerationY()); //SFFloat
    linAccel2TF.setText(espduTrans.getLinearAccelerationZ()); //SFFloat
    markingTF.setText(espduTrans.getMarking()); //String

    mcastRelayHostTF.setText(espduTrans.getMulticastRelayHost()); //String
    mcastRelayPort.setText(espduTrans.getMulticastRelayPort()); // SFInt

    munAppIDTF.setText(espduTrans.getMunitionApplicationID()); //SFInt
    munEndPoint0TF.setText(espduTrans.getMunitionEndPointX()); //SFFloat
    munEndPoint1TF.setText(espduTrans.getMunitionEndPointY()); //SFFloat
    munEndPoint2TF.setText(espduTrans.getMunitionEndPointZ()); //SFFloat
    munEntityIDTF.setText(espduTrans.getMunitionEntityID()); //SFInt
    munitionQuantityTF.setText(espduTrans.getMunitionQuantity()); //SFInt
    munSiteIDTF.setText(espduTrans.getMunitionSiteID()); // SFInt
    munStartPoint0TF.setText(espduTrans.getMunitionStartPointX()); // SFFloat
    munStartPoint1TF.setText(espduTrans.getMunitionStartPointY()); // SFFloat
    munStartPoint2TF.setText(espduTrans.getMunitionStartPointZ()); // SFFloat
    networkModeComboBox.setSelectedItem(espduTrans.getNetworkMode()); // String
    portTF.setText(espduTrans.getPort()); //SFInt
    readIntervalTF.setText(espduTrans.getReadInterval()); //SFFloat

    rotation0TF.setText(espduTrans.getRotationX());// SFFloat
    rotation1TF.setText(espduTrans.getRotationY());// SFFloat
    rotation2TF.setText(espduTrans.getRotationZ());// SFFloat
    rotation3TF.setText(espduTrans.getRotationAngle());// SFFloat
    scale0Tf.setText(espduTrans.getScaleX()); // SFFloat
    scale1TF.setText(espduTrans.getScaleY()); // SFFloat
    scale2TF.setText(espduTrans.getScaleZ()); // SFFloat
    scaleOrientation0TF.setText(espduTrans.getScaleOrientationX()); // SFFloat
    scaleOrientation1TF.setText(espduTrans.getScaleOrientationY()); // SFFloat
    scaleOrientation2TF.setText(espduTrans.getScaleOrientationZ()); // SFFloat
    scaleOrientation3TF.setText(espduTrans.getScaleOrientationAngle()); // SFFloat

    siteIdTF.setText(espduTrans.getSiteID()); //SFInt
    translation0TF.setText(espduTrans.getTranslationX()); //SFFloat
    translation1TF.setText(espduTrans.getTranslationY()); //SFFloat
    translation2TF.setText(espduTrans.getTranslationZ()); //SFFloat

    warheadComboBox.setSelectedItem(espduTrans.getWarhead()); // SFInt
    writeIntervalTF.setText(espduTrans.getWriteInterval()); //SFFloat

    bboxCenterXTF.setText(espduTrans.getBboxCenterX()); // SFFloat
    bboxCenterYTF.setText(espduTrans.getBboxCenterY()); // SFFloat
    bboxCenterZTF.setText(espduTrans.getBboxCenterZ()); // SFFloat
    bboxSizeXTF.setText(espduTrans.getBboxSizeX()); // SFFloat
    bboxSizeYTF.setText(espduTrans.getBboxSizeY()); // SFFloat
    bboxSizeZTF.setText(espduTrans.getBboxSizeZ()); // SFFloprivatetpHdrExpCB.setSelected(Boolean.parseBoolean(espduTrans.isRtpHeaderExpected()));
  }
  
  private Boolean enableVal = null;
  @Override
  protected final void enableWidgets(boolean wh)
  {
    // This will be entered first from initComponents() in the constructor, and we're not yet ready to go.  Defer execution.
    if(espduTabbedPanel != null)
    {
      super.enableWidgets(wh);
      enableNestedComponents(espduTabbedPanel,wh);
    }
    else
      enableVal = wh;
  }
  
  private void enableNestedComponents(Component comp, boolean wh)
  {
    comp.setEnabled(wh);
    
    if (comp instanceof Container) {
      Component[] children =((Container)comp).getComponents();
      for (Component c : children)
        enableNestedComponents(c, wh);
    }
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

        espduTabbedPanel = new javax.swing.JTabbedPane();
        IdNetworkPane = new javax.swing.JPanel();
        mcastaddrLab = new javax.swing.JLabel();
        addressTF = new javax.swing.JTextField();
        enabledLab = new javax.swing.JLabel();
        enabledCB = new javax.swing.JCheckBox();
        markingLab = new javax.swing.JLabel();
        markingTF = new javax.swing.JTextField();
        mcastRelayHostLabel = new javax.swing.JLabel();
        mcastRelayHostTF = new javax.swing.JTextField();
        mcastRelayPortLabel = new javax.swing.JLabel();
        mcastRelayPort = new javax.swing.JTextField();
        networkModeLabel = new javax.swing.JLabel();
        networkModeComboBox = new javax.swing.JComboBox<>();
        readIntervalLabel = new javax.swing.JLabel();
        readIntervalTF = new javax.swing.JTextField();
        readIntervalUnitsLabel = new javax.swing.JLabel();
        writeIntervalLabel = new javax.swing.JLabel();
        writeIntervalTF = new javax.swing.JTextField();
        writeIntervalUnitsLabel = new javax.swing.JLabel();
        portLabel = new javax.swing.JLabel();
        portTF = new javax.swing.JTextField();
        rtpHdrLab = new javax.swing.JLabel();
        rtpHdrExpCB = new javax.swing.JCheckBox();
        entityIDLab = new javax.swing.JLabel();
        entityIDTF = new javax.swing.JTextField();
        applicationIDLabel = new javax.swing.JLabel();
        appIdTF = new javax.swing.JTextField();
        siteIDLabel = new javax.swing.JLabel();
        siteIdTF = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        identificationParametersPanel = new javax.swing.JPanel();
        identificationParametersLabel = new javax.swing.JLabel();
        transformPane = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        centerXTF = new javax.swing.JTextField();
        centerYTF = new javax.swing.JTextField();
        centerZTF = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        rotation0TF = new javax.swing.JTextField();
        rotation1TF = new javax.swing.JTextField();
        rotation2TF = new javax.swing.JTextField();
        rotation3TF = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        scale0Tf = new javax.swing.JTextField();
        scale1TF = new javax.swing.JTextField();
        scale2TF = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        scaleOrientation0TF = new javax.swing.JTextField();
        scaleOrientation1TF = new javax.swing.JTextField();
        scaleOrientation2TF = new javax.swing.JTextField();
        scaleOrientation3TF = new javax.swing.JTextField();
        translationLab = new javax.swing.JLabel();
        translation0TF = new javax.swing.JTextField();
        translation1TF = new javax.swing.JTextField();
        translation2TF = new javax.swing.JTextField();
        jLabel50 = new javax.swing.JLabel();
        bboxCenterXTF = new javax.swing.JTextField();
        bboxCenterYTF = new javax.swing.JTextField();
        bboxCenterZTF = new javax.swing.JTextField();
        jLabel51 = new javax.swing.JLabel();
        bboxSizeXTF = new javax.swing.JTextField();
        bboxSizeYTF = new javax.swing.JTextField();
        bboxSizeZTF = new javax.swing.JTextField();
        normalizeRotationValuesButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        entityEventPane = new javax.swing.JPanel();
        forceIDLabel = new javax.swing.JLabel();
        forceIDCombo = new javax.swing.JComboBox<>();
        categoryLabel = new javax.swing.JLabel();
        entCategoryTF = new javax.swing.JTextField();
        entDomainCombo = new javax.swing.JComboBox<>();
        entKindCombo = new javax.swing.JComboBox<>();
        entCountryCombo = new javax.swing.JComboBox<>();
        countryLabel = new javax.swing.JLabel();
        domainLabel = new javax.swing.JLabel();
        extraLabel = new javax.swing.JLabel();
        entExtraTF = new javax.swing.JTextField();
        kindLabel = new javax.swing.JLabel();
        specificLabel = new javax.swing.JLabel();
        entSpecificTF = new javax.swing.JTextField();
        subCategoryLabel = new javax.swing.JLabel();
        entSubCategoryTF = new javax.swing.JTextField();
        eventApplicationIDLabel = new javax.swing.JLabel();
        evAppIDTF = new javax.swing.JTextField();
        eventEntityIDLabel = new javax.swing.JLabel();
        evEntIDTF = new javax.swing.JTextField();
        eventNumberLabel = new javax.swing.JLabel();
        evNumTF = new javax.swing.JTextField();
        eventSiteIDLabel = new javax.swing.JLabel();
        evSiteIDTF = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        identificationParametersPanel2 = new javax.swing.JPanel();
        identificationParametersLabel2 = new javax.swing.JLabel();
        articulatedParametersPane = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        articParamTable = new JTableColOneReadOnly();
        articulatedParametersButtonPanel = new javax.swing.JPanel();
        plusButt = new javax.swing.JButton();
        minusButt = new javax.swing.JButton();
        upButt = new javax.swing.JButton();
        downButt = new javax.swing.JButton();
        munition1Pane = new javax.swing.JPanel();
        munitionApplicationIDLabel = new javax.swing.JLabel();
        munAppIDTF = new javax.swing.JTextField();
        munitionEntityIDLabel = new javax.swing.JLabel();
        munEntityIDTF = new javax.swing.JTextField();
        munitionQuantityLabel = new javax.swing.JLabel();
        munitionQuantityTF = new javax.swing.JTextField();
        munitionSiteIDLabel = new javax.swing.JLabel();
        munSiteIDTF = new javax.swing.JTextField();
        munitionStartPointLabel = new javax.swing.JLabel();
        munStartPoint0TF = new javax.swing.JTextField();
        munStartPoint1TF = new javax.swing.JTextField();
        munStartPoint2TF = new javax.swing.JTextField();
        munitionEndPointLabel = new javax.swing.JLabel();
        munEndPoint0TF = new javax.swing.JTextField();
        munEndPoint1TF = new javax.swing.JTextField();
        munEndPoint2TF = new javax.swing.JTextField();
        detonationResultLabel = new javax.swing.JLabel();
        detonationLocationLabel = new javax.swing.JLabel();
        detLoc0TF = new javax.swing.JTextField();
        detLoc1TF = new javax.swing.JTextField();
        detLoc2TF = new javax.swing.JTextField();
        detonationRelativeLocationLabel = new javax.swing.JLabel();
        detRelLoc0TF = new javax.swing.JTextField();
        detRelLoc1TF = new javax.swing.JTextField();
        detRelLoc2TF = new javax.swing.JTextField();
        warheadLabel52 = new javax.swing.JLabel();
        warheadComboBox = new javax.swing.JComboBox<>();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        identificationParametersPanel1 = new javax.swing.JPanel();
        identificationParametersLabel1 = new javax.swing.JLabel();
        detonationResultCombo = new javax.swing.JComboBox<>();
        fired1Label = new javax.swing.JLabel();
        fired1TF = new javax.swing.JTextField();
        fired2Label = new javax.swing.JLabel();
        fired2TF = new javax.swing.JTextField();
        fireMissionIndexLabel = new javax.swing.JLabel();
        fireMissionIdxTF = new javax.swing.JTextField();
        firingRangeLabel = new javax.swing.JLabel();
        firingRangeTF = new javax.swing.JTextField();
        fuseLabel = new javax.swing.JLabel();
        fuseTF = new javax.swing.JTextField();
        firingRateLabel = new javax.swing.JLabel();
        firingRateTF = new javax.swing.JTextField();
        munition2Panel = new javax.swing.JPanel();
        physicsPane = new javax.swing.JPanel();
        collisionTypeLabel = new javax.swing.JLabel();
        deadReckoningLabel = new javax.swing.JLabel();
        deadReckoningTF = new javax.swing.JTextField();
        linearVelocityLabel = new javax.swing.JLabel();
        linVel0TF = new javax.swing.JTextField();
        linVel1TF = new javax.swing.JTextField();
        linVel2TF = new javax.swing.JTextField();
        linearAccelerationLabel = new javax.swing.JLabel();
        linAccel0TF = new javax.swing.JTextField();
        linAccel1TF = new javax.swing.JTextField();
        linAccel2TF = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        collisionTypeCombo = new javax.swing.JComboBox<>();
        nodeHintPanel = new javax.swing.JPanel();
        hintLabel = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        espduTabbedPanel.setToolTipText("Information required for representation of the entity’s visual appearance and position of its articulated parts");
        espduTabbedPanel.setMinimumSize(new java.awt.Dimension(550, 600));
        espduTabbedPanel.setPreferredSize(new java.awt.Dimension(550, 400));

        IdNetworkPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 0, 0, 0));
        IdNetworkPane.setLayout(new java.awt.GridBagLayout());

        mcastaddrLab.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        mcastaddrLab.setText("address");
        mcastaddrLab.setToolTipText("Multicast network address, or else \"localhost\" example: 224.2.181.145. ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.insets = new java.awt.Insets(6, 3, 3, 3);
        IdNetworkPane.add(mcastaddrLab, gridBagConstraints);

        addressTF.setToolTipText("Multicast network address, or else \"localhost\" example: 224.2.181.145. ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(6, 3, 3, 3);
        IdNetworkPane.add(addressTF, gridBagConstraints);

        enabledLab.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        enabledLab.setText("enabled");
        enabledLab.setToolTipText("Enable/disable network sensing and event passing");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.insets = new java.awt.Insets(6, 3, 3, 3);
        IdNetworkPane.add(enabledLab, gridBagConstraints);

        enabledCB.setSelected(true);
        enabledCB.setToolTipText("Enable/disable network sensing and event passing");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 3, 3);
        IdNetworkPane.add(enabledCB, gridBagConstraints);

        markingLab.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        markingLab.setText("marking");
        markingLab.setToolTipText("11-character identifier name for this entity");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        IdNetworkPane.add(markingLab, gridBagConstraints);

        markingTF.setToolTipText("11-character identifier name for this entity");
        markingTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                markingTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 70;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        IdNetworkPane.add(markingTF, gridBagConstraints);

        mcastRelayHostLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        mcastRelayHostLabel.setText("multicastRelayHost");
        mcastRelayHostLabel.setToolTipText("fallback server address if multicast not available locally");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        IdNetworkPane.add(mcastRelayHostLabel, gridBagConstraints);

        mcastRelayHostTF.setToolTipText("fallback server address if multicast not available locally");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        IdNetworkPane.add(mcastRelayHostTF, gridBagConstraints);

        mcastRelayPortLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        mcastRelayPortLabel.setText("multicastRelayPort");
        mcastRelayPortLabel.setToolTipText("fallback server port if multicast not available locally (example: 8010)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        IdNetworkPane.add(mcastRelayPortLabel, gridBagConstraints);

        mcastRelayPort.setToolTipText("fallback server port if multicast not available locally (example: 8010)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        IdNetworkPane.add(mcastRelayPort, gridBagConstraints);

        networkModeLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        networkModeLabel.setText("networkMode");
        networkModeLabel.setToolTipText("Whether entity ignores the network (standAlone), sends DIS packets to network (networkWriter) or receives DIS packets from network (networkReader)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        IdNetworkPane.add(networkModeLabel, gridBagConstraints);

        networkModeComboBox.setModel(new DefaultComboBoxModel<>(ESPDUTRANSFORM_ATTR_NETWORKNODE_CHOICES));
        networkModeComboBox.setToolTipText("Whether entity ignores the network (standAlone), sends DIS packets to network (networkWriter) or receives DIS packets from network (networkReader)");
        networkModeComboBox.setPreferredSize(new java.awt.Dimension(15, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        IdNetworkPane.add(networkModeComboBox, gridBagConstraints);

        readIntervalLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        readIntervalLabel.setText("readInterval");
        readIntervalLabel.setToolTipText("seconds between read updates, 0 means no reading");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        IdNetworkPane.add(readIntervalLabel, gridBagConstraints);

        readIntervalTF.setToolTipText("seconds between read updates, 0 means no reading");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        IdNetworkPane.add(readIntervalTF, gridBagConstraints);

        readIntervalUnitsLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        readIntervalUnitsLabel.setText("seconds");
        readIntervalUnitsLabel.setToolTipText("seconds between read updates, 0 means no reading");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        IdNetworkPane.add(readIntervalUnitsLabel, gridBagConstraints);

        writeIntervalLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        writeIntervalLabel.setText("writeInterval");
        writeIntervalLabel.setToolTipText("seconds between write updates, 0 means no writing");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        IdNetworkPane.add(writeIntervalLabel, gridBagConstraints);

        writeIntervalTF.setToolTipText("seconds between write updates, 0 means no writing");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        IdNetworkPane.add(writeIntervalTF, gridBagConstraints);

        writeIntervalUnitsLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        writeIntervalUnitsLabel.setText("seconds");
        writeIntervalUnitsLabel.setToolTipText("seconds between write updates, 0 means no writing");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        IdNetworkPane.add(writeIntervalUnitsLabel, gridBagConstraints);

        portLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        portLabel.setText("port");
        portLabel.setToolTipText("Network connection port number (EXAMPLE 62040) for sending or receiving DIS messages");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        IdNetworkPane.add(portLabel, gridBagConstraints);

        portTF.setToolTipText("Network connection port number (EXAMPLE 62040) for sending or receiving DIS messages");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        IdNetworkPane.add(portTF, gridBagConstraints);

        rtpHdrLab.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        rtpHdrLab.setText("rtpHeaderExpected");
        rtpHdrLab.setToolTipText("whether RTP headers are prepended to DIS PDUs");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        IdNetworkPane.add(rtpHdrLab, gridBagConstraints);

        rtpHdrExpCB.setToolTipText("whether RTP headers are prepended to DIS PDUs");
        rtpHdrExpCB.setMaximumSize(new java.awt.Dimension(12, 18));
        rtpHdrExpCB.setMinimumSize(new java.awt.Dimension(6, 18));
        rtpHdrExpCB.setPreferredSize(new java.awt.Dimension(20, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 50;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        IdNetworkPane.add(rtpHdrExpCB, gridBagConstraints);

        entityIDLab.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        entityIDLab.setText("entityID");
        entityIDLab.setToolTipText("Simulation/exercise entityID is a unique ID for a single entity within that application. ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        IdNetworkPane.add(entityIDLab, gridBagConstraints);

        entityIDTF.setToolTipText("Simulation/exercise entityID is a unique ID for a single entity within that application. ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 70;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        IdNetworkPane.add(entityIDTF, gridBagConstraints);

        applicationIDLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        applicationIDLabel.setText("applicationID");
        applicationIDLabel.setToolTipText("Simulation/exercise applicationID is unique for application at that site");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        IdNetworkPane.add(applicationIDLabel, gridBagConstraints);

        appIdTF.setToolTipText("Simulation/exercise applicationID is unique for application at that site");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 70;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        IdNetworkPane.add(appIdTF, gridBagConstraints);

        siteIDLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        siteIDLabel.setText("siteID");
        siteIDLabel.setToolTipText("Simulation/exercise siteID of participating LAN or organization");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 3;
        gridBagConstraints.ipady = 3;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        IdNetworkPane.add(siteIDLabel, gridBagConstraints);

        siteIdTF.setToolTipText("Simulation/exercise siteID of participating LAN or organization");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 70;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        IdNetworkPane.add(siteIdTF, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.weighty = 1.0;
        IdNetworkPane.add(jLabel1, gridBagConstraints);

        identificationParametersPanel.setLayout(new java.awt.GridBagLayout());

        identificationParametersLabel.setText("<html><p align=\"center\">DIS</p> <p align=\"center\">entity</p> <p align=\"center\">&nbsp;&nbsp;identification&nbsp;&nbsp;</p> <p align=\"center\">parameters</p>");
        identificationParametersLabel.setToolTipText("Unique identification for this DIS entity");
        identificationParametersLabel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        identificationParametersPanel.add(identificationParametersLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        IdNetworkPane.add(identificationParametersPanel, gridBagConstraints);

        espduTabbedPanel.addTab("ID, network", IdNetworkPane);

        transformPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 0, 0, 0));
        transformPane.setLayout(new java.awt.GridBagLayout());

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel33.setText("center");
        jLabel33.setToolTipText("Translation offset from origin of local coordinate system, applied prior to rotation or scaling");
        jLabel33.setMaximumSize(new java.awt.Dimension(6, 20));
        jLabel33.setMinimumSize(new java.awt.Dimension(6, 20));
        jLabel33.setPreferredSize(new java.awt.Dimension(6, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transformPane.add(jLabel33, gridBagConstraints);

        centerXTF.setToolTipText("Translation offset from origin of local coordinate system, applied prior to rotation or scaling");
        centerXTF.setMaximumSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transformPane.add(centerXTF, gridBagConstraints);

        centerYTF.setToolTipText("Translation offset from origin of local coordinate system, applied prior to rotation or scaling");
        centerYTF.setMaximumSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transformPane.add(centerYTF, gridBagConstraints);

        centerZTF.setToolTipText("Translation offset from origin of local coordinate system, applied prior to rotation or scaling");
        centerZTF.setMaximumSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transformPane.add(centerZTF, gridBagConstraints);

        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel34.setText("rotation");
        jLabel34.setToolTipText("Orientation (axis, angle in radians) of children relative to local coordinate system");
        jLabel34.setMaximumSize(new java.awt.Dimension(6, 20));
        jLabel34.setMinimumSize(new java.awt.Dimension(6, 20));
        jLabel34.setPreferredSize(new java.awt.Dimension(6, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transformPane.add(jLabel34, gridBagConstraints);

        rotation0TF.setToolTipText("Orientation (axis, angle in radians) of children relative to local coordinate system");
        rotation0TF.setMaximumSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transformPane.add(rotation0TF, gridBagConstraints);

        rotation1TF.setToolTipText("Orientation (axis, angle in radians) of children relative to local coordinate system");
        rotation1TF.setMaximumSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transformPane.add(rotation1TF, gridBagConstraints);

        rotation2TF.setToolTipText("Orientation (axis, angle in radians) of children relative to local coordinate system");
        rotation2TF.setMaximumSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transformPane.add(rotation2TF, gridBagConstraints);

        rotation3TF.setToolTipText("Orientation (axis, angle in radians) of children relative to local coordinate system");
        rotation3TF.setMaximumSize(new java.awt.Dimension(40, 20));
        rotation3TF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rotation3TFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transformPane.add(rotation3TF, gridBagConstraints);

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel35.setText("scale");
        jLabel35.setToolTipText("Non-uniform x-y-z scale of child coordinate system, adjusted by center and scaleOrientation");
        jLabel35.setMaximumSize(new java.awt.Dimension(6, 20));
        jLabel35.setMinimumSize(new java.awt.Dimension(6, 20));
        jLabel35.setPreferredSize(new java.awt.Dimension(6, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transformPane.add(jLabel35, gridBagConstraints);

        scale0Tf.setToolTipText("Non-uniform x-y-z scale of child coordinate system, adjusted by center and scaleOrientation");
        scale0Tf.setMaximumSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transformPane.add(scale0Tf, gridBagConstraints);

        scale1TF.setToolTipText("Non-uniform x-y-z scale of child coordinate system, adjusted by center and scaleOrientation");
        scale1TF.setMaximumSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transformPane.add(scale1TF, gridBagConstraints);

        scale2TF.setToolTipText("Non-uniform x-y-z scale of child coordinate system, adjusted by center and scaleOrientation");
        scale2TF.setMaximumSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transformPane.add(scale2TF, gridBagConstraints);

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel36.setText("scaleOrientation");
        jLabel36.setToolTipText("Preliminary rotation of coordinate system before scaling (to allow scaling around arbitrary orientations)");
        jLabel36.setMaximumSize(new java.awt.Dimension(6, 20));
        jLabel36.setMinimumSize(new java.awt.Dimension(60, 18));
        jLabel36.setPreferredSize(new java.awt.Dimension(60, 18));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transformPane.add(jLabel36, gridBagConstraints);

        scaleOrientation0TF.setToolTipText("Preliminary rotation of coordinate system before scaling (to allow scaling around arbitrary orientations)");
        scaleOrientation0TF.setMaximumSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transformPane.add(scaleOrientation0TF, gridBagConstraints);

        scaleOrientation1TF.setToolTipText("Preliminary rotation of coordinate system before scaling (to allow scaling around arbitrary orientations)");
        scaleOrientation1TF.setMaximumSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transformPane.add(scaleOrientation1TF, gridBagConstraints);

        scaleOrientation2TF.setToolTipText("Preliminary rotation of coordinate system before scaling (to allow scaling around arbitrary orientations)");
        scaleOrientation2TF.setMaximumSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transformPane.add(scaleOrientation2TF, gridBagConstraints);

        scaleOrientation3TF.setToolTipText("Preliminary rotation of coordinate system before scaling (to allow scaling around arbitrary orientations)");
        scaleOrientation3TF.setMaximumSize(new java.awt.Dimension(40, 20));
        scaleOrientation3TF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scaleOrientation3TFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transformPane.add(scaleOrientation3TF, gridBagConstraints);

        translationLab.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        translationLab.setText("translation");
        translationLab.setToolTipText("Position (x, y, z in meters) of children relative to local coordinate system");
        translationLab.setMaximumSize(new java.awt.Dimension(6, 20));
        translationLab.setMinimumSize(new java.awt.Dimension(6, 20));
        translationLab.setPreferredSize(new java.awt.Dimension(6, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transformPane.add(translationLab, gridBagConstraints);

        translation0TF.setToolTipText("Position (x, y, z in meters) of children relative to local coordinate system");
        translation0TF.setMaximumSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transformPane.add(translation0TF, gridBagConstraints);

        translation1TF.setToolTipText("Position (x, y, z in meters) of children relative to local coordinate system");
        translation1TF.setMaximumSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transformPane.add(translation1TF, gridBagConstraints);

        translation2TF.setToolTipText("Position (x, y, z in meters) of children relative to local coordinate system");
        translation2TF.setMaximumSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transformPane.add(translation2TF, gridBagConstraints);

        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel50.setText("bboxCenter");
        jLabel50.setToolTipText("position offset from origin of local coordinate system for collision bounding box");
        jLabel50.setMaximumSize(new java.awt.Dimension(6, 20));
        jLabel50.setMinimumSize(new java.awt.Dimension(6, 20));
        jLabel50.setPreferredSize(new java.awt.Dimension(6, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transformPane.add(jLabel50, gridBagConstraints);

        bboxCenterXTF.setToolTipText("position offset from origin of local coordinate system for collision bounding box");
        bboxCenterXTF.setMaximumSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transformPane.add(bboxCenterXTF, gridBagConstraints);

        bboxCenterYTF.setToolTipText("position offset from origin of local coordinate system for collision bounding box");
        bboxCenterYTF.setMaximumSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transformPane.add(bboxCenterYTF, gridBagConstraints);

        bboxCenterZTF.setToolTipText("position offset from origin of local coordinate system for collision bounding box");
        bboxCenterZTF.setMaximumSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transformPane.add(bboxCenterZTF, gridBagConstraints);

        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel51.setText("bboxSize");
        jLabel51.setToolTipText("bounding box is automatically calculated, can also be specified as an optimization or constraint");
        jLabel51.setMaximumSize(new java.awt.Dimension(6, 20));
        jLabel51.setMinimumSize(new java.awt.Dimension(6, 20));
        jLabel51.setPreferredSize(new java.awt.Dimension(6, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transformPane.add(jLabel51, gridBagConstraints);

        bboxSizeXTF.setToolTipText("bounding box is automatically calculated, can also be specified as an optimization or constraint");
        bboxSizeXTF.setMaximumSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transformPane.add(bboxSizeXTF, gridBagConstraints);

        bboxSizeYTF.setToolTipText("bounding box is automatically calculated, can also be specified as an optimization or constraint");
        bboxSizeYTF.setMaximumSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transformPane.add(bboxSizeYTF, gridBagConstraints);

        bboxSizeZTF.setToolTipText("bounding box is automatically calculated, can also be specified as an optimization or constraint");
        bboxSizeZTF.setMaximumSize(new java.awt.Dimension(40, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 0.2;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transformPane.add(bboxSizeZTF, gridBagConstraints);

        normalizeRotationValuesButton.setText("normalize rotation and scaleOrientation values");
        normalizeRotationValuesButton.setToolTipText("for rotation and scaleOrientation fields, rescale axis values as normalized vector (unit length, ranges 0..1), reset angles [0..2pi)");
        normalizeRotationValuesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                normalizeRotationValuesButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 0, 0, 3);
        transformPane.add(normalizeRotationValuesButton, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.weighty = 1.0;
        transformPane.add(jLabel2, gridBagConstraints);

        espduTabbedPanel.addTab("Transform", transformPane);

        entityEventPane.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 0, 0, 0));
        entityEventPane.setLayout(new java.awt.GridBagLayout());

        forceIDLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        forceIDLabel.setText("forceID");
        forceIDLabel.setToolTipText("determines the team membership of issuing entity");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 33, 3, 3);
        entityEventPane.add(forceIDLabel, gridBagConstraints);

        Dimension forcePrefSz = forceIDCombo.getPreferredSize();
        forceIDCombo.setEditable(true);
        forceIDCombo.setModel(new DefaultComboBoxModel(ForceID.values()));
        forceIDCombo.setToolTipText("determines the team membership of issuing entity");
        forceIDCombo.setPreferredSize(new java.awt.Dimension(20, 20));
        forceIDCombo.setPreferredSize(forcePrefSz);
        forceIDCombo.setMinimumSize(forcePrefSz);
        forceIDCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                forceIDComboActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 85;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        entityEventPane.add(forceIDCombo, gridBagConstraints);

        categoryLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        categoryLabel.setText("entityCategory");
        categoryLabel.setToolTipText("Main category that describes the entity, semantics of each code varies according to domain. See DIS Enumerations values. ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        entityEventPane.add(categoryLabel, gridBagConstraints);

        entCategoryTF.setColumns(5);
        entCategoryTF.setToolTipText("Main category that describes the entity, semantics of each code varies according to domain. See DIS Enumerations values. ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        entityEventPane.add(entCategoryTF, gridBagConstraints);

        Dimension edc = entDomainCombo.getPreferredSize();
        entDomainCombo.setEditable(true);
        entDomainCombo.setModel(new DefaultComboBoxModel(PlatformDomain.values()));
        entDomainCombo.setToolTipText("Domain in which the entity operates");
        entDomainCombo.setPreferredSize(edc);
        entDomainCombo.setMinimumSize(edc);
        entDomainCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entDomainComboActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 23);
        entityEventPane.add(entDomainCombo, gridBagConstraints);

        Dimension ckDim = entKindCombo.getPreferredSize();
        entKindCombo.setEditable(true);
        entKindCombo.setModel(new DefaultComboBoxModel(EntityKind.values()));
        entKindCombo.setToolTipText("Whether entity is a PLATFORM, MUNITION, LIFE_FORM, ENVIRONMENTAL, CULTURAL_FEATURE, SUPPLY, RADIO, EXPENDABLE, SENSOR_EMITTER or OTHER");
        entKindCombo.setPreferredSize(ckDim);
        entKindCombo.setMinimumSize(ckDim);
        entKindCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entKindComboActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 23);
        entityEventPane.add(entKindCombo, gridBagConstraints);

        Dimension sz = entCountryCombo.getPreferredSize();
        entCountryCombo.setEditable(true);
        entCountryCombo.setMaximumRowCount(20);
        entCountryCombo.setModel(new javax.swing.DefaultComboBoxModel(Country.values()));
        entCountryCombo.setToolTipText("country to which the design of the entity or its design specification is attributed");
        entCountryCombo.setPreferredSize(sz);
        entCountryCombo.setMinimumSize(sz);
        entCountryCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                entCountryComboActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 23);
        entityEventPane.add(entCountryCombo, gridBagConstraints);

        countryLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        countryLabel.setText("entityCountry");
        countryLabel.setToolTipText("country to which the design of the entity or its design specification is attributed");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        entityEventPane.add(countryLabel, gridBagConstraints);

        domainLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        domainLabel.setText("entityDomain");
        domainLabel.setToolTipText("Domain in which the entity operates");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        entityEventPane.add(domainLabel, gridBagConstraints);

        extraLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        extraLabel.setText("entityExtra");
        extraLabel.setToolTipText("Any extra information required to describe a particular entity, depends on type of entity represented");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        entityEventPane.add(extraLabel, gridBagConstraints);

        entExtraTF.setToolTipText("Any extra information required to describe a particular entity, depends on type of entity represented");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        entityEventPane.add(entExtraTF, gridBagConstraints);

        kindLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        kindLabel.setText("entityKind");
        kindLabel.setToolTipText("Whether entity is a PLATFORM, MUNITION, LIFE_FORM, ENVIRONMENTAL, CULTURAL_FEATURE, SUPPLY, RADIO, EXPENDABLE, SENSOR_EMITTER or OTHER");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        entityEventPane.add(kindLabel, gridBagConstraints);

        specificLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        specificLabel.setText("entitySpecific");
        specificLabel.setToolTipText("Specific information about an entity based on the Subcategory field. See DIS Enumerations values. ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        entityEventPane.add(specificLabel, gridBagConstraints);

        entSpecificTF.setToolTipText("Specific information about an entity based on the Subcategory field. See DIS Enumerations values. ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        entityEventPane.add(entSpecificTF, gridBagConstraints);

        subCategoryLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        subCategoryLabel.setText("entitySubCategory");
        subCategoryLabel.setToolTipText("Particular subcategory to which an entity belongs based on Category field");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        entityEventPane.add(subCategoryLabel, gridBagConstraints);

        entSubCategoryTF.setToolTipText("Particular subcategory to which an entity belongs based on Category field");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        entityEventPane.add(entSubCategoryTF, gridBagConstraints);

        eventApplicationIDLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        eventApplicationIDLabel.setText("eventApplicationID");
        eventApplicationIDLabel.setToolTipText("Simulation/exercise applicationID is unique for application at that site");
        eventApplicationIDLabel.setMaximumSize(new java.awt.Dimension(131, 20));
        eventApplicationIDLabel.setMinimumSize(new java.awt.Dimension(131, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        entityEventPane.add(eventApplicationIDLabel, gridBagConstraints);

        evAppIDTF.setColumns(5);
        evAppIDTF.setToolTipText("Simulation/exercise applicationID is unique for application at that site");
        evAppIDTF.setMaximumSize(new java.awt.Dimension(131, 20));
        evAppIDTF.setMinimumSize(new java.awt.Dimension(131, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        entityEventPane.add(evAppIDTF, gridBagConstraints);

        eventEntityIDLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        eventEntityIDLabel.setText("eventEntityID");
        eventEntityIDLabel.setToolTipText("For a given event, simulation/exercise entityID is a unique ID for a single entity within that application");
        eventEntityIDLabel.setMaximumSize(new java.awt.Dimension(131, 20));
        eventEntityIDLabel.setMinimumSize(new java.awt.Dimension(131, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        entityEventPane.add(eventEntityIDLabel, gridBagConstraints);

        evEntIDTF.setColumns(5);
        evEntIDTF.setToolTipText("For a given event, simulation/exercise entityID is a unique ID for a single entity within that application");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        entityEventPane.add(evEntIDTF, gridBagConstraints);

        eventNumberLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        eventNumberLabel.setText("eventNumber");
        eventNumberLabel.setToolTipText("Sequential number of each event issued by an application");
        eventNumberLabel.setMaximumSize(new java.awt.Dimension(131, 20));
        eventNumberLabel.setMinimumSize(new java.awt.Dimension(131, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        entityEventPane.add(eventNumberLabel, gridBagConstraints);

        evNumTF.setColumns(5);
        evNumTF.setToolTipText("Sequential number of each event issued by an application");
        evNumTF.setMaximumSize(new java.awt.Dimension(131, 20));
        evNumTF.setMinimumSize(new java.awt.Dimension(131, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        entityEventPane.add(evNumTF, gridBagConstraints);

        eventSiteIDLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        eventSiteIDLabel.setText("eventSiteID");
        eventSiteIDLabel.setToolTipText("Simulation/exercise siteID of the participating LAN or organization.");
        eventSiteIDLabel.setMaximumSize(new java.awt.Dimension(131, 20));
        eventSiteIDLabel.setMinimumSize(new java.awt.Dimension(131, 20));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        entityEventPane.add(eventSiteIDLabel, gridBagConstraints);

        evSiteIDTF.setColumns(5);
        evSiteIDTF.setToolTipText("Simulation/exercise siteID of the participating LAN or organization.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 40;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        entityEventPane.add(evSiteIDTF, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        entityEventPane.add(jLabel3, gridBagConstraints);

        identificationParametersPanel2.setLayout(new java.awt.GridBagLayout());

        identificationParametersLabel2.setText("<html><p align=\"center\">DIS</p> <p align=\"center\">entity</p> <p align=\"center\">&nbsp;&nbsp;identification&nbsp;&nbsp;</p> <p align=\"center\">parameters</p>");
        identificationParametersLabel2.setToolTipText("Unique identification for this DIS entity");
        identificationParametersLabel2.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        identificationParametersPanel2.add(identificationParametersLabel2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 23);
        entityEventPane.add(identificationParametersPanel2, gridBagConstraints);

        espduTabbedPanel.addTab("Entity, event", entityEventPane);

        jScrollPane2.setMinimumSize(null);

        articParamTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Parameter #", "Designator", "Change", "ID attached part", "Type", "Parameter"
            }
        ));
        articParamTable.setMinimumSize(null);
        articParamTable.setPreferredSize(null);
        jScrollPane2.setViewportView(articParamTable);

        articulatedParametersButtonPanel.setLayout(new java.awt.GridBagLayout());

        plusButt.setText("+");
        plusButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                plusButtActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        articulatedParametersButtonPanel.add(plusButt, gridBagConstraints);

        minusButt.setText("-");
        minusButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minusButtActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        articulatedParametersButtonPanel.add(minusButt, gridBagConstraints);

        upButt.setText("up");
        upButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                upButtActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 10, 4, 4);
        articulatedParametersButtonPanel.add(upButt, gridBagConstraints);

        downButt.setText("down");
        downButt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                downButtActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        articulatedParametersButtonPanel.add(downButt, gridBagConstraints);

        javax.swing.GroupLayout articulatedParametersPaneLayout = new javax.swing.GroupLayout(articulatedParametersPane);
        articulatedParametersPane.setLayout(articulatedParametersPaneLayout);
        articulatedParametersPaneLayout.setHorizontalGroup(
            articulatedParametersPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, articulatedParametersPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(articulatedParametersPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 675, Short.MAX_VALUE)
                    .addComponent(articulatedParametersButtonPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        articulatedParametersPaneLayout.setVerticalGroup(
            articulatedParametersPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, articulatedParametersPaneLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 499, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(articulatedParametersButtonPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        espduTabbedPanel.addTab("Articulation Parameters", articulatedParametersPane);

        munition1Pane.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 0, 0, 0));
        munition1Pane.setLayout(new java.awt.GridBagLayout());

        munitionApplicationIDLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        munitionApplicationIDLabel.setText("munitionApplicationID");
        munitionApplicationIDLabel.setToolTipText("munitionApplicationID, unique for application at that site");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        munition1Pane.add(munitionApplicationIDLabel, gridBagConstraints);

        munAppIDTF.setColumns(7);
        munAppIDTF.setToolTipText("munitionApplicationID, unique for application at that site");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        munition1Pane.add(munAppIDTF, gridBagConstraints);

        munitionEntityIDLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        munitionEntityIDLabel.setText("munitionEntityID");
        munitionEntityIDLabel.setToolTipText("unique ID for entity firing munition within that application");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        munition1Pane.add(munitionEntityIDLabel, gridBagConstraints);

        munEntityIDTF.setColumns(7);
        munEntityIDTF.setToolTipText("unique ID for entity firing munition within that application");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        munition1Pane.add(munEntityIDTF, gridBagConstraints);

        munitionQuantityLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        munitionQuantityLabel.setText("munitionQuantity");
        munitionQuantityLabel.setToolTipText("quantity of munitions fired");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        munition1Pane.add(munitionQuantityLabel, gridBagConstraints);

        munitionQuantityTF.setColumns(7);
        munitionQuantityTF.setToolTipText("quantity of munitions fired");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        munition1Pane.add(munitionQuantityTF, gridBagConstraints);

        munitionSiteIDLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        munitionSiteIDLabel.setText("munitionSiteID");
        munitionSiteIDLabel.setToolTipText("Munition siteID of the participating LAN or organization");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        munition1Pane.add(munitionSiteIDLabel, gridBagConstraints);

        munSiteIDTF.setColumns(7);
        munSiteIDTF.setToolTipText("Munition siteID of the participating LAN or organization");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        munition1Pane.add(munSiteIDTF, gridBagConstraints);

        munitionStartPointLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        munitionStartPointLabel.setText("munitionStartPoint");
        munitionStartPointLabel.setToolTipText("Initial point of the munition path from firing weapon to detonation or impact, in exercise coordinates.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        munition1Pane.add(munitionStartPointLabel, gridBagConstraints);

        munStartPoint0TF.setColumns(7);
        munStartPoint0TF.setToolTipText("Initial point of the munition path from firing weapon to detonation or impact, in exercise coordinates.");
        munStartPoint0TF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                munStartPoint0TFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        munition1Pane.add(munStartPoint0TF, gridBagConstraints);

        munStartPoint1TF.setColumns(7);
        munStartPoint1TF.setToolTipText("Initial point of the munition path from firing weapon to detonation or impact, in exercise coordinates.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        munition1Pane.add(munStartPoint1TF, gridBagConstraints);

        munStartPoint2TF.setColumns(7);
        munStartPoint2TF.setToolTipText("Initial point of the munition path from firing weapon to detonation or impact, in exercise coordinates.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        munition1Pane.add(munStartPoint2TF, gridBagConstraints);

        munitionEndPointLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        munitionEndPointLabel.setText("munitionEndPoint");
        munitionEndPointLabel.setToolTipText("Final point of the munition path from firing weapon to detonation or impact, in exercise coordinates.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        munition1Pane.add(munitionEndPointLabel, gridBagConstraints);

        munEndPoint0TF.setColumns(7);
        munEndPoint0TF.setToolTipText("Final point of the munition path from firing weapon to detonation or impact, in exercise coordinates.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        munition1Pane.add(munEndPoint0TF, gridBagConstraints);

        munEndPoint1TF.setColumns(7);
        munEndPoint1TF.setToolTipText("Final point of the munition path from firing weapon to detonation or impact, in exercise coordinates.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        munition1Pane.add(munEndPoint1TF, gridBagConstraints);

        munEndPoint2TF.setColumns(7);
        munEndPoint2TF.setToolTipText("Final point of the munition path from firing weapon to detonation or impact, in exercise coordinates.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        munition1Pane.add(munEndPoint2TF, gridBagConstraints);

        detonationResultLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        detonationResultLabel.setText("detonationResult");
        detonationResultLabel.setToolTipText("Type of detonation and result that occurred, if any");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        munition1Pane.add(detonationResultLabel, gridBagConstraints);

        detonationLocationLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        detonationLocationLabel.setText("detonationLocation");
        detonationLocationLabel.setToolTipText("World coordinates for detonationLocation");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        munition1Pane.add(detonationLocationLabel, gridBagConstraints);

        detLoc0TF.setColumns(7);
        detLoc0TF.setToolTipText("World coordinates for detonationLocation");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        munition1Pane.add(detLoc0TF, gridBagConstraints);

        detLoc1TF.setColumns(7);
        detLoc1TF.setToolTipText("World coordinates for detonationLocation");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        munition1Pane.add(detLoc1TF, gridBagConstraints);

        detLoc2TF.setColumns(7);
        detLoc2TF.setToolTipText("World coordinates for detonationLocation");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        munition1Pane.add(detLoc2TF, gridBagConstraints);

        detonationRelativeLocationLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        detonationRelativeLocationLabel.setText("detonationRelativeLocation");
        detonationRelativeLocationLabel.setToolTipText("Relative coordinates for detonation location ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        munition1Pane.add(detonationRelativeLocationLabel, gridBagConstraints);

        detRelLoc0TF.setColumns(7);
        detRelLoc0TF.setToolTipText("Relative coordinates for detonation location ");
        detRelLoc0TF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detRelLoc0TFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        munition1Pane.add(detRelLoc0TF, gridBagConstraints);

        detRelLoc1TF.setColumns(7);
        detRelLoc1TF.setToolTipText("Relative coordinates for detonation location ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        munition1Pane.add(detRelLoc1TF, gridBagConstraints);

        detRelLoc2TF.setColumns(7);
        detRelLoc2TF.setToolTipText("Relative coordinates for detonation location ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        munition1Pane.add(detRelLoc2TF, gridBagConstraints);

        warheadLabel52.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        warheadLabel52.setText("warhead");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        munition1Pane.add(warheadLabel52, gridBagConstraints);

        warheadComboBox.setEditable(true);
        warheadComboBox.setModel(new DefaultComboBoxModel(MunitionDescriptorWarhead.values()));
        warheadComboBox.setToolTipText("Type of detonation and result that occurred, if any");
        warheadComboBox.setMinimumSize(null);
        warheadComboBox.setPreferredSize(null);
        warheadComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                warheadComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        munition1Pane.add(warheadComboBox, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        munition1Pane.add(jSeparator1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 19;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.weighty = 1.0;
        munition1Pane.add(jLabel5, gridBagConstraints);

        identificationParametersPanel1.setLayout(new java.awt.GridBagLayout());

        identificationParametersLabel1.setText("<html><p align=\"center\">DIS</p> <p align=\"center\">entity</p> <p align=\"center\">&nbsp;&nbsp;identification&nbsp;&nbsp;</p> <p align=\"center\">parameters</p>");
        identificationParametersLabel1.setToolTipText("Unique identification for this DIS entity");
        identificationParametersLabel1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        identificationParametersPanel1.add(identificationParametersLabel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.gridheight = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        munition1Pane.add(identificationParametersPanel1, gridBagConstraints);

        detonationResultCombo.setEditable(true);
        detonationResultCombo.setModel(new DefaultComboBoxModel(DetonationResult.values()));
        detonationResultCombo.setToolTipText("Type of detonation and result that occurred, if any");
        detonationResultCombo.setMinimumSize(null);
        detonationResultCombo.setPreferredSize(null);
        detonationResultCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detonationResultComboActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        munition1Pane.add(detonationResultCombo, gridBagConstraints);

        fired1Label.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        fired1Label.setText("fired1");
        fired1Label.setToolTipText("has the primary weapon (Fire PDU) been fired?");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 33, 3, 3);
        munition1Pane.add(fired1Label, gridBagConstraints);

        fired1TF.setColumns(5);
        fired1TF.setToolTipText("has the primary weapon (Fire PDU) been fired?");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        munition1Pane.add(fired1TF, gridBagConstraints);

        fired2Label.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        fired2Label.setText("fired2");
        fired2Label.setToolTipText("has the secondary weapon (Fire PDU) been fired? ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 33, 3, 3);
        munition1Pane.add(fired2Label, gridBagConstraints);

        fired2TF.setColumns(5);
        fired2TF.setToolTipText("has the secondary weapon (Fire PDU) been fired? ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        munition1Pane.add(fired2TF, gridBagConstraints);

        fireMissionIndexLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        fireMissionIndexLabel.setText("fireMissionIndex");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 13, 3, 3);
        munition1Pane.add(fireMissionIndexLabel, gridBagConstraints);

        fireMissionIdxTF.setColumns(5);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        munition1Pane.add(fireMissionIdxTF, gridBagConstraints);

        firingRangeLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        firingRangeLabel.setText("firingRange");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 13, 3, 3);
        munition1Pane.add(firingRangeLabel, gridBagConstraints);

        firingRangeTF.setColumns(5);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 16;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        munition1Pane.add(firingRangeTF, gridBagConstraints);

        fuseLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        fuseLabel.setText("fuse");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 33, 3, 3);
        munition1Pane.add(fuseLabel, gridBagConstraints);

        fuseTF.setColumns(5);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        munition1Pane.add(fuseTF, gridBagConstraints);

        firingRateLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        firingRateLabel.setText("firingRate");
        firingRateLabel.setToolTipText("rate at which munitions are fired");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 13, 3, 3);
        munition1Pane.add(firingRateLabel, gridBagConstraints);

        firingRateTF.setColumns(5);
        firingRateTF.setToolTipText("rate at which munitions are fired");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        munition1Pane.add(firingRateTF, gridBagConstraints);

        espduTabbedPanel.addTab("Munition 1", munition1Pane);

        munition2Panel.setLayout(new java.awt.GridBagLayout());
        espduTabbedPanel.addTab("Munition 2", munition2Panel);

        physicsPane.setLayout(new java.awt.GridBagLayout());

        collisionTypeLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        collisionTypeLabel.setText("collisionType");
        collisionTypeLabel.setToolTipText("type of collision: ELASTIC or INELASTIC");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(10, 3, 3, 3);
        physicsPane.add(collisionTypeLabel, gridBagConstraints);

        deadReckoningLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        deadReckoningLabel.setText("deadReckoning");
        deadReckoningLabel.setToolTipText("Enumeration value for dead-reckoning algorithm being used to project position/orientation with velocities/accelerations");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        physicsPane.add(deadReckoningLabel, gridBagConstraints);

        deadReckoningTF.setColumns(5);
        deadReckoningTF.setToolTipText("Enumeration value for dead-reckoning algorithm being used to project position/orientation with velocities/accelerations");
        deadReckoningTF.setMinimumSize(null);
        deadReckoningTF.setPreferredSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        physicsPane.add(deadReckoningTF, gridBagConstraints);

        linearVelocityLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        linearVelocityLabel.setText("linearVelocity");
        linearVelocityLabel.setToolTipText("Velocity of the entity relative to the rotating Earth in either world or entity coordinates, depending on the dead reckoning algorithm used");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        physicsPane.add(linearVelocityLabel, gridBagConstraints);

        linVel0TF.setColumns(5);
        linVel0TF.setToolTipText("Velocity of the entity relative to the rotating Earth in either world or entity coordinates, depending on the dead reckoning algorithm used");
        linVel0TF.setMinimumSize(null);
        linVel0TF.setPreferredSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        physicsPane.add(linVel0TF, gridBagConstraints);

        linVel1TF.setColumns(5);
        linVel1TF.setToolTipText("Velocity of the entity relative to the rotating Earth in either world or entity coordinates, depending on the dead reckoning algorithm used");
        linVel1TF.setMinimumSize(null);
        linVel1TF.setPreferredSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        physicsPane.add(linVel1TF, gridBagConstraints);

        linVel2TF.setColumns(5);
        linVel2TF.setToolTipText("Velocity of the entity relative to the rotating Earth in either world or entity coordinates, depending on the dead reckoning algorithm used");
        linVel2TF.setMinimumSize(null);
        linVel2TF.setPreferredSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        physicsPane.add(linVel2TF, gridBagConstraints);

        linearAccelerationLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        linearAccelerationLabel.setText("linearAcceleration");
        linearAccelerationLabel.setToolTipText("Acceleration of the entity relative to the rotating Earth in either world or entity coordinates, depending on the dead reckoning algorithm used");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        physicsPane.add(linearAccelerationLabel, gridBagConstraints);

        linAccel0TF.setColumns(5);
        linAccel0TF.setToolTipText("Acceleration of the entity relative to the rotating Earth in either world or entity coordinates, depending on the dead reckoning algorithm used");
        linAccel0TF.setMinimumSize(null);
        linAccel0TF.setPreferredSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        physicsPane.add(linAccel0TF, gridBagConstraints);

        linAccel1TF.setColumns(5);
        linAccel1TF.setToolTipText("Acceleration of the entity relative to the rotating Earth in either world or entity coordinates, depending on the dead reckoning algorithm used");
        linAccel1TF.setMinimumSize(null);
        linAccel1TF.setPreferredSize(null);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        physicsPane.add(linAccel1TF, gridBagConstraints);

        linAccel2TF.setColumns(5);
        linAccel2TF.setToolTipText("Acceleration of the entity relative to the rotating Earth in either world or entity coordinates, depending on the dead reckoning algorithm used");
        linAccel2TF.setMinimumSize(null);
        linAccel2TF.setPreferredSize(null);
        linAccel2TF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                linAccel2TFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        physicsPane.add(linAccel2TF, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.weighty = 1.0;
        physicsPane.add(jLabel7, gridBagConstraints);

        collisionTypeCombo.setEditable(true);
        collisionTypeCombo.setModel(new DefaultComboBoxModel(CollisionType.values()));
        collisionTypeCombo.setToolTipText("type of collision: ELASTIC or INELASTIC");
        collisionTypeCombo.setMinimumSize(null);
        collisionTypeCombo.setPreferredSize(null);
        collisionTypeCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                collisionTypeComboActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        physicsPane.add(collisionTypeCombo, gridBagConstraints);

        espduTabbedPanel.addTab("Physics", physicsPane);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(15, 0, 0, 0);
        add(espduTabbedPanel, gridBagConstraints);

        nodeHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nodeHintPanel.setLayout(new java.awt.GridBagLayout());

        hintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel.setText("<html><p align='center'><b>EspduTransform</b> is a networked Transform node that can contain most nodes.</p>\n<p align='center'><b>EspduTransform</b> integrates functionality for the following DIS protocol data units (PDUs):</p>\n<p align='center'><b>EntityStatePdu</b>, <b>CollisionPdu</b>, <b>DetonatePdu</b>, <b>FirePdu</b>, <b>CreateEntity</b> and <b>RemoveEntity<b>.</p>\n<p align='center'>Each of these PDU packets use the IEEE Distributed Interactive Simulation (DIS) protocol.</p>");
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
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(nodeHintPanel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

private void plusButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_plusButtActionPerformed
  DefaultTableModel dftm = (DefaultTableModel)this.articParamTable.getModel();
  Object[] oa = new Object[6];
  oa[0]=dftm.getRowCount();
  oa[ARTICULATEDPARAMETER_DESIGNATOR_COL]     = ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERDESIGNATORARRAY_DFLT;
  oa[ARTICULATEDPARAMETER_CHANGE_COL]         = ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERCHANGEINDICATORARRAY_DFLT;
  oa[ARTICULATEDPARAMETER_ID_COL]             = ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERIDPARTATTACHEDTORARRAY_DFLT;
  oa[ARTICULATEDPARAMETER_TYPE_COL]           = ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERTYPEARRAY_DFLT;
  oa[ARTICULATEDPARAMETER_PARAMETERVALUE_COL] = ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERARRAY_DFLT;

  int row = articParamTable.getSelectedRow();
  if(row < 0)
    dftm.addRow(oa);
  else {
    dftm.insertRow(row+1, oa);
    renumber();
  }
}//GEN-LAST:event_plusButtActionPerformed
private void renumber()
{
  DefaultTableModel dftm = (DefaultTableModel)this.articParamTable.getModel();
  for(int i =0; i<dftm.getRowCount();i++)
    dftm.setValueAt(i, i, 0);
}

private void minusButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minusButtActionPerformed
  DefaultTableModel dftm = (DefaultTableModel)this.articParamTable.getModel();
  int row = articParamTable.getSelectedRow();
  if(row >=0) {
    dftm.removeRow(row);
    renumber();
  }
}//GEN-LAST:event_minusButtActionPerformed

private void upButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_upButtActionPerformed
  DefaultTableModel dftm = (DefaultTableModel)this.articParamTable.getModel();
  int row = articParamTable.getSelectedRow();
  if(row >=1){
    dftm.moveRow(row, row, row-1);
    articParamTable.setRowSelectionInterval(row-1, row-1);
    renumber();
  }
}//GEN-LAST:event_upButtActionPerformed

private void downButtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_downButtActionPerformed
  DefaultTableModel dftm = (DefaultTableModel)this.articParamTable.getModel();
  int row = articParamTable.getSelectedRow();
  int nrows = articParamTable.getRowCount();
  if(nrows >= 2 && row <(nrows-1)) {
    dftm.moveRow(row, row, row+1);
    articParamTable.setRowSelectionInterval(row+1, row+1);
    renumber();
  }
}//GEN-LAST:event_downButtActionPerformed

private void entDomainComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entDomainComboActionPerformed

  checkNumber("Entity domain",entDomainCombo.getSelectedItem(), PlatformDomain.class,8,evt);  // 8 bits
}//GEN-LAST:event_entDomainComboActionPerformed

private void entKindComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entKindComboActionPerformed

  checkNumber("Entity kind",entKindCombo.getSelectedItem(),EntityKind.class,8,evt);  // 8 bits
}//GEN-LAST:event_entKindComboActionPerformed

private void entCountryComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entCountryComboActionPerformed

  checkNumber("Entity country",entCountryCombo.getSelectedItem(), Country.class,16,evt);  // 16 bits
}//GEN-LAST:event_entCountryComboActionPerformed

private void forceIDComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_forceIDComboActionPerformed

  checkNumber("Force ID",forceIDCombo.getSelectedItem(),ForceID.class,8,evt); // 8 bits
}//GEN-LAST:event_forceIDComboActionPerformed

private void linAccel2TFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_linAccel2TFActionPerformed
{//GEN-HEADEREND:event_linAccel2TFActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_linAccel2TFActionPerformed

private void munStartPoint0TFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_munStartPoint0TFActionPerformed
{//GEN-HEADEREND:event_munStartPoint0TFActionPerformed
    // TODO add your handling code here:
}//GEN-LAST:event_munStartPoint0TFActionPerformed

private void normalizeRotationValuesButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_normalizeRotationValuesButtonActionPerformed
{//GEN-HEADEREND:event_normalizeRotationValuesButtonActionPerformed
    checkAngles(true);
    double normalizationFactor, x, y, z, angle;

    x = new SFDouble(rotation0TF.getText()).getValue();
    y = new SFDouble(rotation1TF.getText()).getValue();
    z = new SFDouble(rotation2TF.getText()).getValue();
    angle = new SFDouble(rotation3TF.getText()).getValue();
    normalizationFactor = Math.sqrt(x * x + y * y + z * z);
    if (normalizationFactor == 0.0)
    {
        NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                "Found zero-magnitude axis for rotation, reset to 0 1 0", NotifyDescriptor.WARNING_MESSAGE);
        DialogDisplayer.getDefault().notify(descriptor);
        rotation0TF.setText("0");
        rotation1TF.setText("1");
        rotation2TF.setText("0");
    }
    else
    {
        rotation0TF.setText(fiveDigitFormat.format(x / normalizationFactor));
        rotation1TF.setText(fiveDigitFormat.format(y / normalizationFactor));
        rotation2TF.setText(fiveDigitFormat.format(z / normalizationFactor));
    }
    if (angle == -0.0)
    {
        angle = 0.0;
    }
    while (angle < 0.0)
    {
        angle += 2.0 * Math.PI;
    }
    while (angle > 2.0 * Math.PI)
    {
        angle -= 2.0 * Math.PI;
    }
    rotation3TF.setText(radiansFormat.format(angle));
    rotation3TF.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");

    x = new SFDouble(scaleOrientation0TF.getText()).getValue();
    y = new SFDouble(scaleOrientation1TF.getText()).getValue();
    z = new SFDouble(scaleOrientation2TF.getText()).getValue();
    angle = new SFDouble(scaleOrientation3TF.getText()).getValue();
    normalizationFactor = Math.sqrt(x * x + y * y + z * z);
    if (normalizationFactor == 0.0)
    {
        NotifyDescriptor descriptor = new NotifyDescriptor.Message(
                "Found zero-magnitude axis for scaleOrientation, reset to 0 1 0", NotifyDescriptor.WARNING_MESSAGE);
        DialogDisplayer.getDefault().notify(descriptor);
        scaleOrientation0TF.setText("0");
        scaleOrientation1TF.setText("1");
        scaleOrientation2TF.setText("0");
    }
    else
    {
        scaleOrientation0TF.setText(fiveDigitFormat.format(x / normalizationFactor));
        scaleOrientation1TF.setText(fiveDigitFormat.format(y / normalizationFactor));
        scaleOrientation2TF.setText(fiveDigitFormat.format(z / normalizationFactor));
    }
    if (angle == -0.0)
    {
        angle = 0.0;
    }
    while (angle < 0.0)
    {
        angle += 2.0 * Math.PI;
    }
    while (angle > 2.0 * Math.PI)
    {
        angle -= 2.0 * Math.PI;
    }
    scaleOrientation3TF.setText(radiansFormat.format(angle));
    scaleOrientation3TF.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");
}//GEN-LAST:event_normalizeRotationValuesButtonActionPerformed

private void rotation3TFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_rotation3TFActionPerformed
{//GEN-HEADEREND:event_rotation3TFActionPerformed
    checkAngles (false);
}//GEN-LAST:event_rotation3TFActionPerformed

private void scaleOrientation3TFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_scaleOrientation3TFActionPerformed
{//GEN-HEADEREND:event_scaleOrientation3TFActionPerformed
    checkAngles (false);
}//GEN-LAST:event_scaleOrientation3TFActionPerformed

    private void markingTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_markingTFActionPerformed
    {//GEN-HEADEREND:event_markingTFActionPerformed
        setDefaultDEFname ();
        if (markingTF.getText().length() > 11)
        {
            JOptionPane.showMessageDialog(this, markingTF.getText().substring(0,10), "marking truncated to 11 characters", JOptionPane.WARNING_MESSAGE);
            markingTF.setText(markingTF.getText().substring(0,10));
        }
    }//GEN-LAST:event_markingTFActionPerformed

    private void collisionTypeComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_collisionTypeComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_collisionTypeComboActionPerformed

    private void detonationResultComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detonationResultComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_detonationResultComboActionPerformed

    private void detRelLoc0TFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detRelLoc0TFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_detRelLoc0TFActionPerformed

    private void warheadComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_warheadComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_warheadComboBoxActionPerformed
 
/**
 * Set caret to 0 for long names, check for integer entered, check for legal integer,
 * check for integer with matching enumeration, change if so.
 * @param name
 * @param o
 * @param cls
 * @param bits
 * @param evt
 */
  private void checkNumber(String name, Object o, Class cls, int bits, ActionEvent evt)
  {
    if (!evt.getActionCommand().equals("comboBoxChanged"))
      return;
    
    // First show the beginning of the string for the long names in the top
    final JComboBox combo = (JComboBox) evt.getSource();
    Component c = combo.getEditor().getEditorComponent();
    if (c instanceof JTextField) {
      ((JTextField) c).setCaretPosition(0);  // show beginning of string
    }

    if (o.getClass() == cls)  // If we're one of the enums, ok
      return;
    
    // Here if the user has typed in something
    int max = 1 << bits;
    try {
      int i = (new SFInt32(o.toString())).getValue();
      if (i < max) {
        // here if it's a good int; check if it's within the range of the enumeration, if so change it
        try {
          Field f = cls.getField("lookup");
          Object[] lookup = (Object[]) f.get(null);
          if (lookup.length > i) {
            // Here if we can replace the typed-in integer with an enumeration
            final Object obj = lookup[i];
            SwingUtilities.invokeLater(() -> {
                combo.setSelectedItem(obj);
            });
          }
        }
        catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException ex) 
          {}  // Here if I couldn't find a way to check for legal enumeration
        return;
      }
      // Here if good number but out of range
    }
    catch (NumberFormatException ex) {} // Here if bad number parse
    
    StringBuilder sb = new StringBuilder();
    sb.append(escapeXmlCharacters(name));
    sb.append(" must be an integer between 0 and ");
    sb.append(max);
    sb.append('.');

    JOptionPane.showMessageDialog(this, sb.toString(), "Format Error", JOptionPane.ERROR_MESSAGE);
  } 
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel IdNetworkPane;
    javax.swing.JTextField addressTF;
    javax.swing.JTextField appIdTF;
    private javax.swing.JLabel applicationIDLabel;
    javax.swing.JTable articParamTable;
    private javax.swing.JPanel articulatedParametersButtonPanel;
    private javax.swing.JPanel articulatedParametersPane;
    javax.swing.JTextField bboxCenterXTF;
    javax.swing.JTextField bboxCenterYTF;
    javax.swing.JTextField bboxCenterZTF;
    javax.swing.JTextField bboxSizeXTF;
    javax.swing.JTextField bboxSizeYTF;
    javax.swing.JTextField bboxSizeZTF;
    private javax.swing.JLabel categoryLabel;
    javax.swing.JTextField centerXTF;
    javax.swing.JTextField centerYTF;
    javax.swing.JTextField centerZTF;
    javax.swing.JComboBox<String> collisionTypeCombo;
    private javax.swing.JLabel collisionTypeLabel;
    private javax.swing.JLabel countryLabel;
    private javax.swing.JLabel deadReckoningLabel;
    javax.swing.JTextField deadReckoningTF;
    javax.swing.JTextField detLoc0TF;
    javax.swing.JTextField detLoc1TF;
    javax.swing.JTextField detLoc2TF;
    javax.swing.JTextField detRelLoc0TF;
    javax.swing.JTextField detRelLoc1TF;
    javax.swing.JTextField detRelLoc2TF;
    private javax.swing.JLabel detonationLocationLabel;
    private javax.swing.JLabel detonationRelativeLocationLabel;
    javax.swing.JComboBox<String> detonationResultCombo;
    private javax.swing.JLabel detonationResultLabel;
    private javax.swing.JLabel domainLabel;
    private javax.swing.JButton downButt;
    javax.swing.JCheckBox enabledCB;
    private javax.swing.JLabel enabledLab;
    javax.swing.JTextField entCategoryTF;
    javax.swing.JComboBox<String> entCountryCombo;
    javax.swing.JComboBox<String> entDomainCombo;
    javax.swing.JTextField entExtraTF;
    javax.swing.JComboBox<String> entKindCombo;
    javax.swing.JTextField entSpecificTF;
    javax.swing.JTextField entSubCategoryTF;
    private javax.swing.JPanel entityEventPane;
    private javax.swing.JLabel entityIDLab;
    javax.swing.JTextField entityIDTF;
    private javax.swing.JTabbedPane espduTabbedPanel;
    javax.swing.JTextField evAppIDTF;
    javax.swing.JTextField evEntIDTF;
    javax.swing.JTextField evNumTF;
    javax.swing.JTextField evSiteIDTF;
    private javax.swing.JLabel eventApplicationIDLabel;
    private javax.swing.JLabel eventEntityIDLabel;
    private javax.swing.JLabel eventNumberLabel;
    private javax.swing.JLabel eventSiteIDLabel;
    private javax.swing.JLabel extraLabel;
    javax.swing.JTextField fireMissionIdxTF;
    private javax.swing.JLabel fireMissionIndexLabel;
    private javax.swing.JLabel fired1Label;
    javax.swing.JTextField fired1TF;
    private javax.swing.JLabel fired2Label;
    javax.swing.JTextField fired2TF;
    private javax.swing.JLabel firingRangeLabel;
    javax.swing.JTextField firingRangeTF;
    private javax.swing.JLabel firingRateLabel;
    javax.swing.JTextField firingRateTF;
    javax.swing.JComboBox<String> forceIDCombo;
    private javax.swing.JLabel forceIDLabel;
    private javax.swing.JLabel fuseLabel;
    javax.swing.JTextField fuseTF;
    private javax.swing.JLabel hintLabel;
    private javax.swing.JLabel identificationParametersLabel;
    private javax.swing.JLabel identificationParametersLabel1;
    private javax.swing.JLabel identificationParametersLabel2;
    private javax.swing.JPanel identificationParametersPanel;
    private javax.swing.JPanel identificationParametersPanel1;
    private javax.swing.JPanel identificationParametersPanel2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel kindLabel;
    javax.swing.JTextField linAccel0TF;
    javax.swing.JTextField linAccel1TF;
    javax.swing.JTextField linAccel2TF;
    javax.swing.JTextField linVel0TF;
    javax.swing.JTextField linVel1TF;
    javax.swing.JTextField linVel2TF;
    private javax.swing.JLabel linearAccelerationLabel;
    private javax.swing.JLabel linearVelocityLabel;
    private javax.swing.JLabel markingLab;
    javax.swing.JTextField markingTF;
    private javax.swing.JLabel mcastRelayHostLabel;
    javax.swing.JTextField mcastRelayHostTF;
    javax.swing.JTextField mcastRelayPort;
    private javax.swing.JLabel mcastRelayPortLabel;
    private javax.swing.JLabel mcastaddrLab;
    private javax.swing.JButton minusButt;
    javax.swing.JTextField munAppIDTF;
    javax.swing.JTextField munEndPoint0TF;
    javax.swing.JTextField munEndPoint1TF;
    javax.swing.JTextField munEndPoint2TF;
    javax.swing.JTextField munEntityIDTF;
    javax.swing.JTextField munSiteIDTF;
    javax.swing.JTextField munStartPoint0TF;
    javax.swing.JTextField munStartPoint1TF;
    javax.swing.JTextField munStartPoint2TF;
    private javax.swing.JPanel munition1Pane;
    javax.swing.JPanel munition2Panel;
    private javax.swing.JLabel munitionApplicationIDLabel;
    private javax.swing.JLabel munitionEndPointLabel;
    private javax.swing.JLabel munitionEntityIDLabel;
    private javax.swing.JLabel munitionQuantityLabel;
    javax.swing.JTextField munitionQuantityTF;
    private javax.swing.JLabel munitionSiteIDLabel;
    private javax.swing.JLabel munitionStartPointLabel;
    javax.swing.JComboBox<String> networkModeComboBox;
    private javax.swing.JLabel networkModeLabel;
    private javax.swing.JPanel nodeHintPanel;
    private javax.swing.JButton normalizeRotationValuesButton;
    private javax.swing.JPanel physicsPane;
    private javax.swing.JButton plusButt;
    private javax.swing.JLabel portLabel;
    javax.swing.JTextField portTF;
    private javax.swing.JLabel readIntervalLabel;
    javax.swing.JTextField readIntervalTF;
    private javax.swing.JLabel readIntervalUnitsLabel;
    javax.swing.JTextField rotation0TF;
    javax.swing.JTextField rotation1TF;
    javax.swing.JTextField rotation2TF;
    javax.swing.JTextField rotation3TF;
    javax.swing.JCheckBox rtpHdrExpCB;
    private javax.swing.JLabel rtpHdrLab;
    javax.swing.JTextField scale0Tf;
    javax.swing.JTextField scale1TF;
    javax.swing.JTextField scale2TF;
    javax.swing.JTextField scaleOrientation0TF;
    javax.swing.JTextField scaleOrientation1TF;
    javax.swing.JTextField scaleOrientation2TF;
    javax.swing.JTextField scaleOrientation3TF;
    private javax.swing.JLabel siteIDLabel;
    javax.swing.JTextField siteIdTF;
    private javax.swing.JLabel specificLabel;
    private javax.swing.JLabel subCategoryLabel;
    private javax.swing.JPanel transformPane;
    javax.swing.JTextField translation0TF;
    javax.swing.JTextField translation1TF;
    javax.swing.JTextField translation2TF;
    private javax.swing.JLabel translationLab;
    private javax.swing.JButton upButt;
    javax.swing.JComboBox<String> warheadComboBox;
    private javax.swing.JLabel warheadLabel52;
    private javax.swing.JLabel writeIntervalLabel;
    javax.swing.JTextField writeIntervalTF;
    private javax.swing.JLabel writeIntervalUnitsLabel;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_ESPDUTRANSFORM";
  }

  private void checkAngles(boolean precedesNormalization)
  {
      // usability note:  can enter degree values (-6..+6) as (354..366) to provoke this conversion check
      double angle = new SFDouble(rotation3TF.getText()).getValue();
      rotation3TF.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");
      if (Math.abs(angle) > 2.0 * Math.PI)
      {
            String message;
            message = "<html><center>Large value provided for <b>rotation</b> angle, which is a radians value.<br/><br/>Convert <b>" + angle + " degrees</b> to <b>" +
                    radiansFormat.format((angle % 360.0) * Math.PI / 180.0) + " radians</b>";
            if (precedesNormalization)
                 message += " before normalization?";
            else message += "?";
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  message, "X3D angles are in radians", NotifyDescriptor.YES_NO_OPTION);
          if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
          {
              angle = (angle % 360.0) * Math.PI / 180.0;
              rotation3TF.setText(radiansFormat.format(angle));
              rotation3TF.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");
          }
      }
      angle = new SFDouble(scaleOrientation3TF.getText()).getValue();
      scaleOrientation3TF.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");
      if (Math.abs(angle) > 2.0 * Math.PI)
      {
            String message;
            message = "<html><center>Large value provided for <b>scaleOrientation</b> angle, which is a radians value.<br/><br/>Convert <b>" + angle + " degrees</b> to <b>" +
                    radiansFormat.format((angle % 360.0) * Math.PI / 180.0) + " radians</b>";
            if (precedesNormalization)
                 message += " before normalization?";
            else message += "?";
          NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                  message, "X3D angles are in radians", NotifyDescriptor.YES_NO_OPTION);
          if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
          {
              angle = (angle % 360.0) * Math.PI / 180.0;
              scaleOrientation3TF.setText(radiansFormat.format(angle));
              scaleOrientation3TF.setToolTipText(radiansFormat.format(angle) + " radians = " + singleDigitFormat.format(angle * 180.0 / Math.PI) + " degrees");
          }
      }
  }

  @Override
  public void unloadInput()
  {
    checkAngles (false);
    unLoadDEFUSE();

    espduTrans.setAddress(addressTF.getText().trim());
    espduTrans.setAppID(appIdTF.getText().trim());

    int nrows = articParamTable.getModel().getRowCount();
    espduTrans.setArticulationParameterCount(""+nrows);

    String[] desig = new String[nrows];
    String[] chang = new String[nrows];
    String[] idpar = new String[nrows];
    String[] typ   = new String[nrows];
    String[] parm = new String[nrows];
    for(int i=0;i<nrows;i++) {
      desig[i] = (String) articParamTable.getValueAt(i, 1);
      chang[i] = (String) articParamTable.getValueAt(i, 2);
      idpar[i] = (String) articParamTable.getValueAt(i, 3);
      typ[i]   = (String) articParamTable.getValueAt(i, 4);
      parm[i]  = (String) articParamTable.getValueAt(i, 5);
    }
    espduTrans.setArticulationParameterDesignatorArray(desig);
    espduTrans.setArticulationParameterChangeIndicatorArray(chang);
    espduTrans.setArticulationParameterIdPartAttachedToArray(idpar);
    espduTrans.setArticulationParameterTypeArray(typ);
    espduTrans.setArticulationParameterArray(parm);

    espduTrans.setCenterX(centerXTF.getText().trim());
    espduTrans.setCenterY(centerYTF.getText().trim());
    espduTrans.setCenterZ(centerZTF.getText().trim());
    espduTrans.setCollisionType(String.valueOf(collisionTypeCombo.getSelectedIndex()));
    espduTrans.setDeadReckoning(deadReckoningTF.getText().trim());
    espduTrans.setDetonationLocationX(detLoc0TF.getText().trim());
    espduTrans.setDetonationLocationY(detLoc1TF.getText().trim());
    espduTrans.setDetonationLocationZ(detLoc2TF.getText().trim());
    espduTrans.setDetonationRelativeLocationX(detRelLoc0TF.getText().trim());
    espduTrans.setDetonationRelativeLocationY(detRelLoc1TF.getText().trim());
    espduTrans.setDetonationRelativeLocationZ(detRelLoc2TF.getText().trim());
    espduTrans.setDetonationResult(detonationResultCombo.getSelectedItem().toString());
    espduTrans.setEnabled(""+enabledCB.isSelected());

    espduTrans.setEntityCategory(entCategoryTF.getText().trim());

    Object o = entCountryCombo.getSelectedItem();
    if(o instanceof Country)
      espduTrans.setEntityCountry(""+((Country)o).getValue());
    else
      espduTrans.setEntityCountry(o.toString());

    o = entDomainCombo.getSelectedItem();
    if(o instanceof PlatformDomain)
      espduTrans.setEntityDomain(""+((PlatformDomain)o).getValue());
    else
      espduTrans.setEntityDomain(o.toString());

    espduTrans.setEntityExtra(entExtraTF.getText().trim());
    espduTrans.setEntityID(entityIDTF.getText().trim());

    o = entKindCombo.getSelectedItem();
    if(o instanceof EntityKind)
      espduTrans.setEntityKind(""+((EntityKind)o).getValue());
    else
      espduTrans.setEntityKind(o.toString());

    espduTrans.setEntitySpecific(entSpecificTF.getText().trim());
    espduTrans.setEntitySubCategory(entSubCategoryTF.getText().trim());

    espduTrans.setEventApplicationID(evAppIDTF.getText().trim());
    espduTrans.setEventEntityID(evEntIDTF.getText().trim());
    espduTrans.setEventNumber(evNumTF.getText().trim());
    espduTrans.setEventSiteID(evSiteIDTF.getText().trim());

    espduTrans.setFired1(fired1TF.getText().trim());
    espduTrans.setFired2(fired2TF.getText().trim());
    espduTrans.setFireMissionIndex(fireMissionIdxTF.getText().trim());
    espduTrans.setFiringRange(firingRangeTF.getText().trim());
    espduTrans.setFiringRate(firingRateTF.getText().trim());

    o = forceIDCombo.getSelectedItem();
    if(o instanceof ForceID)
      espduTrans.setForceID(""+((ForceID)o).getValue());
    else
      espduTrans.setForceID(o.toString());

    espduTrans.setFuse(fuseTF.getText().trim());

    espduTrans.setLinearVelocityX(linVel0TF.getText().trim());
    espduTrans.setLinearVelocityY(linVel1TF.getText().trim());
    espduTrans.setLinearVelocityZ(linVel2TF.getText().trim());
    espduTrans.setLinearAccelerationX(linAccel0TF.getText().trim());
    espduTrans.setLinearAccelerationY(linAccel1TF.getText().trim());
    espduTrans.setLinearAccelerationZ(linAccel2TF.getText().trim());
    espduTrans.setMarking(markingTF.getText().trim());

    espduTrans.setMulticastRelayHost(mcastRelayHostTF.getText().trim());
    espduTrans.setMulticastRelayPort(mcastRelayPort.getText().trim());

    espduTrans.setMunitionApplicationID(munAppIDTF.getText().trim());
    espduTrans.setMunitionEndPointX(munEndPoint0TF.getText().trim());
    espduTrans.setMunitionEndPointY(munEndPoint1TF.getText().trim());
    espduTrans.setMunitionEndPointZ(munEndPoint2TF.getText().trim());
    espduTrans.setMunitionEntityID(munEntityIDTF.getText().trim());
    espduTrans.setMunitionQuantity(munitionQuantityTF.getText().trim());
    espduTrans.setMunitionSiteID(munSiteIDTF.getText().trim());
    espduTrans.setMunitionStartPointX(munStartPoint0TF.getText().trim());
    espduTrans.setMunitionStartPointY(munStartPoint1TF.getText().trim());
    espduTrans.setMunitionStartPointZ(munStartPoint2TF.getText().trim());
    espduTrans.setNetworkMode((String)networkModeComboBox.getSelectedItem());

    espduTrans.setPort(portTF.getText().trim());
    espduTrans.setReadInterval(readIntervalTF.getText().trim());

    espduTrans.setRotationX(rotation0TF.getText().trim());
    espduTrans.setRotationY(rotation1TF.getText().trim());
    espduTrans.setRotationZ(rotation2TF.getText().trim());
    espduTrans.setRotationAngle(rotation3TF.getText().trim());
    espduTrans.setScaleX(scale0Tf.getText().trim());
    espduTrans.setScaleY(scale1TF.getText().trim());
    espduTrans.setScaleZ(scale2TF.getText().trim());
    espduTrans.setScaleOrientationX(scaleOrientation0TF.getText().trim());
    espduTrans.setScaleOrientationY(scaleOrientation1TF.getText().trim());
    espduTrans.setScaleOrientationZ(scaleOrientation2TF.getText().trim());
    espduTrans.setScaleOrientationAngle(scaleOrientation3TF.getText().trim());

    espduTrans.setSiteID(siteIdTF.getText().trim());

    espduTrans.setTranslationX(translation0TF.getText().trim());
    espduTrans.setTranslationY(translation1TF.getText().trim());
    espduTrans.setTranslationZ(translation2TF.getText().trim());

    espduTrans.setWarhead(warheadComboBox.getSelectedItem().toString().trim());
    espduTrans.setWriteInterval(writeIntervalTF.getText().trim());

    espduTrans.setBboxCenterX(bboxCenterXTF.getText().trim());
    espduTrans.setBboxCenterY(bboxCenterYTF.getText().trim());
    espduTrans.setBboxCenterZ(bboxCenterZTF.getText().trim());
    espduTrans.setBboxSizeX(bboxSizeXTF.getText().trim());
    espduTrans.setBboxSizeY(bboxSizeYTF.getText().trim());
    espduTrans.setBboxSizeZ(bboxSizeZTF.getText().trim());

    espduTrans.setRtpHeaderExpected(""+rtpHdrExpCB.isSelected());
  }
  
class JTableColOneReadOnly extends JTable
  {
    @Override
    public boolean isCellEditable(int row, int column)
    {
      return column != 0;
    }   
  }
  
  class MyComboBoxRenderer extends JLabel implements ListCellRenderer
  {
    public MyComboBoxRenderer()
    {
      setOpaque(true);
      setHorizontalAlignment(LEFT);
      setVerticalAlignment(CENTER);
    }
    StringBuilder sb = new StringBuilder();

    @Override
    public Component getListCellRendererComponent(JList list,Object value,int index,boolean isSelected,boolean cellHasFocus)
    {
      if (isSelected) {
        setBackground(list.getSelectionBackground());
        setForeground(list.getSelectionForeground());
      }
      else {
        setBackground(list.getBackground());
        setForeground(list.getForeground());
      }
      
      if(value instanceof String) {
        setText((String)value);
        setToolTipText((String)value);
        return this;
      }

      sb.setLength(0);
      int num;
      if(value instanceof Country)
        num = ((Country)value).getValue();
      else if(value instanceof PlatformDomain)
        num = ((PlatformDomain)value).getValue();
      else if(value instanceof EntityKind)
        num = ((EntityKind)value).getValue();
      else if(value instanceof ForceID)
        num = ((ForceID)value).getValue();
      else {
        setText("--error--");
        setToolTipText("--error--");
        return this;
      }
      
      sb.append(num);
      sb.append(' ');
      sb.append(value);  // will toString() to correct string
      setText(sb.toString());
      setToolTipText(value.toString());
      return this;
    }
  }
}
