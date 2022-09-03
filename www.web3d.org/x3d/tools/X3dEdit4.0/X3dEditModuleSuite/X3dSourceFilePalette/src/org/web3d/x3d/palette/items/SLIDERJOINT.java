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
 * SLIDERJOINT.java
 * Created on December 23, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class SLIDERJOINT extends X3DRigidJointNode
{
  private SFFloat axisX, axisXDefault;
  private SFFloat axisY, axisYDefault;
  private SFFloat axisZ, axisZDefault;
  
  private Vector<String> forceOutput;
  
  private SFFloat maxSeparation,       maxSeparationDefault;
  private SFFloat minSeparation,       minSeparationDefault;
  private SFFloat sliderForce,         sliderForceDefault;
  private SFFloat stopBounce,          stopBounceDefault;
  private SFFloat stopErrorCorrection, stopErrorCorrectionDefault;
  
  public SLIDERJOINT()
  {
  }

  @Override
  public String getElementName()
  {
    return SLIDERJOINT_ELNAME;
  }

  @Override
  public void initialize()
  {
    forceOutput = new Vector<String>();
    if(SLIDERJOINT_ATTR_FORCEOUTPUT_DFLT.length()>0)
    {
      forceOutput.add(SLIDERJOINT_ATTR_FORCEOUTPUT_DFLT); // assumes singleton, which is OK
    }

    String[] fa = parse3(SLIDERJOINT_ATTR_AXIS_DFLT);
    axisX = axisXDefault = new SFFloat(fa[0], null, null);
    axisY = axisYDefault = new SFFloat(fa[1],null,null);
    axisZ = axisZDefault = new SFFloat(fa[2],null,null);
    
    maxSeparation       = maxSeparationDefault       = new SFFloat(SLIDERJOINT_ATTR_MAXSEPARATION_DFLT,       0.0f, null);
    minSeparation       = minSeparationDefault       = new SFFloat(SLIDERJOINT_ATTR_MINSEPARATION_DFLT,       0.0f, null);
    sliderForce         = sliderForceDefault         = new SFFloat(SLIDERJOINT_ATTR_SLIDERFORCE_DFLT,         null, null);
    stopBounce          = stopBounceDefault          = new SFFloat(SLIDERJOINT_ATTR_STOPBOUNCE_DFLT,          0.0f, 1.0f);
    stopErrorCorrection = stopErrorCorrectionDefault = new SFFloat(SLIDERJOINT_ATTR_STOPERRORCORRECTION_DFLT, 0.0f, 1.0f);

    setContent("\n\t\t<RigidBody containerField='body1'/>" +
               "\n\t\t<RigidBody containerField='body2'/>\n\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    
    org.jdom.Attribute attr = root.getAttribute(SLIDERJOINT_ATTR_FORCEOUTPUT_NAME);
    if(attr != null) {
      forceOutput = new Vector<String>();
      forceOutput.add(attr.getValue());
    }
    
    attr = root.getAttribute(SLIDERJOINT_ATTR_AXIS_NAME);
    if (attr != null) {
      String[] fa = parse3(attr.getValue());
      axisX = new SFFloat(fa[0], 0.0f, null, true);
      axisY = new SFFloat(fa[1], 0.0f, null, true);
      axisZ = new SFFloat(fa[2], 0.0f, null, true);
    }
    attr = root.getAttribute(SLIDERJOINT_ATTR_MAXSEPARATION_NAME);
    if(attr != null)
        maxSeparation = new SFFloat(attr.getValue(), 0.0f, null);
    
    attr = root.getAttribute(SLIDERJOINT_ATTR_MINSEPARATION_NAME);
    if(attr != null)
        minSeparation = new SFFloat(attr.getValue(), 0.0f, null);

    attr = root.getAttribute(SLIDERJOINT_ATTR_SLIDERFORCE_NAME);
    if(attr != null)
        sliderForce= new SFFloat(attr.getValue(), null, null);

    attr = root.getAttribute(SLIDERJOINT_ATTR_STOPBOUNCE_NAME);
    if(attr != null)
        stopBounce = new SFFloat(attr.getValue(), 0.0f, 1.0f);
    
    attr = root.getAttribute(SLIDERJOINT_ATTR_STOPERRORCORRECTION_NAME);
    if(attr != null)
        stopErrorCorrection = new SFFloat(attr.getValue(), 0.0f, 1.0f);
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return SLIDERJOINTCustomizer.class;
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (SLIDERJOINT_ATTR_AXIS_REQD ||
           (!axisX.equals(axisXDefault) ||
            !axisY.equals(axisYDefault) ||
            !axisZ.equals(axisZDefault)))
    {
      sb.append(" ");
      sb.append(SLIDERJOINT_ATTR_AXIS_NAME);
      sb.append("='");
      sb.append(axisX);
      sb.append(" ");
      sb.append(axisY);
      sb.append(" ");
      sb.append(axisZ);
      sb.append("'");
    }
    if (SLIDERJOINT_ATTR_FORCEOUTPUT_REQD || (forceOutput.size() > 0)) {
      sb.append(" ");
      sb.append(SLIDERJOINT_ATTR_FORCEOUTPUT_NAME);
      sb.append("='");
      sb.append(formatForceOutput());
      sb.append("'");
    }
    if (SLIDERJOINT_ATTR_MAXSEPARATION_REQD || !maxSeparation.equals(maxSeparationDefault)) {
      sb.append(" ");
      sb.append(SLIDERJOINT_ATTR_MAXSEPARATION_NAME);
      sb.append("='");
      sb.append(getMaxSeparation());
      sb.append("'");
    }
    if (SLIDERJOINT_ATTR_MINSEPARATION_REQD || !minSeparation.equals(minSeparationDefault)) {
      sb.append(" ");
      sb.append(SLIDERJOINT_ATTR_MINSEPARATION_NAME);
      sb.append("='");
      sb.append(getMinSeparation());
      sb.append("'");
    }
    if (SLIDERJOINT_ATTR_SLIDERFORCE_REQD || !sliderForce.equals(sliderForceDefault)) {
      sb.append(" ");
      sb.append(SLIDERJOINT_ATTR_SLIDERFORCE_NAME);
      sb.append("='");
      sb.append(getSliderForce());
      sb.append("'");
    }
    if (SLIDERJOINT_ATTR_STOPBOUNCE_REQD || !stopBounce.equals(stopBounceDefault)) {
      sb.append(" ");
      sb.append(SLIDERJOINT_ATTR_STOPBOUNCE_NAME);
      sb.append("='");
      sb.append(getStopBounce());
      sb.append("'");
    }
    if (SLIDERJOINT_ATTR_STOPERRORCORRECTION_REQD || !stopErrorCorrection.equals(stopErrorCorrectionDefault)) {
      sb.append(" ");
      sb.append(SLIDERJOINT_ATTR_STOPERRORCORRECTION_NAME);
      sb.append("='");
      sb.append(getStopErrorCorrection());
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

    public String getAxisX()
    {
    return axisX.toString();
    }

    public void setAxisX(String axisX)
    {
    this.axisX = new SFFloat(axisX,0.0f,null,true);
    }

    public String getAxisY()
    {
    return axisY.toString();
    }

    public void setAxisY(String axisY)
    {
    this.axisY = new SFFloat(axisY,0.0f,null,true);
    }

    public String getAxisZ()
    {
    return axisZ.toString();
    }

    public void setAxisZ(String axisZ)
    {
    this.axisZ = new SFFloat(axisZ,0.0f,null,true);
    }

    public String getMaxSeparation()
    {
        return maxSeparation.toString();
    }

    public void setMaxSeparation(String maxSeparation)
    {
        this.maxSeparation = new SFFloat(maxSeparation,1.0f,null,true);
    }

    public String getMinSeparation()
    {
        return minSeparation.toString();
    }

    public void setMinSeparation(String minSeparation)
    {
        this.minSeparation = new SFFloat(minSeparation,0.0f,null,true);
    }

    public String getStopBounce()
    {
        return stopBounce.toString();
    }

    public void setStopBounce(String stopBounce)
    {
        this.stopBounce = new SFFloat(stopBounce,0.0f,null,true);
    }

    public String getStopErrorCorrection()
    {
        return stopErrorCorrection.toString();
    }

    public void setStopErrorCorrection(String stopErrorCorrection)
    {
        this.stopErrorCorrection = new SFFloat(stopErrorCorrection,1.0f,null,true);
    }

    /**
     * @return the sliderForce
     */
    public String getSliderForce() {
        return sliderForce.toString();
    }

    /**
     * @param sliderForce the sliderForce to set
     */
    public void setSliderForce(String sliderForce) {
        this.sliderForce = new SFFloat(sliderForce,0.0f,null,true);
    }
}
