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

import java.util.Arrays;
import org.web3d.x3d.types.X3DComposableVolumeRenderStyleNode;
import javax.swing.text.JTextComponent;

import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * TONEMAPPEDVOLUMESTYLE.java
 * Created on 15 November 2011
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey and Don Brutzman
 * @version $Id$
 */
public class TONEMAPPEDVOLUMESTYLE extends X3DComposableVolumeRenderStyleNode
{
  private SFFloat[] coolColor, coolColorDefault = new SFFloat[4];
  private SFFloat[] warmColor, warmColorDefault = new SFFloat[4];
  
  private final int RED   = 0;
  private final int GREEN = 1;
  private final int BLUE  = 2;
  private final int ALPHA = 3;  
  
  public TONEMAPPEDVOLUMESTYLE()
  {
  }

  @Override
  public String getElementName()
  {
    return TONEMAPPEDVOLUMESTYLE_ELNAME;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return TONEMAPPEDVOLUMESTYLECustomizer.class;
  }

  @Override
  @SuppressWarnings("NestedAssignment")
  public void initialize()
  {
    enabled = enabledDefault = Boolean.parseBoolean(TONEMAPPEDVOLUMESTYLE_ATTR_ENABLED_DFLT);

    String[] sa;
    if(TONEMAPPEDVOLUMESTYLE_ATTR_COOLCOLOR_DFLT == null || TONEMAPPEDVOLUMESTYLE_ATTR_COOLCOLOR_DFLT.length()<=0)
      sa = new String[]{"0.8", "0.8", "0.8", "1.0"}; // default 
    else
      sa = parseX(TONEMAPPEDVOLUMESTYLE_ATTR_COOLCOLOR_DFLT);
    coolColor = coolColorDefault = parseToSFFloatArray(sa);
    
    if(TONEMAPPEDVOLUMESTYLE_ATTR_WARMCOLOR_DFLT == null || TONEMAPPEDVOLUMESTYLE_ATTR_WARMCOLOR_DFLT.length()<=0)
      sa = new String[]{}; // empty 
    else
      sa = parseX(TONEMAPPEDVOLUMESTYLE_ATTR_WARMCOLOR_DFLT);
      warmColor =   warmColorDefault = parseToSFFloatArray(sa); 
    
    setContent("\n\t\t<!--TODO add surfaceNormals X3DTexture3DNode here-->\n\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(TONEMAPPEDVOLUMESTYLE_ATTR_ENABLED_NAME);
    if (attr != null) {
      enabled = Boolean.parseBoolean(attr.getValue());
    }
    attr = root.getAttribute(TONEMAPPEDVOLUMESTYLE_ATTR_COOLCOLOR_NAME);
    if (attr != null) {
      String[] sa = parseX(attr.getValue());
      coolColor = parseToSFFloatArray(sa);
    }
    attr = root.getAttribute(TONEMAPPEDVOLUMESTYLE_ATTR_WARMCOLOR_NAME);
    if (attr != null) {
      String[] sa = parseX(attr.getValue());
      warmColor = parseToSFFloatArray(sa);
    }
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (TONEMAPPEDVOLUMESTYLE_ATTR_COOLCOLOR_REQD ||
           (!Arrays.equals(coolColor, coolColorDefault))) {
      sb.append(" ");
      sb.append(TONEMAPPEDVOLUMESTYLE_ATTR_COOLCOLOR_NAME);
      sb.append("='");
      sb.append(coolColor[RED]);
      sb.append(" ");
      sb.append(coolColor[GREEN]);
      sb.append(" ");
      sb.append(coolColor[BLUE]);
      sb.append(" ");
      sb.append(coolColor[ALPHA]);
      sb.append("'");
    }
    if (TONEMAPPEDVOLUMESTYLE_ATTR_ENABLED_REQD || enabled != enabledDefault) {
      sb.append(" ");
      sb.append(TONEMAPPEDVOLUMESTYLE_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }
    if (TONEMAPPEDVOLUMESTYLE_ATTR_WARMCOLOR_REQD ||
           (!Arrays.equals(warmColor, warmColorDefault))) {
      sb.append(" ");
      sb.append(TONEMAPPEDVOLUMESTYLE_ATTR_WARMCOLOR_NAME);
      sb.append("='");
      sb.append(warmColor[RED]);
      sb.append(" ");
      sb.append(warmColor[GREEN]);
      sb.append(" ");
      sb.append(warmColor[BLUE]);
      sb.append(" ");
      sb.append(warmColor[ALPHA]);
      sb.append("'");
    }

    return sb.toString();
  }

  public String getCoolColor()
  {
    return coolColor.toString();
  }
  
  public String getCoolColorRed()
  {
    return coolColor[RED].toString();
  }
  
  public String getCoolColorGreen()
  {
    return coolColor[GREEN].toString();
  }
  
  public String getCoolColorBlue()
  {
    return coolColor[BLUE].toString();
  }
  
  public String getCoolColorAlpha()
  {
    return coolColor[ALPHA].toString();
  }

  public void setWarmColor(String coolColor)
  {
      String[] sa = parseX(coolColor);
      this.coolColor = parseToSFFloatArray(sa);
  }

  public void setWarmColor(String coolColorRed, String coolColorGreen, String coolColorBlue, String coolColorAlpha)
  {
      this.coolColor[RED]   = new SFFloat(coolColorRed,   0.0f, 1.0f);
      this.coolColor[GREEN] = new SFFloat(coolColorGreen, 0.0f, 1.0f);
      this.coolColor[BLUE]  = new SFFloat(coolColorBlue,  0.0f, 1.0f);
      this.coolColor[ALPHA] = new SFFloat(coolColorAlpha, 0.0f, 1.0f);
  }

  
  public String getWarmColor()
  {
    return warmColor.toString();
  }
  
  public String getWarmColorRed()
  {
    return warmColor[RED].toString();
  }
  
  public String getWarmColorGreen()
  {
    return warmColor[GREEN].toString();
  }
  
  public String getWarmColorBlue()
  {
    return warmColor[BLUE].toString();
  }
  
  public String getWarmColorAlpha()
  {
    return warmColor[ALPHA].toString();
  }

  public void setParallelColor(String warmColor)
  {
      String[] sa = parseX(warmColor);
      this.warmColor = parseToSFFloatArray(sa);
  }

  public void setParallelColor(String warmColorRed, String warmColorGreen, String warmColorBlue, String warmColorAlpha)
  {
      this.warmColor[RED]   = new SFFloat(warmColorRed,   0.0f, 1.0f);
      this.warmColor[GREEN] = new SFFloat(warmColorGreen, 0.0f, 1.0f);
      this.warmColor[BLUE]  = new SFFloat(warmColorBlue,  0.0f, 1.0f);
      this.warmColor[ALPHA] = new SFFloat(warmColorAlpha, 0.0f, 1.0f);
  }

}