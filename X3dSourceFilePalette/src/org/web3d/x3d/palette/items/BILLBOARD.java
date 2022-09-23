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
import org.web3d.x3d.types.X3DGroupingNode;

import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * BILLBOARD.java
 * Created on March 14, 2007, 9:57 AM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class BILLBOARD extends X3DGroupingNode
{
  private SFFloat axisRotationX, axisRotationXDefault;
  private SFFloat axisRotationY, axisRotationYDefault;
  private SFFloat axisRotationZ, axisRotationZDefault;

  public BILLBOARD()
  {
  }

  @Override
  public String getElementName()
  {
    return BILLBOARD_ELNAME;
  }

  @Override
  public void initialize()
  {
    //setDEF(true);
    //setDEFUSE("My"+getElementName()+getSequenceString());

    String[] fa = parse3(BILLBOARD_ATTR_BBOXCENTER_DFLT);
    bboxCenterX = bboxCenterXDefault = new SFFloat(fa[0], null, null);
    bboxCenterY = bboxCenterYDefault = new SFFloat(fa[1],null,null);
    bboxCenterZ = bboxCenterZDefault = new SFFloat(fa[2],null,null);

    fa = parse3(BILLBOARD_ATTR_BBOXSIZE_DFLT);
    bboxSizeX = bboxSizeXDefault = new SFFloat(fa[0],0.0f,null,true);
    bboxSizeY = bboxSizeYDefault = new SFFloat(fa[1],0.0f,null,true);
    bboxSizeZ = bboxSizeZDefault = new SFFloat(fa[2],0.0f,null,true);

    fa = parse3(BILLBOARD_ATTR_AXISOFROTATION_DFLT);
    axisRotationX = axisRotationXDefault = new SFFloat(fa[0],null,null);
    axisRotationY = axisRotationYDefault = new SFFloat(fa[1],null,null);
    axisRotationZ = axisRotationZDefault = new SFFloat(fa[2],null,null);

    setContent("\n\t\t<!-- TODO add children nodes and statements here -->\n\t");
}

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);

    String[] fa;
    org.jdom.Attribute attr = root.getAttribute(BILLBOARD_ATTR_BBOXCENTER_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      bboxCenterX = new SFFloat(fa[0], null, null);
      bboxCenterY = new SFFloat(fa[1], null, null);
      bboxCenterZ = new SFFloat(fa[2], null, null);
    }
    attr = root.getAttribute(BILLBOARD_ATTR_BBOXSIZE_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      bboxSizeX = new SFFloat(fa[0], 0.0f, null, true);
      bboxSizeY = new SFFloat(fa[1], 0.0f, null, true);
      bboxSizeZ = new SFFloat(fa[2], 0.0f, null, true);
    }
    attr = root.getAttribute(BILLBOARD_ATTR_AXISOFROTATION_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      axisRotationX = new SFFloat(fa[0], null, null);
      axisRotationY = new SFFloat(fa[1], null, null);
      axisRotationZ = new SFFloat(fa[2], null, null);
    }
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return BILLBOARDCustomizer.class;
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if(BILLBOARD_ATTR_AXISOFROTATION_REQD ||
       (!axisRotationX.equals(axisRotationXDefault) ||
        !axisRotationY.equals(axisRotationYDefault) ||
        !axisRotationZ.equals(axisRotationZDefault))) {
      sb.append(" ");
      sb.append(BILLBOARD_ATTR_AXISOFROTATION_NAME);
      sb.append("='");
      sb.append(axisRotationX);
      sb.append(" ");
      sb.append(axisRotationY);
      sb.append(" ");
      sb.append(axisRotationZ);
      sb.append("'");
    }

    if(BILLBOARD_ATTR_BBOXCENTER_REQD ||
       (!bboxCenterX.equals(bboxCenterXDefault) ||
        !bboxCenterY.equals(bboxCenterYDefault) ||
        !bboxCenterZ.equals(bboxCenterZDefault))) {
      sb.append(" ");
      sb.append(BILLBOARD_ATTR_BBOXCENTER_NAME);
      sb.append("='");
      sb.append(bboxCenterX);
      sb.append(" ");
      sb.append(bboxCenterY);
      sb.append(" ");
      sb.append(bboxCenterZ);
      sb.append("'");
    }

    if(BILLBOARD_ATTR_BBOXSIZE_REQD ||
       (!bboxSizeX.equals(bboxSizeXDefault) ||
        !bboxSizeY.equals(bboxSizeYDefault) ||
        !bboxSizeZ.equals(bboxSizeZDefault))) {
      sb.append(" ");
      sb.append(BILLBOARD_ATTR_BBOXSIZE_NAME);
      sb.append("='");
      sb.append(bboxSizeX);
      sb.append(" ");
      sb.append(bboxSizeY);
      sb.append(" ");
      sb.append(bboxSizeZ);
      sb.append("'");
    }
    return sb.toString();
  }

  public String getAxisRotationX()
  {
    return axisRotationX.toString();
  }

  public void setAxisRotationX(String axisRotationX)
  {
    this.axisRotationX = new SFFloat(axisRotationX,null,null);
  }

  public String getAxisRotationY()
  {
    return axisRotationY.toString();
  }

  public void setAxisRotationY(String axisRotationY)
  {
    this.axisRotationY = new SFFloat(axisRotationY,null,null);
  }

  public String getAxisRotationZ()
  {
    return axisRotationZ.toString();
  }

  public void setAxisRotationZ(String axisRotationZ)
  {
    this.axisRotationZ = new SFFloat(axisRotationZ,null,null);
  }

}
