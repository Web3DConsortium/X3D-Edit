package org.web3d.x3d;

import javax.swing.text.BadLocationException;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;
import org.web3d.x3d.palette.X3DPaletteUtilities;
import org.web3d.x3d.palette.X3DPaletteUtilities.ElementLocation;

@ActionID(id = "org.web3d.x3d.SelectElementAction", category = "Edit")
@ActionRegistration(displayName = "#CTL_SelectElementAction", 
                    lazy=true) // don't do lazy=false since iconBase no longer gets registered)
@ActionReferences( value = {
  @ActionReference(path = "Menu/X3D-Edit/Edit Element XML", position = 400),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/Edit Element XML", position = 400)
})

public final class SelectElementAction extends BaseX3DEditAction //CookieAction
{  
  @Override
  protected void doWork(Node[] activatedNodes)
  {
    try {
      ElementLocation selectedLocation = X3DPaletteUtilities.findSelectedElement(documentEditorPane); //findSelectedElement();
      highlightSelectedElement(selectedLocation);
    }
    catch (BadLocationException ex) {
      throw new RuntimeException(ex); // let base class handle
    }
  }

  @Override
  protected int mode()
  {
    return CookieAction.MODE_EXACTLY_ONE;
  }
  
  @Override
  public String getName()
  {
    return NbBundle.getMessage(SelectElementAction.class, "CTL_SelectElementAction");
  }
  
  @Override
  protected Class[] cookieClasses()
  {
    return new Class[] {
      X3DDataObject.class
    };
  }
  
  @Override
  protected void initialize()
  {
    super.initialize();
    // see org.openide.util.actions.SystemAction.iconResource() javadoc for more details
    putValue("noIconInMenu", Boolean.TRUE);
  }
  
  @Override
  public HelpCtx getHelpCtx()
  {
    return HelpCtx.DEFAULT_HELP;
  }
  
  @Override
  protected boolean asynchronous()
  {
    return false;
  }
  
}

