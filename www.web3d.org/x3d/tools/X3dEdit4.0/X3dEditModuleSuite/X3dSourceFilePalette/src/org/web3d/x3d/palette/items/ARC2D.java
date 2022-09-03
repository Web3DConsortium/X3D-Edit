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

/**
 * ARC2D.java
 * Created on Sep 14, 2007, 9:57 AM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class ARC2D extends X3DGeometryNode
{
  private SFFloat startAngle, startAngleDefault;
  private SFFloat endAngle, endAngleDefault;
  private SFFloat radius, radiusDefault;
  
  public ARC2D()
  {
  }

  @Override
  public String getElementName()
  {
    return ARC2D_ELNAME;
  }
  
  @Override
  public void initialize()
  {
    radius     = radiusDefault     = new SFFloat(ARC2D_ATTR_RADIUS_DFLT,     0.0f, null);
    startAngle = startAngleDefault = new SFFloat(ARC2D_ATTR_STARTANGLE_DFLT, (float)-Math.PI*2.0f, (float) Math.PI*2.0f);
    endAngle   = endAngleDefault   = new SFFloat(ARC2D_ATTR_ENDANGLE_DFLT,   (float)-Math.PI*2.0f, (float) Math.PI*2.0f);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(ARC2D_ATTR_RADIUS_NAME);
    if (attr != null)
      radius = new SFFloat(attr.getValue(), 0.0f, null);
    attr = root.getAttribute(ARC2D_ATTR_STARTANGLE_NAME);
    if (attr != null)
      startAngle = new SFFloat(attr.getValue(), (float) -Math.PI * 2.0f, (float) Math.PI * 2.0f);
    attr = root.getAttribute(ARC2D_ATTR_ENDANGLE_NAME);
    if (attr != null)
      endAngle = new SFFloat(attr.getValue(), (float) -Math.PI * 2.0f, (float) Math.PI * 2.0f);
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (ARC2D_ATTR_RADIUS_REQD || !radius.equals(radiusDefault)) {
      sb.append(" ");
      sb.append(ARC2D_ATTR_RADIUS_NAME);
      sb.append("='");
      sb.append(radius);
      sb.append("'");
    }
    if (ARC2D_ATTR_STARTANGLE_REQD || !startAngle.equals(startAngleDefault)) {
      sb.append(" ");
      sb.append(ARC2D_ATTR_STARTANGLE_NAME);
      sb.append("='");
      sb.append(startAngle);
      sb.append("'");
    }
    if (ARC2D_ATTR_ENDANGLE_REQD || !endAngle.equals(endAngleDefault)) {
      sb.append(" ");
      sb.append(ARC2D_ATTR_ENDANGLE_NAME);
      sb.append("='");
      sb.append(endAngle);
      sb.append("'");
    }
    return sb.toString();
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return ARC2DCustomizer.class;
  }

  public String getStartAngle()
  {
    return radiansFormat.format(startAngle.getValue());
  }

  public void setStartAngle(String startAngle)
  {
    this.startAngle = new SFFloat(startAngle, (float)-Math.PI*2.0f, (float) Math.PI*2.0f);
  }

  public String getEndAngle()
  {
    return radiansFormat.format(endAngle.getValue());
  }

  public void setEndAngle(String endAngle)
  {
    this.endAngle = new SFFloat(endAngle, (float)-Math.PI*2.0f, (float) Math.PI*2.0f);
  }

  public String getRadius()
  {
    return radius.toString();
  }

  public void setRadius(String radius)
  {
    this.radius = new SFFloat(radius, 0.0f, null);
  } 
}
