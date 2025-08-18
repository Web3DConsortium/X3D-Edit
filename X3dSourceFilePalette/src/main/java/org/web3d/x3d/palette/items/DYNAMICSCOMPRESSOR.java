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
 * DYNAMICSCOMPRESSOR:
 * DynamicsCompressor implements a dynamics compression effect, 
 * lowering the volume of the loudest parts of the signal and raising the volume of the softest parts.
 * 
 * @author Don Brutzman
 * @version $Id$
 */
public class DYNAMICSCOMPRESSOR extends X3DSoundProcessingNode // and X3DTimeDependentNode
{
    private SFDouble attack, attackDefault; // SFTime
    private SFFloat  knee, kneeDefault;
    private SFFloat  ratio, ratioDefault;
    private SFDouble release, releaseDefault; // SFTime
    private SFFloat  threshold, thresholdDefault;
    
  public DYNAMICSCOMPRESSOR() 
  {
      this.setTraceEventsSelectionAvailable(false);
      this.setTraceEventsTooltip("Trace DynamicsCompressor events on X3D browser console");
  }
  
  @Override
  public String getElementName()
  {
    return DYNAMICSCOMPRESSOR_ELNAME;
  }
  
  @Override
  public void initialize()
  {
    attack    =  attackDefault       = new SFDouble(DYNAMICSCOMPRESSOR_ATTR_ATTACK_DFLT,0.0,null); // SFTime
    knee      =  kneeDefault         = new SFFloat(DYNAMICSCOMPRESSOR_ATTR_KNEE_DFLT,0.0f,null);
    ratio     =  ratioDefault        = new SFFloat(DYNAMICSCOMPRESSOR_ATTR_RATIO_DFLT,0.0f,null);
    release   =  releaseDefault      = new SFDouble(DYNAMICSCOMPRESSOR_ATTR_RELEASE_DFLT,0.0,null); // SFTime
    threshold =  thresholdDefault    = new SFFloat(DYNAMICSCOMPRESSOR_ATTR_THRESHOLD_DFLT,-100.0f,0.0f);
    
    channelCountMode      = channelCountModeDefault      = DYNAMICSCOMPRESSOR_ATTR_CHANNELCOUNTMODE_DFLT;
    channelInterpretation = channelInterpretationDefault = DYNAMICSCOMPRESSOR_ATTR_CHANNELINTERPRETATION_DFLT;
    description = descriptionDefault     = DYNAMICSCOMPRESSOR_ATTR_DESCRIPTION_DFLT;
    enabled                              = Boolean.parseBoolean(DYNAMICSCOMPRESSOR_ATTR_ENABLED_DFLT);
    gain       = gainDefault             = new SFFloat (DYNAMICSCOMPRESSOR_ATTR_GAIN_DFLT,null,null); 
    startTime  =  startTimeDefault       = new SFDouble(DYNAMICSCOMPRESSOR_ATTR_STARTTIME_DFLT,null,null);
    stopTime   =   stopTimeDefault       = new SFDouble(DYNAMICSCOMPRESSOR_ATTR_STOPTIME_DFLT,null,null);  
    pauseTime  = pauseTimeDefault        = new SFDouble(DYNAMICSCOMPRESSOR_ATTR_PAUSETIME_DFLT,null,null);
    resumeTime = resumeTimeDefault       = new SFDouble(DYNAMICSCOMPRESSOR_ATTR_RESUMETIME_DFLT,null,null);
    tailTime   = tailTimeDefault         = new SFDouble(DYNAMICSCOMPRESSOR_ATTR_TAILTIME_DFLT,0.0,null);
    
    setContent("\n\t\t<!-- TODO add child sound-processing input nodes here -->\n\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(DYNAMICSCOMPRESSOR_ATTR_ATTACK_NAME);
    if (attr != null)
      attack = new SFDouble(attr.getValue(), 0.0, null);
    attr = root.getAttribute(DYNAMICSCOMPRESSOR_ATTR_CHANNELCOUNTMODE_NAME);
    if (attr != null)
      setChannelCountMode(attr.getValue());
    attr = root.getAttribute(DYNAMICSCOMPRESSOR_ATTR_CHANNELINTERPRETATION_NAME);
    if (attr != null)
      channelInterpretation = attr.getValue();
    attr = root.getAttribute(DYNAMICSCOMPRESSOR_ATTR_DESCRIPTION_NAME);
    if (attr != null)
      description = attr.getValue();
    attr = root.getAttribute(DYNAMICSCOMPRESSOR_ATTR_ENABLED_NAME);
    if (attr != null)
      enabled = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(DYNAMICSCOMPRESSOR_ATTR_GAIN_NAME);
    if (attr != null)
      gain = new SFFloat(attr.getValue(), null, null);
    attr = root.getAttribute(DYNAMICSCOMPRESSOR_ATTR_KNEE_NAME);
    if (attr != null)
      knee = new SFFloat(attr.getValue(), 0.0f, null);
    attr = root.getAttribute(DYNAMICSCOMPRESSOR_ATTR_RATIO_NAME);
    if (attr != null)
      ratio = new SFFloat(attr.getValue(), 0.0f, null);
    attr = root.getAttribute(DYNAMICSCOMPRESSOR_ATTR_RELEASE_NAME);
    if (attr != null)
      release = new SFDouble(attr.getValue(), 0.0, null);
    attr = root.getAttribute(DYNAMICSCOMPRESSOR_ATTR_THRESHOLD_NAME);
    if (attr != null)
      threshold = new SFFloat(attr.getValue(), -100.0f, 0.0f);
    
    attr = root.getAttribute(DYNAMICSCOMPRESSOR_ATTR_STARTTIME_NAME);
    if (attr != null)
      startTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(DYNAMICSCOMPRESSOR_ATTR_STOPTIME_NAME);
    if (attr != null)
      stopTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(DYNAMICSCOMPRESSOR_ATTR_PAUSETIME_NAME);
    if (attr != null)
      pauseTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(DYNAMICSCOMPRESSOR_ATTR_RESUMETIME_NAME);
    if (attr != null)
      resumeTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(DYNAMICSCOMPRESSOR_ATTR_TAILTIME_NAME);
    if (attr != null)
      tailTime = new SFDouble(attr.getValue(), null, null);
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return DYNAMICSCOMPRESSORCustomizer.class;
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
      
    if (DYNAMICSCOMPRESSOR_ATTR_ATTACK_REQD || !attack.equals(attackDefault)) {
      sb.append(" ");
      sb.append(DYNAMICSCOMPRESSOR_ATTR_ATTACK_NAME);
      sb.append("='");
      sb.append(getAttack());
      sb.append("'");
    }
    
    if (DYNAMICSCOMPRESSOR_ATTR_CHANNELCOUNTMODE_REQD || !channelCountMode.equals(channelCountModeDefault)) {
      sb.append(" ");
      sb.append(DYNAMICSCOMPRESSOR_ATTR_CHANNELCOUNTMODE_NAME);
      sb.append("='");
      sb.append(getChannelCountMode());
      sb.append("'");
    }
    
    if (DYNAMICSCOMPRESSOR_ATTR_CHANNELINTERPRETATION_REQD || !channelInterpretation.equals(channelInterpretationDefault)) {
      sb.append(" ");
      sb.append(DYNAMICSCOMPRESSOR_ATTR_CHANNELINTERPRETATION_NAME);
      sb.append("='");
      sb.append(getChannelInterpretation());
      sb.append("'");
    }
    
    if (DYNAMICSCOMPRESSOR_ATTR_DESCRIPTION_REQD || !description.equals(descriptionDefault)) {
      sb.append(" ");
      sb.append(DYNAMICSCOMPRESSOR_ATTR_DESCRIPTION_NAME);
      sb.append("='");
      sb.append(description);
      sb.append("'");
    }

    if (DYNAMICSCOMPRESSOR_ATTR_ENABLED_REQD || enabled != Boolean.parseBoolean(DYNAMICSCOMPRESSOR_ATTR_ENABLED_DFLT)) {
      sb.append(" ");
      sb.append(DYNAMICSCOMPRESSOR_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }
      
    if (DYNAMICSCOMPRESSOR_ATTR_GAIN_REQD || !gain.equals(gainDefault)) {
      sb.append(" ");
      sb.append(DYNAMICSCOMPRESSOR_ATTR_GAIN_NAME);
      sb.append("='");
      sb.append(getGain());
      sb.append("'");
    }
      
    if (DYNAMICSCOMPRESSOR_ATTR_KNEE_REQD || !knee.equals(kneeDefault)) {
      sb.append(" ");
      sb.append(DYNAMICSCOMPRESSOR_ATTR_KNEE_NAME);
      sb.append("='");
      sb.append(getKnee());
      sb.append("'");
    }

    if (DYNAMICSCOMPRESSOR_ATTR_PAUSETIME_REQD || !pauseTime.equals(pauseTimeDefault)) {
      sb.append(" ");
      sb.append(DYNAMICSCOMPRESSOR_ATTR_PAUSETIME_NAME);
      sb.append("='");
      sb.append(pauseTime);
      sb.append("'");
    }
    if (DYNAMICSCOMPRESSOR_ATTR_RATIO_REQD || !ratio.equals(ratioDefault)) {
      sb.append(" ");
      sb.append(DYNAMICSCOMPRESSOR_ATTR_RATIO_NAME);
      sb.append("='");
      sb.append(getRatio());
      sb.append("'");
    }
    if (DYNAMICSCOMPRESSOR_ATTR_RELEASE_REQD || !release.equals(releaseDefault)) {
      sb.append(" ");
      sb.append(DYNAMICSCOMPRESSOR_ATTR_RELEASE_NAME);
      sb.append("='");
      sb.append(getRelease());
      sb.append("'");
    }
    if (DYNAMICSCOMPRESSOR_ATTR_RESUMETIME_REQD || !resumeTime.equals(resumeTimeDefault)) {
      sb.append(" ");
      sb.append(DYNAMICSCOMPRESSOR_ATTR_RESUMETIME_NAME);
      sb.append("='");
      sb.append(resumeTime);
      sb.append("'");
    }

    if (DYNAMICSCOMPRESSOR_ATTR_STARTTIME_REQD || !startTime.equals(startTimeDefault)) {
      sb.append(" ");
      sb.append(DYNAMICSCOMPRESSOR_ATTR_STARTTIME_NAME);
      sb.append("='");
      sb.append(startTime);
      sb.append("'");
    }
    if (DYNAMICSCOMPRESSOR_ATTR_STOPTIME_REQD || !stopTime.equals(stopTimeDefault)) {
      sb.append(" ");
      sb.append(DYNAMICSCOMPRESSOR_ATTR_STOPTIME_NAME);
      sb.append("='");
      sb.append(stopTime);
      sb.append("'");
    }
    if (DYNAMICSCOMPRESSOR_ATTR_TAILTIME_REQD || !tailTime.equals(tailTimeDefault)) {
      sb.append(" ");
      sb.append(DYNAMICSCOMPRESSOR_ATTR_TAILTIME_NAME);
      sb.append("='");
      sb.append(getTailTime());
      sb.append("'");
    }
    if (DYNAMICSCOMPRESSOR_ATTR_THRESHOLD_REQD || !threshold.equals(thresholdDefault)) {
      sb.append(" ");
      sb.append(DYNAMICSCOMPRESSOR_ATTR_THRESHOLD_NAME);
      sb.append("='");
      sb.append(getThreshold());
      sb.append("'");
    }
    
    return sb.toString();
  }

    /**
     * @return the attack
     */
    public String getAttack() {
        return attack.toString();
    }

    /**
     * @param newAttack the attack to set
     */
    public void setAttack(String newAttack) {
        attack = new SFDouble(newAttack, null, null);
    }

    /**
     * @return the knee
     */
    public String getKnee() {
        return knee.toString();
    }

    /**
     * @param newKnee the knee to set
     */
    public void setKnee(String newKnee) {
        knee = new SFFloat(newKnee, 0.0f, null);
    }

    /**
     * @return the ratio
     */
    public String getRatio() {
        return ratio.toString();
    }

    /**
     * @param newRatio the ratio to set
     */
    public void setRatio(String newRatio) {
        ratio = new SFFloat(newRatio, 0.0f, null);
    }

    /**
     * @return the release
     */
    public String getRelease() {
        return release.toString();
    }

    /**
     * @param newRelease the release to set
     */
    public void setRelease(String newRelease) {
        release = new SFDouble(newRelease, null, null);
    }

    /**
     * @return the threshold
     */
    public String getThreshold() {
        return threshold.toString();
    }

    /**
     * @param newThreshold the threshold to set
     */
    public void setThreshold(String newThreshold) {
        threshold = new SFFloat(newThreshold, -100.0f, 0.0f);
    }

}
