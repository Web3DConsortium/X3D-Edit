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

import org.jdom.Element;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * INDEXEDLINESET.java
 * Created on Sep 6, 2007, 3:05 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class INDEXEDLINESET extends X3DGeometryNode
{
  /** attribute value */
  private boolean colorPerVertex;
  
  /** attribute value */
  private String colorIndex;
  
  /** attribute value */
  private String coordIndex;
  
  private boolean insertCommasColorIndex, insertLineBreaksColorIndex = false;
  
  private boolean insertCommasCoordIndex, insertLineBreaksCoordIndex = false;

  public INDEXEDLINESET()
  {
  }

  @Override
  public String getElementName()
  {
    return INDEXEDLINESET_ELNAME;
  }

  @Override
  public void initialize()
  {
    colorIndex = INDEXEDLINESET_ATTR_COLORINDEX_DFLT;
    coordIndex = INDEXEDLINESET_ATTR_COORDINDEX_DFLT;
    colorPerVertex = Boolean.parseBoolean(INDEXEDLINESET_ATTR_COLORPERVERTEX_DFLT);

    setContent("\n\t\t\t<!-- TODO authors can add Coordinate|CoordinateDouble and Color|ColorRGBA nodes here -->\n\t\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(INDEXEDLINESET_ATTR_COLORINDEX_NAME);
    if (attr != null)
      colorIndex = attr.getValue();
    attr = root.getAttribute(INDEXEDLINESET_ATTR_COORDINDEX_NAME);
    if (attr != null)
      coordIndex = attr.getValue();
    attr = root.getAttribute(INDEXEDLINESET_ATTR_COLORPERVERTEX_NAME);
    if (attr != null)
      colorPerVertex = Boolean.parseBoolean(attr.getValue());

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
    Element el = root.getChild("Coordinate");
    if(el != null)
    {
      COORDINATE coord = new COORDINATE();
      coord.initializeFromJdom(el, comp);
      if(!coord.isNil())
      {
          childCoordinateArraySize = coord.getPoint().length;
      }
      // else TODO try to locate USE node, read corresponding DEF node 
    }
    else // look for CoordinateDouble instead
    {
      el = root.getChild("CoordinateDouble");
      if(el != null)
      {
          COORDINATEDOUBLE coordinateDouble = new COORDINATEDOUBLE();
          coordinateDouble.initializeFromJdom(el, comp);
          if (!coordinateDouble.isNil())
          {
              childColorArraySize = coordinateDouble.getPoint().length;
          }
      }
      // else TODO try to locate USE node, read corresponding DEF node 
    }
    el = root.getChild("Color");
    if(el != null)
    {
      COLOR color = new COLOR();
      color.initializeFromJdom(el, comp);
      if (!color.isNil())
      {
          childColorArraySize = color.getColors().length;
      }
      // else TODO try to locate USE node, read corresponding DEF node 
    }
    else // look for ColorRGBA instead
    {
      el = root.getChild("ColorRGBA");
      if(el != null)
      {
          COLORRGBA colorRGBA = new COLORRGBA();
          colorRGBA.initializeFromJdom(el, comp);
          if (!colorRGBA.isNil())
          {
              childColorArraySize = colorRGBA.getColors().length;
          }
      }
      // else TODO try to locate USE node, read corresponding DEF node 
    }
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return INDEXEDLINESETCustomizer.class;
  }

  /**
   * create all non-default attributes
   * @return string of all attributes
   */
  @Override
  protected String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    if (INDEXEDLINESET_ATTR_COLORINDEX_REQD || !colorIndex.equals(INDEXEDLINESET_ATTR_COLORINDEX_DFLT)) {
      sb.append(" ");
      sb.append(INDEXEDLINESET_ATTR_COLORINDEX_NAME);
      sb.append("='");
      sb.append(colorIndex);
      sb.append("'");
    }

    if (INDEXEDLINESET_ATTR_COLORPERVERTEX_REQD || colorPerVertex != Boolean.parseBoolean(INDEXEDLINESET_ATTR_COLORPERVERTEX_DFLT)) {
      sb.append(" ");
      sb.append(INDEXEDLINESET_ATTR_COLORPERVERTEX_NAME);
      sb.append("='");
      sb.append(colorPerVertex);
      sb.append("'");
    }

    if (INDEXEDLINESET_ATTR_COORDINDEX_REQD || !coordIndex.equals(INDEXEDLINESET_ATTR_COORDINDEX_DFLT)) {
      sb.append(" ");
      sb.append(INDEXEDLINESET_ATTR_COORDINDEX_NAME);
      sb.append("='");
      sb.append(coordIndex);
      sb.append("'");
    }

    return sb.toString();
  }

  public String getColorIndex()
  {
      if ((colorIndex.length() > 0) && colorIndex.contains(","))
           insertCommasColorIndex = true;
      else insertCommasColorIndex = false;
      
      if ((coordIndex.length() > 0) && coordIndex.contains("\n"))
           insertLineBreaksColorIndex = true;
      else insertLineBreaksColorIndex = false;
      
      return colorIndex;
  }

  public String [] getColorIndexArray()
  {
      return getColorIndex().replace(",", " ").split("\\s+");
  }

  public void setColorIndex(String colorIndex)
  {
    this.colorIndex = validateIntegers(colorIndex);
    this.colorIndex = colorIndex; // restore commas, line breaks (if present)
  }

  public boolean isColorPerVertex()
  {
    return colorPerVertex;
  }

  public void setColorPerVertex(boolean colorPerVertex)
  {
    this.colorPerVertex = colorPerVertex;
  }

  public String getCoordIndex()
  {
      if ((coordIndex.length() > 0) && coordIndex.contains(","))
           insertCommasCoordIndex = true;
      else insertCommasCoordIndex = false;
      
      if ((coordIndex.length() > 0) && coordIndex.contains("\n"))
           insertLineBreaksCoordIndex = true;
      else insertLineBreaksCoordIndex = false;
      
      return coordIndex;
  }

  public String [] getCoordIndexArray()
  {
      return getCoordIndex().split("\\s+");
  }

  public void setCoordIndex(String coordIndex)
  {
    this.coordIndex = validateIntegers(coordIndex);
    this.coordIndex = coordIndex; // restore commas, line breaks (if present)
  }

    /**
     * @return the insertCommasColorIndex
     */
    public boolean isInsertCommasColorIndex() {
        return insertCommasColorIndex;
    }

    /**
     * @param insertCommas the insertCommas to set
     */
    public void setInsertCommasColorIndex(boolean insertCommas) {
        this.insertCommasColorIndex = insertCommas;
    }

    /**
     * @return the insertLineBreaksColorIndex
     */
    public boolean isInsertLineBreaksColorIndex() {
        return insertLineBreaksColorIndex;
    }

    /**
     * @param insertLineBreaks the insertLineBreaks to set
     */
    public void setInsertLineBreaksColorIndex(boolean insertLineBreaks) {
        this.insertLineBreaksColorIndex = insertLineBreaks;
    }

    /**
     * @return the insertCommasCoordIndex
     */
    public boolean isInsertCommasCoordIndex() {
        return insertCommasCoordIndex;
    }

    /**
     * @param insertCommas the insertCommas to set
     */
    public void setInsertCommasCoordIndex(boolean insertCommas) {
        this.insertCommasCoordIndex = insertCommas;
    }

    /**
     * @return the insertLineBreaksCoordIndex
     */
    public boolean isInsertLineBreaksCoordIndex() {
        return insertLineBreaksCoordIndex;
    }

    /**
     * @param insertLineBreaks the insertLineBreaks to set
     */
    public void setInsertLineBreaksCoordIndex(boolean insertLineBreaks) {
        this.insertLineBreaksCoordIndex = insertLineBreaks;
    }
}
