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
package org.web3d.x3d.actions.security;

import java.awt.Dimension;
import javax.swing.JTable;
import javax.swing.JViewport;

/**
 * JTableWithHorizontalScrollFix.java
 * Created on Jun 19, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author mike
 * @version $Id$
 */
public class JTableWithHorizontalScrollFix extends JTable
{
//Override BOTH getScrollableTracksViewportWidth() AND getPreferredSize() to have them work together:

// when the viewport shrinks below the preferred size, stop tracking the viewport width
  @Override
  public boolean getScrollableTracksViewportWidth()
  {
    if (autoResizeMode != AUTO_RESIZE_OFF) {
      if (getParent() instanceof JViewport) {
        return (((JViewport) getParent()).getWidth() > getPreferredSize().width);
      }
    }
    return false;
  }

// when the viewport shrinks below the preferred size, return the minimum size
// so that scrollbars will be shown
  @Override
  public Dimension getPreferredSize()
  {
    if (getParent() instanceof JViewport) {
      if (((JViewport) getParent()).getWidth() < super.getPreferredSize().width) {
        return getMinimumSize();
      }
    }

    return super.getPreferredSize();
  }
}
