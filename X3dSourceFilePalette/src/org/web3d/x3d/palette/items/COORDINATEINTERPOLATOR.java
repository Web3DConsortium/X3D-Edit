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
import org.web3d.x3d.types.X3DInterpolatorNode;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * COORDINATEINTERPOLATOR.java
 * Created on Sep 13, 2007, 3:05 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */

public class COORDINATEINTERPOLATOR extends X3DInterpolatorNode
{
  private SFFloat[]   key, keyDefault;
  private SFFloat[][] keyValue, keyValueDefault;
  private boolean     insertCommas, insertLineBreaks = false;
  
  public COORDINATEINTERPOLATOR()
  {
      this.setTraceEventsSelectionAvailable(true);
      this.setTraceEventsTooltip("Trace " + getElementName() + " events on X3D browser console");
  }

  @Override
  public final String getElementName()
  {
    return COORDINATEINTERPOLATOR_ELNAME;
  }
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return COORDINATEINTERPOLATORCustomizer.class;
  }

  @Override
  public void initialize()
  {
    String[] sa;
    if(COORDINATEINTERPOLATOR_ATTR_KEY_DFLT == null || COORDINATEINTERPOLATOR_ATTR_KEY_DFLT.length()<=0)
      sa = new String[]{}; // empty 
    else
      sa = parseX(COORDINATEINTERPOLATOR_ATTR_KEY_DFLT);
    key = keyDefault = parseToSFFloatArray(sa); 
    
    if(COORDINATEINTERPOLATOR_ATTR_KEYVALUE_DFLT == null || COORDINATEINTERPOLATOR_ATTR_KEYVALUE_DFLT.length()<=0)
      sa = new String[]{}; // empty 
    else
      sa = parseX(COORDINATEINTERPOLATOR_ATTR_KEYVALUE_DFLT);
    keyValue = keyValueDefault = parseToSFFloatTable(sa,3); 
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;
    
    attr = root.getAttribute(COORDINATEINTERPOLATOR_ATTR_KEY_NAME);
    if (attr != null) {
         String[] sa = parseX(attr.getValue());
         key = parseToSFFloatArray(sa);
         if (attr.getValue().contains(","))  insertCommas     = true;
         if (attr.getValue().contains("\n") ||
             attr.getValue().contains("\r")) insertLineBreaks = true; // TODO not working, line breaks not being passed from JDOM
         if (insertCommas)                   insertLineBreaks = true; // workaround default, if commas were present then most likely lineBreaks also
    }
    
    attr = root.getAttribute(COORDINATEINTERPOLATOR_ATTR_KEYVALUE_NAME);
    if (attr != null)
    {
      String[] sa = parseX(attr.getValue());
      int numberKeyValues = sa.length;
      int numberRows = key.length; // note key is already parsed
      int numberColumns = numberKeyValues / numberRows;
      keyValue = parseToSFFloatTable(sa,numberColumns);
      
         if (attr.getValue().contains(","))  insertCommas     = true;
         if (attr.getValue().contains("\n") ||
             attr.getValue().contains("\r")) insertLineBreaks = true; // TODO not working, line breaks not being passed from JDOM
         if (insertCommas)                   insertLineBreaks = true; // workaround default, if commas were present then most likely lineBreaks also
    }
  }
 
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    if (COORDINATEINTERPOLATOR_ATTR_KEY_REQD || !arraysIdenticalOrNull(key,keyDefault)) {
      sb.append(" ");
      sb.append(COORDINATEINTERPOLATOR_ATTR_KEY_NAME);
      sb.append("='");
      sb.append(formatFloatArray(key, insertCommas, insertLineBreaks));
      sb.append("'");
     
    }
    if (COORDINATEINTERPOLATOR_ATTR_KEYVALUE_REQD || !arraysIdenticalOrNull(keyValue,keyValueDefault)) {
      
      sb.append(" ");
      sb.append(COORDINATEINTERPOLATOR_ATTR_KEYVALUE_NAME);
      sb.append("='");
      sb.append(formatFloatArray(keyValue, insertCommas, insertLineBreaks));
      sb.append("'");
    }
    return sb.toString();
  }

  public String[][] getKeysAndValues()
  {
    if (key.length == 0)
        return new String[][]{{ }}; // default key, keyValue pair is empty
    
    int kLen = Math.min(key.length, keyValue.length); // TODO warning, will truncate data if one array is shorter
    
    String[][] saa = new String[kLen][keyValue[0].length+1];
    
    for(int r=0; r<kLen; r++)
    {
      saa[r][0] = key[r].toString();
      for(int c=0; c<keyValue[0].length; c++)
      {
        saa[r][c+1] = keyValue[r][c].toString();
      }
    }
    return saa;
  }
  
  public void setKeysAndValues(String[][] saa)
  {
    if(saa.length == 0) {
      key = new SFFloat[]{};
      keyValue = new SFFloat[][]{};
      return;
    }
    
    key = new SFFloat[saa.length];
    keyValue = new SFFloat[saa.length][saa[0].length-1]; // x,y,z
    for (int r=0;r<saa.length; r++) {
      key[r] = buildSFFloat(saa[r][0]);
      for(int c=1;c<saa[0].length;c++) {
        keyValue[r][c-1] = buildSFFloat(saa[r][c]);
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
     * @param insertLineBreaks the insertLineBreak values to set
     */
    public void setInsertLineBreaks(boolean insertLineBreaks) {
        this.insertLineBreaks = insertLineBreaks;
    }
 
}
