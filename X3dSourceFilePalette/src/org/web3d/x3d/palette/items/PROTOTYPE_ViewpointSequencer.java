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
 * PROTOTYPE_ViewpointSequencer.java
 * Created on 18 October 2009
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Don Brutzman, Mike Bailey
 * @version $Id$
 */
public class PROTOTYPE_ViewpointSequencer extends SceneGraphStructureNodeType
{
  public PROTOTYPE_ViewpointSequencer()
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
            "    <ExternProtoDeclare name='ViewpointSequencer'\n" +
            "        appinfo='Sequentially binds each Viewpoint in a set of Viewpoint USE nodes, creating an automatic tour for a scene'\n" +
            "        url='\"../../Savage/Tools/Animation/ViewpointSequencerPrototype.x3d#ViewpointSequencer\"\n" +
            "             \"../../../Savage/Tools/Animation/ViewpointSequencerPrototype.x3d#ViewpointSequencer\"\n" +
            "             \"https://savage.nps.edu/Savage/Tools/Animation/ViewpointSequencerPrototype.x3d#ViewpointSequencer\"\n" +
            "             \"../../../Savage/Tools/Animation/ViewpointSequencerPrototype.wrl#ViewpointSequencer\"\n" +
            "             \"../../Savage/Tools/Animation/ViewpointSequencerPrototype.wrl#ViewpointSequencer\"\n" +
            "             \"https://savage.nps.edu/Savage/Tools/Animation/ViewpointSequencerPrototype.wrl#ViewpointSequencer\" '>\n" +
            "      <field accessType='initializeOnly' appinfo='Viewpoint USE nodes that are sequentially bound' name='viewpoints' type='MFNode'>\n" +
            "         <!-- default value NULL node, overridden by ProtoInstance -->\n" +
            "      </field>\n" +
            "      <field accessType='inputOutput' appinfo='number of seconds between viewpoint shifts' name='interval' type='SFTime'/>\n" +
            "      <field accessType='inputOutput' appinfo='whether ViewpointSequencer is enabled or not' name='enabled' type='SFBool'/>\n" +
            "      <field accessType='inputOnly' appinfo='whether ViewpointSequencer is enabled or not' name='set_enabled' type='SFBool'/>\n" +
            "      <field accessType='inputOnly' appinfo='bind previous Viewpoint in list' name='previous' type='SFBool'/>\n" +
            "      <field accessType='inputOnly' appinfo='bind next Viewpoint in list' name='next' type='SFBool'/>\n" +
            "      <field accessType='inputOutput' appinfo='Select message to toggle ViewpointSequencer' name='toggleMessage' type='MFString'/>\n" +
            "      <field accessType='initializeOnly' appinfo='Font size for toggleMessage text' name='toggleMessageFontSize' type='SFFloat'/>\n" +
            "      <field accessType='inputOutput' appinfo='Color for toggleMessage text' name='toggleMessageColor' type='SFColor'/>\n" +
            "      <field accessType='inputOutput' appinfo='enable console output' name='traceEnabled' type='SFBool'/>\n" +
            "    </ExternProtoDeclare>\n" +
            "    <Transform scale='1 1 1' translation='0 0 0'>\n" +
            "      <!-- Only one copy of a given ExternProtoDeclare is needed in a scene. Multiple ProtoInstance nodes can be created like the following: -->\n" +
            "      <ProtoInstance name='ViewpointSequencer' DEF='ViewpointSequencerExample'>\n" +
            "        <fieldValue name='viewpoints'>\n" +
            "          <!-- Authors can also DEF/USE other Viewpoint nodes used elsewhere in a scene -->\n" +
            "          <Viewpoint DEF='View0' description='View zero (+Y axis)' orientation='1 0 0 -1.57' position='0 10 0'/>\n" +
            "          <Viewpoint DEF='View1' description='View one (+Z axis)'/>\n" +
            "          <Viewpoint DEF='View2' description='View two (+X axis)' orientation='0 1 0 1.57' position='10 0 0'/>\n" +
            "          <Viewpoint DEF='View3' description='View three (-Z axis)' orientation='0 1 0 3.14' position='0 0 -10'/>\n" +
            "          <Viewpoint DEF='View4' description='View four (-X axis)' orientation='0 1 0 -1.57' position='-10 0 0'/>\n" +
            "        </fieldValue>\n" +
            "        <fieldValue name='interval' value='2'/>\n" +
            "        <!-- initially enabled is off, scene provides selectable text to activate -->\n" +
            "        <fieldValue name='enabled' value='false'/>\n" +
            "      </ProtoInstance>\n" +
            "    </Transform>\n" +
            "    <!-- Example use: https://savage.nps.edu/Savage/Tools/Animation/ViewpointSequencerExample.x3d -->\n" +
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
    return "ViewpointSequencer";
  }
}
