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

import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * SIGNALPDU.java
 * Created on 20 May 2008
 * 
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 * 
 * @author Mike Bailey
 * @version $Id$
 */
public class SIGNALPDU extends CommonPdu
{ 
  private SFInt32[] data, dataDefault;
  private SFInt32   dataLength, dataLengthDefault;
  private SFInt32   encodingScheme, encodingSchemeDefault;
  private SFInt32   sampleRate, sampleRateDefault;
  private SFInt32   samples, samplesDefault;
  private SFInt32   tdlType, tdlTypeDefault;
  
  public SIGNALPDU()
  {
  }

  @Override
  public String getElementName()
  {
    return SIGNALPDU_ELNAME;
  }
  
  @Override
  public void initialize()
  {
    super.initialize();
    data = dataDefault                     = handleIntArray(this,SIGNALPDU_ATTR_DATA_DFLT);
    dataLength = dataLengthDefault         = new SFInt32(SIGNALPDU_ATTR_DATALENGTH_DFLT,0,65535);
    encodingScheme = encodingSchemeDefault = new SFInt32(SIGNALPDU_ATTR_ENCODINGSCHEME_DFLT,0,65535);
    sampleRate = sampleRateDefault         = new SFInt32(SIGNALPDU_ATTR_SAMPLERATE_DFLT,0,65535);
    samples = samplesDefault               = new SFInt32(SIGNALPDU_ATTR_SAMPLES_DFLT,0,65535);
    tdlType = tdlTypeDefault               = new SFInt32(SIGNALPDU_ATTR_TDLTYPE_DFLT,0,65535);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(SIGNALPDU_ATTR_DATA_NAME);
    if (attr != null)
      data = handleIntArray(this, attr.getValue());
    attr = root.getAttribute(SIGNALPDU_ATTR_DATALENGTH_NAME);
    if (attr != null)
      dataLength = new SFInt32(attr.getValue());
    attr = root.getAttribute(SIGNALPDU_ATTR_ENCODINGSCHEME_NAME);
    if (attr != null)
      encodingScheme = new SFInt32(attr.getValue());
    attr = root.getAttribute(SIGNALPDU_ATTR_SAMPLERATE_NAME);
    if (attr != null)
      sampleRate = new SFInt32(attr.getValue());
    attr = root.getAttribute(SIGNALPDU_ATTR_SAMPLES_NAME);
    if (attr != null)
      samples = new SFInt32(attr.getValue());
    attr = root.getAttribute(SIGNALPDU_ATTR_TDLTYPE_NAME);
    if (attr != null)
      tdlType = new SFInt32(attr.getValue());
  }

  @Override
  public String createAttributes()
  {
    StringBuffer sb = new StringBuffer();
    
    sb.append(super.createAttributes());  // get pdu common attributes
        
    if(SIGNALPDU_ATTR_DATA_REQD || !arraysIdenticalOrNull(data,dataDefault)) {
      sb.append(" ");
      sb.append(SIGNALPDU_ATTR_DATA_REQD);
      sb.append("='");
      sb.append(formatIntArray(data));
      sb.append("'");
    }
    if(SIGNALPDU_ATTR_DATALENGTH_REQD || !dataLength.equals(dataLengthDefault)) {
      sb.append(" ");
      sb.append(SIGNALPDU_ATTR_DATALENGTH_NAME);
      sb.append("='");
      sb.append(dataLength);
      sb.append("'");      
    }
    if(SIGNALPDU_ATTR_ENCODINGSCHEME_REQD || !encodingScheme.equals(encodingSchemeDefault)) {
      sb.append(" ");
      sb.append(SIGNALPDU_ATTR_ENCODINGSCHEME_NAME);
      sb.append("='");
      sb.append(encodingScheme);
      sb.append("'");      
    }
    if(SIGNALPDU_ATTR_SAMPLERATE_REQD || !sampleRate.equals(sampleRateDefault)) {
      sb.append(" ");
      sb.append(SIGNALPDU_ATTR_SAMPLERATE_NAME);
      sb.append("='");
      sb.append(sampleRate);
      sb.append("'");      
    }
    if(SIGNALPDU_ATTR_SAMPLES_REQD || !samples.equals(samplesDefault)) {
      sb.append(" ");
      sb.append(SIGNALPDU_ATTR_SAMPLES_NAME);
      sb.append("='");
      sb.append(samples);
      sb.append("'");      
    }
    if(SIGNALPDU_ATTR_TDLTYPE_REQD || ! tdlType.equals(tdlTypeDefault)) {
      sb.append(" ");
      sb.append(SIGNALPDU_ATTR_TDLTYPE_NAME);
      sb.append("='");
      sb.append(tdlType);
      sb.append("'");      
    }    
    return sb.toString();
  }

  public String[] getData()
  {
    String[][] saa = makeStringArray(new SFInt32[][]{data});
    return saa[0];
  }
  public String getDataLength()    {return dataLength.toString();}
  public String getEncodingScheme(){return encodingScheme.toString();}
  public String getSampleRate()    {return sampleRate.toString();}
  public String getSamples()       {return samples.toString();}
  public String getTdlType()       {return tdlType.toString();}

  public void setData(String[] s)        {data = parseToSFIntArray(s);}
  public void setDataLength(String s)    {dataLength     = new SFInt32(s,0,65535);}
  public void setEncodingScheme(String s){encodingScheme = new SFInt32(s,0,65535);}
  public void setSampleRate(String s)    {sampleRate     = new SFInt32(s,0,65535);}
  public void setSamples(String s)       {samples        = new SFInt32(s,0,65535);}
  public void setTdlType(String s)       {tdlType        = new SFInt32(s,0,65535);}
  
  private static SFInt32[] handleIntArray(BaseX3DElement el,String str)
  {
    String[] sa;
    if(str == null || str.length()<=0)
      sa = new String[]{}; // empty 
    else
      sa = parseX(str);
    return el.parseToSFIntArray(sa);
  }

}