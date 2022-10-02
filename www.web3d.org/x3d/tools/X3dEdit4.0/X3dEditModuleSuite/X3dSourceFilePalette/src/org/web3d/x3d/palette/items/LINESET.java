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

import org.jdom.Element;
import javax.swing.text.JTextComponent;
import org.web3d.x3d.types.X3DGeometryNode;

import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * LINESET.java
 * Created on Sep 6, 2007, 3:05 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class LINESET extends X3DGeometryNode
{
  private String vertexCount;

  public LINESET()
  {
  }

  @Override
  public String getElementName()
  {
    return LINESET_ELNAME;
  }

  @Override
  public void initialize()
  {
    vertexCount = LINESET_ATTR_VERTEXCOUNT_DFLT;

    setContent("\n\t\t\t<!--TODO add Coordinate|CoordinateDouble and Color|ColorRGBA nodes here-->\n\t\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(LINESET_ATTR_VERTEXCOUNT_NAME);
    if (attr != null)
      vertexCount = attr.getValue();

    examineChildArrays(root,comp);
  }

  private int childColorArraySize      = 0;
  private int childCoordinateArraySize = 0;

  public int getChildColorArraySize()
  {
    return childColorArraySize;
  }

  public int getChildCoordArraySize()
  {
    return childCoordinateArraySize;
  }

  private void examineChildArrays(Element root, JTextComponent comp)
  {
      // TODO handle USE nodes

    Element el = root.getChild("Coordinate");
    if(el != null) {
      COORDINATE coord = new COORDINATE();
      coord.initializeFromJdom(el, comp);
      if(!coord.isNil())
        childCoordinateArraySize = coord.getPoint().length;
    }
    el = root.getChild("Color");
    if(el != null) {
      COLOR color = new COLOR();
      color.initializeFromJdom(el, comp);
      if(!color.isNil())
        childColorArraySize = color.getColors().length;
    }
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return LINESETCustomizer.class;
  }

  @Override
  public String createAttributes()
  {
    StringBuffer sb = new StringBuffer();

    if (LINESET_ATTR_VERTEXCOUNT_REQD || !vertexCount.equals(LINESET_ATTR_VERTEXCOUNT_DFLT)) {
      sb.append(" ");
      sb.append(LINESET_ATTR_VERTEXCOUNT_NAME);
      sb.append("='");
      sb.append(vertexCount);
      sb.append("'");
    }

    return sb.toString();
  }


  public String getVertexCount()
  {
    return vertexCount;
  }

  public void setVertexCount(String newVertexCount)
  {
      if (newVertexCount.isBlank())
           this.vertexCount = "";
      // TODO should verify each integer is 2 or greater
      else this.vertexCount = validatePositiveIntegersOrMinusOne(newVertexCount);
  }

}
