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
import org.web3d.x3d.types.X3DTextureCoordinateNode;

import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * TEXTURECOORDINATEGENERATOR.java
 * Created on Sep 10, 2007, 11:17 AM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class TEXTURECOORDINATEGENERATOR extends X3DTextureCoordinateNode
{
  private String mode;
  private String parameter;
  
  public TEXTURECOORDINATEGENERATOR()
  {
  }

  @Override
  public String getElementName()
  {
    return TEXTURECOORDINATEGENERATOR_ELNAME;
  }
  
  @Override
  public void initialize()
  {
    mode      = TEXTURECOORDINATEGENERATOR_ATTR_MODE_DFLT;
    parameter = TEXTURECOORDINATEGENERATOR_ATTR_PARAMETER_DFLT;
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;
    
    attr = root.getAttribute(TEXTURECOORDINATEGENERATOR_ATTR_MODE_NAME);
    if (attr != null)
      mode      = attr.getValue();
    attr = root.getAttribute(TEXTURECOORDINATEGENERATOR_ATTR_PARAMETER_NAME);
    if (attr != null)
      parameter = attr.getValue();
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return TEXTURECOORDINATEGENERATORCustomizer.class;
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (TEXTURECOORDINATEGENERATOR_ATTR_MODE_REQD || !mode.equals(TEXTURECOORDINATEGENERATOR_ATTR_MODE_DFLT)) {
      sb.append(" ");
      sb.append(TEXTURECOORDINATEGENERATOR_ATTR_MODE_NAME);
      sb.append("='");
      sb.append(mode);
      sb.append("'");
    }
    if (TEXTURECOORDINATEGENERATOR_ATTR_PARAMETER_REQD || !parameter.equals(TEXTURECOORDINATEGENERATOR_ATTR_PARAMETER_DFLT)) {
      sb.append(" ");
      sb.append(TEXTURECOORDINATEGENERATOR_ATTR_PARAMETER_NAME);
      sb.append("='");
      sb.append(parameter);
      sb.append("'");
    }
    return sb.toString();
  }

  public String getMode()
  {
    return mode;
  }

  public void setMode(String mode)
  {
    this.mode = mode;
  }

  public String getParameter()
  {
    return parameter;
  }

  public void setParameter(String parameter)
  {
    this.parameter = validateEvenNumbers(parameter);
  }
}
