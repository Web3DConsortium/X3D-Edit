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

import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * TWOSIDEDMATERIAL.java
 * Created on Dec. 7, 2007, 1:40 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class TWOSIDEDMATERIAL extends X3DMaterialNode
{
  private MATERIAL frontMaterial,backMaterial;
  private boolean  separateBackColor;
 
  public TWOSIDEDMATERIAL()
  {    
  }

  @Override
  public String getElementName()
  {
    return TWOSIDEDMATERIAL_ELNAME;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return TWOSIDEDMATERIALCustomizer.class;
  }

  @Override
  public void initialize()
  {
    frontMaterial = new MATERIAL();
    frontMaterial.setFront(true);
    frontMaterial.initialize(new twoSidedFrontMaterialConstants());
    backMaterial = new MATERIAL();
    backMaterial.setBack(true);
    backMaterial.initialize(new twoSidedBackMaterialConstants());
    
    separateBackColor = Boolean.parseBoolean(TWOSIDEDMATERIAL_ATTR_SEPARATEBACKCOLOR_DFLT);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(TWOSIDEDMATERIAL_ATTR_AMBIENTINTENSITY_NAME);
    if (attr != null)
      frontMaterial.setAmbientIntensity(attr.getValue());
    attr = root.getAttribute(TWOSIDEDMATERIAL_ATTR_SHININESS_NAME);
    if (attr != null)
      frontMaterial.setShininess(attr.getValue());
    attr = root.getAttribute(TWOSIDEDMATERIAL_ATTR_TRANSPARENCY_NAME);
    if (attr != null)
      frontMaterial.setTransparency(attr.getValue());
    String[] fa;
    attr = root.getAttribute(TWOSIDEDMATERIAL_ATTR_DIFFUSECOLOR_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      frontMaterial.setDiffuseColor0(fa[0]);
      frontMaterial.setDiffuseColor1(fa[1]);
      frontMaterial.setDiffuseColor2(fa[2]);
    }
    attr = root.getAttribute(TWOSIDEDMATERIAL_ATTR_EMISSIVECOLOR_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      frontMaterial.setEmissiveColor0(fa[0]);
      frontMaterial.setEmissiveColor1(fa[1]);
      frontMaterial.setEmissiveColor2(fa[2]);
    }
    attr = root.getAttribute(TWOSIDEDMATERIAL_ATTR_SPECULARCOLOR_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      frontMaterial.setSpecularColor0(fa[0]);
      frontMaterial.setSpecularColor0(fa[1]);
      frontMaterial.setSpecularColor0(fa[2]);
    }
    // back
    attr = root.getAttribute(TWOSIDEDMATERIAL_ATTR_BACK_AMBIENTINTENSITY_NAME);
    if (attr != null)
      backMaterial.setAmbientIntensity(attr.getValue());
    attr = root.getAttribute(TWOSIDEDMATERIAL_ATTR_BACK_SHININESS_NAME);
    if (attr != null)
      backMaterial.setShininess(attr.getValue());
    attr = root.getAttribute(TWOSIDEDMATERIAL_ATTR_BACK_TRANSPARENCY_NAME);
    if (attr != null)
      backMaterial.setTransparency(attr.getValue());

    attr = root.getAttribute(TWOSIDEDMATERIAL_ATTR_BACK_DIFFUSECOLOR_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      backMaterial.setDiffuseColor0(fa[0]);
      backMaterial.setDiffuseColor1(fa[1]);
      backMaterial.setDiffuseColor2(fa[2]);
    }
    attr = root.getAttribute(TWOSIDEDMATERIAL_ATTR_BACK_EMISSIVECOLOR_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      backMaterial.setEmissiveColor0(fa[0]);
      backMaterial.setEmissiveColor1(fa[1]);
      backMaterial.setEmissiveColor2(fa[2]);
    }
    attr = root.getAttribute(TWOSIDEDMATERIAL_ATTR_BACK_SPECULARCOLOR_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      backMaterial.setSpecularColor0(fa[0]);
      backMaterial.setSpecularColor0(fa[1]);
      backMaterial.setSpecularColor0(fa[2]);
    }
    attr = root.getAttribute(TWOSIDEDMATERIAL_ATTR_SEPARATEBACKCOLOR_NAME);
    if (attr != null) {
      separateBackColor = Boolean.parseBoolean(attr.getValue());
    }

    String twoSidedMaterialContent = this.getContent();
    String firstString, remainderString, secondString = new String();
    // first or both strings present
    if (twoSidedMaterialContent.contains("frontMaterial"))
    {
               firstString = this.getContent().substring(this.getContent().indexOf("<!--"), this.getContent().indexOf("-->")+3);
           remainderString = this.getContent().substring(this.getContent().indexOf(firstString)+firstString.length());
         if ((remainderString.length() > 0) && remainderString.contains("-->"))
              secondString =   remainderString.substring(  remainderString.indexOf("<!--"),   remainderString.indexOf("-->")+3);
         else
              secondString = "";
    }
    else if (twoSidedMaterialContent.contains("-->")) // second string present
    {
         firstString     = "";
         secondString    = this.getContent().substring(this.getContent().indexOf("<!--"),this.getContent().indexOf("-->")+3);
    }
    else
    {
         firstString     = "";
         secondString    = "";
    }
    frontMaterial.setContent(firstString);
     backMaterial.setContent(secondString);
  }
  
    @Override
  public String createAttributes()
  {
    StringBuffer sb = new StringBuffer();

    // editing pane does not canonicalize output order, since more error prone and less readable...
    
    if(TWOSIDEDMATERIAL_ATTR_SEPARATEBACKCOLOR_REQD ||
        separateBackColor != Boolean.parseBoolean(TWOSIDEDMATERIAL_ATTR_SEPARATEBACKCOLOR_DFLT)) {
      sb.append(" ");
      sb.append(TWOSIDEDMATERIAL_ATTR_SEPARATEBACKCOLOR_NAME);
      sb.append("='");
      sb.append(separateBackColor);
      sb.append("'");
    }
    sb.append(frontMaterial.createAttributes(new twoSidedFrontMaterialConstants()));
    sb.append( backMaterial.createAttributes(new twoSidedBackMaterialConstants()));

    return sb.toString();  
  }
  
  public MATERIAL getBackMaterial()
  {
    return backMaterial;
  }

  public void setBackMaterial(MATERIAL back)
  {
    this.backMaterial = back;
  }

  public MATERIAL getFrontMaterial()
  {
    return frontMaterial;
  }

  public void setFrontMaterial(MATERIAL front)
  {
    this.frontMaterial = front;
  }

  public boolean isSeparateBackColor()
  {
    return separateBackColor;
  }

  public void setSeparateBackColor(boolean separateBackColor)
  {
    this.separateBackColor = separateBackColor;
  }
  
  public static class twoSidedFrontMaterialConstants extends MATERIAL.materialConstants
  {
    boolean get_ambIntensReqd() {return TWOSIDEDMATERIAL_ATTR_AMBIENTINTENSITY_REQD;}
    String  get_ambIntensDflt() {return TWOSIDEDMATERIAL_ATTR_AMBIENTINTENSITY_DFLT;}
    String  get_ambIntensName() {return TWOSIDEDMATERIAL_ATTR_AMBIENTINTENSITY_NAME;}
    boolean get_diffColorReqd() {return TWOSIDEDMATERIAL_ATTR_DIFFUSECOLOR_REQD;}
    String  get_diffColorDflt() {return TWOSIDEDMATERIAL_ATTR_DIFFUSECOLOR_DFLT;}
    String  get_diffColorName() {return TWOSIDEDMATERIAL_ATTR_DIFFUSECOLOR_NAME;}
    boolean get_emisColorReqd() {return TWOSIDEDMATERIAL_ATTR_EMISSIVECOLOR_REQD;}
    String  get_emisColorDflt() {return TWOSIDEDMATERIAL_ATTR_EMISSIVECOLOR_DFLT;}
    String  get_emisColorName() {return TWOSIDEDMATERIAL_ATTR_EMISSIVECOLOR_NAME;}
    boolean get_shininessReqd() {return TWOSIDEDMATERIAL_ATTR_SHININESS_REQD;}
    String  get_shininessDflt() {return TWOSIDEDMATERIAL_ATTR_SHININESS_DFLT;}
    String  get_shininessName() {return TWOSIDEDMATERIAL_ATTR_SHININESS_NAME;}
    boolean get_specColorReqd() {return TWOSIDEDMATERIAL_ATTR_SPECULARCOLOR_REQD;}
    String  get_specColorDflt() {return TWOSIDEDMATERIAL_ATTR_SPECULARCOLOR_DFLT;}
    String  get_specColorName() {return TWOSIDEDMATERIAL_ATTR_SPECULARCOLOR_NAME;}
    boolean get_trnsprncyReqd() {return TWOSIDEDMATERIAL_ATTR_TRANSPARENCY_REQD;}
    String  get_trnsprncyDflt() {return TWOSIDEDMATERIAL_ATTR_TRANSPARENCY_DFLT;}
    String  get_trnsprncyName() {return TWOSIDEDMATERIAL_ATTR_TRANSPARENCY_NAME;}
  }
  
  public static class twoSidedBackMaterialConstants extends MATERIAL.materialConstants
  {
    boolean get_ambIntensReqd() {return TWOSIDEDMATERIAL_ATTR_BACK_AMBIENTINTENSITY_REQD;}
    String  get_ambIntensDflt() {return TWOSIDEDMATERIAL_ATTR_BACK_AMBIENTINTENSITY_DFLT;}
    String  get_ambIntensName() {return TWOSIDEDMATERIAL_ATTR_BACK_AMBIENTINTENSITY_NAME;}
    boolean get_diffColorReqd() {return TWOSIDEDMATERIAL_ATTR_BACK_DIFFUSECOLOR_REQD;}
    String  get_diffColorDflt() {return TWOSIDEDMATERIAL_ATTR_BACK_DIFFUSECOLOR_DFLT;}
    String  get_diffColorName() {return TWOSIDEDMATERIAL_ATTR_BACK_DIFFUSECOLOR_NAME;}
    boolean get_emisColorReqd() {return TWOSIDEDMATERIAL_ATTR_BACK_EMISSIVECOLOR_REQD;}
    String  get_emisColorDflt() {return TWOSIDEDMATERIAL_ATTR_BACK_EMISSIVECOLOR_DFLT;}
    String  get_emisColorName() {return TWOSIDEDMATERIAL_ATTR_BACK_EMISSIVECOLOR_NAME;}
    boolean get_shininessReqd() {return TWOSIDEDMATERIAL_ATTR_BACK_SHININESS_REQD;}
    String  get_shininessDflt() {return TWOSIDEDMATERIAL_ATTR_BACK_SHININESS_DFLT;}
    String  get_shininessName() {return TWOSIDEDMATERIAL_ATTR_BACK_SHININESS_NAME;}
    boolean get_specColorReqd() {return TWOSIDEDMATERIAL_ATTR_BACK_SPECULARCOLOR_REQD;}
    String  get_specColorDflt() {return TWOSIDEDMATERIAL_ATTR_BACK_SPECULARCOLOR_DFLT;}
    String  get_specColorName() {return TWOSIDEDMATERIAL_ATTR_BACK_SPECULARCOLOR_NAME;}
    boolean get_trnsprncyReqd() {return TWOSIDEDMATERIAL_ATTR_BACK_TRANSPARENCY_REQD;}
    String  get_trnsprncyDflt() {return TWOSIDEDMATERIAL_ATTR_BACK_TRANSPARENCY_DFLT;}
    String  get_trnsprncyName() {return TWOSIDEDMATERIAL_ATTR_BACK_TRANSPARENCY_NAME;}
  }
}   

