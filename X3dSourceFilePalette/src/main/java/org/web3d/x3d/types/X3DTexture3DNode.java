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

/**
 * X3DTexture3DNode.java
 * "Marker" interface for ComposedTexture3D, ImageTexture3D, PixelTexture3D
 * Created on 20 November 2011
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public abstract class X3DTexture3DNode extends X3DTextureNode
{
  protected boolean repeatS,   repeatT,   repeatR;

  public boolean isRepeatS()
  {
    return repeatS;
  }

  public void setRepeatS(boolean b)
  {
    this.repeatS = b;
  }
  
  public boolean isRepeatT()
  {
    return repeatT;
  }

  public void setRepeatT(boolean b)
  {
    this.repeatT = b;
  }
  
  public boolean isRepeatR()
  {
    return repeatR;
  }

  public void setRepeatR(boolean b)
  {
    this.repeatR = b;
  }
}