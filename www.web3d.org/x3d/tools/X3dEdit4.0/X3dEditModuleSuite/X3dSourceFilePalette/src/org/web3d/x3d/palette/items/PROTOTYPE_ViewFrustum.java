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
 * PROTOTYPE_ViewFrustum.java
 * Created on 23 June 2009
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Don Brutzman, Mike Bailey
 * @version $Id$
 */
public class PROTOTYPE_ViewFrustum extends SceneGraphStructureNodeType
{
  public PROTOTYPE_ViewFrustum()
  {
  }

  @Override
  public void initialize()
  {
  }

  @Override
  public String createBody()
  {
    // also see Viewpoint and GeoViewpoint

    return  getExternProtoDeclare() + getProtoInstance();
  }

  static public String getExternProtoDeclare ()
  {
     return "\n" +
            "    <!-- ==================== -->\n" +
            "    <ExternProtoDeclare name='ViewFrustum'\n" +
            "        appinfo='Display view frustum associated with a given pair of Viewpoint NavigationInfo nodes'\n" +
            "        url='\"../../../X3dForWebAuthors/Chapter14Prototypes/ViewFrustumPrototype.x3d#ViewFrustum\"\n" +
            "             \"http://X3dGraphics.com/examples/X3dForWebAuthors/Chapter14Prototypes/ViewFrustumPrototype.x3d#ViewFrustum\"\n" +
            "             \"../../../X3dForWebAuthors/Chapter14Prototypes/ViewFrustumPrototype.wrl#ViewFrustum\"\n" +
            "             \"http://X3dGraphics.com/examples/X3dForWebAuthors/Chapter14Prototypes/ViewFrustumPrototype.wrl#ViewFrustum\"'>\n" +
            "      <field name='ViewpointNode' type='SFNode' accessType='initializeOnly' appinfo='required: insert Viewpoint DEF or USE node for view of interest'/>\n" +
            "      <field name='NavigationInfoNode' type='SFNode' accessType='initializeOnly' appinfo='required: insert NavigationInfo DEF or USE node of interest'/>\n" +
            "      <field name='visible' type='SFBool' accessType='inputOutput' appinfo='whether or not frustum geometry is rendered'/>\n" +
            "      <field name='lineColor' type='SFColor' accessType='inputOutput' appinfo='RGB color of ViewFrustum outline, default value 0.9 0.9 0.9'/>\n" +
            "      <field name='frustumColor' type='SFColor' accessType='inputOutput' appinfo='RGB color of ViewFrustum hull geometry, default value 0.8 0.8 0.8'/>\n" +
            "      <field name='transparency' type='SFFloat' accessType='inputOutput' appinfo='transparency of ViewFrustum hull geometry, default value 0.5'/>\n" +
            "      <field name='aspectRatio' type='SFFloat' accessType='inputOutput' appinfo='assumed ratio height/width, default value 0.75'/>\n" +
            "      <field name='trace' type='SFBool' accessType='initializeOnly' appinfo='debug support, default false'/>\n" +
            "    </ExternProtoDeclare>" +
            "    <!-- Only one copy of a given ExternProtoDeclare is needed in a scene. Multiple ProtoInstance nodes can be created like the following: -->\n";
  }
  static public String getProtoInstance ()
  {
     return "\n" +
            "    <!-- ==================== -->\n" +
            "    <ProtoInstance name='ViewFrustum'>\n" +
            "      <fieldValue name='ViewpointNode'>\n" +
            "        <Viewpoint description='viewpoint for ViewFrustum' fieldOfView='0.78' position='0 0 0'/>\n" +
            "      </fieldValue>\n" +
            "      <fieldValue name='NavigationInfoNode'>\n" +
            "        <NavigationInfo avatarSize='1 1.6 0.75' visibilityLimit='20'/>\n" +
            "      </fieldValue>\n" +
            "      <fieldValue name='lineColor' value='0.9 0.9 0.9'/>\n" +
            "      <fieldValue name='frustumColor' value='0.8 0.8 0.8'/>\n" +
            "      <fieldValue name='transparency' value='0.5'/>\n" +
            "      <fieldValue name='aspectRatio' value='0.75'/>\n" +
            "    </ProtoInstance>\n" +
            "    <!-- Example use: https://www.x3dgraphics.com/examples/X3dForWebAuthors/Chapter14Prototypes/ViewFrustumExample.x3d -->\n" +
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
    return "ViewFrustum";
  }
}
