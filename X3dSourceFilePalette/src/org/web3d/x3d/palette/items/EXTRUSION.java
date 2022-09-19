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
import org.web3d.x3d.types.X3DPrimitiveTypes.SFFloat;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

//import org.netbeans.spi.palette.PaletteItemRegistration;

//@PaletteItemRegistration
//(
//    paletteid = "X3DPalette",
//    category = "6. Geometry: Points, Lines, and Polygons (Annotations)",
//    itemid = "EXTRUSION",
//    icon32 = "org/web3d/x3d/palette/items/resources/EXTRUSION32.png",
//    icon16 = "org/web3d/x3d/palette/items/resources/EXTRUSION16.png",
//    body = "<Extrusion beginCap='true' ccw='true' creaseAngle='0' crossSection='1 1 1 -1 -1 -1 -1 1 1 1' endCap='true' solid='true' spine='0 0 0 0 1 0'/>",
//    name = "Extrusion",
//    tooltip = "Extrusion defines geometric shaps based on 2D cross-section extruded along a 3D spine axis"
//)
// https://bits.netbeans.org/14/javadoc/org-netbeans-spi-palette/architecture-summary.html
// https://bits.netbeans.org/14/javadoc/org-netbeans-spi-palette/org/netbeans/spi/palette/PaletteItemRegistration.html

/**
 * EXTRUSION.java
 * Created on Sep 12, 2007, 3:05 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class EXTRUSION extends X3DGeometryNode
{
  private boolean ccw,convex,solid,beginCap,endCap;  
  private SFFloat creaseAngle,creaseAngleDefault; 

  private SFFloat[][] crossSection, crossSectionDefault;
  private SFFloat[][] spine,  spineDefault;
  private SFFloat[][] scale,  scaleDefault;
  private SFFloat[][] orientation,  orientationDefault;
  private boolean insertCommas, insertLineBreaks = false;

  public EXTRUSION()
  {    
  }

  @Override
  public String getElementName()
  {
    return EXTRUSION_ELNAME;
  }
  
  @Override
  public void initialize()
  {
    ccw      = Boolean.parseBoolean(EXTRUSION_ATTR_CCW_DFLT);
    solid    = Boolean.parseBoolean(EXTRUSION_ATTR_SOLID_DFLT);
    convex   = Boolean.parseBoolean(EXTRUSION_ATTR_CONVEX_DFLT);
    beginCap = Boolean.parseBoolean(EXTRUSION_ATTR_BEGINCAP_DFLT);
    endCap   = Boolean.parseBoolean(EXTRUSION_ATTR_ENDCAP_DFLT);
    
    creaseAngle = creaseAngleDefault = new SFFloat(EXTRUSION_ATTR_CREASEANGLE_DFLT,0.0f,null);

    String[] sa = parseX(EXTRUSION_ATTR_CROSSSECTION_DFLT);
    crossSection = crossSectionDefault = new SFFloat[sa.length/2][2];
    int r = crossSection.length;
    if(r <= 0)
      return;
    int c = crossSection[0].length;
    int i = 0;
    for(int ir=0;ir<r;ir++)
    {
        for (int ic = 0; ic < c; ic++)
        {
            crossSection[ir][ic] = crossSectionDefault[ir][ic] = buildSFFloat(sa[i++]); // default, no limits
        }
    } // default, no limits

    sa = parseX(EXTRUSION_ATTR_SPINE_DFLT);
    spine = spineDefault = new SFFloat[sa.length/3][3];
    r = spine.length;
    if(r <= 0)
      return;
    c = spine[0].length;
    i = 0;
    for(int ir=0;ir<r;ir++)
    {
        for (int ic = 0; ic < c; ic++)
        {
            spine[ir][ic] = spineDefault[ir][ic] = buildSFFloat(sa[i++]); // default, no limits
        }
    } // default, no limits

    sa = parseX(EXTRUSION_ATTR_SCALE_DFLT);
    scale = scaleDefault = new SFFloat[sa.length/2][2];
    r = scale.length;
    if(r <= 0)
      return;
    c = scale[0].length;
    i = 0;
    for(int ir=0;ir<r;ir++)
    {
        for (int ic = 0; ic < c; ic++)
        {
            scale[ir][ic] = scaleDefault[ir][ic] = buildSFFloat(sa[i++]); // default, no limits
        }
    } // default, no limits

    sa = parseX(EXTRUSION_ATTR_ORIENTATION_DFLT);
    orientation = orientationDefault = new SFFloat[sa.length/4][4];
    r = orientation.length;
    if(r <= 0)
      return;
    c = orientation[0].length;
    i = 0;
    for(int ir=0;ir<r;ir++)
    {
        for (int ic = 0; ic < c; ic++)
        {
            orientation[ir][ic] = orientationDefault[ir][ic] = buildSFFloat(sa[i++]); // default, no limits
        }
    } // default, no limits
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(EXTRUSION_ATTR_CCW_NAME);
    if (attr != null)
      ccw = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(EXTRUSION_ATTR_SOLID_NAME);
    if (attr != null)
      solid = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(EXTRUSION_ATTR_CONVEX_NAME);
    if (attr != null)
      convex = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(EXTRUSION_ATTR_BEGINCAP_NAME);
    if (attr != null)
      beginCap = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(EXTRUSION_ATTR_ENDCAP_NAME);
    if (attr != null)
      endCap = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(EXTRUSION_ATTR_CREASEANGLE_NAME);
    if (attr != null)
      creaseAngle = new SFFloat(attr.getValue(), 0.0f, null);

    attr = root.getAttribute(EXTRUSION_ATTR_CROSSSECTION_NAME);
    if (attr != null)
    {
      String[] sa = parseX(attr.getValue());
      crossSection = makeSFFloatArray(sa,2);
         if (attr.getValue().contains(","))  insertCommas     = true;
         if (attr.getValue().contains("\n") ||
             attr.getValue().contains("\r")) insertLineBreaks = true; // TODO not working, line breaks not being passed from JDOM
         if (insertCommas)                   insertLineBreaks = true; // workaround default, if commas were present then most likely lineBreaks also
    }
    else
      crossSection = new SFFloat[][]{}; // empty array

    attr = root.getAttribute(EXTRUSION_ATTR_SPINE_NAME);
    if (attr != null)
    {
      String[] sa = parseX(attr.getValue());
      spine = makeSFFloatArray(sa,3);
         if (attr.getValue().contains(","))  insertCommas     = true;
         if (attr.getValue().contains("\n") ||
             attr.getValue().contains("\r")) insertLineBreaks = true; // TODO not working, line breaks not being passed from JDOM
         if (insertCommas)                   insertLineBreaks = true; // workaround default, if commas were present then most likely lineBreaks also
    }
    else
      spine = new SFFloat[][]{}; // empty array

    attr = root.getAttribute(EXTRUSION_ATTR_SCALE_NAME);
    if (attr != null)
    {
      String[] sa = parseX(attr.getValue());
      scale = makeSFFloatArray(sa,2);
         if (attr.getValue().contains(","))  insertCommas     = true;
         if (attr.getValue().contains("\n") ||
             attr.getValue().contains("\r")) insertLineBreaks = true; // TODO not working, line breaks not being passed from JDOM
         if (insertCommas)                   insertLineBreaks = true; // workaround default, if commas were present then most likely lineBreaks also
    }
    else
      scale = new SFFloat[][]{}; // empty array

    attr = root.getAttribute(EXTRUSION_ATTR_ORIENTATION_NAME);
    if (attr != null)
    {
      String[] sa = parseX(attr.getValue());
      orientation = makeSFFloatArray(sa,4);
         if (attr.getValue().contains(","))  insertCommas     = true;
         if (attr.getValue().contains("\n") ||
             attr.getValue().contains("\r")) insertLineBreaks = true; // TODO not working, line breaks not being passed from JDOM
         if (insertCommas)                   insertLineBreaks = true; // workaround default, if commas were present then most likely lineBreaks also
    }
    else
      orientation = new SFFloat[][]{}; // empty array
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return EXTRUSIONTabbedPaneCustomizer.class;
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (EXTRUSION_ATTR_BEGINCAP_REQD || beginCap != Boolean.parseBoolean(EXTRUSION_ATTR_BEGINCAP_DFLT)) {
      sb.append(" ");
      sb.append(EXTRUSION_ATTR_BEGINCAP_NAME);
      sb.append("='");
      sb.append(beginCap);
      sb.append("'");
    }
    if (EXTRUSION_ATTR_CCW_REQD || ccw != Boolean.parseBoolean(EXTRUSION_ATTR_CCW_DFLT)) {
      sb.append(" ");
      sb.append(EXTRUSION_ATTR_CCW_NAME);
      sb.append("='");
      sb.append(ccw);
      sb.append("'");
    }
    if (EXTRUSION_ATTR_CONVEX_REQD || convex != Boolean.parseBoolean(EXTRUSION_ATTR_CONVEX_DFLT)) {
      sb.append(" ");
      sb.append(EXTRUSION_ATTR_CONVEX_NAME);
      sb.append("='");
      sb.append(convex);
      sb.append("'");
    }
    if (EXTRUSION_ATTR_CREASEANGLE_REQD || !creaseAngle.equals(creaseAngleDefault)) {
      sb.append(" ");
      sb.append(EXTRUSION_ATTR_CREASEANGLE_NAME);
      sb.append("='");
      sb.append(creaseAngle);
      sb.append("'");
    }
    if (EXTRUSION_ATTR_CROSSSECTION_REQD || !arraysIdenticalOrNull(crossSection,crossSectionDefault)) {
      sb.append(" ");
      sb.append(EXTRUSION_ATTR_CROSSSECTION_NAME);
      sb.append("='");
      sb.append(formatFloatArray(crossSection, insertCommas, insertLineBreaks));
      sb.append("'");
    }
    if (EXTRUSION_ATTR_ENDCAP_REQD || beginCap != Boolean.parseBoolean(EXTRUSION_ATTR_ENDCAP_DFLT)) {
      sb.append(" ");
      sb.append(EXTRUSION_ATTR_ENDCAP_NAME);
      sb.append("='");
      sb.append(endCap);
      sb.append("'");
    }
    if (EXTRUSION_ATTR_ORIENTATION_REQD || !arraysIdenticalOrNull(orientation,orientationDefault)) {
      sb.append(" ");
      sb.append(EXTRUSION_ATTR_ORIENTATION_NAME);
      sb.append("='");
      sb.append(formatFloatArray(orientation, insertCommas, insertLineBreaks));
      sb.append("'");
    }
    if (EXTRUSION_ATTR_SCALE_REQD || !arraysIdenticalOrNull(scale,scaleDefault)) {
      sb.append(" ");
      sb.append(EXTRUSION_ATTR_SCALE_NAME);
      sb.append("='");
      sb.append(formatFloatArray(scale, insertCommas, insertLineBreaks));
      sb.append("'");
    }
    if (EXTRUSION_ATTR_SOLID_REQD || solid != Boolean.parseBoolean(EXTRUSION_ATTR_SOLID_DFLT)) {
      sb.append(" ");
      sb.append(EXTRUSION_ATTR_SOLID_NAME);
      sb.append("='");
      sb.append(solid);
      sb.append("'");
    }
    if (EXTRUSION_ATTR_SPINE_REQD || !arraysIdenticalOrNull(spine,spineDefault)) {
      sb.append(" ");
      sb.append(EXTRUSION_ATTR_SPINE_NAME);
      sb.append("='");
      sb.append(formatFloatArray(spine, insertCommas, insertLineBreaks));
      sb.append("'");
    }
    return sb.toString();
  }

  public String getCrossSectionString()
  {
      StringBuilder sb = new StringBuilder();
      String[][] stringArray = getCrossSection();
      for      (int i=0; (i < stringArray.length); i++)
      {
          for  (int j=0; j < stringArray[i].length; j++)
          {
              sb.append(stringArray[i][j]);
              sb.append(" ");
          }
      }
      return sb.toString().trim();
  }

  public String[][] getCrossSection()
  {
    if(crossSection != null && crossSection.length > 0)
      return makeStringArray(crossSection);
    return new String[][]{{"1","1"},{"1","-1"},{"-1","-1"},{"-1","1"},{"1","1"}};  // Give it something to start with
  } // default: 1 1, 1 -1, -1 -1, -1 1, 1 1

  public void setCrossSection(String[][] saa)
  {
    if(saa != null && saa.length > 0)
      crossSection = makeSFFloatArray(saa);
    else
      crossSection = new SFFloat[][]{};
  }

  public String[][] getSpine()
  {
    if(spine != null && spine.length > 0)
      return makeStringArray(spine);
    return new String[][]{{"0","0","0"},{"0","1","0"}}; // something to start with
  }

  public String[][] getDefaultSpine()
  {
    return new String[][]{{"0","0","0"},{"0","1","0"}}; // something to start with
  }

  public String getSpineString()
  {
      StringBuilder sb = new StringBuilder();
      String[][] stringArray = getSpine();
      for      (int i=0; (i < stringArray.length); i++)
      {
          for  (int j=0; j < stringArray[i].length; j++)
          {
              sb.append(stringArray[i][j]);
              sb.append(" ");
          }
      }
      return sb.toString().trim();
  }

  public void setSpine(String[][] saa)
  {
    if(saa != null && saa.length > 0)
      spine = makeSFFloatArray(saa);
    else
      spine = new SFFloat[][]{};
  }

  public String[][] getScale()
  {
    if(scale != null && scale.length > 0)
      return makeStringArray(scale);
    return new String[][]{{"1","1"}}; // something to start with
  }

  public String getScaleString()
  {
      StringBuilder sb = new StringBuilder();
      String[][] stringArray = getScale();
      for      (int i=0; (i < stringArray.length); i++)
      {
          for  (int j=0; j < stringArray[i].length; j++)
          {
              sb.append(stringArray[i][j]);
              sb.append(" ");
          }
      }
      return sb.toString().trim();
  }

  public void setScale(String[][] saa)
  {
    if(saa != null && saa.length > 0)
      scale = makeSFFloatArray(saa);
    else
      scale = new SFFloat[][]{};
  }

  public String[][] getOrientation()
  {
    if(orientation != null && orientation.length > 0)
      return makeStringArray(orientation);
    return new String[][]{{"0","0","1","0"}}; // something to start with
  }

  public String getOrientationString()
  {
      StringBuilder sb = new StringBuilder();
      String[][] stringArray = getOrientation();
      for      (int i=0; (i < stringArray.length); i++)
      {
          for  (int j=0; j < stringArray[i].length; j++)
          {
              sb.append(stringArray[i][j]);
              sb.append(" ");
          }
      }
      return sb.toString().trim();
  }

  public void setOrientation(String[][] saa)
  {
    if(saa != null && saa.length > 0)
      orientation = makeSFFloatArray(saa);
    else
      orientation = new SFFloat[][]{};
  }
  
  public boolean isBeginCap()
  {
    return beginCap;
  }

  public void setBeginCap(boolean beginCap)
  {
    this.beginCap = beginCap;
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

  public boolean isEndCap()
  {
    return endCap;
  }

  public void setEndCap(boolean endCap)
  {
    this.endCap = endCap;
  }

//  public String getOrientation()
//  {
//    return orientationOld;
//  }
//
//  public void setOrientation(String orientation)
//  {
//    this.orientationOld = validateNumbersX(orientation,4);
//  }
//
//  public String getScale()
//  {
//    return scaleOld;
//  }
//
//  public void setScale(String scale)
//  {
//    this.scaleOld = validateEvenNumbers(scale);
//  }

  public boolean isSolid()
  {
    return solid;
  }

  public void setSolid(boolean solid)
  {
    this.solid = solid;
  }

    /**
     * @return the crossSectionDefault
     */
    public SFFloat[][] getCrossSectionDefault() {
        return crossSectionDefault;
    }

    /**
     * @return the spineDefault
     */
    public SFFloat[][] getSpineDefault() {
        return spineDefault;
    }

    /**
     * @return the scaleDefault
     */
    public SFFloat[][] getScaleDefault() {
        return scaleDefault;
    }

    /**
     * @return the orientationDefault
     */
    public SFFloat[][] getOrientationDefault() {
        return orientationDefault;
    }

    /**
     * @param creaseAngle the creaseAngle to set
     */
    public void setCreaseAngle(SFFloat creaseAngle) {
        this.creaseAngle = creaseAngle;
    }

    /**
     * @param crossSection the crossSection to set
     */
    public void setCrossSection(SFFloat[][] crossSection) {
        this.crossSection = crossSection;
    }

    /**
     * @param spine the spine to set
     */
    public void setSpine(SFFloat[][] spine) {
        this.spine = spine;
    }

    /**
     * @param scale the scale to set
     */
    public void setScale(SFFloat[][] scale) {
        this.scale = scale;
    }

    /**
     * @param orientation the orientation to set
     */
    public void setOrientation(SFFloat[][] orientation) {
        this.orientation = orientation;
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
