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
 * CARTOONVOLUMESTYLE.java
 * Created on 15 November 2011
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey and Don Brutzman
 * @version $Id$
 */
public class CARTOONVOLUMESTYLE extends X3DComposableVolumeRenderStyleNode
{
  private SFInt32        colorSteps,      colorStepsDefault;
  private SFFloat[] orthogonalColor, orthogonalColorDefault = new SFFloat[4];
  private SFFloat[]   parallelColor,   parallelColorDefault = new SFFloat[4];
  
  private final int RED   = 0;
  private final int GREEN = 1;
  private final int BLUE  = 2;
  private final int ALPHA = 3;  
  
  public CARTOONVOLUMESTYLE()
  {
  }

  @Override
  public String getElementName()
  {
    return CARTOONVOLUMESTYLE_ELNAME;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return CARTOONVOLUMESTYLECustomizer.class;
  }

  @Override
  @SuppressWarnings("NestedAssignment")
  public void initialize()
  {
    enabled = enabledDefault = Boolean.parseBoolean(CARTOONVOLUMESTYLE_ATTR_ENABLED_DFLT);
    
         colorSteps =      colorStepsDefault = new SFInt32(CARTOONVOLUMESTYLE_ATTR_COLORSTEPS_DFLT);

    String[] sa;
    if(CARTOONVOLUMESTYLE_ATTR_ORTHOGONALCOLOR_DFLT == null || CARTOONVOLUMESTYLE_ATTR_ORTHOGONALCOLOR_DFLT.length()<=0)
      sa = new String[]{"0.8", "0.8", "0.8", "1.0"}; // default 
    else
      sa = parseX(CARTOONVOLUMESTYLE_ATTR_ORTHOGONALCOLOR_DFLT);
    
    orthogonalColor = orthogonalColorDefault = parseToSFFloatArray(sa);
    
    if(CARTOONVOLUMESTYLE_ATTR_PARALLELCOLOR_DFLT == null || CARTOONVOLUMESTYLE_ATTR_PARALLELCOLOR_DFLT.length()<=0)
      sa = new String[]{}; // empty 
    else
      sa = parseX(CARTOONVOLUMESTYLE_ATTR_PARALLELCOLOR_DFLT);
    
    parallelColor =   parallelColorDefault = parseToSFFloatArray(sa); 
    
    setContent("\n\t\t<!--TODO add surfaceNormals X3DTexture3DNode here-->\n\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(CARTOONVOLUMESTYLE_ATTR_ENABLED_NAME);
    if (attr != null) {
      enabled = Boolean.parseBoolean(attr.getValue());
    }
    attr = root.getAttribute(CARTOONVOLUMESTYLE_ATTR_COLORSTEPS_NAME);
    if (attr != null) {
        colorSteps  = new SFInt32(attr.getValue());
    }
    attr = root.getAttribute(CARTOONVOLUMESTYLE_ATTR_ORTHOGONALCOLOR_NAME);
    if (attr != null) {
      String[] sa = parseX(attr.getValue());
      orthogonalColor = parseToSFFloatArray(sa);
    }
    attr = root.getAttribute(CARTOONVOLUMESTYLE_ATTR_PARALLELCOLOR_NAME);
    if (attr != null) {
      String[] sa = parseX(attr.getValue());
      parallelColor = parseToSFFloatArray(sa);
    }
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (CARTOONVOLUMESTYLE_ATTR_COLORSTEPS_REQD || (colorSteps != colorStepsDefault)) {
      sb.append(" ");
      sb.append(CARTOONVOLUMESTYLE_ATTR_COLORSTEPS_NAME);
      sb.append("='");
      sb.append(colorSteps);
      sb.append("'");
    }
    if (CARTOONVOLUMESTYLE_ATTR_ENABLED_REQD || enabled != enabledDefault) {
      sb.append(" ");
      sb.append(CARTOONVOLUMESTYLE_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }
    if (CARTOONVOLUMESTYLE_ATTR_ORTHOGONALCOLOR_REQD ||
           (!Arrays.equals(orthogonalColor, orthogonalColorDefault))) {
      sb.append(" ");
      sb.append(CARTOONVOLUMESTYLE_ATTR_ORTHOGONALCOLOR_NAME);
      sb.append("='");
      sb.append(orthogonalColor[RED]);
      sb.append(" ");
      sb.append(orthogonalColor[GREEN]);
      sb.append(" ");
      sb.append(orthogonalColor[BLUE]);
      sb.append(" ");
      sb.append(orthogonalColor[ALPHA]);
      sb.append("'");
    }
    if (CARTOONVOLUMESTYLE_ATTR_PARALLELCOLOR_REQD ||
           (!Arrays.equals(parallelColor, parallelColorDefault))) {
      sb.append(" ");
      sb.append(CARTOONVOLUMESTYLE_ATTR_PARALLELCOLOR_NAME);
      sb.append("='");
      sb.append(parallelColor[RED]);
      sb.append(" ");
      sb.append(parallelColor[GREEN]);
      sb.append(" ");
      sb.append(parallelColor[BLUE]);
      sb.append(" ");
      sb.append(parallelColor[ALPHA]);
      sb.append("'");
    }

    return sb.toString();
  }

  public String getColorSteps()
  {
    return colorSteps.toString();
  }

  public void setColorSteps(String colorSteps)
  {
    this.colorSteps = new SFInt32(colorSteps);
  }
  
  public String getOrthogonalColor()
  {
    return orthogonalColor.toString();
  }
  
  public String getOrthogonalColorRed()
  {
    return orthogonalColor[RED].toString();
  }
  
  public String getOrthogonalColorGreen()
  {
    return orthogonalColor[GREEN].toString();
  }
  
  public String getOrthogonalColorBlue()
  {
    return orthogonalColor[BLUE].toString();
  }
  
  public String getOrthogonalColorAlpha()
  {
    return orthogonalColor[ALPHA].toString();
  }

  public void setOrthogonalColor(String orthogonalColor)
  {
      String[] sa = parseX(orthogonalColor);
      this.orthogonalColor = parseToSFFloatArray(sa);
  }

  public void setOrthogonalColor(String orthogonalColorRed, String orthogonalColorGreen, String orthogonalColorBlue, String orthogonalColorAlpha)
  {
      this.orthogonalColor[RED]   = new SFFloat(orthogonalColorRed,   0.0f, 1.0f);
      this.orthogonalColor[GREEN] = new SFFloat(orthogonalColorGreen, 0.0f, 1.0f);
      this.orthogonalColor[BLUE]  = new SFFloat(orthogonalColorBlue,  0.0f, 1.0f);
      this.orthogonalColor[ALPHA] = new SFFloat(orthogonalColorAlpha, 0.0f, 1.0f);
  }

  
  public String getParallelColor()
  {
    return parallelColor.toString();
  }
  
  public String getParallelColorRed()
  {
    return parallelColor[RED].toString();
  }
  
  public String getParallelColorGreen()
  {
    return parallelColor[GREEN].toString();
  }
  
  public String getParallelColorBlue()
  {
    return parallelColor[BLUE].toString();
  }
  
  public String getParallelColorAlpha()
  {
    return parallelColor[ALPHA].toString();
  }

  public void setParallelColor(String parallelColor)
  {
      String[] sa = parseX(parallelColor);
      this.parallelColor = parseToSFFloatArray(sa);
  }

  public void setParallelColor(String parallelColorRed, String parallelColorGreen, String parallelColorBlue, String parallelColorAlpha)
  {
      this.parallelColor[RED]   = new SFFloat(parallelColorRed,   0.0f, 1.0f);
      this.parallelColor[GREEN] = new SFFloat(parallelColorGreen, 0.0f, 1.0f);
      this.parallelColor[BLUE]  = new SFFloat(parallelColorBlue,  0.0f, 1.0f);
      this.parallelColor[ALPHA] = new SFFloat(parallelColorAlpha, 0.0f, 1.0f);
  }

}