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
 * PROTOTYPE_TimeSensorEaseInEaseOut.java
 * Created on 18 October 2009
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Don Brutzman, Mike Bailey
 * @version $Id$
 */
public class PROTOTYPE_TimeSensorEaseInEaseOut extends SceneGraphStructureNodeType
{
  public PROTOTYPE_TimeSensorEaseInEaseOut()
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
            "    <!-- ==================== -->\n" +
            "    <ExternProtoDeclare name='TimeSensorEaseInEaseOut'\n" +
            "        appinfo='TimeSensor functionality commences after delayInterval pause'\n" +
            "        url='\"../../Savage/Tools/Animation/TimeSensorEaseInEaseOutPrototype.x3d#TimeSensorEaseInEaseOut\"\n" +
            "             \"../../../Savage/Tools/Animation/TimeSensorEaseInEaseOutPrototype.x3d#TimeSensorEaseInEaseOut\"\n" +
            "             \"https://savage.nps.edu/Savage/Tools/Animation/TimeSensorEaseInEaseOutPrototype.x3d#TimeSensorEaseInEaseOut\"\n" +
            "             \"../../../Savage/Tools/Animation/TimeSensorEaseInEaseOutPrototype.wrl#TimeSensorEaseInEaseOut\"\n" +
            "             \"../../Savage/Tools/Animation/TimeSensorEaseInEaseOutPrototype.wrl#TimeSensorEaseInEaseOut\"\n" +
            "             \"https://savage.nps.edu/Savage/Tools/Animation/TimeSensorEaseInEaseOutPrototype.wrl#TimeSensorEaseInEaseOut\" '>\n" +
            "      <field accessType='inputOutput' appinfo='whether sensor is active' name='enabled' type='SFBool'/>\n" +
            "      <field accessType='inputOutput' appinfo='loop duration in seconds' name='cycleInterval' type='SFTime'/>\n" +
            "      <field accessType='outputOnly' appinfo='fraction_changed sends values in range [0,1] showing time progress in the current cycle' name='fraction_changed' type='SFFloat'/>\n" +
            "      <field accessType='inputOutput' appinfo='repeat indefinitely when loop=true, repeat only once when loop=false' name='loop' type='SFBool'/>\n" +
            "      <field accessType='outputOnly' appinfo='isActive true/false events are sent when TimeSensor starts/stops running' name='isActive' type='SFBool'/>\n" +
            "      <field accessType='inputOutput' appinfo='when current time exceeds startTime, isActive becomes true and sensor becomes active' name='startTime' type='SFTime'/>\n" +
            "      <field accessType='inputOutput' appinfo='when current time exceeds stopTime, isActive becomes false and sensor becomes inactive' name='stopTime' type='SFTime'/>\n" +
            "      <field accessType='outputOnly' appinfo='cycleTime sends a time event at startTime, and also at the beginning of each new cycle' name='cycleTime' type='SFTime'/>\n" +
            "      <field accessType='outputOnly' appinfo='absolute time (since January 1, 1970) for each event loop' name='time' type='SFTime'/>\n" +
            "      <field accessType='inputOutput' appinfo='when current time exceeds pauseTime, isPaused becomes false and sensor becomes paused' name='pauseTime' type='SFTime'/>\n" +
            "      <field accessType='outputOnly' appinfo='isPaused true/false events are sent when TimeSensor is paused/resumed' name='isPaused' type='SFBool'/>\n" +
            "      <field accessType='outputOnly' appinfo='elapsed time since TimeSensor activated/running, cumulative in seconds, not counting any paused time' name='elapsedTime' type='SFTime'/>\n" +
            "      <field accessType='inputOutput' appinfo='when current time exceeds resumeTime, isPaused becomes false and sensor resumes running' name='resumeTime' type='SFTime'/>\n" +
            "    </ExternProtoDeclare>\n" +
            "    <!-- Only one copy of a given ExternProtoDeclare is needed in a scene. Multiple ProtoInstance nodes can be created like the following: -->\n" +
            "    <ProtoInstance name='TimeSensorEaseInEaseOut' DEF='TimeSensorEaseInEaseOutExample'>\n" +
            "      <fieldValue name='cycleInterval' value='3'/>\n" +
            "    </ProtoInstance>\n" +
            "    <!-- Example use: https://savage.nps.edu/Savage/Tools/Animation/TimeSensorEaseInEaseOutExample.x3d -->\n" +
            "    <!-- ==================== -->\n";
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
    return "TimeSensorEaseInEaseOut";
  }
}
