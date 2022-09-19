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
import static org.web3d.x3d.palette.X3DPaletteUtilities.escapeXmlCharacters;
import org.web3d.x3d.types.SceneGraphStructureNodeType;
import static org.web3d.x3d.types.X3DSchemaData.*;

//import org.netbeans.spi.palette.PaletteItemRegistration;

//@PaletteItemRegistration
//(
//    paletteid = "X3DPalette",
//    category = "1. X3D Model Structure and Metadata",
//    itemid = "6_META",
//    icon32 = "org/web3d/x3d/palette/items/resources/META32.png",
//    icon16 = "org/web3d/x3d/palette/items/resources/META16.png",
//    body = "<meta name='reference' content='https://www.web3d.org/specifications/X3Dv4Draft/ISO-IEC19775-1v4-DIS/Part01/Architecture.html'/>",
//    name = "meta",
//    tooltip = "Valid comments are found between XML elements"
//)
// https://bits.netbeans.org/14/javadoc/org-netbeans-spi-palette/architecture-summary.html
// https://bits.netbeans.org/14/javadoc/org-netbeans-spi-palette/org/netbeans/spi/palette/PaletteItemRegistration.html

// TODO list example names in tooltipes, fix "type type"
/**
 * META.java
 * Created on March 14, 2007, 9:57 AM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey and Don Brutzman
 * @version $Id$
 */
public class META extends SceneGraphStructureNodeType
{
  /** name of value */
  private String name;
  /** content of value */
  private String contentAttribute;
  /** language code (X3D lang attribute superceding xml:lang, similar to HTML) 
   * http://www.rfc-editor.org/rfc/bcp/bcp47.txt and described in 
   * http://www.w3.org/International/articles/language-tags */
  private String lang;
  /** direction (ltr or rtl) */
  private String dir;
  /** http-equiv example <meta http-equiv="Expires" content="Tue, 20 Aug 1996 14:25:27 GMT"> 
   * see http://www.w3.org/TR/html4/struct/global.html#adef-http-equiv */
  private String httpEquivalent;
  /** scheme allows authors to provide user agents more context for the correct interpretation of meta data, 
   * example <META scheme="ISBN"  name="identifier" content="0-8230-2355-9">, 
   * see http://www.w3.org/TR/html4/struct/global.html#idx-scheme */
  private String scheme;

  public META()
  {
  }

  @Override
  public String getElementName()
  {
    return META_ELNAME;
  }

  @Override
  public void initialize()
  {
    name = META_ATTR_NAME_DFLT;
    contentAttribute = META_ATTR_CONTENT_DFLT;
    dir              = META_ATTR_DIR_DFLT;
    httpEquivalent   = META_ATTR_HTTPEQUIV_DFLT;
    lang             = META_ATTR_LANG_DFLT;
    scheme           = META_ATTR_SCHEME_DFLT;
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);

    org.jdom.Attribute attr = root.getAttribute(META_ATTR_NAME_NAME);
    if (attr != null)
      name = attr.getValue();
    attr = root.getAttribute(META_ATTR_CONTENT_NAME);
    if (attr != null)
      contentAttribute = attr.getValue();
    attr = root.getAttribute(META_ATTR_DIR_NAME);
    if (attr != null)
       dir = attr.getValue();
    attr = root.getAttribute(META_ATTR_HTTPEQUIV_NAME);
    if (attr != null)
       httpEquivalent = attr.getValue();
    attr = root.getAttribute(META_ATTR_LANG_NAME);
    if (attr != null)
       lang = attr.getValue();
    else
    {
        attr = root.getAttribute("xml:" + META_ATTR_LANG_NAME);
        if (attr != null)
           lang = attr.getValue();
    }
    attr = root.getAttribute(META_ATTR_SCHEME_NAME);
    if (attr != null)
       scheme = attr.getValue();
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return METACustomizer.class;
  }
  
  @Override
  public String buildDEF()
  {
    return "";
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (META_ATTR_CONTENT_REQD || !contentAttribute.equals(META_ATTR_CONTENT_DFLT)) {
      sb.append(" ");
      sb.append(META_ATTR_CONTENT_NAME);
      sb.append("='");
      sb.append(escapeXmlCharacters(contentAttribute));
      sb.append("'");
    }
    if (META_ATTR_DIR_REQD || !dir.equals(META_ATTR_DIR_DFLT)) {
      sb.append(" ");
      sb.append(META_ATTR_DIR_NAME);
      sb.append("='");
      sb.append(dir);
      sb.append("'");
    }
    if (META_ATTR_HTTPEQUIV_REQD || !httpEquivalent.equals(META_ATTR_HTTPEQUIV_DFLT)) {
      sb.append(" ");
      sb.append(META_ATTR_HTTPEQUIV_NAME);
      sb.append("='");
      sb.append(getHttpEquivalent());
      sb.append("'");
    }
    if (META_ATTR_LANG_REQD || !lang.equals(META_ATTR_LANG_DFLT)) {
      sb.append(" ");
      sb.append(META_ATTR_LANG_NAME); // do not prepend xml:
      sb.append("='");
      sb.append(lang); // do not prepend with xml:
      sb.append("'");
    }
    if (META_ATTR_NAME_REQD || !name.equals(META_ATTR_NAME_DFLT)) {
      sb.append(" ");
      sb.append(META_ATTR_NAME_NAME);
      sb.append("='");
      sb.append(escapeXmlCharacters(name));
      sb.append("'");
    }
    if (META_ATTR_SCHEME_REQD || !scheme.equals(META_ATTR_SCHEME_DFLT)) {
      sb.append(" ");
      sb.append(META_ATTR_SCHEME_NAME);
      sb.append("='");
      sb.append(scheme);
      sb.append("'");
    }
    
    return sb.toString();
  }
  
  public String getName()
  {
    return name;
  }

  public void setName(String newName)
  {
    this.name = newName;
  }

  public String getContentAttribute()
  {
    return contentAttribute;
  }
  
  public void setContentAttribute(String newContentAttribute)
  {
    contentAttribute = newContentAttribute;
  }

  @Override
  public String getContent()
  {
    return ""; // no contained content for this element
  }
  
  @Override
  public void setContent(String newContainedContent)
  {
    // no contained content for this element
  }

    /**
     * @return the lang value
     */
    public String getLang() {
        return lang;
    }

    /**
     * @param lang the lang value to set
     */
    public void setLang(String lang) {
        this.lang = lang;
    }

    /**
     * @return the dir value
     */
    public String getDir() {
        return dir;
    }

    /**
     * @param dir the dir value to set
     */
    public void setDir(String dir) {
        this.dir = dir;
    }

    /**
     * @return the httpEquivalent value
     */
    public String getHttpEquivalent() {
        return httpEquivalent;
    }

    /**
     * @param httpEquivalent the httpEquivalent value to set
     */
    public void setHttpEquivalent(String httpEquivalent) {
        this.httpEquivalent = httpEquivalent;
    }

    /**
     * @return the scheme value
     */
    public String getScheme() {
        return scheme;
    }

    /**
     * @param scheme the scheme value to set
     */
    public void setScheme(String scheme) {
        this.scheme = scheme;
    }
  
}
