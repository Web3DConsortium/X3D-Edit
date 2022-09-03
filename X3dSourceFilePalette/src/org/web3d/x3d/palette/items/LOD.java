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

import java.util.Vector;
import javax.swing.text.JTextComponent;
import org.web3d.x3d.types.X3DGroupingNode;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFFloat;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * LOD.java
 * Created on August 16, 2007, 1:40 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class LOD extends X3DGroupingNode
{
  private SFFloat centerX,centerXDefault;     // Together make one SFVec3F
  private SFFloat centerY,centerYDefault;
  private SFFloat centerZ,centerZDefault;

  private Vector<SFFloat> range;
  private boolean forceTransitions;

  public LOD()
  {
  }

  @Override
  public String getElementName()
  {
    return LOD_ELNAME;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return LODCustomizer.class;
  }

  @Override
  public void initialize()
  {
    String[] fa = parse3(LOD_ATTR_CENTER_DFLT);

    centerX = centerXDefault = new SFFloat(fa[0], null, null);
    centerY = centerYDefault = new SFFloat(fa[1], null, null);
    centerZ = centerZDefault = new SFFloat(fa[2], null, null);

    fa = parse3(LOD_ATTR_BBOXCENTER_DFLT);
    bboxCenterX = bboxCenterXDefault = new SFFloat(fa[0], null, null);
    bboxCenterY = bboxCenterYDefault = new SFFloat(fa[1], null, null);
    bboxCenterZ = bboxCenterZDefault = new SFFloat(fa[2], null, null);

    fa = parse3(LOD_ATTR_BBOXSIZE_DFLT);
    bboxSizeX = bboxSizeXDefault = new SFFloat(fa[0], 0.0f, null, true);
    bboxSizeY = bboxSizeYDefault = new SFFloat(fa[1], 0.0f, null, true);
    bboxSizeZ = bboxSizeZDefault = new SFFloat(fa[2], 0.0f, null, true);

    forceTransitions = Boolean.parseBoolean(LOD_ATTR_FORCETRANSITIONS_DFLT);
    range = parseRange(LOD_ATTR_RANGE_DFLT);

    setContent("\n\t\t<!--TODO add children nodes here-->\n\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);

    String[] fa;
    org.jdom.Attribute attr = root.getAttribute(LOD_ATTR_CENTER_NAME);
    if(attr != null) {
      fa = parse3(attr.getValue());
      centerX = new SFFloat(fa[0], null, null);
      centerY = new SFFloat(fa[1], null, null);
      centerZ = new SFFloat(fa[2], null, null);
    }
    attr = root.getAttribute(LOD_ATTR_BBOXCENTER_NAME);
    if(attr != null) {
      fa = parse3(attr.getValue());
      bboxCenterX = new SFFloat(fa[0], null, null);
      bboxCenterY = new SFFloat(fa[1], null, null);
      bboxCenterZ = new SFFloat(fa[2], null, null);
    }
    attr = root.getAttribute(LOD_ATTR_BBOXSIZE_NAME);
    if(attr != null) {
      fa = parse3(attr.getValue());
      bboxSizeX = new SFFloat(fa[0], 0.0f, null, true);
      bboxSizeY = new SFFloat(fa[1], 0.0f, null, true);
      bboxSizeZ = new SFFloat(fa[2], 0.0f, null, true);
    }
    attr = root.getAttribute(LOD_ATTR_FORCETRANSITIONS_NAME);
    if(attr != null)
       forceTransitions = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(LOD_ATTR_RANGE_NAME);
    if(attr != null)
      range = parseRange(attr.getValue());
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (LOD_ATTR_BBOXCENTER_REQD ||
           (!bboxCenterX.equals(bboxCenterXDefault) ||
            !bboxCenterY.equals(bboxCenterYDefault) ||
            !bboxCenterZ.equals(bboxCenterZDefault)))
    {
      sb.append(" ");
      sb.append(LOD_ATTR_BBOXCENTER_NAME);
      sb.append("='");
      sb.append(bboxCenterX);
      sb.append(" ");
      sb.append(bboxCenterY);
      sb.append(" ");
      sb.append(bboxCenterZ);
      sb.append("'");
    }
    if (LOD_ATTR_BBOXSIZE_REQD ||
           (!bboxSizeX.equals(bboxSizeXDefault) ||
            !bboxSizeY.equals(bboxSizeYDefault) ||
            !bboxSizeZ.equals(bboxSizeZDefault)))
    {
      sb.append(" ");
      sb.append(LOD_ATTR_BBOXSIZE_NAME);
      sb.append("='");
      sb.append(bboxSizeX);
      sb.append(" ");
      sb.append(bboxSizeY);
      sb.append(" ");
      sb.append(bboxSizeZ);
      sb.append("'");
    }

    if (LOD_ATTR_CENTER_REQD ||
           (!centerX.equals(centerXDefault) ||
            !centerY.equals(centerYDefault) ||
            !centerZ.equals(centerZDefault)))
    {
      sb.append(" ");
      sb.append(LOD_ATTR_CENTER_NAME);
      sb.append("='");
      
      sb.append(centerX.toString());
      sb.append(" ");
      sb.append(centerY.toString());
      sb.append(" ");
      sb.append(centerZ.toString());
      sb.append("'");
    }

    if (LOD_ATTR_FORCETRANSITIONS_REQD || forceTransitions != Boolean.parseBoolean(LOD_ATTR_FORCETRANSITIONS_DFLT)) {
      sb.append(" ");
      sb.append(LOD_ATTR_FORCETRANSITIONS_NAME);
      sb.append("='");
      sb.append(forceTransitions);
      sb.append("'");
    }

    if (LOD_ATTR_RANGE_REQD || range.size() > 0) {
      sb.append(" range='");
      sb.append(formatRange());
      sb.append("'");
    }
    return sb.toString();
  }

  public String getCenterX()
  {
    return centerX.toString();
  }

  public void setCenterX(String centerX)
  {
    this.centerX = new SFFloat(centerX, null, null);
  }

  public String getCenterY()
  {
    return centerY.toString();
  }

  public void setCenterY(String centerY)
  {
    this.centerY = new SFFloat(centerY, null, null);
  }

  public String getCenterZ()
  {
    return centerZ.toString();
  }

  public void setCenterZ(String centerZ)
  {
    this.centerZ = new SFFloat(centerZ, null, null);
  }

  public String getRange()
  {
    return formatRange();
  }

  public void setRange(String range)
  {
    this.range = parseRange(range);
  }

  public void setForceTransitions(boolean forceTransitions)
  {
    this.forceTransitions = forceTransitions;
  }

  private String formatRange()
  {
    StringBuilder sb = new StringBuilder();
    for(SFFloat f : range) {
      sb.append(f.toString());
      sb.append(' ');
    }
    if(sb.length()>0)
      sb.setLength(sb.length()-1);  // last space
    return sb.toString();
  }

  private Vector<SFFloat> parseRange(String s)
  {
    Vector<SFFloat> v = new Vector<SFFloat>();
    s = s.trim();
    if(s.length()>0) {
      String[] sa = s.replace(',', ' ').trim().split("\\s");
      for(String st : sa)
        v.add(new SFFloat(st,0.0f,null));
    }
    return v;
  }

   public boolean isForceTransitions() {
    return forceTransitions;
  }


}
