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

import java.util.HashMap;
import java.util.regex.Pattern;
import static org.web3d.x3d.types.X3DSchemaData.*;

/**
 * FIELDdefaults.java
 * Created on Mar 18, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey
 * @version $Id$
 * 
 * A class to return "default" fields which should start off as selected
 * when the user selectes a particular node (type) in the ROUTE dialog.
 */
public class FIELDdefaults
{
  private HashMap<String, RouteDefault> dflts;
  
  private String INTERPOLATOR_REGEX = ".*Interpolator.*";
  private String SEQUENCER_REGEX    = ".*Sequencer.*";
  private String SENSOR_REGEX       = ".*Sensor";
  private String LIGHT_REGEX        = ".*Light";
  private String METADATA_REGEX     = "Metadata.*";
    
  private Pattern[] regExs = {
    Pattern.compile(INTERPOLATOR_REGEX),
    Pattern.compile(SEQUENCER_REGEX),
    Pattern.compile(SENSOR_REGEX),
    Pattern.compile(LIGHT_REGEX),
    Pattern.compile(METADATA_REGEX),
  };
  
  private String[][] defaultArray = new String[][]{    
    {TIMESENSOR_ELNAME,        "fraction",     "startTime"},
    {MOVIETEXTURE_ELNAME,      "isActive",     "startTime"},  // or duration_changed
    {GEOPOSITIONINTERPOLATOR_ELNAME,  "geovalue",     "fraction"}, //geovalue_changed, set_fraction
    {KEYSENSOR_ELNAME,         "keyPress",     "enabled"},
    {LOADSENSOR_ELNAME,        "isLoaded",     "enabled"},    // or loadTime
    {STRINGSENSOR_ELNAME,      "finalText",    "enabled"},
    {BACKGROUND_ELNAME,        "isBound",      "bind"},       // or bindTime ,set_bind
    {TEXTUREBACKGROUND_ELNAME, "isBound",      "bind"},       // or bindTime ,set_bind
    {FOG_ELNAME,               "isBound",      "bind"},       // or bindTime ,set_bind
    {NAVINFO_ELNAME,           "isBound",      "bind"},       // or bindTime ,set_bind
    {VIEWPOINT_ELNAME,         "isBound",      "bind"},       // or bindTime ,set_bind
    {AUDIOCLIP_ELNAME,         "isActive",     "startTime"},
    {BOOLEANFILTER_ELNAME,     "inputNegate",  "boolean"},     //set_boolean
    {BOOLEANTOGGLE_ELNAME,     "toggle",       "boolean"},     //set_boolean
    {BOOLEANTRIGGER_ELNAME,    "triggerTrue",  "triggerTime"}, //set_triggerTime
    {INTEGERTRIGGER_ELNAME,    "triggerValue", "boolean"},     //set_boolean
    {TIMETRIGGER_ELNAME,       "triggerTime",  "boolean"},     //set_boolean
    {COLLISION_ELNAME,         "isActive",     "enabled"},     // or collideTime
    {ELEVATIONGRID_ELNAME,      null,          "height"},      //set_height
    {GEOELEVATIONGRID_ELNAME,   null,          "height"},      //set_height
    {FILLPROPERTIES_ELNAME,     null,          "hatched"},     //set_hatched
    {INLINE_ELNAME,            "url",          "load"},        //set_url
    {LOD_ELNAME,               "level",         null},         //level_changed
    {SWITCH_ELNAME,            "whichChoice",  "whichChoice"},
    {TEXT_ELNAME,              "string",       "string"},      // string_changed, set_string
    {TRANSFORM_ELNAME,         "translation",  "translation"}, // translation_changed, set_translation
    {ESPDUTRANSFORM_ELNAME,    "translation",  "translation"}, // translation_changed, set_translation
    
    {INTERPOLATOR_REGEX,       "value",        "fraction"},    // value_changed, set_fraction
    {SEQUENCER_REGEX,          "value",        "fraction"},    // value_changed, set_fraction
    {SENSOR_REGEX,             "isActive",     "enabled"},
    {LIGHT_REGEX,              "on",           "on"},
    {METADATA_REGEX,           "value",        "value"},       // value_changed, set_value
  };

  public FIELDdefaults()
  {
    dflts = new HashMap<String, RouteDefault>();
    for(String[] sa : defaultArray)
      dflts.put(sa[0], new RouteDefault(sa[1],sa[2]));
  }

  public RouteDefault getDefaults(String nodeType)
  {
    RouteDefault rd = dflts.get(nodeType);
    if(rd == null) {
      Pattern p = foundWildCard(nodeType);
      if(p != null)
        rd = dflts.get(p.pattern());
    }
    return rd;
  }

  private Pattern foundWildCard(String s)
  {
    for(Pattern pat : regExs) {
      if(pat.matcher(s).matches())
        return pat;
    }
    return null;
  }
  
  public static class RouteDefault
  {
    public String from;
    public String to;

    private RouteDefault(String from, String to)
    {
      this.from = from;
      this.to = to;
    }
  }
}
