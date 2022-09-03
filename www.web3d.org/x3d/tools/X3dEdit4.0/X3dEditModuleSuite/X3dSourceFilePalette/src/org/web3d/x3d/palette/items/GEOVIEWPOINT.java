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
import org.web3d.x3d.types.X3DBindableNode;

import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;
import static org.web3d.x3d.palette.X3DPaletteUtilities.*;

/**
 * GEOVIEWPOINT.java
 * Created on August 16, 2007, 3:30 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class GEOVIEWPOINT extends X3DBindableNode
{
  private SFFloat  centerOfRotationX, centerOfRotationXDefault;
  private SFFloat  centerOfRotationY, centerOfRotationYDefault;
  private SFFloat  centerOfRotationZ, centerOfRotationZDefault;
  private String   description;
  private SFFloat  fieldOfView,fieldOfViewDefault;
  private boolean  jump;
  private boolean  retainUserOffsets;
  private SFFloat  orientationX,     orientationXDefault;
  private SFFloat  orientationY,     orientationYDefault;
  private SFFloat  orientationZ,     orientationZDefault;
  private SFFloat  orientationAngle, orientationAngleDefault;
  private SFDouble positionX, positionXDefault;
  private SFDouble positionY, positionYDefault;
  private SFDouble positionZ, positionZDefault;
  
  private String[] geoSystem;
  private boolean  headlight;
  private String[] navType;
  private String  speedFactor;

  private boolean appendViewFrustrum = false;

  public GEOVIEWPOINT()
  {
  }

  @Override
  public String getElementName()
  {
    return GEOVIEWPOINT_ELNAME;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return GEOVIEWPOINTCustomizer.class;
  }

  @Override
  public void initialize()
  {
    String[] fa;

    description = GEOVIEWPOINT_ATTR_DESCRIPTION_DFLT;
    geoSystem   = GEOVIEWPOINT_ATTR_GEOSYSTEM_DFLT;
    navType     = GEOVIEWPOINT_ATTR_NAVTYPE_DFLT;
    speedFactor = GEOVIEWPOINT_ATTR_SPEEDFACTOR_DFLT;

          fieldOfView = fieldOfViewDefault = new SFFloat(GEOVIEWPOINT_ATTR_FIELDOFVIEW_DFLT, 0.0f, (float)Math.PI);
            headlight = Boolean.parseBoolean(GEOVIEWPOINT_ATTR_HEADLIGHT_DFLT);
                 jump = Boolean.parseBoolean(GEOVIEWPOINT_ATTR_JUMP_DFLT);
	// reuse VIEWPOINT default for ORTHOVIEWPOINT
	retainUserOffsets = Boolean.parseBoolean(VIEWPOINT_ATTR_RETAINUSEROFFSETS_DFLT);

    fa = parse3(GEOVIEWPOINT_ATTR_POSITION_DFLT);
    positionX = positionXDefault = new SFDouble(fa[0],null,null);
    positionY = positionYDefault = new SFDouble(fa[1],null,null);
    positionZ = positionZDefault = new SFDouble(fa[2],null,null);

    fa = parse4(GEOVIEWPOINT_ATTR_ORIENTATION_DFLT);
    orientationX     = orientationXDefault     = new SFFloat(fa[0],null,null);
    orientationY     = orientationYDefault     = new SFFloat(fa[1],null,null);
    orientationZ     = orientationZDefault     = new SFFloat(fa[2],null,null);
    orientationAngle = orientationAngleDefault = new SFFloat(fa[3],null,null);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);

    String[] fa;   
    org.jdom.Attribute attr = root.getAttribute(GEOVIEWPOINT_ATTR_DESCRIPTION_NAME);
    if(attr != null)
      description = attr.getValue();

    attr = root.getAttribute(GEOVIEWPOINT_ATTR_FIELDOFVIEW_NAME);
    if(attr != null)
      fieldOfView = new SFFloat(attr.getValue(), 0.0f, (float)Math.PI);

    attr = root.getAttribute(GEOVIEWPOINT_ATTR_GEOSYSTEM_NAME);
    if(attr != null)
      geoSystem = splitStringIntoStringArray(attr.getValue());

    attr = root.getAttribute(GEOVIEWPOINT_ATTR_HEADLIGHT_NAME);
    if(attr != null)
      headlight = Boolean.parseBoolean(attr.getValue());

    attr = root.getAttribute(GEOVIEWPOINT_ATTR_JUMP_NAME);
    if(attr != null)
      jump = Boolean.parseBoolean(attr.getValue());

    attr = root.getAttribute(VIEWPOINT_ATTR_RETAINUSEROFFSETS_NAME);
    if(attr != null)
      retainUserOffsets = Boolean.parseBoolean(attr.getValue());

    attr = root.getAttribute(GEOVIEWPOINT_ATTR_NAVTYPE_NAME);
    if(attr != null)
      navType = parseMFStringIntoStringArray(attr.getValue(),true);

    attr = root.getAttribute(GEOVIEWPOINT_ATTR_POSITION_NAME);
    if(attr != null) {
      fa = parse3(attr.getValue());
      positionX = new SFDouble(fa[0],null,null);
      positionY = new SFDouble(fa[1],null,null);
      positionZ = new SFDouble(fa[2],null,null);
    }
    
    attr = root.getAttribute(GEOVIEWPOINT_ATTR_ORIENTATION_NAME);
    if(attr != null) {
      fa = parse4(attr.getValue());
      orientationX = new SFFloat(fa[0],null,null);
      orientationY = new SFFloat(fa[1],null,null);
      orientationZ = new SFFloat(fa[2],null,null);
      orientationAngle = new SFFloat(fa[3],null,null);
    }

    attr = root.getAttribute(GEOVIEWPOINT_ATTR_SPEEDFACTOR_NAME);
    if(attr != null)
      speedFactor = attr.getValue();
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

//    if (GEOVIEWPOINT_ATTR_CENTEROFROTATION_REQD || (!centRotX.equals(centRotXDefault) || !centRotY.equals(centRotYDefault) || !centRotZ.equals(centRotZDefault))) {
//      sb.append(" ");
//      sb.append(GEOVIEWPOINT_ATTR_CENTEROFROTATION_NAME);
//      sb.append("='");
//      sb.append(centRotX);
//      sb.append(" ");
//      sb.append(centRotY);
//      sb.append(" ");
//      sb.append(centRotZ);
//      sb.append("'");
//    }

    if (GEOVIEWPOINT_ATTR_DESCRIPTION_REQD || !description.equals(GEOVIEWPOINT_ATTR_DESCRIPTION_DFLT)) {
      sb.append(" ");
      sb.append(GEOVIEWPOINT_ATTR_DESCRIPTION_NAME);
      sb.append("='");
      sb.append(escapeXmlCharacters(description));
      sb.append("'");
    }

    if (GEOVIEWPOINT_ATTR_FIELDOFVIEW_REQD || !fieldOfView.equals(fieldOfViewDefault)) {
      sb.append(" ");
      sb.append(GEOVIEWPOINT_ATTR_FIELDOFVIEW_NAME);
      sb.append("='");
      sb.append(fieldOfView);
      sb.append("'");
    }

    if (GEOVIEWPOINT_ATTR_GEOSYSTEM_REQD || !geoSystemEqualsDefault(geoSystem)) {
      sb.append(" ");
      sb.append(GEOVIEWPOINT_ATTR_GEOSYSTEM_NAME);
      sb.append("='");
      for (String s : geoSystem) {
        sb.append("\"");
        sb.append(s);
        sb.append("\" ");
      }
      sb.setLength(sb.length() - 1); // last space
      sb.append("'");
    }

    if (GEOVIEWPOINT_ATTR_HEADLIGHT_REQD || (headlight != Boolean.parseBoolean(GEOVIEWPOINT_ATTR_HEADLIGHT_DFLT))) {
      sb.append(" ");
      sb.append(GEOVIEWPOINT_ATTR_HEADLIGHT_NAME);
      sb.append("='");
      sb.append(headlight);
      sb.append("'");
    }

    if (GEOVIEWPOINT_ATTR_JUMP_REQD || (jump != Boolean.parseBoolean(GEOVIEWPOINT_ATTR_JUMP_DFLT))) {
      sb.append(" ");
      sb.append(GEOVIEWPOINT_ATTR_JUMP_NAME);
      sb.append("='");
      sb.append(jump);
      sb.append("'");
    }

    if (GEOVIEWPOINT_ATTR_NAVTYPE_REQD || !typesEqualDefault()) {
      sb.append(" ");
      sb.append(GEOVIEWPOINT_ATTR_NAVTYPE_NAME); //todo test
      sb.append("='");
      for (String s : navType) {
        sb.append("\"");
        sb.append(s);
        sb.append("\" ");
      }
      sb.setLength(sb.length() - 1); // last space
      sb.append("'");
    }

    if (GEOVIEWPOINT_ATTR_ORIENTATION_REQD || (!orientationX.equals(orientationXDefault) || !orientationY.equals(orientationYDefault) || !orientationZ.equals(orientationZDefault) || !orientationAngle.equals(orientationAngleDefault))) {
      sb.append(" ");
      sb.append(GEOVIEWPOINT_ATTR_ORIENTATION_NAME);
      sb.append("='");
      sb.append(orientationX);
      sb.append(" ");
      sb.append(orientationY);
      sb.append(" ");
      sb.append(orientationZ);
      sb.append(" ");
      sb.append(orientationAngle);
      sb.append("'");
    }

    if (GEOVIEWPOINT_ATTR_POSITION_REQD || (!positionX.equals(positionXDefault) || !positionY.equals(positionYDefault) || !positionZ.equals(positionZDefault))) {
      sb.append(" ");
      sb.append(GEOVIEWPOINT_ATTR_POSITION_NAME);
      sb.append("='");
      sb.append(positionX);
      sb.append(" ");
      sb.append(positionY);
      sb.append(" ");
      sb.append(positionZ);
      sb.append("'");
    }

    if (VIEWPOINT_ATTR_RETAINUSEROFFSETS_REQD || (retainUserOffsets != Boolean.parseBoolean(VIEWPOINT_ATTR_RETAINUSEROFFSETS_DFLT))) {
      sb.append(" ");
      sb.append(VIEWPOINT_ATTR_RETAINUSEROFFSETS_NAME);
      sb.append("='");
      sb.append(retainUserOffsets);
      sb.append("'");
    }
    
    if (GEOVIEWPOINT_ATTR_SPEEDFACTOR_REQD || !speedFactor.equals(GEOVIEWPOINT_ATTR_SPEEDFACTOR_DFLT)) {
      sb.append(" ");
      sb.append(GEOVIEWPOINT_ATTR_SPEEDFACTOR_NAME);
      sb.append("='");
      sb.append(speedFactor);
      sb.append("'");
    }

    return sb.toString();
  }

  private boolean typesEqualDefault()
  {
    if (GEOVIEWPOINT_ATTR_NAVTYPE_DFLT.length != navType.length)
      return false;

    for(String t : GEOVIEWPOINT_ATTR_NAVTYPE_DFLT) {
      int i;
      for(i=0;i<navType.length;i++) {
        if(navType[i].equals(t)) {
          break;
        }
      }
      if(i >=navType.length)
        return false;  //failed
    }
    return true;
  }

  public String getCenterOfRotationX()
  {
    return centerOfRotationX.toString();
  }

  public void setCenterOfRotationX(String centRotX)
  {
    this.centerOfRotationX = new SFFloat(centRotX, null, null);
  }

  public String getCenterOfRotationY()
  {
    return centerOfRotationY.toString();
  }

  public void setCenterOfRotationY(String centRotY)
  {
    this.centerOfRotationY = new SFFloat(centRotY, null, null);
  }

  public String getCenterOfRotationZ()
  {
    return centerOfRotationZ.toString();
  }

  public void setCenterOfRotationZ(String centRotZ)
  {
    this.centerOfRotationZ = new SFFloat(centRotZ, null, null);
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public String getFieldOfView()
  {
    return fieldOfView.toString();
  }

  public void setFieldOfView(String fieldOfView)
  {
    this.fieldOfView = new SFFloat(fieldOfView, null, null);
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

  public void setGeoSystem (String[] newGeoSystem)
  {
    this.geoSystem = newGeoSystem;
  }

  public boolean isHeadlight()
  {
    return headlight;
  }

  public void setHeadlight(boolean newHeadlight)
  {
    this.headlight = newHeadlight;
  }

  public boolean isJump()
  {
    return jump;
  }

  public void setJump(boolean jump)
  {
    this.jump = jump;
  }
  public String[] getNavType()
  {
    return navType;
  }

    public boolean isRetainUserOffsets() {
        return retainUserOffsets;
    }

    public void setRetainUserOffsets(boolean retainUserOffsets) {
        this.retainUserOffsets = retainUserOffsets;
    }

  public void setNavType(String[] newNavType)
  {
    this.navType = newNavType;
  }

  public String getOrientationX()
  {
    return orientationX.toString();
  }

  public void setOrientationX(String orientX)
  {
    this.orientationX = new SFFloat(orientX, null, null);
  }

  public String getOrientationY()
  {
    return orientationY.toString();
  }

  public void setOrientationY(String orientY)
  {
    this.orientationY = new SFFloat(orientY, null, null);
  }

  public String getOrientationZ()
  {
    return orientationZ.toString();
  }

  public void setOrientationZ(String orientZ)
  {
    this.orientationZ = new SFFloat(orientZ, null, null);
  }

  public String getOrientationAngle()
  {
    return radiansFormat.format(orientationAngle.getValue());
  }

  public void setOrientationAngle(String orientAngle)
  {
    this.orientationAngle = new SFFloat(orientAngle, null, null);
  }

  public String getPositionX()
  {
    return positionX.toString();
  }

  public void setPositionX(String positionX)
  {
    this.positionX = new SFDouble(positionX, null, null);
  }

  public String getPositionY()
  {
    return positionY.toString();
  }

  public void setPositionY(String positionY)
  {
    this.positionY = new SFDouble(positionY, null, null);
  }

  public String getPositionZ()
  {
    return positionZ.toString();
  }

  public void setPositionZ(String positionZ)
  {
    this.positionZ = new SFDouble(positionZ, null, null);
  }

  public String getSpeedFactor() {
    return speedFactor;
  }

  public void setSpeedFactor(String newSpeedFactor)
  {
    this.speedFactor = newSpeedFactor;
  }

    /**
     * @param appendViewFrustrum the appendViewFrustrum to set
     */
    public void setAppendViewFrustrum(boolean appendViewFrustrum) {
        this.appendViewFrustrum = appendViewFrustrum;
    }

    /**
     * @return the appendViewFrustrum
     */
    public boolean isAppendViewFrustrum() {
        return appendViewFrustrum;
    }
}
