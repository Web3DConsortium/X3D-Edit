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

import org.web3d.x3d.types.X3DGroupingNode;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * GEOLOCATION.java
 * Created on 23 Apr 2008
 * 
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 * 
 * @author Mike Bailey
 * @version $Id$
 */
public class GEOLOCATION extends X3DGroupingNode
{ 
  private String[]  geoSystem;
  private SFDouble[] geoCoords         = new SFDouble[3];
  private SFDouble[] geoCoordsDefault  = new SFDouble[3];

  public GEOLOCATION()
  {
  }

  @Override
  public String getElementName()
  {
    return GEOLOCATION_ELNAME;
  }
  
  @Override
  public void initialize()
  {
    geoSystem = GEOLOCATION_ATTR_GEOSYSTEM_DFLT;
    
//    String[] coo = parseMFStringIntoStringArray(GEOLOCATION_ATTR_GEOCOORDS_DFLT,true); // remove quotes
//    geoCoordsDefault = geoCoords = parseToSFDoubleArray(coo);
    // alternate approoach to geoCoords in GeoOrigin
    String[] da = parse3(GEOLOCATION_ATTR_GEOCOORDS_DFLT);
    geoCoordsDefault[0] = geoCoords[0] = new SFDouble(da[0], null, null);
    geoCoordsDefault[1] = geoCoords[1] = new SFDouble(da[1], null, null);
    geoCoordsDefault[2] = geoCoords[2] = new SFDouble(da[2], null, null);

    String[] fa = parse3(GEOLOCATION_ATTR_BBOXCENTER_DFLT);
    bboxCenterX = bboxCenterXDefault = new SFFloat(fa[0], null, null);
    bboxCenterY = bboxCenterYDefault = new SFFloat(fa[1], null, null);
    bboxCenterZ = bboxCenterZDefault = new SFFloat(fa[2], null, null);

    fa = parse3(GEOLOCATION_ATTR_BBOXSIZE_DFLT);
    bboxSizeX = bboxSizeXDefault = new SFFloat(fa[0], 0.0f, null, true);
    bboxSizeY = bboxSizeYDefault = new SFFloat(fa[1], 0.0f, null, true);
    bboxSizeZ = bboxSizeZDefault = new SFFloat(fa[2], 0.0f, null, true);

    setContent(linesep+"<!-- Wrap this node around other scene content and optionally insert an extended-precision GeoOrigin node -->"+linesep);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;
    attr = root.getAttribute(GEOLOCATION_ATTR_GEOSYSTEM_NAME);
    if(attr != null) {
      geoSystem = splitStringIntoStringArray(attr.getValue());
    }
    attr = root.getAttribute(GEOLOCATION_ATTR_GEOCOORDS_NAME);
    if(attr != null) {
      geoCoords = parseToSFDoubleArray(splitStringIntoStringArray(attr.getValue()));
    }
    attr = root.getAttribute(GEOLOCATION_ATTR_BBOXCENTER_NAME);
    if (attr != null) {
      String[] fa = parse3(attr.getValue());
      bboxCenterX = new SFFloat(fa[0], null, null);
      bboxCenterY = new SFFloat(fa[1], null, null);
      bboxCenterZ = new SFFloat(fa[2], null, null);
    }
    attr = root.getAttribute(GEOLOCATION_ATTR_BBOXSIZE_NAME);
    if (attr != null) {
      String[] fa = parse3(attr.getValue());
      bboxSizeX = new SFFloat(fa[0], 0.0f, null, true);
      bboxSizeY = new SFFloat(fa[1], 0.0f, null, true);
      bboxSizeZ = new SFFloat(fa[2], 0.0f, null, true);
    }
  }

  @Override
  public String createAttributes()
  {
    StringBuffer sb = new StringBuffer();

    if (GEOLOCATION_ATTR_BBOXCENTER_REQD ||
            (!bboxCenterX.equals(bboxCenterXDefault) ||
             !bboxCenterY.equals(bboxCenterYDefault) ||
             !bboxCenterZ.equals(bboxCenterZDefault)))
    {
      sb.append(" ");
      sb.append(GEOLOCATION_ATTR_BBOXCENTER_NAME);
      sb.append("='");
      sb.append(bboxCenterX);
      sb.append(" ");
      sb.append(bboxCenterY);
      sb.append(" ");
      sb.append(bboxCenterZ);
      sb.append("'");
    }
    if (GEOLOCATION_ATTR_BBOXSIZE_REQD ||
           (!bboxSizeX.equals(bboxSizeXDefault) ||
            !bboxSizeY.equals(bboxSizeYDefault) ||
            !bboxSizeZ.equals(bboxSizeZDefault)))
    {
      sb.append(" ");
      sb.append(GEOLOCATION_ATTR_BBOXSIZE_NAME);
      sb.append("='");
      sb.append(bboxSizeX);
      sb.append(" ");
      sb.append(bboxSizeY);
      sb.append(" ");
      sb.append(bboxSizeZ);
      sb.append("'");
    }
    if (GEOLOCATION_ATTR_GEOSYSTEM_REQD || !geoSystemEqualsDefault(geoSystem))
    {
      sb.append(" ");
      sb.append(GEOLOCATION_ATTR_GEOSYSTEM_NAME);
      sb.append("='");
      for (String s : geoSystem) {
        sb.append("\"");
        sb.append(s);
        sb.append("\" ");
      }
      sb.setLength(sb.length() - 1); // last space
      sb.append("'");
    }
    
    if(GEOLOCATION_ATTR_GEOCOORDS_REQD ||
        (!geoCoords[0].equals(geoCoordsDefault[0])) ||
        (!geoCoords[1].equals(geoCoordsDefault[1])) ||
        (!geoCoords[2].equals(geoCoordsDefault[2]))) {
      sb.append(" ");
      sb.append(GEOLOCATION_ATTR_GEOCOORDS_NAME);
      sb.append("='");
      sb.append(geoCoords[0]);
      sb.append(" ");
      sb.append(geoCoords[1]);
      sb.append(" ");
      sb.append(geoCoords[2]);
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
}