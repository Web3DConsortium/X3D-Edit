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

/**
 * X3DPaletteCustomizerAction.java
 * Created on March 9, 2007, 10:09 AM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */

package org.web3d.x3d.palette;

import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.openide.util.actions.CallableSystemAction;
//import java.io.IOException;
//import org.openide.ErrorManager;

@SuppressWarnings("serial")
public class X3DPaletteCustomizerAction extends CallableSystemAction
{
  private static String name;
  
  public X3DPaletteCustomizerAction()
  {
      System.out.println ("*** created X3DPaletteCustomizerAction()");
    putValue("noIconInMenu", Boolean.TRUE); // NOI18N
  }
  
  @Override
  protected boolean asynchronous()
  {
    return false;
  }
  
  /** Human presentable name of the action. This should be
   * presented as an item in a menu.
   * @return the name of the action
   */
  @Override
  public String getName()
  {
    if (name == null)
      name = NbBundle.getBundle(X3DPaletteCustomizerAction.class).getString("ACT_OpenX3DCustomizer"); // NOI18N
    
    return name;
  }
  
  /** Help context where to find more about the action.
   * @return the help context for this action
   */
  @Override
  public HelpCtx getHelpCtx()
  {
    return null;
  }
  
  /** This method is called by one of the "invokers" as a result of
   * some user's action that should lead to actual "performing" of the action.
   */
  @Override
  public void performAction()
  {
      X3DPaletteFactory.getPalette().showCustomizer();
      
//    try
//    {
//      X3DPaletteFactory.getPalette().showCustomizer();
//    }
//    catch (IOException ioe)
//    {
//      ErrorManager.getDefault().notify(ErrorManager.EXCEPTION, ioe);
//    }
  }
  
}
