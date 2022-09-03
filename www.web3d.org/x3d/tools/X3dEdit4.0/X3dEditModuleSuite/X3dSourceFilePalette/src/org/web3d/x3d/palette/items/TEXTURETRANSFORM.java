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
import org.web3d.x3d.types.X3DPrimitiveTypes.SFFloat;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;
import org.web3d.x3d.types.X3DTextureTransform2DNode;

/**
 * TEXTURETRANSFORM.java
 * Created on Sep 10, 2007, 3:05 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class TEXTURETRANSFORM extends X3DTextureTransform2DNode
{
  SFFloat center0,center0Default;
  SFFloat center1,center1Default;
  SFFloat rotation,rotationDefault;
  SFFloat translation0,translation0Default;
  SFFloat translation1,translation1Default;
  SFFloat scale0,scale0Default;
  SFFloat scale1,scale1Default;
   
  public TEXTURETRANSFORM()
  {
  }

  @Override
  public String getElementName()
  {
    return TEXTURETRANSFORM_ELNAME;
  }
  
  @Override
  @SuppressWarnings("NestedAssignment")
  public void initialize()
  {
    String[] fa = parse2(TEXTURETRANSFORM_ATTR_CENTER_DFLT);
    center0 = center0Default = new SFFloat(fa[0],null,null);
    center1 = center1Default = new SFFloat(fa[1],null,null);
    
    rotation = rotationDefault = new SFFloat(TEXTURETRANSFORM_ATTR_ROTATION_DFLT,null,null);
    
    fa = parse2(TEXTURETRANSFORM_ATTR_TRANSLATION_DFLT);
    translation0 = translation0Default = new SFFloat(fa[0],null,null);
    translation1 = translation1Default = new SFFloat(fa[1],null,null);
    
    fa = parse2(TEXTURETRANSFORM_ATTR_SCALE_DFLT);
    scale0 = scale0Default = new SFFloat(fa[0],null,null);
    scale1 = scale1Default = new SFFloat(fa[1],null,null);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;
    String[] fa;
    
    attr = root.getAttribute(TEXTURETRANSFORM_ATTR_CENTER_NAME);
    if (attr != null) {
      fa = parse2(attr.getValue());
      center0 = new SFFloat(fa[0],null,null);
      center1 = new SFFloat(fa[1],null,null);
    }
    attr = root.getAttribute(TEXTURETRANSFORM_ATTR_ROTATION_NAME);
    if (attr != null)
      rotation = new SFFloat(attr.getValue(),null,null);
    attr = root.getAttribute(TEXTURETRANSFORM_ATTR_TRANSLATION_NAME);
    if (attr != null) {
      fa = parse2(attr.getValue());
      translation0 = new SFFloat(fa[0],null,null);
      translation1 = new SFFloat(fa[1],null,null);
    }
    attr = root.getAttribute(TEXTURETRANSFORM_ATTR_SCALE_NAME);
    if (attr != null) {
      fa = parse2(attr.getValue());
      scale0 = new SFFloat(fa[0],null,null);
      scale1 = new SFFloat(fa[1],null,null);
    }
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return TEXTURETRANSFORMCustomizer.class;
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    if (TEXTURETRANSFORM_ATTR_CENTER_REQD ||
            !center0.equals(center0Default) ||
            !center1.equals(center1Default))
    {
      sb.append(" ");
      sb.append(TEXTURETRANSFORM_ATTR_CENTER_NAME);
      sb.append("='");
      sb.append(center0);
      sb.append(" ");
      sb.append(center1);
      sb.append("'");
    }
    if (TEXTURETRANSFORM_ATTR_ROTATION_REQD || !rotation.equals(rotationDefault)) {
      sb.append(" ");
      sb.append(TEXTURETRANSFORM_ATTR_ROTATION_NAME);
      sb.append("='");
      sb.append(rotation);
      sb.append("'");
    }
    if (TEXTURETRANSFORM_ATTR_SCALE_REQD ||
            !scale0.equals(scale0Default) ||
            !scale1.equals(scale1Default))
    {
      sb.append(" ");
      sb.append(TEXTURETRANSFORM_ATTR_SCALE_NAME);
      sb.append("='");
      sb.append(scale0);
      sb.append(" ");
      sb.append(scale1);
      sb.append("'");
    }
    if (TEXTURETRANSFORM_ATTR_TRANSLATION_REQD ||
            !translation0.equals(translation0Default) ||
            !translation1.equals(translation1Default))
    {
      sb.append(" ");
      sb.append(TEXTURETRANSFORM_ATTR_TRANSLATION_NAME);
      sb.append("='");
      sb.append(translation0);
      sb.append(" ");
      sb.append(translation1);
      sb.append("'");
    }
    return sb.toString();
  }

  public String getCenter0()
  {
    return center0.toString();
  }
  
  public void setCenter0(String s)
  {
    center0 = new SFFloat(s,null,null);
  }

  public String getCenter1()
  {
    return center1.toString();
  }
  
  public void setCenter1(String s)
  {
    center1 = new SFFloat(s,null,null);
  }

  public String getRotation()
  {
    return rotation.toString();
  }
  
  public void setRotation(String s)
  {
    rotation = new SFFloat(s,null,null);
  }

  public String getTranslation0()
  {
    return translation0.toString();
  }
  
  public void setTranslation0(String s)
  {
    translation0 = new SFFloat(s,null,null);
  }

  public String getTranslation1()
  {
    return translation1.toString();
  }
  
  public void setTranslation1(String s)
  {
    translation1 = new SFFloat(s,null,null);
  }

  public String getScale0()
  {
    return scale0.toString();
  }
  
  public void setScale0(String s)
  {
    scale0 = new SFFloat(s,null,null);
  }

  public String getScale1()
  {
    return scale1.toString();
  }
  
  public void setScale1(String s)
  {
    scale1 = new SFFloat(s,null,null);
  }
}
