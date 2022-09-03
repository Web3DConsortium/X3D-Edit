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
import org.jdom.Element;
import org.web3d.x3d.types.X3DComposedGeometryNode;

import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * INDEXEDFACESET.java
 * Created on Sep 6, 2007, 3:05 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class INDEXEDFACESET extends X3DComposedGeometryNode
{
  private boolean ccw,convex,solid,colorPerVertex,normalPerVertex;

  /** attribute value */
  private SFFloat creaseAngle, creaseAngleDefault;
  
  /** attribute value */
  private String colorIndex;
  
  /** attribute value */
  private String coordIndex;
  
  /** attribute value */
  private String normalIndex;
  
  /** attribute value */
  private String texCoordIndex;
  
  private boolean insertCommasColorIndex, insertLineBreaksColorIndex = false;
  
  private boolean insertCommasCoordIndex, insertLineBreaksCoordIndex = false;
  
  private boolean insertCommasNormalIndex, insertLineBreaksNormalIndex = false;
  
  private boolean insertCommasTexCoordIndex, insertLineBreaksTexCoordIndex = false;

  public INDEXEDFACESET()
  {
  }

  @Override
  public String getElementName()
  {
    return INDEXEDFACESET_ELNAME;
  }

  @Override
  public void initialize()
  {
    ccw = Boolean.parseBoolean(INDEXEDFACESET_ATTR_CCW_DFLT);
    convex = Boolean.parseBoolean(INDEXEDFACESET_ATTR_CONVEX_DFLT);
    solid = Boolean.parseBoolean(INDEXEDFACESET_ATTR_SOLID_DFLT);

    creaseAngle = creaseAngleDefault = new SFFloat(INDEXEDFACESET_ATTR_CREASEANGLE_DFLT,0.0f,null);
    colorPerVertex  = Boolean.parseBoolean(INDEXEDFACESET_ATTR_COLORPERVERTEX_DFLT);
    normalPerVertex = Boolean.parseBoolean(INDEXEDFACESET_ATTR_NORMALPERVERTEX_DFLT);

    colorIndex    = INDEXEDFACESET_ATTR_COLORINDEX_DFLT;
    coordIndex    = INDEXEDFACESET_ATTR_COORDINDEX_DFLT;
    normalIndex   = INDEXEDFACESET_ATTR_NORMALINDEX_DFLT;
    texCoordIndex = INDEXEDFACESET_ATTR_TEXCOORDINDEX_DFLT;

    setContent("\n\t\t\t<!-- TODO authors can add Coordinate|CoordinateDouble, Color|ColorRGBA, Normal and TextureCoordinate|TextureCoordinateGenerator|MultiTextureCoordinate nodes here -->\n\t\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(INDEXEDFACESET_ATTR_CCW_NAME);
    if (attr != null)
      ccw = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(INDEXEDFACESET_ATTR_CONVEX_NAME);
    if (attr != null)
      convex = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(INDEXEDFACESET_ATTR_SOLID_NAME);
    if (attr != null)
      solid = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(INDEXEDFACESET_ATTR_CREASEANGLE_NAME);
    if (attr != null)
      creaseAngle = new SFFloat(attr.getValue(), 0.0f, null);
    attr = root.getAttribute(INDEXEDFACESET_ATTR_COLORPERVERTEX_NAME);
    if (attr != null)
      colorPerVertex = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(INDEXEDFACESET_ATTR_NORMALPERVERTEX_NAME);
    if (attr != null)
      normalPerVertex = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(INDEXEDFACESET_ATTR_COLORINDEX_NAME);
    if (attr != null)
      colorIndex = attr.getValue();
    attr = root.getAttribute(INDEXEDFACESET_ATTR_COORDINDEX_NAME);
    if (attr != null)
      coordIndex = attr.getValue();
    attr = root.getAttribute(INDEXEDFACESET_ATTR_NORMALINDEX_NAME);
    if (attr != null)
      normalIndex = attr.getValue();
    attr = root.getAttribute(INDEXEDFACESET_ATTR_TEXCOORDINDEX_NAME);
    if (attr != null)
      texCoordIndex = attr.getValue();

    examineChildArrays(root,comp);
  }

  private int childColorArraySize      = 0;
  private int childCoordinateArraySize = 0;
  private int childNormalArraySize     = 0;
  private int childTexCoordArraySize   = 0;

  public int getChildColorArraySize()
  {
    return childColorArraySize;
  }
  
  public int getChildCoordArraySize()
  {
    return childCoordinateArraySize;
  }

  public int getChildNormalArraySize()
  {
    return childNormalArraySize;
  }
  
  public int getChildTexCoordArraySize()
  {
    return childTexCoordArraySize;
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
          // else TODO try to locate USE node, read corresponding DEF node 
      }
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
          // else TODO try to locate USE node, read corresponding DEF node 
      }
    }
      
    el = root.getChild("Normal");
    if(el != null) 
    {
      NORMAL normal = new NORMAL();
      normal.initializeFromJdom(el, comp);
      if(!normal.isNil())
      {
          childNormalArraySize = normal.getVector().length;
      }
      // else TODO try to locate USE node, read corresponding DEF node 
    }
    el = root.getChild("TextureCoordinate");
    if(el != null) 
    {
      TEXTURECOORDINATE texCoord = new TEXTURECOORDINATE();
      texCoord.initializeFromJdom(el, comp);
      if(!texCoord.isNil())
      {
          childTexCoordArraySize = texCoord.getPoint().length;
      }
      // else TODO try to locate USE node, read corresponding DEF node 
    }
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return INDEXEDFACESETCustomizer.class;
  }

  /**
   * create all non-default attributes
   * @return string of all attributes
   */
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    if (INDEXEDFACESET_ATTR_CCW_REQD || ccw != Boolean.parseBoolean(INDEXEDFACESET_ATTR_CCW_DFLT)) {
      sb.append(" ");
      sb.append(INDEXEDFACESET_ATTR_CCW_NAME);
      sb.append("='");
      sb.append(ccw);
      sb.append("'");
    }
    if (INDEXEDFACESET_ATTR_COLORINDEX_REQD || !colorIndex.equals(INDEXEDFACESET_ATTR_COLORINDEX_DFLT)) {
      sb.append(" ");
      sb.append(INDEXEDFACESET_ATTR_COLORINDEX_NAME);
      sb.append("='");
      sb.append(colorIndex);
      sb.append("'");
    }
    if (INDEXEDFACESET_ATTR_COLORPERVERTEX_REQD || colorPerVertex != Boolean.parseBoolean(INDEXEDFACESET_ATTR_COLORPERVERTEX_DFLT)) {
      sb.append(" ");
      sb.append(INDEXEDFACESET_ATTR_COLORPERVERTEX_NAME);
      sb.append("='");
      sb.append(colorPerVertex);
      sb.append("'");
    }
    if (INDEXEDFACESET_ATTR_CONVEX_REQD || convex != Boolean.parseBoolean(INDEXEDFACESET_ATTR_CONVEX_DFLT)) {
      sb.append(" ");
      sb.append(INDEXEDFACESET_ATTR_CONVEX_NAME);
      sb.append("='");
      sb.append(convex);
      sb.append("'");
    }
    if (INDEXEDFACESET_ATTR_COORDINDEX_REQD || !coordIndex.equals(INDEXEDFACESET_ATTR_COORDINDEX_DFLT)) {
      sb.append(" ");
      sb.append(INDEXEDFACESET_ATTR_COORDINDEX_NAME);
      sb.append("='");
      sb.append(coordIndex);
      sb.append("'");
    }
    if (INDEXEDFACESET_ATTR_CREASEANGLE_REQD || !creaseAngle.equals(creaseAngleDefault)) {
      sb.append(" ");
      sb.append(INDEXEDFACESET_ATTR_CREASEANGLE_NAME);
      sb.append("='");
      sb.append(creaseAngle);
      sb.append("'");
    }
    if (INDEXEDFACESET_ATTR_NORMALINDEX_REQD || !normalIndex.equals(INDEXEDFACESET_ATTR_NORMALINDEX_DFLT)) {
      sb.append(" ");
      sb.append(INDEXEDFACESET_ATTR_NORMALINDEX_NAME);
      sb.append("='");
      sb.append(normalIndex);
      sb.append("'");
    }
    if (INDEXEDFACESET_ATTR_NORMALPERVERTEX_REQD || normalPerVertex != Boolean.parseBoolean(INDEXEDFACESET_ATTR_NORMALPERVERTEX_DFLT)) {
      sb.append(" ");
      sb.append(INDEXEDFACESET_ATTR_NORMALPERVERTEX_NAME);
      sb.append("='");
      sb.append(normalPerVertex);
      sb.append("'");
    }
    if (INDEXEDFACESET_ATTR_SOLID_REQD || solid != Boolean.parseBoolean(INDEXEDFACESET_ATTR_SOLID_DFLT)) {
      sb.append(" ");
      sb.append(INDEXEDFACESET_ATTR_SOLID_NAME);
      sb.append("='");
      sb.append(solid);
      sb.append("'");
    }
    if (INDEXEDFACESET_ATTR_TEXCOORDINDEX_REQD || !texCoordIndex.equals(INDEXEDFACESET_ATTR_TEXCOORDINDEX_DFLT)) {
      sb.append(" ");
      sb.append(INDEXEDFACESET_ATTR_TEXCOORDINDEX_NAME);
      sb.append("='");
      sb.append(texCoordIndex);
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
      return getColorIndex().replace(",", "").split("\\s+");
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

  public void setCoordIndex(String coordIndex)
  {
    this.coordIndex = validateIntegers(coordIndex);
    this.coordIndex = coordIndex; // restore commas, line breaks (if present)
  }
  public boolean isCcw()
  {
    return ccw;
  }

  public void setCcw(boolean ccw)
  {
    this.ccw = ccw;
  }

  public boolean isConvex()
  {
    return convex;
  }

  public void setConvex(boolean convex)
  {
    this.convex = convex;
  }

  public String getCreaseAngle()
  {
    return radiansFormat.format(creaseAngle.getValue());
  }

  public void setCreaseAngle(String creaseAngle)
  {
    this.creaseAngle = new SFFloat(creaseAngle,0.0f,null);
  }

  public String getNormalIndex()
  {
      if ((normalIndex.length() > 0) && normalIndex.contains(","))
           insertCommasNormalIndex = true;
      else insertCommasNormalIndex = false;
      
      if ((normalIndex.length() > 0) && normalIndex.contains("\n"))
           insertLineBreaksNormalIndex = true;
      else insertLineBreaksNormalIndex = false;
      
    return normalIndex;
  }

  public String [] getNormalIndexArray()
  {
      return getNormalIndex().replace(",", " ").split("\\s+");
  }

  public void setNormalIndex(String normalIndex)
  {
    this.normalIndex = validateIntegers(normalIndex);
    this.normalIndex = normalIndex; // restore commas, line breaks (if present)
  }

  public boolean isNormalPerVertex()
  {
    return normalPerVertex;
  }

  public void setNormalPerVertex(boolean normalPerVertex)
  {
    this.normalPerVertex = normalPerVertex;
  }

  public boolean isSolid()
  {
    return solid;
  }

  public void setSolid(boolean solid)
  {
    this.solid = solid;
  }

  public String getTexCoordIndex()
  {
      if ((texCoordIndex.length() > 0) && texCoordIndex.contains(","))
           insertCommasTexCoordIndex = true;
      else insertCommasTexCoordIndex = false;
      
      if ((texCoordIndex.length() > 0) && texCoordIndex.contains("\n"))
           insertLineBreaksTexCoordIndex = true;
      else insertLineBreaksTexCoordIndex = false;
      
    return texCoordIndex;
  }

  public String [] getTexCoordIndexArray()
  {
      return getTexCoordIndex().replace(",", " ").split("\\s+");
  }

  public void setTexCoordIndex(String texCoordIndex)
  {
    this.texCoordIndex = validateIntegers(texCoordIndex);
    this.texCoordIndex = texCoordIndex; // restore commas, line breaks (if present)
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

    /**
     * @return the insertCommasNormalIndex
     */
    public boolean isInsertCommasNormalIndex() {
        return insertCommasNormalIndex;
    }

    /**
     * @param insertCommas the insertCommas to set
     */
    public void setInsertCommasNormalIndex(boolean insertCommas) {
        this.insertCommasNormalIndex = insertCommas;
    }

    /**
     * @return the insertLineBreaksNormalIndex
     */
    public boolean isInsertLineBreaksNormalIndex() {
        return insertLineBreaksNormalIndex;
    }

    /**
     * @param insertLineBreaks the insertLineBreaks to set
     */
    public void setInsertLineBreaksNormalIndex(boolean insertLineBreaks) {
        this.insertLineBreaksNormalIndex = insertLineBreaks;
    }


    /**
     * @return the insertCommasTexCoordIndex
     */
    public boolean isInsertCommasTexCoordIndex() {
        return insertCommasTexCoordIndex;
    }

    /**
     * @param insertCommas the insertCommas to set
     */
    public void setInsertCommasTexCoordIndex(boolean insertCommas) {
        this.insertCommasTexCoordIndex = insertCommas;
    }

    /**
     * @return the insertLineBreaksTexCoordIndex
     */
    public boolean isInsertLineBreaksTexCoordIndex() {
        return insertLineBreaksTexCoordIndex;
    }

    /**
     * @param insertLineBreaks the insertLineBreaks to set
     */
    public void setInsertLineBreaksTexCoordIndex(boolean insertLineBreaks) {
        this.insertLineBreaksTexCoordIndex = insertLineBreaks;
    }

}
