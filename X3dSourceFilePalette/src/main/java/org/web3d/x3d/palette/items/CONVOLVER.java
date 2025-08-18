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
 * CONVOLVER:
 * Convolver performs a linear convolution on a given AudioBuffer, often used to achieve a reverberation effect. 
 * Potential modifications include chorus effects, reverberation, and telephone-like speech. 
 * The idea for producing room effects is to play back a reference sound in a room, record it, 
 * and then (metaphorically) take the difference between the original sound and the recorded one. 
 * The result of this is an impulse response that captures the effect that the room has on a sound.
 * 
 * @author Don Brutzman
 * @version $Id$
 */
public class CONVOLVER extends X3DSoundProcessingNode // and X3DTimeDependentNode
{
    private SFFloat[]   buffer, bufferDefault;
    private boolean  normalize, normalizeDefault;
    
  public CONVOLVER() 
  {
      this.setTraceEventsSelectionAvailable(false);
      this.setTraceEventsTooltip("Trace Convolver events on X3D browser console");
  }
  
  @Override
  public String getElementName()
  {
    return CONVOLVER_ELNAME;
  }
  
  @Override
  public void initialize()
  {
    String[] sa;
    if(CONVOLVER_ATTR_BUFFER_DFLT == null || CONVOLVER_ATTR_BUFFER_DFLT.length()<=0)
      sa = new String[]{}; // empty array
    else
      sa = parseX(CONVOLVER_ATTR_BUFFER_DFLT);
    buffer    =  bufferDefault           = parseToSFFloatArray(sa);
    normalize =  normalizeDefault        = Boolean.parseBoolean(CONVOLVER_ATTR_NORMALIZE_DFLT);
    
    channelCountMode      = channelCountModeDefault      = CONVOLVER_ATTR_CHANNELCOUNTMODE_DFLT;
    channelInterpretation = channelInterpretationDefault = CONVOLVER_ATTR_CHANNELINTERPRETATION_DFLT;
    description = descriptionDefault     = CONVOLVER_ATTR_DESCRIPTION_DFLT;
    enabled                              = Boolean.parseBoolean(CONVOLVER_ATTR_ENABLED_DFLT);
    gain       = gainDefault             = new SFFloat (CONVOLVER_ATTR_GAIN_DFLT,null,null); 
    startTime  =  startTimeDefault       = new SFDouble(CONVOLVER_ATTR_STARTTIME_DFLT,null,null);
    stopTime   =   stopTimeDefault       = new SFDouble(CONVOLVER_ATTR_STOPTIME_DFLT,null,null);  
    pauseTime  = pauseTimeDefault        = new SFDouble(CONVOLVER_ATTR_PAUSETIME_DFLT,null,null);
    resumeTime = resumeTimeDefault       = new SFDouble(CONVOLVER_ATTR_RESUMETIME_DFLT,null,null);
    tailTime   = tailTimeDefault         = new SFDouble(CONVOLVER_ATTR_TAILTIME_DFLT,0.0,null);
    
    setContent("\n\t\t<!-- TODO add child sound-processing input nodes here -->\n\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;
    String[] sa;

    attr = root.getAttribute(CONVOLVER_ATTR_CHANNELCOUNTMODE_NAME);
    if (attr != null)
      setChannelCountMode(attr.getValue());
    attr = root.getAttribute(CONVOLVER_ATTR_CHANNELINTERPRETATION_NAME);
    if (attr != null)
      channelInterpretation = attr.getValue();
    attr = root.getAttribute(CONVOLVER_ATTR_BUFFER_NAME);
    if (attr != null) {
      sa = parseX(attr.getValue());
      buffer = parseToSFFloatArray(sa);
    }
    attr = root.getAttribute(CONVOLVER_ATTR_DESCRIPTION_NAME);
    if (attr != null)
      description = attr.getValue();
    attr = root.getAttribute(CONVOLVER_ATTR_ENABLED_NAME);
    if (attr != null)
      enabled = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(CONVOLVER_ATTR_GAIN_NAME);
    if (attr != null)
      gain = new SFFloat(attr.getValue(), null, null);
    attr = root.getAttribute(CONVOLVER_ATTR_NORMALIZE_NAME);
    if (attr != null)
      normalize = Boolean.parseBoolean(attr.getValue());
    
    attr = root.getAttribute(CONVOLVER_ATTR_STARTTIME_NAME);
    if (attr != null)
      startTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(CONVOLVER_ATTR_STOPTIME_NAME);
    if (attr != null)
      stopTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(CONVOLVER_ATTR_PAUSETIME_NAME);
    if (attr != null)
      pauseTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(CONVOLVER_ATTR_RESUMETIME_NAME);
    if (attr != null)
      resumeTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(CONVOLVER_ATTR_TAILTIME_NAME);
    if (attr != null)
      tailTime = new SFDouble(attr.getValue(), null, null);
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return CONVOLVERCustomizer.class;
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    
    if (CONVOLVER_ATTR_CHANNELCOUNTMODE_REQD || !channelCountMode.equals(channelCountModeDefault)) {
      sb.append(" ");
      sb.append(CONVOLVER_ATTR_CHANNELCOUNTMODE_NAME);
      sb.append("='");
      sb.append(getChannelCountMode());
      sb.append("'");
    }
    
    if (CONVOLVER_ATTR_CHANNELINTERPRETATION_REQD || !channelInterpretation.equals(channelInterpretationDefault)) {
      sb.append(" ");
      sb.append(CONVOLVER_ATTR_CHANNELINTERPRETATION_NAME);
      sb.append("='");
      sb.append(getChannelInterpretation());
      sb.append("'");
    }
      
    if ((CONVOLVER_ATTR_BUFFER_REQD || !arraysIdenticalOrNull(buffer,bufferDefault)) && buffer.length > 0) {
      sb.append(" ");
      sb.append(CONVOLVER_ATTR_BUFFER_NAME);
      sb.append("='");
      sb.append(formatFloatArray(buffer));
      sb.append("'");
    }
    
    if (CONVOLVER_ATTR_DESCRIPTION_REQD || !description.equals(descriptionDefault)) {
      sb.append(" ");
      sb.append(CONVOLVER_ATTR_DESCRIPTION_NAME);
      sb.append("='");
      sb.append(description);
      sb.append("'");
    }

    if (CONVOLVER_ATTR_ENABLED_REQD || enabled != Boolean.parseBoolean(CONVOLVER_ATTR_ENABLED_DFLT)) {
      sb.append(" ");
      sb.append(CONVOLVER_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }
      
    if (CONVOLVER_ATTR_GAIN_REQD || !gain.equals(gainDefault)) {
      sb.append(" ");
      sb.append(CONVOLVER_ATTR_GAIN_NAME);
      sb.append("='");
      sb.append(getGain());
      sb.append("'");
    }
      
    if (CONVOLVER_ATTR_NORMALIZE_REQD || normalize!= Boolean.parseBoolean(CONVOLVER_ATTR_NORMALIZE_DFLT)) {
      sb.append(" ");
      sb.append(CONVOLVER_ATTR_NORMALIZE_NAME);
      sb.append("='");
      sb.append(isNormalized());
      sb.append("'");
    }

    if (CONVOLVER_ATTR_PAUSETIME_REQD || !pauseTime.equals(pauseTimeDefault)) {
      sb.append(" ");
      sb.append(CONVOLVER_ATTR_PAUSETIME_NAME);
      sb.append("='");
      sb.append(pauseTime);
      sb.append("'");
    }
    if (CONVOLVER_ATTR_RESUMETIME_REQD || !resumeTime.equals(resumeTimeDefault)) {
      sb.append(" ");
      sb.append(CONVOLVER_ATTR_RESUMETIME_NAME);
      sb.append("='");
      sb.append(resumeTime);
      sb.append("'");
    }

    if (CONVOLVER_ATTR_STARTTIME_REQD || !startTime.equals(startTimeDefault)) {
      sb.append(" ");
      sb.append(CONVOLVER_ATTR_STARTTIME_NAME);
      sb.append("='");
      sb.append(startTime);
      sb.append("'");
    }
    if (CONVOLVER_ATTR_STOPTIME_REQD || !stopTime.equals(stopTimeDefault)) {
      sb.append(" ");
      sb.append(CONVOLVER_ATTR_STOPTIME_NAME);
      sb.append("='");
      sb.append(stopTime);
      sb.append("'");
    }
    if (CONVOLVER_ATTR_TAILTIME_REQD || !tailTime.equals(tailTimeDefault)) {
      sb.append(" ");
      sb.append(CONVOLVER_ATTR_TAILTIME_NAME);
      sb.append("='");
      sb.append(getTailTime());
      sb.append("'");
    }
    
    return sb.toString();
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
     * @param newCurve the buffer to set
     */
    public void setCurve(String newCurve) {
        String[] sa;
        if ((newCurve != null) && !newCurve.isBlank()) {
          sa = parseX(newCurve);
        }
        else sa = new String[]{}; // empty array
        buffer = parseToSFFloatArray(sa);
    }

    /**
     * @return the normalize
     */
    public boolean isNormalized() {
        return normalize;
    }

    /**
     * @param newNormalize the normalize to set
     */
    public void setNormalize(boolean newNormalize) {
        normalize = newNormalize;
    }

}
