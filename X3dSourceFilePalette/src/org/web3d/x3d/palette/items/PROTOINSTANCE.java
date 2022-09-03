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

import java.util.List;
import java.util.Vector;
import javax.swing.text.JTextComponent;
import org.jdom.Attribute;
import org.jdom.Comment;
import org.jdom.Content;
import org.jdom.Element;
import org.web3d.x3d.types.X3DChildNode;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * PROTOINSTANCE.java
 * Created on Mar 9, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class PROTOINSTANCE extends X3DChildNode
{
  private List<FIELDVALUE> fieldValueChildren = new Vector<>();
  private List<Comment> commentChildren = new Vector<>();
  
  private boolean insertFieldValuesComment;
  private String  fieldValueContainedComment;
  
  private String  defaultFieldValuesComment = "<!-- Insert fieldValue initializations here, if any -->";
  private String protoDeclareName = PROTOINSTANCE_ATTR_NAME_DFLT;
  
  public PROTOINSTANCE()
  {
  }

  @Override
  public void initialize()
  {
    fieldValueContainedComment = defaultFieldValuesComment;
    insertFieldValuesComment = true;
  }

  @SuppressWarnings("unchecked")
  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    initialize();
    
    super.initializeFromJdom(root, comp);
    Attribute attr = root.getAttribute(PROTOINSTANCE_ATTR_NAME_NAME);
    if(attr != null)
         protoDeclareName = attr.getValue();
// not needed
//	else if ((root.getAttributeValue(Node.USE_KEYWORD) != null) &&
//			 !root.getAttributeValue(Node.USE_KEYWORD).isEmpty())
//		 protoDeclareName = ""; // not found if this ProtoInstance is also a USE node
    
    List<Content> contentLis = (List<Content>)root.getContent();   // suppress cast warning
    if(contentLis.size() > 0) {
      for(Content cnt : contentLis) {
       if(cnt instanceof Element && ((Element)cnt).getName().equals("fieldValue")) {
          FIELDVALUE fld = new FIELDVALUE();
          fld.initialize();
          fld.initializeFromJdomElem((Element)cnt);
          fld.setupContent((Element)cnt);
          fieldValueChildren.add(fld);
          insertFieldValuesComment = false;
        }
        else if(cnt instanceof Comment) {
          commentChildren.add((Comment)cnt);
          insertFieldValuesComment = false;
        }
      }
    }
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    if (PROTOINSTANCE_ATTR_NAME_REQD || !protoDeclareName.equals(PROTOINSTANCE_ATTR_NAME_DFLT)) {
      sb.append(" ");
      sb.append(PROTOINSTANCE_ATTR_NAME_NAME);
      sb.append("='");
      sb.append(protoDeclareName);
      sb.append("'");
    }
    return sb.toString();
  }

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
    if(fieldValueChildren != null) {
      for(FIELDVALUE fieldValue : fieldValueChildren) {
        sb.append(linesep);
        //sb.append("  "); // indent
        sb.append(fieldValue.createBody());
      }
    }
    if (insertFieldValuesComment)
      sb.append(linesep).append(fieldValueContainedComment);
    sb.append(linesep);
    
    return sb.toString();
  }

  @Override
  public String getElementName()
  {
    return PROTOINSTANCE_ELNAME;
  }
  
  public List<FIELDVALUE> getFieldValues()
  {
    return fieldValueChildren;
  }
  
  public void setFieldValues(List<FIELDVALUE> flds)
  {
    fieldValueChildren = flds;
    insertFieldValuesComment = false;
  }
  
  public String getProtoDeclareName()
  {
    return protoDeclareName;
  }
  
  public void setProtoDeclareName(String s)
  {
    protoDeclareName = s;
  }
}
