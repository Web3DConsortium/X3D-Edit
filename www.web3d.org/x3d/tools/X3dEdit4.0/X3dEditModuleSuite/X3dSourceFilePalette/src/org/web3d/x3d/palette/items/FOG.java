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
import org.web3d.x3d.types.X3DBindableNode;

import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * FOG.java
 * Created on Sep 11, 2007, 2:15 PM
 * 
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 * 
 * 
 * @author Mike Bailey
 * @version $Id$
 */
public class FOG extends X3DBindableNode
{
  private SFFloat color0,color0Default;
  private SFFloat color1,color1Default;
  private SFFloat color2,color2Default;
  private String  fogType;
  private SFFloat visibilityRange, visibilityRangeDefault;
  
  public FOG()
  {
  }

  @Override
  public String getElementName()
  {
    return FOG_ELNAME;
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return FOGCustomizer.class;
  }
  
  @Override
  public void initialize()
  {
    visibilityRange = visibilityRangeDefault = new SFFloat(FOG_ATTR_VISIBILITYRANGE_DFLT,0.0f,1.0f);    
    fogType = FOG_ATTR_FOGTYPE_DFLT;
    
    String[] fa;
    if(FOG_ATTR_COLOR_DFLT.length() <=0 ) {
      fa = new String[3];
      fa[0]=fa[1]=fa[2]=null;
    }
    else
      fa = parse3(FOG_ATTR_COLOR_DFLT);
    
    color0 = color0Default = new SFFloat(fa[0],0.0f,1.0f);
    color1 = color1Default = new SFFloat(fa[1],0.0f,1.0f);
    color2 = color2Default = new SFFloat(fa[2],0.0f,1.0f);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(FOG_ATTR_VISIBILITYRANGE_NAME);
    if (attr != null)
      visibilityRange = new SFFloat(attr.getValue(), 0.0f, 1.0f);
    attr = root.getAttribute(FOG_ATTR_FOGTYPE_NAME);
    if (attr != null)
      fogType = attr.getValue();
    attr = root.getAttribute(FOG_ATTR_COLOR_NAME);
    if (attr != null) {
      String[] fa = parse3(attr.getValue());
      color0 = new SFFloat(fa[0], 0.0f, 1.0f);
      color1 = new SFFloat(fa[1], 0.0f, 1.0f);
      color2 = new SFFloat(fa[2], 0.0f, 1.0f);
    }
  }
  
  @Override
  public String createAttributes()
  {
    StringBuffer sb = new StringBuffer();
    if (FOG_ATTR_COLOR_REQD ||
           (!color0.equals(color0Default) ||
            !color1.equals(color1Default) ||
            !color2.equals(color2Default))) {
      sb.append(" ");
      sb.append(FOG_ATTR_COLOR_NAME);
      sb.append("='");
      sb.append(color0);
      sb.append(" ");
      sb.append(color1);
      sb.append(" ");
      sb.append(color2);
      sb.append("'");
    }
    if (FOG_ATTR_FOGTYPE_REQD || !fogType.equals(FOG_ATTR_FOGTYPE_DFLT)) {
      sb.append(" ");
      sb.append(FOG_ATTR_FOGTYPE_NAME);
      sb.append("='");
      sb.append(fogType);
      sb.append("'");
    }
    if (FOG_ATTR_VISIBILITYRANGE_REQD || !visibilityRange.equals(visibilityRangeDefault)) {
      sb.append(" ");
      sb.append(FOG_ATTR_VISIBILITYRANGE_NAME);
      sb.append("='");
      sb.append(visibilityRange);
      sb.append("'");
    }
    return sb.toString();
  }
    
  public void setFogType(String s)
  {
    fogType = s;
  }
  
  public String getFogType()
  {
    return fogType;
  }
  
  public void setVisibilityRange(String s)
  {
    visibilityRange = new SFFloat(s,0.0f,null);
  }
  
  public String getVisibilityRange()
  {
    return visibilityRange.toString();
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
}