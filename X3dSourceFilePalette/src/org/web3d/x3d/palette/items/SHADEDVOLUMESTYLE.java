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

/**
 * SHADEDVOLUMESTYLE.java
 * Created on 20 November 2011
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey and Don Brutzman
 * @version $Id$
 */
public class SHADEDVOLUMESTYLE extends X3DComposableVolumeRenderStyleNode
{
  private boolean      lighting,      lightingDefault;
  private boolean       shadows,       shadowsDefault;
  private String  phaseFunction, phaseFunctionDefault;
  
  public SHADEDVOLUMESTYLE()
  {
  }

  @Override
  public String getElementName()
  {
    return SHADEDVOLUMESTYLE_ELNAME;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return SHADEDVOLUMESTYLECustomizer.class;
  }

  @Override
  @SuppressWarnings("NestedAssignment")
  public void initialize()
  {
     enabled =  enabledDefault = Boolean.parseBoolean(SHADEDVOLUMESTYLE_ATTR_ENABLED_DFLT);
        setLighting(lightingDefault = Boolean.parseBoolean(SHADEDVOLUMESTYLE_ATTR_LIGHTING_DFLT));
        setShadows(shadowsDefault = Boolean.parseBoolean(SHADEDVOLUMESTYLE_ATTR_SHADOWS_DFLT));
    
    phaseFunction = phaseFunctionDefault = SHADEDVOLUMESTYLE_ATTR_PHASEFUNCTION_DFLT;
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(SHADEDVOLUMESTYLE_ATTR_ENABLED_NAME);
    if (attr != null) {
      enabled = Boolean.parseBoolean(attr.getValue());
    }
    attr = root.getAttribute(SHADEDVOLUMESTYLE_ATTR_LIGHTING_NAME);
    if (attr != null) {
            setLighting(Boolean.parseBoolean(attr.getValue()));
    }
    attr = root.getAttribute(SHADEDVOLUMESTYLE_ATTR_SHADOWS_NAME);
    if (attr != null) {
            setShadows(Boolean.parseBoolean(attr.getValue()));
    }
    attr = root.getAttribute(SHADEDVOLUMESTYLE_ATTR_PHASEFUNCTION_NAME);
    if (attr != null) {
        phaseFunction = attr.getValue();
    }
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (SHADEDVOLUMESTYLE_ATTR_ENABLED_REQD || enabled != enabledDefault) {
      sb.append(" ");
      sb.append(SHADEDVOLUMESTYLE_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }
    if (SHADEDVOLUMESTYLE_ATTR_LIGHTING_REQD || (isLighting() != lightingDefault)) {
      sb.append(" ");
      sb.append(SHADEDVOLUMESTYLE_ATTR_LIGHTING_NAME);
      sb.append("='");
      sb.append(isLighting());
      sb.append("'");
    }
    if (SHADEDVOLUMESTYLE_ATTR_PHASEFUNCTION_REQD || (!phaseFunction.equals(phaseFunctionDefault))) {
      sb.append(" ");
      sb.append(SHADEDVOLUMESTYLE_ATTR_PHASEFUNCTION_NAME);
      sb.append("='");
      sb.append(phaseFunction);
      sb.append("'");
    }
    if (SHADEDVOLUMESTYLE_ATTR_SHADOWS_REQD || (isShadows() != shadowsDefault)) {
      sb.append(" ");
      sb.append(SHADEDVOLUMESTYLE_ATTR_SHADOWS_NAME);
      sb.append("='");
      sb.append(isShadows());
      sb.append("'");
    }

    return sb.toString();
  }

    /**
     * @return the type
     */
    public String getPhaseFunction() {
        return phaseFunction;
    }

    /**
     * @param phaseFunction the phaseFunction value to set
     */
    public void setPhaseFunction(String phaseFunction) {
        this.phaseFunction = phaseFunction;
    }

    /**
     * @return the lighting
     */
    public boolean isLighting() {
        return lighting;
    }

    /**
     * @param lighting the lighting to set
     */
    public void setLighting(boolean lighting) {
        this.lighting = lighting;
    }

    /**
     * @return the shadows
     */
    public boolean isShadows() {
        return shadows;
    }

    /**
     * @param shadows the shadows to set
     */
    public void setShadows(boolean shadows) {
        this.shadows = shadows;
    }
}