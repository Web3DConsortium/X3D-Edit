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

/**
 * BOUNDARYENHANCEMENTVOLUMESTYLE.java
 * Created on 15 November 2011
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey and Don Brutzman
 * @version $Id$
 */
public class BOUNDARYENHANCEMENTVOLUMESTYLE extends X3DComposableVolumeRenderStyleNode
{
  private SFFloat   boundaryOpacity, boundaryOpacityDefault;
  private SFFloat     opacityFactor,   opacityFactorDefault;
  private SFFloat   retainedOpacity, retainedOpacityDefault;
  
  public BOUNDARYENHANCEMENTVOLUMESTYLE()
  {
  }

  @Override
  public String getElementName()
  {
    return BOUNDARYENHANCEMENTVOLUMESTYLE_ELNAME;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return BOUNDARYENHANCEMENTVOLUMESTYLECustomizer.class;
  }

  @Override
  @SuppressWarnings("NestedAssignment")
  public void initialize()
  {
    enabled = enabledDefault = Boolean.parseBoolean(BOUNDARYENHANCEMENTVOLUMESTYLE_ATTR_ENABLED_DFLT);
    
    boundaryOpacity = boundaryOpacityDefault = new SFFloat(BOUNDARYENHANCEMENTVOLUMESTYLE_ATTR_BOUNDARYOPACITY_DFLT, 0.0f, null);
    opacityFactor   = opacityFactorDefault   = new SFFloat(BOUNDARYENHANCEMENTVOLUMESTYLE_ATTR_OPACITYFACTOR_DFLT,   0.0f, null);
    retainedOpacity = retainedOpacityDefault = new SFFloat(BOUNDARYENHANCEMENTVOLUMESTYLE_ATTR_RETAINEDOPACITY_DFLT, 0.0f, 1.0f);

    setContent("\n\t\t<!--TODO add transferFunction X3DTextureNode here-->\n\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(BOUNDARYENHANCEMENTVOLUMESTYLE_ATTR_ENABLED_NAME);
    if (attr != null) {
      enabled = Boolean.parseBoolean(attr.getValue());
    }
    attr = root.getAttribute(BOUNDARYENHANCEMENTVOLUMESTYLE_ATTR_BOUNDARYOPACITY_NAME);
    if (attr != null) {
        boundaryOpacity  = new SFFloat(attr.getValue(), 0.0f, null);
    }
    attr = root.getAttribute(BOUNDARYENHANCEMENTVOLUMESTYLE_ATTR_OPACITYFACTOR_NAME);
    if (attr != null) {
        opacityFactor = new SFFloat(attr.getValue(), 0.0f, null);
    }
    attr = root.getAttribute(BOUNDARYENHANCEMENTVOLUMESTYLE_ATTR_RETAINEDOPACITY_NAME);
    if (attr != null) {
        retainedOpacity = new SFFloat(attr.getValue(), 0.0f, 1.0f);
    }
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (BOUNDARYENHANCEMENTVOLUMESTYLE_ATTR_BOUNDARYOPACITY_REQD ||
           (!boundaryOpacity.equals(boundaryOpacityDefault))) {
      sb.append(" ");
      sb.append(BOUNDARYENHANCEMENTVOLUMESTYLE_ATTR_BOUNDARYOPACITY_NAME);
      sb.append("='");
      sb.append(boundaryOpacity);
      sb.append("'");
    }
    if (BOUNDARYENHANCEMENTVOLUMESTYLE_ATTR_ENABLED_REQD || enabled != enabledDefault) {
      sb.append(" ");
      sb.append(BOUNDARYENHANCEMENTVOLUMESTYLE_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }
    if (BOUNDARYENHANCEMENTVOLUMESTYLE_ATTR_OPACITYFACTOR_REQD ||
           (!boundaryOpacity.equals(opacityFactorDefault))) {
      sb.append(" ");
      sb.append(BOUNDARYENHANCEMENTVOLUMESTYLE_ATTR_OPACITYFACTOR_NAME);
      sb.append("='");
      sb.append(opacityFactor);
      sb.append("'");
    }
    if (BOUNDARYENHANCEMENTVOLUMESTYLE_ATTR_RETAINEDOPACITY_REQD ||
           (!retainedOpacity.equals(retainedOpacityDefault))) {
      sb.append(" ");
      sb.append(BOUNDARYENHANCEMENTVOLUMESTYLE_ATTR_RETAINEDOPACITY_NAME);
      sb.append("='");
      sb.append(retainedOpacity);
      sb.append("'");
    }

    return sb.toString();
  }

  public String getBoundaryOpacity()
  {
    return boundaryOpacity.toString();
  }

  public void setBoundaryOpacity(String boundaryOpacity)
  {
    this.boundaryOpacity = new SFFloat(boundaryOpacity, null, null);
  }
  
  public String getOpacityFactor()
  {
    return opacityFactor.toString();
  }

  public void setOpacityFactor(String opacityFactor)
  {
    this.opacityFactor = new SFFloat(opacityFactor, 0.0f, null);
  }

  
  public String getRetainedOpacity()
  {
    return retainedOpacity.toString();
  }

  public void setRetainedOpacity(String retainedOpacity)
  {
    this.retainedOpacity = new SFFloat(retainedOpacity, 0.0f, null);
  }

}