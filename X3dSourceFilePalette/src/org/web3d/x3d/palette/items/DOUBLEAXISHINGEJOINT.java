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
import org.web3d.x3d.types.X3DRigidJointNode;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * DOUBLEAXISHINGEJOINT.java
 * Created on 4 January 2009
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class DOUBLEAXISHINGEJOINT extends X3DRigidJointNode
{
  private Vector<String> forceOutput;
  
  private SFFloat anchorPointX, anchorPointXDefault;
  private SFFloat anchorPointY, anchorPointYDefault;
  private SFFloat anchorPointZ, anchorPointZDefault;
  
  private SFFloat axis1X, axis1XDefault;
  private SFFloat axis1Y, axis1YDefault;
  private SFFloat axis1Z, axis1ZDefault;
  private SFFloat axis2X, axis2XDefault;
  private SFFloat axis2Y, axis2YDefault;
  private SFFloat axis2Z, axis2ZDefault;
  
  private SFFloat desiredAngularVelocity1, desiredAngularVelocity1Default;
  private SFFloat desiredAngularVelocity2, desiredAngularVelocity2Default;
  
  private SFFloat maxAngle1,  maxAngle1Default;
  private SFFloat maxTorque1, maxTorque1Default;
  private SFFloat maxTorque2, maxTorque2Default;
  private SFFloat minAngle1,  minAngle1Default;
  
  private SFFloat stop1Bounce,               stop1BounceDefault;
  private SFFloat stop1ConstantForceMix,     stop1ConstantForceMixDefault;
  private SFFloat stop1ErrorCorrection,      stop1ErrorCorrectionDefault;
  private SFFloat suspensionErrorCorrection, suspensionErrorCorrectionDefault;
  private SFFloat suspensionForce,           suspensionForceDefault;
  
  public DOUBLEAXISHINGEJOINT()
  {
  }

  @Override
  public String getElementName()
  {
    return DOUBLEAXISHINGEJOINT_ELNAME;
  }

  @Override
  public void initialize()
  {
    forceOutput = new Vector<String>();
    if(DOUBLEAXISHINGEJOINT_ATTR_FORCEOUTPUT_DFLT.length()>0)
    {
      forceOutput.add(DOUBLEAXISHINGEJOINT_ATTR_FORCEOUTPUT_DFLT); // assumes singleton, which is OK
    }

    String[] fa = parse3(DOUBLEAXISHINGEJOINT_ATTR_ANCHORPOINT_DFLT);
    anchorPointX       = anchorPointXDefault       = new SFFloat(fa[0], null, null);
    anchorPointY       = anchorPointYDefault       = new SFFloat(fa[1], null, null);
    anchorPointZ       = anchorPointZDefault       = new SFFloat(fa[2], null, null);
    
    fa = parse3(DOUBLEAXISHINGEJOINT_ATTR_AXIS1_DFLT);
    axis1X = axis1XDefault = new SFFloat(fa[0], null, null);
    axis1Y = axis1YDefault = new SFFloat(fa[1],null,null);
    axis1Z = axis1ZDefault = new SFFloat(fa[2],null,null);
    fa = parse3(DOUBLEAXISHINGEJOINT_ATTR_AXIS2_DFLT);
    axis2X = axis2XDefault = new SFFloat(fa[0], null, null);
    axis2Y = axis2YDefault = new SFFloat(fa[1],null,null);
    axis2Z = axis2ZDefault = new SFFloat(fa[2],null,null);
    
    desiredAngularVelocity1  = desiredAngularVelocity1Default = new SFFloat(DOUBLEAXISHINGEJOINT_ATTR_DESIREDANGULARVELOCITY1_DFLT, null, null);
    desiredAngularVelocity2  = desiredAngularVelocity2Default = new SFFloat(DOUBLEAXISHINGEJOINT_ATTR_DESIREDANGULARVELOCITY2_DFLT, null, null);
    
    maxAngle1  = maxAngle1Default  = new SFFloat(DOUBLEAXISHINGEJOINT_ATTR_MAXANGLE1_DFLT, -3.1416f, 3.1416f);
    maxTorque1 = maxTorque1Default = new SFFloat(DOUBLEAXISHINGEJOINT_ATTR_MAXTORQUE1_DFLT, null, null);
    maxTorque2 = maxTorque2Default = new SFFloat(DOUBLEAXISHINGEJOINT_ATTR_MAXTORQUE2_DFLT, null, null);
    minAngle1  = minAngle1Default  = new SFFloat(DOUBLEAXISHINGEJOINT_ATTR_MINANGLE1_DFLT, -3.1416f, 3.1416f);
    
    stop1Bounce               = stop1BounceDefault               = new SFFloat(DOUBLEAXISHINGEJOINT_ATTR_STOP1BOUNCE_DFLT, 0.0f, 1.0f);
    stop1ConstantForceMix     = stop1ConstantForceMixDefault     = new SFFloat(DOUBLEAXISHINGEJOINT_ATTR_STOP1CONSTANTFORCEMIX_DFLT, 0.0f, null);
    stop1ErrorCorrection      = stop1ErrorCorrectionDefault      = new SFFloat(DOUBLEAXISHINGEJOINT_ATTR_STOP1ERRORCORRECTION_DFLT, 0.0f, 1.0f);
    suspensionErrorCorrection = suspensionErrorCorrectionDefault = new SFFloat(DOUBLEAXISHINGEJOINT_ATTR_SUSPENSIONERRORCORRECTION_DFLT, 0.0f, 1.0f);
    suspensionForce           = suspensionForceDefault           = new SFFloat(DOUBLEAXISHINGEJOINT_ATTR_SUSPENSIONFORCE_DFLT, 0.0f, null);
    
    setContent("\n\t\t<RigidBody containerField='body1'/>" +
               "\n\t\t<RigidBody containerField='body2'/>\n\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    
    org.jdom.Attribute attr = root.getAttribute(DOUBLEAXISHINGEJOINT_ATTR_FORCEOUTPUT_NAME);
    if(attr != null) {
      forceOutput = new Vector<String>();
      forceOutput.add(attr.getValue());
    }
    attr = root.getAttribute(DOUBLEAXISHINGEJOINT_ATTR_ANCHORPOINT_NAME);
    if (attr != null) {
      String[] fa = parse3(attr.getValue());
      anchorPointX = new SFFloat(fa[0], 0.0f, null, true);
      anchorPointY = new SFFloat(fa[1], 0.0f, null, true);
      anchorPointZ = new SFFloat(fa[2], 0.0f, null, true);
    }
    attr = root.getAttribute(DOUBLEAXISHINGEJOINT_ATTR_AXIS1_NAME);
    if (attr != null) {
      String[] fa = parse3(attr.getValue());
      axis1X = new SFFloat(fa[0], 0.0f, null, true);
      axis1Y = new SFFloat(fa[1], 0.0f, null, true);
      axis1Z = new SFFloat(fa[2], 0.0f, null, true);
    }
    attr = root.getAttribute(DOUBLEAXISHINGEJOINT_ATTR_AXIS2_NAME);
    if (attr != null) {
      String[] fa = parse3(attr.getValue());
      axis2X = new SFFloat(fa[0], 0.0f, null, true);
      axis2Y = new SFFloat(fa[1], 0.0f, null, true);
      axis2Z = new SFFloat(fa[2], 0.0f, null, true);
    }
    
    attr = root.getAttribute(DOUBLEAXISHINGEJOINT_ATTR_DESIREDANGULARVELOCITY1_NAME);
    if(attr != null)
        desiredAngularVelocity1 = new SFFloat(attr.getValue(), null, null);
    
    attr = root.getAttribute(DOUBLEAXISHINGEJOINT_ATTR_DESIREDANGULARVELOCITY2_NAME);
    if(attr != null)
        desiredAngularVelocity2 = new SFFloat(attr.getValue(), null, null);
    
    attr = root.getAttribute(DOUBLEAXISHINGEJOINT_ATTR_MAXANGLE1_NAME);
    if(attr != null)
        maxAngle1 = new SFFloat(attr.getValue(), -3.1416f, 3.1416f);
    
    attr = root.getAttribute(DOUBLEAXISHINGEJOINT_ATTR_MAXTORQUE1_NAME);
    if(attr != null)
        maxTorque1 = new SFFloat(attr.getValue(), null, null);
    
    attr = root.getAttribute(DOUBLEAXISHINGEJOINT_ATTR_MAXTORQUE2_NAME);
    if(attr != null)
        maxTorque2 = new SFFloat(attr.getValue(), null, null);
    
    attr = root.getAttribute(DOUBLEAXISHINGEJOINT_ATTR_MINANGLE1_NAME);
    if(attr != null)
        minAngle1 = new SFFloat(attr.getValue(), -3.1416f, 3.1416f);
    
    attr = root.getAttribute(DOUBLEAXISHINGEJOINT_ATTR_STOP1BOUNCE_NAME);
    if(attr != null)
        stop1Bounce = new SFFloat(attr.getValue(), 0.0f, 1.0f);
    
    attr = root.getAttribute(DOUBLEAXISHINGEJOINT_ATTR_STOP1CONSTANTFORCEMIX_NAME);
    if(attr != null)
        stop1ConstantForceMix = new SFFloat(attr.getValue(), 0.0f, null);
    
    attr = root.getAttribute(DOUBLEAXISHINGEJOINT_ATTR_STOP1ERRORCORRECTION_NAME);
    if(attr != null)
        stop1ErrorCorrection = new SFFloat(attr.getValue(), 0.0f, 1.0f);
    
    attr = root.getAttribute(DOUBLEAXISHINGEJOINT_ATTR_SUSPENSIONERRORCORRECTION_NAME);
    if(attr != null)
        suspensionErrorCorrection = new SFFloat(attr.getValue(), 0.0f, 1.0f);
    
    attr = root.getAttribute(DOUBLEAXISHINGEJOINT_ATTR_SUSPENSIONFORCE_NAME);
    if(attr != null)
        suspensionForce = new SFFloat(attr.getValue(), 0.0f, null);
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return DOUBLEAXISHINGEJOINTCustomizer.class;
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    
    if (DOUBLEAXISHINGEJOINT_ATTR_ANCHORPOINT_REQD ||
           (!anchorPointX.equals(anchorPointXDefault) ||
            !anchorPointY.equals(anchorPointYDefault) ||
            !anchorPointZ.equals(anchorPointZDefault)))
    {
      sb.append(" ");
      sb.append(DOUBLEAXISHINGEJOINT_ATTR_ANCHORPOINT_NAME);
      sb.append("='");
      sb.append(anchorPointX);
      sb.append(" ");
      sb.append(anchorPointY);
      sb.append(" ");
      sb.append(anchorPointZ);
      sb.append("'");
    }
    if (DOUBLEAXISHINGEJOINT_ATTR_AXIS1_REQD ||
           (!axis1X.equals(axis1XDefault) ||
            !axis1Y.equals(axis1YDefault) ||
            !axis1Z.equals(axis1ZDefault)))
    {
      sb.append(" ");
      sb.append(DOUBLEAXISHINGEJOINT_ATTR_AXIS1_NAME);
      sb.append("='");
      sb.append(axis1X);
      sb.append(" ");
      sb.append(axis1Y);
      sb.append(" ");
      sb.append(axis1Z);
      sb.append("'");
    }
    if (DOUBLEAXISHINGEJOINT_ATTR_AXIS2_REQD ||
           (!axis1X.equals(axis2XDefault) ||
            !axis1Y.equals(axis2YDefault) ||
            !axis1Z.equals(axis2ZDefault)))
    {
      sb.append(" ");
      sb.append(DOUBLEAXISHINGEJOINT_ATTR_AXIS2_NAME);
      sb.append("='");
      sb.append(axis2X);
      sb.append(" ");
      sb.append(axis2Y);
      sb.append(" ");
      sb.append(axis2Z);
      sb.append("'");
    }
    if (DOUBLEAXISHINGEJOINT_ATTR_DESIREDANGULARVELOCITY1_REQD || !desiredAngularVelocity1.equals(desiredAngularVelocity1Default)) {
      sb.append(" ");
      sb.append(DOUBLEAXISHINGEJOINT_ATTR_DESIREDANGULARVELOCITY1_NAME);
      sb.append("='");
      sb.append(desiredAngularVelocity1);
      sb.append("'");
    }
    if (DOUBLEAXISHINGEJOINT_ATTR_DESIREDANGULARVELOCITY2_REQD || !desiredAngularVelocity2.equals(desiredAngularVelocity2Default)) {
      sb.append(" ");
      sb.append(DOUBLEAXISHINGEJOINT_ATTR_DESIREDANGULARVELOCITY2_NAME);
      sb.append("='");
      sb.append(desiredAngularVelocity2);
      sb.append("'");
    }
    if (DOUBLEAXISHINGEJOINT_ATTR_FORCEOUTPUT_REQD || (forceOutput.size() > 0)) {
      sb.append(" ");
      sb.append(DOUBLEAXISHINGEJOINT_ATTR_FORCEOUTPUT_NAME);
      sb.append("='");
      sb.append(formatForceOutput());
      sb.append("'");
    }
    if (DOUBLEAXISHINGEJOINT_ATTR_MAXANGLE1_REQD || !maxAngle1.equals(maxAngle1Default)) {
      sb.append(" ");
      sb.append(DOUBLEAXISHINGEJOINT_ATTR_MAXANGLE1_NAME);
      sb.append("='");
      sb.append(maxAngle1);
      sb.append("'");
    }
    if (DOUBLEAXISHINGEJOINT_ATTR_MAXTORQUE1_REQD || !maxTorque1.equals(maxTorque1Default)) {
      sb.append(" ");
      sb.append(DOUBLEAXISHINGEJOINT_ATTR_MAXTORQUE1_NAME);
      sb.append("='");
      sb.append(maxTorque1);
      sb.append("'");
    }
    if (DOUBLEAXISHINGEJOINT_ATTR_MAXTORQUE2_REQD || !maxTorque2.equals(maxTorque2Default)) {
      sb.append(" ");
      sb.append(DOUBLEAXISHINGEJOINT_ATTR_MAXTORQUE2_NAME);
      sb.append("='");
      sb.append(maxTorque2);
      sb.append("'");
    }
    if (DOUBLEAXISHINGEJOINT_ATTR_MINANGLE1_REQD || !minAngle1.equals(minAngle1Default)) {
      sb.append(" ");
      sb.append(DOUBLEAXISHINGEJOINT_ATTR_MINANGLE1_NAME);
      sb.append("='");
      sb.append(minAngle1);
      sb.append("'");
    }
    if (DOUBLEAXISHINGEJOINT_ATTR_STOP1BOUNCE_REQD || !stop1Bounce.equals(stop1BounceDefault)) {
      sb.append(" ");
      sb.append(DOUBLEAXISHINGEJOINT_ATTR_STOP1BOUNCE_NAME);
      sb.append("='");
      sb.append(getStop1Bounce());
      sb.append("'");
    }
    if (DOUBLEAXISHINGEJOINT_ATTR_STOP1CONSTANTFORCEMIX_REQD || !stop1ConstantForceMix.equals(stop1ConstantForceMixDefault)) {
      sb.append(" ");
      sb.append(DOUBLEAXISHINGEJOINT_ATTR_STOP1CONSTANTFORCEMIX_NAME);
      sb.append("='");
      sb.append(getStop1ConstantForceMix());
      sb.append("'");
    }
    if (DOUBLEAXISHINGEJOINT_ATTR_STOP1ERRORCORRECTION_REQD || !stop1ErrorCorrection.equals(stop1ErrorCorrectionDefault)) {
      sb.append(" ");
      sb.append(DOUBLEAXISHINGEJOINT_ATTR_STOP1ERRORCORRECTION_NAME);
      sb.append("='");
      sb.append(getStop1ErrorCorrection());
      sb.append("'");
    }
    if (DOUBLEAXISHINGEJOINT_ATTR_SUSPENSIONERRORCORRECTION_REQD || !suspensionErrorCorrection.equals(suspensionErrorCorrectionDefault)) {
      sb.append(" ");
      sb.append(DOUBLEAXISHINGEJOINT_ATTR_SUSPENSIONERRORCORRECTION_NAME);
      sb.append("='");
      sb.append(getSuspensionErrorCorrection());
      sb.append("'");
    }
    if (DOUBLEAXISHINGEJOINT_ATTR_SUSPENSIONFORCE_REQD || !suspensionForce.equals(suspensionForceDefault)) {
      sb.append(" ");
      sb.append(DOUBLEAXISHINGEJOINT_ATTR_SUSPENSIONFORCE_NAME);
      sb.append("='");
      sb.append(getSuspensionForce());
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

        String[] sa = s.trim().split("\"\\s*\"");  // quote front and back, white between
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

    public String getAxis1X()
    {
    return axis1X.toString();
    }

    public void setAxis1X(String axisX)
    {
    this.axis1X = new SFFloat(axisX,0.0f,null,true);
    }

    public String getAxis1Y()
    {
    return axis1Y.toString();
    }

    public void setAxis1Y(String axisY)
    {
    this.axis1Y = new SFFloat(axisY,0.0f,null,true);
    }

    public String getAxis1Z()
    {
    return axis1Z.toString();
    }

    public void setAxis1Z(String axisZ)
    {
    this.axis1Z = new SFFloat(axisZ,0.0f,null,true);
    }

    public String getAxis2X()
    {
    return axis2X.toString();
    }

    public void setAxis2X(String axisX)
    {
    this.axis2X = new SFFloat(axisX,0.0f,null,true);
    }

    public String getAxis2Y()
    {
    return axis2Y.toString();
    }

    public void setAxis2Y(String axisY)
    {
    this.axis2Y = new SFFloat(axisY,0.0f,null,true);
    }

    public String getAxis2Z()
    {
    return axis2Z.toString();
    }

    public void setAxis2Z(String axisZ)
    {
    this.axis2Z = new SFFloat(axisZ,0.0f,null,true);
    }
    
    public String getAnchorPointX()
    {
        return anchorPointX.toString();
    }

    public void setAnchorPointX(String anchorPointX)
    {
        this.anchorPointX = new SFFloat(anchorPointX,1.0f,null,true);
    }

    public String getAnchorPointY()
    {
        return anchorPointY.toString();
    }

    public void setAnchorPointY(String anchorPointY)
    {
        this.anchorPointY = new SFFloat(anchorPointY,1.0f,null,true);
    }

    public String getAnchorPointZ()
    {
        return anchorPointZ.toString();
    }

    public void setAnchorPointZ(String anchorPointZ)
    {
        this.anchorPointZ = new SFFloat(anchorPointZ,0.0f,null,true);
    }

    public String getDesiredAngularVelocity1()
    {
        return desiredAngularVelocity1.toString();
    }

    public void setDesiredAngularVelocity1(String desiredAngularVelocity1)
    {
        this.desiredAngularVelocity1 = new SFFloat(desiredAngularVelocity1,1.0f,null,true);
    }

    public String getDesiredAngularVelocity2()
    {
        return desiredAngularVelocity2.toString();
    }

    public void setDesiredAngularVelocity2(String desiredAngularVelocity2)
    {
        this.desiredAngularVelocity2 = new SFFloat(desiredAngularVelocity2,1.0f,null,true);
    }

    public String getMaxAngle1()
    {
        return maxAngle1.toString();
    }

    public void setMaxAngle1(String maxAngle1)
    {
        this.maxAngle1 = new SFFloat(maxAngle1,1.0f,null,true);
    }

    public String getMaxTorque1()
    {
        return maxTorque1.toString();
    }

    public void setMaxTorque1(String maxTorque1)
    {
        this.maxTorque1 = new SFFloat(maxTorque1,1.0f,null,true);
    }

    public String getMaxTorque2()
    {
        return maxTorque2.toString();
    }

    public void setMaxTorque2(String maxTorque2)
    {
        this.maxTorque2 = new SFFloat(maxTorque2,1.0f,null,true);
    }
    
    public String getMinAngle1()
    {
        return minAngle1.toString();
    }

    public void setMinAngle1(String minAngle1)
    {
        this.minAngle1 = new SFFloat(minAngle1,1.0f,null,true);
    }

    public String getStop1Bounce()
    {
        return stop1Bounce.toString();
    }

    public void setStop1Bounce(String stopBounce)
    {
        this.stop1Bounce = new SFFloat(stopBounce,1.0f,null,true);
    }

    public String getStop1ConstantForceMix()
    {
        return stop1ConstantForceMix.toString();
    }

    public void setStop1ConstantForceMix(String stop1ConstantForceMix)
    {
        this.stop1ConstantForceMix = new SFFloat(stop1ConstantForceMix,1.0f,null,true);
    }

    public String getStop1ErrorCorrection()
    {
        return stop1ErrorCorrection.toString();
    }

    public void setStop1ErrorCorrection(String stopErrorCorrection)
    {
        this.stop1ErrorCorrection = new SFFloat(stopErrorCorrection,1.0f,null,true);
    }

    public String getSuspensionErrorCorrection()
    {
        return suspensionErrorCorrection.toString();
    }

    public void setSuspensionErrorCorrection(String suspensionErrorCorrection)
    {
        this.suspensionErrorCorrection = new SFFloat(suspensionErrorCorrection,1.0f,null,true);
    }

    public String getSuspensionForce()
    {
        return suspensionForce.toString();
    }

    public void setSuspensionForce(String suspensionForce)
    {
        this.suspensionForce = new SFFloat(suspensionForce,1.0f,null,true);
    }
}
