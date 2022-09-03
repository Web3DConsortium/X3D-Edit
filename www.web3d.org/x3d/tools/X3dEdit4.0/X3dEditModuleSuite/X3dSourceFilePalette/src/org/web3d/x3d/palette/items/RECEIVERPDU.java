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

import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * RECEIVERPDU.java
 * Created on 20 May 2008
 * 
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 * 
 * @author Mike Bailey
 * @version $Id$
 */
public class RECEIVERPDU extends CommonPdu
{ 
  private SFFloat receivedPower, receivedPowerDefault;
  private SFInt32   receiverState, receiverStateDefault;
  private SFInt32   transmitterAppID, transmitterAppIDDefault;
  private SFInt32   transmitterEntID, transmitterEntIDDefault;
  private SFInt32   transmitterRadioID, transmitterRadioIDDefault;
  private SFInt32   transmitterSiteID, transmitterSiteIDDefault;

  public RECEIVERPDU()
  {
  }

  @Override
  public String getElementName()
  {
    return RECEIVERPDU_ELNAME;
  }
  
  @Override
  public void initialize()
  {
    super.initialize();
    
    receivedPower      = receivedPowerDefault      = new SFFloat(RECEIVERPDU_ATTR_RECEIVEDPOWER_DFLT,0f,null);
    receiverState      = receiverStateDefault      = new SFInt32 (RECEIVERPDU_ATTR_RECEIVERSTATE_DFLT,0,65535);
    transmitterAppID   = transmitterAppIDDefault   = new SFInt32 (RECEIVERPDU_ATTR_TRANSMITTERAPPLICATIONID_DFLT,0,65535);
    transmitterEntID   = transmitterEntIDDefault   = new SFInt32 (RECEIVERPDU_ATTR_TRANSMITTERENTITYID_DFLT,0,65535);
    transmitterRadioID = transmitterRadioIDDefault = new SFInt32 (RECEIVERPDU_ATTR_TRANSMITTERRADIOID_DFLT,0,65535);
    transmitterSiteID  = transmitterSiteIDDefault  = new SFInt32 (RECEIVERPDU_ATTR_TRANSMITTERSITEID_DFLT,0,65535);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(RECEIVERPDU_ATTR_RECEIVEDPOWER_NAME);
    if(attr != null)
      receivedPower = new SFFloat(attr.getValue());
    attr = root.getAttribute(RECEIVERPDU_ATTR_RECEIVERSTATE_NAME);
    if(attr != null)
      receiverState = new SFInt32(attr.getValue());
    attr = root.getAttribute(RECEIVERPDU_ATTR_TRANSMITTERAPPLICATIONID_NAME);
    if(attr != null)
      transmitterAppID = new SFInt32(attr.getValue());
    attr = root.getAttribute(RECEIVERPDU_ATTR_TRANSMITTERENTITYID_NAME);
    if(attr != null)
      transmitterEntID = new SFInt32(attr.getValue());
    attr = root.getAttribute(RECEIVERPDU_ATTR_TRANSMITTERRADIOID_NAME);
    if(attr != null)
      transmitterRadioID = new SFInt32(attr.getValue());
    attr = root.getAttribute(RECEIVERPDU_ATTR_TRANSMITTERSITEID_NAME);
    if(attr != null)
      transmitterSiteID = new SFInt32(attr.getValue());
  }

  @Override
  public String createAttributes()
  {
    StringBuffer sb = new StringBuffer();
    sb.append(super.createAttributes());
    
    if(RECEIVERPDU_ATTR_RECEIVEDPOWER_REQD || !receivedPower.equals(receivedPowerDefault)) {
      sb.append(" ");
      sb.append(RECEIVERPDU_ATTR_RECEIVEDPOWER_NAME);
      sb.append("='");
      sb.append(receivedPower);
      sb.append("'");      
    }
    if(RECEIVERPDU_ATTR_RECEIVERSTATE_REQD || !receiverState.equals(receiverStateDefault)) {
      sb.append(" ");
      sb.append(RECEIVERPDU_ATTR_RECEIVERSTATE_NAME);
      sb.append("='");
      sb.append(receiverState);
      sb.append("'");       
    }
    if(RECEIVERPDU_ATTR_TRANSMITTERAPPLICATIONID_REQD || !transmitterAppID.equals(transmitterAppIDDefault)) {
      sb.append(" ");
      sb.append(RECEIVERPDU_ATTR_TRANSMITTERAPPLICATIONID_NAME);
      sb.append("='");
      sb.append(transmitterAppID);
      sb.append("'");     
    }
    if(RECEIVERPDU_ATTR_TRANSMITTERENTITYID_REQD || !transmitterEntID.equals(transmitterEntIDDefault)) {
      sb.append(" ");
      sb.append(RECEIVERPDU_ATTR_TRANSMITTERENTITYID_NAME);
      sb.append("='");
      sb.append(transmitterEntID);
      sb.append("'");      
    }
    if(RECEIVERPDU_ATTR_TRANSMITTERRADIOID_REQD || !transmitterRadioID.equals(transmitterRadioIDDefault)) {
      sb.append(" ");
      sb.append(RECEIVERPDU_ATTR_TRANSMITTERRADIOID_NAME);
      sb.append("='");
      sb.append(transmitterRadioID);
      sb.append("'");      
    }
    if(RECEIVERPDU_ATTR_TRANSMITTERSITEID_REQD || !transmitterSiteID.equals(transmitterSiteIDDefault)) {
      sb.append(" ");
      sb.append(RECEIVERPDU_ATTR_TRANSMITTERSITEID_NAME);
      sb.append("='");
      sb.append(transmitterSiteID);
      sb.append("'");      
    }

    return sb.toString();
  }

  public String getReceivedPower()
  {
    return receivedPower.toString();
  }

  public void setReceivedPower(String s)
  {
    this.receivedPower = new SFFloat(s,0f,null);
  }

  public String getReceiverState()
  {
    return receiverState.toString();
  }

  public void setReceiverState(String s)
  {
    this.receiverState = new SFInt32(s,0,65535);
  }

  public String getTransmitterAppID()
  {
    return transmitterAppID.toString();
  }

  public void setTransmitterAppID(String s)
  {
    this.transmitterAppID = new SFInt32(s,0,65535);
  }

  public String getTransmitterEntID()
  {
    return transmitterEntID.toString();
  }

  public void setTransmitterEntID(String s)
  {
    this.transmitterEntID = new SFInt32(s,0,65535);
  }

  public String getTransmitterRadioID()
  {
    return transmitterRadioID.toString();
  }

  public void setTransmitterRadioID(String s)
  {
    this.transmitterRadioID = new SFInt32(s,0,65535);
  }

  public String getTransmitterSiteID()
  {
    return transmitterSiteID.toString();
  }

  public void setTransmitterSiteID(String s)
  {
    this.transmitterSiteID = new SFInt32(s,0,65535);
  }
  
}