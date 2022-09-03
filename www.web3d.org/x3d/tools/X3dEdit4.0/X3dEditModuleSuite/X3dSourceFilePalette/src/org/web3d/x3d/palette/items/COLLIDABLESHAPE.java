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
import org.web3d.x3d.types.X3DNBodyCollidableNode;

import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * COLLIDABLESHAPE.java
 * Created on July 11, 2007, 5:13 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class COLLIDABLESHAPE extends X3DNBodyCollidableNode
{
  private boolean enabled, enabledDefault;
  
  private SFFloat translation0, translation0Default;
  private SFFloat translation1, translation1Default;
  private SFFloat translation2, translation2Default;

  private SFFloat rotation0, rotation0Default;
  private SFFloat rotation1, rotation1Default;
  private SFFloat rotation2, rotation2Default;
  private SFFloat rotation3, rotation3Default;

  public COLLIDABLESHAPE()
  {
  }

  @Override
  public String getElementName()
  {
    return COLLIDABLESHAPE_ELNAME;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return COLLIDABLESHAPECustomizer.class;
  }

  @Override
  public void initialize()
  {
    enabled   = enabledDefault = Boolean.parseBoolean(COLLIDABLESHAPE_ATTR_ENABLED_DFLT);
    
    String[] fa;
    fa = parse3(COLLIDABLESHAPE_ATTR_TRANSLATION_DFLT);
    translation0 = translation0Default = new SFFloat(fa[0], null, null);
    translation1 = translation1Default = new SFFloat(fa[1], null, null);
    translation2 = translation2Default = new SFFloat(fa[2], null, null);

    fa = parse4(COLLIDABLESHAPE_ATTR_ROTATION_DFLT);
    rotation0 = rotation0Default = new SFFloat(fa[0], null, null);
    rotation1 = rotation1Default = new SFFloat(fa[1], null, null);
    rotation2 = rotation2Default = new SFFloat(fa[2], null, null);
    rotation3 = rotation3Default = new SFFloat(fa[3], null, null);

    fa = parse3(COLLIDABLESHAPE_ATTR_BBOXCENTER_DFLT);
    bboxCenterX = bboxCenterXDefault = new SFFloat(fa[0], null, null);
    bboxCenterY = bboxCenterYDefault = new SFFloat(fa[1], null, null);
    bboxCenterZ = bboxCenterZDefault = new SFFloat(fa[2], null, null);

    fa = parse3(COLLIDABLESHAPE_ATTR_BBOXSIZE_DFLT);
    bboxSizeX = bboxSizeXDefault = new SFFloat(fa[0], null, null);
    bboxSizeY = bboxSizeYDefault = new SFFloat(fa[1], null, null);
    bboxSizeZ = bboxSizeZDefault = new SFFloat(fa[2], null, null);

    // TODO schematron rule to ensure child Shape has containerField='shape'
    
    setContent("\n\t\t<Shape containerField='shape'>\n\t\t\t<!--insert collidable geometry node here (no point or line nodes)-->\n\t\t\t<Appearance>\n\t\t\t<Material/>\n\t\t\t</Appearance>\n\t\t</Shape>\n\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;
    
    attr = root.getAttribute(COLLIDABLESHAPE_ATTR_ENABLED_NAME);
    if (attr != null)
      enabled   = Boolean.parseBoolean(attr.getValue());

    String[] fa;
    attr = root.getAttribute(COLLIDABLESHAPE_ATTR_TRANSLATION_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      translation0 = new SFFloat(fa[0], null, null);
      translation1 = new SFFloat(fa[1], null, null);
      translation2 = new SFFloat(fa[2], null, null);
    }
    attr = root.getAttribute(COLLIDABLESHAPE_ATTR_ROTATION_NAME);
    if (attr != null) {
      fa = parse4(attr.getValue());
      rotation0 = new SFFloat(fa[0], null, null);
      rotation1 = new SFFloat(fa[1], null, null);
      rotation2 = new SFFloat(fa[2], null, null);
      rotation3 = new SFFloat(fa[3], null, null);
    }
    attr = root.getAttribute(COLLIDABLESHAPE_ATTR_BBOXCENTER_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      bboxCenterX = new SFFloat(fa[0], null, null);
      bboxCenterY = new SFFloat(fa[1], null, null);
      bboxCenterZ = new SFFloat(fa[2], null, null);
    }
    attr = root.getAttribute(COLLIDABLESHAPE_ATTR_BBOXSIZE_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      bboxSizeX = new SFFloat(fa[0], null, null);
      bboxSizeY = new SFFloat(fa[1], null, null);
      bboxSizeZ = new SFFloat(fa[2], null, null);
    }
  }

  @Override
  public String createAttributes()
  {
    StringBuffer sb = new StringBuffer();

    if (COLLIDABLESHAPE_ATTR_BBOXCENTER_REQD ||
           (!bboxCenterX.equals(bboxCenterXDefault) ||
            !bboxCenterY.equals(bboxCenterYDefault) ||
            !bboxCenterZ.equals(bboxCenterZDefault))) {
      sb.append(" ");
      sb.append(COLLIDABLESHAPE_ATTR_BBOXCENTER_NAME);
      sb.append("='");
      sb.append(bboxCenterX);
      sb.append(" ");
      sb.append(bboxCenterY);
      sb.append(" ");
      sb.append(bboxCenterZ);
      sb.append("'");
    }
    if (COLLIDABLESHAPE_ATTR_BBOXSIZE_REQD ||
           (!bboxSizeX.equals(bboxSizeXDefault) ||
            !bboxSizeY.equals(bboxSizeYDefault) ||
            !bboxSizeZ.equals(bboxSizeZDefault))) {
      sb.append(" ");
      sb.append(COLLIDABLESHAPE_ATTR_BBOXSIZE_NAME);
      sb.append("='");
      sb.append(bboxSizeX);
      sb.append(" ");
      sb.append(bboxSizeY);
      sb.append(" ");
      sb.append(bboxSizeZ);
      sb.append("'");
    }
    if (COLLIDABLESHAPE_ATTR_ENABLED_REQD || enabled != enabledDefault) {
      sb.append(" ");
      sb.append(COLLIDABLESHAPE_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }
    if (COLLIDABLESHAPE_ATTR_ROTATION_REQD ||
                   (!rotation0.equals(rotation0Default) ||
                    !rotation1.equals(rotation1Default) ||
                    !rotation2.equals(rotation2Default) ||
                    !rotation3.equals(rotation3Default))) {
      sb.append(" ");
      sb.append(COLLIDABLESHAPE_ATTR_ROTATION_NAME);
      sb.append("='");
      sb.append(rotation0);
      sb.append(" ");
      sb.append(rotation1);
      sb.append(" ");
      sb.append(rotation2);
      sb.append(" ");
      sb.append(rotation3);
      sb.append("'");
    }
    if (COLLIDABLESHAPE_ATTR_TRANSLATION_REQD ||
           (!translation0.equals(translation0Default) ||
            !translation1.equals(translation1Default) ||
            !translation2.equals(translation2Default))) {

      sb.append(" ");
      sb.append(COLLIDABLESHAPE_ATTR_TRANSLATION_NAME);
      sb.append("='");
      sb.append(translation0);
      sb.append(" ");
      sb.append(translation1);
      sb.append(" ");
      sb.append(translation2);
      sb.append("'");
    }

    return sb.toString();
  }

  public boolean isEnabled()
  {
    return enabled;
  }

  public void setEnabled(boolean enabled)
  {
    this.enabled = enabled;
  }

  public String getRotation0()
  {
    return rotation0.toString();
  }

  public void setRotation0(String rotation0)
  {
    this.rotation0 = new SFFloat(rotation0, null, null);
  }

  public String getRotation1()
  {
    return rotation1.toString();
  }

  public void setRotation1(String rotation1)
  {
    this.rotation1 = new SFFloat(rotation1, null, null);
  }

  public String getRotation2()
  {
    return rotation2.toString();
  }

  public void setRotation2(String rotation2)
  {
    this.rotation2 = new SFFloat(rotation2, null, null);
  }

  public String getRotation3()
  {
    return rotation3.toString();
  }

  public void setRotation3(String rotation3)
  {
    this.rotation3 = new SFFloat(rotation3, null, null);
  }

  public String getTranslation0()
  {
    return translation0.toString();
  }

  public void setTranslation0(String translation0)
  {
    this.translation0 = new SFFloat(translation0, null, null);
  }

  public String getTranslation1()
  {
    return translation1.toString();
  }

  public void setTranslation1(String translation1)
  {
    this.translation1 = new SFFloat(translation1, null, null);
  }

  public String getTranslation2()
  {
    return translation2.toString();
  }

  public void setTranslation2(String translation2)
  {
    this.translation2 = new SFFloat(translation2, null, null);
  }


}