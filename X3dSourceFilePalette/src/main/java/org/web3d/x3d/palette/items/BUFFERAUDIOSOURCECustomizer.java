/*
Copyright (c) 1995-2025 held by the author(s).  All rights reserved.
 
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

import javax.swing.DefaultComboBoxModel;
import javax.swing.text.JTextComponent;
import org.openide.util.HelpCtx;
import org.web3d.x3d.X3DDataObject;
import static org.web3d.x3d.types.X3DSchemaData.BUFFERAUDIOSOURCE_ATTR_CHANNELCOUNTMODE_CHOICES;
import static org.web3d.x3d.types.X3DSchemaData.BUFFERAUDIOSOURCE_ATTR_CHANNELINTERPRETATION_CHOICES;

/**
 * BUFFERAUDIOSOURCECustomizer:
 * BufferAudioSource node represents a memory-resident audio asset that can contain one or more channels. 
 * Typically the length of the Pulse-Code Modulation (PCM) data is expected to be fairly short 
 * (usually somewhat less than a minute). For longer sounds, such as music soundtracks, 
 * streaming such as StreamAudioSource should be used.
 * 
 * @author Don Brutzman
 * @version $Id$
 */
public class BUFFERAUDIOSOURCECustomizer extends BaseCustomizer
{ 
  private BUFFERAUDIOSOURCE bufferAudioSource;
  private JTextComponent target;
  private final X3DDataObject xObj;
  
  /** Creates new form BUFFERAUDIOSOURCECustomizer
     * @param bufferAudioSource data
     * @param target component of interest */
  public BUFFERAUDIOSOURCECustomizer(BUFFERAUDIOSOURCE bufferAudioSource, JTextComponent target, X3DDataObject xObj)
  {
    super(bufferAudioSource);
    this.bufferAudioSource = bufferAudioSource;
    this.target = target;
    this.xObj = xObj;
                           
    HelpCtx.setHelpIDString(BUFFERAUDIOSOURCECustomizer.this, "BUFFERAUDIOSOURCE_ELEM_HELPID");   
    
    initComponents();
    
         channelCountModeComboBox.setSelectedItem(bufferAudioSource.getChannelCountMode());
    channelInterpretationComboBox.setSelectedItem(bufferAudioSource.getChannelInterpretation());
    
    autoRefreshTF.setText          (bufferAudioSource.getAutoRefresh());
    autoRefreshTimeLimitTF.setText (bufferAudioSource.getAutoRefreshTimeLimit());
    bufferTF.setText               (bufferAudioSource.getBuffer());
    bufferDurationTF.setText       (bufferAudioSource.getBufferDuration());
    descriptionTF.setText          (bufferAudioSource.getDescription());
    detuneTF.setText               (bufferAudioSource.getDetune());
    enabledCB.setSelected          (bufferAudioSource.isEnabled());
    gainTF.setText                 (bufferAudioSource.getGain());
    loadCB.setSelected             (bufferAudioSource.isLoad());
    loopCB.setSelected             (bufferAudioSource.isLoop());
    loopStartTF.setText            (bufferAudioSource.getLoopStart());
    loopEndTF.setText              (bufferAudioSource.getLoopEnd());
    numberOfChannelsTF.setText     (bufferAudioSource.getNumberOfChannels());
    playbackRateTF.setText         (bufferAudioSource.getPlaybackRate());
    sampleRateTF.setText           (bufferAudioSource.getSampleRate());
    startTimeTF.setText            (bufferAudioSource.getStartTime());
    stopTimeTF.setText             (bufferAudioSource.getStopTime());
    pauseTimeTF.setText            (bufferAudioSource.getPauseTime());
    resumeTimeTF.setText           (bufferAudioSource.getResumeTime());
            
    urlList.setMasterDocumentLocation(xObj.getPrimaryFile());
    urlList.setUrlData(bufferAudioSource.getUrls());
    urlList.setTarget(target); // enable urlList to reach back into jdom tree to getHeaderIdentifierPath()
    urlList.setFileChooserSound();
    urlList.checkUrlValues();
    
  }
  private void setDefaultDEFname ()
  {
	if ((urlList == null) || (urlList.getUrlData() == null) || (urlList.getUrlData().length == 0))
	{
		super.getDEFUSEpanel().setDefaultDEFname("New" + "AudioClip");
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
        fileName += "AudioClip"; // otherwise empty
    
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

        jTextField2 = new javax.swing.JTextField();
        dEFUSEpanel1 = getDEFUSEpanel();
        enabledLabel = new javax.swing.JLabel();
        enabledCB = new javax.swing.JCheckBox();
        descriptionTF = new javax.swing.JTextField();
        descriptionLabel = new javax.swing.JLabel();
        loadCB = new javax.swing.JCheckBox();
        loadLabel = new javax.swing.JLabel();
        loopLabel = new javax.swing.JLabel();
        loopCB = new javax.swing.JCheckBox();
        autoRefreshLabel = new javax.swing.JLabel();
        autoRefreshTF = new javax.swing.JTextField();
        autoRefreshTimeLimitLabel = new javax.swing.JLabel();
        autoRefreshTimeLimitTF = new javax.swing.JTextField();
        bufferLabel = new javax.swing.JLabel();
        bufferTF = new javax.swing.JTextField();
        channelCountModeLabel = new javax.swing.JLabel();
        channelCountModeComboBox = new javax.swing.JComboBox<>();
        channelInterpretationLabel = new javax.swing.JLabel();
        channelInterpretationComboBox = new javax.swing.JComboBox<>();
        gainLabel = new javax.swing.JLabel();
        gainTF = new javax.swing.JTextField();
        numberOfChannelsLabel = new javax.swing.JLabel();
        numberOfChannelsTF = new javax.swing.JTextField();
        startTimeLabel = new javax.swing.JLabel();
        startTimeTF = new javax.swing.JTextField();
        pauseTimeLabel = new javax.swing.JLabel();
        pauseTimeTF = new javax.swing.JTextField();
        resumeTimeTF = new javax.swing.JTextField();
        resumeTimeLabel = new javax.swing.JLabel();
        stopTimeTF = new javax.swing.JTextField();
        stopTimeLabel = new javax.swing.JLabel();
        detuneLabel = new javax.swing.JLabel();
        detuneTF = new javax.swing.JTextField();
        bufferDurationLabel = new javax.swing.JLabel();
        bufferDurationTF = new javax.swing.JTextField();
        loopStartLabel = new javax.swing.JLabel();
        loopStartTF = new javax.swing.JTextField();
        loopEndLabel = new javax.swing.JLabel();
        loopEndTF = new javax.swing.JTextField();
        playbackRateLabel = new javax.swing.JLabel();
        playbackRateTF = new javax.swing.JTextField();
        sampleRateLabel = new javax.swing.JLabel();
        sampleRateTF = new javax.swing.JTextField();
        urlLabel = new javax.swing.JLabel();
        urlList = new org.web3d.x3d.palette.items.UrlExpandableList2();
        eventHelpPanel = new javax.swing.JPanel();
        eventsLabel1 = new javax.swing.JLabel();
        columnSpacerLabel = new javax.swing.JLabel();

        jTextField2.setText("jTextField2");

        setMinimumSize(new java.awt.Dimension(700, 720));
        setPreferredSize(new java.awt.Dimension(720, 740));
        setRequestFocusEnabled(false);
        setLayout(new java.awt.GridBagLayout());
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.FIRST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(dEFUSEpanel1, gridBagConstraints);

        enabledLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        enabledLabel.setText("enabled");
        enabledLabel.setToolTipText("enables/disables node operation");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(enabledLabel, gridBagConstraints);

        enabledCB.setToolTipText("enables/disables node operation");
        enabledCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.WEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(enabledCB, gridBagConstraints);

        descriptionTF.setToolTipText("Author-provided prose that describes intended purpose of the node");
        descriptionTF.setMinimumSize(new java.awt.Dimension(50, 20));
        descriptionTF.setPreferredSize(new java.awt.Dimension(50, 20));
        descriptionTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                descriptionTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(descriptionTF, gridBagConstraints);

        descriptionLabel.setText("description");
        descriptionLabel.setToolTipText("Author-provided prose that describes intended purpose of the node");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(descriptionLabel, gridBagConstraints);

        loadCB.setToolTipText("load=true means load immediately, load=false means defer loading or else unload a previously loaded asset.");
        loadCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(loadCB, gridBagConstraints);

        loadLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        loadLabel.setText("load");
        loadLabel.setToolTipText("load=true means load immediately, load=false means defer loading or else unload a previously loaded asset.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 48;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(loadLabel, gridBagConstraints);

        loopLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        loopLabel.setText("loop");
        loopLabel.setToolTipText("repeat indefinitely when loop=true, repeat only once when loop=false. ");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.ipadx = 48;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(loopLabel, gridBagConstraints);

        loopCB.setToolTipText("repeat indefinitely when loop=true, repeat only once when loop=false. ");
        loopCB.setMargin(new java.awt.Insets(0, 0, 0, 0));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(loopCB, gridBagConstraints);

        autoRefreshLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        autoRefreshLabel.setText("autoRefresh");
        autoRefreshLabel.setToolTipText("Multiplier for the rate at which sampled sound is played. Changing pitch also changes playback speed.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(autoRefreshLabel, gridBagConstraints);

        autoRefreshTF.setToolTipText("Multiplier for the rate at which sampled sound is played. Changing pitch also changes playback speed.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(autoRefreshTF, gridBagConstraints);

        autoRefreshTimeLimitLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        autoRefreshTimeLimitLabel.setText("autoRefreshTimeLimit");
        autoRefreshTimeLimitLabel.setToolTipText("Multiplier for the rate at which sampled sound is played. Changing pitch also changes playback speed.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(autoRefreshTimeLimitLabel, gridBagConstraints);

        autoRefreshTimeLimitTF.setToolTipText("Multiplier for the rate at which sampled sound is played. Changing pitch also changes playback speed.");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(autoRefreshTimeLimitTF, gridBagConstraints);

        bufferLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        bufferLabel.setText("buffer");
        bufferLabel.setToolTipText("memory-resident audio asset (for one-shot sounds and other short audio clips)");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bufferLabel, gridBagConstraints);

        bufferTF.setToolTipText("memory-resident audio asset (for one-shot sounds and other short audio clips)");
        bufferTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bufferTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bufferTF, gridBagConstraints);

        channelCountModeLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        channelCountModeLabel.setText("channelCountMode");
        channelCountModeLabel.setToolTipText("how individual channels are counted when up-mixing and down-mixing connections to any inputs");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(channelCountModeLabel, gridBagConstraints);

        channelCountModeComboBox.setModel(new DefaultComboBoxModel<String>(BUFFERAUDIOSOURCE_ATTR_CHANNELCOUNTMODE_CHOICES));
        channelCountModeComboBox.setToolTipText("how individual channels are counted when up-mixing and down-mixing connections to any inputs");
        channelCountModeComboBox.setMinimumSize(new java.awt.Dimension(100, 20));
        channelCountModeComboBox.setPreferredSize(new java.awt.Dimension(100, 20));
        channelCountModeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                channelCountModeComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(channelCountModeComboBox, gridBagConstraints);

        channelInterpretationLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        channelInterpretationLabel.setText("channelInterpretation");
        channelInterpretationLabel.setToolTipText("how individual channels are treated when up-mixing and down-mixing connections to any inputs");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(channelInterpretationLabel, gridBagConstraints);

        channelInterpretationComboBox.setModel(new DefaultComboBoxModel<String>(BUFFERAUDIOSOURCE_ATTR_CHANNELINTERPRETATION_CHOICES));
        channelInterpretationComboBox.setToolTipText("how individual channels are treated when up-mixing and down-mixing connections to any inputs");
        channelInterpretationComboBox.setMinimumSize(new java.awt.Dimension(100, 20));
        channelInterpretationComboBox.setPreferredSize(new java.awt.Dimension(100, 20));
        channelInterpretationComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                channelInterpretationComboBoxActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 9;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(channelInterpretationComboBox, gridBagConstraints);

        gainLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        gainLabel.setText("gain");
        gainLabel.setToolTipText("factor for linear amplification, can also negate input signal");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(gainLabel, gridBagConstraints);

        gainTF.setToolTipText("factor for linear amplification, can also negate input signal");
        gainTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                gainTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(gainTF, gridBagConstraints);

        numberOfChannelsLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        numberOfChannelsLabel.setText("numberOfChannels");
        numberOfChannelsLabel.setToolTipText("factor for linear amplification, can also negate input signal");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(numberOfChannelsLabel, gridBagConstraints);

        numberOfChannelsTF.setToolTipText("factor for linear amplification, can also negate input signal");
        numberOfChannelsTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                numberOfChannelsTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(numberOfChannelsTF, gridBagConstraints);

        startTimeLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        startTimeLabel.setText("startTime");
        startTimeLabel.setToolTipText("System time when node starts");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(startTimeLabel, gridBagConstraints);

        startTimeTF.setToolTipText("System time when node starts");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(startTimeTF, gridBagConstraints);

        pauseTimeLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        pauseTimeLabel.setText("pauseTime");
        pauseTimeLabel.setToolTipText("System time when node pauses");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(pauseTimeLabel, gridBagConstraints);

        pauseTimeTF.setToolTipText("System time when node pauses");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 14;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(pauseTimeTF, gridBagConstraints);

        resumeTimeTF.setToolTipText("System time when node resumes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(resumeTimeTF, gridBagConstraints);

        resumeTimeLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        resumeTimeLabel.setText("resumeTime");
        resumeTimeLabel.setToolTipText("System time when node resumes");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(resumeTimeLabel, gridBagConstraints);

        stopTimeTF.setToolTipText("System time when node stops");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(stopTimeTF, gridBagConstraints);

        stopTimeLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        stopTimeLabel.setText("stopTime");
        stopTimeLabel.setToolTipText("System time when node stops");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 15;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(stopTimeLabel, gridBagConstraints);

        detuneLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        detuneLabel.setText("detune");
        detuneLabel.setToolTipText("factor for linear amplification, can also negate input signal");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(detuneLabel, gridBagConstraints);

        detuneTF.setToolTipText("factor for linear amplification, can also negate input signal");
        detuneTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                detuneTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(detuneTF, gridBagConstraints);

        bufferDurationLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        bufferDurationLabel.setText("bufferDuration");
        bufferDurationLabel.setToolTipText("factor for linear amplification, can also negate input signal");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bufferDurationLabel, gridBagConstraints);

        bufferDurationTF.setToolTipText("factor for linear amplification, can also negate input signal");
        bufferDurationTF.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bufferDurationTFActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 8;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(bufferDurationTF, gridBagConstraints);

        loopStartLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        loopStartLabel.setText("loopStart");
        loopStartLabel.setToolTipText("System time when node starts");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(loopStartLabel, gridBagConstraints);

        loopStartTF.setToolTipText("System time when node starts");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(loopStartTF, gridBagConstraints);

        loopEndLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        loopEndLabel.setText("loopEnd");
        loopEndLabel.setToolTipText("System time when node pauses");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(loopEndLabel, gridBagConstraints);

        loopEndTF.setToolTipText("System time when node pauses");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 11;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(loopEndTF, gridBagConstraints);

        playbackRateLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        playbackRateLabel.setText("playbackRate");
        playbackRateLabel.setToolTipText("System time when node starts");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(playbackRateLabel, gridBagConstraints);

        playbackRateTF.setToolTipText("System time when node starts");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(playbackRateTF, gridBagConstraints);

        sampleRateLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        sampleRateLabel.setText("sampleRate");
        sampleRateLabel.setToolTipText("System time when node pauses");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(sampleRateLabel, gridBagConstraints);

        sampleRateTF.setToolTipText("System time when node pauses");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 25;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(sampleRateTF, gridBagConstraints);

        urlLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        urlLabel.setText("url");
        urlLabel.setToolTipText("Address of audio file to load into current scene");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(3, 6, 3, 3);
        add(urlLabel, gridBagConstraints);

        urlList.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        urlList.setToolTipText("Address of audio file to load into current scene");
        urlList.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                urlListPropertyChange(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 17;
        gridBagConstraints.gridwidth = 5;
        gridBagConstraints.gridheight = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipady = 50;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        add(urlList, gridBagConstraints);

        eventHelpPanel.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        eventHelpPanel.setLayout(new java.awt.GridBagLayout());

        eventsLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        eventsLabel1.setText("<html><p align=\"center\"> <b>BufferAudioSource</b> represents a memory-resident audio asset that can contain one or more channels. </p> <br /> \n<p align=\"center\">BufferAudioSource has <b>no child nodes</b> as input signals, and <br /> \nthe <b>parent node</b> receives the unmodified output signal from this node. </p>");
        eventsLabel1.setToolTipText("Optionally can create ROUTEs to connect input and output events");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.gridwidth = 4;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 6, 6, 6);
        eventHelpPanel.add(eventsLabel1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 20;
        gridBagConstraints.gridwidth = 6;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.LAST_LINE_START;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 0, 3);
        add(eventHelpPanel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 15;
        gridBagConstraints.ipadx = 20;
        gridBagConstraints.weightx = 1.0;
        add(columnSpacerLabel, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void descriptionTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_descriptionTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_descriptionTFActionPerformed

    private void channelCountModeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_channelCountModeComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_channelCountModeComboBoxActionPerformed

    private void channelInterpretationComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_channelInterpretationComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_channelInterpretationComboBoxActionPerformed

    private void gainTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_gainTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_gainTFActionPerformed

    private void detuneTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_detuneTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_detuneTFActionPerformed

    private void bufferTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bufferTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bufferTFActionPerformed

    private void bufferDurationTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bufferDurationTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bufferDurationTFActionPerformed

    private void numberOfChannelsTFActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_numberOfChannelsTFActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_numberOfChannelsTFActionPerformed

    private void urlListPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_urlListPropertyChange
        setDefaultDEFname ();
    }//GEN-LAST:event_urlListPropertyChange
  
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel autoRefreshLabel;
    private javax.swing.JTextField autoRefreshTF;
    private javax.swing.JLabel autoRefreshTimeLimitLabel;
    private javax.swing.JTextField autoRefreshTimeLimitTF;
    private javax.swing.JLabel bufferDurationLabel;
    private javax.swing.JTextField bufferDurationTF;
    private javax.swing.JLabel bufferLabel;
    private javax.swing.JTextField bufferTF;
    private javax.swing.JComboBox<String> channelCountModeComboBox;
    private javax.swing.JLabel channelCountModeLabel;
    private javax.swing.JComboBox<String> channelInterpretationComboBox;
    private javax.swing.JLabel channelInterpretationLabel;
    private javax.swing.JLabel columnSpacerLabel;
    private org.web3d.x3d.palette.items.DEFUSEpanel dEFUSEpanel1;
    private javax.swing.JLabel descriptionLabel;
    private javax.swing.JTextField descriptionTF;
    private javax.swing.JLabel detuneLabel;
    private javax.swing.JTextField detuneTF;
    private javax.swing.JCheckBox enabledCB;
    private javax.swing.JLabel enabledLabel;
    private javax.swing.JPanel eventHelpPanel;
    private javax.swing.JLabel eventsLabel1;
    private javax.swing.JLabel gainLabel;
    private javax.swing.JTextField gainTF;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JCheckBox loadCB;
    private javax.swing.JLabel loadLabel;
    private javax.swing.JCheckBox loopCB;
    private javax.swing.JLabel loopEndLabel;
    private javax.swing.JTextField loopEndTF;
    private javax.swing.JLabel loopLabel;
    private javax.swing.JLabel loopStartLabel;
    private javax.swing.JTextField loopStartTF;
    private javax.swing.JLabel numberOfChannelsLabel;
    private javax.swing.JTextField numberOfChannelsTF;
    private javax.swing.JLabel pauseTimeLabel;
    private javax.swing.JTextField pauseTimeTF;
    private javax.swing.JLabel playbackRateLabel;
    private javax.swing.JTextField playbackRateTF;
    private javax.swing.JLabel resumeTimeLabel;
    private javax.swing.JTextField resumeTimeTF;
    private javax.swing.JLabel sampleRateLabel;
    private javax.swing.JTextField sampleRateTF;
    private javax.swing.JLabel startTimeLabel;
    private javax.swing.JTextField startTimeTF;
    private javax.swing.JLabel stopTimeLabel;
    private javax.swing.JTextField stopTimeTF;
    private javax.swing.JLabel urlLabel;
    private org.web3d.x3d.palette.items.UrlExpandableList2 urlList;
    // End of variables declaration//GEN-END:variables
  
  
  @Override
  public String getNameKey()
  {
    return "NAME_X3D_BUFFERAUDIOSOURCE";
  }

  @Override
  public void unloadInput() throws IllegalArgumentException
  {
    unLoadDEFUSE();
    
    bufferAudioSource.setChannelCountMode     (channelCountModeComboBox.getSelectedItem().toString());
    bufferAudioSource.setChannelInterpretation(channelInterpretationComboBox.getSelectedItem().toString());
    
    bufferAudioSource.setAutoRefresh          (autoRefreshTF.getText().trim());
    bufferAudioSource.setAutoRefreshTimeLimit (autoRefreshTimeLimitTF.getText().trim());
    bufferAudioSource.setBuffer               (bufferTF.getText().trim());
    bufferAudioSource.setBufferDuration       (bufferDurationTF.getText().trim());
    bufferAudioSource.setDescription          (descriptionTF.getText().trim());
    bufferAudioSource.setDetune               (detuneTF.getText().trim());
    bufferAudioSource.setEnabled              (enabledCB.isSelected());
    bufferAudioSource.setGain                 (gainTF.getText().trim());
    bufferAudioSource.setLoad                 (loadCB.isSelected());
    bufferAudioSource.setLoop                 (loopCB.isSelected());
    bufferAudioSource.setLoopStart            (loopStartTF.getText().trim());
    bufferAudioSource.setLoopEnd              (loopEndTF.getText().trim());
    bufferAudioSource.setNumberOfChannels     (numberOfChannelsTF.getText().trim());
    bufferAudioSource.setPlaybackRate         (playbackRateTF.getText().trim());
    bufferAudioSource.setSampleRate           (sampleRateTF.getText().trim());
    bufferAudioSource.setStartTime            (startTimeTF.getText().trim());
    bufferAudioSource.setStopTime             (stopTimeTF.getText().trim());
    bufferAudioSource.setPauseTime            (pauseTimeTF.getText().trim());
    bufferAudioSource.setResumeTime           (resumeTimeTF.getText().trim());
  }
}
