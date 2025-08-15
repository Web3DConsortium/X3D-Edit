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
 * BIQUADFILTER.java
 *
 * @author Don Brutzman
 * @version $Id$
 */
public class BIQUADFILTER extends X3DSoundProcessingNode // and X3DTimeDependentNode
{
    private SFFloat detune, detuneDefault;
    private SFFloat frequency, frequencyDefault;
    private SFFloat qualityFactor, qualityFactorDefault;
    private String  type, typeDefault;
    
  public BIQUADFILTER() 
  {
      this.setTraceEventsSelectionAvailable(true);
      this.setTraceEventsTooltip("Trace Delay events on X3D browser console");
  }
  
  @Override
  public String getElementName()
  {
    return BIQUADFILTER_ELNAME;
  }
  
  @Override
  public void initialize()
  {
    detune        =  detuneDefault        = new SFFloat(BIQUADFILTER_ATTR_DETUNE_DFLT,0.0f,null);
    frequency     =  frequencyDefault     = new SFFloat(BIQUADFILTER_ATTR_FREQUENCY_DFLT,0.0f,null);
    qualityFactor =  qualityFactorDefault = new SFFloat(BIQUADFILTER_ATTR_QUALITYFACTOR_DFLT,0.0f,null);
    type          =  typeDefault          = BIQUADFILTER_ATTR_TYPE_DFLT;
    
    channelCountMode      = channelCountModeDefault      = BIQUADFILTER_ATTR_CHANNELCOUNTMODE_DFLT;
    channelInterpretation = channelInterpretationDefault = BIQUADFILTER_ATTR_CHANNELINTERPRETATION_DFLT;
    description = descriptionDefault     = BIQUADFILTER_ATTR_DESCRIPTION_DFLT;
    enabled                              = Boolean.parseBoolean(BIQUADFILTER_ATTR_ENABLED_DFLT);
    gain       = gainDefault             = new SFFloat (BIQUADFILTER_ATTR_GAIN_DFLT,null,null); 
    startTime  =  startTimeDefault       = new SFDouble(BIQUADFILTER_ATTR_STARTTIME_DFLT,null,null);
    stopTime   =   stopTimeDefault       = new SFDouble(BIQUADFILTER_ATTR_STOPTIME_DFLT,null,null);  
    pauseTime  = pauseTimeDefault        = new SFDouble(BIQUADFILTER_ATTR_PAUSETIME_DFLT,null,null);
    resumeTime = resumeTimeDefault       = new SFDouble(BIQUADFILTER_ATTR_RESUMETIME_DFLT,null,null);
    tailTime   = tailTimeDefault         = new SFDouble(BIQUADFILTER_ATTR_TAILTIME_DFLT,0.0,null);
    
    setContent("\n\t\t<!-- TODO add child sound-processing input nodes here -->\n\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(BIQUADFILTER_ATTR_DETUNE_NAME);
    if (attr != null)
      detune = new SFFloat(attr.getValue(), 0.0f, null);
    attr = root.getAttribute(BIQUADFILTER_ATTR_CHANNELCOUNTMODE_NAME);
    if (attr != null)
      setChannelCountMode(attr.getValue());
    attr = root.getAttribute(BIQUADFILTER_ATTR_CHANNELINTERPRETATION_NAME);
    if (attr != null)
      channelInterpretation = attr.getValue();
    attr = root.getAttribute(BIQUADFILTER_ATTR_DESCRIPTION_NAME);
    if (attr != null)
      description = attr.getValue();
    attr = root.getAttribute(BIQUADFILTER_ATTR_ENABLED_NAME);
    if (attr != null)
      enabled = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(BIQUADFILTER_ATTR_GAIN_NAME);
    if (attr != null)
      gain = new SFFloat(attr.getValue(), null, null);
    attr = root.getAttribute(BIQUADFILTER_ATTR_FREQUENCY_NAME);
    if (attr != null)
      frequency = new SFFloat(attr.getValue(), 0.0f, null);
    attr = root.getAttribute(BIQUADFILTER_ATTR_QUALITYFACTOR_NAME);
    if (attr != null)
      qualityFactor = new SFFloat(attr.getValue(), 0.0f, null);
    attr = root.getAttribute(BIQUADFILTER_ATTR_TYPE_NAME);
    if (attr != null)
      type = attr.getValue();
    
    attr = root.getAttribute(BIQUADFILTER_ATTR_STARTTIME_NAME);
    if (attr != null)
      startTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(BIQUADFILTER_ATTR_STOPTIME_NAME);
    if (attr != null)
      stopTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(BIQUADFILTER_ATTR_PAUSETIME_NAME);
    if (attr != null)
      pauseTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(BIQUADFILTER_ATTR_RESUMETIME_NAME);
    if (attr != null)
      resumeTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(BIQUADFILTER_ATTR_TAILTIME_NAME);
    if (attr != null)
      tailTime = new SFDouble(attr.getValue(), null, null);
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return BIQUADFILTERCustomizer.class;
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    
    if (BIQUADFILTER_ATTR_CHANNELCOUNTMODE_REQD || !channelCountMode.equals(channelCountModeDefault)) {
      sb.append(" ");
      sb.append(BIQUADFILTER_ATTR_CHANNELCOUNTMODE_NAME);
      sb.append("='");
      sb.append(getChannelCountMode());
      sb.append("'");
    }
    
    if (BIQUADFILTER_ATTR_CHANNELINTERPRETATION_REQD || !channelInterpretation.equals(channelInterpretationDefault)) {
      sb.append(" ");
      sb.append(BIQUADFILTER_ATTR_CHANNELINTERPRETATION_NAME);
      sb.append("='");
      sb.append(getChannelInterpretation());
      sb.append("'");
    }
    
    if (BIQUADFILTER_ATTR_DESCRIPTION_REQD || !description.equals(descriptionDefault)) {
      sb.append(" ");
      sb.append(BIQUADFILTER_ATTR_DESCRIPTION_NAME);
      sb.append("='");
      sb.append(description);
      sb.append("'");
    }
      
    if (BIQUADFILTER_ATTR_DETUNE_REQD || !detune.equals(detuneDefault)) {
      sb.append(" ");
      sb.append(BIQUADFILTER_ATTR_DETUNE_NAME);
      sb.append("='");
      sb.append(getDetune());
      sb.append("'");
    }

    if (BIQUADFILTER_ATTR_ENABLED_REQD || enabled != Boolean.parseBoolean(BIQUADFILTER_ATTR_ENABLED_DFLT)) {
      sb.append(" ");
      sb.append(BIQUADFILTER_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }
      
    if (BIQUADFILTER_ATTR_FREQUENCY_REQD || !frequency.equals(frequencyDefault)) {
      sb.append(" ");
      sb.append(BIQUADFILTER_ATTR_FREQUENCY_NAME);
      sb.append("='");
      sb.append(getFrequency());
      sb.append("'");
    }
      
    if (BIQUADFILTER_ATTR_GAIN_REQD || !gain.equals(gainDefault)) {
      sb.append(" ");
      sb.append(BIQUADFILTER_ATTR_GAIN_NAME);
      sb.append("='");
      sb.append(getGain());
      sb.append("'");
    }

    if (BIQUADFILTER_ATTR_PAUSETIME_REQD || !pauseTime.equals(pauseTimeDefault)) {
      sb.append(" ");
      sb.append(BIQUADFILTER_ATTR_PAUSETIME_NAME);
      sb.append("='");
      sb.append(pauseTime);
      sb.append("'");
    }
    if (BIQUADFILTER_ATTR_QUALITYFACTOR_REQD || !qualityFactor.equals(qualityFactorDefault)) {
      sb.append(" ");
      sb.append(BIQUADFILTER_ATTR_QUALITYFACTOR_NAME);
      sb.append("='");
      sb.append(getQualityFactor());
      sb.append("'");
    }
    if (BIQUADFILTER_ATTR_TYPE_REQD || !type.equals(typeDefault)) {
      sb.append(" ");
      sb.append(BIQUADFILTER_ATTR_TYPE_NAME);
      sb.append("='");
      sb.append(getType());
      sb.append("'");
    }
    if (BIQUADFILTER_ATTR_RESUMETIME_REQD || !resumeTime.equals(resumeTimeDefault)) {
      sb.append(" ");
      sb.append(BIQUADFILTER_ATTR_RESUMETIME_NAME);
      sb.append("='");
      sb.append(resumeTime);
      sb.append("'");
    }

    if (BIQUADFILTER_ATTR_STARTTIME_REQD || !startTime.equals(startTimeDefault)) {
      sb.append(" ");
      sb.append(BIQUADFILTER_ATTR_STARTTIME_NAME);
      sb.append("='");
      sb.append(startTime);
      sb.append("'");
    }
    if (BIQUADFILTER_ATTR_STOPTIME_REQD || !stopTime.equals(stopTimeDefault)) {
      sb.append(" ");
      sb.append(BIQUADFILTER_ATTR_STOPTIME_NAME);
      sb.append("='");
      sb.append(stopTime);
      sb.append("'");
    }
    if (BIQUADFILTER_ATTR_TAILTIME_REQD || !tailTime.equals(tailTimeDefault)) {
      sb.append(" ");
      sb.append(BIQUADFILTER_ATTR_TAILTIME_NAME);
      sb.append("='");
      sb.append(getTailTime());
      sb.append("'");
    }
    
    return sb.toString();
  }

    /**
     * @return the detune
     */
    public String getDetune() {
        return detune.toString();
    }

    /**
     * @param newDetune the detune to set
     */
    public void setDetune(String newDetune) {
        detune = new SFFloat(newDetune, 0.0f, null);
    }

    /**
     * @return the frequency
     */
    public String getFrequency() {
        return frequency.toString();
    }

    /**
     * @param newFrequency the frequency to set
     */
    public void setFrequency(String newFrequency) {
        frequency = new SFFloat(newFrequency, 0.0f, null);
    }

    /**
     * @return the qualityFactor
     */
    public String getQualityFactor() {
        return qualityFactor.toString();
    }

    /**
     * @param newQualityFactor the qualityFactor to set
     */
    public void setQualityFactor(String newQualityFactor) {
        qualityFactor = new SFFloat(newQualityFactor, 0.0f, null);
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param newType the type to set
     */
    public void setType(String newType) {
        type = newType;
    }

}
