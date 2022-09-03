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

import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.palette.X3DPaletteUtilities.*;

/**
 * ANCHOR.java
 * Created on March 14, 2007, 9:57 AM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class ANCHOR extends X3DGroupingNode
{
  private String description;
  private String[] urls, urlsDefault;
  private String[] parameters;

  public ANCHOR()
  {
  }

  @Override
  public String getElementName()
  {
    return ANCHOR_ELNAME;
  }

  @Override
  public void initialize()
  {
    description = ANCHOR_ATTR_DESCRIPTION_DFLT;
    if(ANCHOR_ATTR_URL_DFLT.length()>0)
      urls = urlsDefault = parseUrlsIntoStringArray(ANCHOR_ATTR_URL_DFLT);
    else
      urls = urlsDefault = new String[0];
    parameters   = parseMFStringIntoStringArray(ANCHOR_ATTR_PARAMETER_DFLT[0], true);

    String[] fa = parse3(ANCHOR_ATTR_BBOXCENTER_DFLT);
    bboxCenterX = bboxCenterXDefault = new SFFloat(fa[0], null, null);
    bboxCenterY = bboxCenterYDefault = new SFFloat(fa[1],null,null);
    bboxCenterZ = bboxCenterZDefault = new SFFloat(fa[2],null,null);

    fa = parse3(ANCHOR_ATTR_BBOXSIZE_DFLT);
    bboxSizeX = bboxSizeXDefault = new SFFloat(fa[0],0.0f,null,true);
    bboxSizeY = bboxSizeYDefault = new SFFloat(fa[1],0.0f,null,true);
    bboxSizeZ = bboxSizeZDefault = new SFFloat(fa[2],0.0f,null,true);

    setContent("\n\t\t<!--TODO add children nodes here-->\n\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    
    org.jdom.Attribute attr = root.getAttribute(ANCHOR_ATTR_DESCRIPTION_NAME);

    if (attr != null)
      description = attr.getValue();
    attr = root.getAttribute(ANCHOR_ATTR_URL_NAME);
    if (attr != null)
      urls = parseUrlsIntoStringArray(attr.getValue());
    attr = root.getAttribute(ANCHOR_ATTR_PARAMETER_NAME);
    if (attr != null)
      parameters = parseMFStringIntoStringArray(attr.getValue(), true);
    attr = root.getAttribute(ANCHOR_ATTR_BBOXCENTER_NAME);
    if (attr != null) {
      String[] fa = parse3(attr.getValue());
      bboxCenterX = new SFFloat(fa[0], null, null);
      bboxCenterY = new SFFloat(fa[1], null, null);
      bboxCenterZ = new SFFloat(fa[2], null, null);
    }
    attr = root.getAttribute(ANCHOR_ATTR_BBOXSIZE_NAME);
    if (attr != null) {
      String[] fa = parse3(attr.getValue());
      bboxSizeX = new SFFloat(fa[0], 0.0f, null, true);
      bboxSizeY = new SFFloat(fa[1], 0.0f, null, true);
      bboxSizeZ = new SFFloat(fa[2], 0.0f, null, true);
    }
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return ANCHORCustomizer.class;
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (ANCHOR_ATTR_BBOXCENTER_REQD ||
            (!bboxCenterX.equals(bboxCenterXDefault) ||
             !bboxCenterY.equals(bboxCenterYDefault) ||
             !bboxCenterZ.equals(bboxCenterZDefault)))
    {
      sb.append(" ");
      sb.append(ANCHOR_ATTR_BBOXCENTER_NAME);
      sb.append("='");
      sb.append(bboxCenterX);
      sb.append(" ");
      sb.append(bboxCenterY);
      sb.append(" ");
      sb.append(bboxCenterZ);
      sb.append("'");
    }
    if (ANCHOR_ATTR_BBOXSIZE_REQD ||
           (!bboxSizeX.equals(bboxSizeXDefault) ||
            !bboxSizeY.equals(bboxSizeYDefault) ||
            !bboxSizeZ.equals(bboxSizeZDefault)))
    {
      sb.append(" ");
      sb.append(ANCHOR_ATTR_BBOXSIZE_NAME);
      sb.append("='");
      sb.append(bboxSizeX);
      sb.append(" ");
      sb.append(bboxSizeY);
      sb.append(" ");
      sb.append(bboxSizeZ);
      sb.append("'");
    }
    if (ANCHOR_ATTR_DESCRIPTION_REQD || !description.equals(ANCHOR_ATTR_DESCRIPTION_DFLT)) {
      sb.append(" ");
      sb.append(ANCHOR_ATTR_DESCRIPTION_NAME);
      sb.append("='");
      sb.append(escapeXmlCharacters(description));
      sb.append("'");
    }
    if (ANCHOR_ATTR_PARAMETER_REQD || (!Arrays.equals(parameters, ANCHOR_ATTR_PARAMETER_DFLT) && (parameters.length > 0))) {
      sb.append(" ");
      sb.append(ANCHOR_ATTR_PARAMETER_NAME);
      sb.append("='");
      sb.append(formatStringArray(parameters)); // includes XML character escaping
      sb.append("'");
    }
    if (ANCHOR_ATTR_URL_REQD || (!Arrays.equals(urls, urlsDefault) && (urls.length > 0))) {
      sb.append(" ");
      sb.append(ANCHOR_ATTR_URL_NAME);
      sb.append("='");
      sb.append(formatStringArray(urls));
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

  public String[] getParameters()
  {
    return parameters;
  }

  public String getParameterString()
  {
    return formatStringArray(parameters); // includes XML character escaping
  }

  public void setParameters(String[] parameters)
 {
    this.parameters = parameters;
  }
  public void setParameters(String parameterString)
 {
    this.parameters = splitStringIntoStringArray(parameterString);
  }
}
