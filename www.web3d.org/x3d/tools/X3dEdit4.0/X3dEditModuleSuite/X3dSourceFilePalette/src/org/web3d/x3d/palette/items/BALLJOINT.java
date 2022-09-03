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
 * BALLJOINT.java
 * Created on December 22, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class BALLJOINT extends X3DRigidJointNode
{
  private SFFloat anchorPointX, anchorPointXDefault;
  private SFFloat anchorPointY, anchorPointYDefault;
  private SFFloat anchorPointZ, anchorPointZDefault;
  
  private Vector<String> forceOutput;
  
  public BALLJOINT()
  {
  }

  @Override
  public String getElementName()
  {
    return BALLJOINT_ELNAME;
  }

  @Override
  public void initialize()
  {
    forceOutput = new Vector<String>();
    if(BALLJOINT_ATTR_FORCEOUTPUT_DFLT.length()>0)
    {
      forceOutput.add(BALLJOINT_ATTR_FORCEOUTPUT_DFLT); // assumes singleton, which is OK
    }

    String[] fa = parse3(BALLJOINT_ATTR_ANCHORPOINT_DFLT);
    anchorPointX = anchorPointXDefault = new SFFloat(fa[0],null, null);
    anchorPointY = anchorPointYDefault = new SFFloat(fa[1],null,null);
    anchorPointZ = anchorPointZDefault = new SFFloat(fa[2],null,null);

    setContent("\n\t\t<RigidBody containerField='body1'/>" +
               "\n\t\t<RigidBody containerField='body2'/>\n\t");
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    
    org.jdom.Attribute attr = root.getAttribute(BALLJOINT_ATTR_FORCEOUTPUT_NAME);
    if(attr != null) {
      forceOutput = new Vector<>();
      forceOutput.add(attr.getValue());
    }
    
    attr = root.getAttribute(BALLJOINT_ATTR_ANCHORPOINT_NAME);
    if (attr != null) {
      String[] fa = parse3(attr.getValue());
      anchorPointX = new SFFloat(fa[0], 0.0f, null, true);
      anchorPointY = new SFFloat(fa[1], 0.0f, null, true);
      anchorPointZ = new SFFloat(fa[2], 0.0f, null, true);
    }
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return BALLJOINTCustomizer.class;
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();

    if (BALLJOINT_ATTR_ANCHORPOINT_REQD ||
           (!anchorPointX.equals(anchorPointXDefault) ||
            !anchorPointY.equals(anchorPointYDefault) ||
            !anchorPointZ.equals(anchorPointZDefault)))
    {
      sb.append(" ");
      sb.append(BALLJOINT_ATTR_ANCHORPOINT_NAME);
      sb.append("='");
      sb.append(anchorPointX);
      sb.append(" ");
      sb.append(anchorPointY);
      sb.append(" ");
      sb.append(anchorPointZ);
      sb.append("'");
    }
    if (BALLJOINT_ATTR_FORCEOUTPUT_REQD || (forceOutput.size() > 0)) {
      sb.append(" ");
      sb.append(BALLJOINT_ATTR_FORCEOUTPUT_NAME);
      sb.append("='");
      sb.append(formatForceOutput());
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

  private String formatForceOutput() // format output // TODO ensure correct MFString output
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
    Vector<String> v = new Vector<>();
    s = s.trim();
    if(s.length()>0) {
      if(!s.startsWith("\""))
        v.add(s);
      else {
        s = s.substring(1);
        if(s.endsWith("\"")); // TODO why is this if condition terminated by null semicolon ; ? see TEXT.java
        s = s.substring(0,s.length()-1);

        String[] sa = s.replace(',', ' ').trim().split("\"\\s*\"");  // quote front and back, white between
        for (String str : sa)
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

  public String getAnchorPointX()
  {
    return anchorPointX.toString();
  }

  public void setAnchorPointX(String anchorPointX)
  {
    this.anchorPointX = new SFFloat(anchorPointX,0.0f,null,true);
  }

  public String getAnchorPointY()
  {
    return anchorPointY.toString();
  }

  public void setAnchorPointY(String anchorPointY)
  {
    this.anchorPointY = new SFFloat(anchorPointY,0.0f,null,true);
  }

  public String getAnchorPointZ()
  {
    return anchorPointZ.toString();
  }

  public void setAnchorPointZ(String anchorPointZ)
  {
    this.anchorPointZ = new SFFloat(anchorPointZ,0.0f,null,true);
  }

}
