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
import org.web3d.x3d.types.X3DBackgroundNode;
import static org.web3d.x3d.types.X3DSchemaData4.*;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * TEXTUREBACKGROUND.java
 * Created on May 27, 2008, 5:13 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class TEXTUREBACKGROUND extends X3DBackgroundNode
{
  private SFFloat[][] groundColor,groundColorDefault;
  private SFFloat[]   groundAngle, groundAngleDefault;
  private SFFloat[][] skyColor, skyColorDefault;
  private SFFloat[]   skyAngle, skyAngleDefault;
  private SFFloat     transparency, transparencyDefault;;
  
  public TEXTUREBACKGROUND()
  {
  }

  @Override
  public String getElementName()
  {
    return TEXTUREBACKGROUND_ELNAME;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return TEXTUREBACKGROUNDCustomizer.class;
  }

  @Override
  @SuppressWarnings("NestedAssignment")
  public void initialize()
  {
    String[] sa;
    if(TEXTUREBACKGROUND_ATTR_GROUNDANGLE_DFLT == null || TEXTUREBACKGROUND_ATTR_GROUNDANGLE_DFLT.length()<=0)
      sa = new String[]{};
    else
      sa = parseX(TEXTUREBACKGROUND_ATTR_GROUNDANGLE_DFLT);
    groundAngle = groundAngleDefault = parseToSFFloatArray(sa); //new SFFloat(BACKGROUND_ATTR_GROUNDANGLE_DFLT, 0.0f, (float)Math.PI/2.0f);

    if(TEXTUREBACKGROUND_ATTR_SKYANGLE_DFLT == null || TEXTUREBACKGROUND_ATTR_SKYANGLE_DFLT.length()<=0)
      sa = new String[]{};
    else
      sa = parseX(TEXTUREBACKGROUND_ATTR_SKYANGLE_DFLT);
    skyAngle = skyAngleDefault = parseToSFFloatArray(sa);
    
    if(TEXTUREBACKGROUND_ATTR_GROUNDCOLOR_DFLT.length() <=0 )
      sa = new String[]{};
    else
      sa = parseX(TEXTUREBACKGROUND_ATTR_GROUNDCOLOR_DFLT);
    groundColor = groundColorDefault = parseToSFFloatTable(sa,3);

    if(TEXTUREBACKGROUND_ATTR_SKYCOLOR_DFLT.length() <=0 )
      sa = new String[]{};
    else
      sa = parseX(TEXTUREBACKGROUND_ATTR_SKYCOLOR_DFLT);
    skyColor = skyColorDefault = parseToSFFloatTable(sa,3);

    transparency = transparencyDefault = new SFFloat(TEXTUREBACKGROUND_ATTR_TRANSPARENCY_DFLT);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;
    String[] sa;
    attr = root.getAttribute(BACKGROUND_ATTR_GROUNDANGLE_NAME);
    if (attr != null) {
      sa = parseX(attr.getValue());
      groundAngle = parseToSFFloatArray(sa);//new SFFloat(attr.getValue(), 0.0f, (float) Math.PI / 2.0f);
    }
    attr = root.getAttribute(BACKGROUND_ATTR_GROUNDCOLOR_NAME);
    if (attr != null) {
      sa = parseX(attr.getValue());
      groundColor = parseToSFFloatTable(sa,3);
    }
    
    attr = root.getAttribute(BACKGROUND_ATTR_SKYANGLE_NAME);
    if (attr != null) {
      sa = parseX(attr.getValue());
      skyAngle = parseToSFFloatArray(sa); //new SFFloat(attr.getValue(), 0.0f, (float) Math.PI);
    }    
    attr = root.getAttribute(BACKGROUND_ATTR_SKYCOLOR_NAME);
    if (attr != null) {
      sa = parseX(attr.getValue());
      skyColor = parseToSFFloatTable(sa,3);
    }
    attr = root.getAttribute(TEXTUREBACKGROUND_ATTR_TRANSPARENCY_NAME);
    if(attr != null)
      transparency = new SFFloat(attr.getValue());
  }

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    
    if(TEXTUREBACKGROUND_ATTR_TRANSPARENCY_REQD || !transparency.equals(transparencyDefault)) {
      sb.append(" ");
      sb.append(TEXTUREBACKGROUND_ATTR_TRANSPARENCY_NAME);
      sb.append("='");
      sb.append(transparency);
      sb.append("'");     
    }
    if (TEXTUREBACKGROUND_ATTR_GROUNDANGLE_REQD || !arraysIdenticalOrNull(groundAngle,groundAngleDefault)) {
      sb.append(" ");
      sb.append(TEXTUREBACKGROUND_ATTR_GROUNDANGLE_NAME);
      sb.append("='");
      sb.append(formatFloatArray(groundAngle));
      sb.append("'");
    }
    if (TEXTUREBACKGROUND_ATTR_GROUNDCOLOR_REQD || !arraysIdenticalOrNull(groundColor,groundColorDefault)) { //groundColor0.equals(groundColor0Default) || !groundColor1.equals(groundColor1Default) || !groundColor2.equals(groundColor2Default))) {
      sb.append(" ");
      sb.append(TEXTUREBACKGROUND_ATTR_GROUNDCOLOR_NAME);
      sb.append("='");
      sb.append(formatFloatArray(groundColor));
      sb.append("'");
    }
    if (TEXTUREBACKGROUND_ATTR_SKYANGLE_REQD || !arraysIdenticalOrNull(skyAngle,skyAngleDefault)) {
      sb.append(" ");
      sb.append(TEXTUREBACKGROUND_ATTR_SKYANGLE_NAME);
      sb.append("='");
      sb.append(formatFloatArray(skyAngle));
      sb.append("'");
    }
    if (TEXTUREBACKGROUND_ATTR_SKYCOLOR_REQD || !arraysIdenticalOrNull(skyColor,skyColorDefault)) { //(!skyColor0.equals(skyColor0Default) || !skyColor1.equals(skyColor1Default) || !skyColor2.equals(skyColor2Default))) {
      sb.append(" ");
      sb.append(TEXTUREBACKGROUND_ATTR_SKYCOLOR_NAME);
      sb.append("='");
      sb.append(formatFloatArray(skyColor));
      sb.append("'");
    }
    return sb.toString();
  }

  public String getTransparency()
  {
    return transparency.toString();
  }

  public void setTransparency(String s)
  {
    if (s.length() > 0)
        transparency = new SFFloat(s,0f,1f);
  }

  public String[] getGroundAngleArray()
  {
    if(groundAngle.length == 0)
      return new String[]{};
    String[] sa = new String[groundAngle.length];
    int i=0;
    for(SFFloat sf : groundAngle)
      sa[i++] = radiansFormat.format(sf.getValue());
    return sa;
  }
  public void setGroundAngleArray(String[] sa)
  {
    
    if(sa == null || sa.length <=0)
      groundAngle = new SFFloat[]{};
    else {
      SFFloat[] fa = new SFFloat[sa.length];
      int i=0;
      for(String s : sa)
      {
        if(s.equalsIgnoreCase("nadir"))
          s = "0.0";
        fa[i++] = new SFFloat(s, 0.0f, (float) Math.PI / 2.0f);
      }
      groundAngle = fa;
    }
  }
  
  public void setSkyAngleArray(String[] sa)
  {
    
    if(sa == null || sa.length <=0)
      skyAngle = new SFFloat[]{};
    else {
      SFFloat[] fa = new SFFloat[sa.length];
      int i=0;
      for(String s : sa)
      {
        if(s.equalsIgnoreCase("zenith"))
          s = "0.0";
        fa[i++] = new SFFloat(s, 0.0f, (float) Math.PI / 2.0f);
      }
      skyAngle = fa;
    }
  }
  
  public String[] getSkyAngleArray()
  {
    if(skyAngle.length == 0)
      return new String[]{};
    String[] sa = new String[skyAngle.length];
    int i=0;
    for(SFFloat sf : skyAngle)
    {
        sa[i++] = radiansFormat.format(sf.getValue());
    }
    return sa;
  }
  
  public String[][] getGroundColorArray()
  {
    if(groundColor.length == 0)
      return new String[][]{};
    
    String[][] saa = new String[groundColor.length][3];
    int i=0;
    for(SFFloat[] fa : groundColor) {
      int j=0;
      String[] sa = new String[3];
      for(SFFloat sf : fa)
      {
          sa[j++] = colorFormat.format(sf.getValue());
      }
      saa[i++] = sa;
    }
    return saa;
  }
  
  public void setGroundColorArray(String[][] saa)
  {
    if(saa == null || saa.length <=0)
      groundColor = new SFFloat[][]{};
    else {
      int i=0;
      SFFloat[][] faa = new SFFloat[saa.length][3];
      for(String[]sa : saa) {
        SFFloat[] fa = new SFFloat[3];
        int j=0;
        for(String s : sa)
        {
            fa[j++] = new SFFloat(s,0f,1.0f);
        }
        faa[i++] = fa;
      }
      groundColor = faa;
    }
  }
  
  public String[][] getSkyColorArray()
  {
    if(skyColor.length == 0)
      return new String[][]{};
    
    String[][] saa = new String[skyColor.length][3];
    int i=0;
    for(SFFloat[] fa : skyColor) {
      int j=0;
      String[] sa = new String[3];
      for(SFFloat sf : fa)
      {
          sa[j++] = colorFormat.format(sf.getValue());
      }
      saa[i++] = sa;
    }
    return saa;
  }  
  
  public void setSkyColorArray(String[][] saa)
  {
    if(saa == null || saa.length <=0)
      skyColor = new SFFloat[][]{};
    else {
      int i=0;
      SFFloat[][] faa = new SFFloat[saa.length][3];
      for(String[]sa : saa)
      {
        SFFloat[] fa = new SFFloat[3];
        int j=0;
        for(String s : sa)
        {
            fa[j++] = new SFFloat(s,0f,1.0f);
        }
        faa[i++] = fa;
      }
      skyColor = faa;
    }
  }
  public String[] getGroundAngleDefault()
  {
    return getAngleArray(groundAngleDefault);
  }
  public String[] getSkyAngleDefault()
  {
    return getAngleArray(skyAngleDefault);
  }
  private String[] getAngleArray(SFFloat[] fa)
  {
    if(fa.length == 0)
      return new String[]{};
    String[] sa = new String[fa.length];
    int i=0;
    for(SFFloat sf : fa)
       sa[i++] = radiansFormat.format(sf.getValue());
    return sa;
  }
  public String[][] getGroundColorDefault()
  {
    return getColorArray(groundColorDefault);
  }
  public String[][] getSkyColorDefault()
  {
    return getColorArray(skyColorDefault);
  }
  private String[][] getColorArray(SFFloat[][] faa)
  {
    if(faa.length == 0)
      return new String[][]{};

    String[][] saa = new String[faa.length][3];
    int i=0;
    for(SFFloat[] fa : faa) {
      int j=0;
      String[] sa = new String[3];
      for(SFFloat sf : fa)
        sa[j++] = colorFormat.format(sf.getValue());
      saa[i++] = sa;
    }
    return saa;
  }
}