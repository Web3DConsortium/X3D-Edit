/*
Copyright (c) 1995-2023 held by the author(s).  All rights reserved.
 
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
import org.web3d.x3d.types.X3DPrimitiveTypes.SFFloat;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFInt32;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;
import org.web3d.x3d.types.X3DTransformNode;

/**
 * HANIMHUMANOID.java
 * Created on 30 May 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class HANIMHUMANOID extends X3DTransformNode
{
  private String      description;
  private String      info;
  private String      name;
  private String      skeletalConfiguration; // X3D 4.0
  private String      version;
  private String      prefix; // calculated from DEF = prefix + name
  private SFInt32     loa, loaDefault;
  private SFFloat[][] jointBindingPositions;
  private SFFloat[][] jointBindingRotations;
  private SFFloat[][] jointBindingScales;
  private boolean     insertCommas, insertLineBreaks = false;
  

  public HANIMHUMANOID()
  {    
  }

  @Override
  public String getElementName()
  {
    return HANIMHUMANOID_ELNAME;
  }

  @Override
  public void initialize()
  {
    String[] sa;
    sa = parse3(HANIMHUMANOID_ATTR_CENTER_DFLT);
    centerX = centerXDefault = new SFFloat(sa[0]);
    centerY = centerYDefault = new SFFloat(sa[1]);
    centerZ = centerZDefault = new SFFloat(sa[2]);
    
    description = HANIMHUMANOID_ATTR_DESCRIPTION_DFLT;
    info        = HANIMHUMANOID_ATTR_INFO_DFLT;
    name        = HANIMHUMANOID_ATTR_NAME_DFLT;
    
    setLoa(loaDefault                 = new SFInt32  (HANIMHUMANOID_ATTR_LOA_DFLT,          -1,    4));
    
    sa = parse4(HANIMHUMANOID_ATTR_ROTATION_DFLT);
    rotationX = rotationXDefault = new SFFloat(sa[0]);
    rotationY = rotationYDefault = new SFFloat(sa[1]);
    rotationZ = rotationZDefault = new SFFloat(sa[2]);
    rotationAngle = rotationAngleDefault = new SFFloat(sa[3]);
    
    sa = parse3(HANIMHUMANOID_ATTR_SCALE_DFLT);
    scaleX = scaleXDefault = new SFFloat(sa[0]);
    scaleY = scaleYDefault = new SFFloat(sa[1]);
    scaleZ = scaleZDefault = new SFFloat(sa[2]);
    
    sa = parse4(HANIMHUMANOID_ATTR_SCALEORIENTATION_DFLT);
    scaleOrientationX = scaleOrientation0Default = new SFFloat(sa[0]);
    scaleOrientationY = scaleOrientation1Default = new SFFloat(sa[1]);
    scaleOrientationZ = scaleOrientation2Default = new SFFloat(sa[2]);
    scaleOrientationAngle = scaleOrientation3Default = new SFFloat(sa[3]);
    
    sa = parse3(HANIMHUMANOID_ATTR_TRANSLATION_DFLT);
    translationX = translationXDefault = new SFFloat(sa[0]);
    translationY = translationYDefault = new SFFloat(sa[1]);
    translationZ = translationZDefault = new SFFloat(sa[2]);
    
    skeletalConfiguration = HANIMHUMANOID_ATTR_SKELETALCONFIGURATION_DFLT;
    
    version = HANIMHUMANOID_ATTR_VERSION_DFLT;
    
    sa = parse3(HANIMHUMANOID_ATTR_BBOXCENTER_DFLT);
    bboxCenterX = bboxCenterXDefault = new SFFloat(sa[0]);
    bboxCenterY = bboxCenterYDefault = new SFFloat(sa[1]);
    bboxCenterZ = bboxCenterZDefault = new SFFloat(sa[2]);
    
    sa = parse3(HANIMHUMANOID_ATTR_BBOXSIZE_DFLT);
    bboxSizeX = bboxSizeXDefault = new SFFloat(sa[0]);
    bboxSizeY = bboxSizeYDefault = new SFFloat(sa[1]);
    bboxSizeZ = bboxSizeZDefault = new SFFloat(sa[2]);
    
    if(HANIMHUMANOID_ATTR_JOINTBINDINGPOSITIONS_DFLT == null || HANIMHUMANOID_ATTR_JOINTBINDINGPOSITIONS_DFLT.isEmpty())
      sa = new String[]{}; // empty 
    else
      sa = parseX(HANIMHUMANOID_ATTR_JOINTBINDINGPOSITIONS_DFLT);
    jointBindingPositions = parseToSFFloatTable(sa,3); 
    
    if(HANIMHUMANOID_ATTR_JOINTBINDINGROTATIONS_DFLT == null || HANIMHUMANOID_ATTR_JOINTBINDINGROTATIONS_DFLT.isEmpty())
      sa = new String[]{}; // empty 
    else
      sa = parseX(HANIMHUMANOID_ATTR_JOINTBINDINGROTATIONS_DFLT);
    jointBindingRotations = parseToSFFloatTable(sa,4); 
    
    if(HANIMHUMANOID_ATTR_JOINTBINDINGSCALES_DFLT == null || HANIMHUMANOID_ATTR_JOINTBINDINGSCALES_DFLT.isEmpty())
      sa = new String[]{}; // empty 
    else
      sa = parseX(HANIMHUMANOID_ATTR_JOINTBINDINGSCALES_DFLT);
    jointBindingScales = parseToSFFloatTable(sa,3); 

//    if (getContent().length() == 0)
//        setContent("\n\t\t<!--TODO add child HAnimJoint, HAnimSegment, HAnimSite, Coordinate/CoordinateDouble, Normal, and Viewpoint nodes here -->\n\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;
    String[] sa;
    
    attr = root.getAttribute(HANIMHUMANOID_ATTR_CENTER_NAME);
    if (attr != null){
      sa = parse3(attr.getValue());
      centerX = new SFFloat(sa[0]);
      centerY = new SFFloat(sa[1]);
      centerZ = new SFFloat(sa[2]);
    }
    attr = root.getAttribute(HANIMHUMANOID_ATTR_DESCRIPTION_NAME);
    if (attr != null)
      description = attr.getValue();

    attr = root.getAttribute( HANIMHUMANOID_ATTR_INFO_NAME);
    if (attr != null)
      info = attr.getValue();
    
    attr = root.getAttribute( HANIMHUMANOID_ATTR_LOA_NAME);
    if (attr != null)
        setLoa(new SFInt32(attr.getValue(), -1, 4));
    
    attr = root.getAttribute( HANIMHUMANOID_ATTR_NAME_NAME);
    if (attr != null)
      name = attr.getValue();
    
    attr = root.getAttribute(HANIMHUMANOID_ATTR_ROTATION_NAME);
    if (attr != null){
      sa = parse4(attr.getValue());
      rotationX = new SFFloat(sa[0]);
      rotationY = new SFFloat(sa[1]);
      rotationZ = new SFFloat(sa[2]);
      rotationAngle = new SFFloat(sa[3]);
    }
    attr = root.getAttribute(HANIMHUMANOID_ATTR_SCALE_NAME);
    if (attr != null){
      sa = parse3(attr.getValue());
      scaleX = new SFFloat(sa[0]);
      scaleY = new SFFloat(sa[1]);
      scaleZ = new SFFloat(sa[2]);
    }
    attr = root.getAttribute(HANIMHUMANOID_ATTR_SCALEORIENTATION_NAME);
    if (attr != null){
      sa = parse4(attr.getValue());
      scaleOrientationX = new SFFloat(sa[0]);
      scaleOrientationY = new SFFloat(sa[1]);
      scaleOrientationZ = new SFFloat(sa[2]);
      scaleOrientationAngle = new SFFloat(sa[3]);
    }
    attr = root.getAttribute(HANIMHUMANOID_ATTR_TRANSLATION_NAME);
    if (attr != null){
      sa = parse3(attr.getValue());
      translationX = new SFFloat(sa[0]);
      translationY = new SFFloat(sa[1]);
      translationZ = new SFFloat(sa[2]);
    }
    
    attr = root.getAttribute(HANIMHUMANOID_ATTR_SKELETALCONFIGURATION_NAME);
    if (attr != null)
      skeletalConfiguration = attr.getValue();
    
    attr = root.getAttribute(HANIMHUMANOID_ATTR_VERSION_NAME);
    if (attr != null)
      version = attr.getValue();
    
    attr = root.getAttribute(HANIMHUMANOID_ATTR_BBOXCENTER_NAME);
    if (attr != null){
      sa = parse3(attr.getValue());
      bboxCenterX = new SFFloat(sa[0]);
      bboxCenterY = new SFFloat(sa[1]);
      bboxCenterZ = new SFFloat(sa[2]);
    }
    attr = root.getAttribute(HANIMHUMANOID_ATTR_BBOXSIZE_NAME);
    if (attr != null){
      sa = parse3(attr.getValue());
      bboxSizeX = new SFFloat(sa[0]);
      bboxSizeY = new SFFloat(sa[1]);
      bboxSizeZ = new SFFloat(sa[2]);
    }  
    attr = root.getAttribute(HANIMHUMANOID_ATTR_JOINTBINDINGPOSITIONS_NAME);
    if (attr != null) {
      sa = parseX(attr.getValue());
      jointBindingPositions = parseToSFFloatTable(sa,3); // cols: x, y, z
      if (attr.getValue().contains(","))  insertCommas     = true;
      if (attr.getValue().contains("\n") ||
          attr.getValue().contains("\r")) insertLineBreaks = true; // TODO not working, line breaks not being passed from JDOM
      if (insertCommas)                   insertLineBreaks = true; // workaround default, if commas were present then most likely lineBreaks also
    }
    attr = root.getAttribute(HANIMHUMANOID_ATTR_JOINTBINDINGROTATIONS_NAME);
    if (attr != null) {
      sa = parseX(attr.getValue());
      jointBindingRotations = parseToSFFloatTable(sa,4); // cols: x, y, z, angle
      if (attr.getValue().contains(","))  insertCommas     = true;
      if (attr.getValue().contains("\n") ||
          attr.getValue().contains("\r")) insertLineBreaks = true; // TODO not working, line breaks not being passed from JDOM
      if (insertCommas)                   insertLineBreaks = true; // workaround default, if commas were present then most likely lineBreaks also
    }
    attr = root.getAttribute(HANIMHUMANOID_ATTR_JOINTBINDINGSCALES_NAME);
    if (attr != null) {
      sa = parseX(attr.getValue());
      jointBindingScales = parseToSFFloatTable(sa,3); // cols: x, y, z
      if (attr.getValue().contains(","))  insertCommas     = true;
      if (attr.getValue().contains("\n") ||
          attr.getValue().contains("\r")) insertLineBreaks = true; // TODO not working, line breaks not being passed from JDOM
      if (insertCommas)                   insertLineBreaks = true; // workaround default, if commas were present then most likely lineBreaks also
    }
  }
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (HANIMHUMANOID_ATTR_BBOXCENTER_REQD || (!bboxCenterX.equals(bboxCenterXDefault) || !bboxCenterY.equals(bboxCenterYDefault) || !bboxCenterZ.equals(bboxCenterZDefault) )) {
      sb.append(" ");
      sb.append(HANIMHUMANOID_ATTR_BBOXCENTER_NAME);
      sb.append("='");
      sb.append(bboxCenterX);
      sb.append(" ");
      sb.append(bboxCenterY);
      sb.append(" ");
      sb.append(bboxCenterZ);
      sb.append("'");
    }
    if (HANIMHUMANOID_ATTR_BBOXSIZE_REQD || (!bboxSizeX.equals(bboxSizeXDefault) || !bboxSizeY.equals(bboxSizeYDefault) || !bboxSizeZ.equals(bboxSizeZDefault) )) {
      sb.append(" ");
      sb.append(HANIMHUMANOID_ATTR_BBOXSIZE_NAME);
      sb.append("='");
      sb.append(bboxSizeX);
      sb.append(" ");
      sb.append(bboxSizeY);
      sb.append(" ");
      sb.append(bboxSizeZ);
      sb.append("'");
    }
    if (HANIMHUMANOID_ATTR_CENTER_REQD || (!centerX.equals(centerXDefault) || !centerY.equals(centerYDefault) || !centerZ.equals(centerZDefault) )) {
      sb.append(" ");
      sb.append(HANIMHUMANOID_ATTR_CENTER_NAME);
      sb.append("='");
      sb.append(centerX);
      sb.append(" ");
      sb.append(centerY);
      sb.append(" ");
      sb.append(centerZ);
      sb.append("'");
    }
    if (HANIMHUMANOID_ATTR_DESCRIPTION_REQD || !description.equals(HANIMHUMANOID_ATTR_DESCRIPTION_DFLT)) {
      sb.append(" ");
      sb.append(HANIMHUMANOID_ATTR_DESCRIPTION_NAME);
      sb.append("='");
      sb.append(escapeXmlCharacters(description));
      sb.append("'");
    }
    if (HANIMHUMANOID_ATTR_INFO_REQD || !info.equals(HANIMHUMANOID_ATTR_INFO_DFLT)) {
      sb.append(" ");
      sb.append(HANIMHUMANOID_ATTR_INFO_NAME);
      sb.append("='");
      sb.append(escapeXmlCharacters(info));
      sb.append("'");
    }
    if (HANIMMOTION_ATTR_LOA_REQD || (getLoa() != loaDefault)) {
      sb.append(" ");
      sb.append(HANIMMOTION_ATTR_LOA_NAME);
      sb.append("='");
      sb.append(getLoa());
      sb.append("'");
    }
    if (HANIMHUMANOID_ATTR_NAME_REQD || !name.equalsIgnoreCase(HANIMHUMANOID_ATTR_NAME_DFLT)) {
      sb.append(" ");
      sb.append(HANIMHUMANOID_ATTR_NAME_NAME);
      sb.append("='");
      sb.append(escapeXmlCharacters(name));
      sb.append("'");
    }
    if (HANIMHUMANOID_ATTR_ROTATION_REQD || (!rotationX.equals(rotationXDefault) || !rotationY.equals(rotationYDefault) || !rotationZ.equals(rotationZDefault) || !rotationAngle.equals(rotationAngleDefault))) {
      sb.append(" ");
      sb.append(HANIMHUMANOID_ATTR_ROTATION_NAME);
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
    if (HANIMHUMANOID_ATTR_SCALE_REQD || (!scaleX.equals(scaleXDefault) || !scaleY.equals(scaleYDefault) || !scaleZ.equals(scaleZDefault) )) {
      sb.append(" ");
      sb.append(HANIMHUMANOID_ATTR_SCALE_NAME);
      sb.append("='");
      sb.append(scaleX);
      sb.append(" ");
      sb.append(scaleY);
      sb.append(" ");
      sb.append(scaleZ);
      sb.append("'");
    }
    if (HANIMHUMANOID_ATTR_SCALEORIENTATION_REQD || (!scaleOrientationX.equals(scaleOrientation0Default) || !scaleOrientationY.equals(scaleOrientation1Default) || !scaleOrientationZ.equals(scaleOrientation2Default) || !scaleOrientationAngle.equals(scaleOrientation3Default))) {
      sb.append(" ");
      sb.append(HANIMHUMANOID_ATTR_SCALEORIENTATION_NAME);
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
    if (HANIMHUMANOID_ATTR_TRANSLATION_REQD || (!translationX.equals(translationXDefault) || !translationY.equals(translationYDefault) || !translationZ.equals(translationZDefault) )) {
      sb.append(" ");
      sb.append(HANIMHUMANOID_ATTR_TRANSLATION_NAME);
      sb.append("='");
      sb.append(translationX);
      sb.append(" ");
      sb.append(translationY);
      sb.append(" ");
      sb.append(translationZ);
      sb.append("'");
    }
    if (true || HANIMHUMANOID_ATTR_SKELETALCONFIGURATION_REQD || !skeletalConfiguration.equals(HANIMHUMANOID_ATTR_SKELETALCONFIGURATION_DFLT) ) {
      sb.append(" ");
      sb.append(HANIMHUMANOID_ATTR_SKELETALCONFIGURATION_NAME);
      sb.append("='");
      sb.append(skeletalConfiguration);
      sb.append("'");
    }
    // always output HAnimHumanoid version as a good practice, given all the mixups with past versions and possibilities for future versions
    if (true || HANIMHUMANOID_ATTR_VERSION_REQD || !version.equals(HANIMHUMANOID_ATTR_VERSION_DFLT) ) {
      sb.append(" ");
      sb.append(HANIMHUMANOID_ATTR_VERSION_NAME);
      sb.append("='");
      sb.append(version);
      sb.append("'");
    }
    if (HANIMHUMANOID_ATTR_JOINTBINDINGPOSITIONS_REQD || !getJointBindingPositionsString().equals(HANIMHUMANOID_ATTR_JOINTBINDINGPOSITIONS_DFLT)) {
      sb.append(" ");
      sb.append(HANIMHUMANOID_ATTR_JOINTBINDINGPOSITIONS_NAME);
      sb.append("='");
      sb.append(formatFloatArray(getJointBindingPositions(), insertCommas, insertLineBreaks));
      sb.append("'");
    }
    if (HANIMHUMANOID_ATTR_JOINTBINDINGROTATIONS_REQD || !getJointBindingRotationsString().equals(HANIMHUMANOID_ATTR_JOINTBINDINGROTATIONS_DFLT)) {
      sb.append(" ");
      sb.append(HANIMHUMANOID_ATTR_JOINTBINDINGROTATIONS_NAME);
      sb.append("='");
      sb.append(formatFloatArray(getJointBindingRotations(), insertCommas, insertLineBreaks));
      sb.append("'");
    }
    if (HANIMHUMANOID_ATTR_JOINTBINDINGSCALES_REQD || !getJointBindingScalesString().equals(HANIMHUMANOID_ATTR_JOINTBINDINGSCALES_DFLT)) {
      sb.append(" ");
      sb.append(HANIMHUMANOID_ATTR_JOINTBINDINGSCALES_NAME);
      sb.append("='");
      sb.append(formatFloatArray(getJointBindingScales(), insertCommas, insertLineBreaks));
      sb.append("'");
    }

    return sb.toString();
  }

  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public String getInfo()                   {return info;}
  public String getName()                   {return name;}
  public String getSkeletalConfiguration()  {return skeletalConfiguration;}
  public String getVersion()                {return version;}

  public void setInfo(String s)                 {info = s;}
  public void setName(String s)                 {name = s;}
  public void setSkeletalConfiguration(String s){skeletalConfiguration = s;}
  public void setVersion(String s)              {version = s;}

    /**
     * calculated from DEF = prefix + name
     * @return the prefix
     */
    public String getPrefix()
    {
        return prefix;
    }

    /**
     * calculated from DEF = prefix + name
     * @param newPrefix the prefix to set
     */
    public void setPrefix(String newPrefix)
    {
        this.prefix = newPrefix;
    }

    /**
     * @return the Level of Articulation (LOA)
     */
    public SFInt32 getLoa()
    {
        return loa;
    }

    /**
     * @param loa the Level of Articulation (LOA) to set
     */
    public void setLoa(SFInt32 loa)
    {
        this.loa = loa;
    }

    /**
     * @return the jointBindingPositions
     */
    public SFFloat[][] getJointBindingPositions()
    {
        return jointBindingPositions;
    }
    /**
     * @return the jointBindingPositions
     */
    public String getJointBindingPositionsString()
    {
        return formatFloatArray(getJointBindingPositions());
    }
    /**
     * @param jointBindingPositions the jointBindingPositions to set
     */
    public void setJointBindingPositions(SFFloat[][] jointBindingPositions)
    {
        this.jointBindingPositions = jointBindingPositions;
    }
    /**
     * @return the jointBindingRotations
     */
    public SFFloat[][] getJointBindingRotations()
    {
        return jointBindingRotations;
    }
    /**
     * @return the jointBindingRotations
     */
    public String getJointBindingRotationsString()
    {
        return formatFloatArray(getJointBindingRotations());
    }
    /**
     * @param jointBindingRotations the jointBindingRotations to set
     */
    public void setJointBindingRotations(SFFloat[][] jointBindingRotations)
    {
        this.jointBindingRotations = jointBindingRotations;
    }

    /**
     * @return the jointBindingScales
     */
    public SFFloat[][] getJointBindingScales()
    {
        return jointBindingScales;
    }
    /**
     * @return the jointBindingScales
     */
    public String getJointBindingScalesString()
    {
        return formatFloatArray(getJointBindingScales());
    }
    /**
     * @param jointBindingScales the jointBindingScales to set
     */
    public void setJointBindingScales(SFFloat[][] jointBindingScales)
    {
        this.jointBindingScales = jointBindingScales;
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
     * @param insertLineBreaks the insertLineBreaks value to set
     */
    public void setInsertLineBreaks(boolean insertLineBreaks) {
        this.insertLineBreaks = insertLineBreaks;
    }
}
