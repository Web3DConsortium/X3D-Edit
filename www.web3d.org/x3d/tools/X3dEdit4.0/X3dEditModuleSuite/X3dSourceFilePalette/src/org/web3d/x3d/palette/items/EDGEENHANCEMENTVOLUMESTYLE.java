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
 * EDGEENHANCEMENTVOLUMESTYLE.java
 * Created on 15 November 2011
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey and Don Brutzman
 * @version $Id$
 */
public class EDGEENHANCEMENTVOLUMESTYLE extends X3DComposableVolumeRenderStyleNode
{
  private SFFloat   gradientThreshold, gradientThresholdDefault;
  private SFFloat[] edgeColor, edgeColorDefault = new SFFloat[4];
  
  private final int RED   = 0;
  private final int GREEN = 1;
  private final int BLUE  = 2;
  private final int ALPHA = 3;  
  
  public EDGEENHANCEMENTVOLUMESTYLE()
  {
  }

  @Override
  public String getElementName()
  {
    return EDGEENHANCEMENTVOLUMESTYLE_ELNAME;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return EDGEENHANCEMENTVOLUMESTYLECustomizer.class;
  }

  @Override
  @SuppressWarnings("NestedAssignment")
  public void initialize()
  {
    enabled = enabledDefault = Boolean.parseBoolean(EDGEENHANCEMENTVOLUMESTYLE_ATTR_ENABLED_DFLT);
    
    gradientThreshold = gradientThresholdDefault = new SFFloat(EDGEENHANCEMENTVOLUMESTYLE_ATTR_GRADIENTTHRESHOLD_DFLT, 0.0f, 1.0f);

    String[] sa;
    if(EDGEENHANCEMENTVOLUMESTYLE_ATTR_EDGECOLOR_DFLT == null || EDGEENHANCEMENTVOLUMESTYLE_ATTR_EDGECOLOR_DFLT.length()<=0)
      sa = new String[]{"0.0", "0.0", "0.0", "1.0"}; // default 
    else
      sa = parseX(EDGEENHANCEMENTVOLUMESTYLE_ATTR_EDGECOLOR_DFLT);
    edgeColor = edgeColorDefault = parseToSFFloatArray(sa);
    
    setContent("\n\t\t<!--TODO add surfaceNormals X3DTexture3DNode here-->\n\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(EDGEENHANCEMENTVOLUMESTYLE_ATTR_ENABLED_NAME);
    if (attr != null) {
      enabled = Boolean.parseBoolean(attr.getValue());
    }
    attr = root.getAttribute(EDGEENHANCEMENTVOLUMESTYLE_ATTR_GRADIENTTHRESHOLD_NAME);
    if (attr != null) {
        gradientThreshold  = new SFFloat(attr.getValue(), 0.0f, 1.0f);
    }
    attr = root.getAttribute(EDGEENHANCEMENTVOLUMESTYLE_ATTR_EDGECOLOR_NAME);
    if (attr != null) {
      String[] sa = parseX(attr.getValue());
      edgeColor = parseToSFFloatArray(sa);
    }
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (EDGEENHANCEMENTVOLUMESTYLE_ATTR_EDGECOLOR_REQD ||
           (!Arrays.equals(edgeColor, edgeColorDefault))) {
      sb.append(" ");
      sb.append(EDGEENHANCEMENTVOLUMESTYLE_ATTR_EDGECOLOR_NAME);
      sb.append("='");
      sb.append(edgeColor[RED]);
      sb.append(" ");
      sb.append(edgeColor[GREEN]);
      sb.append(" ");
      sb.append(edgeColor[BLUE]);
      sb.append(" ");
      sb.append(edgeColor[ALPHA]);
      sb.append("'");
    }
    if (EDGEENHANCEMENTVOLUMESTYLE_ATTR_ENABLED_REQD || enabled != enabledDefault) {
      sb.append(" ");
      sb.append(EDGEENHANCEMENTVOLUMESTYLE_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }
    if (EDGEENHANCEMENTVOLUMESTYLE_ATTR_GRADIENTTHRESHOLD_REQD || (gradientThreshold != gradientThresholdDefault)) {
      sb.append(" ");
      sb.append(EDGEENHANCEMENTVOLUMESTYLE_ATTR_GRADIENTTHRESHOLD_NAME);
      sb.append("='");
      sb.append(gradientThreshold);
      sb.append("'");
    }

    return sb.toString();
  }

  public String getGradientThreshold()
  {
    return Float.toString(gradientThreshold.getValue());
  }

  public void setGradientThreshold(String gradientThreshold)
  {
    this.gradientThreshold = new SFFloat(gradientThreshold, 0.0f, 1.0f);
  }
  
  public String getEdgeColor()
  {
    return edgeColor.toString();
  }
  
  public String getEdgeColorRed()
  {
    return edgeColor[RED].toString();
  }
  
  public String getEdgeColorGreen()
  {
    return edgeColor[GREEN].toString();
  }
  
  public String getEdgeColorBlue()
  {
    return edgeColor[BLUE].toString();
  }
  
  public String getEdgeColorAlpha()
  {
    return edgeColor[ALPHA].toString();
  }

  public void setEdgeColor(String edgeColor)
  {
      String[] sa = parseX(edgeColor);
      this.edgeColor = parseToSFFloatArray(sa);
  }

  public void setEdgeColor(String edgeColorRed, String edgeColorGreen, String edgeColorBlue, String edgeColorAlpha)
  {
      this.edgeColor[RED]   = new SFFloat(edgeColorRed,   0.0f, 1.0f);
      this.edgeColor[GREEN] = new SFFloat(edgeColorGreen, 0.0f, 1.0f);
      this.edgeColor[BLUE]  = new SFFloat(edgeColorBlue,  0.0f, 1.0f);
      this.edgeColor[ALPHA] = new SFFloat(edgeColorAlpha, 0.0f, 1.0f);
  }
}