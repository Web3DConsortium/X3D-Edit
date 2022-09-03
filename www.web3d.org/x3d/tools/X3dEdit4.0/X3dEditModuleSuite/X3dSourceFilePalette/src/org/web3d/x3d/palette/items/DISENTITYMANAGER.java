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

import org.web3d.x3d.types.X3DChildNode;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * DISENTITYMANAGER.java
 * Created on 15 May 2008
 * 
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 * 
 * @author Mike Bailey
 * @version $Id$
 */
public class DISENTITYMANAGER extends X3DChildNode
{ 
  private String address;
  private SFInt32 appID, appIDDefault;
  private SFInt32 port, portDefault;
  private SFInt32 siteID, siteIDDefault;

  public DISENTITYMANAGER()
  {
  }

  @Override
  public String getElementName()
  {
    return DISENTITYMANAGER_ELNAME;
  }
  
  @Override
  public void initialize()
  {
    address = DISENTITYMANAGER_ATTR_ADDRESS_DFLT;
    appID = appIDDefault = new SFInt32(DISENTITYMANAGER_ATTR_APPLICATIONID_DFLT,0,65535);
    port = portDefault = new SFInt32(DISENTITYMANAGER_ATTR_PORT_DFLT,0,65535);
    siteID = siteIDDefault = new SFInt32(DISENTITYMANAGER_ATTR_SITEID_DFLT,0,65535);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;
    attr = root.getAttribute(DISENTITYMANAGER_ATTR_ADDRESS_NAME);
    if(attr != null) {
      address = attr.getValue();
    }
    attr = root.getAttribute(DISENTITYMANAGER_ATTR_APPLICATIONID_NAME);
    if(attr != null) {
      appID = new SFInt32(attr.getValue());
    }
    attr = root.getAttribute(DISENTITYMANAGER_ATTR_PORT_NAME);
    if(attr != null) {
      port = new SFInt32(attr.getValue());
    }
    attr = root.getAttribute(DISENTITYMANAGER_ATTR_SITEID_NAME);
    if(attr != null) {
      siteID = new SFInt32(attr.getValue());
    }
  }

  @Override
  public String createAttributes()
  {
    StringBuffer sb = new StringBuffer();
    if(DISENTITYMANAGER_ATTR_ADDRESS_REQD || !address.equals(DISENTITYMANAGER_ATTR_ADDRESS_DFLT)) {
      sb.append(" ");
      sb.append(DISENTITYMANAGER_ATTR_ADDRESS_NAME);
      sb.append("='");
      sb.append(address);
      sb.append("'");
    }
    if(DISENTITYMANAGER_ATTR_APPLICATIONID_REQD || !appID.equals(appIDDefault)){
      sb.append(" ");
      sb.append(DISENTITYMANAGER_ATTR_APPLICATIONID_NAME);
      sb.append("='");
      sb.append(appID);
      sb.append("'");
    }
    if(DISENTITYMANAGER_ATTR_PORT_REQD || !port.equals(portDefault)){
      sb.append(" ");
      sb.append(DISENTITYMANAGER_ATTR_PORT_NAME);
      sb.append("='");
      sb.append(port);
      sb.append("'");
    }
    if(DISENTITYMANAGER_ATTR_SITEID_REQD || !siteID.equals(siteIDDefault)){
      sb.append(" ");
      sb.append(DISENTITYMANAGER_ATTR_SITEID_NAME);
      sb.append("='");
      sb.append(siteID);
      sb.append("'");
    }

    return sb.toString();
  }

  public String getAddress()
  {
    return address;
  }

  public void setAddress(String address)
  {
    this.address = address;
  }

  public String getAppID()
  {
    return appID.toString();
  }

  public void setAppID(String s)
  {
    appID = new SFInt32(s,0,65535);
  }

  public String getPort()
  {
    return port.toString();
  }

  public void setPort(String s)
  {
    port = new SFInt32(s,0,65535);
  }

  public String getSiteID()
  {
    return siteID.toString();
  }

  public void setSiteID(String s)
  {
    siteID = new SFInt32(s,0,65535);
  }
}