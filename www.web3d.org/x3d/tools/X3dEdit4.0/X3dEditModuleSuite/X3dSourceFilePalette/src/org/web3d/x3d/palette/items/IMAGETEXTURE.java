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
import org.web3d.x3d.types.X3DTexture2DNode;

import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * IMAGETEXTURE.java
 * Created on Sep 10, 2007, 3:05 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class IMAGETEXTURE extends X3DTexture2DNode
{
  private String[]  urls, urlsDefault;
  private boolean insertCommas, insertLineBreaks = false;
  
  public IMAGETEXTURE()
  {
  }

  @Override
  public String getElementName()
  {
    return IMAGETEXTURE_ELNAME;
  }

  @Override
  public void initialize()
  {
    repeatS = Boolean.parseBoolean(IMAGETEXTURE_ATTR_REPEATS_DFLT);
    repeatT = Boolean.parseBoolean(IMAGETEXTURE_ATTR_REPEATT_DFLT);
    if(IMAGETEXTURE_ATTR_URL_DFLT.length() > 0)
      urls = urlsDefault = parseUrlsIntoStringArray(IMAGETEXTURE_ATTR_URL_DFLT);
    else
      urls = urlsDefault = new String[0];
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);

    org.jdom.Attribute attr = root.getAttribute(IMAGETEXTURE_ATTR_REPEATS_NAME);
    if (attr != null)
      repeatS = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(IMAGETEXTURE_ATTR_REPEATT_NAME);
    if (attr != null)  
      repeatT = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(IMAGETEXTURE_ATTR_URL_NAME);
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
    return IMAGETEXTURECustomizer.class;
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    if (IMAGETEXTURE_ATTR_REPEATS_REQD || repeatS != Boolean.parseBoolean(IMAGETEXTURE_ATTR_REPEATS_DFLT)) {
      sb.append(" ");
      sb.append(IMAGETEXTURE_ATTR_REPEATS_NAME);
      sb.append("='");
      sb.append(repeatS);
      sb.append("'");
    }
    if (IMAGETEXTURE_ATTR_REPEATT_REQD || repeatT != Boolean.parseBoolean(IMAGETEXTURE_ATTR_REPEATT_DFLT)) {
      sb.append(" ");
      sb.append(IMAGETEXTURE_ATTR_REPEATT_NAME);
      sb.append("='");
      sb.append(repeatT);
      sb.append("'");
    }
    if (IMAGETEXTURE_ATTR_URL_REQD || (!Arrays.equals(urls, urlsDefault) && (urls.length > 0))) {
      sb.append(" ");
      sb.append(IMAGETEXTURE_ATTR_URL_NAME);
      sb.append("='");
      sb.append(formatStringArray(urls, insertCommas, insertLineBreaks));
      sb.append("'");    
    }

    return sb.toString();
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
}
