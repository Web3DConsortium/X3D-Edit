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
 * PROTOTYPE_MaterialChoice.java
 * Created on 17 October 2009
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Don Brutzman, Mike Bailey
 * @version $Id$
 */
public class PROTOTYPE_MaterialChoice extends SceneGraphStructureNodeType
{
  public PROTOTYPE_MaterialChoice()
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
            "    <ExternProtoDeclare name='MaterialChoice'\n" +
            "        appinfo='MaterialChoice selects one of several different Material values'\n" +
            "        url='\"../../Savage/Tools/Animation/MaterialChoicePrototype.x3d#MaterialChoice\"\n" +
            "             \"../../../Savage/Tools/Animation/MaterialChoicePrototype.x3d#MaterialChoice\"\n" +
            "             \"https://savage.nps.edu/Savage/Tools/Animation/MaterialChoicePrototype.x3d#MaterialChoice\"\n" +
            "             \"../../../Savage/Tools/Animation/MaterialChoicePrototype.wrl#MaterialChoice\"\n" +
            "             \"../../Savage/Tools/Animation/MaterialChoicePrototype.wrl#MaterialChoice\"\n" +
            "             \"https://savage.nps.edu/Savage/Tools/Animation/MaterialChoicePrototype.wrl#MaterialChoice\" '>\n" +
            "            <field accessType='inputOnly' name='set_index' type='SFInt32'/>\n" +
            "            <field accessType='initializeOnly' name='index' type='SFInt32' appinfo='which Material node is chosen, with array index starting at 0'/>\n" +
            "            <field accessType='outputOnly' name='index_changed' type='SFInt32'/>\n" +
            "            <field accessType='inputOnly' name='set_fraction' type='SFFloat'/>\n" +
            "            <field accessType='initializeOnly' name='fraction' type='SFFloat'/>\n" +
            "            <field accessType='inputOnly' name='next' type='SFBool'/>\n" +
            "            <field accessType='inputOnly' name='previous' type='SFBool'/>\n" +
            "            <field accessType='initializeOnly' appinfo='array of available Material nodes defaults to zeroth Material' name='materials' type='MFNode'/>\n" +
            "            <field accessType='inputOnly' appinfo='append another Material node to materials array' name='appendMaterial' type='SFNode'/>\n" +
            "            <field accessType='inputOnly' appinfo='delete Material node indicated by input index value' name='deleteMaterial' type='SFInt32'/>\n" +
            "            <field accessType='inputOnly' appinfo='deletes all Material nodes resets scene to default Material values defined in X3D Specification' name='deleteAllMaterials' type='SFBool'/>\n" +
            "    </ExternProtoDeclare>\n" +
            "    <!-- Only one copy of a given ExternProtoDeclare is needed in a scene. Multiple ProtoInstance nodes can be created like the following: -->\n" +
            "    <Shape>\n" +
            "      <!-- geometry goes here -->\n" +
            "      <Appearance>\n" +
            "        <ProtoInstance name='MaterialChoice' containerField='material' DEF='MaterialChoiceExample'>\n" +
            "          <fieldValue name='index' value='0'/>\n" +
            "          <fieldValue name='fraction' value='0.0'/>\n" +
            "          <fieldValue name='materials'>\n" +
            "            <Material ambientIntensity='0.24' diffuseColor='1 0.452381 0.40339' shininess='0.9' specularColor='0.686486 0.396903 0.419275'>\n" +
            "              <!-- Universal Media Library: ArtDeco 3 -->\n" +
            "            </Material>\n" +
            "            <Material ambientIntensity='0.24' diffuseColor='0.330519 0.3389 0.6' shininess='0.78' specularColor='0.290909 0.290909 0.290909'>\n" +
            "              <!-- Universal Media Library: ArtDeco 8 -->\n" +
            "            </Material>\n" +
            "            <Material ambientIntensity='0.25' diffuseColor='0 0.251004 0.239248' shininess='0.06' specularColor='0.177935 0.249369 0.229278'>\n" +
            "              <!-- Universal Media Library: ArtDeco 19 -->\n" +
            "            </Material>\n" +
            "          </fieldValue>\n" +
            "        </ProtoInstance>\n" +
            "        </Appearance>\n" +
            "    </Shape>\n" +
            "    <!-- Example use: https://savage.nps.edu/Savage/Tools/Animation/MaterialChoiceExample.x3d -->\n" +
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
    return "MaterialChoice";
  }
}
