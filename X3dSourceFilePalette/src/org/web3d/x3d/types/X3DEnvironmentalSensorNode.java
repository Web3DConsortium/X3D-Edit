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

import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * X3DEnvironmentalSensor.java
 * 
 *  marker interface for EnvironmentalSensor Nodes, KeySensorNodes, NetworkSensorNodes, PointingDeviceSensorNodes
 * 
 * Created on Sep 26, 2007, 4:00 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public abstract class X3DEnvironmentalSensorNode extends X3DSensorNode
{
  protected SFFloat centerX, centerXDefault;
  protected SFFloat centerY, centerYDefault;
  protected SFFloat centerZ, centerZDefault;
  protected SFFloat sizeX, sizeXDefault;
  protected SFFloat sizeY, sizeYDefault;
  protected SFFloat sizeZ, sizeZDefault;

  public String getCenterX()
  {
    return centerX.toString();
  }

  public void setCenterX(String centerX)
  {
    this.centerX = new SFFloat(centerX,null,null);
  }

  public String getCenterY()
  {
    return centerY.toString();
  }

  public void setCenterY(String centerY)
  {
    this.centerY = new SFFloat(centerY,null,null);
  }

  public String getCenterZ()
  {
    return centerZ.toString();
  }

  public void setCenterZ(String centerZ)
  {
    this.centerZ = new SFFloat(centerZ,null,null);
  }

  public String getSizeX()
  {
    return sizeX.toString();
  }

  public void setSizeX(String sizeX)
  {
    this.sizeX = new SFFloat(sizeX,0.0f,null);
  }

  public String getSizeY()
  {
    return sizeY.toString();
  }

  public void setSizeY(String sizeY)
  {
    this.sizeY = new SFFloat(sizeY,0.0f,null);
  }

  public String getSizeZ()
  {
    return sizeZ.toString();
  }

  public void setSizeZ(String sizeZ)
  {
    this.sizeZ = new SFFloat(sizeZ,0.0f,null);
  }
}
