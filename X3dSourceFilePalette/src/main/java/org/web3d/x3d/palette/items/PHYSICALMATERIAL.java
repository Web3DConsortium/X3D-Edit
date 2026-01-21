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

import org.web3d.x3d.types.X3DMaterialNode;

import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * PHYSICALMATERIAL.java
 * 
 * @author Don Brutzman
 * @version $Id$
 */
public class PHYSICALMATERIAL extends X3DMaterialNode
{
  private SFFloat metallic,          metallicDefault;
  private SFFloat normalScale,       normalScaleDefault;
  private SFFloat occlusionStrength, occlusionStrengthDefault;
  private SFFloat roughness,         roughnessDefault;
  private SFFloat transparency,      transparencyDefault;
  
  private String               baseTextureMapping;
  private String           emissiveTextureMapping;
  private String  metallicRoughnessTextureMapping;
  private String             normalTextureMapping;
  private String          occlusionTextureMapping;
  
  private SFFloat baseColor0,        baseColor0Default;
  private SFFloat baseColor1,        baseColor1Default;
  private SFFloat baseColor2,        baseColor2Default;
  
  private SFFloat emissiveColor0,    emissiveColor0Default;
  private SFFloat emissiveColor1,    emissiveColor1Default;
  private SFFloat emissiveColor2,    emissiveColor2Default;
  
  public PHYSICALMATERIAL()
  {
  }
  
  @Override
  public String getElementName()
  {
    return PHYSICALMATERIAL_ELNAME;
  }
  
  @Override
  public void initialize()
  {
    metallic          = metallicDefault          = new SFFloat(PHYSICALMATERIAL_ATTR_METALLIC_DFLT, 0.0f, 1.0f);
        setNormalScale(normalScaleDefault       = new SFFloat(PHYSICALMATERIAL_ATTR_NORMALSCALE_DFLT, 0.0f, null));
        setOcclusionStrength(occlusionStrengthDefault = new SFFloat(PHYSICALMATERIAL_ATTR_OCCLUSIONSTRENGTH_DFLT, 0.0f, 1.0f));
    roughness         = roughnessDefault         = new SFFloat(PHYSICALMATERIAL_ATTR_ROUGHNESS_DFLT, 0.0f, 1.0f);
    transparency      = transparencyDefault      = new SFFloat(PHYSICALMATERIAL_ATTR_TRANSPARENCY_DFLT, 0.0f, 1.0f);
  
                 setBaseTextureMapping(PHYSICALMATERIAL_ATTR_BASETEXTUREMAPPING_DFLT);
             setEmissiveTextureMapping(PHYSICALMATERIAL_ATTR_EMISSIVETEXTUREMAPPING_DFLT);
        setMetallicRoughnessTextureMapping(PHYSICALMATERIAL_ATTR_METALLICROUGHNESSTEXTUREMAPPING_DFLT);
               setNormalTextureMapping(PHYSICALMATERIAL_ATTR_NORMALTEXTUREMAPPING_DFLT);
            setOcclusionTextureMapping(PHYSICALMATERIAL_ATTR_OCCLUSIONTEXTUREMAPPING_DFLT);
    
    String[] fa;
    fa = parse3(PHYSICALMATERIAL_ATTR_BASECOLOR_DFLT);    
    baseColor0 = baseColor0Default = new SFFloat(fa[0],0.0f,1.0f);
    baseColor1 = baseColor1Default = new SFFloat(fa[1],0.0f,1.0f);
    baseColor2 = baseColor2Default = new SFFloat(fa[2],0.0f,1.0f);    
    fa = parse3(PHYSICALMATERIAL_ATTR_EMISSIVECOLOR_DFLT);    
    emissiveColor0 = emissiveColor0Default = new SFFloat(fa[0],0.0f,1.0f);
    emissiveColor1 = emissiveColor1Default = new SFFloat(fa[1],0.0f,1.0f);
    emissiveColor2 = emissiveColor2Default = new SFFloat(fa[2],0.0f,1.0f);
  }
  
  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(PHYSICALMATERIAL_ATTR_METALLIC_NAME);
    if (attr != null)
       metallic = new SFFloat(attr.getValue(), 0.0f, 1.0f);
    attr = root.getAttribute(PHYSICALMATERIAL_ATTR_NORMALSCALE_NAME);
    if (attr != null)
        setNormalScale(new SFFloat(attr.getValue(), 0.0f, null));
    attr = root.getAttribute(PHYSICALMATERIAL_ATTR_OCCLUSIONSTRENGTH_NAME);
    if (attr != null)
        setOcclusionStrength(new SFFloat(attr.getValue(), 0.0f, 1.0f));
    attr = root.getAttribute(PHYSICALMATERIAL_ATTR_ROUGHNESS_NAME);
    if (attr != null)
       roughness = new SFFloat(attr.getValue(), 0.0f, 1.0f);
    attr = root.getAttribute(PHYSICALMATERIAL_ATTR_TRANSPARENCY_NAME);
    if (attr != null)
       transparency = new SFFloat(attr.getValue(), 0.0f, 1.0f);
    
    attr = root.getAttribute(PHYSICALMATERIAL_ATTR_BASETEXTUREMAPPING_NAME);
    if (attr != null)
        setBaseTextureMapping(attr.getValue());
    attr = root.getAttribute(PHYSICALMATERIAL_ATTR_EMISSIVETEXTUREMAPPING_NAME);
    if (attr != null)
        setEmissiveTextureMapping(attr.getValue());
    attr = root.getAttribute(PHYSICALMATERIAL_ATTR_METALLICROUGHNESSTEXTUREMAPPING_NAME);
    if (attr != null)
        setMetallicRoughnessTextureMapping(attr.getValue());
    attr = root.getAttribute(PHYSICALMATERIAL_ATTR_NORMALTEXTUREMAPPING_NAME);
    if (attr != null)
        setNormalTextureMapping(attr.getValue());
    attr = root.getAttribute(PHYSICALMATERIAL_ATTR_OCCLUSIONTEXTUREMAPPING_NAME);
    if (attr != null)
        setOcclusionTextureMapping(attr.getValue());
    
    String[] fa;
    // actual bound is 1 for color values; allow [0..255] HTML values here and test further in MaterialCustomizer
    attr = root.getAttribute(PHYSICALMATERIAL_ATTR_BASECOLOR_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      baseColor0 = new SFFloat(fa[0], 0.0f, 255.0f);
      baseColor1 = new SFFloat(fa[1], 0.0f, 255.0f);
      baseColor2 = new SFFloat(fa[2], 0.0f, 255.0f);
    }
    attr = root.getAttribute(PHYSICALMATERIAL_ATTR_EMISSIVECOLOR_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      emissiveColor0 = new SFFloat(fa[0], 0.0f, 255.0f);
      emissiveColor1 = new SFFloat(fa[1], 0.0f, 255.0f);
      emissiveColor2 = new SFFloat(fa[2], 0.0f, 255.0f);
    }
  }
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    if(PHYSICALMATERIAL_ATTR_BASECOLOR_REQD || 
           (!baseColor0.equals(baseColor0Default) ||
            !baseColor1.equals(baseColor1Default) ||
            !baseColor2.equals(baseColor2Default)) ) {
      sb.append(" ");
      sb.append(PHYSICALMATERIAL_ATTR_BASECOLOR_NAME);
      sb.append("='");
      sb.append(baseColor0);
      sb.append(" ");
      sb.append(baseColor1);
      sb.append(" ");
      sb.append(baseColor2);
      sb.append("'");       
    }
    if(PHYSICALMATERIAL_ATTR_BASETEXTUREMAPPING_REQD || !baseTextureMapping.equals(PHYSICALMATERIAL_ATTR_BASETEXTUREMAPPING_DFLT)) {
      sb.append(" ");
      sb.append(PHYSICALMATERIAL_ATTR_BASETEXTUREMAPPING_NAME);
      sb.append("='");
      sb.append(getBaseTextureMapping());
      sb.append("'");
    }
    if(PHYSICALMATERIAL_ATTR_EMISSIVECOLOR_REQD  || 
           (!emissiveColor0.equals(emissiveColor0Default) ||
            !emissiveColor1.equals(emissiveColor1Default) ||
            !emissiveColor2.equals(emissiveColor2Default)) ) {
      sb.append(" ");
      sb.append(PHYSICALMATERIAL_ATTR_EMISSIVECOLOR_NAME);
      sb.append("='");
      sb.append(emissiveColor0);
      sb.append(" ");
      sb.append(emissiveColor1);
      sb.append(" ");
      sb.append(emissiveColor2);
      sb.append("'");       
    }
    if(PHYSICALMATERIAL_ATTR_EMISSIVETEXTUREMAPPING_REQD || !emissiveTextureMapping.equals(PHYSICALMATERIAL_ATTR_EMISSIVETEXTUREMAPPING_DFLT)) {
      sb.append(" ");
      sb.append(PHYSICALMATERIAL_ATTR_EMISSIVETEXTUREMAPPING_NAME);
      sb.append("='");
      sb.append(getEmissiveTextureMapping());
      sb.append("'");
    }
    if(PHYSICALMATERIAL_ATTR_METALLIC_REQD || !metallic.equals(metallicDefault)) {
      sb.append(" ");
      sb.append(PHYSICALMATERIAL_ATTR_METALLIC_NAME);
      sb.append("='");
      sb.append(metallic);
      sb.append("'");
    }
    if(PHYSICALMATERIAL_ATTR_METALLICROUGHNESSTEXTUREMAPPING_REQD || !metallicRoughnessTextureMapping.equals(PHYSICALMATERIAL_ATTR_METALLICROUGHNESSTEXTUREMAPPING_DFLT)) {
      sb.append(" ");
      sb.append(PHYSICALMATERIAL_ATTR_METALLICROUGHNESSTEXTUREMAPPING_NAME);
      sb.append("='");
      sb.append(getMetallicRoughnessTextureMapping());
      sb.append("'");
    }
    if(PHYSICALMATERIAL_ATTR_NORMALSCALE_REQD || !normalScale.equals(normalScaleDefault)) {
      sb.append(" ");
      sb.append(PHYSICALMATERIAL_ATTR_NORMALSCALE_NAME);
      sb.append("='");
      sb.append(getNormalScale());
      sb.append("'");
    }
    if(PHYSICALMATERIAL_ATTR_NORMALTEXTUREMAPPING_REQD || !normalTextureMapping.equals(PHYSICALMATERIAL_ATTR_NORMALTEXTUREMAPPING_DFLT)) {
      sb.append(" ");
      sb.append(PHYSICALMATERIAL_ATTR_NORMALTEXTUREMAPPING_NAME);
      sb.append("='");
      sb.append(getNormalTextureMapping());
      sb.append("'");
    }
    if(PHYSICALMATERIAL_ATTR_OCCLUSIONSTRENGTH_REQD || !occlusionStrength.equals(occlusionStrengthDefault)) {
      sb.append(" ");
      sb.append(PHYSICALMATERIAL_ATTR_OCCLUSIONSTRENGTH_NAME);
      sb.append("='");
      sb.append(getOcclusionStrength());
      sb.append("'");
    }
    if(PHYSICALMATERIAL_ATTR_OCCLUSIONTEXTUREMAPPING_REQD || !occlusionTextureMapping.equals(PHYSICALMATERIAL_ATTR_OCCLUSIONTEXTUREMAPPING_DFLT)) {
      sb.append(" ");
      sb.append(PHYSICALMATERIAL_ATTR_OCCLUSIONTEXTUREMAPPING_NAME);
      sb.append("='");
      sb.append(getOcclusionTextureMapping());
      sb.append("'");
    }
    if(PHYSICALMATERIAL_ATTR_ROUGHNESS_REQD || !roughness.equals(roughnessDefault)) {
      sb.append(" ");
      sb.append(PHYSICALMATERIAL_ATTR_ROUGHNESS_NAME);
      sb.append("='");
      sb.append(roughness);
      sb.append("'");
    }
    if(PHYSICALMATERIAL_ATTR_TRANSPARENCY_REQD || !transparency.equals(transparencyDefault)) {
      sb.append(" ");
      sb.append(PHYSICALMATERIAL_ATTR_TRANSPARENCY_NAME);
      sb.append("='");
      sb.append(transparency);
      sb.append("'");
    }
    return sb.toString();  
  }
  
  public String getMetallic()
  {
    return metallic.toString();
  }

  public void setMetallic(String metallic)
  {
    this.metallic = new SFFloat(metallic, 0.0f, 1.0f);
  }

  public String getRoughness()
  {
    return roughness.toString();
  }

  public void setRoughness(String roughness)
  {
    this.roughness = new SFFloat(roughness, 0.0f, 1.0f);
  }

  public String getTransparency()
  {
    return transparency.toString();
  }

  public void setTransparency(String transparency)
  {
    this.transparency = new SFFloat(transparency, 0.0f, 1.0f);
  }

  public String getBaseColor0()
  {
    return baseColor0.toString();
  }

  public void setBaseColor0(String baseColor0)
  {
    this.baseColor0 = new SFFloat(baseColor0, 0.0f, 1.0f);
  }

  public String getBaseColor1()
  {
    return baseColor1.toString();
  }

  public void setBaseColor1(String baseColor1)
  {
    this.baseColor1 = new SFFloat(baseColor1, 0.0f, 1.0f);
  }

  public String getBaseColor2()
  {
    return baseColor2.toString();
  }

  public void setBaseColor2(String baseColor2)
  {
    this.baseColor2 = new SFFloat(baseColor2, 0.0f, 1.0f);
  }

  public String getBaseColor()
  {
    return baseColor0.toString() + " " + baseColor1.toString() + " " + baseColor2.toString();
  }

  public String getEmissiveColor0()
  {
    return emissiveColor0.toString();
  }

  public void setEmissiveColor0(String emissiveColor0)
  {
    this.emissiveColor0 = new SFFloat(emissiveColor0, 0.0f, 1.0f);
  }

  public String getEmissiveColor1()
  {
    return emissiveColor1.toString();
  }

  public void setEmissiveColor1(String emissiveColor1)
  {
    this.emissiveColor1 = new SFFloat(emissiveColor1, 0.0f, 1.0f);
  }

  public String getEmissiveColor2()
  {
    return emissiveColor2.toString();
  }

  public void setEmissiveColor2(String emissiveColor2)
  {
    this.emissiveColor2 = new SFFloat(emissiveColor2, 0.0f, 1.0f);
  }

  public String getEmissiveColor()
  {
    return emissiveColor0.toString() + " " + emissiveColor1.toString() + " " + emissiveColor2.toString();
  }

    /**
     * @return the normalScale
     */
    public SFFloat getNormalScale() {
        return normalScale;
    }

    /**
     * @param normalScale the normalScale to set
     */
    public void setNormalScale(SFFloat normalScale) {
        this.normalScale = normalScale;
    }

    /**
     * @return the occlusionStrength
     */
    public SFFloat getOcclusionStrength() {
        return occlusionStrength;
    }

    /**
     * @param occlusionStrength the occlusionStrength to set
     */
    public void setOcclusionStrength(SFFloat occlusionStrength) {
        this.occlusionStrength = occlusionStrength;
    }

    /**
     * @return the baseTextureMapping
     */
    public String getBaseTextureMapping() {
        return baseTextureMapping;
    }

    /**
     * @param baseTextureMapping the baseTextureMapping to set
     */
    public void setBaseTextureMapping(String baseTextureMapping) {
        this.baseTextureMapping = baseTextureMapping;
    }

    /**
     * @return the emissiveTextureMapping
     */
    public String getEmissiveTextureMapping() {
        return emissiveTextureMapping;
    }

    /**
     * @param emissiveTextureMapping the emissiveTextureMapping to set
     */
    public void setEmissiveTextureMapping(String emissiveTextureMapping) {
        this.emissiveTextureMapping = emissiveTextureMapping;
    }

    /**
     * @return the metallicRoughnessTextureMapping
     */
    public String getMetallicRoughnessTextureMapping() {
        return metallicRoughnessTextureMapping;
    }

    /**
     * @param metallicRoughnessTextureMapping the metallicRoughnessTextureMapping to set
     */
    public void setMetallicRoughnessTextureMapping(String metallicRoughnessTextureMapping) {
        this.metallicRoughnessTextureMapping = metallicRoughnessTextureMapping;
    }

    /**
     * @return the normalTextureMapping
     */
    public String getNormalTextureMapping() {
        return normalTextureMapping;
    }

    /**
     * @param normalTextureMapping the normalTextureMapping to set
     */
    public void setNormalTextureMapping(String normalTextureMapping) {
        this.normalTextureMapping = normalTextureMapping;
    }

    /**
     * @return the occlusionTextureMapping
     */
    public String getOcclusionTextureMapping() {
        return occlusionTextureMapping;
    }

    /**
     * @param occlusionTextureMapping the occlusionTextureMapping to set
     */
    public void setOcclusionTextureMapping(String occlusionTextureMapping) {
        this.occlusionTextureMapping = occlusionTextureMapping;
    }

}
