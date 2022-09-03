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

package org.web3d.x3d.palette.items;

import javax.swing.text.JTextComponent;
import org.web3d.x3d.types.X3DChildNode;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * POSITIONCHASER2D.java
 * Created on 6 February 2010
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */

public class POSITIONCHASER2D extends X3DChildNode
{
  private SFFloat duration,            durationDefault;
  private SFFloat initialDestination0, initialDestination0Default;
  private SFFloat initialDestination1, initialDestination1Default;
  
  private SFFloat initialValue0, initialValue0Default;
  private SFFloat initialValue1, initialValue1Default;

  public POSITIONCHASER2D()
  {
  }

  @Override
  public String getElementName()
  {
    return POSITIONCHASER2D_ELNAME;
  }
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return POSITIONCHASER2DCustomizer.class;
  }

  @Override
  public void initialize()
  {
    duration       = durationDefault       = new SFFloat(POSITIONCHASER2D_ATTR_DURATION_DFLT,        0.0f, null);
    String[] fa;
    fa = parse2(POSITIONCHASER2D_ATTR_INITIALDESTINATION_DFLT);
        setInitialDestination0 (initialDestination0Default = new SFFloat (fa[0], null, null));
        setInitialDestination1 (initialDestination1Default = new SFFloat (fa[1], null, null));

    fa = parse2(POSITIONCHASER2D_ATTR_INITIALVALUE_DFLT);
        setInitialValue0 (initialValue0Default = new SFFloat (fa[0], null, null));
        setInitialValue1 (initialValue1Default = new SFFloat (fa[1], null, null));
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(POSITIONCHASER2D_ATTR_DURATION_NAME);
    if (attr != null)
        duration = new SFFloat(attr.getValue(), 0.0f, null);

    String[] fa;
    attr = root.getAttribute(POSITIONCHASER2D_ATTR_INITIALDESTINATION_NAME);
    if (attr != null) {
      fa = parse2(attr.getValue());
            setInitialDestination0 (new SFFloat (fa[0], null, null));
            setInitialDestination1 (new SFFloat (fa[1], null, null));
    }
    attr = root.getAttribute(POSITIONCHASER2D_ATTR_INITIALVALUE_NAME);
    if (attr != null) {
      fa = parse2(attr.getValue());
            setInitialValue0 (new SFFloat (fa[0], null, null));
            setInitialValue1 (new SFFloat (fa[1], null, null));
    }
  }
 
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (POSITIONCHASER2D_ATTR_DURATION_REQD || (!duration.equals (durationDefault))) {
      sb.append(" ");
      sb.append(POSITIONCHASER2D_ATTR_DURATION_NAME);
      sb.append("='");
      sb.append(duration);
      sb.append("'");
    }
    if(POSITIONCHASER2D_ATTR_INITIALDESTINATION_REQD ||
           (!initialDestination0.equals(initialDestination0Default) ||
            !initialDestination1.equals(initialDestination1Default)) ) {
      sb.append(" ");
      sb.append(POSITIONCHASER2D_ATTR_INITIALDESTINATION_NAME);
      sb.append("='");
      sb.append(getInitialDestination0 ());
      sb.append(" ");
      sb.append(getInitialDestination1 ());
      sb.append("'");
    }
    if(POSITIONCHASER2D_ATTR_INITIALVALUE_REQD ||
           (!initialValue0.equals(initialValue0Default) ||
            !initialValue1.equals(initialValue1Default)) ) {
      sb.append(" ");
      sb.append(POSITIONCHASER2D_ATTR_INITIALVALUE_NAME);
      sb.append("='");
      sb.append(getInitialValue0 ());
      sb.append(" ");
      sb.append(getInitialValue1 ());
      sb.append("'");
    }
    return sb.toString();
  }
    /**
     * @return the duration value
     */
    public String getDuration ()
    {
        return duration.toString();
    }
    /**
     * @param newDuration the duration value to set
     */
    public void setDuration (SFFloat newDuration)
    {
        this.duration = newDuration;
    }

    /**
     * @return the initialDestination0
     */
    public SFFloat getInitialDestination0 ()
    {
        return initialDestination0;
    }

    /**
     * @param initialDestination0 the initialDestination0 to set
     */
    public void setInitialDestination0 (SFFloat initialDestination0)
    {
        this.initialDestination0 = initialDestination0;
    }

    /**
     * @return the initialDestination1
     */
    public SFFloat getInitialDestination1 ()
    {
        return initialDestination1;
    }

    /**
     * @param initialDestination1 the initialDestination1 to set
     */
    public void setInitialDestination1 (SFFloat initialDestination1)
    {
        this.initialDestination1 = initialDestination1;
    }

    /**
     * @return the initialValue0
     */
    public SFFloat getInitialValue0 ()
    {
        return initialValue0;
    }

    /**
     * @param initialValue0 the initialValue0 to set
     */
    public void setInitialValue0 (SFFloat initialValue0)
    {
        this.initialValue0 = initialValue0;
    }

    /**
     * @return the initialValue1
     */
    public SFFloat getInitialValue1 ()
    {
        return initialValue1;
    }

    /**
     * @param initialValue1 the initialValue1 to set
     */
    public void setInitialValue1 (SFFloat initialValue1)
    {
        this.initialValue1 = initialValue1;
    }

}
