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

import org.web3d.x3d.types.X3DComposableVolumeRenderStyleNode;
import javax.swing.text.JTextComponent;

import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * SILHOUETTEENHANCEMENTVOLUMESTYLE.java
 * Created on 20 November 2011
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey and Don Brutzman
 * @version $Id$
 */
public class SILHOUETTEENHANCEMENTVOLUMESTYLE extends X3DComposableVolumeRenderStyleNode
{
  private SFFloat   silhouetteBoundaryOpacity, silhouetteBoundaryOpacityDefault;
  private SFFloat   silhouetteRetainedOpacity, silhouetteRetainedOpacityDefault;
  private SFFloat   silhouetteSharpness,       silhouetteSharpnessDefault;
  
  public SILHOUETTEENHANCEMENTVOLUMESTYLE()
  {
  }

  @Override
  public String getElementName()
  {
    return SILHOUETTEENHANCEMENTVOLUMESTYLE_ELNAME;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return SILHOUETTEENHANCEMENTVOLUMESTYLECustomizer.class;
  }

  @Override
  @SuppressWarnings("NestedAssignment")
  public void initialize()
  {
    enabled = enabledDefault = Boolean.parseBoolean(SILHOUETTEENHANCEMENTVOLUMESTYLE_ATTR_ENABLED_DFLT);
    
    silhouetteBoundaryOpacity = silhouetteBoundaryOpacityDefault = new SFFloat(SILHOUETTEENHANCEMENTVOLUMESTYLE_ATTR_SILHOUETTEBOUNDARYOPACITY_DFLT, 0.0f, 1.0f);
    silhouetteRetainedOpacity = silhouetteRetainedOpacityDefault = new SFFloat(SILHOUETTEENHANCEMENTVOLUMESTYLE_ATTR_SILHOUETTERETAINEDOPACITY_DFLT, 0.0f, 1.0f);
    silhouetteSharpness       = silhouetteSharpnessDefault       = new SFFloat(SILHOUETTEENHANCEMENTVOLUMESTYLE_ATTR_SILHOUETTESHARPNESS_DFLT,       0.0f, null);

    setContent("\n\t\t<!--TODO add transferFunction X3DTextureNode here-->\n\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(SILHOUETTEENHANCEMENTVOLUMESTYLE_ATTR_ENABLED_NAME);
    if (attr != null) {
      enabled = Boolean.parseBoolean(attr.getValue());
    }
    attr = root.getAttribute(SILHOUETTEENHANCEMENTVOLUMESTYLE_ATTR_SILHOUETTEBOUNDARYOPACITY_NAME);
    if (attr != null) {
        silhouetteBoundaryOpacity = new SFFloat(attr.getValue(), 0.0f, 1.0f);
    }
    attr = root.getAttribute(SILHOUETTEENHANCEMENTVOLUMESTYLE_ATTR_SILHOUETTERETAINEDOPACITY_NAME);
    if (attr != null) {
        silhouetteRetainedOpacity = new SFFloat(attr.getValue(), 0.0f, 1.0f);
    }
    attr = root.getAttribute(SILHOUETTEENHANCEMENTVOLUMESTYLE_ATTR_SILHOUETTESHARPNESS_NAME);
    if (attr != null) {
        silhouetteSharpness       = new SFFloat(attr.getValue(), 0.0f, null);
    }
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (SILHOUETTEENHANCEMENTVOLUMESTYLE_ATTR_ENABLED_REQD || enabled != enabledDefault) {
      sb.append(" ");
      sb.append(SILHOUETTEENHANCEMENTVOLUMESTYLE_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }
    if (SILHOUETTEENHANCEMENTVOLUMESTYLE_ATTR_SILHOUETTEBOUNDARYOPACITY_REQD ||
           (!silhouetteBoundaryOpacity.equals(silhouetteBoundaryOpacityDefault))) {
      sb.append(" ");
      sb.append(SILHOUETTEENHANCEMENTVOLUMESTYLE_ATTR_SILHOUETTEBOUNDARYOPACITY_NAME);
      sb.append("='");
      sb.append(silhouetteBoundaryOpacity);
      sb.append("'");
    }
    if (SILHOUETTEENHANCEMENTVOLUMESTYLE_ATTR_SILHOUETTERETAINEDOPACITY_REQD ||
           (!silhouetteBoundaryOpacity.equals(silhouetteRetainedOpacityDefault))) {
      sb.append(" ");
      sb.append(SILHOUETTEENHANCEMENTVOLUMESTYLE_ATTR_SILHOUETTERETAINEDOPACITY_NAME);
      sb.append("='");
      sb.append(silhouetteRetainedOpacity);
      sb.append("'");
    }
    if (SILHOUETTEENHANCEMENTVOLUMESTYLE_ATTR_SILHOUETTESHARPNESS_REQD ||
           (!silhouetteSharpness.equals(silhouetteSharpnessDefault))) {
      sb.append(" ");
      sb.append(SILHOUETTEENHANCEMENTVOLUMESTYLE_ATTR_SILHOUETTESHARPNESS_NAME);
      sb.append("='");
      sb.append(silhouetteSharpness);
      sb.append("'");
    }

    return sb.toString();
  }

  public String getSilhouetteBoundaryOpacity()
  {
    return silhouetteBoundaryOpacity.toString();
  }

  public void setSilhouetteBoundaryOpacity(String silhouetteBoundaryOpacity)
  {
    this.silhouetteBoundaryOpacity = new SFFloat(silhouetteBoundaryOpacity, 0.0f, 1.0f);
  }
  
  public String getSilhouetteRetainedOpacity()
  {
    return silhouetteRetainedOpacity.toString();
  }

  public void setSilhouetteRetainedOpacity(String silhouetteRetainedOpacity)
  {
    this.silhouetteRetainedOpacity = new SFFloat(silhouetteRetainedOpacity, 0.0f, 1.0f);
  }

  
  public String getSilhouetteSharpness()
  {
    return silhouetteSharpness.toString();
  }

  public void setSilhouetteSharpness(String silhouetteSharpness)
  {
    this.silhouetteSharpness = new SFFloat(silhouetteSharpness, 0.0f, null);
  }

}