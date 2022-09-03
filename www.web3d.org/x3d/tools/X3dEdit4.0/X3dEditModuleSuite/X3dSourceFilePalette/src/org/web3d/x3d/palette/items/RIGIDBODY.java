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

import org.web3d.x3d.types.X3DNode;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * RIGIDBODY.java
 * Created on 2 January 2009
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class RIGIDBODY extends X3DNode
{
  private boolean autoDamp,          autoDampDefault;
  private boolean autoDisable,       autoDisableDefault;
  private boolean enabled,           enabledDefault;
  private boolean fixed,             fixedDefault;
  private boolean useFiniteRotation, useFiniteRotationDefault;
  private boolean useGlobalGravity,  useGlobalGravityDefault;

  private SFFloat angularDampingFactor, angularDampingFactorDefault;

  private SFFloat angularVelocity0, angularVelocity0Default;
  private SFFloat angularVelocity1, angularVelocity1Default;
  private SFFloat angularVelocity2, angularVelocity2Default;
  
  private SFFloat centerOfMass0, centerOfMass0Default;
  private SFFloat centerOfMass1, centerOfMass1Default;
  private SFFloat centerOfMass2, centerOfMass2Default;
  
  private SFFloat disableAngularSpeed, disableAngularSpeedDefault;
  private SFFloat disableLinearSpeed,  disableLinearSpeedDefault;
  private SFFloat disableTime,         disableTimeDefault;
  
  private SFFloat finiteRotationAxis0, finiteRotationAxis0Default;
  private SFFloat finiteRotationAxis1, finiteRotationAxis1Default;
  private SFFloat finiteRotationAxis2, finiteRotationAxis2Default;
  
  private String  forces;

  private SFFloat inertia00, inertia00Default;
  private SFFloat inertia01, inertia01Default;
  private SFFloat inertia02, inertia02Default;
  private SFFloat inertia10, inertia10Default;
  private SFFloat inertia11, inertia11Default;
  private SFFloat inertia12, inertia12Default;
  private SFFloat inertia20, inertia20Default;
  private SFFloat inertia21, inertia21Default;
  private SFFloat inertia22, inertia22Default;
  
  private SFFloat linearDampingFactor, linearDampingFactorDefault;

  private SFFloat linearVelocity0, linearVelocity0Default;
  private SFFloat linearVelocity1, linearVelocity1Default;
  private SFFloat linearVelocity2, linearVelocity2Default;
  private SFFloat mass, massDefault;

  private SFFloat orientation0, orientation0Default;
  private SFFloat orientation1, orientation1Default;
  private SFFloat orientation2, orientation2Default;
  private SFFloat orientation3, orientation3Default;

  private SFFloat position0, position0Default;
  private SFFloat position1, position1Default;
  private SFFloat position2, position2Default;
  
  private String  torques;

  public RIGIDBODY()
  {
  }

  @Override
  public String getElementName()
  {
    return RIGIDBODY_ELNAME;
  }
  @Override
  public String getDefaultContainerField()
  {
    return "body1"; // body1, body2, bodies
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return RIGIDBODYCustomizer.class;
  }

  @Override
  public void initialize()
  {
    String[] fa;

    autoDamp          = autoDampDefault          = Boolean.parseBoolean(RIGIDBODY_ATTR_AUTODAMP_DFLT);
    autoDisable       = autoDisableDefault       = Boolean.parseBoolean(RIGIDBODY_ATTR_AUTODISABLE_DFLT);
    enabled           = enabledDefault           = Boolean.parseBoolean(RIGIDBODY_ATTR_ENABLED_DFLT);
    fixed             = fixedDefault             = Boolean.parseBoolean(RIGIDBODY_ATTR_FIXED_DFLT);
    useFiniteRotation = useFiniteRotationDefault = Boolean.parseBoolean(RIGIDBODY_ATTR_USEFINITEROTATION_DFLT);
    useGlobalGravity  = useGlobalGravityDefault  = Boolean.parseBoolean(RIGIDBODY_ATTR_USEGLOBALGRAVITY_DFLT);

    angularDampingFactor     = angularDampingFactorDefault     = new SFFloat(RIGIDBODY_ATTR_ANGULARDAMPINGFACTOR_DFLT, 0.0f, 1.0f);
    disableAngularSpeed      = disableAngularSpeedDefault      = new SFFloat(RIGIDBODY_ATTR_DISABLEANGULARSPEED_DFLT, 0.0f, null);
    disableLinearSpeed       = disableLinearSpeedDefault       = new SFFloat(RIGIDBODY_ATTR_DISABLELINEARSPEED_DFLT, 0.0f, null);
    disableTime              = disableTimeDefault              = new SFFloat(RIGIDBODY_ATTR_DISABLETIME_DFLT, 0.0f, null);
    
    fa = parse3(RIGIDBODY_ATTR_ANGULARVELOCITY_DFLT);
    angularVelocity0 = angularVelocity0Default = new SFFloat(fa[0], null, null);
    angularVelocity1 = angularVelocity1Default = new SFFloat(fa[1], null, null);
    angularVelocity2 = angularVelocity2Default = new SFFloat(fa[2], null, null);

    fa = parse3(RIGIDBODY_ATTR_CENTEROFMASS_DFLT);
    centerOfMass0 = centerOfMass0Default = new SFFloat(fa[0], null, null);
    centerOfMass1 = centerOfMass1Default = new SFFloat(fa[1], null, null);
    centerOfMass2 = centerOfMass2Default = new SFFloat(fa[2], null, null);

    fa = parse3(RIGIDBODY_ATTR_FINITEROTATIONAXIS_DFLT);
    finiteRotationAxis0 = finiteRotationAxis0Default = new SFFloat(fa[0], -1.0f, 1.0f);
    finiteRotationAxis1 = finiteRotationAxis1Default = new SFFloat(fa[1], -1.0f, 1.0f);
    finiteRotationAxis2 = finiteRotationAxis2Default = new SFFloat(fa[2], -1.0f, 1.0f);
    
    forces = RIGIDBODY_ATTR_FORCES_DFLT;

    fa = parse9(RIGIDBODY_ATTR_INERTIA_DFLT);
    inertia00 = inertia00Default = new SFFloat(fa[0], null, null);
    inertia01 = inertia01Default = new SFFloat(fa[1], null, null);
    inertia02 = inertia02Default = new SFFloat(fa[2], null, null);
    inertia10 = inertia10Default = new SFFloat(fa[3], null, null);
    inertia11 = inertia11Default = new SFFloat(fa[4], null, null);
    inertia12 = inertia12Default = new SFFloat(fa[5], null, null);
    inertia20 = inertia20Default = new SFFloat(fa[6], null, null);
    inertia21 = inertia21Default = new SFFloat(fa[7], null, null);
    inertia22 = inertia22Default = new SFFloat(fa[8], null, null);
    
    linearDampingFactor = linearDampingFactorDefault = new SFFloat(RIGIDBODY_ATTR_LINEARDAMPINGFACTOR_DFLT, 0.0f, 1.0f);

    fa = parse3(RIGIDBODY_ATTR_LINEARVELOCITY_DFLT);
    linearVelocity0 = linearVelocity0Default = new SFFloat(fa[0], null, null);
    linearVelocity1 = linearVelocity1Default = new SFFloat(fa[1], null, null);
    linearVelocity2 = linearVelocity2Default = new SFFloat(fa[2], null, null);
    
    mass = massDefault = new SFFloat(RIGIDBODY_ATTR_MASS_DFLT, 0.0f, null);

    fa = parse4(RIGIDBODY_ATTR_ORIENTATION_DFLT);
    orientation0 = orientation0Default = new SFFloat(fa[0], null, null);
    orientation1 = orientation1Default = new SFFloat(fa[1], null, null);
    orientation2 = orientation2Default = new SFFloat(fa[2], null, null);
    orientation3 = orientation3Default = new SFFloat(fa[2], null, null);

    fa = parse3(RIGIDBODY_ATTR_POSITION_DFLT);
    position0 = position0Default = new SFFloat(fa[0], null, null);
    position1 = position1Default = new SFFloat(fa[1], null, null);
    position2 = position2Default = new SFFloat(fa[2], null, null);
    
    torques = RIGIDBODY_ATTR_TORQUES_DFLT;

    setContent("\n\t\t<!-- geometry can be multiple CollidableOffset or CollidableShape nodes -->" +
               "\n\t\t<CollidableShape containerField='geometry'/>" +
               "\n\t\t<!-- massDensityModel can be Sphere, Box or Cone -->" +
               "\n\t\t<Box containerField='massDensityModel'/>\n\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;
    String[] fa;
    
    attr = root.getAttribute(RIGIDBODY_ATTR_ANGULARDAMPINGFACTOR_NAME);
    if (attr != null) {
      angularDampingFactor = new SFFloat(attr.getValue(), 0.0f, 1.0f);
    }
    attr = root.getAttribute(RIGIDBODY_ATTR_ANGULARVELOCITY_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      angularVelocity0 = new SFFloat(fa[0], null, null);
      angularVelocity1 = new SFFloat(fa[1], null, null);
      angularVelocity2 = new SFFloat(fa[2], null, null);
    }
    attr = root.getAttribute(RIGIDBODY_ATTR_AUTODAMP_NAME);
    if (attr != null) {
      autoDamp   = Boolean.parseBoolean(attr.getValue());
    }
    attr = root.getAttribute(RIGIDBODY_ATTR_AUTODISABLE_NAME);
    if (attr != null) {
      autoDisable   = Boolean.parseBoolean(attr.getValue());
    }
    attr = root.getAttribute(RIGIDBODY_ATTR_CENTEROFMASS_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      centerOfMass0 = new SFFloat(fa[0], null, null);
      centerOfMass1 = new SFFloat(fa[1], null, null);
      centerOfMass2 = new SFFloat(fa[2], null, null);
    }    
    attr = root.getAttribute(RIGIDBODY_ATTR_DISABLEANGULARSPEED_NAME);
    if (attr != null)  {
      disableAngularSpeed   = new SFFloat(attr.getValue(), null, null);
    }
    attr = root.getAttribute(RIGIDBODY_ATTR_DISABLELINEARSPEED_NAME);
    if (attr != null)  {
      disableLinearSpeed   = new SFFloat(attr.getValue(), null, null);
    }
    attr = root.getAttribute(RIGIDBODY_ATTR_DISABLETIME_NAME);
    if (attr != null)  {
      disableTime   = new SFFloat(attr.getValue(), null, null);
    }
    attr = root.getAttribute(RIGIDBODY_ATTR_ENABLED_NAME);
    if (attr != null) {
      enabled   = Boolean.parseBoolean(attr.getValue());
    }
    attr = root.getAttribute(RIGIDBODY_ATTR_FINITEROTATIONAXIS_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      finiteRotationAxis0 = new SFFloat(fa[0], null, null);
      finiteRotationAxis1 = new SFFloat(fa[1], null, null);
      finiteRotationAxis2 = new SFFloat(fa[2], null, null);
    }
    attr = root.getAttribute(RIGIDBODY_ATTR_FIXED_NAME);
    if (attr != null) {
      fixed   = Boolean.parseBoolean(attr.getValue());
    }
    attr = root.getAttribute(RIGIDBODY_ATTR_FORCES_NAME);
    if (attr != null) {
      forces = attr.getValue();
    }
    attr = root.getAttribute(RIGIDBODY_ATTR_INERTIA_NAME);
    if (attr != null) {
      fa = parse9(attr.getValue());
      inertia00 = new SFFloat(fa[0], null, null);
      inertia01 = new SFFloat(fa[1], null, null);
      inertia02 = new SFFloat(fa[2], null, null);
      inertia10 = new SFFloat(fa[3], null, null);
      inertia11 = new SFFloat(fa[4], null, null);
      inertia12 = new SFFloat(fa[5], null, null);
      inertia20 = new SFFloat(fa[6], null, null);
      inertia21 = new SFFloat(fa[7], null, null);
      inertia22 = new SFFloat(fa[8], null, null);
    }
    attr = root.getAttribute(RIGIDBODY_ATTR_LINEARDAMPINGFACTOR_NAME);
    if (attr != null) {
      linearDampingFactor = new SFFloat(attr.getValue(), null, null);
    }
    attr = root.getAttribute(RIGIDBODY_ATTR_MASS_NAME);
    if (attr != null) {
      mass = new SFFloat(attr.getValue(), null, null);
    }
    attr = root.getAttribute(RIGIDBODY_ATTR_ORIENTATION_NAME);
    if (attr != null) {
      fa = parse4(attr.getValue());
      orientation0 = new SFFloat(fa[0], null, null);
      orientation1 = new SFFloat(fa[1], null, null);
      orientation2 = new SFFloat(fa[2], null, null);
      orientation3 = new SFFloat(fa[3], null, null);
    }
    attr = root.getAttribute(RIGIDBODY_ATTR_POSITION_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      position0 = new SFFloat(fa[0], null, null);
      position1 = new SFFloat(fa[1], null, null);
      position2 = new SFFloat(fa[2], null, null);
    }
    attr = root.getAttribute(RIGIDBODY_ATTR_TORQUES_NAME);
    if (attr != null) {
      torques = attr.getValue();
    }
    attr = root.getAttribute(RIGIDBODY_ATTR_USEFINITEROTATION_NAME);
    if (attr != null) {
      useFiniteRotation   = Boolean.parseBoolean(attr.getValue());
    }
    attr = root.getAttribute(RIGIDBODY_ATTR_USEGLOBALGRAVITY_NAME);
    if (attr != null) {
      useGlobalGravity    = Boolean.parseBoolean(attr.getValue());
    }
  }

  @Override
  public String createAttributes()
  {
    StringBuffer sb = new StringBuffer();
    
    if (RIGIDBODY_ATTR_ANGULARDAMPINGFACTOR_REQD ||
           (!angularDampingFactor.equals(angularDampingFactorDefault))) {
      sb.append(" ");
      sb.append(RIGIDBODY_ATTR_ANGULARDAMPINGFACTOR_NAME);
      sb.append("='");
      sb.append(angularDampingFactor);
      sb.append("'");
    }
    if (RIGIDBODY_ATTR_ANGULARVELOCITY_REQD ||
                   (!angularVelocity0.equals(angularVelocity0Default) ||
                    !angularVelocity1.equals(angularVelocity1Default) ||
                    !angularVelocity2.equals(angularVelocity2Default))) {
      sb.append(" ");
      sb.append(RIGIDBODY_ATTR_ANGULARVELOCITY_NAME);
      sb.append("='");
      sb.append(angularVelocity0);
      sb.append(" ");
      sb.append(angularVelocity1);
      sb.append(" ");
      sb.append(angularVelocity2);
      sb.append("'");
    }
    if (RIGIDBODY_ATTR_AUTODAMP_REQD || autoDamp != autoDampDefault) {
      sb.append(" ");
      sb.append(RIGIDBODY_ATTR_AUTODAMP_NAME);
      sb.append("='");
      sb.append(autoDamp);
      sb.append("'");
    }
    if (RIGIDBODY_ATTR_AUTODISABLE_REQD || autoDisable != autoDisableDefault) {
      sb.append(" ");
      sb.append(RIGIDBODY_ATTR_AUTODISABLE_NAME);
      sb.append("='");
      sb.append(autoDisable);
      sb.append("'");
    }
    if (RIGIDBODY_ATTR_CENTEROFMASS_REQD ||
                   (!centerOfMass0.equals(centerOfMass0Default) ||
                    !centerOfMass1.equals(centerOfMass1Default) ||
                    !centerOfMass2.equals(centerOfMass2Default))) {
      sb.append(" ");
      sb.append(RIGIDBODY_ATTR_CENTEROFMASS_NAME);
      sb.append("='");
      sb.append(centerOfMass0);
      sb.append(" ");
      sb.append(centerOfMass1);
      sb.append(" ");
      sb.append(centerOfMass2);
      sb.append("'");
    }
    if (RIGIDBODY_ATTR_DISABLEANGULARSPEED_REQD ||
           (!disableAngularSpeed.equals(disableAngularSpeedDefault))) {
      sb.append(" ");
      sb.append(RIGIDBODY_ATTR_DISABLEANGULARSPEED_NAME);
      sb.append("='");
      sb.append(disableAngularSpeed);
      sb.append("'");
    }
    if (RIGIDBODY_ATTR_DISABLELINEARSPEED_REQD ||
           (!disableLinearSpeed.equals(disableLinearSpeedDefault))) {
      sb.append(" ");
      sb.append(RIGIDBODY_ATTR_DISABLELINEARSPEED_NAME);
      sb.append("='");
      sb.append(disableLinearSpeed);
      sb.append("'");
    }
    if (RIGIDBODY_ATTR_DISABLETIME_REQD ||
           (!disableTime.equals(disableTimeDefault))) {
      sb.append(" ");
      sb.append(RIGIDBODY_ATTR_DISABLETIME_NAME);
      sb.append("='");
      sb.append(disableTime);
      sb.append("'");
    }
    if (RIGIDBODY_ATTR_ENABLED_REQD || enabled != enabledDefault) {
      sb.append(" ");
      sb.append(RIGIDBODY_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }
    if (RIGIDBODY_ATTR_FINITEROTATIONAXIS_REQD ||
                   (!finiteRotationAxis0.equals(finiteRotationAxis0Default) ||
                    !finiteRotationAxis1.equals(finiteRotationAxis1Default) ||
                    !finiteRotationAxis2.equals(finiteRotationAxis2Default))) {
      sb.append(" ");
      sb.append(RIGIDBODY_ATTR_FINITEROTATIONAXIS_NAME);
      sb.append("='");
      sb.append(finiteRotationAxis0);
      sb.append(" ");
      sb.append(finiteRotationAxis1);
      sb.append(" ");
      sb.append(finiteRotationAxis2);
      sb.append("'");
    }
    if (RIGIDBODY_ATTR_FIXED_REQD || fixed != fixedDefault) {
      sb.append(" ");
      sb.append(RIGIDBODY_ATTR_FIXED_NAME);
      sb.append("='");
      sb.append(fixed);
      sb.append("'");
    }
    if (RIGIDBODY_ATTR_FORCES_REQD || !forces.equalsIgnoreCase(RIGIDBODY_ATTR_FORCES_DFLT)) {      
      sb.append(" ");
      sb.append(RIGIDBODY_ATTR_FORCES_NAME);
      sb.append("='");
      sb.append(forces);
      sb.append("'");
    }
    if (RIGIDBODY_ATTR_INERTIA_REQD ||
                   (!inertia00.equals(inertia00Default) ||
                    !inertia01.equals(inertia01Default) ||
                    !inertia02.equals(inertia02Default) ||
                    !inertia10.equals(inertia10Default) ||
                    !inertia11.equals(inertia11Default) ||
                    !inertia12.equals(inertia12Default) ||
                    !inertia20.equals(inertia20Default) ||
                    !inertia21.equals(inertia21Default) ||
                    !inertia22.equals(inertia22Default))) {
      sb.append(" ");
      sb.append(RIGIDBODY_ATTR_INERTIA_NAME);
      sb.append("='");
      sb.append(inertia00);
      sb.append(" ");
      sb.append(inertia01);
      sb.append(" ");
      sb.append(inertia02);
      sb.append(" ");
      sb.append(inertia10);
      sb.append(" ");
      sb.append(inertia11);
      sb.append(" ");
      sb.append(inertia12);
      sb.append(" ");
      sb.append(inertia20);
      sb.append(" ");
      sb.append(inertia21);
      sb.append(" ");
      sb.append(inertia22);
      sb.append("'");
    }
    if (RIGIDBODY_ATTR_LINEARDAMPINGFACTOR_REQD ||
           (!linearDampingFactor.equals(linearDampingFactorDefault))) {
      sb.append(" ");
      sb.append(RIGIDBODY_ATTR_LINEARDAMPINGFACTOR_NAME);
      sb.append("='");
      sb.append(linearDampingFactor);
      sb.append("'");
    }
    if (RIGIDBODY_ATTR_LINEARVELOCITY_REQD ||
           (!linearVelocity0.equals(linearVelocity0Default) ||
            !linearVelocity1.equals(linearVelocity1Default) ||
            !linearVelocity2.equals(linearVelocity2Default))) {
      sb.append(" ");
      sb.append(RIGIDBODY_ATTR_LINEARVELOCITY_NAME);
      sb.append("='");
      sb.append(linearVelocity0);
      sb.append(" ");
      sb.append(linearVelocity1);
      sb.append(" ");
      sb.append(linearVelocity2);
      sb.append("'");
    }
    if (RIGIDBODY_ATTR_MASS_REQD ||
           (!mass.equals(massDefault))) {
      sb.append(" ");
      sb.append(RIGIDBODY_ATTR_MASS_NAME);
      sb.append("='");
      sb.append(mass);
      sb.append("'");
    }
    if (RIGIDBODY_ATTR_ORIENTATION_REQD ||
           (!orientation0.equals(orientation0Default) ||
            !orientation1.equals(orientation1Default) ||
            !orientation2.equals(orientation2Default) ||
            !orientation3.equals(orientation3Default))) {
      sb.append(" ");
      sb.append(RIGIDBODY_ATTR_ORIENTATION_NAME);
      sb.append("='");
      sb.append(orientation0);
      sb.append(" ");
      sb.append(orientation1);
      sb.append(" ");
      sb.append(orientation2);
      sb.append(" ");
      sb.append(orientation3);
      sb.append("'");
    }
    if (RIGIDBODY_ATTR_POSITION_REQD ||
           (!position0.equals(position0Default) ||
            !position1.equals(position1Default) ||
            !position2.equals(position2Default))) {
      sb.append(" ");
      sb.append(RIGIDBODY_ATTR_POSITION_NAME);
      sb.append("='");
      sb.append(position0);
      sb.append(" ");
      sb.append(position1);
      sb.append(" ");
      sb.append(position2);
      sb.append("'");
    }
    if (RIGIDBODY_ATTR_TORQUES_REQD || !torques.equalsIgnoreCase(RIGIDBODY_ATTR_TORQUES_DFLT)) {      
      sb.append(" ");
      sb.append(RIGIDBODY_ATTR_TORQUES_NAME);
      sb.append("='");
      sb.append(torques);
      sb.append("'");
    }
    if (RIGIDBODY_ATTR_USEFINITEROTATION_REQD || useFiniteRotation != useFiniteRotationDefault) {
      sb.append(" ");
      sb.append(RIGIDBODY_ATTR_USEFINITEROTATION_NAME);
      sb.append("='");
      sb.append(useFiniteRotation);
      sb.append("'");
    }
    if (RIGIDBODY_ATTR_USEGLOBALGRAVITY_REQD || useGlobalGravity != useGlobalGravityDefault) {
      sb.append(" ");
      sb.append(RIGIDBODY_ATTR_USEGLOBALGRAVITY_NAME);
      sb.append("='");
      sb.append(useGlobalGravity);
      sb.append("'");
    }

    return sb.toString();
  }

  public String getAngularDampingFactor()
  {
    return angularDampingFactor.toString();
  }

  public void setAngularDampingFactor(String bounce)
  {
    this.angularDampingFactor = new SFFloat(bounce, null, null);
  }

    public boolean isAutoDamp()
    {
        return autoDamp;
    }

    public void setAutoDamp(boolean autoDamp)
    {
        this.autoDamp = autoDamp;
    }

    public boolean isAutoDisable()
    {
        return autoDisable;
    }

    public void setAutoDisable(boolean autoDisable)
    {
        this.autoDisable = autoDisable;
    }

    public String getAngularVelocity0()
    {
        return angularVelocity0.toString();
    }

    public void setAngularVelocity0(String angularVelocity0)
    {
        this.angularVelocity0 = new SFFloat(angularVelocity0, null, null);
    }

    public String getAngularVelocity1()
    {
        return angularVelocity1.toString();
    }

    public void setAngularVelocity1(String angularVelocity1)
    {
        this.angularVelocity1 = new SFFloat(angularVelocity1, null, null);
    }

    public String getAngularVelocity2()
    {
        return angularVelocity2.toString();
    }

    public void setAngularVelocity2(String angularVelocity2)
    {
        this.angularVelocity2 = new SFFloat(angularVelocity2, null, null);
    }

  public String getCenterOfMass0()
  {
    return centerOfMass0.toString();
  }

  public void setCenterOfMass0(String centerOfMass0)
  {
    this.centerOfMass0 = new SFFloat(centerOfMass0, null, null);
  }

  public String getCenterOfMass1()
  {
    return centerOfMass1.toString();
  }

  public void setCenterOfMass1(String centerOfMass1)
  {
    this.centerOfMass1 = new SFFloat(centerOfMass1, null, null);
  }

  public String getCenterOfMass2()
  {
    return centerOfMass2.toString();
  }

  public void setCenterOfMass2(String centerOfMass2)
  {
    this.centerOfMass2 = new SFFloat(centerOfMass2, null, null);
  }

  public String getDisableAngularSpeed()
  {
    return disableAngularSpeed.toString();
  }

  public void setDisableAngularSpeed(String disableAngularSpeed)
  {
    this.disableAngularSpeed = new SFFloat(disableAngularSpeed, null, null);
  }

    public String getDisableLinearSpeed()
    {
        return disableLinearSpeed.toString();
    }

    public void setDisableLinearSpeed(String disableLinearSpeed)
    {
        this.disableLinearSpeed = new SFFloat(disableLinearSpeed, null, null);
    }

    public String getDisableTime()
    {
        return disableTime.toString();
    }

    public void setDisableTime(String disableTime)
    {
        this.disableTime = new SFFloat(disableTime, null, null);
    }

  public boolean isEnabled()
  {
    return enabled;
  }

  public void setEnabled(boolean enabled)
  {
    this.enabled = enabled;
  }

  public String getFiniteRotationAxis0()
  {
    return finiteRotationAxis0.toString();
  }

  public void setFiniteRotationAxis0(String finiteRotationAxis0)
  {
    this.finiteRotationAxis0 = new SFFloat(finiteRotationAxis0, null, null);
  }

  public String getFiniteRotationAxis1()
  {
    return finiteRotationAxis1.toString();
  }

  public void setFiniteRotationAxis1(String finiteRotationAxis1)
  {
    this.finiteRotationAxis1 = new SFFloat(finiteRotationAxis1, null, null);
  }

  public String getFiniteRotationAxis2()
  {
    return finiteRotationAxis2.toString();
  }

  public void setFiniteRotationAxis2(String finiteRotationAxis2)
  {
    this.finiteRotationAxis2 = new SFFloat(finiteRotationAxis2, null, null);
  }

    public boolean isFixed()
    {
        return fixed;
    }

    public void setFixed(boolean fixed)
    {
        this.fixed = fixed;
    }
  
  public String getForces()
  {
    return forces;
  }

  public void setForces(String forces)
  {
    this.forces = forces;
  }

  public String getInertia00()
  {
    return inertia00.toString();
  }

  public void setInertia00(String inertia00)
  {
    this.inertia00 = new SFFloat(inertia00, null, null);
  }

  public String getInertia01()
  {
    return inertia01.toString();
  }

  public void setInertia01(String inertia01)
  {
    this.inertia01 = new SFFloat(inertia01, null, null);
  }

  public String getInertia02()
  {
    return inertia02.toString();
  }

  public void setInertia02(String inertia02)
  {
    this.inertia02 = new SFFloat(inertia02, null, null);
  }

  public String getInertia10()
  {
    return inertia10.toString();
  }

  public void setInertia10(String inertia10)
  {
    this.inertia10 = new SFFloat(inertia10, null, null);
  }

  public String getInertia11()
  {
    return inertia11.toString();
  }

  public void setInertia11(String inertia11)
  {
    this.inertia11 = new SFFloat(inertia11, null, null);
  }

  public String getInertia12()
  {
    return inertia12.toString();
  }

  public void setInertia12(String inertia12)
  {
    this.inertia12 = new SFFloat(inertia12, null, null);
  }

  public String getInertia20()
  {
    return inertia20.toString();
  }

  public void setInertia20(String inertia20)
  {
    this.inertia20 = new SFFloat(inertia20, null, null);
  }

  public String getInertia21()
  {
    return inertia21.toString();
  }

  public void setInertia21(String inertia21)
  {
    this.inertia21 = new SFFloat(inertia21, null, null);
  }

  public String getInertia22()
  {
    return inertia22.toString();
  }

  public void setInertia22(String inertia22)
  {
    this.inertia22 = new SFFloat(inertia22, null, null);
  }

  public String getLinearDampingFactor()
  {
    return linearDampingFactor.toString();
  }

  public void setLinearDampingFactor(String linearDampingFactor)
  {
    this.linearDampingFactor = new SFFloat(linearDampingFactor, null, null);
  }
  
  public String getLinearVelocity0()
  {
    return linearVelocity0.toString();
  }

  public void setLinearVelocity0(String linearVelocity0)
  {
    this.linearVelocity0 = new SFFloat(linearVelocity0, null, null);
  }

  public String getLinearVelocity1()
  {
    return linearVelocity1.toString();
  }

  public void setLinearVelocity1(String linearVelocity1)
  {
    this.linearVelocity1 = new SFFloat(linearVelocity1, null, null);
  }

  public String getLinearVelocity2()
  {
    return linearVelocity2.toString();
  }

  public void setLinearVelocity2(String linearVelocity2)
  {
    this.linearVelocity2= new SFFloat(linearVelocity2, null, null);
  }

  public String getMass()
  {
    return mass.toString();
  }

  public void setMass(String mass)
  {
    this.mass = new SFFloat(mass, null, null);
  }

  public String getOrientation0()
  {
    return orientation0.toString();
  }

  public void setOrientation0(String orientation0)
  {
    this.orientation0 = new SFFloat(orientation0, null, null);
  }

  public String getOrientation1()
  {
    return orientation1.toString();
  }

  public void setOrientation1(String orientation1)
  {
    this.orientation1 = new SFFloat(orientation1, null, null);
  }

  public String getOrientation2()
  {
    return orientation2.toString();
  }

  public void setOrientation2(String orientation2)
  {
    this.orientation2= new SFFloat(orientation2, null, null);
  }

  public String getOrientation3()
  {
    return orientation3.toString();
  }

  public void setOrientation3(String orientation3)
  {
    this.orientation3= new SFFloat(orientation3, null, null);
  }

  public String getPosition0()
  {
    return position0.toString();
  }

  public void setPosition0(String position0)
  {
    this.position0 = new SFFloat(position0, null, null);
  }

  public String getPosition1()
  {
    return position1.toString();
  }

  public void setPosition1(String position1)
  {
    this.position1 = new SFFloat(position1, null, null);
  }

  public String getPosition2()
  {
    return position2.toString();
  }

  public void setPosition2(String position2)
  {
    this.position2= new SFFloat(position2, null, null);
  }

  public String getTorques()
  {
    return torques;
  }

  public void setTorques(String torques)
  {
    this.torques = torques;
  }

    public boolean isUseFiniteRotation()
    {
        return useFiniteRotation;
    }

    public void setUseFiniteRotation(boolean useFiniteRotation)
    {
        this.useFiniteRotation = useFiniteRotation;
    }

    public boolean isUseGlobalGravity()
    {
        return useGlobalGravity;
    }

    public void setUseGlobalGravity(boolean useGlobalGravity)
    {
        this.useGlobalGravity = useGlobalGravity;
    }
}