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
 * PROTOTYPE_CrossHair.java
 * Created on 16 June 2009
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Don Brutzman, Mike Bailey
 * @version $Id$
 */
public class PROTOTYPE_CrossHair extends SceneGraphStructureNodeType
{
  public PROTOTYPE_CrossHair()
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
            "    <ExternProtoDeclare name='CrossHair' appinfo='CrossHair prototype provides a heads-up display (HUD) crosshair at the view center, which is useful for assessing NavigationInfo lookAt point'\n" +
            "      url='\"../../Savage/Tools/HeadsUpDisplays/CrossHairPrototype.x3d#CrossHair\"\n" +
            "           \"../../../Savage/Tools/HeadsUpDisplays/CrossHairPrototype.x3d#CrossHair\"\n" +
            "           \"https://savage.nps.edu/Savage/Tools/HeadsUpDisplays/CrossHairPrototype.x3d#CrossHair\"\n" +
            "           \"../../Savage/Tools/HeadsUpDisplays/CrossHairPrototype.wrl#CrossHair\"\n" +
            "           \"../../../Savage/Tools/HeadsUpDisplays/CrossHairPrototype.wrl#CrossHair\"\n" +
            "           \"https://savage.nps.edu/Savage/Tools/HeadsUpDisplays/CrossHairPrototype.wrl#CrossHair\"'>\n" +
            "      <field accessType='initializeOnly' appinfo='whether CrossHair prototype is enabled or not' name='enabled' type='SFBool'/>\n" +
            "      <field accessType='inputOnly' appinfo='control whether enabled/disabled' name='set_enabled' type='SFBool'/>\n" +
            "      <field accessType='inputOutput' appinfo='color of CrossHair marker' name='markerColor' type='SFColor'/>\n" +
            "      <field accessType='inputOutput' appinfo='size of CrossHair in meters' name='scale' type='SFVec3f'/>\n" +
            "      <field accessType='inputOutput' appinfo='distance in front of HUD viewpoint' name='positionOffsetFromCamera' type='SFVec3f'/>\n" +
            "    </ExternProtoDeclare>\n" +
            "    <!-- Only one copy of a given ExternProtoDeclare is needed in a scene. Multiple ProtoInstance nodes can be created like the following: -->\n" +
            "    <ProtoInstance DEF='CrossHairExample' name='CrossHair'>\n" +
            "      <fieldValue name='enabled' value='true'/>\n" +
            "      <fieldValue name='markerColor' value='1 0.5 0'/>\n" +
            "      <fieldValue name='scale' value='1 1 1'/>\n" +
            "      <fieldValue name='positionOffsetFromCamera' value='0 0 -6'/>\n" +
            "    </ProtoInstance>\n" +
            "    <!-- Example use: https://savage.nps.edu/Savage/Tools/Animation/CrossHairExample.x3d -->\n" +
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
    return "CrossHair";
  }
}
