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
import static org.web3d.x3d.types.X3DSchemaData4.parseX;
import org.web3d.x3d.types.X3DSoundProcessingNode;

/**
 * WAVESHAPER.java
 *
 * @author Don Brutzman
 * @version $Id$
 */
public class WAVESHAPER extends X3DSoundProcessingNode // and X3DTimeDependentNode
{
    private SFFloat[]   curve, curveDefault;
    private String oversample, oversampleDefault;
    
  public WAVESHAPER() 
  {
      this.setTraceEventsSelectionAvailable(true);
      this.setTraceEventsTooltip("Trace Delay events on X3D browser console");
  }
  
  @Override
  public String getElementName()
  {
    return WAVESHAPER_ELNAME;
  }
  
  @Override
  public void initialize()
  {
    String[] sa;
    if(WAVESHAPER_ATTR_CURVE_DFLT == null || WAVESHAPER_ATTR_CURVE_DFLT.length()<=0)
      sa = new String[]{}; // empty array
    else
      sa = parseX(WAVESHAPER_ATTR_CURVE_DFLT);
    curve      =  curveDefault           = parseToSFFloatArray(sa);
    oversample =  oversampleDefault      = WAVESHAPER_ATTR_OVERSAMPLE_DFLT;
    
    channelCountMode      = channelCountModeDefault      = WAVESHAPER_ATTR_CHANNELCOUNTMODE_DFLT;
    channelInterpretation = channelInterpretationDefault = WAVESHAPER_ATTR_CHANNELINTERPRETATION_DFLT;
    description = descriptionDefault     = WAVESHAPER_ATTR_DESCRIPTION_DFLT;
    enabled                              = Boolean.parseBoolean(WAVESHAPER_ATTR_ENABLED_DFLT);
    gain       = gainDefault             = new SFFloat (WAVESHAPER_ATTR_GAIN_DFLT,null,null); 
    startTime  =  startTimeDefault       = new SFDouble(WAVESHAPER_ATTR_STARTTIME_DFLT,null,null);
    stopTime   =   stopTimeDefault       = new SFDouble(WAVESHAPER_ATTR_STOPTIME_DFLT,null,null);  
    pauseTime  = pauseTimeDefault        = new SFDouble(WAVESHAPER_ATTR_PAUSETIME_DFLT,null,null);
    resumeTime = resumeTimeDefault       = new SFDouble(WAVESHAPER_ATTR_RESUMETIME_DFLT,null,null);
    tailTime   = tailTimeDefault         = new SFDouble(WAVESHAPER_ATTR_TAILTIME_DFLT,0.0,null);
    
    setContent("\n\t\t<!-- TODO add child sound-processing input nodes here -->\n\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;
    String[] sa;

    attr = root.getAttribute(WAVESHAPER_ATTR_CHANNELCOUNTMODE_NAME);
    if (attr != null)
      setChannelCountMode(attr.getValue());
    attr = root.getAttribute(WAVESHAPER_ATTR_CHANNELINTERPRETATION_NAME);
    if (attr != null)
      channelInterpretation = attr.getValue();
    attr = root.getAttribute(WAVESHAPER_ATTR_CURVE_NAME);
    if (attr != null) {
      sa = parseX(attr.getValue());
      curve = parseToSFFloatArray(sa);
    }
    attr = root.getAttribute(WAVESHAPER_ATTR_DESCRIPTION_NAME);
    if (attr != null)
      description = attr.getValue();
    attr = root.getAttribute(WAVESHAPER_ATTR_ENABLED_NAME);
    if (attr != null)
      enabled = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(WAVESHAPER_ATTR_GAIN_NAME);
    if (attr != null)
      gain = new SFFloat(attr.getValue(), null, null);
    attr = root.getAttribute(WAVESHAPER_ATTR_OVERSAMPLE_NAME);
    if (attr != null)
      oversample = attr.getValue();
    
    attr = root.getAttribute(WAVESHAPER_ATTR_STARTTIME_NAME);
    if (attr != null)
      startTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(WAVESHAPER_ATTR_STOPTIME_NAME);
    if (attr != null)
      stopTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(WAVESHAPER_ATTR_PAUSETIME_NAME);
    if (attr != null)
      pauseTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(WAVESHAPER_ATTR_RESUMETIME_NAME);
    if (attr != null)
      resumeTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(WAVESHAPER_ATTR_TAILTIME_NAME);
    if (attr != null)
      tailTime = new SFDouble(attr.getValue(), null, null);
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return WAVESHAPERCustomizer.class;
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    
    if (WAVESHAPER_ATTR_CHANNELCOUNTMODE_REQD || !channelCountMode.equals(channelCountModeDefault)) {
      sb.append(" ");
      sb.append(WAVESHAPER_ATTR_CHANNELCOUNTMODE_NAME);
      sb.append("='");
      sb.append(getChannelCountMode());
      sb.append("'");
    }
    
    if (WAVESHAPER_ATTR_CHANNELINTERPRETATION_REQD || !channelInterpretation.equals(channelInterpretationDefault)) {
      sb.append(" ");
      sb.append(WAVESHAPER_ATTR_CHANNELINTERPRETATION_NAME);
      sb.append("='");
      sb.append(getChannelInterpretation());
      sb.append("'");
    }
      
    if ((WAVESHAPER_ATTR_CURVE_REQD || !arraysIdenticalOrNull(curve,curveDefault)) && curve.length > 0) {
      sb.append(" ");
      sb.append(WAVESHAPER_ATTR_CURVE_NAME);
      sb.append("='");
      sb.append(formatFloatArray(curve));
      sb.append("'");
    }
    
    if (WAVESHAPER_ATTR_DESCRIPTION_REQD || !description.equals(descriptionDefault)) {
      sb.append(" ");
      sb.append(WAVESHAPER_ATTR_DESCRIPTION_NAME);
      sb.append("='");
      sb.append(description);
      sb.append("'");
    }

    if (WAVESHAPER_ATTR_ENABLED_REQD || enabled != Boolean.parseBoolean(WAVESHAPER_ATTR_ENABLED_DFLT)) {
      sb.append(" ");
      sb.append(WAVESHAPER_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }
      
    if (WAVESHAPER_ATTR_GAIN_REQD || !gain.equals(gainDefault)) {
      sb.append(" ");
      sb.append(WAVESHAPER_ATTR_GAIN_NAME);
      sb.append("='");
      sb.append(getGain());
      sb.append("'");
    }
      
    if (WAVESHAPER_ATTR_OVERSAMPLE_REQD || !oversample.equals(oversampleDefault)) {
      sb.append(" ");
      sb.append(WAVESHAPER_ATTR_OVERSAMPLE_NAME);
      sb.append("='");
      sb.append(getOversample());
      sb.append("'");
    }

    if (WAVESHAPER_ATTR_PAUSETIME_REQD || !pauseTime.equals(pauseTimeDefault)) {
      sb.append(" ");
      sb.append(WAVESHAPER_ATTR_PAUSETIME_NAME);
      sb.append("='");
      sb.append(pauseTime);
      sb.append("'");
    }
    if (WAVESHAPER_ATTR_RESUMETIME_REQD || !resumeTime.equals(resumeTimeDefault)) {
      sb.append(" ");
      sb.append(WAVESHAPER_ATTR_RESUMETIME_NAME);
      sb.append("='");
      sb.append(resumeTime);
      sb.append("'");
    }

    if (WAVESHAPER_ATTR_STARTTIME_REQD || !startTime.equals(startTimeDefault)) {
      sb.append(" ");
      sb.append(WAVESHAPER_ATTR_STARTTIME_NAME);
      sb.append("='");
      sb.append(startTime);
      sb.append("'");
    }
    if (WAVESHAPER_ATTR_STOPTIME_REQD || !stopTime.equals(stopTimeDefault)) {
      sb.append(" ");
      sb.append(WAVESHAPER_ATTR_STOPTIME_NAME);
      sb.append("='");
      sb.append(stopTime);
      sb.append("'");
    }
    if (WAVESHAPER_ATTR_TAILTIME_REQD || !tailTime.equals(tailTimeDefault)) {
      sb.append(" ");
      sb.append(WAVESHAPER_ATTR_TAILTIME_NAME);
      sb.append("='");
      sb.append(getTailTime());
      sb.append("'");
    }
    
    return sb.toString();
  }

    /**
     * @return the curve
     */
    public String getCurve() {
        String value = new String();
        for (int i = 0; i < curve.length; i++)
        {
            value += " " + curve[i].toString();
        }
        return value.trim();
    }

    /**
     * @param newCurve the curve to set
     */
    public void setCurve(String newCurve) {
        String[] sa;
        if ((newCurve != null) && !newCurve.isBlank()) {
          sa = parseX(newCurve);
        }
        else sa = new String[]{}; // empty array
        curve = parseToSFFloatArray(sa);
    }

    /**
     * @return the oversample
     */
    public String getOversample() {
        return oversample;
    }

    /**
     * @param newOversample the oversample to set
     */
    public void setOversample(String newOversample) {
        oversample = newOversample;
    }

}
