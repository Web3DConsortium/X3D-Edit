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
 * PROTOTYPE_RelativeProximitySensor.java
 * Created on 18 October 2009
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Don Brutzman, Mike Bailey
 * @version $Id$
 */
public class PROTOTYPE_RelativeProximitySensor extends SceneGraphStructureNodeType
{
  public PROTOTYPE_RelativeProximitySensor()
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
            "    <ExternProtoDeclare name='RelativeProximitySensor'\n" +
            "        appinfo='TimeSensor functionality commences after delayInterval pause'\n" +
            "        url='\"../../Savage/Tools/Animation/RelativeProximitySensorPrototype.x3d#RelativeProximitySensor\"\n" +
            "             \"../../../Savage/Tools/Animation/RelativeProximitySensorPrototype.x3d#RelativeProximitySensor\"\n" +
            "             \"https://savage.nps.edu/Savage/Tools/Animation/RelativeProximitySensorPrototype.x3d#RelativeProximitySensor\"\n" +
            "             \"../../../Savage/Tools/Animation/RelativeProximitySensorPrototype.wrl#RelativeProximitySensor\"\n" +
            "             \"../../Savage/Tools/Animation/RelativeProximitySensorPrototype.wrl#RelativeProximitySensor\"\n" +
            "             \"https://savage.nps.edu/Savage/Tools/Animation/RelativeProximitySensorPrototype.wrl#RelativeProximitySensor\" '>\n" +
            "      <field accessType='inputOutput' appinfo='describe the purpose of this sensor' name='description' type='SFString'/>\n" +
            "      <field accessType='initializeOnly' appinfo='where is the primary object' name='locationPrimary' type='SFVec3f'/>\n" +
            "      <field accessType='inputOnly' appinfo='update location of the primary object' name='set_locationPrimary' type='SFVec3f'/>\n" +
            "      <field accessType='initializeOnly' appinfo='where is the secondary object' name='locationSecondary' type='SFVec3f'/>\n" +
            "      <field accessType='inputOnly' appinfo='update location of the secondary object' name='set_locationSecondary' type='SFVec3f'/>\n" +
            "      <field accessType='initializeOnly' appinfo='distance for detecting object-to-object collision' name='proximityRangeThreshold' type='SFFloat'/>\n" +
            "      <field accessType='inputOnly' appinfo='change threshold distance for detecting collision' name='set_proximityRangeThreshold' type='SFFloat'/>\n" +
            "      <field accessType='outputOnly' appinfo='is object-to-object distance less than proximityRangeThreshold?' name='isInRange' type='SFBool'/>\n" +
            "      <field accessType='outputOnly' appinfo='when did object-to-object range meet detection criteria?' name='isInRangeTime' type='SFTime'/>\n" +
            "      <field accessType='initializeOnly' appinfo='whether sensor is active' name='enabled' type='SFBool'/>\n" +
            "      <field accessType='inputOnly' name='set_enabled' type='SFBool'/>\n" +
            "    </ExternProtoDeclare>\n" +
            "    <!-- Only one copy of a given ExternProtoDeclare is needed in a scene. Multiple ProtoInstance nodes can be created like the following: -->\n" +
            "    <ProtoInstance name='RelativeProximitySensor' DEF='RelativeProximitySensorExample'>\n" +
            "      <fieldValue name='description' value='test case'/>\n" +
            "      <fieldValue name='enabled' value='true'/>\n" +
            "      <fieldValue name='locationPrimary' value='-10 0 0'/>\n" +
            "      <fieldValue name='locationSecondary' value='10 0 0'/>\n" +
            "    </ProtoInstance>\n" +
            "    <!-- Example use: https://savage.nps.edu/Savage/Tools/Animation/RelativeProximitySensorExample.x3d -->\n" +
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
    return "RelativeProximitySensor";
  }
}
