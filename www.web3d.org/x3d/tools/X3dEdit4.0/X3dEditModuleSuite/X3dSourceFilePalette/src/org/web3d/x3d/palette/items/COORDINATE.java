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
 * COORDINATE.java
 * Created on Sep 11, 2007, 3:05 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class COORDINATE extends X3DCoordinateNode
{
  private SFFloat[][] point, pointDefault;
  private boolean ddouble;
  private boolean insertCommas, insertLineBreaks = false;

  public COORDINATE()
  {
  }

  @Override
  public String getElementName()
  {
    if(ddouble)
      return COORDINATEDOUBLE_ELNAME;
    else
      return COORDINATE_ELNAME;
  }
  
  @Override
  public void initialize()
  {
    ddouble = false;
    
    String[] sa = parseX(COORDINATE_ATTR_POINT_DFLT);
    if ((sa.length  == 0) || (sa[0].length()  == 0)) return;
    point = pointDefault = new SFFloat[sa.length/3][3];
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
    
    if(root.getName().equals(COORDINATEDOUBLE_ELNAME))
      ddouble = true;
    
    org.jdom.Attribute attr;
    
    attr = root.getAttribute(COORDINATE_ATTR_POINT_NAME);
    if (attr != null)
    {
         String[] sa = parseX(attr.getValue());
         point = makeSFFloatArray(sa,3);
         if (attr.getValue().contains(","))  insertCommas     = true;
         if (attr.getValue().contains("\n") ||
             attr.getValue().contains("\r")) insertLineBreaks = true; // TODO not working, line breaks not being passed from JDOM
         if (insertCommas)                   insertLineBreaks = true; // workaround default, if commas were present then most likely lineBreaks also
    }
    else point = new SFFloat[][]{}; // empty array
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return COORDINATECustomizer.class;
  }
    
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    if (COORDINATE_ATTR_POINT_REQD || !arraysIdenticalOrNull(point,pointDefault)) {
      
      sb.append(" ");
      sb.append(COORDINATE_ATTR_POINT_NAME);
      sb.append("='");
      sb.append(formatFloatArray(point, insertCommas, insertLineBreaks));
      sb.append("'");
    }
    return sb.toString();
  }

  public String[][] getPoint()
  {
    if(!isNil())
      return makeStringArray(point);
    return new String[][]{{ }}; // something to start with
  }

  public boolean isNil()
  {
    return !(point != null && point.length>0);
  }

  public void setPoint(String[][] saa)
  {
    if(saa != null && saa.length > 0)
      point = makeSFFloatArray(saa);
    else
      point = new SFFloat[][]{}; 
  }
  
  public boolean isDouble()
  {
    return ddouble;
  }
  
  public void setDouble(boolean wh)
  {
    ddouble = wh;
  }

    /**
     * @return the insertCommas value
     */
    public boolean isInsertCommas() {
        return insertCommas;
    }

    /**
     * @param insertCommas the insertCommas value to set
     */
    public void setInsertCommas(boolean insertCommas) {
        this.insertCommas = insertCommas;
    }

    /**
     * @return the insertLineBreaks value
     */
    public boolean isInsertLineBreaks() {
        return insertLineBreaks;
    }

    /**
     * @param insertLineBreaks the insertLineBreak values to set
     */
    public void setInsertLineBreaks(boolean insertLineBreaks) {
        this.insertLineBreaks = insertLineBreaks;
    }
}
