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
import org.web3d.x3d.types.X3DDragSensorNode;

import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;
import static org.web3d.x3d.palette.X3DPaletteUtilities.*;

/**
 * SPHERESENSOR.java
 * Created on Sep 12, 2007, 2:46 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class SPHERESENSOR extends X3DDragSensorNode
{
  private String description;
  private SFFloat offsetX, offset0Default;
  private SFFloat offsetY, offset1Default;
  private SFFloat offsetZ, offset2Default;
  private SFFloat offsetAngle, offset3Default;
  private boolean autoOffset, autoOffsetDefault;
  
  public SPHERESENSOR()
  {
      this.setTraceEventsSelectionAvailable(true);
      this.setTraceEventsTooltip("Trace " + getElementName() + " events on X3D browser console");
  }
  
  @Override
  public final String getElementName()
  {
    return SPHERESENSOR_ELNAME;
  } 
   
  @Override
  @SuppressWarnings("NestedAssignment")
  public void initialize()
  {
    description = SPHERESENSOR_ATTR_DESCRIPTION_DFLT;
    enabled    = enabledDefault    = Boolean.parseBoolean(SPHERESENSOR_ATTR_ENABLED_DFLT);
    autoOffset = autoOffsetDefault = Boolean.parseBoolean(SPHERESENSOR_ATTR_AUTOOFFSET_DFLT);
    String[] sa = parse4(SPHERESENSOR_ATTR_OFFSET_DFLT);
    offsetX = offset0Default = new SFFloat(sa[0],null,null);
    offsetY = offset1Default = new SFFloat(sa[1],null,null);
    offsetZ = offset2Default = new SFFloat(sa[2],null,null);
    offsetAngle = offset3Default = new SFFloat(sa[3],null,null);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(SPHERESENSOR_ATTR_DESCRIPTION_NAME);
    if (attr != null)
      description = attr.getValue();
    attr = root.getAttribute(SPHERESENSOR_ATTR_ENABLED_NAME);
    if (attr != null)
      enabled = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(SPHERESENSOR_ATTR_AUTOOFFSET_NAME);
    if (attr != null)
      autoOffset = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(SPHERESENSOR_ATTR_OFFSET_NAME);
    if (attr != null) {
      String[] sa = parse4(attr.getValue());
      offsetX = new SFFloat(sa[0], null, null);
      offsetY = new SFFloat(sa[1], null, null);
      offsetZ = new SFFloat(sa[2], null, null);
      offsetAngle = new SFFloat(sa[3], null, null);
    }
  }
 
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return SPHERESENSORCustomizer.class;
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (SPHERESENSOR_ATTR_AUTOOFFSET_REQD || autoOffset != autoOffsetDefault) {
      sb.append(" ");
      sb.append(SPHERESENSOR_ATTR_AUTOOFFSET_NAME);
      sb.append("='");
      sb.append(autoOffset);
      sb.append("'");
    }
    if (SPHERESENSOR_ATTR_DESCRIPTION_REQD || !description.equals(SPHERESENSOR_ATTR_DESCRIPTION_DFLT)) {
      sb.append(" ");
      sb.append(SPHERESENSOR_ATTR_DESCRIPTION_NAME);
      sb.append("='");
      sb.append(escapeXmlCharacters(description));
      sb.append("'");
    }
    if (SPHERESENSOR_ATTR_ENABLED_REQD || enabled != enabledDefault) {
      sb.append(" ");
      sb.append(SPHERESENSOR_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }
    if (SPHERESENSOR_ATTR_OFFSET_REQD || !offsetX.equals(offset0Default) || !offsetY.equals(offset1Default) || !offsetZ.equals(offset2Default) || !offsetAngle.equals(offset3Default)) {
      sb.append(" ");
      sb.append(SPHERESENSOR_ATTR_OFFSET_NAME);
      sb.append("='");
      sb.append(offsetX);
      sb.append(" ");
      sb.append(offsetY);
      sb.append(" ");
      sb.append(offsetZ);
      sb.append(" ");
      sb.append(offsetAngle);
      sb.append("'");
    }
    return sb.toString();
  }
  
  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public boolean isAutoOffset()
  {
    return autoOffset;
  }

  public void setAutoOffset(boolean autoOffset)
  {
    this.autoOffset = autoOffset;
  }

  public String getOffsetX()
  {
    return offsetX.toString();
  }

  public void setOffsetX(String offsetX)
  {
    this.offsetX = new SFFloat(offsetX,null,null);
  }

  public String getOffsetY()
  {
    return offsetY.toString();
  }

  public void setOffsetY(String offsetY)
  {
    this.offsetY = new SFFloat(offsetY,null,null);
  }

  public String getOffsetZ()
  {
    return offsetZ.toString();
  }

  public void setOffsetZ(String offsetZ)
  {
    this.offsetZ = new SFFloat(offsetZ,null,null);
  }
  
  public String getOffsetAngle()
  {
      return radiansFormat.format(offsetAngle.getValue());
  }

  public void setOffsetAngle(String offsetAngle)
  {
    this.offsetAngle = new SFFloat(offsetAngle,null,null);
  }
}
