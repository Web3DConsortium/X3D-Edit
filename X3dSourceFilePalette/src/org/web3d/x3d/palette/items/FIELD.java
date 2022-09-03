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
import javax.swing.text.JTextComponent;
import org.jdom.Attribute;
import org.jdom.Element;
import static org.web3d.x3d.palette.X3DPaletteUtilities.escapeXmlCharacters;
import org.web3d.x3d.types.SceneGraphStructureNodeType;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * FIELD.java
 * Created on Mar 4, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class FIELD extends SceneGraphStructureNodeType
{
  private String accessType    = FIELD_ATTR_ACCESSTYPE_DFLT;
  private String name          = FIELD_ATTR_NAME_DFLT;
  private String type          = FIELD_ATTR_TYPE_DFLT;
  private String value         = FIELD_ATTR_VALUE_DFLT;
  private String appinfo       = FIELD_ATTR_APPINFO_DFLT;
  private String documentation = FIELD_ATTR_DOC_DFLT;
  
  private boolean parentExternProtoDeclare = false;
  
  public FIELD()
  {
  }

  public FIELD(String newName, String newType, String newAccessType, String newValue)
  {
    name          = newName;
    type          = newType;
    accessType    = newAccessType;
    value         = newValue;
  }

  public FIELD(String newName, String newType, String newAccessType, String newValue, String newAppinfo, String newDocumentation)
  {
    name          = newName;
    type          = newType;
    accessType    = newAccessType;
    value         = newValue;
    appinfo       = newAppinfo;
    documentation = newDocumentation;
  }
  
  public static boolean canHaveValue(String type, String accessType)
  {
    if      (type.equals(FIELD_ATTR_TYPE_SFNODE) ||
             type.equals(FIELD_ATTR_TYPE_MFNODE))
         return false;
    else if (accessType.equals(FIELD_ATTR_ACCESSTYPE_INITIALIZEONLY) || 
             accessType.equals(FIELD_ATTR_ACCESSTYPE_INPUTOUTPUT))
         return true;
    else return false;
  }
  /**
   * whether contained content is present, including XML comments
   * @return boolean
   */

  public boolean hasChildElement ()
  {
    Element elem = elementLocation.element; // me
    return (elem.getContentSize() > 0);
  }

  public boolean isInProtoInterfaceAndThereIsAnIS_CONNECTmirrorField()
  {
    Element elem = elementLocation.element; // me
    elem = elem.getParentElement();
    if(elem == null || !elem.getName().equals(PROTOINTERFACE_ELNAME))
      return false;
    elem = elem.getParentElement();
    if(elem == null || !elem.getName().equals(PROTODECLARE_ELNAME))
      return false;
    elem = elem.getChild(PROTOBODY_ELNAME);
    if(elem == null)
      return false;
    return foundIsConnectWithSameName(elem);
  }
  
  @SuppressWarnings("unchecked")
  public boolean foundIsConnectWithSameName(Element elem)
  {
    if(elem.getName().equals(IS_ELNAME))
      if(foundConnectWithSameName(elem))
        return true;

    for(Element ch : (List<Element>)elem.getChildren()) {
      if(foundIsConnectWithSameName(ch))
        return true;
    }
    return false; 
  }
  
  @SuppressWarnings("unchecked")
  public boolean foundConnectWithSameName(Element elem)
  {
    for(Element ch : (List<Element>)elem.getChildren()) {
      if(ch.getName().equals(CONNECT_ELNAME)) {
        Attribute attr = ch.getAttribute(CONNECT_ATTR_PROTOFIELD_NAME);
        if(attr != null && attr.getValue().equals(this.getName()))
          return true;
      }
    }
    return false;    
  }
  
  @Override
  public void initialize()
  {
    accessType    = FIELD_ATTR_ACCESSTYPE_DFLT;
    name          = FIELD_ATTR_NAME_DFLT;
    type          = FIELD_ATTR_TYPE_DFLT;
    value         = FIELD_ATTR_VALUE_DFLT;
    appinfo       = FIELD_ATTR_APPINFO_DFLT;
    documentation = FIELD_ATTR_DOC_DFLT;
  }
  
 @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    
    initialize(); // reset default values for this invocation

    org.jdom.Attribute attr = root.getAttribute(FIELD_ATTR_NAME_NAME);
    if(attr != null)
      name = attr.getValue();
    attr = root.getAttribute(FIELD_ATTR_TYPE_NAME);
    if(attr != null)
      type = attr.getValue();    
    attr = root.getAttribute(FIELD_ATTR_ACCESSTYPE_NAME);
    if(attr != null)
      accessType = attr.getValue();
    attr = root.getAttribute(FIELD_ATTR_VALUE_NAME);
    if(attr != null)
      value = attr.getValue();
    attr = root.getAttribute(FIELD_ATTR_APPINFO_NAME);
    if(attr != null)
      appinfo = attr.getValue();
    attr = root.getAttribute(FIELD_ATTR_DOC_NAME);
    if(attr != null)
      documentation = attr.getValue();
  }
  
 @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return FIELDCustomizer.class;
  }

 /*
  * Before we serialize, do a bit of figuring
  */
  @Override
  public String createBody()
  {
    if (parent == null || !parent.equals(EXTERNPROTODECLARE_ELNAME)) {
      // be careful here because it overwrites any child SF or MFNode
      // TODO:  only insert comment content if parent is ProtoInterface or Script (not allowed for ExternProtoDeclare)
      if (type.equalsIgnoreCase("SFNode") && this.getContent().length() <= 0 &&
          (accessType.equalsIgnoreCase("initializeOnly") || accessType.equalsIgnoreCase("inputOutput")))
        this.setContent("\n      <!--initialization node (if any) goes here-->\n    ");
      else if (type.equalsIgnoreCase("MFNode") && this.getContent().length() <= 0 &&
          (accessType.equalsIgnoreCase("initializeOnly") || accessType.equalsIgnoreCase("inputOutput")))
        this.setContent("\n      <!--initialization nodes (if any) go here-->\n    ");
    }
    
    // continue with base class work
    return super.createBody();
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    // output attributes in canonical (alphabetical) order
    if (FIELD_ATTR_ACCESSTYPE_REQD || !accessType.equals(FIELD_ATTR_ACCESSTYPE_DFLT)) {
      sb.append(" ");
      sb.append(FIELD_ATTR_ACCESSTYPE_NAME);
      sb.append("='");
      sb.append(accessType);
      sb.append("'");
    }
    if (FIELD_ATTR_APPINFO_REQD || !appinfo.equals(FIELD_ATTR_APPINFO_DFLT)) {
      sb.append(" ");
      sb.append(FIELD_ATTR_APPINFO_NAME);
      sb.append("='");
      sb.append(appinfo);
      sb.append("'");
    }
    if (FIELD_ATTR_DOC_REQD || !documentation.equals(FIELD_ATTR_DOC_DFLT)) {
      sb.append(" ");
      sb.append(FIELD_ATTR_DOC_NAME);
      sb.append("='");
      sb.append(documentation);
      sb.append("'");
    }
    if (FIELD_ATTR_NAME_REQD || !name.equals(FIELD_ATTR_NAME_DFLT)){
      sb.append(" ");
      sb.append(FIELD_ATTR_NAME_NAME);
      sb.append("='");
      sb.append(escapeXmlCharacters(name));
      sb.append("'");
    }
    if (FIELD_ATTR_TYPE_REQD || !type.equals(FIELD_ATTR_TYPE_DFLT)) {
      sb.append(" ");
      sb.append(FIELD_ATTR_TYPE_NAME);
      sb.append("='");
      sb.append(type);
      sb.append("'");
    }
       
    if (FIELD.canHaveValue(type, accessType) && (parentExternProtoDeclare == false)) {
      if (FIELD_ATTR_VALUE_REQD || !value.equals(FIELD_ATTR_VALUE_DFLT)) {
        sb.append(" ");
        sb.append(FIELD_ATTR_VALUE_NAME);
        sb.append("='");
        if (getType().equals("MFString") && !value.isEmpty() && !value.contains("\""))
            sb.append("\"");
        sb.append(value);
        if (getType().equals("MFString") && !value.isEmpty() && !value.contains("\""))
            sb.append("\"");
        sb.append("'");
      }
    }
    return sb.toString();
  }
    
  @Override
  public String getElementName()
  {
    return FIELD_ELNAME;
  }

  public String getAccessType()
  {
    return accessType;
  }

  public void setAccessType(String accessType)
  {
    this.accessType = accessType;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getType()
  {
    return type;
  }

  public void setType(String type)
  {
    this.type = type;
  }
  
  public String getValue()
  {
    return value;
  }
  
  public void setValue(String value)
  {
    this.value = value;
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
     * @return the parentExternProtoDeclare
     */
    public boolean isParentExternProtoDeclare() {
        return parentExternProtoDeclare;
    }

    /**
     * @param parentExternProtoDeclare the parentExternProtoDeclare to set
     */
    public void setParentExternProtoDeclare(boolean parentExternProtoDeclare) {
        this.parentExternProtoDeclare = parentExternProtoDeclare;
    }
}
