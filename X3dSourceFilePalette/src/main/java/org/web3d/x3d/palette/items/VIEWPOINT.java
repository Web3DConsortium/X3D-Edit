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
import org.web3d.x3d.types.X3DBindableNode;

import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;
import static org.web3d.x3d.palette.X3DPaletteUtilities.*;

/**
 * VIEWPOINT.java
 * Created on August 16, 2007, 3:30 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class VIEWPOINT extends X3DBindableNode
{
  private SFFloat centerOfRotationX, centerOfRotationXDefault;
  private SFFloat centerOfRotationY, centerOfRotationYDefault;
  private SFFloat centerOfRotationZ, centerOfRotationZDefault;
  private String  description;
  private SFFloat fieldOfView,fieldOfViewDefault;
  private boolean jump;
  private boolean retainUserOffsets;
  private SFFloat orientationX, orientationXDefault;
  private SFFloat orientationY, orientationYDefault;
  private SFFloat orientationZ, orientationZDefault;
  private SFFloat orientationAngle, orientationAngleDefault;
  private SFFloat positionX, positionXDefault;
  private SFFloat positionY, positionYDefault;
  private SFFloat positionZ, positionZDefault;

  private boolean appendViewFrustrum = false;

  public VIEWPOINT()
  {
      this.setTraceEventsSelectionAvailable(true);
      this.setTraceEventsTooltip("Trace " + getElementName() + " events on X3D browser console");
  }

  @Override
  public final String getElementName()
  {
    return VIEWPOINT_ELNAME;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return VIEWPOINTCustomizer.class;
  }

  @Override
  @SuppressWarnings("NestedAssignment")
  public void initialize()
  {
    String[] fa = parse3(VIEWPOINT_ATTR_CENTEROFROTATION_DFLT);
    centerOfRotationX = centerOfRotationXDefault = new SFFloat(fa[0],null,null);
    centerOfRotationY = centerOfRotationYDefault = new SFFloat(fa[1],null,null);
    centerOfRotationZ = centerOfRotationZDefault = new SFFloat(fa[2],null,null);

    description = VIEWPOINT_ATTR_DESCRIPTION_DFLT;

          fieldOfView = fieldOfViewDefault = new SFFloat(VIEWPOINT_ATTR_FIELDOFVIEW_DFLT, 0.0f, (float)Math.PI);
                 jump = Boolean.parseBoolean(VIEWPOINT_ATTR_JUMP_DFLT);
	retainUserOffsets = Boolean.parseBoolean(VIEWPOINT_ATTR_RETAINUSEROFFSETS_DFLT);

    fa = parse3(VIEWPOINT_ATTR_POSITION_DFLT);
    positionX = positionXDefault = new SFFloat(fa[0],null,null);
    positionY = positionYDefault = new SFFloat(fa[1],null,null);
    positionZ = positionZDefault = new SFFloat(fa[2],null,null);

    fa = parse4(VIEWPOINT_ATTR_ORIENTATION_DFLT);
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
    org.jdom.Attribute attr = root.getAttribute(VIEWPOINT_ATTR_CENTEROFROTATION_NAME);

    if(attr != null) {
      fa = parse3(attr.getValue());
      centerOfRotationX = new SFFloat(fa[0],null,null);
      centerOfRotationY = new SFFloat(fa[1],null,null);
      centerOfRotationZ = new SFFloat(fa[2],null,null);
    }
    attr = root.getAttribute(VIEWPOINT_ATTR_DESCRIPTION_NAME);
    if(attr != null)
      description = attr.getValue();

    attr = root.getAttribute(VIEWPOINT_ATTR_FIELDOFVIEW_NAME);
    if(attr != null)
      fieldOfView = new SFFloat(attr.getValue(), 0.0f, (float)Math.PI);

    attr = root.getAttribute(VIEWPOINT_ATTR_JUMP_NAME);
    if(attr != null)
      jump = Boolean.parseBoolean(attr.getValue());

    attr = root.getAttribute(VIEWPOINT_ATTR_RETAINUSEROFFSETS_NAME);
    if(attr != null)
      retainUserOffsets = Boolean.parseBoolean(attr.getValue());

    attr = root.getAttribute(VIEWPOINT_ATTR_POSITION_NAME);
    if(attr != null) {
      fa = parse3(attr.getValue());
      positionX = new SFFloat(fa[0],null,null);
      positionY = new SFFloat(fa[1],null,null);
      positionZ = new SFFloat(fa[2],null,null);
    }
    attr = root.getAttribute(VIEWPOINT_ATTR_ORIENTATION_NAME);
    if(attr != null) {
      fa = parse4(attr.getValue());
      orientationX     = new SFFloat(fa[0],null,null);
      orientationY     = new SFFloat(fa[1],null,null);
      orientationZ     = new SFFloat(fa[2],null,null);
      orientationAngle = new SFFloat(fa[3],null,null);
    }
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    
    if (VIEWPOINT_ATTR_CENTEROFROTATION_REQD || (!centerOfRotationX.equals(centerOfRotationXDefault) || !centerOfRotationY.equals(centerOfRotationYDefault) || !centerOfRotationZ.equals(centerOfRotationZDefault))) {
      sb.append(" ");
      sb.append(VIEWPOINT_ATTR_CENTEROFROTATION_NAME);
      sb.append("='");
      sb.append(centerOfRotationX);
      sb.append(" ");
      sb.append(centerOfRotationY);
      sb.append(" ");
      sb.append(centerOfRotationZ);
      sb.append("'");
    }

     if (VIEWPOINT_ATTR_DESCRIPTION_REQD || !description.equals(VIEWPOINT_ATTR_DESCRIPTION_DFLT)) {
      sb.append(" ");
      sb.append(VIEWPOINT_ATTR_DESCRIPTION_NAME);
      sb.append("='");
      sb.append(escapeXmlCharacters(description));
      sb.append("'");
    }

    if (VIEWPOINT_ATTR_FIELDOFVIEW_REQD || !(fieldOfView.equals(fieldOfViewDefault) || fieldOfView.toString().equalsIgnoreCase("0.785") || fieldOfView.toString().equalsIgnoreCase("0.7854"))) {
      sb.append(" ");
      sb.append(VIEWPOINT_ATTR_FIELDOFVIEW_NAME);
      sb.append("='");
      sb.append(fieldOfView);
      sb.append("'");
    }

    if (VIEWPOINT_ATTR_JUMP_REQD || (jump != Boolean.parseBoolean(VIEWPOINT_ATTR_JUMP_DFLT))) {
      sb.append(" ");
      sb.append(VIEWPOINT_ATTR_JUMP_NAME);
      sb.append("='");
      sb.append(jump);
      sb.append("'");
    }

    if (VIEWPOINT_ATTR_ORIENTATION_REQD || (!orientationX.equals(orientationXDefault) || !orientationY.equals(orientationYDefault) || !orientationZ.equals(orientationZDefault) || !orientationAngle.equals(orientationAngleDefault))) {
      sb.append(" ");
      sb.append(VIEWPOINT_ATTR_ORIENTATION_NAME);
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

    if (VIEWPOINT_ATTR_POSITION_REQD || (!positionX.equals(positionXDefault) || !positionY.equals(positionYDefault) || !positionZ.equals(positionZDefault))) {
      sb.append(" ");
      sb.append(VIEWPOINT_ATTR_POSITION_NAME);
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
    
   return sb.toString();
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
    return radiansFormat.format(fieldOfView.getValue());
  }

  public void setFieldOfView(String fieldOfView)
  {
    this.fieldOfView = new SFFloat(fieldOfView, null, null);
  }

  public boolean isJump()
  {
    return jump;
  }

  public void setJump(boolean jump)
  {
    this.jump = jump;
  }

    public boolean isRetainUserOffsets() {
        return retainUserOffsets;
    }

    public void setRetainUserOffsets(boolean retainUserOffsets) {
        this.retainUserOffsets = retainUserOffsets;
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
    this.positionX = new SFFloat(positionX, null, null);
  }

  public String getPositionY()
  {
    return positionY.toString();
  }

  public void setPositionY(String positionY)
  {
    this.positionY = new SFFloat(positionY, null, null);
  }

  public String getPositionZ()
  {
    return positionZ.toString();
  }

  public void setPositionZ(String positionZ)
  {
    this.positionZ = new SFFloat(positionZ, null, null);
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
