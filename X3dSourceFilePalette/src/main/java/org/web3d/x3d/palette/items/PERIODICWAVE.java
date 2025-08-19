/*
Copyright (c) 1995-2025 held by the author(s).  All rights reserved.
 
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
      (https://www.nps.edu and https://MovesInstitute.nps.edu)
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
import org.web3d.x3d.types.X3DPrimitiveTypes.SFFloat;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.parseX;
import org.web3d.x3d.types.X3DSoundNode;

/**
 * PERIODICWAVE:
 * PeriodicWave defines a periodic waveform that can be used to shape the output of a parent Oscillator node.
 *
 * @author Don Brutzman
 * @version $Id$
 */
public class PERIODICWAVE extends X3DSoundNode
{
    private SFFloat[] optionsReal, optionsRealDefault; // MFFloat
    private SFFloat[] optionsImag, optionsImagDefault; // MFFloat
    private String    type, typeDefault;
    
  public PERIODICWAVE() 
  {
      this.setTraceEventsSelectionAvailable(false);
      this.setTraceEventsTooltip("Trace MicrophoneSource events on X3D browser console");
  }
  
  @Override
  public String getElementName()
  {
    return PERIODICWAVE_ELNAME;
  }
  
  @Override
  public void initialize()
  {
    String[] sa;
    
    description = descriptionDefault       = PERIODICWAVE_ATTR_DESCRIPTION_DFLT;
    enabled                                = Boolean.parseBoolean(PERIODICWAVE_ATTR_ENABLED_DFLT);
    
    if(PERIODICWAVE_ATTR_OPTIONSREAL_DFLT == null || PERIODICWAVE_ATTR_OPTIONSREAL_DFLT.length()<=0)
      sa = new String[]{}; // empty array
    else
      sa = parseX(PERIODICWAVE_ATTR_OPTIONSREAL_DFLT);
    optionsReal    =  optionsRealDefault           = parseToSFFloatArray(sa);
    
    if(PERIODICWAVE_ATTR_OPTIONSIMAG_DFLT == null || PERIODICWAVE_ATTR_OPTIONSIMAG_DFLT.length()<=0)
      sa = new String[]{}; // empty array
    else
      sa = parseX(PERIODICWAVE_ATTR_OPTIONSIMAG_DFLT);
    optionsImag    =  optionsImagDefault           = parseToSFFloatArray(sa);
    
        setType(typeDefault = PERIODICWAVE_ATTR_TYPE_DFLT);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    String[] sa;
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(PERIODICWAVE_ATTR_DESCRIPTION_NAME);
    if (attr != null)
      description = attr.getValue();
    attr = root.getAttribute(PERIODICWAVE_ATTR_ENABLED_NAME);
    if (attr != null)
      enabled = Boolean.parseBoolean(attr.getValue());
    
    attr = root.getAttribute(PERIODICWAVE_ATTR_OPTIONSREAL_NAME);
    if (attr != null) {
      sa = parseX(attr.getValue());
      optionsReal = parseToSFFloatArray(sa);
    }
    
    attr = root.getAttribute(PERIODICWAVE_ATTR_OPTIONSIMAG_NAME);
    if (attr != null) {
      sa = parseX(attr.getValue());
      optionsImag = parseToSFFloatArray(sa);
    }
    attr = root.getAttribute(PERIODICWAVE_ATTR_TYPE_NAME);
    if (attr != null)
        setType(attr.getValue());
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return PERIODICWAVECustomizer.class;
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    
    if (PERIODICWAVE_ATTR_DESCRIPTION_REQD || !description.equals(descriptionDefault)) {
      sb.append(" ");
      sb.append(PERIODICWAVE_ATTR_DESCRIPTION_NAME);
      sb.append("='");
      sb.append(description);
      sb.append("'");
    }

    if (PERIODICWAVE_ATTR_ENABLED_REQD || enabled != Boolean.parseBoolean(PERIODICWAVE_ATTR_ENABLED_DFLT)) {
      sb.append(" ");
      sb.append(PERIODICWAVE_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }
      
    if ((PERIODICWAVE_ATTR_OPTIONSIMAG_REQD || !arraysIdenticalOrNull(optionsImag,optionsImagDefault)) && optionsImag.length > 0) {
      sb.append(" ");
      sb.append(PERIODICWAVE_ATTR_OPTIONSIMAG_NAME);
      sb.append("='");
      sb.append(formatFloatArray(optionsImag));
      sb.append("'");
    }
      
    if ((PERIODICWAVE_ATTR_OPTIONSREAL_REQD || !arraysIdenticalOrNull(optionsReal,optionsRealDefault)) && optionsReal.length > 0) {
      sb.append(" ");
      sb.append(PERIODICWAVE_ATTR_OPTIONSREAL_NAME);
      sb.append("='");
      sb.append(formatFloatArray(optionsReal));
      sb.append("'");
    }
    
    if (PERIODICWAVE_ATTR_TYPE_REQD || !type.equals(typeDefault)) {
      sb.append(" ");
      sb.append(PERIODICWAVE_ATTR_TYPE_NAME);
      sb.append("='");
      sb.append(getType());
      sb.append("'");
    }
    
    return sb.toString();
  }

    /**
     * @return the optionsReal
     */
    public String getOptionsReal() {
        String values = new String();
        for (SFFloat nextValue : optionsReal) {
            values += " " + nextValue.toString();
        }
        return values.trim();
    }

    /**
     * @param newOptionsReal the optionsReal to set
     */
    public void setOptionsReal(String newOptionsReal) {
        String[] sa;
        if ((newOptionsReal != null) && !newOptionsReal.isBlank()) {
          sa = parseX(newOptionsReal);
        }
        else sa = new String[]{}; // empty array
        optionsReal = parseToSFFloatArray(sa);
    }

    /**
     * @return the optionsImag
     */
    public String getOptionsImag() {
        String values = new String();
        for (SFFloat nextValue : optionsImag) {
            values += " " + nextValue.toString();
        }
        return values.trim();
    }

    /**
     * @param newOptionsImag the optionsImag to set
     */
    public void setOptionsImag(String newOptionsImag) {
        String[] sa;
        if ((newOptionsImag != null) && !newOptionsImag.isBlank()) {
          sa = parseX(newOptionsImag);
        }
        else sa = new String[]{}; // empty array
        optionsImag = parseToSFFloatArray(sa);
    }

    /**
     * @return the type
     */
    public String getType() {
        return type;
    }

    /**
     * @param newType the type to set
     */
    public void setType(String newType) {
        type = newType;
    }

}
