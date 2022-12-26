/*
Copyright (c) 1995-2022 held by the author(s).  All rights reserved.
 
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
import org.web3d.x3d.types.X3DInterpolatorNode;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFFloat;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFInt32;
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

public class HAnimMotion extends X3DInterpolatorNode
{
  private String      channels, channelsDefault;
  private boolean[]   channelsEnabled, channelsEnabledDefault;
  private String      description, descriptionDefault;
  private boolean     enabled, enabledDefault;
  private SFInt32     endFrame, endFrameDefault;
  private SFFloat     frameDuration, frameDurationDefault; // SFTime
  private SFInt32     frameIncrement, frameIncrementDefault;
  private SFInt32     frameIndex, frameIndexDefault;
  private String      joints, jointsDefault;
  private SFInt32     loa, loaDefault;
  private boolean     loop, loopDefault;
  private String      name, nameDefault; // TODO add to spec?
  private SFInt32     startFrame, startFrameDefault; // SFTime
  private SFFloat[][] values, valuesDefault;
  private boolean     insertCommas, insertLineBreaks = false;
  
  public HAnimMotion()
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
    channels       = channelsDefault         = HANIMMOTION_ATTR_CHANNELS_DFLT;
    description    = descriptionDefault      = HANIMMOTION_ATTR_DESCRIPTION_DFLT;
    joints         = jointsDefault           = HANIMMOTION_ATTR_JOINTS_DFLT;
    name           = nameDefault             = HANIMMOTION_ATTR_NAME_DFLT; // TODO add to spec?
    
      endFrame     =   endFrameDefault       = new SFInt32  (HANIMMOTION_ATTR_ENDFRAME_DFLT,      0,    65535);
    startFrame     = startFrameDefault       = new SFInt32  (HANIMMOTION_ATTR_STARTFRAME_DFLT,    0,    65535);
    frameIncrement = frameIncrementDefault   = new SFInt32  (HANIMMOTION_ATTR_FRAMEINCREMENT_DFLT,0,    65535);
    frameIndex     = frameIndexDefault       = new SFInt32  (HANIMMOTION_ATTR_FRAMEINDEX_DFLT,    0,    65535);
    loa            = loaDefault              = new SFInt32  (HANIMMOTION_ATTR_LOA_DFLT,          -1,    4);
    
    frameDuration  = frameDurationDefault    = new SFFloat(HANIMMOTION_ATTR_FRAMEDURATION_DFLT,0.0f,null); // SFTime
    enabled        = enabledDefault          = Boolean.parseBoolean(HANIMMOTION_ATTR_ENABLED_DFLT);
    loop           = loopDefault             = Boolean.parseBoolean(HANIMMOTION_ATTR_LOOP_DFLT);
    
    String[] sa;
    
    if(HANIMMOTION_ATTR_VALUES_DFLT == null || HANIMMOTION_ATTR_VALUES_DFLT.length()<=0)
      sa = new String[]{}; // empty 
    else
      sa = parseX(HANIMMOTION_ATTR_VALUES_DFLT);
    values = valuesDefault = parseToSFFloatTable(sa,3); // TODO framewidth or SFFloatArray?
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;
    
    attr = root.getAttribute(HANIMMOTION_ATTR_CHANNELS_NAME);
    if (attr != null)
      channels = attr.getValue();
    
    attr = root.getAttribute(HANIMMOTION_ATTR_DESCRIPTION_NAME);
    if (attr != null)
      description = attr.getValue();
    
    attr = root.getAttribute(HANIMMOTION_ATTR_NAME_NAME);
    if (attr != null)
      name = attr.getValue();
    
    attr = root.getAttribute(HANIMMOTION_ATTR_JOINTS_NAME);
    if (attr != null)
      joints = attr.getValue();

    attr = root.getAttribute(HANIMMOTION_ATTR_ENABLED_NAME);
    if(attr != null)
      enabled = Boolean.parseBoolean(attr.getValue());

    attr = root.getAttribute(HANIMMOTION_ATTR_LOOP_NAME);
    if(attr != null)
      loop = Boolean.parseBoolean(attr.getValue());
  
    attr = root.getAttribute(HANIMMOTION_ATTR_CHANNELSENABLED_NAME);
    if (attr != null) {
         String[] sa = parseX(attr.getValue());
         channelsEnabled = parseToBooleanArray(sa);
         if (attr.getValue().contains(","))  insertCommas     = true;
         if (attr.getValue().contains("\n") ||
             attr.getValue().contains("\r")) insertLineBreaks = true; // TODO not working, line breaks not being passed from JDOM
         if (insertCommas)                   insertLineBreaks = true; // workaround default, if commas were present then most likely lineBreaks also
    }
    attr = root.getAttribute(HANIMMOTION_ATTR_ENDFRAME_NAME);
    if(attr != null)
      endFrame =  new SFInt32(attr.getValue(), 0, 65535);
    
    attr = root.getAttribute(HANIMMOTION_ATTR_FRAMEINCREMENT_NAME);
    if(attr != null)
      frameIncrement =  new SFInt32(attr.getValue(), 0, 65535);
    
    attr = root.getAttribute(HANIMMOTION_ATTR_FRAMEDURATION_NAME);
    if(attr != null)
      frameDuration =  new SFFloat(attr.getValue(), 0.0f, null);
    
    attr = root.getAttribute(HANIMMOTION_ATTR_FRAMEINDEX_NAME);
    if(attr != null)
      frameIndex =  new SFInt32(attr.getValue(), 0, 65535);
    
    attr = root.getAttribute(HANIMMOTION_ATTR_STARTFRAME_NAME);
    if(attr != null)
      startFrame =  new SFInt32(attr.getValue(), 0, 65535);
    
    attr = root.getAttribute(HANIMMOTION_ATTR_LOA_NAME);
    if(attr != null)
      loa =  new SFInt32(attr.getValue(), -1, 4);
    
    attr = root.getAttribute(HANIMMOTION_ATTR_VALUES_NAME);
    if (attr != null)
    {
      String[] sa = parseX(attr.getValue());
      int numberKeyValues = sa.length;
      int numberRows = endFrame.getValue() - startFrame.getValue() + 1; // note each value is already parsed
      if (numberRows < 1)
      {
          System.out.println("*** bad value for numberRows = endFrame - startFrame + 1 = " + 
                  endFrame.getValue() + " - " + startFrame.getValue() + " + 1 = " + numberRows + ", reeset to 1");
          numberRows = 1;
      }
      int numberColumns = numberKeyValues / numberRows;
      values = parseToSFFloatTable(sa,numberColumns);
      
         if (attr.getValue().contains(","))  insertCommas     = true;
         if (attr.getValue().contains("\n") ||
             attr.getValue().contains("\r")) insertLineBreaks = true; // TODO not working, line breaks not being passed from JDOM
         if (insertCommas)                     insertLineBreaks = true; // workaround default, if commas were present then most likely lineBreaks also
    }
  }
 
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    
//  private boolean[]   channelsEnabled, channelsEnabledDefault;
//  private boolean     enabled, enabledDefault;
//  private SFInt32     endFrame, endFrameDefault;
//  private SFFloat     frameDuration, frameDurationDefault; // SFTime
//  private SFInt32     frameIncrement, frameIncrementDefault;
//  private SFInt32     frameIndex, frameIndexDefault;
//  private String      joints, jointsDefault;
//  private SFInt32     loa, loaDefault;
//  private boolean     loop, loopDefault;
//  private SFInt32     startFrame, startFrameDefault; // SFTime
//  private SFFloat[][] values, valuesDefault;
//  private boolean     insertCommas, insertLineBreaks = false;
  
    if (HANIMMOTION_ATTR_CHANNELS_REQD || !channels.isBlank()) {
      sb.append(" ");
      sb.append(HANIMMOTION_ATTR_CHANNELS_NAME);
      sb.append("='");
      sb.append(channels);
      sb.append("'");
    }
    if (HANIMMOTION_ATTR_CHANNELSENABLED_REQD || !arraysIdenticalOrNull(channelsEnabled,channelsEnabledDefault)) {
      sb.append(" ");
      sb.append(HANIMMOTION_ATTR_CHANNELSENABLED_NAME);
      sb.append("='");
      sb.append(formatBooleanArray(channelsEnabled, insertCommas, insertLineBreaks));
      sb.append("'");
    }
    if (HANIMMOTION_ATTR_DESCRIPTION_REQD || !description.equals(descriptionDefault)) {
      sb.append(" ");
      sb.append(HANIMMOTION_ATTR_DESCRIPTION_NAME);
      sb.append("='");
      sb.append(description);
      sb.append("'");
    }
    if (HANIMMOTION_ATTR_ENABLED_REQD || (enabled != enabledDefault)) {
      sb.append(" ");
      sb.append(HANIMMOTION_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }
    if (HANIMMOTION_ATTR_ENDFRAME_REQD || (endFrame != endFrameDefault)) {
      sb.append(" ");
      sb.append(HANIMMOTION_ATTR_ENDFRAME_NAME);
      sb.append("='");
      sb.append(endFrame);
      sb.append("'");
    }
    if (HANIMMOTION_ATTR_FRAMEDURATION_REQD || (frameDuration != frameDurationDefault)) {
      sb.append(" ");
      sb.append(HANIMMOTION_ATTR_FRAMEDURATION_NAME);
      sb.append("='");
      sb.append(frameDuration);
      sb.append("'");
    }
    if (HANIMMOTION_ATTR_FRAMEINCREMENT_REQD || (frameIncrement != frameIncrementDefault)) {
      sb.append(" ");
      sb.append(HANIMMOTION_ATTR_FRAMEINCREMENT_NAME);
      sb.append("='");
      sb.append(frameIncrement);
      sb.append("'");
    }
    if (HANIMMOTION_ATTR_FRAMEINDEX_REQD || (frameIndex != frameIndexDefault)) {
      sb.append(" ");
      sb.append(HANIMMOTION_ATTR_FRAMEINDEX_NAME);
      sb.append("='");
      sb.append(frameIndex);
      sb.append("'");
    }
    if (HANIMMOTION_ATTR_JOINTS_REQD || (!joints.equals(jointsDefault))) {
      sb.append(" ");
      sb.append(HANIMMOTION_ATTR_JOINTS_NAME);
      sb.append("='");
      sb.append(joints);
      sb.append("'");
    }
    if (HANIMMOTION_ATTR_FRAMEINDEX_REQD || (frameIndex != frameIndexDefault)) {
      sb.append(" ");
      sb.append(HANIMMOTION_ATTR_FRAMEINDEX_NAME);
      sb.append("='");
      sb.append(frameIndex);
      sb.append("'");
    }
    if (HANIMMOTION_ATTR_LOA_REQD || (loa != loaDefault)) {
      sb.append(" ");
      sb.append(HANIMMOTION_ATTR_LOA_NAME);
      sb.append("='");
      sb.append(loa);
      sb.append("'");
    }
    if (HANIMMOTION_ATTR_LOOP_REQD || (loop != loopDefault)) {
      sb.append(" ");
      sb.append(HANIMMOTION_ATTR_LOOP_NAME);
      sb.append("='");
      sb.append(loop);
      sb.append("'");
    }
    if (HANIMMOTION_ATTR_NAME_REQD || (!name.equals(nameDefault))) {
      sb.append(" ");
      sb.append(HANIMMOTION_ATTR_NAME_NAME);
      sb.append("='");
      sb.append(name);
      sb.append("'");
    }
    if (HANIMMOTION_ATTR_STARTFRAME_REQD || (startFrame != startFrameDefault)) {
      sb.append(" ");
      sb.append(HANIMMOTION_ATTR_STARTFRAME_NAME);
      sb.append("='");
      sb.append(startFrame);
      sb.append("'");
    }
    if (HANIMMOTION_ATTR_VALUES_REQD || !arraysIdenticalOrNull(values,valuesDefault)) {
      sb.append(" ");
      sb.append(HANIMMOTION_ATTR_VALUES_NAME);
      sb.append("='");
      sb.append(formatFloatArray(values, insertCommas, insertLineBreaks));
      sb.append("'");
    }
    return sb.toString();
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
