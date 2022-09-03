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

/**
 * NAVIGATIONINFO.java
 * Created on August 16, 2007, 3:30 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class NAVIGATIONINFO extends X3DBindableNode
{
  private String[] type;
  private String[] transitionType;
  private SFFloat speed,speedDefault;
  private boolean headlight;
  private SFFloat transitionTime,transitionTimeDefault;
  private SFFloat visibilityLimit,visibilityLimitDefault;
  private SFFloat avatarSizeX,avatarSizeXDefault;
  private SFFloat avatarSizeY,avatarSizeYDefault;
  private SFFloat avatarSizeZ,avatarSizeZDefault;

  public NAVIGATIONINFO()
  {
      this.setTraceEventsSelectionAvailable(true);
      this.setTraceEventsTooltip("Trace " + getElementName() + " events on X3D browser console");
  }

  @Override
  public final String getElementName()
  {
    return NAVINFO_ELNAME;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return NAVIGATIONINFOCustomizer.class;
  }

  @Override
  @SuppressWarnings("NestedAssignment")
  public void initialize()
  {
    type           = NAVINFO_ATTR_TYPE_DFLT;
    transitionType = NAVINFO_ATTR_TRANSTYPE_DFLT;
    headlight      = Boolean.parseBoolean(NAVINFO_ATTR_HEADLIGHT_DFLT);

    transitionTime  = transitionTimeDefault  = new SFFloat(NAVINFO_ATTR_TRANSTIME_DFLT, 0.0f, null);
    visibilityLimit = visibilityLimitDefault = new SFFloat(NAVINFO_ATTR_VISLIMIT_DFLT, 0.0f, null);
    String[] flt    = parse3(NAVINFO_ATTR_AVATARSIZE_DFLT);
    avatarSizeX     = avatarSizeXDefault     = new SFFloat(flt[0], 0.0f, null);
    avatarSizeY     = avatarSizeYDefault     = new SFFloat(flt[1], 0.0f, null);
    avatarSizeZ     = avatarSizeZDefault     = new SFFloat(flt[2], 0.0f, null);
    speed           = speedDefault           = new SFFloat(NAVINFO_ATTR_SPEED_DFLT, 0.0f, null);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);

    org.jdom.Attribute attr = root.getAttribute(NAVINFO_ATTR_TYPE_NAME);
    if(attr != null)
      type = parseMFStringIntoStringArray(attr.getValue(),true);
    attr = root.getAttribute(NAVINFO_ATTR_HEADLIGHT_NAME);
    if(attr != null)
      headlight = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(NAVINFO_ATTR_TRANSTYPE_NAME);
    if(attr != null)
      transitionType = parseMFStringIntoStringArray(attr.getValue(),true);
    attr = root.getAttribute(NAVINFO_ATTR_TRANSTIME_NAME);
    if(attr != null)
      transitionTime = new SFFloat(attr.getValue(), 0.0f, null);
    attr = root.getAttribute(NAVINFO_ATTR_VISLIMIT_NAME);
    if(attr != null)
      visibilityLimit = new SFFloat(attr.getValue(), 0.0f, null);
    attr = root.getAttribute(NAVINFO_ATTR_AVATARSIZE_NAME);    
	if(attr != null)
	{
		String[] flt = NAVINFO_ATTR_AVATARSIZE_DFLT.split(" ");
		try {
			flt = parse3(attr.getValue());
		} 
		catch (NumberFormatException e) 
		{
			if (attr.getValue().length() > 0) // assume single value
				flt = parse3(attr.getValue() + NAVINFO_ATTR_AVATARSIZE_DFLT.substring(NAVINFO_ATTR_AVATARSIZE_DFLT.indexOf(" ")));
			else
				flt = parse3(NAVINFO_ATTR_AVATARSIZE_DFLT);
		}
	  avatarSizeX = new SFFloat(flt[0], 0.0f, null);
	  avatarSizeY = new SFFloat(flt[1], 0.0f, null);
	  avatarSizeZ = new SFFloat(flt[2], 0.0f, null);
	}
    attr = root.getAttribute(NAVINFO_ATTR_SPEED_NAME);
    if(attr != null)
      speed = new SFFloat(attr.getValue(), 0.0f, null);
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (NAVINFO_ATTR_AVATARSIZE_REQD ||
           (!avatarSizeX.equals(avatarSizeXDefault) ||
            !avatarSizeY.equals(avatarSizeYDefault) ||
            !avatarSizeZ.equals(avatarSizeZDefault)))
    {
      sb.append(" ");
      sb.append(NAVINFO_ATTR_AVATARSIZE_NAME);
      sb.append("='");
      sb.append(avatarSizeX);
      sb.append(" ");
      sb.append(avatarSizeY);
      sb.append(" ");
      sb.append(avatarSizeZ);
      sb.append("'");
    }

    if (NAVINFO_ATTR_HEADLIGHT_REQD || headlight != Boolean.parseBoolean(NAVINFO_ATTR_HEADLIGHT_DFLT)) {
      sb.append(" ");
      sb.append(NAVINFO_ATTR_HEADLIGHT_NAME);
      sb.append("='");
      sb.append(headlight);
      sb.append("'");
    }

    if (NAVINFO_ATTR_SPEED_REQD || !speed.equals(speedDefault)) {
      sb.append(" ");
      sb.append(NAVINFO_ATTR_SPEED_NAME);
      sb.append("='");
      sb.append(speed);
      sb.append("'");
    }

    if (NAVINFO_ATTR_TRANSTIME_REQD || !transitionTime.equals(transitionTimeDefault)) {
      sb.append(" ");
      sb.append(NAVINFO_ATTR_TRANSTIME_NAME);
      sb.append("='");
      sb.append(transitionTime);
      sb.append("'");
    }

    if (NAVINFO_ATTR_TRANSTYPE_REQD || !transitionTypesEqualDefault()) {
      sb.append(" ");
      sb.append(NAVINFO_ATTR_TRANSTYPE_NAME);
      sb.append("='");
      for (String s : transitionType) {
        sb.append("\"");
        sb.append(s);
        sb.append("\" ");
      }
      sb.setLength(sb.length() - 1); // last space
      sb.append("'");
    }

    if (NAVINFO_ATTR_TYPE_REQD || !typesEqualDefault()) {
      sb.append(" ");
      sb.append(NAVINFO_ATTR_TYPE_NAME);
      sb.append("='");
      for (String s : type) {
        sb.append("\"");
        sb.append(s);
        sb.append("\" ");
      }
      sb.setLength(sb.length() - 1); // last space
      sb.append("'");
    }

    if (NAVINFO_ATTR_VISLIMIT_REQD || !visibilityLimit.equals(visibilityLimitDefault)) {
      sb.append(" ");
      sb.append(NAVINFO_ATTR_VISLIMIT_NAME);
      sb.append("='");
      sb.append(visibilityLimit);
      sb.append("'");
    }

    return sb.toString();
  }

  private boolean typesEqualDefault()
  {
    if (NAVINFO_ATTR_TYPE_DFLT.length != type.length)
      return false;

    for(String t : NAVINFO_ATTR_TYPE_DFLT) {
      int i;
      for(i=0;i<type.length;i++) {
        if(type[i].equals(t)) {
          break;
        }
      }
      if(i >=type.length)
        return false;  //failed
    }
    return true;
  }

  private boolean transitionTypesEqualDefault()
  {
    if (NAVINFO_ATTR_TRANSTYPE_DFLT.length != transitionType.length)
      return false;

    for(String t : NAVINFO_ATTR_TRANSTYPE_DFLT) {
      int i;
      for(i=0;i<transitionType.length;i++) {
        if(transitionType[i].equals(t)) {
          break;
        }
      }
      if(i >=transitionType.length)
        return false;  //failed
    }
    return true;
  }

  public String[] getType()
  {
    return type;
  }

  public void setType(String[] type)
  {
    this.type = type;
  }

  public String[] getTransitionType()
  {
    return transitionType;
  }

  public void setTransitionType(String[] transitionType)
  {
    this.transitionType = transitionType;
  }

  public String getSpeed()
  {
    return speed.toString();
  }

  public void setSpeed(String speed)
  {
    this.speed = new SFFloat(speed,null,null);
  }

  public boolean isHeadlight()
  {
    return headlight;
  }

  public void setHeadlight(boolean headlight)
  {
    this.headlight = headlight;
  }

  public String getTransitionTime()
  {
    return transitionTime.toString();
  }

  public void setTransitionTime(String transitionTime)
  {
    this.transitionTime = new SFFloat(transitionTime,null,null);
  }

  public String getVisibilityLimit()
  {
    return visibilityLimit.toString();
  }

  public void setVisibilityLimit(String visibilityLimit)
  {
    this.visibilityLimit = new SFFloat(visibilityLimit,null,null);
  }

  public String getAvatarSizeX()
  {
    return avatarSizeX.toString();
  }

  public void setAvatarSizeX(String avatarSizeX)
  {
    this.avatarSizeX = new SFFloat(avatarSizeX,null,null);
  }

  public String getAvatarSizeY()
  {
    return avatarSizeY.toString();
  }

  public void setAvatarSizeY(String avatarSizeY)
  {
    this.avatarSizeY = new SFFloat(avatarSizeY,null,null);
  }

  public String getAvatarSizeZ()
  {
    return avatarSizeZ.toString();
  }

  public void setAvatarSizeZ(String avatarSizeZ)
  {
    this.avatarSizeZ = new SFFloat(avatarSizeZ,null,null);
  }
}
