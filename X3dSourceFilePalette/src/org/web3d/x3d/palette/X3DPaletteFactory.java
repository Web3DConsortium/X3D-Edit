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

package org.web3d.x3d.palette;

/**
 * X3D Palette for Netbeans
 * https://netbeans.apache.org/tutorials/nbm-palette-api1.html
 * https://netbeans.apache.org/tutorials/nbm-palette-api2.html
 * @author brutzman@nps.edu
 */

import java.io.IOException;
import javax.swing.Action;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.spi.palette.DragAndDropHandler;
import org.netbeans.spi.palette.PaletteActions;
import org.netbeans.spi.palette.PaletteController;
import org.netbeans.spi.palette.PaletteFactory;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.datatransfer.ExTransferable;
//          TODO Issue 4521  import org.openide.util.Exceptions;

public class X3DPaletteFactory
{
    private static PaletteController palette = null;
    public  static final String X3D_PALETTE_FOLDER = "X3DPalette"; 

    @MimeRegistration (mimeType = "text/xml", service = PaletteController.class) // TODO add X3D MIME type registration

    public static PaletteController createPalette()
    {
        try {
            if (null == getPalette()) {
                return  PaletteFactory.createPalette(
                    //Folder:
                     X3D_PALETTE_FOLDER, // TODO resolve "items", //
                    //Palette Actions:
                    new X3DPaletteActions(),
//                  replaces:
//                    new  PaletteActions() {
//                        @Override public Action[] getImportActions() {return null;}
//                        @Override public Action[] getCustomPaletteActions() {return null;}
//                        @Override public Action[] getCustomCategoryActions(Lookup lkp) {return null;}
//                        @Override public Action[] getCustomItemActions(Lookup lkp) {return null;}
//                        @Override public Action   getPreferredAction(Lookup lkp) {return null;}
//                    },
                    //Palette Filter:
                    null,
                    //Drag and Drop Handler:
                    new  DragAndDropHandler(true)
                    {
                        @Override public void customize(ExTransferable et, Lookup lkp) {}
                });
            }
        }
        catch (IOException ex)
        {
            Exceptions.printStackTrace(ex);
//          TODO Issue 4521  ex.printStackTrace();
        }
        return null;
    }

    /**
     * Create palette if needed, then return the palette
     * @return the palette
     */
    public static PaletteController getPalette() 
    {
        try
        {
            if (palette == null)
                palette = PaletteFactory.createPalette(X3D_PALETTE_FOLDER, new X3DPaletteActions());
            if (palette == null)
                System.err.println("*** X3DPaletteFactory.getPalette() failed to find " + X3D_PALETTE_FOLDER);
        } 
        catch (IOException ex)
        {
            Exceptions.printStackTrace(ex);
        }
        return palette;
    }

}