/*
Copyright (c) 1995-2026 held by the author(s).  All rights reserved.

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
      (https://www.nps.edu and https://MovesInstitute.nps.edu)
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

import static javax.management.Query.attr;
import javax.swing.text.JTextComponent;
import static org.openide.util.NbPreferences.root;
import org.web3d.x3d.types.X3DAppearanceNode;
import org.web3d.x3d.types.X3DPrimitiveTypes;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFFloat;

import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * APPEARANCE.java
 * Created on August 2, 2007, 2:46 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class APPEARANCE extends X3DAppearanceNode
{
    private SFFloat alphaCutoff, alphaCutoffDefault;
    private String  alphaMode;
    
    public APPEARANCE()
    {
    }

    @Override
    public void initialize()
    {
        setAlphaCutoff(alphaCutoffDefault = new SFFloat(APPEARANCE_ATTR_ALPHACUTOFF_DFLT, 0.0f, 1.0f));
        setAlphaMode(APPEARANCE_ATTR_ALPHAMODE_DFLT);
    }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(APPEARANCE_ATTR_ALPHACUTOFF_NAME);
    if (attr != null)
        setAlphaCutoff(new X3DPrimitiveTypes.SFFloat(attr.getValue(), 0.0f, 1.0f));
    attr = root.getAttribute(APPEARANCE_ATTR_ALPHAMODE_NAME);
    if(attr != null)
        setAlphaMode(attr.getValue());
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    
    if (APPEARANCE_ATTR_ALPHACUTOFF_REQD || !alphaCutoff.equals(alphaCutoffDefault)) {
      sb.append(" ");
      sb.append(APPEARANCE_ATTR_ALPHACUTOFF_NAME);
      sb.append("='");
      sb.append(getAlphaCutoff());
      sb.append("'");
    }
    if (APPEARANCE_ATTR_ALPHAMODE_REQD || !alphaMode.replace("\"", "").equals(APPEARANCE_ATTR_ALPHAMODE_DFLT)) {
      sb.append(" ");
      sb.append(APPEARANCE_ATTR_ALPHAMODE_NAME);
      sb.append("='");
      sb.append(getAlphaMode()); // conversion to MFString handled by customizer
      sb.append("'");
    }
    
    return sb.toString();
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return APPEARANCECustomizer.class;
  }

  @Override
  public String getElementName()
  {
    return APPEARANCE_ELNAME;
  }

    /**
     * @return the alphaCutoff
     */
    public SFFloat getAlphaCutoff() {
        return alphaCutoff;
    }

    /**
     * @param alphaCutoff the alphaCutoff to set
     */
    public void setAlphaCutoff(SFFloat alphaCutoff) {
        this.alphaCutoff = alphaCutoff;
    }

    /**
     * @return the alphaMode
     */
    public String getAlphaMode() {
        return alphaMode;
    }

    /**
     * @param alphaMode the alphaMode to set
     */
    public void setAlphaMode(String alphaMode) {
        this.alphaMode = alphaMode;
    }
}
