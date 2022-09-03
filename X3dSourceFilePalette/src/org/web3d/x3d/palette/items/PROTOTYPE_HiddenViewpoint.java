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
 * PROTOTYPE_HiddenViewpoint.java
 * Created on 6 May 2009
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Don Brutzman, Mike Bailey
 * @version $Id$
 */
public class PROTOTYPE_HiddenViewpoint extends SceneGraphStructureNodeType
{
  public PROTOTYPE_HiddenViewpoint()
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
            "    <ExternProtoDeclare name='HiddenViewpoint'\n" +
            "        appinfo='Hidden viewpoint becomes active (binds) upon pointer selection to reveal an interesting view with an optionally label'\n" +
            "        url='\"../../Savage/Tools/Animation/HiddenViewpointPrototype.x3d#HiddenViewpoint\"\n" +
            "             \"../../../Savage/Tools/Animation/HiddenViewpointPrototype.x3d#HiddenViewpoint\"\n" +
            "             \"https://savage.nps.edu/Savage/Tools/Animation/HiddenViewpointPrototype.x3d#HiddenViewpoint\"\n" +
            "             \"../../../Savage/Tools/Animation/HiddenViewpointPrototype.wrl#HiddenViewpoint\"\n" +
            "             \"../../Savage/Tools/Animation/HiddenViewpointPrototype.wrl#HiddenViewpoint\"\n" +
            "             \"https://savage.nps.edu/Savage/Tools/Animation/HiddenViewpointPrototype.wrl#HiddenViewpoint\" '>\n" +
            "      <field name='position' type='SFVec3f' accessType='inputOutput'/>\n" +
            "      <field name='rotation' type='SFRotation' accessType='inputOutput'/>\n" +
            "      <field name='sensorRadius' type='SFFloat' accessType='initializeOnly'/>\n" +
            "      <field name='activate' type='SFBool' accessType='inputOnly'/>\n" +
            "      <field name='label' type='MFString' accessType='inputOutput'/>\n" +
            "      <field name='labelOffset' type='SFVec3f' accessType='inputOutput'/>\n" +
            "      <field name='labelFontSize' type='SFFloat' accessType='initializeOnly'/>\n" +
            "      <field name='labelColor' type='SFColor' accessType='inputOutput'/>\n" +
            "    </ExternProtoDeclare>\n" +
            "    <!-- Only one copy of a given ExternProtoDeclare is needed in a scene. Multiple ProtoInstance nodes can be created like the following: -->\n" +
            "    <ProtoInstance name='HiddenViewpoint' DEF='HiddenViewpointExample'>\n" +
            "      <fieldValue name='position' value='0 0 0'/>\n" +
            "      <fieldValue name='rotation' value='0 1 0 2.5'/>\n" +
            "      <fieldValue name='sensorRadius' value='1'/>\n" +
            "      <fieldValue name='label' value='\"HiddenViewpoint\" \"test works!\"'/>\n" +
            "      <fieldValue name='labelOffset' value='1 -1.25 0'/>\n" +
            "      <fieldValue name='labelFontSize' value='0.4'/>\n" +
            "      <fieldValue name='labelColor' value='1 0 0'/>\n" +
            "    </ProtoInstance>\n" +
            "    <!-- Example use: https://savage.nps.edu/Savage/Tools/Animation/HiddenViewpointExample.x3d -->\n" +
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
    return "HiddenViewpoint";
  }
}
