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
import org.web3d.x3d.types.X3DChildNode;

import static org.web3d.x3d.types.X3DSchemaData.*;


/**
 * BOOLEANTOGGLE.java
 * Created on Sep 12, 2007, 2:46 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey and Don Brutzman
 * @version $Id$
 */
public class BOOLEANTOGGLE extends X3DChildNode
{
  private boolean toggle;
  
  public BOOLEANTOGGLE()
  {
      this.setTraceEventsSelectionAvailable(true);
      this.setTraceEventsTooltip("Trace " + getElementName() + " events on X3D browser console");
  }
  
  // No attributes
  @Override
  public void initialize()
  {  
        setToggle(Boolean.parseBoolean(BOOLEANTOGGLE_ATTR_TOGGLE_DFLT));
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(BOOLEANTOGGLE_ATTR_TOGGLE_NAME);
    if (attr != null)
        setToggle(Boolean.parseBoolean(attr.getValue()));
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return BOOLEANTOGGLECustomizer.class;
  }
  
  @Override
  public final String getElementName()
  {
    return BOOLEANTOGGLE_ELNAME;
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    
    if (BOOLEANTOGGLE_ATTR_TOGGLE_REQD || isToggle() != Boolean.parseBoolean(BOOLEANTOGGLE_ATTR_TOGGLE_DFLT)) {
      sb.append(" ");
      sb.append(BOOLEANTOGGLE_ATTR_TOGGLE_NAME);
      sb.append("='");
      sb.append(isToggle());
      sb.append("'");
    }

    return sb.toString();
  }

    public boolean isToggle() {
        return toggle;
    }

    public void setToggle(boolean toggle) {
        this.toggle = toggle;
    }
}
