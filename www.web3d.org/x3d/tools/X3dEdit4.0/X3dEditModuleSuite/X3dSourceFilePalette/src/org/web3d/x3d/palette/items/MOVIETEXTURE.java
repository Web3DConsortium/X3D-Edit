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

import org.web3d.x3d.types.X3DTexture2DNode;
import java.util.Arrays;
import javax.swing.text.JTextComponent;
import static org.web3d.x3d.palette.X3DPaletteUtilities.escapeXmlCharacters;

import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * MOVIETEXTURE.java
 * Created on Sep 10, 2007, 3:05 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */
public class MOVIETEXTURE extends X3DTexture2DNode // X3D MovieTexture also extends X3DSoundSourceNode marker interface
{
  private String   description;
  private boolean  loop;
  private SFFloat  resumeTime,resumeTimeDefault;
  private SFFloat  pauseTime,pauseTimeDefault;
  private SFFloat  speed,speedDefault;
  private SFFloat  startTime,startTimeDefault;
  private SFFloat  stopTime,stopTimeDefault;
  private String[] urls, urlsDefault;
  private boolean  insertCommas, insertLineBreaks = false;
  
  public MOVIETEXTURE()
  {
  }
  
  @Override
  public String getDefaultContainerField()
  {
      // According to the schema, the containerField for this node is texture or source, depending on whether the 
      // movie is an image texture or a sound source...
      
      return "texture";  // "source"
  }
  
  @Override
  public String getElementName()
  {
    return MOVIETEXTURE_ELNAME;
  }

  @Override
  public void initialize()
  {
    description = MOVIETEXTURE_ATTR_DESCRIPTION_DFLT;
    repeatS = Boolean.parseBoolean(MOVIETEXTURE_ATTR_REPEATS_DFLT);
    repeatT = Boolean.parseBoolean(MOVIETEXTURE_ATTR_REPEATT_DFLT);
    loop    = Boolean.parseBoolean(MOVIETEXTURE_ATTR_LOOP_DFLT);
    
    resumeTime = resumeTimeDefault = new SFFloat(MOVIETEXTURE_ATTR_RESUMETIME_DFLT, null, null);
    pauseTime  = pauseTimeDefault  = new SFFloat(MOVIETEXTURE_ATTR_PAUSETIME_DFLT, null, null);
    speed      = speedDefault      = new SFFloat(MOVIETEXTURE_ATTR_SPEED_DFLT, null, null);
    startTime  = startTimeDefault  = new SFFloat(MOVIETEXTURE_ATTR_STARTTIME_DFLT, null, null);
    stopTime   = stopTimeDefault   = new SFFloat(MOVIETEXTURE_ATTR_STOPTIME_DFLT, null, null);
    
    if(MOVIETEXTURE_ATTR_URL_DFLT.length()>0)
      urls = urlsDefault = parseUrlsIntoStringArray(MOVIETEXTURE_ATTR_URL_DFLT);
    else
      urls = urlsDefault = new String[0];
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(MOVIETEXTURE_ATTR_DESCRIPTION_NAME);
    if (attr != null)
        setDescription(attr.getValue());
    attr = root.getAttribute(MOVIETEXTURE_ATTR_REPEATS_NAME);
    if (attr != null)
      repeatS = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(MOVIETEXTURE_ATTR_REPEATT_NAME);
    if (attr != null)
      repeatT = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(MOVIETEXTURE_ATTR_LOOP_NAME);
    if (attr != null)
      loop = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(MOVIETEXTURE_ATTR_RESUMETIME_NAME);
    if (attr != null)
      resumeTime = new SFFloat(attr.getValue(), null, null);
    attr = root.getAttribute(MOVIETEXTURE_ATTR_PAUSETIME_NAME);
    if (attr != null)
      pauseTime = new SFFloat(attr.getValue(), null, null);
    attr = root.getAttribute(MOVIETEXTURE_ATTR_SPEED_NAME);
    if (attr != null)
      speed = new SFFloat(attr.getValue(), null, null);
    attr = root.getAttribute(MOVIETEXTURE_ATTR_STARTTIME_NAME);
    if (attr != null)
      startTime = new SFFloat(attr.getValue(), null, null);
    attr = root.getAttribute(MOVIETEXTURE_ATTR_STOPTIME_NAME);
    if (attr != null)
      stopTime = new SFFloat(attr.getValue(), null, null);
    attr = root.getAttribute(MOVIETEXTURE_ATTR_URL_NAME);
    if (attr != null)
    {
         urls = parseUrlsIntoStringArray(attr.getValue());

         if (attr.getValue().contains(","))  insertCommas     = true;
         if (attr.getValue().contains("\n") ||
             attr.getValue().contains("\r")) insertLineBreaks = true; // TODO not working, line breaks not being passed from JDOM
         if (insertCommas)                   insertLineBreaks = true; // workaround default, if commas were present then most likely lineBreaks also
    }
  }
  
  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return MOVIETEXTURECustomizer.class;
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    if (MOVIETEXTURE_ATTR_DESCRIPTION_REQD || !description.equals(MOVIETEXTURE_ATTR_DESCRIPTION_DFLT)) {
      sb.append(" ");
      sb.append(MOVIETEXTURE_ATTR_DESCRIPTION_NAME);
      sb.append("='");
      sb.append(escapeXmlCharacters(description));
      sb.append("'");
    }
    if (MOVIETEXTURE_ATTR_LOOP_REQD || loop != Boolean.parseBoolean(MOVIETEXTURE_ATTR_LOOP_DFLT)) {
      sb.append(" ");
      sb.append(MOVIETEXTURE_ATTR_LOOP_NAME);
      sb.append("='");
      sb.append(loop);
      sb.append("'");
    }

    if (MOVIETEXTURE_ATTR_PAUSETIME_REQD || !pauseTime.equals(pauseTimeDefault)) {
      sb.append(" ");
      sb.append(MOVIETEXTURE_ATTR_PAUSETIME_NAME);
      sb.append("='");
      sb.append(pauseTime);
      sb.append("'");
    }

    if (MOVIETEXTURE_ATTR_REPEATS_REQD || repeatS != Boolean.parseBoolean(MOVIETEXTURE_ATTR_REPEATS_DFLT)) {
      sb.append(" ");
      sb.append(MOVIETEXTURE_ATTR_REPEATS_NAME);
      sb.append("='");
      sb.append(repeatS);
      sb.append("'");
    }

    if (MOVIETEXTURE_ATTR_REPEATT_REQD || repeatT != Boolean.parseBoolean(MOVIETEXTURE_ATTR_REPEATT_DFLT)) {
      sb.append(" ");
      sb.append(MOVIETEXTURE_ATTR_REPEATT_NAME);
      sb.append("='");
      sb.append(repeatT);
      sb.append("'");
    }
    
    if (MOVIETEXTURE_ATTR_RESUMETIME_REQD || !resumeTime.equals(resumeTimeDefault)) {
      sb.append(" ");
      sb.append(MOVIETEXTURE_ATTR_RESUMETIME_NAME);
      sb.append("='");
      sb.append(resumeTime);
      sb.append("'");
    }

    if (MOVIETEXTURE_ATTR_SPEED_REQD || !speed.equals(speedDefault)) {
      sb.append(" ");
      sb.append(MOVIETEXTURE_ATTR_SPEED_NAME);
      sb.append("='");
      sb.append(speed);
      sb.append("'");
    }

    if (MOVIETEXTURE_ATTR_STARTTIME_REQD || !startTime.equals(startTimeDefault)) {
      sb.append(" ");
      sb.append(MOVIETEXTURE_ATTR_STARTTIME_NAME);
      sb.append("='");
      sb.append(startTime);
      sb.append("'");
    }

    if (MOVIETEXTURE_ATTR_STOPTIME_REQD || !stopTime.equals(stopTimeDefault)) {
      sb.append(" ");
      sb.append(MOVIETEXTURE_ATTR_STOPTIME_NAME);
      sb.append("='");
      sb.append(stopTime);
      sb.append("'");
    }

    if (MOVIETEXTURE_ATTR_URL_REQD || (!Arrays.equals(urls, urlsDefault) && (urls.length > 0))) {
      sb.append(" ");
      sb.append(MOVIETEXTURE_ATTR_URL_NAME);
      sb.append("='");
      sb.append(formatStringArray(urls, insertCommas, insertLineBreaks));
      sb.append("'");
    }
    return sb.toString();
  }

  public void setResumeTime(String s)
  {
    resumeTime = new SFFloat(s,null,null);
  }
  
  public String getResumeTime()
  {
    return resumeTime.toString();
  }
  
  public void setPauseTime(String s)
  {
    pauseTime = new SFFloat(s,null,null);
  }
  
  public String getPauseTime()
  {
    return pauseTime.toString();
  }
  
  public void setSpeed(String s)
  {
    speed = new SFFloat(s,null,null);
  }
  
  public String getSpeed()
  {
    return speed.toString();
  }
  
  public void setStartTime(String s)
  {
    startTime = new SFFloat(s,null,null);
  }
  
  public String getStartTime()
  {
    return startTime.toString();
  }
  
  public void setStopTime(String s)
  {
    stopTime = new SFFloat(s,null,null);
  }
  
  public String getStopTime()
  {
    return stopTime.toString();
  }
  
  public boolean isLoop()
  {
    return loop;
  }
  
  public void setLoop(boolean b)
  {
    this.loop = b;
  }
  
  public String[] getUrls()
  {
    String[] ret = new String[urls.length];
    System.arraycopy(urls,0,ret,0,urls.length);
    return ret;
  }

  public void setUrls(String[] urlarray)
  {
    urls = new String[urlarray.length];
    System.arraycopy(urlarray, 0, this.urls, 0, urlarray.length);
  }

    /**
     * @return the insertCommas value
     */
    public boolean isInsertCommas() {
        return insertCommas;
    }

    /**
     * @param insertCommas the insertCommas value to set
     */
    public void setInsertCommas(boolean insertCommas) {
        this.insertCommas = insertCommas;
    }

    /**
     * @return the insertLineBreaks value
     */
    public boolean isInsertLineBreaks() {
        return insertLineBreaks;
    }

    /**
     * @param insertLineBreaks the insertLineBreak values to set
     */
    public void setInsertLineBreaks(boolean insertLineBreaks) {
        this.insertLineBreaks = insertLineBreaks;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }
}
