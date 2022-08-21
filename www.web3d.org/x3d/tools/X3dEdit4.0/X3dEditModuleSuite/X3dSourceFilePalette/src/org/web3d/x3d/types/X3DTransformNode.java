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

package org.web3d.x3d.types;

import javax.swing.text.JTextComponent;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * X3DGroupingNode.java
 * Created on August 2, 2007, 10:05 AM
 *
 * Marker interface for anchor, billboard, collision, group, lod, staticgroup, switch, transform, espdutransform, receiverpdu, signalpdu, transmitterpdu,
 *     GeoLocation, geolod, hanimjoint, hanimsegment, hanimsite, cadassembly, cadlayer, cadpart, 
 * 
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public abstract class X3DTransformNode extends X3DGroupingNode
{
    // TODO had to use public vice protected access due to PduHelper

  public SFFloat centerX, centerXDefault;
  public SFFloat centerY, centerYDefault;
  public SFFloat centerZ, centerZDefault;

  public SFFloat translationX, translationXDefault;
  public SFFloat translationY, translationYDefault;
  public SFFloat translationZ, translationZDefault;

  // Together make one SFRotation
  public SFFloat rotationX, rotationXDefault;
  public SFFloat rotationY, rotationYDefault;
  public SFFloat rotationZ, rotationZDefault;
  public SFFloat rotationAngle, rotationAngleDefault;
  
  public SFFloat scaleX, scaleXDefault;
  public SFFloat scaleY, scaleYDefault;
  public SFFloat scaleZ, scaleZDefault;

  public SFFloat scaleOrientationX, scaleOrientation0Default;
  public SFFloat scaleOrientationY, scaleOrientation1Default;
  public SFFloat scaleOrientationZ, scaleOrientation2Default;
  public SFFloat scaleOrientationAngle, scaleOrientation3Default;

  public String getCenterX()
  {
    return centerX.toString();
  }

  public void setCenterX(String center0)
  {
    this.centerX = new SFFloat(center0, null, null);
  }

  public String getCenterY()
  {
    return centerY.toString();
  }

  public void setCenterY(String center1)
  {
    this.centerY = new SFFloat(center1, null, null);
  }

  public String getCenterZ()
  {
    return centerZ.toString();
  }

  public void setCenterZ(String center2)
  {
    this.centerZ = new SFFloat(center2, null, null);
  }

  public String getTranslationX()
  {
    return translationX.toString();
  }

  public void setTranslationX(String translation0)
  {
    this.translationX = new SFFloat(translation0, null, null);
  }

  public String getTranslationY()
  {
    return translationY.toString();
  }

  public void setTranslationY(String translation1)
  {
    this.translationY = new SFFloat(translation1, null, null);
  }

  public String getTranslationZ()
  {
    return translationZ.toString();
  }

  public void setTranslationZ(String translation2)
  {
    this.translationZ = new SFFloat(translation2, null, null);
  }

  public String getRotationX()
  {
    return rotationX.toString();
  }

  public void setRotationX(String rotationX)
  {
    this.rotationX = new SFFloat(rotationX, null, null);
  }

  public String getRotationY()
  {
    return rotationY.toString();
  }

  public void setRotationY(String rotationY)
  {
    this.rotationY = new SFFloat(rotationY, null, null);
  }

  public String getRotationZ()
  {
    return rotationZ.toString();
  }

  public void setRotationZ(String rotationZ)
  {
    this.rotationZ = new SFFloat(rotationZ, null, null);
  }

  public String getRotationAngle()
  {
    return radiansFormat.format(rotationAngle.getValue());
  }

  public void setRotationAngle(String rotationAngle)
  {
    this.rotationAngle = new SFFloat(rotationAngle, null, null);
  }

  public String getScaleX()
  {
    return scaleX.toString();
  }

  public void setScaleX(String scale0)
  {
    this.scaleX = new SFFloat(scale0, null, null);
  }

  public String getScaleY()
  {
    return scaleY.toString();
  }

  public void setScaleY(String scale1)
  {
    this.scaleY = new SFFloat(scale1, null, null);
  }

  public String getScaleZ()
  {
    return scaleZ.toString();
  }

  public void setScaleZ(String scale2)
  {
    this.scaleZ = new SFFloat(scale2, null, null);
  }

  public String getScaleOrientationX()
  {
    return scaleOrientationX.toString();
  }

  public void setScaleOrientationX(String scaleOrientationX)
  {
    this.scaleOrientationX = new SFFloat(scaleOrientationX, null, null);
  }

  public String getScaleOrientationY()
  {
    return scaleOrientationY.toString();
  }

  public void setScaleOrientationY(String scaleOrientationY)
  {
    this.scaleOrientationY = new SFFloat(scaleOrientationY, null, null);
  }

  public String getScaleOrientationZ()
  {
    return scaleOrientationZ.toString();
  }

  public void setScaleOrientationZ(String scaleOrientationZ)
  {
    this.scaleOrientationZ = new SFFloat(scaleOrientationZ, null, null);
  }

  public String getScaleOrientationAngle()
  {
    return radiansFormat.format(scaleOrientationAngle.getValue());
  }

  public void setScaleOrientationAngle(String scaleOrientationAngle)
  {
    this.scaleOrientationAngle = new SFFloat(scaleOrientationAngle, null, null);
  }
}
