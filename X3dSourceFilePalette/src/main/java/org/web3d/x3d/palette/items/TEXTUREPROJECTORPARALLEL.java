/*
Copyright (c) 1995-2026 held by the author(s).  All rights reserved.
 
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
import static org.web3d.x3d.palette.X3DPaletteUtilities.escapeXmlCharacters;
import org.web3d.x3d.types.X3DLightNode;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * TEXTUREPROJECTORPARALLEL.java
 *
 * @author Don Brutzman
 * @version $Id$
 */
public class TEXTUREPROJECTORPARALLEL extends X3DLightNode
{

  private SFFloat ambientIntensity, ambientIntensityDefault;
  private SFFloat color0,color0Default;
  private SFFloat color1,color1Default;
  private SFFloat color2,color2Default;
  private String  description;
  private SFFloat directionX,directionXDefault;
  private SFFloat directionY,directionYDefault;
  private SFFloat directionZ,directionZDefault;
  private boolean global;
  private SFFloat fieldOfView,  fieldOfViewDefault;
  private SFFloat intensity,    intensityDefault;
  private SFFloat nearDistance, nearDistanceDefault;
  private SFFloat farDistance,  farDistanceDefault;
  private SFFloat locationX,locationXDefault;
  private SFFloat locationY,locationYDefault;
  private SFFloat locationZ,locationZDefault;
  private boolean on;  
  private boolean shadows;
  private SFFloat shadowIntensity,shadowIntensityDefault;
  private SFFloat upVectorX,upVectorXDefault;
  private SFFloat upVectorY,upVectorYDefault;
  private SFFloat upVectorZ,upVectorZDefault;
  
  public TEXTUREPROJECTORPARALLEL()
  {
  }

  @Override
  public String getElementName()
  {
    return TEXTUREPROJECTORPARALLEL_ELNAME;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return TEXTUREPROJECTORPARALLELCustomizer.class;
  }
  
  @Override
  public void initialize()
  {
    ambientIntensity = ambientIntensityDefault = new SFFloat(TEXTUREPROJECTORPARALLEL_ATTR_AMBIENTINTENSITY_DFLT, 0.0f, 1.0f);
    
    String[] fa = parse3(TEXTUREPROJECTORPARALLEL_ATTR_COLOR_DFLT);
    color0 = color0Default = new SFFloat(fa[0],0.0f,1.0f);
    color1 = color1Default = new SFFloat(fa[1],0.0f,1.0f);
    color2 = color2Default = new SFFloat(fa[2],0.0f,1.0f);
    
    description  = TEXTUREPROJECTORPARALLEL_ATTR_DESCRIPTION_DFLT;
    
    fieldOfView  = fieldOfViewDefault = new SFFloat(TEXTUREPROJECTORPARALLEL_ATTR_FIELDOFVIEW_DFLT,   0.0f, 0.7854f);
    farDistance  = farDistanceDefault = new SFFloat(TEXTUREPROJECTORPARALLEL_ATTR_FARDISTANCE_DFLT,  -1.0f, 1.0f);
    intensity    = intensityDefault   = new SFFloat(TEXTUREPROJECTORPARALLEL_ATTR_INTENSITY_DFLT,     0.0f, null);
    nearDistance = farDistanceDefault = new SFFloat(TEXTUREPROJECTORPARALLEL_ATTR_NEARDISTANCE_DFLT, -1.0f, 1.0f);
    
    fa = parse3(TEXTUREPROJECTORPARALLEL_ATTR_DIRECTION_DFLT);
    directionX = directionXDefault = new SFFloat(fa[0],null,null);
    directionY = directionYDefault = new SFFloat(fa[1],null,null);
    directionZ = directionZDefault = new SFFloat(fa[2],null,null);

    on = Boolean.parseBoolean(TEXTUREPROJECTORPARALLEL_ATTR_ON_DFLT);
    global = Boolean.parseBoolean(TEXTUREPROJECTORPARALLEL_ATTR_GLOBAL_DFLT);
    
    fa = parse3(TEXTUREPROJECTORPARALLEL_ATTR_LOCATION_DFLT);
    locationX = locationXDefault = new SFFloat(fa[0],null,null);
    locationY = locationYDefault = new SFFloat(fa[1],null,null);
    locationZ = locationZDefault = new SFFloat(fa[2],null,null);
    
    shadows = Boolean.parseBoolean(TEXTUREPROJECTORPARALLEL_ATTR_SHADOWS_DFLT);
    shadowIntensity = shadowIntensityDefault = new SFFloat(TEXTUREPROJECTORPARALLEL_ATTR_SHADOWINTENSITY_DFLT, 0.0f, 1.0f);
    
    fa = parse3(TEXTUREPROJECTORPARALLEL_ATTR_UPVECTOR_DFLT);
    upVectorX = upVectorXDefault = new SFFloat(fa[0],null,null);
    upVectorY = upVectorYDefault = new SFFloat(fa[1],null,null);
    upVectorZ = upVectorZDefault = new SFFloat(fa[2],null,null);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(TEXTUREPROJECTORPARALLEL_ATTR_AMBIENTINTENSITY_NAME);
    if (attr != null)
      ambientIntensity = new SFFloat(attr.getValue(), 0.0f, 1.0f);
    String[] fa;
    attr = root.getAttribute(TEXTUREPROJECTORPARALLEL_ATTR_COLOR_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      color0 = new SFFloat(fa[0], 0.0f, 1.0f);
      color1 = new SFFloat(fa[1], 0.0f, 1.0f);
      color2 = new SFFloat(fa[2], 0.0f, 1.0f);
    }
    attr = root.getAttribute(TEXTUREPROJECTORPARALLEL_ATTR_DESCRIPTION_NAME);
    if (attr != null)
        setDescription(attr.getValue());
    attr = root.getAttribute(TEXTUREPROJECTORPARALLEL_ATTR_DIRECTION_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      directionX = new SFFloat(fa[0], null, null);
      directionY = new SFFloat(fa[1], null, null);
      directionZ = new SFFloat(fa[2], null, null);
    }
    attr = root.getAttribute(TEXTUREPROJECTORPARALLEL_ATTR_FARDISTANCE_NAME);
    if (attr != null)
      farDistance = new SFFloat(attr.getValue(), -1.0f, null);
    attr = root.getAttribute(TEXTUREPROJECTORPARALLEL_ATTR_FIELDOFVIEW_NAME);
    if (attr != null)
      fieldOfView = new SFFloat(attr.getValue(), 0.0f, 0.7854f);
    attr = root.getAttribute(TEXTUREPROJECTORPARALLEL_ATTR_GLOBAL_NAME);
    if (attr != null)
      global = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(TEXTUREPROJECTORPARALLEL_ATTR_INTENSITY_NAME);
    if (attr != null)
      intensity = new SFFloat(attr.getValue(), 0.0f, null);
    
    attr = root.getAttribute(TEXTUREPROJECTORPARALLEL_ATTR_LOCATION_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      locationX = new SFFloat(fa[0], null, null);
      locationY = new SFFloat(fa[1], null, null);
      locationZ = new SFFloat(fa[2], null, null);
    }
    attr = root.getAttribute(TEXTUREPROJECTORPARALLEL_ATTR_NEARDISTANCE_NAME);
    if (attr != null)
      nearDistance = new SFFloat(attr.getValue(), -1.0f, null);
    attr = root.getAttribute(TEXTUREPROJECTORPARALLEL_ATTR_ON_NAME);
    if (attr != null)
      on = Boolean.parseBoolean(attr.getValue());
    
    attr = root.getAttribute(TEXTUREPROJECTORPARALLEL_ATTR_SHADOWS_NAME);
    if (attr != null)
      shadows = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(TEXTUREPROJECTORPARALLEL_ATTR_SHADOWINTENSITY_NAME);
    if (attr != null)
      shadowIntensity = new SFFloat(attr.getValue(), 0.0f, 1.0f);
    
    attr = root.getAttribute(TEXTUREPROJECTORPARALLEL_ATTR_UPVECTOR_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      upVectorX = new SFFloat(fa[0], null, null);
      upVectorY = new SFFloat(fa[1], null, null);
      upVectorZ = new SFFloat(fa[2], null, null);
    }
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    if (TEXTUREPROJECTORPARALLEL_ATTR_AMBIENTINTENSITY_REQD || !ambientIntensity.equals(ambientIntensityDefault)) {
      sb.append(" ");
      sb.append(TEXTUREPROJECTORPARALLEL_ATTR_AMBIENTINTENSITY_NAME);
      sb.append("='");
      sb.append(ambientIntensity);
      sb.append("'");
    }
    if (TEXTUREPROJECTORPARALLEL_ATTR_COLOR_REQD ||
           (!color0.equals(color0Default) ||
            !color1.equals(color1Default) ||
            !color2.equals(color2Default)))
    {
      sb.append(" ");
      sb.append(TEXTUREPROJECTORPARALLEL_ATTR_COLOR_NAME);
      sb.append("='");
      sb.append(color0);
      sb.append(" ");
      sb.append(color1);
      sb.append(" ");
      sb.append(color2);
      sb.append("'");
    }
    if (TOUCHSENSOR_ATTR_DESCRIPTION_REQD || !description.equals(TOUCHSENSOR_ATTR_DESCRIPTION_DFLT)) {
      sb.append(" ");
      sb.append(TOUCHSENSOR_ATTR_DESCRIPTION_NAME);
      sb.append("='");
      sb.append(escapeXmlCharacters(getDescription()));
      sb.append("'");
    }
    if (TEXTUREPROJECTORPARALLEL_ATTR_DIRECTION_REQD ||
           (!directionX.equals(directionXDefault) ||
            !directionY.equals(directionYDefault) ||
            !directionZ.equals(directionZDefault)))
    {
      sb.append(" ");
      sb.append(TEXTUREPROJECTORPARALLEL_ATTR_DIRECTION_NAME);
      sb.append("='");
      sb.append(directionX);
      sb.append(" ");
      sb.append(directionY);
      sb.append(" ");
      sb.append(directionZ);
      sb.append("'");
    }
    if (TEXTUREPROJECTORPARALLEL_ATTR_FARDISTANCE_REQD || !farDistance.equals(farDistanceDefault)) {
      sb.append(" ");
      sb.append(TEXTUREPROJECTORPARALLEL_ATTR_FARDISTANCE_NAME);
      sb.append("='");
      sb.append(farDistance);
      sb.append("'");
    }
    if (TEXTUREPROJECTORPARALLEL_ATTR_FIELDOFVIEW_REQD || !fieldOfView.equals(fieldOfViewDefault)) {
      sb.append(" ");
      sb.append(TEXTUREPROJECTORPARALLEL_ATTR_FIELDOFVIEW_NAME);
      sb.append("='");
      sb.append(fieldOfView);
      sb.append("'");
    }
    if (TEXTUREPROJECTORPARALLEL_ATTR_GLOBAL_REQD || global != Boolean.parseBoolean(TEXTUREPROJECTORPARALLEL_ATTR_GLOBAL_DFLT)) {
      sb.append(" ");
      sb.append(TEXTUREPROJECTORPARALLEL_ATTR_GLOBAL_NAME);
      sb.append("='");
      sb.append(global);
      sb.append("'");
    }
    if (TEXTUREPROJECTORPARALLEL_ATTR_INTENSITY_REQD || !intensity.equals(intensityDefault)) {
      sb.append(" ");
      sb.append(TEXTUREPROJECTORPARALLEL_ATTR_INTENSITY_NAME);
      sb.append("='");
      sb.append(intensity);
      sb.append("'");
    }
    if(TEXTUREPROJECTORPARALLEL_ATTR_LOCATION_REQD || 
           (!locationX.equals(locationXDefault) ||
            !locationY.equals(locationYDefault) ||
            !locationZ.equals(locationZDefault)) ) {
      sb.append(" ");
      sb.append(TEXTUREPROJECTORPARALLEL_ATTR_LOCATION_NAME);
      sb.append("='");
      sb.append(getLocationX());
      sb.append(" ");
      sb.append(getLocationY());
      sb.append(" ");
      sb.append(getLocationZ());
      sb.append("'");       
    }
    if (TEXTUREPROJECTORPARALLEL_ATTR_NEARDISTANCE_REQD || !farDistance.equals(nearDistanceDefault)) {
      sb.append(" ");
      sb.append(TEXTUREPROJECTORPARALLEL_ATTR_NEARDISTANCE_NAME);
      sb.append("='");
      sb.append(nearDistance);
      sb.append("'");
    }
    if (TEXTUREPROJECTORPARALLEL_ATTR_ON_REQD || on != Boolean.parseBoolean(TEXTUREPROJECTORPARALLEL_ATTR_ON_DFLT)) {
      sb.append(" ");
      sb.append(TEXTUREPROJECTORPARALLEL_ATTR_ON_NAME);
      sb.append("='");
      sb.append(on);
      sb.append("'");
    }
    if(TEXTUREPROJECTORPARALLEL_ATTR_SHADOWS_REQD || shadows != Boolean.parseBoolean(TEXTUREPROJECTORPARALLEL_ATTR_SHADOWS_DFLT) ) {
      sb.append(" ");
      sb.append(TEXTUREPROJECTORPARALLEL_ATTR_SHADOWS_NAME);
      sb.append("='");
      sb.append(shadows);
      sb.append("'");       
    }
    if(TEXTUREPROJECTORPARALLEL_ATTR_SHADOWINTENSITY_REQD || !shadowIntensity.equals(shadowIntensityDefault)) {
      sb.append(" ");
      sb.append(TEXTUREPROJECTORPARALLEL_ATTR_SHADOWINTENSITY_NAME);
      sb.append("='");
      sb.append(shadowIntensity);
      sb.append("'");
    }     
    if(TEXTUREPROJECTORPARALLEL_ATTR_UPVECTOR_REQD || 
           (!upVectorX.equals(upVectorXDefault) ||
            !upVectorY.equals(upVectorYDefault) ||
            !upVectorZ.equals(upVectorZDefault)) ) {
      sb.append(" ");
      sb.append(TEXTUREPROJECTORPARALLEL_ATTR_UPVECTOR_NAME);
      sb.append("='");
      sb.append(getUpVectorX());
      sb.append(" ");
      sb.append(getUpVectorY());
      sb.append(" ");
      sb.append(getUpVectorZ());
      sb.append("'");       
    }     
    return sb.toString();
  }
    
 public String getAmbientIntensity()
  {
    return ambientIntensity.toString();
  }
  
  public void setAmbientIntensity(String ambientIntensity)
  {
    this.ambientIntensity = new SFFloat(ambientIntensity,0.0f,1.0f);
  }

   public String getColor0()
  {
    return color0.toString();
  }

  public void setColor0(String color0)
  {
    this.color0 = new SFFloat(color0,0.0f,1.0f);
  }

  public String getColor1()
  {
    return color1.toString();
  }

  public void setColor1(String color1)
  {
    this.color1 = new SFFloat(color1,0.0f,1.0f);
  }

  public String getColor2()
  {
    return color2.toString();
  }

  public void setColor2(String color2)
  {
    this.color2 = new SFFloat(color2,0.0f,1.0f);
  }

  public boolean isGlobal() {
    return global;
  }

  public void setGlobal(boolean global) {
    this.global = global;
  }

  public String getIntensity()
  {
    return intensity.toString();
  }
  
  public void setIntensity(String intensity)
  {
    this.intensity = new SFFloat(intensity,0.0f,null);
  }
 
  public String getDirectionX() 
  {
    return directionX.toString();
  }

  public void setDirectionX(String directionX) 
  {
    this.directionX = new SFFloat(directionX,null,null);
  }

  public String getDirectionY()
  {
    return directionY.toString();
  }

  public void setDirectionY(String directionY)
  {
    this.directionY = new SFFloat(directionY,null,null);
  }

  public String getDirectionZ()
  {
    return directionZ.toString();
  }

  public void setDirectionZ(String directionZ)
  {
    this.directionZ = new SFFloat(directionZ,null,null);
  }

  public boolean isOn() {
    return on;
  }

  public void setOn(boolean on) {
    this.on = on;
  }

  public boolean isShadows()
  {
    return shadows;
  }

  public void setShadows(boolean shadows)
  {
    this.shadows = shadows;
  }
   
  public String getShadowIntensity()
  {
    return shadowIntensity.toString();
  }

  public void setShadowIntensity(String shadowIntensity)
  {
    this.shadowIntensity = new SFFloat(shadowIntensity,0.0f,1.0f);
  }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the locationX
     */
    public SFFloat getLocationX() {
        return locationX;
    }

    /**
     * @param locationX the locationX to set
     */
    public void setLocationX(SFFloat locationX) {
        this.locationX = locationX;
    }

    /**
     * @return the locationY
     */
    public SFFloat getLocationY() {
        return locationY;
    }

    /**
     * @param locationY the locationY to set
     */
    public void setLocationY(SFFloat locationY) {
        this.locationY = locationY;
    }

    /**
     * @return the locationZ
     */
    public SFFloat getLocationZ() {
        return locationZ;
    }

    /**
     * @param locationZ the locationZ to set
     */
    public void setLocationZ(SFFloat locationZ) {
        this.locationZ = locationZ;
    }
    
    /**
     * @return the upVectorX
     */
    public SFFloat getUpVectorX() {
        return upVectorX;
    }

    /**
     * @param upVectorX the upVectorX to set
     */
    public void setUpVectorX(SFFloat upVectorX) {
        this.upVectorX = upVectorX;
    }

    /**
     * @return the upVectorY
     */
    public SFFloat getUpVectorY() {
        return upVectorY;
    }

    /**
     * @param upVectorY the upVectorY to set
     */
    public void setUpVectorY(SFFloat upVectorY) {
        this.upVectorY = upVectorY;
    }

    /**
     * @return the upVectorZ
     */
    public SFFloat getUpVectorZ() {
        return upVectorZ;
    }

    /**
     * @param upVectorZ the upVectorZ to set
     */
    public void setUpVectorZ(SFFloat upVectorZ) {
        this.upVectorZ = upVectorZ;
    }

    /**
     * @return the fieldOfView
     */
    public SFFloat getFieldOfView() {
        return fieldOfView;
    }

    /**
     * @param fieldOfView the fieldOfView to set
     */
    public void setFieldOfView(SFFloat fieldOfView) {
        this.fieldOfView = fieldOfView;
    }

    /**
     * @return the farDistance
     */
    public SFFloat getFarDistance() {
        return farDistance;
    }

    /**
     * @param farDistance the farDistance to set
     */
    public void setFarDistance(SFFloat farDistance) {
        this.farDistance = farDistance;
    }

    /**
     * @return the nearDistance
     */
    public SFFloat getNearDistance() {
        return nearDistance;
    }

    /**
     * @param nearDistance the nearDistance to set
     */
    public void setNearDistance(SFFloat nearDistance) {
        this.nearDistance = nearDistance;
    }

}
