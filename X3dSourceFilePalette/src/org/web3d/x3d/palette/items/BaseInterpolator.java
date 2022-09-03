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
import org.web3d.x3d.types.X3DInterpolatorNode;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * TIMESENSOR.java
 * Created on August 16, 2007, 1:40 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public abstract class BaseInterpolator extends X3DInterpolatorNode
{
  private String key;
  private String keyValue;
  private String set_fraction;

  public BaseInterpolator() 
  {
  }
  
  public abstract String getElementName();
  public abstract Class<? extends BaseCustomizer> getCustomizer();
  
  public void initialize()
  {
    key          = BASEINTERPOLATOR_ATTR_KEY_DFLT;
    keyValue     = BASEINTERPOLATOR_ATTR_KEYVALUE_DFLT;
    set_fraction = BASEINTERPOLATOR_ATTR_SETFRACTION_DFLT;
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(BASEINTERPOLATOR_ATTR_KEY_NAME);
    if (attr != null)
      key = attr.getValue();
    attr = root.getAttribute(BASEINTERPOLATOR_ATTR_KEYVALUE_NAME);
    if (attr != null)
      keyValue = attr.getValue();
    attr = root.getAttribute(BASEINTERPOLATOR_ATTR_SETFRACTION_NAME);
    if (attr != null)
      set_fraction = attr.getValue();
  }
  
  @Override
  public String createAttributes()
  {
    StringBuffer sb = new StringBuffer();

    if (BASEINTERPOLATOR_ATTR_KEY_REQD || !key.equals(BASEINTERPOLATOR_ATTR_KEY_DFLT)) {
      sb.append(" ");
      sb.append(BASEINTERPOLATOR_ATTR_KEY_NAME);
      sb.append("='");
      sb.append(key);
      sb.append("'");
    }

    if (BASEINTERPOLATOR_ATTR_KEYVALUE_REQD || !keyValue.equals(BASEINTERPOLATOR_ATTR_KEYVALUE_DFLT)) {
      sb.append(" ");
      sb.append(BASEINTERPOLATOR_ATTR_KEYVALUE_NAME);
      sb.append("='");
      sb.append(keyValue);
      sb.append("'");
    }

    if (BASEINTERPOLATOR_ATTR_SETFRACTION_REQD || !set_fraction.equals(BASEINTERPOLATOR_ATTR_SETFRACTION_DFLT)) {
      sb.append(" ");
      sb.append(BASEINTERPOLATOR_ATTR_SETFRACTION_NAME);
      sb.append("='");
      sb.append(set_fraction);
      sb.append("'");
    }

    return sb.toString();
  }
    
  
  // Candidate for override
  protected String validateKey(String s)
  {
    return validateNumbers(s);
  }
  
  // Candidate for override
  protected String validateKeyValue(String s)
  {
    return validateNumbers(s);
  }
    
  public String getKey()
  {
    return key;
  }

  public void setKey(String key)
  {
    this.key = validateKey(key);
  }

  public String getKeyValue()
  {
    return keyValue;
  }

  public void setKeyValue(String keyValue)
  {
    this.keyValue = validateKeyValue(keyValue);
  }

  public String getSet_fraction()
  {
    return set_fraction;
  }

  public void setSet_fraction(String set_fraction)
  {
    this.set_fraction = set_fraction;
  }
}
