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
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.util.HelpCtx;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * RECEIVERPDUCustomizer.java
 * Created on 20 May 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class RECEIVERPDUCustomizer extends BaseCustomizer
{
  private final RECEIVERPDU receiverPdu;
  private final JTextComponent target;

  public RECEIVERPDUCustomizer(RECEIVERPDU receiverPdu, JTextComponent target)
  {
    super(receiverPdu);
    this.receiverPdu = receiverPdu;
    this.target = target;
    
    HelpCtx.setHelpIDString(this, "RECEIVERPDU_ELEM_HELPID");

    receiverPdu.setVisualizationSelectionAvailable(true); // must precede initComponents() interface initialization
    receiverPdu.setVisualizationTooltip("Add wireframe Box and axes to show boundingBox center and size (if defined)");
    
    initComponents();
    
    addressTF.setText(receiverPdu.getAddress()); //String
    appIDTF.setText(receiverPdu.getAppID());     //SFInt
    siteIDTF.setText(receiverPdu.getSiteID());
    enabledCB.setSelected(Boolean.parseBoolean(receiverPdu.isEnabled()));   //remember selected not enabled !!!
    entityIDTF.setText(receiverPdu.getEntityID());  //SFInt
    mcastRelayHostTF.setText(receiverPdu.getMulticastRelayHost()); //String
    mcastRelayPortTF.setText(receiverPdu.getMulticastRelayPort()); // SFInt
    networkModeCombo.setSelectedItem(receiverPdu.getNetworkMode()); // String
    portTF.setText(receiverPdu.getPort()); //SFInt
    radioIDTF.setText(receiverPdu.getRadioID());
    readIntervalTF.setText(receiverPdu.getReadInterval()); //SFFloat
    rtpHdrCB.setSelected(Boolean.parseBoolean(receiverPdu.isRtpHeaderExpected()));
    siteIDTF.setText(receiverPdu.getSiteID()); //SFInt
    whichGeomTF.setText(receiverPdu.getWhichGeometry());
    writeIntervalTF.setText(receiverPdu.getWriteInterval()); //SFFloat
    
    bboxCenterXTF.setText(receiverPdu.getBboxCenterX()); // SFFloat
    bboxCenterYTF.setText(receiverPdu.getBboxCenterY()); // SFFloat
    bboxCenterZTF.setText(receiverPdu.getBboxCenterZ()); // SFFloat
    bboxSizeXTF.setText(receiverPdu.getBboxSizeX()); // SFFloat
    bboxSizeYTF.setText(receiverPdu.getBboxSizeY()); // SFFloat
    bboxSizeZTF.setText(receiverPdu.getBboxSizeZ()); // SFFloat
    
    recvPowerTF.setText(receiverPdu.getReceivedPower());
    int state = Integer.parseInt(receiverPdu.getReceiverState());
    if  ((state >= 0) && (state <= 2))
         receiverStateCombo.setSelectedIndex(state);
    else
    {
        receiverStateCombo.setSelectedIndex(0); // off
        NotifyDescriptor descriptor = new NotifyDescriptor.Message(
            "Found illegal value for receiverState="+state+", reset to 0 (off)", NotifyDescriptor.WARNING_MESSAGE);
        DialogDisplayer.getDefault().notify(descriptor);
    }
    transAppIDTF.setText(receiverPdu.getTransmitterAppID());
    transEntIDTF.setText(receiverPdu.getEntityID());
    transRadioIDTF.setText(receiverPdu.getTransmitterRadioID());
    transSiteIDTF.setText(receiverPdu.getTransmitterSiteID());
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
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        radioIDTF = new javax.swing.JTextField();
        identificationParametersPanel1 = new javax.swing.JPanel();
        identificationParametersLabel1 = new javax.swing.JLabel();
        receiverPduPanel = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        recvPowerTF = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        spacerLabel = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        transAppIDTF = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        transEntIDTF = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        transRadioIDTF = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        transSiteIDTF = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        identificationParametersPanel = new javax.swing.JPanel();
        identificationParametersLabel = new javax.swing.JLabel();
        receiverStateCombo = new javax.swing.JComboBox<String>();
        receivedPowerUnitsLabel = new javax.swing.JLabel();
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
        jLabel1.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(jLabel1, gridBagConstraints);

        addressTF.setColumns(10);
        addressTF.setToolTipText("Multicast network address, or else \"localhost\" example: 224.2.181.145. ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(addressTF, gridBagConstraints);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel2.setText("applicationID");
        jLabel2.setToolTipText("simulation/exercise applicationID is unique for application at that site");
        jLabel2.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(jLabel2, gridBagConstraints);

        appIDTF.setColumns(10);
        appIDTF.setToolTipText("simulation/exercise applicationID is unique for application at that site");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(appIDTF, gridBagConstraints);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel3.setText("enabled");
        jLabel3.setToolTipText("Enable/disable network sensing and event passing");
        jLabel3.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(jLabel3, gridBagConstraints);

        enabledCB.setToolTipText("Enable/disable network sensing and event passing");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        commonPanel.add(enabledCB, gridBagConstraints);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel4.setText("entityID");
        jLabel4.setToolTipText("Simulation/exercise entityID is a unique ID for a single entity within that application. ");
        jLabel4.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(jLabel4, gridBagConstraints);

        entityIDTF.setColumns(10);
        entityIDTF.setToolTipText("Simulation/exercise entityID is a unique ID for a single entity within that application. ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(entityIDTF, gridBagConstraints);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel5.setText("multicastRelayHost");
        jLabel5.setToolTipText("Fallback server address if multicast not available locally");
        jLabel5.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(jLabel5, gridBagConstraints);

        mcastRelayHostTF.setColumns(10);
        mcastRelayHostTF.setToolTipText("Fallback server address if multicast not available locally");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(mcastRelayHostTF, gridBagConstraints);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel6.setText("multicastRelayPort");
        jLabel6.setToolTipText("Fallback server address if multicast not available locally");
        jLabel6.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(jLabel6, gridBagConstraints);

        mcastRelayPortTF.setColumns(10);
        mcastRelayPortTF.setToolTipText("Fallback server address if multicast not available locally");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(mcastRelayPortTF, gridBagConstraints);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel7.setText("networkMode");
        jLabel7.setToolTipText("Whether entity ignores the network (standAlone), sends DIS packets to network (networkWriter) or receives DIS packets from network (networkReader)");
        jLabel7.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(jLabel7, gridBagConstraints);

        networkModeCombo.setModel(new javax.swing.DefaultComboBoxModel<>( PDU_ATTR_NETWORKNODE_CHOICES ));
        networkModeCombo.setToolTipText("Whether entity ignores the network (standAlone), sends DIS packets to network (networkWriter) or receives DIS packets from network (networkReader)");
        networkModeCombo.setMinimumSize(new java.awt.Dimension(6, 22));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(networkModeCombo, gridBagConstraints);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel8.setText("port");
        jLabel8.setToolTipText("Network connection port number (EXAMPLE 3000) for sending or receiving DIS messages");
        jLabel8.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(jLabel8, gridBagConstraints);

        portTF.setColumns(10);
        portTF.setToolTipText("Network connection port number (EXAMPLE 3000) for sending or receiving DIS messages");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(portTF, gridBagConstraints);

        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel9.setText("readInterval");
        jLabel9.setToolTipText("seconds between read updates, 0 means no reading");
        jLabel9.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(jLabel9, gridBagConstraints);

        readIntervalTF.setColumns(10);
        readIntervalTF.setToolTipText("seconds between read updates, 0 means no reading");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(readIntervalTF, gridBagConstraints);

        readIntervalUnitsLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        readIntervalUnitsLabel.setText("seconds");
        readIntervalUnitsLabel.setToolTipText("seconds between read updates, 0 means no reading");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(readIntervalUnitsLabel, gridBagConstraints);

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel10.setText("rtpHeaderExpected");
        jLabel10.setToolTipText("whether RTP headers are prepended to DIS PDUs");
        jLabel10.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(jLabel10, gridBagConstraints);

        rtpHdrCB.setToolTipText("whether RTP headers are prepended to DIS PDUs");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 0, 3, 3);
        commonPanel.add(rtpHdrCB, gridBagConstraints);

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel11.setText("siteID");
        jLabel11.setToolTipText("Simulation/exercise siteID of the participating LAN or organization.");
        jLabel11.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(jLabel11, gridBagConstraints);

        siteIDTF.setColumns(10);
        siteIDTF.setToolTipText("Simulation/exercise siteID of the participating LAN or organization.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(siteIDTF, gridBagConstraints);

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel12.setText("whichGeometry");
        jLabel12.setToolTipText("Select geometry to render: -1 for no geometry, 0 for text trace, 1 for default geometry, (optional) higher values to render different states.");
        jLabel12.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(jLabel12, gridBagConstraints);

        whichGeomTF.setColumns(10);
        whichGeomTF.setToolTipText("Select geometry to render: -1 for no geometry, 0 for text trace, 1 for default geometry, (optional) higher values to render different states.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(whichGeomTF, gridBagConstraints);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel13.setText("writeInterval");
        jLabel13.setToolTipText("seconds between write updates, 0 means no writing");
        jLabel13.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(jLabel13, gridBagConstraints);

        writeIntervalTF.setColumns(10);
        writeIntervalTF.setToolTipText("seconds between write updates, 0 means no writing");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(writeIntervalTF, gridBagConstraints);

        writeIntervalUnitsLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        writeIntervalUnitsLabel.setText("seconds");
        writeIntervalUnitsLabel.setToolTipText("seconds between write updates, 0 means no writing");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(writeIntervalUnitsLabel, gridBagConstraints);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel14.setText("bboxCenter");
        jLabel14.setToolTipText("position offset from origin of local coordinate system");
        jLabel14.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(jLabel14, gridBagConstraints);

        bboxCenterXTF.setColumns(10);
        bboxCenterXTF.setToolTipText("position offset from origin of local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(bboxCenterXTF, gridBagConstraints);

        bboxCenterYTF.setColumns(10);
        bboxCenterYTF.setToolTipText("position offset from origin of local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(bboxCenterYTF, gridBagConstraints);

        bboxCenterZTF.setColumns(10);
        bboxCenterZTF.setToolTipText("position offset from origin of local coordinate system");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(bboxCenterZTF, gridBagConstraints);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel15.setText("bboxSize");
        jLabel15.setToolTipText("bounding box is automatically calculated, can also be specified as an optimization or constraint");
        jLabel15.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
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
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
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
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
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
        gridBagConstraints.gridy = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(bboxSizeZTF, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 10;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(2, 2, 2, 2);
        commonPanel.add(jLabel16, gridBagConstraints);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel17.setText("radioID");
        jLabel17.setToolTipText("Identifies a particular radio within a given entity");
        jLabel17.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(jLabel17, gridBagConstraints);

        radioIDTF.setColumns(10);
        radioIDTF.setToolTipText("Identifies a particular radio within a given entity");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.weightx = 0.33;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(radioIDTF, gridBagConstraints);

        identificationParametersPanel1.setLayout(new java.awt.GridBagLayout());

        identificationParametersLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        identificationParametersLabel1.setText("<html><p align=\"center\">DIS</p> <p align=\"center\">entity</p> <p align=\"center\">&nbsp;&nbsp;identification&nbsp;&nbsp;</p> <p align=\"center\">parameters</p>");
        identificationParametersLabel1.setToolTipText("Combination of parameters provides unique identification of radio used by this DIS entity");
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
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.weightx = 100.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        commonPanel.add(identificationParametersPanel1, gridBagConstraints);

        jTabbedPane1.addTab("PDU common", commonPanel);

        receiverPduPanel.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 0, 0, 0));
        receiverPduPanel.setLayout(new java.awt.GridBagLayout());

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel18.setText("receivedPower");
        jLabel18.setToolTipText("receivedPower indicates radio frequency (RF) power received, in decibel-milliwatts (dBm), after applying any propagation loss and antenna gain");
        jLabel18.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        receiverPduPanel.add(jLabel18, gridBagConstraints);

        recvPowerTF.setColumns(5);
        recvPowerTF.setToolTipText("receivedPower indicates radio frequency (RF) power received, in decibel-milliwatts (dBm), after applying any propagation loss and antenna gain");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        receiverPduPanel.add(recvPowerTF, gridBagConstraints);

        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel19.setText("receiverState");
        jLabel19.setToolTipText("receiverState indicates if receiver is currently idle or busy via one of these enumerated values: 0 = off, 1 = on but not receiving, or 2 = on and receiving.");
        jLabel19.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        receiverPduPanel.add(jLabel19, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(6, 3, 3, 3);
        receiverPduPanel.add(spacerLabel, gridBagConstraints);

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel20.setText("transmitterApplicationID");
        jLabel20.setToolTipText("Simulation/exercise transmitterApplicationID is unique for transmitter application at that site.");
        jLabel20.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 43, 3, 3);
        receiverPduPanel.add(jLabel20, gridBagConstraints);

        transAppIDTF.setColumns(5);
        transAppIDTF.setToolTipText("Simulation/exercise transmitterApplicationID is unique for transmitter application at that site.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        receiverPduPanel.add(transAppIDTF, gridBagConstraints);

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel21.setText("transmitterEntityID");
        jLabel21.setToolTipText("Simulation/exercise transmitterEntityID is a unique ID for a single entity within that application.");
        jLabel21.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        receiverPduPanel.add(jLabel21, gridBagConstraints);

        transEntIDTF.setColumns(5);
        transEntIDTF.setToolTipText("Simulation/exercise transmitterEntityID is a unique ID for a single entity within that application.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        receiverPduPanel.add(transEntIDTF, gridBagConstraints);

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel22.setText("transmitterRadioID");
        jLabel22.setToolTipText("Identifies a particular radio within a given entity.");
        jLabel22.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        receiverPduPanel.add(jLabel22, gridBagConstraints);

        transRadioIDTF.setColumns(5);
        transRadioIDTF.setToolTipText("Identifies a particular radio within a given entity.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        receiverPduPanel.add(transRadioIDTF, gridBagConstraints);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel23.setText("transmitterSiteID");
        jLabel23.setToolTipText("Simulation/exercise transmitterSiteID of the participating LAN or organization.");
        jLabel23.setMinimumSize(new java.awt.Dimension(6, 15));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        receiverPduPanel.add(jLabel23, gridBagConstraints);

        transSiteIDTF.setColumns(5);
        transSiteIDTF.setToolTipText("Simulation/exercise transmitterSiteID of the participating LAN or organization.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        receiverPduPanel.add(transSiteIDTF, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        receiverPduPanel.add(jLabel24, gridBagConstraints);

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
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridheight = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.VERTICAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        receiverPduPanel.add(identificationParametersPanel, gridBagConstraints);

        receiverStateCombo.setModel(new javax.swing.DefaultComboBoxModel<>(RECEIVERPDU_ATTR_RECEIVERSTATE_CHOICES));
        receiverStateCombo.setToolTipText("receiverState indicates if receiver is currently idle or busy via one of these enumerated values: 0 = off, 1 = on but not receiving, or 2 = on and receiving.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 23);
        receiverPduPanel.add(receiverStateCombo, gridBagConstraints);

        receivedPowerUnitsLabel.setText("decibel-milliwatts (dBm)");
        receivedPowerUnitsLabel.setToolTipText("receivedPower indicates radio frequency (RF) power received, in decibel-milliwatts (dBm), after applying any propagation loss and antenna gain");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LINE_START;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 23);
        receiverPduPanel.add(receivedPowerUnitsLabel, gridBagConstraints);

        jTabbedPane1.addTab("ReceiverPdu", receiverPduPanel);

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
        hintLabel.setText("<html><p align='center'><b>ReceiverPdu</b> is a networked Protocol Data Unit (PDU) information node that</p>\n<p align='center'>transmits the state of radio frequency (RF) receivers modeled in a simulation.</p>\n<p align='center'><b>ReceiverPdu</b> packets use the IEEE Distributed Interactive Simulation (DIS) protocol.</p>");
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
  
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField addressTF;
    private javax.swing.JTextField appIDTF;
    private javax.swing.JTextField bboxCenterXTF;
    private javax.swing.JTextField bboxCenterYTF;
    private javax.swing.JTextField bboxCenterZTF;
    private javax.swing.JTextField bboxSizeXTF;
    private javax.swing.JTextField bboxSizeYTF;
    private javax.swing.JTextField bboxSizeZTF;
    private javax.swing.JPanel commonPanel;
    private org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel1;
    private javax.swing.JCheckBox enabledCB;
    private javax.swing.JTextField entityIDTF;
    private javax.swing.JLabel hintLabel;
    private javax.swing.JLabel identificationParametersLabel;
    private javax.swing.JLabel identificationParametersLabel1;
    private javax.swing.JPanel identificationParametersPanel;
    private javax.swing.JPanel identificationParametersPanel1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField mcastRelayHostTF;
    private javax.swing.JTextField mcastRelayPortTF;
    private javax.swing.JComboBox<String> networkModeCombo;
    private javax.swing.JPanel nodeHintPanel;
    private javax.swing.JTextField portTF;
    private javax.swing.JTextField radioIDTF;
    private javax.swing.JTextField readIntervalTF;
    private javax.swing.JLabel readIntervalUnitsLabel;
    private javax.swing.JLabel receivedPowerUnitsLabel;
    private javax.swing.JPanel receiverPduPanel;
    private javax.swing.JComboBox<String> receiverStateCombo;
    private javax.swing.JTextField recvPowerTF;
    private javax.swing.JCheckBox rtpHdrCB;
    private javax.swing.JTextField siteIDTF;
    private javax.swing.JLabel spacerLabel;
    private javax.swing.JTextField transAppIDTF;
    private javax.swing.JTextField transEntIDTF;
    private javax.swing.JTextField transRadioIDTF;
    private javax.swing.JTextField transSiteIDTF;
    private javax.swing.JTextField whichGeomTF;
    private javax.swing.JTextField writeIntervalTF;
    private javax.swing.JLabel writeIntervalUnitsLabel;
    // End of variables declaration//GEN-END:variables

  @Override
  public String getNameKey()
  {
    return "NAME_X3D_RECEIVERPDU";
  }

  @Override
  public void unloadInput()
  {
    unLoadDEFUSE();
    receiverPdu.setAddress(addressTF.getText().trim());
    receiverPdu.setAppID(appIDTF.getText().trim());
    receiverPdu.setEnabled(""+enabledCB.isSelected());
    receiverPdu.setEntityID(entityIDTF.getText().trim());
    receiverPdu.setMulticastRelayHost(mcastRelayHostTF.getText().trim());
    receiverPdu.setMulticastRelayPort(mcastRelayPortTF.getText().trim());
    receiverPdu.setNetworkMode((String)networkModeCombo.getSelectedItem());
    receiverPdu.setPort(portTF.getText().trim());
    receiverPdu.setRadioID(radioIDTF.getText().trim());
    receiverPdu.setReadInterval(readIntervalTF.getText().trim());
    receiverPdu.setRtpHeaderExpected(""+rtpHdrCB.isSelected());
    receiverPdu.setSiteID(siteIDTF.getText().trim());
    receiverPdu.setWhichGeometry(whichGeomTF.getText().trim());
    receiverPdu.setWriteInterval(writeIntervalTF.getText().trim());
    receiverPdu.setBboxCenterX(bboxCenterXTF.getText().trim());
    receiverPdu.setBboxCenterY(bboxCenterYTF.getText().trim());
    receiverPdu.setBboxCenterZ(bboxCenterZTF.getText().trim());
    receiverPdu.setBboxSizeX(bboxSizeXTF.getText().trim());
    receiverPdu.setBboxSizeY(bboxSizeYTF.getText().trim());
    receiverPdu.setBboxSizeZ(bboxSizeZTF.getText().trim());
    
    receiverPdu.setReceivedPower(recvPowerTF.getText().trim());
    receiverPdu.setReceiverState(String.valueOf(receiverStateCombo.getSelectedIndex()));
    receiverPdu.setTransmitterAppID(transAppIDTF.getText().trim());
    receiverPdu.setTransmitterEntID(transEntIDTF.getText().trim());
    receiverPdu.setTransmitterRadioID(transRadioIDTF.getText().trim());
    receiverPdu.setTransmitterSiteID(transSiteIDTF.getText().trim());
  }
}
