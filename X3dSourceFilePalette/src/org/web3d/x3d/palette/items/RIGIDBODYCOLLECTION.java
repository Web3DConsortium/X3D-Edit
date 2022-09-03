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
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * RIGIDBODYCOLLECTION.java
 * Created on 2 January 2009
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class RIGIDBODYCOLLECTION extends X3DChildNode
{
    
  private boolean autoDisable,       autoDisableDefault;
  private boolean enabled,           enabledDefault;
  private boolean preferAccuracy,    preferAccuracyDefault;

  private SFFloat constantForceMix,        constantForceMixDefault;
  private SFFloat contactSurfaceThickness, contactSurfaceThicknessDefault;
  private SFFloat disableAngularSpeed,     disableAngularSpeedDefault;
  private SFFloat disableLinearSpeed,      disableLinearSpeedDefault;
  private SFFloat disableTime,             disableTimeDefault;  
  private SFFloat errorCorrection,         errorCorrectionDefault;
  
  private SFFloat gravity0, gravity0Default;
  private SFFloat gravity1, gravity1Default;
  private SFFloat gravity2, gravity2Default;
  
  private SFInt32   iterations, iterationsDefault;
  
  private SFFloat maxCorrectionSpeed, maxCorrectionSpeedDefault;

  public RIGIDBODYCOLLECTION()
  {
  }

  @Override
  public String getElementName()
  {
    return RIGIDBODYCOLLECTION_ELNAME;
  }
  @Override
  public String getDefaultContainerField()
  {
    return "body1"; // body1, body2, bodies
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return RIGIDBODYCOLLECTIONCustomizer.class;
  }

  @Override
  public void initialize()
  {
    String[] fa;

    autoDisable       = autoDisableDefault       = Boolean.parseBoolean(RIGIDBODYCOLLECTION_ATTR_AUTODISABLE_DFLT);
    enabled           = enabledDefault           = Boolean.parseBoolean(RIGIDBODYCOLLECTION_ATTR_ENABLED_DFLT);
    preferAccuracy    = preferAccuracyDefault    = Boolean.parseBoolean(RIGIDBODYCOLLECTION_ATTR_PREFERACCURACY_DFLT);

    constantForceMix         = constantForceMixDefault         = new SFFloat(RIGIDBODYCOLLECTION_ATTR_CONSTANTFORCEMIX_DFLT, 0.0f, null);
    contactSurfaceThickness  = contactSurfaceThicknessDefault  = new SFFloat(RIGIDBODYCOLLECTION_ATTR_CONTACTSURFACETHICKNESS_DFLT, 0.0f, null);
    disableAngularSpeed      = disableAngularSpeedDefault      = new SFFloat(RIGIDBODYCOLLECTION_ATTR_DISABLEANGULARSPEED_DFLT, 0.0f, null);
    disableLinearSpeed       = disableLinearSpeedDefault       = new SFFloat(RIGIDBODYCOLLECTION_ATTR_DISABLELINEARSPEED_DFLT, 0.0f, null);
    disableTime              = disableTimeDefault              = new SFFloat(RIGIDBODYCOLLECTION_ATTR_DISABLETIME_DFLT, 0.0f, null);
    errorCorrection          = errorCorrectionDefault          = new SFFloat(RIGIDBODYCOLLECTION_ATTR_ERRORCORRECTION_DFLT, 0.0f, 1.0f);
    
    fa = parse3(RIGIDBODYCOLLECTION_ATTR_GRAVITY_DFLT);
    gravity0 = gravity0Default = new SFFloat(fa[0], null, null);
    gravity1 = gravity1Default = new SFFloat(fa[1], null, null);
    gravity2 = gravity2Default = new SFFloat(fa[2], null, null);
    
    iterations = iterationsDefault = new SFInt32(RIGIDBODYCOLLECTION_ATTR_ITERATIONS_DFLT);
    
    maxCorrectionSpeed = maxCorrectionSpeedDefault = new SFFloat(RIGIDBODYCOLLECTION_ATTR_MAXCORRECTIONSPEED_DFLT, -1.0f, null);

    setContent("\n\t\t<!-- bodies can be multiple RigidBody nodes -->" +
               "\n\t\t<RigidBody containerField='bodies'/>" +
               "\n\t\t<!-- joints can be multiple BallJoint, SingleAxisHingeJoint, DoubleAxisHingeJoint, MotorJoint, SliderJoint, UniversalJoint nodes -->" +
               "\n\t\t<BallJoint containerField='joints'/>\n\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;
    String[] fa;
    
    attr = root.getAttribute(RIGIDBODYCOLLECTION_ATTR_AUTODISABLE_NAME);
    if (attr != null) {
      autoDisable   = Boolean.parseBoolean(attr.getValue());
    }
    attr = root.getAttribute(RIGIDBODYCOLLECTION_ATTR_CONSTANTFORCEMIX_NAME);
    if (attr != null) {
      constantForceMix = new SFFloat(attr.getValue(), 0.0f, null);
    }
    attr = root.getAttribute(RIGIDBODYCOLLECTION_ATTR_CONTACTSURFACETHICKNESS_NAME);
    if (attr != null) {
      contactSurfaceThickness = new SFFloat(attr.getValue(), 0.0f, null);
    }
    attr = root.getAttribute(RIGIDBODYCOLLECTION_ATTR_DISABLEANGULARSPEED_NAME);
    if (attr != null)  {
      disableAngularSpeed   = new SFFloat(attr.getValue(), 0.0f, null);
    }
    attr = root.getAttribute(RIGIDBODYCOLLECTION_ATTR_DISABLELINEARSPEED_NAME);
    if (attr != null)  {
      disableLinearSpeed   = new SFFloat(attr.getValue(), 0.0f, null);
    }
    attr = root.getAttribute(RIGIDBODYCOLLECTION_ATTR_DISABLETIME_NAME);
    if (attr != null)  {
      disableTime   = new SFFloat(attr.getValue(), 0.0f, null);
    }
    attr = root.getAttribute(RIGIDBODYCOLLECTION_ATTR_ENABLED_NAME);
    if (attr != null) {
      enabled   = Boolean.parseBoolean(attr.getValue());
    }
    attr = root.getAttribute(RIGIDBODYCOLLECTION_ATTR_ERRORCORRECTION_NAME);
    if (attr != null) {
      errorCorrection = new SFFloat(attr.getValue(), 0.0f, 1.0f);
    }
    attr = root.getAttribute(RIGIDBODYCOLLECTION_ATTR_GRAVITY_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      gravity0 = new SFFloat(fa[0], null, null);
      gravity1 = new SFFloat(fa[1], null, null);
      gravity2 = new SFFloat(fa[2], null, null);
    }
    attr = root.getAttribute(RIGIDBODYCOLLECTION_ATTR_ITERATIONS_NAME);
    if (attr != null) {
      iterations = new SFInt32(attr.getValue());
    }
    attr = root.getAttribute(RIGIDBODYCOLLECTION_ATTR_MAXCORRECTIONSPEED_NAME);
    if (attr != null) {
      maxCorrectionSpeed = new SFFloat(attr.getValue(), -1.0f, null);
    }
    attr = root.getAttribute(RIGIDBODYCOLLECTION_ATTR_PREFERACCURACY_NAME);
    if (attr != null) {
      preferAccuracy   = Boolean.parseBoolean(attr.getValue());
    }
  }

  @Override
  public String createAttributes()
  {
    StringBuffer sb = new StringBuffer();
    
    if (RIGIDBODYCOLLECTION_ATTR_AUTODISABLE_REQD || autoDisable != autoDisableDefault) {
      sb.append(" ");
      sb.append(RIGIDBODYCOLLECTION_ATTR_AUTODISABLE_NAME);
      sb.append("='");
      sb.append(autoDisable);
      sb.append("'");
    }
    if (RIGIDBODYCOLLECTION_ATTR_CONSTANTFORCEMIX_REQD ||
                   (!constantForceMix.equals(constantForceMixDefault))) {
      sb.append(" ");
      sb.append(RIGIDBODYCOLLECTION_ATTR_CONSTANTFORCEMIX_NAME);
      sb.append("='");
      sb.append(constantForceMix);
      sb.append("'");
    }
    if (RIGIDBODYCOLLECTION_ATTR_CONTACTSURFACETHICKNESS_REQD ||
                   (!contactSurfaceThickness.equals(contactSurfaceThicknessDefault))) {
      sb.append(" ");
      sb.append(RIGIDBODYCOLLECTION_ATTR_CONTACTSURFACETHICKNESS_NAME);
      sb.append("='");
      sb.append(contactSurfaceThickness);
      sb.append("'");
    }
    if (RIGIDBODYCOLLECTION_ATTR_DISABLEANGULARSPEED_REQD ||
           (!disableAngularSpeed.equals(disableAngularSpeedDefault))) {
      sb.append(" ");
      sb.append(RIGIDBODYCOLLECTION_ATTR_DISABLEANGULARSPEED_NAME);
      sb.append("='");
      sb.append(disableAngularSpeed);
      sb.append("'");
    }
    if (RIGIDBODYCOLLECTION_ATTR_DISABLELINEARSPEED_REQD ||
           (!disableLinearSpeed.equals(disableLinearSpeedDefault))) {
      sb.append(" ");
      sb.append(RIGIDBODYCOLLECTION_ATTR_DISABLELINEARSPEED_NAME);
      sb.append("='");
      sb.append(disableLinearSpeed);
      sb.append("'");
    }
    if (RIGIDBODYCOLLECTION_ATTR_DISABLETIME_REQD ||
           (!disableTime.equals(disableTimeDefault))) {
      sb.append(" ");
      sb.append(RIGIDBODYCOLLECTION_ATTR_DISABLETIME_NAME);
      sb.append("='");
      sb.append(disableTime);
      sb.append("'");
    }
    if (RIGIDBODYCOLLECTION_ATTR_ENABLED_REQD || enabled != enabledDefault) {
      sb.append(" ");
      sb.append(RIGIDBODYCOLLECTION_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }
    if (RIGIDBODYCOLLECTION_ATTR_ERRORCORRECTION_REQD ||
           (!errorCorrection.equals(errorCorrectionDefault))) {
      sb.append(" ");
      sb.append(RIGIDBODYCOLLECTION_ATTR_ERRORCORRECTION_NAME);
      sb.append("='");
      sb.append(errorCorrection);
      sb.append("'");
    }
    if (RIGIDBODYCOLLECTION_ATTR_GRAVITY_REQD ||
                   (!gravity0.equals(gravity0Default) ||
                    !gravity1.equals(gravity1Default) ||
                    !gravity2.equals(gravity2Default))) {
      sb.append(" ");
      sb.append(RIGIDBODYCOLLECTION_ATTR_GRAVITY_NAME);
      sb.append("='");
      sb.append(gravity0);
      sb.append(" ");
      sb.append(gravity1);
      sb.append(" ");
      sb.append(gravity2);
      sb.append("'");
    }
    if (RIGIDBODYCOLLECTION_ATTR_ITERATIONS_REQD || !iterations.equals(iterationsDefault)) {      
      sb.append(" ");
      sb.append(RIGIDBODYCOLLECTION_ATTR_ITERATIONS_NAME);
      sb.append("='");
      sb.append(iterations);
      sb.append("'");
    }
    if (RIGIDBODYCOLLECTION_ATTR_MAXCORRECTIONSPEED_REQD ||
           (!maxCorrectionSpeed.equals(maxCorrectionSpeedDefault))) {
      sb.append(" ");
      sb.append(RIGIDBODYCOLLECTION_ATTR_MAXCORRECTIONSPEED_NAME);
      sb.append("='");
      sb.append(maxCorrectionSpeed);
      sb.append("'");
    }
    if (RIGIDBODYCOLLECTION_ATTR_PREFERACCURACY_REQD || preferAccuracy != preferAccuracyDefault) {
      sb.append(" ");
      sb.append(RIGIDBODYCOLLECTION_ATTR_PREFERACCURACY_NAME);
      sb.append("='");
      sb.append(preferAccuracy);
      sb.append("'");
    }
    return sb.toString();
  }

    public boolean isAutoDisable()
    {
        return autoDisable;
    }

    public void setAutoDisable(boolean autoDisable)
    {
        this.autoDisable = autoDisable;
    }
    
  public String getConstantForceMix()
  {
    return constantForceMix.toString();
  }

  public void setConstantForceMix(String constantForceMix)
  {
    this.constantForceMix = new SFFloat(constantForceMix, null, null);
  }
    
  public String getContactSurfaceThickness()
  {
    return contactSurfaceThickness.toString();
  }

  public void setContactSurfaceThickness(String contactSurfaceThickness)
  {
    this.contactSurfaceThickness = new SFFloat(contactSurfaceThickness, null, null);
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

  public String getErrorCorrection()
  {
    return errorCorrection.toString();
  }

  public void setErrorCorrection(String errorCorrection)
  {
    this.errorCorrection = new SFFloat(errorCorrection, null, null);
  }

  public String getGravity0()
  {
    return gravity0.toString();
  }

  public void setGravity0(String gravity0)
  {
    this.gravity0 = new SFFloat(gravity0, null, null);
  }

  public String getGravity1()
  {
    return gravity1.toString();
  }

  public void setGravity1(String gravity1)
  {
    this.gravity1 = new SFFloat(gravity1, null, null);
  }

  public String getGravity2()
  {
    return gravity2.toString();
  }

  public void setGravity2(String gravity2)
  {
    this.gravity2 = new SFFloat(gravity2, null, null);
  }
  
  public String getIterations()
  {
    return iterations.toString();
  }

  public void setIterations(String iterations)
  {
    this.iterations = new SFInt32(iterations);
  }

  public String getMaxCorrectionSpeed()
  {
    return maxCorrectionSpeed.toString();
  }

  public void setMaxCorrectionSpeed(String maxCorrectionSpeed)
  {
    this.maxCorrectionSpeed = new SFFloat(maxCorrectionSpeed, null, null);
  }

    public boolean isPreferAccuracy()
    {
        return preferAccuracy;
    }

    public void setPreferAccuracy(boolean preferAccuracy)
    {
        this.preferAccuracy = preferAccuracy;
    }
}