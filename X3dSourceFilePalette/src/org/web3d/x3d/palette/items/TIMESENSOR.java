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
import org.web3d.x3d.types.X3DTimeDependentNode;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * TIMESENSOR.java
 * Created on August 16, 2007, 1:40 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class TIMESENSOR extends X3DTimeDependentNode // and X3DSensorNode
{
  private SFFloat cycleInterval, cycleIntervalDefault;
  private SFFloat startTime, startTimeDefault;
  private SFFloat stopTime, stopTimeDefault;
  
  private SFFloat pauseTime, pauseTimeDefault;
  private SFFloat resumeTime, resumeTimeDefault;
  private boolean enabled, loop;
  
  // TODO description, when added to specification

  public TIMESENSOR() 
  {
      this.setTraceEventsSelectionAvailable(true);
      this.setTraceEventsTooltip("Trace TimeSensor events on X3D browser console");
  }
  
  @Override
  public String getElementName()
  {
    return TIMESENSOR_ELNAME;
  }
  
  @Override
  public void initialize()
  {
    cycleInterval = cycleIntervalDefault = new SFFloat(TIMESENSOR_ATTR_CYCLEINTERVAL_DFLT,0.0f,null);
    startTime = startTimeDefault         = new SFFloat(TIMESENSOR_ATTR_STARTTIME_DFLT,null,null);
    stopTime = stopTimeDefault           = new SFFloat(TIMESENSOR_ATTR_STOPTIME_DFLT,null,null);  
    pauseTime =  pauseTimeDefault        = new SFFloat(TIMESENSOR_ATTR_PAUSETIME_DFLT,null,null);
    resumeTime = resumeTimeDefault       = new SFFloat(TIMESENSOR_ATTR_RESUMETIME_DFLT,null,null);
    enabled                              = Boolean.parseBoolean(TIMESENSOR_ATTR_ENABLED_DFLT);
    loop                                 = Boolean.parseBoolean(TIMESENSOR_ATTR_LOOP_DFLT);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(TIMESENSOR_ATTR_CYCLEINTERVAL_NAME);
    if (attr != null)
      cycleInterval = new SFFloat(attr.getValue(), 0.0f, null);
    attr = root.getAttribute(TIMESENSOR_ATTR_STARTTIME_NAME);
    if (attr != null)
      startTime = new SFFloat(attr.getValue(), null, null);
    attr = root.getAttribute(TIMESENSOR_ATTR_STOPTIME_NAME);
    if (attr != null)
      stopTime = new SFFloat(attr.getValue(), null, null);
    attr = root.getAttribute(TIMESENSOR_ATTR_PAUSETIME_NAME);
    if (attr != null)
      pauseTime = new SFFloat(attr.getValue(), null, null);
    attr = root.getAttribute(TIMESENSOR_ATTR_RESUMETIME_NAME);
    if (attr != null)
      resumeTime = new SFFloat(attr.getValue(), null, null);
    attr = root.getAttribute(TIMESENSOR_ATTR_ENABLED_NAME);
    if (attr != null)
      enabled = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(TIMESENSOR_ATTR_LOOP_NAME);
    if (attr != null)
      loop = Boolean.parseBoolean(attr.getValue());
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return TIMESENSORCustomizer.class;
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    if (TIMESENSOR_ATTR_CYCLEINTERVAL_REQD || !cycleInterval.equals(cycleIntervalDefault)) {
      sb.append(" ");
      sb.append(TIMESENSOR_ATTR_CYCLEINTERVAL_NAME);
      sb.append("='");
      sb.append(cycleInterval);
      sb.append("'");
    }

    if (TIMESENSOR_ATTR_STARTTIME_REQD || !startTime.equals(startTimeDefault)) {
      sb.append(" ");
      sb.append(TIMESENSOR_ATTR_STARTTIME_NAME);
      sb.append("='");
      sb.append(startTime);
      sb.append("'");
    }

    if (TIMESENSOR_ATTR_STOPTIME_REQD || !stopTime.equals(stopTimeDefault)) {
      sb.append(" ");
      sb.append(TIMESENSOR_ATTR_STOPTIME_NAME);
      sb.append("='");
      sb.append(stopTime);
      sb.append("'");
    }

    if (TIMESENSOR_ATTR_PAUSETIME_REQD || !pauseTime.equals(pauseTimeDefault)) {
      sb.append(" ");
      sb.append(TIMESENSOR_ATTR_PAUSETIME_NAME);
      sb.append("='");
      sb.append(pauseTime);
      sb.append("'");
    }
    if (TIMESENSOR_ATTR_RESUMETIME_REQD || !resumeTime.equals(resumeTimeDefault)) {
      sb.append(" ");
      sb.append(TIMESENSOR_ATTR_RESUMETIME_NAME);
      sb.append("='");
      sb.append(resumeTime);
      sb.append("'");
    }

    if (TIMESENSOR_ATTR_ENABLED_REQD || enabled != Boolean.parseBoolean(TIMESENSOR_ATTR_ENABLED_DFLT)) {
      sb.append(" ");
      sb.append(TIMESENSOR_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }
    if (TIMESENSOR_ATTR_LOOP_REQD || loop != Boolean.parseBoolean(TIMESENSOR_ATTR_LOOP_DFLT)) {
      sb.append(" ");
      sb.append(TIMESENSOR_ATTR_LOOP_NAME);
      sb.append("='");
      sb.append(loop);
      sb.append("'");
    }
    return sb.toString();
  }
    
  public String getCycleInterval()
  {
    return cycleInterval.toString();
  }

  public void setCycleInterval(String cycleInterval)
  {
    this.cycleInterval = new SFFloat(cycleInterval,0.0f,null);
  }

  public boolean isEnabled()
  {
    return enabled;
  }

  public void setEnabled(boolean enabled)
  {
    this.enabled = enabled;
  }

  public boolean isLoop()
  {
    return loop;
  }

  public void setLoop(boolean loop)
  {
    this.loop = loop;
  }

  public String getPauseTime()
  {
    return pauseTime.toString();
  }

  public void setPauseTime(String pauseTime)
  {
    this.pauseTime = new SFFloat(pauseTime,null,null);
  }

  public String getResumeTime()
  {
    return resumeTime.toString();
  }

  public void setResumeTime(String resumeTime)
  {
    this.resumeTime = new SFFloat(resumeTime,null,null);
  }

  public String getStartTime()
  {
    return startTime.toString();
  }

  public void setStartTime(String startTime)
  {
    this.startTime = new SFFloat(startTime,null,null);
  }

  public String getStopTime()
  {
    return stopTime.toString();
  }

  public void setStopTime(String stopTime)
  {
    this.stopTime = new SFFloat(stopTime,null,null);
  }

}
