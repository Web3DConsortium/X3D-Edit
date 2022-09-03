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
 * PROTOTYPE_ViewPositionOrientation.java
 * Created on 6 May 2009
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Don Brutzman, Mike Bailey
 * @version $Id$
 */
public class PROTOTYPE_ViewPositionOrientation extends SceneGraphStructureNodeType
{
  public PROTOTYPE_ViewPositionOrientation()
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
            "    <ExternProtoDeclare name='ViewPositionOrientation'\n" +
            "        appinfo='ViewPositionOrientation provides provides console output of local position and orientation as user navigates'\n" +
            "        url='\"../../Savage/Tools/Authoring/ViewPositionOrientationPrototype.x3d#ViewPositionOrientation\"\n" +
            "             \"../../../Savage/Tools/Authoring/ViewPositionOrientationPrototype.x3d#ViewPositionOrientation\"\n" +
            "             \"https://savage.nps.edu/Savage/Tools/Authoring/ViewPositionOrientationPrototype.x3d#ViewPositionOrientation\"\n" +
            "             \"../../Savage/Tools/Authoring/ViewPositionOrientationPrototype.wrl#ViewPositionOrientation\"\n" +
            "             \"../../../Savage/Tools/Authoring/ViewPositionOrientationPrototype.wrl#ViewPositionOrientation\"\n" +
            "             \"https://savage.nps.edu/Savage/Tools/Authoring/ViewPositionOrientationPrototype.wrl#ViewPositionOrientation\"'>\n" +
            "      <field accessType='inputOutput' appinfo='Whether or not ViewPositionOrientation sends output to console' name='enabled' type='SFBool'/>\n" +
            "      <field accessType='initializeOnly' appinfo='Output internal trace messages for debugging this node, intended for developer use only' name='traceEnabled' type='SFBool'/>\n" +
            "      <field accessType='inputOnly' appinfo='Ability to turn output tracing on/off at runtime' name='set_traceEnabled' type='SFBool'/>\n" +
            "      <field accessType='outputOnly' appinfo='Output local position' name='position_changed' type='SFVec3f'/>\n" +
            "      <field accessType='outputOnly' appinfo='Output local orientation' name='orientation_changed' type='SFRotation'/>\n" +
            "      <field accessType='outputOnly' appinfo='MFString value of new Viewpoint, suitable for use in string field of a Text node' name='outputViewpointString' type='MFString'/>\n" +
            "      <field accessType='inputOutput' appinfo='expose internal metadata node' name='metadata' type='SFNode'/>\n" +
            "    </ExternProtoDeclare>\n" +
            "    <!-- Only one copy of a given ExternProtoDeclare is needed in a scene. Multiple ProtoInstance nodes can be created like the following: -->\n" +
            "    <ProtoInstance name='ViewPositionOrientation' DEF='ViewPositionOrientationExample'>\n" +
            "      <fieldValue name='enabled' value='true'/>\n" +
            "    </ProtoInstance>\n" +
            "    <!-- Example use: https://savage.nps.edu/Savage/Tools/Animation/ViewPositionOrientationExample.x3d -->\n" +
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
    return "ViewPositionOrientation";
  }
}
