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
import org.web3d.x3d.types.X3DTransformNode;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * HANIMSITE.java
 * Created on 29 May 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class HANIMSITE extends X3DTransformNode
{
  private String  name;  

  public HANIMSITE()
  {    
  }

  @Override
  public String getElementName()
  {
    return HANIMSITE_ELNAME;
  }

  @Override
  public void initialize()
  {
    String[] sa;
    sa = parse3(HANIMSITE_ATTR_CENTER_DFLT);
    centerX = centerXDefault = new SFFloat(sa[0]);
    centerY = centerYDefault = new SFFloat(sa[1]);
    centerZ = centerZDefault = new SFFloat(sa[2]);
    
    name    = HANIMSITE_ATTR_NAME_DFLT;

    sa = parse4(HANIMSITE_ATTR_ROTATION_DFLT);
    rotationX = rotationXDefault = new SFFloat(sa[0]);
    rotationY = rotationYDefault = new SFFloat(sa[1]);
    rotationZ = rotationZDefault = new SFFloat(sa[2]);
    rotationAngle = rotationAngleDefault = new SFFloat(sa[3]);
    
    sa = parse3(HANIMSITE_ATTR_SCALE_DFLT);
    scaleX = scaleXDefault = new SFFloat(sa[0]);
    scaleY = scaleYDefault = new SFFloat(sa[1]);
    scaleZ = scaleZDefault = new SFFloat(sa[2]);
    
    sa = parse4(HANIMSITE_ATTR_SCALEORIENTATION_DFLT);
    scaleOrientationX = scaleOrientation0Default = new SFFloat(sa[0]);
    scaleOrientationY = scaleOrientation1Default = new SFFloat(sa[1]);
    scaleOrientationZ = scaleOrientation2Default = new SFFloat(sa[2]);
    scaleOrientationAngle = scaleOrientation3Default = new SFFloat(sa[3]);
    
    sa = parse3(HANIMSITE_ATTR_TRANSLATION_DFLT);
    translationX = translationXDefault = new SFFloat(sa[0]);
    translationY = translationYDefault = new SFFloat(sa[1]);
    translationZ = translationZDefault = new SFFloat(sa[2]);
    
    sa = parse3(HANIMSITE_ATTR_BBOXCENTER_DFLT);
    bboxCenterX = bboxCenterXDefault = new SFFloat(sa[0]);
    bboxCenterY = bboxCenterYDefault = new SFFloat(sa[1]);
    bboxCenterZ = bboxCenterZDefault = new SFFloat(sa[2]);
    
    sa = parse3(HANIMSITE_ATTR_BBOXSIZE_DFLT);
    bboxSizeX = bboxSizeXDefault = new SFFloat(sa[0]);
    bboxSizeY = bboxSizeYDefault = new SFFloat(sa[1]);
    bboxSizeZ = bboxSizeZDefault = new SFFloat(sa[2]);

    if (getContent().trim().isEmpty()) // if there, don't clobber visualization
    {
        setContent("\n\t\t<!-- TODO add children nodes and statements here -->\n\t");
    }
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;
    String[] sa;
    
    attr = root.getAttribute(HANIMSITE_ATTR_CENTER_NAME);
    if (attr != null){
      sa = parse3(attr.getValue());
      centerX = new SFFloat(sa[0]);
      centerY = new SFFloat(sa[1]);
      centerZ = new SFFloat(sa[2]);
    }
    attr = root.getAttribute(HANIMSITE_ATTR_NAME_NAME);
    if (attr != null)
      name = attr.getValue();
    attr = root.getAttribute(HANIMSITE_ATTR_ROTATION_NAME);
    if (attr != null){
      sa = parse4(attr.getValue());
      rotationX = new SFFloat(sa[0]);
      rotationY = new SFFloat(sa[1]);
      rotationZ = new SFFloat(sa[2]);
      rotationAngle = new SFFloat(sa[3]);
    }
    attr = root.getAttribute(HANIMSITE_ATTR_SCALE_NAME);
    if (attr != null){
      sa = parse3(attr.getValue());
      scaleX = new SFFloat(sa[0]);
      scaleY = new SFFloat(sa[1]);
      scaleZ = new SFFloat(sa[2]);
    }
    attr = root.getAttribute(HANIMSITE_ATTR_SCALEORIENTATION_NAME);
    if (attr != null){
      sa = parse4(attr.getValue());
      scaleOrientationX = new SFFloat(sa[0]);
      scaleOrientationY = new SFFloat(sa[1]);
      scaleOrientationZ = new SFFloat(sa[2]);
      scaleOrientationAngle = new SFFloat(sa[3]);
    }
    attr = root.getAttribute(HANIMSITE_ATTR_TRANSLATION_NAME);
    if (attr != null){
      sa = parse3(attr.getValue());
      translationX = new SFFloat(sa[0]);
      translationY = new SFFloat(sa[1]);
      translationZ = new SFFloat(sa[2]);
    }
    attr = root.getAttribute(HANIMSITE_ATTR_BBOXCENTER_NAME);
    if (attr != null){
      sa = parse3(attr.getValue());
      bboxCenterX = new SFFloat(sa[0]);
      bboxCenterY = new SFFloat(sa[1]);
      bboxCenterZ = new SFFloat(sa[2]);
    }
    attr = root.getAttribute(HANIMSITE_ATTR_BBOXSIZE_NAME);
    if (attr != null){
      sa = parse3(attr.getValue());
      bboxSizeX = new SFFloat(sa[0]);
      bboxSizeY = new SFFloat(sa[1]);
      bboxSizeZ = new SFFloat(sa[2]);
    }    
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (HANIMSITE_ATTR_BBOXCENTER_REQD || (!bboxCenterX.equals(bboxCenterXDefault) || !bboxCenterY.equals(bboxCenterYDefault) || !bboxCenterZ.equals(bboxCenterZDefault) )) {
      sb.append(" ");
      sb.append(HANIMSITE_ATTR_BBOXCENTER_NAME);
      sb.append("='");
      sb.append(bboxCenterX);
      sb.append(" ");
      sb.append(bboxCenterY);
      sb.append(" ");
      sb.append(bboxCenterZ);
      sb.append("'");
    }
    if (HANIMSITE_ATTR_BBOXSIZE_REQD || (!bboxSizeX.equals(bboxSizeXDefault) || !bboxSizeY.equals(bboxSizeYDefault) || !bboxSizeZ.equals(bboxSizeZDefault) )) {
      sb.append(" ");
      sb.append(HANIMSITE_ATTR_BBOXSIZE_NAME);
      sb.append("='");
      sb.append(bboxSizeX);
      sb.append(" ");
      sb.append(bboxSizeY);
      sb.append(" ");
      sb.append(bboxSizeZ);
      sb.append("'");
    }
    if (HANIMSITE_ATTR_CENTER_REQD || (!centerX.equals(centerXDefault) || !centerY.equals(centerYDefault) || !centerZ.equals(centerZDefault) )) {
      sb.append(" ");
      sb.append(HANIMSITE_ATTR_CENTER_NAME);
      sb.append("='");
      sb.append(centerX);
      sb.append(" ");
      sb.append(centerY);
      sb.append(" ");
      sb.append(centerZ);
      sb.append("'");
    }
    if (HANIMSITE_ATTR_NAME_REQD || !name.equalsIgnoreCase(HANIMSITE_ATTR_NAME_DFLT)) {
      sb.append(" ");
      sb.append(HANIMSITE_ATTR_NAME_NAME);
      sb.append("='");
      sb.append(escapeXmlCharacters(name));
      sb.append("'");
    }    
    if (HANIMSITE_ATTR_ROTATION_REQD || (!rotationX.equals(rotationXDefault) || !rotationY.equals(rotationYDefault) || !rotationZ.equals(rotationZDefault) || !rotationAngle.equals(rotationAngleDefault))) {
      sb.append(" ");
      sb.append(HANIMSITE_ATTR_ROTATION_NAME);
      sb.append("='");
      sb.append(rotationX);
      sb.append(" ");
      sb.append(rotationY);
      sb.append(" ");
      sb.append(rotationZ);
      sb.append(" ");
      sb.append(rotationAngle);
      sb.append("'");
    }
    if (HANIMSITE_ATTR_SCALE_REQD || (!scaleX.equals(scaleXDefault) || !scaleY.equals(scaleYDefault) || !scaleZ.equals(scaleZDefault) )) {
      sb.append(" ");
      sb.append(HANIMSITE_ATTR_SCALE_NAME);
      sb.append("='");
      sb.append(scaleX);
      sb.append(" ");
      sb.append(scaleY);
      sb.append(" ");
      sb.append(scaleZ);
      sb.append("'");
    }
    if (HANIMSITE_ATTR_SCALEORIENTATION_REQD || (!scaleOrientationX.equals(scaleOrientation0Default) || !scaleOrientationY.equals(scaleOrientation1Default) || !scaleOrientationZ.equals(scaleOrientation2Default) || !scaleOrientationAngle.equals(scaleOrientation3Default))) {
      sb.append(" ");
      sb.append(HANIMSITE_ATTR_SCALEORIENTATION_NAME);
      sb.append("='");
      sb.append(scaleOrientationX);
      sb.append(" ");
      sb.append(scaleOrientationY);
      sb.append(" ");
      sb.append(scaleOrientationZ);
      sb.append(" ");
      sb.append(scaleOrientationAngle);
      sb.append("'");
    }
    if (HANIMSITE_ATTR_TRANSLATION_REQD || (!translationX.equals(translationXDefault) || !translationY.equals(translationYDefault) || !translationZ.equals(translationZDefault) )) {
      sb.append(" ");
      sb.append(HANIMSITE_ATTR_TRANSLATION_NAME);
      sb.append("='");
      sb.append(translationX);
      sb.append(" ");
      sb.append(translationY);
      sb.append(" ");
      sb.append(translationZ);
      sb.append("'");
    }

    return sb.toString();
  }

  public String getName()
  {
    return name;
  }

  public void setName(String s)
  {
    name = s;
  }
}
