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

import java.util.Arrays;
import javax.swing.text.JTextComponent;
import static org.web3d.x3d.palette.X3DPaletteUtilities.escapeXmlCharacters;
import org.web3d.x3d.types.X3DBackgroundNode;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * BACKGROUND.java
 * Created on July 11, 2007, 5:13 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class BACKGROUND extends X3DBackgroundNode
{
  private SFFloat[][] groundColor,groundColorDefault;
  private SFFloat[]   groundAngle, groundAngleDefault;
  private SFFloat[][] skyColor, skyColorDefault;
  private SFFloat[]   skyAngle, skyAngleDefault;
  
  private String[] leftUrl,   leftUrlDefault;
  private String[] rightUrl,  rightUrlDefault;
  private String[] frontUrl,  frontUrlDefault;
  private String[] backUrl,   backUrlDefault;
  private String[] topUrl,    topUrlDefault;
  private String[] bottomUrl, bottomUrlDefault;;

  public BACKGROUND()
  {
  }

  @Override
  public String getElementName()
  {
    return BACKGROUND_ELNAME;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return BACKGROUNDCustomizer.class;
  }

  @Override
  public void initialize()
  {
    String[] sa;
    if(BACKGROUND_ATTR_GROUNDANGLE_DFLT == null || BACKGROUND_ATTR_GROUNDANGLE_DFLT.length()<=0)
      sa = new String[]{};
    else
      sa = parseX(BACKGROUND_ATTR_GROUNDANGLE_DFLT);
    groundAngle = groundAngleDefault = parseToSFFloatArray(sa); //new SFFloat(BACKGROUND_ATTR_GROUNDANGLE_DFLT, 0.0f, (float)Math.PI/2.0f);

    if(BACKGROUND_ATTR_GROUNDCOLOR_DFLT.length() <=0 )
      sa = new String[]{};
    else
      sa = parseX(BACKGROUND_ATTR_GROUNDCOLOR_DFLT);
    groundColor = groundColorDefault = parseToSFFloatTable(sa,3);

    if(BACKGROUND_ATTR_SKYANGLE_DFLT == null || BACKGROUND_ATTR_SKYANGLE_DFLT.length()<=0)
      sa = new String[]{};
    else
      sa = parseX(BACKGROUND_ATTR_SKYANGLE_DFLT);
    skyAngle = skyAngleDefault = parseToSFFloatArray(sa);
    
    if(BACKGROUND_ATTR_SKYCOLOR_DFLT.length() <=0 )
      sa = new String[]{};
    else
      sa = parseX(BACKGROUND_ATTR_SKYCOLOR_DFLT);
    skyColor = skyColorDefault = parseToSFFloatTable(sa,3);

    if(BACKGROUND_ATTR_LEFTURL_DFLT.length()>0)
      leftUrl = leftUrlDefault = parseUrlsIntoStringArray(BACKGROUND_ATTR_LEFTURL_DFLT);
    else
      leftUrl = leftUrlDefault = new String[0];

    if(BACKGROUND_ATTR_RIGHTURL_DFLT.length()>0)
      rightUrl = rightUrlDefault = parseUrlsIntoStringArray(BACKGROUND_ATTR_RIGHTURL_DFLT);
    else
      rightUrl = rightUrlDefault = new String[0];

    if(BACKGROUND_ATTR_TOPURL_DFLT.length()>0)
      topUrl = topUrlDefault = parseUrlsIntoStringArray(BACKGROUND_ATTR_TOPURL_DFLT);
    else
      topUrl = topUrlDefault = new String[0];

    if(BACKGROUND_ATTR_BOTTOMURL_DFLT.length()>0)
      bottomUrl = bottomUrlDefault = parseUrlsIntoStringArray(BACKGROUND_ATTR_BOTTOMURL_DFLT);
    else
      bottomUrl = bottomUrlDefault = new String[0];

    if(BACKGROUND_ATTR_BACKURL_DFLT.length()>0)
      backUrl = backUrlDefault = parseUrlsIntoStringArray(BACKGROUND_ATTR_BACKURL_DFLT);
    else
      backUrl = backUrlDefault = new String[0];

    if(BACKGROUND_ATTR_FRONTURL_DFLT.length()>0)
      frontUrl = frontUrlDefault = parseUrlsIntoStringArray(BACKGROUND_ATTR_FRONTURL_DFLT);
    else
      frontUrl = frontUrlDefault = new String[0];
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
    
    attr = root.getAttribute(BACKGROUND_ATTR_LEFTURL_NAME);
    if (attr != null)
      leftUrl = parseUrlsIntoStringArray(attr.getValue());
    attr = root.getAttribute(BACKGROUND_ATTR_RIGHTURL_NAME);
    if (attr != null)
      rightUrl = parseUrlsIntoStringArray(attr.getValue());
    attr = root.getAttribute(BACKGROUND_ATTR_FRONTURL_NAME);
    if (attr != null)
      frontUrl = parseUrlsIntoStringArray(attr.getValue());
    attr = root.getAttribute(BACKGROUND_ATTR_BACKURL_NAME);
    if (attr != null)
      backUrl = parseUrlsIntoStringArray(attr.getValue());
    attr = root.getAttribute(BACKGROUND_ATTR_TOPURL_NAME);
    if (attr != null)
      topUrl = parseUrlsIntoStringArray(attr.getValue());
    attr = root.getAttribute(BACKGROUND_ATTR_BOTTOMURL_NAME);
    if (attr != null)
      bottomUrl = parseUrlsIntoStringArray(attr.getValue());
  }

  @Override
  public String createAttributes()
  {
    // note canonical form is less reviewable
    StringBuffer sb = new StringBuffer();
    writeUrlAttribute(sb, BACKGROUND_ATTR_BACKURL_REQD,  backUrlDefault,  BACKGROUND_ATTR_BACKURL_NAME,  backUrl);
    writeUrlAttribute(sb, BACKGROUND_ATTR_BOTTOMURL_REQD,bottomUrlDefault,BACKGROUND_ATTR_BOTTOMURL_NAME,bottomUrl);
    writeUrlAttribute(sb, BACKGROUND_ATTR_FRONTURL_REQD, frontUrlDefault, BACKGROUND_ATTR_FRONTURL_NAME, frontUrl);
    if ((BACKGROUND_ATTR_GROUNDANGLE_REQD || !arraysIdenticalOrNull(groundAngle,groundAngleDefault)) && groundAngle.length > 0) {
      sb.append(" ");
      sb.append(BACKGROUND_ATTR_GROUNDANGLE_NAME);
      sb.append("='");
      sb.append(formatFloatArray(groundAngle));
      sb.append("'");
    }
    if ((BACKGROUND_ATTR_GROUNDCOLOR_REQD || !arraysIdenticalOrNull(groundColor,groundColorDefault)) && groundColor.length > 0) { 
      sb.append(" ");
      sb.append(BACKGROUND_ATTR_GROUNDCOLOR_NAME);
      sb.append("='");
      sb.append(formatFloatArray(groundColor));
      sb.append("'");
    }
    writeUrlAttribute(sb, BACKGROUND_ATTR_LEFTURL_REQD,  leftUrlDefault,  BACKGROUND_ATTR_LEFTURL_NAME,  leftUrl);
    writeUrlAttribute(sb, BACKGROUND_ATTR_RIGHTURL_REQD, rightUrlDefault, BACKGROUND_ATTR_RIGHTURL_NAME, rightUrl);
    if ((BACKGROUND_ATTR_SKYANGLE_REQD || !arraysIdenticalOrNull(skyAngle,skyAngleDefault)) && skyAngle.length > 0) {
      sb.append(" ");
      sb.append(BACKGROUND_ATTR_SKYANGLE_NAME);
      sb.append("='");
      sb.append(formatFloatArray(skyAngle));
      sb.append("'");
    }
    if ((BACKGROUND_ATTR_SKYCOLOR_REQD || !arraysIdenticalOrNull(skyColor,skyColorDefault)) && skyColor.length > 0) {
      sb.append(" ");
      sb.append(BACKGROUND_ATTR_SKYCOLOR_NAME);
      sb.append("='");
      sb.append(formatFloatArray(skyColor));
      sb.append("'");
    }
    writeUrlAttribute(sb, BACKGROUND_ATTR_TOPURL_REQD,   topUrlDefault,   BACKGROUND_ATTR_TOPURL_NAME,   topUrl);
    
    return sb.toString();
  }

  public void writeUrlAttribute(StringBuffer sb, boolean isRequired, String[] defaultValue, String name, String[] urlsValue)
  {
    if ((isRequired || !Arrays.equals(urlsValue, defaultValue)) && urlsValue.length>0) {
      sb.append(" ");
      sb.append(escapeXmlCharacters(name));
      sb.append("='");
      sb.append(formatStringArray(urlsValue));
      sb.append("'");
    }   
  }
  public String[] reverse (String[] array)
  {
    String[] reversedArray = new String[array.length];
    for (int i = 0; i < array.length; i++)
    {
        reversedArray[i] = array[array.length - 1 - i];
    }
    return reversedArray;
  }
  public String[][] reverse (String[][] array)
  {
    String[][] reversedArray = new String[array.length][];
    for (int i = 0; i < array.length; i++)
    {
        reversedArray[i] = array[array.length - 1 - i];
    }
    return reversedArray;
  }
  public String[] getGroundAngleArray()
  {
    // need to reverse order of displayed groundArray/groundColor table
    return reverse(getAngleArray(groundAngle));
  }
  public String[] getSkyAngleArray()
  {
    return getAngleArray(skyAngle);
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
  public void setGroundAngleArray(String[] sa)
  {
      if(sa == null || sa.length <=0)
          groundAngle = new SFFloat[]{};
      else {
          SFFloat[] fa = new SFFloat[sa.length];
      // need to reverse order of displayed groundArray/groundColor table
      for (int i = 0; i < sa.length; i++)
      {
        String s = sa[i];
        if      (s.equalsIgnoreCase("nadir"))
          s = "0.0";
        else if (s.equalsIgnoreCase("horizon"))
          s = "1.57079";
        else if (s.trim().startsWith("1.571"))
          s = "1.57079";
        fa[sa.length - 1 - i] = new SFFloat(s, 0.0f, 1.571f); // (float) Math.PI / 2.0f);
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
        if      (s.equalsIgnoreCase("zenith"))
          s = "0.0";
        else if (s.equalsIgnoreCase("horizon"))
          s = "1.57079";
        else if (s.trim().startsWith("1.571"))
          s = "1.57079";
        else if (s.equalsIgnoreCase("nadir"))
          s = "3.14159";
        fa[i++] = new SFFloat(s, 0.0f, (float) Math.PI); // skyAngle up to 180 degrees
      }
      skyAngle = fa;
    }
  }
  
  public String[][] getGroundColorArray()
  {
    return reverse(getColorArray(groundColor));
  }
  public String[][] getSkyColorArray()
  {
    return getColorArray(skyColor);
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
  public void setGroundColorArray(String[][] saa)
  {
    if(saa == null || saa.length <=0)
      groundColor = new SFFloat[][]{};
    else {
      // need to reverse order of displayed groundArray/groundColor table
      SFFloat[][] faa = new SFFloat[saa.length][3];
      for (int i = 0; i < saa.length; i++)
      {
        String[] sa = saa[i];
        SFFloat[] fa = new SFFloat[3];
        int j=0;
        for(String s : sa) {
          fa[j++] = new SFFloat(s,0f,1.0f);
        }
        faa[saa.length - 1 - i] = fa;
      }
      groundColor = faa;
    }
  }  
  
  public void setSkyColorArray(String[][] saa)
  {
    if(saa == null || saa.length <=0)
      skyColor = new SFFloat[][]{};
    else {
      int i=0;
      SFFloat[][] faa = new SFFloat[saa.length][3];
      for(String[]sa : saa) {
        SFFloat[] fa = new SFFloat[3];
        int j=0;
        for(String s : sa)
          fa[j++] = new SFFloat(s,0f,1.0f);
        faa[i++] = fa;
      }
      skyColor = faa;
    }
  }
  
  public String[] getLeftUrls()
  {
    return leftUrl;
  }

  public void setLeftUrls(String[] leftUrl)
  {
    this.leftUrl = leftUrl;
  }

  public String[] getRightUrls()
  {
    return rightUrl;
  }

  public void setRightUrls(String[] rightUrl)
  {
    this.rightUrl = rightUrl;
  }

  public String[] getFrontUrls()
  {
    return frontUrl;
  }

  public void setFrontUrls(String[] frontUrl)
  {
    this.frontUrl = frontUrl;
  }

  public String[] getBackUrls()
  {
    return backUrl;
  }

  public void setBackUrls(String[] backUrl)
  {
    this.backUrl = backUrl;
  }

  public String[] getTopUrls()
  {
    return topUrl;
  }

  public void setTopUrls(String[] topUrl)
  {
    this.topUrl = topUrl;
  }

  public String[] getBottomUrls()
  {
    return bottomUrl;
  }

  public void setBottomUrls(String[] backUrl)
  {
    this.bottomUrl = backUrl;
  }

}