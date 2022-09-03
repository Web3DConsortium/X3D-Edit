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
import org.web3d.x3d.types.X3DColorNode;

import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * COLORRGBA.java
 * Created on Sep 11, 2007, 3:05 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class COLORRGBA extends X3DColorNode
{
  private SFFloat[][] colors,colorsDefault;
  private boolean insertCommas, insertLineBreaks = false;
  
  public COLORRGBA()
  {
  }

  @Override
  public String getElementName()
  {
    return COLORRGBA_ELNAME;
  }
  
  @Override
  public void initialize()
  {
    String[] sa;
    if(COLORRGBA_ATTR_COLOR_DFLT == null || COLORRGBA_ATTR_COLOR_DFLT.length()<=0)
      sa = new String[]{};
    else
      sa = parse4(COLORRGBA_ATTR_COLOR_DFLT);
    colors = colorsDefault = parseToSFFloatTable(sa,4);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;
    
    attr = root.getAttribute(COLORRGBA_ATTR_COLOR_NAME);
   if (attr != null)
   {
         String[]sa = parseX(attr.getValue());
         colors = parseToSFFloatTable(sa,4);
         if (attr.getValue().contains(","))  insertCommas     = true;
         if (attr.getValue().contains("\n") ||
             attr.getValue().contains("\r")) insertLineBreaks = true; // TODO not working, line breaks not being passed from JDOM
         if (insertCommas)                   insertLineBreaks = true; // workaround default, if commas were present then most likely lineBreaks also
    }
    else colors = new SFFloat[][]{}; // empty array
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return COLORRGBACustomizer.class;
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (COLORRGBA_ATTR_COLOR_REQD || !arraysIdenticalOrNull(colors,colorsDefault)) {//colorRgba.equals(COLORRGBA_ATTR_COLOR_DFLT)) {
      sb.append(" ");
      sb.append(COLORRGBA_ATTR_COLOR_NAME);
      sb.append("='");
      sb.append(formatFloatArray(colors, insertCommas, insertLineBreaks));
      sb.append("'");
    }
    return sb.toString();
  }

  public String[][] getColors()
  {
   if(colors.length == 0)
      return new String[][]{{ }}; // something to start with
    
    String[][] saa = new String[colors.length][colors[0].length];
    for(int r=0;r<colors.length;r++) {
      for(int c=0;c<colors[0].length;c++)
        saa[r][c] = colors[r][c].toString();
    }
    return saa;

  }
  
  public void setColors(String[][] saa)
  {
    if(saa.length == 0) {
      colors = new SFFloat[][]{};
      return;
    }
    
    colors = new SFFloat[saa.length][saa[0].length]; // x,y,z,a
    for (int r=0;r<saa.length; r++) {
      for(int c=0;c<saa[0].length;c++) {
        colors[r][c] =  new SFFloat(saa[r][c],0.0f,1.0f);
      }
    }  
  }

    /**
     * @return the insertCommas value
     */
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

  public boolean isNil()
  {
    return !(colors != null && colors.length > 0);
  }
}
