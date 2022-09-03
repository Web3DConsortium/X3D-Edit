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
import org.web3d.x3d.types.X3DSoundSourceNode;

import static org.web3d.x3d.types.X3DPrimitiveTypes.*;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.palette.X3DPaletteUtilities.*;

/**
 * AUDIOCLIP.java
 * Created on Sep 12, 2007, 2:46 PM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 */
public class AUDIOCLIP extends X3DSoundSourceNode
{
  private String description;
  private boolean loop, loopDefault;;
  private SFFloat pitch, pitchDefault;
  private SFFloat pauseTime, pauseTimeDefault;
  private SFFloat resumeTime, resumeTimeDefault;
  private SFFloat startTime, startTimeDefault;
  private SFFloat stopTime, stopTimeDefault;
  private String[] urls, urlsDefault;
    
  public AUDIOCLIP()
  {
  }
  
  @Override
  public String getElementName()
  {
    return AUDIOCLIP_ELNAME;
  }

  @Override
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return AUDIOCLIPCustomizer.class;
  }

  @Override
  public void initialize()
  {
    description = AUDIOCLIP_ATTR_DESCRIPTION_DFLT;
    loop       = loopDefault       = Boolean.parseBoolean(AUDIOCLIP_ATTR_LOOP_DFLT);
    pitch      = pitchDefault      = new SFFloat(AUDIOCLIP_ATTR_PITCH_DFLT,0.f,null);
    pauseTime  = pauseTimeDefault  = new SFFloat(AUDIOCLIP_ATTR_PAUSETIME_DFLT,null,null);
    resumeTime = resumeTimeDefault = new SFFloat(AUDIOCLIP_ATTR_RESUMETIME_DFLT,null,null);
    startTime  = startTimeDefault  = new SFFloat(AUDIOCLIP_ATTR_STARTTIME_DFLT,null,null);
    stopTime   = stopTimeDefault   = new SFFloat(AUDIOCLIP_ATTR_STOPTIME_DFLT,null,null);
    if(AUDIOCLIP_ATTR_URL_DFLT.length()>0)
      urls = urlsDefault = parseUrlsIntoStringArray(AUDIOCLIP_ATTR_URL_DFLT);
    else
      urls = urlsDefault = new String[0];
   }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;

    attr = root.getAttribute(AUDIOCLIP_ATTR_DESCRIPTION_NAME);
    if (attr != null)
      description = attr.getValue();
    attr = root.getAttribute(AUDIOCLIP_ATTR_LOOP_NAME);
    if (attr != null)
      loop = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(AUDIOCLIP_ATTR_PITCH_NAME);
    if (attr != null)
      pitch = new SFFloat(attr.getValue(), 0.f, null);
    attr = root.getAttribute(AUDIOCLIP_ATTR_PAUSETIME_NAME);
    if (attr != null)
      pauseTime = new SFFloat(attr.getValue(), null, null);
    attr = root.getAttribute(AUDIOCLIP_ATTR_RESUMETIME_NAME);
    if (attr != null)
      resumeTime = new SFFloat(attr.getValue(), null, null);
    attr = root.getAttribute(AUDIOCLIP_ATTR_STARTTIME_NAME);
    if (attr != null)
      startTime = new SFFloat(attr.getValue(), null, null);
    attr = root.getAttribute(AUDIOCLIP_ATTR_STOPTIME_NAME);
    if (attr != null)
      stopTime = new SFFloat(attr.getValue(), null, null);
    attr = root.getAttribute(AUDIOCLIP_ATTR_URL_NAME);
    if (attr != null)
      urls = parseUrlsIntoStringArray(attr.getValue());
  }
  
  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    if (AUDIOCLIP_ATTR_DESCRIPTION_REQD || !description.equals(AUDIOCLIP_ATTR_DESCRIPTION_DFLT)) {
      sb.append(" ");
      sb.append(AUDIOCLIP_ATTR_DESCRIPTION_NAME);
      sb.append("='");
      sb.append(escapeXmlCharacters(description));
      sb.append("'");
    }
    if (AUDIOCLIP_ATTR_LOOP_REQD || loop != loopDefault) {
      sb.append(" ");
      sb.append(AUDIOCLIP_ATTR_LOOP_NAME);
      sb.append("='");
      sb.append(loop);
      sb.append("'");
    }
    if (AUDIOCLIP_ATTR_PAUSETIME_REQD || !pauseTime.equals(pauseTimeDefault)) {
      sb.append(" ");
      sb.append(AUDIOCLIP_ATTR_PAUSETIME_NAME);
      sb.append("='");
      sb.append(pauseTime);
      sb.append("'");
    }
    if (AUDIOCLIP_ATTR_PITCH_REQD || !pitch.equals(pitchDefault)) {
      sb.append(" ");
      sb.append(AUDIOCLIP_ATTR_PITCH_NAME);
      sb.append("='");
      sb.append(pitch);
      sb.append("'");
    }
    if (AUDIOCLIP_ATTR_RESUMETIME_REQD || !resumeTime.equals(resumeTimeDefault)) {
      sb.append(" ");
      sb.append(AUDIOCLIP_ATTR_RESUMETIME_NAME);
      sb.append("='");
      sb.append(resumeTime);
      sb.append("'");
    }
    if (AUDIOCLIP_ATTR_STARTTIME_REQD || !startTime.equals(startTimeDefault)) {
      sb.append(" ");
      sb.append(AUDIOCLIP_ATTR_STARTTIME_NAME);
      sb.append("='");
      sb.append(startTime);
      sb.append("'");
    }
    if (AUDIOCLIP_ATTR_STOPTIME_REQD || !stopTime.equals(stopTimeDefault)) {
      sb.append(" ");
      sb.append(AUDIOCLIP_ATTR_STOPTIME_NAME);
      sb.append("='");
      sb.append(stopTime);
      sb.append("'");
    }
    if (AUDIOCLIP_ATTR_URL_REQD || (!Arrays.equals(urls, urlsDefault) && (urls.length > 0))) {
      sb.append(" ");
      sb.append(AUDIOCLIP_ATTR_URL_NAME);
      sb.append("='");
      sb.append(formatStringArray(urls));
      sb.append("'");
    }
    return sb.toString();
  }
  
  public String getDescription()
  {
    return description;
  }

  public void setDescription(String description)
  {
    this.description = description;
  }

  public boolean isLoop()
  {
    return loop;
  }

  public void setLoop(boolean loop)
  {
    this.loop = loop;
  }

  public String getPauseTime()
  {
    return pauseTime.toString();
  }

  public void setPauseTime(String pauseTime)
  {
    this.pauseTime = new SFFloat(pauseTime,null,null);
  }

  public String getPitch()
  {
    return pitch.toString();
  }

  public void setPitch(String pitch)
  {
    this.pitch = new SFFloat(pitch,null,null);
  }

  public String getResumeTime()
  {
    return resumeTime.toString();
  }

  public void setResumeTime(String resumeTime)
  {
    this.resumeTime = new SFFloat(resumeTime,null,null);
  }

  public String getStartTime()
  {
    return startTime.toString();
  }

  public void setStartTime(String startTime)
  {
    this.startTime = new SFFloat(startTime,null,null);
  }

  public String getStopTime()
  {
    return stopTime.toString();
  }

  public void setStopTime(String stopTime)
  {
    this.stopTime = new SFFloat(stopTime,null,null);
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
}
