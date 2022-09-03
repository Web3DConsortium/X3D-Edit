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

import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * RECTANGLE2D.java
 * Created on July 11, 2007, 12:00 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class RECTANGLE2D extends X3DGeometryNode
{
  private SFFloat sizeX, sizeXDefault;  // Together make one MFVec2F
  private SFFloat sizeY, sizeYDefault;  // "

  private boolean solid;

  public RECTANGLE2D()
  {
  }

  @Override
  public String getElementName()
  {
    return RECTANGLE2D_ELNAME;
  }

  @Override
  public void initialize()
  {
    String[] sa = parse2(RECTANGLE2D_ATTR_SIZE_DFLT);
    sizeX = sizeXDefault = new SFFloat(sa[0], 0.0f, null);
    sizeY = sizeYDefault = new SFFloat(sa[1], 0.0f, null);

    solid = Boolean.parseBoolean(RECTANGLE2D_ATTR_SOLID_DFLT);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(RECTANGLE2D_ATTR_SIZE_NAME);
    if (attr != null) {
      String[] sa = parse2(attr.getValue());
      sizeX = new SFFloat(sa[0], 0.0f, null);
      sizeY = new SFFloat(sa[1], 0.0f, null);
    }
    attr = root.getAttribute(RECTANGLE2D_ATTR_SOLID_NAME);
    if (attr != null)
      solid = Boolean.parseBoolean(attr.getValue());
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return RECTANGLE2DCustomizer.class;
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (RECTANGLE2D_ATTR_SIZE_REQD || (!sizeX.equals(sizeXDefault) || !sizeY.equals(sizeYDefault))) {
      sb.append(" ");
      sb.append(RECTANGLE2D_ATTR_SIZE_NAME);
      sb.append("='");
      sb.append(sizeX);
      sb.append(" ");
      sb.append(sizeY);
      sb.append("'");
    }

    if (RECTANGLE2D_ATTR_SOLID_REQD || solid != Boolean.parseBoolean(RECTANGLE2D_ATTR_SOLID_DFLT)) {
      sb.append(" ");
      sb.append(RECTANGLE2D_ATTR_SOLID_NAME);
      sb.append("='");
      sb.append(solid);
      sb.append("'");
    }
    return sb.toString();
  }

  public String getSizeX()
  {
    return sizeX.toString();
  }

  public void setSizeX(String size)
  {
    sizeX = new SFFloat(size, 0.0f, null);
  }

  public String getSizeY()
  {
    return sizeY.toString();
  }

  public void setSizeY(String size)
  {
    sizeY = new SFFloat(size, 0.0f, null);
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
