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
import org.web3d.x3d.types.X3DGroupingNode;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * HANIMSEGMENT.java
 * Created on 29 May 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class HANIMSEGMENT extends X3DGroupingNode
{
  private SFFloat centerOfMassX, centerOfMassXDefault;
  private SFFloat centerOfMassY, centerOfMassYDefault;
  private SFFloat centerOfMassZ, centerOfMassZDefault;
  private SFFloat mass,          massDefault;
  private SFFloat[] momentsOfInertia, momentsOfInertiaDefault;
  private String  name;  

  public HANIMSEGMENT()
  {    
  }

  @Override
  public String getElementName()
  {
    return HANIMSEGMENT_ELNAME;
  }

  @Override
  public void initialize()
  {
    String[] sa;
    sa = parse3(HANIMSEGMENT_ATTR_CENTEROFMASS_DFLT);
    centerOfMassX = centerOfMassXDefault = new SFFloat(sa[0]);
    centerOfMassY = centerOfMassYDefault = new SFFloat(sa[1]);
    centerOfMassZ = centerOfMassZDefault = new SFFloat(sa[2]);
    
    mass = massDefault = new SFFloat(HANIMSEGMENT_ATTR_MASS_DFLT);
    
    momentsOfInertia = momentsOfInertiaDefault = handleFloatArray(this,HANIMSEGMENT_ATTR_MOMENTSOFINERTIA_DFLT);
    
    name = HANIMSEGMENT_ATTR_NAME_DFLT;
    
    sa = parse3(HANIMSEGMENT_ATTR_BBOXCENTER_DFLT);
    bboxCenterX = bboxCenterXDefault = new SFFloat(sa[0]);
    bboxCenterY = bboxCenterYDefault = new SFFloat(sa[1]);
    bboxCenterZ = bboxCenterZDefault = new SFFloat(sa[2]);
    
    sa = parse3(HANIMSEGMENT_ATTR_BBOXSIZE_DFLT);
    bboxSizeX = bboxSizeXDefault = new SFFloat(sa[0]);
    bboxSizeY = bboxSizeYDefault = new SFFloat(sa[1]);
    bboxSizeZ = bboxSizeZDefault = new SFFloat(sa[2]);

    if (getContent().length() == 0)
        setContent("\n\t\t<!--TODO add child HAnimDisplacer, Coordinate, content nodes here-->\n\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;
    String[] sa;
    
    attr = root.getAttribute(HANIMSEGMENT_ATTR_CENTEROFMASS_NAME);
    if (attr != null){
      sa = parse3(attr.getValue());
      centerOfMassX = new SFFloat(sa[0]);
      centerOfMassY = new SFFloat(sa[1]);
      centerOfMassZ = new SFFloat(sa[2]);
    }
    attr = root.getAttribute( HANIMSEGMENT_ATTR_MASS_NAME);
    if (attr != null)
      mass = new SFFloat(attr.getValue());
    
    attr = root.getAttribute( HANIMSEGMENT_ATTR_MOMENTSOFINERTIA_NAME);
    if (attr != null)
      momentsOfInertia = handleFloatArray(this,attr.getValue());  
    attr = root.getAttribute( HANIMSEGMENT_ATTR_NAME_NAME);
    if (attr != null)
      name = attr.getValue();    
    attr = root.getAttribute(HANIMSEGMENT_ATTR_BBOXCENTER_NAME);
    if (attr != null){
      sa = parse3(attr.getValue());
      bboxCenterX = new SFFloat(sa[0]);
      bboxCenterY = new SFFloat(sa[1]);
      bboxCenterZ = new SFFloat(sa[2]);
    }
    attr = root.getAttribute(HANIMSEGMENT_ATTR_BBOXSIZE_NAME);
    if (attr != null){
      sa = parse3(attr.getValue());
      bboxSizeX = new SFFloat(sa[0]);
      bboxSizeY = new SFFloat(sa[1]);
      bboxSizeZ = new SFFloat(sa[2]);
    }
    if (getContent().length() == 0)
        setContent("\n\t\t<!--TODO add child HAnimDisplacer, Coordinate, content nodes here-->\n\t");
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    if (HANIMSEGMENT_ATTR_BBOXCENTER_REQD || (!bboxCenterX.equals(bboxCenterXDefault) || !bboxCenterY.equals(bboxCenterYDefault) || !bboxCenterZ.equals(bboxCenterZDefault) )) {
      sb.append(" ");
      sb.append(HANIMSEGMENT_ATTR_BBOXCENTER_NAME);
      sb.append("='");
      sb.append(bboxCenterX);
      sb.append(" ");
      sb.append(bboxCenterY);
      sb.append(" ");
      sb.append(bboxCenterZ);
      sb.append("'");
    }
    if (HANIMSEGMENT_ATTR_BBOXSIZE_REQD || (!bboxSizeX.equals(bboxSizeXDefault) || !bboxSizeY.equals(bboxSizeYDefault) || !bboxSizeZ.equals(bboxSizeZDefault) )) {
      sb.append(" ");
      sb.append(HANIMSEGMENT_ATTR_BBOXSIZE_NAME);
      sb.append("='");
      sb.append(bboxSizeX);
      sb.append(" ");
      sb.append(bboxSizeY);
      sb.append(" ");
      sb.append(bboxSizeZ);
      sb.append("'");
    }
    if (HANIMSEGMENT_ATTR_CENTEROFMASS_REQD || (!centerOfMassX.equals(centerOfMassXDefault) || !centerOfMassY.equals(centerOfMassYDefault) || !centerOfMassZ.equals(centerOfMassZDefault) )) {
      sb.append(" ");
      sb.append(HANIMSEGMENT_ATTR_CENTEROFMASS_NAME);
      sb.append("='");
      sb.append(centerOfMassX);
      sb.append(" ");
      sb.append(centerOfMassY);
      sb.append(" ");
      sb.append(centerOfMassZ);
      sb.append("'");
    }
    if (HANIMSEGMENT_ATTR_MASS_REQD || !mass.equals(massDefault)) {
      sb.append(" ");
      sb.append(HANIMSEGMENT_ATTR_MASS_NAME);
      sb.append("='");
      sb.append(mass);
      sb.append("'");
    }
    if(HANIMSEGMENT_ATTR_MOMENTSOFINERTIA_REQD || !arraysIdenticalOrNull(momentsOfInertia,momentsOfInertiaDefault)) {
      sb.append(" ");
      sb.append(HANIMSEGMENT_ATTR_MOMENTSOFINERTIA_NAME);
      sb.append("='");
      sb.append(formatFloatArray(momentsOfInertia));
      sb.append("'");
    }
    if (HANIMSEGMENT_ATTR_NAME_REQD || !name.equalsIgnoreCase(HANIMSEGMENT_ATTR_NAME_DFLT)) {
      sb.append(" ");
      sb.append(HANIMSEGMENT_ATTR_NAME_NAME);
      sb.append("='");
      sb.append(escapeXmlCharacters(name));
      sb.append("'");
    }
    return sb.toString();
  }

  public String getCenterOfMassX(){return centerOfMassX.toString();}
  public String getCenterOfMassY(){return centerOfMassY.toString();}
  public String getCenterOfMassZ(){return centerOfMassZ.toString();}
  public String getMass()         {return mass.toString();}
  public String getName()         {return name;}
  public String[] getMomentsOfInertia() {
    String[][] saa = makeStringArray(new SFFloat[][]{momentsOfInertia});
    return saa[0];
  }


  public void setCenterOfMassX(String s){centerOfMassX = new SFFloat(s,null,null);}
  public void setCenterOfMassY(String s){centerOfMassY = new SFFloat(s,null,null);}
  public void setCenterOfMassZ(String s){centerOfMassZ = new SFFloat(s,null,null);}
  public void setMass(String s)         {mass = new SFFloat(s);}
  public void setMomentsOfInertia(String[] sa){momentsOfInertia =  parseToSFFloatArray(sa);}
  public void setName(String s)         {name = s;}

  private static SFFloat[] handleFloatArray(BaseX3DElement el, String str)
  {
    String[] sa;
    if(str == null || str.length()<=0)
      sa = new String[]{}; // empty 
    else
      sa = parseX(str);
    return el.parseToSFFloatArray(sa);
  }

}
