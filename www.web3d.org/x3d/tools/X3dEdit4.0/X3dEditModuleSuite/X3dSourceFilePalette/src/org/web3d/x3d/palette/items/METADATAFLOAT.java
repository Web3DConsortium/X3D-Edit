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
import org.web3d.x3d.types.X3DPrimitiveTypes.SFFloat;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

//import org.netbeans.spi.palette.PaletteItemRegistration;

//@PaletteItemRegistration
//(
//    paletteid = "X3DPalette",
//    category = "1. X3D Model Structure and Metadata",
//    itemid = "METADATAFLOAT",
//    icon32 = "org/web3d/x3d/palette/items/resources/METADATAFLOAT32.png",
//    icon16 = "org/web3d/x3d/palette/items/resources/METADATAFLOAT16.png",
//    body = "<MetadataFloat name='someValues' value='1.0 2.0 3.0'/>",
//    name = "MetadataFloat",
//    tooltip = "MetadataFloat provides a typed list of values providing metadata information about its parent node"
//)
// https://bits.netbeans.org/14/javadoc/org-netbeans-spi-palette/architecture-summary.html
// https://bits.netbeans.org/14/javadoc/org-netbeans-spi-palette/org/netbeans/spi/palette/PaletteItemRegistration.html

/**
 * METADATAFLOAT.java
 * Created on March 14, 2007, 9:57 AM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class METADATAFLOAT extends X3DMetadataObject
{
  private String    name;
  private String    reference;
  private SFFloat[] value, defaultValue;
  private boolean   insertCommas, insertLineBreaks = false;

  public METADATAFLOAT()
  {
  }

  @Override
  public String getElementName()
  {
    return METADATAFLOAT_ELNAME;
  }

  @Override
  public void initialize()
  {
    name      = METADATAFLOAT_ATTR_NAME_DFLT;
    reference = METADATAFLOAT_ATTR_REFERENCE_DFLT;

    String[] sa;
    if(METADATAFLOAT_ATTR_VALUE_DFLT == null || METADATAFLOAT_ATTR_VALUE_DFLT.length()<=0)
      sa = new String[]{}; // empty
    else
      sa = parseX(METADATAFLOAT_ATTR_VALUE_DFLT);
    value = defaultValue = parseToSFFloatArray(sa);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);

    org.jdom.Attribute attr = root.getAttribute(METADATAFLOAT_ATTR_NAME_NAME);
    if (attr != null)
      name = attr.getValue();
    attr = root.getAttribute(METADATAFLOAT_ATTR_REFERENCE_NAME);
    if (attr != null)
      reference = attr.getValue();
    attr = root.getAttribute(METADATAFLOAT_ATTR_VALUE_NAME);
    if (attr != null) {
      String[] sa = parseX(attr.getValue());
      value = parseToSFFloatArray(sa);
      if (attr.getValue().contains(","))  insertCommas     = true;
      if (attr.getValue().contains("\n") ||
          attr.getValue().contains("\r")) insertLineBreaks = true; // TODO not working, line breaks not being passed from JDOM
      if (insertCommas)                   insertLineBreaks = true; // workaround default, if commas were present then most likely lineBreaks also
    }
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return METADATAFLOATCustomizer.class;
  }
  
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (METADATAFLOAT_ATTR_NAME_REQD || !name.equals(METADATAFLOAT_ATTR_NAME_DFLT)) {
      sb.append(" ");
      sb.append(METADATAFLOAT_ATTR_NAME_NAME);
      sb.append("='");
      sb.append(escapeXmlCharacters(name));
      sb.append("'");
    }
    if (METADATAFLOAT_ATTR_REFERENCE_REQD || !reference.equals(METADATAFLOAT_ATTR_REFERENCE_DFLT)) {
      sb.append(" ");
      sb.append(METADATAFLOAT_ATTR_REFERENCE_NAME);
      sb.append("='");
      sb.append(escapeXmlCharacters(reference));
      sb.append("'");
    }
    if (METADATAFLOAT_ATTR_VALUE_REQD || !arraysIdenticalOrNull(value,defaultValue)) {
      sb.append(" ");
      sb.append(METADATAFLOAT_ATTR_VALUE_NAME);
      sb.append("='");
      sb.append(formatFloatArray(value, insertCommas, insertLineBreaks));
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

  public String[][] getValueArray()
  {
      String[][] stringArray = new String[value.length][1];
      for (int i = 0; i < value.length; i++)
           stringArray[i][0] = value[i].toString();
    return stringArray;
  }

  public void setValueArray(String[] newValue)
  {
      if ((newValue == null) || (newValue.length == 0) || ((newValue.length == 1) && newValue[0].isEmpty()))
      {
          value = defaultValue;
      }
      else
      {
          value = new SFFloat [newValue.length];
          for (int i = 0; i < newValue.length; i++)
               value[i] = new SFFloat(newValue[i]);
      }
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
