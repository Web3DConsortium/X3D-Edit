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
import org.web3d.x3d.types.X3DTextureNode;

import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * MULTITEXTURE.java
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class MULTITEXTURE extends X3DTextureNode
{
  private SFFloat alpha, alphaDefault;

  private SFFloat color0,  color0Default;
  private SFFloat color1,  color1Default;
  private SFFloat color2,  color2Default;

  private String function, functionDefault; // MFString
  private String mode,     modeDefault;     // MFString
  private String source,   sourceDefault;   // MFString
  
  public MULTITEXTURE()
  {
  }

  @Override
  public String getElementName()
  {
    return MULTITEXTURE_ELNAME;
  }

  @Override
  public void initialize()
  {
    alpha = alphaDefault = new SFFloat(MULTITEXTURE_ATTR_ALPHA_DFLT, 0.0f, 1.0f);

    String[] fa = parse3(MULTITEXTURE_ATTR_COLOR_DFLT);
    color0 = color0Default = new SFFloat(fa[0],0.0f,1.0f);
    color1 = color1Default = new SFFloat(fa[1],0.0f,1.0f);
    color2 = color2Default = new SFFloat(fa[2],0.0f,1.0f);

        mode =     modeDefault = MULTITEXTURE_ATTR_SOURCE_DFLT;
    function = functionDefault = MULTITEXTURE_ATTR_SOURCE_DFLT;
      source =   sourceDefault = MULTITEXTURE_ATTR_SOURCE_DFLT;

    setContent("\n\t\t<!--TODO add one or more texture nodes here\n\t\t\t<ImageTexture/>\n\t\t\t<MovieTexture/>\n\t\t\t<PixelTexture/> -->\n\t\t");
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return MULTITEXTURECustomizer.class;
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);

    org.jdom.Attribute attr = root.getAttribute(MULTITEXTURE_ATTR_ALPHA_NAME);
    if (attr != null)
      alpha = new SFFloat(attr.getValue(), 0.0f, 1.0f);

    attr = root.getAttribute(MULTITEXTURE_ATTR_COLOR_NAME);
    if (attr != null) {
      String[] fa = parse3(attr.getValue());
      color0 = new SFFloat(fa[0], 0.0f, 1.0f);
      color1 = new SFFloat(fa[1], 0.0f, 1.0f);
      color2 = new SFFloat(fa[2], 0.0f, 1.0f);
    }

    attr = root.getAttribute(MULTITEXTURE_ATTR_MODE_NAME);
    if (attr != null)
      mode = attr.getValue();

    attr = root.getAttribute(MULTITEXTURE_ATTR_FUNCTION_NAME);
    if (attr != null)
      function = attr.getValue();

    attr = root.getAttribute(MULTITEXTURE_ATTR_SOURCE_NAME);
    if (attr != null)
      source = attr.getValue();
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (MULTITEXTURE_ATTR_ALPHA_REQD || !alpha.equals(alphaDefault)) {
      sb.append(" ");
      sb.append(MULTITEXTURE_ATTR_ALPHA_NAME);
      sb.append("='");
      sb.append(alpha);
      sb.append("'");
    }
    if (MULTITEXTURE_ATTR_COLOR_REQD ||
           (!color0.equals(color0Default) ||
            !color1.equals(color1Default) ||
            !color2.equals(color2Default))) {
      sb.append(" ");
      sb.append(MULTITEXTURE_ATTR_COLOR_NAME);
      sb.append("='");
      sb.append(color0);
      sb.append(" ");
      sb.append(color1);
      sb.append(" ");
      sb.append(color2);
      sb.append("'");
    }
    if (MULTITEXTURE_ATTR_FUNCTION_REQD || !source.equals(functionDefault)) {
      sb.append(" ");
      sb.append(MULTITEXTURE_ATTR_FUNCTION_NAME);
      sb.append("='");
      sb.append(function);
      sb.append("'");
    }
    if (MULTITEXTURE_ATTR_MODE_REQD || !source.equals(modeDefault)) {
      sb.append(" ");
      sb.append(MULTITEXTURE_ATTR_MODE_NAME);
      sb.append("='");
      sb.append(mode);
      sb.append("'");
    }
    if (MULTITEXTURE_ATTR_SOURCE_REQD || !source.equals(sourceDefault)) {
      sb.append(" ");
      sb.append(MULTITEXTURE_ATTR_SOURCE_NAME);
      sb.append("='");
      sb.append(source);
      sb.append("'");
    }
    return sb.toString();
  }

  public String getAlpha()
  {
    return alpha.toString();
  }

  public void setAlpha(String alpha)
  {
    this.alpha = new SFFloat(alpha, 0.0f, 1.0f);
  }

  public String getColor0()
  {
    return color0.toString();
  }

  public void setColor0(String color0)
  {
    this.color0 = new SFFloat(color0,0.0f,1.0f);
  }

  public String getColor1()
  {
    return color1.toString();
  }

  public void setColor1(String color1)
  {
    this.color1 = new SFFloat(color1,0.0f,1.0f);
  }

  public String getColor2()
  {
    return color2.toString();
  }

  public void setColor2(String color2)
  {
    this.color2 = new SFFloat(color2,0.0f,1.0f);
  }

  public String getSource()
  {
    return source;
  }

  public void setSource(String newSource)
  {
    this.source = newSource;
  }

    /**
     * @return the function
     */
    public String getFunction()
    {
        return function;
    }

    /**
     * @param function the function to set
     */
    public void setFunction(String function)
    {
        this.function = function;
    }

    /**
     * @return the mode
     */
    public String getMode()
    {
        return mode;
    }

    /**
     * @param mode the mode to set
     */
    public void setMode(String mode)
    {
        this.mode = mode;
    }
}
