/*
* Copyright (c) 1995-2021 held by the author(s) .  All rights reserved.
*  
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions
* are met:
*  
*  * Redistributions of source code must retain the above copyright
*       notice, this list of conditions and the following disclaimer.
*  * Redistributions in binary form must reproduce the above copyright
*       notice, this list of conditions and the following disclaimer
*       in the documentation and/or other materials provided with the
*       distribution.
*  * Neither the names of the Naval Postgraduate School (NPS)
*       Modeling Virtual Environments and Simulation (MOVES) Institute
*       (http://www.nps.edu and https://MovesInstitute.nps.edu)
*       nor the names of its contributors may be used to endorse or
*       promote products derived from this software without specific
*       prior written permission.
*  
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
* "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
* LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
* FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
* COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
* INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
* BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
* CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
* ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
* POSSIBILITY OF SUCH DAMAGE.
*/

package org.web3d.x3d.actions.conversions;

import javax.swing.JMenuItem;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;

@ActionID(id = "org.web3d.x3d.actions.conversions.ImportClassicVrmlAction", category = "File")
@ActionRegistration(displayName = "#CTL_ImportClassicVrmlAction")
@ActionReferences(value = {
  @ActionReference(path = "Menu/X3D-Edit/Import Model from File", position = 200),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/Import Model from File", position = 200)
})

public final class ImportClassicVrmlAction extends Xj3DConverterBaseImportAction
{
  @Override
  public String getInputFileType()                   {return "x3dv";}
  @Override
  public String getOutputFileType()                  {return "x3d";}
  @Override
  public String getChooserTitle()                    {return NbBundle.getMessage(getClass(), "CTL_ImportClassicVrmlOpenTitle");}
  @Override
  public String getInputFileTypeDescription()        {return NbBundle.getMessage(getClass(), "CTL_ImportClassicVrmlFileDescription");}
  @Override
  public String getPreConversionMessage()            {return NbBundle.getMessage(getClass(), "CTL_ImportClassicVrmlPreMsg");}
  @Override
  public String getPostConversionMessage()           {return NbBundle.getMessage(getClass(), "CTL_ImportClassicVrmlPostMsg");}
  @Override
  public String getConversionExceptionPrefixMessage(){return NbBundle.getMessage(getClass(), "CTL_ImportClassicVrmlException_");}
  @Override
  public String getConversionCancelledMessage()      {return NbBundle.getMessage(getClass(), "CTL_ImportClassicVrmlCancelled");}
  
  @Override
  public String getName()
  {
    return NbBundle.getMessage(getClass(), "CTL_ImportClassicVrmlAction");
  }

  @Override
  protected void initialize()
  {
    super.initialize();
    // see org.openide.util.actions.SystemAction.iconResource() Javadoc for more details
    putValue("noIconInMenu", Boolean.TRUE);
  }
  
  /**
   * Do this because this call in the super creates a new one every time, losing any
   * previous tt.
   * @return what goes into the menu
   */
  @Override
  public JMenuItem getMenuPresenter()
  {
    JMenuItem mi = super.getMenuPresenter();
    mi.setToolTipText(NbBundle.getMessage(getClass(), "CTL_ImportClassicVrmlAction_tt"));
    return mi;
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
