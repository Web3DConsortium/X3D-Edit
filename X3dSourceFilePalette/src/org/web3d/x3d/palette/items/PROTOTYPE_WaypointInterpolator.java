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

import org.web3d.x3d.types.SceneGraphStructureNodeType;

/**
 * PROTOTYPE_WaypointInterpolator.java
 * Created on 1 July 2009
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Don Brutzman, Mike Bailey
 * @version $Id$
 */
public class PROTOTYPE_WaypointInterpolator extends SceneGraphStructureNodeType
{
  public PROTOTYPE_WaypointInterpolator()
  {
  }

  @Override
  public void initialize()
  {
  }

  @Override
  public String createBody()
  {
    return "\n" +
            "     <!-- ==================== -->\n" +
            "     <ExternProtoDeclare appinfo='Reads waypoints and legSpeeds/legDurations/defaultSpeed to provide a customizable position/orientation interpolator.' name='WaypointInterpolator'"  +
            "          url=' \"../../../Savage/Tools/Animation/WaypointInterpolatorPrototype.x3d#WaypointInterpolator\"\n" +
            "                \"https://savage.nps.edu/Savage/Tools/Animation/WaypointInterpolatorPrototype.x3d#WaypointInterpolator\"\n" +
            "                \"../../../Savage/Tools/Animation/WaypointInterpolatorPrototype.wrl#WaypointInterpolator\"\n" +
            "                \"https://savage.nps.edu/Savage/Tools/Animation/WaypointInterpolatorPrototype.wrl#WaypointInterpolator\"'>\n" +
            "          <!-- Priority of use: legSpeeds (m/sec), legDurations (seconds), defaultSpeed (m/sec) -->\n" +
            "          <field accessType='initializeOnly' appinfo='Short description of what is animated by this WaypointInterpolator.' name='description' type='SFString'/>\n" +
            "          <field accessType='initializeOnly' appinfo='Waypoints being traversed with interpolation of intermediate positions and orientations.' name='waypoints' type='MFVec3f'/>\n" +
            "          <field accessType='inputOnly' appinfo='Add another single waypoint to array of waypoints recalculate interpolator values.' name='add_waypoint' type='SFVec3f'/>\n" +
            "          <field accessType='inputOnly' appinfo='Replace all waypoints recalculate interpolator values.' name='set_waypoints' type='MFVec3f'/>\n" +
            "          <field accessType='initializeOnly' appinfo='Whether to pitch child geometry (such as a vehicle) up or down to match vertical slope' name='pitchUpDownForVerticalWaypoints' type='SFBool'/>\n" +
            "          <field accessType='initializeOnly' appinfo='Units m/sec. If used array lengths for legSpeeds and legDurations must be one less than number of waypoints.' name='legSpeeds' type='MFFloat'/>\n" +
            "          <field accessType='initializeOnly' appinfo='Units in seconds. If used array lengths for legSpeeds and legDurations must be one less than number of waypoints.' name='legDurations' type='MFTime'/>\n" +
            "          <field accessType='initializeOnly' appinfo='Units m/sec.' name='defaultSpeed' type='SFFloat'/>\n" +
            "          <field accessType='initializeOnly' appinfo='turningRate (degrees/second) also determines standoff distance prior to waypoint where turn commences. If 0 turns are instantaneous.' name='turningRate' type='SFFloat'/>\n" +
            "          <field accessType='outputOnly' appinfo='Output calculation summing all leg durations, useful for setting TimeSensor cycleInterval. Units in seconds.' name='totalDuration' type='SFTime'/>\n" +
            "          <!-- interpolation fields -->\n" +
            "          <field accessType='inputOnly' appinfo='exposed PositionInterpolator and OrientationInterpolator setting' name='set_fraction' type='SFFloat'/>\n" +
            "          <field accessType='outputOnly' appinfo='exposed PositionInterpolator setting' name='position_changed' type='SFVec3f'/>\n" +
            "          <field accessType='outputOnly' appinfo='exposed OrientationInterpolator setting' name='orientation_changed' type='SFRotation'/>\n" +
            "          <!-- display-related fields -->\n" +
            "          <field accessType='inputOutput' appinfo='default color for non-active line segments' name='lineColor' type='SFColor'/>\n" +
            "          <field accessType='inputOutput' appinfo='highlightSegmentColors must contain two color values for each endpoint of the highlight segment.' name='highlightSegmentColor' type='SFColor'/>\n" +
            "          <field accessType='inputOutput' appinfo='1.0 is completely transparent, 0.0 is completely opaque.' name='transparency' type='SFFloat'/>\n" +
            "          <field accessType='initializeOnly' appinfo='allowed values: none; waypoints (produce labels at each waypoint); or interpolation (produce single moving label at interpolator time course speed location)' name='labelDisplayMode' type='SFString'/>\n" +
            "          <field accessType='initializeOnly' appinfo='allowed values: altitude depth (negate Y value) none' name='heightLabel' type='SFString'/>\n" +
            "          <field accessType='initializeOnly' appinfo='heightLabel relative location' name='labelOffset' type='SFVec3f'/>\n" +
            "          <field accessType='initializeOnly' appinfo='heightLabel text size' name='labelFontSize' type='SFFloat'/>\n" +
            "          <field accessType='initializeOnly' appinfo='heightLabel text color' name='labelColor' type='SFColor'/>\n" +
            "          <field accessType='initializeOnly' appinfo='enable console output to trace script computations and prototype progress' name='traceEnabled' type='SFBool'/>\n" +
            "          <field accessType='initializeOnly' appinfo='Output the number of waypoints totalDistance and totalDuration to console upon initialization' name='outputInitializationComputations' type='SFBool'/>\n" +
            "          <field accessType='inputOutput' appinfo='default color for vertical drop-line segments' name='verticalDropLineColor' type='SFColor'/>\n" +
            "          <field accessType='inputOutput' appinfo='1.0 is completely transparent 0.0 is completely opaque.' name='verticalDropLineTransparency' type='SFFloat'/>\n" +
            "     </ExternProtoDeclare>\n" +
            "     <!-- Only one copy of a given ExternProtoDeclare is needed in a scene. Multiple ProtoInstance nodes can be created like the following -->\n" +
            "     <ProtoInstance DEF='WaypointInterpolatorExample' name='WaypointInterpolator'>\n" +
            "          <fieldValue name='description' value='TrackBuilder'/>\n" +
            "          <!-- Priority of use: legSpeeds (m/sec), legDurations (seconds), defaultSpeed (m/sec) -->\n" +
            "          <fieldValue name='waypoints' value='-5 0 0 5 2 0 5 5 -10 4.9 2 -10 -5 0 -10 -5 0 0 -5.5 0 1.5 -7 0.5 2 -8 0 0 -5 0 0'/>\n" +
            "          <fieldValue name='pitchUpDownForVerticalWaypoints' value='false'/>\n" +
            "          <fieldValue name='legDurations' value='1 2 3 1 2 3 1 2 3'/>\n" +
            "          <fieldValue name='legSpeeds' value='2 4 2 2 4 1 1 1 1'/>\n" +
            "          <fieldValue name='defaultSpeed' value='5'/>\n" +
            "          <fieldValue name='turningRate' value='90'/>\n" +
            "          <fieldValue name='lineColor' value='1 0 0'/>\n" +
            "          <fieldValue name='highlightSegmentColor' value='0.2 0.2 1'/>\n" +
            "          <fieldValue name='transparency' value='0'/>\n" +
            "          <fieldValue name='labelDisplayMode' value='interpolation'/>\n" +
            "          <fieldValue name='heightLabel' value='altitude'/>\n" +
            "          <fieldValue name='labelOffset' value='0 1.2 0'/>\n" +
            "          <fieldValue name='labelFontSize' value='0.5'/>\n" +
            "          <fieldValue name='labelColor' value='0.3 0.9 0.3'/>\n" +
            "          <fieldValue name='traceEnabled' value='false'/>\n" +
            "          <fieldValue name='outputInitializationComputations' value='true'/>\n" +
            "     </ProtoInstance>\n" +
            "     <!-- Example use: https://savage.nps.edu/Savage/Tools/Animation/WaypointInterpolatorExample.x3d -->\n" +
            "     <!-- ==================== -->";
  }
             
  @SuppressWarnings("unchecked")
  @Override
  /**
   * @return null, no customizer, author can simply edit ProtoInstance
   */
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return null;
  }

  @Override
  public String getElementName()
  {
    return "WaypointInterpolator";
  }
}
