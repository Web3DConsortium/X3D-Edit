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
import static org.web3d.x3d.palette.X3DPaletteUtilities.escapeXmlCharacters;
import org.web3d.x3d.types.SceneGraphStructureNodeType;
import static org.web3d.x3d.types.X3DSchemaData.*;

//import org.netbeans.spi.palette.PaletteItemRegistration;

//@PaletteItemRegistration
//(
//    paletteid = "X3DPalette",
//    category = "1. X3D Model Structure and Metadata",
//    itemid = "5_UNIT",
//    icon32 = "org/web3d/x3d/palette/items/resources/UNIT32.png",
//    icon16 = "org/web3d/x3d/palette/items/resources/UNIT16.png",
//    body = "<unit name='FeetToMeters' category='length' conversionFactor='0.3048'/>",
//    name = "unit",
//    tooltip = "A unit statement defines data-conversion factors for typed values in a scene"
//)
// https://bits.netbeans.org/14/javadoc/org-netbeans-spi-palette/architecture-summary.html
// https://bits.netbeans.org/14/javadoc/org-netbeans-spi-palette/org/netbeans/spi/palette/PaletteItemRegistration.html

/**
 * UNIT.java
 * Created on March 14, 2007, 9:57 AM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class UNIT extends SceneGraphStructureNodeType
{
  private String category;
  private String name;
  private String conversionFactor;
  

  public UNIT()
  {
  }

  @Override
  public String getElementName()
  {
    return UNIT_ELNAME;
  }

  @Override
  public void initialize()
  {
    category         = UNIT_ATTR_CATEGORY_DFLT;
    name             = UNIT_ATTR_NAME_DFLT;
    conversionFactor = UNIT_ATTR_CONVERSIONFACTOR_DFLT;
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);

    org.jdom.Attribute attr = root.getAttribute(UNIT_ATTR_CATEGORY_NAME);
    if (attr != null)
      category = attr.getValue();
    attr = root.getAttribute(UNIT_ATTR_NAME_NAME);
    if (attr != null)
      name = attr.getValue();
    attr = root.getAttribute(UNIT_ATTR_CONVERSIONFACTOR_NAME);
    if (attr != null)
      conversionFactor = attr.getValue();
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return UNITCustomizer.class;
  }
  
  @Override
  public String buildDEF()
  {
    return "";
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (UNIT_ATTR_CATEGORY_REQD || !category.equals(UNIT_ATTR_CATEGORY_DFLT)) {
      sb.append(" ");
      sb.append(UNIT_ATTR_CATEGORY_NAME);
      sb.append("='");
      sb.append(category);
      sb.append("'");
    }
    if (UNIT_ATTR_CONVERSIONFACTOR_REQD || !conversionFactor.equals(UNIT_ATTR_CONVERSIONFACTOR_DFLT)) {
      sb.append(" ");
      sb.append(UNIT_ATTR_CONVERSIONFACTOR_NAME);
      sb.append("='");
      sb.append(conversionFactor);
      sb.append("'");
    }
    if (UNIT_ATTR_NAME_REQD || !name.equals(UNIT_ATTR_NAME_DFLT)) {
      sb.append(" ");
      sb.append(UNIT_ATTR_NAME_NAME);
      sb.append("='");
      sb.append(escapeXmlCharacters(name));
      sb.append("'");
    }
    return sb.toString();
  }

  public String getCategory()
  {
    return category;
  }
  
  public void setCategory(String newCategory)
  {
    category = newCategory;
  }
  
  public String getName()
  {
    return name;
  }

  public void setName(String newName)
  {
    this.name = newName;
  }

  public String getConversionFactor()
  {
    return conversionFactor;
  }
  
  public void setConversionFactor (String newConversionFactor)
  {
    conversionFactor = newConversionFactor;
  }

  @Override
  public String getContent()
  {
    return ""; // no contained content for this element
  }
  
  @Override
  public void setContent(String newContainedContent)
  {
    // no contained content for this element
  }

}
