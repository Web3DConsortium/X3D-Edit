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
 * INLINE_GridsMovable.java
 * Created on 4 June 2009
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Don Brutzman, Mike Bailey
 * @version $Id$
 */
public class INLINE_GridsMovable extends SceneGraphStructureNodeType
{
  public INLINE_GridsMovable()
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
            "    <!-- Grid overlay authoring hint:  first adjust grid scale to convenient large size, then adjust overall scale for your scene model. Default block size 1m by 1m. -->\n" +
            "    <Transform DEF='GridsMovable_AdjustScale' scale='1 1 1'>\n" +
            "       <Inline DEF='GridXY_20x20Movable' url='\"GridXY_20x20Movable.x3d\"\n" +
            "          \"../../../Savage/Tools/Authoring/GridXY_20x20Movable.x3d\"\n" +
            "          \"https://savage.nps.edu/Savage/Tools/Authoring/GridXY_20x20Movable.x3d\"\n" +
            "          \"GridXY_20x20Movable.wrl\"\n" +
            "          \"../../../Savage/Tools/Authoring/GridXY_20x20Movable.wrl\"\n" +
            "          \"https://savage.nps.edu/Savage/Tools/Authoring/GridXY_20x20Movable.wrl\"'/>\n" +
            "       <Inline DEF='GridXZ_20x20Movable' url='\"GridXZ_20x20Movable.x3d\"\n" +
            "          \"../../../Savage/Tools/Authoring/GridXZ_20x20Movable.x3d\"\n" +
            "          \"https://savage.nps.edu/Savage/Tools/Authoring/GridXZ_20x20Movable.x3d\"\n" +
            "          \"GridXZ_20x20Movable.wrl\"\n" +
            "          \"../../../Savage/Tools/Authoring/GridXZ_20x20Movable.wrl\"\n" +
            "          \"https://savage.nps.edu/Savage/Tools/Authoring/GridXZ_20x20Movable.wrl\"'/>\n" +
            "       <Inline DEF='GridYZ_20x20Movable' url='\"GridYZ_20x20Movable.x3d\"\n" +
            "          \"../../../Savage/Tools/Authoring/GridYZ_20x20Movable.x3d\"\n" +
            "          \"https://savage.nps.edu/Savage/Tools/Authoring/GridYZ_20x20Movable.x3d\"\n" +
            "          \"GridYZ_20x20Movable.wrl\"\n" +
            "          \"../../../Savage/Tools/Authoring/GridYZ_20x20Movable.wrl\"\n" +
            "          \"https://savage.nps.edu/Savage/Tools/Authoring/GridYZ_20x20Movable.wrl\"'/>\n" +
            "    </Transform>\n";
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
    return "GridsMovable";
  }
}
