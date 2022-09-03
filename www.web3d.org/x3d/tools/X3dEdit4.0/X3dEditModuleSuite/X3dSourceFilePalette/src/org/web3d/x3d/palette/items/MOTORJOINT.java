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

import java.util.Vector;
import javax.swing.text.JTextComponent;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFFloat;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFInt32;
import static org.web3d.x3d.types.X3DPrimitiveTypes.radiansFormat;
import org.web3d.x3d.types.X3DRigidJointNode;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * MOTORJOINT.java
 * Created on December 23, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class MOTORJOINT extends X3DRigidJointNode
{
  private Vector<String> forceOutput;
  private SFInt32          enabledAxes, enabledAxesDefault;
  private boolean        autoCalc;
  
  private SFFloat motor1AxisX, motor1AxisXDefault;
  private SFFloat motor1AxisY, motor1AxisYDefault;
  private SFFloat motor1AxisZ, motor1AxisZDefault;
  private SFFloat motor2AxisX, motor2AxisXDefault;
  private SFFloat motor2AxisY, motor2AxisYDefault;
  private SFFloat motor2AxisZ, motor2AxisZDefault;
  private SFFloat motor3AxisX, motor3AxisXDefault;
  private SFFloat motor3AxisY, motor3AxisYDefault;
  private SFFloat motor3AxisZ, motor3AxisZDefault;
  
  private SFFloat axis1Angle,  axis1AngleDefault;
  private SFFloat axis2Angle,  axis2AngleDefault;
  private SFFloat axis3Angle,  axis3AngleDefault;
  private SFFloat axis1Torque, axis1TorqueDefault;
  private SFFloat axis2Torque, axis2TorqueDefault;
  private SFFloat axis3Torque, axis3TorqueDefault;
  private SFFloat stop1Bounce, stop1BounceDefault;
  private SFFloat stop2Bounce, stop2BounceDefault;
  private SFFloat stop3Bounce, stop3BounceDefault;
  private SFFloat stop1ErrorCorrection, stop1ErrorCorrectionDefault;
  private SFFloat stop2ErrorCorrection, stop2ErrorCorrectionDefault;
  private SFFloat stop3ErrorCorrection, stop3ErrorCorrectionDefault;
  
  public MOTORJOINT()
  {
  }

  @Override
  public String getElementName()
  {
    return MOTORJOINT_ELNAME;
  }

  @Override
  public void initialize()
  {
    forceOutput = new Vector<String>();
    if(MOTORJOINT_ATTR_FORCEOUTPUT_DFLT.length()>0)
    {
      forceOutput.add(MOTORJOINT_ATTR_FORCEOUTPUT_DFLT); // assumes singleton, which is OK
    }
    enabledAxes = enabledAxesDefault = new SFInt32(MOTORJOINT_ATTR_ENABLEDAXES_DFLT);
        setAutoCalc(Boolean.parseBoolean(MOTORJOINT_ATTR_AUTOCALC_DFLT));

    String[] fa = parse3(MOTORJOINT_ATTR_MOTOR1AXIS_DFLT);
    motor1AxisX = motor1AxisXDefault = new SFFloat(fa[0], null, null);
    motor1AxisY = motor1AxisYDefault = new SFFloat(fa[1],null,null);
    motor1AxisZ = motor1AxisZDefault = new SFFloat(fa[2],null,null);
    fa = parse3(MOTORJOINT_ATTR_MOTOR2AXIS_DFLT);
    motor2AxisX = motor2AxisXDefault = new SFFloat(fa[0], null, null);
    motor2AxisY = motor2AxisYDefault = new SFFloat(fa[1],null,null);
    motor2AxisZ = motor2AxisZDefault = new SFFloat(fa[2],null,null);
    fa = parse3(MOTORJOINT_ATTR_MOTOR3AXIS_DFLT);
    motor3AxisX = motor3AxisXDefault = new SFFloat(fa[0], null, null);
    motor3AxisY = motor3AxisYDefault = new SFFloat(fa[1],null,null);
    motor3AxisZ = motor3AxisZDefault = new SFFloat(fa[2],null,null);
    
    axis1Angle       = axis1AngleDefault       = new SFFloat(MOTORJOINT_ATTR_AXIS1ANGLE_DFLT, -3.1416f, 3.1416f);
    axis2Angle       = axis2AngleDefault       = new SFFloat(MOTORJOINT_ATTR_AXIS2ANGLE_DFLT, -3.1416f, 3.1416f);
    axis3Angle       = axis3AngleDefault       = new SFFloat(MOTORJOINT_ATTR_AXIS3ANGLE_DFLT, -3.1416f, 3.1416f);
    axis1Torque      = axis1TorqueDefault      = new SFFloat(MOTORJOINT_ATTR_AXIS1TORQUE_DFLT, 0.0f, null);
    axis2Torque      = axis2TorqueDefault      = new SFFloat(MOTORJOINT_ATTR_AXIS2TORQUE_DFLT, 0.0f, null);
    axis3Torque      = axis3TorqueDefault      = new SFFloat(MOTORJOINT_ATTR_AXIS3TORQUE_DFLT, 0.0f, null);
    stop1Bounce      = stop1BounceDefault      = new SFFloat(MOTORJOINT_ATTR_STOP1BOUNCE_DFLT, 0.0f, 1.0f);
    stop2Bounce      = stop2BounceDefault      = new SFFloat(MOTORJOINT_ATTR_STOP2BOUNCE_DFLT, 0.0f, 1.0f);
    stop3Bounce      = stop3BounceDefault      = new SFFloat(MOTORJOINT_ATTR_STOP3BOUNCE_DFLT, 0.0f, 1.0f);
    stop1ErrorCorrection = stop1ErrorCorrectionDefault = new SFFloat(MOTORJOINT_ATTR_STOP1ERRORCORRECTION_DFLT, 0.0f, 1.0f);
    stop2ErrorCorrection = stop2ErrorCorrectionDefault = new SFFloat(MOTORJOINT_ATTR_STOP2ERRORCORRECTION_DFLT, 0.0f, 1.0f);
    stop3ErrorCorrection = stop3ErrorCorrectionDefault = new SFFloat(MOTORJOINT_ATTR_STOP3ERRORCORRECTION_DFLT, 0.0f, 1.0f);

    setContent("\n\t\t<RigidBody containerField='body1'/>" +
               "\n\t\t<RigidBody containerField='body2'/>\n\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    
    org.jdom.Attribute attr = root.getAttribute(MOTORJOINT_ATTR_FORCEOUTPUT_NAME);
    if(attr != null) {
      forceOutput = new Vector<String>();
      forceOutput.add(attr.getValue());
    }

    attr = root.getAttribute(MOTORJOINT_ATTR_AUTOCALC_NAME);
    if(attr != null)
        setAutoCalc(Boolean.parseBoolean(attr.getValue()));
    
    attr = root.getAttribute(MOTORJOINT_ATTR_ENABLEDAXES_NAME);
    if(attr != null)
        enabledAxes = new SFInt32(attr.getValue(), 0, null);
    
    attr = root.getAttribute(MOTORJOINT_ATTR_MOTOR1AXIS_NAME);
    if (attr != null) {
      String[] fa = parse3(attr.getValue());
      motor1AxisX = new SFFloat(fa[0], 0.0f, null, true);
      motor1AxisY = new SFFloat(fa[1], 0.0f, null, true);
      motor1AxisZ = new SFFloat(fa[2], 0.0f, null, true);
    }
    attr = root.getAttribute(MOTORJOINT_ATTR_MOTOR2AXIS_NAME);
    if (attr != null) {
      String[] fa = parse3(attr.getValue());
      motor2AxisX = new SFFloat(fa[0], 0.0f, null, true);
      motor2AxisY = new SFFloat(fa[1], 0.0f, null, true);
      motor2AxisZ = new SFFloat(fa[2], 0.0f, null, true);
    }
    attr = root.getAttribute(MOTORJOINT_ATTR_MOTOR3AXIS_NAME);
    if (attr != null) {
      String[] fa = parse3(attr.getValue());
      motor3AxisX = new SFFloat(fa[0], 0.0f, null, true);
      motor3AxisY = new SFFloat(fa[1], 0.0f, null, true);
      motor3AxisZ = new SFFloat(fa[2], 0.0f, null, true);
    }
    attr = root.getAttribute(MOTORJOINT_ATTR_AXIS1ANGLE_NAME);
    if(attr != null)
        axis1Angle = new SFFloat(attr.getValue(), -3.1416f, 3.1416f);
    
    attr = root.getAttribute(MOTORJOINT_ATTR_AXIS2ANGLE_NAME);
    if(attr != null)
        axis2Angle = new SFFloat(attr.getValue(), -3.1416f, 3.1416f);
    
    attr = root.getAttribute(MOTORJOINT_ATTR_AXIS3ANGLE_NAME);
    if(attr != null)
        axis3Angle = new SFFloat(attr.getValue(), -3.1416f, 3.1416f);
    
    attr = root.getAttribute(MOTORJOINT_ATTR_AXIS1TORQUE_NAME);
    if(attr != null)
        axis1Torque = new SFFloat(attr.getValue(), 0.0f, null);
    
    attr = root.getAttribute(MOTORJOINT_ATTR_AXIS2TORQUE_NAME);
    if(attr != null)
        axis2Torque = new SFFloat(attr.getValue(), 0.0f, null);
    
    attr = root.getAttribute(MOTORJOINT_ATTR_AXIS3TORQUE_NAME);
    if(attr != null)
        axis3Torque = new SFFloat(attr.getValue(), 0.0f, null);
    
    attr = root.getAttribute(MOTORJOINT_ATTR_STOP1BOUNCE_NAME);
    if(attr != null)
        stop1Bounce = new SFFloat(attr.getValue(), 0.0f, 1.0f);
    
    attr = root.getAttribute(MOTORJOINT_ATTR_STOP2BOUNCE_NAME);
    if(attr != null)
        stop2Bounce = new SFFloat(attr.getValue(), 0.0f, 1.0f);
    
    attr = root.getAttribute(MOTORJOINT_ATTR_STOP3BOUNCE_NAME);
    if(attr != null)
        stop3Bounce = new SFFloat(attr.getValue(), 0.0f, 1.0f);
    
    attr = root.getAttribute(MOTORJOINT_ATTR_STOP1ERRORCORRECTION_NAME);
    if(attr != null)
        stop1ErrorCorrection = new SFFloat(attr.getValue(), 0.0f, 1.0f);
    
    attr = root.getAttribute(MOTORJOINT_ATTR_STOP2ERRORCORRECTION_NAME);
    if(attr != null)
        stop2ErrorCorrection = new SFFloat(attr.getValue(), 0.0f, 1.0f);
    
    attr = root.getAttribute(MOTORJOINT_ATTR_STOP3ERRORCORRECTION_NAME);
    if(attr != null)
        stop3ErrorCorrection = new SFFloat(attr.getValue(), 0.0f, 1.0f);
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return MOTORJOINTCustomizer.class;
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (MOTORJOINT_ATTR_AUTOCALC_REQD || (isAutoCalc()!= Boolean.parseBoolean(MOTORJOINT_ATTR_AUTOCALC_DFLT))) {
      sb.append(" ");
      sb.append(MOTORJOINT_ATTR_AUTOCALC_NAME);
      sb.append("='");
      sb.append(isAutoCalc());
      sb.append("'");
    }
    if (MOTORJOINT_ATTR_ENABLEDAXES_REQD || (enabledAxes.equals(enabledAxesDefault))) {
      sb.append(" ");
      sb.append(MOTORJOINT_ATTR_ENABLEDAXES_NAME);
      sb.append("='");
      sb.append(getEnabledAxes());
      sb.append("'");
    }
    if (MOTORJOINT_ATTR_FORCEOUTPUT_REQD || (forceOutput.size() > 0)) {
      sb.append(" ");
      sb.append(MOTORJOINT_ATTR_FORCEOUTPUT_NAME);
      sb.append("='");
      sb.append(formatForceOutput());
      sb.append("'");
    }
    if (MOTORJOINT_ATTR_MOTOR1AXIS_REQD ||
           (!motor1AxisX.equals(motor1AxisXDefault) ||
            !motor1AxisY.equals(motor1AxisYDefault) ||
            !motor1AxisZ.equals(motor1AxisZDefault)))
    {
      sb.append(" ");
      sb.append(MOTORJOINT_ATTR_MOTOR1AXIS_NAME);
      sb.append("='");
      sb.append(motor1AxisX);
      sb.append(" ");
      sb.append(motor1AxisY);
      sb.append(" ");
      sb.append(motor1AxisZ);
      sb.append("'");
    }
    if (MOTORJOINT_ATTR_MOTOR2AXIS_REQD ||
           (!motor1AxisX.equals(motor2AxisXDefault) ||
            !motor1AxisY.equals(motor2AxisYDefault) ||
            !motor1AxisZ.equals(motor2AxisZDefault)))
    {
      sb.append(" ");
      sb.append(MOTORJOINT_ATTR_MOTOR2AXIS_NAME);
      sb.append("='");
      sb.append(motor2AxisX);
      sb.append(" ");
      sb.append(motor2AxisY);
      sb.append(" ");
      sb.append(motor2AxisZ);
      sb.append("'");
    }
    if (MOTORJOINT_ATTR_MOTOR3AXIS_REQD ||
           (!motor3AxisX.equals(motor3AxisXDefault) ||
            !motor3AxisY.equals(motor3AxisYDefault) ||
            !motor3AxisZ.equals(motor3AxisZDefault)))
    {
      sb.append(" ");
      sb.append(MOTORJOINT_ATTR_MOTOR3AXIS_NAME);
      sb.append("='");
      sb.append(motor3AxisX);
      sb.append(" ");
      sb.append(motor3AxisY);
      sb.append(" ");
      sb.append(motor3AxisZ);
      sb.append("'");
    }
    if (MOTORJOINT_ATTR_AXIS1ANGLE_REQD || !axis1Angle.equals(axis1AngleDefault)) {
      sb.append(" ");
      sb.append(MOTORJOINT_ATTR_AXIS1ANGLE_NAME);
      sb.append("='");
      sb.append(getAxis1Angle());
      sb.append("'");
    }
    if (MOTORJOINT_ATTR_AXIS2ANGLE_REQD || !axis2Angle.equals(axis2AngleDefault)) {
      sb.append(" ");
      sb.append(MOTORJOINT_ATTR_AXIS2ANGLE_NAME);
      sb.append("='");
      sb.append(getAxis2Angle());
      sb.append("'");
    }
    if (MOTORJOINT_ATTR_AXIS3ANGLE_REQD || !axis3Angle.equals(axis3AngleDefault)) {
      sb.append(" ");
      sb.append(MOTORJOINT_ATTR_AXIS3ANGLE_NAME);
      sb.append("='");
      sb.append(getAxis3Angle());
      sb.append("'");
    }
    if (MOTORJOINT_ATTR_AXIS1TORQUE_REQD || !axis1Torque.equals(axis1TorqueDefault)) {
      sb.append(" ");
      sb.append(MOTORJOINT_ATTR_AXIS1TORQUE_NAME);
      sb.append("='");
      sb.append(getAxis1Torque());
      sb.append("'");
    }
    if (MOTORJOINT_ATTR_AXIS2TORQUE_REQD || !axis2Torque.equals(axis2TorqueDefault)) {
      sb.append(" ");
      sb.append(MOTORJOINT_ATTR_AXIS2TORQUE_NAME);
      sb.append("='");
      sb.append(getAxis2Torque());
      sb.append("'");
    }
    if (MOTORJOINT_ATTR_AXIS3TORQUE_REQD || !axis3Torque.equals(axis3TorqueDefault)) {
      sb.append(" ");
      sb.append(MOTORJOINT_ATTR_AXIS3TORQUE_NAME);
      sb.append("='");
      sb.append(getAxis3Torque());
      sb.append("'");
    }
    if (MOTORJOINT_ATTR_STOP1BOUNCE_REQD || !stop1Bounce.equals(stop1BounceDefault)) {
      sb.append(" ");
      sb.append(MOTORJOINT_ATTR_STOP1BOUNCE_NAME);
      sb.append("='");
      sb.append(getStop1Bounce());
      sb.append("'");
    }
    if (MOTORJOINT_ATTR_STOP2BOUNCE_REQD || !stop2Bounce.equals(stop2BounceDefault)) {
      sb.append(" ");
      sb.append(MOTORJOINT_ATTR_STOP2BOUNCE_NAME);
      sb.append("='");
      sb.append(getStop2Bounce());
      sb.append("'");
    }
    if (MOTORJOINT_ATTR_STOP3BOUNCE_REQD || !stop1Bounce.equals(stop3BounceDefault)) {
      sb.append(" ");
      sb.append(MOTORJOINT_ATTR_STOP3BOUNCE_NAME);
      sb.append("='");
      sb.append(getStop3Bounce());
      sb.append("'");
    }
    if (MOTORJOINT_ATTR_STOP1ERRORCORRECTION_REQD || !stop1ErrorCorrection.equals(stop1ErrorCorrectionDefault)) {
      sb.append(" ");
      sb.append(MOTORJOINT_ATTR_STOP1ERRORCORRECTION_NAME);
      sb.append("='");
      sb.append(getStop1ErrorCorrection());
      sb.append("'");
    }
    if (MOTORJOINT_ATTR_STOP2ERRORCORRECTION_REQD || !stop2ErrorCorrection.equals(stop2ErrorCorrectionDefault)) {
      sb.append(" ");
      sb.append(MOTORJOINT_ATTR_STOP2ERRORCORRECTION_NAME);
      sb.append("='");
      sb.append(getStop2ErrorCorrection());
      sb.append("'");
    }
    if (MOTORJOINT_ATTR_STOP3ERRORCORRECTION_REQD || !stop3ErrorCorrection.equals(stop3ErrorCorrectionDefault)) {
      sb.append(" ");
      sb.append(MOTORJOINT_ATTR_STOP3ERRORCORRECTION_NAME);
      sb.append("='");
      sb.append(getStop3ErrorCorrection());
      sb.append("'");
    }
    return sb.toString();
  }

  public String getUnformattedForceOutput()
  {
    StringBuilder sb = new StringBuilder();

    // convert Vector<String> (named string, in this class) to single StringBuffer holding the MFString array literals
    for(String s : forceOutput) {
      // convert ' character into &apos; character entity
      StringBuffer s2 = new StringBuffer();
      for (int i=0; i < s.length(); i++) // skip first and last ' characters
      {
          // do not escape inputs, that is done already by XML parser on incoming string
            
          s2.append (s.substring(i, i+1));
      }
      // only wrap in double quotes if more than one string element, and then only if quotes not already there 
      if ((forceOutput.size() == 1) ||
          ((s2.substring(0, 0).compareTo("\"") == 0) && (s2.substring(s2.length()-1, s2.length()-1).compareTo("\"") == 0)))
        sb.append(s2);
      else {
        sb.append("\"");
        sb.append(s2);
        sb.append("\"");
      }
      sb.append(" "); // separate next SFString subelement
    }
    return sb.toString().trim();
  }

  private String formatForceOutput() // format output
  {
    StringBuilder sb = new StringBuilder();

    // convert Vector<String> (named string, in this class) to single StringBuffer holding the MFString array literals
    for(String s : forceOutput) {
      // convert ' character into &apos; character entity
      StringBuffer s2 = new StringBuffer();
      for (int i=0; i < s.length(); i++) // skip first and last ' characters
      {
        if (s.substring(i, i+1).compareTo("'") == 0)
             s2.append("&apos;");
        // escape ampersands
        else if (s.substring(i, i+1).compareTo("&") == 0)
             s2.append("&amp;");
        else s2.append (s.substring(i, i+1));
      }
      // now convert special characters to character entities
      // https://htmlentities.dev.java.net/source/browse/htmlentities/src/com/tecnick/htmlutils/htmlentities/sample/HTMLEntitiesSample.java?view=markup
// TODO add .jar to path, bug #1742 https://www.movesinstitute.org/bugzilla/show_bug.cgi?id=1742
//    StringBuffer s3 = HTMLEntities.htmlentities(s2);
      StringBuffer s3 = s2;
      
      // only wrap in double quotes if more than one string element, and then only if quotes not already there 
      if ((forceOutput.size() == 1) ||
          ((s2.substring(0, 0).compareTo("\"") == 0) && (s2.substring(s2.length()-1, s2.length()-1).compareTo("\"") == 0)))
        sb.append(s3);
      else {
        sb.append("\"");
        sb.append(s3);
        sb.append("\"");
      }
      sb.append(" "); // separate next SFString subelement
    }
    return sb.toString().trim();
  }

  private Vector<String> parseForceOutput(String s) // parse input
  {
    Vector<String> v = new Vector<String>();
    s = s.trim();
    if(s.length()>0) {
      if(!s.startsWith("\""))
        v.add(s);
      else {
        s = s.substring(1);
        if(s.endsWith("\"")); // TODO why is this if condition terminated by null semicolon ; ? see TEXT.java
        s = s.substring(0,s.length()-1);

        String[] sa = s.replace(',', ' ').trim().split("\"\\s*\"");  // quote front and back, white between
        for(String str : sa)
        {
            v.add(str);
        }
      }
    }
    return v;
  }

    public void setForceOutput(String forceOutputString)
    {
    forceOutput = parseForceOutput(forceOutputString);
    }

    public String getEnabledAxes()
    {
      return ""+enabledAxes.toString();
    }

    public void setEnabledAxes(String newEnabledAxes)
    {
      enabledAxes = new SFInt32(newEnabledAxes);
    }

    public String getMotor1AxisX()
    {
    return motor1AxisX.toString();
    }

    public void setMotor1AxisX(String axisX)
    {
    this.motor1AxisX = new SFFloat(axisX,0.0f,null,true);
    }

    public String getMotor1AxisY()
    {
    return motor1AxisY.toString();
    }

    public void setMotor1AxisY(String axisY)
    {
    this.motor1AxisY = new SFFloat(axisY,0.0f,null,true);
    }

    public String getMotor1AxisZ()
    {
    return motor1AxisZ.toString();
    }

    public void setMotor1AxisZ(String axisZ)
    {
    this.motor1AxisZ = new SFFloat(axisZ,0.0f,null,true);
    }

    public String getMotor2AxisX()
    {
    return motor2AxisX.toString();
    }

    public void setMotor2AxisX(String axisX)
    {
    this.motor2AxisX = new SFFloat(axisX,0.0f,null,true);
    }

    public String getMotor2AxisY()
    {
    return motor2AxisY.toString();
    }

    public void setMotor2AxisY(String axisY)
    {
    this.motor2AxisY = new SFFloat(axisY,0.0f,null,true);
    }

    public String getMotor2AxisZ()
    {
    return motor2AxisZ.toString();
    }

    public void setMotor2AxisZ(String axisZ)
    {
    this.motor2AxisZ = new SFFloat(axisZ,0.0f,null,true);
    }

    public String getMotor3AxisX()
    {
    return motor3AxisX.toString();
    }

    public void setMotor3AxisX(String axisX)
    {
    this.motor3AxisX = new SFFloat(axisX,0.0f,null,true);
    }

    public String getMotor3AxisY()
    {
    return motor3AxisY.toString();
    }

    public void setMotor3AxisY(String axisY)
    {
    this.motor3AxisY = new SFFloat(axisY,0.0f,null,true);
    }

    public String getMotor3AxisZ()
    {
    return motor3AxisZ.toString();
    }

    public void setMotor3AxisZ(String axisZ)
    {
    this.motor3AxisZ = new SFFloat(axisZ,0.0f,null,true);
    }

    public String getAxis1Angle()
    {
        return radiansFormat.format(axis1Angle.getValue());
    }

    public void setAxis1Angle(String newAngle)
    {
        this.axis1Angle = new SFFloat(newAngle,1.0f,null,true);
    }

    public String getAxis2Angle()
    {
        return radiansFormat.format(axis2Angle.getValue());
    }

    public void setAxis2Angle(String newAngle)
    {
        this.axis2Angle = new SFFloat(newAngle,0.0f,null,true);
    }

    public String getAxis3Angle()
    {
        return radiansFormat.format(axis3Angle.getValue());
    }

    public void setAxis3Angle(String newAngle)
    {
        this.axis3Angle = new SFFloat(newAngle,0.0f,null,true);
    }

    public String getAxis1Torque()
    {
        return axis1Torque.toString();
    }

    public void setAxis1Torque(String newTorque)
    {
        this.axis1Torque = new SFFloat(newTorque,1.0f,null,true);
    }

    public String getAxis2Torque()
    {
        return axis2Torque.toString();
    }

    public void setAxis2Torque(String newTorque)
    {
        this.axis2Torque = new SFFloat(newTorque,0.0f,null,true);
    }

    public String getAxis3Torque()
    {
        return axis3Torque.toString();
    }

    public void setAxis3Torque(String newTorque)
    {
        this.axis3Torque = new SFFloat(newTorque,0.0f,null,true);
    }

    public String getStop1Bounce()
    {
        return stop1Bounce.toString();
    }

    public void setStop1Bounce(String stopBounce)
    {
        this.stop1Bounce = new SFFloat(stopBounce,1.0f,null,true);
    }

    public String getStop2Bounce()
    {
        return stop2Bounce.toString();
    }

    public void setStop2Bounce(String stopBounce)
    {
        this.stop2Bounce = new SFFloat(stopBounce,1.0f,null,true);
    }

    public String getStop3Bounce()
    {
        return stop3Bounce.toString();
    }

    public void setStop3Bounce(String stopBounce)
    {
        this.stop3Bounce = new SFFloat(stopBounce,1.0f,null,true);
    }

    public String getStop1ErrorCorrection()
    {
        return stop1ErrorCorrection.toString();
    }

    public void setStop1ErrorCorrection(String stopErrorCorrection)
    {
        this.stop1ErrorCorrection = new SFFloat(stopErrorCorrection,1.0f,null,true);
    }

    public String getStop2ErrorCorrection()
    {
        return stop2ErrorCorrection.toString();
    }

    public void setStop2ErrorCorrection(String stopErrorCorrection)
    {
        this.stop2ErrorCorrection = new SFFloat(stopErrorCorrection,1.0f,null,true);
    }

    public String getStop3ErrorCorrection()
    {
        return stop3ErrorCorrection.toString();
    }

    public void setStop3ErrorCorrection(String stopErrorCorrection)
    {
        this.stop3ErrorCorrection = new SFFloat(stopErrorCorrection,1.0f,null,true);
    }

    public boolean isAutoCalc()
    {
        return autoCalc;
    }

    public void setAutoCalc(boolean autoCalc)
    {
        this.autoCalc = autoCalc;
    }
}
