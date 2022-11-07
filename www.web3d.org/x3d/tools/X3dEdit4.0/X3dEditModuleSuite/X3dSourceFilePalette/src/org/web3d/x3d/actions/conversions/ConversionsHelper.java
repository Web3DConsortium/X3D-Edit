/*
* Copyright (c) 1995-2022 held by the author(s).  All rights reserved.
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

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import org.openide.DialogDisplayer;
import org.openide.NotifyDescriptor;
import org.openide.cookies.EditCookie;
import org.openide.cookies.OpenCookie;
import org.openide.filesystems.FileObject;
import org.openide.filesystems.FileUtil;
import org.openide.loaders.DataObject;
import org.openide.util.Exceptions;
import org.openide.util.NbBundle;
import org.openide.windows.IOProvider;
import org.web3d.x3d.actions.conversions.BaseConversionsAction.TransformListener;
import xj3d.converter.Xj3DConv;

/**
 * ConversionsHelper.java
 * Created on Jun 12, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Bailey
 * @version $Id$
 */
public class ConversionsHelper
{
  static final JFileChooser saveChooser;
  static final JCheckBox openInEditor;
  static final JCheckBox openInBrowser;
  static final OpenResultInEditorChooserAccessory pan;
  private static boolean openInBrowserSetting = true;
  private static boolean openInEditorSetting  = false;
  private static String saveChooserDialogTitle = "Save X3D conversion";
  
  static {
    saveChooser =  new JFileChooser(); 
    pan = new OpenResultInEditorChooserAccessory();
    openInEditor = pan.getOpenInEditorCheckBox();
    openInEditor.setSelected(false); // default
    openInBrowser = pan.getOpenInBrowserCheckBox();
    openInBrowser.setSelected(true); // default
    saveChooser.setAccessory(pan);
  }
  public void initialize()
  {
    saveChooser.setDialogTitle(getSaveChooserDialogTitle());      
  }

    /**
     * @return the openInBrowserSetting
     */
    public static boolean isOpenInBrowserSetting() {
        return openInBrowserSetting;
    }

    /**
     * whether to open result in Browser
     * @return whether to open or not
     */
    public static boolean getOpenInBrowserSetting( ) {
       return openInBrowserSetting;
    }

    /**
     * @param aOpenInBrowserSetting the openInBrowserSetting to set
     */
    public static void setOpenInBrowserSetting(boolean aOpenInBrowserSetting) {
        openInBrowserSetting = aOpenInBrowserSetting;
    }

    /**
     * @return the openInEditorSetting
     */
    public static boolean isOpenInEditorSetting() {
        return openInEditorSetting;
    }

    /**
     * whether to open result in editor
     * @return whether to open or not
     */
    public static boolean getOpenInEditorSetting( ) {
       return openInEditorSetting;
    }
    /**
     * @param aOpenInEditorSetting the openInEditorSetting to set
     */
    public static void setOpenInEditorSetting(boolean aOpenInEditorSetting) {
        openInEditorSetting = aOpenInEditorSetting;
    }
  
  public static class saveFilePack
  {
    public File file;
    public boolean openInEditor;
    public boolean openInBrowser;
    public boolean initialized = false;
  }
  
  static HashMap<String,String> saveDirs = new HashMap<>();
  
  static saveFilePack getDestinationFile(File sourceFile, String destFileName)
  {
    return getDestinationFile(sourceFile, destFileName, true);
  }
      
  /**
   * Put up a chooser to aim at the destination
   * @param sourceFile
   * @param destFileName name only
   * @return null if should cancel
   */
  static saveFilePack getDestinationFile(File sourceFile, String destFileName, boolean wantOpenInEditorShown)
  {
    String saveDir = saveDirs.get(sourceFile.getAbsolutePath());
    if(saveDir == null)
      saveDir = sourceFile.getParent();
    
    saveChooser.setSelectedFile(new File(saveDir,destFileName));
    saveChooser.setAccessory(wantOpenInEditorShown?pan:null);

//    openInEditor.setEnabled(wantOpenInEditorShown);
//    openInBrowser.setEnabled(wantOpenInEditorShown);
    
    File selFile=null;
    boolean keepLooping = true;
    while(keepLooping) {
      int ret = saveChooser.showSaveDialog(null);
      if(ret == JFileChooser.CANCEL_OPTION)
        return null;
    
      selFile = saveChooser.getSelectedFile();
      if(selFile.exists()) {
        NotifyDescriptor d = new NotifyDescriptor.Confirmation(selFile.getName() + " " +
                                                               NbBundle.getMessage(ConversionsHelper.class,"File_exists._Overwrite?"),
                                                               NbBundle.getMessage(ConversionsHelper.class,"Confirm"),NotifyDescriptor.YES_NO_CANCEL_OPTION);
        Object ans = DialogDisplayer.getDefault().notify(d);
        
        if(ans == NotifyDescriptor.CANCEL_OPTION)
          return null;
        keepLooping = (ans != NotifyDescriptor.YES_OPTION);  // keep loop if overwrite != true
      }
      else
        keepLooping=false;
    }
    
    saveFilePack retData = new saveFilePack();
    retData.file = selFile;
    retData.openInEditor = openInEditor.isSelected();
    retData.openInBrowser= openInBrowser.isSelected();
    if (selFile != null)
        saveDirs.put(sourceFile.getAbsolutePath(),selFile.getParent());
    
    return retData;   
  }
  
  static public void openInEditor(String f)
  {
    try {
      openInEditor(new File(f));
     }
    catch (Exception e) {
      TransformListener.getInstance().message(NbBundle.getMessage(BaseConversionsAction.class,"Error_opening_") + f +
                                              NbBundle.getMessage(BaseConversionsAction.class,"_in_editor:_") + e.getClass().getName());
    }
  }

  static public void openInEditor(File f) throws Exception
  {
    FileObject fo = FileUtil.createData(f);
    if (fo != null) {
      DataObject dobj = DataObject.find(fo);
      if (dobj != null) {
        EditCookie ec = dobj.getLookup().lookup(EditCookie.class);
        if(ec != null) {
          ec.edit();
          return;
        }
        //else
        OpenCookie oc = dobj.getLookup().lookup(OpenCookie.class);
        if(oc != null) {
          oc.open();
          return;
        }
      }
    }
    throw new Exception("Editor not found for "+f.getAbsolutePath());
  }
  
  static public void openInBrowser(String f)
  {
    try {
      openInBrowser(new URL("file://"+f));
    }
    catch (MalformedURLException e) {
      IOProvider.getDefault().getIO("Output",false).getOut().append(
        NbBundle.getMessage(BaseConversionsAction.class,"Trying_to_display_") + f +
        NbBundle.getMessage(BaseConversionsAction.class,"_in_HtmlBrowser:_") + e.getLocalizedMessage());   
    }
  }
  
  static public void openInBrowser(URL url)
  {
    // HtmlBrowser.URLDisplayer.getDefault().showURL(url);
    
    String urlString = url.toString();
    
    // https://stackoverflow.com/questions/5226212/how-to-open-the-default-webbrowser-using-java
    if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE))
        try {
            Desktop.getDesktop().browse(new URI(urlString));
    } catch (IOException | URISyntaxException ex) {
        Exceptions.printStackTrace(ex);
    }
      
  }
  
//  static public void openInBrowser(String urlString)
//  {
//  }
  
  private static Xj3DConv converter;
  
  static Xj3DConv getXj3dConverter()
  {
    if(converter == null)
      converter = new Xj3DConv();
    return converter;
  }

    /**
     * @return the saveChooserDialogTitle
     */
    public static String getSaveChooserDialogTitle() {
        return saveChooserDialogTitle;
    }

    /**
     * @param newSaveChooserDialogTitle the saveChooserDialogTitle to set
     */
    public static void setSaveChooserDialogTitle(String newSaveChooserDialogTitle)
    {
        saveChooser.setDialogTitle(newSaveChooserDialogTitle);   
        saveChooserDialogTitle = newSaveChooserDialogTitle;   
    }
}
