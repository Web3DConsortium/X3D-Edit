/*
Copyright (c) 1995-2025 held by the author(s).  All rights reserved.
 
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

import org.web3d.x3d.types.X3DPrimitiveTypes.SFDouble;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFFloat;

/**
 * X3DSoundProcessingNode.java
 * 
 * Base node type for all sound processing nodes, which are used to enhance audio with filtering, delaying, changing gain, etc.
 *
 * @author Don Brutzman
 * @version $Id$
 */
public abstract class X3DSoundProcessingNode extends X3DTimeDependentNode 
{
  protected String   channelCountMode, channelCountModeDefault;
  protected String   channelInterpretation, channelInterpretationDefault;
  protected SFFloat  gain, gainDefault;

  protected SFDouble tailTime, tailTimeDefault;
  
  public String getChannelCountMode()
  {
    return channelCountMode;
  }

  public void setChannelCountMode(String newChannelCountMode)
  {
    channelCountMode = newChannelCountMode;
  }
    
  public String getChannelInterpretation()
  {
    return channelInterpretation;
  }

  public void setChannelInterpretation(String newChannelInterpretation)
  {
    channelInterpretation = newChannelInterpretation;
  }
  
  public String getGain()
  {
    return gain.toString();
  }

  public void setGain(String newGain)
  {
    gain = new SFFloat(newGain,null,null);
  }
  public String getTailTime()
  {
    return tailTime.toString();
  }

  public void setTailTime(String newTailTime)
  {
    tailTime = new SFDouble(newTailTime,null,null);
  }
  
  @Override
  public String getDefaultContainerField()
  {
    return "source";
  }
}
