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
import org.web3d.x3d.types.X3DPrimitiveTypes.SFFloat;
import static org.web3d.x3d.types.X3DSchemaData.*;
import org.web3d.x3d.types.X3DVolumeDataNode;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * ISOSURFACEVOLUMEDATA.java
 * Created on 15 November 2011
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey and Don Brutzman
 * @version $Id$
 */
public class ISOSURFACEVOLUMEDATA extends X3DVolumeDataNode
{
  protected SFFloat    contourStepSize,  contourStepSizeDefault;
  protected SFFloat   surfaceTolerance, surfaceToleranceDefault;
  protected SFFloat[]    surfaceValues, surfaceValuesDefault;
  
  private boolean  insertCommas, insertLineBreaks = false;
  
  public ISOSURFACEVOLUMEDATA()
  {
  }

  @Override
  public String getElementName()
  {
    return ISOSURFACEVOLUMEDATA_ELNAME;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return ISOSURFACEVOLUMEDATACustomizer.class;
  }

  @Override
  @SuppressWarnings("NestedAssignment")
  public void initialize()
  {
    String[] fa;
    fa = parse3(ISOSURFACEVOLUMEDATA_ATTR_DIMENSIONS_DFLT);
    dimensionsX = dimensionsXDefault = new SFFloat(fa[0], null, null);
    dimensionsY = dimensionsYDefault = new SFFloat(fa[1], null, null);
    dimensionsZ = dimensionsZDefault = new SFFloat(fa[2], null, null);
    
    fa = parse3(ISOSURFACEVOLUMEDATA_ATTR_BBOXCENTER_DFLT);
    bboxCenterX = bboxCenterXDefault = new SFFloat(fa[0], null, null);
    bboxCenterY = bboxCenterYDefault = new SFFloat(fa[1], null, null);
    bboxCenterZ = bboxCenterZDefault = new SFFloat(fa[2], null, null);

    fa = parse3(ISOSURFACEVOLUMEDATA_ATTR_BBOXSIZE_DFLT);
    bboxSizeX = bboxSizeXDefault = new SFFloat(fa[0], null, null);
    bboxSizeY = bboxSizeYDefault = new SFFloat(fa[1], null, null);
    bboxSizeZ = bboxSizeZDefault = new SFFloat(fa[2], null, null);
    
     contourStepSize =  contourStepSizeDefault = new SFFloat(ISOSURFACEVOLUMEDATA_ATTR_CONTOURSTEPSIZE_DFLT,  null, null);
    surfaceTolerance = surfaceToleranceDefault = new SFFloat(ISOSURFACEVOLUMEDATA_ATTR_SURFACETOLERANCE_DFLT, 0.0f, null);
    
    if(ISOSURFACEVOLUMEDATA_ATTR_SURFACEVALUES_DFLT == null || ISOSURFACEVOLUMEDATA_ATTR_SURFACEVALUES_DFLT.length()<=0)
      fa = new String[]{}; // empty 
    else
      fa = parseX(ISOSURFACEVOLUMEDATA_ATTR_SURFACEVALUES_DFLT);
    surfaceValues = surfaceValuesDefault = parseToSFFloatArray(fa);

    setContent("\n\t\t<!--TODO add X3DVolumeRenderStyleNode renderStyle nodes and X3DTexture3DNode voxels node and segmentIdentifiers node here-->\n\t");
  }
  
  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    String[] sa;
    attr = root.getAttribute(ISOSURFACEVOLUMEDATA_ATTR_DIMENSIONS_NAME);
    if (attr != null) {
      sa = parse3(attr.getValue());
      dimensionsX = new SFFloat(sa[0], null, null);
      dimensionsY = new SFFloat(sa[1], null, null);
      dimensionsZ = new SFFloat(sa[2], null, null);
    }
    attr = root.getAttribute(ISOSURFACEVOLUMEDATA_ATTR_BBOXCENTER_NAME);
    if (attr != null) {
      sa = parse3(attr.getValue());
      bboxCenterX = new SFFloat(sa[0], null, null);
      bboxCenterY = new SFFloat(sa[1], null, null);
      bboxCenterZ = new SFFloat(sa[2], null, null);
    }
    attr = root.getAttribute(ISOSURFACEVOLUMEDATA_ATTR_BBOXSIZE_NAME);
    if (attr != null) {
      sa = parse3(attr.getValue());
      bboxSizeX = new SFFloat(sa[0], null, null);
      bboxSizeY = new SFFloat(sa[1], null, null);
      bboxSizeZ = new SFFloat(sa[2], null, null);
    }
    attr = root.getAttribute(ISOSURFACEVOLUMEDATA_ATTR_CONTOURSTEPSIZE_NAME);
    if (attr != null) {
        contourStepSize  = new SFFloat(attr.getValue(), null, null);
    }
    attr = root.getAttribute(ISOSURFACEVOLUMEDATA_ATTR_SURFACETOLERANCE_NAME);
    if (attr != null) {
        surfaceTolerance = new SFFloat(attr.getValue(), 0.0f, null);
    }
    attr = root.getAttribute(ISOSURFACEVOLUMEDATA_ATTR_SURFACEVALUES_NAME);
    if (attr != null) {
      sa = parseX(attr.getValue());
      surfaceValues = parseToSFFloatArray(sa);
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

    if (ISOSURFACEVOLUMEDATA_ATTR_DIMENSIONS_REQD ||
           (!dimensionsX.equals(dimensionsXDefault) ||
            !dimensionsY.equals(dimensionsYDefault) ||
            !dimensionsZ.equals(dimensionsZDefault))) {
      sb.append(" ");
      sb.append(ISOSURFACEVOLUMEDATA_ATTR_DIMENSIONS_NAME);
      sb.append("='");
      sb.append(dimensionsX);
      sb.append(" ");
      sb.append(dimensionsY);
      sb.append(" ");
      sb.append(dimensionsZ);
      sb.append("'");
    }
    if (ISOSURFACEVOLUMEDATA_ATTR_BBOXCENTER_REQD ||
           (!bboxCenterX.equals(bboxCenterXDefault) ||
            !bboxCenterY.equals(bboxCenterYDefault) ||
            !bboxCenterZ.equals(bboxCenterZDefault))) {
      sb.append(" ");
      sb.append(ISOSURFACEVOLUMEDATA_ATTR_BBOXCENTER_NAME);
      sb.append("='");
      sb.append(bboxCenterX);
      sb.append(" ");
      sb.append(bboxCenterY);
      sb.append(" ");
      sb.append(bboxCenterZ);
      sb.append("'");
    }
    if (ISOSURFACEVOLUMEDATA_ATTR_BBOXSIZE_REQD ||
           (!bboxSizeX.equals(bboxSizeXDefault) ||
            !bboxSizeY.equals(bboxSizeYDefault) ||
            !bboxSizeZ.equals(bboxSizeZDefault))) {
      sb.append(" ");
      sb.append(ISOSURFACEVOLUMEDATA_ATTR_BBOXSIZE_NAME);
      sb.append("='");
      sb.append(bboxSizeX);
      sb.append(" ");
      sb.append(bboxSizeY);
      sb.append(" ");
      sb.append(bboxSizeZ);
      sb.append("'");
    }
    if (ISOSURFACEVOLUMEDATA_ATTR_CONTOURSTEPSIZE_REQD ||
           (!contourStepSize.equals(contourStepSizeDefault))) {
      sb.append(" ");
      sb.append(ISOSURFACEVOLUMEDATA_ATTR_CONTOURSTEPSIZE_NAME);
      sb.append("='");
      sb.append(contourStepSize);
      sb.append("'");
    }
    if (ISOSURFACEVOLUMEDATA_ATTR_SURFACETOLERANCE_REQD ||
           (!contourStepSize.equals(contourStepSizeDefault))) {
      sb.append(" ");
      sb.append(ISOSURFACEVOLUMEDATA_ATTR_SURFACETOLERANCE_NAME);
      sb.append("='");
      sb.append(surfaceTolerance);
      sb.append("'");
    }
    if (ISOSURFACEVOLUMEDATA_ATTR_SURFACEVALUES_REQD || !arraysIdenticalOrNull(surfaceValues,surfaceValuesDefault)) {
      sb.append(" ");
      sb.append(ISOSURFACEVOLUMEDATA_ATTR_SURFACEVALUES_NAME);
      sb.append("='");
      sb.append(formatFloatArray(surfaceValues, insertCommas, insertLineBreaks));
      sb.append("'");
    }

    return sb.toString();
  }

  public String getSurfaceValues()
  {
    if(surfaceValues.length == 0)
      return ""; // nothing to start with
   
    StringBuilder surfaceValuesSB = new StringBuilder();
    
    for(int r=0; r < surfaceValues.length; r++) {
      surfaceValuesSB.append(surfaceValues[r]).append(" ");
    }
    return surfaceValuesSB.toString().trim();
  }
  
  public void setSurfaceValues(String[] saa)
  {
    if(saa.length == 0) {
      surfaceValues = new SFFloat[]{};
      return;
    }
    
    surfaceValues = new SFFloat[saa.length];
    for (int r=0; r < saa.length; r++) {
      if(saa[r].length()<= 0)
         saa[r] = "0";
      surfaceValues[r] = new SFFloat(saa[r]);
    }
  }

  public String getContourStepSize()
  {
    return contourStepSize.toString();
  }

  public void setContourStepSize(String contourStepSize)
  {
    this.contourStepSize = new SFFloat(contourStepSize, null, null);
  }
  
  public String getSurfaceTolerance()
  {
    return surfaceTolerance.toString();
  }

  public void setSurfaceTolerance(String surfaceTolerance)
  {
    this.surfaceTolerance = new SFFloat(surfaceTolerance, 0.0f, null);
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