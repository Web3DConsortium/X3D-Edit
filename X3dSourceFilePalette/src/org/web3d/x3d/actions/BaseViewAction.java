/*
Copyright (c) 1995-2022 held by the author(s) .  All rights reserved.
 
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

package org.web3d.x3d.actions;

import java.net.URL;
import org.openide.awt.HtmlBrowser;
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
  public final static String X3DVALIDATORURL                      ="https://savage.nps.edu/X3dValidator";
  public final static String BASICEXAMPLESURL                     ="https://www.web3d.org/x3d/content/examples/Basic";
  public final static String CONFORMEXAMPLESURL                   ="https://www.web3d.org/x3d/content/examples/ConformanceNist";
  public final static String HANIMEXAMPLESURL                     ="https://www.web3d.org/x3d/content/examples/HumanoidAnimation";
  public final static String VRMLEXAMPLESURL                      ="https://www.web3d.org/x3d/content/examples/Vrml2.0Sourcebook";
  public final static String X3D4WA_EXAMPLESURL                   ="http://www.x3dGraphics.com/examples/X3dForWebAuthors";
  public final static String X3D4AM_EXAMPLESURL                   ="http://www.x3dGraphics.com/examples/X3dForAdvancedModeling";
  public final static String SAVAGEEXAMPLESURL                    ="https://savage.nps.edu/Savage";
  public final static String SAVAGEDEFENSEEXAMPLESURL             ="https://SavageDefense.nps.navy.mil/SavageDefense";
  public final static String X3DSECURITYREADME                    ="https://www.web3d.org/x3d/content/examples/Basic/Security/X3dSecurityReadMe.html";
  public final static String X3D_EDIT_HOME                        ="https://savage.nps.edu/X3D-Edit";
  public final static String X3D_EDIT_FEATURES                    = X3D_EDIT_HOME + "#Features";
  public final static String X3D_RESOURCES                        ="https://www.web3d.org/x3d/content/examples/X3dResources.html";
  public final static String X3D_RESOURCES_EXAMPLES_ARCHIVES      = X3D_RESOURCES + "#Examples";
  public final static String X3D_RESOURCES_QUALITY_ASSURANCE      = X3D_RESOURCES + "#QualityAssurance";
  public final static String X3D_SCENE_AUTHORING_HINTS            ="https://www.web3d.org/x3d/content/examples/X3dSceneAuthoringHints.html";
  public final static String X3D_SCENE_AUTHORING_HINTS_CORS       ="https://www.web3d.org/x3d/content/examples/X3dSceneAuthoringHints.html#CORS";
  public final static String X3D_TOOLTIPS                         ="https://www.web3d.org/x3d/tooltips/X3dTooltips.html";
  public final static String X3D_COURSE                           ="https://x3dgraphics.com/slidesets";
  public final static String X3D_COURSE_VIDEOS                    ="https://www.youtube.com/channel/UCSOnGlgAFxkWg8ilg-JEbAQ";
  public final static String X3D_QUICKSTART_TUTORIAL              ="https://www.web3d.org/news-story/x3d-quickstart-tutorial-video-online";
         
  public final static String X3D_SCHEMA_DOCTYPE_VALIDATION        ="https://www.web3d.org/specifications";
  public final static String X3D_REGEX                            ="https://www.web3d.org/specifications/X3dRegularExpressions.html";
  public final static String X3D_X3DUOM                           ="https://www.web3d.org/specifications/X3DUOM.html";
  public final static String SAVAGE_DEVELOPERS_GUIDE              ="https://savage.nps.edu/Savage/developers.html";
  
  public final static String WEB3D_HOME                           ="https://web3d.org";
  public final static String WEB3D_CONFERENCE                     ="https://web3d.siggraph.org";
  public final static String WEB3D_CONFERENCE_ACM_DIGITAL_LIBRARY ="https://dl.acm.org/conference/web3d";
  public final static String WEB3D_CONFERENCE_YOUTUBE             ="https://www.youtube.com/channel/UCxg1RdwicxPHgRT0QQbuGFQ";
  public final static String WEB3D_FACEBOOK                       ="https://www.facebook.com/people/Web3D-Consortium/100057066266099/";
  public final static String WEB3D_TWITTER                        ="https://twitter.com/Web3Dconsortium";
  public final static String WEB3D_YOUTUBE                        ="https://www.youtube.com/Web3DConsortium";
  public final static String WEB3D_EXAMPLES                       ="https://web3d.org/example"; // mistakenly named website page
  public final static String WEB3D_STANDARDS                      ="https://web3d.org/standards";
  public final static String HANIM2_STANDARD                      ="https://www.web3d.org/documents/specifications/19774/V2.0/index.html";
  public final static String X3D4_ARCHITECTURE_STANDARD_CD1       ="https://www.web3d.org/specifications/X3Dv4Draft/ISO-IEC19775-1v4-CD1/Part01/Architecture.html";
  public final static String X3D4_ARCHITECTURE_STANDARD_DIS       ="https://www.web3d.org/specifications/X3Dv4Draft/ISO-IEC19775-1v4-DIS/Part01/Architecture.html";
  public final static String X3D_XML_ENCODING_STANDARD            ="https://www.web3d.org/documents/specifications/19776-1/V3.3/Part01/X3D_XML.html";
  public final static String X3D_CLASSICVRML_ENCODING_STANDARD    ="https://www.web3d.org/documents/specifications/19776-2/V3.3/Part02/X3D_ClassicVRML.html";
  public final static String X3D_JSON_ENCODING_GUIDELINES         ="https://www.web3d.org/x3d/stylesheets/X3dToJson.html"; // TODO standard
  public final static String CASTLE_GAME_ENGINE_CONVERTER         ="https://castle-engine.io/convert.php";
  
  private Boolean menuItemEnabled = Boolean.TRUE;
  
  protected void sendBrowserTo(String urlString)
  {
     try {
       showInBrowser(urlString);
     }
     catch(Exception e) {
       System.err.println("Trying to display "+urlString+" in HtmlBrowser: "+e.getLocalizedMessage());
     }    
  }
  
  protected void showInBrowser(String urlString) throws Exception
  {
    HtmlBrowser.URLDisplayer.getDefault().showURL(new URL(urlString));
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
