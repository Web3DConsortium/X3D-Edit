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
import org.web3d.x3d.types.X3DChildNode;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * ROUTE.java
 * Created on Sep 12, 2007, 2:46 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class ROUTE extends X3DChildNode  // I don't think this is right
{
  private String fromNode="",fromField="";
  private String   toNode="",  toField="";
  private String eventType = "unknownType"; // determined and set by ROUTECustomizer
  
  public ROUTE()
  {
      this.setTraceEventsSelectionAvailable(true);
      this.setTraceEventsTooltip("Trace " + getElementName() + " events on X3D browser console");
  }

  @Override
  public final String getElementName()
  {
    return ROUTE_ELNAME;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return ROUTECustomizer.class;
  }

  @Override
  public void initialize()
  {
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(ROUTE_ATTR_FROMNODE_NAME);
    if(attr != null)
      fromNode = attr.getValue();    
     attr = root.getAttribute(ROUTE_ATTR_FROMFIELD_NAME);
    if(attr != null)
      fromField = attr.getValue();
    attr = root.getAttribute(ROUTE_ATTR_TONODE_NAME);
    if(attr != null)
      toNode = attr.getValue();
    attr = root.getAttribute(ROUTE_ATTR_TOFIELD_NAME);
    if(attr != null)
      toField = attr.getValue();
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    // attribute order matching X3D canonical form
    if (ROUTE_ATTR_FROMFIELD_REQD) {
      sb.append(" ");
      sb.append(ROUTE_ATTR_FROMFIELD_NAME);
      sb.append("='");
      sb.append(fromField);
      sb.append("'");
    }
    if (ROUTE_ATTR_FROMNODE_REQD) {
      sb.append(" ");
      sb.append(ROUTE_ATTR_FROMNODE_NAME);
      sb.append("='");
      sb.append(fromNode);
      sb.append("'");
    }
    if (ROUTE_ATTR_TOFIELD_REQD) {
      sb.append(" ");
      sb.append(ROUTE_ATTR_TOFIELD_NAME);
      sb.append("='");
      sb.append(toField);
      sb.append("'");
    }
    if (ROUTE_ATTR_TONODE_REQD) {
      sb.append(" ");
      sb.append(ROUTE_ATTR_TONODE_NAME);
      sb.append("='");
      sb.append(toNode);
      sb.append("'");
    }
    return sb.toString();
  }

  public String getFromField()
  {
    return fromField;
  }

  public void setFromField(String fromField)
  {
    this.fromField = fromField==null?"":fromField;
  }

  public String getFromNode()
  {
    return fromNode;
  }

  public void setFromNode(String fromNode)
  {
    this.fromNode = fromNode==null?"":fromNode;
  }

  public String getToField()
  {
    return toField;
  }

  public void setToField(String toField)
  {
    this.toField = toField==null?"":toField;
  }

  public String getToNode()
  {
    return toNode;
  }

  public void setToNode(String toNode)
  {
    this.toNode = toNode==null?"":toNode;
  }

    /**
     * @return the eventType
     */
    public String getEventType() {
        return eventType;
    }

    /**
     * @param eventType the eventType to set, determined and set by ROUTECustomizer
     */
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
}
