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
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * FLOATVERTEXATTRIBUTE.java
 * Created on 24 January 2010
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */

public class FLOATVERTEXATTRIBUTE extends X3DChildNode
{
  private String      name;
  private SFFloat[][] value, valueDefault;
  private SFInt32 numComponents;
  
  public FLOATVERTEXATTRIBUTE()
  {
  }

  @Override
  public String getElementName()
  {
    return FLOATVERTEXATTRIBUTE_ELNAME;
  }
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return FLOATVERTEXATTRIBUTECustomizer.class;
  }

  @Override
  public void initialize()
  {
    name = FLOATVERTEXATTRIBUTE_ATTR_NAME_DFLT;

    numComponents = new SFInt32(FLOATVERTEXATTRIBUTE_ATTR_NUMCOMPONENTS_DFLT,1,4);

    String [] sa;
    if(FLOATVERTEXATTRIBUTE_ATTR_VALUE_DFLT == null || FLOATVERTEXATTRIBUTE_ATTR_VALUE_DFLT.length()<=0)
      sa = new String[]{}; // empty
    else
      sa = parseX(FLOATVERTEXATTRIBUTE_ATTR_VALUE_DFLT);
    value = valueDefault = parseToSFFloatTable(sa,numComponents.getValue());
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(FLOATVERTEXATTRIBUTE_ATTR_NAME_NAME);
    if (attr != null)
        name = attr.getValue();

    attr = root.getAttribute(FLOATVERTEXATTRIBUTE_ATTR_NUMCOMPONENTS_NAME);
    if (attr != null)
        numComponents =  new SFInt32(attr.getValue(),1,4);
    
    attr = root.getAttribute(FLOATVERTEXATTRIBUTE_ATTR_VALUE_NAME);
    if (attr != null) {
      String[] sa = parseX(attr.getValue());
      value = parseToSFFloatTable(sa,numComponents.getValue());
    }
  }
 
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    if (FLOATVERTEXATTRIBUTE_ATTR_NAME_REQD || (!name.equals (FLOATVERTEXATTRIBUTE_ATTR_NAME_DFLT))) {
      sb.append(" ");
      sb.append(FLOATVERTEXATTRIBUTE_ATTR_NAME_NAME);
      sb.append("='");
      sb.append(getName ());
      sb.append("'");
    }
    if (FLOATVERTEXATTRIBUTE_ATTR_NUMCOMPONENTS_REQD || (!numComponents.toString().equals (FLOATVERTEXATTRIBUTE_ATTR_NUMCOMPONENTS_DFLT))) {
      sb.append(" ");
      sb.append(FLOATVERTEXATTRIBUTE_ATTR_NUMCOMPONENTS_NAME);
      sb.append("='");
      sb.append(numComponents.toString());
      sb.append("'");
    }
    if (FLOATVERTEXATTRIBUTE_ATTR_VALUE_REQD || !arraysIdenticalOrNull(value,valueDefault)) {
      sb.append(" ");
      sb.append(FLOATVERTEXATTRIBUTE_ATTR_VALUE_NAME);
      sb.append("='");
      sb.append(formatFloatArray(value));
      sb.append("'");
    }
    return sb.toString();
  }

  public String[][] getValues()
  {
    if(value.length == 0)
      return new String[][]{{}}; // something (actually nothing) to start with
    
    int kLen = value.length;
    int width = numComponents.getValue();
    
    String[][] saa = new String[kLen][width];
    
    for(int r=0; r<kLen; r++) {
      saa[r][0] = value[r].toString();
      for(int c=0; c<width; c++)
      {
          if (value[r][c] != null)
              saa[r][c] = value[r][c].toString ();
          else
              saa[r][c] = "0.0"; // fill in too-short array
      }
    }
    return saa;
  }
  
  public void setValues(String[][] saa)
  {
    if(saa.length == 0) {
      value = new SFFloat[][]{};
      return;
    }
    int width = numComponents.getValue();
    
    value = new SFFloat[saa.length][width]; // numComponents-tuple
    for (int r=0;r<saa.length; r++) {
      for(int c=0;c<width;c++) {
          if (saa[r][c] != null)
              value[r][c] = buildSFFloat(saa[r][c]);
          else
              value[r][c] = buildSFFloat("0.0");
      }
    }
  }
    /**
     * @return the name
     */
    public String getName ()
    {
        return name;
    }
    /**
     * @param name the name to set
     */
    public void setName (String name)
    {
        this.name = name;
    }

    /**
     * @return the numComponents
     */
    public int getNumComponents ()
    {
        return numComponents.getValue();
    }

    /**
     * @param numComponents the numComponents value to set
     */
    public void setNumComponents (String numComponents)
    {
        this.numComponents =  new SFInt32(numComponents,1,4);
    }
}
