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
//    itemid = "CYLINDER",
//    icon32 = "org/web3d/x3d/palette/items/resources/CYLINDER32.png",
//    icon16 = "org/web3d/x3d/palette/items/resources/CYLINDER16.png",
//    body = "<Cylinder bottom='true' height='2' radius='1' side='true' solid='true' top='true'/>",
//    name = "Cylinder",
//    tooltip = "Cylinder defines cylindrical 3D geometry"
//)
// https://bits.netbeans.org/14/javadoc/org-netbeans-spi-palette/architecture-summary.html
// https://bits.netbeans.org/14/javadoc/org-netbeans-spi-palette/org/netbeans/spi/palette/PaletteItemRegistration.html

/**
 * CYLINDER.java
 * Created on July 12, 2007, 3:36 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class CYLINDER extends X3DGeometryNode
{
  private boolean bottom;
  private SFFloat height, heightDefault;
  private SFFloat radius, radiusDefault;
  private boolean side;
  private boolean solid;
  private boolean top;
  
  public CYLINDER()
  {
  }
  
  @Override
  public void initialize()
  {
    bottom = Boolean.parseBoolean(CYLINDER_ATTR_BOTTOM_DFLT);
    height = heightDefault = new SFFloat(CYLINDER_ATTR_HEIGHT_DFLT, 0.0f, null);
    radius = radiusDefault = new SFFloat(CYLINDER_ATTR_RADIUS_DFLT, 0.0f, null);
    side   = Boolean.parseBoolean(CYLINDER_ATTR_SIDE_DFLT);
    solid  = Boolean.parseBoolean(CYLINDER_ATTR_SOLID_DFLT);
    top    = Boolean.parseBoolean(CYLINDER_ATTR_TOP_DFLT);
  }
  
 @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr = root.getAttribute(CYLINDER_ATTR_BOTTOM_NAME);
    if(attr != null)
      bottom = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(CYLINDER_ATTR_HEIGHT_NAME);
    if(attr != null)
      height = new SFFloat(attr.getValue(), 0.0f, null);
    attr = root.getAttribute(CYLINDER_ATTR_RADIUS_NAME);
    if(attr != null)
      radius = new SFFloat(attr.getValue(), 0.0f, null);
    attr = root.getAttribute(CYLINDER_ATTR_SIDE_NAME);
    if(attr != null)
      side   = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(CYLINDER_ATTR_SOLID_NAME);
    if(attr != null)
      solid  = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(CYLINDER_ATTR_TOP_NAME);
    if(attr != null)
      top = Boolean.parseBoolean(attr.getValue());
  }

 @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return CYLINDERCustomizer.class;
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (CYLINDER_ATTR_BOTTOM_REQD || bottom != Boolean.parseBoolean(CYLINDER_ATTR_BOTTOM_DFLT)) {
      sb.append(" ");
      sb.append(CYLINDER_ATTR_BOTTOM_NAME);
      sb.append("='");
      sb.append(bottom);
      sb.append("'");
    }

    if (CYLINDER_ATTR_HEIGHT_REQD || !height.equals(heightDefault)) {
      sb.append(" ");
      sb.append(CYLINDER_ATTR_HEIGHT_NAME);
      sb.append("='");
      sb.append(height);
      sb.append("'");
    }
    if (CYLINDER_ATTR_RADIUS_REQD || !radius.equals(radiusDefault)) {
      sb.append(" ");
      sb.append(CYLINDER_ATTR_RADIUS_NAME);
      sb.append("='");
      sb.append(radius);
      sb.append("'");
    }

    if (CYLINDER_ATTR_SIDE_REQD || side != Boolean.parseBoolean(CYLINDER_ATTR_SIDE_DFLT)) {
      sb.append(" ");
      sb.append(CYLINDER_ATTR_SIDE_NAME);
      sb.append("='");
      sb.append(side);
      sb.append("'");
    }

    if (CYLINDER_ATTR_TOP_REQD || top != Boolean.parseBoolean(CYLINDER_ATTR_TOP_DFLT)) {
      sb.append(" ");
      sb.append(CYLINDER_ATTR_TOP_NAME);
      sb.append("='");
      sb.append(top);
      sb.append("'");
    }

    if (CYLINDER_ATTR_SOLID_REQD || solid != Boolean.parseBoolean(CYLINDER_ATTR_SOLID_DFLT)) {
      sb.append(" ");
      sb.append(CYLINDER_ATTR_SOLID_NAME);
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

  public String getHeight()
  {
    return height.toString();
  }

  public void setHeight(String height)
  {
    this.height = new SFFloat(height, 0.0f, null);
  }

  public String getRadius()
  {
    return radius.toString();
  }

  public void setRadius(String radius)
  {
    this.radius = new SFFloat(radius, 0.0f, null);
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

  public boolean isTop()
  {
    return top;
  }

  public void setTop(boolean top)
  {
    this.top = top;
  }
  
  @Override
  public String getElementName()
  {
    return CYLINDER_ELNAME;
  }
}
