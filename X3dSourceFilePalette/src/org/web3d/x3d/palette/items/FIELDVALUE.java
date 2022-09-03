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
import org.jdom.Element;
import static org.web3d.x3d.palette.X3DPaletteUtilities.escapeXmlCharacters;
import org.web3d.x3d.palette.items.PROTOINSTANCECustomizer.nodeElem;
import org.web3d.x3d.types.SceneGraphStructureNodeType;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * FIELDVALUE.java
 * Created on Mar 14, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey and Don Brutzman
 * @version $Id$
 */
public class FIELDVALUE extends SceneGraphStructureNodeType
{

  private String name  = FIELDVALUE_ATTR_NAME_DFLT;
  private String value = FIELDVALUE_ATTR_VALUE_DFLT;
  // Following used if this FIELDVALUE maps directly to an existing FIELD
  private String type = "";
  private String accessType = "";
  
  public FIELDVALUE()
  {
  }

  public FIELDVALUE(String n, String v)
  {
    name = n;
    value = v;
  }
  public FIELDVALUE(String n, String v, String typ, String accTyp)
  {
    this(n,v);
    type = typ;
    accessType = accTyp;
  }
  
  @Override
  public void initialize()
  {
    name  = FIELDVALUE_ATTR_NAME_DFLT;
    value = FIELDVALUE_ATTR_VALUE_DFLT;

    name = "newFieldValueName"; // override default, prompt user to replace
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);

    name = "newFieldValueName"; // override default, prompt user to replace

    initializeFromJdomElem(root); //doc.getRootElement());
  }

  public void initializeFromJdomElem(org.jdom.Element elm)
  {
    org.jdom.Attribute attr = elm.getAttribute(FIELDVALUE_ATTR_NAME_NAME);
    if (attr != null)
      name = attr.getValue();
    attr = elm.getAttribute(FIELDVALUE_ATTR_VALUE_NAME);
    if (attr != null)
      value = attr.getValue();
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return FIELDVALUECustomizer.class;
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    // output attributes in canonical (alphabetical) order
    if (FIELDVALUE_ATTR_NAME_REQD || !name.equals(FIELDVALUE_ATTR_NAME_DFLT)) {
      sb.append(" ");
      sb.append(FIELDVALUE_ATTR_NAME_NAME);
      sb.append("='");
      sb.append(escapeXmlCharacters(name));
      sb.append("'");
    }
    if (FIELDVALUE_ATTR_VALUE_REQD || !value.equals(FIELDVALUE_ATTR_VALUE_DFLT)) {
      if (value.length() > 0) {  // don't put a value, might be SFNode or MFNode
        sb.append(" ");
        sb.append(FIELDVALUE_ATTR_VALUE_NAME);
        sb.append("='");
        if (getType().equals("MFString") && !value.isEmpty() && !value.contains("\""))
            sb.append("\"");
        sb.append(value);
        if (getType().equals("MFString") && !value.isEmpty() && !value.contains("\""))
            sb.append("\"");
        sb.append("'");
      }
    }
    // TODO:  figure out type, accessType of ancestor field definition
    // be careful here because it overwrites any child SF or MFNode
//    if      (       type.equalsIgnoreCase("SFNode") && this.getContent().isEmpty() && 
//             (accessType.equalsIgnoreCase("initializeOnly") || accessType.equalsIgnoreCase("inputOutput")))
//      this.setContent("\n      <!--initialization node (if any) goes here-->\n    ");
//    else if (       type.equalsIgnoreCase("MFNode") && this.getContent().isEmpty() && 
//             (accessType.equalsIgnoreCase("initializeOnly") || accessType.equalsIgnoreCase("inputOutput")))
//      this.setContent("\n      <!--initialization nodes (if any) go here-->\n    ");
    
    return sb.toString();
  }

  @Override
  public String getElementName()
  {
    return FIELDVALUE_ELNAME;
  }

  public String getName()
  {
    return name;
  }

  public void setName(String name)
  {
    this.name = name;
  }

  public String getValue()
  {
    return value;
  }

  public void setValue(String value)
  {
    this.value = value;
  }

  public String getAccessType()
  {
    return accessType;
  }

  public void setAccessType(String s)
  {
    this.accessType = s;
  }

  public String getType()
  {
    return type;
  }

  public void setType(String s)
  {
    this.type = s;
  }

  /**
   * If pallette item being dropped, the elementLocation should be the PROTOINSTANCE.  If editing an existing
   * fieldValue, the elementLocation would be the fieldValue, and we need to get the parent
   * @return
   */
  public String getProtoDeclareName()
  {
    Element me = elementLocation.element;
    if(!me.getName().equals(PROTOINSTANCE_ELNAME)){ 
      me = elementLocation.element.getParentElement();
      if (me == null)
        return null;
    }
    return me.getAttributeValue(PROTOINSTANCE_ATTR_NAME_NAME);
  }

  @SuppressWarnings("unchecked")
  public Vector<Element> getFieldsList(JTextComponent txtComp, String[] parentNameAndTypeGoesHere)
  {
    Vector<Element> fieldVec = new Vector<>();
    Vector<Element> v = getNodeList(txtComp);
    if (v.size() <= 0)
      return fieldVec;

    String parentName = getProtoDeclareName();
    parentNameAndTypeGoesHere[0] = parentName;
    if (parentName == null)
      return fieldVec;

    Vector<nodeElem> ve = new Vector<>(v.size());
    Element targetExternProtoDeclareOrInterface = null;
    for (Element el : v) {
      if (el.getName().equalsIgnoreCase(PROTODECLARE_ELNAME) || el.getName().equalsIgnoreCase(EXTERNPROTODECLARE_ELNAME)) {
        String nm = el.getAttributeValue("name");
        if (nm != null && nm.equals(parentName)) {
          parentNameAndTypeGoesHere[1] = el.getName();
          if (el.getName().equalsIgnoreCase(PROTODECLARE_ELNAME)) {
            el = el.getChild(PROTOINTERFACE_ELNAME);
            if (el == null) {
              parentNameAndTypeGoesHere[1] = null;
              continue;
            }
          }
          targetExternProtoDeclareOrInterface = el;
          break;
        }
      }
    }

    if (targetExternProtoDeclareOrInterface == null)
      return fieldVec; // not found

    List<Element> fieldElementList = (List<Element>) targetExternProtoDeclareOrInterface.getChildren(FIELD_ELNAME);

    for (Element fieldElement : fieldElementList)
    {
        fieldVec.add(fieldElement);
        if (fieldElement.getAttribute("name").getValue().equals(this.name))
        {
            if (fieldElement.getAttribute("type") != null)
                this.type       = fieldElement.getAttribute("type").getValue();
            if (fieldElement.getAttribute("accessType") != null)
                this.accessType = fieldElement.getAttribute("accessType").getValue();
            if (fieldElement.getAttribute("value") != null)
                this.value      = fieldElement.getAttribute("value").getValue();
        }
    }
    return fieldVec;
  }
}
