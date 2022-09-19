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
import static org.web3d.x3d.types.X3DSchemaData4.*;

//import org.netbeans.spi.palette.PaletteItemRegistration;

//@PaletteItemRegistration
//(
//    paletteid = "X3DPalette",
//    category = "2. Geometry Primitives (Annotations)",
//    itemid = "BOX",
//    icon32 = "org/web3d/x3d/palette/items/resources/BOX32.png",
//    icon16 = "org/web3d/x3d/palette/items/resources/BOX16.png",
//    body = "<Box size='1 2 3' solid='true'/>",
//    name = "Box",
//    tooltip = "Box defines rectangular 3D geometry"
//)
// https://bits.netbeans.org/14/javadoc/org-netbeans-spi-palette/architecture-summary.html
// https://bits.netbeans.org/14/javadoc/org-netbeans-spi-palette/org/netbeans/spi/palette/PaletteItemRegistration.html

/**
 * BOX.java
 * Created on July 11, 2007, 3:32 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class BOX extends X3DGeometryNode
{
  private SFFloat sizeX,sizeXDefault;
  private SFFloat sizeY,sizeYDefault;
  private SFFloat sizeZ,sizeZDefault;
  private boolean solid;
  
  public BOX()
  {
  }
  
  @Override
  public String getElementName()
  {
    return BOX_ELNAME;
  }
  
  @Override
  public void initialize()
  {
    String[] sa = parse3(BOX_ATTR_SIZE_DFLT);
    sizeX = sizeXDefault = makeSize(sa[0]);
    sizeY = sizeYDefault = makeSize(sa[1]);
    sizeZ = sizeZDefault = makeSize(sa[2]);
    solid = Boolean.parseBoolean(BOX_ATTR_SOLID_DFLT);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr = root.getAttribute(BOX_ATTR_SIZE_NAME);
    if(attr != null) {
      String[] sa = parse3(attr.getValue());
      sizeX = makeSize(sa[0]);
      sizeY = makeSize(sa[1]);
      sizeZ = makeSize(sa[2]);
    }
    attr = root.getAttribute(BOX_ATTR_SOLID_NAME);
    if(attr != null)
     solid = Boolean.parseBoolean(attr.getValue());
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return BOXCustomizer.class;
  }

  @Override
  protected String createAttributes()
  {
    StringBuffer sb = new StringBuffer();
    if(BOX_ATTR_SIZE_REQD ||
              (!sizeX.equals(sizeXDefault)) ||
              (!sizeY.equals(sizeYDefault)) ||
              (!sizeZ.equals(sizeZDefault)))  {
      sb.append(" ");
      sb.append(BOX_ATTR_SIZE_NAME);
      sb.append("='");
      sb.append(sizeX);
      sb.append(" ");
      sb.append(sizeY);
      sb.append(" ");
      sb.append(sizeZ);
      sb.append("'");        
    }    
      
    if(BOX_ATTR_SOLID_REQD || solid != Boolean.parseBoolean(BOX_ATTR_SOLID_DFLT)) {
      sb.append(" ");
      sb.append(BOX_ATTR_SOLID_NAME);
      sb.append("='");
      sb.append(solid);
      sb.append("'");
    }
    return sb.toString();
  }
 
  // Attribute getters and setters for corresponding customizer
  public String getSizeX()
  {
    return sizeX.toString();
  }

  public void setSizeX(String sizeX)
  {
    this.sizeX = makeSize(sizeX);
  }

  public String getSizeY()
  {
    return sizeY.toString();
  }

  public void setSizeY(String sizeY)
  {
    this.sizeY = makeSize(sizeY);
  }

  public String getSizeZ()
  {
    return sizeZ.toString();
  }

  public void setSizeZ(String sizeZ)
  {
    this.sizeZ = makeSize(sizeZ);
  }

  public boolean isSolid()
  {
    return solid;
  }

  public void setSolid(boolean solid)
  {
    this.solid = solid;
  }

  // Utility method(s)
  private SFFloat makeSize(String s)
  {
    return new SFFloat(s, 0.0f, null);
  }
}
