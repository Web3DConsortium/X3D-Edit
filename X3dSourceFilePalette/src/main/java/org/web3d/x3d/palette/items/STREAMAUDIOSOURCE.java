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
import org.web3d.x3d.types.X3DPrimitiveTypes.SFDouble;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFFloat;
import static org.web3d.x3d.types.X3DSchemaData.*;
import org.web3d.x3d.types.X3DSoundSourceNode;

/**
 * STREAMAUDIOSOURCE:
 * StreamAudioSource operates as an audio source whose media is received from a 
 * MediaStream obtained using the WebRTC or Media Capture and Streams APIs. 
 * This media source might originate from a remote microphone or sound-processing channel 
 * provided by a remote peer on a WebRTC call.
 * 
 * @author Don Brutzman
 * @version $Id$
 */
public class STREAMAUDIOSOURCE extends X3DSoundSourceNode // includes X3DTimeDependentNode
{
  private String   channelCountMode, channelCountModeDefault;
  private String   channelInterpretation, channelInterpretationDefault;   
  private String[] streamIdentifier, streamIdentifierDefault;
    
  public STREAMAUDIOSOURCE() 
  {
      this.setTraceEventsSelectionAvailable(false);
      this.setTraceEventsTooltip("Trace StreamAudioSource events on X3D browser console");
  }
  
  @Override
  public String getElementName()
  {
    return STREAMAUDIOSOURCE_ELNAME;
  }
  
  @Override
  public void initialize()
  {
    channelCountMode      = channelCountModeDefault      = STREAMAUDIOSOURCE_ATTR_CHANNELCOUNTMODE_DFLT;
    channelInterpretation = channelInterpretationDefault = STREAMAUDIOSOURCE_ATTR_CHANNELINTERPRETATION_DFLT;
    description = descriptionDefault     = STREAMAUDIOSOURCE_ATTR_DESCRIPTION_DFLT;
    enabled                              = Boolean.parseBoolean(STREAMAUDIOSOURCE_ATTR_ENABLED_DFLT);
    gain       = gainDefault             = new SFFloat (STREAMAUDIOSOURCE_ATTR_GAIN_DFLT,null,null); 
    pauseTime  = pauseTimeDefault        = new X3DPrimitiveTypes.SFDouble(STREAMAUDIOSOURCE_ATTR_PAUSETIME_DFLT,null,null); // SFTime
    resumeTime = resumeTimeDefault       = new X3DPrimitiveTypes.SFDouble(STREAMAUDIOSOURCE_ATTR_RESUMETIME_DFLT,null,null);
    startTime  = startTimeDefault        = new X3DPrimitiveTypes.SFDouble(STREAMAUDIOSOURCE_ATTR_STARTTIME_DFLT,null,null);
    stopTime   = stopTimeDefault         = new X3DPrimitiveTypes.SFDouble(STREAMAUDIOSOURCE_ATTR_STOPTIME_DFLT,null,null);
    
    if(STREAMAUDIOSOURCE_ATTR_URL_DFLT.length()>0)
      streamIdentifier = streamIdentifierDefault = parseMFStringIntoStringArray(STREAMAUDIOSOURCE_ATTR_STREAMIDENTIFIER_DFLT, true); //removeQuotes
    else
      streamIdentifier = streamIdentifierDefault = new String[0];
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(STREAMAUDIOSOURCE_ATTR_CHANNELCOUNTMODE_NAME);
    if (attr != null)
      channelCountMode = attr.getValue();
    attr = root.getAttribute(STREAMAUDIOSOURCE_ATTR_CHANNELINTERPRETATION_NAME);
    if (attr != null)
      channelInterpretation = attr.getValue();
    attr = root.getAttribute(STREAMAUDIOSOURCE_ATTR_DESCRIPTION_NAME);
    if (attr != null)
      description = attr.getValue();
    attr = root.getAttribute(STREAMAUDIOSOURCE_ATTR_ENABLED_NAME);
    if (attr != null)
      enabled = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(STREAMAUDIOSOURCE_ATTR_GAIN_NAME);
    if (attr != null)
      gain = new SFFloat(attr.getValue(), null, null);
    attr = root.getAttribute(STREAMAUDIOSOURCE_ATTR_STREAMIDENTIFIER_NAME);
    if (attr != null)
      streamIdentifier = parseMFStringIntoStringArray(attr.getValue(), true); //removeQuotes
    attr = root.getAttribute(STREAMAUDIOSOURCE_ATTR_PAUSETIME_NAME);
    if (attr != null)
      pauseTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(STREAMAUDIOSOURCE_ATTR_RESUMETIME_NAME);
    if (attr != null)
      resumeTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(STREAMAUDIOSOURCE_ATTR_STARTTIME_NAME);
    if (attr != null)
      startTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(STREAMAUDIOSOURCE_ATTR_STOPTIME_NAME);
    if (attr != null)
      stopTime = new SFDouble(attr.getValue(), null, null);
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return STREAMAUDIOSOURCECustomizer.class;
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    
    if (STREAMAUDIOSOURCE_ATTR_CHANNELCOUNTMODE_REQD || !channelCountMode.equals(channelCountModeDefault)) {
      sb.append(" ");
      sb.append(STREAMAUDIOSOURCE_ATTR_CHANNELCOUNTMODE_NAME);
      sb.append("='");
      sb.append(getChannelCountMode());
      sb.append("'");
    }
    
    if (STREAMAUDIOSOURCE_ATTR_CHANNELINTERPRETATION_REQD || !channelInterpretation.equals(channelInterpretationDefault)) {
      sb.append(" ");
      sb.append(STREAMAUDIOSOURCE_ATTR_CHANNELINTERPRETATION_NAME);
      sb.append("='");
      sb.append(getChannelInterpretation());
      sb.append("'");
    }
    
    if (STREAMAUDIOSOURCE_ATTR_DESCRIPTION_REQD || !description.equals(descriptionDefault)) {
      sb.append(" ");
      sb.append(STREAMAUDIOSOURCE_ATTR_DESCRIPTION_NAME);
      sb.append("='");
      sb.append(description);
      sb.append("'");
    }

    if (STREAMAUDIOSOURCE_ATTR_ENABLED_REQD || enabled != Boolean.parseBoolean(STREAMAUDIOSOURCE_ATTR_ENABLED_DFLT)) {
      sb.append(" ");
      sb.append(STREAMAUDIOSOURCE_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }
      
    if (STREAMAUDIOSOURCE_ATTR_GAIN_REQD || !gain.equals(gainDefault)) {
      sb.append(" ");
      sb.append(STREAMAUDIOSOURCE_ATTR_GAIN_NAME);
      sb.append("='");
      sb.append(getGain());
      sb.append("'");
    }
    if (STREAMAUDIOSOURCE_ATTR_PAUSETIME_REQD || !pauseTime.equals(pauseTimeDefault)) {
      sb.append(" ");
      sb.append(STREAMAUDIOSOURCE_ATTR_PAUSETIME_NAME);
      sb.append("='");
      sb.append(pauseTime);
      sb.append("'");
    }
    if (STREAMAUDIOSOURCE_ATTR_RESUMETIME_REQD || !resumeTime.equals(resumeTimeDefault)) {
      sb.append(" ");
      sb.append(STREAMAUDIOSOURCE_ATTR_RESUMETIME_NAME);
      sb.append("='");
      sb.append(resumeTime);
      sb.append("'");
    }
    if (STREAMAUDIOSOURCE_ATTR_STARTTIME_REQD || !startTime.equals(startTimeDefault)) {
      sb.append(" ");
      sb.append(STREAMAUDIOSOURCE_ATTR_STARTTIME_NAME);
      sb.append("='");
      sb.append(startTime);
      sb.append("'");
    }
    if (STREAMAUDIOSOURCE_ATTR_STOPTIME_REQD || !stopTime.equals(stopTimeDefault)) {
      sb.append(" ");
      sb.append(STREAMAUDIOSOURCE_ATTR_STOPTIME_NAME);
      sb.append("='");
      sb.append(stopTime);
      sb.append("'");
    }
    
    if (STREAMAUDIOSOURCE_ATTR_STREAMIDENTIFIER_REQD || (!Arrays.equals(streamIdentifier, streamIdentifierDefault) && (streamIdentifier.length > 0))) {
      sb.append(" ");
      sb.append(STREAMAUDIOSOURCE_ATTR_STREAMIDENTIFIER_NAME);
      sb.append("='");
      sb.append(formatStringArray(streamIdentifier));
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
     * @return the streamIdentifier
     */
    public String getStreamIdentifier() {
        if  (streamIdentifier.length == 0)
             return "";
        else return formatStringArray(streamIdentifier);
    }
    /**
     * @return the streamIdentifier
     */
    public String[] getStreamIdentifierArray() {
        String[] streamIdentifierArray = new String[streamIdentifier.length];
        System.arraycopy(streamIdentifier, 0, streamIdentifierArray, 0, streamIdentifier.length);
        return streamIdentifierArray;
    }

    /**
     * @param newStreamIdentifier the streamIdentifier to set
     */
    public void setStreamIdentifier(String newStreamIdentifier) {
        if(newStreamIdentifier.length()>0)
          streamIdentifier = parseMFStringIntoStringArray(newStreamIdentifier, true); //removeQuotes
        else
          streamIdentifier = new String[0];
    }

    /**
     * @param newStreamIdentifierArray the streamIdentifier to set
     */
    public void setStreamIdentifier(String[] newStreamIdentifierArray) {
        System.arraycopy(newStreamIdentifierArray, 0, streamIdentifier, 0, newStreamIdentifierArray.length);
    }

}
