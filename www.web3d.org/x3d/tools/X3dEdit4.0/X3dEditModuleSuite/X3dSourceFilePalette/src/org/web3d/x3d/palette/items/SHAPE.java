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
import org.web3d.x3d.types.X3DShapeNode;

//import org.netbeans.spi.palette.PaletteItemRegistration;

//@PaletteItemRegistration
//(
//    paletteid = "X3DPalette",
//    category = "2. Geometry Primitives (Annotations)",
//    itemid = "1_SHAPE",
//    icon32 = "org/web3d/x3d/palette/items/resources/SHAPE32.png",
//    icon16 = "org/web3d/x3d/palette/items/resources/SHAPE16.png",
//    body = "\n    <Shape>\n      <!-- Geometry node here -->\n      <Appearance><Material/></Appearance>\n    </Shape>",
//    name = "Shape",
//    tooltip = "Shape contains a single geometry node and corresponding Appearance"
//)
// https://bits.netbeans.org/14/javadoc/org-netbeans-spi-palette/architecture-summary.html
// https://bits.netbeans.org/14/javadoc/org-netbeans-spi-palette/org/netbeans/spi/palette/PaletteItemRegistration.html

/**
 * SHAPE.java
 * Created on August 15, 2007, 2:26 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class SHAPE extends X3DShapeNode
{
  public final static int ARRAY_2D          = 0;
  public final static int ARRAY_3D_XY_PLANE = 1;
  public final static int ARRAY_3D_YZ_PLANE = 2;
  public final static int ARRAY_3D_XZ_PLANE = 3;
    
  public SHAPE()
  {
  }

  @Override
  public String getElementName()
  {
    return SHAPE_ELNAME;
  }

  /**
   * Initialize a new element
   */
  @Override
  public void initialize()
  {
    String[] sa = parse3(SHAPE_ATTR_BBOXCENTER_DFLT);

    bboxCenterX = bboxCenterXDefault = new SFFloat(sa[0], null, null);
    bboxCenterY = bboxCenterYDefault = new SFFloat(sa[1], null, null);
    bboxCenterZ = bboxCenterZDefault = new SFFloat(sa[2], null, null);

    sa = parse3(SHAPE_ATTR_BBOXSIZE_DFLT);
    bboxSizeX = bboxSizeXDefault = new SFFloat(sa[0], -1.0f, null, true);
    bboxSizeY = bboxSizeYDefault = new SFFloat(sa[1], -1.0f, null, true);
    bboxSizeZ = bboxSizeZDefault = new SFFloat(sa[2], -1.0f, null, true);

//  setContent("\n\t\t<!--TODO add Appearance and a single geometry node here-->\n\t");
//  setContent("\n\t\t<!--TODO add a single geometry node here-->\n\t\t<Appearance>\n\t\t\t<Material/>\n\t\t\t<!--TODO add ImageTexture, MovieTexture, PixelTexture, TextureTransform, FillProperties, and/or LineProperties nodes here-->\n\t\t</Appearance>\n\t");
  }

  /**
   * Initialize an element from an existing one
   */
  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr = root.getAttribute(SHAPE_ATTR_BBOXCENTER_NAME);
    if(attr != null) {
      String[] sa = parse3(attr.getValue());
      bboxCenterX = new SFFloat(sa[0], null, null);
      bboxCenterY = new SFFloat(sa[1], null, null);
      bboxCenterZ = new SFFloat(sa[2], null, null);
    }
    attr = root.getAttribute(SHAPE_ATTR_BBOXSIZE_NAME);
    if(attr != null) {
      String[] sa = parse3(attr.getValue());
      bboxSizeX = new SFFloat(sa[0], -1.0f, null, true);
      bboxSizeY = new SFFloat(sa[1], -1.0f, null, true);
      bboxSizeZ = new SFFloat(sa[2], -1.0f, null, true);
    }
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    // -1 is sentinel for no value, 0.0 is minimum actual value, intermediate values not allowed
    if ((bboxSizeX.getValue() > -1.0f) && (bboxSizeX.getValue() < 0.0))
    {
        bboxSizeX = new SFFloat("-1.0", -1.0f, null, true);
    }
    if ((bboxSizeY.getValue() > -1.0f) && (bboxSizeY.getValue() < 0.0))
    {
        bboxSizeY = new SFFloat("-1.0", -1.0f, null, true);
    }
    if ((bboxSizeZ.getValue() > -1.0f) && (bboxSizeZ.getValue() < 0.0))
    {
        bboxSizeZ = new SFFloat("-1.0", -1.0f, null, true);
    }
    if (SHAPE_ATTR_BBOXCENTER_REQD ||
           (!bboxCenterX.equals(bboxCenterXDefault) |
            !bboxCenterY.equals(bboxCenterYDefault) |
            !bboxCenterZ.equals(bboxCenterZDefault)))
    {
      sb.append(" ");
      sb.append(SHAPE_ATTR_BBOXCENTER_NAME);
      sb.append("='");
      sb.append(bboxCenterX.toString());
      sb.append(" ");
      sb.append(bboxCenterY.toString());
      sb.append(" ");
      sb.append(bboxCenterZ.toString());
      sb.append("'");
    }
    if (SHAPE_ATTR_BBOXSIZE_REQD ||
           (!bboxSizeX.equals(bboxSizeXDefault) |
            !bboxSizeY.equals(bboxSizeYDefault) |
            !bboxSizeZ.equals(bboxSizeZDefault)))
    {
      sb.append(" ");
      sb.append(SHAPE_ATTR_BBOXSIZE_NAME);
      sb.append("='");
      sb.append(bboxSizeX.toString());
      sb.append(" ");
      sb.append(bboxSizeY.toString());
      sb.append(" ");
      sb.append(bboxSizeZ.toString());
      sb.append("'");
    }
//    System.out.println ("Shape createAttributes() trace");
//    System.out.println ("bboxSizeX=" + bboxSizeX + ", bboxSizeXDefault=" + bboxSizeXDefault);
//    System.out.println ("bboxSizeY=" + bboxSizeY + ", bboxSizeYDefault=" + bboxSizeYDefault);
//    System.out.println ("bboxSizeZ=" + bboxSizeZ + ", bboxSizeZDefault=" + bboxSizeZDefault);
//
//    System.out.println ("bboxCenterX=" + bboxCenterX + ", bboxCenterXDefault=" + bboxCenterXDefault);
//    System.out.println ("bboxCenterY=" + bboxCenterY + ", bboxCenterYDefault=" + bboxCenterYDefault);
//    System.out.println ("bboxCenterZ=" + bboxCenterZ + ", bboxCenterZDefault=" + bboxCenterZDefault);

    return sb.toString();
  }
}
