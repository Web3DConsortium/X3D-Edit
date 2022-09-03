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
import org.web3d.x3d.types.X3DLightNode;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * SpotLight.java
 * Created on July 11, 2007, 5:13 PM
 * 
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 * 
 * 
 * @author Mike Bailey
 * @version $Id$
 */
public class SPOTLIGHT extends X3DLightNode
{
  private SFFloat ambientIntensity, ambientIntensityDefault;
  private SFFloat attenuationX, attenuationXDefault;
  private SFFloat attenuationY, attenuationYDefault;
  private SFFloat attenuationZ, attenuationZDefault;
  private SFFloat color0,color0Default;
  private SFFloat color1,color1Default;
  private SFFloat color2,color2Default;
  private boolean global;
  private SFFloat intensity, intensityDefault;
  private SFFloat locationX,locationXDefault;
  private SFFloat locationY,locationYDefault;
  private SFFloat locationZ,locationZDefault;
  private boolean on;
  private SFFloat radius,radiusDefault;
  
  private SFFloat beamWidth, beamWidthDefault;
  private SFFloat cutOffAngle, cutOffAngleDefault;
  private SFFloat directionX,directionXDefault;
  private SFFloat directionY,directionYDefault;
  private SFFloat directionZ,directionZDefault;
    
  public SPOTLIGHT()
  {
  }

  @Override
  public String getElementName()
  {
    return SPOTLIGHT_ELNAME;
  }

  @Override
  public void initialize()
  {
    String[] fa = parse3(SPOTLIGHT_ATTR_ATTENUATION_DFLT);
    attenuationX = attenuationXDefault = new SFFloat(fa[0],0.0f,null);
    attenuationY = attenuationYDefault = new SFFloat(fa[1],0.0f,null);    
    attenuationZ = attenuationZDefault = new SFFloat(fa[2],0.0f,null);
    
    fa = parse3(SPOTLIGHT_ATTR_COLOR_DFLT);
    color0 = color0Default = new SFFloat(fa[0],0.0f,1.0f);
    color1 = color1Default = new SFFloat(fa[1],0.0f,1.0f);
    color2 = color2Default = new SFFloat(fa[2],0.0f,1.0f);
        
    fa = parse3(SPOTLIGHT_ATTR_LOCATION_DFLT);
    locationX = locationXDefault = new SFFloat(fa[0],null,null);
    locationY = locationYDefault = new SFFloat(fa[1],null,null);
    locationZ = locationZDefault = new SFFloat(fa[2],null,null);

    fa = parse3(SPOTLIGHT_ATTR_DIRECTION_DFLT);
    directionX = directionXDefault = new SFFloat(fa[0],null,null);
    directionY = directionYDefault = new SFFloat(fa[1],null,null);
    directionZ = directionZDefault = new SFFloat(fa[2],null,null);
    
    on     = Boolean.parseBoolean(SPOTLIGHT_ATTR_ON_DFLT);
    global = Boolean.parseBoolean(SPOTLIGHT_ATTR_GLOBAL_DFLT);
    
    ambientIntensity = ambientIntensityDefault = new SFFloat(SPOTLIGHT_ATTR_AMBIENTINTENSITY_DFLT, 0.0f, 1.0f);
    intensity        = intensityDefault        = new SFFloat(SPOTLIGHT_ATTR_INTENSITY_DFLT       , 0.0f, 1.0f);
    radius           = radiusDefault           = new SFFloat(SPOTLIGHT_ATTR_RADIUS_DFLT          , 0.0f, null);
    cutOffAngle      = cutOffAngleDefault      = new SFFloat(SPOTLIGHT_ATTR_CUTOFFANGLE_DFLT     , 0.0f,Float.parseFloat(SPOTLIGHT_ATTR_CUTOFFANGLE_MAX));
    beamWidth        = beamWidthDefault        = new SFFloat(SPOTLIGHT_ATTR_BEAMWIDTH_DFLT       , 0.0f,Float.parseFloat(SPOTLIGHT_ATTR_BEAMWIDTH_MAX));
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;
    String[] fa;

    attr = root.getAttribute(SPOTLIGHT_ATTR_ATTENUATION_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      attenuationX = new SFFloat(fa[0], 0.0f, null);
      attenuationY = new SFFloat(fa[1], 0.0f, null);
      attenuationZ = new SFFloat(fa[2], 0.0f, null);
    }
    attr = root.getAttribute(SPOTLIGHT_ATTR_COLOR_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      color0 = new SFFloat(fa[0], 0.0f, 1.0f);
      color1 = new SFFloat(fa[1], 0.0f, 1.0f);
      color2 = new SFFloat(fa[2], 0.0f, 1.0f);
    }
    attr = root.getAttribute(SPOTLIGHT_ATTR_LOCATION_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      locationX = new SFFloat(fa[0], null, null);
      locationY = new SFFloat(fa[1], null, null);
      locationZ = new SFFloat(fa[2], null, null);
    }
    attr = root.getAttribute(SPOTLIGHT_ATTR_DIRECTION_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      directionX = new SFFloat(fa[0], null, null);
      directionY = new SFFloat(fa[1], null, null);
      directionZ = new SFFloat(fa[2], null, null);
    }
    attr = root.getAttribute(SPOTLIGHT_ATTR_ON_NAME);
    if (attr != null)
      on = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(SPOTLIGHT_ATTR_GLOBAL_NAME);
    if (attr != null)
      global = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(SPOTLIGHT_ATTR_AMBIENTINTENSITY_NAME);
    if (attr != null)
      ambientIntensity = new SFFloat(attr.getValue(), 0.0f, 1.0f);
    attr = root.getAttribute(SPOTLIGHT_ATTR_INTENSITY_NAME);
    if (attr != null)
      intensity = new SFFloat(attr.getValue(), 0.0f, 1.0f);
    attr = root.getAttribute(SPOTLIGHT_ATTR_RADIUS_NAME);
    if (attr != null)
      radius = new SFFloat(attr.getValue(), 0.0f, null);
    attr = root.getAttribute(SPOTLIGHT_ATTR_CUTOFFANGLE_NAME);
    if (attr != null)
      cutOffAngle = new SFFloat(attr.getValue(), 0.0f, cutOffAngleDefault.getValue());
    attr = root.getAttribute(SPOTLIGHT_ATTR_BEAMWIDTH_NAME);
    if (attr != null)
      beamWidth = new SFFloat(attr.getValue(), 0.0f, cutOffAngleDefault.getValue());
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return SPOTLIGHTCustomizer.class;
  }
  
  @Override
  public String createAttributes()
  {
    StringBuffer sb = new StringBuffer();
    if (SPOTLIGHT_ATTR_AMBIENTINTENSITY_REQD || !ambientIntensity.equals(ambientIntensityDefault)) {
      sb.append(" ");
      sb.append(SPOTLIGHT_ATTR_AMBIENTINTENSITY_NAME);
      sb.append("='");
      sb.append(ambientIntensity);
      sb.append("'");
    }
    if (SPOTLIGHT_ATTR_ATTENUATION_REQD ||
           (!attenuationX.equals(attenuationXDefault) ||
            !attenuationY.equals(attenuationYDefault) ||
            !attenuationZ.equals(attenuationZDefault)))
    {
      sb.append(" ");
      sb.append(SPOTLIGHT_ATTR_ATTENUATION_NAME);
      sb.append("='");
      sb.append(attenuationX);
      sb.append(" ");
      sb.append(attenuationY);
      sb.append(" ");
      sb.append(attenuationZ);
      sb.append("'");
    }
    if (SPOTLIGHT_ATTR_BEAMWIDTH_REQD || !beamWidth.equals(beamWidthDefault)) {
      sb.append(" ");
      sb.append(SPOTLIGHT_ATTR_BEAMWIDTH_NAME);
      sb.append("='");
      sb.append(beamWidth);
      sb.append("'");
    }
    if (SPOTLIGHT_ATTR_COLOR_REQD ||
           (!color0.equals(color0Default) ||
            !color1.equals(color1Default) ||
            !color2.equals(color2Default)))
    {
      sb.append(" ");
      sb.append(SPOTLIGHT_ATTR_COLOR_NAME);
      sb.append("='");
      sb.append(color0);
      sb.append(" ");
      sb.append(color1);
      sb.append(" ");
      sb.append(color2);
      sb.append("'");
    }
    if (SPOTLIGHT_ATTR_CUTOFFANGLE_REQD || !cutOffAngle.equals(cutOffAngleDefault)) {
      sb.append(" ");
      sb.append(SPOTLIGHT_ATTR_CUTOFFANGLE_NAME);
      sb.append("='");
      sb.append(cutOffAngle);
      sb.append("'");
    }
    if (SPOTLIGHT_ATTR_DIRECTION_REQD ||
           (!directionX.equals(directionXDefault) ||
            !directionY.equals(directionYDefault) ||
            !directionZ.equals(directionZDefault)))
    {
      sb.append(" ");
      sb.append(SPOTLIGHT_ATTR_DIRECTION_NAME);
      sb.append("='");
      sb.append(directionX);
      sb.append(" ");
      sb.append(directionY);
      sb.append(" ");
      sb.append(directionZ);
      sb.append("'");
    }
    if (SPOTLIGHT_ATTR_GLOBAL_REQD || global != Boolean.parseBoolean(SPOTLIGHT_ATTR_GLOBAL_DFLT)) {
      sb.append(" ");
      sb.append(SPOTLIGHT_ATTR_GLOBAL_NAME);
      sb.append("='");
      sb.append(global);
      sb.append("'");
    }
    if (SPOTLIGHT_ATTR_INTENSITY_REQD || !intensity.equals(intensityDefault)) {
      sb.append(" ");
      sb.append(SPOTLIGHT_ATTR_INTENSITY_NAME);
      sb.append("='");
      sb.append(intensity);
      sb.append("'");
    }
    if (SPOTLIGHT_ATTR_LOCATION_REQD ||
           (!locationX.equals(locationXDefault) ||
            !locationY.equals(locationYDefault) ||
            !locationZ.equals(locationZDefault)))
    {
      sb.append(" ");
      sb.append(SPOTLIGHT_ATTR_LOCATION_NAME);
      sb.append("='");
      sb.append(locationX);
      sb.append(" ");
      sb.append(locationY);
      sb.append(" ");
      sb.append(locationZ);
      sb.append("'");
    }
    if (SPOTLIGHT_ATTR_ON_REQD || on != Boolean.parseBoolean(SPOTLIGHT_ATTR_ON_DFLT)) {
      sb.append(" ");
      sb.append(SPOTLIGHT_ATTR_ON_NAME);
      sb.append("='");
      sb.append(on);
      sb.append("'");
    }
    if (SPOTLIGHT_ATTR_RADIUS_REQD || !radius.equals(radiusDefault)) {
      sb.append(" ");
      sb.append(SPOTLIGHT_ATTR_RADIUS_NAME);
      sb.append("='");
      sb.append(radius);
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

  public boolean isGlobal()
  {
    return global;
  }
  
  public void setGlobal(boolean global)
  {
    this.global = global;
  }
  
  public String getIntensity()
  {
    return intensity.toString();
  }
  
  public void setIntensity(String intensity)
  {
    this.intensity = new SFFloat(intensity,0.0f,1.0f);
  }
    
  public boolean isOn()
  {
    return on;
  }
  
  public void setOn(boolean on)
  {
    this.on = on;
  }
  
  public String getRadius()
  {
    return radius.toString();
  }
  
  public void setRadius(String radius)
  {
    this.radius = new SFFloat(radius,0.0f,null);
  }
    
  public String getAttenuationX()
  {
    return attenuationX.toString();
  }

  public void setAttenuationX(String attenuationX)
  {
    this.attenuationX = new SFFloat(attenuationX,0.0f,null);
  }

  public String getAttenuationY() 
  {
    return attenuationY.toString();
  }

  public void setAttenuationY(String attenuationY)
  {
    this.attenuationY = new SFFloat(attenuationY,0.0f,null);
  }

  public String getAttenuationZ()
  {
    return attenuationZ.toString();
  }

  public void setAttenuationZ(String attenuationZ)
  {
    this.attenuationZ = new SFFloat(attenuationZ,0.0f,null);
  }

  public String getColorRed()
  {
    return color0.toString();
  }

  public void setColor0(String color0)
  {
    this.color0 = new SFFloat(color0,0.0f,1.0f);
  }

  public String getColorGreen()
  {
    return color1.toString();
  }

  public void setColor1(String color1)
  {
    this.color1 = new SFFloat(color1,0.0f,1.0f);
  }

  public String getColorBlue()
  {
    return color2.toString();
  }

  public void setColor2(String color2)
  {
    this.color2 = new SFFloat(color2,0.0f,1.0f);
  }

  public String getLocationX() 
  {
    return locationX.toString();
  }

  public void setLocationX(String locationX) 
  {
    this.locationX = new SFFloat(locationX,null,null);
  }

  public String getLocationY()
  {
    return locationY.toString();
  }

  public void setLocationY(String locationY)
  {
    this.locationY = new SFFloat(locationY,null,null);
  }

  public String getLocationZ()
  {
    return locationZ.toString();
  }

  public void setLocationZ(String locationZ)
  {
    this.locationZ = new SFFloat(locationZ,null,null);
  }

  public String getBeamWidth()
  {
    return beamWidth.toString();
  }
  
  public void setBeamWidth(String beamWidth)
  {
    this.beamWidth = new SFFloat(beamWidth,0.0f, beamWidthDefault.getValue());
  }
  
  public String getCutOffAngle()
  {
      return radiansFormat.format(cutOffAngle.getValue());
  }
  
  public void setCutOffAngle(String cutOffAngle)
  {
    this.cutOffAngle = new SFFloat(cutOffAngle,0.0f, cutOffAngleDefault.getValue());
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
}
