/*
Copyright (c) 1995-2022 held by the author(s) .  All rights reserved.

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

import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import org.netbeans.spi.palette.PaletteItemRegistration;
import org.openide.text.ActiveEditorDrop;
import org.web3d.x3d.palette.X3DPaletteUtilities;
import static org.web3d.x3d.types.X3DSchemaData.*;
// import org.web3d.x3d.types.SceneGraphStructureNodeType;

// TODO verify annotations correctly added to palette
@PaletteItemRegistration
(
    paletteid = "X3DPalette",
    category = "X3D",
    itemid = "COMMENT",
    icon32 = "org/web3d/x3d/palette/items/resources/COMMENT32.png", // icon is <!--
    icon16 = "org/web3d/x3d/palette/items/resources/COMMENT16.png",
    body = "<!-- enter new comment here -->",
    name = "Comment XML -->",
    tooltip = "Valid comments are found between XML elements"
)
/**
 * COMMENT.java
 * Created on Mar 7, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 * https://netbeans.apache.org/tutorials/nbm-palette-api1.html
 *
 * @author Mike Bailey and Don Brutzman
 * @version $Id$
 */
public class COMMENT /* TODO extends SceneGraphStructureNodeType */ implements ActiveEditorDrop
{
    public COMMENT()
    {
    }

    //  @Override
    public void initialize()
    {
        System.out.println("*** COMMENT initialize() ...");
    }

    //  @Override
    public String createBody()
    {
      return "\n    <!-- enter new comment here -->";
    }

    @Override
    public boolean  handleTransfer(JTextComponent targetComponent) {
        String body = createBody();
        try {
            X3DPaletteUtilities.insert(body, targetComponent);
        } catch (BadLocationException ble) {
            return false;
        }
        return true;
    }
//  @SuppressWarnings("unchecked")
//  @Override
  /**
   * If we return null here, the string returned from createBody() simply gets jammed into
   * place without showing a dialog, which is sometimes what we want.
   * Here, we've made it so that if there is a COMMENTCustomizer in the classpath, we'll use
   * it.  Otherwise, just insert the text.
   * 
   * Note, the comment above is re: dragging a new head element.  Trying to edit an existing
   * one where there's no customizer simply selects the element.
   * 
   * Further note:  There is no comment customizer because selecting a comment element in the editor
   * doesn't work.  Further DOM/JDOM support would be required to find a comment element.
   * 
   * @return class to instantiate to show a dialog
   */
//  public Class<? extends BaseCustomizer> getCustomizer()
//  {
//    Class<? extends BaseCustomizer> c = null;
//    try {
//      c = (Class<? extends BaseCustomizer>)Class.forName("org.web3d.x3d.palette.items.COMMENTCustomizer");
//    }
//    catch(Exception e){}
//    return c;
//  }

//  @Override
  public String getElementName()
  {
    return COMMENT_ELNAME;
  }
}
