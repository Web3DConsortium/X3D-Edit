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

import java.util.Arrays;
import javax.swing.text.JTextComponent;

import org.web3d.x3d.types.X3DGroupingNode;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * INLINE.java
 * Created on Sep 26, 2007 2:30 PM
 * 
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 * 
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class INLINE extends X3DGroupingNode
{
  private boolean  load, loadDefault;
  private String[] urls, urlsDefault;
  private boolean  insertCommas, insertLineBreaks = false;
  private String   expectedProfile = "";

  public INLINE()
  {
  }

  @Override
  public String getElementName()
  {
    return INLINE_ELNAME;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return INLINECustomizer.class;
  }
  
  @Override
  public void initialize()
  {
    load = loadDefault = Boolean.parseBoolean(INLINE_ATTR_LOAD_DFLT);
    if(INLINE_ATTR_URL_DFLT.length()>0)
      urls = urlsDefault = parseUrlsIntoStringArray(INLINE_ATTR_URL_DFLT);
    else
      urls = urlsDefault = new String[0];
    
    String[] fa;
    fa = parse3(INLINE_ATTR_BBOXCENTER_DFLT);
    bboxCenterX = bboxCenterXDefault = new SFFloat(fa[0], null, null);
    bboxCenterY = bboxCenterYDefault = new SFFloat(fa[1], null, null);
    bboxCenterZ = bboxCenterZDefault = new SFFloat(fa[2], null, null);

    fa = parse3(INLINE_ATTR_BBOXSIZE_DFLT);
    bboxSizeX = bboxSizeXDefault = new SFFloat(fa[0], null, null);
    bboxSizeY = bboxSizeYDefault = new SFFloat(fa[1], null, null);
    bboxSizeZ = bboxSizeZDefault = new SFFloat(fa[2], null, null);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(INLINE_ATTR_LOAD_NAME);
    if(attr != null)
      load = Boolean.parseBoolean(attr.getValue());
    
    attr = root.getAttribute(INLINE_ATTR_URL_NAME);
    if(attr != null)
    {
         urls = parseUrlsIntoStringArray(attr.getValue());

         if (attr.getValue().contains(","))  insertCommas     = true;
         if (attr.getValue().contains("\n") ||
             attr.getValue().contains("\r")) insertLineBreaks = true; // TODO not working, line breaks not being passed from JDOM
         if (insertCommas)                   insertLineBreaks = true; // workaround default, if commas were present then most likely lineBreaks also
    }
    String[] fa;

    attr = root.getAttribute(INLINE_ATTR_BBOXCENTER_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      bboxCenterX = new SFFloat(fa[0], null, null);
      bboxCenterY = new SFFloat(fa[1], null, null);
      bboxCenterZ = new SFFloat(fa[2], null, null);
    }
    attr = root.getAttribute(INLINE_ATTR_BBOXSIZE_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      bboxSizeX = new SFFloat(fa[0], null, null);
      bboxSizeY = new SFFloat(fa[1], null, null);
      bboxSizeZ = new SFFloat(fa[2], null, null);
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
    if (INLINE_ATTR_BBOXCENTER_REQD ||
           (!bboxCenterX.equals(bboxCenterXDefault) ||
            !bboxCenterY.equals(bboxCenterYDefault) ||
            !bboxCenterZ.equals(bboxCenterZDefault))) {
      sb.append(" ");
      sb.append(INLINE_ATTR_BBOXCENTER_NAME);
      sb.append("='");
      sb.append(bboxCenterX);
      sb.append(" ");
      sb.append(bboxCenterY);
      sb.append(" ");
      sb.append(bboxCenterZ);
      sb.append("'");
    }
    if (INLINE_ATTR_BBOXSIZE_REQD ||
           (!bboxSizeX.equals(bboxSizeXDefault) ||
            !bboxSizeY.equals(bboxSizeYDefault) ||
            !bboxSizeZ.equals(bboxSizeZDefault))) {
      sb.append(" ");
      sb.append(INLINE_ATTR_BBOXSIZE_NAME);
      sb.append("='");
      sb.append(bboxSizeX);
      sb.append(" ");
      sb.append(bboxSizeY);
      sb.append(" ");
      sb.append(bboxSizeZ);
      sb.append("'");
    }
    if (INLINE_ATTR_LOAD_REQD || load != loadDefault) {
      sb.append(" ");
      sb.append(INLINE_ATTR_LOAD_NAME);
      sb.append("='");
      sb.append(load);
      sb.append("'");
    }    
    if (INLINE_ATTR_URL_REQD || (!Arrays.equals(urls, urlsDefault) && (urls.length > 0))) {
      sb.append(" ");
      sb.append(INLINE_ATTR_URL_NAME);
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
}