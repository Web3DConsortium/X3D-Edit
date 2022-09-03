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
import org.web3d.x3d.types.X3DNetworkSensorNode;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * LOADSENSOR.java
 * Created on Sep 12, 2007, 2:46 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class LOADSENSOR extends X3DNetworkSensorNode
{
  private SFFloat timeOut, timeOutDefault;
  private String watchList;

  public LOADSENSOR()
  {
  }

  @Override
  public String getDefaultContainerField()
  {
    return "children";
  }
  
  @Override
  public String getElementName()
  {
    return LOADSENSOR_ELNAME;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return LOADSENSORCustomizer.class;
  }

  @Override
  public void initialize()
  {
    enabled   = enabledDefault = Boolean.parseBoolean(LOADSENSOR_ATTR_ENABLED_DFLT);
    timeOut   = timeOutDefault = new SFFloat(LOADSENSOR_ATTR_TIMEOUT_DFLT,0.0f, null);

    setContent("\n\t\t<!--TODO add AudioClip|ImageTexture|Inline|MovieTexture|" +
               "(X3D version 3.1 or greater)ImageCubeMapTexture|ImageTexture3D|PackagedShader|ShaderPart|ShaderProgram nodes here-->\n\t");
 }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(LOADSENSOR_ATTR_ENABLED_NAME);
    if (attr != null)
      enabled   = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(LOADSENSOR_ATTR_TIMEOUT_NAME);
    if (attr != null)
      timeOut   =  new SFFloat(attr.getValue(),0.0f, null);
  }

  @Override
  public String createAttributes()
  {
    StringBuffer sb = new StringBuffer();
    if (LOADSENSOR_ATTR_ENABLED_REQD || enabled != enabledDefault) {
      sb.append(" ");
      sb.append(LOADSENSOR_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }
    if (LOADSENSOR_ATTR_TIMEOUT_REQD || !timeOut.equals(timeOutDefault)) {
      sb.append(" ");
      sb.append(LOADSENSOR_ATTR_TIMEOUT_NAME);
      sb.append("='");
      sb.append(timeOut);
      sb.append("'");
    }
    return sb.toString();
  }

  public String getTimeOut()
  {
    return timeOut.toString();
  }

  public void setTimeOut(String timeOut)
  {
    this.timeOut = new SFFloat(timeOut,0.0f,null);
  }
}
