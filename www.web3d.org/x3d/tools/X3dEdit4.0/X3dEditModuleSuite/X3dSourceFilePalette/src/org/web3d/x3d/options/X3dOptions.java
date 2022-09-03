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
      (https://www.nps.edu and https://MovesInstitute.nps.edu)
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

package org.web3d.x3d.options;

import java.io.File;
import java.util.Date;
import java.util.prefs.Preferences;
import org.openide.util.NbPreferences;
//import org.web3d.x3d.actions.security.BouncyCastleHelper;

/**
 * X3dOptions.java
 * Created on Apr 17, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey <jmbailey@nps.edu>
 * @version $Id$
 */
@SuppressWarnings("StaticNonFinalUsedInInitialization")
public class X3dOptions
{
  /* Security options */
  public static       String KEYSTORE_PATH_KEY = "KEYSTORE_PATH";
  public static       String KEYSTORE_PATH_DEFAULT; // set in static block
//  public static final String KEYSTORE_FILENAME_DEFAULT = new StringBuilder().append("X3D-EditKeystore.").append(BouncyCastleHelper.getKeyStoreNameExtension()).toString();
  
  public static final String USER_NAME_TOKEN = "__USER-NAME__";
  public static final String userName, x3dEditPath;
  static {
    String homeDir = System.getProperty("user.home");
          userName = System.getProperty("user.name");
       x3dEditPath = System.getProperty("user.dir"); // _path_/X3DEdit3.3/X3dEditorSuite
    
    File dir = new File(new StringBuilder().append(homeDir).append("/X3D-Edit/security").toString());
//    File fil = new File(dir,KEYSTORE_FILENAME_DEFAULT);
//    KEYSTORE_PATH_DEFAULT = fil.getAbsolutePath();
  }
  
  /* Other preferences */
  public static String  SHOW_NEWLINE_OPTION_KEY       = "SHOW_NEWLINE_OPTION";
  public static String  PREPEND_NEWLINE_KEY           = "PREPEND_NEWLINE";
  public static String  APPEND_NEWLINE_KEY            = "APPEND_NEWLINE";
  public static String  AUTOVALIDATE_KEY              = "AUTOVALIDATE";
  public static String  VISUALIZE_COORDINATE_AXES_KEY = "VISUALIZE_COORDINATE_AXES";
  public static String  VISUALIZE_CENTER_LINE_KEY     = "VISUALIZE_CENTER_LINE";
  public static String  VISUALIZE_CONE_LINES_KEY      = "VISUALIZE_CONE_LINES";
  public static String  VISUALIZE_LINECOLOR_RED_KEY   = "VISUALIZE_LINECOLOR_RED";
  public static String  VISUALIZE_LINECOLOR_GREEN_KEY = "VISUALIZE_LINECOLOR_GREEN";
  public static String  VISUALIZE_LINECOLOR_BLUE_KEY  = "VISUALIZE_LINECOLOR_BLUE";
  public static String  VISUALIZE_SHAPECOLOR_RED_KEY  = "VISUALIZE_SHAPECOLOR_RED";
  public static String  VISUALIZE_SHAPECOLOR_GREEN_KEY= "VISUALIZE_SHAPECOLOR_GREEN";
  public static String  VISUALIZE_SHAPECOLOR_BLUE_KEY = "VISUALIZE_SHAPECOLOR_BLUE";
  public static String  VISUALIZE_TRANSPARENCY_KEY    = "VISUALIZE_TRANSPARENCY";
  
  public static String  VISUALIZE_HANIM_COORDINATE_AXES_KEY   = "VISUALIZE_HANIM_COORDINATE_AXES";
  public static String  VISUALIZE_HANIMJOINTCOLOR_RED_KEY     = "VISUALIZE_HANIMJOINTCOLOR_RED";
  public static String  VISUALIZE_HANIMJOINTCOLOR_GREEN_KEY   = "VISUALIZE_HANIMJOINTCOLOR_GREEN";
  public static String  VISUALIZE_HANIMJOINTCOLOR_BLUE_KEY    = "VISUALIZE_HANIMJOINTCOLOR_BLUE";
  public static String  VISUALIZE_HANIMSEGMENTCOLOR_RED_KEY   = "VISUALIZE_HANIMSEGMENTCOLOR_RED";
  public static String  VISUALIZE_HANIMSEGMENTCOLOR_GREEN_KEY = "VISUALIZE_HANIMSEGMENTCOLOR_GREEN";
  public static String  VISUALIZE_HANIMSEGMENTCOLOR_BLUE_KEY  = "VISUALIZE_HANIMSEGMENTCOLOR_BLUE";
  public static String  VISUALIZE_HANIMSITECOLOR_RED_KEY      = "VISUALIZE_HANIMSITECOLOR_RED";
  public static String  VISUALIZE_HANIMSITECOLOR_GREEN_KEY    = "VISUALIZE_HANIMSITECOLOR_GREEN";
  public static String  VISUALIZE_HANIMSITECOLOR_BLUE_KEY     = "VISUALIZE_HANIMSITECOLOR_BLUE";
  
  // default preference, can be overridden by user choices in X3D-Edit Preferences (Tools > Options > Miscellaneous > X3D > Other Preferences panel)
  public static boolean SHOW_NEWLINE_OPTION_DEFAULT = false;
  public static boolean PREPEND_NEWLINE_DEFAULT     = true;
  public static boolean APPEND_NEWLINE_DEFAULT      = false;
  public static boolean AUTOVALIDATE_DEFAULT        = true;
  public static boolean VISUALIZE_COORDINATE_AXES_DEFAULT = true;
  public static boolean VISUALIZE_CENTER_LINE_DEFAULT     = true;
  public static String  VISUALIZE_CONE_LINES_DEFAULT      = "8";
  public static String  VISUALIZE_LINECOLOR_RED_DEFAULT   = "1";
  public static String  VISUALIZE_LINECOLOR_GREEN_DEFAULT = "0.8";
  public static String  VISUALIZE_LINECOLOR_BLUE_DEFAULT  = "0.0";
  public static String  VISUALIZE_SHAPECOLOR_RED_DEFAULT  = "0.8";
  public static String  VISUALIZE_SHAPECOLOR_GREEN_DEFAULT= "0.6";
  public static String  VISUALIZE_SHAPECOLOR_BLUE_DEFAULT = "0.0";
  public static String  VISUALIZE_TRANSPARENCY_DEFAULT    = "0.8";
  
  public static boolean VISUALIZE_HANIM_COORDINATE_AXES_DEFAULT   = false;
  public static String  VISUALIZE_HANIMJOINTCOLOR_RED_DEFAULT     = "1";
  public static String  VISUALIZE_HANIMJOINTCOLOR_GREEN_DEFAULT   = "0.5";
  public static String  VISUALIZE_HANIMJOINTCOLOR_BLUE_DEFAULT    = "0.0";
  public static String  VISUALIZE_HANIMSEGMENTCOLOR_RED_DEFAULT   = "1";
  public static String  VISUALIZE_HANIMSEGMENTCOLOR_GREEN_DEFAULT = "1";
  public static String  VISUALIZE_HANIMSEGMENTCOLOR_BLUE_DEFAULT  = "0.0";
  public static String  VISUALIZE_HANIMSITECOLOR_RED_DEFAULT      = "1";
  public static String  VISUALIZE_HANIMSITECOLOR_GREEN_DEFAULT    = "0.0";
  public static String  VISUALIZE_HANIMSITECOLOR_BLUE_DEFAULT     = "0.0";
  
  public static void    setShowNewlineOption       (boolean tf)    {commonBSet(SHOW_NEWLINE_OPTION_KEY,tf);}
  public static void    setPrependNewline          (boolean tf)    {commonBSet(    PREPEND_NEWLINE_KEY,tf);}
  public static void    setAppendNewline           (boolean tf)    {commonBSet(     APPEND_NEWLINE_KEY,tf);}
  public static void    setAutoValidate            (boolean tf)    {commonBSet(       AUTOVALIDATE_KEY,tf);}
  public static void    setVisualizeCoordinateAxes (boolean tf)    {commonBSet(VISUALIZE_COORDINATE_AXES_KEY,tf);}
  public static void    setVisualizeCenterLine     (boolean tf)    {commonBSet(VISUALIZE_CENTER_LINE_KEY,tf);}
  public static void    setVisualizeConeLines      (String count)  { commonSet(VISUALIZE_CONE_LINES_KEY,count);}
  public static void    setVisualizeLineColorRed   (String color)  { commonSet(VISUALIZE_LINECOLOR_RED_KEY,color);}
  public static void    setVisualizeLineColorGreen (String color)  { commonSet(VISUALIZE_LINECOLOR_GREEN_KEY,color);}
  public static void    setVisualizeLineColorBlue  (String color)  { commonSet(VISUALIZE_LINECOLOR_BLUE_KEY,color);}
  public static void    setVisualizeShapeColorRed   (String color) { commonSet(VISUALIZE_SHAPECOLOR_RED_KEY,color);}
  public static void    setVisualizeShapeColorGreen (String color) { commonSet(VISUALIZE_SHAPECOLOR_GREEN_KEY,color);}
  public static void    setVisualizeShapeColorBlue  (String color) { commonSet(VISUALIZE_SHAPECOLOR_BLUE_KEY,color);}
  public static void    setVisualizeTransparency   (String transparency) {commonSet(VISUALIZE_TRANSPARENCY_KEY,transparency);}
  
  public static void    setVisualizeHanimCoordinateAxes    (boolean tf)    {commonBSet(VISUALIZE_HANIM_COORDINATE_AXES_KEY,tf);}
  public static void    setVisualizeHanimJointColorRed     (String color)  { commonSet(VISUALIZE_HANIMJOINTCOLOR_RED_KEY,color);}
  public static void    setVisualizeHanimJointColorGreen   (String color)  { commonSet(VISUALIZE_HANIMJOINTCOLOR_GREEN_KEY,color);}
  public static void    setVisualizeHanimJointColorBlue    (String color)  { commonSet(VISUALIZE_HANIMJOINTCOLOR_BLUE_KEY,color);}
  public static void    setVisualizeHanimSegmentColorRed   (String color)  { commonSet(VISUALIZE_HANIMSEGMENTCOLOR_RED_KEY,color);}
  public static void    setVisualizeHanimSegmentColorGreen (String color)  { commonSet(VISUALIZE_HANIMSEGMENTCOLOR_GREEN_KEY,color);}
  public static void    setVisualizeHanimSegmentColorBlue  (String color)  { commonSet(VISUALIZE_HANIMSEGMENTCOLOR_BLUE_KEY,color);}
  public static void    setVisualizeHanimSiteColorRed      (String color)  { commonSet(VISUALIZE_HANIMSITECOLOR_RED_KEY,color);}
  public static void    setVisualizeHanimSiteColorGreen    (String color)  { commonSet(VISUALIZE_HANIMSITECOLOR_GREEN_KEY,color);}
  public static void    setVisualizeHanimSiteColorBlue     (String color)  { commonSet(VISUALIZE_HANIMSITECOLOR_BLUE_KEY,color);}

  public static boolean getShowNewlineOption()           {return commonBGet(SHOW_NEWLINE_OPTION_KEY, SHOW_NEWLINE_OPTION_DEFAULT);}
  public static boolean getPrependNewline   ()           {return commonBGet(    PREPEND_NEWLINE_KEY,     PREPEND_NEWLINE_DEFAULT);}
  public static boolean getAppendNewline    ()           {return commonBGet(     APPEND_NEWLINE_KEY,      APPEND_NEWLINE_DEFAULT);}
  public static void    resetNewLineOptions ()
  {
      setShowNewlineOption (SHOW_NEWLINE_OPTION_DEFAULT);
         setPrependNewline (PREPEND_NEWLINE_DEFAULT);
          setAppendNewline (APPEND_NEWLINE_DEFAULT);
  }
  public static boolean getAutoValidate   ()             {return commonBGet(     AUTOVALIDATE_KEY,         AUTOVALIDATE_DEFAULT);}
  public static boolean getVisualizeCoordinateAxes  ()   {return commonBGet(VISUALIZE_COORDINATE_AXES_KEY, VISUALIZE_COORDINATE_AXES_DEFAULT);}
  public static boolean getVisualizeCenterLine  ()       {return commonBGet(VISUALIZE_CENTER_LINE_KEY,     VISUALIZE_CENTER_LINE_DEFAULT);}
  public static String  getVisualizeConeLines  ()        {return commonGet (VISUALIZE_CONE_LINES_KEY,      VISUALIZE_CONE_LINES_DEFAULT);}
  public static String  getVisualizeLineColorRed  ()     {return commonGet (VISUALIZE_LINECOLOR_RED_KEY,   VISUALIZE_LINECOLOR_RED_DEFAULT);}
  public static String  getVisualizeLineColorGreen ()    {return commonGet (VISUALIZE_LINECOLOR_GREEN_KEY, VISUALIZE_LINECOLOR_GREEN_DEFAULT);}
  public static String  getVisualizeLineColorBlue ()     {return commonGet (VISUALIZE_LINECOLOR_BLUE_KEY,  VISUALIZE_LINECOLOR_BLUE_DEFAULT);}
  public static String  getVisualizeShapeColorRed  ()    {return commonGet (VISUALIZE_SHAPECOLOR_RED_KEY,  VISUALIZE_SHAPECOLOR_RED_DEFAULT);}
  public static String  getVisualizeShapeColorGreen ()   {return commonGet (VISUALIZE_SHAPECOLOR_GREEN_KEY,VISUALIZE_SHAPECOLOR_GREEN_DEFAULT);}
  public static String  getVisualizeShapeColorBlue ()    {return commonGet (VISUALIZE_SHAPECOLOR_BLUE_KEY, VISUALIZE_SHAPECOLOR_BLUE_DEFAULT);}
  public static String  getVisualizeTransparency ()      {return commonGet (VISUALIZE_TRANSPARENCY_KEY,  VISUALIZE_TRANSPARENCY_DEFAULT);}
  
  public static boolean getVisualizeHanimCoordinateAxes  ()    {return commonBGet(VISUALIZE_HANIM_COORDINATE_AXES_KEY,   VISUALIZE_HANIM_COORDINATE_AXES_DEFAULT);}
  public static String  getVisualizeHanimJointColorRed  ()     {return commonGet (VISUALIZE_HANIMJOINTCOLOR_RED_KEY,     VISUALIZE_HANIMJOINTCOLOR_RED_DEFAULT);}
  public static String  getVisualizeHanimJointColorGreen ()    {return commonGet (VISUALIZE_HANIMJOINTCOLOR_GREEN_KEY,   VISUALIZE_HANIMJOINTCOLOR_GREEN_DEFAULT);}
  public static String  getVisualizeHanimJointColorBlue ()     {return commonGet (VISUALIZE_HANIMJOINTCOLOR_BLUE_KEY,    VISUALIZE_HANIMJOINTCOLOR_BLUE_DEFAULT);}
  public static String  getVisualizeHanimSegmentColorRed  ()   {return commonGet (VISUALIZE_HANIMSEGMENTCOLOR_RED_KEY,   VISUALIZE_HANIMSEGMENTCOLOR_RED_DEFAULT);}
  public static String  getVisualizeHanimSegmentColorGreen ()  {return commonGet (VISUALIZE_HANIMSEGMENTCOLOR_GREEN_KEY, VISUALIZE_HANIMSEGMENTCOLOR_GREEN_DEFAULT);}
  public static String  getVisualizeHanimSegmentColorBlue ()   {return commonGet (VISUALIZE_HANIMSEGMENTCOLOR_BLUE_KEY,  VISUALIZE_HANIMSEGMENTCOLOR_BLUE_DEFAULT);}
  public static String  getVisualizeHanimSiteColorRed  ()      {return commonGet (VISUALIZE_HANIMSITECOLOR_RED_KEY,      VISUALIZE_HANIMSITECOLOR_RED_DEFAULT);}
  public static String  getVisualizeHanimSiteColorGreen ()     {return commonGet (VISUALIZE_HANIMSITECOLOR_GREEN_KEY,    VISUALIZE_HANIMSITECOLOR_GREEN_DEFAULT);}
  public static String  getVisualizeHanimSiteColorBlue ()      {return commonGet (VISUALIZE_HANIMSITECOLOR_BLUE_KEY,     VISUALIZE_HANIMSITECOLOR_BLUE_DEFAULT);}
  
  public static void    resetVisualizeSettings ()
  {
      setVisualizeLineColorRed   (VISUALIZE_LINECOLOR_RED_DEFAULT);
      setVisualizeLineColorGreen (VISUALIZE_LINECOLOR_GREEN_DEFAULT);
      setVisualizeLineColorBlue  (VISUALIZE_LINECOLOR_BLUE_DEFAULT);
      setVisualizeShapeColorRed  (VISUALIZE_SHAPECOLOR_RED_DEFAULT);
      setVisualizeShapeColorGreen(VISUALIZE_SHAPECOLOR_GREEN_DEFAULT);
      setVisualizeShapeColorBlue (VISUALIZE_SHAPECOLOR_BLUE_DEFAULT);
      setVisualizeTransparency   (VISUALIZE_TRANSPARENCY_DEFAULT);
  }
  
  public static void    resetVisualizeHanimSettings ()
  {
      setVisualizeHanimJointColorRed     (VISUALIZE_HANIMJOINTCOLOR_RED_DEFAULT);
      setVisualizeHanimJointColorGreen   (VISUALIZE_HANIMJOINTCOLOR_GREEN_DEFAULT);
      setVisualizeHanimJointColorBlue    (VISUALIZE_HANIMJOINTCOLOR_BLUE_DEFAULT);
      setVisualizeHanimSegmentColorRed   (VISUALIZE_HANIMSEGMENTCOLOR_RED_DEFAULT);
      setVisualizeHanimSegmentColorGreen (VISUALIZE_HANIMSEGMENTCOLOR_GREEN_DEFAULT);
      setVisualizeHanimSegmentColorBlue  (VISUALIZE_HANIMSEGMENTCOLOR_BLUE_DEFAULT);
      setVisualizeHanimSiteColorRed      (VISUALIZE_HANIMSITECOLOR_RED_DEFAULT);
      setVisualizeHanimSiteColorGreen    (VISUALIZE_HANIMSITECOLOR_GREEN_DEFAULT);
      setVisualizeHanimSiteColorBlue     (VISUALIZE_HANIMSITECOLOR_BLUE_DEFAULT);
  }
  
  public static String  getVisualizeLineColor ()          {return new StringBuilder().append(getVisualizeLineColorRed()).append(" ").append(getVisualizeLineColorGreen()).append(" ").append(getVisualizeLineColorBlue()).toString();}
  public static String  getVisualizeShapeColor ()         {return new StringBuilder().append(getVisualizeShapeColorRed()).append(" ").append(getVisualizeShapeColorGreen()).append(" ").append(getVisualizeShapeColorBlue()).toString();}

  /* External viewers options */
  public static String LAUNCH_INTERVAL_DEFAULT = "1"; // units in seconds;
  
  public static String CONTACT_EXECUTABLE_PATH_KEY            = "CONTACT_EXECUTABLE_PATH";
  public static String CONTACT_GEO_EXECUTABLE_PATH_KEY        = "CONTACT_GEO_EXECUTABLE_PATH";
  public static String FREEWRL_EXECUTABLE_PATH_KEY            = "FREEWRL_EXECUTABLE_PATH";
  public static String H3D_EXECUTABLE_PATH_KEY                = "H3D_EXECUTABLE_PATH";
  public static String HEILAN_EXECUTABLE_PATH_KEY             = "HEILAN_EXECUTABLE_PATH";
  public static String INSTANTREALITY_EXECUTABLE_PATH_KEY     = "INSTANTREALITY_EXECUTABLE_PATH";
  public static String POLYTRANSNUGRAF_EDITOR_PATH_KEY        = "POLYTRANSNUGRAF_EDITOR_PATH";
  public static String OCTAGA_EXECUTABLE_PATH_KEY             = "OCTAGA_EXECUTABLE_PATH";
  public static String SWIRLX3DPLAYER_EXECUTABLE_PATH_KEY     = "SWIRLX3DPLAYER_EXECUTABLE_PATH";
  public static String VIEW3DSCENE_EXECUTABLE_PATH_KEY        = "VIEW3DSCENE_EXECUTABLE_PATH";
  public static String VIVATYPLAYER_EXECUTABLE_PATH_KEY       = "VIVATYPLAYER_EXECUTABLE_PATH";
  public static String XJ3D_EXECUTABLE_PATH_KEY               = "XJ3D_EXECUTABLE_PATH";
  public static String OTHER_X3D_PLAYER_EXECUTABLE_NAME_KEY   = "OTHER_X3D_PLAYER_EXECUTABLE_NAME";
  public static String OTHER_X3D_PLAYER_EXECUTABLE_PATH_KEY   = "OTHER_X3D_PLAYER_EXECUTABLE_PATH";
  public static String OTHER_X3D_PLAYER_EXECUTABLE_SWITCH_KEY = "OTHER_X3D_PLAYER_EXECUTABLE_SWITCH";
  public static String OTHER_X3D_EDITOR_EXECUTABLE_NAME_KEY   = "OTHER_X3D_EDITOR_EXECUTABLE_NAME";
  public static String OTHER_X3D_EDITOR_EXECUTABLE_PATH_KEY   = "OTHER_X3D_EDITOR_EXECUTABLE_PATH";
  
  public static String LAUNCH_INTERVAL_KEY                    = "LAUNCH_INTERVAL";

  public static String CONTACT_EXECUTABLE_AUTOLAUNCH_KEY            = "CONTACT_AUTOLAUNCH";
  public static String CONTACT_GEO_EXECUTABLE_AUTOLAUNCH_KEY        = "CONTACT_GEO_AUTOLAUNCH";
  public static String FREEWRL_EXECUTABLE_AUTOLAUNCH_KEY            = "FREEWRL_AUTOLAUNCH";
  public static String H3D_EXECUTABLE_AUTOLAUNCH_KEY                = "H3D_AUTOLAUNCH";
  public static String HEILAN_EXECUTABLE_AUTOLAUNCH_KEY             = "HEILAN_AUTOLAUNCH";
  public static String INSTANTREALITY_EXECUTABLE_AUTOLAUNCH_KEY     = "INSTANTREALITY_AUTOLAUNCH";
  public static String OCTAGA_EXECUTABLE_AUTOLAUNCH_KEY             = "OCTAGA_AUTOLAUNCH";
  public static String SWIRLX3DPLAYER_EXECUTABLE_AUTOLAUNCH_KEY     = "SWIRLX3DPLAYER_AUTOLAUNCH";
  public static String VIEW3DSCENE_EXECUTABLE_AUTOLAUNCH_KEY        = "VIEW3DSCENE_AUTOLAUNCH";
  public static String VIVATYPLAYER_EXECUTABLE_AUTOLAUNCH_KEY       = "VIVATYPLAYER_AUTOLAUNCH";
  public static String XJ3D_EXECUTABLE_AUTOLAUNCH_KEY               = "XJ3D_AUTOLAUNCH";
  public static String OTHER_X3D_PLAYER_EXECUTABLE_AUTOLAUNCH_KEY   = "OTHER_X3D_PLAYER_AUTOLAUNCH";
  public static String OTHER_X3D_EDITOR_EXECUTABLE_AUTOLAUNCH_KEY   = "OTHER_X3D_EDITOR_AUTOLAUNCH";

  public static String AUTOLAUNCH_DEFAULT                       = "true";
  public static String HEILAN_AUTOLAUNCH_DEFAULT                = "false";
  
  public static String              AMAYA_EXECUTABLE_AUTOLAUNCH_KEY =              "AMAYA_AUTOLAUNCH";
  public static String           AUDACITY_EXECUTABLE_AUTOLAUNCH_KEY =           "AUDACITY_AUTOLAUNCH";
  public static String          MUSESCORE_EXECUTABLE_AUTOLAUNCH_KEY =          "MUSESCORE_AUTOLAUNCH";
  public static String               GIMP_EXECUTABLE_AUTOLAUNCH_KEY =               "GIMP_AUTOLAUNCH";
  public static String               FIJI_EXECUTABLE_AUTOLAUNCH_KEY =               "FIJI_AUTOLAUNCH";
  public static String             IMAGEJ_EXECUTABLE_AUTOLAUNCH_KEY =             "IMAGEJ_AUTOLAUNCH";
  public static String        IMAGEMAGICK_EXECUTABLE_AUTOLAUNCH_KEY =        "IMAGEMAGICK_AUTOLAUNCH";
  public static String                VLC_EXECUTABLE_AUTOLAUNCH_KEY =                "VLC_AUTOLAUNCH";
  public static String            BLENDER_EXECUTABLE_AUTOLAUNCH_KEY =            "BLENDER_AUTOLAUNCH";
  public static String    BSCONTENTSTUDIO_EXECUTABLE_AUTOLAUNCH_KEY =    "BSCONTENTSTUDIO_AUTOLAUNCH";
  public static String               CURA_EXECUTABLE_AUTOLAUNCH_KEY =               "CURA_AUTOLAUNCH";
  public static String            MESHLAB_EXECUTABLE_AUTOLAUNCH_KEY =            "MESHLAB_AUTOLAUNCH";
  public static String           PARAVIEW_EXECUTABLE_AUTOLAUNCH_KEY =           "PARAVIEW_AUTOLAUNCH";
  public static String        POLYTRANSNUGRAF_EDITOR_AUTOLAUNCH_KEY =    "POLYTRANSNUGRAF_AUTOLAUNCH";
  public static String         SEAMLESS3D_EXECUTABLE_AUTOLAUNCH_KEY =         "SEAMLESS3D_AUTOLAUNCH";
  public static String            ITKSNAP_EXECUTABLE_AUTOLAUNCH_KEY =            "ITKSNAP_AUTOLAUNCH";
  public static String              SEG3D_EXECUTABLE_AUTOLAUNCH_KEY =              "SEG3D_AUTOLAUNCH";
  public static String           SLICER3D_EXECUTABLE_AUTOLAUNCH_KEY =           "SLICER3D_AUTOLAUNCH";
  public static String           SVG_EDIT_EXECUTABLE_AUTOLAUNCH_KEY =           "SVG_EDIT_AUTOLAUNCH";
  public static String          WHITEDUNE_EXECUTABLE_AUTOLAUNCH_KEY =          "WHITEDUNE_AUTOLAUNCH";
  public static String            WINGS3D_EXECUTABLE_AUTOLAUNCH_KEY =            "WINGS3D_AUTOLAUNCH";
  public static String          ULTRAEDIT_EXECUTABLE_AUTOLAUNCH_KEY =          "ULTRAEDIT_AUTOLAUNCH";
  public static String OTHER_AUDIO_EDITOR_EXECUTABLE_AUTOLAUNCH_KEY = "OTHER_AUDIO_EDITOR_AUTOLAUNCH";
  public static String OTHER_HTML5_EDITOR_EXECUTABLE_AUTOLAUNCH_KEY = "OTHER_HTML5_EDITOR_AUTOLAUNCH";
  public static String OTHER_IMAGE_EDITOR_EXECUTABLE_AUTOLAUNCH_KEY = "OTHER_IMAGE_EDITOR_AUTOLAUNCH";
  public static String OTHER_VIDEO_EDITOR_EXECUTABLE_AUTOLAUNCH_KEY = "OTHER_VIDEO_EDITOR_AUTOLAUNCH";
  public static String OTHER_VOLUME_EDITOR_EXECUTABLE_AUTOLAUNCH_KEY="OTHER_VOLUME_EDITOR_AUTOLAUNCH";
  
  public static String OTHER_AUDIO_EDITOR_EXECUTABLE_NAME_KEY       = "OTHER_AUDIO_EDITOR_EXECUTABLE_NAME";
  public static String OTHER_HTML5_EDITOR_EXECUTABLE_NAME_KEY       = "OTHER_HTML5_EDITOR_EXECUTABLE_NAME";
  public static String OTHER_IMAGE_EDITOR_EXECUTABLE_NAME_KEY       = "OTHER_IMAGE_EDITOR_EXECUTABLE_NAME";
  public static String OTHER_VIDEO_EDITOR_EXECUTABLE_NAME_KEY       = "OTHER_VIDEO_EDITOR_EXECUTABLE_NAME";
  public static String OTHER_VOLUME_EDITOR_EXECUTABLE_NAME_KEY      ="OTHER_VOLUME_EDITOR_EXECUTABLE_NAME";
  
  /* External viewers options */
  public static String           AMAYA_EDITOR_PATH_KEY          =          "AMAYA_EDITOR_PATH";
  public static String        AUDACITY_EDITOR_PATH_KEY          =       "AUDACITY_EDITOR_PATH";
  public static String       MUSESCORE_EDITOR_PATH_KEY          =      "MUSESCORE_EDITOR_PATH";
  public static String            GIMP_EDITOR_PATH_KEY          =           "GIMP_EDITOR_PATH";
  public static String          IMAGEJ_EDITOR_PATH_KEY          =         "IMAGEJ_EDITOR_PATH";
  public static String     IMAGEMAGICK_EDITOR_PATH_KEY          =    "IMAGEMAGICK_EDITOR_PATH";
  public static String            FIJI_EDITOR_PATH_KEY          =           "FIJI_EDITOR_PATH";
  public static String             VLC_PLAYER_PATH_KEY          =            "VLC_PLAYER_PATH";
  public static String     BLENDER_X3D_EDITOR_PATH_KEY          =    "BLENDER_X3D_EDITOR_PATH";
  public static String BSCONTENTSTUDIO_X3D_EDITOR_PATH_KEY      = "BSCONTENTSTUDIO_X3D_EDITOR_PATH";
  public static String        CURA_X3D_EDITOR_PATH_KEY          =       "CURA_X3D_EDITOR_PATH";
  public static String     MESHLAB_X3D_EDITOR_PATH_KEY          =    "MESHLAB_X3D_EDITOR_PATH";
  public static String    PARAVIEW_X3D_EDITOR_PATH_KEY          =   "PARAVIEW_X3D_EDITOR_PATH";
  public static String  SEAMLESS3D_X3D_EDITOR_PATH_KEY          = "SEAMLESS3D_X3D_EDITOR_PATH";
  public static String  ITKSNAP_VOLUME_EDITOR_PATH_KEY          =    "ITKSNAP_X3D_EDITOR_PATH";
  public static String    SEG3D_VOLUME_EDITOR_PATH_KEY          =      "SEG3D_X3D_EDITOR_PATH";
  public static String SLICER3D_VOLUME_EDITOR_PATH_KEY          =   "SLICER3D_X3D_EDITOR_PATH";
  public static String SVG_EDIT_VOLUME_EDITOR_PATH_KEY          =   "SVG_EDIT_X3D_EDITOR_PATH";
  public static String   WHITEDUNE_X3D_EDITOR_PATH_KEY          =  "WHITEDUNE_X3D_EDITOR_PATH";
  public static String     WINGS3D_X3D_EDITOR_PATH_KEY          =    "WINGS3D_X3D_EDITOR_PATH";
  public static String   ULTRAEDIT_X3D_EDITOR_PATH_KEY          =  "ULTRAEDIT_X3D_EDITOR_PATH";
  
  public static String   OTHER_AUDIO_EDITOR_NAME_KEY            =   "OTHER_AUDIO_EDITOR_NAME";
  public static String   OTHER_HTML5_EDITOR_NAME_KEY            =   "OTHER_HTML5_EDITOR_NAME";
  public static String   OTHER_IMAGE_EDITOR_NAME_KEY            =   "OTHER_IMAGE_EDITOR_NAME";
  public static String   OTHER_VIDEO_EDITOR_NAME_KEY            =   "OTHER_VIDEO_EDITOR_NAME";
  public static String  OTHER_VOLUME_EDITOR_NAME_KEY            =  "OTHER_VOLUME_EDITOR_NAME";
  public static String     OTHER_X3D_EDITOR_NAME_KEY            =     "OTHER_X3D_EDITOR_NAME";
  public static String   OTHER_AUDIO_EDITOR_PATH_KEY            =   "OTHER_AUDIO_EDITOR_PATH";
  public static String   OTHER_HTML5_EDITOR_PATH_KEY            =   "OTHER_HTML5_EDITOR_PATH";
  public static String   OTHER_IMAGE_EDITOR_PATH_KEY            =   "OTHER_IMAGE_EDITOR_PATH";
  public static String   OTHER_VIDEO_EDITOR_PATH_KEY            =   "OTHER_VIDEO_EDITOR_PATH";
  public static String  OTHER_VOLUME_EDITOR_PATH_KEY            =  "OTHER_VOLUME_EDITOR_PATH";
  public static String     OTHER_X3D_EDITOR_PATH_KEY            =     "OTHER_X3D_EDITOR_PATH";
  public static String   OTHER_AUDIO_EDITOR_SWITCH_KEY          =   "OTHER_AUDIO_EDITOR_SWITCH";
  public static String   OTHER_HTML5_EDITOR_SWITCH_KEY          =   "OTHER_HTML5_EDITOR_SWITCH";
  public static String   OTHER_IMAGE_EDITOR_SWITCH_KEY          =   "OTHER_IMAGE_EDITOR_SWITCH";
  public static String   OTHER_VIDEO_EDITOR_SWITCH_KEY          =   "OTHER_VIDEO_EDITOR_SWITCH";
  public static String  OTHER_VOLUME_EDITOR_SWITCH_KEY          =  "OTHER_VOLUME_EDITOR_SWITCH";
  public static String     OTHER_X3D_EDITOR_SWITCH_KEY          =     "OTHER_X3D_EDITOR_SWITCH";
       
  // set in static block below
  public static String CONTACT_EXECUTABLE_PATH_DEFAULT;
  public static String CONTACT_GEO_EXECUTABLE_PATH_DEFAULT;
  public static String FREEWRL_EXECUTABLE_PATH_DEFAULT;
  public static String H3D_EXECUTABLE_PATH_DEFAULT;
  public static String HEILAN_EXECUTABLE_PATH_DEFAULT;
  public static String INSTANTREALITY_EXECUTABLE_PATH_DEFAULT;
  public static String OCTAGA_EXECUTABLE_PATH_DEFAULT;
  public static String SWIRLX3DPLAYER_EXECUTABLE_PATH_DEFAULT;
  public static String VIEW3DSCENE_EXECUTABLE_PATH_DEFAULT;
  public static String VIVATYPLAYER_EXECUTABLE_PATH_DEFAULT;
  public static String XJ3D_EXECUTABLE_PATH_DEFAULT;
  public static String OTHER_X3D_PLAYER_EXECUTABLE_NAME_DEFAULT;
  public static String OTHER_X3D_PLAYER_EXECUTABLE_PATH_DEFAULT;
  public static String OTHER_X3D_PLAYER_EXECUTABLE_SWITCH_DEFAULT;
  public static String OTHER_X3D_EDITOR_EXECUTABLE_NAME_DEFAULT;
  public static String OTHER_X3D_EDITOR_EXECUTABLE_PATH_DEFAULT;
  
  public static String CHROME_EXECUTABLE_PATH_DEFAULT;
  public static String FIREFOX_EXECUTABLE_PATH_DEFAULT;
  public static String INTERNETEXPLORER_EXECUTABLE_PATH_DEFAULT;
  public static String OPERA_EXECUTABLE_PATH_DEFAULT;
  public static String SAFARI_EXECUTABLE_PATH_DEFAULT;
  
  public static String           AMAYA_EDITOR_PATH_DEFAULT;
  public static String        AUDACITY_EDITOR_PATH_DEFAULT;
  public static String       MUSESCORE_EDITOR_PATH_DEFAULT;
  public static String            GIMP_EDITOR_PATH_DEFAULT;
  public static String            FIJI_EDITOR_PATH_DEFAULT;
  public static String          IMAGEJ_EDITOR_PATH_DEFAULT;
  public static String     IMAGEMAGICK_EDITOR_PATH_DEFAULT;
  public static String             VLC_PLAYER_PATH_DEFAULT;
  public static String     BLENDER_X3D_EDITOR_PATH_DEFAULT;
  public static String BSCONTENTSTUDIO_X3D_EDITOR_PATH_DEFAULT;
  public static String        CURA_X3D_EDITOR_PATH_DEFAULT;
  public static String     MESHLAB_X3D_EDITOR_PATH_DEFAULT;
  public static String    PARAVIEW_X3D_EDITOR_PATH_DEFAULT;
  public static String POLYTRANSNUGRAF_EDITOR_PATH_DEFAULT;
  public static String  SEAMLESS3D_X3D_EDITOR_PATH_DEFAULT;
  public static String     ITKSNAP_X3D_EDITOR_PATH_DEFAULT;
  public static String       SEG3D_X3D_EDITOR_PATH_DEFAULT;
  public static String    SLICER3D_X3D_EDITOR_PATH_DEFAULT;
  public static String    SVG_EDIT_X3D_EDITOR_PATH_DEFAULT;
  public static String   WHITEDUNE_X3D_EDITOR_PATH_DEFAULT;
  public static String     WINGS3D_X3D_EDITOR_PATH_DEFAULT;
  public static String   ULTRAEDIT_X3D_EDITOR_PATH_DEFAULT;
  public static String     OTHER_AUDIO_EDITOR_NAME_DEFAULT;
  public static String     OTHER_HTML5_EDITOR_NAME_DEFAULT;
  public static String     OTHER_IMAGE_EDITOR_NAME_DEFAULT;
  public static String     OTHER_VIDEO_EDITOR_NAME_DEFAULT;
  public static String    OTHER_VOLUME_EDITOR_NAME_DEFAULT;
  public static String       OTHER_X3D_EDITOR_NAME_DEFAULT;
  public static String     OTHER_AUDIO_EDITOR_PATH_DEFAULT;
  public static String     OTHER_HTML5_EDITOR_PATH_DEFAULT;
  public static String     OTHER_IMAGE_EDITOR_PATH_DEFAULT;
  public static String     OTHER_VIDEO_EDITOR_PATH_DEFAULT;
  public static String    OTHER_VOLUME_EDITOR_PATH_DEFAULT;
  public static String       OTHER_X3D_EDITOR_PATH_DEFAULT;
  public static String   OTHER_AUDIO_EDITOR_SWITCH_DEFAULT;
  public static String   OTHER_HTML5_EDITOR_SWITCH_DEFAULT;
  public static String   OTHER_IMAGE_EDITOR_SWITCH_DEFAULT;
  public static String   OTHER_VIDEO_EDITOR_SWITCH_DEFAULT;
  public static String  OTHER_VOLUME_EDITOR_SWITCH_DEFAULT;
  public static String     OTHER_X3D_EDITOR_SWITCH_DEFAULT;
  
  // note that installation of 32-bit software on 64-bit machines goes to directory C:\\Program Files (x86)\\
  // platform defaults:
                                                                   // C:\\Program Files\\Bitmanagement Software\\BS Contact\\BSContact.exe
  private static final String winxpContactPathDefault              = "C:\\Users\\"+USER_NAME_TOKEN+"\\AppData\\Local\\Bitmanagement Software\\BS Contact\\";
  private static final String winxpContactGeoPathDefault           = "C:\\Users\\"+USER_NAME_TOKEN+"\\AppData\\Local\\Bitmanagement Software\\BS Contact\\";
  private static final String winxpFreeWrlPathDefault              = "C:\\Program Files\\freeWRL\\freeWRL.3\\freeWRL.exe";
//private static final String winxpFreeWrlPathDefault              = "C:\\Program Files\\freeWRL\\freeWRL.3\\launchdir\\freeWRL_Launcher.exe"; // alternative
  private static final String winxpH3dPathDefault                  = "C:\\Program Files\\SenseGraphics\\H3DViewer\\bin32\\H3DViewer.exe"; // TODO 32
  private static final String winxpHeilanPathDefault               = "C:\\Program Files\\HeilanBrowser-0.15\\HeilanBrowser.exe";
  private static final String winxpInstantRealityPathDefault       = "C:\\Program Files\\Instant Reality\\bin\\InstantPlayer.exe";
  private static final String winxpPolyTransNuGrafPathDefault      = "C:\\Program Files\\NuGraf64\\NuGraf64.exe";
  private static final String winxpOctagaPathDefault               = "C:\\Program Files\\Octaga Visual Solutions\\Octaga Player 5.0 (64 bit)\\OctagaPlayer.exe";
  private static final String winxpSwirlX3DPlayerPathDefault       = "C:\\Program Files\\Pinecoast\\SwirlViewer\\SwView.exe";
  private static final String winxpView3dScenePathDefault          = "C:\\Program Files\\view3dscene\\view3dscene.exe";
  private static final String winxpVivatyPlayerPathDefault         = "C:\\Program Files\\Vivaty\\VivatyPlayer\\VivatyPlayer.exe";
  private static final String winxpXj3DPathDefault                 = "C:\\Program Files\\Xj3D\\browser.bat";
  private static final String winxpOtherX3dPlayerPathDefault       = ""; // user configured
  private static final String winxpOtherX3dPlayerNameDefault       = "(Add another player using X3D-Edit Preferences)"; // user configured
  private static final String winxpOtherX3dEditorPathDefault       = ""; // user configured
  private static final String winxpOtherX3dEditorNameDefault       = "(Add another authoring tool using X3D-Edit Preferences)"; // user configured
  private static final String winxpAmayaPathDefault                = "C:\\Program Files\\Amaya\\WindowsWX\\bin\\amaya.exe";
  private static final String winxpChromePathDefault               = "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";
  private static final String winxpFirefoxPathDefault              = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
  private static final String winxpInternetExplorerPathDefault     = "C:\\Program Files\\Internet Explorer\\iexplore.exe";
  private static final String winxpOperaPathDefault                = "C:\\Program Files\\Opera\\opera.exe";
  private static final String winxpSafariPathDefault               = "C:\\Program Files\\Safari\\Safari.exe";
 
                                                                   // C:\\Program Files (x86)\\Bitmanagement Software\\BS Contact\\BSContact.exe
  private static final String windows64ContactPathDefault          = "C:\\Users\\"+USER_NAME_TOKEN+"\\AppData\\Local\\Bitmanagement Software\\BS Contact\\BSContact.exe";
  private static final String windows64ContactGeoPathDefault       = "C:\\Users\\"+USER_NAME_TOKEN+"\\AppData\\Local\\Bitmanagement Software\\BS Contact\\BSContact.exe";
  private static final String windows64FreeWrlPathDefault          = "C:\\Program Files (x86)\\freeWRL\\freeWRL.4\\freeWRL.exe";
//private static final String windows64FreeWrlPathDefault          = "C:\\Program Files (x86)\\freeWRL\\freeWRL.4\\launchdir\\freeWRL_launcher.exe"; // alternative
  private static final String windows64H3dPathDefault              = "C:\\Program Files\\SenseGraphics\\H3DViewer\\bin64\\H3DViewer.exe";
  private static final String windows64HeilanPathDefault           = "C:\\Program Files (x86)\\HeilanBrowser-0.15\\HeilanBrowser.exe";
  private static final String windows64InstantRealityPathDefault   = "C:\\Program Files\\Instant Reality\\bin\\InstantPlayer.exe";
  private static final String windows64PolyTransNuGrafPathDefault  = "C:\\Program Files\\NuGraf64\\NuGraf64.exe";
  private static final String windows64OctagaPathDefault           = "C:\\Program Files\\Octaga Visual Solutions\\Octaga Player 5.0 (64 bit)\\OctagaPlayer.exe";
  private static final String windows64SwirlX3DPlayerPathDefault   = "C:\\Program Files (x86)\\Pinecoast\\SwirlViewer\\SwView.exe";
  private static final String windows64View3dScenePathDefault      = "C:\\Program Files\\view3dscene\\view3dscene.exe";
  private static final String windows64VivatyPlayerPathDefault     = "C:\\Program Files (x86)\\Vivaty\\VivatyPlayer\\VivatyPlayer.exe";
  private static final String windows64Xj3DPathDefault             = "C:\\Program Files (x86)\\Xj3D\\browser.bat";
  private static final String windows64OtherX3dPlayerPathDefault   = ""; // user configured
  private static final String windows64OtherX3dPlayerNameDefault   = winxpOtherX3dPlayerNameDefault; // user configured
  private static final String windows64OtherX3dEditorPathDefault   = ""; // user configured
  private static final String windows64OtherX3dEditorNameDefault   = winxpOtherX3dEditorNameDefault; // user configured
  private static final String windows64AmayaPathDefault            = "C:\\Program Files (x86)\\Amaya\\WindowsWX\\bin\\amaya.exe";
  private static final String windows64ChromePathDefault           = "C:\\Program Files\\Google (x86)\\Chrome\\Application\\chrome.exe";
  private static final String windows64FirefoxPathDefault          = "C:\\Program Files\\Mozilla Firefox (x86)\\firefox.exe";
  private static final String windows64InternetExplorerPathDefault = "C:\\Program Files\\Internet Explorer (x86)\\iexplore.exe";
  private static final String windows64OperaPathDefault            = "C:\\Program Files\\Opera (x86)\\opera.exe";
  private static final String windows64SafariPathDefault           = "C:\\Program Files\\Safari (x86)\\Safari.exe";

  private static final String macosxContactPathDefault             = "/Applications/BS Contact.app";      // TODO verify BitManagement handled install path problem
  private static final String macosxContactGeoPathDefault          = "/Applications/BS Contact Geo.app";  // TODO verify BitManagement handled install path problem
  private static final String macosxFreeWrlPathDefault             = "/Applications/FreeWRL/FreeWrl.app";
  private static final String macosxH3dPathDefault                 = "/Applications/H3DViewer.app";
  private static final String macosxHeilanPathDefault              = "/Applications/HeilanBrowser.app";
  private static final String macosxInstantRealityPathDefault      = "/Applications/Instant Player.app";
  private static final String macosxPolyTransNuGrafPathDefault     = "/Applications/NuGraf.app";
  private static final String macosxOctagaPathDefault              = "/Applications/Octaga Player.app";
  private static final String macosxSwirlX3DPlayerPathDefault      = ""; // No Mac version as of 17 July 2008
  private static final String macosxView3dScenePathDefault         = "";
  private static final String macosxVivatyPlayerPathDefault        = ""; // No Mac version as of 17 July 2008
  private static final String macosxXj3DPathDefault                = "/Applications/Xj3D/Xj3DBrowser.app";
  private static final String macosxOtherX3dPlayerPathDefault      = ""; // user configured
  private static final String macosxOtherX3dPlayerNameDefault      = winxpOtherX3dPlayerNameDefault; // user configured
  private static final String macosxOtherX3dEditorPathDefault      = ""; // user configured
  private static final String macosxOtherX3dEditorNameDefault      = winxpOtherX3dEditorNameDefault; // user configured
  private static final String macosxAmayaPathDefault               = "";
  private static final String macosxChromePathDefault              = "";
  private static final String macosxFirefoxPathDefault             = "";
  private static final String macosxInternetExplorerPathDefault    = "";
  private static final String macosxOperaPathDefault               = "";
  private static final String macosxSafariPathDefault              = "";

  private static final String otherContactPathDefault              = "ContactPlayer";
  private static final String otherContactGeoPathDefault           = "ContactGeoPlayer";
  private static final String otherFreeWrlPathDefault              = "FreeWrlPlayer"; // linux
  private static final String otherH3dPathDefault                  = "H3DViewer";
  private static final String otherHeilanPathDefault               = "HeilanBrowser";
  private static final String otherInstantRealityPathDefault       = "InstantPlayer";
  private static final String otherPolyTransNuGrafPathDefault      = "NuGraf";
  private static final String otherOctagaPathDefault               = "OctagaPlayer";
  private static final String otherSwirlX3DPlayerPathDefault       = "SwirlX3DPlayer";
  private static final String otherView3dScenePathDefault          = "View3dScene";
  private static final String otherVivatyPlayerPathDefault         = "VivatyPlayer";
  private static final String otherXj3DPathDefault                 = "Xj3DPlayer";
  private static final String otherX3dPlayerNameDefault            = winxpOtherX3dPlayerNameDefault; // user configured
  private static final String otherX3dPlayerPathDefault            = ""; // user configured
  private static final String otherX3dPlayerSwitchDefault          = ""; // user configured
  private static final String otherX3dEditorNameDefault            = winxpOtherX3dEditorNameDefault; // user configured
  private static final String otherX3dEditorPathDefault            = ""; // user configured
  private static final String otherX3dEditorSwitchDefault          = ""; // user configured
  private static final String otherAmayaPathDefault                = "Other X3D player";
  private static final String otherChromePathDefault               = "";
  private static final String otherFirefoxPathDefault              = "";
  private static final String otherInternetExplorerPathDefault     = "";
  private static final String otherOperaPathDefault                = "";
  private static final String otherSafariPathDefault               = "";

  private static final String downloadSiteContact                  = "https://www.bitmanagement.com";
  private static final String downloadSiteContactGeo               = "https://www.bitmanagement.com";
  private static final String downloadSiteFreeWrl                  = "https://sourceforge.net/projects/freewrl"; 
                                                         // versions: https://sourceforge.net/projects/freewrl/files/
                                                         // formerly "http://www.crc.ca/FreeWRL"; 
                                                         //     also "https://sourceforge.net/projects/freewrl/files/freewrl-win32/3.0/"
  private static final String downloadSiteH3d                      = "https://www.h3dapi.org";
  private static final String downloadSiteHeilan                   = "https://www.niallmoody.com/heilan";
  private static final String downloadSiteInstantReality           = "https://instantreality.org";
  private static final String downloadSitePolyTransNuGraf          = "https://www.okino.com";
  private static final String downloadSiteOctaga                   = "https://www.octagavs.com";
  private static final String downloadSiteSwirlX3DPlayer           = "http://www.pinecoast.com"; // defunct
  private static final String downloadSiteView3dScene              = "https://castle-engine.io/view3dscene.php"; // "https://castle-engine.sourceforge.net";
  private static final String downloadSiteVivatyPlayer             = "https://www.web3d.org/projects/vivaty-studio"; // http://www.vivaty.com";
  private static final String downloadSiteXj3D                     = "https://savage.nps.edu/Savage/developers.html#Xj3D"; // "https://sourceforge.net/projects/xj3d"; // "http://www.Xj3D.org";
  private static final String downloadSiteOtherX3dPlayer           = "https://www.web3d.org/x3d/content/examples/X3dResources.html#Applications";
  private static final String downloadSiteAmaya                    = "https://www.w3.org/Amaya";
  private static final String downloadSiteChrome                   = "https://www.google.com/chrome";
  private static final String downloadSiteFirefox                  = "https://www.mozilla.org/firefox";
  private static final String downloadSiteMicrosoftEdge            = "https://www.microsoft.com/edge"; // https://windows.microsoft.com/en-US/internet-explorer/download-ie";
  private static final String downloadSiteOpera                    = "https://www.opera.com";
  private static final String downloadSiteSafari                   = "https://www.apple.com/safari";
  private static final String downloadSiteOtherAudioEditor         = "https://www.web3d.org/x3d/content/examples/X3dSceneAuthoringHints.html#Audio";
  private static final String downloadSiteOtherHtml5Editor         = "https://www.web3d.org/x3d/content/examples/X3dSceneAuthoringHints.html#HTML";
  private static final String downloadSiteOtherImageEditor         = "https://www.web3d.org/x3d/content/examples/X3dSceneAuthoringHints.html#Images";
  private static final String downloadSiteOtherVideoEditor         = "https://www.web3d.org/x3d/content/examples/X3dSceneAuthoringHints.html#Images";
  private static final String downloadSiteOtherVolumeEditor        = "https://www.web3d.org/x3d/content/examples/X3dSceneAuthoringHints.html#Volumes";
  private static final String downloadSiteOtherX3dEditor           = "https://www.web3d.org/x3d/content/examples/X3dResources.html#AuthoringSoftware";
  
  private static final String winxpAudacityEditorPathDefault             = "C:\\Program Files\\Audacity\\audacity.exe";
  private static final String winxpMuseScoreEditorPathDefault            = "C:\\Program Files\\MuseScore 3\\bin\\MuseScore3.exe";
  private static final String winxpGimpEditorPathDefault                 = "C:\\Program Files\\GIMP 2\\bin\\gimp-2.10.exe";
  private static final String winxpFijiEditorPathDefault                 = "C:\\Program Files\\Fiji.app\\ImageJ-win32.exe";
  private static final String winxpImageJEditorPathDefault               = "C:\\Program Files\\ImageJ\\ImageJ.exe";
  private static final String winxpImageMagickEditorPathDefault          = "C:\\Program Files\\ImageMagick-7.0.11-Q16-HDRI\\imdisplay.exe";
  private static final String winxpVlcPlayerPathDefault                  = "C:\\Program Files\\VideoLAN\\VLC\\vlc.exe";
  private static final String winxpBlenderX3dEditorPathDefault           = "C:\\Program Files\\Blender Foundation\\Blender 2.92\\blender.exe";
  private static final String winxpBsContentStudioX3dEditorPathDefault   = "C:\\Program Files\\\\Bitmanagement Software\\BS Content Studio\\x64\\BSComposer.exe";
  private static final String winxpCuraX3dEditorPathDefault              = "C:\\Program Files\\Ultimaker Cura 4.9.1\\Cura.exe";
  private static final String winxpMeshLabX3dEditorPathDefault           = "C:\\Program Files\\VCG\\MeshLab\\meshlab.exe";
  private static final String winxpParaviewX3dEditorPathDefault          = "C:\\Program Files\\ParaView 5.9.1-Windows-Python3.8-msvc2017-64bit\\bin\\paraview.exe";
  private static final String winxpSeamlessX3dEditorPathDefault          = "C:\\Program Files\\Seamless3d (x86)\\seamless3d.exe";
  private static final String winxpItksnapEditorPathDefault              = "C:\\Program Files\\ITK-SNAP 3.8\\bin\\ITK-SNAP.exe";
  private static final String winxpSeg3dEditorPathDefault                = "C:\\Program Files\\Seg3D2_2.5\\bin\\Seg3D2.exe";
  private static final String winxpSlicer3dEditorPathDefault             = "C:\\Program Files\\Slicer 4.3.1\\Slicer.exe";
  private static final String winxpSvgeditEditorPathDefault              = x3dEditPath + "/externals/svg-edit-2.7/svg-editor.html"; // built in
  private static final String winxpWhiteDuneX3dEditorPathDefault         = "C:\\installs\\WhiteDune\\wdune-0.99pl164.exe";
  private static final String winxpWingsX3dEditorPathDefault             = "C:\\Program Files\\wings3d_2.2.6.1\\Wings3D.exe";
  private static final String winxpUltraEditX3dEditorPathDefault         = "C:\\Program Files\\IDM Computer Solutions\\UltraEdit\\uedit32.exe";

  private static final String windows64AudacityEditorPathDefault         = "C:\\Program Files (x86)\\Audacity\\audacity.exe";
  private static final String windows64MuseScoreEditorPathDefault        = "C:\\Program Files\\MuseScore 3\\bin\\MuseScore3.exe";
  private static final String windows64GimpEditorPathDefault             = "C:\\Program Files\\GIMP 2\\bin\\gimp-2.10.exe";
  private static final String windows64FijiEditorPathDefault             = "C:\\Program Files\\Fiji.app\\ImageJ-win64.exe";
  private static final String windows64ImageJEditorPathDefault           = "C:\\Program Files\\ImageJ\\ImageJ.exe";
  private static final String windows64ImageMagickEditorPathDefault      = "C:\\Program Files\\ImageMagick-7.0.11-Q16-HDRI\\imdisplay.exe";
  private static final String windows64VlcPlayerPathDefault              = "C:\\Program Files\\VideoLAN\\VLC\\vlc.exe";
  private static final String windows64BlenderX3dEditorPathDefault       = "C:\\Program Files\\Blender Foundation\\Blender 2.92\\blender.exe";
  private static final String windows64BsContentStudioX3dEditorPathDefault="C:\\Program Files (x86)\\Bitmanagement Software\\BS Content Studio\\x64\\BSComposer.exe";
  private static final String windows64CuraX3dEditorPathDefault          = "C:\\Program Files\\Ultimaker Cura 4.9.1\\Cura.exe";
  private static final String windows64MeshLabX3dEditorPathDefault       = "C:\\Program Files\\VCG\\MeshLab\\meshlab.exe";
  private static final String windows64ParaviewX3dEditorPathDefault      = "C:\\Program Files\\ParaView 5.9.1-Windows-Python3.8-msvc2017-64bit\\bin\\paraview.exe";
  private static final String windows64SeamlessX3dEditorPathDefault      = "C:\\Program Files (x86)\\Seamless3d\\seamless3d.exe";
  private static final String windows64ItksnapEditorPathDefault          = "C:\\Program Files\\ITK-SNAP 3.8\\bin\\ITK-SNAP.exe";
  private static final String windows64Seg3dEditorPathDefault            = "C:\\Program Files\\Seg3D2_2.5\\bin\\Seg3D2.exe";
  private static final String windows64Slicer3dEditorPathDefault         = "C:\\Program Files\\Slicer 4.3.1\\Slicer.exe";
  private static final String windows64SvgeditEditorPathDefault          = x3dEditPath + "/externals/svg-edit-2.7/svg-editor.html"; // built in
  private static final String windows64WhiteDuneX3dEditorPathDefault     = "C:\\installs\\WhiteDune\\wdune-0.99pl164.exe";
  private static final String windows64WingsX3dEditorPathDefault         = "C:\\Program Files\\wings3d_2.2.6.1\\Wings3D.exe";
  private static final String windows64UltraEditX3dEditorPathDefault     = "C:\\Program Files\\IDM Computer Solutions\\UltraEdit\\uedit64.exe";
  
  private static final String macosxAudacityEditorPathDefault            = "audacity";       // TODO insert correct value
  private static final String macosxMuseScoreEditorPathDefault           = "musescore";      // TODO insert correct value
  private static final String macosxGimpImageEditorPathDefault           = "gimp-2.10";      // TODO insert correct value
  private static final String macosxFijiEditorPathDefault                = "Fiji";           // TODO insert correct value
  private static final String macosxImageJEditorPathDefault              = "ij.jar";         // TODO insert correct value
  private static final String macosxImageMagickEditorPathDefault         = "imageconverter"; // TODO insert correct value
  private static final String macosxVlcPlayerPathDefault                 = "vlc";            // TODO insert correct value
  private static final String macosxBlenderX3dEditorPathDefault          = "blender";        // TODO insert correct value
  private static final String macosxBsContentStudioX3dEditorPathDefault  = "bscomposer";     // TODO insert correct value
  private static final String macosxCuraX3dEditorPathDefault             = "Cura";           // TODO insert correct value
  private static final String macosxMeshLabX3dEditorPathDefault          = "MeshLab";        // TODO insert correct value
  private static final String macosxParaviewX3dEditorPathDefault         = "Paraview";       // TODO insert correct value
  private static final String macosxSeamlessX3dEditorPathDefault         = "Seamless3d";     // TODO insert correct value
  private static final String macosxItksnapEditorPathDefault             = "Itksnap";        // TODO insert correct value
  private static final String macosxSeg3dEditorPathDefault               = "Seg3d";          // TODO insert correct value
  private static final String macosxSlicer3dEditorPathDefault            = "Slicer3d";       // TODO insert correct value
  private static final String macosxSvgeditEditorPathDefault             = x3dEditPath + "/externals/svg-edit-2.7/svg-editor.html"; // built in
  private static final String macosxWhiteDuneX3dEditorPathDefault        = "WhiteDune";      // TODO insert correct value
  private static final String macosxWingsX3dEditorPathDefault            = "Wings3d";        // TODO insert correct value
  private static final String macosxUltraEditX3dEditorPathDefault        = "UltraEdit";      // TODO insert correct value
  
  private static final String otherAudacityEditorPathDefault             = "audacity";       // TODO insert correct value
  private static final String otherMuseScoreEditorPathDefault            = "musescore";      // TODO insert correct value
  private static final String otherGimpEditorPathDefault                 = "gimp-2.10";      // TODO insert correct value
  private static final String otherFijiEditorPathDefault                 = "ImageJ-linux64"; // or ImageJ-linux32
  private static final String otherImageJEditorPathDefault               = "ij.jar";         // TODO insert correct value
  private static final String otherImageMagickEditorPathDefault          = "imageconverter"; // TODO insert correct value
  private static final String otherVlcPlayerPathDefault                  = "vlc";            // TODO insert correct value
  private static final String otherBlenderX3dEditorPathDefault           = "blender";        // TODO insert correct value
  private static final String otherBsContentStudioX3dEditorPathDefault   = "bscomposer";     // TODO insert correct value
  private static final String otherCuraX3dEditorPathDefault              = "Cura";           // TODO insert correct value
  private static final String otherMeshLabX3dEditorPathDefault           = "MeshLab";        // TODO insert correct value
  private static final String otherParaviewX3dEditorPathDefault          = "Paraview";       // TODO insert correct value
  private static final String otherSeamlessX3dEditorPathDefault          = "Seamless3d";     // TODO insert correct value
  private static final String otherItksnapEditorPathDefault              = "Itksnap";        // TODO insert correct value
  private static final String otherSeg3dEditorPathDefault                = "Seg3d";          // TODO insert correct value
  private static final String otherSlicer3dEditorPathDefault             = "Slicer3d";       // TODO insert correct value
  private static final String otherSvgeditEditorPathDefault              = x3dEditPath + "/externals/svg-edit-2.7/svg-editor.html"; // built in
  private static final String otherWhiteDuneX3dEditorPathDefault         = "WhiteDune";      // TODO insert correct value
  private static final String otherWingsX3dEditorPathDefault             = "Wings3d";        // TODO insert correct value
  private static final String otherUltraEditX3dEditorPathDefault         = "UltraEdit";      // TODO insert correct value
  
  private static final String  otherAudioEditorNameDefault                = "Other tool";
  private static final String  otherHtml5EditorNameDefault                = "Other tool";
  private static final String  otherImageEditorNameDefault                = "Other tool";
  private static final String  otherVideoEditorNameDefault                = "Other tool";
  private static final String otherVolumeEditorNameDefault                = "Other tool";
  private static final String  otherAudioEditorPathDefault                = ""; // User defined
  private static final String  otherHtml5EditorPathDefault                = ""; // User defined
  private static final String  otherImageEditorPathDefault                = ""; // User defined
  private static final String  otherVideoEditorPathDefault                = ""; // User defined
  private static final String otherVolumeEditorPathDefault                = ""; // User defined
  private static final String  otherAudioEditorSwitchDefault              = ""; // User defined command-line launch switch
  private static final String  otherHtml5EditorSwitchDefault              = ""; // User defined command-line launch switch
  private static final String  otherImageEditorSwitchDefault              = ""; // User defined command-line launch switch
  private static final String  otherVideoEditorSwitchDefault              = ""; // User defined command-line launch switch
  private static final String otherVolumeEditorSwitchDefault              = ""; // User defined command-line launch switch
  
  private static final String downloadSiteAudacity                       = "https://www.audacityteam.org/download"; // https://sourceforge.net/projects/audacity";
  private static final String downloadSiteMuseScore                      = "https://musescore.org";
  private static final String downloadSiteGimp                           = "https://www.gimp.org";
  private static final String downloadSiteFiji                           = "https://imagej.github.io/software/fiji/downloads"; // "https://imagej.net/Fiji/Downloads";
  private static final String downloadSiteImageJ                         = "https://imagej.nih.gov/ij/download.html"; // https://rsbweb.nih.gov/ij/download.html";
  private static final String downloadSiteImageMagick                    = "https://imagemagick.org/script/download.php";
  private static final String downloadSiteVlc                            = "https://www.videolan.org";
  private static final String downloadSiteBlender                        = "https://www.blender.org";
  private static final String downloadSiteBsContentStudio                = "https://www.bitmanagement.com/download/studio";
  private static final String downloadSiteCura                           = "https://ultimaker.com/software/ultimaker-cura";
  private static final String downloadSiteMeshLab                        = "https://www.meshlab.net";
  private static final String downloadSiteParaview                       = "https://www.kitware.com/platforms/#paraview";
  private static final String downloadSiteSeamless3d                     = "https://www.seamless3d.com";
  private static final String downloadSiteItksnap                        = "http://www.itksnap.org"; // https://www.itksnap.org/pmwiki/pmwiki.php?n=Main.Downloads";
  private static final String downloadSiteSeg3d                          = "https://www.sci.utah.edu/cibc-software/seg3d.html";
  private static final String downloadSiteSlicer3d                       = "https://www.slicer.org";
  private static final String downloadSiteSvgedit                        = "https://github.com/SVG-Edit"; // https://code.google.com/p/svg-edit";
  private static final String downloadSiteWhiteDune                      = "https://wdune.ourproject.org";
  private static final String downloadSiteWings3d                        = "http://www.wings3d.com"; // https://www.wings3d.com/?page_id=84";
  private static final String downloadSiteUltraEdit                      = "https://www.UltraEdit.com";
  
  // no command line options found to hand off the import of an X3D file, TODO provide local help
  // http://wiki.blender.org/index.php/Doc:2.4/Reference/Command_Line

  protected static final String helpSiteAmaya                            = "https://www.w3.org/Amaya/User/Overview.html";
  protected static final String helpSiteAudacity                         = "https://www.audacityteam.org/help/"; // https://audacity.sourceforge.net/help";
  protected static final String helpSiteMuseScore                        = "https://musescore.org/en/handbook";
  protected static final String helpSiteGimp                             = "https://www.gimp.org/docs";
  protected static final String helpSiteFiji                             = "https://fiji.sc/Documentation";
  protected static final String helpSiteImageJ                           = "https://rsbweb.nih.gov/ij/docs";
  protected static final String helpSiteImageMagick                      = "https://www.imagemagick.org";
  protected static final String helpSiteVlc                              = "https://www.videolan.org/support/#documentation";
  protected static final String helpSiteBlender                          = "https://www.blender.org/get-involved/documentation/"; // https://www.blender.org/education-help";
  protected static final String helpSiteBsContentStudio                  = "https://www.bitmanagement.com/download/studio";
  protected static final String helpSiteCura                             = "https://ultimaker.com/software/ultimaker-cura"; 
  protected static final String helpSiteMeshLab                          = "https://www.meshlab.net"; // https://sourceforge.net/apps/mediawiki/meshlab";
  protected static final String helpSiteParaview                         = "https://www.paraview.org";
  protected static final String helpSitePolyTransNuGraf                  = "https://www.okino.com/nrs/nrs.htm";
  protected static final String helpSiteSeamless3d                       = "https://www.seamless3d.com/faq.html";
  protected static final String helpSiteItksnap                          = "http://www.itksnap.org/pmwiki/pmwiki.php?n=Documentation.SNAP3"; // https://www.itksnap.org/pmwiki/pmwiki.php";
  protected static final String helpSiteSeg3d                            = "https://www.sci.utah.edu/cibc-software/seg3d.html";
  protected static final String helpSiteSlicer3d                         = "https://www.slicer.org/slicerWiki/index.php/Documentation/Release";
  protected static final String helpSiteSvgedit                          = "https://en.wikipedia.org/wiki/SVG-edit"; // https://code.google.com/p/svg-edit";
  protected static final String helpSiteWhiteDune                        = "https://wdune.ourproject.org/docs";
  protected static final String helpSiteWings3d                          = "http://www.wings3d.com/?page_id=87";
  protected static final String helpSiteUltraEdit                        = "https://www.ultraedit.com/wiki/Main_Page"; // https://www.UltraEdit.com/help/category/ultraedit-uestudio";

  public static String getDownloadSiteContact()        {return downloadSiteContact;}
  public static String getDownloadSiteContactGeo()     {return downloadSiteContactGeo;}
  public static String getDownloadSiteFreeWrl()        {return downloadSiteFreeWrl;}
  public static String getDownloadSiteH3d()            {return downloadSiteH3d;}
  public static String getDownloadSiteHeilan()         {return downloadSiteHeilan;}
  public static String getDownloadSiteInstantReality() {return downloadSiteInstantReality;}
  public static String getDownloadSitePolyTransNuGraf(){return downloadSitePolyTransNuGraf;}
  public static String getDownloadSiteOctaga()         {return downloadSiteOctaga;}
  public static String getDownloadSiteSwirlX3D()       {return downloadSiteSwirlX3DPlayer;}
  public static String getDownloadSiteView3dScene()    {return downloadSiteView3dScene;}
  public static String getDownloadSiteVivatyPlayer()   {return downloadSiteVivatyPlayer;}
  public static String getDownloadSiteXj3D()           {return downloadSiteXj3D;}
  public static String getDownloadSiteOtherX3dPlayer() {return downloadSiteOtherX3dPlayer;}
  
  public static String getDownloadSiteAmaya()          {return downloadSiteAmaya;}
  public static String getDownloadSiteAudacity()       {return downloadSiteAudacity;}
  public static String getDownloadSiteMuseScore()      {return downloadSiteMuseScore;}
  public static String getDownloadSiteGimp()           {return downloadSiteGimp;}
  public static String getDownloadSiteFiji()           {return downloadSiteFiji;}
  public static String getDownloadSiteImageJ()         {return downloadSiteImageJ;}
  public static String getDownloadSiteImageMagick()    {return downloadSiteImageMagick;}
  public static String getDownloadSiteVlc()            {return downloadSiteVlc;}
  public static String getDownloadSiteBlender()        {return downloadSiteBlender;}
  public static String getDownloadSiteBsContentStudio(){return downloadSiteBsContentStudio;}
  public static String getDownloadSiteCura()           {return downloadSiteCura;}
  public static String getDownloadSiteMeshLab()        {return downloadSiteMeshLab;}
  public static String getDownloadSiteParaview()       {return downloadSiteParaview;}
  public static String getDownloadSiteSeamless3d()     {return downloadSiteSeamless3d;}
  public static String getDownloadSiteItksnap()        {return downloadSiteItksnap;}
  public static String getDownloadSiteSeg3d()          {return downloadSiteSeg3d;}
  public static String getDownloadSiteSlicer3d()       {return downloadSiteSlicer3d;}
  public static String getDownloadSiteSvgedit()        {return downloadSiteSvgedit;}
  public static String getDownloadSiteWhiteDune()      {return downloadSiteWhiteDune;}
  public static String getDownloadSiteWings3d()        {return downloadSiteWings3d;}
  public static String getDownloadSiteUltraEdit()      {return downloadSiteUltraEdit;}
  
  public static String getDownloadSiteOtherAudioEditor() {return downloadSiteOtherAudioEditor;}
  public static String getDownloadSiteOtherHtml5Editor() {return downloadSiteOtherHtml5Editor;}
  public static String getDownloadSiteOtherImageEditor() {return downloadSiteOtherImageEditor;}
  public static String getDownloadSiteOtherVideoEditor() {return downloadSiteOtherVideoEditor;}
  public static String getDownloadSiteOtherVolumeEditor(){return downloadSiteOtherVolumeEditor;}
  public static String getDownloadSiteOtherX3dEditor()   {return downloadSiteOtherX3dEditor;}
    
  static {
   String os_name = System.getProperty("os.name");
   String os_path = System.getProperty("java.library.path");
   
   if (os_name.equals("Mac OS X") || os_name.contains("Mac")) 
   {
     CONTACT_EXECUTABLE_PATH_DEFAULT         = toks(macosxContactPathDefault);
     CONTACT_GEO_EXECUTABLE_PATH_DEFAULT     = toks(macosxContactGeoPathDefault);
     FREEWRL_EXECUTABLE_PATH_DEFAULT         = toks(macosxFreeWrlPathDefault);
     H3D_EXECUTABLE_PATH_DEFAULT             = toks(macosxH3dPathDefault);
     HEILAN_EXECUTABLE_PATH_DEFAULT          = toks(macosxHeilanPathDefault);
     INSTANTREALITY_EXECUTABLE_PATH_DEFAULT  = toks(macosxInstantRealityPathDefault);
     OCTAGA_EXECUTABLE_PATH_DEFAULT          = toks(macosxOctagaPathDefault);
     SWIRLX3DPLAYER_EXECUTABLE_PATH_DEFAULT  = toks(macosxSwirlX3DPlayerPathDefault);
     VIEW3DSCENE_EXECUTABLE_PATH_DEFAULT     = toks(macosxView3dScenePathDefault);
     VIVATYPLAYER_EXECUTABLE_PATH_DEFAULT    = toks(macosxVivatyPlayerPathDefault);
     XJ3D_EXECUTABLE_PATH_DEFAULT            = toks(macosxXj3DPathDefault);
 OTHER_X3D_PLAYER_EXECUTABLE_PATH_DEFAULT    = toks(macosxOtherX3dPlayerPathDefault);
 OTHER_X3D_PLAYER_EXECUTABLE_NAME_DEFAULT    = toks(macosxOtherX3dPlayerNameDefault);
 OTHER_X3D_EDITOR_EXECUTABLE_PATH_DEFAULT    = toks(macosxOtherX3dEditorPathDefault);
 OTHER_X3D_EDITOR_EXECUTABLE_NAME_DEFAULT    = toks(macosxOtherX3dEditorNameDefault);
     AMAYA_EDITOR_PATH_DEFAULT               = toks(macosxAmayaPathDefault);
     CHROME_EXECUTABLE_PATH_DEFAULT          = toks(macosxChromePathDefault);
     FIREFOX_EXECUTABLE_PATH_DEFAULT         = toks(macosxFirefoxPathDefault);
     INTERNETEXPLORER_EXECUTABLE_PATH_DEFAULT= toks(macosxInternetExplorerPathDefault);
     OPERA_EXECUTABLE_PATH_DEFAULT           = toks(macosxOperaPathDefault);
     SAFARI_EXECUTABLE_PATH_DEFAULT          = toks(macosxSafariPathDefault);
     
         AUDACITY_EDITOR_PATH_DEFAULT        = toks(    macosxAudacityEditorPathDefault);
        MUSESCORE_EDITOR_PATH_DEFAULT        = toks(   macosxMuseScoreEditorPathDefault);
             GIMP_EDITOR_PATH_DEFAULT        = toks(   macosxGimpImageEditorPathDefault);
             FIJI_EDITOR_PATH_DEFAULT        = toks(        macosxFijiEditorPathDefault);
           IMAGEJ_EDITOR_PATH_DEFAULT        = toks(      macosxImageJEditorPathDefault);
      IMAGEMAGICK_EDITOR_PATH_DEFAULT        = toks( macosxImageMagickEditorPathDefault);
              VLC_PLAYER_PATH_DEFAULT        = toks(         macosxVlcPlayerPathDefault);
      BLENDER_X3D_EDITOR_PATH_DEFAULT        = toks(  macosxBlenderX3dEditorPathDefault);
      BLENDER_X3D_EDITOR_PATH_DEFAULT        = toks(macosxBsContentStudioX3dEditorPathDefault);
         CURA_X3D_EDITOR_PATH_DEFAULT        = toks(     macosxCuraX3dEditorPathDefault);
      MESHLAB_X3D_EDITOR_PATH_DEFAULT        = toks(  macosxMeshLabX3dEditorPathDefault);
     PARAVIEW_X3D_EDITOR_PATH_DEFAULT        = toks( macosxParaviewX3dEditorPathDefault);
  POLYTRANSNUGRAF_EDITOR_PATH_DEFAULT        = toks(   macosxPolyTransNuGrafPathDefault);
   SEAMLESS3D_X3D_EDITOR_PATH_DEFAULT        = toks( macosxSeamlessX3dEditorPathDefault);
      ITKSNAP_X3D_EDITOR_PATH_DEFAULT        = toks(     macosxItksnapEditorPathDefault);
        SEG3D_X3D_EDITOR_PATH_DEFAULT        = toks(       macosxSeg3dEditorPathDefault);
     SLICER3D_X3D_EDITOR_PATH_DEFAULT        = toks(    macosxSlicer3dEditorPathDefault);
     SVG_EDIT_X3D_EDITOR_PATH_DEFAULT        = toks (    macosxSvgeditEditorPathDefault);
    WHITEDUNE_X3D_EDITOR_PATH_DEFAULT        = toks(macosxWhiteDuneX3dEditorPathDefault);
      WINGS3D_X3D_EDITOR_PATH_DEFAULT        = toks(    macosxWingsX3dEditorPathDefault);
    ULTRAEDIT_X3D_EDITOR_PATH_DEFAULT        = toks(macosxUltraEditX3dEditorPathDefault);
   }
   // crude test for Program Files directory on 64-bit machines
   else if ((os_name.equals("Windows XP") || os_name.contains("Windows")) && os_path.contains("(x86)"))
   {
      CONTACT_EXECUTABLE_PATH_DEFAULT        = toks(windows64ContactPathDefault);
      CONTACT_GEO_EXECUTABLE_PATH_DEFAULT    = toks(windows64ContactGeoPathDefault);
      FREEWRL_EXECUTABLE_PATH_DEFAULT        = toks(windows64FreeWrlPathDefault);
      H3D_EXECUTABLE_PATH_DEFAULT            = toks(windows64H3dPathDefault);
      HEILAN_EXECUTABLE_PATH_DEFAULT         = toks(windows64HeilanPathDefault);
      INSTANTREALITY_EXECUTABLE_PATH_DEFAULT = toks(windows64InstantRealityPathDefault);
      OCTAGA_EXECUTABLE_PATH_DEFAULT         = toks(windows64OctagaPathDefault);
      SWIRLX3DPLAYER_EXECUTABLE_PATH_DEFAULT = toks(windows64SwirlX3DPlayerPathDefault);
      VIEW3DSCENE_EXECUTABLE_PATH_DEFAULT    = toks(windows64View3dScenePathDefault);
      VIVATYPLAYER_EXECUTABLE_PATH_DEFAULT   = toks(windows64VivatyPlayerPathDefault);
      XJ3D_EXECUTABLE_PATH_DEFAULT           = toks(windows64Xj3DPathDefault);
  OTHER_X3D_PLAYER_EXECUTABLE_PATH_DEFAULT   = toks(windows64OtherX3dPlayerPathDefault);
  OTHER_X3D_PLAYER_EXECUTABLE_NAME_DEFAULT   = toks(windows64OtherX3dPlayerNameDefault);
  OTHER_X3D_EDITOR_EXECUTABLE_PATH_DEFAULT   = toks(windows64OtherX3dEditorPathDefault);
  OTHER_X3D_EDITOR_EXECUTABLE_NAME_DEFAULT   = toks(windows64OtherX3dEditorNameDefault);
     AMAYA_EDITOR_PATH_DEFAULT               = toks(windows64AmayaPathDefault);
     CHROME_EXECUTABLE_PATH_DEFAULT          = toks(windows64ChromePathDefault);
     FIREFOX_EXECUTABLE_PATH_DEFAULT         = toks(windows64FirefoxPathDefault);
     INTERNETEXPLORER_EXECUTABLE_PATH_DEFAULT= toks(windows64InternetExplorerPathDefault);
     OPERA_EXECUTABLE_PATH_DEFAULT           = toks(windows64OperaPathDefault);
     SAFARI_EXECUTABLE_PATH_DEFAULT          = toks(windows64SafariPathDefault);
     
         AUDACITY_EDITOR_PATH_DEFAULT        = toks(    windows64AudacityEditorPathDefault);
         MUSESCORE_EDITOR_PATH_DEFAULT       = toks(   windows64MuseScoreEditorPathDefault);
             GIMP_EDITOR_PATH_DEFAULT        = toks(        windows64GimpEditorPathDefault);
             FIJI_EDITOR_PATH_DEFAULT        = toks(        windows64FijiEditorPathDefault);
           IMAGEJ_EDITOR_PATH_DEFAULT        = toks(      windows64ImageJEditorPathDefault);
      IMAGEMAGICK_EDITOR_PATH_DEFAULT        = toks( windows64ImageMagickEditorPathDefault);
              VLC_PLAYER_PATH_DEFAULT        = toks(         windows64VlcPlayerPathDefault);
      BLENDER_X3D_EDITOR_PATH_DEFAULT        = toks(  windows64BlenderX3dEditorPathDefault);
BSCONTENTSTUDIO_X3D_EDITOR_PATH_DEFAULT      = toks(windows64BsContentStudioX3dEditorPathDefault);
         CURA_X3D_EDITOR_PATH_DEFAULT        = toks(     windows64CuraX3dEditorPathDefault);
      MESHLAB_X3D_EDITOR_PATH_DEFAULT        = toks(  windows64MeshLabX3dEditorPathDefault);
     PARAVIEW_X3D_EDITOR_PATH_DEFAULT        = toks( windows64ParaviewX3dEditorPathDefault);
  POLYTRANSNUGRAF_EDITOR_PATH_DEFAULT        = toks(   windows64PolyTransNuGrafPathDefault);
   SEAMLESS3D_X3D_EDITOR_PATH_DEFAULT        = toks( windows64SeamlessX3dEditorPathDefault);
      ITKSNAP_X3D_EDITOR_PATH_DEFAULT        = toks(     windows64ItksnapEditorPathDefault);
        SEG3D_X3D_EDITOR_PATH_DEFAULT        = toks(       windows64Seg3dEditorPathDefault);
     SLICER3D_X3D_EDITOR_PATH_DEFAULT        = toks(    windows64Slicer3dEditorPathDefault);
     SVG_EDIT_X3D_EDITOR_PATH_DEFAULT        = toks(     windows64SvgeditEditorPathDefault);
    WHITEDUNE_X3D_EDITOR_PATH_DEFAULT        = toks(windows64WhiteDuneX3dEditorPathDefault);
      WINGS3D_X3D_EDITOR_PATH_DEFAULT        = toks(    windows64WingsX3dEditorPathDefault);
    ULTRAEDIT_X3D_EDITOR_PATH_DEFAULT        = toks(windows64UltraEditX3dEditorPathDefault);
    }
    else if (os_name.equals("Windows XP") || os_name.contains("Windows"))
    {
      CONTACT_EXECUTABLE_PATH_DEFAULT        = toks(winxpContactPathDefault);
      CONTACT_GEO_EXECUTABLE_PATH_DEFAULT    = toks(winxpContactGeoPathDefault);
      FREEWRL_EXECUTABLE_PATH_DEFAULT        = toks(winxpFreeWrlPathDefault);
      H3D_EXECUTABLE_PATH_DEFAULT            = toks(winxpH3dPathDefault);
      HEILAN_EXECUTABLE_PATH_DEFAULT         = toks(winxpHeilanPathDefault);
      INSTANTREALITY_EXECUTABLE_PATH_DEFAULT = toks(winxpInstantRealityPathDefault);
      OCTAGA_EXECUTABLE_PATH_DEFAULT         = toks(winxpOctagaPathDefault);
      SWIRLX3DPLAYER_EXECUTABLE_PATH_DEFAULT = toks(winxpSwirlX3DPlayerPathDefault);
      VIEW3DSCENE_EXECUTABLE_PATH_DEFAULT    = toks(winxpView3dScenePathDefault);
      VIVATYPLAYER_EXECUTABLE_PATH_DEFAULT   = toks(winxpVivatyPlayerPathDefault);
      XJ3D_EXECUTABLE_PATH_DEFAULT           = toks(winxpXj3DPathDefault);
  OTHER_X3D_PLAYER_EXECUTABLE_PATH_DEFAULT   = toks(winxpOtherX3dPlayerPathDefault);
  OTHER_X3D_PLAYER_EXECUTABLE_NAME_DEFAULT   = toks(winxpOtherX3dPlayerNameDefault);
  OTHER_X3D_EDITOR_EXECUTABLE_PATH_DEFAULT   = toks(winxpOtherX3dEditorPathDefault);
  OTHER_X3D_EDITOR_EXECUTABLE_NAME_DEFAULT   = toks(winxpOtherX3dEditorNameDefault);
     AMAYA_EDITOR_PATH_DEFAULT               = toks(winxpAmayaPathDefault);
     CHROME_EXECUTABLE_PATH_DEFAULT          = toks(winxpChromePathDefault);
     FIREFOX_EXECUTABLE_PATH_DEFAULT         = toks(winxpFirefoxPathDefault);
     INTERNETEXPLORER_EXECUTABLE_PATH_DEFAULT= toks(winxpInternetExplorerPathDefault);
     OPERA_EXECUTABLE_PATH_DEFAULT           = toks(winxpOperaPathDefault);
     SAFARI_EXECUTABLE_PATH_DEFAULT          = toks(winxpSafariPathDefault);
     
         AUDACITY_EDITOR_PATH_DEFAULT        = toks(    winxpAudacityEditorPathDefault);
         MUSESCORE_EDITOR_PATH_DEFAULT       = toks(   winxpMuseScoreEditorPathDefault);
             GIMP_EDITOR_PATH_DEFAULT        = toks(        winxpGimpEditorPathDefault);
             FIJI_EDITOR_PATH_DEFAULT        = toks(        winxpFijiEditorPathDefault);
           IMAGEJ_EDITOR_PATH_DEFAULT        = toks(      winxpImageJEditorPathDefault);
      IMAGEMAGICK_EDITOR_PATH_DEFAULT        = toks( winxpImageMagickEditorPathDefault);
              VLC_PLAYER_PATH_DEFAULT        = toks(         winxpVlcPlayerPathDefault);
      BLENDER_X3D_EDITOR_PATH_DEFAULT        = toks(  winxpBlenderX3dEditorPathDefault);
BSCONTENTSTUDIO_X3D_EDITOR_PATH_DEFAULT      = toks(winxpBsContentStudioX3dEditorPathDefault);
         CURA_X3D_EDITOR_PATH_DEFAULT        = toks(     winxpCuraX3dEditorPathDefault);
      MESHLAB_X3D_EDITOR_PATH_DEFAULT        = toks(  winxpMeshLabX3dEditorPathDefault);
     PARAVIEW_X3D_EDITOR_PATH_DEFAULT        = toks( winxpParaviewX3dEditorPathDefault);
  POLYTRANSNUGRAF_EDITOR_PATH_DEFAULT        = toks(   winxpPolyTransNuGrafPathDefault);
   SEAMLESS3D_X3D_EDITOR_PATH_DEFAULT        = toks( winxpSeamlessX3dEditorPathDefault);
      ITKSNAP_X3D_EDITOR_PATH_DEFAULT        = toks(     winxpItksnapEditorPathDefault);
        SEG3D_X3D_EDITOR_PATH_DEFAULT        = toks(       winxpSeg3dEditorPathDefault);
     SLICER3D_X3D_EDITOR_PATH_DEFAULT        = toks(    winxpSlicer3dEditorPathDefault);
     SVG_EDIT_X3D_EDITOR_PATH_DEFAULT        = toks(     winxpSvgeditEditorPathDefault);
    WHITEDUNE_X3D_EDITOR_PATH_DEFAULT        = toks(winxpWhiteDuneX3dEditorPathDefault);
      WINGS3D_X3D_EDITOR_PATH_DEFAULT        = toks(    winxpWingsX3dEditorPathDefault);
    ULTRAEDIT_X3D_EDITOR_PATH_DEFAULT        = toks(winxpUltraEditX3dEditorPathDefault);
    }
    else
    {
      CONTACT_EXECUTABLE_PATH_DEFAULT         = toks(otherContactPathDefault);
      CONTACT_GEO_EXECUTABLE_PATH_DEFAULT     = toks(otherContactGeoPathDefault);
      FREEWRL_EXECUTABLE_PATH_DEFAULT         = toks(otherFreeWrlPathDefault);
      H3D_EXECUTABLE_PATH_DEFAULT             = toks(otherH3dPathDefault);
      HEILAN_EXECUTABLE_PATH_DEFAULT          = toks(otherHeilanPathDefault);
      INSTANTREALITY_EXECUTABLE_PATH_DEFAULT  = toks(otherInstantRealityPathDefault);
      OCTAGA_EXECUTABLE_PATH_DEFAULT          = toks(otherOctagaPathDefault);
      SWIRLX3DPLAYER_EXECUTABLE_PATH_DEFAULT  = toks(otherSwirlX3DPlayerPathDefault);
      VIEW3DSCENE_EXECUTABLE_PATH_DEFAULT     = toks(otherView3dScenePathDefault);
      VIVATYPLAYER_EXECUTABLE_PATH_DEFAULT    = toks(otherVivatyPlayerPathDefault);
      XJ3D_EXECUTABLE_PATH_DEFAULT            = toks(otherXj3DPathDefault);
      OTHER_X3D_PLAYER_EXECUTABLE_PATH_DEFAULT= toks(otherX3dPlayerPathDefault);
      OTHER_X3D_PLAYER_EXECUTABLE_NAME_DEFAULT= toks(otherX3dPlayerNameDefault);
      OTHER_X3D_EDITOR_EXECUTABLE_PATH_DEFAULT= toks(otherX3dEditorPathDefault);
      OTHER_X3D_EDITOR_EXECUTABLE_NAME_DEFAULT= toks(otherX3dEditorNameDefault);
     AMAYA_EDITOR_PATH_DEFAULT                = toks(otherAmayaPathDefault);
     CHROME_EXECUTABLE_PATH_DEFAULT           = toks(otherChromePathDefault);
     FIREFOX_EXECUTABLE_PATH_DEFAULT          = toks(otherFirefoxPathDefault);
     INTERNETEXPLORER_EXECUTABLE_PATH_DEFAULT = toks(otherInternetExplorerPathDefault);
     OPERA_EXECUTABLE_PATH_DEFAULT            = toks(otherOperaPathDefault);
     SAFARI_EXECUTABLE_PATH_DEFAULT           = toks(otherSafariPathDefault);
     
         AUDACITY_EDITOR_PATH_DEFAULT        = toks(    otherAudacityEditorPathDefault);
         MUSESCORE_EDITOR_PATH_DEFAULT       = toks(   otherMuseScoreEditorPathDefault);
             GIMP_EDITOR_PATH_DEFAULT        = toks(        otherGimpEditorPathDefault);
             FIJI_EDITOR_PATH_DEFAULT        = toks(        otherFijiEditorPathDefault);
           IMAGEJ_EDITOR_PATH_DEFAULT        = toks(      otherImageJEditorPathDefault);
      IMAGEMAGICK_EDITOR_PATH_DEFAULT        = toks( otherImageMagickEditorPathDefault);
              VLC_PLAYER_PATH_DEFAULT        = toks(         otherVlcPlayerPathDefault);
      BLENDER_X3D_EDITOR_PATH_DEFAULT        = toks(  otherBlenderX3dEditorPathDefault);
BSCONTENTSTUDIO_X3D_EDITOR_PATH_DEFAULT      = toks(otherBsContentStudioX3dEditorPathDefault);
         CURA_X3D_EDITOR_PATH_DEFAULT        = toks(     otherCuraX3dEditorPathDefault);
      MESHLAB_X3D_EDITOR_PATH_DEFAULT        = toks(  otherMeshLabX3dEditorPathDefault);
     PARAVIEW_X3D_EDITOR_PATH_DEFAULT        = toks( otherParaviewX3dEditorPathDefault);
  POLYTRANSNUGRAF_EDITOR_PATH_DEFAULT        = toks(   otherPolyTransNuGrafPathDefault);
   SEAMLESS3D_X3D_EDITOR_PATH_DEFAULT        = toks( otherSeamlessX3dEditorPathDefault);
      ITKSNAP_X3D_EDITOR_PATH_DEFAULT        = toks(     otherItksnapEditorPathDefault);
        SEG3D_X3D_EDITOR_PATH_DEFAULT        = toks(       otherSeg3dEditorPathDefault);
     SLICER3D_X3D_EDITOR_PATH_DEFAULT        = toks(    otherSlicer3dEditorPathDefault);
     SVG_EDIT_X3D_EDITOR_PATH_DEFAULT        = toks(     otherSvgeditEditorPathDefault);
    WHITEDUNE_X3D_EDITOR_PATH_DEFAULT        = toks(otherWhiteDuneX3dEditorPathDefault);
      WINGS3D_X3D_EDITOR_PATH_DEFAULT        = toks(    otherWingsX3dEditorPathDefault);
    ULTRAEDIT_X3D_EDITOR_PATH_DEFAULT        = toks(otherUltraEditX3dEditorPathDefault);
    }
   
    OTHER_AUDIO_EDITOR_NAME_DEFAULT          = toks(        otherAudioEditorNameDefault);
    OTHER_HTML5_EDITOR_NAME_DEFAULT          = toks(        otherHtml5EditorNameDefault);
    OTHER_IMAGE_EDITOR_NAME_DEFAULT          = toks(        otherImageEditorNameDefault);
    OTHER_VIDEO_EDITOR_NAME_DEFAULT          = toks(        otherVideoEditorNameDefault);
   OTHER_VOLUME_EDITOR_NAME_DEFAULT          = toks(       otherVolumeEditorNameDefault);
    OTHER_X3D_EDITOR_NAME_DEFAULT          = toks(          otherX3dEditorNameDefault);
    OTHER_AUDIO_EDITOR_PATH_DEFAULT          = toks(        otherAudioEditorPathDefault);
    OTHER_HTML5_EDITOR_PATH_DEFAULT          = toks(        otherHtml5EditorPathDefault);
    OTHER_IMAGE_EDITOR_PATH_DEFAULT          = toks(        otherImageEditorPathDefault);
    OTHER_VIDEO_EDITOR_PATH_DEFAULT          = toks(        otherVideoEditorPathDefault);
   OTHER_VOLUME_EDITOR_PATH_DEFAULT          = toks(       otherVolumeEditorPathDefault);
    OTHER_X3D_EDITOR_PATH_DEFAULT          = toks(          otherX3dEditorPathDefault);
  OTHER_AUDIO_EDITOR_SWITCH_DEFAULT          = toks(      otherAudioEditorSwitchDefault);
  OTHER_HTML5_EDITOR_SWITCH_DEFAULT          = toks(      otherHtml5EditorSwitchDefault);
  OTHER_IMAGE_EDITOR_SWITCH_DEFAULT          = toks(      otherImageEditorSwitchDefault);
  OTHER_VIDEO_EDITOR_SWITCH_DEFAULT          = toks(      otherVideoEditorSwitchDefault);
 OTHER_VOLUME_EDITOR_SWITCH_DEFAULT          = toks(     otherVolumeEditorSwitchDefault);
  OTHER_X3D_EDITOR_SWITCH_DEFAULT          = toks(        otherX3dEditorSwitchDefault);
  }
  private static String toks(String s)
  {
    return s.replaceAll(USER_NAME_TOKEN,userName);
  }
  
  public static String getContactPathDefault()        {return CONTACT_EXECUTABLE_PATH_DEFAULT;}
  public static String getContactGeoPathDefault()     {return CONTACT_GEO_EXECUTABLE_PATH_DEFAULT;}
  public static String getFreeWrlPathDefault()        {return FREEWRL_EXECUTABLE_PATH_DEFAULT;}
  public static String getH3dPathDefault()            {return H3D_EXECUTABLE_PATH_DEFAULT;}
  public static String getHeilanPathDefault()         {return HEILAN_EXECUTABLE_PATH_DEFAULT;}
  public static String getInstantRealityPathDefault() {return INSTANTREALITY_EXECUTABLE_PATH_DEFAULT;}
  public static String getOctagaPathDefault()         {return OCTAGA_EXECUTABLE_PATH_DEFAULT;}
  public static String getSwirlX3DPathDefault()       {return SWIRLX3DPLAYER_EXECUTABLE_PATH_DEFAULT;}
  public static String getView3dScenePathDefault()    {return VIEW3DSCENE_EXECUTABLE_PATH_DEFAULT;}
  public static String getVivatyPlayerPathDefault()   {return VIVATYPLAYER_EXECUTABLE_PATH_DEFAULT;}
  public static String getXj3DPathDefault()           {return XJ3D_EXECUTABLE_PATH_DEFAULT;}
  public static String getOtherX3dPlayerNameDefault() {return OTHER_X3D_PLAYER_EXECUTABLE_NAME_DEFAULT;}
  public static String getOtherX3dPlayerPathDefault() {return OTHER_X3D_PLAYER_EXECUTABLE_PATH_DEFAULT;}
  public static String getOtherX3dEditorNameDefault() {return OTHER_X3D_EDITOR_EXECUTABLE_NAME_DEFAULT;}
  public static String getOtherX3dEditorPathDefault() {return OTHER_X3D_EDITOR_EXECUTABLE_PATH_DEFAULT;}

  public static String getLaunchIntervalDefault()     {return LAUNCH_INTERVAL_DEFAULT;}

  public static String getKeystorePathDefault()       {return KEYSTORE_PATH_DEFAULT;}
  
  public static String        getAmayaEditorPathDefault()   {return           AMAYA_EDITOR_PATH_DEFAULT;}
  public static String     getAudacityEditorPathDefault()   {return        AUDACITY_EDITOR_PATH_DEFAULT;}
  public static String    getMuseScoreEditorPathDefault()   {return       MUSESCORE_EDITOR_PATH_DEFAULT;}
  public static String    getGimpImageEditorPathDefault()   {return            GIMP_EDITOR_PATH_DEFAULT;}
  public static String    getFijiImageEditorPathDefault()   {return            FIJI_EDITOR_PATH_DEFAULT;}
  public static String       getImageJEditorPathDefault()   {return          IMAGEJ_EDITOR_PATH_DEFAULT;}
  public static String  getImageMagickEditorPathDefault()   {return     IMAGEMAGICK_EDITOR_PATH_DEFAULT;}
  public static String          getVlcPlayerPathDefault()   {return             VLC_PLAYER_PATH_DEFAULT;}
  public static String   getBlenderX3dEditorPathDefault()   {return     BLENDER_X3D_EDITOR_PATH_DEFAULT;}
  public static String getBsContentStudioX3dEditorPathDefault() {return BSCONTENTSTUDIO_X3D_EDITOR_PATH_DEFAULT;}
  public static String      getCuraX3dEditorPathDefault()   {return        CURA_X3D_EDITOR_PATH_DEFAULT;}
  public static String   getMeshLabX3dEditorPathDefault()   {return     MESHLAB_X3D_EDITOR_PATH_DEFAULT;}
  public static String  getParaviewX3dEditorPathDefault()   {return    PARAVIEW_X3D_EDITOR_PATH_DEFAULT;}
  public static String getPolyTransNuGrafEditorPathDefault(){return POLYTRANSNUGRAF_EDITOR_PATH_DEFAULT;}
  public static String  getSeamlessX3dEditorPathDefault()   {return  SEAMLESS3D_X3D_EDITOR_PATH_DEFAULT;}
  public static String      getItksnapEditorPathDefault()   {return     ITKSNAP_X3D_EDITOR_PATH_DEFAULT;}
  public static String        getSeg3dEditorPathDefault()   {return       SEG3D_X3D_EDITOR_PATH_DEFAULT;}
  public static String     getSlicer3dEditorPathDefault()   {return    SLICER3D_X3D_EDITOR_PATH_DEFAULT;}
  public static String      getSvgeditEditorPathDefault()   {return    SVG_EDIT_X3D_EDITOR_PATH_DEFAULT;}
  public static String getWhiteDuneX3dEditorPathDefault()   {return   WHITEDUNE_X3D_EDITOR_PATH_DEFAULT;}
  public static String     getWingsX3dEditorPathDefault()   {return     WINGS3D_X3D_EDITOR_PATH_DEFAULT;}
  public static String getUltraEditX3dEditorPathDefault()   {return   ULTRAEDIT_X3D_EDITOR_PATH_DEFAULT;}
  public static String   getOtherAudioEditorNameDefault()   {return     OTHER_AUDIO_EDITOR_NAME_DEFAULT;}
  public static String   getOtherHtml5EditorNameDefault()   {return     OTHER_HTML5_EDITOR_NAME_DEFAULT;}
  public static String   getOtherImageEditorNameDefault()   {return     OTHER_IMAGE_EDITOR_NAME_DEFAULT;}
  public static String   getOtherVideoEditorNameDefault()   {return     OTHER_VIDEO_EDITOR_NAME_DEFAULT;}
  public static String  getOtherVolumeEditorNameDefault()   {return    OTHER_VOLUME_EDITOR_NAME_DEFAULT;}
  public static String   getOtherSceneEditorNameDefault()   {return     OTHER_X3D_EDITOR_NAME_DEFAULT;}
  public static String   getOtherAudioEditorPathDefault()   {return     OTHER_AUDIO_EDITOR_PATH_DEFAULT;}
  public static String   getOtherHtml5EditorPathDefault()   {return     OTHER_HTML5_EDITOR_PATH_DEFAULT;}
  public static String   getOtherImageEditorPathDefault()   {return     OTHER_IMAGE_EDITOR_PATH_DEFAULT;}
  public static String   getOtherVideoEditorPathDefault()   {return     OTHER_VIDEO_EDITOR_PATH_DEFAULT;}
  public static String  getOtherVolumeEditorPathDefault()   {return    OTHER_VOLUME_EDITOR_PATH_DEFAULT;}
  public static String   getOtherSceneEditorPathDefault()   {return     OTHER_X3D_EDITOR_PATH_DEFAULT;}
  public static String   getOtherAudioEditorSwitchDefault() {return     OTHER_AUDIO_EDITOR_SWITCH_DEFAULT;}
  public static String   getOtherHtml5EditorSwitchDefault() {return     OTHER_HTML5_EDITOR_SWITCH_DEFAULT;}
  public static String   getOtherImageEditorSwitchDefault() {return     OTHER_IMAGE_EDITOR_SWITCH_DEFAULT;}
  public static String   getOtherVideoEditorSwitchDefault() {return     OTHER_VIDEO_EDITOR_SWITCH_DEFAULT;}
  public static String  getOtherVolumeEditorSwitchDefault() {return    OTHER_VOLUME_EDITOR_SWITCH_DEFAULT;}
  public static String   getOtherSceneEditorSwitchDefault() {return     OTHER_X3D_EDITOR_SWITCH_DEFAULT;}

  public static void setContactPath              (String path){commonSet(CONTACT_EXECUTABLE_PATH_KEY, path);}
  public static void setContactGeoPath           (String path){commonSet(CONTACT_GEO_EXECUTABLE_PATH_KEY, path);}
  public static void setFreeWrlPath              (String path){commonSet(FREEWRL_EXECUTABLE_PATH_KEY, path);}
  public static void setH3dPath                  (String path){commonSet(H3D_EXECUTABLE_PATH_KEY, path);}
  public static void setHeilanPath               (String path){commonSet(HEILAN_EXECUTABLE_PATH_KEY, path);}
  public static void setInstantRealityPath       (String path){commonSet(INSTANTREALITY_EXECUTABLE_PATH_KEY, path);}
  public static void setOctagaPath               (String path){commonSet(OCTAGA_EXECUTABLE_PATH_KEY, path);}
  public static void setSwirlX3DPath             (String path){commonSet(SWIRLX3DPLAYER_EXECUTABLE_PATH_KEY, path);}
  public static void setView3dScenePath          (String path){commonSet(VIEW3DSCENE_EXECUTABLE_PATH_KEY, path);}
  public static void setVivatyPlayerPath         (String path){commonSet(VIVATYPLAYER_EXECUTABLE_PATH_KEY, path);}
  public static void setXj3DPath                 (String path){commonSet(XJ3D_EXECUTABLE_PATH_KEY, path);}
  public static void setOtherX3dPlayerName       (String name){commonSet(OTHER_X3D_PLAYER_EXECUTABLE_NAME_KEY, name);}
  public static void setOtherX3dPlayerPath       (String path){commonSet(OTHER_X3D_PLAYER_EXECUTABLE_PATH_KEY, path);}
  public static void setOtherX3dPlayerSwitch     (String pswitch){commonSet(OTHER_X3D_PLAYER_EXECUTABLE_SWITCH_KEY, pswitch);}
  public static void setOtherX3dEditorName       (String name){commonSet(OTHER_X3D_EDITOR_EXECUTABLE_NAME_KEY, name);}
  public static void setOtherX3dEditorPath       (String path){commonSet(OTHER_X3D_EDITOR_EXECUTABLE_PATH_KEY, path);}
  
  public static void        setAmayaEditorPath    (String path){commonSet(         AMAYA_EDITOR_PATH_KEY, path);}
  public static void     setAudacityEditorPath    (String path){commonSet(      AUDACITY_EDITOR_PATH_KEY, path);}
  public static void    setMuseScoreEditorPath    (String path){commonSet(     MUSESCORE_EDITOR_PATH_KEY, path);}
  public static void    setGimpImageEditorPath    (String path){commonSet(          GIMP_EDITOR_PATH_KEY, path);}
  public static void         setFijiEditorPath    (String path){commonSet(          FIJI_EDITOR_PATH_KEY, path);}
  public static void       setImageJEditorPath    (String path){commonSet(        IMAGEJ_EDITOR_PATH_KEY, path);}
  public static void  setImageMagickEditorPath    (String path){commonSet(   IMAGEMAGICK_EDITOR_PATH_KEY, path);}
  public static void          setVlcPlayerPath    (String path){commonSet(           VLC_PLAYER_PATH_KEY, path);}
  public static void   setBlenderX3dEditorPath    (String path){commonSet(   BLENDER_X3D_EDITOR_PATH_KEY, path);}
  public static void setBsContentStudioX3dEditorPath(String path){commonSet(BSCONTENTSTUDIO_X3D_EDITOR_PATH_KEY, path);}
  public static void      setCuraX3dEditorPath    (String path){commonSet(      CURA_X3D_EDITOR_PATH_KEY, path);}
  public static void   setMeshLabX3dEditorPath    (String path){commonSet(   MESHLAB_X3D_EDITOR_PATH_KEY, path);}
  public static void  setParaviewX3dEditorPath    (String path){commonSet(  PARAVIEW_X3D_EDITOR_PATH_KEY, path);}
  public static void setPolyTransNuGrafEditorPath (String path){commonSet(POLYTRANSNUGRAF_EDITOR_PATH_KEY, path);}
  public static void  setSeamlessX3dEditorPath    (String path){commonSet(SEAMLESS3D_X3D_EDITOR_PATH_KEY, path);}
  public static void      setItksnapEditorPath    (String path){commonSet(ITKSNAP_VOLUME_EDITOR_PATH_KEY, path);}
  public static void        setSeg3dEditorPath    (String path){commonSet(  SEG3D_VOLUME_EDITOR_PATH_KEY, path);}
  public static void     setSlicer3dEditorPath    (String path){commonSet(SLICER3D_VOLUME_EDITOR_PATH_KEY,path);}
  public static void      setSvgeditEditorPath    (String path){commonSet(SVG_EDIT_VOLUME_EDITOR_PATH_KEY,path);}
  public static void setWhiteDuneX3dEditorPath    (String path){commonSet( WHITEDUNE_X3D_EDITOR_PATH_KEY, path);}
  public static void     setWingsX3dEditorPath    (String path){commonSet(   WINGS3D_X3D_EDITOR_PATH_KEY, path);}
  public static void setUltraEditX3dEditorPath    (String path){commonSet( ULTRAEDIT_X3D_EDITOR_PATH_KEY, path);}
  
  public static void   setOtherAudioEditorName    (String name){commonSet(   OTHER_AUDIO_EDITOR_NAME_KEY, name);}
  public static void   setOtherHtml5EditorName    (String name){commonSet(   OTHER_HTML5_EDITOR_NAME_KEY, name);}
  public static void   setOtherImageEditorName    (String name){commonSet(   OTHER_IMAGE_EDITOR_NAME_KEY, name);}
  public static void   setOtherVideoEditorName    (String name){commonSet(   OTHER_VIDEO_EDITOR_NAME_KEY, name);}
  public static void  setOtherVolumeEditorName    (String name){commonSet(  OTHER_VOLUME_EDITOR_NAME_KEY, name);}
  public static void   setOtherSceneEditorName    (String name){commonSet(OTHER_X3D_EDITOR_NAME_KEY, name);}
  
  public static void   setOtherAudioEditorPath    (String path){commonSet(   OTHER_AUDIO_EDITOR_PATH_KEY, path);}
  public static void   setOtherHtml5EditorPath    (String path){commonSet(   OTHER_HTML5_EDITOR_PATH_KEY, path);}
  public static void   setOtherImageEditorPath    (String path){commonSet(   OTHER_IMAGE_EDITOR_PATH_KEY, path);}
  public static void   setOtherVideoEditorPath    (String path){commonSet(   OTHER_VIDEO_EDITOR_PATH_KEY, path);}
  public static void  setOtherVolumeEditorPath    (String path){commonSet(  OTHER_VOLUME_EDITOR_PATH_KEY, path);}
  public static void   setOtherSceneEditorPath    (String path){commonSet(OTHER_X3D_EDITOR_PATH_KEY, path);}
  public static void setOtherAudioEditorSwitch (String pswitch){commonSet(   OTHER_AUDIO_EDITOR_SWITCH_KEY, pswitch);}
  public static void setOtherHtml5EditorSwitch (String pswitch){commonSet(   OTHER_HTML5_EDITOR_SWITCH_KEY, pswitch);}
  public static void setOtherImageEditorSwitch (String pswitch){commonSet(   OTHER_IMAGE_EDITOR_SWITCH_KEY, pswitch);}
  public static void setOtherVideoEditorSwitch (String pswitch){commonSet(   OTHER_VIDEO_EDITOR_SWITCH_KEY, pswitch);}
  public static void setOtherVolumeEditorSwitch(String pswitch){commonSet(  OTHER_VOLUME_EDITOR_SWITCH_KEY, pswitch);}
  public static void setOtherSceneEditorSwitch (String pswitch){commonSet(OTHER_X3D_EDITOR_SWITCH_KEY, pswitch);}

  public static void setContactAutoLaunch        (String autoLaunch){commonSet(CONTACT_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void setContactGeoAutoLaunch     (String autoLaunch){commonSet(CONTACT_GEO_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void setFreeWrlAutoLaunch        (String autoLaunch){commonSet(FREEWRL_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void setH3dAutoLaunch            (String autoLaunch){commonSet(H3D_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void setHeilanAutoLaunch         (String autoLaunch){commonSet(HEILAN_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void setInstantRealityAutoLaunch (String autoLaunch){commonSet(INSTANTREALITY_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void setOctagaAutoLaunch         (String autoLaunch){commonSet(OCTAGA_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void setSwirlX3dAutoLaunch       (String autoLaunch){commonSet(SWIRLX3DPLAYER_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void setView3dSceneAutoLaunch    (String autoLaunch){commonSet(VIEW3DSCENE_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void setVivatyPlayerAutoLaunch   (String autoLaunch){commonSet(VIVATYPLAYER_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void setXj3dAutoLaunch           (String autoLaunch){commonSet(XJ3D_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void setOtherX3dPlayerAutoLaunch (String autoLaunch){commonSet(OTHER_X3D_PLAYER_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void setOtherX3dEditorAutoLaunch (String autoLaunch){commonSet(OTHER_X3D_EDITOR_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  
  public static void            setAmayaAutoLaunch (String autoLaunch){commonSet(             AMAYA_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void         setAudacityAutoLaunch (String autoLaunch){commonSet(          AUDACITY_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void        setMuseScoreAutoLaunch (String autoLaunch){commonSet(         MUSESCORE_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void             setGimpAutoLaunch (String autoLaunch){commonSet(              GIMP_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void             setFijiAutoLaunch (String autoLaunch){commonSet(              FIJI_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void           setImageJAutoLaunch (String autoLaunch){commonSet(            IMAGEJ_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void      setImageMagickAutoLaunch (String autoLaunch){commonSet(       IMAGEMAGICK_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void              setVlcAutoLaunch (String autoLaunch){commonSet(               VLC_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void          setBlenderAutoLaunch (String autoLaunch){commonSet(           BLENDER_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void  setBsContentStudioAutoLaunch (String autoLaunch){commonSet(   BSCONTENTSTUDIO_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void             setCuraAutoLaunch (String autoLaunch){commonSet(              CURA_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void          setMeshLabAutoLaunch (String autoLaunch){commonSet(           MESHLAB_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void         setParaviewAutoLaunch (String autoLaunch){commonSet(          PARAVIEW_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void  setPolyTransNuGrafAutoLaunch (String autoLaunch){commonSet(       POLYTRANSNUGRAF_EDITOR_AUTOLAUNCH_KEY, autoLaunch);}
  public static void       setSeamless3dAutoLaunch (String autoLaunch){commonSet(        SEAMLESS3D_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void          setItksnapAutoLaunch (String autoLaunch){commonSet(           ITKSNAP_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void            setSeg3dAutoLaunch (String autoLaunch){commonSet(             SEG3D_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void         setSlicer3dAutoLaunch (String autoLaunch){commonSet(          SLICER3D_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void          setSvgeditAutoLaunch (String autoLaunch){commonSet(          SVG_EDIT_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void        setWhiteDuneAutoLaunch (String autoLaunch){commonSet(         WHITEDUNE_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void          setWings3dAutoLaunch (String autoLaunch){commonSet(           WINGS3D_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void        setUltraEditAutoLaunch (String autoLaunch){commonSet(         ULTRAEDIT_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void setOtherAudioEditorAutoLaunch (String autoLaunch){commonSet(OTHER_AUDIO_EDITOR_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void setOtherHtml5EditorAutoLaunch (String autoLaunch){commonSet(OTHER_HTML5_EDITOR_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void setOtherImageEditorAutoLaunch (String autoLaunch){commonSet(OTHER_IMAGE_EDITOR_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void setOtherVideoEditorAutoLaunch (String autoLaunch){commonSet(OTHER_VIDEO_EDITOR_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void setOtherVolumeEditorAutoLaunch(String autoLaunch){commonSet(OTHER_VOLUME_EDITOR_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}

  public static void setLaunchInterval           (String path){commonSet(LAUNCH_INTERVAL_KEY,path);}

  public static void setKeystorePath             (String path){commonSet(KEYSTORE_PATH_KEY,path);}

  public static String getContactPath()          {return commonGet(CONTACT_EXECUTABLE_PATH_KEY,        CONTACT_EXECUTABLE_PATH_DEFAULT);}
  public static String getContactGeoPath()       {return commonGet(CONTACT_GEO_EXECUTABLE_PATH_KEY,    CONTACT_GEO_EXECUTABLE_PATH_DEFAULT);}
  public static String getFreeWrlPath()          {return commonGet(FREEWRL_EXECUTABLE_PATH_KEY,        FREEWRL_EXECUTABLE_PATH_DEFAULT);}
  public static String getH3dPath()              {return commonGet(H3D_EXECUTABLE_PATH_KEY,            H3D_EXECUTABLE_PATH_DEFAULT);}
  public static String getHeilanPath()           {return commonGet(HEILAN_EXECUTABLE_PATH_KEY,         HEILAN_EXECUTABLE_PATH_DEFAULT);}
  public static String getInstantRealityPath()   {return commonGet(INSTANTREALITY_EXECUTABLE_PATH_KEY, INSTANTREALITY_EXECUTABLE_PATH_DEFAULT);}
  public static String getOctagaPath()           {return commonGet(OCTAGA_EXECUTABLE_PATH_KEY,         OCTAGA_EXECUTABLE_PATH_DEFAULT);}
  public static String getSwirlX3DPath()         {return commonGet(SWIRLX3DPLAYER_EXECUTABLE_PATH_KEY, SWIRLX3DPLAYER_EXECUTABLE_PATH_DEFAULT);}
  public static String getView3dScenePath()      {return commonGet(VIEW3DSCENE_EXECUTABLE_PATH_KEY,    VIEW3DSCENE_EXECUTABLE_PATH_DEFAULT);}
  public static String getVivatyPlayerPath()     {return commonGet(VIVATYPLAYER_EXECUTABLE_PATH_KEY,   VIVATYPLAYER_EXECUTABLE_PATH_DEFAULT);}
  public static String getXj3DPath()             {return commonGet(XJ3D_EXECUTABLE_PATH_KEY,           XJ3D_EXECUTABLE_PATH_DEFAULT);}
  public static String getOtherX3dPlayerName()   {return commonGet(OTHER_X3D_PLAYER_EXECUTABLE_NAME_KEY,   OTHER_X3D_PLAYER_EXECUTABLE_NAME_DEFAULT);}
  public static String getOtherX3dPlayerPath()   {return commonGet(OTHER_X3D_PLAYER_EXECUTABLE_PATH_KEY,   OTHER_X3D_PLAYER_EXECUTABLE_PATH_DEFAULT);}
  public static String getOtherX3dPlayerSwitch() {return commonGet(OTHER_X3D_PLAYER_EXECUTABLE_SWITCH_KEY, OTHER_X3D_PLAYER_EXECUTABLE_SWITCH_DEFAULT);}
  public static String getOtherX3dEditorName()   {return commonGet(OTHER_X3D_EDITOR_EXECUTABLE_NAME_KEY,   OTHER_X3D_EDITOR_EXECUTABLE_NAME_DEFAULT);}
  public static String getOtherX3dEditorPath()   {return commonGet(OTHER_X3D_EDITOR_EXECUTABLE_PATH_KEY,   OTHER_X3D_EDITOR_EXECUTABLE_PATH_DEFAULT);}
  
  public static String        getAmayaEditorPath()       {return commonGet(         AMAYA_EDITOR_PATH_KEY,         AMAYA_EDITOR_PATH_DEFAULT);}
  public static String     getAudacityEditorPath()       {return commonGet(      AUDACITY_EDITOR_PATH_KEY,      AUDACITY_EDITOR_PATH_DEFAULT);}
  public static String    getMuseScoreEditorPath()       {return commonGet(     MUSESCORE_EDITOR_PATH_KEY,     MUSESCORE_EDITOR_PATH_DEFAULT);}
  public static String    getGimpImageEditorPath()       {return commonGet(          GIMP_EDITOR_PATH_KEY,          GIMP_EDITOR_PATH_DEFAULT);}
  public static String    getFijiImageEditorPath()       {return commonGet(          FIJI_EDITOR_PATH_KEY,          FIJI_EDITOR_PATH_DEFAULT);}
  public static String       getImageJEditorPath()       {return commonGet(        IMAGEJ_EDITOR_PATH_KEY,        IMAGEJ_EDITOR_PATH_DEFAULT);}
  public static String  getImageMagickEditorPath()       {return commonGet(   IMAGEMAGICK_EDITOR_PATH_KEY,   IMAGEMAGICK_EDITOR_PATH_DEFAULT);}
  public static String          getVlcPlayerPath()       {return commonGet(           VLC_PLAYER_PATH_KEY,           VLC_PLAYER_PATH_DEFAULT);}
  public static String   getBlenderX3dEditorPath()       {return commonGet(   BLENDER_X3D_EDITOR_PATH_KEY,   BLENDER_X3D_EDITOR_PATH_DEFAULT);}
  public static String getBsContentStudioX3dEditorPath() {return commonGet(BSCONTENTSTUDIO_X3D_EDITOR_PATH_KEY,  BSCONTENTSTUDIO_X3D_EDITOR_PATH_DEFAULT);}
  public static String      getCuraX3dEditorPath()       {return commonGet(      CURA_X3D_EDITOR_PATH_KEY,      CURA_X3D_EDITOR_PATH_DEFAULT);}
  public static String   getMeshLabX3dEditorPath()       {return commonGet(   MESHLAB_X3D_EDITOR_PATH_KEY,   MESHLAB_X3D_EDITOR_PATH_DEFAULT);}
  public static String  getParaviewX3dEditorPath()       {return commonGet(  PARAVIEW_X3D_EDITOR_PATH_KEY,  PARAVIEW_X3D_EDITOR_PATH_DEFAULT);}
  public static String getPolyTransNuGrafEditorPath()    {return commonGet(POLYTRANSNUGRAF_EDITOR_PATH_KEY, POLYTRANSNUGRAF_EDITOR_PATH_DEFAULT);}
  public static String  getSeamlessX3dEditorPath()       {return commonGet(SEAMLESS3D_X3D_EDITOR_PATH_KEY,SEAMLESS3D_X3D_EDITOR_PATH_DEFAULT);}
  public static String      getItksnapEditorPath()       {return commonGet(ITKSNAP_VOLUME_EDITOR_PATH_KEY,   ITKSNAP_X3D_EDITOR_PATH_DEFAULT);}
  public static String        getSeg3dEditorPath()       {return commonGet(  SEG3D_VOLUME_EDITOR_PATH_KEY,     SEG3D_X3D_EDITOR_PATH_DEFAULT);}
  public static String     getSlicer3dEditorPath()       {return commonGet(SLICER3D_VOLUME_EDITOR_PATH_KEY, SLICER3D_X3D_EDITOR_PATH_DEFAULT);}
  public static String      getSvgeditEditorPath()       {return commonGet(SVG_EDIT_VOLUME_EDITOR_PATH_KEY, SVG_EDIT_X3D_EDITOR_PATH_DEFAULT);}
  public static String getWhiteDuneX3dEditorPath()       {return commonGet( WHITEDUNE_X3D_EDITOR_PATH_KEY, WHITEDUNE_X3D_EDITOR_PATH_DEFAULT);}
  public static String     getWingsX3dEditorPath()       {return commonGet(   WINGS3D_X3D_EDITOR_PATH_KEY,   WINGS3D_X3D_EDITOR_PATH_DEFAULT);}
  public static String getUltraEditX3dEditorPath()       {return commonGet( ULTRAEDIT_X3D_EDITOR_PATH_KEY, ULTRAEDIT_X3D_EDITOR_PATH_DEFAULT);}
  public static String   getOtherAudioEditorPath()       {return commonGet(   OTHER_AUDIO_EDITOR_PATH_KEY,   OTHER_AUDIO_EDITOR_PATH_DEFAULT);}
  public static String   getOtherHtml5EditorPath()       {return commonGet(   OTHER_HTML5_EDITOR_PATH_KEY,   OTHER_HTML5_EDITOR_PATH_DEFAULT);}
  public static String   getOtherImageEditorPath()       {return commonGet(   OTHER_IMAGE_EDITOR_PATH_KEY,   OTHER_IMAGE_EDITOR_PATH_DEFAULT);}
  public static String   getOtherVideoEditorPath()       {return commonGet(   OTHER_VIDEO_EDITOR_PATH_KEY,   OTHER_VIDEO_EDITOR_PATH_DEFAULT);}
  public static String   getOtherVolumeEditorPath()      {return commonGet(  OTHER_VOLUME_EDITOR_PATH_KEY,  OTHER_VOLUME_EDITOR_PATH_DEFAULT);}
  public static String   getOtherAudioEditorName()       {return commonGet(   OTHER_AUDIO_EDITOR_NAME_KEY,   OTHER_AUDIO_EDITOR_NAME_DEFAULT);}
  public static String   getOtherHtml5EditorName()       {return commonGet(   OTHER_HTML5_EDITOR_NAME_KEY,   OTHER_HTML5_EDITOR_NAME_DEFAULT);}
  public static String   getOtherImageEditorName()       {return commonGet(   OTHER_IMAGE_EDITOR_NAME_KEY,   OTHER_IMAGE_EDITOR_NAME_DEFAULT);}
  public static String   getOtherVideoEditorName()       {return commonGet(   OTHER_VIDEO_EDITOR_NAME_KEY,   OTHER_VIDEO_EDITOR_NAME_DEFAULT);}
  public static String   getOtherVolumeEditorName()      {return commonGet(  OTHER_VOLUME_EDITOR_NAME_KEY,  OTHER_VOLUME_EDITOR_NAME_DEFAULT);}

  public static String isContactAutoLaunch()        {return commonGet(CONTACT_EXECUTABLE_AUTOLAUNCH_KEY,               AUTOLAUNCH_DEFAULT);}
  public static String isContactGeoAutoLaunch()     {return commonGet(CONTACT_GEO_EXECUTABLE_AUTOLAUNCH_KEY,           AUTOLAUNCH_DEFAULT);}
  public static String isFreeWrlAutoLaunch()        {return commonGet(FREEWRL_EXECUTABLE_AUTOLAUNCH_KEY,               AUTOLAUNCH_DEFAULT);}
  public static String isH3dAutoLaunch()            {return commonGet(H3D_EXECUTABLE_AUTOLAUNCH_KEY,                   AUTOLAUNCH_DEFAULT);}
  public static String isHeilanAutoLaunch()         {return commonGet(HEILAN_EXECUTABLE_AUTOLAUNCH_KEY,         HEILAN_AUTOLAUNCH_DEFAULT);}
  public static String isInstantRealityAutoLaunch() {return commonGet(INSTANTREALITY_EXECUTABLE_AUTOLAUNCH_KEY,        AUTOLAUNCH_DEFAULT);}
  public static String isOctagaAutoLaunch()         {return commonGet(OCTAGA_EXECUTABLE_AUTOLAUNCH_KEY,                AUTOLAUNCH_DEFAULT);}
  public static String isSwirlX3DAutoLaunch()       {return commonGet(SWIRLX3DPLAYER_EXECUTABLE_AUTOLAUNCH_KEY,        AUTOLAUNCH_DEFAULT);}
  public static String isView3dSceneAutoLaunch()    {return commonGet(VIEW3DSCENE_EXECUTABLE_AUTOLAUNCH_KEY,           AUTOLAUNCH_DEFAULT);}
  public static String isVivatyPlayerAutoLaunch()   {return commonGet(VIVATYPLAYER_EXECUTABLE_AUTOLAUNCH_KEY,          AUTOLAUNCH_DEFAULT);}
  public static String isXj3dAutoLaunch()           {return commonGet(XJ3D_EXECUTABLE_AUTOLAUNCH_KEY,                  AUTOLAUNCH_DEFAULT);}
  public static String isOtherX3dPlayerAutoLaunch() {return commonGet(OTHER_X3D_PLAYER_EXECUTABLE_AUTOLAUNCH_KEY,      AUTOLAUNCH_DEFAULT);}
  public static String isOtherX3dEditorAutoLaunch() {return commonGet(OTHER_X3D_EDITOR_EXECUTABLE_AUTOLAUNCH_KEY,      AUTOLAUNCH_DEFAULT);}
  
  public static String             isAmayaAutoLaunch() {return commonGet(              AMAYA_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String          isAudacityAutoLaunch() {return commonGet(           AUDACITY_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String         isMuseScoreAutoLaunch() {return commonGet(          MUSESCORE_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String              isGimpAutoLaunch() {return commonGet(               GIMP_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String              isFijiAutoLaunch() {return commonGet(               FIJI_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String            isImageJAutoLaunch() {return commonGet(             IMAGEJ_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String       isImageMagickAutoLaunch() {return commonGet(        IMAGEMAGICK_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String               isVlcAutoLaunch() {return commonGet(                VLC_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String           isBlenderAutoLaunch() {return commonGet(            BLENDER_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String   isBsContentStudioAutoLaunch() {return commonGet(    BSCONTENTSTUDIO_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String              isCuraAutoLaunch() {return commonGet(               CURA_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String           isMeshLabAutoLaunch() {return commonGet(            MESHLAB_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String          isParaviewAutoLaunch() {return commonGet(           PARAVIEW_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String   isPolyTransNuGrafAutoLaunch() {return commonGet(        POLYTRANSNUGRAF_EDITOR_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String        isSeamless3dAutoLaunch() {return commonGet(         SEAMLESS3D_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String           isItksnapAutoLaunch() {return commonGet(            ITKSNAP_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String             isSeg3dAutoLaunch() {return commonGet(              SEG3D_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String          isSlicer3dAutoLaunch() {return commonGet(           SLICER3D_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String           isSvgeditAutoLaunch() {return commonGet(           SVG_EDIT_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String         isWhiteDuneAutoLaunch() {return commonGet(          WHITEDUNE_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String           isWings3dAutoLaunch() {return commonGet(            WINGS3D_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String         isUltraEditAutoLaunch() {return commonGet(          ULTRAEDIT_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String  isOtherAudioEditorAutoLaunch() {return commonGet( OTHER_AUDIO_EDITOR_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String  isOtherHtml5EditorAutoLaunch() {return commonGet( OTHER_HTML5_EDITOR_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String  isOtherImageEditorAutoLaunch() {return commonGet( OTHER_IMAGE_EDITOR_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String  isOtherVideoEditorAutoLaunch() {return commonGet( OTHER_VIDEO_EDITOR_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String isOtherVolumeEditorAutoLaunch() {return commonGet(OTHER_VOLUME_EDITOR_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}

  public static String getLaunchInterval()             {return commonGet(LAUNCH_INTERVAL_KEY,                  LAUNCH_INTERVAL_DEFAULT);}
  public static Long   getLaunchIntervalMilliseconds() {return Long.valueOf(getLaunchInterval()) * 1000l;} // convert seconds to msec

  public static String getKeystorePath()      {return commonGet(KEYSTORE_PATH_KEY,                      KEYSTORE_PATH_DEFAULT);}

  public static void resetContactPath()          {commonReset(CONTACT_EXECUTABLE_PATH_KEY);}
  public static void resetContactGeoPath()       {commonReset(CONTACT_GEO_EXECUTABLE_PATH_KEY);}
  public static void resetFreeWrlPath()          {commonReset(FREEWRL_EXECUTABLE_PATH_KEY);}
  public static void resetH3dPath()              {commonReset(H3D_EXECUTABLE_PATH_KEY);}
  public static void resetHeilanPath()           {commonReset(HEILAN_EXECUTABLE_PATH_KEY);}
  public static void resetInstantRealityPath()   {commonReset(INSTANTREALITY_EXECUTABLE_PATH_KEY);}
  public static void resetOctagaPath()           {commonReset(OCTAGA_EXECUTABLE_PATH_KEY);}
  public static void resetSwirlX3DPath()         {commonReset(SWIRLX3DPLAYER_EXECUTABLE_PATH_KEY);}
  public static void resetView3dScenePath()      {commonReset(VIEW3DSCENE_EXECUTABLE_PATH_KEY);}
  public static void resetVivatyPlayerPath()     {commonReset(VIVATYPLAYER_EXECUTABLE_PATH_KEY);}
  public static void resetXj3DPath()             {commonReset(XJ3D_EXECUTABLE_PATH_KEY);}
  public static void resetOtherX3dPlayerPath()   {commonReset(OTHER_X3D_PLAYER_EXECUTABLE_PATH_KEY);}
  public static void resetOtherX3dPlayerName()   {commonReset(OTHER_X3D_PLAYER_EXECUTABLE_NAME_KEY);}
  public static void resetOtherX3dPlayerSwitch() {commonReset(OTHER_X3D_PLAYER_EXECUTABLE_SWITCH_KEY);}
  public static void resetOtherX3dEditorPath()   {commonReset(OTHER_X3D_EDITOR_EXECUTABLE_PATH_KEY);}
  public static void resetOtherX3dEditorName()   {commonReset(OTHER_X3D_EDITOR_EXECUTABLE_NAME_KEY);}

  public static void resetLaunchInterval()       {commonReset(LAUNCH_INTERVAL_KEY);}

  public static void resetKeystorePath()         {commonReset(KEYSTORE_PATH_KEY);}
  
  public static void        resetAmayaEditorPath() {commonReset(          AMAYA_EDITOR_PATH_KEY);}
  public static void     resetAudacityEditorPath() {commonReset(       AUDACITY_EDITOR_PATH_KEY);}
  public static void    resetMuseScoreEditorPath() {commonReset(      MUSESCORE_EDITOR_PATH_KEY);}
  public static void         resetGimpEditorPath() {commonReset(           GIMP_EDITOR_PATH_KEY);}
  public static void         resetFijiEditorPath() {commonReset(           FIJI_EDITOR_PATH_KEY);}
  public static void       resetImageJEditorPath() {commonReset(         IMAGEJ_EDITOR_PATH_KEY);}
  public static void  resetImageMagickEditorPath() {commonReset(    IMAGEMAGICK_EDITOR_PATH_KEY);}
  public static void          resetVlcPlayerPath() {commonReset(            VLC_PLAYER_PATH_KEY);}
  public static void   resetBlenderX3dEditorPath() {commonReset(    BLENDER_X3D_EDITOR_PATH_KEY);}
  public static void resetBsContentStudioX3dEditorPath() {commonReset(BSCONTENTSTUDIO_X3D_EDITOR_PATH_KEY);}
  public static void      resetCuraX3dEditorPath() {commonReset(       CURA_X3D_EDITOR_PATH_KEY);}
  public static void   resetMeshLabX3dEditorPath() {commonReset(    MESHLAB_X3D_EDITOR_PATH_KEY);}
  public static void  resetParaviewX3dEditorPath() {commonReset(   PARAVIEW_X3D_EDITOR_PATH_KEY);}
  public static void    resetPolyTransNuGrafPath() {commonReset(POLYTRANSNUGRAF_EDITOR_PATH_KEY);}
  public static void  resetSeamlessX3dEditorPath() {commonReset( SEAMLESS3D_X3D_EDITOR_PATH_KEY);}
  public static void      resetItksnapEditorPath() {commonReset( ITKSNAP_VOLUME_EDITOR_PATH_KEY);}
  public static void        resetSeg3dEditorPath() {commonReset(   SEG3D_VOLUME_EDITOR_PATH_KEY);}
  public static void     resetSlicer3dEditorPath() {commonReset(SLICER3D_VOLUME_EDITOR_PATH_KEY);}
  public static void      resetSvgeditEditorPath() {commonReset(SVG_EDIT_VOLUME_EDITOR_PATH_KEY);}
  public static void resetWhiteDuneX3dEditorPath() {commonReset(  WHITEDUNE_X3D_EDITOR_PATH_KEY);}
  public static void     resetWingsX3dEditorPath() {commonReset(    WINGS3D_X3D_EDITOR_PATH_KEY);}
  public static void resetUltraEditX3dEditorPath() {commonReset(  ULTRAEDIT_X3D_EDITOR_PATH_KEY);}
  public static void   resetOtherAudioEditorPath() {commonReset(    OTHER_AUDIO_EDITOR_PATH_KEY);}
  public static void   resetOtherHtml5EditorPath() {commonReset(    OTHER_HTML5_EDITOR_PATH_KEY);}
  public static void   resetOtherImageEditorPath() {commonReset(    OTHER_IMAGE_EDITOR_PATH_KEY);}
  public static void   resetOtherVideoEditorPath() {commonReset(    OTHER_VIDEO_EDITOR_PATH_KEY);}
  public static void  resetOtherVolumeEditorPath() {commonReset(   OTHER_VOLUME_EDITOR_PATH_KEY);}
  public static void   resetOtherAudioEditorName() {commonReset(    OTHER_AUDIO_EDITOR_NAME_KEY);}
  public static void   resetOtherHtml5EditorName() {commonReset(    OTHER_HTML5_EDITOR_NAME_KEY);}
  public static void   resetOtherImageEditorName() {commonReset(    OTHER_IMAGE_EDITOR_NAME_KEY);}
  public static void   resetOtherVideoEditorName() {commonReset(    OTHER_VIDEO_EDITOR_NAME_KEY);}
  public static void  resetOtherVolumeEditorName() {commonReset(   OTHER_VOLUME_EDITOR_NAME_KEY);}
 
  private static void commonSet(String key, String val)
  {
    Preferences prefs = NbPreferences.forModule(X3dOptions.class);
    prefs.put(key,val);  
  }
  private static void commonBSet(String key, boolean tf)
  {
    Preferences prefs = NbPreferences.forModule(X3dOptions.class);
    prefs.putBoolean(key,tf);      
  }
  public static String commonGet(String key, String defaultValue)
  {
    Preferences prefs = NbPreferences.forModule(X3dOptions.class);
    return prefs.get(key, defaultValue);
  } 
  public static boolean commonBGet(String key, boolean defalt)
  {
    Preferences prefs = NbPreferences.forModule(X3dOptions.class);
    return prefs.getBoolean(key, defalt);   
  }
  public static void commonReset(String key)
  {
    Preferences prefs = NbPreferences.forModule(X3dOptions.class);
    prefs.remove(key);
  }
  
  public static String CERTIFICATE_SERIALNUMBER_KEY = "CERTIFICATE_SERIALNUMBER";
  public static long   CERTIFICATE_SERIALNUMBER_DEFAULT = (new Date().getTime()/(1000*60*60)) * 10L;  // date, no time, 0 trail
  
  public static long getLastCertificateSerialNum()
  {
     Preferences prefs = NbPreferences.forModule(X3dOptions.class);
     return prefs.getLong(CERTIFICATE_SERIALNUMBER_KEY, CERTIFICATE_SERIALNUMBER_DEFAULT);
  }
  
  public static void setLastCertificateSerialNum(long val)
  {
     Preferences prefs = NbPreferences.forModule(X3dOptions.class);
     prefs.putLong(CERTIFICATE_SERIALNUMBER_KEY, val);
  }   
  // Cad Filter preferences
  public static String CADFILTER_KEY_VERSION              = "CADFILTER_KEY_VERSION";
  public static String CADFILTER_KEY_TRIANGLE_COUNT       = "CADFILTER_KEY_TRIANGLE_COUNT";
  public static String CADFILTER_KEY_LOG_LEVEL            = "CADFILTER_KEY_LOG_LEVEL";
  public static String CADFILTER_KEY_EMBED_PROTO          = "CADFILTER_KEY_EMBED_PROTO";
  public static String CADFILTER_KEY_BIN_COMPRESS         = "CADFILTER_KEY_BIN_COMPRESS";
  public static String CADFILTER_KEY_MIN_PROFILE          = "CADFILTER_KEY_MIN_PROFILE";
  public static String CADFILTER_KEY_APPEARANCE_FILTER    = "CADFILTER_KEY_APPEARANCE_FILTER";
  public static String CADFILTER_KEY_IDENTITY_FILTER      = "CADFILTER_KEY_IDENTITY_FILTER";
  public static String CADFILTER_KEY_CAD_FILTERS          = "CADFILTER_KEY_CAD_FILTERS";
  public static String CADFILTER_KEY_ABS_SCALE_FACTOR     = "CADFILTER_KEY_ABS_SCALE_FACTOR";
  public static String CADFILTER_KEY_BOUNDING_BOXES       = "CADFILTER_KEY_BOUNDING_BOXES";
  public static String CADFILTER_KEY_IFACE_TO_ITRIANGLE   = "CADFILTER_KEY_IFACE_TO_ITRIANGLE";
  public static String CADFILTER_KEY_FLOAT_QUANT          = "CADFILTER_KEY_FLOAT_QUANT";
  public static String CADFILTER_KEY_CENTER               = "CADFILTER_KEY_CENTER";
  public static String CADFILTER_KEY_IFACE_TO_TRIANGLE    = "CADFILTER_KEY_IFACE_TO_TRIANGLE";
  public static String CADFILTER_KEY_COMBINE_APPEARANCES  = "CADFILTER_KEY_COMBINE_APPEARANCES";
  public static String CADFILTER_KEY_COMBINE_SHAPES       = "CADFILTER_KEY_COMBINE_SHAPES";
  public static String CADFILTER_KEY_INDEX                = "CADFILTER_KEY_INDEX";
  public static String CADFILTER_KEY_DEFUSE_IMAGE_TEXTURE = "CADFILTER_KEY_DEFUSE_IMAGE_TEXTURE";
  public static String CADFILTER_KEY_MATERIAL             = "CADFILTER_KEY_MATERIAL";
  public static String CADFILTER_KEY_MOD_VIEWPOINT        = "CADFILTER_KEY_MOD_VIEWPOINT";
  public static String CADFILTER_KEY_FLATTEN_TRANSFORM    = "CADFILTER_KEY_FLATTEN_TRANSFORM";
  public static String CADFILTER_KEY_FLATTEN_TEXTURETRANSFORM= "CADFILTER_KEY_FLATTEN_TEXTURETRANSFORM";
  public static String CADFILTER_KEY_FLATTEN_SELECTABLE   = "CADFILTER_KEY_FLATTEN_SELECTABLE";
  public static String CADFILTER_KEY_SHORTEN_DEF          = "CADFILTER_KEY_SHORTEN_DEF";
  public static String CADFILTER_KEY_GEN_NORMALS          = "CADFILTER_KEY_GEN_NORMALS";
  public static String CADFILTER_KEY_TRIANGULATION        = "CADFILTER_KEY_TRIANGULATION";
  public static String CADFILTER_KEY_REINDEX              = "CADFILTER_KEY_REINDEX";
  public static String CADFILTER_KEY_DEBUG                = "CADFILTER_KEY_DEBUG";
  
  public static String  CADFILTER_DEFAULT_VERSION        = "3.2";
  public static boolean CADFILTER_DEFAULT_TRIANGLE_COUNT = false;
  public static String  CADFILTER_DEFAULT_LOG_LEVEL      = "ALL";
  public static boolean CADFILTER_DEFAULT_EMBED_PROTO    = false;
  public static String  CADFILTER_DEFAULT_BIN_COMPRESS   = "SMALLEST";
  public static boolean CADFILTER_DEFAULT_MIN_PROFILE    = false;
  
  public static boolean CADFILTER_DEFAULT_IDENTITY_FILTER = true;
  public static boolean CADFILTER_DEFAULT_CAD_FILTERS     = false;
  
  public static String  CADFILTER_DEFAULT_ABS_SCALE_FACTOR     = "1.0";
  public static boolean CADFILTER_DEFAULT_APPEARANCE_FILTER    = false;
  public static boolean CADFILTER_DEFAULT_BOUNDING_BOXES       = false;
  public static boolean CADFILTER_DEFAULT_IFACE_TO_ITRIANGLE   = false;
  public static String  CADFILTER_DEFAULT_FLOAT_QUANT          ="0.001";
  public static boolean CADFILTER_DEFAULT_CENTER               = false;
  public static boolean CADFILTER_DEFAULT_IFACE_TO_TRIANGLE    = false;
  public static boolean CADFILTER_DEFAULT_MATERIAL             = false;
  public static boolean CADFILTER_DEFAULT_COMBINE_APPEARANCES  = false;
  public static boolean CADFILTER_DEFAULT_COMBINE_SHAPES       = false;
  public static boolean CADFILTER_DEFAULT_INDEX                = false;
  public static boolean CADFILTER_DEFAULT_DEFUSE_IMAGE_TEXTURE = false;
  public static boolean CADFILTER_DEFAULT_MOD_VIEWPOINT        = false;
  public static boolean CADFILTER_DEFAULT_FLATTEN_TRANSFORM    = false;
  public static boolean CADFILTER_DEFAULT_FLATTEN_TEXTURETRANSFORM= false;
  public static boolean CADFILTER_DEFAULT_FLATTEN_SELECTABLE   = false;
  public static boolean CADFILTER_DEFAULT_SHORTEN_DEF          = false;
  public static boolean CADFILTER_DEFAULT_GEN_NORMALS          = false;
  public static boolean CADFILTER_DEFAULT_TRIANGULATION        = false;
  
  public static boolean CADFILTER_DEFAULT_REINDEX = false;
  public static boolean CADFILTER_DEFAULT_DEBUG   = false;

  public static void setCadFilterX3dVersion(String s)                { commonSet(CADFILTER_KEY_VERSION,s);}
  public static void setCadFilterTriangleCount(boolean tf)           {commonBSet(CADFILTER_KEY_TRIANGLE_COUNT,tf);}
  public static void setCadFilterLogLevel(String s)                  { commonSet(CADFILTER_KEY_LOG_LEVEL,s);}
  public static void setCadFilterEmbedProto(boolean tf)              {commonBSet(CADFILTER_KEY_EMBED_PROTO,tf);}
  public static void setCadFilterBinaryCompression(String s)         { commonSet(CADFILTER_KEY_BIN_COMPRESS,s);}
  public static void setCadFilterMinimumProfile(boolean tf)          {commonBSet(CADFILTER_KEY_MIN_PROFILE,tf);}
  public static void setCadFilterAppearanceFilter(boolean tf)        {commonBSet(CADFILTER_KEY_APPEARANCE_FILTER,tf);}
  public static void setCadFilterIdentityFilter(boolean tf)          {commonBSet(CADFILTER_KEY_IDENTITY_FILTER,tf);}
  public static void setCadFilterCadFiltersRB(boolean tf)            {commonBSet(CADFILTER_KEY_CAD_FILTERS,tf);}
  public static void setCadFilterAbsScaleFactor(String s)            { commonSet(CADFILTER_KEY_ABS_SCALE_FACTOR,s);}
  public static void setCadFilterAddBoundingBoxes(boolean tf)        {commonBSet(CADFILTER_KEY_BOUNDING_BOXES,tf);}
  public static void setCadFilterIFStoITS(boolean tf)                {commonBSet(CADFILTER_KEY_IFACE_TO_ITRIANGLE,tf);}
  public static void setCadFilterFloatingPointQuantization(String s) { commonSet(CADFILTER_KEY_FLOAT_QUANT,s);}
  public static void setCadFilterCenterFilter(boolean tf)            {commonBSet(CADFILTER_KEY_CENTER,tf);}
  public static void setCadFilterIFStoTS(boolean tf)                 {commonBSet(CADFILTER_KEY_IFACE_TO_TRIANGLE,tf);}
  public static void setCadFilterCombineAppearances(boolean tf)      {commonBSet(CADFILTER_KEY_COMBINE_APPEARANCES,tf);}
  public static void setCadFilterCombineShapes(boolean tf)           {commonBSet(CADFILTER_KEY_COMBINE_SHAPES,tf);}
  public static void setCadFilterIndexFilter(boolean tf)             {commonBSet(CADFILTER_KEY_INDEX,tf);}
  public static void setCadFilterDefuseImageTexture(boolean tf)      {commonBSet(CADFILTER_KEY_DEFUSE_IMAGE_TEXTURE,tf);}
  public static void setCadFilterMaterialFilter(boolean tf)          {commonBSet(CADFILTER_KEY_MATERIAL,tf);}
  public static void setCadFilterModifyViewpoint(boolean tf)         {commonBSet(CADFILTER_KEY_MOD_VIEWPOINT,tf);}
  public static void setCadFilterFlattenTransforms(boolean tf)       {commonBSet(CADFILTER_KEY_FLATTEN_TRANSFORM,tf);}
  public static void setCadFilterFlattenTextureTransforms(boolean tf){commonBSet(CADFILTER_KEY_FLATTEN_TEXTURETRANSFORM,tf);}
  public static void setCadFilterFlattenSelectable(boolean tf)       {commonBSet(CADFILTER_KEY_FLATTEN_SELECTABLE,tf);}
  public static void setCadFilterDefNameShortened(boolean tf)        {commonBSet(CADFILTER_KEY_SHORTEN_DEF,tf);}
  public static void setCadFilterGenerateNormals(boolean tf)         {commonBSet(CADFILTER_KEY_GEN_NORMALS,tf);}
  public static void setCadFilterTriangulationFilter(boolean tf)     {commonBSet(CADFILTER_KEY_TRIANGULATION,tf);}
  public static void setCadFilterReindex(boolean tf)                 {commonBSet(CADFILTER_KEY_REINDEX,tf);}
  public static void setCadFilterDebug(boolean tf)                   {commonBSet(CADFILTER_KEY_DEBUG,tf);}
       
  public static String  getCadFilterX3dVersion()                {return  commonGet(CADFILTER_KEY_VERSION,             CADFILTER_DEFAULT_VERSION);}
  public static boolean getCadFilterTriangleCount()             {return commonBGet(CADFILTER_KEY_TRIANGLE_COUNT,      CADFILTER_DEFAULT_TRIANGLE_COUNT);}
  public static String  getCadFilterLogLevel()                  {return  commonGet(CADFILTER_KEY_LOG_LEVEL,           CADFILTER_DEFAULT_LOG_LEVEL);}
  public static boolean getCadFilterEmbedProto()                {return commonBGet(CADFILTER_KEY_EMBED_PROTO,         CADFILTER_DEFAULT_EMBED_PROTO);}
  public static String  getCadFilterBinaryCompressionMethod()   {return  commonGet(CADFILTER_KEY_BIN_COMPRESS,        CADFILTER_DEFAULT_BIN_COMPRESS);}
  public static boolean getCadFilterMinimumProfile()            {return commonBGet(CADFILTER_KEY_MIN_PROFILE,         CADFILTER_DEFAULT_MIN_PROFILE);}
  public static boolean getCadFilterAppearanceFilter()            {return commonBGet(CADFILTER_KEY_IDENTITY_FILTER,   CADFILTER_DEFAULT_APPEARANCE_FILTER);}
  public static boolean getCadFilterIdentityFilter()            {return commonBGet(CADFILTER_KEY_IDENTITY_FILTER,     CADFILTER_DEFAULT_IDENTITY_FILTER);}
  public static boolean getCadFiltersEnabledRadioButton()       {return commonBGet(CADFILTER_KEY_CAD_FILTERS,         CADFILTER_DEFAULT_CAD_FILTERS);}
  public static String  getCadFilterAbsScaleFactor()            {return  commonGet(CADFILTER_KEY_ABS_SCALE_FACTOR,    CADFILTER_DEFAULT_ABS_SCALE_FACTOR);}
  public static boolean getCadFilterAddBoundingBoxes()          {return commonBGet(CADFILTER_KEY_BOUNDING_BOXES,      CADFILTER_DEFAULT_BOUNDING_BOXES);}
  public static boolean getCadFilterIFStoITS()                  {return commonBGet(CADFILTER_KEY_IFACE_TO_ITRIANGLE,  CADFILTER_DEFAULT_IFACE_TO_ITRIANGLE);}
  public static String  getCadFilterFloatingPointQuantization() {return  commonGet(CADFILTER_KEY_FLOAT_QUANT,         CADFILTER_DEFAULT_FLOAT_QUANT);}
  public static boolean getCadFilterCenterFilter()              {return commonBGet(CADFILTER_KEY_CENTER,              CADFILTER_DEFAULT_CENTER);}
  public static boolean getCadFilterIFStoTS()                   {return commonBGet(CADFILTER_KEY_IFACE_TO_TRIANGLE,   CADFILTER_DEFAULT_IFACE_TO_TRIANGLE);}
  public static boolean getCadFilterCombineAppearances()             {return commonBGet(CADFILTER_KEY_COMBINE_SHAPES, CADFILTER_DEFAULT_COMBINE_APPEARANCES);}
  public static boolean getCadFilterCombineShapes()             {return commonBGet(CADFILTER_KEY_COMBINE_SHAPES,      CADFILTER_DEFAULT_COMBINE_SHAPES);}
  public static boolean getCadFilterIndexFilter()               {return commonBGet(CADFILTER_KEY_INDEX,               CADFILTER_DEFAULT_INDEX);}
  public static boolean getCadFilterDefuseImageTexture()        {return commonBGet(CADFILTER_KEY_DEFUSE_IMAGE_TEXTURE,CADFILTER_DEFAULT_DEFUSE_IMAGE_TEXTURE);}
  public static boolean getCadFilterMaterialFilter()            {return commonBGet(CADFILTER_KEY_MOD_VIEWPOINT,       CADFILTER_DEFAULT_MATERIAL);}
  public static boolean getCadFilterModifyViewpoint()           {return commonBGet(CADFILTER_KEY_MOD_VIEWPOINT,       CADFILTER_DEFAULT_MOD_VIEWPOINT);}
  public static boolean getCadFilterFlattenTransforms()         {return commonBGet(CADFILTER_KEY_FLATTEN_TRANSFORM,   CADFILTER_DEFAULT_FLATTEN_TRANSFORM);}
  public static boolean getCadFilterFlattenTextureTransforms()  {return commonBGet(CADFILTER_KEY_FLATTEN_TEXTURETRANSFORM,CADFILTER_DEFAULT_FLATTEN_TEXTURETRANSFORM);}
  public static boolean getCadFilterFlattenSelectable()         {return commonBGet(CADFILTER_KEY_FLATTEN_SELECTABLE,  CADFILTER_DEFAULT_FLATTEN_SELECTABLE);}
  public static boolean getCadFilterDefNameShortened()          {return commonBGet(CADFILTER_KEY_SHORTEN_DEF,         CADFILTER_DEFAULT_SHORTEN_DEF);}
  public static boolean getCadFilterGenerateNormals()           {return commonBGet(CADFILTER_KEY_GEN_NORMALS,         CADFILTER_DEFAULT_GEN_NORMALS);}
  public static boolean getCadFilterTriangulationFilter()       {return commonBGet(CADFILTER_KEY_TRIANGULATION,       CADFILTER_DEFAULT_TRIANGULATION);}
  public static boolean getCadFilterReIndex()                   {return commonBGet(CADFILTER_KEY_REINDEX,             CADFILTER_DEFAULT_REINDEX);}
  public static boolean getCadFilterDebug()                     {return commonBGet(CADFILTER_KEY_DEBUG,               CADFILTER_DEFAULT_DEBUG);}
  
  public static void resetAllCadFilterPreferences()
  {
    commonReset(CADFILTER_KEY_VERSION);
    commonReset(CADFILTER_KEY_TRIANGLE_COUNT);
    commonReset(CADFILTER_KEY_LOG_LEVEL);
    commonReset(CADFILTER_KEY_EMBED_PROTO);
    commonReset(CADFILTER_KEY_BIN_COMPRESS);
    commonReset(CADFILTER_KEY_MIN_PROFILE);
    commonReset(CADFILTER_KEY_APPEARANCE_FILTER);
    commonReset(CADFILTER_KEY_IDENTITY_FILTER);
    commonReset(CADFILTER_KEY_CAD_FILTERS);
    commonReset(CADFILTER_KEY_ABS_SCALE_FACTOR);
    commonReset(CADFILTER_KEY_BOUNDING_BOXES);
    commonReset(CADFILTER_KEY_IFACE_TO_ITRIANGLE);
    commonReset(CADFILTER_KEY_FLOAT_QUANT);
    commonReset(CADFILTER_KEY_CENTER);
    commonReset(CADFILTER_KEY_IFACE_TO_TRIANGLE);
    commonReset(CADFILTER_KEY_MATERIAL);
    commonReset(CADFILTER_KEY_COMBINE_APPEARANCES);
    commonReset(CADFILTER_KEY_COMBINE_SHAPES);
    commonReset(CADFILTER_KEY_INDEX);
    commonReset(CADFILTER_KEY_DEFUSE_IMAGE_TEXTURE);
    commonReset(CADFILTER_KEY_MOD_VIEWPOINT);
    commonReset(CADFILTER_KEY_FLATTEN_TRANSFORM);
    commonReset(CADFILTER_KEY_FLATTEN_TEXTURETRANSFORM);
    commonReset(CADFILTER_KEY_FLATTEN_SELECTABLE);
    commonReset(CADFILTER_KEY_SHORTEN_DEF);
    commonReset(CADFILTER_KEY_GEN_NORMALS);
    commonReset(CADFILTER_KEY_TRIANGULATION);
    commonReset(CADFILTER_KEY_REINDEX);
    commonReset(CADFILTER_KEY_DEBUG);
  }
  // DIS preferences
  public static String DIS_ADDRESS_KEY  = "DIS_ADDRESS";
  public static String DIS_PORT_KEY     = "DIS_PORT";
  public static String DIS_APPID_KEY    = "DIS_APPID";
  public static String DIS_ENTITYID_KEY = "DIS_ENTITYID";
  public static String DIS_SITEID_KEY   = "DIS_SITEID";
  public static String DIS_SCALEX_KEY   = "DIS_SCALEX";
  public static String DIS_SCALEY_KEY   = "DIS_SCALEY";
  public static String DIS_SCALEZ_KEY   = "DIS_SCALEZ";
  
  public static String getDISaddress(String dflt)  {return commonGet(DIS_ADDRESS_KEY, dflt);}
  public static String getDISport(String dflt)     {return commonGet(DIS_PORT_KEY,    dflt);}
  public static String getDISappID(String dflt)    {return commonGet(DIS_APPID_KEY,   dflt);}
  public static String getDISentityID(String dflt) {return commonGet(DIS_ENTITYID_KEY,dflt);}
  public static String getDISsiteID(String dflt)   {return commonGet(DIS_SITEID_KEY,  dflt);}
  public static String getDIStranslationScaleX(String dflt)   {return commonGet(DIS_SCALEX_KEY, dflt);}
  public static String getDIStranslationScaleY(String dflt)   {return commonGet(DIS_SCALEY_KEY, dflt);}
  public static String getDIStranslationScaleZ(String dflt)   {return commonGet(DIS_SCALEZ_KEY, dflt);}
  
  public static void setDISaddress(String val)  {commonSet(DIS_ADDRESS_KEY, val);}
  public static void setDISport(String val)     {commonSet(DIS_PORT_KEY,    val);}
  public static void setDISappID(String val)    {commonSet(DIS_APPID_KEY,   val);}
  public static void setDISentityID(String val) {commonSet(DIS_ENTITYID_KEY,val);}
  public static void setDISsiteID(String val)   {commonSet(DIS_SITEID_KEY,  val);}
  public static void setDIStranslationScaleX(String val) {commonSet(DIS_SCALEX_KEY, val);}
  public static void setDIStranslationScaleY(String val) {commonSet(DIS_SCALEY_KEY, val);}
  public static void setDIStranslationScaleZ(String val) {commonSet(DIS_SCALEZ_KEY, val);}
}
