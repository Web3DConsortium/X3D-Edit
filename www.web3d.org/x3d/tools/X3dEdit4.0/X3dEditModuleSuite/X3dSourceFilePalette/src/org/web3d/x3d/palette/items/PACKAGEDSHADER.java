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
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.JTextComponent;
import org.jdom.CDATA;
import org.jdom.Comment;
import org.jdom.Content;
import org.jdom.Element;
import org.web3d.x3d.types.X3DChildNode;

import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * PACKAGEDSHADER.java
 * Created on 16 January 2010
 * 
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 * 
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class PACKAGEDSHADER extends X3DChildNode
{  
  private String language;
  private String[] urls, urlsDefault;

  private String ecmaContent;
  private boolean showECMA;
  private boolean insertFieldsComment;
  
  public static String ecmaScriptHeader1 = "<![CDATA[";
  public static String ecmaScriptHeader2 = "ecmascript:";
  public static String ecmaScriptTrailer = "]]>";
  
  public static final String defaultEcmaContent = "// TODO insert source here"+linesep; // TODO  (see newECMAscript.js for example PackagedShader code)
  public static final String insertFieldsHere = "<!-- TODO insert field definitions here, if any -->";
                                           
  // TODO mix field definitions and comments
  private List<FIELD> fieldChildren = new Vector<>();
  private List<Comment> commentChildren = new Vector<>();
  private List<IS> isChildren = new Vector<>();
  
  public PACKAGEDSHADER()
  {
  }

  @Override
  public String getElementName()
  {
    return PACKAGEDSHADER_ELNAME;
  }
  
  @Override
  public void initialize()
  {
    language = PACKAGEDSHADER_ATTR_LANGUAGE_DFLT;

    if(PACKAGEDSHADER_ATTR_URL_DFLT.length()>0)
      urls = urlsDefault = parseUrlsIntoStringArray(PACKAGEDSHADER_ATTR_URL_DFLT);
    else
      urls = urlsDefault = new String[0];
    showECMA = true;
    ecmaContent = defaultEcmaContent;   
    insertFieldsComment = true;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(PACKAGEDSHADER_ATTR_LANGUAGE_NAME);
    if (attr != null)
      language = attr.getValue();

    attr = root.getAttribute(PACKAGEDSHADER_ATTR_URL_NAME);
    if(attr != null)
      urls = parseUrlsIntoStringArray(attr.getValue()); 
    
    List<Content> contentList = (List<Content>)root.getContent();   // suppress cast warning
    if(contentList.size() > 0) {
      ecmaContent = ""; // reset default hint
      for(Content cnt : contentList) {
        if(cnt instanceof CDATA) {
           ecmaContent += ((CDATA)cnt).getText();
        }
        else if(cnt instanceof Element && ((Element)cnt).getName().equals("field")) {
           //fields.add((Element)cnt);
          FIELD fld = new FIELD();
          fld.initialize();
          fld.initializeFromJdom((Element)cnt,comp);
          fld.setParent(PACKAGEDSHADER_ELNAME);
          fieldChildren.add(fld);
          insertFieldsComment = false;
        }
        else if(cnt instanceof Element && ((Element)cnt).getName().equals("IS")) {
          IS iselem = new IS();
          iselem.initialize();
          iselem.initializeFromJdom((Element)cnt, comp);
       
          isChildren.add(iselem);
          insertFieldsComment = false;
        }
        else if(cnt instanceof Comment) {
          commentChildren.add((Comment)cnt);
          insertFieldsComment = false;
        }
        else
          System.err.println("Unsupported content in PACKAGEDSHADER element");
      }
    }
    // Take out the header of the CDATA; we'll put it back in below
    if(ecmaContent != null && ecmaContent.length()>0) {
      Pattern p = Pattern.compile("\\A\\s*"+ecmaScriptHeader2+"\\s*",Pattern.MULTILINE);
      Matcher m = p.matcher(ecmaContent);
      ecmaContent = m.replaceAll("");  // take it out
     }
    showECMA = ecmaContent != null && ecmaContent.length()>0;   
  }

  /* The following need to be overridden because we're managing our content (nested
   * field elements and source) ourselves instead of relying on the base class.
   */
  
  @Override
  protected String getContent()
  {
    StringBuilder sb = new StringBuilder();

    if(this.commentChildren != null && commentChildren.size()>0) {
      for(Comment com : commentChildren) {
        sb.append(linesep);
        sb.append("<!--");
        sb.append(com.getText());
        sb.append("-->");
      }
    }
    if(fieldChildren != null) {
      for(FIELD fld : fieldChildren) {
        sb.append(linesep);
        sb.append("  "); // indent
        sb.append(fld.createBody());
      }
    }
    if(isChildren != null) {
      for(IS iselem : isChildren) {
        sb.append(linesep);
        sb.append("  "); // indent
        sb.append(iselem.createBody());
      }
    }
    if (insertFieldsComment)
      sb.append(linesep).append(insertFieldsHere);
    
    sb.append(linesep);
    if(this.ecmaContent != null && ecmaContent.length()>0) {
      sb.append(ecmaScriptHeader1);
      sb.append(linesep);
      sb.append(ecmaScriptHeader2);
      sb.append(linesep);
      sb.append(ecmaContent);
      sb.append(ecmaScriptTrailer);
      sb.append(linesep);
    }
    return sb.toString();
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    if (PACKAGEDSHADER_ATTR_LANGUAGE_REQD || !language.equals (PACKAGEDSHADER_ATTR_LANGUAGE_DFLT))
    {
      sb.append(" ");
      sb.append(PACKAGEDSHADER_ATTR_LANGUAGE_NAME);
      sb.append("='");
      sb.append(language);
      sb.append("'");
    }
    if (PACKAGEDSHADER_ATTR_URL_REQD || (!Arrays.equals(urls, urlsDefault) && (urls.length > 0)))
    {
      sb.append(" ");
      sb.append(PACKAGEDSHADER_ATTR_URL_NAME);
      sb.append("='");
      sb.append(formatStringArray(urls));
      sb.append("'");
    }
    return sb.toString();
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
   * This one is used by the dialog to determine if a sample ecmascript comment
   * should be shown.  We say yes if this script object was newly created instead
   * of an existing element being edited.
   * @return
   */
  public boolean isShowECMA()
  {
    return showECMA;
  }
  
  public String getEcmaContent()
  {
    return ecmaContent;
  }
  
  public void setEcmaContent(String s)
  {
    ecmaContent = s;
  }
  
  public List<FIELD> getFields()
  {
    return fieldChildren;
  }
  
  public void setFields(List<FIELD> flds)
  {
    fieldChildren = flds;
    insertFieldsComment = false;
  }
    /**
     * @return the language
     */
    public String getLanguage ()
    {
        return language;
    }
    /**
     * @param language the language to set
     */
    public void setLanguage (String language)
    {
        this.language = language;
    }
}