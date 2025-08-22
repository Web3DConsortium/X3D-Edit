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

import java.util.Arrays;
import javax.swing.text.JTextComponent;
import org.web3d.x3d.types.X3DPrimitiveTypes;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFInt32;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFDouble;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFFloat;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.parseX;
import org.web3d.x3d.types.X3DSoundSourceNode;

/**
 * BUFFERAUDIOSOURCE:
 * BufferAudioSource node represents a memory-resident audio asset that can contain one or more channels. 
 * Typically the length of the Pulse-Code Modulation (PCM) data is expected to be fairly short 
 * (usually somewhat less than a minute). For longer sounds, such as music soundtracks, 
 * streaming such as StreamAudioSource should be used.
 * 
 * @author Don Brutzman
 * @version $Id$
 */
public class BUFFERAUDIOSOURCE extends X3DSoundSourceNode // includes X3DUrlObjec
{
    private SFDouble  autoRefresh, autoRefreshDefault; // SFTime
    private SFDouble  autoRefreshTimeLimit, autoRefreshTimeLimitDefault; // SFTime
    private SFFloat[] buffer, bufferDefault; // MFFloat
    private SFDouble  bufferDuration, bufferDurationDefault; // SFTime
    private String    channelCountMode, channelCountModeDefault;
    private String    channelInterpretation, channelInterpretationDefault;   
    private SFFloat   detune, detuneDefault;
    private boolean   load, loadDefault;
    private boolean   loop, loopDefault;
    private SFDouble  loopEnd, loopEndDefault;     // SFTime
    private SFDouble  loopStart, loopStartDefault; // SFTime
    private SFInt32   numberOfChannels, numberOfChannelsDefault;
    private SFFloat   playbackRate, playbackRateDefault;
    private SFFloat   sampleRate, sampleRateDefault;
    private String[]  urls, urlsDefault;
    
  public BUFFERAUDIOSOURCE() 
  {
      this.setTraceEventsSelectionAvailable(false);
      this.setTraceEventsTooltip("Trace BufferAudioSource events on X3D browser console");
  }
  
  @Override
  public String getElementName()
  {
    return BUFFERAUDIOSOURCE_ELNAME;
  }
  
  @Override
  public void initialize()
  {
    String[] sa;
    autoRefresh           = autoRefreshDefault           = new SFDouble (BUFFERAUDIOSOURCE_ATTR_AUTOREFRESH_DFLT,0.0,null); // SFTime
    autoRefreshTimeLimit  = autoRefreshTimeLimitDefault  = new SFDouble (BUFFERAUDIOSOURCE_ATTR_AUTOREFRESHTIMELIMIT_DFLT,0.0,null); // SFTime
    
    if(BUFFERAUDIOSOURCE_ATTR_BUFFER_DFLT == null || BUFFERAUDIOSOURCE_ATTR_BUFFER_DFLT.length()<=0)
      sa = new String[]{}; // empty array
    else
      sa = parseX(BUFFERAUDIOSOURCE_ATTR_BUFFER_DFLT);
    buffer                = bufferDefault                = parseToSFFloatArray(sa);
    bufferDuration        = bufferDurationDefault        = new SFDouble (BUFFERAUDIOSOURCE_ATTR_BUFFERDURATION_DFLT, 0.0, null); // SFTime
    channelCountMode      = channelCountModeDefault      = BUFFERAUDIOSOURCE_ATTR_CHANNELCOUNTMODE_DFLT;
    channelInterpretation = channelInterpretationDefault = BUFFERAUDIOSOURCE_ATTR_CHANNELINTERPRETATION_DFLT;
    description           = descriptionDefault           = BUFFERAUDIOSOURCE_ATTR_DESCRIPTION_DFLT;
    detune                = detuneDefault                = new SFFloat(BUFFERAUDIOSOURCE_ATTR_DETUNE_DFLT,0.0f,null);
    enabled                                              = Boolean.parseBoolean(BUFFERAUDIOSOURCE_ATTR_ENABLED_DFLT);
    gain                  = gainDefault                  = new SFFloat(BUFFERAUDIOSOURCE_ATTR_GAIN_DFLT,null,null);
    load                  = loadDefault                  = Boolean.parseBoolean(BUFFERAUDIOSOURCE_ATTR_LOAD_DFLT);
    loop                  = loopDefault                  = Boolean.parseBoolean(BUFFERAUDIOSOURCE_ATTR_LOOP_DFLT);
    loopEnd               = loopEndDefault               = new SFDouble (BUFFERAUDIOSOURCE_ATTR_LOOPEND_DFLT,0.0,null); // SFTime
    loopStart             = loopStartDefault             = new SFDouble (BUFFERAUDIOSOURCE_ATTR_LOOPSTART_DFLT,0.0,null); // SFTime
    numberOfChannels      = numberOfChannelsDefault      = new SFInt32 (BUFFERAUDIOSOURCE_ATTR_NUMBEROFCHANNELS_DFLT,0,null);
    playbackRate          = playbackRateDefault          = new SFFloat(BUFFERAUDIOSOURCE_ATTR_PLAYBACKRATE_DFLT,null,null);
    sampleRate            = sampleRateDefault            = new SFFloat(BUFFERAUDIOSOURCE_ATTR_SAMPLERATE_DFLT,0.0f,null);
    
    pauseTime             = pauseTimeDefault             = new X3DPrimitiveTypes.SFDouble(BUFFERAUDIOSOURCE_ATTR_PAUSETIME_DFLT,null,null); // SFTime
    resumeTime            = resumeTimeDefault            = new X3DPrimitiveTypes.SFDouble(BUFFERAUDIOSOURCE_ATTR_RESUMETIME_DFLT,null,null);
    startTime             = startTimeDefault             = new X3DPrimitiveTypes.SFDouble(BUFFERAUDIOSOURCE_ATTR_STARTTIME_DFLT,null,null);
    stopTime              = stopTimeDefault              = new X3DPrimitiveTypes.SFDouble(BUFFERAUDIOSOURCE_ATTR_STOPTIME_DFLT,null,null);
    
    if(BUFFERAUDIOSOURCE_ATTR_URL_DFLT.length()>0)
      urls = urlsDefault = parseMFStringIntoStringArray(BUFFERAUDIOSOURCE_ATTR_URL_DFLT, true); //removeQuotes
    else
      urls = urlsDefault = new String[0];
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;
    String[] sa;

    attr = root.getAttribute(BUFFERAUDIOSOURCE_ATTR_AUTOREFRESH_NAME);
    if (attr != null)
      autoRefresh = new SFDouble(attr.getValue(), 0.0, null);
    attr = root.getAttribute(BUFFERAUDIOSOURCE_ATTR_AUTOREFRESHTIMELIMIT_NAME);
    if (attr != null)
      autoRefreshTimeLimit = new SFDouble(attr.getValue(), 0.0, null);
    attr = root.getAttribute(BUFFERAUDIOSOURCE_ATTR_BUFFER_NAME);
    if (attr != null) {
      sa = parseX(attr.getValue());
      buffer = parseToSFFloatArray(sa);
    }
    attr = root.getAttribute(BUFFERAUDIOSOURCE_ATTR_BUFFERDURATION_NAME);
    if (attr != null)
      bufferDuration = new SFDouble(attr.getValue(), 0.0, null);
    
    attr = root.getAttribute(BUFFERAUDIOSOURCE_ATTR_CHANNELCOUNTMODE_NAME);
    if (attr != null)
      channelCountMode = attr.getValue();
    attr = root.getAttribute(BUFFERAUDIOSOURCE_ATTR_CHANNELINTERPRETATION_NAME);
    if (attr != null)
      channelInterpretation = attr.getValue();
    attr = root.getAttribute(BUFFERAUDIOSOURCE_ATTR_DESCRIPTION_NAME);
    if (attr != null)
      description = attr.getValue();
    attr = root.getAttribute(BUFFERAUDIOSOURCE_ATTR_DETUNE_NAME);
    if (attr != null)
      detune = new SFFloat(attr.getValue(), 0.0f, null);
    attr = root.getAttribute(BUFFERAUDIOSOURCE_ATTR_ENABLED_NAME);
    if (attr != null)
      enabled = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(BUFFERAUDIOSOURCE_ATTR_GAIN_NAME);
    if (attr != null)
      gain = new SFFloat(attr.getValue(), null, null);
    attr = root.getAttribute(BUFFERAUDIOSOURCE_ATTR_LOAD_NAME);
    if (attr != null)
      load = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(BUFFERAUDIOSOURCE_ATTR_LOOP_NAME);
    if (attr != null)
      loop = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(BUFFERAUDIOSOURCE_ATTR_LOOPEND_NAME);
    if (attr != null)
      loopEnd = new SFDouble(attr.getValue(), 0.0, null);
    attr = root.getAttribute(BUFFERAUDIOSOURCE_ATTR_LOOPSTART_NAME);
    if (attr != null)
      loopStart = new SFDouble(attr.getValue(), 0.0, null);
    attr = root.getAttribute(BUFFERAUDIOSOURCE_ATTR_NUMBEROFCHANNELS_NAME);
    if (attr != null)
      numberOfChannels = new SFInt32(attr.getValue(), 0, null);
    attr = root.getAttribute(BUFFERAUDIOSOURCE_ATTR_PLAYBACKRATE_NAME);
    if (attr != null)
      playbackRate = new SFFloat(attr.getValue(), null, null);
    attr = root.getAttribute(BUFFERAUDIOSOURCE_ATTR_SAMPLERATE_NAME);
    if (attr != null)
      sampleRate = new SFFloat(attr.getValue(), 0.0f, null);
    attr = root.getAttribute(BUFFERAUDIOSOURCE_ATTR_PAUSETIME_NAME);
    if (attr != null)
      pauseTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(BUFFERAUDIOSOURCE_ATTR_RESUMETIME_NAME);
    if (attr != null)
      resumeTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(BUFFERAUDIOSOURCE_ATTR_STARTTIME_NAME);
    if (attr != null)
      startTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(BUFFERAUDIOSOURCE_ATTR_STOPTIME_NAME);
    if (attr != null)
      stopTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(BUFFERAUDIOSOURCE_ATTR_URL_NAME);
    if (attr != null)
      urls =  parseUrlsIntoStringArray(attr.getValue());
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return BUFFERAUDIOSOURCECustomizer.class;
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    
    if (BUFFERAUDIOSOURCE_ATTR_AUTOREFRESH_REQD || !autoRefresh.equals(autoRefreshDefault)) {
      sb.append(" ");
      sb.append(BUFFERAUDIOSOURCE_ATTR_AUTOREFRESH_NAME);
      sb.append("='");
      sb.append(autoRefresh);
      sb.append("'");
    }

    if (BUFFERAUDIOSOURCE_ATTR_AUTOREFRESHTIMELIMIT_REQD || !autoRefreshTimeLimit.equals(autoRefreshTimeLimitDefault)) {
      sb.append(" ");
      sb.append(BUFFERAUDIOSOURCE_ATTR_AUTOREFRESHTIMELIMIT_NAME);
      sb.append("='");
      sb.append(autoRefreshTimeLimit);
      sb.append("'");
    }
    if ((BUFFERAUDIOSOURCE_ATTR_BUFFER_REQD || !arraysIdenticalOrNull(buffer,bufferDefault)) && buffer.length > 0) {
      sb.append(" ");
      sb.append(BUFFERAUDIOSOURCE_ATTR_BUFFER_NAME);
      sb.append("='");
      sb.append(formatFloatArray(buffer));
      sb.append("'");
    }
    if (BUFFERAUDIOSOURCE_ATTR_BUFFERDURATION_REQD || !bufferDuration.equals(bufferDurationDefault)) {
      sb.append(" ");
      sb.append(BUFFERAUDIOSOURCE_ATTR_BUFFERDURATION_NAME);
      sb.append("='");
      sb.append(bufferDuration);
      sb.append("'");
    }
    if (BUFFERAUDIOSOURCE_ATTR_CHANNELCOUNTMODE_REQD || !channelCountMode.equals(channelCountModeDefault)) {
      sb.append(" ");
      sb.append(BUFFERAUDIOSOURCE_ATTR_CHANNELCOUNTMODE_NAME);
      sb.append("='");
      sb.append(getChannelCountMode());
      sb.append("'");
    }
    
    if (BUFFERAUDIOSOURCE_ATTR_CHANNELINTERPRETATION_REQD || !channelInterpretation.equals(channelInterpretationDefault)) {
      sb.append(" ");
      sb.append(BUFFERAUDIOSOURCE_ATTR_CHANNELINTERPRETATION_NAME);
      sb.append("='");
      sb.append(getChannelInterpretation());
      sb.append("'");
    }
    
    if (BUFFERAUDIOSOURCE_ATTR_DESCRIPTION_REQD || !description.equals(descriptionDefault)) {
      sb.append(" ");
      sb.append(BUFFERAUDIOSOURCE_ATTR_DESCRIPTION_NAME);
      sb.append("='");
      sb.append(description);
      sb.append("'");
    }
    if (BUFFERAUDIOSOURCE_ATTR_DETUNE_REQD || !detune.equals(detuneDefault)) {
      sb.append(" ");
      sb.append(BUFFERAUDIOSOURCE_ATTR_DETUNE_NAME);
      sb.append("='");
      sb.append(detune);
      sb.append("'");
    }

    if (BUFFERAUDIOSOURCE_ATTR_ENABLED_REQD || enabled != Boolean.parseBoolean(BUFFERAUDIOSOURCE_ATTR_ENABLED_DFLT)) {
      sb.append(" ");
      sb.append(BUFFERAUDIOSOURCE_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }
    if (BUFFERAUDIOSOURCE_ATTR_GAIN_REQD || !gain.equals(gainDefault)) {
      sb.append(" ");
      sb.append(BUFFERAUDIOSOURCE_ATTR_GAIN_NAME);
      sb.append("='");
      sb.append(gain);
      sb.append("'");
    }
      
    if (BUFFERAUDIOSOURCE_ATTR_LOAD_REQD || load != loadDefault) {
      sb.append(" ");
      sb.append(BUFFERAUDIOSOURCE_ATTR_LOAD_NAME);
      sb.append("='");
      sb.append(load);
      sb.append("'");
    }
    if (BUFFERAUDIOSOURCE_ATTR_LOOP_REQD || loop != loopDefault) {
      sb.append(" ");
      sb.append(BUFFERAUDIOSOURCE_ATTR_LOOP_NAME);
      sb.append("='");
      sb.append(loop);
      sb.append("'");
    }
    if (BUFFERAUDIOSOURCE_ATTR_LOOPEND_REQD || !loopEnd.equals(loopEndDefault)) {
      sb.append(" ");
      sb.append(BUFFERAUDIOSOURCE_ATTR_LOOPEND_NAME);
      sb.append("='");
      sb.append(loopEnd);
      sb.append("'");
    }
    if (BUFFERAUDIOSOURCE_ATTR_LOOPSTART_REQD || !loopStart.equals(loopStartDefault)) {
      sb.append(" ");
      sb.append(BUFFERAUDIOSOURCE_ATTR_LOOPSTART_NAME);
      sb.append("='");
      sb.append(loopStart);
      sb.append("'");
    }
    if (BUFFERAUDIOSOURCE_ATTR_NUMBEROFCHANNELS_REQD || !numberOfChannels.equals(numberOfChannelsDefault)) {
      sb.append(" ");
      sb.append(BUFFERAUDIOSOURCE_ATTR_NUMBEROFCHANNELS_NAME);
      sb.append("='");
      sb.append(numberOfChannels);
      sb.append("'");
    }
    if (BUFFERAUDIOSOURCE_ATTR_PAUSETIME_REQD || !pauseTime.equals(pauseTimeDefault)) {
      sb.append(" ");
      sb.append(BUFFERAUDIOSOURCE_ATTR_PAUSETIME_NAME);
      sb.append("='");
      sb.append(pauseTime);
      sb.append("'");
    }
    if (BUFFERAUDIOSOURCE_ATTR_PLAYBACKRATE_REQD || !playbackRate.equals(playbackRateDefault)) {
      sb.append(" ");
      sb.append(BUFFERAUDIOSOURCE_ATTR_PLAYBACKRATE_NAME);
      sb.append("='");
      sb.append(playbackRate);
      sb.append("'");
    }
    if (BUFFERAUDIOSOURCE_ATTR_RESUMETIME_REQD || !resumeTime.equals(resumeTimeDefault)) {
      sb.append(" ");
      sb.append(BUFFERAUDIOSOURCE_ATTR_RESUMETIME_NAME);
      sb.append("='");
      sb.append(resumeTime);
      sb.append("'");
    }
    if (BUFFERAUDIOSOURCE_ATTR_SAMPLERATE_REQD || !sampleRate.equals(sampleRateDefault)) {
      sb.append(" ");
      sb.append(BUFFERAUDIOSOURCE_ATTR_SAMPLERATE_NAME);
      sb.append("='");
      sb.append(sampleRate);
      sb.append("'");
    }
    if (BUFFERAUDIOSOURCE_ATTR_STARTTIME_REQD || !startTime.equals(startTimeDefault)) {
      sb.append(" ");
      sb.append(BUFFERAUDIOSOURCE_ATTR_STARTTIME_NAME);
      sb.append("='");
      sb.append(startTime);
      sb.append("'");
    }
    if (BUFFERAUDIOSOURCE_ATTR_STOPTIME_REQD || !stopTime.equals(stopTimeDefault)) {
      sb.append(" ");
      sb.append(BUFFERAUDIOSOURCE_ATTR_STOPTIME_NAME);
      sb.append("='");
      sb.append(stopTime);
      sb.append("'");
    }
    if (BUFFERAUDIOSOURCE_ATTR_URL_REQD || (!Arrays.equals(urls, urlsDefault) && (urls.length > 0))) {
      sb.append(" ");
      sb.append(BUFFERAUDIOSOURCE_ATTR_URL_NAME);
      sb.append("='");
      sb.append(formatStringArray(urls));
      sb.append("'");
    }
    
    return sb.toString();
  }

    /**
     * @return the channelCountMode
     */
    public String getChannelCountMode() {
        return channelCountMode;
    }

    /**
     * @param newChannelCountMode the channelCountMode to set
     */
    public void setChannelCountMode(String newChannelCountMode) {
        this.channelCountMode = newChannelCountMode;
    }

    /**
     * @return the channelInterpretation
     */
    public String getChannelInterpretation() {
        return channelInterpretation;
    }

    /**
     * @param newChannelInterpretation the channelInterpretation to set
     */
    public void setChannelInterpretation(String newChannelInterpretation) {
        channelInterpretation = newChannelInterpretation;
    }

    /**
     * @return the load field
     */
    public boolean isLoad()
    {
      return load;
    }

    /**
     * @param newLoad the load field to set
     */
    public void setLoad(boolean newLoad)
    {
      load = newLoad;
    }

    /**
     * @return the loop field
     */
    public boolean isLoop()
    {
      return loop;
    }

    /**
     * @param newLoop the loop field to set
     */
    public void setLoop(boolean newLoop)
    {
      loop = newLoop;
    }

    /**
     * @param newAutoRefresh the autoRefresh to set
     */
    public void setAutoRefresh(String newAutoRefresh) {
        autoRefresh = new SFDouble(newAutoRefresh, 0.0, null);
    }
    /**
     * @return the autoRefresh
     */
    public String getAutoRefresh() {
        return autoRefresh.toString();
    }

    /**
     * @param newAutoRefreshTimeLimit the autoRefreshTimeLimit to set
     */
    public void setAutoRefreshTimeLimit(String newAutoRefreshTimeLimit) {
        autoRefreshTimeLimit = new SFDouble(newAutoRefreshTimeLimit, 0.0, null);
    }
    /**
     * @return the autoRefreshTimeLimit
     */
    public String getAutoRefreshTimeLimit() {
        return autoRefreshTimeLimit.toString();
    }

    /**
     * @return the buffer
     */
    public String getBuffer() {
        String values = new String();
        for (SFFloat nextValue : buffer) {
            values += " " + nextValue.toString();
        }
        return values.trim();
    }

    /**
     * @param newBuffer the buffer to set
     */
    public void setBuffer(String newBuffer) {
        String[] sa;
        if ((newBuffer != null) && !newBuffer.isBlank()) {
          sa = parseX(newBuffer);
        }
        else sa = new String[]{}; // empty array
        buffer = parseToSFFloatArray(sa);
    }

    /**
     * @param newBufferDuration the bufferDuration to set
     */
    public void setBufferDuration(String newBufferDuration) {
        bufferDuration = new SFDouble(newBufferDuration, 0.0, null);
    }
    /**
     * @return the bufferDuration
     */
    public String getBufferDuration() {
        return bufferDuration.toString();
    }
    
    /**
     * @param newDetune the detune to set
     */
    public void setDetune(String newDetune) {
        detune = new SFFloat(newDetune, 0.0f, null);
    }
    /**
     * @return the detune
     */
    public String getDetune() {
        return detune.toString();
    }
    /**
     * @param newPlaybackRate the playbackRate to set
     */
    public void setPlaybackRate(String newPlaybackRate) {
        playbackRate = new SFFloat(newPlaybackRate, null, null);
    }
    /**
     * @return the playbackRate
     */
    public String getPlaybackRate() {
        return playbackRate.toString();
    }
    /**
            * @param newSampleRate the sampleRate to set
     */
    public void setSampleRate(String newSampleRate) {
        sampleRate = new SFFloat(newSampleRate, 0.0f, null);
    }
    /**
     * @return the sampleRate
     */
    public String getSampleRate() {
        return sampleRate.toString();
    }
    /**
     * @param newLoopEnd the loopEnd to set
     */
    public void setLoopEnd(String newLoopEnd) {
        loopEnd = new SFDouble(newLoopEnd, 0.0, null);
    }
    /**
     * @return the loopEnd
     */
    public String getLoopEnd() {
        return loopEnd.toString();
    }
    /**
     * @param newLoopStart the loopStart to set
     */
    public void setLoopStart(String newLoopStart) {
        loopStart = new SFDouble(newLoopStart, 0.0, null);
    }
    /**
     * @return the loopStart
     */
    public String getLoopStart() {
        return loopEnd.toString();
    }
    /**
     * @param newNumberOfChannels the numberOfChannels to set
     */
    public void setNumberOfChannels(String newNumberOfChannels) {
        numberOfChannels = new SFInt32(newNumberOfChannels, 0, null);
    }
    /**
     * @return the numberOfChannels
     */
    public String getNumberOfChannels() {
        return loopEnd.toString();
    }

    public String[] getUrls()
    {
      String[] urlArray = new String[urls.length];
      System.arraycopy(urls, 0, urlArray, 0, urls.length);
      return urlArray;
    }

    public void setUrls(String[] urlArray)
    {
      urls = new String[urlArray.length];
      System.arraycopy(urlArray, 0, this.urls, 0, urlArray.length);
    }
  
}
