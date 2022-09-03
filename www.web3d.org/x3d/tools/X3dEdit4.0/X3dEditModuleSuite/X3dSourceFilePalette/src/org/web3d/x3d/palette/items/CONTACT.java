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
 * CONTACT.java
 * Created on 1 January 2009
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class CONTACT extends X3DChildNode
{
  private boolean enabled, enabledDefault;
  
  private String[] appliedParameters;

  private SFFloat bounce, bounceDefault;
  private SFFloat depth, depthDefault;
  private SFFloat minBounceSpeed, minBounceSpeedDefault;

  private SFFloat contactNormal0, contactNormal0Default;
  private SFFloat contactNormal1, contactNormal1Default;
  private SFFloat contactNormal2, contactNormal2Default;

  private SFFloat frictionCoefficients0, frictionCoefficients0Default;
  private SFFloat frictionCoefficients1, frictionCoefficients1Default;

  private SFFloat frictionDirection0, frictionDirection0Default;
  private SFFloat frictionDirection1, frictionDirection1Default;
  private SFFloat frictionDirection2, frictionDirection2Default;
  
  private SFFloat position0, position0Default;
  private SFFloat position1, position1Default;
  private SFFloat position2, position2Default;
  
  private SFFloat softnessConstantForceMix, softnessConstantForceMixDefault;
  private SFFloat softnessErrorCorrection, softnessErrorCorrectionDefault;
  
  private SFFloat slipCoefficients0, slipCoefficients0Default;
  private SFFloat slipCoefficients1, slipCoefficients1Default;

  private SFFloat surfaceSpeed0, surfaceSpeed0Default;
  private SFFloat surfaceSpeed1, surfaceSpeed1Default;

  public CONTACT()
  {
  }

  @Override
  public String getElementName()
  {
    return CONTACT_ELNAME;
  }
  @Override
  public String getDefaultContainerField()
  {
    return "collider";
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return CONTACTCustomizer.class;
  }

  @Override
  public void initialize()
  {
    String[] fa;

    appliedParameters        = CONTACT_ATTR_APPLIEDPARAMETERS_DFLT;
    bounce                   = bounceDefault                   = new SFFloat(CONTACT_ATTR_BOUNCE_DFLT, 0.0f, 1.0f);
    depth                    = depthDefault                    = new SFFloat(CONTACT_ATTR_DEPTH_DFLT, null, null);
    minBounceSpeed           = minBounceSpeedDefault           = new SFFloat(CONTACT_ATTR_MINBOUNCESPEED_DFLT, 0.0f, null);
    softnessConstantForceMix = softnessConstantForceMixDefault = new SFFloat(CONTACT_ATTR_SOFTNESSCONSTANTFORCEMIX_DFLT, 0.0f, 1.0f);
    softnessErrorCorrection  = softnessErrorCorrectionDefault  = new SFFloat(CONTACT_ATTR_SOFTNESSERRORCORRECTION_DFLT, 0.0f, 1.0f);
    
    enabled = enabledDefault = Boolean.parseBoolean(CONTACT_ATTR_ENABLED_DFLT);

    fa = parse3(CONTACT_ATTR_CONTACTNORMAL_DFLT);
    contactNormal0 = contactNormal0Default = new SFFloat(fa[0], null, null);
    contactNormal1 = contactNormal1Default = new SFFloat(fa[1], null, null);
    contactNormal2 = contactNormal2Default = new SFFloat(fa[2], null, null);

    fa = parse2(CONTACT_ATTR_FRICTIONCOEFFICIENTS_DFLT);
    frictionCoefficients0 = frictionCoefficients0Default = new SFFloat(fa[0], 0.0f, null);
    frictionCoefficients1 = frictionCoefficients1Default = new SFFloat(fa[1], 0.0f, null);

    fa = parse3(CONTACT_ATTR_FRICTIONDIRECTION_DFLT);
    frictionDirection0 = frictionDirection0Default = new SFFloat(fa[0], null, null);
    frictionDirection1 = frictionDirection1Default = new SFFloat(fa[1], null, null);
    frictionDirection2 = frictionDirection2Default = new SFFloat(fa[2], null, null);

    fa = parse3(CONTACT_ATTR_POSITION_DFLT);
    position0 = position0Default = new SFFloat(fa[0], null, null);
    position1 = position1Default = new SFFloat(fa[1], null, null);
    position2 = position2Default = new SFFloat(fa[2], null, null);
    
    fa = parse2(CONTACT_ATTR_SLIPCOEFFICIENTS_DFLT);
    slipCoefficients0 = slipCoefficients0Default = new SFFloat(fa[0], null, null);
    slipCoefficients1 = slipCoefficients1Default = new SFFloat(fa[1], null, null);

    fa = parse2(CONTACT_ATTR_SURFACESPEED_DFLT);
    surfaceSpeed0 = surfaceSpeed0Default = new SFFloat(fa[0], null, null);
    surfaceSpeed1 = surfaceSpeed1Default = new SFFloat(fa[1], null, null);

    setContent("\n\t\t<RigidBody containerField='body1'/>" +
               "\n\t\t<RigidBody containerField='body2'/>" +
               "\n\t\t<!-- geometry can be CollidableOffset or CollidableShape -->" +
               "\n\t\t<CollidableShape containerField='geometry1'/>" +
               "\n\t\t<CollidableShape containerField='geometry2'/>\n\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;
    String[] fa;
    
    attr = root.getAttribute(CONTACT_ATTR_APPLIEDPARAMETERS_NAME);
    if (attr != null)
      appliedParameters = parseMFStringIntoStringArray(attr.getValue(),true);
    
    attr = root.getAttribute(CONTACT_ATTR_BOUNCE_NAME);
    if (attr != null) {
      bounce = new SFFloat(attr.getValue(), 0.0f, 1.0f);
      
    attr = root.getAttribute(CONTACT_ATTR_CONTACTNORMAL_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      contactNormal0 = new SFFloat(fa[0], null, null);
      contactNormal1 = new SFFloat(fa[1], null, null);
      contactNormal2 = new SFFloat(fa[2], null, null);
    }
    
    attr = root.getAttribute(CONTACT_ATTR_DEPTH_NAME);
    if (attr != null)
      depth   = new SFFloat(attr.getValue(), null, null);
    
    attr = root.getAttribute(CONTACT_ATTR_ENABLED_NAME);
    if (attr != null)
      enabled   = Boolean.parseBoolean(attr.getValue());
    }    
    attr = root.getAttribute(CONTACT_ATTR_MINBOUNCESPEED_NAME);
    if (attr != null) {
      minBounceSpeed = new SFFloat(attr.getValue(), null, null);
    }    
    attr = root.getAttribute(CONTACT_ATTR_SOFTNESSCONSTANTFORCEMIX_NAME);
    if (attr != null) {
      softnessConstantForceMix = new SFFloat(attr.getValue(), 0.0f, 1.0f);
    }    
    attr = root.getAttribute(CONTACT_ATTR_SOFTNESSERRORCORRECTION_NAME);
    if (attr != null) {
      softnessErrorCorrection = new SFFloat(attr.getValue(),0.0f, 1.0f);
    }
    attr = root.getAttribute(CONTACT_ATTR_FRICTIONCOEFFICIENTS_NAME);
    if (attr != null) {
      fa = parse2(attr.getValue());
      frictionCoefficients0 = new SFFloat(fa[0], null, null);
      frictionCoefficients1 = new SFFloat(fa[1], null, null);
    }
    attr = root.getAttribute(CONTACT_ATTR_FRICTIONDIRECTION_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      frictionDirection0 = new SFFloat(fa[0], null, null);
      frictionDirection1 = new SFFloat(fa[1], null, null);
      frictionDirection2 = new SFFloat(fa[2], null, null);
    }
    attr = root.getAttribute(CONTACT_ATTR_POSITION_NAME);
    if (attr != null) {
      fa = parse3(attr.getValue());
      position0 = new SFFloat(fa[0], null, null);
      position1 = new SFFloat(fa[1], null, null);
      position2 = new SFFloat(fa[2], null, null);
    }
    attr = root.getAttribute(CONTACT_ATTR_SLIPCOEFFICIENTS_NAME);
    if (attr != null) {
      fa = parse2(attr.getValue());
      slipCoefficients0 = new SFFloat(fa[0], null, null);
      slipCoefficients1 = new SFFloat(fa[1], null, null);
    }
    attr = root.getAttribute(CONTACT_ATTR_SURFACESPEED_NAME);
    if (attr != null) {
      fa = parse2(attr.getValue());
      surfaceSpeed0 = new SFFloat(fa[0], null, null);
      surfaceSpeed1 = new SFFloat(fa[1], null, null);
    }
  }

  @Override
  public String createAttributes()
  {
    StringBuffer sb = new StringBuffer();

    if (CONTACT_ATTR_APPLIEDPARAMETERS_REQD ||!appliedParametersEqualDefault()) 
    {
      sb.append(" ");
      sb.append(CONTACT_ATTR_APPLIEDPARAMETERS_NAME); //todo test
      sb.append("='");
      for (String s : appliedParameters) {
        sb.append("\"");
        sb.append(s);
        sb.append("\" ");
      }
    }
    if (CONTACT_ATTR_BOUNCE_REQD ||
           (!bounce.equals(bounceDefault))) {
      sb.append(" ");
      sb.append(CONTACT_ATTR_BOUNCE_NAME);
      sb.append("='");
      sb.append(bounce);
      sb.append("'");
    }
    if (CONTACT_ATTR_CONTACTNORMAL_REQD ||
                   (!contactNormal0.equals(contactNormal0Default) ||
                    !contactNormal1.equals(contactNormal1Default) ||
                    !contactNormal2.equals(contactNormal2Default))) {
      sb.append(" ");
      sb.append(CONTACT_ATTR_CONTACTNORMAL_NAME);
      sb.append("='");
      sb.append(contactNormal0);
      sb.append(" ");
      sb.append(contactNormal1);
      sb.append(" ");
      sb.append(contactNormal2);
      sb.append("'");
    }
    if (CONTACT_ATTR_DEPTH_REQD ||
           (!depth.equals(depthDefault))) {
      sb.append(" ");
      sb.append(CONTACT_ATTR_DEPTH_NAME);
      sb.append("='");
      sb.append(depth);
      sb.append("'");
    }
    if (CONTACT_ATTR_ENABLED_REQD || enabled != enabledDefault) {
      sb.append(" ");
      sb.append(CONTACT_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }
    if (CONTACT_ATTR_FRICTIONCOEFFICIENTS_REQD ||
                   (!frictionCoefficients0.equals(frictionCoefficients0Default) ||
                    !frictionCoefficients1.equals(frictionCoefficients1Default))) {
      sb.append(" ");
      sb.append(CONTACT_ATTR_FRICTIONCOEFFICIENTS_NAME);
      sb.append("='");
      sb.append(frictionCoefficients0);
      sb.append(" ");
      sb.append(frictionCoefficients1);
      sb.append("'");
    }
    if (CONTACT_ATTR_FRICTIONDIRECTION_REQD ||
                   (!frictionDirection0.equals(frictionDirection0Default) ||
                    !frictionDirection1.equals(frictionDirection1Default) ||
                    !frictionDirection2.equals(frictionDirection2Default))) {
      sb.append(" ");
      sb.append(CONTACT_ATTR_FRICTIONDIRECTION_NAME);
      sb.append("='");
      sb.append(frictionDirection0);
      sb.append(" ");
      sb.append(frictionDirection1);
      sb.append(" ");
      sb.append(frictionDirection2);
      sb.append("'");
    }
    if (CONTACT_ATTR_MINBOUNCESPEED_REQD ||
           (!minBounceSpeed.equals(minBounceSpeedDefault))) {
      sb.append(" ");
      sb.append(CONTACT_ATTR_MINBOUNCESPEED_NAME);
      sb.append("='");
      sb.append(minBounceSpeed);
      sb.append("'");
    }
    if (CONTACT_ATTR_POSITION_REQD ||
                   (!position0.equals(frictionDirection0Default) ||
                    !frictionDirection1.equals(frictionDirection1Default) ||
                    !frictionDirection2.equals(frictionDirection2Default))) {
      sb.append(" ");
      sb.append(CONTACT_ATTR_POSITION_NAME);
      sb.append("='");
      sb.append(position0);
      sb.append(" ");
      sb.append(position1);
      sb.append(" ");
      sb.append(position2);
      sb.append("'");
    }
    if (CONTACT_ATTR_SLIPCOEFFICIENTS_REQD ||
           (!slipCoefficients0.equals(slipCoefficients0Default) ||
            !slipCoefficients1.equals(slipCoefficients1Default))) {

      sb.append(" ");
      sb.append(CONTACT_ATTR_SLIPCOEFFICIENTS_NAME);
      sb.append("='");
      sb.append(slipCoefficients0);
      sb.append(" ");
      sb.append(slipCoefficients1);
      sb.append("'");
    }
    if (CONTACT_ATTR_SOFTNESSCONSTANTFORCEMIX_REQD ||
           (!softnessConstantForceMix.equals(softnessConstantForceMixDefault))) {
      sb.append(" ");
      sb.append(CONTACT_ATTR_SOFTNESSCONSTANTFORCEMIX_NAME);
      sb.append("='");
      sb.append(softnessConstantForceMix);
      sb.append("'");
    }
    if (CONTACT_ATTR_SOFTNESSERRORCORRECTION_REQD ||
           (!softnessErrorCorrection.equals(softnessErrorCorrectionDefault))) {
      sb.append(" ");
      sb.append(CONTACT_ATTR_SOFTNESSERRORCORRECTION_NAME);
      sb.append("='");
      sb.append(softnessErrorCorrection);
      sb.append("'");
    }
    if (CONTACT_ATTR_SURFACESPEED_REQD ||
           (!surfaceSpeed0.equals(surfaceSpeed0Default) ||
            !surfaceSpeed1.equals(surfaceSpeed1Default))) {
      sb.append(" ");
      sb.append(CONTACT_ATTR_SURFACESPEED_NAME);
      sb.append("='");
      sb.append(surfaceSpeed0);
      sb.append(" ");
      sb.append(surfaceSpeed1);
      sb.append("'");
    }

    return sb.toString();
  }

  private boolean appliedParametersEqualDefault()
  {
    if (CONTACT_ATTR_APPLIEDPARAMETERS_DFLT.length != appliedParameters.length)
      return false;

    for(String t : CONTACT_ATTR_APPLIEDPARAMETERS_DFLT) {
      int i;
      for(i=0;i<appliedParameters.length;i++) {
        if(appliedParameters[i].equals(t)) {
          break;
        }
      }
      if(i >=appliedParameters.length)
        return false;  //failed
    }
    return true;
  }

  public String getBounce()
  {
    return bounce.toString();
  }

  public void setBounce(String bounce)
  {
    this.bounce = new SFFloat(bounce, null, null);
  }

  public String getDepth()
  {
    return depth.toString();
  }

  public void setDepth(String depth)
  {
    this.depth = new SFFloat(depth, null, null);
  }

  public String getMinBounceSpeed()
  {
    return minBounceSpeed.toString();
  }

  public void setMinBounceSpeed(String minBounceSpeed)
  {
    this.minBounceSpeed = new SFFloat(minBounceSpeed, null, null);
  }

  public String getSoftnessConstantForceMix ()
  {
    return softnessConstantForceMix.toString();
  }

  public void setSoftnessConstantForceMix (String softnessConstantForceMix)
  {
    this.softnessConstantForceMix = new SFFloat(softnessConstantForceMix, null, null);
  }

  public String getSoftnessErrorCorrection()
  {
    return softnessErrorCorrection.toString();
  }

  public void setSoftnessErrorCorrection(String softnessErrorCorrection)
  {
    this.softnessErrorCorrection = new SFFloat(softnessErrorCorrection, null, null);
  }

  public boolean isEnabled()
  {
    return enabled;
  }

  public void setEnabled(boolean enabled)
  {
    this.enabled = enabled;
  }

  public String getSurfaceSpeed0()
  {
    return surfaceSpeed0.toString();
  }

  public void setSurfaceSpeed0(String surfaceSpeed0)
  {
    this.surfaceSpeed0 = new SFFloat(surfaceSpeed0, null, null);
  }

  public String getSurfaceSpeed1()
  {
    return surfaceSpeed1.toString();
  }

  public void setSurfaceSpeed1(String surfaceSpeed1)
  {
    this.surfaceSpeed1 = new SFFloat(surfaceSpeed1, null, null);
  }
  
  public String getFrictionCoefficients0()
  {
    return frictionCoefficients0.toString();
  }

  public void setFrictionCoefficients0(String frictionCoefficients0)
  {
    this.frictionCoefficients0 = new SFFloat(frictionCoefficients0, null, null);
  }

  public String getFrictionCoefficients1()
  {
    return frictionCoefficients1.toString();
  }

  public void setFrictionCoefficients1(String frictionCoefficients1)
  {
    this.frictionCoefficients1 = new SFFloat(frictionCoefficients1, null, null);
  }

  public String getFrictionDirection0()
  {
    return frictionDirection0.toString();
  }

  public void setFrictionDirection0(String frictionDirection0)
  {
    this.frictionDirection0 = new SFFloat(frictionDirection0, null, null);
  }

  public String getFrictionDirection1()
  {
    return frictionDirection1.toString();
  }

  public void setFrictionDirection1(String frictionDirection1)
  {
    this.frictionDirection1 = new SFFloat(frictionDirection1, null, null);
  }

  public String getFrictionDirection2()
  {
    return frictionDirection2.toString();
  }

  public void setFrictionDirection2(String frictionDirection2)
  {
    this.frictionDirection2 = new SFFloat(frictionDirection2, null, null);
  }

  public String getContactNormal0()
  {
    return contactNormal0.toString();
  }

  public void setContactNormal0(String contactNormal0)
  {
    this.contactNormal0 = new SFFloat(contactNormal0, null, null);
  }

  public String getContactNormal1()
  {
    return contactNormal1.toString();
  }

  public void setContactNormal1(String contactNormal1)
  {
    this.contactNormal1 = new SFFloat(contactNormal1, null, null);
  }

  public String getContactNormal2()
  {
    return contactNormal2.toString();
  }

  public void setContactNormal2(String contactNormal2)
  {
    this.contactNormal2 = new SFFloat(contactNormal2, null, null);
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
    this.position2 = new SFFloat(position2, null, null);
  }
  
  public String getSlipFactors0()
  {
    return slipCoefficients0.toString();
  }

  public void setSlipFactors0(String slipCoefficients0)
  {
    this.slipCoefficients0 = new SFFloat(slipCoefficients0, null, null);
  }

  public String getSlipFactors1()
  {
    return slipCoefficients1.toString();
  }

  public void setSlipFactors1(String slipCoefficients1)
  {
    this.slipCoefficients1 = new SFFloat(slipCoefficients1, null, null);
  }  

  public String[] getAppliedParameters()
  {
    return appliedParameters;
  }

  public void setAppliedParameters(String[] newAppliedParameters)
  {
    this.appliedParameters = newAppliedParameters;
  }
}