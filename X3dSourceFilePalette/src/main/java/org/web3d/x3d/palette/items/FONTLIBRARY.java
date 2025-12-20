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

import java.util.Arrays;
import javax.swing.text.JTextComponent;
import static org.web3d.x3d.palette.X3DPaletteUtilities.escapeXmlCharacters;
import static org.web3d.x3d.palette.items.BaseX3DElement.parseUrlsIntoStringArray;
import org.web3d.x3d.types.X3DFontLibraryNode;
import org.web3d.x3d.types.X3DPrimitiveTypes;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * FONTLIBRARY.java
 *
 * @author Don Brutzman
 * @version $Id$
 */
public class FONTLIBRARY extends X3DFontLibraryNode
{
    private X3DPrimitiveTypes.SFDouble autoRefresh, autoRefreshDefault; // SFTime
    private X3DPrimitiveTypes.SFDouble autoRefreshTimeLimit, autoRefreshTimeLimitDefault; // SFTime
    private String   description;
    private String   family;
    private boolean  load, loadDefault;
    private String[] urls, urlsDefault;

  public FONTLIBRARY()
  {
  }

  @Override
  public String getElementName()
  {
    return FONTLIBRARY_ELNAME;
  }

  @Override
  public void initialize()
  {
    autoRefresh          = autoRefreshDefault                   = new X3DPrimitiveTypes.SFDouble(FONTLIBRARY_ATTR_AUTOREFRESH_DFLT,0.0,null);
    autoRefreshTimeLimit = autoRefreshTimeLimitDefault          = new X3DPrimitiveTypes.SFDouble(FONTLIBRARY_ATTR_AUTOREFRESHTIMELIMIT_DFLT,0.0,null);
    description          = FONTLIBRARY_ATTR_DESCRIPTION_DFLT;
    family               = FONTLIBRARY_ATTR_FAMILY_DFLT;
    load                 = loadDefault                          = Boolean.parseBoolean(FONTLIBRARY_ATTR_LOAD_DFLT);
    if(FONTLIBRARY_ATTR_URL_DFLT.length()>0)
      urls = urlsDefault = parseUrlsIntoStringArray(FONTLIBRARY_ATTR_URL_DFLT);
    else
      urls = urlsDefault = new String[0];
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(FONTLIBRARY_ATTR_AUTOREFRESH_NAME);
    if (attr != null)
      autoRefresh = new X3DPrimitiveTypes.SFDouble(attr.getValue(), 0.0, null);
    attr = root.getAttribute(FONTLIBRARY_ATTR_AUTOREFRESHTIMELIMIT_NAME);
    if (attr != null)
      autoRefreshTimeLimit = new X3DPrimitiveTypes.SFDouble(attr.getValue(), 0.0, null);
    attr = root.getAttribute(FONTLIBRARY_ATTR_DESCRIPTION_NAME);
    if (attr != null)
      description = attr.getValue();
    attr = root.getAttribute(FONTLIBRARY_ATTR_FAMILY_NAME);
    if(attr != null)
      family   = attr.getValue();
    attr = root.getAttribute(FONTLIBRARY_ATTR_LOAD_NAME);
    if (attr != null)
      load = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(FONTLIBRARY_ATTR_URL_NAME);
    if (attr != null)
      urls = parseUrlsIntoStringArray(attr.getValue());
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return FONTLIBRARYCustomizer.class;
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (FONTLIBRARY_ATTR_AUTOREFRESH_REQD || !autoRefresh.equals(autoRefreshDefault)) {
      sb.append(" ");
      sb.append(FONTLIBRARY_ATTR_AUTOREFRESH_NAME);
      sb.append("='");
      sb.append(autoRefresh);
      sb.append("'");
    }
    if (FONTLIBRARY_ATTR_AUTOREFRESHTIMELIMIT_REQD || !autoRefreshTimeLimit.equals(autoRefreshTimeLimitDefault)) {
      sb.append(" ");
      sb.append(FONTLIBRARY_ATTR_AUTOREFRESHTIMELIMIT_NAME);
      sb.append("='");
      sb.append(autoRefreshTimeLimit);
      sb.append("'");
    }
    if (FONTLIBRARY_ATTR_DESCRIPTION_REQD || !description.equals(FONTLIBRARY_ATTR_DESCRIPTION_DFLT)) {
      sb.append(" ");
      sb.append(FONTLIBRARY_ATTR_DESCRIPTION_NAME);
      sb.append("='");
      sb.append(escapeXmlCharacters(description));
      sb.append("'");
    }
    if (FONTLIBRARY_ATTR_FAMILY_REQD || !family.replace("\"", "").equals(FONTLIBRARY_ATTR_FAMILY_DFLT)) {
      sb.append(" ");
      sb.append(FONTLIBRARY_ATTR_FAMILY_NAME);
      sb.append("='");
      sb.append(family); // conversion to MFString handled by customizer
      sb.append("'");
    }
    if (AUDIOCLIP_ATTR_LOAD_REQD || load != loadDefault) {
      sb.append(" ");
      sb.append(AUDIOCLIP_ATTR_LOAD_NAME);
      sb.append("='");
      sb.append(load);
      sb.append("'");
    }
    if (AUDIOCLIP_ATTR_URL_REQD || (!Arrays.equals(urls, urlsDefault) && (urls.length > 0))) {
      sb.append(" ");
      sb.append(AUDIOCLIP_ATTR_URL_NAME);
      sb.append("='");
      sb.append(formatStringArray(urls));
      sb.append("'");
    }
    return sb.toString();
  }
    /**
     * accessor method for field
     * @return value
     */
      public String getDescription()
      {
        return description;
      }

    /**
     * accessor method for field
     * @param newDescription value of interest
     */
      public void setDescription(String newDescription)
      {
        this.description = newDescription;
      }
  
    // Accessors for customizer
    public String getFamily()
    {
      return family;
    }

    public void setFamily(String family)
    {
      this.family = family;
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

    public String[] getUrls()
    {
      String[] urlArray = new String[urls.length];
      System.arraycopy(urls, 0, urlArray, 0, urls.length);
      return urlArray;
    }

    public void setUrls(String[] urlArray)
    {
      urls = new String[urlArray.length];
      System.arraycopy(urlArray, 0, this.urls, 0, urlArray.length);
    }

    /**
     * @return the load field
     */
    public boolean isLoad()
    {
      return load;
    }

    /**
     * @param newLoad the load field to set
     */
    public void setLoad(boolean newLoad)
    {
      load = newLoad;
    }
  
  }
