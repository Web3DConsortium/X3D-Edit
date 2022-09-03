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
import java.io.IOException;
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

@ActionID(id = "org.web3d.x3d.actions.conversions.X3dTidyConversionAction", category = "Tools")

@ActionRegistration(   iconBase = "org/web3d/x3d/resources/bubble_mania24.png",
                    displayName = "#CTL_X3dTidyXsltAction",
                    lazy=true) // don't do lazy=false since iconBase no longer gets registered
@ActionReferences(value = {
  @ActionReference(path = "Menu/X3D-Edit/Author Workflow", position = 70),
  @ActionReference(path = "Toolbars/Author Workflow", position = 70),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/Author Workflow", position = 70),
})

public final class X3dTidyConversionAction extends BaseConversionsAction
{
    public static String xsltFile = "X3dTidy.xslt";

    private X3dTidyConversionPanel x3dTidyConversionPanel;

    private final HashMap<String,Object> x3dTidyParametersHashMap = new HashMap<>();

    private boolean x3dTidyContinue = true;

    private final String conversionRequiredDefault            = "true";
    private final String titleDefault                         = "";     // default title value for file name is empty
    private final String modifyX3dVersionDefault              = "true";
    private final String revisedX3dVersionDefault             = "3.3";
    private final String fixDateFormatsDefault                = "true";
    private final String fixMFStringQuotesDefault             = "true";
    private final String fixGeoSystemMetadataDefault          = "true";
    private final String fixMetaNamesMatchDublinCoreDefault   = "true";
    private final String replaceBlackEmissiveColorDefault     = "true";

    private final String fixUrlAdditionHttpAddressesDefault   = "true"; // expand local url array to include online addresses

    private final String appendWrlAfterX3dAddressesDefault    = "true"; // note that url quotes are always added if needed
    private final String prependX3dBeforeWrlAddressesDefault  = "true";
    private final String defaultUrlAddressDefault             = "";     // <!-- default value is empty -->
  //    <!-- $baseUrlAvailable false means that stylesheet is being used by servlet, or else styled results won't be in original directory: -->
  //  private final String baseUrlAvailable                   = "true";
    private final String changeJavascriptEcmascriptDefault    = "true";
    private final String insertMissingEcmascriptDefault       = "true";
    private final String insertMissingMetaLicenseDefault      = "true";
    private final String licenseLinkDefault                   = "https://www.web3d.org/x3d/content/examples/license.html";

    private final String HAnimGeometryRemoveDefault           = "false";
    private final String HAnimSkeletonIllustrateDefault       = "false";
    private final String HAnimSiteIllustrateDefault           = "false";
    private final String HAnimViewpointIllustrateDefault      = "false";
    private final String HAnimAddBoneSegmentsDefault          = "false";

    private final String jointColorDefault                    = "1 0.5 0";
    private final String segmentColorDefault                  = "1 1 0";
    private final String siteColorDefault                     = "1 0 0";
    private final String siteViewpointColorDefault            = "0 0 1";

    private String conversionRequired;
    private String title;
    private String modifyX3dVersion;
    private String revisedX3dVersion;
    private String fixDateFormats;
    private String fixMFStringQuotes;
    private String fixGeoSystemMetadata;
    private String fixMetaNamesMatchDublinCore;
    private String replaceBlackEmissiveColor;

    private String fixUrlAdditionHttpAddresses;

    private String appendWrlAfterX3dAddresses;
    private String prependX3dBeforeWrlAddresses;
    private String defaultUrlAddress;

    private String changeJavascriptEcmascript;
    private String insertMissingEcmascript;
    private String insertMissingMetaLicense;
    private String licenseLink;

    private String HAnimGeometryRemove;
    private String HAnimSkeletonIllustrate;
    private String HAnimSiteIllustrate;
    private String HAnimViewpointIllustrate;
    private String HAnimAddBoneSegments;

    private String jointColor;
    private String segmentColor;
    private String siteColor;
    private String siteViewpointColor;
  
    private DialogDescriptor descriptor;
    private Dialog dialog;
    private JButton continueButton, resetButton, cancelButton;

    @Override
    protected void initialize()
    {
        super.initialize();

        if (x3dTidyParametersHashMap.isEmpty())
        {
            resetValuesToDefault ();
            saveParametersHashMap ();
        }
        // launch panel to confirm settings; default re-use behavior is to retain prior values by not reinitializing each time
        if (x3dTidyConversionPanel == null)
        {
            x3dTidyConversionPanel = new X3dTidyConversionPanel (this);
    
            // pattern from Xj3dCadFilterOptionsPanel to launch and exit the panel
             continueButton = new JButton(NbBundle.getMessage(getClass(),"MSG_Continue"));
                resetButton = new JButton(NbBundle.getMessage(getClass(),"MSG_Reset"));
               cancelButton = new JButton(NbBundle.getMessage(getClass(),"MSG_Cancel"));
            continueButton.setToolTipText(NbBundle.getMessage(getClass(),"TIP_Continue"));
               resetButton.setToolTipText(NbBundle.getMessage(getClass(),"TIP_Reset"));
              cancelButton.setToolTipText(NbBundle.getMessage(getClass(),"TIP_Cancel"));
            HelpCtx.setHelpIDString(x3dTidyConversionPanel, "X3dTidy.html");

            descriptor = new DialogDescriptor(
                x3dTidyConversionPanel, // inner pane
                NbBundle.getMessage(getClass(),"X3dTidyConversionPanel.DialogTitle"),
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
        setConversionRequired(conversionRequiredDefault);
        setTitle(titleDefault);
        setModifyX3dVersion(modifyX3dVersionDefault);
        setRevisedX3dVersion(revisedX3dVersionDefault);
        setFixDateFormats(fixDateFormatsDefault);
        setFixMFStringQuotes(fixMFStringQuotesDefault);
        setFixGeoSystemMetadata(fixGeoSystemMetadataDefault);
        setFixMetaNamesMatchDublinCore(fixMetaNamesMatchDublinCoreDefault);
        setReplaceBlackEmissiveColor(replaceBlackEmissiveColorDefault);

        setFixUrlAdditionHttpAddresses(fixUrlAdditionHttpAddressesDefault);
        setAppendWrlAfterX3dAddresses(appendWrlAfterX3dAddressesDefault); 
        setPrependX3dBeforeWrlAddresses(prependX3dBeforeWrlAddressesDefault);
        setDefaultUrlAddress(defaultUrlAddressDefault);
    
        setChangeJavascriptEcmascript(changeJavascriptEcmascriptDefault);
        setInsertMissingEcmascript(insertMissingEcmascriptDefault);
        setInsertMissingMetaLicense(insertMissingMetaLicenseDefault);
        setLicenseLink(licenseLinkDefault);

        HAnimGeometryRemove           = HAnimGeometryRemoveDefault;
        HAnimSkeletonIllustrate       = HAnimSkeletonIllustrateDefault;
        HAnimSiteIllustrate           = HAnimSiteIllustrateDefault;
        HAnimViewpointIllustrate      = HAnimViewpointIllustrateDefault;
        HAnimAddBoneSegments          = HAnimAddBoneSegmentsDefault;

        jointColor                    = jointColorDefault;
        segmentColor                  = segmentColorDefault;
        siteColor                     = siteColorDefault;
        siteViewpointColor            = siteViewpointColorDefault;
    }
    
    @Override
    public String transformSingleFile(X3DEditorSupport.X3dEditor x3dEditor)
    {
        saveParametersHashMap (); // save prior values
    
        boolean conversionPanelSettingsReady = false;
        
        X3DDataObject dob = (X3DDataObject)x3dEditor.getX3dEditorSupport().getDataObject();
        // TODO save or else warn if not saved
        try {
            boolean containsHumanoid = dob.getPrimaryFile().asText().contains("<HAnimHumanoid");
            x3dTidyConversionPanel.setHAnimPanelEnabled(containsHumanoid);
            boolean containsGeoMetadata = dob.getPrimaryFile().asText().contains("<GeoMetadata");
            x3dTidyConversionPanel.setGeospatialPanelEnabled(containsGeoMetadata);
        } catch (IOException ex) {
//            Exceptions.printStackTrace(ex);
        }

        while (!conversionPanelSettingsReady)
        {
            dialog.setVisible(true); //blocks until dialog button pressed
            
            if       (descriptor.getValue() == resetButton)
            {
                resetValuesToDefault(); // but do not save since user may later cancel
                x3dTidyConversionPanel.loadValuesInPanel ();
                // continue looping
            }
            else if (descriptor.getValue() == continueButton)
            {
                saveParametersHashMap (); // save new values from panel to hash map
                conversionPanelSettingsReady = true;
            }
            else // (descriptor.getValue() == cancelButton)
            {
                return null;
            }
        }
      
        ConversionsHelper.saveFilePack filePack;
        //  if(BaseConversionsAction.xsltFilesRoot == null)
          filePack = xsltOneFile(x3dEditor,"X3dTransforms/"+xsltFile,"Tidy.x3d",true,false,x3dTidyParametersHashMap);
        //  else {
        //    File target = new File(BaseConversionsAction.xsltFilesRoot,xsltFile);
        //    fp = xsltOneFile(ed,target.getAbsolutePath(),".wrl",false,true,null);
        //  }

        if(filePack != null) {
          // If you pass true above, uncomment below
          if(filePack.openInEditor)
            ConversionsHelper.openInEditor(filePack.file.getAbsolutePath());
          if(filePack.openInBrowser)
            ConversionsHelper.openInBrowser(filePack.file.getAbsolutePath());
          return filePack.file.getAbsolutePath();
        }
        return null;
    }
    
    private void saveParametersHashMap ()
    {
        x3dTidyParametersHashMap.clear();
        x3dTidyParametersHashMap.put("conversionRequired", getConversionRequired());
        x3dTidyParametersHashMap.put("title", getTitle());
        x3dTidyParametersHashMap.put("modifyX3dVersion", getModifyX3dVersion());
        x3dTidyParametersHashMap.put("revisedX3dVersion", getRevisedX3dVersion());
        x3dTidyParametersHashMap.put("fixDateFormats", getFixDateFormats());
        x3dTidyParametersHashMap.put("fixMFStringQuotes", getFixMFStringQuotes());
        x3dTidyParametersHashMap.put("fixGeoSystemMetadata", getFixGeoSystemMetadata());
        x3dTidyParametersHashMap.put("fixMetaNamesMatchDublinCore", getFixMetaNamesMatchDublinCore());
        x3dTidyParametersHashMap.put("replaceBlackEmissiveColor", getReplaceBlackEmissiveColor());
        x3dTidyParametersHashMap.put("fixUrlAdditionHttpAddresses", getFixUrlAdditionHttpAddresses());
        x3dTidyParametersHashMap.put("appendWrlAfterX3dAddresses", getAppendWrlAfterX3dAddresses());
        x3dTidyParametersHashMap.put("prependX3dBeforeWrlAddresses", getPrependX3dBeforeWrlAddresses());
        x3dTidyParametersHashMap.put("defaultUrlAddress", getDefaultUrlAddress());
        x3dTidyParametersHashMap.put("changeJavascriptEcmascript", getChangeJavascriptEcmascript());
        x3dTidyParametersHashMap.put("insertMissingEcmascript", getInsertMissingEcmascript());
        x3dTidyParametersHashMap.put("insertMissingMetaLicense", getInsertMissingMetaLicense());
        x3dTidyParametersHashMap.put("licenseLink", getLicenseLink());
        x3dTidyParametersHashMap.put("HAnimGeometryRemove", HAnimGeometryRemove);
        x3dTidyParametersHashMap.put("HAnimSkeletonIllustrate", HAnimSkeletonIllustrate);
        x3dTidyParametersHashMap.put("HAnimSiteIllustrate", HAnimSiteIllustrate);
        x3dTidyParametersHashMap.put("HAnimViewpointIllustrate", HAnimViewpointIllustrate);
        x3dTidyParametersHashMap.put("HAnimAddBoneSegments", HAnimAddBoneSegments);
        x3dTidyParametersHashMap.put("jointColor", jointColor);
        x3dTidyParametersHashMap.put("segmentColor", segmentColor);
        x3dTidyParametersHashMap.put("siteColor", siteColor);
        x3dTidyParametersHashMap.put("siteViewpointColor", siteViewpointColor);
    }
  
  @Override
  public String getName()
  {
    return NbBundle.getMessage(getClass(), "CTL_X3dTidyXsltAction");
  }

  @Override
  protected String iconResource()
  {
    return "org/web3d/x3d/resources/bubble_mania24.png";
  }
  // see org.openide.util.actions.SystemAction.iconResource() Javadoc for more details

  @Override
  public HelpCtx getHelpCtx()
  {
    return new HelpCtx("X3dTidy.html");
  }

  @Override
  protected boolean asynchronous()
  {
    return false;
  }
  
  
  /**
   * Do this because this call in the super creates a new one every time, losing any 
   * previous tt tooltip.
   * @return what goes into the menu
   */
  @Override
  public JMenuItem getMenuPresenter()
  {
    JMenuItem mi = super.getMenuPresenter();
    mi.setToolTipText(NbBundle.getMessage(getClass(), "CTL_X3dTidyXsltAction_tt"));
    return mi;
  }

    /**
     * @return the conversionRequired
     */
    public String getConversionRequired() {
        return conversionRequired;
    }

    /**
     * @param conversionRequired the conversionRequired to set
     */
    public void setConversionRequired(String conversionRequired) {
        this.conversionRequired = conversionRequired;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the modifyX3dVersion
     */
    public String getModifyX3dVersion() {
        return modifyX3dVersion;
    }

    /**
     * @param modifyX3dVersion the modifyX3dVersion to set
     */
    public void setModifyX3dVersion(String modifyX3dVersion) {
        this.modifyX3dVersion = modifyX3dVersion;
    }

    /**
     * @return the revisedX3dVersion
     */
    public String getRevisedX3dVersion() {
        return revisedX3dVersion;
    }

    /**
     * @param revisedX3dVersion the revisedX3dVersion to set
     */
    public void setRevisedX3dVersion(String revisedX3dVersion) {
        this.revisedX3dVersion = revisedX3dVersion;
    }

    /**
     * @return the fixDateFormats
     */
    public String getFixDateFormats() {
        return fixDateFormats;
    }

    /**
     * @param fixDateFormats the fixDateFormats to set
     */
    public void setFixDateFormats(String fixDateFormats) {
        this.fixDateFormats = fixDateFormats;
    }

    /**
     * @return the fixMFStringQuotes
     */
    public String getFixMFStringQuotes() {
        return fixMFStringQuotes;
    }

    /**
     * @param fixMFStringQuotes the fixMFStringQuotes to set
     */
    public void setFixMFStringQuotes(String fixMFStringQuotes) {
        this.fixMFStringQuotes = fixMFStringQuotes;
    }

    /**
     * @return the fixGeoSystemMetadata
     */
    public String getFixGeoSystemMetadata() {
        return fixGeoSystemMetadata;
    }

    /**
     * @param fixGeoSystemMetadata the fixGeoSystemMetadata to set
     */
    public void setFixGeoSystemMetadata(String fixGeoSystemMetadata) {
        this.fixGeoSystemMetadata = fixGeoSystemMetadata;
    }

    /**
     * @return the fixMetaNamesMatchDublinCore
     */
    public String getFixMetaNamesMatchDublinCore() {
        return fixMetaNamesMatchDublinCore;
    }

    /**
     * @param fixMetaNamesMatchDublinCore the fixMetaNamesMatchDublinCore to set
     */
    public void setFixMetaNamesMatchDublinCore(String fixMetaNamesMatchDublinCore) {
        this.fixMetaNamesMatchDublinCore = fixMetaNamesMatchDublinCore;
    }

    /**
     * @return the replaceBlackEmissiveColor
     */
    public String getReplaceBlackEmissiveColor() {
        return replaceBlackEmissiveColor;
    }

    /**
     * @param replaceBlackEmissiveColor the replaceBlackEmissiveColor to set
     */
    public void setReplaceBlackEmissiveColor(String replaceBlackEmissiveColor) {
        this.replaceBlackEmissiveColor = replaceBlackEmissiveColor;
    }

    /**
     * @return the fixUrlAdditionHttpAddresses
     */
    public String getFixUrlAdditionHttpAddresses() {
        return fixUrlAdditionHttpAddresses;
    }

    /**
     * @param fixUrlAdditionHttpAddresses the fixUrlAdditionHttpAddresses to set
     */
    public void setFixUrlAdditionHttpAddresses(String fixUrlAdditionHttpAddresses) {
        this.fixUrlAdditionHttpAddresses = fixUrlAdditionHttpAddresses;
    }

    /**
     * @return the appendWrlAfterX3dAddresses
     */
    public String getAppendWrlAfterX3dAddresses() {
        return appendWrlAfterX3dAddresses;
    }

    /**
     * @param appendWrlAfterX3dAddresses the appendWrlAfterX3dAddresses to set
     */
    public void setAppendWrlAfterX3dAddresses(String appendWrlAfterX3dAddresses) {
        this.appendWrlAfterX3dAddresses = appendWrlAfterX3dAddresses;
    }

    /**
     * @return the prependX3dBeforeWrlAddresses
     */
    public String getPrependX3dBeforeWrlAddresses() {
        return prependX3dBeforeWrlAddresses;
    }

    /**
     * @param prependX3dBeforeWrlAddresses the prependX3dBeforeWrlAddresses to set
     */
    public void setPrependX3dBeforeWrlAddresses(String prependX3dBeforeWrlAddresses) {
        this.prependX3dBeforeWrlAddresses = prependX3dBeforeWrlAddresses;
    }

    /**
     * @return the defaultUrlAddress
     */
    public String getDefaultUrlAddress() {
        return defaultUrlAddress;
    }

    /**
     * @param defaultUrlAddress the defaultUrlAddress to set
     */
    public void setDefaultUrlAddress(String defaultUrlAddress) {
        this.defaultUrlAddress = defaultUrlAddress;
    }

    /**
     * @return the changeJavascriptEcmascript
     */
    public String getChangeJavascriptEcmascript() {
        return changeJavascriptEcmascript;
    }

    /**
     * @param changeJavascriptEcmascript the changeJavascriptEcmascript to set
     */
    public void setChangeJavascriptEcmascript(String changeJavascriptEcmascript) {
        this.changeJavascriptEcmascript = changeJavascriptEcmascript;
    }

    /**
     * @return the insertMissingEcmascript
     */
    public String getInsertMissingEcmascript() {
        return insertMissingEcmascript;
    }

    /**
     * @param insertMissingEcmascript the insertMissingEcmascript to set
     */
    public void setInsertMissingEcmascript(String insertMissingEcmascript) {
        this.insertMissingEcmascript = insertMissingEcmascript;
    }

    /**
     * @return the insertMissingMetaLicense
     */
    public String getInsertMissingMetaLicense() {
        return insertMissingMetaLicense;
    }

    /**
     * @param insertMissingMetaLicense the insertMissingMetaLicense to set
     */
    public void setInsertMissingMetaLicense(String insertMissingMetaLicense) {
        this.insertMissingMetaLicense = insertMissingMetaLicense;
    }

    /**
     * @return the licenseLink
     */
    public String getLicenseLink() {
        return licenseLink;
    }

    /**
     * @param licenseLink the licenseLink to set
     */
    public void setLicenseLink(String licenseLink) {
        this.licenseLink = licenseLink;
    }

    /**
     * @return the HAnimGeometryRemove
     */
    public String getHAnimGeometryRemove() {
        return HAnimGeometryRemove;
    }

    /**
     * @param HAnimGeometryRemove the HAnimGeometryRemove to set
     */
    public void setHAnimGeometryRemove(String HAnimGeometryRemove) {
        this.HAnimGeometryRemove = HAnimGeometryRemove;
    }

    /**
     * @return the HAnimSkeletonIllustrate
     */
    public String getHAnimSkeletonIllustrate() {
        return HAnimSkeletonIllustrate;
    }

    /**
     * @param HAnimSkeletonIllustrate the HAnimSkeletonIllustrate to set
     */
    public void setHAnimSkeletonIllustrate(String HAnimSkeletonIllustrate) {
        this.HAnimSkeletonIllustrate = HAnimSkeletonIllustrate;
    }

    /**
     * @return the HAnimSiteIllustrate
     */
    public String getHAnimSiteIllustrate() {
        return HAnimSiteIllustrate;
    }

    /**
     * @param HAnimSiteIllustrate the HAnimSiteIllustrate to set
     */
    public void setHAnimSiteIllustrate(String HAnimSiteIllustrate) {
        this.HAnimSiteIllustrate = HAnimSiteIllustrate;
    }

    /**
     * @return the HAnimViewpointIllustrate
     */
    public String getHAnimViewpointIllustrate() {
        return HAnimViewpointIllustrate;
    }

    /**
     * @param HAnimViewpointIllustrate the HAnimViewpointIllustrate to set
     */
    public void setHAnimViewpointIllustrate(String HAnimViewpointIllustrate) {
        this.HAnimViewpointIllustrate = HAnimViewpointIllustrate;
    }

    /**
     * @return the HAnimAddBoneSegments
     */
    public String getHAnimAddBoneSegments() {
        return HAnimAddBoneSegments;
    }

    /**
     * @param HAnimAddBoneSegments the HAnimAddBoneSegments to set
     */
    public void setHAnimAddBoneSegments(String HAnimAddBoneSegments) {
        this.HAnimAddBoneSegments = HAnimAddBoneSegments;
    }

    /**
     * @return the jointColor
     */
    public String getJointColor() {
        return jointColor;
    }

    /**
     * @param jointColor the jointColor to set
     */
    public void setJointColor(String jointColor) {
        this.jointColor = jointColor;
    }

    /**
     * @return the segmentColor
     */
    public String getSegmentColor() {
        return segmentColor;
    }

    /**
     * @param segmentColor the segmentColor to set
     */
    public void setSegmentColor(String segmentColor) {
        this.segmentColor = segmentColor;
    }

    /**
     * @return the siteColor
     */
    public String getSiteColor() {
        return siteColor;
    }

    /**
     * @param siteColor the siteColor to set
     */
    public void setSiteColor(String siteColor) {
        this.siteColor = siteColor;
    }

    /**
     * @return the siteViewpointColor
     */
    public String getSiteViewpointColor() {
        return siteViewpointColor;
    }

    /**
     * @param siteViewpointColor the siteViewpointColor to set
     */
    public void setSiteViewpointColor(String siteViewpointColor) {
        this.siteViewpointColor = siteViewpointColor;
    }

    /**
     * @return the x3dTidyContinue
     */
    public boolean isX3dTidyContinue() {
        return x3dTidyContinue;
    }

    /**
     * @param x3dTidyContinue the x3dTidyContinue to set
     */
    public void setX3dTidyContinue(boolean x3dTidyContinue) {
        this.x3dTidyContinue = x3dTidyContinue;
    }
}
