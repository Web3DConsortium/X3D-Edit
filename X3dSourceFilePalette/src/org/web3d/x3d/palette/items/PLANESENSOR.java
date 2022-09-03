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
import org.web3d.x3d.types.X3DDragSensorNode;

import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;
import static org.web3d.x3d.palette.X3DPaletteUtilities.*;

/**
 * PLANESENSOR.java
 * Created on Sep 12, 2007, 2:46 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class PLANESENSOR extends X3DDragSensorNode
{
  private String description;
  private SFFloat minPosition0, minPosition0Default;
  private SFFloat minPosition1, minPosition1Default;
  private SFFloat maxPosition0, maxPosition0Default;
  private SFFloat maxPosition1, maxPosition1Default;
  private SFFloat offsetX, offset0Default;
  private SFFloat offsetY, offset1Default;
  private SFFloat offsetZ, offset2Default;
  private boolean autoOffset, autoOffsetDefault;
  // Together make one SFRotation
  public SFFloat axisRotation0, axisRotation0Default;
  public SFFloat axisRotation1, axisRotation1Default;
  public SFFloat axisRotation2, axisRotation2Default;
  public SFFloat axisRotation3, axisRotation3Default;
  
  public PLANESENSOR()
  {
      this.setTraceEventsSelectionAvailable(true);
      this.setTraceEventsTooltip("Trace " + getElementName() + " events on X3D browser console");
  }
  
  @Override
  public final String getElementName()
  {
    return PLANESENSOR_ELNAME;
  } 
   
  @Override
  @SuppressWarnings("NestedAssignment")
  public void initialize()
  {
    description = PLANESENSOR_ATTR_DESCRIPTION_DFLT;
    enabled    = enabledDefault    = Boolean.parseBoolean(PLANESENSOR_ATTR_ENABLED_DFLT);
    autoOffset = autoOffsetDefault = Boolean.parseBoolean(PLANESENSOR_ATTR_AUTOOFFSET_DFLT);
    
    String[] sa = parse2(PLANESENSOR_ATTR_MINPOSITION_DFLT);
    minPosition0 = minPosition0Default = new SFFloat(sa[0],null,null);
    minPosition1 = minPosition1Default = new SFFloat(sa[1],null,null);
    
    sa = parse2(PLANESENSOR_ATTR_MAXPOSITION_DFLT);
    maxPosition0 = maxPosition0Default = new SFFloat(sa[0],null,null);
    maxPosition1 = maxPosition1Default = new SFFloat(sa[1],null,null);
   
    sa = parse3(PLANESENSOR_ATTR_OFFSET_DFLT);
    offsetX = offset0Default = new SFFloat(sa[0],null,null);
    offsetY = offset1Default = new SFFloat(sa[1],null,null);
    offsetZ = offset2Default = new SFFloat(sa[2],null,null);

    String[] fa;
    fa = parse4(PLANESENSOR_ATTR_AXISROTATION_DFLT);
    axisRotation0 = axisRotation0Default = new SFFloat(fa[0], null, null);
    axisRotation1 = axisRotation1Default = new SFFloat(fa[1], null, null);
    axisRotation2 = axisRotation2Default = new SFFloat(fa[2], null, null);
    axisRotation3 = axisRotation3Default = new SFFloat(fa[3], null, null);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(PLANESENSOR_ATTR_DESCRIPTION_NAME);
    if (attr != null)
      description = attr.getValue();
    attr = root.getAttribute(PLANESENSOR_ATTR_ENABLED_NAME);
    if (attr != null)
      enabled = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(PLANESENSOR_ATTR_AUTOOFFSET_NAME);
    if (attr != null)
      autoOffset = Boolean.parseBoolean(attr.getValue());
    String[] sa;

    attr = root.getAttribute(PLANESENSOR_ATTR_MINPOSITION_NAME);
    if (attr != null) {
      sa = parse2(attr.getValue());
      minPosition0 = new SFFloat(sa[0], null, null);
      minPosition1 = new SFFloat(sa[1], null, null);
    }
    attr = root.getAttribute(PLANESENSOR_ATTR_MAXPOSITION_NAME);
    if (attr != null) {
      sa = parse2(attr.getValue());
      maxPosition0 = new SFFloat(sa[0], null, null);
      maxPosition1 = new SFFloat(sa[1], null, null);
    }
    attr = root.getAttribute(PLANESENSOR_ATTR_OFFSET_NAME);
    if (attr != null) {
      sa = parse3(attr.getValue());
      offsetX = new SFFloat(sa[0], null, null);
      offsetY = new SFFloat(sa[1], null, null);
      offsetZ = new SFFloat(sa[2], null, null);
    }
    
    String[] fa;
    attr = root.getAttribute(PLANESENSOR_ATTR_AXISROTATION_NAME);
    if (attr != null) {
      fa = parse4(attr.getValue());
      axisRotation0 = new SFFloat(fa[0], null, null);
      axisRotation1 = new SFFloat(fa[1], null, null);
      axisRotation2 = new SFFloat(fa[2], null, null);
      axisRotation3 = new SFFloat(fa[3], null, null);
    }
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return PLANESENSORCustomizer.class;
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (PLANESENSOR_ATTR_AUTOOFFSET_REQD || autoOffset != autoOffsetDefault) {
      sb.append(" ");
      sb.append(PLANESENSOR_ATTR_AUTOOFFSET_NAME);
      sb.append("='");
      sb.append(autoOffset);
      sb.append("'");
    }
    if (PLANESENSOR_ATTR_DESCRIPTION_REQD || !description.equals(PLANESENSOR_ATTR_DESCRIPTION_DFLT)) {
      sb.append(" ");
      sb.append(PLANESENSOR_ATTR_DESCRIPTION_NAME);
      sb.append("='");
      sb.append(escapeXmlCharacters(description));
      sb.append("'");
    }
    if (PLANESENSOR_ATTR_ENABLED_REQD || enabled != enabledDefault) {
      sb.append(" ");
      sb.append(PLANESENSOR_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }
    if (PLANESENSOR_ATTR_MAXPOSITION_REQD || !maxPosition0.equals(maxPosition0Default) || !maxPosition1.equals(maxPosition1Default)) {
      sb.append(" ");
      sb.append(PLANESENSOR_ATTR_MAXPOSITION_NAME);
      sb.append("='");
      sb.append(maxPosition0);
      sb.append(" ");
      sb.append(maxPosition1);
      sb.append("'");
    }
    if (PLANESENSOR_ATTR_MINPOSITION_REQD || !minPosition0.equals(minPosition0Default) || !minPosition1.equals(minPosition1Default)) {
      sb.append(" ");
      sb.append(PLANESENSOR_ATTR_MINPOSITION_NAME);
      sb.append("='");
      sb.append(minPosition0);
      sb.append(" ");
      sb.append(minPosition1);
      sb.append("'");
    }
    if (PLANESENSOR_ATTR_OFFSET_REQD || !offsetX.equals(offset0Default) || !offsetY.equals(offset1Default) || !offsetZ.equals(offset2Default)) {
      sb.append(" ");
      sb.append(PLANESENSOR_ATTR_OFFSET_NAME);
      sb.append("='");
      sb.append(offsetX);
      sb.append(" ");
      sb.append(offsetY);
      sb.append(" ");
      sb.append(offsetZ);
      sb.append("'");
    }
    if (PLANESENSOR_ATTR_AXISROTATION_REQD ||
                   (!axisRotation0.equals(axisRotation0Default) ||
                    !axisRotation1.equals(axisRotation1Default) ||
                    !axisRotation2.equals(axisRotation2Default) ||
                    !axisRotation3.equals(axisRotation3Default))) {
      sb.append(" ");
      sb.append(PLANESENSOR_ATTR_AXISROTATION_NAME);
      sb.append("='");
      sb.append(axisRotation0);
      sb.append(" ");
      sb.append(axisRotation1);
      sb.append(" ");
      sb.append(axisRotation2);
      sb.append(" ");
      sb.append(axisRotation3);
      sb.append("'");
    }
    return sb.toString();
  }
  
  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public boolean isAutoOffset()
  {
    return autoOffset;
  }

  public void setAutoOffset(boolean autoOffset)
  {
    this.autoOffset = autoOffset;
  }

  public String getMaxPosition0()
  {
    return maxPosition0.toString();
  }

  public void setMaxPosition0(String maxPosition0)
  {
    this.maxPosition0 = new SFFloat(maxPosition0,null,null);
  }

  public String getMaxPosition1()
  {
    return maxPosition1.toString();
  }

  public void setMaxPosition1(String maxPosition1)
  {
    this.maxPosition1 = new SFFloat(maxPosition1,null,null);
  }

  public String getMinPosition0()
  {
    return minPosition0.toString();
  }

  public void setMinPosition0(String minPosition0)
  {
    this.minPosition0 = new SFFloat(minPosition0,null,null);
  }

  public String getMinPosition1()
  {
    return minPosition1.toString();
  }

  public void setMinPosition1(String minPosition1)
  {
    this.minPosition1 = new SFFloat(minPosition1,null,null);
  }

  public String getOffsetX()
  {
    return offsetX.toString();
  }

  public void setOffsetX(String offsetX)
  {
    this.offsetX = new SFFloat(offsetX,null,null);
  }

  public String getOffsetY()
  {
    return offsetY.toString();
  }

  public void setOffsetY(String offsetY)
  {
    this.offsetY = new SFFloat(offsetY,null,null);
  }

  public String getOffset2()
  {
    return offsetZ.toString();
  }

  public void setOffsetZ(String offsetZ)
  {
    this.offsetZ = new SFFloat(offsetZ,null,null);
  }  

  public String getAxisRotationX()
  {
    return axisRotation0.toString();
  }

  public void setAxisRotation0(String axisRotation0)
  {
    this.axisRotation0 = new SFFloat(axisRotation0, null, null);
  }

  public String getAxisRotationY()
  {
    return axisRotation1.toString();
  }

  public void setAxisRotation1(String axisRotation1)
  {
    this.axisRotation1 = new SFFloat(axisRotation1, null, null);
  }

  public String getAxisRotationZ()
  {
    return axisRotation2.toString();
  }

  public void setAxisRotation2(String axisRotation2)
  {
    this.axisRotation2 = new SFFloat(axisRotation2, null, null);
  }

  public String getAxisRotationAngle()
  {
    return radiansFormat.format(axisRotation3.getValue());
  }

  public void setAxisRotation3(String axisRotation3)
  {
    this.axisRotation3 = new SFFloat(axisRotation3, null, null);
  }
}
