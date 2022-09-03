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

/**
 * X3DPaletteActions.java
 * Created on March 8, 2007, 5:15 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */

package org.web3d.x3d.palette;

import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.text.JTextComponent;
import org.netbeans.editor.Utilities;
import org.netbeans.spi.palette.PaletteActions;
import org.netbeans.spi.palette.PaletteController;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.text.ActiveEditorDrop;
import org.openide.util.Lookup;
import org.openide.util.NbBundle;

public class X3DPaletteActions extends PaletteActions
{
  
  /** Creates a new instance of FormPaletteProvider */
  public X3DPaletteActions()
  {
  }
  
  public Action[] getImportActions()
  {
    return new Action[0]; //TODO implement this
  }
  
  public Action[] getCustomCategoryActions(Lookup category)
  {
    return new Action[0]; //TODO implement this
  }
  
  public Action[] getCustomItemActions(Lookup item)
  {
    return new Action[0]; //TODO implement this
  }
  
  public Action[] getCustomPaletteActions()
  {
    return new Action[0]; //TODO implement this
  }
  
  public Action getPreferredAction( Lookup item )
  {
    return new X3DPaletteInsertAction(item);
  }
  
  private static class X3DPaletteInsertAction extends AbstractAction
  {
    
    private Lookup item;
    
    X3DPaletteInsertAction(Lookup item)
    {
      this.item = item;
    }
    
    public void actionPerformed(ActionEvent e)
    {
      
      ActiveEditorDrop drop = (ActiveEditorDrop) item.lookup(ActiveEditorDrop.class);
//            if (drop == null) {
//                String body = (String) item.lookup(String.class);
//                drop = new X3DEditorDropDefault(body);
//            }
      
      JTextComponent target = Utilities.getFocusedComponent();
      if (target == null)
      {
        String msg = NbBundle.getMessage(X3DPaletteActions.class, "MSG_ErrorNoFocusedDocument");
        DialogDisplayer.getDefault().notify(new NotifyDescriptor.Message(msg, NotifyDescriptor.ERROR_MESSAGE));
        return;
      }
      
      try
      {
        drop.handleTransfer(target);
      }
      finally
      {
        Utilities.requestFocus(target);
      }
      
      try
      {
        PaletteController pc = X3DPaletteFactory.getPalette();
        pc.clearSelection();
      }
      catch (IOException ioe)
      {
      } //should not occur
      
    }
  }
  
}
