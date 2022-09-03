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
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * SCENE.java
 * Created on August 2, 2007, 2:46 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class SCENE extends SceneGraphStructureNodeType
{
  public SCENE()
  {
  }

  @Override
  public void initialize()
  {
    setContent("\n    <!--TODO add X3D elements here-->\n  ");
  }

  @SuppressWarnings("unchecked")
  /**
   * If we return null here, the string returned from createBody() simply gets jammed into
   * place without showing a dialog, which is sometimes what we want.
   * Here, we've made it so that if there is a SCENECustomizer in the classpath, we'll use
   * it.  Otherwise, just insert the text.
   * 
   * Note, the comment above is re: dragging a new Scene element.  Trying to edit an existing
   * one where there's no customizer simply selects the element.
   * @return class to instantiate to show a dialog
   */
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    Class<? extends BaseCustomizer> c = null;
    try {
      c = (Class<? extends BaseCustomizer>)Class.forName("org.web3d.x3d.palette.items.SCENECustomizer");
    }
    catch(Exception e){}
    return c;
  }

  @Override
  public String getElementName()
  {
    return SCENE_ELNAME;
  }
}
