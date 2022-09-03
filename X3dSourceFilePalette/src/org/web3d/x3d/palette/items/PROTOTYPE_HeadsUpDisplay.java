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
 * PROTOTYPE_HeadsUpDisplay.java
 * Created on 23 June 2009
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Don Brutzman, Mike Bailey
 * @version $Id$
 */
public class PROTOTYPE_HeadsUpDisplay extends SceneGraphStructureNodeType
{
  public PROTOTYPE_HeadsUpDisplay()
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
            "    <ExternProtoDeclare name='HeadsUpDisplay'\n" +
            "        appinfo='Heads-up display (HUD) keeps child geometry aligned on screen in a consistent location'\n" +
            "        url='\"../../../X3dForWebAuthors/Chapter14Prototypes/HeadsUpDisplayPrototype.x3d#HeadsUpDisplay\"\n" +
            "             \"http://X3dGraphics.com/examples/X3dForWebAuthors/Chapter14Prototypes/HeadsUpDisplayPrototype.x3d#HeadsUpDisplay\"\n" +
            "             \"../../../X3dForWebAuthors/Chapter14Prototypes/HeadsUpDisplayPrototype.wrl#HeadsUpDisplay\"\n" +
            "             \"http://X3dGraphics.com/examples/X3dForWebAuthors/Chapter14Prototypes/HeadsUpDisplayPrototype.wrl#HeadsUpDisplay\"'>\n" +
            "      <field accessType='inputOutput' appinfo='X3D content positioned at HUD offset' name='children' type='MFNode'/>\n" +
            "      <field accessType='inputOutput' appinfo='offset position for HUD relative to current view location, default 0 0 -5' name='screenOffset' type='SFVec3f'/>\n" +
            "      <field accessType='outputOnly' appinfo='HUD position update (in world coordinates) relative to original location' name='position_changed' type='SFVec3f'/>\n" +
            "      <field accessType='outputOnly' appinfo='HUD orientation update relative to original location' name='orientation_changed' type='SFRotation'/>\n" +
            "    </ExternProtoDeclare>\n" +
            "    <!-- Only one copy of ExternProtoDeclare is needed in a scene. Multiple ProtoInstance nodes can be created like the following -->\n" +
            "    <ProtoInstance DEF='HUD' name='HeadsUpDisplay'>\n" +
            "      <!-- example: upper left-hand corner of screen (x=-2, y=1) and set back z=-5 from user view -->\n" +
            "      <fieldValue name='screenOffset' value='-2 1 -5'/>\n" +
            "      <fieldValue name='children'>\n" +
            "        <Shape>\n" +
            "          <Text string='\"HUD text stays fixed\" \"while user navigates\"'>\n" +
            "            <FontStyle justify='\"MIDDLE\" \"MIDDLE\"' size='0.3'/>\n" +
            "          </Text>\n" +
            "          <Appearance>\n" +
            "            <Material diffuseColor='0.894118 0.819608 1'/>\n" +
            "          </Appearance>\n" +
            "        </Shape>\n" +
            "      </fieldValue>\n" +
            "    </ProtoInstance>\n" +
            "    <!-- Example use: https://savage.nps.edu/Savage/Tools/Animation/HeadsUpDisplayExample.x3d -->\n" +
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
    return "HeadsUpDisplay";
  }
}
