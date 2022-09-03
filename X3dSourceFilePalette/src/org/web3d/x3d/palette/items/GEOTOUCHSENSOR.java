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
import org.web3d.x3d.types.X3DTouchSensorNode;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.palette.X3DPaletteUtilities.*;

/**
 * GEOTOUCHSENSOR.java
 * Created on 31 Narch 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class GEOTOUCHSENSOR extends X3DTouchSensorNode
{
  private String description;
  
  private String[]   geoSystem;
  
  public GEOTOUCHSENSOR()
  {
  }
  
  @Override
  public String getElementName()
  {
    return GEOTOUCHSENSOR_ELNAME;
  } 
   
  @Override
  public void initialize()
  {
    description = GEOTOUCHSENSOR_ATTR_DESCRIPTION_DFLT;
    enabled = enabledDefault = Boolean.parseBoolean(GEOTOUCHSENSOR_ATTR_ENABLED_DFLT);
    geoSystem   = GEOTOUCHSENSOR_ATTR_GEOSYSTEM_DFLT;
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(GEOTOUCHSENSOR_ATTR_DESCRIPTION_NAME);
    if (attr != null)
      description = attr.getValue();
    
    attr = root.getAttribute(GEOTOUCHSENSOR_ATTR_ENABLED_NAME);
    if (attr != null)
      enabled = Boolean.parseBoolean(attr.getValue());

    attr = root.getAttribute(GEOTOUCHSENSOR_ATTR_GEOSYSTEM_NAME);
    if (attr != null)
      geoSystem = splitStringIntoStringArray(attr.getValue());
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return GEOTOUCHSENSORCustomizer.class;
  }

  @Override
  public String createAttributes()
  {
    StringBuffer sb = new StringBuffer();

    if (GEOTOUCHSENSOR_ATTR_DESCRIPTION_REQD || !description.equals(GEOTOUCHSENSOR_ATTR_DESCRIPTION_DFLT)) {
      sb.append(" ");
      sb.append(GEOTOUCHSENSOR_ATTR_DESCRIPTION_NAME);
      sb.append("='");
      sb.append(escapeXmlCharacters(description));
      sb.append("'");
    }

    if (GEOTOUCHSENSOR_ATTR_ENABLED_REQD || enabled != enabledDefault) {
      sb.append(" ");
      sb.append(GEOTOUCHSENSOR_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }

    if (GEOTOUCHSENSOR_ATTR_GEOSYSTEM_REQD || !geoSystemEqualsDefault(geoSystem))
    {
      sb.append(" ");
      sb.append(GEOTOUCHSENSOR_ATTR_GEOSYSTEM_NAME);
      sb.append("='");
      for (String s : geoSystem) {
        sb.append("\"");
        sb.append(s);
        sb.append("\" ");
      }
      sb.setLength(sb.length() - 1); // last space
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
 
  public String getGeoSystem()
  {
      StringBuilder sb = new StringBuilder();

      for (String s : geoSystem) {
        sb.append(s);
        sb.append(" ");
      }
      sb.setLength(sb.length() - 1); // last space

      return sb.toString();
  }

  public void setGeoSystem(String[] newGeoSystem)
  {
    this.geoSystem = newGeoSystem;
  }
}
