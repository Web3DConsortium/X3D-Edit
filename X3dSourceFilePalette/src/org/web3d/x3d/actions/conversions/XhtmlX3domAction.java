/*
 * Copyright (c) 1995-2021 held by the author(s) .  All rights reserved.
 *  
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *  
 *  * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *  * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer
 *       in the documentation and/or other materials provided with the
 *       distribution.
 *  * Neither the names of the Naval Postgraduate School (NPS)
 *       Modeling Virtual Environments and Simulation (MOVES) Institute
 *       (http://www.nps.edu and https://MovesInstitute.nps.edu)
 *       nor the names of its contributors may be used to endorse or
 *       promote products derived from this software without specific
 *       prior written permission.
 *  
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
 * FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
 * COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
 * BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.web3d.x3d.actions.conversions;

import java.awt.Dialog;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.web3d.x3d.X3DDataObject;
import org.web3d.x3d.X3DEditorSupport;

@ActionID(id = "org.web3d.x3d.actions.conversions.XhtmlX3domAction", category = "Tools")

@ActionRegistration(
        iconBase = "org/web3d/x3d/resources/x3dom-whiteOnblue24.png",
     displayName = "#CTL_XhtmlX3domAction",
             lazy=true) // don't do lazy=false since iconBase no longer gets registered

@ActionReferences(value = {
  @ActionReference(path = "Menu/X3D-Edit/Author Workflow", position = 91),
  @ActionReference(path = "Menu/X3D-Edit/View Saved Scene", position = 151),
  @ActionReference(path = "Toolbars/Author Workflow", position = 91),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/Author Workflow", position = 91),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/View Saved Scene", position = 151)
})

public class XhtmlX3domAction extends BaseConversionsAction
{
    public static String xsltFile = "X3dToX3dom.xslt";

    private X3dToXhtmlDomConversionPanel x3dToXhtmlDomConversionPanel;

    private final HashMap<String,Object> parametersHashMap = new HashMap<>();
    
    private final String            playerDefault = "X3DOM";// "X_CIT"
    private final String      traceEnabledDefault = "true"; // development, debug mode for XSLT stylesheed
    // X3D
    private final String         x3dHeightDefault = "450";
    private final String          x3dWidthDefault = "800";
    // X3DOM
    private final boolean          showLogDefault = true;
    private final boolean     showProgressDefault = true;
    private final boolean   showStatisticsDefault = true;
    private final String  primitiveQualityDefault = "High";
    // Cobweb
    private final boolean            cacheDefault = true;
    private final String               urlDefault = ""; // String holding MFString
   
    private String         x3dHeight = x3dHeightDefault;
    private String          x3dWidth = x3dWidthDefault;
    private String            player = playerDefault;
    private boolean          showLog = showLogDefault;
    private boolean     showProgress = showProgressDefault;
    private boolean   showStatistics = showStatisticsDefault;
    private String  primitiveQuality = primitiveQualityDefault;
    private boolean            cache = cacheDefault;
    private String          urlScene = urlDefault; // String holding MFString
    private String      traceEnabled = traceEnabledDefault;
    
    private DialogDescriptor descriptor;
    private Dialog dialog;
    private JButton continueButton, resetButton, cancelButton;
    
    private ConversionsHelper.saveFilePack fp;

  @Override
  public String getName()
  {
    return NbBundle.getMessage(getClass(), "CTL_XhtmlX3domAction");
  }

  @Override
  protected String iconResource()
  {
      if (getPlayer().equalsIgnoreCase("Cobweb"))
           return "org/web3d/x3d/resources/cobweb-logo32.png";
      else return "org/web3d/x3d/resources/x3dom-whiteOnblue24.png";
  }
  // see org.openide.util.actions.SystemAction.iconResource() Javadoc for more details

    @Override
    protected void initialize()
    {
        super.initialize();

        if (parametersHashMap.isEmpty())
        {
            resetValuesToDefault ();
            saveParametersHashMap ();
        }
        // launch panel to confirm settings; default re-use behavior is to retain prior values by not reinitializing each time
        if (x3dToXhtmlDomConversionPanel == null)
        {
            x3dToXhtmlDomConversionPanel = new X3dToXhtmlDomConversionPanel (this);
    
            // pattern from Xj3dCadFilterOptionsPanel to launch and exit the panel
             continueButton = new JButton(NbBundle.getMessage(getClass(),"MSG_Continue")); // Transform
                resetButton = new JButton(NbBundle.getMessage(getClass(),"MSG_Reset"));
               cancelButton = new JButton(NbBundle.getMessage(getClass(),"MSG_Cancel"));
            continueButton.setToolTipText(NbBundle.getMessage(getClass(),"TIP_Continue"));
               resetButton.setToolTipText(NbBundle.getMessage(getClass(),"TIP_Reset"));
              cancelButton.setToolTipText(NbBundle.getMessage(getClass(),"TIP_Cancel"));
            HelpCtx.setHelpIDString(x3dToXhtmlDomConversionPanel, "X3dToJson.html");

            descriptor = new DialogDescriptor(
                x3dToXhtmlDomConversionPanel, // inner pane
                NbBundle.getMessage(getClass(),"X3dToXhtmlDomConversionPanel.DialogTitle"),
                true, // modal
                new Object[]{continueButton, resetButton, cancelButton},  // buttons
                continueButton,                            // default
                DialogDescriptor.DEFAULT_ALIGN,
                HelpCtx.DEFAULT_HELP, // TODO confirm linking
                null); // action listener

            dialog = DialogDisplayer.getDefault().createDialog(descriptor);
            dialog.setResizable(true);
            dialog.pack();
        }
    }
  
    protected void resetValuesToDefault ()
    {
//                setPlayer(playerDefault); // do not override
          setTraceEnabled(traceEnabledDefault);
    // X3D
             setX3dHeight(x3dHeightDefault);
              setX3dWidth(x3dWidthDefault);
    // X3DOM
               showLog = showLogDefault;
          showProgress = showProgressDefault;
        showStatistics = showStatisticsDefault;
      primitiveQuality = primitiveQualityDefault;
    // Cobweb
               setCache(cacheDefault);
            setUrlScene(urlDefault);
    }

  @Override
  public String transformSingleFile(X3DEditorSupport.X3dEditor x3dEditor)
  {
        saveParametersHashMap (); // save prior values
        
        // used in parent XhtmlX3domAction and X3dToXhtmlDomConversionPanel
        X3DDataObject dob = (X3DDataObject)x3dEditor.getX3dEditorSupport().getDataObject();
        String fileName = dob.getPrimaryFile().getNameExt();
        String filePath = dob.getPrimaryFile().getPath();
        
        String urlList;
        urlList  =  "\"" + fileName +  "\""; // Cobweb requires relative path to file first
        urlList += " \"" + filePath +  "\""; // Cobweb has problems with file path, likely local security issue
        setUrlScene(urlList);
        
        x3dToXhtmlDomConversionPanel.loadValuesInPanel(); // ensure latest greatest
    
        boolean conversionPanelSettingsReady = false;

        while (!conversionPanelSettingsReady)
        {
            dialog.setVisible(true);  // display X3dToXhtmlDomConversionPanel, blocks until a dialog button is pressed
            
            if       (descriptor.getValue() == resetButton)
            {
                resetValuesToDefault(); // but do not save since user may later cancel
                x3dToXhtmlDomConversionPanel.loadValuesInPanel ();
                // continue looping
            }
            else if (descriptor.getValue() == continueButton)
            {
                // do not save url until after panel operations are complete
                x3dToXhtmlDomConversionPanel.saveUrlValues();
                
                saveParametersHashMap (); // save new values from panel to hash map
                conversionPanelSettingsReady = true; // X3dToXhtmlDomConversionPanel panel operations complete
            }
            else // (descriptor.getValue() == cancelButton)
            {
                return null;
            }
        }
  //  if (BaseConversionsAction.xsltFilesRoot == null)
        String fileExtension;
        if (getPlayer().equalsIgnoreCase("X_ITE") || getPlayer().equalsIgnoreCase("Cobweb"))
             fileExtension = "X_ITE.html";
        else fileExtension = "X3dom.xhtml";
        fp = xsltOneFile(x3dEditor, "X3dTransforms/" + xsltFile, fileExtension, true, false, parametersHashMap);
  //  else {
  //    File target = new File(BaseConversionsAction.xsltFilesRoot, xsltFile);
  //    fp = xsltOneFile(ed, target.getAbsolutePath(), ".html", false, true, null);
  //  }
    if (fp != null) {
//          if (!fp.initialized)  // set defaults, first time through
//          {
//              fp.openInBrowser = true;
//              fp.openInEditor  = false;
//              fp.initialized   = true;
//          }
          if (fp.openInEditor) {
              ConversionsHelper.openInEditor(fp.file.getAbsolutePath());
          }
          if (fp.openInBrowser) {
              ConversionsHelper.openInBrowser(fp.file.getAbsolutePath());
          }
          return fp.file.getAbsolutePath();
      }
      return null;
    }
    
    private void saveParametersHashMap ()
    {
        parametersHashMap.clear();
        parametersHashMap.put("player",           getPlayer());
        // X3D
        parametersHashMap.put("x3dHeight",        getX3dHeight());
        parametersHashMap.put("x3dWidth",         getX3dWidth());
        // X3DOM
        parametersHashMap.put("showLog",          isShowLog());
        parametersHashMap.put("showProgress",     isShowProgress());
        parametersHashMap.put("showStatistics",   isShowStatistics());
        parametersHashMap.put("primitiveQuality", getPrimitiveQuality());
        // Cobweb
        parametersHashMap.put("cache",            isCache());
        parametersHashMap.put("urlScene",         getUrlScene());
        parametersHashMap.put("traceEnabled",     getTraceEnabled());
    }

  /**
   * Do this because this call in the super creates a new one every time, losing any
   * previous tt.
   * @return what goes into the menu
   */
  @Override
  public JMenuItem getMenuPresenter()
  {
    JMenuItem mi = super.getMenuPresenter();
    mi.setToolTipText(NbBundle.getMessage(getClass(), "CTL_XhtmlX3domAction_tt"));
    return mi;
  }

  @Override
  public HelpCtx getHelpCtx()
  {
    return HelpCtx.DEFAULT_HELP; // TODO .html help file?
  }

  @Override
  protected boolean asynchronous()
  {
    return false;
  }

    /**
     * @return the showLog
     */
    public boolean isShowLog() {
        return showLog;
    }

    /**
     * @param showLog the showLog to set
     */
    public void setShowLog(boolean showLog) {
        this.showLog = showLog;
    }

    /**
     * @return the showProgress
     */
    public boolean isShowProgress() {
        return showProgress;
    }

    /**
     * @param showProgress the showProgress to set
     */
    public void setShowProgress(boolean showProgress) {
        this.showProgress = showProgress;
    }

    /**
     * @return the showStatistics
     */
    public boolean isShowStatistics() {
        return showStatistics;
    }

    /**
     * @param showStatistics the showStatistics to set
     */
    public void setShowStatistics(boolean showStatistics) {
        this.showStatistics = showStatistics;
    }

    /**
     * @return the primitiveQuality
     */
    public String getPrimitiveQuality() {
        return primitiveQuality;
    }

    /**
     * @param primitiveQuality the primitiveQuality to set
     */
    public void setPrimitiveQuality(String primitiveQuality) {
        this.primitiveQuality = primitiveQuality;
    }

    /**
     * @return the traceEnabled
     */
    public String getTraceEnabled() {
        return traceEnabled;
    }

    /**
     * @param traceEnabled the traceEnabled to set
     */
    public void setTraceEnabled(String traceEnabled) {
        this.traceEnabled = traceEnabled;
    }

    /**
     * @return the x3dHeight
     */
    public String getX3dHeight() {
        return x3dHeight;
    }

    /**
     * @param x3dHeight the x3dHeight to set
     */
    public void setX3dHeight(String x3dHeight) {
        this.x3dHeight = x3dHeight;
    }

    /**
     * @return the x3dWidth
     */
    public String getX3dWidth() {
        return x3dWidth;
    }

    /**
     * @param x3dWidth the x3dWidth to set
     */
    public void setX3dWidth(String x3dWidth) {
        this.x3dWidth = x3dWidth;
    }

    /**
     * @return the url
     */
    public String getUrlScene() {
        return urlScene;
    }

    /**
     * @param url the url to set
     */
    public void setUrlScene(String url) {
        this.urlScene = url;
    }

    /**
     * @return the cache
     */
    public boolean isCache() {
        return cache;
    }

    /**
     * @param cache the cache to set
     */
    public void setCache(boolean cache) {
        this.cache = cache;
    }

    /**
     * @return the player
     */
    public String getPlayer() {
        return player;
    }

    /**
     * @param player the player to set
     */
    public void setPlayer(String player) {
        this.player = player;
    }
}
