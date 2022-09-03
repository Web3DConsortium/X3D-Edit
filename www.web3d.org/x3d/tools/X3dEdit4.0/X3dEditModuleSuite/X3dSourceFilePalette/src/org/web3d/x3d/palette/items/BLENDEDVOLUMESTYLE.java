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
import org.web3d.x3d.types.X3DComposableVolumeRenderStyleNode;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFFloat;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * BLENDEDVOLUMESTYLE.java
 * Created on 16 November 2011
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey and Don Brutzman
 * @version $Id$
 */
public class BLENDEDVOLUMESTYLE extends X3DComposableVolumeRenderStyleNode
{
  private SFFloat   weightConstant1, weightConstant1Default;
  private SFFloat   weightConstant2, weightConstant2Default;
  private String    weightFunction1, weightFunction1Default;
  private String    weightFunction2, weightFunction2Default;  
  // Together make one SFVec3F
  protected SFFloat bboxCenterX, bboxCenterXDefault;
  protected SFFloat bboxCenterY, bboxCenterYDefault;
  protected SFFloat bboxCenterZ, bboxCenterZDefault;

  // Together make one SFVec3F
  protected SFFloat bboxSizeX,   bboxSizeXDefault;
  protected SFFloat bboxSizeY,   bboxSizeYDefault;
  protected SFFloat bboxSizeZ,   bboxSizeZDefault;
  
  public BLENDEDVOLUMESTYLE()
  {
  }

  @Override
  public String getElementName()
  {
    return BLENDEDVOLUMESTYLE_ELNAME;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return BLENDEDVOLUMESTYLECustomizer.class;
  }

  @Override
  @SuppressWarnings("NestedAssignment")
  public void initialize()
  {
    enabled = enabledDefault = Boolean.parseBoolean(BLENDEDVOLUMESTYLE_ATTR_ENABLED_DFLT);
    
    weightConstant1 = weightConstant1Default = new SFFloat(BLENDEDVOLUMESTYLE_ATTR_WEIGHTCONSTANT1_DFLT, 0.0f, 1.0f);
    weightConstant2 = weightConstant2Default = new SFFloat(BLENDEDVOLUMESTYLE_ATTR_WEIGHTCONSTANT2_DFLT, 0.0f, 1.0f);
    weightFunction1 = weightFunction1Default = BLENDEDVOLUMESTYLE_ATTR_WEIGHTFUNCTION1_DFLT;
    weightFunction2 = weightFunction2Default = BLENDEDVOLUMESTYLE_ATTR_WEIGHTFUNCTION2_DFLT;
    
    String[] fa;
    
    fa = parse3(VOLUMEDATA_ATTR_BBOXCENTER_DFLT);
    bboxCenterX = bboxCenterXDefault = new SFFloat(fa[0], null, null);
    bboxCenterY = bboxCenterYDefault = new SFFloat(fa[1], null, null);
    bboxCenterZ = bboxCenterZDefault = new SFFloat(fa[2], null, null);

    fa = parse3(VOLUMEDATA_ATTR_BBOXSIZE_DFLT);
    bboxSizeX = bboxSizeXDefault = new SFFloat(fa[0], null, null);
    bboxSizeY = bboxSizeYDefault = new SFFloat(fa[1], null, null);
    bboxSizeZ = bboxSizeZDefault = new SFFloat(fa[2], null, null);

    setContent("\n\t\t<!--TODO add X3DVolumeRenderStyleNode and X3DTexture3DNode voxels here-->\n\t" +
               "\n\t\t<!--TODO add weightTransferFunction1 and weightTransferFunction2 Texture2D nodes here -->\n\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(BLENDEDVOLUMESTYLE_ATTR_ENABLED_NAME);
    if (attr != null) {
      enabled = Boolean.parseBoolean(attr.getValue());
    }
    attr = root.getAttribute(BLENDEDVOLUMESTYLE_ATTR_WEIGHTCONSTANT1_NAME);
    if (attr != null) {
        weightConstant1  = new SFFloat(attr.getValue(), 0.0f, null);
    }
    attr = root.getAttribute(BLENDEDVOLUMESTYLE_ATTR_WEIGHTCONSTANT2_NAME);
    if (attr != null) {
        weightConstant2 = new SFFloat(attr.getValue(), 0.0f, null);
    }
    attr = root.getAttribute(BLENDEDVOLUMESTYLE_ATTR_WEIGHTFUNCTION1_NAME);
    if (attr != null) {
        weightFunction1 = attr.getValue();
    }
    attr = root.getAttribute(BLENDEDVOLUMESTYLE_ATTR_WEIGHTFUNCTION2_NAME);
    if (attr != null) {
        weightFunction2 = attr.getValue();
    }

    String[] fa;
    attr = root.getAttribute(VOLUMEDATA_ATTR_BBOXCENTER_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      bboxCenterX = new SFFloat(fa[0], null, null);
      bboxCenterY = new SFFloat(fa[1], null, null);
      bboxCenterZ = new SFFloat(fa[2], null, null);
    }
    attr = root.getAttribute(VOLUMEDATA_ATTR_BBOXSIZE_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      bboxSizeX = new SFFloat(fa[0], null, null);
      bboxSizeY = new SFFloat(fa[1], null, null);
      bboxSizeZ = new SFFloat(fa[2], null, null);
    }
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (VOLUMEDATA_ATTR_BBOXCENTER_REQD ||
           (!bboxCenterX.equals(bboxCenterXDefault) ||
            !bboxCenterY.equals(bboxCenterYDefault) ||
            !bboxCenterZ.equals(bboxCenterZDefault))) {
      sb.append(" ");
      sb.append(VOLUMEDATA_ATTR_BBOXCENTER_NAME);
      sb.append("='");
      sb.append(bboxCenterX);
      sb.append(" ");
      sb.append(bboxCenterY);
      sb.append(" ");
      sb.append(bboxCenterZ);
      sb.append("'");
    }
    if (VOLUMEDATA_ATTR_BBOXSIZE_REQD ||
           (!bboxSizeX.equals(bboxSizeXDefault) ||
            !bboxSizeY.equals(bboxSizeYDefault) ||
            !bboxSizeZ.equals(bboxSizeZDefault))) {
      sb.append(" ");
      sb.append(VOLUMEDATA_ATTR_BBOXSIZE_NAME);
      sb.append("='");
      sb.append(bboxSizeX);
      sb.append(" ");
      sb.append(bboxSizeY);
      sb.append(" ");
      sb.append(bboxSizeZ);
      sb.append("'");
    }
    if (BLENDEDVOLUMESTYLE_ATTR_ENABLED_REQD || enabled != enabledDefault) {
      sb.append(" ");
      sb.append(BLENDEDVOLUMESTYLE_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }
    if (BLENDEDVOLUMESTYLE_ATTR_WEIGHTCONSTANT1_REQD ||
           (!weightConstant1.equals(weightConstant1Default))) {
      sb.append(" ");
      sb.append(BLENDEDVOLUMESTYLE_ATTR_WEIGHTCONSTANT1_NAME);
      sb.append("='");
      sb.append(weightConstant1);
      sb.append("'");
    }
    if (BLENDEDVOLUMESTYLE_ATTR_WEIGHTCONSTANT2_REQD ||
           (!weightConstant1.equals(weightConstant2Default))) {
      sb.append(" ");
      sb.append(BLENDEDVOLUMESTYLE_ATTR_WEIGHTCONSTANT2_NAME);
      sb.append("='");
      sb.append(weightConstant2);
      sb.append("'");
    }
    if (BLENDEDVOLUMESTYLE_ATTR_WEIGHTFUNCTION1_REQD ||
           (!weightConstant1.equals(weightConstant2Default))) {
      sb.append(" ");
      sb.append(BLENDEDVOLUMESTYLE_ATTR_WEIGHTFUNCTION1_NAME);
      sb.append("='");
      sb.append(weightFunction1);
      sb.append("'");
    }
    if (BLENDEDVOLUMESTYLE_ATTR_WEIGHTFUNCTION2_REQD ||
           (!weightConstant1.equals(weightConstant2Default))) {
      sb.append(" ");
      sb.append(BLENDEDVOLUMESTYLE_ATTR_WEIGHTFUNCTION2_NAME);
      sb.append("='");
      sb.append(weightFunction2);
      sb.append("'");
    }
    return sb.toString();
  }

  public String getBboxCenterX()
  {
    return bboxCenterX.toString();
  }

  public void setBboxCenterX(String bboxCenterX)
  {
    this.bboxCenterX = new SFFloat(bboxCenterX, null, null);
  }

  public String getBboxCenterY()
  {
    return bboxCenterY.toString();
  }

  public void setBboxCenterY(String bboxCenterY)
  {
    this.bboxCenterY = new SFFloat(bboxCenterY, null, null);
  }

  public String getBboxCenterZ()
  {
    return bboxCenterZ.toString();
  }

  public void setBboxCenterZ(String bboxCenterZ)
  {
    this.bboxCenterZ = new SFFloat(bboxCenterZ, null, null);
  }

  public String getBboxSizeX()
  {
    return bboxSizeX.toString();
  }

  public void setBboxSizeX(String bboxSizeX)
  {
    this.bboxSizeX = new SFFloat(bboxSizeX, -1.0f, null);
  }

  public String getBboxSizeY()
  {
    return bboxSizeY.toString();
  }

  public void setBboxSizeY(String bboxSizeY)
  {
    this.bboxSizeY = new SFFloat(bboxSizeY, -1.0f, null);
  }

  public String getBboxSizeZ()
  {
    return bboxSizeZ.toString();
  }

  public void setBboxSizeZ(String bboxSizeZ)
  {
    this.bboxSizeZ = new SFFloat(bboxSizeZ, -1.0f, null);
  }

  public String getWeightConstant1()
  {
    return weightConstant1.toString();
  }

  public void setWeightConstant1(String weightConstant1)
  {
    this.weightConstant1 = new SFFloat(weightConstant1, null, null);
  }

  public String getWeightConstant2()
  {
    return weightConstant2.toString();
  }

  public void setWeightConstant2(String weightConstant1)
  {
    this.weightConstant2 = new SFFloat(weightConstant1, null, null);
  }

  public String getWeightFunction1()
  {
    return weightFunction1;
  }

  public void setWeightFunction1(String weightFunction)
  {
    this.weightFunction1 = weightFunction;
  }

  public String getWeightFunction2()
  {
    return weightFunction2;
  }

  public void setWeightFunction2(String weightFunction)
  {
    this.weightFunction2 = weightFunction;
  }

}