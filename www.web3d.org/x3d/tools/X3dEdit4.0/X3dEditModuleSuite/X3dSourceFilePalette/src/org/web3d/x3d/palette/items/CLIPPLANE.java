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
import org.web3d.x3d.types.X3DChildNode;

import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * CLIPPLANE.java
 * Created on 2 April 2013
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Don Brutzman, Mike Bailey
 * @version $Id$
 */
public class CLIPPLANE extends X3DChildNode
{
  private boolean enabled;
  SFFloat plane0, plane0Default;
  SFFloat plane1, plane1Default;
  SFFloat plane2, plane2Default;
  SFFloat plane3, plane3Default;

  public CLIPPLANE()
  {
      // TODO
      this.setTraceEventsSelectionAvailable(true);
      this.setTraceEventsTooltip("Trace " + getElementName() + " events on X3D browser console");
  }

  @Override
  public final String getElementName()
  {
    return CLIPPLANE_ELNAME;
  }

  @Override
  @SuppressWarnings("NestedAssignment")
  public void initialize()
  {
    enabled = Boolean.parseBoolean(CLIPPLANE_ATTR_ENABLED_DFLT);

    String[] fa = parse4(CLIPPLANE_ATTR_PLANE_DFLT);
    plane0 = plane0Default = new SFFloat(fa[0], null, null);
    plane1 = plane1Default = new SFFloat(fa[1], null, null);
    plane2 = plane2Default = new SFFloat(fa[2], null, null);
    plane3 = plane3Default = new SFFloat(fa[3], null, null);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);

    org.jdom.Attribute attr = root.getAttribute(CLIPPLANE_ATTR_ENABLED_NAME);
    if (attr != null)
      enabled = Boolean.parseBoolean(attr.getValue());

    String[] fa;
    attr = root.getAttribute(CLIPPLANE_ATTR_PLANE_NAME);
    if (attr != null) {
      fa = parse4(attr.getValue());
      plane0 = new SFFloat(fa[0], null, null);
      plane1 = new SFFloat(fa[1], null, null);
      plane2 = new SFFloat(fa[2], null, null);
      plane3 = new SFFloat(fa[3], null, null);
    }
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return CLIPPLANECustomizer.class;
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if(CLIPPLANE_ATTR_PLANE_REQD ||
       (!plane0.equals(plane0Default) ||
        !plane1.equals(plane1Default) ||
        !plane2.equals(plane2Default) ||
        !plane3.equals(plane3Default))) {
      sb.append(" ");
      sb.append(CLIPPLANE_ATTR_PLANE_NAME);
      sb.append("='");
      sb.append(plane0);
      sb.append(" ");
      sb.append(plane1);
      sb.append(" ");
      sb.append(plane2);
      sb.append(" ");
      sb.append(plane3);
      sb.append("'");
    }

    if (CLIPPLANE_ATTR_ENABLED_REQD || (enabled != Boolean.parseBoolean(CLIPPLANE_ATTR_ENABLED_DFLT))) {
      sb.append(" ");
      sb.append(CLIPPLANE_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }

    return sb.toString();
  }

  public boolean isEnabled()
  {
    return enabled;
  }

  public void setEnabled(boolean wh)
  {
    enabled = wh;
  }

  public String getPlane0()
  {
    return Float.toString(plane0.getValue());
  }
  public String getPlane1()
  {
    return Float.toString(plane1.getValue());
  }
  public String getPlane2()
  {
    return Float.toString(plane2.getValue());
  }
  public String getPlane3()
  {
    return Float.toString(plane3.getValue());
  }

  public void setPlane(String p0, String p1, String p2, String p3)
  {
      plane0 = new SFFloat(p0, null, null);
      plane1 = new SFFloat(p1, null, null);
      plane2 = new SFFloat(p2, null, null);
      plane3 = new SFFloat(p3, null, null);
  }
}
