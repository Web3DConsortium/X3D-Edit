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
 * PROTOTYPE_TimeDelaySensor.java
 * Created on 6 May 2009
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Don Brutzman, Mike Bailey
 * @version $Id$
 */
public class PROTOTYPE_TimeDelaySensor extends SceneGraphStructureNodeType
{
  public PROTOTYPE_TimeDelaySensor()
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
            "    <ExternProtoDeclare name='TimeDelaySensor'\n" +
            "        appinfo='TimeSensor functionality commences after delayInterval pause'\n" +
            "        url='\"../../Savage/Tools/Animation/TimeDelaySensorPrototype.x3d#TimeDelaySensor\"\n" +
            "             \"../../../Savage/Tools/Animation/TimeDelaySensorPrototype.x3d#TimeDelaySensor\"\n" +
            "             \"https://savage.nps.edu/Savage/Tools/Animation/TimeDelaySensorPrototype.x3d#TimeDelaySensor\"\n" +
            "             \"../../../Savage/Tools/Animation/TimeDelaySensorPrototype.wrl#TimeDelaySensor\"\n" +
            "             \"../../Savage/Tools/Animation/TimeDelaySensorPrototype.wrl#TimeDelaySensor\"\n" +
            "             \"https://savage.nps.edu/Savage/Tools/Animation/TimeDelaySensorPrototype.wrl#TimeDelaySensor\" '>\n" +
            "      <field name='description' type='SFString' accessType='inputOutput' appinfo='describe the purpose of this sensor'/>\n" +
            "      <field name='startTime' type='SFTime' accessType='inputOutput'/>\n" +
            "      <field name='enabled' type='SFBool' accessType='inputOutput'/>\n" +
            "      <field name='delayInterval' type='SFTime' accessType='inputOutput' appinfo='seconds'/>\n" +
            "      <field name='delayCompleteTime' type='SFTime' accessType='outputOnly'/>\n" +
            "      <field name='traceEnabled' type='SFBool' accessType='initializeOnly'/>\n" +
            "    </ExternProtoDeclare>\n" +
            "    <!-- Only one copy of a given ExternProtoDeclare is needed in a scene. Multiple ProtoInstance nodes can be created like the following: -->\n" +
            "    <ProtoInstance name='TimeDelaySensor' DEF='TimeDelaySensorExample'>\n" +
            "      <fieldValue name='description' value='double click to initiate time-delayed event'/>\n" +
            "      <fieldValue name='delayInterval' value='1'/>\n" +
            "    </ProtoInstance>\n" +
            "    <!-- Example use: https://savage.nps.edu/Savage/Tools/Animation/TimeDelaySensorExample.x3d -->\n" +
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
    return "TimeDelaySensor";
  }
}
