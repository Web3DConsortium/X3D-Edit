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
import org.web3d.x3d.types.X3DSoundChannelNode;

/**
 * CHANNELMERGER:
 * ChannelMerger unites different input channels into a single output channel.
 * 
 * @author Don Brutzman
 * @version $Id$
 */
public class CHANNELMERGER extends X3DSoundChannelNode
{
  public CHANNELMERGER() 
  {
      this.setTraceEventsSelectionAvailable(false);
      this.setTraceEventsTooltip("Trace ChannelMerger events on X3D browser console");
  }
  
  @Override
  public String getElementName()
  {
    return CHANNELMERGER_ELNAME;
  }
  
  @Override
  public void initialize()
  {
    channelCountMode      = channelCountModeDefault      = CHANNELMERGER_ATTR_CHANNELCOUNTMODE_DFLT;
    channelInterpretation = channelInterpretationDefault = CHANNELMERGER_ATTR_CHANNELINTERPRETATION_DFLT;
    description = descriptionDefault     = CHANNELMERGER_ATTR_DESCRIPTION_DFLT;
    enabled                              = Boolean.parseBoolean(CHANNELMERGER_ATTR_ENABLED_DFLT);
    gain       = gainDefault             = new SFFloat (CHANNELMERGER_ATTR_GAIN_DFLT,null,null); 
    
    setContent("\n\t\t<!-- TODO add child sound-processing input nodes here -->\n\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(CHANNELMERGER_ATTR_CHANNELCOUNTMODE_NAME);
    if (attr != null)
      setChannelCountMode(attr.getValue());
    attr = root.getAttribute(CHANNELMERGER_ATTR_CHANNELINTERPRETATION_NAME);
    if (attr != null)
      channelInterpretation = attr.getValue();
    attr = root.getAttribute(CHANNELMERGER_ATTR_DESCRIPTION_NAME);
    if (attr != null)
      description = attr.getValue();
    attr = root.getAttribute(CHANNELMERGER_ATTR_ENABLED_NAME);
    if (attr != null)
      enabled = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(CHANNELMERGER_ATTR_GAIN_NAME);
    if (attr != null)
      gain = new SFFloat(attr.getValue(), null, null);
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return CHANNELMERGERCustomizer.class;
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    
    if (CHANNELMERGER_ATTR_CHANNELCOUNTMODE_REQD || !channelCountMode.equals(channelCountModeDefault)) {
      sb.append(" ");
      sb.append(CHANNELMERGER_ATTR_CHANNELCOUNTMODE_NAME);
      sb.append("='");
      sb.append(getChannelCountMode());
      sb.append("'");
    }
    
    if (CHANNELMERGER_ATTR_CHANNELINTERPRETATION_REQD || !channelInterpretation.equals(channelInterpretationDefault)) {
      sb.append(" ");
      sb.append(CHANNELMERGER_ATTR_CHANNELINTERPRETATION_NAME);
      sb.append("='");
      sb.append(getChannelInterpretation());
      sb.append("'");
    }
    
    if (CHANNELMERGER_ATTR_DESCRIPTION_REQD || !description.equals(descriptionDefault)) {
      sb.append(" ");
      sb.append(CHANNELMERGER_ATTR_DESCRIPTION_NAME);
      sb.append("='");
      sb.append(description);
      sb.append("'");
    }

    if (CHANNELMERGER_ATTR_ENABLED_REQD || enabled != Boolean.parseBoolean(CHANNELMERGER_ATTR_ENABLED_DFLT)) {
      sb.append(" ");
      sb.append(CHANNELMERGER_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }
      
    if (CHANNELMERGER_ATTR_GAIN_REQD || !gain.equals(gainDefault)) {
      sb.append(" ");
      sb.append(CHANNELMERGER_ATTR_GAIN_NAME);
      sb.append("='");
      sb.append(getGain());
      sb.append("'");
    }
    
    return sb.toString();
  }

}
