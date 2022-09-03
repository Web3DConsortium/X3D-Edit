/*
* Copyright (c) 1995-2021 held by the author(s) .  All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions
* are met:
*
*  * Redistributions of source code must retain the above copyright
*       notice, this list of conditions and the following disclaimer.
*  * Redistributions in binary form must reproduce the above copyright
*       notice, this list of conditions and the following disclaimer
*       in the documentation and/or other materials provided with the
*       distribution.
*  * Neither the names of the Naval Postgraduate School (NPS)
*       Modeling Virtual Environments and Simulation (MOVES) Institute
*       (http://www.nps.edu and https://MovesInstitute.nps.edu)
*       nor the names of its contributors may be used to endorse or
*       promote products derived from this software without specific
*       prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
* "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
* LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
* FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
* COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
* INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
* BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
* CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
* ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
* POSSIBILITY OF SUCH DAMAGE.
*/
package org.web3d.x3d.dis;

import edu.nps.moves.dis7.pdus.EntityID;
import edu.nps.moves.dis7.pdus.EntityStatePdu;
import edu.nps.moves.dis7.pdus.EntityMarking;
import edu.nps.moves.dis7.pdus.EulerAngles;
import edu.nps.moves.dis7.pdus.Vector3Double;

import edu.nps.moves.dis7.utilities.DisThreadedNetworkInterface;
import edu.nps.moves.dis7.utilities.PduFactory;

import java.io.Serializable;

import java.net.InetAddress;
import java.net.MulticastSocket;

import java.util.Hashtable;
import java.util.logging.Logger;

import javax.swing.JLabel;
import javax.swing.JTextField;

import org.openide.util.NbBundle;

import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;
import org.openide.windows.TopComponent;
import org.openide.windows.WindowManager;

import org.web3d.x3d.options.X3dOptions;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * Top component which displays something.
 */
final public class DisEspduSenderControlPanel extends TopComponent
{
  private static DisEspduSenderControlPanel instance;
  /** path to the icon used by the component and its open action */
  //    static final String ICON_PATH = "SET/PATH/TO/ICON/HERE";

  private static final String PREFERRED_ID = "DisTesterTopComponent";

  public static final String DEFAULT_MARKING     = "X3D-Edit4.0";    // 11 characters max
  public static final String DEFAULT_DIS_ADDRESS = "239.1.2.3";  // Destination address, typically multicast group
  public static final String DEFAULT_PORT        = "3000";          // Destination port
  public static final String DEFAULT_SITEID      = "0";
  public static final String DEFAULT_APPID       = "1";
  public static final String DEFAULT_ENTITYID    = "2";
  public static final int    DEFAULT_DIS_PORT    = 3000;

  public static final int PDU_LENGTH = 144; // TODO confirm

  private MulticastSocket socket = null; // Socket for sending packets
  private InetAddress     address;       // Multicast address we use
  private int             timestamp = 0; // Timestamp, with is in this case simply a monotonically increasing long
  private EntityStatePdu  espdu;         // Single, shared ESPDU--created once, position & orientation updated by the various sliders
  private int             port;
  private EntityMarking   testMarking = new EntityMarking();

  // Gui settings
  private final int rotationSliderMin = 0;
  private final int rotationSliderMax = 360;

  private final int pitchRotationSliderMin = -180;
  private final int pitchRotationSliderMax =  180;

  private final int rotationSliderMajorTickSpacing = 90;
  private final int rotationSliderMinorTickSpacing =  1; // 1 degree accuracy default

  private final int translationSliderMin = -100; // 1m accuracy default
  private final int translationSliderMax =  100;
  private final int translationSliderMajorTickSpacing = 50;
  private final int translationSliderMinorTickSpacing =  1;

  private final String DEFAULT_TRANSLATION_SCALE = "1.00";

  private boolean joystickInstalled = false; // TODO
  private boolean joystickActive    = false;
  
  private DisThreadedNetworkInterface distni;

  private DisEspduSenderControlPanel()
  {
    initComponents(); // initialize

    //Create the label table for degrees on rotation sliders
    Hashtable<Integer,JLabel> zeroStartRotationLabelTable = new Hashtable<>();
    zeroStartRotationLabelTable.put(rotationSliderMin,                                                   new JLabel("<html>&nbsp;0"));
    zeroStartRotationLabelTable.put(rotationSliderMin + (rotationSliderMax - rotationSliderMin) * 1 / 4, new JLabel("<html>&nbsp;90&deg;</html>"));
    zeroStartRotationLabelTable.put(rotationSliderMin + (rotationSliderMax - rotationSliderMin) * 2 / 4, new JLabel("<html>&nbsp;180&deg;</html>"));
    zeroStartRotationLabelTable.put(rotationSliderMin + (rotationSliderMax - rotationSliderMin) * 3 / 4, new JLabel("<html>&nbsp;270&deg;</html>"));
    zeroStartRotationLabelTable.put(rotationSliderMax,                                                   new JLabel("<html>&nbsp;360&deg;</html>"));

    Hashtable<Integer,JLabel> zeroCenterRotationLabelTable = new Hashtable<>();
    zeroCenterRotationLabelTable.put(pitchRotationSliderMin,                                                             new JLabel("<html>&nbsp;-180&deg;</html>"));
    zeroCenterRotationLabelTable.put(pitchRotationSliderMin + (pitchRotationSliderMax - pitchRotationSliderMin) * 1 / 4, new JLabel("<html>&nbsp;-90&deg;</html>"));
    zeroCenterRotationLabelTable.put(pitchRotationSliderMin + (pitchRotationSliderMax - pitchRotationSliderMin) * 2 / 4, new JLabel("<html>&nbsp;0&deg;</html>"));
    zeroCenterRotationLabelTable.put(pitchRotationSliderMin + (pitchRotationSliderMax - pitchRotationSliderMin) * 3 / 4, new JLabel("<html>&nbsp;90&deg;</html>"));
    zeroCenterRotationLabelTable.put(pitchRotationSliderMax,                                                             new JLabel("<html>&nbsp;180&deg;</html>"));

    pitchSlider.setLabelTable(zeroCenterRotationLabelTable);
     rollSlider.setLabelTable(zeroCenterRotationLabelTable);
      yawSlider.setLabelTable(zeroStartRotationLabelTable);

    int fontSize = zeroPositionButton.getFont().getSize();
    zeroPositionButton.setFont(zeroPositionButton.getFont().deriveFont(fontSize-2));
    zeroRotationButton.setFont(zeroPositionButton.getFont().deriveFont(fontSize-2));
    initNetworking(); // initialize

    setName(NbBundle.getMessage(DisEspduSenderControlPanel.class, "CTL_DisTesterTopComponent"));
    setToolTipText(NbBundle.getMessage(DisEspduSenderControlPanel.class, "HINT_DisTesterTopComponent"));
//        setIcon(Utilities.loadImage(ICON_PATH, true));

    // TODO initialize joystick configuration, setJoystickInstalled
  }

  private void initNetworking()
  {
    // Create the espdu
    PduFactory pduFactory = new PduFactory();
    espdu = pduFactory.makeEntityStatePdu();
    testMarking.setCharacters(DEFAULT_MARKING.getBytes());
    espdu.setMarking(testMarking);
    
    // Set the entity ID, the unique identifer of an entity in the world.
    EntityID id = espdu.getEntityID();   // Globally unique identifier for the entity
    id.setSiteID(       (new SFInt32(siteIDTF.  getText())).getValue());
    id.setApplicationID((new SFInt32(appIDTF.   getText())).getValue());
    id.setEntityID(     (new SFInt32(entityIDTF.getText())).getValue());
    espdu.setEntityID(id);

    // create multicast socket, join group
    port = (new SFInt32(portTF.getText().trim()).getValue());
    distni = new DisThreadedNetworkInterface(addressTF.getText().trim(), port);
  }

  private void showMsg(String s)
  {
    InputOutput io = IOProvider.getDefault().getIO("Output", false);
    io.select();
    io.getOut().println(s);
  }

  private double getScale(JTextField tf)
  {
    try {
      double d = (new SFDouble(tf.getText())).getValue();
      return d;
    }
    catch (NumberFormatException ex) {
      String s = tf.getText().trim();
      s = s.length() > 0 ? s : "<blank>";
      showMsg("Expected numeric value, bad parse of " + s);
      return 1.0d;
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

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        resetPanel = new javax.swing.JPanel();
        enabledCheckBox = new javax.swing.JCheckBox();
        zeroPositionButton = new javax.swing.JButton();
        zeroRotationButton = new javax.swing.JButton();
        joystickCheckBox = new javax.swing.JCheckBox();
        translationPanel = new javax.swing.JPanel();
        xSlider = new javax.swing.JSlider();
        ySlider = new javax.swing.JSlider();
        zSlider = new javax.swing.JSlider();
        scaleLabel = new javax.swing.JLabel();
        xScaleTF = new javax.swing.JTextField();
        yScaleTF = new javax.swing.JTextField();
        zScaleTF = new javax.swing.JTextField();
        jSeparator4 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        yLabel = new javax.swing.JLabel();
        xLabel = new javax.swing.JLabel();
        zLabel = new javax.swing.JLabel();
        xTextField = new javax.swing.JTextField();
        yTextField = new javax.swing.JTextField();
        metersLabel = new javax.swing.JLabel();
        zTextField = new javax.swing.JTextField();
        rotationPanel = new javax.swing.JPanel();
        yawLabel = new javax.swing.JLabel();
        yawSlider = new javax.swing.JSlider();
        pitchLabel = new javax.swing.JLabel();
        pitchSlider = new javax.swing.JSlider();
        rollLabel = new javax.swing.JLabel();
        rollSlider = new javax.swing.JSlider();
        rollDegreesTextField = new javax.swing.JTextField();
        pitchDegreesTextField = new javax.swing.JTextField();
        yawDegreesTextField = new javax.swing.JTextField();
        rollRadiansTextField = new javax.swing.JTextField();
        pitchRadiansTextField = new javax.swing.JTextField();
        yawRadiansTextField = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator2 = new javax.swing.JSeparator();
        degreesLabel = new javax.swing.JLabel();
        radiansLabel = new javax.swing.JLabel();
        DisSettingsPanel = new javax.swing.JPanel();
        markingLabel = new javax.swing.JLabel();
        markingTF = new javax.swing.JTextField();
        addressLabel = new javax.swing.JLabel();
        addressTF = new javax.swing.JTextField();
        portLabel = new javax.swing.JLabel();
        portTF = new javax.swing.JTextField();
        siteIDLabel = new javax.swing.JLabel();
        siteIDTF = new javax.swing.JTextField();
        appIDLabel = new javax.swing.JLabel();
        appIDTF = new javax.swing.JTextField();
        entityIDLabel = new javax.swing.JLabel();
        entityIDTF = new javax.swing.JTextField();
        verticalSpaceLabel = new javax.swing.JLabel();

        setLayout(new java.awt.GridBagLayout());

        jScrollPane1.setMaximumSize(new java.awt.Dimension(32767, 520));

        jPanel1.setMinimumSize(new java.awt.Dimension(100, 100));
        jPanel1.setLayout(new java.awt.GridBagLayout());

        resetPanel.setLayout(new java.awt.GridBagLayout());

        enabledCheckBox.setSelected(true);
        org.openide.awt.Mnemonics.setLocalizedText(enabledCheckBox, org.openide.util.NbBundle.getMessage(DisEspduSenderControlPanel.class, "DisEspduSenderControlPanel.enabledCheckBox.text")); // NOI18N
        enabledCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(DisEspduSenderControlPanel.class, "DisEspduSenderControlPanel.enabledCheckBox.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        resetPanel.add(enabledCheckBox, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(zeroPositionButton, org.openide.util.NbBundle.getMessage(DisEspduSenderControlPanel.class, "DisEspduSenderControlPanel.zeroPositionButton.text")); // NOI18N
        zeroPositionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zeroPositionButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(3, 10, 3, 10);
        resetPanel.add(zeroPositionButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(zeroRotationButton, org.openide.util.NbBundle.getMessage(DisEspduSenderControlPanel.class, "DisEspduSenderControlPanel.zeroRotationButton.text")); // NOI18N
        zeroRotationButton.setActionCommand(org.openide.util.NbBundle.getMessage(DisEspduSenderControlPanel.class, "DisEspduSenderControlPanel.zeroRotationButton.actionCommand")); // NOI18N
        zeroRotationButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zeroRotationButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(3, 10, 3, 10);
        resetPanel.add(zeroRotationButton, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(joystickCheckBox, org.openide.util.NbBundle.getMessage(DisEspduSenderControlPanel.class, "DisEspduSenderControlPanel.joystickCheckBox.text")); // NOI18N
        joystickCheckBox.setToolTipText(org.openide.util.NbBundle.getMessage(DisEspduSenderControlPanel.class, "DisEspduSenderControlPanel.joystickCheckBox.toolTipText")); // NOI18N
        joystickCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                joystickCheckBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        resetPanel.add(joystickCheckBox, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        jPanel1.add(resetPanel, gridBagConstraints);

        translationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, NbBundle.getMessage(getClass(), "DisEspduSenderControlPanel.translationPanel.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, scaleLabel.getFont(), scaleLabel.getForeground())); // NOI18N
        translationPanel.setLayout(new java.awt.GridBagLayout());

        xSlider.setMajorTickSpacing(translationSliderMajorTickSpacing);
        xSlider.setMaximum(translationSliderMax);
        xSlider.setMinimum(translationSliderMin);
        xSlider.setMinorTickSpacing(translationSliderMinorTickSpacing);
        xSlider.setPaintLabels(true);
        xSlider.setToolTipText(org.openide.util.NbBundle.getMessage(DisEspduSenderControlPanel.class, "DisEspduSenderControlPanel.xSlider.toolTipText")); // NOI18N
        xSlider.setValue(0);
        xSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                xSliderChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        translationPanel.add(xSlider, gridBagConstraints);

        ySlider.setMajorTickSpacing(translationSliderMajorTickSpacing);
        ySlider.setMaximum(translationSliderMax);
        ySlider.setMinimum(translationSliderMin);
        ySlider.setMinorTickSpacing(translationSliderMinorTickSpacing);
        ySlider.setPaintLabels(true);
        ySlider.setToolTipText(org.openide.util.NbBundle.getMessage(DisEspduSenderControlPanel.class, "DisEspduSenderControlPanel.ySlider.toolTipText")); // NOI18N
        ySlider.setValue(0);
        ySlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ySliderChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        translationPanel.add(ySlider, gridBagConstraints);

        zSlider.setMajorTickSpacing(translationSliderMajorTickSpacing);
        zSlider.setMaximum(translationSliderMax);
        zSlider.setMinimum(translationSliderMin);
        zSlider.setPaintLabels(true);
        zSlider.setSnapToTicks(true);
        zSlider.setToolTipText(org.openide.util.NbBundle.getMessage(DisEspduSenderControlPanel.class, "DisEspduSenderControlPanel.zSlider.toolTipText")); // NOI18N
        zSlider.setValue(0);
        zSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                zSliderChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        translationPanel.add(zSlider, gridBagConstraints);

        scaleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(scaleLabel, NbBundle.getMessage(getClass(), "DisEspduSenderControlPanel.scaleLabel.text")); // NOI18N
        scaleLabel.setToolTipText("scale-factor multiplier"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        translationPanel.add(scaleLabel, gridBagConstraints);

        xScaleTF.setText(X3dOptions.getDIStranslationScaleX(DEFAULT_TRANSLATION_SCALE));
        xScaleTF.setToolTipText("scale-factor multiplier"); // NOI18N
        xScaleTF.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        xScaleTF.setMinimumSize(new java.awt.Dimension(32, 20));
        xScaleTF.setPreferredSize(new java.awt.Dimension(32, 20));
        xScaleTF.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                xScaleTFCaretUpdate(evt);
            }
        });
        xScaleTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xScaleTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        translationPanel.add(xScaleTF, gridBagConstraints);

        yScaleTF.setText(X3dOptions.getDIStranslationScaleY(DEFAULT_TRANSLATION_SCALE));
        yScaleTF.setToolTipText(org.openide.util.NbBundle.getMessage(DisEspduSenderControlPanel.class, "DisEspduSenderControlPanel.yScaleTF.toolTipText")); // NOI18N
        yScaleTF.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        yScaleTF.setMinimumSize(new java.awt.Dimension(32, 20));
        yScaleTF.setPreferredSize(new java.awt.Dimension(32, 20));
        yScaleTF.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                yScaleTFCaretUpdate(evt);
            }
        });
        yScaleTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yScaleTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        translationPanel.add(yScaleTF, gridBagConstraints);

        zScaleTF.setText(X3dOptions.getDIStranslationScaleZ(DEFAULT_TRANSLATION_SCALE));
        zScaleTF.setToolTipText(org.openide.util.NbBundle.getMessage(DisEspduSenderControlPanel.class, "DisEspduSenderControlPanel.zScaleTF.toolTipText")); // NOI18N
        zScaleTF.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        zScaleTF.setMinimumSize(new java.awt.Dimension(32, 20));
        zScaleTF.setPreferredSize(new java.awt.Dimension(32, 20));
        zScaleTF.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                zScaleTFCaretUpdate(evt);
            }
        });
        zScaleTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zScaleTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        translationPanel.add(zScaleTF, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        translationPanel.add(jSeparator4, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        translationPanel.add(jSeparator5, gridBagConstraints);

        yLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(yLabel, org.openide.util.NbBundle.getMessage(DisEspduSenderControlPanel.class, "DisEspduSenderControlPanel.yLabel.text")); // NOI18N
        yLabel.setToolTipText("y-axis translation"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 22, 5, 5);
        translationPanel.add(yLabel, gridBagConstraints);

        xLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(xLabel, org.openide.util.NbBundle.getMessage(DisEspduSenderControlPanel.class, "DisEspduSenderControlPanel.xLabel.text")); // NOI18N
        xLabel.setToolTipText("x-axis translation"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 22, 5, 5);
        translationPanel.add(xLabel, gridBagConstraints);

        zLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(zLabel, org.openide.util.NbBundle.getMessage(DisEspduSenderControlPanel.class, "DisEspduSenderControlPanel.zLabel.text")); // NOI18N
        zLabel.setToolTipText("z-axis translation");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 22, 5, 5);
        translationPanel.add(zLabel, gridBagConstraints);

        xTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        xTextField.setText(org.openide.util.NbBundle.getMessage(DisEspduSenderControlPanel.class, "DisEspduSenderControlPanel.xTextField.text")); // NOI18N
        xTextField.setToolTipText("x-axis translation");
        xTextField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        xTextField.setMinimumSize(new java.awt.Dimension(32, 20));
        xTextField.setPreferredSize(new java.awt.Dimension(32, 20));
        xTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                xTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        translationPanel.add(xTextField, gridBagConstraints);

        yTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        yTextField.setText(org.openide.util.NbBundle.getMessage(DisEspduSenderControlPanel.class, "DisEspduSenderControlPanel.yTextField.text")); // NOI18N
        yTextField.setToolTipText("y-axis translation");
        yTextField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        yTextField.setMinimumSize(new java.awt.Dimension(32, 20));
        yTextField.setPreferredSize(new java.awt.Dimension(32, 20));
        yTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        translationPanel.add(yTextField, gridBagConstraints);

        metersLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(metersLabel, org.openide.util.NbBundle.getMessage(DisEspduSenderControlPanel.class, "DisEspduSenderControlPanel.metersLabel.text")); // NOI18N
        metersLabel.setToolTipText("all DIS values are sent in meters"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        translationPanel.add(metersLabel, gridBagConstraints);

        zTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        zTextField.setText(org.openide.util.NbBundle.getMessage(DisEspduSenderControlPanel.class, "DisEspduSenderControlPanel.zTextField.text")); // NOI18N
        zTextField.setToolTipText("z-axis translation"); // NOI18N
        zTextField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        zTextField.setMinimumSize(new java.awt.Dimension(32, 20));
        zTextField.setPreferredSize(new java.awt.Dimension(32, 20));
        zTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                zTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        translationPanel.add(zTextField, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(translationPanel, gridBagConstraints);

        rotationPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, NbBundle.getMessage(getClass(), "DisEspduSenderControlPanel.rotationPanel.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, scaleLabel.getFont(), scaleLabel.getForeground())); // NOI18N
        rotationPanel.setLayout(new java.awt.GridBagLayout());

        yawLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(yawLabel, NbBundle.getMessage(getClass(), "DisEspduSenderControlPanel.yawLabel.text")); // NOI18N
        yawLabel.setToolTipText("yaw orientation (phi Euler angle)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 5, 5);
        rotationPanel.add(yawLabel, gridBagConstraints);

        yawSlider.setMajorTickSpacing(rotationSliderMajorTickSpacing);
        yawSlider.setMaximum(rotationSliderMax);
        yawSlider.setMinimum(rotationSliderMin);
        yawSlider.setMinorTickSpacing(rotationSliderMinorTickSpacing);
        yawSlider.setPaintLabels(true);
        yawSlider.setToolTipText("yaw orientation (phi Euler angle)");
        yawSlider.setValue(0);
        yawSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                yawSliderChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        rotationPanel.add(yawSlider, gridBagConstraints);

        pitchLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(pitchLabel, NbBundle.getMessage(getClass(), "DisEspduSenderControlPanel.pitchLabel.text")); // NOI18N
        pitchLabel.setToolTipText("pitch orientation (theta Euler angle)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 5, 5);
        rotationPanel.add(pitchLabel, gridBagConstraints);

        pitchSlider.setMajorTickSpacing(rotationSliderMajorTickSpacing);
        pitchSlider.setMaximum(pitchRotationSliderMax);
        pitchSlider.setMinimum(pitchRotationSliderMin);
        pitchSlider.setMinorTickSpacing(rotationSliderMinorTickSpacing);
        pitchSlider.setPaintLabels(true);
        pitchSlider.setToolTipText("pitch orientation (theta Euler angle)");
        pitchSlider.setValue(0);
        pitchSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                pitchSliderChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        rotationPanel.add(pitchSlider, gridBagConstraints);

        rollLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(rollLabel, NbBundle.getMessage(getClass(), "DisEspduSenderControlPanel.rollLabel.text")); // NOI18N
        rollLabel.setToolTipText(org.openide.util.NbBundle.getMessage(DisEspduSenderControlPanel.class, "DisEspduSenderControlPanel.rollLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 5, 5);
        rotationPanel.add(rollLabel, gridBagConstraints);

        rollSlider.setMajorTickSpacing(rotationSliderMajorTickSpacing);
        rollSlider.setMaximum(pitchRotationSliderMax);
        rollSlider.setMinimum(pitchRotationSliderMin);
        rollSlider.setMinorTickSpacing(rotationSliderMinorTickSpacing);
        rollSlider.setPaintLabels(true);
        rollSlider.setToolTipText("roll orientation (phi Euler angle)"); // NOI18N
        rollSlider.setValue(0);
        rollSlider.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                rollSliderChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        rotationPanel.add(rollSlider, gridBagConstraints);

        rollDegreesTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        rollDegreesTextField.setText("0");
        rollDegreesTextField.setToolTipText(org.openide.util.NbBundle.getMessage(DisEspduSenderControlPanel.class, "DisEspduSenderControlPanel.rollDegreesTextField.toolTipText")); // NOI18N
        rollDegreesTextField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        rollDegreesTextField.setMinimumSize(new java.awt.Dimension(32, 20));
        rollDegreesTextField.setPreferredSize(new java.awt.Dimension(32, 20));
        rollDegreesTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rollDegreesTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        rotationPanel.add(rollDegreesTextField, gridBagConstraints);

        pitchDegreesTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pitchDegreesTextField.setText("0");
        pitchDegreesTextField.setToolTipText("pitch orientation (theta Euler angle)");
        pitchDegreesTextField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pitchDegreesTextField.setMinimumSize(new java.awt.Dimension(32, 20));
        pitchDegreesTextField.setPreferredSize(new java.awt.Dimension(32, 20));
        pitchDegreesTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pitchDegreesTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        rotationPanel.add(pitchDegreesTextField, gridBagConstraints);

        yawDegreesTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        yawDegreesTextField.setText(org.openide.util.NbBundle.getMessage(DisEspduSenderControlPanel.class, "DisEspduSenderControlPanel.yawDegreesTextField.text")); // NOI18N
        yawDegreesTextField.setToolTipText("yaw orientation (phi Euler angle)");
        yawDegreesTextField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        yawDegreesTextField.setMinimumSize(new java.awt.Dimension(32, 20));
        yawDegreesTextField.setPreferredSize(new java.awt.Dimension(32, 20));
        yawDegreesTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yawDegreesTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.insets = new java.awt.Insets(0, 2, 5, 5);
        rotationPanel.add(yawDegreesTextField, gridBagConstraints);

        rollRadiansTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        rollRadiansTextField.setText(org.openide.util.NbBundle.getMessage(DisEspduSenderControlPanel.class, "DisEspduSenderControlPanel.rollRadiansTextField.text")); // NOI18N
        rollRadiansTextField.setToolTipText(org.openide.util.NbBundle.getMessage(DisEspduSenderControlPanel.class, "DisEspduSenderControlPanel.rollRadiansTextField.toolTipText")); // NOI18N
        rollRadiansTextField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        rollRadiansTextField.setMinimumSize(new java.awt.Dimension(32, 20));
        rollRadiansTextField.setPreferredSize(new java.awt.Dimension(32, 20));
        rollRadiansTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rollRadiansTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        rotationPanel.add(rollRadiansTextField, gridBagConstraints);

        pitchRadiansTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        pitchRadiansTextField.setText(org.openide.util.NbBundle.getMessage(DisEspduSenderControlPanel.class, "DisEspduSenderControlPanel.pitchRadiansTextField.text")); // NOI18N
        pitchRadiansTextField.setToolTipText("pitch orientation (theta Euler angle)"); // NOI18N
        pitchRadiansTextField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        pitchRadiansTextField.setMinimumSize(new java.awt.Dimension(32, 20));
        pitchRadiansTextField.setPreferredSize(new java.awt.Dimension(32, 20));
        pitchRadiansTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pitchRadiansTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        rotationPanel.add(pitchRadiansTextField, gridBagConstraints);

        yawRadiansTextField.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        yawRadiansTextField.setText(org.openide.util.NbBundle.getMessage(DisEspduSenderControlPanel.class, "DisEspduSenderControlPanel.yawRadiansTextField.text")); // NOI18N
        yawRadiansTextField.setToolTipText("yaw orientation (phi Euler angle)"); // NOI18N
        yawRadiansTextField.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        yawRadiansTextField.setMinimumSize(new java.awt.Dimension(32, 20));
        yawRadiansTextField.setPreferredSize(new java.awt.Dimension(32, 20));
        yawRadiansTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yawRadiansTextFieldActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 5);
        rotationPanel.add(yawRadiansTextField, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        rotationPanel.add(jSeparator1, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.REMAINDER;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 5, 0);
        rotationPanel.add(jSeparator2, gridBagConstraints);

        degreesLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(degreesLabel, org.openide.util.NbBundle.getMessage(DisEspduSenderControlPanel.class, "DisEspduSenderControlPanel.degreesLabel.text")); // NOI18N
        degreesLabel.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        rotationPanel.add(degreesLabel, gridBagConstraints);

        radiansLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        org.openide.awt.Mnemonics.setLocalizedText(radiansLabel, org.openide.util.NbBundle.getMessage(DisEspduSenderControlPanel.class, "DisEspduSenderControlPanel.radiansLabel.text")); // NOI18N
        radiansLabel.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(5, 0, 5, 5);
        rotationPanel.add(radiansLabel, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(rotationPanel, gridBagConstraints);

        DisSettingsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, NbBundle.getMessage(getClass(), "DisEspduSenderControlPanel.DisSettingsPanel.border.title"), javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, scaleLabel.getFont(), scaleLabel.getForeground())); // NOI18N
        DisSettingsPanel.setToolTipText(org.openide.util.NbBundle.getMessage(DisEspduSenderControlPanel.class, "DisEspduSenderControlPanel.DisSettingsPanel.toolTipText")); // NOI18N
        DisSettingsPanel.setLayout(new java.awt.GridBagLayout());

        markingLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(markingLabel, org.openide.util.NbBundle.getMessage(DisEspduSenderControlPanel.class, "DisEspduSenderControlPanel.markingLabel.text")); // NOI18N
        markingLabel.setToolTipText("11-character identifier name for this entity"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        DisSettingsPanel.add(markingLabel, gridBagConstraints);

        markingTF.setText(org.openide.util.NbBundle.getMessage(DisEspduSenderControlPanel.class, "DisEspduSenderControlPanel.markingTF.text")); // NOI18N
        markingTF.setToolTipText(org.openide.util.NbBundle.getMessage(DisEspduSenderControlPanel.class, "DisEspduSenderControlPanel.markingTF.toolTipText")); // NOI18N
        markingTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                markingTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        DisSettingsPanel.add(markingTF, gridBagConstraints);

        addressLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(addressLabel, NbBundle.getMessage(getClass(), "DisEspduSenderControlPanel.addressLabel.text")); // NOI18N
        addressLabel.setToolTipText(org.openide.util.NbBundle.getMessage(DisEspduSenderControlPanel.class, "DisEspduSenderControlPanel.addressLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        DisSettingsPanel.add(addressLabel, gridBagConstraints);

        addressTF.setText(X3dOptions.getDISaddress(DEFAULT_DIS_ADDRESS));
        addressTF.setToolTipText("multicast address 224.0.0.0 through 239.255.255.255 for this simulation"); // NOI18N
        addressTF.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                addressTFCaretUpdate(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        DisSettingsPanel.add(addressTF, gridBagConstraints);

        portLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(portLabel, NbBundle.getMessage(getClass(), "DisEspduSenderControlPanel.portLabel.text")); // NOI18N
        portLabel.setToolTipText(org.openide.util.NbBundle.getMessage(DisEspduSenderControlPanel.class, "DisEspduSenderControlPanel.portLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        DisSettingsPanel.add(portLabel, gridBagConstraints);

        portTF.setText(X3dOptions.getDISport(DEFAULT_PORT));
        portTF.setToolTipText("multicast port number between 1 and 65535 for this simulation"); // NOI18N
        portTF.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                portTFCaretUpdate(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        DisSettingsPanel.add(portTF, gridBagConstraints);

        siteIDLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(siteIDLabel, NbBundle.getMessage(getClass(), "DisEspduSenderControlPanel.siteIDLabel.text")); // NOI18N
        siteIDLabel.setToolTipText("simulation/exercise siteID of participating LAN or organization"); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        DisSettingsPanel.add(siteIDLabel, gridBagConstraints);

        siteIDTF.setText(X3dOptions.getDISsiteID(DEFAULT_SITEID));
        siteIDTF.setToolTipText("simulation/exercise siteID of participating LAN or organization");
        siteIDTF.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                siteIDTFCaretUpdate(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        DisSettingsPanel.add(siteIDTF, gridBagConstraints);

        appIDLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(appIDLabel, NbBundle.getMessage(getClass(), "DisEspduSenderControlPanel.appIDLabel.text")); // NOI18N
        appIDLabel.setToolTipText("simulation/exercise applicationID is unique for a given simulation");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        DisSettingsPanel.add(appIDLabel, gridBagConstraints);

        appIDTF.setText(X3dOptions.getDISappID(DEFAULT_APPID));
        appIDTF.setToolTipText("simulation/exercise applicationID is unique for a given simulation"); // NOI18N
        appIDTF.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                appIDTFCaretUpdate(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        DisSettingsPanel.add(appIDTF, gridBagConstraints);

        entityIDLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        org.openide.awt.Mnemonics.setLocalizedText(entityIDLabel, NbBundle.getMessage(getClass(), "DisEspduSenderControlPanel.entityIDLabel.text")); // NOI18N
        entityIDLabel.setToolTipText(org.openide.util.NbBundle.getMessage(DisEspduSenderControlPanel.class, "DisEspduSenderControlPanel.entityIDLabel.toolTipText")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        DisSettingsPanel.add(entityIDLabel, gridBagConstraints);

        entityIDTF.setText(X3dOptions.getDISentityID(DEFAULT_ENTITYID));
        entityIDTF.setToolTipText("simulation/exercise entityID is unique value for given entity within that application"); // NOI18N
        entityIDTF.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                entityIDTFCaretUpdate(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        DisSettingsPanel.add(entityIDTF, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(DisSettingsPanel, gridBagConstraints);

        org.openide.awt.Mnemonics.setLocalizedText(verticalSpaceLabel, org.openide.util.NbBundle.getMessage(DisEspduSenderControlPanel.class, "DisEspduSenderControlPanel.verticalSpaceLabel.text")); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        jPanel1.add(verticalSpaceLabel, gridBagConstraints);

        jScrollPane1.setViewportView(jPanel1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        add(jScrollPane1, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

private void xSliderChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_xSliderChanged
  if (isJoystickActive()) return; // do nothing
  double newX = xSlider.getValue() * getScale(xScaleTF);
  xTextField.setText(degreesFormat.format(newX));
  sendPositionUpdate ();
}//GEN-LAST:event_xSliderChanged

private void ySliderChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ySliderChanged
  if (isJoystickActive()) return; // do nothing
  double newY = ySlider.getValue() * getScale(yScaleTF);
  yTextField.setText(degreesFormat.format(newY));
  sendPositionUpdate ();
}//GEN-LAST:event_ySliderChanged

private void zSliderChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_zSliderChanged
  if (isJoystickActive()) return; // do nothing
  double newZ = zSlider.getValue() * getScale(zScaleTF);
  zTextField.setText(degreesFormat.format(newZ));
  sendPositionUpdate ();
}//GEN-LAST:event_zSliderChanged

private void pitchSliderChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_pitchSliderChanged
{//GEN-HEADEREND:event_pitchSliderChanged
  if (isJoystickActive()) return; // do nothing
  double pitchChanged = (double) (pitchSlider.getValue()) * Math.PI / 180.0;
  pitchRadiansTextField.setText(radiansFormat.format(pitchChanged));
  pitchDegreesTextField.setText(degreesFormat.format(pitchSlider.getValue()));
  sendOrientationUpdate ();
}//GEN-LAST:event_pitchSliderChanged

private void yawSliderChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_yawSliderChanged
{//GEN-HEADEREND:event_yawSliderChanged
  if (isJoystickActive()) return; // do nothing
  double yawChanged = (double) (yawSlider.getValue()) * Math.PI / 180.0;
  yawRadiansTextField.setText(radiansFormat.format(yawChanged));
  yawDegreesTextField.setText(degreesFormat.format(yawSlider.getValue()));
  sendOrientationUpdate ();
}//GEN-LAST:event_yawSliderChanged

private void addressTFCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_addressTFCaretUpdate
  networkParamsChanged = true;
}//GEN-LAST:event_addressTFCaretUpdate

private void portTFCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_portTFCaretUpdate
  networkParamsChanged = true;
}//GEN-LAST:event_portTFCaretUpdate

private void siteIDTFCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_siteIDTFCaretUpdate
  networkParamsChanged = true;
}//GEN-LAST:event_siteIDTFCaretUpdate

private void appIDTFCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_appIDTFCaretUpdate
  networkParamsChanged = true;
}//GEN-LAST:event_appIDTFCaretUpdate

private void entityIDTFCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_entityIDTFCaretUpdate
  networkParamsChanged = true;
}//GEN-LAST:event_entityIDTFCaretUpdate

private void xScaleTFCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_xScaleTFCaretUpdate
  networkParamsChanged = true;
}//GEN-LAST:event_xScaleTFCaretUpdate

private void yScaleTFCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_yScaleTFCaretUpdate
  networkParamsChanged = true;
}//GEN-LAST:event_yScaleTFCaretUpdate

private void zScaleTFCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_zScaleTFCaretUpdate
  networkParamsChanged = true;
}//GEN-LAST:event_zScaleTFCaretUpdate

private void markingTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_markingTFActionPerformed
{//GEN-HEADEREND:event_markingTFActionPerformed
    if (markingTF.getText().length() > 11)
    {
        String truncatedText = markingTF.getText().substring(0, 11);
        markingTF.setText(truncatedText);
    }
    testMarking.setCharacters(markingTF.getText().getBytes());
    espdu.setMarking(testMarking);
}//GEN-LAST:event_markingTFActionPerformed

private void yawRadiansTextFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_yawRadiansTextFieldActionPerformed
{//GEN-HEADEREND:event_yawRadiansTextFieldActionPerformed
    if (isJoystickActive()) return; // do nothing
    double yawValueDegrees = normalizeDegrees((new SFDouble(yawRadiansTextField.getText())).getValue() * 180.0 / Math.PI);
    yawSlider.setValue((int) yawValueDegrees);
    yawDegreesTextField.setText(degreesFormat.format(yawValueDegrees));
    sendOrientationUpdate ();
}//GEN-LAST:event_yawRadiansTextFieldActionPerformed

private void yawDegreesTextFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_yawDegreesTextFieldActionPerformed
{//GEN-HEADEREND:event_yawDegreesTextFieldActionPerformed
    if (isJoystickActive()) return; // do nothing
    double yawValueDegrees = normalizeDegrees((new SFDouble(yawDegreesTextField.getText())).getValue());
    yawSlider.setValue((int) (yawValueDegrees));
    yawRadiansTextField.setText(radiansFormat.format(yawValueDegrees * Math.PI / 180.0));
    sendOrientationUpdate ();
}//GEN-LAST:event_yawDegreesTextFieldActionPerformed

private void rollRadiansTextFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_rollRadiansTextFieldActionPerformed
{//GEN-HEADEREND:event_rollRadiansTextFieldActionPerformed
    if (isJoystickActive()) return; // do nothing
    double rollValueDegrees = normalizeDegrees180((new SFDouble(rollRadiansTextField.getText())).getValue() * 180.0 / Math.PI);
    rollSlider.setValue((int) rollValueDegrees);
    rollDegreesTextField.setText(degreesFormat.format(rollValueDegrees));
    sendOrientationUpdate ();
}//GEN-LAST:event_rollRadiansTextFieldActionPerformed

private void pitchRadiansTextFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_pitchRadiansTextFieldActionPerformed
{//GEN-HEADEREND:event_pitchRadiansTextFieldActionPerformed
    if (isJoystickActive()) return; // do nothing
    double pitchValueDegrees = normalizeDegrees180((new SFDouble(pitchRadiansTextField.getText())).getValue() * 180.0 / Math.PI);
    pitchSlider.setValue((int) pitchValueDegrees);
    pitchDegreesTextField.setText(degreesFormat.format(pitchValueDegrees));
    sendOrientationUpdate ();
}//GEN-LAST:event_pitchRadiansTextFieldActionPerformed

private void pitchDegreesTextFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_pitchDegreesTextFieldActionPerformed
{//GEN-HEADEREND:event_pitchDegreesTextFieldActionPerformed
    if (isJoystickActive()) return; // do nothing
    double pitchValueDegrees = normalizeDegrees180((new SFDouble(pitchDegreesTextField.getText())).getValue());
    pitchSlider.setValue((int) (pitchValueDegrees));
    pitchRadiansTextField.setText(radiansFormat.format(pitchValueDegrees * Math.PI / 180.0));
    sendOrientationUpdate ();
}//GEN-LAST:event_pitchDegreesTextFieldActionPerformed

private void xTextFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_xTextFieldActionPerformed
{//GEN-HEADEREND:event_xTextFieldActionPerformed
    if (isJoystickActive()) return; // do nothing
    double xValue = (new SFDouble(xTextField.getText())).getValue() / getScale(xScaleTF);
    xSlider.setValue((int) xValue);
    sendPositionUpdate ();
}//GEN-LAST:event_xTextFieldActionPerformed

private void yTextFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_yTextFieldActionPerformed
{//GEN-HEADEREND:event_yTextFieldActionPerformed
    if (isJoystickActive()) return; // do nothing
    double yValue = (new SFDouble(yTextField.getText())).getValue() / getScale(yScaleTF);
    ySlider.setValue((int) yValue);
    sendPositionUpdate ();
}//GEN-LAST:event_yTextFieldActionPerformed

private void xScaleTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_xScaleTFActionPerformed
{//GEN-HEADEREND:event_xScaleTFActionPerformed
  double newX = xSlider.getValue() * getScale(xScaleTF);
  xTextField.setText(degreesFormat.format(newX));
}//GEN-LAST:event_xScaleTFActionPerformed

private void yScaleTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_yScaleTFActionPerformed
{//GEN-HEADEREND:event_yScaleTFActionPerformed
  double newY = ySlider.getValue() * getScale(yScaleTF);
  yTextField.setText(degreesFormat.format(newY));
}//GEN-LAST:event_yScaleTFActionPerformed

private void zScaleTFActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_zScaleTFActionPerformed
{//GEN-HEADEREND:event_zScaleTFActionPerformed
  double newZ = zSlider.getValue() * getScale(zScaleTF);
  zTextField.setText(degreesFormat.format(newZ));
}//GEN-LAST:event_zScaleTFActionPerformed

private void rollDegreesTextFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_rollDegreesTextFieldActionPerformed
{//GEN-HEADEREND:event_rollDegreesTextFieldActionPerformed
    if (isJoystickActive()) return; // do nothing
    double rollValueDegrees = normalizeDegrees180((new SFDouble(rollDegreesTextField.getText())).getValue());
    rollSlider.setValue((int) (rollValueDegrees));
    rollRadiansTextField.setText(radiansFormat.format(rollValueDegrees * Math.PI / 180.0));
    sendOrientationUpdate ();
}//GEN-LAST:event_rollDegreesTextFieldActionPerformed

private void zTextFieldActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_zTextFieldActionPerformed
{//GEN-HEADEREND:event_zTextFieldActionPerformed
    if (isJoystickActive()) return; // do nothing
    double zValue = (new SFDouble(zTextField.getText())).getValue() / getScale(zScaleTF);
    zSlider.setValue((int) zValue);
    sendPositionUpdate ();
}//GEN-LAST:event_zTextFieldActionPerformed

private void rollSliderChanged(javax.swing.event.ChangeEvent evt)//GEN-FIRST:event_rollSliderChanged
{//GEN-HEADEREND:event_rollSliderChanged
  if (isJoystickActive()) return; // do nothing

  double rollChanged = (double) (rollSlider.getValue()) * Math.PI / 180.0;
  rollRadiansTextField.setText(radiansFormat.format(rollChanged));
  rollDegreesTextField.setText(degreesFormat.format(rollSlider.getValue()));
  sendOrientationUpdate ();
}//GEN-LAST:event_rollSliderChanged

private void zeroRotationButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_zeroRotationButtonActionPerformed
{//GEN-HEADEREND:event_zeroRotationButtonActionPerformed
    yawSlider.setValue(0);
    yawRadiansTextField.setText("0.0");
    yawDegreesTextField.setText("0");
    pitchSlider.setValue(0);
    pitchRadiansTextField.setText("0.0");
    pitchDegreesTextField.setText("0");
    rollSlider.setValue(0);
    rollRadiansTextField.setText("0.0");
    rollDegreesTextField.setText("0");
    sendOrientationUpdate ();
}//GEN-LAST:event_zeroRotationButtonActionPerformed

private void zeroPositionButtonActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_zeroPositionButtonActionPerformed
{//GEN-HEADEREND:event_zeroPositionButtonActionPerformed
    xSlider.setValue(0);
    xTextField.setText("0");
    xScaleTF.setText("1.0");
    ySlider.setValue(0);
    yTextField.setText("0");
    yScaleTF.setText("1.0");
    zSlider.setValue(0);
    zTextField.setText("0");
    zScaleTF.setText("1.0");
    sendPositionUpdate ();
}//GEN-LAST:event_zeroPositionButtonActionPerformed

private void joystickCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_joystickCheckBoxActionPerformed
    // TODO check whether joystick is available for use?

    if  (joystickCheckBox.isSelected())
    {
         joystickCheckBox.setToolTipText("Use joystick as input, show values on sliders");
    }
    else joystickCheckBox.setToolTipText("Ignore joystick, use values on sliders");

}//GEN-LAST:event_joystickCheckBoxActionPerformed

  private void savePrefs()
  {
    X3dOptions.setDISaddress(addressTF.getText().trim());
    X3dOptions.setDISport(portTF.getText().trim());
    X3dOptions.setDISappID(appIDTF.getText().trim());
    X3dOptions.setDISentityID(entityIDTF.getText().trim());
    X3dOptions.setDISsiteID(siteIDTF.getText().trim());
    X3dOptions.setDIStranslationScaleX(xScaleTF.getText().trim());
    X3dOptions.setDIStranslationScaleY(yScaleTF.getText().trim());
    X3dOptions.setDIStranslationScaleZ(zScaleTF.getText().trim());
  }

  private void sendPositionUpdate ()
  {
        Vector3Double location = espdu.getEntityLocation();

        double xValue = (new SFDouble(xTextField.getText())).getValue() / getScale(xScaleTF);
        double yValue = (new SFDouble(yTextField.getText())).getValue() / getScale(yScaleTF);
        double zValue = (new SFDouble(zTextField.getText())).getValue() / getScale(zScaleTF);

        // Coordinate system shift from X3D to DIS
        location.setX(xValue);          // X -> X
        location.setY(zValue);          // Z -> Y
        location.setZ(yValue * -1.0d);  // Y -> -Z

        this.sendPdu();
  }

  private void sendOrientationUpdate ()
  {
        EulerAngles orientation = espdu.getEntityOrientation();

        double rollChanged  = (double) ( rollSlider.getValue()) * Math.PI / 180.0;
        double pitchChanged = (double) (pitchSlider.getValue()) * Math.PI / 180.0;
        double yawChanged   = (double) (  yawSlider.getValue()) * Math.PI / 180.0;

        // Coordinate system shift from X3D to DIS
        orientation.setPhi  ((float)  rollChanged);
        orientation.setTheta((float) pitchChanged);
        orientation.setPsi  ((float)  -yawChanged);

        this.sendPdu();
  }

  private boolean networkParamsChanged = false;

  /**
   * Sends a PDU out over the multicast socket.
   * TODO dead-reckoning filter capability, instead of spewing all values.
   */
  public void sendPdu()
  {
    if(networkParamsChanged) {
      initNetworking();
      savePrefs();
      networkParamsChanged = false;
    }

    timestamp++;
    espdu.setTimestamp(timestamp); // TODO: fix the timestamp estimation to match elapsed time. NPS Time?
    distni.send(espdu);
  }

  /**
   * Normalize value [0..360)
   * @param value input value degrees
   * @return normalized output value degrees
   */
  private double normalizeDegrees (double value)
  {
      double newValue = value;
      while (newValue >= 360.0) newValue -= 360.0;
      while (newValue <    0.0) newValue += 360.0;
      return newValue;
  }

  /**
   * Normalize value [0..2Pi)
   * @param value input value radians
   * @return normalized output value radians
   */
  private double normalizeRadians(double value)
  {
      double TWO_PI = 2.0 * Math.PI;
      double newValue = value;
      while (newValue >= TWO_PI) newValue -= TWO_PI;
      while (newValue <     0.0) newValue += TWO_PI;
      return newValue;
  }

  /**
   * Normalize value [-180..180)
   * @param value input value degrees
   * @return normalized output value degrees
   */
  private double normalizeDegrees180 (double value)
  {
      double newValue = value;
      while (newValue >=  180.0) newValue -= 360.0;
      while (newValue <  -180.0) newValue += 360.0;
      return newValue;
  }

  /**
   * Normalize value [-Pi..Pi)
   * @param value input value radians
   * @return normalized output value radians
   */
  private double normalizeRadiansPI(double value)
  {
      double newValue = value;
      while (newValue >=  Math.PI) newValue -= Math.PI;
      while (newValue <  -Math.PI) newValue += Math.PI;
      return newValue;
  }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel DisSettingsPanel;
    private javax.swing.JLabel addressLabel;
    private javax.swing.JTextField addressTF;
    private javax.swing.JLabel appIDLabel;
    private javax.swing.JTextField appIDTF;
    private javax.swing.JLabel degreesLabel;
    private javax.swing.JCheckBox enabledCheckBox;
    private javax.swing.JLabel entityIDLabel;
    private javax.swing.JTextField entityIDTF;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JCheckBox joystickCheckBox;
    private javax.swing.JLabel markingLabel;
    private javax.swing.JTextField markingTF;
    private javax.swing.JLabel metersLabel;
    private javax.swing.JTextField pitchDegreesTextField;
    private javax.swing.JLabel pitchLabel;
    private javax.swing.JTextField pitchRadiansTextField;
    private javax.swing.JSlider pitchSlider;
    private javax.swing.JLabel portLabel;
    private javax.swing.JTextField portTF;
    private javax.swing.JLabel radiansLabel;
    private javax.swing.JPanel resetPanel;
    private javax.swing.JTextField rollDegreesTextField;
    private javax.swing.JLabel rollLabel;
    private javax.swing.JTextField rollRadiansTextField;
    private javax.swing.JSlider rollSlider;
    private javax.swing.JPanel rotationPanel;
    private javax.swing.JLabel scaleLabel;
    private javax.swing.JLabel siteIDLabel;
    private javax.swing.JTextField siteIDTF;
    private javax.swing.JPanel translationPanel;
    private javax.swing.JLabel verticalSpaceLabel;
    private javax.swing.JLabel xLabel;
    private javax.swing.JTextField xScaleTF;
    private javax.swing.JSlider xSlider;
    private javax.swing.JTextField xTextField;
    private javax.swing.JLabel yLabel;
    private javax.swing.JTextField yScaleTF;
    private javax.swing.JSlider ySlider;
    private javax.swing.JTextField yTextField;
    private javax.swing.JTextField yawDegreesTextField;
    private javax.swing.JLabel yawLabel;
    private javax.swing.JTextField yawRadiansTextField;
    private javax.swing.JSlider yawSlider;
    private javax.swing.JLabel zLabel;
    private javax.swing.JTextField zScaleTF;
    private javax.swing.JSlider zSlider;
    private javax.swing.JTextField zTextField;
    private javax.swing.JButton zeroPositionButton;
    private javax.swing.JButton zeroRotationButton;
    // End of variables declaration//GEN-END:variables
  /**
   * Gets default instance. Do not use directly: reserved for *.settings files only,
   * i.e. deserialization routines; otherwise you could get a non-deserialized instance.
   * To obtain the singleton instance, use {@link findInstance}.
   */
  public static synchronized DisEspduSenderControlPanel getDefault()
  {
    if (instance == null) {
      instance = new DisEspduSenderControlPanel();
    }
    return instance;
  }

  /**
   * Obtain the DisEspduSenderControlPanel instance. Never call {@link #getDefault} directly!
   */
  public static synchronized DisEspduSenderControlPanel findInstance()
  {
    TopComponent win = WindowManager.getDefault().findTopComponent(PREFERRED_ID);
    if (win == null) {
      Logger.getLogger(DisEspduSenderControlPanel.class.getName()).warning(
          "Cannot find " + PREFERRED_ID + " component. It will not be located properly in the window system.");
      return getDefault();
    }
    if (win instanceof DisEspduSenderControlPanel) {
      return (DisEspduSenderControlPanel) win;
    }
    Logger.getLogger(DisEspduSenderControlPanel.class.getName()).warning(
        "There seem to be multiple components with the '" + PREFERRED_ID +
        "' ID. That is a potential source of errors and unexpected behavior.");
    return getDefault();
  }

  @Override
  public int getPersistenceType()
  {
    return TopComponent.PERSISTENCE_ALWAYS;
  }

  @Override
  public void componentOpened()
  {
    // TODO add custom code on component opening
    }

  @Override
  public void componentClosed()
  {
    System.out.println("Component Closed");
    }

  /** replaces this in object stream */
  @Override
  public Object writeReplace()
  {
    return new ResolvableHelper();
  }

  @Override
  protected String preferredID()
  {
    return PREFERRED_ID;
  }

    /**
     * @return whether joystickInstalled is true
     */
    public boolean isJoystickInstalled() {
        return joystickInstalled;
    }

    /**
     * @param joystickInstalled whether the joystick is installed or not
     */
    public void setJoystickInstalled(boolean joystickInstalled) {
        this.joystickInstalled = joystickInstalled;

        if (joystickInstalled == false)
            joystickActive = false;
    }

    /**
     * @return whether joystick is active and providing values
     */
    public boolean isJoystickActive() {
        if  (joystickInstalled == false)
             return false;
        else return joystickActive;
    }

    /**
     * @param whether the joystick is installed and able to provide inputs; may be active or inactive
     */
    public void setJoystickActive(boolean joystickActive) {
        if  (joystickInstalled == false)
             this.joystickActive = false;
        else this.joystickActive = joystickActive;
    }

  final static class ResolvableHelper implements Serializable
  {
    private static final long serialVersionUID = 1L;

    public Object readResolve()
    {
      return DisEspduSenderControlPanel.getDefault();
    }
  }
}
