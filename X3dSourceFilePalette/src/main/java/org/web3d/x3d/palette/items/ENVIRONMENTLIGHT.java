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
import org.web3d.x3d.types.X3DLightNode;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * ENVIRONMENTLIGHT.java
 * 
 * @author Don Brutzman
 * @version $Id$
 */
public class ENVIRONMENTLIGHT extends X3DLightNode
{
  private SFFloat ambientIntensity,ambientIntensityDefault;
  private SFFloat color0,color0Default;
  private SFFloat color1,color1Default;
  private SFFloat color2,color2Default;
  // TODO 3x9 MFFloat 
  private String diffuseCoefficients, diffuseCoefficientsDefault;
  private boolean global;
  private SFFloat intensity,intensityDefault;
  private boolean on;
  private SFFloat originX,originXDefault;
  private SFFloat originY,originYDefault;
  private SFFloat originZ,originZDefault;
  
  // Together make one SFRotation
  public SFFloat rotationX, rotationXDefault;
  public SFFloat rotationY, rotationYDefault;
  public SFFloat rotationZ, rotationZDefault;
  public SFFloat rotationAngle, rotationAngleDefault;
  private boolean shadows;
  private SFFloat shadowIntensity,shadowIntensityDefault;
  
  public ENVIRONMENTLIGHT()
  {
  }
  
  @Override
  public String getElementName()
  {
    return ENVIRONMENTLIGHT_ELNAME;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return ENVIRONMENTLIGHTCustomizer.class;
  }
  
  @Override
  public void initialize()
  {
    ambientIntensity = ambientIntensityDefault = new SFFloat(ENVIRONMENTLIGHT_ATTR_AMBIENTINTENSITY_DFLT, 0.0f, 1.0f);
    
    String[] fa = parse3(ENVIRONMENTLIGHT_ATTR_COLOR_DFLT);
    color0 = color0Default = new SFFloat(fa[0],0.0f,1.0f);
    color1 = color1Default = new SFFloat(fa[1],0.0f,1.0f);
    color2 = color2Default = new SFFloat(fa[2],0.0f,1.0f);
    
    diffuseCoefficients = diffuseCoefficientsDefault = ENVIRONMENTLIGHT_ATTR_DIFFUSECOEFFICIENTS_DFLT;
    
    intensity = intensityDefault = new SFFloat(ENVIRONMENTLIGHT_ATTR_INTENSITY_DFLT, 0.0f, null);
    
    fa = parse3(ENVIRONMENTLIGHT_ATTR_ORIGIN_DFLT);
    originX = originXDefault = new SFFloat(fa[0],null,null);
    originY = originYDefault = new SFFloat(fa[1],null,null);
    originZ = originZDefault = new SFFloat(fa[2],null,null);

    global = Boolean.parseBoolean(ENVIRONMENTLIGHT_ATTR_GLOBAL_DFLT);
    on = Boolean.parseBoolean(ENVIRONMENTLIGHT_ATTR_ON_DFLT);
    
    fa = parse4(ENVIRONMENTLIGHT_ATTR_ROTATION_DFLT);
    rotationX = rotationXDefault = new SFFloat(fa[0], null, null);
    rotationY = rotationYDefault = new SFFloat(fa[1], null, null);
    rotationZ = rotationZDefault = new SFFloat(fa[2], null, null);
    rotationAngle = rotationAngleDefault = new SFFloat(fa[3], null, null);
    
    shadows = Boolean.parseBoolean(ENVIRONMENTLIGHT_ATTR_SHADOWS_DFLT);
    shadowIntensity = shadowIntensityDefault = new SFFloat(ENVIRONMENTLIGHT_ATTR_SHADOWINTENSITY_DFLT, 0.0f, 1.0f);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(ENVIRONMENTLIGHT_ATTR_AMBIENTINTENSITY_NAME);
    if (attr != null)
      ambientIntensity = new SFFloat(attr.getValue(), 0.0f, 1.0f);
    String[] fa;
    attr = root.getAttribute(ENVIRONMENTLIGHT_ATTR_COLOR_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      color0 = new SFFloat(fa[0], 0.0f, 1.0f);
      color1 = new SFFloat(fa[1], 0.0f, 1.0f);
      color2 = new SFFloat(fa[2], 0.0f, 1.0f);
    }
    attr = root.getAttribute(ENVIRONMENTLIGHT_ATTR_DIFFUSECOEFFICIENTS_NAME);
    if (attr != null)
        setDiffuseCoefficients(attr.getValue().replaceAll("\\s+", " ").trim());
    attr = root.getAttribute(ENVIRONMENTLIGHT_ATTR_GLOBAL_NAME);
    if (attr != null)
      global = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(ENVIRONMENTLIGHT_ATTR_INTENSITY_NAME);
    if (attr != null)
      intensity = new SFFloat(attr.getValue(), 0.0f, null);
    attr = root.getAttribute(ENVIRONMENTLIGHT_ATTR_ORIGIN_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      originX = new SFFloat(fa[0], null, null);
      originY = new SFFloat(fa[1], null, null);
      originZ = new SFFloat(fa[2], null, null);
    }
    attr = root.getAttribute(ENVIRONMENTLIGHT_ATTR_ON_NAME);
    if (attr != null)
      on = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(ENVIRONMENTLIGHT_ATTR_ROTATION_NAME);
    if (attr != null) {
      fa = parse4(attr.getValue());
      rotationX     = new SFFloat(fa[0], null, null);
      rotationY     = new SFFloat(fa[1], null, null);
      rotationZ     = new SFFloat(fa[2], null, null);
      rotationAngle = new SFFloat(fa[3], null, null);
    }
    
    attr = root.getAttribute(ENVIRONMENTLIGHT_ATTR_SHADOWS_NAME);
    if (attr != null)
      shadows = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(ENVIRONMENTLIGHT_ATTR_SHADOWINTENSITY_NAME);
    if (attr != null)
      shadowIntensity = new SFFloat(attr.getValue(), 0.0f, 1.0f);
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    if(ENVIRONMENTLIGHT_ATTR_AMBIENTINTENSITY_REQD || !ambientIntensity.equals(ambientIntensityDefault)) {
      sb.append(" ");
      sb.append(ENVIRONMENTLIGHT_ATTR_AMBIENTINTENSITY_NAME);
      sb.append("='");
      sb.append(ambientIntensity);
      sb.append("'");
    }     
    if(ENVIRONMENTLIGHT_ATTR_COLOR_REQD || 
           (!color0.equals(color0Default) ||
            !color1.equals(color1Default) ||
            !color2.equals(color2Default)) ) {
      sb.append(" ");
      sb.append(ENVIRONMENTLIGHT_ATTR_COLOR_NAME);
      sb.append("='");
      sb.append(color0);
      sb.append(" ");
      sb.append(color1);
      sb.append(" ");
      sb.append(color2);
      sb.append("'");       
    }     
    if(ENVIRONMENTLIGHT_ATTR_DIFFUSECOEFFICIENTS_REQD || !diffuseCoefficients.equals(diffuseCoefficients) ) {
      sb.append(" ");
      sb.append(ENVIRONMENTLIGHT_ATTR_DIFFUSECOEFFICIENTS_NAME);
      sb.append("='");
      sb.append(getDiffuseCoefficients().replaceAll("\\s+", " ").trim());
      sb.append("'");       
    }    
    if(ENVIRONMENTLIGHT_ATTR_GLOBAL_REQD || global != Boolean.parseBoolean(ENVIRONMENTLIGHT_ATTR_GLOBAL_DFLT) ) {
      sb.append(" ");
      sb.append(ENVIRONMENTLIGHT_ATTR_GLOBAL_NAME);
      sb.append("='");
      sb.append(global);
      sb.append("'");       
    }    
    if(ENVIRONMENTLIGHT_ATTR_INTENSITY_REQD || !intensity.equals(intensityDefault)) {
      sb.append(" ");
      sb.append(ENVIRONMENTLIGHT_ATTR_INTENSITY_NAME);
      sb.append("='");
      sb.append(intensity);
      sb.append("'");       
    }
    if(ENVIRONMENTLIGHT_ATTR_ON_REQD || on != Boolean.parseBoolean(ENVIRONMENTLIGHT_ATTR_ON_DFLT) ) {
      sb.append(" ");
      sb.append(ENVIRONMENTLIGHT_ATTR_ON_NAME);
      sb.append("='");
      sb.append(on);
      sb.append("'");       
    }
    if(ENVIRONMENTLIGHT_ATTR_ORIGIN_REQD || 
           (!originX.equals(originXDefault) ||
            !originY.equals(originYDefault) ||
            !originZ.equals(originZDefault)) ) {
      sb.append(" ");
      sb.append(ENVIRONMENTLIGHT_ATTR_ORIGIN_NAME);
      sb.append("='");
      sb.append(originX);
      sb.append(" ");
      sb.append(originY);
      sb.append(" ");
      sb.append(originZ);
      sb.append("'");       
    }
    if (ENVIRONMENTLIGHT_ATTR_ROTATION_REQD ||
                   (!rotationX.equals(rotationXDefault) ||
                    !rotationY.equals(rotationYDefault) ||
                    !rotationZ.equals(rotationZDefault) ||
                    !rotationAngle.equals(rotationAngleDefault))) {
      sb.append(" ");
      sb.append(ENVIRONMENTLIGHT_ATTR_ROTATION_NAME);
      sb.append("='");
      sb.append(rotationX);
      sb.append(" ");
      sb.append(rotationY);
      sb.append(" ");
      sb.append(rotationZ);
      sb.append(" ");
      sb.append(rotationAngle);
      sb.append("'");
    }
    if(ENVIRONMENTLIGHT_ATTR_SHADOWS_REQD || shadows != Boolean.parseBoolean(ENVIRONMENTLIGHT_ATTR_SHADOWS_DFLT) ) {
      sb.append(" ");
      sb.append(ENVIRONMENTLIGHT_ATTR_SHADOWS_NAME);
      sb.append("='");
      sb.append(shadows);
      sb.append("'");       
    }
    if(ENVIRONMENTLIGHT_ATTR_SHADOWINTENSITY_REQD || !shadowIntensity.equals(shadowIntensityDefault)) {
      sb.append(" ");
      sb.append(ENVIRONMENTLIGHT_ATTR_SHADOWINTENSITY_NAME);
      sb.append("='");
      sb.append(shadowIntensity);
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
    this.intensity = new SFFloat(intensity,0.0f,null);
  }

  public boolean isOn()
  {
    return on;
  }

  public void setOn(boolean on)
  {
    this.on = on;
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

  public String getOriginX() 
  {
    return originX.toString();
  }

  public void setOriginX(String originX) 
  {
    this.originX = new SFFloat(originX,null,null);
  }

  public String getOriginY()
  {
    return originY.toString();
  }

  public void setOriginY(String originY)
  {
    this.originY = new SFFloat(originY,null,null);
  }

  public String getOriginZ()
  {
    return originZ.toString();
  }

  public void setOriginZ(String originZ)
  {
    this.originZ = new SFFloat(originZ,null,null);
  }

  public String getRotationX()
  {
    return rotationX.toString();
  }

  public void setRotationX(String rotationX)
  {
    this.rotationX = new SFFloat(rotationX, null, null);
  }

  public String getRotationY()
  {
    return rotationY.toString();
  }

  public void setRotationY(String rotationY)
  {
    this.rotationY = new SFFloat(rotationY, null, null);
  }

  public String getRotationZ()
  {
    return rotationZ.toString();
  }

  public void setRotationZ(String rotationZ)
  {
    this.rotationZ = new SFFloat(rotationZ, null, null);
  }

  public String getRotationAngle()
  {
    return radiansFormat.format(rotationAngle.getValue());
  }

  public void setRotationAngle(String rotationAngle)
  {
    this.rotationAngle = new SFFloat(rotationAngle, null, null);
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
     * @return the diffuseCoefficients
     */
    public String getDiffuseCoefficients() {
        return diffuseCoefficients;
    }

    /**
     * @param diffuseCoefficients the diffuseCoefficients to set
     */
    public void setDiffuseCoefficients(String diffuseCoefficients) {
        this.diffuseCoefficients = diffuseCoefficients;
    }
}
