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

import org.web3d.x3d.types.X3DComposableVolumeRenderStyleNode;
import javax.swing.text.JTextComponent;

import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * PROJECTIONVOLUMESTYLE.java
 * Created on 20 November 2011
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey and Don Brutzman
 * @version $Id$
 */
public class PROJECTIONVOLUMESTYLE extends X3DComposableVolumeRenderStyleNode
{
  private SFFloat intensityThreshold, intensityThresholdDefault;
  private String  type, typeDefault;
  
  public PROJECTIONVOLUMESTYLE()
  {
  }

  @Override
  public String getElementName()
  {
    return PROJECTIONVOLUMESTYLE_ELNAME;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return PROJECTIONVOLUMESTYLECustomizer.class;
  }

  @Override
  @SuppressWarnings("NestedAssignment")
  public void initialize()
  {
    enabled = enabledDefault = Boolean.parseBoolean(PROJECTIONVOLUMESTYLE_ATTR_ENABLED_DFLT);
    
    intensityThreshold = intensityThresholdDefault = new SFFloat(PROJECTIONVOLUMESTYLE_ATTR_INTENSITYTHRESHOLD_DFLT, 0.0f, 1.0f);
    
        setType(typeDefault = PROJECTIONVOLUMESTYLE_ATTR_TYPE_DFLT);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(PROJECTIONVOLUMESTYLE_ATTR_ENABLED_NAME);
    if (attr != null) {
      enabled = Boolean.parseBoolean(attr.getValue());
    }
    attr = root.getAttribute(PROJECTIONVOLUMESTYLE_ATTR_INTENSITYTHRESHOLD_NAME);
    if (attr != null) {
        intensityThreshold  = new SFFloat(attr.getValue(), 0.0f, 1.0f);
    }
    attr = root.getAttribute(PROJECTIONVOLUMESTYLE_ATTR_TYPE_NAME);
    if (attr != null) {
            setType(attr.getValue());
    }
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (PROJECTIONVOLUMESTYLE_ATTR_ENABLED_REQD || enabled != enabledDefault) {
      sb.append(" ");
      sb.append(PROJECTIONVOLUMESTYLE_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }
    if (PROJECTIONVOLUMESTYLE_ATTR_INTENSITYTHRESHOLD_REQD || (intensityThreshold != intensityThresholdDefault)) {
      sb.append(" ");
      sb.append(PROJECTIONVOLUMESTYLE_ATTR_INTENSITYTHRESHOLD_NAME);
      sb.append("='");
      sb.append(intensityThreshold);
      sb.append("'");
    }
    if (PROJECTIONVOLUMESTYLE_ATTR_TYPE_REQD || (!type.equals(typeDefault))) {
      sb.append(" ");
      sb.append(PROJECTIONVOLUMESTYLE_ATTR_TYPE_NAME);
      sb.append("='");
      sb.append(getType());
      sb.append("'");
    }

    return sb.toString();
  }

  public String getIntensityThreshold()
  {
    return Float.toString(intensityThreshold.getValue());
  }

  public void setIntensityhreshold(String intensityThreshold)
  {
    this.intensityThreshold = new SFFloat(intensityThreshold, 0.0f, 1.0f);
  }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(String type) {
        this.type = type;
    }
}