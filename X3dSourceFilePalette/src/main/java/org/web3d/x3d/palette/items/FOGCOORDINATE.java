/*
Copyright (c) 1995-2026 held by the author(s).  All rights reserved.

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
      (https://www.nps.edu and https://MovesInstitute.nps.edu)
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

import org.jdom.Element;
import javax.swing.text.JTextComponent;
import org.web3d.x3d.types.X3DGeometryNode;
import org.web3d.x3d.types.X3DPrimitiveTypes;

import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.parseX;

/**
 * FOGCOORDINATE.java
 *
 * @author Don Brutzman
 * @version $Id$
 */
public class FOGCOORDINATE extends X3DGeometryNode
{
  private X3DPrimitiveTypes.SFFloat[] depth, depthDefault; // MFFloat

  public FOGCOORDINATE()
  {
  }

  @Override
  public String getElementName()
  {
    return FOGCOORDINATE_ELNAME;
  }

  @Override
  public void initialize()
  {
    String[] sa;
    if(FOGCOORDINATE_ATTR_DEPTH_DFLT == null || FOGCOORDINATE_ATTR_DEPTH_DFLT.length()<=0)
      sa = new String[]{}; // empty array
    else
      sa = parseX(FOGCOORDINATE_ATTR_DEPTH_DFLT);
    depth                = depthDefault                = parseToSFFloatArray(sa);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;
    String[] sa;

    attr = root.getAttribute(FOGCOORDINATE_ATTR_DEPTH_NAME);
    if (attr != null) {
      sa = parseX(attr.getValue());
      depth = parseToSFFloatArray(sa);
    }
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return FOGCOORDINATECustomizer.class;
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (FOGCOORDINATE_ATTR_DEPTH_REQD || (!arraysIdenticalOrNull(depth,depthDefault) && (depth.length > 0))) {
      sb.append(" ");
      sb.append(FOGCOORDINATE_ATTR_DEPTH_NAME);
      sb.append("='");
      sb.append(depth);
      sb.append("'");
    }
    return sb.toString();
  }

    /**
     * @return the depth
     */
    public String getDepth() {
        String values = new String();
        for (X3DPrimitiveTypes.SFFloat nextValue : depth) {
            values += " " + nextValue.toString();
        }
        return values.trim();
    }

    /**
     * @param newDepth the depth to set
     */
    public void setDepth(String newDepth) {
        String[] sa;
        if ((newDepth != null) && !newDepth.isBlank()) {
          sa = parseX(newDepth);
        }
        else sa = new String[]{}; // empty array
        depth = parseToSFFloatArray(sa);
    }

}
