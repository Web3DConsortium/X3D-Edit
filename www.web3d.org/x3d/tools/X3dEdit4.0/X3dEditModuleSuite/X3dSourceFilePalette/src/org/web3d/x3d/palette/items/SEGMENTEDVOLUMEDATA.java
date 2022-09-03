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

import org.web3d.x3d.types.X3DVolumeDataNode;
import javax.swing.text.JTextComponent;

import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * SEGMENTEDVOLUMEDATA.java
 * Created on July 11, 2007, 5:13 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey and Don Brutzman
 * @version $Id$
 */
public class SEGMENTEDVOLUMEDATA extends X3DVolumeDataNode
{
  private boolean[] segmentEnabled, segmentEnabledDefault;
  private boolean   insertCommas, insertLineBreaks = false;
  
  public SEGMENTEDVOLUMEDATA()
  {
  }

  @Override
  public String getElementName()
  {
    return SEGMENTEDVOLUMEDATA_ELNAME;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return SEGMENTEDVOLUMEDATACustomizer.class;
  }

  @Override
  @SuppressWarnings("NestedAssignment")
  public void initialize()
  {
    String[] fa;
    fa = parse3(SEGMENTEDVOLUMEDATA_ATTR_DIMENSIONS_DFLT);
    dimensionsX = dimensionsXDefault = new SFFloat(fa[0], null, null);
    dimensionsY = dimensionsYDefault = new SFFloat(fa[1], null, null);
    dimensionsZ = dimensionsZDefault = new SFFloat(fa[2], null, null);
    
    fa = parse3(SEGMENTEDVOLUMEDATA_ATTR_BBOXCENTER_DFLT);
    bboxCenterX = bboxCenterXDefault = new SFFloat(fa[0], null, null);
    bboxCenterY = bboxCenterYDefault = new SFFloat(fa[1], null, null);
    bboxCenterZ = bboxCenterZDefault = new SFFloat(fa[2], null, null);

    fa = parse3(SEGMENTEDVOLUMEDATA_ATTR_BBOXSIZE_DFLT);
    bboxSizeX = bboxSizeXDefault = new SFFloat(fa[0], null, null);
    bboxSizeY = bboxSizeYDefault = new SFFloat(fa[1], null, null);
    bboxSizeZ = bboxSizeZDefault = new SFFloat(fa[2], null, null);
    
    String[] sa;
    if(SEGMENTEDVOLUMEDATA_ATTR_SEGMENTENABLED_DFLT == null || SEGMENTEDVOLUMEDATA_ATTR_SEGMENTENABLED_DFLT.length()<=0)
      sa = new String[]{}; // empty 
    else
      sa = parseX(SEGMENTEDVOLUMEDATA_ATTR_SEGMENTENABLED_DFLT);
    segmentEnabled = segmentEnabledDefault = parseToBooleanArray(sa);

    setContent("\n\t\t<!--TODO add X3DVolumeRenderStyleNode renderStyle nodes and X3DTexture3DNode segmentIdentifiers node and voxels node here-->\n\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    String[] fa;
    attr = root.getAttribute(SEGMENTEDVOLUMEDATA_ATTR_DIMENSIONS_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      dimensionsX = new SFFloat(fa[0], null, null);
      dimensionsY = new SFFloat(fa[1], null, null);
      dimensionsZ = new SFFloat(fa[2], null, null);
    }
    attr = root.getAttribute(SEGMENTEDVOLUMEDATA_ATTR_BBOXCENTER_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      bboxCenterX = new SFFloat(fa[0], null, null);
      bboxCenterY = new SFFloat(fa[1], null, null);
      bboxCenterZ = new SFFloat(fa[2], null, null);
    }
    attr = root.getAttribute(SEGMENTEDVOLUMEDATA_ATTR_BBOXSIZE_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      bboxSizeX = new SFFloat(fa[0], null, null);
      bboxSizeY = new SFFloat(fa[1], null, null);
      bboxSizeZ = new SFFloat(fa[2], null, null);
    }
    attr = root.getAttribute(SEGMENTEDVOLUMEDATA_ATTR_SEGMENTENABLED_NAME);
    if (attr != null) {
      String[] sa = parseX(attr.getValue());
      segmentEnabled = parseToBooleanArray(sa);
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

    if (SEGMENTEDVOLUMEDATA_ATTR_DIMENSIONS_REQD ||
           (!dimensionsX.equals(dimensionsXDefault) ||
            !dimensionsY.equals(dimensionsYDefault) ||
            !dimensionsZ.equals(dimensionsZDefault))) {
      sb.append(" ");
      sb.append(SEGMENTEDVOLUMEDATA_ATTR_DIMENSIONS_NAME);
      sb.append("='");
      sb.append(dimensionsX);
      sb.append(" ");
      sb.append(dimensionsY);
      sb.append(" ");
      sb.append(dimensionsZ);
      sb.append("'");
    }
    if (SEGMENTEDVOLUMEDATA_ATTR_BBOXCENTER_REQD ||
           (!bboxCenterX.equals(bboxCenterXDefault) ||
            !bboxCenterY.equals(bboxCenterYDefault) ||
            !bboxCenterZ.equals(bboxCenterZDefault))) {
      sb.append(" ");
      sb.append(SEGMENTEDVOLUMEDATA_ATTR_BBOXCENTER_NAME);
      sb.append("='");
      sb.append(bboxCenterX);
      sb.append(" ");
      sb.append(bboxCenterY);
      sb.append(" ");
      sb.append(bboxCenterZ);
      sb.append("'");
    }
    if (SEGMENTEDVOLUMEDATA_ATTR_BBOXSIZE_REQD ||
           (!bboxSizeX.equals(bboxSizeXDefault) ||
            !bboxSizeY.equals(bboxSizeYDefault) ||
            !bboxSizeZ.equals(bboxSizeZDefault))) {
      sb.append(" ");
      sb.append(SEGMENTEDVOLUMEDATA_ATTR_BBOXSIZE_NAME);
      sb.append("='");
      sb.append(bboxSizeX);
      sb.append(" ");
      sb.append(bboxSizeY);
      sb.append(" ");
      sb.append(bboxSizeZ);
      sb.append("'");
    }
    if (SEGMENTEDVOLUMEDATA_ATTR_SEGMENTENABLED_REQD || !arraysIdenticalOrNull(segmentEnabled,segmentEnabledDefault)) {
      sb.append(" ");
      sb.append(SEGMENTEDVOLUMEDATA_ATTR_SEGMENTENABLED_NAME);
      sb.append("='");
      sb.append(formatBooleanArray(segmentEnabled, insertCommas, insertLineBreaks));
      sb.append("'");
    }

    return sb.toString();
  }

  public Object[][] getSegmentEnabled()
  {
    if(segmentEnabled.length == 0)
      return new Object[][] {{}}; // nothing to start with
   
    Object[][] oaa = new Object[segmentEnabled.length][1];
    
    for(int r=0; r < segmentEnabled.length; r++) {
      oaa[r][0] = Boolean.toString(segmentEnabled[r]);
    }
    return oaa;
  }
  
  public void setSegmentEnabled(String[][] saa)
  {
    if(saa.length == 0) {
      segmentEnabled = new boolean[]{};
      return;
    }
    
    segmentEnabled = new boolean[saa.length];
    for (int r=0; r < saa.length; r++) {
      if(saa[r][0].length()<= 0)
         saa[r][0] = "false";
      segmentEnabled[r] = Boolean.parseBoolean(saa[r][0]);
    }
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