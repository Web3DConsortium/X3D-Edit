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
import org.web3d.x3d.types.X3DTimeDependentNode;

/**
 * GAIN.java
 * Created on August 16, 2007, 1:40 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class GAIN extends X3DTimeDependentNode // and X3DSensorNode
{
  private String   channelCountMode, channelCountModeDefault;
  private String   channelInterpretation, channelInterpretationDefault;
  private String   description, descriptionDefault;
  private boolean  enabled;
  private SFFloat  gain;
  private SFDouble startTime, startTimeDefault;
  private SFDouble stopTime, stopTimeDefault;
  private SFDouble pauseTime, pauseTimeDefault;
  private SFDouble resumeTime, resumeTimeDefault;
  private SFDouble tailTime, tailTimeDefault;

  public GAIN() 
  {
      this.setTraceEventsSelectionAvailable(true);
      this.setTraceEventsTooltip("Trace Gain events on X3D browser console");
  }
  
  @Override
  public String getElementName()
  {
    return GAIN_ELNAME;
  }
  
  @Override
  public void initialize()
  {
    channelCountMode      = channelCountModeDefault      = GAIN_ATTR_CHANNELCOUNTMODE_DFLT;
    channelInterpretation = channelInterpretationDefault = GAIN_ATTR_CHANNELINTERPRETATION_DFLT;
    description = descriptionDefault     = GAIN_ATTR_DESCRIPTION_DFLT;
    enabled                              = Boolean.parseBoolean(GAIN_ATTR_ENABLED_DFLT);
    gain                                 = new SFFloat (GAIN_ATTR_GAIN_DFLT,null,null); 
    startTime  =  startTimeDefault       = new SFDouble(GAIN_ATTR_STARTTIME_DFLT,null,null);
    stopTime   =   stopTimeDefault       = new SFDouble(GAIN_ATTR_STOPTIME_DFLT,null,null);  
    pauseTime  = pauseTimeDefault        = new SFDouble(GAIN_ATTR_PAUSETIME_DFLT,null,null);
    resumeTime = resumeTimeDefault       = new SFDouble(GAIN_ATTR_RESUMETIME_DFLT,null,null);
    tailTime   = tailTimeDefault         = new SFDouble(GAIN_ATTR_TAILTIME_DFLT,null,null);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(GAIN_ATTR_CHANNELCOUNTMODE_NAME);
    if (attr != null)
      channelCountMode = attr.getValue();
    attr = root.getAttribute(GAIN_ATTR_CHANNELINTERPRETATION_NAME);
    if (attr != null)
      channelInterpretation = attr.getValue();
    attr = root.getAttribute(GAIN_ATTR_DESCRIPTION_NAME);
    if (attr != null)
      description = attr.getValue();
    attr = root.getAttribute(GAIN_ATTR_ENABLED_NAME);
    if (attr != null)
      enabled = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(GAIN_ATTR_GAIN_NAME);
    if (attr != null)
      gain = new SFFloat(attr.getValue(), null, null);
    
    attr = root.getAttribute(GAIN_ATTR_STARTTIME_NAME);
    if (attr != null)
      startTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(GAIN_ATTR_STOPTIME_NAME);
    if (attr != null)
      stopTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(GAIN_ATTR_PAUSETIME_NAME);
    if (attr != null)
      pauseTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(GAIN_ATTR_RESUMETIME_NAME);
    if (attr != null)
      resumeTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(GAIN_ATTR_TAILTIME_NAME);
    if (attr != null)
      tailTime = new SFDouble(attr.getValue(), null, null);
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return GAINCustomizer.class;
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    
    if (GAIN_ATTR_CHANNELCOUNTMODE_REQD || !channelCountMode.equals(channelCountModeDefault)) {
      sb.append(" ");
      sb.append(GAIN_ATTR_CHANNELCOUNTMODE_NAME);
      sb.append("='");
      sb.append(channelCountMode);
      sb.append("'");
    }
    
    if (GAIN_ATTR_CHANNELINTERPRETATION_REQD || !channelInterpretation.equals(channelInterpretationDefault)) {
      sb.append(" ");
      sb.append(GAIN_ATTR_CHANNELINTERPRETATION_NAME);
      sb.append("='");
      sb.append(channelInterpretation);
      sb.append("'");
    }
    
    if (GAIN_ATTR_DESCRIPTION_REQD || !description.equals(descriptionDefault)) {
      sb.append(" ");
      sb.append(GAIN_ATTR_DESCRIPTION_NAME);
      sb.append("='");
      sb.append(description);
      sb.append("'");
    }

    if (GAIN_ATTR_ENABLED_REQD || enabled != Boolean.parseBoolean(GAIN_ATTR_ENABLED_DFLT)) {
      sb.append(" ");
      sb.append(GAIN_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }

    if (GAIN_ATTR_PAUSETIME_REQD || !pauseTime.equals(pauseTimeDefault)) {
      sb.append(" ");
      sb.append(GAIN_ATTR_PAUSETIME_NAME);
      sb.append("='");
      sb.append(pauseTime);
      sb.append("'");
    }
    if (GAIN_ATTR_RESUMETIME_REQD || !resumeTime.equals(resumeTimeDefault)) {
      sb.append(" ");
      sb.append(GAIN_ATTR_RESUMETIME_NAME);
      sb.append("='");
      sb.append(resumeTime);
      sb.append("'");
    }

    if (GAIN_ATTR_STARTTIME_REQD || !startTime.equals(startTimeDefault)) {
      sb.append(" ");
      sb.append(GAIN_ATTR_STARTTIME_NAME);
      sb.append("='");
      sb.append(startTime);
      sb.append("'");
    }
    if (GAIN_ATTR_STOPTIME_REQD || !stopTime.equals(stopTimeDefault)) {
      sb.append(" ");
      sb.append(GAIN_ATTR_STOPTIME_NAME);
      sb.append("='");
      sb.append(stopTime);
      sb.append("'");
    }
    if (GAIN_ATTR_TAILTIME_REQD || !tailTime.equals(tailTimeDefault)) {
      sb.append(" ");
      sb.append(GAIN_ATTR_TAILTIME_NAME);
      sb.append("='");
      sb.append(tailTime);
      sb.append("'");
    }
    
    return sb.toString();
  }
    
  public String getChannelCountMode()
  {
    return channelCountMode;
  }

  public void setChannelCountMode(String newChannelCountMode)
  {
    channelCountMode = newChannelCountMode;
  }
    
  public String getChannelInterpretation()
  {
    return channelInterpretation;
  }

  public void setChannelInterpretation(String newChannelInterpretation)
  {
    channelInterpretation = newChannelInterpretation;
  }
  
    /**
     * accessor method for field
     * @return value
     */
  public String getDescription()
  {
    return description;
  }

    /**
     * accessor method for field
     * @param newDescription value of interest
     */
  public void setDescription(String newDescription)
  {
    this.description = newDescription;
  }

  public boolean isEnabled()
  {
    return enabled;
  }

  public void setEnabled(boolean enabled)
  {
    this.enabled = enabled;
  }
  public String getPauseTime()
  {
    return pauseTime.toString();
  }

  public void setPauseTime(String pauseTime)
  {
    this.pauseTime = new SFDouble(pauseTime,null,null);
  }

  public String getResumeTime()
  {
    return resumeTime.toString();
  }

  public void setResumeTime(String resumeTime)
  {
    this.resumeTime = new SFDouble(resumeTime,null,null);
  }

  public String getStartTime()
  {
    return startTime.toString();
  }

  public void setStartTime(String startTime)
  {
    this.startTime = new SFDouble(startTime,null,null);
  }

  public String getStopTime()
  {
    return stopTime.toString();
  }

  public void setStopTime(String stopTime)
  {
    this.stopTime = new SFDouble(stopTime,null,null);
  }

  public String getTailTime()
  {
    return tailTime.toString();
  }

  public void setTailTime(String newTailTime)
  {
    tailTime = new SFDouble(newTailTime,null,null);
  }

}
