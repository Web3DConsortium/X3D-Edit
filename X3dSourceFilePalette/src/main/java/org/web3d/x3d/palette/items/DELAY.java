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

import javax.swing.text.JTextComponent;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFDouble;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFFloat;
import static org.web3d.x3d.types.X3DSchemaData.*;
import org.web3d.x3d.types.X3DSoundProcessingNode;

/**
 * DELAY:
 * Delay causes a time delay between the arrival of input data and subsequent propagation to the output.
 * 
 * @author Don Brutzman
 * @version $Id$
 */
public class DELAY extends X3DSoundProcessingNode // and X3DTimeDependentNode
{
    private SFDouble delayTime,    delayTimeDefault;
    private SFDouble maxDelayTime, maxDelayTimeDefault;
    
  public DELAY() 
  {
      this.setTraceEventsSelectionAvailable(false);
      this.setTraceEventsTooltip("Trace Delay events on X3D browser console");
  }
  
  @Override
  public String getElementName()
  {
    return DELAY_ELNAME;
  }
  
  @Override
  public void initialize()
  {
    delayTime  =  delayTimeDefault       = new SFDouble(DELAY_ATTR_DELAYTIME_DFLT,0.0,null);
    maxDelayTime =  maxDelayTimeDefault  = new SFDouble(DELAY_ATTR_MAXDELAYTIME_DFLT,0.0,null);
    
    channelCountMode      = channelCountModeDefault      = DELAY_ATTR_CHANNELCOUNTMODE_DFLT;
    channelInterpretation = channelInterpretationDefault = DELAY_ATTR_CHANNELINTERPRETATION_DFLT;
    description = descriptionDefault     = DELAY_ATTR_DESCRIPTION_DFLT;
    enabled                              = Boolean.parseBoolean(DELAY_ATTR_ENABLED_DFLT);
    gain       = gainDefault             = new SFFloat (DELAY_ATTR_GAIN_DFLT,null,null); 
    startTime  =  startTimeDefault       = new SFDouble(DELAY_ATTR_STARTTIME_DFLT,null,null);
    stopTime   =   stopTimeDefault       = new SFDouble(DELAY_ATTR_STOPTIME_DFLT,null,null);  
    pauseTime  = pauseTimeDefault        = new SFDouble(DELAY_ATTR_PAUSETIME_DFLT,null,null);
    resumeTime = resumeTimeDefault       = new SFDouble(DELAY_ATTR_RESUMETIME_DFLT,null,null);
    tailTime   = tailTimeDefault         = new SFDouble(DELAY_ATTR_TAILTIME_DFLT,0.0,null);
    
    setContent("\n\t\t<!-- TODO add child sound-processing input nodes here -->\n\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(DELAY_ATTR_CHANNELCOUNTMODE_NAME);
    if (attr != null)
      setChannelCountMode(attr.getValue());
    attr = root.getAttribute(DELAY_ATTR_CHANNELINTERPRETATION_NAME);
    if (attr != null)
      channelInterpretation = attr.getValue();
    attr = root.getAttribute(DELAY_ATTR_DELAYTIME_NAME);
    if (attr != null)
      delayTime = new SFDouble(attr.getValue(), 0.0, null);
    attr = root.getAttribute(DELAY_ATTR_DESCRIPTION_NAME);
    if (attr != null)
      description = attr.getValue();
    attr = root.getAttribute(DELAY_ATTR_ENABLED_NAME);
    if (attr != null)
      enabled = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(DELAY_ATTR_GAIN_NAME);
    if (attr != null)
      gain = new SFFloat(attr.getValue(), null, null);
    attr = root.getAttribute(DELAY_ATTR_MAXDELAYTIME_NAME);
    if (attr != null)
      maxDelayTime = new SFDouble(attr.getValue(), 0.0, null);
    
    attr = root.getAttribute(DELAY_ATTR_STARTTIME_NAME);
    if (attr != null)
      startTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(DELAY_ATTR_STOPTIME_NAME);
    if (attr != null)
      stopTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(DELAY_ATTR_PAUSETIME_NAME);
    if (attr != null)
      pauseTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(DELAY_ATTR_RESUMETIME_NAME);
    if (attr != null)
      resumeTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(DELAY_ATTR_TAILTIME_NAME);
    if (attr != null)
      tailTime = new SFDouble(attr.getValue(), null, null);
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return DELAYCustomizer.class;
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    
    if (DELAY_ATTR_CHANNELCOUNTMODE_REQD || !channelCountMode.equals(channelCountModeDefault)) {
      sb.append(" ");
      sb.append(DELAY_ATTR_CHANNELCOUNTMODE_NAME);
      sb.append("='");
      sb.append(getChannelCountMode());
      sb.append("'");
    }
    
    if (DELAY_ATTR_CHANNELINTERPRETATION_REQD || !channelInterpretation.equals(channelInterpretationDefault)) {
      sb.append(" ");
      sb.append(DELAY_ATTR_CHANNELINTERPRETATION_NAME);
      sb.append("='");
      sb.append(getChannelInterpretation());
      sb.append("'");
    }
      
    if (DELAY_ATTR_DELAYTIME_REQD || !delayTime.equals(delayTimeDefault)) {
      sb.append(" ");
      sb.append(DELAY_ATTR_DELAYTIME_NAME);
      sb.append("='");
      sb.append(getDelayTime());
      sb.append("'");
    }
    
    if (DELAY_ATTR_DESCRIPTION_REQD || !description.equals(descriptionDefault)) {
      sb.append(" ");
      sb.append(DELAY_ATTR_DESCRIPTION_NAME);
      sb.append("='");
      sb.append(description);
      sb.append("'");
    }

    if (DELAY_ATTR_ENABLED_REQD || enabled != Boolean.parseBoolean(DELAY_ATTR_ENABLED_DFLT)) {
      sb.append(" ");
      sb.append(DELAY_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }
      
    if (DELAY_ATTR_GAIN_REQD || !gain.equals(gainDefault)) {
      sb.append(" ");
      sb.append(DELAY_ATTR_GAIN_NAME);
      sb.append("='");
      sb.append(getGain());
      sb.append("'");
    }
      
    if (DELAY_ATTR_MAXDELAYTIME_REQD || !maxDelayTime.equals(maxDelayTimeDefault)) {
      sb.append(" ");
      sb.append(DELAY_ATTR_MAXDELAYTIME_NAME);
      sb.append("='");
      sb.append(getMaxDelayTime());
      sb.append("'");
    }

    if (DELAY_ATTR_PAUSETIME_REQD || !pauseTime.equals(pauseTimeDefault)) {
      sb.append(" ");
      sb.append(DELAY_ATTR_PAUSETIME_NAME);
      sb.append("='");
      sb.append(pauseTime);
      sb.append("'");
    }
    if (DELAY_ATTR_RESUMETIME_REQD || !resumeTime.equals(resumeTimeDefault)) {
      sb.append(" ");
      sb.append(DELAY_ATTR_RESUMETIME_NAME);
      sb.append("='");
      sb.append(resumeTime);
      sb.append("'");
    }

    if (DELAY_ATTR_STARTTIME_REQD || !startTime.equals(startTimeDefault)) {
      sb.append(" ");
      sb.append(DELAY_ATTR_STARTTIME_NAME);
      sb.append("='");
      sb.append(startTime);
      sb.append("'");
    }
    if (DELAY_ATTR_STOPTIME_REQD || !stopTime.equals(stopTimeDefault)) {
      sb.append(" ");
      sb.append(DELAY_ATTR_STOPTIME_NAME);
      sb.append("='");
      sb.append(stopTime);
      sb.append("'");
    }
    if (DELAY_ATTR_TAILTIME_REQD || !tailTime.equals(tailTimeDefault)) {
      sb.append(" ");
      sb.append(DELAY_ATTR_TAILTIME_NAME);
      sb.append("='");
      sb.append(getTailTime());
      sb.append("'");
    }
    
    return sb.toString();
  }

    /**
     * @return the delayTime
     */
    public String getDelayTime() {
        return delayTime.toString();
    }

    /**
     * @param newDelayTime the delayTime to set
     */
    public void setDelayTime(String newDelayTime) {
        delayTime = new SFDouble(newDelayTime, null, null);
    }

    /**
     * @return the maxDelayTime
     */
    public String getMaxDelayTime() {
        return maxDelayTime.toString();
    }

    /**
     * @param newMaxDelayTime the maxDelayTime to set
     */
    public void setMaxDelayTime(String newMaxDelayTime) {
        maxDelayTime = new SFDouble(newMaxDelayTime, null, null);
    }

}
