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
 * GEOPROXIMITYSENSOR.java
 * Created on 30 March 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class GEOPROXIMITYSENSOR extends X3DEnvironmentalSensorNode
{
  private SFDouble geoCenter0, geoCenter0Default;
  private SFDouble geoCenter1, geoCenter1Default;
  private SFDouble geoCenter2, geoCenter2Default;
  private SFFloat  size0, size0Default;
  private SFFloat  size1, size1Default;
  private SFFloat  size2, size2Default;
  
  private String[] geoSystem;
  
  public GEOPROXIMITYSENSOR()
  {
  }
  
  @Override
  public String getElementName()
  {
    return GEOPROXIMITYSENSOR_ELNAME;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return GEOPROXIMITYSENSORCustomizer.class;
  }

  @Override
  public void initialize()
  {
    enabled   = enabledDefault = Boolean.parseBoolean(GEOPROXIMITYSENSOR_ATTR_ENABLED_DFLT);
    String[] sa = parse3(GEOPROXIMITYSENSOR_ATTR_GEOCENTER_DFLT);
    geoCenter0 = geoCenter0Default = new SFDouble(sa[0],null,null);
    geoCenter1 = geoCenter1Default = new SFDouble(sa[1],null,null);
    geoCenter2 = geoCenter2Default = new SFDouble(sa[2],null,null);
    
    sa = parse3(GEOPROXIMITYSENSOR_ATTR_SIZE_DFLT);
    size0 = size0Default = new SFFloat(sa[0],0.0f,null);
    size1 = size1Default = new SFFloat(sa[1],0.0f,null);
    size2 = size2Default = new SFFloat(sa[2],0.0f,null);    
    
    geoSystem = GEOPROXIMITYSENSOR_ATTR_GEOSYSTEM_DFLT;
   }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(GEOPROXIMITYSENSOR_ATTR_ENABLED_NAME);
    if (attr != null)
      enabled = Boolean.parseBoolean(attr.getValue());
    String[] sa;
    attr = root.getAttribute(GEOPROXIMITYSENSOR_ATTR_GEOCENTER_NAME);
    if (attr != null) {
      sa = parse3(attr.getValue());
      geoCenter0 = new SFDouble(sa[0], null, null);
      geoCenter1 = new SFDouble(sa[1], null, null);
      geoCenter2 = new SFDouble(sa[2], null, null);
    }
    attr = root.getAttribute(GEOPROXIMITYSENSOR_ATTR_SIZE_NAME);
    if (attr != null) {
      sa = parse3(attr.getValue());
      size0 = new SFFloat(sa[0], 0.0f, null);
      size1 = new SFFloat(sa[1], 0.0f, null);
      size2 = new SFFloat(sa[2], 0.0f, null);
    }
    
    attr = root.getAttribute(GEOPROXIMITYSENSOR_ATTR_GEOSYSTEM_NAME);
    if (attr != null)
      geoSystem = splitStringIntoStringArray(attr.getValue());
  }
  
  @Override
  public String createAttributes()
  {
    StringBuffer sb = new StringBuffer();
    if (GEOPROXIMITYSENSOR_ATTR_ENABLED_REQD || (enabled != enabledDefault)) {
      sb.append(" ");
      sb.append(GEOPROXIMITYSENSOR_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }
    if (GEOPROXIMITYSENSOR_ATTR_GEOCENTER_REQD || (!geoCenter0.equals(geoCenter0Default)) ||
                                                  (!geoCenter1.equals(geoCenter1Default)) ||
                                                  (!geoCenter2.equals(geoCenter2Default))) {
      sb.append(" ");
      sb.append(GEOPROXIMITYSENSOR_ATTR_GEOCENTER_NAME);
      sb.append("='");
      sb.append(geoCenter0);
      sb.append(" ");
      sb.append(geoCenter1);
      sb.append(" ");
      sb.append(geoCenter2);
      sb.append("'");
    }
    if (GEOPROXIMITYSENSOR_ATTR_SIZE_REQD || (!size0.equals(size0Default)) ||
                                             (!size1.equals(size1Default)) ||
                                             (!size2.equals(size2Default))) {

      sb.append(" ");
      sb.append(GEOPROXIMITYSENSOR_ATTR_SIZE_NAME);
      sb.append("='");
      sb.append(size0);
      sb.append(" ");
      sb.append(size1);
      sb.append(" ");
      sb.append(size2);
      sb.append("'");
    }
    
    if (GEOPROXIMITYSENSOR_ATTR_GEOSYSTEM_REQD || !geoSystemEqualsDefault(geoSystem))
    {
      sb.append(" ");
      sb.append(GEOPROXIMITYSENSOR_ATTR_GEOSYSTEM_NAME);
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

  public String getCenter0()
  {
    return geoCenter0.toString();
  }

  public void setCenter0(String center0)
  {
    this.geoCenter0 = new SFDouble(center0,null,null);
  }

  public String getCenter1()
  {
    return geoCenter1.toString();
  }

  public void setCenter1(String center1)
  {
    this.geoCenter1 = new SFDouble(center1,null,null);
  }

  public String getCenter2()
  {
    return geoCenter2.toString();
  }

  public void setCenter2(String center2)
  {
    this.geoCenter2 = new SFDouble(center2,null,null);
  }

  public String getSize0()
  {
    return size0.toString();
  }

  public void setSize0(String size0)
  {
    this.size0 = new SFFloat(size0,0.0f,null);
  }

  public String getSize1()
  {
    return size1.toString();
  }

  public void setSize1(String size1)
  {
    this.size1 = new SFFloat(size1,0.0f,null);
  }

  public String getSize2()
  {
    return size2.toString();
  }

  public void setSize2(String size2)
  {
    this.size2 = new SFFloat(size2,0.0f,null);
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
