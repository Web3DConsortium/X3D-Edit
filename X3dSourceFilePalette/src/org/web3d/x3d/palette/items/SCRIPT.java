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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.text.JTextComponent;
import org.jdom.CDATA;
import org.jdom.Comment;
import org.jdom.Content;
import org.jdom.DataConversionException;
import org.jdom.Element;
import org.web3d.x3d.types.X3DChildNode;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * SCRIPT.java
 * Created on Sep 26, 2007 2:30 PM
 * 
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 * 
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class SCRIPT extends X3DChildNode
{  
  private boolean mustEvaluate, mustEvaluateDefault;
  private boolean directOutput, directOutputDefault;
  private String[] urls, urlsDefault;

  private String ecmaContent;
  private boolean showECMA;
  private boolean insertFieldsComment;
  
  public static String ecmaScriptHeader1 = "<![CDATA[";
  public static String ecmaScriptHeader2 = "ecmascript:";
  public static String ecmaScriptTrailer = "]]>";
  
  public static final String defaultEcmaContent = "// TODO insert source here (see newECMAscript.js for example Script code)"+linesep;
  public static final String insertFieldsHere = "<!-- TODO insert field definitions here, if any -->";
                                           
  // TODO mix field definitions and comments
  private ArrayList<FIELD>   fieldChildren   = new ArrayList<>();
  private ArrayList<Comment> commentChildren = new ArrayList<>();
  private ArrayList<IS>      isChildren      = new ArrayList<>();

  private boolean insertCommas, insertLineBreaks = false;
  
  public SCRIPT()
  {
  }

  @Override
  public String getElementName()
  {
    return SCRIPT_ELNAME;
  }
  
  @Override
  public void initialize()
  {
    mustEvaluate = mustEvaluateDefault = Boolean.parseBoolean(SCRIPT_ATTR_MUSTEVALUATE_DFLT);
    directOutput = directOutputDefault = Boolean.parseBoolean(SCRIPT_ATTR_DIRECTOUTPUT_DFLT);
    if(SCRIPT_ATTR_URL_DFLT.length()>0)
      urls = urlsDefault = parseUrlsIntoStringArray(SCRIPT_ATTR_URL_DFLT);
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

    attr = root.getAttribute(SCRIPT_ATTR_MUSTEVALUATE_NAME);
    if(attr != null)
      try {
      mustEvaluate = attr.getBooleanValue();
      }
      catch(DataConversionException ex) {
        System.err.println("Bad conversion reading SCRIPT/"+SCRIPT_ATTR_MUSTEVALUATE_NAME);
      }
    
    attr = root.getAttribute(SCRIPT_ATTR_DIRECTOUTPUT_NAME);
    if(attr != null)
      try {
        directOutput = attr.getBooleanValue();
      }
      catch(DataConversionException ex) {
        System.err.println("Bad conversion reading SCRIPT/"+SCRIPT_ATTR_DIRECTOUTPUT_NAME);
      }
    attr = root.getAttribute(SCRIPT_ATTR_URL_NAME);
    if(attr != null)
    {
        urls = parseUrlsIntoStringArray(attr.getValue());
         if (attr.getValue().contains(","))  insertCommas     = true;
         if (attr.getValue().contains("\n") ||
             attr.getValue().contains("\r")) insertLineBreaks = true; // TODO not working, line breaks not being passed from JDOM
         if (insertCommas)                   insertLineBreaks = true; // workaround default, if commas were present then most likely lineBreaks also
    }
    
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
          fld.setParent(SCRIPT_ELNAME);
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
          System.err.println("Unsupported content in Script element");
      }
    }
    // Take out the header of the CDATA; we'll put it back in below
    if(ecmaContent != null && ecmaContent.length()>0)
    {
      Pattern p = Pattern.compile("\\A\\s*"+ecmaScriptHeader2+"(\\s)?",Pattern.MULTILINE);
      Matcher m = p.matcher(ecmaContent);
      ecmaContent = m.replaceFirst("");  // take header out
    }
    showECMA = ecmaContent != null && ecmaContent.length()>0;   
  }

  /* The following need to be overridden because we're managing our content (nested
   * field elements and ecmascript source) ourselves instead of relying on the base class.
   */
  
  @Override
  protected String getContent()
  {
    StringBuilder sb = new StringBuilder();
    // TODO figure out how to maintain original order of comments among fields, if possible
    if(this.commentChildren != null && commentChildren.size()>0) {
      for(Comment comment : commentChildren) {
        sb.append(linesep);
        sb.append("<!--");
        sb.append(comment.getText());
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
      if (!ecmaContent.trim().startsWith(ecmaScriptHeader2))
      {
          sb.append(ecmaScriptHeader2);
          sb.append(linesep);
      }
      sb.append(ecmaContent);
      if (!ecmaContent.endsWith(linesep) && !ecmaContent.endsWith("\n") && !ecmaContent.endsWith("\r"))
      {
          sb.append(linesep);
      }
      sb.append(ecmaScriptTrailer);
      sb.append(linesep);
    }
    return sb.toString();
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    if (SCRIPT_ATTR_DIRECTOUTPUT_REQD || directOutput != directOutputDefault)
    {
      sb.append(" ");
      sb.append(SCRIPT_ATTR_DIRECTOUTPUT_NAME);
      sb.append("='");
      sb.append(directOutput);
      sb.append("'");
    }
    if (SCRIPT_ATTR_MUSTEVALUATE_REQD || mustEvaluate != mustEvaluateDefault)
    {
      sb.append(" ");
      sb.append(SCRIPT_ATTR_MUSTEVALUATE_NAME);
      sb.append("='");
      sb.append(mustEvaluate);
      sb.append("'");
    }
    if (SCRIPT_ATTR_URL_REQD || (!Arrays.equals(urls, urlsDefault) && (urls.length > 0)))
    {
      sb.append(" ");
      sb.append(SCRIPT_ATTR_URL_NAME);
      sb.append("='");
      sb.append(formatStringArray(urls, insertCommas, insertLineBreaks));
      sb.append("'");
    }
    return sb.toString();
  }

  public boolean isDirectOutput()
  {
    return directOutput;
  }

  public void setDirectOutput(boolean dout)
  {
    directOutput = dout;
  }

  public boolean isMustEvaluate()
  {
    return mustEvaluate;
  }

  public void setMustEvaluate(boolean mEval)
  {
    mustEvaluate = mEval;
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
  public void setShowECMA(boolean value)
  {
    showECMA = value;
  }
  
  public String getEcmaContent()
  {
    return ecmaContent.stripTrailing();
  }
  
  public void setEcmaContent(String s)
  {
    ecmaContent = s.stripTrailing();
  }
  
  public ArrayList<FIELD> getFields()
  {
    return fieldChildren;
  }
  
  public void setFields(ArrayList<FIELD> flds)
  {
    fieldChildren = flds;
    insertFieldsComment = false;
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