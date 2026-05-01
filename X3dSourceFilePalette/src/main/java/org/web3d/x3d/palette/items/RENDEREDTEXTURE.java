/*
Copyright (c) 1995-2026 held by the author(s).  All rights reserved.
 
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

import java.util.Arrays;
import javax.swing.text.JTextComponent;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFDouble;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFInt32;
import static org.web3d.x3d.types.X3DSchemaData.*;
import org.web3d.x3d.types.X3DTexture2DNode;

/**
 * RENDEREDTEXTURE.java
 *
 * @author Don Brutzman
 * @version $Id$
 */
public class RENDEREDTEXTURE extends X3DTexture2DNode // X3DUrlOutputObject, X3DNetworkSensorNode
{
  private boolean   depthMap,    depthMapDefault;
  private String    description, descriptionDefault;
  private SFInt32[] dimensions,  dimensionsDefault;
  private boolean   enabled,     enabledDefault;
  
  private SFInt32   maximumNumberFrames, maximumNumberFramesDefault;
  private boolean   replaceImage, replaceImageDefault;
  private String    update, updateDefault;
  private SFDouble  updateInterval, updateIntervalDefault; // actually SFTime
  
  private String[]  urls, urlsDefault;
  private boolean   insertCommas, insertLineBreaks = false;
  
  public RENDEREDTEXTURE()
  {
  }

  @Override
  public String getElementName()
  {
    return RENDEREDTEXTURE_ELNAME;
  }

  @Override
  public void initialize()
  {
    depthMap     = depthMapDefault  = Boolean.parseBoolean(RENDEREDTEXTURE_ATTR_DEPTHMAP_DFLT);
    enabled      = enabledDefault   = Boolean.parseBoolean(RENDEREDTEXTURE_ATTR_ENABLED_DFLT);
    repeatS      = Boolean.parseBoolean(RENDEREDTEXTURE_ATTR_REPEATS_DFLT);
    repeatT      = Boolean.parseBoolean(RENDEREDTEXTURE_ATTR_REPEATT_DFLT);
    replaceImage = replaceImageDefault = Boolean.parseBoolean(RENDEREDTEXTURE_ATTR_REPLACEIMAGE_DFLT);
    
    description  = descriptionDefault = RENDEREDTEXTURE_ATTR_DESCRIPTION_DFLT; // X3D4.0
    dimensions   = dimensionsDefault = new SFInt32[]{buildSFInt32(RENDEREDTEXTURE_ATTR_DIMENSIONS_WIDTH_DFLT),
                                                     buildSFInt32(RENDEREDTEXTURE_ATTR_DIMENSIONS_HEIGHT_DFLT),
                                                     buildSFInt32(RENDEREDTEXTURE_ATTR_DIMENSIONS_DEPTH_DFLT)}; // actually MFInt32
    
    maximumNumberFrames = maximumNumberFramesDefault = new SFInt32(RENDEREDTEXTURE_ATTR_MAXIMUMNUMBERFRAMES_DFLT); // author assist when creating new Switch node
    
    update         = updateDefault         = RENDEREDTEXTURE_ATTR_UPDATE_DFLT;
    updateInterval = updateIntervalDefault = new SFDouble(RENDEREDTEXTURE_ATTR_UPDATEINTERVAL_DFLT,0.0,null);
    
    if(RENDEREDTEXTURE_ATTR_URL_DFLT.length() > 0)
      urls = urlsDefault = parseUrlsIntoStringArray(RENDEREDTEXTURE_ATTR_URL_DFLT);
    else
      urls = urlsDefault = new String[0];
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    
    org.jdom.Attribute attr = root.getAttribute(RENDEREDTEXTURE_ATTR_DESCRIPTION_NAME);
    if (attr != null)
      description = attr.getValue();

    attr = root.getAttribute(RENDEREDTEXTURE_ATTR_DEPTHMAP_NAME);
    if (attr != null)
      depthMap = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(RENDEREDTEXTURE_ATTR_ENABLED_NAME);
    if (attr != null)
      enabled = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(RENDEREDTEXTURE_ATTR_REPEATS_NAME);
    if (attr != null)
      repeatS = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(RENDEREDTEXTURE_ATTR_REPEATS_NAME);
    if (attr != null)
      repeatS = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(RENDEREDTEXTURE_ATTR_REPEATT_NAME);
    if (attr != null)  
      repeatT = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(RENDEREDTEXTURE_ATTR_REPLACEIMAGE_NAME);
    if (attr != null)  
      replaceImage = Boolean.parseBoolean(attr.getValue());
    
    attr = root.getAttribute(RENDEREDTEXTURE_ATTR_DIMENSIONS_NAME);
    if (attr != null)  
      buildDimensions(attr.getValue());
    attr = root.getAttribute(RENDEREDTEXTURE_ATTR_MAXIMUMNUMBERFRAMES_NAME);
    if (attr != null)  
      maximumNumberFrames = new SFInt32(attr.getValue(), 0, null);
    attr = root.getAttribute(RENDEREDTEXTURE_ATTR_UPDATE_NAME);
    if (attr != null)  
      update = attr.getValue();
    attr = root.getAttribute(RENDEREDTEXTURE_ATTR_UPDATEINTERVAL_NAME);
    if (attr != null)  
      updateInterval = new SFDouble(attr.getValue(), 0.0, null);
    
    attr.getValue();
    attr = root.getAttribute(RENDEREDTEXTURE_ATTR_URL_NAME);
    if (attr != null)  
    {
         urls     = parseUrlsIntoStringArray(attr.getValue());

         if (attr.getValue().contains(","))  insertCommas     = true;
         if (attr.getValue().contains("\n") ||
             attr.getValue().contains("\r")) insertLineBreaks = true; // TODO not working, line breaks not being passed from JDOM
         if (insertCommas)                   insertLineBreaks = true; // workaround default, if commas were present then most likely lineBreaks also
    }
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return RENDEREDTEXTURECustomizer.class;
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    
    if (RENDEREDTEXTURE_ATTR_DEPTHMAP_REQD || depthMap != Boolean.parseBoolean(RENDEREDTEXTURE_ATTR_DEPTHMAP_DFLT)) {
      sb.append(" ");
      sb.append(RENDEREDTEXTURE_ATTR_DEPTHMAP_NAME);
      sb.append("='");
      sb.append(depthMap);
      sb.append("'");
    }
    if (RENDEREDTEXTURE_ATTR_DESCRIPTION_REQD || !description.equals(descriptionDefault)) {
      sb.append(" ");
      sb.append(RENDEREDTEXTURE_ATTR_DESCRIPTION_NAME);
      sb.append("='");
      sb.append(description);
      sb.append("'");  
    }
    if (RENDEREDTEXTURE_ATTR_DIMENSIONS_REQD || !dimensions.equals(dimensionsDefault)) {
      sb.append(" ");
      sb.append(RENDEREDTEXTURE_ATTR_DIMENSIONS_NAME);
      sb.append("='");
      sb.append(dimensionsToString());
      sb.append("'");  
    }
    if (RENDEREDTEXTURE_ATTR_ENABLED_REQD || enabled != Boolean.parseBoolean(RENDEREDTEXTURE_ATTR_ENABLED_DFLT)) {
      sb.append(" ");
      sb.append(RENDEREDTEXTURE_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }
    if (RENDEREDTEXTURE_ATTR_MAXIMUMNUMBERFRAMES_REQD || (maximumNumberFrames != maximumNumberFramesDefault)) {
      sb.append(" ");
      sb.append(RENDEREDTEXTURE_ATTR_MAXIMUMNUMBERFRAMES_NAME);
      sb.append("='");
      sb.append(getMaximumNumberFrames());
      sb.append("'");
    }
    if (RENDEREDTEXTURE_ATTR_REPEATS_REQD || repeatS != Boolean.parseBoolean(RENDEREDTEXTURE_ATTR_REPEATS_DFLT)) {
      sb.append(" ");
      sb.append(RENDEREDTEXTURE_ATTR_REPEATS_NAME);
      sb.append("='");
      sb.append(repeatS);
      sb.append("'");
    }
    if (RENDEREDTEXTURE_ATTR_REPEATT_REQD || repeatT != Boolean.parseBoolean(RENDEREDTEXTURE_ATTR_REPEATT_DFLT)) {
      sb.append(" ");
      sb.append(RENDEREDTEXTURE_ATTR_REPEATT_NAME);
      sb.append("='");
      sb.append(repeatT);
      sb.append("'");
    }
    if (RENDEREDTEXTURE_ATTR_REPLACEIMAGE_REQD || (replaceImage != replaceImageDefault)) {
      sb.append(" ");
      sb.append(RENDEREDTEXTURE_ATTR_REPLACEIMAGE_NAME);
      sb.append("='");
      sb.append(replaceImage);
      sb.append("'");
    }
    if (RENDEREDTEXTURE_ATTR_UPDATE_REQD || update != updateDefault) {
      sb.append(" ");
      sb.append(RENDEREDTEXTURE_ATTR_UPDATE_NAME);
      sb.append("='");
      sb.append(update);
      sb.append("'");
    }
    if (RENDEREDTEXTURE_ATTR_URL_REQD || (!Arrays.equals(urls, urlsDefault) && (urls.length > 0))) {
      sb.append(" ");
      sb.append(RENDEREDTEXTURE_ATTR_URL_NAME);
      sb.append("='");
      sb.append(formatStringArray(urls, insertCommas, insertLineBreaks));
      sb.append("'");    
    }

    return sb.toString();
  }

  private void buildDimensions(String s)
  {
    String[] sa = s.replace(',', ' ').trim().split("\\s");
    if (sa.length != 3)
    {
        sa    = new String[3];
        sa[0] = RENDEREDTEXTURE_ATTR_DIMENSIONS_HEIGHT_DFLT;
        sa[1] = RENDEREDTEXTURE_ATTR_DIMENSIONS_WIDTH_DFLT;
        sa[2] = RENDEREDTEXTURE_ATTR_DIMENSIONS_DEPTH_DFLT;
        if (sa.length != 0)
        {
            System.err.println("** incorrect value found for RenderedTexture dimensions='" + s + "' must be empty or have length of 3, using default dimensions='" +
                    RENDEREDTEXTURE_ATTR_DIMENSIONS_HEIGHT_DFLT + " " + 
                    RENDEREDTEXTURE_ATTR_DIMENSIONS_WIDTH_DFLT  + " " + 
                    RENDEREDTEXTURE_ATTR_DIMENSIONS_DEPTH_DFLT  + "'");
        }
    }
    dimensions = new SFInt32[3];
    for (int i = 0; i < sa.length; i++)
    {
      dimensions[i] = new SFInt32(sa[i], 0, null);
    }
  }
 
  private String dimensionsToString()
  {
    StringBuilder sb = new StringBuilder();
    for(SFInt32 sf : dimensions)
    {
      sb.append(sf.toString());
      sb.append(" ");
    }
    return sb.toString().trim();
  }
  public String getUpdate()
  {
    return update;
  }

  public void setUpdate(String update)
  {
    this.update = update;
  }

    /**
     * @return the fftSize
     */
    public String getMaximumNumberFrames() {
        return maximumNumberFrames.toString();
    }

    /**
     * @param newMaximumNumberFrames the maximumNumberFrames to set
     */
    public void setMaximumNumberFrames(String newMaximumNumberFrames) {
        maximumNumberFrames = new SFInt32(newMaximumNumberFrames, null, null);
    }
  
  public String[] getUrls()
  {
    String[]ret = new String[urls.length];
    System.arraycopy(urls,0,ret,0,urls.length);
    return ret;
  }
  
  public void setUrls(String[] urlarray)
  {
    urls = new String[urlarray.length];
    System.arraycopy(urlarray, 0, urls, 0, urlarray.length);
  }

    /**
     * @return the depthMap value
     */
    public boolean isDepthMap() {
        return depthMap;
    }

    /**
     * @param depthMap the depthMap value to set
     */
    public void setDepthMap(boolean depthMap) {
        this.depthMap = depthMap;
    }
    
    /**
     * @return the replaceImage value
     */
    public boolean isReplaceImage() {
        return replaceImage;
    }

    /**
     * @param replaceImage the replaceImage value to set
     */
    public void setReplaceImage(boolean replaceImage) {
        this.replaceImage = replaceImage;
    }
    
    public String getUpdateInterval()
    {
      return updateInterval.toString();
    }

    public void setUpdateInterval(String newUpdateInterval)
    {
      this.updateInterval = new SFDouble(newUpdateInterval,0.0,null);
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
  
    public String getDescription()
    {
        return description;
    }

    public void setDescription(String newDescription)
    {
        this.description = newDescription;
    }
}
