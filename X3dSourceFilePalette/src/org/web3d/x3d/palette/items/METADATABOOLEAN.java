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
import org.web3d.x3d.types.X3DMetadataObject;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

//import org.netbeans.spi.palette.PaletteItemRegistration;

//@PaletteItemRegistration
//(
//    paletteid = "X3DPalette",
//    category = "1. X3D Model Structure and Metadata",
//    itemid = "METADATABOOLEAN",
//    icon32 = "org/web3d/x3d/palette/items/resources/METADATABOOLEAN32.png",
//    icon16 = "org/web3d/x3d/palette/items/resources/METADATABOOLEAN16.png",
//    body = "<MetadataBoolean name='someValues' value='true false'/>",
//    name = "MetadataBoolean",
//    tooltip = "MetadataBoolean provides a typed list of values providing metadata information about its parent node"
//)
// https://bits.netbeans.org/14/javadoc/org-netbeans-spi-palette/architecture-summary.html
// https://bits.netbeans.org/14/javadoc/org-netbeans-spi-palette/org/netbeans/spi/palette/PaletteItemRegistration.html

/**
 * METADATABOOLEAN.java
 * Created on March 14, 2007, 9:57 AM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class METADATABOOLEAN extends X3DMetadataObject
{
  private String    name;
  private String    reference;
  private boolean[] value, valueDefault;
  private boolean   insertCommas, insertLineBreaks = false;

  public METADATABOOLEAN()
  {
  }

    @Override
  public String getElementName()
  {
    return METADATABOOLEAN_ELNAME;
  }

  @Override
  public void initialize()
  {
    name      = METADATABOOLEAN_ATTR_NAME_DFLT;
    reference = METADATABOOLEAN_ATTR_REFERENCE_DFLT;
//    value     = parseMFStringIntoStringArray(METADATABOOLEAN_ATTR_VALUE_DFLT);
    String[] sa;
    if(METADATABOOLEAN_ATTR_VALUE_DFLT == null || METADATABOOLEAN_ATTR_VALUE_DFLT.length()<=0)
      sa = new String[]{}; // empty
    else
      sa = parseX(METADATABOOLEAN_ATTR_VALUE_DFLT);
    value = valueDefault = parseToBooleanArray(sa);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);

    org.jdom.Attribute attr = root.getAttribute(METADATABOOLEAN_ATTR_NAME_NAME);
    if (attr != null)
      name = attr.getValue();
    attr = root.getAttribute(METADATABOOLEAN_ATTR_REFERENCE_NAME);
    if (attr != null)
      reference = attr.getValue();
    attr = root.getAttribute(METADATABOOLEAN_ATTR_VALUE_NAME);
    if (attr != null) {
      String[] sa = parseX(attr.getValue());
      value = parseToBooleanArray(sa);
      if (attr.getValue().contains(","))  insertCommas     = true;
      if (attr.getValue().contains("\n") ||
          attr.getValue().contains("\r")) insertLineBreaks = true; // TODO not working, line breaks not being passed from JDOM
      if (insertCommas)                   insertLineBreaks = true; // workaround default, if commas were present then most likely lineBreaks also
    }
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return METADATABOOLEANCustomizer.class;
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (METADATABOOLEAN_ATTR_NAME_REQD || !name.equals(METADATABOOLEAN_ATTR_NAME_DFLT)) {
      sb.append(" ");
      sb.append(METADATABOOLEAN_ATTR_NAME_NAME);
      sb.append("='");
      sb.append(escapeXmlCharacters(name));
      sb.append("'");
    }
    if (METADATABOOLEAN_ATTR_REFERENCE_REQD || !reference.equals(METADATABOOLEAN_ATTR_REFERENCE_DFLT)) {
      sb.append(" ");
      sb.append(METADATABOOLEAN_ATTR_REFERENCE_NAME);
      sb.append("='");
      sb.append(escapeXmlCharacters(reference));
      sb.append("'");
    }
    if (METADATABOOLEAN_ATTR_VALUE_REQD || !arraysIdenticalOrNull(value,valueDefault)) {
      sb.append(" ");
      sb.append(METADATABOOLEAN_ATTR_VALUE_NAME);
      sb.append("='");
      sb.append(formatBooleanArray(value, insertCommas, insertLineBreaks));
      sb.append("'");
    }
    
    return sb.toString();
  }
  
  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public boolean[] getValueArray()
  {
    return value;
  }

  public Object[][] getBooleanValueObjectArray()
  {
    if(value.length == 0)
      return new Object[][] {{}}; // nothing to start with

    Object[][] oaa = new Object[value.length][1];
    
    for (int i = 0; i < value.length; i++)
    {
           oaa[i][0] = value[i];
    }
    return oaa;
  }

  public void setValueArray(boolean[] newValue)
  {
    value = newValue;
  }

  public String getReference()
  {
    return reference;
  }
  
  public void setReference(String newReference)
  {
    reference = newReference;
  }

    /**
     * @return the insertCommas value
     */
    public boolean isInsertCommas() {
        return insertCommas;
    }

    /**
     * @param insertCommas the insertCommas value to set
     */
    public void setInsertCommas(boolean insertCommas) {
        this.insertCommas = insertCommas;
    }

    /**
     * @return the insertLineBreaks value
     */
    public boolean isInsertLineBreaks() {
        return insertLineBreaks;
    }

    /**
     * @param insertLineBreaks the insertLineBreak values to set
     */
    public void setInsertLineBreaks(boolean insertLineBreaks) {
        this.insertLineBreaks = insertLineBreaks;
    }
}
