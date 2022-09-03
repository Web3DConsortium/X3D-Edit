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
 * PROTOTYPE_ColorSequencer.java
 * Created on 17 October 2009
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Don Brutzman, Mike Bailey
 * @version $Id$
 */
public class PROTOTYPE_ColorSequencer extends SceneGraphStructureNodeType
{
  public PROTOTYPE_ColorSequencer()
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
            "    <ExternProtoDeclare name='ColorSequencer'\n" +
            "        appinfo='ColorSequencer outputs a single color value by selecting an array index or simply sequencing next/previous'\n" +
            "        url='\"../../Savage/Tools/Animation/ColorSequencerPrototype.x3d#ColorSequencer\"\n" +
            "             \"../../../Savage/Tools/Animation/ColorSequencerPrototype.x3d#ColorSequencer\"\n" +
            "             \"https://savage.nps.edu/Savage/Tools/Animation/ColorSequencerPrototype.x3d#ColorSequencer\"\n" +
            "             \"../../../Savage/Tools/Animation/ColorSequencerPrototype.wrl#ColorSequencer\"\n" +
            "             \"../../Savage/Tools/Animation/ColorSequencerPrototype.wrl#ColorSequencer\"\n" +
            "             \"https://savage.nps.edu/Savage/Tools/Animation/ColorSequencerPrototype.wrl#ColorSequencer\" '>\n" +
            "            <field accessType='inputOutput' appinfo='Whether or not this sequencer is active' name='enabled' type='SFBool'/>\n" +
            "            <field accessType='inputOutput' appinfo='Initial index is array element 0. Setting index past colors[max] uses final color value setting, while index less than 0 uses colors[0] value.' name='index' type='SFInt32'/>\n" +
            "            <field accessType='inputOutput' appinfo='Array of color values that are each the outputs of the sequencer. No interpolation occurs between values.' name='colors' type='MFColor'/>\n" +
            "            <field accessType='outputOnly' appinfo='Current output color value of the sequencer corresponding to colors[index] value.' name='color_changed' type='SFColor'/>\n" +
            "            <field accessType='inputOnly' appinfo='Trigger previous color value. Wrap around from zeroth color to final color if necessary. Only respond to true inputs.' name='previous' type='SFBool'/>\n" +
            "            <field accessType='inputOnly' appinfo='Trigger next color value. Wrap around from final color to zeroth color if necessary. Only respond to true inputs.' name='next' type='SFBool'/>\n" +
            "            <field accessType='initializeOnly' appinfo='Enable tracing of node operation on browser console' name='traceEnabled' type='SFBool'/>\n" +
            "    </ExternProtoDeclare>\n" +
            "    <!-- Only one copy of a given ExternProtoDeclare is needed in a scene. Multiple ProtoInstance nodes can be created like the following: -->\n" +
            "    <ProtoInstance name='ColorSequencer' DEF='ColorSequencerExample'>\n" +
            "      <fieldValue name='enabled' value='true'/>\n" +
            "      <fieldValue name='index' value='0'/>\n" +
            "      <!-- ROY G BIV = red orange yellow green blue indigo violet -->\n" +
            "      <fieldValue name='colors' value='1 0 0 1 0.5 0 1 1 0 0 1 0 0 0 1 0.2 0.2 0.2 0.4 0 0.4'/>\n" +
            "    </ProtoInstance>\n" +
            "    <!-- Example use: https://savage.nps.edu/Savage/Tools/Animation/ColorSequencerExample.x3d -->\n" +
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
    return "ColorSequencer";
  }
}
