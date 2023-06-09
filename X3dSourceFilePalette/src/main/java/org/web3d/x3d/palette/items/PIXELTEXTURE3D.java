/*
Copyright (c) 1995-2021 held by the author(s).  All rights reserved.
 
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

import org.web3d.x3d.types.X3DTexture3DNode;
import javax.swing.text.JTextComponent;

import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * PIXELTEXTURE3D.java
 * Created on 20 November 2011
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class PIXELTEXTURE3D extends X3DTexture3DNode
{
  private SFInt32   numWidth,  numWidthDefault;
  private SFInt32   numHeight, numHeightDefault;
  private SFInt32   numDepth, numDepthDefault;
  private SFInt32   numColor,  numColorDefault;
  private String  imageData, imageDataDefault;
  
  public PIXELTEXTURE3D()
  {
  }

  @Override
  public String getElementName()
  {
    return PIXELTEXTURE3D_ELNAME;
  }
  
  @Override
  public void initialize()
  {
    repeatS = Boolean.parseBoolean(PIXELTEXTURE3D_ATTR_REPEATS_DFLT);
    repeatT = Boolean.parseBoolean(PIXELTEXTURE3D_ATTR_REPEATT_DFLT);
    repeatR = Boolean.parseBoolean(PIXELTEXTURE3D_ATTR_REPEATR_DFLT);
    
    String[] fa = parseX(PIXELTEXTURE3D_ATTR_IMAGE_DFLT);
    
    numWidth  = numWidthDefault  = new SFInt32(fa[0],0,null);
    numHeight = numHeightDefault = new SFInt32(fa[1],0,null);
    numDepth  = numDepthDefault  = new SFInt32(fa[2],0,null);
    numColor  = numColorDefault  = new SFInt32(fa[3],0,null);
    
    imageData = imageDataDefault = concatStringArray(fa,4); // skip first 4
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(PIXELTEXTURE3D_ATTR_REPEATS_NAME);
    if (attr != null)
      repeatS = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(PIXELTEXTURE3D_ATTR_REPEATT_NAME);
    if (attr != null)
      repeatT = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(PIXELTEXTURE3D_ATTR_REPEATR_NAME);
    if (attr != null)
      repeatR = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(PIXELTEXTURE3D_ATTR_IMAGE_NAME);
    if (attr != null) {
      String[] fa = parseX(attr.getValue());

      numWidth  = new SFInt32(fa[0], 0, null);
      numHeight = new SFInt32(fa[1], 0, null);
      numDepth  = new SFInt32(fa[2], 0, null);
      numColor  = new SFInt32(fa[3], 0, null);

      imageData = concatStringArray(fa, 4); // skip first 4
    }
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return PIXELTEXTURE3DCustomizer.class;
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    
    if (PIXELTEXTURE3D_ATTR_REPEATS_REQD || repeatS != Boolean.parseBoolean(PIXELTEXTURE3D_ATTR_REPEATS_DFLT)) {
      sb.append(" ");
      sb.append(PIXELTEXTURE3D_ATTR_REPEATS_NAME);
      sb.append("='");
      sb.append(repeatS);
      sb.append("'");
    }
    if (PIXELTEXTURE3D_ATTR_REPEATT_REQD || repeatT != Boolean.parseBoolean(PIXELTEXTURE3D_ATTR_REPEATT_DFLT)) {
      sb.append(" ");
      sb.append(PIXELTEXTURE3D_ATTR_REPEATT_NAME);
      sb.append("='");
      sb.append(repeatT);
      sb.append("'");
    }
    if (PIXELTEXTURE3D_ATTR_REPEATR_REQD || repeatR != Boolean.parseBoolean(PIXELTEXTURE3D_ATTR_REPEATR_DFLT)) {
      sb.append(" ");
      sb.append(PIXELTEXTURE3D_ATTR_REPEATR_NAME);
      sb.append("='");
      sb.append(repeatR);
      sb.append("'");
    }
    if (PIXELTEXTURE3D_ATTR_IMAGE_REQD ||
            !numWidth.equals(numWidthDefault) ||
            !numHeight.equals(numHeightDefault) ||
            !numDepth.equals(numDepthDefault) ||
            !numColor.equals(numColorDefault) ||
            !imageData.equals(imageDataDefault))
    {
      sb.append(" ");
      sb.append(PIXELTEXTURE3D_ATTR_IMAGE_NAME);
      sb.append("='");
      sb.append(concatStringArray(new String[]{" " + numWidth, " " + numHeight, " " + numDepth, " " + numColor}));
      if (imageData.length() > 0) {
        if (imageData.contains("\n")) // rows if zeroed
             sb.append("\n");
        else sb.append(" ");
        sb.append(imageData);
      }
      sb.append("'");
    }

    return sb.toString();
  }

  public String getNumWidth()
  {
    return numWidth.toString();
  }

  public int getWidth()
  {
    return numWidth.getValue();
  }
  
  public void setNumWidth(String s)
  {
    this.numWidth = new SFInt32(s,0,null);
  }
  
  public String getNumHeight()
  {
    return numHeight.toString();
  }

  public int getHeight()
  {
    return numHeight.getValue();
  }
  
  public void setNumHeight(String s)
  {
    this.numHeight = new SFInt32(s,0,null);
  }

  public String getNumDepth()
  {
    return numDepth.toString();
  }

  public int getDepth()
  {
    return numDepth.getValue();
  }
  
  public void setNumDepth(String s)
  {
    this.numDepth = new SFInt32(s,0,null);
  }
  
  public String getNumColor()
  {
    return numColor.toString();
  }
  
  public void setNumColor(String s)
  {
    if (s.equalsIgnoreCase("") || s.equalsIgnoreCase("-1"))
         this.numColor = new SFInt32("0",0,null);
    else this.numColor = new SFInt32(s,0,null);
  }
  
  public void setImageData(String s)
  {
    this.imageData = s.trim();
  }
  
  public String getImageData()
  {
    return imageData;
  }
}
