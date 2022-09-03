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
import org.web3d.x3d.types.X3DGeometryNode;

import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * POLYLINE2D.java
 * Created on July 11, 2007, 11:58 AM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class POLYLINE2D extends X3DGeometryNode
{
  private SFFloat[][] lineSegments;
  private SFFloat[][] lineSegmentsDefault;
  
  public POLYLINE2D()
  {
  }
  
  @Override
  public String getElementName()
  {
    return POLYLINE2D_ELNAME;
  }
  
 @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return POLYLINE2DCustomizer.class;
  }
  
  @Override
  public void initialize()
  {    
    String[] sa = parseX(POLYLINE2D_ATTR_LINESEGMENTS_DFLT);   
    lineSegments = lineSegmentsDefault = new SFFloat[sa.length/2][2];
    int r = lineSegments.length;
    if(r <= 0)
      return;
    int c = lineSegments[0].length;
    int i = 0;
    for(int ir=0;ir<r;ir++)
      for(int ic=0;ic<c;ic++)
        lineSegments[ir][ic] = lineSegmentsDefault[ir][ic] = buildSFFloat(sa[i++]); // default, no limits

  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(POLYLINE2D_ATTR_LINESEGMENTS_NAME);
    if (attr != null) {
      String[] sa = parseX(attr.getValue());      
      lineSegments = makeSFFloatArray(sa,2);
    }
    else
      lineSegments = new SFFloat[][]{}; // empty array
  }
  
  @Override
  public String createAttributes()
  {
   StringBuilder sb = new StringBuilder();
    if (POLYLINE2D_ATTR_LINESEGMENTS_REQD || !arraysIdenticalOrNull(lineSegments,lineSegmentsDefault)) {
      sb.append(" ");
      sb.append(POLYLINE2D_ATTR_LINESEGMENTS_NAME);
      sb.append("='");    
      sb.append(formatFloatArray(lineSegments));
      sb.append("'"); 
    }
    return sb.toString();
  }

  public String[][] getLineSegments()
  {
    if(lineSegments == null || lineSegments.length == 0)
      return new String[][]{{"0","1"}}; // it's most likely a new node drug from palette, give it something to start with
    
    return makeStringArray(lineSegments);
  }

  public void setLineSegments(String[][] segs)
  {
    if(segs != null && segs.length >0)
      lineSegments = makeSFFloatArray(segs);
    else
      lineSegments = new SFFloat[][]{}; // empty array
  }  
}
