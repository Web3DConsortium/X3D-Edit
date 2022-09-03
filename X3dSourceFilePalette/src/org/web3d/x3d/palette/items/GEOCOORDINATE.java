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
import org.web3d.x3d.types.X3DCoordinateNode;

import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * GEOCOORDINATE.java
 * Created on 28 March 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class GEOCOORDINATE extends X3DCoordinateNode
{
  private SFDouble[][] point, pointDefault;
  
  private String[] geoSystem;
  
  public GEOCOORDINATE()
  {
  }

  @Override
  public String getElementName()
  {
    return GEOCOORDINATE_ELNAME;
  }
  
  @Override
  public void initialize()
  {    
    String[] sa = parseX(GEOCOORDINATE_ATTR_POINT_DFLT);
    point = pointDefault = new SFDouble[sa.length/3][3];
    int r = point.length;
    if(r > 0) {
      int c = point[0].length;
      int i = 0;
      for(int ir=0;ir<r;ir++)
        for(int ic=0;ic<c;ic++)
          point[ir][ic] = pointDefault[ir][ic] = buildSFDouble(sa[i++]); // default, no limits
    }
    geoSystem = GEOCOORDINATE_ATTR_GEOSYSTEM_DFLT;
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    
    org.jdom.Attribute attr;
    
    attr = root.getAttribute(GEOCOORDINATE_ATTR_POINT_NAME);
    if (attr != null) {
      String[] sa = parseX(attr.getValue());      
      point = makeSFDoubleArray(sa,3);
    }
    else
      point = new SFDouble[][]{}; // empty array
    
    attr = root.getAttribute(GEOCOORDINATE_ATTR_GEOSYSTEM_NAME);
    if (attr != null)
      geoSystem = splitStringIntoStringArray(attr.getValue());
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return GEOCOORDINATECustomizer.class;
  }
    
  @Override
  public String createAttributes()
  {
    StringBuffer sb = new StringBuffer();
    if (GEOCOORDINATE_ATTR_GEOSYSTEM_REQD || !geoSystemEqualsDefault(geoSystem))
    {
      sb.append(" ");
      sb.append(GEOCOORDINATE_ATTR_GEOSYSTEM_NAME);
      sb.append("='");
      for (String s : geoSystem) {
        sb.append("\"");
        sb.append(s);
        sb.append("\" ");
      }
      sb.setLength(sb.length() - 1); // last space
      sb.append("'");
    }
    if (GEOCOORDINATE_ATTR_POINT_REQD || !arraysIdenticalOrNull(point,pointDefault))
    {
      sb.append(" ");
      sb.append(GEOCOORDINATE_ATTR_POINT_NAME);
      sb.append("='");
      sb.append(formatDoubleArray(point));
      sb.append("'");
    }
    return sb.toString();
  }

  public String[][] getPoint()
  {
    if(point != null && point.length > 0)
      return makeStringArray(point);
    return new String[][]{{"0","0","0"}}; // something to start with
  }
  
  public void setPoint(String[][] saa)
  {
    if(saa != null && saa.length > 0)
      point = makeSFDoubleArray(saa);
    else
      point = new SFDouble[][]{};
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
    geoSystem = newGeoSystem;
  }
}
