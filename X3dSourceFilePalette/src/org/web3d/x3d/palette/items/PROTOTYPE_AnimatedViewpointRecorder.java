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
 * PROTOTYPE_AnimatedViewpointRecorder.java
 * Created on 6 May 2009
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Don Brutzman, Mike Bailey
 * @version $Id$
 */
public class PROTOTYPE_AnimatedViewpointRecorder extends SceneGraphStructureNodeType
{
  public PROTOTYPE_AnimatedViewpointRecorder()
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
            "    <ExternProtoDeclare name='AnimatedViewpointRecorder'\n" +
            "        appinfo='AnimatedViewpointRecorder captures view position and orientation tour to create a guided tour animation. The recording output goes to the browser console where the .x3d (or .x3dv) output can be cut/pasted for further use.'\n" +
            "        url='\"../../Savage/Tools/Authoring/AnimatedViewpointRecorderPrototype.x3d#AnimatedViewpointRecorder\"\n" +
            "             \"../../../Savage/Tools/Authoring/AnimatedViewpointRecorderPrototype.x3d#AnimatedViewpointRecorder\"\n" +
            "             \"https://savage.nps.edu/Savage/Tools/Authoring/AnimatedViewpointRecorderPrototype.x3d#AnimatedViewpointRecorder\"\n" +
            "             \"../../../Savage/Tools/Authoring/AnimatedViewpointRecorderPrototype.wrl#AnimatedViewpointRecorder\"\n" +
            "             \"../../Savage/Tools/Authoring/AnimatedViewpointRecorderPrototype.wrl#AnimatedViewpointRecorder\"\n" +
            "             \"https://savage.nps.edu/Savage/Tools/Authoring/AnimatedViewpointRecorderPrototype.wrl#AnimatedViewpointRecorder\" '>\n" +
            "      <field accessType='inputOnly' appinfo='Set start=true to commence recording viewpoint position/orientation.' name='start' type='SFBool'/>\n" +
            "      <field accessType='inputOnly' appinfo='Set stop=true to finish recording viewpoint position/orientation. Resulting VRML is added to scene resulting X3D and VRML is output to console.' name='stop' type='SFBool'/>\n" +
            "      <field accessType='initializeOnly' appinfo='seconds' name='samplingInterval' type='SFTime'/>\n" +
            "      <field accessType='initializeOnly' appinfo='TODO not yet implemented' name='filterDeadTime' type='SFBool'/>\n" +
            "    </ExternProtoDeclare>\n" +
            "    <!-- Only one copy of a given ExternProtoDeclare is needed in a scene. Multiple ProtoInstance nodes can be created like the following: -->\n" +
            "    <ProtoInstance DEF='AnimatedViewpointRecorderExample' name='AnimatedViewpointRecorder'/>\n" +
            "    <Group DEF='RecordingControls'>\n" +
            "      <Transform translation='-3 0 0'>\n" +
            "        <Shape>\n" +
            "          <Text string='\"Touch text to\" \"start recording\" \"view animation\"'>\n" +
            "            <FontStyle DEF='CenterJustify' justify='\"MIDDLE\" \"MIDDLE\"' size='0.5'/>\n" +
            "          </Text>\n" +
            "          <Appearance>\n" +
            "            <Material diffuseColor='0.2 0.8 0.2' transparency='0.5'/>\n" +
            "          </Appearance>\n" +
            "        </Shape>\n" +
            "        <Shape DEF='TransparentBox'>\n" +
            "          <Box size='3 1.75 0.01'/>\n" +
            "          <Appearance>\n" +
            "            <Material transparency='1'/>\n" +
            "          </Appearance>\n" +
            "        </Shape>\n" +
            "        <TouchSensor DEF='StartTouch' description='touch to start recording' enabled='true'/>\n" +
            "        <ROUTE fromField='isActive' fromNode='StartTouch' toField='start' toNode='AnimatedViewpointRecorderExample'/>\n" +
            "      </Transform>\n" +
            "      <Transform translation='3 0 0'>\n" +
            "        <Shape>\n" +
            "          <Text string='\"Touch text to\" \"stop recording\" \"view animation\"'>\n" +
            "            <FontStyle USE='CenterJustify'/>\n" +
            "          </Text>\n" +
            "          <Appearance>\n" +
            "            <Material diffuseColor='0.8 0.2 0.2' transparency='0.5'/>\n" +
            "          </Appearance>\n" +
            "        </Shape>\n" +
            "        <Shape USE='TransparentBox'/>\n" +
            "        <TouchSensor DEF='StopTouch' description='touch to stop recording' enabled='true'/>\n" +
            "        <ROUTE fromField='isActive' fromNode='StopTouch' toField='stop' toNode='AnimatedViewpointRecorderExample'/>\n" +
            "      </Transform>\n" +
            "    </Group>\n" +
            "    <!-- Example use: https://savage.nps.edu/Savage/Tools/Animation/AnimatedViewpointRecorderExample.x3d -->\n" +
            "    <!-- ==================== -->\n";

    // TODO:  instead use an Inline with push button start/stop?

    // X3D-Edit TODO:  make this capability a built-in option
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
    return "AnimatedViewpointRecorder";
  }
}
