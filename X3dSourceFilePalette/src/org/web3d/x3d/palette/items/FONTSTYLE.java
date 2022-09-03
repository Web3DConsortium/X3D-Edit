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
import org.web3d.x3d.types.X3DFontStyleNode;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFFloat;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * FONTSTYLE.java
 * Created on August 2, 2007, 2:53 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class FONTSTYLE extends X3DFontStyleNode
{
  private String family;
  private boolean horizontal;
  private String justify;
  private String language;
  private boolean leftToRight;
  private SFFloat size, sizeDefault;
  private SFFloat spacing, spacingDefault;
  private String style;
  private boolean topToBottom;

  public FONTSTYLE()
  {
  }

  @Override
  public String getElementName()
  {
    return FONTSTYLE_ELNAME;
  }

  @Override
  public void initialize()
  {
    family   = FONTSTYLE_ATTR_FAMILY_DFLT;
    style    = FONTSTYLE_ATTR_STYLE_DFLT;
    language = FONTSTYLE_ATTR_LANGUAGE_DFLT;

    // "MIDDLE" "MIDDLE" preferred over FONTSTYLE_ATTR_JUSTIFY_DFLT1 FONTSTYLE_ATTR_JUSTIFY_DFLT2 ("BEGIN" "FIRST"),
    // but must properly initialize to match empty FontStyle in scene
    // meanwhile this is the first initialization, can't use accessor methods yet, so set justify string directly (note no quotes internally)
    justify = "MIDDLE MIDDLE"; 
    // These preferred values remain for a FontStyle dragged/dropped from palette, while FontStyle read from scene will respect specification defaults

    horizontal  = Boolean.parseBoolean(FONTSTYLE_ATTR_HORIZONTAL_DFLT);
    leftToRight = Boolean.parseBoolean(FONTSTYLE_ATTR_LEFTTORIGHT_DFLT);
    topToBottom = Boolean.parseBoolean(FONTSTYLE_ATTR_TOPTOBOTTOM_DFLT);

    size    = sizeDefault    = makeSize(FONTSTYLE_ATTR_SIZE_DFLT);
    spacing = spacingDefault = makeSpacing(FONTSTYLE_ATTR_SPACING_DFLT);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);

    org.jdom.Attribute attr = root.getAttribute(FONTSTYLE_ATTR_FAMILY_NAME);
    if(attr != null)
      family   = attr.getValue();
    
    attr = root.getAttribute(FONTSTYLE_ATTR_JUSTIFY_NAME);
    if(attr != null)
      justify   = concatStringArray(parseMFStringIntoStringArray(attr.getValue(),true));
    else // restore specification defaults since initializing from scene
    {
        setSingleJustifyValue(0, FONTSTYLE_ATTR_JUSTIFY_DFLT1);
        setSingleJustifyValue(1, FONTSTYLE_ATTR_JUSTIFY_DFLT2);
    }

    attr = root.getAttribute(FONTSTYLE_ATTR_STYLE_NAME);
    if(attr != null)
      style   = attr.getValue();
    attr = root.getAttribute(FONTSTYLE_ATTR_LANGUAGE_NAME);
    if(attr != null)
      language   = attr.getValue();

    attr = root.getAttribute(FONTSTYLE_ATTR_HORIZONTAL_NAME);
    if(attr != null)
      horizontal  = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(FONTSTYLE_ATTR_LEFTTORIGHT_NAME);
    if(attr != null)
      leftToRight = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(FONTSTYLE_ATTR_TOPTOBOTTOM_NAME);
    if(attr != null)
      topToBottom = Boolean.parseBoolean(attr.getValue());

    attr = root.getAttribute(FONTSTYLE_ATTR_SIZE_NAME);
    if(attr != null)
      size      = makeSize(attr.getValue());
    attr = root.getAttribute(FONTSTYLE_ATTR_SPACING_DFLT);
    if(attr != null)
      spacing  = makeSpacing(attr.getValue());
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return FONTSTYLECustomizer.class;
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (FONTSTYLE_ATTR_FAMILY_REQD || !family.replace("\"", "").equals(FONTSTYLE_ATTR_FAMILY_DFLT)) {
      sb.append(" ");
      sb.append(FONTSTYLE_ATTR_FAMILY_NAME);
      sb.append("='");
      sb.append(family); // conversion to MFString handled by customizer
      sb.append("'");
    }
    if (FONTSTYLE_ATTR_HORIZONTAL_REQD || horizontal != Boolean.parseBoolean(FONTSTYLE_ATTR_HORIZONTAL_DFLT)) {
      sb.append(" ");
      sb.append(FONTSTYLE_ATTR_HORIZONTAL_NAME);
      sb.append("='");
      sb.append(horizontal);
      sb.append("'");
    }
    if (FONTSTYLE_ATTR_JUSTIFY_REQD ||
        !(justify.equals(FONTSTYLE_ATTR_JUSTIFY_DFLT1) ||
          justify.equals(FONTSTYLE_ATTR_JUSTIFY_DFLT1 + " " + FONTSTYLE_ATTR_JUSTIFY_DFLT2))) {
      String[]sa = justify.replace("\"", "").replace(',', ' ').trim().split("\\s", 2);
      sb.append(" ");
      sb.append(FONTSTYLE_ATTR_JUSTIFY_NAME);
      sb.append("='");
      sb.append("\"");
      sb.append(sa[0]);
      sb.append("\"");
      if(sa.length >= 2) {
        sb.append(" \"");
        sb.append(sa[1]);
        sb.append("\"");
      }
      sb.append("'");
    }
    if (FONTSTYLE_ATTR_LANGUAGE_REQD || !language.equals(FONTSTYLE_ATTR_LANGUAGE_DFLT)) {
      sb.append(" ");
      sb.append(FONTSTYLE_ATTR_LANGUAGE_NAME);
      sb.append("='");
      sb.append(language);
      sb.append("'");
    }
    if (FONTSTYLE_ATTR_LEFTTORIGHT_REQD || leftToRight != Boolean.parseBoolean(FONTSTYLE_ATTR_LEFTTORIGHT_DFLT)) {
      sb.append(" ");
      sb.append(FONTSTYLE_ATTR_LEFTTORIGHT_NAME);
      sb.append("='");
      sb.append(leftToRight);
      sb.append("'");
    }
    if (FONTSTYLE_ATTR_SIZE_REQD || !size.equals(sizeDefault)) {
      sb.append(" ");
      sb.append(FONTSTYLE_ATTR_SIZE_NAME);
      sb.append("='");
      sb.append(size);
      sb.append("'");
    }
    if (FONTSTYLE_ATTR_SPACING_REQD || !spacing.equals(spacingDefault)) {
      sb.append(" ");
      sb.append(FONTSTYLE_ATTR_SPACING_NAME);
      sb.append("='");
      sb.append(spacing);
      sb.append("'");
    }
    if (FONTSTYLE_ATTR_STYLE_REQD || !style.equals(FONTSTYLE_ATTR_STYLE_DFLT)) {
      sb.append(" ");
      sb.append(FONTSTYLE_ATTR_STYLE_NAME);
      sb.append("='");
      sb.append(style);
      sb.append("'");
    }
    if (FONTSTYLE_ATTR_TOPTOBOTTOM_REQD || topToBottom != Boolean.parseBoolean(FONTSTYLE_ATTR_TOPTOBOTTOM_DFLT)) {
      sb.append(" ");
      sb.append(FONTSTYLE_ATTR_TOPTOBOTTOM_NAME);
      sb.append("='");
      sb.append(topToBottom);
      sb.append("'");
    }
    return sb.toString();
  }

  private String[] getJustifyValues()
  {
    String[] sa = justify.replace(',', ' ').trim().split("\\s",2);
    if(sa.length < 2) {
      String[]ssa = new String[2];
      ssa[0] = sa[0];
      ssa[1] = "";
      return ssa;
    }
    return sa;
  }

  private void setSingleJustifyValue(int i, String s)
  {
    String[] sa = getJustifyValues();
    sa[i] = s;
    justify = sa[0] + " " + sa[1];
  }

  // Accessors for customizer
  public String getFamily()
  {
    return family;
  }

  public void setFamily(String family)
  {
    this.family = family;
  }

  public String getMajorJustify()
  {
    return getJustifyValues()[0];
  }

  public void setMajorJustify(String justify)
  {
    setSingleJustifyValue(0,justify);
  }

  public String getMinorJustify()
  {
    return getJustifyValues()[1];
  }

  public void setMinorJustify(String justify)
  {
    setSingleJustifyValue(1,justify);
  }

  public String getStyle()
  {
    return style;
  }

  public void setStyle(String style)
  {
    this.style = style;
  }

  public String getLanguage()
  {
    return language;
  }

  public void setLanguage(String language)
  {
    this.language = language;
  }

  public boolean isHorizontal()
  {
    return horizontal;
  }

  public void setHorizontal(boolean horizontal)
  {
    this.horizontal = horizontal;
  }

  public boolean isLeftToRight()
  {
    return leftToRight;
  }

  public void setLeftToRight(boolean leftToRight)
  {
    this.leftToRight = leftToRight;
  }

  public boolean isTopToBottom()
  {
    return topToBottom;
  }

  public void setTopToBottom(boolean topToBottom)
  {
    this.topToBottom = topToBottom;
  }

  public String getSize()
  {
    return size.toString();
  }

  public void setSize(String size)
  {
    this.size = makeSize(size);
  }

  public String getSpacing()
  {
    return spacing.toString();
  }

  public void setSpacing(String spacing)
  {
    this.spacing = makeSpacing(spacing);
  }

  // Utilities
  private SFFloat makeSize(String s)
  {
    return new SFFloat(s,0.0f,null);
  }

  private SFFloat makeSpacing(String s)
  {
    return new SFFloat(s,0.0f,null);
  }
  }
