/*
 * Copyright (c) 1995-2023 held by the author(s).  All rights reserved.
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
 *       (https://www.nps.edu and https://MovesInstitute.nps.edu)
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

import java.io.File;
import java.util.HashMap;
import javax.swing.JButton;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.ActionReferences;
import org.openide.awt.ActionRegistration;
import org.openide.util.HelpCtx;
import org.openide.util.NbBundle;
import org.web3d.x3d.X3DDataObject;
import org.web3d.x3d.X3DEditorSupport;
import static org.web3d.x3d.actions.conversions.X3dToXhtmlDomConversionFrame.CORS_TAB;
import static org.web3d.x3d.actions.conversions.X3dToXhtmlDomConversionFrame.NO_CHANGE_IN_TAB;
import org.web3d.x3d.options.X3dOptions;

@ActionID(id = "org.web3d.x3d.actions.conversions.XhtmlX3domAction", category = "X3D-Edit")

@ActionRegistration(
        iconBase = "org/web3d/x3d/resources/x3dom-whiteOnblue32.png",
     displayName = "#CTL_XhtmlX3domAction",
             lazy=true) // don't do lazy=false since iconBase no longer gets registered

@ActionReferences(value = {
  @ActionReference(path = "Menu/&X3D-Edit/&Author Workflow", position = 80),
  @ActionReference(path = "Menu/&X3D-Edit/&View Saved X3D Model", position = 151),
  @ActionReference(path = "Toolbars/X3D-Edit Author Workflow", position = 80),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/&Author Workflow", position = 80),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/&View Saved X3D Model", position = 151),
//@ActionReference(path = "Shortcuts", name = "CS-8"), // shortcut control-shift-8
  // see Apache NetBeans > Help > Keyboard Shortcuts Card for other shortcuts
})

public class X3dToXhtmlDomConversionAction extends BaseConversionsAction
{

    /**
     * @return the transformSingleFileName
     */
    public String getTransformSingleFileName()
    {
        return transformSingleFileName;
    }

    /**
     * @return the transformSingleFilePath
     */
    public String getTransformSingleFilePath()
    {
        return transformSingleFilePath;
    }
    public static String xsltFile = "X3dToX3domX_ITE.xslt";

    private X3dToXhtmlDomConversionFrame x3dToXhtmlDomConversionFrame;
//  private X3dToXhtmlDomConversionPanel x3dToXhtmlDomConversionPanel; // obsolete

    private final HashMap<String,Object> parametersHashMap = new HashMap<>();
    
    public  static final String             X3DOM = "X3DOM";
    public  static final String             X_ITE = "X_ITE";
    private              int         preferredTab = NO_CHANGE_IN_TAB;
    private final String            playerDefault = X3DOM;  // otherwise setPlayer(X_ITE) via subclass initialization
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
    
//    private DialogDescriptor dialogDescriptor;
//    private Dialog dialog;
    private JButton transformModelButton, resetButton, continueButton;
    
    private ConversionsHelper.saveFilePack filePack;
    /** prevent runaway autolaunch of converter before ready */
    private boolean readyForConversion = false;
    /** ask once if needed and not set */
    boolean userConfirmedWhetherAutolaunchOK = false;
    private String transformSingleFileName = new String();
    private String transformSingleFilePath = new String();

  @Override
  public String getName()
  {
    return NbBundle.getMessage(getClass(), "CTL_XhtmlX3domAction");
  }

  @Override
  protected String iconResource()
  {
      ConversionsHelper.setSaveChooserDialogTitle("Export X3D Model as XHTML with X3DOM via XSLT");
    
      if (getPlayer().equalsIgnoreCase("Cobweb") || getPlayer().equalsIgnoreCase("X_ITE"))
           return "org/web3d/x3d/resources/cobweb-logo32.png";
      else return "org/web3d/x3d/resources/x3dom-whiteOnblue32.png";
  }
  // see org.openide.util.actions.SystemAction.iconResource() Javadoc for more details

    @Override
    protected void initialize()
    {
        super.initialize(); // BaseConversionsAction

        // TODO confirm whether needed, possibly moving properties to X3dOptions instead
        if (parametersHashMap.isEmpty()) 
        {
            resetValuesToDefault ();
            saveParametersHashMap ();
        }
        // create frame to initialize settings; default re-use behavior is to retain prior values by not reinitializing each time
        if (x3dToXhtmlDomConversionFrame == null)
        {
            x3dToXhtmlDomConversionFrame = new X3dToXhtmlDomConversionFrame (this);
//            x3dToXhtmlDomConversionFrame.setParentActionClass(this); // allow callback configurations
            if      (getPreferredTab() == CORS_TAB)
                     x3dToXhtmlDomConversionFrame.setPaneIndex(CORS_TAB);  
            else if (getPlayer().equals(X3DOM))
                     x3dToXhtmlDomConversionFrame.setPaneIndex(X3dToXhtmlDomConversionFrame.X3DOM_TAB);
            else if (getPlayer().equals(X_ITE))
                     x3dToXhtmlDomConversionFrame.setPaneIndex(X3dToXhtmlDomConversionFrame.X_ITE_TAB);
        }
        
        if(x3dToXhtmlDomConversionFrame != null)
           SwingUtilities.invokeLater(() -> {
              
              // TODO problem, stray event/invocation immediately performing conversion task upon launch
              // hack prevents first-time fall through to processing xslt
              x3dToXhtmlDomConversionFrame.toFront();
              x3dToXhtmlDomConversionFrame.setVisible(true);
              
//            x3dToXhtmlDomConversionFrame.repaint(); // causes infinite loop
           });
        
// prior
//        // launch panel to confirm settings; default re-use behavior is to retain prior values by not reinitializing each time
//        if (x3dToXhtmlDomConversionPanel == null)
//        {
//            x3dToXhtmlDomConversionPanel = new X3dToXhtmlDomConversionPanel (this);
//            x3dToXhtmlDomConversionPanel.setParentActionClass(this); // allow callback configurations
//            if      (getPreferredTab() == CORS_TAB)
//                     x3dToXhtmlDomConversionPanel.setPaneIndex(CORS_TAB);  
//            else if (getPlayer().equals(X3DOM))
//                     x3dToXhtmlDomConversionPanel.setPaneIndex(X3dToXhtmlDomConversionPanel.X3DOM_TAB);
//            else if (getPlayer().equals(X_ITE))
//                     x3dToXhtmlDomConversionPanel.setPaneIndex(X3dToXhtmlDomConversionPanel.X_ITE_TAB);
//    
//            // pattern from Xj3dCadFilterOptionsPanel to launch and exit the panel
//            transformModelButton = new JButton(NbBundle.getMessage(getClass(),"MSG_TransformModel")); // Transform Model
//            transformModelButton.setToolTipText(NbBundle.getMessage(getClass(),"TIP_TransformModel"));
//
////            resetButton = new JButton(NbBundle.getMessage(getClass(),"MSG_Reset"));    // Reset
////            resetButton.setToolTipText(NbBundle.getMessage(getClass(),"TIP_Reset"));
//
//            continueButton = new JButton(NbBundle.getMessage(getClass(),"MSG_CONTINUE"));   // Continue
//            continueButton.setToolTipText(NbBundle.getMessage(getClass(),"TIP_Continue"));
//            x3dToXhtmlDomConversionPanel.updatePageIntegrationTabbedPaneState();
//            
////            HelpCtx.setHelpIDString(x3dToXhtmlDomConversionPanel, "X3dToJson.html");
//
//            // https://bits.netbeans.org/dev/javadoc/org-openide-dialogs/org/openide/DialogDescriptor.html
//            descriptor = new DialogDescriptor(
//                x3dToXhtmlDomConversionPanel, // inner pane
//                NbBundle.getMessage(getClass(),"X3dToXhtmlDomConversionPanel.DialogTitle"),
//                true, // modal
//                new Object[]{transformModelButton, continueButton},  // other buttons: resetButton, 
//                transformModelButton,                      // default
//                DialogDescriptor.DEFAULT_ALIGN,
//                HelpCtx.DEFAULT_HELP, // unneeded, unfortunately provides a button
//                null); // action listener
//
//            // no way to set options without also getting a help contact,
//            // TODO need to replace this block with classic Swing setup like DownloadX3dExamplesArchivesAction
//            dialog = DialogDisplayer.getDefault().createDialog(descriptor);
//            dialog.setResizable(true);
//            dialog.pack();
//        }
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
        // used in parent X3dToXhtmlDomConversionAction and X3dToXhtmlDomConversionPanel
        X3DDataObject x3dDataObject = (X3DDataObject)x3dEditor.getX3dEditorSupport().getDataObject();
        transformSingleFileName = x3dDataObject.getPrimaryFile().getNameExt();
        transformSingleFilePath = x3dDataObject.getPrimaryFile().getPath(); // full path, including file name
        
        String urlList;
        urlList  =  "\"" + transformSingleFileName + "\""; // Cobweb requires relative path to file first
        urlList += " \"" + transformSingleFilePath + "\""; // Cobweb has problems with file path, likely local security issue
        
        // TODO also add identifier if provided in scene
        // x3dDataObject.getFreshSaxSource().getInputSource().etc...;
        
        // TODO parametersHashMap add urlList
        
        
        setUrlScene(urlList);
        x3dToXhtmlDomConversionFrame.setUrlData(urlList); // update panel display
        
//        x3dToXhtmlDomConversionFrame.initializeValuesInPanel(); // ensure latest greatest
    
//        boolean conversionPanelSettingsReady = false;
//
//        while (!conversionPanelSettingsReady)
//        {
//            dialog.setVisible(true);  // display X3dToXhtmlDomConversionPanel, blocks until a dialog button is pressed
//            
//            if       (descriptor.getValue() == resetButton)
//            {
//                resetValuesToDefault(); // but do not save since user may later cancel
//                x3dToXhtmlDomConversionPanel.initializeValuesInPanel ();
//                // continue looping
//            }
//            else if (descriptor.getValue() == transformModelButton)
//            {
//                // do not save url until after panel operations are complete
//                x3dToXhtmlDomConversionPanel.saveUrlValues();
//                
//                saveParametersHashMap (); // save new values from panel to hash map
//                conversionPanelSettingsReady = true; // X3dToXhtmlDomConversionPanel panel operations complete
//            }
//            else // (descriptor.getValue() == cancelButton)
//            {
//                return null;
//            }
//        }
  //  if (BaseConversionsAction.xsltFilesRoot == null)
        String fileExtension;
        int    newModelPort;
        if (getPlayer().equalsIgnoreCase("X_ITE") || getPlayer().equalsIgnoreCase("Cobweb"))
        {
            fileExtension = "X_ITE.html";
            if (!userConfirmedWhetherAutolaunchOK && 
                !X3dOptions.isActiveX3dModelServerAutolaunch() && 
                !X3dToXhtmlDomConversionFrame.isPortBoundAuthorModelsServer())
            {
                userConfirmedWhetherAutolaunchOK = true; // ask once per session
                
                NotifyDescriptor descriptor = new NotifyDescriptor.Confirmation(
                        "Autolaunch localhost http server to overcome CORS restrictions?",
                        "Autolaunch localhost http server?", NotifyDescriptor.YES_NO_OPTION);
                if (DialogDisplayer.getDefault().notify(descriptor) == NotifyDescriptor.YES_OPTION)
                {
                    X3dOptions.setActiveX3dModelServerAutolaunch(true);
                }
            }
            // time to autolaunch for new model, if allowed
            if (X3dOptions.isActiveX3dModelServerAutolaunch())
            {
                newModelPort = x3dToXhtmlDomConversionFrame.launchNewActiveX3dModelServer(transformSingleFileName, transformSingleFilePath);
                if (newModelPort == -1)
                    System.err.println ("*** transformSingleFile() " + transformSingleFileName + " launchNewActiveX3dModelServerfailed, continuing...");
            }
        }
        else fileExtension = "X3dom.xhtml";
        
        saveParametersHashMap(); // save latest values for stylesheet usage
        filePack = xsltOneFile(x3dEditor, "X3dTransforms/" + xsltFile, fileExtension, true, false, parametersHashMap);
  //  else {
  //    File target = new File(BaseConversionsAction.xsltFilesRoot, xsltFile);
  //    fp = xsltOneFile(ed, target.getAbsolutePath(), ".html", false, true, null);
  //  }
    if (filePack != null) {
//          if (!fp.initialized)  // set defaults, first time through
//          {
//              fp.openInBrowser = true;
//              fp.openInEditor  = false;
//              fp.initialized   = true;
//          }
          if (filePack.openInEditor)
          {
              ConversionsHelper.openInEditor(filePack.file.getAbsolutePath());
          }
          if (filePack.openInBrowser)
          {
              x3dToXhtmlDomConversionFrame.getLocalHttpPrefix();
              ConversionsHelper.openInBrowser(filePack.file.getAbsolutePath());
          }
          return filePack.file.getAbsolutePath();
      }
      return null;
    }
    
    private void saveParametersHashMap()
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

    /**
     * @return the preferredTab
     */
    public int getPreferredTab()
    {
        return preferredTab;
    }

    /**
     * @param aPreferredTab the preferredTab to set
     */
    public void setPreferredTab(int aPreferredTab)
    {
        preferredTab = aPreferredTab;
    }
    public void setTransformModelButtonEnabled (boolean value)
    {
        if (transformModelButton != null)
            transformModelButton.setEnabled(value);
    }

    /**
     * @return the readyForConversion
     */
    public boolean isReadyForConversion()
    {
        return readyForConversion;
    }

    /**
     * @param readyForConversion the readyForConversion to set
     */
    public void setReadyForConversion(boolean readyForConversion)
    {
        this.readyForConversion = readyForConversion;
    }
}
