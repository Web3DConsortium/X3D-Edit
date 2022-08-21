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
LOSS OF USE, DATA, OR PROFITS;OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.
*/
package org.web3d.x3d.types;

/**
 * X3DSchemaData.java
 * Created on August 17, 2007, 9:26 AM
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey, Don Brutzman
 * @version $Id$
 */

public interface X3DSchemaData
{
  // XML header
  String  XML_HEADER = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>";
  // X3D doctypes
  String  DOCTYPE_4_0 = "<!DOCTYPE X3D PUBLIC \"ISO//Web3D//DTD X3D 4.0//EN\" \"https://www.web3d.org/specifications/x3d-4.0.dtd\">";
  String  DOCTYPE_3_3 = "<!DOCTYPE X3D PUBLIC \"ISO//Web3D//DTD X3D 3.3//EN\" \"https://www.web3d.org/specifications/x3d-3.3.dtd\">";
  String  DOCTYPE_3_2 = "<!DOCTYPE X3D PUBLIC \"ISO//Web3D//DTD X3D 3.2//EN\" \"https://www.web3d.org/specifications/x3d-3.2.dtd\">";
  String  DOCTYPE_3_1 = "<!DOCTYPE X3D PUBLIC \"ISO//Web3D//DTD X3D 3.1//EN\" \"https://www.web3d.org/specifications/x3d-3.1.dtd\">";
  String  DOCTYPE_3_0 = "<!DOCTYPE X3D PUBLIC \"ISO//Web3D//DTD X3D 3.0//EN\" \"https://www.web3d.org/specifications/x3d-3.0.dtd\">";

  String  DOCTYPE_ELNAME                = "DOCTYPE";
  String  DOCTYPE_ATTR_VERSION_NAME     = "version";
  boolean DOCTYPE_ATTR_VERSION_REQD     = true;
  String  DOCTYPE_ATTR_VERSION_DFLT     = "3.3";

  // XML comment
  String  COMMENT_ELNAME   = "COMMENT"; //XML comment"; this gets remapped

  // Root X3D element
  String  X3D_ELNAME                = "X3D";
  String  X3D_ATTR_PROFILE_NAME     = "profile";
  boolean X3D_ATTR_PROFILE_REQD     = true;
  String  X3D_ATTR_PROFILE_DFLT     = "Immersive";
  String  X3D_ATTR_VERSION_NAME     = "version";
  boolean X3D_ATTR_VERSION_REQD     = true;
  String  X3D_ATTR_VERSION_DFLT     = "3.3";
  String  X3D_ATTR_XMLNSXSD_NAME    = "xmlns:xsd";
  boolean X3D_ATTR_XMLNSXSD_REQD    = true;
  String  X3D_ATTR_XMLNSXSD_DFLT    = "http://www.w3.org/2001/XMLSchema-instance";
  String  X3D_ATTR_XSDNONAMESPACESCHEMALOCATION_NAME    = "xsd:noNamespaceSchemaLocation";
  boolean X3D_ATTR_XSDNONAMESPACESCHEMALOCATION_REQD    = true;
  String  X3D_ATTR_XSDNONAMESPACESCHEMALOCATION_DFLT    = "https://www.web3d.org/specifications/x3d-3.3.xsd";

  String[]X3D_ATTR_PROFILE_CHOICES  = {
      "Core", "Interchange", "Interactive", "Immersive", "CADInterchange", "MedicalInterchange", "Full",
  };
  // Inline customizer: profile choice might not be known, thus provide empty option
  String[]X3D_ATTR_PROFILE_OPTIONS  = {
      "", "Core", "Interchange", "Interactive", "Immersive", "CADInterchange", "MedicalInterchange", "Full",
  };
  String[]X3D_ATTR_VERSION_CHOICES  = {
      "3.0","3.1","3.2","3.3","4.0"
  };
  String[]X3D_ATTR_XSDNONAMESPACESCHEMALOCATION_CHOICES  = {
      "https://www.web3d.org/specifications/x3d-3.0.xsd",
      "https://www.web3d.org/specifications/x3d-3.1.xsd",
      "https://www.web3d.org/specifications/x3d-3.2.xsd",
      "https://www.web3d.org/specifications/x3d-3.3.xsd",
      "https://www.web3d.org/specifications/x3d-4.0.xsd",
  };
  
  String[] X3D_ATTR_VERSION_HINTS = {
      "ISO Standard 2004",
      "ISO Standard 2006",
      "ISO Standard 2008",
      "ISO Standard 2012",
      "ISO Ballot 2021",
  };

  // head element
  String HEAD_ELNAME  = "head";

  // Scene element
  String SCENE_ELNAME = "Scene";

  // meta element
  String  META_ELNAME             = "meta"; // not a mistake, must use lower case
  // https://www.web3d.org/files/specifications/19775-1/V3.3/Part01/components/core.html#METAStatement
  // https://www.web3d.org/files/specifications/19776-1/V3.2/Part01/concepts.html#Header

  String  META_ATTR_NAME_NAME     = "name";
  boolean META_ATTR_NAME_REQD     = false;
  String  META_ATTR_NAME_DFLT     = "";
  String  META_ATTR_NAME_REFERENCE= "http://www.dublincore.org/documents/dcmi-terms"; // TODO local url
  String  META_ATTR_NAME_EXAMPLES = "https://www.web3d.org/x3d/content/examples/newScene.html"; // TODO local url
  String  META_ATTR_CONTENT_NAME  = "content";
  boolean META_ATTR_CONTENT_REQD  = true;
  String  META_ATTR_CONTENT_DFLT  = "";

  // TODO upgrade references to HTML5 Recommendation when that is stable

  String  META_ATTR_DIR_NAME        = "dir";
  boolean META_ATTR_DIR_REQD        = false;
  String  META_ATTR_DIR_DFLT        = "";
  String  META_ATTR_DIR_REFERENCE   = "http://www.w3.org/TR/html4/struct/dirlang.html#adef-dir";
  String[]META_ATTR_DIR_CHOICES  = {
      "(none)",
      "ltr",
      "rtl"
  };

  String  META_ATTR_HTTPEQUIV_NAME  = "http-equiv";
  boolean META_ATTR_HTTPEQUIV_REQD  = false;
  String  META_ATTR_HTTPEQUIV_DFLT  = "";
  String  META_ATTR_HTTPEQUIV_REFERENCE = "http://www.w3.org/TR/html4/struct/global.html#adef-http-equiv";
  String  META_ATTR_HTTPEQUIV_LOOKUP    = "http://reference.sitepoint.com/html/meta/http-equiv";
                                                           // "http://www.w3schools.com/tags/att_meta_http_equiv.asp";

  String  META_ATTR_LANG_NAME       = "lang"; // superceding xml:lang
  boolean META_ATTR_LANG_REQD       = false;
  String  META_ATTR_LANG_DFLT       = "";
  String  META_ATTR_LANG_REFERENCE  = "http://www.w3.org/TR/html4/struct/dirlang.html#h-8.1.1";
                                                        // http://www.w3.org/International/articles/language-tags";
  String  META_ATTR_LANG_LOOKUP     = "http://people.w3.org/rishida/utils/subtags"; // Language Subtag Lookup by Richard Ishida

  String  META_ATTR_SCHEME_NAME      = "scheme";
  boolean META_ATTR_SCHEME_REQD      = false;
  String  META_ATTR_SCHEME_DFLT      = "";
  String  META_ATTR_SCHEME_REFERENCE = "http://www.w3.org/TR/html4/struct/global.html#idx-scheme";

  String[]META_ATTR_NAME_CHOICES  = {
      "accessRights",
      "contributor",
      "created",
      "creator",
      "description",
      "documentation",
      "drawing",
      "error",
      "generator",
      "hint",
      "identifier",
      "Image",
      "info",
//    "keyword", // do not use
      "license",
//      "metacard",
      "modified",
      "MovingImage",
      "photo",
      "reference",
      "requires",
      "rights",
      "robots",
      "source",
      "Sound",
      "specificationSection", // supported by ContentCatalogBuilder
      "specificationUrl",     // https://www.web3d.org/x3d/content/examples/Basic/X3dSpecifications
      "subject",
      "Text",
      "title",
      "TODO",
      "translator",
      "translated",
      "version",
      "warning"
  };
  // also used in X3dToXhtml.xslt tooltips
  String[]META_ATTR_NAME_CHOICES_TOOLTIPS  = {
      "permission required to access resource or security status", // accessRights
      "name of individual contributing to this resource", // contributor
      "date of initial version", // created
      "name of original author", // creator
      "summary overview describing this resource", // description
      "further information about this resource", // documentation
      "name or reference link to a supporting drawing or sketch file", // drawing
      "information about error or known problem that can prevent proper operation", // error
      "authoring tool or translation tool", // generator
      "user hint about resource features or operation", // hint
      "url address or unique Uniform Resource Identifier (URI) for resource", // identifier
      "name or reference link to supporting image file", // image
      "additional information of interest", // info
      "content or software license", // license
//      "", // metacard
      "date of modified version", // modified
      "name or reference link to supporting movie", // MovingImage
      "name or reference link to supporting photograph", // photo
      "name or reference link to supporting reference", // reference
      "prerequisites for operation or viewing", // requires
      "intellectual property rights (IPR)", // rights
      "search engine and web-spider guidance:  noindex to block page indexing, nofollow to block following links", // robots
			// https://www.w3.org/TR/html5/links.html#linkTypes 
			// https://support.google.com/webmasters/answer/96569?hl=en
			// https://support.google.com/webmasters/answer/93710?hl=en
			// https://developers.google.com/webmasters/control-crawl-index/docs/robots_meta_tag?csw=1
      "name or reference link to supporting sound file", // Sound
      "title of relevant specification section", // specificationSection
      "url for relevant specification section", // specificationUrl
      "search-index subject keywords, key phrases, or classification codes", // subject
      "resource consisting primarily of words for reading", // Text
      "file name for this resource", // title
      "action item \"to do\" that still needs to be performed", // TODO
      "name of person performing translation from another format or language", // translator
      "date of translation from another format or language", // translated
      "current version number or ID of this resource", // version
      "warning information about a known problem that impedes proper operation"  // warning
  };
//  int      META_ATTR_TODO_X3D_SPECIFICATION_MANTIS_PUBLIC  = 1;
//  int      META_ATTR_TODO_X3D_SPECIFICATION_MANTIS_MEMBER  = 2;
//  int      META_ATTR_TODO_X3D_EXAMPLES_BUGZILLA            = 3;
//  int      META_ATTR_TODO_X3D_EDIT_BUGZILLA                = 4;
//  int      META_ATTR_TODO_X3DOM_ISSUES                     = 5;
//  int      META_ATTR_TODO_X_ITE_ISSUES                     = 6;
//  int      META_ATTR_TODO_BS_CONTACT                       = 7;
//  int      META_ATTR_TODO_FREEWRL_SOURCEFORGE_BUGTRACKER   = 8;
//  int      META_ATTR_TODO_INSTANT_REALITY_FORUM            = 9;
//  int      META_ATTR_TODO_INSTANT_REALITY_EMAIL            =10;
//  int      META_ATTR_TODO_SWIRL_VIEWER_REPORT              =11;
//  int      META_ATTR_TODO_XJ3D_PLAYER_BUGZILLA             =12;
  int      META_ATTR_TODO_RESET                            = 13; // TODO_RESET must be last

  String[] META_ATTR_TODO_CHOICES =
  {
      "TODO: select an issue tracker...",              // 0
      "TODO X3D Specification Comment (public)",       // 1
      "TODO X3D Specification Mantis Issue (member)",  // 2
      "TODO X3D example scene archive Bugzilla Issue", // 3
      "TODO X3D-Edit authoring tool Bugzilla Issue",   // 4
      "TODO X3DOM Player Issue",                       // 5
      "TODO X_ITE Player Issue",                       // 6
      "TODO BS Contact BitManagement Bug Report",      // 7
      "TODO FreeWrl SourceForge Bugtracker Issue",     // 8
      "TODO instantReality Forum Issue",               // 9
      "TODO issues@instantReality.org",                //10
      "TODO Swirl Bug Report",                         //11
      "TODO Xj3D Player Bugzilla Issue",               //12
      "TODO"                                           //13, new item, must be last
  };

  String[] META_ATTR_TODO_CHOICES_TOOLTIPS =
  {
      "Option:  select a TODO issue tracker...",                     // 0
      "Enter url/number of X3D Specification Comment",               // 1
      "Enter url/number of X3D Specification Mantis Issue",          // 2
      "Enter url/number of X3D example scene Bugzilla Issue",        // 3
      "Enter url/number of X3D-Edit authoring tool Bugzilla Issue",  // 4
      "Note details of BS Contact bug-report message",               // 5
      "Note details of BS Contact bug-report message",               // 6
      "Note details of BS Contact bug-report message",               // 7
      "Enter url/number of FreeWrl SourceForge Bugtracker Issue",    // 8
      "Enter url/number of instantReality Forum Issue",              // 9
      "Send mail to InstantReality team",                            //10
      "Note details of Swirl bug-report message",                    //11
      "Enter url/number of Xj3D Player Bugzilla Issue",              //12
      "Enter new item TODO"                                          //13, new item, must be last
  };

  String[] META_ATTR_TODO_URLS  =
  {
      "", // intentionally unused                                    // 0
      "https://www.web3d.org/standards/comment",                      // 1
      "https://www.web3d.org/member-only/mantis/my_view_page.php",    // 2
      "https://www.movesinstitute.org/bugzilla/buglist.cgi?quicksearch=example+archives", // 3
      "https://www.movesinstitute.org/bugzilla/buglist.cgi?quicksearch=X3D-Edit",         // 4
      "https://github.com/x3dom/x3dom/issues",                       // 5
      "http://create3000.de/x_ite/",                                 // 6
      "http://www.bitmanagement.com/contact",                        // 7
      "http://sourceforge.net/tracker/?group_id=9120&atid=109120",   // 8
      "http://forum.instantreality.org",                             // 9
      "mailto:issues@instantreality.org",                            //10
      "http://www.pinecoast.com/support.php",                        //11
      "http://bugzilla.xj3d.org",                                    //12
      ""                                                             //13, must be last
  };
  
//  int      META_ATTR_GENERATOR_X3D_EDIT                = 2;
//  int      META_ATTR_GENERATOR_X3D_BLENDER             = 3;
//  int      META_ATTR_GENERATOR_X3D_MESHLAB             = 4;
//  int      META_ATTR_GENERATOR_PARAVIEW                = 5;
//  int      META_ATTR_GENERATOR_SEAMLESS3D              = 5;
//  int      META_ATTR_GENERATOR_ITK_SNAP                = 6;
//  int      META_ATTR_GENERATOR_SEG3D                   = 7;
//  int      META_ATTR_GENERATOR_SLICER                  = 8;
//  int      META_ATTR_GENERATOR_WINGS3D                 = 9;
  int      META_ATTR_GENERATOR_RESET                     = 31; // GENERATOR_RESET must be last
  
  String[] META_ATTR_GENERATOR_CHOICES =
  {
    "generator: select an authoring tool...",                                   // 0
    "generator ===== 3D Graphics Editors ==================",               // 1
    "generator X3D-Edit 4.0, https://savage.nps.edu/X3D-Edit",              // 2
    "generator AUV Workbench, https://savage.nps.edu/AuvWorkbench",         // 3
    "generator Blender, http://www.blender.org",                            // 4
    "generator BS Content Studio, http://www.bitmanagement.org",            // 5
    "generator ITK-SNAP, http://www.itksnap.org/pmwiki/pmwiki.php",         // 6
    "generator Mathematica, http://www.wolfram.com",                        // 7
    "generator Matlab, http://www.mathworks.org",                           // 8
    "generator Meshlab, http://www.meshlab.org",                            // 9
    "generator Paraview, http://www.kitware.com/opensource/paraview.html",  // 10
    "generator Savage Studio, https://savage.nps.edu/Savage/developers.html#DES", // 11
    "generator Seamless3D, http://www.seamless3d.com",                      // 12
    "generator Seg3D, http://www.sci.utah.edu/cibc-software/seg3d.html",    // 13
    "generator Slicer, http://www.slicer.org",                              // 14
    "generator Titania, http://create3000.de",                              // 15
    "generator Wings3D, http://www.wings3d.com",                            // 16
    "generator X3DJSAIL, X3D Java Scene Access Interface Library https://www.web3d.org/specifications/java/X3DJSAIL.html", // 17
    "generator X3DJSONLD Loader, http://coderextreme.net/X3DJSONLD",        // 18
    "generator ===== Audio Editors ========================",               // 19
    "generator Audacity, http://audacity.sourceforge.net",                  // 20
    "generator MuseScore, http://musescore.org",                            // 21
    "generator ===== Image and Video Editors ==============",               // 22
    "generator GIMP Gnu Image Manipulation Program, http://www.gimp.org",   // 23
    "generator FIJI is Just ImageJ, http://fiji.sc/Downloads",              // 24
    "generator ImageJ, http://rsbweb.nih.gov/ij/download.html",             // 25
    "generator ImageMagick, http://www.imagemagick.org",                    // 26
    "generator avidemux, http://avidemux.sourceforge.net",                  // 27
    "generator ===== Text and XML Editors =================",               // 28
    "generator Netbeans, https://www.netbeans.org",                         // 29
    "generator UltraEdit, http://www.UltraEdit.com",                        // 30
    "generator XML Spy, http://www.altova.com",                             // 31
    "generator"                                                             // 32, new item, must be last
  };
  
  String[] META_ATTR_GENERATOR_CHOICES_TOOLTIPS =
  {
    "Option:  select a generator (authoring tool) ...",                     // 0
    "generator ===== 3D Graphics Editors ==================",               // 1
    "generator X3D-Edit 3D Graphics Editor",                                // 2
    "generator Autonomous Underwater Vehicle (AUV) Workbench",              // 3
    "generator Blender 3D Graphics Editor",                                 // 4
    "generator BS Content Studio, 3D Graphics Editor",                      // 5
    "generator ITK-SNAP, Volume Editor",                                    // 6
    "generator Mathematica, Modeling Tool",                                 // 7
    "generator Matlab, Modeling Tool",                                      // 8
    "generator Meshlab 3D Graphics Editor",                                 // 9
    "generator Paraview 3D Graphics Editor",                                // 10
    "generator Savage Studio, Discrete Event Simulation Scenario Builder",  // 11
    "generator Seamless3D Graphics Editor",                                 // 12
    "generator Seg3D, Volume Editor",                                       // 13
    "generator Slicer, Volume Editor",                                      // 14
    "generator Titania X3D Editor, bring colours to your life!",            // 15
    "generator Wings3D, 3D Graphics Editor",                                // 16
    "generator X3DJSAIL, X3D Java Scene Access Interface Library",          // 17
    "generator X3DJSONLD, X3D JSON Loader Library",                         // 18
    "generator ===== Audio Editors ========================",               // 19
    "generator Audacity, Audio Editor",                                     // 20
    "generator MuseScore, Audio Editor",                                    // 21
    "generator ===== Image and Video Editors ==============",               // 22
    "generator GIMP Gnu Image Manipulation Program",                        // 23
    "generator FIJI is Just ImageJ, http://fiji.sc/Downloads",              // 24
    "generator ImageJ, Image Editor",                                       // 25
    "generator ImageMagick, Image Editor",                                  // 26
    "generator avidemux, Video Editor",                                     // 27
    "generator ===== Text and XML Editors =================",               // 28
    "generator Netbeans, Integrated Developer Environment (IDE)",           // 29
    "generator UltraEdit, Text Editor",                                     // 30
    "generator XML Spy, XML Editor",                                        // 31
    "Enter new generator name"                                              // 32, must be last
  };
  
  String[] META_ATTR_GENERATOR_URLS  =
  {
    "", // intentionally unused                                         // 0
    "", // intentionally unused                                         // 1
    "X3D-Edit 4.0, https://savage.nps.edu/X3D-Edit",                    // 2
    "AUV Workbench, https://savage.nps.edu/AuvWorkbench",               // 3
    "Blender, http://www.blender.org",                                  // 4
    "BS Content Studio, http://www.bitmanagement.com/download/studio",  // 5
    "ITK-SNAP, http://www.itksnap.org/pmwiki/pmwiki.php",               // 6
    "Mathematica, http://www.wolfram.com",                              // 7
    "Matlab, http://www.mathworks.org",                                 // 8
    "Meshlab, http://www.meshlab.org",                                  // 9
    "Paraview, http://www.kitware.com/opensource/paraview.html",        // 10
    "Savage Studio, https://savage.nps.edu/Savage/developers.html#DES", // 11
    "Seamless 3D, http://www.seamless3d.com",                           // 12
    "Seg3D, http://www.sci.utah.edu/cibc-software/seg3d.html",          // 13
    "Slicer, http://www.slicer.org",                                    // 14
    "Titania, http://create3000.de",                                    // 15
    "Wings3D, http://www.wings3d.com",                                  // 16
    "X3DJSONLD Loader, http://coderextreme.net/X3DJSONLD",                     // 17
    "", // intentionally unused                                         // 18
    "Audacity, http://audacity.sourceforge.net",                        // 19
    "MuseScore, http://musescore.org",                                  // 20
    "", // intentionally unused                                         // 21
    "GIMP Gnu Image Manipulation Program, http://www.gimp.org",         // 22
    "FIJI is Just ImageJ, http://fiji.sc/Downloads",                    // 23
    "ImageJ, http://rsbweb.nih.gov/ij/download.html",                   // 24
    "ImageMagick, http://www.imagemagick.org",                          // 25
    "avidemux, http://avidemux.sourceforge.net",                        // 26
    "", // intentionally unused                                         // 27
    "Netbeans, https://www.netbeans.org",                               // 28
    "UltraEdit, http://www.UltraEdit.com",                              // 29
    "XML Spy, http://www.altova.com",                                   // 30
    ""                                                                  // 31, must be last
  };

  // component element
  String  COMPONENT_ELNAME            = "component"; // not a mistake, must use lower case
  String  COMPONENT_ATTR_NAME_NAME    = "name";
  boolean COMPONENT_ATTR_NAME_REQD    = true;
  String  COMPONENT_ATTR_NAME_DFLT    = "";
  String  COMPONENT_ATTR_LEVEL_NAME   = "level";
  boolean COMPONENT_ATTR_LEVEL_REQD   = true;
  String  COMPONENT_ATTR_LEVEL_DFLT   = "1";
  String[]COMPONENT_ATTR_NAME_CHOICES = {
      "CADGeometry","Core","CubeMapTexturing","DIS","EnvironmentalEffects","EnvironmentalSensor","EventUtilities","Followers","Geometry2D","Geometry3D","Geospatial",
      "Grouping","H-Anim","Interpolation","KeyDeviceSensor","Layering","Layout","Lighting","Navigation","Networking","NURBS","ParticleSystems","PickingSensor",
      "PointDeviceSensor","Shaders","Rendering","RigidBodyPhysics","Scripting","Shape","Sound","Text","Texturing","Texturing3D","Time","VolumeRendering"
  };
  // Xj3D components from   http://www.xj3d.org/extensions/index.html
  String[]COMPONENT_ATTR_NAME_CHOICES_XJ3D = {
      "xj3d_Clipping","xj3d_IODevice","xj3d_DIS",
  };
  // Avalon components from http://www.instant-reality.org/documentation/component/?sortby=standard.name
  String[]COMPONENT_ATTR_NAME_CHOICES_AVALON = {
      "BehaviorController","CollisionSensor","Combiner","DirectSensor","Engine",
      "Layer","Physics","Morphing","Operator","RayTracing","Simulator",
      "Snapping","Simulator","SpatialUserInterface","Steering",
      "TreeSensor","VolumeRendering",
  };

  String[]COMPONENT_ATTR_LEVEL_CHOICES  = {"1","2","3","4"};

  // unit element
  String  UNIT_ELNAME             = "unit"; // not a mistake, must use lower case
  String  UNIT_ATTR_CATEGORY_NAME = "category";
  boolean UNIT_ATTR_CATEGORY_REQD = true;
  String  UNIT_ATTR_CATEGORY_DFLT = "angle";
  String[]UNIT_ATTR_CATEGORY_CHOICES = {
      "angle", "length", "force", "mass"
  };
  String[]UNIT_ATTR_CATEGORY_CHOICES_DEFAULT_TOOLTIPS = {
       "angle default base units are radians",
      "length default base units are meters (m)",
       "force default base units are newtons (N)",
        "mass default base units are kilograms (kg)"
  };
  String  UNIT_ATTR_NAME_NAME     = "name";
  boolean UNIT_ATTR_NAME_REQD     = true;
  String  UNIT_ATTR_NAME_DFLT     = "";

  // provide correct names and values for common conversion factors via convenience menu, following TransformCustomizer pattern
  // http://www.unit-conversion.info
  // http://en.wikipedia.org/wiki/Newton_%28unit%29#Conversion_factors
  // http://www.unitsconversion.com.ar
  // http://www.unitsconversion.com.ar/massunitsconversion/index.htm

  String[]UNIT_ATTR_NAME_ANGLE_CHOICES    = { "",                       "Degrees",            "Radians",  "FullCircle",             "Grads" };
  String[]UNIT_ATTR_NAME_ANGLE_TOOLTIPS   = { "Insert scale factor...", "Degrees to Radians", "Identity", "Full Circle to Radians", "Grads to Radians" };
  String[]UNIT_ATTR_NAME_ANGLE_FACTORS    = { "1.0",                    "0.0174532925167",    "1.0",      "6.283185307179",         "0.01570796326795"};

  String[]UNIT_ATTR_NAME_LENGTH_CHOICES   = {"",                       "Pica",           "Inches",           "Feet",           "Yards",           "Meters",   "Fathoms",           "Furlongs",           "Miles",           "NauticalMiles",            "------------------------", "Microns",             "Millimeters",           "Centimeters",           "Kilometers"  };
  String[]UNIT_ATTR_NAME_LENGTH_TOOLTIPS  = {"Insert scale factor...", "Pica to Meters", "Inches to Meters", "Feet to Meters", "Yards to Meters", "Identity", "Fathoms to Meters", "Furlongs to Meters", "Miles to Meters", "Nautical Miles to Meters", "",                         "Microns to Meters",   "Millimeters to Meters", "Centimeters to Meters", "Kilometers to Meters"};
  String[]UNIT_ATTR_NAME_LENGTH_FACTORS   = {"1.0",                    "0.0042175176",   "0.0254",           "0.3048",          "0.9144",         "1.0",      "1.8288",            "201.1684",           "1609.344",        "1852.0",                   "1",                        "0.000001",            "0.001",                 "0.01",                  "1000.0"              };

  // UK, Canada furlong is 201.168 but such a slight difference is not worth cluttering interface further...

  String[]UNIT_ATTR_NAME_FORCE_CHOICES    = { "",                       "Dynes",                "Newtons (N)",  "Kilogram-force",                "Pounds-force",                "Poundal"};
  String[]UNIT_ATTR_NAME_FORCE_TOOLTIPS   = { "Insert scale factor...", "Dynes to Newtons (N)", "Identity",     "Kilogram-force to Newtons (N)", "Pounds-force to Newtons (N)", "Poundals to Newtons (N)"};
  String[]UNIT_ATTR_NAME_FORCE_FACTORS    = { "1.0",                    "0.00001",              "1.0",          "9.8068",                        "4.4482",                      "0.13826" };

  String[]UNIT_ATTR_NAME_MASS_CHOICES    = { "",                       "Grains",                                    "Drams",                                    "Ounces",                                    "TroyOunces",                          "Pounds",                                    "Stone",                                        "Tons",                                "------------------------", "Micrograms",                   "Milligrams",                   "Centigrams",                   "Carats",                   "Grams",                       "Dekagrams",                   "MetricTonnes" };
  String[]UNIT_ATTR_NAME_MASS_TOOLTIPS   = { "Insert scale factor...", "Grains Avoirdupois (gr) to Kilograms (kg)", "Drams Avoirdupois (dr) to Kilograms (kg)", "Ounces Avoirdupois (oz) to Kilograms (kg)", "Troy Ounces (toz) to Kilograms (kg)", "Pounds Avoirdupois (lb) to Kilograms (kg)", "14 Pounds Avoirdupois (lb) to Kilograms (kg)", "Tons (U.S. short) to Kilograms (kg)", "",                         "Micrograms to Kilograms (kg)", "Milligrams to Kilograms (kg)", "Centigrams to Kilograms (kg)", "Carats to Kilograms (kg)", "Grams (g) to Kilograms (kg)", "Dekagrams to Kilograms (kg)", "Metric Tonnes (t) to Kilograms (kg)"  };
  String[]UNIT_ATTR_NAME_MASS_FACTORS    = { "1.0",                    "0.00006479891",                             "0.001771845195312",                        "0.028349523125",                            "0.0311034768",                        "0.45359237",                                "6.35029318",                                   "907.18474",                           "1.0",                      "0.000000001",                  "0.000001",                     "0.00001",                      "0.0002",                   "0.001",                       "0.01",                        "1000.0" };

  String  UNIT_ATTR_CONVERSIONFACTOR_NAME = "conversionFactor";
  boolean UNIT_ATTR_CONVERSIONFACTOR_REQD = true;
  String  UNIT_ATTR_CONVERSIONFACTOR_DFLT = "1.0";

  String[]CHILD_CONTAINERFIELD_CHOICES    = {"children", "proxy", "rootNode"};
  String[]CHILD_CONTAINERFIELD_TOOLTIPS   =
  {
      "scene root node or child node",
      "only used as proxy geometry for parent Collision node",
      "child of a GeoLOD node"
  };

  // EXPORT element
  String  EXPORT_ELNAME             = "EXPORT";
  String  EXPORT_ATTR_LOCALDEF_NAME = "localDEF";
  boolean EXPORT_ATTR_LOCALDEF_REQD = true;
  String  EXPORT_ATTR_LOCALDEF_DFLT = "";
  String  EXPORT_ATTR_AS_NAME       = "AS";
  boolean EXPORT_ATTR_AS_REQD       = false;
  String  EXPORT_ATTR_AS_DFLT       = "";

  // IMPORT element
  String  IMPORT_ELNAME                = "IMPORT";
  String  IMPORT_ATTR_INLINEDEF_NAME   = "inlineDEF";
  boolean IMPORT_ATTR_INLINEDEF_REQD   = true;
  String  IMPORT_ATTR_INLINEDEF_DFLT   = "";
  String  IMPORT_ATTR_IMPORTEDDEF_NAME = "importedDEF";
  boolean IMPORT_ATTR_IMPORTEDDEF_REQD = true;
  String  IMPORT_ATTR_IMPORTEDDEF_DFLT = "";
  String  IMPORT_ATTR_AS_NAME          = "AS";
  boolean IMPORT_ATTR_AS_REQD          = false;
  String  IMPORT_ATTR_AS_DFLT          = "";

  // Metadata elements
  String[]METADATA_CONTAINERFIELD_CHOICES    = {"metadata", "value"};
  String  METADATA_CONTAINERFIELD_TOOLTIP    = "'metadata' when describing parent node, 'value' when used as part of MetadataSet";
  String[]METADATA_CONTAINERFIELD_TOOLTIPS   =
  {
      "metadata describing parent node",
      "value for parent MetadataSet node"
  };

  String  METADATABOOLEAN_ELNAME              = "MetadataBoolean";
  String  METADATABOOLEAN_ATTR_NAME_NAME      = "name";
  boolean METADATABOOLEAN_ATTR_NAME_REQD      = false;
  String  METADATABOOLEAN_ATTR_NAME_DFLT      = "";
  String  METADATABOOLEAN_ATTR_VALUE_NAME     = "value";
  boolean METADATABOOLEAN_ATTR_VALUE_REQD     = false;
  String  METADATABOOLEAN_ATTR_VALUE_DFLT     = "";
  String  METADATABOOLEAN_ATTR_REFERENCE_NAME = "reference";
  boolean METADATABOOLEAN_ATTR_REFERENCE_REQD = false;
  String  METADATABOOLEAN_ATTR_REFERENCE_DFLT = "";

  String  METADATADOUBLE_ELNAME              = "MetadataDouble";
  String  METADATADOUBLE_ATTR_NAME_NAME      = "name";
  boolean METADATADOUBLE_ATTR_NAME_REQD      = false;
  String  METADATADOUBLE_ATTR_NAME_DFLT      = "";
  String  METADATADOUBLE_ATTR_VALUE_NAME     = "value";
  boolean METADATADOUBLE_ATTR_VALUE_REQD     = false;
  String  METADATADOUBLE_ATTR_VALUE_DFLT     = "";
  String  METADATADOUBLE_ATTR_REFERENCE_NAME = "reference";
  boolean METADATADOUBLE_ATTR_REFERENCE_REQD = false;
  String  METADATADOUBLE_ATTR_REFERENCE_DFLT = "";

  String  METADATAFLOAT_ELNAME              = "MetadataFloat";
  String  METADATAFLOAT_ATTR_NAME_NAME      = "name";
  boolean METADATAFLOAT_ATTR_NAME_REQD      = false;
  String  METADATAFLOAT_ATTR_NAME_DFLT      = "";
  String  METADATAFLOAT_ATTR_VALUE_NAME     = "value";
  boolean METADATAFLOAT_ATTR_VALUE_REQD     = false;
  String  METADATAFLOAT_ATTR_VALUE_DFLT     = "";
  String  METADATAFLOAT_ATTR_REFERENCE_NAME = "reference";
  boolean METADATAFLOAT_ATTR_REFERENCE_REQD = false;
  String  METADATAFLOAT_ATTR_REFERENCE_DFLT = "";

  String  METADATAINTEGER_ELNAME              = "MetadataInteger";
  String  METADATAINTEGER_ATTR_NAME_NAME      = "name";
  boolean METADATAINTEGER_ATTR_NAME_REQD      = false;
  String  METADATAINTEGER_ATTR_NAME_DFLT      = "";
  String  METADATAINTEGER_ATTR_VALUE_NAME     = "value";
  boolean METADATAINTEGER_ATTR_VALUE_REQD     = false;
  String  METADATAINTEGER_ATTR_VALUE_DFLT     = "";
  String  METADATAINTEGER_ATTR_REFERENCE_NAME = "reference";
  boolean METADATAINTEGER_ATTR_REFERENCE_REQD = false;
  String  METADATAINTEGER_ATTR_REFERENCE_DFLT = "";

  String  METADATASTRING_ELNAME              = "MetadataString";
  String  METADATASTRING_ATTR_NAME_NAME      = "name";
  boolean METADATASTRING_ATTR_NAME_REQD      = false;
  String  METADATASTRING_ATTR_NAME_DFLT      = "";
  String  METADATASTRING_ATTR_VALUE_NAME     = "value";
  boolean METADATASTRING_ATTR_VALUE_REQD     = false;
  String  METADATASTRING_ATTR_VALUE_DFLT     = "";
  String  METADATASTRING_ATTR_REFERENCE_NAME = "reference";
  boolean METADATASTRING_ATTR_REFERENCE_REQD = false;
  String  METADATASTRING_ATTR_REFERENCE_DFLT = "";

  String  METADATASET_ELNAME              = "MetadataSet";
  String  METADATASET_ATTR_NAME_NAME      = "name";
  boolean METADATASET_ATTR_NAME_REQD      = false;
  String  METADATASET_ATTR_NAME_DFLT      = "";
  String  METADATASET_ATTR_REFERENCE_NAME = "reference";
  boolean METADATASET_ATTR_REFERENCE_REQD = false;
  String  METADATASET_ATTR_REFERENCE_DFLT = "";

  String  WORLDINFO_ELNAME          = "WorldInfo";
  String  WORLDINFO_ATTR_TITLE_NAME = "title";
  boolean WORLDINFO_ATTR_TITLE_REQD = false;
  String  WORLDINFO_ATTR_TITLE_DFLT = "";
  String  WORLDINFO_ATTR_INFO_NAME  = "info";
  boolean WORLDINFO_ATTR_INFO_REQD  = false;
  String  WORLDINFO_ATTR_INFO_DFLT  = "";

  // Switch element
  String  SWITCH_ELNAME                = "Switch";
  String  SWITCH_ATTR_WHICHCHOICE_NAME = "whichChoice";
  boolean SWITCH_ATTR_WHICHCHOICE_REQD = false;
  String  SWITCH_ATTR_WHICHCHOICE_DFLT = "-1";     // specification default "-1" means non-visible geometry
  String  SWITCH_ATTR_WHICHCHOICE_PREFERRED = "0"; // value "0" means initial child of contained node array
  String  SWITCH_ATTR_BBOXCENTER_NAME  = "bboxCenter";
  boolean SWITCH_ATTR_BBOXCENTER_REQD  = false;
  String  SWITCH_ATTR_BBOXCENTER_DFLT  = "0 0 0";
  String  SWITCH_ATTR_BBOXSIZE_NAME    = "bboxSize";
  boolean SWITCH_ATTR_BBOXSIZE_REQD    = false;
  String  SWITCH_ATTR_BBOXSIZE_DFLT    = "-1 -1 -1";

  // Text element
  String  TEXT_ELNAME               = "Text";
  String  TEXT_ATTR_STRING_NAME     = "string";
  boolean TEXT_ATTR_STRING_REQD     = false;
  String  TEXT_ATTR_STRING_DFLT     = "";
  String  TEXT_ATTR_LENGTH_NAME     = "length";
  boolean TEXT_ATTR_LENGTH_REQD     = false;
  String  TEXT_ATTR_LENGTH_DFLT     = ""; // empty MFFloat array
  String  TEXT_ATTR_MAXEXTENT_NAME  = "maxExtent";
  boolean TEXT_ATTR_MAXEXTENT_REQD  = false;
  String  TEXT_ATTR_MAXEXTENT_DFLT  = "0.0";
  String  TEXT_ATTR_SOLID_NAME      = "solid";
  boolean TEXT_ATTR_SOLID_REQD      = false;
  String  TEXT_ATTR_SOLID_DFLT      = "false";

  String  TEXT_ATTR_LINEBOUNDS_NAME = "lineBounds";
  boolean TEXT_ATTR_LINEBOUNDS_REQD = false;
  String  TEXT_ATTR_LINEBOUNDS_DFLT = "";
  String  TEXT_ATTR_TEXTBOUNDS_NAME = "textBounds";
  boolean TEXT_ATTR_TEXTBOUNDS_REQD = false;
  String  TEXT_ATTR_TEXTBOUNDS_DFLT = "";

  // Viewpoint element
  String  VIEWPOINT_ELNAME                     = "Viewpoint";
  String  VIEWPOINT_ATTR_FIELDOFVIEW_NAME      = "fieldOfView";
  boolean VIEWPOINT_ATTR_FIELDOFVIEW_REQD      = false;
  String  VIEWPOINT_ATTR_FIELDOFVIEW_DFLT      = "0.7854";
  String  VIEWPOINT_ATTR_JUMP_NAME             = "jump";
  boolean VIEWPOINT_ATTR_JUMP_REQD             = false;
  String  VIEWPOINT_ATTR_JUMP_DFLT             = "true";
  String  VIEWPOINT_ATTR_RETAINUSEROFFSETS_NAME= "retainUserOffsets";
  boolean VIEWPOINT_ATTR_RETAINUSEROFFSETS_REQD= false;
  String  VIEWPOINT_ATTR_RETAINUSEROFFSETS_DFLT= "false";
  String  VIEWPOINT_ATTR_ORIENTATION_NAME      = "orientation";
  boolean VIEWPOINT_ATTR_ORIENTATION_REQD      = false;
  String  VIEWPOINT_ATTR_ORIENTATION_DFLT      = "0 0 1 0"; // spec default is poor choice, X3D-Edit resets to 0 1 0 0
  String  VIEWPOINT_ATTR_POSITION_NAME         = "position";
  boolean VIEWPOINT_ATTR_POSITION_REQD         = false;
  String  VIEWPOINT_ATTR_POSITION_DFLT         = "0 0 10";
  String  VIEWPOINT_ATTR_DESCRIPTION_NAME      = "description";
  boolean VIEWPOINT_ATTR_DESCRIPTION_REQD      = false;
  String  VIEWPOINT_ATTR_DESCRIPTION_DFLT      = "";
  String  VIEWPOINT_ATTR_CENTEROFROTATION_NAME = "centerOfRotation";
  boolean VIEWPOINT_ATTR_CENTEROFROTATION_REQD = false;
  String  VIEWPOINT_ATTR_CENTEROFROTATION_DFLT = "0 0 0";

  // OrthoViewpoint element
  String  ORTHOVIEWPOINT_ELNAME                = "OrthoViewpoint";
  String  ORTHOVIEWPOINT_ATTR_FIELDOFVIEW_NAME = "fieldOfView";
  boolean ORTHOVIEWPOINT_ATTR_FIELDOFVIEW_REQD = false;
  String  ORTHOVIEWPOINT_ATTR_FIELDOFVIEW_DFLT = "-1 -1 1 1";
  // all other fields except fieldOfview are identical, reuse those from Viewpoint

  // ViewpointGroup element
  String  VIEWPOINTGROUP_ELNAME                = "ViewpointGroup";
  String  VIEWPOINTGROUP_ATTR_DESCRIPTION_NAME = "description";
  boolean VIEWPOINTGROUP_ATTR_DESCRIPTION_REQD = false;
  String  VIEWPOINTGROUP_ATTR_DESCRIPTION_DFLT = "";
  String  VIEWPOINTGROUP_ATTR_DISPLAYED_NAME = "displayed";
  boolean VIEWPOINTGROUP_ATTR_DISPLAYED_REQD = false;
  String  VIEWPOINTGROUP_ATTR_DISPLAYED_DFLT = "true";
  String  VIEWPOINTGROUP_ATTR_RETAINUSEROFFSETS_NAME = "retainUserOffsets";
  boolean VIEWPOINTGROUP_ATTR_RETAINUSEROFFSETS_REQD = false;
  String  VIEWPOINTGROUP_ATTR_RETAINUSEROFFSETS_DFLT = "false";
  String  VIEWPOINTGROUP_ATTR_CENTER_NAME = "center";
  boolean VIEWPOINTGROUP_ATTR_CENTER_REQD = false;
  String  VIEWPOINTGROUP_ATTR_CENTER_DFLT = "0 0 0";
  String  VIEWPOINTGROUP_ATTR_SIZE_NAME = "size";
  boolean VIEWPOINTGROUP_ATTR_SIZE_REQD = false;
  String  VIEWPOINTGROUP_ATTR_SIZE_DFLT = "0 0 0";

  // ArcClose2D element
  String  ARCCLOSE2D_ELNAME                   = "ArcClose2D";
  String  ARCCLOSE2D_ATTR_RADIUS_NAME         = "radius";
  boolean ARCCLOSE2D_ATTR_RADIUS_REQD         = false;
  String  ARCCLOSE2D_ATTR_RADIUS_DFLT         = "1";
  String  ARCCLOSE2D_ATTR_STARTANGLE_NAME     = "startAngle";
  boolean ARCCLOSE2D_ATTR_STARTANGLE_REQD     = false;
  String  ARCCLOSE2D_ATTR_STARTANGLE_DFLT     = "0";
  String  ARCCLOSE2D_ATTR_ENDANGLE_NAME       = "endAngle";
  boolean ARCCLOSE2D_ATTR_ENDANGLE_REQD       = false;
  String  ARCCLOSE2D_ATTR_ENDANGLE_DFLT       = "1.570796"; // ""+(float)Math.PI/2.0f;
  String  ARCCLOSE2D_ATTR_CLOSURETYPE_NAME    = "closureType";
  boolean ARCCLOSE2D_ATTR_CLOSURETYPE_REQD    = false;
  String[]ARCCLOSE2D_ATTR_CLOSURETYPE_CHOICES = {"PIE","CHORD"};
  String  ARCCLOSE2D_ATTR_CLOSURETYPE_DFLT    = "PIE";
  String  ARCCLOSE2D_ATTR_SOLID_NAME          = "solid";
  boolean ARCCLOSE2D_ATTR_SOLID_REQD          = false;
  String  ARCCLOSE2D_ATTR_SOLID_DFLT          = "false";

  // Arc2D element
  String  ARC2D_ELNAME                   = "Arc2D";
  String  ARC2D_ATTR_RADIUS_NAME         = "radius";
  boolean ARC2D_ATTR_RADIUS_REQD         = false;
  String  ARC2D_ATTR_RADIUS_DFLT         = "1";
  String  ARC2D_ATTR_STARTANGLE_NAME     = "startAngle";
  boolean ARC2D_ATTR_STARTANGLE_REQD     = false;
  String  ARC2D_ATTR_STARTANGLE_DFLT     = "0";
  String  ARC2D_ATTR_ENDANGLE_NAME       = "endAngle";
  boolean ARC2D_ATTR_ENDANGLE_REQD       = false;
  String  ARC2D_ATTR_ENDANGLE_DFLT       = "1.570796"; // ""+(float)Math.PI/2.0f;

  // NavigationInfo element
  String  NAVINFO_ELNAME                  = "NavigationInfo";
  String  NAVINFO_ATTR_TYPE_NAME          = "type";
  boolean NAVINFO_ATTR_TYPE_REQD          = false;
  String[]NAVINFO_ATTR_TYPE_CHOICES       = {"EXAMINE","FLY","WALK","LOOKAT","EXPLORE","ANY","NONE"};
  String[]NAVINFO_ATTR_TYPE_DFLT          = {"EXAMINE","ANY"};

  String  NAVINFO_ATTR_SPEED_NAME         = "speed";
  boolean NAVINFO_ATTR_SPEED_REQD         = false;
  String  NAVINFO_ATTR_SPEED_DFLT         = "1.0";
  String  NAVINFO_ATTR_HEADLIGHT_NAME     = "headlight";
  boolean NAVINFO_ATTR_HEADLIGHT_REQD     = false;
  String  NAVINFO_ATTR_HEADLIGHT_DFLT     = "true";
  String  NAVINFO_ATTR_TRANSTYPE_NAME     = "transitionType";
  boolean NAVINFO_ATTR_TRANSTYPE_REQD     = false;
  String[]NAVINFO_ATTR_TRANSTYPE_CHOICES  = {"TELEPORT","LINEAR","ANIMATE"};
  String[]NAVINFO_ATTR_TRANSTYPE_DFLT     = {"LINEAR"};

  String  NAVINFO_ATTR_TRANSTIME_NAME     = "transitionTime";
  boolean NAVINFO_ATTR_TRANSTIME_REQD     = false;
  String  NAVINFO_ATTR_TRANSTIME_DFLT     = "1.0";
  String  NAVINFO_ATTR_VISLIMIT_NAME      = "visibilityLimit";
  boolean NAVINFO_ATTR_VISLIMIT_REQD      = false;
  String  NAVINFO_ATTR_VISLIMIT_DFLT      = "0.0";
  String  NAVINFO_ATTR_AVATARSIZE_NAME    = "avatarSize";
  boolean NAVINFO_ATTR_AVATARSIZE_REQD    = false;
  String  NAVINFO_ATTR_AVATARSIZE_DFLT    = "0.25 1.6 0.75";

  // Anchor element
  String  ANCHOR_ELNAME                = "Anchor";
  String  ANCHOR_ATTR_DESCRIPTION_NAME = "description";
  boolean ANCHOR_ATTR_DESCRIPTION_REQD = false;
  String  ANCHOR_ATTR_DESCRIPTION_DFLT = "";
  String  ANCHOR_ATTR_URL_NAME         = "url";
  boolean ANCHOR_ATTR_URL_REQD         = false;
  String  ANCHOR_ATTR_URL_DFLT         = "";
  String  ANCHOR_ATTR_PARAMETER_NAME   = "parameter";
  boolean ANCHOR_ATTR_PARAMETER_REQD   = false;
  String[]ANCHOR_ATTR_PARAMETER_DFLT   = {""};
  String  ANCHOR_ATTR_BBOXCENTER_NAME  = "bboxCenter";
  boolean ANCHOR_ATTR_BBOXCENTER_REQD  = false;
  String  ANCHOR_ATTR_BBOXCENTER_DFLT  = "0 0 0";
  String  ANCHOR_ATTR_BBOXSIZE_NAME    = "bboxSize";
  boolean ANCHOR_ATTR_BBOXSIZE_REQD    = false;
  String  ANCHOR_ATTR_BBOXSIZE_DFLT    = "-1 -1 -1";

  // Billboard element
  String  BILLBOARD_ELNAME                   = "Billboard";
  String  BILLBOARD_ATTR_AXISOFROTATION_NAME = "axisOfRotation";
  boolean BILLBOARD_ATTR_AXISOFROTATION_REQD = false;
  String  BILLBOARD_ATTR_AXISOFROTATION_DFLT = "0 1 0";
  String  BILLBOARD_ATTR_BBOXCENTER_NAME     = "bboxCenter";
  boolean BILLBOARD_ATTR_BBOXCENTER_REQD     = false;
  String  BILLBOARD_ATTR_BBOXCENTER_DFLT     = "0 0 0";
  String  BILLBOARD_ATTR_BBOXSIZE_NAME       = "bboxSize";
  boolean BILLBOARD_ATTR_BBOXSIZE_REQD       = false;
  String  BILLBOARD_ATTR_BBOXSIZE_DFLT       = "-1 -1 -1";

  // ClipPlane element
  String  CLIPPLANE_ELNAME                = "ClipPlane";
  String  CLIPPLANE_ATTR_ENABLED_NAME     = "enabled";
  boolean CLIPPLANE_ATTR_ENABLED_REQD     = false;
  String  CLIPPLANE_ATTR_ENABLED_DFLT     = "true";
  String  CLIPPLANE_ATTR_PLANE_NAME       = "plane";
  boolean CLIPPLANE_ATTR_PLANE_REQD       = false;
  String  CLIPPLANE_ATTR_PLANE_DFLT       = "0 1 0 0";

  // Collision element
  String  COLLISION_ELNAME                = "Collision";
  String  COLLISION_ATTR_ENABLED_NAME     = "enabled";
  boolean COLLISION_ATTR_ENABLED_REQD     = false;
  String  COLLISION_ATTR_ENABLED_DFLT     = "true";
  String  COLLISION_ATTR_COLLIDETIME_NAME = "collideTime";
  boolean COLLISION_ATTR_COLLIDETIME_REQD = false;
  String  COLLISION_ATTR_COLLIDETIME_DFLT = "";
  String  COLLISION_ATTR_BBOXCENTER_NAME  = "bboxCenter";
  boolean COLLISION_ATTR_BBOXCENTER_REQD  = false;
  String  COLLISION_ATTR_BBOXCENTER_DFLT  = "0 0 0";
  String  COLLISION_ATTR_BBOXSIZE_NAME    = "bboxSize";
  boolean COLLISION_ATTR_BBOXSIZE_REQD    = false;
  String  COLLISION_ATTR_BBOXSIZE_DFLT    = "-1 -1 -1";

  // PointLight element
  String  POINTLIGHT_ELNAME                     = "PointLight";
  String  POINTLIGHT_ATTR_AMBIENTINTENSITY_NAME = "ambientIntensity";
  boolean POINTLIGHT_ATTR_AMBIENTINTENSITY_REQD = false;
  String  POINTLIGHT_ATTR_AMBIENTINTENSITY_DFLT = "0";
  String  POINTLIGHT_ATTR_ATTENUATION_NAME      = "attenuation";
  boolean POINTLIGHT_ATTR_ATTENUATION_REQD      = false;
  String  POINTLIGHT_ATTR_ATTENUATION_DFLT      = "1 0 0";
  String  POINTLIGHT_ATTR_COLOR_NAME            = "color";
  boolean POINTLIGHT_ATTR_COLOR_REQD            = false;
  String  POINTLIGHT_ATTR_COLOR_DFLT            = "1 1 1";
  String  POINTLIGHT_ATTR_GLOBAL_NAME           = "global";
  boolean POINTLIGHT_ATTR_GLOBAL_REQD           = false;
  String  POINTLIGHT_ATTR_GLOBAL_DFLT           = "true";
  String  POINTLIGHT_ATTR_INTENSITY_NAME        = "intensity";
  boolean POINTLIGHT_ATTR_INTENSITY_REQD        = false;
  String  POINTLIGHT_ATTR_INTENSITY_DFLT        = "1";
  String  POINTLIGHT_ATTR_LOCATION_NAME         = "location";
  boolean POINTLIGHT_ATTR_LOCATION_REQD         = false;
  String  POINTLIGHT_ATTR_LOCATION_DFLT         = "0 0 0";
  String  POINTLIGHT_ATTR_ON_NAME               = "on";
  boolean POINTLIGHT_ATTR_ON_REQD               = false;
  String  POINTLIGHT_ATTR_ON_DFLT               = "true";
  String  POINTLIGHT_ATTR_RADIUS_NAME           = "radius";
  boolean POINTLIGHT_ATTR_RADIUS_REQD           = false;
  String  POINTLIGHT_ATTR_RADIUS_DFLT           = "100";

    // DirectionalLight element
  String  DIRECTIONALLIGHT_ELNAME                     = "DirectionalLight";
  String  DIRECTIONALLIGHT_ATTR_AMBIENTINTENSITY_NAME = "ambientIntensity";
  boolean DIRECTIONALLIGHT_ATTR_AMBIENTINTENSITY_REQD = false;
  String  DIRECTIONALLIGHT_ATTR_AMBIENTINTENSITY_DFLT = "0";
  String  DIRECTIONALLIGHT_ATTR_COLOR_NAME            = "color";
  boolean DIRECTIONALLIGHT_ATTR_COLOR_REQD            = false;
  String  DIRECTIONALLIGHT_ATTR_COLOR_DFLT            = "1 1 1";
  String  DIRECTIONALLIGHT_ATTR_GLOBAL_NAME           = "global";
  boolean DIRECTIONALLIGHT_ATTR_GLOBAL_REQD           = false;
  String  DIRECTIONALLIGHT_ATTR_GLOBAL_DFLT           = "false";
  String  DIRECTIONALLIGHT_ATTR_INTENSITY_NAME        = "intensity";
  boolean DIRECTIONALLIGHT_ATTR_INTENSITY_REQD        = false;
  String  DIRECTIONALLIGHT_ATTR_INTENSITY_DFLT        = "1";
  String  DIRECTIONALLIGHT_ATTR_DIRECTION_NAME        = "direction";
  boolean DIRECTIONALLIGHT_ATTR_DIRECTION_REQD        = false;
  String  DIRECTIONALLIGHT_ATTR_DIRECTION_DFLT        = "0 0 -1";
  String  DIRECTIONALLIGHT_ATTR_ON_NAME               = "on";
  boolean DIRECTIONALLIGHT_ATTR_ON_REQD               = false;
  String  DIRECTIONALLIGHT_ATTR_ON_DFLT               = "true";

  // SpotLight element
  // note reversal of beamWidth and cutOffAngle defaults, spec bug submitted
  String  SPOTLIGHT_ELNAME                     = "SpotLight";
  String  SPOTLIGHT_ATTR_AMBIENTINTENSITY_NAME = "ambientIntensity";
  boolean SPOTLIGHT_ATTR_AMBIENTINTENSITY_REQD = false;
  String  SPOTLIGHT_ATTR_AMBIENTINTENSITY_DFLT = "0";
  String  SPOTLIGHT_ATTR_ATTENUATION_NAME      = "attenuation";
  boolean SPOTLIGHT_ATTR_ATTENUATION_REQD      = false;
  String  SPOTLIGHT_ATTR_ATTENUATION_DFLT      = "1 0 0";
  String  SPOTLIGHT_ATTR_BEAMWIDTH_NAME        = "beamWidth";
  String  SPOTLIGHT_ATTR_BEAMWIDTH_DFLT        = "0.7854";// ""+(float)Math.PI/4.0f;//formatter.format(Math.PI/4.0);
  String  SPOTLIGHT_ATTR_BEAMWIDTH_MAX         = "1.570796";
  boolean SPOTLIGHT_ATTR_BEAMWIDTH_REQD        = false;
  String  SPOTLIGHT_ATTR_COLOR_NAME            = "color";
  boolean SPOTLIGHT_ATTR_COLOR_REQD            = false;
  String  SPOTLIGHT_ATTR_COLOR_DFLT            = "1 1 1";
  String  SPOTLIGHT_ATTR_CUTOFFANGLE_NAME      = "cutOffAngle";
  boolean SPOTLIGHT_ATTR_CUTOFFANGLE_REQD      = false;
  String  SPOTLIGHT_ATTR_CUTOFFANGLE_DFLT      = "1.570796";// ""+(float)Math.PI/2.0f;//formatter.format(Math.PI/2.0); this rounds up, which exceed our limits
  String  SPOTLIGHT_ATTR_CUTOFFANGLE_MAX       = "1.570796";
  String  SPOTLIGHT_ATTR_DIRECTION_NAME        = "direction";
  boolean SPOTLIGHT_ATTR_DIRECTION_REQD        = false;
  String  SPOTLIGHT_ATTR_DIRECTION_DFLT        = "0 0 -1";
  String  SPOTLIGHT_ATTR_GLOBAL_NAME           = "global";
  boolean SPOTLIGHT_ATTR_GLOBAL_REQD           = false;
  String  SPOTLIGHT_ATTR_GLOBAL_DFLT           = "true";
  String  SPOTLIGHT_ATTR_INTENSITY_NAME        = "intensity";
  boolean SPOTLIGHT_ATTR_INTENSITY_REQD        = false;
  String  SPOTLIGHT_ATTR_INTENSITY_DFLT        = "1";
  String  SPOTLIGHT_ATTR_LOCATION_NAME         = "location";
  boolean SPOTLIGHT_ATTR_LOCATION_REQD         = false;
  String  SPOTLIGHT_ATTR_LOCATION_DFLT         = "0 0 0";
  String  SPOTLIGHT_ATTR_ON_NAME               = "on";
  boolean SPOTLIGHT_ATTR_ON_REQD               = false;
  String  SPOTLIGHT_ATTR_ON_DFLT               = "true";
  String  SPOTLIGHT_ATTR_RADIUS_NAME           = "radius";
  boolean SPOTLIGHT_ATTR_RADIUS_REQD           = false;
  String  SPOTLIGHT_ATTR_RADIUS_DFLT           = "100";

  // Background element
  String  BACKGROUND_ELNAME                = "Background";
  String  BACKGROUND_ATTR_GROUNDANGLE_NAME = "groundAngle";
  boolean BACKGROUND_ATTR_GROUNDANGLE_REQD = false;
  String  BACKGROUND_ATTR_GROUNDANGLE_DFLT = "";
  String  BACKGROUND_ATTR_GROUNDCOLOR_NAME = "groundColor";
  boolean BACKGROUND_ATTR_GROUNDCOLOR_REQD = false;
  String  BACKGROUND_ATTR_GROUNDCOLOR_DFLT = "";
  String  BACKGROUND_ATTR_SKYANGLE_NAME    = "skyAngle";
  boolean BACKGROUND_ATTR_SKYANGLE_REQD    = false;
  String  BACKGROUND_ATTR_SKYANGLE_DFLT    = "";
  String  BACKGROUND_ATTR_SKYCOLOR_NAME    = "skyColor";
  boolean BACKGROUND_ATTR_SKYCOLOR_REQD    = false;
  String  BACKGROUND_ATTR_SKYCOLOR_DFLT    = "0 0 0";
  String  BACKGROUND_ATTR_LEFTURL_NAME     = "leftUrl";
  boolean BACKGROUND_ATTR_LEFTURL_REQD     = false;
  String  BACKGROUND_ATTR_LEFTURL_DFLT     = "";
  String  BACKGROUND_ATTR_RIGHTURL_NAME    = "rightUrl";
  boolean BACKGROUND_ATTR_RIGHTURL_REQD    = false;
  String  BACKGROUND_ATTR_RIGHTURL_DFLT    = "";
  String  BACKGROUND_ATTR_FRONTURL_NAME    = "frontUrl";
  boolean BACKGROUND_ATTR_FRONTURL_REQD    = false;
  String  BACKGROUND_ATTR_FRONTURL_DFLT    = "";
  String  BACKGROUND_ATTR_BACKURL_NAME     = "backUrl";
  boolean BACKGROUND_ATTR_BACKURL_REQD     = false;
  String  BACKGROUND_ATTR_BACKURL_DFLT     = "";
  String  BACKGROUND_ATTR_TOPURL_NAME      = "topUrl";
  boolean BACKGROUND_ATTR_TOPURL_REQD      = false;
  String  BACKGROUND_ATTR_TOPURL_DFLT      = "";
  String  BACKGROUND_ATTR_BOTTOMURL_NAME   = "bottomUrl";
  boolean BACKGROUND_ATTR_BOTTOMURL_REQD   = false;
  String  BACKGROUND_ATTR_BOTTOMURL_DFLT   = "";

 // TextureBackground element
  String  TEXTUREBACKGROUND_ELNAME                 = "TextureBackground";
  String  TEXTUREBACKGROUND_ATTR_TRANSPARENCY_NAME = "transparency";
  boolean TEXTUREBACKGROUND_ATTR_TRANSPARENCY_REQD = false;
  String  TEXTUREBACKGROUND_ATTR_TRANSPARENCY_DFLT = "0";
  String  TEXTUREBACKGROUND_ATTR_GROUNDANGLE_NAME  = "groundAngle";
  boolean TEXTUREBACKGROUND_ATTR_GROUNDANGLE_REQD  = false;
  String  TEXTUREBACKGROUND_ATTR_GROUNDANGLE_DFLT  = "";
  String  TEXTUREBACKGROUND_ATTR_GROUNDCOLOR_NAME  = "groundColor";
  boolean TEXTUREBACKGROUND_ATTR_GROUNDCOLOR_REQD  = false;
  String  TEXTUREBACKGROUND_ATTR_GROUNDCOLOR_DFLT  = "";
  String  TEXTUREBACKGROUND_ATTR_SKYANGLE_NAME     = "skyAngle";
  boolean TEXTUREBACKGROUND_ATTR_SKYANGLE_REQD     = false;
  String  TEXTUREBACKGROUND_ATTR_SKYANGLE_DFLT     = "";
  String  TEXTUREBACKGROUND_ATTR_SKYCOLOR_NAME     = "skyColor";
  boolean TEXTUREBACKGROUND_ATTR_SKYCOLOR_REQD     = false;
  String  TEXTUREBACKGROUND_ATTR_SKYCOLOR_DFLT     = "0 0 0";

  // elements Fog and LocalFog

  String  FOG_ELNAME                    = "Fog";
  String  FOG_ATTR_COLOR_NAME           = "color";
  boolean FOG_ATTR_COLOR_REQD           = false;
  String  FOG_ATTR_COLOR_DFLT           = "0.8 0.8 0.8";
  String  FOG_ATTR_FOGTYPE_NAME         = "fogType";
  boolean FOG_ATTR_FOGTYPE_REQD         = false;
  String  FOG_ATTR_FOGTYPE_DFLT         = "LINEAR";
  String  FOG_ATTR_VISIBILITYRANGE_NAME = "visibilityRange";
  boolean FOG_ATTR_VISIBILITYRANGE_REQD = false;
  String  FOG_ATTR_VISIBILITYRANGE_DFLT = "0";
  String[]FOG_ATTR_FOGTYPE_CHOICES = { "LINEAR", "EXPONENTIAL" };

  String  LOCALFOG_ELNAME               = "LocalFog";
  String  LOCALFOG_ATTR_ENABLED_NAME    = "enabled";
  boolean LOCALFOG_ATTR_ENABLED_REQD    = false;
  String  LOCALFOG_ATTR_ENABLED_DFLT    = "true";

  // element Group
  String  GROUP_ELNAME                     = "Group";
  // element StaticGroup
  String  STATICGROUP_ELNAME               = "StaticGroup";// code duplicates Group

  String  GROUP_ATTR_BBOXCENTER_NAME       = "bboxCenter";
  boolean GROUP_ATTR_BBOXCENTER_REQD       = false;
  String  GROUP_ATTR_BBOXCENTER_DFLT       = "0 0 0";
  String  GROUP_ATTR_BBOXSIZE_NAME         = "bboxSize";
  boolean GROUP_ATTR_BBOXSIZE_REQD         = false;
  String  GROUP_ATTR_BBOXSIZE_DFLT         = "-1 -1 -1";

  // Also used by Shape, LOD
  String[]GROUP_CONTAINERFIELD_CHOICES    = {"children", "proxy", "rootNode"};
  String[]GROUP_CONTAINERFIELD_TOOLTIPS   =
  {
      "'children' for typical use as child node",
      "proxy field for a Collision child node",
      "child of a GeoLOD node"
  };

  // element Transform
  String  TRANSFORM_ELNAME                     = "Transform";
  String  TRANSFORM_ATTR_TRANSLATION_NAME      = "translation";
  boolean TRANSFORM_ATTR_TRANSLATION_REQD      = false;
  String  TRANSFORM_ATTR_TRANSLATION_DFLT      = "0 0 0";
  String  TRANSFORM_ATTR_ROTATION_NAME         = "rotation";
  boolean TRANSFORM_ATTR_ROTATION_REQD         = false;
  String  TRANSFORM_ATTR_ROTATION_DFLT         = "0 0 1 0";
  String  TRANSFORM_ATTR_CENTER_NAME           = "center";
  boolean TRANSFORM_ATTR_CENTER_REQD           = false;
  String  TRANSFORM_ATTR_CENTER_DFLT           = "0 0 0";
  String  TRANSFORM_ATTR_SCALE_NAME            = "scale";
  boolean TRANSFORM_ATTR_SCALE_REQD            = false;
  String  TRANSFORM_ATTR_SCALE_DFLT            = "1 1 1";
  String[]TRANSFORM_ATTR_TRANSLATION_CHOICES   = {"Apply scaling factor...", "Unit scaling", "Pica to Meters", "Inches to Meters", "Feet to Meters", "Yards to Meters", "Fathoms to Meters", "Miles to Meters", "Nautical Miles to Meters", "------------------------", "Microns to Meters",   "Millimeters to Meters", "Centimeters to Meters", "Kilometers to Meters", "multiply by 2", "multiply by 3", "multiply by 4", "multiply by 5", "multiply by 10", "multiply by 100", "divide by 2", "divide by 3", "divide by 4", "divide by 5", "divide by 10", "divide by 100"};
  String[]TRANSFORM_ATTR_SCALE_CHOICES         = {"Insert scale factor...",  "Unit scaling", "Pica to Meters", "Inches to Meters", "Feet to Meters", "Yards to Meters", "Fathoms to Meters", "Miles to Meters", "Nautical Miles to Meters", "------------------------", "Microns to Meters",   "Millimeters to Meters", "Centimeters to Meters", "Kilometers to Meters", "multiply by 2", "multiply by 3", "multiply by 4", "multiply by 5", "multiply by 10", "multiply by 100", "divide by 2", "divide by 3", "divide by 4", "divide by 5", "divide by 10", "divide by 100"};
  String[]TRANSFORM_ATTR_SCALE_FACTORS         = {"1",                       "1",            "0.0042175176",   "0.0254",           "0.3048",          "0.9144",         "1.8288",            "1609.344",        "1852",                     "1",                        "0.000001",            "0.001",                 "0.01",                  "1000",                             "2",             "3",             "4",             "5",             "10",             "100",         "0.5", "0.333333333333",     "0.25",         "0.2",         "0.10",          "0.01"};
  // the following labels should not included embedded spaces since they are used to construct indentifying DEF names
  String[]TRANSFORM_ATTR_SCALE_LABELS          = {"",                        "",             "PicaToMeters",   "InchesToMeters",   "FeetToMeters",    "YardsToMeters",  "FathomsToMeters",   "MilesToMeters",   "NauticalMilesToMeters",    "",                         "MicronsToMeters",     "MillimetersToMeters",   "CentimetersToMeters",   "KilometersToMeters"  ,              "",             "",               "",              "",               "",                "",            "",               "",         "",            "",             "",              ""};
  String  TRANSFORM_ATTR_SCALEORIENTATION_NAME = "scaleOrientation";
  boolean TRANSFORM_ATTR_SCALEORIENTATION_REQD = false;
  String  TRANSFORM_ATTR_SCALEORIENTATION_DFLT = "0 0 1 0";
  String  TRANSFORM_ATTR_BBOXCENTER_NAME       = "bboxCenter";
  boolean TRANSFORM_ATTR_BBOXCENTER_REQD       = false;
  String  TRANSFORM_ATTR_BBOXCENTER_DFLT       = "0 0 0";
  String  TRANSFORM_ATTR_BBOXSIZE_NAME         = "bboxSize";
  boolean TRANSFORM_ATTR_BBOXSIZE_REQD         = false;
  String  TRANSFORM_ATTR_BBOXSIZE_DFLT         = "-1 -1 -1";

  // Also used by Shape, LOD
  String[]TRANSFORM_CONTAINERFIELD_CHOICES    = {"children", "proxy", "shape", "rootNode"};
  String  TRANSFORM_CONTAINERFIELD_TOOLTIP    = "'children' for typical use as child node, 'shape' when initial child of CADFace";
  String[]TRANSFORM_CONTAINERFIELD_TOOLTIPS   =
  {
      "'children' for typical use as child node",
      "proxy field for a Collision child node",
      "'shape' when initial child of CADFace",
      "child of a GeoLOD node"
  };

  // element Inline
  String  INLINE_ELNAME               = "Inline";
  String  INLINE_ATTR_LOAD_NAME       = "load";
  boolean INLINE_ATTR_LOAD_REQD       = false;
  String  INLINE_ATTR_LOAD_DFLT       = "true";
  String  INLINE_ATTR_URL_NAME        = "url";
  boolean INLINE_ATTR_URL_REQD        = false;
  String  INLINE_ATTR_URL_DFLT        = "";
  String  INLINE_ATTR_BBOXCENTER_NAME = "bboxCenter";
  boolean INLINE_ATTR_BBOXCENTER_REQD = false;
  String  INLINE_ATTR_BBOXCENTER_DFLT = "0 0 0";
  String  INLINE_ATTR_BBOXSIZE_NAME   = "bboxSize";
  boolean INLINE_ATTR_BBOXSIZE_REQD   = false;
  String  INLINE_ATTR_BBOXSIZE_DFLT   = "-1 -1 -1";

  // element LOD
  String  LOD_ELNAME                     = "LOD";
  String  LOD_ATTR_CENTER_NAME           = "center";
  boolean LOD_ATTR_CENTER_REQD           = false;
  String  LOD_ATTR_CENTER_DFLT           = "0 0 0";
  String  LOD_ATTR_RANGE_NAME            = "range";
  boolean LOD_ATTR_RANGE_REQD            = false;
  String  LOD_ATTR_RANGE_DFLT            = "";
  String  LOD_ATTR_FORCETRANSITIONS_NAME = "forceTransitions";
  boolean LOD_ATTR_FORCETRANSITIONS_REQD = false;
  String  LOD_ATTR_FORCETRANSITIONS_DFLT = "false";
  String  LOD_ATTR_BBOXCENTER_NAME       = "bboxCenter";
  boolean LOD_ATTR_BBOXCENTER_REQD       = false;
  String  LOD_ATTR_BBOXCENTER_DFLT       = "0 0 0";
  String  LOD_ATTR_BBOXSIZE_NAME         = "bboxSize";
  boolean LOD_ATTR_BBOXSIZE_REQD         = false;
  String  LOD_ATTR_BBOXSIZE_DFLT         = "-1 -1 -1";

  // element Shape
  String  SHAPE_ELNAME               = "Shape";
  String  SHAPE_ATTR_BBOXCENTER_NAME = "bboxCenter";
  boolean SHAPE_ATTR_BBOXCENTER_REQD = false;
  String  SHAPE_ATTR_BBOXCENTER_DFLT = "0 0 0";
  String  SHAPE_ATTR_BBOXSIZE_NAME   = "bboxSize";
  boolean SHAPE_ATTR_BBOXSIZE_REQD   = false;
  String  SHAPE_ATTR_BBOXSIZE_DFLT   = "-1 -1 -1";

  String[]SHAPE_ATTR_CONTAINERFIELD_CHOICES    = {"children", "proxy", "shape", "rootNode"};
  String[]SHAPE_ATTR_CONTAINERFIELD_TOOLTIPS   =
  {
      "child of a grouping node",
      "proxy field for a Collision child node",
      "shape field for a CADFace child node",
      "child of a GeoLOD node"
  };

  // element Sphere
  String  SPHERE_ELNAME           = "Sphere";
  String  SPHERE_ATTR_RADIUS_NAME = "radius";
  boolean SPHERE_ATTR_RADIUS_REQD = false;
  String  SPHERE_ATTR_RADIUS_DFLT = "1";
  String  SPHERE_ATTR_SOLID_NAME  = "solid";
  boolean SPHERE_ATTR_SOLID_REQD  = false;
  String  SPHERE_ATTR_SOLID_DFLT  = "true";

  // element TriangleSet2D
  String  TRIANGLESET2D_ELNAME             = "TriangleSet2D";
  String  TRIANGLESET2D_ATTR_VERTICES_NAME = "vertices";
  boolean TRIANGLESET2D_ATTR_VERTICES_REQD = false;
  String  TRIANGLESET2D_ATTR_VERTICES_DFLT = "";
  String  TRIANGLESET2D_ATTR_SOLID_NAME    = "solid";
  boolean TRIANGLESET2D_ATTR_SOLID_REQD    = false;
  String  TRIANGLESET2D_ATTR_SOLID_DFLT    = "false";

  // element Rectangle2D
  String  RECTANGLE2D_ELNAME          = "Rectangle2D";
  String  RECTANGLE2D_ATTR_SIZE_NAME  = "size";
  boolean RECTANGLE2D_ATTR_SIZE_REQD  = false;
  String  RECTANGLE2D_ATTR_SIZE_DFLT  = "2 2";
  String  RECTANGLE2D_ATTR_SOLID_NAME = "solid";
  boolean RECTANGLE2D_ATTR_SOLID_REQD = false;
  String  RECTANGLE2D_ATTR_SOLID_DFLT = "false";

  // element Disk2D
  String  DISK2D_ELNAME                = "Disk2D";
  String  DISK2D_ATTR_INNERRADIUS_NAME = "innerRadius";
  boolean DISK2D_ATTR_INNERRADIUS_REQD = false;
  String  DISK2D_ATTR_INNERRADIUS_DFLT = "0";
  String  DISK2D_ATTR_OUTERRADIUS_NAME = "outerRadius";
  boolean DISK2D_ATTR_OUTERRADIUS_REQD = false;
  String  DISK2D_ATTR_OUTERRADIUS_DFLT = "1";
  String  DISK2D_ATTR_SOLID_NAME       = "solid";
  boolean DISK2D_ATTR_SOLID_REQD       = false;
  String  DISK2D_ATTR_SOLID_DFLT       = "false";

  // element Cylinder
  String  CYLINDER_ELNAME           = "Cylinder";
  String  CYLINDER_ATTR_BOTTOM_NAME = "bottom";
  boolean CYLINDER_ATTR_BOTTOM_REQD = false;
  String  CYLINDER_ATTR_BOTTOM_DFLT = "true";
  String  CYLINDER_ATTR_HEIGHT_NAME = "height";
  boolean CYLINDER_ATTR_HEIGHT_REQD = false;
  String  CYLINDER_ATTR_HEIGHT_DFLT = "2";
  String  CYLINDER_ATTR_RADIUS_NAME = "radius";
  boolean CYLINDER_ATTR_RADIUS_REQD = false;
  String  CYLINDER_ATTR_RADIUS_DFLT = "1";
  String  CYLINDER_ATTR_SIDE_NAME   = "side";
  boolean CYLINDER_ATTR_SIDE_REQD   = false;
  String  CYLINDER_ATTR_SIDE_DFLT   = "true";
  String  CYLINDER_ATTR_TOP_NAME    = "top";
  boolean CYLINDER_ATTR_TOP_REQD    = false;
  String  CYLINDER_ATTR_TOP_DFLT    = "true";
  String  CYLINDER_ATTR_SOLID_NAME  = "solid";
  boolean CYLINDER_ATTR_SOLID_REQD  = false;
  String  CYLINDER_ATTR_SOLID_DFLT  = "true";

  // element Cone
  String  CONE_ELNAME                 = "Cone";
  String  CONE_ATTR_BOTTOMRADIUS_NAME = "bottomRadius";
  boolean CONE_ATTR_BOTTOMRADIUS_REQD = false;
  String  CONE_ATTR_BOTTOMRADIUS_DFLT = "1";
  String  CONE_ATTR_HEIGHT_NAME       = "height";
  boolean CONE_ATTR_HEIGHT_REQD       = false;
  String  CONE_ATTR_HEIGHT_DFLT       = "2";
  String  CONE_ATTR_SIDE_NAME         = "side";
  boolean CONE_ATTR_SIDE_REQD         = false;
  String  CONE_ATTR_SIDE_DFLT         = "true";
  String  CONE_ATTR_BOTTOM_NAME       = "bottom";
  boolean CONE_ATTR_BOTTOM_REQD       = false;
  String  CONE_ATTR_BOTTOM_DFLT       = "true";
  String  CONE_ATTR_SOLID_NAME        = "solid";
  boolean CONE_ATTR_SOLID_REQD        = false;
  String  CONE_ATTR_SOLID_DFLT        = "true";

  // element Box
  String  BOX_ELNAME                 = "Box";
  String  BOX_ATTR_SIZE_NAME         = "size";
  boolean BOX_ATTR_SIZE_REQD         = false;
  String  BOX_ATTR_SIZE_DFLT         = "2 2 2";
  String  BOX_ATTR_SOLID_NAME        = "solid";
  boolean BOX_ATTR_SOLID_REQD        = false;
  String  BOX_ATTR_SOLID_DFLT        = "true";

  // element Circle2D
  String  CIRCLE2D_ELNAME           = "Circle2D";
  String  CIRCLE2D_ATTR_RADIUS_NAME = "radius";
  boolean CIRCLE2D_ATTR_RADIUS_REQD = false;
  String  CIRCLE2D_ATTR_RADIUS_DFLT = "1";

  // element FontStyle
  String  FONTSTYLE_ELNAME                = "FontStyle";
  String  FONTSTYLE_ATTR_FAMILY_NAME      = "family";
  boolean FONTSTYLE_ATTR_FAMILY_REQD      = false;
  String  FONTSTYLE_ATTR_FAMILY_DFLT      = "SERIF";
  String[]FONTSTYLE_ATTR_FAMILY_CHOICES   = {"SERIF","SANS","TYPEWRITER"};
  String  FONTSTYLE_ATTR_HORIZONTAL_NAME  = "horizontal";
  boolean FONTSTYLE_ATTR_HORIZONTAL_REQD  = false;
  String  FONTSTYLE_ATTR_HORIZONTAL_DFLT  = "true";
  String  FONTSTYLE_ATTR_JUSTIFY_NAME     = "justify";
  boolean FONTSTYLE_ATTR_JUSTIFY_REQD     = false;
  String  FONTSTYLE_ATTR_JUSTIFY_DFLT1    = "BEGIN"; // prefer "MIDDLE" when building new node
  String  FONTSTYLE_ATTR_JUSTIFY_DFLT2    = "FIRST"; // prefer "MIDDLE" when building new node
  String[]FONTSTYLE_ATTR_JUSTIFY_CHOICES  = {"MIDDLE","BEGIN","END","FIRST"};
  String  FONTSTYLE_ATTR_LANGUAGE_NAME    = "language";
  boolean FONTSTYLE_ATTR_LANGUAGE_REQD    = false;
  String  FONTSTYLE_ATTR_LANGUAGE_DFLT    = "";
  String  FONTSTYLE_ATTR_LEFTTORIGHT_NAME = "leftToRight";
  boolean FONTSTYLE_ATTR_LEFTTORIGHT_REQD = false;
  String  FONTSTYLE_ATTR_LEFTTORIGHT_DFLT = "true";
  String  FONTSTYLE_ATTR_SIZE_NAME        = "size";
  boolean FONTSTYLE_ATTR_SIZE_REQD        = false;
  String  FONTSTYLE_ATTR_SIZE_DFLT        = "1.0";
  String  FONTSTYLE_ATTR_SPACING_NAME     = "spacing";
  boolean FONTSTYLE_ATTR_SPACING_REQD     = false;
  String  FONTSTYLE_ATTR_SPACING_DFLT     = "1.0";
  String  FONTSTYLE_ATTR_STYLE_NAME       = "style";
  boolean FONTSTYLE_ATTR_STYLE_REQD       = false;
  String  FONTSTYLE_ATTR_STYLE_DFLT       = "PLAIN";
  String[]FONTSTYLE_ATTR_STYLE_CHOICES    = {"PLAIN","BOLD","ITALIC","BOLDITALIC"};
  String  FONTSTYLE_ATTR_TOPTOBOTTOM_NAME = "topToBottom";
  boolean FONTSTYLE_ATTR_TOPTOBOTTOM_REQD = false;
  String  FONTSTYLE_ATTR_TOPTOBOTTOM_DFLT = "true";

  // element Appearance
  String  APPEARANCE_ELNAME = "Appearance";

  // element FillProperties
  String  FILLPROPERTIES_ELNAME               = "FillProperties";
  String  FILLPROPERTIES_ATTR_FILLED_NAME     = "filled";
  boolean FILLPROPERTIES_ATTR_FILLED_REQD     = false;
  String  FILLPROPERTIES_ATTR_FILLED_DFLT     = "true";
  String  FILLPROPERTIES_ATTR_HATCHCOLOR_NAME = "hatchColor";
  boolean FILLPROPERTIES_ATTR_HATCHCOLOR_REQD = false;
  String  FILLPROPERTIES_ATTR_HATCHCOLOR_DFLT = "1 1 1";
  String  FILLPROPERTIES_ATTR_HATCHED_NAME    = "hatched";
  boolean FILLPROPERTIES_ATTR_HATCHED_REQD    = false;
  String  FILLPROPERTIES_ATTR_HATCHED_DFLT    = "true";
  String  FILLPROPERTIES_ATTR_HATCHSTYLE_NAME = "hatchStyle";
  boolean FILLPROPERTIES_ATTR_HATCHSTYLE_REQD = false;
  String  FILLPROPERTIES_ATTR_HATCHSTYLE_DFLT = "1";
  String[] FILLPROPERTIES_ATTR_HATCHSTYLE_CHOICES = {
    "hatchStyle='1'  horizontal equally spaced parallel lines",//1
    "hatchStyle='2'  vertical equally spaced parallel lines", //2
    "hatchStyle='3'  positive slope equally spaced parallel lines",//3
    "hatchStyle='4'  negative slope equally spaced parallel lines",//4
    "hatchStyle='5'  horizontal/vertical crosshatch",//5
    "hatchStyle='6'  positive slope / negative slope crosshatch",//6
    "hatchStyle='7'  (optional support) cast iron or malleable iron and general use for all materials",//7
    "hatchStyle='8'  (optional support) steel",//8
    "hatchStyle='9'  (optional support) bronze, brass, copper, and compositions",//9
    "hatchStyle='10' (optional support) white metal, zinc, lead, babbit, and alloys",//10
    "hatchStyle='11' (optional support) magnesium, aluminum, and aluminum alloys",//11
    "hatchStyle='12' (optional support) rubber, plastic, and electrical insulation",//12
    "hatchStyle='13' (optional support) cork, felt, fabric, leather, and fibre",//13
    "hatchStyle='14' (optional support) thermal insulation",//14
    "hatchStyle='15' (optional support) titanium and refractory material",//15
    "hatchStyle='16' (optional support) marble, slate, porcelain, glass, etc.",//16
    "hatchStyle='17' (optional support) earth",//17
    "hatchStyle='18' (optional support) sand",//18
    "hatchStyle='19' (optional support) repeating dot",//19
  };

  // element TextureProperties
  String  TEXTUREPROPERTIES_ELNAME                        = "TextureProperties";
  String  TEXTUREPROPERTIES_ATTR_ANISOTROPICDEGREE_NAME   = "anisotropicDegree";
  boolean TEXTUREPROPERTIES_ATTR_ANISOTROPICDEGREE_REQD   = false;
  String  TEXTUREPROPERTIES_ATTR_ANISOTROPICDEGREE_DFLT   = "1.0";
  String  TEXTUREPROPERTIES_ATTR_BORDERCOLOR_NAME         = "borderColor";
  boolean TEXTUREPROPERTIES_ATTR_BORDERCOLOR_REQD         = false;
  String  TEXTUREPROPERTIES_ATTR_BORDERCOLOR_DFLT         = "0 0 0 0";
  String  TEXTUREPROPERTIES_ATTR_BORDERWIDTH_NAME         = "borderWidth";
  boolean TEXTUREPROPERTIES_ATTR_BORDERWIDTH_REQD         = false;
  String  TEXTUREPROPERTIES_ATTR_BORDERWIDTH_DFLT         = "0";
  String  TEXTUREPROPERTIES_ATTR_BOUNDARYMODE_S_NAME      = "boundaryModeS";
  boolean TEXTUREPROPERTIES_ATTR_BOUNDARYMODE_S_REQD      = false;
  String  TEXTUREPROPERTIES_ATTR_BOUNDARYMODE_S_DFLT      = "REPEAT";
  String  TEXTUREPROPERTIES_ATTR_BOUNDARYMODE_T_NAME      = "boundaryModeT";
  boolean TEXTUREPROPERTIES_ATTR_BOUNDARYMODE_T_REQD      = false;
  String  TEXTUREPROPERTIES_ATTR_BOUNDARYMODE_T_DFLT      = "REPEAT";
  String  TEXTUREPROPERTIES_ATTR_BOUNDARYMODE_R_NAME      = "boundaryModeR";
  boolean TEXTUREPROPERTIES_ATTR_BOUNDARYMODE_R_REQD      = false;
  String  TEXTUREPROPERTIES_ATTR_BOUNDARYMODE_R_DFLT      = "REPEAT";
  String  TEXTUREPROPERTIES_ATTR_GENERATEMIPMAPS_NAME     = "generateMipMaps";
  boolean TEXTUREPROPERTIES_ATTR_GENERATEMIPMAPS_REQD     = false;
  String  TEXTUREPROPERTIES_ATTR_GENERATEMIPMAPS_DFLT     = "false";
  String  TEXTUREPROPERTIES_ATTR_MAGNIFICATIONFILTER_NAME = "magnificationFilter";
  boolean TEXTUREPROPERTIES_ATTR_MAGNIFICATIONFILTER_REQD = false;
  String  TEXTUREPROPERTIES_ATTR_MAGNIFICATIONFILTER_DFLT = "FASTEST";
  String  TEXTUREPROPERTIES_ATTR_MINIFICATIONFILTER_NAME  = "minificationFilter";
  boolean TEXTUREPROPERTIES_ATTR_MINIFICATIONFILTER_REQD  = false;
  String  TEXTUREPROPERTIES_ATTR_MINIFICATIONFILTER_DFLT  = "FASTEST";
  String  TEXTUREPROPERTIES_ATTR_TEXTURECOMPRESSION_NAME  = "textureCompression";
  boolean TEXTUREPROPERTIES_ATTR_TEXTURECOMPRESSION_REQD  = false;
  String  TEXTUREPROPERTIES_ATTR_TEXTURECOMPRESSION_DFLT  = "FASTEST";
  String  TEXTUREPROPERTIES_ATTR_TEXTUREPRIORITY_NAME     = "texturePriority";
  boolean TEXTUREPROPERTIES_ATTR_TEXTUREPRIORITY_REQD     = false;
  String  TEXTUREPROPERTIES_ATTR_TEXTUREPRIORITY_DFLT     = "0.0";

  String[] TEXTUREPROPERTIES_ATTR_BOUNDARYMODE_LABELS = {
    "CLAMP                        Clamp to range [0,1]",//1
    "CLAMP_TO_EDGE                Clamp such that border texel is never sampled", //2
    "CLAMP_TO_BOUNDARY            Clamp to range [-1/(2N), 1 + 1/(2N)]",//3
    "MIRRORED_REPEAT              Mirror texture coordinates then CLAMP_TO_EDGE",//4
    "REPEAT                       Repeat across fragment, only use fractional part",//5
  };
  String[] TEXTUREPROPERTIES_ATTR_BOUNDARYMODE_CHOICES = {
    "CLAMP",//1
    "CLAMP_TO_EDGE", //2
    "CLAMP_TO_BOUNDARY",//3
    "MIRRORED_REPEAT",//4
    "REPEAT",//5
  };
  String[] TEXTUREPROPERTIES_ATTR_MAGNIFICATIONMODE_LABELS = {
    "AVG_PIXEL                    Weighted average of 4 texels closest to center",//1
    "DEFAULT                      Browser-specified default magnification mode", //2
    "FASTEST                      Fastest method available",//3
    "NEAREST_PIXEL                Pixel nearest to center of pixel being textured",//4
    "NICEST                       Highest-quality method available",//5
  };
  String[] TEXTUREPROPERTIES_ATTR_MAGNIFICATIONMODE_CHOICES = {
    "AVG_PIXEL",//1
    "DEFAULT", //2
    "FASTEST",//3
    "NEAREST_PIXEL",//4
    "NICEST",//5
  };
  String[] TEXTUREPROPERTIES_ATTR_MINIFICATIONMODE_LABELS = {
    "AVG_PIXEL                    Weighted average of 4 texels closest to center",//1
    "AVG_PIXEL_AVG_MIPMAP         Tri-linear filtering",//2
    "AVG_PIXEL_NEAREST_MIPMAP     Weighted average of 4 texels, nearest mipmap",//3
    "DEFAULT                      Browser-specified default magnification mode", //4
    "FASTEST                      Fastest method available",//5
    "NEAREST_PIXEL                Pixel nearest to center of textured pixel",//6
    "NEAREST_PIXEL_AVG_MIPMAP     Match 2 nearest-sized mipmaps, nearest texel",//7
    "NEAREST_PIXEL_NEAREST_MIPMAP Match nearest-sized mipmap, nearest texel",//8
    "NICEST                       Highest-quality method available, use mipmaps",//9
  };
  String[] TEXTUREPROPERTIES_ATTR_MINIFICATIONMODE_CHOICES = {
    "AVG_PIXEL",//1
    "AVG_PIXEL_AVG_MIPMAP",//2
    "AVG_PIXEL_NEAREST_MIPMAP",//3
    "DEFAULT", //4
    "FASTEST",//5
    "NEAREST_PIXEL",//6
    "NEAREST_PIXEL_AVG_MIPMAP",//7
    "NEAREST_PIXEL_NEAREST_MIPMAP",//8
    "NICEST",//9
  };
  String[] TEXTUREPROPERTIES_ATTR_COMPRESSIONMODE_LABELS = {
    "DEFAULT                      Browser-specified default compression mode",//1
    "FASTEST                      Fastest compression mode available",//2
    "HIGH                         Greatest amount of compression",//3
    "LOW                          Least amount of compression", //4
    "MEDIUM                       Moderate amount of compression",//5
    "NICEST                       Compression producing nicest effect",//6
  };
  String[] TEXTUREPROPERTIES_ATTR_COMPRESSIONMODE_CHOICES = {
    "DEFAULT",//1
    "FASTEST",//2
    "HIGH",//3
    "LOW", //4
    "MEDIUM",//5
    "NICEST",//6
  };

  // Element MultiTexture
  String  MULTITEXTURE_ELNAME              = "MultiTexture";
  String  MULTITEXTURE_ATTR_ALPHA_NAME     = "alpha";
  boolean MULTITEXTURE_ATTR_ALPHA_REQD     = false;
  String  MULTITEXTURE_ATTR_ALPHA_DFLT     = "1";
  String  MULTITEXTURE_ATTR_COLOR_NAME     = "color";
  boolean MULTITEXTURE_ATTR_COLOR_REQD     = false;
  String  MULTITEXTURE_ATTR_COLOR_DFLT     = "1 1 1";
  String  MULTITEXTURE_ATTR_FUNCTION_NAME  = "function";
  boolean MULTITEXTURE_ATTR_FUNCTION_REQD  = false;
  String  MULTITEXTURE_ATTR_FUNCTION_DFLT  = "";
  String  MULTITEXTURE_ATTR_MODE_NAME      = "mode";
  boolean MULTITEXTURE_ATTR_MODE_REQD      = false;
  String  MULTITEXTURE_ATTR_MODE_DFLT      = "";
  String  MULTITEXTURE_ATTR_SOURCE_NAME    = "source";
  boolean MULTITEXTURE_ATTR_SOURCE_REQD    = false;
  String  MULTITEXTURE_ATTR_SOURCE_DFLT    = "";

  String[] MULTITEXTURE_ATTR_MODE_CHOICES = {
    "MODULATE",//1
    "REPLACE", //2
    "MODULATE2X",//3
    "MODULATE4X",//4
    "ADD",//5
    "ADDSIGNED",//6
    "ADDSIGNED2X",//7
    "SUBTRACT",//8
    "ADDSMOOTH",//9
    "BLENDDIFFUSEALPHA",//10
    "BLENDTEXTUREALPHA",//11
    "BLENDFACTORALPHA",//12
    "BLENDCURRENTALPHA",//13
    "MODULATEALPHA_ADDCOLOR",//14
    "MODULATEINVALPHA_ADDCOLOR",//15
    "MODULATEINVCOLOR_ADDALPHA",//16
    "OFF",//17
    "SELECTARG1",//18
    "SELECTARG2",//19
    "DOTPRODUCT3"//20
  };

  String[] MULTITEXTURE_ATTR_MODE_DESCRIPTIONS = {
    "Multiply texture color with current color, Arg1 × Arg2",//1
    "Replace current color, Arg2", //2
    "Multiply components of  arguments, shift products left 1 bit (multiplying by 2) for brightening",//3
    "Multiply components of  arguments, shift products left 2 bits (multiplying by 4) for brightening",//4
    "Add the components of the arguments, Arg1 + Arg2",//5
    "Add components of arguments with -0.5 bias, effective range becomes −0.5 through 0.5",//6
    "Add components of arguments with -0.5 bias, shift products to left 1 bit",//7
    "Subtract components of second argument from first argument, Arg1 − Arg2",//8
    "Add first and second arguments, then subtract product from sum. Arg1 + Arg2 − Arg1 × Arg2 = Arg1 + (1 − Arg1) × Arg2",//9
    "Linearly blend this texture stage using interpolated alpha from each vertex, Arg1 × (Alpha) + Arg2 × (1 − Alpha)",//10
    "Linearly blend this texture stage using alpha from this stage's texture, Arg1 × (Alpha) + Arg2 × (1 − Alpha)",//11
    "Linearly blend this texture stage using alpha factor from MultiTexture node, Arg1 × (Alpha) + Arg2 × (1 − Alpha)",//12
    "Linearly blend this texture stage using alpha taken from previous texture stage, Arg1 × (Alpha) + Arg2 × (1 − Alpha)",//13
    "Modulate color of second argument using alpha of first argument, then add result to argument one, Arg1.RGB + Arg1.A × Arg2.RGB",//14
    "Similar to MODULATEALPHA_ADDCOLOR but use inverse of alpha of first argument, (1 − Arg1.A) × Arg2.RGB + Arg1.RGB",//15
    "Similar to MODULATECOLOR_ADDALPHA but use inverse of color of first argument, (1 − Arg1.RGB) × Arg2.RGB + Arg1.A",//16
    "No texture composition for this stage",//17
    "Use color argument 1, Arg1",//18
    "Use color argument 1, Arg2 ",//19
    "Modulate components of each argument (as signed components), add their products, then replicate sum to all color channels, including alpha."//20
  };
  String[] MULTITEXTURE_ATTR_SOURCE_CHOICES = {
    "",//1
    "DIFFUSE",//2
    "SPECULAR",//3
    "FACTOR" //4
  };
  String[] MULTITEXTURE_ATTR_SOURCE_DESCRIPTIONS = {
    "(DEFAULT) Second argument color (ARG2) is color from previous rendering stage (DIFFUSE for first stage)",//1
    "Texture argument is diffuse color interpolated from vertex components during Gouraud shading",//2
    "Texture argument is specular color interpolated from vertex components during Gouraud shading",//3
    "Texture argument is multiplicative factor (color, alpha) from MultiTexture node" //4
  };
  String[] MULTITEXTURE_ATTR_FUNCTION_CHOICES = {
    "",//1
    "COMPLEMENT", //2
    "ALPHAREPLICATE"//3
  };
  String[] MULTITEXTURE_ATTR_FUNCTION_DESCRIPTIONS = {
    "(DEFAULT) No function is applied",//1
    "Invert  argument so that, if result of  argument is x, result value is 1.0 minus x.", //2
    "Replicate alpha information to all color channels before operation completes"//3
  };

  // Element MultiTextureCoordinate
  String  MULTITEXTURECOORDINATE_ELNAME        = "MultiTextureCoordinate";

  // Element MultiTextureTransform
  String  MULTITEXTURETRANSFORM_ELNAME         = "MultiTextureTransform";

  // Element Material
  String  MATERIAL_ELNAME                      = "Material";
  String  MATERIAL_ATTR_AMBIENTINTENSITY_NAME  = "ambientIntensity";
  boolean MATERIAL_ATTR_AMBIENTINTENSITY_REQD  = false;
  String  MATERIAL_ATTR_AMBIENTINTENSITY_DFLT  = "0.2";
  String  MATERIAL_ATTR_SHININESS_NAME         = "shininess";
  boolean MATERIAL_ATTR_SHININESS_REQD         = false;
  String  MATERIAL_ATTR_SHININESS_DFLT         = "0.2";
  String  MATERIAL_ATTR_TRANSPARENCY_NAME      = "transparency";
  boolean MATERIAL_ATTR_TRANSPARENCY_REQD      = false;
  String  MATERIAL_ATTR_TRANSPARENCY_DFLT      = "0.0";
  String  MATERIAL_ATTR_DIFFUSECOLOR_NAME      = "diffuseColor";
  boolean MATERIAL_ATTR_DIFFUSECOLOR_REQD      = false;
  String  MATERIAL_ATTR_DIFFUSECOLOR_DFLT      = "0.8 0.8 0.8";
  String  MATERIAL_ATTR_EMISSIVECOLOR_NAME     = "emissiveColor";
  boolean MATERIAL_ATTR_EMISSIVECOLOR_REQD     = false;
  String  MATERIAL_ATTR_EMISSIVECOLOR_DFLT     = "0 0 0";
  String  MATERIAL_ATTR_SPECULARCOLOR_NAME     = "specularColor";
  boolean MATERIAL_ATTR_SPECULARCOLOR_REQD     = false;
  String  MATERIAL_ATTR_SPECULARCOLOR_DFLT     = "0 0 0";

  // Element TwoSidedMaterial
  String  TWOSIDEDMATERIAL_ELNAME                      = "TwoSidedMaterial";
  String  TWOSIDEDMATERIAL_ATTR_SEPARATEBACKCOLOR_NAME = "separateBackColor";
  boolean TWOSIDEDMATERIAL_ATTR_SEPARATEBACKCOLOR_REQD = false;
  String  TWOSIDEDMATERIAL_ATTR_SEPARATEBACKCOLOR_DFLT = "false";

  String  TWOSIDEDMATERIAL_ATTR_AMBIENTINTENSITY_NAME  = "ambientIntensity";
  boolean TWOSIDEDMATERIAL_ATTR_AMBIENTINTENSITY_REQD  = false;
  String  TWOSIDEDMATERIAL_ATTR_AMBIENTINTENSITY_DFLT  = "0.2";
  String  TWOSIDEDMATERIAL_ATTR_SHININESS_NAME         = "shininess";
  boolean TWOSIDEDMATERIAL_ATTR_SHININESS_REQD         = false;
  String  TWOSIDEDMATERIAL_ATTR_SHININESS_DFLT         = "0.2";
  String  TWOSIDEDMATERIAL_ATTR_TRANSPARENCY_NAME      = "transparency";
  boolean TWOSIDEDMATERIAL_ATTR_TRANSPARENCY_REQD      = false;
  String  TWOSIDEDMATERIAL_ATTR_TRANSPARENCY_DFLT      = "0.0";
  String  TWOSIDEDMATERIAL_ATTR_DIFFUSECOLOR_NAME      = "diffuseColor";
  boolean TWOSIDEDMATERIAL_ATTR_DIFFUSECOLOR_REQD      = false;
  String  TWOSIDEDMATERIAL_ATTR_DIFFUSECOLOR_DFLT      = "0.8 0.8 0.8";
  String  TWOSIDEDMATERIAL_ATTR_EMISSIVECOLOR_NAME     = "emissiveColor";
  boolean TWOSIDEDMATERIAL_ATTR_EMISSIVECOLOR_REQD     = false;
  String  TWOSIDEDMATERIAL_ATTR_EMISSIVECOLOR_DFLT     = "0 0 0";
  String  TWOSIDEDMATERIAL_ATTR_SPECULARCOLOR_NAME     = "specularColor";
  boolean TWOSIDEDMATERIAL_ATTR_SPECULARCOLOR_REQD     = false;
  String  TWOSIDEDMATERIAL_ATTR_SPECULARCOLOR_DFLT     = "0 0 0";
  String  TWOSIDEDMATERIAL_ATTR_BACK_AMBIENTINTENSITY_NAME  = "backAmbientIntensity";
  boolean TWOSIDEDMATERIAL_ATTR_BACK_AMBIENTINTENSITY_REQD  = false;
  String  TWOSIDEDMATERIAL_ATTR_BACK_AMBIENTINTENSITY_DFLT  = "0.2";
  String  TWOSIDEDMATERIAL_ATTR_BACK_SHININESS_NAME         = "backShininess";
  boolean TWOSIDEDMATERIAL_ATTR_BACK_SHININESS_REQD         = false;
  String  TWOSIDEDMATERIAL_ATTR_BACK_SHININESS_DFLT         = "0.2";
  String  TWOSIDEDMATERIAL_ATTR_BACK_TRANSPARENCY_NAME      = "backTransparency";
  boolean TWOSIDEDMATERIAL_ATTR_BACK_TRANSPARENCY_REQD      = false;
  String  TWOSIDEDMATERIAL_ATTR_BACK_TRANSPARENCY_DFLT      = "0.0";
  String  TWOSIDEDMATERIAL_ATTR_BACK_DIFFUSECOLOR_NAME      = "backDiffuseColor";
  boolean TWOSIDEDMATERIAL_ATTR_BACK_DIFFUSECOLOR_REQD      = false;
  String  TWOSIDEDMATERIAL_ATTR_BACK_DIFFUSECOLOR_DFLT      = "0.8 0.8 0.8";
  String  TWOSIDEDMATERIAL_ATTR_BACK_EMISSIVECOLOR_NAME     = "backEmissiveColor";
  boolean TWOSIDEDMATERIAL_ATTR_BACK_EMISSIVECOLOR_REQD     = false;
  String  TWOSIDEDMATERIAL_ATTR_BACK_EMISSIVECOLOR_DFLT     = "0 0 0";
  String  TWOSIDEDMATERIAL_ATTR_BACK_SPECULARCOLOR_NAME     = "backSpecularColor";
  boolean TWOSIDEDMATERIAL_ATTR_BACK_SPECULARCOLOR_REQD     = false;
  String  TWOSIDEDMATERIAL_ATTR_BACK_SPECULARCOLOR_DFLT     = "0 0 0";
  // Element LineProperties

  String  LINEPROPERTIES_ELNAME                = "LineProperties";
  String  LINEPROPERTIES_ATTR_APPLIED_NAME     = "applied";
  boolean LINEPROPERTIES_ATTR_APPLIED_REQD     = false;
  String  LINEPROPERTIES_ATTR_APPLIED_DFLT     = "true";
  String  LINEPROPERTIES_ATTR_LINEWIDTHSCALEFACTOR_NAME = "linewidthScaleFactor";
  boolean LINEPROPERTIES_ATTR_LINEWIDTHSCALEFACTOR_REQD = false;
  String  LINEPROPERTIES_ATTR_LINEWIDTHSCALEFACTOR_DFLT = "0";
  String  LINEPROPERTIES_ATTR_LINETYPE_NAME    = "linetype";
  boolean LINEPROPERTIES_ATTR_LINETYPE_REQD    = false;
  String  LINEPROPERTIES_ATTR_LINETYPE_DFLT    = "1";
  String[] LINEPROPERTIES_ATTR_LINETYPE_CHOICES = {
    "linetype='1'  solid",//1
    "linetype='2'  dashed", //2
    "linetype='3'  dotted",//3
    "linetype='4'  dashed-dotted",//4
    "linetype='5'  dash-dot-dot",//5
    "linetype='6'  (optional support) single-headed arrows",//6
    "linetype='7'  (optional support) single dot",//7
    "linetype='8'  (optional support) double-headed arrows",//8
    "linetype='9'  [no entry]",//9
    "linetype='10' (optional support) chain line",//10
    "linetype='11' (optional support) center line",//11
    "linetype='12' (optional support) hidden line",//12
    "linetype='13' (optional support) phantom line",//13
    "linetype='14' (optional support) break line 1",//14
    "linetype='15' (optional support) break line 2",//15
    "linetype='16' (optional support) user-specified dash pattern",//16
  };

  // Element ImageTexture
  String  IMAGETEXTURE_ELNAME            = "ImageTexture";
  String  IMAGETEXTURE_ATTR_REPEATS_NAME = "repeatS";
  boolean IMAGETEXTURE_ATTR_REPEATS_REQD = false;
  String  IMAGETEXTURE_ATTR_REPEATS_DFLT = "true";

  String  IMAGETEXTURE_ATTR_REPEATT_NAME = "repeatT";
  boolean IMAGETEXTURE_ATTR_REPEATT_REQD = false;
  String  IMAGETEXTURE_ATTR_REPEATT_DFLT = "true";

  String  IMAGETEXTURE_ATTR_URL_NAME     = "url";
  boolean IMAGETEXTURE_ATTR_URL_REQD     = false;
  String  IMAGETEXTURE_ATTR_URL_DFLT     = "";

  String[]IMAGETEXTURE_ATTR_CONTAINERFIELD_CHOICES = {
    "texture", "leftTexture","rightTexture","backTexture","frontTexture","topTexture","bottomTexture"};
  String  IMAGETEXTURE_ATTR_CONTAINERFIELD_TOOLTIP =
    "texture if parent is Shape or ComposedTexture3D, otherTexture if parent is TextureBackground";
  String[]IMAGETEXTURE_ATTR_CONTAINERFIELD_TOOLTIPS = {
    "texture if parent is Shape or ComposedTexture3D",
    "leftTexture if parent is TextureBackground",
    "rightTexture if parent is TextureBackground",
    "backTexture if parent is TextureBackground",
    "frontTexture if parent is TextureBackground",
    "topTexture if parent is TextureBackground",
    "bottomTexture if parent is TextureBackground"};

  // Element MovieTexture
  String  MOVIETEXTURE_ELNAME               = "MovieTexture";
  String  MOVIETEXTURE_ATTR_DESCRIPTION_NAME= "description";
  boolean MOVIETEXTURE_ATTR_DESCRIPTION_REQD= false;
  String  MOVIETEXTURE_ATTR_DESCRIPTION_DFLT= "";
  String  MOVIETEXTURE_ATTR_REPEATS_NAME    = "repeatS";
  boolean MOVIETEXTURE_ATTR_REPEATS_REQD    = false;
  String  MOVIETEXTURE_ATTR_REPEATS_DFLT    = "true";
  String  MOVIETEXTURE_ATTR_REPEATT_NAME    = "repeatT";
  boolean MOVIETEXTURE_ATTR_REPEATT_REQD    = false;
  String  MOVIETEXTURE_ATTR_REPEATT_DFLT    = "true";
  String  MOVIETEXTURE_ATTR_LOOP_NAME       = "loop";
  boolean MOVIETEXTURE_ATTR_LOOP_REQD       = false;
  String  MOVIETEXTURE_ATTR_LOOP_DFLT       = "false";
  String  MOVIETEXTURE_ATTR_RESUMETIME_NAME = "resumeTime";
  boolean MOVIETEXTURE_ATTR_RESUMETIME_REQD = false;
  String  MOVIETEXTURE_ATTR_RESUMETIME_DFLT = "0";
  String  MOVIETEXTURE_ATTR_PAUSETIME_NAME  = "pauseTime";
  boolean MOVIETEXTURE_ATTR_PAUSETIME_REQD  = false;
  String  MOVIETEXTURE_ATTR_PAUSETIME_DFLT  = "0";
  String  MOVIETEXTURE_ATTR_SPEED_NAME      = "speed";
  boolean MOVIETEXTURE_ATTR_SPEED_REQD      = false;
  String  MOVIETEXTURE_ATTR_SPEED_DFLT      = "1.0";
  String  MOVIETEXTURE_ATTR_STARTTIME_NAME  = "startTime";
  boolean MOVIETEXTURE_ATTR_STARTTIME_REQD  = false;
  String  MOVIETEXTURE_ATTR_STARTTIME_DFLT  = "0";
  String  MOVIETEXTURE_ATTR_STOPTIME_NAME   = "stopTime";
  boolean MOVIETEXTURE_ATTR_STOPTIME_REQD   = false;
  String  MOVIETEXTURE_ATTR_STOPTIME_DFLT   = "0";
  String  MOVIETEXTURE_ATTR_URL_NAME        = "url";
  boolean MOVIETEXTURE_ATTR_URL_REQD        = false;
  String  MOVIETEXTURE_ATTR_URL_DFLT        = "";

  String[]MOVIETEXTURE_ATTR_CONTAINERFIELD_CHOICES = {
    "texture", "source", "leftTexture","rightTexture","backTexture","frontTexture","topTexture","bottomTexture"};
  String  MOVIETEXTURE_ATTR_CONTAINERFIELD_TOOLTIP =
    "texture if parent is Shape or ComposedTexture3D, source of parent is Sound, or backTexture/frontTexture/etc. if parent is TextureBackground";
  String[]MOVIETEXTURE_ATTR_CONTAINERFIELD_TOOLTIPS = {
    "texture if parent is Shape or ComposedTexture3D",
    "source if parent is Sound",
    "leftTexture if parent is TextureBackground",
    "rightTexture if parent is TextureBackground",
    "backTexture if parent is TextureBackground",
    "frontTexture if parent is TextureBackground",
    "topTexture if parent is TextureBackground",
    "bottomTexture if parent is TextureBackground"};

  // Element PixelTexture
  String  PIXELTEXTURE_ELNAME             = "PixelTexture";
  String  PIXELTEXTURE_ATTR_IMAGE_NAME    = "image";
  boolean PIXELTEXTURE_ATTR_IMAGE_REQD    = false;
  String  PIXELTEXTURE_ATTR_IMAGE_DFLT    = "0 0 0";
  String  PIXELTEXTURE_ATTR_REPEATS_NAME  = "repeatS";
  boolean PIXELTEXTURE_ATTR_REPEATS_REQD  = false;
  String  PIXELTEXTURE_ATTR_REPEATS_DFLT  = "true";
  String  PIXELTEXTURE_ATTR_REPEATT_NAME  = "repeatT";
  boolean PIXELTEXTURE_ATTR_REPEATT_REQD  = false;
  String  PIXELTEXTURE_ATTR_REPEATT_DFLT  = "true";

  // Element TextureTransform
  String  TEXTURETRANSFORM_ELNAME                = "TextureTransform";
  String  TEXTURETRANSFORM_ATTR_CENTER_NAME      = "center";
  boolean TEXTURETRANSFORM_ATTR_CENTER_REQD      = false;
  String  TEXTURETRANSFORM_ATTR_CENTER_DFLT      = "0 0";
  String  TEXTURETRANSFORM_ATTR_ROTATION_NAME    = "rotation";
  boolean TEXTURETRANSFORM_ATTR_ROTATION_REQD    = false;
  String  TEXTURETRANSFORM_ATTR_ROTATION_DFLT    = "0";
  String  TEXTURETRANSFORM_ATTR_TRANSLATION_NAME = "translation";
  boolean TEXTURETRANSFORM_ATTR_TRANSLATION_REQD = false;
  String  TEXTURETRANSFORM_ATTR_TRANSLATION_DFLT = "0 0";
  String  TEXTURETRANSFORM_ATTR_SCALE_NAME       = "scale";
  boolean TEXTURETRANSFORM_ATTR_SCALE_REQD       = false;
  String  TEXTURETRANSFORM_ATTR_SCALE_DFLT       = "1 1";

  // Element TextureCoordinate
  String  TEXTURECOORDINATE_ELNAME          = "TextureCoordinate";
  String  TEXTURECOORDINATE_ATTR_POINT_NAME = "point";
  boolean TEXTURECOORDINATE_ATTR_POINT_REQD = false;
  String  TEXTURECOORDINATE_ATTR_POINT_DFLT = "0 0, 1 0, 1 1, 0 1"; // override X3D spec to provide commonly used defaults for building interface

  // Element TextureCoordinateGenerator
  String  TEXTURECOORDINATEGENERATOR_ELNAME              = "TextureCoordinateGenerator";
  String  TEXTURECOORDINATEGENERATOR_ATTR_MODE_NAME      = "mode";
  boolean TEXTURECOORDINATEGENERATOR_ATTR_MODE_REQD      = false;
  String  TEXTURECOORDINATEGENERATOR_ATTR_MODE_DFLT      = "SPHERE";
  String  TEXTURECOORDINATEGENERATOR_ATTR_PARAMETER_NAME = "parameter";
  boolean TEXTURECOORDINATEGENERATOR_ATTR_PARAMETER_REQD = false;
  String  TEXTURECOORDINATEGENERATOR_ATTR_PARAMETER_DFLT = "";
  String[]TEXTURECOORDINATEGENERATOR_ATTR_PARAMETER_CHOICES = {
    "SPHERE",
    "CAMERASPACENORMAL",
    "CAMERASPACEPOSITION",
    "CAMERASPACEREFLECTIONVECTOR",
    "SPHERE-LOCAL",
    "COORD",
    "COORD-EYE",
    "NOISE",
    "NOISE-EYE",
    "SPHERE-REFLECT",
    "SPHERE-REFLECT-LOCAL"
  };

  // Element Color
  String  COLOR_ELNAME          = "Color";
  String  COLOR_ATTR_COLOR_NAME = "color";
  boolean COLOR_ATTR_COLOR_REQD = false;
  String  COLOR_ATTR_COLOR_DFLT = "";

  // Element ColorRGBA
  String  COLORRGBA_ELNAME          = "ColorRGBA";
  String  COLORRGBA_ATTR_COLOR_NAME = "color";
  boolean COLORRGBA_ATTR_COLOR_REQD = false;
  String  COLORRGBA_ATTR_COLOR_DFLT = "";

  // Elements Coordinate and CoordinateDouble
  String  COORDINATE_ELNAME          = "Coordinate";
  String  COORDINATEDOUBLE_ELNAME    = "CoordinateDouble";
  String  COORDINATE_ATTR_POINT_NAME = "point";
  boolean COORDINATE_ATTR_POINT_REQD = false;
  String  COORDINATE_ATTR_POINT_DFLT = "";

  // Geometry set choices are provided to ExpandableList to simplify populating coordinate array values

  String N_SIDED_POLYGON = "N-sided Polygon";

  String[] COORDINATE_ATTR_POINT_CHOICES = {
    "Select geometry:",//1
    "Triangle, Right",//1
    "Triangle, Equilateral",//2
    "Triangle, Left",//3
    "Square (z=0)",//4
    "Square (x=0)",//5
    "Square (y=0)",//6
    N_SIDED_POLYGON + " (z=0)",//8 XY plane
    N_SIDED_POLYGON + " (x=0)",//9 YZ plane
    N_SIDED_POLYGON + " (y=0)",//10 XZ plane
  };
  String[][][] COORDINATE_ATTR_POINT_VALUES = {
      {},//0
      {{"#","0","0","0"},{"#","1","0","0"},{"#","1","1","0"}},//1
      {{"#","0","0","0"},{"#","0.5","0.866025404","0"},{"#","1","0","0"}},//2
      {{"#","0","0","0"},{"#","0","1","0"},{"#","1","0","0"}},//3
      {{"#","0","0","0"},{"#","1","0","0"},{"#","1","1","0"},{"#","0","1","0"}},//4 ("#" is placeholder for index column)
      {{"#","0","0","0"},{"#","0","1","0"},{"#","0","1","1"},{"#","0","0","1"}},//5
      {{"#","0","0","0"},{"#","0","0","1"},{"#","1","0","1"},{"#","1","0","0"}},//6
      {},//7
      {},//8
      {},//9
  };
  String[] COORDINATE_ATTR_POINT_DESCRIPTIONS = {
    "Select geometry of interest then Add points",//0
    "Right triangle (z=0, X-Y plane)", //1
    "Equilateral triangle (z=0, X-Y plane)", //2
    "Right triangle, left-hand side (z=0, X-Y plane)", //3
    "Unit square, vertical (z=0, X-Y plane)",//4
    "Unit square, vertical (x=0, Y-Z plane)",//5
    "Unit square, horizontal (y=0, X-Z plane)", //6
    N_SIDED_POLYGON + ", vertical (z=0, X-Y plane)",//7
    N_SIDED_POLYGON + ", vertical (x=0, Y-Z plane)",//8
    N_SIDED_POLYGON + ", horizontal (y=0, X-Z plane)", //9
  };

  String[] COORDINATE2D_ATTR_POINT_CHOICES = {
    "Select geometry:",//0
    "Triangle, Right",//1
    "Triangle, Equilateral",//2
    "Triangle, Left",//3
    "Square",//4
    N_SIDED_POLYGON + "",//5
  };

  String[][][] COORDINATE2D_ATTR_POINT_VALUES = {
      {},//0
      {{"#","0","0"},{"#","1","0"},{"#","1","1"}},//1
      {{"#","0","0"},{"#","0.5","0.866025404"},{"#","1","0"}},//2
      {{"#","0","0"},{"#","0","1"},{"#","1","0"}},//3
      {{"#","0","0"},{"#","1","0"},{"#","1","1"},{"#","0","1"}},//4 ("#" is placeholder for index column)
      {},//5
  };
  String[] COORDINATE2D_ATTR_POINT_DESCRIPTIONS = {
    "Select geometry of interest then Add points",//0
    "Right triangle", //1
    "Equilateral triangle, unit sides", //2
    "Right triangle, left-hand side", //3
    "Unit square",//4
    N_SIDED_POLYGON + " points",//5
  };

  String[] TRIANGLE2D_ATTR_POINT_CHOICES = {
    "Select geometry:",//0
    "Triangle, Right",//1
    "Triangle, Equilateral",//2
    "Triangle, Left",//3
  };
  // Note single row of list for each set of Triangle2D coordinates
  String[][][] TRIANGLE2D_ATTR_POINT_VALUES = {
      // TODO add index column to display, start entries here with "#",
      {},//0
      {{"0","0","1","0","1","1"}},//1 ("#" is placeholder for index column)
      {{"0","0","0.5","0.866025404","1","0"}},//2
      {{"0","0","0","1","1","0"}},//3
  };
  String[] TRIANGLE2D_ATTR_POINT_DESCRIPTIONS = {
    "Select geometry of interest then Add points",//0
    "Right triangle", //1
    "Equilateral triangle, unit sides", //2
    "Right triangle, left-hand side", //3
  };

  // Element PointSet
  String POINTSET_ELNAME = "PointSet";

  // Element IndexedLineSet
  String  INDEXEDLINESET_ELNAME                   = "IndexedLineSet";
  String  INDEXEDLINESET_ATTR_COLORINDEX_NAME     = "colorIndex";
  boolean INDEXEDLINESET_ATTR_COLORINDEX_REQD     = false;
  String  INDEXEDLINESET_ATTR_COLORINDEX_DFLT     = "";
  String  INDEXEDLINESET_ATTR_COLORPERVERTEX_NAME = "colorPerVertex";
  boolean INDEXEDLINESET_ATTR_COLORPERVERTEX_REQD = false;
  String  INDEXEDLINESET_ATTR_COLORPERVERTEX_DFLT = "true";
  String  INDEXEDLINESET_ATTR_COORDINDEX_NAME     = "coordIndex";
  boolean INDEXEDLINESET_ATTR_COORDINDEX_REQD     = false;
  String  INDEXEDLINESET_ATTR_COORDINDEX_DFLT     = "";

  // Element LineSet
  String  LINESET_ELNAME                = "LineSet";
  String  LINESET_ATTR_VERTEXCOUNT_NAME = "vertexCount";
  boolean LINESET_ATTR_VERTEXCOUNT_REQD = false;
  String  LINESET_ATTR_VERTEXCOUNT_DFLT = "";

  // Element IndexedFaceSet
  String  INDEXEDFACESET_ELNAME           = "IndexedFaceSet";
  String  INDEXEDFACESET_ATTR_CCW_NAME    = "ccw";
  boolean INDEXEDFACESET_ATTR_CCW_REQD    = false;
  String  INDEXEDFACESET_ATTR_CCW_DFLT    = "true";
  String  INDEXEDFACESET_ATTR_CONVEX_NAME = "convex";
  boolean INDEXEDFACESET_ATTR_CONVEX_REQD = false;
  String  INDEXEDFACESET_ATTR_CONVEX_DFLT = "true";
  String  INDEXEDFACESET_ATTR_SOLID_NAME  = "solid";
  boolean INDEXEDFACESET_ATTR_SOLID_REQD  = false;
  String  INDEXEDFACESET_ATTR_SOLID_DFLT  = "true";
  String  INDEXEDFACESET_ATTR_CREASEANGLE_NAME     = "creaseAngle";
  boolean INDEXEDFACESET_ATTR_CREASEANGLE_REQD     = false;
  String  INDEXEDFACESET_ATTR_CREASEANGLE_DFLT     = "0";
  String  INDEXEDFACESET_ATTR_COLORPERVERTEX_NAME  = "colorPerVertex";
  boolean INDEXEDFACESET_ATTR_COLORPERVERTEX_REQD  = false;
  String  INDEXEDFACESET_ATTR_COLORPERVERTEX_DFLT  = "true";
  String  INDEXEDFACESET_ATTR_NORMALPERVERTEX_NAME = "normalPerVertex";
  boolean INDEXEDFACESET_ATTR_NORMALPERVERTEX_REQD = false;
  String  INDEXEDFACESET_ATTR_NORMALPERVERTEX_DFLT = "true";
  String  INDEXEDFACESET_ATTR_COLORINDEX_NAME    = "colorIndex";
  boolean INDEXEDFACESET_ATTR_COLORINDEX_REQD    = false;
  String  INDEXEDFACESET_ATTR_COLORINDEX_DFLT    = "";
  String  INDEXEDFACESET_ATTR_COORDINDEX_NAME    = "coordIndex";
  boolean INDEXEDFACESET_ATTR_COORDINDEX_REQD    = false;
  String  INDEXEDFACESET_ATTR_COORDINDEX_DFLT    = "";
  String  INDEXEDFACESET_ATTR_NORMALINDEX_NAME   = "normalIndex";
  boolean INDEXEDFACESET_ATTR_NORMALINDEX_REQD   = false;
  String  INDEXEDFACESET_ATTR_NORMALINDEX_DFLT   = "";
  String  INDEXEDFACESET_ATTR_TEXCOORDINDEX_NAME = "texCoordIndex";
  boolean INDEXEDFACESET_ATTR_TEXCOORDINDEX_REQD = false;
  String  INDEXEDFACESET_ATTR_TEXCOORDINDEX_DFLT = "";

  // Element ElevationGrid
  String  ELEVATIONGRID_ELNAME                    = "ElevationGrid";
  String  ELEVATIONGRID_ATTR_CCW_NAME             = "ccw";
  boolean ELEVATIONGRID_ATTR_CCW_REQD             = false;
  String  ELEVATIONGRID_ATTR_CCW_DFLT             = "true";
  String  ELEVATIONGRID_ATTR_SOLID_NAME           = "solid";
  boolean ELEVATIONGRID_ATTR_SOLID_REQD           = false;
  String  ELEVATIONGRID_ATTR_SOLID_DFLT           = "true";
  String  ELEVATIONGRID_ATTR_COLORPERVERTEX_NAME  = "colorPerVertex";
  boolean ELEVATIONGRID_ATTR_COLORPERVERTEX_REQD  = false;
  String  ELEVATIONGRID_ATTR_COLORPERVERTEX_DFLT  = "true";
  String  ELEVATIONGRID_ATTR_NORMALPERVERTEX_NAME = "normalPerVertex";
  boolean ELEVATIONGRID_ATTR_NORMALPERVERTEX_REQD = false;
  String  ELEVATIONGRID_ATTR_NORMALPERVERTEX_DFLT = "true";
  String  ELEVATIONGRID_ATTR_CREASEANGLE_NAME     = "creaseAngle";
  boolean ELEVATIONGRID_ATTR_CREASEANGLE_REQD     = false;
  String  ELEVATIONGRID_ATTR_CREASEANGLE_DFLT     = "0";
  String  ELEVATIONGRID_ATTR_XDIMENSION_NAME      = "xDimension";
  boolean ELEVATIONGRID_ATTR_XDIMENSION_REQD      = false;
  String  ELEVATIONGRID_ATTR_XDIMENSION_DFLT      = "2";
  String  ELEVATIONGRID_ATTR_XSPACING_NAME        = "xSpacing";
  boolean ELEVATIONGRID_ATTR_XSPACING_REQD        = false;
  String  ELEVATIONGRID_ATTR_XSPACING_DFLT        = "1.0";
  String  ELEVATIONGRID_ATTR_ZDIMENSION_NAME      = "zDimension";
  boolean ELEVATIONGRID_ATTR_ZDIMENSION_REQD      = false;
  String  ELEVATIONGRID_ATTR_ZDIMENSION_DFLT      = "2";
  String  ELEVATIONGRID_ATTR_ZSPACING_NAME        = "zSpacing";
  boolean ELEVATIONGRID_ATTR_ZSPACING_REQD        = false;
  String  ELEVATIONGRID_ATTR_ZSPACING_DFLT        = "1.0";
  String  ELEVATIONGRID_ATTR_HEIGHT_NAME          = "height";
  boolean ELEVATIONGRID_ATTR_HEIGHT_REQD          = false;
  String  ELEVATIONGRID_ATTR_HEIGHT_DFLT          = "0 0 0 0";

  // Element Extrusion

  String EXTRUSION_ELNAME                  = "Extrusion";
  String  EXTRUSION_ATTR_CCW_NAME          = "ccw";
  boolean EXTRUSION_ATTR_CCW_REQD          = false;
  String  EXTRUSION_ATTR_CCW_DFLT          = "true";
  String  EXTRUSION_ATTR_SOLID_NAME        = "solid";
  boolean EXTRUSION_ATTR_SOLID_REQD        = false;
  String  EXTRUSION_ATTR_SOLID_DFLT        = "true";
  String  EXTRUSION_ATTR_CONVEX_NAME       = "convex";
  boolean EXTRUSION_ATTR_CONVEX_REQD       = false;
  String  EXTRUSION_ATTR_CONVEX_DFLT       = "true";
  String  EXTRUSION_ATTR_BEGINCAP_NAME     = "beginCap";
  boolean EXTRUSION_ATTR_BEGINCAP_REQD     = false;
  String  EXTRUSION_ATTR_BEGINCAP_DFLT     = "true";
  String  EXTRUSION_ATTR_ENDCAP_NAME       = "endCap";
  boolean EXTRUSION_ATTR_ENDCAP_REQD       = false;
  String  EXTRUSION_ATTR_ENDCAP_DFLT       = "true";
  String  EXTRUSION_ATTR_CREASEANGLE_NAME  = "creaseAngle";
  boolean EXTRUSION_ATTR_CREASEANGLE_REQD  = false;
  String  EXTRUSION_ATTR_CREASEANGLE_DFLT  = "0";
  String  EXTRUSION_ATTR_CROSSSECTION_NAME = "crossSection";
  boolean EXTRUSION_ATTR_CROSSSECTION_REQD = false;
  String  EXTRUSION_ATTR_CROSSSECTION_DFLT = "1 1, 1 -1, -1 -1, -1 1, 1 1";
  String  EXTRUSION_ATTR_SPINE_NAME        = "spine";
  boolean EXTRUSION_ATTR_SPINE_REQD        = false;
  String  EXTRUSION_ATTR_SPINE_DFLT        = "0 0 0, 0 1 0";
  String  EXTRUSION_ATTR_SCALE_NAME        = "scale";
  boolean EXTRUSION_ATTR_SCALE_REQD        = false;
  String  EXTRUSION_ATTR_SCALE_DFLT        = "1 1";
  String  EXTRUSION_ATTR_ORIENTATION_NAME  = "orientation";
  boolean EXTRUSION_ATTR_ORIENTATION_REQD  = false;
  String  EXTRUSION_ATTR_ORIENTATION_DFLT  = "0 0 1 0";

  // Element TimeSensor
  String  TIMESENSOR_ELNAME                  = "TimeSensor";
  String  TIMESENSOR_ATTR_CYCLEINTERVAL_NAME = "cycleInterval";
  boolean TIMESENSOR_ATTR_CYCLEINTERVAL_REQD = false;
  String  TIMESENSOR_ATTR_CYCLEINTERVAL_DFLT = "1";
  String  TIMESENSOR_ATTR_STARTTIME_NAME     = "startTime";
  boolean TIMESENSOR_ATTR_STARTTIME_REQD     = false;
  String  TIMESENSOR_ATTR_STARTTIME_DFLT     = "0";
  String  TIMESENSOR_ATTR_STOPTIME_NAME      = "stopTime";
  boolean TIMESENSOR_ATTR_STOPTIME_REQD      = false;
  String  TIMESENSOR_ATTR_STOPTIME_DFLT      = "0";
  String  TIMESENSOR_ATTR_PAUSETIME_NAME     = "pauseTime";
  boolean TIMESENSOR_ATTR_PAUSETIME_REQD     = false;
  String  TIMESENSOR_ATTR_PAUSETIME_DFLT     = "0";
  String  TIMESENSOR_ATTR_RESUMETIME_NAME    = "resumeTime";
  boolean TIMESENSOR_ATTR_RESUMETIME_REQD    = false;
  String  TIMESENSOR_ATTR_RESUMETIME_DFLT    = "0";
  String  TIMESENSOR_ATTR_ENABLED_NAME       = "enabled";
  boolean TIMESENSOR_ATTR_ENABLED_REQD       = false;
  String  TIMESENSOR_ATTR_ENABLED_DFLT       = "true";
  String  TIMESENSOR_ATTR_LOOP_NAME          = "loop";
  boolean TIMESENSOR_ATTR_LOOP_REQD          = false;
  String  TIMESENSOR_ATTR_LOOP_DFLT          = "false";

  String  BASEINTERPOLATOR_ATTR_KEY_NAME         = "key";
  boolean BASEINTERPOLATOR_ATTR_KEY_REQD         = false;
  String  BASEINTERPOLATOR_ATTR_KEY_DFLT         = "";
  String  BASEINTERPOLATOR_ATTR_KEYVALUE_NAME    = "keyValue";
  boolean BASEINTERPOLATOR_ATTR_KEYVALUE_REQD    = false;
  String  BASEINTERPOLATOR_ATTR_KEYVALUE_DFLT    = "";
  String  BASEINTERPOLATOR_ATTR_SETFRACTION_NAME = "set_fraction";
  boolean BASEINTERPOLATOR_ATTR_SETFRACTION_REQD = false;
  String  BASEINTERPOLATOR_ATTR_SETFRACTION_DFLT = "";

  // Element PositionInterpolator
  String  POSITIONINTERPOLATOR_ELNAME             = "PositionInterpolator";
  String  POSITIONINTERPOLATOR_ATTR_KEY_NAME      = "key";
  boolean POSITIONINTERPOLATOR_ATTR_KEY_REQD      = false;
  String  POSITIONINTERPOLATOR_ATTR_KEY_DFLT      = "";
  String  POSITIONINTERPOLATOR_ATTR_KEYVALUE_NAME = "keyValue";
  boolean POSITIONINTERPOLATOR_ATTR_KEYVALUE_REQD = false;
  String  POSITIONINTERPOLATOR_ATTR_KEYVALUE_DFLT = "";

  // Element OrientationInterpolator
  String  ORIENTATIONINTERPOLATOR_ELNAME             = "OrientationInterpolator";
  String  ORIENTATIONINTERPOLATOR_ATTR_KEY_NAME      = "key";
  boolean ORIENTATIONINTERPOLATOR_ATTR_KEY_REQD      = false;
  String  ORIENTATIONINTERPOLATOR_ATTR_KEY_DFLT      = "";
  String  ORIENTATIONINTERPOLATOR_ATTR_KEYVALUE_NAME = "keyValue";
  boolean ORIENTATIONINTERPOLATOR_ATTR_KEYVALUE_REQD = false;
  String  ORIENTATIONINTERPOLATOR_ATTR_KEYVALUE_DFLT = "";

  // Element NormalInterpolator
  String  NORMALINTERPOLATOR_ELNAME             = "NormalInterpolator";
  String  NORMALINTERPOLATOR_ATTR_KEY_NAME      = "key";
  boolean NORMALINTERPOLATOR_ATTR_KEY_REQD      = false;
  String  NORMALINTERPOLATOR_ATTR_KEY_DFLT      = "";
  String  NORMALINTERPOLATOR_ATTR_KEYVALUE_NAME = "keyValue";
  boolean NORMALINTERPOLATOR_ATTR_KEYVALUE_REQD = false;
  String  NORMALINTERPOLATOR_ATTR_KEYVALUE_DFLT = "";

  // Element ColorInterpolator
  String  COLORINTERPOLATOR_ELNAME             = "ColorInterpolator";
  String  COLORINTERPOLATOR_ATTR_KEY_NAME      = "key";
  boolean COLORINTERPOLATOR_ATTR_KEY_REQD      = false;
  String  COLORINTERPOLATOR_ATTR_KEY_DFLT      = "";
  String  COLORINTERPOLATOR_ATTR_KEYVALUE_NAME = "keyValue";
  boolean COLORINTERPOLATOR_ATTR_KEYVALUE_REQD = false;
  String  COLORINTERPOLATOR_ATTR_KEYVALUE_DFLT = "";

  // Element CoordinateInterpolator
  String  COORDINATEINTERPOLATOR_ELNAME             = "CoordinateInterpolator";
  String  COORDINATEINTERPOLATOR_ATTR_KEY_NAME      = "key";
  boolean COORDINATEINTERPOLATOR_ATTR_KEY_REQD      = false;
  String  COORDINATEINTERPOLATOR_ATTR_KEY_DFLT      = "";
  String  COORDINATEINTERPOLATOR_ATTR_KEYVALUE_NAME = "keyValue";
  boolean COORDINATEINTERPOLATOR_ATTR_KEYVALUE_REQD = false;
  String  COORDINATEINTERPOLATOR_ATTR_KEYVALUE_DFLT = "";

  // Element PositionInterpolator2D
  String  POSITIONINTERPOLATOR2D_ELNAME             = "PositionInterpolator2D";
  String  POSITIONINTERPOLATOR2D_ATTR_KEY_NAME      = "key";
  boolean POSITIONINTERPOLATOR2D_ATTR_KEY_REQD      = false;
  String  POSITIONINTERPOLATOR2D_ATTR_KEY_DFLT      = "";
  String  POSITIONINTERPOLATOR2D_ATTR_KEYVALUE_NAME = "keyValue";
  boolean POSITIONINTERPOLATOR2D_ATTR_KEYVALUE_REQD = false;
  String  POSITIONINTERPOLATOR2D_ATTR_KEYVALUE_DFLT = "";

  // Element ScalarInterpolator
  String  SCALARINTERPOLATOR_ELNAME             = "ScalarInterpolator";
  String  SCALARINTERPOLATOR_ATTR_KEY_NAME      = "key";
  boolean SCALARINTERPOLATOR_ATTR_KEY_REQD      = false;
  String  SCALARINTERPOLATOR_ATTR_KEY_DFLT      = "";
  String  SCALARINTERPOLATOR_ATTR_KEYVALUE_NAME = "keyValue";
  boolean SCALARINTERPOLATOR_ATTR_KEYVALUE_REQD = false;
  String  SCALARINTERPOLATOR_ATTR_KEYVALUE_DFLT = "";

 // Element CoordinateInterpolator2D
  String  COORDINATEINTERPOLATOR2D_ELNAME             = "CoordinateInterpolator2D";
  String  COORDINATEINTERPOLATOR2D_ATTR_KEY_NAME      = "key";
  boolean COORDINATEINTERPOLATOR2D_ATTR_KEY_REQD      = false;
  String  COORDINATEINTERPOLATOR2D_ATTR_KEY_DFLT      = "";
  String  COORDINATEINTERPOLATOR2D_ATTR_KEYVALUE_NAME = "keyValue";
  boolean COORDINATEINTERPOLATOR2D_ATTR_KEYVALUE_REQD = false;
  String  COORDINATEINTERPOLATOR2D_ATTR_KEYVALUE_DFLT = "";

 // Element TouchSensor
  String  TOUCHSENSOR_ELNAME                = "TouchSensor";
  String  TOUCHSENSOR_ATTR_ENABLED_NAME     = "enabled";
  boolean TOUCHSENSOR_ATTR_ENABLED_REQD     = false;
  String  TOUCHSENSOR_ATTR_ENABLED_DFLT     = "true";
  String  TOUCHSENSOR_ATTR_DESCRIPTION_NAME = "description";
  boolean TOUCHSENSOR_ATTR_DESCRIPTION_REQD = false;
  String  TOUCHSENSOR_ATTR_DESCRIPTION_DFLT = "";

  // Element PlaneSensor
  String  PLANESENSOR_ELNAME                = "PlaneSensor";
  String  PLANESENSOR_ATTR_ENABLED_NAME     = "enabled";
  boolean PLANESENSOR_ATTR_ENABLED_REQD     = false;
  String  PLANESENSOR_ATTR_ENABLED_DFLT     = "true";
  String  PLANESENSOR_ATTR_DESCRIPTION_NAME = "description";
  boolean PLANESENSOR_ATTR_DESCRIPTION_REQD = false;
  String  PLANESENSOR_ATTR_DESCRIPTION_DFLT = "";
  String  PLANESENSOR_ATTR_MINPOSITION_NAME = "minPosition";
  boolean PLANESENSOR_ATTR_MINPOSITION_REQD = false;
  String  PLANESENSOR_ATTR_MINPOSITION_DFLT = "0 0";
  String  PLANESENSOR_ATTR_MAXPOSITION_NAME = "maxPosition";
  boolean PLANESENSOR_ATTR_MAXPOSITION_REQD = false;
  String  PLANESENSOR_ATTR_MAXPOSITION_DFLT = "-1 -1";
  String  PLANESENSOR_ATTR_OFFSET_NAME      = "offset";
  boolean PLANESENSOR_ATTR_OFFSET_REQD      = false;
  String  PLANESENSOR_ATTR_OFFSET_DFLT      = "0 0 0";
  String  PLANESENSOR_ATTR_AUTOOFFSET_NAME  = "autoOffset";
  boolean PLANESENSOR_ATTR_AUTOOFFSET_REQD  = false;
  String  PLANESENSOR_ATTR_AUTOOFFSET_DFLT  = "true";
  String  PLANESENSOR_ATTR_AXISROTATION_NAME= "axisRotation";
  boolean PLANESENSOR_ATTR_AXISROTATION_REQD= false;
  String  PLANESENSOR_ATTR_AXISROTATION_DFLT= "0 0 1 0";

  // Element CylinderSensor
  String  CYLINDERSENSOR_ELNAME                = "CylinderSensor";
  String  CYLINDERSENSOR_ATTR_ENABLED_NAME     = "enabled";
  boolean CYLINDERSENSOR_ATTR_ENABLED_REQD     = false;
  String  CYLINDERSENSOR_ATTR_ENABLED_DFLT     = "true";
  String  CYLINDERSENSOR_ATTR_DESCRIPTION_NAME = "description";
  boolean CYLINDERSENSOR_ATTR_DESCRIPTION_REQD = false;
  String  CYLINDERSENSOR_ATTR_DESCRIPTION_DFLT = "";
  String  CYLINDERSENSOR_ATTR_MINANGLE_NAME    = "minAngle";
  boolean CYLINDERSENSOR_ATTR_MINANGLE_REQD    = false;
  String  CYLINDERSENSOR_ATTR_MINANGLE_DFLT    = "0";
  String  CYLINDERSENSOR_ATTR_MAXANGLE_NAME    = "maxAngle";
  boolean CYLINDERSENSOR_ATTR_MAXANGLE_REQD    = false;
  String  CYLINDERSENSOR_ATTR_MAXANGLE_DFLT    = "0";
  String  CYLINDERSENSOR_ATTR_DISKANGLE_NAME   = "diskAngle";
  boolean CYLINDERSENSOR_ATTR_DISKANGLE_REQD   = false;
  String  CYLINDERSENSOR_ATTR_DISKANGLE_DFLT   = "0.2618"; // +(float)Math.PI/12.0f;
  String  CYLINDERSENSOR_ATTR_OFFSET_NAME      = "offset";
  boolean CYLINDERSENSOR_ATTR_OFFSET_REQD      = false;
  String  CYLINDERSENSOR_ATTR_OFFSET_DFLT      = "0";
  String  CYLINDERSENSOR_ATTR_AUTOOFFSET_NAME  = "autoOffset";
  boolean CYLINDERSENSOR_ATTR_AUTOOFFSET_REQD  = false;
  String  CYLINDERSENSOR_ATTR_AUTOOFFSET_DFLT  = "true";
  String  CYLINDERSENSOR_ATTR_AXISROTATION_NAME= "axisRotation";
  boolean CYLINDERSENSOR_ATTR_AXISROTATION_REQD= false;
  String  CYLINDERSENSOR_ATTR_AXISROTATION_DFLT= "0 0 1 0";

  // Element SphereSensor
  String  SPHERESENSOR_ELNAME                = "SphereSensor";
  String  SPHERESENSOR_ATTR_DESCRIPTION_NAME = "description";
  boolean SPHERESENSOR_ATTR_DESCRIPTION_REQD = false;
  String  SPHERESENSOR_ATTR_DESCRIPTION_DFLT = "";
  String  SPHERESENSOR_ATTR_ENABLED_NAME     = "enabled";
  boolean SPHERESENSOR_ATTR_ENABLED_REQD     = false;
  String  SPHERESENSOR_ATTR_ENABLED_DFLT     = "true";
  String  SPHERESENSOR_ATTR_AUTOOFFSET_NAME  = "autoOffset";
  boolean SPHERESENSOR_ATTR_AUTOOFFSET_REQD  = false;
  String  SPHERESENSOR_ATTR_AUTOOFFSET_DFLT  = "true";
  String  SPHERESENSOR_ATTR_OFFSET_NAME      = "offset";
  boolean SPHERESENSOR_ATTR_OFFSET_REQD      = false;
  String  SPHERESENSOR_ATTR_OFFSET_DFLT      = "0 1 0 0";

  // Element KeySensor
  String  KEYSENSOR_ELNAME            = "KeySensor";
  String  KEYSENSOR_ATTR_ENABLED_NAME = "enabled";
  boolean KEYSENSOR_ATTR_ENABLED_REQD = false;
  String  KEYSENSOR_ATTR_ENABLED_DFLT = "true";

  // Element StringSensor
  String  STRINGSENSOR_ELNAME                    = "StringSensor";
  String  STRINGSENSOR_ATTR_DELETIONALLOWED_NAME = "deletionAllowed";
  boolean STRINGSENSOR_ATTR_DELETIONALLOWED_REQD = false;
  String  STRINGSENSOR_ATTR_DELETIONALLOWED_DFLT = "true";
  String  STRINGSENSOR_ATTR_ENABLED_NAME         = "enabled";
  boolean STRINGSENSOR_ATTR_ENABLED_REQD         = false;
  String  STRINGSENSOR_ATTR_ENABLED_DFLT         = "true";

  // Event elements
  String  BOOLEANFILTER_ELNAME    = "BooleanFilter";

  String  BOOLEANTOGGLE_ELNAME                   = "BooleanToggle";
  String  BOOLEANTOGGLE_ATTR_TOGGLE_NAME         = "toggle";
  boolean BOOLEANTOGGLE_ATTR_TOGGLE_REQD         = false;
  String  BOOLEANTOGGLE_ATTR_TOGGLE_DFLT         = "true";

  String  BOOLEANTRIGGER_ELNAME   = "BooleanTrigger";

  String  TIMETRIGGER_ELNAME      = "TimeTrigger";

  // Element Script
  String  SCRIPT_ELNAME = "Script";
  String  SCRIPT_ATTR_URL_NAME          = "url";
  boolean SCRIPT_ATTR_URL_REQD          = false;
  String  SCRIPT_ATTR_URL_DFLT          = "";
  String  SCRIPT_ATTR_DIRECTOUTPUT_NAME = "directOutput";
  boolean SCRIPT_ATTR_DIRECTOUTPUT_REQD = false;
  String  SCRIPT_ATTR_DIRECTOUTPUT_DFLT = "false";
  String  SCRIPT_ATTR_MUSTEVALUATE_NAME = "mustEvaluate";
  boolean SCRIPT_ATTR_MUSTEVALUATE_REQD = false;
  String  SCRIPT_ATTR_MUSTEVALUATE_DFLT = "false";

  // Element BooleanSequencer
  String  BOOLEANSEQUENCER_ELNAME             = "BooleanSequencer";
  String  BOOLEANSEQUENCER_ATTR_KEY_NAME      = "key";
  boolean BOOLEANSEQUENCER_ATTR_KEY_REQD      = false;
  String  BOOLEANSEQUENCER_ATTR_KEY_DFLT      = "";
  String  BOOLEANSEQUENCER_ATTR_KEYVALUE_NAME = "keyValue";
  boolean BOOLEANSEQUENCER_ATTR_KEYVALUE_REQD = false;
  String  BOOLEANSEQUENCER_ATTR_KEYVALUE_DFLT = "";

 // Element IntegerSequencer
  String  INTEGERSEQUENCER_ELNAME             = "IntegerSequencer";
  String  INTEGERSEQUENCER_ATTR_KEY_NAME      = "key";
  boolean INTEGERSEQUENCER_ATTR_KEY_REQD      = false;
  String  INTEGERSEQUENCER_ATTR_KEY_DFLT      = "";
  String  INTEGERSEQUENCER_ATTR_KEYVALUE_NAME = "keyValue";
  boolean INTEGERSEQUENCER_ATTR_KEYVALUE_REQD = false;
  String  INTEGERSEQUENCER_ATTR_KEYVALUE_DFLT = "";

  // Element IntegerTrigger
  String  INTEGERTRIGGER_ELNAME               = "IntegerTrigger";
  String  INTEGERTRIGGER_ATTR_INTEGERKEY_NAME = "integerKey";
  boolean INTEGERTRIGGER_ATTR_INTEGERKEY_REQD = false;
  String  INTEGERTRIGGER_ATTR_INTEGERKEY_DFLT = "-1";

  // Element LoadSensor
  String  LOADSENSOR_ELNAME              = "LoadSensor";
  String  LOADSENSOR_ATTR_ENABLED_NAME   = "enabled";
  boolean LOADSENSOR_ATTR_ENABLED_REQD   = false;
  String  LOADSENSOR_ATTR_ENABLED_DFLT   = "true";
  String  LOADSENSOR_ATTR_TIMEOUT_NAME   = "timeOut";
  boolean LOADSENSOR_ATTR_TIMEOUT_REQD   = false;
  String  LOADSENSOR_ATTR_TIMEOUT_DFLT   = "0";

  /* wrong...this is a X3DSoundSourceNode type */

  String  LOADSENSOR_ATTR_DESCRIPTION_NAME  = "enabled";

  boolean LOADSENSOR_ATTR_DESCRIPTION_REQD  = false;
  String  LOADSENSOR_ATTR_DESCRIPTION_DFLT  = "";
  String  LOADSENSOR_ATTR_LOOP_NAME         = "loop";
  boolean LOADSENSOR_ATTR_LOOP_REQD         = false;
  String  LOADSENSOR_ATTR_LOOP_DFLT         = "false";
  String  LOADSENSOR_ATTR_PITCH_NAME        = "pitch";
  boolean LOADSENSOR_ATTR_PITCH_REQD        = false;
  String  LOADSENSOR_ATTR_PITCH_DFLT        = "1.0";
  String  LOADSENSOR_ATTR_PAUSETIME_NAME    = "pauseTime";
  boolean LOADSENSOR_ATTR_PAUSETIME_REQD    = false;
  String  LOADSENSOR_ATTR_PAUSETIME_DFLT    = "0";
  String  LOADSENSOR_ATTR_RESUMETIME_NAME   = "resumeTime";
  boolean LOADSENSOR_ATTR_RESUMETIME_REQD   = false;
  String  LOADSENSOR_ATTR_RESUMETIME_DFLT   = "0";
  String  LOADSENSOR_ATTR_STARTTIME_NAME    = "startTime";
  boolean LOADSENSOR_ATTR_STARTTIME_REQD    = false;
  String  LOADSENSOR_ATTR_STARTTIME_DFLT    = "0";
  String  LOADSENSOR_ATTR_STOPTIME_NAME     = "stopTime";
  boolean LOADSENSOR_ATTR_STOPTIME_REQD     = false;
  String  LOADSENSOR_ATTR_STOPTIME_DFLT     = "0";
  String  LOADSENSOR_ATTR_URL_NAME          = "url";
  boolean LOADSENSOR_ATTR_URL_REQD          = false;
  String  LOADSENSOR_ATTR_URL_DFLT          = "";

  // ProximitySensor element
  String  PROXIMITYSENSOR_ELNAME            = "ProximitySensor";
  String  PROXIMITYSENSOR_ATTR_ENABLED_NAME = "enabled";
  boolean PROXIMITYSENSOR_ATTR_ENABLED_REQD = false;
  String  PROXIMITYSENSOR_ATTR_ENABLED_DFLT = "true";
  String  PROXIMITYSENSOR_ATTR_CENTER_NAME  = "center";
  boolean PROXIMITYSENSOR_ATTR_CENTER_REQD  = false;
  String  PROXIMITYSENSOR_ATTR_CENTER_DFLT  = "0 0 0";
  String  PROXIMITYSENSOR_ATTR_SIZE_NAME    = "size";
  boolean PROXIMITYSENSOR_ATTR_SIZE_REQD    = false;
  String  PROXIMITYSENSOR_ATTR_SIZE_DFLT    = "0 0 0";

  // VisibilitySensor element
  String  VISIBILITYSENSOR_ELNAME            = "VisibilitySensor";
  String  VISIBILITYSENSOR_ATTR_ENABLED_NAME = "enabled";
  boolean VISIBILITYSENSOR_ATTR_ENABLED_REQD = false;
  String  VISIBILITYSENSOR_ATTR_ENABLED_DFLT = "true";
  String  VISIBILITYSENSOR_ATTR_CENTER_NAME  = "center";
  boolean VISIBILITYSENSOR_ATTR_CENTER_REQD  = false;
  String  VISIBILITYSENSOR_ATTR_CENTER_DFLT  = "0 0 0";
  String  VISIBILITYSENSOR_ATTR_SIZE_NAME    = "size";
  boolean VISIBILITYSENSOR_ATTR_SIZE_REQD    = false;
  String  VISIBILITYSENSOR_ATTR_SIZE_DFLT    = "0 0 0";


  // Sound element
  String  SOUND_ELNAME               = "Sound";
  String  SOUND_ATTR_LOCATION_NAME   = "location";
  boolean SOUND_ATTR_LOCATION_REQD   = false;
  String  SOUND_ATTR_LOCATION_DFLT   = "0 0 0";
  String  SOUND_ATTR_DIRECTION_NAME  = "direction";
  boolean SOUND_ATTR_DIRECTION_REQD  = false;
  String  SOUND_ATTR_DIRECTION_DFLT  = "0 0 1";
  String  SOUND_ATTR_INTENSITY_NAME  = "intensity";
  boolean SOUND_ATTR_INTENSITY_REQD  = false;
  String  SOUND_ATTR_INTENSITY_DFLT  = "1";
  String  SOUND_ATTR_PRIORITY_NAME   = "priority";
  boolean SOUND_ATTR_PRIORITY_REQD   = false;
  String  SOUND_ATTR_PRIORITY_DFLT   = "0";
  String  SOUND_ATTR_SPATIALIZE_NAME = "spatialize";
  boolean SOUND_ATTR_SPATIALIZE_REQD = false;
  String  SOUND_ATTR_SPATIALIZE_DFLT = "true";
  String  SOUND_ATTR_MINFRONT_NAME   = "minFront";
  boolean SOUND_ATTR_MINFRONT_REQD   = false;
  String  SOUND_ATTR_MINFRONT_DFLT   = "1";
  String  SOUND_ATTR_MINBACK_NAME    = "minBack";
  boolean SOUND_ATTR_MINBACK_REQD    = false;
  String  SOUND_ATTR_MINBACK_DFLT    = "1";
  String  SOUND_ATTR_MAXFRONT_NAME   = "maxFront";
  boolean SOUND_ATTR_MAXFRONT_REQD   = false;
  String  SOUND_ATTR_MAXFRONT_DFLT   = "10";
  String  SOUND_ATTR_MAXBACK_NAME    = "maxBack";
  boolean SOUND_ATTR_MAXBACK_REQD    = false;
  String  SOUND_ATTR_MAXBACK_DFLT    = "10";

  // AcousticProperties element
  String  ACOUSTICPROPERTIES_ELNAME                = "AcousticProperties";
  String  ACOUSTICPROPERTIES_ATTR_DESCRIPTION_NAME = "description";
  boolean ACOUSTICPROPERTIES_ATTR_DESCRIPTION_REQD = false;
  String  ACOUSTICPROPERTIES_ATTR_DESCRIPTION_DFLT = "";
  String  ACOUSTICPROPERTIES_ATTR_ENABLED_NAME     = "enabled";
  boolean ACOUSTICPROPERTIES_ATTR_ENABLED_REQD     = false;
  String  ACOUSTICPROPERTIES_ATTR_ENABLED_DFLT     = "true";
  String  ACOUSTICPROPERTIES_ATTR_ABSORPTION_NAME  = "absorption";
  boolean ACOUSTICPROPERTIES_ATTR_ABSORPTION_REQD  = false;
  String  ACOUSTICPROPERTIES_ATTR_ABSORPTION_DFLT  = "0";
  String  ACOUSTICPROPERTIES_ATTR_DIFFUSE_NAME     = "diffuse";
  boolean ACOUSTICPROPERTIES_ATTR_DIFFUSE_REQD     = false;
  String  ACOUSTICPROPERTIES_ATTR_DIFFUSE_DFLT     = "0";
  String  ACOUSTICPROPERTIES_ATTR_REFRACTION_NAME  = "refraction";
  boolean ACOUSTICPROPERTIES_ATTR_REFRACTION_REQD  = false;
  String  ACOUSTICPROPERTIES_ATTR_REFRACTION_DFLT  = "0";
  String  ACOUSTICPROPERTIES_ATTR_SPECULAR_NAME    = "specular";
  boolean ACOUSTICPROPERTIES_ATTR_SPECULAR_REQD    = false;
  String  ACOUSTICPROPERTIES_ATTR_SPECULAR_DFLT    = "0";

  // Analyser element
  String  ANALYSER_ELNAME                = "Analyser";
  String  ANALYSER_ATTR_DESCRIPTION_NAME = "description";
  boolean ANALYSER_ATTR_DESCRIPTION_REQD = false;
  String  ANALYSER_ATTR_DESCRIPTION_DFLT = "";
  String  ANALYSER_ATTR_ENABLED_NAME     = "enabled";
  boolean ANALYSER_ATTR_ENABLED_REQD     = false;
  String  ANALYSER_ATTR_ENABLED_DFLT     = "true";
  String  ANALYSER_ATTR_CHANNELCOUNTMODE_NAME  = "channelCountMode";
  boolean ANALYSER_ATTR_CHANNELCOUNTMODE_REQD  = false;
  String  ANALYSER_ATTR_CHANNELCOUNTMODE_DFLT  = "max";
  String  ANALYSER_ATTR_CHANNELINTERPRETATION_NAME  = "channelInterpretation";
  boolean ANALYSER_ATTR_CHANNELINTERPRETATION_REQD  = false;
  String  ANALYSER_ATTR_CHANNELINTERPRETATION_DFLT  = "speakers";
  String  ANALYSER_ATTR_FFTSIZE_NAME  = "fftSize";
  boolean ANALYSER_ATTR_FFTSIZE_REQD  = false;
  String  ANALYSER_ATTR_FFTSIZE_DFLT  = "2048";
  String  ANALYSER_ATTR_FREQUENCYBINCOUNT_NAME  = "frequencyBinCount";
  boolean ANALYSER_ATTR_FREQUENCYBINCOUNT_REQD  = false;
  String  ANALYSER_ATTR_FREQUENCYBINCOUNT_DFLT  = "1024";
  String  ANALYSER_ATTR_GAIN_NAME  = "gain";
  boolean ANALYSER_ATTR_GAIN_REQD  = false;
  String  ANALYSER_ATTR_GAIN_DFLT  = "1";
  String  ANALYSER_ATTR_MINDECIBELS_NAME  = "minDecibels";
  boolean ANALYSER_ATTR_MINDECIBELS_REQD  = false;
  String  ANALYSER_ATTR_MINDECIBELS_DFLT  = "-100";
  String  ANALYSER_ATTR_MAXDECIBELS_NAME  = "maxDecibels";
  boolean ANALYSER_ATTR_MAXDECIBELS_REQD  = false;
  String  ANALYSER_ATTR_MAXDECIBELS_DFLT  = "-30";
  String  ANALYSER_ATTR_PAUSETIME_NAME  = "pauseTime";
  boolean ANALYSER_ATTR_PAUSETIME_REQD  = false;
  String  ANALYSER_ATTR_PAUSETIME_DFLT  = "0";
  String  ANALYSER_ATTR_RESUMETIME_NAME  = "resumeTime";
  boolean ANALYSER_ATTR_RESUMETIME_REQD  = false;
  String  ANALYSER_ATTR_RESUMETIME_DFLT  = "0";
  String  ANALYSER_ATTR_SMOOTHINGTIMECONSTANT_NAME  = "smoothingTimeConstant";
  boolean ANALYSER_ATTR_SMOOTHINGTIMECONSTANT_REQD  = false;
  String  ANALYSER_ATTR_SMOOTHINGTIMECONSTANT_DFLT  = "0.8";
  String  ANALYSER_ATTR_STARTTIME_NAME  = "startTime";
  boolean ANALYSER_ATTR_STARTTIME_REQD  = false;
  String  ANALYSER_ATTR_STARTTIME_DFLT  = "0";
  String  ANALYSER_ATTR_STOPTIME_NAME  = "stopTime";
  boolean ANALYSER_ATTR_STOPTIME_REQD  = false;
  String  ANALYSER_ATTR_STOPTIME_DFLT  = "0";
  String  ANALYSER_ATTR_TAILTIME_NAME  = "tailTime";
  boolean ANALYSER_ATTR_TAILTIME_REQD  = false;
  String  ANALYSER_ATTR_TAILTIME_DFLT  = "0";

  // AudioClip element
  String  AUDIOCLIP_ELNAME                = "AudioClip";
  String  AUDIOCLIP_ATTR_DESCRIPTION_NAME = "description";
  boolean AUDIOCLIP_ATTR_DESCRIPTION_REQD = false;
  String  AUDIOCLIP_ATTR_DESCRIPTION_DFLT = "";
  String  AUDIOCLIP_ATTR_LOOP_NAME        = "loop";
  boolean AUDIOCLIP_ATTR_LOOP_REQD        = false;
  String  AUDIOCLIP_ATTR_LOOP_DFLT        = "false";
  String  AUDIOCLIP_ATTR_PITCH_NAME       = "pitch";
  boolean AUDIOCLIP_ATTR_PITCH_REQD       = false;
  String  AUDIOCLIP_ATTR_PITCH_DFLT       = "1.0";
  String  AUDIOCLIP_ATTR_PAUSETIME_NAME   = "pauseTime";
  boolean AUDIOCLIP_ATTR_PAUSETIME_REQD   = false;
  String  AUDIOCLIP_ATTR_PAUSETIME_DFLT   = "0";
  String  AUDIOCLIP_ATTR_RESUMETIME_NAME  = "resumeTime";
  boolean AUDIOCLIP_ATTR_RESUMETIME_REQD  = false;
  String  AUDIOCLIP_ATTR_RESUMETIME_DFLT  = "0";
  String  AUDIOCLIP_ATTR_STARTTIME_NAME   = "startTime";
  boolean AUDIOCLIP_ATTR_STARTTIME_REQD   = false;
  String  AUDIOCLIP_ATTR_STARTTIME_DFLT   = "0";
  String  AUDIOCLIP_ATTR_STOPTIME_NAME    = "stopTime";
  boolean AUDIOCLIP_ATTR_STOPTIME_REQD    = false;
  String  AUDIOCLIP_ATTR_STOPTIME_DFLT    = "0";
  String  AUDIOCLIP_ATTR_URL_NAME         = "url";
  boolean AUDIOCLIP_ATTR_URL_REQD         = false;
  String  AUDIOCLIP_ATTR_URL_DFLT         = "";

  // Polyline2d element
  String  POLYLINE2D_ELNAME                 = "Polyline2D";
  String  POLYLINE2D_ATTR_LINESEGMENTS_NAME = "lineSegments";
  boolean POLYLINE2D_ATTR_LINESEGMENTS_REQD = false;
  String  POLYLINE2D_ATTR_LINESEGMENTS_DFLT = "";

  // Polypoint2d element
  String  POLYPOINT2D_ELNAME          = "Polypoint2D";
  String  POLYPOINT2D_ATTR_POINT_NAME = "point";
  boolean POLYPOINT2D_ATTR_POINT_REQD = false;
  String  POLYPOINT2D_ATTR_POINT_DFLT = "";

  // Normal element
  String  NORMAL_ELNAME           = "Normal";
  String  NORMAL_ATTR_VECTOR_NAME ="vector";
  boolean NORMAL_ATTR_VECTOR_REQD = false;
  String  NORMAL_ATTR_VECTOR_DFLT = "";

  // TriangleSet element
  String  TRIANGLESET_ELNAME                    = "TriangleSet";
  String  TRIANGLESET_ATTR_CCW_NAME             = "ccw";
  boolean TRIANGLESET_ATTR_CCW_REQD             = false;
  String  TRIANGLESET_ATTR_CCW_DFLT             = "true";
  String  TRIANGLESET_ATTR_COLORPERVERTEX_NAME  = "colorPerVertex";
  boolean TRIANGLESET_ATTR_COLORPERVERTEX_REQD  = false;
  String  TRIANGLESET_ATTR_COLORPERVERTEX_DFLT  = "true";
  String  TRIANGLESET_ATTR_NORMALPERVERTEX_NAME = "normalPerVertex";
  boolean TRIANGLESET_ATTR_NORMALPERVERTEX_REQD = false;
  String  TRIANGLESET_ATTR_NORMALPERVERTEX_DFLT = "true";
  String  TRIANGLESET_ATTR_SOLID_NAME           = "solid";
  boolean TRIANGLESET_ATTR_SOLID_REQD           = false;
  String  TRIANGLESET_ATTR_SOLID_DFLT           = "true";

  // TriangleFanSet element
  String  TRIANGLEFANSET_ELNAME                    = "TriangleFanSet";
  String  TRIANGLEFANSET_ATTR_CCW_NAME             = "ccw";
  boolean TRIANGLEFANSET_ATTR_CCW_REQD             = false;
  String  TRIANGLEFANSET_ATTR_CCW_DFLT             = "true";
  String  TRIANGLEFANSET_ATTR_COLORPERVERTEX_NAME  = "colorPerVertex";
  boolean TRIANGLEFANSET_ATTR_COLORPERVERTEX_REQD  = false;
  String  TRIANGLEFANSET_ATTR_COLORPERVERTEX_DFLT  = "true";
  String  TRIANGLEFANSET_ATTR_NORMALPERVERTEX_NAME = "normalPerVertex";
  boolean TRIANGLEFANSET_ATTR_NORMALPERVERTEX_REQD = false;
  String  TRIANGLEFANSET_ATTR_NORMALPERVERTEX_DFLT = "true";
  String  TRIANGLEFANSET_ATTR_SOLID_NAME           = "solid";
  boolean TRIANGLEFANSET_ATTR_SOLID_REQD           = false;
  String  TRIANGLEFANSET_ATTR_SOLID_DFLT           = "true";
  String  TRIANGLEFANSET_ATTR_FANCOUNT_NAME        = "fanCount";
  boolean TRIANGLEFANSET_ATTR_FANCOUNT_REQD        = false;
  String  TRIANGLEFANSET_ATTR_FANCOUNT_DFLT        = "";

  // TriangleStripSet element
  String  TRIANGLESTRIPSET_ELNAME                    = "TriangleStripSet";
  String  TRIANGLESTRIPSET_ATTR_CCW_NAME             = "ccw";
  boolean TRIANGLESTRIPSET_ATTR_CCW_REQD             = false;
  String  TRIANGLESTRIPSET_ATTR_CCW_DFLT             = "true";
  String  TRIANGLESTRIPSET_ATTR_COLORPERVERTEX_NAME  = "colorPerVertex";
  boolean TRIANGLESTRIPSET_ATTR_COLORPERVERTEX_REQD  = false;
  String  TRIANGLESTRIPSET_ATTR_COLORPERVERTEX_DFLT  = "true";
  String  TRIANGLESTRIPSET_ATTR_NORMALPERVERTEX_NAME = "normalPerVertex";
  boolean TRIANGLESTRIPSET_ATTR_NORMALPERVERTEX_REQD = false;
  String  TRIANGLESTRIPSET_ATTR_NORMALPERVERTEX_DFLT = "true";
  String  TRIANGLESTRIPSET_ATTR_SOLID_NAME           = "solid";
  boolean TRIANGLESTRIPSET_ATTR_SOLID_REQD           = false;
  String  TRIANGLESTRIPSET_ATTR_SOLID_DFLT           = "true";
  String  TRIANGLESTRIPSET_ATTR_STRIPCOUNT_NAME      = "stripCount";
  boolean TRIANGLESTRIPSET_ATTR_STRIPCOUNT_REQD      = false;
  String  TRIANGLESTRIPSET_ATTR_STRIPCOUNT_DFLT      = "";

 // QuadSet element
  String  QUADSET_ELNAME                    = "QuadSet";
  String  QUADSET_ATTR_CCW_NAME             = "ccw";
  boolean QUADSET_ATTR_CCW_REQD             = false;
  String  QUADSET_ATTR_CCW_DFLT             = "true";
  String  QUADSET_ATTR_COLORPERVERTEX_NAME  = "colorPerVertex";
  boolean QUADSET_ATTR_COLORPERVERTEX_REQD  = false;
  String  QUADSET_ATTR_COLORPERVERTEX_DFLT  = "true";
  String  QUADSET_ATTR_NORMALPERVERTEX_NAME = "normalPerVertex";
  boolean QUADSET_ATTR_NORMALPERVERTEX_REQD = false;
  String  QUADSET_ATTR_NORMALPERVERTEX_DFLT = "true";
  String  QUADSET_ATTR_SOLID_NAME           = "solid";
  boolean QUADSET_ATTR_SOLID_REQD           = false;
  String  QUADSET_ATTR_SOLID_DFLT           = "true";

  // IndexedTriangleSet element
  String  INDEXEDTRIANGLESET_ELNAME                    = "IndexedTriangleSet";
  String  INDEXEDTRIANGLESET_ATTR_CCW_NAME             = "ccw";
  boolean INDEXEDTRIANGLESET_ATTR_CCW_REQD             = false;
  String  INDEXEDTRIANGLESET_ATTR_CCW_DFLT             = "true";
  String  INDEXEDTRIANGLESET_ATTR_COLORPERVERTEX_NAME  = "colorPerVertex";
  boolean INDEXEDTRIANGLESET_ATTR_COLORPERVERTEX_REQD  = false;
  String  INDEXEDTRIANGLESET_ATTR_COLORPERVERTEX_DFLT  = "true";
  String  INDEXEDTRIANGLESET_ATTR_NORMALPERVERTEX_NAME = "normalPerVertex";
  boolean INDEXEDTRIANGLESET_ATTR_NORMALPERVERTEX_REQD = false;
  String  INDEXEDTRIANGLESET_ATTR_NORMALPERVERTEX_DFLT = "true";
  String  INDEXEDTRIANGLESET_ATTR_SOLID_NAME           = "solid";
  boolean INDEXEDTRIANGLESET_ATTR_SOLID_REQD           = false;
  String  INDEXEDTRIANGLESET_ATTR_SOLID_DFLT           = "true";
  String  INDEXEDTRIANGLESET_ATTR_INDEX_NAME           = "index";
  boolean INDEXEDTRIANGLESET_ATTR_INDEX_REQD           = false;
  String  INDEXEDTRIANGLESET_ATTR_INDEX_DFLT           = "";

  // IndexedTriangleFanSet element
  String  INDEXEDTRIANGLEFANSET_ELNAME                    = "IndexedTriangleFanSet";
  String  INDEXEDTRIANGLEFANSET_ATTR_CCW_NAME             = "ccw";
  boolean INDEXEDTRIANGLEFANSET_ATTR_CCW_REQD             = false;
  String  INDEXEDTRIANGLEFANSET_ATTR_CCW_DFLT             = "true";
  String  INDEXEDTRIANGLEFANSET_ATTR_COLORPERVERTEX_NAME  = "colorPerVertex";
  boolean INDEXEDTRIANGLEFANSET_ATTR_COLORPERVERTEX_REQD  = false;
  String  INDEXEDTRIANGLEFANSET_ATTR_COLORPERVERTEX_DFLT  = "true";
  String  INDEXEDTRIANGLEFANSET_ATTR_NORMALPERVERTEX_NAME = "normalPerVertex";
  boolean INDEXEDTRIANGLEFANSET_ATTR_NORMALPERVERTEX_REQD = false;
  String  INDEXEDTRIANGLEFANSET_ATTR_NORMALPERVERTEX_DFLT = "true";
  String  INDEXEDTRIANGLEFANSET_ATTR_SOLID_NAME           = "solid";
  boolean INDEXEDTRIANGLEFANSET_ATTR_SOLID_REQD           = false;
  String  INDEXEDTRIANGLEFANSET_ATTR_SOLID_DFLT           = "true";
  String  INDEXEDTRIANGLEFANSET_ATTR_INDEX_NAME           = "index";
  boolean INDEXEDTRIANGLEFANSET_ATTR_INDEX_REQD           = false;
  String  INDEXEDTRIANGLEFANSET_ATTR_INDEX_DFLT           = "";

  // IndexedTriangleStripSet element
  String  INDEXEDTRIANGLESTRIPSET_ELNAME                    = "IndexedTriangleStripSet";
  String  INDEXEDTRIANGLESTRIPSET_ATTR_CCW_NAME             = "ccw";
  boolean INDEXEDTRIANGLESTRIPSET_ATTR_CCW_REQD             = false;
  String  INDEXEDTRIANGLESTRIPSET_ATTR_CCW_DFLT             = "true";
  String  INDEXEDTRIANGLESTRIPSET_ATTR_COLORPERVERTEX_NAME  = "colorPerVertex";
  boolean INDEXEDTRIANGLESTRIPSET_ATTR_COLORPERVERTEX_REQD  = false;
  String  INDEXEDTRIANGLESTRIPSET_ATTR_COLORPERVERTEX_DFLT  = "true";
  String  INDEXEDTRIANGLESTRIPSET_ATTR_NORMALPERVERTEX_NAME = "normalPerVertex";
  boolean INDEXEDTRIANGLESTRIPSET_ATTR_NORMALPERVERTEX_REQD = false;
  String  INDEXEDTRIANGLESTRIPSET_ATTR_NORMALPERVERTEX_DFLT = "true";
  String  INDEXEDTRIANGLESTRIPSET_ATTR_SOLID_NAME           = "solid";
  boolean INDEXEDTRIANGLESTRIPSET_ATTR_SOLID_REQD           = false;
  String  INDEXEDTRIANGLESTRIPSET_ATTR_SOLID_DFLT           = "true";
  String  INDEXEDTRIANGLESTRIPSET_ATTR_INDEX_NAME           = "index";
  boolean INDEXEDTRIANGLESTRIPSET_ATTR_INDEX_REQD           = false;
  String  INDEXEDTRIANGLESTRIPSET_ATTR_INDEX_DFLT           = "";

   // IndexedQuadSet element
  String  INDEXEDQUADSET_ELNAME                    = "IndexedQuadSet";
  String  INDEXEDQUADSET_ATTR_CCW_NAME             = "ccw";
  boolean INDEXEDQUADSET_ATTR_CCW_REQD             = false;
  String  INDEXEDQUADSET_ATTR_CCW_DFLT             = "true";
  String  INDEXEDQUADSET_ATTR_COLORPERVERTEX_NAME  = "colorPerVertex";
  boolean INDEXEDQUADSET_ATTR_COLORPERVERTEX_REQD  = false;
  String  INDEXEDQUADSET_ATTR_COLORPERVERTEX_DFLT  = "true";
  String  INDEXEDQUADSET_ATTR_NORMALPERVERTEX_NAME = "normalPerVertex";
  boolean INDEXEDQUADSET_ATTR_NORMALPERVERTEX_REQD = false;
  String  INDEXEDQUADSET_ATTR_NORMALPERVERTEX_DFLT = "true";
  String  INDEXEDQUADSET_ATTR_SOLID_NAME           = "solid";
  boolean INDEXEDQUADSET_ATTR_SOLID_REQD           = false;
  String  INDEXEDQUADSET_ATTR_SOLID_DFLT           = "true";
  String  INDEXEDQUADSET_ATTR_INDEX_NAME           = "index";
  boolean INDEXEDQUADSET_ATTR_INDEX_REQD           = false;
  String  INDEXEDQUADSET_ATTR_INDEX_DFLT           = "";

  // Element ROUTE
  String  ROUTE_ELNAME              = "ROUTE";
  String  ROUTE_ATTR_FROMNODE_NAME  = "fromNode";
  boolean ROUTE_ATTR_FROMNODE_REQD  = true;
  String  ROUTE_ATTR_FROMNODE_DFLT  = "";
  String  ROUTE_ATTR_FROMFIELD_NAME = "fromField";
  boolean ROUTE_ATTR_FROMFIELD_REQD = true;
  String  ROUTE_ATTR_FROMFIELD_DFLT = "";
  String  ROUTE_ATTR_TONODE_NAME    = "toNode";
  boolean ROUTE_ATTR_TONODE_REQD    = true;
  String  ROUTE_ATTR_TONODE_DFLT    = "";
  String  ROUTE_ATTR_TOFIELD_NAME   = "toField";
  boolean ROUTE_ATTR_TOFIELD_REQD   = true;
  String  ROUTE_ATTR_TOFIELD_DFLT   = "";

  // Element ProtoDeclare
 String  PROTODECLARE_ELNAME            = "ProtoDeclare";
  String  PROTODECLARE_ATTR_NAME_NAME    = "name";
  boolean PROTODECLARE_ATTR_NAME_REQD    = true;
  String  PROTODECLARE_ATTR_NAME_DFLT    = "NewGroup";
  String  PROTODECLARE_ATTR_APPINFO_NAME = "appinfo";
  boolean PROTODECLARE_ATTR_APPINFO_REQD = false;
  String  PROTODECLARE_ATTR_APPINFO_DFLT = "";
  String  PROTODECLARE_ATTR_DOC_NAME     = "documentation";
  boolean PROTODECLARE_ATTR_DOC_REQD     = false;
  String  PROTODECLARE_ATTR_DOC_DFLT     = "";

  // Element ExternProtoDeclare
  String  EXTERNPROTODECLARE_ELNAME         = "ExternProtoDeclare";
  String  EXTERNPROTODECLARE_ATTR_NAME_NAME = "name";
  boolean EXTERNPROTODECLARE_ATTR_NAME_REQD = true;
  String  EXTERNPROTODECLARE_ATTR_NAME_DFLT = "NewExternalPrototype";
  String  EXTERNPROTODECLARE_ATTR_URL_NAME  = "url";
  boolean EXTERNPROTODECLARE_ATTR_URL_REQD  = true;
  String  EXTERNPROTODECLARE_ATTR_URL_DFLT  = "";
  String  EXTERNPROTODECLARE_ATTR_APPINFO_NAME = "appinfo";
  boolean EXTERNPROTODECLARE_ATTR_APPINFO_REQD = false;
  String  EXTERNPROTODECLARE_ATTR_APPINFO_DFLT = "";
  String  EXTERNPROTODECLARE_ATTR_DOC_NAME     = "documentation";
  boolean EXTERNPROTODECLARE_ATTR_DOC_REQD     = false;
  String  EXTERNPROTODECLARE_ATTR_DOC_DFLT     = "";

  // Element ProtoInterface, ProtoBody, ProtoInstance
  String  PROTOINTERFACE_ELNAME        = "ProtoInterface";
  String  PROTOBODY_ELNAME             = "ProtoBody";
  String  PROTOINSTANCE_ELNAME         = "ProtoInstance";
  String  PROTOINSTANCE_ATTR_NAME_NAME = "name";
  boolean PROTOINSTANCE_ATTR_NAME_REQD = true;
  String  PROTOINSTANCE_ATTR_NAME_DFLT = "NewProtoInstance";

  // Elements IS, connect
  String  IS_ELNAME = "IS";
  String  CONNECT_ELNAME = "connect";
  String  CONNECT_ATTR_NODEFIELD_NAME  = "nodeField";
  boolean CONNECT_ATTR_NODEFIELD_REQD  = true;
  String  CONNECT_ATTR_NODEFIELD_DFLT  = "";
  String  CONNECT_ATTR_PROTOFIELD_NAME = "protoField";
  boolean CONNECT_ATTR_PROTOFIELD_REQD = true;
  String  CONNECT_ATTR_PROTOFIELD_DFLT = "";

  // Element field
  String  FIELD_ELNAME               = "field";
  String  FIELD_HEADER_TOOLTIP       = "Add and edit field definitions";
  String  FIELD_ATTR_NAME_NAME       = "name";
  boolean FIELD_ATTR_NAME_REQD       = true;
  String  FIELD_ATTR_NAME_DFLT       = "newFieldName";
  String  FIELD_ATTR_ACCESSTYPE_NAME = "accessType";
  boolean FIELD_ATTR_ACCESSTYPE_REQD = true;
  String  FIELD_ATTR_ACCESSTYPE_DFLT = "inputOnly"; // force transition to prompt default for value field
  String  FIELD_ATTR_TYPE_NAME       = "type";
  boolean FIELD_ATTR_TYPE_REQD       = true;
  String  FIELD_ATTR_TYPE_DFLT       = "";
  String  FIELD_ATTR_VALUE_NAME      = "value";
  boolean FIELD_ATTR_VALUE_REQD      = false;
  String  FIELD_ATTR_VALUE_DFLT      = "";
  String  FIELD_ATTR_APPINFO_NAME    = "appinfo";
  boolean FIELD_ATTR_APPINFO_REQD    = false;
  String  FIELD_ATTR_APPINFO_DFLT    = "";
  String  FIELD_ATTR_DOC_NAME        = "documentation";
  boolean FIELD_ATTR_DOC_REQD        = false;
  String  FIELD_ATTR_DOC_DFLT        = "";

  // Element fieldValue
  String  FIELDVALUE_ELNAME          = "fieldValue";
  String  FIELDVALUE_HEADER_TOOLTIP  = "Override values for each field defined by original ProtoDeclare";
  String  FIELDVALUE_ATTR_NAME_NAME  = "name";
  boolean FIELDVALUE_ATTR_NAME_REQD  = true;
  String  FIELDVALUE_ATTR_NAME_DFLT  = "";
  String  FIELDVALUE_ATTR_VALUE_NAME = "value";
  boolean FIELDVALUE_ATTR_VALUE_REQD = false;
  String  FIELDVALUE_ATTR_VALUE_DFLT = "";

  String  FIELD_ATTR_TYPE_SFNODE = "SFNode";
  String  FIELD_ATTR_TYPE_MFNODE = "MFNode";

  String  FIELD_ATTR_ACCESSTYPE_INITIALIZEONLY = "initializeOnly";
  String  FIELD_ATTR_ACCESSTYPE_INPUTOUTPUT    = "inputOutput";
  String  FIELD_ATTR_ACCESSTYPE_INPUTONLY      = "inputOnly";
  String  FIELD_ATTR_ACCESSTYPE_OUTPUTONLY     = "outputOnly";

  String[] FIELD_ATTR_ACCESSTYPE_CHOICES = {
    FIELD_ATTR_ACCESSTYPE_INPUTONLY,
    FIELD_ATTR_ACCESSTYPE_OUTPUTONLY,
    FIELD_ATTR_ACCESSTYPE_INITIALIZEONLY,
    FIELD_ATTR_ACCESSTYPE_INPUTOUTPUT
  };
   String[] FIELD_ATTR_NAMES    = {"name","type","accessType","value","appinfo","documentation"};
   String[] FIELD_ATTR_TOOLTIPS = {
        "name must be unique for this node",                                      // name
        "type is SF Single or MF Multiple Field type",                            // type
        "accessType determines event input, event output, or persistent state",   // accessType
        "value initialization only for accessType initializeOnly or inputOutput; not allowed for SFNode, MFNode or IS/connect fields", // value
        "appinfo tooltip describing this field",                                  // appinfo
        "documentation url for more information"                                  // documentation
  };

  String[]FIELD_ATTR_TYPE_CHOICES = {
    "SFBool",
    "SFColor",
    "SFColorRGBA",
    "SFDouble",
    "SFFloat",
    "SFImage",
    "SFInt32",
    "SFMatrix3f",
    "SFMatrix3d",
    "SFMatrix4f",
    "SFMatrix4d",
    "SFNode",
    "SFRotation",
    "SFString",
    "SFTime",
    "SFVec2f",
    "SFVec2d",
    "SFVec3f",
    "SFVec3d",
    "SFVec4f",
    "SFVec4d",
    "MFBool",
    "MFColor",
    "MFColorRGBA",
    "MFDouble",
    "MFFloat",
    "MFImage",
    "MFInt32",
    "MFMatrix3f",
    "MFMatrix3d",
    "MFMatrix4f",
    "MFMatrix4d",
    "MFNode",
    "MFRotation",
    "MFString",
    "MFTime",
    "MFVec2f",
    "MFVec2d",
    "MFVec3f",
    "MFVec3d",
    "MFVec4f",
    "MFVec4d",
  };

  // Geospatial component

  String[] GEOSYSTEM_ATTR_CHOICES = {
    "GD WE",
    "GD WD",
    "GD WGS84",
    "UTM Z10 WE",
    "UTM Z11 S WE",
    "GC",
	"etc."
  };
  String[] GEOSYSTEM_ATTR_TOOLTIPS = {
    "GD Geodetic (Earth's surface) spatial reference frame, WE WGS84 geoid",
    "GD Geodetic (Earth's surface) spatial reference frame, WD WGS 72 geoid",
    "GD Geodetic (Earth's surface) spatial reference frame, WGS84 geoid",
    "UTM Universal Transverse Mercator, Z10 WE",
    "UTM Universal Transverse Mercator, Z11 S WE",
    "GC Geocentric spatial reference frame",
  };

  String[] GEO_EARTH_ELLIPSOIDS = {
    "AA Airy 1830",
    "AM Modified Airy",
    "AN Australian National",
    "BN Bessel 1841 (Namibia)",
    "BR Bessel 1841 (Ethiopia Indonesia...)",
    "CC Clarke 1866",
    "CD Clarke 1880",
    "EA Everest (India 1830)",
    "EB Everest (Sabah & Sarawak)",
    "EC Everest (India 1956)",
    "ED Everest (W. Malaysia 1969)",
    "EE Everest (W. Malaysia & Singapore 1948)",
    "EF Everest (Pakistan)",
    "FA Modified Fischer 1960",
    "HE Helmert 1906",
    "HO Hough 1960",
    "ID Indonesian 1974",
    "IN International 1924",
    "KA Krassovsky 1940",
    "RF Geodetic Reference System 1980 (GRS 80)",
    "SA South American 1969",
    "WD WGS 72",
    "WE WGS 84" // default
  };

  String[] GEO_EARTH_GEOIDS = {
    "WGS84 World Geodetic System (dated 1984, revised 2004)"
  };

  // https://www.web3d.org/files/specifications/19775-1/V3.3/Part01/components/geodata.html#t-keywordsandvalues
  String[] GEOMETADATA_KEYS = {
    "title", // 1
    "description", // 2
    "coordinateSystem", // 3
    "horizontalDatum", // 4
    "verticalDatum", // 5
    "ellipsoid", // 6
    "extent", // 7
    "resolution", // 8
    "originator", // 9
    "copyright", // 10
    "date", // 11
    "metadataFormat", // 12
    "dataUrl", // 13
    "dataFormat" // 14
  };

  String[] GEOMETADATA_KEY_TOOLTIPS = {
    "A name to succinctly identify the dataset to user", // 1
    "A brief textual description or summary of the content", // 2
    "Spatial reference frame used (e.g. GD, UTM, or LCC)", // 3
    "Name of geodetic datum, for example W84", // 4
    "Name of the vertical datum (geoid), for example W84", // 5
    "Name of the geodetic ellipsoid, for example WE", // 6
    "Bounding coordinates for dataset given in coordinateSystem spatial reference frame, in order eastmost, southmost, westmost, northmost (example for GD: -180.0 -90.0 180.0 90.0)", // 7
    "Resolution, or ground sample distance, given in length base units", // 8
    "Originator of the data (for example the author, agency, organization, publisher)", // 9
    "Appropriate copyright declaration that pertains to the data", // 10
    "Valid date or time period to which the data pertains", // 11
    "Format of external metadata description specified by url (for example FGDC, ISO TC211, CEN TC287, or OGC)", // 12
    "source data used to create the X3D to which this metadata pertains", // 13
    "format of source data in dataUrl used to create the X3D (fA free-text string that describes the format of the source data used to create the X3D node(s) to which this metadata pertains. This refers to the source data specified by the dataUrl keyword (if present). For example, USGS 5.5-min DEM)" // 14
  };

  String  GEOCOORDINATE_ELNAME              = "GeoCoordinate";
  String  GEOCOORDINATE_ATTR_GEOSYSTEM_NAME = "geoSystem";
  boolean GEOCOORDINATE_ATTR_GEOSYSTEM_REQD = false;
  String[]GEOCOORDINATE_ATTR_GEOSYSTEM_DFLT = {"GD","WE"};
  String  GEOCOORDINATE_ATTR_POINT_NAME     = "point";
  boolean GEOCOORDINATE_ATTR_POINT_REQD     = false;
  String  GEOCOORDINATE_ATTR_POINT_DFLT     = "";

  String  GEOELEVATIONGRID_ELNAME              = "GeoElevationGrid";
  String  GEOELEVATIONGRID_ATTR_GEOSYSTEM_NAME = "geoSystem";
  boolean GEOELEVATIONGRID_ATTR_GEOSYSTEM_REQD = false;
  String[]GEOELEVATIONGRID_ATTR_GEOSYSTEM_DFLT = GEOCOORDINATE_ATTR_GEOSYSTEM_DFLT;
  String  GEOELEVATIONGRID_ATTR_GEOGRIDORIGIN_NAME = "geoGridOrigin";
  boolean GEOELEVATIONGRID_ATTR_GEOGRIDORIGIN_REQD = false;
  String  GEOELEVATIONGRID_ATTR_GEOGRIDORIGIN_DFLT = "0 0 0";
  String  GEOELEVATIONGRID_ATTR_XDIMENSION_NAME = "xDimension";
  boolean GEOELEVATIONGRID_ATTR_XDIMENSION_REQD = false;
  String  GEOELEVATIONGRID_ATTR_XDIMENSION_DFLT = "2";
  String  GEOELEVATIONGRID_ATTR_ZDIMENSION_NAME = "zDimension";
  boolean GEOELEVATIONGRID_ATTR_ZDIMENSION_REQD = false;
  String  GEOELEVATIONGRID_ATTR_ZDIMENSION_DFLT = "2";
  String  GEOELEVATIONGRID_ATTR_XSPACING_NAME = "xSpacing";
  boolean GEOELEVATIONGRID_ATTR_XSPACING_REQD = false;
  String  GEOELEVATIONGRID_ATTR_XSPACING_DFLT = "1.0";
  String  GEOELEVATIONGRID_ATTR_ZSPACING_NAME = "zSpacing";
  boolean GEOELEVATIONGRID_ATTR_ZSPACING_REQD = false;
  String  GEOELEVATIONGRID_ATTR_ZSPACING_DFLT = "1.0";
  String  GEOELEVATIONGRID_ATTR_YSCALE_NAME = "yScale";
  boolean GEOELEVATIONGRID_ATTR_YSCALE_REQD = false;
  String  GEOELEVATIONGRID_ATTR_YSCALE_DFLT = "1.0";
  String  GEOELEVATIONGRID_ATTR_HEIGHT_NAME = "height";
  boolean GEOELEVATIONGRID_ATTR_HEIGHT_REQD = false;
  String  GEOELEVATIONGRID_ATTR_HEIGHT_DFLT = "0 0 0 0";
  String  GEOELEVATIONGRID_ATTR_CCW_NAME             = "ccw";
  boolean GEOELEVATIONGRID_ATTR_CCW_REQD             = false;
  String  GEOELEVATIONGRID_ATTR_CCW_DFLT             = "true";
  String  GEOELEVATIONGRID_ATTR_SOLID_NAME           = "solid";
  boolean GEOELEVATIONGRID_ATTR_SOLID_REQD           = false;
  String  GEOELEVATIONGRID_ATTR_SOLID_DFLT           = "true";
  String  GEOELEVATIONGRID_ATTR_COLORPERVERTEX_NAME  = "colorPerVertex";
  boolean GEOELEVATIONGRID_ATTR_COLORPERVERTEX_REQD  = false;
  String  GEOELEVATIONGRID_ATTR_COLORPERVERTEX_DFLT  = "true";
  String  GEOELEVATIONGRID_ATTR_NORMALPERVERTEX_NAME = "normalPerVertex";
  boolean GEOELEVATIONGRID_ATTR_NORMALPERVERTEX_REQD = false;
  String  GEOELEVATIONGRID_ATTR_NORMALPERVERTEX_DFLT = "true";
  String  GEOELEVATIONGRID_ATTR_CREASEANGLE_NAME     = "creaseAngle";
  boolean GEOELEVATIONGRID_ATTR_CREASEANGLE_REQD     = false;
  String  GEOELEVATIONGRID_ATTR_CREASEANGLE_DFLT     = "0";

  String  GEOLOCATION_ELNAME              = "GeoLocation";
  String  GEOLOCATION_ATTR_GEOSYSTEM_NAME = "geoSystem";
  boolean GEOLOCATION_ATTR_GEOSYSTEM_REQD = false;
  String[]GEOLOCATION_ATTR_GEOSYSTEM_DFLT = GEOCOORDINATE_ATTR_GEOSYSTEM_DFLT;
  String  GEOLOCATION_ATTR_GEOCOORDS_NAME = "geoCoords";
  boolean GEOLOCATION_ATTR_GEOCOORDS_REQD = false;
  String  GEOLOCATION_ATTR_GEOCOORDS_DFLT = "0 0 0";
  String  GEOLOCATION_ATTR_BBOXCENTER_NAME = "bboxCenter";
  boolean GEOLOCATION_ATTR_BBOXCENTER_REQD = false;
  String  GEOLOCATION_ATTR_BBOXCENTER_DFLT = "0 0 0";
  String  GEOLOCATION_ATTR_BBOXSIZE_NAME = "bboxSize";
  boolean GEOLOCATION_ATTR_BBOXSIZE_REQD = false;
  String  GEOLOCATION_ATTR_BBOXSIZE_DFLT = "-1 -1 -1";

  String  GEOLOD_ELNAME              = "GeoLOD";
  String  GEOLOD_ATTR_GEOSYSTEM_NAME = "geoSystem";
  boolean GEOLOD_ATTR_GEOSYSTEM_REQD = false;
  String[]GEOLOD_ATTR_GEOSYSTEM_DFLT = GEOCOORDINATE_ATTR_GEOSYSTEM_DFLT;
  String  GEOLOD_ATTR_ROOTURL_NAME = "rootUrl";
  boolean GEOLOD_ATTR_ROOTURL_REQD = false;
  String  GEOLOD_ATTR_ROOTURL_DFLT = "";
  String  GEOLOD_ATTR_CHILD1URL_NAME = "child1Url";
  boolean GEOLOD_ATTR_CHILD1URL_REQD = false;
  String  GEOLOD_ATTR_CHILD1URL_DFLT = "";
  String  GEOLOD_ATTR_CHILD2URL_NAME = "child2Url";
  boolean GEOLOD_ATTR_CHILD2URL_REQD = false;
  String  GEOLOD_ATTR_CHILD2URL_DFLT = "";
  String  GEOLOD_ATTR_CHILD3URL_NAME = "child3Url";
  boolean GEOLOD_ATTR_CHILD3URL_REQD = false;
  String  GEOLOD_ATTR_CHILD3URL_DFLT = "";
  String  GEOLOD_ATTR_CHILD4URL_NAME = "child4Url";
  boolean GEOLOD_ATTR_CHILD4URL_REQD = false;
  String  GEOLOD_ATTR_CHILD4URL_DFLT = "";
  String  GEOLOD_ATTR_RANGE_NAME = "range";
  boolean GEOLOD_ATTR_RANGE_REQD = false;
  String  GEOLOD_ATTR_RANGE_DFLT = "10";
  String  GEOLOD_ATTR_CENTER_NAME = "center";
  boolean GEOLOD_ATTR_CENTER_REQD = false;
  String  GEOLOD_ATTR_CENTER_DFLT = "0 0 0";
  String  GEOLOD_ATTR_BBOXCENTER_NAME = "bboxCenter";
  boolean GEOLOD_ATTR_BBOXCENTER_REQD = false;
  String  GEOLOD_ATTR_BBOXCENTER_DFLT = "0 0 0";
  String  GEOLOD_ATTR_BBOXSIZE_NAME = "bboxSize";
  boolean GEOLOD_ATTR_BBOXSIZE_REQD = false;
  String  GEOLOD_ATTR_BBOXSIZE_DFLT = "-1 -1 -1";

  String  GEOMETADATA_ELNAME         = "GeoMetadata";
  String  GEOMETADATA_ATTR_DATA_NAME = "data";
  boolean GEOMETADATA_ATTR_DATA_REQD = false;
  String  GEOMETADATA_ATTR_DATA_DFLT = "";
  String  GEOMETADATA_ATTR_SUMMARY_NAME = "summary";
  boolean GEOMETADATA_ATTR_SUMMARY_REQD = false;
  String  GEOMETADATA_ATTR_SUMMARY_DFLT = "";
  String  GEOMETADATA_ATTR_URL_NAME = "url";
  boolean GEOMETADATA_ATTR_URL_REQD = false;
  String  GEOMETADATA_ATTR_URL_DFLT = "";

  String  GEOORIGIN_ELNAME              = "GeoOrigin";
  String  GEOORIGIN_ATTR_GEOSYSTEM_NAME = "geoSystem";
  boolean GEOORIGIN_ATTR_GEOSYSTEM_REQD = false;
  String[]GEOORIGIN_ATTR_GEOSYSTEM_DFLT = GEOCOORDINATE_ATTR_GEOSYSTEM_DFLT;
  String  GEOORIGIN_ATTR_GEOCOORDS_NAME = "geoCoords";
  boolean GEOORIGIN_ATTR_GEOCOORDS_REQD = false;
  String  GEOORIGIN_ATTR_GEOCOORDS_DFLT = "0 0 0";
  String  GEOORIGIN_ATTR_ROTATEYUP_NAME = "rotateYUp";
  boolean GEOORIGIN_ATTR_ROTATEYUP_REQD = false;
  String  GEOORIGIN_ATTR_ROTATEYUP_DFLT = "false";

  String  GEOPOSITIONINTERPOLATOR_ELNAME              = "GeoPositionInterpolator";
  String  GEOPOSITIONINTERPOLATOR_ATTR_GEOSYSTEM_NAME = "geoSystem";
  boolean GEOPOSITIONINTERPOLATOR_ATTR_GEOSYSTEM_REQD = false;
  String[]GEOPOSITIONINTERPOLATOR_ATTR_GEOSYSTEM_DFLT = GEOCOORDINATE_ATTR_GEOSYSTEM_DFLT;
  String  GEOPOSITIONINTERPOLATOR_ATTR_KEY_NAME = "key";
  boolean GEOPOSITIONINTERPOLATOR_ATTR_KEY_REQD = false;
  String  GEOPOSITIONINTERPOLATOR_ATTR_KEY_DFLT = "";
  String  GEOPOSITIONINTERPOLATOR_ATTR_KEYVALUE_NAME = "keyValue";
  boolean GEOPOSITIONINTERPOLATOR_ATTR_KEYVALUE_REQD = false;
  String  GEOPOSITIONINTERPOLATOR_ATTR_KEYVALUE_DFLT = "";

  String  GEOPROXIMITYSENSOR_ELNAME              = "GeoProximitySensor";
  String  GEOPROXIMITYSENSOR_ATTR_GEOSYSTEM_NAME = "geoSystem";
  boolean GEOPROXIMITYSENSOR_ATTR_GEOSYSTEM_REQD = false;
  String[]GEOPROXIMITYSENSOR_ATTR_GEOSYSTEM_DFLT = GEOCOORDINATE_ATTR_GEOSYSTEM_DFLT;
  String  GEOPROXIMITYSENSOR_ATTR_GEOCENTER_NAME = "geoCenter";
  boolean GEOPROXIMITYSENSOR_ATTR_GEOCENTER_REQD = false;
  String  GEOPROXIMITYSENSOR_ATTR_GEOCENTER_DFLT = "0 0 0";
  String  GEOPROXIMITYSENSOR_ATTR_SIZE_NAME = "size";
  boolean GEOPROXIMITYSENSOR_ATTR_SIZE_REQD = false;
  String  GEOPROXIMITYSENSOR_ATTR_SIZE_DFLT = "0 0 0";
  String  GEOPROXIMITYSENSOR_ATTR_ENABLED_NAME = "enabled";
  boolean GEOPROXIMITYSENSOR_ATTR_ENABLED_REQD = false;
  String  GEOPROXIMITYSENSOR_ATTR_ENABLED_DFLT = "true";

  String  GEOTOUCHSENSOR_ELNAME              = "GeoTouchSensor";
  String  GEOTOUCHSENSOR_ATTR_GEOSYSTEM_NAME = "geoSystem";
  boolean GEOTOUCHSENSOR_ATTR_GEOSYSTEM_REQD = false;
  String[]GEOTOUCHSENSOR_ATTR_GEOSYSTEM_DFLT = GEOCOORDINATE_ATTR_GEOSYSTEM_DFLT;
  String  GEOTOUCHSENSOR_ATTR_DESCRIPTION_NAME = "description";
  boolean GEOTOUCHSENSOR_ATTR_DESCRIPTION_REQD = false;
  String  GEOTOUCHSENSOR_ATTR_DESCRIPTION_DFLT = "";
  String  GEOTOUCHSENSOR_ATTR_ENABLED_NAME = "enabled";
  boolean GEOTOUCHSENSOR_ATTR_ENABLED_REQD = false;
  String  GEOTOUCHSENSOR_ATTR_ENABLED_DFLT = "true";

  String  GEOTRANSFORM_ELNAME              = "GeoTransform";
  String  GEOTRANSFORM_ATTR_GEOSYSTEM_NAME = "geoSystem";
  boolean GEOTRANSFORM_ATTR_GEOSYSTEM_REQD = false;
  String[]GEOTRANSFORM_ATTR_GEOSYSTEM_DFLT = GEOCOORDINATE_ATTR_GEOSYSTEM_DFLT;
  String  GEOTRANSFORM_ATTR_GEOCENTER_NAME = "geoCenter";
  boolean GEOTRANSFORM_ATTR_GEOCENTER_REQD = false;
  String  GEOTRANSFORM_ATTR_GEOCENTER_DFLT = "0 0 0";
  String  GEOTRANSFORM_ATTR_TRANSLATION_NAME = "translation";
  boolean GEOTRANSFORM_ATTR_TRANSLATION_REQD = false;
  String  GEOTRANSFORM_ATTR_TRANSLATION_DFLT = "0 0 0";
  String  GEOTRANSFORM_ATTR_ROTATION_NAME = "rotation";
  boolean GEOTRANSFORM_ATTR_ROTATION_REQD = false;
  String  GEOTRANSFORM_ATTR_ROTATION_DFLT = "0 0 1 0";
  String  GEOTRANSFORM_ATTR_SCALE_NAME = "scale";
  boolean GEOTRANSFORM_ATTR_SCALE_REQD = false;
  String  GEOTRANSFORM_ATTR_SCALE_DFLT = "1 1 1";
  String  GEOTRANSFORM_ATTR_SCALEORIENTATION_NAME = "scaleOrientation";
  boolean GEOTRANSFORM_ATTR_SCALEORIENTATION_REQD = false;
  String  GEOTRANSFORM_ATTR_SCALEORIENTATION_DFLT = "0 0 1 0";
  String  GEOTRANSFORM_ATTR_BBOXCENTER_NAME = "bboxCenter";
  boolean GEOTRANSFORM_ATTR_BBOXCENTER_REQD = false;
  String  GEOTRANSFORM_ATTR_BBOXCENTER_DFLT = "0 0 0";
  String  GEOTRANSFORM_ATTR_BBOXSIZE_NAME = "bboxSize";
  boolean GEOTRANSFORM_ATTR_BBOXSIZE_REQD = false;
  String  GEOTRANSFORM_ATTR_BBOXSIZE_DFLT = "-1 -1 -1";

  String  GEOVIEWPOINT_ELNAME                = "GeoViewpoint";
  String  GEOVIEWPOINT_ATTR_GEOSYSTEM_NAME   = "geoSystem";
  boolean GEOVIEWPOINT_ATTR_GEOSYSTEM_REQD   = false;
  String[]GEOVIEWPOINT_ATTR_GEOSYSTEM_DFLT   = GEOCOORDINATE_ATTR_GEOSYSTEM_DFLT;
  String  GEOVIEWPOINT_ATTR_DESCRIPTION_NAME = "description";
  boolean GEOVIEWPOINT_ATTR_DESCRIPTION_REQD = false;
  String  GEOVIEWPOINT_ATTR_DESCRIPTION_DFLT = "";
  String  GEOVIEWPOINT_ATTR_POSITION_NAME    = "position";
  boolean GEOVIEWPOINT_ATTR_POSITION_REQD    = false;
  String  GEOVIEWPOINT_ATTR_POSITION_DFLT    = "0 0 100000";
  String  GEOVIEWPOINT_ATTR_ORIENTATION_NAME = "orientation";
  boolean GEOVIEWPOINT_ATTR_ORIENTATION_REQD = false;
  String  GEOVIEWPOINT_ATTR_ORIENTATION_DFLT = "0 0 1 0";
  String  GEOVIEWPOINT_ATTR_NAVTYPE_NAME     = "navType";
  boolean GEOVIEWPOINT_ATTR_NAVTYPE_REQD     = false;
  String[]GEOVIEWPOINT_ATTR_NAVTYPE_CHOICES  = {"EXAMINE","FLY","WALK","LOOKAT","ANY","NONE"};
  String[]GEOVIEWPOINT_ATTR_NAVTYPE_DFLT     = {"EXAMINE","ANY"};
  String  GEOVIEWPOINT_ATTR_HEADLIGHT_NAME   = "headlight";
  boolean GEOVIEWPOINT_ATTR_HEADLIGHT_REQD   = false;
  String  GEOVIEWPOINT_ATTR_HEADLIGHT_DFLT   = "true";
  String  GEOVIEWPOINT_ATTR_FIELDOFVIEW_NAME = "fieldOfView";
  boolean GEOVIEWPOINT_ATTR_FIELDOFVIEW_REQD = false;
  String  GEOVIEWPOINT_ATTR_FIELDOFVIEW_DFLT = "0.7854";
  String  GEOVIEWPOINT_ATTR_JUMP_NAME        = "jump";
  boolean GEOVIEWPOINT_ATTR_JUMP_REQD        = false;
  String  GEOVIEWPOINT_ATTR_JUMP_DFLT        = "true";
  String  GEOVIEWPOINT_ATTR_SPEEDFACTOR_NAME = "speedFactor";
  boolean GEOVIEWPOINT_ATTR_SPEEDFACTOR_REQD = false;
  String  GEOVIEWPOINT_ATTR_SPEEDFACTOR_DFLT = "1";

  // Humanoid Animation Component
  String  HANIMHUMANOID_ELNAME            = "HAnimHumanoid";
  String  HANIMHUMANOID_ATTR_NAME_NAME    = "name";
  boolean HANIMHUMANOID_ATTR_NAME_REQD    = false;
  String  HANIMHUMANOID_ATTR_NAME_DFLT    = "";
  String  HANIMHUMANOID_ATTR_CENTER_NAME  = "center";
  boolean HANIMHUMANOID_ATTR_CENTER_REQD  = false;
  String  HANIMHUMANOID_ATTR_CENTER_DFLT  = "0 0 0";
  String  HANIMHUMANOID_ATTR_INFO_NAME    = "info";
  boolean HANIMHUMANOID_ATTR_INFO_REQD    = false;
  String  HANIMHUMANOID_ATTR_INFO_DFLT    = "";
  String  HANIMHUMANOID_ATTR_ROTATION_NAME= "rotation";
  boolean HANIMHUMANOID_ATTR_ROTATION_REQD= false;
  String  HANIMHUMANOID_ATTR_ROTATION_DFLT= "0 0 1 0";
  String  HANIMHUMANOID_ATTR_SCALE_NAME   = "scale";
  boolean HANIMHUMANOID_ATTR_SCALE_REQD   = false;
  String  HANIMHUMANOID_ATTR_SCALE_DFLT   = "1 1 1";
  String  HANIMHUMANOID_ATTR_SCALEORIENTATION_NAME   = "scaleOrientation";
  boolean HANIMHUMANOID_ATTR_SCALEORIENTATION_REQD   = false;
  String  HANIMHUMANOID_ATTR_SCALEORIENTATION_DFLT   = "0 0 1 0";
  String  HANIMHUMANOID_ATTR_TRANSLATION_NAME   = "translation";
  boolean HANIMHUMANOID_ATTR_TRANSLATION_REQD   = false;
  String  HANIMHUMANOID_ATTR_TRANSLATION_DFLT   = "0 0 0";
  String  HANIMHUMANOID_ATTR_VERSION_NAME       = "version";
  boolean HANIMHUMANOID_ATTR_VERSION_REQD       = false;
  String  HANIMHUMANOID_ATTR_VERSION_DFLT       = "2.0";
  String  HANIMHUMANOID_ATTR_BBOXCENTER_NAME    = "bboxCenter";
  boolean HANIMHUMANOID_ATTR_BBOXCENTER_REQD    = false;
  String  HANIMHUMANOID_ATTR_BBOXCENTER_DFLT    = "0 0 0";
  String  HANIMHUMANOID_ATTR_BBOXSIZE_NAME      = "bboxSize";
  boolean HANIMHUMANOID_ATTR_BBOXSIZE_REQD      = false;
  String  HANIMHUMANOID_ATTR_BBOXSIZE_DFLT      = "-1 -1 -1";
  String[]HANIMHUMANOID_ATTR_VERSION_CHOICES = {
    "1.0","1.1","2.0","2.1","2.2"};
    String[]HANIMHUMANOID_VERSION_TOOLTIPS   =
  {
      "Initial H-Anim version, validation not yet fully supported",
      "Developmental H-Anim version, validation not yet fully supported",
      "Approved standardized ISO version",
      "Experimental H-Anim version, validation not well defined",
      "Draft ISO 19774 version 2018, suggested practice",
  };
  String[][]HANIMHUMANOID_ATTR_INFO_PSEUDO_DEFAULTS =
  {
    {"authorName",""},
    {"authorEmail",""},
    {"copyright",""},
    {"creationDate",""},
    {"usageRestrictions",""},
    {"humanoidVersion","2.0"},
    {"age",""},
    {"gender",""},
    {"height",""},
    {"weight",""},
  };

  String  HANIMDISPLACER_ELNAME                 = "HAnimDisplacer";
  String  HANIMDISPLACER_ATTR_NAME_NAME         = "name";
  boolean HANIMDISPLACER_ATTR_NAME_REQD         = false;
  String  HANIMDISPLACER_ATTR_NAME_DFLT         = "";
  String  HANIMDISPLACER_ATTR_COORDINDEX_NAME   = "coordIndex";
  boolean HANIMDISPLACER_ATTR_COORDINDEX_REQD   = false;
  String  HANIMDISPLACER_ATTR_COORDINDEX_DFLT   = "";
  String  HANIMDISPLACER_ATTR_DISPLACEMENTS_NAME= "displacements";
  boolean HANIMDISPLACER_ATTR_DISPLACEMENTS_REQD= false;
  String  HANIMDISPLACER_ATTR_DISPLACEMENTS_DFLT= "";
  String  HANIMDISPLACER_ATTR_WEIGHT_NAME       = "weight";
  boolean HANIMDISPLACER_ATTR_WEIGHT_REQD       = false;
  String  HANIMDISPLACER_ATTR_WEIGHT_DFLT       = "0.0";

  String[]HANIMDISPLACER_CONTAINERFIELD_CHOICES    = {"displacers"};
  String[]HANIMDISPLACER_CONTAINERFIELD_TOOLTIPS   =
  {
      "'displacers' when a child of HAnimJoint or HAnimSegment"
  };

  String[]HANIMDISPLACER_NAME_FEATUREPOINT_CHOICES =
  {
    " 1  sellion                 Sellion                             ",
    " 2  r_infraorbitale         Right Infraorbitale                 ",
    " 3  l_infraorbitale         Left Infraorbitale                  ",
    " 4  supramenton             Supramenton                         ",
    " 5  r_tragion               Right Tragion                       ",
    " 6  r_gonion                Right Gonion                        ",
    " 7  l_tragion               Left Tragion                        ",
    " 8  l_gonion                Left Gonion                         ",
    " 9  nuchale                 Nuchale                             ",
    "10  r_clavicale             Right Clavicale                     ",
    "11  suprasternale           Suprasternale                       ",
    "12  l_clavicale             Left Clavicale                      ",
    "13  r_thelion/bustpoint     Right Thelion/Bustpoint             ",
    "14  l_thelion/bustpoint     Left Thelion/Bustpoint              ",
    "15  substernale             Substernale                         ",
    "16  r_rib10                 Right 10th Rib                      ",
    "17  r_asis                  Right Anterior Superior Iliac Spine ",
    "18  l_rib10                 Left 10th Rib                       ",
    "19  l_asis                  Left Anterior Superior Iliac Spine  ",
    "20  r_iliocristale          Right Iliocristale                  ",
    "21  r_trochanterion         Right Trochanterion                 ",
    "22  l_iliocristale          Left Iliocristale                   ",
    "23  l_trochanterion         Left Trochanterion                  ",
    "24  cervicale               Cervicale                           ",
    "25  rib10_midspine          10th Rib Midspine                   ",
    "26  r_psis                  Right Posterior Superior Iliac Spine",
    "27  l_psis                  Left Posterior Superior Iliac Spine ",
    "28  waist_preferred_post    Waist, Preferred, Posterior         ",
    "29  r_acromion              Right Acromion                      ",
    "30  r_axilla_ant            Right Axilla, Anterior              ",
    "31  r_radial_styloid        Right Radial Styloid                ",
    "32  r_axilla_post           Right Axilla, Posterior             ",
    "33  r_olecranon             Right Olecranon                     ",
    "34  r_humeral_lateral_epicn Right Humeral Lateral Epicondyle    ",
    "35  r_humeral_medial_epicn  Right Humeral Medial Epicondyle     ",
    "36  r_radiale               Right Radiale                       ",
    "37  r_metacarpal_pha2       Right Metacarpal Phalange II        ",
    "38  r_dactylion             Right Dactylion                     ",
    "39  r_ulnar_styloid         Right Ulnar Styloid                 ",
    "40  r_metacarpal_pha5       Right Metacarpal-Phalange V         ",
    "41  l_acromion              Left Acromion                       ",
    "42  l_axilla_ant            Left Axilla, Ant                    ",
    "43  l_radial_styloid        Left Radial Styloid                 ",
    "44  l_axilla_post.          Left Axilla, Posterior              ",
    "45  l_olecranon             Left Olecranon                      ",
    "46  l_humeral_lateral_epicn Left Humeral Lateral Epicondyle     ",
    "47  l_humeral_medial_epicn  Left Humeral Medial Epicondyle      ",
    "48  l_radiale               Left Radiale                        ",
    "49  l_metacarpal_pha2       Left Metacarpal-Phalange II         ",
    "50  l_dactylion             Left Dactylion                      ",
    "51  l_ulnar_styloid         Left Ulnar Styloid                  ",
    "52  l_metacarpal_pha5       Left Metacarpal-Phalange V          ",
    "53  r_knee_crease           Right Knee Crease                   ",
    "54  r_femoral_lateral_epicn Right Femoral Lateral Epicondyle    ",
    "55  r_femoral_medial_epicn  Right Femoral Medial Epicondyle     ",
    "56  r_metatarsal_pha5       Right Metatarsal-Phalange V         ",
    "57  r_lateral_malleolus     Right Lateral Malleolus             ",
    "58  r_medial_malleolus      Right Medial Malleolus              ",
    "59  r_sphyrion              Right Sphyrion                      ",
    "60  r_metatarsal_pha1       Right Metatarsal-Phalange I         ",
    "61  r_calcaneous_post       Right Calcaneous, Posterior         ",
    "62  r_digit2                Right Digit II                      ",
    "63  l_knee_crease           Left Knee Crease                    ",
    "64  l_femoral_lateral_epicn Left Femoral Lateral Epicondyle     ",
    "65  l_femoral_medial_epicn  Left Femoral Medial Epicondyle      ",
    "66  l_metatarsal_pha5       Left Metatarsal-Phalange V          ",
    "67  l_lateral_malleolus     Left Lateral Malleolus              ",
    "68  l_medial_malleolus      Left Medial Malleolus               ",
    "69  l_sphyrion              Left Sphyrion                       ",
    "70  l_metatarsal_pha1       Left Metatarsal-Phalange I          ",
    "71  l_calcaneous_post       Left Calcaneous, Posterior          ",
    "72  l_digit2                Left Digit II                       ",
    "73  crotch                  Crotch                              ",
    "74  r_neck_base             Neck Base                           ",
    "75  l_neck_base             Neck Base                           ",
    "76  navel                   Navel                               ",
  };

  String  HANIMJOINT_ELNAME            = "HAnimJoint";
  String  HANIMJOINT_ATTR_NAME_NAME    = "name";
  boolean HANIMJOINT_ATTR_NAME_REQD    = false;
  String  HANIMJOINT_ATTR_NAME_DFLT    = "";
  String  HANIMJOINT_ATTR_CENTER_NAME  = "center";
  boolean HANIMJOINT_ATTR_CENTER_REQD  = false;
  String  HANIMJOINT_ATTR_CENTER_DFLT  = "0 0 0";
  String  HANIMJOINT_ATTR_LIMITORIENTATION_NAME   = "limitOrientation";
  boolean HANIMJOINT_ATTR_LIMITORIENTATION_REQD   = false;
  String  HANIMJOINT_ATTR_LIMITORIENTATION_DFLT   = "0 0 1 0";
  String  HANIMJOINT_ATTR_LLIMIT_NAME   = "llimit";
  boolean HANIMJOINT_ATTR_LLIMIT_REQD   = false;
  String  HANIMJOINT_ATTR_LLIMIT_DFLT   = "";
  String  HANIMJOINT_ATTR_ROTATION_NAME = "rotation";
  boolean HANIMJOINT_ATTR_ROTATION_REQD = false;
  String  HANIMJOINT_ATTR_ROTATION_DFLT = "0 0 1 0";
  String  HANIMJOINT_ATTR_SCALE_NAME    = "scale";
  boolean HANIMJOINT_ATTR_SCALE_REQD    = false;
  String  HANIMJOINT_ATTR_SCALE_DFLT    = "1 1 1";
  String  HANIMJOINT_ATTR_SCALEORIENTATION_NAME = "scaleOrientation";
  boolean HANIMJOINT_ATTR_SCALEORIENTATION_REQD = false;
  String  HANIMJOINT_ATTR_SCALEORIENTATION_DFLT = "0 0 1 0";
  String  HANIMJOINT_ATTR_TRANSLATION_NAME      = "translation";
  boolean HANIMJOINT_ATTR_TRANSLATION_REQD      = false;
  String  HANIMJOINT_ATTR_TRANSLATION_DFLT      = "0 0 0";
  String  HANIMJOINT_ATTR_SKINCOORDINDEX_NAME   = "skinCoordIndex";
  boolean HANIMJOINT_ATTR_SKINCOORDINDEX_REQD   = false;
  String  HANIMJOINT_ATTR_SKINCOORDINDEX_DFLT   = "";
  String  HANIMJOINT_ATTR_SKINCOORDWEIGHT_NAME  = "skinCoordWeight";
  boolean HANIMJOINT_ATTR_SKINCOORDWEIGHT_REQD  = false;
  String  HANIMJOINT_ATTR_SKINCOORDWEIGHT_DFLT  = "";
  String  HANIMJOINT_ATTR_STIFFNESS_NAME   = "stiffness";
  boolean HANIMJOINT_ATTR_STIFFNESS_REQD   = false;
  String  HANIMJOINT_ATTR_STIFFNESS_DFLT   = "0 0 0";
  String  HANIMJOINT_ATTR_ULIMIT_NAME      = "ulimit";
  boolean HANIMJOINT_ATTR_ULIMIT_REQD      = false;
  String  HANIMJOINT_ATTR_ULIMIT_DFLT      = "";
  String  HANIMJOINT_ATTR_BBOXCENTER_NAME  = "bboxCenter";
  boolean HANIMJOINT_ATTR_BBOXCENTER_REQD  = false;
  String  HANIMJOINT_ATTR_BBOXCENTER_DFLT  = "0 0 0";
  String  HANIMJOINT_ATTR_BBOXSIZE_NAME    = "bboxSize";
  boolean HANIMJOINT_ATTR_BBOXSIZE_REQD    = false;
  String  HANIMJOINT_ATTR_BBOXSIZE_DFLT    = "-1 -1 -1";

  String[]HANIMJOINT_CONTAINERFIELD_CHOICES    = {"children", "skeleton", "joints"};
  String[]HANIMJOINT_CONTAINERFIELD_TOOLTIPS   =
  {
      "'children' when part of another HAnimJoint",
      "'skeleton' when primary child of HAnimHumanoid",
      "'joints' when USE reference (following skeleton tree) in HAnimHumanoid"
  };

  String[]HANIMJOINT_NAME_CHOICES   =
  {
    "HumanoidRoot",
    "l_acromioclavicular",
    "l_ankle",
    "l_elbow",
    "l_eyeball_joint",
    "l_eyebrow_joint",
    "l_eyelid_joint",
    "l_hip",
    "l_index0",
    "l_index1",
    "l_index2",
    "l_index3",
    "l_knee",
    "l_metatarsal",
    "l_middle0",
    "l_middle1",
    "l_middle2",
    "l_middle3",
    "l_midtarsal",
    "l_pinky0",
    "l_pinky1",
    "l_pinky2",
    "l_pinky3",
    "l_ring0",
    "l_ring1",
    "l_ring2",
    "l_ring3",
    "l_shoulder",
    "l_sternoclavicular",
    "l_subtalar",
    "l_thumb1",
    "l_thumb2",
    "l_thumb3",
    "l_wrist",
    "r_acromioclavicular",
    "r_ankle",
    "r_elbow",
    "r_eyeball_joint",
    "r_eyebrow_joint",
    "r_eyelid_joint",
    "r_hip",
    "r_index0",
    "r_index1",
    "r_index2",
    "r_index3",
    "r_knee",
    "r_metatarsal",
    "r_middle0",
    "r_middle1",
    "r_middle2",
    "r_middle3",
    "r_midtarsal",
    "r_pinky0",
    "r_pinky1",
    "r_pinky2",
    "r_pinky3",
    "r_ring0",
    "r_ring1",
    "r_ring2",
    "r_ring3",
    "r_shoulder",
    "r_sternoclavicular",
    "r_subtalar",
    "r_thumb1",
    "r_thumb2",
    "r_thumb3",
    "r_wrist",
    "sacroiliac",
    "skullbase",
    "temporomandibular",
    "vc1",
    "vc2",
    "vc3",
    "vc4",
    "vc5",
    "vc6",
    "vc7",
    "vl1",
    "vl2",
    "vl3",
    "vl4",
    "vl5",
    "vt1",
    "vt10",
    "vt11",
    "vt12",
    "vt2",
    "vt3",
    "vt4",
    "vt5",
    "vt6",
    "vt7",
    "vt8",
    "vt9",
  };

  String  HANIMSEGMENT_ELNAME            = "HAnimSegment";
  String  HANIMSEGMENT_ATTR_NAME_NAME    = "name";
  boolean HANIMSEGMENT_ATTR_NAME_REQD    = false;
  String  HANIMSEGMENT_ATTR_NAME_DFLT    = "";
  String  HANIMSEGMENT_ATTR_CENTEROFMASS_NAME  = "centerOfMass";
  boolean HANIMSEGMENT_ATTR_CENTEROFMASS_REQD  = false;
  String  HANIMSEGMENT_ATTR_CENTEROFMASS_DFLT  = "0 0 0";
  String  HANIMSEGMENT_ATTR_MASS_NAME    = "mass";
  boolean HANIMSEGMENT_ATTR_MASS_REQD    = false;
  String  HANIMSEGMENT_ATTR_MASS_DFLT    = "0"; // TODO why not 0.0 in spec?
  String  HANIMSEGMENT_ATTR_MOMENTSOFINERTIA_NAME   = "momentsOfInertia";
  boolean HANIMSEGMENT_ATTR_MOMENTSOFINERTIA_REQD   = false;
  String  HANIMSEGMENT_ATTR_MOMENTSOFINERTIA_DFLT   = "0 0 0 0 0 0 0 0 0";
  String  HANIMSEGMENT_ATTR_BBOXCENTER_NAME    = "bboxCenter";
  boolean HANIMSEGMENT_ATTR_BBOXCENTER_REQD    = false;
  String  HANIMSEGMENT_ATTR_BBOXCENTER_DFLT    = "0 0 0";
  String  HANIMSEGMENT_ATTR_BBOXSIZE_NAME      = "bboxSize";
  boolean HANIMSEGMENT_ATTR_BBOXSIZE_REQD      = false;
  String  HANIMSEGMENT_ATTR_BBOXSIZE_DFLT      = "-1 -1 -1";

  String[]HANIMSEGMENT_CONTAINERFIELD_CHOICES    = {"children", "segments"};
  String[]HANIMSEGMENT_CONTAINERFIELD_TOOLTIPS   =
  {
      "'children' when part of HAnimJoint",
      "'segments' when USE reference (following skeleton tree) in HAnimHumanoid"
  };

  String[]HANIMSEGMENT_NAME_CHOICES   =
  {
    "c1",
    "c2",
    "c3",
    "c4",
    "c5",
    "c6",
    "c7",
    "jaw",
    "l1",
    "l2",
    "l3",
    "l4",
    "l5",
    "l_calf",
    "l_clavicle",
    "l_eyeball",
    "l_eyebrow",
    "l_eyelid",
    "l_forearm",
    "l_forefoot",
    "l_hand",
    "l_hindfoot",
    "l_index_distal",
    "l_index_metacarpal",
    "l_index_middle",
    "l_index_proximal",
    "l_middistal",
    "l_middle_distal",
    "l_middle_metacarpal",
    "l_middle_middle",
    "l_middle_proximal",
    "l_midproximal",
    "l_pinky_distal",
    "l_pinky_metacarpal",
    "l_pinky_middle",
    "l_pinky_proximal",
    "l_ring_distal",
    "l_ring_metacarpal",
    "l_ring_middle",
    "l_ring_proximal",
    "l_scapula",
    "l_thigh",
    "l_thumb_distal",
    "l_thumb_metacarpal",
    "l_thumb_proximal",
    "l_upperarm",
    "pelvis",
    "r_calf",
    "r_clavicle",
    "r_eyeball",
    "r_eyebrow",
    "r_eyelid",
    "r_forearm",
    "r_forefoot",
    "r_hand",
    "r_hindfoot",
    "r_index_distal",
    "r_index_metacarpal",
    "r_index_middle",
    "r_index_proximal",
    "r_middistal",
    "r_middle_distal",
    "r_middle_metacarpal",
    "r_middle_middle",
    "r_middle_proximal",
    "r_midproximal",
    "r_pinky_distal",
    "r_pinky_metacarpal",
    "r_pinky_middle",
    "r_pinky_proximal",
    "r_ring_distal",
    "r_ring_metacarpal",
    "r_ring_middle",
    "r_ring_proximal",
    "r_scapula",
    "r_thigh",
    "r_thumb_distal",
    "r_thumb_metacarpal",
    "r_thumb_proximal",
    "r_upperarm",
    "sacrum",
    "skull",
    "t1",
    "t10",
    "t11",
    "t12",
    "t2",
    "t3",
    "t4",
    "t5",
    "t6",
    "t7",
    "t8",
    "t9",
  };

  String  HANIMSITE_ELNAME            = "HAnimSite";
  String  HANIMSITE_ATTR_NAME_NAME    = "name";
  boolean HANIMSITE_ATTR_NAME_REQD    = false;
  String  HANIMSITE_ATTR_NAME_DFLT    = "";
  String  HANIMSITE_ATTR_CENTER_NAME  = "center";
  boolean HANIMSITE_ATTR_CENTER_REQD  = false;
  String  HANIMSITE_ATTR_CENTER_DFLT  = "0 0 0";
  String  HANIMSITE_ATTR_ROTATION_NAME= "rotation";
  boolean HANIMSITE_ATTR_ROTATION_REQD= false;
  String  HANIMSITE_ATTR_ROTATION_DFLT= "0 0 1 0";
  String  HANIMSITE_ATTR_SCALE_NAME   = "scale";
  boolean HANIMSITE_ATTR_SCALE_REQD   = false;
  String  HANIMSITE_ATTR_SCALE_DFLT   = "1 1 1";
  String  HANIMSITE_ATTR_SCALEORIENTATION_NAME   = "scaleOrientation";
  boolean HANIMSITE_ATTR_SCALEORIENTATION_REQD   = false;
  String  HANIMSITE_ATTR_SCALEORIENTATION_DFLT   = "0 0 1 0";
  String  HANIMSITE_ATTR_TRANSLATION_NAME   = "translation";
  boolean HANIMSITE_ATTR_TRANSLATION_REQD   = false;
  String  HANIMSITE_ATTR_TRANSLATION_DFLT   = "0 0 0";
  String  HANIMSITE_ATTR_BBOXCENTER_NAME    = "bboxCenter";
  boolean HANIMSITE_ATTR_BBOXCENTER_REQD    = false;
  String  HANIMSITE_ATTR_BBOXCENTER_DFLT    = "0 0 0";
  String  HANIMSITE_ATTR_BBOXSIZE_NAME      = "bboxSize";
  boolean HANIMSITE_ATTR_BBOXSIZE_REQD      = false;
  String  HANIMSITE_ATTR_BBOXSIZE_DFLT      = "-1 -1 -1";

  String[]HANIMSITE_CONTAINERFIELD_CHOICES    = {"children", "skeleton", "sites", "viewpoints"};
  String[]HANIMSITE_CONTAINERFIELD_TOOLTIPS   =
  {
      "'children' when part of HAnimJoint",
      "'skeleton' when primary child of HAnimHumanoid",
      "'sites' when USE reference (following skeleton tree) in HAnimHumanoid",
      "'viewpoints' when USE reference (following skeleton tree) in HAnimHumanoid",
  };

  String[]HANIMSITE_NAME_CHOICES   =
  {
"cervicale",
"crotch",
"l_acromion",
"l_asis",
"l_axilla_ant",
"l_axilla_post",
"l_calcaneous_post",
"l_clavicale",
"l_dactylion",
"l_digit2",
"l_femoral_lateral_epicn",
"l_femoral_medial_epicn",
"l_forefoot_tip",
"l_gonion",
"l_hand_tip",
"l_humeral_lateral_epicn",
"l_humeral_medial_epicn",
"l_iliocristale",
"l_index_distal_tip",
"l_infraorbitale",
"l_knee_crease",
"l_lateral_malleolus",
"l_medial_malleolus",
"l_metacarpal_pha2",
"l_metacarpal_pha5",
"l_metatarsal_pha1",
"l_metatarsal_pha5",
"l_middle_distal_tip",
"l_neck_base",
"l_olecranon",
"l_pinky_distal_tip",
"l_psis",
"l_radial_styloid",
"l_radiale",
"l_rib10",
"l_ring_distal_tip",
"l_sphyrion",
"l_thelion",
"l_thumb_distal_tip",
"l_tragion",
"l_trochanterion",
"l_ulnar_styloid",
"navel",
"nuchale",
"r_acromion",
"r_asis",
"r_axilla_ant",
"r_axilla_post",
"r_calcaneous_post",
"r_clavicale",
"r_dactylion",
"r_digit2",
"r_femoral_lateral_epicn",
"r_femoral_medial_epicn",
"r_forefoot_tip",
"r_gonion",
"r_hand_tip",
"r_humeral_lateral_epicn",
"r_humeral_medial_epicn",
"r_iliocristale",
"r_index_distal_tip",
"r_infraorbitale",
"r_knee_crease",
"r_lateral_malleolus",
"r_medial_malleolus",
"r_metacarpal_pha2",
"r_metacarpal_pha5",
"r_metatarsal_pha1",
"r_metatarsal_pha5",
"r_middle_distal_tip",
"r_neck_base",
"r_olecranon",
"r_pinky_distal_tip",
"r_psis",
"r_radial_styloid",
"r_radiale",
"r_rib10",
"r_ring_distal_tip",
"r_sphyrion",
"r_thelion",
"r_thumb_distal_tip",
"r_tragion",
"r_trochanterion",
"r_ulnar_styloid",
"rib10_midspine",
"sellion",
"skull_tip",
"substernale",
"supramenton",
"suprasternale",
"waist_preferred_post",
  };

  // NURBS Component
  String  CONTOUR2D_ELNAME                            = "Contour2D";

  String[]CONTOUR2D_CONTAINERFIELD_CHOICES            = {"trimmingContour"};
  String  CONTOUR2D_CONTAINERFIELD_TOOLTIP            = "collected curve definitions for this node";
  String[]CONTOUR2D_CONTAINERFIELD_TOOLTIPS           =
  {
      "'trimmingContour' field of NurbsTrimmedSurface"
  };

  String  CONTOURPOLYLINE2D_ELNAME                    = "ContourPolyline2D";
  String  CONTOURPOLYLINE2D_ATTR_CONTROLPOINT_NAME    = "controlPoint";
  boolean CONTOURPOLYLINE2D_ATTR_CONTROLPOINT_REQD    = false;
  String  CONTOURPOLYLINE2D_ATTR_CONTROLPOINT_DFLT    = "";

  String[]CONTOURPOLYLINE2D_CONTAINERFIELD_CHOICES    = {"children", "crossSectionCurve", "profileCurve", "trajectoryCurve"};
  String  CONTOURPOLYLINE2D_CONTAINERFIELD_TOOLTIP    = "curve definition for this node";
  String[]CONTOURPOLYLINE2D_CONTAINERFIELD_TOOLTIPS   =
  {
      "'children' when part of Contour2D",
      "'crossSectionCurve' when part of NurbsSweptSurface",
      "'profileCurve' when part of NurbsSwungSurface",
      "'trajectoryCurve' when part of NurbsSweptSurface or NurbsSwungSurface"
  };

  String  NURBSCURVE_ELNAME                           = "NurbsCurve";
  String  NURBSCURVE_ATTR_CLOSED_NAME                 = "closed";
  boolean NURBSCURVE_ATTR_CLOSED_REQD                 = false;
  String  NURBSCURVE_ATTR_CLOSED_DFLT                 = "false";
  String  NURBSCURVE_ATTR_KNOT_NAME                   = "knot";
  boolean NURBSCURVE_ATTR_KNOT_REQD                   = false;
  String  NURBSCURVE_ATTR_KNOT_DFLT                   = "";
  String  NURBSCURVE_ATTR_ORDER_NAME                  = "order";
  boolean NURBSCURVE_ATTR_ORDER_REQD                  = false;
  String  NURBSCURVE_ATTR_ORDER_DFLT                  = "3";
  String  NURBSCURVE_ATTR_TESSELLATION_NAME           = "tessellation";
  boolean NURBSCURVE_ATTR_TESSELLATION_REQD           = false;
  String  NURBSCURVE_ATTR_TESSELLATION_DFLT           = "0";
  String  NURBSCURVE_ATTR_WEIGHT_NAME                 = "weight";
  boolean NURBSCURVE_ATTR_WEIGHT_REQD                 = false;
  String  NURBSCURVE_ATTR_WEIGHT_DFLT                 = "";
  String  NURBSCURVE_ATTR_CONTROLPOINT_DFLT           = ""; // proxy default prior to reading Coordinate node

  String[]NURBSCURVE_CONTAINERFIELD_CHOICES    = {"geometry", "trajectoryCurve"};
  String  NURBSCURVE_CONTAINERFIELD_TOOLTIP    = "curve definition for this node";
  String[]NURBSCURVE_CONTAINERFIELD_TOOLTIPS   =
  {
      "'geometry' when part of Shape",
      "'trajectoryCurve' when part of NurbsSweptSurface"
  };

  String  NURBSCURVE2D_ELNAME                         = "NurbsCurve2D";
  String  NURBSCURVE2D_ATTR_CONTROLPOINT_NAME         = "controlPoint";
  boolean NURBSCURVE2D_ATTR_CONTROLPOINT_REQD         = false;
  String  NURBSCURVE2D_ATTR_CONTROLPOINT_DFLT         = "";
  String  NURBSCURVE2D_ATTR_CLOSED_NAME               = "closed";
  boolean NURBSCURVE2D_ATTR_CLOSED_REQD               = false;
  String  NURBSCURVE2D_ATTR_CLOSED_DFLT               = "false";
  String  NURBSCURVE2D_ATTR_KNOT_NAME                 = "knot";
  boolean NURBSCURVE2D_ATTR_KNOT_REQD                 = false;
  String  NURBSCURVE2D_ATTR_KNOT_DFLT                 = "";
  String  NURBSCURVE2D_ATTR_ORDER_NAME                = "order";
  boolean NURBSCURVE2D_ATTR_ORDER_REQD                = false;
  String  NURBSCURVE2D_ATTR_ORDER_DFLT                = "3";
  String  NURBSCURVE2D_ATTR_TESSELLATION_NAME         = "tessellation";
  boolean NURBSCURVE2D_ATTR_TESSELLATION_REQD         = false;
  String  NURBSCURVE2D_ATTR_TESSELLATION_DFLT         = "0";
  String  NURBSCURVE2D_ATTR_WEIGHT_NAME               = "weight";
  boolean NURBSCURVE2D_ATTR_WEIGHT_REQD               = false;
  String  NURBSCURVE2D_ATTR_WEIGHT_DFLT               = "";

  String[]NURBSCURVE2D_CONTAINERFIELD_CHOICES    = {"children", "crossSectionCurve", "profileCurve", "trajectoryCurve"};
  String  NURBSCURVE2D_CONTAINERFIELD_TOOLTIP    = "curve definition for this node";
  String[]NURBSCURVE2D_CONTAINERFIELD_TOOLTIPS   =
  {
      "'children' when part of Contour2D",
      "'crossSectionCurve' when part of NurbsSweptSurface",
      "'profileCurve' when part of NurbsSwungSurface",
      "'trajectoryCurve' when part of NurbsSweptSurface or NurbsSwungSurface"
  };

  String  NURBSORIENTATIONINTERPOLATOR_ELNAME                         = "NurbsOrientationInterpolator";
  String  NURBSORIENTATIONINTERPOLATOR_ATTR_KNOT_NAME                 = "knot";
  boolean NURBSORIENTATIONINTERPOLATOR_ATTR_KNOT_REQD                 = false;
  String  NURBSORIENTATIONINTERPOLATOR_ATTR_KNOT_DFLT                 = "";
  String  NURBSORIENTATIONINTERPOLATOR_ATTR_ORDER_NAME                = "order";
  boolean NURBSORIENTATIONINTERPOLATOR_ATTR_ORDER_REQD                = false;
  String  NURBSORIENTATIONINTERPOLATOR_ATTR_ORDER_DFLT                = "3";
  String  NURBSORIENTATIONINTERPOLATOR_ATTR_WEIGHT_NAME               = "weight";
  boolean NURBSORIENTATIONINTERPOLATOR_ATTR_WEIGHT_REQD               = false;
  String  NURBSORIENTATIONINTERPOLATOR_ATTR_WEIGHT_DFLT               = "";

  String  NURBSPOSITIONINTERPOLATOR_ELNAME                         = "NurbsPositionInterpolator";
  String  NURBSPOSITIONINTERPOLATOR_ATTR_KNOT_NAME                 = "knot";
  boolean NURBSPOSITIONINTERPOLATOR_ATTR_KNOT_REQD                 = false;
  String  NURBSPOSITIONINTERPOLATOR_ATTR_KNOT_DFLT                 = "";
  String  NURBSPOSITIONINTERPOLATOR_ATTR_ORDER_NAME                = "order";
  boolean NURBSPOSITIONINTERPOLATOR_ATTR_ORDER_REQD                = false;
  String  NURBSPOSITIONINTERPOLATOR_ATTR_ORDER_DFLT                = "3";
  String  NURBSPOSITIONINTERPOLATOR_ATTR_WEIGHT_NAME               = "weight";
  boolean NURBSPOSITIONINTERPOLATOR_ATTR_WEIGHT_REQD               = false;
  String  NURBSPOSITIONINTERPOLATOR_ATTR_WEIGHT_DFLT               = "";

  String  NURBSSET_ELNAME                      = "NurbsSet";
  String  NURBSSET_ATTR_TESSELLATIONSCALE_NAME = "tessellationScale";
  boolean NURBSSET_ATTR_TESSELLATIONSCALE_REQD = false;
  String  NURBSSET_ATTR_TESSELLATIONSCALE_DFLT = "1.0";
  String  NURBSSET_ATTR_BBOXCENTER_NAME        = "bboxCenter";
  boolean NURBSSET_ATTR_BBOXCENTER_REQD        = false;
  String  NURBSSET_ATTR_BBOXCENTER_DFLT        = "0 0 0";
  String  NURBSSET_ATTR_BBOXSIZE_NAME          = "bboxSize";
  boolean NURBSSET_ATTR_BBOXSIZE_REQD          = false;
  String  NURBSSET_ATTR_BBOXSIZE_DFLT          = "-1 -1 -1";

  String  NURBSSWEPTSURFACE_ELNAME          = "NurbsSweptSurface";
  String  NURBSSWEPTSURFACE_ATTR_CCW_NAME   = "ccw";
  boolean NURBSSWEPTSURFACE_ATTR_CCW_REQD   = false;
  String  NURBSSWEPTSURFACE_ATTR_CCW_DFLT   = "true";
  String  NURBSSWEPTSURFACE_ATTR_SOLID_NAME = "solid";
  boolean NURBSSWEPTSURFACE_ATTR_SOLID_REQD = false;
  String  NURBSSWEPTSURFACE_ATTR_SOLID_DFLT = "true";

  String  NURBSSWUNGSURFACE_ELNAME          = "NurbsSwungSurface";
  String  NURBSSWUNGSURFACE_ATTR_CCW_NAME   = "ccw";
  boolean NURBSSWUNGSURFACE_ATTR_CCW_REQD   = false;
  String  NURBSSWUNGSURFACE_ATTR_CCW_DFLT   = "true";
  String  NURBSSWUNGSURFACE_ATTR_SOLID_NAME = "solid";
  boolean NURBSSWUNGSURFACE_ATTR_SOLID_REQD = false;
  String  NURBSSWUNGSURFACE_ATTR_SOLID_DFLT = "true";

  String  NURBSSURFACEINTERPOLATOR_ELNAME                          = "NurbsSurfaceInterpolator";
  String  NURBSSURFACEINTERPOLATOR_ATTR_UDIMENSION_NAME            = "uDimension";
  boolean NURBSSURFACEINTERPOLATOR_ATTR_UDIMENSION_REQD            = false;
  String  NURBSSURFACEINTERPOLATOR_ATTR_UDIMENSION_DFLT            = "0";
  String  NURBSSURFACEINTERPOLATOR_ATTR_VDIMENSION_NAME            = "vDimension";
  boolean NURBSSURFACEINTERPOLATOR_ATTR_VDIMENSION_REQD            = false;
  String  NURBSSURFACEINTERPOLATOR_ATTR_VDIMENSION_DFLT            = "0";
  String  NURBSSURFACEINTERPOLATOR_ATTR_UKNOT_NAME                 = "uKnot";
  boolean NURBSSURFACEINTERPOLATOR_ATTR_UKNOT_REQD                 = false;
  String  NURBSSURFACEINTERPOLATOR_ATTR_UKNOT_DFLT                 = "";
  String  NURBSSURFACEINTERPOLATOR_ATTR_VKNOT_NAME                 = "vKnot";
  boolean NURBSSURFACEINTERPOLATOR_ATTR_VKNOT_REQD                 = false;
  String  NURBSSURFACEINTERPOLATOR_ATTR_VKNOT_DFLT                 = "";
  String  NURBSSURFACEINTERPOLATOR_ATTR_UORDER_NAME                = "uOrder";
  boolean NURBSSURFACEINTERPOLATOR_ATTR_UORDER_REQD                = false;
  String  NURBSSURFACEINTERPOLATOR_ATTR_UORDER_DFLT                = "3";
  String  NURBSSURFACEINTERPOLATOR_ATTR_VORDER_NAME                = "vOrder";
  boolean NURBSSURFACEINTERPOLATOR_ATTR_VORDER_REQD                = false;
  String  NURBSSURFACEINTERPOLATOR_ATTR_VORDER_DFLT                = "3";
  String  NURBSSURFACEINTERPOLATOR_ATTR_WEIGHT_NAME                = "weight";
  boolean NURBSSURFACEINTERPOLATOR_ATTR_WEIGHT_REQD                = false;
  String  NURBSSURFACEINTERPOLATOR_ATTR_WEIGHT_DFLT                = "";

  String  NURBSTEXTURECOORDINATE_ELNAME                          = "NurbsTextureCoordinate";
  String  NURBSTEXTURECOORDINATE_ATTR_CONTROLPOINT_NAME          = "controlPoint";
  boolean NURBSTEXTURECOORDINATE_ATTR_CONTROLPOINT_REQD          = false;
  String  NURBSTEXTURECOORDINATE_ATTR_CONTROLPOINT_DFLT          = "";
  String  NURBSTEXTURECOORDINATE_ATTR_UDIMENSION_NAME            = "uDimension";
  boolean NURBSTEXTURECOORDINATE_ATTR_UDIMENSION_REQD            = false;
  String  NURBSTEXTURECOORDINATE_ATTR_UDIMENSION_DFLT            = "0";
  String  NURBSTEXTURECOORDINATE_ATTR_VDIMENSION_NAME            = "vDimension";
  boolean NURBSTEXTURECOORDINATE_ATTR_VDIMENSION_REQD            = false;
  String  NURBSTEXTURECOORDINATE_ATTR_VDIMENSION_DFLT            = "0";
  String  NURBSTEXTURECOORDINATE_ATTR_UKNOT_NAME                 = "uKnot";
  boolean NURBSTEXTURECOORDINATE_ATTR_UKNOT_REQD                 = false;
  String  NURBSTEXTURECOORDINATE_ATTR_UKNOT_DFLT                 = "";
  String  NURBSTEXTURECOORDINATE_ATTR_VKNOT_NAME                 = "vKnot";
  boolean NURBSTEXTURECOORDINATE_ATTR_VKNOT_REQD                 = false;
  String  NURBSTEXTURECOORDINATE_ATTR_VKNOT_DFLT                 = "";
  String  NURBSTEXTURECOORDINATE_ATTR_UORDER_NAME                = "uOrder";
  boolean NURBSTEXTURECOORDINATE_ATTR_UORDER_REQD                = false;
  String  NURBSTEXTURECOORDINATE_ATTR_UORDER_DFLT                = "3";
  String  NURBSTEXTURECOORDINATE_ATTR_VORDER_NAME                = "vOrder";
  boolean NURBSTEXTURECOORDINATE_ATTR_VORDER_REQD                = false;
  String  NURBSTEXTURECOORDINATE_ATTR_VORDER_DFLT                = "3";
  String  NURBSTEXTURECOORDINATE_ATTR_WEIGHT_NAME                = "weight";
  boolean NURBSTEXTURECOORDINATE_ATTR_WEIGHT_REQD                = false;
  String  NURBSTEXTURECOORDINATE_ATTR_WEIGHT_DFLT                = "";

  String  NURBSPATCHSURFACE_ELNAME                          = "NurbsPatchSurface";
  String  NURBSPATCHSURFACE_ATTR_UCLOSED_NAME               = "uClosed";
  boolean NURBSPATCHSURFACE_ATTR_UCLOSED_REQD               = false;
  String  NURBSPATCHSURFACE_ATTR_UCLOSED_DFLT               = "0";
  String  NURBSPATCHSURFACE_ATTR_VCLOSED_NAME               = "vClosed";
  boolean NURBSPATCHSURFACE_ATTR_VCLOSED_REQD               = false;
  String  NURBSPATCHSURFACE_ATTR_VCLOSED_DFLT               = "0";
  String  NURBSPATCHSURFACE_ATTR_UDIMENSION_NAME            = "uDimension";
  boolean NURBSPATCHSURFACE_ATTR_UDIMENSION_REQD            = false;
  String  NURBSPATCHSURFACE_ATTR_UDIMENSION_DFLT            = "0";
  String  NURBSPATCHSURFACE_ATTR_VDIMENSION_NAME            = "vDimension";
  boolean NURBSPATCHSURFACE_ATTR_VDIMENSION_REQD            = false;
  String  NURBSPATCHSURFACE_ATTR_VDIMENSION_DFLT            = "0";
  String  NURBSPATCHSURFACE_ATTR_UKNOT_NAME                 = "uKnot";
  boolean NURBSPATCHSURFACE_ATTR_UKNOT_REQD                 = false;
  String  NURBSPATCHSURFACE_ATTR_UKNOT_DFLT                 = "";
  String  NURBSPATCHSURFACE_ATTR_VKNOT_NAME                 = "vKnot";
  boolean NURBSPATCHSURFACE_ATTR_VKNOT_REQD                 = false;
  String  NURBSPATCHSURFACE_ATTR_VKNOT_DFLT                 = "";
  String  NURBSPATCHSURFACE_ATTR_UORDER_NAME                = "uOrder";
  boolean NURBSPATCHSURFACE_ATTR_UORDER_REQD                = false;
  String  NURBSPATCHSURFACE_ATTR_UORDER_DFLT                = "3";
  String  NURBSPATCHSURFACE_ATTR_VORDER_NAME                = "vOrder";
  boolean NURBSPATCHSURFACE_ATTR_VORDER_REQD                = false;
  String  NURBSPATCHSURFACE_ATTR_VORDER_DFLT                = "3";
  String  NURBSPATCHSURFACE_ATTR_UTESSELLATION_NAME         = "uTessellation";
  boolean NURBSPATCHSURFACE_ATTR_UTESSELLATION_REQD         = false;
  String  NURBSPATCHSURFACE_ATTR_UTESSELLATION_DFLT         = "0";
  String  NURBSPATCHSURFACE_ATTR_VTESSELLATION_NAME         = "vTessellation";
  boolean NURBSPATCHSURFACE_ATTR_VTESSELLATION_REQD         = false;
  String  NURBSPATCHSURFACE_ATTR_VTESSELLATION_DFLT         = "0";
  String  NURBSPATCHSURFACE_ATTR_SOLID_NAME                 = "solid";
  boolean NURBSPATCHSURFACE_ATTR_SOLID_REQD                 = false;
  String  NURBSPATCHSURFACE_ATTR_SOLID_DFLT                 = "true";
  String  NURBSPATCHSURFACE_ATTR_WEIGHT_NAME                = "weight";
  boolean NURBSPATCHSURFACE_ATTR_WEIGHT_REQD                = false;
  String  NURBSPATCHSURFACE_ATTR_WEIGHT_DFLT                = "";

  String  NURBSTRIMMEDSURFACE_ELNAME                          = "NurbsTrimmedSurface";
  String  NURBSTRIMMEDSURFACE_ATTR_UCLOSED_NAME               = "uClosed";
  boolean NURBSTRIMMEDSURFACE_ATTR_UCLOSED_REQD               = false;
  String  NURBSTRIMMEDSURFACE_ATTR_UCLOSED_DFLT               = "0";
  String  NURBSTRIMMEDSURFACE_ATTR_VCLOSED_NAME               = "vClosed";
  boolean NURBSTRIMMEDSURFACE_ATTR_VCLOSED_REQD               = false;
  String  NURBSTRIMMEDSURFACE_ATTR_VCLOSED_DFLT               = "0";
  String  NURBSTRIMMEDSURFACE_ATTR_UDIMENSION_NAME            = "uDimension";
  boolean NURBSTRIMMEDSURFACE_ATTR_UDIMENSION_REQD            = false;
  String  NURBSTRIMMEDSURFACE_ATTR_UDIMENSION_DFLT            = "0";
  String  NURBSTRIMMEDSURFACE_ATTR_VDIMENSION_NAME            = "vDimension";
  boolean NURBSTRIMMEDSURFACE_ATTR_VDIMENSION_REQD            = false;
  String  NURBSTRIMMEDSURFACE_ATTR_VDIMENSION_DFLT            = "0";
  String  NURBSTRIMMEDSURFACE_ATTR_UKNOT_NAME                 = "uKnot";
  boolean NURBSTRIMMEDSURFACE_ATTR_UKNOT_REQD                 = false;
  String  NURBSTRIMMEDSURFACE_ATTR_UKNOT_DFLT                 = "";
  String  NURBSTRIMMEDSURFACE_ATTR_VKNOT_NAME                 = "vKnot";
  boolean NURBSTRIMMEDSURFACE_ATTR_VKNOT_REQD                 = false;
  String  NURBSTRIMMEDSURFACE_ATTR_VKNOT_DFLT                 = "";
  String  NURBSTRIMMEDSURFACE_ATTR_UORDER_NAME                = "uOrder";
  boolean NURBSTRIMMEDSURFACE_ATTR_UORDER_REQD                = false;
  String  NURBSTRIMMEDSURFACE_ATTR_UORDER_DFLT                = "3";
  String  NURBSTRIMMEDSURFACE_ATTR_VORDER_NAME                = "vOrder";
  boolean NURBSTRIMMEDSURFACE_ATTR_VORDER_REQD                = false;
  String  NURBSTRIMMEDSURFACE_ATTR_VORDER_DFLT                = "3";
  String  NURBSTRIMMEDSURFACE_ATTR_UTESSELLATION_NAME         = "uTessellation";
  boolean NURBSTRIMMEDSURFACE_ATTR_UTESSELLATION_REQD         = false;
  String  NURBSTRIMMEDSURFACE_ATTR_UTESSELLATION_DFLT         = "0";
  String  NURBSTRIMMEDSURFACE_ATTR_VTESSELLATION_NAME         = "vTessellation";
  boolean NURBSTRIMMEDSURFACE_ATTR_VTESSELLATION_REQD         = false;
  String  NURBSTRIMMEDSURFACE_ATTR_VTESSELLATION_DFLT         = "0";
  String  NURBSTRIMMEDSURFACE_ATTR_SOLID_NAME                 = "solid";
  boolean NURBSTRIMMEDSURFACE_ATTR_SOLID_REQD                 = false;
  String  NURBSTRIMMEDSURFACE_ATTR_SOLID_DFLT                 = "true";
  String  NURBSTRIMMEDSURFACE_ATTR_WEIGHT_NAME                = "weight";
  boolean NURBSTRIMMEDSURFACE_ATTR_WEIGHT_REQD                = false;
  String  NURBSTRIMMEDSURFACE_ATTR_WEIGHT_DFLT                = "";

  // DIS Component
  String  DISENTITYMANAGER_ELNAME                  = "DISEntityManager";
  String  DISENTITYMANAGER_ATTR_ADDRESS_NAME       = "address";
  boolean DISENTITYMANAGER_ATTR_ADDRESS_REQD       = false;
  String  DISENTITYMANAGER_ATTR_ADDRESS_DFLT       = "localhost";
  String  DISENTITYMANAGER_ATTR_APPLICATIONID_NAME ="applicationID";
  boolean DISENTITYMANAGER_ATTR_APPLICATIONID_REQD = false;
  String  DISENTITYMANAGER_ATTR_APPLICATIONID_DFLT = "1";
  String  DISENTITYMANAGER_ATTR_PORT_NAME          = "port";
  boolean DISENTITYMANAGER_ATTR_PORT_REQD          = false;
  String  DISENTITYMANAGER_ATTR_PORT_DFLT          = "0";
  String  DISENTITYMANAGER_ATTR_SITEID_NAME        = "siteID";
  boolean DISENTITYMANAGER_ATTR_SITEID_REQD        = false;
  String  DISENTITYMANAGER_ATTR_SITEID_DFLT        = "0";

  String  DISENTITYTYPEMAPPING_ELNAME                = "DISEntityTypeMapping";
  String  DISENTITYTYPEMAPPING_ATTR_URL_NAME         = "url";
  boolean DISENTITYTYPEMAPPING_ATTR_URL_REQD         = false;
  String  DISENTITYTYPEMAPPING_ATTR_URL_DFLT         = "";
  String  DISENTITYTYPEMAPPING_ATTR_CATEGORY_NAME    = "category";
  boolean DISENTITYTYPEMAPPING_ATTR_CATEGORY_REQD    = false;
  String  DISENTITYTYPEMAPPING_ATTR_CATEGORY_DFLT    = "0";
  String  DISENTITYTYPEMAPPING_ATTR_COUNTRY_NAME     = "country";
  boolean DISENTITYTYPEMAPPING_ATTR_COUNTRY_REQD     = false;
  String  DISENTITYTYPEMAPPING_ATTR_COUNTRY_DFLT     = "0";
  String  DISENTITYTYPEMAPPING_ATTR_DOMAIN_NAME      = "domain";
  boolean DISENTITYTYPEMAPPING_ATTR_DOMAIN_REQD      = false;
  String  DISENTITYTYPEMAPPING_ATTR_DOMAIN_DFLT      = "0";
  String  DISENTITYTYPEMAPPING_ATTR_EXTRA_NAME       = "extra";
  boolean DISENTITYTYPEMAPPING_ATTR_EXTRA_REQD       = false;
  String  DISENTITYTYPEMAPPING_ATTR_EXTRA_DFLT       = "0";
  String  DISENTITYTYPEMAPPING_ATTR_KIND_NAME        = "kind";
  boolean DISENTITYTYPEMAPPING_ATTR_KIND_REQD        = false;
  String  DISENTITYTYPEMAPPING_ATTR_KIND_DFLT        = "0";
  String  DISENTITYTYPEMAPPING_ATTR_SPECIFIC_NAME    = "specific";
  boolean DISENTITYTYPEMAPPING_ATTR_SPECIFIC_REQD    = false;
  String  DISENTITYTYPEMAPPING_ATTR_SPECIFIC_DFLT    = "0";
  String  DISENTITYTYPEMAPPING_ATTR_SUBCATEGORY_NAME = "subcategory";
  boolean DISENTITYTYPEMAPPING_ATTR_SUBCATEGORY_REQD = false;
  String  DISENTITYTYPEMAPPING_ATTR_SUBCATEGORY_DFLT = "0";

  String  ESPDUTRANSFORM_ELNAME                               = "EspduTransform";
  String  ESPDUTRANSFORM_ATTR_ADDRESS_NAME                    = "address";
  boolean ESPDUTRANSFORM_ATTR_ADDRESS_REQD                    = false;
  String  ESPDUTRANSFORM_ATTR_ADDRESS_DFLT                    = "localhost";
  String  ESPDUTRANSFORM_ATTR_APPLICATIONID_NAME              = "applicationID";
  boolean ESPDUTRANSFORM_ATTR_APPLICATIONID_REQD              = false;
  String  ESPDUTRANSFORM_ATTR_APPLICATIONID_DFLT              = "0";
  String  ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERCOUNT_NAME = "articulationParameterCount";
  boolean ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERCOUNT_REQD = false;
  String  ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERCOUNT_DFLT = "0";
  String  ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERDESIGNATORARRAY_NAME      = "articulationParameterDesignatorArray";
  boolean ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERDESIGNATORARRAY_REQD           = false;
  String  ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERDESIGNATORARRAY_DFLT      = "";
  String  ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERCHANGEINDICATORARRAY_NAME = "articulationParameterChangeIndicatorArray";
  boolean ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERCHANGEINDICATORARRAY_REQD      = false;
  String  ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERCHANGEINDICATORARRAY_DFLT      = "";
  String  ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERIDPARTATTACHEDTORARRAY_NAME    = "articulationParameterIdPartAttachedToArray";
  boolean ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERIDPARTATTACHEDTORARRAY_REQD    = false;
  String  ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERIDPARTATTACHEDTORARRAY_DFLT    = "";
  String  ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERTYPEARRAY_NAME  = "articulationParameterTypeArray";
  boolean ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERTYPEARRAY_REQD  = false;
  String  ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERTYPEARRAY_DFLT  = "";
  String  ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERARRAY_NAME      = "articulationParameterArray";
  boolean ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERARRAY_REQD      = false;
  String  ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERARRAY_DFLT      = "";
  String  ESPDUTRANSFORM_ATTR_CENTER_NAME                     = "center";
  boolean ESPDUTRANSFORM_ATTR_CENTER_REQD                     = false;
  String  ESPDUTRANSFORM_ATTR_CENTER_DFLT                     = "0 0 0";
  String  ESPDUTRANSFORM_ATTR_COLLISIONTYPE_NAME              = "collisionType";
  boolean ESPDUTRANSFORM_ATTR_COLLISIONTYPE_REQD              = false;
  String  ESPDUTRANSFORM_ATTR_COLLISIONTYPE_DFLT              = "0";
  String  ESPDUTRANSFORM_ATTR_DEADRECKONING_NAME              = "deadReckoning";
  boolean ESPDUTRANSFORM_ATTR_DEADRECKONING_REQD              = false;
  String  ESPDUTRANSFORM_ATTR_DEADRECKONING_DFLT              = "0";
  String  ESPDUTRANSFORM_ATTR_DETONATIONLOCATION_NAME         = "detonationLocation";
  boolean ESPDUTRANSFORM_ATTR_DETONATIONLOCATION_REQD         = false;
  String  ESPDUTRANSFORM_ATTR_DETONATIONLOCATION_DFLT         = "0 0 0";
  String  ESPDUTRANSFORM_ATTR_DETONATIONRELATIVELOCATION_NAME = "detonationRelativeLocation";
  boolean ESPDUTRANSFORM_ATTR_DETONATIONRELATIVELOCATION_REQD = false;
  String  ESPDUTRANSFORM_ATTR_DETONATIONRELATIVELOCATION_DFLT = "0 0 0";
  String  ESPDUTRANSFORM_ATTR_DETONATIONRESULT_NAME           = "detonationResult";
  boolean ESPDUTRANSFORM_ATTR_DETONATIONRESULT_REQD           = false;
  String  ESPDUTRANSFORM_ATTR_DETONATIONRESULT_DFLT           = "0";
  String  ESPDUTRANSFORM_ATTR_ENABLED_NAME                    = "enabled";
  boolean ESPDUTRANSFORM_ATTR_ENABLED_REQD                    = false;
  String  ESPDUTRANSFORM_ATTR_ENABLED_DFLT                    = "true";
  String  ESPDUTRANSFORM_ATTR_ENTITYCATEGORY_NAME             = "entityCategory";
  boolean ESPDUTRANSFORM_ATTR_ENTITYCATEGORY_REQD             = false;
  String  ESPDUTRANSFORM_ATTR_ENTITYCATEGORY_DFLT             = "0";
  String  ESPDUTRANSFORM_ATTR_ENTITYCOUNTRY_NAME              = "entityCountry";
  boolean ESPDUTRANSFORM_ATTR_ENTITYCOUNTRY_REQD              = false;
  String  ESPDUTRANSFORM_ATTR_ENTITYCOUNTRY_DFLT              = "0";
  String  ESPDUTRANSFORM_ATTR_ENTITYDOMAIN_NAME               = "entityDomain";
  boolean ESPDUTRANSFORM_ATTR_ENTITYDOMAIN_REQD               = false;
  String  ESPDUTRANSFORM_ATTR_ENTITYDOMAIN_DFLT               = "0";
  String  ESPDUTRANSFORM_ATTR_ENTITYEXTRA_NAME                = "entityExtra";
  boolean ESPDUTRANSFORM_ATTR_ENTITYEXTRA_REQD                = false;
  String  ESPDUTRANSFORM_ATTR_ENTITYEXTRA_DFLT                = "0";
  String  ESPDUTRANSFORM_ATTR_ENTITYID_NAME                   = "entityID";
  boolean ESPDUTRANSFORM_ATTR_ENTITYID_REQD                   = false;
  String  ESPDUTRANSFORM_ATTR_ENTITYID_DFLT                   = "0";
  String  ESPDUTRANSFORM_ATTR_ENTITYKIND_NAME                 = "entityKind";
  boolean ESPDUTRANSFORM_ATTR_ENTITYKIND_REQD                 = false;
  String  ESPDUTRANSFORM_ATTR_ENTITYKIND_DFLT                 = "0";
  String  ESPDUTRANSFORM_ATTR_ENTITYSPECIFIC_NAME             = "entitySpecific";
  boolean ESPDUTRANSFORM_ATTR_ENTITYSPECIFIC_REQD             = false;
  String  ESPDUTRANSFORM_ATTR_ENTITYSPECIFIC_DFLT             = "0";
  String  ESPDUTRANSFORM_ATTR_ENTITYSUBCATEGORY_NAME          = "entitySubCategory";
  boolean ESPDUTRANSFORM_ATTR_ENTITYSUBCATEGORY_REQD          = false;
  String  ESPDUTRANSFORM_ATTR_ENTITYSUBCATEGORY_DFLT          = "0";
  String  ESPDUTRANSFORM_ATTR_EVENTAPPLICATIONID_NAME         = "eventApplicationID";
  boolean ESPDUTRANSFORM_ATTR_EVENTAPPLICATIONID_REQD         = false;
  String  ESPDUTRANSFORM_ATTR_EVENTAPPLICATIONID_DFLT         = "1";
  String  ESPDUTRANSFORM_ATTR_EVENTENTITYID_NAME              = "eventEntityID";
  boolean ESPDUTRANSFORM_ATTR_EVENTENTITYID_REQD              = false;
  String  ESPDUTRANSFORM_ATTR_EVENTENTITYID_DFLT              = "0";
  String  ESPDUTRANSFORM_ATTR_EVENTNUMBER_NAME                = "eventNumber";
  boolean ESPDUTRANSFORM_ATTR_EVENTNUMBER_REQD                = false;
  String  ESPDUTRANSFORM_ATTR_EVENTNUMBER_DFLT                = "0";
  String  ESPDUTRANSFORM_ATTR_EVENTSITEID_NAME                = "eventSiteID";
  boolean ESPDUTRANSFORM_ATTR_EVENTSITEID_REQD                = false;
  String  ESPDUTRANSFORM_ATTR_EVENTSITEID_DFLT                = "0";
  String  ESPDUTRANSFORM_ATTR_FIRED1_NAME                     = "fired1";
  boolean ESPDUTRANSFORM_ATTR_FIRED1_REQD                     = false;
  String  ESPDUTRANSFORM_ATTR_FIRED1_DFLT                     = "false";
  String  ESPDUTRANSFORM_ATTR_FIRED2_NAME                     = "fired2";
  boolean ESPDUTRANSFORM_ATTR_FIRED2_REQD                     = false;
  String  ESPDUTRANSFORM_ATTR_FIRED2_DFLT                     = "false";
  String  ESPDUTRANSFORM_ATTR_FIREMISSIONINDEX_NAME           = "fireMissionIndex";
  boolean ESPDUTRANSFORM_ATTR_FIREMISSIONINDEX_REQD           = false;
  String  ESPDUTRANSFORM_ATTR_FIREMISSIONINDEX_DFLT           = "0";
  String  ESPDUTRANSFORM_ATTR_FIRINGRANGE_NAME                = "firingRange";
  boolean ESPDUTRANSFORM_ATTR_FIRINGRANGE_REQD                = false;
  String  ESPDUTRANSFORM_ATTR_FIRINGRANGE_DFLT                = "0.0";
  String  ESPDUTRANSFORM_ATTR_FIRINGRATE_NAME                 = "firingRate";
  boolean ESPDUTRANSFORM_ATTR_FIRINGRATE_REQD                 = false;
  String  ESPDUTRANSFORM_ATTR_FIRINGRATE_DFLT                 = "0";
  String  ESPDUTRANSFORM_ATTR_FORCEID_NAME                    = "forceID";
  boolean ESPDUTRANSFORM_ATTR_FORCEID_REQD                    = false;
  String  ESPDUTRANSFORM_ATTR_FORCEID_DFLT                    = "0";
  String  ESPDUTRANSFORM_ATTR_FUSE_NAME                       = "fuse";
  boolean ESPDUTRANSFORM_ATTR_FUSE_REQD                       = false;
  String  ESPDUTRANSFORM_ATTR_FUSE_DFLT                       = "0";
  String  ESPDUTRANSFORM_ATTR_LINEARVELOCITY_NAME             = "linearVelocity";
  boolean ESPDUTRANSFORM_ATTR_LINEARVELOCITY_REQD             = false;
  String  ESPDUTRANSFORM_ATTR_LINEARVELOCITY_DFLT             = "0 0 0";
  String  ESPDUTRANSFORM_ATTR_LINEARACCELERATION_NAME         = "linearAcceleration";
  boolean ESPDUTRANSFORM_ATTR_LINEARACCELERATION_REQD         = false;
  String  ESPDUTRANSFORM_ATTR_LINEARACCELERATION_DFLT         = "0 0 0";
  String  ESPDUTRANSFORM_ATTR_MARKING_NAME                    = "marking";
  boolean ESPDUTRANSFORM_ATTR_MARKING_REQD                    = false;
  String  ESPDUTRANSFORM_ATTR_MARKING_DFLT                    = "";
  String  ESPDUTRANSFORM_ATTR_MULTICASTRELAYHOST_NAME         = "multicastRelayHost";
  boolean ESPDUTRANSFORM_ATTR_MULTICASTRELAYHOST_REQD         = false;
  String  ESPDUTRANSFORM_ATTR_MULTICASTRELAYHOST_DFLT         = "";
  String  ESPDUTRANSFORM_ATTR_MULTICASTRELAYPORT_NAME         = "multicastRelayPort";
  boolean ESPDUTRANSFORM_ATTR_MULTICASTRELAYPORT_REQD         = false;
  String  ESPDUTRANSFORM_ATTR_MULTICASTRELAYPORT_DFLT         = "0";
  String  ESPDUTRANSFORM_ATTR_MUNITIONAPPLICATIONID_NAME      = "munitionApplicationID";
  boolean ESPDUTRANSFORM_ATTR_MUNITIONAPPLICATIONID_REQD      = false;
  String  ESPDUTRANSFORM_ATTR_MUNITIONAPPLICATIONID_DFLT      = "1";
  String  ESPDUTRANSFORM_ATTR_MUNITIONENDPOINT_NAME           = "munitionEndPoint";
  boolean ESPDUTRANSFORM_ATTR_MUNITIONENDPOINT_REQD           = false;
  String  ESPDUTRANSFORM_ATTR_MUNITIONENDPOINT_DFLT           = "0 0 0";
  String  ESPDUTRANSFORM_ATTR_MUNITIONENTITYID_NAME           = "munitionEntityID";
  boolean ESPDUTRANSFORM_ATTR_MUNITIONENTITYID_REQD           = false;
  String  ESPDUTRANSFORM_ATTR_MUNITIONENTITYID_DFLT           = "0";
  String  ESPDUTRANSFORM_ATTR_MUNITIONQUANTITY_NAME           = "munitionQuantity";
  boolean ESPDUTRANSFORM_ATTR_MUNITIONQUANTITY_REQD           = false;
  String  ESPDUTRANSFORM_ATTR_MUNITIONQUANTITY_DFLT           = "0";
  String  ESPDUTRANSFORM_ATTR_MUNITIONSITEID_NAME             = "munitionSiteID";
  boolean ESPDUTRANSFORM_ATTR_MUNITIONSITEID_REQD             = false;
  String  ESPDUTRANSFORM_ATTR_MUNITIONSITEID_DFLT             = "0";
  String  ESPDUTRANSFORM_ATTR_MUNITIONSTARTPOINT_NAME         = "munitionStartPoint";
  boolean ESPDUTRANSFORM_ATTR_MUNITIONSTARTPOINT_REQD         = false;
  String  ESPDUTRANSFORM_ATTR_MUNITIONSTARTPOINT_DFLT         = "0 0 0";
  String  ESPDUTRANSFORM_ATTR_NETWORKMODE_NAME                = "networkMode";
  boolean ESPDUTRANSFORM_ATTR_NETWORKMODE_REQD                = false;
  String  ESPDUTRANSFORM_ATTR_NETWORKMODE_DFLT                = "standAlone";
  String  ESPDUTRANSFORM_ATTR_PORT_NAME                       = "port";
  boolean ESPDUTRANSFORM_ATTR_PORT_REQD                       = false;
  String  ESPDUTRANSFORM_ATTR_PORT_DFLT                       = "0";
  String  ESPDUTRANSFORM_ATTR_READINTERVAL_NAME               = "readInterval";
  boolean ESPDUTRANSFORM_ATTR_READINTERVAL_REQD               = false;
  String  ESPDUTRANSFORM_ATTR_READINTERVAL_DFLT               = "0.1";
  String  ESPDUTRANSFORM_ATTR_ROTATION_NAME                   = "rotation";
  boolean ESPDUTRANSFORM_ATTR_ROTATION_REQD                   = false;
  String  ESPDUTRANSFORM_ATTR_ROTATION_DFLT                   = "0 0 1 0";
  String  ESPDUTRANSFORM_ATTR_SCALE_NAME                      = "scale";
  boolean ESPDUTRANSFORM_ATTR_SCALE_REQD                      = false;
  String  ESPDUTRANSFORM_ATTR_SCALE_DFLT                      = "1 1 1";
  String  ESPDUTRANSFORM_ATTR_SCALEORIENTATION_NAME           = "scaleOrientation";
  boolean ESPDUTRANSFORM_ATTR_SCALEORIENTATION_REQD           = false;
  String  ESPDUTRANSFORM_ATTR_SCALEORIENTATION_DFLT           = "0 0 1 0";
  String  ESPDUTRANSFORM_ATTR_SITEID_NAME                     = "siteID";
  boolean ESPDUTRANSFORM_ATTR_SITEID_REQD                     = false;
  String  ESPDUTRANSFORM_ATTR_SITEID_DFLT                     = "0";
  String  ESPDUTRANSFORM_ATTR_TRANSLATION_NAME                = "translation";
  boolean ESPDUTRANSFORM_ATTR_TRANSLATION_REQD                = false;
  String  ESPDUTRANSFORM_ATTR_TRANSLATION_DFLT                = "0 0 0";
  String  ESPDUTRANSFORM_ATTR_WARHEAD_NAME                    = "warhead";
  boolean ESPDUTRANSFORM_ATTR_WARHEAD_REQD                    = false;
  String  ESPDUTRANSFORM_ATTR_WARHEAD_DFLT                    = "0";
  String  ESPDUTRANSFORM_ATTR_WRITEINTERVAL_NAME              = "writeInterval";
  boolean ESPDUTRANSFORM_ATTR_WRITEINTERVAL_REQD              = false;
  String  ESPDUTRANSFORM_ATTR_WRITEINTERVAL_DFLT              = "1.0";
  String  ESPDUTRANSFORM_ATTR_BBOXCENTER_NAME                 = "bboxCenter";
  boolean ESPDUTRANSFORM_ATTR_BBOXCENTER_REQD                 = false;
  String  ESPDUTRANSFORM_ATTR_BBOXCENTER_DFLT                 = "0 0 0";
  String  ESPDUTRANSFORM_ATTR_BBOXSIZE_NAME                   = "bboxSize";
  boolean ESPDUTRANSFORM_ATTR_BBOXSIZE_REQD                   = false;
  String  ESPDUTRANSFORM_ATTR_BBOXSIZE_DFLT                   = "-1 -1 -1";
  String  ESPDUTRANSFORM_ATTR_RTPHEADEREXPECTED_NAME          = "rtpHeaderExpected";
  boolean ESPDUTRANSFORM_ATTR_RTPHEADEREXPECTED_REQD          = false;
  String  ESPDUTRANSFORM_ATTR_RTPHEADEREXPECTED_DFLT          = "false";

  String[] ESPDUTRANSFORM_ATTR_NETWORKNODE_CHOICES = {
      "standAlone",
      "networkReader",
      "networkWriter",};

  String  PDU_ATTR_ADDRESS_NAME                    = "address";
  boolean PDU_ATTR_ADDRESS_REQD                    = false;
  String  PDU_ATTR_ADDRESS_DFLT                    = "localhost";
  String  PDU_ATTR_APPLICATIONID_NAME              = "applicationID";
  boolean PDU_ATTR_APPLICATIONID_REQD              = false;
  String  PDU_ATTR_APPLICATIONID_DFLT              = "1";
  String  PDU_ATTR_ENABLED_NAME                    = "enabled";
  boolean PDU_ATTR_ENABLED_REQD                    = false;
  String  PDU_ATTR_ENABLED_DFLT                    = "true";
  String  PDU_ATTR_ENTITYID_NAME                   = "entityID";
  boolean PDU_ATTR_ENTITYID_REQD                   = false;
  String  PDU_ATTR_ENTITYID_DFLT                   = "0";
  String  PDU_ATTR_MULTICASTRELAYHOST_NAME         = "multicastRelayHost";
  boolean PDU_ATTR_MULTICASTRELAYHOST_REQD         = false;
  String  PDU_ATTR_MULTICASTRELAYHOST_DFLT         = "";
  String  PDU_ATTR_MULTICASTRELAYPORT_NAME         = "multicastRelayPort";
  boolean PDU_ATTR_MULTICASTRELAYPORT_REQD         = false;
  String  PDU_ATTR_MULTICASTRELAYPORT_DFLT         = "0";
  String  PDU_ATTR_NETWORKMODE_NAME                = "networkMode";
  boolean PDU_ATTR_NETWORKMODE_REQD                = false;
  String  PDU_ATTR_NETWORKMODE_DFLT                = "standAlone";
  String  PDU_ATTR_PORT_NAME                       = "port";
  boolean PDU_ATTR_PORT_REQD                       = false;
  String  PDU_ATTR_PORT_DFLT                       = "0";
  String  PDU_ATTR_RADIOID_NAME                    = "radioID";
  boolean PDU_ATTR_RADIOID_REQD                    = false;
  String  PDU_ATTR_RADIOID_DFLT                    = "0";
  String  PDU_ATTR_READINTERVAL_NAME               = "readInterval";
  boolean PDU_ATTR_READINTERVAL_REQD               = false;
  String  PDU_ATTR_READINTERVAL_DFLT               = "0.1";
  String  PDU_ATTR_RTPHEADEREXPECTED_NAME          = "rtpHeaderExpected";
  boolean PDU_ATTR_RTPHEADEREXPECTED_REQD          = false;
  String  PDU_ATTR_RTPHEADEREXPECTED_DFLT          = "false";
  String  PDU_ATTR_SITEID_NAME                     = "siteID";
  boolean PDU_ATTR_SITEID_REQD                     = false;
  String  PDU_ATTR_SITEID_DFLT                     = "0";
  String  PDU_ATTR_WHICHGEOMETRY_NAME              = "whichGeometry";
  boolean PDU_ATTR_WHICHGEOMETRY_REQD              = false;
  String  PDU_ATTR_WHICHGEOMETRY_DFLT              = "1";
  String  PDU_ATTR_WRITEINTERVAL_NAME              = "writeInterval";
  boolean PDU_ATTR_WRITEINTERVAL_REQD              = false;
  String  PDU_ATTR_WRITEINTERVAL_DFLT              = "1.0";
  String  PDU_ATTR_BBOXCENTER_NAME                 = "bboxCenter";
  boolean PDU_ATTR_BBOXCENTER_REQD                 = false;
  String  PDU_ATTR_BBOXCENTER_DFLT                 = "0 0 0";
  String  PDU_ATTR_BBOXSIZE_NAME                   = "bboxSize";
  boolean PDU_ATTR_BBOXSIZE_REQD                   = false;
  String  PDU_ATTR_BBOXSIZE_DFLT                   = "-1 -1 -1";

  String[] PDU_ATTR_NETWORKNODE_CHOICES           = ESPDUTRANSFORM_ATTR_NETWORKNODE_CHOICES;

  String  RECEIVERPDU_ELNAME                             = "ReceiverPdu";
  String  RECEIVERPDU_ATTR_RECEIVEDPOWER_NAME            = "receivedPower";
  boolean RECEIVERPDU_ATTR_RECEIVEDPOWER_REQD            = false;
  String  RECEIVERPDU_ATTR_RECEIVEDPOWER_DFLT            = "0";
  String  RECEIVERPDU_ATTR_RECEIVERSTATE_NAME            = "receiverState";
  boolean RECEIVERPDU_ATTR_RECEIVERSTATE_REQD            = false;
  String  RECEIVERPDU_ATTR_RECEIVERSTATE_DFLT            = "0";
  String  RECEIVERPDU_ATTR_TRANSMITTERAPPLICATIONID_NAME = "transmitterApplicationID";
  boolean RECEIVERPDU_ATTR_TRANSMITTERAPPLICATIONID_REQD = false;
  String  RECEIVERPDU_ATTR_TRANSMITTERAPPLICATIONID_DFLT = "1";
  String  RECEIVERPDU_ATTR_TRANSMITTERENTITYID_NAME      = "transmitterEntityID";
  boolean RECEIVERPDU_ATTR_TRANSMITTERENTITYID_REQD      = false;
  String  RECEIVERPDU_ATTR_TRANSMITTERENTITYID_DFLT      = "0";
  String  RECEIVERPDU_ATTR_TRANSMITTERRADIOID_NAME       = "transmitterRadioID";
  boolean RECEIVERPDU_ATTR_TRANSMITTERRADIOID_REQD       = false;
  String  RECEIVERPDU_ATTR_TRANSMITTERRADIOID_DFLT       = "0";
  String  RECEIVERPDU_ATTR_TRANSMITTERSITEID_NAME        = "transmitterSiteID";
  boolean RECEIVERPDU_ATTR_TRANSMITTERSITEID_REQD        = false;
  String  RECEIVERPDU_ATTR_TRANSMITTERSITEID_DFLT        = "0";

  String[]RECEIVERPDU_ATTR_RECEIVERSTATE_CHOICES = {
    "off","on but not receiving","on and receiving"};

  String  TRANSMITTERPDU_ELNAME                                       = "TransmitterPdu";
  String  TRANSMITTERPDU_ATTR_ANTENNALOCATION_NAME                    = "antennaLocation";
  boolean TRANSMITTERPDU_ATTR_ANTENNALOCATION_REQD                    = false;
  String  TRANSMITTERPDU_ATTR_ANTENNALOCATION_DFLT                    = "0 0 0";
  String  TRANSMITTERPDU_ATTR_ANTENNAPATTERNLENGTH_NAME               = "antennaPatternLength";
  boolean TRANSMITTERPDU_ATTR_ANTENNAPATTERNLENGTH_REQD               = false;
  String  TRANSMITTERPDU_ATTR_ANTENNAPATTERNLENGTH_DFLT               = "0";
  String  TRANSMITTERPDU_ATTR_ANTENNAPATTERNTYPE_NAME                 = "antennaPatternType";
  boolean TRANSMITTERPDU_ATTR_ANTENNAPATTERNTYPE_REQD                 = false;
  String  TRANSMITTERPDU_ATTR_ANTENNAPATTERNTYPE_DFLT                 = "0";
  String  TRANSMITTERPDU_ATTR_CRYPTOKEYID_NAME                        = "cryptoKeyID";
  boolean TRANSMITTERPDU_ATTR_CRYPTOKEYID_REQD                        = false;
  String  TRANSMITTERPDU_ATTR_CRYPTOKEYID_DFLT                        = "0";
  String  TRANSMITTERPDU_ATTR_CRYPTOSYSTEM_NAME                       = "cryptoSystem";
  boolean TRANSMITTERPDU_ATTR_CRYPTOSYSTEM_REQD                       = false;
  String  TRANSMITTERPDU_ATTR_CRYPTOSYSTEM_DFLT                       = "0";
  String  TRANSMITTERPDU_ATTR_FREQUENCY_NAME                          = "frequency";
  boolean TRANSMITTERPDU_ATTR_FREQUENCY_REQD                          = false;
  String  TRANSMITTERPDU_ATTR_FREQUENCY_DFLT                          = "0";
  String  TRANSMITTERPDU_ATTR_INPUTSOURCE_NAME                        = "inputSource";
  boolean TRANSMITTERPDU_ATTR_INPUTSOURCE_REQD                        = false;
  String  TRANSMITTERPDU_ATTR_INPUTSOURCE_DFLT                        = "0";
  String  TRANSMITTERPDU_ATTR_LENGTHOFMODULATIONPARAMETERS_NAME       = "lengthOfModulationParameters";
  boolean TRANSMITTERPDU_ATTR_LENGTHOFMODULATIONPARAMETERS_REQD       = false;
  String  TRANSMITTERPDU_ATTR_LENGTHOFMODULATIONPARAMETERS_DFLT       = "0";
  String  TRANSMITTERPDU_ATTR_MODULATIONTYPEDETAIL_NAME               = "modulationTypeDetail";
  boolean TRANSMITTERPDU_ATTR_MODULATIONTYPEDETAIL_REQD               = false;
  String  TRANSMITTERPDU_ATTR_MODULATIONTYPEDETAIL_DFLT               = "0";
  String  TRANSMITTERPDU_ATTR_MODULATIONTYPEMAJOR_NAME                = "modulationTypeMajor";
  boolean TRANSMITTERPDU_ATTR_MODULATIONTYPEMAJOR_REQD                = false;
  String  TRANSMITTERPDU_ATTR_MODULATIONTYPEMAJOR_DFLT                = "0";
  String  TRANSMITTERPDU_ATTR_MODULATIONTYPESPREADSPECTRUM_NAME       = "modulationTypeSpreadSpectrum";
  boolean TRANSMITTERPDU_ATTR_MODULATIONTYPESPREADSPECTRUM_REQD       = false;
  String  TRANSMITTERPDU_ATTR_MODULATIONTYPESPREADSPECTRUM_DFLT       = "0";
  String  TRANSMITTERPDU_ATTR_MODULATIONTYPESYSTEM_NAME               = "modulationTypeSystem";
  boolean TRANSMITTERPDU_ATTR_MODULATIONTYPESYSTEM_REQD               = false;
  String  TRANSMITTERPDU_ATTR_MODULATIONTYPESYSTEM_DFLT               = "0";
  String  TRANSMITTERPDU_ATTR_POWER_NAME                              = "power";
  boolean TRANSMITTERPDU_ATTR_POWER_REQD                              = false;
  String  TRANSMITTERPDU_ATTR_POWER_DFLT                              = "0";
  String  TRANSMITTERPDU_ATTR_RADIOENTITYTYPECATEGORY_NAME            = "radioEntityTypeCategory";
  boolean TRANSMITTERPDU_ATTR_RADIOENTITYTYPECATEGORY_REQD            = false;
  String  TRANSMITTERPDU_ATTR_RADIOENTITYTYPECATEGORY_DFLT            = "0";
  String  TRANSMITTERPDU_ATTR_RADIOENTITYTYPECOUNTRY_NAME             = "radioEntityTypeCountry";
  boolean TRANSMITTERPDU_ATTR_RADIOENTITYTYPECOUNTRY_REQD             = false;
  String  TRANSMITTERPDU_ATTR_RADIOENTITYTYPECOUNTRY_DFLT             = "0";
  String  TRANSMITTERPDU_ATTR_RADIOENTITYTYPEDOMAIN_NAME              = "radioEntityTypeDomain";
  boolean TRANSMITTERPDU_ATTR_RADIOENTITYTYPEDOMAIN_REQD              = false;
  String  TRANSMITTERPDU_ATTR_RADIOENTITYTYPEDOMAIN_DFLT              = "0";
  String  TRANSMITTERPDU_ATTR_RADIOENTITYTYPEKIND_NAME                = "radioEntityTypeKind";
  boolean TRANSMITTERPDU_ATTR_RADIOENTITYTYPEKIND_REQD                = false;
  String  TRANSMITTERPDU_ATTR_RADIOENTITYTYPEKIND_DFLT                = "0";
  String  TRANSMITTERPDU_ATTR_RADIOENTITYTYPENOMENCLATURE_NAME        = "radioEntityTypeNomenclature";
  boolean TRANSMITTERPDU_ATTR_RADIOENTITYTYPENOMENCLATURE_REQD        = false;
  String  TRANSMITTERPDU_ATTR_RADIOENTITYTYPENOMENCLATURE_DFLT        = "0";
  String  TRANSMITTERPDU_ATTR_RADIOENTITYTYPENOMENCLATUREVERSION_NAME = "radioEntityTypeNomenclatureVersion";
  boolean TRANSMITTERPDU_ATTR_RADIOENTITYTYPENOMENCLATUREVERSION_REQD = false;
  String  TRANSMITTERPDU_ATTR_RADIOENTITYTYPENOMENCLATUREVERSION_DFLT = "0";
  String  TRANSMITTERPDU_ATTR_RELATIVEANTENNALOCATION_NAME            = "relativeAntennaLocation";
  boolean TRANSMITTERPDU_ATTR_RELATIVEANTENNALOCATION_REQD            = false;
  String  TRANSMITTERPDU_ATTR_RELATIVEANTENNALOCATION_DFLT            = "0 0 0";
  String  TRANSMITTERPDU_ATTR_TRANSMITFREQUENCYBANDWIDTH_NAME         = "transmitFrequencyBandwidth";
  boolean TRANSMITTERPDU_ATTR_TRANSMITFREQUENCYBANDWIDTH_REQD         = false;
  String  TRANSMITTERPDU_ATTR_TRANSMITFREQUENCYBANDWIDTH_DFLT         = "0";
  String  TRANSMITTERPDU_ATTR_TRANSMITSTATE_NAME                      = "transmitState";
  boolean TRANSMITTERPDU_ATTR_TRANSMITSTATE_REQD                      = false;
  String  TRANSMITTERPDU_ATTR_TRANSMITSTATE_DFLT                      = "0";

  String[] TRANSMITTERPDU_ATTR_RADIOENTITYTYPEDOMAIN_CHOICES = {
      "other","land","air","surface","subsurface" };

  String[] TRANSMITTERPDU_ATTR_TRANSMITSTATE_CHOICES = {
    "off","on but not transmitting","on and transmitting"};

  String[] TRANSMITTERPDU_ATTR_INPUTSOURCE_CHOICES = {
    "other","pilot","copilot","first officer","driver","loader","gunner",
    "commander","digital data device","intercom"};

  String[] TRANSMITTERPDU_ATTR_ANTENNAPATTERNTYPE_CHOICES = {
    "omnidirectional","beam","spherical harmonic"};

  String[] TRANSMITTERPDU_ATTR_MODULATIONTYPESPREADSPECTRUM_CHOICES = {
    "frequency hopping","pseudo-noise","time hopping"};

  String[] TRANSMITTERPDU_ATTR_MODULATIONTYPEMAJOR_CHOICES = {
    "other","amplitude","amplitude and angle","angle","combination","pulse","unmodulated"};

  String[] TRANSMITTERPDU_ATTR_MODULATIONTYPESYSTEM_CHOICES = {
    "other","generic","HQ","HQII","HQIIA","SINCGARS","CCTT SINCGARS"};

  String[] TRANSMITTERPDU_ATTR_CRYPTOSYSTEM_CHOICES = {
    "other","KY-28","KY-58","Narrow Spectrum Secure Voice (NSVE)","Wide Spectrum Secure Voice (WSVE)","SINCGARS ICOM"};

  String  SIGNALPDU_ELNAME                   = "SignalPdu";
  String  SIGNALPDU_ATTR_DATA_NAME           = "data";
  boolean SIGNALPDU_ATTR_DATA_REQD           = false;
  String  SIGNALPDU_ATTR_DATA_DFLT           = "";
  String  SIGNALPDU_ATTR_DATALENGTH_NAME     = "dataLength";
  boolean SIGNALPDU_ATTR_DATALENGTH_REQD     = false;
  String  SIGNALPDU_ATTR_DATALENGTH_DFLT     = "0";
  String  SIGNALPDU_ATTR_ENCODINGSCHEME_NAME = "encodingScheme";
  boolean SIGNALPDU_ATTR_ENCODINGSCHEME_REQD = false;
  String  SIGNALPDU_ATTR_ENCODINGSCHEME_DFLT = "0";
  String  SIGNALPDU_ATTR_SAMPLERATE_NAME     = "sampleRate";
  boolean SIGNALPDU_ATTR_SAMPLERATE_REQD     = false;
  String  SIGNALPDU_ATTR_SAMPLERATE_DFLT     = "0";
  String  SIGNALPDU_ATTR_SAMPLES_NAME        = "samples";
  boolean SIGNALPDU_ATTR_SAMPLES_REQD        = false;
  String  SIGNALPDU_ATTR_SAMPLES_DFLT        = "0";
  String  SIGNALPDU_ATTR_TDLTYPE_NAME        = "tdlType";
  boolean SIGNALPDU_ATTR_TDLTYPE_REQD        = false;
  String  SIGNALPDU_ATTR_TDLTYPE_DFLT        = "0";

  String[]SIGNALPDU_ATTR_ENCODINGSCHEME_CHOICES = {
    "encoded voice","raw binary data","application-specific data","database index"};

  String[]SIGNALPDU_ATTR_ENCODINGTYPE_CHOICES = {
    "8-bit mu-law","CVSD per MIL-STD-188-113","ADPCM per CCITT G.721","16-bit linear PCM","8-bit linear PCM","vector quantization"};

  // CAD

  String  CADASSEMBLY_ELNAME                = "CADAssembly";

  String  CADASSEMBLY_ATTR_NAME_NAME        = "name";
  boolean CADASSEMBLY_ATTR_NAME_REQD        = false;
  String  CADASSEMBLY_ATTR_NAME_DFLT        = "";
  String  CADASSEMBLY_ATTR_BBOXCENTER_NAME  = "bboxCenter";
  boolean CADASSEMBLY_ATTR_BBOXCENTER_REQD  = false;
  String  CADASSEMBLY_ATTR_BBOXCENTER_DFLT  = "0 0 0";
  String  CADASSEMBLY_ATTR_BBOXSIZE_NAME    = "bboxSize";
  boolean CADASSEMBLY_ATTR_BBOXSIZE_REQD    = false;
  String  CADASSEMBLY_ATTR_BBOXSIZE_DFLT    = "-1 -1 -1";

  String  CADFACE_ELNAME                = "CADFace";
  String  CADFACE_ATTR_NAME_NAME        = "name";
  boolean CADFACE_ATTR_NAME_REQD        = false;
  String  CADFACE_ATTR_NAME_DFLT        = "";
  String  CADFACE_ATTR_BBOXCENTER_NAME  = "bboxCenter";
  boolean CADFACE_ATTR_BBOXCENTER_REQD  = false;
  String  CADFACE_ATTR_BBOXCENTER_DFLT  = "0 0 0";
  String  CADFACE_ATTR_BBOXSIZE_NAME    = "bboxSize";
  boolean CADFACE_ATTR_BBOXSIZE_REQD    = false;
  String  CADFACE_ATTR_BBOXSIZE_DFLT    = "-1 -1 -1";

  String  CADLAYER_ELNAME                = "CADLayer";
  String  CADLAYER_ATTR_NAME_NAME        = "name";
  boolean CADLAYER_ATTR_NAME_REQD        = false;
  String  CADLAYER_ATTR_NAME_DFLT        = "";
  String  CADLAYER_ATTR_VISIBLE_NAME     = "visible";
  boolean CADLAYER_ATTR_VISIBLE_REQD     = false;
  String  CADLAYER_ATTR_VISIBLE_DFLT     = "";
  String  CADLAYER_ATTR_BBOXCENTER_NAME  = "bboxCenter";
  boolean CADLAYER_ATTR_BBOXCENTER_REQD  = false;
  String  CADLAYER_ATTR_BBOXCENTER_DFLT  = "0 0 0";
  String  CADLAYER_ATTR_BBOXSIZE_NAME    = "bboxSize";
  boolean CADLAYER_ATTR_BBOXSIZE_REQD    = false;
  String  CADLAYER_ATTR_BBOXSIZE_DFLT    = "-1 -1 -1";

  String  CADPART_ELNAME                     = "CADPart";
  String  CADPART_ATTR_NAME_NAME             = "name";
  boolean CADPART_ATTR_NAME_REQD             = false;
  String  CADPART_ATTR_NAME_DFLT             = "";
  String  CADPART_ATTR_TRANSLATION_NAME      = "translation";
  boolean CADPART_ATTR_TRANSLATION_REQD      = false;
  String  CADPART_ATTR_TRANSLATION_DFLT      = "0 0 0";
  String  CADPART_ATTR_ROTATION_NAME         = "rotation";
  boolean CADPART_ATTR_ROTATION_REQD         = false;
  String  CADPART_ATTR_ROTATION_DFLT         = "0 0 1 0";
  String  CADPART_ATTR_CENTER_NAME           = "center";
  boolean CADPART_ATTR_CENTER_REQD           = false;
  String  CADPART_ATTR_CENTER_DFLT           = "0 0 0";
  String  CADPART_ATTR_SCALE_NAME            = "scale";
  boolean CADPART_ATTR_SCALE_REQD            = false;
  String  CADPART_ATTR_SCALE_DFLT            = "1 1 1";
  String  CADPART_ATTR_SCALEORIENTATION_NAME = "scaleOrientation";
  boolean CADPART_ATTR_SCALEORIENTATION_REQD = false;
  String  CADPART_ATTR_SCALEORIENTATION_DFLT = "0 0 1 0";
  String  CADPART_ATTR_BBOXCENTER_NAME       = "bboxCenter";
  boolean CADPART_ATTR_BBOXCENTER_REQD       = false;
  String  CADPART_ATTR_BBOXCENTER_DFLT       = "0 0 0";
  String  CADPART_ATTR_BBOXSIZE_NAME         = "bboxSize";
  boolean CADPART_ATTR_BBOXSIZE_REQD         = false;
  String  CADPART_ATTR_BBOXSIZE_DFLT         = "-1 -1 -1";

  // Texturing3D Component

  // Element ComposedTexture3D
  String  COMPOSEDTEXTURE3D_ELNAME            = "ComposedTexture3D";
  String  COMPOSEDTEXTURE3D_ATTR_REPEATS_NAME = "repeatS";
  boolean COMPOSEDTEXTURE3D_ATTR_REPEATS_REQD = false;
  String  COMPOSEDTEXTURE3D_ATTR_REPEATS_DFLT = "false";

  String  COMPOSEDTEXTURE3D_ATTR_REPEATT_NAME = "repeatT";
  boolean COMPOSEDTEXTURE3D_ATTR_REPEATT_REQD = false;
  String  COMPOSEDTEXTURE3D_ATTR_REPEATT_DFLT = "false";

  String  COMPOSEDTEXTURE3D_ATTR_REPEATR_NAME = "repeatR";
  boolean COMPOSEDTEXTURE3D_ATTR_REPEATR_REQD = false;
  String  COMPOSEDTEXTURE3D_ATTR_REPEATR_DFLT = "false";

  // containerfield arrays also used by ImageTexture3D and PixelTexture3D
  String[]COMPOSEDTEXTURE3D_ATTR_CONTAINERFIELD_CHOICES = {
    "texture", "gradients","segmentIdentifiers","surfaceNormals","transferFunction","voxels"};
  String  COMPOSEDTEXTURE3D_ATTR_CONTAINERFIELD_TOOLTIP =
    "texture if parent is Shape or ComposedTexture3D, or other value if parent is a volume node";
  String[]COMPOSEDTEXTURE3D_ATTR_CONTAINERFIELD_TOOLTIPS = {
    "texture if parent is Shape or ComposedTexture3D",
    "gradients if parent is IsoSurfaceVolumeData",
    "segmentIdentifiers if parent is SegmentedVolumeData",
    "surfaceNormals if parent is Cartoon*, EdgeEnhancement*, Shaded*, SilhouetteEnhancement* or ToneMapped* *VolumeStyle",
    "transferFunction if parent is OpacityMapVolumeStyle",
    "voxels if parent is VolumeData, IsoSurfaceVolumeData, SegmentedVolumeData or BlendedVolumeStyle",
    };

  // Element ImageTexture3D
  String  IMAGETEXTURE3D_ELNAME            = "ImageTexture3D";
  String  IMAGETEXTURE3D_ATTR_REPEATS_NAME = "repeatS";
  boolean IMAGETEXTURE3D_ATTR_REPEATS_REQD = false;
  String  IMAGETEXTURE3D_ATTR_REPEATS_DFLT = "false";

  String  IMAGETEXTURE3D_ATTR_REPEATT_NAME = "repeatT";
  boolean IMAGETEXTURE3D_ATTR_REPEATT_REQD = false;
  String  IMAGETEXTURE3D_ATTR_REPEATT_DFLT = "false";

  String  IMAGETEXTURE3D_ATTR_REPEATR_NAME = "repeatR";
  boolean IMAGETEXTURE3D_ATTR_REPEATR_REQD = false;
  String  IMAGETEXTURE3D_ATTR_REPEATR_DFLT = "false";

  String  IMAGETEXTURE3D_ATTR_URL_NAME     = "url";
  boolean IMAGETEXTURE3D_ATTR_URL_REQD     = false;
  String  IMAGETEXTURE3D_ATTR_URL_DFLT     = "";

  // Element PixelTexture3D
  String  PIXELTEXTURE3D_ELNAME             = "PixelTexture3D";
  String  PIXELTEXTURE3D_ATTR_IMAGE_NAME    = "image";
  boolean PIXELTEXTURE3D_ATTR_IMAGE_REQD    = false;
  String  PIXELTEXTURE3D_ATTR_IMAGE_DFLT    = "0 0 0 0";
  String  PIXELTEXTURE3D_ATTR_REPEATS_NAME  = "repeatS";
  boolean PIXELTEXTURE3D_ATTR_REPEATS_REQD  = false;
  String  PIXELTEXTURE3D_ATTR_REPEATS_DFLT  = "false";
  String  PIXELTEXTURE3D_ATTR_REPEATT_NAME  = "repeatT";
  boolean PIXELTEXTURE3D_ATTR_REPEATT_REQD  = false;
  String  PIXELTEXTURE3D_ATTR_REPEATT_DFLT  = "false";
  String  PIXELTEXTURE3D_ATTR_REPEATR_NAME  = "repeatR";
  boolean PIXELTEXTURE3D_ATTR_REPEATR_REQD  = false;
  String  PIXELTEXTURE3D_ATTR_REPEATR_DFLT  = "false";

  // Element TextureCoordinate3D
  String  TEXTURECOORDINATE3D_ELNAME          = "TextureCoordinate3D";
  String  TEXTURECOORDINATE3D_ATTR_POINT_NAME = "point";
  boolean TEXTURECOORDINATE3D_ATTR_POINT_REQD = false;
  String  TEXTURECOORDINATE3D_ATTR_POINT_DFLT = "0 0 0"; // override X3D spec to provide commonly used defaults for building interface

  // Element TextureCoordinate4D
  String  TEXTURECOORDINATE4D_ELNAME          = "TextureCoordinate4D";
  String  TEXTURECOORDINATE4D_ATTR_POINT_NAME = "point";
  boolean TEXTURECOORDINATE4D_ATTR_POINT_REQD = false;
  String  TEXTURECOORDINATE4D_ATTR_POINT_DFLT = "0 0 0 0"; // override X3D spec to provide commonly used defaults for building interface

  // TextureTransformMatrix3D
  String  TEXTURETRANSFORMMATRIX3D_ELNAME          = "TextureTransformMatrix3D";
  String  TEXTURETRANSFORMMATRIX3D_ATTR_MATRIX_NAME = "matrix";
  boolean TEXTURETRANSFORMMATRIX3D_ATTR_MATRIX_REQD = false;
  String  TEXTURETRANSFORMMATRIX3D_ATTR_MATRIX_DFLT = "1 0 0 0 0 1 0 0 0 0 1 0 0 0 0 1";

  // element TextureTransform3D
  String  TEXTURETRANSFORM3D_ELNAME                     = "TextureTransform3D";
  String  TEXTURETRANSFORM3D_ATTR_TRANSLATION_NAME      = "translation";
  boolean TEXTURETRANSFORM3D_ATTR_TRANSLATION_REQD      = false;
  String  TEXTURETRANSFORM3D_ATTR_TRANSLATION_DFLT      = "0 0 0";
  String  TEXTURETRANSFORM3D_ATTR_ROTATION_NAME         = "rotation";
  boolean TEXTURETRANSFORM3D_ATTR_ROTATION_REQD         = false;
  String  TEXTURETRANSFORM3D_ATTR_ROTATION_DFLT         = "0 0 1 0";
  String  TEXTURETRANSFORM3D_ATTR_CENTER_NAME           = "center";
  boolean TEXTURETRANSFORM3D_ATTR_CENTER_REQD           = false;
  String  TEXTURETRANSFORM3D_ATTR_CENTER_DFLT           = "0 0 0";
  String  TEXTURETRANSFORM3D_ATTR_SCALE_NAME            = "scale";
  boolean TEXTURETRANSFORM3D_ATTR_SCALE_REQD            = false;
  String  TEXTURETRANSFORM3D_ATTR_SCALE_DFLT            = "1 1 1";

  // RigidBodyPhysics Component

  // Element BallJoint
  String  BALLJOINT_ELNAME                   = "BallJoint";
  String  BALLJOINT_ATTR_ANCHORPOINT_NAME    = "anchorPoint";
  boolean BALLJOINT_ATTR_ANCHORPOINT_REQD    = false;
  String  BALLJOINT_ATTR_ANCHORPOINT_DFLT    = "0 0 0";
  String  BALLJOINT_ATTR_FORCEOUTPUT_NAME    = "forceOutput";
  boolean BALLJOINT_ATTR_FORCEOUTPUT_REQD    = false;
  String  BALLJOINT_ATTR_FORCEOUTPUT_DFLT    = "NONE";
  String[]BALLJOINT_ATTR_FORCEOUTPUT_CHOICES = {
    "ALL","NONE"};  // otherwise other joint names

  // X3DNBodyCollidableNode includes CollidableOffset and CollidableShape

  String[]X3DNBodyCollidableNode_ATTR_CONTAINERFIELD_CHOICES = {
    "children", "collidable","collidables","intersections","geometry","geometry1","geometry2"};
  String X3DNBodyCollidableNode_ATTR_CONTAINERFIELD_TOOLTIP = "see X3D Tooltips for selecting proper containerField";
  // TODO individual tooltips for each choice not yet implemented:
  String[]X3DNBodyCollidableNode_ATTR_CONTAINERFIELD_TOOLTIPS = {
    "children if parent is a grouping node or X3D scene root",
    "collidable if parent is CollidableOffset",
    "collidables if parent is CollisionCollection",
    "intersections if parent is CollisionSensor",
    "geometry if parent is RigidBody",
    "geometry1 if parent is Contact",
    "geometry2 if parent is Contact"};

  String  COLLIDABLEOFFSET_ELNAME                = "CollidableOffset";
  String  COLLIDABLEOFFSET_ATTR_ENABLED_NAME     = "enabled";
  boolean COLLIDABLEOFFSET_ATTR_ENABLED_REQD     = false;
  String  COLLIDABLEOFFSET_ATTR_ENABLED_DFLT     = "true";
  String  COLLIDABLEOFFSET_ATTR_ROTATION_NAME    = "rotation";
  boolean COLLIDABLEOFFSET_ATTR_ROTATION_REQD    = false;
  String  COLLIDABLEOFFSET_ATTR_ROTATION_DFLT    = "0 0 1 0";
  String  COLLIDABLEOFFSET_ATTR_TRANSLATION_NAME = "translation";
  boolean COLLIDABLEOFFSET_ATTR_TRANSLATION_REQD = false;
  String  COLLIDABLEOFFSET_ATTR_TRANSLATION_DFLT = "0 0 0";
  String  COLLIDABLEOFFSET_ATTR_BBOXCENTER_NAME  = "bboxCenter";
  boolean COLLIDABLEOFFSET_ATTR_BBOXCENTER_REQD  = false;
  String  COLLIDABLEOFFSET_ATTR_BBOXCENTER_DFLT  = "0 0 0";
  String  COLLIDABLEOFFSET_ATTR_BBOXSIZE_NAME    = "bboxSize";
  boolean COLLIDABLEOFFSET_ATTR_BBOXSIZE_REQD    = false;
  String  COLLIDABLEOFFSET_ATTR_BBOXSIZE_DFLT    = "-1 -1 -1";

  String  COLLIDABLESHAPE_ELNAME                    = "CollidableShape";
  String  COLLIDABLESHAPE_ATTR_ENABLED_NAME         = "enabled";
  boolean COLLIDABLESHAPE_ATTR_ENABLED_REQD         = false;
  String  COLLIDABLESHAPE_ATTR_ENABLED_DFLT         = "true";
  String  COLLIDABLESHAPE_ATTR_TRANSLATION_NAME     = "translation";
  boolean COLLIDABLESHAPE_ATTR_TRANSLATION_REQD     = false;
  String  COLLIDABLESHAPE_ATTR_TRANSLATION_DFLT     = "0 0 0";
  String  COLLIDABLESHAPE_ATTR_ROTATION_NAME        = "rotation";
  boolean COLLIDABLESHAPE_ATTR_ROTATION_REQD        = false;
  String  COLLIDABLESHAPE_ATTR_ROTATION_DFLT        = "0 0 1 0";
  String  COLLIDABLESHAPE_ATTR_BBOXCENTER_NAME      = "bboxCenter";
  boolean COLLIDABLESHAPE_ATTR_BBOXCENTER_REQD      = false;
  String  COLLIDABLESHAPE_ATTR_BBOXCENTER_DFLT      = "0 0 0";
  String  COLLIDABLESHAPE_ATTR_BBOXSIZE_NAME        = "bboxSize";
  boolean COLLIDABLESHAPE_ATTR_BBOXSIZE_REQD        = false;
  String  COLLIDABLESHAPE_ATTR_BBOXSIZE_DFLT        = "-1 -1 -1";

  String[]COLLISIONCOLLECTION_ATTR_CONTAINERFIELD_CHOICES = {
    "children", "collider"};
  String COLLISIONCOLLECTION_ATTR_CONTAINERFIELD_TOOLTIP  =
    "'collider' if parent is CollisionSensor or RigidBodyCollection, otherwise 'children'";
    // TODO individual tooltips for each choice not yet implemented:
  String[]COLLISIONCOLLECTION_ATTR_CONTAINERFIELD_TOOLTIPS = {
    "children if parent is a grouping node or X3D scene root",
    "collider if parent is CollisionSensor or RigidBodyCollection"
  };
  String  COLLISIONCOLLECTION_ELNAME                             = "CollisionCollection";
  String  COLLISIONCOLLECTION_ATTR_APPLIEDPARAMETERS_NAME        = "appliedParameters";
  boolean COLLISIONCOLLECTION_ATTR_APPLIEDPARAMETERS_REQD        = false;
  String[]COLLISIONCOLLECTION_ATTR_APPLIEDPARAMETERS_DFLT       = {"BOUNCE"};
  String[]COLLISIONCOLLECTION_ATTR_APPLIEDPARAMETERS_CHOICES = {
    "BOUNCE","USER_FRICTION","FRICTION_COEFFICIENT-2","ERROR_REDUCTION","CONSTANT_FORCE","SPEED-1","SPEED-2","SLIP-1","SLIP-2"};
  String  COLLISIONCOLLECTION_ATTR_BOUNCE_NAME                   = "bounce";
  boolean COLLISIONCOLLECTION_ATTR_BOUNCE_REQD                   = false;
  String  COLLISIONCOLLECTION_ATTR_BOUNCE_DFLT                   = "0";
  String  COLLISIONCOLLECTION_ATTR_ENABLED_NAME                  = "enabled";
  boolean COLLISIONCOLLECTION_ATTR_ENABLED_REQD                  = false;
  String  COLLISIONCOLLECTION_ATTR_ENABLED_DFLT                  = "true";
  String  COLLISIONCOLLECTION_ATTR_FRICTIONCOEFFICIENTS_NAME     = "frictionCoefficients";
  boolean COLLISIONCOLLECTION_ATTR_FRICTIONCOEFFICIENTS_REQD     = false;
  String  COLLISIONCOLLECTION_ATTR_FRICTIONCOEFFICIENTS_DFLT     = "0 0";
  String  COLLISIONCOLLECTION_ATTR_MINBOUNCESPEED_NAME           = "minBounceSpeed";
  boolean COLLISIONCOLLECTION_ATTR_MINBOUNCESPEED_REQD           = false;
  String  COLLISIONCOLLECTION_ATTR_MINBOUNCESPEED_DFLT           = "0.1";
  String  COLLISIONCOLLECTION_ATTR_SLIPFACTORS_NAME              = "slipFactors";
  boolean COLLISIONCOLLECTION_ATTR_SLIPFACTORS_REQD              = false;
  String  COLLISIONCOLLECTION_ATTR_SLIPFACTORS_DFLT              = "0 0";
  String  COLLISIONCOLLECTION_ATTR_SOFTNESSCONSTANTFORCEMIX_NAME = "softnessConstantForceMix";
  boolean COLLISIONCOLLECTION_ATTR_SOFTNESSCONSTANTFORCEMIX_REQD = false;
  String  COLLISIONCOLLECTION_ATTR_SOFTNESSCONSTANTFORCEMIX_DFLT = "0.0001";
  String  COLLISIONCOLLECTION_ATTR_SOFTNESSERRORCORRECTION_NAME  = "softnessErrorCorrection";
  boolean COLLISIONCOLLECTION_ATTR_SOFTNESSERRORCORRECTION_REQD  = false;
  String  COLLISIONCOLLECTION_ATTR_SOFTNESSERRORCORRECTION_DFLT  = "0.8";
  String  COLLISIONCOLLECTION_ATTR_SURFACESPEED_NAME             = "surfaceSpeed";
  boolean COLLISIONCOLLECTION_ATTR_SURFACESPEED_REQD             = false;
  String  COLLISIONCOLLECTION_ATTR_SURFACESPEED_DFLT             = "0 0";

  String  COLLISIONSENSOR_ELNAME                    = "CollisionSensor";
  String  COLLISIONSENSOR_ATTR_ENABLED_NAME         = "enabled";
  boolean COLLISIONSENSOR_ATTR_ENABLED_REQD         = false;
  String  COLLISIONSENSOR_ATTR_ENABLED_DFLT         = "true";

  // X3DNBodyCollisionSpaceNode only includes CollisionSpace
  String[]COLLISIONSPACE_ATTR_CONTAINERFIELD_CHOICES = {
    "children", "collidables"};
  String  COLLISIONSPACE_ATTR_CONTAINERFIELD_TOOLTIP = "'collidables' if parent is CollisionCollection or CollisionSpace, otherwise 'children'";
  String[]COLLISIONSPACE_ATTR_CONTAINERFIELD_TOOLTIPS = {
    "children if parent is a grouping node or X3D scene root",
    "collidables if parent is CollisionCollection or CollisionSpace"
  };
  String  COLLISIONSPACE_ELNAME                     = "CollisionSpace";
  String  COLLISIONSPACE_ATTR_ENABLED_NAME          = "enabled";
  boolean COLLISIONSPACE_ATTR_ENABLED_REQD          = false;
  String  COLLISIONSPACE_ATTR_ENABLED_DFLT          = "true";
  String  COLLISIONSPACE_ATTR_USEGEOMETRY_NAME      = "useGeometry";
  boolean COLLISIONSPACE_ATTR_USEGEOMETRY_REQD      = false;
  String  COLLISIONSPACE_ATTR_USEGEOMETRY_DFLT      = "false";
  String  COLLISIONSPACE_ATTR_BBOXCENTER_NAME       = "bboxCenter";
  boolean COLLISIONSPACE_ATTR_BBOXCENTER_REQD       = false;
  String  COLLISIONSPACE_ATTR_BBOXCENTER_DFLT       = "0 0 0";
  String  COLLISIONSPACE_ATTR_BBOXSIZE_NAME         = "bboxSize";
  boolean COLLISIONSPACE_ATTR_BBOXSIZE_REQD         = false;
  String  COLLISIONSPACE_ATTR_BBOXSIZE_DFLT         = "-1 -1 -1";

  String  CONTACT_ELNAME                             = "Contact";
  String  CONTACT_ATTR_APPLIEDPARAMETERS_NAME        = "appliedParameters";
  boolean CONTACT_ATTR_APPLIEDPARAMETERS_REQD        = false;
  String[]CONTACT_ATTR_APPLIEDPARAMETERS_DFLT       = {"BOUNCE"};
  String[]CONTACT_ATTR_APPLIEDPARAMETERS_CHOICES = {
    "BOUNCE","USER_FRICTION","FRICTION_COEFFICIENT-2","ERROR_REDUCTION","CONSTANT_FORCE","SPEED-1","SPEED-2","SLIP-1","SLIP-2"};
  String  CONTACT_ATTR_BOUNCE_NAME                   = "bounce";
  boolean CONTACT_ATTR_BOUNCE_REQD                   = false;
  String  CONTACT_ATTR_BOUNCE_DFLT                   = "0";
  String  CONTACT_ATTR_CONTACTNORMAL_NAME            = "contactNormal";
  boolean CONTACT_ATTR_CONTACTNORMAL_REQD            = false;
  String  CONTACT_ATTR_CONTACTNORMAL_DFLT            = "0 1 0";
  String  CONTACT_ATTR_DEPTH_NAME                    = "depth";
  boolean CONTACT_ATTR_DEPTH_REQD                    = false;
  String  CONTACT_ATTR_DEPTH_DFLT                    = "0";
  String  CONTACT_ATTR_ENABLED_NAME                  = "enabled";
  boolean CONTACT_ATTR_ENABLED_REQD                  = false;
  String  CONTACT_ATTR_ENABLED_DFLT                  = "true";
  String  CONTACT_ATTR_FRICTIONCOEFFICIENTS_NAME     = "frictionCoefficients";
  boolean CONTACT_ATTR_FRICTIONCOEFFICIENTS_REQD     = false;
  String  CONTACT_ATTR_FRICTIONCOEFFICIENTS_DFLT     = "0 0";
  String  CONTACT_ATTR_FRICTIONDIRECTION_NAME        = "frictionDirection";
  boolean CONTACT_ATTR_FRICTIONDIRECTION_REQD        = false;
  String  CONTACT_ATTR_FRICTIONDIRECTION_DFLT        = "0 1 0";
  String  CONTACT_ATTR_MINBOUNCESPEED_NAME           = "minBounceSpeed";
  boolean CONTACT_ATTR_MINBOUNCESPEED_REQD           = false;
  String  CONTACT_ATTR_MINBOUNCESPEED_DFLT           = "0.1";
  String  CONTACT_ATTR_POSITION_NAME                 = "position";
  boolean CONTACT_ATTR_POSITION_REQD                 = false;
  String  CONTACT_ATTR_POSITION_DFLT                 = "0 0 0";
  String  CONTACT_ATTR_SLIPCOEFFICIENTS_NAME         = "slipCoefficients";
  boolean CONTACT_ATTR_SLIPCOEFFICIENTS_REQD         = false;
  String  CONTACT_ATTR_SLIPCOEFFICIENTS_DFLT         = "0 0";
  String  CONTACT_ATTR_SOFTNESSCONSTANTFORCEMIX_NAME = "softnessConstantForceMix";
  boolean CONTACT_ATTR_SOFTNESSCONSTANTFORCEMIX_REQD = false;
  String  CONTACT_ATTR_SOFTNESSCONSTANTFORCEMIX_DFLT = "0.0001";
  String  CONTACT_ATTR_SOFTNESSERRORCORRECTION_NAME  = "softnessErrorCorrection";
  boolean CONTACT_ATTR_SOFTNESSERRORCORRECTION_REQD  = false;
  String  CONTACT_ATTR_SOFTNESSERRORCORRECTION_DFLT  = "0.8";
  String  CONTACT_ATTR_SURFACESPEED_NAME             = "surfaceSpeed";
  boolean CONTACT_ATTR_SURFACESPEED_REQD             = false;
  String  CONTACT_ATTR_SURFACESPEED_DFLT             = "0 0";

  String  MOTORJOINT_ELNAME                         = "MotorJoint";
  String  MOTORJOINT_ATTR_FORCEOUTPUT_NAME          = "forceOutput";
  boolean MOTORJOINT_ATTR_FORCEOUTPUT_REQD          = false;
  String  MOTORJOINT_ATTR_FORCEOUTPUT_DFLT          = "NONE";
  String  MOTORJOINT_ATTR_AUTOCALC_NAME             = "autoCalc";
  boolean MOTORJOINT_ATTR_AUTOCALC_REQD             = false;
  String  MOTORJOINT_ATTR_AUTOCALC_DFLT             = "false";
  String  MOTORJOINT_ATTR_ENABLEDAXES_NAME          = "enabledAxes";
  boolean MOTORJOINT_ATTR_ENABLEDAXES_REQD          = false;
  String  MOTORJOINT_ATTR_ENABLEDAXES_DFLT          = "0";
  String  MOTORJOINT_ATTR_AXIS1ANGLE_NAME           = "axis1Angle";
  String  MOTORJOINT_ATTR_AXIS2ANGLE_NAME           = "axis2Angle";
  String  MOTORJOINT_ATTR_AXIS3ANGLE_NAME           = "axis3Angle";
  boolean MOTORJOINT_ATTR_AXIS1ANGLE_REQD           = false;
  boolean MOTORJOINT_ATTR_AXIS2ANGLE_REQD           = false;
  boolean MOTORJOINT_ATTR_AXIS3ANGLE_REQD           = false;
  String  MOTORJOINT_ATTR_AXIS1ANGLE_DFLT           = "0";
  String  MOTORJOINT_ATTR_AXIS2ANGLE_DFLT           = "0";
  String  MOTORJOINT_ATTR_AXIS3ANGLE_DFLT           = "0";
  String  MOTORJOINT_ATTR_AXIS1TORQUE_NAME          = "axis1Torque";
  String  MOTORJOINT_ATTR_AXIS2TORQUE_NAME          = "axis2Torque";
  String  MOTORJOINT_ATTR_AXIS3TORQUE_NAME          = "axis3Torque";
  boolean MOTORJOINT_ATTR_AXIS1TORQUE_REQD          = false;
  boolean MOTORJOINT_ATTR_AXIS2TORQUE_REQD          = false;
  boolean MOTORJOINT_ATTR_AXIS3TORQUE_REQD          = false;
  String  MOTORJOINT_ATTR_AXIS1TORQUE_DFLT          = "0";
  String  MOTORJOINT_ATTR_AXIS2TORQUE_DFLT          = "0";
  String  MOTORJOINT_ATTR_AXIS3TORQUE_DFLT          = "0";
  String  MOTORJOINT_ATTR_MOTOR1AXIS_NAME           = "motor1Axis";
  String  MOTORJOINT_ATTR_MOTOR2AXIS_NAME           = "motor2Axis";
  String  MOTORJOINT_ATTR_MOTOR3AXIS_NAME           = "motor3Axis";
  boolean MOTORJOINT_ATTR_MOTOR1AXIS_REQD           = false;
  boolean MOTORJOINT_ATTR_MOTOR2AXIS_REQD           = false;
  boolean MOTORJOINT_ATTR_MOTOR3AXIS_REQD           = false;
  String  MOTORJOINT_ATTR_MOTOR1AXIS_DFLT           = "0 0 0";
  String  MOTORJOINT_ATTR_MOTOR2AXIS_DFLT           = "0 0 0";
  String  MOTORJOINT_ATTR_MOTOR3AXIS_DFLT           = "0 0 0";
  String  MOTORJOINT_ATTR_STOP1BOUNCE_NAME          = "stop1Bounce";
  String  MOTORJOINT_ATTR_STOP2BOUNCE_NAME          = "stop2Bounce";
  String  MOTORJOINT_ATTR_STOP3BOUNCE_NAME          = "stop3Bounce";
  boolean MOTORJOINT_ATTR_STOP1BOUNCE_REQD          = false;
  boolean MOTORJOINT_ATTR_STOP2BOUNCE_REQD          = false;
  boolean MOTORJOINT_ATTR_STOP3BOUNCE_REQD          = false;
  String  MOTORJOINT_ATTR_STOP1BOUNCE_DFLT          = "0";
  String  MOTORJOINT_ATTR_STOP2BOUNCE_DFLT          = "0";
  String  MOTORJOINT_ATTR_STOP3BOUNCE_DFLT          = "0";
  String  MOTORJOINT_ATTR_STOP1ERRORCORRECTION_NAME = "stop1ErrorCorrection";
  String  MOTORJOINT_ATTR_STOP2ERRORCORRECTION_NAME = "stop2ErrorCorrection";
  String  MOTORJOINT_ATTR_STOP3ERRORCORRECTION_NAME = "stop3ErrorCorrection";
  boolean MOTORJOINT_ATTR_STOP1ERRORCORRECTION_REQD = false;
  boolean MOTORJOINT_ATTR_STOP2ERRORCORRECTION_REQD = false;
  boolean MOTORJOINT_ATTR_STOP3ERRORCORRECTION_REQD = false;
  String  MOTORJOINT_ATTR_STOP1ERRORCORRECTION_DFLT = "0.8";
  String  MOTORJOINT_ATTR_STOP2ERRORCORRECTION_DFLT = "0.8";
  String  MOTORJOINT_ATTR_STOP3ERRORCORRECTION_DFLT = "0.8";

  String[]RIGIDBODY_ATTR_CONTAINERFIELD_CHOICES    = {"body1", "body2", "bodies"};
  String  RIGIDBODY_ATTR_CONTAINERFIELD_TOOLTIP    = "'body1' or 'body2' for parent Contact or Joint node, 'bodies' for parent RigidBodyCollection";
  String[]RIGIDBODY_ATTR_CONTAINERFIELD_TOOLTIPS   =
  {
      "body1 for parent Contact or Joint node",
      "body2 for parent Contact or Joint node",
      "bodies for parent RigidBodyCollection"
  };
  String  RIGIDBODY_ELNAME                             = "RigidBody";
  String  RIGIDBODY_ATTR_ANGULARDAMPINGFACTOR_NAME     = "angularDampingFactor";
  boolean RIGIDBODY_ATTR_ANGULARDAMPINGFACTOR_REQD     = false;
  String  RIGIDBODY_ATTR_ANGULARDAMPINGFACTOR_DFLT     = "0.001";
  String  RIGIDBODY_ATTR_ANGULARVELOCITY_NAME          = "angularVelocity";
  boolean RIGIDBODY_ATTR_ANGULARVELOCITY_REQD          = false;
  String  RIGIDBODY_ATTR_ANGULARVELOCITY_DFLT          = "0 0 0";
  String  RIGIDBODY_ATTR_AUTODAMP_NAME                 = "autoDamp";
  boolean RIGIDBODY_ATTR_AUTODAMP_REQD                 = false;
  String  RIGIDBODY_ATTR_AUTODAMP_DFLT                 = "false";
  String  RIGIDBODY_ATTR_AUTODISABLE_NAME              = "autoDisable";
  boolean RIGIDBODY_ATTR_AUTODISABLE_REQD              = false;
  String  RIGIDBODY_ATTR_AUTODISABLE_DFLT              = "false";
  String  RIGIDBODY_ATTR_CENTEROFMASS_NAME             = "centerOfMass";
  boolean RIGIDBODY_ATTR_CENTEROFMASS_REQD             = false;
  String  RIGIDBODY_ATTR_CENTEROFMASS_DFLT             = "0 0 0";
  String  RIGIDBODY_ATTR_DISABLEANGULARSPEED_NAME      = "disableAngularSpeed";
  boolean RIGIDBODY_ATTR_DISABLEANGULARSPEED_REQD      = false;
  String  RIGIDBODY_ATTR_DISABLEANGULARSPEED_DFLT      = "0";
  String  RIGIDBODY_ATTR_DISABLELINEARSPEED_NAME       = "disableLinearSpeed";
  boolean RIGIDBODY_ATTR_DISABLELINEARSPEED_REQD       = false;
  String  RIGIDBODY_ATTR_DISABLELINEARSPEED_DFLT       = "0";
  String  RIGIDBODY_ATTR_DISABLETIME_NAME              = "disableTime";
  boolean RIGIDBODY_ATTR_DISABLETIME_REQD              = false;
  String  RIGIDBODY_ATTR_DISABLETIME_DFLT              = "0";
  String  RIGIDBODY_ATTR_ENABLED_NAME                  = "enabled";
  boolean RIGIDBODY_ATTR_ENABLED_REQD                  = false;
  String  RIGIDBODY_ATTR_ENABLED_DFLT                  = "true";
  String  RIGIDBODY_ATTR_FINITEROTATIONAXIS_NAME       = "finiteRotationAxis";
  boolean RIGIDBODY_ATTR_FINITEROTATIONAXIS_REQD       = false;
  String  RIGIDBODY_ATTR_FINITEROTATIONAXIS_DFLT       = "0 1 0";
  String  RIGIDBODY_ATTR_FIXED_NAME                    = "fixed";
  boolean RIGIDBODY_ATTR_FIXED_REQD                    = false;
  String  RIGIDBODY_ATTR_FIXED_DFLT                    = "false";
  String  RIGIDBODY_ATTR_FORCES_NAME                   = "forces";
  boolean RIGIDBODY_ATTR_FORCES_REQD                   = false;
  String  RIGIDBODY_ATTR_FORCES_DFLT                   = "";
  String  RIGIDBODY_ATTR_INERTIA_NAME                  = "inertia";
  boolean RIGIDBODY_ATTR_INERTIA_REQD                  = false;
  String  RIGIDBODY_ATTR_INERTIA_DFLT                  = "1 0 0 0 1 0 0 0 1";
  String  RIGIDBODY_ATTR_LINEARDAMPINGFACTOR_NAME      = "linearDampingFactor";
  boolean RIGIDBODY_ATTR_LINEARDAMPINGFACTOR_REQD      = false;
  String  RIGIDBODY_ATTR_LINEARDAMPINGFACTOR_DFLT      = "0.001";
  String  RIGIDBODY_ATTR_LINEARVELOCITY_NAME           = "linearVelocity";
  boolean RIGIDBODY_ATTR_LINEARVELOCITY_REQD           = false;
  String  RIGIDBODY_ATTR_LINEARVELOCITY_DFLT           = "0 0 0";
  String  RIGIDBODY_ATTR_MASS_NAME                     = "mass";
  boolean RIGIDBODY_ATTR_MASS_REQD                     = false;
  String  RIGIDBODY_ATTR_MASS_DFLT                     = "1";
  String  RIGIDBODY_ATTR_ORIENTATION_NAME              = "orientation";
  boolean RIGIDBODY_ATTR_ORIENTATION_REQD              = false;
  String  RIGIDBODY_ATTR_ORIENTATION_DFLT              = "0 0 1 0";
  String  RIGIDBODY_ATTR_POSITION_NAME                 = "position";
  boolean RIGIDBODY_ATTR_POSITION_REQD                 = false;
  String  RIGIDBODY_ATTR_POSITION_DFLT                 = "0 0 0";
  String  RIGIDBODY_ATTR_TORQUES_NAME                  = "torques";
  boolean RIGIDBODY_ATTR_TORQUES_REQD                  = false;
  String  RIGIDBODY_ATTR_TORQUES_DFLT                  = "";
  String  RIGIDBODY_ATTR_USEFINITEROTATION_NAME        = "useFiniteRotation";
  boolean RIGIDBODY_ATTR_USEFINITEROTATION_REQD        = false;
  String  RIGIDBODY_ATTR_USEFINITEROTATION_DFLT        = "false";
  String  RIGIDBODY_ATTR_USEGLOBALGRAVITY_NAME         = "useGlobalGravity";
  boolean RIGIDBODY_ATTR_USEGLOBALGRAVITY_REQD         = false;
  String  RIGIDBODY_ATTR_USEGLOBALGRAVITY_DFLT         = "true";

  String  RIGIDBODYCOLLECTION_ELNAME                             = "RigidBodyCollection";
  String  RIGIDBODYCOLLECTION_ATTR_AUTODISABLE_NAME              = "autoDisable";
  boolean RIGIDBODYCOLLECTION_ATTR_AUTODISABLE_REQD              = false;
  String  RIGIDBODYCOLLECTION_ATTR_AUTODISABLE_DFLT              = "false";
  String  RIGIDBODYCOLLECTION_ATTR_CONSTANTFORCEMIX_NAME         = "constantForceMix";
  boolean RIGIDBODYCOLLECTION_ATTR_CONSTANTFORCEMIX_REQD         = false;
  String  RIGIDBODYCOLLECTION_ATTR_CONSTANTFORCEMIX_DFLT         = "0.0001";
  String  RIGIDBODYCOLLECTION_ATTR_CONTACTSURFACETHICKNESS_NAME  = "contactSurfaceThickness";
  boolean RIGIDBODYCOLLECTION_ATTR_CONTACTSURFACETHICKNESS_REQD  = false;
  String  RIGIDBODYCOLLECTION_ATTR_CONTACTSURFACETHICKNESS_DFLT  = "0";
  String  RIGIDBODYCOLLECTION_ATTR_DISABLEANGULARSPEED_NAME      = "disableAngularSpeed";
  boolean RIGIDBODYCOLLECTION_ATTR_DISABLEANGULARSPEED_REQD      = false;
  String  RIGIDBODYCOLLECTION_ATTR_DISABLEANGULARSPEED_DFLT      = "0";
  String  RIGIDBODYCOLLECTION_ATTR_DISABLELINEARSPEED_NAME       = "disableLinearSpeed";
  boolean RIGIDBODYCOLLECTION_ATTR_DISABLELINEARSPEED_REQD       = false;
  String  RIGIDBODYCOLLECTION_ATTR_DISABLELINEARSPEED_DFLT       = "0";
  String  RIGIDBODYCOLLECTION_ATTR_DISABLETIME_NAME              = "disableTime";
  boolean RIGIDBODYCOLLECTION_ATTR_DISABLETIME_REQD              = false;
  String  RIGIDBODYCOLLECTION_ATTR_DISABLETIME_DFLT              = "0";
  String  RIGIDBODYCOLLECTION_ATTR_ENABLED_NAME                  = "enabled";
  boolean RIGIDBODYCOLLECTION_ATTR_ENABLED_REQD                  = false;
  String  RIGIDBODYCOLLECTION_ATTR_ENABLED_DFLT                  = "true";
  String  RIGIDBODYCOLLECTION_ATTR_ERRORCORRECTION_NAME          = "errorCorrection";
  boolean RIGIDBODYCOLLECTION_ATTR_ERRORCORRECTION_REQD          = false;
  String  RIGIDBODYCOLLECTION_ATTR_ERRORCORRECTION_DFLT          = "0.8";
  String  RIGIDBODYCOLLECTION_ATTR_GRAVITY_NAME                  = "gravity";
  boolean RIGIDBODYCOLLECTION_ATTR_GRAVITY_REQD                  = false;
  String  RIGIDBODYCOLLECTION_ATTR_GRAVITY_DFLT                  = "0 -9.8 0";
  String  RIGIDBODYCOLLECTION_ATTR_ITERATIONS_NAME               = "iterations";
  boolean RIGIDBODYCOLLECTION_ATTR_ITERATIONS_REQD               = false;
  String  RIGIDBODYCOLLECTION_ATTR_ITERATIONS_DFLT               = "10";
  String  RIGIDBODYCOLLECTION_ATTR_MAXCORRECTIONSPEED_NAME       = "maxCorrectionSpeed";
  boolean RIGIDBODYCOLLECTION_ATTR_MAXCORRECTIONSPEED_REQD       = false;
  String  RIGIDBODYCOLLECTION_ATTR_MAXCORRECTIONSPEED_DFLT       = "-1";
  String  RIGIDBODYCOLLECTION_ATTR_PREFERACCURACY_NAME           = "preferAccuracy";
  boolean RIGIDBODYCOLLECTION_ATTR_PREFERACCURACY_REQD           = false;
  String  RIGIDBODYCOLLECTION_ATTR_PREFERACCURACY_DFLT           = "false";

  String  SLIDERJOINT_ELNAME                   = "SliderJoint";
  String  SLIDERJOINT_ATTR_AXIS_NAME           = "axis";
  boolean SLIDERJOINT_ATTR_AXIS_REQD           = false;
  String  SLIDERJOINT_ATTR_AXIS_DFLT           = "0 0 0";
  String  SLIDERJOINT_ATTR_FORCEOUTPUT_NAME    = "forceOutput";
  boolean SLIDERJOINT_ATTR_FORCEOUTPUT_REQD    = false;
  String  SLIDERJOINT_ATTR_FORCEOUTPUT_DFLT    = "NONE";
  String  SLIDERJOINT_ATTR_MAXSEPARATION_NAME        = "maxSeparation";
  boolean SLIDERJOINT_ATTR_MAXSEPARATION_REQD        = false;
  String  SLIDERJOINT_ATTR_MAXSEPARATION_DFLT        = "1";
  String  SLIDERJOINT_ATTR_MINSEPARATION_NAME        = "minSeparation";
  boolean SLIDERJOINT_ATTR_MINSEPARATION_REQD        = false;
  String  SLIDERJOINT_ATTR_MINSEPARATION_DFLT        = "0";
  String  SLIDERJOINT_ATTR_SLIDERFORCE_NAME          = "sliderForce";
  boolean SLIDERJOINT_ATTR_SLIDERFORCE_REQD          = false;
  String  SLIDERJOINT_ATTR_SLIDERFORCE_DFLT          = "0";
  String  SLIDERJOINT_ATTR_STOPBOUNCE_NAME           = "stopBounce";
  boolean SLIDERJOINT_ATTR_STOPBOUNCE_REQD           = false;
  String  SLIDERJOINT_ATTR_STOPBOUNCE_DFLT           = "0";
  String  SLIDERJOINT_ATTR_STOPERRORCORRECTION_NAME  = "stopErrorCorrection";
  boolean SLIDERJOINT_ATTR_STOPERRORCORRECTION_REQD  = false;
  String  SLIDERJOINT_ATTR_STOPERRORCORRECTION_DFLT  = "1";

  String  UNIVERSALJOINT_ELNAME                         = "UniversalJoint";
  String  UNIVERSALJOINT_ATTR_ANCHORPOINT_NAME          = "anchorPoint";
  boolean UNIVERSALJOINT_ATTR_ANCHORPOINT_REQD          = false;
  String  UNIVERSALJOINT_ATTR_ANCHORPOINT_DFLT          = "0 0 0";
  String  UNIVERSALJOINT_ATTR_AXIS1_NAME                = "axis1";
  boolean UNIVERSALJOINT_ATTR_AXIS1_REQD                = false;
  String  UNIVERSALJOINT_ATTR_AXIS1_DFLT                = "0 0 0";
  String  UNIVERSALJOINT_ATTR_AXIS2_NAME                = "axis2";
  boolean UNIVERSALJOINT_ATTR_AXIS2_REQD                = false;
  String  UNIVERSALJOINT_ATTR_AXIS2_DFLT                = "0 0 0";
  String  UNIVERSALJOINT_ATTR_FORCEOUTPUT_NAME          = "forceOutput";
  boolean UNIVERSALJOINT_ATTR_FORCEOUTPUT_REQD          = false;
  String  UNIVERSALJOINT_ATTR_FORCEOUTPUT_DFLT          = "NONE";
  String  UNIVERSALJOINT_ATTR_STOP1BOUNCE_NAME          = "stop1Bounce";
  boolean UNIVERSALJOINT_ATTR_STOP1BOUNCE_REQD          = false;
  String  UNIVERSALJOINT_ATTR_STOP1BOUNCE_DFLT          = "0";
  String  UNIVERSALJOINT_ATTR_STOP2BOUNCE_NAME          = "stop2Bounce";
  boolean UNIVERSALJOINT_ATTR_STOP2BOUNCE_REQD          = false;
  String  UNIVERSALJOINT_ATTR_STOP2BOUNCE_DFLT          = "0";
  String  UNIVERSALJOINT_ATTR_STOP1ERRORCORRECTION_NAME = "stop1ErrorCorrection";
  boolean UNIVERSALJOINT_ATTR_STOP1ERRORCORRECTION_REQD = false;
  String  UNIVERSALJOINT_ATTR_STOP1ERRORCORRECTION_DFLT = "0.8";
  String  UNIVERSALJOINT_ATTR_STOP2ERRORCORRECTION_NAME = "stop2ErrorCorrection";
  boolean UNIVERSALJOINT_ATTR_STOP2ERRORCORRECTION_REQD = false;
  String  UNIVERSALJOINT_ATTR_STOP2ERRORCORRECTION_DFLT = "0.8";

  String  SINGLEAXISHINGEJOINT_ELNAME                             = "SingleAxisHingeJoint";
  String  SINGLEAXISHINGEJOINT_ATTR_ANCHORPOINT_NAME              = "anchorPoint";
  boolean SINGLEAXISHINGEJOINT_ATTR_ANCHORPOINT_REQD              = false;
  String  SINGLEAXISHINGEJOINT_ATTR_ANCHORPOINT_DFLT              = "0 0 0";
  String  SINGLEAXISHINGEJOINT_ATTR_AXIS_NAME                     = "axis";
  boolean SINGLEAXISHINGEJOINT_ATTR_AXIS_REQD                     = false;
  String  SINGLEAXISHINGEJOINT_ATTR_AXIS_DFLT                     = "0 0 0";
  String  SINGLEAXISHINGEJOINT_ATTR_FORCEOUTPUT_NAME              = "forceOutput";
  boolean SINGLEAXISHINGEJOINT_ATTR_FORCEOUTPUT_REQD              = false;
  String  SINGLEAXISHINGEJOINT_ATTR_FORCEOUTPUT_DFLT              = "NONE";
  String  SINGLEAXISHINGEJOINT_ATTR_MINANGLE_NAME                 = "minAngle";
  boolean SINGLEAXISHINGEJOINT_ATTR_MINANGLE_REQD                 = false;
  String  SINGLEAXISHINGEJOINT_ATTR_MINANGLE_DFLT                 = "-3.1416";
  String  SINGLEAXISHINGEJOINT_ATTR_MAXANGLE_NAME                 = "maxAngle";
  boolean SINGLEAXISHINGEJOINT_ATTR_MAXANGLE_REQD                 = false;
  String  SINGLEAXISHINGEJOINT_ATTR_MAXANGLE_DFLT                 = "3.1416";
  String  SINGLEAXISHINGEJOINT_ATTR_STOPBOUNCE_NAME               = "stopBounce";
  boolean SINGLEAXISHINGEJOINT_ATTR_STOPBOUNCE_REQD               = false;
  String  SINGLEAXISHINGEJOINT_ATTR_STOPBOUNCE_DFLT               = "0";
  String  SINGLEAXISHINGEJOINT_ATTR_STOPERRORCORRECTION_NAME      = "stopErrorCorrection";
  boolean SINGLEAXISHINGEJOINT_ATTR_STOPERRORCORRECTION_REQD      = false;
  String  SINGLEAXISHINGEJOINT_ATTR_STOPERRORCORRECTION_DFLT      = "0.8";

  String  DOUBLEAXISHINGEJOINT_ELNAME                             = "DoubleAxisHingeJoint";
  String  DOUBLEAXISHINGEJOINT_ATTR_ANCHORPOINT_NAME              = "anchorPoint";
  boolean DOUBLEAXISHINGEJOINT_ATTR_ANCHORPOINT_REQD              = false;
  String  DOUBLEAXISHINGEJOINT_ATTR_ANCHORPOINT_DFLT              = "0 0 0";
  String  DOUBLEAXISHINGEJOINT_ATTR_AXIS1_NAME                    = "axis1";
  boolean DOUBLEAXISHINGEJOINT_ATTR_AXIS1_REQD                    = false;
  String  DOUBLEAXISHINGEJOINT_ATTR_AXIS1_DFLT                    = "0 0 0";
  String  DOUBLEAXISHINGEJOINT_ATTR_AXIS2_NAME                    = "axis2";
  boolean DOUBLEAXISHINGEJOINT_ATTR_AXIS2_REQD                    = false;
  String  DOUBLEAXISHINGEJOINT_ATTR_AXIS2_DFLT                    = "0 0 0";
  String  DOUBLEAXISHINGEJOINT_ATTR_DESIREDANGULARVELOCITY1_NAME  = "desiredAngularVelocity1";
  boolean DOUBLEAXISHINGEJOINT_ATTR_DESIREDANGULARVELOCITY1_REQD  = false;
  String  DOUBLEAXISHINGEJOINT_ATTR_DESIREDANGULARVELOCITY1_DFLT  = "0";
  String  DOUBLEAXISHINGEJOINT_ATTR_DESIREDANGULARVELOCITY2_NAME  = "desiredAngularVelocity2";
  boolean DOUBLEAXISHINGEJOINT_ATTR_DESIREDANGULARVELOCITY2_REQD  = false;
  String  DOUBLEAXISHINGEJOINT_ATTR_DESIREDANGULARVELOCITY2_DFLT  = "0";
  String  DOUBLEAXISHINGEJOINT_ATTR_FORCEOUTPUT_NAME              = "forceOutput";
  boolean DOUBLEAXISHINGEJOINT_ATTR_FORCEOUTPUT_REQD              = false;
  String  DOUBLEAXISHINGEJOINT_ATTR_FORCEOUTPUT_DFLT              = "NONE";
  String  DOUBLEAXISHINGEJOINT_ATTR_MINANGLE1_NAME                = "minAngle1";
  boolean DOUBLEAXISHINGEJOINT_ATTR_MINANGLE1_REQD                = false;
  String  DOUBLEAXISHINGEJOINT_ATTR_MINANGLE1_DFLT                = "-3.1416";
  String  DOUBLEAXISHINGEJOINT_ATTR_MAXANGLE1_NAME                = "maxAngle1";
  boolean DOUBLEAXISHINGEJOINT_ATTR_MAXANGLE1_REQD                = false;
  String  DOUBLEAXISHINGEJOINT_ATTR_MAXANGLE1_DFLT                = "3.1416";
  String  DOUBLEAXISHINGEJOINT_ATTR_MAXTORQUE1_NAME               = "maxTorque1";
  boolean DOUBLEAXISHINGEJOINT_ATTR_MAXTORQUE1_REQD               = false;
  String  DOUBLEAXISHINGEJOINT_ATTR_MAXTORQUE1_DFLT               = "0";
  String  DOUBLEAXISHINGEJOINT_ATTR_MAXTORQUE2_NAME               = "maxTorque2";
  boolean DOUBLEAXISHINGEJOINT_ATTR_MAXTORQUE2_REQD               = false;
  String  DOUBLEAXISHINGEJOINT_ATTR_MAXTORQUE2_DFLT               = "0";
  String  DOUBLEAXISHINGEJOINT_ATTR_STOP1BOUNCE_NAME              = "stop1Bounce";
  boolean DOUBLEAXISHINGEJOINT_ATTR_STOP1BOUNCE_REQD              = false;
  String  DOUBLEAXISHINGEJOINT_ATTR_STOP1BOUNCE_DFLT              = "0";
  String  DOUBLEAXISHINGEJOINT_ATTR_STOP1CONSTANTFORCEMIX_NAME    = "stop1ConstantForceMix";
  boolean DOUBLEAXISHINGEJOINT_ATTR_STOP1CONSTANTFORCEMIX_REQD    = false;
  String  DOUBLEAXISHINGEJOINT_ATTR_STOP1CONSTANTFORCEMIX_DFLT    = "0.001";
  String  DOUBLEAXISHINGEJOINT_ATTR_STOP1ERRORCORRECTION_NAME     = "stop1ErrorCorrection";
  boolean DOUBLEAXISHINGEJOINT_ATTR_STOP1ERRORCORRECTION_REQD     = false;
  String  DOUBLEAXISHINGEJOINT_ATTR_STOP1ERRORCORRECTION_DFLT     = "0.8";
  String  DOUBLEAXISHINGEJOINT_ATTR_SUSPENSIONERRORCORRECTION_NAME= "suspensionErrorCorrection";
  boolean DOUBLEAXISHINGEJOINT_ATTR_SUSPENSIONERRORCORRECTION_REQD= false;
  String  DOUBLEAXISHINGEJOINT_ATTR_SUSPENSIONERRORCORRECTION_DFLT= "0.8";
  String  DOUBLEAXISHINGEJOINT_ATTR_SUSPENSIONFORCE_NAME          = "suspensionForce";
  boolean DOUBLEAXISHINGEJOINT_ATTR_SUSPENSIONFORCE_REQD          = false;
  String  DOUBLEAXISHINGEJOINT_ATTR_SUSPENSIONFORCE_DFLT          = "0";

  // Shader nodes

  // Element ComposedShader
  String   COMPOSEDSHADER_ELNAME = "ComposedShader";
  String   COMPOSEDSHADER_ATTR_URL_NAME          = "url";
  boolean  COMPOSEDSHADER_ATTR_URL_REQD          = false;
  String   COMPOSEDSHADER_ATTR_URL_DFLT          = "";
  String   COMPOSEDSHADER_ATTR_LANGUAGE_NAME     = "language";
  boolean  COMPOSEDSHADER_ATTR_LANGUAGE_REQD     = false;
  String   COMPOSEDSHADER_ATTR_LANGUAGE_DFLT     = "";
  // suggested values:
  String[] SHADER_ATTR_LANGUAGE_CHOICES = {
    "", //1
    "Cg", //2
    "GLSL",//3
    "HLSL", //4
  };
  // Element PackagedShader
  String   PACKAGEDSHADER_ELNAME = "PackagedShader";
  String   PACKAGEDSHADER_ATTR_URL_NAME          = "url";
  boolean  PACKAGEDSHADER_ATTR_URL_REQD          = false;
  String   PACKAGEDSHADER_ATTR_URL_DFLT          = "";
  String   PACKAGEDSHADER_ATTR_LANGUAGE_NAME     = "language";
  boolean  PACKAGEDSHADER_ATTR_LANGUAGE_REQD     = false;
  String   PACKAGEDSHADER_ATTR_LANGUAGE_DFLT     = "";

  // Element FloatVertexAttribute
  String   FLOATVERTEXATTRIBUTE_ELNAME = "FloatVertexAttribute";
  String   FLOATVERTEXATTRIBUTE_ATTR_NAME_NAME          = "name";
  boolean  FLOATVERTEXATTRIBUTE_ATTR_NAME_REQD          = false;
  String   FLOATVERTEXATTRIBUTE_ATTR_NAME_DFLT          = "";
  String   FLOATVERTEXATTRIBUTE_ATTR_NUMCOMPONENTS_NAME = "numComponents";
  boolean  FLOATVERTEXATTRIBUTE_ATTR_NUMCOMPONENTS_REQD = false;
  String   FLOATVERTEXATTRIBUTE_ATTR_NUMCOMPONENTS_DFLT = "4";
  String   FLOATVERTEXATTRIBUTE_ATTR_VALUE_NAME         = "value";
  boolean  FLOATVERTEXATTRIBUTE_ATTR_VALUE_REQD         = false;
  String   FLOATVERTEXATTRIBUTE_ATTR_VALUE_DFLT         = "";

  // Element Matrix3VertexAttribute
  String   MATRIX3VERTEXATTRIBUTE_ELNAME = "Matrix3VertexAttribute";
  String   MATRIX3VERTEXATTRIBUTE_ATTR_NAME_NAME      = "name";
  boolean  MATRIX3VERTEXATTRIBUTE_ATTR_NAME_REQD      = false;
  String   MATRIX3VERTEXATTRIBUTE_ATTR_NAME_DFLT      = "";
  String   MATRIX3VERTEXATTRIBUTE_ATTR_VALUE_NAME     = "value";
  boolean  MATRIX3VERTEXATTRIBUTE_ATTR_VALUE_REQD     = false;
  String   MATRIX3VERTEXATTRIBUTE_ATTR_VALUE_DFLT     = "";

  // Element Matrix4VertexAttribute
  String   MATRIX4VERTEXATTRIBUTE_ELNAME = "Matrix4VertexAttribute";
  String   MATRIX4VERTEXATTRIBUTE_ATTR_NAME_NAME      = "name";
  boolean  MATRIX4VERTEXATTRIBUTE_ATTR_NAME_REQD      = false;
  String   MATRIX4VERTEXATTRIBUTE_ATTR_NAME_DFLT      = "";
  String   MATRIX4VERTEXATTRIBUTE_ATTR_VALUE_NAME     = "value";
  boolean  MATRIX4VERTEXATTRIBUTE_ATTR_VALUE_REQD     = false;
  String   MATRIX4VERTEXATTRIBUTE_ATTR_VALUE_DFLT     = "";

  // Element ProgramShader
  String   PROGRAMSHADER_ELNAME = "ProgramShader";
  String   PROGRAMSHADER_ATTR_LANGUAGE_NAME     = "language";
  boolean  PROGRAMSHADER_ATTR_LANGUAGE_REQD     = false;
  String   PROGRAMSHADER_ATTR_LANGUAGE_DFLT     = "";

  // Element ShaderPart
  String   SHADERPART_ELNAME = "ShaderPart";
  String   SHADERPART_ATTR_URL_NAME          = "url";
  boolean  SHADERPART_ATTR_URL_REQD          = false;
  String   SHADERPART_ATTR_URL_DFLT          = "";
  String   SHADERPART_ATTR_TYPE_NAME         = "type";
  boolean  SHADERPART_ATTR_TYPE_REQD         = false;
  String   SHADERPART_ATTR_TYPE_DFLT         = "VERTEX";
  String[] SHADERPART_ATTR_TYPE_CHOICES = {
    "VERTEX",//1
    "FRAGMENT", //2
  };
  // Element ShaderProgram
  String   SHADERPROGRAM_ELNAME = "ShaderProgram";
  String   SHADERPROGRAM_ATTR_URL_NAME          = "url";
  boolean  SHADERPROGRAM_ATTR_URL_REQD          = false;
  String   SHADERPROGRAM_ATTR_URL_DFLT          = "";
  String   SHADERPROGRAM_ATTR_TYPE_NAME         = "type";
  boolean  SHADERPROGRAM_ATTR_TYPE_REQD         = false;
  String   SHADERPROGRAM_ATTR_TYPE_DFLT         = "VERTEX";
  String[] SHADERPROGRAM_ATTR_TYPE_CHOICES = {
    "VERTEX",//1
    "FRAGMENT", //2
  };

  // Followers:  dampers and chasers

  // Element ColorChaser
  String   COLORCHASER_ELNAME = "ColorChaser";
  String   COLORCHASER_ATTR_DURATION_NAME			= "duration";
  boolean  COLORCHASER_ATTR_DURATION_REQD			= false;
  String   COLORCHASER_ATTR_DURATION_DFLT			= "1";
  String   COLORCHASER_ATTR_INITIALVALUE_NAME       = "initialValue";
  boolean  COLORCHASER_ATTR_INITIALVALUE_REQD       = false;
  String   COLORCHASER_ATTR_INITIALVALUE_DFLT       = "0.8 0.8 0.8";
  String   COLORCHASER_ATTR_INITIALDESTINATION_NAME = "initialDestination";
  boolean  COLORCHASER_ATTR_INITIALDESTINATION_REQD = false;
  String   COLORCHASER_ATTR_INITIALDESTINATION_DFLT = "0.8 0.8 0.8";

  // Element ColorDamper
  String   COLORDAMPER_ELNAME = "ColorDamper";
  String   COLORDAMPER_ATTR_TAU_NAME           = "tau";
  boolean  COLORDAMPER_ATTR_TAU_REQD           = false;
  String   COLORDAMPER_ATTR_TAU_DFLT           = "0.3";
  String   COLORDAMPER_ATTR_TOLERANCE_NAME     = "tolerance";
  boolean  COLORDAMPER_ATTR_TOLERANCE_REQD     = false;
  String   COLORDAMPER_ATTR_TOLERANCE_DFLT     = "-1";
  String   COLORDAMPER_ATTR_ORDER_NAME         = "order";
  boolean  COLORDAMPER_ATTR_ORDER_REQD         = false;
  String   COLORDAMPER_ATTR_ORDER_DFLT         = "3";
  String   COLORDAMPER_ATTR_INITIALVALUE_NAME       = "initialValue";
  boolean  COLORDAMPER_ATTR_INITIALVALUE_REQD       = false;
  String   COLORDAMPER_ATTR_INITIALVALUE_DFLT       = "0.8 0.8 0.8";
  String   COLORDAMPER_ATTR_INITIALDESTINATION_NAME = "initialDestination";
  boolean  COLORDAMPER_ATTR_INITIALDESTINATION_REQD = false;
  String   COLORDAMPER_ATTR_INITIALDESTINATION_DFLT = "0.8 0.8 0.8";

  // Element CoordinateChaser
  String   COORDINATECHASER_ELNAME = "CoordinateChaser";
  String   COORDINATECHASER_ATTR_DURATION_NAME			 = "duration";
  boolean  COORDINATECHASER_ATTR_DURATION_REQD			 = false;
  String   COORDINATECHASER_ATTR_DURATION_DFLT			 = "1";
  String   COORDINATECHASER_ATTR_INITIALVALUE_NAME       = "initialValue";
  boolean  COORDINATECHASER_ATTR_INITIALVALUE_REQD       = false;
  String   COORDINATECHASER_ATTR_INITIALVALUE_DFLT       = "0.0 0.0 0.0";
  String   COORDINATECHASER_ATTR_INITIALDESTINATION_NAME = "initialDestination";
  boolean  COORDINATECHASER_ATTR_INITIALDESTINATION_REQD = false;
  String   COORDINATECHASER_ATTR_INITIALDESTINATION_DFLT = "0.0 0.0 0.0";

  // Element CoordinateDamper
  String   COORDINATEDAMPER_ELNAME = "CoordinateDamper";
  String   COORDINATEDAMPER_ATTR_TAU_NAME           = "tau";
  boolean  COORDINATEDAMPER_ATTR_TAU_REQD           = false;
  String   COORDINATEDAMPER_ATTR_TAU_DFLT           = "0.3";
  String   COORDINATEDAMPER_ATTR_TOLERANCE_NAME     = "tolerance";
  boolean  COORDINATEDAMPER_ATTR_TOLERANCE_REQD     = false;
  String   COORDINATEDAMPER_ATTR_TOLERANCE_DFLT     = "-1";
  String   COORDINATEDAMPER_ATTR_ORDER_NAME         = "order";
  boolean  COORDINATEDAMPER_ATTR_ORDER_REQD         = false;
  String   COORDINATEDAMPER_ATTR_ORDER_DFLT         = "3";
  String   COORDINATEDAMPER_ATTR_INITIALVALUE_NAME       = "initialValue";
  boolean  COORDINATEDAMPER_ATTR_INITIALVALUE_REQD       = false;
  String   COORDINATEDAMPER_ATTR_INITIALVALUE_DFLT       = "0 0 0";
  String   COORDINATEDAMPER_ATTR_INITIALDESTINATION_NAME = "initialDestination";
  boolean  COORDINATEDAMPER_ATTR_INITIALDESTINATION_REQD = false;
  String   COORDINATEDAMPER_ATTR_INITIALDESTINATION_DFLT = "0 0 0";

  // Element OrientationDamper
  String   ORIENTATIONDAMPER_ELNAME = "OrientationDamper";
  String   ORIENTATIONDAMPER_ATTR_TAU_NAME           = "tau";
  boolean  ORIENTATIONDAMPER_ATTR_TAU_REQD           = false;
  String   ORIENTATIONDAMPER_ATTR_TAU_DFLT           = "0.3";
  String   ORIENTATIONDAMPER_ATTR_TOLERANCE_NAME     = "tolerance";
  boolean  ORIENTATIONDAMPER_ATTR_TOLERANCE_REQD     = false;
  String   ORIENTATIONDAMPER_ATTR_TOLERANCE_DFLT     = "-1";
  String   ORIENTATIONDAMPER_ATTR_ORDER_NAME         = "order";
  boolean  ORIENTATIONDAMPER_ATTR_ORDER_REQD         = false;
  String   ORIENTATIONDAMPER_ATTR_ORDER_DFLT         = "3";
  String   ORIENTATIONDAMPER_ATTR_INITIALVALUE_NAME       = "initialValue";
  boolean  ORIENTATIONDAMPER_ATTR_INITIALVALUE_REQD       = false;
  String   ORIENTATIONDAMPER_ATTR_INITIALVALUE_DFLT       = "0 1 0 0";
  String   ORIENTATIONDAMPER_ATTR_INITIALDESTINATION_NAME = "initialDestination";
  boolean  ORIENTATIONDAMPER_ATTR_INITIALDESTINATION_REQD = false;
  String   ORIENTATIONDAMPER_ATTR_INITIALDESTINATION_DFLT = "0 1 0 0";

  // Element PositionDamper
  String   POSITIONDAMPER_ELNAME = "PositionDamper";
  String   POSITIONDAMPER_ATTR_TAU_NAME           = "tau";
  boolean  POSITIONDAMPER_ATTR_TAU_REQD           = false;
  String   POSITIONDAMPER_ATTR_TAU_DFLT           = "0.3";
  String   POSITIONDAMPER_ATTR_TOLERANCE_NAME     = "tolerance";
  boolean  POSITIONDAMPER_ATTR_TOLERANCE_REQD     = false;
  String   POSITIONDAMPER_ATTR_TOLERANCE_DFLT     = "-1";
  String   POSITIONDAMPER_ATTR_ORDER_NAME         = "order";
  boolean  POSITIONDAMPER_ATTR_ORDER_REQD         = false;
  String   POSITIONDAMPER_ATTR_ORDER_DFLT         = "3";
  String   POSITIONDAMPER_ATTR_INITIALVALUE_NAME       = "initialValue";
  boolean  POSITIONDAMPER_ATTR_INITIALVALUE_REQD       = false;
  String   POSITIONDAMPER_ATTR_INITIALVALUE_DFLT       = "0 0 0";
  String   POSITIONDAMPER_ATTR_INITIALDESTINATION_NAME = "initialDestination";
  boolean  POSITIONDAMPER_ATTR_INITIALDESTINATION_REQD = false;
  String   POSITIONDAMPER_ATTR_INITIALDESTINATION_DFLT = "0 0 0";

  // Element PositionDamper2D
  String   POSITIONDAMPER2D_ELNAME = "PositionDamper2D";
  String   POSITIONDAMPER2D_ATTR_TAU_NAME           = "tau";
  boolean  POSITIONDAMPER2D_ATTR_TAU_REQD           = false;
  String   POSITIONDAMPER2D_ATTR_TAU_DFLT           = "0.3";
  String   POSITIONDAMPER2D_ATTR_TOLERANCE_NAME     = "tolerance";
  boolean  POSITIONDAMPER2D_ATTR_TOLERANCE_REQD     = false;
  String   POSITIONDAMPER2D_ATTR_TOLERANCE_DFLT     = "-1";
  String   POSITIONDAMPER2D_ATTR_ORDER_NAME         = "order";
  boolean  POSITIONDAMPER2D_ATTR_ORDER_REQD         = false;
  String   POSITIONDAMPER2D_ATTR_ORDER_DFLT         = "3";
  String   POSITIONDAMPER2D_ATTR_INITIALVALUE_NAME       = "initialValue";
  boolean  POSITIONDAMPER2D_ATTR_INITIALVALUE_REQD       = false;
  String   POSITIONDAMPER2D_ATTR_INITIALVALUE_DFLT       = "0 0";
  String   POSITIONDAMPER2D_ATTR_INITIALDESTINATION_NAME = "initialDestination";
  boolean  POSITIONDAMPER2D_ATTR_INITIALDESTINATION_REQD = false;
  String   POSITIONDAMPER2D_ATTR_INITIALDESTINATION_DFLT = "0 0";

  // Element TexCoordChaser2D
  String   TEXCOORDCHASER2D_ELNAME = "TexCoordChaser2D";
  String   TEXCOORDCHASER2D_ATTR_DURATION_NAME			 = "duration";
  boolean  TEXCOORDCHASER2D_ATTR_DURATION_REQD			 = false;
  String   TEXCOORDCHASER2D_ATTR_DURATION_DFLT			 = "1";
  String   TEXCOORDCHASER2D_ATTR_INITIALVALUE_NAME       = "initialValue";
  boolean  TEXCOORDCHASER2D_ATTR_INITIALVALUE_REQD       = false;
  String   TEXCOORDCHASER2D_ATTR_INITIALVALUE_DFLT       = ""; // empty array
  String   TEXCOORDCHASER2D_ATTR_INITIALDESTINATION_NAME = "initialDestination";
  boolean  TEXCOORDCHASER2D_ATTR_INITIALDESTINATION_REQD = false;
  String   TEXCOORDCHASER2D_ATTR_INITIALDESTINATION_DFLT = ""; // empty array

  // Element TexCoordDamper2D
  String   TEXCOORDDAMPER2D_ELNAME = "TexCoordDamper2D";
  String   TEXCOORDDAMPER2D_ATTR_TAU_NAME           = "tau";
  boolean  TEXCOORDDAMPER2D_ATTR_TAU_REQD           = false;
  String   TEXCOORDDAMPER2D_ATTR_TAU_DFLT           = "0.3";
  String   TEXCOORDDAMPER2D_ATTR_TOLERANCE_NAME     = "tolerance";
  boolean  TEXCOORDDAMPER2D_ATTR_TOLERANCE_REQD     = false;
  String   TEXCOORDDAMPER2D_ATTR_TOLERANCE_DFLT     = "-1";
  String   TEXCOORDDAMPER2D_ATTR_ORDER_NAME         = "order";
  boolean  TEXCOORDDAMPER2D_ATTR_ORDER_REQD         = false;
  String   TEXCOORDDAMPER2D_ATTR_ORDER_DFLT         = "3";
  String   TEXCOORDDAMPER2D_ATTR_INITIALVALUE_NAME       = "initialValue";
  boolean  TEXCOORDDAMPER2D_ATTR_INITIALVALUE_REQD       = false;
  String   TEXCOORDDAMPER2D_ATTR_INITIALVALUE_DFLT       = ""; // empty array
  String   TEXCOORDDAMPER2D_ATTR_INITIALDESTINATION_NAME = "initialDestination";
  boolean  TEXCOORDDAMPER2D_ATTR_INITIALDESTINATION_REQD = false;
  String   TEXCOORDDAMPER2D_ATTR_INITIALDESTINATION_DFLT = ""; // empty array

  // Element OrientationChaser
  String   ORIENTATIONCHASER_ELNAME = "OrientationChaser";
  String   ORIENTATIONCHASER_ATTR_DURATION_NAME           = "duration";
  boolean  ORIENTATIONCHASER_ATTR_DURATION_REQD           = false;
  String   ORIENTATIONCHASER_ATTR_DURATION_DFLT           = "1";
  String   ORIENTATIONCHASER_ATTR_INITIALVALUE_NAME       = "initialValue";
  boolean  ORIENTATIONCHASER_ATTR_INITIALVALUE_REQD       = false;
  String   ORIENTATIONCHASER_ATTR_INITIALVALUE_DFLT       = "0 1 0 0";
  String   ORIENTATIONCHASER_ATTR_INITIALDESTINATION_NAME = "initialDestination";
  boolean  ORIENTATIONCHASER_ATTR_INITIALDESTINATION_REQD = false;
  String   ORIENTATIONCHASER_ATTR_INITIALDESTINATION_DFLT = "0 1 0 0";

  // Element PositionChaser
  String   POSITIONCHASER_ELNAME = "PositionChaser";
  String   POSITIONCHASER_ATTR_DURATION_NAME           = "duration";
  boolean  POSITIONCHASER_ATTR_DURATION_REQD           = false;
  String   POSITIONCHASER_ATTR_DURATION_DFLT           = "1";
  String   POSITIONCHASER_ATTR_INITIALVALUE_NAME       = "initialValue";
  boolean  POSITIONCHASER_ATTR_INITIALVALUE_REQD       = false;
  String   POSITIONCHASER_ATTR_INITIALVALUE_DFLT       = "0 0 0";
  String   POSITIONCHASER_ATTR_INITIALDESTINATION_NAME = "initialDestination";
  boolean  POSITIONCHASER_ATTR_INITIALDESTINATION_REQD = false;
  String   POSITIONCHASER_ATTR_INITIALDESTINATION_DFLT = "0 0 0";

  // Element PositionChaser2D
  String   POSITIONCHASER2D_ELNAME = "PositionChaser2D";
  String   POSITIONCHASER2D_ATTR_DURATION_NAME           = "duration";
  boolean  POSITIONCHASER2D_ATTR_DURATION_REQD           = false;
  String   POSITIONCHASER2D_ATTR_DURATION_DFLT           = "1";
  String   POSITIONCHASER2D_ATTR_INITIALVALUE_NAME       = "initialValue";
  boolean  POSITIONCHASER2D_ATTR_INITIALVALUE_REQD       = false;
  String   POSITIONCHASER2D_ATTR_INITIALVALUE_DFLT       = "0 0";
  String   POSITIONCHASER2D_ATTR_INITIALDESTINATION_NAME = "initialDestination";
  boolean  POSITIONCHASER2D_ATTR_INITIALDESTINATION_REQD = false;
  String   POSITIONCHASER2D_ATTR_INITIALDESTINATION_DFLT = "0 0";

  // Element ScalarChaser
  String   SCALARCHASER_ELNAME = "ScalarChaser";
  String   SCALARCHASER_ATTR_DURATION_NAME           = "duration";
  boolean  SCALARCHASER_ATTR_DURATION_REQD           = false;
  String   SCALARCHASER_ATTR_DURATION_DFLT           = "1";
  String   SCALARCHASER_ATTR_INITIALVALUE_NAME       = "initialValue";
  boolean  SCALARCHASER_ATTR_INITIALVALUE_REQD       = false;
  String   SCALARCHASER_ATTR_INITIALVALUE_DFLT       = "0";
  String   SCALARCHASER_ATTR_INITIALDESTINATION_NAME = "initialDestination";
  boolean  SCALARCHASER_ATTR_INITIALDESTINATION_REQD = false;
  String   SCALARCHASER_ATTR_INITIALDESTINATION_DFLT = "0";

  // Element ScalarDamper
  String   SCALARDAMPER_ELNAME = "ScalarDamper";
  String   SCALARDAMPER_ATTR_TAU_NAME           = "tau";
  boolean  SCALARDAMPER_ATTR_TAU_REQD           = false;
  String   SCALARDAMPER_ATTR_TAU_DFLT           = "0.3";
  String   SCALARDAMPER_ATTR_TOLERANCE_NAME     = "tolerance";
  boolean  SCALARDAMPER_ATTR_TOLERANCE_REQD     = false;
  String   SCALARDAMPER_ATTR_TOLERANCE_DFLT     = "-1";
  String   SCALARDAMPER_ATTR_ORDER_NAME         = "order";
  boolean  SCALARDAMPER_ATTR_ORDER_REQD         = false;
  String   SCALARDAMPER_ATTR_ORDER_DFLT         = "3";
  String   SCALARDAMPER_ATTR_INITIALVALUE_NAME       = "initialValue";
  boolean  SCALARDAMPER_ATTR_INITIALVALUE_REQD       = false;
  String   SCALARDAMPER_ATTR_INITIALVALUE_DFLT       = "0";
  String   SCALARDAMPER_ATTR_INITIALDESTINATION_NAME = "initialDestination";
  boolean  SCALARDAMPER_ATTR_INITIALDESTINATION_REQD = false;
  String   SCALARDAMPER_ATTR_INITIALDESTINATION_DFLT = "0";

  // X3D v3.3 ===================================================================================

  // Element BlendedVolumeStyle
  String   BLENDEDVOLUMESTYLE_ELNAME = "BlendedVolumeStyle";
  String   BLENDEDVOLUMESTYLE_ATTR_ENABLED_NAME          = "enabled";
  boolean  BLENDEDVOLUMESTYLE_ATTR_ENABLED_REQD          = false;
  String   BLENDEDVOLUMESTYLE_ATTR_ENABLED_DFLT          = "true";
  String   BLENDEDVOLUMESTYLE_ATTR_WEIGHTCONSTANT1_NAME  = "weightConstant1";
  boolean  BLENDEDVOLUMESTYLE_ATTR_WEIGHTCONSTANT1_REQD  = false;
  String   BLENDEDVOLUMESTYLE_ATTR_WEIGHTCONSTANT1_DFLT  = "0.5";
  String   BLENDEDVOLUMESTYLE_ATTR_WEIGHTCONSTANT2_NAME  = "weightConstant2";
  boolean  BLENDEDVOLUMESTYLE_ATTR_WEIGHTCONSTANT2_REQD  = false;
  String   BLENDEDVOLUMESTYLE_ATTR_WEIGHTCONSTANT2_DFLT  = "0.5";
  String   BLENDEDVOLUMESTYLE_ATTR_WEIGHTFUNCTION1_NAME  = "weightFunction1";
  boolean  BLENDEDVOLUMESTYLE_ATTR_WEIGHTFUNCTION1_REQD  = false;
  String   BLENDEDVOLUMESTYLE_ATTR_WEIGHTFUNCTION1_DFLT  = "CONSTANT";
  String   BLENDEDVOLUMESTYLE_ATTR_WEIGHTFUNCTION2_NAME  = "weightFunction2";
  boolean  BLENDEDVOLUMESTYLE_ATTR_WEIGHTFUNCTION2_REQD  = false;
  String   BLENDEDVOLUMESTYLE_ATTR_WEIGHTFUNCTION2_DFLT  = "CONSTANT";

  String[] BLENDEDVOLUMESTYLE_ATTR_WEIGHTFUNCTION_CHOICES  = {
     "CONSTANT", "ALPHA0", "ALPHA1", "TABLE", "ONE_MINUS_ALPHA0", "ONE_MINUS_ALPHA1"
  };
  // TODO
  String[] BLENDEDVOLUMESTYLE_ATTR_WEIGHTFUNCTION_TOOLTIPS  = {
     "CONSTANT: use corresponding weightConstant, see Table 41.3 — Weight function types",
     "ALPHA0: see Table 41.3 — Weight function types",
     "ALPHA1: see Table 41.3 — Weight function types",
     "TABLE: see Table 41.3 — Weight function types",
     "ONE_MINUS_ALPHA0: see Table 41.3 — Weight function types",
     "ONE_MINUS_ALPHA1: see Table 41.3 — Weight function types"
  };

  // Element BoundaryEnhancementVolumeStyle
  String   BOUNDARYENHANCEMENTVOLUMESTYLE_ELNAME = "BoundaryEnhancementVolumeStyle";
  String   BOUNDARYENHANCEMENTVOLUMESTYLE_ATTR_ENABLED_NAME          = "enabled";
  boolean  BOUNDARYENHANCEMENTVOLUMESTYLE_ATTR_ENABLED_REQD          = false;
  String   BOUNDARYENHANCEMENTVOLUMESTYLE_ATTR_ENABLED_DFLT          = "true";
  String   BOUNDARYENHANCEMENTVOLUMESTYLE_ATTR_BOUNDARYOPACITY_NAME  = "boundaryOpacity";
  boolean  BOUNDARYENHANCEMENTVOLUMESTYLE_ATTR_BOUNDARYOPACITY_REQD  = false;
  String   BOUNDARYENHANCEMENTVOLUMESTYLE_ATTR_BOUNDARYOPACITY_DFLT  = "0";
  String   BOUNDARYENHANCEMENTVOLUMESTYLE_ATTR_OPACITYFACTOR_NAME    = "opacityFactor";
  boolean  BOUNDARYENHANCEMENTVOLUMESTYLE_ATTR_OPACITYFACTOR_REQD    = false;
  String   BOUNDARYENHANCEMENTVOLUMESTYLE_ATTR_OPACITYFACTOR_DFLT    = "1";
  String   BOUNDARYENHANCEMENTVOLUMESTYLE_ATTR_RETAINEDOPACITY_NAME  = "retainedOpacity";
  boolean  BOUNDARYENHANCEMENTVOLUMESTYLE_ATTR_RETAINEDOPACITY_REQD  = false;
  String   BOUNDARYENHANCEMENTVOLUMESTYLE_ATTR_RETAINEDOPACITY_DFLT  = "1";
  String   BOUNDARYENHANCEMENTVOLUMESTYLE_ATTR_BBOXCENTER_NAME  = "bboxCenter";
  boolean  BOUNDARYENHANCEMENTVOLUMESTYLE_ATTR_BBOXCENTER_REQD  = false;
  String   BOUNDARYENHANCEMENTVOLUMESTYLE_ATTR_BBOXCENTER_DFLT  = "0 0 0";
  String   BOUNDARYENHANCEMENTVOLUMESTYLE_ATTR_BBOXSIZE_NAME    = "bboxSize";
  boolean  BOUNDARYENHANCEMENTVOLUMESTYLE_ATTR_BBOXSIZE_REQD    = false;
  String   BOUNDARYENHANCEMENTVOLUMESTYLE_ATTR_BBOXSIZE_DFLT    = "-1 -1 -1";

  // CartoonVolumeStyle
  String   CARTOONVOLUMESTYLE_ELNAME = "CartoonVolumeStyle";
  String   CARTOONVOLUMESTYLE_ATTR_ENABLED_NAME          = "enabled";
  boolean  CARTOONVOLUMESTYLE_ATTR_ENABLED_REQD          = false;
  String   CARTOONVOLUMESTYLE_ATTR_ENABLED_DFLT          = "true";
  String   CARTOONVOLUMESTYLE_ATTR_COLORSTEPS_NAME       = "colorSteps";
  boolean  CARTOONVOLUMESTYLE_ATTR_COLORSTEPS_REQD       = false;
  String   CARTOONVOLUMESTYLE_ATTR_COLORSTEPS_DFLT       = "4";
  String   CARTOONVOLUMESTYLE_ATTR_ORTHOGONALCOLOR_NAME  = "orthogonalColor";
  boolean  CARTOONVOLUMESTYLE_ATTR_ORTHOGONALCOLOR_REQD  = false;
  String   CARTOONVOLUMESTYLE_ATTR_ORTHOGONALCOLOR_DFLT  = "1 1 1 1";
  String   CARTOONVOLUMESTYLE_ATTR_PARALLELCOLOR_NAME    = "parallelColor";
  boolean  CARTOONVOLUMESTYLE_ATTR_PARALLELCOLOR_REQD    = false;
  String   CARTOONVOLUMESTYLE_ATTR_PARALLELCOLOR_DFLT    = "0 0 0 1";

  // ComposedVolumeStyle
  String   COMPOSEDVOLUMESTYLE_ELNAME = "ComposedVolumeStyle";
  String   COMPOSEDVOLUMESTYLE_ATTR_ENABLED_NAME          = "enabled";
  boolean  COMPOSEDVOLUMESTYLE_ATTR_ENABLED_REQD          = false;
  String   COMPOSEDVOLUMESTYLE_ATTR_ENABLED_DFLT          = "true";
  String   COMPOSEDVOLUMESTYLE_ATTR_ORDERED_NAME          = "ordered";
  boolean  COMPOSEDVOLUMESTYLE_ATTR_ORDERED_REQD          = false;
  String   COMPOSEDVOLUMESTYLE_ATTR_ORDERED_DFLT          = "false";

  // EdgeEnhancementVolumeStyle
  String   EDGEENHANCEMENTVOLUMESTYLE_ELNAME = "EdgeEnhancementVolumeStyle";
  String   EDGEENHANCEMENTVOLUMESTYLE_ATTR_ENABLED_NAME           = "enabled";
  boolean  EDGEENHANCEMENTVOLUMESTYLE_ATTR_ENABLED_REQD           = false;
  String   EDGEENHANCEMENTVOLUMESTYLE_ATTR_ENABLED_DFLT           = "true";
  String   EDGEENHANCEMENTVOLUMESTYLE_ATTR_EDGECOLOR_NAME         = "edgeColor";
  boolean  EDGEENHANCEMENTVOLUMESTYLE_ATTR_EDGECOLOR_REQD         = false;
  String   EDGEENHANCEMENTVOLUMESTYLE_ATTR_EDGECOLOR_DFLT         = "0 0 0 1";
  String   EDGEENHANCEMENTVOLUMESTYLE_ATTR_GRADIENTTHRESHOLD_NAME = "gradientThreshold";
  boolean  EDGEENHANCEMENTVOLUMESTYLE_ATTR_GRADIENTTHRESHOLD_REQD = false;
  String   EDGEENHANCEMENTVOLUMESTYLE_ATTR_GRADIENTTHRESHOLD_DFLT = "0.4";

  // OpacityMapVolumeStyle
  String   OPACITYMAPVOLUMESTYLE_ELNAME = "OpacityMapVolumeStyle";
  String   OPACITYMAPVOLUMESTYLE_ATTR_ENABLED_NAME          = "enabled";
  boolean  OPACITYMAPVOLUMESTYLE_ATTR_ENABLED_REQD          = false;
  String   OPACITYMAPVOLUMESTYLE_ATTR_ENABLED_DFLT          = "true";

  // ProjectionVolumeStyle
  String   PROJECTIONVOLUMESTYLE_ELNAME = "ProjectionVolumeStyle";
  String   PROJECTIONVOLUMESTYLE_ATTR_ENABLED_NAME            = "enabled";
  boolean  PROJECTIONVOLUMESTYLE_ATTR_ENABLED_REQD            = false;
  String   PROJECTIONVOLUMESTYLE_ATTR_ENABLED_DFLT            = "true";
  String   PROJECTIONVOLUMESTYLE_ATTR_INTENSITYTHRESHOLD_NAME = "intensityThreshold";
  boolean  PROJECTIONVOLUMESTYLE_ATTR_INTENSITYTHRESHOLD_REQD = false;
  String   PROJECTIONVOLUMESTYLE_ATTR_INTENSITYTHRESHOLD_DFLT = "0";
  String   PROJECTIONVOLUMESTYLE_ATTR_TYPE_NAME               = "type";
  boolean  PROJECTIONVOLUMESTYLE_ATTR_TYPE_REQD               = false;
  String   PROJECTIONVOLUMESTYLE_ATTR_TYPE_DFLT               = "MAX";

  String[] PROJECTIONVOLUMESTYLE_ATTR_TYPE_CHOICES = {
      "MAX", "AVERAGE", "MIN"
  };

  // ShadedVolumeStyle
  String   SHADEDVOLUMESTYLE_ELNAME = "ShadedVolumeStyle";
  String   SHADEDVOLUMESTYLE_ATTR_ENABLED_NAME       = "enabled";
  boolean  SHADEDVOLUMESTYLE_ATTR_ENABLED_REQD       = false;
  String   SHADEDVOLUMESTYLE_ATTR_ENABLED_DFLT       = "true";
  String   SHADEDVOLUMESTYLE_ATTR_LIGHTING_NAME      = "lighting";
  boolean  SHADEDVOLUMESTYLE_ATTR_LIGHTING_REQD      = false;
  String   SHADEDVOLUMESTYLE_ATTR_LIGHTING_DFLT      = "false";
  String   SHADEDVOLUMESTYLE_ATTR_SHADOWS_NAME       = "shadows";
  boolean  SHADEDVOLUMESTYLE_ATTR_SHADOWS_REQD       = false;
  String   SHADEDVOLUMESTYLE_ATTR_SHADOWS_DFLT       = "false";
  String   SHADEDVOLUMESTYLE_ATTR_PHASEFUNCTION_NAME = "phaseFunction";
  boolean  SHADEDVOLUMESTYLE_ATTR_PHASEFUNCTION_REQD = false;
  String   SHADEDVOLUMESTYLE_ATTR_PHASEFUNCTION_DFLT = "Henyey-Greenstein";

  String[] SHADEDVOLUMESTYLE_ATTR_PHASEFUNCTION_CHOICES = {
      "Henyey-Greenstein", "None" // and no others yet defined for X3D v3.3+
  };

  // SilhouetteEnhancementVolumeStyle
  String  SILHOUETTEENHANCEMENTVOLUMESTYLE_ELNAME = "SilhouetteEnhancementVolumeStyle";
  String  SILHOUETTEENHANCEMENTVOLUMESTYLE_ATTR_ENABLED_NAME                   = "enabled";
  boolean SILHOUETTEENHANCEMENTVOLUMESTYLE_ATTR_ENABLED_REQD                   = false;
  String  SILHOUETTEENHANCEMENTVOLUMESTYLE_ATTR_ENABLED_DFLT                   = "true";
  String  SILHOUETTEENHANCEMENTVOLUMESTYLE_ATTR_SILHOUETTEBOUNDARYOPACITY_NAME = "silhouetteBoundaryOpacity";
  boolean SILHOUETTEENHANCEMENTVOLUMESTYLE_ATTR_SILHOUETTEBOUNDARYOPACITY_REQD = false;
  String  SILHOUETTEENHANCEMENTVOLUMESTYLE_ATTR_SILHOUETTEBOUNDARYOPACITY_DFLT = "0";
  String  SILHOUETTEENHANCEMENTVOLUMESTYLE_ATTR_SILHOUETTERETAINEDOPACITY_NAME = "silhouetteRetainedOpacity";
  boolean SILHOUETTEENHANCEMENTVOLUMESTYLE_ATTR_SILHOUETTERETAINEDOPACITY_REQD = false;
  String  SILHOUETTEENHANCEMENTVOLUMESTYLE_ATTR_SILHOUETTERETAINEDOPACITY_DFLT = "1";
  String  SILHOUETTEENHANCEMENTVOLUMESTYLE_ATTR_SILHOUETTESHARPNESS_NAME       = "silhouetteSharpness";
  boolean SILHOUETTEENHANCEMENTVOLUMESTYLE_ATTR_SILHOUETTESHARPNESS_REQD       = false;
  String  SILHOUETTEENHANCEMENTVOLUMESTYLE_ATTR_SILHOUETTESHARPNESS_DFLT       = "0.5";

  // ToneMappedVolumeStyle
  String   TONEMAPPEDVOLUMESTYLE_ELNAME = "ToneMappedVolumeStyle";
  String   TONEMAPPEDVOLUMESTYLE_ATTR_ENABLED_NAME           = "enabled";
  boolean  TONEMAPPEDVOLUMESTYLE_ATTR_ENABLED_REQD           = false;
  String   TONEMAPPEDVOLUMESTYLE_ATTR_ENABLED_DFLT           = "true";
  String   TONEMAPPEDVOLUMESTYLE_ATTR_COOLCOLOR_NAME         = "coolColor";
  boolean  TONEMAPPEDVOLUMESTYLE_ATTR_COOLCOLOR_REQD         = false;
  String   TONEMAPPEDVOLUMESTYLE_ATTR_COOLCOLOR_DFLT         = "0 0 1 0";
  String   TONEMAPPEDVOLUMESTYLE_ATTR_WARMCOLOR_NAME         = "warmColor";
  boolean  TONEMAPPEDVOLUMESTYLE_ATTR_WARMCOLOR_REQD         = false;
  String   TONEMAPPEDVOLUMESTYLE_ATTR_WARMCOLOR_DFLT         = "1 1 0 0";

  // Element VolumeData
  String   VOLUMEDATA_ELNAME = "VolumeData";
  String   VOLUMEDATA_ATTR_DIMENSIONS_NAME  = "dimensions";
  boolean  VOLUMEDATA_ATTR_DIMENSIONS_REQD  = false;
  String   VOLUMEDATA_ATTR_DIMENSIONS_DFLT  = "1 1 1";
  String   VOLUMEDATA_ATTR_BBOXCENTER_NAME  = "bboxCenter";
  boolean  VOLUMEDATA_ATTR_BBOXCENTER_REQD  = false;
  String   VOLUMEDATA_ATTR_BBOXCENTER_DFLT  = "0 0 0";
  String   VOLUMEDATA_ATTR_BBOXSIZE_NAME    = "bboxSize";
  boolean  VOLUMEDATA_ATTR_BBOXSIZE_REQD    = false;
  String   VOLUMEDATA_ATTR_BBOXSIZE_DFLT    = "-1 -1 -1";

  // Element IsoSurfaceVolumeData
  String   ISOSURFACEVOLUMEDATA_ELNAME = "IsoSurfaceVolumeData";
  String   ISOSURFACEVOLUMEDATA_ATTR_CONTOURSTEPSIZE_NAME   = "contourStepSize";
  boolean  ISOSURFACEVOLUMEDATA_ATTR_CONTOURSTEPSIZE_REQD   = false;
  String   ISOSURFACEVOLUMEDATA_ATTR_CONTOURSTEPSIZE_DFLT   = "1";
  String   ISOSURFACEVOLUMEDATA_ATTR_DIMENSIONS_NAME        = "dimensions";
  boolean  ISOSURFACEVOLUMEDATA_ATTR_DIMENSIONS_REQD        = false;
  String   ISOSURFACEVOLUMEDATA_ATTR_DIMENSIONS_DFLT        = "1 1 1";
  String   ISOSURFACEVOLUMEDATA_ATTR_SURFACETOLERANCE_NAME  = "surfaceTolerance";
  boolean  ISOSURFACEVOLUMEDATA_ATTR_SURFACETOLERANCE_REQD  = false;
  String   ISOSURFACEVOLUMEDATA_ATTR_SURFACETOLERANCE_DFLT  = "0";
  String   ISOSURFACEVOLUMEDATA_ATTR_SURFACEVALUES_NAME     = "surfaceValues";
  boolean  ISOSURFACEVOLUMEDATA_ATTR_SURFACEVALUES_REQD     = false;
  String   ISOSURFACEVOLUMEDATA_ATTR_SURFACEVALUES_DFLT     = "";
  String   ISOSURFACEVOLUMEDATA_ATTR_BBOXCENTER_NAME        = "bboxCenter";
  boolean  ISOSURFACEVOLUMEDATA_ATTR_BBOXCENTER_REQD        = false;
  String   ISOSURFACEVOLUMEDATA_ATTR_BBOXCENTER_DFLT        = "0 0 0";
  String   ISOSURFACEVOLUMEDATA_ATTR_BBOXSIZE_NAME          = "bboxSize";
  boolean  ISOSURFACEVOLUMEDATA_ATTR_BBOXSIZE_REQD          = false;
  String   ISOSURFACEVOLUMEDATA_ATTR_BBOXSIZE_DFLT          = "-1 -1 -1";

  // Element SegmentedVolumeData
  String   SEGMENTEDVOLUMEDATA_ELNAME = "SegmentedVolumeData";
  String   SEGMENTEDVOLUMEDATA_ATTR_DIMENSIONS_NAME     = "dimensions";
  boolean  SEGMENTEDVOLUMEDATA_ATTR_DIMENSIONS_REQD     = false;
  String   SEGMENTEDVOLUMEDATA_ATTR_DIMENSIONS_DFLT     = "1 1 1";
  String   SEGMENTEDVOLUMEDATA_ATTR_SEGMENTENABLED_NAME = "segmentEnabled";
  boolean  SEGMENTEDVOLUMEDATA_ATTR_SEGMENTENABLED_REQD = false;
  String   SEGMENTEDVOLUMEDATA_ATTR_SEGMENTENABLED_DFLT = "";
  String   SEGMENTEDVOLUMEDATA_ATTR_BBOXCENTER_NAME     = "bboxCenter";
  boolean  SEGMENTEDVOLUMEDATA_ATTR_BBOXCENTER_REQD     = false;
  String   SEGMENTEDVOLUMEDATA_ATTR_BBOXCENTER_DFLT     = "0 0 0";
  String   SEGMENTEDVOLUMEDATA_ATTR_BBOXSIZE_NAME       = "bboxSize";
  boolean  SEGMENTEDVOLUMEDATA_ATTR_BBOXSIZE_REQD       = false;
  String   SEGMENTEDVOLUMEDATA_ATTR_BBOXSIZE_DFLT       = "-1 -1 -1";

  /** AllX3dElementNames.txt */
  String[] ALL_X3D_NODE_NAMES = {
"AcousticProperties",
"Analyser",
"Anchor",
"Appearance",
"Arc2D",
"ArcClose2D",
"AudioClip",
"AudioDestination",
"Background",
"BallJoint",
"Billboard",
"BiquadFilter",
"BlendedVolumeStyle",
"BooleanFilter",
"BooleanSequencer",
"BooleanToggle",
"BooleanTrigger",
"BoundaryEnhancementVolumeStyle",
"BoundedPhysicsModel",
"Box",
"BufferAudioSource",
"CADAssembly",
"CADFace",
"CADLayer",
"CADPart",
"CartoonVolumeStyle",
"ChannelMerger",
"ChannelSelector",
"ChannelSplitter",
"Circle2D",
"ClipPlane",
"CollidableOffset",
"CollidableShape",
"Collision",
"CollisionCollection",
"CollisionSensor",
"CollisionSpace",
"Color",
"ColorChaser",
"ColorDamper",
"ColorInterpolator",
"ColorRGBA",
"ComposedCubeMapTexture",
"ComposedShader",
"ComposedTexture3D",
"ComposedVolumeStyle",
"Cone",
"ConeEmitter",
"Contact",
"Contour2D",
"ContourPolyline2D",
"Convolver",
"Coordinate",
"CoordinateChaser",
"CoordinateDamper",
"CoordinateDouble",
"CoordinateInterpolator",
"CoordinateInterpolator2D",
"Cylinder",
"CylinderSensor",
"Delay",
"DirectionalLight",
"DISEntityManager",
"DISEntityTypeMapping",
"Disk2D",
"DoubleAxisHingeJoint",
"DynamicsCompressor",
"EaseInEaseOut",
"EdgeEnhancementVolumeStyle",
"ElevationGrid",
"EnvironmentLight",
"EspduTransform",
"ExplosionEmitter",
"Extrusion",
"FillProperties",
"FloatVertexAttribute",
"Fog",
"FogCoordinate",
"FontStyle",
"ForcePhysicsModel",
"Gain",
"GeneratedCubeMapTexture",
"GeoCoordinate",
"GeoElevationGrid",
"GeoLocation",
"GeoLOD",
"GeoMetadata",
"GeoOrigin",
"GeoPositionInterpolator",
"GeoProximitySensor",
"GeoTouchSensor",
"GeoTransform",
"GeoViewpoint",
"Group",
"HAnimDisplacer",
"HAnimHumanoid",
"HAnimJoint",
"HAnimMotion",
"HAnimSegment",
"HAnimSite",
"ImageCubeMapTexture",
"ImageTexture",
"ImageTexture3D",
"IndexedFaceSet",
"IndexedLineSet",
"IndexedQuadSet",
"IndexedTriangleFanSet",
"IndexedTriangleSet",
"IndexedTriangleStripSet",
"Inline",
"IntegerSequencer",
"IntegerTrigger",
"IsoSurfaceVolumeData",
"KeySensor",
"Layer",
"LayerSet",
"Layout",
"LayoutGroup",
"LayoutLayer",
"LinePickSensor",
"LineProperties",
"LineSet",
"ListenerPointSource",
"LoadSensor",
"LocalFog",
"LOD",
"Material",
"Matrix3VertexAttribute",
"Matrix4VertexAttribute",
"MetadataBoolean",
"MetadataDouble",
"MetadataFloat",
"MetadataInteger",
"MetadataSet",
"MetadataString",
"MicrophoneSource",
"MotorJoint",
"MovieTexture",
"MultiTexture",
"MultiTextureCoordinate",
"MultiTextureTransform",
"NavigationInfo",
"Normal",
"NormalInterpolator",
"NurbsCurve",
"NurbsCurve2D",
"NurbsOrientationInterpolator",
"NurbsPatchSurface",
"NurbsPositionInterpolator",
"NurbsSet",
"NurbsSurfaceInterpolator",
"NurbsSweptSurface",
"NurbsSwungSurface",
"NurbsTextureCoordinate",
"NurbsTrimmedSurface",
"OpacityMapVolumeStyle",
"OrientationChaser",
"OrientationDamper",
"OrientationInterpolator",
"OrthoViewpoint",
"OscillatorSource",
"PackagedShader",
"ParticleSystem",
"PeriodicWave",
"PhysicalMaterial",
"PickableGroup",
"PixelTexture",
"PixelTexture3D",
"PlaneSensor",
"PointEmitter",
"PointLight",
"PointPickSensor",
"PointProperties",
"PointSet",
"Polyline2D",
"PolylineEmitter",
"Polypoint2D",
"PositionChaser",
"PositionChaser2D",
"PositionDamper",
"PositionDamper2D",
"PositionInterpolator",
"PositionInterpolator2D",
"PrimitivePickSensor",
"ProgramShader",
"ProjectionVolumeStyle",
"ProtoInstance",
"ProximitySensor",
"QuadSet",
"ReceiverPdu",
"Rectangle2D",
"RigidBody",
"RigidBodyCollection",
"ScalarChaser",
"ScalarDamper",
"ScalarInterpolator",
"ScreenFontStyle",
"ScreenGroup",
"Script",
"SegmentedVolumeData",
"ShadedVolumeStyle",
"ShaderPart",
"ShaderProgram",
"Shape",
"SignalPdu",
"SilhouetteEnhancementVolumeStyle",
"SingleAxisHingeJoint",
"SliderJoint",
"Sound",
"SpatialSound",
"Sphere",
"SphereSensor",
"SplinePositionInterpolator",
"SplinePositionInterpolator2D",
"SplineScalarInterpolator",
"SpotLight",
"SquadOrientationInterpolator",
"StaticGroup",
"StreamAudioDestination",
"StreamAudioSource",
"StringSensor",
"SurfaceEmitter",
"Switch",
"TexCoordChaser2D",
"TexCoordDamper2D",
"Text",
"TextureBackground",
"TextureCoordinate",
"TextureCoordinate3D",
"TextureCoordinate4D",
"TextureCoordinateGenerator",
"TextureProjector",
"TextureProjectorParallel",
"TextureProperties",
"TextureTransform",
"TextureTransform3D",
"TextureTransformMatrix3D",
"TimeSensor",
"TimeTrigger",
"ToneMappedVolumeStyle",
"TouchSensor",
"Transform",
"TransformSensor",
"TransmitterPdu",
"TriangleFanSet",
"TriangleSet",
"TriangleSet2D",
"TriangleStripSet",
"TwoSidedMaterial",
"UniversalJoint",
"UnlitMaterial",
"Viewpoint",
"ViewpointGroup",
"Viewport",
"VisibilitySensor",
"VolumeData",
"VolumeEmitter",
"VolumePickSensor",
"WaveShaper",
"WindPhysicsModel",
"WorldInfo"
};

}
