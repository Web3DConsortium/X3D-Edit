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
import org.web3d.x3d.types.X3DGroupingNode;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFFloat;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * CADLAYER.java
 * Created on July 3, 2008, 5:13 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class CADLAYER extends X3DGroupingNode
{
  private String name;
  private boolean[] visible, visibleDefault;
  private boolean   insertCommas, insertLineBreaks = false;

  public CADLAYER()
  {
  }

  @Override
  public String getElementName()
  {
    return CADLAYER_ELNAME;
  }

  @Override
  public void initialize()
  {
    name = CADLAYER_ATTR_NAME_DFLT;

    String[] sa;
    if(CADLAYER_ATTR_VISIBLE_DFLT == null || CADLAYER_ATTR_VISIBLE_DFLT.length()<=0)
      sa = new String[]{}; // empty
    else
      sa = parseX(CADLAYER_ATTR_VISIBLE_DFLT);
        setVisible(visibleDefault = parseToBooleanArray(sa));

    String[] fa;
    fa = parse3(CADLAYER_ATTR_BBOXCENTER_DFLT);
    bboxCenterX = bboxCenterXDefault = new SFFloat(fa[0], null, null);
    bboxCenterY = bboxCenterYDefault = new SFFloat(fa[1], null, null);
    bboxCenterZ = bboxCenterZDefault = new SFFloat(fa[2], null, null);

    fa = parse3(CADLAYER_ATTR_BBOXSIZE_DFLT);
    bboxSizeX = bboxSizeXDefault = new SFFloat(fa[0], null, null);
    bboxSizeY = bboxSizeYDefault = new SFFloat(fa[1], null, null);
    bboxSizeZ = bboxSizeZDefault = new SFFloat(fa[2], null, null);

    setContent("\n\t\t<!--TODO add CADAssembly and other nodes here-->\n\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(CADLAYER_ATTR_NAME_NAME);
    if(attr != null)
      name = attr.getValue();
    
    attr = root.getAttribute(CADLAYER_ATTR_VISIBLE_NAME);
    if(attr != null) {
      try {
       String[] sa = parseX(attr.getValue());
                setVisible(parseToBooleanArray(sa));
      } catch(Exception ex) {
        System.err.println("Bad boolean array attribute for visible field in CADLayer element");
      }
      if (attr.getValue().contains(","))  insertCommas     = true;
      if (attr.getValue().contains("\n") ||
          attr.getValue().contains("\r")) insertLineBreaks = true; // TODO not working, line breaks not being passed from JDOM
      if (insertCommas)                   insertLineBreaks = true; // workaround default, if commas were present then most likely lineBreaks also
    }
    String[] fa;
    attr = root.getAttribute(CADLAYER_ATTR_BBOXCENTER_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      bboxCenterX = new SFFloat(fa[0], null, null);
      bboxCenterY = new SFFloat(fa[1], null, null);
      bboxCenterZ = new SFFloat(fa[2], null, null);
    }
    attr = root.getAttribute(CADLAYER_ATTR_BBOXSIZE_NAME);
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
    StringBuilder sb = new StringBuilder();
    
    if (CADLAYER_ATTR_BBOXCENTER_REQD ||
           (!bboxCenterX.equals(bboxCenterXDefault) ||
            !bboxCenterY.equals(bboxCenterYDefault) ||
            !bboxCenterZ.equals(bboxCenterZDefault))) {
      sb.append(" ");
      sb.append(CADLAYER_ATTR_BBOXCENTER_NAME);
      sb.append("='");
      sb.append(bboxCenterX);
      sb.append(" ");
      sb.append(bboxCenterY);
      sb.append(" ");
      sb.append(bboxCenterZ);
      sb.append("'");
    }
    if (CADLAYER_ATTR_BBOXSIZE_REQD ||
           (!bboxSizeX.equals(bboxSizeXDefault) ||
            !bboxSizeY.equals(bboxSizeYDefault) ||
            !bboxSizeZ.equals(bboxSizeZDefault))) {
      sb.append(" ");
      sb.append(CADLAYER_ATTR_BBOXSIZE_NAME);
      sb.append("='");
      sb.append(bboxSizeX);
      sb.append(" ");
      sb.append(bboxSizeY);
      sb.append(" ");
      sb.append(bboxSizeZ);
      sb.append("'");
    }
    if (CADLAYER_ATTR_NAME_REQD || !name.equals(CADLAYER_ATTR_NAME_DFLT)) {
      sb.append(" ");
      sb.append(CADLAYER_ATTR_NAME_NAME);
      sb.append("='");
      sb.append(escapeXmlCharacters(name));
      sb.append("'");
    }
    if (CADLAYER_ATTR_VISIBLE_REQD || getVisible() != visibleDefault) {
      sb.append(" ");
      sb.append(CADLAYER_ATTR_VISIBLE_NAME);
      sb.append("='");
      sb.append(formatBooleanArray(getVisible(),insertCommas, insertLineBreaks));
      sb.append("'");
   }

    return sb.toString();
  }

  public String getName()
  {
    return name;
  }
  
  public void setName(String s)
  {
    name = s;
  }
  
    public boolean isInsertCommas() {
        return insertCommas;
    }

    /**
     * @param insertCommas the insertCommas value to set
     */
    public void setInsertCommas(boolean insertCommas) {
        this.insertCommas = insertCommas;
    }

    /**
     * @return the insertLineBreaks value
     */
    public boolean isInsertLineBreaks() {
        return insertLineBreaks;
    }

    /**
     * @param insertLineBreaks the insertLineBreaks value to set
     */
    public void setInsertLineBreaks(boolean insertLineBreaks) {
        this.insertLineBreaks = insertLineBreaks;
    }

    /**
     * @return the visible array
     */
    public boolean[] getVisible()
    {
        return visible;
    }

    /**
     * @param index of boolean value of interest
     * @return the visible value at index position
     */
    public boolean getVisible(int index)
    {
        return visible[index];
    }

    /**
     * @param visibleArray the visible array to set
     */
    public void setVisible(boolean[] visibleArray)
    {
        this.visible = visibleArray;
    }

    /**
     * @param visibleArray the visible array to set
     */
    public void setVisible(String visibleArray)
    {
        this.visible = parseToBooleanArray(parseX(visibleArray));
    }

    /**
     * @param visibleArray the visible array to set
     */
    public void setVisible(String[] visibleArray)
    {
        this.visible = parseToBooleanArray(visibleArray);
    }

    /**
     * set single visible value in array
     * @param value the visible element to set
     * @param index of the visible element to set
     */
    public void setVisible(boolean value, int index)
    {
        if ((index >= 0) && (index < visible.length))
            this.visible[index] = value;
        else System.out.println("Illegal index " + index + " for boolean array visible of length " + visible.length);
    }
}