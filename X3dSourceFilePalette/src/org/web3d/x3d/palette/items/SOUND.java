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
import org.web3d.x3d.types.X3DSoundNode;

import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * SOUND.java
 * Created on Sep 12, 2007, 2:46 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class SOUND extends X3DSoundNode
{
  private boolean spatialize, spatializeDefault;
  private SFFloat location0, location0Default;
  private SFFloat location1, location1Default;
  private SFFloat location2, location2Default;
  private SFFloat direction0, direction0Default;
  private SFFloat direction1, direction1Default;
  private SFFloat direction2, direction2Default;

  private SFFloat intensity, intensityDefault;
  private SFFloat priority, priorityDefault;
  private SFFloat minFront, minFrontDefault;
  private SFFloat maxFront, maxFrontDefault;
  private SFFloat minBack, minBackDefault;
  private SFFloat maxBack, maxBackDefault;

  private boolean appendOutlineGeometry = false;

  public SOUND()
  {
  }

  @Override
  public String getElementName()
  {
    return SOUND_ELNAME;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return SOUNDCustomizer.class;
  }

  @Override
  public void initialize()
  {
    spatialize   = spatializeDefault = Boolean.parseBoolean(SOUND_ATTR_SPATIALIZE_DFLT);

    String[] sa = parse3(SOUND_ATTR_LOCATION_DFLT);
    location0 = location0Default = new SFFloat(sa[0],null,null);
    location1 = location1Default = new SFFloat(sa[1],null,null);
    location2 = location2Default = new SFFloat(sa[2],null,null);

    sa = parse3(SOUND_ATTR_DIRECTION_DFLT);
    direction0 = direction0Default = new SFFloat(sa[0],null,null);
    direction1 = direction1Default = new SFFloat(sa[1],null,null);
    direction2 = direction2Default = new SFFloat(sa[2],null,null);

    intensity = intensityDefault = new SFFloat(SOUND_ATTR_INTENSITY_DFLT,0.0f, 1.0f);
    priority  = priorityDefault  = new SFFloat(SOUND_ATTR_PRIORITY_DFLT ,0.0f, 1.0f);
    minFront  = minFrontDefault  = new SFFloat(SOUND_ATTR_MINFRONT_DFLT ,0.0f, null);
    minBack   = minBackDefault   = new SFFloat(SOUND_ATTR_MINBACK_DFLT  ,0.0f, null);
    maxFront  = maxFrontDefault  = new SFFloat(SOUND_ATTR_MAXFRONT_DFLT ,0.0f, null);
    maxBack   = maxBackDefault   = new SFFloat(SOUND_ATTR_MAXBACK_DFLT  ,0.0f, null);

    setContent("\n\t\t\t<!--TODO add sound source AudioClip or MovieTexture node here-->\n\t\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(SOUND_ATTR_SPATIALIZE_NAME);
    if (attr != null)
      spatialize = Boolean.parseBoolean(attr.getValue());
    String[] sa;
    attr = root.getAttribute(SOUND_ATTR_LOCATION_NAME);
    if (attr != null) {
      sa = parse3(attr.getValue());
      location0 = new SFFloat(sa[0], null, null);
      location1 = new SFFloat(sa[1], null, null);
      location2 = new SFFloat(sa[2], null, null);
    }
    attr = root.getAttribute(SOUND_ATTR_DIRECTION_NAME);
    if (attr != null) {
      sa = parse3(attr.getValue());
      direction0 = new SFFloat(sa[0], null, null);
      direction1 = new SFFloat(sa[1], null, null);
      direction2 = new SFFloat(sa[2], null, null);
    }
    attr = root.getAttribute(SOUND_ATTR_INTENSITY_NAME);
    if (attr != null)
      intensity = new SFFloat(attr.getValue(), 0.0f, 1.0f);
    attr = root.getAttribute(SOUND_ATTR_PRIORITY_NAME);
    if (attr != null)
      priority = new SFFloat(attr.getValue(), 0.0f, 1.0f);
    attr = root.getAttribute(SOUND_ATTR_MINFRONT_NAME);
    if (attr != null)
      minFront = new SFFloat(attr.getValue(), 0.0f, null);
    attr = root.getAttribute(SOUND_ATTR_MINBACK_NAME);
    if (attr != null)
      minBack = new SFFloat(attr.getValue(), 0.0f, null);
    attr = root.getAttribute(SOUND_ATTR_MAXFRONT_NAME);
    if (attr != null)
      maxFront = new SFFloat(attr.getValue(), 0.0f, null);
    attr = root.getAttribute(SOUND_ATTR_MAXBACK_NAME);
    if (attr != null)
      maxBack = new SFFloat(attr.getValue(), 0.0f, null);
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    if (SOUND_ATTR_DIRECTION_REQD ||
            (!direction0.equals(direction0Default)) ||
            (!direction1.equals(direction1Default)) ||
            (!direction2.equals(direction2Default)))
    {
      sb.append(" ");
      sb.append(SOUND_ATTR_DIRECTION_NAME);
      sb.append("='");
      sb.append(direction0);
      sb.append(" ");
      sb.append(direction1);
      sb.append(" ");
      sb.append(direction2);
      sb.append("'");
    }
    if (SOUND_ATTR_INTENSITY_REQD || !intensity.equals(intensityDefault)) {

      sb.append(" ");
      sb.append(SOUND_ATTR_INTENSITY_NAME);
      sb.append("='");
      sb.append(intensity);
      sb.append("'");
    }
    if (SOUND_ATTR_LOCATION_REQD ||
            (!location0.equals(location0Default)) ||
            (!location1.equals(location1Default)) ||
            (!location2.equals(location2Default)))
    {
      sb.append(" ");
      sb.append(SOUND_ATTR_LOCATION_NAME);
      sb.append("='");
      sb.append(location0);
      sb.append(" ");
      sb.append(location1);
      sb.append(" ");
      sb.append(location2);
      sb.append("'");
    }
    if (SOUND_ATTR_MAXBACK_REQD || !maxBack.equals(maxBackDefault)) {

      sb.append(" ");
      sb.append(SOUND_ATTR_MAXBACK_NAME);
      sb.append("='");
      sb.append(maxBack);
      sb.append("'");
    }
    if (SOUND_ATTR_MAXFRONT_REQD || !maxFront.equals(maxFrontDefault)) {

      sb.append(" ");
      sb.append(SOUND_ATTR_MAXFRONT_NAME);
      sb.append("='");
      sb.append(maxFront);
      sb.append("'");
    }
    if (SOUND_ATTR_MINBACK_REQD || !minBack.equals(minBackDefault)) {

      sb.append(" ");
      sb.append(SOUND_ATTR_MINBACK_NAME);
      sb.append("='");
      sb.append(minBack);
      sb.append("'");
    }
    if (SOUND_ATTR_MINFRONT_REQD || !minFront.equals(minFrontDefault)) {

      sb.append(" ");
      sb.append(SOUND_ATTR_MINFRONT_NAME);
      sb.append("='");
      sb.append(minFront);
      sb.append("'");
    }
    if (SOUND_ATTR_PRIORITY_REQD || !priority.equals(priorityDefault)) {

      sb.append(" ");
      sb.append(SOUND_ATTR_PRIORITY_NAME);
      sb.append("='");
      sb.append(priority);
      sb.append("'");
    }
    if (SOUND_ATTR_SPATIALIZE_REQD || spatialize != spatializeDefault) {
      sb.append(" ");
      sb.append(SOUND_ATTR_SPATIALIZE_NAME);
      sb.append("='");
      sb.append(spatialize);
      sb.append("'");
    }
    return sb.toString();
  }

  public String getDirectionX()
  {
    return direction0.toString();
  }

  public void setDirectionX(String direction0)
  {
    this.direction0 = new SFFloat(direction0,null,null);
  }

  public String getDirectionY()
  {
    return direction1.toString();
  }

  public void setDirectionY(String direction1)
  {
    this.direction1 = new SFFloat(direction1,null,null);
  }

  public String getDirectionZ()
  {
    return direction2.toString();
  }

  public void setDirectionZ(String direction2)
  {
    this.direction2 = new SFFloat(direction2,null,null);
  }

  public String getIntensity()
  {
    return intensity.toString();
  }

  public void setIntensity(String intensity)
  {
    this.intensity = new SFFloat(intensity,0.0f,1.0f);
  }

  public String getLocationX()
  {
    return location0.toString();
  }

  public void setLocationX(String location0)
  {
    this.location0 = new SFFloat(location0,null,null);
  }

  public String getLocationY()
  {
    return location1.toString();
  }

  public void setLocationY(String location1)
  {
    this.location1 = new SFFloat(location1,null,null);
  }

  public String getLocationZ()
  {
    return location2.toString();
  }

  public void setLocationZ(String location2)
  {
    this.location2 = new SFFloat(location2,null,null);
  }

  public String getMaxBack()
  {
    return maxBack.toString();
  }

  public void setMaxBack(String maxBack)
  {
    this.maxBack = new SFFloat(maxBack,0.0f, null);
  }

  public String getMaxFront()
  {
    return maxFront.toString();
  }

  public void setMaxFront(String maxFront)
  {
    this.maxFront = new SFFloat(maxFront,0.0f, null);
  }

  public String getMinBack()
  {
    return minBack.toString();
  }

  public void setMinBack(String minBack)
  {
    this.minBack = new SFFloat(minBack,0.0f, null);
  }

  public String getMinFront()
  {
    return minFront.toString();
  }

  public void setMinFront(String minFront)
  {
    this.minFront = new SFFloat(minFront,0.0f, null);
  }

  public String getPriority()
  {
    return priority.toString();
  }

  public void setPriority(String priority)
  {
    this.priority = new SFFloat(priority,0.0f, 1.0f);
  }

  public boolean isSpatialize()
  {
    return spatialize;
  }

  public void setSpatialize(boolean spatialize)
  {
    this.spatialize = spatialize;
  }

    /**
     * @return the appendOutlineGeometry
     */
    public boolean isAppendOutlineGeometry() {
        return appendOutlineGeometry;
    }

    /**
     * @param appendOutlineGeometry the appendOutlineGeometry to set
     */
    public void setAppendOutlineGeometry(boolean appendOutlineGeometry) {
        this.appendOutlineGeometry = appendOutlineGeometry;
    }
}
