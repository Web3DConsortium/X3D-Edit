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
import org.web3d.x3d.types.X3DPrimitiveTypes.SFDouble;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFFloat;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.parse3;
import static org.web3d.x3d.types.X3DSchemaData4.parse4;
import org.web3d.x3d.types.X3DSoundProcessingNode;

/**
 * LISTENERPOINTSOURCE:
 * ListenerPointSource represents the position and orientation of a person 
 * listening to virtual sound in the audio scene, and provides single or multiple 
 * sound channels as output. Multiple ListenerPointSource nodes can be active for 
 * sound processing.
 *
 * @author Don Brutzman
 * @version $Id$
 */
public class LISTENERPOINTSOURCE extends X3DSoundProcessingNode // and X3DTimeDependentNode
{
    private boolean dopplerEnabled, dopplerEnabledDefault;
    private SFFloat interauralDistance, interauralDistanceDefault;
    private SFFloat position0, position0Default;
    private SFFloat position1, position1Default;
    private SFFloat position2, position2Default;
    private SFFloat orientation0, orientation0Default;
    private SFFloat orientation1, orientation1Default;
    private SFFloat orientation2, orientation2Default;
    private SFFloat orientation3, orientation3Default;
    private boolean trackCurrentView, trackCurrentViewDefault;
    
  public LISTENERPOINTSOURCE() 
  {
      this.setTraceEventsSelectionAvailable(false);
      this.setTraceEventsTooltip("Trace MicrophoneSource events on X3D browser console");
  }
  
  @Override
  public String getElementName()
  {
    return LISTENERPOINTSOURCE_ELNAME;
  }
  
  @Override
  public void initialize()
  {
    description        = descriptionDefault         = LISTENERPOINTSOURCE_ATTR_DESCRIPTION_DFLT;
    enabled                                         = Boolean.parseBoolean(LISTENERPOINTSOURCE_ATTR_ENABLED_DFLT);
    gain               = gainDefault                = new SFFloat (LISTENERPOINTSOURCE_ATTR_GAIN_DFLT,null,null); 
    startTime          =  startTimeDefault          = new SFDouble(LISTENERPOINTSOURCE_ATTR_STARTTIME_DFLT,null,null);
    stopTime           =   stopTimeDefault          = new SFDouble(LISTENERPOINTSOURCE_ATTR_STOPTIME_DFLT,null,null);  
    pauseTime          =  pauseTimeDefault          = new SFDouble(LISTENERPOINTSOURCE_ATTR_PAUSETIME_DFLT,null,null);
    resumeTime         = resumeTimeDefault          = new SFDouble(LISTENERPOINTSOURCE_ATTR_RESUMETIME_DFLT,null,null);
    dopplerEnabled     = dopplerEnabledDefault      = Boolean.getBoolean(LISTENERPOINTSOURCE_ATTR_DOPPLERENABLED_DFLT);
    interauralDistance = interauralDistanceDefault  = new SFFloat (LISTENERPOINTSOURCE_ATTR_INTERAURALDISTANCE_DFLT, 0.0f, null);

    String[] sa = parse3(LISTENERPOINTSOURCE_ATTR_POSITION_DFLT);
    position0 = position0Default = new SFFloat(sa[0],null,null);
    position1 = position1Default = new SFFloat(sa[1],null,null);
    position2 = position2Default = new SFFloat(sa[2],null,null);

    sa = parse4(LISTENERPOINTSOURCE_ATTR_ORIENTATION_DFLT);
    orientation0 = orientation0Default = new SFFloat(sa[0],null,null);
    orientation1 = orientation1Default = new SFFloat(sa[1],null,null);
    orientation2 = orientation2Default = new SFFloat(sa[2],null,null);
    orientation3 = orientation3Default = new SFFloat(sa[2],null,null);
    
    trackCurrentView  = trackCurrentViewDefault = Boolean.getBoolean(LISTENERPOINTSOURCE_ATTR_TRACKCURRENTVIEW_DFLT);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;
    String[] sa;

    attr = root.getAttribute(LISTENERPOINTSOURCE_ATTR_DESCRIPTION_NAME);
    if (attr != null)
      description = attr.getValue();
    attr = root.getAttribute(LISTENERPOINTSOURCE_ATTR_ENABLED_NAME);
    if (attr != null)
      enabled = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(LISTENERPOINTSOURCE_ATTR_GAIN_NAME);
    if (attr != null)
      gain = new SFFloat(attr.getValue(), null, null);
    
    attr = root.getAttribute(LISTENERPOINTSOURCE_ATTR_STARTTIME_NAME);
    if (attr != null)
      startTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(LISTENERPOINTSOURCE_ATTR_STOPTIME_NAME);
    if (attr != null)
      stopTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(LISTENERPOINTSOURCE_ATTR_PAUSETIME_NAME);
    if (attr != null)
      pauseTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(LISTENERPOINTSOURCE_ATTR_RESUMETIME_NAME);
    if (attr != null)
      resumeTime = new SFDouble(attr.getValue(), null, null);
    attr = root.getAttribute(LISTENERPOINTSOURCE_ATTR_DOPPLERENABLED_NAME);
    if (attr != null)
      dopplerEnabled = Boolean.getBoolean(attr.getValue());
    attr = root.getAttribute(LISTENERPOINTSOURCE_ATTR_INTERAURALDISTANCE_NAME);
    if (attr != null)
      interauralDistance = new SFFloat(attr.getValue(), 0.0f, null);
    attr = root.getAttribute(LISTENERPOINTSOURCE_ATTR_TRACKCURRENTVIEW_NAME);
    if (attr != null)
      trackCurrentView = Boolean.getBoolean(attr.getValue());

    attr = root.getAttribute(LISTENERPOINTSOURCE_ATTR_POSITION_NAME);
    if (attr != null)
    {
        sa = parse3(attr.getValue());
        position0 = new SFFloat(sa[0],null,null);
        position1 = new SFFloat(sa[1],null,null);
        position2 = new SFFloat(sa[2],null,null);
    }

    attr = root.getAttribute(LISTENERPOINTSOURCE_ATTR_ORIENTATION_NAME);
    if (attr != null)
    {
        sa = parse4(attr.getValue());
        orientation0 = new SFFloat(sa[0],null,null);
        orientation1 = new SFFloat(sa[1],null,null);
        orientation2 = new SFFloat(sa[2],null,null);
        orientation3 = new SFFloat(sa[3],null,null);
    }
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return LISTENERPOINTSOURCECustomizer.class;
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    
    if (LISTENERPOINTSOURCE_ATTR_DESCRIPTION_REQD || !description.equals(descriptionDefault)) {
      sb.append(" ");
      sb.append(LISTENERPOINTSOURCE_ATTR_DESCRIPTION_NAME);
      sb.append("='");
      sb.append(description);
      sb.append("'");
    }
    
    if (LISTENERPOINTSOURCE_ATTR_DOPPLERENABLED_REQD || (dopplerEnabled != dopplerEnabledDefault)) {
      sb.append(" ");
      sb.append(LISTENERPOINTSOURCE_ATTR_DOPPLERENABLED_NAME);
      sb.append("='");
      sb.append(dopplerEnabled);
      sb.append("'");
    }

    if (LISTENERPOINTSOURCE_ATTR_ENABLED_REQD || enabled != Boolean.parseBoolean(LISTENERPOINTSOURCE_ATTR_ENABLED_DFLT)) {
      sb.append(" ");
      sb.append(LISTENERPOINTSOURCE_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }
      
    if (LISTENERPOINTSOURCE_ATTR_GAIN_REQD || !gain.equals(gainDefault)) {
      sb.append(" ");
      sb.append(LISTENERPOINTSOURCE_ATTR_GAIN_NAME);
      sb.append("='");
      sb.append(getGain());
      sb.append("'");
    }
    
    if (LISTENERPOINTSOURCE_ATTR_INTERAURALDISTANCE_REQD || !interauralDistance.equals(interauralDistanceDefault)) {
      sb.append(" ");
      sb.append(LISTENERPOINTSOURCE_ATTR_INTERAURALDISTANCE_NAME);
      sb.append("='");
      sb.append(interauralDistance);
      sb.append("'");
    }
    
    if (LISTENERPOINTSOURCE_ATTR_ORIENTATION_REQD ||
            (!orientation0.equals(orientation0Default)) ||
            (!orientation1.equals(orientation1Default)) ||
            (!orientation2.equals(orientation2Default)) ||
            (!orientation3.equals(orientation3Default)))
    {
      sb.append(" ");
      sb.append(LISTENERPOINTSOURCE_ATTR_ORIENTATION_NAME);
      sb.append("='");
      sb.append(orientation0);
      sb.append(" ");
      sb.append(orientation1);
      sb.append(" ");
      sb.append(orientation2);
      sb.append(" ");
      sb.append(orientation3);
      sb.append("'");
    }

    if (LISTENERPOINTSOURCE_ATTR_PAUSETIME_REQD || !pauseTime.equals(pauseTimeDefault)) {
      sb.append(" ");
      sb.append(LISTENERPOINTSOURCE_ATTR_PAUSETIME_NAME);
      sb.append("='");
      sb.append(pauseTime);
      sb.append("'");
    }
    
    if (LISTENERPOINTSOURCE_ATTR_POSITION_REQD ||
            (!position0.equals(position0Default)) ||
            (!position1.equals(position1Default)) ||
            (!position2.equals(position2Default)))
    {
      sb.append(" ");
      sb.append(LISTENERPOINTSOURCE_ATTR_POSITION_NAME);
      sb.append("='");
      sb.append(position0);
      sb.append(" ");
      sb.append(position1);
      sb.append(" ");
      sb.append(position2);
      sb.append("'");
    }
    
    if (LISTENERPOINTSOURCE_ATTR_RESUMETIME_REQD || !resumeTime.equals(resumeTimeDefault)) {
      sb.append(" ");
      sb.append(LISTENERPOINTSOURCE_ATTR_RESUMETIME_NAME);
      sb.append("='");
      sb.append(resumeTime);
      sb.append("'");
    }

    if (LISTENERPOINTSOURCE_ATTR_STARTTIME_REQD || !startTime.equals(startTimeDefault)) {
      sb.append(" ");
      sb.append(LISTENERPOINTSOURCE_ATTR_STARTTIME_NAME);
      sb.append("='");
      sb.append(startTime);
      sb.append("'");
    }
    if (LISTENERPOINTSOURCE_ATTR_STOPTIME_REQD || !stopTime.equals(stopTimeDefault)) {
      sb.append(" ");
      sb.append(LISTENERPOINTSOURCE_ATTR_STOPTIME_NAME);
      sb.append("='");
      sb.append(stopTime);
      sb.append("'");
    }
    
    if (LISTENERPOINTSOURCE_ATTR_TRACKCURRENTVIEW_REQD || (trackCurrentView != trackCurrentViewDefault)) {
      sb.append(" ");
      sb.append(LISTENERPOINTSOURCE_ATTR_TRACKCURRENTVIEW_NAME);
      sb.append("='");
      sb.append(trackCurrentView);
      sb.append("'");
    }
    return sb.toString();
  }

    /**
     * @return the dopplerEnabled
     */
    public String isDopplerEnabled() {
        return Boolean.toString(dopplerEnabled);
    }

    /** 
     * @param newDopplerEnabled the dopplerEnabled to set
     */
    public void setDopplerEnabled(boolean newDopplerEnabled) {
        dopplerEnabled = newDopplerEnabled;
    }

    /**
     * @return the interauralDistance
     */
    public String getInterauralDistance() {
        return interauralDistance.toString();
    }

    /** 
     * @param newInterauralDistance the interauralDistance to set
     */
    public void setInterauralDistance(String newInterauralDistance) {
        interauralDistance = new SFFloat(newInterauralDistance, 0.0f, null);
    }

    /**
     * @return the trackCurrentView
     */
    public String isTrackCurrentView() {
        return Boolean.toString(trackCurrentView);
    }

    /** 
     * @param newTrackCurrentView the trackCurrentView to set
     */
    public void setTrackCurrentView(boolean newTrackCurrentView) {
        trackCurrentView = newTrackCurrentView;
    }

    /**
     * @return the value
     */
    public String getPosition0() {
        return position0.toString();
    }

    /**
     * @param newValue the value to set
     */
    public void setPosition0(String newValue) {
        position0 = new SFFloat(newValue, null, null);
    }

    /**
     * @return the value
     */
    public String getPosition1() {
        return position1.toString();
    }

    /**
     * @param newValue the value to set
     */
    public void setPosition1(String newValue) {
        position1 = new SFFloat(newValue, null, null);
    }

    /**
     * @return the value
     */
    public String getPosition2() {
        return position2.toString();
    }

    /**
     * @param newValue the value to set
     */
    public void setPosition2(String newValue) {
        position2 = new SFFloat(newValue, null, null);
    }

    /**
     * @return the value
     */
    public String getOrientation0() {
        return orientation0.toString();
    }

    /**
     * @param newValue the value to set
     */
    public void setOrientation0(String newValue) {
        orientation0 = new SFFloat(newValue, null, null);
    }
    /**
     * @return the value
     */
    public String getOrientation1() {
        return orientation1.toString();
    }

    /**
     * @param newValue the value to set
     */
    public void setOrientation1(String newValue) {
        orientation1 = new SFFloat(newValue, null, null);
    }
    /**
     * @return the value
     */
    public String getOrientation2() {
        return orientation2.toString();
    }

    /**
     * @param newValue the value to set
     */
    public void setOrientation2(String newValue) {
        orientation2 = new SFFloat(newValue, null, null);
    }
    /**
     * @return the value
     */
    public String getOrientation3() {
        return orientation3.toString();
    }

    /**
     * @param newValue the value to set
     */
    public void setOrientation3(String newValue) {
        orientation3 = new SFFloat(newValue, null, null);
    }
}
