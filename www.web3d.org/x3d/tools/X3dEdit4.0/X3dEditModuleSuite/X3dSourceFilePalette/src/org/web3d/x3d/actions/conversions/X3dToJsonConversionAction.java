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

@ActionID(id = "org.web3d.x3d.actions.conversions.X3dToJsonConversionAction", category = "Tools")

@ActionRegistration(   iconBase = "org/web3d/x3d/resources/json24.png",
                    displayName = "#CTL_X3dToJsonXsltAction",
                    lazy=true) // don't do lazy=false since iconBase no longer gets registered
@ActionReferences(value = {
  @ActionReference(path = "Menu/X3D-Edit/Author Workflow", position = 96),
  @ActionReference(path = "Menu/X3D-Edit/Export Model to File", position = 96),
  @ActionReference(path = "Toolbars/Author Workflow", position = 96),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/Author Workflow", position = 96),
  @ActionReference(path = "Editors/model/x3d+xml/Popup/Export Model to File", position = 96)
})

public final class X3dToJsonConversionAction extends BaseConversionsAction
{
    public static String xsltFile = "X3dToJson.xslt";

    private X3dToJsonConversionPanel x3dToJsonConversionPanel;

    private final HashMap<String,Object> parametersHashMap = new HashMap<>();

    private boolean x3dToJsonContinue = true;

    private final String indentEnabledDefault                 = "true";
    private final String stripCommentsDefault                 = "false";
    private final String stripDefaultAttributesDefault        = "true";
    private final String sourceTextDefault                    = "strings"; // escaped | strings | plaintext
    private final String traceEnabledDefault                  = "false";

    private String stripComments;
    private String stripDefaultAttributes;
    private String indentEnabled;
    private String sourceText;
    private String traceEnabled;
    
    private DialogDescriptor descriptor;
    private Dialog dialog;
    private JButton continueButton, resetButton, cancelButton;

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
        if (x3dToJsonConversionPanel == null)
        {
            x3dToJsonConversionPanel = new X3dToJsonConversionPanel (this);
    
            // pattern from Xj3dCadFilterOptionsPanel to launch and exit the panel
             continueButton = new JButton(NbBundle.getMessage(getClass(),"MSG_Continue"));
                resetButton = new JButton(NbBundle.getMessage(getClass(),"MSG_Reset"));
               cancelButton = new JButton(NbBundle.getMessage(getClass(),"MSG_Cancel"));
            continueButton.setToolTipText(NbBundle.getMessage(getClass(),"TIP_Continue"));
               resetButton.setToolTipText(NbBundle.getMessage(getClass(),"TIP_Reset"));
              cancelButton.setToolTipText(NbBundle.getMessage(getClass(),"TIP_Cancel"));
            HelpCtx.setHelpIDString(x3dToJsonConversionPanel, "X3dToJson.html");

            descriptor = new DialogDescriptor(
                x3dToJsonConversionPanel, // inner pane
                NbBundle.getMessage(getClass(),"X3dToJsonConversionPanel.DialogTitle"),
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
        setIndentEnabled(indentEnabledDefault);
        setStripComments(stripCommentsDefault);
        setStripDefaultAttributes(stripDefaultAttributesDefault);
        setSourceText(sourceTextDefault);
        setTraceEnabled(traceEnabledDefault);
    }
    
    @Override
    public String transformSingleFile(X3DEditorSupport.X3dEditor x3dEditor)
    {
        saveParametersHashMap (); // save prior values
    
        boolean conversionPanelSettingsReady = false;
        
        X3DDataObject dob = (X3DDataObject)x3dEditor.getX3dEditorSupport().getDataObject();
        // TODO save or else warn if not saved

        while (!conversionPanelSettingsReady)
        {
            dialog.setVisible(true); //blocks until dialog button pressed
            
            if       (descriptor.getValue() == resetButton)
            {
                resetValuesToDefault(); // but do not save since user may later cancel
                x3dToJsonConversionPanel.loadValuesInPanel ();
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
          filePack = xsltOneFile(x3dEditor,"X3dTransforms/"+xsltFile,".json",true,false,parametersHashMap);
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
        parametersHashMap.clear();
        parametersHashMap.put("indentEnabled", getIndentEnabled());
        parametersHashMap.put("stripComments", getStripComments());
        parametersHashMap.put("stripDefaultAttributes", getStripDefaultAttributes());
        
        parametersHashMap.put("sourceText", getSourceText());
        parametersHashMap.put("traceEnabled", getTraceEnabled());
    }
  
  @Override
  public String getName()
  {
    return NbBundle.getMessage(getClass(), "CTL_X3dToJsonXsltAction");
  }

  @Override
  protected String iconResource()
  {
    return "org/web3d/x3d/resources/json24.png";
  }
  // see org.openide.util.actions.SystemAction.iconResource() Javadoc for more details

  @Override
  public HelpCtx getHelpCtx()
  {
    return new HelpCtx("X3dToJson.html");
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
    mi.setToolTipText(NbBundle.getMessage(getClass(), "CTL_X3dToJsonXsltAction_tt"));
    return mi;
  }

    /**
     * @return the stripComments
     */
    public String getStripComments() {
        return stripComments;
    }

    /**
     * @param stripComments the stripComments to set
     */
    public void setStripComments(String stripComments) {
        this.stripComments = stripComments;
    }

    /**
     * @return the sourceText
     */
    public String getSourceText() {
        return sourceText;
    }

    /**
     * @param sourceText the sourceText to set
     */
    public void setSourceText(String sourceText) {
        this.sourceText = sourceText;
    }

    /**
     * @return the stripDefaultAttributes
     */
    public String getStripDefaultAttributes() {
        return stripDefaultAttributes;
    }

    /**
     * @param stripDefaultAttributes the stripDefaultAttributes to set
     */
    public void setStripDefaultAttributes(String stripDefaultAttributes) {
        this.stripDefaultAttributes = stripDefaultAttributes;
    }

    /**
     * @return the indentEnabled
     */
    public String getIndentEnabled() {
        return indentEnabled;
    }

    /**
     * @param indentEnabled the indentEnabled to set
     */
    public void setIndentEnabled(String indentEnabled) {
        this.indentEnabled = indentEnabled;
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
     * @return the x3dToJsonContinue
     */
    public boolean isX3dToJsonContinue() {
        return x3dToJsonContinue;
    }

    /**
     * @param x3dToJsonContinue the x3dToJsonContinue to set
     */
    public void setX3dToJsonContinue(boolean x3dToJsonContinue) {
        this.x3dToJsonContinue = x3dToJsonContinue;
    }
}
