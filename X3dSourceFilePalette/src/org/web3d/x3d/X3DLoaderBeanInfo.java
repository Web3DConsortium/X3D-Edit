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
 * X3DLoaderBeanInfo.java
 * Created on March 9, 2007, 9:37 AM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */

package org.web3d.x3d;

import java.awt.Image;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.SimpleBeanInfo;
import org.openide.ErrorManager;
import org.openide.loaders.UniFileLoader;
import org.openide.util.ImageUtilities;

public class X3DLoaderBeanInfo extends SimpleBeanInfo
{
  @Override
  public BeanInfo[] getAdditionalBeanInfo()
  {
    try
    {
      return new BeanInfo[] { Introspector.getBeanInfo(UniFileLoader.class) };
    }
    catch (IntrospectionException ie)
    {
      ErrorManager.getDefault().notify(ie);
      return null;
    }
  }

  /** @param type Desired type of the icon
   * @return returns the Image loader's icon
   */
  @Override
  public Image getIcon(final int type)
  {
    if ( (type == java.beans.BeanInfo.ICON_COLOR_16x16) ||
         (type == java.beans.BeanInfo.ICON_MONO_16x16))
    {
      return ImageUtilities.loadImage("org/web3d/x3d/resources/x3dObject.png"); // NOI18N
    }
    else
    {
      return ImageUtilities.loadImage("org/web3d/x3d/resources/x3dObject32.png"); // NOI18N
    }
  }

}
