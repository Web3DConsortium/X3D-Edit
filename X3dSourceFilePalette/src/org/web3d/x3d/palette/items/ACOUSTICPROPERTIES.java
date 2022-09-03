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
import org.web3d.x3d.types.X3DAppearanceChildNode;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFFloat;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * ACOUSTICPROPERTIES.java
 * Created on July 12, 2007, 3:05 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class ACOUSTICPROPERTIES extends X3DAppearanceChildNode // needs new X3D*Node class
{
  private String   description, descriptionDefault;
  private boolean  enabled,     enabledDefault;
  private SFFloat  absorption,  absorptionDefault;
  private SFFloat  diffuse,     diffuseDefault;
  private SFFloat  refraction,  refractionDefault;
  private SFFloat  specular,    specularDefault;
  
  public ACOUSTICPROPERTIES()
  {
  }

  @Override
  public String getDefaultContainerField()
  {
    return "acousticProperties";        // Should be handled in the X3D*Node hierarchy
  }

  @Override
  public String getElementName()
  {
    return ACOUSTICPROPERTIES_ELNAME;
  }

  @Override
  public void initialize()
  {
    description = descriptionDefault = ACOUSTICPROPERTIES_ATTR_DESCRIPTION_DFLT; 
    enabled     = enabledDefault     = Boolean.parseBoolean(ACOUSTICPROPERTIES_ATTR_ENABLED_DFLT);
    
    absorption = absorptionDefault = new SFFloat(ACOUSTICPROPERTIES_ATTR_ABSORPTION_DFLT,0.0f,1.0f);
    diffuse    = diffuseDefault    = new SFFloat(ACOUSTICPROPERTIES_ATTR_DIFFUSE_DFLT,   0.0f,1.0f);
    refraction = refractionDefault = new SFFloat(ACOUSTICPROPERTIES_ATTR_REFRACTION_DFLT,0.0f,1.0f);
    specular   = specularDefault   = new SFFloat(ACOUSTICPROPERTIES_ATTR_SPECULAR_DFLT,  0.0f,1.0f);
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return ACOUSTICPROPERTIESCustomizer.class;
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);

    org.jdom.Attribute attr = root.getAttribute(ACOUSTICPROPERTIES_ATTR_DESCRIPTION_NAME);
    if (attr != null)
      description = attr.getValue();
    attr = root.getAttribute(ACOUSTICPROPERTIES_ATTR_ENABLED_NAME);
    if (attr != null)
      enabled = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(ACOUSTICPROPERTIES_ATTR_ABSORPTION_NAME);
    if (attr != null)
      absorption = new SFFloat(attr.getValue(), 0.0f, 1.0f);
    attr = root.getAttribute(ACOUSTICPROPERTIES_ATTR_DIFFUSE_NAME);
    if (attr != null)
      diffuse = new SFFloat(attr.getValue(), 0.0f, 1.0f);
    attr = root.getAttribute(ACOUSTICPROPERTIES_ATTR_REFRACTION_NAME);
    if (attr != null)
      refraction = new SFFloat(attr.getValue(), 0.0f, 1.0f);
    attr = root.getAttribute(ACOUSTICPROPERTIES_ATTR_SPECULAR_NAME);
    if (attr != null)
      specular = new SFFloat(attr.getValue(), 0.0f, 1.0f);
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (ACOUSTICPROPERTIES_ATTR_DESCRIPTION_REQD || !description.equals(descriptionDefault)) {
      sb.append(" ");
      sb.append(ACOUSTICPROPERTIES_ATTR_DESCRIPTION_NAME);
      sb.append("='");
      sb.append(description);
      sb.append("'");
    }
    if (ACOUSTICPROPERTIES_ATTR_ENABLED_REQD || (enabled != enabledDefault)) {
      sb.append(" ");
      sb.append(ACOUSTICPROPERTIES_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }
    if (ACOUSTICPROPERTIES_ATTR_ABSORPTION_REQD || (absorption.getValue() != absorptionDefault.getValue())) {
      sb.append(" ");
      sb.append(ACOUSTICPROPERTIES_ATTR_ABSORPTION_NAME);
      sb.append("='");
      sb.append(absorption);
      sb.append("'");
    }
    if (ACOUSTICPROPERTIES_ATTR_DIFFUSE_REQD || (diffuse.getValue() != diffuseDefault.getValue())) {
      sb.append(" ");
      sb.append(ACOUSTICPROPERTIES_ATTR_DIFFUSE_NAME);
      sb.append("='");
      sb.append(diffuse);
      sb.append("'");
    }
    if (ACOUSTICPROPERTIES_ATTR_REFRACTION_REQD || (refraction.getValue() != refractionDefault.getValue())) {
      sb.append(" ");
      sb.append(ACOUSTICPROPERTIES_ATTR_REFRACTION_NAME);
      sb.append("='");
      sb.append(refraction);
      sb.append("'");
    }
    if (ACOUSTICPROPERTIES_ATTR_SPECULAR_REQD || (specular.getValue() != specularDefault.getValue())) {
      sb.append(" ");
      sb.append(ACOUSTICPROPERTIES_ATTR_SPECULAR_NAME);
      sb.append("='");
      sb.append(specular);
      sb.append("'");
    }
    return sb.toString();
  }

  public boolean isEnabled()
  {
    return enabled;
  }

  public void setEnabled(boolean enabled)
  {
    this.enabled = enabled;
  }
  
  public String getDescription()
  {
    return description;
  }

  public void setDescription(String newDescription)
  {
    this.description = newDescription;
  }
  
  public SFFloat getAbsorption()
  {
    return absorption;
  } 

  public void setAbsorption(SFFloat newAbsorption)
  {
    this.absorption = newAbsorption;
  }

  
  public SFFloat getDiffuse()
  {
    return diffuse;
  }

  public void setDiffuse(SFFloat newDiffuse)
  {
    this.diffuse = newDiffuse;
  }
  
  public SFFloat getRefraction()
  {
    return refraction;
  }

  public void setRefraction(SFFloat newRefraction)
  {
    this.refraction = newRefraction;
  }
  
  public SFFloat getSpecular()
  {
    return specular;
  }

  public void setSpecular(SFFloat newSpecular)
  {
    this.specular = newSpecular;
  }
}
