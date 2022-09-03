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

import org.web3d.x3d.types.X3DChildNode;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * DISENTITYTYPEMAPPING.java
 * Created on 15 May 2008
 * 
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 * 
 * @author Mike Bailey
 * @version $Id$
 */
public class DISENTITYTYPEMAPPING extends X3DChildNode
{
  private String[] urls, urlsDefault;
  private SFInt32 category,categoryDefault;
  private SFInt32 country,countryDefault;
  private SFInt32 domain,domainDefault;
  private SFInt32 extra,extraDefault;
  private SFInt32 kind,kindDefault;
  private SFInt32 specific,specificDefault;
  private SFInt32 subcategory,subcategoryDefault;

  public DISENTITYTYPEMAPPING()
  {
  }

  @Override
  public String getElementName()
  {
    return DISENTITYTYPEMAPPING_ELNAME;
  }
  
  @Override
  public void initialize()
  {
    if(DISENTITYTYPEMAPPING_ATTR_URL_DFLT.length()>0)
      urls = urlsDefault = parseUrlsIntoStringArray(DISENTITYTYPEMAPPING_ATTR_URL_DFLT);
    else
      urls = urlsDefault = new String[0];
    category = categoryDefault       = new SFInt32(DISENTITYTYPEMAPPING_ATTR_CATEGORY_DFLT,   0,255);
    country = countryDefault         = new SFInt32(DISENTITYTYPEMAPPING_ATTR_COUNTRY_DFLT,    0,65535);
    domain = domainDefault           = new SFInt32(DISENTITYTYPEMAPPING_ATTR_DOMAIN_DFLT,     0,255);
    extra = extraDefault             = new SFInt32(DISENTITYTYPEMAPPING_ATTR_EXTRA_DFLT,      0,255);
    kind = kindDefault               = new SFInt32(DISENTITYTYPEMAPPING_ATTR_KIND_DFLT,       0,255);
    specific = specificDefault       = new SFInt32(DISENTITYTYPEMAPPING_ATTR_SPECIFIC_DFLT,   0,255);
    subcategory = subcategoryDefault = new SFInt32(DISENTITYTYPEMAPPING_ATTR_SUBCATEGORY_DFLT,0,255);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;
    
    attr = root.getAttribute(DISENTITYTYPEMAPPING_ATTR_URL_NAME);
    if(attr != null) {
      urls = parseUrlsIntoStringArray(attr.getValue());
    }

    attr = root.getAttribute(DISENTITYTYPEMAPPING_ATTR_CATEGORY_NAME);
    if(attr != null) {
      category = new SFInt32(attr.getValue());
    }
    attr = root.getAttribute(DISENTITYTYPEMAPPING_ATTR_COUNTRY_NAME);
    if(attr != null) {
      country = new SFInt32(attr.getValue());
    }
    attr = root.getAttribute(DISENTITYTYPEMAPPING_ATTR_DOMAIN_NAME);
    if(attr != null) {
      domain = new SFInt32(attr.getValue());
    }
    attr = root.getAttribute(DISENTITYTYPEMAPPING_ATTR_EXTRA_NAME);
    if(attr != null) {
      extra = new SFInt32(attr.getValue());
    }
    attr = root.getAttribute(DISENTITYTYPEMAPPING_ATTR_KIND_NAME);
    if(attr != null) {
      kind = new SFInt32(attr.getValue());
    }
    attr = root.getAttribute(DISENTITYTYPEMAPPING_ATTR_SPECIFIC_NAME);
    if(attr != null) {
      specific = new SFInt32(attr.getValue());
    }
    attr = root.getAttribute(DISENTITYTYPEMAPPING_ATTR_SUBCATEGORY_NAME);
    if(attr != null) {
      subcategory = new SFInt32(attr.getValue());
    }
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if(DISENTITYTYPEMAPPING_ATTR_CATEGORY_REQD || !category.equals(categoryDefault)){
      sb.append(" ");
      sb.append(DISENTITYTYPEMAPPING_ATTR_CATEGORY_NAME);
      sb.append("='");
      sb.append(category);
      sb.append("'");
    }
    if(DISENTITYTYPEMAPPING_ATTR_COUNTRY_REQD || !country.equals(countryDefault)){
      sb.append(" ");
      sb.append(DISENTITYTYPEMAPPING_ATTR_COUNTRY_NAME);
      sb.append("='");
      sb.append(country);
      sb.append("'");
    }
    if(DISENTITYTYPEMAPPING_ATTR_DOMAIN_REQD || !domain.equals(domainDefault)){
      sb.append(" ");
      sb.append(DISENTITYTYPEMAPPING_ATTR_DOMAIN_NAME);
      sb.append("='");
      sb.append(domain);
      sb.append("'");
    }
    if(DISENTITYTYPEMAPPING_ATTR_EXTRA_REQD || !extra.equals(extraDefault)){
      sb.append(" ");
      sb.append(DISENTITYTYPEMAPPING_ATTR_EXTRA_NAME);
      sb.append("='");
      sb.append(extra);
      sb.append("'");
    }
    if(DISENTITYTYPEMAPPING_ATTR_KIND_REQD || !kind.equals(kindDefault)){
      sb.append(" ");
      sb.append(DISENTITYTYPEMAPPING_ATTR_KIND_NAME);
      sb.append("='");
      sb.append(kind);
      sb.append("'");
    }
    if(DISENTITYTYPEMAPPING_ATTR_SPECIFIC_REQD || !specific.equals(specificDefault)){
      sb.append(" ");
      sb.append(DISENTITYTYPEMAPPING_ATTR_SPECIFIC_NAME);
      sb.append("='");
      sb.append(specific);
      sb.append("'");
    }
    if(DISENTITYTYPEMAPPING_ATTR_SUBCATEGORY_REQD || !subcategory.equals(subcategoryDefault)){
      sb.append(" ");
      sb.append(DISENTITYTYPEMAPPING_ATTR_SUBCATEGORY_NAME);
      sb.append("='");
      sb.append(subcategory);
      sb.append("'");
    }
    if(DISENTITYTYPEMAPPING_ATTR_URL_REQD || (!Arrays.equals(urls, urlsDefault) && (urls.length > 0))) {
      sb.append(" ");
      sb.append(DISENTITYTYPEMAPPING_ATTR_URL_NAME);
      sb.append("='");
      sb.append(formatStringArray(urls));
      sb.append("'");
    }

    return sb.toString();
  }

  public String getCategory()
  {
    return category.toString();
  }

  public void setCategory(String s)
  {
    this.category =  new SFInt32(s,0,255);
  }

  public String getCountry()
  {
    return country.toString();
  }

  public void setCountry(String s)
  {
    this.country = new SFInt32(s,0,65535);
  }

  public String getDomain()
  {
    return domain.toString();
  }

  public void setDomain(String s)
  {
    this.domain = new SFInt32(s,0,255);
  }

  public String getExtra()
  {
    return extra.toString();
  }

  public void setExtra(String s)
  {
    this.extra = new SFInt32(s,0,255);
  }

  public String getKind()
  {
    return kind.toString();
  }

  public void setKind(String s)
  {
    this.kind = new SFInt32(s,0,255);
  }

  public String getSpecific()
  {
    return specific.toString();
  }

  public void setSpecific(String s)
  {
    this.specific =  new SFInt32(s,0,255);
  }

  public String getSubcategory()
  {
    return subcategory.toString();
  }

  public void setSubcategory(String s)
  {
    this.subcategory =  new SFInt32(s,0,255);
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

  @Override
  public String getDefaultContainerField()
  {
    return "mapping";
  }
}