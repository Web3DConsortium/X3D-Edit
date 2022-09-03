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

import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;

import org.web3d.x3d.types.X3DGeometryNode;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFDouble;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFFloat;

import static org.web3d.x3d.types.X3DPrimitiveTypes.radiansFormat;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * ELEVATIONGRID.java
 * Recreated on 29 March 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class ELEVATIONGRID extends X3DGeometryNode
{
  //In terms of attributes, this is identical to ElevationGrid plus geoGridOrigin and geoSystem
  private boolean ccw,solid,colorPerVertex,normalPerVertex;

  private SFFloat creaseAngle, creaseAngleDefault;

  private String      height = new String();

  private int     xDimension, xDimensionDefault;
  private int     zDimension, zDimensionDefault;
  // use SFDouble rather than SFFloat so that just one ExpandableElevationGridTable can be used for ElevationGrid and GeoElevationGrid
  private SFDouble xSpacing,  xSpacingDefault;
  private SFDouble zSpacing,  zSpacingDefault; 

  private boolean insertCommas, insertLineBreaks = false;
  
  public ELEVATIONGRID()
  {

  }

  @Override
  public String getElementName()
  {
    return ELEVATIONGRID_ELNAME;
  }

  @Override
  public void initialize()
  {
    ccw = Boolean.parseBoolean(ELEVATIONGRID_ATTR_CCW_DFLT);
    solid = Boolean.parseBoolean(ELEVATIONGRID_ATTR_SOLID_DFLT);
    colorPerVertex  = Boolean.parseBoolean(ELEVATIONGRID_ATTR_COLORPERVERTEX_DFLT);
    normalPerVertex = Boolean.parseBoolean(ELEVATIONGRID_ATTR_NORMALPERVERTEX_DFLT);

    creaseAngle = creaseAngleDefault = new SFFloat(ELEVATIONGRID_ATTR_CREASEANGLE_DFLT,0.0f,null);
    xDimension  = xDimensionDefault  = Integer.valueOf(ELEVATIONGRID_ATTR_XDIMENSION_DFLT);
    zDimension  = zDimensionDefault  = Integer.valueOf(ELEVATIONGRID_ATTR_ZDIMENSION_DFLT);
    xSpacing    = xSpacingDefault    = new SFDouble(ELEVATIONGRID_ATTR_XSPACING_DFLT,0.0d,null);
    zSpacing    = zSpacingDefault    = new SFDouble(ELEVATIONGRID_ATTR_ZSPACING_DFLT,0.0d,null);
    height = ELEVATIONGRID_ATTR_HEIGHT_DFLT;
    
    setContent("\n\t\t\t<!--TODO can optionally add Color|ColorRGBA, Normal and/or TextureCoordinate nodes here-->\n\t\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(ELEVATIONGRID_ATTR_CCW_NAME);
    if (attr != null)
      ccw = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(ELEVATIONGRID_ATTR_SOLID_NAME);
    if (attr != null)
      solid = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(ELEVATIONGRID_ATTR_COLORPERVERTEX_NAME);
    if (attr != null)
      colorPerVertex = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(ELEVATIONGRID_ATTR_NORMALPERVERTEX_NAME);
    if (attr != null)
      normalPerVertex = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(ELEVATIONGRID_ATTR_CREASEANGLE_NAME);
    if (attr != null)
      creaseAngle = new SFFloat(attr.getValue(), 0.0f, null);
    attr = root.getAttribute(ELEVATIONGRID_ATTR_XDIMENSION_NAME);
    if (attr != null)
      xDimension = Integer.valueOf(attr.getValue());
    attr = root.getAttribute(ELEVATIONGRID_ATTR_XSPACING_NAME);
    if (attr != null)
      xSpacing = new SFDouble(attr.getValue(), 0.0d, null);
    attr = root.getAttribute(ELEVATIONGRID_ATTR_ZDIMENSION_NAME);
    if (attr != null)
      zDimension = Integer.valueOf(attr.getValue());
    attr = root.getAttribute(ELEVATIONGRID_ATTR_ZSPACING_NAME);
    if (attr != null)
      zSpacing = new SFDouble(attr.getValue(), 0.0d, null);

    attr = root.getAttribute(ELEVATIONGRID_ATTR_HEIGHT_NAME);
    if (attr != null)
    {
         height = attr.getValue();
         if (attr.getValue().contains(","))  insertCommas     = true;
         if (attr.getValue().contains("\n") ||
             attr.getValue().contains("\r")) insertLineBreaks = true; // TODO not working, line breaks not being passed in from JDOM
         if (insertCommas)                   insertLineBreaks = true; // workaround default, if commas were present then most likely lineBreaks also
    }
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return ELEVATIONGRIDCustomizer.class;
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (ELEVATIONGRID_ATTR_CCW_REQD || ccw != Boolean.parseBoolean(ELEVATIONGRID_ATTR_CCW_DFLT)) {
      sb.append(" ");
      sb.append(ELEVATIONGRID_ATTR_CCW_NAME);
      sb.append("='");
      sb.append(ccw);
      sb.append("'");
    }
    if (ELEVATIONGRID_ATTR_COLORPERVERTEX_REQD || colorPerVertex != Boolean.parseBoolean(ELEVATIONGRID_ATTR_COLORPERVERTEX_DFLT)) {
      sb.append(" ");
      sb.append(ELEVATIONGRID_ATTR_COLORPERVERTEX_NAME);
      sb.append("='");
      sb.append(colorPerVertex);
      sb.append("'");
    }
    if (ELEVATIONGRID_ATTR_CREASEANGLE_REQD || !creaseAngle.equals(creaseAngleDefault)) {
      sb.append(" ");
      sb.append(ELEVATIONGRID_ATTR_CREASEANGLE_NAME);
      sb.append("='");
      sb.append(creaseAngle);
      sb.append("'");
    }
    if (ELEVATIONGRID_ATTR_HEIGHT_REQD || !height.equals(ELEVATIONGRID_ATTR_HEIGHT_DFLT)) {
      sb.append(" ");
      sb.append(ELEVATIONGRID_ATTR_HEIGHT_NAME);
      sb.append("='");
      sb.append(formatStringElements (height, insertCommas, insertLineBreaks, xDimension));
      sb.append("'");
    }
    if (ELEVATIONGRID_ATTR_NORMALPERVERTEX_REQD || normalPerVertex != Boolean.parseBoolean(ELEVATIONGRID_ATTR_NORMALPERVERTEX_DFLT)) {
      sb.append(" ");
      sb.append(ELEVATIONGRID_ATTR_NORMALPERVERTEX_NAME);
      sb.append("='");
      sb.append(normalPerVertex);
      sb.append("'");
    }
    if (ELEVATIONGRID_ATTR_SOLID_REQD || solid != Boolean.parseBoolean(ELEVATIONGRID_ATTR_SOLID_DFLT)) {
      sb.append(" ");
      sb.append(ELEVATIONGRID_ATTR_SOLID_NAME);
      sb.append("='");
      sb.append(solid);
      sb.append("'");
    }
    if (ELEVATIONGRID_ATTR_XDIMENSION_REQD || (xDimension != xDimensionDefault)) {
      sb.append(" ");
      sb.append(ELEVATIONGRID_ATTR_XDIMENSION_NAME);
      sb.append("='");
      sb.append(xDimension);
      sb.append("'");
    }
    if (ELEVATIONGRID_ATTR_XSPACING_REQD || !xSpacing.equals(xSpacingDefault)) {
      sb.append(" ");
      sb.append(ELEVATIONGRID_ATTR_XSPACING_NAME);
      sb.append("='");
      sb.append(xSpacing);
      sb.append("'");
    }
    if (ELEVATIONGRID_ATTR_ZDIMENSION_REQD || (zDimension != zDimensionDefault)) {
      sb.append(" ");
      sb.append(ELEVATIONGRID_ATTR_ZDIMENSION_NAME);
      sb.append("='");
      sb.append(zDimension);
      sb.append("'");
    }
    if (ELEVATIONGRID_ATTR_ZSPACING_REQD || !zSpacing.equals(zSpacingDefault)) {
      sb.append(" ");
      sb.append(ELEVATIONGRID_ATTR_ZSPACING_NAME);
      sb.append("='");
      sb.append(zSpacing);
      sb.append("'");
    }

    return sb.toString();
  }

  public boolean isColorPerVertex()
  {
    return colorPerVertex;
  }

  public void setColorPerVertex(boolean colorPerVertex)
  {
    this.colorPerVertex = colorPerVertex;
  }

  public boolean isCcw()
  {
    return ccw;
  }

  public void setCcw(boolean ccw)
  {
    this.ccw = ccw;
  }

  public String getCreaseAngle()
  {
    return radiansFormat.format(creaseAngle.getValue());
  }

  public void setCreaseAngle(String creaseAngle)
  {
    this.creaseAngle = new SFFloat(creaseAngle,0.0f,null);
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

  public String getHeight()
  {
    return height;
  }

  public void setHeight(String height)
  {
    this.height = validateNumbersX(height,-1);
  }
   
  public String getXDimension()
  {
    return String.valueOf(xDimension);
  }

  public void setXDimension(String newXDimension)
  {
    if (newXDimension.equals(""))
         this.xDimension = 0;
    else this.xDimension = Integer.valueOf(newXDimension);
  }

  public double getXSpacingValue()
  {
    return xSpacing.getValue();
  }

  public String getXSpacing()
  {
    return xSpacing.toString();
  }

  public void setXSpacing(String newXSpacing)
  {
    if (newXSpacing.equals(""))
         this.xSpacing = new SFDouble("0.0");
    else this.xSpacing = new SFDouble(newXSpacing,0.0d,null);
  }

  public String getZDimension()
  {
    return String.valueOf(zDimension);
  }

  public void setZDimension(String newZDimension)
  {
    if (newZDimension.equals(""))
         this.zDimension = 0;
    else this.zDimension = Integer.valueOf(newZDimension);
  }

  public double getZSpacingValue()
  {
    return zSpacing.getValue();
  }


  public String getZSpacing()
  {
    return zSpacing.toString();
  }

  public void setZSpacing(String newZSpacing)
  {
    if (newZSpacing.equals(""))
         this.zSpacing = new SFDouble("0.0");
    else this.zSpacing = new SFDouble(newZSpacing,0.0d,null);
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

    /**
     * Ensure height values are provided (or omitted) if necessary
     */
    public void checkHeightArray()
    {
        // array checks performed prior to (and following) ELEVATIONGRIDCustomizer since table size is controlled by GUI there
        String errorMessage;
        errorMessage = "<ElevationGrid";
        if (getDEFUSEvalue().length() > 0)
            errorMessage += " DEF='" + getDEFUSEvalue() + "'";
        errorMessage += " xDimension='" + xDimension + "'";
        errorMessage += " zDimension='" + zDimension + "'";
            
        String[] heightArray = height.trim().split("(\\s)+");
        int heightArrayLength = heightArray.length;
        
        if (xDimension < 0) 
        {
            xDimension = -xDimension;
            DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message("ELEVATIONGRID.checkHeightArray():\n" + 
                    "found negative xDimension value, reset to xDimension=" + xDimension, NotifyDescriptor.WARNING_MESSAGE));
        }
        if (zDimension < 0) 
        {
            zDimension = -zDimension;
            DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message("ELEVATIONGRID.checkHeightArray():\n" + 
                    "found negative zDimension value, reset to zDimension=" + zDimension, NotifyDescriptor.WARNING_MESSAGE));
        }
        if ((xDimension > 0) && (zDimension > 0) && (heightArrayLength == 0))
        {
            height = "";
            for (int col = 0; col < xDimension; col++)
            {
                for (int row = 0; row < zDimension; row++)
                {
                    height += "0 ";
                }
            }
            height = height.trim();
            
            DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message("ELEVATIONGRID.checkHeightArray():\n" + 
                    "missing height array, appended (xDimension * zDimension = " + xDimension + " * " + zDimension + " = " + (xDimension*zDimension) + ") zero values", NotifyDescriptor.WARNING_MESSAGE));
            
            errorMessage += "/> missing height array, inserted missing array of " + (xDimension * zDimension) + " height='0 0 ... 0' values";
            System.out.println(errorMessage);
        }
        else if ((xDimension > 0) && (zDimension > 0) && (heightArrayLength < (xDimension * zDimension)))
        {
            DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message("ELEVATIONGRID.checkHeightArray():\n" + 
                    "found insufficient (" + heightArrayLength + ") height values, appended " + (xDimension * zDimension - heightArrayLength) + " zeroes\n" +
                    "for total of (xDimension * zDimension = " + xDimension + " * " + zDimension + " = " + (xDimension*zDimension) + ") values", NotifyDescriptor.WARNING_MESSAGE));
            for (int i=heightArrayLength; i < (xDimension * zDimension); i++)
            {
                height += " 0";
            }
        }
        else if ((xDimension > 0) && (zDimension > 0) && (heightArrayLength > (xDimension * zDimension)))
        {
            DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message("ELEVATIONGRID.checkHeightArray():\n" + 
                    "found too many height values, " + (heightArrayLength - xDimension * zDimension) + " excess values will be truncated\n" +
                    "for total of (xDimension * zDimension = " + xDimension + " * " + zDimension + " = " + (xDimension*zDimension) + ") values\n(View IDE Log console for truncated values)", NotifyDescriptor.WARNING_MESSAGE));
            errorMessage += "/> had excess height array values: ";
            System.out.println(errorMessage);
            for (int i= (xDimension * zDimension); i < heightArrayLength; i++)
            {
                errorMessage += " " + heightArray[i];
            }
        }
    }
}
