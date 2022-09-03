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

import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * LINEPROPERTIES.java
 * Created on Sep 6, 2007, 3:05 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class LINEPROPERTIES extends X3DAppearanceChildNode  // needs new X3D*Node class
{
  private boolean applied,              appliedDefault;
  private SFInt32 linetype,             linetypeDefault;
  private SFFloat linewidthScaleFactor, linewidthScaleFactorDefault;
  
  public LINEPROPERTIES()
  {
  }
  
  @Override
  public String getDefaultContainerField()
  {
    return "lineProperties";        // Should be handled in the X3D*Node hierarchy
  }

  @Override
  public String getElementName()
  {
    return LINEPROPERTIES_ELNAME;
  }

  @Override
  public void initialize()
  {
    applied              = appliedDefault              = Boolean.parseBoolean(LINEPROPERTIES_ATTR_APPLIED_DFLT); 
    linetype             = linetypeDefault             = new SFInt32(LINEPROPERTIES_ATTR_LINETYPE_DFLT,1,null,true);
    linewidthScaleFactor = linewidthScaleFactorDefault = new SFFloat(LINEPROPERTIES_ATTR_LINEWIDTHSCALEFACTOR_DFLT);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);

    org.jdom.Attribute attr = root.getAttribute(LINEPROPERTIES_ATTR_APPLIED_NAME);
    if (attr != null)
      applied = Boolean.parseBoolean(attr.getValue()); 
    attr = root.getAttribute(LINEPROPERTIES_ATTR_LINETYPE_NAME);
    if (attr != null)
      linetype = new SFInt32(attr.getValue(),1,null,true);
    attr = root.getAttribute(LINEPROPERTIES_ATTR_LINEWIDTHSCALEFACTOR_NAME);
    if (attr != null)
      linewidthScaleFactor = new SFFloat(attr.getValue());
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return LINEPROPERTIESCustomizer.class;
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    if (LINEPROPERTIES_ATTR_APPLIED_REQD || applied != Boolean.parseBoolean(LINEPROPERTIES_ATTR_APPLIED_DFLT)) {
      sb.append(" ");
      sb.append(LINEPROPERTIES_ATTR_APPLIED_NAME);
      sb.append("='");
      sb.append(applied);
      sb.append("'");
    }
    if (LINEPROPERTIES_ATTR_LINETYPE_REQD || !linetype.equals(linetypeDefault)) {
      sb.append(" ");
      sb.append(LINEPROPERTIES_ATTR_LINETYPE_NAME);
      sb.append("='");
      sb.append(linetype);
      sb.append("'");
    }
    setContent("\n\t\t\t<!--"+LINEPROPERTIES_ATTR_LINETYPE_CHOICES[linetype.getValue() - 1]+"-->\n\t\t");
    
    if (LINEPROPERTIES_ATTR_LINEWIDTHSCALEFACTOR_REQD || !linewidthScaleFactor.equals(linewidthScaleFactorDefault)) {
      sb.append(" ");
      sb.append(LINEPROPERTIES_ATTR_LINEWIDTHSCALEFACTOR_NAME);
      sb.append("='");
      sb.append(linewidthScaleFactor);
      sb.append("'");
    }
    return sb.toString();
  }

  public boolean isApplied()
  {
    return applied;
  }

  public void setApplied(boolean applied)
  {
    this.applied = applied;
  }

  public String getLinetype()
  {
    return linetype.toString();
  }

  public void setLinetype(int newLinetype)
  {
    this.linetype.setValue(newLinetype);
  }

  public String getLinewidthScaleFactor()
  {
    return linewidthScaleFactor.toString();
  }

  public void setLinewidthScaleFactor(String linewidthScaleFactor)
  {
    this.linewidthScaleFactor = new SFFloat(linewidthScaleFactor);
  }
  
}
