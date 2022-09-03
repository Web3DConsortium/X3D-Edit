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
 * CYLINDERSENSOR.java
 * Created on Sep 12, 2007, 2:46 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class CYLINDERSENSOR extends X3DDragSensorNode
{
  private String  description;
  private SFFloat minAngle, minAngleDefault;
  private SFFloat maxAngle, maxAngleDefault;
  private SFFloat diskAngle, diskAngleDefault;
  private SFFloat offset, offsetDefault;
  private boolean autoOffset, autoOffsetDefault;
  // Together make one SFRotation
  public SFFloat axisRotation0, axisRotation0Default;
  public SFFloat axisRotation1, axisRotation1Default;
  public SFFloat axisRotation2, axisRotation2Default;
  public SFFloat axisRotation3, axisRotation3Default;
    
  public CYLINDERSENSOR()
  {
      this.setTraceEventsSelectionAvailable(true);
      this.setTraceEventsTooltip("Trace " + getElementName() + " events on X3D browser console");
  }
  
  @Override
  public final String getElementName()
  {
    return CYLINDERSENSOR_ELNAME;
  } 
   
  @Override
  @SuppressWarnings("NestedAssignment")
  public void initialize()
  {
    description = CYLINDERSENSOR_ATTR_DESCRIPTION_DFLT;
    enabled    = enabledDefault    = Boolean.parseBoolean(CYLINDERSENSOR_ATTR_ENABLED_DFLT);
    minAngle   = minAngleDefault   = new SFFloat(CYLINDERSENSOR_ATTR_MINANGLE_DFLT,(float)-Math.PI*2.0f, (float)Math.PI*2.0f);
    maxAngle   = maxAngleDefault   = new SFFloat(CYLINDERSENSOR_ATTR_MAXANGLE_DFLT,-(float)Math.PI*2.0f, (float)Math.PI*2.0f);
    diskAngle  = diskAngleDefault  = new SFFloat(CYLINDERSENSOR_ATTR_DISKANGLE_DFLT,0.0f, (float)Math.PI/2.0f);
    offset     = offsetDefault     = new SFFloat(CYLINDERSENSOR_ATTR_OFFSET_DFLT,null,null);
    autoOffset = autoOffsetDefault = Boolean.parseBoolean(CYLINDERSENSOR_ATTR_AUTOOFFSET_DFLT);

    String[] fa;
    fa = parse4(CYLINDERSENSOR_ATTR_AXISROTATION_DFLT);
    axisRotation0 = axisRotation0Default = new SFFloat(fa[0], null, null);
    axisRotation1 = axisRotation1Default = new SFFloat(fa[1], null, null);
    axisRotation2 = axisRotation2Default = new SFFloat(fa[2], null, null);
    axisRotation3 = axisRotation3Default = new SFFloat(fa[3], null, null);
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return CYLINDERSENSORCustomizer.class;
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(CYLINDERSENSOR_ATTR_ENABLED_NAME);
    if (attr != null)
      enabled = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(CYLINDERSENSOR_ATTR_DESCRIPTION_NAME);
    if (attr != null)
      description = attr.getValue();
    attr = root.getAttribute(CYLINDERSENSOR_ATTR_MINANGLE_NAME);
    if (attr != null)    
      minAngle = new SFFloat(attr.getValue(), (float) -Math.PI * 2.0f, (float) Math.PI * 2.0f);
    attr = root.getAttribute(CYLINDERSENSOR_ATTR_MAXANGLE_NAME);
    if (attr != null)
      maxAngle = new SFFloat(attr.getValue(), -(float) Math.PI * 2.0f, (float) Math.PI * 2.0f);
    attr = root.getAttribute(CYLINDERSENSOR_ATTR_DISKANGLE_NAME);
    if (attr != null)
      diskAngle = new SFFloat(attr.getValue(), 0.0f, (float) Math.PI / 2.0f);
    attr = root.getAttribute(CYLINDERSENSOR_ATTR_OFFSET_NAME);
    if (attr != null)
      offset = new SFFloat(attr.getValue(), null, null);
    attr = root.getAttribute(CYLINDERSENSOR_ATTR_AUTOOFFSET_NAME);
    if (attr != null)
      autoOffset = Boolean.parseBoolean(attr.getValue());
    
    String[] fa;
    attr = root.getAttribute(CYLINDERSENSOR_ATTR_AXISROTATION_NAME);
    if (attr != null) {
      fa = parse4(attr.getValue());
      axisRotation0 = new SFFloat(fa[0], null, null);
      axisRotation1 = new SFFloat(fa[1], null, null);
      axisRotation2 = new SFFloat(fa[2], null, null);
      axisRotation3 = new SFFloat(fa[3], null, null);
    }
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    
    if (CYLINDERSENSOR_ATTR_AUTOOFFSET_REQD || autoOffset != autoOffsetDefault) {
      sb.append(" ");
      sb.append(CYLINDERSENSOR_ATTR_AUTOOFFSET_NAME);
      sb.append("='");
      sb.append(autoOffset);
      sb.append("'");
    }
    if (CYLINDERSENSOR_ATTR_DESCRIPTION_REQD || !description.equals(CYLINDERSENSOR_ATTR_DESCRIPTION_DFLT)) {
      sb.append(" ");
      sb.append(CYLINDERSENSOR_ATTR_DESCRIPTION_NAME);
      sb.append("='");
      sb.append(escapeXmlCharacters(description));
      sb.append("'");
    }
    if (CYLINDERSENSOR_ATTR_DISKANGLE_REQD || !diskAngle.equals(diskAngleDefault)) {
      sb.append(" ");
      sb.append(CYLINDERSENSOR_ATTR_DISKANGLE_NAME);
      sb.append("='");
      sb.append(diskAngle);
      sb.append("'");
    }
    if (CYLINDERSENSOR_ATTR_ENABLED_REQD || enabled != enabledDefault) {
      sb.append(" ");
      sb.append(CYLINDERSENSOR_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }
    if (CYLINDERSENSOR_ATTR_MAXANGLE_REQD || !maxAngle.equals(maxAngleDefault)) {
      sb.append(" ");
      sb.append(CYLINDERSENSOR_ATTR_MAXANGLE_NAME);
      sb.append("='");
      sb.append(maxAngle);
      sb.append("'");
    }
    if (CYLINDERSENSOR_ATTR_MINANGLE_REQD || !minAngle.equals(minAngleDefault)) {
      sb.append(" ");
      sb.append(CYLINDERSENSOR_ATTR_MINANGLE_NAME);
      sb.append("='");
      sb.append(minAngle);
      sb.append("'");
    }
    if (CYLINDERSENSOR_ATTR_OFFSET_REQD || !offset.equals(offsetDefault)) {
      sb.append(" ");
      sb.append(CYLINDERSENSOR_ATTR_OFFSET_NAME);
      sb.append("='");
      sb.append(offset);
      sb.append("'");
    }
    if (CYLINDERSENSOR_ATTR_AXISROTATION_REQD ||
                   (!axisRotation0.equals(axisRotation0Default) ||
                    !axisRotation1.equals(axisRotation1Default) ||
                    !axisRotation2.equals(axisRotation2Default) ||
                    !axisRotation3.equals(axisRotation3Default))) {
      sb.append(" ");
      sb.append(CYLINDERSENSOR_ATTR_AXISROTATION_NAME);
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

  public String getDiskAngle()
  {
    return radiansFormat.format(diskAngle.getValue());
  }

  public void setDiskAngle(String diskAngle)
  {
    this.diskAngle = new SFFloat(diskAngle,0.0f, (float)Math.PI/2.0f);
  }

  public String getMaxAngle()
  {
    return radiansFormat.format(maxAngle.getValue());
  }
  
  public void setMaxAngle(String maxAngle)
  {
    this.maxAngle = new SFFloat(maxAngle,-(float)Math.PI*2.0f, (float)Math.PI*2.0f);
  }

  public String getMinAngle()
  {
    return radiansFormat.format(minAngle.getValue());
  }

  public void setMinAngle(String minAngle)
  {
    this.minAngle = new SFFloat(minAngle,-(float)Math.PI*2.0f, (float)Math.PI*2.0f);
  }

  public String getOffset()
  {
    return offset.toString();
  }

  public void setOffset(String offset)
  {
    this.offset = new SFFloat(offset,null,null);
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
