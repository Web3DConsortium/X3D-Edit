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
import org.web3d.x3d.types.X3DGeometryNode;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * HANIMDISPLACER.java
 * Created on 2 May 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey and Don Brutzman
 * @version $Id$
 */
public class HANIMDISPLACER extends X3DGeometryNode
{
  private SFInt32[]   coordIndex;
  private SFFloat[] displacements;
  private String    name;
  private SFFloat   weight, weightDefault;

  public HANIMDISPLACER()
  {    
  }

  @Override
  public String getElementName()
  {
    return HANIMDISPLACER_ELNAME;
  }

  @Override
  public void initialize()
  {
    String[] sa;
    if(HANIMDISPLACER_ATTR_COORDINDEX_DFLT == null || HANIMDISPLACER_ATTR_COORDINDEX_DFLT.length()<=0)
      sa = new String[]{}; // empty 
    else
      sa = parseX(HANIMDISPLACER_ATTR_COORDINDEX_DFLT);
    coordIndex = parseToSFIntArray(sa); 
    
    if(HANIMDISPLACER_ATTR_DISPLACEMENTS_DFLT == null || HANIMDISPLACER_ATTR_DISPLACEMENTS_DFLT.length()<=0)
      sa = new String[]{}; // empty 
    else
      sa = parseX(HANIMDISPLACER_ATTR_DISPLACEMENTS_DFLT);
    displacements = parseToSFFloatArray(sa); 
    
    name    = HANIMDISPLACER_ATTR_NAME_DFLT;
    weight  = weightDefault     = new SFFloat(HANIMDISPLACER_ATTR_WEIGHT_DFLT);
    
    // Does not contain children nodes (except for Metadata)
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(HANIMDISPLACER_ATTR_COORDINDEX_NAME);
    if (attr != null){
      String[] sa = parseX(attr.getValue());
      coordIndex = parseToSFIntArray(sa);
    }
    attr = root.getAttribute(HANIMDISPLACER_ATTR_DISPLACEMENTS_NAME);
    if (attr != null){
      String[] sa = parseX(attr.getValue());
      displacements = parseToSFFloatArray(sa);
    }
    attr = root.getAttribute(HANIMDISPLACER_ATTR_NAME_NAME);
    if (attr != null)
      name = attr.getValue();
    attr = root.getAttribute(HANIMDISPLACER_ATTR_WEIGHT_NAME);
    if (attr != null)
      weight = new SFFloat(attr.getValue());
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    if (HANIMDISPLACER_ATTR_NAME_REQD || !name.equalsIgnoreCase(HANIMDISPLACER_ATTR_NAME_DFLT)) {
      sb.append(" ");
      sb.append(HANIMDISPLACER_ATTR_NAME_NAME);
      sb.append("='");
      sb.append(escapeXmlCharacters(name));
      sb.append("'");
    }
    if (HANIMDISPLACER_ATTR_COORDINDEX_REQD || coordIndex.length > 0) {
      sb.append(" ");
      sb.append(HANIMDISPLACER_ATTR_COORDINDEX_NAME);
      sb.append("='");
      sb.append(getCoordIndex());
      sb.append("'");
    }
    if (HANIMDISPLACER_ATTR_DISPLACEMENTS_REQD || displacements.length > 0) {
      sb.append(" ");
      sb.append(HANIMDISPLACER_ATTR_DISPLACEMENTS_NAME);
      sb.append("='");
      sb.append(getDisplacements());
      sb.append("'");
    }
    if (HANIMDISPLACER_ATTR_WEIGHT_REQD || (weight.getValue() != weightDefault.getValue())) {
      sb.append(" ");
      sb.append(HANIMDISPLACER_ATTR_WEIGHT_NAME);
      sb.append("='");
      sb.append(weight);
      sb.append("'");
    }
    return sb.toString();
  }

  public String getCoordIndex()
  {
    return formatIntArray(coordIndex);
  }

  public void setCoordIndex(String newCoordIndex)
  {
    String[] sa;
    if(newCoordIndex.length()<=0)
      sa = new String[]{}; // empty 
    else
      sa = parseX(newCoordIndex);
    this.coordIndex = parseToSFIntArray(sa); 
  }

  public String getDisplacements()
  {
    return formatFloatArray(displacements);
  }

  public void setDisplacements(String newDisplacements)
  {
    String[] sa;
    if(newDisplacements.length()<=0)
      sa = new String[]{}; // empty 
    else
      sa = parseX(newDisplacements);
    this.displacements = parseToSFFloatArray(sa);
  }

  public String getWeight()
  {
    return weight.toString();
  }

  public void setWeight(String newWeight)
  {
    this.weight = new SFFloat(newWeight);
  }


  public String getName()
  {
    return name;
  }

  public void setName(String newName)
  {
    this.name = newName;
  }

}
