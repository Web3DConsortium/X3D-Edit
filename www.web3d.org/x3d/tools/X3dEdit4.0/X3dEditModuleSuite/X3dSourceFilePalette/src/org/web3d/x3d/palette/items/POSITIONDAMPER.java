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
 * POSITIONDAMPER.java
 * Created on 6 February 2010
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */

public class POSITIONDAMPER extends X3DChildNode
{
  private SFFloat tau,       tauDefault;
  private SFFloat tolerance, toleranceDefault;
  private SFInt32   order,     orderDefault;
  private SFFloat initialDestination0, initialDestination0Default;
  private SFFloat initialDestination1, initialDestination1Default;
  private SFFloat initialDestination2, initialDestination2Default;
  
  private SFFloat initialValue0, initialValue0Default;
  private SFFloat initialValue1, initialValue1Default;
  private SFFloat initialValue2, initialValue2Default;

  public POSITIONDAMPER()
  {
  }

  @Override
  public String getElementName()
  {
    return POSITIONDAMPER_ELNAME;
  }
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return POSITIONDAMPERCustomizer.class;
  }

  @Override
  public void initialize()
  {
    tau       = tauDefault       = new SFFloat(POSITIONDAMPER_ATTR_TAU_DFLT,        0.0f, null);
    tolerance = toleranceDefault = new SFFloat(POSITIONDAMPER_ATTR_TOLERANCE_DFLT, -1.0f, null);
    order     = orderDefault     = new SFInt32  (POSITIONDAMPER_ATTR_ORDER_DFLT,      0,    5);

    String[] fa;
    fa = parse3(POSITIONDAMPER_ATTR_INITIALDESTINATION_DFLT);
        setInitialDestination0 (initialDestination0Default = new SFFloat (fa[0], null, null));
        setInitialDestination1 (initialDestination1Default = new SFFloat (fa[1], null, null));
        setInitialDestination2 (initialDestination2Default = new SFFloat (fa[2], null, null));

    fa = parse3(POSITIONDAMPER_ATTR_INITIALVALUE_DFLT);
        setInitialValue0 (initialValue0Default = new SFFloat (fa[0], null, null));
        setInitialValue1 (initialValue1Default = new SFFloat (fa[1], null, null));
        setInitialValue2 (initialValue2Default = new SFFloat (fa[2], null, null));
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(POSITIONDAMPER_ATTR_TAU_NAME);
    if (attr != null)
        tau = new SFFloat(attr.getValue(), 0.0f, null);

    attr = root.getAttribute(POSITIONDAMPER_ATTR_TOLERANCE_NAME);
    if (attr != null)
        tolerance = new SFFloat(attr.getValue(), -1.0f, null);

    attr = root.getAttribute(POSITIONDAMPER_ATTR_ORDER_NAME);
    if (attr != null)
        order =  new SFInt32(attr.getValue(),0,5);

    String[] fa;
    attr = root.getAttribute(POSITIONDAMPER_ATTR_INITIALDESTINATION_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
            setInitialDestination0 (new SFFloat (fa[0], null, null));
            setInitialDestination1 (new SFFloat (fa[1], null, null));
            setInitialDestination2 (new SFFloat (fa[2], null, null));
    }
    attr = root.getAttribute(POSITIONDAMPER_ATTR_INITIALVALUE_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
            setInitialValue0 (new SFFloat (fa[0], null, null));
            setInitialValue1 (new SFFloat (fa[1], null, null));
            setInitialValue2 (new SFFloat (fa[2], null, null));
    }
  }
 
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if(POSITIONDAMPER_ATTR_INITIALDESTINATION_REQD ||
           (!initialDestination0.equals(initialDestination0Default) ||
            !initialDestination1.equals(initialDestination1Default) ||
            !initialDestination2.equals(initialDestination2Default)) ) {
      sb.append(" ");
      sb.append(POSITIONDAMPER_ATTR_INITIALDESTINATION_NAME);
      sb.append("='");
      sb.append(getInitialDestination0 ());
      sb.append(" ");
      sb.append(getInitialDestination1 ());
      sb.append(" ");
      sb.append(getInitialDestination2 ());
      sb.append("'");
    }
    if(POSITIONDAMPER_ATTR_INITIALVALUE_REQD ||
           (!initialValue0.equals(initialValue0Default) ||
            !initialValue1.equals(initialValue1Default) ||
            !initialValue2.equals(initialValue2Default)) ) {
      sb.append(" ");
      sb.append(POSITIONDAMPER_ATTR_INITIALVALUE_NAME);
      sb.append("='");
      sb.append(getInitialValue0 ());
      sb.append(" ");
      sb.append(getInitialValue1 ());
      sb.append(" ");
      sb.append(getInitialValue2 ());
      sb.append("'");
    }
    if (POSITIONDAMPER_ATTR_ORDER_REQD || (!order.equals (orderDefault))) {
      sb.append(" ");
      sb.append(POSITIONDAMPER_ATTR_ORDER_NAME);
      sb.append("='");
      sb.append(order);
      sb.append("'");
    }
    if (POSITIONDAMPER_ATTR_TAU_REQD || (!tau.equals (tauDefault))) {
      sb.append(" ");
      sb.append(POSITIONDAMPER_ATTR_TAU_NAME);
      sb.append("='");
      sb.append(tau);
      sb.append("'");
    }
    if (POSITIONDAMPER_ATTR_TOLERANCE_REQD || (!tolerance.equals (toleranceDefault))) {
      sb.append(" ");
      sb.append(POSITIONDAMPER_ATTR_TOLERANCE_NAME);
      sb.append("='");
      sb.append(tolerance);
      sb.append("'");
    }
    return sb.toString();
  }
    /**
     * @return the tau value
     */
    public String getTau ()
    {
        return tau.toString();
    }
    /**
     * @param newTolerance the tau value to set
     */
    public void setTau (String newTau)
    {
        this.tau = new SFFloat(newTau, 0.0f, null);
    }
    /**
     * @return the tolerance value
     */
    public String getTolerance ()
    {
        return tolerance.toString();
    }
    /**
     * @param newTolerance the tolerance value to set
     */
    public void setTolerance (String newTolerance)
    {
        this.tolerance = new SFFloat(newTolerance, -1.0f, null);
    }

    /**
     * @return the order value
     */
    public int getOrder ()
    {
        return order.getValue();
    }

    /**
     * @param order the order value to set
     */
    public void setOrder (String order)
    {
        this.order =  new SFInt32(order,0,5);
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
     * @return the initialDestination2
     */
    public SFFloat getInitialDestination2 ()
    {
        return initialDestination2;
    }

    /**
     * @param initialDestination2 the initialDestination2 to set
     */
    public void setInitialDestination2 (SFFloat initialDestination2)
    {
        this.initialDestination2 = initialDestination2;
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

    /**
     * @return the initialValue2
     */
    public SFFloat getInitialValue2 ()
    {
        return initialValue2;
    }

    /**
     * @param initialValue2 the initialValue2 to set
     */
    public void setInitialValue2 (SFFloat initialValue2)
    {
        this.initialValue2 = initialValue2;
    }
}
