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
import org.web3d.x3d.types.X3DGroupingNode;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFFloat;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFInt32;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * ESPDUTRANSFORM.java
 * Created on 15 May 2008
 * 
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 * 
 * @author Mike Bailey
 * @version $Id$
 */
public abstract class CommonPdu extends X3DGroupingNode
{ 
  protected String  address;
  protected SFInt32   appID, appIDDefault;
  protected boolean enabled, enabledDefault;
  protected SFInt32   entityID, entityIDDefault;
  protected String  multicastRelayHost;
  protected SFInt32   multicastRelayPort, multicastRelayPortDefault;
  protected String  networkMode;
  protected SFInt32   port, portDefault;
  protected SFInt32   radioID, radioIDDefault;
  protected SFFloat readInterval, readIntervalDefault; // SFTime
  protected boolean rtpHeaderExpected, rtpHeaderExpecteDefault;
  protected SFInt32   siteID, siteIDDefault;
  protected SFInt32   whichGeometry, whichGeometryDefault;
  protected SFFloat writeInterval, writeIntervalDefault; // SFTime
  public CommonPdu()
  {
  }
  
  @Override
  public void initialize()
  {
    address                                         = PDU_ATTR_ADDRESS_DFLT;	
    appID = appIDDefault                            = new SFInt32(PDU_ATTR_APPLICATIONID_DFLT,0,65535);    
    enabled = enabledDefault                        = Boolean.parseBoolean(PDU_ATTR_ENABLED_DFLT);
    entityID = entityIDDefault                      = new SFInt32(PDU_ATTR_ENTITYID_DFLT,0,65535);
    multicastRelayHost                              = PDU_ATTR_MULTICASTRELAYHOST_DFLT;
    multicastRelayPort = multicastRelayPortDefault  = new SFInt32(PDU_ATTR_MULTICASTRELAYPORT_DFLT,0,null);
    networkMode                                     = PDU_ATTR_NETWORKMODE_DFLT;
    port = portDefault                              = new SFInt32(PDU_ATTR_PORT_DFLT,0,65535);
    radioID = radioIDDefault                        = new SFInt32(PDU_ATTR_RADIOID_DFLT,0,65535);
    readInterval = readIntervalDefault              = new SFFloat(PDU_ATTR_READINTERVAL_DFLT,0f,null);
    rtpHeaderExpected = rtpHeaderExpecteDefault     = Boolean.parseBoolean(PDU_ATTR_RTPHEADEREXPECTED_DFLT);
    siteID = siteIDDefault                          = new SFInt32(PDU_ATTR_SITEID_DFLT,0,65535);
    whichGeometry = whichGeometryDefault            = new SFInt32(PDU_ATTR_WHICHGEOMETRY_DFLT,-1,null);
    writeInterval = writeIntervalDefault            = new SFFloat(PDU_ATTR_WRITEINTERVAL_DFLT,0f,null);

    String[] fa = parse3(PDU_ATTR_BBOXCENTER_DFLT);
    bboxCenterX   = bboxCenterXDefault              = new SFFloat(fa[0],null,null);
    bboxCenterY   = bboxCenterYDefault              = new SFFloat(fa[1],null,null);
    bboxCenterZ   = bboxCenterZDefault              = new SFFloat(fa[2],null,null);
    fa = parse3(PDU_ATTR_BBOXSIZE_DFLT);
    bboxSizeX     = bboxSizeXDefault                = new SFFloat(fa[0],0f,null,true);
    bboxSizeY     = bboxSizeYDefault                = new SFFloat(fa[1],0f,null,true);
    bboxSizeZ     = bboxSizeZDefault                = new SFFloat(fa[2],0f,null,true);    
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;
    String[] sa;

    attr = root.getAttribute(PDU_ATTR_ADDRESS_NAME);
    if (attr != null)
      address = attr.getValue();
    attr = root.getAttribute(PDU_ATTR_APPLICATIONID_NAME);
    if (attr != null)
      appID = new SFInt32(attr.getValue());
 
    attr = root.getAttribute(PDU_ATTR_ENABLED_NAME);
    if (attr != null)
      enabled = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(PDU_ATTR_ENTITYID_NAME);
    if (attr != null)
      entityID = new SFInt32(attr.getValue());
    attr = root.getAttribute(PDU_ATTR_MULTICASTRELAYHOST_NAME);
    if (attr != null)
      multicastRelayHost = attr.getValue();
    attr = root.getAttribute(PDU_ATTR_MULTICASTRELAYPORT_NAME);
    if (attr != null)
      multicastRelayPort = new SFInt32(attr.getValue());
    attr = root.getAttribute(PDU_ATTR_NETWORKMODE_NAME);
    if (attr != null)
      networkMode = attr.getValue();
    attr = root.getAttribute(PDU_ATTR_PORT_NAME);
    if (attr != null)
      port = new SFInt32(attr.getValue());
    attr = root.getAttribute(PDU_ATTR_RADIOID_NAME);
    if(attr != null)
      radioID = new SFInt32(attr.getValue());
    attr = root.getAttribute(PDU_ATTR_READINTERVAL_NAME);
    if (attr != null)
      readInterval = new SFFloat(attr.getValue());
    attr = root.getAttribute(PDU_ATTR_RTPHEADEREXPECTED_NAME);
    if (attr != null)
      rtpHeaderExpected = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(PDU_ATTR_SITEID_NAME);
    if (attr != null)
      siteID = new SFInt32(attr.getValue());
    attr = root.getAttribute(PDU_ATTR_WHICHGEOMETRY_NAME);
    if(attr != null)
      whichGeometry = new SFInt32(attr.getValue());
    attr = root.getAttribute(PDU_ATTR_WRITEINTERVAL_NAME);
    if (attr != null)
      writeInterval = new SFFloat(attr.getValue());
    attr = root.getAttribute(PDU_ATTR_BBOXCENTER_NAME);
    if (attr != null) {
      sa = parse3(attr.getValue());
      bboxCenterX = new SFFloat(sa[0]);
      bboxCenterY = new SFFloat(sa[1]);
      bboxCenterZ = new SFFloat(sa[2]);
    }
    attr = root.getAttribute(PDU_ATTR_BBOXSIZE_NAME);
    if (attr != null) {
      sa = parse3(attr.getValue());
      bboxSizeX = new SFFloat(sa[0]);
      bboxSizeY = new SFFloat(sa[1]);
      bboxSizeZ = new SFFloat(sa[2]);
    }
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    int sblen=0;
    String newLineString = "\n               ";  //15 spaces since the word ESPDUTransform is at least that long
     
    if(ESPDUTRANSFORM_ATTR_ADDRESS_REQD || !address.equals(ESPDUTRANSFORM_ATTR_ADDRESS_DFLT)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_ADDRESS_NAME);
      sb.append("='");
      sb.append(address);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_APPLICATIONID_REQD || !appID.equals(appIDDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_APPLICATIONID_NAME);
      sb.append("='");
      sb.append(appID);
      sb.append("'");
    }
     
    if(ESPDUTRANSFORM_ATTR_ENABLED_REQD || (enabled != enabledDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }     
    if(ESPDUTRANSFORM_ATTR_ENTITYID_REQD || !entityID.equals(entityIDDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_ENTITYID_NAME);
      sb.append("='");
      sb.append(entityID);
      sb.append("'");
    }
     
    if(sb.length() > sblen) {sb.append(newLineString);sblen=sb.length();}
     
     if(ESPDUTRANSFORM_ATTR_MULTICASTRELAYHOST_REQD || !multicastRelayHost.equals(ESPDUTRANSFORM_ATTR_MULTICASTRELAYHOST_DFLT)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_MULTICASTRELAYHOST_NAME);
      sb.append("='");
      sb.append(multicastRelayHost);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_MULTICASTRELAYPORT_REQD || !multicastRelayPort.equals(multicastRelayPortDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_MULTICASTRELAYPORT_NAME);
      sb.append("='");
      sb.append(multicastRelayPort);
      sb.append("'");
    }
     
    if(sb.length() > sblen) {sb.append(newLineString);sblen=sb.length();}
     
    if(ESPDUTRANSFORM_ATTR_NETWORKMODE_REQD || !networkMode.equals(ESPDUTRANSFORM_ATTR_NETWORKMODE_DFLT)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_NETWORKMODE_NAME);
      sb.append("='");
      sb.append(networkMode);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_PORT_REQD || !port.equals(portDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_PORT_NAME);
      sb.append("='");
      sb.append(port);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_READINTERVAL_REQD || !readInterval.equals(readIntervalDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_READINTERVAL_NAME);
      sb.append("='");
      sb.append(readInterval);
      sb.append("'");
    }     
    if(ESPDUTRANSFORM_ATTR_RTPHEADEREXPECTED_REQD || (rtpHeaderExpected != rtpHeaderExpecteDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_RTPHEADEREXPECTED_NAME);
      sb.append("='");
      sb.append(rtpHeaderExpected);
      sb.append("'");
    }
     
    if(sb.length() > sblen) {sb.append(newLineString);sblen=sb.length();}
          
    if(ESPDUTRANSFORM_ATTR_SITEID_REQD || !siteID.equals(siteIDDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_SITEID_NAME);
      sb.append("='");
      sb.append(siteID);
      sb.append("'");
    }
    if(PDU_ATTR_WHICHGEOMETRY_REQD || !whichGeometry.equals(whichGeometryDefault)) {
      sb.append(" ");
      sb.append(PDU_ATTR_WHICHGEOMETRY_NAME);
      sb.append("='");
      sb.append(whichGeometry);
      sb.append("'");      
    }
    if(ESPDUTRANSFORM_ATTR_WRITEINTERVAL_REQD || !writeInterval.equals(writeIntervalDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_WRITEINTERVAL_NAME);
      sb.append("='");
      sb.append(writeInterval);
      sb.append("'");
    }
     
    if(ESPDUTRANSFORM_ATTR_BBOXCENTER_REQD || 
       (!bboxCenterX.equals(bboxCenterXDefault) ||
        !bboxCenterY.equals(bboxCenterYDefault) ||
        !bboxCenterZ.equals(bboxCenterZDefault))) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_BBOXCENTER_NAME);
      sb.append("='");
      sb.append(bboxCenterX);
      sb.append(" ");
      sb.append(bboxCenterY);
      sb.append(" ");
      sb.append(bboxCenterZ);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_BBOXSIZE_REQD || 
       (!bboxSizeX.equals(bboxSizeXDefault) ||
        !bboxSizeY.equals(bboxSizeYDefault) ||
        !bboxSizeZ.equals(bboxSizeZDefault))) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_BBOXSIZE_NAME);
      sb.append("='");
      sb.append(bboxSizeX);
      sb.append(" ");
      sb.append(bboxSizeX);
      sb.append(" ");
      sb.append(bboxSizeX);
      sb.append("'");
    }
    if(sb.length() > sblen) 
    {
        sb.append(newLineString);
    }
                
    return sb.toString();
  }

  public String getAddress()
  {
    return address;
  }

  public void setAddress(String s)
  {
    address = s;
  }

  public String getAppID()
  {
    return appID.toString();
  }

  public void setAppID(String s)
  {
    appID = new SFInt32(s, 0, 65535);
  }

  public String isEnabled()
  {
    return ""+enabled;
  }

  public void setEnabled(String s)
  {
    enabled = Boolean.parseBoolean(s);
  }

  public String getEntityID()
  {
    return entityID.toString();
  }

  public void setEntityID(String s)
  {
    entityID = new SFInt32(s,0,65535);
  }

  public String getMulticastRelayHost()
  {
    return multicastRelayHost;
  }

  public void setMulticastRelayHost(String s)
  {
    this.multicastRelayHost = s;
  }

  public String getMulticastRelayPort()
  {
    return multicastRelayPort.toString();
  }

  public void setMulticastRelayPort(String s)
  {
    multicastRelayPort = new SFInt32(s,0,null);
  }

  public String getNetworkMode()
  {
    return networkMode.toString();
  }

  public void setNetworkMode(String s)
  {
    networkMode = s;
  }

  public String getPort()
  {
    return port.toString();
  }

  public void setPort(String s)
  {
    port = new SFInt32(s,0,65535);
  }

  public String getRadioID()
  {
    return radioID.toString();
  }
  
  public void setRadioID(String s)
  {
    radioID = new SFInt32(s,0,65535);
  }
  
  public String getReadInterval()
  {
    return readInterval.toString();
  }

  public void setReadInterval(String s)
  {
    readInterval = new SFFloat(s,0f,null);
  }

  public String isRtpHeaderExpected()
  {
    return ""+rtpHeaderExpected;
  }

  public void setRtpHeaderExpected(String s)
  {
    rtpHeaderExpected = Boolean.parseBoolean(s);
  }

  public String getSiteID()
  {
    return siteID.toString();
  }

  public void setSiteID(String s)
  {
    siteID = new SFInt32(s,0,65535);
  }

  public String getWhichGeometry()
  {
    return whichGeometry.toString();
  }
  
  public void setWhichGeometry(String s)
  {
    whichGeometry = new SFInt32(s,-1,null);  
  }
  
  public String getWriteInterval()
  {
    return writeInterval.toString();
  }

  public void setWriteInterval(String s)
  {
    writeInterval = new SFFloat(s,0f,null);
  }      
}