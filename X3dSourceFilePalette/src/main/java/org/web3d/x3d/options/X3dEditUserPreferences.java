/*
Copyright (c) 1995-2024 held by the author(s).  All rights reserved.
 
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

//import java.io.File;
import java.io.File;
import java.util.Date;
import java.util.prefs.Preferences;
import org.openide.util.NbPreferences;
//import org.web3d.x3d.actions.security.BouncyCastleHelper;

/**
 * X3dEditUserPreferences.java
 * Created on Apr 17, 2008
 * 
 * Note X3D-Edit options persistence: 
 * www.web3d.org/x3d/tools/X3dEdit4.0/X3dEditModuleSuite/build/testuserdir/config/Preferences/org/web3d/x3d/palette.properties
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey jmbailey@nps.edu
 * @version $Id$
 */
// @SuppressWarnings("StaticNonFinalUsedInInitialization")
public class X3dEditUserPreferences
{
  public static final String AUTHOR_NAME_TOKEN = "__AUTHOR-NAME__";
  public static       String X3D_EDIT_PATH  = System.getProperty("user.dir"); // _path_/X3DEdit4.0/X3dEditModuleSuite

  static {
//    String homeDir = System.getProperty("user.home");
//       USER_NAME             = System.getProperty("user.name"); // add key, save value
//       USER_HOME_DIR         = System.getProperty("user.dir");  // add key, save value
//       AUTHOR_NAME_DEFAULT     = System.getProperty("user.name");
//       USER_HOME_DIR_DEFAULT = System.getProperty("user.dir");
//       X3D_EDIT_PATH         = System.getProperty("user.dir"); // _path_/X3DEdit4.0/X3dEditModuleSuite
    
//    File dir = new File(new StringBuilder().append(homeDir).append("/X3D-Edit/XML &Security").toString());
//    File fil = new File(dir,KEYSTORE_FILENAME_DEFAULT);
//    KEYSTORE_PATH_DEFAULT = fil.getAbsolutePath();
  }

  /** Constructor not needed while all properties and methods are static */
//  X3dEditUserPreferences()
//  {
////      System.out.println("*** X3dEditUserPreferences ");
//  }
  
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
  
  private static final String AUTHOR_NAME_KEY                       = "AUTHOR_NAME";
  private static final String AUTHOR_EMAIL_KEY                      = "AUTHOR_EMAIL";
  private static final String EXAMPLES_ROOT_DIRECTORY_KEY           = "EXAMPLES_ROOT_DIRECTORY";
  
  private static final String AUTHOR_PREFERENCE_CORS_DIRECTORY_KEY  = "AUTHOR_PREFERENCE_CORS_DIRECTORY_KEY";
  private static final String AUTHOR_PREFERENCE_HTML_WIDTH_KEY      = "AUTHOR_PREFERENCE_HTML_WIDTH_KEY";
  private static final String AUTHOR_PREFERENCE_HTML_HEIGHT_KEY     = "AUTHOR_PREFERENCE_HTML_HEIGHT_KEY";
  
  private static final String AUTHOR_MODELS_DIRECTORY_KEY           = "AUTHOR_MODELS_DIRECTORY_KEY";
  private static final String AUTHOR_MODELS_SERVER_AUTOLAUNCH_KEY   = "AUTHOR_MODELS_SERVER_AUTOLAUNCH_KEY";
  private static final String EXAMPLE_ARCHIVES_SERVER_AUTOLAUNCH_KEY= "EXAMPLE_ARCHIVES_AUTOLAUNCH_KEY";
  private static final String ACTIVE_X3D_MODEL_SERVER_AUTOLAUNCH_KEY= "ACTIVE_X3D_MODEL_SERVER_AUTOLAUNCH_KEY";
  private static final String AUTHOR_MODELS_SERVER_PORT_KEY         = "AUTHOR_MODELS_SERVER_PORT_KEY";
  private static final String EXAMPLE_ARCHIVES_SERVER_PORT_KEY      = "EXAMPLE_ARCHIVES_SERVER_PORT_KEY";
  private static final String NEW_X3D_MODELS_DIRECTORY_KEY          = "NEW_X3D_MODELS_DIRECTORY_KEY";
  
  private static final String             BASIC_LOCALEXAMPLES_PRESENT_KEY =             "BASIC_LOCALEXAMPLES_PRESENT_KEY";
  private static final String   CONFORMANCENIST_LOCALEXAMPLES_PRESENT_KEY =   "CONFORMANCENIST_LOCALEXAMPLES_PRESENT_KEY";
  private static final String HUMANOIDANIMATION_LOCALEXAMPLES_PRESENT_KEY = "HUMANOIDANIMATION_LOCALEXAMPLES_PRESENT_KEY";
  private static final String    VRMLSOURCEBOOK_LOCALEXAMPLES_PRESENT_KEY =    "VRMLSOURCEBOOK_LOCALEXAMPLES_PRESENT_KEY";
  private static final String            SAVAGE_LOCALEXAMPLES_PRESENT_KEY =            "SAVAGE_LOCALEXAMPLES_PRESENT_KEY";
  private static final String     SAVAGEDEFENSE_LOCALEXAMPLES_PRESENT_KEY =     "SAVAGEDEFENSE_LOCALEXAMPLES_PRESENT_KEY";
  private static final String            X3D4WA_LOCALEXAMPLES_PRESENT_KEY =            "X3D4WA_LOCALEXAMPLES_PRESENT_KEY";
  private static final String            X3D4AM_LOCALEXAMPLES_PRESENT_KEY =            "X3D4AM_LOCALEXAMPLES_PRESENT_KEY";

  private static final String KEYSTORE_PASSWORD_KEY                 = "KEYSTORE_PASSWORD_KEY";
  private static final String KEYSTORE_FILENAME_KEY                 = "KEYSTORE_FILENAME_KEY";
  private static final String KEYSTORE_DIRECTORY_KEY                = "KEYSTORE_DIRECTORY_KEY";
  private static final String KEYSTORE_PATH_KEY                     = "KEYSTORE_PATH"; // path is directory+filename combination
  
  // default preference, can be overridden by user choices in X3D-Edit Preferences Panels (Tools > Options > Miscellaneous > X3D > Other Preferences panel)
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
  
  public static String  AUTHOR_NAME_DEFAULT             = System.getProperty("user.name");
  public static String  AUTHOR_EMAIL_DEFAULT            = "";
  // https://stackoverflow.com/questions/585534/what-is-the-best-way-to-find-the-users-home-directory-in-java
  // TODO confirm if /Desktop is a good location on Linux?
  public static String  NEW_X3D_MODELS_DIRECTORY_DEFAULT           = System.getProperty("user.home") + File.separatorChar + "Desktop" + File.separatorChar + "NewX3dModels";
  public static String  AUTHOR_MODELS_DIRECTORY_DEFAULT            = System.getProperty("user.home") + File.separatorChar + "Desktop" + File.separatorChar + "NewX3dModels"; // user.dir is local X3D-Edit execution directory
  public static String  EXAMPLES_ROOT_DIRECTORY_DEFAULT            = System.getProperty("user.home") + File.separatorChar + "Desktop" + File.separatorChar + "NewX3dModels" +
                                                                     File.separatorChar + "www.web3d.org" + File.separatorChar + "x3d" + File.separatorChar + "content" + File.separatorChar + "examples";
  
  public static String  AUTHOR_PREFERENCE_CORS_DIRECTORY_DEFAULT   =  "";
  public static String  AUTHOR_PREFERENCE_HTML_WIDTH_DEFAULT       =  "450";
  public static String  AUTHOR_PREFERENCE_HTML_HEIGHT_DEFAULT      =  "800";
  
  public static boolean AUTHOR_MODELS_SERVER_AUTOLAUNCH_DEFAULT    = false; // TODO Ask author prior to setting true
  public static boolean EXAMPLE_ARCHIVES_SERVER_AUTOLAUNCH_DEFAULT = false; // TODO Ask author prior to setting true
  public static boolean AUTHOR_X3D_MODEL_SERVER_AUTOLAUNCH_DEFAULT = false; // TODO wait until functionality implemented

  public static String  AUTHOR_MODELS_SERVER_PORT_DEFAULT          = "8001";
  public static String  EXAMPLE_ARCHIVES_SERVER_PORT_DEFAULT       = "8002";
  
  public static String  NIST_NVD_SECURITY_CHECK_URL                = "https://nvd.nist.gov/vuln/search";
  public static String  NIST_NVD_SEARCH_PREFIX                     = "/results?form_type=Basic&results_type=overview&search_type=all&query=";
  
  // there is no unique best default path as a user could store examples anywhere on their local machine
  // thus user.dir property persistence will allow a path to be remembered
  public  static final String KEYSTORE_FILENAME_DEFAULT = "X3D-EditKeystore.ks"; // TODO final?
//private static final String KEYSTORE_FILENAME_DEFAULT = new StringBuilder().append("X3D-EditKeystore.").append(BouncyCastleHelper.getKeystoreNameExtension()).toString();

  public static String  KEYSTORE_PASSWORD_DEFAULT       = "test";
  public static String  KEYSTORE_DIRECTORY_DEFAULT      = System.getProperty("user.dir") + File.separatorChar; // otherwise user can define a local keystore path
  // https://stackoverflow.com/questions/5971964/when-should-i-use-file-separator-and-when-file-pathseparator
  public static String  KEYSTORE_PATH_DEFAULT           = System.getProperty("user.dir") + File.separator + KEYSTORE_FILENAME_DEFAULT;

  public static void    setShowNewlineOption       (boolean tf)    {commonBooleanSet(SHOW_NEWLINE_OPTION_KEY,tf);}
  public static void    setPrependNewline          (boolean tf)    {commonBooleanSet(    PREPEND_NEWLINE_KEY,tf);}
  public static void    setAppendNewline           (boolean tf)    {commonBooleanSet(     APPEND_NEWLINE_KEY,tf);}
  public static void    setAutoValidate            (boolean tf)    {commonBooleanSet(       AUTOVALIDATE_KEY,tf);}
  public static void    setVisualizeCoordinateAxes (boolean tf)    {commonBooleanSet(VISUALIZE_COORDINATE_AXES_KEY,tf);}
  public static void    setVisualizeCenterLine     (boolean tf)    {commonBooleanSet(VISUALIZE_CENTER_LINE_KEY,tf);}
  public static void    setVisualizeConeLines      (String count)  { commonStringSet(VISUALIZE_CONE_LINES_KEY,count);}
  public static void    setVisualizeLineColorRed   (String color)  { commonStringSet(VISUALIZE_LINECOLOR_RED_KEY,color);}
  public static void    setVisualizeLineColorGreen (String color)  { commonStringSet(VISUALIZE_LINECOLOR_GREEN_KEY,color);}
  public static void    setVisualizeLineColorBlue  (String color)  { commonStringSet(VISUALIZE_LINECOLOR_BLUE_KEY,color);}
  public static void    setVisualizeShapeColorRed  (String color)  { commonStringSet(VISUALIZE_SHAPECOLOR_RED_KEY,color);}
  public static void    setVisualizeShapeColorGreen(String color)  { commonStringSet(VISUALIZE_SHAPECOLOR_GREEN_KEY,color);}
  public static void    setVisualizeShapeColorBlue (String color)  { commonStringSet(VISUALIZE_SHAPECOLOR_BLUE_KEY,color);}
  public static void    setVisualizeTransparency   (String transparency) {commonStringSet(VISUALIZE_TRANSPARENCY_KEY,transparency);}
  
  public static void    setVisualizeHanimCoordinateAxes    (boolean tf)    {commonBooleanSet(VISUALIZE_HANIM_COORDINATE_AXES_KEY,tf);}
  public static void    setVisualizeHanimJointColorRed     (String color)  { commonStringSet(VISUALIZE_HANIMJOINTCOLOR_RED_KEY,color);}
  public static void    setVisualizeHanimJointColorGreen   (String color)  { commonStringSet(VISUALIZE_HANIMJOINTCOLOR_GREEN_KEY,color);}
  public static void    setVisualizeHanimJointColorBlue    (String color)  { commonStringSet(VISUALIZE_HANIMJOINTCOLOR_BLUE_KEY,color);}
  public static void    setVisualizeHanimSegmentColorRed   (String color)  { commonStringSet(VISUALIZE_HANIMSEGMENTCOLOR_RED_KEY,color);}
  public static void    setVisualizeHanimSegmentColorGreen (String color)  { commonStringSet(VISUALIZE_HANIMSEGMENTCOLOR_GREEN_KEY,color);}
  public static void    setVisualizeHanimSegmentColorBlue  (String color)  { commonStringSet(VISUALIZE_HANIMSEGMENTCOLOR_BLUE_KEY,color);}
  public static void    setVisualizeHanimSiteColorRed      (String color)  { commonStringSet(VISUALIZE_HANIMSITECOLOR_RED_KEY,color);}
  public static void    setVisualizeHanimSiteColorGreen    (String color)  { commonStringSet(VISUALIZE_HANIMSITECOLOR_GREEN_KEY,color);}
  public static void    setVisualizeHanimSiteColorBlue     (String color)  { commonStringSet(VISUALIZE_HANIMSITECOLOR_BLUE_KEY,color);}

  public static void    setAuthorName                      (String value)  { commonStringSet(AUTHOR_NAME_KEY, value);}
  public static void    setAuthorEmail                     (String value)  { commonStringSet(AUTHOR_EMAIL_KEY, value);}
  
  public static void    setAuthorPreferenceCorsDirectory   (String value)  { commonStringSet(AUTHOR_PREFERENCE_CORS_DIRECTORY_KEY, value);}
  public static void    setAuthorPreferenceHtmlWidth       (String value)  { commonStringSet(AUTHOR_PREFERENCE_HTML_WIDTH_KEY, value);}
  public static void    setAuthorPreferenceHtmlHeight      (String value)  { commonStringSet(AUTHOR_PREFERENCE_HTML_HEIGHT_KEY, value);}
  
  public static void    setExampleArchivesRootDirectory    (String value)  { 
      if (value == null)
          return;
      if      (value.endsWith("\\") || value.endsWith("/"))
               value = value.substring(0, value.length() - 1);
      
      // do not shorten ExamplesRootDirectory address since it is source directory for localhost http cors server
//    if      (value.endsWith("www.web3d.org\\x3d\\content\\examples"))
//             value = value.substring(0, value.lastIndexOf("www.web3d.org\\x3d\\content\\examples"));
//    else if (value.endsWith("www.web3d.org/x3d/content/examples"))
//             value = value.substring(0, value.lastIndexOf("www.web3d.org/x3d/content/examples"));

      commonStringSet(EXAMPLES_ROOT_DIRECTORY_KEY, value);}

  public static void    setNewX3dModelsDirectory           (String value)  {  commonStringSet(NEW_X3D_MODELS_DIRECTORY_KEY, value);}
  public static void    setAuthorModelsDirectory           (String value)  {  commonStringSet(AUTHOR_MODELS_DIRECTORY_KEY, value);}
  public static void    setAuthorModelsServerAutolaunch    (boolean value) { commonBooleanSet(AUTHOR_MODELS_SERVER_AUTOLAUNCH_KEY, value);}
  public static void    setExampleArchivesServerAutolaunch (boolean value) { commonBooleanSet(EXAMPLE_ARCHIVES_SERVER_AUTOLAUNCH_KEY, value);}
  public static void    setActiveX3dModelServerAutolaunch  (boolean value) { commonBooleanSet(ACTIVE_X3D_MODEL_SERVER_AUTOLAUNCH_KEY, value);}
  public static void    setAuthorModelsServerPort          (String value)  {  commonStringSet(AUTHOR_MODELS_SERVER_PORT_KEY, value);}
  public static void    setExampleArchivesServerPort       (String value)  {  commonStringSet(EXAMPLE_ARCHIVES_SERVER_PORT_KEY, value);}
  
  public static void    resetUserOptions ()
  {
                        setAuthorName (AUTHOR_NAME_DEFAULT);
                       setAuthorEmail (AUTHOR_EMAIL_DEFAULT);
      setExampleArchivesRootDirectory (EXAMPLES_ROOT_DIRECTORY_DEFAULT);
     setAuthorPreferenceCorsDirectory (AUTHOR_PREFERENCE_CORS_DIRECTORY_DEFAULT);
     
      if (getAuthorName().equals("brutzman") && getAuthorEmail().isBlank())
      {
          setAuthorName ("Don Brutzman");
          setAuthorEmail("brutzman@nps.edu");
      }
  }
  public static void             setBasicLocalExamplesPresent (boolean value)  { commonBooleanSet(            BASIC_LOCALEXAMPLES_PRESENT_KEY, value);}
  public static void   setConformanceNistLocalExamplesPresent (boolean value)  { commonBooleanSet(  CONFORMANCENIST_LOCALEXAMPLES_PRESENT_KEY, value);}
  public static void setHumanoidAnimationLocalExamplesPresent (boolean value)  { commonBooleanSet(HUMANOIDANIMATION_LOCALEXAMPLES_PRESENT_KEY, value);}
  public static void    setVrmlSourcebookLocalExamplesPresent (boolean value)  { commonBooleanSet(   VRMLSOURCEBOOK_LOCALEXAMPLES_PRESENT_KEY, value);}
  public static void            setSavageLocalExamplesPresent (boolean value)  { commonBooleanSet(           SAVAGE_LOCALEXAMPLES_PRESENT_KEY, value);}
  public static void     setSavageDefenseLocalExamplesPresent (boolean value)  { commonBooleanSet(    SAVAGEDEFENSE_LOCALEXAMPLES_PRESENT_KEY, value);}
  public static void            setX3d4waLocalExamplesPresent (boolean value)  { commonBooleanSet(           X3D4WA_LOCALEXAMPLES_PRESENT_KEY, value);}
  public static void            setX3d4amLocalExamplesPresent (boolean value)  { commonBooleanSet(           X3D4AM_LOCALEXAMPLES_PRESENT_KEY, value);}
  
  public static void    setKeystorePassword                (String value)  { commonStringSet(KEYSTORE_PASSWORD_KEY, value);}
  public static void    setKeystoreFileName                (String value)  
  { 
      commonStringSet(KEYSTORE_FILENAME_KEY, value);
      setKeystorePath(getKeystoreDirectory() + '/' + getKeystoreFileName());
  }
  public static void    setKeystoreDirectory               (String value)  
  { 
      commonStringSet(KEYSTORE_DIRECTORY_KEY, value);
  }
  public static void    setKeystorePath                (String value)  { commonStringSet(KEYSTORE_PATH_KEY, value);}
  public static void    resetSecurityPassword  ()
  {
      setKeystorePassword (KEYSTORE_PASSWORD_DEFAULT);
  }
  public static void    resetKeystoreDirectory  ()
  {
     setKeystoreDirectory (KEYSTORE_DIRECTORY_DEFAULT);
  }
  
  public static String  getAuthorName ()                            { return  commonStringGet(AUTHOR_NAME_KEY,    AUTHOR_NAME_DEFAULT);}
  public static String  getAuthorEmail ()                           { return  commonStringGet(AUTHOR_EMAIL_KEY,   AUTHOR_EMAIL_DEFAULT);}
  public static String  getExampleArchivesRootDirectory ()          { return  commonStringGet(EXAMPLES_ROOT_DIRECTORY_KEY,   EXAMPLES_ROOT_DIRECTORY_DEFAULT);}
  
  public static String  getAuthorPreferenceCorsDirectory ()         { return  commonStringGet(AUTHOR_PREFERENCE_CORS_DIRECTORY_KEY,      AUTHOR_PREFERENCE_CORS_DIRECTORY_DEFAULT);}
  public static String  getAuthorPreferenceHtmlWidth ()             { return  commonStringGet(AUTHOR_PREFERENCE_HTML_WIDTH_KEY,          AUTHOR_PREFERENCE_HTML_WIDTH_DEFAULT);}
  public static String  getAuthorPreferenceHtmlHeight ()            { return  commonStringGet(AUTHOR_PREFERENCE_HTML_HEIGHT_KEY,         AUTHOR_PREFERENCE_HTML_HEIGHT_DEFAULT);}
  
  public static String  getNewX3dModelsDirectory ()                 { return  commonStringGet(NEW_X3D_MODELS_DIRECTORY_KEY,              NEW_X3D_MODELS_DIRECTORY_DEFAULT);}
  public static String  getAuthorModelsDirectory ()                 { return  commonStringGet(AUTHOR_MODELS_DIRECTORY_KEY,               AUTHOR_MODELS_DIRECTORY_DEFAULT);}
  public static boolean isAuthorModelsServerAutolaunch ()           { return commonBooleanGet(AUTHOR_MODELS_SERVER_AUTOLAUNCH_KEY,       AUTHOR_MODELS_SERVER_AUTOLAUNCH_DEFAULT);}
  public static boolean isExampleArchivesServerAutolaunch ()        { return commonBooleanGet(EXAMPLE_ARCHIVES_SERVER_AUTOLAUNCH_KEY,    EXAMPLE_ARCHIVES_SERVER_AUTOLAUNCH_DEFAULT);}
  public static boolean isActiveX3dModelServerAutolaunch ()         { return commonBooleanGet(ACTIVE_X3D_MODEL_SERVER_AUTOLAUNCH_KEY,    AUTHOR_X3D_MODEL_SERVER_AUTOLAUNCH_DEFAULT);}
  public static String  getAuthorModelsServerPort ()                { return  commonStringGet(AUTHOR_MODELS_SERVER_PORT_KEY,             AUTHOR_MODELS_SERVER_PORT_DEFAULT);}
  public static String  getExampleArchivesServerPort ()             { return  commonStringGet(EXAMPLE_ARCHIVES_SERVER_PORT_KEY,          EXAMPLE_ARCHIVES_SERVER_PORT_DEFAULT);}
  
  public static boolean             getBasicLocalExamplesPresent () { return commonBooleanGet(            BASIC_LOCALEXAMPLES_PRESENT_KEY, false);}
  public static boolean   getConformanceNistLocalExamplesPresent () { return commonBooleanGet(  CONFORMANCENIST_LOCALEXAMPLES_PRESENT_KEY, false);}
  public static boolean getHumanoidAnimationLocalExamplesPresent () { return commonBooleanGet(HUMANOIDANIMATION_LOCALEXAMPLES_PRESENT_KEY, false);}
  public static boolean    getVrmlSourcebookLocalExamplesPresent () { return commonBooleanGet(   VRMLSOURCEBOOK_LOCALEXAMPLES_PRESENT_KEY, false);}
  public static boolean            getSavageLocalExamplesPresent () { return commonBooleanGet(           SAVAGE_LOCALEXAMPLES_PRESENT_KEY, false);}
  public static boolean     getSavageDefenseLocalExamplesPresent () { return commonBooleanGet(    SAVAGEDEFENSE_LOCALEXAMPLES_PRESENT_KEY, false);}
  public static boolean            getX3d4waLocalExamplesPresent () { return commonBooleanGet(           X3D4WA_LOCALEXAMPLES_PRESENT_KEY, false);}
  public static boolean            getX3d4amLocalExamplesPresent () { return commonBooleanGet(           X3D4AM_LOCALEXAMPLES_PRESENT_KEY, false);}
  
  public static String  getKeystorePassword ()                      { return  commonStringGet(KEYSTORE_PASSWORD_KEY,   KEYSTORE_PASSWORD_DEFAULT);}
  public static String  getKeystoreFileName ()                      { return  commonStringGet(KEYSTORE_FILENAME_KEY,   KEYSTORE_FILENAME_DEFAULT);}
  public static String  getKeystoreDirectory ()                     { return  commonStringGet(KEYSTORE_DIRECTORY_KEY,   KEYSTORE_DIRECTORY_DEFAULT);}
            
  public static String getKeystorePasswordDefault()                 { return KEYSTORE_PASSWORD_DEFAULT;}
  public static String getKeystoreFileNameDefault()                 { return KEYSTORE_FILENAME_DEFAULT;}
  public static String getKeystoreDirectoryDefault()                { return KEYSTORE_DIRECTORY_DEFAULT;}
  public static String getKeystorePathDefault()                     { return KEYSTORE_DIRECTORY_DEFAULT + "/" + KEYSTORE_FILENAME_DEFAULT;}
  
  public static boolean getShowNewlineOption()           {return commonBooleanGet(SHOW_NEWLINE_OPTION_KEY, SHOW_NEWLINE_OPTION_DEFAULT);}
  public static boolean getPrependNewline   ()           {return commonBooleanGet(    PREPEND_NEWLINE_KEY,     PREPEND_NEWLINE_DEFAULT);}
  public static boolean getAppendNewline    ()           {return commonBooleanGet(     APPEND_NEWLINE_KEY,      APPEND_NEWLINE_DEFAULT);}
  public static void    resetNewLineOptions ()
  {
      setShowNewlineOption (SHOW_NEWLINE_OPTION_DEFAULT);
         setPrependNewline (PREPEND_NEWLINE_DEFAULT);
          setAppendNewline (APPEND_NEWLINE_DEFAULT);
  }
  public static boolean getAutoValidate   ()             {return commonBooleanGet(     AUTOVALIDATE_KEY,         AUTOVALIDATE_DEFAULT);}
  public static boolean getVisualizeCoordinateAxes  ()   {return commonBooleanGet(VISUALIZE_COORDINATE_AXES_KEY, VISUALIZE_COORDINATE_AXES_DEFAULT);}
  public static boolean getVisualizeCenterLine  ()       {return commonBooleanGet(VISUALIZE_CENTER_LINE_KEY,     VISUALIZE_CENTER_LINE_DEFAULT);}
  public static String  getVisualizeConeLines  ()        {return commonStringGet (VISUALIZE_CONE_LINES_KEY,      VISUALIZE_CONE_LINES_DEFAULT);}
  public static String  getVisualizeLineColorRed  ()     {return commonStringGet (VISUALIZE_LINECOLOR_RED_KEY,   VISUALIZE_LINECOLOR_RED_DEFAULT);}
  public static String  getVisualizeLineColorGreen ()    {return commonStringGet (VISUALIZE_LINECOLOR_GREEN_KEY, VISUALIZE_LINECOLOR_GREEN_DEFAULT);}
  public static String  getVisualizeLineColorBlue ()     {return commonStringGet (VISUALIZE_LINECOLOR_BLUE_KEY,  VISUALIZE_LINECOLOR_BLUE_DEFAULT);}
  public static String  getVisualizeShapeColorRed  ()    {return commonStringGet (VISUALIZE_SHAPECOLOR_RED_KEY,  VISUALIZE_SHAPECOLOR_RED_DEFAULT);}
  public static String  getVisualizeShapeColorGreen ()   {return commonStringGet (VISUALIZE_SHAPECOLOR_GREEN_KEY,VISUALIZE_SHAPECOLOR_GREEN_DEFAULT);}
  public static String  getVisualizeShapeColorBlue ()    {return commonStringGet (VISUALIZE_SHAPECOLOR_BLUE_KEY, VISUALIZE_SHAPECOLOR_BLUE_DEFAULT);}
  public static String  getVisualizeTransparency ()      {return commonStringGet (VISUALIZE_TRANSPARENCY_KEY,  VISUALIZE_TRANSPARENCY_DEFAULT);}
  
  public static boolean getVisualizeHanimCoordinateAxes  ()    {return commonBooleanGet(VISUALIZE_HANIM_COORDINATE_AXES_KEY,   VISUALIZE_HANIM_COORDINATE_AXES_DEFAULT);}
  public static String  getVisualizeHanimJointColorRed  ()     {return commonStringGet (VISUALIZE_HANIMJOINTCOLOR_RED_KEY,     VISUALIZE_HANIMJOINTCOLOR_RED_DEFAULT);}
  public static String  getVisualizeHanimJointColorGreen ()    {return commonStringGet (VISUALIZE_HANIMJOINTCOLOR_GREEN_KEY,   VISUALIZE_HANIMJOINTCOLOR_GREEN_DEFAULT);}
  public static String  getVisualizeHanimJointColorBlue ()     {return commonStringGet (VISUALIZE_HANIMJOINTCOLOR_BLUE_KEY,    VISUALIZE_HANIMJOINTCOLOR_BLUE_DEFAULT);}
  public static String  getVisualizeHanimSegmentColorRed  ()   {return commonStringGet (VISUALIZE_HANIMSEGMENTCOLOR_RED_KEY,   VISUALIZE_HANIMSEGMENTCOLOR_RED_DEFAULT);}
  public static String  getVisualizeHanimSegmentColorGreen ()  {return commonStringGet (VISUALIZE_HANIMSEGMENTCOLOR_GREEN_KEY, VISUALIZE_HANIMSEGMENTCOLOR_GREEN_DEFAULT);}
  public static String  getVisualizeHanimSegmentColorBlue ()   {return commonStringGet (VISUALIZE_HANIMSEGMENTCOLOR_BLUE_KEY,  VISUALIZE_HANIMSEGMENTCOLOR_BLUE_DEFAULT);}
  public static String  getVisualizeHanimSiteColorRed  ()      {return commonStringGet (VISUALIZE_HANIMSITECOLOR_RED_KEY,      VISUALIZE_HANIMSITECOLOR_RED_DEFAULT);}
  public static String  getVisualizeHanimSiteColorGreen ()     {return commonStringGet (VISUALIZE_HANIMSITECOLOR_GREEN_KEY,    VISUALIZE_HANIMSITECOLOR_GREEN_DEFAULT);}
  public static String  getVisualizeHanimSiteColorBlue ()      {return commonStringGet (VISUALIZE_HANIMSITECOLOR_BLUE_KEY,     VISUALIZE_HANIMSITECOLOR_BLUE_DEFAULT);}
  
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
  public static String CASTLEMODELVIEWER_EXECUTABLE_PATH_KEY        = "VIEW3DSCENE_EXECUTABLE_PATH";
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
  public static String CASTLEMODELVIEWER_EXECUTABLE_AUTOLAUNCH_KEY        = "VIEW3DSCENE_AUTOLAUNCH";
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
  public static String            PROTEGE_EXECUTABLE_AUTOLAUNCH_KEY =            "PROTEGE_AUTOLAUNCH";
  public static String           PORTECLE_EXECUTABLE_AUTOLAUNCH_KEY =           "PORTECLE_AUTOLAUNCH";
  public static String   KEYSTOREEXPLORER_EXECUTABLE_AUTOLAUNCH_KEY =   "KEYSTOREEXPLORER_AUTOLAUNCH";
  public static String          WIRESHARK_EXECUTABLE_AUTOLAUNCH_KEY =          "WIRESHARK_AUTOLAUNCH";
  
  public static String      ALTOVA_XMLSPY_EXECUTABLE_AUTOLAUNCH_KEY =      "ALTOVA_XMLSPY_AUTOLAUNCH";
  public static String            BLENDER_EXECUTABLE_AUTOLAUNCH_KEY =            "BLENDER_AUTOLAUNCH";
  public static String    BSCONTENTSTUDIO_EXECUTABLE_AUTOLAUNCH_KEY =    "BSCONTENTSTUDIO_AUTOLAUNCH";
  public static String           BVHACKER_EXECUTABLE_AUTOLAUNCH_KEY =           "BVHACKER_AUTOLAUNCH";
  public static String               CURA_EXECUTABLE_AUTOLAUNCH_KEY =               "CURA_AUTOLAUNCH";
  public static String            MESHLAB_EXECUTABLE_AUTOLAUNCH_KEY =            "MESHLAB_AUTOLAUNCH";
  public static String           PARAVIEW_EXECUTABLE_AUTOLAUNCH_KEY =           "PARAVIEW_AUTOLAUNCH";
  public static String         MAYARAWKEE_EXECUTABLE_AUTOLAUNCH_KEY =         "MAYARAWKEE_AUTOLAUNCH";
  public static String            RHINO3D_EXECUTABLE_AUTOLAUNCH_KEY =            "RHINO3D_AUTOLAUNCH";
  public static String        POLYTRANSNUGRAF_EDITOR_AUTOLAUNCH_KEY =    "POLYTRANSNUGRAF_AUTOLAUNCH";
  public static String         SEAMLESS3D_EXECUTABLE_AUTOLAUNCH_KEY =         "SEAMLESS3D_AUTOLAUNCH";
  public static String            SUNRIZE_EXECUTABLE_AUTOLAUNCH_KEY =            "SUNRIZE_AUTOLAUNCH";
  public static String            ITKSNAP_EXECUTABLE_AUTOLAUNCH_KEY =            "ITKSNAP_AUTOLAUNCH";
  public static String              SEG3D_EXECUTABLE_AUTOLAUNCH_KEY =              "SEG3D_AUTOLAUNCH";
  public static String           SLICER3D_EXECUTABLE_AUTOLAUNCH_KEY =           "SLICER3D_AUTOLAUNCH";
  public static String              BATIK_EXECUTABLE_AUTOLAUNCH_KEY =              "BATIK_AUTOLAUNCH";
  public static String           INKSCAPE_EXECUTABLE_AUTOLAUNCH_KEY =           "INKSCAPE_AUTOLAUNCH";
  public static String           SVG_EDIT_EXECUTABLE_AUTOLAUNCH_KEY =           "SVG_EDIT_AUTOLAUNCH";
  public static String          WHITEDUNE_EXECUTABLE_AUTOLAUNCH_KEY =          "WHITEDUNE_AUTOLAUNCH";
  public static String            WINGS3D_EXECUTABLE_AUTOLAUNCH_KEY =            "WINGS3D_AUTOLAUNCH";
  public static String          ULTRAEDIT_EXECUTABLE_AUTOLAUNCH_KEY =          "ULTRAEDIT_AUTOLAUNCH";
  public static String OTHER_AUDIO_EDITOR_EXECUTABLE_AUTOLAUNCH_KEY = "OTHER_AUDIO_EDITOR_AUTOLAUNCH";
  public static String OTHER_HTML5_EDITOR_EXECUTABLE_AUTOLAUNCH_KEY = "OTHER_HTML5_EDITOR_AUTOLAUNCH";
  public static String OTHER_IMAGE_EDITOR_EXECUTABLE_AUTOLAUNCH_KEY = "OTHER_IMAGE_EDITOR_AUTOLAUNCH";
  public static String OTHER_VIDEO_EDITOR_EXECUTABLE_AUTOLAUNCH_KEY = "OTHER_VIDEO_EDITOR_AUTOLAUNCH";
  public static String OTHER_VOLUME_EDITOR_EXECUTABLE_AUTOLAUNCH_KEY="OTHER_VOLUME_EDITOR_AUTOLAUNCH";
  public static String OTHER_SEMANTICWEB_EDITOR_EXECUTABLE_AUTOLAUNCH_KEY="OTHER_SEMANTICWEB_EDITOR_AUTOLAUNCH";
  
  public static String OTHER_AUDIO_EDITOR_EXECUTABLE_NAME_KEY       = "OTHER_AUDIO_EDITOR_EXECUTABLE_NAME";
  public static String OTHER_HTML5_EDITOR_EXECUTABLE_NAME_KEY       = "OTHER_HTML5_EDITOR_EXECUTABLE_NAME";
  public static String OTHER_IMAGE_EDITOR_EXECUTABLE_NAME_KEY       = "OTHER_IMAGE_EDITOR_EXECUTABLE_NAME";
  public static String OTHER_VIDEO_EDITOR_EXECUTABLE_NAME_KEY       = "OTHER_VIDEO_EDITOR_EXECUTABLE_NAME";
  public static String OTHER_VOLUME_EDITOR_EXECUTABLE_NAME_KEY      ="OTHER_VOLUME_EDITOR_EXECUTABLE_NAME";
  public static String OTHER_SEMANTICWEB_EDITOR_EXECUTABLE_NAME_KEY ="OTHER_SEMANTICWEB_EDITOR_EXECUTABLE_NAME";
  
  /* External viewers options */
  public static String           AMAYA_EDITOR_PATH_KEY          =          "AMAYA_EDITOR_PATH";
  public static String        AUDACITY_EDITOR_PATH_KEY          =       "AUDACITY_EDITOR_PATH";
  public static String       MUSESCORE_EDITOR_PATH_KEY          =      "MUSESCORE_EDITOR_PATH";
  public static String            GIMP_EDITOR_PATH_KEY          =           "GIMP_EDITOR_PATH";
  public static String          IMAGEJ_EDITOR_PATH_KEY          =         "IMAGEJ_EDITOR_PATH";
  public static String     IMAGEMAGICK_EDITOR_PATH_KEY          =    "IMAGEMAGICK_EDITOR_PATH";
  public static String            FIJI_EDITOR_PATH_KEY          =           "FIJI_EDITOR_PATH";
  public static String             VLC_PLAYER_PATH_KEY          =            "VLC_PLAYER_PATH";
  public static String         PROTEGE_PLAYER_PATH_KEY          =        "PROTEGE_PLAYER_PATH";
  public static String        PORTECLE_PLAYER_PATH_KEY          =       "PORTECLE_PLAYER_PATH";
  public static String KEYSTOREEXPLORER_PLAYER_PATH_KEY         = "KEYSTOREEXPLORER_PLAYER_PATH";
  public static String               WIRESHARK_PATH_KEY          =              "WIRESHARK_PATH";
  
  public static String ALTOVA_XMLSPY_X3D_EDITOR_PATH_KEY        = "ALTOVA_XMLSPY_X3D_EDITOR_PATH";
  public static String     BLENDER_X3D_EDITOR_PATH_KEY          =    "BLENDER_X3D_EDITOR_PATH";
  public static String BSCONTENTSTUDIO_X3D_EDITOR_PATH_KEY      = "BSCONTENTSTUDIO_X3D_EDITOR_PATH";
  public static String        BVHACKER_EDITOR_PATH_KEY          =       "BVHACKER_EDITOR_PATH";
  public static String        CURA_X3D_EDITOR_PATH_KEY          =       "CURA_X3D_EDITOR_PATH";
  public static String     MESHLAB_X3D_EDITOR_PATH_KEY          =    "MESHLAB_X3D_EDITOR_PATH";
  public static String    PARAVIEW_X3D_EDITOR_PATH_KEY          =   "PARAVIEW_X3D_EDITOR_PATH";
  public static String  MAYARAWKEE_X3D_EDITOR_PATH_KEY          = "MAYARAWKEE_X3D_EDITOR_PATH";
  public static String     RHINO3D_X3D_EDITOR_PATH_KEY          =    "RHINO3D_X3D_EDITOR_PATH";
  public static String  SEAMLESS3D_X3D_EDITOR_PATH_KEY          = "SEAMLESS3D_X3D_EDITOR_PATH";
  public static String     SUNRIZE_X3D_EDITOR_PATH_KEY          =    "SUNRIZE_X3D_EDITOR_PATH";
  public static String  ITKSNAP_VOLUME_EDITOR_PATH_KEY          =    "ITKSNAP_X3D_EDITOR_PATH";
  public static String    SEG3D_VOLUME_EDITOR_PATH_KEY          =      "SEG3D_X3D_EDITOR_PATH";
  public static String SLICER3D_VOLUME_EDITOR_PATH_KEY          =   "SLICER3D_X3D_EDITOR_PATH";
  public static String       BATIK_SVG_EDITOR_PATH_KEY          =      "BATIK_SVG_EDITOR_PATH";
  public static String    INKSCAPE_SVG_EDITOR_PATH_KEY          =   "INKSCAPE_SVG_EDITOR_PATH";
  public static String    SVG_EDIT_SVG_EDITOR_PATH_KEY          =   "SVG_EDIT_SVG_EDITOR_PATH";
  public static String   WHITEDUNE_X3D_EDITOR_PATH_KEY          =  "WHITEDUNE_X3D_EDITOR_PATH";
  public static String     WINGS3D_X3D_EDITOR_PATH_KEY          =    "WINGS3D_X3D_EDITOR_PATH";
  public static String   ULTRAEDIT_X3D_EDITOR_PATH_KEY          =  "ULTRAEDIT_X3D_EDITOR_PATH";
  
  public static String   OTHER_AUDIO_EDITOR_NAME_KEY            =   "OTHER_AUDIO_EDITOR_NAME";
  public static String   OTHER_HTML5_EDITOR_NAME_KEY            =   "OTHER_HTML5_EDITOR_NAME";
  public static String   OTHER_IMAGE_EDITOR_NAME_KEY            =   "OTHER_IMAGE_EDITOR_NAME";
  public static String   OTHER_VIDEO_EDITOR_NAME_KEY            =   "OTHER_VIDEO_EDITOR_NAME";
  public static String  OTHER_VOLUME_EDITOR_NAME_KEY            =  "OTHER_VOLUME_EDITOR_NAME";
  public static String  OTHER_SEMANTICWEB_EDITOR_NAME_KEY       =  "OTHER_SEMANTICWEB_EDITOR_NAME";
  public static String     OTHER_X3D_EDITOR_NAME_KEY            =     "OTHER_X3D_EDITOR_NAME";
  public static String   OTHER_AUDIO_EDITOR_PATH_KEY            =   "OTHER_AUDIO_EDITOR_PATH";
  public static String   OTHER_HTML5_EDITOR_PATH_KEY            =   "OTHER_HTML5_EDITOR_PATH";
  public static String   OTHER_IMAGE_EDITOR_PATH_KEY            =   "OTHER_IMAGE_EDITOR_PATH";
  public static String   OTHER_VIDEO_EDITOR_PATH_KEY            =   "OTHER_VIDEO_EDITOR_PATH";
  public static String  OTHER_VOLUME_EDITOR_PATH_KEY            =  "OTHER_VOLUME_EDITOR_PATH";
  public static String  OTHER_SEMANTICWEB_EDITOR_PATH_KEY       =  "OTHER_SEMANTICWEB_EDITOR_PATH";
  public static String     OTHER_X3D_EDITOR_PATH_KEY            =     "OTHER_X3D_EDITOR_PATH";
  public static String   OTHER_AUDIO_EDITOR_SWITCH_KEY          =   "OTHER_AUDIO_EDITOR_SWITCH";
  public static String   OTHER_HTML5_EDITOR_SWITCH_KEY          =   "OTHER_HTML5_EDITOR_SWITCH";
  public static String   OTHER_IMAGE_EDITOR_SWITCH_KEY          =   "OTHER_IMAGE_EDITOR_SWITCH";
  public static String   OTHER_VIDEO_EDITOR_SWITCH_KEY          =   "OTHER_VIDEO_EDITOR_SWITCH";
  public static String  OTHER_VOLUME_EDITOR_SWITCH_KEY          =  "OTHER_VOLUME_EDITOR_SWITCH";
  public static String  OTHER_SEMANTICWEB_EDITOR_SWITCH_KEY     =  "OTHER_SEMANTICWEB_EDITOR_SWITCH";
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
  public static String CASTLEMODELVIEWER_EXECUTABLE_PATH_DEFAULT;
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
  public static String         PROTEGE_PLAYER_PATH_DEFAULT;
  public static String        PORTECLE_PLAYER_PATH_DEFAULT;
  public static String KEYSTOREEXPLORER_PLAYER_PATH_DEFAULT;
  public static String               WIRESHARK_PATH_DEFAULT;
  
  public static String ALTOVA_XMLSPY_X3D_EDITOR_PATH_DEFAULT;
  public static String     BLENDER_X3D_EDITOR_PATH_DEFAULT;
  public static String BSCONTENTSTUDIO_X3D_EDITOR_PATH_DEFAULT;
  public static String        BVHACKER_EDITOR_PATH_DEFAULT;
  public static String        CURA_X3D_EDITOR_PATH_DEFAULT;
  public static String     MESHLAB_X3D_EDITOR_PATH_DEFAULT;
  public static String    PARAVIEW_X3D_EDITOR_PATH_DEFAULT;
  public static String  MAYARAWKEE_X3D_EDITOR_PATH_DEFAULT;
  public static String     RHINO3D_X3D_EDITOR_PATH_DEFAULT;
  public static String POLYTRANSNUGRAF_EDITOR_PATH_DEFAULT;
  public static String  SEAMLESS3D_X3D_EDITOR_PATH_DEFAULT;
  public static String     SUNRIZE_X3D_EDITOR_PATH_DEFAULT;
  public static String     ITKSNAP_X3D_EDITOR_PATH_DEFAULT;
  public static String       SEG3D_X3D_EDITOR_PATH_DEFAULT;
  public static String    SLICER3D_X3D_EDITOR_PATH_DEFAULT;
  public static String       BATIK_SVG_EDITOR_PATH_DEFAULT;
  public static String    INKSCAPE_SVG_EDITOR_PATH_DEFAULT;
  public static String    SVG_EDIT_SVG_EDITOR_PATH_DEFAULT;
  public static String   WHITEDUNE_X3D_EDITOR_PATH_DEFAULT;
  public static String     WINGS3D_X3D_EDITOR_PATH_DEFAULT;
  public static String   ULTRAEDIT_X3D_EDITOR_PATH_DEFAULT;
  public static String     OTHER_AUDIO_EDITOR_NAME_DEFAULT;
  public static String     OTHER_HTML5_EDITOR_NAME_DEFAULT;
  public static String     OTHER_IMAGE_EDITOR_NAME_DEFAULT;
  public static String     OTHER_VIDEO_EDITOR_NAME_DEFAULT;
  public static String    OTHER_VOLUME_EDITOR_NAME_DEFAULT;
  public static String OTHER_SEMANTICWEB_EDITOR_NAME_DEFAULT;
  public static String       OTHER_X3D_EDITOR_NAME_DEFAULT;
  public static String     OTHER_AUDIO_EDITOR_PATH_DEFAULT;
  public static String     OTHER_HTML5_EDITOR_PATH_DEFAULT;
  public static String     OTHER_IMAGE_EDITOR_PATH_DEFAULT;
  public static String     OTHER_VIDEO_EDITOR_PATH_DEFAULT;
  public static String    OTHER_VOLUME_EDITOR_PATH_DEFAULT;
  public static String OTHER_SEMANTICWEB_EDITOR_PATH_DEFAULT;
  public static String       OTHER_X3D_EDITOR_PATH_DEFAULT;
  public static String   OTHER_AUDIO_EDITOR_SWITCH_DEFAULT;
  public static String   OTHER_HTML5_EDITOR_SWITCH_DEFAULT;
  public static String   OTHER_IMAGE_EDITOR_SWITCH_DEFAULT;
  public static String   OTHER_VIDEO_EDITOR_SWITCH_DEFAULT;
  public static String  OTHER_VOLUME_EDITOR_SWITCH_DEFAULT;
  public static String OTHER_SEMANTICWEB_EDITOR_SWITCH_DEFAULT;
  public static String     OTHER_X3D_EDITOR_SWITCH_DEFAULT;
  
  // note that installation of 32-bit software on 64-bit machines goes to directory C:\\Program Files (x86)\\
  // platform defaults:
                                                                   // C:\\Program Files\\Bitmanagement Software\\BS Contact\\BSContact.exe
  private static final String winxpContactPathDefault              = "C:\\Users\\"+AUTHOR_NAME_TOKEN+"\\AppData\\Local\\Bitmanagement Software\\BS Contact\\";
  private static final String winxpContactGeoPathDefault           = "C:\\Users\\"+AUTHOR_NAME_TOKEN+"\\AppData\\Local\\Bitmanagement Software\\BS Contact\\";
  private static final String winxpFreeWrlPathDefault              = "C:\\Program Files (x86)\\freeWRL\\freeWRL.4\\freeWRL.exe";
//private static final String winxpFreeWrlPathDefault              = "C:\\Program Files (x86)\\freeWRL\\freeWRL.4\\launchdir\\freeWRL_Launcher.exe"; // alternative
   
  private static final String winxpH3dPathDefault                  = "C:\\Program Files\\SenseGraphics\\H3DViewer\\bin32\\H3DViewer.exe"; // TODO 32
  private static final String winxpHeilanPathDefault               = "C:\\Program Files\\HeilanBrowser-0.15\\HeilanBrowser.exe";
  private static final String winxpInstantRealityPathDefault       = "C:\\Program Files\\Instant Reality\\bin\\InstantPlayer.exe";
  private static final String winxpPolyTransNuGrafPathDefault      = "C:\\Program Files\\NuGraf64\\NuGraf64.exe";
  private static final String winxpOctagaPathDefault               = "C:\\Program Files\\Octaga Visual Solutions\\Octaga Player 5.0 (64 bit)\\OctagaPlayer.exe";
  private static final String winxpSwirlX3DPlayerPathDefault       = "C:\\Program Files\\Pinecoast\\SwirlViewer\\SwView.exe";
  private static final String winxpCastleModelViewerPathDefault    = "C:\\Program Files\\castle-model-viewer\\castle-model-viewer.exe";
  private static final String winxpVivatyPlayerPathDefault         = "C:\\Program Files\\Vivaty\\VivatyPlayer\\VivatyPlayer.exe";
  private static final String winxpXj3DPathDefault                 = "C:\\Program Files\\Xj3D\\browser.bat";
  private static final String winxpOtherX3dPlayerPathDefault       = ""; // user configured
  private static final String winxpOtherX3dPlayerNameDefault       = "(Add another player using X3D-Edit User Preferences Panel)"; // user configured
  private static final String winxpOtherX3dEditorPathDefault       = ""; // user configured
  private static final String winxpOtherX3dEditorNameDefault       = "(Add another modeling tool using X3D-Edit User Preferences Panel)"; // user configured
  private static final String winxpAmayaPathDefault                = "C:\\Program Files\\Amaya\\WindowsWX\\bin\\amaya.exe";
  private static final String winxpChromePathDefault               = "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe";
  private static final String winxpFirefoxPathDefault              = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
  private static final String winxpInternetExplorerPathDefault     = "C:\\Program Files\\Internet Explorer\\iexplore.exe";
  private static final String winxpOperaPathDefault                = "C:\\Program Files\\Opera\\opera.exe";
  private static final String winxpSafariPathDefault               = "C:\\Program Files\\Safari\\Safari.exe";
  private static final String winxpWiresharkPathDefault            = "C:\\Program Files\\Wireshark\\Wireshark.exe";
 
                                                                   // C:\\Program Files (x86)\\Bitmanagement Software\\BS Contact\\BSContact.exe
  private static final String windows64ContactPathDefault          = "C:\\Users\\"+AUTHOR_NAME_TOKEN+"\\AppData\\Local\\Bitmanagement Software\\BS Contact\\BSContact.exe";
  private static final String windows64ContactGeoPathDefault       = "C:\\Users\\"+AUTHOR_NAME_TOKEN+"\\AppData\\Local\\Bitmanagement Software\\BS Contact\\BSContact.exe";
  private static final String windows64FreeWrlPathDefault          = "C:\\Program Files (x86)\\freeWRL\\freeWRL.4\\freeWRL.exe";
//private static final String windows64FreeWrlPathDefault          = "C:\\Program Files (x86)\\freeWRL\\freeWRL.4\\launchdir\\freeWRL_launcher.exe"; // alternative
  private static final String windows64H3dPathDefault              = "C:\\Program Files\\SenseGraphics\\H3DViewer\\bin64\\H3DViewer.exe";
  private static final String windows64HeilanPathDefault           = "C:\\Program Files (x86)\\HeilanBrowser-0.15\\HeilanBrowser.exe";
  private static final String windows64InstantRealityPathDefault   = "C:\\Program Files\\Instant Reality\\bin\\InstantPlayer.exe";
  private static final String windows64PolyTransNuGrafPathDefault  = "C:\\Program Files\\NuGraf64\\NuGraf64.exe";
  private static final String windows64OctagaPathDefault           = "C:\\Program Files\\Octaga Visual Solutions\\Octaga Player 5.0 (64 bit)\\OctagaPlayer.exe";
  private static final String windows64SwirlX3DPlayerPathDefault   = "C:\\Program Files (x86)\\Pinecoast\\SwirlViewer\\SwView.exe";
  private static final String windows64CastleModelViewerPathDefault= "C:\\Program Files\\castle-model-viewer\\castle-model-viewer.exe";
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
  private static final String windows64WiresharkPathDefault        = "C:\\Program Files\\Wireshark\\Wireshark.exe";

  private static final String macosxContactPathDefault             = "/Applications/BS Contact.app";      // TODO verify BitManagement handled install path problem
  private static final String macosxContactGeoPathDefault          = "/Applications/BS Contact Geo.app";  // TODO verify BitManagement handled install path problem
  private static final String macosxFreeWrlPathDefault             = "/Applications/FreeWRL/FreeWrl.app";
  private static final String macosxH3dPathDefault                 = "/Applications/H3DViewer.app";
  private static final String macosxHeilanPathDefault              = "/Applications/HeilanBrowser.app";
  private static final String macosxInstantRealityPathDefault      = "/Applications/Instant Player.app";
  private static final String macosxPolyTransNuGrafPathDefault     = "/Applications/NuGraf.app";
  private static final String macosxOctagaPathDefault              = "/Applications/Octaga Player.app";
  private static final String macosxSwirlX3DPlayerPathDefault      = ""; // No Mac version as of 17 July 2008
  private static final String macosxCastleModelViewerPathDefault         = "";
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
  private static final String macosxWiresharkPathDefault           = "";

  private static final String otherContactPathDefault              = "ContactPlayer";
  private static final String otherContactGeoPathDefault           = "ContactGeoPlayer";
  private static final String otherFreeWrlPathDefault              = "FreeWrlPlayer"; // linux
  private static final String otherH3dPathDefault                  = "H3DViewer";
  private static final String otherHeilanPathDefault               = "HeilanBrowser";
  private static final String otherInstantRealityPathDefault       = "InstantPlayer";
  private static final String otherPolyTransNuGrafPathDefault      = "NuGraf";
  private static final String otherOctagaPathDefault               = "OctagaPlayer";
  private static final String otherSwirlX3DPlayerPathDefault       = "SwirlX3DPlayer";
  private static final String otherCastleModelViewerPathDefault    = "CastleModelViewer";
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

  private static final String downloadSiteBSContact                = "https://www.bitmanagement.de/en";
  private static final String downloadSiteBSContactGeo             = "https://www.bitmanagement.de/en";
  private static final String downloadSiteFreeWrl                  = "https://sourceforge.net/projects/freewrl"; 
                                                         // versions: https://sourceforge.net/projects/freewrl/files/
                                                         // formerly "https://www.crc.ca/FreeWRL"; 
                                                         //     also "https://sourceforge.net/projects/freewrl/files/freewrl-win32/3.0/"
  private static final String downloadSiteH3d                      = "https://www.h3dapi.org";
  private static final String downloadSiteHeilan                   = "https://www.niallmoody.com/heilan";
  private static final String downloadSiteInstantReality           = "https://instantreality.org";
  private static final String downloadSitePolyTransNuGraf          = "https://www.okino.com";
  private static final String downloadSiteOctaga                   = "https://www.octagavs.com";
  private static final String downloadSiteSwirlX3DPlayer           = "https://www.pinecoast.com"; // defunct
  private static final String downloadSiteCastleModelViewer        = "https://castle-engine.io/castle-model-viewer"; // https://castle-engine.io/view3dscene.php"; // "https://castle-engine.sourceforge.net";
  private static final String downloadSiteVivatyPlayer             = "https://www.web3d.org/projects/vivaty-studio"; // https://www.vivaty.com";
  private static final String downloadSiteXj3D                     = "https://savage.nps.edu/Savage/developers.html#Xj3D"; // "https://sourceforge.net/projects/xj3d"; // "https://www.Xj3D.org";
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
  private static final String downloadSiteOtherSemanticWebEditor   = "https://www.web3d.org/x3d/content/semantics/semantics.html#Tools";
  private static final String downloadSiteOtherX3dEditor           = "https://www.web3d.org/x3d/content/examples/X3dResources.html#AuthoringSoftware";
  private static final String downloadSiteWireshark                = "https://www.wireshark.org";
  
  private static final String winxpAudacityEditorPathDefault             = "C:\\Program Files\\Audacity\\Audacity.exe";
  private static final String winxpMuseScoreEditorPathDefault            = "C:\\Program Files\\MuseScore 3\\bin\\MuseScore3.exe";
  private static final String winxpGimpEditorPathDefault                 = "C:\\Program Files\\GIMP 3\\bin\\gimp-3.exe";
  private static final String winxpFijiEditorPathDefault                 = "C:\\Program Files\\Fiji.app\\ImageJ-win32.exe";
  private static final String winxpImageJEditorPathDefault               = "C:\\Program Files\\ImageJ\\ImageJ.exe";
  private static final String winxpImageMagickEditorPathDefault          = "C:\\Program Files\\ImageMagick-7.1.1-Q16-HDRI\\imdisplay.exe";
  private static final String winxpVlcPlayerPathDefault                  = "C:\\Program Files\\VideoLAN\\VLC\\vlc.exe";
  private static final String winxpProtegePlayerPathDefault              = "C:\\Program Files\\Protege-5.6.4\\Protege.exe";
  private static final String winxpPorteclePlayerPathDefault             = "C:\\Program Files (x86)\\Portecle\\Portecle.exe";
  private static final String winxpKeystoreExplorerPlayerPathDefault     = "C:\\Program Files (x86)\\Keystore Explorer\\kse.exe";
  
  private static final String winxpAltovaXMLSpyX3dEditorPathDefault      = "C:\\Program Files\\Altova\\XMLSpy2024\\XMLSpy.exe";
  private static final String winxpBlenderX3dEditorPathDefault           = "C:\\Program Files\\Blender Foundation\\Blender 4.2\\blender.exe";
  private static final String winxpBsContentStudioX3dEditorPathDefault   = "C:\\Program Files\\\\Bitmanagement Software\\BS Content Studio\\x64\\BSComposer.exe";
  private static final String winxpBvhackerEditorPathDefault             = "C:\\Program Files (x86)\\davedub\\bvhacker\\bvhacker.exe";
  private static final String winxpCuraX3dEditorPathDefault              = "C:\\Program Files\\Ultimaker Cura 5.9.0\\Ultimaker-Cura.exe";
  private static final String winxpMeshLabX3dEditorPathDefault           = "C:\\Program Files\\VCG\\MeshLab\\meshlab.exe";
  private static final String winxpParaviewX3dEditorPathDefault          = "C:\\Program Files\\ParaView 5.13.1\\bin\\paraview.exe";
  private static final String winxpMayaRawkeeX3dEditorPathDefault        = "";
  private static final String winxpRhino3DX3dEditorPathDefault           = "C:\\Program Files\\Rhino 8\\System\\Rhino.exe";
  private static final String winxpSeamlessX3dEditorPathDefault          = "C:\\Program Files\\Seamless3d\\seamless3d.exe";
  private static final String winxpSunrizeX3dEditorPathDefault           = "npx sunrize"; // node.js
  private static final String winxpItksnapEditorPathDefault              = "C:\\Program Files\\ITK-SNAP 4.0\\bin\\ITK-SNAP.exe";
  private static final String winxpSeg3dEditorPathDefault                = "C:\\Program Files\\Seg3D2_2.5\\bin\\Seg3D2.exe";
  private static final String winxpSlicer3dEditorPathDefault             = "C:\\ProgramData\\slicer.org\\Slicer 5.6.2\\Slicer.exe";
  private static final String winxpBatikEditorPathDefault                = "C:\\languages\\java\\batik-1.17\\batik-squiggle-1.17.jar";
  private static final String winxpInkscapeEditorPathDefault             = "C:\\Program Files\\Inkscape\\Inkscape";
  private static final String winxpSvgeditEditorPathDefault              = "https://svgedit.netlify.app/editor/index.html"; // online editor
  private static final String winxpWhiteDuneX3dEditorPathDefault         = "C:\\installs\\WhiteDune\\white_dune-1.956.exe";
  private static final String winxpWingsX3dEditorPathDefault             = "C:\\Program Files\\wings3d_2.4.1\\Wings3D.exe";
  private static final String winxpUltraEditX3dEditorPathDefault         = "C:\\Program Files\\IDM Computer Solutions\\UltraEdit\\uedit32.exe";

  private static final String windows64AudacityEditorPathDefault         = "C:\\Program Files\\Audacity\\Audacity.exe";
  private static final String windows64MuseScoreEditorPathDefault        = "C:\\Program Files\\MuseScore 4\\bin\\MuseScore4.exe";
  private static final String windows64GimpEditorPathDefault             = "C:\\Program Files\\GIMP 3\\bin\\gimp-3.exe";
  private static final String windows64FijiEditorPathDefault             = "C:\\Program Files\\Fiji.app\\ImageJ-win64.exe";
  private static final String windows64ImageJEditorPathDefault           = "C:\\Program Files\\ImageJ\\ImageJ.exe";
  private static final String windows64ImageMagickEditorPathDefault      = "C:\\Program Files\\ImageMagick-7.1.1-Q16-HDRI\\imdisplay.exe";
  private static final String windows64VlcPlayerPathDefault              = "C:\\Program Files\\VideoLAN\\VLC\\vlc.exe";
  private static final String windows64ProtegePlayerPathDefault          = "C:\\Program Files\\Protege-5.6.4\\Protege.exe";
  private static final String windows64PorteclePlayerPathDefault         = "C:\\Program Files (x86)\\Portecle\\Portecle.exe";
  private static final String windows64KeystoreExplorerPlayerPathDefault = "C:\\Program Files (x86)\\Keystore Explorer\\kse.exe";
  
  private static final String windows64AltovaXMLSpyX3dEditorPathDefault  = "C:\\Program Files\\Altova\\XMLSpy2024\\XMLSpy.exe";
  private static final String windows64BlenderX3dEditorPathDefault       = "C:\\Program Files\\Blender Foundation\\Blender 4.2\\blender.exe";
  private static final String windows64BsContentStudioX3dEditorPathDefault="C:\\Program Files (x86)\\Bitmanagement Software\\BS Content Studio\\x64\\BSComposer.exe";
  private static final String windows64BvhackerEditorPathDefault         = "C:\\Program Files (x86)\\davedub\\bvhacker\\bvhacker.exe";
  private static final String windows64CuraX3dEditorPathDefault          = "C:\\Program Files\\Ultimaker Cura 5.9.0\\Ultimaker-Cura.exe";
  private static final String windows64MeshLabX3dEditorPathDefault       = "C:\\Program Files\\VCG\\MeshLab\\meshlab.exe";
  private static final String windows64ParaviewX3dEditorPathDefault      = "C:\\Program Files\\ParaView 5.13.1\\bin\\paraview.exe";
  private static final String windows64MayaRawkeeX3dEditorPathDefault    = "";
  private static final String windows64Rhino3DX3dEditorPathDefault       = "C:\\Program Files\\Rhino 8\\System\\Rhino.exe";
  private static final String windows64SeamlessX3dEditorPathDefault      = "C:\\Program Files\\Seamless3d\\seamless3d.exe";
  private static final String windows64SunrizeX3dEditorPathDefault       = "npx sunrize"; // node.js
  private static final String windows64ItksnapEditorPathDefault          = "C:\\Program Files\\ITK-SNAP 4.0\\bin\\ITK-SNAP.exe";
  private static final String windows64Seg3dEditorPathDefault            = "C:\\Program Files\\Seg3D2_2.5\\bin\\Seg3D2.exe";
  private static final String windows64Slicer3dEditorPathDefault         = "C:\\ProgramData\\slicer.org\\Slicer 5.6.2\\Slicer.exe";
  private static final String windows64BatikEditorPathDefault            = "C:\\languages\\java\\batik-1.17\\batik-squiggle-1.17.jar";
  private static final String windows64InkscapeEditorPathDefault         = "C:\\Program Files\\Inkscape\\bin\\inkscape.exe";
  private static final String windows64SvgeditEditorPathDefault          = "https://svgedit.netlify.app/editor/index.html"; // online editor
  private static final String windows64WhiteDuneX3dEditorPathDefault     = "C:\\installs\\WhiteDune\\white_dune-1.956.exe";
  private static final String windows64WingsX3dEditorPathDefault         = "C:\\Program Files\\wings3d_2.4.1\\Wings3D.exe";
  private static final String windows64UltraEditX3dEditorPathDefault     = "C:\\Program Files\\IDM Computer Solutions\\UltraEdit\\uedit64.exe";
  
  private static final String macosxAudacityEditorPathDefault            = "audacity";       // TODO insert correct value
  private static final String macosxMuseScoreEditorPathDefault           = "musescore";      // TODO insert correct value
  private static final String macosxGimpImageEditorPathDefault           = "gimp-3";      // TODO insert correct value
  private static final String macosxFijiEditorPathDefault                = "Fiji";           // TODO insert correct value
  private static final String macosxImageJEditorPathDefault              = "ij.jar";         // TODO insert correct value
  private static final String macosxImageMagickEditorPathDefault         = "imageconverter"; // TODO insert correct value
  private static final String macosxVlcPlayerPathDefault                 = "vlc";            // TODO insert correct value
  private static final String macosxProtegePlayerPathDefault             = "protege";        // TODO insert correct value
  private static final String macosxPorteclePlayerPathDefault            = "portecle";       // TODO insert correct value
  private static final String macosxKeystoreExplorerPlayerPathDefault    = "keystoreexplorer";// TODO insert correct value
  
  private static final String macosxAltovaXMLSpyX3dEditorPathDefault     = "xmlspy";         // TODO insert correct value
  private static final String macosxBlenderX3dEditorPathDefault          = "blender";        // TODO insert correct value
  private static final String macosxBsContentStudioX3dEditorPathDefault  = "bscomposer";     // TODO insert correct value
  private static final String macosxBvhackerEditorPathDefault            = "bvhacker";       // TODO insert correct value
  private static final String macosxCuraX3dEditorPathDefault             = "Cura";           // TODO insert correct value
  private static final String macosxMeshLabX3dEditorPathDefault          = "MeshLab";        // TODO insert correct value
  private static final String macosxParaviewX3dEditorPathDefault         = "Paraview";       // TODO insert correct value
  private static final String macosxMayaRawkeeX3dEditorPathDefault       = "Maya";           // TODO insert correct value
  private static final String macosxRhino3DX3dEditorPathDefault          = "Rhino3D";        // TODO insert correct value
  private static final String macosxSeamlessX3dEditorPathDefault         = "Seamless3d";     // TODO insert correct value
  private static final String macosxSunrizeX3dEditorPathDefault          = "npx sunrize";    // node.js
  private static final String macosxItksnapEditorPathDefault             = "Itksnap";        // TODO insert correct value
  private static final String macosxSeg3dEditorPathDefault               = "Seg3d";          // TODO insert correct value
  private static final String macosxSlicer3dEditorPathDefault            = "Slicer3d";       // TODO insert correct value
  private static final String macosxBatikEditorPathDefault               = "batik-squiggle"; // TODO insert correct value
  private static final String macosxInkscapeEditorPathDefault            = "Inkscape";       // TODO insert correct value
  private static final String macosxSvgeditEditorPathDefault             = "https://svgedit.netlify.app/editor/index.html"; // online editor
  private static final String macosxWhiteDuneX3dEditorPathDefault        = "WhiteDune";      // TODO insert correct value
  private static final String macosxWingsX3dEditorPathDefault            = "Wings3d";        // TODO insert correct value
  private static final String macosxUltraEditX3dEditorPathDefault        = "UltraEdit";      // TODO insert correct value
  
  private static final String otherAudacityEditorPathDefault             = "audacity";       // TODO insert correct value
  private static final String otherMuseScoreEditorPathDefault            = "musescore";      // TODO insert correct value
  private static final String otherGimpEditorPathDefault                 = "gimp-3";         // TODO insert correct value
  private static final String otherFijiEditorPathDefault                 = "ImageJ-linux64"; // or ImageJ-linux32
  private static final String otherImageJEditorPathDefault               = "ij.jar";         // TODO insert correct value
  private static final String otherImageMagickEditorPathDefault          = "imageconverter"; // TODO insert correct value
  private static final String otherVlcPlayerPathDefault                  = "vlc";            // TODO insert correct value
  private static final String otherProtegePlayerPathDefault              = "protege";        // TODO insert correct value
  private static final String otherPorteclePlayerPathDefault             = "portecle";       // TODO insert correct value
  private static final String otherKeystoreExplorerPlayerPathDefault     = "keystoreexplorer";// TODO insert correct value
  
  private static final String otherBlenderX3dEditorPathDefault           = "blender";        // TODO insert correct value
  private static final String otherBsContentStudioX3dEditorPathDefault   = "bscomposer";     // TODO insert correct value
  private static final String otherBvhackerEditorPathDefault             = "bvhacker";        // TODO insert correct value
  private static final String otherCuraX3dEditorPathDefault              = "Cura";           // TODO insert correct value
  private static final String otherMeshLabX3dEditorPathDefault           = "MeshLab";        // TODO insert correct value
  private static final String otherParaviewX3dEditorPathDefault          = "Paraview";       // TODO insert correct value
  private static final String otherMayaRawkeeX3dEditorPathDefault        = "Maya";           // TODO insert correct value
  private static final String otherRhino3DX3dEditorPathDefault           = "Rhino3D";        // TODO insert correct value
  private static final String otherSeamlessX3dEditorPathDefault          = "Seamless3d";     // TODO insert correct value
  private static final String otherSunrizeX3dEditorPathDefault           = "npx sunrize";    // node.js
  private static final String otherItksnapEditorPathDefault              = "Itksnap";        // TODO insert correct value
  private static final String otherSeg3dEditorPathDefault                = "Seg3d";          // TODO insert correct value
  private static final String otherSlicer3dEditorPathDefault             = "Slicer3d";       // TODO insert correct value
  private static final String otherBatikEditorPathDefault                = "batik-squiggle";       // TODO insert correct value
  private static final String otherInkscapeEditorPathDefault             = "Inkscape";       // TODO insert correct value
  private static final String otherSvgeditEditorPathDefault              = "https://svgedit.netlify.app/editor/index.html"; // online editor
  private static final String otherWhiteDuneX3dEditorPathDefault         = "WhiteDune";      // TODO insert correct value
  private static final String otherWingsX3dEditorPathDefault             = "Wings3d";        // TODO insert correct value
  private static final String otherUltraEditX3dEditorPathDefault         = "UltraEdit";      // TODO insert correct value
  
  private static final String  otherAudioEditorNameDefault                = "Other tool";
  private static final String  otherHtml5EditorNameDefault                = "Other tool";
  private static final String  otherImageEditorNameDefault                = "Other tool";
  private static final String  otherVideoEditorNameDefault                = "Other tool";
  private static final String otherVolumeEditorNameDefault                = "Other tool";
  private static final String otherSemanticWebEditorNameDefault           = "Other tool";
  private static final String  otherAudioEditorPathDefault                = ""; // User defined
  private static final String  otherHtml5EditorPathDefault                = ""; // User defined
  private static final String  otherImageEditorPathDefault                = ""; // User defined
  private static final String  otherVideoEditorPathDefault                = ""; // User defined
  private static final String otherSemanticWebEditorPathDefault           = ""; // User defined
  private static final String otherVolumeEditorPathDefault                = ""; // User defined
  private static final String  otherAudioEditorSwitchDefault              = ""; // User defined command-line launch switch
  private static final String  otherHtml5EditorSwitchDefault              = ""; // User defined command-line launch switch
  private static final String  otherImageEditorSwitchDefault              = ""; // User defined command-line launch switch
  private static final String  otherVideoEditorSwitchDefault              = ""; // User defined command-line launch switch
  private static final String otherVolumeEditorSwitchDefault              = ""; // User defined command-line launch switch
  private static final String otherSemanticWebEditorSwitchDefault         = ""; // User defined command-line launch switch
  
  private static final String downloadSiteAudacity                       = "https://www.audacityteam.org"; // /download"; 
                                                                         // https://sourceforge.net/projects/audacity";
  private static final String downloadSiteMuseScore                      = "https://musescore.org";
  private static final String downloadSiteGimp                           = "https://www.gimp.org";
  private static final String downloadSiteFiji                           = "https://imagej.github.io/software/fiji/downloads"; // "https://imagej.net/Fiji/Downloads";
  private static final String downloadSiteImageJ                         = "https://imagej.net/ij/download.html"; 
                                                                         // https://imagej.nih.gov/ij/download.html;
                                                                         // https://rsbweb.nih.gov/ij/download.html;
  private static final String downloadSiteImageMagick                    = "https://imagemagick.org/script/download.php";
  private static final String downloadSiteVlc                            = "https://www.videolan.org";
  private static final String downloadSiteProtege                        = "https://protege.stanford.edu";
  private static final String downloadSitePortecle                       = "https://sourceforge.net/projects/portecleinstall";
  private static final String downloadSiteKeystoreExplorer               = "https://keystore-explorer.org";
 
  private static final String downloadSiteAltovaXMLSpy                   = "https://www.altova.com/download";
  private static final String downloadSiteBlender                        = "https://www.blender.org/download"; // https://www.blender.org/download/lts/3-6";
  private static final String downloadSiteBsContentStudio                = "https://www.bitmanagement.de/en/download/studio";
  private static final String downloadSiteBvhacker                       = "https://www.bvhacker.com";
  private static final String downloadSiteCura                           = "https://ultimaker.com/software/ultimaker-cura";
  private static final String downloadSiteMeshLab                        = "https://www.meshlab.net";
  private static final String downloadSiteParaview                       = "https://www.kitware.com/platforms/#paraview";
  private static final String downloadSiteMayaRawkee                     = ""; // TODO two entries?
  private static final String downloadSiteRhino3D                        = "https://www.rhino3d.com/download";
  private static final String downloadSiteSeamless3d                     = "https://www.seamless3d.com";
  private static final String downloadSiteSunrize                        = "https://nodejs.org/en/download";
  private static final String downloadSiteItksnap                        = "http://www.itksnap.org"; // http://www.itksnap.org/pmwiki/pmwiki.php?n=Main.Downloads";
  private static final String downloadSiteSeg3d                          = "https://www.sci.utah.edu/software/seg3d.html";
                                                                        // "https://www.sci.utah.edu/cibc-software/seg3d.html";
  private static final String downloadSiteSlicer3d                       = "https://www.slicer.org";
  private static final String downloadSiteBatik                          = "https://xmlgraphics.apache.org/batik";
  private static final String downloadSiteInkscape                       = "https://www.inkscape.org";
  private static final String downloadSiteSvgedit                        = "https://github.com/SVG-Edit"; // https://code.google.com/p/svg-edit";
  private static final String downloadSiteWhiteDune                      = "https://wdune.ourproject.org";
  private static final String downloadSiteWings3d                        = "https://www.wings3d.com"; // https://www.wings3d.com/?page_id=84";
  private static final String downloadSiteUltraEdit                      = "https://www.UltraEdit.com";
  
  // no command line options found to hand off the import of an X3D file, TODO provide local help
  // https://wiki.blender.org/index.php/Doc:2.4/Reference/Command_Line

  protected static final String helpSiteAmaya                            = "https://www.w3.org/Amaya/User/Overview.html";
  protected static final String helpSiteAudacity                         = "https://www.audacityteam.org/help/"; // https://audacity.sourceforge.net/help";
  protected static final String helpSiteMuseScore                        = "https://musescore.org/en/handbook";
  protected static final String helpSiteGimp                             = "https://www.gimp.org/docs";
  protected static final String helpSiteFiji                             = "https://fiji.sc/Documentation";
  protected static final String helpSiteImageJ                           = "https://imagej.net/ij/docs"; 
                                                                         //"https://rsbweb.nih.gov/ij/docs";
  protected static final String helpSiteImageMagick                      = "https://www.imagemagick.org";
  protected static final String helpSiteVlc                              = "https://www.videolan.org/support/#documentation";
  protected static final String helpSiteProtege                          = "https://protege.stanford.edu/support.php#documentationSupport";
  protected static final String helpSitePortecle                         = "https://portecle.sourceforge.net/howtos.html";
  protected static final String helpSiteKeystoreExplorer                 = "https://keystore-explorer.org/doc/5.5/overview.html";
  protected static final String helpSiteWireshark                        = "https://www.wireshark.org/faq.html";
  
  protected static final String helpSiteAltovaXMLSpy                     = "https://www.altova.com/library";
  protected static final String helpSiteBlender                          = "https://www.blender.org/support";
  protected static final String helpSiteBsContentStudio                  = "https://www.bitmanagement.de/en/download/studio";
  protected static final String helpSiteBvhacker                         = "https://www.bvhacker.com/help.html";
  protected static final String helpSiteCura                             = "https://ultimaker.com/learn"; 
  protected static final String helpSiteMeshLab                          = "https://www.meshlab.net"; // https://sourceforge.net/apps/mediawiki/meshlab";
  protected static final String helpSiteParaview                         = "https://www.paraview.org";
  protected static final String helpSiteMayaRawkee                       = ""; // TODO two entries?
  protected static final String helpSiteRhino3D                          = "https://www.rhino3d.com/learn";
  protected static final String helpSitePolyTransNuGraf                  = "https://www.okino.com/nrs/nrs.htm";
  protected static final String helpSiteSeamless3d                       = "https://www.seamless3d.com/faq.html";
  protected static final String helpSiteSunrize                          = "https://create3000.github.io/sunrize";
  protected static final String helpSiteItksnap                          = "http://www.itksnap.org/pmwiki/pmwiki.php?n=Documentation.SNAP3"; // http://www.itksnap.org/pmwiki/pmwiki.php";
  protected static final String helpSiteSeg3d                            = "http://sciinstitute.github.io/seg3d.pages";
                                                                        // "https://www.sci.utah.edu/cibc-software/seg3d.html";
  protected static final String helpSiteSlicer3d                         = "https://www.slicer.org/slicerWiki/index.php/Documentation/Release";
  protected static final String helpSiteBatik                            = "https://xmlgraphics.apache.org/batik/faq.html";
  protected static final String helpSiteInkscape                         = "https://www.inkscape.org/learn";
  protected static final String helpSiteSvgedit                          = "https://en.wikipedia.org/wiki/SVG-edit"; // https://code.google.com/p/svg-edit";
  protected static final String helpSiteWhiteDune                        = "https://wdune.ourproject.org/docs";
  protected static final String helpSiteWings3d                          = "https://www.wings3d.com/?page_id=87";
  protected static final String helpSiteUltraEdit                        = "https://www.ultraedit.com/wiki/Main_Page"; // https://www.UltraEdit.com/help/category/ultraedit-uestudio";

  public static String getDownloadSiteContact()        {return downloadSiteBSContact;}
  public static String getDownloadSiteContactGeo()     {return downloadSiteBSContactGeo;}
  public static String getDownloadSiteFreeWrl()        {return downloadSiteFreeWrl;}
  public static String getDownloadSiteH3d()            {return downloadSiteH3d;}
  public static String getDownloadSiteHeilan()         {return downloadSiteHeilan;}
  public static String getDownloadSiteInstantReality() {return downloadSiteInstantReality;}
  public static String getDownloadSitePolyTransNuGraf(){return downloadSitePolyTransNuGraf;}
  public static String getDownloadSiteOctaga()         {return downloadSiteOctaga;}
  public static String getDownloadSiteSwirlX3D()       {return downloadSiteSwirlX3DPlayer;}
  public static String getDownloadSiteCastleModelViewer() {return downloadSiteCastleModelViewer;}
  public static String getDownloadSiteVivaty()         {return downloadSiteVivatyPlayer;}
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
  public static String getDownloadSiteProtege()        {return downloadSiteProtege;}
  public static String getDownloadSitePortecle()       {return downloadSitePortecle;}
  public static String getDownloadSiteKeystoreExplorer() {return downloadSiteKeystoreExplorer;}
  public static String getDownloadSiteWireshark()      {return downloadSiteWireshark;}
  
  public static String getDownloadSiteAltovaXMLSpy()   {return downloadSiteAltovaXMLSpy;}
  public static String getDownloadSiteBlender()        {return downloadSiteBlender;}
  public static String getDownloadSiteBsContentStudio(){return downloadSiteBsContentStudio;}
  public static String getDownloadSiteBvhacker()       {return downloadSiteBvhacker;}
  public static String getDownloadSiteCura()           {return downloadSiteCura;}
  public static String getDownloadSiteMeshLab()        {return downloadSiteMeshLab;}
  public static String getDownloadSiteParaview()       {return downloadSiteParaview;}
  public static String getDownloadSiteMayaRawkee()     {return downloadSiteMayaRawkee;}
  public static String getDownloadSiteRhino3D()        {return downloadSiteRhino3D;}
  public static String getDownloadSiteSeamless3d()     {return downloadSiteSeamless3d;}
  public static String getDownloadSiteSunrize()        {return downloadSiteSunrize;}
  public static String getDownloadSiteItksnap()        {return downloadSiteItksnap;}
  public static String getDownloadSiteSeg3d()          {return downloadSiteSeg3d;}
  public static String getDownloadSiteSlicer3d()       {return downloadSiteSlicer3d;}
  public static String getDownloadSiteBatik()          {return downloadSiteBatik;}
  public static String getDownloadSiteInkscape()       {return downloadSiteInkscape;}
  public static String getDownloadSiteSvgedit()        {return downloadSiteSvgedit;}
  public static String getDownloadSiteWhiteDune()      {return downloadSiteWhiteDune;}
  public static String getDownloadSiteWings3d()        {return downloadSiteWings3d;}
  public static String getDownloadSiteUltraEdit()      {return downloadSiteUltraEdit;}
  
  public static String getDownloadSiteOtherAudioEditor() {return downloadSiteOtherAudioEditor;}
  public static String getDownloadSiteOtherHtml5Editor() {return downloadSiteOtherHtml5Editor;}
  public static String getDownloadSiteOtherImageEditor() {return downloadSiteOtherImageEditor;}
  public static String getDownloadSiteOtherVideoEditor() {return downloadSiteOtherVideoEditor;}
  public static String getDownloadSiteOtherVolumeEditor(){return downloadSiteOtherVolumeEditor;}
  public static String getDownloadSiteOtherSemanticWebEditor(){return downloadSiteOtherSemanticWebEditor;}
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
     CASTLEMODELVIEWER_EXECUTABLE_PATH_DEFAULT = toks(macosxCastleModelViewerPathDefault);
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
     WIRESHARK_PATH_DEFAULT                  = toks(macosxWiresharkPathDefault);
     
         AUDACITY_EDITOR_PATH_DEFAULT        = toks(    macosxAudacityEditorPathDefault);
        MUSESCORE_EDITOR_PATH_DEFAULT        = toks(   macosxMuseScoreEditorPathDefault);
             GIMP_EDITOR_PATH_DEFAULT        = toks(   macosxGimpImageEditorPathDefault);
             FIJI_EDITOR_PATH_DEFAULT        = toks(        macosxFijiEditorPathDefault);
           IMAGEJ_EDITOR_PATH_DEFAULT        = toks(      macosxImageJEditorPathDefault);
      IMAGEMAGICK_EDITOR_PATH_DEFAULT        = toks( macosxImageMagickEditorPathDefault);
              VLC_PLAYER_PATH_DEFAULT        = toks(         macosxVlcPlayerPathDefault);
          PROTEGE_PLAYER_PATH_DEFAULT        = toks(     macosxProtegePlayerPathDefault);
         PORTECLE_PLAYER_PATH_DEFAULT        = toks(    macosxPorteclePlayerPathDefault);
 KEYSTOREEXPLORER_PLAYER_PATH_DEFAULT        = toks(macosxKeystoreExplorerPlayerPathDefault);
 
      BLENDER_X3D_EDITOR_PATH_DEFAULT        = toks(  macosxBlenderX3dEditorPathDefault);
      BLENDER_X3D_EDITOR_PATH_DEFAULT        = toks(macosxBsContentStudioX3dEditorPathDefault);
         BVHACKER_EDITOR_PATH_DEFAULT        = toks(    macosxBvhackerEditorPathDefault);
         CURA_X3D_EDITOR_PATH_DEFAULT        = toks(     macosxCuraX3dEditorPathDefault);
      MESHLAB_X3D_EDITOR_PATH_DEFAULT        = toks(  macosxMeshLabX3dEditorPathDefault);
     PARAVIEW_X3D_EDITOR_PATH_DEFAULT        = toks( macosxParaviewX3dEditorPathDefault);
   MAYARAWKEE_X3D_EDITOR_PATH_DEFAULT        = toks(macosxMayaRawkeeX3dEditorPathDefault);
      RHINO3D_X3D_EDITOR_PATH_DEFAULT        = toks(  macosxRhino3DX3dEditorPathDefault);
  POLYTRANSNUGRAF_EDITOR_PATH_DEFAULT        = toks(   macosxPolyTransNuGrafPathDefault);
   SEAMLESS3D_X3D_EDITOR_PATH_DEFAULT        = toks( macosxSeamlessX3dEditorPathDefault);
      SUNRIZE_X3D_EDITOR_PATH_DEFAULT        = toks(  macosxSunrizeX3dEditorPathDefault);
      ITKSNAP_X3D_EDITOR_PATH_DEFAULT        = toks(     macosxItksnapEditorPathDefault);
        SEG3D_X3D_EDITOR_PATH_DEFAULT        = toks(       macosxSeg3dEditorPathDefault);
     SLICER3D_X3D_EDITOR_PATH_DEFAULT        = toks(    macosxSlicer3dEditorPathDefault);
        BATIK_SVG_EDITOR_PATH_DEFAULT        = toks (      macosxBatikEditorPathDefault);
     INKSCAPE_SVG_EDITOR_PATH_DEFAULT        = toks (   macosxInkscapeEditorPathDefault);
     SVG_EDIT_SVG_EDITOR_PATH_DEFAULT        = toks (    macosxSvgeditEditorPathDefault);
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
      CASTLEMODELVIEWER_EXECUTABLE_PATH_DEFAULT = toks(windows64CastleModelViewerPathDefault);
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
     WIRESHARK_PATH_DEFAULT                  = toks(windows64WiresharkPathDefault);
     
         AUDACITY_EDITOR_PATH_DEFAULT        = toks(    windows64AudacityEditorPathDefault);
         MUSESCORE_EDITOR_PATH_DEFAULT       = toks(   windows64MuseScoreEditorPathDefault);
             GIMP_EDITOR_PATH_DEFAULT        = toks(        windows64GimpEditorPathDefault);
             FIJI_EDITOR_PATH_DEFAULT        = toks(        windows64FijiEditorPathDefault);
           IMAGEJ_EDITOR_PATH_DEFAULT        = toks(      windows64ImageJEditorPathDefault);
      IMAGEMAGICK_EDITOR_PATH_DEFAULT        = toks( windows64ImageMagickEditorPathDefault);
              VLC_PLAYER_PATH_DEFAULT        = toks(         windows64VlcPlayerPathDefault);
          PROTEGE_PLAYER_PATH_DEFAULT        = toks(     windows64ProtegePlayerPathDefault);
         PORTECLE_PLAYER_PATH_DEFAULT        = toks(    windows64PorteclePlayerPathDefault);
 KEYSTOREEXPLORER_PLAYER_PATH_DEFAULT        = toks(windows64KeystoreExplorerPlayerPathDefault);
              
ALTOVA_XMLSPY_X3D_EDITOR_PATH_DEFAULT        = toks(  windows64AltovaXMLSpyX3dEditorPathDefault);
      BLENDER_X3D_EDITOR_PATH_DEFAULT        = toks(  windows64BlenderX3dEditorPathDefault);
BSCONTENTSTUDIO_X3D_EDITOR_PATH_DEFAULT      = toks(windows64BsContentStudioX3dEditorPathDefault);
         BVHACKER_EDITOR_PATH_DEFAULT        = toks(    windows64BvhackerEditorPathDefault);
         CURA_X3D_EDITOR_PATH_DEFAULT        = toks(     windows64CuraX3dEditorPathDefault);
      MESHLAB_X3D_EDITOR_PATH_DEFAULT        = toks(  windows64MeshLabX3dEditorPathDefault);
     PARAVIEW_X3D_EDITOR_PATH_DEFAULT        = toks( windows64ParaviewX3dEditorPathDefault);
   MAYARAWKEE_X3D_EDITOR_PATH_DEFAULT        = toks(windows64MayaRawkeeX3dEditorPathDefault);
      RHINO3D_X3D_EDITOR_PATH_DEFAULT        = toks(  windows64Rhino3DX3dEditorPathDefault);
  POLYTRANSNUGRAF_EDITOR_PATH_DEFAULT        = toks(   windows64PolyTransNuGrafPathDefault);
   SEAMLESS3D_X3D_EDITOR_PATH_DEFAULT        = toks( windows64SeamlessX3dEditorPathDefault);
      SUNRIZE_X3D_EDITOR_PATH_DEFAULT        = toks(  windows64SunrizeX3dEditorPathDefault);
      ITKSNAP_X3D_EDITOR_PATH_DEFAULT        = toks(     windows64ItksnapEditorPathDefault);
        SEG3D_X3D_EDITOR_PATH_DEFAULT        = toks(       windows64Seg3dEditorPathDefault);
     SLICER3D_X3D_EDITOR_PATH_DEFAULT        = toks(    windows64Slicer3dEditorPathDefault);
        BATIK_SVG_EDITOR_PATH_DEFAULT        = toks(       windows64BatikEditorPathDefault);
     INKSCAPE_SVG_EDITOR_PATH_DEFAULT        = toks(    windows64InkscapeEditorPathDefault);
     SVG_EDIT_SVG_EDITOR_PATH_DEFAULT        = toks(     windows64SvgeditEditorPathDefault);
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
      CASTLEMODELVIEWER_EXECUTABLE_PATH_DEFAULT    = toks(winxpCastleModelViewerPathDefault);
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
     WIRESHARK_PATH_DEFAULT                  = toks(winxpWiresharkPathDefault);
     
         AUDACITY_EDITOR_PATH_DEFAULT        = toks(    winxpAudacityEditorPathDefault);
         MUSESCORE_EDITOR_PATH_DEFAULT       = toks(   winxpMuseScoreEditorPathDefault);
             GIMP_EDITOR_PATH_DEFAULT        = toks(        winxpGimpEditorPathDefault);
             FIJI_EDITOR_PATH_DEFAULT        = toks(        winxpFijiEditorPathDefault);
           IMAGEJ_EDITOR_PATH_DEFAULT        = toks(      winxpImageJEditorPathDefault);
      IMAGEMAGICK_EDITOR_PATH_DEFAULT        = toks( winxpImageMagickEditorPathDefault);
              VLC_PLAYER_PATH_DEFAULT        = toks(         winxpVlcPlayerPathDefault);
          PROTEGE_PLAYER_PATH_DEFAULT        = toks(     winxpProtegePlayerPathDefault);
         PORTECLE_PLAYER_PATH_DEFAULT        = toks(    winxpPorteclePlayerPathDefault);
  KEYSTOREEXPLORER_PLAYER_PATH_DEFAULT        = toks(winxpKeystoreExplorerPlayerPathDefault);
  
      BLENDER_X3D_EDITOR_PATH_DEFAULT        = toks(  winxpBlenderX3dEditorPathDefault);
BSCONTENTSTUDIO_X3D_EDITOR_PATH_DEFAULT      = toks(winxpBsContentStudioX3dEditorPathDefault);
         BVHACKER_EDITOR_PATH_DEFAULT        = toks(    winxpBvhackerEditorPathDefault);
         CURA_X3D_EDITOR_PATH_DEFAULT        = toks(     winxpCuraX3dEditorPathDefault);
      MESHLAB_X3D_EDITOR_PATH_DEFAULT        = toks(  winxpMeshLabX3dEditorPathDefault);
     PARAVIEW_X3D_EDITOR_PATH_DEFAULT        = toks( winxpParaviewX3dEditorPathDefault);
   MAYARAWKEE_X3D_EDITOR_PATH_DEFAULT        = toks(winxpMayaRawkeeX3dEditorPathDefault);
      RHINO3D_X3D_EDITOR_PATH_DEFAULT        = toks(  winxpRhino3DX3dEditorPathDefault);
  POLYTRANSNUGRAF_EDITOR_PATH_DEFAULT        = toks(   winxpPolyTransNuGrafPathDefault);
   SEAMLESS3D_X3D_EDITOR_PATH_DEFAULT        = toks( winxpSeamlessX3dEditorPathDefault);
      SUNRIZE_X3D_EDITOR_PATH_DEFAULT        = toks(  winxpSunrizeX3dEditorPathDefault);
      ITKSNAP_X3D_EDITOR_PATH_DEFAULT        = toks(     winxpItksnapEditorPathDefault);
        SEG3D_X3D_EDITOR_PATH_DEFAULT        = toks(       winxpSeg3dEditorPathDefault);
     SLICER3D_X3D_EDITOR_PATH_DEFAULT        = toks(    winxpSlicer3dEditorPathDefault);
        BATIK_SVG_EDITOR_PATH_DEFAULT        = toks(       winxpBatikEditorPathDefault);
     INKSCAPE_SVG_EDITOR_PATH_DEFAULT        = toks(    winxpInkscapeEditorPathDefault);
     SVG_EDIT_SVG_EDITOR_PATH_DEFAULT        = toks(     winxpSvgeditEditorPathDefault);
    WHITEDUNE_X3D_EDITOR_PATH_DEFAULT        = toks(winxpWhiteDuneX3dEditorPathDefault);
      WINGS3D_X3D_EDITOR_PATH_DEFAULT        = toks(    winxpWingsX3dEditorPathDefault);
    ULTRAEDIT_X3D_EDITOR_PATH_DEFAULT        = toks(winxpUltraEditX3dEditorPathDefault);
    }
    else
    {
      CONTACT_EXECUTABLE_PATH_DEFAULT           = toks(otherContactPathDefault);
      CONTACT_GEO_EXECUTABLE_PATH_DEFAULT       = toks(otherContactGeoPathDefault);
      FREEWRL_EXECUTABLE_PATH_DEFAULT           = toks(otherFreeWrlPathDefault);
      H3D_EXECUTABLE_PATH_DEFAULT               = toks(otherH3dPathDefault);
      HEILAN_EXECUTABLE_PATH_DEFAULT            = toks(otherHeilanPathDefault);
      INSTANTREALITY_EXECUTABLE_PATH_DEFAULT    = toks(otherInstantRealityPathDefault);
      OCTAGA_EXECUTABLE_PATH_DEFAULT            = toks(otherOctagaPathDefault);
      SWIRLX3DPLAYER_EXECUTABLE_PATH_DEFAULT    = toks(otherSwirlX3DPlayerPathDefault);
      CASTLEMODELVIEWER_EXECUTABLE_PATH_DEFAULT = toks(otherCastleModelViewerPathDefault);
      VIVATYPLAYER_EXECUTABLE_PATH_DEFAULT      = toks(otherVivatyPlayerPathDefault);
      XJ3D_EXECUTABLE_PATH_DEFAULT              = toks(otherXj3DPathDefault);
      OTHER_X3D_PLAYER_EXECUTABLE_PATH_DEFAULT  = toks(otherX3dPlayerPathDefault);
      OTHER_X3D_PLAYER_EXECUTABLE_NAME_DEFAULT  = toks(otherX3dPlayerNameDefault);
      OTHER_X3D_EDITOR_EXECUTABLE_PATH_DEFAULT  = toks(otherX3dEditorPathDefault);
      OTHER_X3D_EDITOR_EXECUTABLE_NAME_DEFAULT  = toks(otherX3dEditorNameDefault);
     AMAYA_EDITOR_PATH_DEFAULT                  = toks(otherAmayaPathDefault);
     CHROME_EXECUTABLE_PATH_DEFAULT             = toks(otherChromePathDefault);
     FIREFOX_EXECUTABLE_PATH_DEFAULT            = toks(otherFirefoxPathDefault);
     INTERNETEXPLORER_EXECUTABLE_PATH_DEFAULT   = toks(otherInternetExplorerPathDefault);
     OPERA_EXECUTABLE_PATH_DEFAULT              = toks(otherOperaPathDefault);
     SAFARI_EXECUTABLE_PATH_DEFAULT             = toks(otherSafariPathDefault);
     
         AUDACITY_EDITOR_PATH_DEFAULT           = toks(    otherAudacityEditorPathDefault);
         MUSESCORE_EDITOR_PATH_DEFAULT          = toks(   otherMuseScoreEditorPathDefault);
             GIMP_EDITOR_PATH_DEFAULT           = toks(        otherGimpEditorPathDefault);
             FIJI_EDITOR_PATH_DEFAULT           = toks(        otherFijiEditorPathDefault);
           IMAGEJ_EDITOR_PATH_DEFAULT           = toks(      otherImageJEditorPathDefault);
      IMAGEMAGICK_EDITOR_PATH_DEFAULT           = toks( otherImageMagickEditorPathDefault);
              VLC_PLAYER_PATH_DEFAULT           = toks(         otherVlcPlayerPathDefault);
          PROTEGE_PLAYER_PATH_DEFAULT           = toks(     otherProtegePlayerPathDefault);
         PORTECLE_PLAYER_PATH_DEFAULT           = toks(    otherPorteclePlayerPathDefault);
  KEYSTOREEXPLORER_PLAYER_PATH_DEFAULT          = toks(otherKeystoreExplorerPlayerPathDefault);
  
      BLENDER_X3D_EDITOR_PATH_DEFAULT           = toks(  otherBlenderX3dEditorPathDefault);
BSCONTENTSTUDIO_X3D_EDITOR_PATH_DEFAULT         = toks(otherBsContentStudioX3dEditorPathDefault);
         BVHACKER_EDITOR_PATH_DEFAULT           = toks(    otherBvhackerEditorPathDefault);
         CURA_X3D_EDITOR_PATH_DEFAULT           = toks(     otherCuraX3dEditorPathDefault);
      MESHLAB_X3D_EDITOR_PATH_DEFAULT           = toks(  otherMeshLabX3dEditorPathDefault);
     PARAVIEW_X3D_EDITOR_PATH_DEFAULT           = toks( otherParaviewX3dEditorPathDefault);
   MAYARAWKEE_X3D_EDITOR_PATH_DEFAULT           = toks(otherMayaRawkeeX3dEditorPathDefault);
      RHINO3D_X3D_EDITOR_PATH_DEFAULT           = toks(  otherRhino3DX3dEditorPathDefault);
  POLYTRANSNUGRAF_EDITOR_PATH_DEFAULT           = toks(   otherPolyTransNuGrafPathDefault);
   SEAMLESS3D_X3D_EDITOR_PATH_DEFAULT           = toks( otherSeamlessX3dEditorPathDefault);
      SUNRIZE_X3D_EDITOR_PATH_DEFAULT           = toks(  otherSunrizeX3dEditorPathDefault);
      ITKSNAP_X3D_EDITOR_PATH_DEFAULT           = toks(     otherItksnapEditorPathDefault);
        SEG3D_X3D_EDITOR_PATH_DEFAULT           = toks(       otherSeg3dEditorPathDefault);
     SLICER3D_X3D_EDITOR_PATH_DEFAULT           = toks(    otherSlicer3dEditorPathDefault);
        BATIK_SVG_EDITOR_PATH_DEFAULT           = toks(       otherBatikEditorPathDefault);
     INKSCAPE_SVG_EDITOR_PATH_DEFAULT           = toks(    otherInkscapeEditorPathDefault);
     SVG_EDIT_SVG_EDITOR_PATH_DEFAULT           = toks(     otherSvgeditEditorPathDefault);
    WHITEDUNE_X3D_EDITOR_PATH_DEFAULT           = toks(otherWhiteDuneX3dEditorPathDefault);
      WINGS3D_X3D_EDITOR_PATH_DEFAULT           = toks(    otherWingsX3dEditorPathDefault);
    ULTRAEDIT_X3D_EDITOR_PATH_DEFAULT           = toks(otherUltraEditX3dEditorPathDefault);
    }
   
    OTHER_AUDIO_EDITOR_NAME_DEFAULT             = toks(        otherAudioEditorNameDefault);
    OTHER_HTML5_EDITOR_NAME_DEFAULT             = toks(        otherHtml5EditorNameDefault);
    OTHER_IMAGE_EDITOR_NAME_DEFAULT             = toks(        otherImageEditorNameDefault);
    OTHER_VIDEO_EDITOR_NAME_DEFAULT             = toks(        otherVideoEditorNameDefault);
   OTHER_VOLUME_EDITOR_NAME_DEFAULT             = toks(       otherVolumeEditorNameDefault);
   OTHER_SEMANTICWEB_EDITOR_NAME_DEFAULT        = toks(  otherSemanticWebEditorNameDefault);
    OTHER_X3D_EDITOR_NAME_DEFAULT               = toks(          otherX3dEditorNameDefault);
    OTHER_AUDIO_EDITOR_PATH_DEFAULT             = toks(        otherAudioEditorPathDefault);
    OTHER_HTML5_EDITOR_PATH_DEFAULT             = toks(        otherHtml5EditorPathDefault);
    OTHER_IMAGE_EDITOR_PATH_DEFAULT             = toks(        otherImageEditorPathDefault);
    OTHER_VIDEO_EDITOR_PATH_DEFAULT             = toks(        otherVideoEditorPathDefault);
   OTHER_VOLUME_EDITOR_PATH_DEFAULT             = toks(       otherVolumeEditorPathDefault);
   OTHER_SEMANTICWEB_EDITOR_PATH_DEFAULT        = toks(  otherSemanticWebEditorPathDefault);
    OTHER_X3D_EDITOR_PATH_DEFAULT               = toks(          otherX3dEditorPathDefault);
  OTHER_AUDIO_EDITOR_SWITCH_DEFAULT             = toks(      otherAudioEditorSwitchDefault);
  OTHER_HTML5_EDITOR_SWITCH_DEFAULT             = toks(      otherHtml5EditorSwitchDefault);
  OTHER_IMAGE_EDITOR_SWITCH_DEFAULT             = toks(      otherImageEditorSwitchDefault);
  OTHER_VIDEO_EDITOR_SWITCH_DEFAULT             = toks(      otherVideoEditorSwitchDefault);
 OTHER_VOLUME_EDITOR_SWITCH_DEFAULT             = toks(     otherVolumeEditorSwitchDefault);
 OTHER_SEMANTICWEB_EDITOR_SWITCH_DEFAULT        = toks(otherSemanticWebEditorSwitchDefault);
  OTHER_X3D_EDITOR_SWITCH_DEFAULT               = toks(        otherX3dEditorSwitchDefault);
  }
  private static String toks(String s)
  {
//  return s.replaceAll(AUTHOR_NAME_TOKEN, AUTHOR_NAME); // TODO huh?
    return s;
  }
  
  public static String getContactPathDefault()           {return CONTACT_EXECUTABLE_PATH_DEFAULT;}
  public static String getContactGeoPathDefault()        {return CONTACT_GEO_EXECUTABLE_PATH_DEFAULT;}
  public static String getFreeWrlPathDefault()           {return FREEWRL_EXECUTABLE_PATH_DEFAULT;}
  public static String getH3dPathDefault()               {return H3D_EXECUTABLE_PATH_DEFAULT;}
  public static String getHeilanPathDefault()            {return HEILAN_EXECUTABLE_PATH_DEFAULT;}
  public static String getInstantRealityPathDefault()    {return INSTANTREALITY_EXECUTABLE_PATH_DEFAULT;}
  public static String getOctagaPathDefault()            {return OCTAGA_EXECUTABLE_PATH_DEFAULT;}
  public static String getSwirlX3DPathDefault()          {return SWIRLX3DPLAYER_EXECUTABLE_PATH_DEFAULT;}
  public static String getCastleModelViewerPathDefault() {return CASTLEMODELVIEWER_EXECUTABLE_PATH_DEFAULT;}
  public static String getVivatyPlayerPathDefault()      {return VIVATYPLAYER_EXECUTABLE_PATH_DEFAULT;}
  public static String getXj3DPathDefault()              {return XJ3D_EXECUTABLE_PATH_DEFAULT;}
  public static String getOtherX3dPlayerNameDefault()    {return OTHER_X3D_PLAYER_EXECUTABLE_NAME_DEFAULT;}
  public static String getOtherX3dPlayerPathDefault()    {return OTHER_X3D_PLAYER_EXECUTABLE_PATH_DEFAULT;}
  public static String getOtherX3dEditorNameDefault()    {return OTHER_X3D_EDITOR_EXECUTABLE_NAME_DEFAULT;}
  public static String getOtherX3dEditorPathDefault()    {return OTHER_X3D_EDITOR_EXECUTABLE_PATH_DEFAULT;}

  public static String getLaunchIntervalDefault()        {return LAUNCH_INTERVAL_DEFAULT;}

  public static String        getAmayaEditorPathDefault()   {return           AMAYA_EDITOR_PATH_DEFAULT;}
  public static String     getAudacityEditorPathDefault()   {return        AUDACITY_EDITOR_PATH_DEFAULT;}
  public static String    getMuseScoreEditorPathDefault()   {return       MUSESCORE_EDITOR_PATH_DEFAULT;}
  public static String    getGimpImageEditorPathDefault()   {return            GIMP_EDITOR_PATH_DEFAULT;}
  public static String    getFijiImageEditorPathDefault()   {return            FIJI_EDITOR_PATH_DEFAULT;}
  public static String       getImageJEditorPathDefault()   {return          IMAGEJ_EDITOR_PATH_DEFAULT;}
  public static String  getImageMagickEditorPathDefault()   {return     IMAGEMAGICK_EDITOR_PATH_DEFAULT;}
  public static String          getVlcPlayerPathDefault()   {return             VLC_PLAYER_PATH_DEFAULT;}
  public static String      getProtegePlayerPathDefault()   {return         PROTEGE_PLAYER_PATH_DEFAULT;}
  public static String     getPorteclePlayerPathDefault()   {return        PORTECLE_PLAYER_PATH_DEFAULT;}
  public static String getKeystoreExplorerPlayerPathDefault() {return KEYSTOREEXPLORER_PLAYER_PATH_DEFAULT;}
  public static String        getWiresharkPathDefault()     {return               WIRESHARK_PATH_DEFAULT;}
  
  public static String getAltovaXMLSpyX3dEditorPathDefault() {return ALTOVA_XMLSPY_X3D_EDITOR_PATH_DEFAULT;}
  public static String   getBlenderX3dEditorPathDefault()   {return     BLENDER_X3D_EDITOR_PATH_DEFAULT;}
  public static String getBsContentStudioX3dEditorPathDefault() {return BSCONTENTSTUDIO_X3D_EDITOR_PATH_DEFAULT;}
  public static String     getBvhackerEditorPathDefault()   {return        BVHACKER_EDITOR_PATH_DEFAULT;}
  public static String      getCuraX3dEditorPathDefault()   {return        CURA_X3D_EDITOR_PATH_DEFAULT;}
  public static String   getMeshLabX3dEditorPathDefault()   {return     MESHLAB_X3D_EDITOR_PATH_DEFAULT;}
  public static String  getParaviewX3dEditorPathDefault()   {return    PARAVIEW_X3D_EDITOR_PATH_DEFAULT;}
  public static String getMayaRawkeeX3dEditorPathDefault()  {return  MAYARAWKEE_X3D_EDITOR_PATH_DEFAULT;}
  public static String   getRhino3DX3dEditorPathDefault()   {return     RHINO3D_X3D_EDITOR_PATH_DEFAULT;}
  public static String getPolyTransNuGrafEditorPathDefault(){return POLYTRANSNUGRAF_EDITOR_PATH_DEFAULT;}
  public static String  getSeamlessX3dEditorPathDefault()   {return  SEAMLESS3D_X3D_EDITOR_PATH_DEFAULT;}
  public static String   getSunrizeX3dEditorPathDefault()   {return     SUNRIZE_X3D_EDITOR_PATH_DEFAULT;}
  public static String      getItksnapEditorPathDefault()   {return     ITKSNAP_X3D_EDITOR_PATH_DEFAULT;}
  public static String        getSeg3dEditorPathDefault()   {return       SEG3D_X3D_EDITOR_PATH_DEFAULT;}
  public static String     getSlicer3dEditorPathDefault()   {return    SLICER3D_X3D_EDITOR_PATH_DEFAULT;}
  public static String        getBatikEditorPathDefault()   {return       BATIK_SVG_EDITOR_PATH_DEFAULT;}
  public static String     getInkscapeEditorPathDefault()   {return    INKSCAPE_SVG_EDITOR_PATH_DEFAULT;}
  public static String      getSvgeditEditorPathDefault()   {return    SVG_EDIT_SVG_EDITOR_PATH_DEFAULT;}
  public static String getWhiteDuneX3dEditorPathDefault()   {return   WHITEDUNE_X3D_EDITOR_PATH_DEFAULT;}
  public static String     getWingsX3dEditorPathDefault()   {return     WINGS3D_X3D_EDITOR_PATH_DEFAULT;}
  public static String getUltraEditX3dEditorPathDefault()   {return   ULTRAEDIT_X3D_EDITOR_PATH_DEFAULT;}
  public static String   getOtherAudioEditorNameDefault()   {return     OTHER_AUDIO_EDITOR_NAME_DEFAULT;}
  public static String   getOtherHtml5EditorNameDefault()   {return     OTHER_HTML5_EDITOR_NAME_DEFAULT;}
  public static String   getOtherImageEditorNameDefault()   {return     OTHER_IMAGE_EDITOR_NAME_DEFAULT;}
  public static String   getOtherVideoEditorNameDefault()   {return     OTHER_VIDEO_EDITOR_NAME_DEFAULT;}
  public static String  getOtherVolumeEditorNameDefault()   {return     OTHER_VOLUME_EDITOR_NAME_DEFAULT;}
  public static String  getOtherSemanticWebEditorNameDefault() {return  OTHER_SEMANTICWEB_EDITOR_NAME_DEFAULT;}
  public static String   getOtherSceneEditorNameDefault()   {return     OTHER_X3D_EDITOR_NAME_DEFAULT;}
  public static String   getOtherAudioEditorPathDefault()   {return     OTHER_AUDIO_EDITOR_PATH_DEFAULT;}
  public static String   getOtherHtml5EditorPathDefault()   {return     OTHER_HTML5_EDITOR_PATH_DEFAULT;}
  public static String   getOtherImageEditorPathDefault()   {return     OTHER_IMAGE_EDITOR_PATH_DEFAULT;}
  public static String   getOtherVideoEditorPathDefault()   {return     OTHER_VIDEO_EDITOR_PATH_DEFAULT;}
  public static String  getOtherVolumeEditorPathDefault()   {return     OTHER_VOLUME_EDITOR_PATH_DEFAULT;}
  public static String  getOtherSemanticWebEditorPathDefault() {return  OTHER_SEMANTICWEB_EDITOR_PATH_DEFAULT;}
  public static String   getOtherSceneEditorPathDefault()   {return     OTHER_X3D_EDITOR_PATH_DEFAULT;}
  public static String   getOtherAudioEditorSwitchDefault() {return     OTHER_AUDIO_EDITOR_SWITCH_DEFAULT;}
  public static String   getOtherHtml5EditorSwitchDefault() {return     OTHER_HTML5_EDITOR_SWITCH_DEFAULT;}
  public static String   getOtherImageEditorSwitchDefault() {return     OTHER_IMAGE_EDITOR_SWITCH_DEFAULT;}
  public static String   getOtherVideoEditorSwitchDefault() {return     OTHER_VIDEO_EDITOR_SWITCH_DEFAULT;}
  public static String  getOtherVolumeEditorSwitchDefault() {return     OTHER_VOLUME_EDITOR_SWITCH_DEFAULT;}
  public static String  getOtherSemanticWebEditorSwitchDefault() {return OTHER_SEMANTICWEB_EDITOR_SWITCH_DEFAULT;}
  public static String   getOtherSceneEditorSwitchDefault() {return     OTHER_X3D_EDITOR_SWITCH_DEFAULT;}

  public static void setContactPath              (String path){commonStringSet(CONTACT_EXECUTABLE_PATH_KEY, path);}
  public static void setContactGeoPath           (String path){commonStringSet(CONTACT_GEO_EXECUTABLE_PATH_KEY, path);}
  public static void setFreeWrlPath              (String path){commonStringSet(FREEWRL_EXECUTABLE_PATH_KEY, path);}
  public static void setH3dPath                  (String path){commonStringSet(H3D_EXECUTABLE_PATH_KEY, path);}
  public static void setHeilanPath               (String path){commonStringSet(HEILAN_EXECUTABLE_PATH_KEY, path);}
  public static void setInstantRealityPath       (String path){commonStringSet(INSTANTREALITY_EXECUTABLE_PATH_KEY, path);}
  public static void setOctagaPath               (String path){commonStringSet(OCTAGA_EXECUTABLE_PATH_KEY, path);}
  public static void setSwirlX3DPath             (String path){commonStringSet(SWIRLX3DPLAYER_EXECUTABLE_PATH_KEY, path);}
  public static void setCastleModelViewerPath    (String path){commonStringSet(CASTLEMODELVIEWER_EXECUTABLE_PATH_KEY, path);}
  public static void setVivatyPlayerPath         (String path){commonStringSet(VIVATYPLAYER_EXECUTABLE_PATH_KEY, path);}
  public static void setXj3DPath                 (String path){commonStringSet(XJ3D_EXECUTABLE_PATH_KEY, path);}
  public static void setOtherX3dPlayerName       (String name){commonStringSet(OTHER_X3D_PLAYER_EXECUTABLE_NAME_KEY, name);}
  public static void setOtherX3dPlayerPath       (String path){commonStringSet(OTHER_X3D_PLAYER_EXECUTABLE_PATH_KEY, path);}
  public static void setOtherX3dPlayerSwitch     (String pswitch){commonStringSet(OTHER_X3D_PLAYER_EXECUTABLE_SWITCH_KEY, pswitch);}
  public static void setOtherX3dEditorName       (String name){commonStringSet(OTHER_X3D_EDITOR_EXECUTABLE_NAME_KEY, name);}
  public static void setOtherX3dEditorPath       (String path){commonStringSet(OTHER_X3D_EDITOR_EXECUTABLE_PATH_KEY, path);}
  
  public static void        setAmayaEditorPath    (String path){commonStringSet(         AMAYA_EDITOR_PATH_KEY, path);}
  public static void     setAudacityEditorPath    (String path){commonStringSet(      AUDACITY_EDITOR_PATH_KEY, path);}
  public static void    setMuseScoreEditorPath    (String path){commonStringSet(     MUSESCORE_EDITOR_PATH_KEY, path);}
  public static void    setGimpImageEditorPath    (String path){commonStringSet(          GIMP_EDITOR_PATH_KEY, path);}
  public static void         setFijiEditorPath    (String path){commonStringSet(          FIJI_EDITOR_PATH_KEY, path);}
  public static void       setImageJEditorPath    (String path){commonStringSet(        IMAGEJ_EDITOR_PATH_KEY, path);}
  public static void  setImageMagickEditorPath    (String path){commonStringSet(   IMAGEMAGICK_EDITOR_PATH_KEY, path);}
  public static void          setVlcPlayerPath    (String path){commonStringSet(           VLC_PLAYER_PATH_KEY, path);}
  public static void      setProtegePlayerPath    (String path){commonStringSet(       PROTEGE_PLAYER_PATH_KEY, path);}
  public static void     setPorteclePlayerPath    (String path){commonStringSet(      PORTECLE_PLAYER_PATH_KEY, path);}
  public static void setKeystoreExplorerPlayerPath(String path){commonStringSet(KEYSTOREEXPLORER_PLAYER_PATH_KEY, path);}
  public static void          setWiresharkPath    (String path){commonStringSet(            WIRESHARK_PATH_KEY, path);}
  
  public static void setAltovaXMLSpyX3dEditorPath   (String path){commonStringSet(ALTOVA_XMLSPY_X3D_EDITOR_PATH_KEY, path);}
  public static void   setBlenderX3dEditorPath      (String path){commonStringSet(      BLENDER_X3D_EDITOR_PATH_KEY, path);}
  public static void setBsContentStudioX3dEditorPath(String path){commonStringSet(BSCONTENTSTUDIO_X3D_EDITOR_PATH_KEY, path);}
  public static void     setBvhackerEditorPath      (String path){commonStringSet(         BVHACKER_EDITOR_PATH_KEY, path);}
  public static void      setCuraX3dEditorPath      (String path){commonStringSet(         CURA_X3D_EDITOR_PATH_KEY, path);}
  public static void   setMeshLabX3dEditorPath      (String path){commonStringSet(      MESHLAB_X3D_EDITOR_PATH_KEY, path);}
  public static void  setParaviewX3dEditorPath      (String path){commonStringSet(     PARAVIEW_X3D_EDITOR_PATH_KEY, path);}
  public static void setMayaRawkeeX3dEditorPath     (String path){commonStringSet(   MAYARAWKEE_X3D_EDITOR_PATH_KEY, path);}
  public static void  setRhino3DX3dEditorPath       (String path){commonStringSet(      RHINO3D_X3D_EDITOR_PATH_KEY, path);}
  public static void setPolyTransNuGrafEditorPath   (String path){commonStringSet(  POLYTRANSNUGRAF_EDITOR_PATH_KEY, path);}
  public static void  setSeamlessX3dEditorPath      (String path){commonStringSet(   SEAMLESS3D_X3D_EDITOR_PATH_KEY, path);}
  public static void   setSunrizeX3dEditorPath      (String path){commonStringSet(      SUNRIZE_X3D_EDITOR_PATH_KEY, path);}
  public static void      setItksnapEditorPath      (String path){commonStringSet(   ITKSNAP_VOLUME_EDITOR_PATH_KEY, path);}
  public static void        setSeg3dEditorPath      (String path){commonStringSet(     SEG3D_VOLUME_EDITOR_PATH_KEY, path);}
  public static void     setSlicer3dEditorPath      (String path){commonStringSet(   SLICER3D_VOLUME_EDITOR_PATH_KEY,path);}
  public static void        setBatikEditorPath      (String path){commonStringSet(         BATIK_SVG_EDITOR_PATH_KEY,path);}
  public static void     setInkscapeEditorPath      (String path){commonStringSet(      INKSCAPE_SVG_EDITOR_PATH_KEY,path);}
  public static void      setSvgeditEditorPath      (String path){commonStringSet(      SVG_EDIT_SVG_EDITOR_PATH_KEY,path);}
  public static void setWhiteDuneX3dEditorPath      (String path){commonStringSet(    WHITEDUNE_X3D_EDITOR_PATH_KEY, path);}
  public static void     setWingsX3dEditorPath      (String path){commonStringSet(      WINGS3D_X3D_EDITOR_PATH_KEY, path);}
  public static void setUltraEditX3dEditorPath      (String path){commonStringSet(    ULTRAEDIT_X3D_EDITOR_PATH_KEY, path);}
  
  public static void   setOtherAudioEditorName    (String name){commonStringSet(   OTHER_AUDIO_EDITOR_NAME_KEY, name);}
  public static void   setOtherHtml5EditorName    (String name){commonStringSet(   OTHER_HTML5_EDITOR_NAME_KEY, name);}
  public static void   setOtherImageEditorName    (String name){commonStringSet(   OTHER_IMAGE_EDITOR_NAME_KEY, name);}
  public static void   setOtherVideoEditorName    (String name){commonStringSet(   OTHER_VIDEO_EDITOR_NAME_KEY, name);}
  public static void  setOtherVolumeEditorName    (String name){commonStringSet(  OTHER_VOLUME_EDITOR_NAME_KEY, name);}
  public static void  setOtherSemanticWebEditorName (String name){commonStringSet(  OTHER_SEMANTICWEB_EDITOR_NAME_KEY, name);}
  public static void   setOtherSceneEditorName    (String name){commonStringSet(OTHER_X3D_EDITOR_NAME_KEY, name);}
  
  public static void   setOtherAudioEditorPath    (String path){commonStringSet(   OTHER_AUDIO_EDITOR_PATH_KEY, path);}
  public static void   setOtherHtml5EditorPath    (String path){commonStringSet(   OTHER_HTML5_EDITOR_PATH_KEY, path);}
  public static void   setOtherImageEditorPath    (String path){commonStringSet(   OTHER_IMAGE_EDITOR_PATH_KEY, path);}
  public static void   setOtherVideoEditorPath    (String path){commonStringSet(   OTHER_VIDEO_EDITOR_PATH_KEY, path);}
  public static void  setOtherVolumeEditorPath    (String path){commonStringSet(  OTHER_VOLUME_EDITOR_PATH_KEY, path);}
  public static void setOtherSemanticWebEditorPath (String path){commonStringSet(  OTHER_SEMANTICWEB_EDITOR_PATH_KEY, path);}
  public static void   setOtherSceneEditorPath    (String path){commonStringSet(OTHER_X3D_EDITOR_PATH_KEY, path);}
  public static void setOtherAudioEditorSwitch (String pswitch){commonStringSet(   OTHER_AUDIO_EDITOR_SWITCH_KEY, pswitch);}
  public static void setOtherHtml5EditorSwitch (String pswitch){commonStringSet(   OTHER_HTML5_EDITOR_SWITCH_KEY, pswitch);}
  public static void setOtherImageEditorSwitch (String pswitch){commonStringSet(   OTHER_IMAGE_EDITOR_SWITCH_KEY, pswitch);}
  public static void setOtherVideoEditorSwitch (String pswitch){commonStringSet(   OTHER_VIDEO_EDITOR_SWITCH_KEY, pswitch);}
  public static void setOtherVolumeEditorSwitch(String pswitch){commonStringSet(  OTHER_VOLUME_EDITOR_SWITCH_KEY, pswitch);}
  public static void setOtherSemanticWebEditorSwitch(String pswitch){commonStringSet(  OTHER_SEMANTICWEB_EDITOR_SWITCH_KEY, pswitch);}
  public static void setOtherSceneEditorSwitch (String pswitch){commonStringSet(OTHER_X3D_EDITOR_SWITCH_KEY, pswitch);}

  public static void setContactAutoLaunch           (String autoLaunch){commonStringSet(CONTACT_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void setContactGeoAutoLaunch        (String autoLaunch){commonStringSet(CONTACT_GEO_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void setFreeWrlAutoLaunch           (String autoLaunch){commonStringSet(FREEWRL_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void setH3dAutoLaunch               (String autoLaunch){commonStringSet(H3D_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void setHeilanAutoLaunch            (String autoLaunch){commonStringSet(HEILAN_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void setInstantRealityAutoLaunch    (String autoLaunch){commonStringSet(INSTANTREALITY_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void setOctagaAutoLaunch            (String autoLaunch){commonStringSet(OCTAGA_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void setSwirlx3dAutoLaunch          (String autoLaunch){commonStringSet(SWIRLX3DPLAYER_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void setCastleModelViewerAutoLaunch (String autoLaunch){commonStringSet(CASTLEMODELVIEWER_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void setVivatyAutoLaunch            (String autoLaunch){commonStringSet(VIVATYPLAYER_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void setXj3dAutoLaunch              (String autoLaunch){commonStringSet(XJ3D_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void setOtherX3dPlayerAutoLaunch    (String autoLaunch){commonStringSet(OTHER_X3D_PLAYER_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void setOtherX3dEditorAutoLaunch    (String autoLaunch){commonStringSet(OTHER_X3D_EDITOR_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  
  public static void             setAmayaAutoLaunch (String autoLaunch){commonStringSet(             AMAYA_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void          setAudacityAutoLaunch (String autoLaunch){commonStringSet(          AUDACITY_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void         setMuseScoreAutoLaunch (String autoLaunch){commonStringSet(         MUSESCORE_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void              setGimpAutoLaunch (String autoLaunch){commonStringSet(              GIMP_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void              setFijiAutoLaunch (String autoLaunch){commonStringSet(              FIJI_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void            setImageJAutoLaunch (String autoLaunch){commonStringSet(            IMAGEJ_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void       setImageMagickAutoLaunch (String autoLaunch){commonStringSet(       IMAGEMAGICK_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void               setVlcAutoLaunch (String autoLaunch){commonStringSet(               VLC_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void           setProtegeAutoLaunch (String autoLaunch){commonStringSet(           PROTEGE_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void          setPortecleAutoLaunch (String autoLaunch){commonStringSet(          PORTECLE_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void  setKeystoreExplorerAutoLaunch (String autoLaunch){commonStringSet(  KEYSTOREEXPLORER_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void         setWiresharkAutoLaunch (String autoLaunch){commonStringSet(         WIRESHARK_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  
  public static void      setAltovaXMLSpyAutoLaunch (String autoLaunch){commonStringSet(     ALTOVA_XMLSPY_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void           setBlenderAutoLaunch (String autoLaunch){commonStringSet(           BLENDER_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void   setBsContentStudioAutoLaunch (String autoLaunch){commonStringSet(   BSCONTENTSTUDIO_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void          setBvhackerAutoLaunch (String autoLaunch){commonStringSet(          BVHACKER_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void              setCuraAutoLaunch (String autoLaunch){commonStringSet(              CURA_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void           setMeshLabAutoLaunch (String autoLaunch){commonStringSet(           MESHLAB_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void          setParaviewAutoLaunch (String autoLaunch){commonStringSet(          PARAVIEW_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void        setMayaRawkeeAutoLaunch (String autoLaunch){commonStringSet(        MAYARAWKEE_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void           setRhino3DAutoLaunch (String autoLaunch){commonStringSet(           RHINO3D_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void   setPolyTransNuGrafAutoLaunch (String autoLaunch){commonStringSet(       POLYTRANSNUGRAF_EDITOR_AUTOLAUNCH_KEY, autoLaunch);}
  public static void        setSeamless3dAutoLaunch (String autoLaunch){commonStringSet(        SEAMLESS3D_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void           setSunrizeAutoLaunch (String autoLaunch){commonStringSet(           SUNRIZE_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void           setItksnapAutoLaunch (String autoLaunch){commonStringSet(           ITKSNAP_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void             setSeg3dAutoLaunch (String autoLaunch){commonStringSet(             SEG3D_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void          setSlicer3dAutoLaunch (String autoLaunch){commonStringSet(          SLICER3D_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void             setBatikAutoLaunch (String autoLaunch){commonStringSet(             BATIK_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void          setInkscapeAutoLaunch (String autoLaunch){commonStringSet(          INKSCAPE_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void           setSvgeditAutoLaunch (String autoLaunch){commonStringSet(          SVG_EDIT_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void         setWhiteDuneAutoLaunch (String autoLaunch){commonStringSet(         WHITEDUNE_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void           setWings3dAutoLaunch (String autoLaunch){commonStringSet(           WINGS3D_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void         setUltraEditAutoLaunch (String autoLaunch){commonStringSet(         ULTRAEDIT_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void  setOtherAudioEditorAutoLaunch (String autoLaunch){commonStringSet(OTHER_AUDIO_EDITOR_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void  setOtherHtml5EditorAutoLaunch (String autoLaunch){commonStringSet(OTHER_HTML5_EDITOR_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void  setOtherImageEditorAutoLaunch (String autoLaunch){commonStringSet(OTHER_IMAGE_EDITOR_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void  setOtherVideoEditorAutoLaunch (String autoLaunch){commonStringSet(OTHER_VIDEO_EDITOR_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void  setOtherVolumeEditorAutoLaunch(String autoLaunch){commonStringSet(OTHER_VOLUME_EDITOR_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}
  public static void setOtherSemanticWebEditorAutoLaunch(String autoLaunch){commonStringSet(OTHER_SEMANTICWEB_EDITOR_EXECUTABLE_AUTOLAUNCH_KEY, autoLaunch);}

  public static void  setLaunchInterval             (String path){commonStringSet(LAUNCH_INTERVAL_KEY,path);}

  public static String getContactPath()          {return commonStringGet(CONTACT_EXECUTABLE_PATH_KEY,        CONTACT_EXECUTABLE_PATH_DEFAULT);}
  public static String getContactGeoPath()       {return commonStringGet(CONTACT_GEO_EXECUTABLE_PATH_KEY,    CONTACT_GEO_EXECUTABLE_PATH_DEFAULT);}
  public static String getFreeWrlPath()          {return commonStringGet(FREEWRL_EXECUTABLE_PATH_KEY,        FREEWRL_EXECUTABLE_PATH_DEFAULT);}
  public static String getH3dPath()              {return commonStringGet(H3D_EXECUTABLE_PATH_KEY,            H3D_EXECUTABLE_PATH_DEFAULT);}
  public static String getHeilanPath()           {return commonStringGet(HEILAN_EXECUTABLE_PATH_KEY,         HEILAN_EXECUTABLE_PATH_DEFAULT);}
  public static String getInstantRealityPath()   {return commonStringGet(INSTANTREALITY_EXECUTABLE_PATH_KEY, INSTANTREALITY_EXECUTABLE_PATH_DEFAULT);}
  public static String getOctagaPath()           {return commonStringGet(OCTAGA_EXECUTABLE_PATH_KEY,         OCTAGA_EXECUTABLE_PATH_DEFAULT);}
  public static String getSwirlX3DPath()         {return commonStringGet(SWIRLX3DPLAYER_EXECUTABLE_PATH_KEY, SWIRLX3DPLAYER_EXECUTABLE_PATH_DEFAULT);}
  public static String getCastleModelViewerPath(){return commonStringGet(CASTLEMODELVIEWER_EXECUTABLE_PATH_KEY,    CASTLEMODELVIEWER_EXECUTABLE_PATH_DEFAULT);}
  public static String getVivatyPath()           {return commonStringGet(VIVATYPLAYER_EXECUTABLE_PATH_KEY,   VIVATYPLAYER_EXECUTABLE_PATH_DEFAULT);}
  public static String getXj3DPath()             {return commonStringGet(XJ3D_EXECUTABLE_PATH_KEY,           XJ3D_EXECUTABLE_PATH_DEFAULT);}
  public static String getOtherX3dPlayerName()   {return commonStringGet(OTHER_X3D_PLAYER_EXECUTABLE_NAME_KEY,   OTHER_X3D_PLAYER_EXECUTABLE_NAME_DEFAULT);}
  public static String getOtherX3dPlayerPath()   {return commonStringGet(OTHER_X3D_PLAYER_EXECUTABLE_PATH_KEY,   OTHER_X3D_PLAYER_EXECUTABLE_PATH_DEFAULT);}
  public static String getOtherX3dPlayerSwitch() {return commonStringGet(OTHER_X3D_PLAYER_EXECUTABLE_SWITCH_KEY, OTHER_X3D_PLAYER_EXECUTABLE_SWITCH_DEFAULT);}
  public static String getOtherX3dEditorName()   {return commonStringGet(OTHER_X3D_EDITOR_EXECUTABLE_NAME_KEY,   OTHER_X3D_EDITOR_EXECUTABLE_NAME_DEFAULT);}
  public static String getOtherX3dEditorPath()   {return commonStringGet(OTHER_X3D_EDITOR_EXECUTABLE_PATH_KEY,   OTHER_X3D_EDITOR_EXECUTABLE_PATH_DEFAULT);}
  
  public static String        getAmayaEditorPath()       {return commonStringGet(         AMAYA_EDITOR_PATH_KEY,         AMAYA_EDITOR_PATH_DEFAULT);}
  public static String     getAudacityEditorPath()       {return commonStringGet(      AUDACITY_EDITOR_PATH_KEY,      AUDACITY_EDITOR_PATH_DEFAULT);}
  public static String    getMuseScoreEditorPath()       {return commonStringGet(     MUSESCORE_EDITOR_PATH_KEY,     MUSESCORE_EDITOR_PATH_DEFAULT);}
  public static String    getGimpImageEditorPath()       {return commonStringGet(          GIMP_EDITOR_PATH_KEY,          GIMP_EDITOR_PATH_DEFAULT);}
  public static String    getFijiImageEditorPath()       {return commonStringGet(          FIJI_EDITOR_PATH_KEY,          FIJI_EDITOR_PATH_DEFAULT);}
  public static String       getImageJEditorPath()       {return commonStringGet(        IMAGEJ_EDITOR_PATH_KEY,        IMAGEJ_EDITOR_PATH_DEFAULT);}
  public static String  getImageMagickEditorPath()       {return commonStringGet(   IMAGEMAGICK_EDITOR_PATH_KEY,   IMAGEMAGICK_EDITOR_PATH_DEFAULT);}
  public static String          getVlcPlayerPath()       {return commonStringGet(           VLC_PLAYER_PATH_KEY,           VLC_PLAYER_PATH_DEFAULT);}
  public static String      getProtegePlayerPath()       {return commonStringGet(       PROTEGE_PLAYER_PATH_KEY,       PROTEGE_PLAYER_PATH_DEFAULT);}
  public static String     getPorteclePlayerPath()       {return commonStringGet(      PORTECLE_PLAYER_PATH_KEY,      PORTECLE_PLAYER_PATH_DEFAULT);}
  public static String getKeystoreExplorerPlayerPath()   {return commonStringGet(KEYSTOREEXPLORER_PLAYER_PATH_KEY,KEYSTOREEXPLORER_PLAYER_PATH_DEFAULT);}
  public static String          getWiresharkPath()       {return commonStringGet(            WIRESHARK_PATH_KEY,            WIRESHARK_PATH_DEFAULT);}
  
  public static String getAltovaXMLSpyX3dEditorPath()    {return commonStringGet(ALTOVA_XMLSPY_X3D_EDITOR_PATH_KEY,ALTOVA_XMLSPY_X3D_EDITOR_PATH_DEFAULT);}
  public static String   getBlenderX3dEditorPath()       {return commonStringGet(   BLENDER_X3D_EDITOR_PATH_KEY,   BLENDER_X3D_EDITOR_PATH_DEFAULT);}
  public static String getBsContentStudioX3dEditorPath() {return commonStringGet(BSCONTENTSTUDIO_X3D_EDITOR_PATH_KEY,  BSCONTENTSTUDIO_X3D_EDITOR_PATH_DEFAULT);}
  public static String     getBvhackerEditorPath()       {return commonStringGet(      BVHACKER_EDITOR_PATH_KEY,      BVHACKER_EDITOR_PATH_DEFAULT);}
  public static String      getCuraX3dEditorPath()       {return commonStringGet(      CURA_X3D_EDITOR_PATH_KEY,      CURA_X3D_EDITOR_PATH_DEFAULT);}
  public static String   getMeshLabX3dEditorPath()       {return commonStringGet(   MESHLAB_X3D_EDITOR_PATH_KEY,   MESHLAB_X3D_EDITOR_PATH_DEFAULT);}
  public static String  getParaviewX3dEditorPath()       {return commonStringGet(  PARAVIEW_X3D_EDITOR_PATH_KEY,  PARAVIEW_X3D_EDITOR_PATH_DEFAULT);}
  public static String getMayaRawkeeX3dEditorPath()      {return commonStringGet(MAYARAWKEE_X3D_EDITOR_PATH_KEY,   MAYARAWKEE_X3D_EDITOR_PATH_DEFAULT);}
  public static String   getRhino3DX3dEditorPath()       {return commonStringGet(   RHINO3D_X3D_EDITOR_PATH_KEY,   RHINO3D_X3D_EDITOR_PATH_DEFAULT);}
  public static String getPolyTransNuGrafEditorPath()    {return commonStringGet(POLYTRANSNUGRAF_EDITOR_PATH_KEY, POLYTRANSNUGRAF_EDITOR_PATH_DEFAULT);}
  public static String  getSeamlessX3dEditorPath()       {return commonStringGet(SEAMLESS3D_X3D_EDITOR_PATH_KEY,SEAMLESS3D_X3D_EDITOR_PATH_DEFAULT);}
  public static String   getSunrizeX3dEditorPath()       {return commonStringGet(   SUNRIZE_X3D_EDITOR_PATH_KEY,   SUNRIZE_X3D_EDITOR_PATH_DEFAULT);}
  public static String      getItksnapEditorPath()       {return commonStringGet(ITKSNAP_VOLUME_EDITOR_PATH_KEY,   ITKSNAP_X3D_EDITOR_PATH_DEFAULT);}
  public static String        getSeg3dEditorPath()       {return commonStringGet(  SEG3D_VOLUME_EDITOR_PATH_KEY,     SEG3D_X3D_EDITOR_PATH_DEFAULT);}
  public static String     getSlicer3dEditorPath()       {return commonStringGet(SLICER3D_VOLUME_EDITOR_PATH_KEY, SLICER3D_X3D_EDITOR_PATH_DEFAULT);}
  public static String         getBatikEditorPath()      {return commonStringGet(     BATIK_SVG_EDITOR_PATH_KEY,    BATIK_SVG_EDITOR_PATH_DEFAULT);}
  public static String      getInkscapeEditorPath()      {return commonStringGet(  INKSCAPE_SVG_EDITOR_PATH_KEY, INKSCAPE_SVG_EDITOR_PATH_DEFAULT);}
  public static String      getSvgeditEditorPath()       {return commonStringGet(  SVG_EDIT_SVG_EDITOR_PATH_KEY, SVG_EDIT_SVG_EDITOR_PATH_DEFAULT);}
  public static String getWhiteDuneX3dEditorPath()       {return commonStringGet( WHITEDUNE_X3D_EDITOR_PATH_KEY, WHITEDUNE_X3D_EDITOR_PATH_DEFAULT);}
  public static String     getWingsX3dEditorPath()       {return commonStringGet(   WINGS3D_X3D_EDITOR_PATH_KEY,   WINGS3D_X3D_EDITOR_PATH_DEFAULT);}
  public static String getUltraEditX3dEditorPath()       {return commonStringGet( ULTRAEDIT_X3D_EDITOR_PATH_KEY, ULTRAEDIT_X3D_EDITOR_PATH_DEFAULT);}
  public static String   getOtherAudioEditorPath()       {return commonStringGet(   OTHER_AUDIO_EDITOR_PATH_KEY,   OTHER_AUDIO_EDITOR_PATH_DEFAULT);}
  public static String   getOtherHtml5EditorPath()       {return commonStringGet(   OTHER_HTML5_EDITOR_PATH_KEY,   OTHER_HTML5_EDITOR_PATH_DEFAULT);}
  public static String   getOtherImageEditorPath()       {return commonStringGet(   OTHER_IMAGE_EDITOR_PATH_KEY,   OTHER_IMAGE_EDITOR_PATH_DEFAULT);}
  public static String   getOtherVideoEditorPath()       {return commonStringGet(   OTHER_VIDEO_EDITOR_PATH_KEY,   OTHER_VIDEO_EDITOR_PATH_DEFAULT);}
  public static String   getOtherVolumeEditorPath()      {return commonStringGet(  OTHER_VOLUME_EDITOR_PATH_KEY,  OTHER_VOLUME_EDITOR_PATH_DEFAULT);}
  public static String   getOtherSemanticWebEditorPath() {return commonStringGet(OTHER_SEMANTICWEB_EDITOR_PATH_KEY,  OTHER_SEMANTICWEB_EDITOR_PATH_DEFAULT);}
  public static String   getOtherAudioEditorName()       {return commonStringGet(   OTHER_AUDIO_EDITOR_NAME_KEY,   OTHER_AUDIO_EDITOR_NAME_DEFAULT);}
  public static String   getOtherHtml5EditorName()       {return commonStringGet(   OTHER_HTML5_EDITOR_NAME_KEY,   OTHER_HTML5_EDITOR_NAME_DEFAULT);}
  public static String   getOtherImageEditorName()       {return commonStringGet(   OTHER_IMAGE_EDITOR_NAME_KEY,   OTHER_IMAGE_EDITOR_NAME_DEFAULT);}
  public static String   getOtherVideoEditorName()       {return commonStringGet(   OTHER_VIDEO_EDITOR_NAME_KEY,   OTHER_VIDEO_EDITOR_NAME_DEFAULT);}
  public static String   getOtherVolumeEditorName()      {return commonStringGet(  OTHER_VOLUME_EDITOR_NAME_KEY,  OTHER_VOLUME_EDITOR_NAME_DEFAULT);}
  public static String   getOtherSemanticWebEditorName() {return commonStringGet(OTHER_SEMANTICWEB_EDITOR_NAME_KEY,OTHER_SEMANTICWEB_EDITOR_NAME_DEFAULT);}

  public static String isContactAutoLaunch()        {return commonStringGet(CONTACT_EXECUTABLE_AUTOLAUNCH_KEY,               AUTOLAUNCH_DEFAULT);}
  public static String isContactGeoAutoLaunch()     {return commonStringGet(CONTACT_GEO_EXECUTABLE_AUTOLAUNCH_KEY,           AUTOLAUNCH_DEFAULT);}
  public static String isFreeWrlAutoLaunch()        {return commonStringGet(FREEWRL_EXECUTABLE_AUTOLAUNCH_KEY,               AUTOLAUNCH_DEFAULT);}
  public static String isH3dAutoLaunch()            {return commonStringGet(H3D_EXECUTABLE_AUTOLAUNCH_KEY,                   AUTOLAUNCH_DEFAULT);}
  public static String isHeilanAutoLaunch()         {return commonStringGet(HEILAN_EXECUTABLE_AUTOLAUNCH_KEY,         HEILAN_AUTOLAUNCH_DEFAULT);}
  public static String isInstantRealityAutoLaunch() {return commonStringGet(INSTANTREALITY_EXECUTABLE_AUTOLAUNCH_KEY,        AUTOLAUNCH_DEFAULT);}
  public static String isOctagaAutoLaunch()         {return commonStringGet(OCTAGA_EXECUTABLE_AUTOLAUNCH_KEY,                AUTOLAUNCH_DEFAULT);}
  public static String isSwirlX3DAutoLaunch()       {return commonStringGet(SWIRLX3DPLAYER_EXECUTABLE_AUTOLAUNCH_KEY,        AUTOLAUNCH_DEFAULT);}
  public static String isCastleModelViewerAutoLaunch()    {return commonStringGet(CASTLEMODELVIEWER_EXECUTABLE_AUTOLAUNCH_KEY,           AUTOLAUNCH_DEFAULT);}
  public static String isVivatyAutoLaunch()         {return commonStringGet(VIVATYPLAYER_EXECUTABLE_AUTOLAUNCH_KEY,          AUTOLAUNCH_DEFAULT);}
  public static String isXj3dAutoLaunch()           {return commonStringGet(XJ3D_EXECUTABLE_AUTOLAUNCH_KEY,                  AUTOLAUNCH_DEFAULT);}
  public static String isOtherX3dPlayerAutoLaunch() {return commonStringGet(OTHER_X3D_PLAYER_EXECUTABLE_AUTOLAUNCH_KEY,      AUTOLAUNCH_DEFAULT);}
  public static String isOtherX3dEditorAutoLaunch() {return commonStringGet(OTHER_X3D_EDITOR_EXECUTABLE_AUTOLAUNCH_KEY,      AUTOLAUNCH_DEFAULT);}
  
  public static String             isAmayaAutoLaunch() {return commonStringGet(              AMAYA_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String          isAudacityAutoLaunch() {return commonStringGet(           AUDACITY_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String         isMuseScoreAutoLaunch() {return commonStringGet(          MUSESCORE_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String              isGimpAutoLaunch() {return commonStringGet(               GIMP_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String              isFijiAutoLaunch() {return commonStringGet(               FIJI_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String            isImageJAutoLaunch() {return commonStringGet(             IMAGEJ_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String       isImageMagickAutoLaunch() {return commonStringGet(        IMAGEMAGICK_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String               isVlcAutoLaunch() {return commonStringGet(                VLC_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String           isProtegeAutoLaunch() {return commonStringGet(            PROTEGE_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String          isPortecleAutoLaunch() {return commonStringGet(           PORTECLE_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String  isKeystoreExplorerAutoLaunch() {return commonStringGet(   KEYSTOREEXPLORER_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String         isWiresharkAutoLaunch() {return commonStringGet(          WIRESHARK_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  
  public static String      isAltovaXMLSpyAutoLaunch() {return commonStringGet(      ALTOVA_XMLSPY_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String           isBlenderAutoLaunch() {return commonStringGet(            BLENDER_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String   isBsContentStudioAutoLaunch() {return commonStringGet(    BSCONTENTSTUDIO_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String          isBvhackerAutoLaunch() {return commonStringGet(           BVHACKER_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String              isCuraAutoLaunch() {return commonStringGet(               CURA_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String           isMeshLabAutoLaunch() {return commonStringGet(            MESHLAB_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String          isParaviewAutoLaunch() {return commonStringGet(           PARAVIEW_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String        isMayaRawkeeAutoLaunch() {return commonStringGet(         MAYARAWKEE_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String           isRhino3DAutoLaunch() {return commonStringGet(            RHINO3D_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String   isPolyTransNuGrafAutoLaunch() {return commonStringGet(        POLYTRANSNUGRAF_EDITOR_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String        isSeamless3dAutoLaunch() {return commonStringGet(         SEAMLESS3D_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String           isSunrizeAutoLaunch() {return commonStringGet(            SUNRIZE_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String           isItksnapAutoLaunch() {return commonStringGet(            ITKSNAP_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String             isSeg3dAutoLaunch() {return commonStringGet(              SEG3D_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String          isSlicer3dAutoLaunch() {return commonStringGet(           SLICER3D_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String             isBatikAutoLaunch() {return commonStringGet(              BATIK_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String          isInkscapeAutoLaunch() {return commonStringGet(           INKSCAPE_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String           isSvgeditAutoLaunch() {return commonStringGet(           SVG_EDIT_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String         isWhiteDuneAutoLaunch() {return commonStringGet(          WHITEDUNE_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String           isWings3dAutoLaunch() {return commonStringGet(            WINGS3D_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String         isUltraEditAutoLaunch() {return commonStringGet(          ULTRAEDIT_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String  isOtherAudioEditorAutoLaunch() {return commonStringGet( OTHER_AUDIO_EDITOR_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String  isOtherHtml5EditorAutoLaunch() {return commonStringGet( OTHER_HTML5_EDITOR_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String  isOtherImageEditorAutoLaunch() {return commonStringGet( OTHER_IMAGE_EDITOR_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String  isOtherVideoEditorAutoLaunch() {return commonStringGet( OTHER_VIDEO_EDITOR_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String isOtherVolumeEditorAutoLaunch() {return commonStringGet(OTHER_VOLUME_EDITOR_EXECUTABLE_AUTOLAUNCH_KEY,         AUTOLAUNCH_DEFAULT);}
  public static String isOtherSemanticWebEditorAutoLaunch() {return commonStringGet(OTHER_SEMANTICWEB_EDITOR_EXECUTABLE_AUTOLAUNCH_KEY,AUTOLAUNCH_DEFAULT);}

  public static String getLaunchInterval()             {return commonStringGet(LAUNCH_INTERVAL_KEY,                  LAUNCH_INTERVAL_DEFAULT);}
  public static Long   getLaunchIntervalMilliseconds() {return Long.parseLong(getLaunchInterval()) * 1000l;} // convert seconds to msec

  public static String getKeystorePath()         {return commonStringGet(KEYSTORE_PATH_KEY, "");} // user must initially define their keystore path

  public static void resetContactPath()          {commonReset(CONTACT_EXECUTABLE_PATH_KEY);}
  public static void resetContactGeoPath()       {commonReset(CONTACT_GEO_EXECUTABLE_PATH_KEY);}
  public static void resetFreeWrlPath()          {commonReset(FREEWRL_EXECUTABLE_PATH_KEY);}
  public static void resetH3dPath()              {commonReset(H3D_EXECUTABLE_PATH_KEY);}
  public static void resetHeilanPath()           {commonReset(HEILAN_EXECUTABLE_PATH_KEY);}
  public static void resetInstantRealityPath()   {commonReset(INSTANTREALITY_EXECUTABLE_PATH_KEY);}
  public static void resetOctagaPath()           {commonReset(OCTAGA_EXECUTABLE_PATH_KEY);}
  public static void resetSwirlX3DPath()         {commonReset(SWIRLX3DPLAYER_EXECUTABLE_PATH_KEY);}
  public static void resetCastleModelViewerPath(){commonReset(CASTLEMODELVIEWER_EXECUTABLE_PATH_KEY);}
  public static void resetVivatyPath()           {commonReset(VIVATYPLAYER_EXECUTABLE_PATH_KEY);}
  public static void resetXj3DPath()             {commonReset(XJ3D_EXECUTABLE_PATH_KEY);}
  public static void resetOtherX3dPlayerPath()   {commonReset(OTHER_X3D_PLAYER_EXECUTABLE_PATH_KEY);}
  public static void resetOtherX3dPlayerName()   {commonReset(OTHER_X3D_PLAYER_EXECUTABLE_NAME_KEY);}
  public static void resetOtherX3dPlayerSwitch() {commonReset(OTHER_X3D_PLAYER_EXECUTABLE_SWITCH_KEY);}
  public static void resetOtherX3dEditorPath()   {commonReset(OTHER_X3D_EDITOR_EXECUTABLE_PATH_KEY);}
  public static void resetOtherX3dEditorName()   {commonReset(OTHER_X3D_EDITOR_EXECUTABLE_NAME_KEY);}

  public static void resetLaunchInterval()       {commonReset(LAUNCH_INTERVAL_KEY);}
  
  public static void        resetAmayaEditorPath()     {commonReset(          AMAYA_EDITOR_PATH_KEY);}
  public static void     resetAudacityEditorPath()     {commonReset(       AUDACITY_EDITOR_PATH_KEY);}
  public static void    resetMuseScoreEditorPath()     {commonReset(      MUSESCORE_EDITOR_PATH_KEY);}
  public static void         resetGimpEditorPath()     {commonReset(           GIMP_EDITOR_PATH_KEY);}
  public static void         resetFijiEditorPath()     {commonReset(           FIJI_EDITOR_PATH_KEY);}
  public static void       resetImageJEditorPath()     {commonReset(         IMAGEJ_EDITOR_PATH_KEY);}
  public static void  resetImageMagickEditorPath()     {commonReset(    IMAGEMAGICK_EDITOR_PATH_KEY);}
  public static void          resetVlcPlayerPath()     {commonReset(            VLC_PLAYER_PATH_KEY);}
  public static void      resetProtegePlayerPath()     {commonReset(        PROTEGE_PLAYER_PATH_KEY);}
  public static void     resetPorteclePlayerPath()     {commonReset(       PORTECLE_PLAYER_PATH_KEY);}
  public static void resetKeystoreExplorerPlayerPath() {commonReset(KEYSTOREEXPLORER_PLAYER_PATH_KEY);}
  public static void          resetWiresharkPath()     {commonReset(              WIRESHARK_PATH_KEY);}
  
  public static void   resetAltovaXMLSpyX3dEditorPath() {commonReset(ALTOVA_XMLSPY_X3D_EDITOR_PATH_KEY);}
  public static void   resetBlenderX3dEditorPath()      {commonReset(    BLENDER_X3D_EDITOR_PATH_KEY);}
  public static void resetBsContentStudioX3dEditorPath(){commonReset(BSCONTENTSTUDIO_X3D_EDITOR_PATH_KEY);}
  public static void     resetBvhackerEditorPath() {commonReset(       BVHACKER_EDITOR_PATH_KEY);}
  public static void      resetCuraX3dEditorPath() {commonReset(       CURA_X3D_EDITOR_PATH_KEY);}
  public static void   resetMeshLabX3dEditorPath() {commonReset(    MESHLAB_X3D_EDITOR_PATH_KEY);}
  public static void  resetParaviewX3dEditorPath() {commonReset(   PARAVIEW_X3D_EDITOR_PATH_KEY);}
  public static void resetMayaRawkeeX3dEditorPath(){commonReset( MAYARAWKEE_X3D_EDITOR_PATH_KEY);}
  public static void   resetRhino3DX3dEditorPath() {commonReset(    RHINO3D_X3D_EDITOR_PATH_KEY);}
  public static void    resetPolyTransNuGrafPath() {commonReset(POLYTRANSNUGRAF_EDITOR_PATH_KEY);}
  public static void  resetSeamlessX3dEditorPath() {commonReset( SEAMLESS3D_X3D_EDITOR_PATH_KEY);}
  public static void   resetSunrizeX3dEditorPath() {commonReset(    SUNRIZE_X3D_EDITOR_PATH_KEY);}
  public static void      resetItksnapEditorPath() {commonReset( ITKSNAP_VOLUME_EDITOR_PATH_KEY);}
  public static void        resetSeg3dEditorPath() {commonReset(   SEG3D_VOLUME_EDITOR_PATH_KEY);}
  public static void     resetSlicer3dEditorPath() {commonReset(SLICER3D_VOLUME_EDITOR_PATH_KEY);}
  public static void        resetBatikEditorPath() {commonReset(BATIK_SVG_EDITOR_PATH_KEY);}
  public static void     resetInkscapeEditorPath() {commonReset(INKSCAPE_SVG_EDITOR_PATH_KEY);}
  public static void      resetSvgeditEditorPath() {commonReset(SVG_EDIT_SVG_EDITOR_PATH_KEY);}
  public static void resetWhiteDuneX3dEditorPath() {commonReset(  WHITEDUNE_X3D_EDITOR_PATH_KEY);}
  public static void     resetWingsX3dEditorPath() {commonReset(    WINGS3D_X3D_EDITOR_PATH_KEY);}
  public static void resetUltraEditX3dEditorPath() {commonReset(  ULTRAEDIT_X3D_EDITOR_PATH_KEY);}
  public static void   resetOtherAudioEditorPath() {commonReset(    OTHER_AUDIO_EDITOR_PATH_KEY);}
  public static void   resetOtherHtml5EditorPath() {commonReset(    OTHER_HTML5_EDITOR_PATH_KEY);}
  public static void   resetOtherImageEditorPath() {commonReset(    OTHER_IMAGE_EDITOR_PATH_KEY);}
  public static void   resetOtherVideoEditorPath() {commonReset(    OTHER_VIDEO_EDITOR_PATH_KEY);}
  public static void  resetOtherVolumeEditorPath() {commonReset(   OTHER_VOLUME_EDITOR_PATH_KEY);}
  public static void  resetOtherSemanticWebEditorPath() {commonReset(OTHER_SEMANTICWEB_EDITOR_PATH_KEY);}
  public static void   resetOtherAudioEditorName() {commonReset(    OTHER_AUDIO_EDITOR_NAME_KEY);}
  public static void   resetOtherHtml5EditorName() {commonReset(    OTHER_HTML5_EDITOR_NAME_KEY);}
  public static void   resetOtherImageEditorName() {commonReset(    OTHER_IMAGE_EDITOR_NAME_KEY);}
  public static void   resetOtherVideoEditorName() {commonReset(    OTHER_VIDEO_EDITOR_NAME_KEY);}
  public static void  resetOtherVolumeEditorName() {commonReset(   OTHER_VOLUME_EDITOR_NAME_KEY);}
  public static void  resetOtherSemanticWebEditorName() {commonReset(   OTHER_SEMANTICWEB_EDITOR_NAME_KEY);}
 
  private static void commonStringSet(String key, String val)
  {
    Preferences prefs = NbPreferences.forModule(X3dEditUserPreferences.class);
    prefs.put(key,val);  
  }
  private static void commonBooleanSet(String key, boolean tf)
  {
    Preferences prefs = NbPreferences.forModule(X3dEditUserPreferences.class);
    prefs.putBoolean(key,tf);      
  }
  public static String commonStringGet(String key, String defaultValue)
  {
    Preferences prefs = NbPreferences.forModule(X3dEditUserPreferences.class);
    return prefs.get(key, defaultValue);
  } 
  public static boolean commonBooleanGet(String key, boolean defaultValue)
  {
    Preferences prefs = NbPreferences.forModule(X3dEditUserPreferences.class);
    return prefs.getBoolean(key, defaultValue);   
  }
  public static void commonReset(String key)
  {
    Preferences prefs = NbPreferences.forModule(X3dEditUserPreferences.class);
    prefs.remove(key);
  }
  
  public static String CERTIFICATE_SERIALNUMBER_KEY     = "CERTIFICATE_SERIALNUMBER";
  public static long   CERTIFICATE_SERIALNUMBER_DEFAULT = (new Date().getTime()/(1000*60*60)) * 10L;  // date, no time, 0 trail
  
  public static long getLastCertificateSerialNumber()
  {
     Preferences prefs = NbPreferences.forModule(X3dEditUserPreferences.class);
     return prefs.getLong(CERTIFICATE_SERIALNUMBER_KEY, CERTIFICATE_SERIALNUMBER_DEFAULT);
  }
  
  public static void setLastCertificateSerialNumber(long val)
  {
     Preferences prefs = NbPreferences.forModule(X3dEditUserPreferences.class);
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

  public static void setCadFilterX3dVersion(String s)                { commonStringSet(CADFILTER_KEY_VERSION,s);}
  public static void setCadFilterTriangleCount(boolean tf)           {commonBooleanSet(CADFILTER_KEY_TRIANGLE_COUNT,tf);}
  public static void setCadFilterLogLevel(String s)                  { commonStringSet(CADFILTER_KEY_LOG_LEVEL,s);}
  public static void setCadFilterEmbedProto(boolean tf)              {commonBooleanSet(CADFILTER_KEY_EMBED_PROTO,tf);}
  public static void setCadFilterBinaryCompression(String s)         { commonStringSet(CADFILTER_KEY_BIN_COMPRESS,s);}
  public static void setCadFilterMinimumProfile(boolean tf)          {commonBooleanSet(CADFILTER_KEY_MIN_PROFILE,tf);}
  public static void setCadFilterAppearanceFilter(boolean tf)        {commonBooleanSet(CADFILTER_KEY_APPEARANCE_FILTER,tf);}
  public static void setCadFilterIdentityFilter(boolean tf)          {commonBooleanSet(CADFILTER_KEY_IDENTITY_FILTER,tf);}
  public static void setCadFilterCadFiltersRB(boolean tf)            {commonBooleanSet(CADFILTER_KEY_CAD_FILTERS,tf);}
  public static void setCadFilterAbsScaleFactor(String s)            { commonStringSet(CADFILTER_KEY_ABS_SCALE_FACTOR,s);}
  public static void setCadFilterAddBoundingBoxes(boolean tf)        {commonBooleanSet(CADFILTER_KEY_BOUNDING_BOXES,tf);}
  public static void setCadFilterIFStoITS(boolean tf)                {commonBooleanSet(CADFILTER_KEY_IFACE_TO_ITRIANGLE,tf);}
  public static void setCadFilterFloatingPointQuantization(String s) { commonStringSet(CADFILTER_KEY_FLOAT_QUANT,s);}
  public static void setCadFilterCenterFilter(boolean tf)            {commonBooleanSet(CADFILTER_KEY_CENTER,tf);}
  public static void setCadFilterIFStoTS(boolean tf)                 {commonBooleanSet(CADFILTER_KEY_IFACE_TO_TRIANGLE,tf);}
  public static void setCadFilterCombineAppearances(boolean tf)      {commonBooleanSet(CADFILTER_KEY_COMBINE_APPEARANCES,tf);}
  public static void setCadFilterCombineShapes(boolean tf)           {commonBooleanSet(CADFILTER_KEY_COMBINE_SHAPES,tf);}
  public static void setCadFilterIndexFilter(boolean tf)             {commonBooleanSet(CADFILTER_KEY_INDEX,tf);}
  public static void setCadFilterDefuseImageTexture(boolean tf)      {commonBooleanSet(CADFILTER_KEY_DEFUSE_IMAGE_TEXTURE,tf);}
  public static void setCadFilterMaterialFilter(boolean tf)          {commonBooleanSet(CADFILTER_KEY_MATERIAL,tf);}
  public static void setCadFilterModifyViewpoint(boolean tf)         {commonBooleanSet(CADFILTER_KEY_MOD_VIEWPOINT,tf);}
  public static void setCadFilterFlattenTransforms(boolean tf)       {commonBooleanSet(CADFILTER_KEY_FLATTEN_TRANSFORM,tf);}
  public static void setCadFilterFlattenTextureTransforms(boolean tf){commonBooleanSet(CADFILTER_KEY_FLATTEN_TEXTURETRANSFORM,tf);}
  public static void setCadFilterFlattenSelectable(boolean tf)       {commonBooleanSet(CADFILTER_KEY_FLATTEN_SELECTABLE,tf);}
  public static void setCadFilterDefNameShortened(boolean tf)        {commonBooleanSet(CADFILTER_KEY_SHORTEN_DEF,tf);}
  public static void setCadFilterGenerateNormals(boolean tf)         {commonBooleanSet(CADFILTER_KEY_GEN_NORMALS,tf);}
  public static void setCadFilterTriangulationFilter(boolean tf)     {commonBooleanSet(CADFILTER_KEY_TRIANGULATION,tf);}
  public static void setCadFilterReindex(boolean tf)                 {commonBooleanSet(CADFILTER_KEY_REINDEX,tf);}
  public static void setCadFilterDebug(boolean tf)                   {commonBooleanSet(CADFILTER_KEY_DEBUG,tf);}
       
  public static String  getCadFilterX3dVersion()                {return  commonStringGet(CADFILTER_KEY_VERSION,             CADFILTER_DEFAULT_VERSION);}
  public static boolean getCadFilterTriangleCount()             {return commonBooleanGet(CADFILTER_KEY_TRIANGLE_COUNT,      CADFILTER_DEFAULT_TRIANGLE_COUNT);}
  public static String  getCadFilterLogLevel()                  {return  commonStringGet(CADFILTER_KEY_LOG_LEVEL,           CADFILTER_DEFAULT_LOG_LEVEL);}
  public static boolean getCadFilterEmbedProto()                {return commonBooleanGet(CADFILTER_KEY_EMBED_PROTO,         CADFILTER_DEFAULT_EMBED_PROTO);}
  public static String  getCadFilterBinaryCompressionMethod()   {return  commonStringGet(CADFILTER_KEY_BIN_COMPRESS,        CADFILTER_DEFAULT_BIN_COMPRESS);}
  public static boolean getCadFilterMinimumProfile()            {return commonBooleanGet(CADFILTER_KEY_MIN_PROFILE,         CADFILTER_DEFAULT_MIN_PROFILE);}
  public static boolean getCadFilterAppearanceFilter()            {return commonBooleanGet(CADFILTER_KEY_IDENTITY_FILTER,   CADFILTER_DEFAULT_APPEARANCE_FILTER);}
  public static boolean getCadFilterIdentityFilter()            {return commonBooleanGet(CADFILTER_KEY_IDENTITY_FILTER,     CADFILTER_DEFAULT_IDENTITY_FILTER);}
  public static boolean getCadFiltersEnabledRadioButton()       {return commonBooleanGet(CADFILTER_KEY_CAD_FILTERS,         CADFILTER_DEFAULT_CAD_FILTERS);}
  public static String  getCadFilterAbsScaleFactor()            {return  commonStringGet(CADFILTER_KEY_ABS_SCALE_FACTOR,    CADFILTER_DEFAULT_ABS_SCALE_FACTOR);}
  public static boolean getCadFilterAddBoundingBoxes()          {return commonBooleanGet(CADFILTER_KEY_BOUNDING_BOXES,      CADFILTER_DEFAULT_BOUNDING_BOXES);}
  public static boolean getCadFilterIFStoITS()                  {return commonBooleanGet(CADFILTER_KEY_IFACE_TO_ITRIANGLE,  CADFILTER_DEFAULT_IFACE_TO_ITRIANGLE);}
  public static String  getCadFilterFloatingPointQuantization() {return  commonStringGet(CADFILTER_KEY_FLOAT_QUANT,         CADFILTER_DEFAULT_FLOAT_QUANT);}
  public static boolean getCadFilterCenterFilter()              {return commonBooleanGet(CADFILTER_KEY_CENTER,              CADFILTER_DEFAULT_CENTER);}
  public static boolean getCadFilterIFStoTS()                   {return commonBooleanGet(CADFILTER_KEY_IFACE_TO_TRIANGLE,   CADFILTER_DEFAULT_IFACE_TO_TRIANGLE);}
  public static boolean getCadFilterCombineAppearances()             {return commonBooleanGet(CADFILTER_KEY_COMBINE_SHAPES, CADFILTER_DEFAULT_COMBINE_APPEARANCES);}
  public static boolean getCadFilterCombineShapes()             {return commonBooleanGet(CADFILTER_KEY_COMBINE_SHAPES,      CADFILTER_DEFAULT_COMBINE_SHAPES);}
  public static boolean getCadFilterIndexFilter()               {return commonBooleanGet(CADFILTER_KEY_INDEX,               CADFILTER_DEFAULT_INDEX);}
  public static boolean getCadFilterDefuseImageTexture()        {return commonBooleanGet(CADFILTER_KEY_DEFUSE_IMAGE_TEXTURE,CADFILTER_DEFAULT_DEFUSE_IMAGE_TEXTURE);}
  public static boolean getCadFilterMaterialFilter()            {return commonBooleanGet(CADFILTER_KEY_MOD_VIEWPOINT,       CADFILTER_DEFAULT_MATERIAL);}
  public static boolean getCadFilterModifyViewpoint()           {return commonBooleanGet(CADFILTER_KEY_MOD_VIEWPOINT,       CADFILTER_DEFAULT_MOD_VIEWPOINT);}
  public static boolean getCadFilterFlattenTransforms()         {return commonBooleanGet(CADFILTER_KEY_FLATTEN_TRANSFORM,   CADFILTER_DEFAULT_FLATTEN_TRANSFORM);}
  public static boolean getCadFilterFlattenTextureTransforms()  {return commonBooleanGet(CADFILTER_KEY_FLATTEN_TEXTURETRANSFORM,CADFILTER_DEFAULT_FLATTEN_TEXTURETRANSFORM);}
  public static boolean getCadFilterFlattenSelectable()         {return commonBooleanGet(CADFILTER_KEY_FLATTEN_SELECTABLE,  CADFILTER_DEFAULT_FLATTEN_SELECTABLE);}
  public static boolean getCadFilterDefNameShortened()          {return commonBooleanGet(CADFILTER_KEY_SHORTEN_DEF,         CADFILTER_DEFAULT_SHORTEN_DEF);}
  public static boolean getCadFilterGenerateNormals()           {return commonBooleanGet(CADFILTER_KEY_GEN_NORMALS,         CADFILTER_DEFAULT_GEN_NORMALS);}
  public static boolean getCadFilterTriangulationFilter()       {return commonBooleanGet(CADFILTER_KEY_TRIANGULATION,       CADFILTER_DEFAULT_TRIANGULATION);}
  public static boolean getCadFilterReIndex()                   {return commonBooleanGet(CADFILTER_KEY_REINDEX,             CADFILTER_DEFAULT_REINDEX);}
  public static boolean getCadFilterDebug()                     {return commonBooleanGet(CADFILTER_KEY_DEBUG,               CADFILTER_DEFAULT_DEBUG);}
  
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
  
  public static String getDISaddress(String dflt)  {return commonStringGet(DIS_ADDRESS_KEY, dflt);}
  public static String getDISport(String dflt)     {return commonStringGet(DIS_PORT_KEY,    dflt);}
  public static String getDISappID(String dflt)    {return commonStringGet(DIS_APPID_KEY,   dflt);}
  public static String getDISentityID(String dflt) {return commonStringGet(DIS_ENTITYID_KEY,dflt);}
  public static String getDISsiteID(String dflt)   {return commonStringGet(DIS_SITEID_KEY,  dflt);}
  public static String getDIStranslationScaleX(String dflt)   {return commonStringGet(DIS_SCALEX_KEY, dflt);}
  public static String getDIStranslationScaleY(String dflt)   {return commonStringGet(DIS_SCALEY_KEY, dflt);}
  public static String getDIStranslationScaleZ(String dflt)   {return commonStringGet(DIS_SCALEZ_KEY, dflt);}
  
  public static void setDISaddress(String val)  {commonStringSet(DIS_ADDRESS_KEY, val);}
  public static void setDISport(String val)     {commonStringSet(DIS_PORT_KEY,    val);}
  public static void setDISappID(String val)    {commonStringSet(DIS_APPID_KEY,   val);}
  public static void setDISentityID(String val) {commonStringSet(DIS_ENTITYID_KEY,val);}
  public static void setDISsiteID(String val)   {commonStringSet(DIS_SITEID_KEY,  val);}
  public static void setDIStranslationScaleX(String val) {commonStringSet(DIS_SCALEX_KEY, val);}
  public static void setDIStranslationScaleY(String val) {commonStringSet(DIS_SCALEY_KEY, val);}
  public static void setDIStranslationScaleZ(String val) {commonStringSet(DIS_SCALEZ_KEY, val);}
}
