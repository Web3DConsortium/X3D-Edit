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
import org.web3d.x3d.types.X3DPrimitiveTypes.SFFloat;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * COLLISIONCOLLECTION.java
 * Created on 27 December 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class COLLISIONCOLLECTION extends X3DChildNode
{
  private boolean enabled, enabledDefault;
  
  private String[] appliedParameters;

  private SFFloat bounce, bounceDefault;
  private SFFloat minBounceSpeed, minBounceSpeedDefault;
  private SFFloat softnessConstantForceMix, softnessConstantForceMixDefault;
  private SFFloat softnessErrorCorrection, softnessErrorCorrectionDefault;
  
  private SFFloat slipFactors0, slipFactors0Default;
  private SFFloat slipFactors1, slipFactors1Default;

  private SFFloat frictionCoefficients0, frictionCoefficients0Default;
  private SFFloat frictionCoefficients1, frictionCoefficients1Default;

  private SFFloat surfaceSpeed0, surfaceSpeed0Default;
  private SFFloat surfaceSpeed1, surfaceSpeed1Default;

  public COLLISIONCOLLECTION()
  {
  }

  @Override
  public String getElementName()
  {
    return COLLISIONCOLLECTION_ELNAME;
  }
  @Override
  public String getDefaultContainerField()
  {
    return "collider";
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return COLLISIONCOLLECTIONCustomizer.class;
  }

  @Override
  public void initialize()
  {
    String[] fa;

    appliedParameters        = COLLISIONCOLLECTION_ATTR_APPLIEDPARAMETERS_DFLT;
    bounce                   = bounceDefault                   = new SFFloat(COLLISIONCOLLECTION_ATTR_BOUNCE_DFLT, 0.0f, 1.0f);
    minBounceSpeed           = minBounceSpeedDefault           = new SFFloat(COLLISIONCOLLECTION_ATTR_MINBOUNCESPEED_DFLT, 0.0f, null);
    softnessConstantForceMix = softnessConstantForceMixDefault = new SFFloat(COLLISIONCOLLECTION_ATTR_SOFTNESSCONSTANTFORCEMIX_DFLT, 0.0f, 1.0f);
    softnessErrorCorrection  = softnessErrorCorrectionDefault  = new SFFloat(COLLISIONCOLLECTION_ATTR_SOFTNESSERRORCORRECTION_DFLT, 0.0f, 1.0f);
    
    enabled = enabledDefault = Boolean.parseBoolean(COLLISIONCOLLECTION_ATTR_ENABLED_DFLT);

    fa = parse2(COLLISIONCOLLECTION_ATTR_FRICTIONCOEFFICIENTS_DFLT);
    frictionCoefficients0 = frictionCoefficients0Default = new SFFloat(fa[0], 0.0f, null);
    frictionCoefficients1 = frictionCoefficients1Default = new SFFloat(fa[1], 0.0f, null);
    
    fa = parse2(COLLISIONCOLLECTION_ATTR_SLIPFACTORS_DFLT);
    slipFactors0 = slipFactors0Default = new SFFloat(fa[0], null, null);
    slipFactors1 = slipFactors1Default = new SFFloat(fa[1], null, null);

    fa = parse2(COLLISIONCOLLECTION_ATTR_SURFACESPEED_DFLT);
    surfaceSpeed0 = surfaceSpeed0Default = new SFFloat(fa[0], null, null);
    surfaceSpeed1 = surfaceSpeed1Default = new SFFloat(fa[1], null, null);

    // TODO schematron rule to ensure child nodes have containerField='collidables'
    
    setContent("\n\t\t<!--insert X3DNBodyCollisionSpaceNode or X3DNBodyCollidableNode here: CollisionSpace, CollidableShape or CollidableOffset (containerField='collidables')-->\n\t\t<CollidableShape containerField='collidables'/>\n\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;
    String[] fa;
    
    attr = root.getAttribute(COLLISIONCOLLECTION_ATTR_APPLIEDPARAMETERS_NAME);
    if (attr != null)
      appliedParameters = parseMFStringIntoStringArray(attr.getValue(),true);
    
    attr = root.getAttribute(COLLISIONCOLLECTION_ATTR_ENABLED_NAME);
    if (attr != null)
      enabled   = Boolean.parseBoolean(attr.getValue());
    
    attr = root.getAttribute(COLLISIONCOLLECTION_ATTR_BOUNCE_NAME);
    if (attr != null) {
      bounce = new SFFloat(attr.getValue(), null, null);
    }    
    attr = root.getAttribute(COLLISIONCOLLECTION_ATTR_MINBOUNCESPEED_NAME);
    if (attr != null) {
      minBounceSpeed = new SFFloat(attr.getValue(), null, null);
    }    
    attr = root.getAttribute(COLLISIONCOLLECTION_ATTR_SOFTNESSCONSTANTFORCEMIX_NAME);
    if (attr != null) {
      softnessConstantForceMix = new SFFloat(attr.getValue(), null, null);
    }    
    attr = root.getAttribute(COLLISIONCOLLECTION_ATTR_SOFTNESSERRORCORRECTION_NAME);
    if (attr != null) {
      softnessErrorCorrection = new SFFloat(attr.getValue(), null, null);
    }
    attr = root.getAttribute(COLLISIONCOLLECTION_ATTR_FRICTIONCOEFFICIENTS_NAME);
    if (attr != null) {
      fa = parse2(attr.getValue());
      frictionCoefficients0 = new SFFloat(fa[0], null, null);
      frictionCoefficients1 = new SFFloat(fa[1], null, null);
    }
    attr = root.getAttribute(COLLISIONCOLLECTION_ATTR_SLIPFACTORS_NAME);
    if (attr != null) {
      fa = parse2(attr.getValue());
      slipFactors0 = new SFFloat(fa[0], null, null);
      slipFactors1 = new SFFloat(fa[1], null, null);
    }
    attr = root.getAttribute(COLLISIONCOLLECTION_ATTR_SURFACESPEED_NAME);
    if (attr != null) {
      fa = parse2(attr.getValue());
      surfaceSpeed0 = new SFFloat(fa[0], null, null);
      surfaceSpeed1 = new SFFloat(fa[1], null, null);
    }
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (COLLISIONCOLLECTION_ATTR_APPLIEDPARAMETERS_REQD ||!appliedParametersEqualDefault()) 
    {
      sb.append(" ");
      sb.append(COLLISIONCOLLECTION_ATTR_APPLIEDPARAMETERS_NAME); //todo test
      sb.append("='");
      for (String s : appliedParameters) {
        sb.append("\"");
        sb.append(s);
        sb.append("\" ");
      }
    }
    if (COLLISIONCOLLECTION_ATTR_BOUNCE_REQD ||
           (!bounce.equals(bounceDefault))) {
      sb.append(" ");
      sb.append(COLLISIONCOLLECTION_ATTR_BOUNCE_NAME);
      sb.append("='");
      sb.append(bounce);
      sb.append("'");
    }
    if (COLLISIONCOLLECTION_ATTR_ENABLED_REQD || enabled != enabledDefault) {
      sb.append(" ");
      sb.append(COLLISIONCOLLECTION_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }
    if (COLLISIONCOLLECTION_ATTR_FRICTIONCOEFFICIENTS_REQD ||
                   (!frictionCoefficients0.equals(frictionCoefficients0Default) ||
                    !frictionCoefficients1.equals(frictionCoefficients1Default))) {
      sb.append(" ");
      sb.append(COLLISIONCOLLECTION_ATTR_FRICTIONCOEFFICIENTS_NAME);
      sb.append("='");
      sb.append(frictionCoefficients0);
      sb.append(" ");
      sb.append(frictionCoefficients1);
      sb.append("'");
    }
    if (COLLISIONCOLLECTION_ATTR_MINBOUNCESPEED_REQD ||
           (!minBounceSpeed.equals(minBounceSpeedDefault))) {
      sb.append(" ");
      sb.append(COLLISIONCOLLECTION_ATTR_MINBOUNCESPEED_NAME);
      sb.append("='");
      sb.append(minBounceSpeed);
      sb.append("'");
    }
    if (COLLISIONCOLLECTION_ATTR_SOFTNESSCONSTANTFORCEMIX_REQD ||
           (!softnessConstantForceMix.equals(softnessConstantForceMixDefault))) {
      sb.append(" ");
      sb.append(COLLISIONCOLLECTION_ATTR_SOFTNESSCONSTANTFORCEMIX_NAME);
      sb.append("='");
      sb.append(softnessConstantForceMix);
      sb.append("'");
    }
    if (COLLISIONCOLLECTION_ATTR_SOFTNESSERRORCORRECTION_REQD ||
           (!softnessErrorCorrection.equals(softnessErrorCorrectionDefault))) {
      sb.append(" ");
      sb.append(COLLISIONCOLLECTION_ATTR_SOFTNESSERRORCORRECTION_NAME);
      sb.append("='");
      sb.append(softnessErrorCorrection);
      sb.append("'");
    }
    if (COLLISIONCOLLECTION_ATTR_SLIPFACTORS_REQD ||
           (!slipFactors0.equals(slipFactors0Default) ||
            !slipFactors1.equals(slipFactors1Default))) {

      sb.append(" ");
      sb.append(COLLISIONCOLLECTION_ATTR_SLIPFACTORS_NAME);
      sb.append("='");
      sb.append(slipFactors0);
      sb.append(" ");
      sb.append(slipFactors1);
      sb.append("'");
    }
    if (COLLISIONCOLLECTION_ATTR_SURFACESPEED_REQD ||
           (!surfaceSpeed0.equals(surfaceSpeed0Default) ||
            !surfaceSpeed1.equals(surfaceSpeed1Default))) {
      sb.append(" ");
      sb.append(COLLISIONCOLLECTION_ATTR_SURFACESPEED_NAME);
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
    if (COLLISIONCOLLECTION_ATTR_APPLIEDPARAMETERS_DFLT.length != appliedParameters.length)
      return false;

    for(String t : COLLISIONCOLLECTION_ATTR_APPLIEDPARAMETERS_DFLT) {
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
  public String getSlipFactors0()
  {
    return slipFactors0.toString();
  }

  public void setSlipFactors0(String slipFactors0)
  {
    this.slipFactors0 = new SFFloat(slipFactors0, null, null);
  }

  public String getSlipFactors1()
  {
    return slipFactors1.toString();
  }

  public void setSlipFactors1(String slipFactors1)
  {
    this.slipFactors1 = new SFFloat(slipFactors1, null, null);
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