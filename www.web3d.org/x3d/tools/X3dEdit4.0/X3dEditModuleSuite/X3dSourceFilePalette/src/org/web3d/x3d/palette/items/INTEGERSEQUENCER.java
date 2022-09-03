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
import org.web3d.x3d.types.X3DSequencerNode;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * INTEGERSEQUENCER.java
 * Created on Sep 12, 2007, 2:46 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class INTEGERSEQUENCER extends X3DSequencerNode
{
  private SFFloat[] key, keyDefault;
  private SFInt32[]   keyValue, keyValueDefault;
  private boolean   insertCommas, insertLineBreaks = false;

  public INTEGERSEQUENCER()
  {
      this.setTraceEventsSelectionAvailable(true);
      this.setTraceEventsTooltip("Trace " + getElementName() + " events on X3D browser console");
  }
    
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return INTEGERSEQUENCERCustomizer.class;
  }
  
  @Override
  public final String getElementName()
  {
    return INTEGERSEQUENCER_ELNAME;
  }      

  @Override
  public void initialize()
  {
    String[] sa;
    if(INTEGERSEQUENCER_ATTR_KEY_DFLT == null || INTEGERSEQUENCER_ATTR_KEY_DFLT.length()<=0)
      sa = new String[]{}; // empty 
    else
      sa = parseX(INTEGERSEQUENCER_ATTR_KEY_DFLT);
    key = keyDefault = parseToSFFloatArray(sa); 
    
    if(INTEGERSEQUENCER_ATTR_KEYVALUE_DFLT == null || INTEGERSEQUENCER_ATTR_KEYVALUE_DFLT.length()<=0)
      sa = new String[]{}; // empty 
    else
      sa = parseX(INTEGERSEQUENCER_ATTR_KEYVALUE_DFLT);
    keyValue = keyValueDefault = parseToSFIntArray(sa);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;
    
    attr = root.getAttribute(INTEGERSEQUENCER_ATTR_KEY_NAME);
    if (attr != null) {
      String[] sa = parseX(attr.getValue());
      key = parseToSFFloatArray(sa);
      if (attr.getValue().contains(","))  insertCommas     = true;
      if (attr.getValue().contains("\n") ||
          attr.getValue().contains("\r")) insertLineBreaks = true; // TODO not working, line breaks not being passed from JDOM
      if (insertCommas)                   insertLineBreaks = true; // workaround default, if commas were present then most likely lineBreaks also
    }
    
    attr = root.getAttribute(INTEGERSEQUENCER_ATTR_KEYVALUE_NAME);
    if (attr != null) {
      String[] sa = parseX(attr.getValue());
      keyValue = parseToSFIntArray(sa);
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
    if (INTEGERSEQUENCER_ATTR_KEY_REQD || !arraysIdenticalOrNull(key,keyDefault)) {
      sb.append(" ");
      sb.append(INTEGERSEQUENCER_ATTR_KEY_NAME);
      sb.append("='");
      sb.append(formatFloatArray(key, insertCommas, insertLineBreaks));
      sb.append("'");
     
    }
    if (INTEGERSEQUENCER_ATTR_KEYVALUE_REQD || !arraysIdenticalOrNull(keyValue,keyValueDefault)) {
      
      sb.append(" ");
      sb.append(INTEGERSEQUENCER_ATTR_KEYVALUE_NAME);
      sb.append("='");
      sb.append(formatIntArray(keyValue, insertCommas, insertLineBreaks));
      sb.append("'");
    }
    return sb.toString();
  }

  public String[][] getKeysAndValues()
  {
    if (key.length == 0)
        return new String[][]{{ }}; // default key, keyValue pair is empty
    
    int kLen = Math.min(key.length, keyValue.length); // TODO warning, will truncate data if one array is shorter
    
    String[][] saa = new String[kLen][2];
    
    for(int r=0; r<kLen; r++) {
      saa[r][0] = key[r].toString();
      saa[r][1] = keyValue[r].toString();
    }
    return saa;
  }
  
  public void setKeysAndValues(String[][] saa)
  {
    if(saa.length == 0) {
      key = new SFFloat[]{};
      keyValue = new SFInt32[]{};
      return;
    }
    
    key = new SFFloat[saa.length];
    keyValue = new SFInt32[saa.length];
    for (int r=0;r<saa.length; r++) {
      if(saa[r][0].length()<= 0)
        saa[r][0] = "0";
      key[r] = buildSFFloat(saa[r][0]);
      if(saa[r][1].length()<= 0)
        saa[r][1] = "0";
      keyValue[r] = buildSFInt(saa[r][1]);
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
}

