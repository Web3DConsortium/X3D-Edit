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

import org.web3d.x3d.types.X3DTexture3DNode;
import javax.swing.text.JTextComponent;

import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * COMPOSEDTEXTURE3D.java
 * Created on 20 November 2011
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class COMPOSEDTEXTURE3D extends X3DTexture3DNode
{
  
  public COMPOSEDTEXTURE3D()
  {
  }

  @Override
  public String getElementName()
  {
    return COMPOSEDTEXTURE3D_ELNAME;
  }

  @Override
  public void initialize()
  {
    repeatS = Boolean.parseBoolean(COMPOSEDTEXTURE3D_ATTR_REPEATS_DFLT);
    repeatT = Boolean.parseBoolean(COMPOSEDTEXTURE3D_ATTR_REPEATT_DFLT);
    repeatR = Boolean.parseBoolean(COMPOSEDTEXTURE3D_ATTR_REPEATR_DFLT);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);

    org.jdom.Attribute attr = root.getAttribute(COMPOSEDTEXTURE3D_ATTR_REPEATS_NAME);
    if (attr != null)
      repeatS = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(COMPOSEDTEXTURE3D_ATTR_REPEATT_NAME);
    if (attr != null)  
      repeatT = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(COMPOSEDTEXTURE3D_ATTR_REPEATR_NAME);
    if (attr != null)  
      repeatR = Boolean.parseBoolean(attr.getValue());
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return COMPOSEDTEXTURE3DCustomizer.class;
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    if (COMPOSEDTEXTURE3D_ATTR_REPEATS_REQD || repeatS != Boolean.parseBoolean(COMPOSEDTEXTURE3D_ATTR_REPEATS_DFLT)) {
      sb.append(" ");
      sb.append(COMPOSEDTEXTURE3D_ATTR_REPEATS_NAME);
      sb.append("='");
      sb.append(repeatS);
      sb.append("'");
    }
    if (COMPOSEDTEXTURE3D_ATTR_REPEATT_REQD || repeatT != Boolean.parseBoolean(COMPOSEDTEXTURE3D_ATTR_REPEATT_DFLT)) {
      sb.append(" ");
      sb.append(COMPOSEDTEXTURE3D_ATTR_REPEATT_NAME);
      sb.append("='");
      sb.append(repeatT);
      sb.append("'");
    }
    if (COMPOSEDTEXTURE3D_ATTR_REPEATR_REQD || repeatR != Boolean.parseBoolean(COMPOSEDTEXTURE3D_ATTR_REPEATR_DFLT)) {
      sb.append(" ");
      sb.append(COMPOSEDTEXTURE3D_ATTR_REPEATR_NAME);
      sb.append("='");
      sb.append(repeatR);
      sb.append("'");
    }

    return sb.toString();
  }
}
