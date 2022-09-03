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
import javax.swing.text.JTextComponent;
import org.jdom.Comment;
import org.jdom.Content;
import org.jdom.Element;
import static org.web3d.x3d.palette.X3DPaletteUtilities.escapeXmlCharacters;
import org.web3d.x3d.types.X3DChildNode;

import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * EXTERNPROTODECLARE.java
 * Created on Mar 10, 2008
 * 
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 * 
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class EXTERNPROTODECLARE extends X3DChildNode
{
  private String name;
  private String[] urls, urlsDefault;
  private String appinfo       = EXTERNPROTODECLARE_ATTR_APPINFO_DFLT;
  private String documentation = EXTERNPROTODECLARE_ATTR_DOC_DFLT;
  private boolean insertCommas, insertLineBreaks = false;

  private boolean insertFieldsComment;
  
  public static final String insertFieldsHere = "<!-- field definitions (if any) go here, do not include initial values -->";
                                           
  private List<FIELD>   fieldChildren   = new Vector<>();
  private List<Comment> commentChildren = new Vector<>();

  private String appendedContent;
  
  public EXTERNPROTODECLARE()
  {
  }

  @Override
  public String getElementName()
  {
    return EXTERNPROTODECLARE_ELNAME;
  }
  
  @Override
  public void initialize()
  {
    name = EXTERNPROTODECLARE_ATTR_NAME_DFLT;
    if(EXTERNPROTODECLARE_ATTR_URL_DFLT.length()>0)
      urls = urlsDefault = parseUrlsIntoStringArray(EXTERNPROTODECLARE_ATTR_URL_DFLT); //, true);
    else
      urls = urlsDefault = new String[0];
    insertFieldsComment = true;
    
    appinfo       = EXTERNPROTODECLARE_ATTR_APPINFO_DFLT;
    documentation = EXTERNPROTODECLARE_ATTR_DOC_DFLT;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(EXTERNPROTODECLARE_ATTR_NAME_NAME);
    if(attr != null)
      name = attr.getValue();
    
    attr = root.getAttribute(EXTERNPROTODECLARE_ATTR_URL_NAME);
    if(attr != null)
    {
        urls = parseUrlsIntoStringArray(attr.getValue()); //, true); 

         if (attr.getValue().contains(","))  insertCommas     = true;
         if (attr.getValue().contains("\n") ||
             attr.getValue().contains("\r")) insertLineBreaks = true; // TODO not working, line breaks not being passed from JDOM
         if (insertCommas)                   insertLineBreaks = true; // workaround default, if commas were present then most likely lineBreaks also
    }
    
    attr = root.getAttribute(EXTERNPROTODECLARE_ATTR_APPINFO_NAME);
    if(attr != null)
      appinfo = attr.getValue();
    attr = root.getAttribute(EXTERNPROTODECLARE_ATTR_DOC_NAME);
    if(attr != null)
      documentation = attr.getValue();
    
    List<Content> contentLis = (List<Content>)root.getContent();   // suppress cast warning
    if(contentLis.size() > 0) {
      for(Content content : contentLis) {
        if(content instanceof Element && ((Element)content).getName().equals("field")) {
           //fields.add((Element)cnt);
          FIELD field = new FIELD();
          field.initialize();
          field.initializeFromJdom((Element)content,comp);
          field.setParent(EXTERNPROTODECLARE_ELNAME);
          field.setParentExternProtoDeclare(true);
          fieldChildren.add(field);
          insertFieldsComment = false;
        }
        else if(content instanceof Comment) {
          commentChildren.add((Comment)content);
        }
      }
    }
  }

  /* The following need to be overridden because we're managing our content (nested
   * field elements) ourselves instead of relying on the base class.
   */
  
  @Override
  protected String getContent()
  {
    StringBuilder sb = new StringBuilder();
    if(this.commentChildren != null && commentChildren.size()>0) {
      for(Comment comment : commentChildren) {
        sb.append(linesep);
        sb.append("<!--");
        sb.append(comment.getText());
        sb.append("-->");
      }
    }
    if(fieldChildren != null) {
      for(FIELD field : fieldChildren) {
        sb.append(linesep);
        sb.append("  "); // indent
        sb.append(field.createBody());
      }
    }
    if (insertFieldsComment)
        sb.append(linesep).append(insertFieldsHere);
    sb.append(linesep);
    
    return sb.toString();
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    if (EXTERNPROTODECLARE_ATTR_NAME_REQD || !name.equals(EXTERNPROTODECLARE_ATTR_NAME_DFLT)) {
      sb.append(" ");
      sb.append(EXTERNPROTODECLARE_ATTR_NAME_NAME);
      sb.append("='");
      sb.append(escapeXmlCharacters(name));
      sb.append("'");
    }    
    if (EXTERNPROTODECLARE_ATTR_URL_REQD || (!Arrays.equals(urls, urlsDefault) && (urls.length > 0))) {
      sb.append(" ");
      sb.append(EXTERNPROTODECLARE_ATTR_URL_NAME);
      sb.append("='");
      sb.append(formatStringArray(urls, insertCommas, insertLineBreaks));
      sb.append("'");
    }
    if (EXTERNPROTODECLARE_ATTR_APPINFO_REQD || !appinfo.equals(EXTERNPROTODECLARE_ATTR_APPINFO_DFLT)) {
      sb.append(" ");
      sb.append(EXTERNPROTODECLARE_ATTR_APPINFO_NAME);
      sb.append("='");
      sb.append(appinfo);
      sb.append("'");
    }
    if (EXTERNPROTODECLARE_ATTR_DOC_REQD || !documentation.equals(EXTERNPROTODECLARE_ATTR_DOC_DFLT)) {
      sb.append(" ");
      sb.append(EXTERNPROTODECLARE_ATTR_DOC_NAME);
      sb.append("='");
      sb.append(documentation);
      sb.append("'");
    }

    return sb.toString();
  }

  public String[] getUrls()
  {
    String[] ret = new String[this.urls.length];
    System.arraycopy(this.urls,0,ret,0,this.urls.length);
    return ret;
  }

  public void setUrls(String[] urlarray)
  {
    urls = new String[urlarray.length];
    System.arraycopy(urlarray, 0, this.urls, 0, urlarray.length);
  }
  
  public void setName(String n)
  {
    name = n;
  }
  
  public String getName()
  {
    return name;
  }
  public List<FIELD> getFields()
  {
    return fieldChildren;
  }
  
  public void setFields(List<FIELD> fieldsList)
  {
    for (FIELD field : fieldsList)
    {
        field.setParentExternProtoDeclare (true);
    }
    fieldChildren = fieldsList;
    insertFieldsComment = false;
  }
  
  public String getAppinfo()
  {
    return appinfo;
  }
  
  public void setAppinfo(String appinfo)
  {
    this.appinfo = appinfo;
  }
  
  public String getDocumentation()
  {
    return documentation;
  }
  
  public void setDocumentation(String documentation)
  {
    this.documentation = documentation;
  }

    /**
     * @return the appendedContent for serialization output following the ProtoDeclare
     */
    public String getAppendedContent() {
        if (appendedContent == null) return "";
        else                         return appendedContent;
    }

    /**
     * @param appendedContent the appendedContent to set for serialization output following the ProtoDeclare
     */
    public void setAppendedContent(String appendedContent) {
        this.appendedContent = appendedContent;
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