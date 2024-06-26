/*
Copyright (c) 1995-2022 held by the author(s).  All rights reserved.
 
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

package org.web3d.x3d.types;

import org.web3d.x3d.types.X3DPrimitiveTypes.SFFloat;

/**
 * X3DGroupingNode.java
 * Created on August 2, 2007, 10:05 AM
 *
 * Shared interface for anchor, billboard, collision, group, lod, staticgroup, switch, transform, espdutransform, receiverpdu, signalpdu, transmitterpdu,
 *     GeoLocation, geolod, hanimjoint, hanimsegment, hanimsite, cadassembly, cadlayer, cadpart, 
 * 
 * Also used by NurbsSet as a convenience interface
 * 
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey and Don Brutzman
 * @version $Id$
 */
public abstract class X3DGroupingNode extends X3DChildNode
{
  // Together make one SFVec3F
  protected SFFloat bboxCenterX, bboxCenterXDefault;
  protected SFFloat bboxCenterY, bboxCenterYDefault;
  protected SFFloat bboxCenterZ, bboxCenterZDefault;

  // Together make one SFVec3F
  protected SFFloat bboxSizeX,   bboxSizeXDefault;
  protected SFFloat bboxSizeY,   bboxSizeYDefault;
  protected SFFloat bboxSizeZ,   bboxSizeZDefault;

  public String getBboxCenterX()
  {
    return bboxCenterX.toString();
  }

  public void setBboxCenterX(String bboxCenterX)
  {
    this.bboxCenterX = new SFFloat(bboxCenterX, null, null);
  }

  public String getBboxCenterY()
  {
    return bboxCenterY.toString();
  }

  public void setBboxCenterY(String bboxCenterY)
  {
    this.bboxCenterY = new SFFloat(bboxCenterY, null, null);
  }

  public String getBboxCenterZ()
  {
    return bboxCenterZ.toString();
  }

  public void setBboxCenterZ(String bboxCenterZ)
  {
    this.bboxCenterZ = new SFFloat(bboxCenterZ, null, null);
  }

  public String getBboxSizeX()
  {
    return bboxSizeX.toString();
  }

  public void setBboxSizeX(String bboxSizeX)
  {
    this.bboxSizeX = new SFFloat(bboxSizeX, -1.0f, null);
  }

  public String getBboxSizeY()
  {
    return bboxSizeY.toString();
  }

  public void setBboxSizeY(String bboxSizeY)
  {
    this.bboxSizeY = new SFFloat(bboxSizeY, -1.0f, null);
  }

  public String getBboxSizeZ()
  {
    return bboxSizeZ.toString();
  }

  public void setBboxSizeZ(String bboxSizeZ)
  {
    this.bboxSizeZ = new SFFloat(bboxSizeZ, -1.0f, null);
  }
}
