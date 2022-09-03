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
import org.web3d.x3d.types.X3DGroupingNode;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * SWITCH.java
 * Created on August 16, 2007, 2:58 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class SWITCH extends X3DGroupingNode
{
  private SFInt32 whichChoice,whichChoiceDefault;

  public SWITCH()
  {
  }

  @Override
  public String getElementName()
  {
    return SWITCH_ELNAME;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return SWITCHCustomizer.class;
  }

  @Override
  @SuppressWarnings("NestedAssignment")
  public void initialize()
  {
    whichChoice        = new SFInt32(SWITCH_ATTR_WHICHCHOICE_PREFERRED); // author assist when creating new Switch node
    whichChoiceDefault = new SFInt32(SWITCH_ATTR_WHICHCHOICE_DFLT);

    String[] fa = parse3(SWITCH_ATTR_BBOXCENTER_DFLT);
    bboxCenterX = bboxCenterXDefault = new SFFloat(fa[0], null, null);
    bboxCenterY = bboxCenterYDefault = new SFFloat(fa[1], null, null);
    bboxCenterZ = bboxCenterZDefault = new SFFloat(fa[2], null, null);

    fa = parse3(SWITCH_ATTR_BBOXSIZE_DFLT);
    bboxSizeX = bboxSizeXDefault = new SFFloat(fa[0], 0.0f, null, true);
    bboxSizeY = bboxSizeYDefault = new SFFloat(fa[1], 0.0f, null, true);
    bboxSizeZ = bboxSizeZDefault = new SFFloat(fa[2], 0.0f, null, true);

    setContent("\n\t\t<!--TODO add children nodes here-->\n\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    
    whichChoice        = new SFInt32(SWITCH_ATTR_WHICHCHOICE_DFLT); // ensure actual default whichChoice is respected if not defined in scene

    org.jdom.Attribute attr = root.getAttribute(SWITCH_ATTR_WHICHCHOICE_NAME);
    if(attr != null)
      whichChoice = new SFInt32(attr.getValue());

    String[] fa;
    attr = root.getAttribute(SWITCH_ATTR_BBOXCENTER_NAME);
    if(attr != null) {
      fa = parse3(attr.getValue());
      bboxCenterX = new SFFloat(fa[0], null, null);
      bboxCenterY = new SFFloat(fa[1], null, null);
      bboxCenterZ = new SFFloat(fa[2], null, null);
    }
    attr = root.getAttribute(SWITCH_ATTR_BBOXSIZE_NAME);
    if(attr != null) {
      fa = parse3(attr.getValue());
      bboxSizeX = new SFFloat(fa[0], 0.0f, null, true);
      bboxSizeY = new SFFloat(fa[1], 0.0f, null, true);
      bboxSizeZ = new SFFloat(fa[2], 0.0f, null, true);
    }
  }

  @Override
  public String createAttributes()
  {
      // the following block is a bad error that overwrites all node children!  another approach is needed if appending a comment.
      // no need to append a warning comment if no children are present
//    String negativeOneWarning = "";
//    if (whichChoice.equals(whichChoiceDefault))
//        negativeOneWarning = "\n\t\t<!--" + SWITCHCustomizer.WHICHCHOICE_NEGATIVE_ONE_WARNING + "-->\n";
//
//    if (getContent().length() == 0) // lookout - do not overwrite children nodes!
//        setContent(negativeOneWarning + "\t\t<!--TODO add children nodes here-->\n\t");
    
    StringBuilder sb = new StringBuilder();

    if (SWITCH_ATTR_WHICHCHOICE_REQD || !whichChoice.equals(whichChoiceDefault)) {
      sb.append(" ");
      sb.append(SWITCH_ATTR_WHICHCHOICE_NAME);
      sb.append("='");
      sb.append(whichChoice);
      sb.append("'");
    }
    if (SWITCH_ATTR_BBOXCENTER_REQD ||
           (!bboxCenterX.equals(bboxCenterXDefault) ||
            !bboxCenterY.equals(bboxCenterYDefault) ||
            !bboxCenterZ.equals(bboxCenterZDefault)))
    {
      sb.append(" ");
      sb.append(SWITCH_ATTR_BBOXCENTER_NAME);
      sb.append("='");
      sb.append(bboxCenterX);
      sb.append(" ");
      sb.append(bboxCenterY);
      sb.append(" ");
      sb.append(bboxCenterZ);
      sb.append("'");
    }
    if (SWITCH_ATTR_BBOXSIZE_REQD ||
           (!bboxSizeX.equals(bboxSizeXDefault) ||
            !bboxSizeY.equals(bboxSizeYDefault) ||
            !bboxSizeZ.equals(bboxSizeZDefault)))
    {
      sb.append(" ");
      sb.append(SWITCH_ATTR_BBOXSIZE_NAME);
      sb.append("='");
      sb.append(bboxSizeX);
      sb.append(" ");
      sb.append(bboxSizeY);
      sb.append(" ");
      sb.append(bboxSizeZ);
      sb.append("'");
    }
    return sb.toString();
  }

  public String getWhichChoice()
  {
    return ""+whichChoice.toString();
  }

  public void setWhichChoice(String ch)
  {
    whichChoice = new SFInt32(ch);
  }
}
