package org.web3d.x3d.palette;

/**
 *
 * @author brutzman
 */

import java.io.IOException;
import javax.swing.Action;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.spi.palette.DragAndDropHandler;
import org.netbeans.spi.palette.PaletteActions;
import org.netbeans.spi.palette.PaletteController;
import org.netbeans.spi.palette.PaletteFactory;
import org.openide.util.Lookup;
import org.openide.util.datatransfer.ExTransferable;
//          TODO Issue 4521  import org.openide.util.Exceptions;

public class X3dPaletteFactory
{
    public  static final String X3D_PALETTE_FOLDER = "X3DPalette"; 
    private static PaletteController palette = null;

    @MimeRegistration (mimeType = "text/x-java", service = PaletteController.class) // TODO MIME type registration
    public static PaletteController createPalette()
    {
        try {
            if (null == palette) {
                return  PaletteFactory.createPalette(
                //Folder:
                X3D_PALETTE_FOLDER,
                //Palette Actions:
                new  PaletteActions() {
                    @Override public Action[] getImportActions() {return null;}
                    @Override public Action[] getCustomPaletteActions() {return null;}
                    @Override public Action[] getCustomCategoryActions(Lookup lkp) {return null;}
                    @Override public Action[] getCustomItemActions(Lookup lkp) {return null;}
                    @Override public Action   getPreferredAction(Lookup lkp) {return null;}
                },
                //Palette Filter:
                null,
                //Drag and Drop Handler:
                new  DragAndDropHandler(true) {
                    @Override public void customize(ExTransferable et, Lookup lkp) {}
                });
            }
        } catch (IOException ex) {
//          TODO Issue 4521  Exceptions.printStackTrace(ex);
            ex.printStackTrace();
        }
        return null;
    }

}