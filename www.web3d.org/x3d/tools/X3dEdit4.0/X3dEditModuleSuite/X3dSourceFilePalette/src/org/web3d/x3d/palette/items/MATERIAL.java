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

import org.web3d.x3d.types.X3DMaterialNode;

import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * MATERIAL.java
 * Created on March 14, 2007, 10:09 AM
 * 
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 * 
 * @author Mike Bailey
 * @version $Id$
 */
public class MATERIAL extends X3DMaterialNode
{
  private SFFloat ambientIntensity, ambientIntensityDefault;
  private SFFloat shininess, shininessDefault;
  private SFFloat transparency, transparencyDefault;
  
  private SFFloat diffuseColor0, diffuseColor0Default;
  private SFFloat diffuseColor1, diffuseColor1Default;
  private SFFloat diffuseColor2, diffuseColor2Default;
  
  private SFFloat emissiveColor0, emissiveColor0Default;
  private SFFloat emissiveColor1, emissiveColor1Default;
  private SFFloat emissiveColor2, emissiveColor2Default;
  
  private SFFloat specularColor0, specularColor0Default;
  private SFFloat specularColor1, specularColor1Default;
  private SFFloat specularColor2, specularColor2Default;

  private boolean front, back = false;
  
  public MATERIAL()
  {
  }
  
  @Override
  public String getElementName()
  {
    return MATERIAL_ELNAME;
  }

  /*  uncomment when materialcustomizer is turned back into a singleton
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return null; //MATERIALCustomizer.class;
  }


  @Override
  public Method getCustomizerFactoryMethod()
  {
    Method m = null;
    try {
      m = MATERIALCustomizer.class.getDeclaredMethod("getInstance", MATERIAL.class, JTextComponent.class);
    }
    catch (Exception ex) {}
    return m;
  }
  */
  
  @Override
  public void initialize()
  {
    initialize(new materialConstants());
  }
  
  public void initialize(materialConstants constants)
  {
    ambientIntensity = ambientIntensityDefault = new SFFloat(constants.get_ambIntensDflt(), 0.0f, 1.0f);
    shininess = shininessDefault               = new SFFloat(constants.get_shininessDflt(), 0.0f, 1.0f);
    transparency = transparencyDefault         = new SFFloat(constants.get_trnsprncyDflt(), 0.0f, 1.0f);
    
    String[] fa;
    
    fa = parse3(constants.get_diffColorDflt());    
    diffuseColor0 = diffuseColor0Default = new SFFloat(fa[0],0.0f,1.0f);
    diffuseColor1 = diffuseColor1Default = new SFFloat(fa[1],0.0f,1.0f);
    diffuseColor2 = diffuseColor2Default = new SFFloat(fa[2],0.0f,1.0f);    
    fa = parse3(constants.get_emisColorDflt());    
    emissiveColor0 = emissiveColor0Default = new SFFloat(fa[0],0.0f,1.0f);
    emissiveColor1 = emissiveColor1Default = new SFFloat(fa[1],0.0f,1.0f);
    emissiveColor2 = emissiveColor2Default = new SFFloat(fa[2],0.0f,1.0f);
    fa = parse3(constants.get_specColorDflt());    
    specularColor0 = specularColor0Default = new SFFloat(fa[0],0.0f,1.0f);
    specularColor1 = specularColor1Default = new SFFloat(fa[1],0.0f,1.0f);
    specularColor2 = specularColor2Default = new SFFloat(fa[2],0.0f,1.0f);    
  }
  
  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(MATERIAL_ATTR_AMBIENTINTENSITY_NAME);
    if (attr != null)
      ambientIntensity = new SFFloat(attr.getValue(), 0.0f, 1.0f);
    attr = root.getAttribute(MATERIAL_ATTR_SHININESS_NAME);
    if (attr != null)
      shininess = new SFFloat(attr.getValue(), 0.0f, 1.0f);
    attr = root.getAttribute(MATERIAL_ATTR_TRANSPARENCY_NAME);
    if (attr != null)
      transparency = new SFFloat(attr.getValue(), 0.0f, 1.0f);
    String[] fa;
    
    // actual bound is 1 for color values; allow [0..255] HTML values here and test further in MaterialCustomizer
    attr = root.getAttribute(MATERIAL_ATTR_DIFFUSECOLOR_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      diffuseColor0 = new SFFloat(fa[0], 0.0f, 255.0f);
      diffuseColor1 = new SFFloat(fa[1], 0.0f, 255.0f);
      diffuseColor2 = new SFFloat(fa[2], 0.0f, 255.0f);
    }
    attr = root.getAttribute(MATERIAL_ATTR_EMISSIVECOLOR_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      emissiveColor0 = new SFFloat(fa[0], 0.0f, 255.0f);
      emissiveColor1 = new SFFloat(fa[1], 0.0f, 255.0f);
      emissiveColor2 = new SFFloat(fa[2], 0.0f, 255.0f);
    }
    attr = root.getAttribute(MATERIAL_ATTR_SPECULARCOLOR_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      specularColor0 = new SFFloat(fa[0], 0.0f, 255.0f);
      specularColor1 = new SFFloat(fa[1], 0.0f, 255.0f);
      specularColor2 = new SFFloat(fa[2], 0.0f, 255.0f);
    }
  }
  @Override
  public String createAttributes()
  {
    return createAttributes(new materialConstants());
  }
  
  public String createAttributes(materialConstants constants)
  {
    StringBuilder sb = new StringBuilder();
    if(constants.get_ambIntensReqd() || !ambientIntensity.equals(ambientIntensityDefault)) {
      sb.append(" ");
      sb.append(constants.get_ambIntensName());
      sb.append("='");
      sb.append(ambientIntensity);
      sb.append("'");
    }      
    if(constants.get_diffColorReqd() || 
           (!diffuseColor0.equals(diffuseColor0Default) ||
            !diffuseColor1.equals(diffuseColor1Default) ||
            !diffuseColor2.equals(diffuseColor2Default)) ) {
      sb.append(" ");
      sb.append(constants.get_diffColorName());
      sb.append("='");
      sb.append(diffuseColor0);
      sb.append(" ");
      sb.append(diffuseColor1);
      sb.append(" ");
      sb.append(diffuseColor2);
      sb.append("'");       
    }     
    if(constants.get_emisColorReqd() || 
           (!emissiveColor0.equals(emissiveColor0Default) ||
            !emissiveColor1.equals(emissiveColor1Default) ||
            !emissiveColor2.equals(emissiveColor2Default)) ) {
      sb.append(" ");
      sb.append(constants.get_emisColorName());
      sb.append("='");
      sb.append(emissiveColor0);
      sb.append(" ");
      sb.append(emissiveColor1);
      sb.append(" ");
      sb.append(emissiveColor2);
      sb.append("'");       
    }      
    if(constants.get_shininessReqd() || !shininess.equals(shininessDefault)) {
      sb.append(" ");
      sb.append(constants.get_shininessName());
      sb.append("='");
      sb.append(shininess);
      sb.append("'");
    }     
    if(constants.get_specColorReqd() || 
           (!specularColor0.equals(specularColor0Default) ||
            !specularColor1.equals(specularColor1Default) ||
            !specularColor2.equals(specularColor2Default)) ) {
      sb.append(" ");
      sb.append(constants.get_specColorName());
      sb.append("='");
      sb.append(specularColor0);
      sb.append(" ");
      sb.append(specularColor1);
      sb.append(" ");
      sb.append(specularColor2);
      sb.append("'");       
    }      
    if(constants.get_trnsprncyReqd() || !transparency.equals(transparencyDefault)) {
      sb.append(" ");
      sb.append(constants.get_trnsprncyName());
      sb.append("='");
      sb.append(transparency);
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
    this.ambientIntensity = new SFFloat(ambientIntensity, 0.0f, 1.0f);
  }

  public String getShininess()
  {
    return shininess.toString();
  }

  public void setShininess(String shininess)
  {
    this.shininess = new SFFloat(shininess, 0.0f, 1.0f);
  }

  public String getTransparency()
  {
    return transparency.toString();
  }

  public void setTransparency(String transparency)
  {
    this.transparency = new SFFloat(transparency, 0.0f, 1.0f);
  }

  public String getDiffuseColor0()
  {
    return diffuseColor0.toString();
  }

  public void setDiffuseColor0(String diffuseColor0)
  {
    this.diffuseColor0 = new SFFloat(diffuseColor0, 0.0f, 1.0f);
  }

  public String getDiffuseColor1()
  {
    return diffuseColor1.toString();
  }

  public void setDiffuseColor1(String diffuseColor1)
  {
    this.diffuseColor1 = new SFFloat(diffuseColor1, 0.0f, 1.0f);
  }

  public String getDiffuseColor2()
  {
    return diffuseColor2.toString();
  }

  public void setDiffuseColor2(String diffuseColor2)
  {
    this.diffuseColor2 = new SFFloat(diffuseColor2, 0.0f, 1.0f);
  }

  public String getDiffuseColor()
  {
    return diffuseColor0.toString() + " " + diffuseColor1.toString() + " " + diffuseColor2.toString();
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
  public String getSpecularColor0()
  {
    return specularColor0.toString();
  }

  public void setSpecularColor0(String specularColor0)
  {
    this.specularColor0 = new SFFloat(specularColor0, 0.0f, 1.0f);
  }

  public String getSpecularColor1()
  {
    return specularColor1.toString();
  }

  public void setSpecularColor1(String specularColor1)
  {
    this.specularColor1 = new SFFloat(specularColor1, 0.0f, 1.0f);
  }

  public String getSpecularColor2()
  {
    return specularColor2.toString();
  }

  public void setSpecularColor2(String specularColor2)
  {
    this.specularColor2 = new SFFloat(specularColor2, 0.0f, 1.0f);
  }

  public String getSpecularColor()
  {
    return specularColor0.toString() + " " + specularColor1.toString() + " " + specularColor2.toString();
  }
  
    /**
     * @return whether this is a front material for TwoSidedMaterial
     */
    public boolean isFront()
    {
        return front;
    }

    /**
     * @param isFront set whether this is a front material for TwoSidedMaterial
     */
    public void setFront(boolean isFront)
    {
        this.front = isFront;
    }

    /**
     * @return whether this is a back material for TwoSidedMaterial
     */
    public boolean isBack()
    {
        return back;
    }

    /**
     * @param isBack set whether this is a back material for TwoSidedMaterial
     */
    public void setBack(boolean isBack)
    {
        this.back = isBack;
    }
  
  public static class materialConstants
  {
    boolean get_ambIntensReqd() {return MATERIAL_ATTR_AMBIENTINTENSITY_REQD;}
    String  get_ambIntensDflt() {return MATERIAL_ATTR_AMBIENTINTENSITY_DFLT;}
    String  get_ambIntensName() {return MATERIAL_ATTR_AMBIENTINTENSITY_NAME;}
    boolean get_diffColorReqd() {return MATERIAL_ATTR_DIFFUSECOLOR_REQD;}
    String  get_diffColorDflt() {return MATERIAL_ATTR_DIFFUSECOLOR_DFLT;}
    String  get_diffColorName() {return MATERIAL_ATTR_DIFFUSECOLOR_NAME;}
    boolean get_emisColorReqd() {return MATERIAL_ATTR_EMISSIVECOLOR_REQD;}
    String  get_emisColorDflt() {return MATERIAL_ATTR_EMISSIVECOLOR_DFLT;}
    String  get_emisColorName() {return MATERIAL_ATTR_EMISSIVECOLOR_NAME;}
    boolean get_shininessReqd() {return MATERIAL_ATTR_SHININESS_REQD;}
    String  get_shininessDflt() {return MATERIAL_ATTR_SHININESS_DFLT;}
    String  get_shininessName() {return MATERIAL_ATTR_SHININESS_NAME;}
    boolean get_specColorReqd() {return MATERIAL_ATTR_SPECULARCOLOR_REQD;}
    String  get_specColorDflt() {return MATERIAL_ATTR_SPECULARCOLOR_DFLT;}
    String  get_specColorName() {return MATERIAL_ATTR_SPECULARCOLOR_NAME;}
    boolean get_trnsprncyReqd() {return MATERIAL_ATTR_TRANSPARENCY_REQD;}
    String  get_trnsprncyDflt() {return MATERIAL_ATTR_TRANSPARENCY_DFLT;}
    String  get_trnsprncyName() {return MATERIAL_ATTR_TRANSPARENCY_NAME;}
  }
}
