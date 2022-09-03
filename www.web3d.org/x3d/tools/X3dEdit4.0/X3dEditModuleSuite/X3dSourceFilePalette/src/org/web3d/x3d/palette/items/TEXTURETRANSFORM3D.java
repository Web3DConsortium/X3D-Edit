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
import org.web3d.x3d.types.X3DTextureTransform2DNode;

import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * TEXTURETRANSFORM3D.java
 * Created on Sep 10, 2007, 3:05 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class TEXTURETRANSFORM3D extends X3DTextureTransform2DNode
{
  SFFloat centerX,centerXDefault;
  SFFloat centerY,centerYDefault;
  SFFloat centerZ,centerZDefault;
  SFFloat rotationX,rotationXDefault;
  SFFloat rotationY,rotationYDefault;
  SFFloat rotationZ,rotationZDefault;
  SFFloat rotationAngle,rotationAngleDefault;
  SFFloat translationX,translationXDefault;
  SFFloat translationY,translationYDefault;
  SFFloat translationZ,translationZDefault;
  SFFloat scaleX,scaleXDefault;
  SFFloat scaleY,scaleYDefault;
  SFFloat scaleZ,scaleZDefault;
   
  public TEXTURETRANSFORM3D()
  {
  }

  @Override
  public String getElementName()
  {
    return TEXTURETRANSFORM3D_ELNAME;
  }
  
  @Override
  @SuppressWarnings("NestedAssignment")
  public void initialize()
  {
    String[] fa = parse3(TEXTURETRANSFORM3D_ATTR_CENTER_DFLT);
    centerX = centerXDefault = new SFFloat(fa[0],null,null);
    centerY = centerYDefault = new SFFloat(fa[1],null,null);
    centerZ = centerZDefault = new SFFloat(fa[2],null,null);
    
    String[] sa = parse4(TEXTURETRANSFORM3D_ATTR_ROTATION_DFLT);
    rotationX = rotationXDefault = new SFFloat(sa[0],null,null);
    rotationY = rotationYDefault = new SFFloat(sa[1],null,null);
    rotationZ = rotationZDefault = new SFFloat(sa[2],null,null);
    rotationAngle = rotationAngleDefault = new SFFloat(sa[3],null,null);
    
    fa = parse3(TEXTURETRANSFORM3D_ATTR_TRANSLATION_DFLT);
    translationX = translationXDefault = new SFFloat(fa[0],null,null);
    translationY = translationYDefault = new SFFloat(fa[1],null,null);
    translationZ = translationZDefault = new SFFloat(fa[2],null,null);
    
    fa = parse3(TEXTURETRANSFORM3D_ATTR_SCALE_DFLT);
    scaleX = scaleXDefault = new SFFloat(fa[0],null,null);
    scaleY = scaleYDefault = new SFFloat(fa[1],null,null);
    scaleZ = scaleZDefault = new SFFloat(fa[2],null,null);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;
    String[] fa;
    
    attr = root.getAttribute(TEXTURETRANSFORM3D_ATTR_CENTER_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      centerX = new SFFloat(fa[0],null,null);
      centerY = new SFFloat(fa[1],null,null);
      centerZ = new SFFloat(fa[2],null,null);
    }
    attr = root.getAttribute(TEXTURETRANSFORM3D_ATTR_ROTATION_NAME);
    if (attr != null)
    {
        String[] sa = parse4(attr.getValue());   
        rotationX = new SFFloat(sa[0],null,null);
        rotationY = new SFFloat(sa[1],null,null);
        rotationZ = new SFFloat(sa[2],null,null);
        rotationAngle = new SFFloat(sa[3],null,null);
    }
    attr = root.getAttribute(TEXTURETRANSFORM3D_ATTR_TRANSLATION_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      translationX = new SFFloat(fa[0],null,null);
      translationY = new SFFloat(fa[1],null,null);
      translationZ = new SFFloat(fa[1],null,null);
    }
    attr = root.getAttribute(TEXTURETRANSFORM3D_ATTR_SCALE_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      scaleX = new SFFloat(fa[0],null,null);
      scaleY = new SFFloat(fa[1],null,null);
      scaleZ = new SFFloat(fa[1],null,null);
    }
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return TEXTURETRANSFORM3DCustomizer.class;
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    if (TEXTURETRANSFORM3D_ATTR_CENTER_REQD ||
            !centerX.equals(centerXDefault) ||
            !centerY.equals(centerYDefault))
    {
      sb.append(" ");
      sb.append(TEXTURETRANSFORM3D_ATTR_CENTER_NAME);
      sb.append("='");
      sb.append(centerX);
      sb.append(" ");
      sb.append(centerY);
      sb.append(" ");
      sb.append(centerZ);
      sb.append("'");
    }
    if (TEXTURETRANSFORM3D_ATTR_ROTATION_REQD || 
            !(rotationX.equals(rotationXDefault) && 
              rotationY.equals(rotationYDefault) && 
              rotationZ.equals(rotationZDefault) && 
              rotationAngle.equals(rotationAngleDefault))) {
      sb.append(" ");
      sb.append(TEXTURETRANSFORM3D_ATTR_ROTATION_NAME);
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
    if (TEXTURETRANSFORM3D_ATTR_SCALE_REQD ||
            !scaleX.equals(scaleXDefault) ||
            !scaleY.equals(scaleYDefault))
    {
      sb.append(" ");
      sb.append(TEXTURETRANSFORM3D_ATTR_SCALE_NAME);
      sb.append("='");
      sb.append(scaleX);
      sb.append(" ");
      sb.append(scaleY);
      sb.append(" ");
      sb.append(scaleZ);
      sb.append("'");
    }
    if (TEXTURETRANSFORM3D_ATTR_TRANSLATION_REQD ||
            !translationX.equals(translationXDefault) ||
            !translationY.equals(translationYDefault))
    {
      sb.append(" ");
      sb.append(TEXTURETRANSFORM3D_ATTR_TRANSLATION_NAME);
      sb.append("='");
      sb.append(translationX);
      sb.append(" ");
      sb.append(translationY);
      sb.append(" ");
      sb.append(translationZ);
      sb.append("'");
    }
    return sb.toString();
  }

  public String getCenterX()
  {
    return centerX.toString();
  }
  
  public void setCenterX(String s)
  {
    centerX = new SFFloat(s,null,null);
  }

  public String getCenterY()
  {
    return centerY.toString();
  }
  
  public void setCenterY(String s)
  {
    centerY = new SFFloat(s,null,null);
  }

  public String getCenterZ()
  {
    return centerZ.toString();
  }
  
  public void setCenterZ(String s)
  {
    centerZ = new SFFloat(s,null,null);
  }

  public String getRotationX()
  {
    return rotationX.toString();
  }
  
  public void setRotationX(String s)
  {
    rotationX = new SFFloat(s,null,null);
  }

  public String getRotationY()
  {
    return rotationY.toString();
  }
  
  public void setRotationY(String s)
  {
    rotationY = new SFFloat(s,null,null);
  }

  public String getRotationZ()
  {
    return rotationZ.toString();
  }
  
  public void setRotationZ(String s)
  {
    rotationZ = new SFFloat(s,null,null);
  }

  public String getRotationAngle()
  {
    return rotationAngle.toString();
  }
  
  public void setRotationAngle(String s)
  {
    rotationAngle = new SFFloat(s,null,null);
  }

  public String getTranslationX()
  {
    return translationX.toString();
  }
  
  public void setTranslationX(String s)
  {
    translationX = new SFFloat(s,null,null);
  }

  public String getTranslationY()
  {
    return translationY.toString();
  }
  
  public void setTranslationY(String s)
  {
    translationY = new SFFloat(s,null,null);
  }

  public String getTranslationZ()
  {
    return translationZ.toString();
  }
  
  public void setTranslationZ(String s)
  {
    translationZ = new SFFloat(s,null,null);
  }

  public String getScaleX()
  {
    return scaleX.toString();
  }
  
  public void setScaleX(String s)
  {
    scaleX = new SFFloat(s,null,null);
  }

  public String getScaleY()
  {
    return scaleY.toString();
  }
  
  public void setScaleY(String s)
  {
    scaleY = new SFFloat(s,null,null);
  }

  public String getScaleZ()
  {
    return scaleZ.toString();
  }
  
  public void setScaleZ(String s)
  {
    scaleZ = new SFFloat(s,null,null);
  }
}
