package org.web3d.x3d;

import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.nodes.Node;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CookieAction;
import org.web3d.x3d.palette.X3DPaletteUtilitiesJdom;
import org.web3d.x3d.palette.X3DPaletteUtilitiesJdom.ElementLocation;

@ActionID(id = "org.web3d.x3d.DeleteElementAction", category = "Edit")
@ActionRegistration(displayName = "#CTL_DeleteElementAction", 
                    lazy=true) // don't do lazy=false since iconBase no longer gets registered
@ActionReferences( value = {
  @ActionReference(path = "Menu/X3D-Edit/Edit Element XML", position = 700),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/Edit Element XML", position = 700)
})

public final class DeleteElementAction extends BaseX3DEditAction //CookieAction
{  
  @Override
  protected void doWork(Node[] activatedNodes)
  {      
    try {
     ElementLocation selectedLocation = X3DPaletteUtilitiesJdom.findSelectedElement(documentEditorPane); //findSelectedElement();
     deleteLocatedElement(selectedLocation);
     
     Node n = activatedNodes[0];
     doValidate(n);
    }
    catch(Exception ex) {  //todo, specific msgs for spec exceptions
      throw new RuntimeException(ex); // let baseclass handle
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
    return NbBundle.getMessage(DeleteElementAction.class, "CTL_DeleteElementAction");
  }
  
  @Override
  protected Class<?>[] cookieClasses()
  {
    return new Class<?>[] {
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

