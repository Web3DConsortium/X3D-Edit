/*
Copyright (c) 1995-2023 held by the author(s).  All rights reserved.
 
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
import org.web3d.x3d.types.X3DPrimitiveTypes.SFDouble;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFFloat;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFInt32;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * HANIMMOTION.java
 * Created on Sep 13, 2007, 3:05 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */

public class HANIMMOTION extends X3DInterpolatorNode
{
  private String      channels, channelsDefault;
  private boolean[]   channelsEnabled, channelsEnabledDefault;
  private String      description, descriptionDefault;
  private boolean     enabled, enabledDefault;
  private SFInt32     endFrame, endFrameDefault;
  private SFInt32     frameCount, frameCountDefault;
  private SFDouble    frameDuration, frameDurationDefault; // SFTime
  private SFInt32     frameIncrement, frameIncrementDefault;
  private SFInt32     frameIndex, frameIndexDefault;
  private String      joints, jointsDefault;
  private SFInt32     loa, loaDefault;
  private boolean     loop, loopDefault;
  private String      name, nameDefault;     // X3D 4.0
  private String      skeletalConfiguration; // X3D 4.1
  private SFInt32     startFrame, startFrameDefault;
  private SFFloat[][] values, valuesDefault;
  private boolean     insertCommas, insertLineBreaks = false;
  
  private int         numberRows    = 0;
  private int         numberColumns = 0;
  
  public HANIMMOTION()
  {
      this.setTraceEventsSelectionAvailable(true);
      this.setTraceEventsTooltip("Trace " + getElementName() + " events on X3D browser console");
  }

  @Override
  public final String getElementName()
  {
    return HANIMMOTION_ELNAME;
  }
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return HANIMMOTIONCustomizer.class;
  }

  @Override
  public void initialize()
  {
    setChannels(channelsDefault       = HANIMMOTION_ATTR_CHANNELS_DFLT);
    
    String[] sa;
    if(HANIMMOTION_ATTR_CHANNELSENABLED_DFLT == null || HANIMMOTION_ATTR_CHANNELSENABLED_DFLT.isBlank())
      sa = new String[]{}; // empty 
    else
      sa = parseX(HANIMMOTION_ATTR_CHANNELSENABLED_DFLT);
     setChannelsEnabled(channelsEnabledDefault       = parseToBooleanArray(sa));
    
    setDescription(descriptionDefault = HANIMMOTION_ATTR_DESCRIPTION_DFLT);
    setJoints(jointsDefault           = HANIMMOTION_ATTR_JOINTS_DFLT);
    setName(nameDefault               = HANIMMOTION_ATTR_NAME_DFLT); // TODO add to spec?

    setEndFrame(endFrameDefault       = new SFInt32  (HANIMMOTION_ATTR_ENDFRAME_DFLT,      0,    65535));
    setStartFrame(startFrameDefault   = new SFInt32  (HANIMMOTION_ATTR_STARTFRAME_DFLT,    0,    65535));
    setFrameCount(frameCountDefault   = new SFInt32  (HANIMMOTION_ATTR_FRAMECOUNT_DFLT,0,    65535));
    setFrameIncrement(frameIncrementDefault= new SFInt32  (HANIMMOTION_ATTR_FRAMEINCREMENT_DFLT,0,    65535));
    setFrameIndex(frameIndexDefault   = new SFInt32  (HANIMMOTION_ATTR_FRAMEINDEX_DFLT,    0,    65535));
    setLoa(loaDefault                 = new SFInt32  (HANIMMOTION_ATTR_LOA_DFLT,          -1,    4));

    setFrameDuration(frameDurationDefault = new SFDouble(HANIMMOTION_ATTR_FRAMEDURATION_DFLT,0.0,null)); // SFTime
    setEnabled(enabledDefault         = Boolean.parseBoolean(HANIMMOTION_ATTR_ENABLED_DFLT));
    setLoop(loopDefault               = Boolean.parseBoolean(HANIMMOTION_ATTR_LOOP_DFLT));
    
    skeletalConfiguration = HANIMMOTION_ATTR_SKELETALCONFIGURATION_DFLT;
        
    if(HANIMMOTION_ATTR_VALUES_DFLT == null || HANIMMOTION_ATTR_VALUES_DFLT.isBlank())
      sa = new String[]{}; // empty 
    else
      sa = parseX(HANIMMOTION_ATTR_VALUES_DFLT);
    setValues(valuesDefault = parseToSFFloatTable(sa,getNumberColumns()));
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;
    
    attr = root.getAttribute(HANIMMOTION_ATTR_CHANNELS_NAME);
    if (attr != null)
        setChannels(attr.getValue());
  
    attr = root.getAttribute(HANIMMOTION_ATTR_CHANNELSENABLED_NAME);
    if (attr != null) {
         String[] sa = parseX(attr.getValue());
            setChannelsEnabled(parseToBooleanArray(sa));
         if (attr.getValue().contains(","))  setInsertCommas(true);
         if (attr.getValue().contains("\n") ||
             attr.getValue().contains("\r")) setInsertLineBreaks(true); // TODO not working, line breaks not being passed from JDOM
         if (isInsertCommas())                   setInsertLineBreaks(true); // workaround default, if commas were present then most likely lineBreaks also
    }
    
    attr = root.getAttribute(HANIMMOTION_ATTR_DESCRIPTION_NAME);
    if (attr != null)
        setDescription(attr.getValue());
    
    attr = root.getAttribute(HANIMMOTION_ATTR_NAME_NAME);
    if (attr != null)
        setName(attr.getValue());
    
    attr = root.getAttribute(HANIMMOTION_ATTR_JOINTS_NAME);
    if (attr != null)
        setJoints(attr.getValue());

    attr = root.getAttribute(HANIMMOTION_ATTR_ENABLED_NAME);
    if(attr != null)
        setEnabled(Boolean.parseBoolean(attr.getValue()));

    attr = root.getAttribute(HANIMMOTION_ATTR_LOOP_NAME);
    if(attr != null)
        setLoop(Boolean.parseBoolean(attr.getValue()));
    attr = root.getAttribute(HANIMMOTION_ATTR_ENDFRAME_NAME);
    if(attr != null)
        setEndFrame(new SFInt32(attr.getValue(), 0, 65535));
    
    attr = root.getAttribute(HANIMMOTION_ATTR_FRAMECOUNT_NAME);
    if(attr != null)
        setFrameCount(new SFInt32(attr.getValue(), 0, 65535));
    
    attr = root.getAttribute(HANIMMOTION_ATTR_FRAMEINCREMENT_NAME);
    if(attr != null)
        setFrameIncrement(new SFInt32(attr.getValue(), 0, 65535));
    
    attr = root.getAttribute(HANIMMOTION_ATTR_FRAMEDURATION_NAME);
    if(attr != null)
        setFrameDuration(new SFDouble(attr.getValue(), 0.0, null));
    
    attr = root.getAttribute(HANIMMOTION_ATTR_FRAMEINDEX_NAME);
    if(attr != null)
        setFrameIndex(new SFInt32(attr.getValue(), 0, 65535));
    
    attr = root.getAttribute(HANIMMOTION_ATTR_STARTFRAME_NAME);
    if(attr != null)
        setStartFrame(new SFInt32(attr.getValue(), 0, 65535));
    
    attr = root.getAttribute(HANIMMOTION_ATTR_LOA_NAME);
    if(attr != null)
        setLoa(new SFInt32(attr.getValue(), -1, 4));
    
    attr = root.getAttribute(HANIMMOTION_ATTR_SKELETALCONFIGURATION_NAME);
    if (attr != null)
      skeletalConfiguration = attr.getValue();
    
    attr = root.getAttribute(HANIMMOTION_ATTR_VALUES_NAME);
    if (attr != null)
    {
      String[] sa = parseX(attr.getValue());
      int numberValues = sa.length;
            setNumberRows(getEndFrame().getValue() - getStartFrame().getValue() + 1); // note each value is already parsed
      if (  getNumberRows() < 1)
      {
          System.out.println("*** bad value for numberRows = endFrame - startFrame + 1 = " + 
                  getEndFrame().getValue() + " - " + getStartFrame().getValue() + " + 1 = " + getNumberRows() + ", reeset to 1");
                setNumberRows(1);
      }
            setNumberColumns(numberValues / getNumberRows());
            setValues(parseToSFFloatTable(sa, getNumberColumns()));
      
         if (attr.getValue().contains(","))  setInsertCommas(true);
         if (attr.getValue().contains("\n") ||
             attr.getValue().contains("\r")) setInsertLineBreaks(true); // TODO not working, line breaks not being passed from JDOM
         if (isInsertCommas())                     setInsertLineBreaks(true); // workaround default, if commas were present then most likely lineBreaks also
    }
  }
 
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
  
    if (HANIMMOTION_ATTR_CHANNELS_REQD || !channels.equals(channelsDefault)) {
      sb.append(" ");
      sb.append(HANIMMOTION_ATTR_CHANNELS_NAME);
      sb.append("='");
      sb.append(getChannels());
      sb.append("'");
    }
    if (HANIMMOTION_ATTR_CHANNELSENABLED_REQD || !arraysIdenticalOrNull(channelsEnabled,channelsEnabledDefault)) {
      sb.append(" ");
      sb.append(HANIMMOTION_ATTR_CHANNELSENABLED_NAME);
      sb.append("='");
      sb.append(formatBooleanArray(getChannelsEnabled(), isInsertCommas(), isInsertLineBreaks()));
      sb.append("'");
    }
    if (HANIMMOTION_ATTR_DESCRIPTION_REQD || !description.equals(descriptionDefault)) {
      sb.append(" ");
      sb.append(HANIMMOTION_ATTR_DESCRIPTION_NAME);
      sb.append("='");
      sb.append(getDescription());
      sb.append("'");
    }
    if (HANIMMOTION_ATTR_ENABLED_REQD || (isEnabled() != enabledDefault)) {
      sb.append(" ");
      sb.append(HANIMMOTION_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(isEnabled());
      sb.append("'");
    }
    if (HANIMMOTION_ATTR_ENDFRAME_REQD || (getEndFrame().getValue() != endFrameDefault.getValue())) {
      sb.append(" ");
      sb.append(HANIMMOTION_ATTR_ENDFRAME_NAME);
      sb.append("='");
      sb.append(getEndFrame());
      sb.append("'");
    }
    if (HANIMMOTION_ATTR_FRAMECOUNT_REQD || (getFrameCount().getValue() != frameCountDefault.getValue())) {
      sb.append(" ");
      sb.append(HANIMMOTION_ATTR_FRAMECOUNT_NAME);
      sb.append("='");
      sb.append(getFrameCount());
      sb.append("'");
    }
    if (HANIMMOTION_ATTR_FRAMEDURATION_REQD || (getFrameDuration().getValue() != frameDurationDefault.getValue())) {
      sb.append(" ");
      sb.append(HANIMMOTION_ATTR_FRAMEDURATION_NAME);
      sb.append("='");
      sb.append(getFrameDuration());
      sb.append("'");
    }
    if (HANIMMOTION_ATTR_FRAMEINCREMENT_REQD || (getFrameIncrement().getValue() != frameIncrementDefault.getValue())) {
      sb.append(" ");
      sb.append(HANIMMOTION_ATTR_FRAMEINCREMENT_NAME);
      sb.append("='");
      sb.append(getFrameIncrement());
      sb.append("'");
    }
    if (HANIMMOTION_ATTR_JOINTS_REQD || (!joints.equals(jointsDefault))) {
      sb.append(" ");
      sb.append(HANIMMOTION_ATTR_JOINTS_NAME);
      sb.append("='");
      sb.append(getJoints());
      sb.append("'");
    }
    if (HANIMMOTION_ATTR_FRAMEINDEX_REQD || (getFrameIndex().getValue() != frameIndexDefault.getValue())) {
      sb.append(" ");
      sb.append(HANIMMOTION_ATTR_FRAMEINDEX_NAME);
      sb.append("='");
      sb.append(getFrameIndex());
      sb.append("'");
    }
    if (HANIMMOTION_ATTR_LOA_REQD || (getLoa().getValue() != loaDefault.getValue())) {
      sb.append(" ");
      sb.append(HANIMMOTION_ATTR_LOA_NAME);
      sb.append("='");
      sb.append(getLoa());
      sb.append("'");
    }
    if (HANIMMOTION_ATTR_LOOP_REQD || (isLoop() != loopDefault)) {
      sb.append(" ");
      sb.append(HANIMMOTION_ATTR_LOOP_NAME);
      sb.append("='");
      sb.append(isLoop());
      sb.append("'");
    }
    if (HANIMMOTION_ATTR_NAME_REQD || (!name.equals(nameDefault))) {
      sb.append(" ");
      sb.append(HANIMMOTION_ATTR_NAME_NAME);
      sb.append("='");
      sb.append(getName());
      sb.append("'");
    }
    if (true || HANIMMOTION_ATTR_SKELETALCONFIGURATION_REQD || !skeletalConfiguration.equals(HANIMMOTION_ATTR_SKELETALCONFIGURATION_DFLT) ) {
      sb.append(" ");
      sb.append(HANIMMOTION_ATTR_SKELETALCONFIGURATION_NAME);
      sb.append("='");
      sb.append(skeletalConfiguration);
      sb.append("'");
    }
    if (HANIMMOTION_ATTR_STARTFRAME_REQD || (getStartFrame().getValue() != startFrameDefault.getValue())) {
      sb.append(" ");
      sb.append(HANIMMOTION_ATTR_STARTFRAME_NAME);
      sb.append("='");
      sb.append(getStartFrame());
      sb.append("'");
    }
    if (HANIMMOTION_ATTR_VALUES_REQD || !arraysIdenticalOrNull(values,valuesDefault)) {
      sb.append(" ");
      sb.append(HANIMMOTION_ATTR_VALUES_NAME);
      sb.append("='");
      sb.append(formatFloatArray(getValues(), isInsertCommas(), isInsertLineBreaks()));
      sb.append("'");
    }
    return sb.toString();
  }

    /**
     * @param insertLineBreaks the insertLineBreak values to set
     */
    public void setInsertLineBreaks(boolean insertLineBreaks) {
        this.insertLineBreaks = insertLineBreaks;
    }

    /**
     * @return the channels
     */
    public String getChannels()
    {
        return channels;
    }

    /**
     * @param channels the channels to set
     */
    public void setChannels(String channels)
    {
        this.channels = channels;
    }

    /**
     * @return the channelsEnabled
     */
    public boolean[] getChannelsEnabled()
    {
        return channelsEnabled;
    }

    /**
     * @param channelsEnabled the channelsEnabled to set
     */
    public void setChannelsEnabled(boolean[] channelsEnabled)
    {
        this.channelsEnabled = channelsEnabled;
    }

    /**
     * @return the description
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * @return the enabled
     */
    public boolean isEnabled()
    {
        return enabled;
    }

    /**
     * @param enabled the enabled to set
     */
    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    /**
     * @return the endFrame
     */
    public SFInt32 getEndFrame()
    {
        return endFrame;
    }

    /**
     * @param endFrame the endFrame to set
     */
    public void setEndFrame(SFInt32 endFrame)
    {
        this.endFrame = endFrame;
    }

    /**
     * @return the frameDuration
     */
    public SFDouble getFrameDuration()
    {
        return frameDuration;
    }

    /**
     * @return the frameCount
     */
    public SFInt32 getFrameCount()
    {
        return frameCount;
    }

    /**
     * @param frameCount the frameCount to set
     */
    public void setFrameCount(SFInt32 frameCount)
    {
        this.frameCount = frameCount;
    }

    /**
     * @param frameDuration the frameDuration to set
     */
    public void setFrameDuration(SFDouble frameDuration)
    {
        this.frameDuration = frameDuration;
    }

    /**
     * @return the frameIncrement
     */
    public SFInt32 getFrameIncrement()
    {
        return frameIncrement;
    }

    /**
     * @param frameIncrement the frameIncrement to set
     */
    public void setFrameIncrement(SFInt32 frameIncrement)
    {
        this.frameIncrement = frameIncrement;
    }

    /**
     * @return the frameIndex
     */
    public SFInt32 getFrameIndex()
    {
        return frameIndex;
    }

    /**
     * @param frameIndex the frameIndex to set
     */
    public void setFrameIndex(SFInt32 frameIndex)
    {
        this.frameIndex = frameIndex;
    }

    /**
     * @return the joints
     */
    public String getJoints()
    {
        return joints;
    }

    /**
     * @param joints the joints to set
     */
    public void setJoints(String joints)
    {
        this.joints = joints;
    }

    /**
     * @return the Level of Articulation (LOA)
     */
    public SFInt32 getLoa()
    {
        return loa;
    }

    /**
     * @param loa the Level of Articulation (LOA) to set
     */
    public void setLoa(SFInt32 loa)
    {
        this.loa = loa;
    }

    /**
     * @return the loop
     */
    public boolean isLoop()
    {
        return loop;
    }

    /**
     * @param loop the loop to set
     */
    public void setLoop(boolean loop)
    {
        this.loop = loop;
    }

    /**
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name)
    {
        this.name = name;
    }
    
    /**
     * @return the startFrame
     */
    public SFInt32 getStartFrame()
    {
        return startFrame;
    }

    /**
     * @param startFrame the startFrame to set
     */
    public void setStartFrame(SFInt32 startFrame)
    {
        this.startFrame = startFrame;
    }

    /**
     * @return the values
     */
    public SFFloat[][] getValues()
    {
        return values;
    }

    /**
     * @param values the values to set
     */
    public void setValues(SFFloat[][] values)
    {
        this.values = values;
    }

  public String[][] getValuesString()
  {
    if (values.length == 0)
        return new String[][]{{ }}; // default value array is empty
    
    String[][] saa = new String[numberRows][numberColumns];
    
    for(int r = 0; r < numberRows; r++)
    {
      for(int c =0 ; c < numberColumns; c++)
      {
        saa[r][c] = values[r][c].toString();
      }
    }
    return saa;
  }
  
  public void setValuesString(String[][] saa)
  {
    if(saa.length == 0) {
      values = new SFFloat[0][0];
      return;
    }
    
    values= new SFFloat[numberRows][numberColumns];
    for (int r = 0; r  <numberRows; r++) {
      for(int c = 0; c < numberColumns; c++) {
        values[r][c] = buildSFFloat(saa[r][c]);
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
     * @return the numberRows
     */
    public int getNumberRows()
    {
        return numberRows;
    }

    /**
     * @param numberRows the numberRows to set
     */
    public void setNumberRows(int numberRows)
    {
        this.numberRows = numberRows;
    }

    /**
     * @return the numberColumns
     */
    public int getNumberColumns()
    {
        return numberColumns;
    }

    /**
     * @param numberColumns the numberColumns to set
     */
    public void setNumberColumns(int numberColumns)
    {
        this.numberColumns = numberColumns;
    }

    /**
     * @return the skeletalConfiguration
     */
    public String getSkeletalConfiguration()
    {
        return skeletalConfiguration;
    }

    /**
     * @param newSkeletalConfiguration the skeletalConfiguration to set
     */
    public void setSkeletalConfiguration(String newSkeletalConfiguration)
    {
        this.skeletalConfiguration = newSkeletalConfiguration;
    }
}
