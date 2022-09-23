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
import org.web3d.x3d.types.X3DGroupingNode;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFDouble;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFFloat;
import static org.web3d.x3d.types.X3DPrimitiveTypes.radiansFormat;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * GEOTRANSFORM.java
 * Created on 29 March 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class GEOTRANSFORM extends X3DGroupingNode
{
  private SFDouble translationX, translationXDefault;
  private SFDouble translationY, translationYDefault;
  private SFDouble translationZ, translationZDefault;

  private SFDouble rotationX,     rotationXDefault;
  private SFDouble rotationY,     rotationYDefault;
  private SFDouble rotationZ,     rotationZDefault;
  private SFDouble rotationAngle, rotationAngleDefault;

  private SFDouble geoCenterX, geoCenterXDefault;
  private SFDouble geoCenterY, geoCenterYDefault;
  private SFDouble geoCenterZ, geoCenterZDefault;

  private SFDouble scaleX, scaleXDefault;
  private SFDouble scaleY, scaleYDefault;
  private SFDouble scaleZ, scaleZDefault;

  private SFDouble scaleOrientationX,     scaleOrientationXDefault;
  private SFDouble scaleOrientationY,     scaleOrientationYDefault;
  private SFDouble scaleOrientationZ,     scaleOrientationZDefault;
  private SFDouble scaleOrientationAngle, scaleOrientationAngleDefault;
  
  private String[] geoSystem;

  public GEOTRANSFORM()
  {
  }

  @Override
  public String getElementName()
  {
    return GEOTRANSFORM_ELNAME;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return GEOTRANSFORMCustomizer.class;
  }

  @Override
  public void initialize()
  {
    String[] da;
    da = parse3(GEOTRANSFORM_ATTR_TRANSLATION_DFLT);
    translationX = translationXDefault = new SFDouble(da[0], null, null);
    translationY = translationYDefault = new SFDouble(da[1], null, null);
    translationZ = translationZDefault = new SFDouble(da[2], null, null);

    da = parse4(GEOTRANSFORM_ATTR_ROTATION_DFLT);
    rotationX = rotationXDefault = new SFDouble(da[0], null, null);
    rotationY = rotationYDefault = new SFDouble(da[1], null, null);
    rotationZ = rotationZDefault = new SFDouble(da[2], null, null);
    rotationAngle = rotationAngleDefault = new SFDouble(da[3], null, null);

    da = parse3(GEOTRANSFORM_ATTR_GEOCENTER_DFLT);
    geoCenterX = geoCenterXDefault = new SFDouble(da[0], null, null);
    geoCenterY = geoCenterYDefault = new SFDouble(da[1], null, null);
    geoCenterZ = geoCenterZDefault = new SFDouble(da[2], null, null);

    da = parse3(GEOTRANSFORM_ATTR_SCALE_DFLT);
    scaleX = scaleXDefault = new SFDouble(da[0], null, null);
    scaleY = scaleYDefault = new SFDouble(da[1], null, null);
    scaleZ = scaleZDefault = new SFDouble(da[2], null, null);

    da = parse4(GEOTRANSFORM_ATTR_SCALEORIENTATION_DFLT);
    scaleOrientationX = scaleOrientationXDefault = new SFDouble(da[0], null, null);
    scaleOrientationY = scaleOrientationYDefault = new SFDouble(da[1], null, null);
    scaleOrientationZ = scaleOrientationZDefault = new SFDouble(da[2], null, null);
    scaleOrientationAngle = scaleOrientationAngleDefault = new SFDouble(da[3], null, null);

    da = parse3(GEOTRANSFORM_ATTR_BBOXCENTER_DFLT);
    bboxCenterX = bboxCenterXDefault = new SFFloat(da[0], null, null);
    bboxCenterY = bboxCenterYDefault = new SFFloat(da[1], null, null);
    bboxCenterZ = bboxCenterZDefault = new SFFloat(da[2], null, null);

    da = parse3(GEOTRANSFORM_ATTR_BBOXSIZE_DFLT);
    bboxSizeX = bboxSizeXDefault = new SFFloat(da[0], null, null);
    bboxSizeY = bboxSizeYDefault = new SFFloat(da[1], null, null);
    bboxSizeZ = bboxSizeZDefault = new SFFloat(da[2], null, null);
    
    geoSystem = GEOTRANSFORM_ATTR_GEOSYSTEM_DFLT;

    setContent("\n\t\t<!-- TODO add children nodes and statements here -->\n\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    String[] da;
    attr = root.getAttribute(GEOTRANSFORM_ATTR_TRANSLATION_NAME);
    if (attr != null) {
      da = parse3(attr.getValue());
      translationX = new SFDouble(da[0], null, null);
      translationY = new SFDouble(da[1], null, null);
      translationZ = new SFDouble(da[2], null, null);
    }
    attr = root.getAttribute(GEOTRANSFORM_ATTR_ROTATION_NAME);
    if (attr != null) {
      da = parse4(attr.getValue());
      rotationX = new SFDouble(da[0], null, null);
      rotationY = new SFDouble(da[1], null, null);
      rotationZ = new SFDouble(da[2], null, null);
      rotationAngle = new SFDouble(da[3], null, null);
    }
    attr = root.getAttribute(GEOTRANSFORM_ATTR_GEOCENTER_NAME);
    if (attr != null) {
      da = parse3(attr.getValue());
      geoCenterX = new SFDouble(da[0], null, null);
      geoCenterY = new SFDouble(da[1], null, null);
      geoCenterZ = new SFDouble(da[2], null, null);
    }
    attr = root.getAttribute(GEOTRANSFORM_ATTR_SCALE_NAME);
    if (attr != null) {
      da = parse3(attr.getValue());
      scaleX = new SFDouble(da[0], null, null);
      scaleY = new SFDouble(da[1], null, null);
      scaleZ = new SFDouble(da[2], null, null);
    }
    attr = root.getAttribute(GEOTRANSFORM_ATTR_SCALEORIENTATION_NAME);
    if (attr != null) {
      da = parse4(attr.getValue());
      scaleOrientationX = new SFDouble(da[0], null, null);
      scaleOrientationY = new SFDouble(da[1], null, null);
      scaleOrientationZ = new SFDouble(da[2], null, null);
      scaleOrientationAngle = new SFDouble(da[3], null, null);
    }

    attr = root.getAttribute(GEOTRANSFORM_ATTR_BBOXCENTER_NAME);
    if (attr != null) {
      da = parse3(attr.getValue());
      bboxCenterX = new SFFloat(da[0], null, null);
      bboxCenterY = new SFFloat(da[1], null, null);
      bboxCenterZ = new SFFloat(da[2], null, null);
    }
    attr = root.getAttribute(GEOTRANSFORM_ATTR_BBOXSIZE_NAME);
    if (attr != null) {
      da = parse3(attr.getValue());
      bboxSizeX = new SFFloat(da[0], null, null);
      bboxSizeY = new SFFloat(da[1], null, null);
      bboxSizeZ = new SFFloat(da[2], null, null);
    }
    attr = root.getAttribute(GEOTRANSFORM_ATTR_GEOSYSTEM_NAME);
    if (attr != null)
      geoSystem = splitStringIntoStringArray(attr.getValue());
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (GEOTRANSFORM_ATTR_BBOXCENTER_REQD ||
           (!bboxCenterX.equals(bboxCenterXDefault) ||
            !bboxCenterY.equals(bboxCenterYDefault) ||
            !bboxCenterZ.equals(bboxCenterZDefault))) {
      sb.append(" ");
      sb.append(GEOTRANSFORM_ATTR_BBOXCENTER_NAME);
      sb.append("='");
      sb.append(bboxCenterX);
      sb.append(" ");
      sb.append(bboxCenterY);
      sb.append(" ");
      sb.append(bboxCenterZ);
      sb.append("'");
    }
    if (GEOTRANSFORM_ATTR_BBOXSIZE_REQD ||
           (!bboxSizeX.equals(bboxSizeXDefault) ||
            !bboxSizeY.equals(bboxSizeYDefault) ||
            !bboxSizeZ.equals(bboxSizeZDefault))) {
      sb.append(" ");
      sb.append(GEOTRANSFORM_ATTR_BBOXSIZE_NAME);
      sb.append("='");
      sb.append(bboxSizeX);
      sb.append(" ");
      sb.append(bboxSizeY);
      sb.append(" ");
      sb.append(bboxSizeZ);
      sb.append("'");
    }
    if (GEOTRANSFORM_ATTR_GEOCENTER_REQD ||
           (!geoCenterX.equals(geoCenterXDefault) ||
            !geoCenterY.equals(geoCenterYDefault) ||
            !geoCenterZ.equals(geoCenterZDefault))) {
      sb.append(" ");
      sb.append(GEOTRANSFORM_ATTR_GEOCENTER_NAME);
      sb.append("='");
      sb.append(geoCenterX);
      sb.append(" ");
      sb.append(geoCenterY);
      sb.append(" ");
      sb.append(geoCenterZ);
      sb.append("'");
    }
    if (GEOTRANSFORM_ATTR_GEOSYSTEM_REQD || !geoSystemEqualsDefault(geoSystem))
    {
      sb.append(" ");
      sb.append(GEOTRANSFORM_ATTR_GEOSYSTEM_NAME);
      sb.append("='");
      for (String s : geoSystem) {
        sb.append("\"");
        sb.append(s);
        sb.append("\" ");
      }
      sb.setLength(sb.length() - 1); // last space
      sb.append("'");
    }
    if (GEOTRANSFORM_ATTR_ROTATION_REQD ||
                   (!rotationX.equals(rotationXDefault) ||
                    !rotationY.equals(rotationYDefault) ||
                    !rotationZ.equals(rotationZDefault) ||
                    !rotationAngle.equals(rotationAngleDefault))) {
      sb.append(" ");
      sb.append(GEOTRANSFORM_ATTR_ROTATION_NAME);
      sb.append("='");
      sb.append(rotationX);
      sb.append(" ");
      sb.append(rotationY);
      sb.append(" ");
      sb.append(rotationZ);
      sb.append(" ");
      sb.append(rotationAngle);
      sb.append("'");
    }
    if (GEOTRANSFORM_ATTR_SCALE_REQD ||
           (!scaleX.equals(scaleXDefault) ||
            !scaleY.equals(scaleYDefault) ||
            !scaleZ.equals(scaleZDefault))) {
      sb.append(" ");
      sb.append(GEOTRANSFORM_ATTR_SCALE_NAME);
      sb.append("='");
      sb.append(scaleX);
      sb.append(" ");
      sb.append(scaleY);
      sb.append(" ");
      sb.append(scaleZ);
      sb.append("'");
    }
    if (GEOTRANSFORM_ATTR_SCALEORIENTATION_REQD ||
           (!scaleOrientationX.equals(scaleOrientationXDefault) ||
            !scaleOrientationY.equals(scaleOrientationYDefault) ||
            !scaleOrientationZ.equals(scaleOrientationZDefault) ||
            !scaleOrientationAngle.equals(scaleOrientationAngleDefault))) {
      sb.append(" ");
      sb.append(GEOTRANSFORM_ATTR_SCALEORIENTATION_NAME);
      sb.append("='");
      sb.append(scaleOrientationX);
      sb.append(" ");
      sb.append(scaleOrientationY);
      sb.append(" ");
      sb.append(scaleOrientationZ);
      sb.append(" ");
      sb.append(scaleOrientationZ);
      sb.append("'");
    }
    if (GEOTRANSFORM_ATTR_TRANSLATION_REQD ||
           (!translationX.equals(translationXDefault) ||
            !translationY.equals(translationYDefault) ||
            !translationZ.equals(translationZDefault))) {

      sb.append(" ");
      sb.append(GEOTRANSFORM_ATTR_TRANSLATION_NAME);
      sb.append("='");
      sb.append(translationX);
      sb.append(" ");
      sb.append(translationY);
      sb.append(" ");
      sb.append(translationZ);
      sb.append("'");
    }

    return sb.toString();
  }

  public String getGeoCenterX()
  {
    return geoCenterX.toString();
  }

  public void setGeoCenterX(String geoCenter0)
  {
    this.geoCenterX = new SFDouble(geoCenter0, null, null);
  }

  public String getGeoCenterY()
  {
    return geoCenterY.toString();
  }

  public void setGeoCenterY(String geoCenter1)
  {
    this.geoCenterY = new SFDouble(geoCenter1, null, null);
  }

  public String getGeoCenterZ()
  {
    return geoCenterZ.toString();
  }

  public void setGeoCenterZ(String geoCenter2)
  {
    this.geoCenterZ = new SFDouble(geoCenter2, null, null);
  }

  public String getRotationX()
  {
    return rotationX.toString();
  }

  public void setRotationX(String rotationX)
  {
    this.rotationX = new SFDouble(rotationX, null, null);
  }

  public String getRotationY()
  {
    return rotationY.toString();
  }

  public void setRotationY(String rotationY)
  {
    this.rotationY = new SFDouble(rotationY, null, null);
  }

  public String getRotationZ()
  {
    return rotationZ.toString();
  }

  public void setRotationZ(String rotationZ)
  {
    this.rotationZ = new SFDouble(rotationZ, null, null);
  }

  public String getRotationAngle()
  {
    return radiansFormat.format(rotationAngle.getValue());
  }

  public void setRotationAngle(String rotationAngle)
  {
    this.rotationAngle = new SFDouble(rotationAngle, null, null);
  }

  public String getScaleX()
  {
    return scaleX.toString();
  }

  public void setScaleX(String scale0)
  {
    this.scaleX = new SFDouble(scale0, null, null);
  }

  public String getScaleY()
  {
    return scaleY.toString();
  }

  public void setScaleY(String scale1)
  {
    this.scaleY = new SFDouble(scale1, null, null);
  }

  public String getScaleZ()
  {
    return scaleZ.toString();
  }

  public void setScaleZ(String scaleZ)
  {
    this.scaleZ = new SFDouble(scaleZ, null, null);
  }

  public String getScaleOrientationX()
  {
    return scaleOrientationX.toString();
  }

  public void setScaleOrientationX(String scaleOrientationX)
  {
    this.scaleOrientationX = new SFDouble(scaleOrientationX, null, null);
  }

  public String getScaleOrientationY()
  {
    return scaleOrientationY.toString();
  }

  public void setScaleOrientationY(String scaleOrientationY)
  {
    this.scaleOrientationY = new SFDouble(scaleOrientationY, null, null);
  }

  public String getScaleOrientationZ()
  {
    return scaleOrientationZ.toString();
  }

  public void setScaleOrientationZ(String scaleOrientationZ)
  {
    this.scaleOrientationZ = new SFDouble(scaleOrientationZ, null, null);
  }

  public String getScaleOrientationAngle()
  {
      return radiansFormat.format(scaleOrientationAngle.getValue());
  }

  public void setScaleOrientationAngle(String scaleOrientationAngle)
  {
    this.scaleOrientationAngle = new SFDouble(scaleOrientationAngle, null, null);
  }

  public String getTranslationX()
  {
    return translationX.toString();
  }

  public void setTranslationX(String translation0)
  {
    this.translationX = new SFDouble(translation0, null, null);
  }

  public String getTranslationY()
  {
    return translationY.toString();
  }

  public void setTranslationY(String translation1)
  {
    this.translationY = new SFDouble(translation1, null, null);
  }

  public String getTranslationZ()
  {
    return translationZ.toString();
  }

  public void setTranslationZ(String translation2)
  {
    this.translationZ = new SFDouble(translation2, null, null);
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
    this.geoSystem = newGeoSystem;
  }

}