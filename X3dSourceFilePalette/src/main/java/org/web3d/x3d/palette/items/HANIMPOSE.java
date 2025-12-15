/*
Copyright (c) 1995-2025 held by the author(s).  All rights reserved.
 
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
import javax.swing.text.JTextComponent;
import static org.web3d.x3d.palette.X3DPaletteUtilities.escapeXmlCharacters;
import org.web3d.x3d.types.X3DGroupingNode;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFFloat;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFInt32;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * HANIMPOSE.java
 *
 * @author Don Brutzman
 * @version $Id$
 */
public class HANIMPOSE extends X3DGroupingNode
{
  private String  name;  
  private String  description;
  private boolean enabled, enabledDefault;
  private SFInt32 loa, loaDefault;
  private SFFloat transitionDuration, transitionDurationDefault;
  
  public HANIMPOSE()
  {    
  }

  @Override
  public String getElementName()
  {
    return HANIMPOSE_ELNAME;
  }

  @Override
  public void initialize()
  {
    description        = HANIMPOSE_ATTR_DESCRIPTION_DFLT;
    name               = HANIMPOSE_ATTR_NAME_DFLT;
    enabled            = enabledDefault            = Boolean.parseBoolean(HANIMPOSE_ATTR_ENABLED_DFLT);
    loa                = loaDefault                = new SFInt32(HANIMPOSE_ATTR_LOA_DFLT);
    transitionDuration = transitionDurationDefault = new SFFloat(HANIMPOSE_ATTR_TRANSITIONDURATION_DFLT);

//    if (getContent().length() == 0)
//        setContent("\n\t\t<!--TODO add child HAnimJoint, content nodes here-->\n\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  { 
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;
    
    attr = root.getAttribute(HANIMPOSE_ATTR_DESCRIPTION_NAME);
    if (attr != null)
      description = attr.getValue();
    attr = root.getAttribute(HANIMPOSE_ATTR_NAME_NAME);
    if (attr != null)
      name = attr.getValue();   
    attr = root.getAttribute(HANIMPOSE_ATTR_ENABLED_NAME);
    if (attr != null)
      enabled = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(HANIMPOSE_ATTR_LOA_NAME);
    if (attr != null)
        setLoa(new SFInt32(attr.getValue(), -1, 4));
    attr = root.getAttribute(HANIMPOSE_ATTR_TRANSITIONDURATION_NAME);
    if (attr != null)
      transitionDuration = new SFFloat(attr.getValue());
    
//    if (getContent().length() == 0)
//        setContent("\n\t\t<!--TODO add child HAnimDisplacer, Coordinate, content nodes here-->\n\t");
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    
    if (HANIMPOSE_ATTR_DESCRIPTION_REQD || !description.equals(HANIMPOSE_ATTR_DESCRIPTION_DFLT)) {
      sb.append(" ");
      sb.append(HANIMPOSE_ATTR_DESCRIPTION_NAME);
      sb.append("='");
      sb.append(escapeXmlCharacters(description));
      sb.append("'");
    }
    if (HANIMPOSE_ATTR_ENABLED_REQD || (enabled != enabledDefault)) {
      sb.append(" ");
      sb.append(HANIMPOSE_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(isEnabled());
      sb.append("'");
    }
    if (HANIMPOSE_ATTR_LOA_REQD || (loa != loaDefault)) {
      sb.append(" ");
      sb.append(HANIMPOSE_ATTR_LOA_NAME);
      sb.append("='");
      sb.append(getLoa());
      sb.append("'");
    }
    if (HANIMPOSE_ATTR_NAME_REQD || !name.equalsIgnoreCase(HANIMPOSE_ATTR_NAME_DFLT)) {
      sb.append(" ");
      sb.append(HANIMPOSE_ATTR_NAME_NAME);
      sb.append("='");
      sb.append(escapeXmlCharacters(name));
      sb.append("'");
    }
    if (HANIMPOSE_ATTR_TRANSITIONDURATION_REQD || (transitionDuration.getValue() != transitionDurationDefault.getValue())) {
      sb.append(" ");
      sb.append(HANIMPOSE_ATTR_TRANSITIONDURATION_NAME);
      sb.append("='");
      sb.append(transitionDuration);
      sb.append("'");
    }
    
    return sb.toString();
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String newDescription)
  {
    this.description = newDescription;
  }

  public boolean isEnabled()                      {return enabled;}
  public String  getName()                        {return name;}
  public String  getLoa()                         {return loa.toString();}
  public String  getTransitionDuration()          {return transitionDuration.toString();}
      
  public void setEnabled(boolean value)           {enabled = value;}
  public void setName(String s)                   {name = s;}
  public void setLoa(String s)                    {loa = new SFInt32(s);}
  public void setLoa(SFInt32 value)               {loa = value;}
  public void setTransitionDuration(String s)     {transitionDuration = new SFFloat(s);}

}
