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

import java.util.Arrays;
import javax.swing.text.JTextComponent;
import org.web3d.x3d.types.X3DTextureCoordinateNode;

import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * TEXTURECOORDINATE.java
 * Created on Sep 11, 2007, 9:05 AM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class TEXTURECOORDINATE extends X3DTextureCoordinateNode
{
  private SFFloat[][] point, pointDefault;

  public TEXTURECOORDINATE()
  {
  }

  @Override
  public String getElementName()
  {
    return TEXTURECOORDINATE_ELNAME;
  }

  @Override
  public void initialize()
  {
    String[] sa = parseX(TEXTURECOORDINATE_ATTR_POINT_DFLT);
        
    point = pointDefault = new SFFloat[sa.length/2][2];
    int r = point.length;
    if(r <= 0)
      return;
    int c = point[0].length;
    int i = 0;
    for(int ir=0;ir<r;ir++)
      for(int ic=0;ic<c;ic++)
        point[ir][ic] = pointDefault[ir][ic] = buildSFFloat(sa[i++]); // default, no limits

  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;
    
    attr = root.getAttribute(TEXTURECOORDINATE_ATTR_POINT_NAME);
    if (attr != null) {
      String[] sa = parseX(attr.getValue());      
      point = makeSFFloatArray(sa,2);
    }
    else
      point = new SFFloat[][]{}; // empty array
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return TEXTURECOORDINATECustomizer.class;
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (TEXTURECOORDINATE_ATTR_POINT_REQD || !Arrays.equals(point, pointDefault)) {
      sb.append(" ");
      sb.append(TEXTURECOORDINATE_ATTR_POINT_NAME);
      sb.append("='");
      sb.append(formatFloatArray(point));
      sb.append("'");
    }
    return sb.toString();
  }

  public String[][] getPoint()
  {
    if(point != null && point.length > 0)
      return makeStringArray(point);
    return new String[][]{{ }}; // something to start with
  }

  public void setPoint(String[][] saa)
  {
    if(saa != null && saa.length > 0)
      point = makeSFFloatArray(saa);
    else
      point = new SFFloat[][]{}; 
  }

  public boolean isNil()
  {
    return !(point != null && point.length>0);
  }

}
