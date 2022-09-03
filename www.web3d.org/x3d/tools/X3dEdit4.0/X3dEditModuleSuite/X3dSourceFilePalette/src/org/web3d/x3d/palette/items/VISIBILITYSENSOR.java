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
import org.web3d.x3d.types.X3DEnvironmentalSensorNode;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * VISIBILITYSENSOR.java
 * Created on Sep 12, 2007, 2:46 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class VISIBILITYSENSOR extends X3DEnvironmentalSensorNode
{
  
  public VISIBILITYSENSOR()
  {
  }
  
  @Override
  public String getElementName()
  {
    return VISIBILITYSENSOR_ELNAME;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return VISIBILITYSENSORCustomizer.class;
  }

  @Override
  public void initialize()
  {
    enabled   = enabledDefault = Boolean.parseBoolean(VISIBILITYSENSOR_ATTR_ENABLED_DFLT);
    String[] sa = parse3(VISIBILITYSENSOR_ATTR_CENTER_DFLT);
    centerX = centerXDefault = new SFFloat(sa[0],null,null);
    centerY = centerYDefault = new SFFloat(sa[1],null,null);
    centerZ = centerZDefault = new SFFloat(sa[2],null,null);
    
    sa = parse3(VISIBILITYSENSOR_ATTR_SIZE_DFLT);
    sizeX = sizeXDefault = new SFFloat(sa[0],0.0f,null);
    sizeY = sizeYDefault = new SFFloat(sa[1],0.0f,null);
    sizeZ = sizeZDefault = new SFFloat(sa[2],0.0f,null);
   }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(VISIBILITYSENSOR_ATTR_ENABLED_NAME);
    if (attr != null)
      enabled = Boolean.parseBoolean(attr.getValue());
    String[] sa;
    attr = root.getAttribute(VISIBILITYSENSOR_ATTR_CENTER_NAME);
    if (attr != null) {
      sa = parse3(attr.getValue());
      centerX = new SFFloat(sa[0], null, null);
      centerY = new SFFloat(sa[1], null, null);
      centerZ = new SFFloat(sa[2], null, null);
    }
    attr = root.getAttribute(VISIBILITYSENSOR_ATTR_SIZE_NAME);
    if (attr != null) {
      sa = parse3(attr.getValue());
      sizeX = new SFFloat(sa[0], 0.0f, null);
      sizeY = new SFFloat(sa[1], 0.0f, null);
      sizeZ = new SFFloat(sa[2], 0.0f, null);
    }
  }
  
  @Override
  public String createAttributes()
  {
    StringBuffer sb = new StringBuffer();
    if (VISIBILITYSENSOR_ATTR_CENTER_REQD ||(!centerX.equals(centerXDefault)) ||
                                            (!centerY.equals(centerYDefault)) ||
                                            (!centerZ.equals(centerZDefault))) {
      sb.append(" ");
      sb.append(VISIBILITYSENSOR_ATTR_CENTER_NAME);
      sb.append("='");
      sb.append(centerX);
      sb.append(" ");
      sb.append(centerY);
      sb.append(" ");
      sb.append(centerZ);
      sb.append("'");
    }
    if (VISIBILITYSENSOR_ATTR_SIZE_REQD ||(!sizeX.equals(sizeXDefault)) ||
                                          (!sizeY.equals(sizeYDefault)) ||
                                          (!sizeZ.equals(sizeZDefault))) {
      sb.append(" ");
      sb.append(VISIBILITYSENSOR_ATTR_SIZE_NAME);
      sb.append("='");
      sb.append(sizeX);
      sb.append(" ");
      sb.append(sizeY);
      sb.append(" ");
      sb.append(sizeZ);
      sb.append("'");
    }
    if (VISIBILITYSENSOR_ATTR_ENABLED_REQD || enabled != enabledDefault) {
      sb.append(" ");
      sb.append(VISIBILITYSENSOR_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }
    return sb.toString();
  }  
}
