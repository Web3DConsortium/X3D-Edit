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

/**
 * SCALARDAMPER.java
 * Created on 6 February 2010
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */

public class SCALARDAMPER extends X3DChildNode
{
  private SFFloat tau,       tauDefault;
  private SFFloat tolerance, toleranceDefault;
  private SFInt32   order,     orderDefault;
  private SFFloat initialDestination0, initialDestination0Default;
  
  private SFFloat initialValue0, initialValue0Default;

  public SCALARDAMPER()
  {
  }

  @Override
  public String getElementName()
  {
    return SCALARDAMPER_ELNAME;
  }
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return SCALARDAMPERCustomizer.class;
  }

  @Override
  public void initialize()
  {
    tau       = tauDefault       = new SFFloat(SCALARDAMPER_ATTR_TAU_DFLT,        0.0f, null);
    tolerance = toleranceDefault = new SFFloat(SCALARDAMPER_ATTR_TOLERANCE_DFLT, -1.0f, null);
    order     = orderDefault     = new SFInt32  (SCALARDAMPER_ATTR_ORDER_DFLT,      0,    5);
	
    setInitialDestination0 (initialDestination0Default = new SFFloat (SCALARDAMPER_ATTR_INITIALDESTINATION_DFLT, null, null));

    setInitialValue0 (initialValue0Default = new SFFloat (SCALARDAMPER_ATTR_INITIALVALUE_DFLT, null, null));
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(SCALARDAMPER_ATTR_TAU_NAME);
    if (attr != null)
        tau = new SFFloat(attr.getValue(), 0.0f, null);

    attr = root.getAttribute(SCALARDAMPER_ATTR_TOLERANCE_NAME);
    if (attr != null)
        tolerance = new SFFloat(attr.getValue(), -1.0f, null);

    attr = root.getAttribute(SCALARDAMPER_ATTR_ORDER_NAME);
    if (attr != null)
        order =  new SFInt32(attr.getValue(),0,5);

    attr = root.getAttribute(SCALARDAMPER_ATTR_INITIALDESTINATION_NAME);
    if (attr != null) {
      setInitialDestination0 (new SFFloat (attr.getValue(), null, null));
    }
    attr = root.getAttribute(SCALARDAMPER_ATTR_INITIALVALUE_NAME);
    if (attr != null) {
      setInitialValue0 (new SFFloat (attr.getValue(), null, null));
    }
  }
 
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (SCALARDAMPER_ATTR_ORDER_REQD || (!order.equals (orderDefault))) {
      sb.append(" ");
      sb.append(SCALARDAMPER_ATTR_ORDER_NAME);
      sb.append("='");
      sb.append(order);
      sb.append("'");
    }
    if (SCALARDAMPER_ATTR_TAU_REQD || (!tau.equals (tauDefault))) {
      sb.append(" ");
      sb.append(SCALARDAMPER_ATTR_TAU_NAME);
      sb.append("='");
      sb.append(tau);
      sb.append("'");
    }
    if (SCALARDAMPER_ATTR_TOLERANCE_REQD || (!tolerance.equals (toleranceDefault))) {
      sb.append(" ");
      sb.append(SCALARDAMPER_ATTR_TOLERANCE_NAME);
      sb.append("='");
      sb.append(tolerance);
      sb.append("'");
	}
    if(SCALARDAMPER_ATTR_INITIALDESTINATION_REQD ||
           (!initialDestination0.equals(initialDestination0Default)) ) {
      sb.append(" ");
      sb.append(SCALARDAMPER_ATTR_INITIALDESTINATION_NAME);
      sb.append("='");
      sb.append(getInitialDestination0 ());
      sb.append("'");
    }
    if(SCALARDAMPER_ATTR_INITIALVALUE_REQD ||
           (!initialValue0.equals(initialValue0Default)) ) {
      sb.append(" ");
      sb.append(SCALARDAMPER_ATTR_INITIALVALUE_NAME);
      sb.append("='");
      sb.append(getInitialValue0 ());
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
     * @param newTau the tau value to set
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

}
