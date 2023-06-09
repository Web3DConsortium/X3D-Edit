/*
Copyright (c) 1995-2021 held by the author(s).  All rights reserved.
 
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
      (https://www.nps.edu and https://MovesInstitute.nps.edu)
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
 * TRIANGLESET2D.java
 * Created on July 11, 2007, 1:41 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class TRIANGLESET2D extends X3DGeometryNode
{
  private SFFloat[][] vertices;
  private SFFloat[][] verticesDefault;
  private boolean solid;

  public TRIANGLESET2D()
  {
  }
    
  @Override
  public String getElementName()
  {
    return TRIANGLESET2D_ELNAME;
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return TRIANGLESET2DCustomizer.class;
  }
  
  @Override
  public void initialize()
  {
    solid = Boolean.parseBoolean(TRIANGLESET2D_ATTR_SOLID_DFLT);
    
     String[] sa = parseX(TRIANGLESET2D_ATTR_VERTICES_DFLT);   
    vertices = verticesDefault = new SFFloat[sa.length/2][2];
    int r = vertices.length;
    if(r <= 0)
      return;
    int c = vertices[0].length;
    int i = 0;
    for(int ir=0;ir<r;ir++)
      for(int ic=0;ic<c;ic++)
        vertices[ir][ic] = verticesDefault[ir][ic] = buildSFFloat(sa[i++]); // default, no limits
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(TRIANGLESET2D_ATTR_SOLID_NAME);
    if (attr != null)
      solid = Boolean.parseBoolean(attr.getValue());
    
    attr = root.getAttribute(TRIANGLESET2D_ATTR_VERTICES_NAME);
    if (attr != null) {
      String[] sa = parseX(attr.getValue());      
      vertices = makeSFFloatArray(sa,6);
    }
    else
      vertices = new SFFloat[][]{}; // empty array
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    if (TRIANGLESET2D_ATTR_VERTICES_REQD || !arraysIdenticalOrNull(vertices,verticesDefault)) {
      sb.append(" ");
      sb.append(TRIANGLESET2D_ATTR_VERTICES_NAME);
      sb.append("='");
      sb.append(formatFloatArray(vertices));
      sb.append("'");
    }
    if (TRIANGLESET2D_ATTR_SOLID_REQD || solid != Boolean.parseBoolean(TRIANGLESET2D_ATTR_SOLID_DFLT)) {
      sb.append(" ");
      sb.append(TRIANGLESET2D_ATTR_SOLID_NAME);
      sb.append("='");
      sb.append(solid);
      sb.append("'");
    }
    return sb.toString();
  }
  
  public String[][] getVertices()
  {
    if(vertices != null && vertices.length > 0)
     return makeStringArray(vertices);
    
    return new String[][]{ }; // start with empty array
  }
  
  public void setVertices(String[][] saa)
  {
    if(saa != null && saa.length >0)
      vertices = makeSFFloatArray(saa);
    else
      vertices = new SFFloat[][]{}; // empty array
  }
  
  public boolean isSolid()
  {
    return solid;
  }
  
  public void setSolid(boolean solid)
  {
    this.solid = solid;
  } 
}
