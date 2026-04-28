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
import org.web3d.x3d.types.X3DGroupingNode;
import org.web3d.x3d.types.X3DPrimitiveTypes;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * INLINEGEOMETRY.java
 * 
 * @author Don Brutzman
 * @version $Id$
 */
public class INLINEGEOMETRY extends X3DGroupingNode
{
  private X3DPrimitiveTypes.SFDouble autoRefresh, autoRefreshDefault; // SFTime
  private X3DPrimitiveTypes.SFDouble autoRefreshTimeLimit, autoRefreshTimeLimitDefault; // SFTime
  private boolean  load,   loadDefault;
  private boolean  smooth, smoothDefault;
  private boolean  solid,  solidDefault;
  private String[] urls, urlsDefault;
  private boolean  insertCommas, insertLineBreaks = false;
  private String   expectedProfile = "";
  
  private String   description, descriptionDefault; // X3D4.0

  public INLINEGEOMETRY()
  {
  }

  @Override
  public String getElementName()
  {
    return INLINEGEOMETRY_ELNAME;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return INLINEGEOMETRYCustomizer.class;
  }
  
  @Override
  public void initialize()
  {
    autoRefresh          = autoRefreshDefault                   = new X3DPrimitiveTypes.SFDouble(INLINEGEOMETRY_ATTR_AUTOREFRESH_DFLT,0.0,null);
    autoRefreshTimeLimit = autoRefreshTimeLimitDefault          = new X3DPrimitiveTypes.SFDouble(INLINEGEOMETRY_ATTR_AUTOREFRESHTIMELIMIT_DFLT,0.0,null);
    
    description = descriptionDefault = INLINEGEOMETRY_ATTR_DESCRIPTION_DFLT; // X3D4.0
    
    load   = loadDefault   = Boolean.parseBoolean(INLINEGEOMETRY_ATTR_LOAD_DFLT);
    smooth = smoothDefault = Boolean.parseBoolean(INLINEGEOMETRY_ATTR_SMOOTH_DFLT);
    solid  = solidDefault  = Boolean.parseBoolean(INLINEGEOMETRY_ATTR_SOLID_DFLT);
    
    if(INLINEGEOMETRY_ATTR_URL_DFLT.length()>0)
      urls = urlsDefault = parseUrlsIntoStringArray(INLINEGEOMETRY_ATTR_URL_DFLT);
    else
      urls = urlsDefault = new String[0];
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;
    
    attr = root.getAttribute(INLINEGEOMETRY_ATTR_AUTOREFRESH_NAME);
    if (attr != null)
      autoRefresh = new X3DPrimitiveTypes.SFDouble(attr.getValue(), 0.0, null);
    attr = root.getAttribute(INLINEGEOMETRY_ATTR_AUTOREFRESHTIMELIMIT_NAME);
    if (attr != null)
      autoRefreshTimeLimit = new X3DPrimitiveTypes.SFDouble(attr.getValue(), 0.0, null);
    
    attr = root.getAttribute(INLINEGEOMETRY_ATTR_DESCRIPTION_NAME);
    if (attr != null)
      description = attr.getValue();

    attr = root.getAttribute(INLINEGEOMETRY_ATTR_LOAD_NAME);
    if(attr != null)
      load = Boolean.parseBoolean(attr.getValue());

    attr = root.getAttribute(INLINEGEOMETRY_ATTR_SMOOTH_NAME);
    if(attr != null)
      smooth = Boolean.parseBoolean(attr.getValue());

    attr = root.getAttribute(INLINEGEOMETRY_ATTR_SOLID_NAME);
    if(attr != null)
      solid = Boolean.parseBoolean(attr.getValue());
    
    attr = root.getAttribute(INLINEGEOMETRY_ATTR_URL_NAME);
    if(attr != null)
    {
         urls = parseUrlsIntoStringArray(attr.getValue());

         if (attr.getValue().contains(","))  insertCommas     = true;
         if (attr.getValue().contains("\n") ||
             attr.getValue().contains("\r")) insertLineBreaks = true; // TODO not working, line breaks not being passed from JDOM
         if (insertCommas)                   insertLineBreaks = true; // workaround default, if commas were present then most likely lineBreaks also
    }
    
    String containedContent = this.getContent();
    if      (containedContent.contains("<MetadataString name='profile' value='\"Immersive\"'"))
         setExpectedProfile("Immersive");
    else if (containedContent.contains("<MetadataString name='profile' value='\"Interchange\"'"))
         setExpectedProfile("Interchange");
    else if (containedContent.contains("<MetadataString name='profile' value='\"Interactive\"'"))
         setExpectedProfile("Interactive");
    else if (containedContent.contains("<MetadataString name='profile' value='\"CADInteractive\"'"))
         setExpectedProfile("Interactive");
    else if (containedContent.contains("<MetadataString name='profile' value='\"Full\"'"))
         setExpectedProfile("Full");
    else if (containedContent.contains("<MetadataString name='profile' value='\"Core\"'"))
         setExpectedProfile("Core");
    // otherwise unknown
  }
   
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (INLINEGEOMETRY_ATTR_AUTOREFRESH_REQD || !autoRefresh.equals(autoRefreshDefault)) {
      sb.append(" ");
      sb.append(INLINEGEOMETRY_ATTR_AUTOREFRESH_NAME);
      sb.append("='");
      sb.append(autoRefresh);
      sb.append("'");
    }
    if (INLINEGEOMETRY_ATTR_AUTOREFRESHTIMELIMIT_REQD || !autoRefreshTimeLimit.equals(autoRefreshTimeLimitDefault)) {
      sb.append(" ");
      sb.append(INLINEGEOMETRY_ATTR_AUTOREFRESHTIMELIMIT_NAME);
      sb.append("='");
      sb.append(autoRefreshTimeLimit);
      sb.append("'");
    }
    
    if (INLINEGEOMETRY_ATTR_DESCRIPTION_REQD || !description.equals(descriptionDefault)) {
      sb.append(" ");
      sb.append(INLINEGEOMETRY_ATTR_DESCRIPTION_NAME);
      sb.append("='");
      sb.append(description);
      sb.append("'");
    }
    if (INLINEGEOMETRY_ATTR_LOAD_REQD || load != loadDefault) {
      sb.append(" ");
      sb.append(INLINEGEOMETRY_ATTR_LOAD_NAME);
      sb.append("='");
      sb.append(load);
      sb.append("'");
    }
    if (INLINEGEOMETRY_ATTR_SMOOTH_REQD || smooth != smoothDefault) {
      sb.append(" ");
      sb.append(INLINEGEOMETRY_ATTR_SMOOTH_NAME);
      sb.append("='");
      sb.append(smooth);
      sb.append("'");
    }
    if (INLINEGEOMETRY_ATTR_SOLID_REQD || solid != solidDefault) {
      sb.append(" ");
      sb.append(INLINEGEOMETRY_ATTR_SOLID_NAME);
      sb.append("='");
      sb.append(solid);
      sb.append("'");
    }
    if (INLINEGEOMETRY_ATTR_URL_REQD || (!Arrays.equals(urls, urlsDefault) && (urls.length > 0))) {
      sb.append(" ");
      sb.append(INLINEGEOMETRY_ATTR_URL_NAME);
      sb.append("='");
      sb.append(formatStringArray(urls, insertCommas, insertLineBreaks));
      sb.append("'");
    }

    return sb.toString();
  }

  public boolean isLoad()
  {
    return load;
  }

  public void setLoad(boolean load)
  {
    this.load = load;
  }

  public boolean isSmooth()
  {
    return smooth;
  }

  public void setSmooth(boolean smooth)
  {
    this.smooth = smooth;
  }

  public boolean isSolid()
  {
    return solid;
  }

  public void setSolid(boolean solid)
  {
    this.solid = solid;
  }

  public String[] getUrls()
  {
    String[] ret = new String[urls.length];
    System.arraycopy(urls,0,ret,0,urls.length);
    return ret;
  }

  public void setUrls(String[] urlarray)
  {
    urls = new String[urlarray.length];
    System.arraycopy(urlarray, 0, this.urls, 0, urlarray.length);
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

    /**
     * @return the expectedProfile
     */
    public String getExpectedProfile() {
        return expectedProfile;
    }

    /**
     * @param expectedProfile the expectedProfile to set
     */
    public void setExpectedProfile(String expectedProfile) {
        this.expectedProfile = expectedProfile;
    }

    /**
     * @param newAutoRefresh the autoRefresh to set
     */
    public void setAutoRefresh(String newAutoRefresh) {
        autoRefresh = new X3DPrimitiveTypes.SFDouble(newAutoRefresh, 0.0, null);
    }
    /**
     * @return the autoRefresh
     */
    public String getAutoRefresh() {
        return autoRefresh.toString();
    }

    /**
     * @param newAutoRefreshTimeLimit the autoRefreshTimeLimit to set
     */
    public void setAutoRefreshTimeLimit(String newAutoRefreshTimeLimit) {
        autoRefreshTimeLimit = new X3DPrimitiveTypes.SFDouble(newAutoRefreshTimeLimit, 0.0, null);
    }
    /**
     * @return the autoRefreshTimeLimit
     */
    public String getAutoRefreshTimeLimit() {
        return autoRefreshTimeLimit.toString();
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