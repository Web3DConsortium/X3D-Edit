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
import org.web3d.x3d.types.X3DGeometryNode;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFFloat;
import static org.web3d.x3d.types.X3DSchemaData.*;

//import org.netbeans.spi.palette.PaletteItemRegistration;

//@PaletteItemRegistration
//(
//    paletteid = "X3DPalette",
//    category = "2. Geometry Primitives (Annotations)",
//    itemid = "CONE",
//    icon32 = "org/web3d/x3d/palette/items/resources/CONE32.png",
//    icon16 = "org/web3d/x3d/palette/items/resources/CONE16.png",
//    body = "<Cone bottom='true' bottomRadius='1' height='2' side='true' solid='true'/>",
//    name = "Cone",
//    tooltip = "Cone defines conical 3D geometry"
//)
// https://bits.netbeans.org/14/javadoc/org-netbeans-spi-palette/architecture-summary.html
// https://bits.netbeans.org/14/javadoc/org-netbeans-spi-palette/org/netbeans/spi/palette/PaletteItemRegistration.html

/**
 * CONE.java
 * Created on July 12, 2007, 3:05 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class CONE extends X3DGeometryNode
{
  private SFFloat bottomRadius, bottomRadiusDefault;
  private SFFloat height, heightDefault;
  private boolean side;
  private boolean bottom;  private boolean solid;
  
  public CONE()
  {
  }
  
  @Override
  public String getElementName()
  {
    return CONE_ELNAME;
  }
  
  @Override
  public void initialize()
  {
    bottomRadius = bottomRadiusDefault = new SFFloat(CONE_ATTR_BOTTOMRADIUS_DFLT, 0.0f, null);
    height       = heightDefault       = new SFFloat(CONE_ATTR_HEIGHT_DFLT, 0.0f, null);
    side         = Boolean.parseBoolean(CONE_ATTR_SIDE_DFLT);
    bottom       = Boolean.parseBoolean(CONE_ATTR_BOTTOM_DFLT);
    solid        = Boolean.parseBoolean(CONE_ATTR_SOLID_DFLT);
  }
  
 @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr = root.getAttribute(CONE_ATTR_BOTTOMRADIUS_NAME);
    if(attr != null)
      bottomRadius = new SFFloat(attr.getValue(), 0.0f, null);
    attr = root.getAttribute(CONE_ATTR_HEIGHT_NAME);
    if(attr != null)
      height = new SFFloat(attr.getValue(), 0.0f, null);
    attr = root.getAttribute(CONE_ATTR_SIDE_NAME);
    if(attr != null)
      side = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(CONE_ATTR_BOTTOM_NAME);
    if(attr != null)
      bottom = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(CONE_ATTR_SOLID_NAME);
    if(attr != null)
      solid = Boolean.parseBoolean(attr.getValue());
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return CONECustomizer.class;
  }
  
  @Override
  public String createAttributes()
  {
    StringBuffer sb = new StringBuffer();

    if (CONE_ATTR_BOTTOMRADIUS_REQD || !bottomRadius.equals(bottomRadiusDefault)) {
      sb.append(" ");
      sb.append(CONE_ATTR_BOTTOMRADIUS_NAME);
      sb.append("='");
      sb.append(bottomRadius);
      sb.append("'");
    }
    if (CONE_ATTR_HEIGHT_REQD || !height.equals(heightDefault)) {
      sb.append(" ");
      sb.append(CONE_ATTR_HEIGHT_NAME);
      sb.append("='");
      sb.append(height);
      sb.append("'");
    }

    if (CONE_ATTR_SIDE_REQD || side != Boolean.parseBoolean(CONE_ATTR_SIDE_DFLT)) {
      sb.append(" ");
      sb.append(CONE_ATTR_SIDE_NAME);
      sb.append("='");
      sb.append(side);
      sb.append("'");
    }

    if (CONE_ATTR_BOTTOM_REQD || bottom != Boolean.parseBoolean(CONE_ATTR_BOTTOM_DFLT)) {
      sb.append(" ");
      sb.append(CONE_ATTR_BOTTOM_NAME);
      sb.append("='");
      sb.append(bottom);
      sb.append("'");
    }

    if (CONE_ATTR_SOLID_REQD || solid != Boolean.parseBoolean(CONE_ATTR_SOLID_DFLT)) {
      sb.append(" ");
      sb.append(CONE_ATTR_SOLID_NAME);
      sb.append("='");
      sb.append(solid);
      sb.append("'");
    }
    return sb.toString();
  }

  public boolean isBottom()
  {
    return bottom;
  }

  public void setBottom(boolean bottom)
  {
    this.bottom = bottom;
  }

  public String getBottomRadius()
  {
    return bottomRadius.toString();
  }

  public void setBottomRadius(String bottomRadius)
  {
    this.bottomRadius = new SFFloat(bottomRadius, 0.0f, null);
  }

  public String getHeight()
  {
    return height.toString();
  }

  public void setHeight(String height)
  {
    this.height = new SFFloat(height, 0.0f, null);
  }

  public boolean isSide()
  {
    return side;
  }

  public void setSide(boolean side)
  {
    this.side = side;
  }

  public boolean isSolid()
  {
    return solid;
  }

  public void setSolid(boolean solid)
  {
    this.solid = solid;
  }

}
