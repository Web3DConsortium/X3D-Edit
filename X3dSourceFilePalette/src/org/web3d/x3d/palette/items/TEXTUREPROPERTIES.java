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
import org.web3d.x3d.types.X3DAppearanceChildNode;

import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * TEXTUREPROPERTIES.java
 * Created on July 12, 2007, 3:05 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class TEXTUREPROPERTIES extends X3DAppearanceChildNode // needs new X3D*Node class
{
  private SFFloat anisotropicDegree, anisotropicDegreeDefault;

  private SFFloat borderColor0,  borderColor0Default;
  private SFFloat borderColor1,  borderColor1Default;
  private SFFloat borderColor2,  borderColor2Default;
  private SFFloat borderColor3,  borderColor3Default;

  private SFInt32 borderWidth,   borderWidthDefault;

  private String  boundaryModeS, boundaryModeSDefault;
  private String  boundaryModeT, boundaryModeTDefault;
  private String  boundaryModeR, boundaryModeRDefault;

  private String  magnificationFilter, magnificationFilterDefault;
  private String  minificationFilter,  minificationFilterDefault;
  private String  textureCompression,  textureCompressionDefault;

  private SFFloat texturePriority,   texturePriorityDefault;
  
  private boolean generateMipMaps;
  
  public TEXTUREPROPERTIES()
  {
  }

  @Override
  public String getDefaultContainerField()
  {
    return "textureProperties";        // Should be handled in the X3D*Node hierarchy
  }

  @Override
  public String getElementName()
  {
    return TEXTUREPROPERTIES_ELNAME;
  }

  @Override
  public void initialize()
  {
    anisotropicDegree = anisotropicDegreeDefault = new SFFloat(TEXTUREPROPERTIES_ATTR_ANISOTROPICDEGREE_DFLT, 1.0f, null);

    String[] fa = parse4(TEXTUREPROPERTIES_ATTR_BORDERCOLOR_DFLT);
    borderColor0 = borderColor0Default = new SFFloat(fa[0],0.0f,1.0f);
    borderColor1 = borderColor1Default = new SFFloat(fa[1],0.0f,1.0f);
    borderColor2 = borderColor2Default = new SFFloat(fa[2],0.0f,1.0f);
    borderColor3 = borderColor3Default = new SFFloat(fa[3],0.0f,1.0f);

    borderWidth  = borderWidthDefault = new SFInt32(TEXTUREPROPERTIES_ATTR_BORDERWIDTH_DFLT, 0, 1);

    boundaryModeS = boundaryModeSDefault = TEXTUREPROPERTIES_ATTR_BOUNDARYMODE_S_DFLT;
    boundaryModeT = boundaryModeTDefault = TEXTUREPROPERTIES_ATTR_BOUNDARYMODE_T_DFLT;
    boundaryModeR = boundaryModeRDefault = TEXTUREPROPERTIES_ATTR_BOUNDARYMODE_R_DFLT;

    magnificationFilter = magnificationFilterDefault = TEXTUREPROPERTIES_ATTR_MAGNIFICATIONFILTER_DFLT;
    minificationFilter  = minificationFilterDefault  = TEXTUREPROPERTIES_ATTR_MINIFICATIONFILTER_DFLT;
    textureCompression  = textureCompressionDefault  = TEXTUREPROPERTIES_ATTR_TEXTURECOMPRESSION_DFLT;

    texturePriority   =   texturePriorityDefault = new SFFloat(TEXTUREPROPERTIES_ATTR_TEXTUREPRIORITY_DFLT, 0.0f, 1.0f);

    generateMipMaps  = Boolean.parseBoolean(TEXTUREPROPERTIES_ATTR_GENERATEMIPMAPS_DFLT);
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return TEXTUREPROPERTIESCustomizer.class;
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);

    org.jdom.Attribute attr = root.getAttribute(TEXTUREPROPERTIES_ATTR_ANISOTROPICDEGREE_NAME);
    if (attr != null)
      anisotropicDegree = new SFFloat(attr.getValue(), 1.0f, null);
    attr = root.getAttribute(TEXTUREPROPERTIES_ATTR_TEXTUREPRIORITY_NAME);
    if (attr != null)
      texturePriority = new SFFloat(attr.getValue(), 0.0f, 1.0f);

    attr = root.getAttribute(TEXTUREPROPERTIES_ATTR_BORDERCOLOR_NAME);
    if (attr != null) {
      String[] fa = parse4(attr.getValue());
      borderColor0 = new SFFloat(fa[0], 0.0f, 1.0f);
      borderColor1 = new SFFloat(fa[1], 0.0f, 1.0f);
      borderColor2 = new SFFloat(fa[2], 0.0f, 1.0f);
      borderColor3 = new SFFloat(fa[3], 0.0f, 1.0f);
    }
    attr = root.getAttribute(TEXTUREPROPERTIES_ATTR_BORDERWIDTH_NAME);
    if (attr != null)
      borderWidth = new SFInt32(attr.getValue(), 0, 1);

    attr = root.getAttribute(TEXTUREPROPERTIES_ATTR_BOUNDARYMODE_S_NAME);
    if(attr != null)
        setBoundaryModeS(attr.getValue());
    attr = root.getAttribute(TEXTUREPROPERTIES_ATTR_BOUNDARYMODE_T_NAME);
    if(attr != null)
        setBoundaryModeT(attr.getValue());
    attr = root.getAttribute(TEXTUREPROPERTIES_ATTR_BOUNDARYMODE_R_NAME);
    if(attr != null)
        setBoundaryModeR(attr.getValue());

    attr = root.getAttribute(TEXTUREPROPERTIES_ATTR_MAGNIFICATIONFILTER_NAME);
    if(attr != null)
        setMagnificationFilter(attr.getValue());
    attr = root.getAttribute(TEXTUREPROPERTIES_ATTR_MINIFICATIONFILTER_NAME);
    if(attr != null)
        setMinificationFilter(attr.getValue());
    attr = root.getAttribute(TEXTUREPROPERTIES_ATTR_TEXTURECOMPRESSION_NAME);
    if(attr != null)
        setTextureCompression(attr.getValue());

    attr = root.getAttribute(TEXTUREPROPERTIES_ATTR_GENERATEMIPMAPS_NAME);
    if (attr != null)
      generateMipMaps = Boolean.parseBoolean(attr.getValue());
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (TEXTUREPROPERTIES_ATTR_ANISOTROPICDEGREE_REQD || !anisotropicDegree.equals(anisotropicDegreeDefault)) {
      sb.append(" ");
      sb.append(TEXTUREPROPERTIES_ATTR_ANISOTROPICDEGREE_NAME);
      sb.append("='");
      sb.append(anisotropicDegree);
      sb.append("'");
    }
    if (TEXTUREPROPERTIES_ATTR_BORDERCOLOR_REQD ||
           (!borderColor0.equals(borderColor0Default) ||
            !borderColor1.equals(borderColor1Default) ||
            !borderColor2.equals(borderColor2Default) ||
            !borderColor3.equals(borderColor3Default))) {
      sb.append(" ");
      sb.append(TEXTUREPROPERTIES_ATTR_BORDERCOLOR_NAME);
      sb.append("='");
      sb.append(borderColor0);
      sb.append(" ");
      sb.append(borderColor1);
      sb.append(" ");
      sb.append(borderColor2);
      sb.append(" ");
      sb.append(borderColor3);
      sb.append("'");
    }
    if (TEXTUREPROPERTIES_ATTR_BORDERWIDTH_REQD || !borderWidth.equals(borderWidthDefault)) {
      sb.append(" ");
      sb.append(TEXTUREPROPERTIES_ATTR_BORDERWIDTH_NAME);
      sb.append("='");
      sb.append(borderWidth);
      sb.append("'");
    }
    if (TEXTUREPROPERTIES_ATTR_BOUNDARYMODE_S_REQD || !boundaryModeS.equals(boundaryModeSDefault)) {
      sb.append(" ");
      sb.append(TEXTUREPROPERTIES_ATTR_BOUNDARYMODE_S_NAME);
      sb.append("='");
      sb.append(boundaryModeS);
      sb.append("'");
    }
    if (TEXTUREPROPERTIES_ATTR_BOUNDARYMODE_T_REQD || !boundaryModeT.equals(boundaryModeTDefault)) {
      sb.append(" ");
      sb.append(TEXTUREPROPERTIES_ATTR_BOUNDARYMODE_T_NAME);
      sb.append("='");
      sb.append(boundaryModeT);
      sb.append("'");
    }
    if (TEXTUREPROPERTIES_ATTR_BOUNDARYMODE_R_REQD || !boundaryModeR.equals(boundaryModeRDefault)) {
      sb.append(" ");
      sb.append(TEXTUREPROPERTIES_ATTR_BOUNDARYMODE_R_NAME);
      sb.append("='");
      sb.append(boundaryModeR);
      sb.append("'");
    }
    if (TEXTUREPROPERTIES_ATTR_GENERATEMIPMAPS_REQD || generateMipMaps != Boolean.parseBoolean(TEXTUREPROPERTIES_ATTR_GENERATEMIPMAPS_DFLT)) {
      sb.append(" ");
      sb.append(TEXTUREPROPERTIES_ATTR_GENERATEMIPMAPS_NAME);
      sb.append("='");
      sb.append(generateMipMaps);
      sb.append("'");
    }
    if (TEXTUREPROPERTIES_ATTR_MAGNIFICATIONFILTER_REQD || !magnificationFilter.equals(magnificationFilterDefault)) {
      sb.append(" ");
      sb.append(TEXTUREPROPERTIES_ATTR_MAGNIFICATIONFILTER_NAME);
      sb.append("='");
      sb.append(magnificationFilter);
      sb.append("'");
    }
    if (TEXTUREPROPERTIES_ATTR_MINIFICATIONFILTER_REQD || !minificationFilter.equals(minificationFilterDefault)) {
      sb.append(" ");
      sb.append(TEXTUREPROPERTIES_ATTR_MINIFICATIONFILTER_NAME);
      sb.append("='");
      sb.append(minificationFilter);
      sb.append("'");
    }
    if (TEXTUREPROPERTIES_ATTR_TEXTURECOMPRESSION_REQD || !textureCompression.equals(textureCompressionDefault)) {
      sb.append(" ");
      sb.append(TEXTUREPROPERTIES_ATTR_TEXTURECOMPRESSION_NAME);
      sb.append("='");
      sb.append(textureCompression);
      sb.append("'");
    }
    if (TEXTUREPROPERTIES_ATTR_TEXTUREPRIORITY_REQD || !texturePriority.equals(texturePriorityDefault)) {
      sb.append(" ");
      sb.append(TEXTUREPROPERTIES_ATTR_TEXTUREPRIORITY_NAME);
      sb.append("='");
      sb.append(texturePriority);
      sb.append("'");
    }
    return sb.toString();
  }

  public String getAnisotropicDegree()
  {
    return anisotropicDegree.toString();
  }

  public void setAnisotropicDegree(String anisotropicDegree)
  {
    this.anisotropicDegree = new SFFloat(anisotropicDegree, 1.0f, null);
  }

  public String getTexturePriority()
  {
    return texturePriority.toString();
  }

  public void setTexturePriority(String texturePriority)
  {
    this.texturePriority = new SFFloat(texturePriority, 0.0f, 1.0f);
  }

  public String getMagnificationFilter()
  {
    return magnificationFilter;
  }

  public void setMinificationFilter(String minificationFilter)
  {
    this.minificationFilter = minificationFilter;
  }

  public String getMinificationFilter()
  {
    return minificationFilter;
  }

  public void setMagnificationFilter(String magnificationFilter)
  {
    this.magnificationFilter = magnificationFilter;
  }

  public String getTextureCompression()
  {
    return textureCompression;
  }

  public void setTextureCompression(String textureCompression)
  {
    this.textureCompression = textureCompression;
  }

  public boolean isGenerateMipMaps()
  {
    return generateMipMaps;
  }

  public void setGenerateMipMaps(boolean generateMipMaps)
  {
    this.generateMipMaps = generateMipMaps;
  }

  public int getBorderWidth()
  {
    return borderWidth.getValue();
  }

  public void setBorderWidth(int borderWidth)
  {
    this.borderWidth.setValue(borderWidth);
  }

  public String getBoundaryModeS()
  {
    return boundaryModeS;
  }

  public void setBoundaryModeS(String boundaryMode)
  {
    this.boundaryModeS = boundaryMode;
  }

  public String getBoundaryModeT()
  {
    return boundaryModeT;
  }

  public void setBoundaryModeT(String boundaryMode)
  {
    this.boundaryModeT = boundaryMode;
  }

  public String getBoundaryModeR()
  {
    return boundaryModeR;
  }

  public void setBoundaryModeR(String boundaryMode)
  {
    this.boundaryModeR = boundaryMode;
  }

  public String getColor0()
  {
    return borderColor0.toString();
  }

  public void setColor0(String color0)
  {
    this.borderColor0 = new SFFloat( color0,0.0f,1.0f);
  }

  public String getColor1()
  {
    return borderColor1.toString();
  }

  public void setColor1(String color1)
  {
    this.borderColor1 = new SFFloat( color1,0.0f,1.0f);
  }

  public String getColor2()
  {
    return borderColor2.toString();
  }

  public void setColor2(String color2)
  {
    this.borderColor2 = new SFFloat( color2,0.0f,1.0f);
  }

  public String getColor3()
  {
    return borderColor3.toString();
  }

  public void setColor3(String color3)
  {
    this.borderColor3 = new SFFloat( color3,0.0f,1.0f);
  }
}
