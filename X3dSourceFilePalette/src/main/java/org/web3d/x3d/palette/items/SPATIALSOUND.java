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
import org.web3d.x3d.types.X3DPrimitiveTypes.SFFloat;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.parse3;
import org.web3d.x3d.types.X3DSoundNode;

/**
 * SPATIALSOUND:
 * SpatialSound represents a processing node which positions, emits and 
 * spatializes an audio stream in three-dimensional (3D) space. 
 * This node provides full spatialization of panner capabilities defined by 
 * W3C Web Audio API within an X3D scene.
 *
 * @author Don Brutzman
 * @version $Id$
 */
public class SPATIALSOUND extends X3DSoundNode
{
    private SFFloat coneInnerAngle, coneInnerAngleDefault;
    private SFFloat coneOuterAngle, coneOuterAngleDefault;
    private SFFloat coneOuterGain,  coneOuterGainDefault;
    private SFFloat direction0,     direction0Default;
    private SFFloat direction1,     direction1Default;
    private SFFloat direction2,     direction2Default;
    private String  distanceModel,  distanceModelDefault;
    private boolean dopplerEnabled;
    private boolean enableHRTF;
    private SFFloat gain,      gainDefault;
    private SFFloat intensity, intensityDefault;
    
    private SFFloat location0, location0Default;
    private SFFloat location1, location1Default;
    private SFFloat location2, location2Default;
    
    private SFFloat maxDistance,       maxDistanceDefault;
    private SFFloat priority,          priorityDefault;
    private SFFloat referenceDistance, referenceDistanceDefault;
    private SFFloat rolloffFactor,     rolloffFactorDefault;
    
    private boolean   spatialize;
    
  public SPATIALSOUND() 
  {
      this.setTraceEventsSelectionAvailable(false);
      this.setTraceEventsTooltip("Trace MicrophoneSource events on X3D browser console");
  }
  
  @Override
  public String getElementName()
  {
    return SPATIALSOUND_ELNAME;
  }
  
  @Override
  public void initialize()
  {    
    description = descriptionDefault       = SPATIALSOUND_ATTR_DESCRIPTION_DFLT;
    enabled                                = Boolean.parseBoolean(SPATIALSOUND_ATTR_ENABLED_DFLT);
    
    coneInnerAngle = coneInnerAngleDefault = new SFFloat(SPATIALSOUND_ATTR_CONEINNERANGLE_DFLT, 0.0f, 6.2832f);
    coneOuterAngle = coneOuterAngleDefault = new SFFloat(SPATIALSOUND_ATTR_CONEOUTERANGLE_DFLT, 0.0f, 6.2832f);
    coneOuterGain  = coneOuterGainDefault  = new SFFloat(SPATIALSOUND_ATTR_CONEOUTERGAIN_DFLT, null, null); // interestingly, negative is possible
    distanceModel  = distanceModelDefault  = SPATIALSOUND_ATTR_DISTANCEMODEL_DFLT;
    
    String[] sa = parse3(SPATIALSOUND_ATTR_DIRECTION_DFLT);
    direction0 = direction0Default = new SFFloat(sa[0],null,null);
    direction1 = direction1Default = new SFFloat(sa[1],null,null);
    direction2 = direction2Default = new SFFloat(sa[2],null,null);
    
    sa = parse3(SPATIALSOUND_ATTR_LOCATION_DFLT);
    location0 = location0Default = new SFFloat(sa[0],null,null);
    location1 = location1Default = new SFFloat(sa[1],null,null);
    location2 = location2Default = new SFFloat(sa[2],null,null);
    
    gain       = gainDefault       = new SFFloat(SPATIALSOUND_ATTR_GAIN_DFLT, null, null);
    intensity  = intensityDefault  = new SFFloat(SPATIALSOUND_ATTR_INTENSITY_DFLT, 0.0f, 1.0f);
    
    dopplerEnabled = Boolean.parseBoolean(SPATIALSOUND_ATTR_DOPPLERENABLED_DFLT);
    enableHRTF     = Boolean.parseBoolean(SPATIALSOUND_ATTR_ENABLEHRTF_DFLT);
    spatialize     = Boolean.parseBoolean(SPATIALSOUND_ATTR_SPATIALIZE_DFLT);
    
    maxDistance       = maxDistanceDefault       = new SFFloat(SPATIALSOUND_ATTR_MAXDISTANCE_DFLT, 0.0f, null);
    priority          = priorityDefault          = new SFFloat(SPATIALSOUND_ATTR_PRIORITY_DFLT, 0.0f, 1.0f);
    referenceDistance = referenceDistanceDefault = new SFFloat(SPATIALSOUND_ATTR_REFERENCEDISTANCE_DFLT, 0.0f, null);
    rolloffFactor     = rolloffFactorDefault     = new SFFloat(SPATIALSOUND_ATTR_ROLLOFFFACTOR_DFLT, 0.0f, null);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    String[] sa;
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(SPATIALSOUND_ATTR_DESCRIPTION_NAME);
    if (attr != null)
      description = attr.getValue();
    attr = root.getAttribute(SPATIALSOUND_ATTR_ENABLED_NAME);
    if (attr != null)
      enabled = Boolean.parseBoolean(attr.getValue());
    
    attr = root.getAttribute(SPATIALSOUND_ATTR_CONEINNERANGLE_NAME);
    if (attr != null) 
        coneInnerAngle = new SFFloat(attr.getValue(), 0.0f, 6.2832f); // restrictive bounds
    attr = root.getAttribute(SPATIALSOUND_ATTR_CONEOUTERANGLE_NAME);
    if (attr != null) 
        coneOuterAngle = new SFFloat(attr.getValue(), 0.0f, 6.2832f); // restrictive bounds
    attr = root.getAttribute(SPATIALSOUND_ATTR_CONEOUTERGAIN_NAME);
    if (attr != null) 
        coneOuterGain = new SFFloat(attr.getValue(), null, null);
    
    attr = root.getAttribute(SPATIALSOUND_ATTR_DIRECTION_NAME);
    if (attr != null) {
      sa = parse3(attr.getValue());
      direction0 = new SFFloat(sa[0], null, null);
      direction1 = new SFFloat(sa[1], null, null);
      direction2 = new SFFloat(sa[2], null, null);
    }
    attr = root.getAttribute(SPATIALSOUND_ATTR_LOCATION_NAME);
    if (attr != null) {
      sa = parse3(attr.getValue());
      location0 = new SFFloat(sa[0], null, null);
      location1 = new SFFloat(sa[1], null, null);
      location2 = new SFFloat(sa[2], null, null);
    }
    
    attr = root.getAttribute(SPATIALSOUND_ATTR_DISTANCEMODEL_NAME);
    if (attr != null)
        distanceModel = attr.getValue();
    attr = root.getAttribute(SPATIALSOUND_ATTR_DOPPLERENABLED_NAME);
    if (attr != null)
      dopplerEnabled = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(SPATIALSOUND_ATTR_ENABLED_NAME);
    if (attr != null)
      enabled = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(SPATIALSOUND_ATTR_ENABLEHRTF_NAME);
    if (attr != null)
      enableHRTF = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(SPATIALSOUND_ATTR_GAIN_NAME);
    if (attr != null) 
        gain = new SFFloat(attr.getValue(), null, null);
    attr = root.getAttribute(SPATIALSOUND_ATTR_INTENSITY_NAME);
    if (attr != null)
      intensity = new SFFloat(attr.getValue(), 0.0f, 1.0f);
    
    attr = root.getAttribute(SPATIALSOUND_ATTR_MAXDISTANCE_NAME);
    if (attr != null)
      maxDistance = new SFFloat(attr.getValue(), 0.0f, null);
    attr = root.getAttribute(SPATIALSOUND_ATTR_PRIORITY_NAME);
    if (attr != null)
      priority = new SFFloat(attr.getValue(), 0.0f, null);
    attr = root.getAttribute(SPATIALSOUND_ATTR_REFERENCEDISTANCE_NAME);
    if (attr != null)
      referenceDistance = new SFFloat(attr.getValue(), 0.0f, null);
    attr = root.getAttribute(SPATIALSOUND_ATTR_ROLLOFFFACTOR_NAME);
    if (attr != null)
      rolloffFactor = new SFFloat(attr.getValue(), 0.0f, null);
    attr = root.getAttribute(SPATIALSOUND_ATTR_SPATIALIZE_NAME);
    if (attr != null)
      spatialize = Boolean.parseBoolean(attr.getValue());
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return SPATIALSOUNDCustomizer.class;
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
      
    if (SPATIALSOUND_ATTR_CONEINNERANGLE_REQD || !coneInnerAngle.equals(coneInnerAngleDefault)) {
      sb.append(" ");
      sb.append(SPATIALSOUND_ATTR_CONEINNERANGLE_NAME);
      sb.append("='");
      sb.append(coneInnerAngle);
      sb.append("'");
    }
    if (SPATIALSOUND_ATTR_CONEOUTERANGLE_REQD || !coneOuterAngle.equals(coneOuterAngleDefault)) {
      sb.append(" ");
      sb.append(SPATIALSOUND_ATTR_CONEOUTERANGLE_NAME);
      sb.append("='");
      sb.append(coneOuterAngle);
      sb.append("'");
    }
    if (SPATIALSOUND_ATTR_CONEOUTERGAIN_REQD || !coneOuterGain.equals(coneOuterGainDefault)) {
      sb.append(" ");
      sb.append(SPATIALSOUND_ATTR_CONEOUTERGAIN_NAME);
      sb.append("='");
      sb.append(coneOuterGain);
      sb.append("'");
    }
    
    if (SPATIALSOUND_ATTR_DESCRIPTION_REQD || !description.equals(descriptionDefault)) {
      sb.append(" ");
      sb.append(SPATIALSOUND_ATTR_DESCRIPTION_NAME);
      sb.append("='");
      sb.append(description);
      sb.append("'");
    }
    if (SPATIALSOUND_ATTR_DIRECTION_REQD ||
            (!direction0.equals(direction0Default)) ||
            (!direction1.equals(direction1Default)) ||
            (!direction2.equals(direction2Default)))
    {
      sb.append(" ");
      sb.append(SPATIALSOUND_ATTR_DIRECTION_NAME);
      sb.append("='");
      sb.append(direction0);
      sb.append(" ");
      sb.append(direction1);
      sb.append(" ");
      sb.append(direction2);
      sb.append("'");
    }
    
    if (SPATIALSOUND_ATTR_DISTANCEMODEL_REQD || !distanceModel.equals(distanceModelDefault)) {
      sb.append(" ");
      sb.append(SPATIALSOUND_ATTR_DISTANCEMODEL_NAME);
      sb.append("='");
      sb.append(getDistanceModel());
      sb.append("'");
    }

    if (SPATIALSOUND_ATTR_DOPPLERENABLED_REQD || (dopplerEnabled != Boolean.parseBoolean(SPATIALSOUND_ATTR_DOPPLERENABLED_DFLT))) {
      sb.append(" ");
      sb.append(SPATIALSOUND_ATTR_DOPPLERENABLED_NAME);
      sb.append("='");
      sb.append(dopplerEnabled);
      sb.append("'");
    }
    if (SPATIALSOUND_ATTR_ENABLED_REQD || enabled != Boolean.parseBoolean(SPATIALSOUND_ATTR_ENABLED_DFLT)) {
      sb.append(" ");
      sb.append(SPATIALSOUND_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }
    if (SPATIALSOUND_ATTR_ENABLEHRTF_REQD || enableHRTF != Boolean.parseBoolean(SPATIALSOUND_ATTR_ENABLEHRTF_DFLT)) {
      sb.append(" ");
      sb.append(SPATIALSOUND_ATTR_ENABLEHRTF_NAME);
      sb.append("='");
      sb.append(enableHRTF);
      sb.append("'");
    }
    if (SPATIALSOUND_ATTR_GAIN_REQD || !gain.equals(gainDefault)) {
      sb.append(" ");
      sb.append(SPATIALSOUND_ATTR_GAIN_NAME);
      sb.append("='");
      sb.append(gain);
      sb.append("'");
    }
    if (SPATIALSOUND_ATTR_INTENSITY_REQD || !intensity.equals(intensityDefault)) {
      sb.append(" ");
      sb.append(SPATIALSOUND_ATTR_INTENSITY_NAME);
      sb.append("='");
      sb.append(intensity);
      sb.append("'");
    }
    if (SPATIALSOUND_ATTR_LOCATION_REQD ||
            (!location0.equals(location0Default)) ||
            (!location1.equals(location1Default)) ||
            (!location2.equals(location2Default)))
    {
      sb.append(" ");
      sb.append(SPATIALSOUND_ATTR_LOCATION_NAME);
      sb.append("='");
      sb.append(location0);
      sb.append(" ");
      sb.append(location1);
      sb.append(" ");
      sb.append(location2);
      sb.append("'");
    }
    if (SPATIALSOUND_ATTR_MAXDISTANCE_REQD || !maxDistance.equals(maxDistanceDefault)) {
      sb.append(" ");
      sb.append(SPATIALSOUND_ATTR_MAXDISTANCE_NAME);
      sb.append("='");
      sb.append(maxDistance);
      sb.append("'");
    }
    if (SPATIALSOUND_ATTR_PRIORITY_REQD || !priority.equals(priorityDefault)) {
      sb.append(" ");
      sb.append(SPATIALSOUND_ATTR_PRIORITY_NAME);
      sb.append("='");
      sb.append(priority);
      sb.append("'");
    }
    if (SPATIALSOUND_ATTR_REFERENCEDISTANCE_REQD || !referenceDistance.equals(referenceDistanceDefault)) {
      sb.append(" ");
      sb.append(SPATIALSOUND_ATTR_REFERENCEDISTANCE_NAME);
      sb.append("='");
      sb.append(referenceDistance);
      sb.append("'");
    }
    if (SPATIALSOUND_ATTR_ROLLOFFFACTOR_REQD || !rolloffFactor.equals(rolloffFactorDefault)) {
      sb.append(" ");
      sb.append(SPATIALSOUND_ATTR_ROLLOFFFACTOR_NAME);
      sb.append("='");
      sb.append(rolloffFactor);
      sb.append("'");
    }
    if (SPATIALSOUND_ATTR_SPATIALIZE_REQD || spatialize != Boolean.parseBoolean(SPATIALSOUND_ATTR_SPATIALIZE_DFLT)) {
      sb.append(" ");
      sb.append(SPATIALSOUND_ATTR_SPATIALIZE_NAME);
      sb.append("='");
      sb.append(spatialize);
      sb.append("'");
    }
    
    return sb.toString();
  }

    /**
     * @param newConeInnerAngle the coneInnerAngle to set
     */
    public void setConeInnerAngle(String newConeInnerAngle) {
        coneInnerAngle = new SFFloat(newConeInnerAngle, 0.0f, 6.2832f); // restrictive bounds
    }
    /**
     * @return the coneInnerAngle
     */
    public String getConeInnerAngle() {
        return coneInnerAngle.toString();
    }
    /**
     * @param newConeOuterAngle the coneOuterAngle to set
     */
    public void setConeOuterAngle(String newConeOuterAngle) {
        coneOuterAngle = new SFFloat(newConeOuterAngle, 0.0f, 6.2832f); // restrictive boundsl
    }
    /**
     * @return the coneOuterAngle
     */
    public String getConeOuterAngle() {
        return coneOuterAngle.toString();
    }
    /**
     * @param newConeOuterGain the coneOuterGain to set
     */
    public void setConeOuterGain(String newConeOuterGain) {
        coneOuterGain = new SFFloat(newConeOuterGain, null, null);
    }
    /**
     * @return the coneOuterGain
     */
    public String getConeOuterGain() {
        return coneOuterGain.toString();
    }

  public String getDirectionX()
  {
    return direction0.toString();
  }

  public void setDirectionX(String direction0)
  {
    this.direction0 = new SFFloat(direction0,null,null);
  }

  public String getDirectionY()
  {
    return direction1.toString();
  }

  public void setDirectionY(String direction1)
  {
    this.direction1 = new SFFloat(direction1,null,null);
  }

  public String getDirectionZ()
  {
    return direction2.toString();
  }

  public void setDirectionZ(String direction2)
  {
    this.direction2 = new SFFloat(direction2,null,null);
  }

  public String getLocationX()
  {
    return location0.toString();
  }

  public void setLocationX(String location0)
  {
    this.location0 = new SFFloat(location0,null,null);
  }

  public String getLocationY()
  {
    return location1.toString();
  }

  public void setLocationY(String location1)
  {
    this.location1 = new SFFloat(location1,null,null);
  }

  public String getLocationZ()
  {
    return location2.toString();
  }

  public void setLocationZ(String location2)
  {
    this.location2 = new SFFloat(location2,null,null);
  }

    /**
     * @return the distanceModel
     */
    public String getDistanceModel() {
        return distanceModel;
    }

    /**
     * @param newDistanceModel the distanceModel to set
     */
    public void setDistanceModel(String newDistanceModel) {
        distanceModel = newDistanceModel;
    }

  public boolean isDopplerEnabled()
  {
    return dopplerEnabled;
  }

  public void setDopplerEnabled(boolean newDopplerEnabled)
  {
    dopplerEnabled = newDopplerEnabled;
  }

  public boolean isEnableHRTF()
  {
    return enableHRTF;
  }

  public void setEnableHRTF(boolean newEnableHRTF)
  {
    enableHRTF = newEnableHRTF;
  }

  public String getIntensity()
  {
    return intensity.toString();
  }

  public void setIntensity(String intensity)
  {
    this.intensity = new SFFloat(intensity,0.0f,1.0f);
  }

  public String getMaxDistance()
  {
    return maxDistance.toString();
  }

  public void setMaxDistance(String newMaxDistance)
  {
    maxDistance = new SFFloat(newMaxDistance,0.0f, null);
  }

  public String getGain()
  {
    return gain.toString();
  }

  public void setGain(String newGain)
  {
    gain = new SFFloat(newGain, null, null);
  }

  public String getPriority()
  {
    return priority.toString();
  }

  public void setPriority(String newPriority)
  {
    priority = new SFFloat(newPriority,0.0f, 1.0f);
  }

  public String getReferenceDistance()
  {
    return referenceDistance.toString();
  }

  public void setReferenceDistance(String newReferenceDistance)
  {
    referenceDistance = new SFFloat(newReferenceDistance,0.0f, null);
  }

  public String getRolloffFactor()
  {
    return rolloffFactor.toString();
  }

  public void setRolloffFactor(String newRolloffFactor)
  {
    rolloffFactor = new SFFloat(newRolloffFactor,0.0f, null);
  }

  public boolean isSpatialize()
  {
    return spatialize;
  }

  public void setSpatialize(boolean spatialize)
  {
    this.spatialize = spatialize;
  }

}
