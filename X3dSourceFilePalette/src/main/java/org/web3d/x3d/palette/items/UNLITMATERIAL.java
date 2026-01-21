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
 * UNLITMATERIAL.java
 * 
 * @author Don Brutzman
 * @version $Id$
 */
public class UNLITMATERIAL extends X3DMaterialNode
{
  private SFFloat normalScale, normalScaleDefault;
  private String  emissiveTextureMapping;
  private String    normalTextureMapping;
  private SFFloat transparency, transparencyDefault;
  
  private SFFloat emissiveColor0, emissiveColor0Default;
  private SFFloat emissiveColor1, emissiveColor1Default;
  private SFFloat emissiveColor2, emissiveColor2Default;
  
  public UNLITMATERIAL()
  {
  }
  
  @Override
  public String getElementName()
  {
    return UNLITMATERIAL_ELNAME;
  }

  @Override
  public void initialize()
  {
        setNormalScale(normalScaleDefault   = new SFFloat(UNLITMATERIAL_ATTR_NORMALSCALE_DFLT, 0.0f, null));
        setTransparency(transparencyDefault = new SFFloat(UNLITMATERIAL_ATTR_TRANSPARENCY_DFLT, 0.0f, 1.0f));
    
    String[] fa; 
    fa = parse3(UNLITMATERIAL_ATTR_EMISSIVECOLOR_DFLT);    
    emissiveColor0 = emissiveColor0Default = new SFFloat(fa[0],0.0f,1.0f);
    emissiveColor1 = emissiveColor1Default = new SFFloat(fa[1],0.0f,1.0f);
    emissiveColor2 = emissiveColor2Default = new SFFloat(fa[2],0.0f,1.0f);
    
    emissiveTextureMapping = UNLITMATERIAL_ATTR_EMISSIVETEXTUREMAPPING_DFLT;
      normalTextureMapping = UNLITMATERIAL_ATTR_NORMALTEXTUREMAPPING_DFLT;
  }
  
  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(UNLITMATERIAL_ATTR_NORMALSCALE_NAME);
    if (attr != null)
        setNormalScale(new SFFloat(attr.getValue(), 0.0f, null));
    attr = root.getAttribute(UNLITMATERIAL_ATTR_TRANSPARENCY_NAME);
    if (attr != null)
        setTransparency(new SFFloat(attr.getValue(), 0.0f, 1.0f));
    String[] fa;
    attr = root.getAttribute(UNLITMATERIAL_ATTR_EMISSIVECOLOR_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      emissiveColor0 = new SFFloat(fa[0], 0.0f, 1.0f);
      emissiveColor1 = new SFFloat(fa[1], 0.0f, 1.0f);
      emissiveColor2 = new SFFloat(fa[2], 0.0f, 1.0f);
    }
    attr = root.getAttribute(UNLITMATERIAL_ATTR_EMISSIVETEXTUREMAPPING_NAME);
    if (attr != null)
      emissiveTextureMapping = attr.getValue();
    attr = root.getAttribute(UNLITMATERIAL_ATTR_NORMALTEXTUREMAPPING_NAME);
    if (attr != null)
      normalTextureMapping = attr.getValue();
  }
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    
    if(UNLITMATERIAL_ATTR_EMISSIVECOLOR_REQD || 
           (!emissiveColor0.equals(emissiveColor0Default) ||
            !emissiveColor1.equals(emissiveColor1Default) ||
            !emissiveColor2.equals(emissiveColor2Default)) ) {
      sb.append(" ");
      sb.append(UNLITMATERIAL_ATTR_EMISSIVECOLOR_NAME);
      sb.append("='");
      sb.append(emissiveColor0);
      sb.append(" ");
      sb.append(emissiveColor1);
      sb.append(" ");
      sb.append(emissiveColor2);
      sb.append("'");       
    }      
    if(UNLITMATERIAL_ATTR_EMISSIVETEXTUREMAPPING_REQD || !normalTextureMapping.equals(UNLITMATERIAL_ATTR_EMISSIVETEXTUREMAPPING_DFLT)) {
      sb.append(" ");
      sb.append(UNLITMATERIAL_ATTR_EMISSIVETEXTUREMAPPING_NAME);
      sb.append("='");
      sb.append(emissiveTextureMapping);
      sb.append("'");
    }
    if(UNLITMATERIAL_ATTR_NORMALSCALE_REQD || !normalScale.equals(normalScaleDefault)) {
      sb.append(" ");
      sb.append(UNLITMATERIAL_ATTR_NORMALSCALE_NAME);
      sb.append("='");
      sb.append(getNormalScale());
      sb.append("'");
    }
    if(UNLITMATERIAL_ATTR_NORMALTEXTUREMAPPING_REQD || !normalTextureMapping.equals(UNLITMATERIAL_ATTR_NORMALTEXTUREMAPPING_DFLT)) {
      sb.append(" ");
      sb.append(UNLITMATERIAL_ATTR_NORMALTEXTUREMAPPING_NAME);
      sb.append("='");
      sb.append(normalTextureMapping);
      sb.append("'");
    }
    if(UNLITMATERIAL_ATTR_TRANSPARENCY_REQD || !transparency.equals(transparencyDefault)) {
      sb.append(" ");
      sb.append(UNLITMATERIAL_ATTR_TRANSPARENCY_NAME);
      sb.append("='");
      sb.append(getTransparency());
      sb.append("'");
    }
    return sb.toString();  
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
     * @return the transparency
     */
    public SFFloat getTransparency() {
        return transparency;
    }

    /**
     * @param transparency the transparency to set
     */
    public void setTransparency(SFFloat transparency) {
        this.transparency = transparency;
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
}
