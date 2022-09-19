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

//import org.netbeans.spi.palette.PaletteItemRegistration;

//@PaletteItemRegistration
//(
//    paletteid = "X3DPalette",
//    category = "1. X3D Model Structure and Metadata",
//    itemid = "METADATASET",
//    icon32 = "org/web3d/x3d/palette/items/resources/METADATASET32.png",
//    icon16 = "org/web3d/x3d/palette/items/resources/METADATASET16.png",
//    body = "<MetadataSet name='moreMetadataNodes'><!-- Additional Metadata* nodes with containerField='value' go here --></MetadataSet>",
//    name = "MetadataSet",
//    tooltip = "MetadataSet provides a typed list of values providing metadata information about its parent node"
//)
// https://bits.netbeans.org/14/javadoc/org-netbeans-spi-palette/architecture-summary.html
// https://bits.netbeans.org/14/javadoc/org-netbeans-spi-palette/org/netbeans/spi/palette/PaletteItemRegistration.html

/**
 * METADATASET.java
 * Created on March 14, 2007, 9:57 AM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class METADATASET extends X3DMetadataObject
{
  private String name;
  private String reference;
  // no value element since MetadataSet only contains other Metadata nodes

  public METADATASET()
  {
  }

  @Override
  public String getElementName()
  {
    return METADATASET_ELNAME;
  }

  @Override
  public void initialize()
  {
    name      = METADATASET_ATTR_NAME_DFLT;
    reference = METADATASET_ATTR_REFERENCE_DFLT;
    
    this.setContent("\n\t\t\t<!-- enter other Metadata nodes here -->\n\t\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);

    org.jdom.Attribute attr = root.getAttribute(METADATASET_ATTR_NAME_NAME);
    if (attr != null)
      name = attr.getValue();
    attr = root.getAttribute(METADATASET_ATTR_REFERENCE_NAME);
    if (attr != null)
      reference = attr.getValue();
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return METADATASETCustomizer.class;
  }
  
//  @Override
//  public String buildDEF()
//  {
//    return "";
//  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (METADATASET_ATTR_NAME_REQD || !name.equals(METADATASET_ATTR_NAME_DFLT)) {
      sb.append(" ");
      sb.append(METADATASET_ATTR_NAME_NAME);
      sb.append("='");
      sb.append(escapeXmlCharacters(name));
      sb.append("'");
    }
    if (METADATASET_ATTR_REFERENCE_REQD || !reference.equals(METADATASET_ATTR_REFERENCE_DFLT)) {
      sb.append(" ");
      sb.append(METADATASET_ATTR_REFERENCE_NAME);
      sb.append("='");
      sb.append(escapeXmlCharacters(reference));
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

  public String getReference()
  {
    return reference;
  }
  
  public void setReference(String newReference)
  {
    reference = newReference;
  }  
}
