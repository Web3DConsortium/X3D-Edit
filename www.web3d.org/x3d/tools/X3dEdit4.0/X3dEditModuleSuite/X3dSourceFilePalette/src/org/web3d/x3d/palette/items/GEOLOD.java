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
import org.web3d.x3d.types.X3DGroupingNode;

import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * GEOLOD.java
 * Created on A6 December 2009
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class GEOLOD extends X3DGroupingNode
{
  private SFDouble centerX,centerXDefault;     // Together make one SFVec3F
  private SFDouble centerY,centerYDefault;
  private SFDouble centerZ,centerZDefault;

  private String[] child1Url, child1UrlDefault;
  private String[] child2Url, child2UrlDefault;
  private String[] child3Url, child3UrlDefault;
  private String[] child4Url, child4UrlDefault;
  private String[] rootUrl,   rootUrlDefault;
  private SFFloat  range,     rangeDefault;

  private String[] geoSystem;

  public GEOLOD()
  {
  }

  @Override
  public String getElementName()
  {
    return GEOLOD_ELNAME;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return GEOLODCustomizer.class;
  }

  @Override
  public void initialize()
  {
    String[] fa = parse3(GEOLOD_ATTR_CENTER_DFLT);
    centerX = centerXDefault = new SFDouble(fa[0], null, null);
    centerY = centerYDefault = new SFDouble(fa[1], null, null);
    centerZ = centerZDefault = new SFDouble(fa[2], null, null);

    fa = parse3(GEOLOD_ATTR_BBOXCENTER_DFLT);
    bboxCenterX = bboxCenterXDefault = new SFFloat(fa[0], null, null);
    bboxCenterY = bboxCenterYDefault = new SFFloat(fa[1], null, null);
    bboxCenterZ = bboxCenterZDefault = new SFFloat(fa[2], null, null);

    fa = parse3(GEOLOD_ATTR_BBOXSIZE_DFLT);
    bboxSizeX = bboxSizeXDefault = new SFFloat(fa[0], 0.0f, null, true);
    bboxSizeY = bboxSizeYDefault = new SFFloat(fa[1], 0.0f, null, true);
    bboxSizeZ = bboxSizeZDefault = new SFFloat(fa[2], 0.0f, null, true);

    if(GEOLOD_ATTR_CHILD1URL_DFLT.length()>0)
        setChild1Url(child1UrlDefault = parseUrlsIntoStringArray(GEOLOD_ATTR_CHILD1URL_DFLT));
    else
        setChild1Url(child1UrlDefault = new String[0]);
    
    if(GEOLOD_ATTR_CHILD2URL_DFLT.length()>0)
        setChild2Url(child2UrlDefault = parseUrlsIntoStringArray(GEOLOD_ATTR_CHILD2URL_DFLT));
    else
        setChild2Url(child2UrlDefault = new String[0]);
    
    if(GEOLOD_ATTR_CHILD3URL_DFLT.length()>0)
        setChild3Url(child3UrlDefault = parseUrlsIntoStringArray(GEOLOD_ATTR_CHILD3URL_DFLT));
    else
        setChild3Url(child3UrlDefault = new String[0]);
    
    if(GEOLOD_ATTR_CHILD4URL_DFLT.length()>0)
        setChild4Url(child4UrlDefault = parseUrlsIntoStringArray(GEOLOD_ATTR_CHILD4URL_DFLT));
    else
        setChild4Url(child4UrlDefault = new String[0]);
    
    if(GEOLOD_ATTR_ROOTURL_DFLT.length()>0)
        setRootUrl(rootUrlDefault = parseUrlsIntoStringArray(GEOLOD_ATTR_ROOTURL_DFLT));
    else
        setRootUrl(rootUrlDefault = new String[0]);

    range = rangeDefault = new SFFloat(GEOLOD_ATTR_RANGE_DFLT, 0.0f,null);

    geoSystem = GEOLOD_ATTR_GEOSYSTEM_DFLT;

    setContent("\n\t\t<!--TODO add children nodes here-->\n\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);

    String[] fa;
    org.jdom.Attribute attr = root.getAttribute(GEOLOD_ATTR_CENTER_NAME);
    if(attr != null) {
      fa = parse3(attr.getValue());
      centerX = new SFDouble(fa[0], null, null);
      centerY = new SFDouble(fa[1], null, null);
      centerZ = new SFDouble(fa[2], null, null);
    }
    attr = root.getAttribute(GEOLOD_ATTR_BBOXCENTER_NAME);
    if(attr != null) {
      fa = parse3(attr.getValue());
      bboxCenterX = new SFFloat(fa[0], null, null);
      bboxCenterY = new SFFloat(fa[1], null, null);
      bboxCenterZ = new SFFloat(fa[2], null, null);
    }
    attr = root.getAttribute(GEOLOD_ATTR_BBOXSIZE_NAME);
    if(attr != null) {
      fa = parse3(attr.getValue());
      bboxSizeX = new SFFloat(fa[0], 0.0f, null, true);
      bboxSizeY = new SFFloat(fa[1], 0.0f, null, true);
      bboxSizeZ = new SFFloat(fa[2], 0.0f, null, true);
    }

    attr = root.getAttribute(GEOLOD_ATTR_ROOTURL_NAME);
    if (attr != null)
        setRootUrl(parseUrlsIntoStringArray(attr.getValue()));
    attr = root.getAttribute(GEOLOD_ATTR_CHILD1URL_NAME);
    if (attr != null)
        setChild1Url(parseUrlsIntoStringArray(attr.getValue()));
    attr = root.getAttribute(GEOLOD_ATTR_CHILD2URL_NAME);
    if (attr != null)
        setChild2Url(parseUrlsIntoStringArray(attr.getValue()));
    attr = root.getAttribute(GEOLOD_ATTR_CHILD3URL_NAME);
    if (attr != null)
        setChild3Url(parseUrlsIntoStringArray(attr.getValue()));
    attr = root.getAttribute(GEOLOD_ATTR_CHILD4URL_NAME);
    if (attr != null)
        setChild4Url(parseUrlsIntoStringArray(attr.getValue()));

    attr = root.getAttribute(GEOLOD_ATTR_RANGE_NAME);
    if(attr != null)
      range = new SFFloat(attr.getValue(), 0.0f, null);

    attr = root.getAttribute(GEOLOD_ATTR_GEOSYSTEM_NAME);
    if (attr != null)
      geoSystem = splitStringIntoStringArray(attr.getValue());
  }

  @Override
  public String createAttributes()
  {
    StringBuffer sb = new StringBuffer();

    if (GEOLOD_ATTR_BBOXCENTER_REQD ||
           (!bboxCenterX.equals(bboxCenterXDefault) ||
            !bboxCenterY.equals(bboxCenterYDefault) ||
            !bboxCenterZ.equals(bboxCenterZDefault)))
    {
      sb.append(" ");
      sb.append(GEOLOD_ATTR_BBOXCENTER_NAME);
      sb.append("='");
      sb.append(bboxCenterX);
      sb.append(" ");
      sb.append(bboxCenterY);
      sb.append(" ");
      sb.append(bboxCenterZ);
      sb.append("'");
    }
    if (GEOLOD_ATTR_BBOXSIZE_REQD ||
           (!bboxSizeX.equals(bboxSizeXDefault) ||
            !bboxSizeY.equals(bboxSizeYDefault) ||
            !bboxSizeZ.equals(bboxSizeZDefault)))
    {
      sb.append(" ");
      sb.append(GEOLOD_ATTR_BBOXSIZE_NAME);
      sb.append("='");
      sb.append(bboxSizeX);
      sb.append(" ");
      sb.append(bboxSizeY);
      sb.append(" ");
      sb.append(bboxSizeZ);
      sb.append("'");
    }
    if (GEOLOD_ATTR_CENTER_REQD ||
           (!centerX.equals(centerXDefault) ||
            !centerY.equals(centerYDefault) ||
            !centerZ.equals(centerZDefault)))
    {
      sb.append(" ");
      sb.append(GEOLOD_ATTR_CENTER_NAME);
      sb.append("='");
      
      sb.append(centerX.toString());
      sb.append(" ");
      sb.append(centerY.toString());
      sb.append(" ");
      sb.append(centerZ.toString());
      sb.append("'");
    }
    if (GEOLOD_ATTR_CHILD1URL_REQD || (!child1Url.equals(child1UrlDefault) && getChild1Url().length>0)) {
      sb.append(" ");
      sb.append(GEOLOD_ATTR_CHILD1URL_NAME);
      sb.append("='");
      sb.append(formatStringArray(getChild1Url()));
      sb.append("'");
    }
    if (GEOLOD_ATTR_CHILD2URL_REQD || (!child2Url.equals(child2UrlDefault) && getChild2Url().length>0)) {
      sb.append(" ");
      sb.append(GEOLOD_ATTR_CHILD2URL_NAME);
      sb.append("='");
      sb.append(formatStringArray(getChild2Url()));
      sb.append("'");
    }
    if (GEOLOD_ATTR_CHILD3URL_REQD || (!child3Url.equals(child3UrlDefault) && getChild3Url().length>0)) {
      sb.append(" ");
      sb.append(GEOLOD_ATTR_CHILD3URL_NAME);
      sb.append("='");
      sb.append(formatStringArray(getChild3Url()));
      sb.append("'");
    }
    if (GEOLOD_ATTR_CHILD4URL_REQD || (!child4Url.equals(child4UrlDefault) && getChild4Url().length>0)) {
      sb.append(" ");
      sb.append(GEOLOD_ATTR_CHILD4URL_NAME);
      sb.append("='");
      sb.append(formatStringArray(getChild4Url()));
      sb.append("'");
    }
    if (GEOLOD_ATTR_GEOSYSTEM_REQD || !geoSystemEqualsDefault(geoSystem))
    {
      sb.append(" ");
      sb.append(GEOLOD_ATTR_GEOSYSTEM_NAME);
      sb.append("='");
      for (String s : geoSystem) {
        sb.append("\"");
        sb.append(s);
        sb.append("\" ");
      }
      sb.setLength(sb.length() - 1); // last space
      sb.append("'");
    }
    if (GEOLOD_ATTR_ROOTURL_REQD || (!rootUrl.equals(rootUrlDefault) && getRootUrl().length>0)) {
      sb.append(" ");
      sb.append(GEOLOD_ATTR_ROOTURL_NAME);
      sb.append("='");
      sb.append(formatStringArray(getRootUrl()));
      sb.append("'");
    }
    if (GEOLOD_ATTR_RANGE_REQD || !range.equals(rangeDefault)) {
      sb.append(" range='");
      sb.append(range.toString());
      sb.append("'");
    }
    return sb.toString();
  }

  public String getCenterX()
  {
    return centerX.toString();
  }

  public void setCenterX(String centerX)
  {
    this.centerX = new SFDouble(centerX, null, null);
  }

  public String getCenterY()
  {
    return centerY.toString();
  }

  public void setCenterY(String centerY)
  {
    this.centerY = new SFDouble(centerY, null, null);
  }

  public String getCenterZ()
  {
    return centerZ.toString();
  }

  public void setCenterZ(String centerZ)
  {
    this.centerZ = new SFDouble(centerZ, null, null);
  }

  public String getRange()
  {
    return range.toString();
  }

  public void setRange(String newRange)
  {
    this.range = new SFFloat (newRange, 0.0f, null);
  }

    /**
     * @return the child1Url
     */
    public String[] getChild1Url() {
        return child1Url;
    }

    /**
     * @param child1Url the child1Url to set
     */
    public void setChild1Url(String[] child1Url) {
        this.child1Url = child1Url;
    }

    /**
     * @return the child2Url
     */
    public String[] getChild2Url() {
        return child2Url;
    }

    /**
     * @param child2Url the child2Url to set
     */
    public void setChild2Url(String[] child2Url) {
        this.child2Url = child2Url;
    }

    /**
     * @return the child3Url
     */
    public String[] getChild3Url() {
        return child3Url;
    }

    /**
     * @param child3Url the child3Url to set
     */
    public void setChild3Url(String[] child3Url) {
        this.child3Url = child3Url;
    }

    /**
     * @return the child4Url
     */
    public String[] getChild4Url() {
        return child4Url;
    }

    /**
     * @param child4Url the child4Url to set
     */
    public void setChild4Url(String[] child4Url) {
        this.child4Url = child4Url;
    }

    /**
     * @return the rootUrl
     */
    public String[] getRootUrl() {
        return rootUrl;
    }

    /**
     * @param rootUrl the rootUrl to set
     */
    public void setRootUrl(String[] rootUrl) {
        this.rootUrl = rootUrl;
    }

  public String getGeoSystem()
  {
      StringBuilder sb = new StringBuilder();

      for (String s : geoSystem) {
        sb.append(s);
        sb.append(" ");
      }
      sb.setLength(sb.length() - 1); // last space

      return sb.toString();
  }

  public void setGeoSystem(String[] geoSystem)
  {
    this.geoSystem = geoSystem;
  }
}
