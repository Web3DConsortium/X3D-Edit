/*
Copyright (c) 1995-2021 held by the author(s).  All rights reserved.

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

import java.util.List;
import java.util.Vector;
import org.jdom.Content;
import javax.swing.text.JTextComponent;
import org.jdom.Comment;
import org.jdom.Element;
import org.web3d.x3d.types.SceneGraphStructureNodeType;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * HEAD.java
 * Created on August 2, 2007, 2:46 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class PROTOINTERFACE extends SceneGraphStructureNodeType
{
  private List<FIELD>   fieldChildren   = new Vector<FIELD>();
  private List<Comment> commentChildren = new Vector<Comment>();
  
  private boolean insertFieldsComment;
  private String  fieldsComment;
  
  private String defaultFieldsComment = "<!-- TODO insert field definitions here, if any -->";
  
  public PROTOINTERFACE()
  {
  }

  @Override
  public void initialize()
  {
    fieldsComment = defaultFieldsComment;   
    insertFieldsComment = true;
  }
  
  @SuppressWarnings("unchecked")
  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    initialize();
    
    super.initializeFromJdom(root, comp);
    
    List<Content> contentLis = (List<Content>)root.getContent();   // suppress cast warning
    if(contentLis.size() > 0) {
      for(Content cnt : contentLis) {
       if(cnt instanceof Element && ((Element)cnt).getName().equals("field")) {
          FIELD fld = new FIELD();
          fld.initialize();
          fld.initializeFromJdom((Element)cnt,comp);
          fieldChildren.add(fld);
          insertFieldsComment = false;
        }
        else if(cnt instanceof Comment) {
          commentChildren.add((Comment)cnt);  // insertFieldsComment remains unchanged
        }
      }
    }
    else // no contained comment
    {
        insertFieldsComment = true;
        fieldsComment = defaultFieldsComment;
    }
  }

  @Override
  protected String getContent()
  {
    StringBuffer sb = new StringBuffer();
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
      insertFieldsComment = false;
    }
    if (insertFieldsComment)
    {
      sb.append(linesep+fieldsComment);
    }
    sb.append(linesep);
    
    return sb.toString();
  }

  @Override
  public String getElementName()
  {
    return PROTOINTERFACE_ELNAME;
  }
  
  public List<FIELD> getFields()
  {
    return fieldChildren;
  }
  
  public void setFields(List<FIELD> flds)
  {
    fieldChildren = flds;
  }
  
  public void setFieldsComment(String newComment)
  {
    fieldsComment = newComment;
    insertFieldsComment = true;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return PROTOINTERFACECustomizer.class;
  }

}
