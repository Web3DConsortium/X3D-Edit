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
import org.web3d.x3d.types.X3DNode;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFDouble;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * GEOORIGIN.java
 * Created on 23 Apr 2008
 * 
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 * 
 * @author Mike Bailey
 * @version $Id$
 */
public class GEOORIGIN extends X3DNode
{ 
  private String[]  geoSystem;
  private SFDouble[] geoCoords;
  private SFDouble[] geoCoordsDefault;
  private boolean   rotateYUp;
  
  public GEOORIGIN()
  {
  }

  @Override
  public String getElementName()
  {
    return GEOORIGIN_ELNAME;
  }
  
  @Override
  public void initialize()
  {
    geoSystem = GEOORIGIN_ATTR_GEOSYSTEM_DFLT;
    // alternate approoach to geoCoords in GeoLocation
    String[] coo = GEOORIGIN_ATTR_GEOCOORDS_DFLT.replace(',', ' ').trim().split("\\s"); // SFVec3d so no need to remove quotes
    geoCoordsDefault = geoCoords = parseToSFDoubleArray(coo);
    rotateYUp = Boolean.parseBoolean(GEOORIGIN_ATTR_ROTATEYUP_DFLT);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;
    attr = root.getAttribute(GEOORIGIN_ATTR_GEOSYSTEM_NAME);
    if(attr != null) {
      geoSystem = splitStringIntoStringArray(attr.getValue());
    }
    attr = root.getAttribute(GEOORIGIN_ATTR_GEOCOORDS_NAME);
    if(attr != null) {
      geoCoords = parseToSFDoubleArray(splitStringIntoStringArray(attr.getValue()));
    }
    attr = root.getAttribute(GEOORIGIN_ATTR_ROTATEYUP_NAME);
    if(attr != null) {
      rotateYUp = Boolean.parseBoolean(attr.getValue());
    }
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    if (GEOORIGIN_ATTR_GEOSYSTEM_REQD || !geoSystemEqualsDefault(geoSystem))
    {
      sb.append(" ");
      sb.append(GEOORIGIN_ATTR_GEOSYSTEM_NAME);
      sb.append("='");
      for (String s : geoSystem) {
        sb.append("\"");
        sb.append(s);
        sb.append("\" ");
      }
      sb.setLength(sb.length() - 1); // last space
      sb.append("'");
    }    
    if(GEOORIGIN_ATTR_GEOCOORDS_REQD ||
        (!geoCoords[0].equals(geoCoordsDefault[0])) ||
        (!geoCoords[1].equals(geoCoordsDefault[1])) ||
        (!geoCoords[2].equals(geoCoordsDefault[2]))) {
      sb.append(" ");
      sb.append(GEOORIGIN_ATTR_GEOCOORDS_NAME);
      sb.append("='");
      sb.append(geoCoords[0]);
      sb.append(" ");
      sb.append(geoCoords[1]);
      sb.append(" ");
      sb.append(geoCoords[2]);
      sb.append("'");
    }
    if(GEOORIGIN_ATTR_ROTATEYUP_REQD || (rotateYUp != Boolean.parseBoolean(GEOORIGIN_ATTR_ROTATEYUP_DFLT))) {
      sb.append(" ");
      sb.append(GEOORIGIN_ATTR_ROTATEYUP_NAME);
      sb.append("='");
      sb.append(rotateYUp);
      sb.append("'");
      
    }
    return sb.toString();
  }
  
  public String[] getGeoCoords()
  {
    String[][] saa = makeStringArray(new SFDouble[][]{geoCoords});
    return saa[0];
  }

  public void setGeoCoords(String[] newGeoCoords)
  {
    geoCoords = this.parseToSFDoubleArray(newGeoCoords);
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

  public void setGeoSystem(String[] geoSystem)
  {
    this.geoSystem = geoSystem;
  }
  
  public boolean isRotateYUp()
  {
    return rotateYUp;
  }
  public void setRotateYUp(boolean b)
  {
    rotateYUp = b;
  }

    @Override
    public String getDefaultContainerField() {
        return "geoOrigin";
    }
}