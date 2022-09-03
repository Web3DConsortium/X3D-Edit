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
import org.web3d.x3d.types.X3DChildNode;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * VIEWPOINTGROUP.java
 * Created on July 11, 2007, 5:13 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class VIEWPOINTGROUP extends X3DChildNode
{
  private String  description;
  private boolean displayed;
  private boolean retainUserOffsets;
  
  private SFFloat center0, center0Default;
  private SFFloat center1, center1Default;
  private SFFloat center2, center2Default;

  private SFFloat size0, size0Default;
  private SFFloat size1, size1Default;
  private SFFloat size2, size2Default;

  public VIEWPOINTGROUP()
  {
      this.setTraceEventsSelectionAvailable(true);
      this.setTraceEventsTooltip("Trace " + getElementName() + " events on X3D browser console");
  }

  @Override
  public final String getElementName()
  {
    return VIEWPOINTGROUP_ELNAME;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return VIEWPOINTGROUPCustomizer.class;
  }

  @Override
    @SuppressWarnings("NestedAssignment")
  public void initialize()
  {
    setDescription(VIEWPOINTGROUP_ATTR_DESCRIPTION_DFLT);
    setDisplayed(Boolean.parseBoolean(VIEWPOINTGROUP_ATTR_DISPLAYED_DFLT));
    setRetainUserOffsets(Boolean.parseBoolean(VIEWPOINTGROUP_ATTR_RETAINUSEROFFSETS_DFLT));

    String[] fa;
    fa = parse3(VIEWPOINTGROUP_ATTR_CENTER_DFLT);
    center0 = center0Default = new SFFloat(fa[0], null, null);
    center1 = center1Default = new SFFloat(fa[1], null, null);
    center2 = center2Default = new SFFloat(fa[2], null, null);

    fa = parse3(VIEWPOINTGROUP_ATTR_SIZE_DFLT);
    size0 = size0Default = new SFFloat(fa[0], null, null);
    size1 = size1Default = new SFFloat(fa[1], null, null);
    size2 = size2Default = new SFFloat(fa[2], null, null);

    setContent("\n\t\t<!--TODO add Viewpoint, OrthoViewpoint or additional ViewpointGroup nodes here-->\n\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;
    
    attr = root.getAttribute(VIEWPOINTGROUP_ATTR_DESCRIPTION_NAME);
    if(attr != null)
        setDescription(attr.getValue());

    attr = root.getAttribute(VIEWPOINTGROUP_ATTR_DISPLAYED_NAME);
    if(attr != null)
        setDisplayed(Boolean.parseBoolean(attr.getValue()));

    attr = root.getAttribute(VIEWPOINTGROUP_ATTR_RETAINUSEROFFSETS_NAME);
    if(attr != null)
        setRetainUserOffsets(Boolean.parseBoolean(attr.getValue()));

    String[] fa;
     attr = root.getAttribute(VIEWPOINTGROUP_ATTR_CENTER_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      center0 = new SFFloat(fa[0], null, null);
      center1 = new SFFloat(fa[1], null, null);
      center2 = new SFFloat(fa[2], null, null);
    }
    attr = root.getAttribute(VIEWPOINTGROUP_ATTR_SIZE_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      size0 = new SFFloat(fa[0], null, null);
      size1 = new SFFloat(fa[1], null, null);
      size2 = new SFFloat(fa[2], null, null);
    }
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (VIEWPOINTGROUP_ATTR_CENTER_REQD ||
           (!center0.equals(center0Default) ||
            !center1.equals(center1Default) ||
            !center2.equals(center2Default))) {
      sb.append(" ");
      sb.append(VIEWPOINTGROUP_ATTR_CENTER_NAME);
      sb.append("='");
      sb.append(center0);
      sb.append(" ");
      sb.append(center1);
      sb.append(" ");
      sb.append(center2);
      sb.append("'");
    }

    if (VIEWPOINTGROUP_ATTR_DESCRIPTION_REQD || !description.equals(VIEWPOINTGROUP_ATTR_DESCRIPTION_DFLT)) {
      sb.append(" ");
      sb.append(VIEWPOINTGROUP_ATTR_DESCRIPTION_NAME);
      sb.append("='");
      sb.append(escapeXmlCharacters(description));
      sb.append("'");
    }

    if (VIEWPOINTGROUP_ATTR_DISPLAYED_REQD || (isDisplayed() != Boolean.parseBoolean(VIEWPOINTGROUP_ATTR_DISPLAYED_DFLT))) {
      sb.append(" ");
      sb.append(VIEWPOINTGROUP_ATTR_DISPLAYED_NAME);
      sb.append("='");
      sb.append(isDisplayed());
      sb.append("'");
    }
    
    if (VIEWPOINTGROUP_ATTR_SIZE_REQD ||
           (!size0.equals(size0Default) ||
            !size1.equals(size1Default) ||
            !size2.equals(size2Default))) {
      sb.append(" ");
      sb.append(VIEWPOINTGROUP_ATTR_SIZE_NAME);
      sb.append("='");
      sb.append(size0);
      sb.append(" ");
      sb.append(size1);
      sb.append(" ");
      sb.append(size2);
      sb.append("'");
    }

    if (VIEWPOINTGROUP_ATTR_RETAINUSEROFFSETS_REQD || (isRetainUserOffsets() != Boolean.parseBoolean(VIEWPOINTGROUP_ATTR_RETAINUSEROFFSETS_DFLT))) {
      sb.append(" ");
      sb.append(VIEWPOINTGROUP_ATTR_RETAINUSEROFFSETS_NAME);
      sb.append("='");
      sb.append(isRetainUserOffsets());
      sb.append("'");
    }

    return sb.toString();
  }

  public String getCenter0()
  {
    return center0.toString();
  }

  public void setCenter0(String center0)
  {
    this.center0 = new SFFloat(center0, null, null);
  }

  public String getCenter1()
  {
    return center1.toString();
  }

  public void setCenter1(String center1)
  {
    this.center1 = new SFFloat(center1, null, null);
  }

  public String getCenter2()
  {
    return center2.toString();
  }

  public void setCenter2(String center2)
  {
    this.center2 = new SFFloat(center2, null, null);
  }

  public String getSize0()
  {
    return size0.toString();
  }

  public void setSize0(String size0)
  {
    this.size0 = new SFFloat(size0, null, null);
  }

  public String getSize1()
  {
    return size1.toString();
  }

  public void setSize1(String size1)
  {
    this.size1 = new SFFloat(size1, null, null);
  }

  public String getSize2()
  {
    return size2.toString();
  }

  public void setSize2(String size2)
  {
    this.size2 = new SFFloat(size2, null, null);
  }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDisplayed() {
        return displayed;
    }

    public void setDisplayed(boolean displayed) {
        this.displayed = displayed;
    }

    public boolean isRetainUserOffsets() {
        return retainUserOffsets;
    }

    public void setRetainUserOffsets(boolean retainUserOffsets) {
        this.retainUserOffsets = retainUserOffsets;
    }
}