/*
Copyright (c) 1995-2022 held by the author(s).  All rights reserved.
 
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

package org.web3d.x3d.actions;

import java.awt.Desktop;
import java.net.URI;
import org.openide.util.HelpCtx;
import org.openide.util.actions.CallableSystemAction;

/**
 * BaseViewAction.java
 * Created on Feb 7, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Mike Bailey <jmbailey@nps.edu>
 * @version $Id$
 */
@SuppressWarnings("serial")
abstract public class BaseViewAction extends CallableSystemAction
{
  public final static String X3D_TIDY_URL                           ="https://www.web3d.org/x3d/stylesheets/X3dTidy.html";
  public final static String X3DVALIDATORURL                        ="https://savage.nps.edu/X3dValidator";
  public final static String BASICEXAMPLESURL                       ="https://www.web3d.org/x3d/content/examples/Basic";
  public final static String CONFORMEXAMPLESURL                     ="https://www.web3d.org/x3d/content/examples/ConformanceNist";
  public final static String HANIMEXAMPLESURL                       ="https://www.web3d.org/x3d/content/examples/HumanoidAnimation";
  public final static String VRMLEXAMPLESURL                        ="https://www.web3d.org/x3d/content/examples/Vrml2.0Sourcebook";
  public final static String X3D4WA_EXAMPLESURL                     ="https://www.x3dGraphics.com/examples/X3dForWebAuthors";
  public final static String X3D4AM_EXAMPLESURL                     ="https://www.x3dGraphics.com/examples/X3dForAdvancedModeling";
  public final static String SAVAGEEXAMPLESURL                      ="https://savage.nps.edu/Savage";
  public final static String SAVAGEDEFENSEEXAMPLESURL               ="https://SavageDefense.nps.navy.mil/SavageDefense";
  public final static String X3DSECURITYEXAMPLES                    ="https://www.web3d.org/x3d/content/examples/Basic/Security";
  public final static String X3DSECURITYREADME                      ="https://www.web3d.org/x3d/content/examples/Basic/Security/X3dSecurityReadMe.html";
  public final static String X3D_EDIT_HOME                          ="https://savage.nps.edu/X3D-Edit";
  public final static String X3D_EDIT_FEATURES                      = X3D_EDIT_HOME + "#Features";
  public final static String SOURCEFORGE_RELEASE_INSTALLERS         ="https://sourceforge.net/projects/x3d/files";
  public final static String SOURCEFORGE_X3DEDIT_SOURCE             ="https://sourceforge.net/p/x3d/code/HEAD/tree/www.web3d.org/x3d/tools/X3dEdit4.0";
  public final static String X3D_RESOURCES                          ="https://www.web3d.org/x3d/content/examples/X3dResources.html";
  public final static String X3D_RESOURCES_EXAMPLES_ARCHIVES        = X3D_RESOURCES + "#Examples";
  public final static String X3D_RESOURCES_QUALITY_ASSURANCE        = X3D_RESOURCES + "#QualityAssurance";
  public final static String X3D_RESOURCES_SECURITY                 = X3D_RESOURCES + "#Security";
  public final static String X3D_RESOURCES_SECURITY_VULNERABILITIES =X3D_RESOURCES + "#Vulnerabilities";
  public final static String X3D_SCENE_AUTHORING_HINTS              ="https://www.web3d.org/x3d/content/examples/X3dSceneAuthoringHints.html";
  public final static String ECMASCRIPT_SPECIFICATION               ="https://262.ecma-international.org/13.0";
  public final static String X3D_SCENE_AUTHORING_HINTS_CORS         ="https://www.web3d.org/x3d/content/examples/X3dSceneAuthoringHints.html#CORS";
  public final static String X3D_TOOLTIPS                           ="https://www.web3d.org/x3d/tooltips/X3dTooltips.html";
  public final static String X3D_COURSE                             ="https://x3dgraphics.com/slidesets";
  public final static String X3D_COURSE_VIDEOS                      ="https://www.youtube.com/channel/UCSOnGlgAFxkWg8ilg-JEbAQ";
  public final static String X3D_QUICKSTART_TUTORIAL                ="https://www.web3d.org/news-story/x3d-quickstart-tutorial-video-online";
           
  public final static String X3D_SCHEMA_DOCTYPE_VALIDATION          ="https://www.web3d.org/specifications";
  public final static String X3D_REGEX                              ="https://www.web3d.org/specifications/X3dRegularExpressions.html";
  public final static String X3D_SCHEMATRON                         ="https://www.web3d.org/x3d/tools/schematron/X3dSchematron.html";
  public final static String X3D_X3DUOM                             ="https://www.web3d.org/specifications/X3DUOM.html";
  public final static String SAVAGE_DEVELOPERS_GUIDE                ="https://savage.nps.edu/Savage/developers.html";
  public final static String SISO_DIS_RPR_FOM_PRODUCT_SUPPORT_GROUP ="https://www.sisostds.org/StandardsActivities/SupportGroups/DISRPRFOMPSG.aspx";
  
  public final static String ACM_SIGGRAPH                           ="https://siggraph.acm.org";
  public final static String WEB3D_CONSORTIUM_HOME                  ="https://web3D.org";
  public final static String WEB3D_CONSORTIUM_JOIN                  ="https://web3D.org/join";
  public final static String WEB3D_CONSORTIUM_GITHUB_DOCUMENTS      ="https://github.com/Web3dConsortium";
  public final static String WEB3D_CONSORTIUM_MAILING_LISTS         ="https://www.web3d.org/community/public-mailing-lists";
  public final static String WEB3D_CONSORTIUM_MANTIS_ISSUES         ="https://www.web3d.org/member-only/mantis/my_view_page.php";
//public final static String WEB3D_CONSORTIUM_MANTIS_ISSUES         ="https://www.web3d.org/member-only/mantis/login_page.php";
  public final static String WEB3D_CONFERENCE                       ="https://web3d.siggraph.org";
  public final static String WEB3D_CONFERENCE_ACM_DIGITAL_LIBRARY   ="https://dl.acm.org/conference/web3d";
  public final static String WEB3D_CONFERENCE_YOUTUBE               ="https://www.youtube.com/channel/UCxg1RdwicxPHgRT0QQbuGFQ";
  public final static String WEB3D_FACEBOOK                         ="https://www.facebook.com/people/Web3D-Consortium/100057066266099/";
  public final static String WEB3D_TWITTER                          ="https://twitter.com/Web3Dconsortium";
  public final static String WEB3D_YOUTUBE                          ="https://www.youtube.com/Web3DConsortium";
  public final static String WEB3D_EXAMPLES                         ="https://web3D.org/example"; // mistakenly named website page
  public final static String WEB3D_STANDARDS                        ="https://web3D.org/standards";
  public final static String X3D_GRAPHICS_STANDARDS_RELATIONSHIPS   ="https://web3D.org/specifications/X3dGraphicsStandardsRelationships.png";
  public final static String HANIM2_STANDARD                        ="https://www.web3d.org/documents/specifications/19774/V2.0/index.html";
  public final static String X3D4_ARCHITECTURE_STANDARD_CD1         ="https://www.web3d.org/specifications/X3Dv4Draft/ISO-IEC19775-1v4-CD1/Part01/Architecture.html";
  public final static String X3D4_ARCHITECTURE_STANDARD_DIS         ="https://www.web3d.org/specifications/X3Dv4Draft/ISO-IEC19775-1v4-DIS/Part01/Architecture.html";
  public final static String X3D_XML_ENCODING_STANDARD              ="https://www.web3d.org/documents/specifications/19776-1/V3.3/Part01/X3D_XML.html";
  public final static String X3D_CLASSICVRML_ENCODING_STANDARD      ="https://www.web3d.org/documents/specifications/19776-2/V3.3/Part02/X3D_ClassicVRML.html";
  public final static String X3D_COMPRESSED_BINARY_ENCODING_STANDARD="https://www.web3d.org/documents/specifications/19776-3/V3.3/Part03/X3D_Binary.html";
  public final static String X3D_JSON_ENCODING_GUIDELINES           ="https://www.web3d.org/x3d/stylesheets/X3dToJson.html"; // TODO standard
  public final static String VRML97_STANDARD                        ="https://www.web3d.org/documents/specifications/14772/V2.0/index.html";
  public final static String VRML97_STANDARD_AMENDMENT_1            ="https://www.web3d.org/documents/specifications/14772-1/V2.1/index.html";

  public final static String CASTLE_GAME_ENGINE_CONVERTER           ="https://castle-engine.io/convert.php";
  public final static String X3D_JAVA_LANGUAGE_BINDING_X3DJSAIL     ="https://www.web3d.org/specifications/java/X3DJSAIL.html";
  public final static String X3D_X3DJSAIL_JAVADOC                   ="https://www.web3d.org/specifications/java/javadoc";
  public final static String X3D_JSON_CONVERTER                     ="https://www.web3d.org/x3d/stylesheets/X3dToJson.html";
  public final static String X3D_PYTHON_LANGUAGE_BINDING_X3DPSAIL   ="https://www.web3d.org/x3d/stylesheets/python/python.html";
  public final static String X3D_PYPI                               ="https://pypi.org/project/x3d";
  public final static String X3D_ONTOLOGY                           ="https://www.web3d.org/x3d/content/semantics/semantics.html";
  public final static String X3D_ONTOLOGY_DOCUMENTATION             ="https://www.web3d.org/x3d/content/semantics/documentation/owldoc/index.html";

  public final static String X3D_C_LANGUAGE_BINDING_SAI             ="http://www.web3d.org/specifications/ISO-IEC19777/ISO-IEC19777-3v3.3-DIS";
  public final static String X3D_CPP_LANGUAGE_BINDING_SAI           ="http://www.web3d.org/specifications/ISO-IEC19777/ISO-IEC19777-4v3.3-DIS";
  public final static String X3D_CSHARP_LANGUAGE_BINDING_SAI        ="http://www.web3d.org/specifications/ISO-IEC19777/ISO-IEC19777-5v3.3-DIS";
  
  public final static String W3C_HOME                               ="https://www.w3.org";
  public final static String W3C_STANDARDS_OPEN_WEB_PLATFORM        ="https://www.w3.org/standards";
  public final static String W3C_DEVELOPERS                         ="https://www.w3.org/developers";
  public final static String W3C_EXI_RECOMMENDATION                 ="https://www.w3.org/TR/exi/";
  public final static String W3C_CASCADING_STYLESHEETS              ="https://www.w3.org/Style/CSS";
  public final static String W3C_WEB_AUDIO_API                      ="https://www.w3.org/TR/webaudio";
  public final static String W3C_SEMANTIC_WEB                       ="https://www.w3.org/standards/semanticweb";
  public final static String W3C_WHATWG_HTML5_STANDARD              ="https://html.spec.whatwg.org/multipage";
  public final static String W3CX_FREE_ONLINE_COURSES               ="https://www.edx.org/school/w3cx";
  
  private Boolean menuItemEnabled = Boolean.TRUE;
  
  protected void sendBrowserTo(String urlString)
  {
     try {
       showInBrowser(urlString);
     }
     catch(Exception e)
     {
       System.err.println("Trying to display "+urlString+" in HtmlBrowser: "+e.getLocalizedMessage());
     }    
  }
  
  protected void showInBrowser(String urlString) throws Exception
  {
    // HtmlBrowser.URLDisplayer.getDefault().showURL(new URL(urlString));
      
    // https://stackoverflow.com/questions/5226212/how-to-open-the-default-webbrowser-using-java
    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
        Desktop.getDesktop().browse(new URI(urlString));
    
  }
  
  protected String getLocalExamplesPath()
  {
    return null;
  }
  
 @Override
  protected void initialize()
  {
      super.initialize();
      // see org.openide.util.actions.SystemAction.iconResource() Javadoc for more details
      if (getMenuItemEnabled())
      {
        putValue("noIconInMenu", getMenuItemEnabled());
      }
      else this.setEnabled(false);
  }

  @Override
  public HelpCtx getHelpCtx()
  {
    return new HelpCtx("helpExamples");
  }

  @Override
  protected boolean asynchronous()
  {
    return false;
  }

    /**
     * @return the menuItemEnabled
     */
    public Boolean getMenuItemEnabled()
    {
        return menuItemEnabled;
    }

    /**
     * @param menuItemEnabled the menuItemEnabled to set
     */
    public void setMenuItemEnabled(Boolean menuItemEnabled)
    {
        this.menuItemEnabled = menuItemEnabled;
    }
}
