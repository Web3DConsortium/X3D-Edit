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
import org.web3d.x3d.types.X3DNBodyCollidableNode;

import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * COLLISIONSPACE.java
 * Created on July 11, 2007, 5:13 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class COLLISIONSPACE extends X3DNBodyCollidableNode
{
  private boolean enabled, enabledDefault;
  private boolean useGeometry, useGeometryDefault;

  public COLLISIONSPACE()
  {
  }

  @Override
  public String getElementName()
  {
    return COLLISIONSPACE_ELNAME;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return COLLISIONSPACECustomizer.class;
  }

  @Override
  public void initialize()
  {
    enabled     = enabledDefault     = Boolean.parseBoolean(COLLISIONSPACE_ATTR_ENABLED_DFLT);
    useGeometry = useGeometryDefault = Boolean.parseBoolean(COLLISIONSPACE_ATTR_USEGEOMETRY_DFLT);
    
    String[] fa;
    fa = parse3(COLLISIONSPACE_ATTR_BBOXCENTER_DFLT);
    bboxCenterX = bboxCenterXDefault = new SFFloat(fa[0], null, null);
    bboxCenterY = bboxCenterYDefault = new SFFloat(fa[1], null, null);
    bboxCenterZ = bboxCenterZDefault = new SFFloat(fa[2], null, null);

    fa = parse3(COLLISIONSPACE_ATTR_BBOXSIZE_DFLT);
    bboxSizeX = bboxSizeXDefault = new SFFloat(fa[0], null, null);
    bboxSizeY = bboxSizeYDefault = new SFFloat(fa[1], null, null);
    bboxSizeZ = bboxSizeZDefault = new SFFloat(fa[2], null, null);

    // TODO schematron rule to ensure child Shape has containerField='shape'
    
    setContent("\n\t\t<!--can insert multiple X3DNBodyCollisionSpaceNode, X3DNBodyCollidableNode here: CollidableShape, CollidableOffset, or CollisionSpace (containerField='collidables')-->\n\t\t<CollidableShape containerField='collidables'/>\n\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;
    
    attr = root.getAttribute(COLLISIONSPACE_ATTR_ENABLED_NAME);
    if (attr != null)
      enabled   = Boolean.parseBoolean(attr.getValue());
    
    attr = root.getAttribute(COLLISIONSPACE_ATTR_USEGEOMETRY_NAME);
    if (attr != null)
      useGeometry   = Boolean.parseBoolean(attr.getValue());

    String[] fa;
    attr = root.getAttribute(COLLISIONSPACE_ATTR_BBOXCENTER_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      bboxCenterX = new SFFloat(fa[0], null, null);
      bboxCenterY = new SFFloat(fa[1], null, null);
      bboxCenterZ = new SFFloat(fa[2], null, null);
    }
    attr = root.getAttribute(COLLISIONSPACE_ATTR_BBOXSIZE_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      bboxSizeX = new SFFloat(fa[0], null, null);
      bboxSizeY = new SFFloat(fa[1], null, null);
      bboxSizeZ = new SFFloat(fa[2], null, null);
    }
  }

  @Override
  public String createAttributes()
  {
    StringBuffer sb = new StringBuffer();

    if (COLLISIONSPACE_ATTR_BBOXCENTER_REQD ||
           (!bboxCenterX.equals(bboxCenterXDefault) ||
            !bboxCenterY.equals(bboxCenterYDefault) ||
            !bboxCenterZ.equals(bboxCenterZDefault))) {
      sb.append(" ");
      sb.append(COLLISIONSPACE_ATTR_BBOXCENTER_NAME);
      sb.append("='");
      sb.append(bboxCenterX);
      sb.append(" ");
      sb.append(bboxCenterY);
      sb.append(" ");
      sb.append(bboxCenterZ);
      sb.append("'");
    }
    if (COLLISIONSPACE_ATTR_BBOXSIZE_REQD ||
           (!bboxSizeX.equals(bboxSizeXDefault) ||
            !bboxSizeY.equals(bboxSizeYDefault) ||
            !bboxSizeZ.equals(bboxSizeZDefault))) {
      sb.append(" ");
      sb.append(COLLISIONSPACE_ATTR_BBOXSIZE_NAME);
      sb.append("='");
      sb.append(bboxSizeX);
      sb.append(" ");
      sb.append(bboxSizeY);
      sb.append(" ");
      sb.append(bboxSizeZ);
      sb.append("'");
    }
    if (COLLISIONSPACE_ATTR_ENABLED_REQD || enabled != enabledDefault) {
      sb.append(" ");
      sb.append(COLLISIONSPACE_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }
    if (COLLISIONSPACE_ATTR_USEGEOMETRY_REQD || useGeometry != useGeometryDefault) {
      sb.append(" ");
      sb.append(COLLISIONSPACE_ATTR_USEGEOMETRY_NAME);
      sb.append("='");
      sb.append(useGeometry);
      sb.append("'");
    }

    return sb.toString();
  }

  public boolean isEnabled()
  {
    return enabled;
  }

  public void setEnabled(boolean newEnabled)
  {
    this.enabled = newEnabled;
  }

  public boolean isUseGeometry()
  {
    return useGeometry;
  }

  public void setUseGeometry(boolean newUseGeometry)
  {
    this.useGeometry = newUseGeometry;
  }
}