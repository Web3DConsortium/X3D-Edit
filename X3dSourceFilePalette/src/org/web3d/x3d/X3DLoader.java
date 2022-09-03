/*
Copyright (c) 1995-2021 held by the author(s).  All rights reserved.
 
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
package org.web3d.x3d;

import java.io.IOException;
import org.openide.filesystems.FileObject;
import org.openide.loaders.DataObjectExistsException;
import org.openide.loaders.MultiDataObject;
import org.openide.loaders.UniFileLoader;
import org.openide.util.NbBundle;

/**
 * Loader for X3D DataObjects.
 * 
 * X3DLoader.java
 * Created on March 9, 2007, 9:29 AM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class X3DLoader extends UniFileLoader
{
  private static final long serialVersionUID = 1L;
  public static String X3D_MIME = "model/x3d+xml";
  
  public X3DLoader()
  {
    super("org.web3d.x3d.X3DDataObject"); // NOI18N
  }
  
  @Override
  protected void initialize()
  {
    super.initialize();
    getExtensions().addMimeType(X3D_MIME); // NOI18N
  }
  
  @Override
  protected MultiDataObject createMultiObject(final FileObject primaryFile) throws DataObjectExistsException, IOException
  {
    return new X3DDataObject(primaryFile, this);
  }
  
  /** Get the default display name of this loader.
   * @return default display name
   */
  @Override
  protected String defaultDisplayName()
  {
    return NbBundle.getMessage(X3DLoader.class, "PROP_X3dLoader_Name");
  }
  
  @Override
  protected String actionsContext()
  {
    return "Loaders/"+X3D_MIME+"/Actions/"; // NOI18N
  }
  
//  private void dump(String s)
//  {
//    InputOutput io = IOProvider.getDefault().getIO("Hello", true);
//    io.getOut().println(s);
//    io.getErr().println(s);  //this text should appear in red
//    io.getOut().close();
//    io.getErr().close();
//  }
}
