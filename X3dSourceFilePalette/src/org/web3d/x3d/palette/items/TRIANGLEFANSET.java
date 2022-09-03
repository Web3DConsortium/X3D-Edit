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
import org.web3d.x3d.types.X3DComposedGeometryNode;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFFloat;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * TRIANGLEFANSET.java
 * Created on Oct 11, 2007, 1:40 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class TRIANGLEFANSET extends X3DComposedGeometryNode
{
  private SFFloat[] fanCount,fanCountDefault;
  private boolean ccw;
  private boolean colorPerVertex;
  private boolean normalPerVertex;
  private boolean solid;

  private SFFloat[][] coordinatePoints;
  
  public TRIANGLEFANSET()
  {
  }

  @Override
  public String getElementName()
  {
    return TRIANGLEFANSET_ELNAME;
  }

  @Override
  public void initialize()
  {
    ccw =             Boolean.parseBoolean(TRIANGLEFANSET_ATTR_CCW_DFLT);
    colorPerVertex =  Boolean.parseBoolean(TRIANGLEFANSET_ATTR_COLORPERVERTEX_DFLT);
    normalPerVertex = Boolean.parseBoolean(TRIANGLEFANSET_ATTR_NORMALPERVERTEX_DFLT);
    solid =           Boolean.parseBoolean(TRIANGLEFANSET_ATTR_SOLID_DFLT);
    fanCount =  new SFFloat[]{buildSFFloat("3")};//new SFFloat[0];
    fanCountDefault = new SFFloat[0];
    setContent("\n\t\t\t<!--TODO add Color|ColorRGBA, Coordinate|CoordinateDouble, Normal and/or TextureCoordinate|MultiTextureCoordinate nodes here-->\n\t\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(TRIANGLEFANSET_ATTR_CCW_NAME);
    if (attr != null)
      ccw = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(TRIANGLEFANSET_ATTR_COLORPERVERTEX_NAME);
    if (attr != null)
      colorPerVertex = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(TRIANGLEFANSET_ATTR_NORMALPERVERTEX_NAME);
    if (attr != null)
      normalPerVertex = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(TRIANGLEFANSET_ATTR_SOLID_NAME);
    if (attr != null)
      solid = Boolean.parseBoolean(attr.getValue());
    
    attr = root.getAttribute(TRIANGLEFANSET_ATTR_FANCOUNT_NAME);
    if(attr != null) {
      buildFanCount(attr.getValue());
    }
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return TRIANGLEFANSETCustomizer.class;
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    if (TRIANGLEFANSET_ATTR_CCW_REQD || ccw != Boolean.parseBoolean(TRIANGLEFANSET_ATTR_CCW_DFLT)) {
      sb.append(" ");
      sb.append(TRIANGLEFANSET_ATTR_CCW_NAME);
      sb.append("='");
      sb.append(ccw);
      sb.append("'");
    }
    if (TRIANGLEFANSET_ATTR_COLORPERVERTEX_REQD || colorPerVertex != Boolean.parseBoolean(TRIANGLEFANSET_ATTR_COLORPERVERTEX_DFLT)) {
      sb.append(" ");
      sb.append(TRIANGLEFANSET_ATTR_COLORPERVERTEX_NAME);
      sb.append("='");
      sb.append(colorPerVertex);
      sb.append("'");
    }
    if (TRIANGLEFANSET_ATTR_NORMALPERVERTEX_REQD || normalPerVertex != Boolean.parseBoolean(TRIANGLEFANSET_ATTR_NORMALPERVERTEX_DFLT)) {
      sb.append(" ");
      sb.append(TRIANGLEFANSET_ATTR_NORMALPERVERTEX_NAME);
      sb.append("='");
      sb.append(normalPerVertex);
      sb.append("'");
    }
    if (TRIANGLEFANSET_ATTR_SOLID_REQD || solid != Boolean.parseBoolean(TRIANGLEFANSET_ATTR_SOLID_DFLT)) {
      sb.append(" ");
      sb.append(TRIANGLEFANSET_ATTR_SOLID_NAME);
      sb.append("='");
      sb.append(solid);
      sb.append("'");
    }
    if (TRIANGLEFANSET_ATTR_FANCOUNT_REQD || !arraysIdenticalOrNull(fanCount, fanCountDefault)) {
      sb.append(" ");
      sb.append(TRIANGLEFANSET_ATTR_FANCOUNT_NAME);
      sb.append("='");
      sb.append(fanCountToString());
      sb.append("'");
    }
    return sb.toString();
  }

  @Override
  protected SFFloat buildSFFloat(String s)
  {
    return new SFFloat(s,3.0f,null); // lower limit only
  }

  private void buildFanCount(String s)
  {
    String[] sa = s.replace(',', ' ').trim().split("\\s");
    fanCount = new SFFloat[sa.length];
    for (int i = 0; i < sa.length; i++)
    {
            fanCount[i] = new SFFloat(sa[i], 0.0f, null);
    }
  }
 
  private String fanCountToString()
  {
    StringBuilder sb = new StringBuilder();
    for(SFFloat sf : fanCount) {
      sb.append(sf.toString());
      sb.append(" ");
    }
    return sb.toString().trim();
  }
  
  public boolean isCcw()
  {
    return ccw;
  }

  public void setCcw(boolean ccw)
  {
    this.ccw = ccw;
  }

  public boolean isColorPerVertex()
  {
    return colorPerVertex;
  }

  public void setColorPerVertex(boolean colorPerVertex)
  {
    this.colorPerVertex = colorPerVertex;
  }

  public boolean isNormalPerVertex()
  {
    return normalPerVertex;
  }

  public void setNormalPerVertex(boolean normalPerVertex)
  {
    this.normalPerVertex = normalPerVertex;
  }

  public boolean isSolid()
  {
    return solid;
  }

  public void setSolid(boolean solid)
  {
    this.solid = solid;
  }
  
  public void setFanCount(String s)
  {
    buildFanCount(s);   
  }
  
  public String getFanCount()
  {
    return fanCountToString();
  }
}
