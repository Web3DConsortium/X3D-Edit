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
 * X3dDataNode.java
 * Created on March 8, 2007, 3:11 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */

package org.web3d.x3d;

import org.openide.loaders.DataObject;
import org.openide.loaders.DataNode;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.nodes.Sheet;
import org.openide.util.NbBundle;

/**
 * Node that represents X3D data object.
 *
 */
public class X3DDataNode extends DataNode
{
  private Sheet sheet = null;

  /** Creates new X3DDataNode
   * @param dobj
   * @param ch
   */
  public X3DDataNode(DataObject dobj, Children ch)
  {
    super(dobj, ch);
    setShortDescription(NbBundle.getMessage(X3DDataNode.class, "LBL_x3dNodeShortDesc"));
    // todo, set OpenAction as "preferredAction"?
  }

  @Override
  public Node.PropertySet[] getPropertySets()
  {
    if(sheet == null)
    {
      sheet = new Sheet();

      Node.PropertySet[] tmp = super.getPropertySets();
        for (PropertySet tmp1 : tmp) {
            Sheet.Set set = new Sheet.Set();
            set.setName(tmp1.getName());
            set.setShortDescription(tmp1.getShortDescription());
            set.setDisplayName(tmp1.getDisplayName());
            set.setValue("helpID", X3DDataNode.class.getName() + ".PropertySheet");// NOI18N
            set.put(tmp1.getProperties());
            sheet.put(set);
        }
    }

    return sheet.toArray();
  }
}

