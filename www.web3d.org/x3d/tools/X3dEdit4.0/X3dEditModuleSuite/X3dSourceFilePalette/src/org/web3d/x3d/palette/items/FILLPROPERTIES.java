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
import static org.web3d.x3d.types.X3DSchemaData4.*;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * FILLPROPERTIES.java
 * Created on July 12, 2007, 3:05 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class FILLPROPERTIES extends X3DAppearanceChildNode // needs new X3D*Node class
{
  private boolean filled, hatched;
  private SFInt32 hatchStyle, hatchStyleDefault;
  private SFFloat color0, color0Default;
  private SFFloat color1, color1Default;
  private SFFloat color2, color2Default;
  
  public FILLPROPERTIES()
  {
  }

  @Override
  public String getDefaultContainerField()
  {
    return "fillProperties";        // Should be handled in the X3D*Node hierarchy
  }

  @Override
  public String getElementName()
  {
    return FILLPROPERTIES_ELNAME;
  }

  @Override
  public void initialize()
  {
    filled  = Boolean.parseBoolean(FILLPROPERTIES_ATTR_FILLED_DFLT); 
    hatched = Boolean.parseBoolean(FILLPROPERTIES_ATTR_HATCHED_DFLT); 
    
    hatchStyle = hatchStyleDefault = new SFInt32(FILLPROPERTIES_ATTR_HATCHSTYLE_DFLT, 0, null);
    
    String[] fa = parse3(FILLPROPERTIES_ATTR_HATCHCOLOR_DFLT);
    color0 = color0Default = new SFFloat(fa[0],0.0f,1.0f);
    color1 = color1Default = new SFFloat(fa[1],0.0f,1.0f);    
    color2 = color2Default = new SFFloat(fa[2],0.0f,1.0f);
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return FILLPROPERTIESCustomizer.class;
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);

    org.jdom.Attribute attr = root.getAttribute(FILLPROPERTIES_ATTR_FILLED_NAME);
    if (attr != null)
      filled = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(FILLPROPERTIES_ATTR_HATCHED_NAME);
    if (attr != null)
      hatched = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(FILLPROPERTIES_ATTR_HATCHSTYLE_NAME);
    if (attr != null)
      hatchStyle = new SFInt32(attr.getValue(), 0, null);
    attr = root.getAttribute(FILLPROPERTIES_ATTR_HATCHCOLOR_NAME);
    if (attr != null) {
      String[] fa = parse3(attr.getValue());
      color0 = new SFFloat(fa[0], 0.0f, 1.0f);
      color1 = new SFFloat(fa[1], 0.0f, 1.0f);
      color2 = new SFFloat(fa[2], 0.0f, 1.0f);
    }
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (FILLPROPERTIES_ATTR_FILLED_REQD || filled != Boolean.parseBoolean(FILLPROPERTIES_ATTR_FILLED_DFLT)) {
      sb.append(" ");
      sb.append(FILLPROPERTIES_ATTR_FILLED_NAME);
      sb.append("='");
      sb.append(filled);
      sb.append("'");
    }
    if (FILLPROPERTIES_ATTR_HATCHCOLOR_REQD ||
           (!color0.equals(color0Default) ||
            !color1.equals(color1Default) ||
            !color2.equals(color2Default))) {
      sb.append(" ");
      sb.append(FILLPROPERTIES_ATTR_HATCHCOLOR_NAME);
      sb.append("='");
      sb.append(color0);
      sb.append(" ");
      sb.append(color1);
      sb.append(" ");
      sb.append(color2);
      sb.append("'");
    }
    if (FILLPROPERTIES_ATTR_HATCHED_REQD || hatched != Boolean.parseBoolean(FILLPROPERTIES_ATTR_HATCHED_DFLT)) {
      sb.append(" ");
      sb.append(FILLPROPERTIES_ATTR_HATCHED_NAME);
      sb.append("='");
      sb.append(hatched);
      sb.append("'");
    }
    boolean dohatchstylecomment = false;
    if (FILLPROPERTIES_ATTR_HATCHSTYLE_REQD || !hatchStyle.equals(hatchStyleDefault)) {
      sb.append(" ");
      sb.append(FILLPROPERTIES_ATTR_HATCHSTYLE_NAME);
      sb.append("='");
      sb.append(hatchStyle);
      sb.append("'");
    }
    setContent("\n\t\t\t<!--"+FILLPROPERTIES_ATTR_HATCHSTYLE_CHOICES[hatchStyle.getValue() - 1]+"-->\n\t\t");
    return sb.toString();
  }

  public boolean isFilled()
  {
    return filled;
  }

  public void setFilled(boolean filled)
  {
    this.filled = filled;
  }
  
  public boolean isHatched()
  {
    return hatched;
  }

  public void setHatched(boolean hatched)
  {
    this.hatched = hatched;
  }

  public int getHatchStyle()
  {
    return hatchStyle.getValue();
  }

  public void setHatchStyle(int hatchStyle)
  {
    this.hatchStyle.setValue(hatchStyle);
  }

  public String getColor0()
  {
    return color0.toString();
  }

  public void setColor0(String color0)
  {
    this.color0 = new SFFloat( color0,0.0f,1.0f);
  }

  public String getColor1()
  {
    return color1.toString();
  }

  public void setColor1(String color1)
  {
    this.color1 = new SFFloat( color1,0.0f,1.0f);
  }

  public String getColor2()
  {
    return color2.toString();
  }

  public void setColor2(String color2)
  {
    this.color2 = new SFFloat( color2,0.0f,1.0f);
  }
}
