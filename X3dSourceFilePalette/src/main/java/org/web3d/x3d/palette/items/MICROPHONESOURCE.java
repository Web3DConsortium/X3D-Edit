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
 * MICROPHONESOURCE:
 * MicrophoneSource captures input from a physical microphone in the real world.
 *
 * @author Don Brutzman
 * @version $Id$
 */
public class MICROPHONESOURCE extends X3DSoundProcessingNode // and X3DTimeDependentNode
{
    protected String   mediaDeviceID, mediaDeviceIDDefault;
    
  public MICROPHONESOURCE() 
  {
      this.setTraceEventsSelectionAvailable(false);
      this.setTraceEventsTooltip("Trace MicrophoneSource events on X3D browser console");
  }
  
  @Override
  public String getElementName()
  {
    return MICROPHONESOURCE_ELNAME;
  }
  
  @Override
  public void initialize()
  {
    description = descriptionDefault       = MICROPHONESOURCE_ATTR_DESCRIPTION_DFLT;
    enabled                                = Boolean.parseBoolean(MICROPHONESOURCE_ATTR_ENABLED_DFLT);
    gain       = gainDefault               = new SFFloat (MICROPHONESOURCE_ATTR_GAIN_DFLT,null,null); 
    startTime  =  startTimeDefault         = new SFDouble(MICROPHONESOURCE_ATTR_STARTTIME_DFLT,null,null);
    stopTime   =   stopTimeDefault         = new SFDouble(MICROPHONESOURCE_ATTR_STOPTIME_DFLT,null,null);  
    pauseTime  = pauseTimeDefault          = new SFDouble(MICROPHONESOURCE_ATTR_PAUSETIME_DFLT,null,null);
    resumeTime = resumeTimeDefault         = new SFDouble(MICROPHONESOURCE_ATTR_RESUMETIME_DFLT,null,null);
    mediaDeviceID   = mediaDeviceIDDefault = MICROPHONESOURCE_ATTR_MEDIADEVICEID_DFLT;
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(MICROPHONESOURCE_ATTR_DESCRIPTION_NAME);
    if (attr != null)
      description = attr.getValue();
    attr = root.getAttribute(MICROPHONESOURCE_ATTR_ENABLED_NAME);
    if (attr != null)
      enabled = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(MICROPHONESOURCE_ATTR_GAIN_NAME);
    if (attr != null)
      gain = new SFFloat(attr.getValue(), null, null);
    
    attr = root.getAttribute(MICROPHONESOURCE_ATTR_STARTTIME_NAME);
    if (attr != null)
      startTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(MICROPHONESOURCE_ATTR_STOPTIME_NAME);
    if (attr != null)
      stopTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(MICROPHONESOURCE_ATTR_PAUSETIME_NAME);
    if (attr != null)
      pauseTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(MICROPHONESOURCE_ATTR_RESUMETIME_NAME);
    if (attr != null)
      resumeTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(MICROPHONESOURCE_ATTR_MEDIADEVICEID_NAME);
    if (attr != null)
      mediaDeviceID = attr.getValue();
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return MICROPHONESOURCECustomizer.class;
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    
    if (MICROPHONESOURCE_ATTR_DESCRIPTION_REQD || !description.equals(descriptionDefault)) {
      sb.append(" ");
      sb.append(MICROPHONESOURCE_ATTR_DESCRIPTION_NAME);
      sb.append("='");
      sb.append(description);
      sb.append("'");
    }

    if (MICROPHONESOURCE_ATTR_ENABLED_REQD || enabled != Boolean.parseBoolean(MICROPHONESOURCE_ATTR_ENABLED_DFLT)) {
      sb.append(" ");
      sb.append(MICROPHONESOURCE_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }
      
    if (MICROPHONESOURCE_ATTR_GAIN_REQD || !gain.equals(gainDefault)) {
      sb.append(" ");
      sb.append(MICROPHONESOURCE_ATTR_GAIN_NAME);
      sb.append("='");
      sb.append(getGain());
      sb.append("'");
    }
    if (MICROPHONESOURCE_ATTR_MEDIADEVICEID_REQD || !mediaDeviceID.equals(mediaDeviceIDDefault)) {
      sb.append(" ");
      sb.append(MICROPHONESOURCE_ATTR_MEDIADEVICEID_NAME);
      sb.append("='");
      sb.append(mediaDeviceID);
      sb.append("'");
    }

    if (MICROPHONESOURCE_ATTR_PAUSETIME_REQD || !pauseTime.equals(pauseTimeDefault)) {
      sb.append(" ");
      sb.append(MICROPHONESOURCE_ATTR_PAUSETIME_NAME);
      sb.append("='");
      sb.append(pauseTime);
      sb.append("'");
    }
    if (MICROPHONESOURCE_ATTR_RESUMETIME_REQD || !resumeTime.equals(resumeTimeDefault)) {
      sb.append(" ");
      sb.append(MICROPHONESOURCE_ATTR_RESUMETIME_NAME);
      sb.append("='");
      sb.append(resumeTime);
      sb.append("'");
    }

    if (MICROPHONESOURCE_ATTR_STARTTIME_REQD || !startTime.equals(startTimeDefault)) {
      sb.append(" ");
      sb.append(MICROPHONESOURCE_ATTR_STARTTIME_NAME);
      sb.append("='");
      sb.append(startTime);
      sb.append("'");
    }
    if (MICROPHONESOURCE_ATTR_STOPTIME_REQD || !stopTime.equals(stopTimeDefault)) {
      sb.append(" ");
      sb.append(MICROPHONESOURCE_ATTR_STOPTIME_NAME);
      sb.append("='");
      sb.append(stopTime);
      sb.append("'");
    }
    
    return sb.toString();
  }

    /**
     * @return the mediaDeviceID
     */
    public String getMediaDeviceID() {
        return mediaDeviceID;
    }

    /**
     * @param newMediaDeviceID the mediaDeviceID to set
     */
    public void setMediaDeviceID(String newMediaDeviceID) {
        mediaDeviceID = newMediaDeviceID;
    }

}
