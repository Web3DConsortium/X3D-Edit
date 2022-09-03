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
import org.web3d.x3d.types.X3DNormalNode;

import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * NORMAL.java
 * Created on Oct 9, 2007, 12:26 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class NORMAL extends X3DNormalNode
{
  private SFFloat[][] vector;
  private SFFloat[][] vectorDefault;
  private boolean insertCommas, insertLineBreaks = false;

  public NORMAL()
  {
  }

  @Override
  public String getElementName()
  {
    return NORMAL_ELNAME;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return NORMALCustomizer.class;
  }

  /**
   * Initialize a new element
   */
  @Override
  public void initialize()
  {
    String[] sa;
    if(NORMAL_ATTR_VECTOR_DFLT == null || NORMAL_ATTR_VECTOR_DFLT.length()<=0)
      sa = new String[]{};
    else
      sa = parse3(NORMAL_ATTR_VECTOR_DFLT);
    vector = vectorDefault = parseToSFFloatTable(sa,3);
  }

  /**
   * Initialize an element from an existing one
   */
  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);

    org.jdom.Attribute attr = root.getAttribute(NORMAL_ATTR_VECTOR_NAME);
    if (attr != null) {
      String[] sa = parseX(attr.getValue());
      vector = makeSFFloatArray(sa, 3); //new SFFloat[sa.length/3][3];
      if (attr.getValue().contains(","))
        insertCommas = true;
      if (attr.getValue().contains("\n")
          || attr.getValue().contains("\r"))
        insertLineBreaks = true; // TODO not working, line breaks not being passed from JDOM
      if (insertCommas)                   insertLineBreaks = true; // workaround default, if commas were present then most likely lineBreaks also
    }
    else
      vector = new SFFloat[][]{ }; // empty array
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (NORMAL_ATTR_VECTOR_REQD || !arraysIdenticalOrNull(vector, vectorDefault)) {
      sb.append(" ");
      sb.append(NORMAL_ATTR_VECTOR_NAME);
      sb.append("='");
      sb.append(formatFloatArray(vector, insertCommas, insertLineBreaks));
      sb.append("'");
    }

    return sb.toString();
  }

  // Attribute getters and setters
  public String[][] getVector()
  {
    if (vector == null || vector.length == 0)
      return new String[0][];

    return makeStringArray(vector);
  }

  public void setVector(String[][] saa)
  {
    if (saa != null && saa.length > 0)
      vector = makeSFFloatArray(saa);
    else
      vector = new SFFloat[][]{}; // empty array
  }

  /**
   * @return the insertCommas value
   */
  public boolean isInsertCommas()
  {
    return insertCommas;
  }

  /**
   * @param insertCommas the insertCommas value to set
   */
  public void setInsertCommas(boolean insertCommas)
  {
    this.insertCommas = insertCommas;
  }

  /**
   * @return the insertLineBreaks value
   */
  public boolean isInsertLineBreaks()
  {
    return insertLineBreaks;
  }

  /**
   * @param insertLineBreaks the insertLineBreak values to set
   */
  public void setInsertLineBreaks(boolean insertLineBreaks)
  {
    this.insertLineBreaks = insertLineBreaks;
  }

  public boolean isNil()
  {
    return !(vector != null && vector.length > 0);
  }
}
