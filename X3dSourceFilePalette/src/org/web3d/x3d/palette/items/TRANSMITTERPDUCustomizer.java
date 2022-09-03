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

import javax.swing.text.JTextComponent;
import org.openide.util.HelpCtx;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * TRANSMITTERPDUCustomizer.java
 * Created on 20 May 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class TRANSMITTERPDUCustomizer extends BaseCustomizer
{
  private final TRANSMITTERPDU transmitterPdu;
  private final JTextComponent target;

  public TRANSMITTERPDUCustomizer(TRANSMITTERPDU transmitterPdu, JTextComponent target)
  {
    super(transmitterPdu);
    this.transmitterPdu = transmitterPdu;
    this.target = target;
    
    HelpCtx.setHelpIDString(this, "TRANSMITTERPDU_ELEM_HELPID");

    transmitterPdu.setVisualizationSelectionAvailable(true); // must precede initComponents() interface initialization
    transmitterPdu.setVisualizationTooltip("Add wireframe Box and axes to show boundingBox center and size (if defined)");
    
    initComponents();
    
    addressTF.setText(transmitterPdu.getAddress()); //String
    appIDTF.setText(transmitterPdu.getAppID());     //SFInt
    siteIDTF.setText(transmitterPdu.getSiteID());
    enabledCB.setSelected(Boolean.parseBoolean(transmitterPdu.isEnabled()));   //remember selected not enabled !!!
    entityIDTF.setText(transmitterPdu.getEntityID());  //SFInt
    mcastRelayHostTF.setText(transmitterPdu.getMulticastRelayHost()); //String
    mcastRelayPortTF.setText(transmitterPdu.getMulticastRelayPort()); // SFInt
    networkModeCombo.setSelectedItem(transmitterPdu.getNetworkMode()); // String
    portTF.setText(transmitterPdu.getPort()); //SFInt
    radioIDTF.setText(transmitterPdu.getRadioID());
    readIntervalTF.setText(transmitterPdu.getReadInterval()); //SFFloat
    rtpHdrCB.setSelected(Boolean.parseBoolean(transmitterPdu.isRtpHeaderExpected()));
    siteIDTF.setText(transmitterPdu.getSiteID()); //SFInt
    whichGeomTF.setText(transmitterPdu.getWhichGeometry());
    writeIntervalTF.setText(transmitterPdu.getWriteInterval()); //SFFloat
    
    bboxCenterXTF.setText(transmitterPdu.getBboxCenterX()); // SFFloat
    bboxCenterYTF.setText(transmitterPdu.getBboxCenterY()); // SFFloat
    bboxCenterZTF.setText(transmitterPdu.getBboxCenterZ()); // SFFloat
    bboxSizeXTF.setText(transmitterPdu.getBboxSizeX()); // SFFloat
    bboxSizeYTF.setText(transmitterPdu.getBboxSizeY()); // SFFloat
    bboxSizeZTF.setText(transmitterPdu.getBboxSizeZ()); // SFFloat
    
    antLoc0TF.setText(transmitterPdu.getAntennaLocation0());
    antLoc1TF.setText(transmitterPdu.getAntennaLocation1());
    antLoc2.setText(transmitterPdu.getAntennaLocation2());
    antPatLenTF.setText(transmitterPdu.getAntennaPatternLength());
    antPatTypeCombo.setSelectedIndex((new SFInt32(transmitterPdu.getAntennaPatternType())).getValue());
    cryptoKeyIDTF.setText(transmitterPdu.getCryptoKeyID());
    cryptoSysCombo.setSelectedIndex((new SFInt32(transmitterPdu.getCryptoSystem())).getValue());
    frequencyTF.setText( transmitterPdu.getFrequency());
    inputSourceCombo.setSelectedIndex((new SFInt32(transmitterPdu.getInputSource())).getValue());
    lenModParmsTF.setText(transmitterPdu.getLengthOfModulationParameters());
    modTypDetailTF.setText(transmitterPdu.getModulationTypeDetail());
    modTypeMajTCombo.setSelectedIndex((new SFInt32(transmitterPdu.getModulationTypeMajor())).getValue());
    modTypeSpreadSpCombo.setSelectedIndex((new SFInt32(transmitterPdu.getModulationTypeSpreadSpectrum())).getValue());
    modTypeSysCombo.setSelectedIndex((new SFInt32(transmitterPdu.getModulationTypeSystem())).getValue());
    powerTF.setText(transmitterPdu.getPower());
    radEntTypCatTF.setText(transmitterPdu.getRadioEntityTypeCategory());
    radEntTypCountryTF.setText(transmitterPdu.getRadioEntityTypeCountry());
    radEntTypNomenclatureTF.setText(transmitterPdu.getRadioEntityTypeNomenclature());
    radEntTypNomenVersTF.setText(transmitterPdu.getRadioEntityTypeNomenclatureVersion());
    radEntTypeDomainCombo.setSelectedIndex((new SFInt32(transmitterPdu.getRadioEntityTypeDomain())).getValue());
    radEntTypeKindTF.setText(transmitterPdu.getRadioEntityTypeKind());
    relAntLoc0TF.setText(transmitterPdu.getRelativeAntennaLocation0());
    relAntLoc1TF.setText(transmitterPdu.getRelativeAntennaLocation1());
    relAntLoc2TF.setText(transmitterPdu.getRelativeAntennaLocation2());
    transFreqBandWidTF.setText(transmitterPdu.getTransmitFrequencyBandwidth());
    transStateCombo.setSelectedIndex((new SFInt32(transmitterPdu.getTransmitState())).getValue());
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
        jTabbedPane1 = new javax.swing.JTabbedPane();
        commonPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        addressTF = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        appIDTF = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        enabledCB = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        entityIDTF = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        mcastRelayHostTF = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        mcastRelayPortTF = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        networkModeCombo = new javax.swing.JComboBox<String>();
        jLabel8 = new javax.swing.JLabel();
        portTF = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        readIntervalTF = new javax.swing.JTextField();
        readIntervalUnitsLabel = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        rtpHdrCB = new javax.swing.JCheckBox();
        jLabel11 = new javax.swing.JLabel();
        siteIDTF = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        whichGeomTF = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        writeIntervalTF = new javax.swing.JTextField();
        writeIntervalUnitsLabel = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        bboxCenterXTF = new javax.swing.JTextField();
        bboxCenterYTF = new javax.swing.JTextField();
        bboxCenterZTF = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        bboxSizeXTF = new javax.swing.JTextField();
        bboxSizeYTF = new javax.swing.JTextField();
        bboxSizeZTF = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        radioIDTF = new javax.swing.JTextField();
        spacerLabel = new javax.swing.JLabel();
        identificationParametersPanel = new javax.swing.JPanel();
        identificationParametersLabel = new javax.swing.JLabel();
        transmitterPduPanel = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        antLoc0TF = new javax.swing.JTextField();
        antLoc1TF = new javax.swing.JTextField();
        antLoc2 = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        antPatLenTF = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        cryptoKeyIDTF = new javax.swing.JTextField();
        cryptoSystemLab = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        frequencyTF = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        inputSourceCombo = new javax.swing.JComboBox<String>();
        jLabel24 = new javax.swing.JLabel();
        lenModParmsTF = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        modTypeMajTCombo = new javax.swing.JComboBox<String>();
        jLabel27 = new javax.swing.JLabel();
        modTypeSpreadSpCombo = new javax.swing.JComboBox<String>();
        jLabel28 = new javax.swing.JLabel();
        modTypeSysCombo = new javax.swing.JComboBox<String>();
        jLabel29 = new javax.swing.JLabel();
        powerTF = new javax.swing.JTextField();
        jLabel30 = new javax.swing.JLabel();
        radEntTypCatTF = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        radEntTypCountryTF = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        radEntTypeDomainCombo = new javax.swing.JComboBox<String>();
        jLabel33 = new javax.swing.JLabel();
        radEntTypeKindTF = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        radEntTypNomenclatureTF = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        radEntTypNomenVersTF = new javax.swing.JTextField();
        jLabel36 = new javax.swing.JLabel();
        relAntLoc0TF = new javax.swing.JTextField();
        relAntLoc1TF = new javax.swing.JTextField();
        relAntLoc2TF = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        transFreqBandWidTF = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        transStateCombo = new javax.swing.JComboBox<String>();
        antPatTypeCombo = new javax.swing.JComboBox<String>();
        cryptoSysCombo = new javax.swing.JComboBox<String>();
        modTypDetailTF = new javax.swing.JTextField();
        nodeHintPanel = new javax.swing.JPanel();
        hintLabel = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel1, gridBagConstraints);

        commonPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 0, 0, 0));
        commonPanel.setLayout(new java.awt.GridBagLayout());

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel1.setText("address");
        jLabel1.setToolTipText("Multicast network address, or else \"localhost\" example: 224.2.181.145. ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 55, 3, 3);
        commonPanel.add(jLabel1, gridBagConstraints);

        addressTF.setColumns(10);
        addressTF.setToolTipText("Multicast network address, or else \"localhost\" example: 224.2.181.145. ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(addressTF, gridBagConstraints);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel2.setText("applicationID");
        jLabel2.setToolTipText("simulation/exercise applicationID is unique for application at that site");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(jLabel2, gridBagConstraints);

        appIDTF.setColumns(10);
        appIDTF.setToolTipText("simulation/exercise applicationID is unique for application at that site");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(appIDTF, gridBagConstraints);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel3.setText("enabled");
        jLabel3.setToolTipText("Enable/disable network sensing and event passing");
        jLabel3.setMinimumSize(new java.awt.Dimension(6, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(jLabel3, gridBagConstraints);

        enabledCB.setToolTipText("Enable/disable network sensing and event passing");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        commonPanel.add(enabledCB, gridBagConstraints);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel4.setText("entityID");
        jLabel4.setToolTipText("Simulation/exercise entityID is a unique ID for a single entity within that application. ");
        jLabel4.setMinimumSize(new java.awt.Dimension(6, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(jLabel4, gridBagConstraints);

        entityIDTF.setColumns(10);
        entityIDTF.setToolTipText("Simulation/exercise entityID is a unique ID for a single entity within that application. ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(entityIDTF, gridBagConstraints);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel5.setText("multicastRelayHost");
        jLabel5.setToolTipText("Fallback server address if multicast not available locally");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 55, 3, 3);
        commonPanel.add(jLabel5, gridBagConstraints);

        mcastRelayHostTF.setColumns(10);
        mcastRelayHostTF.setToolTipText("Fallback server address if multicast not available locally");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(mcastRelayHostTF, gridBagConstraints);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel6.setText("multicastRelayPort");
        jLabel6.setToolTipText("Fallback server port if multicast not available locally");
        jLabel6.setMinimumSize(new java.awt.Dimension(6, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(jLabel6, gridBagConstraints);

        mcastRelayPortTF.setColumns(10);
        mcastRelayPortTF.setToolTipText("Fallback server port if multicast not available locally");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(mcastRelayPortTF, gridBagConstraints);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel7.setText("networkMode");
        jLabel7.setToolTipText("Whether entity ignores the network (standAlone), sends DIS packets to network (networkWriter) or receives DIS packets from network (networkReader)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 55, 3, 3);
        commonPanel.add(jLabel7, gridBagConstraints);

        networkModeCombo.setModel(new javax.swing.DefaultComboBoxModel<>( PDU_ATTR_NETWORKNODE_CHOICES ));
        networkModeCombo.setToolTipText("Whether entity ignores the network (standAlone), sends DIS packets to network (networkWriter) or receives DIS packets from network (networkReader)");
        networkModeCombo.setMinimumSize(new java.awt.Dimension(6, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(networkModeCombo, gridBagConstraints);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel8.setText("port");
        jLabel8.setToolTipText("Network connection port number (EXAMPLE 3000) for sending or receiving DIS messages");
        jLabel8.setMinimumSize(new java.awt.Dimension(6, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(jLabel8, gridBagConstraints);

        portTF.setColumns(10);
        portTF.setToolTipText("Network connection port number (EXAMPLE 3000) for sending or receiving DIS messages");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(portTF, gridBagConstraints);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel9.setText("readInterval");
        jLabel9.setToolTipText("seconds between read updates, 0 means no reading");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(jLabel9, gridBagConstraints);

        readIntervalTF.setColumns(10);
        readIntervalTF.setToolTipText("seconds between read updates, 0 means no reading");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(readIntervalTF, gridBagConstraints);

        readIntervalUnitsLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        readIntervalUnitsLabel.setText("seconds");
        readIntervalUnitsLabel.setToolTipText("seconds between read updates, 0 means no reading");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(readIntervalUnitsLabel, gridBagConstraints);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel10.setText("rtpHeaderExpected");
        jLabel10.setToolTipText("whether RTP headers are prepended to DIS PDUs");
        jLabel10.setMinimumSize(new java.awt.Dimension(6, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 55, 3, 3);
        commonPanel.add(jLabel10, gridBagConstraints);

        rtpHdrCB.setToolTipText("whether RTP headers are prepended to DIS PDUs");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        commonPanel.add(rtpHdrCB, gridBagConstraints);

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel11.setText("siteID");
        jLabel11.setToolTipText("Simulation/exercise siteID of the participating LAN or organization.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(jLabel11, gridBagConstraints);

        siteIDTF.setColumns(10);
        siteIDTF.setToolTipText("Simulation/exercise siteID of the participating LAN or organization.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(siteIDTF, gridBagConstraints);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel12.setText("whichGeometry");
        jLabel12.setToolTipText("Select geometry to render: -1 for no geometry, 0 for text trace, 1 for default geometry, (optional) higher values to render different states.");
        jLabel12.setMinimumSize(new java.awt.Dimension(6, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(jLabel12, gridBagConstraints);

        whichGeomTF.setColumns(10);
        whichGeomTF.setToolTipText("Select geometry to render: -1 for no geometry, 0 for text trace, 1 for default geometry, (optional) higher values to render different states.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(whichGeomTF, gridBagConstraints);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel13.setText("writeInterval");
        jLabel13.setToolTipText("seconds between write updates, 0 means no writing");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(jLabel13, gridBagConstraints);

        writeIntervalTF.setColumns(10);
        writeIntervalTF.setToolTipText("seconds between write updates, 0 means no writing");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(writeIntervalTF, gridBagConstraints);

        writeIntervalUnitsLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        writeIntervalUnitsLabel.setText("seconds");
        writeIntervalUnitsLabel.setToolTipText("seconds between write updates, 0 means no writing");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(writeIntervalUnitsLabel, gridBagConstraints);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel14.setText("bboxCenter");
        jLabel14.setToolTipText("position offset from origin of local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 55, 3, 3);
        commonPanel.add(jLabel14, gridBagConstraints);

        bboxCenterXTF.setColumns(10);
        bboxCenterXTF.setToolTipText("position offset from origin of local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(bboxCenterXTF, gridBagConstraints);

        bboxCenterYTF.setColumns(10);
        bboxCenterYTF.setToolTipText("position offset from origin of local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(bboxCenterYTF, gridBagConstraints);

        bboxCenterZTF.setColumns(10);
        bboxCenterZTF.setToolTipText("position offset from origin of local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(bboxCenterZTF, gridBagConstraints);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel15.setText("bboxSize");
        jLabel15.setToolTipText("bounding box is automatically calculated, can also be specified as an optimization or constraint");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 55, 3, 3);
        commonPanel.add(jLabel15, gridBagConstraints);

        bboxSizeXTF.setColumns(10);
        bboxSizeXTF.setToolTipText("bounding box is automatically calculated, can also be specified as an optimization or constraint");
        bboxSizeXTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bboxSizeXTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(bboxSizeXTF, gridBagConstraints);

        bboxSizeYTF.setColumns(10);
        bboxSizeYTF.setToolTipText("bounding box is automatically calculated, can also be specified as an optimization or constraint");
        bboxSizeYTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bboxSizeYTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(bboxSizeYTF, gridBagConstraints);

        bboxSizeZTF.setColumns(10);
        bboxSizeZTF.setToolTipText("bounding box is automatically calculated, can also be specified as an optimization or constraint");
        bboxSizeZTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bboxSizeZTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(bboxSizeZTF, gridBagConstraints);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel17.setText("radioID");
        jLabel17.setToolTipText("Identifies a particular radio within a given entity");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(jLabel17, gridBagConstraints);

        radioIDTF.setToolTipText("Identifies a particular radio within a given entity");
        radioIDTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioIDTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(radioIDTF, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        commonPanel.add(spacerLabel, gridBagConstraints);

        identificationParametersPanel.setLayout(new java.awt.GridBagLayout());

        identificationParametersLabel.setText("<html><p align=\"center\">DIS</p> <p align=\"center\">entity</p> <p align=\"center\">&nbsp;&nbsp;identification&nbsp;&nbsp;</p> <p align=\"center\">parameters</p>");
        identificationParametersLabel.setToolTipText("Combination of parameters provides unique identification of radio used by this DIS entity");
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
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        commonPanel.add(identificationParametersPanel, gridBagConstraints);

        jTabbedPane1.addTab("PDU common", commonPanel);

        transmitterPduPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 0, 0, 0));
        transmitterPduPanel.setMinimumSize(new java.awt.Dimension(5, 5));
        transmitterPduPanel.setLayout(new java.awt.GridBagLayout());

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel18.setText("antennaLocation");
        jLabel18.setToolTipText("World coordinates for antenna location");
        jLabel18.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(jLabel18, gridBagConstraints);

        antLoc0TF.setColumns(8);
        antLoc0TF.setToolTipText("World coordinates for antenna location");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.3333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(antLoc0TF, gridBagConstraints);

        antLoc1TF.setColumns(8);
        antLoc1TF.setToolTipText("World coordinates for antenna location");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.3333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(antLoc1TF, gridBagConstraints);

        antLoc2.setColumns(8);
        antLoc2.setToolTipText("World coordinates for antenna location");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.3333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(antLoc2, gridBagConstraints);

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel19.setText("antennaPatternLength");
        jLabel19.setToolTipText("Length of antenna pattern");
        jLabel19.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(jLabel19, gridBagConstraints);

        antPatLenTF.setColumns(8);
        antPatLenTF.setToolTipText("Length of antenna pattern");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.3333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(antPatLenTF, gridBagConstraints);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel20.setText("antennaPatternType");
        jLabel20.setToolTipText("Antenna shape pattern: 0 for omnidirectional, 1 for beam, 2 for spherical harmonic (deprecated), or optional higher value ");
        jLabel20.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(jLabel20, gridBagConstraints);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel21.setText("cryptoKeyID");
        jLabel21.setToolTipText("Nonzero value corresponding to the simulated cryptographic key. Enumerations value 0 indicates plain (unencrypted) communications");
        jLabel21.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(jLabel21, gridBagConstraints);

        cryptoKeyIDTF.setColumns(8);
        cryptoKeyIDTF.setToolTipText("Nonzero value corresponding to the simulated cryptographic key. Enumerations value 0 indicates plain (unencrypted) communications");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.3333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(cryptoKeyIDTF, gridBagConstraints);

        cryptoSystemLab.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        cryptoSystemLab.setText("cryptoSystem");
        cryptoSystemLab.setToolTipText("Indicates type of crypto system being used, even if the encryption equipment is not keyed. Value 0 for No Encryption Device, higher enumerations values correspond to other specific equipment");
        cryptoSystemLab.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(cryptoSystemLab, gridBagConstraints);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel22.setText("frequency");
        jLabel22.setToolTipText("Transmission frequency in Hz");
        jLabel22.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(jLabel22, gridBagConstraints);

        frequencyTF.setColumns(8);
        frequencyTF.setToolTipText("Transmission frequency in Hz");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.3333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(frequencyTF, gridBagConstraints);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel23.setText("inputSource");
        jLabel23.setToolTipText("Source of transmission input");
        jLabel23.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(jLabel23, gridBagConstraints);

        inputSourceCombo.setModel(new javax.swing.DefaultComboBoxModel<>(TRANSMITTERPDU_ATTR_INPUTSOURCE_CHOICES));
        inputSourceCombo.setToolTipText("Source of transmission input");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(inputSourceCombo, gridBagConstraints);

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel24.setText("lengthOfModulationParameters");
        jLabel24.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(jLabel24, gridBagConstraints);

        lenModParmsTF.setColumns(8);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.3333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(lenModParmsTF, gridBagConstraints);

        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel25.setText("modulationTypeDetail");
        jLabel25.setToolTipText("Enumeration containing detailed information depending on the major modulation type");
        jLabel25.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(jLabel25, gridBagConstraints);

        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel26.setText("modulationTypeMajor");
        jLabel26.setToolTipText("Enumeration containing major classification of the modulation type");
        jLabel26.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(jLabel26, gridBagConstraints);

        modTypeMajTCombo.setModel(new javax.swing.DefaultComboBoxModel<String>(TRANSMITTERPDU_ATTR_MODULATIONTYPEMAJOR_CHOICES));
        modTypeMajTCombo.setToolTipText("Enumeration containing major classification of the modulation type");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(modTypeMajTCombo, gridBagConstraints);

        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel27.setText("modulationTypeSpreadSpectrum");
        jLabel27.setToolTipText("Indicates the spread spectrum technique or combination of spread spectrum techniques in use");
        jLabel27.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 13, 3, 3);
        transmitterPduPanel.add(jLabel27, gridBagConstraints);

        modTypeSpreadSpCombo.setModel(new javax.swing.DefaultComboBoxModel<>(TRANSMITTERPDU_ATTR_MODULATIONTYPESPREADSPECTRUM_CHOICES));
        modTypeSpreadSpCombo.setToolTipText("Indicates the spread spectrum technique or combination of spread spectrum techniques in use");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(modTypeSpreadSpCombo, gridBagConstraints);

        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel28.setText("modulationTypeSystem");
        jLabel28.setToolTipText("Specifies radio system associated with this Transmitter PDU");
        jLabel28.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(jLabel28, gridBagConstraints);

        modTypeSysCombo.setModel(new javax.swing.DefaultComboBoxModel<>(TRANSMITTERPDU_ATTR_MODULATIONTYPESYSTEM_CHOICES));
        modTypeSysCombo.setToolTipText("Specifies radio system associated with this Transmitter PDU");
        modTypeSysCombo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                modTypeSysComboActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(modTypeSysCombo, gridBagConstraints);

        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel29.setText("power");
        jLabel29.setToolTipText("Power that radio would be capable of outputting if on");
        jLabel29.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(jLabel29, gridBagConstraints);

        powerTF.setColumns(8);
        powerTF.setToolTipText("Power that radio would be capable of outputting if on");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.3333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(powerTF, gridBagConstraints);

        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel30.setText("radioEntityTypeCategory");
        jLabel30.setToolTipText("Enumeration containing EntityType of transmitter radio");
        jLabel30.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(jLabel30, gridBagConstraints);

        radEntTypCatTF.setColumns(8);
        radEntTypCatTF.setToolTipText("Enumeration containing EntityType of transmitter radio");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.3333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(radEntTypCatTF, gridBagConstraints);

        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel31.setText("radioEntityTypeCountry");
        jLabel31.setToolTipText("country to which the design of the entity or its design specification is attributed");
        jLabel31.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(jLabel31, gridBagConstraints);

        radEntTypCountryTF.setColumns(8);
        radEntTypCountryTF.setToolTipText("country to which the design of the entity or its design specification is attributed");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.3333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(radEntTypCountryTF, gridBagConstraints);

        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel32.setText("radioEntityTypeDomain");
        jLabel32.setToolTipText("domain in which the entity operates");
        jLabel32.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(jLabel32, gridBagConstraints);

        radEntTypeDomainCombo.setModel(new javax.swing.DefaultComboBoxModel<>(TRANSMITTERPDU_ATTR_RADIOENTITYTYPEDOMAIN_CHOICES));
        radEntTypeDomainCombo.setToolTipText("domain in which the entity operates");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(radEntTypeDomainCombo, gridBagConstraints);

        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel33.setText("radioEntityTypeKind");
        jLabel33.setToolTipText("Enumerations value for kind of Entity Type");
        jLabel33.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(jLabel33, gridBagConstraints);

        radEntTypeKindTF.setColumns(8);
        radEntTypeKindTF.setToolTipText("Enumerations value for kind of Entity Type");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.3333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(radEntTypeKindTF, gridBagConstraints);

        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel34.setText("radioEntityTypeNomenclature");
        jLabel34.setToolTipText("nomenclature (name) for a particular emitter");
        jLabel34.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(jLabel34, gridBagConstraints);

        radEntTypNomenclatureTF.setColumns(8);
        radEntTypNomenclatureTF.setToolTipText("nomenclature (name) for a particular emitter");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.3333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(radEntTypNomenclatureTF, gridBagConstraints);

        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel35.setText("radioEntityTypeNomenclatureVersion");
        jLabel35.setToolTipText("Named equipment version number");
        jLabel35.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        transmitterPduPanel.add(jLabel35, gridBagConstraints);

        radEntTypNomenVersTF.setColumns(8);
        radEntTypNomenVersTF.setToolTipText("Named equipment version number");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.3333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(radEntTypNomenVersTF, gridBagConstraints);

        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel36.setText("relativeAntennaLocation");
        jLabel36.setToolTipText("Relative coordinates for antenna location");
        jLabel36.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(jLabel36, gridBagConstraints);

        relAntLoc0TF.setColumns(8);
        relAntLoc0TF.setToolTipText("Relative coordinates for antenna location");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.3333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(relAntLoc0TF, gridBagConstraints);

        relAntLoc1TF.setColumns(8);
        relAntLoc1TF.setToolTipText("Relative coordinates for antenna location");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.3333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(relAntLoc1TF, gridBagConstraints);

        relAntLoc2TF.setColumns(8);
        relAntLoc2TF.setToolTipText("Relative coordinates for antenna location");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.3333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(relAntLoc2TF, gridBagConstraints);

        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel37.setText("transmitFrequencyBandwith");
        jLabel37.setToolTipText("Bandwidth of the particular transmitter measured between the half-power (-3 dB) points");
        jLabel37.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(jLabel37, gridBagConstraints);

        transFreqBandWidTF.setColumns(8);
        transFreqBandWidTF.setToolTipText("Bandwidth of the particular transmitter measured between the half-power (-3 dB) points");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.3333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(transFreqBandWidTF, gridBagConstraints);

        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel38.setText("transmitState");
        jLabel38.setToolTipText("Specify radio transmission state");
        jLabel38.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(jLabel38, gridBagConstraints);

        transStateCombo.setModel(new javax.swing.DefaultComboBoxModel<>(TRANSMITTERPDU_ATTR_TRANSMITSTATE_CHOICES));
        transStateCombo.setToolTipText("Specify radio transmission state");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 13;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(transStateCombo, gridBagConstraints);

        antPatTypeCombo.setModel(new javax.swing.DefaultComboBoxModel<>(TRANSMITTERPDU_ATTR_ANTENNAPATTERNTYPE_CHOICES));
        antPatTypeCombo.setToolTipText("Antenna shape pattern: 0 for omnidirectional, 1 for beam, 2 for spherical harmonic (deprecated), or optional higher value ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(antPatTypeCombo, gridBagConstraints);

        cryptoSysCombo.setModel(new javax.swing.DefaultComboBoxModel<>(TRANSMITTERPDU_ATTR_CRYPTOSYSTEM_CHOICES));
        cryptoSysCombo.setToolTipText("Indicates type of crypto system being used, even if the encryption equipment is not keyed. Value 0 for No Encryption Device, higher enumerations values correspond to other specific equipment");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(cryptoSysCombo, gridBagConstraints);

        modTypDetailTF.setColumns(8);
        modTypDetailTF.setToolTipText("Enumeration containing detailed information depending on the major modulation type");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.3333;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        transmitterPduPanel.add(modTypDetailTF, gridBagConstraints);

        jTabbedPane1.addTab("TransmitterPdu", transmitterPduPanel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(jTabbedPane1, gridBagConstraints);

        nodeHintPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        nodeHintPanel.setLayout(new java.awt.GridBagLayout());

        hintLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        hintLabel.setText("<html><p align='center'><b>TransmitterPdu</b> is a networked Protocol Data Unit (PDU) information node that</p>\n<p align='center'>provides detailed information about a radio transmitter modeled in a simulation.</p>\n<p align='center'><b>TransmitterPdu</b> packets use the IEEE Distributed Interactive Simulation (DIS) protocol.</p>");
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

    private void bboxSizeXTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bboxSizeXTFActionPerformed
        checkVisualizable ();
    }//GEN-LAST:event_bboxSizeXTFActionPerformed

    private void bboxSizeYTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bboxSizeYTFActionPerformed
        checkVisualizable ();
    }//GEN-LAST:event_bboxSizeYTFActionPerformed

    private void bboxSizeZTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bboxSizeZTFActionPerformed
        checkVisualizable ();
    }//GEN-LAST:event_bboxSizeZTFActionPerformed

    private void modTypeSysComboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_modTypeSysComboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_modTypeSysComboActionPerformed

    private void radioIDTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioIDTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_radioIDTFActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField addressTF;
    private javax.swing.JTextField antLoc0TF;
    private javax.swing.JTextField antLoc1TF;
    private javax.swing.JTextField antLoc2;
    private javax.swing.JTextField antPatLenTF;
    private javax.swing.JComboBox<String> antPatTypeCombo;
    private javax.swing.JTextField appIDTF;
    private javax.swing.JTextField bboxCenterXTF;
    private javax.swing.JTextField bboxCenterYTF;
    private javax.swing.JTextField bboxCenterZTF;
    private javax.swing.JTextField bboxSizeXTF;
    private javax.swing.JTextField bboxSizeYTF;
    private javax.swing.JTextField bboxSizeZTF;
    private javax.swing.JPanel commonPanel;
    private javax.swing.JTextField cryptoKeyIDTF;
    private javax.swing.JComboBox<String> cryptoSysCombo;
    private javax.swing.JLabel cryptoSystemLab;
    private org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel1;
    private javax.swing.JCheckBox enabledCB;
    private javax.swing.JTextField entityIDTF;
    private javax.swing.JTextField frequencyTF;
    private javax.swing.JLabel hintLabel;
    private javax.swing.JLabel identificationParametersLabel;
    private javax.swing.JPanel identificationParametersPanel;
    private javax.swing.JComboBox<String> inputSourceCombo;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField lenModParmsTF;
    private javax.swing.JTextField mcastRelayHostTF;
    private javax.swing.JTextField mcastRelayPortTF;
    private javax.swing.JTextField modTypDetailTF;
    private javax.swing.JComboBox<String> modTypeMajTCombo;
    private javax.swing.JComboBox<String> modTypeSpreadSpCombo;
    private javax.swing.JComboBox<String> modTypeSysCombo;
    private javax.swing.JComboBox<String> networkModeCombo;
    private javax.swing.JPanel nodeHintPanel;
    private javax.swing.JTextField portTF;
    private javax.swing.JTextField powerTF;
    private javax.swing.JTextField radEntTypCatTF;
    private javax.swing.JTextField radEntTypCountryTF;
    private javax.swing.JTextField radEntTypNomenVersTF;
    private javax.swing.JTextField radEntTypNomenclatureTF;
    private javax.swing.JComboBox<String> radEntTypeDomainCombo;
    private javax.swing.JTextField radEntTypeKindTF;
    private javax.swing.JTextField radioIDTF;
    private javax.swing.JTextField readIntervalTF;
    private javax.swing.JLabel readIntervalUnitsLabel;
    private javax.swing.JTextField relAntLoc0TF;
    private javax.swing.JTextField relAntLoc1TF;
    private javax.swing.JTextField relAntLoc2TF;
    private javax.swing.JCheckBox rtpHdrCB;
    private javax.swing.JTextField siteIDTF;
    private javax.swing.JLabel spacerLabel;
    private javax.swing.JTextField transFreqBandWidTF;
    private javax.swing.JComboBox<String> transStateCombo;
    private javax.swing.JPanel transmitterPduPanel;
    private javax.swing.JTextField whichGeomTF;
    private javax.swing.JTextField writeIntervalTF;
    private javax.swing.JLabel writeIntervalUnitsLabel;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_TRANSMITTERPDU";
  }

  @Override
  public void unloadInput()
  {
    unLoadDEFUSE();
    transmitterPdu.setAddress(addressTF.getText().trim());
    transmitterPdu.setAppID(appIDTF.getText().trim());
    transmitterPdu.setEnabled(""+enabledCB.isSelected());
    transmitterPdu.setEntityID(entityIDTF.getText().trim());
    transmitterPdu.setMulticastRelayHost(mcastRelayHostTF.getText().trim());
    transmitterPdu.setMulticastRelayPort(mcastRelayPortTF.getText().trim());
    transmitterPdu.setNetworkMode((String)networkModeCombo.getSelectedItem());
    transmitterPdu.setPort(portTF.getText().trim());
    transmitterPdu.setRadioID(radioIDTF.getText().trim());
    transmitterPdu.setReadInterval(readIntervalTF.getText().trim());
    transmitterPdu.setRtpHeaderExpected(""+rtpHdrCB.isSelected());
    transmitterPdu.setSiteID(siteIDTF.getText().trim());
    transmitterPdu.setWhichGeometry(whichGeomTF.getText().trim());
    transmitterPdu.setWriteInterval(writeIntervalTF.getText().trim());
    transmitterPdu.setBboxCenterX(bboxCenterXTF.getText().trim());
    transmitterPdu.setBboxCenterY(bboxCenterYTF.getText().trim());
    transmitterPdu.setBboxCenterZ(bboxCenterZTF.getText().trim());
    transmitterPdu.setBboxSizeX(bboxSizeXTF.getText().trim());
    transmitterPdu.setBboxSizeY(bboxSizeYTF.getText().trim());
    transmitterPdu.setBboxSizeZ(bboxSizeZTF.getText().trim());
    
    transmitterPdu.setAntennaLocation0(antLoc0TF.getText().trim());
    transmitterPdu.setAntennaLocation1(antLoc1TF.getText().trim());
    transmitterPdu.setAntennaLocation2(antLoc2.getText().trim());
    transmitterPdu.setAntennaPatternLength(antPatLenTF.getText().trim());
    transmitterPdu.setAntennaPatternType(""+antPatTypeCombo.getSelectedIndex());
    transmitterPdu.setCryptoKeyID(cryptoKeyIDTF.getText().trim());
    transmitterPdu.setCryptoSystem(""+cryptoSysCombo.getSelectedIndex());
    transmitterPdu.setFrequency(frequencyTF.getText().trim());
    transmitterPdu.setInputSource(""+inputSourceCombo.getSelectedIndex());
    transmitterPdu.setLengthOfModulationParameters(lenModParmsTF.getText().trim());
    transmitterPdu.setModulationTypeDetail(modTypDetailTF.getText().trim());
    transmitterPdu.setModulationTypeMajor(""+modTypeMajTCombo.getSelectedIndex());
    transmitterPdu.setModulationTypeSpreadSpectrum(""+modTypeSpreadSpCombo.getSelectedIndex());
    transmitterPdu.setModulationTypeSystem(""+modTypeSysCombo.getSelectedIndex());
    transmitterPdu.setPower(powerTF.getText().trim());
    transmitterPdu.setRadioEntityTypeCategory(radEntTypCatTF.getText().trim());
    transmitterPdu.setRadioEntityTypeCountry(radEntTypCountryTF.getText().trim());
    transmitterPdu.setRadioEntityTypeNomenclature(radEntTypNomenclatureTF.getText().trim());
    transmitterPdu.setRadioEntityTypeNomenclatureVersion(radEntTypNomenVersTF.getText().trim());
    transmitterPdu.setRadioEntityTypeDomain(""+radEntTypeDomainCombo.getSelectedIndex());
    transmitterPdu.setRadioEntityTypeKind(radEntTypeKindTF.getText().trim());
    transmitterPdu.setRelativeAntennaLocation0(relAntLoc0TF.getText().trim());
    transmitterPdu.setRelativeAntennaLocation1(relAntLoc1TF.getText().trim());
    transmitterPdu.setRelativeAntennaLocation2(relAntLoc2TF.getText().trim());
    transmitterPdu.setTransmitFrequencyBandwidth(transFreqBandWidTF.getText().trim());
    transmitterPdu.setTransmitState(""+transStateCombo.getSelectedIndex());
  }
}
